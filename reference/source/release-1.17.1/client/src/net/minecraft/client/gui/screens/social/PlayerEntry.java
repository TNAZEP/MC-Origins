package net.minecraft.client.gui.screens.social;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.FormattedCharSequence;

public class PlayerEntry extends ContainerObjectSelectionList.Entry<PlayerEntry> {
   private static final int TOOLTIP_DELAY = 10;
   private static final int TOOLTIP_MAX_WIDTH = 150;
   private final Minecraft minecraft;
   private final List<AbstractWidget> children;
   private final UUID id;
   private final String playerName;
   private final Supplier<ResourceLocation> skinGetter;
   private boolean isRemoved;
   @Nullable
   private Button hideButton;
   @Nullable
   private Button showButton;
   final Component hideText;
   final Component showText;
   final List<FormattedCharSequence> hideTooltip;
   final List<FormattedCharSequence> showTooltip;
   float tooltipHoverTime;
   private static final Component HIDDEN = new TranslatableComponent("gui.socialInteractions.status_hidden").withStyle(ChatFormatting.ITALIC);
   private static final Component BLOCKED = new TranslatableComponent("gui.socialInteractions.status_blocked").withStyle(ChatFormatting.ITALIC);
   private static final Component OFFLINE = new TranslatableComponent("gui.socialInteractions.status_offline").withStyle(ChatFormatting.ITALIC);
   private static final Component HIDDEN_OFFLINE = new TranslatableComponent("gui.socialInteractions.status_hidden_offline").withStyle(ChatFormatting.ITALIC);
   private static final Component BLOCKED_OFFLINE = new TranslatableComponent("gui.socialInteractions.status_blocked_offline").withStyle(ChatFormatting.ITALIC);
   private static final int SKIN_SIZE = 24;
   private static final int PADDING = 4;
   private static final int CHAT_TOGGLE_ICON_SIZE = 20;
   private static final int CHAT_TOGGLE_ICON_X = 0;
   private static final int CHAT_TOGGLE_ICON_Y = 38;
   public static final int SKIN_SHADE = FastColor.ARGB32.color(190, 0, 0, 0);
   public static final int BG_FILL = FastColor.ARGB32.color(255, 74, 74, 74);
   public static final int BG_FILL_REMOVED = FastColor.ARGB32.color(255, 48, 48, 48);
   public static final int PLAYERNAME_COLOR = FastColor.ARGB32.color(255, 255, 255, 255);
   public static final int PLAYER_STATUS_COLOR = FastColor.ARGB32.color(140, 255, 255, 255);

