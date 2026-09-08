package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.UpgradeData;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class WoodlandMansionStructure extends Structure<WoodlandMansionConfig> {
   @Override
   protected ChunkPos func_211744_a(IChunkGenerator<?> var1, Random var2, int var3, int var4, int var5, int var6) {
      int ☃ = ☃.func_201496_a_().func_202179_i();
      int ☃x = ☃.func_201496_a_().func_211726_q();
      int ☃xx = ☃ + ☃ * ☃;
      int ☃xxx = ☃ + ☃ * ☃;
      int ☃xxxx = ☃xx < 0 ? ☃xx - ☃ + 1 : ☃xx;
      int ☃xxxxx = ☃xxx < 0 ? ☃xxx - ☃ + 1 : ☃xxx;
      int ☃xxxxxx = ☃xxxx / ☃;
      int ☃xxxxxxx = ☃xxxxx / ☃;
      ((SharedSeedRandom)☃).func_202427_a(☃.func_202089_c(), ☃xxxxxx, ☃xxxxxxx, 10387319);
      ☃xxxxxx *= ☃;
      ☃xxxxxxx *= ☃;
      ☃xxxxxx += (☃.nextInt(☃ - ☃x) + ☃.nextInt(☃ - ☃x)) / 2;
      ☃xxxxxxx += (☃.nextInt(☃ - ☃x) + ☃.nextInt(☃ - ☃x)) / 2;
      return new ChunkPos(☃xxxxxx, ☃xxxxxxx);
   }

   @Override
   protected boolean func_202372_a(IChunkGenerator<?> var1, Random var2, int var3, int var4) {
      ChunkPos ☃ = this.func_211744_a(☃, ☃, ☃, ☃, 0, 0);
      if (☃ == ☃.field_77276_a && ☃ == ☃.field_77275_b) {
         for(Biome ☃x : ☃.func_202090_b().func_201538_a(☃ * 16 + 9, ☃ * 16 + 9, 32)) {
            if (!☃.func_202094_a(☃x, Feature.field_202330_h)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   protected boolean func_202365_a(IWorld var1) {
      return ☃.func_72912_H().func_76089_r();
   }

   @Override
   protected StructureStart func_202369_a(IWorld var1, IChunkGenerator<?> var2, SharedSeedRandom var3, int var4, int var5) {
      Biome ☃ = ☃.func_202090_b().func_180300_a(new BlockPos((☃ << 4) + 9, 0, (☃ << 4) + 9), Biomes.field_180279_ad);
      return new WoodlandMansionStructure.Start(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   protected String func_143025_a() {
      return "Mansion";
   }

   @Override
   public int func_202367_b() {
      return 8;
   }

   public static class Start extends StructureStart {
      private boolean field_191093_c;

      public Start() {
      }

      public Start(IWorld var1, IChunkGenerator<?> var2, SharedSeedRandom var3, int var4, int var5, Biome var6) {
         super(☃, ☃, ☃, ☃, ☃.func_72905_C());
         Rotation ☃ = Rotation.values()[☃.nextInt(Rotation.values().length)];
         int ☃x = 5;
         int ☃xx = 5;
         if (☃ == Rotation.CLOCKWISE_90) {
            ☃x = -5;
         } else if (☃ == Rotation.CLOCKWISE_180) {
            ☃x = -5;
            ☃xx = -5;
         } else if (☃ == Rotation.COUNTERCLOCKWISE_90) {
            ☃xx = -5;
         }

         ChunkPrimer ☃ = new ChunkPrimer(new ChunkPos(☃, ☃), UpgradeData.field_196994_a);
         ☃.func_202088_a(☃);
         int ☃x = ☃.func_201576_a(Heightmap.Type.MOTION_BLOCKING, 7, 7);
         int ☃xx = ☃.func_201576_a(Heightmap.Type.MOTION_BLOCKING, 7, 7 + ☃xx);
         int ☃xxx = ☃.func_201576_a(Heightmap.Type.MOTION_BLOCKING, 7 + ☃x, 7);
         int ☃xxxx = ☃.func_201576_a(Heightmap.Type.MOTION_BLOCKING, 7 + ☃x, 7 + ☃xx);
         int ☃xxxxx = Math.min(Math.min(☃x, ☃xx), Math.min(☃xxx, ☃xxxx));
         if (☃xxxxx < 60) {
            this.field_191093_c = false;
         } else {
            BlockPos ☃ = new BlockPos(☃ * 16 + 8, ☃xxxxx + 1, ☃ * 16 + 8);
            List<WoodlandMansionPieces.MansionTemplate> ☃x = Lists.<WoodlandMansionPieces.MansionTemplate>newLinkedList();
            WoodlandMansionPieces.func_191152_a(☃.func_72860_G().func_186340_h(), ☃, ☃, ☃x, ☃);
            this.field_75075_a.addAll(☃x);
            this.func_202500_a(☃);
            this.field_191093_c = true;
         }
      }

      @Override
      public void func_75068_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         super.func_75068_a(☃, ☃, ☃, ☃);
         int ☃ = this.field_75074_b.field_78895_b;

         for(int ☃x = ☃.field_78897_a; ☃x <= ☃.field_78893_d; ++☃x) {
            for(int ☃xx = ☃.field_78896_c; ☃xx <= ☃.field_78892_f; ++☃xx) {
               BlockPos ☃xxx = new BlockPos(☃x, ☃, ☃xx);
               if (!☃.func_175623_d(☃xxx) && this.field_75074_b.func_175898_b(☃xxx)) {
                  boolean ☃xxxx = false;

                  for(StructurePiece ☃xxxxx : this.field_75075_a) {
                     if (☃xxxxx.func_74874_b().func_175898_b(☃xxx)) {
                        ☃xxxx = true;
                        break;
                     }
                  }

                  if (☃xxxx) {
                     for(int ☃xxxxx = ☃ - 1; ☃xxxxx > 1; --☃xxxxx) {
                        BlockPos ☃xxxxxx = new BlockPos(☃x, ☃xxxxx, ☃xx);
                        if (!☃.func_175623_d(☃xxxxxx) && !☃.func_180495_p(☃xxxxxx).func_185904_a().func_76224_d()) {
                           break;
                        }

                        ☃.func_180501_a(☃xxxxxx, Blocks.field_150347_e.func_176223_P(), 2);
                     }
                  }
               }
            }
         }
      }

      @Override
      public boolean func_75069_d() {
         return this.field_191093_c;
      }
   }
}
