package net.minecraft.client.gui.screens.advancements;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;

enum AdvancementTabType {
   ABOVE(0, 0, 28, 32, 8),
   BELOW(84, 0, 28, 32, 8),
   LEFT(0, 64, 32, 28, 5),
   RIGHT(96, 64, 32, 28, 5);

   private final int textureX;
   private final int textureY;
   private final int width;
   private final int height;
   private final int max;

   private AdvancementTabType(int var3, int var4, int var5, int var6, int var7) {
      this.textureX = â˜ƒ;
      this.textureY = â˜ƒ;
      this.width = â˜ƒ;
      this.height = â˜ƒ;
      this.max = â˜ƒ;
   }

   public int getMax() {
      return this.max;
   }

   public void draw(PoseStack var1, GuiComponent var2, int var3, int var4, boolean var5, int var6) {
      int â˜ƒ = this.textureX;
      if (â˜ƒ > 0) {
         â˜ƒ += this.width;
      }

      if (â˜ƒ == this.max - 1) {
         â˜ƒ += this.width;
      }

      int â˜ƒ = â˜ƒ ? this.textureY + this.height : this.textureY;
      â˜ƒ.blit(â˜ƒ, â˜ƒ + this.getX(â˜ƒ), â˜ƒ + this.getY(â˜ƒ), â˜ƒ, â˜ƒ, this.width, this.height);
   }

   public void drawIcon(int var1, int var2, int var3, ItemRenderer var4, ItemStack var5) {
      int â˜ƒ = â˜ƒ + this.getX(â˜ƒ);
      int â˜ƒx = â˜ƒ + this.getY(â˜ƒ);
      switch(this) {
         case ABOVE:
            â˜ƒ += 6;
            â˜ƒx += 9;
            break;
         case BELOW:
            â˜ƒ += 6;
            â˜ƒx += 6;
            break;
         case LEFT:
            â˜ƒ += 10;
            â˜ƒx += 5;
            break;
         case RIGHT:
            â˜ƒ += 6;
            â˜ƒx += 5;
      }

      â˜ƒ.renderAndDecorateFakeItem(â˜ƒ, â˜ƒ, â˜ƒx);
   }

   public int getX(int var1) {
      switch(this) {
         case ABOVE:
            return (this.width + 4) * â˜ƒ;
         case BELOW:
            return (this.width + 4) * â˜ƒ;
         case LEFT:
            return -this.width + 4;
         case RIGHT:
            return 248;
         default:
            throw new UnsupportedOperationException("Don't know what this tab type is!" + this);
      }
   }

   public int getY(int var1) {
      switch(this) {
         case ABOVE:
            return -this.height + 4;
         case BELOW:
            return 136;
         case LEFT:
            return this.height * â˜ƒ;
         case RIGHT:
            return this.height * â˜ƒ;
         default:
            throw new UnsupportedOperationException("Don't know what this tab type is!" + this);
      }
   }

   public boolean isMouseOver(int var1, int var2, int var3, double var4, double var6) {
      int â˜ƒ = â˜ƒ + this.getX(â˜ƒ);
      int â˜ƒx = â˜ƒ + this.getY(â˜ƒ);
      return â˜ƒ > (double)â˜ƒ && â˜ƒ < (double)(â˜ƒ + this.width) && â˜ƒ > (double)â˜ƒx && â˜ƒ < (double)(â˜ƒx + this.height);
   }
}
