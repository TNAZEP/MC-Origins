package net.minecraft.entity.ai;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class EntityAIVillagerInteract extends EntityAIWatchClosestWithoutMoving {
   private int field_179478_e;
   private final EntityVillager field_179477_f;

   public EntityAIVillagerInteract(EntityVillager var1) {
      super(☃, EntityVillager.class, 3.0F, 0.02F);
      this.field_179477_f = ☃;
   }

   @Override
   public void func_75249_e() {
      super.func_75249_e();
      if (this.field_179477_f.func_175555_cq() && this.field_75334_a instanceof EntityVillager && ((EntityVillager)this.field_75334_a).func_175557_cr()) {
         this.field_179478_e = 10;
      } else {
         this.field_179478_e = 0;
      }
   }

   @Override
   public void func_75246_d() {
      super.func_75246_d();
      if (this.field_179478_e > 0) {
         --this.field_179478_e;
         if (this.field_179478_e == 0) {
            InventoryBasic ☃ = this.field_179477_f.func_175551_co();

            for(int ☃x = 0; ☃x < ☃.func_70302_i_(); ++☃x) {
               ItemStack ☃xx = ☃.func_70301_a(☃x);
               ItemStack ☃xxx = ItemStack.field_190927_a;
               if (!☃xx.func_190926_b()) {
                  Item ☃xxxx = ☃xx.func_77973_b();
                  if ((☃xxxx == Items.field_151025_P || ☃xxxx == Items.field_151174_bG || ☃xxxx == Items.field_151172_bF || ☃xxxx == Items.field_185164_cV)
                     && ☃xx.func_190916_E() > 3) {
                     int ☃xxxxx = ☃xx.func_190916_E() / 2;
                     ☃xx.func_190918_g(☃xxxxx);
                     ☃xxx = new ItemStack(☃xxxx, ☃xxxxx);
                  } else if (☃xxxx == Items.field_151015_O && ☃xx.func_190916_E() > 5) {
                     int ☃xxxx = ☃xx.func_190916_E() / 2 / 3 * 3;
                     int ☃xxxxx = ☃xxxx / 3;
                     ☃xx.func_190918_g(☃xxxx);
                     ☃xxx = new ItemStack(Items.field_151025_P, ☃xxxxx);
                  }

                  if (☃xx.func_190926_b()) {
                     ☃.func_70299_a(☃x, ItemStack.field_190927_a);
                  }
               }

               if (!☃xxx.func_190926_b()) {
                  double ☃xx = this.field_179477_f.field_70163_u - 0.3F + (double)this.field_179477_f.func_70047_e();
                  EntityItem ☃xxx = new EntityItem(
                     this.field_179477_f.field_70170_p, this.field_179477_f.field_70165_t, ☃xx, this.field_179477_f.field_70161_v, ☃xxx
                  );
                  float ☃xxxx = 0.3F;
                  float ☃xxxxx = this.field_179477_f.field_70759_as;
                  float ☃xxxxxx = this.field_179477_f.field_70125_A;
                  ☃xxx.field_70159_w = (double)(
                     -MathHelper.func_76126_a(☃xxxxx * (float) (Math.PI / 180.0)) * MathHelper.func_76134_b(☃xxxxxx * (float) (Math.PI / 180.0)) * 0.3F
                  );
                  ☃xxx.field_70179_y = (double)(
                     MathHelper.func_76134_b(☃xxxxx * (float) (Math.PI / 180.0)) * MathHelper.func_76134_b(☃xxxxxx * (float) (Math.PI / 180.0)) * 0.3F
                  );
                  ☃xxx.field_70181_x = (double)(-MathHelper.func_76126_a(☃xxxxxx * (float) (Math.PI / 180.0)) * 0.3F + 0.1F);
                  ☃xxx.func_174869_p();
                  this.field_179477_f.field_70170_p.func_72838_d(☃xxx);
                  break;
               }
            }
         }
      }
   }
}
