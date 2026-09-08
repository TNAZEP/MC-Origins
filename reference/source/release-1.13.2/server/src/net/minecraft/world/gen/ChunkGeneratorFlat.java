package net.minecraft.world.gen;

import com.google.common.collect.Lists;
import java.util.BitSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.PhantomSpawner;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.WorldCarverWrapper;
import net.minecraft.world.gen.feature.CompositeFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.surfacebuilders.CompositeSurfaceBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkGeneratorFlat extends AbstractChunkGenerator<FlatGenSettings> {
   private static final Logger field_202101_d = LogManager.getLogger();
   private final FlatGenSettings field_82699_e;
   private final Biome field_202103_f;
   private final PhantomSpawner field_203229_i = new PhantomSpawner();

   public ChunkGeneratorFlat(IWorld var1, BiomeProvider var2, FlatGenSettings var3) {
      super(☃, ☃);
      this.field_82699_e = ☃;
      this.field_202103_f = this.func_202099_e();
   }

   private Biome func_202099_e() {
      Biome ☃ = this.field_82699_e.func_82648_a();
      ChunkGeneratorFlat.BiomeWrapper ☃x = new ChunkGeneratorFlat.BiomeWrapper(
         ☃.func_205401_q(),
         ☃.func_201851_b(),
         ☃.func_201856_r(),
         ☃.func_185355_j(),
         ☃.func_185360_m(),
         ☃.func_185353_n(),
         ☃.func_76727_i(),
         ☃.func_185361_o(),
         ☃.func_204274_p(),
         ☃.func_205402_s()
      );
      Map<String, Map<String, String>> ☃xx = this.field_82699_e.func_82644_b();

      for(String ☃xxx : ☃xx.keySet()) {
         CompositeFeature<?, ?>[] ☃xxxx = (CompositeFeature[])FlatGenSettings.field_202247_j.get(☃xxx);
         if (☃xxxx != null) {
            for(CompositeFeature<?, ?> ☃xxxxx : ☃xxxx) {
               ☃x.func_203611_a((GenerationStage.Decoration)FlatGenSettings.field_202248_k.get(☃xxxxx), ☃xxxxx);
               Feature<?> ☃xxxxxx = ☃xxxxx.func_202349_a();
               if (☃xxxxxx instanceof Structure) {
                  IFeatureConfig ☃xxxxxxx = ☃.func_201857_b((Structure)☃xxxxxx);
                  ☃x.func_201865_a((Structure)☃xxxxxx, ☃xxxxxxx != null ? ☃xxxxxxx : (IFeatureConfig)FlatGenSettings.field_202249_l.get(☃xxxxx));
               }
            }
         }
      }

      boolean ☃xxx = (!this.field_82699_e.func_202238_o() || ☃ == Biomes.field_185440_P) && ☃xx.containsKey("decoration");
      if (☃xxx) {
         List<GenerationStage.Decoration> ☃xxxx = Lists.newArrayList();
         ☃xxxx.add(GenerationStage.Decoration.UNDERGROUND_STRUCTURES);
         ☃xxxx.add(GenerationStage.Decoration.SURFACE_STRUCTURES);

         for(GenerationStage.Decoration ☃xxxxx : GenerationStage.Decoration.values()) {
            if (!☃xxxx.contains(☃xxxxx)) {
               for(CompositeFeature<?, ?> ☃xxxxxx : ☃.func_203607_a(☃xxxxx)) {
                  ☃x.func_203611_a(☃xxxxx, ☃xxxxxx);
               }
            }
         }
      }

      return ☃x;
   }

   @Override
   public void func_202088_a(IChunk var1) {
      ChunkPos ☃ = ☃.func_76632_l();
      int ☃x = ☃.field_77276_a;
      int ☃xx = ☃.field_77275_b;
      Biome[] ☃xxx = this.field_202097_c.func_201539_b(☃x * 16, ☃xx * 16, 16, 16);
      ☃.func_201577_a(☃xxx);
      this.func_202100_a(☃x, ☃xx, ☃);
      ☃.func_201588_a(Heightmap.Type.WORLD_SURFACE_WG, Heightmap.Type.OCEAN_FLOOR_WG);
      ☃.func_201574_a(ChunkStatus.BASE);
   }

   @Override
   public void func_202091_a(WorldGenRegion var1, GenerationStage.Carving var2) {
      int ☃ = 8;
      int ☃x = ☃.func_201679_a();
      int ☃xx = ☃.func_201680_b();
      BitSet ☃xxx = new BitSet(65536);
      SharedSeedRandom ☃xxxx = new SharedSeedRandom();

      for(int ☃xxxxx = ☃x - 8; ☃xxxxx <= ☃x + 8; ++☃xxxxx) {
         for(int ☃xxxxxx = ☃xx - 8; ☃xxxxxx <= ☃xx + 8; ++☃xxxxxx) {
            List<WorldCarverWrapper<?>> ☃xxxxxxx = this.field_202103_f.func_203603_a(GenerationStage.Carving.AIR);
            ListIterator<WorldCarverWrapper<?>> ☃xxxxxxxx = ☃xxxxxxx.listIterator();

            while(☃xxxxxxxx.hasNext()) {
               int ☃xxxxxxxxx = ☃xxxxxxxx.nextIndex();
               WorldCarverWrapper<?> ☃xxxxxxxxxx = (WorldCarverWrapper)☃xxxxxxxx.next();
               ☃xxxx.func_202425_c(☃.func_201672_e().func_72905_C() + (long)☃xxxxxxxxx, ☃xxxxx, ☃xxxxxx);
               if (☃xxxxxxxxxx.func_212246_a(☃, ☃xxxx, ☃xxxxx, ☃xxxxxx, IFeatureConfig.field_202429_e)) {
                  ☃xxxxxxxxxx.func_202522_a(☃, ☃xxxx, ☃xxxxx, ☃xxxxxx, ☃x, ☃xx, ☃xxx, IFeatureConfig.field_202429_e);
               }
            }
         }
      }
   }

   public FlatGenSettings func_201496_a_() {
      return this.field_82699_e;
   }

   @Override
   public double[] func_205473_a(int var1, int var2) {
      return new double[0];
   }

   @Override
   public int func_205470_d() {
      IChunk ☃ = this.field_202095_a.func_72964_e(0, 0);
      return ☃.func_201576_a(Heightmap.Type.MOTION_BLOCKING, 8, 8);
   }

   @Override
   public void func_202092_b(WorldGenRegion var1) {
      int ☃ = ☃.func_201679_a();
      int ☃x = ☃.func_201680_b();
      int ☃xx = ☃ * 16;
      int ☃xxx = ☃x * 16;
      BlockPos ☃xxxx = new BlockPos(☃xx, 0, ☃xxx);
      SharedSeedRandom ☃xxxxx = new SharedSeedRandom();
      long ☃xxxxxx = ☃xxxxx.func_202424_a(☃.func_72905_C(), ☃xx, ☃xxx);

      for(GenerationStage.Decoration ☃xxxxxxx : GenerationStage.Decoration.values()) {
         this.field_202103_f.func_203608_a(☃xxxxxxx, this, ☃, ☃xxxxxx, ☃xxxxx, ☃xxxx);
      }
   }

   @Override
   public void func_202093_c(WorldGenRegion var1) {
   }

   public void func_202100_a(int var1, int var2, IChunk var3) {
      IBlockState[] ☃ = this.field_82699_e.func_202233_q();
      BlockPos.MutableBlockPos ☃x = new BlockPos.MutableBlockPos();

      for(int ☃xx = 0; ☃xx < ☃.length; ++☃xx) {
         IBlockState ☃xxx = ☃[☃xx];
         if (☃xxx != null) {
            for(int ☃xxxx = 0; ☃xxxx < 16; ++☃xxxx) {
               for(int ☃xxxxx = 0; ☃xxxxx < 16; ++☃xxxxx) {
                  ☃.func_177436_a(☃x.func_181079_c(☃xxxx, ☃xx, ☃xxxxx), ☃xxx, false);
               }
            }
         }
      }
   }

   @Override
   public List<Biome.SpawnListEntry> func_177458_a(EnumCreatureType var1, BlockPos var2) {
      Biome ☃ = this.field_202095_a.func_180494_b(☃);
      return ☃.func_76747_a(☃);
   }

   @Override
   public int func_203222_a(World var1, boolean var2, boolean var3) {
      int ☃ = 0;
      return ☃ + this.field_203229_i.func_203232_a(☃, ☃, ☃);
   }

   @Override
   public boolean func_202094_a(Biome var1, Structure<? extends IFeatureConfig> var2) {
      return this.field_202103_f.func_201858_a(☃);
   }

   @Nullable
   @Override
   public IFeatureConfig func_202087_b(Biome var1, Structure<? extends IFeatureConfig> var2) {
      return this.field_202103_f.func_201857_b(☃);
   }

   @Nullable
   @Override
   public BlockPos func_211403_a(World var1, String var2, BlockPos var3, int var4, boolean var5) {
      return !this.field_82699_e.func_82644_b().keySet().contains(☃) ? null : super.func_211403_a(☃, ☃, ☃, ☃, ☃);
   }

   class BiomeWrapper extends Biome {
      protected BiomeWrapper(
         CompositeSurfaceBuilder<?> var2,
         Biome.RainType var3,
         Biome.Category var4,
         float var5,
         float var6,
         float var7,
         float var8,
         int var9,
         int var10,
         @Nullable String var11
      ) {
         super(
            new Biome.BiomeBuilder()
               .func_205416_a(☃)
               .func_205415_a(☃)
               .func_205419_a(☃)
               .func_205421_a(☃)
               .func_205420_b(☃)
               .func_205414_c(☃)
               .func_205417_d(☃)
               .func_205412_a(☃)
               .func_205413_b(☃)
               .func_205418_a(☃)
         );
      }
   }
}
