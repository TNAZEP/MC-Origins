package net.minecraft.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;

public abstract class AbstractWidget extends GuiComponent implements Widget, GuiEventListener, NarratableEntry {
   public static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation("textures/gui/widgets.png");
   protected int width;
   protected int height;
   public int x;
   public int y;
   private Component message;
   protected boolean isHovered;
   public boolean active = true;
   public boolean visible = true;
   protected float alpha = 1.0F;
   private boolean focused;

   public AbstractWidget(int var1, int var2, int var3, int var4, Component var5) {
      this.x = â˜ƒ;
      this.y = â˜ƒ;
      this.width = â˜ƒ;
      this.height = â˜ƒ;
      this.message = â˜ƒ;
   }

   public int getHeight() {
      return this.height;
   }

   protected int getYImage(boolean var1) {
      int â˜ƒ = 1;
      if (!this.active) {
         â˜ƒ = 0;
      } else if (â˜ƒ) {
         â˜ƒ = 2;
      }

      return â˜ƒ;
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      if (this.visible) {
         this.isHovered = â˜ƒ >= this.x && â˜ƒ >= this.y && â˜ƒ < this.x + this.width && â˜ƒ < this.y + this.height;
         this.renderButton(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   protected MutableComponent createNarrationMessage() {
      return wrapDefaultNarrationMessage(this.getMessage());
   }

   public static MutableComponent wrapDefaultNarrationMessage(Component var0) {
      return new TranslatableComponent("gui.narrate.button", â˜ƒ);
   }

   public void renderButton(PoseStack var1, int var2, int var3, float var4) {
      Minecraft â˜ƒ = Minecraft.getInstance();
      Font â˜ƒx = â˜ƒ.font;
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
      int â˜ƒxx = this.getYImage(this.isHovered());
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.enableDepthTest();
      this.blit(â˜ƒ, this.x, this.y, 0, 46 + â˜ƒxx * 20, this.width / 2, this.height);
      this.blit(â˜ƒ, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + â˜ƒxx * 20, this.width / 2, this.height);
      this.renderBg(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      int â˜ƒxxx = this.active ? 16777215 : 10526880;
      drawCenteredString(â˜ƒ, â˜ƒx, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, â˜ƒxxx | Mth.ceil(this.alpha * 255.0F) << 24);
   }

   protected void renderBg(PoseStack var1, Minecraft var2, int var3, int var4) {
   }

   public void onClick(double var1, double var3) {
   }

   public void onRelease(double var1, double var3) {
   }

   protected void onDrag(double var1, double var3, double var5, double var7) {
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (this.active && this.visible) {
         if (this.isValidClickButton(â˜ƒ)) {
            boolean â˜ƒ = this.clicked(â˜ƒ, â˜ƒ);
            if (â˜ƒ) {
               this.playDownSound(Minecraft.getInstance().getSoundManager());
               this.onClick(â˜ƒ, â˜ƒ);
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   @Override
   public boolean mouseReleased(double var1, double var3, int var5) {
      if (this.isValidClickButton(â˜ƒ)) {
         this.onRelease(â˜ƒ, â˜ƒ);
         return true;
      } else {
         return false;
      }
   }

   protected boolean isValidClickButton(int var1) {
      return â˜ƒ == 0;
   }

   @Override
   public boolean mouseDragged(double var1, double var3, int var5, double var6, double var8) {
      if (this.isValidClickButton(â˜ƒ)) {
         this.onDrag(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         return true;
      } else {
         return false;
      }
   }

   protected boolean clicked(double var1, double var3) {
      return this.active
         && this.visible
         && â˜ƒ >= (double)this.x
         && â˜ƒ >= (double)this.y
         && â˜ƒ < (double)(this.x + this.width)
         && â˜ƒ < (double)(this.y + this.height);
   }

   public boolean isHovered() {
      return this.isHovered || this.focused;
   }

   @Override
   public boolean changeFocus(boolean var1) {
      if (this.active && this.visible) {
         this.focused = !this.focused;
         this.onFocusedChanged(this.focused);
         return this.focused;
      } else {
         return false;
      }
   }

   protected void onFocusedChanged(boolean var1) {
   }

   @Override
   public boolean isMouseOver(double var1, double var3) {
      return this.active
         && this.visible
         && â˜ƒ >= (double)this.x
         && â˜ƒ >= (double)this.y
         && â˜ƒ < (double)(this.x + this.width)
         && â˜ƒ < (double)(this.y + this.height);
   }

   public void renderToolTip(PoseStack var1, int var2, int var3) {
   }

   public void playDownSound(SoundManager var1) {
      â˜ƒ.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
   }

   public int getWidth() {
      return this.width;
   }

   public void setWidth(int var1) {
      this.width = â˜ƒ;
   }

   public void setAlpha(float var1) {
      this.alpha = â˜ƒ;
   }

   public void setMessage(Component var1) {
      this.message = â˜ƒ;
   }

   public Component getMessage() {
      return this.message;
   }

   public boolean isFocused() {
      return this.focused;
   }

   @Override
   public boolean isActive() {
      return this.visible && this.active;
   }

   protected void setFocused(boolean var1) {
      this.focused = â˜ƒ;
   }

   @Override
   public NarratableEntry.NarrationPriority narrationPriority() {
      if (this.focused) {
         return NarratableEntry.NarrationPriority.FOCUSED;
      } else {
         return this.isHovered ? NarratableEntry.NarrationPriority.HOVERED : NarratableEntry.NarrationPriority.NONE;
      }
   }

   protected void defaultButtonNarrationText(NarrationElementOutput var1) {
      â˜ƒ.add(NarratedElementType.TITLE, this.createNarrationMessage());
      if (this.active) {
         if (this.isFocused()) {
            â˜ƒ.add(NarratedElementType.USAGE, new TranslatableComponent("narration.button.usage.focused"));
         } else {
            â˜ƒ.add(NarratedElementType.USAGE, new TranslatableComponent("narration.button.usage.hovered"));
         }
      }
   }
}
