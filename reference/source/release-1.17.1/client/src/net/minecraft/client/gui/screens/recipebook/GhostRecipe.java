package net.minecraft.client.gui.screens.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public class GhostRecipe {
   @Nullable
   private Recipe<?> recipe;
   private final List<GhostRecipe.GhostIngredient> ingredients = Lists.<GhostRecipe.GhostIngredient>newArrayList();
   float time;

   public void clear() {
      this.recipe = null;
      this.ingredients.clear();
      this.time = 0.0F;
   }

   public void addIngredient(Ingredient var1, int var2, int var3) {
      this.ingredients.add(new GhostRecipe.GhostIngredient(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public GhostRecipe.GhostIngredient get(int var1) {
      return (GhostRecipe.GhostIngredient)this.ingredients.get(â˜ƒ);
   }

   public int size() {
      return this.ingredients.size();
   }

   @Nullable
   public Recipe<?> getRecipe() {
      return this.recipe;
   }

   public void setRecipe(Recipe<?> var1) {
      this.recipe = â˜ƒ;
   }

   public void render(PoseStack var1, Minecraft var2, int var3, int var4, boolean var5, float var6) {
      if (!Screen.hasControlDown()) {
         this.time += â˜ƒ;
      }

      for(int â˜ƒ = 0; â˜ƒ < this.ingredients.size(); ++â˜ƒ) {
         GhostRecipe.GhostIngredient â˜ƒx = (GhostRecipe.GhostIngredient)this.ingredients.get(â˜ƒ);
         int â˜ƒxx = â˜ƒx.getX() + â˜ƒ;
         int â˜ƒxxx = â˜ƒx.getY() + â˜ƒ;
         if (â˜ƒ == 0 && â˜ƒ) {
            GuiComponent.fill(â˜ƒ, â˜ƒxx - 4, â˜ƒxxx - 4, â˜ƒxx + 20, â˜ƒxxx + 20, 822018048);
         } else {
            GuiComponent.fill(â˜ƒ, â˜ƒxx, â˜ƒxxx, â˜ƒxx + 16, â˜ƒxxx + 16, 822018048);
         }

         ItemStack â˜ƒx = â˜ƒx.getItem();
         ItemRenderer â˜ƒxx = â˜ƒ.getItemRenderer();
         â˜ƒxx.renderAndDecorateFakeItem(â˜ƒx, â˜ƒxx, â˜ƒxxx);
         RenderSystem.depthFunc(516);
         GuiComponent.fill(â˜ƒ, â˜ƒxx, â˜ƒxxx, â˜ƒxx + 16, â˜ƒxxx + 16, 822083583);
         RenderSystem.depthFunc(515);
         if (â˜ƒ == 0) {
            â˜ƒxx.renderGuiItemDecorations(â˜ƒ.font, â˜ƒx, â˜ƒxx, â˜ƒxxx);
         }
      }
   }

   public class GhostIngredient {
      private final Ingredient ingredient;
      private final int x;
      private final int y;

      public GhostIngredient(Ingredient var2, int var3, int var4) {
         this.ingredient = â˜ƒ;
         this.x = â˜ƒ;
         this.y = â˜ƒ;
      }

      public int getX() {
         return this.x;
      }

      public int getY() {
         return this.y;
      }

      public ItemStack getItem() {
         ItemStack[] â˜ƒ = this.ingredient.getItems();
         return â˜ƒ.length == 0 ? ItemStack.EMPTY : â˜ƒ[Mth.floor(GhostRecipe.this.time / 30.0F) % â˜ƒ.length];
      }
   }
}
