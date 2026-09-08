package net.minecraft.client.gui.components;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;

public abstract class AbstractSelectionList<E extends AbstractSelectionList.Entry<E>> extends AbstractContainerEventHandler implements Widget, NarratableEntry {
   protected final Minecraft minecraft;
   protected final int itemHeight;
   private final List<E> children = new AbstractSelectionList.TrackedList();
   protected int width;
   protected int height;
   protected int y0;
   protected int y1;
   protected int x1;
   protected int x0;
   protected boolean centerListVertically = true;
   private double scrollAmount;
   private boolean renderSelection = true;
   private boolean renderHeader;
   protected int headerHeight;
   private boolean scrolling;
   @Nullable
   private E selected;
   private boolean renderBackground = true;
   private boolean renderTopAndBottom = true;
   @Nullable
   private E hovered;

   public AbstractSelectionList(Minecraft var1, int var2, int var3, int var4, int var5, int var6) {
      this.minecraft = â˜ƒ;
      this.width = â˜ƒ;
      this.height = â˜ƒ;
      this.y0 = â˜ƒ;
      this.y1 = â˜ƒ;
      this.itemHeight = â˜ƒ;
      this.x0 = 0;
      this.x1 = â˜ƒ;
   }

   public void setRenderSelection(boolean var1) {
      this.renderSelection = â˜ƒ;
   }

   protected void setRenderHeader(boolean var1, int var2) {
      this.renderHeader = â˜ƒ;
      this.headerHeight = â˜ƒ;
      if (!â˜ƒ) {
         this.headerHeight = 0;
      }
   }

   public int getRowWidth() {
      return 220;
   }

   @Nullable
   public E getSelected() {
      return this.selected;
   }

   public void setSelected(@Nullable E var1) {
      this.selected = â˜ƒ;
   }

   public void setRenderBackground(boolean var1) {
      this.renderBackground = â˜ƒ;
   }

   public void setRenderTopAndBottom(boolean var1) {
      this.renderTopAndBottom = â˜ƒ;
   }

   @Nullable
   public E getFocused() {
      return (E)super.getFocused();
   }

   @Override
   public final List<E> children() {
      return this.children;
   }

   protected final void clearEntries() {
      this.children.clear();
   }

   protected void replaceEntries(Collection<E> var1) {
      this.children.clear();
      this.children.addAll(â˜ƒ);
   }

   protected E getEntry(int var1) {
      return (E)this.children().get(â˜ƒ);
   }

   protected int addEntry(E var1) {
      this.children.add(â˜ƒ);
      return this.children.size() - 1;
   }

   protected int getItemCount() {
      return this.children().size();
   }

   protected boolean isSelectedItem(int var1) {
      return Objects.equals(this.getSelected(), this.children().get(â˜ƒ));
   }

   @Nullable
   protected final E getEntryAtPosition(double var1, double var3) {
      int â˜ƒ = this.getRowWidth() / 2;
      int â˜ƒx = this.x0 + this.width / 2;
      int â˜ƒxx = â˜ƒx - â˜ƒ;
      int â˜ƒxxx = â˜ƒx + â˜ƒ;
      int â˜ƒxxxx = Mth.floor(â˜ƒ - (double)this.y0) - this.headerHeight + (int)this.getScrollAmount() - 4;
      int â˜ƒxxxxx = â˜ƒxxxx / this.itemHeight;
      return (E)(â˜ƒ < (double)this.getScrollbarPosition()
            && â˜ƒ >= (double)â˜ƒxx
            && â˜ƒ <= (double)â˜ƒxxx
            && â˜ƒxxxxx >= 0
            && â˜ƒxxxx >= 0
            && â˜ƒxxxxx < this.getItemCount()
         ? this.children().get(â˜ƒxxxxx)
         : null);
   }

   public void updateSize(int var1, int var2, int var3, int var4) {
      this.width = â˜ƒ;
      this.height = â˜ƒ;
      this.y0 = â˜ƒ;
      this.y1 = â˜ƒ;
      this.x0 = 0;
      this.x1 = â˜ƒ;
   }

   public void setLeftPos(int var1) {
      this.x0 = â˜ƒ;
      this.x1 = â˜ƒ + this.width;
   }

   protected int getMaxPosition() {
      return this.getItemCount() * this.itemHeight + this.headerHeight;
   }

