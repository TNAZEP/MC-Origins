package net.minecraft.client.gui.screens.advancements;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class AdvancementTab extends GuiComponent {
   private final Minecraft minecraft;
   private final AdvancementsScreen screen;
   private final AdvancementTabType type;
   private final int index;
   private final Advancement advancement;
   private final DisplayInfo display;
   private final ItemStack icon;
   private final Component title;
   private final AdvancementWidget root;
   private final Map<Advancement, AdvancementWidget> widgets = Maps.<Advancement, AdvancementWidget>newLinkedHashMap();
   private double scrollX;
   private double scrollY;
   private int minX = Integer.MAX_VALUE;
   private int minY = Integer.MAX_VALUE;
   private int maxX = Integer.MIN_VALUE;
   private int maxY = Integer.MIN_VALUE;
   private float fade;
   private boolean centered;

   public AdvancementTab(Minecraft var1, AdvancementsScreen var2, AdvancementTabType var3, int var4, Advancement var5, DisplayInfo var6) {
      this.minecraft = â˜ƒ;
      this.screen = â˜ƒ;
      this.type = â˜ƒ;
      this.index = â˜ƒ;
      this.advancement = â˜ƒ;
      this.display = â˜ƒ;
      this.icon = â˜ƒ.getIcon();
      this.title = â˜ƒ.getTitle();
      this.root = new AdvancementWidget(this, â˜ƒ, â˜ƒ, â˜ƒ);
      this.addWidget(this.root, â˜ƒ);
   }

   public AdvancementTabType getType() {
      return this.type;
   }

   public int getIndex() {
      return this.index;
   }

   public Advancement getAdvancement() {
      return this.advancement;
   }

   public Component getTitle() {
      return this.title;
   }

   public DisplayInfo getDisplay() {
      return this.display;
   }

   public void drawTab(PoseStack var1, int var2, int var3, boolean var4) {
      this.type.draw(â˜ƒ, this, â˜ƒ, â˜ƒ, â˜ƒ, this.index);
   }

   public void drawIcon(int var1, int var2, ItemRenderer var3) {
      this.type.drawIcon(â˜ƒ, â˜ƒ, this.index, â˜ƒ, this.icon);
   }

   public void drawContents(PoseStack var1) {
      if (!this.centered) {
         this.scrollX = (double)(117 - (this.maxX + this.minX) / 2);
         this.scrollY = (double)(56 - (this.maxY + this.minY) / 2);
         this.centered = true;
      }

      â˜ƒ.pushPose();
      â˜ƒ.translate(0.0, 0.0, 950.0);
      RenderSystem.enableDepthTest();
      RenderSystem.colorMask(false, false, false, false);
      fill(â˜ƒ, 4680, 2260, -4680, -2260, -16777216);
      RenderSystem.colorMask(true, true, true, true);
      â˜ƒ.translate(0.0, 0.0, -950.0);
      RenderSystem.depthFunc(518);
      fill(â˜ƒ, 234, 113, 0, 0, -16777216);
      RenderSystem.depthFunc(515);
      ResourceLocation â˜ƒ = this.display.getBackground();
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      if (â˜ƒ != null) {
         RenderSystem.setShaderTexture(0, â˜ƒ);
      } else {
         RenderSystem.setShaderTexture(0, TextureManager.INTENTIONAL_MISSING_TEXTURE);
      }

      int â˜ƒ = Mth.floor(this.scrollX);
      int â˜ƒx = Mth.floor(this.scrollY);
      int â˜ƒxx = â˜ƒ % 16;
      int â˜ƒxxx = â˜ƒx % 16;

      for(int â˜ƒxxxx = -1; â˜ƒxxxx <= 15; ++â˜ƒxxxx) {
         for(int â˜ƒxxxxx = -1; â˜ƒxxxxx <= 8; ++â˜ƒxxxxx) {
            blit(â˜ƒ, â˜ƒxx + 16 * â˜ƒxxxx, â˜ƒxxx + 16 * â˜ƒxxxxx, 0.0F, 0.0F, 16, 16, 16, 16);
         }
      }

      this.root.drawConnectivity(â˜ƒ, â˜ƒ, â˜ƒx, true);
      this.root.drawConnectivity(â˜ƒ, â˜ƒ, â˜ƒx, false);
      this.root.draw(â˜ƒ, â˜ƒ, â˜ƒx);
      RenderSystem.depthFunc(518);
      â˜ƒ.translate(0.0, 0.0, -950.0);
      RenderSystem.colorMask(false, false, false, false);
      fill(â˜ƒ, 4680, 2260, -4680, -2260, -16777216);
      RenderSystem.colorMask(true, true, true, true);
      RenderSystem.depthFunc(515);
      â˜ƒ.popPose();
   }

   public void drawTooltips(PoseStack var1, int var2, int var3, int var4, int var5) {
      â˜ƒ.pushPose();
      â˜ƒ.translate(0.0, 0.0, -200.0);
      fill(â˜ƒ, 0, 0, 234, 113, Mth.floor(this.fade * 255.0F) << 24);
      boolean â˜ƒ = false;
      int â˜ƒx = Mth.floor(this.scrollX);
      int â˜ƒxx = Mth.floor(this.scrollY);
      if (â˜ƒ > 0 && â˜ƒ < 234 && â˜ƒ > 0 && â˜ƒ < 113) {
         for(AdvancementWidget â˜ƒxxx : this.widgets.values()) {
            if (â˜ƒxxx.isMouseOver(â˜ƒx, â˜ƒxx, â˜ƒ, â˜ƒ)) {
               â˜ƒ = true;
               â˜ƒxxx.drawHover(â˜ƒ, â˜ƒx, â˜ƒxx, this.fade, â˜ƒ, â˜ƒ);
               break;
            }
         }
      }

      â˜ƒ.popPose();
      if (â˜ƒ) {
         this.fade = Mth.clamp(this.fade + 0.02F, 0.0F, 0.3F);
      } else {
         this.fade = Mth.clamp(this.fade - 0.04F, 0.0F, 1.0F);
      }
   }

   public boolean isMouseOver(int var1, int var2, double var3, double var5) {
      return this.type.isMouseOver(â˜ƒ, â˜ƒ, this.index, â˜ƒ, â˜ƒ);
   }

   @Nullable
   public static AdvancementTab create(Minecraft var0, AdvancementsScreen var1, int var2, Advancement var3) {
      if (â˜ƒ.getDisplay() == null) {
         return null;
      } else {
         for(AdvancementTabType â˜ƒ : AdvancementTabType.values()) {
            if (â˜ƒ < â˜ƒ.getMax()) {
               return new AdvancementTab(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getDisplay());
            }

            â˜ƒ -= â˜ƒ.getMax();
         }

         return null;
      }
   }

   public void scroll(double var1, double var3) {
      if (this.maxX - this.minX > 234) {
         this.scrollX = Mth.clamp(this.scrollX + â˜ƒ, (double)(-(this.maxX - 234)), 0.0);
      }

      if (this.maxY - this.minY > 113) {
         this.scrollY = Mth.clamp(this.scrollY + â˜ƒ, (double)(-(this.maxY - 113)), 0.0);
      }
   }

   public void addAdvancement(Advancement var1) {
      if (â˜ƒ.getDisplay() != null) {
         AdvancementWidget â˜ƒ = new AdvancementWidget(this, this.minecraft, â˜ƒ, â˜ƒ.getDisplay());
         this.addWidget(â˜ƒ, â˜ƒ);
      }
   }

   private void addWidget(AdvancementWidget var1, Advancement var2) {
      this.widgets.put(â˜ƒ, â˜ƒ);
      int â˜ƒ = â˜ƒ.getX();
      int â˜ƒx = â˜ƒ + 28;
      int â˜ƒxx = â˜ƒ.getY();
      int â˜ƒxxx = â˜ƒxx + 27;
      this.minX = Math.min(this.minX, â˜ƒ);
      this.maxX = Math.max(this.maxX, â˜ƒx);
      this.minY = Math.min(this.minY, â˜ƒxx);
      this.maxY = Math.max(this.maxY, â˜ƒxxx);

      for(AdvancementWidget â˜ƒxxxx : this.widgets.values()) {
         â˜ƒxxxx.attachToParent();
      }
   }

   @Nullable
   public AdvancementWidget getWidget(Advancement var1) {
      return (AdvancementWidget)this.widgets.get(â˜ƒ);
   }

   public AdvancementsScreen getScreen() {
      return this.screen;
   }
}
