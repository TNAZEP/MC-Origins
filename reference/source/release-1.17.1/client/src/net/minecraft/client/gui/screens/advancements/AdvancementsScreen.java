package net.minecraft.client.gui.screens.advancements;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ServerboundSeenAdvancementsPacket;
import net.minecraft.resources.ResourceLocation;

public class AdvancementsScreen extends Screen implements ClientAdvancements.Listener {
   private static final ResourceLocation WINDOW_LOCATION = new ResourceLocation("textures/gui/advancements/window.png");
   private static final ResourceLocation TABS_LOCATION = new ResourceLocation("textures/gui/advancements/tabs.png");
   public static final int WINDOW_WIDTH = 252;
   public static final int WINDOW_HEIGHT = 140;
   private static final int WINDOW_INSIDE_X = 9;
   private static final int WINDOW_INSIDE_Y = 18;
   public static final int WINDOW_INSIDE_WIDTH = 234;
   public static final int WINDOW_INSIDE_HEIGHT = 113;
   private static final int WINDOW_TITLE_X = 8;
   private static final int WINDOW_TITLE_Y = 6;
   public static final int BACKGROUND_TILE_WIDTH = 16;
   public static final int BACKGROUND_TILE_HEIGHT = 16;
   public static final int BACKGROUND_TILE_COUNT_X = 14;
   public static final int BACKGROUND_TILE_COUNT_Y = 7;
   private static final Component VERY_SAD_LABEL = new TranslatableComponent("advancements.sad_label");
   private static final Component NO_ADVANCEMENTS_LABEL = new TranslatableComponent("advancements.empty");
   private static final Component TITLE = new TranslatableComponent("gui.advancements");
   private final ClientAdvancements advancements;
   private final Map<Advancement, AdvancementTab> tabs = Maps.<Advancement, AdvancementTab>newLinkedHashMap();
   private AdvancementTab selectedTab;
   private boolean isScrolling;

   public AdvancementsScreen(ClientAdvancements var1) {
      super(NarratorChatListener.NO_TITLE);
      this.advancements = â˜ƒ;
   }

   @Override
   protected void init() {
      this.tabs.clear();
      this.selectedTab = null;
      this.advancements.setListener(this);
      if (this.selectedTab == null && !this.tabs.isEmpty()) {
         this.advancements.setSelectedTab(((AdvancementTab)this.tabs.values().iterator().next()).getAdvancement(), true);
      } else {
         this.advancements.setSelectedTab(this.selectedTab == null ? null : this.selectedTab.getAdvancement(), true);
      }
   }