   protected void clickedHeader(int var1, int var2) {
   }

   protected void renderHeader(PoseStack var1, int var2, int var3, Tesselator var4) {
   }

   protected void renderBackground(PoseStack var1) {
   }

   protected void renderDecorations(PoseStack var1, int var2, int var3) {
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      int â˜ƒ = this.getScrollbarPosition();
      int â˜ƒx = â˜ƒ + 6;
      Tesselator â˜ƒxx = Tesselator.getInstance();
      BufferBuilder â˜ƒxxx = â˜ƒxx.getBuilder();
      RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
      this.hovered = this.isMouseOver((double)â˜ƒ, (double)â˜ƒ) ? this.getEntryAtPosition((double)â˜ƒ, (double)â˜ƒ) : null;
      if (this.renderBackground) {
         RenderSystem.setShaderTexture(0, GuiComponent.BACKGROUND_LOCATION);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         float â˜ƒxxxx = 32.0F;
         â˜ƒxxx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
         â˜ƒxxx.vertex((double)this.x0, (double)this.y1, 0.0)
            .uv((float)this.x0 / 32.0F, (float)(this.y1 + (int)this.getScrollAmount()) / 32.0F)
            .color(32, 32, 32, 255)
            .endVertex();
         â˜ƒxxx.vertex((double)this.x1, (double)this.y1, 0.0)
            .uv((float)this.x1 / 32.0F, (float)(this.y1 + (int)this.getScrollAmount()) / 32.0F)
            .color(32, 32, 32, 255)
            .endVertex();
         â˜ƒxxx.vertex((double)this.x1, (double)this.y0, 0.0)
            .uv((float)this.x1 / 32.0F, (float)(this.y0 + (int)this.getScrollAmount()) / 32.0F)
            .color(32, 32, 32, 255)
            .endVertex();
         â˜ƒxxx.vertex((double)this.x0, (double)this.y0, 0.0)
            .uv((float)this.x0 / 32.0F, (float)(this.y0 + (int)this.getScrollAmount()) / 32.0F)
            .color(32, 32, 32, 255)
            .endVertex();
         â˜ƒxx.end();
      }

      int â˜ƒ = this.getRowLeft();
      int â˜ƒx = this.y0 + 4 - (int)this.getScrollAmount();
      if (this.renderHeader) {
         this.renderHeader(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx);
      }

      this.renderList(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒ);
      if (this.renderTopAndBottom) {
         RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
         RenderSystem.setShaderTexture(0, GuiComponent.BACKGROUND_LOCATION);
         RenderSystem.enableDepthTest();
         RenderSystem.depthFunc(519);
         float â˜ƒ = 32.0F;
         int â˜ƒx = -100;
         â˜ƒxxx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
         â˜ƒxxx.vertex((double)this.x0, (double)this.y0, -100.0).uv(0.0F, (float)this.y0 / 32.0F).color(64, 64, 64, 255).endVertex();
         â˜ƒxxx.vertex((double)(this.x0 + this.width), (double)this.y0, -100.0)
            .uv((float)this.width / 32.0F, (float)this.y0 / 32.0F)
            .color(64, 64, 64, 255)
            .endVertex();
         â˜ƒxxx.vertex((double)(this.x0 + this.width), 0.0, -100.0).uv((float)this.width / 32.0F, 0.0F).color(64, 64, 64, 255).endVertex();
         â˜ƒxxx.vertex((double)this.x0, 0.0, -100.0).uv(0.0F, 0.0F).color(64, 64, 64, 255).endVertex();
         â˜ƒxxx.vertex((double)this.x0, (double)this.height, -100.0).uv(0.0F, (float)this.height / 32.0F).color(64, 64, 64, 255).endVertex();
         â˜ƒxxx.vertex((double)(this.x0 + this.width), (double)this.height, -100.0)
            .uv((float)this.width / 32.0F, (float)this.height / 32.0F)
            .color(64, 64, 64, 255)
            .endVertex();
         â˜ƒxxx.vertex((double)(this.x0 + this.width), (double)this.y1, -100.0)
            .uv((float)this.width / 32.0F, (float)this.y1 / 32.0F)
            .color(64, 64, 64, 255)
            .endVertex();
         â˜ƒxxx.vertex((double)this.x0, (double)this.y1, -100.0).uv(0.0F, (float)this.y1 / 32.0F).color(64, 64, 64, 255).endVertex();
         â˜ƒxx.end();
         RenderSystem.depthFunc(515);
         RenderSystem.disableDepthTest();
         RenderSystem.enableBlend();
         RenderSystem.blendFuncSeparate(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ZERO,
            GlStateManager.DestFactor.ONE
         );
         RenderSystem.disableTexture();
         RenderSystem.setShader(GameRenderer::getPositionColorShader);
         int â˜ƒxx = 4;
         â˜ƒxxx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         â˜ƒxxx.vertex((double)this.x0, (double)(this.y0 + 4), 0.0).color(0, 0, 0, 0).endVertex();
         â˜ƒxxx.vertex((double)this.x1, (double)(this.y0 + 4), 0.0).color(0, 0, 0, 0).endVertex();
         â˜ƒxxx.vertex((double)this.x1, (double)this.y0, 0.0).color(0, 0, 0, 255).endVertex();
         â˜ƒxxx.vertex((double)this.x0, (double)this.y0, 0.0).color(0, 0, 0, 255).endVertex();
         â˜ƒxxx.vertex((double)this.x0, (double)this.y1, 0.0).color(0, 0, 0, 255).endVertex();
         â˜ƒxxx.vertex((double)this.x1, (double)this.y1, 0.0).color(0, 0, 0, 255).endVertex();
         â˜ƒxxx.vertex((double)this.x1, (double)(this.y1 - 4), 0.0).color(0, 0, 0, 0).endVertex();
         â˜ƒxxx.vertex((double)this.x0, (double)(this.y1 - 4), 0.0).color(0, 0, 0, 0).endVertex();
         â˜ƒxx.end();
      }

      int â˜ƒ = this.getMaxScroll();
      if (â˜ƒ > 0) {
         RenderSystem.disableTexture();
         RenderSystem.setShader(GameRenderer::getPositionColorShader);
         int â˜ƒx = (int)((float)((this.y1 - this.y0) * (this.y1 - this.y0)) / (float)this.getMaxPosition());
         â˜ƒx = Mth.clamp(â˜ƒx, 32, this.y1 - this.y0 - 8);
         int â˜ƒxx = (int)this.getScrollAmount() * (this.y1 - this.y0 - â˜ƒx) / â˜ƒ + this.y0;
         if (â˜ƒxx < this.y0) {
            â˜ƒxx = this.y0;
         }

         â˜ƒxxx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         â˜ƒxxx.vertex((double)â˜ƒ, (double)this.y1, 0.0).color(0, 0, 0, 255).endVertex();
         â˜ƒxxx.vertex((double)â˜ƒx, (double)this.y1, 0.0).color(0, 0, 0, 255).endVertex();
         â˜ƒxxx.vertex((double)â˜ƒx, (double)this.y0, 0.0).color(0, 0, 0, 255).endVertex();
         â˜ƒxxx.vertex((double)â˜ƒ, (double)this.y0, 0.0).color(0, 0, 0, 255).endVertex();
         â˜ƒxxx.vertex((double)â˜ƒ, (double)(â˜ƒxx + â˜ƒx), 0.0).color(128, 128, 128, 255).endVertex();
         â˜ƒxxx.vertex((double)â˜ƒx, (double)(â˜ƒxx + â˜ƒx), 0.0).color(128, 128, 128, 255).endVertex();
         â˜ƒxxx.vertex((double)â˜ƒx, (double)â˜ƒxx, 0.0).color(128, 128, 128, 255).endVertex();
         â˜ƒxxx.vertex((double)â˜ƒ, (double)â˜ƒxx, 0.0).color(128, 128, 128, 255).endVertex();
         â˜ƒxxx.vertex((double)â˜ƒ, (double)(â˜ƒxx + â˜ƒx - 1), 0.0).color(192, 192, 192, 255).endVertex();
         â˜ƒxxx.vertex((double)(â˜ƒx - 1), (double)(â˜ƒxx + â˜ƒx - 1), 0.0).color(192, 192, 192, 255).endVertex();
         â˜ƒxxx.vertex((double)(â˜ƒx - 1), (double)â˜ƒxx, 0.0).color(192, 192, 192, 255).endVertex();
         â˜ƒxxx.vertex((double)â˜ƒ, (double)â˜ƒxx, 0.0).color(192, 192, 192, 255).endVertex();
         â˜ƒxx.end();
      }

      this.renderDecorations(â˜ƒ, â˜ƒ, â˜ƒ);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
   }

