package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.entity.EntityType;
import net.minecraft.init.Biomes;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class OceanMonumentStructure extends Structure<OceanMonumentConfig> {
   private static final List<Biome.SpawnListEntry> field_175803_h = Lists.<Biome.SpawnListEntry>newArrayList(
      new Biome.SpawnListEntry(EntityType.field_200761_A, 1, 2, 4)
   );

   @Override
   protected ChunkPos func_211744_a(IChunkGenerator<?> var1, Random var2, int var3, int var4, int var5, int var6) {
      int ☃ = ☃.func_201496_a_().func_202174_b();
      int ☃x = ☃.func_201496_a_().func_202171_c();
      int ☃xx = ☃ + ☃ * ☃;
      int ☃xxx = ☃ + ☃ * ☃;
      int ☃xxxx = ☃xx < 0 ? ☃xx - ☃ + 1 : ☃xx;
      int ☃xxxxx = ☃xxx < 0 ? ☃xxx - ☃ + 1 : ☃xxx;
      int ☃xxxxxx = ☃xxxx / ☃;
      int ☃xxxxxxx = ☃xxxxx / ☃;
      ((SharedSeedRandom)☃).func_202427_a(☃.func_202089_c(), ☃xxxxxx, ☃xxxxxxx, 10387313);
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
         for(Biome ☃x : ☃.func_202090_b().func_201538_a(☃ * 16 + 9, ☃ * 16 + 9, 16)) {
            if (!☃.func_202094_a(☃x, Feature.field_202336_n)) {
               return false;
            }
         }

         for(Biome ☃x : ☃.func_202090_b().func_201538_a(☃ * 16 + 9, ☃ * 16 + 9, 29)) {
            if (☃x.func_201856_r() != Biome.Category.OCEAN && ☃x.func_201856_r() != Biome.Category.RIVER) {
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
      return new OceanMonumentStructure.Start(☃, ☃, ☃, ☃, ☃);
   }

   @Override
   protected String func_143025_a() {
      return "Monument";
   }

   @Override
   public int func_202367_b() {
      return 8;
   }

   @Override
   public List<Biome.SpawnListEntry> func_202279_e() {
      return field_175803_h;
   }

   public static class Start extends StructureStart {
      private final Set<ChunkPos> field_175791_c = Sets.<ChunkPos>newHashSet();
      private boolean field_175790_d;

      public Start() {
      }

      public Start(IWorld var1, SharedSeedRandom var2, int var3, int var4, Biome var5) {
         super(☃, ☃, ☃, ☃, ☃.func_72905_C());
         this.func_175789_b(☃, ☃, ☃, ☃);
      }

      private void func_175789_b(IBlockReader var1, Random var2, int var3, int var4) {
         int ☃ = ☃ * 16 - 29;
         int ☃x = ☃ * 16 - 29;
         EnumFacing ☃xx = EnumFacing.Plane.HORIZONTAL.func_179518_a(☃);
         this.field_75075_a.add(new OceanMonumentPieces.MonumentBuilding(☃, ☃, ☃x, ☃xx));
         this.func_202500_a(☃);
         this.field_175790_d = true;
      }

      @Override
      public void func_75068_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         if (!this.field_175790_d) {
            this.field_75075_a.clear();
            this.func_175789_b(☃, ☃, this.func_143019_e(), this.func_143018_f());
         }

         super.func_75068_a(☃, ☃, ☃, ☃);
      }

      @Override
      public void func_175787_b(ChunkPos var1) {
         super.func_175787_b(☃);
         this.field_175791_c.add(☃);
      }

      @Override
      public void func_143022_a(NBTTagCompound var1) {
         super.func_143022_a(☃);
         NBTTagList ☃ = new NBTTagList();

         for(ChunkPos ☃x : this.field_175791_c) {
            NBTTagCompound ☃xx = new NBTTagCompound();
            ☃xx.func_74768_a("X", ☃x.field_77276_a);
            ☃xx.func_74768_a("Z", ☃x.field_77275_b);
            ☃.add((INBTBase)☃xx);
         }

         ☃.func_74782_a("Processed", ☃);
      }

      @Override
      public void func_143017_b(NBTTagCompound var1) {
         super.func_143017_b(☃);
         if (☃.func_150297_b("Processed", 9)) {
            NBTTagList ☃ = ☃.func_150295_c("Processed", 10);

            for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
               NBTTagCompound ☃xx = ☃.func_150305_b(☃x);
               this.field_175791_c.add(new ChunkPos(☃xx.func_74762_e("X"), ☃xx.func_74762_e("Z")));
            }
         }
      }
   }
}
