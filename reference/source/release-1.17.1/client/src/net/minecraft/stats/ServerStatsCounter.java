package net.minecraft.stats;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ClientboundAwardStatsPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerStatsCounter extends StatsCounter {
   private static final Logger LOGGER = LogManager.getLogger();
   private final MinecraftServer server;
   private final File file;
   private final Set<Stat<?>> dirty = Sets.<Stat<?>>newHashSet();

   public ServerStatsCounter(MinecraftServer var1, File var2) {
      this.server = â˜ƒ;
      this.file = â˜ƒ;
      if (â˜ƒ.isFile()) {
         try {
            this.parseLocal(â˜ƒ.getFixerUpper(), FileUtils.readFileToString(â˜ƒ));
         } catch (IOException var4) {
            LOGGER.error("Couldn't read statistics file {}", â˜ƒ, var4);
         } catch (JsonParseException var5) {
            LOGGER.error("Couldn't parse statistics file {}", â˜ƒ, var5);
         }
      }
   }

   public void save() {
      try {
         FileUtils.writeStringToFile(this.file, this.toJson());
      } catch (IOException var2) {
         LOGGER.error("Couldn't save stats", var2);
      }
   }

   @Override
   public void setValue(Player var1, Stat<?> var2, int var3) {
      super.setValue(â˜ƒ, â˜ƒ, â˜ƒ);
      this.dirty.add(â˜ƒ);
   }

   private Set<Stat<?>> getDirty() {
      Set<Stat<?>> â˜ƒ = Sets.<Stat<?>>newHashSet(this.dirty);
      this.dirty.clear();
      return â˜ƒ;
   }

   public void parseLocal(DataFixer var1, String var2) {
      try {
         JsonReader â˜ƒ = new JsonReader(new StringReader(â˜ƒ));

         label51: {
            try {
               â˜ƒ.setLenient(false);
               JsonElement â˜ƒx = Streams.parse(â˜ƒ);
               if (!â˜ƒx.isJsonNull()) {
                  CompoundTag â˜ƒxx = fromJson(â˜ƒx.getAsJsonObject());
                  if (!â˜ƒxx.contains("DataVersion", 99)) {
                     â˜ƒxx.putInt("DataVersion", 1343);
                  }

                  â˜ƒxx = NbtUtils.update(â˜ƒ, DataFixTypes.STATS, â˜ƒxx, â˜ƒxx.getInt("DataVersion"));
                  if (!â˜ƒxx.contains("stats", 10)) {
                     break label51;
                  }

                  CompoundTag â˜ƒxx = â˜ƒxx.getCompound("stats");
                  Iterator var7 = â˜ƒxx.getAllKeys().iterator();

                  while(true) {
                     if (!var7.hasNext()) {
                        break label51;
                     }

                     String â˜ƒxxx = (String)var7.next();
                     if (â˜ƒxx.contains(â˜ƒxxx, 10)) {
                        Util.ifElse(
                           Registry.STAT_TYPE.getOptional(new ResourceLocation(â˜ƒxxx)),
                           var3x -> {
                              CompoundTag â˜ƒ = â˜ƒ.getCompound(â˜ƒ);
   
                              for(String â˜ƒx : â˜ƒ.getAllKeys()) {
                                 if (â˜ƒ.contains(â˜ƒx, 99)) {
                                    Util.ifElse(
                                       this.getStat(var3x, â˜ƒx),
                                       var3xx -> this.stats.put(var3xx, â˜ƒ.getInt(â˜ƒ)),
                                       () -> LOGGER.warn("Invalid statistic in {}: Don't know what {} is", this.file, â˜ƒ)
                                    );
                                 } else {
                                    LOGGER.warn("Invalid statistic value in {}: Don't know what {} is for key {}", this.file, â˜ƒ.get(â˜ƒx), â˜ƒx);
                                 }
                              }
                           },
                           () -> LOGGER.warn("Invalid statistic type in {}: Don't know what {} is", this.file, â˜ƒ)
                        );
                     }
                  }
               }

               LOGGER.error("Unable to parse Stat data from {}", this.file);
            } catch (Throwable var10) {
               try {
                  â˜ƒ.close();
               } catch (Throwable var9) {
                  var10.addSuppressed(var9);
               }

               throw var10;
            }

            â˜ƒ.close();
            return;
         }

         â˜ƒ.close();
      } catch (IOException | JsonParseException var11) {
         LOGGER.error("Unable to parse Stat data from {}", this.file, var11);
      }
   }

   private <T> Optional<Stat<T>> getStat(StatType<T> var1, String var2) {
      return Optional.ofNullable(ResourceLocation.tryParse(â˜ƒ)).flatMap(â˜ƒ.getRegistry()::getOptional).map(â˜ƒ::get);
   }

   private static CompoundTag fromJson(JsonObject var0) {
      CompoundTag â˜ƒ = new CompoundTag();

      for(Entry<String, JsonElement> â˜ƒx : â˜ƒ.entrySet()) {
         JsonElement â˜ƒxx = (JsonElement)â˜ƒx.getValue();
         if (â˜ƒxx.isJsonObject()) {
            â˜ƒ.put((String)â˜ƒx.getKey(), fromJson(â˜ƒxx.getAsJsonObject()));
         } else if (â˜ƒxx.isJsonPrimitive()) {
            JsonPrimitive â˜ƒxx = â˜ƒxx.getAsJsonPrimitive();
            if (â˜ƒxx.isNumber()) {
               â˜ƒ.putInt((String)â˜ƒx.getKey(), â˜ƒxx.getAsInt());
            }
         }
      }

      return â˜ƒ;
   }

   protected String toJson() {
      Map<StatType<?>, JsonObject> â˜ƒ = Maps.<StatType<?>, JsonObject>newHashMap();

      for(it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<Stat<?>> â˜ƒx : this.stats.object2IntEntrySet()) {
         Stat<?> â˜ƒxx = (Stat)â˜ƒx.getKey();
         ((JsonObject)â˜ƒ.computeIfAbsent(â˜ƒxx.getType(), var0 -> new JsonObject())).addProperty(getKey(â˜ƒxx).toString(), â˜ƒx.getIntValue());
      }

      JsonObject â˜ƒx = new JsonObject();

      for(Entry<StatType<?>, JsonObject> â˜ƒxx : â˜ƒ.entrySet()) {
         â˜ƒx.add(Registry.STAT_TYPE.getKey((StatType<?>)â˜ƒxx.getKey()).toString(), (JsonElement)â˜ƒxx.getValue());
      }

      JsonObject â˜ƒxx = new JsonObject();
      â˜ƒxx.add("stats", â˜ƒx);
      â˜ƒxx.addProperty("DataVersion", SharedConstants.getCurrentVersion().getWorldVersion());
      return â˜ƒxx.toString();
   }

   private static <T> ResourceLocation getKey(Stat<T> var0) {
      return â˜ƒ.getType().getRegistry().getKey(â˜ƒ.getValue());
   }

   public void markAllDirty() {
      this.dirty.addAll(this.stats.keySet());
   }

   public void sendStats(ServerPlayer var1) {
      Object2IntMap<Stat<?>> â˜ƒ = new Object2IntOpenHashMap<>();

      for(Stat<?> â˜ƒx : this.getDirty()) {
         â˜ƒ.put(â˜ƒx, this.getValue(â˜ƒx));
      }

      â˜ƒ.connection.send(new ClientboundAwardStatsPacket(â˜ƒ));
   }
}
