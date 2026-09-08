package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;

public class OceanMonumentPieces {
   private OceanMonumentPieces() {
   }

   static class FitDoubleXRoom implements OceanMonumentPieces.MonumentRoomFitter {
      @Override
      public boolean fits(OceanMonumentPieces.RoomDefinition var1) {
         return â˜ƒ.hasOpening[Direction.EAST.get3DDataValue()] && !â˜ƒ.connections[Direction.EAST.get3DDataValue()].claimed;
      }

      @Override
      public OceanMonumentPieces.OceanMonumentPiece create(Direction var1, OceanMonumentPieces.RoomDefinition var2, Random var3) {
         â˜ƒ.claimed = true;
         â˜ƒ.connections[Direction.EAST.get3DDataValue()].claimed = true;
         return new OceanMonumentPieces.OceanMonumentDoubleXRoom(â˜ƒ, â˜ƒ);
      }
   }

   static class FitDoubleXYRoom implements OceanMonumentPieces.MonumentRoomFitter {
      @Override
      public boolean fits(OceanMonumentPieces.RoomDefinition var1) {
         if (â˜ƒ.hasOpening[Direction.EAST.get3DDataValue()]
            && !â˜ƒ.connections[Direction.EAST.get3DDataValue()].claimed
            && â˜ƒ.hasOpening[Direction.UP.get3DDataValue()]
            && !â˜ƒ.connections[Direction.UP.get3DDataValue()].claimed) {
            OceanMonumentPieces.RoomDefinition â˜ƒ = â˜ƒ.connections[Direction.EAST.get3DDataValue()];
            return â˜ƒ.hasOpening[Direction.UP.get3DDataValue()] && !â˜ƒ.connections[Direction.UP.get3DDataValue()].claimed;
         } else {
            return false;
         }
      }

      @Override
      public OceanMonumentPieces.OceanMonumentPiece create(Direction var1, OceanMonumentPieces.RoomDefinition var2, Random var3) {
         â˜ƒ.claimed = true;
         â˜ƒ.connections[Direction.EAST.get3DDataValue()].claimed = true;
         â˜ƒ.connections[Direction.UP.get3DDataValue()].claimed = true;
         â˜ƒ.connections[Direction.EAST.get3DDataValue()].connections[Direction.UP.get3DDataValue()].claimed = true;
         return new OceanMonumentPieces.OceanMonumentDoubleXYRoom(â˜ƒ, â˜ƒ);
      }
   }

   static class FitDoubleYRoom implements OceanMonumentPieces.MonumentRoomFitter {
      @Override
      public boolean fits(OceanMonumentPieces.RoomDefinition var1) {
         return â˜ƒ.hasOpening[Direction.UP.get3DDataValue()] && !â˜ƒ.connections[Direction.UP.get3DDataValue()].claimed;
      }

      @Override
      public OceanMonumentPieces.OceanMonumentPiece create(Direction var1, OceanMonumentPieces.RoomDefinition var2, Random var3) {
         â˜ƒ.claimed = true;
         â˜ƒ.connections[Direction.UP.get3DDataValue()].claimed = true;
         return new OceanMonumentPieces.OceanMonumentDoubleYRoom(â˜ƒ, â˜ƒ);
      }
   }

   static class FitDoubleYZRoom implements OceanMonumentPieces.MonumentRoomFitter {
      @Override
      public boolean fits(OceanMonumentPieces.RoomDefinition var1) {
         if (â˜ƒ.hasOpening[Direction.NORTH.get3DDataValue()]
            && !â˜ƒ.connections[Direction.NORTH.get3DDataValue()].claimed
            && â˜ƒ.hasOpening[Direction.UP.get3DDataValue()]
            && !â˜ƒ.connections[Direction.UP.get3DDataValue()].claimed) {
            OceanMonumentPieces.RoomDefinition â˜ƒ = â˜ƒ.connections[Direction.NORTH.get3DDataValue()];
            return â˜ƒ.hasOpening[Direction.UP.get3DDataValue()] && !â˜ƒ.connections[Direction.UP.get3DDataValue()].claimed;
         } else {
            return false;
         }
      }

      @Override
      public OceanMonumentPieces.OceanMonumentPiece create(Direction var1, OceanMonumentPieces.RoomDefinition var2, Random var3) {
         â˜ƒ.claimed = true;
         â˜ƒ.connections[Direction.NORTH.get3DDataValue()].claimed = true;
         â˜ƒ.connections[Direction.UP.get3DDataValue()].claimed = true;
         â˜ƒ.connections[Direction.NORTH.get3DDataValue()].connections[Direction.UP.get3DDataValue()].claimed = true;
         return new OceanMonumentPieces.OceanMonumentDoubleYZRoom(â˜ƒ, â˜ƒ);
      }
   }

   static class FitDoubleZRoom implements OceanMonumentPieces.MonumentRoomFitter {
      @Override
      public boolean fits(OceanMonumentPieces.RoomDefinition var1) {
         return â˜ƒ.hasOpening[Direction.NORTH.get3DDataValue()] && !â˜ƒ.connections[Direction.NORTH.get3DDataValue()].claimed;
      }

      @Override
      public OceanMonumentPieces.OceanMonumentPiece create(Direction var1, OceanMonumentPieces.RoomDefinition var2, Random var3) {
         OceanMonumentPieces.RoomDefinition â˜ƒ = â˜ƒ;
         if (!â˜ƒ.hasOpening[Direction.NORTH.get3DDataValue()] || â˜ƒ.connections[Direction.NORTH.get3DDataValue()].claimed) {
            â˜ƒ = â˜ƒ.connections[Direction.SOUTH.get3DDataValue()];
         }

         â˜ƒ.claimed = true;
         â˜ƒ.connections[Direction.NORTH.get3DDataValue()].claimed = true;
         return new OceanMonumentPieces.OceanMonumentDoubleZRoom(â˜ƒ, â˜ƒ);
      }
   }

   static class FitSimpleRoom implements OceanMonumentPieces.MonumentRoomFitter {
      @Override
      public boolean fits(OceanMonumentPieces.RoomDefinition var1) {
         return true;
      }

