package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class BonusChestFeature extends Feature<NoneFeatureConfiguration> {
   public BonusChestFeature(Codec<NoneFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> var1) {
      Random â˜ƒ = â˜ƒ.random();
      WorldGenLevel â˜ƒx = â˜ƒ.level();
      ChunkPos â˜ƒxx = new ChunkPos(â˜ƒ.origin());
      List<Integer> â˜ƒxxx = (List)IntStream.rangeClosed(â˜ƒxx.getMinBlockX(), â˜ƒxx.getMaxBlockX()).boxed().collect(Collectors.toList());
      Collections.shuffle(â˜ƒxxx, â˜ƒ);
      List<Integer> â˜ƒxxxx = (List)IntStream.rangeClosed(â˜ƒxx.getMinBlockZ(), â˜ƒxx.getMaxBlockZ()).boxed().collect(Collectors.toList());
      Collections.shuffle(â˜ƒxxxx, â˜ƒ);
      BlockPos.MutableBlockPos â˜ƒxxxxx = new BlockPos.MutableBlockPos();

      for(Integer â˜ƒxxxxxx : â˜ƒxxx) {
         for(Integer â˜ƒxxxxxxx : â˜ƒxxxx) {
            â˜ƒxxxxx.set(â˜ƒxxxxxx, 0, â˜ƒxxxxxxx);
            BlockPos â˜ƒxxxxxxxx = â˜ƒx.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, â˜ƒxxxxx);
            if (â˜ƒx.isEmptyBlock(â˜ƒxxxxxxxx) || â˜ƒx.getBlockState(â˜ƒxxxxxxxx).getCollisionShape(â˜ƒx, â˜ƒxxxxxxxx).isEmpty()) {
               â˜ƒx.setBlock(â˜ƒxxxxxxxx, Blocks.CHEST.defaultBlockState(), 2);
               RandomizableContainerBlockEntity.setLootTable(â˜ƒx, â˜ƒ, â˜ƒxxxxxxxx, BuiltInLootTables.SPAWN_BONUS_CHEST);
               BlockState â˜ƒxxxxxxxxx = Blocks.TORCH.defaultBlockState();

               for(Direction â˜ƒxxxxxxxxxx : Direction.Plane.HORIZONTAL) {
                  BlockPos â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxx.relative(â˜ƒxxxxxxxxxx);
                  if (â˜ƒxxxxxxxxx.canSurvive(â˜ƒx, â˜ƒxxxxxxxxxxx)) {
                     â˜ƒx.setBlock(â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxx, 2);
                  }
               }

               return true;
            }
         }
      }

      return false;
   }
}
