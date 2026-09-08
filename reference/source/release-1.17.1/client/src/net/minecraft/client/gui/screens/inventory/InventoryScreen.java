package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;

public class InventoryScreen extends EffectRenderingInventoryScreen<InventoryMenu> implements RecipeUpdateListener {
   private static final ResourceLocation RECIPE_BUTTON_LOCATION = new ResourceLocation("textures/gui/recipe_button.png");
   private float xMouse;
   private float yMouse;
   private final RecipeBookComponent recipeBookComponent = new RecipeBookComponent();
   private boolean recipeBookComponentInitialized;
   private boolean widthTooNarrow;
   private boolean buttonClicked;

   public InventoryScreen(Player var1) {
      super(â˜ƒ.inventoryMenu, â˜ƒ.getInventory(), new TranslatableComponent("container.crafting"));
      this.passEvents = true;
      this.titleLabelX = 97;
   }

   @Override
   public void containerTick() {
      if (this.minecraft.gameMode.hasInfiniteItems()) {
         this.minecraft.setScreen(new CreativeModeInventoryScreen(this.minecraft.player));
      } else {
         this.recipeBookComponent.tick();
      }
   }

   @Override
   protected void init() {
      if (this.minecraft.gameMode.hasInfiniteItems()) {
         this.minecraft.setScreen(new CreativeModeInventoryScreen(this.minecraft.player));
      } else {
         super.init();
         this.widthTooNarrow = this.width < 379;
         this.recipeBookComponent.init(this.width, this.height, this.minecraft, this.widthTooNarrow, this.menu);
         this.recipeBookComponentInitialized = true;
         this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
         this.addRenderableWidget(new ImageButton(this.leftPos + 104, this.height / 2 - 22, 20, 18, 0, 0, 19, RECIPE_BUTTON_LOCATION, var1 -> {
            this.recipeBookComponent.toggleVisibility();
            this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
            ((ImageButton)var1).setPosition(this.leftPos + 104, this.height / 2 - 22);
            this.buttonClicked = true;
         }));
         this.addWidget(this.recipeBookComponent);
         this.setInitialFocus(this.recipeBookComponent);
      }
   }