      @Override
      public OceanMonumentPieces.OceanMonumentPiece create(Direction var1, OceanMonumentPieces.RoomDefinition var2, Random var3) {
         â˜ƒ.claimed = true;
         return new OceanMonumentPieces.OceanMonumentSimpleRoom(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   static class FitSimpleTopRoom implements OceanMonumentPieces.MonumentRoomFitter {
      @Override
      public boolean fits(OceanMonumentPieces.RoomDefinition var1) {
         return !â˜ƒ.hasOpening[Direction.WEST.get3DDataValue()]
            && !â˜ƒ.hasOpening[Direction.EAST.get3DDataValue()]
            && !â˜ƒ.hasOpening[Direction.NORTH.get3DDataValue()]
            && !â˜ƒ.hasOpening[Direction.SOUTH.get3DDataValue()]
            && !â˜ƒ.hasOpening[Direction.UP.get3DDataValue()];
      }

      @Override
      public OceanMonumentPieces.OceanMonumentPiece create(Direction var1, OceanMonumentPieces.RoomDefinition var2, Random var3) {
         â˜ƒ.claimed = true;
         return new OceanMonumentPieces.OceanMonumentSimpleTopRoom(â˜ƒ, â˜ƒ);
      }
   }

   public static class MonumentBuilding extends OceanMonumentPieces.OceanMonumentPiece {
      private static final int WIDTH = 58;
      private static final int HEIGHT = 22;
      private static final int DEPTH = 58;
      public static final int BIOME_RANGE_CHECK = 29;
      private static final int TOP_POSITION = 61;
      private OceanMonumentPieces.RoomDefinition sourceRoom;
      private OceanMonumentPieces.RoomDefinition coreRoom;
      private final List<OceanMonumentPieces.OceanMonumentPiece> childPieces = Lists.<OceanMonumentPieces.OceanMonumentPiece>newArrayList();

      public MonumentBuilding(Random var1, int var2, int var3, Direction var4) {
         super(StructurePieceType.OCEAN_MONUMENT_BUILDING, â˜ƒ, 0, makeBoundingBox(â˜ƒ, 39, â˜ƒ, â˜ƒ, 58, 23, 58));
         this.setOrientation(â˜ƒ);
         List<OceanMonumentPieces.RoomDefinition> â˜ƒ = this.generateRoomGraph(â˜ƒ);
         this.sourceRoom.claimed = true;
         this.childPieces.add(new OceanMonumentPieces.OceanMonumentEntryRoom(â˜ƒ, this.sourceRoom));
         this.childPieces.add(new OceanMonumentPieces.OceanMonumentCoreRoom(â˜ƒ, this.coreRoom));
         List<OceanMonumentPieces.MonumentRoomFitter> â˜ƒx = Lists.<OceanMonumentPieces.MonumentRoomFitter>newArrayList();
         â˜ƒx.add(new OceanMonumentPieces.FitDoubleXYRoom());
         â˜ƒx.add(new OceanMonumentPieces.FitDoubleYZRoom());
         â˜ƒx.add(new OceanMonumentPieces.FitDoubleZRoom());
         â˜ƒx.add(new OceanMonumentPieces.FitDoubleXRoom());
         â˜ƒx.add(new OceanMonumentPieces.FitDoubleYRoom());
         â˜ƒx.add(new OceanMonumentPieces.FitSimpleTopRoom());
         â˜ƒx.add(new OceanMonumentPieces.FitSimpleRoom());

         for(OceanMonumentPieces.RoomDefinition â˜ƒxx : â˜ƒ) {
            if (!â˜ƒxx.claimed && !â˜ƒxx.isSpecial()) {
               for(OceanMonumentPieces.MonumentRoomFitter â˜ƒxxx : â˜ƒx) {
                  if (â˜ƒxxx.fits(â˜ƒxx)) {
                     this.childPieces.add(â˜ƒxxx.create(â˜ƒ, â˜ƒxx, â˜ƒ));
                     break;
                  }
               }
            }
         }

         BlockPos â˜ƒxx = this.getWorldPos(9, 0, 22);

         for(OceanMonumentPieces.OceanMonumentPiece â˜ƒxxx : this.childPieces) {
            â˜ƒxxx.getBoundingBox().move(â˜ƒxx);
         }

         BoundingBox â˜ƒxxx = BoundingBox.fromCorners(this.getWorldPos(1, 1, 1), this.getWorldPos(23, 8, 21));
         BoundingBox â˜ƒxxxx = BoundingBox.fromCorners(this.getWorldPos(34, 1, 1), this.getWorldPos(56, 8, 21));
         BoundingBox â˜ƒxxxxx = BoundingBox.fromCorners(this.getWorldPos(22, 13, 22), this.getWorldPos(35, 17, 35));
         int â˜ƒxxxxxx = â˜ƒ.nextInt();
         this.childPieces.add(new OceanMonumentPieces.OceanMonumentWingRoom(â˜ƒ, â˜ƒxxx, â˜ƒxxxxxx++));
         this.childPieces.add(new OceanMonumentPieces.OceanMonumentWingRoom(â˜ƒ, â˜ƒxxxx, â˜ƒxxxxxx++));
         this.childPieces.add(new OceanMonumentPieces.OceanMonumentPenthouse(â˜ƒ, â˜ƒxxxxx));
      }

      public MonumentBuilding(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.OCEAN_MONUMENT_BUILDING, â˜ƒ);
      }

      private List<OceanMonumentPieces.RoomDefinition> generateRoomGraph(Random var1) {
         OceanMonumentPieces.RoomDefinition[] â˜ƒ = new OceanMonumentPieces.RoomDefinition[75];

         for(int â˜ƒx = 0; â˜ƒx < 5; ++â˜ƒx) {
            for(int â˜ƒxx = 0; â˜ƒxx < 4; ++â˜ƒxx) {
               int â˜ƒxxx = 0;
               int â˜ƒxxxx = getRoomIndex(â˜ƒx, 0, â˜ƒxx);
               â˜ƒ[â˜ƒxxxx] = new OceanMonumentPieces.RoomDefinition(â˜ƒxxxx);
            }
         }

         for(int â˜ƒx = 0; â˜ƒx < 5; ++â˜ƒx) {
            for(int â˜ƒxx = 0; â˜ƒxx < 4; ++â˜ƒxx) {
               int â˜ƒxxx = 1;
               int â˜ƒxxxx = getRoomIndex(â˜ƒx, 1, â˜ƒxx);
               â˜ƒ[â˜ƒxxxx] = new OceanMonumentPieces.RoomDefinition(â˜ƒxxxx);
            }
         }

         for(int â˜ƒx = 1; â˜ƒx < 4; ++â˜ƒx) {
            for(int â˜ƒxx = 0; â˜ƒxx < 2; ++â˜ƒxx) {
               int â˜ƒxxx = 2;
               int â˜ƒxxxx = getRoomIndex(â˜ƒx, 2, â˜ƒxx);
               â˜ƒ[â˜ƒxxxx] = new OceanMonumentPieces.RoomDefinition(â˜ƒxxxx);
            }
         }

         this.sourceRoom = â˜ƒ[GRIDROOM_SOURCE_INDEX];

         for(int â˜ƒx = 0; â˜ƒx < 5; ++â˜ƒx) {
            for(int â˜ƒxx = 0; â˜ƒxx < 5; ++â˜ƒxx) {
               for(int â˜ƒxxx = 0; â˜ƒxxx < 3; ++â˜ƒxxx) {
                  int â˜ƒxxxx = getRoomIndex(â˜ƒx, â˜ƒxxx, â˜ƒxx);
                  if (â˜ƒ[â˜ƒxxxx] != null) {
                     for(Direction â˜ƒxxxxx : Direction.values()) {
                        int â˜ƒxxxxxx = â˜ƒx + â˜ƒxxxxx.getStepX();
                        int â˜ƒxxxxxxx = â˜ƒxxx + â˜ƒxxxxx.getStepY();
                        int â˜ƒxxxxxxxx = â˜ƒxx + â˜ƒxxxxx.getStepZ();
                        if (â˜ƒxxxxxx >= 0 && â˜ƒxxxxxx < 5 && â˜ƒxxxxxxxx >= 0 && â˜ƒxxxxxxxx < 5 && â˜ƒxxxxxxx >= 0 && â˜ƒxxxxxxx < 3) {
                           int â˜ƒxxxxxxxxx = getRoomIndex(â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx);
                           if (â˜ƒ[â˜ƒxxxxxxxxx] != null) {
                              if (â˜ƒxxxxxxxx == â˜ƒxx) {
                                 â˜ƒ[â˜ƒxxxx].setConnection(â˜ƒxxxxx, â˜ƒ[â˜ƒxxxxxxxxx]);
                              } else {
                                 â˜ƒ[â˜ƒxxxx].setConnection(â˜ƒxxxxx.getOpposite(), â˜ƒ[â˜ƒxxxxxxxxx]);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         OceanMonumentPieces.RoomDefinition â˜ƒx = new OceanMonumentPieces.RoomDefinition(1003);
         OceanMonumentPieces.RoomDefinition â˜ƒxx = new OceanMonumentPieces.RoomDefinition(1001);
         OceanMonumentPieces.RoomDefinition â˜ƒxxx = new OceanMonumentPieces.RoomDefinition(1002);
         â˜ƒ[GRIDROOM_TOP_CONNECT_INDEX].setConnection(Direction.UP, â˜ƒx);
         â˜ƒ[GRIDROOM_LEFTWING_CONNECT_INDEX].setConnection(Direction.SOUTH, â˜ƒxx);
         â˜ƒ[GRIDROOM_RIGHTWING_CONNECT_INDEX].setConnection(Direction.SOUTH, â˜ƒxxx);
         â˜ƒx.claimed = true;
         â˜ƒxx.claimed = true;
         â˜ƒxxx.claimed = true;
         this.sourceRoom.isSource = true;
         this.coreRoom = â˜ƒ[getRoomIndex(â˜ƒ.nextInt(4), 0, 2)];
         this.coreRoom.claimed = true;
         this.coreRoom.connections[Direction.EAST.get3DDataValue()].claimed = true;
         this.coreRoom.connections[Direction.NORTH.get3DDataValue()].claimed = true;
         this.coreRoom.connections[Direction.EAST.get3DDataValue()].connections[Direction.NORTH.get3DDataValue()].claimed = true;
         this.coreRoom.connections[Direction.UP.get3DDataValue()].claimed = true;
         this.coreRoom.connections[Direction.EAST.get3DDataValue()].connections[Direction.UP.get3DDataValue()].claimed = true;
         this.coreRoom.connections[Direction.NORTH.get3DDataValue()].connections[Direction.UP.get3DDataValue()].claimed = true;
         this.coreRoom.connections[Direction.EAST.get3DDataValue()].connections[Direction.NORTH.get3DDataValue()].connections[Direction.UP.get3DDataValue()].claimed = true;
         List<OceanMonumentPieces.RoomDefinition> â˜ƒxxxx = Lists.<OceanMonumentPieces.RoomDefinition>newArrayList();

         for(OceanMonumentPieces.RoomDefinition â˜ƒxxxxx : â˜ƒ) {
            if (â˜ƒxxxxx != null) {
               â˜ƒxxxxx.updateOpenings();
               â˜ƒxxxx.add(â˜ƒxxxxx);
            }
         }

         â˜ƒx.updateOpenings();
         Collections.shuffle(â˜ƒxxxx, â˜ƒ);
         int â˜ƒxxxxx = 1;

         for(OceanMonumentPieces.RoomDefinition â˜ƒxxxxxx : â˜ƒxxxx) {
            int â˜ƒxxxxxxx = 0;
            int â˜ƒxxxxxxxx = 0;

            while(â˜ƒxxxxxxx < 2 && â˜ƒxxxxxxxx < 5) {
               ++â˜ƒxxxxxxxx;
               int â˜ƒxxxxxxxxx = â˜ƒ.nextInt(6);
               if (â˜ƒxxxxxx.hasOpening[â˜ƒxxxxxxxxx]) {
                  int â˜ƒxxxxxxxxxx = Direction.from3DDataValue(â˜ƒxxxxxxxxx).getOpposite().get3DDataValue();
                  â˜ƒxxxxxx.hasOpening[â˜ƒxxxxxxxxx] = false;
                  â˜ƒxxxxxx.connections[â˜ƒxxxxxxxxx].hasOpening[â˜ƒxxxxxxxxxx] = false;
                  if (â˜ƒxxxxxx.findSource(â˜ƒxxxxx++) && â˜ƒxxxxxx.connections[â˜ƒxxxxxxxxx].findSource(â˜ƒxxxxx++)) {
                     ++â˜ƒxxxxxxx;
                  } else {
                     â˜ƒxxxxxx.hasOpening[â˜ƒxxxxxxxxx] = true;
                     â˜ƒxxxxxx.connections[â˜ƒxxxxxxxxx].hasOpening[â˜ƒxxxxxxxxxx] = true;
                  }
               }
            }
         }

         â˜ƒxxxx.add(â˜ƒx);
         â˜ƒxxxx.add(â˜ƒxx);
         â˜ƒxxxx.add(â˜ƒxxx);
         return â˜ƒxxxx;
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         int â˜ƒ = Math.max(â˜ƒ.getSeaLevel(), 64) - this.boundingBox.minY();
         this.generateWaterBox(â˜ƒ, â˜ƒ, 0, 0, 0, 58, â˜ƒ, 58);
         this.generateWing(false, 0, â˜ƒ, â˜ƒ, â˜ƒ);
         this.generateWing(true, 33, â˜ƒ, â˜ƒ, â˜ƒ);
         this.generateEntranceArchs(â˜ƒ, â˜ƒ, â˜ƒ);
         this.generateEntranceWall(â˜ƒ, â˜ƒ, â˜ƒ);
         this.generateRoofPiece(â˜ƒ, â˜ƒ, â˜ƒ);
         this.generateLowerWall(â˜ƒ, â˜ƒ, â˜ƒ);
         this.generateMiddleWall(â˜ƒ, â˜ƒ, â˜ƒ);
         this.generateUpperWall(â˜ƒ, â˜ƒ, â˜ƒ);

         for(int â˜ƒx = 0; â˜ƒx < 7; ++â˜ƒx) {
            int â˜ƒxx = 0;

            while(â˜ƒxx < 7) {
               if (â˜ƒxx == 0 && â˜ƒx == 3) {
                  â˜ƒxx = 6;
               }

               int â˜ƒxxx = â˜ƒx * 9;
               int â˜ƒxxxx = â˜ƒxx * 9;

               for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < 4; ++â˜ƒxxxxx) {
                  for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < 4; ++â˜ƒxxxxxx) {
                     this.placeBlock(â˜ƒ, BASE_LIGHT, â˜ƒxxx + â˜ƒxxxxx, 0, â˜ƒxxxx + â˜ƒxxxxxx, â˜ƒ);
                     this.fillColumnDown(â˜ƒ, BASE_LIGHT, â˜ƒxxx + â˜ƒxxxxx, -1, â˜ƒxxxx + â˜ƒxxxxxx, â˜ƒ);
                  }
               }

               if (â˜ƒx != 0 && â˜ƒx != 6) {
                  â˜ƒxx += 6;
               } else {
                  ++â˜ƒxx;
               }
            }
         }

         for(int â˜ƒx = 0; â˜ƒx < 5; ++â˜ƒx) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, -1 - â˜ƒx, 0 + â˜ƒx * 2, -1 - â˜ƒx, -1 - â˜ƒx, 23, 58 + â˜ƒx);
            this.generateWaterBox(â˜ƒ, â˜ƒ, 58 + â˜ƒx, 0 + â˜ƒx * 2, -1 - â˜ƒx, 58 + â˜ƒx, 23, 58 + â˜ƒx);
            this.generateWaterBox(â˜ƒ, â˜ƒ, 0 - â˜ƒx, 0 + â˜ƒx * 2, -1 - â˜ƒx, 57 + â˜ƒx, 23, -1 - â˜ƒx);
            this.generateWaterBox(â˜ƒ, â˜ƒ, 0 - â˜ƒx, 0 + â˜ƒx * 2, 58 + â˜ƒx, 57 + â˜ƒx, 23, 58 + â˜ƒx);
         }

         for(OceanMonumentPieces.OceanMonumentPiece â˜ƒx : this.childPieces) {
            if (â˜ƒx.getBoundingBox().intersects(â˜ƒ)) {
               â˜ƒx.postProcess(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
            }
         }

         return true;
      }

      private void generateWing(boolean var1, int var2, WorldGenLevel var3, Random var4, BoundingBox var5) {
         int â˜ƒ = 24;
         if (this.chunkIntersects(â˜ƒ, â˜ƒ, 0, â˜ƒ + 23, 20)) {
            this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ + 0, 0, 0, â˜ƒ + 24, 0, 20, BASE_GRAY, BASE_GRAY, false);
            this.generateWaterBox(â˜ƒ, â˜ƒ, â˜ƒ + 0, 1, 0, â˜ƒ + 24, 10, 20);

            for(int â˜ƒx = 0; â˜ƒx < 4; ++â˜ƒx) {
               this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒx, â˜ƒx + 1, â˜ƒx, â˜ƒ + â˜ƒx, â˜ƒx + 1, 20, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒx + 7, â˜ƒx + 5, â˜ƒx + 7, â˜ƒ + â˜ƒx + 7, â˜ƒx + 5, 20, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ + 17 - â˜ƒx, â˜ƒx + 5, â˜ƒx + 7, â˜ƒ + 17 - â˜ƒx, â˜ƒx + 5, 20, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ + 24 - â˜ƒx, â˜ƒx + 1, â˜ƒx, â˜ƒ + 24 - â˜ƒx, â˜ƒx + 1, 20, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒx + 1, â˜ƒx + 1, â˜ƒx, â˜ƒ + 23 - â˜ƒx, â˜ƒx + 1, â˜ƒx, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒx + 8, â˜ƒx + 5, â˜ƒx + 7, â˜ƒ + 16 - â˜ƒx, â˜ƒx + 5, â˜ƒx + 7, BASE_LIGHT, BASE_LIGHT, false);
            }

            this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ + 4, 4, 4, â˜ƒ + 6, 4, 20, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ + 7, 4, 4, â˜ƒ + 17, 4, 6, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ + 18, 4, 4, â˜ƒ + 20, 4, 20, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ + 11, 8, 11, â˜ƒ + 13, 8, 20, BASE_GRAY, BASE_GRAY, false);
            this.placeBlock(â˜ƒ, DOT_DECO_DATA, â˜ƒ + 12, 9, 12, â˜ƒ);
            this.placeBlock(â˜ƒ, DOT_DECO_DATA, â˜ƒ + 12, 9, 15, â˜ƒ);
            this.placeBlock(â˜ƒ, DOT_DECO_DATA, â˜ƒ + 12, 9, 18, â˜ƒ);
            int â˜ƒx = â˜ƒ + (â˜ƒ ? 19 : 5);
            int â˜ƒxx = â˜ƒ + (â˜ƒ ? 5 : 19);

            for(int â˜ƒxxx = 20; â˜ƒxxx >= 5; â˜ƒxxx -= 3) {
               this.placeBlock(â˜ƒ, DOT_DECO_DATA, â˜ƒx, 5, â˜ƒxxx, â˜ƒ);
            }

            for(int â˜ƒxxx = 19; â˜ƒxxx >= 7; â˜ƒxxx -= 3) {
               this.placeBlock(â˜ƒ, DOT_DECO_DATA, â˜ƒxx, 5, â˜ƒxxx, â˜ƒ);
            }

            for(int â˜ƒxxx = 0; â˜ƒxxx < 4; ++â˜ƒxxx) {
               int â˜ƒxxxx = â˜ƒ ? â˜ƒ + 24 - (17 - â˜ƒxxx * 3) : â˜ƒ + 17 - â˜ƒxxx * 3;
               this.placeBlock(â˜ƒ, DOT_DECO_DATA, â˜ƒxxxx, 5, 5, â˜ƒ);
            }

            this.placeBlock(â˜ƒ, DOT_DECO_DATA, â˜ƒxx, 5, 5, â˜ƒ);
            this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ + 11, 1, 12, â˜ƒ + 13, 7, 12, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ + 12, 1, 11, â˜ƒ + 12, 7, 13, BASE_GRAY, BASE_GRAY, false);
         }
      }

      private void generateEntranceArchs(WorldGenLevel var1, Random var2, BoundingBox var3) {
         if (this.chunkIntersects(â˜ƒ, 22, 5, 35, 17)) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 25, 0, 0, 32, 8, 20);

            for(int â˜ƒ = 0; â˜ƒ < 4; ++â˜ƒ) {
               this.generateBox(â˜ƒ, â˜ƒ, 24, 2, 5 + â˜ƒ * 4, 24, 4, 5 + â˜ƒ * 4, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, 22, 4, 5 + â˜ƒ * 4, 23, 4, 5 + â˜ƒ * 4, BASE_LIGHT, BASE_LIGHT, false);
               this.placeBlock(â˜ƒ, BASE_LIGHT, 25, 5, 5 + â˜ƒ * 4, â˜ƒ);
               this.placeBlock(â˜ƒ, BASE_LIGHT, 26, 6, 5 + â˜ƒ * 4, â˜ƒ);
               this.placeBlock(â˜ƒ, LAMP_BLOCK, 26, 5, 5 + â˜ƒ * 4, â˜ƒ);
               this.generateBox(â˜ƒ, â˜ƒ, 33, 2, 5 + â˜ƒ * 4, 33, 4, 5 + â˜ƒ * 4, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, 34, 4, 5 + â˜ƒ * 4, 35, 4, 5 + â˜ƒ * 4, BASE_LIGHT, BASE_LIGHT, false);
               this.placeBlock(â˜ƒ, BASE_LIGHT, 32, 5, 5 + â˜ƒ * 4, â˜ƒ);
               this.placeBlock(â˜ƒ, BASE_LIGHT, 31, 6, 5 + â˜ƒ * 4, â˜ƒ);
               this.placeBlock(â˜ƒ, LAMP_BLOCK, 31, 5, 5 + â˜ƒ * 4, â˜ƒ);
               this.generateBox(â˜ƒ, â˜ƒ, 27, 6, 5 + â˜ƒ * 4, 30, 6, 5 + â˜ƒ * 4, BASE_GRAY, BASE_GRAY, false);
            }
         }
      }

      private void generateEntranceWall(WorldGenLevel var1, Random var2, BoundingBox var3) {
         if (this.chunkIntersects(â˜ƒ, 15, 20, 42, 21)) {
            this.generateBox(â˜ƒ, â˜ƒ, 15, 0, 21, 42, 0, 21, BASE_GRAY, BASE_GRAY, false);
            this.generateWaterBox(â˜ƒ, â˜ƒ, 26, 1, 21, 31, 3, 21);
            this.generateBox(â˜ƒ, â˜ƒ, 21, 12, 21, 36, 12, 21, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, 17, 11, 21, 40, 11, 21, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, 16, 10, 21, 41, 10, 21, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, 15, 7, 21, 42, 9, 21, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, 16, 6, 21, 41, 6, 21, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, 17, 5, 21, 40, 5, 21, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, 21, 4, 21, 36, 4, 21, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, 22, 3, 21, 26, 3, 21, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, 31, 3, 21, 35, 3, 21, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, 23, 2, 21, 25, 2, 21, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, 32, 2, 21, 34, 2, 21, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, 28, 4, 20, 29, 4, 21, BASE_LIGHT, BASE_LIGHT, false);
            this.placeBlock(â˜ƒ, BASE_LIGHT, 27, 3, 21, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_LIGHT, 30, 3, 21, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_LIGHT, 26, 2, 21, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_LIGHT, 31, 2, 21, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_LIGHT, 25, 1, 21, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_LIGHT, 32, 1, 21, â˜ƒ);

            for(int â˜ƒ = 0; â˜ƒ < 7; ++â˜ƒ) {
               this.placeBlock(â˜ƒ, BASE_BLACK, 28 - â˜ƒ, 6 + â˜ƒ, 21, â˜ƒ);
               this.placeBlock(â˜ƒ, BASE_BLACK, 29 + â˜ƒ, 6 + â˜ƒ, 21, â˜ƒ);
            }

            for(int â˜ƒ = 0; â˜ƒ < 4; ++â˜ƒ) {
               this.placeBlock(â˜ƒ, BASE_BLACK, 28 - â˜ƒ, 9 + â˜ƒ, 21, â˜ƒ);
               this.placeBlock(â˜ƒ, BASE_BLACK, 29 + â˜ƒ, 9 + â˜ƒ, 21, â˜ƒ);
            }

            this.placeBlock(â˜ƒ, BASE_BLACK, 28, 12, 21, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_BLACK, 29, 12, 21, â˜ƒ);

            for(int â˜ƒ = 0; â˜ƒ < 3; ++â˜ƒ) {
               this.placeBlock(â˜ƒ, BASE_BLACK, 22 - â˜ƒ * 2, 8, 21, â˜ƒ);
               this.placeBlock(â˜ƒ, BASE_BLACK, 22 - â˜ƒ * 2, 9, 21, â˜ƒ);
               this.placeBlock(â˜ƒ, BASE_BLACK, 35 + â˜ƒ * 2, 8, 21, â˜ƒ);
               this.placeBlock(â˜ƒ, BASE_BLACK, 35 + â˜ƒ * 2, 9, 21, â˜ƒ);
            }

            this.generateWaterBox(â˜ƒ, â˜ƒ, 15, 13, 21, 42, 15, 21);
            this.generateWaterBox(â˜ƒ, â˜ƒ, 15, 1, 21, 15, 6, 21);
            this.generateWaterBox(â˜ƒ, â˜ƒ, 16, 1, 21, 16, 5, 21);
            this.generateWaterBox(â˜ƒ, â˜ƒ, 17, 1, 21, 20, 4, 21);
            this.generateWaterBox(â˜ƒ, â˜ƒ, 21, 1, 21, 21, 3, 21);
            this.generateWaterBox(â˜ƒ, â˜ƒ, 22, 1, 21, 22, 2, 21);
            this.generateWaterBox(â˜ƒ, â˜ƒ, 23, 1, 21, 24, 1, 21);
            this.generateWaterBox(â˜ƒ, â˜ƒ, 42, 1, 21, 42, 6, 21);
            this.generateWaterBox(â˜ƒ, â˜ƒ, 41, 1, 21, 41, 5, 21);
            this.generateWaterBox(â˜ƒ, â˜ƒ, 37, 1, 21, 40, 4, 21);
            this.generateWaterBox(â˜ƒ, â˜ƒ, 36, 1, 21, 36, 3, 21);
            this.generateWaterBox(â˜ƒ, â˜ƒ, 33, 1, 21, 34, 1, 21);
            this.generateWaterBox(â˜ƒ, â˜ƒ, 35, 1, 21, 35, 2, 21);
         }
      }

      private void generateRoofPiece(WorldGenLevel var1, Random var2, BoundingBox var3) {
         if (this.chunkIntersects(â˜ƒ, 21, 21, 36, 36)) {
            this.generateBox(â˜ƒ, â˜ƒ, 21, 0, 22, 36, 0, 36, BASE_GRAY, BASE_GRAY, false);
            this.generateWaterBox(â˜ƒ, â˜ƒ, 21, 1, 22, 36, 23, 36);

            for(int â˜ƒ = 0; â˜ƒ < 4; ++â˜ƒ) {
               this.generateBox(â˜ƒ, â˜ƒ, 21 + â˜ƒ, 13 + â˜ƒ, 21 + â˜ƒ, 36 - â˜ƒ, 13 + â˜ƒ, 21 + â˜ƒ, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, 21 + â˜ƒ, 13 + â˜ƒ, 36 - â˜ƒ, 36 - â˜ƒ, 13 + â˜ƒ, 36 - â˜ƒ, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, 21 + â˜ƒ, 13 + â˜ƒ, 22 + â˜ƒ, 21 + â˜ƒ, 13 + â˜ƒ, 35 - â˜ƒ, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, 36 - â˜ƒ, 13 + â˜ƒ, 22 + â˜ƒ, 36 - â˜ƒ, 13 + â˜ƒ, 35 - â˜ƒ, BASE_LIGHT, BASE_LIGHT, false);
            }

            this.generateBox(â˜ƒ, â˜ƒ, 25, 16, 25, 32, 16, 32, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, 25, 17, 25, 25, 19, 25, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 32, 17, 25, 32, 19, 25, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 25, 17, 32, 25, 19, 32, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 32, 17, 32, 32, 19, 32, BASE_LIGHT, BASE_LIGHT, false);
            this.placeBlock(â˜ƒ, BASE_LIGHT, 26, 20, 26, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_LIGHT, 27, 21, 27, â˜ƒ);
            this.placeBlock(â˜ƒ, LAMP_BLOCK, 27, 20, 27, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_LIGHT, 26, 20, 31, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_LIGHT, 27, 21, 30, â˜ƒ);
            this.placeBlock(â˜ƒ, LAMP_BLOCK, 27, 20, 30, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_LIGHT, 31, 20, 31, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_LIGHT, 30, 21, 30, â˜ƒ);
            this.placeBlock(â˜ƒ, LAMP_BLOCK, 30, 20, 30, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_LIGHT, 31, 20, 26, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_LIGHT, 30, 21, 27, â˜ƒ);
            this.placeBlock(â˜ƒ, LAMP_BLOCK, 30, 20, 27, â˜ƒ);
            this.generateBox(â˜ƒ, â˜ƒ, 28, 21, 27, 29, 21, 27, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, 27, 21, 28, 27, 21, 29, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, 28, 21, 30, 29, 21, 30, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, 30, 21, 28, 30, 21, 29, BASE_GRAY, BASE_GRAY, false);
         }
      }

      private void generateLowerWall(WorldGenLevel var1, Random var2, BoundingBox var3) {
         if (this.chunkIntersects(â˜ƒ, 0, 21, 6, 58)) {
            this.generateBox(â˜ƒ, â˜ƒ, 0, 0, 21, 6, 0, 57, BASE_GRAY, BASE_GRAY, false);
            this.generateWaterBox(â˜ƒ, â˜ƒ, 0, 1, 21, 6, 7, 57);
            this.generateBox(â˜ƒ, â˜ƒ, 4, 4, 21, 6, 4, 53, BASE_GRAY, BASE_GRAY, false);

            for(int â˜ƒ = 0; â˜ƒ < 4; ++â˜ƒ) {
               this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + 1, 21, â˜ƒ, â˜ƒ + 1, 57 - â˜ƒ, BASE_LIGHT, BASE_LIGHT, false);
            }

            for(int â˜ƒ = 23; â˜ƒ < 53; â˜ƒ += 3) {
               this.placeBlock(â˜ƒ, DOT_DECO_DATA, 5, 5, â˜ƒ, â˜ƒ);
            }

            this.placeBlock(â˜ƒ, DOT_DECO_DATA, 5, 5, 52, â˜ƒ);

            for(int â˜ƒ = 0; â˜ƒ < 4; ++â˜ƒ) {
               this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + 1, 21, â˜ƒ, â˜ƒ + 1, 57 - â˜ƒ, BASE_LIGHT, BASE_LIGHT, false);
            }

            this.generateBox(â˜ƒ, â˜ƒ, 4, 1, 52, 6, 3, 52, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, 5, 1, 51, 5, 3, 53, BASE_GRAY, BASE_GRAY, false);
         }

