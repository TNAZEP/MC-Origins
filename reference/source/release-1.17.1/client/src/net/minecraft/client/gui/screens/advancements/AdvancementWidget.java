package net.minecraft.client.gui.screens.advancements;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

public class AdvancementWidget extends GuiComponent {
   private static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation("textures/gui/advancements/widgets.png");
   private static final int HEIGHT = 26;
   private static final int BOX_X = 0;
   private static final int BOX_WIDTH = 200;
   private static final int FRAME_WIDTH = 26;
   private static final int ICON_X = 8;
   private static final int ICON_Y = 5;
   private static final int ICON_WIDTH = 26;
   private static final int TITLE_PADDING_LEFT = 3;
   private static final int TITLE_PADDING_RIGHT = 5;
   private static final int TITLE_X = 32;
   private static final int TITLE_Y = 9;
   private static final int TITLE_MAX_WIDTH = 163;
   private static final int[] TEST_SPLIT_OFFSETS = new int[]{0, 10, -10, 25, -25};
   private final AdvancementTab tab;
   private final Advancement advancement;
   private final DisplayInfo display;
   private final FormattedCharSequence title;
   private final int width;
   private final List<FormattedCharSequence> description;
   private final Minecraft minecraft;
   private AdvancementWidget parent;
   private final List<AdvancementWidget> children = Lists.<AdvancementWidget>newArrayList();
   private AdvancementProgress progress;
   private final int x;
   private final int y;

   public AdvancementWidget(AdvancementTab var1, Minecraft var2, Advancement var3, DisplayInfo var4) {
      this.tab = â˜ƒ;
      this.advancement = â˜ƒ;
      this.display = â˜ƒ;
      this.minecraft = â˜ƒ;
      this.title = Language.getInstance().getVisualOrder(â˜ƒ.font.substrByWidth(â˜ƒ.getTitle(), 163));
      this.x = Mth.floor(â˜ƒ.getX() * 28.0F);
      this.y = Mth.floor(â˜ƒ.getY() * 27.0F);
      int â˜ƒ = â˜ƒ.getMaxCriteraRequired();
      int â˜ƒx = String.valueOf(â˜ƒ).length();
      int â˜ƒxx = â˜ƒ > 1 ? â˜ƒ.font.width("  ") + â˜ƒ.font.width("0") * â˜ƒx * 2 + â˜ƒ.font.width("/") : 0;
      int â˜ƒxxx = 29 + â˜ƒ.font.width(this.title) + â˜ƒxx;
      this.description = Language.getInstance()
         .getVisualOrder(
            this.findOptimalLines(ComponentUtils.mergeStyles(â˜ƒ.getDescription().copy(), Style.EMPTY.withColor(â˜ƒ.getFrame().getChatColor())), â˜ƒxxx)
         );

      for(FormattedCharSequence â˜ƒxxxx : this.description) {
         â˜ƒxxx = Math.max(â˜ƒxxx, â˜ƒ.font.width(â˜ƒxxxx));
      }

      this.width = â˜ƒxxx + 3 + 5;
   }

   private static float getMaxWidth(StringSplitter var0, List<FormattedText> var1) {
      return (float)â˜ƒ.stream().mapToDouble(â˜ƒ::stringWidth).max().orElse(0.0);
   }

   private List<FormattedText> findOptimalLines(Component var1, int var2) {
      StringSplitter â˜ƒ = this.minecraft.font.getSplitter();
      List<FormattedText> â˜ƒx = null;
      float â˜ƒxx = Float.MAX_VALUE;

      for(int â˜ƒxxx : TEST_SPLIT_OFFSETS) {
         List<FormattedText> â˜ƒxxxx = â˜ƒ.splitLines(â˜ƒ, â˜ƒ - â˜ƒxxx, Style.EMPTY);
         float â˜ƒxxxxx = Math.abs(getMaxWidth(â˜ƒ, â˜ƒxxxx) - (float)â˜ƒ);
         if (â˜ƒxxxxx <= 10.0F) {
            return â˜ƒxxxx;
         }

         if (â˜ƒxxxxx < â˜ƒxx) {
            â˜ƒxx = â˜ƒxxxxx;
            â˜ƒx = â˜ƒxxxx;
         }
      }

      return â˜ƒx;
   }

