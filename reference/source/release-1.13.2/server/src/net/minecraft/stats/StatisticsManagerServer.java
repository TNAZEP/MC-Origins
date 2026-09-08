package net.minecraft.stats;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.mojang.datafixers.DataFixTypes;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.play.server.SPacketStatistics;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StatisticsManagerServer extends StatisticsManager {
   private static final Logger field_150889_b = LogManager.getLogger();
   private final MinecraftServer field_150890_c;
   private final File field_150887_d;
   private final Set<Stat<?>> field_150888_e = Sets.<Stat<?>>newHashSet();
   private int field_150885_f = -300;

   public StatisticsManagerServer(MinecraftServer var1, File var2) {
      this.field_150890_c = ☃;
      this.field_150887_d = ☃;
      if (☃.isFile()) {
         try {
            this.func_199062_a(☃.func_195563_aC(), FileUtils.readFileToString(☃));
         } catch (IOException var4) {
            field_150889_b.error("Couldn't read statistics file {}", ☃, var4);
         } catch (JsonParseException var5) {
            field_150889_b.error("Couldn't parse statistics file {}", ☃, var5);
         }
      }
   }

   public void func_150883_b() {
      try {
         FileUtils.writeStringToFile(this.field_150887_d, this.func_199061_b());
      } catch (IOException var2) {
         field_150889_b.error("Couldn't save stats", var2);
      }
   }

   @Override
   public void func_150873_a(EntityPlayer var1, Stat<?> var2, int var3) {
      super.func_150873_a(☃, ☃, ☃);
      this.field_150888_e.add(☃);
   }

   private Set<Stat<?>> func_150878_c() {
      Set<Stat<?>> ☃ = Sets.<Stat<?>>newHashSet(this.field_150888_e);
      this.field_150888_e.clear();
      return ☃;
   }

   public void func_199062_a(DataFixer var1, String var2) {
      try {
         JsonReader ☃ = new JsonReader(new StringReader(☃));
         Throwable var4 = null;

         try {
            ☃.setLenient(false);
            JsonElement ☃x = Streams.parse(☃);
            if (!☃x.isJsonNull()) {
               NBTTagCompound ☃xx = func_199065_a(☃x.getAsJsonObject());
               if (!☃xx.func_150297_b("DataVersion", 99)) {
                  ☃xx.func_74768_a("DataVersion", 1343);
               }

               ☃xx = NBTUtil.func_210822_a(☃, DataFixTypes.STATS, ☃xx, ☃xx.func_74762_e("DataVersion"));
               if (☃xx.func_150297_b("stats", 10)) {
                  NBTTagCompound ☃xx = ☃xx.func_74775_l("stats");

                  for(String ☃xxx : ☃xx.func_150296_c()) {
                     if (☃xx.func_150297_b(☃xxx, 10)) {
                        StatType<?> ☃xxxx = IRegistry.field_212634_w.func_212608_b(new ResourceLocation(☃xxx));
                        if (☃xxxx == null) {
                           field_150889_b.warn("Invalid statistic type in {}: Don't know what {} is", this.field_150887_d, ☃xxx);
                        } else {
                           NBTTagCompound ☃xxxx = ☃xx.func_74775_l(☃xxx);

                           for(String ☃xxxxx : ☃xxxx.func_150296_c()) {
                              if (☃xxxx.func_150297_b(☃xxxxx, 99)) {
                                 Stat<?> ☃xxxxxx = this.func_199063_a(☃xxxx, ☃xxxxx);
                                 if (☃xxxxxx == null) {
                                    field_150889_b.warn("Invalid statistic in {}: Don't know what {} is", this.field_150887_d, ☃xxxxx);
                                 } else {
                                    this.field_150875_a.put(☃xxxxxx, ☃xxxx.func_74762_e(☃xxxxx));
                                 }
                              } else {
                                 field_150889_b.warn(
                                    "Invalid statistic value in {}: Don't know what {} is for key {}", this.field_150887_d, ☃xxxx.func_74781_a(☃xxxxx), ☃xxxxx
                                 );
                              }
                           }
                        }
                     }
                  }
               }
            } else {
               field_150889_b.error("Unable to parse Stat data from {}", this.field_150887_d);
            }
         } catch (Throwable var23) {
            var4 = var23;
            throw var23;
         } finally {
            if (☃ != null) {
               if (var4 != null) {
                  try {
                     ☃.close();
                  } catch (Throwable var22) {
                     var4.addSuppressed(var22);
                  }
               } else {
                  ☃.close();
               }
            }
         }
      } catch (IOException | JsonParseException var25) {
         field_150889_b.error("Unable to parse Stat data from {}", this.field_150887_d, var25);
      }
   }

   @Nullable
   private <T> Stat<T> func_199063_a(StatType<T> var1, String var2) {
      ResourceLocation ☃ = ResourceLocation.func_208304_a(☃);
      if (☃ == null) {
         return null;
      } else {
         T ☃ = ☃.func_199080_a().func_212608_b(☃);
         return ☃ == null ? null : ☃.func_199076_b(☃);
      }
   }

   private static NBTTagCompound func_199065_a(JsonObject var0) {
      NBTTagCompound ☃ = new NBTTagCompound();

      for(Entry<String, JsonElement> ☃x : ☃.entrySet()) {
         JsonElement ☃xx = (JsonElement)☃x.getValue();
         if (☃xx.isJsonObject()) {
            ☃.func_74782_a((String)☃x.getKey(), func_199065_a(☃xx.getAsJsonObject()));
         } else if (☃xx.isJsonPrimitive()) {
            JsonPrimitive ☃xx = ☃xx.getAsJsonPrimitive();
            if (☃xx.isNumber()) {
               ☃.func_74768_a((String)☃x.getKey(), ☃xx.getAsInt());
            }
         }
      }

      return ☃;
   }

   protected String func_199061_b() {
      Map<StatType<?>, JsonObject> ☃ = Maps.<StatType<?>, JsonObject>newHashMap();

      for(it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<Stat<?>> ☃x : this.field_150875_a.object2IntEntrySet()) {
         Stat<?> ☃xx = (Stat)☃x.getKey();
         ((JsonObject)☃.computeIfAbsent(☃xx.func_197921_a(), var0 -> new JsonObject())).addProperty(func_199066_b(☃xx).toString(), ☃x.getIntValue());
      }

      JsonObject ☃x = new JsonObject();

      for(Entry<StatType<?>, JsonObject> ☃xx : ☃.entrySet()) {
         ☃x.add(IRegistry.field_212634_w.func_177774_c((StatType<?>)☃xx.getKey()).toString(), (JsonElement)☃xx.getValue());
      }

      JsonObject ☃xx = new JsonObject();
      ☃xx.add("stats", ☃x);
      ☃xx.addProperty("DataVersion", 1631);
      return ☃xx.toString();
   }

   private static <T> ResourceLocation func_199066_b(Stat<T> var0) {
      return ☃.func_197921_a().func_199080_a().func_177774_c(☃.func_197920_b());
   }

   public void func_150877_d() {
      this.field_150888_e.addAll(this.field_150875_a.keySet());
   }

   public void func_150876_a(EntityPlayerMP var1) {
      int ☃ = this.field_150890_c.func_71259_af();
      Object2IntMap<Stat<?>> ☃x = new Object2IntOpenHashMap<>();
      if (☃ - this.field_150885_f > 300) {
         this.field_150885_f = ☃;

         for(Stat<?> ☃xx : this.func_150878_c()) {
            ☃x.put(☃xx, this.func_77444_a(☃xx));
         }
      }

      ☃.field_71135_a.func_147359_a(new SPacketStatistics(☃x));
   }
}
