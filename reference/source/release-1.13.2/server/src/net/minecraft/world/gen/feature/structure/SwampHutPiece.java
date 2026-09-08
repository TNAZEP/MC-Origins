package net.minecraft.world.gen.feature.structure;

import java.util.Random;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class SwampHutPiece extends ScatteredStructurePiece {
   private boolean field_202596_e;

   public static void func_202595_b() {
      StructureIO.func_143031_a(SwampHutPiece.class, "TeSH");
   }

   public SwampHutPiece() {
   }

   public SwampHutPiece(Random var1, int var2, int var3) {
      super(☃, ☃, 64, ☃, 7, 7, 9);
   }

   @Override
   protected void func_143012_a(NBTTagCompound var1) {
      super.func_143012_a(☃);
      ☃.func_74757_a("Witch", this.field_202596_e);
   }

   @Override
   protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
      super.func_143011_b(☃, ☃);
      this.field_202596_e = ☃.func_74767_n("Witch");
   }

   @Override
   public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
      if (!this.func_202580_a(☃, ☃, 0)) {
         return false;
      } else {
         this.func_175804_a(☃, ☃, 1, 1, 1, 5, 1, 7, Blocks.field_196664_o.func_176223_P(), Blocks.field_196664_o.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 4, 2, 5, 4, 7, Blocks.field_196664_o.func_176223_P(), Blocks.field_196664_o.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 2, 1, 0, 4, 1, 0, Blocks.field_196664_o.func_176223_P(), Blocks.field_196664_o.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 2, 2, 2, 3, 3, 2, Blocks.field_196664_o.func_176223_P(), Blocks.field_196664_o.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 2, 3, 1, 3, 6, Blocks.field_196664_o.func_176223_P(), Blocks.field_196664_o.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 5, 2, 3, 5, 3, 6, Blocks.field_196664_o.func_176223_P(), Blocks.field_196664_o.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 2, 2, 7, 4, 3, 7, Blocks.field_196664_o.func_176223_P(), Blocks.field_196664_o.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 0, 2, 1, 3, 2, Blocks.field_196617_K.func_176223_P(), Blocks.field_196617_K.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 5, 0, 2, 5, 3, 2, Blocks.field_196617_K.func_176223_P(), Blocks.field_196617_K.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 0, 7, 1, 3, 7, Blocks.field_196617_K.func_176223_P(), Blocks.field_196617_K.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 5, 0, 7, 5, 3, 7, Blocks.field_196617_K.func_176223_P(), Blocks.field_196617_K.func_176223_P(), false);
         this.func_175811_a(☃, Blocks.field_180407_aO.func_176223_P(), 2, 3, 2, ☃);
         this.func_175811_a(☃, Blocks.field_180407_aO.func_176223_P(), 3, 3, 7, ☃);
         this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 1, 3, 4, ☃);
         this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 5, 3, 4, ☃);
         this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 5, 3, 5, ☃);
         this.func_175811_a(☃, Blocks.field_196756_ey.func_176223_P(), 1, 3, 5, ☃);
         this.func_175811_a(☃, Blocks.field_150462_ai.func_176223_P(), 3, 2, 6, ☃);
         this.func_175811_a(☃, Blocks.field_150383_bp.func_176223_P(), 4, 2, 6, ☃);
         this.func_175811_a(☃, Blocks.field_180407_aO.func_176223_P(), 1, 2, 1, ☃);
         this.func_175811_a(☃, Blocks.field_180407_aO.func_176223_P(), 5, 2, 1, ☃);
         IBlockState ☃ = Blocks.field_150485_bF.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.NORTH);
         IBlockState ☃x = Blocks.field_150485_bF.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.EAST);
         IBlockState ☃xx = Blocks.field_150485_bF.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.WEST);
         IBlockState ☃xxx = Blocks.field_150485_bF.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.SOUTH);
         this.func_175804_a(☃, ☃, 0, 4, 1, 6, 4, 1, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 0, 4, 2, 0, 4, 7, ☃x, ☃x, false);
         this.func_175804_a(☃, ☃, 6, 4, 2, 6, 4, 7, ☃xx, ☃xx, false);
         this.func_175804_a(☃, ☃, 0, 4, 8, 6, 4, 8, ☃xxx, ☃xxx, false);
         this.func_175811_a(☃, ☃.func_206870_a(BlockStairs.field_176310_M, StairsShape.OUTER_RIGHT), 0, 4, 1, ☃);
         this.func_175811_a(☃, ☃.func_206870_a(BlockStairs.field_176310_M, StairsShape.OUTER_LEFT), 6, 4, 1, ☃);
         this.func_175811_a(☃, ☃xxx.func_206870_a(BlockStairs.field_176310_M, StairsShape.OUTER_LEFT), 0, 4, 8, ☃);
         this.func_175811_a(☃, ☃xxx.func_206870_a(BlockStairs.field_176310_M, StairsShape.OUTER_RIGHT), 6, 4, 8, ☃);

         for(int ☃xxxx = 2; ☃xxxx <= 7; ☃xxxx += 5) {
            for(int ☃xxxxx = 1; ☃xxxxx <= 5; ☃xxxxx += 4) {
               this.func_175808_b(☃, Blocks.field_196617_K.func_176223_P(), ☃xxxxx, -1, ☃xxxx, ☃);
            }
         }

         if (!this.field_202596_e) {
            int ☃xxxx = this.func_74865_a(2, 5);
            int ☃xxxxx = this.func_74862_a(2);
            int ☃xxxxxx = this.func_74873_b(2, 5);
            if (☃.func_175898_b(new BlockPos(☃xxxx, ☃xxxxx, ☃xxxxxx))) {
               this.field_202596_e = true;
               EntityWitch ☃xxxxxxx = new EntityWitch(☃.func_201672_e());
               ☃xxxxxxx.func_110163_bv();
               ☃xxxxxxx.func_70012_b((double)☃xxxx + 0.5, (double)☃xxxxx, (double)☃xxxxxx + 0.5, 0.0F, 0.0F);
               ☃xxxxxxx.func_204210_a(☃.func_175649_E(new BlockPos(☃xxxx, ☃xxxxx, ☃xxxxxx)), null, null);
               ☃.func_72838_d(☃xxxxxxx);
            }
         }

         return true;
      }
   }
}
