package net.minecraft.world.entity.ai.memory;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Optional;
import net.minecraft.util.VisibleForDebug;

public class ExpirableValue<T> {
   private final T value;
   private long timeToLive;

   public ExpirableValue(T var1, long var2) {
      this.value = â˜ƒ;
      this.timeToLive = â˜ƒ;
   }

   public void tick() {
      if (this.canExpire()) {
         --this.timeToLive;
      }
   }

   public static <T> ExpirableValue<T> of(T var0) {
      return new ExpirableValue<>(â˜ƒ, Long.MAX_VALUE);
   }

   public static <T> ExpirableValue<T> of(T var0, long var1) {
      return new ExpirableValue<>(â˜ƒ, â˜ƒ);
   }

   public long getTimeToLive() {
      return this.timeToLive;
   }

   public T getValue() {
      return this.value;
   }

   public boolean hasExpired() {
      return this.timeToLive <= 0L;
   }

   public String toString() {
      return this.value + (this.canExpire() ? " (ttl: " + this.timeToLive + ")" : "");
   }

   @VisibleForDebug
   public boolean canExpire() {
      return this.timeToLive != Long.MAX_VALUE;
   }

   public static <T> Codec<ExpirableValue<T>> codec(Codec<T> var0) {
      return RecordCodecBuilder.create(
         var1 -> var1.group(
                  â˜ƒ.fieldOf("value").forGetter(var0x -> var0x.value),
                  Codec.LONG.optionalFieldOf("ttl").forGetter(var0x -> var0x.canExpire() ? Optional.of(var0x.timeToLive) : Optional.empty())
               )
               .apply(var1, (var0x, var1x) -> new ExpirableValue<>(var0x, var1x.orElse(Long.MAX_VALUE)))
      );
   }
}
