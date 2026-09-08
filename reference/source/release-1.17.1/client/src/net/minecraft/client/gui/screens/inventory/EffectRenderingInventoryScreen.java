package net.minecraft.client.gui.screens.inventory;

import com.google.common.collect.Ordering;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collection;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public abstract class EffectRenderingInventoryScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
   protected boolean doRenderEffects;

   public EffectRenderingInventoryScreen(T var1, Inventory var2, Component var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected void init() {
      super.init();
      this.checkEffectRendering();
   }

   protected void checkEffectRendering() {
      if (this.minecraft.player.getActiveEffects().isEmpty()) {
         this.leftPos = (this.width - this.imageWidth) / 2;
         this.doRenderEffects = false;
      } else {
         this.leftPos = 160 + (this.width - this.imageWidth - 200) / 2;
         this.doRenderEffects = true;
      }
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (this.doRenderEffects) {
         this.renderEffects(â˜ƒ);
      }
   }

   private void renderEffects(PoseStack var1) {
      int â˜ƒ = this.leftPos - 124;
      Collection<MobEffectInstance> â˜ƒx = this.minecraft.player.getActiveEffects();
      if (!â˜ƒx.isEmpty()) {
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         int â˜ƒxx = 33;
         if (â˜ƒx.size() > 5) {
            â˜ƒxx = 132 / (â˜ƒx.size() - 1);
         }

         Iterable<MobEffectInstance> â˜ƒxx = Ordering.natural().sortedCopy(â˜ƒx);
         this.renderBackgrounds(â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒxx);
         this.renderIcons(â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒxx);
         this.renderLabels(â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒxx);
      }
   }

   private void renderBackgrounds(PoseStack var1, int var2, int var3, Iterable<MobEffectInstance> var4) {
      RenderSystem.setShaderTexture(0, INVENTORY_LOCATION);
      int â˜ƒ = this.topPos;

      for(MobEffectInstance â˜ƒx : â˜ƒ) {
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         this.blit(â˜ƒ, â˜ƒ, â˜ƒ, 0, 166, 140, 32);
         â˜ƒ += â˜ƒ;
      }
   }

   private void renderIcons(PoseStack var1, int var2, int var3, Iterable<MobEffectInstance> var4) {
      MobEffectTextureManager â˜ƒ = this.minecraft.getMobEffectTextures();
      int â˜ƒx = this.topPos;

      for(MobEffectInstance â˜ƒxx : â˜ƒ) {
         MobEffect â˜ƒxxx = â˜ƒxx.getEffect();
         TextureAtlasSprite â˜ƒxxxx = â˜ƒ.get(â˜ƒxxx);
         RenderSystem.setShaderTexture(0, â˜ƒxxxx.atlas().location());
         blit(â˜ƒ, â˜ƒ + 6, â˜ƒx + 7, this.getBlitOffset(), 18, 18, â˜ƒxxxx);
         â˜ƒx += â˜ƒ;
      }
   }

   private void renderLabels(PoseStack var1, int var2, int var3, Iterable<MobEffectInstance> var4) {
      int â˜ƒ = this.topPos;

      for(MobEffectInstance â˜ƒx : â˜ƒ) {
         String â˜ƒxx = I18n.get(â˜ƒx.getEffect().getDescriptionId());
         if (â˜ƒx.getAmplifier() >= 1 && â˜ƒx.getAmplifier() <= 9) {
            â˜ƒxx = â˜ƒxx + " " + I18n.get("enchantment.level." + (â˜ƒx.getAmplifier() + 1));
         }

         this.font.drawShadow(â˜ƒ, â˜ƒxx, (float)(â˜ƒ + 10 + 18), (float)(â˜ƒ + 6), 16777215);
         String â˜ƒxx = MobEffectUtil.formatDuration(â˜ƒx, 1.0F);
         this.font.drawShadow(â˜ƒ, â˜ƒxx, (float)(â˜ƒ + 10 + 18), (float)(â˜ƒ + 6 + 10), 8355711);
         â˜ƒ += â˜ƒ;
      }
   }
}
