package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.apache.commons.lang3.mutable.MutableInt;

public class FossilFeature extends Feature<FossilFeatureConfiguration> {
   public FossilFeature(Codec<FossilFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<FossilFeatureConfiguration> var1) {
      Random â˜ƒ = â˜ƒ.random();
      WorldGenLevel â˜ƒx = â˜ƒ.level();
      BlockPos â˜ƒxx = â˜ƒ.origin();
      Rotation â˜ƒxxx = Rotation.getRandom(â˜ƒ);
      FossilFeatureConfiguration â˜ƒxxxx = â˜ƒ.config();
      int â˜ƒxxxxx = â˜ƒ.nextInt(â˜ƒxxxx.fossilStructures.size());
      StructureManager â˜ƒxxxxxx = â˜ƒx.getLevel().getServer().getStructureManager();
      StructureTemplate â˜ƒxxxxxxx = â˜ƒxxxxxx.getOrCreate((ResourceLocation)â˜ƒxxxx.fossilStructures.get(â˜ƒxxxxx));
      StructureTemplate â˜ƒxxxxxxxx = â˜ƒxxxxxx.getOrCreate((ResourceLocation)â˜ƒxxxx.overlayStructures.get(â˜ƒxxxxx));
      ChunkPos â˜ƒxxxxxxxxx = new ChunkPos(â˜ƒxx);
      BoundingBox â˜ƒxxxxxxxxxx = new BoundingBox(
         â˜ƒxxxxxxxxx.getMinBlockX(),
         â˜ƒx.getMinBuildHeight(),
         â˜ƒxxxxxxxxx.getMinBlockZ(),
         â˜ƒxxxxxxxxx.getMaxBlockX(),
         â˜ƒx.getMaxBuildHeight(),
         â˜ƒxxxxxxxxx.getMaxBlockZ()
      );
      StructurePlaceSettings â˜ƒxxxxxxxxxxx = new StructurePlaceSettings().setRotation(â˜ƒxxx).setBoundingBox(â˜ƒxxxxxxxxxx).setRandom(â˜ƒ);
      Vec3i â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxx.getSize(â˜ƒxxx);
      int â˜ƒxxxxxxxxxxxxx = â˜ƒ.nextInt(16 - â˜ƒxxxxxxxxxxxx.getX());
      int â˜ƒxxxxxxxxxxxxxx = â˜ƒ.nextInt(16 - â˜ƒxxxxxxxxxxxx.getZ());
      int â˜ƒxxxxxxxxxxxxxxx = â˜ƒx.getMaxBuildHeight();

      for(int â˜ƒxxxxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxxxxx < â˜ƒxxxxxxxxxxxx.getX(); ++â˜ƒxxxxxxxxxxxxxxxx) {
         for(int â˜ƒxxxxxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxxxxxx < â˜ƒxxxxxxxxxxxx.getZ(); ++â˜ƒxxxxxxxxxxxxxxxxx) {
            â˜ƒxxxxxxxxxxxxxxx = Math.min(
               â˜ƒxxxxxxxxxxxxxxx,
               â˜ƒx.getHeight(
                  Heightmap.Types.OCEAN_FLOOR_WG,
                  â˜ƒxx.getX() + â˜ƒxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxx,
                  â˜ƒxx.getZ() + â˜ƒxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxx
               )
            );
         }
      }

      int â˜ƒxxxxxxxxxxxxxxxx = Math.max(â˜ƒxxxxxxxxxxxxxxx - 15 - â˜ƒ.nextInt(10), â˜ƒx.getMinBuildHeight() + 10);
      BlockPos â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxx.getZeroPositionWithTransform(
         â˜ƒxx.offset(â˜ƒxxxxxxxxxxxxx, 0, â˜ƒxxxxxxxxxxxxxx).atY(â˜ƒxxxxxxxxxxxxxxxx), Mirror.NONE, â˜ƒxxx
      );
      if (countEmptyCorners(â˜ƒx, â˜ƒxxxxxxx.getBoundingBox(â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx)) > â˜ƒxxxx.maxEmptyCornersAllowed) {
         return false;
      } else {
         â˜ƒxxxxxxxxxxx.clearProcessors();
         ((StructureProcessorList)â˜ƒxxxx.fossilProcessors.get()).list().forEach(var1x -> â˜ƒ.addProcessor(var1x));
         â˜ƒxxxxxxx.placeInWorld(â˜ƒx, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒ, 4);
         â˜ƒxxxxxxxxxxx.clearProcessors();
         ((StructureProcessorList)â˜ƒxxxx.overlayProcessors.get()).list().forEach(var1x -> â˜ƒ.addProcessor(var1x));
         â˜ƒxxxxxxxx.placeInWorld(â˜ƒx, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒ, 4);
         return true;
      }
   }

   private static int countEmptyCorners(WorldGenLevel var0, BoundingBox var1) {
      MutableInt â˜ƒ = new MutableInt(0);
      â˜ƒ.forAllCorners(var2x -> {
         BlockState â˜ƒ = â˜ƒ.getBlockState(var2x);
         if (â˜ƒ.isAir() || â˜ƒ.is(Blocks.LAVA) || â˜ƒ.is(Blocks.WATER)) {
            â˜ƒ.add(1);
         }
      });
      return â˜ƒ.getValue();
   }
}
