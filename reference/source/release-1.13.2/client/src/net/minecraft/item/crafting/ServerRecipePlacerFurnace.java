package net.minecraft.item.crafting;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Iterator;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ServerRecipePlacerFurnace extends ServerRecipePlacer {
   private boolean field_201517_e;

   @Override
   protected void func_201508_a(IRecipe var1, boolean var2) {
      this.field_201517_e = this.field_201515_d.func_201769_a(☃);
      int ☃ = this.field_194331_b.func_194114_b(☃, null);
      if (this.field_201517_e) {
         ItemStack ☃x = this.field_201515_d.func_75139_a(0).func_75211_c();
         if (☃x.func_190926_b() || ☃ <= ☃x.func_190916_E()) {
            return;
         }
      }

      int ☃ = this.func_201509_a(☃, ☃, this.field_201517_e);
      IntList ☃x = new IntArrayList();
      if (this.field_194331_b.func_194118_a(☃, ☃x, ☃)) {
         if (!this.field_201517_e) {
            this.func_201510_a(this.field_201515_d.func_201767_f());
            this.func_201510_a(0);
         }

         this.func_201516_a(☃, ☃x);
      }
   }

   @Override
   protected void func_201511_a() {
      this.func_201510_a(this.field_201515_d.func_201767_f());
      super.func_201511_a();
   }

   protected void func_201516_a(int var1, IntList var2) {
      Iterator<Integer> ☃ = ☃.iterator();
      Slot ☃x = this.field_201515_d.func_75139_a(0);
      ItemStack ☃xx = RecipeItemHelper.func_194115_b(☃.next());
      if (!☃xx.func_190926_b()) {
         int ☃xxx = Math.min(☃xx.func_77976_d(), ☃);
         if (this.field_201517_e) {
            ☃xxx -= ☃x.func_75211_c().func_190916_E();
         }

         for(int ☃xxx = 0; ☃xxx < ☃xxx; ++☃xxx) {
            this.func_194325_a(☃x, ☃xx);
         }
      }
   }
}
