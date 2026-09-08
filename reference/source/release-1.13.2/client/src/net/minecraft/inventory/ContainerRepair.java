package net.minecraft.inventory;

import java.util.Map;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ContainerRepair extends Container {
   private static final Logger field_148326_f = LogManager.getLogger();
   private final IInventory field_82852_f = new InventoryCraftResult();
   private final IInventory field_82853_g = new InventoryBasic(new TextComponentString("Repair"), 2) {
      @Override
      public void func_70296_d() {
         super.func_70296_d();
         ContainerRepair.this.func_75130_a(this);
      }
   };
   private final World field_82860_h;
   private final BlockPos field_178156_j;
   public int field_82854_e;
   private int field_82856_l;
   private String field_82857_m;
   private final EntityPlayer field_82855_n;

   public ContainerRepair(InventoryPlayer var1, World var2, EntityPlayer var3) {
      this(☃, ☃, BlockPos.field_177992_a, ☃);
   }

   public ContainerRepair(InventoryPlayer var1, final World var2, final BlockPos var3, EntityPlayer var4) {
      this.field_178156_j = ☃;
      this.field_82860_h = ☃;
      this.field_82855_n = ☃;
      this.func_75146_a(new Slot(this.field_82853_g, 0, 27, 47));
      this.func_75146_a(new Slot(this.field_82853_g, 1, 76, 47));
      this.func_75146_a(
         new Slot(this.field_82852_f, 2, 134, 47) {
            @Override
            public boolean func_75214_a(ItemStack var1) {
               return false;
            }
   
            @Override
            public boolean func_82869_a(EntityPlayer var1) {
               return (☃.field_71075_bZ.field_75098_d || ☃.field_71068_ca >= ContainerRepair.this.field_82854_e)
                  && ContainerRepair.this.field_82854_e > 0
                  && this.func_75216_d();
            }
   
            @Override
            public ItemStack func_190901_a(EntityPlayer var1, ItemStack var2x) {
               if (!☃.field_71075_bZ.field_75098_d) {
                  ☃.func_82242_a(-ContainerRepair.this.field_82854_e);
               }
   
               ContainerRepair.this.field_82853_g.func_70299_a(0, ItemStack.field_190927_a);
               if (ContainerRepair.this.field_82856_l > 0) {
                  ItemStack ☃ = ContainerRepair.this.field_82853_g.func_70301_a(1);
                  if (!☃.func_190926_b() && ☃.func_190916_E() > ContainerRepair.this.field_82856_l) {
                     ☃.func_190918_g(ContainerRepair.this.field_82856_l);
                     ContainerRepair.this.field_82853_g.func_70299_a(1, ☃);
                  } else {
                     ContainerRepair.this.field_82853_g.func_70299_a(1, ItemStack.field_190927_a);
                  }
               } else {
                  ContainerRepair.this.field_82853_g.func_70299_a(1, ItemStack.field_190927_a);
               }
   
               ContainerRepair.this.field_82854_e = 0;
               IBlockState ☃ = ☃.func_180495_p(☃);
               if (!☃.field_72995_K) {
                  if (!☃.field_71075_bZ.field_75098_d && ☃.func_203425_a(BlockTags.field_200572_k) && ☃.func_70681_au().nextFloat() < 0.12F) {
                     IBlockState ☃x = BlockAnvil.func_196433_f(☃);
                     if (☃x == null) {
                        ☃.func_175698_g(☃);
                        ☃.func_175718_b(1029, ☃, 0);
                     } else {
                        ☃.func_180501_a(☃, ☃x, 2);
                        ☃.func_175718_b(1030, ☃, 0);
                     }
                  } else {
                     ☃.func_175718_b(1030, ☃, 0);
                  }
               }
   
               return ☃;
            }
         }
      );

      for(int ☃ = 0; ☃ < 3; ++☃) {
         for(int ☃x = 0; ☃x < 9; ++☃x) {
            this.func_75146_a(new Slot(☃, ☃x + ☃ * 9 + 9, 8 + ☃x * 18, 84 + ☃ * 18));
         }
      }

      for(int ☃ = 0; ☃ < 9; ++☃) {
         this.func_75146_a(new Slot(☃, ☃, 8 + ☃ * 18, 142));
      }
   }

   @Override
   public void func_75130_a(IInventory var1) {
      super.func_75130_a(☃);
      if (☃ == this.field_82853_g) {
         this.func_82848_d();
      }
   }

   public void func_82848_d() {
      ItemStack ☃ = this.field_82853_g.func_70301_a(0);
      this.field_82854_e = 1;
      int ☃x = 0;
      int ☃xx = 0;
      int ☃xxx = 0;
      if (☃.func_190926_b()) {
         this.field_82852_f.func_70299_a(0, ItemStack.field_190927_a);
         this.field_82854_e = 0;
      } else {
         ItemStack ☃ = ☃.func_77946_l();
         ItemStack ☃x = this.field_82853_g.func_70301_a(1);
         Map<Enchantment, Integer> ☃xx = EnchantmentHelper.func_82781_a(☃);
         ☃xx += ☃.func_82838_A() + (☃x.func_190926_b() ? 0 : ☃x.func_82838_A());
         this.field_82856_l = 0;
         if (!☃x.func_190926_b()) {
            boolean ☃xxx = ☃x.func_77973_b() == Items.field_151134_bR && !ItemEnchantedBook.func_92110_g(☃x).isEmpty();
            if (☃.func_77984_f() && ☃.func_77973_b().func_82789_a(☃, ☃x)) {
               int ☃xxxx = Math.min(☃.func_77952_i(), ☃.func_77958_k() / 4);
               if (☃xxxx <= 0) {
                  this.field_82852_f.func_70299_a(0, ItemStack.field_190927_a);
                  this.field_82854_e = 0;
                  return;
               }

               int ☃;
               for(☃ = 0; ☃xxxx > 0 && ☃ < ☃x.func_190916_E(); ++☃) {
                  int ☃xxxx = ☃.func_77952_i() - ☃xxxx;
                  ☃.func_196085_b(☃xxxx);
                  ++☃x;
                  ☃xxxx = Math.min(☃.func_77952_i(), ☃.func_77958_k() / 4);
               }

               this.field_82856_l = ☃;
            } else {
               if (!☃xxx && (☃.func_77973_b() != ☃x.func_77973_b() || !☃.func_77984_f())) {
                  this.field_82852_f.func_70299_a(0, ItemStack.field_190927_a);
                  this.field_82854_e = 0;
                  return;
               }

               if (☃.func_77984_f() && !☃xxx) {
                  int ☃xxx = ☃.func_77958_k() - ☃.func_77952_i();
                  int ☃xxxx = ☃x.func_77958_k() - ☃x.func_77952_i();
                  int ☃xxxxx = ☃xxxx + ☃.func_77958_k() * 12 / 100;
                  int ☃xxxxxx = ☃xxx + ☃xxxxx;
                  int ☃xxxxxxx = ☃.func_77958_k() - ☃xxxxxx;
                  if (☃xxxxxxx < 0) {
                     ☃xxxxxxx = 0;
                  }

                  if (☃xxxxxxx < ☃.func_77952_i()) {
                     ☃.func_196085_b(☃xxxxxxx);
                     ☃x += 2;
                  }
               }

               Map<Enchantment, Integer> ☃xxx = EnchantmentHelper.func_82781_a(☃x);
               boolean ☃xxxx = false;
               boolean ☃xxxxx = false;

               for(Enchantment ☃xxxxxx : ☃xxx.keySet()) {
                  if (☃xxxxxx != null) {
                     int ☃xxxxxxx = ☃xx.containsKey(☃xxxxxx) ? ☃xx.get(☃xxxxxx) : 0;
                     int ☃xxxxxxxx = ☃xxx.get(☃xxxxxx);
                     ☃xxxxxxxx = ☃xxxxxxx == ☃xxxxxxxx ? ☃xxxxxxxx + 1 : Math.max(☃xxxxxxxx, ☃xxxxxxx);
                     boolean ☃xxxxxxxxx = ☃xxxxxx.func_92089_a(☃);
                     if (this.field_82855_n.field_71075_bZ.field_75098_d || ☃.func_77973_b() == Items.field_151134_bR) {
                        ☃xxxxxxxxx = true;
                     }

                     for(Enchantment ☃xxxxxxx : ☃xx.keySet()) {
                        if (☃xxxxxxx != ☃xxxxxx && !☃xxxxxx.func_191560_c(☃xxxxxxx)) {
                           ☃xxxxxxxxx = false;
                           ++☃x;
                        }
                     }

                     if (!☃xxxxxxxxx) {
                        ☃xxxxx = true;
                     } else {
                        ☃xxxx = true;
                        if (☃xxxxxxxx > ☃xxxxxx.func_77325_b()) {
                           ☃xxxxxxxx = ☃xxxxxx.func_77325_b();
                        }

                        ☃xx.put(☃xxxxxx, ☃xxxxxxxx);
                        int ☃xxxxxxx = 0;
                        switch(☃xxxxxx.func_77324_c()) {
                           case COMMON:
                              ☃xxxxxxx = 1;
                              break;
                           case UNCOMMON:
                              ☃xxxxxxx = 2;
                              break;
                           case RARE:
                              ☃xxxxxxx = 4;
                              break;
                           case VERY_RARE:
                              ☃xxxxxxx = 8;
                        }

                        if (☃xxx) {
                           ☃xxxxxxx = Math.max(1, ☃xxxxxxx / 2);
                        }

                        ☃x += ☃xxxxxxx * ☃xxxxxxxx;
                        if (☃.func_190916_E() > 1) {
                           ☃x = 40;
                        }
                     }
                  }
               }

               if (☃xxxxx && !☃xxxx) {
                  this.field_82852_f.func_70299_a(0, ItemStack.field_190927_a);
                  this.field_82854_e = 0;
                  return;
               }
            }
         }

         if (StringUtils.isBlank(this.field_82857_m)) {
            if (☃.func_82837_s()) {
               ☃xxx = 1;
               ☃x += ☃xxx;
               ☃.func_135074_t();
            }
         } else if (!this.field_82857_m.equals(☃.func_200301_q().getString())) {
            ☃xxx = 1;
            ☃x += ☃xxx;
            ☃.func_200302_a(new TextComponentString(this.field_82857_m));
         }

         this.field_82854_e = ☃xx + ☃x;
         if (☃x <= 0) {
            ☃ = ItemStack.field_190927_a;
         }

         if (☃xxx == ☃x && ☃xxx > 0 && this.field_82854_e >= 40) {
            this.field_82854_e = 39;
         }

         if (this.field_82854_e >= 40 && !this.field_82855_n.field_71075_bZ.field_75098_d) {
            ☃ = ItemStack.field_190927_a;
         }

         if (!☃.func_190926_b()) {
            int ☃ = ☃.func_82838_A();
            if (!☃x.func_190926_b() && ☃ < ☃x.func_82838_A()) {
               ☃ = ☃x.func_82838_A();
            }

            if (☃xxx != ☃x || ☃xxx == 0) {
               ☃ = ☃ * 2 + 1;
            }

            ☃.func_82841_c(☃);
            EnchantmentHelper.func_82782_a(☃xx, ☃);
         }

         this.field_82852_f.func_70299_a(0, ☃);
         this.func_75142_b();
      }
   }

   @Override
   public void func_75132_a(IContainerListener var1) {
      super.func_75132_a(☃);
      ☃.func_71112_a(this, 0, this.field_82854_e);
   }

   @Override
   public void func_75137_b(int var1, int var2) {
      if (☃ == 0) {
         this.field_82854_e = ☃;
      }
   }

   @Override
   public void func_75134_a(EntityPlayer var1) {
      super.func_75134_a(☃);
      if (!this.field_82860_h.field_72995_K) {
         this.func_193327_a(☃, this.field_82860_h, this.field_82853_g);
      }
   }

   @Override
   public boolean func_75145_c(EntityPlayer var1) {
      if (!this.field_82860_h.func_180495_p(this.field_178156_j).func_203425_a(BlockTags.field_200572_k)) {
         return false;
      } else {
         return ☃.func_70092_e(
               (double)this.field_178156_j.func_177958_n() + 0.5,
               (double)this.field_178156_j.func_177956_o() + 0.5,
               (double)this.field_178156_j.func_177952_p() + 0.5
            )
            <= 64.0;
      }
   }

   @Override
   public ItemStack func_82846_b(EntityPlayer var1, int var2) {
      ItemStack ☃ = ItemStack.field_190927_a;
      Slot ☃x = (Slot)this.field_75151_b.get(☃);
      if (☃x != null && ☃x.func_75216_d()) {
         ItemStack ☃xx = ☃x.func_75211_c();
         ☃ = ☃xx.func_77946_l();
         if (☃ == 2) {
            if (!this.func_75135_a(☃xx, 3, 39, true)) {
               return ItemStack.field_190927_a;
            }

            ☃x.func_75220_a(☃xx, ☃);
         } else if (☃ != 0 && ☃ != 1) {
            if (☃ >= 3 && ☃ < 39 && !this.func_75135_a(☃xx, 0, 2, false)) {
               return ItemStack.field_190927_a;
            }
         } else if (!this.func_75135_a(☃xx, 3, 39, false)) {
            return ItemStack.field_190927_a;
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

   public void func_82850_a(String var1) {
      this.field_82857_m = ☃;
      if (this.func_75139_a(2).func_75216_d()) {
         ItemStack ☃ = this.func_75139_a(2).func_75211_c();
         if (StringUtils.isBlank(☃)) {
            ☃.func_135074_t();
         } else {
            ☃.func_200302_a(new TextComponentString(this.field_82857_m));
         }
      }

      this.func_82848_d();
   }
}
