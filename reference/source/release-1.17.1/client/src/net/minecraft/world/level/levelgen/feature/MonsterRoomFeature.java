package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MonsterRoomFeature extends Feature<NoneFeatureConfiguration> {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final EntityType<?>[] MOBS = new EntityType[]{EntityType.SKELETON, EntityType.ZOMBIE, EntityType.ZOMBIE, EntityType.SPIDER};
   private static final BlockState AIR = Blocks.CAVE_AIR.defaultBlockState();

   public MonsterRoomFeature(Codec<NoneFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> var1) {
      Predicate<BlockState> â˜ƒ = Feature.isReplaceable(BlockTags.FEATURES_CANNOT_REPLACE.getName());
      BlockPos â˜ƒx = â˜ƒ.origin();
      Random â˜ƒxx = â˜ƒ.random();
      WorldGenLevel â˜ƒxxx = â˜ƒ.level();
      int â˜ƒxxxx = 3;
      int â˜ƒxxxxx = â˜ƒxx.nextInt(2) + 2;
      int â˜ƒxxxxxx = -â˜ƒxxxxx - 1;
      int â˜ƒxxxxxxx = â˜ƒxxxxx + 1;
      int â˜ƒxxxxxxxx = -1;
      int â˜ƒxxxxxxxxx = 4;
      int â˜ƒxxxxxxxxxx = â˜ƒxx.nextInt(2) + 2;
      int â˜ƒxxxxxxxxxxx = -â˜ƒxxxxxxxxxx - 1;
      int â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxx + 1;
      int â˜ƒxxxxxxxxxxxxx = 0;

      for(int â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxx; â˜ƒxxxxxxxxxxxxxx <= â˜ƒxxxxxxx; ++â˜ƒxxxxxxxxxxxxxx) {
         for(int â˜ƒxxxxxxxxxxxxxxx = -1; â˜ƒxxxxxxxxxxxxxxx <= 4; ++â˜ƒxxxxxxxxxxxxxxx) {
            for(int â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx; â˜ƒxxxxxxxxxxxxxxxx <= â˜ƒxxxxxxxxxxxx; ++â˜ƒxxxxxxxxxxxxxxxx) {
               BlockPos â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒx.offset(â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx);
               Material â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒxxx.getBlockState(â˜ƒxxxxxxxxxxxxxxxxx).getMaterial();
               boolean â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxx.isSolid();
               if (â˜ƒxxxxxxxxxxxxxxx == -1 && !â˜ƒxxxxxxxxxxxxxxxxxxx) {
                  return false;
               }

               if (â˜ƒxxxxxxxxxxxxxxx == 4 && !â˜ƒxxxxxxxxxxxxxxxxxxx) {
                  return false;
               }

               if ((
                     â˜ƒxxxxxxxxxxxxxx == â˜ƒxxxxxx
                        || â˜ƒxxxxxxxxxxxxxx == â˜ƒxxxxxxx
                        || â˜ƒxxxxxxxxxxxxxxxx == â˜ƒxxxxxxxxxxx
                        || â˜ƒxxxxxxxxxxxxxxxx == â˜ƒxxxxxxxxxxxx
                  )
                  && â˜ƒxxxxxxxxxxxxxxx == 0
                  && â˜ƒxxx.isEmptyBlock(â˜ƒxxxxxxxxxxxxxxxxx)
                  && â˜ƒxxx.isEmptyBlock(â˜ƒxxxxxxxxxxxxxxxxx.above())) {
                  ++â˜ƒxxxxxxxxxxxxx;
               }
            }
         }
      }

      if (â˜ƒxxxxxxxxxxxxx >= 1 && â˜ƒxxxxxxxxxxxxx <= 5) {
         for(int â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxx; â˜ƒxxxxxxxxxxxxxx <= â˜ƒxxxxxxx; ++â˜ƒxxxxxxxxxxxxxx) {
            for(int â˜ƒxxxxxxxxxxxxxxx = 3; â˜ƒxxxxxxxxxxxxxxx >= -1; --â˜ƒxxxxxxxxxxxxxxx) {
               for(int â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx; â˜ƒxxxxxxxxxxxxxxxx <= â˜ƒxxxxxxxxxxxx; ++â˜ƒxxxxxxxxxxxxxxxx) {
                  BlockPos â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒx.offset(â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx);
                  BlockState â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒxxx.getBlockState(â˜ƒxxxxxxxxxxxxxxxxx);
                  if (â˜ƒxxxxxxxxxxxxxx == â˜ƒxxxxxx
                     || â˜ƒxxxxxxxxxxxxxxx == -1
                     || â˜ƒxxxxxxxxxxxxxxxx == â˜ƒxxxxxxxxxxx
                     || â˜ƒxxxxxxxxxxxxxx == â˜ƒxxxxxxx
                     || â˜ƒxxxxxxxxxxxxxxx == 4
                     || â˜ƒxxxxxxxxxxxxxxxx == â˜ƒxxxxxxxxxxxx) {
                     if (â˜ƒxxxxxxxxxxxxxxxxx.getY() >= â˜ƒxxx.getMinBuildHeight()
                        && !â˜ƒxxx.getBlockState(â˜ƒxxxxxxxxxxxxxxxxx.below()).getMaterial().isSolid()) {
                        â˜ƒxxx.setBlock(â˜ƒxxxxxxxxxxxxxxxxx, AIR, 2);
                     } else if (â˜ƒxxxxxxxxxxxxxxxxxx.getMaterial().isSolid() && !â˜ƒxxxxxxxxxxxxxxxxxx.is(Blocks.CHEST)) {
                        if (â˜ƒxxxxxxxxxxxxxxx == -1 && â˜ƒxx.nextInt(4) != 0) {
                           this.safeSetBlock(â˜ƒxxx, â˜ƒxxxxxxxxxxxxxxxxx, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), â˜ƒ);
                        } else {
                           this.safeSetBlock(â˜ƒxxx, â˜ƒxxxxxxxxxxxxxxxxx, Blocks.COBBLESTONE.defaultBlockState(), â˜ƒ);
                        }
                     }
                  } else if (!â˜ƒxxxxxxxxxxxxxxxxxx.is(Blocks.CHEST) && !â˜ƒxxxxxxxxxxxxxxxxxx.is(Blocks.SPAWNER)) {
                     this.safeSetBlock(â˜ƒxxx, â˜ƒxxxxxxxxxxxxxxxxx, AIR, â˜ƒ);
                  }
               }
            }
         }

         for(int â˜ƒxxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxxx < 2; ++â˜ƒxxxxxxxxxxxxxx) {
            for(int â˜ƒxxxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxxxx < 3; ++â˜ƒxxxxxxxxxxxxxxx) {
               int â˜ƒxxxxxxxxxxxxxxxx = â˜ƒx.getX() + â˜ƒxx.nextInt(â˜ƒxxxxx * 2 + 1) - â˜ƒxxxxx;
               int â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒx.getY();
               int â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒx.getZ() + â˜ƒxx.nextInt(â˜ƒxxxxxxxxxx * 2 + 1) - â˜ƒxxxxxxxxxx;
               BlockPos â˜ƒxxxxxxxxxxxxxxxxxxx = new BlockPos(â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxx);
               if (â˜ƒxxx.isEmptyBlock(â˜ƒxxxxxxxxxxxxxxxxxxx)) {
                  int â˜ƒxxxxxxxxxxxxxxxxxxxx = 0;

                  for(Direction â˜ƒxxxxxxxxxxxxxxxxxxxxx : Direction.Plane.HORIZONTAL) {
                     if (â˜ƒxxx.getBlockState(â˜ƒxxxxxxxxxxxxxxxxxxx.relative(â˜ƒxxxxxxxxxxxxxxxxxxxxx)).getMaterial().isSolid()) {
                        ++â˜ƒxxxxxxxxxxxxxxxxxxxx;
                     }
                  }

                  if (â˜ƒxxxxxxxxxxxxxxxxxxxx == 1) {
                     this.safeSetBlock(
                        â˜ƒxxx, â˜ƒxxxxxxxxxxxxxxxxxxx, StructurePiece.reorient(â˜ƒxxx, â˜ƒxxxxxxxxxxxxxxxxxxx, Blocks.CHEST.defaultBlockState()), â˜ƒ
                     );
                     RandomizableContainerBlockEntity.setLootTable(â˜ƒxxx, â˜ƒxx, â˜ƒxxxxxxxxxxxxxxxxxxx, BuiltInLootTables.SIMPLE_DUNGEON);
                     break;
                  }
               }
            }
         }

         this.safeSetBlock(â˜ƒxxx, â˜ƒx, Blocks.SPAWNER.defaultBlockState(), â˜ƒ);
         BlockEntity â˜ƒxxxxxxxxxxxxxx = â˜ƒxxx.getBlockEntity(â˜ƒx);
         if (â˜ƒxxxxxxxxxxxxxx instanceof SpawnerBlockEntity) {
            ((SpawnerBlockEntity)â˜ƒxxxxxxxxxxxxxx).getSpawner().setEntityId(this.randomEntityId(â˜ƒxx));
         } else {
            LOGGER.error("Failed to fetch mob spawner entity at ({}, {}, {})", â˜ƒx.getX(), â˜ƒx.getY(), â˜ƒx.getZ());
         }

         return true;
      } else {
         return false;
      }
   }

   private EntityType<?> randomEntityId(Random var1) {
      return Util.getRandom(MOBS, â˜ƒ);
   }
}
