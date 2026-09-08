package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.MinecartChest;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.RailBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.MineshaftFeature;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MineShaftPieces {
   static final Logger LOGGER = LogManager.getLogger();
   private static final int DEFAULT_SHAFT_WIDTH = 3;
   private static final int DEFAULT_SHAFT_HEIGHT = 3;
   private static final int DEFAULT_SHAFT_LENGTH = 5;
   private static final int MAX_PILLAR_HEIGHT = 20;
   private static final int MAX_CHAIN_HEIGHT = 50;
   private static final int MAX_DEPTH = 8;

   private static MineShaftPieces.MineShaftPiece createRandomShaftPiece(
      StructurePieceAccessor var0, Random var1, int var2, int var3, int var4, @Nullable Direction var5, int var6, MineshaftFeature.Type var7
   ) {
      int â˜ƒ = â˜ƒ.nextInt(100);
      if (â˜ƒ >= 80) {
         BoundingBox â˜ƒx = MineShaftPieces.MineShaftCrossing.findCrossing(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒx != null) {
            return new MineShaftPieces.MineShaftCrossing(â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ);
         }
      } else if (â˜ƒ >= 70) {
         BoundingBox â˜ƒ = MineShaftPieces.MineShaftStairs.findStairs(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒ != null) {
            return new MineShaftPieces.MineShaftStairs(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }
      } else {
         BoundingBox â˜ƒ = MineShaftPieces.MineShaftCorridor.findCorridorSize(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒ != null) {
            return new MineShaftPieces.MineShaftCorridor(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }

      return null;
   }

   static MineShaftPieces.MineShaftPiece generateAndAddPiece(
      StructurePiece var0, StructurePieceAccessor var1, Random var2, int var3, int var4, int var5, Direction var6, int var7
   ) {
      if (â˜ƒ > 8) {
         return null;
      } else if (Math.abs(â˜ƒ - â˜ƒ.getBoundingBox().minX()) <= 80 && Math.abs(â˜ƒ - â˜ƒ.getBoundingBox().minZ()) <= 80) {
         MineshaftFeature.Type â˜ƒ = ((MineShaftPieces.MineShaftPiece)â˜ƒ).type;
         MineShaftPieces.MineShaftPiece â˜ƒx = createRandomShaftPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + 1, â˜ƒ);
         if (â˜ƒx != null) {
            â˜ƒ.addPiece(â˜ƒx);
            â˜ƒx.addChildren(â˜ƒ, â˜ƒ, â˜ƒ);
         }

         return â˜ƒx;
      } else {
         return null;
      }
   }

   public static class MineShaftCorridor extends MineShaftPieces.MineShaftPiece {
      private final boolean hasRails;
      private final boolean spiderCorridor;
      private boolean hasPlacedSpider;
      private final int numSections;

      public MineShaftCorridor(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.MINE_SHAFT_CORRIDOR, â˜ƒ);
         this.hasRails = â˜ƒ.getBoolean("hr");
         this.spiderCorridor = â˜ƒ.getBoolean("sc");
         this.hasPlacedSpider = â˜ƒ.getBoolean("hps");
         this.numSections = â˜ƒ.getInt("Num");
      }

      @Override
      protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
         super.addAdditionalSaveData(â˜ƒ, â˜ƒ);
         â˜ƒ.putBoolean("hr", this.hasRails);
         â˜ƒ.putBoolean("sc", this.spiderCorridor);
         â˜ƒ.putBoolean("hps", this.hasPlacedSpider);
         â˜ƒ.putInt("Num", this.numSections);
      }

      public MineShaftCorridor(int var1, Random var2, BoundingBox var3, Direction var4, MineshaftFeature.Type var5) {
         super(StructurePieceType.MINE_SHAFT_CORRIDOR, â˜ƒ, â˜ƒ, â˜ƒ);
         this.setOrientation(â˜ƒ);
         this.hasRails = â˜ƒ.nextInt(3) == 0;
         this.spiderCorridor = !this.hasRails && â˜ƒ.nextInt(23) == 0;
         if (this.getOrientation().getAxis() == Direction.Axis.Z) {
            this.numSections = â˜ƒ.getZSpan() / 5;
         } else {
            this.numSections = â˜ƒ.getXSpan() / 5;
         }
      }

      @Nullable
      public static BoundingBox findCorridorSize(StructurePieceAccessor var0, Random var1, int var2, int var3, int var4, Direction var5) {
         for(int â˜ƒ = â˜ƒ.nextInt(3) + 2; â˜ƒ > 0; --â˜ƒ) {
            int â˜ƒ = â˜ƒ * 5;

            BoundingBox var7 = switch(â˜ƒ) {
               default -> new BoundingBox(0, 0, -(â˜ƒ - 1), 2, 2, 0);
               case SOUTH -> new BoundingBox(0, 0, 0, 2, 2, â˜ƒ - 1);
               case WEST -> new BoundingBox(-(â˜ƒ - 1), 0, 0, 0, 2, 2);
               case EAST -> new BoundingBox(0, 0, 0, â˜ƒ - 1, 2, 2);
            };
            var7.move(â˜ƒ, â˜ƒ, â˜ƒ);
            if (â˜ƒ.findCollisionPiece(var7) == null) {
               return var7;
            }
         }

         return null;
      }

      @Override
      public void addChildren(StructurePiece var1, StructurePieceAccessor var2, Random var3) {
         int â˜ƒ = this.getGenDepth();
         int â˜ƒx = â˜ƒ.nextInt(4);
         Direction â˜ƒxx = this.getOrientation();
         if (â˜ƒxx != null) {
            switch(â˜ƒxx) {
               case NORTH:
               default:
                  if (â˜ƒx <= 1) {
                     MineShaftPieces.generateAndAddPiece(
                        â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX(), this.boundingBox.minY() - 1 + â˜ƒ.nextInt(3), this.boundingBox.minZ() - 1, â˜ƒxx, â˜ƒ
                     );
                  } else if (â˜ƒx == 2) {
                     MineShaftPieces.generateAndAddPiece(
                        â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX() - 1, this.boundingBox.minY() - 1 + â˜ƒ.nextInt(3), this.boundingBox.minZ(), Direction.WEST, â˜ƒ
                     );
                  } else {
                     MineShaftPieces.generateAndAddPiece(
                        â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.maxX() + 1, this.boundingBox.minY() - 1 + â˜ƒ.nextInt(3), this.boundingBox.minZ(), Direction.EAST, â˜ƒ
                     );
                  }
                  break;
               case SOUTH:
                  if (â˜ƒx <= 1) {
                     MineShaftPieces.generateAndAddPiece(
                        â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX(), this.boundingBox.minY() - 1 + â˜ƒ.nextInt(3), this.boundingBox.maxZ() + 1, â˜ƒxx, â˜ƒ
                     );
                  } else if (â˜ƒx == 2) {
                     MineShaftPieces.generateAndAddPiece(
                        â˜ƒ,
                        â˜ƒ,
                        â˜ƒ,
                        this.boundingBox.minX() - 1,
                        this.boundingBox.minY() - 1 + â˜ƒ.nextInt(3),
                        this.boundingBox.maxZ() - 3,
                        Direction.WEST,
                        â˜ƒ
                     );
                  } else {
                     MineShaftPieces.generateAndAddPiece(
                        â˜ƒ,
                        â˜ƒ,
                        â˜ƒ,
                        this.boundingBox.maxX() + 1,
                        this.boundingBox.minY() - 1 + â˜ƒ.nextInt(3),
                        this.boundingBox.maxZ() - 3,
                        Direction.EAST,
                        â˜ƒ
                     );
                  }
                  break;
               case WEST:
                  if (â˜ƒx <= 1) {
                     MineShaftPieces.generateAndAddPiece(
                        â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX() - 1, this.boundingBox.minY() - 1 + â˜ƒ.nextInt(3), this.boundingBox.minZ(), â˜ƒxx, â˜ƒ
                     );
                  } else if (â˜ƒx == 2) {
                     MineShaftPieces.generateAndAddPiece(
                        â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX(), this.boundingBox.minY() - 1 + â˜ƒ.nextInt(3), this.boundingBox.minZ() - 1, Direction.NORTH, â˜ƒ
                     );
                  } else {
                     MineShaftPieces.generateAndAddPiece(
                        â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX(), this.boundingBox.minY() - 1 + â˜ƒ.nextInt(3), this.boundingBox.maxZ() + 1, Direction.SOUTH, â˜ƒ
                     );
                  }
                  break;
               case EAST:
                  if (â˜ƒx <= 1) {
                     MineShaftPieces.generateAndAddPiece(
                        â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.maxX() + 1, this.boundingBox.minY() - 1 + â˜ƒ.nextInt(3), this.boundingBox.minZ(), â˜ƒxx, â˜ƒ
                     );
                  } else if (â˜ƒx == 2) {
                     MineShaftPieces.generateAndAddPiece(
                        â˜ƒ,
                        â˜ƒ,
                        â˜ƒ,
                        this.boundingBox.maxX() - 3,
                        this.boundingBox.minY() - 1 + â˜ƒ.nextInt(3),
                        this.boundingBox.minZ() - 1,
                        Direction.NORTH,
                        â˜ƒ
                     );
                  } else {
                     MineShaftPieces.generateAndAddPiece(
                        â˜ƒ,
                        â˜ƒ,
                        â˜ƒ,
                        this.boundingBox.maxX() - 3,
                        this.boundingBox.minY() - 1 + â˜ƒ.nextInt(3),
                        this.boundingBox.maxZ() + 1,
                        Direction.SOUTH,
                        â˜ƒ
                     );
                  }
            }
         }

         if (â˜ƒ < 8) {
            if (â˜ƒxx != Direction.NORTH && â˜ƒxx != Direction.SOUTH) {
               for(int â˜ƒ = this.boundingBox.minX() + 3; â˜ƒ + 3 <= this.boundingBox.maxX(); â˜ƒ += 5) {
                  int â˜ƒx = â˜ƒ.nextInt(5);
                  if (â˜ƒx == 0) {
                     MineShaftPieces.generateAndAddPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minY(), this.boundingBox.minZ() - 1, Direction.NORTH, â˜ƒ + 1);
                  } else if (â˜ƒx == 1) {
                     MineShaftPieces.generateAndAddPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minY(), this.boundingBox.maxZ() + 1, Direction.SOUTH, â˜ƒ + 1);
                  }
               }
            } else {
               for(int â˜ƒ = this.boundingBox.minZ() + 3; â˜ƒ + 3 <= this.boundingBox.maxZ(); â˜ƒ += 5) {
                  int â˜ƒx = â˜ƒ.nextInt(5);
                  if (â˜ƒx == 0) {
                     MineShaftPieces.generateAndAddPiece(â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX() - 1, this.boundingBox.minY(), â˜ƒ, Direction.WEST, â˜ƒ + 1);
                  } else if (â˜ƒx == 1) {
                     MineShaftPieces.generateAndAddPiece(â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.maxX() + 1, this.boundingBox.minY(), â˜ƒ, Direction.EAST, â˜ƒ + 1);
                  }
               }
            }
         }
      }

      @Override
      protected boolean createChest(WorldGenLevel var1, BoundingBox var2, Random var3, int var4, int var5, int var6, ResourceLocation var7) {
         BlockPos â˜ƒ = this.getWorldPos(â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒ.isInside(â˜ƒ) && â˜ƒ.getBlockState(â˜ƒ).isAir() && !â˜ƒ.getBlockState(â˜ƒ.below()).isAir()) {
            BlockState â˜ƒx = Blocks.RAIL.defaultBlockState().setValue(RailBlock.SHAPE, â˜ƒ.nextBoolean() ? RailShape.NORTH_SOUTH : RailShape.EAST_WEST);
            this.placeBlock(â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
            MinecartChest â˜ƒxx = new MinecartChest(â˜ƒ.getLevel(), (double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY() + 0.5, (double)â˜ƒ.getZ() + 0.5);
            â˜ƒxx.setLootTable(â˜ƒ, â˜ƒ.nextLong());
            â˜ƒ.addFreshEntity(â˜ƒxx);
            return true;
         } else {
            return false;
         }
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         if (this.edgesLiquid(â˜ƒ, â˜ƒ)) {
            return false;
         } else {
            int â˜ƒ = 0;
            int â˜ƒx = 2;
            int â˜ƒxx = 0;
            int â˜ƒxxx = 2;
            int â˜ƒxxxx = this.numSections * 5 - 1;
            BlockState â˜ƒxxxxx = this.type.getPlanksState();
            this.generateBox(â˜ƒ, â˜ƒ, 0, 0, 0, 2, 1, â˜ƒxxxx, CAVE_AIR, CAVE_AIR, false);
            this.generateMaybeBox(â˜ƒ, â˜ƒ, â˜ƒ, 0.8F, 0, 2, 0, 2, 2, â˜ƒxxxx, CAVE_AIR, CAVE_AIR, false, false);
            if (this.spiderCorridor) {
               this.generateMaybeBox(â˜ƒ, â˜ƒ, â˜ƒ, 0.6F, 0, 0, 0, 2, 1, â˜ƒxxxx, Blocks.COBWEB.defaultBlockState(), CAVE_AIR, false, true);
            }

            for(int â˜ƒ = 0; â˜ƒ < this.numSections; ++â˜ƒ) {
               int â˜ƒx = 2 + â˜ƒ * 5;
               this.placeSupport(â˜ƒ, â˜ƒ, 0, 0, â˜ƒx, 2, 2, â˜ƒ);
               this.maybePlaceCobWeb(â˜ƒ, â˜ƒ, â˜ƒ, 0.1F, 0, 2, â˜ƒx - 1);
               this.maybePlaceCobWeb(â˜ƒ, â˜ƒ, â˜ƒ, 0.1F, 2, 2, â˜ƒx - 1);
               this.maybePlaceCobWeb(â˜ƒ, â˜ƒ, â˜ƒ, 0.1F, 0, 2, â˜ƒx + 1);
               this.maybePlaceCobWeb(â˜ƒ, â˜ƒ, â˜ƒ, 0.1F, 2, 2, â˜ƒx + 1);
               this.maybePlaceCobWeb(â˜ƒ, â˜ƒ, â˜ƒ, 0.05F, 0, 2, â˜ƒx - 2);
               this.maybePlaceCobWeb(â˜ƒ, â˜ƒ, â˜ƒ, 0.05F, 2, 2, â˜ƒx - 2);
               this.maybePlaceCobWeb(â˜ƒ, â˜ƒ, â˜ƒ, 0.05F, 0, 2, â˜ƒx + 2);
               this.maybePlaceCobWeb(â˜ƒ, â˜ƒ, â˜ƒ, 0.05F, 2, 2, â˜ƒx + 2);
               if (â˜ƒ.nextInt(100) == 0) {
                  this.createChest(â˜ƒ, â˜ƒ, â˜ƒ, 2, 0, â˜ƒx - 1, BuiltInLootTables.ABANDONED_MINESHAFT);
               }

               if (â˜ƒ.nextInt(100) == 0) {
                  this.createChest(â˜ƒ, â˜ƒ, â˜ƒ, 0, 0, â˜ƒx + 1, BuiltInLootTables.ABANDONED_MINESHAFT);
               }

               if (this.spiderCorridor && !this.hasPlacedSpider) {
                  int â˜ƒx = 1;
                  int â˜ƒxx = â˜ƒx - 1 + â˜ƒ.nextInt(3);
                  BlockPos â˜ƒxxx = this.getWorldPos(1, 0, â˜ƒxx);
                  if (â˜ƒ.isInside(â˜ƒxxx) && this.isInterior(â˜ƒ, 1, 0, â˜ƒxx, â˜ƒ)) {
                     this.hasPlacedSpider = true;
                     â˜ƒ.setBlock(â˜ƒxxx, Blocks.SPAWNER.defaultBlockState(), 2);
                     BlockEntity â˜ƒxxxx = â˜ƒ.getBlockEntity(â˜ƒxxx);
                     if (â˜ƒxxxx instanceof SpawnerBlockEntity) {
                        ((SpawnerBlockEntity)â˜ƒxxxx).getSpawner().setEntityId(EntityType.CAVE_SPIDER);
                     }
                  }
               }
            }

            for(int â˜ƒ = 0; â˜ƒ <= 2; ++â˜ƒ) {
               for(int â˜ƒx = 0; â˜ƒx <= â˜ƒxxxx; ++â˜ƒx) {
                  this.setPlanksBlock(â˜ƒ, â˜ƒ, â˜ƒxxxxx, â˜ƒ, -1, â˜ƒx);
               }
            }

            int â˜ƒ = 2;
            this.placeDoubleLowerOrUpperSupport(â˜ƒ, â˜ƒ, 0, -1, 2);
            if (this.numSections > 1) {
               int â˜ƒx = â˜ƒxxxx - 2;
               this.placeDoubleLowerOrUpperSupport(â˜ƒ, â˜ƒ, 0, -1, â˜ƒx);
            }

            if (this.hasRails) {
               BlockState â˜ƒ = Blocks.RAIL.defaultBlockState().setValue(RailBlock.SHAPE, RailShape.NORTH_SOUTH);

               for(int â˜ƒx = 0; â˜ƒx <= â˜ƒxxxx; ++â˜ƒx) {
                  BlockState â˜ƒxx = this.getBlock(â˜ƒ, 1, -1, â˜ƒx, â˜ƒ);
                  if (!â˜ƒxx.isAir() && â˜ƒxx.isSolidRender(â˜ƒ, this.getWorldPos(1, -1, â˜ƒx))) {
                     float â˜ƒxxx = this.isInterior(â˜ƒ, 1, 0, â˜ƒx, â˜ƒ) ? 0.7F : 0.9F;
                     this.maybeGenerateBlock(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxx, 1, 0, â˜ƒx, â˜ƒ);
                  }
               }
            }

            return true;
         }
      }

      private void placeDoubleLowerOrUpperSupport(WorldGenLevel var1, BoundingBox var2, int var3, int var4, int var5) {
         BlockState â˜ƒ = this.type.getWoodState();
         BlockState â˜ƒx = this.type.getPlanksState();
         if (this.getBlock(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).is(â˜ƒx.getBlock())) {
            this.fillPillarDownOrChainUp(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }

         if (this.getBlock(â˜ƒ, â˜ƒ + 2, â˜ƒ, â˜ƒ, â˜ƒ).is(â˜ƒx.getBlock())) {
            this.fillPillarDownOrChainUp(â˜ƒ, â˜ƒ, â˜ƒ + 2, â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }

      @Override
      protected void fillColumnDown(WorldGenLevel var1, BlockState var2, int var3, int var4, int var5, BoundingBox var6) {
         BlockPos.MutableBlockPos â˜ƒ = this.getWorldPos(â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒ.isInside(â˜ƒ)) {
            int â˜ƒx = â˜ƒ.getY();

            while(this.isReplaceableByStructures(â˜ƒ.getBlockState(â˜ƒ)) && â˜ƒ.getY() > â˜ƒ.getMinBuildHeight() + 1) {
               â˜ƒ.move(Direction.DOWN);
            }

            if (this.canPlaceColumnOnTopOf(â˜ƒ.getBlockState(â˜ƒ))) {
               while(â˜ƒ.getY() < â˜ƒx) {
                  â˜ƒ.move(Direction.UP);
                  â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 2);
               }
            }
         }
      }

      protected void fillPillarDownOrChainUp(WorldGenLevel var1, BlockState var2, int var3, int var4, int var5, BoundingBox var6) {
         BlockPos.MutableBlockPos â˜ƒ = this.getWorldPos(â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒ.isInside(â˜ƒ)) {
            int â˜ƒx = â˜ƒ.getY();
            int â˜ƒxx = 1;
            boolean â˜ƒxxx = true;

            for(boolean â˜ƒxxxx = true; â˜ƒxxx || â˜ƒxxxx; ++â˜ƒxx) {
               if (â˜ƒxxx) {
                  â˜ƒ.setY(â˜ƒx - â˜ƒxx);
                  BlockState â˜ƒxxxxx = â˜ƒ.getBlockState(â˜ƒ);
                  boolean â˜ƒxxxxxx = this.isReplaceableByStructures(â˜ƒxxxxx) && !â˜ƒxxxxx.is(Blocks.LAVA);
                  if (!â˜ƒxxxxxx && this.canPlaceColumnOnTopOf(â˜ƒxxxxx)) {
                     fillColumnBetween(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx - â˜ƒxx + 1, â˜ƒx);
                     return;
                  }

                  â˜ƒxxx = â˜ƒxx <= 20 && â˜ƒxxxxxx && â˜ƒ.getY() > â˜ƒ.getMinBuildHeight() + 1;
               }

               if (â˜ƒxxxx) {
                  â˜ƒ.setY(â˜ƒx + â˜ƒxx);
                  BlockState â˜ƒxxxxx = â˜ƒ.getBlockState(â˜ƒ);
                  boolean â˜ƒxxxxxx = this.isReplaceableByStructures(â˜ƒxxxxx);
                  if (!â˜ƒxxxxxx && this.canHangChainBelow(â˜ƒ, â˜ƒ, â˜ƒxxxxx)) {
                     â˜ƒ.setBlock(â˜ƒ.setY(â˜ƒx + 1), this.type.getFenceState(), 2);
                     fillColumnBetween(â˜ƒ, Blocks.CHAIN.defaultBlockState(), â˜ƒ, â˜ƒx + 2, â˜ƒx + â˜ƒxx);
                     return;
                  }

                  â˜ƒxxxx = â˜ƒxx <= 50 && â˜ƒxxxxxx && â˜ƒ.getY() < â˜ƒ.getMaxBuildHeight() - 1;
               }
            }
         }
      }

      private static void fillColumnBetween(WorldGenLevel var0, BlockState var1, BlockPos.MutableBlockPos var2, int var3, int var4) {
         for(int â˜ƒ = â˜ƒ; â˜ƒ < â˜ƒ; ++â˜ƒ) {
            â˜ƒ.setBlock(â˜ƒ.setY(â˜ƒ), â˜ƒ, 2);
         }
      }

      private boolean canPlaceColumnOnTopOf(BlockState var1) {
         return !â˜ƒ.is(Blocks.RAIL) && !â˜ƒ.is(Blocks.LAVA);
      }

      private boolean canHangChainBelow(LevelReader var1, BlockPos var2, BlockState var3) {
         return Block.canSupportCenter(â˜ƒ, â˜ƒ, Direction.DOWN) && !(â˜ƒ.getBlock() instanceof FallingBlock);
      }

      private void placeSupport(WorldGenLevel var1, BoundingBox var2, int var3, int var4, int var5, int var6, int var7, Random var8) {
         if (this.isSupportingBox(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
            BlockState â˜ƒ = this.type.getPlanksState();
            BlockState â˜ƒx = this.type.getFenceState();
            this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ - 1, â˜ƒ, â˜ƒx.setValue(FenceBlock.WEST, Boolean.valueOf(true)), CAVE_AIR, false);
            this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ - 1, â˜ƒ, â˜ƒx.setValue(FenceBlock.EAST, Boolean.valueOf(true)), CAVE_AIR, false);
            if (â˜ƒ.nextInt(4) == 0) {
               this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, CAVE_AIR, false);
               this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, CAVE_AIR, false);
            } else {
               this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, CAVE_AIR, false);
               this.maybeGenerateBlock(
                  â˜ƒ, â˜ƒ, â˜ƒ, 0.05F, â˜ƒ + 1, â˜ƒ, â˜ƒ - 1, Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, Direction.NORTH)
               );
               this.maybeGenerateBlock(
                  â˜ƒ, â˜ƒ, â˜ƒ, 0.05F, â˜ƒ + 1, â˜ƒ, â˜ƒ + 1, Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, Direction.SOUTH)
               );
            }
         }
      }

      private void maybePlaceCobWeb(WorldGenLevel var1, BoundingBox var2, Random var3, float var4, int var5, int var6, int var7) {
         if (this.isInterior(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) && â˜ƒ.nextFloat() < â˜ƒ && this.hasSturdyNeighbours(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 2)) {
            this.placeBlock(â˜ƒ, Blocks.COBWEB.defaultBlockState(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }

      private boolean hasSturdyNeighbours(WorldGenLevel var1, BoundingBox var2, int var3, int var4, int var5, int var6) {
         BlockPos.MutableBlockPos â˜ƒ = this.getWorldPos(â˜ƒ, â˜ƒ, â˜ƒ);
         int â˜ƒx = 0;

         for(Direction â˜ƒxx : Direction.values()) {
            â˜ƒ.move(â˜ƒxx);
            if (â˜ƒ.isInside(â˜ƒ) && â˜ƒ.getBlockState(â˜ƒ).isFaceSturdy(â˜ƒ, â˜ƒ, â˜ƒxx.getOpposite())) {
               if (++â˜ƒx >= â˜ƒ) {
                  return true;
               }
            }

            â˜ƒ.move(â˜ƒxx.getOpposite());
         }

         return false;
      }
   }

   public static class MineShaftCrossing extends MineShaftPieces.MineShaftPiece {
      private final Direction direction;
      private final boolean isTwoFloored;

      public MineShaftCrossing(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.MINE_SHAFT_CROSSING, â˜ƒ);
         this.isTwoFloored = â˜ƒ.getBoolean("tf");
         this.direction = Direction.from2DDataValue(â˜ƒ.getInt("D"));
      }

      @Override
      protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
         super.addAdditionalSaveData(â˜ƒ, â˜ƒ);
         â˜ƒ.putBoolean("tf", this.isTwoFloored);
         â˜ƒ.putInt("D", this.direction.get2DDataValue());
      }

      public MineShaftCrossing(int var1, BoundingBox var2, @Nullable Direction var3, MineshaftFeature.Type var4) {
         super(StructurePieceType.MINE_SHAFT_CROSSING, â˜ƒ, â˜ƒ, â˜ƒ);
         this.direction = â˜ƒ;
         this.isTwoFloored = â˜ƒ.getYSpan() > 3;
      }

      @Nullable
      public static BoundingBox findCrossing(StructurePieceAccessor var0, Random var1, int var2, int var3, int var4, Direction var5) {
         int â˜ƒ;
         if (â˜ƒ.nextInt(4) == 0) {
            â˜ƒ = 6;
         } else {
            â˜ƒ = 2;
         }
         BoundingBox var7 = switch(â˜ƒ) {
            default -> new BoundingBox(-1, 0, -4, 3, â˜ƒ, 0);
            case SOUTH -> new BoundingBox(-1, 0, 0, 3, â˜ƒ, 4);
            case WEST -> new BoundingBox(-4, 0, -1, 0, â˜ƒ, 3);
            case EAST -> new BoundingBox(0, 0, -1, 4, â˜ƒ, 3);
         };
         var7.move(â˜ƒ, â˜ƒ, â˜ƒ);
         return â˜ƒ.findCollisionPiece(var7) != null ? null : var7;
      }

      @Override
      public void addChildren(StructurePiece var1, StructurePieceAccessor var2, Random var3) {
         int â˜ƒ = this.getGenDepth();
         switch(this.direction) {
            case NORTH:
            default:
               MineShaftPieces.generateAndAddPiece(
                  â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX() + 1, this.boundingBox.minY(), this.boundingBox.minZ() - 1, Direction.NORTH, â˜ƒ
               );
               MineShaftPieces.generateAndAddPiece(
                  â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX() - 1, this.boundingBox.minY(), this.boundingBox.minZ() + 1, Direction.WEST, â˜ƒ
               );
               MineShaftPieces.generateAndAddPiece(
                  â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.maxX() + 1, this.boundingBox.minY(), this.boundingBox.minZ() + 1, Direction.EAST, â˜ƒ
               );
               break;
            case SOUTH:
               MineShaftPieces.generateAndAddPiece(
                  â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX() + 1, this.boundingBox.minY(), this.boundingBox.maxZ() + 1, Direction.SOUTH, â˜ƒ
               );
               MineShaftPieces.generateAndAddPiece(
                  â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX() - 1, this.boundingBox.minY(), this.boundingBox.minZ() + 1, Direction.WEST, â˜ƒ
               );
               MineShaftPieces.generateAndAddPiece(
                  â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.maxX() + 1, this.boundingBox.minY(), this.boundingBox.minZ() + 1, Direction.EAST, â˜ƒ
               );
               break;
            case WEST:
               MineShaftPieces.generateAndAddPiece(
                  â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX() + 1, this.boundingBox.minY(), this.boundingBox.minZ() - 1, Direction.NORTH, â˜ƒ
               );
               MineShaftPieces.generateAndAddPiece(
                  â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX() + 1, this.boundingBox.minY(), this.boundingBox.maxZ() + 1, Direction.SOUTH, â˜ƒ
               );
               MineShaftPieces.generateAndAddPiece(
                  â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX() - 1, this.boundingBox.minY(), this.boundingBox.minZ() + 1, Direction.WEST, â˜ƒ
               );
               break;
            case EAST:
               MineShaftPieces.generateAndAddPiece(
                  â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX() + 1, this.boundingBox.minY(), this.boundingBox.minZ() - 1, Direction.NORTH, â˜ƒ
               );
               MineShaftPieces.generateAndAddPiece(
                  â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX() + 1, this.boundingBox.minY(), this.boundingBox.maxZ() + 1, Direction.SOUTH, â˜ƒ
               );
               MineShaftPieces.generateAndAddPiece(
                  â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.maxX() + 1, this.boundingBox.minY(), this.boundingBox.minZ() + 1, Direction.EAST, â˜ƒ
               );
         }

         if (this.isTwoFloored) {
            if (â˜ƒ.nextBoolean()) {
               MineShaftPieces.generateAndAddPiece(
                  â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX() + 1, this.boundingBox.minY() + 3 + 1, this.boundingBox.minZ() - 1, Direction.NORTH, â˜ƒ
               );
            }

            if (â˜ƒ.nextBoolean()) {
               MineShaftPieces.generateAndAddPiece(
                  â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX() - 1, this.boundingBox.minY() + 3 + 1, this.boundingBox.minZ() + 1, Direction.WEST, â˜ƒ
               );
            }

            if (â˜ƒ.nextBoolean()) {
               MineShaftPieces.generateAndAddPiece(
                  â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.maxX() + 1, this.boundingBox.minY() + 3 + 1, this.boundingBox.minZ() + 1, Direction.EAST, â˜ƒ
               );
            }

            if (â˜ƒ.nextBoolean()) {
               MineShaftPieces.generateAndAddPiece(
                  â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX() + 1, this.boundingBox.minY() + 3 + 1, this.boundingBox.maxZ() + 1, Direction.SOUTH, â˜ƒ
               );
            }
         }
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         if (this.edgesLiquid(â˜ƒ, â˜ƒ)) {
            return false;
         } else {
            BlockState â˜ƒ = this.type.getPlanksState();
            if (this.isTwoFloored) {
               this.generateBox(
                  â˜ƒ,
                  â˜ƒ,
                  this.boundingBox.minX() + 1,
                  this.boundingBox.minY(),
                  this.boundingBox.minZ(),
                  this.boundingBox.maxX() - 1,
                  this.boundingBox.minY() + 3 - 1,
                  this.boundingBox.maxZ(),
                  CAVE_AIR,
                  CAVE_AIR,
                  false
               );
               this.generateBox(
                  â˜ƒ,
                  â˜ƒ,
                  this.boundingBox.minX(),
                  this.boundingBox.minY(),
                  this.boundingBox.minZ() + 1,
                  this.boundingBox.maxX(),
                  this.boundingBox.minY() + 3 - 1,
                  this.boundingBox.maxZ() - 1,
                  CAVE_AIR,
                  CAVE_AIR,
                  false
               );
               this.generateBox(
                  â˜ƒ,
                  â˜ƒ,
                  this.boundingBox.minX() + 1,
                  this.boundingBox.maxY() - 2,
                  this.boundingBox.minZ(),
                  this.boundingBox.maxX() - 1,
                  this.boundingBox.maxY(),
                  this.boundingBox.maxZ(),
                  CAVE_AIR,
                  CAVE_AIR,
                  false
               );
               this.generateBox(
                  â˜ƒ,
                  â˜ƒ,
                  this.boundingBox.minX(),
                  this.boundingBox.maxY() - 2,
                  this.boundingBox.minZ() + 1,
                  this.boundingBox.maxX(),
                  this.boundingBox.maxY(),
                  this.boundingBox.maxZ() - 1,
                  CAVE_AIR,
                  CAVE_AIR,
                  false
               );
               this.generateBox(
                  â˜ƒ,
                  â˜ƒ,
                  this.boundingBox.minX() + 1,
                  this.boundingBox.minY() + 3,
                  this.boundingBox.minZ() + 1,
                  this.boundingBox.maxX() - 1,
                  this.boundingBox.minY() + 3,
                  this.boundingBox.maxZ() - 1,
                  CAVE_AIR,
                  CAVE_AIR,
                  false
               );
            } else {
               this.generateBox(
                  â˜ƒ,
                  â˜ƒ,
                  this.boundingBox.minX() + 1,
                  this.boundingBox.minY(),
                  this.boundingBox.minZ(),
                  this.boundingBox.maxX() - 1,
                  this.boundingBox.maxY(),
                  this.boundingBox.maxZ(),
                  CAVE_AIR,
                  CAVE_AIR,
                  false
               );
               this.generateBox(
                  â˜ƒ,
                  â˜ƒ,
                  this.boundingBox.minX(),
                  this.boundingBox.minY(),
                  this.boundingBox.minZ() + 1,
                  this.boundingBox.maxX(),
                  this.boundingBox.maxY(),
                  this.boundingBox.maxZ() - 1,
                  CAVE_AIR,
                  CAVE_AIR,
                  false
               );
            }

            this.placeSupportPillar(â˜ƒ, â˜ƒ, this.boundingBox.minX() + 1, this.boundingBox.minY(), this.boundingBox.minZ() + 1, this.boundingBox.maxY());
            this.placeSupportPillar(â˜ƒ, â˜ƒ, this.boundingBox.minX() + 1, this.boundingBox.minY(), this.boundingBox.maxZ() - 1, this.boundingBox.maxY());
            this.placeSupportPillar(â˜ƒ, â˜ƒ, this.boundingBox.maxX() - 1, this.boundingBox.minY(), this.boundingBox.minZ() + 1, this.boundingBox.maxY());
            this.placeSupportPillar(â˜ƒ, â˜ƒ, this.boundingBox.maxX() - 1, this.boundingBox.minY(), this.boundingBox.maxZ() - 1, this.boundingBox.maxY());
            int â˜ƒ = this.boundingBox.minY() - 1;

            for(int â˜ƒx = this.boundingBox.minX(); â˜ƒx <= this.boundingBox.maxX(); ++â˜ƒx) {
               for(int â˜ƒxx = this.boundingBox.minZ(); â˜ƒxx <= this.boundingBox.maxZ(); ++â˜ƒxx) {
                  this.setPlanksBlock(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒxx);
               }
            }

            return true;
         }
      }

      private void placeSupportPillar(WorldGenLevel var1, BoundingBox var2, int var3, int var4, int var5, int var6) {
         if (!this.getBlock(â˜ƒ, â˜ƒ, â˜ƒ + 1, â˜ƒ, â˜ƒ).isAir()) {
            this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.type.getPlanksState(), CAVE_AIR, false);
         }
      }
   }

   abstract static class MineShaftPiece extends StructurePiece {
      protected MineshaftFeature.Type type;

      public MineShaftPiece(StructurePieceType var1, int var2, MineshaftFeature.Type var3, BoundingBox var4) {
         super(â˜ƒ, â˜ƒ, â˜ƒ);
         this.type = â˜ƒ;
      }

      public MineShaftPiece(StructurePieceType var1, CompoundTag var2) {
         super(â˜ƒ, â˜ƒ);
         this.type = MineshaftFeature.Type.byId(â˜ƒ.getInt("MST"));
      }

      @Override
      protected boolean canBeReplaced(LevelReader var1, int var2, int var3, int var4, BoundingBox var5) {
         BlockState â˜ƒ = this.getBlock(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         return !â˜ƒ.is(this.type.getPlanksState().getBlock())
            && !â˜ƒ.is(this.type.getWoodState().getBlock())
            && !â˜ƒ.is(this.type.getFenceState().getBlock())
            && !â˜ƒ.is(Blocks.CHAIN);
      }

      @Override
      protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
         â˜ƒ.putInt("MST", this.type.ordinal());
      }

      protected boolean isSupportingBox(BlockGetter var1, BoundingBox var2, int var3, int var4, int var5, int var6) {
         for(int â˜ƒ = â˜ƒ; â˜ƒ <= â˜ƒ; ++â˜ƒ) {
            if (this.getBlock(â˜ƒ, â˜ƒ, â˜ƒ + 1, â˜ƒ, â˜ƒ).isAir()) {
               return false;
            }
         }

         return true;
      }

      protected boolean edgesLiquid(BlockGetter var1, BoundingBox var2) {
         int â˜ƒ = Math.max(this.boundingBox.minX() - 1, â˜ƒ.minX());
         int â˜ƒx = Math.max(this.boundingBox.minY() - 1, â˜ƒ.minY());
         int â˜ƒxx = Math.max(this.boundingBox.minZ() - 1, â˜ƒ.minZ());
         int â˜ƒxxx = Math.min(this.boundingBox.maxX() + 1, â˜ƒ.maxX());
         int â˜ƒxxxx = Math.min(this.boundingBox.maxY() + 1, â˜ƒ.maxY());
         int â˜ƒxxxxx = Math.min(this.boundingBox.maxZ() + 1, â˜ƒ.maxZ());
         BlockPos.MutableBlockPos â˜ƒxxxxxx = new BlockPos.MutableBlockPos();

         for(int â˜ƒxxxxxxx = â˜ƒ; â˜ƒxxxxxxx <= â˜ƒxxx; ++â˜ƒxxxxxxx) {
            for(int â˜ƒxxxxxxxx = â˜ƒxx; â˜ƒxxxxxxxx <= â˜ƒxxxxx; ++â˜ƒxxxxxxxx) {
               if (â˜ƒ.getBlockState(â˜ƒxxxxxx.set(â˜ƒxxxxxxx, â˜ƒx, â˜ƒxxxxxxxx)).getMaterial().isLiquid()) {
                  return true;
               }

               if (â˜ƒ.getBlockState(â˜ƒxxxxxx.set(â˜ƒxxxxxxx, â˜ƒxxxx, â˜ƒxxxxxxxx)).getMaterial().isLiquid()) {
                  return true;
               }
            }
         }

         for(int â˜ƒxxxxxxx = â˜ƒ; â˜ƒxxxxxxx <= â˜ƒxxx; ++â˜ƒxxxxxxx) {
            for(int â˜ƒxxxxxxxx = â˜ƒx; â˜ƒxxxxxxxx <= â˜ƒxxxx; ++â˜ƒxxxxxxxx) {
               if (â˜ƒ.getBlockState(â˜ƒxxxxxx.set(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxx)).getMaterial().isLiquid()) {
                  return true;
               }

               if (â˜ƒ.getBlockState(â˜ƒxxxxxx.set(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxx)).getMaterial().isLiquid()) {
                  return true;
               }
            }
         }

         for(int â˜ƒxxxxxxx = â˜ƒxx; â˜ƒxxxxxxx <= â˜ƒxxxxx; ++â˜ƒxxxxxxx) {
            for(int â˜ƒxxxxxxxx = â˜ƒx; â˜ƒxxxxxxxx <= â˜ƒxxxx; ++â˜ƒxxxxxxxx) {
               if (â˜ƒ.getBlockState(â˜ƒxxxxxx.set(â˜ƒ, â˜ƒxxxxxxxx, â˜ƒxxxxxxx)).getMaterial().isLiquid()) {
                  return true;
               }

               if (â˜ƒ.getBlockState(â˜ƒxxxxxx.set(â˜ƒxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxx)).getMaterial().isLiquid()) {
                  return true;
               }
            }
         }

         return false;
      }

      protected void setPlanksBlock(WorldGenLevel var1, BoundingBox var2, BlockState var3, int var4, int var5, int var6) {
         if (this.isInterior(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
            BlockPos â˜ƒ = this.getWorldPos(â˜ƒ, â˜ƒ, â˜ƒ);
            BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
            if (â˜ƒx.isAir() || â˜ƒx.is(Blocks.CHAIN)) {
               â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 2);
            }
         }
      }
   }

   public static class MineShaftRoom extends MineShaftPieces.MineShaftPiece {
      private final List<BoundingBox> childEntranceBoxes = Lists.<BoundingBox>newLinkedList();

      public MineShaftRoom(int var1, Random var2, int var3, int var4, MineshaftFeature.Type var5) {
         super(
            StructurePieceType.MINE_SHAFT_ROOM,
            â˜ƒ,
            â˜ƒ,
            new BoundingBox(â˜ƒ, 50, â˜ƒ, â˜ƒ + 7 + â˜ƒ.nextInt(6), 54 + â˜ƒ.nextInt(6), â˜ƒ + 7 + â˜ƒ.nextInt(6))
         );
         this.type = â˜ƒ;
      }

      public MineShaftRoom(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.MINE_SHAFT_ROOM, â˜ƒ);
         BoundingBox.CODEC
            .listOf()
            .parse(NbtOps.INSTANCE, â˜ƒ.getList("Entrances", 11))
            .resultOrPartial(MineShaftPieces.LOGGER::error)
            .ifPresent(this.childEntranceBoxes::addAll);
      }

      @Override
      public void addChildren(StructurePiece var1, StructurePieceAccessor var2, Random var3) {
         int â˜ƒ = this.getGenDepth();
         int â˜ƒx = this.boundingBox.getYSpan() - 3 - 1;
         if (â˜ƒx <= 0) {
            â˜ƒx = 1;
         }

         int â˜ƒ;
         for(â˜ƒ = 0; â˜ƒ < this.boundingBox.getXSpan(); â˜ƒ += 4) {
            â˜ƒ += â˜ƒ.nextInt(this.boundingBox.getXSpan());
            if (â˜ƒ + 3 > this.boundingBox.getXSpan()) {
               break;
            }

            MineShaftPieces.MineShaftPiece â˜ƒ = MineShaftPieces.generateAndAddPiece(
               â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX() + â˜ƒ, this.boundingBox.minY() + â˜ƒ.nextInt(â˜ƒx) + 1, this.boundingBox.minZ() - 1, Direction.NORTH, â˜ƒ
            );
            if (â˜ƒ != null) {
               BoundingBox â˜ƒx = â˜ƒ.getBoundingBox();
               this.childEntranceBoxes
                  .add(new BoundingBox(â˜ƒx.minX(), â˜ƒx.minY(), this.boundingBox.minZ(), â˜ƒx.maxX(), â˜ƒx.maxY(), this.boundingBox.minZ() + 1));
            }
         }

         for(â˜ƒ = 0; â˜ƒ < this.boundingBox.getXSpan(); â˜ƒ += 4) {
            â˜ƒ += â˜ƒ.nextInt(this.boundingBox.getXSpan());
            if (â˜ƒ + 3 > this.boundingBox.getXSpan()) {
               break;
            }

            MineShaftPieces.MineShaftPiece â˜ƒ = MineShaftPieces.generateAndAddPiece(
               â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX() + â˜ƒ, this.boundingBox.minY() + â˜ƒ.nextInt(â˜ƒx) + 1, this.boundingBox.maxZ() + 1, Direction.SOUTH, â˜ƒ
            );
            if (â˜ƒ != null) {
               BoundingBox â˜ƒx = â˜ƒ.getBoundingBox();
               this.childEntranceBoxes
                  .add(new BoundingBox(â˜ƒx.minX(), â˜ƒx.minY(), this.boundingBox.maxZ() - 1, â˜ƒx.maxX(), â˜ƒx.maxY(), this.boundingBox.maxZ()));
            }
         }

         for(â˜ƒ = 0; â˜ƒ < this.boundingBox.getZSpan(); â˜ƒ += 4) {
            â˜ƒ += â˜ƒ.nextInt(this.boundingBox.getZSpan());
            if (â˜ƒ + 3 > this.boundingBox.getZSpan()) {
               break;
            }

            MineShaftPieces.MineShaftPiece â˜ƒ = MineShaftPieces.generateAndAddPiece(
               â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX() - 1, this.boundingBox.minY() + â˜ƒ.nextInt(â˜ƒx) + 1, this.boundingBox.minZ() + â˜ƒ, Direction.WEST, â˜ƒ
            );
            if (â˜ƒ != null) {
               BoundingBox â˜ƒx = â˜ƒ.getBoundingBox();
               this.childEntranceBoxes
                  .add(new BoundingBox(this.boundingBox.minX(), â˜ƒx.minY(), â˜ƒx.minZ(), this.boundingBox.minX() + 1, â˜ƒx.maxY(), â˜ƒx.maxZ()));
            }
         }

         for(â˜ƒ = 0; â˜ƒ < this.boundingBox.getZSpan(); â˜ƒ += 4) {
            â˜ƒ += â˜ƒ.nextInt(this.boundingBox.getZSpan());
            if (â˜ƒ + 3 > this.boundingBox.getZSpan()) {
               break;
            }

            StructurePiece â˜ƒ = MineShaftPieces.generateAndAddPiece(
               â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.maxX() + 1, this.boundingBox.minY() + â˜ƒ.nextInt(â˜ƒx) + 1, this.boundingBox.minZ() + â˜ƒ, Direction.EAST, â˜ƒ
            );
            if (â˜ƒ != null) {
               BoundingBox â˜ƒx = â˜ƒ.getBoundingBox();
               this.childEntranceBoxes
                  .add(new BoundingBox(this.boundingBox.maxX() - 1, â˜ƒx.minY(), â˜ƒx.minZ(), this.boundingBox.maxX(), â˜ƒx.maxY(), â˜ƒx.maxZ()));
            }
         }
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         if (this.edgesLiquid(â˜ƒ, â˜ƒ)) {
            return false;
         } else {
            this.generateBox(
               â˜ƒ,
               â˜ƒ,
               this.boundingBox.minX(),
               this.boundingBox.minY(),
               this.boundingBox.minZ(),
               this.boundingBox.maxX(),
               this.boundingBox.minY(),
               this.boundingBox.maxZ(),
               Blocks.DIRT.defaultBlockState(),
               CAVE_AIR,
               true
            );
            this.generateBox(
               â˜ƒ,
               â˜ƒ,
               this.boundingBox.minX(),
               this.boundingBox.minY() + 1,
               this.boundingBox.minZ(),
               this.boundingBox.maxX(),
               Math.min(this.boundingBox.minY() + 3, this.boundingBox.maxY()),
               this.boundingBox.maxZ(),
               CAVE_AIR,
               CAVE_AIR,
               false
            );

            for(BoundingBox â˜ƒ : this.childEntranceBoxes) {
               this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ.minX(), â˜ƒ.maxY() - 2, â˜ƒ.minZ(), â˜ƒ.maxX(), â˜ƒ.maxY(), â˜ƒ.maxZ(), CAVE_AIR, CAVE_AIR, false);
            }

            this.generateUpperHalfSphere(
               â˜ƒ,
               â˜ƒ,
               this.boundingBox.minX(),
               this.boundingBox.minY() + 4,
               this.boundingBox.minZ(),
               this.boundingBox.maxX(),
               this.boundingBox.maxY(),
               this.boundingBox.maxZ(),
               CAVE_AIR,
               false
            );
            return true;
         }
      }

      @Override
      public void move(int var1, int var2, int var3) {
         super.move(â˜ƒ, â˜ƒ, â˜ƒ);

         for(BoundingBox â˜ƒ : this.childEntranceBoxes) {
            â˜ƒ.move(â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }

      @Override
      protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
         super.addAdditionalSaveData(â˜ƒ, â˜ƒ);
         BoundingBox.CODEC
            .listOf()
            .encodeStart(NbtOps.INSTANCE, this.childEntranceBoxes)
            .resultOrPartial(MineShaftPieces.LOGGER::error)
            .ifPresent(var1x -> â˜ƒ.put("Entrances", var1x));
      }
   }

   public static class MineShaftStairs extends MineShaftPieces.MineShaftPiece {
      public MineShaftStairs(int var1, BoundingBox var2, Direction var3, MineshaftFeature.Type var4) {
         super(StructurePieceType.MINE_SHAFT_STAIRS, â˜ƒ, â˜ƒ, â˜ƒ);
         this.setOrientation(â˜ƒ);
      }

      public MineShaftStairs(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.MINE_SHAFT_STAIRS, â˜ƒ);
      }

      @Nullable
      public static BoundingBox findStairs(StructurePieceAccessor var0, Random var1, int var2, int var3, int var4, Direction var5) {
         BoundingBox var6 = switch(â˜ƒ) {
            default -> new BoundingBox(0, -5, -8, 2, 2, 0);
            case SOUTH -> new BoundingBox(0, -5, 0, 2, 2, 8);
            case WEST -> new BoundingBox(-8, -5, 0, 0, 2, 2);
            case EAST -> new BoundingBox(0, -5, 0, 8, 2, 2);
         };
         var6.move(â˜ƒ, â˜ƒ, â˜ƒ);
         return â˜ƒ.findCollisionPiece(var6) != null ? null : var6;
      }

      @Override
      public void addChildren(StructurePiece var1, StructurePieceAccessor var2, Random var3) {
         int â˜ƒ = this.getGenDepth();
         Direction â˜ƒx = this.getOrientation();
         if (â˜ƒx != null) {
            switch(â˜ƒx) {
               case NORTH:
               default:
                  MineShaftPieces.generateAndAddPiece(
                     â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX(), this.boundingBox.minY(), this.boundingBox.minZ() - 1, Direction.NORTH, â˜ƒ
                  );
                  break;
               case SOUTH:
                  MineShaftPieces.generateAndAddPiece(
                     â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX(), this.boundingBox.minY(), this.boundingBox.maxZ() + 1, Direction.SOUTH, â˜ƒ
                  );
                  break;
               case WEST:
                  MineShaftPieces.generateAndAddPiece(
                     â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX() - 1, this.boundingBox.minY(), this.boundingBox.minZ(), Direction.WEST, â˜ƒ
                  );
                  break;
               case EAST:
                  MineShaftPieces.generateAndAddPiece(
                     â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.maxX() + 1, this.boundingBox.minY(), this.boundingBox.minZ(), Direction.EAST, â˜ƒ
                  );
            }
         }
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         if (this.edgesLiquid(â˜ƒ, â˜ƒ)) {
            return false;
         } else {
            this.generateBox(â˜ƒ, â˜ƒ, 0, 5, 0, 2, 7, 1, CAVE_AIR, CAVE_AIR, false);
            this.generateBox(â˜ƒ, â˜ƒ, 0, 0, 7, 2, 2, 8, CAVE_AIR, CAVE_AIR, false);

            for(int â˜ƒ = 0; â˜ƒ < 5; ++â˜ƒ) {
               this.generateBox(â˜ƒ, â˜ƒ, 0, 5 - â˜ƒ - (â˜ƒ < 4 ? 1 : 0), 2 + â˜ƒ, 2, 7 - â˜ƒ, 2 + â˜ƒ, CAVE_AIR, CAVE_AIR, false);
            }

            return true;
         }
      }
   }
}
