package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.NoiseEffect;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class StrongholdPieces {
   private static final int SMALL_DOOR_WIDTH = 3;
   private static final int SMALL_DOOR_HEIGHT = 3;
   private static final int MAX_DEPTH = 50;
   private static final int LOWEST_Y_POSITION = 10;
   private static final boolean CHECK_AIR = true;
   private static final StrongholdPieces.PieceWeight[] STRONGHOLD_PIECE_WEIGHTS = new StrongholdPieces.PieceWeight[]{
      new StrongholdPieces.PieceWeight(StrongholdPieces.Straight.class, 40, 0),
      new StrongholdPieces.PieceWeight(StrongholdPieces.PrisonHall.class, 5, 5),
      new StrongholdPieces.PieceWeight(StrongholdPieces.LeftTurn.class, 20, 0),
      new StrongholdPieces.PieceWeight(StrongholdPieces.RightTurn.class, 20, 0),
      new StrongholdPieces.PieceWeight(StrongholdPieces.RoomCrossing.class, 10, 6),
      new StrongholdPieces.PieceWeight(StrongholdPieces.StraightStairsDown.class, 5, 5),
      new StrongholdPieces.PieceWeight(StrongholdPieces.StairsDown.class, 5, 5),
      new StrongholdPieces.PieceWeight(StrongholdPieces.FiveCrossing.class, 5, 4),
      new StrongholdPieces.PieceWeight(StrongholdPieces.ChestCorridor.class, 5, 4),
      new StrongholdPieces.PieceWeight(StrongholdPieces.Library.class, 10, 2) {
         @Override
         public boolean doPlace(int var1) {
            return super.doPlace(â˜ƒ) && â˜ƒ > 4;
         }
      },
      new StrongholdPieces.PieceWeight(StrongholdPieces.PortalRoom.class, 20, 1) {
         @Override
         public boolean doPlace(int var1) {
            return super.doPlace(â˜ƒ) && â˜ƒ > 5;
         }
      }
   };
   private static List<StrongholdPieces.PieceWeight> currentPieces;
   static Class<? extends StrongholdPieces.StrongholdPiece> imposedPiece;
   private static int totalWeight;
   static final StrongholdPieces.SmoothStoneSelector SMOOTH_STONE_SELECTOR = new StrongholdPieces.SmoothStoneSelector();

   public static void resetPieces() {
      currentPieces = Lists.<StrongholdPieces.PieceWeight>newArrayList();

      for(StrongholdPieces.PieceWeight â˜ƒ : STRONGHOLD_PIECE_WEIGHTS) {
         â˜ƒ.placeCount = 0;
         currentPieces.add(â˜ƒ);
      }

      imposedPiece = null;
   }

   private static boolean updatePieceWeight() {
      boolean â˜ƒ = false;
      totalWeight = 0;

      for(StrongholdPieces.PieceWeight â˜ƒx : currentPieces) {
         if (â˜ƒx.maxPlaceCount > 0 && â˜ƒx.placeCount < â˜ƒx.maxPlaceCount) {
            â˜ƒ = true;
         }

         totalWeight += â˜ƒx.weight;
      }

      return â˜ƒ;
   }

   private static StrongholdPieces.StrongholdPiece findAndCreatePieceFactory(
      Class<? extends StrongholdPieces.StrongholdPiece> var0,
      StructurePieceAccessor var1,
      Random var2,
      int var3,
      int var4,
      int var5,
      @Nullable Direction var6,
      int var7
   ) {
      StrongholdPieces.StrongholdPiece â˜ƒ = null;
      if (â˜ƒ == StrongholdPieces.Straight.class) {
         â˜ƒ = StrongholdPieces.Straight.createPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (â˜ƒ == StrongholdPieces.PrisonHall.class) {
         â˜ƒ = StrongholdPieces.PrisonHall.createPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (â˜ƒ == StrongholdPieces.LeftTurn.class) {
         â˜ƒ = StrongholdPieces.LeftTurn.createPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (â˜ƒ == StrongholdPieces.RightTurn.class) {
         â˜ƒ = StrongholdPieces.RightTurn.createPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (â˜ƒ == StrongholdPieces.RoomCrossing.class) {
         â˜ƒ = StrongholdPieces.RoomCrossing.createPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (â˜ƒ == StrongholdPieces.StraightStairsDown.class) {
         â˜ƒ = StrongholdPieces.StraightStairsDown.createPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (â˜ƒ == StrongholdPieces.StairsDown.class) {
         â˜ƒ = StrongholdPieces.StairsDown.createPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (â˜ƒ == StrongholdPieces.FiveCrossing.class) {
         â˜ƒ = StrongholdPieces.FiveCrossing.createPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (â˜ƒ == StrongholdPieces.ChestCorridor.class) {
         â˜ƒ = StrongholdPieces.ChestCorridor.createPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (â˜ƒ == StrongholdPieces.Library.class) {
         â˜ƒ = StrongholdPieces.Library.createPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (â˜ƒ == StrongholdPieces.PortalRoom.class) {
         â˜ƒ = StrongholdPieces.PortalRoom.createPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      return â˜ƒ;
   }

   private static StrongholdPieces.StrongholdPiece generatePieceFromSmallDoor(
      StrongholdPieces.StartPiece var0, StructurePieceAccessor var1, Random var2, int var3, int var4, int var5, Direction var6, int var7
   ) {
      if (!updatePieceWeight()) {
         return null;
      } else {
         if (imposedPiece != null) {
            StrongholdPieces.StrongholdPiece â˜ƒ = findAndCreatePieceFactory(imposedPiece, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
            imposedPiece = null;
            if (â˜ƒ != null) {
               return â˜ƒ;
            }
         }

         int â˜ƒ = 0;

         while(â˜ƒ < 5) {
            ++â˜ƒ;
            int â˜ƒx = â˜ƒ.nextInt(totalWeight);

            for(StrongholdPieces.PieceWeight â˜ƒxx : currentPieces) {
               â˜ƒx -= â˜ƒxx.weight;
               if (â˜ƒx < 0) {
                  if (!â˜ƒxx.doPlace(â˜ƒ) || â˜ƒxx == â˜ƒ.previousPiece) {
                     break;
                  }

                  StrongholdPieces.StrongholdPiece â˜ƒxxx = findAndCreatePieceFactory(â˜ƒxx.pieceClass, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
                  if (â˜ƒxxx != null) {
                     ++â˜ƒxx.placeCount;
                     â˜ƒ.previousPiece = â˜ƒxx;
                     if (!â˜ƒxx.isValid()) {
                        currentPieces.remove(â˜ƒxx);
                     }

                     return â˜ƒxxx;
                  }
               }
            }
         }

         BoundingBox â˜ƒx = StrongholdPieces.FillerCorridor.findPieceBox(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         return â˜ƒx != null && â˜ƒx.minY() > 1 ? new StrongholdPieces.FillerCorridor(â˜ƒ, â˜ƒx, â˜ƒ) : null;
      }
   }

   static StructurePiece generateAndAddPiece(
      StrongholdPieces.StartPiece var0, StructurePieceAccessor var1, Random var2, int var3, int var4, int var5, @Nullable Direction var6, int var7
   ) {
      if (â˜ƒ > 50) {
         return null;
      } else if (Math.abs(â˜ƒ - â˜ƒ.getBoundingBox().minX()) <= 112 && Math.abs(â˜ƒ - â˜ƒ.getBoundingBox().minZ()) <= 112) {
         StructurePiece â˜ƒ = generatePieceFromSmallDoor(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + 1);
         if (â˜ƒ != null) {
            â˜ƒ.addPiece(â˜ƒ);
            â˜ƒ.pendingChildren.add(â˜ƒ);
         }

         return â˜ƒ;
      } else {
         return null;
      }
   }

   public static class ChestCorridor extends StrongholdPieces.StrongholdPiece {
      private static final int WIDTH = 5;
      private static final int HEIGHT = 5;
      private static final int DEPTH = 7;
      private boolean hasPlacedChest;

      public ChestCorridor(int var1, Random var2, BoundingBox var3, Direction var4) {
         super(StructurePieceType.STRONGHOLD_CHEST_CORRIDOR, â˜ƒ, â˜ƒ);
         this.setOrientation(â˜ƒ);
         this.entryDoor = this.randomSmallDoor(â˜ƒ);
      }

      public ChestCorridor(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.STRONGHOLD_CHEST_CORRIDOR, â˜ƒ);
         this.hasPlacedChest = â˜ƒ.getBoolean("Chest");
      }

      @Override
      protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
         super.addAdditionalSaveData(â˜ƒ, â˜ƒ);
         â˜ƒ.putBoolean("Chest", this.hasPlacedChest);
      }

      @Override
      public void addChildren(StructurePiece var1, StructurePieceAccessor var2, Random var3) {
         this.generateSmallDoorChildForward((StrongholdPieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 1, 1);
      }

      public static StrongholdPieces.ChestCorridor createPiece(StructurePieceAccessor var0, Random var1, int var2, int var3, int var4, Direction var5, int var6) {
         BoundingBox â˜ƒ = BoundingBox.orientBox(â˜ƒ, â˜ƒ, â˜ƒ, -1, -1, 0, 5, 5, 7, â˜ƒ);
         return isOkBox(â˜ƒ) && â˜ƒ.findCollisionPiece(â˜ƒ) == null ? new StrongholdPieces.ChestCorridor(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) : null;
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         this.generateBox(â˜ƒ, â˜ƒ, 0, 0, 0, 4, 4, 6, true, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);
         this.generateSmallDoor(â˜ƒ, â˜ƒ, â˜ƒ, this.entryDoor, 1, 1, 0);
         this.generateSmallDoor(â˜ƒ, â˜ƒ, â˜ƒ, StrongholdPieces.StrongholdPiece.SmallDoorType.OPENING, 1, 1, 6);
         this.generateBox(â˜ƒ, â˜ƒ, 3, 1, 2, 3, 1, 4, Blocks.STONE_BRICKS.defaultBlockState(), Blocks.STONE_BRICKS.defaultBlockState(), false);
         this.placeBlock(â˜ƒ, Blocks.STONE_BRICK_SLAB.defaultBlockState(), 3, 1, 1, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.STONE_BRICK_SLAB.defaultBlockState(), 3, 1, 5, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.STONE_BRICK_SLAB.defaultBlockState(), 3, 2, 2, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.STONE_BRICK_SLAB.defaultBlockState(), 3, 2, 4, â˜ƒ);

         for(int â˜ƒ = 2; â˜ƒ <= 4; ++â˜ƒ) {
            this.placeBlock(â˜ƒ, Blocks.STONE_BRICK_SLAB.defaultBlockState(), 2, 1, â˜ƒ, â˜ƒ);
         }

         if (!this.hasPlacedChest && â˜ƒ.isInside(this.getWorldPos(3, 2, 3))) {
            this.hasPlacedChest = true;
            this.createChest(â˜ƒ, â˜ƒ, â˜ƒ, 3, 2, 3, BuiltInLootTables.STRONGHOLD_CORRIDOR);
         }

         return true;
      }
   }

   public static class FillerCorridor extends StrongholdPieces.StrongholdPiece {
      private final int steps;

      public FillerCorridor(int var1, BoundingBox var2, Direction var3) {
         super(StructurePieceType.STRONGHOLD_FILLER_CORRIDOR, â˜ƒ, â˜ƒ);
         this.setOrientation(â˜ƒ);
         this.steps = â˜ƒ != Direction.NORTH && â˜ƒ != Direction.SOUTH ? â˜ƒ.getXSpan() : â˜ƒ.getZSpan();
      }

      public FillerCorridor(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.STRONGHOLD_FILLER_CORRIDOR, â˜ƒ);
         this.steps = â˜ƒ.getInt("Steps");
      }

      @Override
      protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
         super.addAdditionalSaveData(â˜ƒ, â˜ƒ);
         â˜ƒ.putInt("Steps", this.steps);
      }

      public static BoundingBox findPieceBox(StructurePieceAccessor var0, Random var1, int var2, int var3, int var4, Direction var5) {
         int â˜ƒ = 3;
         BoundingBox â˜ƒx = BoundingBox.orientBox(â˜ƒ, â˜ƒ, â˜ƒ, -1, -1, 0, 5, 5, 4, â˜ƒ);
         StructurePiece â˜ƒxx = â˜ƒ.findCollisionPiece(â˜ƒx);
         if (â˜ƒxx == null) {
            return null;
         } else {
            if (â˜ƒxx.getBoundingBox().minY() == â˜ƒx.minY()) {
               for(int â˜ƒ = 2; â˜ƒ >= 1; --â˜ƒ) {
                  â˜ƒx = BoundingBox.orientBox(â˜ƒ, â˜ƒ, â˜ƒ, -1, -1, 0, 5, 5, â˜ƒ, â˜ƒ);
                  if (!â˜ƒxx.getBoundingBox().intersects(â˜ƒx)) {
                     return BoundingBox.orientBox(â˜ƒ, â˜ƒ, â˜ƒ, -1, -1, 0, 5, 5, â˜ƒ + 1, â˜ƒ);
                  }
               }
            }

            return null;
         }
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         for(int â˜ƒ = 0; â˜ƒ < this.steps; ++â˜ƒ) {
            this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 0, 0, â˜ƒ, â˜ƒ);
            this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 1, 0, â˜ƒ, â˜ƒ);
            this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 2, 0, â˜ƒ, â˜ƒ);
            this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 3, 0, â˜ƒ, â˜ƒ);
            this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 4, 0, â˜ƒ, â˜ƒ);

            for(int â˜ƒx = 1; â˜ƒx <= 3; ++â˜ƒx) {
               this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 0, â˜ƒx, â˜ƒ, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.CAVE_AIR.defaultBlockState(), 1, â˜ƒx, â˜ƒ, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.CAVE_AIR.defaultBlockState(), 2, â˜ƒx, â˜ƒ, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.CAVE_AIR.defaultBlockState(), 3, â˜ƒx, â˜ƒ, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 4, â˜ƒx, â˜ƒ, â˜ƒ);
            }

            this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 0, 4, â˜ƒ, â˜ƒ);
            this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 1, 4, â˜ƒ, â˜ƒ);
            this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 2, 4, â˜ƒ, â˜ƒ);
            this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 3, 4, â˜ƒ, â˜ƒ);
            this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 4, 4, â˜ƒ, â˜ƒ);
         }

         return true;
      }
   }

   public static class FiveCrossing extends StrongholdPieces.StrongholdPiece {
      protected static final int WIDTH = 10;
      protected static final int HEIGHT = 9;
      protected static final int DEPTH = 11;
      private final boolean leftLow;
      private final boolean leftHigh;
      private final boolean rightLow;
      private final boolean rightHigh;

      public FiveCrossing(int var1, Random var2, BoundingBox var3, Direction var4) {
         super(StructurePieceType.STRONGHOLD_FIVE_CROSSING, â˜ƒ, â˜ƒ);
         this.setOrientation(â˜ƒ);
         this.entryDoor = this.randomSmallDoor(â˜ƒ);
         this.leftLow = â˜ƒ.nextBoolean();
         this.leftHigh = â˜ƒ.nextBoolean();
         this.rightLow = â˜ƒ.nextBoolean();
         this.rightHigh = â˜ƒ.nextInt(3) > 0;
      }

      public FiveCrossing(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.STRONGHOLD_FIVE_CROSSING, â˜ƒ);
         this.leftLow = â˜ƒ.getBoolean("leftLow");
         this.leftHigh = â˜ƒ.getBoolean("leftHigh");
         this.rightLow = â˜ƒ.getBoolean("rightLow");
         this.rightHigh = â˜ƒ.getBoolean("rightHigh");
      }

      @Override
      protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
         super.addAdditionalSaveData(â˜ƒ, â˜ƒ);
         â˜ƒ.putBoolean("leftLow", this.leftLow);
         â˜ƒ.putBoolean("leftHigh", this.leftHigh);
         â˜ƒ.putBoolean("rightLow", this.rightLow);
         â˜ƒ.putBoolean("rightHigh", this.rightHigh);
      }

      @Override
      public void addChildren(StructurePiece var1, StructurePieceAccessor var2, Random var3) {
         int â˜ƒ = 3;
         int â˜ƒx = 5;
         Direction â˜ƒxx = this.getOrientation();
         if (â˜ƒxx == Direction.WEST || â˜ƒxx == Direction.NORTH) {
            â˜ƒ = 8 - â˜ƒ;
            â˜ƒx = 8 - â˜ƒx;
         }

         this.generateSmallDoorChildForward((StrongholdPieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 5, 1);
         if (this.leftLow) {
            this.generateSmallDoorChildLeft((StrongholdPieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 1);
         }

         if (this.leftHigh) {
            this.generateSmallDoorChildLeft((StrongholdPieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, 7);
         }

         if (this.rightLow) {
            this.generateSmallDoorChildRight((StrongholdPieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 1);
         }

         if (this.rightHigh) {
            this.generateSmallDoorChildRight((StrongholdPieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, 7);
         }
      }

      public static StrongholdPieces.FiveCrossing createPiece(StructurePieceAccessor var0, Random var1, int var2, int var3, int var4, Direction var5, int var6) {
         BoundingBox â˜ƒ = BoundingBox.orientBox(â˜ƒ, â˜ƒ, â˜ƒ, -4, -3, 0, 10, 9, 11, â˜ƒ);
         return isOkBox(â˜ƒ) && â˜ƒ.findCollisionPiece(â˜ƒ) == null ? new StrongholdPieces.FiveCrossing(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) : null;
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         this.generateBox(â˜ƒ, â˜ƒ, 0, 0, 0, 9, 8, 10, true, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);
         this.generateSmallDoor(â˜ƒ, â˜ƒ, â˜ƒ, this.entryDoor, 4, 3, 0);
         if (this.leftLow) {
            this.generateBox(â˜ƒ, â˜ƒ, 0, 3, 1, 0, 5, 3, CAVE_AIR, CAVE_AIR, false);
         }

         if (this.rightLow) {
            this.generateBox(â˜ƒ, â˜ƒ, 9, 3, 1, 9, 5, 3, CAVE_AIR, CAVE_AIR, false);
         }

         if (this.leftHigh) {
            this.generateBox(â˜ƒ, â˜ƒ, 0, 5, 7, 0, 7, 9, CAVE_AIR, CAVE_AIR, false);
         }

         if (this.rightHigh) {
            this.generateBox(â˜ƒ, â˜ƒ, 9, 5, 7, 9, 7, 9, CAVE_AIR, CAVE_AIR, false);
         }

         this.generateBox(â˜ƒ, â˜ƒ, 5, 1, 10, 7, 3, 10, CAVE_AIR, CAVE_AIR, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 2, 1, 8, 2, 6, false, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 1, 5, 4, 4, 9, false, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 8, 1, 5, 8, 4, 9, false, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 4, 7, 3, 4, 9, false, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 3, 5, 3, 3, 6, false, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 3, 4, 3, 3, 4, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 4, 6, 3, 4, 6, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 1, 7, 7, 1, 8, false, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 1, 9, 7, 1, 9, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 2, 7, 7, 2, 7, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 5, 7, 4, 5, 9, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 8, 5, 7, 8, 5, 9, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), false);
         this.generateBox(
            â˜ƒ,
            â˜ƒ,
            5,
            5,
            7,
            7,
            5,
            9,
            Blocks.SMOOTH_STONE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.DOUBLE),
            Blocks.SMOOTH_STONE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.DOUBLE),
            false
         );
         this.placeBlock(â˜ƒ, Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, Direction.SOUTH), 6, 5, 6, â˜ƒ);
         return true;
      }
   }

   public static class LeftTurn extends StrongholdPieces.Turn {
      public LeftTurn(int var1, Random var2, BoundingBox var3, Direction var4) {
         super(StructurePieceType.STRONGHOLD_LEFT_TURN, â˜ƒ, â˜ƒ);
         this.setOrientation(â˜ƒ);
         this.entryDoor = this.randomSmallDoor(â˜ƒ);
      }

      public LeftTurn(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.STRONGHOLD_LEFT_TURN, â˜ƒ);
      }

      @Override
      public void addChildren(StructurePiece var1, StructurePieceAccessor var2, Random var3) {
         Direction â˜ƒ = this.getOrientation();
         if (â˜ƒ != Direction.NORTH && â˜ƒ != Direction.EAST) {
            this.generateSmallDoorChildRight((StrongholdPieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 1, 1);
         } else {
            this.generateSmallDoorChildLeft((StrongholdPieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 1, 1);
         }
      }

      public static StrongholdPieces.LeftTurn createPiece(StructurePieceAccessor var0, Random var1, int var2, int var3, int var4, Direction var5, int var6) {
         BoundingBox â˜ƒ = BoundingBox.orientBox(â˜ƒ, â˜ƒ, â˜ƒ, -1, -1, 0, 5, 5, 5, â˜ƒ);
         return isOkBox(â˜ƒ) && â˜ƒ.findCollisionPiece(â˜ƒ) == null ? new StrongholdPieces.LeftTurn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) : null;
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         this.generateBox(â˜ƒ, â˜ƒ, 0, 0, 0, 4, 4, 4, true, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);
         this.generateSmallDoor(â˜ƒ, â˜ƒ, â˜ƒ, this.entryDoor, 1, 1, 0);
         Direction â˜ƒ = this.getOrientation();
         if (â˜ƒ != Direction.NORTH && â˜ƒ != Direction.EAST) {
            this.generateBox(â˜ƒ, â˜ƒ, 4, 1, 1, 4, 3, 3, CAVE_AIR, CAVE_AIR, false);
         } else {
            this.generateBox(â˜ƒ, â˜ƒ, 0, 1, 1, 0, 3, 3, CAVE_AIR, CAVE_AIR, false);
         }

         return true;
      }
   }

   public static class Library extends StrongholdPieces.StrongholdPiece {
      protected static final int WIDTH = 14;
      protected static final int HEIGHT = 6;
      protected static final int TALL_HEIGHT = 11;
      protected static final int DEPTH = 15;
      private final boolean isTall;

      public Library(int var1, Random var2, BoundingBox var3, Direction var4) {
         super(StructurePieceType.STRONGHOLD_LIBRARY, â˜ƒ, â˜ƒ);
         this.setOrientation(â˜ƒ);
         this.entryDoor = this.randomSmallDoor(â˜ƒ);
         this.isTall = â˜ƒ.getYSpan() > 6;
      }

      public Library(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.STRONGHOLD_LIBRARY, â˜ƒ);
         this.isTall = â˜ƒ.getBoolean("Tall");
      }

      @Override
      protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
         super.addAdditionalSaveData(â˜ƒ, â˜ƒ);
         â˜ƒ.putBoolean("Tall", this.isTall);
      }

      public static StrongholdPieces.Library createPiece(StructurePieceAccessor var0, Random var1, int var2, int var3, int var4, Direction var5, int var6) {
         BoundingBox â˜ƒ = BoundingBox.orientBox(â˜ƒ, â˜ƒ, â˜ƒ, -4, -1, 0, 14, 11, 15, â˜ƒ);
         if (!isOkBox(â˜ƒ) || â˜ƒ.findCollisionPiece(â˜ƒ) != null) {
            â˜ƒ = BoundingBox.orientBox(â˜ƒ, â˜ƒ, â˜ƒ, -4, -1, 0, 14, 6, 15, â˜ƒ);
            if (!isOkBox(â˜ƒ) || â˜ƒ.findCollisionPiece(â˜ƒ) != null) {
               return null;
            }
         }

         return new StrongholdPieces.Library(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         int â˜ƒ = 11;
         if (!this.isTall) {
            â˜ƒ = 6;
         }

         this.generateBox(â˜ƒ, â˜ƒ, 0, 0, 0, 13, â˜ƒ - 1, 14, true, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);
         this.generateSmallDoor(â˜ƒ, â˜ƒ, â˜ƒ, this.entryDoor, 4, 1, 0);
         this.generateMaybeBox(â˜ƒ, â˜ƒ, â˜ƒ, 0.07F, 2, 1, 1, 11, 4, 13, Blocks.COBWEB.defaultBlockState(), Blocks.COBWEB.defaultBlockState(), false, false);
         int â˜ƒ = 1;
         int â˜ƒx = 12;

         for(int â˜ƒxx = 1; â˜ƒxx <= 13; ++â˜ƒxx) {
            if ((â˜ƒxx - 1) % 4 == 0) {
               this.generateBox(â˜ƒ, â˜ƒ, 1, 1, â˜ƒxx, 1, 4, â˜ƒxx, Blocks.OAK_PLANKS.defaultBlockState(), Blocks.OAK_PLANKS.defaultBlockState(), false);
               this.generateBox(â˜ƒ, â˜ƒ, 12, 1, â˜ƒxx, 12, 4, â˜ƒxx, Blocks.OAK_PLANKS.defaultBlockState(), Blocks.OAK_PLANKS.defaultBlockState(), false);
               this.placeBlock(â˜ƒ, Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, Direction.EAST), 2, 3, â˜ƒxx, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, Direction.WEST), 11, 3, â˜ƒxx, â˜ƒ);
               if (this.isTall) {
                  this.generateBox(â˜ƒ, â˜ƒ, 1, 6, â˜ƒxx, 1, 9, â˜ƒxx, Blocks.OAK_PLANKS.defaultBlockState(), Blocks.OAK_PLANKS.defaultBlockState(), false);
                  this.generateBox(â˜ƒ, â˜ƒ, 12, 6, â˜ƒxx, 12, 9, â˜ƒxx, Blocks.OAK_PLANKS.defaultBlockState(), Blocks.OAK_PLANKS.defaultBlockState(), false);
               }
            } else {
               this.generateBox(â˜ƒ, â˜ƒ, 1, 1, â˜ƒxx, 1, 4, â˜ƒxx, Blocks.BOOKSHELF.defaultBlockState(), Blocks.BOOKSHELF.defaultBlockState(), false);
               this.generateBox(â˜ƒ, â˜ƒ, 12, 1, â˜ƒxx, 12, 4, â˜ƒxx, Blocks.BOOKSHELF.defaultBlockState(), Blocks.BOOKSHELF.defaultBlockState(), false);
               if (this.isTall) {
                  this.generateBox(â˜ƒ, â˜ƒ, 1, 6, â˜ƒxx, 1, 9, â˜ƒxx, Blocks.BOOKSHELF.defaultBlockState(), Blocks.BOOKSHELF.defaultBlockState(), false);
                  this.generateBox(â˜ƒ, â˜ƒ, 12, 6, â˜ƒxx, 12, 9, â˜ƒxx, Blocks.BOOKSHELF.defaultBlockState(), Blocks.BOOKSHELF.defaultBlockState(), false);
               }
            }
         }

         for(int â˜ƒxx = 3; â˜ƒxx < 12; â˜ƒxx += 2) {
            this.generateBox(â˜ƒ, â˜ƒ, 3, 1, â˜ƒxx, 4, 3, â˜ƒxx, Blocks.BOOKSHELF.defaultBlockState(), Blocks.BOOKSHELF.defaultBlockState(), false);
            this.generateBox(â˜ƒ, â˜ƒ, 6, 1, â˜ƒxx, 7, 3, â˜ƒxx, Blocks.BOOKSHELF.defaultBlockState(), Blocks.BOOKSHELF.defaultBlockState(), false);
            this.generateBox(â˜ƒ, â˜ƒ, 9, 1, â˜ƒxx, 10, 3, â˜ƒxx, Blocks.BOOKSHELF.defaultBlockState(), Blocks.BOOKSHELF.defaultBlockState(), false);
         }

         if (this.isTall) {
            this.generateBox(â˜ƒ, â˜ƒ, 1, 5, 1, 3, 5, 13, Blocks.OAK_PLANKS.defaultBlockState(), Blocks.OAK_PLANKS.defaultBlockState(), false);
            this.generateBox(â˜ƒ, â˜ƒ, 10, 5, 1, 12, 5, 13, Blocks.OAK_PLANKS.defaultBlockState(), Blocks.OAK_PLANKS.defaultBlockState(), false);
            this.generateBox(â˜ƒ, â˜ƒ, 4, 5, 1, 9, 5, 2, Blocks.OAK_PLANKS.defaultBlockState(), Blocks.OAK_PLANKS.defaultBlockState(), false);
            this.generateBox(â˜ƒ, â˜ƒ, 4, 5, 12, 9, 5, 13, Blocks.OAK_PLANKS.defaultBlockState(), Blocks.OAK_PLANKS.defaultBlockState(), false);
            this.placeBlock(â˜ƒ, Blocks.OAK_PLANKS.defaultBlockState(), 9, 5, 11, â˜ƒ);
            this.placeBlock(â˜ƒ, Blocks.OAK_PLANKS.defaultBlockState(), 8, 5, 11, â˜ƒ);
            this.placeBlock(â˜ƒ, Blocks.OAK_PLANKS.defaultBlockState(), 9, 5, 10, â˜ƒ);
            BlockState â˜ƒxx = Blocks.OAK_FENCE
               .defaultBlockState()
               .setValue(FenceBlock.WEST, Boolean.valueOf(true))
               .setValue(FenceBlock.EAST, Boolean.valueOf(true));
            BlockState â˜ƒxxx = Blocks.OAK_FENCE
               .defaultBlockState()
               .setValue(FenceBlock.NORTH, Boolean.valueOf(true))
               .setValue(FenceBlock.SOUTH, Boolean.valueOf(true));
            this.generateBox(â˜ƒ, â˜ƒ, 3, 6, 3, 3, 6, 11, â˜ƒxxx, â˜ƒxxx, false);
            this.generateBox(â˜ƒ, â˜ƒ, 10, 6, 3, 10, 6, 9, â˜ƒxxx, â˜ƒxxx, false);
            this.generateBox(â˜ƒ, â˜ƒ, 4, 6, 2, 9, 6, 2, â˜ƒxx, â˜ƒxx, false);
            this.generateBox(â˜ƒ, â˜ƒ, 4, 6, 12, 7, 6, 12, â˜ƒxx, â˜ƒxx, false);
            this.placeBlock(
               â˜ƒ,
               Blocks.OAK_FENCE.defaultBlockState().setValue(FenceBlock.NORTH, Boolean.valueOf(true)).setValue(FenceBlock.EAST, Boolean.valueOf(true)),
               3,
               6,
               2,
               â˜ƒ
            );
            this.placeBlock(
               â˜ƒ,
               Blocks.OAK_FENCE.defaultBlockState().setValue(FenceBlock.SOUTH, Boolean.valueOf(true)).setValue(FenceBlock.EAST, Boolean.valueOf(true)),
               3,
               6,
               12,
               â˜ƒ
            );
            this.placeBlock(
               â˜ƒ,
               Blocks.OAK_FENCE.defaultBlockState().setValue(FenceBlock.NORTH, Boolean.valueOf(true)).setValue(FenceBlock.WEST, Boolean.valueOf(true)),
               10,
               6,
               2,
               â˜ƒ
            );

            for(int â˜ƒxxxx = 0; â˜ƒxxxx <= 2; ++â˜ƒxxxx) {
               this.placeBlock(
                  â˜ƒ,
                  Blocks.OAK_FENCE.defaultBlockState().setValue(FenceBlock.SOUTH, Boolean.valueOf(true)).setValue(FenceBlock.WEST, Boolean.valueOf(true)),
                  8 + â˜ƒxxxx,
                  6,
                  12 - â˜ƒxxxx,
                  â˜ƒ
               );
               if (â˜ƒxxxx != 2) {
                  this.placeBlock(
                     â˜ƒ,
                     Blocks.OAK_FENCE.defaultBlockState().setValue(FenceBlock.NORTH, Boolean.valueOf(true)).setValue(FenceBlock.EAST, Boolean.valueOf(true)),
                     8 + â˜ƒxxxx,
                     6,
                     11 - â˜ƒxxxx,
                     â˜ƒ
                  );
               }
            }

            BlockState â˜ƒxxxx = Blocks.LADDER.defaultBlockState().setValue(LadderBlock.FACING, Direction.SOUTH);
            this.placeBlock(â˜ƒ, â˜ƒxxxx, 10, 1, 13, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxxx, 10, 2, 13, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxxx, 10, 3, 13, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxxx, 10, 4, 13, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxxx, 10, 5, 13, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxxx, 10, 6, 13, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxxx, 10, 7, 13, â˜ƒ);
            int â˜ƒxxxxx = 7;
            int â˜ƒxxxxxx = 7;
            BlockState â˜ƒxxxxxxx = Blocks.OAK_FENCE.defaultBlockState().setValue(FenceBlock.EAST, Boolean.valueOf(true));
            this.placeBlock(â˜ƒ, â˜ƒxxxxxxx, 6, 9, 7, â˜ƒ);
            BlockState â˜ƒxxxxxxxx = Blocks.OAK_FENCE.defaultBlockState().setValue(FenceBlock.WEST, Boolean.valueOf(true));
            this.placeBlock(â˜ƒ, â˜ƒxxxxxxxx, 7, 9, 7, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxxxxxx, 6, 8, 7, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxxxxxxx, 7, 8, 7, â˜ƒ);
            BlockState â˜ƒxxxxxxxxx = â˜ƒxxx.setValue(FenceBlock.WEST, Boolean.valueOf(true)).setValue(FenceBlock.EAST, Boolean.valueOf(true));
            this.placeBlock(â˜ƒ, â˜ƒxxxxxxxxx, 6, 7, 7, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxxxxxxxx, 7, 7, 7, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxxxxxx, 5, 7, 7, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxxxxxxx, 8, 7, 7, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxxxxxx.setValue(FenceBlock.NORTH, Boolean.valueOf(true)), 6, 7, 6, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxxxxxx.setValue(FenceBlock.SOUTH, Boolean.valueOf(true)), 6, 7, 8, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxxxxxxx.setValue(FenceBlock.NORTH, Boolean.valueOf(true)), 7, 7, 6, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxxxxxxx.setValue(FenceBlock.SOUTH, Boolean.valueOf(true)), 7, 7, 8, â˜ƒ);
            BlockState â˜ƒxxxxxxxxxx = Blocks.TORCH.defaultBlockState();
            this.placeBlock(â˜ƒ, â˜ƒxxxxxxxxxx, 5, 8, 7, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxxxxxxxxx, 8, 8, 7, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxxxxxxxxx, 6, 8, 6, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxxxxxxxxx, 6, 8, 8, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxxxxxxxxx, 7, 8, 6, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxxxxxxxxx, 7, 8, 8, â˜ƒ);
         }

         this.createChest(â˜ƒ, â˜ƒ, â˜ƒ, 3, 3, 5, BuiltInLootTables.STRONGHOLD_LIBRARY);
         if (this.isTall) {
            this.placeBlock(â˜ƒ, CAVE_AIR, 12, 9, 1, â˜ƒ);
            this.createChest(â˜ƒ, â˜ƒ, â˜ƒ, 12, 8, 1, BuiltInLootTables.STRONGHOLD_LIBRARY);
         }

         return true;
      }
   }

   static class PieceWeight {
      public final Class<? extends StrongholdPieces.StrongholdPiece> pieceClass;
      public final int weight;
      public int placeCount;
      public final int maxPlaceCount;

      public PieceWeight(Class<? extends StrongholdPieces.StrongholdPiece> var1, int var2, int var3) {
         this.pieceClass = â˜ƒ;
         this.weight = â˜ƒ;
         this.maxPlaceCount = â˜ƒ;
      }

      public boolean doPlace(int var1) {
         return this.maxPlaceCount == 0 || this.placeCount < this.maxPlaceCount;
      }

      public boolean isValid() {
         return this.maxPlaceCount == 0 || this.placeCount < this.maxPlaceCount;
      }
   }

   public static class PortalRoom extends StrongholdPieces.StrongholdPiece {
      protected static final int WIDTH = 11;
      protected static final int HEIGHT = 8;
      protected static final int DEPTH = 16;
      private boolean hasPlacedSpawner;

      public PortalRoom(int var1, BoundingBox var2, Direction var3) {
         super(StructurePieceType.STRONGHOLD_PORTAL_ROOM, â˜ƒ, â˜ƒ);
         this.setOrientation(â˜ƒ);
      }

      public PortalRoom(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.STRONGHOLD_PORTAL_ROOM, â˜ƒ);
         this.hasPlacedSpawner = â˜ƒ.getBoolean("Mob");
      }

      @Override
      protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
         super.addAdditionalSaveData(â˜ƒ, â˜ƒ);
         â˜ƒ.putBoolean("Mob", this.hasPlacedSpawner);
      }

      @Override
      public void addChildren(StructurePiece var1, StructurePieceAccessor var2, Random var3) {
         if (â˜ƒ != null) {
            ((StrongholdPieces.StartPiece)â˜ƒ).portalRoomPiece = this;
         }
      }

      public static StrongholdPieces.PortalRoom createPiece(StructurePieceAccessor var0, int var1, int var2, int var3, Direction var4, int var5) {
         BoundingBox â˜ƒ = BoundingBox.orientBox(â˜ƒ, â˜ƒ, â˜ƒ, -4, -1, 0, 11, 8, 16, â˜ƒ);
         return isOkBox(â˜ƒ) && â˜ƒ.findCollisionPiece(â˜ƒ) == null ? new StrongholdPieces.PortalRoom(â˜ƒ, â˜ƒ, â˜ƒ) : null;
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         this.generateBox(â˜ƒ, â˜ƒ, 0, 0, 0, 10, 7, 15, false, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);
         this.generateSmallDoor(â˜ƒ, â˜ƒ, â˜ƒ, StrongholdPieces.StrongholdPiece.SmallDoorType.GRATES, 4, 1, 0);
         int â˜ƒ = 6;
         this.generateBox(â˜ƒ, â˜ƒ, 1, â˜ƒ, 1, 1, â˜ƒ, 14, false, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 9, â˜ƒ, 1, 9, â˜ƒ, 14, false, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 2, â˜ƒ, 1, 8, â˜ƒ, 2, false, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 2, â˜ƒ, 14, 8, â˜ƒ, 14, false, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 1, 1, 2, 1, 4, false, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 8, 1, 1, 9, 1, 4, false, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 1, 1, 1, 1, 3, Blocks.LAVA.defaultBlockState(), Blocks.LAVA.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 9, 1, 1, 9, 1, 3, Blocks.LAVA.defaultBlockState(), Blocks.LAVA.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 3, 1, 8, 7, 1, 12, false, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 1, 9, 6, 1, 11, Blocks.LAVA.defaultBlockState(), Blocks.LAVA.defaultBlockState(), false);
         BlockState â˜ƒx = Blocks.IRON_BARS
            .defaultBlockState()
            .setValue(IronBarsBlock.NORTH, Boolean.valueOf(true))
            .setValue(IronBarsBlock.SOUTH, Boolean.valueOf(true));
         BlockState â˜ƒxx = Blocks.IRON_BARS
            .defaultBlockState()
            .setValue(IronBarsBlock.WEST, Boolean.valueOf(true))
            .setValue(IronBarsBlock.EAST, Boolean.valueOf(true));

         for(int â˜ƒxxx = 3; â˜ƒxxx < 14; â˜ƒxxx += 2) {
            this.generateBox(â˜ƒ, â˜ƒ, 0, 3, â˜ƒxxx, 0, 4, â˜ƒxxx, â˜ƒx, â˜ƒx, false);
            this.generateBox(â˜ƒ, â˜ƒ, 10, 3, â˜ƒxxx, 10, 4, â˜ƒxxx, â˜ƒx, â˜ƒx, false);
         }

         for(int â˜ƒxxx = 2; â˜ƒxxx < 9; â˜ƒxxx += 2) {
            this.generateBox(â˜ƒ, â˜ƒ, â˜ƒxxx, 3, 15, â˜ƒxxx, 4, 15, â˜ƒxx, â˜ƒxx, false);
         }

         BlockState â˜ƒxxx = Blocks.STONE_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 1, 5, 6, 1, 7, false, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 2, 6, 6, 2, 7, false, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 3, 7, 6, 3, 7, false, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);

         for(int â˜ƒxxxx = 4; â˜ƒxxxx <= 6; ++â˜ƒxxxx) {
            this.placeBlock(â˜ƒ, â˜ƒxxx, â˜ƒxxxx, 1, 4, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxx, â˜ƒxxxx, 2, 5, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxx, â˜ƒxxxx, 3, 6, â˜ƒ);
         }

         BlockState â˜ƒxxxx = Blocks.END_PORTAL_FRAME.defaultBlockState().setValue(EndPortalFrameBlock.FACING, Direction.NORTH);
         BlockState â˜ƒxxxxx = Blocks.END_PORTAL_FRAME.defaultBlockState().setValue(EndPortalFrameBlock.FACING, Direction.SOUTH);
         BlockState â˜ƒxxxxxx = Blocks.END_PORTAL_FRAME.defaultBlockState().setValue(EndPortalFrameBlock.FACING, Direction.EAST);
         BlockState â˜ƒxxxxxxx = Blocks.END_PORTAL_FRAME.defaultBlockState().setValue(EndPortalFrameBlock.FACING, Direction.WEST);
         boolean â˜ƒxxxxxxxx = true;
         boolean[] â˜ƒxxxxxxxxx = new boolean[12];

         for(int â˜ƒxxxxxxxxxx = 0; â˜ƒxxxxxxxxxx < â˜ƒxxxxxxxxx.length; ++â˜ƒxxxxxxxxxx) {
            â˜ƒxxxxxxxxx[â˜ƒxxxxxxxxxx] = â˜ƒ.nextFloat() > 0.9F;
            â˜ƒxxxxxxxx &= â˜ƒxxxxxxxxx[â˜ƒxxxxxxxxxx];
         }

         this.placeBlock(â˜ƒ, â˜ƒxxxx.setValue(EndPortalFrameBlock.HAS_EYE, Boolean.valueOf(â˜ƒxxxxxxxxx[0])), 4, 3, 8, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxxx.setValue(EndPortalFrameBlock.HAS_EYE, Boolean.valueOf(â˜ƒxxxxxxxxx[1])), 5, 3, 8, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxxx.setValue(EndPortalFrameBlock.HAS_EYE, Boolean.valueOf(â˜ƒxxxxxxxxx[2])), 6, 3, 8, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxxxx.setValue(EndPortalFrameBlock.HAS_EYE, Boolean.valueOf(â˜ƒxxxxxxxxx[3])), 4, 3, 12, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxxxx.setValue(EndPortalFrameBlock.HAS_EYE, Boolean.valueOf(â˜ƒxxxxxxxxx[4])), 5, 3, 12, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxxxx.setValue(EndPortalFrameBlock.HAS_EYE, Boolean.valueOf(â˜ƒxxxxxxxxx[5])), 6, 3, 12, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxxxxx.setValue(EndPortalFrameBlock.HAS_EYE, Boolean.valueOf(â˜ƒxxxxxxxxx[6])), 3, 3, 9, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxxxxx.setValue(EndPortalFrameBlock.HAS_EYE, Boolean.valueOf(â˜ƒxxxxxxxxx[7])), 3, 3, 10, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxxxxx.setValue(EndPortalFrameBlock.HAS_EYE, Boolean.valueOf(â˜ƒxxxxxxxxx[8])), 3, 3, 11, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxxxxxx.setValue(EndPortalFrameBlock.HAS_EYE, Boolean.valueOf(â˜ƒxxxxxxxxx[9])), 7, 3, 9, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxxxxxx.setValue(EndPortalFrameBlock.HAS_EYE, Boolean.valueOf(â˜ƒxxxxxxxxx[10])), 7, 3, 10, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxxxxxx.setValue(EndPortalFrameBlock.HAS_EYE, Boolean.valueOf(â˜ƒxxxxxxxxx[11])), 7, 3, 11, â˜ƒ);
         if (â˜ƒxxxxxxxx) {
            BlockState â˜ƒxxxxxxxxxx = Blocks.END_PORTAL.defaultBlockState();
            this.placeBlock(â˜ƒ, â˜ƒxxxxxxxxxx, 4, 3, 9, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxxxxxxxxx, 5, 3, 9, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxxxxxxxxx, 6, 3, 9, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxxxxxxxxx, 4, 3, 10, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxxxxxxxxx, 5, 3, 10, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxxxxxxxxx, 6, 3, 10, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxxxxxxxxx, 4, 3, 11, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxxxxxxxxx, 5, 3, 11, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxxxxxxxxxx, 6, 3, 11, â˜ƒ);
         }

         if (!this.hasPlacedSpawner) {
            BlockPos â˜ƒxxxxxxxxxx = this.getWorldPos(5, 3, 6);
            if (â˜ƒ.isInside(â˜ƒxxxxxxxxxx)) {
               this.hasPlacedSpawner = true;
               â˜ƒ.setBlock(â˜ƒxxxxxxxxxx, Blocks.SPAWNER.defaultBlockState(), 2);
               BlockEntity â˜ƒxxxxxxxxxxx = â˜ƒ.getBlockEntity(â˜ƒxxxxxxxxxx);
               if (â˜ƒxxxxxxxxxxx instanceof SpawnerBlockEntity) {
                  ((SpawnerBlockEntity)â˜ƒxxxxxxxxxxx).getSpawner().setEntityId(EntityType.SILVERFISH);
               }
            }
         }

         return true;
      }
   }

   public static class PrisonHall extends StrongholdPieces.StrongholdPiece {
      protected static final int WIDTH = 9;
      protected static final int HEIGHT = 5;
      protected static final int DEPTH = 11;

      public PrisonHall(int var1, Random var2, BoundingBox var3, Direction var4) {
         super(StructurePieceType.STRONGHOLD_PRISON_HALL, â˜ƒ, â˜ƒ);
         this.setOrientation(â˜ƒ);
         this.entryDoor = this.randomSmallDoor(â˜ƒ);
      }

      public PrisonHall(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.STRONGHOLD_PRISON_HALL, â˜ƒ);
      }

      @Override
      public void addChildren(StructurePiece var1, StructurePieceAccessor var2, Random var3) {
         this.generateSmallDoorChildForward((StrongholdPieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 1, 1);
      }

      public static StrongholdPieces.PrisonHall createPiece(StructurePieceAccessor var0, Random var1, int var2, int var3, int var4, Direction var5, int var6) {
         BoundingBox â˜ƒ = BoundingBox.orientBox(â˜ƒ, â˜ƒ, â˜ƒ, -1, -1, 0, 9, 5, 11, â˜ƒ);
         return isOkBox(â˜ƒ) && â˜ƒ.findCollisionPiece(â˜ƒ) == null ? new StrongholdPieces.PrisonHall(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) : null;
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         this.generateBox(â˜ƒ, â˜ƒ, 0, 0, 0, 8, 4, 10, true, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);
         this.generateSmallDoor(â˜ƒ, â˜ƒ, â˜ƒ, this.entryDoor, 1, 1, 0);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 1, 10, 3, 3, 10, CAVE_AIR, CAVE_AIR, false);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 1, 1, 4, 3, 1, false, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 1, 3, 4, 3, 3, false, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 1, 7, 4, 3, 7, false, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 1, 9, 4, 3, 9, false, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);

         for(int â˜ƒ = 1; â˜ƒ <= 3; ++â˜ƒ) {
            this.placeBlock(
               â˜ƒ,
               Blocks.IRON_BARS.defaultBlockState().setValue(IronBarsBlock.NORTH, Boolean.valueOf(true)).setValue(IronBarsBlock.SOUTH, Boolean.valueOf(true)),
               4,
               â˜ƒ,
               4,
               â˜ƒ
            );
            this.placeBlock(
               â˜ƒ,
               Blocks.IRON_BARS
                  .defaultBlockState()
                  .setValue(IronBarsBlock.NORTH, Boolean.valueOf(true))
                  .setValue(IronBarsBlock.SOUTH, Boolean.valueOf(true))
                  .setValue(IronBarsBlock.EAST, Boolean.valueOf(true)),
               4,
               â˜ƒ,
               5,
               â˜ƒ
            );
            this.placeBlock(
               â˜ƒ,
               Blocks.IRON_BARS.defaultBlockState().setValue(IronBarsBlock.NORTH, Boolean.valueOf(true)).setValue(IronBarsBlock.SOUTH, Boolean.valueOf(true)),
               4,
               â˜ƒ,
               6,
               â˜ƒ
            );
            this.placeBlock(
               â˜ƒ,
               Blocks.IRON_BARS.defaultBlockState().setValue(IronBarsBlock.WEST, Boolean.valueOf(true)).setValue(IronBarsBlock.EAST, Boolean.valueOf(true)),
               5,
               â˜ƒ,
               5,
               â˜ƒ
            );
            this.placeBlock(
               â˜ƒ,
               Blocks.IRON_BARS.defaultBlockState().setValue(IronBarsBlock.WEST, Boolean.valueOf(true)).setValue(IronBarsBlock.EAST, Boolean.valueOf(true)),
               6,
               â˜ƒ,
               5,
               â˜ƒ
            );
            this.placeBlock(
               â˜ƒ,
               Blocks.IRON_BARS.defaultBlockState().setValue(IronBarsBlock.WEST, Boolean.valueOf(true)).setValue(IronBarsBlock.EAST, Boolean.valueOf(true)),
               7,
               â˜ƒ,
               5,
               â˜ƒ
            );
         }

         this.placeBlock(
            â˜ƒ,
            Blocks.IRON_BARS.defaultBlockState().setValue(IronBarsBlock.NORTH, Boolean.valueOf(true)).setValue(IronBarsBlock.SOUTH, Boolean.valueOf(true)),
            4,
            3,
            2,
            â˜ƒ
         );
         this.placeBlock(
            â˜ƒ,
            Blocks.IRON_BARS.defaultBlockState().setValue(IronBarsBlock.NORTH, Boolean.valueOf(true)).setValue(IronBarsBlock.SOUTH, Boolean.valueOf(true)),
            4,
            3,
            8,
            â˜ƒ
         );
         BlockState â˜ƒ = Blocks.IRON_DOOR.defaultBlockState().setValue(DoorBlock.FACING, Direction.WEST);
         BlockState â˜ƒx = Blocks.IRON_DOOR.defaultBlockState().setValue(DoorBlock.FACING, Direction.WEST).setValue(DoorBlock.HALF, DoubleBlockHalf.UPPER);
         this.placeBlock(â˜ƒ, â˜ƒ, 4, 1, 2, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒx, 4, 2, 2, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒ, 4, 1, 8, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒx, 4, 2, 8, â˜ƒ);
         return true;
      }
   }

   public static class RightTurn extends StrongholdPieces.Turn {
      public RightTurn(int var1, Random var2, BoundingBox var3, Direction var4) {
         super(StructurePieceType.STRONGHOLD_RIGHT_TURN, â˜ƒ, â˜ƒ);
         this.setOrientation(â˜ƒ);
         this.entryDoor = this.randomSmallDoor(â˜ƒ);
      }

      public RightTurn(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.STRONGHOLD_RIGHT_TURN, â˜ƒ);
      }

      @Override
      public void addChildren(StructurePiece var1, StructurePieceAccessor var2, Random var3) {
         Direction â˜ƒ = this.getOrientation();
         if (â˜ƒ != Direction.NORTH && â˜ƒ != Direction.EAST) {
            this.generateSmallDoorChildLeft((StrongholdPieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 1, 1);
         } else {
            this.generateSmallDoorChildRight((StrongholdPieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 1, 1);
         }
      }

      public static StrongholdPieces.RightTurn createPiece(StructurePieceAccessor var0, Random var1, int var2, int var3, int var4, Direction var5, int var6) {
         BoundingBox â˜ƒ = BoundingBox.orientBox(â˜ƒ, â˜ƒ, â˜ƒ, -1, -1, 0, 5, 5, 5, â˜ƒ);
         return isOkBox(â˜ƒ) && â˜ƒ.findCollisionPiece(â˜ƒ) == null ? new StrongholdPieces.RightTurn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) : null;
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         this.generateBox(â˜ƒ, â˜ƒ, 0, 0, 0, 4, 4, 4, true, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);
         this.generateSmallDoor(â˜ƒ, â˜ƒ, â˜ƒ, this.entryDoor, 1, 1, 0);
         Direction â˜ƒ = this.getOrientation();
         if (â˜ƒ != Direction.NORTH && â˜ƒ != Direction.EAST) {
            this.generateBox(â˜ƒ, â˜ƒ, 0, 1, 1, 0, 3, 3, CAVE_AIR, CAVE_AIR, false);
         } else {
            this.generateBox(â˜ƒ, â˜ƒ, 4, 1, 1, 4, 3, 3, CAVE_AIR, CAVE_AIR, false);
         }

         return true;
      }
   }

   public static class RoomCrossing extends StrongholdPieces.StrongholdPiece {
      protected static final int WIDTH = 11;
      protected static final int HEIGHT = 7;
      protected static final int DEPTH = 11;
      protected final int type;

      public RoomCrossing(int var1, Random var2, BoundingBox var3, Direction var4) {
         super(StructurePieceType.STRONGHOLD_ROOM_CROSSING, â˜ƒ, â˜ƒ);
         this.setOrientation(â˜ƒ);
         this.entryDoor = this.randomSmallDoor(â˜ƒ);
         this.type = â˜ƒ.nextInt(5);
      }

      public RoomCrossing(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.STRONGHOLD_ROOM_CROSSING, â˜ƒ);
         this.type = â˜ƒ.getInt("Type");
      }

      @Override
      protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
         super.addAdditionalSaveData(â˜ƒ, â˜ƒ);
         â˜ƒ.putInt("Type", this.type);
      }

      @Override
      public void addChildren(StructurePiece var1, StructurePieceAccessor var2, Random var3) {
         this.generateSmallDoorChildForward((StrongholdPieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 4, 1);
         this.generateSmallDoorChildLeft((StrongholdPieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 1, 4);
         this.generateSmallDoorChildRight((StrongholdPieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 1, 4);
      }

      public static StrongholdPieces.RoomCrossing createPiece(StructurePieceAccessor var0, Random var1, int var2, int var3, int var4, Direction var5, int var6) {
         BoundingBox â˜ƒ = BoundingBox.orientBox(â˜ƒ, â˜ƒ, â˜ƒ, -4, -1, 0, 11, 7, 11, â˜ƒ);
         return isOkBox(â˜ƒ) && â˜ƒ.findCollisionPiece(â˜ƒ) == null ? new StrongholdPieces.RoomCrossing(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) : null;
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         this.generateBox(â˜ƒ, â˜ƒ, 0, 0, 0, 10, 6, 10, true, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);
         this.generateSmallDoor(â˜ƒ, â˜ƒ, â˜ƒ, this.entryDoor, 4, 1, 0);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 1, 10, 6, 3, 10, CAVE_AIR, CAVE_AIR, false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 1, 4, 0, 3, 6, CAVE_AIR, CAVE_AIR, false);
         this.generateBox(â˜ƒ, â˜ƒ, 10, 1, 4, 10, 3, 6, CAVE_AIR, CAVE_AIR, false);
         switch(this.type) {
            case 0:
               this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 5, 1, 5, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 5, 2, 5, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 5, 3, 5, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, Direction.WEST), 4, 3, 5, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, Direction.EAST), 6, 3, 5, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, Direction.SOUTH), 5, 3, 4, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, Direction.NORTH), 5, 3, 6, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 4, 1, 4, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 4, 1, 5, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 4, 1, 6, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 6, 1, 4, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 6, 1, 5, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 6, 1, 6, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 5, 1, 4, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 5, 1, 6, â˜ƒ);
               break;
            case 1:
               for(int â˜ƒ = 0; â˜ƒ < 5; ++â˜ƒ) {
                  this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 3, 1, 3 + â˜ƒ, â˜ƒ);
                  this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 7, 1, 3 + â˜ƒ, â˜ƒ);
                  this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 3 + â˜ƒ, 1, 3, â˜ƒ);
                  this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 3 + â˜ƒ, 1, 7, â˜ƒ);
               }

               this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 5, 1, 5, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 5, 2, 5, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 5, 3, 5, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.WATER.defaultBlockState(), 5, 4, 5, â˜ƒ);
               break;
            case 2:
               for(int â˜ƒ = 1; â˜ƒ <= 9; ++â˜ƒ) {
                  this.placeBlock(â˜ƒ, Blocks.COBBLESTONE.defaultBlockState(), 1, 3, â˜ƒ, â˜ƒ);
                  this.placeBlock(â˜ƒ, Blocks.COBBLESTONE.defaultBlockState(), 9, 3, â˜ƒ, â˜ƒ);
               }

               for(int â˜ƒ = 1; â˜ƒ <= 9; ++â˜ƒ) {
                  this.placeBlock(â˜ƒ, Blocks.COBBLESTONE.defaultBlockState(), â˜ƒ, 3, 1, â˜ƒ);
                  this.placeBlock(â˜ƒ, Blocks.COBBLESTONE.defaultBlockState(), â˜ƒ, 3, 9, â˜ƒ);
               }

               this.placeBlock(â˜ƒ, Blocks.COBBLESTONE.defaultBlockState(), 5, 1, 4, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.COBBLESTONE.defaultBlockState(), 5, 1, 6, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.COBBLESTONE.defaultBlockState(), 5, 3, 4, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.COBBLESTONE.defaultBlockState(), 5, 3, 6, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.COBBLESTONE.defaultBlockState(), 4, 1, 5, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.COBBLESTONE.defaultBlockState(), 6, 1, 5, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.COBBLESTONE.defaultBlockState(), 4, 3, 5, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.COBBLESTONE.defaultBlockState(), 6, 3, 5, â˜ƒ);

               for(int â˜ƒ = 1; â˜ƒ <= 3; ++â˜ƒ) {
                  this.placeBlock(â˜ƒ, Blocks.COBBLESTONE.defaultBlockState(), 4, â˜ƒ, 4, â˜ƒ);
                  this.placeBlock(â˜ƒ, Blocks.COBBLESTONE.defaultBlockState(), 6, â˜ƒ, 4, â˜ƒ);
                  this.placeBlock(â˜ƒ, Blocks.COBBLESTONE.defaultBlockState(), 4, â˜ƒ, 6, â˜ƒ);
                  this.placeBlock(â˜ƒ, Blocks.COBBLESTONE.defaultBlockState(), 6, â˜ƒ, 6, â˜ƒ);
               }

               this.placeBlock(â˜ƒ, Blocks.TORCH.defaultBlockState(), 5, 3, 5, â˜ƒ);

               for(int â˜ƒ = 2; â˜ƒ <= 8; ++â˜ƒ) {
                  this.placeBlock(â˜ƒ, Blocks.OAK_PLANKS.defaultBlockState(), 2, 3, â˜ƒ, â˜ƒ);
                  this.placeBlock(â˜ƒ, Blocks.OAK_PLANKS.defaultBlockState(), 3, 3, â˜ƒ, â˜ƒ);
                  if (â˜ƒ <= 3 || â˜ƒ >= 7) {
                     this.placeBlock(â˜ƒ, Blocks.OAK_PLANKS.defaultBlockState(), 4, 3, â˜ƒ, â˜ƒ);
                     this.placeBlock(â˜ƒ, Blocks.OAK_PLANKS.defaultBlockState(), 5, 3, â˜ƒ, â˜ƒ);
                     this.placeBlock(â˜ƒ, Blocks.OAK_PLANKS.defaultBlockState(), 6, 3, â˜ƒ, â˜ƒ);
                  }

                  this.placeBlock(â˜ƒ, Blocks.OAK_PLANKS.defaultBlockState(), 7, 3, â˜ƒ, â˜ƒ);
                  this.placeBlock(â˜ƒ, Blocks.OAK_PLANKS.defaultBlockState(), 8, 3, â˜ƒ, â˜ƒ);
               }

               BlockState â˜ƒ = Blocks.LADDER.defaultBlockState().setValue(LadderBlock.FACING, Direction.WEST);
               this.placeBlock(â˜ƒ, â˜ƒ, 9, 1, 3, â˜ƒ);
               this.placeBlock(â˜ƒ, â˜ƒ, 9, 2, 3, â˜ƒ);
               this.placeBlock(â˜ƒ, â˜ƒ, 9, 3, 3, â˜ƒ);
               this.createChest(â˜ƒ, â˜ƒ, â˜ƒ, 3, 4, 8, BuiltInLootTables.STRONGHOLD_CROSSING);
         }

         return true;
      }
   }

   static class SmoothStoneSelector extends StructurePiece.BlockSelector {
      @Override
      public void next(Random var1, int var2, int var3, int var4, boolean var5) {
         if (â˜ƒ) {
            float â˜ƒ = â˜ƒ.nextFloat();
            if (â˜ƒ < 0.2F) {
               this.next = Blocks.CRACKED_STONE_BRICKS.defaultBlockState();
            } else if (â˜ƒ < 0.5F) {
               this.next = Blocks.MOSSY_STONE_BRICKS.defaultBlockState();
            } else if (â˜ƒ < 0.55F) {
               this.next = Blocks.INFESTED_STONE_BRICKS.defaultBlockState();
            } else {
               this.next = Blocks.STONE_BRICKS.defaultBlockState();
            }
         } else {
            this.next = Blocks.CAVE_AIR.defaultBlockState();
         }
      }
   }

   public static class StairsDown extends StrongholdPieces.StrongholdPiece {
      private static final int WIDTH = 5;
      private static final int HEIGHT = 11;
      private static final int DEPTH = 5;
      private final boolean isSource;

      public StairsDown(StructurePieceType var1, int var2, int var3, int var4, Direction var5) {
         super(â˜ƒ, â˜ƒ, makeBoundingBox(â˜ƒ, 64, â˜ƒ, â˜ƒ, 5, 11, 5));
         this.isSource = true;
         this.setOrientation(â˜ƒ);
         this.entryDoor = StrongholdPieces.StrongholdPiece.SmallDoorType.OPENING;
      }

      public StairsDown(int var1, Random var2, BoundingBox var3, Direction var4) {
         super(StructurePieceType.STRONGHOLD_STAIRS_DOWN, â˜ƒ, â˜ƒ);
         this.isSource = false;
         this.setOrientation(â˜ƒ);
         this.entryDoor = this.randomSmallDoor(â˜ƒ);
      }

      public StairsDown(StructurePieceType var1, CompoundTag var2) {
         super(â˜ƒ, â˜ƒ);
         this.isSource = â˜ƒ.getBoolean("Source");
      }

      public StairsDown(ServerLevel var1, CompoundTag var2) {
         this(StructurePieceType.STRONGHOLD_STAIRS_DOWN, â˜ƒ);
      }

      @Override
      protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
         super.addAdditionalSaveData(â˜ƒ, â˜ƒ);
         â˜ƒ.putBoolean("Source", this.isSource);
      }

      @Override
      public void addChildren(StructurePiece var1, StructurePieceAccessor var2, Random var3) {
         if (this.isSource) {
            StrongholdPieces.imposedPiece = StrongholdPieces.FiveCrossing.class;
         }

         this.generateSmallDoorChildForward((StrongholdPieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 1, 1);
      }

      public static StrongholdPieces.StairsDown createPiece(StructurePieceAccessor var0, Random var1, int var2, int var3, int var4, Direction var5, int var6) {
         BoundingBox â˜ƒ = BoundingBox.orientBox(â˜ƒ, â˜ƒ, â˜ƒ, -1, -7, 0, 5, 11, 5, â˜ƒ);
         return isOkBox(â˜ƒ) && â˜ƒ.findCollisionPiece(â˜ƒ) == null ? new StrongholdPieces.StairsDown(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) : null;
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         this.generateBox(â˜ƒ, â˜ƒ, 0, 0, 0, 4, 10, 4, true, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);
         this.generateSmallDoor(â˜ƒ, â˜ƒ, â˜ƒ, this.entryDoor, 1, 7, 0);
         this.generateSmallDoor(â˜ƒ, â˜ƒ, â˜ƒ, StrongholdPieces.StrongholdPiece.SmallDoorType.OPENING, 1, 1, 4);
         this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 2, 6, 1, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 1, 5, 1, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 1, 6, 1, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 1, 5, 2, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 1, 4, 3, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 1, 5, 3, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 2, 4, 3, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 3, 3, 3, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 3, 4, 3, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 3, 3, 2, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 3, 2, 1, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 3, 3, 1, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 2, 2, 1, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 1, 1, 1, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 1, 2, 1, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 1, 1, 2, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 1, 1, 3, â˜ƒ);
         return true;
      }
   }

   public static class StartPiece extends StrongholdPieces.StairsDown {
      public StrongholdPieces.PieceWeight previousPiece;
      @Nullable
      public StrongholdPieces.PortalRoom portalRoomPiece;
      public final List<StructurePiece> pendingChildren = Lists.<StructurePiece>newArrayList();

      public StartPiece(Random var1, int var2, int var3) {
         super(StructurePieceType.STRONGHOLD_START, 0, â˜ƒ, â˜ƒ, getRandomHorizontalDirection(â˜ƒ));
      }

      public StartPiece(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.STRONGHOLD_START, â˜ƒ);
      }

      @Override
      public BlockPos getLocatorPosition() {
         return this.portalRoomPiece != null ? this.portalRoomPiece.getLocatorPosition() : super.getLocatorPosition();
      }
   }

   public static class Straight extends StrongholdPieces.StrongholdPiece {
      private static final int WIDTH = 5;
      private static final int HEIGHT = 5;
      private static final int DEPTH = 7;
      private final boolean leftChild;
      private final boolean rightChild;

      public Straight(int var1, Random var2, BoundingBox var3, Direction var4) {
         super(StructurePieceType.STRONGHOLD_STRAIGHT, â˜ƒ, â˜ƒ);
         this.setOrientation(â˜ƒ);
         this.entryDoor = this.randomSmallDoor(â˜ƒ);
         this.leftChild = â˜ƒ.nextInt(2) == 0;
         this.rightChild = â˜ƒ.nextInt(2) == 0;
      }

      public Straight(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.STRONGHOLD_STRAIGHT, â˜ƒ);
         this.leftChild = â˜ƒ.getBoolean("Left");
         this.rightChild = â˜ƒ.getBoolean("Right");
      }

      @Override
      protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
         super.addAdditionalSaveData(â˜ƒ, â˜ƒ);
         â˜ƒ.putBoolean("Left", this.leftChild);
         â˜ƒ.putBoolean("Right", this.rightChild);
      }

      @Override
      public void addChildren(StructurePiece var1, StructurePieceAccessor var2, Random var3) {
         this.generateSmallDoorChildForward((StrongholdPieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 1, 1);
         if (this.leftChild) {
            this.generateSmallDoorChildLeft((StrongholdPieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 1, 2);
         }

         if (this.rightChild) {
            this.generateSmallDoorChildRight((StrongholdPieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 1, 2);
         }
      }

      public static StrongholdPieces.Straight createPiece(StructurePieceAccessor var0, Random var1, int var2, int var3, int var4, Direction var5, int var6) {
         BoundingBox â˜ƒ = BoundingBox.orientBox(â˜ƒ, â˜ƒ, â˜ƒ, -1, -1, 0, 5, 5, 7, â˜ƒ);
         return isOkBox(â˜ƒ) && â˜ƒ.findCollisionPiece(â˜ƒ) == null ? new StrongholdPieces.Straight(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) : null;
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         this.generateBox(â˜ƒ, â˜ƒ, 0, 0, 0, 4, 4, 6, true, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);
         this.generateSmallDoor(â˜ƒ, â˜ƒ, â˜ƒ, this.entryDoor, 1, 1, 0);
         this.generateSmallDoor(â˜ƒ, â˜ƒ, â˜ƒ, StrongholdPieces.StrongholdPiece.SmallDoorType.OPENING, 1, 1, 6);
         BlockState â˜ƒ = Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, Direction.EAST);
         BlockState â˜ƒx = Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, Direction.WEST);
         this.maybeGenerateBlock(â˜ƒ, â˜ƒ, â˜ƒ, 0.1F, 1, 2, 1, â˜ƒ);
         this.maybeGenerateBlock(â˜ƒ, â˜ƒ, â˜ƒ, 0.1F, 3, 2, 1, â˜ƒx);
         this.maybeGenerateBlock(â˜ƒ, â˜ƒ, â˜ƒ, 0.1F, 1, 2, 5, â˜ƒ);
         this.maybeGenerateBlock(â˜ƒ, â˜ƒ, â˜ƒ, 0.1F, 3, 2, 5, â˜ƒx);
         if (this.leftChild) {
            this.generateBox(â˜ƒ, â˜ƒ, 0, 1, 2, 0, 3, 4, CAVE_AIR, CAVE_AIR, false);
         }

         if (this.rightChild) {
            this.generateBox(â˜ƒ, â˜ƒ, 4, 1, 2, 4, 3, 4, CAVE_AIR, CAVE_AIR, false);
         }

         return true;
      }
   }

   public static class StraightStairsDown extends StrongholdPieces.StrongholdPiece {
      private static final int WIDTH = 5;
      private static final int HEIGHT = 11;
      private static final int DEPTH = 8;

      public StraightStairsDown(int var1, Random var2, BoundingBox var3, Direction var4) {
         super(StructurePieceType.STRONGHOLD_STRAIGHT_STAIRS_DOWN, â˜ƒ, â˜ƒ);
         this.setOrientation(â˜ƒ);
         this.entryDoor = this.randomSmallDoor(â˜ƒ);
      }

      public StraightStairsDown(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.STRONGHOLD_STRAIGHT_STAIRS_DOWN, â˜ƒ);
      }

      @Override
      public void addChildren(StructurePiece var1, StructurePieceAccessor var2, Random var3) {
         this.generateSmallDoorChildForward((StrongholdPieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 1, 1);
      }

      public static StrongholdPieces.StraightStairsDown createPiece(
         StructurePieceAccessor var0, Random var1, int var2, int var3, int var4, Direction var5, int var6
      ) {
         BoundingBox â˜ƒ = BoundingBox.orientBox(â˜ƒ, â˜ƒ, â˜ƒ, -1, -7, 0, 5, 11, 8, â˜ƒ);
         return isOkBox(â˜ƒ) && â˜ƒ.findCollisionPiece(â˜ƒ) == null ? new StrongholdPieces.StraightStairsDown(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) : null;
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         this.generateBox(â˜ƒ, â˜ƒ, 0, 0, 0, 4, 10, 7, true, â˜ƒ, StrongholdPieces.SMOOTH_STONE_SELECTOR);
         this.generateSmallDoor(â˜ƒ, â˜ƒ, â˜ƒ, this.entryDoor, 1, 7, 0);
         this.generateSmallDoor(â˜ƒ, â˜ƒ, â˜ƒ, StrongholdPieces.StrongholdPiece.SmallDoorType.OPENING, 1, 1, 7);
         BlockState â˜ƒ = Blocks.COBBLESTONE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);

         for(int â˜ƒx = 0; â˜ƒx < 6; ++â˜ƒx) {
            this.placeBlock(â˜ƒ, â˜ƒ, 1, 6 - â˜ƒx, 1 + â˜ƒx, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒ, 2, 6 - â˜ƒx, 1 + â˜ƒx, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒ, 3, 6 - â˜ƒx, 1 + â˜ƒx, â˜ƒ);
            if (â˜ƒx < 5) {
               this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 1, 5 - â˜ƒx, 1 + â˜ƒx, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 2, 5 - â˜ƒx, 1 + â˜ƒx, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), 3, 5 - â˜ƒx, 1 + â˜ƒx, â˜ƒ);
            }
         }

         return true;
      }
   }

   abstract static class StrongholdPiece extends StructurePiece {
      protected StrongholdPieces.StrongholdPiece.SmallDoorType entryDoor = StrongholdPieces.StrongholdPiece.SmallDoorType.OPENING;

      protected StrongholdPiece(StructurePieceType var1, int var2, BoundingBox var3) {
         super(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public StrongholdPiece(StructurePieceType var1, CompoundTag var2) {
         super(â˜ƒ, â˜ƒ);
         this.entryDoor = StrongholdPieces.StrongholdPiece.SmallDoorType.valueOf(â˜ƒ.getString("EntryDoor"));
      }

      @Override
      public NoiseEffect getNoiseEffect() {
         return NoiseEffect.BURY;
      }

      @Override
      protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
         â˜ƒ.putString("EntryDoor", this.entryDoor.name());
      }

      protected void generateSmallDoor(
         WorldGenLevel var1, Random var2, BoundingBox var3, StrongholdPieces.StrongholdPiece.SmallDoorType var4, int var5, int var6, int var7
      ) {
         switch(â˜ƒ) {
            case OPENING:
               this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + 3 - 1, â˜ƒ + 3 - 1, â˜ƒ, CAVE_AIR, CAVE_AIR, false);
               break;
            case WOOD_DOOR:
               this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), â˜ƒ, â˜ƒ + 1, â˜ƒ, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), â˜ƒ, â˜ƒ + 2, â˜ƒ, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), â˜ƒ + 1, â˜ƒ + 2, â˜ƒ, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), â˜ƒ + 2, â˜ƒ + 2, â˜ƒ, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), â˜ƒ + 2, â˜ƒ + 1, â˜ƒ, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), â˜ƒ + 2, â˜ƒ, â˜ƒ, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.OAK_DOOR.defaultBlockState(), â˜ƒ + 1, â˜ƒ, â˜ƒ, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.OAK_DOOR.defaultBlockState().setValue(DoorBlock.HALF, DoubleBlockHalf.UPPER), â˜ƒ + 1, â˜ƒ + 1, â˜ƒ, â˜ƒ);
               break;
            case GRATES:
               this.placeBlock(â˜ƒ, Blocks.CAVE_AIR.defaultBlockState(), â˜ƒ + 1, â˜ƒ, â˜ƒ, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.CAVE_AIR.defaultBlockState(), â˜ƒ + 1, â˜ƒ + 1, â˜ƒ, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.IRON_BARS.defaultBlockState().setValue(IronBarsBlock.WEST, Boolean.valueOf(true)), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.IRON_BARS.defaultBlockState().setValue(IronBarsBlock.WEST, Boolean.valueOf(true)), â˜ƒ, â˜ƒ + 1, â˜ƒ, â˜ƒ);
               this.placeBlock(
                  â˜ƒ,
                  Blocks.IRON_BARS.defaultBlockState().setValue(IronBarsBlock.EAST, Boolean.valueOf(true)).setValue(IronBarsBlock.WEST, Boolean.valueOf(true)),
                  â˜ƒ,
                  â˜ƒ + 2,
                  â˜ƒ,
                  â˜ƒ
               );
               this.placeBlock(
                  â˜ƒ,
                  Blocks.IRON_BARS.defaultBlockState().setValue(IronBarsBlock.EAST, Boolean.valueOf(true)).setValue(IronBarsBlock.WEST, Boolean.valueOf(true)),
                  â˜ƒ + 1,
                  â˜ƒ + 2,
                  â˜ƒ,
                  â˜ƒ
               );
               this.placeBlock(
                  â˜ƒ,
                  Blocks.IRON_BARS.defaultBlockState().setValue(IronBarsBlock.EAST, Boolean.valueOf(true)).setValue(IronBarsBlock.WEST, Boolean.valueOf(true)),
                  â˜ƒ + 2,
                  â˜ƒ + 2,
                  â˜ƒ,
                  â˜ƒ
               );
               this.placeBlock(â˜ƒ, Blocks.IRON_BARS.defaultBlockState().setValue(IronBarsBlock.EAST, Boolean.valueOf(true)), â˜ƒ + 2, â˜ƒ + 1, â˜ƒ, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.IRON_BARS.defaultBlockState().setValue(IronBarsBlock.EAST, Boolean.valueOf(true)), â˜ƒ + 2, â˜ƒ, â˜ƒ, â˜ƒ);
               break;
            case IRON_DOOR:
               this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), â˜ƒ, â˜ƒ + 1, â˜ƒ, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), â˜ƒ, â˜ƒ + 2, â˜ƒ, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), â˜ƒ + 1, â˜ƒ + 2, â˜ƒ, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), â˜ƒ + 2, â˜ƒ + 2, â˜ƒ, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), â˜ƒ + 2, â˜ƒ + 1, â˜ƒ, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.STONE_BRICKS.defaultBlockState(), â˜ƒ + 2, â˜ƒ, â˜ƒ, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.IRON_DOOR.defaultBlockState(), â˜ƒ + 1, â˜ƒ, â˜ƒ, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.IRON_DOOR.defaultBlockState().setValue(DoorBlock.HALF, DoubleBlockHalf.UPPER), â˜ƒ + 1, â˜ƒ + 1, â˜ƒ, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.STONE_BUTTON.defaultBlockState().setValue(ButtonBlock.FACING, Direction.NORTH), â˜ƒ + 2, â˜ƒ + 1, â˜ƒ + 1, â˜ƒ);
               this.placeBlock(â˜ƒ, Blocks.STONE_BUTTON.defaultBlockState().setValue(ButtonBlock.FACING, Direction.SOUTH), â˜ƒ + 2, â˜ƒ + 1, â˜ƒ - 1, â˜ƒ);
         }
      }

      protected StrongholdPieces.StrongholdPiece.SmallDoorType randomSmallDoor(Random var1) {
         int â˜ƒ = â˜ƒ.nextInt(5);
         switch(â˜ƒ) {
            case 0:
            case 1:
            default:
               return StrongholdPieces.StrongholdPiece.SmallDoorType.OPENING;
            case 2:
               return StrongholdPieces.StrongholdPiece.SmallDoorType.WOOD_DOOR;
            case 3:
               return StrongholdPieces.StrongholdPiece.SmallDoorType.GRATES;
            case 4:
               return StrongholdPieces.StrongholdPiece.SmallDoorType.IRON_DOOR;
         }
      }

      @Nullable
      protected StructurePiece generateSmallDoorChildForward(StrongholdPieces.StartPiece var1, StructurePieceAccessor var2, Random var3, int var4, int var5) {
         Direction â˜ƒ = this.getOrientation();
         if (â˜ƒ != null) {
            switch(â˜ƒ) {
               case NORTH:
                  return StrongholdPieces.generateAndAddPiece(
                     â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX() + â˜ƒ, this.boundingBox.minY() + â˜ƒ, this.boundingBox.minZ() - 1, â˜ƒ, this.getGenDepth()
                  );
               case SOUTH:
                  return StrongholdPieces.generateAndAddPiece(
                     â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX() + â˜ƒ, this.boundingBox.minY() + â˜ƒ, this.boundingBox.maxZ() + 1, â˜ƒ, this.getGenDepth()
                  );
               case WEST:
                  return StrongholdPieces.generateAndAddPiece(
                     â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX() - 1, this.boundingBox.minY() + â˜ƒ, this.boundingBox.minZ() + â˜ƒ, â˜ƒ, this.getGenDepth()
                  );
               case EAST:
                  return StrongholdPieces.generateAndAddPiece(
                     â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.maxX() + 1, this.boundingBox.minY() + â˜ƒ, this.boundingBox.minZ() + â˜ƒ, â˜ƒ, this.getGenDepth()
                  );
            }
         }

         return null;
      }

      @Nullable
      protected StructurePiece generateSmallDoorChildLeft(StrongholdPieces.StartPiece var1, StructurePieceAccessor var2, Random var3, int var4, int var5) {
         Direction â˜ƒ = this.getOrientation();
         if (â˜ƒ != null) {
            switch(â˜ƒ) {
               case NORTH:
                  return StrongholdPieces.generateAndAddPiece(
                     â˜ƒ,
                     â˜ƒ,
                     â˜ƒ,
                     this.boundingBox.minX() - 1,
                     this.boundingBox.minY() + â˜ƒ,
                     this.boundingBox.minZ() + â˜ƒ,
                     Direction.WEST,
                     this.getGenDepth()
                  );
               case SOUTH:
                  return StrongholdPieces.generateAndAddPiece(
                     â˜ƒ,
                     â˜ƒ,
                     â˜ƒ,
                     this.boundingBox.minX() - 1,
                     this.boundingBox.minY() + â˜ƒ,
                     this.boundingBox.minZ() + â˜ƒ,
                     Direction.WEST,
                     this.getGenDepth()
                  );
               case WEST:
                  return StrongholdPieces.generateAndAddPiece(
                     â˜ƒ,
                     â˜ƒ,
                     â˜ƒ,
                     this.boundingBox.minX() + â˜ƒ,
                     this.boundingBox.minY() + â˜ƒ,
                     this.boundingBox.minZ() - 1,
                     Direction.NORTH,
                     this.getGenDepth()
                  );
               case EAST:
                  return StrongholdPieces.generateAndAddPiece(
                     â˜ƒ,
                     â˜ƒ,
                     â˜ƒ,
                     this.boundingBox.minX() + â˜ƒ,
                     this.boundingBox.minY() + â˜ƒ,
                     this.boundingBox.minZ() - 1,
                     Direction.NORTH,
                     this.getGenDepth()
                  );
            }
         }

         return null;
      }

      @Nullable
      protected StructurePiece generateSmallDoorChildRight(StrongholdPieces.StartPiece var1, StructurePieceAccessor var2, Random var3, int var4, int var5) {
         Direction â˜ƒ = this.getOrientation();
         if (â˜ƒ != null) {
            switch(â˜ƒ) {
               case NORTH:
                  return StrongholdPieces.generateAndAddPiece(
                     â˜ƒ,
                     â˜ƒ,
                     â˜ƒ,
                     this.boundingBox.maxX() + 1,
                     this.boundingBox.minY() + â˜ƒ,
                     this.boundingBox.minZ() + â˜ƒ,
                     Direction.EAST,
                     this.getGenDepth()
                  );
               case SOUTH:
                  return StrongholdPieces.generateAndAddPiece(
                     â˜ƒ,
                     â˜ƒ,
                     â˜ƒ,
                     this.boundingBox.maxX() + 1,
                     this.boundingBox.minY() + â˜ƒ,
                     this.boundingBox.minZ() + â˜ƒ,
                     Direction.EAST,
                     this.getGenDepth()
                  );
               case WEST:
                  return StrongholdPieces.generateAndAddPiece(
                     â˜ƒ,
                     â˜ƒ,
                     â˜ƒ,
                     this.boundingBox.minX() + â˜ƒ,
                     this.boundingBox.minY() + â˜ƒ,
                     this.boundingBox.maxZ() + 1,
                     Direction.SOUTH,
                     this.getGenDepth()
                  );
               case EAST:
                  return StrongholdPieces.generateAndAddPiece(
                     â˜ƒ,
                     â˜ƒ,
                     â˜ƒ,
                     this.boundingBox.minX() + â˜ƒ,
                     this.boundingBox.minY() + â˜ƒ,
                     this.boundingBox.maxZ() + 1,
                     Direction.SOUTH,
                     this.getGenDepth()
                  );
            }
         }

         return null;
      }

      protected static boolean isOkBox(BoundingBox var0) {
         return â˜ƒ != null && â˜ƒ.minY() > 10;
      }

      protected static enum SmallDoorType {
         OPENING,
         WOOD_DOOR,
         GRATES,
         IRON_DOOR;
      }
   }

   public abstract static class Turn extends StrongholdPieces.StrongholdPiece {
      protected static final int WIDTH = 5;
      protected static final int HEIGHT = 5;
      protected static final int DEPTH = 5;

      protected Turn(StructurePieceType var1, int var2, BoundingBox var3) {
         super(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public Turn(StructurePieceType var1, CompoundTag var2) {
         super(â˜ƒ, â˜ƒ);
      }
   }
}