   protected void centerScrollOn(E var1) {
      this.setScrollAmount((double)(this.children().indexOf(â˜ƒ) * this.itemHeight + this.itemHeight / 2 - (this.y1 - this.y0) / 2));
   }

   protected void ensureVisible(E var1) {
      int â˜ƒ = this.getRowTop(this.children().indexOf(â˜ƒ));
      int â˜ƒx = â˜ƒ - this.y0 - 4 - this.itemHeight;
      if (â˜ƒx < 0) {
         this.scroll(â˜ƒx);
      }

      int â˜ƒ = this.y1 - â˜ƒ - this.itemHeight - this.itemHeight;
      if (â˜ƒ < 0) {
         this.scroll(-â˜ƒ);
      }
   }

   private void scroll(int var1) {
      this.setScrollAmount(this.getScrollAmount() + (double)â˜ƒ);
   }

   public double getScrollAmount() {
      return this.scrollAmount;
   }

   public void setScrollAmount(double var1) {
      this.scrollAmount = Mth.clamp(â˜ƒ, 0.0, (double)this.getMaxScroll());
   }

   public int getMaxScroll() {
      return Math.max(0, this.getMaxPosition() - (this.y1 - this.y0 - 4));
   }

   public int getScrollBottom() {
      return (int)this.getScrollAmount() - this.height - this.headerHeight;
   }

