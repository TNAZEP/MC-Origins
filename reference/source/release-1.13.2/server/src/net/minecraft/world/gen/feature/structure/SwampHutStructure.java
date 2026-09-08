package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.entity.EntityType;
import net.minecraft.init.Biomes;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenerator;

public class SwampHutStructure extends ScatteredStructure<SwampHutConfig> {
   private static final List<Biome.SpawnListEntry> field_202384_d = Lists.<Biome.SpawnListEntry>newArrayList(
      new Biome.SpawnListEntry(EntityType.field_200759_ay, 1, 1, 1)
   );

   @Override
   protected String func_143025_a() {
      return "Swamp_Hut";
   }

   @Override
   public int func_202367_b() {
      return 3;
   }

   @Override
   protected StructureStart func_202369_a(IWorld var1, IChunkGenerator<?> var2, SharedSeedRandom var3, int var4, int var5) {
      Biome ☃ = ☃.func_202090_b().func_180300_a(new BlockPos((☃ << 4) + 9, 0, (☃ << 4) + 9), Biomes.field_76772_c);
      return new SwampHutStructure.Start(☃, ☃, ☃, ☃, ☃);
   }

   @Override
   protected int func_202382_c() {
      return 14357620;
   }

   @Override
   public List<Biome.SpawnListEntry> func_202279_e() {
      return field_202384_d;
   }

   public boolean func_202383_b(IWorld var1, BlockPos var2) {
      StructureStart ☃ = this.func_202364_a(☃, ☃);
      if (☃ != field_202376_c && ☃ instanceof SwampHutStructure.Start && !☃.func_186161_c().isEmpty()) {
         StructurePiece ☃x = (StructurePiece)☃.func_186161_c().get(0);
         return ☃x instanceof SwampHutPiece;
      } else {
         return false;
      }
   }

   public static class Start extends StructureStart {
      public Start() {
      }

      public Start(IWorld var1, SharedSeedRandom var2, int var3, int var4, Biome var5) {
         super(☃, ☃, ☃, ☃, ☃.func_72905_C());
         SwampHutPiece ☃ = new SwampHutPiece(☃, ☃ * 16, ☃ * 16);
         this.field_75075_a.add(☃);
         this.func_202500_a(☃);
      }
   }
}
