package net.minecraft.world.gen;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;

public class ChunkGeneratorDebug extends AbstractChunkGenerator<DebugGenSettings> {
   private static final List<IBlockState> field_177464_a = (List<IBlockState>)StreamSupport.stream(IRegistry.field_212618_g.spliterator(), false)
      .flatMap(var0 -> var0.func_176194_O().func_177619_a().stream())
      .collect(Collectors.toList());
   private static final int field_177462_b = MathHelper.func_76123_f(MathHelper.func_76129_c((float)field_177464_a.size()));
   private static final int field_181039_c = MathHelper.func_76123_f((float)field_177464_a.size() / (float)field_177462_b);
   protected static final IBlockState field_185934_a = Blocks.field_150350_a.func_176223_P();
   protected static final IBlockState field_185935_b = Blocks.field_180401_cv.func_176223_P();
   private final DebugGenSettings field_202098_i;

   public ChunkGeneratorDebug(IWorld var1, BiomeProvider var2, DebugGenSettings var3) {
      super(☃, ☃);
      this.field_202098_i = ☃;
   }

   @Override
   public void func_202088_a(IChunk var1) {
      ChunkPos ☃ = ☃.func_76632_l();
      int ☃x = ☃.field_77276_a;
      int ☃xx = ☃.field_77275_b;
      Biome[] ☃xxx = this.field_202097_c.func_201539_b(☃x * 16, ☃xx * 16, 16, 16);
      ☃.func_201577_a(☃xxx);
      ☃.func_201588_a(Heightmap.Type.WORLD_SURFACE_WG, Heightmap.Type.OCEAN_FLOOR_WG);
      ☃.func_201574_a(ChunkStatus.BASE);
   }

   @Override
   public void func_202091_a(WorldGenRegion var1, GenerationStage.Carving var2) {
   }

   public DebugGenSettings func_201496_a_() {
      return this.field_202098_i;
   }

   @Override
   public double[] func_205473_a(int var1, int var2) {
      return new double[0];
   }

   @Override
   public int func_205470_d() {
      return this.field_202095_a.func_181545_F() + 1;
   }

   @Override
   public void func_202092_b(WorldGenRegion var1) {
      BlockPos.MutableBlockPos ☃ = new BlockPos.MutableBlockPos();
      int ☃x = ☃.func_201679_a();
      int ☃xx = ☃.func_201680_b();

      for(int ☃xxx = 0; ☃xxx < 16; ++☃xxx) {
         for(int ☃xxxx = 0; ☃xxxx < 16; ++☃xxxx) {
            int ☃xxxxx = (☃x << 4) + ☃xxx;
            int ☃xxxxxx = (☃xx << 4) + ☃xxxx;
            ☃.func_180501_a(☃.func_181079_c(☃xxxxx, 60, ☃xxxxxx), field_185935_b, 2);
            IBlockState ☃xxxxxxx = func_177461_b(☃xxxxx, ☃xxxxxx);
            if (☃xxxxxxx != null) {
               ☃.func_180501_a(☃.func_181079_c(☃xxxxx, 70, ☃xxxxxx), ☃xxxxxxx, 2);
            }
         }
      }
   }

   @Override
   public void func_202093_c(WorldGenRegion var1) {
   }

   public static IBlockState func_177461_b(int var0, int var1) {
      IBlockState ☃ = field_185934_a;
      if (☃ > 0 && ☃ > 0 && ☃ % 2 != 0 && ☃ % 2 != 0) {
         ☃ /= 2;
         ☃ /= 2;
         if (☃ <= field_177462_b && ☃ <= field_181039_c) {
            int ☃x = MathHelper.func_76130_a(☃ * field_177462_b + ☃);
            if (☃x < field_177464_a.size()) {
               ☃ = (IBlockState)field_177464_a.get(☃x);
            }
         }
      }

      return ☃;
   }

   @Override
   public List<Biome.SpawnListEntry> func_177458_a(EnumCreatureType var1, BlockPos var2) {
      Biome ☃ = this.field_202095_a.func_180494_b(☃);
      return ☃.func_76747_a(☃);
   }

   @Override
   public int func_203222_a(World var1, boolean var2, boolean var3) {
      return 0;
   }
}
