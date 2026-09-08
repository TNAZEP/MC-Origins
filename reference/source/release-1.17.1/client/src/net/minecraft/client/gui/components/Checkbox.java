package net.minecraft.client.gui.components;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class Checkbox extends AbstractButton {
   private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/checkbox.png");
   private static final int TEXT_COLOR = 14737632;
   private boolean selected;
   private final boolean showLabel;

   public Checkbox(int var1, int var2, int var3, int var4, Component var5, boolean var6) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, true);
   }

   public Checkbox(int var1, int var2, int var3, int var4, Component var5, boolean var6, boolean var7) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.selected = â˜ƒ;
      this.showLabel = â˜ƒ;
   }

   @Override
   public void onPress() {
      this.selected = !this.selected;
   }

   public boolean selected() {
      return this.selected;
   }

   @Override
   public void updateNarration(NarrationElementOutput var1) {
      â˜ƒ.add(NarratedElementType.TITLE, this.createNarrationMessage());
      if (this.active) {
         if (this.isFocused()) {
            â˜ƒ.add(NarratedElementType.USAGE, new TranslatableComponent("narration.checkbox.usage.focused"));
         } else {
            â˜ƒ.add(NarratedElementType.USAGE, new TranslatableComponent("narration.checkbox.usage.hovered"));
         }
      }
   }

   @Override
   public void renderButton(PoseStack var1, int var2, int var3, float var4) {
      Minecraft â˜ƒ = Minecraft.getInstance();
      RenderSystem.setShaderTexture(0, TEXTURE);
      RenderSystem.enableDepthTest();
      Font â˜ƒx = â˜ƒ.font;
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      blit(â˜ƒ, this.x, this.y, this.isFocused() ? 20.0F : 0.0F, this.selected ? 20.0F : 0.0F, 20, this.height, 64, 64);
      this.renderBg(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (this.showLabel) {
         drawString(â˜ƒ, â˜ƒx, this.getMessage(), this.x + 24, this.y + (this.height - 8) / 2, 14737632 | Mth.ceil(this.alpha * 255.0F) << 24);
      }
   }
}
