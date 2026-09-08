package net.minecraft.world.gen.feature.structure;

import java.util.List;
import java.util.Random;
import net.minecraft.init.Biomes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class VillageStructure extends Structure<VillageConfig> {
   @Override
   public String func_143025_a() {
      return "Village";
   }

   @Override
   public int func_202367_b() {
      return 8;
   }

   @Override
   protected boolean func_202365_a(IWorld var1) {
      return ☃.func_72912_H().func_76089_r();
   }

   @Override
   protected ChunkPos func_211744_a(IChunkGenerator<?> var1, Random var2, int var3, int var4, int var5, int var6) {
      int ☃ = ☃.func_201496_a_().func_202173_a();
      int ☃x = ☃.func_201496_a_().func_211729_b();
      int ☃xx = ☃ + ☃ * ☃;
      int ☃xxx = ☃ + ☃ * ☃;
      int ☃xxxx = ☃xx < 0 ? ☃xx - ☃ + 1 : ☃xx;
      int ☃xxxxx = ☃xxx < 0 ? ☃xxx - ☃ + 1 : ☃xxx;
      int ☃xxxxxx = ☃xxxx / ☃;
      int ☃xxxxxxx = ☃xxxxx / ☃;
      ((SharedSeedRandom)☃).func_202427_a(☃.func_202089_c(), ☃xxxxxx, ☃xxxxxxx, 10387312);
      ☃xxxxxx *= ☃;
      ☃xxxxxxx *= ☃;
      ☃xxxxxx += ☃.nextInt(☃ - ☃x);
      ☃xxxxxxx += ☃.nextInt(☃ - ☃x);
      return new ChunkPos(☃xxxxxx, ☃xxxxxxx);
   }

   @Override
   protected boolean func_202372_a(IChunkGenerator<?> var1, Random var2, int var3, int var4) {
      ChunkPos ☃ = this.func_211744_a(☃, ☃, ☃, ☃, 0, 0);
      if (☃ == ☃.field_77276_a && ☃ == ☃.field_77275_b) {
         Biome ☃x = ☃.func_202090_b().func_180300_a(new BlockPos((☃ << 4) + 9, 0, (☃ << 4) + 9), Biomes.field_180279_ad);
         return ☃.func_202094_a(☃x, Feature.field_202328_f);
      } else {
         return false;
      }
   }

   @Override
   protected StructureStart func_202369_a(IWorld var1, IChunkGenerator<?> var2, SharedSeedRandom var3, int var4, int var5) {
      Biome ☃ = ☃.func_202090_b().func_180300_a(new BlockPos((☃ << 4) + 9, 0, (☃ << 4) + 9), Biomes.field_180279_ad);
      return new VillageStructure.Start(☃, ☃, ☃, ☃, ☃, ☃);
   }

   public static class Start extends StructureStart {
      private boolean field_75076_c;

      public Start() {
      }

      public Start(IWorld var1, IChunkGenerator<?> var2, SharedSeedRandom var3, int var4, int var5, Biome var6) {
         super(☃, ☃, ☃, ☃, ☃.func_72905_C());
         VillageConfig ☃ = (VillageConfig)☃.func_202087_b(☃, Feature.field_202328_f);
         List<VillagePieces.PieceWeight> ☃x = VillagePieces.func_75084_a(☃, ☃.field_202461_a);
         VillagePieces.Start ☃xx = new VillagePieces.Start(0, ☃, (☃ << 4) + 2, (☃ << 4) + 2, ☃x, ☃);
         this.field_75075_a.add(☃xx);
         ☃xx.func_74861_a(☃xx, this.field_75075_a, ☃);
         List<StructurePiece> ☃xxx = ☃xx.field_74930_j;
         List<StructurePiece> ☃xxxx = ☃xx.field_74932_i;

         while(!☃xxx.isEmpty() || !☃xxxx.isEmpty()) {
            if (☃xxx.isEmpty()) {
               int ☃xxxxx = ☃.nextInt(☃xxxx.size());
               StructurePiece ☃xxxxxx = (StructurePiece)☃xxxx.remove(☃xxxxx);
               ☃xxxxxx.func_74861_a(☃xx, this.field_75075_a, ☃);
            } else {
               int ☃xxxxx = ☃.nextInt(☃xxx.size());
               StructurePiece ☃xxxxxx = (StructurePiece)☃xxx.remove(☃xxxxx);
               ☃xxxxxx.func_74861_a(☃xx, this.field_75075_a, ☃);
            }
         }

         this.func_202500_a(☃);
         int ☃xxxxx = 0;

         for(StructurePiece ☃xxxxxx : this.field_75075_a) {
            if (!(☃xxxxxx instanceof VillagePieces.Road)) {
               ++☃xxxxx;
            }
         }

         this.field_75076_c = ☃xxxxx > 2;
      }

      @Override
      public boolean func_75069_d() {
         return this.field_75076_c;
      }

      @Override
      public void func_143022_a(NBTTagCompound var1) {
         super.func_143022_a(☃);
         ☃.func_74757_a("Valid", this.field_75076_c);
      }

      @Override
      public void func_143017_b(NBTTagCompound var1) {
         super.func_143017_b(☃);
         this.field_75076_c = ☃.func_74767_n("Valid");
      }
   }
}
