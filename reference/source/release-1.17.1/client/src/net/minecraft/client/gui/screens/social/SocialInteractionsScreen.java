package net.minecraft.client.gui.screens.social;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collection;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class SocialInteractionsScreen extends Screen {
   protected static final ResourceLocation SOCIAL_INTERACTIONS_LOCATION = new ResourceLocation("textures/gui/social_interactions.png");
   private static final Component TAB_ALL = new TranslatableComponent("gui.socialInteractions.tab_all");
   private static final Component TAB_HIDDEN = new TranslatableComponent("gui.socialInteractions.tab_hidden");
   private static final Component TAB_BLOCKED = new TranslatableComponent("gui.socialInteractions.tab_blocked");
   private static final Component TAB_ALL_SELECTED = TAB_ALL.plainCopy().withStyle(ChatFormatting.UNDERLINE);
   private static final Component TAB_HIDDEN_SELECTED = TAB_HIDDEN.plainCopy().withStyle(ChatFormatting.UNDERLINE);
   private static final Component TAB_BLOCKED_SELECTED = TAB_BLOCKED.plainCopy().withStyle(ChatFormatting.UNDERLINE);
   private static final Component SEARCH_HINT = new TranslatableComponent("gui.socialInteractions.search_hint")
      .withStyle(ChatFormatting.ITALIC)
      .withStyle(ChatFormatting.GRAY);
   static final Component EMPTY_SEARCH = new TranslatableComponent("gui.socialInteractions.search_empty").withStyle(ChatFormatting.GRAY);
   private static final Component EMPTY_HIDDEN = new TranslatableComponent("gui.socialInteractions.empty_hidden").withStyle(ChatFormatting.GRAY);
   private static final Component EMPTY_BLOCKED = new TranslatableComponent("gui.socialInteractions.empty_blocked").withStyle(ChatFormatting.GRAY);
   private static final Component BLOCKING_HINT = new TranslatableComponent("gui.socialInteractions.blocking_hint");
   private static final String BLOCK_LINK = "https://aka.ms/javablocking";
   private static final int BG_BORDER_SIZE = 8;
   private static final int BG_UNITS = 16;
   private static final int BG_WIDTH = 236;
   private static final int SEARCH_HEIGHT = 16;
   private static final int MARGIN_Y = 64;
   public static final int LIST_START = 88;
   public static final int SEARCH_START = 78;
   private static final int IMAGE_WIDTH = 238;
   private static final int BUTTON_HEIGHT = 20;
   private static final int ITEM_HEIGHT = 36;
   SocialInteractionsPlayerList socialInteractionsPlayerList;
   EditBox searchBox;
   private String lastSearch = "";
   private SocialInteractionsScreen.Page page = SocialInteractionsScreen.Page.ALL;
   private Button allButton;
   private Button hiddenButton;
   private Button blockedButton;
   private Button blockingHintButton;
   @Nullable
   private Component serverLabel;
   private int playerCount;
   private boolean initialized;
   @Nullable
   private Runnable postRenderRunnable;

   public SocialInteractionsScreen() {
      super(new TranslatableComponent("gui.socialInteractions.title"));
      this.updateServerLabel(Minecraft.getInstance());
   }

   private int windowHeight() {
      return Math.max(52, this.height - 128 - 16);
   }

   private int backgroundUnits() {
      return this.windowHeight() / 16;
   }

   private int listEnd() {
      return 80 + this.backgroundUnits() * 16 - 8;
   }

   private int marginX() {
      return (this.width - 238) / 2;
   }

   @Override
   public Component getNarrationMessage() {
      return (Component)(this.serverLabel != null
         ? CommonComponents.joinForNarration(super.getNarrationMessage(), this.serverLabel)
         : super.getNarrationMessage());
   }

   @Override
   public void tick() {
      super.tick();
      this.searchBox.tick();
   }

   @Override
   protected void init() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
      if (this.initialized) {
         this.socialInteractionsPlayerList.updateSize(this.width, this.height, 88, this.listEnd());
      } else {
         this.socialInteractionsPlayerList = new SocialInteractionsPlayerList(this, this.minecraft, this.width, this.height, 88, this.listEnd(), 36);
      }

      int â˜ƒ = this.socialInteractionsPlayerList.getRowWidth() / 3;
      int â˜ƒx = this.socialInteractionsPlayerList.getRowLeft();
      int â˜ƒxx = this.socialInteractionsPlayerList.getRowRight();
      int â˜ƒxxx = this.font.width(BLOCKING_HINT) + 40;
      int â˜ƒxxxx = 64 + 16 * this.backgroundUnits();
      int â˜ƒxxxxx = (this.width - â˜ƒxxx) / 2;
      this.allButton = this.addRenderableWidget(new Button(â˜ƒx, 45, â˜ƒ, 20, TAB_ALL, var1x -> this.showPage(SocialInteractionsScreen.Page.ALL)));
      this.hiddenButton = this.addRenderableWidget(
         new Button((â˜ƒx + â˜ƒxx - â˜ƒ) / 2 + 1, 45, â˜ƒ, 20, TAB_HIDDEN, var1x -> this.showPage(SocialInteractionsScreen.Page.HIDDEN))
      );
      this.blockedButton = this.addRenderableWidget(
         new Button(â˜ƒxx - â˜ƒ + 1, 45, â˜ƒ, 20, TAB_BLOCKED, var1x -> this.showPage(SocialInteractionsScreen.Page.BLOCKED))
      );
      this.blockingHintButton = this.addRenderableWidget(
         new Button(â˜ƒxxxxx, â˜ƒxxxx, â˜ƒxxx, 20, BLOCKING_HINT, var1x -> this.minecraft.setScreen(new ConfirmLinkScreen(var1xx -> {
               if (var1xx) {
                  Util.getPlatform().openUri("https://aka.ms/javablocking");
               }
   
               this.minecraft.setScreen(this);
            }, "https://aka.ms/javablocking", true)))
      );
      String â˜ƒxxxxxx = this.searchBox != null ? this.searchBox.getValue() : "";
      this.searchBox = new EditBox(this.font, this.marginX() + 28, 78, 196, 16, SEARCH_HINT) {
         @Override
         protected MutableComponent createNarrationMessage() {
            return !SocialInteractionsScreen.this.searchBox.getValue().isEmpty() && SocialInteractionsScreen.this.socialInteractionsPlayerList.isEmpty()
               ? super.createNarrationMessage().append(", ").append(SocialInteractionsScreen.EMPTY_SEARCH)
               : super.createNarrationMessage();
         }
      };
      this.searchBox.setMaxLength(16);
      this.searchBox.setBordered(false);
      this.searchBox.setVisible(true);
      this.searchBox.setTextColor(16777215);
      this.searchBox.setValue(â˜ƒxxxxxx);
      this.searchBox.setResponder(this::checkSearchStringUpdate);
      this.addWidget(this.searchBox);
      this.addWidget(this.socialInteractionsPlayerList);
      this.initialized = true;
      this.showPage(this.page);
   }

   private void showPage(SocialInteractionsScreen.Page var1) {
      this.page = â˜ƒ;
      this.allButton.setMessage(TAB_ALL);
      this.hiddenButton.setMessage(TAB_HIDDEN);
      this.blockedButton.setMessage(TAB_BLOCKED);

      Collection var2 = switch(â˜ƒ) {
         case ALL -> {
            this.allButton.setMessage(TAB_ALL_SELECTED);
            yield this.minecraft.player.connection.getOnlinePlayerIds();
         }
         case HIDDEN -> {
            this.hiddenButton.setMessage(TAB_HIDDEN_SELECTED);
            yield this.minecraft.getPlayerSocialManager().getHiddenPlayers();
         }
         case BLOCKED -> {
            this.blockedButton.setMessage(TAB_BLOCKED_SELECTED);
            PlayerSocialManager â˜ƒ = this.minecraft.getPlayerSocialManager();
            yield (Collection)this.minecraft.player.connection.getOnlinePlayerIds().stream().filter(â˜ƒ::isBlocked).collect(Collectors.toSet());
         }
         default -> ImmutableList.of();
      };
      this.socialInteractionsPlayerList.updatePlayerList(var2, this.socialInteractionsPlayerList.getScrollAmount());
      if (!this.searchBox.getValue().isEmpty() && this.socialInteractionsPlayerList.isEmpty() && !this.searchBox.isFocused()) {
         NarratorChatListener.INSTANCE.sayNow(EMPTY_SEARCH);
      } else if (var2.isEmpty()) {
         if (â˜ƒ == SocialInteractionsScreen.Page.HIDDEN) {
            NarratorChatListener.INSTANCE.sayNow(EMPTY_HIDDEN);
         } else if (â˜ƒ == SocialInteractionsScreen.Page.BLOCKED) {
            NarratorChatListener.INSTANCE.sayNow(EMPTY_BLOCKED);
         }
      }
   }

   @Override
   public void removed() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
   }

   @Override
   public void renderBackground(PoseStack var1) {
      int â˜ƒ = this.marginX() + 3;
      super.renderBackground(â˜ƒ);
      RenderSystem.setShaderTexture(0, SOCIAL_INTERACTIONS_LOCATION);
      this.blit(â˜ƒ, â˜ƒ, 64, 1, 1, 236, 8);
      int â˜ƒx = this.backgroundUnits();

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx; ++â˜ƒxx) {
         this.blit(â˜ƒ, â˜ƒ, 72 + 16 * â˜ƒxx, 1, 10, 236, 16);
      }

      this.blit(â˜ƒ, â˜ƒ, 72 + 16 * â˜ƒx, 1, 27, 236, 8);
      this.blit(â˜ƒ, â˜ƒ + 10, 76, 243, 1, 12, 12);
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.updateServerLabel(this.minecraft);
      this.renderBackground(â˜ƒ);
      if (this.serverLabel != null) {
         drawString(â˜ƒ, this.minecraft.font, this.serverLabel, this.marginX() + 8, 35, -1);
      }

      if (!this.socialInteractionsPlayerList.isEmpty()) {
         this.socialInteractionsPlayerList.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (!this.searchBox.getValue().isEmpty()) {
         drawCenteredString(â˜ƒ, this.minecraft.font, EMPTY_SEARCH, this.width / 2, (78 + this.listEnd()) / 2, -1);
      } else if (this.page == SocialInteractionsScreen.Page.HIDDEN) {
         drawCenteredString(â˜ƒ, this.minecraft.font, EMPTY_HIDDEN, this.width / 2, (78 + this.listEnd()) / 2, -1);
      } else if (this.page == SocialInteractionsScreen.Page.BLOCKED) {
         drawCenteredString(â˜ƒ, this.minecraft.font, EMPTY_BLOCKED, this.width / 2, (78 + this.listEnd()) / 2, -1);
      }

      if (!this.searchBox.isFocused() && this.searchBox.getValue().isEmpty()) {
         drawString(â˜ƒ, this.minecraft.font, SEARCH_HINT, this.searchBox.x, this.searchBox.y, -1);
      } else {
         this.searchBox.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      this.blockingHintButton.visible = this.page == SocialInteractionsScreen.Page.BLOCKED;
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (this.postRenderRunnable != null) {
         this.postRenderRunnable.run();
      }
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (this.searchBox.isFocused()) {
         this.searchBox.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      return super.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ) || this.socialInteractionsPlayerList.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (!this.searchBox.isFocused() && this.minecraft.options.keySocialInteractions.matches(â˜ƒ, â˜ƒ)) {
         this.minecraft.setScreen(null);
         return true;
      } else {
         return super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public boolean isPauseScreen() {
      return false;
   }

   private void checkSearchStringUpdate(String var1) {
      â˜ƒ = â˜ƒ.toLowerCase(Locale.ROOT);
      if (!â˜ƒ.equals(this.lastSearch)) {
         this.socialInteractionsPlayerList.setFilter(â˜ƒ);
         this.lastSearch = â˜ƒ;
         this.showPage(this.page);
      }
   }

   private void updateServerLabel(Minecraft var1) {
      int â˜ƒ = â˜ƒ.getConnection().getOnlinePlayers().size();
      if (this.playerCount != â˜ƒ) {
         String â˜ƒx = "";
         ServerData â˜ƒxx = â˜ƒ.getCurrentServer();
         if (â˜ƒ.isLocalServer()) {
            â˜ƒx = â˜ƒ.getSingleplayerServer().getMotd();
         } else if (â˜ƒxx != null) {
            â˜ƒx = â˜ƒxx.name;
         }

         if (â˜ƒ > 1) {
            this.serverLabel = new TranslatableComponent("gui.socialInteractions.server_label.multiple", â˜ƒx, â˜ƒ);
         } else {
            this.serverLabel = new TranslatableComponent("gui.socialInteractions.server_label.single", â˜ƒx, â˜ƒ);
         }

         this.playerCount = â˜ƒ;
      }
   }

   public void onAddPlayer(PlayerInfo var1) {
      this.socialInteractionsPlayerList.addPlayer(â˜ƒ, this.page);
   }

   public void onRemovePlayer(UUID var1) {
      this.socialInteractionsPlayerList.removePlayer(â˜ƒ);
   }

   public void setPostRenderRunnable(@Nullable Runnable var1) {
      this.postRenderRunnable = â˜ƒ;
   }

   public static enum Page {
      ALL,
      HIDDEN,
      BLOCKED;
   }
}
