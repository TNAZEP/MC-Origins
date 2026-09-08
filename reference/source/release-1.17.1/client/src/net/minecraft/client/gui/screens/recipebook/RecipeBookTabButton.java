package net.minecraft.client.gui.screens.recipebook;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public class RecipeBookTabButton extends StateSwitchingButton {
   private final RecipeBookCategories category;
   private static final float ANIMATION_TIME = 15.0F;
   private float animationTime;

   public RecipeBookTabButton(RecipeBookCategories var1) {
      super(0, 0, 35, 27, false);
      this.category = â˜ƒ;
      this.initTextureValues(153, 2, 35, 0, RecipeBookComponent.RECIPE_BOOK_LOCATION);
   }

   public void startAnimation(Minecraft var1) {
      ClientRecipeBook â˜ƒ = â˜ƒ.player.getRecipeBook();
      List<RecipeCollection> â˜ƒx = â˜ƒ.getCollection(this.category);
      if (â˜ƒ.player.containerMenu instanceof RecipeBookMenu) {
         for(RecipeCollection â˜ƒxx : â˜ƒx) {
            for(Recipe<?> â˜ƒxxx : â˜ƒxx.getRecipes(â˜ƒ.isFiltering((RecipeBookMenu<?>)â˜ƒ.player.containerMenu))) {
               if (â˜ƒ.willHighlight(â˜ƒxxx)) {
                  this.animationTime = 15.0F;
                  return;
               }
            }
         }
      }
   }

   @Override
   public void renderButton(PoseStack var1, int var2, int var3, float var4) {
      if (this.animationTime > 0.0F) {
         float â˜ƒ = 1.0F + 0.1F * (float)Math.sin((double)(this.animationTime / 15.0F * (float) Math.PI));
         â˜ƒ.pushPose();
         â˜ƒ.translate((double)(this.x + 8), (double)(this.y + 12), 0.0);
         â˜ƒ.scale(1.0F, â˜ƒ, 1.0F);
         â˜ƒ.translate((double)(-(this.x + 8)), (double)(-(this.y + 12)), 0.0);
      }

      Minecraft â˜ƒ = Minecraft.getInstance();
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderTexture(0, this.resourceLocation);
      RenderSystem.disableDepthTest();
      int â˜ƒx = this.xTexStart;
      int â˜ƒxx = this.yTexStart;
      if (this.isStateTriggered) {
         â˜ƒx += this.xDiffTex;
      }

      if (this.isHovered()) {
         â˜ƒxx += this.yDiffTex;
      }

      int â˜ƒ = this.x;
      if (this.isStateTriggered) {
         â˜ƒ -= 2;
      }

      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      this.blit(â˜ƒ, â˜ƒ, this.y, â˜ƒx, â˜ƒxx, this.width, this.height);
      RenderSystem.enableDepthTest();
      this.renderIcon(â˜ƒ.getItemRenderer());
      if (this.animationTime > 0.0F) {
         â˜ƒ.popPose();
         this.animationTime -= â˜ƒ;
      }
   }

   private void renderIcon(ItemRenderer var1) {
      List<ItemStack> â˜ƒ = this.category.getIconItems();
      int â˜ƒx = this.isStateTriggered ? -2 : 0;
      if (â˜ƒ.size() == 1) {
         â˜ƒ.renderAndDecorateFakeItem((ItemStack)â˜ƒ.get(0), this.x + 9 + â˜ƒx, this.y + 5);
      } else if (â˜ƒ.size() == 2) {
         â˜ƒ.renderAndDecorateFakeItem((ItemStack)â˜ƒ.get(0), this.x + 3 + â˜ƒx, this.y + 5);
         â˜ƒ.renderAndDecorateFakeItem((ItemStack)â˜ƒ.get(1), this.x + 14 + â˜ƒx, this.y + 5);
      }
   }

   public RecipeBookCategories getCategory() {
      return this.category;
   }

   public boolean updateVisibility(ClientRecipeBook var1) {
      List<RecipeCollection> â˜ƒ = â˜ƒ.getCollection(this.category);
      this.visible = false;
      if (â˜ƒ != null) {
         for(RecipeCollection â˜ƒx : â˜ƒ) {
            if (â˜ƒx.hasKnownRecipes() && â˜ƒx.hasFitting()) {
               this.visible = true;
               break;
            }
         }
      }

      return this.visible;
   }
}