         if (this.chunkIntersects(â˜ƒ, 51, 21, 58, 58)) {
            this.generateBox(â˜ƒ, â˜ƒ, 51, 0, 21, 57, 0, 57, BASE_GRAY, BASE_GRAY, false);
            this.generateWaterBox(â˜ƒ, â˜ƒ, 51, 1, 21, 57, 7, 57);
            this.generateBox(â˜ƒ, â˜ƒ, 51, 4, 21, 53, 4, 53, BASE_GRAY, BASE_GRAY, false);

            for(int â˜ƒ = 0; â˜ƒ < 4; ++â˜ƒ) {
               this.generateBox(â˜ƒ, â˜ƒ, 57 - â˜ƒ, â˜ƒ + 1, 21, 57 - â˜ƒ, â˜ƒ + 1, 57 - â˜ƒ, BASE_LIGHT, BASE_LIGHT, false);
            }

            for(int â˜ƒ = 23; â˜ƒ < 53; â˜ƒ += 3) {
               this.placeBlock(â˜ƒ, DOT_DECO_DATA, 52, 5, â˜ƒ, â˜ƒ);
            }

            this.placeBlock(â˜ƒ, DOT_DECO_DATA, 52, 5, 52, â˜ƒ);
            this.generateBox(â˜ƒ, â˜ƒ, 51, 1, 52, 53, 3, 52, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, 52, 1, 51, 52, 3, 53, BASE_GRAY, BASE_GRAY, false);
         }

