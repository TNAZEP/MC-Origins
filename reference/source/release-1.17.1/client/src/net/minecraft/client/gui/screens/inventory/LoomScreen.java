package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.LoomMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BannerPattern;

public class LoomScreen extends AbstractContainerScreen<LoomMenu> {
   private static final ResourceLocation BG_LOCATION = new ResourceLocation("textures/gui/container/loom.png");
   private static final int BASE_PATTERN_INDEX = 1;
   private static final int PATTERN_COLUMNS = 4;
   private static final int PATTERN_ROWS = 4;
   private static final int TOTAL_PATTERN_ROWS = (BannerPattern.COUNT - BannerPattern.PATTERN_ITEM_COUNT - 1 + 4 - 1) / 4;
   private static final int SCROLLER_WIDTH = 12;
   private static final int SCROLLER_HEIGHT = 15;
   private static final int PATTERN_IMAGE_SIZE = 14;
   private static final int SCROLLER_FULL_HEIGHT = 56;
   private static final int PATTERNS_X = 60;
   private static final int PATTERNS_Y = 13;
   private ModelPart flag;
   @Nullable
   private List<Pair<BannerPattern, DyeColor>> resultBannerPatterns;
   private ItemStack bannerStack = ItemStack.EMPTY;
   private ItemStack dyeStack = ItemStack.EMPTY;
   private ItemStack patternStack = ItemStack.EMPTY;
   private boolean displayPatterns;
   private boolean displaySpecialPattern;
   private boolean hasMaxPatterns;
   private float scrollOffs;
   private boolean scrolling;
   private int startIndex = 1;

   public LoomScreen(LoomMenu var1, Inventory var2, Component var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.registerUpdateListener(this::containerChanged);
      this.titleLabelY -= 2;
   }

