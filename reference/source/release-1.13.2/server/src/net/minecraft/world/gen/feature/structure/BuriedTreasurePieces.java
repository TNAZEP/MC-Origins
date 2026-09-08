package net.minecraft.world.gen.feature.structure;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

public class BuriedTreasurePieces {
   public static void func_204296_a() {
      StructureIO.func_143031_a(BuriedTreasurePieces.Piece.class, "BTP");
   }

   public static class Piece extends StructurePiece {
      public Piece() {
      }

      public Piece(BlockPos var1) {
         super(0);
         this.field_74887_e = new MutableBoundingBox(
            ☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p(), ☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p()
         );
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         int ☃ = ☃.func_201676_a(Heightmap.Type.OCEAN_FLOOR_WG, this.field_74887_e.field_78897_a, this.field_74887_e.field_78896_c);
         BlockPos.MutableBlockPos ☃x = new BlockPos.MutableBlockPos(this.field_74887_e.field_78897_a, ☃, this.field_74887_e.field_78896_c);

         while(☃x.func_177956_o() > 0) {
            IBlockState ☃xx = ☃.func_180495_p(☃x);
            IBlockState ☃xxx = ☃.func_180495_p(☃x.func_177977_b());
            if (☃xxx == Blocks.field_150322_A.func_176223_P()
               || ☃xxx == Blocks.field_150348_b.func_176223_P()
               || ☃xxx == Blocks.field_196656_g.func_176223_P()
               || ☃xxx == Blocks.field_196650_c.func_176223_P()
               || ☃xxx == Blocks.field_196654_e.func_176223_P()) {
               IBlockState ☃xxxx = !☃xx.func_196958_f() && !this.func_204295_a(☃xx) ? ☃xx : Blocks.field_150354_m.func_176223_P();

               for(EnumFacing ☃xxxxx : EnumFacing.values()) {
                  BlockPos ☃xxxxxx = ☃x.func_177972_a(☃xxxxx);
                  IBlockState ☃xxxxxxx = ☃.func_180495_p(☃xxxxxx);
                  if (☃xxxxxxx.func_196958_f() || this.func_204295_a(☃xxxxxxx)) {
                     BlockPos ☃xxxxxxxx = ☃xxxxxx.func_177977_b();
                     IBlockState ☃xxxxxxxxx = ☃.func_180495_p(☃xxxxxxxx);
                     if ((☃xxxxxxxxx.func_196958_f() || this.func_204295_a(☃xxxxxxxxx)) && ☃xxxxx != EnumFacing.UP) {
                        ☃.func_180501_a(☃xxxxxx, ☃xxx, 3);
                     } else {
                        ☃.func_180501_a(☃xxxxxx, ☃xxxx, 3);
                     }
                  }
               }

               return this.func_191080_a(
                  ☃,
                  ☃,
                  ☃,
                  new BlockPos(this.field_74887_e.field_78897_a, ☃x.func_177956_o(), this.field_74887_e.field_78896_c),
                  LootTableList.field_204312_r,
                  null
               );
            }

            ☃x.func_196234_d(0, -1, 0);
         }

         return false;
      }

      private boolean func_204295_a(IBlockState var1) {
         return ☃ == Blocks.field_150355_j.func_176223_P() || ☃ == Blocks.field_150353_l.func_176223_P();
      }
   }
}
