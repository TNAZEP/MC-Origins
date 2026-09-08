package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

public class FortressPieces {
   private static final FortressPieces.PieceWeight[] field_78742_a = new FortressPieces.PieceWeight[]{
      new FortressPieces.PieceWeight(FortressPieces.Straight.class, 30, 0, true),
      new FortressPieces.PieceWeight(FortressPieces.Crossing3.class, 10, 4),
      new FortressPieces.PieceWeight(FortressPieces.Crossing.class, 10, 4),
      new FortressPieces.PieceWeight(FortressPieces.Stairs.class, 10, 3),
      new FortressPieces.PieceWeight(FortressPieces.Throne.class, 5, 2),
      new FortressPieces.PieceWeight(FortressPieces.Entrance.class, 5, 1)
   };
   private static final FortressPieces.PieceWeight[] field_78741_b = new FortressPieces.PieceWeight[]{
      new FortressPieces.PieceWeight(FortressPieces.Corridor5.class, 25, 0, true),
      new FortressPieces.PieceWeight(FortressPieces.Crossing2.class, 15, 5),
      new FortressPieces.PieceWeight(FortressPieces.Corridor2.class, 5, 10),
      new FortressPieces.PieceWeight(FortressPieces.Corridor.class, 5, 10),
      new FortressPieces.PieceWeight(FortressPieces.Corridor3.class, 10, 3, true),
      new FortressPieces.PieceWeight(FortressPieces.Corridor4.class, 7, 2),
      new FortressPieces.PieceWeight(FortressPieces.NetherStalkRoom.class, 5, 2)
   };

   public static void func_143049_a() {
      StructureIO.func_143031_a(FortressPieces.Crossing3.class, "NeBCr");
      StructureIO.func_143031_a(FortressPieces.End.class, "NeBEF");
      StructureIO.func_143031_a(FortressPieces.Straight.class, "NeBS");
      StructureIO.func_143031_a(FortressPieces.Corridor3.class, "NeCCS");
      StructureIO.func_143031_a(FortressPieces.Corridor4.class, "NeCTB");
      StructureIO.func_143031_a(FortressPieces.Entrance.class, "NeCE");
      StructureIO.func_143031_a(FortressPieces.Crossing2.class, "NeSCSC");
      StructureIO.func_143031_a(FortressPieces.Corridor.class, "NeSCLT");
      StructureIO.func_143031_a(FortressPieces.Corridor5.class, "NeSC");
      StructureIO.func_143031_a(FortressPieces.Corridor2.class, "NeSCRT");
      StructureIO.func_143031_a(FortressPieces.NetherStalkRoom.class, "NeCSR");
      StructureIO.func_143031_a(FortressPieces.Throne.class, "NeMT");
      StructureIO.func_143031_a(FortressPieces.Crossing.class, "NeRC");
      StructureIO.func_143031_a(FortressPieces.Stairs.class, "NeSR");
      StructureIO.func_143031_a(FortressPieces.Start.class, "NeStart");
   }

   private static FortressPieces.Piece func_175887_b(
      FortressPieces.PieceWeight var0, List<StructurePiece> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
   ) {
      Class<? extends FortressPieces.Piece> ☃ = ☃.field_78828_a;
      FortressPieces.Piece ☃x = null;
      if (☃ == FortressPieces.Straight.class) {
         ☃x = FortressPieces.Straight.func_175882_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == FortressPieces.Crossing3.class) {
         ☃x = FortressPieces.Crossing3.func_175885_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == FortressPieces.Crossing.class) {
         ☃x = FortressPieces.Crossing.func_175873_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == FortressPieces.Stairs.class) {
         ☃x = FortressPieces.Stairs.func_175872_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == FortressPieces.Throne.class) {
         ☃x = FortressPieces.Throne.func_175874_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == FortressPieces.Entrance.class) {
         ☃x = FortressPieces.Entrance.func_175881_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == FortressPieces.Corridor5.class) {
         ☃x = FortressPieces.Corridor5.func_175877_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == FortressPieces.Corridor2.class) {
         ☃x = FortressPieces.Corridor2.func_175876_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == FortressPieces.Corridor.class) {
         ☃x = FortressPieces.Corridor.func_175879_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == FortressPieces.Corridor3.class) {
         ☃x = FortressPieces.Corridor3.func_175883_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == FortressPieces.Corridor4.class) {
         ☃x = FortressPieces.Corridor4.func_175880_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == FortressPieces.Crossing2.class) {
         ☃x = FortressPieces.Crossing2.func_175878_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == FortressPieces.NetherStalkRoom.class) {
         ☃x = FortressPieces.NetherStalkRoom.func_175875_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      }

      return ☃x;
   }

   public static class Corridor extends FortressPieces.Piece {
      private boolean field_111021_b;

      public Corridor() {
      }

      public Corridor(int var1, Random var2, MutableBoundingBox var3, EnumFacing var4) {
         super(☃);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
         this.field_111021_b = ☃.nextInt(3) == 0;
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
         super.func_143011_b(☃, ☃);
         this.field_111021_b = ☃.func_74767_n("Chest");
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
         super.func_143012_a(☃);
         ☃.func_74757_a("Chest", this.field_111021_b);
      }

      @Override
      public void func_74861_a(StructurePiece var1, List<StructurePiece> var2, Random var3) {
         this.func_74961_b((FortressPieces.Start)☃, ☃, ☃, 0, 1, true);
      }