   public PlayerEntry(final Minecraft var1, final SocialInteractionsScreen var2, UUID var3, String var4, Supplier<ResourceLocation> var5) {
      this.minecraft = â˜ƒ;
      this.id = â˜ƒ;
      this.playerName = â˜ƒ;
      this.skinGetter = â˜ƒ;
      this.hideText = new TranslatableComponent("gui.socialInteractions.tooltip.hide", â˜ƒ);
      this.showText = new TranslatableComponent("gui.socialInteractions.tooltip.show", â˜ƒ);
      this.hideTooltip = â˜ƒ.font.split(this.hideText, 150);
      this.showTooltip = â˜ƒ.font.split(this.showText, 150);
      PlayerSocialManager â˜ƒ = â˜ƒ.getPlayerSocialManager();
      if (!â˜ƒ.player.getGameProfile().getId().equals(â˜ƒ) && !â˜ƒ.isBlocked(â˜ƒ)) {
         this.hideButton = new ImageButton(0, 0, 20, 20, 0, 38, 20, SocialInteractionsScreen.SOCIAL_INTERACTIONS_LOCATION, 256, 256, var4x -> {
            â˜ƒ.hidePlayer(â˜ƒ);
            this.onHiddenOrShown(true, new TranslatableComponent("gui.socialInteractions.hidden_in_chat", â˜ƒ));
         }, new Button.OnTooltip() {
            @Override
            public void onTooltip(Button var1x, PoseStack var2x, int var3, int var4) {
               PlayerEntry.this.tooltipHoverTime += â˜ƒ.getDeltaFrameTime();
               if (PlayerEntry.this.tooltipHoverTime >= 10.0F) {
                  â˜ƒ.setPostRenderRunnable(() -> PlayerEntry.postRenderTooltip(â˜ƒ, â˜ƒ, PlayerEntry.this.hideTooltip, â˜ƒ, â˜ƒ));
               }
            }

            @Override
            public void narrateTooltip(Consumer<Component> var1x) {
               â˜ƒ.accept(PlayerEntry.this.hideText);
            }
         }, new TranslatableComponent("gui.socialInteractions.hide")) {
            @Override
            protected MutableComponent createNarrationMessage() {
               return PlayerEntry.this.getEntryNarationMessage(super.createNarrationMessage());
            }
         };
         this.showButton = new ImageButton(0, 0, 20, 20, 20, 38, 20, SocialInteractionsScreen.SOCIAL_INTERACTIONS_LOCATION, 256, 256, var4x -> {
            â˜ƒ.showPlayer(â˜ƒ);
            this.onHiddenOrShown(false, new TranslatableComponent("gui.socialInteractions.shown_in_chat", â˜ƒ));
         }, new Button.OnTooltip() {
            @Override
            public void onTooltip(Button var1x, PoseStack var2x, int var3, int var4) {
               PlayerEntry.this.tooltipHoverTime += â˜ƒ.getDeltaFrameTime();
               if (PlayerEntry.this.tooltipHoverTime >= 10.0F) {
                  â˜ƒ.setPostRenderRunnable(() -> PlayerEntry.postRenderTooltip(â˜ƒ, â˜ƒ, PlayerEntry.this.showTooltip, â˜ƒ, â˜ƒ));
               }
            }

            @Override
            public void narrateTooltip(Consumer<Component> var1x) {
               â˜ƒ.accept(PlayerEntry.this.showText);
            }
         }, new TranslatableComponent("gui.socialInteractions.show")) {
            @Override
            protected MutableComponent createNarrationMessage() {
               return PlayerEntry.this.getEntryNarationMessage(super.createNarrationMessage());
            }
         };
         this.showButton.visible = â˜ƒ.isHidden(â˜ƒ);
         this.hideButton.visible = !this.showButton.visible;
         this.children = ImmutableList.of(this.hideButton, this.showButton);
      } else {
         this.children = ImmutableList.of();
      }
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
      int â˜ƒx = â˜ƒ + 4;
      int â˜ƒxx = â˜ƒ + (â˜ƒ - 24) / 2;
      int â˜ƒxxx = â˜ƒx + 24 + 4;
      Component â˜ƒxxxx = this.getStatusComponent();
      int â˜ƒ;
      if (â˜ƒxxxx == TextComponent.EMPTY) {
         GuiComponent.fill(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ + â˜ƒ, BG_FILL);
         â˜ƒ = â˜ƒ + (â˜ƒ - 9) / 2;
      } else {
         GuiComponent.fill(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ + â˜ƒ, BG_FILL_REMOVED);
         â˜ƒ = â˜ƒ + (â˜ƒ - (9 + 9)) / 2;
         this.minecraft.font.draw(â˜ƒ, â˜ƒxxxx, (float)â˜ƒxxx, (float)(â˜ƒ + 12), PLAYER_STATUS_COLOR);
      }

      RenderSystem.setShaderTexture(0, (ResourceLocation)this.skinGetter.get());
      GuiComponent.blit(â˜ƒ, â˜ƒx, â˜ƒxx, 24, 24, 8.0F, 8.0F, 8, 8, 64, 64);
      RenderSystem.enableBlend();
      GuiComponent.blit(â˜ƒ, â˜ƒx, â˜ƒxx, 24, 24, 40.0F, 8.0F, 8, 8, 64, 64);
      RenderSystem.disableBlend();
      this.minecraft.font.draw(â˜ƒ, this.playerName, (float)â˜ƒxxx, (float)â˜ƒ, PLAYERNAME_COLOR);
      if (this.isRemoved) {
         GuiComponent.fill(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒx + 24, â˜ƒxx + 24, SKIN_SHADE);
      }

      if (this.hideButton != null && this.showButton != null) {
         float â˜ƒ = this.tooltipHoverTime;
         this.hideButton.x = â˜ƒ + (â˜ƒ - this.hideButton.getWidth() - 4);
         this.hideButton.y = â˜ƒ + (â˜ƒ - this.hideButton.getHeight()) / 2;
         this.hideButton.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.showButton.x = â˜ƒ + (â˜ƒ - this.showButton.getWidth() - 4);
         this.showButton.y = â˜ƒ + (â˜ƒ - this.showButton.getHeight()) / 2;
         this.showButton.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒ == this.tooltipHoverTime) {
            this.tooltipHoverTime = 0.0F;
         }
      }
   }

   @Override
   public List<? extends GuiEventListener> children() {
      return this.children;
   }

   @Override
   public List<? extends NarratableEntry> narratables() {
      return this.children;
   }

   public String getPlayerName() {
      return this.playerName;
   }

   public UUID getPlayerId() {
      return this.id;
   }

   public void setRemoved(boolean var1) {
      this.isRemoved = â˜ƒ;
   }

   private void onHiddenOrShown(boolean var1, Component var2) {
      this.showButton.visible = â˜ƒ;
      this.hideButton.visible = !â˜ƒ;
      this.minecraft.gui.getChat().addMessage(â˜ƒ);
      NarratorChatListener.INSTANCE.sayNow(â˜ƒ);
   }

   MutableComponent getEntryNarationMessage(MutableComponent var1) {
      Component â˜ƒ = this.getStatusComponent();
      return â˜ƒ == TextComponent.EMPTY
         ? new TextComponent(this.playerName).append(", ").append(â˜ƒ)
         : new TextComponent(this.playerName).append(", ").append(â˜ƒ).append(", ").append(â˜ƒ);
   }

   private Component getStatusComponent() {
      boolean â˜ƒ = this.minecraft.getPlayerSocialManager().isHidden(this.id);
      boolean â˜ƒx = this.minecraft.getPlayerSocialManager().isBlocked(this.id);
      if (â˜ƒx && this.isRemoved) {
         return BLOCKED_OFFLINE;
      } else if (â˜ƒ && this.isRemoved) {
         return HIDDEN_OFFLINE;
      } else if (â˜ƒx) {
         return BLOCKED;
      } else if (â˜ƒ) {
         return HIDDEN;
      } else {
         return this.isRemoved ? OFFLINE : TextComponent.EMPTY;
      }
   }

   static void postRenderTooltip(SocialInteractionsScreen var0, PoseStack var1, List<FormattedCharSequence> var2, int var3, int var4) {
      â˜ƒ.renderTooltip(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.setPostRenderRunnable(null);
   }
}
