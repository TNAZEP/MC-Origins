package net.minecraft.world.level.timers;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TimerCallbacks<C> {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final TimerCallbacks<MinecraftServer> SERVER_CALLBACKS = new TimerCallbacks<MinecraftServer>()
      .register(new FunctionCallback.Serializer())
      .register(new FunctionTagCallback.Serializer());
   private final Map<ResourceLocation, TimerCallback.Serializer<C, ?>> idToSerializer = Maps.<ResourceLocation, TimerCallback.Serializer<C, ?>>newHashMap();
   private final Map<Class<?>, TimerCallback.Serializer<C, ?>> classToSerializer = Maps.newHashMap();

   public TimerCallbacks<C> register(TimerCallback.Serializer<C, ?> var1) {
      this.idToSerializer.put(â˜ƒ.getId(), â˜ƒ);
      this.classToSerializer.put(â˜ƒ.getCls(), â˜ƒ);
      return this;
   }

   private <T extends TimerCallback<C>> TimerCallback.Serializer<C, T> getSerializer(Class<?> var1) {
      return (TimerCallback.Serializer<C, T>)this.classToSerializer.get(â˜ƒ);
   }

   public <T extends TimerCallback<C>> CompoundTag serialize(T var1) {
      TimerCallback.Serializer<C, T> â˜ƒ = this.getSerializer(â˜ƒ.getClass());
      CompoundTag â˜ƒx = new CompoundTag();
      â˜ƒ.serialize(â˜ƒx, â˜ƒ);
      â˜ƒx.putString("Type", â˜ƒ.getId().toString());
      return â˜ƒx;
   }

   @Nullable
   public TimerCallback<C> deserialize(CompoundTag var1) {
      ResourceLocation â˜ƒ = ResourceLocation.tryParse(â˜ƒ.getString("Type"));
      TimerCallback.Serializer<C, ?> â˜ƒx = (TimerCallback.Serializer)this.idToSerializer.get(â˜ƒ);
      if (â˜ƒx == null) {
         LOGGER.error("Failed to deserialize timer callback: {}", â˜ƒ);
         return null;
      } else {
         try {
            return â˜ƒx.deserialize(â˜ƒ);
         } catch (Exception var5) {
            LOGGER.error("Failed to deserialize timer callback: {}", â˜ƒ, var5);
            return null;
         }
      }
   }
}
