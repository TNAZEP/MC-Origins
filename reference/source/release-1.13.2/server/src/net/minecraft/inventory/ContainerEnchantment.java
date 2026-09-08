package net.minecraft.inventory;

import java.util.List;
import java.util.Random;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ContainerEnchantment extends Container {
   public IInventory field_75168_e = new InventoryBasic(new TextComponentString("Enchant"), 2) {
      @Override
      public int func_70297_j_() {
         return 64;
      }

      @Override
      public void func_70296_d() {
         super.func_70296_d();
         ContainerEnchantment.this.func_75130_a(this);
      }
   };
   private final World field_75172_h;
   private final BlockPos field_178150_j;
   private final Random field_75169_l = new Random();
   public int field_178149_f;
   public int[] field_75167_g = new int[3];
   public int[] field_185001_h = new int[]{-1, -1, -1};
   public int[] field_185002_i = new int[]{-1, -1, -1};

   public ContainerEnchantment(InventoryPlayer var1, World var2, BlockPos var3) {
      this.field_75172_h = ☃;
      this.field_178150_j = ☃;
      this.field_178149_f = ☃.field_70458_d.func_175138_ci();
      this.func_75146_a(new Slot(this.field_75168_e, 0, 15, 47) {
         @Override
         public boolean func_75214_a(ItemStack var1) {
            return true;
         }

         @Override
         public int func_75219_a() {
            return 1;
         }
      });
      this.func_75146_a(new Slot(this.field_75168_e, 1, 35, 47) {
         @Override
         public boolean func_75214_a(ItemStack var1) {
            return ☃.func_77973_b() == Items.field_196128_bn;
         }
      });

      for(int ☃ = 0; ☃ < 3; ++☃) {
         for(int ☃x = 0; ☃x < 9; ++☃x) {
            this.func_75146_a(new Slot(☃, ☃x + ☃ * 9 + 9, 8 + ☃x * 18, 84 + ☃ * 18));
         }
      }

      for(int ☃ = 0; ☃ < 9; ++☃) {
         this.func_75146_a(new Slot(☃, ☃, 8 + ☃ * 18, 142));
      }
   }

   protected void func_185000_c(IContainerListener var1) {
      ☃.func_71112_a(this, 0, this.field_75167_g[0]);
      ☃.func_71112_a(this, 1, this.field_75167_g[1]);
      ☃.func_71112_a(this, 2, this.field_75167_g[2]);
      ☃.func_71112_a(this, 3, this.field_178149_f & -16);
      ☃.func_71112_a(this, 4, this.field_185001_h[0]);
      ☃.func_71112_a(this, 5, this.field_185001_h[1]);
      ☃.func_71112_a(this, 6, this.field_185001_h[2]);
      ☃.func_71112_a(this, 7, this.field_185002_i[0]);
      ☃.func_71112_a(this, 8, this.field_185002_i[1]);
      ☃.func_71112_a(this, 9, this.field_185002_i[2]);
   }

   @Override
   public void func_75132_a(IContainerListener var1) {
      super.func_75132_a(☃);
      this.func_185000_c(☃);
   }

   @Override
   public void func_75142_b() {
      super.func_75142_b();

      for(int ☃ = 0; ☃ < this.field_75149_d.size(); ++☃) {
         IContainerListener ☃x = (IContainerListener)this.field_75149_d.get(☃);
         this.func_185000_c(☃x);
      }
   }

   @Override
   public void func_75130_a(IInventory var1) {
      if (☃ == this.field_75168_e) {
         ItemStack ☃ = ☃.func_70301_a(0);
         if (!☃.func_190926_b() && ☃.func_77956_u()) {
            if (!this.field_75172_h.field_72995_K) {
               int ☃x = 0;

               for(int ☃xx = -1; ☃xx <= 1; ++☃xx) {
                  for(int ☃xxx = -1; ☃xxx <= 1; ++☃xxx) {
                     if ((☃xx != 0 || ☃xxx != 0)
                        && this.field_75172_h.func_175623_d(this.field_178150_j.func_177982_a(☃xxx, 0, ☃xx))
                        && this.field_75172_h.func_175623_d(this.field_178150_j.func_177982_a(☃xxx, 1, ☃xx))) {
                        if (this.field_75172_h.func_180495_p(this.field_178150_j.func_177982_a(☃xxx * 2, 0, ☃xx * 2)).func_177230_c() == Blocks.field_150342_X) {
                           ++☃x;
                        }

                        if (this.field_75172_h.func_180495_p(this.field_178150_j.func_177982_a(☃xxx * 2, 1, ☃xx * 2)).func_177230_c() == Blocks.field_150342_X) {
                           ++☃x;
                        }

                        if (☃xxx != 0 && ☃xx != 0) {
                           if (this.field_75172_h.func_180495_p(this.field_178150_j.func_177982_a(☃xxx * 2, 0, ☃xx)).func_177230_c() == Blocks.field_150342_X) {
                              ++☃x;
                           }

                           if (this.field_75172_h.func_180495_p(this.field_178150_j.func_177982_a(☃xxx * 2, 1, ☃xx)).func_177230_c() == Blocks.field_150342_X) {
                              ++☃x;
                           }

                           if (this.field_75172_h.func_180495_p(this.field_178150_j.func_177982_a(☃xxx, 0, ☃xx * 2)).func_177230_c() == Blocks.field_150342_X) {
                              ++☃x;
                           }

                           if (this.field_75172_h.func_180495_p(this.field_178150_j.func_177982_a(☃xxx, 1, ☃xx * 2)).func_177230_c() == Blocks.field_150342_X) {
                              ++☃x;
                           }
                        }
                     }
                  }
               }

               this.field_75169_l.setSeed((long)this.field_178149_f);

               for(int ☃xx = 0; ☃xx < 3; ++☃xx) {
                  this.field_75167_g[☃xx] = EnchantmentHelper.func_77514_a(this.field_75169_l, ☃xx, ☃x, ☃);
                  this.field_185001_h[☃xx] = -1;
                  this.field_185002_i[☃xx] = -1;
                  if (this.field_75167_g[☃xx] < ☃xx + 1) {
                     this.field_75167_g[☃xx] = 0;
                  }
               }

               for(int ☃xx = 0; ☃xx < 3; ++☃xx) {
                  if (this.field_75167_g[☃xx] > 0) {
                     List<EnchantmentData> ☃xxx = this.func_178148_a(☃, ☃xx, this.field_75167_g[☃xx]);
                     if (☃xxx != null && !☃xxx.isEmpty()) {
                        EnchantmentData ☃xxxx = (EnchantmentData)☃xxx.get(this.field_75169_l.nextInt(☃xxx.size()));
                        this.field_185001_h[☃xx] = IRegistry.field_212628_q.func_148757_b(☃xxxx.field_76302_b);
                        this.field_185002_i[☃xx] = ☃xxxx.field_76303_c;
                     }
                  }
               }

               this.func_75142_b();
            }
         } else {
            for(int ☃ = 0; ☃ < 3; ++☃) {
               this.field_75167_g[☃] = 0;
               this.field_185001_h[☃] = -1;
               this.field_185002_i[☃] = -1;
            }
         }
      }
   }

   @Override
   public boolean func_75140_a(EntityPlayer var1, int var2) {
      ItemStack ☃ = this.field_75168_e.func_70301_a(0);
      ItemStack ☃x = this.field_75168_e.func_70301_a(1);
      int ☃xx = ☃ + 1;
      if ((☃x.func_190926_b() || ☃x.func_190916_E() < ☃xx) && !☃.field_71075_bZ.field_75098_d) {
         return false;
      } else if (this.field_75167_g[☃] > 0
         && !☃.func_190926_b()
         && (☃.field_71068_ca >= ☃xx && ☃.field_71068_ca >= this.field_75167_g[☃] || ☃.field_71075_bZ.field_75098_d)) {
         if (!this.field_75172_h.field_72995_K) {
            List<EnchantmentData> ☃ = this.func_178148_a(☃, ☃, this.field_75167_g[☃]);
            if (!☃.isEmpty()) {
               ☃.func_192024_a(☃, ☃xx);
               boolean ☃x = ☃.func_77973_b() == Items.field_151122_aG;
               if (☃x) {
                  ☃ = new ItemStack(Items.field_151134_bR);
                  this.field_75168_e.func_70299_a(0, ☃);
               }

               for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
                  EnchantmentData ☃xx = (EnchantmentData)☃.get(☃x);
                  if (☃x) {
                     ItemEnchantedBook.func_92115_a(☃, ☃xx);
                  } else {
                     ☃.func_77966_a(☃xx.field_76302_b, ☃xx.field_76303_c);
                  }
               }

               if (!☃.field_71075_bZ.field_75098_d) {
                  ☃x.func_190918_g(☃xx);
                  if (☃x.func_190926_b()) {
                     this.field_75168_e.func_70299_a(1, ItemStack.field_190927_a);
                  }
               }

               ☃.func_195066_a(StatList.field_188091_Y);
               if (☃ instanceof EntityPlayerMP) {
                  CriteriaTriggers.field_192129_i.func_192190_a((EntityPlayerMP)☃, ☃, ☃xx);
               }

               this.field_75168_e.func_70296_d();
               this.field_178149_f = ☃.func_175138_ci();
               this.func_75130_a(this.field_75168_e);
               this.field_75172_h
                  .func_184133_a(
                     null,
                     this.field_178150_j,
                     SoundEvents.field_190021_aL,
                     SoundCategory.BLOCKS,
                     1.0F,
                     this.field_75172_h.field_73012_v.nextFloat() * 0.1F + 0.9F
                  );
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private List<EnchantmentData> func_178148_a(ItemStack var1, int var2, int var3) {
      this.field_75169_l.setSeed((long)(this.field_178149_f + ☃));
      List<EnchantmentData> ☃ = EnchantmentHelper.func_77513_b(this.field_75169_l, ☃, ☃, false);
      if (☃.func_77973_b() == Items.field_151122_aG && ☃.size() > 1) {
         ☃.remove(this.field_75169_l.nextInt(☃.size()));
      }

      return ☃;
   }

   @Override
   public void func_75134_a(EntityPlayer var1) {
      super.func_75134_a(☃);
      if (!this.field_75172_h.field_72995_K) {
         this.func_193327_a(☃, ☃.field_70170_p, this.field_75168_e);
      }
   }

   @Override
   public boolean func_75145_c(EntityPlayer var1) {
      if (this.field_75172_h.func_180495_p(this.field_178150_j).func_177230_c() != Blocks.field_150381_bn) {
         return false;
      } else {
         return !(
            ☃.func_70092_e(
                  (double)this.field_178150_j.func_177958_n() + 0.5,
                  (double)this.field_178150_j.func_177956_o() + 0.5,
                  (double)this.field_178150_j.func_177952_p() + 0.5
               )
               > 64.0
         );
      }
   }

   @Override
   public ItemStack func_82846_b(EntityPlayer var1, int var2) {
      ItemStack ☃ = ItemStack.field_190927_a;
      Slot ☃x = (Slot)this.field_75151_b.get(☃);
      if (☃x != null && ☃x.func_75216_d()) {
         ItemStack ☃xx = ☃x.func_75211_c();
         ☃ = ☃xx.func_77946_l();
         if (☃ == 0) {
            if (!this.func_75135_a(☃xx, 2, 38, true)) {
               return ItemStack.field_190927_a;
            }
         } else if (☃ == 1) {
            if (!this.func_75135_a(☃xx, 2, 38, true)) {
               return ItemStack.field_190927_a;
            }
         } else if (☃xx.func_77973_b() == Items.field_196128_bn) {
            if (!this.func_75135_a(☃xx, 1, 2, true)) {
               return ItemStack.field_190927_a;
            }
         } else {
            if (((Slot)this.field_75151_b.get(0)).func_75216_d() || !((Slot)this.field_75151_b.get(0)).func_75214_a(☃xx)) {
               return ItemStack.field_190927_a;
            }

            if (☃xx.func_77942_o() && ☃xx.func_190916_E() == 1) {
               ((Slot)this.field_75151_b.get(0)).func_75215_d(☃xx.func_77946_l());
               ☃xx.func_190920_e(0);
            } else if (!☃xx.func_190926_b()) {
               ((Slot)this.field_75151_b.get(0)).func_75215_d(new ItemStack(☃xx.func_77973_b()));
               ☃xx.func_190918_g(1);
            }
         }

         if (☃xx.func_190926_b()) {
            ☃x.func_75215_d(ItemStack.field_190927_a);
         } else {
            ☃x.func_75218_e();
         }

         if (☃xx.func_190916_E() == ☃.func_190916_E()) {
            return ItemStack.field_190927_a;
         }

         ☃x.func_190901_a(☃, ☃xx);
      }

      return ☃;
   }
}