   @Override
   protected void init() {
      super.init();
      this.flag = this.minecraft.getEntityModels().bakeLayer(ModelLayers.BANNER).getChild("flag");
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.renderTooltip(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected void renderBg(PoseStack var1, float var2, int var3, int var4) {
      this.renderBackground(â˜ƒ);
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderTexture(0, BG_LOCATION);
      int â˜ƒ = this.leftPos;
      int â˜ƒx = this.topPos;
      this.blit(â˜ƒ, â˜ƒ, â˜ƒx, 0, 0, this.imageWidth, this.imageHeight);
      Slot â˜ƒxx = this.menu.getBannerSlot();
      Slot â˜ƒxxx = this.menu.getDyeSlot();
      Slot â˜ƒxxxx = this.menu.getPatternSlot();
      Slot â˜ƒxxxxx = this.menu.getResultSlot();
      if (!â˜ƒxx.hasItem()) {
         this.blit(â˜ƒ, â˜ƒ + â˜ƒxx.x, â˜ƒx + â˜ƒxx.y, this.imageWidth, 0, 16, 16);
      }

      if (!â˜ƒxxx.hasItem()) {
         this.blit(â˜ƒ, â˜ƒ + â˜ƒxxx.x, â˜ƒx + â˜ƒxxx.y, this.imageWidth + 16, 0, 16, 16);
      }

      if (!â˜ƒxxxx.hasItem()) {
         this.blit(â˜ƒ, â˜ƒ + â˜ƒxxxx.x, â˜ƒx + â˜ƒxxxx.y, this.imageWidth + 32, 0, 16, 16);
      }

      int â˜ƒ = (int)(41.0F * this.scrollOffs);
      this.blit(â˜ƒ, â˜ƒ + 119, â˜ƒx + 13 + â˜ƒ, 232 + (this.displayPatterns ? 0 : 12), 0, 12, 15);
      Lighting.setupForFlatItems();
      if (this.resultBannerPatterns != null && !this.hasMaxPatterns) {
         MultiBufferSource.BufferSource â˜ƒx = this.minecraft.renderBuffers().bufferSource();
         â˜ƒ.pushPose();
         â˜ƒ.translate((double)(â˜ƒ + 139), (double)(â˜ƒx + 52), 0.0);
         â˜ƒ.scale(24.0F, -24.0F, 1.0F);
         â˜ƒ.translate(0.5, 0.5, 0.5);
         float â˜ƒxx = 0.6666667F;
         â˜ƒ.scale(0.6666667F, -0.6666667F, -0.6666667F);
         this.flag.xRot = 0.0F;
         this.flag.y = -32.0F;
         BannerRenderer.renderPatterns(â˜ƒ, â˜ƒx, 15728880, OverlayTexture.NO_OVERLAY, this.flag, ModelBakery.BANNER_BASE, true, this.resultBannerPatterns);
         â˜ƒ.popPose();
         â˜ƒx.endBatch();
      } else if (this.hasMaxPatterns) {
         this.blit(â˜ƒ, â˜ƒ + â˜ƒxxxxx.x - 2, â˜ƒx + â˜ƒxxxxx.y - 2, this.imageWidth, 17, 17, 16);
      }

      if (this.displayPatterns) {
         int â˜ƒ = â˜ƒ + 60;
         int â˜ƒx = â˜ƒx + 13;
         int â˜ƒxx = this.startIndex + 16;

         for(int â˜ƒxxx = this.startIndex; â˜ƒxxx < â˜ƒxx && â˜ƒxxx < BannerPattern.COUNT - BannerPattern.PATTERN_ITEM_COUNT; ++â˜ƒxxx) {
            int â˜ƒxxxx = â˜ƒxxx - this.startIndex;
            int â˜ƒxxxxx = â˜ƒ + â˜ƒxxxx % 4 * 14;
            int â˜ƒxxxxxx = â˜ƒx + â˜ƒxxxx / 4 * 14;
            RenderSystem.setShaderTexture(0, BG_LOCATION);
            int â˜ƒxxxxxxx = this.imageHeight;
            if (â˜ƒxxx == this.menu.getSelectedBannerPatternIndex()) {
               â˜ƒxxxxxxx += 14;
            } else if (â˜ƒ >= â˜ƒxxxxx && â˜ƒ >= â˜ƒxxxxxx && â˜ƒ < â˜ƒxxxxx + 14 && â˜ƒ < â˜ƒxxxxxx + 14) {
               â˜ƒxxxxxxx += 28;
            }

            this.blit(â˜ƒ, â˜ƒxxxxx, â˜ƒxxxxxx, 0, â˜ƒxxxxxxx, 14, 14);
            this.renderPattern(â˜ƒxxx, â˜ƒxxxxx, â˜ƒxxxxxx);
         }
      } else if (this.displaySpecialPattern) {
         int â˜ƒ = â˜ƒ + 60;
         int â˜ƒx = â˜ƒx + 13;
         RenderSystem.setShaderTexture(0, BG_LOCATION);
         this.blit(â˜ƒ, â˜ƒ, â˜ƒx, 0, this.imageHeight, 14, 14);
         int â˜ƒxx = this.menu.getSelectedBannerPatternIndex();
         this.renderPattern(â˜ƒxx, â˜ƒ, â˜ƒx);
      }

      Lighting.setupFor3DItems();
   }

   private void renderPattern(int var1, int var2, int var3) {
      ItemStack â˜ƒ = new ItemStack(Items.GRAY_BANNER);
      CompoundTag â˜ƒx = â˜ƒ.getOrCreateTagElement("BlockEntityTag");
      ListTag â˜ƒxx = new BannerPattern.Builder()
         .addPattern(BannerPattern.BASE, DyeColor.GRAY)
         .addPattern(BannerPattern.values()[â˜ƒ], DyeColor.WHITE)
         .toListTag();
      â˜ƒx.put("Patterns", â˜ƒxx);
      PoseStack â˜ƒxxx = new PoseStack();
      â˜ƒxxx.pushPose();
      â˜ƒxxx.translate((double)((float)â˜ƒ + 0.5F), (double)(â˜ƒ + 16), 0.0);
      â˜ƒxxx.scale(6.0F, -6.0F, 1.0F);
      â˜ƒxxx.translate(0.5, 0.5, 0.0);
      â˜ƒxxx.translate(0.5, 0.5, 0.5);
      float â˜ƒxxxx = 0.6666667F;
      â˜ƒxxx.scale(0.6666667F, -0.6666667F, -0.6666667F);
      MultiBufferSource.BufferSource â˜ƒxxxxx = this.minecraft.renderBuffers().bufferSource();
      this.flag.xRot = 0.0F;
      this.flag.y = -32.0F;
      List<Pair<BannerPattern, DyeColor>> â˜ƒxxxxxx = BannerBlockEntity.createPatterns(DyeColor.GRAY, BannerBlockEntity.getItemPatterns(â˜ƒ));
      BannerRenderer.renderPatterns(â˜ƒxxx, â˜ƒxxxxx, 15728880, OverlayTexture.NO_OVERLAY, this.flag, ModelBakery.BANNER_BASE, true, â˜ƒxxxxxx);
      â˜ƒxxx.popPose();
      â˜ƒxxxxx.endBatch();
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      this.scrolling = false;
      if (this.displayPatterns) {
         int â˜ƒ = this.leftPos + 60;
         int â˜ƒx = this.topPos + 13;
         int â˜ƒxx = this.startIndex + 16;

         for(int â˜ƒxxx = this.startIndex; â˜ƒxxx < â˜ƒxx; ++â˜ƒxxx) {
            int â˜ƒxxxx = â˜ƒxxx - this.startIndex;
            double â˜ƒxxxxx = â˜ƒ - (double)(â˜ƒ + â˜ƒxxxx % 4 * 14);
            double â˜ƒxxxxxx = â˜ƒ - (double)(â˜ƒx + â˜ƒxxxx / 4 * 14);
            if (â˜ƒxxxxx >= 0.0 && â˜ƒxxxxxx >= 0.0 && â˜ƒxxxxx < 14.0 && â˜ƒxxxxxx < 14.0 && this.menu.clickMenuButton(this.minecraft.player, â˜ƒxxx)) {
               Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_LOOM_SELECT_PATTERN, 1.0F));
               this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, â˜ƒxxx);
               return true;
            }
         }

         â˜ƒ = this.leftPos + 119;
         â˜ƒx = this.topPos + 9;
         if (â˜ƒ >= (double)â˜ƒ && â˜ƒ < (double)(â˜ƒ + 12) && â˜ƒ >= (double)â˜ƒx && â˜ƒ < (double)(â˜ƒx + 56)) {
            this.scrolling = true;
         }
      }