   @Nullable
   private AdvancementWidget getFirstVisibleParent(Advancement var1) {
      do {
         â˜ƒ = â˜ƒ.getParent();
      } while(â˜ƒ != null && â˜ƒ.getDisplay() == null);

      return â˜ƒ != null && â˜ƒ.getDisplay() != null ? this.tab.getWidget(â˜ƒ) : null;
   }

   public void drawConnectivity(PoseStack var1, int var2, int var3, boolean var4) {
      if (this.parent != null) {
         int â˜ƒ = â˜ƒ + this.parent.x + 13;
         int â˜ƒx = â˜ƒ + this.parent.x + 26 + 4;
         int â˜ƒxx = â˜ƒ + this.parent.y + 13;
         int â˜ƒxxx = â˜ƒ + this.x + 13;
         int â˜ƒxxxx = â˜ƒ + this.y + 13;
         int â˜ƒxxxxx = â˜ƒ ? -16777216 : -1;
         if (â˜ƒ) {
            this.hLine(â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒxx - 1, â˜ƒxxxxx);
            this.hLine(â˜ƒ, â˜ƒx + 1, â˜ƒ, â˜ƒxx, â˜ƒxxxxx);
            this.hLine(â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒxx + 1, â˜ƒxxxxx);
            this.hLine(â˜ƒ, â˜ƒxxx, â˜ƒx - 1, â˜ƒxxxx - 1, â˜ƒxxxxx);
            this.hLine(â˜ƒ, â˜ƒxxx, â˜ƒx - 1, â˜ƒxxxx, â˜ƒxxxxx);
            this.hLine(â˜ƒ, â˜ƒxxx, â˜ƒx - 1, â˜ƒxxxx + 1, â˜ƒxxxxx);
            this.vLine(â˜ƒ, â˜ƒx - 1, â˜ƒxxxx, â˜ƒxx, â˜ƒxxxxx);
            this.vLine(â˜ƒ, â˜ƒx + 1, â˜ƒxxxx, â˜ƒxx, â˜ƒxxxxx);
         } else {
            this.hLine(â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒxx, â˜ƒxxxxx);
            this.hLine(â˜ƒ, â˜ƒxxx, â˜ƒx, â˜ƒxxxx, â˜ƒxxxxx);
            this.vLine(â˜ƒ, â˜ƒx, â˜ƒxxxx, â˜ƒxx, â˜ƒxxxxx);
         }
      }

      for(AdvancementWidget â˜ƒ : this.children) {
         â˜ƒ.drawConnectivity(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public void draw(PoseStack var1, int var2, int var3) {
      if (!this.display.isHidden() || this.progress != null && this.progress.isDone()) {
         float â˜ƒx = this.progress == null ? 0.0F : this.progress.getPercent();
         AdvancementWidgetType â˜ƒ;
         if (â˜ƒx >= 1.0F) {
            â˜ƒ = AdvancementWidgetType.OBTAINED;
         } else {
            â˜ƒ = AdvancementWidgetType.UNOBTAINED;
         }

         RenderSystem.setShader(GameRenderer::getPositionTexShader);
         RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
         this.blit(â˜ƒ, â˜ƒ + this.x + 3, â˜ƒ + this.y, this.display.getFrame().getTexture(), 128 + â˜ƒ.getIndex() * 26, 26, 26);
         this.minecraft.getItemRenderer().renderAndDecorateFakeItem(this.display.getIcon(), â˜ƒ + this.x + 8, â˜ƒ + this.y + 5);
      }

      for(AdvancementWidget â˜ƒ : this.children) {
         â˜ƒ.draw(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public int getWidth() {
      return this.width;
   }

   public void setProgress(AdvancementProgress var1) {
      this.progress = â˜ƒ;
   }

   public void addChild(AdvancementWidget var1) {
      this.children.add(â˜ƒ);
   }

   public void drawHover(PoseStack var1, int var2, int var3, float var4, int var5, int var6) {
      boolean â˜ƒxxx = â˜ƒ + â˜ƒ + this.x + this.width + 26 >= this.tab.getScreen().width;
      String â˜ƒxxxx = this.progress == null ? null : this.progress.getProgressText();
      int â˜ƒxxxxx = â˜ƒxxxx == null ? 0 : this.minecraft.font.width(â˜ƒxxxx);
      boolean â˜ƒxxxxxx = 113 - â˜ƒ - this.y - 26 <= 6 + this.description.size() * 9;
      float â˜ƒxxxxxxx = this.progress == null ? 0.0F : this.progress.getPercent();
      int â˜ƒxxxxxxxx = Mth.floor(â˜ƒxxxxxxx * (float)this.width);
      AdvancementWidgetType â˜ƒ;
      AdvancementWidgetType â˜ƒx;
      AdvancementWidgetType â˜ƒxx;
      if (â˜ƒxxxxxxx >= 1.0F) {
         â˜ƒxxxxxxxx = this.width / 2;
         â˜ƒ = AdvancementWidgetType.OBTAINED;
         â˜ƒx = AdvancementWidgetType.OBTAINED;
         â˜ƒxx = AdvancementWidgetType.OBTAINED;
      } else if (â˜ƒxxxxxxxx < 2) {
         â˜ƒxxxxxxxx = this.width / 2;
         â˜ƒ = AdvancementWidgetType.UNOBTAINED;
         â˜ƒx = AdvancementWidgetType.UNOBTAINED;
         â˜ƒxx = AdvancementWidgetType.UNOBTAINED;
      } else if (â˜ƒxxxxxxxx > this.width - 2) {
         â˜ƒxxxxxxxx = this.width / 2;
         â˜ƒ = AdvancementWidgetType.OBTAINED;
         â˜ƒx = AdvancementWidgetType.OBTAINED;
         â˜ƒxx = AdvancementWidgetType.UNOBTAINED;
      } else {
         â˜ƒ = AdvancementWidgetType.OBTAINED;
         â˜ƒx = AdvancementWidgetType.UNOBTAINED;
         â˜ƒxx = AdvancementWidgetType.UNOBTAINED;
      }

      int â˜ƒx = this.width - â˜ƒxxxxxxxx;
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableBlend();
      int â˜ƒxx = â˜ƒ + this.y;
      int â˜ƒ;
      if (â˜ƒxxx) {
         â˜ƒ = â˜ƒ + this.x - this.width + 26 + 6;
      } else {
         â˜ƒ = â˜ƒ + this.x;
      }

      int â˜ƒ = 32 + this.description.size() * 9;
      if (!this.description.isEmpty()) {
         if (â˜ƒxxxxxx) {
            this.render9Sprite(â˜ƒ, â˜ƒ, â˜ƒxx + 26 - â˜ƒ, this.width, â˜ƒ, 10, 200, 26, 0, 52);
         } else {
            this.render9Sprite(â˜ƒ, â˜ƒ, â˜ƒxx, this.width, â˜ƒ, 10, 200, 26, 0, 52);
         }
      }

      this.blit(â˜ƒ, â˜ƒ, â˜ƒxx, 0, â˜ƒ.getIndex() * 26, â˜ƒxxxxxxxx, 26);
      this.blit(â˜ƒ, â˜ƒ + â˜ƒxxxxxxxx, â˜ƒxx, 200 - â˜ƒx, â˜ƒx.getIndex() * 26, â˜ƒx, 26);
      this.blit(â˜ƒ, â˜ƒ + this.x + 3, â˜ƒ + this.y, this.display.getFrame().getTexture(), 128 + â˜ƒxx.getIndex() * 26, 26, 26);
      if (â˜ƒxxx) {
         this.minecraft.font.drawShadow(â˜ƒ, this.title, (float)(â˜ƒ + 5), (float)(â˜ƒ + this.y + 9), -1);
         if (â˜ƒxxxx != null) {
            this.minecraft.font.drawShadow(â˜ƒ, â˜ƒxxxx, (float)(â˜ƒ + this.x - â˜ƒxxxxx), (float)(â˜ƒ + this.y + 9), -1);
         }
      } else {
         this.minecraft.font.drawShadow(â˜ƒ, this.title, (float)(â˜ƒ + this.x + 32), (float)(â˜ƒ + this.y + 9), -1);
         if (â˜ƒxxxx != null) {
            this.minecraft.font.drawShadow(â˜ƒ, â˜ƒxxxx, (float)(â˜ƒ + this.x + this.width - â˜ƒxxxxx - 5), (float)(â˜ƒ + this.y + 9), -1);
         }
      }

      if (â˜ƒxxxxxx) {
         for(int â˜ƒ = 0; â˜ƒ < this.description.size(); ++â˜ƒ) {
            this.minecraft
               .font
               .draw(â˜ƒ, (FormattedCharSequence)this.description.get(â˜ƒ), (float)(â˜ƒ + 5), (float)(â˜ƒxx + 26 - â˜ƒ + 7 + â˜ƒ * 9), -5592406);
         }
      } else {
         for(int â˜ƒ = 0; â˜ƒ < this.description.size(); ++â˜ƒ) {
            this.minecraft
               .font
               .draw(â˜ƒ, (FormattedCharSequence)this.description.get(â˜ƒ), (float)(â˜ƒ + 5), (float)(â˜ƒ + this.y + 9 + 17 + â˜ƒ * 9), -5592406);
         }
      }

      this.minecraft.getItemRenderer().renderAndDecorateFakeItem(this.display.getIcon(), â˜ƒ + this.x + 8, â˜ƒ + this.y + 5);
   }

   protected void render9Sprite(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10) {
      this.blit(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.renderRepeating(â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ, â˜ƒ - â˜ƒ - â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ, â˜ƒ - â˜ƒ - â˜ƒ, â˜ƒ);
      this.blit(â˜ƒ, â˜ƒ + â˜ƒ - â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒ - â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.blit(â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒ - â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒ - â˜ƒ, â˜ƒ, â˜ƒ);
      this.renderRepeating(â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ + â˜ƒ - â˜ƒ, â˜ƒ - â˜ƒ - â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ + â˜ƒ - â˜ƒ, â˜ƒ - â˜ƒ - â˜ƒ, â˜ƒ);
      this.blit(â˜ƒ, â˜ƒ + â˜ƒ - â˜ƒ, â˜ƒ + â˜ƒ - â˜ƒ, â˜ƒ + â˜ƒ - â˜ƒ, â˜ƒ + â˜ƒ - â˜ƒ, â˜ƒ, â˜ƒ);
      this.renderRepeating(â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ, â˜ƒ - â˜ƒ - â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ, â˜ƒ - â˜ƒ - â˜ƒ);
      this.renderRepeating(â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ - â˜ƒ - â˜ƒ, â˜ƒ - â˜ƒ - â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ - â˜ƒ - â˜ƒ, â˜ƒ - â˜ƒ - â˜ƒ);
      this.renderRepeating(â˜ƒ, â˜ƒ + â˜ƒ - â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ, â˜ƒ - â˜ƒ - â˜ƒ, â˜ƒ + â˜ƒ - â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ, â˜ƒ - â˜ƒ - â˜ƒ);
   }

   protected void renderRepeating(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ; â˜ƒ += â˜ƒ) {
         int â˜ƒx = â˜ƒ + â˜ƒ;
         int â˜ƒxx = Math.min(â˜ƒ, â˜ƒ - â˜ƒ);

         for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒ; â˜ƒxxx += â˜ƒ) {
            int â˜ƒxxxx = â˜ƒ + â˜ƒxxx;
            int â˜ƒxxxxx = Math.min(â˜ƒ, â˜ƒ - â˜ƒxxx);
            this.blit(â˜ƒ, â˜ƒx, â˜ƒxxxx, â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒxxxxx);
         }
      }
   }

   public boolean isMouseOver(int var1, int var2, int var3, int var4) {
      if (!this.display.isHidden() || this.progress != null && this.progress.isDone()) {
         int â˜ƒ = â˜ƒ + this.x;
         int â˜ƒx = â˜ƒ + 26;
         int â˜ƒxx = â˜ƒ + this.y;
         int â˜ƒxxx = â˜ƒxx + 26;
         return â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒx && â˜ƒ >= â˜ƒxx && â˜ƒ <= â˜ƒxxx;
      } else {
         return false;
      }
   }

   public void attachToParent() {
      if (this.parent == null && this.advancement.getParent() != null) {
         this.parent = this.getFirstVisibleParent(this.advancement);
         if (this.parent != null) {
            this.parent.addChild(this);
         }
      }
   }

   public int getY() {
      return this.y;
   }

   public int getX() {
      return this.x;
   }
}
