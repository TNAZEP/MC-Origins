package net.minecraft.world.gen.carver;

import java.util.BitSet;
import java.util.Random;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class WorldCarverWrapper<C extends IFeatureConfig> implements IWorldCarver<NoFeatureConfig> {
   private final IWorldCarver<C> field_203623_a;
   private final C field_203624_b;

   public WorldCarverWrapper(IWorldCarver<C> var1, C var2) {
      this.field_203623_a = ☃;
      this.field_203624_b = ☃;
   }

   public boolean func_212246_a(IBlockReader var1, Random var2, int var3, int var4, NoFeatureConfig var5) {
      return this.field_203623_a.func_212246_a(☃, ☃, ☃, ☃, this.field_203624_b);
   }

   public boolean func_202522_a(IWorld var1, Random var2, int var3, int var4, int var5, int var6, BitSet var7, NoFeatureConfig var8) {
      return this.field_203623_a.func_202522_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, this.field_203624_b);
   }
}
