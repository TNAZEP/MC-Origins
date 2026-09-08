package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;

public class EntityAIHarvestFarmland extends EntityAIMoveToBlock {
   private final EntityVillager field_179504_c;
   private boolean field_179502_d;
   private boolean field_179503_e;
   private int field_179501_f;

   public EntityAIHarvestFarmland(EntityVillager var1, double var2) {
      super(☃, ☃, 16);
      this.field_179504_c = ☃;
   }

   @Override
   public boolean func_75250_a() {
      if (this.field_179496_a <= 0) {
         if (!this.field_179504_c.field_70170_p.func_82736_K().func_82766_b("mobGriefing")) {
            return false;
         }

         this.field_179501_f = -1;
         this.field_179502_d = this.field_179504_c.func_175556_cs();
         this.field_179503_e = this.field_179504_c.func_175557_cr();
      }

      return super.func_75250_a();
   }

   @Override
   public boolean func_75253_b() {
      return this.field_179501_f >= 0 && super.func_75253_b();
   }

   @Override
   public void func_75246_d() {
      super.func_75246_d();
      this.field_179504_c
         .func_70671_ap()
         .func_75650_a(
            (double)this.field_179494_b.func_177958_n() + 0.5,
            (double)(this.field_179494_b.func_177956_o() + 1),
            (double)this.field_179494_b.func_177952_p() + 0.5,
            10.0F,
            (float)this.field_179504_c.func_70646_bf()
         );
      if (this.func_179487_f()) {
         IWorld ☃ = this.field_179504_c.field_70170_p;
         BlockPos ☃x = this.field_179494_b.func_177984_a();
         IBlockState ☃xx = ☃.func_180495_p(☃x);
         Block ☃xxx = ☃xx.func_177230_c();
         if (this.field_179501_f == 0 && ☃xxx instanceof BlockCrops && ((BlockCrops)☃xxx).func_185525_y(☃xx)) {
            ☃.func_175655_b(☃x, true);
         } else if (this.field_179501_f == 1 && ☃xx.func_196958_f()) {
            InventoryBasic ☃ = this.field_179504_c.func_175551_co();

            for(int ☃x = 0; ☃x < ☃.func_70302_i_(); ++☃x) {
               ItemStack ☃xx = ☃.func_70301_a(☃x);
               boolean ☃xxx = false;
               if (!☃xx.func_190926_b()) {
                  if (☃xx.func_77973_b() == Items.field_151014_N) {
                     ☃.func_180501_a(☃x, Blocks.field_150464_aj.func_176223_P(), 3);
                     ☃xxx = true;
                  } else if (☃xx.func_77973_b() == Items.field_151174_bG) {
                     ☃.func_180501_a(☃x, Blocks.field_150469_bN.func_176223_P(), 3);
                     ☃xxx = true;
                  } else if (☃xx.func_77973_b() == Items.field_151172_bF) {
                     ☃.func_180501_a(☃x, Blocks.field_150459_bM.func_176223_P(), 3);
                     ☃xxx = true;
                  } else if (☃xx.func_77973_b() == Items.field_185163_cU) {
                     ☃.func_180501_a(☃x, Blocks.field_185773_cZ.func_176223_P(), 3);
                     ☃xxx = true;
                  }
               }

               if (☃xxx) {
                  ☃xx.func_190918_g(1);
                  if (☃xx.func_190926_b()) {
                     ☃.func_70299_a(☃x, ItemStack.field_190927_a);
                  }
                  break;
               }
            }
         }

         this.field_179501_f = -1;
         this.field_179496_a = 10;
      }
   }

   @Override
   protected boolean func_179488_a(IWorldReaderBase var1, BlockPos var2) {
      Block ☃ = ☃.func_180495_p(☃).func_177230_c();
      if (☃ == Blocks.field_150458_ak) {
         ☃ = ☃.func_177984_a();
         IBlockState ☃x = ☃.func_180495_p(☃);
         ☃ = ☃x.func_177230_c();
         if (☃ instanceof BlockCrops && ((BlockCrops)☃).func_185525_y(☃x) && this.field_179503_e && (this.field_179501_f == 0 || this.field_179501_f < 0)) {
            this.field_179501_f = 0;
            return true;
         }

         if (☃x.func_196958_f() && this.field_179502_d && (this.field_179501_f == 1 || this.field_179501_f < 0)) {
            this.field_179501_f = 1;
            return true;
         }
      }

      return false;
   }
}
