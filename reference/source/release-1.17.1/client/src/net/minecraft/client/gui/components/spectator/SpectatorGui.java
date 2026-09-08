package net.minecraft.client.gui.components.spectator;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.SpectatorMenuItem;
import net.minecraft.client.gui.spectator.SpectatorMenuListener;
import net.minecraft.client.gui.spectator.categories.SpectatorPage;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SpectatorGui extends GuiComponent implements SpectatorMenuListener {
   private static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation("textures/gui/widgets.png");
   public static final ResourceLocation SPECTATOR_LOCATION = new ResourceLocation("textures/gui/spectator_widgets.png");
   private static final long FADE_OUT_DELAY = 5000L;
   private static final long FADE_OUT_TIME = 2000L;
   private final Minecraft minecraft;
   private long lastSelectionTime;
   private SpectatorMenu menu;

   public SpectatorGui(Minecraft var1) {
      this.minecraft = â˜ƒ;
   }

   public void onHotbarSelected(int var1) {
      this.lastSelectionTime = Util.getMillis();
      if (this.menu != null) {
         this.menu.selectSlot(â˜ƒ);
      } else {
         this.menu = new SpectatorMenu(this);
      }
   }

   private float getHotbarAlpha() {
      long â˜ƒ = this.lastSelectionTime - Util.getMillis() + 5000L;
      return Mth.clamp((float)â˜ƒ / 2000.0F, 0.0F, 1.0F);
   }

   public void renderHotbar(PoseStack var1, float var2) {
      if (this.menu != null) {
         float â˜ƒ = this.getHotbarAlpha();
         if (â˜ƒ <= 0.0F) {
            this.menu.exit();
         } else {
            int â˜ƒ = this.minecraft.getWindow().getGuiScaledWidth() / 2;
            int â˜ƒx = this.getBlitOffset();
            this.setBlitOffset(-90);
            int â˜ƒxx = Mth.floor((float)this.minecraft.getWindow().getGuiScaledHeight() - 22.0F * â˜ƒ);
            SpectatorPage â˜ƒxxx = this.menu.getCurrentPage();
            this.renderPage(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒxxx);
            this.setBlitOffset(â˜ƒx);
         }
      }
   }

   protected void renderPage(PoseStack var1, float var2, int var3, int var4, SpectatorPage var5) {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, â˜ƒ);
      RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
      this.blit(â˜ƒ, â˜ƒ - 91, â˜ƒ, 0, 0, 182, 22);
      if (â˜ƒ.getSelectedSlot() >= 0) {
         this.blit(â˜ƒ, â˜ƒ - 91 - 1 + â˜ƒ.getSelectedSlot() * 20, â˜ƒ - 1, 0, 22, 24, 22);
      }

      for(int â˜ƒ = 0; â˜ƒ < 9; ++â˜ƒ) {
         this.renderSlot(â˜ƒ, â˜ƒ, this.minecraft.getWindow().getGuiScaledWidth() / 2 - 90 + â˜ƒ * 20 + 2, (float)(â˜ƒ + 3), â˜ƒ, â˜ƒ.getItem(â˜ƒ));
      }

      RenderSystem.disableBlend();
   }

   private void renderSlot(PoseStack var1, int var2, int var3, float var4, float var5, SpectatorMenuItem var6) {
      RenderSystem.setShaderTexture(0, SPECTATOR_LOCATION);
      if (â˜ƒ != SpectatorMenu.EMPTY_SLOT) {
         int â˜ƒ = (int)(â˜ƒ * 255.0F);
         â˜ƒ.pushPose();
         â˜ƒ.translate((double)â˜ƒ, (double)â˜ƒ, 0.0);
         float â˜ƒx = â˜ƒ.isEnabled() ? 1.0F : 0.25F;
         RenderSystem.setShaderColor(â˜ƒx, â˜ƒx, â˜ƒx, â˜ƒ);
         â˜ƒ.renderIcon(â˜ƒ, â˜ƒx, â˜ƒ);
         â˜ƒ.popPose();
         if (â˜ƒ > 3 && â˜ƒ.isEnabled()) {
            Component â˜ƒxx = this.minecraft.options.keyHotbarSlots[â˜ƒ].getTranslatedKeyMessage();
            this.minecraft.font.drawShadow(â˜ƒ, â˜ƒxx, (float)(â˜ƒ + 19 - 2 - this.minecraft.font.width(â˜ƒxx)), â˜ƒ + 6.0F + 3.0F, 16777215 + (â˜ƒ << 24));
         }
      }
   }

   public void renderTooltip(PoseStack var1) {
      int â˜ƒ = (int)(this.getHotbarAlpha() * 255.0F);
      if (â˜ƒ > 3 && this.menu != null) {
         SpectatorMenuItem â˜ƒx = this.menu.getSelectedItem();
         Component â˜ƒxx = â˜ƒx == SpectatorMenu.EMPTY_SLOT ? this.menu.getSelectedCategory().getPrompt() : â˜ƒx.getName();
         if (â˜ƒxx != null) {
            int â˜ƒxxx = (this.minecraft.getWindow().getGuiScaledWidth() - this.minecraft.font.width(â˜ƒxx)) / 2;
            int â˜ƒxxxx = this.minecraft.getWindow().getGuiScaledHeight() - 35;
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            this.minecraft.font.drawShadow(â˜ƒ, â˜ƒxx, (float)â˜ƒxxx, (float)â˜ƒxxxx, 16777215 + (â˜ƒ << 24));
            RenderSystem.disableBlend();
         }
      }
   }

   @Override
   public void onSpectatorMenuClosed(SpectatorMenu var1) {
      this.menu = null;
      this.lastSelectionTime = 0L;
   }

   public boolean isMenuActive() {
      return this.menu != null;
   }

   public void onMouseScrolled(double var1) {
      int â˜ƒ = this.menu.getSelectedSlot() + (int)â˜ƒ;

      while(â˜ƒ >= 0 && â˜ƒ <= 8 && (this.menu.getItem(â˜ƒ) == SpectatorMenu.EMPTY_SLOT || !this.menu.getItem(â˜ƒ).isEnabled())) {
         â˜ƒ = (int)((double)â˜ƒ + â˜ƒ);
      }

      if (â˜ƒ >= 0 && â˜ƒ <= 8) {
         this.menu.selectSlot(â˜ƒ);
         this.lastSelectionTime = Util.getMillis();
      }
   }

   public void onMouseMiddleClick() {
      this.lastSelectionTime = Util.getMillis();
      if (this.isMenuActive()) {
         int â˜ƒ = this.menu.getSelectedSlot();
         if (â˜ƒ != -1) {
            this.menu.selectSlot(â˜ƒ);
         }
      } else {
         this.menu = new SpectatorMenu(this);
      }
   }
}
