package net.minecraft.server.management;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockStructure;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameType;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class PlayerInteractionManager {
   public World field_73092_a;
   public EntityPlayerMP field_73090_b;
   private GameType field_73091_c = GameType.NOT_SET;
   private boolean field_73088_d;
   private int field_73089_e;
   private BlockPos field_180240_f = BlockPos.field_177992_a;
   private int field_73100_i;
   private boolean field_73097_j;
   private BlockPos field_180241_i = BlockPos.field_177992_a;
   private int field_73093_n;
   private int field_73094_o = -1;

   public PlayerInteractionManager(World var1) {
      this.field_73092_a = ☃;
   }

   public void func_73076_a(GameType var1) {
      this.field_73091_c = ☃;
      ☃.func_77147_a(this.field_73090_b.field_71075_bZ);
      this.field_73090_b.func_71016_p();
      this.field_73090_b
         .field_71133_b
         .func_184103_al()
         .func_148540_a(new SPacketPlayerListItem(SPacketPlayerListItem.Action.UPDATE_GAME_MODE, this.field_73090_b));
      this.field_73092_a.func_72854_c();
   }

   public GameType func_73081_b() {
      return this.field_73091_c;
   }

   public boolean func_180239_c() {
      return this.field_73091_c.func_77144_e();
   }

   public boolean func_73083_d() {
      return this.field_73091_c.func_77145_d();
   }

   public void func_73077_b(GameType var1) {
      if (this.field_73091_c == GameType.NOT_SET) {
         this.field_73091_c = ☃;
      }

      this.func_73076_a(this.field_73091_c);
   }

   public void func_73075_a() {
      ++this.field_73100_i;
      if (this.field_73097_j) {
         int ☃ = this.field_73100_i - this.field_73093_n;
         IBlockState ☃x = this.field_73092_a.func_180495_p(this.field_180241_i);
         if (☃x.func_196958_f()) {
            this.field_73097_j = false;
         } else {
            float ☃ = ☃x.func_185903_a(this.field_73090_b, this.field_73090_b.field_70170_p, this.field_180241_i) * (float)(☃ + 1);
            int ☃x = (int)(☃ * 10.0F);
            if (☃x != this.field_73094_o) {
               this.field_73092_a.func_175715_c(this.field_73090_b.func_145782_y(), this.field_180241_i, ☃x);
               this.field_73094_o = ☃x;
            }

            if (☃ >= 1.0F) {
               this.field_73097_j = false;
               this.func_180237_b(this.field_180241_i);
            }
         }
      } else if (this.field_73088_d) {
         IBlockState ☃ = this.field_73092_a.func_180495_p(this.field_180240_f);
         if (☃.func_196958_f()) {
            this.field_73092_a.func_175715_c(this.field_73090_b.func_145782_y(), this.field_180240_f, -1);
            this.field_73094_o = -1;
            this.field_73088_d = false;
         } else {
            int ☃ = this.field_73100_i - this.field_73089_e;
            float ☃x = ☃.func_185903_a(this.field_73090_b, this.field_73090_b.field_70170_p, this.field_180241_i) * (float)(☃ + 1);
            int ☃xx = (int)(☃x * 10.0F);
            if (☃xx != this.field_73094_o) {
               this.field_73092_a.func_175715_c(this.field_73090_b.func_145782_y(), this.field_180240_f, ☃xx);
               this.field_73094_o = ☃xx;
            }
         }
      }
   }

   public void func_180784_a(BlockPos var1, EnumFacing var2) {
      if (this.func_73083_d()) {
         if (!this.field_73092_a.func_175719_a(null, ☃, ☃)) {
            this.func_180237_b(☃);
         }
      } else {
         if (this.field_73091_c.func_82752_c()) {
            if (this.field_73091_c == GameType.SPECTATOR) {
               return;
            }

            if (!this.field_73090_b.func_175142_cm()) {
               ItemStack ☃ = this.field_73090_b.func_184614_ca();
               if (☃.func_190926_b()) {
                  return;
               }

               BlockWorldState ☃ = new BlockWorldState(this.field_73092_a, ☃, false);
               if (!☃.func_206848_a(this.field_73092_a.func_205772_D(), ☃)) {
                  return;
               }
            }
         }

         this.field_73092_a.func_175719_a(null, ☃, ☃);
         this.field_73089_e = this.field_73100_i;
         float ☃ = 1.0F;
         IBlockState ☃x = this.field_73092_a.func_180495_p(☃);
         if (!☃x.func_196958_f()) {
            ☃x.func_196942_a(this.field_73092_a, ☃, this.field_73090_b);
            ☃ = ☃x.func_185903_a(this.field_73090_b, this.field_73090_b.field_70170_p, ☃);
         }

         if (!☃x.func_196958_f() && ☃ >= 1.0F) {
            this.func_180237_b(☃);
         } else {
            this.field_73088_d = true;
            this.field_180240_f = ☃;
            int ☃ = (int)(☃ * 10.0F);
            this.field_73092_a.func_175715_c(this.field_73090_b.func_145782_y(), ☃, ☃);
            this.field_73090_b.field_71135_a.func_147359_a(new SPacketBlockChange(this.field_73092_a, ☃));
            this.field_73094_o = ☃;
         }
      }
   }

   public void func_180785_a(BlockPos var1) {
      if (☃.equals(this.field_180240_f)) {
         int ☃ = this.field_73100_i - this.field_73089_e;
         IBlockState ☃x = this.field_73092_a.func_180495_p(☃);
         if (!☃x.func_196958_f()) {
            float ☃xx = ☃x.func_185903_a(this.field_73090_b, this.field_73090_b.field_70170_p, ☃) * (float)(☃ + 1);
            if (☃xx >= 0.7F) {
               this.field_73088_d = false;
               this.field_73092_a.func_175715_c(this.field_73090_b.func_145782_y(), ☃, -1);
               this.func_180237_b(☃);
            } else if (!this.field_73097_j) {
               this.field_73088_d = false;
               this.field_73097_j = true;
               this.field_180241_i = ☃;
               this.field_73093_n = this.field_73089_e;
            }
         }
      }
   }

   public void func_180238_e() {
      this.field_73088_d = false;
      this.field_73092_a.func_175715_c(this.field_73090_b.func_145782_y(), this.field_180240_f, -1);
   }

   private boolean func_180235_c(BlockPos var1) {
      IBlockState ☃ = this.field_73092_a.func_180495_p(☃);
      ☃.func_177230_c().func_176208_a(this.field_73092_a, ☃, ☃, this.field_73090_b);
      boolean ☃x = this.field_73092_a.func_175698_g(☃);
      if (☃x) {
         ☃.func_177230_c().func_176206_d(this.field_73092_a, ☃, ☃);
      }

      return ☃x;
   }

   public boolean func_180237_b(BlockPos var1) {
      IBlockState ☃ = this.field_73092_a.func_180495_p(☃);
      if (!this.field_73090_b.func_184614_ca().func_77973_b().func_195938_a(☃, this.field_73092_a, ☃, this.field_73090_b)) {
         return false;
      } else {
         TileEntity ☃ = this.field_73092_a.func_175625_s(☃);
         Block ☃x = ☃.func_177230_c();
         if ((☃x instanceof BlockCommandBlock || ☃x instanceof BlockStructure) && !this.field_73090_b.func_195070_dx()) {
            this.field_73092_a.func_184138_a(☃, ☃, ☃, 3);
            return false;
         } else {
            if (this.field_73091_c.func_82752_c()) {
               if (this.field_73091_c == GameType.SPECTATOR) {
                  return false;
               }

               if (!this.field_73090_b.func_175142_cm()) {
                  ItemStack ☃ = this.field_73090_b.func_184614_ca();
                  if (☃.func_190926_b()) {
                     return false;
                  }

                  BlockWorldState ☃ = new BlockWorldState(this.field_73092_a, ☃, false);
                  if (!☃.func_206848_a(this.field_73092_a.func_205772_D(), ☃)) {
                     return false;
                  }
               }
            }

            boolean ☃ = this.func_180235_c(☃);
            if (!this.func_73083_d()) {
               ItemStack ☃x = this.field_73090_b.func_184614_ca();
               boolean ☃xx = this.field_73090_b.func_184823_b(☃);
               ☃x.func_179548_a(this.field_73092_a, ☃, ☃, this.field_73090_b);
               if (☃ && ☃xx) {
                  ItemStack ☃xxx = ☃x.func_190926_b() ? ItemStack.field_190927_a : ☃x.func_77946_l();
                  ☃.func_177230_c().func_180657_a(this.field_73092_a, this.field_73090_b, ☃, ☃, ☃, ☃xxx);
               }
            }

            return ☃;
         }
      }
   }

   public EnumActionResult func_187250_a(EntityPlayer var1, World var2, ItemStack var3, EnumHand var4) {
      if (this.field_73091_c == GameType.SPECTATOR) {
         return EnumActionResult.PASS;
      } else if (☃.func_184811_cZ().func_185141_a(☃.func_77973_b())) {
         return EnumActionResult.PASS;
      } else {
         int ☃ = ☃.func_190916_E();
         int ☃x = ☃.func_77952_i();
         ActionResult<ItemStack> ☃xx = ☃.func_77957_a(☃, ☃, ☃);
         ItemStack ☃xxx = ☃xx.func_188398_b();
         if (☃xxx == ☃ && ☃xxx.func_190916_E() == ☃ && ☃xxx.func_77988_m() <= 0 && ☃xxx.func_77952_i() == ☃x) {
            return ☃xx.func_188397_a();
         } else if (☃xx.func_188397_a() == EnumActionResult.FAIL && ☃xxx.func_77988_m() > 0 && !☃.func_184587_cr()) {
            return ☃xx.func_188397_a();
         } else {
            ☃.func_184611_a(☃, ☃xxx);
            if (this.func_73083_d()) {
               ☃xxx.func_190920_e(☃);
               if (☃xxx.func_77984_f()) {
                  ☃xxx.func_196085_b(☃x);
               }
            }

            if (☃xxx.func_190926_b()) {
               ☃.func_184611_a(☃, ItemStack.field_190927_a);
            }

            if (!☃.func_184587_cr()) {
               ((EntityPlayerMP)☃).func_71120_a(☃.field_71069_bz);
            }

            return ☃xx.func_188397_a();
         }
      }
   }

   public EnumActionResult func_187251_a(
      EntityPlayer var1, World var2, ItemStack var3, EnumHand var4, BlockPos var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      IBlockState ☃ = ☃.func_180495_p(☃);
      if (this.field_73091_c == GameType.SPECTATOR) {
         TileEntity ☃x = ☃.func_175625_s(☃);
         if (☃x instanceof ILockableContainer) {
            Block ☃xx = ☃.func_177230_c();
            ILockableContainer ☃xxx = (ILockableContainer)☃x;
            if (☃xxx instanceof TileEntityChest && ☃xx instanceof BlockChest) {
               ☃xxx = ((BlockChest)☃xx).func_196309_a(☃, ☃, ☃, false);
            }

            if (☃xxx != null) {
               ☃.func_71007_a(☃xxx);
               return EnumActionResult.SUCCESS;
            }
         } else if (☃x instanceof IInventory) {
            ☃.func_71007_a((IInventory)☃x);
            return EnumActionResult.SUCCESS;
         }

         return EnumActionResult.PASS;
      } else {
         boolean ☃ = !☃.func_184614_ca().func_190926_b() || !☃.func_184592_cb().func_190926_b();
         boolean ☃x = ☃.func_70093_af() && ☃;
         if (!☃x && ☃.func_196943_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃)) {
            return EnumActionResult.SUCCESS;
         } else if (!☃.func_190926_b() && !☃.func_184811_cZ().func_185141_a(☃.func_77973_b())) {
            ItemUseContext ☃ = new ItemUseContext(☃, ☃.func_184586_b(☃), ☃, ☃, ☃, ☃, ☃);
            if (this.func_73083_d()) {
               int ☃x = ☃.func_190916_E();
               EnumActionResult ☃xx = ☃.func_196084_a(☃);
               ☃.func_190920_e(☃x);
               return ☃xx;
            } else {
               return ☃.func_196084_a(☃);
            }
         } else {
            return EnumActionResult.PASS;
         }
      }
   }

   public void func_73080_a(WorldServer var1) {
      this.field_73092_a = ☃;
   }
}