         if (this.chunkIntersects(â˜ƒ, 0, 51, 57, 57)) {
            this.generateBox(â˜ƒ, â˜ƒ, 7, 0, 51, 50, 0, 57, BASE_GRAY, BASE_GRAY, false);
            this.generateWaterBox(â˜ƒ, â˜ƒ, 7, 1, 51, 50, 10, 57);

            for(int â˜ƒ = 0; â˜ƒ < 4; ++â˜ƒ) {
               this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ + 1, â˜ƒ + 1, 57 - â˜ƒ, 56 - â˜ƒ, â˜ƒ + 1, 57 - â˜ƒ, BASE_LIGHT, BASE_LIGHT, false);
            }
         }
      }

      private void generateMiddleWall(WorldGenLevel var1, Random var2, BoundingBox var3) {
         if (this.chunkIntersects(â˜ƒ, 7, 21, 13, 50)) {
            this.generateBox(â˜ƒ, â˜ƒ, 7, 0, 21, 13, 0, 50, BASE_GRAY, BASE_GRAY, false);
            this.generateWaterBox(â˜ƒ, â˜ƒ, 7, 1, 21, 13, 10, 50);
            this.generateBox(â˜ƒ, â˜ƒ, 11, 8, 21, 13, 8, 53, BASE_GRAY, BASE_GRAY, false);

            for(int â˜ƒ = 0; â˜ƒ < 4; ++â˜ƒ) {
               this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ + 7, â˜ƒ + 5, 21, â˜ƒ + 7, â˜ƒ + 5, 54, BASE_LIGHT, BASE_LIGHT, false);
            }

            for(int â˜ƒ = 21; â˜ƒ <= 45; â˜ƒ += 3) {
               this.placeBlock(â˜ƒ, DOT_DECO_DATA, 12, 9, â˜ƒ, â˜ƒ);
            }
         }

         if (this.chunkIntersects(â˜ƒ, 44, 21, 50, 54)) {
            this.generateBox(â˜ƒ, â˜ƒ, 44, 0, 21, 50, 0, 50, BASE_GRAY, BASE_GRAY, false);
            this.generateWaterBox(â˜ƒ, â˜ƒ, 44, 1, 21, 50, 10, 50);
            this.generateBox(â˜ƒ, â˜ƒ, 44, 8, 21, 46, 8, 53, BASE_GRAY, BASE_GRAY, false);

            for(int â˜ƒ = 0; â˜ƒ < 4; ++â˜ƒ) {
               this.generateBox(â˜ƒ, â˜ƒ, 50 - â˜ƒ, â˜ƒ + 5, 21, 50 - â˜ƒ, â˜ƒ + 5, 54, BASE_LIGHT, BASE_LIGHT, false);
            }

            for(int â˜ƒ = 21; â˜ƒ <= 45; â˜ƒ += 3) {
               this.placeBlock(â˜ƒ, DOT_DECO_DATA, 45, 9, â˜ƒ, â˜ƒ);
            }
         }

         if (this.chunkIntersects(â˜ƒ, 8, 44, 49, 54)) {
            this.generateBox(â˜ƒ, â˜ƒ, 14, 0, 44, 43, 0, 50, BASE_GRAY, BASE_GRAY, false);
            this.generateWaterBox(â˜ƒ, â˜ƒ, 14, 1, 44, 43, 10, 50);

            for(int â˜ƒ = 12; â˜ƒ <= 45; â˜ƒ += 3) {
               this.placeBlock(â˜ƒ, DOT_DECO_DATA, â˜ƒ, 9, 45, â˜ƒ);
               this.placeBlock(â˜ƒ, DOT_DECO_DATA, â˜ƒ, 9, 52, â˜ƒ);
               if (â˜ƒ == 12 || â˜ƒ == 18 || â˜ƒ == 24 || â˜ƒ == 33 || â˜ƒ == 39 || â˜ƒ == 45) {
                  this.placeBlock(â˜ƒ, DOT_DECO_DATA, â˜ƒ, 9, 47, â˜ƒ);
                  this.placeBlock(â˜ƒ, DOT_DECO_DATA, â˜ƒ, 9, 50, â˜ƒ);
                  this.placeBlock(â˜ƒ, DOT_DECO_DATA, â˜ƒ, 10, 45, â˜ƒ);
                  this.placeBlock(â˜ƒ, DOT_DECO_DATA, â˜ƒ, 10, 46, â˜ƒ);
                  this.placeBlock(â˜ƒ, DOT_DECO_DATA, â˜ƒ, 10, 51, â˜ƒ);
                  this.placeBlock(â˜ƒ, DOT_DECO_DATA, â˜ƒ, 10, 52, â˜ƒ);
                  this.placeBlock(â˜ƒ, DOT_DECO_DATA, â˜ƒ, 11, 47, â˜ƒ);
                  this.placeBlock(â˜ƒ, DOT_DECO_DATA, â˜ƒ, 11, 50, â˜ƒ);
                  this.placeBlock(â˜ƒ, DOT_DECO_DATA, â˜ƒ, 12, 48, â˜ƒ);
                  this.placeBlock(â˜ƒ, DOT_DECO_DATA, â˜ƒ, 12, 49, â˜ƒ);
               }
            }

            for(int â˜ƒ = 0; â˜ƒ < 3; ++â˜ƒ) {
               this.generateBox(â˜ƒ, â˜ƒ, 8 + â˜ƒ, 5 + â˜ƒ, 54, 49 - â˜ƒ, 5 + â˜ƒ, 54, BASE_GRAY, BASE_GRAY, false);
            }

            this.generateBox(â˜ƒ, â˜ƒ, 11, 8, 54, 46, 8, 54, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 14, 8, 44, 43, 8, 53, BASE_GRAY, BASE_GRAY, false);
         }
      }

      private void generateUpperWall(WorldGenLevel var1, Random var2, BoundingBox var3) {
         if (this.chunkIntersects(â˜ƒ, 14, 21, 20, 43)) {
            this.generateBox(â˜ƒ, â˜ƒ, 14, 0, 21, 20, 0, 43, BASE_GRAY, BASE_GRAY, false);
            this.generateWaterBox(â˜ƒ, â˜ƒ, 14, 1, 22, 20, 14, 43);
            this.generateBox(â˜ƒ, â˜ƒ, 18, 12, 22, 20, 12, 39, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, 18, 12, 21, 20, 12, 21, BASE_LIGHT, BASE_LIGHT, false);

            for(int â˜ƒ = 0; â˜ƒ < 4; ++â˜ƒ) {
               this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ + 14, â˜ƒ + 9, 21, â˜ƒ + 14, â˜ƒ + 9, 43 - â˜ƒ, BASE_LIGHT, BASE_LIGHT, false);
            }

            for(int â˜ƒ = 23; â˜ƒ <= 39; â˜ƒ += 3) {
               this.placeBlock(â˜ƒ, DOT_DECO_DATA, 19, 13, â˜ƒ, â˜ƒ);
            }
         }

         if (this.chunkIntersects(â˜ƒ, 37, 21, 43, 43)) {
            this.generateBox(â˜ƒ, â˜ƒ, 37, 0, 21, 43, 0, 43, BASE_GRAY, BASE_GRAY, false);
            this.generateWaterBox(â˜ƒ, â˜ƒ, 37, 1, 22, 43, 14, 43);
            this.generateBox(â˜ƒ, â˜ƒ, 37, 12, 22, 39, 12, 39, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, 37, 12, 21, 39, 12, 21, BASE_LIGHT, BASE_LIGHT, false);

            for(int â˜ƒ = 0; â˜ƒ < 4; ++â˜ƒ) {
               this.generateBox(â˜ƒ, â˜ƒ, 43 - â˜ƒ, â˜ƒ + 9, 21, 43 - â˜ƒ, â˜ƒ + 9, 43 - â˜ƒ, BASE_LIGHT, BASE_LIGHT, false);
            }

            for(int â˜ƒ = 23; â˜ƒ <= 39; â˜ƒ += 3) {
               this.placeBlock(â˜ƒ, DOT_DECO_DATA, 38, 13, â˜ƒ, â˜ƒ);
            }
         }

         if (this.chunkIntersects(â˜ƒ, 15, 37, 42, 43)) {
            this.generateBox(â˜ƒ, â˜ƒ, 21, 0, 37, 36, 0, 43, BASE_GRAY, BASE_GRAY, false);
            this.generateWaterBox(â˜ƒ, â˜ƒ, 21, 1, 37, 36, 14, 43);
            this.generateBox(â˜ƒ, â˜ƒ, 21, 12, 37, 36, 12, 39, BASE_GRAY, BASE_GRAY, false);

            for(int â˜ƒ = 0; â˜ƒ < 4; ++â˜ƒ) {
               this.generateBox(â˜ƒ, â˜ƒ, 15 + â˜ƒ, â˜ƒ + 9, 43 - â˜ƒ, 42 - â˜ƒ, â˜ƒ + 9, 43 - â˜ƒ, BASE_LIGHT, BASE_LIGHT, false);
            }

            for(int â˜ƒ = 21; â˜ƒ <= 36; â˜ƒ += 3) {
               this.placeBlock(â˜ƒ, DOT_DECO_DATA, â˜ƒ, 13, 38, â˜ƒ);
            }
         }
      }
   }

   interface MonumentRoomFitter {
      boolean fits(OceanMonumentPieces.RoomDefinition var1);

      OceanMonumentPieces.OceanMonumentPiece create(Direction var1, OceanMonumentPieces.RoomDefinition var2, Random var3);
   }

   public static class OceanMonumentCoreRoom extends OceanMonumentPieces.OceanMonumentPiece {
      public OceanMonumentCoreRoom(Direction var1, OceanMonumentPieces.RoomDefinition var2) {
         super(StructurePieceType.OCEAN_MONUMENT_CORE_ROOM, 1, â˜ƒ, â˜ƒ, 2, 2, 2);
      }

      public OceanMonumentCoreRoom(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.OCEAN_MONUMENT_CORE_ROOM, â˜ƒ);
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         this.generateBoxOnFillOnly(â˜ƒ, â˜ƒ, 1, 8, 0, 14, 8, 14, BASE_GRAY);
         int â˜ƒ = 7;
         BlockState â˜ƒx = BASE_LIGHT;
         this.generateBox(â˜ƒ, â˜ƒ, 0, 7, 0, 0, 7, 15, â˜ƒx, â˜ƒx, false);
         this.generateBox(â˜ƒ, â˜ƒ, 15, 7, 0, 15, 7, 15, â˜ƒx, â˜ƒx, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 7, 0, 15, 7, 0, â˜ƒx, â˜ƒx, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 7, 15, 14, 7, 15, â˜ƒx, â˜ƒx, false);

         for(int â˜ƒxx = 1; â˜ƒxx <= 6; ++â˜ƒxx) {
            â˜ƒx = BASE_LIGHT;
            if (â˜ƒxx == 2 || â˜ƒxx == 6) {
               â˜ƒx = BASE_GRAY;
            }

            for(int â˜ƒxxx = 0; â˜ƒxxx <= 15; â˜ƒxxx += 15) {
               this.generateBox(â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒxx, 0, â˜ƒxxx, â˜ƒxx, 1, â˜ƒx, â˜ƒx, false);
               this.generateBox(â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒxx, 6, â˜ƒxxx, â˜ƒxx, 9, â˜ƒx, â˜ƒx, false);
               this.generateBox(â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒxx, 14, â˜ƒxxx, â˜ƒxx, 15, â˜ƒx, â˜ƒx, false);
            }

            this.generateBox(â˜ƒ, â˜ƒ, 1, â˜ƒxx, 0, 1, â˜ƒxx, 0, â˜ƒx, â˜ƒx, false);
            this.generateBox(â˜ƒ, â˜ƒ, 6, â˜ƒxx, 0, 9, â˜ƒxx, 0, â˜ƒx, â˜ƒx, false);
            this.generateBox(â˜ƒ, â˜ƒ, 14, â˜ƒxx, 0, 14, â˜ƒxx, 0, â˜ƒx, â˜ƒx, false);
            this.generateBox(â˜ƒ, â˜ƒ, 1, â˜ƒxx, 15, 14, â˜ƒxx, 15, â˜ƒx, â˜ƒx, false);
         }

         this.generateBox(â˜ƒ, â˜ƒ, 6, 3, 6, 9, 6, 9, BASE_BLACK, BASE_BLACK, false);
         this.generateBox(â˜ƒ, â˜ƒ, 7, 4, 7, 8, 5, 8, Blocks.GOLD_BLOCK.defaultBlockState(), Blocks.GOLD_BLOCK.defaultBlockState(), false);

         for(int â˜ƒxx = 3; â˜ƒxx <= 6; â˜ƒxx += 3) {
            for(int â˜ƒxxx = 6; â˜ƒxxx <= 9; â˜ƒxxx += 3) {
               this.placeBlock(â˜ƒ, LAMP_BLOCK, â˜ƒxxx, â˜ƒxx, 6, â˜ƒ);
               this.placeBlock(â˜ƒ, LAMP_BLOCK, â˜ƒxxx, â˜ƒxx, 9, â˜ƒ);
            }
         }

         this.generateBox(â˜ƒ, â˜ƒ, 5, 1, 6, 5, 2, 6, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 1, 9, 5, 2, 9, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 10, 1, 6, 10, 2, 6, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 10, 1, 9, 10, 2, 9, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 6, 1, 5, 6, 2, 5, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 9, 1, 5, 9, 2, 5, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 6, 1, 10, 6, 2, 10, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 9, 1, 10, 9, 2, 10, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 2, 5, 5, 6, 5, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 2, 10, 5, 6, 10, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 10, 2, 5, 10, 6, 5, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 10, 2, 10, 10, 6, 10, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 7, 1, 5, 7, 6, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 10, 7, 1, 10, 7, 6, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 7, 9, 5, 7, 14, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 10, 7, 9, 10, 7, 14, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 7, 5, 6, 7, 5, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 7, 10, 6, 7, 10, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 9, 7, 5, 14, 7, 5, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 9, 7, 10, 14, 7, 10, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 1, 2, 2, 1, 3, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 3, 1, 2, 3, 1, 2, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 13, 1, 2, 13, 1, 3, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 12, 1, 2, 12, 1, 2, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 1, 12, 2, 1, 13, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 3, 1, 13, 3, 1, 13, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 13, 1, 12, 13, 1, 13, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 12, 1, 13, 12, 1, 13, BASE_LIGHT, BASE_LIGHT, false);
         return true;
      }
   }

   public static class OceanMonumentDoubleXRoom extends OceanMonumentPieces.OceanMonumentPiece {
      public OceanMonumentDoubleXRoom(Direction var1, OceanMonumentPieces.RoomDefinition var2) {
         super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_X_ROOM, 1, â˜ƒ, â˜ƒ, 2, 1, 1);
      }

      public OceanMonumentDoubleXRoom(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_X_ROOM, â˜ƒ);
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         OceanMonumentPieces.RoomDefinition â˜ƒ = this.roomDefinition.connections[Direction.EAST.get3DDataValue()];
         OceanMonumentPieces.RoomDefinition â˜ƒx = this.roomDefinition;
         if (this.roomDefinition.index / 25 > 0) {
            this.generateDefaultFloor(â˜ƒ, â˜ƒ, 8, 0, â˜ƒ.hasOpening[Direction.DOWN.get3DDataValue()]);
            this.generateDefaultFloor(â˜ƒ, â˜ƒ, 0, 0, â˜ƒx.hasOpening[Direction.DOWN.get3DDataValue()]);
         }

         if (â˜ƒx.connections[Direction.UP.get3DDataValue()] == null) {
            this.generateBoxOnFillOnly(â˜ƒ, â˜ƒ, 1, 4, 1, 7, 4, 6, BASE_GRAY);
         }

         if (â˜ƒ.connections[Direction.UP.get3DDataValue()] == null) {
            this.generateBoxOnFillOnly(â˜ƒ, â˜ƒ, 8, 4, 1, 14, 4, 6, BASE_GRAY);
         }

         this.generateBox(â˜ƒ, â˜ƒ, 0, 3, 0, 0, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 15, 3, 0, 15, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 3, 0, 15, 3, 0, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 3, 7, 14, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 0, 0, 2, 7, BASE_GRAY, BASE_GRAY, false);
         this.generateBox(â˜ƒ, â˜ƒ, 15, 2, 0, 15, 2, 7, BASE_GRAY, BASE_GRAY, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 2, 0, 15, 2, 0, BASE_GRAY, BASE_GRAY, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 2, 7, 14, 2, 7, BASE_GRAY, BASE_GRAY, false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 1, 0, 0, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 15, 1, 0, 15, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 1, 0, 15, 1, 0, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 1, 7, 14, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 1, 0, 10, 1, 4, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 6, 2, 0, 9, 2, 3, BASE_GRAY, BASE_GRAY, false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 3, 0, 10, 3, 4, BASE_LIGHT, BASE_LIGHT, false);
         this.placeBlock(â˜ƒ, LAMP_BLOCK, 6, 2, 3, â˜ƒ);
         this.placeBlock(â˜ƒ, LAMP_BLOCK, 9, 2, 3, â˜ƒ);
         if (â˜ƒx.hasOpening[Direction.SOUTH.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 3, 1, 0, 4, 2, 0);
         }

         if (â˜ƒx.hasOpening[Direction.NORTH.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 3, 1, 7, 4, 2, 7);
         }

         if (â˜ƒx.hasOpening[Direction.WEST.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 0, 1, 3, 0, 2, 4);
         }

         if (â˜ƒ.hasOpening[Direction.SOUTH.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 11, 1, 0, 12, 2, 0);
         }

         if (â˜ƒ.hasOpening[Direction.NORTH.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 11, 1, 7, 12, 2, 7);
         }

         if (â˜ƒ.hasOpening[Direction.EAST.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 15, 1, 3, 15, 2, 4);
         }

         return true;
      }
   }

   public static class OceanMonumentDoubleXYRoom extends OceanMonumentPieces.OceanMonumentPiece {
      public OceanMonumentDoubleXYRoom(Direction var1, OceanMonumentPieces.RoomDefinition var2) {
         super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_XY_ROOM, 1, â˜ƒ, â˜ƒ, 2, 2, 1);
      }

      public OceanMonumentDoubleXYRoom(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_XY_ROOM, â˜ƒ);
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         OceanMonumentPieces.RoomDefinition â˜ƒ = this.roomDefinition.connections[Direction.EAST.get3DDataValue()];
         OceanMonumentPieces.RoomDefinition â˜ƒx = this.roomDefinition;
         OceanMonumentPieces.RoomDefinition â˜ƒxx = â˜ƒx.connections[Direction.UP.get3DDataValue()];
         OceanMonumentPieces.RoomDefinition â˜ƒxxx = â˜ƒ.connections[Direction.UP.get3DDataValue()];
         if (this.roomDefinition.index / 25 > 0) {
            this.generateDefaultFloor(â˜ƒ, â˜ƒ, 8, 0, â˜ƒ.hasOpening[Direction.DOWN.get3DDataValue()]);
            this.generateDefaultFloor(â˜ƒ, â˜ƒ, 0, 0, â˜ƒx.hasOpening[Direction.DOWN.get3DDataValue()]);
         }

         if (â˜ƒxx.connections[Direction.UP.get3DDataValue()] == null) {
            this.generateBoxOnFillOnly(â˜ƒ, â˜ƒ, 1, 8, 1, 7, 8, 6, BASE_GRAY);
         }

         if (â˜ƒxxx.connections[Direction.UP.get3DDataValue()] == null) {
            this.generateBoxOnFillOnly(â˜ƒ, â˜ƒ, 8, 8, 1, 14, 8, 6, BASE_GRAY);
         }

         for(int â˜ƒ = 1; â˜ƒ <= 7; ++â˜ƒ) {
            BlockState â˜ƒx = BASE_LIGHT;
            if (â˜ƒ == 2 || â˜ƒ == 6) {
               â˜ƒx = BASE_GRAY;
            }

            this.generateBox(â˜ƒ, â˜ƒ, 0, â˜ƒ, 0, 0, â˜ƒ, 7, â˜ƒx, â˜ƒx, false);
            this.generateBox(â˜ƒ, â˜ƒ, 15, â˜ƒ, 0, 15, â˜ƒ, 7, â˜ƒx, â˜ƒx, false);
            this.generateBox(â˜ƒ, â˜ƒ, 1, â˜ƒ, 0, 15, â˜ƒ, 0, â˜ƒx, â˜ƒx, false);
            this.generateBox(â˜ƒ, â˜ƒ, 1, â˜ƒ, 7, 14, â˜ƒ, 7, â˜ƒx, â˜ƒx, false);
         }

         this.generateBox(â˜ƒ, â˜ƒ, 2, 1, 3, 2, 7, 4, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 3, 1, 2, 4, 7, 2, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 3, 1, 5, 4, 7, 5, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 13, 1, 3, 13, 7, 4, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 11, 1, 2, 12, 7, 2, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 11, 1, 5, 12, 7, 5, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 1, 3, 5, 3, 4, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 10, 1, 3, 10, 3, 4, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 7, 2, 10, 7, 5, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 5, 2, 5, 7, 2, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 10, 5, 2, 10, 7, 2, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 5, 5, 5, 7, 5, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 10, 5, 5, 10, 7, 5, BASE_LIGHT, BASE_LIGHT, false);
         this.placeBlock(â˜ƒ, BASE_LIGHT, 6, 6, 2, â˜ƒ);
         this.placeBlock(â˜ƒ, BASE_LIGHT, 9, 6, 2, â˜ƒ);
         this.placeBlock(â˜ƒ, BASE_LIGHT, 6, 6, 5, â˜ƒ);
         this.placeBlock(â˜ƒ, BASE_LIGHT, 9, 6, 5, â˜ƒ);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 4, 3, 6, 4, 4, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 9, 4, 3, 10, 4, 4, BASE_LIGHT, BASE_LIGHT, false);
         this.placeBlock(â˜ƒ, LAMP_BLOCK, 5, 4, 2, â˜ƒ);
         this.placeBlock(â˜ƒ, LAMP_BLOCK, 5, 4, 5, â˜ƒ);
         this.placeBlock(â˜ƒ, LAMP_BLOCK, 10, 4, 2, â˜ƒ);
         this.placeBlock(â˜ƒ, LAMP_BLOCK, 10, 4, 5, â˜ƒ);
         if (â˜ƒx.hasOpening[Direction.SOUTH.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 3, 1, 0, 4, 2, 0);
         }

         if (â˜ƒx.hasOpening[Direction.NORTH.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 3, 1, 7, 4, 2, 7);
         }

         if (â˜ƒx.hasOpening[Direction.WEST.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 0, 1, 3, 0, 2, 4);
         }

         if (â˜ƒ.hasOpening[Direction.SOUTH.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 11, 1, 0, 12, 2, 0);
         }

         if (â˜ƒ.hasOpening[Direction.NORTH.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 11, 1, 7, 12, 2, 7);
         }

         if (â˜ƒ.hasOpening[Direction.EAST.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 15, 1, 3, 15, 2, 4);
         }

         if (â˜ƒxx.hasOpening[Direction.SOUTH.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 3, 5, 0, 4, 6, 0);
         }

         if (â˜ƒxx.hasOpening[Direction.NORTH.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 3, 5, 7, 4, 6, 7);
         }

         if (â˜ƒxx.hasOpening[Direction.WEST.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 0, 5, 3, 0, 6, 4);
         }

         if (â˜ƒxxx.hasOpening[Direction.SOUTH.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 11, 5, 0, 12, 6, 0);
         }

         if (â˜ƒxxx.hasOpening[Direction.NORTH.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 11, 5, 7, 12, 6, 7);
         }

         if (â˜ƒxxx.hasOpening[Direction.EAST.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 15, 5, 3, 15, 6, 4);
         }

         return true;
      }
   }

   public static class OceanMonumentDoubleYRoom extends OceanMonumentPieces.OceanMonumentPiece {
      public OceanMonumentDoubleYRoom(Direction var1, OceanMonumentPieces.RoomDefinition var2) {
         super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_Y_ROOM, 1, â˜ƒ, â˜ƒ, 1, 2, 1);
      }

      public OceanMonumentDoubleYRoom(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_Y_ROOM, â˜ƒ);
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         if (this.roomDefinition.index / 25 > 0) {
            this.generateDefaultFloor(â˜ƒ, â˜ƒ, 0, 0, this.roomDefinition.hasOpening[Direction.DOWN.get3DDataValue()]);
         }

         OceanMonumentPieces.RoomDefinition â˜ƒ = this.roomDefinition.connections[Direction.UP.get3DDataValue()];
         if (â˜ƒ.connections[Direction.UP.get3DDataValue()] == null) {
            this.generateBoxOnFillOnly(â˜ƒ, â˜ƒ, 1, 8, 1, 6, 8, 6, BASE_GRAY);
         }

         this.generateBox(â˜ƒ, â˜ƒ, 0, 4, 0, 0, 4, 7, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 7, 4, 0, 7, 4, 7, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 4, 0, 6, 4, 0, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 4, 7, 6, 4, 7, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 4, 1, 2, 4, 2, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 4, 2, 1, 4, 2, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 4, 1, 5, 4, 2, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 6, 4, 2, 6, 4, 2, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 4, 5, 2, 4, 6, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 4, 5, 1, 4, 5, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 4, 5, 5, 4, 6, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 6, 4, 5, 6, 4, 5, BASE_LIGHT, BASE_LIGHT, false);
         OceanMonumentPieces.RoomDefinition â˜ƒ = this.roomDefinition;

         for(int â˜ƒx = 1; â˜ƒx <= 5; â˜ƒx += 4) {
            int â˜ƒxx = 0;
            if (â˜ƒ.hasOpening[Direction.SOUTH.get3DDataValue()]) {
               this.generateBox(â˜ƒ, â˜ƒ, 2, â˜ƒx, â˜ƒxx, 2, â˜ƒx + 2, â˜ƒxx, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, 5, â˜ƒx, â˜ƒxx, 5, â˜ƒx + 2, â˜ƒxx, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, 3, â˜ƒx + 2, â˜ƒxx, 4, â˜ƒx + 2, â˜ƒxx, BASE_LIGHT, BASE_LIGHT, false);
            } else {
               this.generateBox(â˜ƒ, â˜ƒ, 0, â˜ƒx, â˜ƒxx, 7, â˜ƒx + 2, â˜ƒxx, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, 0, â˜ƒx + 1, â˜ƒxx, 7, â˜ƒx + 1, â˜ƒxx, BASE_GRAY, BASE_GRAY, false);
            }

            int var13 = 7;
            if (â˜ƒ.hasOpening[Direction.NORTH.get3DDataValue()]) {
               this.generateBox(â˜ƒ, â˜ƒ, 2, â˜ƒx, var13, 2, â˜ƒx + 2, var13, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, 5, â˜ƒx, var13, 5, â˜ƒx + 2, var13, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, 3, â˜ƒx + 2, var13, 4, â˜ƒx + 2, var13, BASE_LIGHT, BASE_LIGHT, false);
            } else {
               this.generateBox(â˜ƒ, â˜ƒ, 0, â˜ƒx, var13, 7, â˜ƒx + 2, var13, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, 0, â˜ƒx + 1, var13, 7, â˜ƒx + 1, var13, BASE_GRAY, BASE_GRAY, false);
            }

            int â˜ƒxx = 0;
            if (â˜ƒ.hasOpening[Direction.WEST.get3DDataValue()]) {
               this.generateBox(â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒx, 2, â˜ƒxx, â˜ƒx + 2, 2, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒx, 5, â˜ƒxx, â˜ƒx + 2, 5, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒx + 2, 3, â˜ƒxx, â˜ƒx + 2, 4, BASE_LIGHT, BASE_LIGHT, false);
            } else {
               this.generateBox(â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒx, 0, â˜ƒxx, â˜ƒx + 2, 7, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒx + 1, 0, â˜ƒxx, â˜ƒx + 1, 7, BASE_GRAY, BASE_GRAY, false);
            }

            int var14 = 7;
            if (â˜ƒ.hasOpening[Direction.EAST.get3DDataValue()]) {
               this.generateBox(â˜ƒ, â˜ƒ, var14, â˜ƒx, 2, var14, â˜ƒx + 2, 2, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, var14, â˜ƒx, 5, var14, â˜ƒx + 2, 5, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, var14, â˜ƒx + 2, 3, var14, â˜ƒx + 2, 4, BASE_LIGHT, BASE_LIGHT, false);
            } else {
               this.generateBox(â˜ƒ, â˜ƒ, var14, â˜ƒx, 0, var14, â˜ƒx + 2, 7, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, var14, â˜ƒx + 1, 0, var14, â˜ƒx + 1, 7, BASE_GRAY, BASE_GRAY, false);
            }

            â˜ƒ = â˜ƒ;
         }

         return true;
      }
   }

   public static class OceanMonumentDoubleYZRoom extends OceanMonumentPieces.OceanMonumentPiece {
      public OceanMonumentDoubleYZRoom(Direction var1, OceanMonumentPieces.RoomDefinition var2) {
         super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_YZ_ROOM, 1, â˜ƒ, â˜ƒ, 1, 2, 2);
      }

      public OceanMonumentDoubleYZRoom(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_YZ_ROOM, â˜ƒ);
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         OceanMonumentPieces.RoomDefinition â˜ƒ = this.roomDefinition.connections[Direction.NORTH.get3DDataValue()];
         OceanMonumentPieces.RoomDefinition â˜ƒx = this.roomDefinition;
         OceanMonumentPieces.RoomDefinition â˜ƒxx = â˜ƒ.connections[Direction.UP.get3DDataValue()];
         OceanMonumentPieces.RoomDefinition â˜ƒxxx = â˜ƒx.connections[Direction.UP.get3DDataValue()];
         if (this.roomDefinition.index / 25 > 0) {
            this.generateDefaultFloor(â˜ƒ, â˜ƒ, 0, 8, â˜ƒ.hasOpening[Direction.DOWN.get3DDataValue()]);
            this.generateDefaultFloor(â˜ƒ, â˜ƒ, 0, 0, â˜ƒx.hasOpening[Direction.DOWN.get3DDataValue()]);
         }

         if (â˜ƒxxx.connections[Direction.UP.get3DDataValue()] == null) {
            this.generateBoxOnFillOnly(â˜ƒ, â˜ƒ, 1, 8, 1, 6, 8, 7, BASE_GRAY);
         }

         if (â˜ƒxx.connections[Direction.UP.get3DDataValue()] == null) {
            this.generateBoxOnFillOnly(â˜ƒ, â˜ƒ, 1, 8, 8, 6, 8, 14, BASE_GRAY);
         }

         for(int â˜ƒ = 1; â˜ƒ <= 7; ++â˜ƒ) {
            BlockState â˜ƒx = BASE_LIGHT;
            if (â˜ƒ == 2 || â˜ƒ == 6) {
               â˜ƒx = BASE_GRAY;
            }

            this.generateBox(â˜ƒ, â˜ƒ, 0, â˜ƒ, 0, 0, â˜ƒ, 15, â˜ƒx, â˜ƒx, false);
            this.generateBox(â˜ƒ, â˜ƒ, 7, â˜ƒ, 0, 7, â˜ƒ, 15, â˜ƒx, â˜ƒx, false);
            this.generateBox(â˜ƒ, â˜ƒ, 1, â˜ƒ, 0, 6, â˜ƒ, 0, â˜ƒx, â˜ƒx, false);
            this.generateBox(â˜ƒ, â˜ƒ, 1, â˜ƒ, 15, 6, â˜ƒ, 15, â˜ƒx, â˜ƒx, false);
         }

         for(int â˜ƒ = 1; â˜ƒ <= 7; ++â˜ƒ) {
            BlockState â˜ƒx = BASE_BLACK;
            if (â˜ƒ == 2 || â˜ƒ == 6) {
               â˜ƒx = LAMP_BLOCK;
            }

            this.generateBox(â˜ƒ, â˜ƒ, 3, â˜ƒ, 7, 4, â˜ƒ, 8, â˜ƒx, â˜ƒx, false);
         }

         if (â˜ƒx.hasOpening[Direction.SOUTH.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 3, 1, 0, 4, 2, 0);
         }

         if (â˜ƒx.hasOpening[Direction.EAST.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 7, 1, 3, 7, 2, 4);
         }

         if (â˜ƒx.hasOpening[Direction.WEST.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 0, 1, 3, 0, 2, 4);
         }

         if (â˜ƒ.hasOpening[Direction.NORTH.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 3, 1, 15, 4, 2, 15);
         }

         if (â˜ƒ.hasOpening[Direction.WEST.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 0, 1, 11, 0, 2, 12);
         }

         if (â˜ƒ.hasOpening[Direction.EAST.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 7, 1, 11, 7, 2, 12);
         }

         if (â˜ƒxxx.hasOpening[Direction.SOUTH.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 3, 5, 0, 4, 6, 0);
         }

         if (â˜ƒxxx.hasOpening[Direction.EAST.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 7, 5, 3, 7, 6, 4);
            this.generateBox(â˜ƒ, â˜ƒ, 5, 4, 2, 6, 4, 5, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 6, 1, 2, 6, 3, 2, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 6, 1, 5, 6, 3, 5, BASE_LIGHT, BASE_LIGHT, false);
         }

         if (â˜ƒxxx.hasOpening[Direction.WEST.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 0, 5, 3, 0, 6, 4);
            this.generateBox(â˜ƒ, â˜ƒ, 1, 4, 2, 2, 4, 5, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 1, 1, 2, 1, 3, 2, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 1, 1, 5, 1, 3, 5, BASE_LIGHT, BASE_LIGHT, false);
         }

         if (â˜ƒxx.hasOpening[Direction.NORTH.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 3, 5, 15, 4, 6, 15);
         }

         if (â˜ƒxx.hasOpening[Direction.WEST.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 0, 5, 11, 0, 6, 12);
            this.generateBox(â˜ƒ, â˜ƒ, 1, 4, 10, 2, 4, 13, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 1, 1, 10, 1, 3, 10, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 1, 1, 13, 1, 3, 13, BASE_LIGHT, BASE_LIGHT, false);
         }

         if (â˜ƒxx.hasOpening[Direction.EAST.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 7, 5, 11, 7, 6, 12);
            this.generateBox(â˜ƒ, â˜ƒ, 5, 4, 10, 6, 4, 13, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 6, 1, 10, 6, 3, 10, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 6, 1, 13, 6, 3, 13, BASE_LIGHT, BASE_LIGHT, false);
         }

         return true;
      }
   }

   public static class OceanMonumentDoubleZRoom extends OceanMonumentPieces.OceanMonumentPiece {
      public OceanMonumentDoubleZRoom(Direction var1, OceanMonumentPieces.RoomDefinition var2) {
         super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_Z_ROOM, 1, â˜ƒ, â˜ƒ, 1, 1, 2);
      }

      public OceanMonumentDoubleZRoom(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_Z_ROOM, â˜ƒ);
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         OceanMonumentPieces.RoomDefinition â˜ƒ = this.roomDefinition.connections[Direction.NORTH.get3DDataValue()];
         OceanMonumentPieces.RoomDefinition â˜ƒx = this.roomDefinition;
         if (this.roomDefinition.index / 25 > 0) {
            this.generateDefaultFloor(â˜ƒ, â˜ƒ, 0, 8, â˜ƒ.hasOpening[Direction.DOWN.get3DDataValue()]);
            this.generateDefaultFloor(â˜ƒ, â˜ƒ, 0, 0, â˜ƒx.hasOpening[Direction.DOWN.get3DDataValue()]);
         }

         if (â˜ƒx.connections[Direction.UP.get3DDataValue()] == null) {
            this.generateBoxOnFillOnly(â˜ƒ, â˜ƒ, 1, 4, 1, 6, 4, 7, BASE_GRAY);
         }

         if (â˜ƒ.connections[Direction.UP.get3DDataValue()] == null) {
            this.generateBoxOnFillOnly(â˜ƒ, â˜ƒ, 1, 4, 8, 6, 4, 14, BASE_GRAY);
         }

         this.generateBox(â˜ƒ, â˜ƒ, 0, 3, 0, 0, 3, 15, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 7, 3, 0, 7, 3, 15, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 3, 0, 7, 3, 0, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 3, 15, 6, 3, 15, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 0, 0, 2, 15, BASE_GRAY, BASE_GRAY, false);
         this.generateBox(â˜ƒ, â˜ƒ, 7, 2, 0, 7, 2, 15, BASE_GRAY, BASE_GRAY, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 2, 0, 7, 2, 0, BASE_GRAY, BASE_GRAY, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 2, 15, 6, 2, 15, BASE_GRAY, BASE_GRAY, false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 1, 0, 0, 1, 15, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 7, 1, 0, 7, 1, 15, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 1, 0, 7, 1, 0, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 1, 15, 6, 1, 15, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 1, 1, 1, 1, 2, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 6, 1, 1, 6, 1, 2, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 3, 1, 1, 3, 2, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 6, 3, 1, 6, 3, 2, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 1, 13, 1, 1, 14, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 6, 1, 13, 6, 1, 14, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 3, 13, 1, 3, 14, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 6, 3, 13, 6, 3, 14, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 1, 6, 2, 3, 6, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 1, 6, 5, 3, 6, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 1, 9, 2, 3, 9, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 1, 9, 5, 3, 9, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 3, 2, 6, 4, 2, 6, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 3, 2, 9, 4, 2, 9, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 2, 7, 2, 2, 8, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 2, 7, 5, 2, 8, BASE_LIGHT, BASE_LIGHT, false);
         this.placeBlock(â˜ƒ, LAMP_BLOCK, 2, 2, 5, â˜ƒ);
         this.placeBlock(â˜ƒ, LAMP_BLOCK, 5, 2, 5, â˜ƒ);
         this.placeBlock(â˜ƒ, LAMP_BLOCK, 2, 2, 10, â˜ƒ);
         this.placeBlock(â˜ƒ, LAMP_BLOCK, 5, 2, 10, â˜ƒ);
         this.placeBlock(â˜ƒ, BASE_LIGHT, 2, 3, 5, â˜ƒ);
         this.placeBlock(â˜ƒ, BASE_LIGHT, 5, 3, 5, â˜ƒ);
         this.placeBlock(â˜ƒ, BASE_LIGHT, 2, 3, 10, â˜ƒ);
         this.placeBlock(â˜ƒ, BASE_LIGHT, 5, 3, 10, â˜ƒ);
         if (â˜ƒx.hasOpening[Direction.SOUTH.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 3, 1, 0, 4, 2, 0);
         }

         if (â˜ƒx.hasOpening[Direction.EAST.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 7, 1, 3, 7, 2, 4);
         }

         if (â˜ƒx.hasOpening[Direction.WEST.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 0, 1, 3, 0, 2, 4);
         }

         if (â˜ƒ.hasOpening[Direction.NORTH.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 3, 1, 15, 4, 2, 15);
         }

         if (â˜ƒ.hasOpening[Direction.WEST.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 0, 1, 11, 0, 2, 12);
         }

         if (â˜ƒ.hasOpening[Direction.EAST.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 7, 1, 11, 7, 2, 12);
         }

         return true;
      }
   }

   public static class OceanMonumentEntryRoom extends OceanMonumentPieces.OceanMonumentPiece {
      public OceanMonumentEntryRoom(Direction var1, OceanMonumentPieces.RoomDefinition var2) {
         super(StructurePieceType.OCEAN_MONUMENT_ENTRY_ROOM, 1, â˜ƒ, â˜ƒ, 1, 1, 1);
      }

      public OceanMonumentEntryRoom(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.OCEAN_MONUMENT_ENTRY_ROOM, â˜ƒ);
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         this.generateBox(â˜ƒ, â˜ƒ, 0, 3, 0, 2, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 3, 0, 7, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 0, 1, 2, 7, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 6, 2, 0, 7, 2, 7, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 1, 0, 0, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 7, 1, 0, 7, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 1, 7, 7, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 1, 0, 2, 3, 0, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 1, 0, 6, 3, 0, BASE_LIGHT, BASE_LIGHT, false);
         if (this.roomDefinition.hasOpening[Direction.NORTH.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 3, 1, 7, 4, 2, 7);
         }

         if (this.roomDefinition.hasOpening[Direction.WEST.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 0, 1, 3, 1, 2, 4);
         }

         if (this.roomDefinition.hasOpening[Direction.EAST.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 6, 1, 3, 7, 2, 4);
         }

         return true;
      }
   }

   public static class OceanMonumentPenthouse extends OceanMonumentPieces.OceanMonumentPiece {
      public OceanMonumentPenthouse(Direction var1, BoundingBox var2) {
         super(StructurePieceType.OCEAN_MONUMENT_PENTHOUSE, â˜ƒ, 1, â˜ƒ);
      }

      public OceanMonumentPenthouse(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.OCEAN_MONUMENT_PENTHOUSE, â˜ƒ);
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         this.generateBox(â˜ƒ, â˜ƒ, 2, -1, 2, 11, -1, 11, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, -1, 0, 1, -1, 11, BASE_GRAY, BASE_GRAY, false);
         this.generateBox(â˜ƒ, â˜ƒ, 12, -1, 0, 13, -1, 11, BASE_GRAY, BASE_GRAY, false);
         this.generateBox(â˜ƒ, â˜ƒ, 2, -1, 0, 11, -1, 1, BASE_GRAY, BASE_GRAY, false);
         this.generateBox(â˜ƒ, â˜ƒ, 2, -1, 12, 11, -1, 13, BASE_GRAY, BASE_GRAY, false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 0, 0, 0, 0, 13, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 13, 0, 0, 13, 0, 13, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 0, 0, 12, 0, 0, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 0, 13, 12, 0, 13, BASE_LIGHT, BASE_LIGHT, false);

         for(int â˜ƒ = 2; â˜ƒ <= 11; â˜ƒ += 3) {
            this.placeBlock(â˜ƒ, LAMP_BLOCK, 0, 0, â˜ƒ, â˜ƒ);
            this.placeBlock(â˜ƒ, LAMP_BLOCK, 13, 0, â˜ƒ, â˜ƒ);
            this.placeBlock(â˜ƒ, LAMP_BLOCK, â˜ƒ, 0, 0, â˜ƒ);
         }

         this.generateBox(â˜ƒ, â˜ƒ, 2, 0, 3, 4, 0, 9, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 9, 0, 3, 11, 0, 9, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 0, 9, 9, 0, 11, BASE_LIGHT, BASE_LIGHT, false);
         this.placeBlock(â˜ƒ, BASE_LIGHT, 5, 0, 8, â˜ƒ);
         this.placeBlock(â˜ƒ, BASE_LIGHT, 8, 0, 8, â˜ƒ);
         this.placeBlock(â˜ƒ, BASE_LIGHT, 10, 0, 10, â˜ƒ);
         this.placeBlock(â˜ƒ, BASE_LIGHT, 3, 0, 10, â˜ƒ);
         this.generateBox(â˜ƒ, â˜ƒ, 3, 0, 3, 3, 0, 7, BASE_BLACK, BASE_BLACK, false);
         this.generateBox(â˜ƒ, â˜ƒ, 10, 0, 3, 10, 0, 7, BASE_BLACK, BASE_BLACK, false);
         this.generateBox(â˜ƒ, â˜ƒ, 6, 0, 10, 7, 0, 10, BASE_BLACK, BASE_BLACK, false);
         int â˜ƒ = 3;

         for(int â˜ƒx = 0; â˜ƒx < 2; ++â˜ƒx) {
            for(int â˜ƒxx = 2; â˜ƒxx <= 8; â˜ƒxx += 3) {
               this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ, 0, â˜ƒxx, â˜ƒ, 2, â˜ƒxx, BASE_LIGHT, BASE_LIGHT, false);
            }

            â˜ƒ = 10;
         }

         this.generateBox(â˜ƒ, â˜ƒ, 5, 0, 10, 5, 2, 10, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 8, 0, 10, 8, 2, 10, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 6, -1, 7, 7, -1, 8, BASE_BLACK, BASE_BLACK, false);
         this.generateWaterBox(â˜ƒ, â˜ƒ, 6, -1, 3, 7, -1, 4);
         this.spawnElder(â˜ƒ, â˜ƒ, 6, 1, 6);
         return true;
      }
   }

   protected abstract static class OceanMonumentPiece extends StructurePiece {
      protected static final BlockState BASE_GRAY = Blocks.PRISMARINE.defaultBlockState();
      protected static final BlockState BASE_LIGHT = Blocks.PRISMARINE_BRICKS.defaultBlockState();
      protected static final BlockState BASE_BLACK = Blocks.DARK_PRISMARINE.defaultBlockState();
      protected static final BlockState DOT_DECO_DATA = BASE_LIGHT;
      protected static final BlockState LAMP_BLOCK = Blocks.SEA_LANTERN.defaultBlockState();
      protected static final boolean DO_FILL = true;
      protected static final BlockState FILL_BLOCK = Blocks.WATER.defaultBlockState();
      protected static final Set<Block> FILL_KEEP = ImmutableSet.<Block>builder()
         .add(Blocks.ICE)
         .add(Blocks.PACKED_ICE)
         .add(Blocks.BLUE_ICE)
         .add(FILL_BLOCK.getBlock())
         .build();
      protected static final int GRIDROOM_WIDTH = 8;
      protected static final int GRIDROOM_DEPTH = 8;
      protected static final int GRIDROOM_HEIGHT = 4;
      protected static final int GRID_WIDTH = 5;
      protected static final int GRID_DEPTH = 5;
      protected static final int GRID_HEIGHT = 3;
      protected static final int GRID_FLOOR_COUNT = 25;
      protected static final int GRID_SIZE = 75;
      protected static final int GRIDROOM_SOURCE_INDEX = getRoomIndex(2, 0, 0);
      protected static final int GRIDROOM_TOP_CONNECT_INDEX = getRoomIndex(2, 2, 0);
      protected static final int GRIDROOM_LEFTWING_CONNECT_INDEX = getRoomIndex(0, 1, 0);
      protected static final int GRIDROOM_RIGHTWING_CONNECT_INDEX = getRoomIndex(4, 1, 0);
      protected static final int LEFTWING_INDEX = 1001;
      protected static final int RIGHTWING_INDEX = 1002;
      protected static final int PENTHOUSE_INDEX = 1003;
      protected OceanMonumentPieces.RoomDefinition roomDefinition;

      protected static int getRoomIndex(int var0, int var1, int var2) {
         return â˜ƒ * 25 + â˜ƒ * 5 + â˜ƒ;
      }

      public OceanMonumentPiece(StructurePieceType var1, Direction var2, int var3, BoundingBox var4) {
         super(â˜ƒ, â˜ƒ, â˜ƒ);
         this.setOrientation(â˜ƒ);
      }

      protected OceanMonumentPiece(StructurePieceType var1, int var2, Direction var3, OceanMonumentPieces.RoomDefinition var4, int var5, int var6, int var7) {
         super(â˜ƒ, â˜ƒ, makeBoundingBox(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
         this.setOrientation(â˜ƒ);
         this.roomDefinition = â˜ƒ;
      }

      private static BoundingBox makeBoundingBox(Direction var0, OceanMonumentPieces.RoomDefinition var1, int var2, int var3, int var4) {
         int â˜ƒ = â˜ƒ.index;
         int â˜ƒx = â˜ƒ % 5;
         int â˜ƒxx = â˜ƒ / 5 % 5;
         int â˜ƒxxx = â˜ƒ / 25;
         BoundingBox â˜ƒxxxx = makeBoundingBox(0, 0, 0, â˜ƒ, â˜ƒ * 8, â˜ƒ * 4, â˜ƒ * 8);
         switch(â˜ƒ) {
            case NORTH:
               â˜ƒxxxx.move(â˜ƒx * 8, â˜ƒxxx * 4, -(â˜ƒxx + â˜ƒ) * 8 + 1);
               break;
            case SOUTH:
               â˜ƒxxxx.move(â˜ƒx * 8, â˜ƒxxx * 4, â˜ƒxx * 8);
               break;
            case WEST:
               â˜ƒxxxx.move(-(â˜ƒxx + â˜ƒ) * 8 + 1, â˜ƒxxx * 4, â˜ƒx * 8);
               break;
            case EAST:
            default:
               â˜ƒxxxx.move(â˜ƒxx * 8, â˜ƒxxx * 4, â˜ƒx * 8);
         }

         return â˜ƒxxxx;
      }

      public OceanMonumentPiece(StructurePieceType var1, CompoundTag var2) {
         super(â˜ƒ, â˜ƒ);
      }

      @Override
      protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
      }

      protected void generateWaterBox(WorldGenLevel var1, BoundingBox var2, int var3, int var4, int var5, int var6, int var7, int var8) {
         for(int â˜ƒ = â˜ƒ; â˜ƒ <= â˜ƒ; ++â˜ƒ) {
            for(int â˜ƒx = â˜ƒ; â˜ƒx <= â˜ƒ; ++â˜ƒx) {
               for(int â˜ƒxx = â˜ƒ; â˜ƒxx <= â˜ƒ; ++â˜ƒxx) {
                  BlockState â˜ƒxxx = this.getBlock(â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒxx, â˜ƒ);
                  if (!FILL_KEEP.contains(â˜ƒxxx.getBlock())) {
                     if (this.getWorldY(â˜ƒ) >= â˜ƒ.getSeaLevel() && â˜ƒxxx != FILL_BLOCK) {
                        this.placeBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), â˜ƒx, â˜ƒ, â˜ƒxx, â˜ƒ);
                     } else {
                        this.placeBlock(â˜ƒ, FILL_BLOCK, â˜ƒx, â˜ƒ, â˜ƒxx, â˜ƒ);
                     }
                  }
               }
            }
         }
      }

      protected void generateDefaultFloor(WorldGenLevel var1, BoundingBox var2, int var3, int var4, boolean var5) {
         if (â˜ƒ) {
            this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ + 0, 0, â˜ƒ + 0, â˜ƒ + 2, 0, â˜ƒ + 8 - 1, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ + 5, 0, â˜ƒ + 0, â˜ƒ + 8 - 1, 0, â˜ƒ + 8 - 1, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ + 3, 0, â˜ƒ + 0, â˜ƒ + 4, 0, â˜ƒ + 2, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ + 3, 0, â˜ƒ + 5, â˜ƒ + 4, 0, â˜ƒ + 8 - 1, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ + 3, 0, â˜ƒ + 2, â˜ƒ + 4, 0, â˜ƒ + 2, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ + 3, 0, â˜ƒ + 5, â˜ƒ + 4, 0, â˜ƒ + 5, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ + 2, 0, â˜ƒ + 3, â˜ƒ + 2, 0, â˜ƒ + 4, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ + 5, 0, â˜ƒ + 3, â˜ƒ + 5, 0, â˜ƒ + 4, BASE_LIGHT, BASE_LIGHT, false);
         } else {
            this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ + 0, 0, â˜ƒ + 0, â˜ƒ + 8 - 1, 0, â˜ƒ + 8 - 1, BASE_GRAY, BASE_GRAY, false);
         }
      }

      protected void generateBoxOnFillOnly(WorldGenLevel var1, BoundingBox var2, int var3, int var4, int var5, int var6, int var7, int var8, BlockState var9) {
         for(int â˜ƒ = â˜ƒ; â˜ƒ <= â˜ƒ; ++â˜ƒ) {
            for(int â˜ƒx = â˜ƒ; â˜ƒx <= â˜ƒ; ++â˜ƒx) {
               for(int â˜ƒxx = â˜ƒ; â˜ƒxx <= â˜ƒ; ++â˜ƒxx) {
                  if (this.getBlock(â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒxx, â˜ƒ) == FILL_BLOCK) {
                     this.placeBlock(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒxx, â˜ƒ);
                  }
               }
            }
         }
      }

      protected boolean chunkIntersects(BoundingBox var1, int var2, int var3, int var4, int var5) {
         int â˜ƒ = this.getWorldX(â˜ƒ, â˜ƒ);
         int â˜ƒx = this.getWorldZ(â˜ƒ, â˜ƒ);
         int â˜ƒxx = this.getWorldX(â˜ƒ, â˜ƒ);
         int â˜ƒxxx = this.getWorldZ(â˜ƒ, â˜ƒ);
         return â˜ƒ.intersects(Math.min(â˜ƒ, â˜ƒxx), Math.min(â˜ƒx, â˜ƒxxx), Math.max(â˜ƒ, â˜ƒxx), Math.max(â˜ƒx, â˜ƒxxx));
      }

      protected boolean spawnElder(WorldGenLevel var1, BoundingBox var2, int var3, int var4, int var5) {
         BlockPos â˜ƒ = this.getWorldPos(â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒ.isInside(â˜ƒ)) {
            ElderGuardian â˜ƒx = EntityType.ELDER_GUARDIAN.create(â˜ƒ.getLevel());
            â˜ƒx.heal(â˜ƒx.getMaxHealth());
            â˜ƒx.moveTo((double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY(), (double)â˜ƒ.getZ() + 0.5, 0.0F, 0.0F);
            â˜ƒx.finalizeSpawn(â˜ƒ, â˜ƒ.getCurrentDifficultyAt(â˜ƒx.blockPosition()), MobSpawnType.STRUCTURE, null, null);
            â˜ƒ.addFreshEntityWithPassengers(â˜ƒx);
            return true;
         } else {
            return false;
         }
      }
   }

   public static class OceanMonumentSimpleRoom extends OceanMonumentPieces.OceanMonumentPiece {
      private int mainDesign;

      public OceanMonumentSimpleRoom(Direction var1, OceanMonumentPieces.RoomDefinition var2, Random var3) {
         super(StructurePieceType.OCEAN_MONUMENT_SIMPLE_ROOM, 1, â˜ƒ, â˜ƒ, 1, 1, 1);
         this.mainDesign = â˜ƒ.nextInt(3);
      }

      public OceanMonumentSimpleRoom(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.OCEAN_MONUMENT_SIMPLE_ROOM, â˜ƒ);
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         if (this.roomDefinition.index / 25 > 0) {
            this.generateDefaultFloor(â˜ƒ, â˜ƒ, 0, 0, this.roomDefinition.hasOpening[Direction.DOWN.get3DDataValue()]);
         }

         if (this.roomDefinition.connections[Direction.UP.get3DDataValue()] == null) {
            this.generateBoxOnFillOnly(â˜ƒ, â˜ƒ, 1, 4, 1, 6, 4, 6, BASE_GRAY);
         }

         boolean â˜ƒ = this.mainDesign != 0
            && â˜ƒ.nextBoolean()
            && !this.roomDefinition.hasOpening[Direction.DOWN.get3DDataValue()]
            && !this.roomDefinition.hasOpening[Direction.UP.get3DDataValue()]
            && this.roomDefinition.countOpenings() > 1;
         if (this.mainDesign == 0) {
            this.generateBox(â˜ƒ, â˜ƒ, 0, 1, 0, 2, 1, 2, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 0, 3, 0, 2, 3, 2, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 0, 0, 2, 2, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, 1, 2, 0, 2, 2, 0, BASE_GRAY, BASE_GRAY, false);
            this.placeBlock(â˜ƒ, LAMP_BLOCK, 1, 2, 1, â˜ƒ);
            this.generateBox(â˜ƒ, â˜ƒ, 5, 1, 0, 7, 1, 2, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 5, 3, 0, 7, 3, 2, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 7, 2, 0, 7, 2, 2, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, 5, 2, 0, 6, 2, 0, BASE_GRAY, BASE_GRAY, false);
            this.placeBlock(â˜ƒ, LAMP_BLOCK, 6, 2, 1, â˜ƒ);
            this.generateBox(â˜ƒ, â˜ƒ, 0, 1, 5, 2, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 0, 3, 5, 2, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 5, 0, 2, 7, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, 1, 2, 7, 2, 2, 7, BASE_GRAY, BASE_GRAY, false);
            this.placeBlock(â˜ƒ, LAMP_BLOCK, 1, 2, 6, â˜ƒ);
            this.generateBox(â˜ƒ, â˜ƒ, 5, 1, 5, 7, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 5, 3, 5, 7, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 7, 2, 5, 7, 2, 7, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, 5, 2, 7, 6, 2, 7, BASE_GRAY, BASE_GRAY, false);
            this.placeBlock(â˜ƒ, LAMP_BLOCK, 6, 2, 6, â˜ƒ);
            if (this.roomDefinition.hasOpening[Direction.SOUTH.get3DDataValue()]) {
               this.generateBox(â˜ƒ, â˜ƒ, 3, 3, 0, 4, 3, 0, BASE_LIGHT, BASE_LIGHT, false);
            } else {
               this.generateBox(â˜ƒ, â˜ƒ, 3, 3, 0, 4, 3, 1, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, 3, 2, 0, 4, 2, 0, BASE_GRAY, BASE_GRAY, false);
               this.generateBox(â˜ƒ, â˜ƒ, 3, 1, 0, 4, 1, 1, BASE_LIGHT, BASE_LIGHT, false);
            }

            if (this.roomDefinition.hasOpening[Direction.NORTH.get3DDataValue()]) {
               this.generateBox(â˜ƒ, â˜ƒ, 3, 3, 7, 4, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
            } else {
               this.generateBox(â˜ƒ, â˜ƒ, 3, 3, 6, 4, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, 3, 2, 7, 4, 2, 7, BASE_GRAY, BASE_GRAY, false);
               this.generateBox(â˜ƒ, â˜ƒ, 3, 1, 6, 4, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
            }

            if (this.roomDefinition.hasOpening[Direction.WEST.get3DDataValue()]) {
               this.generateBox(â˜ƒ, â˜ƒ, 0, 3, 3, 0, 3, 4, BASE_LIGHT, BASE_LIGHT, false);
            } else {
               this.generateBox(â˜ƒ, â˜ƒ, 0, 3, 3, 1, 3, 4, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 3, 0, 2, 4, BASE_GRAY, BASE_GRAY, false);
               this.generateBox(â˜ƒ, â˜ƒ, 0, 1, 3, 1, 1, 4, BASE_LIGHT, BASE_LIGHT, false);
            }

            if (this.roomDefinition.hasOpening[Direction.EAST.get3DDataValue()]) {
               this.generateBox(â˜ƒ, â˜ƒ, 7, 3, 3, 7, 3, 4, BASE_LIGHT, BASE_LIGHT, false);
            } else {
               this.generateBox(â˜ƒ, â˜ƒ, 6, 3, 3, 7, 3, 4, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, 7, 2, 3, 7, 2, 4, BASE_GRAY, BASE_GRAY, false);
               this.generateBox(â˜ƒ, â˜ƒ, 6, 1, 3, 7, 1, 4, BASE_LIGHT, BASE_LIGHT, false);
            }
         } else if (this.mainDesign == 1) {
            this.generateBox(â˜ƒ, â˜ƒ, 2, 1, 2, 2, 3, 2, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 2, 1, 5, 2, 3, 5, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 5, 1, 5, 5, 3, 5, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 5, 1, 2, 5, 3, 2, BASE_LIGHT, BASE_LIGHT, false);
            this.placeBlock(â˜ƒ, LAMP_BLOCK, 2, 2, 2, â˜ƒ);
            this.placeBlock(â˜ƒ, LAMP_BLOCK, 2, 2, 5, â˜ƒ);
            this.placeBlock(â˜ƒ, LAMP_BLOCK, 5, 2, 5, â˜ƒ);
            this.placeBlock(â˜ƒ, LAMP_BLOCK, 5, 2, 2, â˜ƒ);
            this.generateBox(â˜ƒ, â˜ƒ, 0, 1, 0, 1, 3, 0, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 0, 1, 1, 0, 3, 1, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 0, 1, 7, 1, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 0, 1, 6, 0, 3, 6, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 6, 1, 7, 7, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 7, 1, 6, 7, 3, 6, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 6, 1, 0, 7, 3, 0, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 7, 1, 1, 7, 3, 1, BASE_LIGHT, BASE_LIGHT, false);
            this.placeBlock(â˜ƒ, BASE_GRAY, 1, 2, 0, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_GRAY, 0, 2, 1, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_GRAY, 1, 2, 7, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_GRAY, 0, 2, 6, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_GRAY, 6, 2, 7, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_GRAY, 7, 2, 6, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_GRAY, 6, 2, 0, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_GRAY, 7, 2, 1, â˜ƒ);
            if (!this.roomDefinition.hasOpening[Direction.SOUTH.get3DDataValue()]) {
               this.generateBox(â˜ƒ, â˜ƒ, 1, 3, 0, 6, 3, 0, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, 1, 2, 0, 6, 2, 0, BASE_GRAY, BASE_GRAY, false);
               this.generateBox(â˜ƒ, â˜ƒ, 1, 1, 0, 6, 1, 0, BASE_LIGHT, BASE_LIGHT, false);
            }

            if (!this.roomDefinition.hasOpening[Direction.NORTH.get3DDataValue()]) {
               this.generateBox(â˜ƒ, â˜ƒ, 1, 3, 7, 6, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, 1, 2, 7, 6, 2, 7, BASE_GRAY, BASE_GRAY, false);
               this.generateBox(â˜ƒ, â˜ƒ, 1, 1, 7, 6, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
            }

            if (!this.roomDefinition.hasOpening[Direction.WEST.get3DDataValue()]) {
               this.generateBox(â˜ƒ, â˜ƒ, 0, 3, 1, 0, 3, 6, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 1, 0, 2, 6, BASE_GRAY, BASE_GRAY, false);
               this.generateBox(â˜ƒ, â˜ƒ, 0, 1, 1, 0, 1, 6, BASE_LIGHT, BASE_LIGHT, false);
            }

            if (!this.roomDefinition.hasOpening[Direction.EAST.get3DDataValue()]) {
               this.generateBox(â˜ƒ, â˜ƒ, 7, 3, 1, 7, 3, 6, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, 7, 2, 1, 7, 2, 6, BASE_GRAY, BASE_GRAY, false);
               this.generateBox(â˜ƒ, â˜ƒ, 7, 1, 1, 7, 1, 6, BASE_LIGHT, BASE_LIGHT, false);
            }
         } else if (this.mainDesign == 2) {
            this.generateBox(â˜ƒ, â˜ƒ, 0, 1, 0, 0, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 7, 1, 0, 7, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 1, 1, 0, 6, 1, 0, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 1, 1, 7, 6, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 0, 0, 2, 7, BASE_BLACK, BASE_BLACK, false);
            this.generateBox(â˜ƒ, â˜ƒ, 7, 2, 0, 7, 2, 7, BASE_BLACK, BASE_BLACK, false);
            this.generateBox(â˜ƒ, â˜ƒ, 1, 2, 0, 6, 2, 0, BASE_BLACK, BASE_BLACK, false);
            this.generateBox(â˜ƒ, â˜ƒ, 1, 2, 7, 6, 2, 7, BASE_BLACK, BASE_BLACK, false);
            this.generateBox(â˜ƒ, â˜ƒ, 0, 3, 0, 0, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 7, 3, 0, 7, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 1, 3, 0, 6, 3, 0, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 1, 3, 7, 6, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 0, 1, 3, 0, 2, 4, BASE_BLACK, BASE_BLACK, false);
            this.generateBox(â˜ƒ, â˜ƒ, 7, 1, 3, 7, 2, 4, BASE_BLACK, BASE_BLACK, false);
            this.generateBox(â˜ƒ, â˜ƒ, 3, 1, 0, 4, 2, 0, BASE_BLACK, BASE_BLACK, false);
            this.generateBox(â˜ƒ, â˜ƒ, 3, 1, 7, 4, 2, 7, BASE_BLACK, BASE_BLACK, false);
            if (this.roomDefinition.hasOpening[Direction.SOUTH.get3DDataValue()]) {
               this.generateWaterBox(â˜ƒ, â˜ƒ, 3, 1, 0, 4, 2, 0);
            }

            if (this.roomDefinition.hasOpening[Direction.NORTH.get3DDataValue()]) {
               this.generateWaterBox(â˜ƒ, â˜ƒ, 3, 1, 7, 4, 2, 7);
            }

            if (this.roomDefinition.hasOpening[Direction.WEST.get3DDataValue()]) {
               this.generateWaterBox(â˜ƒ, â˜ƒ, 0, 1, 3, 0, 2, 4);
            }

            if (this.roomDefinition.hasOpening[Direction.EAST.get3DDataValue()]) {
               this.generateWaterBox(â˜ƒ, â˜ƒ, 7, 1, 3, 7, 2, 4);
            }
         }

         if (â˜ƒ) {
            this.generateBox(â˜ƒ, â˜ƒ, 3, 1, 3, 4, 1, 4, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 3, 2, 3, 4, 2, 4, BASE_GRAY, BASE_GRAY, false);
            this.generateBox(â˜ƒ, â˜ƒ, 3, 3, 3, 4, 3, 4, BASE_LIGHT, BASE_LIGHT, false);
         }

         return true;
      }
   }

   public static class OceanMonumentSimpleTopRoom extends OceanMonumentPieces.OceanMonumentPiece {
      public OceanMonumentSimpleTopRoom(Direction var1, OceanMonumentPieces.RoomDefinition var2) {
         super(StructurePieceType.OCEAN_MONUMENT_SIMPLE_TOP_ROOM, 1, â˜ƒ, â˜ƒ, 1, 1, 1);
      }

      public OceanMonumentSimpleTopRoom(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.OCEAN_MONUMENT_SIMPLE_TOP_ROOM, â˜ƒ);
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         if (this.roomDefinition.index / 25 > 0) {
            this.generateDefaultFloor(â˜ƒ, â˜ƒ, 0, 0, this.roomDefinition.hasOpening[Direction.DOWN.get3DDataValue()]);
         }

         if (this.roomDefinition.connections[Direction.UP.get3DDataValue()] == null) {
            this.generateBoxOnFillOnly(â˜ƒ, â˜ƒ, 1, 4, 1, 6, 4, 6, BASE_GRAY);
         }

         for(int â˜ƒ = 1; â˜ƒ <= 6; ++â˜ƒ) {
            for(int â˜ƒx = 1; â˜ƒx <= 6; ++â˜ƒx) {
               if (â˜ƒ.nextInt(3) != 0) {
                  int â˜ƒxx = 2 + (â˜ƒ.nextInt(4) == 0 ? 0 : 1);
                  BlockState â˜ƒxxx = Blocks.WET_SPONGE.defaultBlockState();
                  this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒx, â˜ƒ, 3, â˜ƒx, â˜ƒxxx, â˜ƒxxx, false);
               }
            }
         }

         this.generateBox(â˜ƒ, â˜ƒ, 0, 1, 0, 0, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 7, 1, 0, 7, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 1, 0, 6, 1, 0, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 1, 7, 6, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 2, 0, 0, 2, 7, BASE_BLACK, BASE_BLACK, false);
         this.generateBox(â˜ƒ, â˜ƒ, 7, 2, 0, 7, 2, 7, BASE_BLACK, BASE_BLACK, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 2, 0, 6, 2, 0, BASE_BLACK, BASE_BLACK, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 2, 7, 6, 2, 7, BASE_BLACK, BASE_BLACK, false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 3, 0, 0, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 7, 3, 0, 7, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 3, 0, 6, 3, 0, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 3, 7, 6, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 1, 3, 0, 2, 4, BASE_BLACK, BASE_BLACK, false);
         this.generateBox(â˜ƒ, â˜ƒ, 7, 1, 3, 7, 2, 4, BASE_BLACK, BASE_BLACK, false);
         this.generateBox(â˜ƒ, â˜ƒ, 3, 1, 0, 4, 2, 0, BASE_BLACK, BASE_BLACK, false);
         this.generateBox(â˜ƒ, â˜ƒ, 3, 1, 7, 4, 2, 7, BASE_BLACK, BASE_BLACK, false);
         if (this.roomDefinition.hasOpening[Direction.SOUTH.get3DDataValue()]) {
            this.generateWaterBox(â˜ƒ, â˜ƒ, 3, 1, 0, 4, 2, 0);
         }

         return true;
      }
   }

   public static class OceanMonumentWingRoom extends OceanMonumentPieces.OceanMonumentPiece {
      private int mainDesign;

      public OceanMonumentWingRoom(Direction var1, BoundingBox var2, int var3) {
         super(StructurePieceType.OCEAN_MONUMENT_WING_ROOM, â˜ƒ, 1, â˜ƒ);
         this.mainDesign = â˜ƒ & 1;
      }

      public OceanMonumentWingRoom(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.OCEAN_MONUMENT_WING_ROOM, â˜ƒ);
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         if (this.mainDesign == 0) {
            for(int â˜ƒ = 0; â˜ƒ < 4; ++â˜ƒ) {
               this.generateBox(â˜ƒ, â˜ƒ, 10 - â˜ƒ, 3 - â˜ƒ, 20 - â˜ƒ, 12 + â˜ƒ, 3 - â˜ƒ, 20, BASE_LIGHT, BASE_LIGHT, false);
            }

            this.generateBox(â˜ƒ, â˜ƒ, 7, 0, 6, 15, 0, 16, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 6, 0, 6, 6, 3, 20, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 16, 0, 6, 16, 3, 20, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 7, 1, 7, 7, 1, 20, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 15, 1, 7, 15, 1, 20, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 7, 1, 6, 9, 3, 6, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 13, 1, 6, 15, 3, 6, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 8, 1, 7, 9, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 13, 1, 7, 14, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 9, 0, 5, 13, 0, 5, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 10, 0, 7, 12, 0, 7, BASE_BLACK, BASE_BLACK, false);
            this.generateBox(â˜ƒ, â˜ƒ, 8, 0, 10, 8, 0, 12, BASE_BLACK, BASE_BLACK, false);
            this.generateBox(â˜ƒ, â˜ƒ, 14, 0, 10, 14, 0, 12, BASE_BLACK, BASE_BLACK, false);

            for(int â˜ƒ = 18; â˜ƒ >= 7; â˜ƒ -= 3) {
               this.placeBlock(â˜ƒ, LAMP_BLOCK, 6, 3, â˜ƒ, â˜ƒ);
               this.placeBlock(â˜ƒ, LAMP_BLOCK, 16, 3, â˜ƒ, â˜ƒ);
            }

            this.placeBlock(â˜ƒ, LAMP_BLOCK, 10, 0, 10, â˜ƒ);
            this.placeBlock(â˜ƒ, LAMP_BLOCK, 12, 0, 10, â˜ƒ);
            this.placeBlock(â˜ƒ, LAMP_BLOCK, 10, 0, 12, â˜ƒ);
            this.placeBlock(â˜ƒ, LAMP_BLOCK, 12, 0, 12, â˜ƒ);
            this.placeBlock(â˜ƒ, LAMP_BLOCK, 8, 3, 6, â˜ƒ);
            this.placeBlock(â˜ƒ, LAMP_BLOCK, 14, 3, 6, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_LIGHT, 4, 2, 4, â˜ƒ);
            this.placeBlock(â˜ƒ, LAMP_BLOCK, 4, 1, 4, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_LIGHT, 4, 0, 4, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_LIGHT, 18, 2, 4, â˜ƒ);
            this.placeBlock(â˜ƒ, LAMP_BLOCK, 18, 1, 4, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_LIGHT, 18, 0, 4, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_LIGHT, 4, 2, 18, â˜ƒ);
            this.placeBlock(â˜ƒ, LAMP_BLOCK, 4, 1, 18, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_LIGHT, 4, 0, 18, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_LIGHT, 18, 2, 18, â˜ƒ);
            this.placeBlock(â˜ƒ, LAMP_BLOCK, 18, 1, 18, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_LIGHT, 18, 0, 18, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_LIGHT, 9, 7, 20, â˜ƒ);
            this.placeBlock(â˜ƒ, BASE_LIGHT, 13, 7, 20, â˜ƒ);
            this.generateBox(â˜ƒ, â˜ƒ, 6, 0, 21, 7, 4, 21, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 15, 0, 21, 16, 4, 21, BASE_LIGHT, BASE_LIGHT, false);
            this.spawnElder(â˜ƒ, â˜ƒ, 11, 2, 16);
         } else if (this.mainDesign == 1) {
            this.generateBox(â˜ƒ, â˜ƒ, 9, 3, 18, 13, 3, 20, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 9, 0, 18, 9, 2, 18, BASE_LIGHT, BASE_LIGHT, false);
            this.generateBox(â˜ƒ, â˜ƒ, 13, 0, 18, 13, 2, 18, BASE_LIGHT, BASE_LIGHT, false);
            int â˜ƒ = 9;
            int â˜ƒx = 20;
            int â˜ƒxx = 5;

            for(int â˜ƒxxx = 0; â˜ƒxxx < 2; ++â˜ƒxxx) {
               this.placeBlock(â˜ƒ, BASE_LIGHT, â˜ƒ, 6, 20, â˜ƒ);
               this.placeBlock(â˜ƒ, LAMP_BLOCK, â˜ƒ, 5, 20, â˜ƒ);
               this.placeBlock(â˜ƒ, BASE_LIGHT, â˜ƒ, 4, 20, â˜ƒ);
               â˜ƒ = 13;
            }

            this.generateBox(â˜ƒ, â˜ƒ, 7, 3, 7, 15, 3, 14, BASE_LIGHT, BASE_LIGHT, false);
            int var14 = 10;

            for(int â˜ƒxxx = 0; â˜ƒxxx < 2; ++â˜ƒxxx) {
               this.generateBox(â˜ƒ, â˜ƒ, var14, 0, 10, var14, 6, 10, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, var14, 0, 12, var14, 6, 12, BASE_LIGHT, BASE_LIGHT, false);
               this.placeBlock(â˜ƒ, LAMP_BLOCK, var14, 0, 10, â˜ƒ);
               this.placeBlock(â˜ƒ, LAMP_BLOCK, var14, 0, 12, â˜ƒ);
               this.placeBlock(â˜ƒ, LAMP_BLOCK, var14, 4, 10, â˜ƒ);
               this.placeBlock(â˜ƒ, LAMP_BLOCK, var14, 4, 12, â˜ƒ);
               var14 = 12;
            }

            var14 = 8;

            for(int â˜ƒxxx = 0; â˜ƒxxx < 2; ++â˜ƒxxx) {
               this.generateBox(â˜ƒ, â˜ƒ, var14, 0, 7, var14, 2, 7, BASE_LIGHT, BASE_LIGHT, false);
               this.generateBox(â˜ƒ, â˜ƒ, var14, 0, 14, var14, 2, 14, BASE_LIGHT, BASE_LIGHT, false);
               var14 = 14;
            }

            this.generateBox(â˜ƒ, â˜ƒ, 8, 3, 8, 8, 3, 13, BASE_BLACK, BASE_BLACK, false);
            this.generateBox(â˜ƒ, â˜ƒ, 14, 3, 8, 14, 3, 13, BASE_BLACK, BASE_BLACK, false);
            this.spawnElder(â˜ƒ, â˜ƒ, 11, 5, 13);
         }

         return true;
      }
   }

   static class RoomDefinition {
      final int index;
      final OceanMonumentPieces.RoomDefinition[] connections = new OceanMonumentPieces.RoomDefinition[6];
      final boolean[] hasOpening = new boolean[6];
      boolean claimed;
      boolean isSource;
      private int scanIndex;

      public RoomDefinition(int var1) {
         this.index = â˜ƒ;
      }

      public void setConnection(Direction var1, OceanMonumentPieces.RoomDefinition var2) {
         this.connections[â˜ƒ.get3DDataValue()] = â˜ƒ;
         â˜ƒ.connections[â˜ƒ.getOpposite().get3DDataValue()] = this;
      }

      public void updateOpenings() {
         for(int â˜ƒ = 0; â˜ƒ < 6; ++â˜ƒ) {
            this.hasOpening[â˜ƒ] = this.connections[â˜ƒ] != null;
         }
      }

      public boolean findSource(int var1) {
         if (this.isSource) {
            return true;
         } else {
            this.scanIndex = â˜ƒ;

            for(int â˜ƒ = 0; â˜ƒ < 6; ++â˜ƒ) {
               if (this.connections[â˜ƒ] != null && this.hasOpening[â˜ƒ] && this.connections[â˜ƒ].scanIndex != â˜ƒ && this.connections[â˜ƒ].findSource(â˜ƒ)) {
                  return true;
               }
            }

            return false;
         }
      }

      public boolean isSpecial() {
         return this.index >= 75;
      }

      public int countOpenings() {
         int â˜ƒ = 0;

         for(int â˜ƒx = 0; â˜ƒx < 6; ++â˜ƒx) {
            if (this.hasOpening[â˜ƒx]) {
               ++â˜ƒ;
            }
         }

         return â˜ƒ;
      }
   }
}
