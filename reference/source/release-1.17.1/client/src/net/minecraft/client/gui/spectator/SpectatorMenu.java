package net.minecraft.client.gui.spectator;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.spectator.SpectatorGui;
import net.minecraft.client.gui.spectator.categories.SpectatorPage;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class SpectatorMenu {
   private static final SpectatorMenuItem CLOSE_ITEM = new SpectatorMenu.CloseSpectatorItem();
   private static final SpectatorMenuItem SCROLL_LEFT = new SpectatorMenu.ScrollMenuItem(-1, true);
   private static final SpectatorMenuItem SCROLL_RIGHT_ENABLED = new SpectatorMenu.ScrollMenuItem(1, true);
   private static final SpectatorMenuItem SCROLL_RIGHT_DISABLED = new SpectatorMenu.ScrollMenuItem(1, false);
   private static final int MAX_PER_PAGE = 8;
   static final Component CLOSE_MENU_TEXT = new TranslatableComponent("spectatorMenu.close");
   static final Component PREVIOUS_PAGE_TEXT = new TranslatableComponent("spectatorMenu.previous_page");
   static final Component NEXT_PAGE_TEXT = new TranslatableComponent("spectatorMenu.next_page");
   public static final SpectatorMenuItem EMPTY_SLOT = new SpectatorMenuItem() {
      @Override
      public void selectItem(SpectatorMenu var1) {
      }

      @Override
      public Component getName() {
         return TextComponent.EMPTY;
      }

      @Override
      public void renderIcon(PoseStack var1, float var2, int var3) {
      }

      @Override
      public boolean isEnabled() {
         return false;
      }
   };
   private final SpectatorMenuListener listener;
   private SpectatorMenuCategory category;
   private int selectedSlot = -1;
   int page;

   public SpectatorMenu(SpectatorMenuListener var1) {
      this.category = new RootSpectatorMenuCategory();
      this.listener = â˜ƒ;
   }

   public SpectatorMenuItem getItem(int var1) {
      int â˜ƒ = â˜ƒ + this.page * 6;
      if (this.page > 0 && â˜ƒ == 0) {
         return SCROLL_LEFT;
      } else if (â˜ƒ == 7) {
         return â˜ƒ < this.category.getItems().size() ? SCROLL_RIGHT_ENABLED : SCROLL_RIGHT_DISABLED;
      } else if (â˜ƒ == 8) {
         return CLOSE_ITEM;
      } else {
         return â˜ƒ >= 0 && â˜ƒ < this.category.getItems().size()
            ? MoreObjects.firstNonNull((SpectatorMenuItem)this.category.getItems().get(â˜ƒ), EMPTY_SLOT)
            : EMPTY_SLOT;
      }
   }

   public List<SpectatorMenuItem> getItems() {
      List<SpectatorMenuItem> â˜ƒ = Lists.<SpectatorMenuItem>newArrayList();

      for(int â˜ƒx = 0; â˜ƒx <= 8; ++â˜ƒx) {
         â˜ƒ.add(this.getItem(â˜ƒx));
      }

      return â˜ƒ;
   }

   public SpectatorMenuItem getSelectedItem() {
      return this.getItem(this.selectedSlot);
   }

   public SpectatorMenuCategory getSelectedCategory() {
      return this.category;
   }

   public void selectSlot(int var1) {
      SpectatorMenuItem â˜ƒ = this.getItem(â˜ƒ);
      if (â˜ƒ != EMPTY_SLOT) {
         if (this.selectedSlot == â˜ƒ && â˜ƒ.isEnabled()) {
            â˜ƒ.selectItem(this);
         } else {
            this.selectedSlot = â˜ƒ;
         }
      }
   }

   public void exit() {
      this.listener.onSpectatorMenuClosed(this);
   }

   public int getSelectedSlot() {
      return this.selectedSlot;
   }

   public void selectCategory(SpectatorMenuCategory var1) {
      this.category = â˜ƒ;
      this.selectedSlot = -1;
      this.page = 0;
   }

   public SpectatorPage getCurrentPage() {
      return new SpectatorPage(this.getItems(), this.selectedSlot);
   }

   static class CloseSpectatorItem implements SpectatorMenuItem {
      @Override
      public void selectItem(SpectatorMenu var1) {
         â˜ƒ.exit();
      }

      @Override
      public Component getName() {
         return SpectatorMenu.CLOSE_MENU_TEXT;
      }

      @Override
      public void renderIcon(PoseStack var1, float var2, int var3) {
         RenderSystem.setShaderTexture(0, SpectatorGui.SPECTATOR_LOCATION);
         GuiComponent.blit(â˜ƒ, 0, 0, 128.0F, 0.0F, 16, 16, 256, 256);
      }

      @Override
      public boolean isEnabled() {
         return true;
      }
   }

   static class ScrollMenuItem implements SpectatorMenuItem {
      private final int direction;
      private final boolean enabled;

      public ScrollMenuItem(int var1, boolean var2) {
         this.direction = â˜ƒ;
         this.enabled = â˜ƒ;
      }

      @Override
      public void selectItem(SpectatorMenu var1) {
         â˜ƒ.page += this.direction;
      }

      @Override
      public Component getName() {
         return this.direction < 0 ? SpectatorMenu.PREVIOUS_PAGE_TEXT : SpectatorMenu.NEXT_PAGE_TEXT;
      }

      @Override
      public void renderIcon(PoseStack var1, float var2, int var3) {
         RenderSystem.setShaderTexture(0, SpectatorGui.SPECTATOR_LOCATION);
         if (this.direction < 0) {
            GuiComponent.blit(â˜ƒ, 0, 0, 144.0F, 0.0F, 16, 16, 256, 256);
         } else {
            GuiComponent.blit(â˜ƒ, 0, 0, 160.0F, 0.0F, 16, 16, 256, 256);
         }
      }

      @Override
      public boolean isEnabled() {
         return this.enabled;
      }
   }
}