      return super.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean mouseDragged(double var1, double var3, int var5, double var6, double var8) {
      if (this.scrolling && this.displayPatterns) {
         int â˜ƒ = this.topPos + 13;
         int â˜ƒx = â˜ƒ + 56;
         this.scrollOffs = ((float)â˜ƒ - (float)â˜ƒ - 7.5F) / ((float)(â˜ƒx - â˜ƒ) - 15.0F);
         this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
         int â˜ƒxx = TOTAL_PATTERN_ROWS - 4;
         int â˜ƒxxx = (int)((double)(this.scrollOffs * (float)â˜ƒxx) + 0.5);
         if (â˜ƒxxx < 0) {
            â˜ƒxxx = 0;
         }

         this.startIndex = 1 + â˜ƒxxx * 4;
         return true;
      } else {
         return super.mouseDragged(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public boolean mouseScrolled(double var1, double var3, double var5) {
      if (this.displayPatterns) {
         int â˜ƒ = TOTAL_PATTERN_ROWS - 4;
         this.scrollOffs = (float)((double)this.scrollOffs - â˜ƒ / (double)â˜ƒ);
         this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
         this.startIndex = 1 + (int)((double)(this.scrollOffs * (float)â˜ƒ) + 0.5) * 4;
      }

      return true;
   }

   @Override
   protected boolean hasClickedOutside(double var1, double var3, int var5, int var6, int var7) {
      return â˜ƒ < (double)â˜ƒ || â˜ƒ < (double)â˜ƒ || â˜ƒ >= (double)(â˜ƒ + this.imageWidth) || â˜ƒ >= (double)(â˜ƒ + this.imageHeight);
   }

   private void containerChanged() {
      ItemStack â˜ƒ = this.menu.getResultSlot().getItem();
      if (â˜ƒ.isEmpty()) {
         this.resultBannerPatterns = null;
      } else {
         this.resultBannerPatterns = BannerBlockEntity.createPatterns(((BannerItem)â˜ƒ.getItem()).getColor(), BannerBlockEntity.getItemPatterns(â˜ƒ));
      }

      ItemStack â˜ƒ = this.menu.getBannerSlot().getItem();
      ItemStack â˜ƒx = this.menu.getDyeSlot().getItem();
      ItemStack â˜ƒxx = this.menu.getPatternSlot().getItem();
      CompoundTag â˜ƒxxx = â˜ƒ.getOrCreateTagElement("BlockEntityTag");
      this.hasMaxPatterns = â˜ƒxxx.contains("Patterns", 9) && !â˜ƒ.isEmpty() && â˜ƒxxx.getList("Patterns", 10).size() >= 6;
      if (this.hasMaxPatterns) {
         this.resultBannerPatterns = null;
      }

      if (!ItemStack.matches(â˜ƒ, this.bannerStack) || !ItemStack.matches(â˜ƒx, this.dyeStack) || !ItemStack.matches(â˜ƒxx, this.patternStack)) {
         this.displayPatterns = !â˜ƒ.isEmpty() && !â˜ƒx.isEmpty() && â˜ƒxx.isEmpty() && !this.hasMaxPatterns;
         this.displaySpecialPattern = !this.hasMaxPatterns && !â˜ƒxx.isEmpty() && !â˜ƒ.isEmpty() && !â˜ƒx.isEmpty();
      }

      this.bannerStack = â˜ƒ.copy();
      this.dyeStack = â˜ƒx.copy();
      this.patternStack = â˜ƒxx.copy();
   }
}
