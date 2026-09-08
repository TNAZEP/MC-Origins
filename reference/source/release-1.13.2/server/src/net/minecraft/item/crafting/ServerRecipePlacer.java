package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.ContainerRecipeBook;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketPlaceGhostRecipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerRecipePlacer implements IRecipePlacer<Integer> {
   protected static final Logger field_194330_a = LogManager.getLogger();
   protected final RecipeItemHelper field_194331_b = new RecipeItemHelper();
   protected InventoryPlayer field_201514_c;
   protected ContainerRecipeBook field_201515_d;

   public void func_194327_a(EntityPlayerMP var1, @Nullable IRecipe var2, boolean var3) {
      if (☃ != null && ☃.func_192037_E().func_193830_f(☃)) {
         this.field_201514_c = ☃.field_71071_by;
         this.field_201515_d = (ContainerRecipeBook)☃.field_71070_bA;
         if (this.func_194328_c() || ☃.func_184812_l_()) {
            this.field_194331_b.func_194119_a();
            ☃.field_71071_by.func_201571_a(this.field_194331_b);
            this.field_201515_d.func_201771_a(this.field_194331_b);
            if (this.field_194331_b.func_194116_a(☃, null)) {
               this.func_201508_a(☃, ☃);
            } else {
               this.func_201511_a();
               ☃.field_71135_a.func_147359_a(new SPacketPlaceGhostRecipe(☃.field_71070_bA.field_75152_c, ☃));
            }

            ☃.field_71071_by.func_70296_d();
         }
      }
   }

   protected void func_201511_a() {
      for(int ☃ = 0; ☃ < this.field_201515_d.func_201770_g() * this.field_201515_d.func_201772_h() + 1; ++☃) {
         if (☃ != this.field_201515_d.func_201767_f()
            || !(this.field_201515_d instanceof ContainerWorkbench) && !(this.field_201515_d instanceof ContainerPlayer)) {
            this.func_201510_a(☃);
         }
      }

      this.field_201515_d.func_201768_e();
   }

   protected void func_201510_a(int var1) {
      ItemStack ☃ = this.field_201515_d.func_75139_a(☃).func_75211_c();
      if (!☃.func_190926_b()) {
         for(; ☃.func_190916_E() > 0; this.field_201515_d.func_75139_a(☃).func_75209_a(1)) {
            int ☃x = this.field_201514_c.func_70432_d(☃);
            if (☃x == -1) {
               ☃x = this.field_201514_c.func_70447_i();
            }

            ItemStack ☃x = ☃.func_77946_l();
            ☃x.func_190920_e(1);
            if (!this.field_201514_c.func_191971_c(☃x, ☃x)) {
               field_194330_a.error("Can't find any space for item in the inventory");
            }
         }
      }
   }

   protected void func_201508_a(IRecipe var1, boolean var2) {
      boolean ☃ = this.field_201515_d.func_201769_a(☃);
      int ☃x = this.field_194331_b.func_194114_b(☃, null);
      if (☃) {
         for(int ☃xx = 0; ☃xx < this.field_201515_d.func_201772_h() * this.field_201515_d.func_201770_g() + 1; ++☃xx) {
            if (☃xx != this.field_201515_d.func_201767_f()) {
               ItemStack ☃xxx = this.field_201515_d.func_75139_a(☃xx).func_75211_c();
               if (!☃xxx.func_190926_b() && Math.min(☃x, ☃xxx.func_77976_d()) < ☃xxx.func_190916_E() + 1) {
                  return;
               }
            }
         }
      }

      int ☃ = this.func_201509_a(☃, ☃x, ☃);
      IntList ☃x = new IntArrayList();
      if (this.field_194331_b.func_194118_a(☃, ☃x, ☃)) {
         int ☃xx = ☃;

         for(int ☃xxx : ☃x) {
            int ☃xxxx = RecipeItemHelper.func_194115_b(☃xxx).func_77976_d();
            if (☃xxxx < ☃xx) {
               ☃xx = ☃xxxx;
            }
         }

         if (this.field_194331_b.func_194118_a(☃, ☃x, ☃xx)) {
            this.func_201511_a();
            this.func_201501_a(
               this.field_201515_d.func_201770_g(), this.field_201515_d.func_201772_h(), this.field_201515_d.func_201767_f(), ☃, ☃x.iterator(), ☃xx
            );
         }
      }
   }

   @Override
   public void func_201500_a(Iterator<Integer> var1, int var2, int var3, int var4, int var5) {
      Slot ☃ = this.field_201515_d.func_75139_a(☃);
      ItemStack ☃x = RecipeItemHelper.func_194115_b(☃.next());
      if (!☃x.func_190926_b()) {
         for(int ☃xx = 0; ☃xx < ☃; ++☃xx) {
            this.func_194325_a(☃, ☃x);
         }
      }
   }

   protected int func_201509_a(boolean var1, int var2, boolean var3) {
      int ☃ = 1;
      if (☃) {
         ☃ = ☃;
      } else if (☃) {
         ☃ = 64;

         for(int ☃ = 0; ☃ < this.field_201515_d.func_201770_g() * this.field_201515_d.func_201772_h() + 1; ++☃) {
            if (☃ != this.field_201515_d.func_201767_f()) {
               ItemStack ☃x = this.field_201515_d.func_75139_a(☃).func_75211_c();
               if (!☃x.func_190926_b() && ☃ > ☃x.func_190916_E()) {
                  ☃ = ☃x.func_190916_E();
               }
            }
         }

         if (☃ < 64) {
            ++☃;
         }
      }

      return ☃;
   }

   protected void func_194325_a(Slot var1, ItemStack var2) {
      int ☃ = this.field_201514_c.func_194014_c(☃);
      if (☃ != -1) {
         ItemStack ☃x = this.field_201514_c.func_70301_a(☃).func_77946_l();
         if (!☃x.func_190926_b()) {
            if (☃x.func_190916_E() > 1) {
               this.field_201514_c.func_70298_a(☃, 1);
            } else {
               this.field_201514_c.func_70304_b(☃);
            }

            ☃x.func_190920_e(1);
            if (☃.func_75211_c().func_190926_b()) {
               ☃.func_75215_d(☃x);
            } else {
               ☃.func_75211_c().func_190917_f(1);
            }
         }
      }
   }

   private boolean func_194328_c() {
      List<ItemStack> ☃ = Lists.<ItemStack>newArrayList();
      int ☃x = this.func_203600_c();

      for(int ☃xx = 0; ☃xx < this.field_201515_d.func_201770_g() * this.field_201515_d.func_201772_h() + 1; ++☃xx) {
         if (☃xx != this.field_201515_d.func_201767_f()) {
            ItemStack ☃xxx = this.field_201515_d.func_75139_a(☃xx).func_75211_c().func_77946_l();
            if (!☃xxx.func_190926_b()) {
               int ☃xxxx = this.field_201514_c.func_70432_d(☃xxx);
               if (☃xxxx == -1 && ☃.size() <= ☃x) {
                  for(ItemStack ☃xxxxx : ☃) {
                     if (☃xxxxx.func_77969_a(☃xxx)
                        && ☃xxxxx.func_190916_E() != ☃xxxxx.func_77976_d()
                        && ☃xxxxx.func_190916_E() + ☃xxx.func_190916_E() <= ☃xxxxx.func_77976_d()) {
                        ☃xxxxx.func_190917_f(☃xxx.func_190916_E());
                        ☃xxx.func_190920_e(0);
                        break;
                     }
                  }

                  if (!☃xxx.func_190926_b()) {
                     if (☃.size() >= ☃x) {
                        return false;
                     }

                     ☃.add(☃xxx);
                  }
               } else if (☃xxxx == -1) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   private int func_203600_c() {
      int ☃ = 0;

      for(ItemStack ☃x : this.field_201514_c.field_70462_a) {
         if (☃x.func_190926_b()) {
            ++☃;
         }
      }

      return ☃;
   }
}
