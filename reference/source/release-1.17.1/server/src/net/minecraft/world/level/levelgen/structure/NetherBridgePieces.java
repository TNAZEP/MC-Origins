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
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class NetherBridgePieces {
   private static final int MAX_DEPTH = 30;
   private static final int LOWEST_Y_POSITION = 10;
   static final NetherBridgePieces.PieceWeight[] BRIDGE_PIECE_WEIGHTS = new NetherBridgePieces.PieceWeight[]{
      new NetherBridgePieces.PieceWeight(NetherBridgePieces.BridgeStraight.class, 30, 0, true),
      new NetherBridgePieces.PieceWeight(NetherBridgePieces.BridgeCrossing.class, 10, 4),
      new NetherBridgePieces.PieceWeight(NetherBridgePieces.RoomCrossing.class, 10, 4),
      new NetherBridgePieces.PieceWeight(NetherBridgePieces.StairsRoom.class, 10, 3),
      new NetherBridgePieces.PieceWeight(NetherBridgePieces.MonsterThrone.class, 5, 2),
      new NetherBridgePieces.PieceWeight(NetherBridgePieces.CastleEntrance.class, 5, 1)
   };
   static final NetherBridgePieces.PieceWeight[] CASTLE_PIECE_WEIGHTS = new NetherBridgePieces.PieceWeight[]{
      new NetherBridgePieces.PieceWeight(NetherBridgePieces.CastleSmallCorridorPiece.class, 25, 0, true),
      new NetherBridgePieces.PieceWeight(NetherBridgePieces.CastleSmallCorridorCrossingPiece.class, 15, 5),
      new NetherBridgePieces.PieceWeight(NetherBridgePieces.CastleSmallCorridorRightTurnPiece.class, 5, 10),
      new NetherBridgePieces.PieceWeight(NetherBridgePieces.CastleSmallCorridorLeftTurnPiece.class, 5, 10),
      new NetherBridgePieces.PieceWeight(NetherBridgePieces.CastleCorridorStairsPiece.class, 10, 3, true),
      new NetherBridgePieces.PieceWeight(NetherBridgePieces.CastleCorridorTBalconyPiece.class, 7, 2),
      new NetherBridgePieces.PieceWeight(NetherBridgePieces.CastleStalkRoom.class, 5, 2)
   };

   static NetherBridgePieces.NetherBridgePiece findAndCreateBridgePieceFactory(
      NetherBridgePieces.PieceWeight var0, StructurePieceAccessor var1, Random var2, int var3, int var4, int var5, Direction var6, int var7
   ) {
      Class<? extends NetherBridgePieces.NetherBridgePiece> â˜ƒ = â˜ƒ.pieceClass;
      NetherBridgePieces.NetherBridgePiece â˜ƒx = null;
      if (â˜ƒ == NetherBridgePieces.BridgeStraight.class) {
         â˜ƒx = NetherBridgePieces.BridgeStraight.createPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (â˜ƒ == NetherBridgePieces.BridgeCrossing.class) {
         â˜ƒx = NetherBridgePieces.BridgeCrossing.createPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (â˜ƒ == NetherBridgePieces.RoomCrossing.class) {
         â˜ƒx = NetherBridgePieces.RoomCrossing.createPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (â˜ƒ == NetherBridgePieces.StairsRoom.class) {
         â˜ƒx = NetherBridgePieces.StairsRoom.createPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (â˜ƒ == NetherBridgePieces.MonsterThrone.class) {
         â˜ƒx = NetherBridgePieces.MonsterThrone.createPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (â˜ƒ == NetherBridgePieces.CastleEntrance.class) {
         â˜ƒx = NetherBridgePieces.CastleEntrance.createPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (â˜ƒ == NetherBridgePieces.CastleSmallCorridorPiece.class) {
         â˜ƒx = NetherBridgePieces.CastleSmallCorridorPiece.createPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (â˜ƒ == NetherBridgePieces.CastleSmallCorridorRightTurnPiece.class) {
         â˜ƒx = NetherBridgePieces.CastleSmallCorridorRightTurnPiece.createPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (â˜ƒ == NetherBridgePieces.CastleSmallCorridorLeftTurnPiece.class) {
         â˜ƒx = NetherBridgePieces.CastleSmallCorridorLeftTurnPiece.createPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (â˜ƒ == NetherBridgePieces.CastleCorridorStairsPiece.class) {
         â˜ƒx = NetherBridgePieces.CastleCorridorStairsPiece.createPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (â˜ƒ == NetherBridgePieces.CastleCorridorTBalconyPiece.class) {
         â˜ƒx = NetherBridgePieces.CastleCorridorTBalconyPiece.createPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (â˜ƒ == NetherBridgePieces.CastleSmallCorridorCrossingPiece.class) {
         â˜ƒx = NetherBridgePieces.CastleSmallCorridorCrossingPiece.createPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (â˜ƒ == NetherBridgePieces.CastleStalkRoom.class) {
         â˜ƒx = NetherBridgePieces.CastleStalkRoom.createPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      return â˜ƒx;
   }

   public static class BridgeCrossing extends NetherBridgePieces.NetherBridgePiece {
      private static final int WIDTH = 19;
      private static final int HEIGHT = 10;
      private static final int DEPTH = 19;

      public BridgeCrossing(int var1, BoundingBox var2, Direction var3) {
         super(StructurePieceType.NETHER_FORTRESS_BRIDGE_CROSSING, â˜ƒ, â˜ƒ);
         this.setOrientation(â˜ƒ);
      }

      protected BridgeCrossing(int var1, int var2, Direction var3) {
         super(StructurePieceType.NETHER_FORTRESS_BRIDGE_CROSSING, 0, StructurePiece.makeBoundingBox(â˜ƒ, 64, â˜ƒ, â˜ƒ, 19, 10, 19));
         this.setOrientation(â˜ƒ);
      }

      protected BridgeCrossing(StructurePieceType var1, CompoundTag var2) {
         super(â˜ƒ, â˜ƒ);
      }

      public BridgeCrossing(ServerLevel var1, CompoundTag var2) {
         this(StructurePieceType.NETHER_FORTRESS_BRIDGE_CROSSING, â˜ƒ);
      }

      @Override
      public void addChildren(StructurePiece var1, StructurePieceAccessor var2, Random var3) {
         this.generateChildForward((NetherBridgePieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 8, 3, false);
         this.generateChildLeft((NetherBridgePieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 3, 8, false);
         this.generateChildRight((NetherBridgePieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 3, 8, false);
      }

      public static NetherBridgePieces.BridgeCrossing createPiece(StructurePieceAccessor var0, int var1, int var2, int var3, Direction var4, int var5) {
         BoundingBox â˜ƒ = BoundingBox.orientBox(â˜ƒ, â˜ƒ, â˜ƒ, -8, -3, 0, 19, 10, 19, â˜ƒ);
         return isOkBox(â˜ƒ) && â˜ƒ.findCollisionPiece(â˜ƒ) == null ? new NetherBridgePieces.BridgeCrossing(â˜ƒ, â˜ƒ, â˜ƒ) : null;
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         this.generateBox(â˜ƒ, â˜ƒ, 7, 3, 0, 11, 4, 18, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 3, 7, 18, 4, 11, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 8, 5, 0, 10, 7, 18, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 5, 8, 18, 7, 10, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 7, 5, 0, 7, 5, 7, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 7, 5, 11, 7, 5, 18, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 11, 5, 0, 11, 5, 7, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 11, 5, 11, 11, 5, 18, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 5, 7, 7, 5, 7, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 11, 5, 7, 18, 5, 7, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 5, 11, 7, 5, 11, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 11, 5, 11, 18, 5, 11, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 7, 2, 0, 11, 2, 5, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 7, 2, 13, 11, 2, 18, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 7, 0, 0, 11, 1, 3, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 7, 0, 15, 11, 1, 18, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);

         for(int â˜ƒ = 7; â˜ƒ <= 11; ++â˜ƒ) {
            for(int â˜ƒx = 0; â˜ƒx <= 2; ++â˜ƒx) {
               this.fillColumnDown(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), â˜ƒ, -1, â˜ƒx, â˜ƒ);
               this.fillColumnDown(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), â˜ƒ, -1, 18 - â˜ƒx, â˜ƒ);
            }
         }

         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 7, 5, 2, 11, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 13, 2, 7, 18, 2, 11, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 0, 7, 3, 1, 11, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 15, 0, 7, 18, 1, 11, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);

         for(int â˜ƒ = 0; â˜ƒ <= 2; ++â˜ƒ) {
            for(int â˜ƒx = 7; â˜ƒx <= 11; ++â˜ƒx) {
               this.fillColumnDown(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), â˜ƒ, -1, â˜ƒx, â˜ƒ);
               this.fillColumnDown(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), 18 - â˜ƒ, -1, â˜ƒx, â˜ƒ);
            }
         }

         return true;
      }
   }

   public static class BridgeEndFiller extends NetherBridgePieces.NetherBridgePiece {
      private static final int WIDTH = 5;
      private static final int HEIGHT = 10;
      private static final int DEPTH = 8;
      private final int selfSeed;

      public BridgeEndFiller(int var1, Random var2, BoundingBox var3, Direction var4) {
         super(StructurePieceType.NETHER_FORTRESS_BRIDGE_END_FILLER, â˜ƒ, â˜ƒ);
         this.setOrientation(â˜ƒ);
         this.selfSeed = â˜ƒ.nextInt();
      }

      public BridgeEndFiller(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.NETHER_FORTRESS_BRIDGE_END_FILLER, â˜ƒ);
         this.selfSeed = â˜ƒ.getInt("Seed");
      }

      public static NetherBridgePieces.BridgeEndFiller createPiece(
         StructurePieceAccessor var0, Random var1, int var2, int var3, int var4, Direction var5, int var6
      ) {
         BoundingBox â˜ƒ = BoundingBox.orientBox(â˜ƒ, â˜ƒ, â˜ƒ, -1, -3, 0, 5, 10, 8, â˜ƒ);
         return isOkBox(â˜ƒ) && â˜ƒ.findCollisionPiece(â˜ƒ) == null ? new NetherBridgePieces.BridgeEndFiller(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) : null;
      }

      @Override
      protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
         super.addAdditionalSaveData(â˜ƒ, â˜ƒ);
         â˜ƒ.putInt("Seed", this.selfSeed);
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         Random â˜ƒ = new Random((long)this.selfSeed);

         for(int â˜ƒx = 0; â˜ƒx <= 4; ++â˜ƒx) {
            for(int â˜ƒxx = 3; â˜ƒxx <= 4; ++â˜ƒxx) {
               int â˜ƒxxx = â˜ƒ.nextInt(8);
               this.generateBox(
                  â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx, 0, â˜ƒx, â˜ƒxx, â˜ƒxxx, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false
               );
            }
         }

         int â˜ƒx = â˜ƒ.nextInt(8);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 5, 0, 0, 5, â˜ƒx, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         â˜ƒx = â˜ƒ.nextInt(8);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 5, 0, 4, 5, â˜ƒx, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);

         for(int â˜ƒxx = 0; â˜ƒxx <= 4; ++â˜ƒxx) {
            int â˜ƒxxx = â˜ƒ.nextInt(5);
            this.generateBox(â˜ƒ, â˜ƒ, â˜ƒxx, 2, 0, â˜ƒxx, 2, â˜ƒxxx, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         }

         for(int â˜ƒxx = 0; â˜ƒxx <= 4; ++â˜ƒxx) {
            for(int â˜ƒxxx = 0; â˜ƒxxx <= 1; ++â˜ƒxxx) {
               int â˜ƒxxxx = â˜ƒ.nextInt(3);
               this.generateBox(
                  â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒxxx, 0, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false
               );
            }
         }

         return true;
      }
   }

   public static class BridgeStraight extends NetherBridgePieces.NetherBridgePiece {
      private static final int WIDTH = 5;
      private static final int HEIGHT = 10;
      private static final int DEPTH = 19;

      public BridgeStraight(int var1, Random var2, BoundingBox var3, Direction var4) {
         super(StructurePieceType.NETHER_FORTRESS_BRIDGE_STRAIGHT, â˜ƒ, â˜ƒ);
         this.setOrientation(â˜ƒ);
      }

      public BridgeStraight(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.NETHER_FORTRESS_BRIDGE_STRAIGHT, â˜ƒ);
      }

      @Override
      public void addChildren(StructurePiece var1, StructurePieceAccessor var2, Random var3) {
         this.generateChildForward((NetherBridgePieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 1, 3, false);
      }

      public static NetherBridgePieces.BridgeStraight createPiece(
         StructurePieceAccessor var0, Random var1, int var2, int var3, int var4, Direction var5, int var6
      ) {
         BoundingBox â˜ƒ = BoundingBox.orientBox(â˜ƒ, â˜ƒ, â˜ƒ, -1, -3, 0, 5, 10, 19, â˜ƒ);
         return isOkBox(â˜ƒ) && â˜ƒ.findCollisionPiece(â˜ƒ) == null ? new NetherBridgePieces.BridgeStraight(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) : null;
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         this.generateBox(â˜ƒ, â˜ƒ, 0, 3, 0, 4, 4, 18, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 5, 0, 3, 7, 18, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 5, 0, 0, 5, 18, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 5, 0, 4, 5, 18, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 0, 4, 2, 5, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 13, 4, 2, 18, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 0, 0, 4, 1, 3, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 0, 15, 4, 1, 18, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);

         for(int â˜ƒ = 0; â˜ƒ <= 4; ++â˜ƒ) {
            for(int â˜ƒx = 0; â˜ƒx <= 2; ++â˜ƒx) {
               this.fillColumnDown(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), â˜ƒ, -1, â˜ƒx, â˜ƒ);
               this.fillColumnDown(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), â˜ƒ, -1, 18 - â˜ƒx, â˜ƒ);
            }
         }

         BlockState â˜ƒ = Blocks.NETHER_BRICK_FENCE
            .defaultBlockState()
            .setValue(FenceBlock.NORTH, Boolean.valueOf(true))
            .setValue(FenceBlock.SOUTH, Boolean.valueOf(true));
         BlockState â˜ƒx = â˜ƒ.setValue(FenceBlock.EAST, Boolean.valueOf(true));
         BlockState â˜ƒxx = â˜ƒ.setValue(FenceBlock.WEST, Boolean.valueOf(true));
         this.generateBox(â˜ƒ, â˜ƒ, 0, 1, 1, 0, 4, 1, â˜ƒx, â˜ƒx, false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 3, 4, 0, 4, 4, â˜ƒx, â˜ƒx, false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 3, 14, 0, 4, 14, â˜ƒx, â˜ƒx, false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 1, 17, 0, 4, 17, â˜ƒx, â˜ƒx, false);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 1, 1, 4, 4, 1, â˜ƒxx, â˜ƒxx, false);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 3, 4, 4, 4, 4, â˜ƒxx, â˜ƒxx, false);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 3, 14, 4, 4, 14, â˜ƒxx, â˜ƒxx, false);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 1, 17, 4, 4, 17, â˜ƒxx, â˜ƒxx, false);
         return true;
      }
   }

   public static class CastleCorridorStairsPiece extends NetherBridgePieces.NetherBridgePiece {
      private static final int WIDTH = 5;
      private static final int HEIGHT = 14;
      private static final int DEPTH = 10;

      public CastleCorridorStairsPiece(int var1, BoundingBox var2, Direction var3) {
         super(StructurePieceType.NETHER_FORTRESS_CASTLE_CORRIDOR_STAIRS, â˜ƒ, â˜ƒ);
         this.setOrientation(â˜ƒ);
      }

      public CastleCorridorStairsPiece(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.NETHER_FORTRESS_CASTLE_CORRIDOR_STAIRS, â˜ƒ);
      }

      @Override
      public void addChildren(StructurePiece var1, StructurePieceAccessor var2, Random var3) {
         this.generateChildForward((NetherBridgePieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 1, 0, true);
      }

      public static NetherBridgePieces.CastleCorridorStairsPiece createPiece(
         StructurePieceAccessor var0, int var1, int var2, int var3, Direction var4, int var5
      ) {
         BoundingBox â˜ƒ = BoundingBox.orientBox(â˜ƒ, â˜ƒ, â˜ƒ, -1, -7, 0, 5, 14, 10, â˜ƒ);
         return isOkBox(â˜ƒ) && â˜ƒ.findCollisionPiece(â˜ƒ) == null ? new NetherBridgePieces.CastleCorridorStairsPiece(â˜ƒ, â˜ƒ, â˜ƒ) : null;
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         BlockState â˜ƒ = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
         BlockState â˜ƒx = Blocks.NETHER_BRICK_FENCE
            .defaultBlockState()
            .setValue(FenceBlock.NORTH, Boolean.valueOf(true))
            .setValue(FenceBlock.SOUTH, Boolean.valueOf(true));

         for(int â˜ƒxx = 0; â˜ƒxx <= 9; ++â˜ƒxx) {
            int â˜ƒxxx = Math.max(1, 7 - â˜ƒxx);
            int â˜ƒxxxx = Math.min(Math.max(â˜ƒxxx + 5, 14 - â˜ƒxx), 13);
            int â˜ƒxxxxx = â˜ƒxx;
            this.generateBox(â˜ƒ, â˜ƒ, 0, 0, â˜ƒxx, 4, â˜ƒxxx, â˜ƒxx, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
            this.generateBox(â˜ƒ, â˜ƒ, 1, â˜ƒxxx + 1, â˜ƒxx, 3, â˜ƒxxxx - 1, â˜ƒxx, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
            if (â˜ƒxx <= 6) {
               this.placeBlock(â˜ƒ, â˜ƒ, 1, â˜ƒxxx + 1, â˜ƒxx, â˜ƒ);
               this.placeBlock(â˜ƒ, â˜ƒ, 2, â˜ƒxxx + 1, â˜ƒxx, â˜ƒ);
               this.placeBlock(â˜ƒ, â˜ƒ, 3, â˜ƒxxx + 1, â˜ƒxx, â˜ƒ);
            }

            this.generateBox(
               â˜ƒ, â˜ƒ, 0, â˜ƒxxxx, â˜ƒxx, 4, â˜ƒxxxx, â˜ƒxx, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false
            );
            this.generateBox(
               â˜ƒ, â˜ƒ, 0, â˜ƒxxx + 1, â˜ƒxx, 0, â˜ƒxxxx - 1, â˜ƒxx, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false
            );
            this.generateBox(
               â˜ƒ, â˜ƒ, 4, â˜ƒxxx + 1, â˜ƒxx, 4, â˜ƒxxxx - 1, â˜ƒxx, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false
            );
            if ((â˜ƒxx & 1) == 0) {
               this.generateBox(â˜ƒ, â˜ƒ, 0, â˜ƒxxx + 2, â˜ƒxx, 0, â˜ƒxxx + 3, â˜ƒxx, â˜ƒx, â˜ƒx, false);
               this.generateBox(â˜ƒ, â˜ƒ, 4, â˜ƒxxx + 2, â˜ƒxx, 4, â˜ƒxxx + 3, â˜ƒxx, â˜ƒx, â˜ƒx, false);
            }

            for(int â˜ƒxxx = 0; â˜ƒxxx <= 4; ++â˜ƒxxx) {
               this.fillColumnDown(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), â˜ƒxxx, -1, â˜ƒxxxxx, â˜ƒ);
            }
         }

         return true;
      }
   }

   public static class CastleCorridorTBalconyPiece extends NetherBridgePieces.NetherBridgePiece {
      private static final int WIDTH = 9;
      private static final int HEIGHT = 7;
      private static final int DEPTH = 9;

      public CastleCorridorTBalconyPiece(int var1, BoundingBox var2, Direction var3) {
         super(StructurePieceType.NETHER_FORTRESS_CASTLE_CORRIDOR_T_BALCONY, â˜ƒ, â˜ƒ);
         this.setOrientation(â˜ƒ);
      }

      public CastleCorridorTBalconyPiece(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.NETHER_FORTRESS_CASTLE_CORRIDOR_T_BALCONY, â˜ƒ);
      }

      @Override
      public void addChildren(StructurePiece var1, StructurePieceAccessor var2, Random var3) {
         int â˜ƒ = 1;
         Direction â˜ƒx = this.getOrientation();
         if (â˜ƒx == Direction.WEST || â˜ƒx == Direction.NORTH) {
            â˜ƒ = 5;
         }

         this.generateChildLeft((NetherBridgePieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 0, â˜ƒ, â˜ƒ.nextInt(8) > 0);
         this.generateChildRight((NetherBridgePieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 0, â˜ƒ, â˜ƒ.nextInt(8) > 0);
      }

      public static NetherBridgePieces.CastleCorridorTBalconyPiece createPiece(
         StructurePieceAccessor var0, int var1, int var2, int var3, Direction var4, int var5
      ) {
         BoundingBox â˜ƒ = BoundingBox.orientBox(â˜ƒ, â˜ƒ, â˜ƒ, -3, 0, 0, 9, 7, 9, â˜ƒ);
         return isOkBox(â˜ƒ) && â˜ƒ.findCollisionPiece(â˜ƒ) == null ? new NetherBridgePieces.CastleCorridorTBalconyPiece(â˜ƒ, â˜ƒ, â˜ƒ) : null;
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         BlockState â˜ƒ = Blocks.NETHER_BRICK_FENCE
            .defaultBlockState()
            .setValue(FenceBlock.NORTH, Boolean.valueOf(true))
            .setValue(FenceBlock.SOUTH, Boolean.valueOf(true));
         BlockState â˜ƒx = Blocks.NETHER_BRICK_FENCE
            .defaultBlockState()
            .setValue(FenceBlock.WEST, Boolean.valueOf(true))
            .setValue(FenceBlock.EAST, Boolean.valueOf(true));
         this.generateBox(â˜ƒ, â˜ƒ, 0, 0, 0, 8, 1, 8, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 0, 8, 5, 8, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 6, 0, 8, 6, 5, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 0, 2, 5, 0, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 6, 2, 0, 8, 5, 0, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 3, 0, 1, 4, 0, â˜ƒx, â˜ƒx, false);
         this.generateBox(â˜ƒ, â˜ƒ, 7, 3, 0, 7, 4, 0, â˜ƒx, â˜ƒx, false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 4, 8, 2, 8, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 1, 4, 2, 2, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 6, 1, 4, 7, 2, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 3, 8, 7, 3, 8, â˜ƒx, â˜ƒx, false);
         this.placeBlock(
            â˜ƒ,
            Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.EAST, Boolean.valueOf(true)).setValue(FenceBlock.SOUTH, Boolean.valueOf(true)),
            0,
            3,
            8,
            â˜ƒ
         );
         this.placeBlock(
            â˜ƒ,
            Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.WEST, Boolean.valueOf(true)).setValue(FenceBlock.SOUTH, Boolean.valueOf(true)),
            8,
            3,
            8,
            â˜ƒ
         );
         this.generateBox(â˜ƒ, â˜ƒ, 0, 3, 6, 0, 3, 7, â˜ƒ, â˜ƒ, false);
         this.generateBox(â˜ƒ, â˜ƒ, 8, 3, 6, 8, 3, 7, â˜ƒ, â˜ƒ, false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 3, 4, 0, 5, 5, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 8, 3, 4, 8, 5, 5, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 3, 5, 2, 5, 5, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 6, 3, 5, 7, 5, 5, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 4, 5, 1, 5, 5, â˜ƒx, â˜ƒx, false);
         this.generateBox(â˜ƒ, â˜ƒ, 7, 4, 5, 7, 5, 5, â˜ƒx, â˜ƒx, false);

         for(int â˜ƒxx = 0; â˜ƒxx <= 5; ++â˜ƒxx) {
            for(int â˜ƒxxx = 0; â˜ƒxxx <= 8; ++â˜ƒxxx) {
               this.fillColumnDown(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), â˜ƒxxx, -1, â˜ƒxx, â˜ƒ);
            }
         }

         return true;
      }
   }

   public static class CastleEntrance extends NetherBridgePieces.NetherBridgePiece {
      private static final int WIDTH = 13;
      private static final int HEIGHT = 14;
      private static final int DEPTH = 13;

      public CastleEntrance(int var1, Random var2, BoundingBox var3, Direction var4) {
         super(StructurePieceType.NETHER_FORTRESS_CASTLE_ENTRANCE, â˜ƒ, â˜ƒ);
         this.setOrientation(â˜ƒ);
      }

      public CastleEntrance(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.NETHER_FORTRESS_CASTLE_ENTRANCE, â˜ƒ);
      }

      @Override
      public void addChildren(StructurePiece var1, StructurePieceAccessor var2, Random var3) {
         this.generateChildForward((NetherBridgePieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 5, 3, true);
      }

      public static NetherBridgePieces.CastleEntrance createPiece(
         StructurePieceAccessor var0, Random var1, int var2, int var3, int var4, Direction var5, int var6
      ) {
         BoundingBox â˜ƒ = BoundingBox.orientBox(â˜ƒ, â˜ƒ, â˜ƒ, -5, -3, 0, 13, 14, 13, â˜ƒ);
         return isOkBox(â˜ƒ) && â˜ƒ.findCollisionPiece(â˜ƒ) == null ? new NetherBridgePieces.CastleEntrance(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) : null;
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         this.generateBox(â˜ƒ, â˜ƒ, 0, 3, 0, 12, 4, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 5, 0, 12, 13, 12, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 5, 0, 1, 12, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 11, 5, 0, 12, 12, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 5, 11, 4, 12, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 8, 5, 11, 10, 12, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 9, 11, 7, 12, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 5, 0, 4, 12, 1, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 8, 5, 0, 10, 12, 1, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 9, 0, 7, 12, 1, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 11, 2, 10, 12, 10, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 8, 0, 7, 8, 0, Blocks.NETHER_BRICK_FENCE.defaultBlockState(), Blocks.NETHER_BRICK_FENCE.defaultBlockState(), false);
         BlockState â˜ƒ = Blocks.NETHER_BRICK_FENCE
            .defaultBlockState()
            .setValue(FenceBlock.WEST, Boolean.valueOf(true))
            .setValue(FenceBlock.EAST, Boolean.valueOf(true));
         BlockState â˜ƒx = Blocks.NETHER_BRICK_FENCE
            .defaultBlockState()
            .setValue(FenceBlock.NORTH, Boolean.valueOf(true))
            .setValue(FenceBlock.SOUTH, Boolean.valueOf(true));

         for(int â˜ƒxx = 1; â˜ƒxx <= 11; â˜ƒxx += 2) {
            this.generateBox(â˜ƒ, â˜ƒ, â˜ƒxx, 10, 0, â˜ƒxx, 11, 0, â˜ƒ, â˜ƒ, false);
            this.generateBox(â˜ƒ, â˜ƒ, â˜ƒxx, 10, 12, â˜ƒxx, 11, 12, â˜ƒ, â˜ƒ, false);
            this.generateBox(â˜ƒ, â˜ƒ, 0, 10, â˜ƒxx, 0, 11, â˜ƒxx, â˜ƒx, â˜ƒx, false);
            this.generateBox(â˜ƒ, â˜ƒ, 12, 10, â˜ƒxx, 12, 11, â˜ƒxx, â˜ƒx, â˜ƒx, false);
            this.placeBlock(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), â˜ƒxx, 13, 0, â˜ƒ);
            this.placeBlock(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), â˜ƒxx, 13, 12, â˜ƒ);
            this.placeBlock(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), 0, 13, â˜ƒxx, â˜ƒ);
            this.placeBlock(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), 12, 13, â˜ƒxx, â˜ƒ);
            if (â˜ƒxx != 11) {
               this.placeBlock(â˜ƒ, â˜ƒ, â˜ƒxx + 1, 13, 0, â˜ƒ);
               this.placeBlock(â˜ƒ, â˜ƒ, â˜ƒxx + 1, 13, 12, â˜ƒ);
               this.placeBlock(â˜ƒ, â˜ƒx, 0, 13, â˜ƒxx + 1, â˜ƒ);
               this.placeBlock(â˜ƒ, â˜ƒx, 12, 13, â˜ƒxx + 1, â˜ƒ);
            }
         }

         this.placeBlock(
            â˜ƒ,
            Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.NORTH, Boolean.valueOf(true)).setValue(FenceBlock.EAST, Boolean.valueOf(true)),
            0,
            13,
            0,
            â˜ƒ
         );
         this.placeBlock(
            â˜ƒ,
            Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.SOUTH, Boolean.valueOf(true)).setValue(FenceBlock.EAST, Boolean.valueOf(true)),
            0,
            13,
            12,
            â˜ƒ
         );
         this.placeBlock(
            â˜ƒ,
            Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.SOUTH, Boolean.valueOf(true)).setValue(FenceBlock.WEST, Boolean.valueOf(true)),
            12,
            13,
            12,
            â˜ƒ
         );
         this.placeBlock(
            â˜ƒ,
            Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.NORTH, Boolean.valueOf(true)).setValue(FenceBlock.WEST, Boolean.valueOf(true)),
            12,
            13,
            0,
            â˜ƒ
         );

         for(int â˜ƒxx = 3; â˜ƒxx <= 9; â˜ƒxx += 2) {
            this.generateBox(
               â˜ƒ,
               â˜ƒ,
               1,
               7,
               â˜ƒxx,
               1,
               8,
               â˜ƒxx,
               â˜ƒx.setValue(FenceBlock.WEST, Boolean.valueOf(true)),
               â˜ƒx.setValue(FenceBlock.WEST, Boolean.valueOf(true)),
               false
            );
            this.generateBox(
               â˜ƒ,
               â˜ƒ,
               11,
               7,
               â˜ƒxx,
               11,
               8,
               â˜ƒxx,
               â˜ƒx.setValue(FenceBlock.EAST, Boolean.valueOf(true)),
               â˜ƒx.setValue(FenceBlock.EAST, Boolean.valueOf(true)),
               false
            );
         }

         this.generateBox(â˜ƒ, â˜ƒ, 4, 2, 0, 8, 2, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 4, 12, 2, 8, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 0, 0, 8, 1, 3, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 0, 9, 8, 1, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 0, 4, 3, 1, 8, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 9, 0, 4, 12, 1, 8, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);

         for(int â˜ƒxx = 4; â˜ƒxx <= 8; ++â˜ƒxx) {
            for(int â˜ƒxxx = 0; â˜ƒxxx <= 2; ++â˜ƒxxx) {
               this.fillColumnDown(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), â˜ƒxx, -1, â˜ƒxxx, â˜ƒ);
               this.fillColumnDown(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), â˜ƒxx, -1, 12 - â˜ƒxxx, â˜ƒ);
            }
         }

         for(int â˜ƒxx = 0; â˜ƒxx <= 2; ++â˜ƒxx) {
            for(int â˜ƒxxx = 4; â˜ƒxxx <= 8; ++â˜ƒxxx) {
               this.fillColumnDown(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), â˜ƒxx, -1, â˜ƒxxx, â˜ƒ);
               this.fillColumnDown(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), 12 - â˜ƒxx, -1, â˜ƒxxx, â˜ƒ);
            }
         }

         this.generateBox(â˜ƒ, â˜ƒ, 5, 5, 5, 7, 5, 7, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 6, 1, 6, 6, 4, 6, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
         this.placeBlock(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), 6, 0, 6, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.LAVA.defaultBlockState(), 6, 5, 6, â˜ƒ);
         BlockPos â˜ƒxx = this.getWorldPos(6, 5, 6);
         if (â˜ƒ.isInside(â˜ƒxx)) {
            â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒxx, Fluids.LAVA, 0);
         }

         return true;
      }
   }

   public static class CastleSmallCorridorCrossingPiece extends NetherBridgePieces.NetherBridgePiece {
      private static final int WIDTH = 5;
      private static final int HEIGHT = 7;
      private static final int DEPTH = 5;

      public CastleSmallCorridorCrossingPiece(int var1, BoundingBox var2, Direction var3) {
         super(StructurePieceType.NETHER_FORTRESS_CASTLE_SMALL_CORRIDOR_CROSSING, â˜ƒ, â˜ƒ);
         this.setOrientation(â˜ƒ);
      }

      public CastleSmallCorridorCrossingPiece(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.NETHER_FORTRESS_CASTLE_SMALL_CORRIDOR_CROSSING, â˜ƒ);
      }

      @Override
      public void addChildren(StructurePiece var1, StructurePieceAccessor var2, Random var3) {
         this.generateChildForward((NetherBridgePieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 1, 0, true);
         this.generateChildLeft((NetherBridgePieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 0, 1, true);
         this.generateChildRight((NetherBridgePieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 0, 1, true);
      }

      public static NetherBridgePieces.CastleSmallCorridorCrossingPiece createPiece(
         StructurePieceAccessor var0, int var1, int var2, int var3, Direction var4, int var5
      ) {
         BoundingBox â˜ƒ = BoundingBox.orientBox(â˜ƒ, â˜ƒ, â˜ƒ, -1, 0, 0, 5, 7, 5, â˜ƒ);
         return isOkBox(â˜ƒ) && â˜ƒ.findCollisionPiece(â˜ƒ) == null ? new NetherBridgePieces.CastleSmallCorridorCrossingPiece(â˜ƒ, â˜ƒ, â˜ƒ) : null;
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         this.generateBox(â˜ƒ, â˜ƒ, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 0, 4, 5, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 0, 0, 5, 0, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 2, 0, 4, 5, 0, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 4, 0, 5, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 2, 4, 4, 5, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);

         for(int â˜ƒ = 0; â˜ƒ <= 4; ++â˜ƒ) {
            for(int â˜ƒx = 0; â˜ƒx <= 4; ++â˜ƒx) {
               this.fillColumnDown(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), â˜ƒ, -1, â˜ƒx, â˜ƒ);
            }
         }

         return true;
      }
   }

   public static class CastleSmallCorridorLeftTurnPiece extends NetherBridgePieces.NetherBridgePiece {
      private static final int WIDTH = 5;
      private static final int HEIGHT = 7;
      private static final int DEPTH = 5;
      private boolean isNeedingChest;

      public CastleSmallCorridorLeftTurnPiece(int var1, Random var2, BoundingBox var3, Direction var4) {
         super(StructurePieceType.NETHER_FORTRESS_CASTLE_SMALL_CORRIDOR_LEFT_TURN, â˜ƒ, â˜ƒ);
         this.setOrientation(â˜ƒ);
         this.isNeedingChest = â˜ƒ.nextInt(3) == 0;
      }

      public CastleSmallCorridorLeftTurnPiece(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.NETHER_FORTRESS_CASTLE_SMALL_CORRIDOR_LEFT_TURN, â˜ƒ);
         this.isNeedingChest = â˜ƒ.getBoolean("Chest");
      }

      @Override
      protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
         super.addAdditionalSaveData(â˜ƒ, â˜ƒ);
         â˜ƒ.putBoolean("Chest", this.isNeedingChest);
      }

      @Override
      public void addChildren(StructurePiece var1, StructurePieceAccessor var2, Random var3) {
         this.generateChildLeft((NetherBridgePieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 0, 1, true);
      }

      public static NetherBridgePieces.CastleSmallCorridorLeftTurnPiece createPiece(
         StructurePieceAccessor var0, Random var1, int var2, int var3, int var4, Direction var5, int var6
      ) {
         BoundingBox â˜ƒ = BoundingBox.orientBox(â˜ƒ, â˜ƒ, â˜ƒ, -1, 0, 0, 5, 7, 5, â˜ƒ);
         return isOkBox(â˜ƒ) && â˜ƒ.findCollisionPiece(â˜ƒ) == null ? new NetherBridgePieces.CastleSmallCorridorLeftTurnPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) : null;
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         this.generateBox(â˜ƒ, â˜ƒ, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 0, 4, 5, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
         BlockState â˜ƒ = Blocks.NETHER_BRICK_FENCE
            .defaultBlockState()
            .setValue(FenceBlock.WEST, Boolean.valueOf(true))
            .setValue(FenceBlock.EAST, Boolean.valueOf(true));
         BlockState â˜ƒx = Blocks.NETHER_BRICK_FENCE
            .defaultBlockState()
            .setValue(FenceBlock.NORTH, Boolean.valueOf(true))
            .setValue(FenceBlock.SOUTH, Boolean.valueOf(true));
         this.generateBox(â˜ƒ, â˜ƒ, 4, 2, 0, 4, 5, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 3, 1, 4, 4, 1, â˜ƒx, â˜ƒx, false);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 3, 3, 4, 4, 3, â˜ƒx, â˜ƒx, false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 0, 0, 5, 0, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 4, 3, 5, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 3, 4, 1, 4, 4, â˜ƒ, â˜ƒ, false);
         this.generateBox(â˜ƒ, â˜ƒ, 3, 3, 4, 3, 4, 4, â˜ƒ, â˜ƒ, false);
         if (this.isNeedingChest && â˜ƒ.isInside(this.getWorldPos(3, 2, 3))) {
            this.isNeedingChest = false;
            this.createChest(â˜ƒ, â˜ƒ, â˜ƒ, 3, 2, 3, BuiltInLootTables.NETHER_BRIDGE);
         }

         this.generateBox(â˜ƒ, â˜ƒ, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);

         for(int â˜ƒ = 0; â˜ƒ <= 4; ++â˜ƒ) {
            for(int â˜ƒx = 0; â˜ƒx <= 4; ++â˜ƒx) {
               this.fillColumnDown(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), â˜ƒ, -1, â˜ƒx, â˜ƒ);
            }
         }

         return true;
      }
   }

   public static class CastleSmallCorridorPiece extends NetherBridgePieces.NetherBridgePiece {
      private static final int WIDTH = 5;
      private static final int HEIGHT = 7;
      private static final int DEPTH = 5;

      public CastleSmallCorridorPiece(int var1, BoundingBox var2, Direction var3) {
         super(StructurePieceType.NETHER_FORTRESS_CASTLE_SMALL_CORRIDOR, â˜ƒ, â˜ƒ);
         this.setOrientation(â˜ƒ);
      }

      public CastleSmallCorridorPiece(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.NETHER_FORTRESS_CASTLE_SMALL_CORRIDOR, â˜ƒ);
      }

      @Override
      public void addChildren(StructurePiece var1, StructurePieceAccessor var2, Random var3) {
         this.generateChildForward((NetherBridgePieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 1, 0, true);
      }

      public static NetherBridgePieces.CastleSmallCorridorPiece createPiece(StructurePieceAccessor var0, int var1, int var2, int var3, Direction var4, int var5) {
         BoundingBox â˜ƒ = BoundingBox.orientBox(â˜ƒ, â˜ƒ, â˜ƒ, -1, 0, 0, 5, 7, 5, â˜ƒ);
         return isOkBox(â˜ƒ) && â˜ƒ.findCollisionPiece(â˜ƒ) == null ? new NetherBridgePieces.CastleSmallCorridorPiece(â˜ƒ, â˜ƒ, â˜ƒ) : null;
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         this.generateBox(â˜ƒ, â˜ƒ, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 0, 4, 5, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
         BlockState â˜ƒ = Blocks.NETHER_BRICK_FENCE
            .defaultBlockState()
            .setValue(FenceBlock.NORTH, Boolean.valueOf(true))
            .setValue(FenceBlock.SOUTH, Boolean.valueOf(true));
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 0, 0, 5, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 2, 0, 4, 5, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 3, 1, 0, 4, 1, â˜ƒ, â˜ƒ, false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 3, 3, 0, 4, 3, â˜ƒ, â˜ƒ, false);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 3, 1, 4, 4, 1, â˜ƒ, â˜ƒ, false);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 3, 3, 4, 4, 3, â˜ƒ, â˜ƒ, false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);

         for(int â˜ƒx = 0; â˜ƒx <= 4; ++â˜ƒx) {
            for(int â˜ƒxx = 0; â˜ƒxx <= 4; ++â˜ƒxx) {
               this.fillColumnDown(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), â˜ƒx, -1, â˜ƒxx, â˜ƒ);
            }
         }

         return true;
      }
   }

   public static class CastleSmallCorridorRightTurnPiece extends NetherBridgePieces.NetherBridgePiece {
      private static final int WIDTH = 5;
      private static final int HEIGHT = 7;
      private static final int DEPTH = 5;
      private boolean isNeedingChest;

      public CastleSmallCorridorRightTurnPiece(int var1, Random var2, BoundingBox var3, Direction var4) {
         super(StructurePieceType.NETHER_FORTRESS_CASTLE_SMALL_CORRIDOR_RIGHT_TURN, â˜ƒ, â˜ƒ);
         this.setOrientation(â˜ƒ);
         this.isNeedingChest = â˜ƒ.nextInt(3) == 0;
      }

      public CastleSmallCorridorRightTurnPiece(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.NETHER_FORTRESS_CASTLE_SMALL_CORRIDOR_RIGHT_TURN, â˜ƒ);
         this.isNeedingChest = â˜ƒ.getBoolean("Chest");
      }

      @Override
      protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
         super.addAdditionalSaveData(â˜ƒ, â˜ƒ);
         â˜ƒ.putBoolean("Chest", this.isNeedingChest);
      }

      @Override
      public void addChildren(StructurePiece var1, StructurePieceAccessor var2, Random var3) {
         this.generateChildRight((NetherBridgePieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 0, 1, true);
      }

      public static NetherBridgePieces.CastleSmallCorridorRightTurnPiece createPiece(
         StructurePieceAccessor var0, Random var1, int var2, int var3, int var4, Direction var5, int var6
      ) {
         BoundingBox â˜ƒ = BoundingBox.orientBox(â˜ƒ, â˜ƒ, â˜ƒ, -1, 0, 0, 5, 7, 5, â˜ƒ);
         return isOkBox(â˜ƒ) && â˜ƒ.findCollisionPiece(â˜ƒ) == null ? new NetherBridgePieces.CastleSmallCorridorRightTurnPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) : null;
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         this.generateBox(â˜ƒ, â˜ƒ, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 0, 4, 5, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
         BlockState â˜ƒ = Blocks.NETHER_BRICK_FENCE
            .defaultBlockState()
            .setValue(FenceBlock.WEST, Boolean.valueOf(true))
            .setValue(FenceBlock.EAST, Boolean.valueOf(true));
         BlockState â˜ƒx = Blocks.NETHER_BRICK_FENCE
            .defaultBlockState()
            .setValue(FenceBlock.NORTH, Boolean.valueOf(true))
            .setValue(FenceBlock.SOUTH, Boolean.valueOf(true));
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 0, 0, 5, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 3, 1, 0, 4, 1, â˜ƒx, â˜ƒx, false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 3, 3, 0, 4, 3, â˜ƒx, â˜ƒx, false);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 2, 0, 4, 5, 0, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 2, 4, 4, 5, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 3, 4, 1, 4, 4, â˜ƒ, â˜ƒ, false);
         this.generateBox(â˜ƒ, â˜ƒ, 3, 3, 4, 3, 4, 4, â˜ƒ, â˜ƒ, false);
         if (this.isNeedingChest && â˜ƒ.isInside(this.getWorldPos(1, 2, 3))) {
            this.isNeedingChest = false;
            this.createChest(â˜ƒ, â˜ƒ, â˜ƒ, 1, 2, 3, BuiltInLootTables.NETHER_BRIDGE);
         }

         this.generateBox(â˜ƒ, â˜ƒ, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);

         for(int â˜ƒ = 0; â˜ƒ <= 4; ++â˜ƒ) {
            for(int â˜ƒx = 0; â˜ƒx <= 4; ++â˜ƒx) {
               this.fillColumnDown(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), â˜ƒ, -1, â˜ƒx, â˜ƒ);
            }
         }

         return true;
      }
   }

   public static class CastleStalkRoom extends NetherBridgePieces.NetherBridgePiece {
      private static final int WIDTH = 13;
      private static final int HEIGHT = 14;
      private static final int DEPTH = 13;

      public CastleStalkRoom(int var1, BoundingBox var2, Direction var3) {
         super(StructurePieceType.NETHER_FORTRESS_CASTLE_STALK_ROOM, â˜ƒ, â˜ƒ);
         this.setOrientation(â˜ƒ);
      }

      public CastleStalkRoom(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.NETHER_FORTRESS_CASTLE_STALK_ROOM, â˜ƒ);
      }

      @Override
      public void addChildren(StructurePiece var1, StructurePieceAccessor var2, Random var3) {
         this.generateChildForward((NetherBridgePieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 5, 3, true);
         this.generateChildForward((NetherBridgePieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 5, 11, true);
      }

      public static NetherBridgePieces.CastleStalkRoom createPiece(StructurePieceAccessor var0, int var1, int var2, int var3, Direction var4, int var5) {
         BoundingBox â˜ƒ = BoundingBox.orientBox(â˜ƒ, â˜ƒ, â˜ƒ, -5, -3, 0, 13, 14, 13, â˜ƒ);
         return isOkBox(â˜ƒ) && â˜ƒ.findCollisionPiece(â˜ƒ) == null ? new NetherBridgePieces.CastleStalkRoom(â˜ƒ, â˜ƒ, â˜ƒ) : null;
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         this.generateBox(â˜ƒ, â˜ƒ, 0, 3, 0, 12, 4, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 5, 0, 12, 13, 12, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 5, 0, 1, 12, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 11, 5, 0, 12, 12, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 5, 11, 4, 12, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 8, 5, 11, 10, 12, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 9, 11, 7, 12, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 5, 0, 4, 12, 1, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 8, 5, 0, 10, 12, 1, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 9, 0, 7, 12, 1, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 11, 2, 10, 12, 10, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         BlockState â˜ƒ = Blocks.NETHER_BRICK_FENCE
            .defaultBlockState()
            .setValue(FenceBlock.WEST, Boolean.valueOf(true))
            .setValue(FenceBlock.EAST, Boolean.valueOf(true));
         BlockState â˜ƒx = Blocks.NETHER_BRICK_FENCE
            .defaultBlockState()
            .setValue(FenceBlock.NORTH, Boolean.valueOf(true))
            .setValue(FenceBlock.SOUTH, Boolean.valueOf(true));
         BlockState â˜ƒxx = â˜ƒx.setValue(FenceBlock.WEST, Boolean.valueOf(true));
         BlockState â˜ƒxxx = â˜ƒx.setValue(FenceBlock.EAST, Boolean.valueOf(true));

         for(int â˜ƒxxxx = 1; â˜ƒxxxx <= 11; â˜ƒxxxx += 2) {
            this.generateBox(â˜ƒ, â˜ƒ, â˜ƒxxxx, 10, 0, â˜ƒxxxx, 11, 0, â˜ƒ, â˜ƒ, false);
            this.generateBox(â˜ƒ, â˜ƒ, â˜ƒxxxx, 10, 12, â˜ƒxxxx, 11, 12, â˜ƒ, â˜ƒ, false);
            this.generateBox(â˜ƒ, â˜ƒ, 0, 10, â˜ƒxxxx, 0, 11, â˜ƒxxxx, â˜ƒx, â˜ƒx, false);
            this.generateBox(â˜ƒ, â˜ƒ, 12, 10, â˜ƒxxxx, 12, 11, â˜ƒxxxx, â˜ƒx, â˜ƒx, false);
            this.placeBlock(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), â˜ƒxxxx, 13, 0, â˜ƒ);
            this.placeBlock(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), â˜ƒxxxx, 13, 12, â˜ƒ);
            this.placeBlock(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), 0, 13, â˜ƒxxxx, â˜ƒ);
            this.placeBlock(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), 12, 13, â˜ƒxxxx, â˜ƒ);
            if (â˜ƒxxxx != 11) {
               this.placeBlock(â˜ƒ, â˜ƒ, â˜ƒxxxx + 1, 13, 0, â˜ƒ);
               this.placeBlock(â˜ƒ, â˜ƒ, â˜ƒxxxx + 1, 13, 12, â˜ƒ);
               this.placeBlock(â˜ƒ, â˜ƒx, 0, 13, â˜ƒxxxx + 1, â˜ƒ);
               this.placeBlock(â˜ƒ, â˜ƒx, 12, 13, â˜ƒxxxx + 1, â˜ƒ);
            }
         }

         this.placeBlock(
            â˜ƒ,
            Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.NORTH, Boolean.valueOf(true)).setValue(FenceBlock.EAST, Boolean.valueOf(true)),
            0,
            13,
            0,
            â˜ƒ
         );
         this.placeBlock(
            â˜ƒ,
            Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.SOUTH, Boolean.valueOf(true)).setValue(FenceBlock.EAST, Boolean.valueOf(true)),
            0,
            13,
            12,
            â˜ƒ
         );
         this.placeBlock(
            â˜ƒ,
            Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.SOUTH, Boolean.valueOf(true)).setValue(FenceBlock.WEST, Boolean.valueOf(true)),
            12,
            13,
            12,
            â˜ƒ
         );
         this.placeBlock(
            â˜ƒ,
            Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.NORTH, Boolean.valueOf(true)).setValue(FenceBlock.WEST, Boolean.valueOf(true)),
            12,
            13,
            0,
            â˜ƒ
         );

         for(int â˜ƒxxxx = 3; â˜ƒxxxx <= 9; â˜ƒxxxx += 2) {
            this.generateBox(â˜ƒ, â˜ƒ, 1, 7, â˜ƒxxxx, 1, 8, â˜ƒxxxx, â˜ƒxx, â˜ƒxx, false);
            this.generateBox(â˜ƒ, â˜ƒ, 11, 7, â˜ƒxxxx, 11, 8, â˜ƒxxxx, â˜ƒxxx, â˜ƒxxx, false);
         }

         BlockState â˜ƒxxxx = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);

         for(int â˜ƒxxxxx = 0; â˜ƒxxxxx <= 6; ++â˜ƒxxxxx) {
            int â˜ƒxxxxxx = â˜ƒxxxxx + 4;

            for(int â˜ƒxxxxxxx = 5; â˜ƒxxxxxxx <= 7; ++â˜ƒxxxxxxx) {
               this.placeBlock(â˜ƒ, â˜ƒxxxx, â˜ƒxxxxxxx, 5 + â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒ);
            }

            if (â˜ƒxxxxxx >= 5 && â˜ƒxxxxxx <= 8) {
               this.generateBox(
                  â˜ƒ,
                  â˜ƒ,
                  5,
                  5,
                  â˜ƒxxxxxx,
                  7,
                  â˜ƒxxxxx + 4,
                  â˜ƒxxxxxx,
                  Blocks.NETHER_BRICKS.defaultBlockState(),
                  Blocks.NETHER_BRICKS.defaultBlockState(),
                  false
               );
            } else if (â˜ƒxxxxxx >= 9 && â˜ƒxxxxxx <= 10) {
               this.generateBox(
                  â˜ƒ,
                  â˜ƒ,
                  5,
                  8,
                  â˜ƒxxxxxx,
                  7,
                  â˜ƒxxxxx + 4,
                  â˜ƒxxxxxx,
                  Blocks.NETHER_BRICKS.defaultBlockState(),
                  Blocks.NETHER_BRICKS.defaultBlockState(),
                  false
               );
            }

            if (â˜ƒxxxxx >= 1) {
               this.generateBox(
                  â˜ƒ, â˜ƒ, 5, 6 + â˜ƒxxxxx, â˜ƒxxxxxx, 7, 9 + â˜ƒxxxxx, â˜ƒxxxxxx, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false
               );
            }
         }

         for(int â˜ƒxxxxx = 5; â˜ƒxxxxx <= 7; ++â˜ƒxxxxx) {
            this.placeBlock(â˜ƒ, â˜ƒxxxx, â˜ƒxxxxx, 12, 11, â˜ƒ);
         }

         this.generateBox(â˜ƒ, â˜ƒ, 5, 6, 7, 5, 7, 7, â˜ƒxxx, â˜ƒxxx, false);
         this.generateBox(â˜ƒ, â˜ƒ, 7, 6, 7, 7, 7, 7, â˜ƒxx, â˜ƒxx, false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 13, 12, 7, 13, 12, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 5, 2, 3, 5, 3, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 5, 9, 3, 5, 10, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 5, 4, 2, 5, 8, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 9, 5, 2, 10, 5, 3, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 9, 5, 9, 10, 5, 10, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 10, 5, 4, 10, 5, 8, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         BlockState â˜ƒxxxxx = â˜ƒxxxx.setValue(StairBlock.FACING, Direction.EAST);
         BlockState â˜ƒxxxxxx = â˜ƒxxxx.setValue(StairBlock.FACING, Direction.WEST);
         this.placeBlock(â˜ƒ, â˜ƒxxxxxx, 4, 5, 2, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxxxxx, 4, 5, 3, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxxxxx, 4, 5, 9, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxxxxx, 4, 5, 10, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxxxx, 8, 5, 2, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxxxx, 8, 5, 3, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxxxx, 8, 5, 9, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxxxx, 8, 5, 10, â˜ƒ);
         this.generateBox(â˜ƒ, â˜ƒ, 3, 4, 4, 4, 4, 8, Blocks.SOUL_SAND.defaultBlockState(), Blocks.SOUL_SAND.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 8, 4, 4, 9, 4, 8, Blocks.SOUL_SAND.defaultBlockState(), Blocks.SOUL_SAND.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 3, 5, 4, 4, 5, 8, Blocks.NETHER_WART.defaultBlockState(), Blocks.NETHER_WART.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 8, 5, 4, 9, 5, 8, Blocks.NETHER_WART.defaultBlockState(), Blocks.NETHER_WART.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 2, 0, 8, 2, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 4, 12, 2, 8, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 0, 0, 8, 1, 3, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 0, 9, 8, 1, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 0, 4, 3, 1, 8, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 9, 0, 4, 12, 1, 8, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);

         for(int â˜ƒxxxxxxx = 4; â˜ƒxxxxxxx <= 8; ++â˜ƒxxxxxxx) {
            for(int â˜ƒxxxxxxxx = 0; â˜ƒxxxxxxxx <= 2; ++â˜ƒxxxxxxxx) {
               this.fillColumnDown(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), â˜ƒxxxxxxx, -1, â˜ƒxxxxxxxx, â˜ƒ);
               this.fillColumnDown(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), â˜ƒxxxxxxx, -1, 12 - â˜ƒxxxxxxxx, â˜ƒ);
            }
         }

         for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx <= 2; ++â˜ƒxxxxxxx) {
            for(int â˜ƒxxxxxxxx = 4; â˜ƒxxxxxxxx <= 8; ++â˜ƒxxxxxxxx) {
               this.fillColumnDown(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), â˜ƒxxxxxxx, -1, â˜ƒxxxxxxxx, â˜ƒ);
               this.fillColumnDown(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), 12 - â˜ƒxxxxxxx, -1, â˜ƒxxxxxxxx, â˜ƒ);
            }
         }

         return true;
      }
   }

   public static class MonsterThrone extends NetherBridgePieces.NetherBridgePiece {
      private static final int WIDTH = 7;
      private static final int HEIGHT = 8;
      private static final int DEPTH = 9;
      private boolean hasPlacedSpawner;

      public MonsterThrone(int var1, BoundingBox var2, Direction var3) {
         super(StructurePieceType.NETHER_FORTRESS_MONSTER_THRONE, â˜ƒ, â˜ƒ);
         this.setOrientation(â˜ƒ);
      }

      public MonsterThrone(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.NETHER_FORTRESS_MONSTER_THRONE, â˜ƒ);
         this.hasPlacedSpawner = â˜ƒ.getBoolean("Mob");
      }

      @Override
      protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
         super.addAdditionalSaveData(â˜ƒ, â˜ƒ);
         â˜ƒ.putBoolean("Mob", this.hasPlacedSpawner);
      }

      public static NetherBridgePieces.MonsterThrone createPiece(StructurePieceAccessor var0, int var1, int var2, int var3, int var4, Direction var5) {
         BoundingBox â˜ƒ = BoundingBox.orientBox(â˜ƒ, â˜ƒ, â˜ƒ, -2, 0, 0, 7, 8, 9, â˜ƒ);
         return isOkBox(â˜ƒ) && â˜ƒ.findCollisionPiece(â˜ƒ) == null ? new NetherBridgePieces.MonsterThrone(â˜ƒ, â˜ƒ, â˜ƒ) : null;
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 0, 6, 7, 7, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 0, 0, 5, 1, 7, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 2, 1, 5, 2, 7, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 3, 2, 5, 3, 7, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 4, 3, 5, 4, 7, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 2, 0, 1, 4, 2, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 2, 0, 5, 4, 2, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 5, 2, 1, 5, 3, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 5, 2, 5, 5, 3, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 5, 3, 0, 5, 8, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 6, 5, 3, 6, 5, 8, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 5, 8, 5, 5, 8, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         BlockState â˜ƒ = Blocks.NETHER_BRICK_FENCE
            .defaultBlockState()
            .setValue(FenceBlock.WEST, Boolean.valueOf(true))
            .setValue(FenceBlock.EAST, Boolean.valueOf(true));
         BlockState â˜ƒx = Blocks.NETHER_BRICK_FENCE
            .defaultBlockState()
            .setValue(FenceBlock.NORTH, Boolean.valueOf(true))
            .setValue(FenceBlock.SOUTH, Boolean.valueOf(true));
         this.placeBlock(â˜ƒ, Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.WEST, Boolean.valueOf(true)), 1, 6, 3, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.EAST, Boolean.valueOf(true)), 5, 6, 3, â˜ƒ);
         this.placeBlock(
            â˜ƒ,
            Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.EAST, Boolean.valueOf(true)).setValue(FenceBlock.NORTH, Boolean.valueOf(true)),
            0,
            6,
            3,
            â˜ƒ
         );
         this.placeBlock(
            â˜ƒ,
            Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.WEST, Boolean.valueOf(true)).setValue(FenceBlock.NORTH, Boolean.valueOf(true)),
            6,
            6,
            3,
            â˜ƒ
         );
         this.generateBox(â˜ƒ, â˜ƒ, 0, 6, 4, 0, 6, 7, â˜ƒx, â˜ƒx, false);
         this.generateBox(â˜ƒ, â˜ƒ, 6, 6, 4, 6, 6, 7, â˜ƒx, â˜ƒx, false);
         this.placeBlock(
            â˜ƒ,
            Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.EAST, Boolean.valueOf(true)).setValue(FenceBlock.SOUTH, Boolean.valueOf(true)),
            0,
            6,
            8,
            â˜ƒ
         );
         this.placeBlock(
            â˜ƒ,
            Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.WEST, Boolean.valueOf(true)).setValue(FenceBlock.SOUTH, Boolean.valueOf(true)),
            6,
            6,
            8,
            â˜ƒ
         );
         this.generateBox(â˜ƒ, â˜ƒ, 1, 6, 8, 5, 6, 8, â˜ƒ, â˜ƒ, false);
         this.placeBlock(â˜ƒ, Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.EAST, Boolean.valueOf(true)), 1, 7, 8, â˜ƒ);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 7, 8, 4, 7, 8, â˜ƒ, â˜ƒ, false);
         this.placeBlock(â˜ƒ, Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.WEST, Boolean.valueOf(true)), 5, 7, 8, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.EAST, Boolean.valueOf(true)), 2, 8, 8, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒ, 3, 8, 8, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.WEST, Boolean.valueOf(true)), 4, 8, 8, â˜ƒ);
         if (!this.hasPlacedSpawner) {
            BlockPos â˜ƒxx = this.getWorldPos(3, 5, 5);
            if (â˜ƒ.isInside(â˜ƒxx)) {
               this.hasPlacedSpawner = true;
               â˜ƒ.setBlock(â˜ƒxx, Blocks.SPAWNER.defaultBlockState(), 2);
               BlockEntity â˜ƒxxx = â˜ƒ.getBlockEntity(â˜ƒxx);
               if (â˜ƒxxx instanceof SpawnerBlockEntity) {
                  ((SpawnerBlockEntity)â˜ƒxxx).getSpawner().setEntityId(EntityType.BLAZE);
               }
            }
         }

         for(int â˜ƒ = 0; â˜ƒ <= 6; ++â˜ƒ) {
            for(int â˜ƒx = 0; â˜ƒx <= 6; ++â˜ƒx) {
               this.fillColumnDown(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), â˜ƒ, -1, â˜ƒx, â˜ƒ);
            }
         }

         return true;
      }
   }

   abstract static class NetherBridgePiece extends StructurePiece {
      protected NetherBridgePiece(StructurePieceType var1, int var2, BoundingBox var3) {
         super(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public NetherBridgePiece(StructurePieceType var1, CompoundTag var2) {
         super(â˜ƒ, â˜ƒ);
      }

      @Override
      protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
      }

      private int updatePieceWeight(List<NetherBridgePieces.PieceWeight> var1) {
         boolean â˜ƒ = false;
         int â˜ƒx = 0;

         for(NetherBridgePieces.PieceWeight â˜ƒxx : â˜ƒ) {
            if (â˜ƒxx.maxPlaceCount > 0 && â˜ƒxx.placeCount < â˜ƒxx.maxPlaceCount) {
               â˜ƒ = true;
            }

            â˜ƒx += â˜ƒxx.weight;
         }

         return â˜ƒ ? â˜ƒx : -1;
      }

      private NetherBridgePieces.NetherBridgePiece generatePiece(
         NetherBridgePieces.StartPiece var1,
         List<NetherBridgePieces.PieceWeight> var2,
         StructurePieceAccessor var3,
         Random var4,
         int var5,
         int var6,
         int var7,
         Direction var8,
         int var9
      ) {
         int â˜ƒ = this.updatePieceWeight(â˜ƒ);
         boolean â˜ƒx = â˜ƒ > 0 && â˜ƒ <= 30;
         int â˜ƒxx = 0;

         while(â˜ƒxx < 5 && â˜ƒx) {
            ++â˜ƒxx;
            int â˜ƒxxx = â˜ƒ.nextInt(â˜ƒ);

            for(NetherBridgePieces.PieceWeight â˜ƒxxxx : â˜ƒ) {
               â˜ƒxxx -= â˜ƒxxxx.weight;
               if (â˜ƒxxx < 0) {
                  if (!â˜ƒxxxx.doPlace(â˜ƒ) || â˜ƒxxxx == â˜ƒ.previousPiece && !â˜ƒxxxx.allowInRow) {
                     break;
                  }

                  NetherBridgePieces.NetherBridgePiece â˜ƒxxxxx = NetherBridgePieces.findAndCreateBridgePieceFactory(â˜ƒxxxx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
                  if (â˜ƒxxxxx != null) {
                     ++â˜ƒxxxx.placeCount;
                     â˜ƒ.previousPiece = â˜ƒxxxx;
                     if (!â˜ƒxxxx.isValid()) {
                        â˜ƒ.remove(â˜ƒxxxx);
                     }

                     return â˜ƒxxxxx;
                  }
               }
            }
         }

         return NetherBridgePieces.BridgeEndFiller.createPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      private StructurePiece generateAndAddPiece(
         NetherBridgePieces.StartPiece var1,
         StructurePieceAccessor var2,
         Random var3,
         int var4,
         int var5,
         int var6,
         @Nullable Direction var7,
         int var8,
         boolean var9
      ) {
         if (Math.abs(â˜ƒ - â˜ƒ.getBoundingBox().minX()) <= 112 && Math.abs(â˜ƒ - â˜ƒ.getBoundingBox().minZ()) <= 112) {
            List<NetherBridgePieces.PieceWeight> â˜ƒ = â˜ƒ.availableBridgePieces;
            if (â˜ƒ) {
               â˜ƒ = â˜ƒ.availableCastlePieces;
            }

            StructurePiece â˜ƒ = this.generatePiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + 1);
            if (â˜ƒ != null) {
               â˜ƒ.addPiece(â˜ƒ);
               â˜ƒ.pendingChildren.add(â˜ƒ);
            }

            return â˜ƒ;
         } else {
            return NetherBridgePieces.BridgeEndFiller.createPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }

      @Nullable
      protected StructurePiece generateChildForward(
         NetherBridgePieces.StartPiece var1, StructurePieceAccessor var2, Random var3, int var4, int var5, boolean var6
      ) {
         Direction â˜ƒ = this.getOrientation();
         if (â˜ƒ != null) {
            switch(â˜ƒ) {
               case NORTH:
                  return this.generateAndAddPiece(
                     â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX() + â˜ƒ, this.boundingBox.minY() + â˜ƒ, this.boundingBox.minZ() - 1, â˜ƒ, this.getGenDepth(), â˜ƒ
                  );
               case SOUTH:
                  return this.generateAndAddPiece(
                     â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX() + â˜ƒ, this.boundingBox.minY() + â˜ƒ, this.boundingBox.maxZ() + 1, â˜ƒ, this.getGenDepth(), â˜ƒ
                  );
               case WEST:
                  return this.generateAndAddPiece(
                     â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.minX() - 1, this.boundingBox.minY() + â˜ƒ, this.boundingBox.minZ() + â˜ƒ, â˜ƒ, this.getGenDepth(), â˜ƒ
                  );
               case EAST:
                  return this.generateAndAddPiece(
                     â˜ƒ, â˜ƒ, â˜ƒ, this.boundingBox.maxX() + 1, this.boundingBox.minY() + â˜ƒ, this.boundingBox.minZ() + â˜ƒ, â˜ƒ, this.getGenDepth(), â˜ƒ
                  );
            }
         }

         return null;
      }

      @Nullable
      protected StructurePiece generateChildLeft(NetherBridgePieces.StartPiece var1, StructurePieceAccessor var2, Random var3, int var4, int var5, boolean var6) {
         Direction â˜ƒ = this.getOrientation();
         if (â˜ƒ != null) {
            switch(â˜ƒ) {
               case NORTH:
                  return this.generateAndAddPiece(
                     â˜ƒ,
                     â˜ƒ,
                     â˜ƒ,
                     this.boundingBox.minX() - 1,
                     this.boundingBox.minY() + â˜ƒ,
                     this.boundingBox.minZ() + â˜ƒ,
                     Direction.WEST,
                     this.getGenDepth(),
                     â˜ƒ
                  );
               case SOUTH:
                  return this.generateAndAddPiece(
                     â˜ƒ,
                     â˜ƒ,
                     â˜ƒ,
                     this.boundingBox.minX() - 1,
                     this.boundingBox.minY() + â˜ƒ,
                     this.boundingBox.minZ() + â˜ƒ,
                     Direction.WEST,
                     this.getGenDepth(),
                     â˜ƒ
                  );
               case WEST:
                  return this.generateAndAddPiece(
                     â˜ƒ,
                     â˜ƒ,
                     â˜ƒ,
                     this.boundingBox.minX() + â˜ƒ,
                     this.boundingBox.minY() + â˜ƒ,
                     this.boundingBox.minZ() - 1,
                     Direction.NORTH,
                     this.getGenDepth(),
                     â˜ƒ
                  );
               case EAST:
                  return this.generateAndAddPiece(
                     â˜ƒ,
                     â˜ƒ,
                     â˜ƒ,
                     this.boundingBox.minX() + â˜ƒ,
                     this.boundingBox.minY() + â˜ƒ,
                     this.boundingBox.minZ() - 1,
                     Direction.NORTH,
                     this.getGenDepth(),
                     â˜ƒ
                  );
            }
         }

         return null;
      }

      @Nullable
      protected StructurePiece generateChildRight(
         NetherBridgePieces.StartPiece var1, StructurePieceAccessor var2, Random var3, int var4, int var5, boolean var6
      ) {
         Direction â˜ƒ = this.getOrientation();
         if (â˜ƒ != null) {
            switch(â˜ƒ) {
               case NORTH:
                  return this.generateAndAddPiece(
                     â˜ƒ,
                     â˜ƒ,
                     â˜ƒ,
                     this.boundingBox.maxX() + 1,
                     this.boundingBox.minY() + â˜ƒ,
                     this.boundingBox.minZ() + â˜ƒ,
                     Direction.EAST,
                     this.getGenDepth(),
                     â˜ƒ
                  );
               case SOUTH:
                  return this.generateAndAddPiece(
                     â˜ƒ,
                     â˜ƒ,
                     â˜ƒ,
                     this.boundingBox.maxX() + 1,
                     this.boundingBox.minY() + â˜ƒ,
                     this.boundingBox.minZ() + â˜ƒ,
                     Direction.EAST,
                     this.getGenDepth(),
                     â˜ƒ
                  );
               case WEST:
                  return this.generateAndAddPiece(
                     â˜ƒ,
                     â˜ƒ,
                     â˜ƒ,
                     this.boundingBox.minX() + â˜ƒ,
                     this.boundingBox.minY() + â˜ƒ,
                     this.boundingBox.maxZ() + 1,
                     Direction.SOUTH,
                     this.getGenDepth(),
                     â˜ƒ
                  );
               case EAST:
                  return this.generateAndAddPiece(
                     â˜ƒ,
                     â˜ƒ,
                     â˜ƒ,
                     this.boundingBox.minX() + â˜ƒ,
                     this.boundingBox.minY() + â˜ƒ,
                     this.boundingBox.maxZ() + 1,
                     Direction.SOUTH,
                     this.getGenDepth(),
                     â˜ƒ
                  );
            }
         }

         return null;
      }

      protected static boolean isOkBox(BoundingBox var0) {
         return â˜ƒ != null && â˜ƒ.minY() > 10;
      }
   }

   static class PieceWeight {
      public final Class<? extends NetherBridgePieces.NetherBridgePiece> pieceClass;
      public final int weight;
      public int placeCount;
      public final int maxPlaceCount;
      public final boolean allowInRow;

      public PieceWeight(Class<? extends NetherBridgePieces.NetherBridgePiece> var1, int var2, int var3, boolean var4) {
         this.pieceClass = â˜ƒ;
         this.weight = â˜ƒ;
         this.maxPlaceCount = â˜ƒ;
         this.allowInRow = â˜ƒ;
      }

      public PieceWeight(Class<? extends NetherBridgePieces.NetherBridgePiece> var1, int var2, int var3) {
         this(â˜ƒ, â˜ƒ, â˜ƒ, false);
      }

      public boolean doPlace(int var1) {
         return this.maxPlaceCount == 0 || this.placeCount < this.maxPlaceCount;
      }

      public boolean isValid() {
         return this.maxPlaceCount == 0 || this.placeCount < this.maxPlaceCount;
      }
   }

   public static class RoomCrossing extends NetherBridgePieces.NetherBridgePiece {
      private static final int WIDTH = 7;
      private static final int HEIGHT = 9;
      private static final int DEPTH = 7;

      public RoomCrossing(int var1, BoundingBox var2, Direction var3) {
         super(StructurePieceType.NETHER_FORTRESS_ROOM_CROSSING, â˜ƒ, â˜ƒ);
         this.setOrientation(â˜ƒ);
      }

      public RoomCrossing(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.NETHER_FORTRESS_ROOM_CROSSING, â˜ƒ);
      }

      @Override
      public void addChildren(StructurePiece var1, StructurePieceAccessor var2, Random var3) {
         this.generateChildForward((NetherBridgePieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 2, 0, false);
         this.generateChildLeft((NetherBridgePieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 0, 2, false);
         this.generateChildRight((NetherBridgePieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 0, 2, false);
      }

      public static NetherBridgePieces.RoomCrossing createPiece(StructurePieceAccessor var0, int var1, int var2, int var3, Direction var4, int var5) {
         BoundingBox â˜ƒ = BoundingBox.orientBox(â˜ƒ, â˜ƒ, â˜ƒ, -2, 0, 0, 7, 9, 7, â˜ƒ);
         return isOkBox(â˜ƒ) && â˜ƒ.findCollisionPiece(â˜ƒ) == null ? new NetherBridgePieces.RoomCrossing(â˜ƒ, â˜ƒ, â˜ƒ) : null;
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         this.generateBox(â˜ƒ, â˜ƒ, 0, 0, 0, 6, 1, 6, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 0, 6, 7, 6, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 0, 1, 6, 0, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 6, 1, 6, 6, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 2, 0, 6, 6, 0, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 2, 6, 6, 6, 6, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 0, 0, 6, 1, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 5, 0, 6, 6, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 6, 2, 0, 6, 6, 1, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 6, 2, 5, 6, 6, 6, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         BlockState â˜ƒ = Blocks.NETHER_BRICK_FENCE
            .defaultBlockState()
            .setValue(FenceBlock.WEST, Boolean.valueOf(true))
            .setValue(FenceBlock.EAST, Boolean.valueOf(true));
         BlockState â˜ƒx = Blocks.NETHER_BRICK_FENCE
            .defaultBlockState()
            .setValue(FenceBlock.NORTH, Boolean.valueOf(true))
            .setValue(FenceBlock.SOUTH, Boolean.valueOf(true));
         this.generateBox(â˜ƒ, â˜ƒ, 2, 6, 0, 4, 6, 0, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 5, 0, 4, 5, 0, â˜ƒ, â˜ƒ, false);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 6, 6, 4, 6, 6, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 5, 6, 4, 5, 6, â˜ƒ, â˜ƒ, false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 6, 2, 0, 6, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 5, 2, 0, 5, 4, â˜ƒx, â˜ƒx, false);
         this.generateBox(â˜ƒ, â˜ƒ, 6, 6, 2, 6, 6, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 6, 5, 2, 6, 5, 4, â˜ƒx, â˜ƒx, false);

         for(int â˜ƒxx = 0; â˜ƒxx <= 6; ++â˜ƒxx) {
            for(int â˜ƒxxx = 0; â˜ƒxxx <= 6; ++â˜ƒxxx) {
               this.fillColumnDown(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), â˜ƒxx, -1, â˜ƒxxx, â˜ƒ);
            }
         }

         return true;
      }
   }

   public static class StairsRoom extends NetherBridgePieces.NetherBridgePiece {
      private static final int WIDTH = 7;
      private static final int HEIGHT = 11;
      private static final int DEPTH = 7;

      public StairsRoom(int var1, BoundingBox var2, Direction var3) {
         super(StructurePieceType.NETHER_FORTRESS_STAIRS_ROOM, â˜ƒ, â˜ƒ);
         this.setOrientation(â˜ƒ);
      }

      public StairsRoom(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.NETHER_FORTRESS_STAIRS_ROOM, â˜ƒ);
      }

      @Override
      public void addChildren(StructurePiece var1, StructurePieceAccessor var2, Random var3) {
         this.generateChildRight((NetherBridgePieces.StartPiece)â˜ƒ, â˜ƒ, â˜ƒ, 6, 2, false);
      }

      public static NetherBridgePieces.StairsRoom createPiece(StructurePieceAccessor var0, int var1, int var2, int var3, int var4, Direction var5) {
         BoundingBox â˜ƒ = BoundingBox.orientBox(â˜ƒ, â˜ƒ, â˜ƒ, -2, 0, 0, 7, 11, 7, â˜ƒ);
         return isOkBox(â˜ƒ) && â˜ƒ.findCollisionPiece(â˜ƒ) == null ? new NetherBridgePieces.StairsRoom(â˜ƒ, â˜ƒ, â˜ƒ) : null;
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         this.generateBox(â˜ƒ, â˜ƒ, 0, 0, 0, 6, 1, 6, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 0, 6, 10, 6, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 0, 1, 8, 0, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 2, 0, 6, 8, 0, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 1, 0, 8, 6, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 6, 2, 1, 6, 8, 6, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 2, 6, 5, 8, 6, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         BlockState â˜ƒ = Blocks.NETHER_BRICK_FENCE
            .defaultBlockState()
            .setValue(FenceBlock.WEST, Boolean.valueOf(true))
            .setValue(FenceBlock.EAST, Boolean.valueOf(true));
         BlockState â˜ƒx = Blocks.NETHER_BRICK_FENCE
            .defaultBlockState()
            .setValue(FenceBlock.NORTH, Boolean.valueOf(true))
            .setValue(FenceBlock.SOUTH, Boolean.valueOf(true));
         this.generateBox(â˜ƒ, â˜ƒ, 0, 3, 2, 0, 5, 4, â˜ƒx, â˜ƒx, false);
         this.generateBox(â˜ƒ, â˜ƒ, 6, 3, 2, 6, 5, 2, â˜ƒx, â˜ƒx, false);
         this.generateBox(â˜ƒ, â˜ƒ, 6, 3, 4, 6, 5, 4, â˜ƒx, â˜ƒx, false);
         this.placeBlock(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), 5, 2, 5, â˜ƒ);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 2, 5, 4, 3, 5, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 3, 2, 5, 3, 4, 5, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 2, 5, 2, 5, 5, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 2, 5, 1, 6, 5, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 7, 1, 5, 7, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 6, 8, 2, 6, 8, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 6, 0, 4, 8, 0, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 5, 0, 4, 5, 0, â˜ƒ, â˜ƒ, false);

         for(int â˜ƒxx = 0; â˜ƒxx <= 6; ++â˜ƒxx) {
            for(int â˜ƒxxx = 0; â˜ƒxxx <= 6; ++â˜ƒxxx) {
               this.fillColumnDown(â˜ƒ, Blocks.NETHER_BRICKS.defaultBlockState(), â˜ƒxx, -1, â˜ƒxxx, â˜ƒ);
            }
         }

         return true;
      }
   }

   public static class StartPiece extends NetherBridgePieces.BridgeCrossing {
      public NetherBridgePieces.PieceWeight previousPiece;
      public List<NetherBridgePieces.PieceWeight> availableBridgePieces;
      public List<NetherBridgePieces.PieceWeight> availableCastlePieces;
      public final List<StructurePiece> pendingChildren = Lists.<StructurePiece>newArrayList();

      public StartPiece(Random var1, int var2, int var3) {
         super(â˜ƒ, â˜ƒ, getRandomHorizontalDirection(â˜ƒ));
         this.availableBridgePieces = Lists.<NetherBridgePieces.PieceWeight>newArrayList();

         for(NetherBridgePieces.PieceWeight â˜ƒ : NetherBridgePieces.BRIDGE_PIECE_WEIGHTS) {
            â˜ƒ.placeCount = 0;
            this.availableBridgePieces.add(â˜ƒ);
         }

         this.availableCastlePieces = Lists.<NetherBridgePieces.PieceWeight>newArrayList();

         for(NetherBridgePieces.PieceWeight â˜ƒ : NetherBridgePieces.CASTLE_PIECE_WEIGHTS) {
            â˜ƒ.placeCount = 0;
            this.availableCastlePieces.add(â˜ƒ);
         }
      }

      public StartPiece(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.NETHER_FORTRESS_START, â˜ƒ);
      }
   }
}
