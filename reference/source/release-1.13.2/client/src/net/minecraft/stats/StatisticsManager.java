package net.minecraft.stats;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.entity.player.EntityPlayer;

public class StatisticsManager {
   protected final Object2IntMap<Stat<?>> field_150875_a = Object2IntMaps.synchronize(new Object2IntOpenHashMap<>());

   public StatisticsManager() {
      this.field_150875_a.defaultReturnValue(0);
   }

   public void func_150871_b(EntityPlayer var1, Stat<?> var2, int var3) {
      this.func_150873_a(☃, ☃, this.func_77444_a(☃) + ☃);
   }

   public void func_150873_a(EntityPlayer var1, Stat<?> var2, int var3) {
      this.field_150875_a.put(☃, ☃);
   }

   public <T> int func_199060_a(StatType<T> var1, T var2) {
      return ☃.func_199079_a(☃) ? this.func_77444_a(☃.func_199076_b(☃)) : 0;
   }

   public int func_77444_a(Stat<?> var1) {
      return this.field_150875_a.getInt(☃);
   }
}
