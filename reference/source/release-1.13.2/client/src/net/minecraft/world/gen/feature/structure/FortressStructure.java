package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.EntityType;
import net.minecraft.init.Biomes;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class FortressStructure extends Structure<FortressConfig> {
   private static final List<Biome.SpawnListEntry> field_202381_d = Lists.<Biome.SpawnListEntry>newArrayList(
      new Biome.SpawnListEntry(EntityType.field_200792_f, 10, 2, 3),
      new Biome.SpawnListEntry(EntityType.field_200785_Y, 5, 4, 4),
      new Biome.SpawnListEntry(EntityType.field_200722_aA, 8, 5, 5),
      new Biome.SpawnListEntry(EntityType.field_200741_ag, 2, 5, 5),
      new Biome.SpawnListEntry(EntityType.field_200771_K, 3, 4, 4)
   );

   @Override
   protected boolean func_202372_a(IChunkGenerator<?> var1, Random var2, int var3, int var4) {
      int ☃ = ☃ >> 4;
      int ☃x = ☃ >> 4;
      ☃.setSeed((long)(☃ ^ ☃x << 4) ^ ☃.func_202089_c());
      ☃.nextInt();
      if (☃.nextInt(3) != 0) {
         return false;
      } else if (☃ != (☃ << 4) + 4 + ☃.nextInt(8)) {
         return false;
      } else if (☃ != (☃x << 4) + 4 + ☃.nextInt(8)) {
         return false;
      } else {
         Biome ☃ = ☃.func_202090_b().func_180300_a(new BlockPos((☃ << 4) + 9, 0, (☃ << 4) + 9), Biomes.field_180279_ad);
         return ☃.func_202094_a(☃, Feature.field_202337_o);
      }
   }

   @Override
   protected boolean func_202365_a(IWorld var1) {
      return ☃.func_72912_H().func_76089_r();
   }

   @Override
   protected StructureStart func_202369_a(IWorld var1, IChunkGenerator<?> var2, SharedSeedRandom var3, int var4, int var5) {
      Biome ☃ = ☃.func_202090_b().func_180300_a(new BlockPos((☃ << 4) + 9, 0, (☃ << 4) + 9), Biomes.field_180279_ad);
      return new FortressStructure.Start(☃, ☃, ☃, ☃, ☃);
   }

   @Override
   protected String func_143025_a() {
      return "Fortress";
   }

   @Override
   public int func_202367_b() {
      return 8;
   }

   @Override
   public List<Biome.SpawnListEntry> func_202279_e() {
      return field_202381_d;
   }

   public static class Start extends StructureStart {
      public Start() {
      }

      public Start(IWorld var1, SharedSeedRandom var2, int var3, int var4, Biome var5) {
         super(☃, ☃, ☃, ☃, ☃.func_72905_C());
         FortressPieces.Start ☃ = new FortressPieces.Start(☃, (☃ << 4) + 2, (☃ << 4) + 2);
         this.field_75075_a.add(☃);
         ☃.func_74861_a(☃, this.field_75075_a, ☃);
         List<StructurePiece> ☃x = ☃.field_74967_d;

         while(!☃x.isEmpty()) {
            int ☃xx = ☃.nextInt(☃x.size());
            StructurePiece ☃xxx = (StructurePiece)☃x.remove(☃xx);
            ☃xxx.func_74861_a(☃, this.field_75075_a, ☃);
         }

         this.func_202500_a(☃);
         this.func_75070_a(☃, ☃, 48, 70);
      }
   }
}