   @Override
   public void removed() {
      this.advancements.setListener(null);
      ClientPacketListener â˜ƒ = this.minecraft.getConnection();
      if (â˜ƒ != null) {
         â˜ƒ.send(ServerboundSeenAdvancementsPacket.closedScreen());
      }
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (â˜ƒ == 0) {
         int â˜ƒ = (this.width - 252) / 2;
         int â˜ƒx = (this.height - 140) / 2;

         for(AdvancementTab â˜ƒxx : this.tabs.values()) {
            if (â˜ƒxx.isMouseOver(â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ)) {
               this.advancements.setSelectedTab(â˜ƒxx.getAdvancement(), true);
               break;
            }
         }
      }

      return super.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (this.minecraft.options.keyAdvancements.matches(â˜ƒ, â˜ƒ)) {
         this.minecraft.setScreen(null);
         this.minecraft.mouseHandler.grabMouse();
         return true;
      } else {
         return super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      int â˜ƒ = (this.width - 252) / 2;
      int â˜ƒx = (this.height - 140) / 2;
      this.renderBackground(â˜ƒ);
      this.renderInside(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx);
      this.renderWindow(â˜ƒ, â˜ƒ, â˜ƒx);
      this.renderTooltips(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx);
   }

   @Override
   public boolean mouseDragged(double var1, double var3, int var5, double var6, double var8) {
      if (â˜ƒ != 0) {
         this.isScrolling = false;
         return false;
      } else {
         if (!this.isScrolling) {
            this.isScrolling = true;
         } else if (this.selectedTab != null) {
            this.selectedTab.scroll(â˜ƒ, â˜ƒ);
         }

         return true;
      }
   }

   private void renderInside(PoseStack var1, int var2, int var3, int var4, int var5) {
      AdvancementTab â˜ƒ = this.selectedTab;
      if (â˜ƒ == null) {
         fill(â˜ƒ, â˜ƒ + 9, â˜ƒ + 18, â˜ƒ + 9 + 234, â˜ƒ + 18 + 113, -16777216);
         int â˜ƒx = â˜ƒ + 9 + 117;
         drawCenteredString(â˜ƒ, this.font, NO_ADVANCEMENTS_LABEL, â˜ƒx, â˜ƒ + 18 + 56 - 9 / 2, -1);
         drawCenteredString(â˜ƒ, this.font, VERY_SAD_LABEL, â˜ƒx, â˜ƒ + 18 + 113 - 9, -1);
      } else {
         PoseStack â˜ƒ = RenderSystem.getModelViewStack();
         â˜ƒ.pushPose();
         â˜ƒ.translate((double)(â˜ƒ + 9), (double)(â˜ƒ + 18), 0.0);
         RenderSystem.applyModelViewMatrix();
         â˜ƒ.drawContents(â˜ƒ);
         â˜ƒ.popPose();
         RenderSystem.applyModelViewMatrix();
         RenderSystem.depthFunc(515);
         RenderSystem.disableDepthTest();
      }
   }

   public void renderWindow(PoseStack var1, int var2, int var3) {
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableBlend();
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderTexture(0, WINDOW_LOCATION);
      this.blit(â˜ƒ, â˜ƒ, â˜ƒ, 0, 0, 252, 140);
      if (this.tabs.size() > 1) {
         RenderSystem.setShaderTexture(0, TABS_LOCATION);

         for(AdvancementTab â˜ƒ : this.tabs.values()) {
            â˜ƒ.drawTab(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ == this.selectedTab);
         }

         RenderSystem.defaultBlendFunc();

         for(AdvancementTab â˜ƒ : this.tabs.values()) {
            â˜ƒ.drawIcon(â˜ƒ, â˜ƒ, this.itemRenderer);
         }

         RenderSystem.disableBlend();
      }

      this.font.draw(â˜ƒ, TITLE, (float)(â˜ƒ + 8), (float)(â˜ƒ + 6), 4210752);
   }

   private void renderTooltips(PoseStack var1, int var2, int var3, int var4, int var5) {
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      if (this.selectedTab != null) {
         PoseStack â˜ƒ = RenderSystem.getModelViewStack();
         â˜ƒ.pushPose();
         â˜ƒ.translate((double)(â˜ƒ + 9), (double)(â˜ƒ + 18), 400.0);
         RenderSystem.applyModelViewMatrix();
         RenderSystem.enableDepthTest();
         this.selectedTab.drawTooltips(â˜ƒ, â˜ƒ - â˜ƒ - 9, â˜ƒ - â˜ƒ - 18, â˜ƒ, â˜ƒ);
         RenderSystem.disableDepthTest();
         â˜ƒ.popPose();
         RenderSystem.applyModelViewMatrix();
      }

      if (this.tabs.size() > 1) {
         for(AdvancementTab â˜ƒ : this.tabs.values()) {
            if (â˜ƒ.isMouseOver(â˜ƒ, â˜ƒ, (double)â˜ƒ, (double)â˜ƒ)) {
               this.renderTooltip(â˜ƒ, â˜ƒ.getTitle(), â˜ƒ, â˜ƒ);
            }
         }
      }
   }

   @Override
   public void onAddAdvancementRoot(Advancement var1) {
      AdvancementTab â˜ƒ = AdvancementTab.create(this.minecraft, this, this.tabs.size(), â˜ƒ);
      if (â˜ƒ != null) {
         this.tabs.put(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void onRemoveAdvancementRoot(Advancement var1) {
   }

   @Override
   public void onAddAdvancementTask(Advancement var1) {
      AdvancementTab â˜ƒ = this.getTab(â˜ƒ);
      if (â˜ƒ != null) {
         â˜ƒ.addAdvancement(â˜ƒ);
      }
   }

   @Override
   public void onRemoveAdvancementTask(Advancement var1) {
   }

   @Override
   public void onUpdateAdvancementProgress(Advancement var1, AdvancementProgress var2) {
      AdvancementWidget â˜ƒ = this.getAdvancementWidget(â˜ƒ);
      if (â˜ƒ != null) {
         â˜ƒ.setProgress(â˜ƒ);
      }
   }

   @Override
   public void onSelectedTabChanged(@Nullable Advancement var1) {
      this.selectedTab = (AdvancementTab)this.tabs.get(â˜ƒ);
   }

   @Override
   public void onAdvancementsCleared() {
      this.tabs.clear();
      this.selectedTab = null;
   }

   @Nullable
   public AdvancementWidget getAdvancementWidget(Advancement var1) {
      AdvancementTab â˜ƒ = this.getTab(â˜ƒ);
      return â˜ƒ == null ? null : â˜ƒ.getWidget(â˜ƒ);
   }

   @Nullable
   private AdvancementTab getTab(Advancement var1) {
      while(â˜ƒ.getParent() != null) {
         â˜ƒ = â˜ƒ.getParent();
      }

      return (AdvancementTab)this.tabs.get(â˜ƒ);
   }
}
