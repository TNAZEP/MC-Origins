package net.minecraft.client.gui.screens.inventory;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ServerboundSetBeaconPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.BeaconMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;

public class BeaconScreen extends AbstractContainerScreen<BeaconMenu> {
   static final ResourceLocation BEACON_LOCATION = new ResourceLocation("textures/gui/container/beacon.png");
   private static final Component PRIMARY_EFFECT_LABEL = new TranslatableComponent("block.minecraft.beacon.primary");
   private static final Component SECONDARY_EFFECT_LABEL = new TranslatableComponent("block.minecraft.beacon.secondary");
   private final List<BeaconScreen.BeaconButton> beaconButtons = Lists.<BeaconScreen.BeaconButton>newArrayList();
   @Nullable
   MobEffect primary;
   @Nullable
   MobEffect secondary;

   public BeaconScreen(final BeaconMenu var1, Inventory var2, Component var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
      this.imageWidth = 230;
      this.imageHeight = 219;
      â˜ƒ.addSlotListener(new ContainerListener() {
         @Override
         public void slotChanged(AbstractContainerMenu var1x, int var2, ItemStack var3) {
         }

         @Override
         public void dataChanged(AbstractContainerMenu var1x, int var2, int var3) {
            BeaconScreen.this.primary = â˜ƒ.getPrimaryEffect();
            BeaconScreen.this.secondary = â˜ƒ.getSecondaryEffect();
         }
      });
   }

   private <T extends AbstractWidget & BeaconScreen.BeaconButton> void addBeaconButton(T var1) {
      this.addRenderableWidget(â˜ƒ);
      this.beaconButtons.add(â˜ƒ);
   }

   @Override
   protected void init() {
      super.init();
      this.beaconButtons.clear();
      this.addBeaconButton(new BeaconScreen.BeaconConfirmButton(this.leftPos + 164, this.topPos + 107));
      this.addBeaconButton(new BeaconScreen.BeaconCancelButton(this.leftPos + 190, this.topPos + 107));

      for(int â˜ƒ = 0; â˜ƒ <= 2; ++â˜ƒ) {
         int â˜ƒx = BeaconBlockEntity.BEACON_EFFECTS[â˜ƒ].length;
         int â˜ƒxx = â˜ƒx * 22 + (â˜ƒx - 1) * 2;

         for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒx; ++â˜ƒxxx) {
            MobEffect â˜ƒxxxx = BeaconBlockEntity.BEACON_EFFECTS[â˜ƒ][â˜ƒxxx];
            BeaconScreen.BeaconPowerButton â˜ƒxxxxx = new BeaconScreen.BeaconPowerButton(
               this.leftPos + 76 + â˜ƒxxx * 24 - â˜ƒxx / 2, this.topPos + 22 + â˜ƒ * 25, â˜ƒxxxx, true, â˜ƒ
            );
            â˜ƒxxxxx.active = false;
            this.addBeaconButton(â˜ƒxxxxx);
         }
      }

      int â˜ƒ = 3;
      int â˜ƒx = BeaconBlockEntity.BEACON_EFFECTS[3].length + 1;
      int â˜ƒxx = â˜ƒx * 22 + (â˜ƒx - 1) * 2;

