package net.minecraft.block.state;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPistonStructureHelper {
   private final World field_177261_a;
   private final BlockPos field_177259_b;
   private final boolean field_211724_c;
   private final BlockPos field_177260_c;
   private final EnumFacing field_177257_d;
   private final List<BlockPos> field_177258_e = Lists.<BlockPos>newArrayList();
   private final List<BlockPos> field_177256_f = Lists.<BlockPos>newArrayList();
   private final EnumFacing field_211906_h;

   public BlockPistonStructureHelper(World var1, BlockPos var2, EnumFacing var3, boolean var4) {
      this.field_177261_a = ☃;
      this.field_177259_b = ☃;
      this.field_211906_h = ☃;
      this.field_211724_c = ☃;
      if (☃) {
         this.field_177257_d = ☃;
         this.field_177260_c = ☃.func_177972_a(☃);
      } else {
         this.field_177257_d = ☃.func_176734_d();
         this.field_177260_c = ☃.func_177967_a(☃, 2);
      }
   }

   public boolean func_177253_a() {
      this.field_177258_e.clear();
      this.field_177256_f.clear();
      IBlockState ☃ = this.field_177261_a.func_180495_p(this.field_177260_c);
      if (!BlockPistonBase.func_185646_a(☃, this.field_177261_a, this.field_177260_c, this.field_177257_d, false, this.field_211906_h)) {
         if (this.field_211724_c && ☃.func_185905_o() == EnumPushReaction.DESTROY) {
            this.field_177256_f.add(this.field_177260_c);
            return true;
         } else {
            return false;
         }
      } else if (!this.func_177251_a(this.field_177260_c, this.field_177257_d)) {
         return false;
      } else {
         for(int ☃ = 0; ☃ < this.field_177258_e.size(); ++☃) {
            BlockPos ☃x = (BlockPos)this.field_177258_e.get(☃);
            if (this.field_177261_a.func_180495_p(☃x).func_177230_c() == Blocks.field_180399_cE && !this.func_177250_b(☃x)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean func_177251_a(BlockPos var1, EnumFacing var2) {
      IBlockState ☃ = this.field_177261_a.func_180495_p(☃);
      Block ☃x = ☃.func_177230_c();
      if (☃.func_196958_f()) {
         return true;
      } else if (!BlockPistonBase.func_185646_a(☃, this.field_177261_a, ☃, this.field_177257_d, false, ☃)) {
         return true;
      } else if (☃.equals(this.field_177259_b)) {
         return true;
      } else if (this.field_177258_e.contains(☃)) {
         return true;
      } else {
         int ☃ = 1;
         if (☃ + this.field_177258_e.size() > 12) {
            return false;
         } else {
            while(☃x == Blocks.field_180399_cE) {
               BlockPos ☃ = ☃.func_177967_a(this.field_177257_d.func_176734_d(), ☃);
               ☃ = this.field_177261_a.func_180495_p(☃);
               ☃x = ☃.func_177230_c();
               if (☃.func_196958_f()
                  || !BlockPistonBase.func_185646_a(☃, this.field_177261_a, ☃, this.field_177257_d, false, this.field_177257_d.func_176734_d())
                  || ☃.equals(this.field_177259_b)) {
                  break;
               }

               if (++☃ + this.field_177258_e.size() > 12) {
                  return false;
               }
            }

            int ☃ = 0;

            for(int ☃x = ☃ - 1; ☃x >= 0; --☃x) {
               this.field_177258_e.add(☃.func_177967_a(this.field_177257_d.func_176734_d(), ☃x));
               ++☃;
            }

            int ☃x = 1;

            while(true) {
               BlockPos ☃xx = ☃.func_177967_a(this.field_177257_d, ☃x);
               int ☃xxx = this.field_177258_e.indexOf(☃xx);
               if (☃xxx > -1) {
                  this.func_177255_a(☃, ☃xxx);

                  for(int ☃xxxx = 0; ☃xxxx <= ☃xxx + ☃; ++☃xxxx) {
                     BlockPos ☃xxxxx = (BlockPos)this.field_177258_e.get(☃xxxx);
                     if (this.field_177261_a.func_180495_p(☃xxxxx).func_177230_c() == Blocks.field_180399_cE && !this.func_177250_b(☃xxxxx)) {
                        return false;
                     }
                  }

                  return true;
               }

               ☃ = this.field_177261_a.func_180495_p(☃xx);
               if (☃.func_196958_f()) {
                  return true;
               }

               if (!BlockPistonBase.func_185646_a(☃, this.field_177261_a, ☃xx, this.field_177257_d, true, this.field_177257_d)
                  || ☃xx.equals(this.field_177259_b)) {
                  return false;
               }

               if (☃.func_185905_o() == EnumPushReaction.DESTROY) {
                  this.field_177256_f.add(☃xx);
                  return true;
               }

               if (this.field_177258_e.size() >= 12) {
                  return false;
               }

               this.field_177258_e.add(☃xx);
               ++☃;
               ++☃x;
            }
         }
      }
   }

   private void func_177255_a(int var1, int var2) {
      List<BlockPos> ☃ = Lists.<BlockPos>newArrayList();
      List<BlockPos> ☃x = Lists.<BlockPos>newArrayList();
      List<BlockPos> ☃xx = Lists.<BlockPos>newArrayList();
      ☃.addAll(this.field_177258_e.subList(0, ☃));
      ☃x.addAll(this.field_177258_e.subList(this.field_177258_e.size() - ☃, this.field_177258_e.size()));
      ☃xx.addAll(this.field_177258_e.subList(☃, this.field_177258_e.size() - ☃));
      this.field_177258_e.clear();
      this.field_177258_e.addAll(☃);
      this.field_177258_e.addAll(☃x);
      this.field_177258_e.addAll(☃xx);
   }

   private boolean func_177250_b(BlockPos var1) {
      for(EnumFacing ☃ : EnumFacing.values()) {
         if (☃.func_176740_k() != this.field_177257_d.func_176740_k() && !this.func_177251_a(☃.func_177972_a(☃), ☃)) {
            return false;
         }
      }

      return true;
   }

   public List<BlockPos> func_177254_c() {
      return this.field_177258_e;
   }

   public List<BlockPos> func_177252_d() {
      return this.field_177256_f;
   }
}
