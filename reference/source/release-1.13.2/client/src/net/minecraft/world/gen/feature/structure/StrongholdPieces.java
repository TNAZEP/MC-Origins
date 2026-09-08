package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTorchWall;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

public class StrongholdPieces {
   private static final StrongholdPieces.PieceWeight[] field_75205_b = new StrongholdPieces.PieceWeight[]{
      new StrongholdPieces.PieceWeight(StrongholdPieces.Straight.class, 40, 0),
      new StrongholdPieces.PieceWeight(StrongholdPieces.Prison.class, 5, 5),
      new StrongholdPieces.PieceWeight(StrongholdPieces.LeftTurn.class, 20, 0),
      new StrongholdPieces.PieceWeight(StrongholdPieces.RightTurn.class, 20, 0),
      new StrongholdPieces.PieceWeight(StrongholdPieces.RoomCrossing.class, 10, 6),
      new StrongholdPieces.PieceWeight(StrongholdPieces.StairsStraight.class, 5, 5),
      new StrongholdPieces.PieceWeight(StrongholdPieces.Stairs.class, 5, 5),
      new StrongholdPieces.PieceWeight(StrongholdPieces.Crossing.class, 5, 4),
      new StrongholdPieces.PieceWeight(StrongholdPieces.ChestCorridor.class, 5, 4),
      new StrongholdPieces.PieceWeight(StrongholdPieces.Library.class, 10, 2) {
         @Override
         public boolean func_75189_a(int var1) {
            return super.func_75189_a(☃) && ☃ > 4;
         }
      },
      new StrongholdPieces.PieceWeight(StrongholdPieces.PortalRoom.class, 20, 1) {
         @Override
         public boolean func_75189_a(int var1) {
            return super.func_75189_a(☃) && ☃ > 5;
         }
      }
   };
   private static List<StrongholdPieces.PieceWeight> field_75206_c;
   private static Class<? extends StrongholdPieces.Stronghold> field_75203_d;
   private static int field_75207_a;
   private static final StrongholdPieces.Stones field_75204_e = new StrongholdPieces.Stones();

   public static void func_143046_a() {
      StructureIO.func_143031_a(StrongholdPieces.ChestCorridor.class, "SHCC");
      StructureIO.func_143031_a(StrongholdPieces.Corridor.class, "SHFC");
      StructureIO.func_143031_a(StrongholdPieces.Crossing.class, "SH5C");
      StructureIO.func_143031_a(StrongholdPieces.LeftTurn.class, "SHLT");
      StructureIO.func_143031_a(StrongholdPieces.Library.class, "SHLi");
      StructureIO.func_143031_a(StrongholdPieces.PortalRoom.class, "SHPR");
      StructureIO.func_143031_a(StrongholdPieces.Prison.class, "SHPH");
      StructureIO.func_143031_a(StrongholdPieces.RightTurn.class, "SHRT");
      StructureIO.func_143031_a(StrongholdPieces.RoomCrossing.class, "SHRC");
      StructureIO.func_143031_a(StrongholdPieces.Stairs.class, "SHSD");
      StructureIO.func_143031_a(StrongholdPieces.Stairs2.class, "SHStart");
      StructureIO.func_143031_a(StrongholdPieces.Straight.class, "SHS");
      StructureIO.func_143031_a(StrongholdPieces.StairsStraight.class, "SHSSD");
   }

   public static void func_75198_a() {
      field_75206_c = Lists.<StrongholdPieces.PieceWeight>newArrayList();

      for(StrongholdPieces.PieceWeight ☃ : field_75205_b) {
         ☃.field_75193_c = 0;
         field_75206_c.add(☃);
      }

      field_75203_d = null;
   }

   private static boolean func_75202_c() {
      boolean ☃ = false;
      field_75207_a = 0;

      for(StrongholdPieces.PieceWeight ☃x : field_75206_c) {
         if (☃x.field_75191_d > 0 && ☃x.field_75193_c < ☃x.field_75191_d) {
            ☃ = true;
         }

         field_75207_a += ☃x.field_75192_b;
      }

      return ☃;
   }

   private static StrongholdPieces.Stronghold func_175954_a(
      Class<? extends StrongholdPieces.Stronghold> var0,
      List<StructurePiece> var1,
      Random var2,
      int var3,
      int var4,
      int var5,
      @Nullable EnumFacing var6,
      int var7
   ) {
      StrongholdPieces.Stronghold ☃ = null;
      if (☃ == StrongholdPieces.Straight.class) {
         ☃ = StrongholdPieces.Straight.func_175862_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == StrongholdPieces.Prison.class) {
         ☃ = StrongholdPieces.Prison.func_175860_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == StrongholdPieces.LeftTurn.class) {
         ☃ = StrongholdPieces.LeftTurn.func_175867_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == StrongholdPieces.RightTurn.class) {
         ☃ = StrongholdPieces.RightTurn.func_175867_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == StrongholdPieces.RoomCrossing.class) {
         ☃ = StrongholdPieces.RoomCrossing.func_175859_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == StrongholdPieces.StairsStraight.class) {
         ☃ = StrongholdPieces.StairsStraight.func_175861_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == StrongholdPieces.Stairs.class) {
         ☃ = StrongholdPieces.Stairs.func_175863_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == StrongholdPieces.Crossing.class) {
         ☃ = StrongholdPieces.Crossing.func_175866_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == StrongholdPieces.ChestCorridor.class) {
         ☃ = StrongholdPieces.ChestCorridor.func_175868_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == StrongholdPieces.Library.class) {
         ☃ = StrongholdPieces.Library.func_175864_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == StrongholdPieces.PortalRoom.class) {
         ☃ = StrongholdPieces.PortalRoom.func_175865_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      }

      return ☃;
   }

   private static StrongholdPieces.Stronghold func_175955_b(
      StrongholdPieces.Stairs2 var0, List<StructurePiece> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
   ) {
      if (!func_75202_c()) {
         return null;
      } else {
         if (field_75203_d != null) {
            StrongholdPieces.Stronghold ☃ = func_175954_a(field_75203_d, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
            field_75203_d = null;
            if (☃ != null) {
               return ☃;
            }
         }

         int ☃ = 0;

         while(☃ < 5) {
            ++☃;
            int ☃x = ☃.nextInt(field_75207_a);

            for(StrongholdPieces.PieceWeight ☃xx : field_75206_c) {
               ☃x -= ☃xx.field_75192_b;
               if (☃x < 0) {
                  if (!☃xx.func_75189_a(☃) || ☃xx == ☃.field_75027_a) {
                     break;
                  }

                  StrongholdPieces.Stronghold ☃xxx = func_175954_a(☃xx.field_75194_a, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
                  if (☃xxx != null) {
                     ++☃xx.field_75193_c;
                     ☃.field_75027_a = ☃xx;
                     if (!☃xx.func_75190_a()) {
                        field_75206_c.remove(☃xx);
                     }

                     return ☃xxx;
                  }
               }
            }
         }

         MutableBoundingBox ☃x = StrongholdPieces.Corridor.func_175869_a(☃, ☃, ☃, ☃, ☃, ☃);
         return ☃x != null && ☃x.field_78895_b > 1 ? new StrongholdPieces.Corridor(☃, ☃, ☃x, ☃) : null;
      }
   }

   private static StructurePiece func_175953_c(
      StrongholdPieces.Stairs2 var0, List<StructurePiece> var1, Random var2, int var3, int var4, int var5, @Nullable EnumFacing var6, int var7
   ) {
      if (☃ > 50) {
         return null;
      } else if (Math.abs(☃ - ☃.func_74874_b().field_78897_a) <= 112 && Math.abs(☃ - ☃.func_74874_b().field_78896_c) <= 112) {
         StructurePiece ☃ = func_175955_b(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃ + 1);
         if (☃ != null) {
            ☃.add(☃);
            ☃.field_75026_c.add(☃);
         }

         return ☃;
      } else {
         return null;
      }
   }

   public static class ChestCorridor extends StrongholdPieces.Stronghold {
      private boolean field_75002_c;

      public ChestCorridor() {
      }

      public ChestCorridor(int var1, Random var2, MutableBoundingBox var3, EnumFacing var4) {
         super(☃);
         this.func_186164_a(☃);
         this.field_143013_d = this.func_74988_a(☃);
         this.field_74887_e = ☃;
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
         super.func_143012_a(☃);
         ☃.func_74757_a("Chest", this.field_75002_c);
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
         super.func_143011_b(☃, ☃);
         this.field_75002_c = ☃.func_74767_n("Chest");
      }

      @Override
      public void func_74861_a(StructurePiece var1, List<StructurePiece> var2, Random var3) {
         this.func_74986_a((StrongholdPieces.Stairs2)☃, ☃, ☃, 1, 1);
      }

      public static StrongholdPieces.ChestCorridor func_175868_a(
         List<StructurePiece> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6
      ) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, -1, -1, 0, 5, 5, 7, ☃);
         return func_74991_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new StrongholdPieces.ChestCorridor(☃, ☃, ☃, ☃) : null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         this.func_74882_a(☃, ☃, 0, 0, 0, 4, 4, 6, true, ☃, StrongholdPieces.field_75204_e);
         this.func_74990_a(☃, ☃, ☃, this.field_143013_d, 1, 1, 0);
         this.func_74990_a(☃, ☃, ☃, StrongholdPieces.Stronghold.Door.OPENING, 1, 1, 6);
         this.func_175804_a(☃, ☃, 3, 1, 2, 3, 1, 4, Blocks.field_196696_di.func_176223_P(), Blocks.field_196696_di.func_176223_P(), false);
         this.func_175811_a(☃, Blocks.field_196573_bB.func_176223_P(), 3, 1, 1, ☃);
         this.func_175811_a(☃, Blocks.field_196573_bB.func_176223_P(), 3, 1, 5, ☃);
         this.func_175811_a(☃, Blocks.field_196573_bB.func_176223_P(), 3, 2, 2, ☃);
         this.func_175811_a(☃, Blocks.field_196573_bB.func_176223_P(), 3, 2, 4, ☃);

         for(int ☃ = 2; ☃ <= 4; ++☃) {
            this.func_175811_a(☃, Blocks.field_196573_bB.func_176223_P(), 2, 1, ☃, ☃);
         }

         if (!this.field_75002_c && ☃.func_175898_b(new BlockPos(this.func_74865_a(3, 3), this.func_74862_a(2), this.func_74873_b(3, 3)))) {
            this.field_75002_c = true;
            this.func_186167_a(☃, ☃, ☃, 3, 2, 3, LootTableList.field_186428_j);
         }

         return true;
      }
   }

