package net.minecraft.world.chunk;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumLightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class EmptyChunk extends Chunk {
   private static final Biome[] field_201623_e = Util.func_200696_a(new Biome[256], var0 -> Arrays.fill(var0, Biomes.field_76772_c));

   public EmptyChunk(World var1, int var2, int var3) {
      super(☃, ☃, ☃, field_201623_e);
   }

   @Override
   public boolean func_76600_a(int var1, int var2) {
      return ☃ == this.field_76635_g && ☃ == this.field_76647_h;
   }

   @Override
   public void func_76590_a() {
   }

   @Override
   public void func_76603_b() {
   }

   @Override
   public IBlockState func_180495_p(BlockPos var1) {
      return Blocks.field_201940_ji.func_176223_P();
   }

   @Override
   public int func_201587_a(EnumLightType var1, BlockPos var2, boolean var3) {
      return ☃.field_77198_c;
   }

   @Override
   public void func_201580_a(EnumLightType var1, boolean var2, BlockPos var3, int var4) {
   }

   @Override
   public int func_201586_a(BlockPos var1, int var2, boolean var3) {
      return 0;
   }

   @Override
   public void func_76612_a(Entity var1) {
   }

   @Override
   public void func_76622_b(Entity var1) {
   }

   @Override
   public void func_76608_a(Entity var1, int var2) {
   }

   @Override
   public boolean func_177444_d(BlockPos var1) {
      return false;
   }

   @Nullable
   @Override
   public TileEntity func_177424_a(BlockPos var1, Chunk.EnumCreateEntityType var2) {
      return null;
   }

   @Override
   public void func_150813_a(TileEntity var1) {
   }

   @Override
   public void func_177426_a(BlockPos var1, TileEntity var2) {
   }

   @Override
   public void func_177425_e(BlockPos var1) {
   }

   @Override
   public void func_76631_c() {
   }

   @Override
   public void func_76623_d() {
   }

   @Override
   public void func_76630_e() {
   }

   @Override
   public void func_177414_a(@Nullable Entity var1, AxisAlignedBB var2, List<Entity> var3, Predicate<? super Entity> var4) {
   }

   @Override
   public <T extends Entity> void func_177430_a(Class<? extends T> var1, AxisAlignedBB var2, List<T> var3, Predicate<? super T> var4) {
   }

   @Override
   public boolean func_76601_a(boolean var1) {
      return false;
   }

   @Override
   public boolean func_76621_g() {
      return true;
   }

   @Override
   public boolean func_76606_c(int var1, int var2) {
      return true;
   }
}