      for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒx - 1; ++â˜ƒxxx) {
         MobEffect â˜ƒxxxx = BeaconBlockEntity.BEACON_EFFECTS[3][â˜ƒxxx];
         BeaconScreen.BeaconPowerButton â˜ƒxxxxx = new BeaconScreen.BeaconPowerButton(
            this.leftPos + 167 + â˜ƒxxx * 24 - â˜ƒxx / 2, this.topPos + 47, â˜ƒxxxx, false, 3
         );
         â˜ƒxxxxx.active = false;
         this.addBeaconButton(â˜ƒxxxxx);
      }

      BeaconScreen.BeaconPowerButton â˜ƒxxx = new BeaconScreen.BeaconUpgradePowerButton(
         this.leftPos + 167 + (â˜ƒx - 1) * 24 - â˜ƒxx / 2, this.topPos + 47, BeaconBlockEntity.BEACON_EFFECTS[0][0]
      );
      â˜ƒxxx.visible = false;
      this.addBeaconButton(â˜ƒxxx);
   }

   @Override
   public void containerTick() {
      super.containerTick();
      this.updateButtons();
   }

   void updateButtons() {
      int â˜ƒ = this.menu.getLevels();
      this.beaconButtons.forEach(var1x -> var1x.updateStatus(â˜ƒ));
   }

   @Override
   protected void renderLabels(PoseStack var1, int var2, int var3) {
      drawCenteredString(â˜ƒ, this.font, PRIMARY_EFFECT_LABEL, 62, 10, 14737632);
      drawCenteredString(â˜ƒ, this.font, SECONDARY_EFFECT_LABEL, 169, 10, 14737632);

      for(BeaconScreen.BeaconButton â˜ƒ : this.beaconButtons) {
         if (â˜ƒ.isShowingTooltip()) {
            â˜ƒ.renderToolTip(â˜ƒ, â˜ƒ - this.leftPos, â˜ƒ - this.topPos);
            break;
         }
      }
   }

   @Override
   protected void renderBg(PoseStack var1, float var2, int var3, int var4) {
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShaderTexture(0, BEACON_LOCATION);
      int â˜ƒ = (this.width - this.imageWidth) / 2;
      int â˜ƒx = (this.height - this.imageHeight) / 2;
      this.blit(â˜ƒ, â˜ƒ, â˜ƒx, 0, 0, this.imageWidth, this.imageHeight);
      this.itemRenderer.blitOffset = 100.0F;
      this.itemRenderer.renderAndDecorateItem(new ItemStack(Items.NETHERITE_INGOT), â˜ƒ + 20, â˜ƒx + 109);
      this.itemRenderer.renderAndDecorateItem(new ItemStack(Items.EMERALD), â˜ƒ + 41, â˜ƒx + 109);
      this.itemRenderer.renderAndDecorateItem(new ItemStack(Items.DIAMOND), â˜ƒ + 41 + 22, â˜ƒx + 109);
      this.itemRenderer.renderAndDecorateItem(new ItemStack(Items.GOLD_INGOT), â˜ƒ + 42 + 44, â˜ƒx + 109);
      this.itemRenderer.renderAndDecorateItem(new ItemStack(Items.IRON_INGOT), â˜ƒ + 42 + 66, â˜ƒx + 109);
      this.itemRenderer.blitOffset = 0.0F;
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.renderTooltip(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   interface BeaconButton {
      boolean isShowingTooltip();

      void renderToolTip(PoseStack var1, int var2, int var3);

      void updateStatus(int var1);
   }

   class BeaconCancelButton extends BeaconScreen.BeaconSpriteScreenButton {
      public BeaconCancelButton(int var2, int var3) {
         super(â˜ƒ, â˜ƒ, 112, 220, CommonComponents.GUI_CANCEL);
      }

      @Override
      public void onPress() {
         BeaconScreen.this.minecraft.player.closeContainer();
      }

      @Override
      public void updateStatus(int var1) {
      }
   }

   class BeaconConfirmButton extends BeaconScreen.BeaconSpriteScreenButton {
      public BeaconConfirmButton(int var2, int var3) {
         super(â˜ƒ, â˜ƒ, 90, 220, CommonComponents.GUI_DONE);
      }

      @Override
      public void onPress() {
         BeaconScreen.this.minecraft
            .getConnection()
            .send(new ServerboundSetBeaconPacket(MobEffect.getId(BeaconScreen.this.primary), MobEffect.getId(BeaconScreen.this.secondary)));
         BeaconScreen.this.minecraft.player.closeContainer();
      }

      @Override
      public void updateStatus(int var1) {
         this.active = BeaconScreen.this.menu.hasPayment() && BeaconScreen.this.primary != null;
      }
   }

   class BeaconPowerButton extends BeaconScreen.BeaconScreenButton {
      private final boolean isPrimary;
      protected final int tier;
      private MobEffect effect;
      private TextureAtlasSprite sprite;
      private Component tooltip;

      public BeaconPowerButton(int var2, int var3, MobEffect var4, boolean var5, int var6) {
         super(â˜ƒ, â˜ƒ);
         this.isPrimary = â˜ƒ;
         this.tier = â˜ƒ;
         this.setEffect(â˜ƒ);
      }

      protected void setEffect(MobEffect var1) {
         this.effect = â˜ƒ;
         this.sprite = Minecraft.getInstance().getMobEffectTextures().get(â˜ƒ);
         this.tooltip = this.createEffectDescription(â˜ƒ);
      }

      protected MutableComponent createEffectDescription(MobEffect var1) {
         return new TranslatableComponent(â˜ƒ.getDescriptionId());
      }

      @Override
      public void onPress() {
         if (!this.isSelected()) {
            if (this.isPrimary) {
               BeaconScreen.this.primary = this.effect;
            } else {
               BeaconScreen.this.secondary = this.effect;
            }

            BeaconScreen.this.updateButtons();
         }
      }

      @Override
      public void renderToolTip(PoseStack var1, int var2, int var3) {
         BeaconScreen.this.renderTooltip(â˜ƒ, this.tooltip, â˜ƒ, â˜ƒ);
      }

      @Override
      protected void renderIcon(PoseStack var1) {
         RenderSystem.setShaderTexture(0, this.sprite.atlas().location());
         blit(â˜ƒ, this.x + 2, this.y + 2, this.getBlitOffset(), 18, 18, this.sprite);
      }

      @Override
      public void updateStatus(int var1) {
         this.active = this.tier < â˜ƒ;
         this.setSelected(this.effect == (this.isPrimary ? BeaconScreen.this.primary : BeaconScreen.this.secondary));
      }

      @Override
      protected MutableComponent createNarrationMessage() {
         return this.createEffectDescription(this.effect);
      }
   }

   abstract static class BeaconScreenButton extends AbstractButton implements BeaconScreen.BeaconButton {
      private boolean selected;

      protected BeaconScreenButton(int var1, int var2) {
         super(â˜ƒ, â˜ƒ, 22, 22, TextComponent.EMPTY);
      }

      protected BeaconScreenButton(int var1, int var2, Component var3) {
         super(â˜ƒ, â˜ƒ, 22, 22, â˜ƒ);
      }

      @Override
      public void renderButton(PoseStack var1, int var2, int var3, float var4) {
         RenderSystem.setShader(GameRenderer::getPositionTexShader);
         RenderSystem.setShaderTexture(0, BeaconScreen.BEACON_LOCATION);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         int â˜ƒ = 219;
         int â˜ƒx = 0;
         if (!this.active) {
            â˜ƒx += this.width * 2;
         } else if (this.selected) {
            â˜ƒx += this.width * 1;
         } else if (this.isHovered()) {
            â˜ƒx += this.width * 3;
         }

         this.blit(â˜ƒ, this.x, this.y, â˜ƒx, 219, this.width, this.height);
         this.renderIcon(â˜ƒ);
      }

      protected abstract void renderIcon(PoseStack var1);

      public boolean isSelected() {
         return this.selected;
      }

      public void setSelected(boolean var1) {
         this.selected = â˜ƒ;
      }

      @Override
      public boolean isShowingTooltip() {
         return this.isHovered;
      }

      @Override
      public void updateNarration(NarrationElementOutput var1) {
         this.defaultButtonNarrationText(â˜ƒ);
      }
   }

   abstract class BeaconSpriteScreenButton extends BeaconScreen.BeaconScreenButton {
      private final int iconX;
      private final int iconY;

      protected BeaconSpriteScreenButton(int var2, int var3, int var4, int var5, Component var6) {
         super(â˜ƒ, â˜ƒ, â˜ƒ);
         this.iconX = â˜ƒ;
         this.iconY = â˜ƒ;
      }

      @Override
      protected void renderIcon(PoseStack var1) {
         this.blit(â˜ƒ, this.x + 2, this.y + 2, this.iconX, this.iconY, 18, 18);
      }

      @Override
      public void renderToolTip(PoseStack var1, int var2, int var3) {
         BeaconScreen.this.renderTooltip(â˜ƒ, BeaconScreen.this.title, â˜ƒ, â˜ƒ);
      }
   }

   class BeaconUpgradePowerButton extends BeaconScreen.BeaconPowerButton {
      public BeaconUpgradePowerButton(int var2, int var3, MobEffect var4) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, false, 3);
      }

      @Override
      protected MutableComponent createEffectDescription(MobEffect var1) {
         return new TranslatableComponent(â˜ƒ.getDescriptionId()).append(" II");
      }

      @Override
      public void updateStatus(int var1) {
         if (BeaconScreen.this.primary != null) {
            this.visible = true;
            this.setEffect(BeaconScreen.this.primary);
            super.updateStatus(â˜ƒ);
         } else {
            this.visible = false;
         }
      }
   }
}
