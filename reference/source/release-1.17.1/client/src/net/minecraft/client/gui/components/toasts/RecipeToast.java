package net.minecraft.client.gui.components.toasts;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public class RecipeToast implements Toast {
   private static final long DISPLAY_TIME = 5000L;
   private static final Component TITLE_TEXT = new TranslatableComponent("recipe.toast.title");
   private static final Component DESCRIPTION_TEXT = new TranslatableComponent("recipe.toast.description");
   private final List<Recipe<?>> recipes = Lists.<Recipe<?>>newArrayList();
   private long lastChanged;
   private boolean changed;

   public RecipeToast(Recipe<?> var1) {
      this.recipes.add(â˜ƒ);
   }

   @Override
   public Toast.Visibility render(PoseStack var1, ToastComponent var2, long var3) {
      if (this.changed) {
         this.lastChanged = â˜ƒ;
         this.changed = false;
      }

      if (this.recipes.isEmpty()) {
         return Toast.Visibility.HIDE;
      } else {
         RenderSystem.setShader(GameRenderer::getPositionTexShader);
         RenderSystem.setShaderTexture(0, TEXTURE);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         â˜ƒ.blit(â˜ƒ, 0, 0, 0, 32, this.width(), this.height());
         â˜ƒ.getMinecraft().font.draw(â˜ƒ, TITLE_TEXT, 30.0F, 7.0F, -11534256);
         â˜ƒ.getMinecraft().font.draw(â˜ƒ, DESCRIPTION_TEXT, 30.0F, 18.0F, -16777216);
         Recipe<?> â˜ƒ = (Recipe)this.recipes.get((int)(â˜ƒ / Math.max(1L, 5000L / (long)this.recipes.size()) % (long)this.recipes.size()));
         ItemStack â˜ƒx = â˜ƒ.getToastSymbol();
         PoseStack â˜ƒxx = RenderSystem.getModelViewStack();
         â˜ƒxx.pushPose();
         â˜ƒxx.scale(0.6F, 0.6F, 1.0F);
         RenderSystem.applyModelViewMatrix();
         â˜ƒ.getMinecraft().getItemRenderer().renderAndDecorateFakeItem(â˜ƒx, 3, 3);
         â˜ƒxx.popPose();
         RenderSystem.applyModelViewMatrix();
         â˜ƒ.getMinecraft().getItemRenderer().renderAndDecorateFakeItem(â˜ƒ.getResultItem(), 8, 8);
         return â˜ƒ - this.lastChanged >= 5000L ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
      }
   }

   private void addItem(Recipe<?> var1) {
      this.recipes.add(â˜ƒ);
      this.changed = true;
   }

   public static void addOrUpdate(ToastComponent var0, Recipe<?> var1) {
      RecipeToast â˜ƒ = â˜ƒ.getToast(RecipeToast.class, NO_TOKEN);
      if (â˜ƒ == null) {
         â˜ƒ.addToast(new RecipeToast(â˜ƒ));
      } else {
         â˜ƒ.addItem(â˜ƒ);
      }
   }
}