      public static FortressPieces.Corridor func_175879_a(List<StructurePiece> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, -1, 0, 0, 5, 7, 5, ☃);
         return func_74964_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new FortressPieces.Corridor(☃, ☃, ☃, ☃) : null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         this.func_175804_a(☃, ☃, 0, 0, 0, 4, 1, 4, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 2, 0, 4, 5, 4, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         IBlockState ☃ = Blocks.field_150386_bk
            .func_176223_P()
            .func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true))
            .func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true));
         IBlockState ☃x = Blocks.field_150386_bk
            .func_176223_P()
            .func_206870_a(BlockFence.field_196409_a, Boolean.valueOf(true))
            .func_206870_a(BlockFence.field_196413_c, Boolean.valueOf(true));
         this.func_175804_a(☃, ☃, 4, 2, 0, 4, 5, 4, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 4, 3, 1, 4, 4, 1, ☃x, ☃x, false);
         this.func_175804_a(☃, ☃, 4, 3, 3, 4, 4, 3, ☃x, ☃x, false);
         this.func_175804_a(☃, ☃, 0, 2, 0, 0, 5, 0, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 2, 4, 3, 5, 4, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 3, 4, 1, 4, 4, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 3, 3, 4, 3, 4, 4, ☃, ☃, false);
         if (this.field_111021_b && ☃.func_175898_b(new BlockPos(this.func_74865_a(3, 3), this.func_74862_a(2), this.func_74873_b(3, 3)))) {
            this.field_111021_b = false;
            this.func_186167_a(☃, ☃, ☃, 3, 2, 3, LootTableList.field_186425_g);
         }

         this.func_175804_a(☃, ☃, 0, 6, 0, 4, 6, 4, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);

         for(int ☃ = 0; ☃ <= 4; ++☃) {
            for(int ☃x = 0; ☃x <= 4; ++☃x) {
               this.func_175808_b(☃, Blocks.field_196653_dH.func_176223_P(), ☃, -1, ☃x, ☃);
            }
         }

         return true;
      }
   }

   public static class Corridor2 extends FortressPieces.Piece {
      private boolean field_111020_b;

      public Corridor2() {
      }

      public Corridor2(int var1, Random var2, MutableBoundingBox var3, EnumFacing var4) {
         super(☃);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
         this.field_111020_b = ☃.nextInt(3) == 0;
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
         super.func_143011_b(☃, ☃);
         this.field_111020_b = ☃.func_74767_n("Chest");
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
         super.func_143012_a(☃);
         ☃.func_74757_a("Chest", this.field_111020_b);
      }

      @Override
      public void func_74861_a(StructurePiece var1, List<StructurePiece> var2, Random var3) {
         this.func_74965_c((FortressPieces.Start)☃, ☃, ☃, 0, 1, true);
      }

      public static FortressPieces.Corridor2 func_175876_a(List<StructurePiece> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, -1, 0, 0, 5, 7, 5, ☃);
         return func_74964_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new FortressPieces.Corridor2(☃, ☃, ☃, ☃) : null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         this.func_175804_a(☃, ☃, 0, 0, 0, 4, 1, 4, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 2, 0, 4, 5, 4, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         IBlockState ☃ = Blocks.field_150386_bk
            .func_176223_P()
            .func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true))
            .func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true));
         IBlockState ☃x = Blocks.field_150386_bk
            .func_176223_P()
            .func_206870_a(BlockFence.field_196409_a, Boolean.valueOf(true))
            .func_206870_a(BlockFence.field_196413_c, Boolean.valueOf(true));
         this.func_175804_a(☃, ☃, 0, 2, 0, 0, 5, 4, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 3, 1, 0, 4, 1, ☃x, ☃x, false);
         this.func_175804_a(☃, ☃, 0, 3, 3, 0, 4, 3, ☃x, ☃x, false);
         this.func_175804_a(☃, ☃, 4, 2, 0, 4, 5, 0, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 2, 4, 4, 5, 4, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 3, 4, 1, 4, 4, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 3, 3, 4, 3, 4, 4, ☃, ☃, false);
         if (this.field_111020_b && ☃.func_175898_b(new BlockPos(this.func_74865_a(1, 3), this.func_74862_a(2), this.func_74873_b(1, 3)))) {
            this.field_111020_b = false;
            this.func_186167_a(☃, ☃, ☃, 1, 2, 3, LootTableList.field_186425_g);
         }

         this.func_175804_a(☃, ☃, 0, 6, 0, 4, 6, 4, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);

         for(int ☃ = 0; ☃ <= 4; ++☃) {
            for(int ☃x = 0; ☃x <= 4; ++☃x) {
               this.func_175808_b(☃, Blocks.field_196653_dH.func_176223_P(), ☃, -1, ☃x, ☃);
            }
         }

         return true;
      }
   }

   public static class Corridor3 extends FortressPieces.Piece {
      public Corridor3() {
      }

      public Corridor3(int var1, Random var2, MutableBoundingBox var3, EnumFacing var4) {
         super(☃);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
      }

      @Override
      public void func_74861_a(StructurePiece var1, List<StructurePiece> var2, Random var3) {
         this.func_74963_a((FortressPieces.Start)☃, ☃, ☃, 1, 0, true);
      }

      public static FortressPieces.Corridor3 func_175883_a(List<StructurePiece> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, -1, -7, 0, 5, 14, 10, ☃);
         return func_74964_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new FortressPieces.Corridor3(☃, ☃, ☃, ☃) : null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         IBlockState ☃ = Blocks.field_150387_bl.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.SOUTH);
         IBlockState ☃x = Blocks.field_150386_bk
            .func_176223_P()
            .func_206870_a(BlockFence.field_196409_a, Boolean.valueOf(true))
            .func_206870_a(BlockFence.field_196413_c, Boolean.valueOf(true));

         for(int ☃xx = 0; ☃xx <= 9; ++☃xx) {
            int ☃xxx = Math.max(1, 7 - ☃xx);
            int ☃xxxx = Math.min(Math.max(☃xxx + 5, 14 - ☃xx), 13);
            int ☃xxxxx = ☃xx;
            this.func_175804_a(☃, ☃, 0, 0, ☃xx, 4, ☃xxx, ☃xx, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
            this.func_175804_a(☃, ☃, 1, ☃xxx + 1, ☃xx, 3, ☃xxxx - 1, ☃xx, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
            if (☃xx <= 6) {
               this.func_175811_a(☃, ☃, 1, ☃xxx + 1, ☃xx, ☃);
               this.func_175811_a(☃, ☃, 2, ☃xxx + 1, ☃xx, ☃);
               this.func_175811_a(☃, ☃, 3, ☃xxx + 1, ☃xx, ☃);
            }

            this.func_175804_a(☃, ☃, 0, ☃xxxx, ☃xx, 4, ☃xxxx, ☃xx, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
            this.func_175804_a(☃, ☃, 0, ☃xxx + 1, ☃xx, 0, ☃xxxx - 1, ☃xx, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
            this.func_175804_a(☃, ☃, 4, ☃xxx + 1, ☃xx, 4, ☃xxxx - 1, ☃xx, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
            if ((☃xx & 1) == 0) {
               this.func_175804_a(☃, ☃, 0, ☃xxx + 2, ☃xx, 0, ☃xxx + 3, ☃xx, ☃x, ☃x, false);
               this.func_175804_a(☃, ☃, 4, ☃xxx + 2, ☃xx, 4, ☃xxx + 3, ☃xx, ☃x, ☃x, false);
            }

            for(int ☃xxx = 0; ☃xxx <= 4; ++☃xxx) {
               this.func_175808_b(☃, Blocks.field_196653_dH.func_176223_P(), ☃xxx, -1, ☃xxxxx, ☃);
            }
         }

         return true;
      }
   }

   public static class Corridor4 extends FortressPieces.Piece {
      public Corridor4() {
      }

      public Corridor4(int var1, Random var2, MutableBoundingBox var3, EnumFacing var4) {
         super(☃);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
      }

      @Override
      public void func_74861_a(StructurePiece var1, List<StructurePiece> var2, Random var3) {
         int ☃ = 1;
         EnumFacing ☃x = this.func_186165_e();
         if (☃x == EnumFacing.WEST || ☃x == EnumFacing.NORTH) {
            ☃ = 5;
         }

         this.func_74961_b((FortressPieces.Start)☃, ☃, ☃, 0, ☃, ☃.nextInt(8) > 0);
         this.func_74965_c((FortressPieces.Start)☃, ☃, ☃, 0, ☃, ☃.nextInt(8) > 0);
      }

      public static FortressPieces.Corridor4 func_175880_a(List<StructurePiece> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, -3, 0, 0, 9, 7, 9, ☃);
         return func_74964_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new FortressPieces.Corridor4(☃, ☃, ☃, ☃) : null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         IBlockState ☃ = Blocks.field_150386_bk
            .func_176223_P()
            .func_206870_a(BlockFence.field_196409_a, Boolean.valueOf(true))
            .func_206870_a(BlockFence.field_196413_c, Boolean.valueOf(true));
         IBlockState ☃x = Blocks.field_150386_bk
            .func_176223_P()
            .func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true))
            .func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true));
         this.func_175804_a(☃, ☃, 0, 0, 0, 8, 1, 8, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 2, 0, 8, 5, 8, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 6, 0, 8, 6, 5, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 2, 0, 2, 5, 0, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 6, 2, 0, 8, 5, 0, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 3, 0, 1, 4, 0, ☃x, ☃x, false);
         this.func_175804_a(☃, ☃, 7, 3, 0, 7, 4, 0, ☃x, ☃x, false);
         this.func_175804_a(☃, ☃, 0, 2, 4, 8, 2, 8, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 1, 4, 2, 2, 4, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 6, 1, 4, 7, 2, 4, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 3, 8, 7, 3, 8, ☃x, ☃x, false);
         this.func_175811_a(
            ☃,
            Blocks.field_150386_bk
               .func_176223_P()
               .func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true))
               .func_206870_a(BlockFence.field_196413_c, Boolean.valueOf(true)),
            0,
            3,
            8,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150386_bk
               .func_176223_P()
               .func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true))
               .func_206870_a(BlockFence.field_196413_c, Boolean.valueOf(true)),
            8,
            3,
            8,
            ☃
         );
         this.func_175804_a(☃, ☃, 0, 3, 6, 0, 3, 7, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 8, 3, 6, 8, 3, 7, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 0, 3, 4, 0, 5, 5, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 8, 3, 4, 8, 5, 5, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 3, 5, 2, 5, 5, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 6, 3, 5, 7, 5, 5, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 4, 5, 1, 5, 5, ☃x, ☃x, false);
         this.func_175804_a(☃, ☃, 7, 4, 5, 7, 5, 5, ☃x, ☃x, false);

         for(int ☃xx = 0; ☃xx <= 5; ++☃xx) {
            for(int ☃xxx = 0; ☃xxx <= 8; ++☃xxx) {
               this.func_175808_b(☃, Blocks.field_196653_dH.func_176223_P(), ☃xxx, -1, ☃xx, ☃);
            }
         }

         return true;
      }
   }

   public static class Corridor5 extends FortressPieces.Piece {
      public Corridor5() {
      }

      public Corridor5(int var1, Random var2, MutableBoundingBox var3, EnumFacing var4) {
         super(☃);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
      }

      @Override
      public void func_74861_a(StructurePiece var1, List<StructurePiece> var2, Random var3) {
         this.func_74963_a((FortressPieces.Start)☃, ☃, ☃, 1, 0, true);
      }

      public static FortressPieces.Corridor5 func_175877_a(List<StructurePiece> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, -1, 0, 0, 5, 7, 5, ☃);
         return func_74964_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new FortressPieces.Corridor5(☃, ☃, ☃, ☃) : null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         this.func_175804_a(☃, ☃, 0, 0, 0, 4, 1, 4, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 2, 0, 4, 5, 4, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         IBlockState ☃ = Blocks.field_150386_bk
            .func_176223_P()
            .func_206870_a(BlockFence.field_196409_a, Boolean.valueOf(true))
            .func_206870_a(BlockFence.field_196413_c, Boolean.valueOf(true));
         this.func_175804_a(☃, ☃, 0, 2, 0, 0, 5, 4, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 4, 2, 0, 4, 5, 4, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 3, 1, 0, 4, 1, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 0, 3, 3, 0, 4, 3, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 4, 3, 1, 4, 4, 1, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 4, 3, 3, 4, 4, 3, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 0, 6, 0, 4, 6, 4, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);

         for(int ☃x = 0; ☃x <= 4; ++☃x) {
            for(int ☃xx = 0; ☃xx <= 4; ++☃xx) {
               this.func_175808_b(☃, Blocks.field_196653_dH.func_176223_P(), ☃x, -1, ☃xx, ☃);
            }
         }

         return true;
      }
   }

   public static class Crossing extends FortressPieces.Piece {
      public Crossing() {
      }

      public Crossing(int var1, Random var2, MutableBoundingBox var3, EnumFacing var4) {
         super(☃);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
      }

      @Override
      public void func_74861_a(StructurePiece var1, List<StructurePiece> var2, Random var3) {
         this.func_74963_a((FortressPieces.Start)☃, ☃, ☃, 2, 0, false);
         this.func_74961_b((FortressPieces.Start)☃, ☃, ☃, 0, 2, false);
         this.func_74965_c((FortressPieces.Start)☃, ☃, ☃, 0, 2, false);
      }

      public static FortressPieces.Crossing func_175873_a(List<StructurePiece> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, -2, 0, 0, 7, 9, 7, ☃);
         return func_74964_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new FortressPieces.Crossing(☃, ☃, ☃, ☃) : null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         this.func_175804_a(☃, ☃, 0, 0, 0, 6, 1, 6, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 2, 0, 6, 7, 6, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 2, 0, 1, 6, 0, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 2, 6, 1, 6, 6, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 5, 2, 0, 6, 6, 0, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 5, 2, 6, 6, 6, 6, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 2, 0, 0, 6, 1, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 2, 5, 0, 6, 6, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 6, 2, 0, 6, 6, 1, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 6, 2, 5, 6, 6, 6, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         IBlockState ☃ = Blocks.field_150386_bk
            .func_176223_P()
            .func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true))
            .func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true));
         IBlockState ☃x = Blocks.field_150386_bk
            .func_176223_P()
            .func_206870_a(BlockFence.field_196409_a, Boolean.valueOf(true))
            .func_206870_a(BlockFence.field_196413_c, Boolean.valueOf(true));
         this.func_175804_a(☃, ☃, 2, 6, 0, 4, 6, 0, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 2, 5, 0, 4, 5, 0, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 2, 6, 6, 4, 6, 6, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 2, 5, 6, 4, 5, 6, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 0, 6, 2, 0, 6, 4, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 5, 2, 0, 5, 4, ☃x, ☃x, false);
         this.func_175804_a(☃, ☃, 6, 6, 2, 6, 6, 4, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 6, 5, 2, 6, 5, 4, ☃x, ☃x, false);

         for(int ☃xx = 0; ☃xx <= 6; ++☃xx) {
            for(int ☃xxx = 0; ☃xxx <= 6; ++☃xxx) {
               this.func_175808_b(☃, Blocks.field_196653_dH.func_176223_P(), ☃xx, -1, ☃xxx, ☃);
            }
         }

         return true;
      }
   }

   public static class Crossing2 extends FortressPieces.Piece {
      public Crossing2() {
      }

      public Crossing2(int var1, Random var2, MutableBoundingBox var3, EnumFacing var4) {
         super(☃);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
      }

      @Override
      public void func_74861_a(StructurePiece var1, List<StructurePiece> var2, Random var3) {
         this.func_74963_a((FortressPieces.Start)☃, ☃, ☃, 1, 0, true);
         this.func_74961_b((FortressPieces.Start)☃, ☃, ☃, 0, 1, true);
         this.func_74965_c((FortressPieces.Start)☃, ☃, ☃, 0, 1, true);
      }

      public static FortressPieces.Crossing2 func_175878_a(List<StructurePiece> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, -1, 0, 0, 5, 7, 5, ☃);
         return func_74964_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new FortressPieces.Crossing2(☃, ☃, ☃, ☃) : null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         this.func_175804_a(☃, ☃, 0, 0, 0, 4, 1, 4, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 2, 0, 4, 5, 4, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 2, 0, 0, 5, 0, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 4, 2, 0, 4, 5, 0, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 2, 4, 0, 5, 4, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 4, 2, 4, 4, 5, 4, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 6, 0, 4, 6, 4, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);

         for(int ☃ = 0; ☃ <= 4; ++☃) {
            for(int ☃x = 0; ☃x <= 4; ++☃x) {
               this.func_175808_b(☃, Blocks.field_196653_dH.func_176223_P(), ☃, -1, ☃x, ☃);
            }
         }

         return true;
      }
   }

   public static class Crossing3 extends FortressPieces.Piece {
      public Crossing3() {
      }

      public Crossing3(int var1, Random var2, MutableBoundingBox var3, EnumFacing var4) {
         super(☃);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
      }

      protected Crossing3(Random var1, int var2, int var3) {
         super(0);
         this.func_186164_a(EnumFacing.Plane.HORIZONTAL.func_179518_a(☃));
         if (this.func_186165_e().func_176740_k() == EnumFacing.Axis.Z) {
            this.field_74887_e = new MutableBoundingBox(☃, 64, ☃, ☃ + 19 - 1, 73, ☃ + 19 - 1);
         } else {
            this.field_74887_e = new MutableBoundingBox(☃, 64, ☃, ☃ + 19 - 1, 73, ☃ + 19 - 1);
         }
      }

      @Override
      public void func_74861_a(StructurePiece var1, List<StructurePiece> var2, Random var3) {
         this.func_74963_a((FortressPieces.Start)☃, ☃, ☃, 8, 3, false);
         this.func_74961_b((FortressPieces.Start)☃, ☃, ☃, 3, 8, false);
         this.func_74965_c((FortressPieces.Start)☃, ☃, ☃, 3, 8, false);
      }

      public static FortressPieces.Crossing3 func_175885_a(List<StructurePiece> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, -8, -3, 0, 19, 10, 19, ☃);
         return func_74964_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new FortressPieces.Crossing3(☃, ☃, ☃, ☃) : null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         this.func_175804_a(☃, ☃, 7, 3, 0, 11, 4, 18, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 3, 7, 18, 4, 11, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 8, 5, 0, 10, 7, 18, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 5, 8, 18, 7, 10, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 7, 5, 0, 7, 5, 7, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 7, 5, 11, 7, 5, 18, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 11, 5, 0, 11, 5, 7, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 11, 5, 11, 11, 5, 18, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 5, 7, 7, 5, 7, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 11, 5, 7, 18, 5, 7, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 5, 11, 7, 5, 11, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 11, 5, 11, 18, 5, 11, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 7, 2, 0, 11, 2, 5, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 7, 2, 13, 11, 2, 18, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 7, 0, 0, 11, 1, 3, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 7, 0, 15, 11, 1, 18, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);

         for(int ☃ = 7; ☃ <= 11; ++☃) {
            for(int ☃x = 0; ☃x <= 2; ++☃x) {
               this.func_175808_b(☃, Blocks.field_196653_dH.func_176223_P(), ☃, -1, ☃x, ☃);
               this.func_175808_b(☃, Blocks.field_196653_dH.func_176223_P(), ☃, -1, 18 - ☃x, ☃);
            }
         }

         this.func_175804_a(☃, ☃, 0, 2, 7, 5, 2, 11, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 13, 2, 7, 18, 2, 11, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 0, 7, 3, 1, 11, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 15, 0, 7, 18, 1, 11, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);

         for(int ☃ = 0; ☃ <= 2; ++☃) {
            for(int ☃x = 7; ☃x <= 11; ++☃x) {
               this.func_175808_b(☃, Blocks.field_196653_dH.func_176223_P(), ☃, -1, ☃x, ☃);
               this.func_175808_b(☃, Blocks.field_196653_dH.func_176223_P(), 18 - ☃, -1, ☃x, ☃);
            }
         }

         return true;
      }
   }

   public static class End extends FortressPieces.Piece {
      private int field_74972_a;

      public End() {
      }

      public End(int var1, Random var2, MutableBoundingBox var3, EnumFacing var4) {
         super(☃);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
         this.field_74972_a = ☃.nextInt();
      }

      public static FortressPieces.End func_175884_a(List<StructurePiece> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, -1, -3, 0, 5, 10, 8, ☃);
         return func_74964_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new FortressPieces.End(☃, ☃, ☃, ☃) : null;
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
         super.func_143011_b(☃, ☃);
         this.field_74972_a = ☃.func_74762_e("Seed");
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
         super.func_143012_a(☃);
         ☃.func_74768_a("Seed", this.field_74972_a);
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         Random ☃ = new Random((long)this.field_74972_a);

         for(int ☃x = 0; ☃x <= 4; ++☃x) {
            for(int ☃xx = 3; ☃xx <= 4; ++☃xx) {
               int ☃xxx = ☃.nextInt(8);
               this.func_175804_a(☃, ☃, ☃x, ☃xx, 0, ☃x, ☃xx, ☃xxx, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
            }
         }

         int ☃x = ☃.nextInt(8);
         this.func_175804_a(☃, ☃, 0, 5, 0, 0, 5, ☃x, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         ☃x = ☃.nextInt(8);
         this.func_175804_a(☃, ☃, 4, 5, 0, 4, 5, ☃x, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);

         for(int ☃xx = 0; ☃xx <= 4; ++☃xx) {
            int ☃xxx = ☃.nextInt(5);
            this.func_175804_a(☃, ☃, ☃xx, 2, 0, ☃xx, 2, ☃xxx, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         }

         for(int ☃xx = 0; ☃xx <= 4; ++☃xx) {
            for(int ☃xxx = 0; ☃xxx <= 1; ++☃xxx) {
               int ☃xxxx = ☃.nextInt(3);
               this.func_175804_a(☃, ☃, ☃xx, ☃xxx, 0, ☃xx, ☃xxx, ☃xxxx, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
            }
         }

         return true;
      }
   }

   public static class Entrance extends FortressPieces.Piece {
      public Entrance() {
      }

      public Entrance(int var1, Random var2, MutableBoundingBox var3, EnumFacing var4) {
         super(☃);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
      }

      @Override
      public void func_74861_a(StructurePiece var1, List<StructurePiece> var2, Random var3) {
         this.func_74963_a((FortressPieces.Start)☃, ☃, ☃, 5, 3, true);
      }

      public static FortressPieces.Entrance func_175881_a(List<StructurePiece> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, -5, -3, 0, 13, 14, 13, ☃);
         return func_74964_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new FortressPieces.Entrance(☃, ☃, ☃, ☃) : null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         this.func_175804_a(☃, ☃, 0, 3, 0, 12, 4, 12, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 5, 0, 12, 13, 12, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 5, 0, 1, 12, 12, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 11, 5, 0, 12, 12, 12, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 2, 5, 11, 4, 12, 12, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 8, 5, 11, 10, 12, 12, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 5, 9, 11, 7, 12, 12, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 2, 5, 0, 4, 12, 1, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 8, 5, 0, 10, 12, 1, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 5, 9, 0, 7, 12, 1, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 2, 11, 2, 10, 12, 10, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 5, 8, 0, 7, 8, 0, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         IBlockState ☃ = Blocks.field_150386_bk
            .func_176223_P()
            .func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true))
            .func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true));
         IBlockState ☃x = Blocks.field_150386_bk
            .func_176223_P()
            .func_206870_a(BlockFence.field_196409_a, Boolean.valueOf(true))
            .func_206870_a(BlockFence.field_196413_c, Boolean.valueOf(true));

         for(int ☃xx = 1; ☃xx <= 11; ☃xx += 2) {
            this.func_175804_a(☃, ☃, ☃xx, 10, 0, ☃xx, 11, 0, ☃, ☃, false);
            this.func_175804_a(☃, ☃, ☃xx, 10, 12, ☃xx, 11, 12, ☃, ☃, false);
            this.func_175804_a(☃, ☃, 0, 10, ☃xx, 0, 11, ☃xx, ☃x, ☃x, false);
            this.func_175804_a(☃, ☃, 12, 10, ☃xx, 12, 11, ☃xx, ☃x, ☃x, false);
            this.func_175811_a(☃, Blocks.field_196653_dH.func_176223_P(), ☃xx, 13, 0, ☃);
            this.func_175811_a(☃, Blocks.field_196653_dH.func_176223_P(), ☃xx, 13, 12, ☃);
            this.func_175811_a(☃, Blocks.field_196653_dH.func_176223_P(), 0, 13, ☃xx, ☃);
            this.func_175811_a(☃, Blocks.field_196653_dH.func_176223_P(), 12, 13, ☃xx, ☃);
            if (☃xx != 11) {
               this.func_175811_a(☃, ☃, ☃xx + 1, 13, 0, ☃);
               this.func_175811_a(☃, ☃, ☃xx + 1, 13, 12, ☃);
               this.func_175811_a(☃, ☃x, 0, 13, ☃xx + 1, ☃);
               this.func_175811_a(☃, ☃x, 12, 13, ☃xx + 1, ☃);
            }
         }

         this.func_175811_a(
            ☃,
            Blocks.field_150386_bk
               .func_176223_P()
               .func_206870_a(BlockFence.field_196409_a, Boolean.valueOf(true))
               .func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true)),
            0,
            13,
            0,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150386_bk
               .func_176223_P()
               .func_206870_a(BlockFence.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true)),
            0,
            13,
            12,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150386_bk
               .func_176223_P()
               .func_206870_a(BlockFence.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true)),
            12,
            13,
            12,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150386_bk
               .func_176223_P()
               .func_206870_a(BlockFence.field_196409_a, Boolean.valueOf(true))
               .func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true)),
            12,
            13,
            0,
            ☃
         );

         for(int ☃xx = 3; ☃xx <= 9; ☃xx += 2) {
            this.func_175804_a(
               ☃,
               ☃,
               1,
               7,
               ☃xx,
               1,
               8,
               ☃xx,
               ☃x.func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true)),
               ☃x.func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true)),
               false
            );
            this.func_175804_a(
               ☃,
               ☃,
               11,
               7,
               ☃xx,
               11,
               8,
               ☃xx,
               ☃x.func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true)),
               ☃x.func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true)),
               false
            );
         }

         this.func_175804_a(☃, ☃, 4, 2, 0, 8, 2, 12, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 2, 4, 12, 2, 8, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 4, 0, 0, 8, 1, 3, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 4, 0, 9, 8, 1, 12, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 0, 4, 3, 1, 8, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 9, 0, 4, 12, 1, 8, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);

         for(int ☃xx = 4; ☃xx <= 8; ++☃xx) {
            for(int ☃xxx = 0; ☃xxx <= 2; ++☃xxx) {
               this.func_175808_b(☃, Blocks.field_196653_dH.func_176223_P(), ☃xx, -1, ☃xxx, ☃);
               this.func_175808_b(☃, Blocks.field_196653_dH.func_176223_P(), ☃xx, -1, 12 - ☃xxx, ☃);
            }
         }

         for(int ☃xx = 0; ☃xx <= 2; ++☃xx) {
            for(int ☃xxx = 4; ☃xxx <= 8; ++☃xxx) {
               this.func_175808_b(☃, Blocks.field_196653_dH.func_176223_P(), ☃xx, -1, ☃xxx, ☃);
               this.func_175808_b(☃, Blocks.field_196653_dH.func_176223_P(), 12 - ☃xx, -1, ☃xxx, ☃);
            }
         }

         this.func_175804_a(☃, ☃, 5, 5, 5, 7, 5, 7, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 6, 1, 6, 6, 4, 6, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175811_a(☃, Blocks.field_196653_dH.func_176223_P(), 6, 0, 6, ☃);
         this.func_175811_a(☃, Blocks.field_150353_l.func_176223_P(), 6, 5, 6, ☃);
         BlockPos ☃xx = new BlockPos(this.func_74865_a(6, 6), this.func_74862_a(5), this.func_74873_b(6, 6));
         if (☃.func_175898_b(☃xx)) {
            ☃.func_205219_F_().func_205360_a(☃xx, Fluids.field_204547_b, 0);
         }

         return true;
      }
   }

   public static class NetherStalkRoom extends FortressPieces.Piece {
      public NetherStalkRoom() {
      }

      public NetherStalkRoom(int var1, Random var2, MutableBoundingBox var3, EnumFacing var4) {
         super(☃);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
      }

      @Override
      public void func_74861_a(StructurePiece var1, List<StructurePiece> var2, Random var3) {
         this.func_74963_a((FortressPieces.Start)☃, ☃, ☃, 5, 3, true);
         this.func_74963_a((FortressPieces.Start)☃, ☃, ☃, 5, 11, true);
      }

      public static FortressPieces.NetherStalkRoom func_175875_a(
         List<StructurePiece> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6
      ) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, -5, -3, 0, 13, 14, 13, ☃);
         return func_74964_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new FortressPieces.NetherStalkRoom(☃, ☃, ☃, ☃) : null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         this.func_175804_a(☃, ☃, 0, 3, 0, 12, 4, 12, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 5, 0, 12, 13, 12, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 5, 0, 1, 12, 12, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 11, 5, 0, 12, 12, 12, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 2, 5, 11, 4, 12, 12, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 8, 5, 11, 10, 12, 12, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 5, 9, 11, 7, 12, 12, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 2, 5, 0, 4, 12, 1, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 8, 5, 0, 10, 12, 1, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 5, 9, 0, 7, 12, 1, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 2, 11, 2, 10, 12, 10, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         IBlockState ☃ = Blocks.field_150386_bk
            .func_176223_P()
            .func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true))
            .func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true));
         IBlockState ☃x = Blocks.field_150386_bk
            .func_176223_P()
            .func_206870_a(BlockFence.field_196409_a, Boolean.valueOf(true))
            .func_206870_a(BlockFence.field_196413_c, Boolean.valueOf(true));
         IBlockState ☃xx = ☃x.func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true));
         IBlockState ☃xxx = ☃x.func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true));

         for(int ☃xxxx = 1; ☃xxxx <= 11; ☃xxxx += 2) {
            this.func_175804_a(☃, ☃, ☃xxxx, 10, 0, ☃xxxx, 11, 0, ☃, ☃, false);
            this.func_175804_a(☃, ☃, ☃xxxx, 10, 12, ☃xxxx, 11, 12, ☃, ☃, false);
            this.func_175804_a(☃, ☃, 0, 10, ☃xxxx, 0, 11, ☃xxxx, ☃x, ☃x, false);
            this.func_175804_a(☃, ☃, 12, 10, ☃xxxx, 12, 11, ☃xxxx, ☃x, ☃x, false);
            this.func_175811_a(☃, Blocks.field_196653_dH.func_176223_P(), ☃xxxx, 13, 0, ☃);
            this.func_175811_a(☃, Blocks.field_196653_dH.func_176223_P(), ☃xxxx, 13, 12, ☃);
            this.func_175811_a(☃, Blocks.field_196653_dH.func_176223_P(), 0, 13, ☃xxxx, ☃);
            this.func_175811_a(☃, Blocks.field_196653_dH.func_176223_P(), 12, 13, ☃xxxx, ☃);
            if (☃xxxx != 11) {
               this.func_175811_a(☃, ☃, ☃xxxx + 1, 13, 0, ☃);
               this.func_175811_a(☃, ☃, ☃xxxx + 1, 13, 12, ☃);
               this.func_175811_a(☃, ☃x, 0, 13, ☃xxxx + 1, ☃);
               this.func_175811_a(☃, ☃x, 12, 13, ☃xxxx + 1, ☃);
            }
         }

         this.func_175811_a(
            ☃,
            Blocks.field_150386_bk
               .func_176223_P()
               .func_206870_a(BlockFence.field_196409_a, Boolean.valueOf(true))
               .func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true)),
            0,
            13,
            0,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150386_bk
               .func_176223_P()
               .func_206870_a(BlockFence.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true)),
            0,
            13,
            12,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150386_bk
               .func_176223_P()
               .func_206870_a(BlockFence.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true)),
            12,
            13,
            12,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150386_bk
               .func_176223_P()
               .func_206870_a(BlockFence.field_196409_a, Boolean.valueOf(true))
               .func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true)),
            12,
            13,
            0,
            ☃
         );

         for(int ☃xxxx = 3; ☃xxxx <= 9; ☃xxxx += 2) {
            this.func_175804_a(☃, ☃, 1, 7, ☃xxxx, 1, 8, ☃xxxx, ☃xx, ☃xx, false);
            this.func_175804_a(☃, ☃, 11, 7, ☃xxxx, 11, 8, ☃xxxx, ☃xxx, ☃xxx, false);
         }

         IBlockState ☃xxxx = Blocks.field_150387_bl.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.NORTH);

         for(int ☃xxxxx = 0; ☃xxxxx <= 6; ++☃xxxxx) {
            int ☃xxxxxx = ☃xxxxx + 4;

            for(int ☃xxxxxxx = 5; ☃xxxxxxx <= 7; ++☃xxxxxxx) {
               this.func_175811_a(☃, ☃xxxx, ☃xxxxxxx, 5 + ☃xxxxx, ☃xxxxxx, ☃);
            }

            if (☃xxxxxx >= 5 && ☃xxxxxx <= 8) {
               this.func_175804_a(
                  ☃, ☃, 5, 5, ☃xxxxxx, 7, ☃xxxxx + 4, ☃xxxxxx, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false
               );
            } else if (☃xxxxxx >= 9 && ☃xxxxxx <= 10) {
               this.func_175804_a(
                  ☃, ☃, 5, 8, ☃xxxxxx, 7, ☃xxxxx + 4, ☃xxxxxx, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false
               );
            }

            if (☃xxxxx >= 1) {
               this.func_175804_a(
                  ☃, ☃, 5, 6 + ☃xxxxx, ☃xxxxxx, 7, 9 + ☃xxxxx, ☃xxxxxx, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false
               );
            }
         }

         for(int ☃xxxxx = 5; ☃xxxxx <= 7; ++☃xxxxx) {
            this.func_175811_a(☃, ☃xxxx, ☃xxxxx, 12, 11, ☃);
         }

         this.func_175804_a(☃, ☃, 5, 6, 7, 5, 7, 7, ☃xxx, ☃xxx, false);
         this.func_175804_a(☃, ☃, 7, 6, 7, 7, 7, 7, ☃xx, ☃xx, false);
         this.func_175804_a(☃, ☃, 5, 13, 12, 7, 13, 12, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 2, 5, 2, 3, 5, 3, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 2, 5, 9, 3, 5, 10, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 2, 5, 4, 2, 5, 8, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 9, 5, 2, 10, 5, 3, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 9, 5, 9, 10, 5, 10, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 10, 5, 4, 10, 5, 8, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         IBlockState ☃xxxxx = ☃xxxx.func_206870_a(BlockStairs.field_176309_a, EnumFacing.EAST);
         IBlockState ☃xxxxxx = ☃xxxx.func_206870_a(BlockStairs.field_176309_a, EnumFacing.WEST);
         this.func_175811_a(☃, ☃xxxxxx, 4, 5, 2, ☃);
         this.func_175811_a(☃, ☃xxxxxx, 4, 5, 3, ☃);
         this.func_175811_a(☃, ☃xxxxxx, 4, 5, 9, ☃);
         this.func_175811_a(☃, ☃xxxxxx, 4, 5, 10, ☃);
         this.func_175811_a(☃, ☃xxxxx, 8, 5, 2, ☃);
         this.func_175811_a(☃, ☃xxxxx, 8, 5, 3, ☃);
         this.func_175811_a(☃, ☃xxxxx, 8, 5, 9, ☃);
         this.func_175811_a(☃, ☃xxxxx, 8, 5, 10, ☃);
         this.func_175804_a(☃, ☃, 3, 4, 4, 4, 4, 8, Blocks.field_150425_aM.func_176223_P(), Blocks.field_150425_aM.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 8, 4, 4, 9, 4, 8, Blocks.field_150425_aM.func_176223_P(), Blocks.field_150425_aM.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 3, 5, 4, 4, 5, 8, Blocks.field_150388_bm.func_176223_P(), Blocks.field_150388_bm.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 8, 5, 4, 9, 5, 8, Blocks.field_150388_bm.func_176223_P(), Blocks.field_150388_bm.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 4, 2, 0, 8, 2, 12, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 2, 4, 12, 2, 8, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 4, 0, 0, 8, 1, 3, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 4, 0, 9, 8, 1, 12, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 0, 4, 3, 1, 8, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 9, 0, 4, 12, 1, 8, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);

         for(int ☃xxxxxxx = 4; ☃xxxxxxx <= 8; ++☃xxxxxxx) {
            for(int ☃xxxxxxxx = 0; ☃xxxxxxxx <= 2; ++☃xxxxxxxx) {
               this.func_175808_b(☃, Blocks.field_196653_dH.func_176223_P(), ☃xxxxxxx, -1, ☃xxxxxxxx, ☃);
               this.func_175808_b(☃, Blocks.field_196653_dH.func_176223_P(), ☃xxxxxxx, -1, 12 - ☃xxxxxxxx, ☃);
            }
         }

         for(int ☃xxxxxxx = 0; ☃xxxxxxx <= 2; ++☃xxxxxxx) {
            for(int ☃xxxxxxxx = 4; ☃xxxxxxxx <= 8; ++☃xxxxxxxx) {
               this.func_175808_b(☃, Blocks.field_196653_dH.func_176223_P(), ☃xxxxxxx, -1, ☃xxxxxxxx, ☃);
               this.func_175808_b(☃, Blocks.field_196653_dH.func_176223_P(), 12 - ☃xxxxxxx, -1, ☃xxxxxxxx, ☃);
            }
         }

         return true;
      }
   }

   abstract static class Piece extends StructurePiece {
      public Piece() {
      }

      protected Piece(int var1) {
         super(☃);
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
      }

      private int func_74960_a(List<FortressPieces.PieceWeight> var1) {
         boolean ☃ = false;
         int ☃x = 0;

         for(FortressPieces.PieceWeight ☃xx : ☃) {
            if (☃xx.field_78824_d > 0 && ☃xx.field_78827_c < ☃xx.field_78824_d) {
               ☃ = true;
            }

            ☃x += ☃xx.field_78826_b;
         }

         return ☃ ? ☃x : -1;
      }

      private FortressPieces.Piece func_175871_a(
         FortressPieces.Start var1,
         List<FortressPieces.PieceWeight> var2,
         List<StructurePiece> var3,
         Random var4,
         int var5,
         int var6,
         int var7,
         EnumFacing var8,
         int var9
      ) {
         int ☃ = this.func_74960_a(☃);
         boolean ☃x = ☃ > 0 && ☃ <= 30;
         int ☃xx = 0;

         while(☃xx < 5 && ☃x) {
            ++☃xx;
            int ☃xxx = ☃.nextInt(☃);

            for(FortressPieces.PieceWeight ☃xxxx : ☃) {
               ☃xxx -= ☃xxxx.field_78826_b;
               if (☃xxx < 0) {
                  if (!☃xxxx.func_78822_a(☃) || ☃xxxx == ☃.field_74970_a && !☃xxxx.field_78825_e) {
                     break;
                  }

                  FortressPieces.Piece ☃xxxxx = FortressPieces.func_175887_b(☃xxxx, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
                  if (☃xxxxx != null) {
                     ++☃xxxx.field_78827_c;
                     ☃.field_74970_a = ☃xxxx;
                     if (!☃xxxx.func_78823_a()) {
                        ☃.remove(☃xxxx);
                     }

                     return ☃xxxxx;
                  }
               }
            }
         }

         return FortressPieces.End.func_175884_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      }

      private StructurePiece func_175870_a(
         FortressPieces.Start var1, List<StructurePiece> var2, Random var3, int var4, int var5, int var6, @Nullable EnumFacing var7, int var8, boolean var9
      ) {
         if (Math.abs(☃ - ☃.func_74874_b().field_78897_a) <= 112 && Math.abs(☃ - ☃.func_74874_b().field_78896_c) <= 112) {
            List<FortressPieces.PieceWeight> ☃ = ☃.field_74968_b;
            if (☃) {
               ☃ = ☃.field_74969_c;
            }

            StructurePiece ☃ = this.func_175871_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃ + 1);
            if (☃ != null) {
               ☃.add(☃);
               ☃.field_74967_d.add(☃);
            }

            return ☃;
         } else {
            return FortressPieces.End.func_175884_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
         }
      }

      @Nullable
      protected StructurePiece func_74963_a(FortressPieces.Start var1, List<StructurePiece> var2, Random var3, int var4, int var5, boolean var6) {
         EnumFacing ☃ = this.func_186165_e();
         if (☃ != null) {
            switch(☃) {
               case NORTH:
                  return this.func_175870_a(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78897_a + ☃,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78896_c - 1,
                     ☃,
                     this.func_74877_c(),
                     ☃
                  );
               case SOUTH:
                  return this.func_175870_a(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78897_a + ☃,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78892_f + 1,
                     ☃,
                     this.func_74877_c(),
                     ☃
                  );
               case WEST:
                  return this.func_175870_a(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78897_a - 1,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78896_c + ☃,
                     ☃,
                     this.func_74877_c(),
                     ☃
                  );
               case EAST:
                  return this.func_175870_a(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78893_d + 1,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78896_c + ☃,
                     ☃,
                     this.func_74877_c(),
                     ☃
                  );
            }
         }

         return null;
      }

      @Nullable
      protected StructurePiece func_74961_b(FortressPieces.Start var1, List<StructurePiece> var2, Random var3, int var4, int var5, boolean var6) {
         EnumFacing ☃ = this.func_186165_e();
         if (☃ != null) {
            switch(☃) {
               case NORTH:
                  return this.func_175870_a(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78897_a - 1,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78896_c + ☃,
                     EnumFacing.WEST,
                     this.func_74877_c(),
                     ☃
                  );
               case SOUTH:
                  return this.func_175870_a(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78897_a - 1,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78896_c + ☃,
                     EnumFacing.WEST,
                     this.func_74877_c(),
                     ☃
                  );
               case WEST:
                  return this.func_175870_a(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78897_a + ☃,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78896_c - 1,
                     EnumFacing.NORTH,
                     this.func_74877_c(),
                     ☃
                  );
               case EAST:
                  return this.func_175870_a(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78897_a + ☃,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78896_c - 1,
                     EnumFacing.NORTH,
                     this.func_74877_c(),
                     ☃
                  );
            }
         }

         return null;
      }

      @Nullable
      protected StructurePiece func_74965_c(FortressPieces.Start var1, List<StructurePiece> var2, Random var3, int var4, int var5, boolean var6) {
         EnumFacing ☃ = this.func_186165_e();
         if (☃ != null) {
            switch(☃) {
               case NORTH:
                  return this.func_175870_a(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78893_d + 1,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78896_c + ☃,
                     EnumFacing.EAST,
                     this.func_74877_c(),
                     ☃
                  );
               case SOUTH:
                  return this.func_175870_a(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78893_d + 1,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78896_c + ☃,
                     EnumFacing.EAST,
                     this.func_74877_c(),
                     ☃
                  );
               case WEST:
                  return this.func_175870_a(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78897_a + ☃,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78892_f + 1,
                     EnumFacing.SOUTH,
                     this.func_74877_c(),
                     ☃
                  );
               case EAST:
                  return this.func_175870_a(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78897_a + ☃,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78892_f + 1,
                     EnumFacing.SOUTH,
                     this.func_74877_c(),
                     ☃
                  );
            }
         }

         return null;
      }

      protected static boolean func_74964_a(MutableBoundingBox var0) {
         return ☃ != null && ☃.field_78895_b > 10;
      }
   }

   static class PieceWeight {
      public Class<? extends FortressPieces.Piece> field_78828_a;
      public final int field_78826_b;
      public int field_78827_c;
      public int field_78824_d;
      public boolean field_78825_e;

      public PieceWeight(Class<? extends FortressPieces.Piece> var1, int var2, int var3, boolean var4) {
         this.field_78828_a = ☃;
         this.field_78826_b = ☃;
         this.field_78824_d = ☃;
         this.field_78825_e = ☃;
      }

      public PieceWeight(Class<? extends FortressPieces.Piece> var1, int var2, int var3) {
         this(☃, ☃, ☃, false);
      }

      public boolean func_78822_a(int var1) {
         return this.field_78824_d == 0 || this.field_78827_c < this.field_78824_d;
      }

      public boolean func_78823_a() {
         return this.field_78824_d == 0 || this.field_78827_c < this.field_78824_d;
      }
   }

   public static class Stairs extends FortressPieces.Piece {
      public Stairs() {
      }

      public Stairs(int var1, Random var2, MutableBoundingBox var3, EnumFacing var4) {
         super(☃);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
      }

      @Override
      public void func_74861_a(StructurePiece var1, List<StructurePiece> var2, Random var3) {
         this.func_74965_c((FortressPieces.Start)☃, ☃, ☃, 6, 2, false);
      }

      public static FortressPieces.Stairs func_175872_a(List<StructurePiece> var0, Random var1, int var2, int var3, int var4, int var5, EnumFacing var6) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, -2, 0, 0, 7, 11, 7, ☃);
         return func_74964_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new FortressPieces.Stairs(☃, ☃, ☃, ☃) : null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         this.func_175804_a(☃, ☃, 0, 0, 0, 6, 1, 6, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 2, 0, 6, 10, 6, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 2, 0, 1, 8, 0, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 5, 2, 0, 6, 8, 0, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 2, 1, 0, 8, 6, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 6, 2, 1, 6, 8, 6, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 2, 6, 5, 8, 6, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         IBlockState ☃ = Blocks.field_150386_bk
            .func_176223_P()
            .func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true))
            .func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true));
         IBlockState ☃x = Blocks.field_150386_bk
            .func_176223_P()
            .func_206870_a(BlockFence.field_196409_a, Boolean.valueOf(true))
            .func_206870_a(BlockFence.field_196413_c, Boolean.valueOf(true));
         this.func_175804_a(☃, ☃, 0, 3, 2, 0, 5, 4, ☃x, ☃x, false);
         this.func_175804_a(☃, ☃, 6, 3, 2, 6, 5, 2, ☃x, ☃x, false);
         this.func_175804_a(☃, ☃, 6, 3, 4, 6, 5, 4, ☃x, ☃x, false);
         this.func_175811_a(☃, Blocks.field_196653_dH.func_176223_P(), 5, 2, 5, ☃);
         this.func_175804_a(☃, ☃, 4, 2, 5, 4, 3, 5, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 3, 2, 5, 3, 4, 5, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 2, 2, 5, 2, 5, 5, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 2, 5, 1, 6, 5, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 7, 1, 5, 7, 4, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 6, 8, 2, 6, 8, 4, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 2, 6, 0, 4, 8, 0, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 2, 5, 0, 4, 5, 0, ☃, ☃, false);

         for(int ☃xx = 0; ☃xx <= 6; ++☃xx) {
            for(int ☃xxx = 0; ☃xxx <= 6; ++☃xxx) {
               this.func_175808_b(☃, Blocks.field_196653_dH.func_176223_P(), ☃xx, -1, ☃xxx, ☃);
            }
         }

         return true;
      }
   }

   public static class Start extends FortressPieces.Crossing3 {
      public FortressPieces.PieceWeight field_74970_a;
      public List<FortressPieces.PieceWeight> field_74968_b;
      public List<FortressPieces.PieceWeight> field_74969_c;
      public List<StructurePiece> field_74967_d = Lists.<StructurePiece>newArrayList();

      public Start() {
      }

      public Start(Random var1, int var2, int var3) {
         super(☃, ☃, ☃);
         this.field_74968_b = Lists.<FortressPieces.PieceWeight>newArrayList();

         for(FortressPieces.PieceWeight ☃ : FortressPieces.field_78742_a) {
            ☃.field_78827_c = 0;
            this.field_74968_b.add(☃);
         }

         this.field_74969_c = Lists.<FortressPieces.PieceWeight>newArrayList();

         for(FortressPieces.PieceWeight ☃ : FortressPieces.field_78741_b) {
            ☃.field_78827_c = 0;
            this.field_74969_c.add(☃);
         }
      }
   }

   public static class Straight extends FortressPieces.Piece {
      public Straight() {
      }

      public Straight(int var1, Random var2, MutableBoundingBox var3, EnumFacing var4) {
         super(☃);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
      }

      @Override
      public void func_74861_a(StructurePiece var1, List<StructurePiece> var2, Random var3) {
         this.func_74963_a((FortressPieces.Start)☃, ☃, ☃, 1, 3, false);
      }

      public static FortressPieces.Straight func_175882_a(List<StructurePiece> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, -1, -3, 0, 5, 10, 19, ☃);
         return func_74964_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new FortressPieces.Straight(☃, ☃, ☃, ☃) : null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         this.func_175804_a(☃, ☃, 0, 3, 0, 4, 4, 18, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 5, 0, 3, 7, 18, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 5, 0, 0, 5, 18, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 4, 5, 0, 4, 5, 18, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 2, 0, 4, 2, 5, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 2, 13, 4, 2, 18, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 0, 0, 4, 1, 3, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 0, 15, 4, 1, 18, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);

         for(int ☃ = 0; ☃ <= 4; ++☃) {
            for(int ☃x = 0; ☃x <= 2; ++☃x) {
               this.func_175808_b(☃, Blocks.field_196653_dH.func_176223_P(), ☃, -1, ☃x, ☃);
               this.func_175808_b(☃, Blocks.field_196653_dH.func_176223_P(), ☃, -1, 18 - ☃x, ☃);
            }
         }

         IBlockState ☃ = Blocks.field_150386_bk
            .func_176223_P()
            .func_206870_a(BlockFence.field_196409_a, Boolean.valueOf(true))
            .func_206870_a(BlockFence.field_196413_c, Boolean.valueOf(true));
         IBlockState ☃x = ☃.func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true));
         IBlockState ☃xx = ☃.func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true));
         this.func_175804_a(☃, ☃, 0, 1, 1, 0, 4, 1, ☃x, ☃x, false);
         this.func_175804_a(☃, ☃, 0, 3, 4, 0, 4, 4, ☃x, ☃x, false);
         this.func_175804_a(☃, ☃, 0, 3, 14, 0, 4, 14, ☃x, ☃x, false);
         this.func_175804_a(☃, ☃, 0, 1, 17, 0, 4, 17, ☃x, ☃x, false);
         this.func_175804_a(☃, ☃, 4, 1, 1, 4, 4, 1, ☃xx, ☃xx, false);
         this.func_175804_a(☃, ☃, 4, 3, 4, 4, 4, 4, ☃xx, ☃xx, false);
         this.func_175804_a(☃, ☃, 4, 3, 14, 4, 4, 14, ☃xx, ☃xx, false);
         this.func_175804_a(☃, ☃, 4, 1, 17, 4, 4, 17, ☃xx, ☃xx, false);
         return true;
      }
   }

   public static class Throne extends FortressPieces.Piece {
      private boolean field_74976_a;

      public Throne() {
      }

      public Throne(int var1, Random var2, MutableBoundingBox var3, EnumFacing var4) {
         super(☃);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
         super.func_143011_b(☃, ☃);
         this.field_74976_a = ☃.func_74767_n("Mob");
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
         super.func_143012_a(☃);
         ☃.func_74757_a("Mob", this.field_74976_a);
      }

      public static FortressPieces.Throne func_175874_a(List<StructurePiece> var0, Random var1, int var2, int var3, int var4, int var5, EnumFacing var6) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, -2, 0, 0, 7, 8, 9, ☃);
         return func_74964_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new FortressPieces.Throne(☃, ☃, ☃, ☃) : null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         this.func_175804_a(☃, ☃, 0, 2, 0, 6, 7, 7, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 0, 0, 5, 1, 7, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 2, 1, 5, 2, 7, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 3, 2, 5, 3, 7, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 4, 3, 5, 4, 7, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 2, 0, 1, 4, 2, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 5, 2, 0, 5, 4, 2, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 5, 2, 1, 5, 3, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 5, 5, 2, 5, 5, 3, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 5, 3, 0, 5, 8, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 6, 5, 3, 6, 5, 8, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 5, 8, 5, 5, 8, Blocks.field_196653_dH.func_176223_P(), Blocks.field_196653_dH.func_176223_P(), false);
         IBlockState ☃ = Blocks.field_150386_bk
            .func_176223_P()
            .func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true))
            .func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true));
         IBlockState ☃x = Blocks.field_150386_bk
            .func_176223_P()
            .func_206870_a(BlockFence.field_196409_a, Boolean.valueOf(true))
            .func_206870_a(BlockFence.field_196413_c, Boolean.valueOf(true));
         this.func_175811_a(☃, Blocks.field_150386_bk.func_176223_P().func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true)), 1, 6, 3, ☃);
         this.func_175811_a(☃, Blocks.field_150386_bk.func_176223_P().func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true)), 5, 6, 3, ☃);
         this.func_175811_a(
            ☃,
            Blocks.field_150386_bk
               .func_176223_P()
               .func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true))
               .func_206870_a(BlockFence.field_196409_a, Boolean.valueOf(true)),
            0,
            6,
            3,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150386_bk
               .func_176223_P()
               .func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true))
               .func_206870_a(BlockFence.field_196409_a, Boolean.valueOf(true)),
            6,
            6,
            3,
            ☃
         );
         this.func_175804_a(☃, ☃, 0, 6, 4, 0, 6, 7, ☃x, ☃x, false);
         this.func_175804_a(☃, ☃, 6, 6, 4, 6, 6, 7, ☃x, ☃x, false);
         this.func_175811_a(
            ☃,
            Blocks.field_150386_bk
               .func_176223_P()
               .func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true))
               .func_206870_a(BlockFence.field_196413_c, Boolean.valueOf(true)),
            0,
            6,
            8,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150386_bk
               .func_176223_P()
               .func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true))
               .func_206870_a(BlockFence.field_196413_c, Boolean.valueOf(true)),
            6,
            6,
            8,
            ☃
         );
         this.func_175804_a(☃, ☃, 1, 6, 8, 5, 6, 8, ☃, ☃, false);
         this.func_175811_a(☃, Blocks.field_150386_bk.func_176223_P().func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true)), 1, 7, 8, ☃);
         this.func_175804_a(☃, ☃, 2, 7, 8, 4, 7, 8, ☃, ☃, false);
         this.func_175811_a(☃, Blocks.field_150386_bk.func_176223_P().func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true)), 5, 7, 8, ☃);
         this.func_175811_a(☃, Blocks.field_150386_bk.func_176223_P().func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true)), 2, 8, 8, ☃);
         this.func_175811_a(☃, ☃, 3, 8, 8, ☃);
         this.func_175811_a(☃, Blocks.field_150386_bk.func_176223_P().func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true)), 4, 8, 8, ☃);
         if (!this.field_74976_a) {
            BlockPos ☃xx = new BlockPos(this.func_74865_a(3, 5), this.func_74862_a(5), this.func_74873_b(3, 5));
            if (☃.func_175898_b(☃xx)) {
               this.field_74976_a = true;
               ☃.func_180501_a(☃xx, Blocks.field_150474_ac.func_176223_P(), 2);
               TileEntity ☃xxx = ☃.func_175625_s(☃xx);
               if (☃xxx instanceof TileEntityMobSpawner) {
                  ((TileEntityMobSpawner)☃xxx).func_145881_a().func_200876_a(EntityType.field_200792_f);
               }
            }
         }

         for(int ☃ = 0; ☃ <= 6; ++☃) {
            for(int ☃x = 0; ☃x <= 6; ++☃x) {
               this.func_175808_b(☃, Blocks.field_196653_dH.func_176223_P(), ☃, -1, ☃x, ☃);
            }
         }

         return true;
      }
   }
}
