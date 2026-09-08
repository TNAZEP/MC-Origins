package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class WoodlandMansionPieces {
   public static void generateMansion(StructureManager var0, BlockPos var1, Rotation var2, List<WoodlandMansionPieces.WoodlandMansionPiece> var3, Random var4) {
      WoodlandMansionPieces.MansionGrid â˜ƒ = new WoodlandMansionPieces.MansionGrid(â˜ƒ);
      WoodlandMansionPieces.MansionPiecePlacer â˜ƒx = new WoodlandMansionPieces.MansionPiecePlacer(â˜ƒ, â˜ƒ);
      â˜ƒx.createMansion(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void main(String[] var0) {
      Random â˜ƒ = new Random();
      long â˜ƒx = â˜ƒ.nextLong();
      System.out.println("Seed: " + â˜ƒx);
      â˜ƒ.setSeed(â˜ƒx);
      WoodlandMansionPieces.MansionGrid â˜ƒxx = new WoodlandMansionPieces.MansionGrid(â˜ƒ);
      â˜ƒxx.print();
   }

   static class FirstFloorRoomCollection extends WoodlandMansionPieces.FloorRoomCollection {
      @Override
      public String get1x1(Random var1) {
         return "1x1_a" + (â˜ƒ.nextInt(5) + 1);
      }

      @Override
      public String get1x1Secret(Random var1) {
         return "1x1_as" + (â˜ƒ.nextInt(4) + 1);
      }

      @Override
      public String get1x2SideEntrance(Random var1, boolean var2) {
         return "1x2_a" + (â˜ƒ.nextInt(9) + 1);
      }

      @Override
      public String get1x2FrontEntrance(Random var1, boolean var2) {
         return "1x2_b" + (â˜ƒ.nextInt(5) + 1);
      }

      @Override
      public String get1x2Secret(Random var1) {
         return "1x2_s" + (â˜ƒ.nextInt(2) + 1);
      }

      @Override
      public String get2x2(Random var1) {
         return "2x2_a" + (â˜ƒ.nextInt(4) + 1);
      }

      @Override
      public String get2x2Secret(Random var1) {
         return "2x2_s1";
      }
   }

   abstract static class FloorRoomCollection {
      public abstract String get1x1(Random var1);

      public abstract String get1x1Secret(Random var1);

      public abstract String get1x2SideEntrance(Random var1, boolean var2);

      public abstract String get1x2FrontEntrance(Random var1, boolean var2);

      public abstract String get1x2Secret(Random var1);

      public abstract String get2x2(Random var1);

      public abstract String get2x2Secret(Random var1);
   }

   static class MansionGrid {
      private static final int DEFAULT_SIZE = 11;
      private static final int CLEAR = 0;
      private static final int CORRIDOR = 1;
      private static final int ROOM = 2;
      private static final int START_ROOM = 3;
      private static final int TEST_ROOM = 4;
      private static final int BLOCKED = 5;
      private static final int ROOM_1x1 = 65536;
      private static final int ROOM_1x2 = 131072;
      private static final int ROOM_2x2 = 262144;
      private static final int ROOM_ORIGIN_FLAG = 1048576;
      private static final int ROOM_DOOR_FLAG = 2097152;
      private static final int ROOM_STAIRS_FLAG = 4194304;
      private static final int ROOM_CORRIDOR_FLAG = 8388608;
      private static final int ROOM_TYPE_MASK = 983040;
      private static final int ROOM_ID_MASK = 65535;
      private final Random random;
      final WoodlandMansionPieces.SimpleGrid baseGrid;
      final WoodlandMansionPieces.SimpleGrid thirdFloorGrid;
      final WoodlandMansionPieces.SimpleGrid[] floorRooms;
      final int entranceX;
      final int entranceY;

      public MansionGrid(Random var1) {
         this.random = â˜ƒ;
         int â˜ƒ = 11;
         this.entranceX = 7;
         this.entranceY = 4;
         this.baseGrid = new WoodlandMansionPieces.SimpleGrid(11, 11, 5);
         this.baseGrid.set(this.entranceX, this.entranceY, this.entranceX + 1, this.entranceY + 1, 3);
         this.baseGrid.set(this.entranceX - 1, this.entranceY, this.entranceX - 1, this.entranceY + 1, 2);
         this.baseGrid.set(this.entranceX + 2, this.entranceY - 2, this.entranceX + 3, this.entranceY + 3, 5);
         this.baseGrid.set(this.entranceX + 1, this.entranceY - 2, this.entranceX + 1, this.entranceY - 1, 1);
         this.baseGrid.set(this.entranceX + 1, this.entranceY + 2, this.entranceX + 1, this.entranceY + 3, 1);
         this.baseGrid.set(this.entranceX - 1, this.entranceY - 1, 1);
         this.baseGrid.set(this.entranceX - 1, this.entranceY + 2, 1);
         this.baseGrid.set(0, 0, 11, 1, 5);
         this.baseGrid.set(0, 9, 11, 11, 5);
         this.recursiveCorridor(this.baseGrid, this.entranceX, this.entranceY - 2, Direction.WEST, 6);
         this.recursiveCorridor(this.baseGrid, this.entranceX, this.entranceY + 3, Direction.WEST, 6);
         this.recursiveCorridor(this.baseGrid, this.entranceX - 2, this.entranceY - 1, Direction.WEST, 3);
         this.recursiveCorridor(this.baseGrid, this.entranceX - 2, this.entranceY + 2, Direction.WEST, 3);

         while(this.cleanEdges(this.baseGrid)) {
         }

         this.floorRooms = new WoodlandMansionPieces.SimpleGrid[3];
         this.floorRooms[0] = new WoodlandMansionPieces.SimpleGrid(11, 11, 5);
         this.floorRooms[1] = new WoodlandMansionPieces.SimpleGrid(11, 11, 5);
         this.floorRooms[2] = new WoodlandMansionPieces.SimpleGrid(11, 11, 5);
         this.identifyRooms(this.baseGrid, this.floorRooms[0]);
         this.identifyRooms(this.baseGrid, this.floorRooms[1]);
         this.floorRooms[0].set(this.entranceX + 1, this.entranceY, this.entranceX + 1, this.entranceY + 1, 8388608);
         this.floorRooms[1].set(this.entranceX + 1, this.entranceY, this.entranceX + 1, this.entranceY + 1, 8388608);
         this.thirdFloorGrid = new WoodlandMansionPieces.SimpleGrid(this.baseGrid.width, this.baseGrid.height, 5);
         this.setupThirdFloor();
         this.identifyRooms(this.thirdFloorGrid, this.floorRooms[2]);
      }

      public static boolean isHouse(WoodlandMansionPieces.SimpleGrid var0, int var1, int var2) {
         int â˜ƒ = â˜ƒ.get(â˜ƒ, â˜ƒ);
         return â˜ƒ == 1 || â˜ƒ == 2 || â˜ƒ == 3 || â˜ƒ == 4;
      }

      public boolean isRoomId(WoodlandMansionPieces.SimpleGrid var1, int var2, int var3, int var4, int var5) {
         return (this.floorRooms[â˜ƒ].get(â˜ƒ, â˜ƒ) & 65535) == â˜ƒ;
      }

      @Nullable
      public Direction get1x2RoomDirection(WoodlandMansionPieces.SimpleGrid var1, int var2, int var3, int var4, int var5) {
         for(Direction â˜ƒ : Direction.Plane.HORIZONTAL) {
            if (this.isRoomId(â˜ƒ, â˜ƒ + â˜ƒ.getStepX(), â˜ƒ + â˜ƒ.getStepZ(), â˜ƒ, â˜ƒ)) {
               return â˜ƒ;
            }
         }

         return null;
      }

      private void recursiveCorridor(WoodlandMansionPieces.SimpleGrid var1, int var2, int var3, Direction var4, int var5) {
         if (â˜ƒ > 0) {
            â˜ƒ.set(â˜ƒ, â˜ƒ, 1);
            â˜ƒ.setif(â˜ƒ + â˜ƒ.getStepX(), â˜ƒ + â˜ƒ.getStepZ(), 0, 1);

            for(int â˜ƒ = 0; â˜ƒ < 8; ++â˜ƒ) {
               Direction â˜ƒx = Direction.from2DDataValue(this.random.nextInt(4));
               if (â˜ƒx != â˜ƒ.getOpposite() && (â˜ƒx != Direction.EAST || !this.random.nextBoolean())) {
                  int â˜ƒxx = â˜ƒ + â˜ƒ.getStepX();
                  int â˜ƒxxx = â˜ƒ + â˜ƒ.getStepZ();
                  if (â˜ƒ.get(â˜ƒxx + â˜ƒx.getStepX(), â˜ƒxxx + â˜ƒx.getStepZ()) == 0
                     && â˜ƒ.get(â˜ƒxx + â˜ƒx.getStepX() * 2, â˜ƒxxx + â˜ƒx.getStepZ() * 2) == 0) {
                     this.recursiveCorridor(â˜ƒ, â˜ƒ + â˜ƒ.getStepX() + â˜ƒx.getStepX(), â˜ƒ + â˜ƒ.getStepZ() + â˜ƒx.getStepZ(), â˜ƒx, â˜ƒ - 1);
                     break;
                  }
               }
            }

            Direction â˜ƒ = â˜ƒ.getClockWise();
            Direction â˜ƒx = â˜ƒ.getCounterClockWise();
            â˜ƒ.setif(â˜ƒ + â˜ƒ.getStepX(), â˜ƒ + â˜ƒ.getStepZ(), 0, 2);
            â˜ƒ.setif(â˜ƒ + â˜ƒx.getStepX(), â˜ƒ + â˜ƒx.getStepZ(), 0, 2);
            â˜ƒ.setif(â˜ƒ + â˜ƒ.getStepX() + â˜ƒ.getStepX(), â˜ƒ + â˜ƒ.getStepZ() + â˜ƒ.getStepZ(), 0, 2);
            â˜ƒ.setif(â˜ƒ + â˜ƒ.getStepX() + â˜ƒx.getStepX(), â˜ƒ + â˜ƒ.getStepZ() + â˜ƒx.getStepZ(), 0, 2);
            â˜ƒ.setif(â˜ƒ + â˜ƒ.getStepX() * 2, â˜ƒ + â˜ƒ.getStepZ() * 2, 0, 2);
            â˜ƒ.setif(â˜ƒ + â˜ƒ.getStepX() * 2, â˜ƒ + â˜ƒ.getStepZ() * 2, 0, 2);
            â˜ƒ.setif(â˜ƒ + â˜ƒx.getStepX() * 2, â˜ƒ + â˜ƒx.getStepZ() * 2, 0, 2);
         }
      }

      private boolean cleanEdges(WoodlandMansionPieces.SimpleGrid var1) {
         boolean â˜ƒ = false;

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.height; ++â˜ƒx) {
            for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.width; ++â˜ƒxx) {
               if (â˜ƒ.get(â˜ƒxx, â˜ƒx) == 0) {
                  int â˜ƒxxx = 0;
                  â˜ƒxxx += isHouse(â˜ƒ, â˜ƒxx + 1, â˜ƒx) ? 1 : 0;
                  â˜ƒxxx += isHouse(â˜ƒ, â˜ƒxx - 1, â˜ƒx) ? 1 : 0;
                  â˜ƒxxx += isHouse(â˜ƒ, â˜ƒxx, â˜ƒx + 1) ? 1 : 0;
                  â˜ƒxxx += isHouse(â˜ƒ, â˜ƒxx, â˜ƒx - 1) ? 1 : 0;
                  if (â˜ƒxxx >= 3) {
                     â˜ƒ.set(â˜ƒxx, â˜ƒx, 2);
                     â˜ƒ = true;
                  } else if (â˜ƒxxx == 2) {
                     int â˜ƒxxx = 0;
                     â˜ƒxxx += isHouse(â˜ƒ, â˜ƒxx + 1, â˜ƒx + 1) ? 1 : 0;
                     â˜ƒxxx += isHouse(â˜ƒ, â˜ƒxx - 1, â˜ƒx + 1) ? 1 : 0;
                     â˜ƒxxx += isHouse(â˜ƒ, â˜ƒxx + 1, â˜ƒx - 1) ? 1 : 0;
                     â˜ƒxxx += isHouse(â˜ƒ, â˜ƒxx - 1, â˜ƒx - 1) ? 1 : 0;
                     if (â˜ƒxxx <= 1) {
                        â˜ƒ.set(â˜ƒxx, â˜ƒx, 2);
                        â˜ƒ = true;
                     }
                  }
               }
            }
         }

         return â˜ƒ;
      }

      private void setupThirdFloor() {
         List<Tuple<Integer, Integer>> â˜ƒ = Lists.<Tuple<Integer, Integer>>newArrayList();
         WoodlandMansionPieces.SimpleGrid â˜ƒx = this.floorRooms[1];

         for(int â˜ƒxx = 0; â˜ƒxx < this.thirdFloorGrid.height; ++â˜ƒxx) {
            for(int â˜ƒxxx = 0; â˜ƒxxx < this.thirdFloorGrid.width; ++â˜ƒxxx) {
               int â˜ƒxxxx = â˜ƒx.get(â˜ƒxxx, â˜ƒxx);
               int â˜ƒxxxxx = â˜ƒxxxx & 983040;
               if (â˜ƒxxxxx == 131072 && (â˜ƒxxxx & 2097152) == 2097152) {
                  â˜ƒ.add(new Tuple(â˜ƒxxx, â˜ƒxx));
               }
            }
         }

         if (â˜ƒ.isEmpty()) {
            this.thirdFloorGrid.set(0, 0, this.thirdFloorGrid.width, this.thirdFloorGrid.height, 5);
         } else {
            Tuple<Integer, Integer> â˜ƒxx = (Tuple)â˜ƒ.get(this.random.nextInt(â˜ƒ.size()));
            int â˜ƒxxx = â˜ƒx.get(â˜ƒxx.getA(), â˜ƒxx.getB());
            â˜ƒx.set(â˜ƒxx.getA(), â˜ƒxx.getB(), â˜ƒxxx | 4194304);
            Direction â˜ƒxxxx = this.get1x2RoomDirection(this.baseGrid, â˜ƒxx.getA(), â˜ƒxx.getB(), 1, â˜ƒxxx & 65535);
            int â˜ƒxxxxx = â˜ƒxx.getA() + â˜ƒxxxx.getStepX();
            int â˜ƒxxxxxx = â˜ƒxx.getB() + â˜ƒxxxx.getStepZ();

            for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < this.thirdFloorGrid.height; ++â˜ƒxxxxxxx) {
               for(int â˜ƒxxxxxxxx = 0; â˜ƒxxxxxxxx < this.thirdFloorGrid.width; ++â˜ƒxxxxxxxx) {
                  if (!isHouse(this.baseGrid, â˜ƒxxxxxxxx, â˜ƒxxxxxxx)) {
                     this.thirdFloorGrid.set(â˜ƒxxxxxxxx, â˜ƒxxxxxxx, 5);
                  } else if (â˜ƒxxxxxxxx == â˜ƒxx.getA() && â˜ƒxxxxxxx == â˜ƒxx.getB()) {
                     this.thirdFloorGrid.set(â˜ƒxxxxxxxx, â˜ƒxxxxxxx, 3);
                  } else if (â˜ƒxxxxxxxx == â˜ƒxxxxx && â˜ƒxxxxxxx == â˜ƒxxxxxx) {
                     this.thirdFloorGrid.set(â˜ƒxxxxxxxx, â˜ƒxxxxxxx, 3);
                     this.floorRooms[2].set(â˜ƒxxxxxxxx, â˜ƒxxxxxxx, 8388608);
                  }
               }
            }

            List<Direction> â˜ƒxxxxxxx = Lists.<Direction>newArrayList();

            for(Direction â˜ƒxxxxxxxx : Direction.Plane.HORIZONTAL) {
               if (this.thirdFloorGrid.get(â˜ƒxxxxx + â˜ƒxxxxxxxx.getStepX(), â˜ƒxxxxxx + â˜ƒxxxxxxxx.getStepZ()) == 0) {
                  â˜ƒxxxxxxx.add(â˜ƒxxxxxxxx);
               }
            }

            if (â˜ƒxxxxxxx.isEmpty()) {
               this.thirdFloorGrid.set(0, 0, this.thirdFloorGrid.width, this.thirdFloorGrid.height, 5);
               â˜ƒx.set(â˜ƒxx.getA(), â˜ƒxx.getB(), â˜ƒxxx);
            } else {
               Direction â˜ƒxxxxxxxx = (Direction)â˜ƒxxxxxxx.get(this.random.nextInt(â˜ƒxxxxxxx.size()));
               this.recursiveCorridor(this.thirdFloorGrid, â˜ƒxxxxx + â˜ƒxxxxxxxx.getStepX(), â˜ƒxxxxxx + â˜ƒxxxxxxxx.getStepZ(), â˜ƒxxxxxxxx, 4);

               while(this.cleanEdges(this.thirdFloorGrid)) {
               }
            }
         }
      }

      private void identifyRooms(WoodlandMansionPieces.SimpleGrid var1, WoodlandMansionPieces.SimpleGrid var2) {
         List<Tuple<Integer, Integer>> â˜ƒ = Lists.<Tuple<Integer, Integer>>newArrayList();

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.height; ++â˜ƒx) {
            for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.width; ++â˜ƒxx) {
               if (â˜ƒ.get(â˜ƒxx, â˜ƒx) == 2) {
                  â˜ƒ.add(new Tuple(â˜ƒxx, â˜ƒx));
               }
            }
         }

         Collections.shuffle(â˜ƒ, this.random);
         int â˜ƒx = 10;

         for(Tuple<Integer, Integer> â˜ƒxx : â˜ƒ) {
            int â˜ƒxxx = â˜ƒxx.getA();
            int â˜ƒxxxx = â˜ƒxx.getB();
            if (â˜ƒ.get(â˜ƒxxx, â˜ƒxxxx) == 0) {
               int â˜ƒxxxxx = â˜ƒxxx;
               int â˜ƒxxxxxx = â˜ƒxxx;
               int â˜ƒxxxxxxx = â˜ƒxxxx;
               int â˜ƒxxxxxxxx = â˜ƒxxxx;
               int â˜ƒxxxxxxxxx = 65536;
               if (â˜ƒ.get(â˜ƒxxx + 1, â˜ƒxxxx) == 0
                  && â˜ƒ.get(â˜ƒxxx, â˜ƒxxxx + 1) == 0
                  && â˜ƒ.get(â˜ƒxxx + 1, â˜ƒxxxx + 1) == 0
                  && â˜ƒ.get(â˜ƒxxx + 1, â˜ƒxxxx) == 2
                  && â˜ƒ.get(â˜ƒxxx, â˜ƒxxxx + 1) == 2
                  && â˜ƒ.get(â˜ƒxxx + 1, â˜ƒxxxx + 1) == 2) {
                  â˜ƒxxxxxx = â˜ƒxxx + 1;
                  â˜ƒxxxxxxxx = â˜ƒxxxx + 1;
                  â˜ƒxxxxxxxxx = 262144;
               } else if (â˜ƒ.get(â˜ƒxxx - 1, â˜ƒxxxx) == 0
                  && â˜ƒ.get(â˜ƒxxx, â˜ƒxxxx + 1) == 0
                  && â˜ƒ.get(â˜ƒxxx - 1, â˜ƒxxxx + 1) == 0
                  && â˜ƒ.get(â˜ƒxxx - 1, â˜ƒxxxx) == 2
                  && â˜ƒ.get(â˜ƒxxx, â˜ƒxxxx + 1) == 2
                  && â˜ƒ.get(â˜ƒxxx - 1, â˜ƒxxxx + 1) == 2) {
                  â˜ƒxxxxx = â˜ƒxxx - 1;
                  â˜ƒxxxxxxxx = â˜ƒxxxx + 1;
                  â˜ƒxxxxxxxxx = 262144;
               } else if (â˜ƒ.get(â˜ƒxxx - 1, â˜ƒxxxx) == 0
                  && â˜ƒ.get(â˜ƒxxx, â˜ƒxxxx - 1) == 0
                  && â˜ƒ.get(â˜ƒxxx - 1, â˜ƒxxxx - 1) == 0
                  && â˜ƒ.get(â˜ƒxxx - 1, â˜ƒxxxx) == 2
                  && â˜ƒ.get(â˜ƒxxx, â˜ƒxxxx - 1) == 2
                  && â˜ƒ.get(â˜ƒxxx - 1, â˜ƒxxxx - 1) == 2) {
                  â˜ƒxxxxx = â˜ƒxxx - 1;
                  â˜ƒxxxxxxx = â˜ƒxxxx - 1;
                  â˜ƒxxxxxxxxx = 262144;
               } else if (â˜ƒ.get(â˜ƒxxx + 1, â˜ƒxxxx) == 0 && â˜ƒ.get(â˜ƒxxx + 1, â˜ƒxxxx) == 2) {
                  â˜ƒxxxxxx = â˜ƒxxx + 1;
                  â˜ƒxxxxxxxxx = 131072;
               } else if (â˜ƒ.get(â˜ƒxxx, â˜ƒxxxx + 1) == 0 && â˜ƒ.get(â˜ƒxxx, â˜ƒxxxx + 1) == 2) {
                  â˜ƒxxxxxxxx = â˜ƒxxxx + 1;
                  â˜ƒxxxxxxxxx = 131072;
               } else if (â˜ƒ.get(â˜ƒxxx - 1, â˜ƒxxxx) == 0 && â˜ƒ.get(â˜ƒxxx - 1, â˜ƒxxxx) == 2) {
                  â˜ƒxxxxx = â˜ƒxxx - 1;
                  â˜ƒxxxxxxxxx = 131072;
               } else if (â˜ƒ.get(â˜ƒxxx, â˜ƒxxxx - 1) == 0 && â˜ƒ.get(â˜ƒxxx, â˜ƒxxxx - 1) == 2) {
                  â˜ƒxxxxxxx = â˜ƒxxxx - 1;
                  â˜ƒxxxxxxxxx = 131072;
               }

               int â˜ƒxxxxx = this.random.nextBoolean() ? â˜ƒxxxxx : â˜ƒxxxxxx;
               int â˜ƒxxxxxx = this.random.nextBoolean() ? â˜ƒxxxxxxx : â˜ƒxxxxxxxx;
               int â˜ƒxxxxxxx = 2097152;
               if (!â˜ƒ.edgesTo(â˜ƒxxxxx, â˜ƒxxxxxx, 1)) {
                  â˜ƒxxxxx = â˜ƒxxxxx == â˜ƒxxxxx ? â˜ƒxxxxxx : â˜ƒxxxxx;
                  â˜ƒxxxxxx = â˜ƒxxxxxx == â˜ƒxxxxxxx ? â˜ƒxxxxxxxx : â˜ƒxxxxxxx;
                  if (!â˜ƒ.edgesTo(â˜ƒxxxxx, â˜ƒxxxxxx, 1)) {
                     â˜ƒxxxxxx = â˜ƒxxxxxx == â˜ƒxxxxxxx ? â˜ƒxxxxxxxx : â˜ƒxxxxxxx;
                     if (!â˜ƒ.edgesTo(â˜ƒxxxxx, â˜ƒxxxxxx, 1)) {
                        â˜ƒxxxxx = â˜ƒxxxxx == â˜ƒxxxxx ? â˜ƒxxxxxx : â˜ƒxxxxx;
                        â˜ƒxxxxxx = â˜ƒxxxxxx == â˜ƒxxxxxxx ? â˜ƒxxxxxxxx : â˜ƒxxxxxxx;
                        if (!â˜ƒ.edgesTo(â˜ƒxxxxx, â˜ƒxxxxxx, 1)) {
                           â˜ƒxxxxxxx = 0;
                           â˜ƒxxxxx = â˜ƒxxxxx;
                           â˜ƒxxxxxx = â˜ƒxxxxxxx;
                        }
                     }
                  }
               }

               for(int â˜ƒxxxxx = â˜ƒxxxxxxx; â˜ƒxxxxx <= â˜ƒxxxxxxxx; ++â˜ƒxxxxx) {
                  for(int â˜ƒxxxxxx = â˜ƒxxxxx; â˜ƒxxxxxx <= â˜ƒxxxxxx; ++â˜ƒxxxxxx) {
                     if (â˜ƒxxxxxx == â˜ƒxxxxx && â˜ƒxxxxx == â˜ƒxxxxxx) {
                        â˜ƒ.set(â˜ƒxxxxxx, â˜ƒxxxxx, 1048576 | â˜ƒxxxxxxx | â˜ƒxxxxxxxxx | â˜ƒx);
                     } else {
                        â˜ƒ.set(â˜ƒxxxxxx, â˜ƒxxxxx, â˜ƒxxxxxxxxx | â˜ƒx);
                     }
                  }
               }

               ++â˜ƒx;
            }
         }
      }

      public void print() {
         for(int â˜ƒ = 0; â˜ƒ < 2; ++â˜ƒ) {
            WoodlandMansionPieces.SimpleGrid â˜ƒx = â˜ƒ == 0 ? this.baseGrid : this.thirdFloorGrid;

            for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx.height; ++â˜ƒxx) {
               for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒx.width; ++â˜ƒxxx) {
                  int â˜ƒxxxx = â˜ƒx.get(â˜ƒxxx, â˜ƒxx);
                  if (â˜ƒxxxx == 1) {
                     System.out.print("+");
                  } else if (â˜ƒxxxx == 4) {
                     System.out.print("x");
                  } else if (â˜ƒxxxx == 2) {
                     System.out.print("X");
                  } else if (â˜ƒxxxx == 3) {
                     System.out.print("O");
                  } else if (â˜ƒxxxx == 5) {
                     System.out.print("#");
                  } else {
                     System.out.print(" ");
                  }
               }

               System.out.println("");
            }

            System.out.println("");
         }
      }
   }

   static class MansionPiecePlacer {
      private final StructureManager structureManager;
      private final Random random;
      private int startX;
      private int startY;

      public MansionPiecePlacer(StructureManager var1, Random var2) {
         this.structureManager = â˜ƒ;
         this.random = â˜ƒ;
      }

      public void createMansion(BlockPos var1, Rotation var2, List<WoodlandMansionPieces.WoodlandMansionPiece> var3, WoodlandMansionPieces.MansionGrid var4) {
         WoodlandMansionPieces.PlacementData â˜ƒ = new WoodlandMansionPieces.PlacementData();
         â˜ƒ.position = â˜ƒ;
         â˜ƒ.rotation = â˜ƒ;
         â˜ƒ.wallType = "wall_flat";
         WoodlandMansionPieces.PlacementData â˜ƒx = new WoodlandMansionPieces.PlacementData();
         this.entrance(â˜ƒ, â˜ƒ);
         â˜ƒx.position = â˜ƒ.position.above(8);
         â˜ƒx.rotation = â˜ƒ.rotation;
         â˜ƒx.wallType = "wall_window";
         if (!â˜ƒ.isEmpty()) {
         }

         WoodlandMansionPieces.SimpleGrid â˜ƒ = â˜ƒ.baseGrid;
         WoodlandMansionPieces.SimpleGrid â˜ƒx = â˜ƒ.thirdFloorGrid;
         this.startX = â˜ƒ.entranceX + 1;
         this.startY = â˜ƒ.entranceY + 1;
         int â˜ƒxx = â˜ƒ.entranceX + 1;
         int â˜ƒxxx = â˜ƒ.entranceY;
         this.traverseOuterWalls(â˜ƒ, â˜ƒ, â˜ƒ, Direction.SOUTH, this.startX, this.startY, â˜ƒxx, â˜ƒxxx);
         this.traverseOuterWalls(â˜ƒ, â˜ƒx, â˜ƒ, Direction.SOUTH, this.startX, this.startY, â˜ƒxx, â˜ƒxxx);
         WoodlandMansionPieces.PlacementData â˜ƒxxxx = new WoodlandMansionPieces.PlacementData();
         â˜ƒxxxx.position = â˜ƒ.position.above(19);
         â˜ƒxxxx.rotation = â˜ƒ.rotation;
         â˜ƒxxxx.wallType = "wall_window";
         boolean â˜ƒxxxxx = false;

         for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < â˜ƒx.height && !â˜ƒxxxxx; ++â˜ƒxxxxxx) {
            for(int â˜ƒxxxxxxx = â˜ƒx.width - 1; â˜ƒxxxxxxx >= 0 && !â˜ƒxxxxx; --â˜ƒxxxxxxx) {
               if (WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒx, â˜ƒxxxxxxx, â˜ƒxxxxxx)) {
                  â˜ƒxxxx.position = â˜ƒxxxx.position.relative(â˜ƒ.rotate(Direction.SOUTH), 8 + (â˜ƒxxxxxx - this.startY) * 8);
                  â˜ƒxxxx.position = â˜ƒxxxx.position.relative(â˜ƒ.rotate(Direction.EAST), (â˜ƒxxxxxxx - this.startX) * 8);
                  this.traverseWallPiece(â˜ƒ, â˜ƒxxxx);
                  this.traverseOuterWalls(â˜ƒ, â˜ƒxxxx, â˜ƒx, Direction.SOUTH, â˜ƒxxxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxx);
                  â˜ƒxxxxx = true;
               }
            }
         }

         this.createRoof(â˜ƒ, â˜ƒ.above(16), â˜ƒ, â˜ƒ, â˜ƒx);
         this.createRoof(â˜ƒ, â˜ƒ.above(27), â˜ƒ, â˜ƒx, null);
         if (!â˜ƒ.isEmpty()) {
         }

         WoodlandMansionPieces.FloorRoomCollection[] â˜ƒxxxxxx = new WoodlandMansionPieces.FloorRoomCollection[]{
            new WoodlandMansionPieces.FirstFloorRoomCollection(),
            new WoodlandMansionPieces.SecondFloorRoomCollection(),
            new WoodlandMansionPieces.ThirdFloorRoomCollection()
         };

         for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < 3; ++â˜ƒxxxxxxx) {
            BlockPos â˜ƒxxxxxxxx = â˜ƒ.above(8 * â˜ƒxxxxxxx + (â˜ƒxxxxxxx == 2 ? 3 : 0));
            WoodlandMansionPieces.SimpleGrid â˜ƒxxxxxxxxx = â˜ƒ.floorRooms[â˜ƒxxxxxxx];
            WoodlandMansionPieces.SimpleGrid â˜ƒxxxxxxxxxx = â˜ƒxxxxxxx == 2 ? â˜ƒx : â˜ƒ;
            String â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxx == 0 ? "carpet_south_1" : "carpet_south_2";
            String â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxx == 0 ? "carpet_west_1" : "carpet_west_2";

            for(int â˜ƒxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxx < â˜ƒxxxxxxxxxx.height; ++â˜ƒxxxxxxxxxxxxx) {
               for(int â˜ƒxxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxxx < â˜ƒxxxxxxxxxx.width; ++â˜ƒxxxxxxxxxxxxxx) {
                  if (â˜ƒxxxxxxxxxx.get(â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxx) == 1) {
                     BlockPos â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxxxxx.relative(â˜ƒ.rotate(Direction.SOUTH), 8 + (â˜ƒxxxxxxxxxxxxx - this.startY) * 8);
                     â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxx.relative(â˜ƒ.rotate(Direction.EAST), (â˜ƒxxxxxxxxxxxxxx - this.startX) * 8);
                     â˜ƒ.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureManager, "corridor_floor", â˜ƒxxxxxxxxxxxxxxx, â˜ƒ));
                     if (â˜ƒxxxxxxxxxx.get(â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxx - 1) == 1
                        || (â˜ƒxxxxxxxxx.get(â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxx - 1) & 8388608) == 8388608) {
                        â˜ƒ.add(
                           new WoodlandMansionPieces.WoodlandMansionPiece(
                              this.structureManager, "carpet_north", â˜ƒxxxxxxxxxxxxxxx.relative(â˜ƒ.rotate(Direction.EAST), 1).above(), â˜ƒ
                           )
                        );
                     }

                     if (â˜ƒxxxxxxxxxx.get(â˜ƒxxxxxxxxxxxxxx + 1, â˜ƒxxxxxxxxxxxxx) == 1
                        || (â˜ƒxxxxxxxxx.get(â˜ƒxxxxxxxxxxxxxx + 1, â˜ƒxxxxxxxxxxxxx) & 8388608) == 8388608) {
                        â˜ƒ.add(
                           new WoodlandMansionPieces.WoodlandMansionPiece(
                              this.structureManager,
                              "carpet_east",
                              â˜ƒxxxxxxxxxxxxxxx.relative(â˜ƒ.rotate(Direction.SOUTH), 1).relative(â˜ƒ.rotate(Direction.EAST), 5).above(),
                              â˜ƒ
                           )
                        );
                     }

                     if (â˜ƒxxxxxxxxxx.get(â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxx + 1) == 1
                        || (â˜ƒxxxxxxxxx.get(â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxx + 1) & 8388608) == 8388608) {
                        â˜ƒ.add(
                           new WoodlandMansionPieces.WoodlandMansionPiece(
                              this.structureManager,
                              â˜ƒxxxxxxxxxxx,
                              â˜ƒxxxxxxxxxxxxxxx.relative(â˜ƒ.rotate(Direction.SOUTH), 5).relative(â˜ƒ.rotate(Direction.WEST), 1),
                              â˜ƒ
                           )
                        );
                     }

                     if (â˜ƒxxxxxxxxxx.get(â˜ƒxxxxxxxxxxxxxx - 1, â˜ƒxxxxxxxxxxxxx) == 1
                        || (â˜ƒxxxxxxxxx.get(â˜ƒxxxxxxxxxxxxxx - 1, â˜ƒxxxxxxxxxxxxx) & 8388608) == 8388608) {
                        â˜ƒ.add(
                           new WoodlandMansionPieces.WoodlandMansionPiece(
                              this.structureManager,
                              â˜ƒxxxxxxxxxxxx,
                              â˜ƒxxxxxxxxxxxxxxx.relative(â˜ƒ.rotate(Direction.WEST), 1).relative(â˜ƒ.rotate(Direction.NORTH), 1),
                              â˜ƒ
                           )
                        );
                     }
                  }
               }
            }

            String â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxx == 0 ? "indoors_wall_1" : "indoors_wall_2";
            String â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxx == 0 ? "indoors_door_1" : "indoors_door_2";
            List<Direction> â˜ƒxxxxxxxxxxxxxxx = Lists.<Direction>newArrayList();

            for(int â˜ƒxxxxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxxxxx < â˜ƒxxxxxxxxxx.height; ++â˜ƒxxxxxxxxxxxxxxxx) {
               for(int â˜ƒxxxxxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxxxxxx < â˜ƒxxxxxxxxxx.width; ++â˜ƒxxxxxxxxxxxxxxxxx) {
                  boolean â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxx == 2 && â˜ƒxxxxxxxxxx.get(â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx) == 3;
                  if (â˜ƒxxxxxxxxxx.get(â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx) == 2 || â˜ƒxxxxxxxxxxxxxxxxxx) {
                     int â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxx.get(â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx);
                     int â˜ƒxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxx & 983040;
                     int â˜ƒxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxx & 65535;
                     â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxx && (â˜ƒxxxxxxxxxxxxxxxxxxx & 8388608) == 8388608;
                     â˜ƒxxxxxxxxxxxxxxx.clear();
                     if ((â˜ƒxxxxxxxxxxxxxxxxxxx & 2097152) == 2097152) {
                        for(Direction â˜ƒxxxxxxxxxxxxxxxxxxxxxx : Direction.Plane.HORIZONTAL) {
                           if (â˜ƒxxxxxxxxxx.get(
                                 â˜ƒxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxxxxxx.getStepX(), â˜ƒxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxxxxxx.getStepZ()
                              )
                              == 1) {
                              â˜ƒxxxxxxxxxxxxxxx.add(â˜ƒxxxxxxxxxxxxxxxxxxxxxx);
                           }
                        }
                     }

                     Direction â˜ƒxxxxxxxxxxxxxxxxxxx = null;
                     if (!â˜ƒxxxxxxxxxxxxxxx.isEmpty()) {
                        â˜ƒxxxxxxxxxxxxxxxxxxx = (Direction)â˜ƒxxxxxxxxxxxxxxx.get(this.random.nextInt(â˜ƒxxxxxxxxxxxxxxx.size()));
                     } else if ((â˜ƒxxxxxxxxxxxxxxxxxxx & 1048576) == 1048576) {
                        â˜ƒxxxxxxxxxxxxxxxxxxx = Direction.UP;
                     }

                     BlockPos â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxx.relative(â˜ƒ.rotate(Direction.SOUTH), 8 + (â˜ƒxxxxxxxxxxxxxxxx - this.startY) * 8);
                     â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxx.relative(â˜ƒ.rotate(Direction.EAST), -1 + (â˜ƒxxxxxxxxxxxxxxxxx - this.startX) * 8);
                     if (WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx - 1, â˜ƒxxxxxxxxxxxxxxxx)
                        && !â˜ƒ.isRoomId(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx - 1, â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxx)) {
                        â˜ƒ.add(
                           new WoodlandMansionPieces.WoodlandMansionPiece(
                              this.structureManager,
                              â˜ƒxxxxxxxxxxxxxxxxxxx == Direction.WEST ? â˜ƒxxxxxxxxxxxxxx : â˜ƒxxxxxxxxxxxxx,
                              â˜ƒxxxxxxxxxxxxxxxxxxx,
                              â˜ƒ
                           )
                        );
                     }

                     if (â˜ƒxxxxxxxxxx.get(â˜ƒxxxxxxxxxxxxxxxxx + 1, â˜ƒxxxxxxxxxxxxxxxx) == 1 && !â˜ƒxxxxxxxxxxxxxxxxxx) {
                        BlockPos â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxx.relative(â˜ƒ.rotate(Direction.EAST), 8);
                        â˜ƒ.add(
                           new WoodlandMansionPieces.WoodlandMansionPiece(
                              this.structureManager,
                              â˜ƒxxxxxxxxxxxxxxxxxxx == Direction.EAST ? â˜ƒxxxxxxxxxxxxxx : â˜ƒxxxxxxxxxxxxx,
                              â˜ƒxxxxxxxxxxxxxxxxxxx,
                              â˜ƒ
                           )
                        );
                     }

                     if (WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx + 1)
                        && !â˜ƒ.isRoomId(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx + 1, â˜ƒxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxx)) {
                        BlockPos â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxx.relative(â˜ƒ.rotate(Direction.SOUTH), 7);
                        â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxx.relative(â˜ƒ.rotate(Direction.EAST), 7);
                        â˜ƒ.add(
                           new WoodlandMansionPieces.WoodlandMansionPiece(
                              this.structureManager,
                              â˜ƒxxxxxxxxxxxxxxxxxxx == Direction.SOUTH ? â˜ƒxxxxxxxxxxxxxx : â˜ƒxxxxxxxxxxxxx,
                              â˜ƒxxxxxxxxxxxxxxxxxxx,
                              â˜ƒ.getRotated(Rotation.CLOCKWISE_90)
                           )
                        );
                     }

                     if (â˜ƒxxxxxxxxxx.get(â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx - 1) == 1 && !â˜ƒxxxxxxxxxxxxxxxxxx) {
                        BlockPos â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxx.relative(â˜ƒ.rotate(Direction.NORTH), 1);
                        â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxx.relative(â˜ƒ.rotate(Direction.EAST), 7);
                        â˜ƒ.add(
                           new WoodlandMansionPieces.WoodlandMansionPiece(
                              this.structureManager,
                              â˜ƒxxxxxxxxxxxxxxxxxxx == Direction.NORTH ? â˜ƒxxxxxxxxxxxxxx : â˜ƒxxxxxxxxxxxxx,
                              â˜ƒxxxxxxxxxxxxxxxxxxx,
                              â˜ƒ.getRotated(Rotation.CLOCKWISE_90)
                           )
                        );
                     }

                     if (â˜ƒxxxxxxxxxxxxxxxxxxxx == 65536) {
                        this.addRoom1x1(â˜ƒ, â˜ƒxxxxxxxxxxxxxxxxxxx, â˜ƒ, â˜ƒxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxx[â˜ƒxxxxxxx]);
                     } else if (â˜ƒxxxxxxxxxxxxxxxxxxxx == 131072 && â˜ƒxxxxxxxxxxxxxxxxxxx != null) {
                        Direction â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒ.get1x2RoomDirection(
                           â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxx
                        );
                        boolean â˜ƒxxxxxxxxxxxxxxxxxxxx = (â˜ƒxxxxxxxxxxxxxxxxxxx & 4194304) == 4194304;
                        this.addRoom1x2(
                           â˜ƒ, â˜ƒxxxxxxxxxxxxxxxxxxx, â˜ƒ, â˜ƒxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxx[â˜ƒxxxxxxx], â˜ƒxxxxxxxxxxxxxxxxxxxx
                        );
                     } else if (â˜ƒxxxxxxxxxxxxxxxxxxxx == 262144 && â˜ƒxxxxxxxxxxxxxxxxxxx != null && â˜ƒxxxxxxxxxxxxxxxxxxx != Direction.UP) {
                        Direction â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxx.getClockWise();
                        if (!â˜ƒ.isRoomId(
                           â˜ƒxxxxxxxxxx,
                           â˜ƒxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxxx.getStepX(),
                           â˜ƒxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxxx.getStepZ(),
                           â˜ƒxxxxxxx,
                           â˜ƒxxxxxxxxxxxxxxxxxxxxx
                        )) {
                           â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxx.getOpposite();
                        }

                        this.addRoom2x2(â˜ƒ, â˜ƒxxxxxxxxxxxxxxxxxxx, â˜ƒ, â˜ƒxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxx[â˜ƒxxxxxxx]);
                     } else if (â˜ƒxxxxxxxxxxxxxxxxxxxx == 262144 && â˜ƒxxxxxxxxxxxxxxxxxxx == Direction.UP) {
                        this.addRoom2x2Secret(â˜ƒ, â˜ƒxxxxxxxxxxxxxxxxxxx, â˜ƒ, â˜ƒxxxxxx[â˜ƒxxxxxxx]);
                     }
                  }
               }
            }
         }
      }

      private void traverseOuterWalls(
         List<WoodlandMansionPieces.WoodlandMansionPiece> var1,
         WoodlandMansionPieces.PlacementData var2,
         WoodlandMansionPieces.SimpleGrid var3,
         Direction var4,
         int var5,
         int var6,
         int var7,
         int var8
      ) {
         int â˜ƒ = â˜ƒ;
         int â˜ƒx = â˜ƒ;
         Direction â˜ƒxx = â˜ƒ;

         do {
            if (!WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒ + â˜ƒ.getStepX(), â˜ƒx + â˜ƒ.getStepZ())) {
               this.traverseTurn(â˜ƒ, â˜ƒ);
               â˜ƒ = â˜ƒ.getClockWise();
               if (â˜ƒ != â˜ƒ || â˜ƒx != â˜ƒ || â˜ƒxx != â˜ƒ) {
                  this.traverseWallPiece(â˜ƒ, â˜ƒ);
               }
            } else if (WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒ + â˜ƒ.getStepX(), â˜ƒx + â˜ƒ.getStepZ())
               && WoodlandMansionPieces.MansionGrid.isHouse(
                  â˜ƒ, â˜ƒ + â˜ƒ.getStepX() + â˜ƒ.getCounterClockWise().getStepX(), â˜ƒx + â˜ƒ.getStepZ() + â˜ƒ.getCounterClockWise().getStepZ()
               )) {
               this.traverseInnerTurn(â˜ƒ, â˜ƒ);
               â˜ƒ += â˜ƒ.getStepX();
               â˜ƒx += â˜ƒ.getStepZ();
               â˜ƒ = â˜ƒ.getCounterClockWise();
            } else {
               â˜ƒ += â˜ƒ.getStepX();
               â˜ƒx += â˜ƒ.getStepZ();
               if (â˜ƒ != â˜ƒ || â˜ƒx != â˜ƒ || â˜ƒxx != â˜ƒ) {
                  this.traverseWallPiece(â˜ƒ, â˜ƒ);
               }
            }
         } while(â˜ƒ != â˜ƒ || â˜ƒx != â˜ƒ || â˜ƒxx != â˜ƒ);
      }

      private void createRoof(
         List<WoodlandMansionPieces.WoodlandMansionPiece> var1,
         BlockPos var2,
         Rotation var3,
         WoodlandMansionPieces.SimpleGrid var4,
         @Nullable WoodlandMansionPieces.SimpleGrid var5
      ) {
         for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.height; ++â˜ƒ) {
            for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.width; ++â˜ƒx) {
               BlockPos var8 = â˜ƒ.relative(â˜ƒ.rotate(Direction.SOUTH), 8 + (â˜ƒ - this.startY) * 8);
               var8 = var8.relative(â˜ƒ.rotate(Direction.EAST), (â˜ƒx - this.startX) * 8);
               boolean â˜ƒxx = â˜ƒ != null && WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx, â˜ƒ);
               if (WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx, â˜ƒ) && !â˜ƒxx) {
                  â˜ƒ.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureManager, "roof", var8.above(3), â˜ƒ));
                  if (!WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx + 1, â˜ƒ)) {
                     BlockPos â˜ƒxxx = var8.relative(â˜ƒ.rotate(Direction.EAST), 6);
                     â˜ƒ.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureManager, "roof_front", â˜ƒxxx, â˜ƒ));
                  }

                  if (!WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx - 1, â˜ƒ)) {
                     BlockPos â˜ƒxxx = var8.relative(â˜ƒ.rotate(Direction.EAST), 0);
                     â˜ƒxxx = â˜ƒxxx.relative(â˜ƒ.rotate(Direction.SOUTH), 7);
                     â˜ƒ.add(
                        new WoodlandMansionPieces.WoodlandMansionPiece(this.structureManager, "roof_front", â˜ƒxxx, â˜ƒ.getRotated(Rotation.CLOCKWISE_180))
                     );
                  }

                  if (!WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx, â˜ƒ - 1)) {
                     BlockPos â˜ƒxxx = var8.relative(â˜ƒ.rotate(Direction.WEST), 1);
                     â˜ƒ.add(
                        new WoodlandMansionPieces.WoodlandMansionPiece(
                           this.structureManager, "roof_front", â˜ƒxxx, â˜ƒ.getRotated(Rotation.COUNTERCLOCKWISE_90)
                        )
                     );
                  }

                  if (!WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx, â˜ƒ + 1)) {
                     BlockPos â˜ƒxxx = var8.relative(â˜ƒ.rotate(Direction.EAST), 6);
                     â˜ƒxxx = â˜ƒxxx.relative(â˜ƒ.rotate(Direction.SOUTH), 6);
                     â˜ƒ.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureManager, "roof_front", â˜ƒxxx, â˜ƒ.getRotated(Rotation.CLOCKWISE_90)));
                  }
               }
            }
         }

         if (â˜ƒ != null) {
            for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.height; ++â˜ƒ) {
               for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.width; ++â˜ƒx) {
                  BlockPos var17 = â˜ƒ.relative(â˜ƒ.rotate(Direction.SOUTH), 8 + (â˜ƒ - this.startY) * 8);
                  var17 = var17.relative(â˜ƒ.rotate(Direction.EAST), (â˜ƒx - this.startX) * 8);
                  boolean â˜ƒxx = WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx, â˜ƒ);
                  if (WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx, â˜ƒ) && â˜ƒxx) {
                     if (!WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx + 1, â˜ƒ)) {
                        BlockPos â˜ƒxxx = var17.relative(â˜ƒ.rotate(Direction.EAST), 7);
                        â˜ƒ.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureManager, "small_wall", â˜ƒxxx, â˜ƒ));
                     }

                     if (!WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx - 1, â˜ƒ)) {
                        BlockPos â˜ƒxxx = var17.relative(â˜ƒ.rotate(Direction.WEST), 1);
                        â˜ƒxxx = â˜ƒxxx.relative(â˜ƒ.rotate(Direction.SOUTH), 6);
                        â˜ƒ.add(
                           new WoodlandMansionPieces.WoodlandMansionPiece(this.structureManager, "small_wall", â˜ƒxxx, â˜ƒ.getRotated(Rotation.CLOCKWISE_180))
                        );
                     }

                     if (!WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx, â˜ƒ - 1)) {
                        BlockPos â˜ƒxxx = var17.relative(â˜ƒ.rotate(Direction.WEST), 0);
                        â˜ƒxxx = â˜ƒxxx.relative(â˜ƒ.rotate(Direction.NORTH), 1);
                        â˜ƒ.add(
                           new WoodlandMansionPieces.WoodlandMansionPiece(
                              this.structureManager, "small_wall", â˜ƒxxx, â˜ƒ.getRotated(Rotation.COUNTERCLOCKWISE_90)
                           )
                        );
                     }

                     if (!WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx, â˜ƒ + 1)) {
                        BlockPos â˜ƒxxx = var17.relative(â˜ƒ.rotate(Direction.EAST), 6);
                        â˜ƒxxx = â˜ƒxxx.relative(â˜ƒ.rotate(Direction.SOUTH), 7);
                        â˜ƒ.add(
                           new WoodlandMansionPieces.WoodlandMansionPiece(this.structureManager, "small_wall", â˜ƒxxx, â˜ƒ.getRotated(Rotation.CLOCKWISE_90))
                        );
                     }

                     if (!WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx + 1, â˜ƒ)) {
                        if (!WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx, â˜ƒ - 1)) {
                           BlockPos â˜ƒxxx = var17.relative(â˜ƒ.rotate(Direction.EAST), 7);
                           â˜ƒxxx = â˜ƒxxx.relative(â˜ƒ.rotate(Direction.NORTH), 2);
                           â˜ƒ.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureManager, "small_wall_corner", â˜ƒxxx, â˜ƒ));
                        }

                        if (!WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx, â˜ƒ + 1)) {
                           BlockPos â˜ƒxxx = var17.relative(â˜ƒ.rotate(Direction.EAST), 8);
                           â˜ƒxxx = â˜ƒxxx.relative(â˜ƒ.rotate(Direction.SOUTH), 7);
                           â˜ƒ.add(
                              new WoodlandMansionPieces.WoodlandMansionPiece(
                                 this.structureManager, "small_wall_corner", â˜ƒxxx, â˜ƒ.getRotated(Rotation.CLOCKWISE_90)
                              )
                           );
                        }
                     }

                     if (!WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx - 1, â˜ƒ)) {
                        if (!WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx, â˜ƒ - 1)) {
                           BlockPos â˜ƒxxx = var17.relative(â˜ƒ.rotate(Direction.WEST), 2);
                           â˜ƒxxx = â˜ƒxxx.relative(â˜ƒ.rotate(Direction.NORTH), 1);
                           â˜ƒ.add(
                              new WoodlandMansionPieces.WoodlandMansionPiece(
                                 this.structureManager, "small_wall_corner", â˜ƒxxx, â˜ƒ.getRotated(Rotation.COUNTERCLOCKWISE_90)
                              )
                           );
                        }

                        if (!WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx, â˜ƒ + 1)) {
                           BlockPos â˜ƒxxx = var17.relative(â˜ƒ.rotate(Direction.WEST), 1);
                           â˜ƒxxx = â˜ƒxxx.relative(â˜ƒ.rotate(Direction.SOUTH), 8);
                           â˜ƒ.add(
                              new WoodlandMansionPieces.WoodlandMansionPiece(
                                 this.structureManager, "small_wall_corner", â˜ƒxxx, â˜ƒ.getRotated(Rotation.CLOCKWISE_180)
                              )
                           );
                        }
                     }
                  }
               }
            }
         }

         for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.height; ++â˜ƒ) {
            for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.width; ++â˜ƒx) {
               BlockPos var19 = â˜ƒ.relative(â˜ƒ.rotate(Direction.SOUTH), 8 + (â˜ƒ - this.startY) * 8);
               var19 = var19.relative(â˜ƒ.rotate(Direction.EAST), (â˜ƒx - this.startX) * 8);
               boolean â˜ƒxx = â˜ƒ != null && WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx, â˜ƒ);
               if (WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx, â˜ƒ) && !â˜ƒxx) {
                  if (!WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx + 1, â˜ƒ)) {
                     BlockPos â˜ƒxxx = var19.relative(â˜ƒ.rotate(Direction.EAST), 6);
                     if (!WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx, â˜ƒ + 1)) {
                        BlockPos â˜ƒxxxx = â˜ƒxxx.relative(â˜ƒ.rotate(Direction.SOUTH), 6);
                        â˜ƒ.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureManager, "roof_corner", â˜ƒxxxx, â˜ƒ));
                     } else if (WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx + 1, â˜ƒ + 1)) {
                        BlockPos â˜ƒxxx = â˜ƒxxx.relative(â˜ƒ.rotate(Direction.SOUTH), 5);
                        â˜ƒ.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureManager, "roof_inner_corner", â˜ƒxxx, â˜ƒ));
                     }

                     if (!WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx, â˜ƒ - 1)) {
                        â˜ƒ.add(
                           new WoodlandMansionPieces.WoodlandMansionPiece(
                              this.structureManager, "roof_corner", â˜ƒxxx, â˜ƒ.getRotated(Rotation.COUNTERCLOCKWISE_90)
                           )
                        );
                     } else if (WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx + 1, â˜ƒ - 1)) {
                        BlockPos â˜ƒxxx = var19.relative(â˜ƒ.rotate(Direction.EAST), 9);
                        â˜ƒxxx = â˜ƒxxx.relative(â˜ƒ.rotate(Direction.NORTH), 2);
                        â˜ƒ.add(
                           new WoodlandMansionPieces.WoodlandMansionPiece(
                              this.structureManager, "roof_inner_corner", â˜ƒxxx, â˜ƒ.getRotated(Rotation.CLOCKWISE_90)
                           )
                        );
                     }
                  }

                  if (!WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx - 1, â˜ƒ)) {
                     BlockPos â˜ƒxxx = var19.relative(â˜ƒ.rotate(Direction.EAST), 0);
                     â˜ƒxxx = â˜ƒxxx.relative(â˜ƒ.rotate(Direction.SOUTH), 0);
                     if (!WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx, â˜ƒ + 1)) {
                        BlockPos â˜ƒxxxx = â˜ƒxxx.relative(â˜ƒ.rotate(Direction.SOUTH), 6);
                        â˜ƒ.add(
                           new WoodlandMansionPieces.WoodlandMansionPiece(this.structureManager, "roof_corner", â˜ƒxxxx, â˜ƒ.getRotated(Rotation.CLOCKWISE_90))
                        );
                     } else if (WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx - 1, â˜ƒ + 1)) {
                        BlockPos â˜ƒxxx = â˜ƒxxx.relative(â˜ƒ.rotate(Direction.SOUTH), 8);
                        â˜ƒxxx = â˜ƒxxx.relative(â˜ƒ.rotate(Direction.WEST), 3);
                        â˜ƒ.add(
                           new WoodlandMansionPieces.WoodlandMansionPiece(
                              this.structureManager, "roof_inner_corner", â˜ƒxxx, â˜ƒ.getRotated(Rotation.COUNTERCLOCKWISE_90)
                           )
                        );
                     }

                     if (!WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx, â˜ƒ - 1)) {
                        â˜ƒ.add(
                           new WoodlandMansionPieces.WoodlandMansionPiece(this.structureManager, "roof_corner", â˜ƒxxx, â˜ƒ.getRotated(Rotation.CLOCKWISE_180))
                        );
                     } else if (WoodlandMansionPieces.MansionGrid.isHouse(â˜ƒ, â˜ƒx - 1, â˜ƒ - 1)) {
                        BlockPos â˜ƒxxx = â˜ƒxxx.relative(â˜ƒ.rotate(Direction.SOUTH), 1);
                        â˜ƒ.add(
                           new WoodlandMansionPieces.WoodlandMansionPiece(
                              this.structureManager, "roof_inner_corner", â˜ƒxxx, â˜ƒ.getRotated(Rotation.CLOCKWISE_180)
                           )
                        );
                     }
                  }
               }
            }
         }
      }

      private void entrance(List<WoodlandMansionPieces.WoodlandMansionPiece> var1, WoodlandMansionPieces.PlacementData var2) {
         Direction â˜ƒ = â˜ƒ.rotation.rotate(Direction.WEST);
         â˜ƒ.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureManager, "entrance", â˜ƒ.position.relative(â˜ƒ, 9), â˜ƒ.rotation));
         â˜ƒ.position = â˜ƒ.position.relative(â˜ƒ.rotation.rotate(Direction.SOUTH), 16);
      }

      private void traverseWallPiece(List<WoodlandMansionPieces.WoodlandMansionPiece> var1, WoodlandMansionPieces.PlacementData var2) {
         â˜ƒ.add(
            new WoodlandMansionPieces.WoodlandMansionPiece(
               this.structureManager, â˜ƒ.wallType, â˜ƒ.position.relative(â˜ƒ.rotation.rotate(Direction.EAST), 7), â˜ƒ.rotation
            )
         );
         â˜ƒ.position = â˜ƒ.position.relative(â˜ƒ.rotation.rotate(Direction.SOUTH), 8);
      }

      private void traverseTurn(List<WoodlandMansionPieces.WoodlandMansionPiece> var1, WoodlandMansionPieces.PlacementData var2) {
         â˜ƒ.position = â˜ƒ.position.relative(â˜ƒ.rotation.rotate(Direction.SOUTH), -1);
         â˜ƒ.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureManager, "wall_corner", â˜ƒ.position, â˜ƒ.rotation));
         â˜ƒ.position = â˜ƒ.position.relative(â˜ƒ.rotation.rotate(Direction.SOUTH), -7);
         â˜ƒ.position = â˜ƒ.position.relative(â˜ƒ.rotation.rotate(Direction.WEST), -6);
         â˜ƒ.rotation = â˜ƒ.rotation.getRotated(Rotation.CLOCKWISE_90);
      }

      private void traverseInnerTurn(List<WoodlandMansionPieces.WoodlandMansionPiece> var1, WoodlandMansionPieces.PlacementData var2) {
         â˜ƒ.position = â˜ƒ.position.relative(â˜ƒ.rotation.rotate(Direction.SOUTH), 6);
         â˜ƒ.position = â˜ƒ.position.relative(â˜ƒ.rotation.rotate(Direction.EAST), 8);
         â˜ƒ.rotation = â˜ƒ.rotation.getRotated(Rotation.COUNTERCLOCKWISE_90);
      }

      private void addRoom1x1(
         List<WoodlandMansionPieces.WoodlandMansionPiece> var1, BlockPos var2, Rotation var3, Direction var4, WoodlandMansionPieces.FloorRoomCollection var5
      ) {
         Rotation â˜ƒ = Rotation.NONE;
         String â˜ƒx = â˜ƒ.get1x1(this.random);
         if (â˜ƒ != Direction.EAST) {
            if (â˜ƒ == Direction.NORTH) {
               â˜ƒ = â˜ƒ.getRotated(Rotation.COUNTERCLOCKWISE_90);
            } else if (â˜ƒ == Direction.WEST) {
               â˜ƒ = â˜ƒ.getRotated(Rotation.CLOCKWISE_180);
            } else if (â˜ƒ == Direction.SOUTH) {
               â˜ƒ = â˜ƒ.getRotated(Rotation.CLOCKWISE_90);
            } else {
               â˜ƒx = â˜ƒ.get1x1Secret(this.random);
            }
         }

         BlockPos â˜ƒ = StructureTemplate.getZeroPositionWithTransform(new BlockPos(1, 0, 0), Mirror.NONE, â˜ƒ, 7, 7);
         â˜ƒ = â˜ƒ.getRotated(â˜ƒ);
         â˜ƒ = â˜ƒ.rotate(â˜ƒ);
         BlockPos â˜ƒx = â˜ƒ.offset(â˜ƒ.getX(), 0, â˜ƒ.getZ());
         â˜ƒ.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureManager, â˜ƒx, â˜ƒx, â˜ƒ));
      }

      private void addRoom1x2(
         List<WoodlandMansionPieces.WoodlandMansionPiece> var1,
         BlockPos var2,
         Rotation var3,
         Direction var4,
         Direction var5,
         WoodlandMansionPieces.FloorRoomCollection var6,
         boolean var7
      ) {
         if (â˜ƒ == Direction.EAST && â˜ƒ == Direction.SOUTH) {
            BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ.rotate(Direction.EAST), 1);
            â˜ƒ.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureManager, â˜ƒ.get1x2SideEntrance(this.random, â˜ƒ), â˜ƒ, â˜ƒ));
         } else if (â˜ƒ == Direction.EAST && â˜ƒ == Direction.NORTH) {
            BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ.rotate(Direction.EAST), 1);
            â˜ƒ = â˜ƒ.relative(â˜ƒ.rotate(Direction.SOUTH), 6);
            â˜ƒ.add(
               new WoodlandMansionPieces.WoodlandMansionPiece(this.structureManager, â˜ƒ.get1x2SideEntrance(this.random, â˜ƒ), â˜ƒ, â˜ƒ, Mirror.LEFT_RIGHT)
            );
         } else if (â˜ƒ == Direction.WEST && â˜ƒ == Direction.NORTH) {
            BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ.rotate(Direction.EAST), 7);
            â˜ƒ = â˜ƒ.relative(â˜ƒ.rotate(Direction.SOUTH), 6);
            â˜ƒ.add(
               new WoodlandMansionPieces.WoodlandMansionPiece(
                  this.structureManager, â˜ƒ.get1x2SideEntrance(this.random, â˜ƒ), â˜ƒ, â˜ƒ.getRotated(Rotation.CLOCKWISE_180)
               )
            );
         } else if (â˜ƒ == Direction.WEST && â˜ƒ == Direction.SOUTH) {
            BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ.rotate(Direction.EAST), 7);
            â˜ƒ.add(
               new WoodlandMansionPieces.WoodlandMansionPiece(this.structureManager, â˜ƒ.get1x2SideEntrance(this.random, â˜ƒ), â˜ƒ, â˜ƒ, Mirror.FRONT_BACK)
            );
         } else if (â˜ƒ == Direction.SOUTH && â˜ƒ == Direction.EAST) {
            BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ.rotate(Direction.EAST), 1);
            â˜ƒ.add(
               new WoodlandMansionPieces.WoodlandMansionPiece(
                  this.structureManager, â˜ƒ.get1x2SideEntrance(this.random, â˜ƒ), â˜ƒ, â˜ƒ.getRotated(Rotation.CLOCKWISE_90), Mirror.LEFT_RIGHT
               )
            );
         } else if (â˜ƒ == Direction.SOUTH && â˜ƒ == Direction.WEST) {
            BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ.rotate(Direction.EAST), 7);
            â˜ƒ.add(
               new WoodlandMansionPieces.WoodlandMansionPiece(
                  this.structureManager, â˜ƒ.get1x2SideEntrance(this.random, â˜ƒ), â˜ƒ, â˜ƒ.getRotated(Rotation.CLOCKWISE_90)
               )
            );
         } else if (â˜ƒ == Direction.NORTH && â˜ƒ == Direction.WEST) {
            BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ.rotate(Direction.EAST), 7);
            â˜ƒ = â˜ƒ.relative(â˜ƒ.rotate(Direction.SOUTH), 6);
            â˜ƒ.add(
               new WoodlandMansionPieces.WoodlandMansionPiece(
                  this.structureManager, â˜ƒ.get1x2SideEntrance(this.random, â˜ƒ), â˜ƒ, â˜ƒ.getRotated(Rotation.CLOCKWISE_90), Mirror.FRONT_BACK
               )
            );
         } else if (â˜ƒ == Direction.NORTH && â˜ƒ == Direction.EAST) {
            BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ.rotate(Direction.EAST), 1);
            â˜ƒ = â˜ƒ.relative(â˜ƒ.rotate(Direction.SOUTH), 6);
            â˜ƒ.add(
               new WoodlandMansionPieces.WoodlandMansionPiece(
                  this.structureManager, â˜ƒ.get1x2SideEntrance(this.random, â˜ƒ), â˜ƒ, â˜ƒ.getRotated(Rotation.COUNTERCLOCKWISE_90)
               )
            );
         } else if (â˜ƒ == Direction.SOUTH && â˜ƒ == Direction.NORTH) {
            BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ.rotate(Direction.EAST), 1);
            â˜ƒ = â˜ƒ.relative(â˜ƒ.rotate(Direction.NORTH), 8);
            â˜ƒ.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureManager, â˜ƒ.get1x2FrontEntrance(this.random, â˜ƒ), â˜ƒ, â˜ƒ));
         } else if (â˜ƒ == Direction.NORTH && â˜ƒ == Direction.SOUTH) {
            BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ.rotate(Direction.EAST), 7);
            â˜ƒ = â˜ƒ.relative(â˜ƒ.rotate(Direction.SOUTH), 14);
            â˜ƒ.add(
               new WoodlandMansionPieces.WoodlandMansionPiece(
                  this.structureManager, â˜ƒ.get1x2FrontEntrance(this.random, â˜ƒ), â˜ƒ, â˜ƒ.getRotated(Rotation.CLOCKWISE_180)
               )
            );
         } else if (â˜ƒ == Direction.WEST && â˜ƒ == Direction.EAST) {
            BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ.rotate(Direction.EAST), 15);
            â˜ƒ.add(
               new WoodlandMansionPieces.WoodlandMansionPiece(
                  this.structureManager, â˜ƒ.get1x2FrontEntrance(this.random, â˜ƒ), â˜ƒ, â˜ƒ.getRotated(Rotation.CLOCKWISE_90)
               )
            );
         } else if (â˜ƒ == Direction.EAST && â˜ƒ == Direction.WEST) {
            BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ.rotate(Direction.WEST), 7);
            â˜ƒ = â˜ƒ.relative(â˜ƒ.rotate(Direction.SOUTH), 6);
            â˜ƒ.add(
               new WoodlandMansionPieces.WoodlandMansionPiece(
                  this.structureManager, â˜ƒ.get1x2FrontEntrance(this.random, â˜ƒ), â˜ƒ, â˜ƒ.getRotated(Rotation.COUNTERCLOCKWISE_90)
               )
            );
         } else if (â˜ƒ == Direction.UP && â˜ƒ == Direction.EAST) {
            BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ.rotate(Direction.EAST), 15);
            â˜ƒ.add(
               new WoodlandMansionPieces.WoodlandMansionPiece(this.structureManager, â˜ƒ.get1x2Secret(this.random), â˜ƒ, â˜ƒ.getRotated(Rotation.CLOCKWISE_90))
            );
         } else if (â˜ƒ == Direction.UP && â˜ƒ == Direction.SOUTH) {
            BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ.rotate(Direction.EAST), 1);
            â˜ƒ = â˜ƒ.relative(â˜ƒ.rotate(Direction.NORTH), 0);
            â˜ƒ.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureManager, â˜ƒ.get1x2Secret(this.random), â˜ƒ, â˜ƒ));
         }
      }

      private void addRoom2x2(
         List<WoodlandMansionPieces.WoodlandMansionPiece> var1,
         BlockPos var2,
         Rotation var3,
         Direction var4,
         Direction var5,
         WoodlandMansionPieces.FloorRoomCollection var6
      ) {
         int â˜ƒ = 0;
         int â˜ƒx = 0;
         Rotation â˜ƒxx = â˜ƒ;
         Mirror â˜ƒxxx = Mirror.NONE;
         if (â˜ƒ == Direction.EAST && â˜ƒ == Direction.SOUTH) {
            â˜ƒ = -7;
         } else if (â˜ƒ == Direction.EAST && â˜ƒ == Direction.NORTH) {
            â˜ƒ = -7;
            â˜ƒx = 6;
            â˜ƒxxx = Mirror.LEFT_RIGHT;
         } else if (â˜ƒ == Direction.NORTH && â˜ƒ == Direction.EAST) {
            â˜ƒ = 1;
            â˜ƒx = 14;
            â˜ƒxx = â˜ƒ.getRotated(Rotation.COUNTERCLOCKWISE_90);
         } else if (â˜ƒ == Direction.NORTH && â˜ƒ == Direction.WEST) {
            â˜ƒ = 7;
            â˜ƒx = 14;
            â˜ƒxx = â˜ƒ.getRotated(Rotation.COUNTERCLOCKWISE_90);
            â˜ƒxxx = Mirror.LEFT_RIGHT;
         } else if (â˜ƒ == Direction.SOUTH && â˜ƒ == Direction.WEST) {
            â˜ƒ = 7;
            â˜ƒx = -8;
            â˜ƒxx = â˜ƒ.getRotated(Rotation.CLOCKWISE_90);
         } else if (â˜ƒ == Direction.SOUTH && â˜ƒ == Direction.EAST) {
            â˜ƒ = 1;
            â˜ƒx = -8;
            â˜ƒxx = â˜ƒ.getRotated(Rotation.CLOCKWISE_90);
            â˜ƒxxx = Mirror.LEFT_RIGHT;
         } else if (â˜ƒ == Direction.WEST && â˜ƒ == Direction.NORTH) {
            â˜ƒ = 15;
            â˜ƒx = 6;
            â˜ƒxx = â˜ƒ.getRotated(Rotation.CLOCKWISE_180);
         } else if (â˜ƒ == Direction.WEST && â˜ƒ == Direction.SOUTH) {
            â˜ƒ = 15;
            â˜ƒxxx = Mirror.FRONT_BACK;
         }

         BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ.rotate(Direction.EAST), â˜ƒ);
         â˜ƒ = â˜ƒ.relative(â˜ƒ.rotate(Direction.SOUTH), â˜ƒx);
         â˜ƒ.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureManager, â˜ƒ.get2x2(this.random), â˜ƒ, â˜ƒxx, â˜ƒxxx));
      }

      private void addRoom2x2Secret(
         List<WoodlandMansionPieces.WoodlandMansionPiece> var1, BlockPos var2, Rotation var3, WoodlandMansionPieces.FloorRoomCollection var4
      ) {
         BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ.rotate(Direction.EAST), 1);
         â˜ƒ.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureManager, â˜ƒ.get2x2Secret(this.random), â˜ƒ, â˜ƒ, Mirror.NONE));
      }
   }

   static class PlacementData {
      public Rotation rotation;
      public BlockPos position;
      public String wallType;
   }

   static class SecondFloorRoomCollection extends WoodlandMansionPieces.FloorRoomCollection {
      @Override
      public String get1x1(Random var1) {
         return "1x1_b" + (â˜ƒ.nextInt(4) + 1);
      }

      @Override
      public String get1x1Secret(Random var1) {
         return "1x1_as" + (â˜ƒ.nextInt(4) + 1);
      }

      @Override
      public String get1x2SideEntrance(Random var1, boolean var2) {
         return â˜ƒ ? "1x2_c_stairs" : "1x2_c" + (â˜ƒ.nextInt(4) + 1);
      }

      @Override
      public String get1x2FrontEntrance(Random var1, boolean var2) {
         return â˜ƒ ? "1x2_d_stairs" : "1x2_d" + (â˜ƒ.nextInt(5) + 1);
      }

      @Override
      public String get1x2Secret(Random var1) {
         return "1x2_se" + (â˜ƒ.nextInt(1) + 1);
      }

      @Override
      public String get2x2(Random var1) {
         return "2x2_b" + (â˜ƒ.nextInt(5) + 1);
      }

      @Override
      public String get2x2Secret(Random var1) {
         return "2x2_s1";
      }
   }

   static class SimpleGrid {
      private final int[][] grid;
      final int width;
      final int height;
      private final int valueIfOutside;

      public SimpleGrid(int var1, int var2, int var3) {
         this.width = â˜ƒ;
         this.height = â˜ƒ;
         this.valueIfOutside = â˜ƒ;
         this.grid = new int[â˜ƒ][â˜ƒ];
      }

      public void set(int var1, int var2, int var3) {
         if (â˜ƒ >= 0 && â˜ƒ < this.width && â˜ƒ >= 0 && â˜ƒ < this.height) {
            this.grid[â˜ƒ][â˜ƒ] = â˜ƒ;
         }
      }

      public void set(int var1, int var2, int var3, int var4, int var5) {
         for(int â˜ƒ = â˜ƒ; â˜ƒ <= â˜ƒ; ++â˜ƒ) {
            for(int â˜ƒx = â˜ƒ; â˜ƒx <= â˜ƒ; ++â˜ƒx) {
               this.set(â˜ƒx, â˜ƒ, â˜ƒ);
            }
         }
      }

      public int get(int var1, int var2) {
         return â˜ƒ >= 0 && â˜ƒ < this.width && â˜ƒ >= 0 && â˜ƒ < this.height ? this.grid[â˜ƒ][â˜ƒ] : this.valueIfOutside;
      }

      public void setif(int var1, int var2, int var3, int var4) {
         if (this.get(â˜ƒ, â˜ƒ) == â˜ƒ) {
            this.set(â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }

      public boolean edgesTo(int var1, int var2, int var3) {
         return this.get(â˜ƒ - 1, â˜ƒ) == â˜ƒ || this.get(â˜ƒ + 1, â˜ƒ) == â˜ƒ || this.get(â˜ƒ, â˜ƒ + 1) == â˜ƒ || this.get(â˜ƒ, â˜ƒ - 1) == â˜ƒ;
      }
   }

   static class ThirdFloorRoomCollection extends WoodlandMansionPieces.SecondFloorRoomCollection {
   }

   public static class WoodlandMansionPiece extends TemplateStructurePiece {
      public WoodlandMansionPiece(StructureManager var1, String var2, BlockPos var3, Rotation var4) {
         this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Mirror.NONE);
      }

      public WoodlandMansionPiece(StructureManager var1, String var2, BlockPos var3, Rotation var4, Mirror var5) {
         super(StructurePieceType.WOODLAND_MANSION_PIECE, 0, â˜ƒ, makeLocation(â˜ƒ), â˜ƒ, makeSettings(â˜ƒ, â˜ƒ), â˜ƒ);
      }

      public WoodlandMansionPiece(ServerLevel var1, CompoundTag var2) {
         super(
            StructurePieceType.WOODLAND_MANSION_PIECE,
            â˜ƒ,
            â˜ƒ,
            var1x -> makeSettings(Mirror.valueOf(â˜ƒ.getString("Mi")), Rotation.valueOf(â˜ƒ.getString("Rot")))
         );
      }

      @Override
      protected ResourceLocation makeTemplateLocation() {
         return makeLocation(this.templateName);
      }

      private static ResourceLocation makeLocation(String var0) {
         return new ResourceLocation("woodland_mansion/" + â˜ƒ);
      }

      private static StructurePlaceSettings makeSettings(Mirror var0, Rotation var1) {
         return new StructurePlaceSettings().setIgnoreEntities(true).setRotation(â˜ƒ).setMirror(â˜ƒ).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
      }

      @Override
      protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
         super.addAdditionalSaveData(â˜ƒ, â˜ƒ);
         â˜ƒ.putString("Rot", this.placeSettings.getRotation().name());
         â˜ƒ.putString("Mi", this.placeSettings.getMirror().name());
      }

      @Override
      protected void handleDataMarker(String var1, BlockPos var2, ServerLevelAccessor var3, Random var4, BoundingBox var5) {
         if (â˜ƒ.startsWith("Chest")) {
            Rotation â˜ƒ = this.placeSettings.getRotation();
            BlockState â˜ƒx = Blocks.CHEST.defaultBlockState();
            if ("ChestWest".equals(â˜ƒ)) {
               â˜ƒx = â˜ƒx.setValue(ChestBlock.FACING, â˜ƒ.rotate(Direction.WEST));
            } else if ("ChestEast".equals(â˜ƒ)) {
               â˜ƒx = â˜ƒx.setValue(ChestBlock.FACING, â˜ƒ.rotate(Direction.EAST));
            } else if ("ChestSouth".equals(â˜ƒ)) {
               â˜ƒx = â˜ƒx.setValue(ChestBlock.FACING, â˜ƒ.rotate(Direction.SOUTH));
            } else if ("ChestNorth".equals(â˜ƒ)) {
               â˜ƒx = â˜ƒx.setValue(ChestBlock.FACING, â˜ƒ.rotate(Direction.NORTH));
            }

            this.createChest(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, BuiltInLootTables.WOODLAND_MANSION, â˜ƒx);
         } else {
            AbstractIllager â˜ƒ;
            switch(â˜ƒ) {
               case "Mage":
                  â˜ƒ = EntityType.EVOKER.create(â˜ƒ.getLevel());
                  break;
               case "Warrior":
                  â˜ƒ = EntityType.VINDICATOR.create(â˜ƒ.getLevel());
                  break;
               default:
                  return;
            }

            â˜ƒ.setPersistenceRequired();
            â˜ƒ.moveTo(â˜ƒ, 0.0F, 0.0F);
            â˜ƒ.finalizeSpawn(â˜ƒ, â˜ƒ.getCurrentDifficultyAt(â˜ƒ.blockPosition()), MobSpawnType.STRUCTURE, null, null);
            â˜ƒ.addFreshEntityWithPassengers(â˜ƒ);
            â˜ƒ.setBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 2);
         }
      }
   }
}
