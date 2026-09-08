package net.minecraft.world;

import java.util.function.Function;
import net.minecraft.util.math.BlockPos;

public class WorldGenTickList<T> implements ITickList<T> {
   private final Function<BlockPos, ITickList<T>> field_205387_a;

   public WorldGenTickList(Function<BlockPos, ITickList<T>> var1) {
      this.field_205387_a = ☃;
   }

   @Override
   public boolean func_205359_a(BlockPos var1, T var2) {
      return ((ITickList)this.field_205387_a.apply(☃)).func_205359_a(☃, ☃);
   }

   @Override
   public void func_205362_a(BlockPos var1, T var2, int var3, TickPriority var4) {
      ((ITickList)this.field_205387_a.apply(☃)).func_205362_a(☃, ☃, ☃, ☃);
   }

   @Override
   public boolean func_205361_b(BlockPos var1, T var2) {
      return false;
   }
}
