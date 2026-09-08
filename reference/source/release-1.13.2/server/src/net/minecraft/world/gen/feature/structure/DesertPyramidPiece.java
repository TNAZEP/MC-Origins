package net.minecraft.world.gen.feature.structure;

import java.util.Random;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

public class DesertPyramidPiece extends ScatteredStructurePiece {
   private final boolean[] field_202598_e = new boolean[4];

   public static void func_202597_ad_() {
      StructureIO.func_143031_a(DesertPyramidPiece.class, "TeDP");
   }

   public DesertPyramidPiece() {
   }

   public DesertPyramidPiece(Random var1, int var2, int var3) {
      super(☃, ☃, 64, ☃, 21, 15, 21);
   }

   @Override
   protected void func_143012_a(NBTTagCompound var1) {
      super.func_143012_a(☃);
      ☃.func_74757_a("hasPlacedChest0", this.field_202598_e[0]);
      ☃.func_74757_a("hasPlacedChest1", this.field_202598_e[1]);
      ☃.func_74757_a("hasPlacedChest2", this.field_202598_e[2]);
      ☃.func_74757_a("hasPlacedChest3", this.field_202598_e[3]);
   }

   @Override
   protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
      super.func_143011_b(☃, ☃);
      this.field_202598_e[0] = ☃.func_74767_n("hasPlacedChest0");
      this.field_202598_e[1] = ☃.func_74767_n("hasPlacedChest1");
      this.field_202598_e[2] = ☃.func_74767_n("hasPlacedChest2");
      this.field_202598_e[3] = ☃.func_74767_n("hasPlacedChest3");
   }

   @Override
   public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
      this.func_175804_a(
         ☃,
         ☃,
         0,
         -4,
         0,
         this.field_202581_a - 1,
         0,
         this.field_202583_c - 1,
         Blocks.field_150322_A.func_176223_P(),
         Blocks.field_150322_A.func_176223_P(),
         false
      );

      for(int ☃ = 1; ☃ <= 9; ++☃) {
         this.func_175804_a(
            ☃,
            ☃,
            ☃,
            ☃,
            ☃,
            this.field_202581_a - 1 - ☃,
            ☃,
            this.field_202583_c - 1 - ☃,
            Blocks.field_150322_A.func_176223_P(),
            Blocks.field_150322_A.func_176223_P(),
            false
         );
         this.func_175804_a(
            ☃,
            ☃,
            ☃ + 1,
            ☃,
            ☃ + 1,
            this.field_202581_a - 2 - ☃,
            ☃,
            this.field_202583_c - 2 - ☃,
            Blocks.field_150350_a.func_176223_P(),
            Blocks.field_150350_a.func_176223_P(),
            false
         );
      }

      for(int ☃ = 0; ☃ < this.field_202581_a; ++☃) {
         for(int ☃x = 0; ☃x < this.field_202583_c; ++☃x) {
            int ☃xx = -5;
            this.func_175808_b(☃, Blocks.field_150322_A.func_176223_P(), ☃, -5, ☃x, ☃);
         }
      }

      IBlockState ☃ = Blocks.field_150372_bz.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.NORTH);
      IBlockState ☃x = Blocks.field_150372_bz.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.SOUTH);
      IBlockState ☃xx = Blocks.field_150372_bz.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.EAST);
      IBlockState ☃xxx = Blocks.field_150372_bz.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.WEST);
      this.func_175804_a(☃, ☃, 0, 0, 0, 4, 9, 4, Blocks.field_150322_A.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
      this.func_175804_a(☃, ☃, 1, 10, 1, 3, 10, 3, Blocks.field_150322_A.func_176223_P(), Blocks.field_150322_A.func_176223_P(), false);
      this.func_175811_a(☃, ☃, 2, 10, 0, ☃);
      this.func_175811_a(☃, ☃x, 2, 10, 4, ☃);
      this.func_175811_a(☃, ☃xx, 0, 10, 2, ☃);
      this.func_175811_a(☃, ☃xxx, 4, 10, 2, ☃);
      this.func_175804_a(
         ☃,
         ☃,
         this.field_202581_a - 5,
         0,
         0,
         this.field_202581_a - 1,
         9,
         4,
         Blocks.field_150322_A.func_176223_P(),
         Blocks.field_150350_a.func_176223_P(),
         false
      );
      this.func_175804_a(
         ☃,
         ☃,
         this.field_202581_a - 4,
         10,
         1,
         this.field_202581_a - 2,
         10,
         3,
         Blocks.field_150322_A.func_176223_P(),
         Blocks.field_150322_A.func_176223_P(),
         false
      );
      this.func_175811_a(☃, ☃, this.field_202581_a - 3, 10, 0, ☃);
      this.func_175811_a(☃, ☃x, this.field_202581_a - 3, 10, 4, ☃);
      this.func_175811_a(☃, ☃xx, this.field_202581_a - 5, 10, 2, ☃);
      this.func_175811_a(☃, ☃xxx, this.field_202581_a - 1, 10, 2, ☃);
      this.func_175804_a(☃, ☃, 8, 0, 0, 12, 4, 4, Blocks.field_150322_A.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
      this.func_175804_a(☃, ☃, 9, 1, 0, 11, 3, 4, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
      this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), 9, 1, 1, ☃);
      this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), 9, 2, 1, ☃);
      this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), 9, 3, 1, ☃);
      this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), 10, 3, 1, ☃);
      this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), 11, 3, 1, ☃);
      this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), 11, 2, 1, ☃);
      this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), 11, 1, 1, ☃);
      this.func_175804_a(☃, ☃, 4, 1, 1, 8, 3, 3, Blocks.field_150322_A.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
      this.func_175804_a(☃, ☃, 4, 1, 2, 8, 2, 2, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
      this.func_175804_a(☃, ☃, 12, 1, 1, 16, 3, 3, Blocks.field_150322_A.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
      this.func_175804_a(☃, ☃, 12, 1, 2, 16, 2, 2, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
      this.func_175804_a(
         ☃,
         ☃,
         5,
         4,
         5,
         this.field_202581_a - 6,
         4,
         this.field_202583_c - 6,
         Blocks.field_150322_A.func_176223_P(),
         Blocks.field_150322_A.func_176223_P(),
         false
      );
      this.func_175804_a(☃, ☃, 9, 4, 9, 11, 4, 11, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
      this.func_175804_a(☃, ☃, 8, 1, 8, 8, 3, 8, Blocks.field_196585_ak.func_176223_P(), Blocks.field_196585_ak.func_176223_P(), false);
      this.func_175804_a(☃, ☃, 12, 1, 8, 12, 3, 8, Blocks.field_196585_ak.func_176223_P(), Blocks.field_196585_ak.func_176223_P(), false);
      this.func_175804_a(☃, ☃, 8, 1, 12, 8, 3, 12, Blocks.field_196585_ak.func_176223_P(), Blocks.field_196585_ak.func_176223_P(), false);
      this.func_175804_a(☃, ☃, 12, 1, 12, 12, 3, 12, Blocks.field_196585_ak.func_176223_P(), Blocks.field_196585_ak.func_176223_P(), false);
      this.func_175804_a(☃, ☃, 1, 1, 5, 4, 4, 11, Blocks.field_150322_A.func_176223_P(), Blocks.field_150322_A.func_176223_P(), false);
      this.func_175804_a(
         ☃,
         ☃,
         this.field_202581_a - 5,
         1,
         5,
         this.field_202581_a - 2,
         4,
         11,
         Blocks.field_150322_A.func_176223_P(),
         Blocks.field_150322_A.func_176223_P(),
         false
      );
      this.func_175804_a(☃, ☃, 6, 7, 9, 6, 7, 11, Blocks.field_150322_A.func_176223_P(), Blocks.field_150322_A.func_176223_P(), false);
      this.func_175804_a(
         ☃,
         ☃,
         this.field_202581_a - 7,
         7,
         9,
         this.field_202581_a - 7,
         7,
         11,
         Blocks.field_150322_A.func_176223_P(),
         Blocks.field_150322_A.func_176223_P(),
         false
      );
      this.func_175804_a(☃, ☃, 5, 5, 9, 5, 7, 11, Blocks.field_196585_ak.func_176223_P(), Blocks.field_196585_ak.func_176223_P(), false);
      this.func_175804_a(
         ☃,
         ☃,
         this.field_202581_a - 6,
         5,
         9,
         this.field_202581_a - 6,
         7,
         11,
         Blocks.field_196585_ak.func_176223_P(),
         Blocks.field_196585_ak.func_176223_P(),
         false
      );
      this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 5, 5, 10, ☃);
      this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 5, 6, 10, ☃);
      this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 6, 6, 10, ☃);
      this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), this.field_202581_a - 6, 5, 10, ☃);
      this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), this.field_202581_a - 6, 6, 10, ☃);
      this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), this.field_202581_a - 7, 6, 10, ☃);
      this.func_175804_a(☃, ☃, 2, 4, 4, 2, 6, 4, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
      this.func_175804_a(
         ☃,
         ☃,
         this.field_202581_a - 3,
         4,
         4,
         this.field_202581_a - 3,
         6,
         4,
         Blocks.field_150350_a.func_176223_P(),
         Blocks.field_150350_a.func_176223_P(),
         false
      );
      this.func_175811_a(☃, ☃, 2, 4, 5, ☃);
      this.func_175811_a(☃, ☃, 2, 3, 4, ☃);
      this.func_175811_a(☃, ☃, this.field_202581_a - 3, 4, 5, ☃);
      this.func_175811_a(☃, ☃, this.field_202581_a - 3, 3, 4, ☃);
      this.func_175804_a(☃, ☃, 1, 1, 3, 2, 2, 3, Blocks.field_150322_A.func_176223_P(), Blocks.field_150322_A.func_176223_P(), false);
      this.func_175804_a(
         ☃,
         ☃,
         this.field_202581_a - 3,
         1,
         3,
         this.field_202581_a - 2,
         2,
         3,
         Blocks.field_150322_A.func_176223_P(),
         Blocks.field_150322_A.func_176223_P(),
         false
      );
      this.func_175811_a(☃, Blocks.field_150322_A.func_176223_P(), 1, 1, 2, ☃);
      this.func_175811_a(☃, Blocks.field_150322_A.func_176223_P(), this.field_202581_a - 2, 1, 2, ☃);
      this.func_175811_a(☃, Blocks.field_196640_bx.func_176223_P(), 1, 2, 2, ☃);
      this.func_175811_a(☃, Blocks.field_196640_bx.func_176223_P(), this.field_202581_a - 2, 2, 2, ☃);
      this.func_175811_a(☃, ☃xxx, 2, 1, 2, ☃);
      this.func_175811_a(☃, ☃xx, this.field_202581_a - 3, 1, 2, ☃);
      this.func_175804_a(☃, ☃, 4, 3, 5, 4, 3, 17, Blocks.field_150322_A.func_176223_P(), Blocks.field_150322_A.func_176223_P(), false);
      this.func_175804_a(
         ☃,
         ☃,
         this.field_202581_a - 5,
         3,
         5,
         this.field_202581_a - 5,
         3,
         17,
         Blocks.field_150322_A.func_176223_P(),
         Blocks.field_150322_A.func_176223_P(),
         false
      );
      this.func_175804_a(☃, ☃, 3, 1, 5, 4, 2, 16, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
      this.func_175804_a(
         ☃,
         ☃,
         this.field_202581_a - 6,
         1,
         5,
         this.field_202581_a - 5,
         2,
         16,
         Blocks.field_150350_a.func_176223_P(),
         Blocks.field_150350_a.func_176223_P(),
         false
      );

      for(int ☃xxxx = 5; ☃xxxx <= 17; ☃xxxx += 2) {
         this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), 4, 1, ☃xxxx, ☃);
         this.func_175811_a(☃, Blocks.field_196583_aj.func_176223_P(), 4, 2, ☃xxxx, ☃);
         this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), this.field_202581_a - 5, 1, ☃xxxx, ☃);
         this.func_175811_a(☃, Blocks.field_196583_aj.func_176223_P(), this.field_202581_a - 5, 2, ☃xxxx, ☃);
      }

      this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), 10, 0, 7, ☃);
      this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), 10, 0, 8, ☃);
      this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), 9, 0, 9, ☃);
      this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), 11, 0, 9, ☃);
      this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), 8, 0, 10, ☃);
      this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), 12, 0, 10, ☃);
      this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), 7, 0, 10, ☃);
      this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), 13, 0, 10, ☃);
      this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), 9, 0, 11, ☃);
      this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), 11, 0, 11, ☃);
      this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), 10, 0, 12, ☃);
      this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), 10, 0, 13, ☃);
      this.func_175811_a(☃, Blocks.field_196797_fz.func_176223_P(), 10, 0, 10, ☃);

      for(int ☃xxxx = 0; ☃xxxx <= this.field_202581_a - 1; ☃xxxx += this.field_202581_a - 1) {
         this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), ☃xxxx, 2, 1, ☃);
         this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), ☃xxxx, 2, 2, ☃);
         this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), ☃xxxx, 2, 3, ☃);
         this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), ☃xxxx, 3, 1, ☃);
         this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), ☃xxxx, 3, 2, ☃);
         this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), ☃xxxx, 3, 3, ☃);
         this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), ☃xxxx, 4, 1, ☃);
         this.func_175811_a(☃, Blocks.field_196583_aj.func_176223_P(), ☃xxxx, 4, 2, ☃);
         this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), ☃xxxx, 4, 3, ☃);
         this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), ☃xxxx, 5, 1, ☃);
         this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), ☃xxxx, 5, 2, ☃);
         this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), ☃xxxx, 5, 3, ☃);
         this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), ☃xxxx, 6, 1, ☃);
         this.func_175811_a(☃, Blocks.field_196583_aj.func_176223_P(), ☃xxxx, 6, 2, ☃);
         this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), ☃xxxx, 6, 3, ☃);
         this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), ☃xxxx, 7, 1, ☃);
         this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), ☃xxxx, 7, 2, ☃);
         this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), ☃xxxx, 7, 3, ☃);
         this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), ☃xxxx, 8, 1, ☃);
         this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), ☃xxxx, 8, 2, ☃);
         this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), ☃xxxx, 8, 3, ☃);
      }

      for(int ☃xxxx = 2; ☃xxxx <= this.field_202581_a - 3; ☃xxxx += this.field_202581_a - 3 - 2) {
         this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), ☃xxxx - 1, 2, 0, ☃);
         this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), ☃xxxx, 2, 0, ☃);
         this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), ☃xxxx + 1, 2, 0, ☃);
         this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), ☃xxxx - 1, 3, 0, ☃);
         this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), ☃xxxx, 3, 0, ☃);
         this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), ☃xxxx + 1, 3, 0, ☃);
         this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), ☃xxxx - 1, 4, 0, ☃);
         this.func_175811_a(☃, Blocks.field_196583_aj.func_176223_P(), ☃xxxx, 4, 0, ☃);
         this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), ☃xxxx + 1, 4, 0, ☃);
         this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), ☃xxxx - 1, 5, 0, ☃);
         this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), ☃xxxx, 5, 0, ☃);
         this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), ☃xxxx + 1, 5, 0, ☃);
         this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), ☃xxxx - 1, 6, 0, ☃);
         this.func_175811_a(☃, Blocks.field_196583_aj.func_176223_P(), ☃xxxx, 6, 0, ☃);
         this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), ☃xxxx + 1, 6, 0, ☃);
         this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), ☃xxxx - 1, 7, 0, ☃);
         this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), ☃xxxx, 7, 0, ☃);
         this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), ☃xxxx + 1, 7, 0, ☃);
         this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), ☃xxxx - 1, 8, 0, ☃);
         this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), ☃xxxx, 8, 0, ☃);
         this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), ☃xxxx + 1, 8, 0, ☃);
      }

      this.func_175804_a(☃, ☃, 8, 4, 0, 12, 6, 0, Blocks.field_196585_ak.func_176223_P(), Blocks.field_196585_ak.func_176223_P(), false);
      this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 8, 6, 0, ☃);
      this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 12, 6, 0, ☃);
      this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), 9, 5, 0, ☃);
      this.func_175811_a(☃, Blocks.field_196583_aj.func_176223_P(), 10, 5, 0, ☃);
      this.func_175811_a(☃, Blocks.field_196778_fp.func_176223_P(), 11, 5, 0, ☃);
      this.func_175804_a(☃, ☃, 8, -14, 8, 12, -11, 12, Blocks.field_196585_ak.func_176223_P(), Blocks.field_196585_ak.func_176223_P(), false);
      this.func_175804_a(☃, ☃, 8, -10, 8, 12, -10, 12, Blocks.field_196583_aj.func_176223_P(), Blocks.field_196583_aj.func_176223_P(), false);
      this.func_175804_a(☃, ☃, 8, -9, 8, 12, -9, 12, Blocks.field_196585_ak.func_176223_P(), Blocks.field_196585_ak.func_176223_P(), false);
      this.func_175804_a(☃, ☃, 8, -8, 8, 12, -1, 12, Blocks.field_150322_A.func_176223_P(), Blocks.field_150322_A.func_176223_P(), false);
      this.func_175804_a(☃, ☃, 9, -11, 9, 11, -1, 11, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
      this.func_175811_a(☃, Blocks.field_150456_au.func_176223_P(), 10, -11, 10, ☃);
      this.func_175804_a(☃, ☃, 9, -13, 9, 11, -13, 11, Blocks.field_150335_W.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
      this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 8, -11, 10, ☃);
      this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 8, -10, 10, ☃);
      this.func_175811_a(☃, Blocks.field_196583_aj.func_176223_P(), 7, -10, 10, ☃);
      this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), 7, -11, 10, ☃);
      this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 12, -11, 10, ☃);
      this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 12, -10, 10, ☃);
      this.func_175811_a(☃, Blocks.field_196583_aj.func_176223_P(), 13, -10, 10, ☃);
      this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), 13, -11, 10, ☃);
      this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 10, -11, 8, ☃);
      this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 10, -10, 8, ☃);
      this.func_175811_a(☃, Blocks.field_196583_aj.func_176223_P(), 10, -10, 7, ☃);
      this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), 10, -11, 7, ☃);
      this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 10, -11, 12, ☃);
      this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 10, -10, 12, ☃);
      this.func_175811_a(☃, Blocks.field_196583_aj.func_176223_P(), 10, -10, 13, ☃);
      this.func_175811_a(☃, Blocks.field_196585_ak.func_176223_P(), 10, -11, 13, ☃);

      for(EnumFacing ☃xxxx : EnumFacing.Plane.HORIZONTAL) {
         if (!this.field_202598_e[☃xxxx.func_176736_b()]) {
            int ☃xxxxx = ☃xxxx.func_82601_c() * 2;
            int ☃xxxxxx = ☃xxxx.func_82599_e() * 2;
            this.field_202598_e[☃xxxx.func_176736_b()] = this.func_186167_a(☃, ☃, ☃, 10 + ☃xxxxx, -11, 10 + ☃xxxxxx, LootTableList.field_186429_k);
         }
      }

      return true;
   }
}