   public static class Corridor extends StrongholdPieces.Stronghold {
      private int field_74993_a;

      public Corridor() {
      }

      public Corridor(int var1, Random var2, MutableBoundingBox var3, EnumFacing var4) {
         super(☃);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
         this.field_74993_a = ☃ != EnumFacing.NORTH && ☃ != EnumFacing.SOUTH ? ☃.func_78883_b() : ☃.func_78880_d();
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
         super.func_143012_a(☃);
         ☃.func_74768_a("Steps", this.field_74993_a);
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
         super.func_143011_b(☃, ☃);
         this.field_74993_a = ☃.func_74762_e("Steps");
      }

      public static MutableBoundingBox func_175869_a(List<StructurePiece> var0, Random var1, int var2, int var3, int var4, EnumFacing var5) {
         int ☃ = 3;
         MutableBoundingBox ☃x = MutableBoundingBox.func_175897_a(☃, ☃, ☃, -1, -1, 0, 5, 5, 4, ☃);
         StructurePiece ☃xx = StructurePiece.func_74883_a(☃, ☃x);
         if (☃xx == null) {
            return null;
         } else {
            if (☃xx.func_74874_b().field_78895_b == ☃x.field_78895_b) {
               for(int ☃ = 3; ☃ >= 1; --☃) {
                  ☃x = MutableBoundingBox.func_175897_a(☃, ☃, ☃, -1, -1, 0, 5, 5, ☃ - 1, ☃);
                  if (!☃xx.func_74874_b().func_78884_a(☃x)) {
                     return MutableBoundingBox.func_175897_a(☃, ☃, ☃, -1, -1, 0, 5, 5, ☃, ☃);
                  }
               }
            }

            return null;
         }
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         for(int ☃ = 0; ☃ < this.field_74993_a; ++☃) {
            this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 0, 0, ☃, ☃);
            this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 1, 0, ☃, ☃);
            this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 2, 0, ☃, ☃);
            this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 3, 0, ☃, ☃);
            this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 4, 0, ☃, ☃);

            for(int ☃x = 1; ☃x <= 3; ++☃x) {
               this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 0, ☃x, ☃, ☃);
               this.func_175811_a(☃, Blocks.field_201941_jj.func_176223_P(), 1, ☃x, ☃, ☃);
               this.func_175811_a(☃, Blocks.field_201941_jj.func_176223_P(), 2, ☃x, ☃, ☃);
               this.func_175811_a(☃, Blocks.field_201941_jj.func_176223_P(), 3, ☃x, ☃, ☃);
               this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 4, ☃x, ☃, ☃);
            }

            this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 0, 4, ☃, ☃);
            this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 1, 4, ☃, ☃);
            this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 2, 4, ☃, ☃);
            this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 3, 4, ☃, ☃);
            this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 4, 4, ☃, ☃);
         }

         return true;
      }
   }

   public static class Crossing extends StrongholdPieces.Stronghold {
      private boolean field_74996_b;
      private boolean field_74997_c;
      private boolean field_74995_d;
      private boolean field_74999_h;

      public Crossing() {
      }

      public Crossing(int var1, Random var2, MutableBoundingBox var3, EnumFacing var4) {
         super(☃);
         this.func_186164_a(☃);
         this.field_143013_d = this.func_74988_a(☃);
         this.field_74887_e = ☃;
         this.field_74996_b = ☃.nextBoolean();
         this.field_74997_c = ☃.nextBoolean();
         this.field_74995_d = ☃.nextBoolean();
         this.field_74999_h = ☃.nextInt(3) > 0;
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
         super.func_143012_a(☃);
         ☃.func_74757_a("leftLow", this.field_74996_b);
         ☃.func_74757_a("leftHigh", this.field_74997_c);
         ☃.func_74757_a("rightLow", this.field_74995_d);
         ☃.func_74757_a("rightHigh", this.field_74999_h);
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
         super.func_143011_b(☃, ☃);
         this.field_74996_b = ☃.func_74767_n("leftLow");
         this.field_74997_c = ☃.func_74767_n("leftHigh");
         this.field_74995_d = ☃.func_74767_n("rightLow");
         this.field_74999_h = ☃.func_74767_n("rightHigh");
      }

      @Override
      public void func_74861_a(StructurePiece var1, List<StructurePiece> var2, Random var3) {
         int ☃ = 3;
         int ☃x = 5;
         EnumFacing ☃xx = this.func_186165_e();
         if (☃xx == EnumFacing.WEST || ☃xx == EnumFacing.NORTH) {
            ☃ = 8 - ☃;
            ☃x = 8 - ☃x;
         }

         this.func_74986_a((StrongholdPieces.Stairs2)☃, ☃, ☃, 5, 1);
         if (this.field_74996_b) {
            this.func_74989_b((StrongholdPieces.Stairs2)☃, ☃, ☃, ☃, 1);
         }

         if (this.field_74997_c) {
            this.func_74989_b((StrongholdPieces.Stairs2)☃, ☃, ☃, ☃x, 7);
         }

         if (this.field_74995_d) {
            this.func_74987_c((StrongholdPieces.Stairs2)☃, ☃, ☃, ☃, 1);
         }

         if (this.field_74999_h) {
            this.func_74987_c((StrongholdPieces.Stairs2)☃, ☃, ☃, ☃x, 7);
         }
      }

      public static StrongholdPieces.Crossing func_175866_a(List<StructurePiece> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, -4, -3, 0, 10, 9, 11, ☃);
         return func_74991_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new StrongholdPieces.Crossing(☃, ☃, ☃, ☃) : null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         this.func_74882_a(☃, ☃, 0, 0, 0, 9, 8, 10, true, ☃, StrongholdPieces.field_75204_e);
         this.func_74990_a(☃, ☃, ☃, this.field_143013_d, 4, 3, 0);
         if (this.field_74996_b) {
            this.func_175804_a(☃, ☃, 0, 3, 1, 0, 5, 3, field_202556_l, field_202556_l, false);
         }

         if (this.field_74995_d) {
            this.func_175804_a(☃, ☃, 9, 3, 1, 9, 5, 3, field_202556_l, field_202556_l, false);
         }

         if (this.field_74997_c) {
            this.func_175804_a(☃, ☃, 0, 5, 7, 0, 7, 9, field_202556_l, field_202556_l, false);
         }

         if (this.field_74999_h) {
            this.func_175804_a(☃, ☃, 9, 5, 7, 9, 7, 9, field_202556_l, field_202556_l, false);
         }

         this.func_175804_a(☃, ☃, 5, 1, 10, 7, 3, 10, field_202556_l, field_202556_l, false);
         this.func_74882_a(☃, ☃, 1, 2, 1, 8, 2, 6, false, ☃, StrongholdPieces.field_75204_e);
         this.func_74882_a(☃, ☃, 4, 1, 5, 4, 4, 9, false, ☃, StrongholdPieces.field_75204_e);
         this.func_74882_a(☃, ☃, 8, 1, 5, 8, 4, 9, false, ☃, StrongholdPieces.field_75204_e);
         this.func_74882_a(☃, ☃, 1, 4, 7, 3, 4, 9, false, ☃, StrongholdPieces.field_75204_e);
         this.func_74882_a(☃, ☃, 1, 3, 5, 3, 3, 6, false, ☃, StrongholdPieces.field_75204_e);
         this.func_175804_a(☃, ☃, 1, 3, 4, 3, 3, 4, Blocks.field_150333_U.func_176223_P(), Blocks.field_150333_U.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 4, 6, 3, 4, 6, Blocks.field_150333_U.func_176223_P(), Blocks.field_150333_U.func_176223_P(), false);
         this.func_74882_a(☃, ☃, 5, 1, 7, 7, 1, 8, false, ☃, StrongholdPieces.field_75204_e);
         this.func_175804_a(☃, ☃, 5, 1, 9, 7, 1, 9, Blocks.field_150333_U.func_176223_P(), Blocks.field_150333_U.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 5, 2, 7, 7, 2, 7, Blocks.field_150333_U.func_176223_P(), Blocks.field_150333_U.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 4, 5, 7, 4, 5, 9, Blocks.field_150333_U.func_176223_P(), Blocks.field_150333_U.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 8, 5, 7, 8, 5, 9, Blocks.field_150333_U.func_176223_P(), Blocks.field_150333_U.func_176223_P(), false);
         this.func_175804_a(
            ☃,
            ☃,
            5,
            5,
            7,
            7,
            5,
            9,
            Blocks.field_150333_U.func_176223_P().func_206870_a(BlockSlab.field_196505_a, SlabType.DOUBLE),
            Blocks.field_150333_U.func_176223_P().func_206870_a(BlockSlab.field_196505_a, SlabType.DOUBLE),
            false
         );
         this.func_175811_a(☃, Blocks.field_196591_bQ.func_176223_P().func_206870_a(BlockTorchWall.field_196532_a, EnumFacing.SOUTH), 6, 5, 6, ☃);
         return true;
      }
   }

   public static class LeftTurn extends StrongholdPieces.Stronghold {
      public LeftTurn() {
      }

      public LeftTurn(int var1, Random var2, MutableBoundingBox var3, EnumFacing var4) {
         super(☃);
         this.func_186164_a(☃);
         this.field_143013_d = this.func_74988_a(☃);
         this.field_74887_e = ☃;
      }

      @Override
      public void func_74861_a(StructurePiece var1, List<StructurePiece> var2, Random var3) {
         EnumFacing ☃ = this.func_186165_e();
         if (☃ != EnumFacing.NORTH && ☃ != EnumFacing.EAST) {
            this.func_74987_c((StrongholdPieces.Stairs2)☃, ☃, ☃, 1, 1);
         } else {
            this.func_74989_b((StrongholdPieces.Stairs2)☃, ☃, ☃, 1, 1);
         }
      }

      public static StrongholdPieces.LeftTurn func_175867_a(List<StructurePiece> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, -1, -1, 0, 5, 5, 5, ☃);
         return func_74991_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new StrongholdPieces.LeftTurn(☃, ☃, ☃, ☃) : null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         this.func_74882_a(☃, ☃, 0, 0, 0, 4, 4, 4, true, ☃, StrongholdPieces.field_75204_e);
         this.func_74990_a(☃, ☃, ☃, this.field_143013_d, 1, 1, 0);
         EnumFacing ☃ = this.func_186165_e();
         if (☃ != EnumFacing.NORTH && ☃ != EnumFacing.EAST) {
            this.func_175804_a(☃, ☃, 4, 1, 1, 4, 3, 3, field_202556_l, field_202556_l, false);
         } else {
            this.func_175804_a(☃, ☃, 0, 1, 1, 0, 3, 3, field_202556_l, field_202556_l, false);
         }

         return true;
      }
   }

   public static class Library extends StrongholdPieces.Stronghold {
      private boolean field_75008_c;

      public Library() {
      }

      public Library(int var1, Random var2, MutableBoundingBox var3, EnumFacing var4) {
         super(☃);
         this.func_186164_a(☃);
         this.field_143013_d = this.func_74988_a(☃);
         this.field_74887_e = ☃;
         this.field_75008_c = ☃.func_78882_c() > 6;
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
         super.func_143012_a(☃);
         ☃.func_74757_a("Tall", this.field_75008_c);
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
         super.func_143011_b(☃, ☃);
         this.field_75008_c = ☃.func_74767_n("Tall");
      }

      public static StrongholdPieces.Library func_175864_a(List<StructurePiece> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, -4, -1, 0, 14, 11, 15, ☃);
         if (!func_74991_a(☃) || StructurePiece.func_74883_a(☃, ☃) != null) {
            ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, -4, -1, 0, 14, 6, 15, ☃);
            if (!func_74991_a(☃) || StructurePiece.func_74883_a(☃, ☃) != null) {
               return null;
            }
         }

         return new StrongholdPieces.Library(☃, ☃, ☃, ☃);
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         int ☃ = 11;
         if (!this.field_75008_c) {
            ☃ = 6;
         }

         this.func_74882_a(☃, ☃, 0, 0, 0, 13, ☃ - 1, 14, true, ☃, StrongholdPieces.field_75204_e);
         this.func_74990_a(☃, ☃, ☃, this.field_143013_d, 4, 1, 0);
         this.func_189914_a(☃, ☃, ☃, 0.07F, 2, 1, 1, 11, 4, 13, Blocks.field_196553_aF.func_176223_P(), Blocks.field_196553_aF.func_176223_P(), false, false);
         int ☃ = 1;
         int ☃x = 12;

         for(int ☃xx = 1; ☃xx <= 13; ++☃xx) {
            if ((☃xx - 1) % 4 == 0) {
               this.func_175804_a(☃, ☃, 1, 1, ☃xx, 1, 4, ☃xx, Blocks.field_196662_n.func_176223_P(), Blocks.field_196662_n.func_176223_P(), false);
               this.func_175804_a(☃, ☃, 12, 1, ☃xx, 12, 4, ☃xx, Blocks.field_196662_n.func_176223_P(), Blocks.field_196662_n.func_176223_P(), false);
               this.func_175811_a(☃, Blocks.field_196591_bQ.func_176223_P().func_206870_a(BlockTorchWall.field_196532_a, EnumFacing.EAST), 2, 3, ☃xx, ☃);
               this.func_175811_a(☃, Blocks.field_196591_bQ.func_176223_P().func_206870_a(BlockTorchWall.field_196532_a, EnumFacing.WEST), 11, 3, ☃xx, ☃);
               if (this.field_75008_c) {
                  this.func_175804_a(☃, ☃, 1, 6, ☃xx, 1, 9, ☃xx, Blocks.field_196662_n.func_176223_P(), Blocks.field_196662_n.func_176223_P(), false);
                  this.func_175804_a(☃, ☃, 12, 6, ☃xx, 12, 9, ☃xx, Blocks.field_196662_n.func_176223_P(), Blocks.field_196662_n.func_176223_P(), false);
               }
            } else {
               this.func_175804_a(☃, ☃, 1, 1, ☃xx, 1, 4, ☃xx, Blocks.field_150342_X.func_176223_P(), Blocks.field_150342_X.func_176223_P(), false);
               this.func_175804_a(☃, ☃, 12, 1, ☃xx, 12, 4, ☃xx, Blocks.field_150342_X.func_176223_P(), Blocks.field_150342_X.func_176223_P(), false);
               if (this.field_75008_c) {
                  this.func_175804_a(☃, ☃, 1, 6, ☃xx, 1, 9, ☃xx, Blocks.field_150342_X.func_176223_P(), Blocks.field_150342_X.func_176223_P(), false);
                  this.func_175804_a(☃, ☃, 12, 6, ☃xx, 12, 9, ☃xx, Blocks.field_150342_X.func_176223_P(), Blocks.field_150342_X.func_176223_P(), false);
               }
            }
         }

         for(int ☃xx = 3; ☃xx < 12; ☃xx += 2) {
            this.func_175804_a(☃, ☃, 3, 1, ☃xx, 4, 3, ☃xx, Blocks.field_150342_X.func_176223_P(), Blocks.field_150342_X.func_176223_P(), false);
            this.func_175804_a(☃, ☃, 6, 1, ☃xx, 7, 3, ☃xx, Blocks.field_150342_X.func_176223_P(), Blocks.field_150342_X.func_176223_P(), false);
            this.func_175804_a(☃, ☃, 9, 1, ☃xx, 10, 3, ☃xx, Blocks.field_150342_X.func_176223_P(), Blocks.field_150342_X.func_176223_P(), false);
         }

         if (this.field_75008_c) {
            this.func_175804_a(☃, ☃, 1, 5, 1, 3, 5, 13, Blocks.field_196662_n.func_176223_P(), Blocks.field_196662_n.func_176223_P(), false);
            this.func_175804_a(☃, ☃, 10, 5, 1, 12, 5, 13, Blocks.field_196662_n.func_176223_P(), Blocks.field_196662_n.func_176223_P(), false);
            this.func_175804_a(☃, ☃, 4, 5, 1, 9, 5, 2, Blocks.field_196662_n.func_176223_P(), Blocks.field_196662_n.func_176223_P(), false);
            this.func_175804_a(☃, ☃, 4, 5, 12, 9, 5, 13, Blocks.field_196662_n.func_176223_P(), Blocks.field_196662_n.func_176223_P(), false);
            this.func_175811_a(☃, Blocks.field_196662_n.func_176223_P(), 9, 5, 11, ☃);
            this.func_175811_a(☃, Blocks.field_196662_n.func_176223_P(), 8, 5, 11, ☃);
            this.func_175811_a(☃, Blocks.field_196662_n.func_176223_P(), 9, 5, 10, ☃);
            IBlockState ☃xx = Blocks.field_180407_aO
               .func_176223_P()
               .func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true))
               .func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true));
            IBlockState ☃xxx = Blocks.field_180407_aO
               .func_176223_P()
               .func_206870_a(BlockFence.field_196409_a, Boolean.valueOf(true))
               .func_206870_a(BlockFence.field_196413_c, Boolean.valueOf(true));
            this.func_175804_a(☃, ☃, 3, 6, 3, 3, 6, 11, ☃xxx, ☃xxx, false);
            this.func_175804_a(☃, ☃, 10, 6, 3, 10, 6, 9, ☃xxx, ☃xxx, false);
            this.func_175804_a(☃, ☃, 4, 6, 2, 9, 6, 2, ☃xx, ☃xx, false);
            this.func_175804_a(☃, ☃, 4, 6, 12, 7, 6, 12, ☃xx, ☃xx, false);
            this.func_175811_a(
               ☃,
               Blocks.field_180407_aO
                  .func_176223_P()
                  .func_206870_a(BlockFence.field_196409_a, Boolean.valueOf(true))
                  .func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true)),
               3,
               6,
               2,
               ☃
            );
            this.func_175811_a(
               ☃,
               Blocks.field_180407_aO
                  .func_176223_P()
                  .func_206870_a(BlockFence.field_196413_c, Boolean.valueOf(true))
                  .func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true)),
               3,
               6,
               12,
               ☃
            );
            this.func_175811_a(
               ☃,
               Blocks.field_180407_aO
                  .func_176223_P()
                  .func_206870_a(BlockFence.field_196409_a, Boolean.valueOf(true))
                  .func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true)),
               10,
               6,
               2,
               ☃
            );

            for(int ☃xxxx = 0; ☃xxxx <= 2; ++☃xxxx) {
               this.func_175811_a(
                  ☃,
                  Blocks.field_180407_aO
                     .func_176223_P()
                     .func_206870_a(BlockFence.field_196413_c, Boolean.valueOf(true))
                     .func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true)),
                  8 + ☃xxxx,
                  6,
                  12 - ☃xxxx,
                  ☃
               );
               if (☃xxxx != 2) {
                  this.func_175811_a(
                     ☃,
                     Blocks.field_180407_aO
                        .func_176223_P()
                        .func_206870_a(BlockFence.field_196409_a, Boolean.valueOf(true))
                        .func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true)),
                     8 + ☃xxxx,
                     6,
                     11 - ☃xxxx,
                     ☃
                  );
               }
            }

            IBlockState ☃xxxx = Blocks.field_150468_ap.func_176223_P().func_206870_a(BlockLadder.field_176382_a, EnumFacing.SOUTH);
            this.func_175811_a(☃, ☃xxxx, 10, 1, 13, ☃);
            this.func_175811_a(☃, ☃xxxx, 10, 2, 13, ☃);
            this.func_175811_a(☃, ☃xxxx, 10, 3, 13, ☃);
            this.func_175811_a(☃, ☃xxxx, 10, 4, 13, ☃);
            this.func_175811_a(☃, ☃xxxx, 10, 5, 13, ☃);
            this.func_175811_a(☃, ☃xxxx, 10, 6, 13, ☃);
            this.func_175811_a(☃, ☃xxxx, 10, 7, 13, ☃);
            int ☃xxxxx = 7;
            int ☃xxxxxx = 7;
            IBlockState ☃xxxxxxx = Blocks.field_180407_aO.func_176223_P().func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true));
            this.func_175811_a(☃, ☃xxxxxxx, 6, 9, 7, ☃);
            IBlockState ☃xxxxxxxx = Blocks.field_180407_aO.func_176223_P().func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true));
            this.func_175811_a(☃, ☃xxxxxxxx, 7, 9, 7, ☃);
            this.func_175811_a(☃, ☃xxxxxxx, 6, 8, 7, ☃);
            this.func_175811_a(☃, ☃xxxxxxxx, 7, 8, 7, ☃);
            IBlockState ☃xxxxxxxxx = ☃xxx.func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true))
               .func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true));
            this.func_175811_a(☃, ☃xxxxxxxxx, 6, 7, 7, ☃);
            this.func_175811_a(☃, ☃xxxxxxxxx, 7, 7, 7, ☃);
            this.func_175811_a(☃, ☃xxxxxxx, 5, 7, 7, ☃);
            this.func_175811_a(☃, ☃xxxxxxxx, 8, 7, 7, ☃);
            this.func_175811_a(☃, ☃xxxxxxx.func_206870_a(BlockFence.field_196409_a, Boolean.valueOf(true)), 6, 7, 6, ☃);
            this.func_175811_a(☃, ☃xxxxxxx.func_206870_a(BlockFence.field_196413_c, Boolean.valueOf(true)), 6, 7, 8, ☃);
            this.func_175811_a(☃, ☃xxxxxxxx.func_206870_a(BlockFence.field_196409_a, Boolean.valueOf(true)), 7, 7, 6, ☃);
            this.func_175811_a(☃, ☃xxxxxxxx.func_206870_a(BlockFence.field_196413_c, Boolean.valueOf(true)), 7, 7, 8, ☃);
            IBlockState ☃xxxxxxxxxx = Blocks.field_150478_aa.func_176223_P();
            this.func_175811_a(☃, ☃xxxxxxxxxx, 5, 8, 7, ☃);
            this.func_175811_a(☃, ☃xxxxxxxxxx, 8, 8, 7, ☃);
            this.func_175811_a(☃, ☃xxxxxxxxxx, 6, 8, 6, ☃);
            this.func_175811_a(☃, ☃xxxxxxxxxx, 6, 8, 8, ☃);
            this.func_175811_a(☃, ☃xxxxxxxxxx, 7, 8, 6, ☃);
            this.func_175811_a(☃, ☃xxxxxxxxxx, 7, 8, 8, ☃);
         }

         this.func_186167_a(☃, ☃, ☃, 3, 3, 5, LootTableList.field_186426_h);
         if (this.field_75008_c) {
            this.func_175811_a(☃, field_202556_l, 12, 9, 1, ☃);
            this.func_186167_a(☃, ☃, ☃, 12, 8, 1, LootTableList.field_186426_h);
         }

         return true;
      }
   }

   static class PieceWeight {
      public Class<? extends StrongholdPieces.Stronghold> field_75194_a;
      public final int field_75192_b;
      public int field_75193_c;
      public int field_75191_d;

      public PieceWeight(Class<? extends StrongholdPieces.Stronghold> var1, int var2, int var3) {
         this.field_75194_a = ☃;
         this.field_75192_b = ☃;
         this.field_75191_d = ☃;
      }

      public boolean func_75189_a(int var1) {
         return this.field_75191_d == 0 || this.field_75193_c < this.field_75191_d;
      }

      public boolean func_75190_a() {
         return this.field_75191_d == 0 || this.field_75193_c < this.field_75191_d;
      }
   }

   public static class PortalRoom extends StrongholdPieces.Stronghold {
      private boolean field_75005_a;

      public PortalRoom() {
      }

      public PortalRoom(int var1, Random var2, MutableBoundingBox var3, EnumFacing var4) {
         super(☃);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
         super.func_143012_a(☃);
         ☃.func_74757_a("Mob", this.field_75005_a);
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
         super.func_143011_b(☃, ☃);
         this.field_75005_a = ☃.func_74767_n("Mob");
      }

      @Override
      public void func_74861_a(StructurePiece var1, List<StructurePiece> var2, Random var3) {
         if (☃ != null) {
            ((StrongholdPieces.Stairs2)☃).field_75025_b = this;
         }
      }

      public static StrongholdPieces.PortalRoom func_175865_a(List<StructurePiece> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, -4, -1, 0, 11, 8, 16, ☃);
         return func_74991_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new StrongholdPieces.PortalRoom(☃, ☃, ☃, ☃) : null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         this.func_74882_a(☃, ☃, 0, 0, 0, 10, 7, 15, false, ☃, StrongholdPieces.field_75204_e);
         this.func_74990_a(☃, ☃, ☃, StrongholdPieces.Stronghold.Door.GRATES, 4, 1, 0);
         int ☃ = 6;
         this.func_74882_a(☃, ☃, 1, ☃, 1, 1, ☃, 14, false, ☃, StrongholdPieces.field_75204_e);
         this.func_74882_a(☃, ☃, 9, ☃, 1, 9, ☃, 14, false, ☃, StrongholdPieces.field_75204_e);
         this.func_74882_a(☃, ☃, 2, ☃, 1, 8, ☃, 2, false, ☃, StrongholdPieces.field_75204_e);
         this.func_74882_a(☃, ☃, 2, ☃, 14, 8, ☃, 14, false, ☃, StrongholdPieces.field_75204_e);
         this.func_74882_a(☃, ☃, 1, 1, 1, 2, 1, 4, false, ☃, StrongholdPieces.field_75204_e);
         this.func_74882_a(☃, ☃, 8, 1, 1, 9, 1, 4, false, ☃, StrongholdPieces.field_75204_e);
         this.func_175804_a(☃, ☃, 1, 1, 1, 1, 1, 3, Blocks.field_150353_l.func_176223_P(), Blocks.field_150353_l.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 9, 1, 1, 9, 1, 3, Blocks.field_150353_l.func_176223_P(), Blocks.field_150353_l.func_176223_P(), false);
         this.func_74882_a(☃, ☃, 3, 1, 8, 7, 1, 12, false, ☃, StrongholdPieces.field_75204_e);
         this.func_175804_a(☃, ☃, 4, 1, 9, 6, 1, 11, Blocks.field_150353_l.func_176223_P(), Blocks.field_150353_l.func_176223_P(), false);
         IBlockState ☃x = Blocks.field_150411_aY
            .func_176223_P()
            .func_206870_a(BlockPane.field_196409_a, Boolean.valueOf(true))
            .func_206870_a(BlockPane.field_196413_c, Boolean.valueOf(true));
         IBlockState ☃xx = Blocks.field_150411_aY
            .func_176223_P()
            .func_206870_a(BlockPane.field_196414_y, Boolean.valueOf(true))
            .func_206870_a(BlockPane.field_196411_b, Boolean.valueOf(true));

         for(int ☃xxx = 3; ☃xxx < 14; ☃xxx += 2) {
            this.func_175804_a(☃, ☃, 0, 3, ☃xxx, 0, 4, ☃xxx, ☃x, ☃x, false);
            this.func_175804_a(☃, ☃, 10, 3, ☃xxx, 10, 4, ☃xxx, ☃x, ☃x, false);
         }

         for(int ☃xxx = 2; ☃xxx < 9; ☃xxx += 2) {
            this.func_175804_a(☃, ☃, ☃xxx, 3, 15, ☃xxx, 4, 15, ☃xx, ☃xx, false);
         }

         IBlockState ☃xxx = Blocks.field_150390_bg.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.NORTH);
         this.func_74882_a(☃, ☃, 4, 1, 5, 6, 1, 7, false, ☃, StrongholdPieces.field_75204_e);
         this.func_74882_a(☃, ☃, 4, 2, 6, 6, 2, 7, false, ☃, StrongholdPieces.field_75204_e);
         this.func_74882_a(☃, ☃, 4, 3, 7, 6, 3, 7, false, ☃, StrongholdPieces.field_75204_e);

         for(int ☃xxxx = 4; ☃xxxx <= 6; ++☃xxxx) {
            this.func_175811_a(☃, ☃xxx, ☃xxxx, 1, 4, ☃);
            this.func_175811_a(☃, ☃xxx, ☃xxxx, 2, 5, ☃);
            this.func_175811_a(☃, ☃xxx, ☃xxxx, 3, 6, ☃);
         }

         IBlockState ☃xxxx = Blocks.field_150378_br.func_176223_P().func_206870_a(BlockEndPortalFrame.field_176508_a, EnumFacing.NORTH);
         IBlockState ☃xxxxx = Blocks.field_150378_br.func_176223_P().func_206870_a(BlockEndPortalFrame.field_176508_a, EnumFacing.SOUTH);
         IBlockState ☃xxxxxx = Blocks.field_150378_br.func_176223_P().func_206870_a(BlockEndPortalFrame.field_176508_a, EnumFacing.EAST);
         IBlockState ☃xxxxxxx = Blocks.field_150378_br.func_176223_P().func_206870_a(BlockEndPortalFrame.field_176508_a, EnumFacing.WEST);
         boolean ☃xxxxxxxx = true;
         boolean[] ☃xxxxxxxxx = new boolean[12];

         for(int ☃xxxxxxxxxx = 0; ☃xxxxxxxxxx < ☃xxxxxxxxx.length; ++☃xxxxxxxxxx) {
            ☃xxxxxxxxx[☃xxxxxxxxxx] = ☃.nextFloat() > 0.9F;
            ☃xxxxxxxx &= ☃xxxxxxxxx[☃xxxxxxxxxx];
         }

         this.func_175811_a(☃, ☃xxxx.func_206870_a(BlockEndPortalFrame.field_176507_b, Boolean.valueOf(☃xxxxxxxxx[0])), 4, 3, 8, ☃);
         this.func_175811_a(☃, ☃xxxx.func_206870_a(BlockEndPortalFrame.field_176507_b, Boolean.valueOf(☃xxxxxxxxx[1])), 5, 3, 8, ☃);
         this.func_175811_a(☃, ☃xxxx.func_206870_a(BlockEndPortalFrame.field_176507_b, Boolean.valueOf(☃xxxxxxxxx[2])), 6, 3, 8, ☃);
         this.func_175811_a(☃, ☃xxxxx.func_206870_a(BlockEndPortalFrame.field_176507_b, Boolean.valueOf(☃xxxxxxxxx[3])), 4, 3, 12, ☃);
         this.func_175811_a(☃, ☃xxxxx.func_206870_a(BlockEndPortalFrame.field_176507_b, Boolean.valueOf(☃xxxxxxxxx[4])), 5, 3, 12, ☃);
         this.func_175811_a(☃, ☃xxxxx.func_206870_a(BlockEndPortalFrame.field_176507_b, Boolean.valueOf(☃xxxxxxxxx[5])), 6, 3, 12, ☃);
         this.func_175811_a(☃, ☃xxxxxx.func_206870_a(BlockEndPortalFrame.field_176507_b, Boolean.valueOf(☃xxxxxxxxx[6])), 3, 3, 9, ☃);
         this.func_175811_a(☃, ☃xxxxxx.func_206870_a(BlockEndPortalFrame.field_176507_b, Boolean.valueOf(☃xxxxxxxxx[7])), 3, 3, 10, ☃);
         this.func_175811_a(☃, ☃xxxxxx.func_206870_a(BlockEndPortalFrame.field_176507_b, Boolean.valueOf(☃xxxxxxxxx[8])), 3, 3, 11, ☃);
         this.func_175811_a(☃, ☃xxxxxxx.func_206870_a(BlockEndPortalFrame.field_176507_b, Boolean.valueOf(☃xxxxxxxxx[9])), 7, 3, 9, ☃);
         this.func_175811_a(☃, ☃xxxxxxx.func_206870_a(BlockEndPortalFrame.field_176507_b, Boolean.valueOf(☃xxxxxxxxx[10])), 7, 3, 10, ☃);
         this.func_175811_a(☃, ☃xxxxxxx.func_206870_a(BlockEndPortalFrame.field_176507_b, Boolean.valueOf(☃xxxxxxxxx[11])), 7, 3, 11, ☃);
         if (☃xxxxxxxx) {
            IBlockState ☃xxxxxxxxxx = Blocks.field_150384_bq.func_176223_P();
            this.func_175811_a(☃, ☃xxxxxxxxxx, 4, 3, 9, ☃);
            this.func_175811_a(☃, ☃xxxxxxxxxx, 5, 3, 9, ☃);
            this.func_175811_a(☃, ☃xxxxxxxxxx, 6, 3, 9, ☃);
            this.func_175811_a(☃, ☃xxxxxxxxxx, 4, 3, 10, ☃);
            this.func_175811_a(☃, ☃xxxxxxxxxx, 5, 3, 10, ☃);
            this.func_175811_a(☃, ☃xxxxxxxxxx, 6, 3, 10, ☃);
            this.func_175811_a(☃, ☃xxxxxxxxxx, 4, 3, 11, ☃);
            this.func_175811_a(☃, ☃xxxxxxxxxx, 5, 3, 11, ☃);
            this.func_175811_a(☃, ☃xxxxxxxxxx, 6, 3, 11, ☃);
         }

         if (!this.field_75005_a) {
            ☃ = this.func_74862_a(3);
            BlockPos ☃xxxxxxxxxx = new BlockPos(this.func_74865_a(5, 6), ☃, this.func_74873_b(5, 6));
            if (☃.func_175898_b(☃xxxxxxxxxx)) {
               this.field_75005_a = true;
               ☃.func_180501_a(☃xxxxxxxxxx, Blocks.field_150474_ac.func_176223_P(), 2);
               TileEntity ☃xxxxxxxxxxx = ☃.func_175625_s(☃xxxxxxxxxx);
               if (☃xxxxxxxxxxx instanceof TileEntityMobSpawner) {
                  ((TileEntityMobSpawner)☃xxxxxxxxxxx).func_145881_a().func_200876_a(EntityType.field_200740_af);
               }
            }
         }

         return true;
      }
   }

   public static class Prison extends StrongholdPieces.Stronghold {
      public Prison() {
      }

      public Prison(int var1, Random var2, MutableBoundingBox var3, EnumFacing var4) {
         super(☃);
         this.func_186164_a(☃);
         this.field_143013_d = this.func_74988_a(☃);
         this.field_74887_e = ☃;
      }

      @Override
      public void func_74861_a(StructurePiece var1, List<StructurePiece> var2, Random var3) {
         this.func_74986_a((StrongholdPieces.Stairs2)☃, ☃, ☃, 1, 1);
      }

      public static StrongholdPieces.Prison func_175860_a(List<StructurePiece> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, -1, -1, 0, 9, 5, 11, ☃);
         return func_74991_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new StrongholdPieces.Prison(☃, ☃, ☃, ☃) : null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         this.func_74882_a(☃, ☃, 0, 0, 0, 8, 4, 10, true, ☃, StrongholdPieces.field_75204_e);
         this.func_74990_a(☃, ☃, ☃, this.field_143013_d, 1, 1, 0);
         this.func_175804_a(☃, ☃, 1, 1, 10, 3, 3, 10, field_202556_l, field_202556_l, false);
         this.func_74882_a(☃, ☃, 4, 1, 1, 4, 3, 1, false, ☃, StrongholdPieces.field_75204_e);
         this.func_74882_a(☃, ☃, 4, 1, 3, 4, 3, 3, false, ☃, StrongholdPieces.field_75204_e);
         this.func_74882_a(☃, ☃, 4, 1, 7, 4, 3, 7, false, ☃, StrongholdPieces.field_75204_e);
         this.func_74882_a(☃, ☃, 4, 1, 9, 4, 3, 9, false, ☃, StrongholdPieces.field_75204_e);

         for(int ☃ = 1; ☃ <= 3; ++☃) {
            this.func_175811_a(
               ☃,
               Blocks.field_150411_aY
                  .func_176223_P()
                  .func_206870_a(BlockPane.field_196409_a, Boolean.valueOf(true))
                  .func_206870_a(BlockPane.field_196413_c, Boolean.valueOf(true)),
               4,
               ☃,
               4,
               ☃
            );
            this.func_175811_a(
               ☃,
               Blocks.field_150411_aY
                  .func_176223_P()
                  .func_206870_a(BlockPane.field_196409_a, Boolean.valueOf(true))
                  .func_206870_a(BlockPane.field_196413_c, Boolean.valueOf(true))
                  .func_206870_a(BlockPane.field_196411_b, Boolean.valueOf(true)),
               4,
               ☃,
               5,
               ☃
            );
            this.func_175811_a(
               ☃,
               Blocks.field_150411_aY
                  .func_176223_P()
                  .func_206870_a(BlockPane.field_196409_a, Boolean.valueOf(true))
                  .func_206870_a(BlockPane.field_196413_c, Boolean.valueOf(true)),
               4,
               ☃,
               6,
               ☃
            );
            this.func_175811_a(
               ☃,
               Blocks.field_150411_aY
                  .func_176223_P()
                  .func_206870_a(BlockPane.field_196414_y, Boolean.valueOf(true))
                  .func_206870_a(BlockPane.field_196411_b, Boolean.valueOf(true)),
               5,
               ☃,
               5,
               ☃
            );
            this.func_175811_a(
               ☃,
               Blocks.field_150411_aY
                  .func_176223_P()
                  .func_206870_a(BlockPane.field_196414_y, Boolean.valueOf(true))
                  .func_206870_a(BlockPane.field_196411_b, Boolean.valueOf(true)),
               6,
               ☃,
               5,
               ☃
            );
            this.func_175811_a(
               ☃,
               Blocks.field_150411_aY
                  .func_176223_P()
                  .func_206870_a(BlockPane.field_196414_y, Boolean.valueOf(true))
                  .func_206870_a(BlockPane.field_196411_b, Boolean.valueOf(true)),
               7,
               ☃,
               5,
               ☃
            );
         }

         this.func_175811_a(
            ☃,
            Blocks.field_150411_aY
               .func_176223_P()
               .func_206870_a(BlockPane.field_196409_a, Boolean.valueOf(true))
               .func_206870_a(BlockPane.field_196413_c, Boolean.valueOf(true)),
            4,
            3,
            2,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150411_aY
               .func_176223_P()
               .func_206870_a(BlockPane.field_196409_a, Boolean.valueOf(true))
               .func_206870_a(BlockPane.field_196413_c, Boolean.valueOf(true)),
            4,
            3,
            8,
            ☃
         );
         IBlockState ☃ = Blocks.field_150454_av.func_176223_P().func_206870_a(BlockDoor.field_176520_a, EnumFacing.WEST);
         IBlockState ☃x = Blocks.field_150454_av
            .func_176223_P()
            .func_206870_a(BlockDoor.field_176520_a, EnumFacing.WEST)
            .func_206870_a(BlockDoor.field_176523_O, DoubleBlockHalf.UPPER);
         this.func_175811_a(☃, ☃, 4, 1, 2, ☃);
         this.func_175811_a(☃, ☃x, 4, 2, 2, ☃);
         this.func_175811_a(☃, ☃, 4, 1, 8, ☃);
         this.func_175811_a(☃, ☃x, 4, 2, 8, ☃);
         return true;
      }
   }

   public static class RightTurn extends StrongholdPieces.LeftTurn {
      @Override
      public void func_74861_a(StructurePiece var1, List<StructurePiece> var2, Random var3) {
         EnumFacing ☃ = this.func_186165_e();
         if (☃ != EnumFacing.NORTH && ☃ != EnumFacing.EAST) {
            this.func_74989_b((StrongholdPieces.Stairs2)☃, ☃, ☃, 1, 1);
         } else {
            this.func_74987_c((StrongholdPieces.Stairs2)☃, ☃, ☃, 1, 1);
         }
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         this.func_74882_a(☃, ☃, 0, 0, 0, 4, 4, 4, true, ☃, StrongholdPieces.field_75204_e);
         this.func_74990_a(☃, ☃, ☃, this.field_143013_d, 1, 1, 0);
         EnumFacing ☃ = this.func_186165_e();
         if (☃ != EnumFacing.NORTH && ☃ != EnumFacing.EAST) {
            this.func_175804_a(☃, ☃, 0, 1, 1, 0, 3, 3, field_202556_l, field_202556_l, false);
         } else {
            this.func_175804_a(☃, ☃, 4, 1, 1, 4, 3, 3, field_202556_l, field_202556_l, false);
         }

         return true;
      }
   }

   public static class RoomCrossing extends StrongholdPieces.Stronghold {
      protected int field_75013_b;

      public RoomCrossing() {
      }

      public RoomCrossing(int var1, Random var2, MutableBoundingBox var3, EnumFacing var4) {
         super(☃);
         this.func_186164_a(☃);
         this.field_143013_d = this.func_74988_a(☃);
         this.field_74887_e = ☃;
         this.field_75013_b = ☃.nextInt(5);
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
         super.func_143012_a(☃);
         ☃.func_74768_a("Type", this.field_75013_b);
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
         super.func_143011_b(☃, ☃);
         this.field_75013_b = ☃.func_74762_e("Type");
      }

      @Override
      public void func_74861_a(StructurePiece var1, List<StructurePiece> var2, Random var3) {
         this.func_74986_a((StrongholdPieces.Stairs2)☃, ☃, ☃, 4, 1);
         this.func_74989_b((StrongholdPieces.Stairs2)☃, ☃, ☃, 1, 4);
         this.func_74987_c((StrongholdPieces.Stairs2)☃, ☃, ☃, 1, 4);
      }

      public static StrongholdPieces.RoomCrossing func_175859_a(List<StructurePiece> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, -4, -1, 0, 11, 7, 11, ☃);
         return func_74991_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new StrongholdPieces.RoomCrossing(☃, ☃, ☃, ☃) : null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         this.func_74882_a(☃, ☃, 0, 0, 0, 10, 6, 10, true, ☃, StrongholdPieces.field_75204_e);
         this.func_74990_a(☃, ☃, ☃, this.field_143013_d, 4, 1, 0);
         this.func_175804_a(☃, ☃, 4, 1, 10, 6, 3, 10, field_202556_l, field_202556_l, false);
         this.func_175804_a(☃, ☃, 0, 1, 4, 0, 3, 6, field_202556_l, field_202556_l, false);
         this.func_175804_a(☃, ☃, 10, 1, 4, 10, 3, 6, field_202556_l, field_202556_l, false);
         switch(this.field_75013_b) {
            case 0:
               this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 5, 1, 5, ☃);
               this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 5, 2, 5, ☃);
               this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 5, 3, 5, ☃);
               this.func_175811_a(☃, Blocks.field_196591_bQ.func_176223_P().func_206870_a(BlockTorchWall.field_196532_a, EnumFacing.WEST), 4, 3, 5, ☃);
               this.func_175811_a(☃, Blocks.field_196591_bQ.func_176223_P().func_206870_a(BlockTorchWall.field_196532_a, EnumFacing.EAST), 6, 3, 5, ☃);
               this.func_175811_a(☃, Blocks.field_196591_bQ.func_176223_P().func_206870_a(BlockTorchWall.field_196532_a, EnumFacing.SOUTH), 5, 3, 4, ☃);
               this.func_175811_a(☃, Blocks.field_196591_bQ.func_176223_P().func_206870_a(BlockTorchWall.field_196532_a, EnumFacing.NORTH), 5, 3, 6, ☃);
               this.func_175811_a(☃, Blocks.field_150333_U.func_176223_P(), 4, 1, 4, ☃);
               this.func_175811_a(☃, Blocks.field_150333_U.func_176223_P(), 4, 1, 5, ☃);
               this.func_175811_a(☃, Blocks.field_150333_U.func_176223_P(), 4, 1, 6, ☃);
               this.func_175811_a(☃, Blocks.field_150333_U.func_176223_P(), 6, 1, 4, ☃);
               this.func_175811_a(☃, Blocks.field_150333_U.func_176223_P(), 6, 1, 5, ☃);
               this.func_175811_a(☃, Blocks.field_150333_U.func_176223_P(), 6, 1, 6, ☃);
               this.func_175811_a(☃, Blocks.field_150333_U.func_176223_P(), 5, 1, 4, ☃);
               this.func_175811_a(☃, Blocks.field_150333_U.func_176223_P(), 5, 1, 6, ☃);
               break;
            case 1:
               for(int ☃ = 0; ☃ < 5; ++☃) {
                  this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 3, 1, 3 + ☃, ☃);
                  this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 7, 1, 3 + ☃, ☃);
                  this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 3 + ☃, 1, 3, ☃);
                  this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 3 + ☃, 1, 7, ☃);
               }

               this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 5, 1, 5, ☃);
               this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 5, 2, 5, ☃);
               this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 5, 3, 5, ☃);
               this.func_175811_a(☃, Blocks.field_150355_j.func_176223_P(), 5, 4, 5, ☃);
               break;
            case 2:
               for(int ☃ = 1; ☃ <= 9; ++☃) {
                  this.func_175811_a(☃, Blocks.field_150347_e.func_176223_P(), 1, 3, ☃, ☃);
                  this.func_175811_a(☃, Blocks.field_150347_e.func_176223_P(), 9, 3, ☃, ☃);
               }

               for(int ☃ = 1; ☃ <= 9; ++☃) {
                  this.func_175811_a(☃, Blocks.field_150347_e.func_176223_P(), ☃, 3, 1, ☃);
                  this.func_175811_a(☃, Blocks.field_150347_e.func_176223_P(), ☃, 3, 9, ☃);
               }

               this.func_175811_a(☃, Blocks.field_150347_e.func_176223_P(), 5, 1, 4, ☃);
               this.func_175811_a(☃, Blocks.field_150347_e.func_176223_P(), 5, 1, 6, ☃);
               this.func_175811_a(☃, Blocks.field_150347_e.func_176223_P(), 5, 3, 4, ☃);
               this.func_175811_a(☃, Blocks.field_150347_e.func_176223_P(), 5, 3, 6, ☃);
               this.func_175811_a(☃, Blocks.field_150347_e.func_176223_P(), 4, 1, 5, ☃);
               this.func_175811_a(☃, Blocks.field_150347_e.func_176223_P(), 6, 1, 5, ☃);
               this.func_175811_a(☃, Blocks.field_150347_e.func_176223_P(), 4, 3, 5, ☃);
               this.func_175811_a(☃, Blocks.field_150347_e.func_176223_P(), 6, 3, 5, ☃);

               for(int ☃ = 1; ☃ <= 3; ++☃) {
                  this.func_175811_a(☃, Blocks.field_150347_e.func_176223_P(), 4, ☃, 4, ☃);
                  this.func_175811_a(☃, Blocks.field_150347_e.func_176223_P(), 6, ☃, 4, ☃);
                  this.func_175811_a(☃, Blocks.field_150347_e.func_176223_P(), 4, ☃, 6, ☃);
                  this.func_175811_a(☃, Blocks.field_150347_e.func_176223_P(), 6, ☃, 6, ☃);
               }

               this.func_175811_a(☃, Blocks.field_150478_aa.func_176223_P(), 5, 3, 5, ☃);

               for(int ☃ = 2; ☃ <= 8; ++☃) {
                  this.func_175811_a(☃, Blocks.field_196662_n.func_176223_P(), 2, 3, ☃, ☃);
                  this.func_175811_a(☃, Blocks.field_196662_n.func_176223_P(), 3, 3, ☃, ☃);
                  if (☃ <= 3 || ☃ >= 7) {
                     this.func_175811_a(☃, Blocks.field_196662_n.func_176223_P(), 4, 3, ☃, ☃);
                     this.func_175811_a(☃, Blocks.field_196662_n.func_176223_P(), 5, 3, ☃, ☃);
                     this.func_175811_a(☃, Blocks.field_196662_n.func_176223_P(), 6, 3, ☃, ☃);
                  }

                  this.func_175811_a(☃, Blocks.field_196662_n.func_176223_P(), 7, 3, ☃, ☃);
                  this.func_175811_a(☃, Blocks.field_196662_n.func_176223_P(), 8, 3, ☃, ☃);
               }

               IBlockState ☃ = Blocks.field_150468_ap.func_176223_P().func_206870_a(BlockLadder.field_176382_a, EnumFacing.WEST);
               this.func_175811_a(☃, ☃, 9, 1, 3, ☃);
               this.func_175811_a(☃, ☃, 9, 2, 3, ☃);
               this.func_175811_a(☃, ☃, 9, 3, 3, ☃);
               this.func_186167_a(☃, ☃, ☃, 3, 4, 8, LootTableList.field_186427_i);
         }

         return true;
      }
   }

   public static class Stairs extends StrongholdPieces.Stronghold {
      private boolean field_75024_a;

      public Stairs() {
      }

      public Stairs(int var1, Random var2, int var3, int var4) {
         super(☃);
         this.field_75024_a = true;
         this.func_186164_a(EnumFacing.Plane.HORIZONTAL.func_179518_a(☃));
         this.field_143013_d = StrongholdPieces.Stronghold.Door.OPENING;
         if (this.func_186165_e().func_176740_k() == EnumFacing.Axis.Z) {
            this.field_74887_e = new MutableBoundingBox(☃, 64, ☃, ☃ + 5 - 1, 74, ☃ + 5 - 1);
         } else {
            this.field_74887_e = new MutableBoundingBox(☃, 64, ☃, ☃ + 5 - 1, 74, ☃ + 5 - 1);
         }
      }

      public Stairs(int var1, Random var2, MutableBoundingBox var3, EnumFacing var4) {
         super(☃);
         this.field_75024_a = false;
         this.func_186164_a(☃);
         this.field_143013_d = this.func_74988_a(☃);
         this.field_74887_e = ☃;
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
         super.func_143012_a(☃);
         ☃.func_74757_a("Source", this.field_75024_a);
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
         super.func_143011_b(☃, ☃);
         this.field_75024_a = ☃.func_74767_n("Source");
      }

      @Override
      public void func_74861_a(StructurePiece var1, List<StructurePiece> var2, Random var3) {
         if (this.field_75024_a) {
            StrongholdPieces.field_75203_d = StrongholdPieces.Crossing.class;
         }

         this.func_74986_a((StrongholdPieces.Stairs2)☃, ☃, ☃, 1, 1);
      }

      public static StrongholdPieces.Stairs func_175863_a(List<StructurePiece> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, -1, -7, 0, 5, 11, 5, ☃);
         return func_74991_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new StrongholdPieces.Stairs(☃, ☃, ☃, ☃) : null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         this.func_74882_a(☃, ☃, 0, 0, 0, 4, 10, 4, true, ☃, StrongholdPieces.field_75204_e);
         this.func_74990_a(☃, ☃, ☃, this.field_143013_d, 1, 7, 0);
         this.func_74990_a(☃, ☃, ☃, StrongholdPieces.Stronghold.Door.OPENING, 1, 1, 4);
         this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 2, 6, 1, ☃);
         this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 1, 5, 1, ☃);
         this.func_175811_a(☃, Blocks.field_150333_U.func_176223_P(), 1, 6, 1, ☃);
         this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 1, 5, 2, ☃);
         this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 1, 4, 3, ☃);
         this.func_175811_a(☃, Blocks.field_150333_U.func_176223_P(), 1, 5, 3, ☃);
         this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 2, 4, 3, ☃);
         this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 3, 3, 3, ☃);
         this.func_175811_a(☃, Blocks.field_150333_U.func_176223_P(), 3, 4, 3, ☃);
         this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 3, 3, 2, ☃);
         this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 3, 2, 1, ☃);
         this.func_175811_a(☃, Blocks.field_150333_U.func_176223_P(), 3, 3, 1, ☃);
         this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 2, 2, 1, ☃);
         this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 1, 1, 1, ☃);
         this.func_175811_a(☃, Blocks.field_150333_U.func_176223_P(), 1, 2, 1, ☃);
         this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 1, 1, 2, ☃);
         this.func_175811_a(☃, Blocks.field_150333_U.func_176223_P(), 1, 1, 3, ☃);
         return true;
      }
   }

   public static class Stairs2 extends StrongholdPieces.Stairs {
      public StrongholdPieces.PieceWeight field_75027_a;
      public StrongholdPieces.PortalRoom field_75025_b;
      public List<StructurePiece> field_75026_c = Lists.<StructurePiece>newArrayList();

      public Stairs2() {
      }

      public Stairs2(int var1, Random var2, int var3, int var4) {
         super(0, ☃, ☃, ☃);
      }
   }

   public static class StairsStraight extends StrongholdPieces.Stronghold {
      public StairsStraight() {
      }

      public StairsStraight(int var1, Random var2, MutableBoundingBox var3, EnumFacing var4) {
         super(☃);
         this.func_186164_a(☃);
         this.field_143013_d = this.func_74988_a(☃);
         this.field_74887_e = ☃;
      }

      @Override
      public void func_74861_a(StructurePiece var1, List<StructurePiece> var2, Random var3) {
         this.func_74986_a((StrongholdPieces.Stairs2)☃, ☃, ☃, 1, 1);
      }

      public static StrongholdPieces.StairsStraight func_175861_a(
         List<StructurePiece> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6
      ) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, -1, -7, 0, 5, 11, 8, ☃);
         return func_74991_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new StrongholdPieces.StairsStraight(☃, ☃, ☃, ☃) : null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         this.func_74882_a(☃, ☃, 0, 0, 0, 4, 10, 7, true, ☃, StrongholdPieces.field_75204_e);
         this.func_74990_a(☃, ☃, ☃, this.field_143013_d, 1, 7, 0);
         this.func_74990_a(☃, ☃, ☃, StrongholdPieces.Stronghold.Door.OPENING, 1, 1, 7);
         IBlockState ☃ = Blocks.field_196659_cl.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.SOUTH);

         for(int ☃x = 0; ☃x < 6; ++☃x) {
            this.func_175811_a(☃, ☃, 1, 6 - ☃x, 1 + ☃x, ☃);
            this.func_175811_a(☃, ☃, 2, 6 - ☃x, 1 + ☃x, ☃);
            this.func_175811_a(☃, ☃, 3, 6 - ☃x, 1 + ☃x, ☃);
            if (☃x < 5) {
               this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 1, 5 - ☃x, 1 + ☃x, ☃);
               this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 2, 5 - ☃x, 1 + ☃x, ☃);
               this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), 3, 5 - ☃x, 1 + ☃x, ☃);
            }
         }

         return true;
      }
   }

   static class Stones extends StructurePiece.BlockSelector {
      private Stones() {
      }

      @Override
      public void func_75062_a(Random var1, int var2, int var3, int var4, boolean var5) {
         if (☃) {
            float ☃ = ☃.nextFloat();
            if (☃ < 0.2F) {
               this.field_151562_a = Blocks.field_196700_dk.func_176223_P();
            } else if (☃ < 0.5F) {
               this.field_151562_a = Blocks.field_196698_dj.func_176223_P();
            } else if (☃ < 0.55F) {
               this.field_151562_a = Blocks.field_196688_de.func_176223_P();
            } else {
               this.field_151562_a = Blocks.field_196696_di.func_176223_P();
            }
         } else {
            this.field_151562_a = Blocks.field_201941_jj.func_176223_P();
         }
      }
   }

   public static class Straight extends StrongholdPieces.Stronghold {
      private boolean field_75019_b;
      private boolean field_75020_c;

      public Straight() {
      }

      public Straight(int var1, Random var2, MutableBoundingBox var3, EnumFacing var4) {
         super(☃);
         this.func_186164_a(☃);
         this.field_143013_d = this.func_74988_a(☃);
         this.field_74887_e = ☃;
         this.field_75019_b = ☃.nextInt(2) == 0;
         this.field_75020_c = ☃.nextInt(2) == 0;
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
         super.func_143012_a(☃);
         ☃.func_74757_a("Left", this.field_75019_b);
         ☃.func_74757_a("Right", this.field_75020_c);
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
         super.func_143011_b(☃, ☃);
         this.field_75019_b = ☃.func_74767_n("Left");
         this.field_75020_c = ☃.func_74767_n("Right");
      }

      @Override
      public void func_74861_a(StructurePiece var1, List<StructurePiece> var2, Random var3) {
         this.func_74986_a((StrongholdPieces.Stairs2)☃, ☃, ☃, 1, 1);
         if (this.field_75019_b) {
            this.func_74989_b((StrongholdPieces.Stairs2)☃, ☃, ☃, 1, 2);
         }

         if (this.field_75020_c) {
            this.func_74987_c((StrongholdPieces.Stairs2)☃, ☃, ☃, 1, 2);
         }
      }

      public static StrongholdPieces.Straight func_175862_a(List<StructurePiece> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, -1, -1, 0, 5, 5, 7, ☃);
         return func_74991_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new StrongholdPieces.Straight(☃, ☃, ☃, ☃) : null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         this.func_74882_a(☃, ☃, 0, 0, 0, 4, 4, 6, true, ☃, StrongholdPieces.field_75204_e);
         this.func_74990_a(☃, ☃, ☃, this.field_143013_d, 1, 1, 0);
         this.func_74990_a(☃, ☃, ☃, StrongholdPieces.Stronghold.Door.OPENING, 1, 1, 6);
         IBlockState ☃ = Blocks.field_196591_bQ.func_176223_P().func_206870_a(BlockTorchWall.field_196532_a, EnumFacing.EAST);
         IBlockState ☃x = Blocks.field_196591_bQ.func_176223_P().func_206870_a(BlockTorchWall.field_196532_a, EnumFacing.WEST);
         this.func_175809_a(☃, ☃, ☃, 0.1F, 1, 2, 1, ☃);
         this.func_175809_a(☃, ☃, ☃, 0.1F, 3, 2, 1, ☃x);
         this.func_175809_a(☃, ☃, ☃, 0.1F, 1, 2, 5, ☃);
         this.func_175809_a(☃, ☃, ☃, 0.1F, 3, 2, 5, ☃x);
         if (this.field_75019_b) {
            this.func_175804_a(☃, ☃, 0, 1, 2, 0, 3, 4, field_202556_l, field_202556_l, false);
         }

         if (this.field_75020_c) {
            this.func_175804_a(☃, ☃, 4, 1, 2, 4, 3, 4, field_202556_l, field_202556_l, false);
         }

         return true;
      }
   }

   abstract static class Stronghold extends StructurePiece {
      protected StrongholdPieces.Stronghold.Door field_143013_d = StrongholdPieces.Stronghold.Door.OPENING;

      public Stronghold() {
      }

      protected Stronghold(int var1) {
         super(☃);
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
         ☃.func_74778_a("EntryDoor", this.field_143013_d.name());
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
         this.field_143013_d = StrongholdPieces.Stronghold.Door.valueOf(☃.func_74779_i("EntryDoor"));
      }

      protected void func_74990_a(IWorld var1, Random var2, MutableBoundingBox var3, StrongholdPieces.Stronghold.Door var4, int var5, int var6, int var7) {
         switch(☃) {
            case OPENING:
               this.func_175804_a(☃, ☃, ☃, ☃, ☃, ☃ + 3 - 1, ☃ + 3 - 1, ☃, field_202556_l, field_202556_l, false);
               break;
            case WOOD_DOOR:
               this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), ☃, ☃, ☃, ☃);
               this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), ☃, ☃ + 1, ☃, ☃);
               this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), ☃, ☃ + 2, ☃, ☃);
               this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), ☃ + 1, ☃ + 2, ☃, ☃);
               this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), ☃ + 2, ☃ + 2, ☃, ☃);
               this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), ☃ + 2, ☃ + 1, ☃, ☃);
               this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), ☃ + 2, ☃, ☃, ☃);
               this.func_175811_a(☃, Blocks.field_180413_ao.func_176223_P(), ☃ + 1, ☃, ☃, ☃);
               this.func_175811_a(☃, Blocks.field_180413_ao.func_176223_P().func_206870_a(BlockDoor.field_176523_O, DoubleBlockHalf.UPPER), ☃ + 1, ☃ + 1, ☃, ☃);
               break;
            case GRATES:
               this.func_175811_a(☃, Blocks.field_201941_jj.func_176223_P(), ☃ + 1, ☃, ☃, ☃);
               this.func_175811_a(☃, Blocks.field_201941_jj.func_176223_P(), ☃ + 1, ☃ + 1, ☃, ☃);
               this.func_175811_a(☃, Blocks.field_150411_aY.func_176223_P().func_206870_a(BlockPane.field_196414_y, Boolean.valueOf(true)), ☃, ☃, ☃, ☃);
               this.func_175811_a(☃, Blocks.field_150411_aY.func_176223_P().func_206870_a(BlockPane.field_196414_y, Boolean.valueOf(true)), ☃, ☃ + 1, ☃, ☃);
               this.func_175811_a(
                  ☃,
                  Blocks.field_150411_aY
                     .func_176223_P()
                     .func_206870_a(BlockPane.field_196411_b, Boolean.valueOf(true))
                     .func_206870_a(BlockPane.field_196414_y, Boolean.valueOf(true)),
                  ☃,
                  ☃ + 2,
                  ☃,
                  ☃
               );
               this.func_175811_a(
                  ☃,
                  Blocks.field_150411_aY
                     .func_176223_P()
                     .func_206870_a(BlockPane.field_196411_b, Boolean.valueOf(true))
                     .func_206870_a(BlockPane.field_196414_y, Boolean.valueOf(true)),
                  ☃ + 1,
                  ☃ + 2,
                  ☃,
                  ☃
               );
               this.func_175811_a(
                  ☃,
                  Blocks.field_150411_aY
                     .func_176223_P()
                     .func_206870_a(BlockPane.field_196411_b, Boolean.valueOf(true))
                     .func_206870_a(BlockPane.field_196414_y, Boolean.valueOf(true)),
                  ☃ + 2,
                  ☃ + 2,
                  ☃,
                  ☃
               );
               this.func_175811_a(☃, Blocks.field_150411_aY.func_176223_P().func_206870_a(BlockPane.field_196411_b, Boolean.valueOf(true)), ☃ + 2, ☃ + 1, ☃, ☃);
               this.func_175811_a(☃, Blocks.field_150411_aY.func_176223_P().func_206870_a(BlockPane.field_196411_b, Boolean.valueOf(true)), ☃ + 2, ☃, ☃, ☃);
               break;
            case IRON_DOOR:
               this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), ☃, ☃, ☃, ☃);
               this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), ☃, ☃ + 1, ☃, ☃);
               this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), ☃, ☃ + 2, ☃, ☃);
               this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), ☃ + 1, ☃ + 2, ☃, ☃);
               this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), ☃ + 2, ☃ + 2, ☃, ☃);
               this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), ☃ + 2, ☃ + 1, ☃, ☃);
               this.func_175811_a(☃, Blocks.field_196696_di.func_176223_P(), ☃ + 2, ☃, ☃, ☃);
               this.func_175811_a(☃, Blocks.field_150454_av.func_176223_P(), ☃ + 1, ☃, ☃, ☃);
               this.func_175811_a(☃, Blocks.field_150454_av.func_176223_P().func_206870_a(BlockDoor.field_176523_O, DoubleBlockHalf.UPPER), ☃ + 1, ☃ + 1, ☃, ☃);
               this.func_175811_a(☃, Blocks.field_150430_aB.func_176223_P().func_206870_a(BlockButton.field_185512_D, EnumFacing.NORTH), ☃ + 2, ☃ + 1, ☃ + 1, ☃);
               this.func_175811_a(☃, Blocks.field_150430_aB.func_176223_P().func_206870_a(BlockButton.field_185512_D, EnumFacing.SOUTH), ☃ + 2, ☃ + 1, ☃ - 1, ☃);
         }
      }

      protected StrongholdPieces.Stronghold.Door func_74988_a(Random var1) {
         int ☃ = ☃.nextInt(5);
         switch(☃) {
            case 0:
            case 1:
            default:
               return StrongholdPieces.Stronghold.Door.OPENING;
            case 2:
               return StrongholdPieces.Stronghold.Door.WOOD_DOOR;
            case 3:
               return StrongholdPieces.Stronghold.Door.GRATES;
            case 4:
               return StrongholdPieces.Stronghold.Door.IRON_DOOR;
         }
      }

      @Nullable
      protected StructurePiece func_74986_a(StrongholdPieces.Stairs2 var1, List<StructurePiece> var2, Random var3, int var4, int var5) {
         EnumFacing ☃ = this.func_186165_e();
         if (☃ != null) {
            switch(☃) {
               case NORTH:
                  return StrongholdPieces.func_175953_c(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78897_a + ☃,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78896_c - 1,
                     ☃,
                     this.func_74877_c()
                  );
               case SOUTH:
                  return StrongholdPieces.func_175953_c(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78897_a + ☃,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78892_f + 1,
                     ☃,
                     this.func_74877_c()
                  );
               case WEST:
                  return StrongholdPieces.func_175953_c(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78897_a - 1,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78896_c + ☃,
                     ☃,
                     this.func_74877_c()
                  );
               case EAST:
                  return StrongholdPieces.func_175953_c(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78893_d + 1,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78896_c + ☃,
                     ☃,
                     this.func_74877_c()
                  );
            }
         }

         return null;
      }

      @Nullable
      protected StructurePiece func_74989_b(StrongholdPieces.Stairs2 var1, List<StructurePiece> var2, Random var3, int var4, int var5) {
         EnumFacing ☃ = this.func_186165_e();
         if (☃ != null) {
            switch(☃) {
               case NORTH:
                  return StrongholdPieces.func_175953_c(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78897_a - 1,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78896_c + ☃,
                     EnumFacing.WEST,
                     this.func_74877_c()
                  );
               case SOUTH:
                  return StrongholdPieces.func_175953_c(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78897_a - 1,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78896_c + ☃,
                     EnumFacing.WEST,
                     this.func_74877_c()
                  );
               case WEST:
                  return StrongholdPieces.func_175953_c(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78897_a + ☃,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78896_c - 1,
                     EnumFacing.NORTH,
                     this.func_74877_c()
                  );
               case EAST:
                  return StrongholdPieces.func_175953_c(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78897_a + ☃,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78896_c - 1,
                     EnumFacing.NORTH,
                     this.func_74877_c()
                  );
            }
         }

         return null;
      }

      @Nullable
      protected StructurePiece func_74987_c(StrongholdPieces.Stairs2 var1, List<StructurePiece> var2, Random var3, int var4, int var5) {
         EnumFacing ☃ = this.func_186165_e();
         if (☃ != null) {
            switch(☃) {
               case NORTH:
                  return StrongholdPieces.func_175953_c(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78893_d + 1,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78896_c + ☃,
                     EnumFacing.EAST,
                     this.func_74877_c()
                  );
               case SOUTH:
                  return StrongholdPieces.func_175953_c(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78893_d + 1,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78896_c + ☃,
                     EnumFacing.EAST,
                     this.func_74877_c()
                  );
               case WEST:
                  return StrongholdPieces.func_175953_c(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78897_a + ☃,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78892_f + 1,
                     EnumFacing.SOUTH,
                     this.func_74877_c()
                  );
               case EAST:
                  return StrongholdPieces.func_175953_c(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78897_a + ☃,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78892_f + 1,
                     EnumFacing.SOUTH,
                     this.func_74877_c()
                  );
            }
         }

         return null;
      }

      protected static boolean func_74991_a(MutableBoundingBox var0) {
         return ☃ != null && ☃.field_78895_b > 10;
      }

      public static enum Door {
         OPENING,
         WOOD_DOOR,
         GRATES,
         IRON_DOOR;
      }
   }
}
