package net.minecraft.inventory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public abstract class Container {
   public NonNullList<ItemStack> field_75153_a = NonNullList.func_191196_a();
   public List<Slot> field_75151_b = Lists.<Slot>newArrayList();
   public int field_75152_c;
   private short field_75150_e;
   private int field_94535_f = -1;
   private int field_94536_g;
   private final Set<Slot> field_94537_h = Sets.<Slot>newHashSet();
   protected List<IContainerListener> field_75149_d = Lists.<IContainerListener>newArrayList();
   private final Set<EntityPlayer> field_75148_f = Sets.<EntityPlayer>newHashSet();

   protected Slot func_75146_a(Slot var1) {
      ☃.field_75222_d = this.field_75151_b.size();
      this.field_75151_b.add(☃);
      this.field_75153_a.add(ItemStack.field_190927_a);
      return ☃;
   }

   public void func_75132_a(IContainerListener var1) {
      if (this.field_75149_d.contains(☃)) {
         throw new IllegalArgumentException("Listener already listening");
      } else {
         this.field_75149_d.add(☃);
         ☃.func_71110_a(this, this.func_75138_a());
         this.func_75142_b();
      }
   }

   public void func_82847_b(IContainerListener var1) {
      this.field_75149_d.remove(☃);
   }

   public NonNullList<ItemStack> func_75138_a() {
      NonNullList<ItemStack> ☃ = NonNullList.func_191196_a();

      for(int ☃x = 0; ☃x < this.field_75151_b.size(); ++☃x) {
         ☃.add(((Slot)this.field_75151_b.get(☃x)).func_75211_c());
      }

      return ☃;
   }

   public void func_75142_b() {
      for(int ☃ = 0; ☃ < this.field_75151_b.size(); ++☃) {
         ItemStack ☃x = ((Slot)this.field_75151_b.get(☃)).func_75211_c();
         ItemStack ☃xx = this.field_75153_a.get(☃);
         if (!ItemStack.func_77989_b(☃xx, ☃x)) {
            ☃xx = ☃x.func_190926_b() ? ItemStack.field_190927_a : ☃x.func_77946_l();
            this.field_75153_a.set(☃, ☃xx);

            for(int ☃xxx = 0; ☃xxx < this.field_75149_d.size(); ++☃xxx) {
               ((IContainerListener)this.field_75149_d.get(☃xxx)).func_71111_a(this, ☃, ☃xx);
            }
         }
      }
   }

   public boolean func_75140_a(EntityPlayer var1, int var2) {
      return false;
   }

   @Nullable
   public Slot func_75147_a(IInventory var1, int var2) {
      for(int ☃ = 0; ☃ < this.field_75151_b.size(); ++☃) {
         Slot ☃x = (Slot)this.field_75151_b.get(☃);
         if (☃x.func_75217_a(☃, ☃)) {
            return ☃x;
         }
      }

      return null;
   }

   public Slot func_75139_a(int var1) {
      return (Slot)this.field_75151_b.get(☃);
   }

   public ItemStack func_82846_b(EntityPlayer var1, int var2) {
      Slot ☃ = (Slot)this.field_75151_b.get(☃);
      return ☃ != null ? ☃.func_75211_c() : ItemStack.field_190927_a;
   }

   public ItemStack func_184996_a(int var1, int var2, ClickType var3, EntityPlayer var4) {
      ItemStack ☃ = ItemStack.field_190927_a;
      InventoryPlayer ☃x = ☃.field_71071_by;
      if (☃ == ClickType.QUICK_CRAFT) {
         int ☃xx = this.field_94536_g;
         this.field_94536_g = func_94532_c(☃);
         if ((☃xx != 1 || this.field_94536_g != 2) && ☃xx != this.field_94536_g) {
            this.func_94533_d();
         } else if (☃x.func_70445_o().func_190926_b()) {
            this.func_94533_d();
         } else if (this.field_94536_g == 0) {
            this.field_94535_f = func_94529_b(☃);
            if (func_180610_a(this.field_94535_f, ☃)) {
               this.field_94536_g = 1;
               this.field_94537_h.clear();
            } else {
               this.func_94533_d();
            }
         } else if (this.field_94536_g == 1) {
            Slot ☃xx = (Slot)this.field_75151_b.get(☃);
            ItemStack ☃xxx = ☃x.func_70445_o();
            if (☃xx != null
               && func_94527_a(☃xx, ☃xxx, true)
               && ☃xx.func_75214_a(☃xxx)
               && (this.field_94535_f == 2 || ☃xxx.func_190916_E() > this.field_94537_h.size())
               && this.func_94531_b(☃xx)) {
               this.field_94537_h.add(☃xx);
            }
         } else if (this.field_94536_g == 2) {
            if (!this.field_94537_h.isEmpty()) {
               ItemStack ☃xx = ☃x.func_70445_o().func_77946_l();
               int ☃xxx = ☃x.func_70445_o().func_190916_E();

               for(Slot ☃xxxx : this.field_94537_h) {
                  ItemStack ☃xxxxx = ☃x.func_70445_o();
                  if (☃xxxx != null
                     && func_94527_a(☃xxxx, ☃xxxxx, true)
                     && ☃xxxx.func_75214_a(☃xxxxx)
                     && (this.field_94535_f == 2 || ☃xxxxx.func_190916_E() >= this.field_94537_h.size())
                     && this.func_94531_b(☃xxxx)) {
                     ItemStack ☃xxxxxx = ☃xx.func_77946_l();
                     int ☃xxxxxxx = ☃xxxx.func_75216_d() ? ☃xxxx.func_75211_c().func_190916_E() : 0;
                     func_94525_a(this.field_94537_h, this.field_94535_f, ☃xxxxxx, ☃xxxxxxx);
                     int ☃xxxxxxxx = Math.min(☃xxxxxx.func_77976_d(), ☃xxxx.func_178170_b(☃xxxxxx));
                     if (☃xxxxxx.func_190916_E() > ☃xxxxxxxx) {
                        ☃xxxxxx.func_190920_e(☃xxxxxxxx);
                     }

                     ☃xxx -= ☃xxxxxx.func_190916_E() - ☃xxxxxxx;
                     ☃xxxx.func_75215_d(☃xxxxxx);
                  }
               }

               ☃xx.func_190920_e(☃xxx);
               ☃x.func_70437_b(☃xx);
            }

            this.func_94533_d();
         } else {
            this.func_94533_d();
         }
      } else if (this.field_94536_g != 0) {
         this.func_94533_d();
      } else if ((☃ == ClickType.PICKUP || ☃ == ClickType.QUICK_MOVE) && (☃ == 0 || ☃ == 1)) {
         if (☃ == -999) {
            if (!☃x.func_70445_o().func_190926_b()) {
               if (☃ == 0) {
                  ☃.func_71019_a(☃x.func_70445_o(), true);
                  ☃x.func_70437_b(ItemStack.field_190927_a);
               }

               if (☃ == 1) {
                  ☃.func_71019_a(☃x.func_70445_o().func_77979_a(1), true);
               }
            }
         } else if (☃ == ClickType.QUICK_MOVE) {
            if (☃ < 0) {
               return ItemStack.field_190927_a;
            }

            Slot ☃ = (Slot)this.field_75151_b.get(☃);
            if (☃ == null || !☃.func_82869_a(☃)) {
               return ItemStack.field_190927_a;
            }

            for(ItemStack ☃ = this.func_82846_b(☃, ☃); !☃.func_190926_b() && ItemStack.func_179545_c(☃.func_75211_c(), ☃); ☃ = this.func_82846_b(☃, ☃)) {
               ☃ = ☃.func_77946_l();
            }
         } else {
            if (☃ < 0) {
               return ItemStack.field_190927_a;
            }

            Slot ☃ = (Slot)this.field_75151_b.get(☃);
            if (☃ != null) {
               ItemStack ☃x = ☃.func_75211_c();
               ItemStack ☃xx = ☃x.func_70445_o();
               if (!☃x.func_190926_b()) {
                  ☃ = ☃x.func_77946_l();
               }

               if (☃x.func_190926_b()) {
                  if (!☃xx.func_190926_b() && ☃.func_75214_a(☃xx)) {
                     int ☃x = ☃ == 0 ? ☃xx.func_190916_E() : 1;
                     if (☃x > ☃.func_178170_b(☃xx)) {
                        ☃x = ☃.func_178170_b(☃xx);
                     }

                     ☃.func_75215_d(☃xx.func_77979_a(☃x));
                  }
               } else if (☃.func_82869_a(☃)) {
                  if (☃xx.func_190926_b()) {
                     if (☃x.func_190926_b()) {
                        ☃.func_75215_d(ItemStack.field_190927_a);
                        ☃x.func_70437_b(ItemStack.field_190927_a);
                     } else {
                        int ☃x = ☃ == 0 ? ☃x.func_190916_E() : (☃x.func_190916_E() + 1) / 2;
                        ☃x.func_70437_b(☃.func_75209_a(☃x));
                        if (☃x.func_190926_b()) {
                           ☃.func_75215_d(ItemStack.field_190927_a);
                        }

                        ☃.func_190901_a(☃, ☃x.func_70445_o());
                     }
                  } else if (☃.func_75214_a(☃xx)) {
                     if (func_195929_a(☃x, ☃xx)) {
                        int ☃x = ☃ == 0 ? ☃xx.func_190916_E() : 1;
                        if (☃x > ☃.func_178170_b(☃xx) - ☃x.func_190916_E()) {
                           ☃x = ☃.func_178170_b(☃xx) - ☃x.func_190916_E();
                        }

                        if (☃x > ☃xx.func_77976_d() - ☃x.func_190916_E()) {
                           ☃x = ☃xx.func_77976_d() - ☃x.func_190916_E();
                        }

                        ☃xx.func_190918_g(☃x);
                        ☃x.func_190917_f(☃x);
                     } else if (☃xx.func_190916_E() <= ☃.func_178170_b(☃xx)) {
                        ☃.func_75215_d(☃xx);
                        ☃x.func_70437_b(☃x);
                     }
                  } else if (☃xx.func_77976_d() > 1 && func_195929_a(☃x, ☃xx) && !☃x.func_190926_b()) {
                     int ☃x = ☃x.func_190916_E();
                     if (☃x + ☃xx.func_190916_E() <= ☃xx.func_77976_d()) {
                        ☃xx.func_190917_f(☃x);
                        ☃x = ☃.func_75209_a(☃x);
                        if (☃x.func_190926_b()) {
                           ☃.func_75215_d(ItemStack.field_190927_a);
                        }

                        ☃.func_190901_a(☃, ☃x.func_70445_o());
                     }
                  }
               }

               ☃.func_75218_e();
            }
         }
      } else if (☃ == ClickType.SWAP && ☃ >= 0 && ☃ < 9) {
         Slot ☃ = (Slot)this.field_75151_b.get(☃);
         ItemStack ☃x = ☃x.func_70301_a(☃);
         ItemStack ☃xx = ☃.func_75211_c();
         if (!☃x.func_190926_b() || !☃xx.func_190926_b()) {
            if (☃x.func_190926_b()) {
               if (☃.func_82869_a(☃)) {
                  ☃x.func_70299_a(☃, ☃xx);
                  ☃.func_190900_b(☃xx.func_190916_E());
                  ☃.func_75215_d(ItemStack.field_190927_a);
                  ☃.func_190901_a(☃, ☃xx);
               }
            } else if (☃xx.func_190926_b()) {
               if (☃.func_75214_a(☃x)) {
                  int ☃xxx = ☃.func_178170_b(☃x);
                  if (☃x.func_190916_E() > ☃xxx) {
                     ☃.func_75215_d(☃x.func_77979_a(☃xxx));
                  } else {
                     ☃.func_75215_d(☃x);
                     ☃x.func_70299_a(☃, ItemStack.field_190927_a);
                  }
               }
            } else if (☃.func_82869_a(☃) && ☃.func_75214_a(☃x)) {
               int ☃xxx = ☃.func_178170_b(☃x);
               if (☃x.func_190916_E() > ☃xxx) {
                  ☃.func_75215_d(☃x.func_77979_a(☃xxx));
                  ☃.func_190901_a(☃, ☃xx);
                  if (!☃x.func_70441_a(☃xx)) {
                     ☃.func_71019_a(☃xx, true);
                  }
               } else {
                  ☃.func_75215_d(☃x);
                  ☃x.func_70299_a(☃, ☃xx);
                  ☃.func_190901_a(☃, ☃xx);
               }
            }
         }
      } else if (☃ == ClickType.CLONE && ☃.field_71075_bZ.field_75098_d && ☃x.func_70445_o().func_190926_b() && ☃ >= 0) {
         Slot ☃ = (Slot)this.field_75151_b.get(☃);
         if (☃ != null && ☃.func_75216_d()) {
            ItemStack ☃x = ☃.func_75211_c().func_77946_l();
            ☃x.func_190920_e(☃x.func_77976_d());
            ☃x.func_70437_b(☃x);
         }
      } else if (☃ == ClickType.THROW && ☃x.func_70445_o().func_190926_b() && ☃ >= 0) {
         Slot ☃ = (Slot)this.field_75151_b.get(☃);
         if (☃ != null && ☃.func_75216_d() && ☃.func_82869_a(☃)) {
            ItemStack ☃x = ☃.func_75209_a(☃ == 0 ? 1 : ☃.func_75211_c().func_190916_E());
            ☃.func_190901_a(☃, ☃x);
            ☃.func_71019_a(☃x, true);
         }
      } else if (☃ == ClickType.PICKUP_ALL && ☃ >= 0) {
         Slot ☃ = (Slot)this.field_75151_b.get(☃);
         ItemStack ☃x = ☃x.func_70445_o();
         if (!☃x.func_190926_b() && (☃ == null || !☃.func_75216_d() || !☃.func_82869_a(☃))) {
            int ☃xx = ☃ == 0 ? 0 : this.field_75151_b.size() - 1;
            int ☃xxx = ☃ == 0 ? 1 : -1;

            for(int ☃xxxx = 0; ☃xxxx < 2; ++☃xxxx) {
               for(int ☃xxxxx = ☃xx; ☃xxxxx >= 0 && ☃xxxxx < this.field_75151_b.size() && ☃x.func_190916_E() < ☃x.func_77976_d(); ☃xxxxx += ☃xxx) {
                  Slot ☃xxxxxx = (Slot)this.field_75151_b.get(☃xxxxx);
                  if (☃xxxxxx.func_75216_d() && func_94527_a(☃xxxxxx, ☃x, true) && ☃xxxxxx.func_82869_a(☃) && this.func_94530_a(☃x, ☃xxxxxx)) {
                     ItemStack ☃xxxxxxx = ☃xxxxxx.func_75211_c();
                     if (☃xxxx != 0 || ☃xxxxxxx.func_190916_E() != ☃xxxxxxx.func_77976_d()) {
                        int ☃xxxxxxxx = Math.min(☃x.func_77976_d() - ☃x.func_190916_E(), ☃xxxxxxx.func_190916_E());
                        ItemStack ☃xxxxxxxxx = ☃xxxxxx.func_75209_a(☃xxxxxxxx);
                        ☃x.func_190917_f(☃xxxxxxxx);
                        if (☃xxxxxxxxx.func_190926_b()) {
                           ☃xxxxxx.func_75215_d(ItemStack.field_190927_a);
                        }

                        ☃xxxxxx.func_190901_a(☃, ☃xxxxxxxxx);
                     }
                  }
               }
            }
         }

         this.func_75142_b();
      }

      return ☃;
   }

   public static boolean func_195929_a(ItemStack var0, ItemStack var1) {
      return ☃.func_77973_b() == ☃.func_77973_b() && ItemStack.func_77970_a(☃, ☃);
   }

   public boolean func_94530_a(ItemStack var1, Slot var2) {
      return true;
   }

   public void func_75134_a(EntityPlayer var1) {
      InventoryPlayer ☃ = ☃.field_71071_by;
      if (!☃.func_70445_o().func_190926_b()) {
         ☃.func_71019_a(☃.func_70445_o(), false);
         ☃.func_70437_b(ItemStack.field_190927_a);
      }
   }

   protected void func_193327_a(EntityPlayer var1, World var2, IInventory var3) {
      if (!☃.func_70089_S() || ☃ instanceof EntityPlayerMP && ((EntityPlayerMP)☃).func_193105_t()) {
         for(int ☃ = 0; ☃ < ☃.func_70302_i_(); ++☃) {
            ☃.func_71019_a(☃.func_70304_b(☃), false);
         }
      } else {
         for(int ☃ = 0; ☃ < ☃.func_70302_i_(); ++☃) {
            ☃.field_71071_by.func_191975_a(☃, ☃.func_70304_b(☃));
         }
      }
   }

   public void func_75130_a(IInventory var1) {
      this.func_75142_b();
   }

   public void func_75141_a(int var1, ItemStack var2) {
      this.func_75139_a(☃).func_75215_d(☃);
   }

   public void func_190896_a(List<ItemStack> var1) {
      for(int ☃ = 0; ☃ < ☃.size(); ++☃) {
         this.func_75139_a(☃).func_75215_d((ItemStack)☃.get(☃));
      }
   }

   public void func_75137_b(int var1, int var2) {
   }

   public short func_75136_a(InventoryPlayer var1) {
      ++this.field_75150_e;
      return this.field_75150_e;
   }

   public boolean func_75129_b(EntityPlayer var1) {
      return !this.field_75148_f.contains(☃);
   }

   public void func_75128_a(EntityPlayer var1, boolean var2) {
      if (☃) {
         this.field_75148_f.remove(☃);
      } else {
         this.field_75148_f.add(☃);
      }
   }

   public abstract boolean func_75145_c(EntityPlayer var1);

   protected boolean func_75135_a(ItemStack var1, int var2, int var3, boolean var4) {
      boolean ☃ = false;
      int ☃x = ☃;
      if (☃) {
         ☃x = ☃ - 1;
      }

      if (☃.func_77985_e()) {
         while(!☃.func_190926_b() && (☃ ? ☃x >= ☃ : ☃x < ☃)) {
            Slot ☃ = (Slot)this.field_75151_b.get(☃x);
            ItemStack ☃x = ☃.func_75211_c();
            if (!☃x.func_190926_b() && func_195929_a(☃, ☃x)) {
               int ☃xx = ☃x.func_190916_E() + ☃.func_190916_E();
               if (☃xx <= ☃.func_77976_d()) {
                  ☃.func_190920_e(0);
                  ☃x.func_190920_e(☃xx);
                  ☃.func_75218_e();
                  ☃ = true;
               } else if (☃x.func_190916_E() < ☃.func_77976_d()) {
                  ☃.func_190918_g(☃.func_77976_d() - ☃x.func_190916_E());
                  ☃x.func_190920_e(☃.func_77976_d());
                  ☃.func_75218_e();
                  ☃ = true;
               }
            }

            if (☃) {
               --☃x;
            } else {
               ++☃x;
            }
         }
      }

      if (!☃.func_190926_b()) {
         if (☃) {
            ☃x = ☃ - 1;
         } else {
            ☃x = ☃;
         }

         while(☃ ? ☃x >= ☃ : ☃x < ☃) {
            Slot ☃ = (Slot)this.field_75151_b.get(☃x);
            ItemStack ☃x = ☃.func_75211_c();
            if (☃x.func_190926_b() && ☃.func_75214_a(☃)) {
               if (☃.func_190916_E() > ☃.func_75219_a()) {
                  ☃.func_75215_d(☃.func_77979_a(☃.func_75219_a()));
               } else {
                  ☃.func_75215_d(☃.func_77979_a(☃.func_190916_E()));
               }

               ☃.func_75218_e();
               ☃ = true;
               break;
            }

            if (☃) {
               --☃x;
            } else {
               ++☃x;
            }
         }
      }

      return ☃;
   }

   public static int func_94529_b(int var0) {
      return ☃ >> 2 & 3;
   }

   public static int func_94532_c(int var0) {
      return ☃ & 3;
   }

   public static int func_94534_d(int var0, int var1) {
      return ☃ & 3 | (☃ & 3) << 2;
   }

   public static boolean func_180610_a(int var0, EntityPlayer var1) {
      if (☃ == 0) {
         return true;
      } else if (☃ == 1) {
         return true;
      } else {
         return ☃ == 2 && ☃.field_71075_bZ.field_75098_d;
      }
   }

   protected void func_94533_d() {
      this.field_94536_g = 0;
      this.field_94537_h.clear();
   }

   public static boolean func_94527_a(@Nullable Slot var0, ItemStack var1, boolean var2) {
      boolean ☃ = ☃ == null || !☃.func_75216_d();
      if (!☃ && ☃.func_77969_a(☃.func_75211_c()) && ItemStack.func_77970_a(☃.func_75211_c(), ☃)) {
         return ☃.func_75211_c().func_190916_E() + (☃ ? 0 : ☃.func_190916_E()) <= ☃.func_77976_d();
      } else {
         return ☃;
      }
   }

   public static void func_94525_a(Set<Slot> var0, int var1, ItemStack var2, int var3) {
      switch(☃) {
         case 0:
            ☃.func_190920_e(MathHelper.func_76141_d((float)☃.func_190916_E() / (float)☃.size()));
            break;
         case 1:
            ☃.func_190920_e(1);
            break;
         case 2:
            ☃.func_190920_e(☃.func_77973_b().func_77639_j());
      }

      ☃.func_190917_f(☃);
   }

   public boolean func_94531_b(Slot var1) {
      return true;
   }

   public static int func_178144_a(@Nullable TileEntity var0) {
      return ☃ instanceof IInventory ? func_94526_b((IInventory)☃) : 0;
   }

   public static int func_94526_b(@Nullable IInventory var0) {
      if (☃ == null) {
         return 0;
      } else {
         int ☃ = 0;
         float ☃x = 0.0F;

         for(int ☃xx = 0; ☃xx < ☃.func_70302_i_(); ++☃xx) {
            ItemStack ☃xxx = ☃.func_70301_a(☃xx);
            if (!☃xxx.func_190926_b()) {
               ☃x += (float)☃xxx.func_190916_E() / (float)Math.min(☃.func_70297_j_(), ☃xxx.func_77976_d());
               ++☃;
            }
         }

         ☃x /= (float)☃.func_70302_i_();
         return MathHelper.func_76141_d(☃x * 14.0F) + (☃ > 0 ? 1 : 0);
      }
   }

   protected void func_192389_a(World var1, EntityPlayer var2, IInventory var3, InventoryCraftResult var4) {
      if (!☃.field_72995_K) {
         EntityPlayerMP ☃ = (EntityPlayerMP)☃;
         ItemStack ☃x = ItemStack.field_190927_a;
         IRecipe ☃xx = ☃.func_73046_m().func_199529_aN().func_199515_b(☃, ☃);
         if (☃.func_201561_a(☃, ☃, ☃xx) && ☃xx != null) {
            ☃x = ☃xx.func_77572_b(☃);
         }

         ☃.func_70299_a(0, ☃x);
         ☃.field_71135_a.func_147359_a(new SPacketSetSlot(this.field_75152_c, 0, ☃x));
      }
   }
}