   protected void updateScrollingState(double var1, double var3, int var5) {
      this.scrolling = â˜ƒ == 0 && â˜ƒ >= (double)this.getScrollbarPosition() && â˜ƒ < (double)(this.getScrollbarPosition() + 6);
   }

   protected int getScrollbarPosition() {
      return this.width / 2 + 124;
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      this.updateScrollingState(â˜ƒ, â˜ƒ, â˜ƒ);
      if (!this.isMouseOver(â˜ƒ, â˜ƒ)) {
         return false;
      } else {
         E â˜ƒ = this.getEntryAtPosition(â˜ƒ, â˜ƒ);
         if (â˜ƒ != null) {
            if (â˜ƒ.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ)) {
               this.setFocused(â˜ƒ);
               this.setDragging(true);
               return true;
            }
         } else if (â˜ƒ == 0) {
            this.clickedHeader(
               (int)(â˜ƒ - (double)(this.x0 + this.width / 2 - this.getRowWidth() / 2)), (int)(â˜ƒ - (double)this.y0) + (int)this.getScrollAmount() - 4
            );
            return true;
         }

         return this.scrolling;
      }
   }

   @Override
   public boolean mouseReleased(double var1, double var3, int var5) {
      if (this.getFocused() != null) {
         this.getFocused().mouseReleased(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      return false;
   }

   @Override
   public boolean mouseDragged(double var1, double var3, int var5, double var6, double var8) {
      if (super.mouseDragged(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
         return true;
      } else if (â˜ƒ == 0 && this.scrolling) {
         if (â˜ƒ < (double)this.y0) {
            this.setScrollAmount(0.0);
         } else if (â˜ƒ > (double)this.y1) {
            this.setScrollAmount((double)this.getMaxScroll());
         } else {
            double â˜ƒ = (double)Math.max(1, this.getMaxScroll());
            int â˜ƒx = this.y1 - this.y0;
            int â˜ƒxx = Mth.clamp((int)((float)(â˜ƒx * â˜ƒx) / (float)this.getMaxPosition()), 32, â˜ƒx - 8);
            double â˜ƒxxx = Math.max(1.0, â˜ƒ / (double)(â˜ƒx - â˜ƒxx));
            this.setScrollAmount(this.getScrollAmount() + â˜ƒ * â˜ƒxxx);
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean mouseScrolled(double var1, double var3, double var5) {
      this.setScrollAmount(this.getScrollAmount() - â˜ƒ * (double)this.itemHeight / 2.0);
      return true;
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ)) {
         return true;
      } else if (â˜ƒ == 264) {
         this.moveSelection(AbstractSelectionList.SelectionDirection.DOWN);
         return true;
      } else if (â˜ƒ == 265) {
         this.moveSelection(AbstractSelectionList.SelectionDirection.UP);
         return true;
      } else {
         return false;
      }
   }

   protected void moveSelection(AbstractSelectionList.SelectionDirection var1) {
      this.moveSelection(â˜ƒ, var0 -> true);
   }

   protected void refreshSelection() {
      E â˜ƒ = this.getSelected();
      if (â˜ƒ != null) {
         this.setSelected(â˜ƒ);
         this.ensureVisible(â˜ƒ);
      }
   }

   protected void moveSelection(AbstractSelectionList.SelectionDirection var1, Predicate<E> var2) {
      int â˜ƒ = â˜ƒ == AbstractSelectionList.SelectionDirection.UP ? -1 : 1;
      if (!this.children().isEmpty()) {
         int â˜ƒx = this.children().indexOf(this.getSelected());

         while(true) {
            int â˜ƒxx = Mth.clamp(â˜ƒx + â˜ƒ, 0, this.getItemCount() - 1);
            if (â˜ƒx == â˜ƒxx) {
               break;
            }

            E â˜ƒxx = (E)this.children().get(â˜ƒxx);
            if (â˜ƒ.test(â˜ƒxx)) {
               this.setSelected(â˜ƒxx);
               this.ensureVisible(â˜ƒxx);
               break;
            }

            â˜ƒx = â˜ƒxx;
         }
      }
   }

   @Override
   public boolean isMouseOver(double var1, double var3) {
      return â˜ƒ >= (double)this.y0 && â˜ƒ <= (double)this.y1 && â˜ƒ >= (double)this.x0 && â˜ƒ <= (double)this.x1;
   }

   protected void renderList(PoseStack var1, int var2, int var3, int var4, int var5, float var6) {
      int â˜ƒ = this.getItemCount();
      Tesselator â˜ƒx = Tesselator.getInstance();
      BufferBuilder â˜ƒxx = â˜ƒx.getBuilder();

      for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒ; ++â˜ƒxxx) {
         int â˜ƒxxxx = this.getRowTop(â˜ƒxxx);
         int â˜ƒxxxxx = this.getRowBottom(â˜ƒxxx);
         if (â˜ƒxxxxx >= this.y0 && â˜ƒxxxx <= this.y1) {
            int â˜ƒxxxxxx = â˜ƒ + â˜ƒxxx * this.itemHeight + this.headerHeight;
            int â˜ƒxxxxxxx = this.itemHeight - 4;
            E â˜ƒxxxxxxxx = this.getEntry(â˜ƒxxx);
            int â˜ƒxxxxxxxxx = this.getRowWidth();
            if (this.renderSelection && this.isSelectedItem(â˜ƒxxx)) {
               int â˜ƒxxxxxxxxxx = this.x0 + this.width / 2 - â˜ƒxxxxxxxxx / 2;
               int â˜ƒxxxxxxxxxxx = this.x0 + this.width / 2 + â˜ƒxxxxxxxxx / 2;
               RenderSystem.disableTexture();
               RenderSystem.setShader(GameRenderer::getPositionShader);
               float â˜ƒxxxxxxxxxxxx = this.isFocused() ? 1.0F : 0.5F;
               RenderSystem.setShaderColor(â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx, 1.0F);
               â˜ƒxx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
               â˜ƒxx.vertex((double)â˜ƒxxxxxxxxxx, (double)(â˜ƒxxxxxx + â˜ƒxxxxxxx + 2), 0.0).endVertex();
               â˜ƒxx.vertex((double)â˜ƒxxxxxxxxxxx, (double)(â˜ƒxxxxxx + â˜ƒxxxxxxx + 2), 0.0).endVertex();
               â˜ƒxx.vertex((double)â˜ƒxxxxxxxxxxx, (double)(â˜ƒxxxxxx - 2), 0.0).endVertex();
               â˜ƒxx.vertex((double)â˜ƒxxxxxxxxxx, (double)(â˜ƒxxxxxx - 2), 0.0).endVertex();
               â˜ƒx.end();
               RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
               â˜ƒxx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
               â˜ƒxx.vertex((double)(â˜ƒxxxxxxxxxx + 1), (double)(â˜ƒxxxxxx + â˜ƒxxxxxxx + 1), 0.0).endVertex();
               â˜ƒxx.vertex((double)(â˜ƒxxxxxxxxxxx - 1), (double)(â˜ƒxxxxxx + â˜ƒxxxxxxx + 1), 0.0).endVertex();
               â˜ƒxx.vertex((double)(â˜ƒxxxxxxxxxxx - 1), (double)(â˜ƒxxxxxx - 1), 0.0).endVertex();
               â˜ƒxx.vertex((double)(â˜ƒxxxxxxxxxx + 1), (double)(â˜ƒxxxxxx - 1), 0.0).endVertex();
               â˜ƒx.end();
               RenderSystem.enableTexture();
            }

            int â˜ƒxxxxxx = this.getRowLeft();
            â˜ƒxxxxxxxx.render(â˜ƒ, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxx, â˜ƒ, â˜ƒ, Objects.equals(this.hovered, â˜ƒxxxxxxxx), â˜ƒ);
         }
      }
   }

   public int getRowLeft() {
      return this.x0 + this.width / 2 - this.getRowWidth() / 2 + 2;
   }

   public int getRowRight() {
      return this.getRowLeft() + this.getRowWidth();
   }

   protected int getRowTop(int var1) {
      return this.y0 + 4 - (int)this.getScrollAmount() + â˜ƒ * this.itemHeight + this.headerHeight;
   }

   private int getRowBottom(int var1) {
      return this.getRowTop(â˜ƒ) + this.itemHeight;
   }

   protected boolean isFocused() {
      return false;
   }

   @Override
   public NarratableEntry.NarrationPriority narrationPriority() {
      if (this.isFocused()) {
         return NarratableEntry.NarrationPriority.FOCUSED;
      } else {
         return this.hovered != null ? NarratableEntry.NarrationPriority.HOVERED : NarratableEntry.NarrationPriority.NONE;
      }
   }

   @Nullable
   protected E remove(int var1) {
      E â˜ƒ = (E)this.children.get(â˜ƒ);
      return this.removeEntry((E)this.children.get(â˜ƒ)) ? â˜ƒ : null;
   }

   protected boolean removeEntry(E var1) {
      boolean â˜ƒ = this.children.remove(â˜ƒ);
      if (â˜ƒ && â˜ƒ == this.getSelected()) {
         this.setSelected((E)null);
      }

      return â˜ƒ;
   }

   @Nullable
   protected E getHovered() {
      return this.hovered;
   }

   void bindEntryToSelf(AbstractSelectionList.Entry<E> var1) {
      â˜ƒ.list = this;
   }

   protected void narrateListElementPosition(NarrationElementOutput var1, E var2) {
      List<E> â˜ƒ = this.children();
      if (â˜ƒ.size() > 1) {
         int â˜ƒx = â˜ƒ.indexOf(â˜ƒ);
         if (â˜ƒx != -1) {
            â˜ƒ.add(NarratedElementType.POSITION, new TranslatableComponent("narrator.position.list", â˜ƒx + 1, â˜ƒ.size()));
         }
      }
   }

   public abstract static class Entry<E extends AbstractSelectionList.Entry<E>> implements GuiEventListener {
      @Deprecated
      AbstractSelectionList<E> list;

      public abstract void render(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10);

      @Override
      public boolean isMouseOver(double var1, double var3) {
         return Objects.equals(this.list.getEntryAtPosition(â˜ƒ, â˜ƒ), this);
      }
   }

   protected static enum SelectionDirection {
      UP,
      DOWN;
   }

   class TrackedList extends AbstractList<E> {
      private final List<E> delegate = Lists.<E>newArrayList();

      public E get(int var1) {
         return (E)this.delegate.get(â˜ƒ);
      }

      public int size() {
         return this.delegate.size();
      }

      public E set(int var1, E var2) {
         E â˜ƒ = (E)this.delegate.set(â˜ƒ, â˜ƒ);
         AbstractSelectionList.this.bindEntryToSelf(â˜ƒ);
         return â˜ƒ;
      }

      public void add(int var1, E var2) {
         this.delegate.add(â˜ƒ, â˜ƒ);
         AbstractSelectionList.this.bindEntryToSelf(â˜ƒ);
      }

      public E remove(int var1) {
         return (E)this.delegate.remove(â˜ƒ);
      }
   }
}