   @Override
   protected void renderLabels(PoseStack var1, int var2, int var3) {
      this.font.draw(â˜ƒ, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      this.doRenderEffects = !this.recipeBookComponent.isVisible();
      if (this.recipeBookComponent.isVisible() && this.widthTooNarrow) {
         this.renderBg(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.recipeBookComponent.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         this.recipeBookComponent.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.recipeBookComponent.renderGhostRecipe(â˜ƒ, this.leftPos, this.topPos, false, â˜ƒ);
      }

      this.renderTooltip(â˜ƒ, â˜ƒ, â˜ƒ);
      this.recipeBookComponent.renderTooltip(â˜ƒ, this.leftPos, this.topPos, â˜ƒ, â˜ƒ);
      this.xMouse = (float)â˜ƒ;
      this.yMouse = (float)â˜ƒ;
   }

   @Override
   protected void renderBg(PoseStack var1, float var2, int var3, int var4) {
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShaderTexture(0, INVENTORY_LOCATION);
      int â˜ƒ = this.leftPos;
      int â˜ƒx = this.topPos;
      this.blit(â˜ƒ, â˜ƒ, â˜ƒx, 0, 0, this.imageWidth, this.imageHeight);
      renderEntityInInventory(â˜ƒ + 51, â˜ƒx + 75, 30, (float)(â˜ƒ + 51) - this.xMouse, (float)(â˜ƒx + 75 - 50) - this.yMouse, this.minecraft.player);
   }

   public static void renderEntityInInventory(int var0, int var1, int var2, float var3, float var4, LivingEntity var5) {
      float â˜ƒ = (float)Math.atan((double)(â˜ƒ / 40.0F));
      float â˜ƒx = (float)Math.atan((double)(â˜ƒ / 40.0F));
      PoseStack â˜ƒxx = RenderSystem.getModelViewStack();
      â˜ƒxx.pushPose();
      â˜ƒxx.translate((double)â˜ƒ, (double)â˜ƒ, 1050.0);
      â˜ƒxx.scale(1.0F, 1.0F, -1.0F);
      RenderSystem.applyModelViewMatrix();
      PoseStack â˜ƒxxx = new PoseStack();
      â˜ƒxxx.translate(0.0, 0.0, 1000.0);
      â˜ƒxxx.scale((float)â˜ƒ, (float)â˜ƒ, (float)â˜ƒ);
      Quaternion â˜ƒxxxx = Vector3f.ZP.rotationDegrees(180.0F);
      Quaternion â˜ƒxxxxx = Vector3f.XP.rotationDegrees(â˜ƒx * 20.0F);
      â˜ƒxxxx.mul(â˜ƒxxxxx);
      â˜ƒxxx.mulPose(â˜ƒxxxx);
      float â˜ƒxxxxxx = â˜ƒ.yBodyRot;
      float â˜ƒxxxxxxx = â˜ƒ.getYRot();
      float â˜ƒxxxxxxxx = â˜ƒ.getXRot();
      float â˜ƒxxxxxxxxx = â˜ƒ.yHeadRotO;
      float â˜ƒxxxxxxxxxx = â˜ƒ.yHeadRot;
      â˜ƒ.yBodyRot = 180.0F + â˜ƒ * 20.0F;
      â˜ƒ.setYRot(180.0F + â˜ƒ * 40.0F);
      â˜ƒ.setXRot(-â˜ƒx * 20.0F);
      â˜ƒ.yHeadRot = â˜ƒ.getYRot();
      â˜ƒ.yHeadRotO = â˜ƒ.getYRot();
      Lighting.setupForEntityInInventory();
      EntityRenderDispatcher â˜ƒxxxxxxxxxxx = Minecraft.getInstance().getEntityRenderDispatcher();
      â˜ƒxxxxx.conj();
      â˜ƒxxxxxxxxxxx.overrideCameraOrientation(â˜ƒxxxxx);
      â˜ƒxxxxxxxxxxx.setRenderShadow(false);
      MultiBufferSource.BufferSource â˜ƒxxxxxxxxxxxx = Minecraft.getInstance().renderBuffers().bufferSource();
      RenderSystem.runAsFancy(() -> â˜ƒ.render(â˜ƒ, 0.0, 0.0, 0.0, 0.0F, 1.0F, â˜ƒ, â˜ƒ, 15728880));
      â˜ƒxxxxxxxxxxxx.endBatch();
      â˜ƒxxxxxxxxxxx.setRenderShadow(true);
      â˜ƒ.yBodyRot = â˜ƒxxxxxx;
      â˜ƒ.setYRot(â˜ƒxxxxxxx);
      â˜ƒ.setXRot(â˜ƒxxxxxxxx);
      â˜ƒ.yHeadRotO = â˜ƒxxxxxxxxx;
      â˜ƒ.yHeadRot = â˜ƒxxxxxxxxxx;
      â˜ƒxx.popPose();
      RenderSystem.applyModelViewMatrix();
      Lighting.setupFor3DItems();
   }

   @Override
   protected boolean isHovering(int var1, int var2, int var3, int var4, double var5, double var7) {
      return (!this.widthTooNarrow || !this.recipeBookComponent.isVisible()) && super.isHovering(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (this.recipeBookComponent.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ)) {
         this.setFocused(this.recipeBookComponent);
         return true;
      } else {
         return this.widthTooNarrow && this.recipeBookComponent.isVisible() ? false : super.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public boolean mouseReleased(double var1, double var3, int var5) {
      if (this.buttonClicked) {
         this.buttonClicked = false;
         return true;
      } else {
         return super.mouseReleased(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   protected boolean hasClickedOutside(double var1, double var3, int var5, int var6, int var7) {
      boolean â˜ƒ = â˜ƒ < (double)â˜ƒ || â˜ƒ < (double)â˜ƒ || â˜ƒ >= (double)(â˜ƒ + this.imageWidth) || â˜ƒ >= (double)(â˜ƒ + this.imageHeight);
      return this.recipeBookComponent.hasClickedOutside(â˜ƒ, â˜ƒ, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, â˜ƒ) && â˜ƒ;
   }

   @Override
   protected void slotClicked(Slot var1, int var2, int var3, ClickType var4) {
      super.slotClicked(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.recipeBookComponent.slotClicked(â˜ƒ);
   }

   @Override
   public void recipesUpdated() {
      this.recipeBookComponent.recipesUpdated();
   }

   @Override
   public void removed() {
      if (this.recipeBookComponentInitialized) {
         this.recipeBookComponent.removed();
      }

      super.removed();
   }

   @Override
   public RecipeBookComponent getRecipeBookComponent() {
      return this.recipeBookComponent;
   }
}
