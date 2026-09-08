package net.minecraft.world.chunk;

import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Fluids;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.EnumLightType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.StructureStart;

public class ChunkPrimerWrapper extends ChunkPrimer {
   private final IChunk field_209220_a;

   public ChunkPrimerWrapper(IChunk var1) {
      super(☃.func_76632_l(), UpgradeData.field_196994_a);
      this.field_209220_a = ☃;
   }

   @Nullable
   @Override
   public TileEntity func_175625_s(BlockPos var1) {
      return this.field_209220_a.func_175625_s(☃);
   }

   @Nullable
   @Override
   public IBlockState func_180495_p(BlockPos var1) {
      return this.field_209220_a.func_180495_p(☃);
   }

   @Override
   public IFluidState func_204610_c(BlockPos var1) {
      return this.field_209220_a.func_204610_c(☃);
   }

   @Override
   public int func_201572_C() {
      return this.field_209220_a.func_201572_C();
   }

   @Nullable
   @Override
   public IBlockState func_177436_a(BlockPos var1, IBlockState var2, boolean var3) {
      return null;
   }

   @Override
   public void func_177426_a(BlockPos var1, TileEntity var2) {
   }

   @Override
   public void func_76612_a(Entity var1) {
   }

   @Override
   public void func_201574_a(ChunkStatus var1) {
   }

   @Override
   public ChunkSection[] func_76587_i() {
      return this.field_209220_a.func_76587_i();
   }

   @Override
   public int func_201587_a(EnumLightType var1, BlockPos var2, boolean var3) {
      return this.field_209220_a.func_201587_a(☃, ☃, ☃);
   }

   @Override
   public int func_201586_a(BlockPos var1, int var2, boolean var3) {
      return this.field_209220_a.func_201586_a(☃, ☃, ☃);
   }

   @Override
   public boolean func_177444_d(BlockPos var1) {
      return this.field_209220_a.func_177444_d(☃);
   }

   @Override
   public void func_201643_a(Heightmap.Type var1, long[] var2) {
   }

   private Heightmap.Type func_209532_c(Heightmap.Type var1) {
      if (☃ == Heightmap.Type.WORLD_SURFACE_WG) {
         return Heightmap.Type.WORLD_SURFACE;
      } else {
         return ☃ == Heightmap.Type.OCEAN_FLOOR_WG ? Heightmap.Type.OCEAN_FLOOR : ☃;
      }
   }

   @Override
   public int func_201576_a(Heightmap.Type var1, int var2, int var3) {
      return this.field_209220_a.func_201576_a(this.func_209532_c(☃), ☃, ☃);
   }

   @Override
   public ChunkPos func_76632_l() {
      return this.field_209220_a.func_76632_l();
   }

   @Override
   public void func_177432_b(long var1) {
   }

   @Nullable
   @Override
   public StructureStart func_201585_a(String var1) {
      return this.field_209220_a.func_201585_a(☃);
   }

   @Override
   public void func_201584_a(String var1, StructureStart var2) {
   }

   @Override
   public Map<String, StructureStart> func_201609_c() {
      return this.field_209220_a.func_201609_c();
   }

   @Override
   public void func_201648_a(Map<String, StructureStart> var1) {
   }

   @Nullable
   @Override
   public LongSet func_201578_b(String var1) {
      return this.field_209220_a.func_201578_b(☃);
   }

   @Override
   public void func_201583_a(String var1, long var2) {
   }

   @Override
   public Map<String, LongSet> func_201604_d() {
      return this.field_209220_a.func_201604_d();
   }

   @Override
   public void func_201641_b(Map<String, LongSet> var1) {
   }

   @Override
   public Biome[] func_201590_e() {
      return this.field_209220_a.func_201590_e();
   }

   @Override
   public void func_177427_f(boolean var1) {
   }

   @Override
   public boolean func_201593_f() {
      return false;
   }

   @Override
   public ChunkStatus func_201589_g() {
      return this.field_209220_a.func_201589_g();
   }

   @Override
   public void func_177425_e(BlockPos var1) {
   }

   @Override
   public void func_201580_a(EnumLightType var1, boolean var2, BlockPos var3, int var4) {
      this.field_209220_a.func_201580_a(☃, ☃, ☃, ☃);
   }

   @Override
   public void func_201594_d(BlockPos var1) {
   }

   @Override
   public void func_201591_a(NBTTagCompound var1) {
   }

   @Nullable
   @Override
   public NBTTagCompound func_201579_g(BlockPos var1) {
      return this.field_209220_a.func_201579_g(☃);
   }

   @Override
   public void func_201577_a(Biome[] var1) {
   }

   @Override
   public void func_201588_a(Heightmap.Type... var1) {
   }

   @Override
   public List<BlockPos> func_201582_h() {
      return this.field_209220_a.func_201582_h();
   }

   @Override
   public ChunkPrimerTickList<Block> func_205218_i_() {
      return new ChunkPrimerTickList<>(
         var0 -> var0.func_176223_P().func_196958_f(), IRegistry.field_212618_g::func_177774_c, IRegistry.field_212618_g::func_82594_a, this.func_76632_l()
      );
   }

   @Override
   public ChunkPrimerTickList<Fluid> func_212247_j() {
      return new ChunkPrimerTickList<>(
         var0 -> var0 == Fluids.field_204541_a, IRegistry.field_212619_h::func_177774_c, IRegistry.field_212619_h::func_82594_a, this.func_76632_l()
      );
   }

   @Override
   public BitSet func_205749_a(GenerationStage.Carving var1) {
      return this.field_209220_a.func_205749_a(☃);
   }

   @Override
   public void func_207739_b(boolean var1) {
   }
}
