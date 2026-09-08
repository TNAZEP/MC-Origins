package net.minecraft.client.gui.advancements;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;

enum AdvancementTabType {
   ABOVE(0, 0, 28, 32, 8),
   BELOW(84, 0, 28, 32, 8),
   LEFT(0, 64, 32, 28, 5),
   RIGHT(96, 64, 32, 28, 5);

   private final int field_192660_f;
   private final int field_192661_g;
   private final int field_192662_h;
   private final int field_192663_i;
   private final int field_192664_j;

   private AdvancementTabType(int var3, int var4, int var5, int var6, int var7) {
      this.field_192660_f = ☃;
      this.field_192661_g = ☃;
      this.field_192662_h = ☃;
      this.field_192663_i = ☃;
      this.field_192664_j = ☃;
   }

   public int func_192650_a() {
      return this.field_192664_j;
   }

   public void func_192651_a(Gui var1, int var2, int var3, boolean var4, int var5) {
      int ☃ = this.field_192660_f;
      if (☃ > 0) {
         ☃ += this.field_192662_h;
      }

      if (☃ == this.field_192664_j - 1) {
         ☃ += this.field_192662_h;
      }

      int ☃ = ☃ ? this.field_192661_g + this.field_192663_i : this.field_192661_g;
      ☃.func_73729_b(☃ + this.func_192648_a(☃), ☃ + this.func_192653_b(☃), ☃, ☃, this.field_192662_h, this.field_192663_i);
   }

   public void func_192652_a(int var1, int var2, int var3, ItemRenderer var4, ItemStack var5) {
      int ☃ = ☃ + this.func_192648_a(☃);
      int ☃x = ☃ + this.func_192653_b(☃);
      switch(this) {
         case ABOVE:
            ☃ += 6;
            ☃x += 9;
            break;
         case BELOW:
            ☃ += 6;
            ☃x += 6;
            break;
         case LEFT:
            ☃ += 10;
            ☃x += 5;
            break;
         case RIGHT:
            ☃ += 6;
            ☃x += 5;
      }

      ☃.func_184391_a(null, ☃, ☃, ☃x);
   }

   public int func_192648_a(int var1) {
      switch(this) {
         case ABOVE:
            return (this.field_192662_h + 4) * ☃;
         case BELOW:
            return (this.field_192662_h + 4) * ☃;
         case LEFT:
            return -this.field_192662_h + 4;
         case RIGHT:
            return 248;
         default:
            throw new UnsupportedOperationException("Don't know what this tab type is!" + this);
      }
   }

   public int func_192653_b(int var1) {
      switch(this) {
         case ABOVE:
            return -this.field_192663_i + 4;
         case BELOW:
            return 136;
         case LEFT:
            return this.field_192663_i * ☃;
         case RIGHT:
            return this.field_192663_i * ☃;
         default:
            throw new UnsupportedOperationException("Don't know what this tab type is!" + this);
      }
   }

   public boolean func_198891_a(int var1, int var2, int var3, double var4, double var6) {
      int ☃ = ☃ + this.func_192648_a(☃);
      int ☃x = ☃ + this.func_192653_b(☃);
      return ☃ > (double)☃ && ☃ < (double)(☃ + this.field_192662_h) && ☃ > (double)☃x && ☃ < (double)(☃x + this.field_192663_i);
   }
}
