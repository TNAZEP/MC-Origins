package net.minecraft.stats;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.world.entity.player.Player;

public class StatsCounter {
   protected final Object2IntMap<Stat<?>> stats = Object2IntMaps.synchronize(new Object2IntOpenHashMap<>());

   public StatsCounter() {
      this.stats.defaultReturnValue(0);
   }

   public void increment(Player var1, Stat<?> var2, int var3) {
      int â˜ƒ = (int)Math.min((long)this.getValue(â˜ƒ) + (long)â˜ƒ, 2147483647L);
      this.setValue(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void setValue(Player var1, Stat<?> var2, int var3) {
      this.stats.put(â˜ƒ, â˜ƒ);
   }

   public <T> int getValue(StatType<T> var1, T var2) {
      return â˜ƒ.contains(â˜ƒ) ? this.getValue(â˜ƒ.get(â˜ƒ)) : 0;
   }

   public int getValue(Stat<?> var1) {
      return this.stats.getInt(â˜ƒ);
   }
}
