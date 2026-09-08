package com.mojang.realmsclient;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.mojang.realmsclient.client.Ping;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.PingResult;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsServerPlayerList;
import com.mojang.realmsclient.dto.RealmsServerPlayerLists;
import com.mojang.realmsclient.dto.RegionPingResult;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.RealmsDataFetcher;
import com.mojang.realmsclient.gui.screens.RealmsClientOutdatedScreen;
import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsCreateRealmScreen;
import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongConfirmationScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTaskScreen;
import com.mojang.realmsclient.gui.screens.RealmsParentalConsentScreen;
import com.mojang.realmsclient.gui.screens.RealmsPendingInvitesScreen;
import com.mojang.realmsclient.util.RealmsPersistence;
import com.mojang.realmsclient.util.RealmsTextureManager;
import com.mojang.realmsclient.util.task.GetServerDetailsTask;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsObjectSelectionList;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Mth;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsMainScreen extends RealmsScreen {
   static final Logger LOGGER = LogManager.getLogger();
   private static final ResourceLocation ON_ICON_LOCATION = new ResourceLocation("realms", "textures/gui/realms/on_icon.png");
   private static final ResourceLocation OFF_ICON_LOCATION = new ResourceLocation("realms", "textures/gui/realms/off_icon.png");
   private static final ResourceLocation EXPIRED_ICON_LOCATION = new ResourceLocation("realms", "textures/gui/realms/expired_icon.png");
   private static final ResourceLocation EXPIRES_SOON_ICON_LOCATION = new ResourceLocation("realms", "textures/gui/realms/expires_soon_icon.png");
   private static final ResourceLocation LEAVE_ICON_LOCATION = new ResourceLocation("realms", "textures/gui/realms/leave_icon.png");
   private static final ResourceLocation INVITATION_ICONS_LOCATION = new ResourceLocation("realms", "textures/gui/realms/invitation_icons.png");
   private static final ResourceLocation INVITE_ICON_LOCATION = new ResourceLocation("realms", "textures/gui/realms/invite_icon.png");
   static final ResourceLocation WORLDICON_LOCATION = new ResourceLocation("realms", "textures/gui/realms/world_icon.png");
   private static final ResourceLocation LOGO_LOCATION = new ResourceLocation("realms", "textures/gui/title/realms.png");
   private static final ResourceLocation CONFIGURE_LOCATION = new ResourceLocation("realms", "textures/gui/realms/configure_icon.png");
   private static final ResourceLocation QUESTIONMARK_LOCATION = new ResourceLocation("realms", "textures/gui/realms/questionmark.png");
   private static final ResourceLocation NEWS_LOCATION = new ResourceLocation("realms", "textures/gui/realms/news_icon.png");
   private static final ResourceLocation POPUP_LOCATION = new ResourceLocation("realms", "textures/gui/realms/popup.png");
   private static final ResourceLocation DARKEN_LOCATION = new ResourceLocation("realms", "textures/gui/realms/darken.png");
   static final ResourceLocation CROSS_ICON_LOCATION = new ResourceLocation("realms", "textures/gui/realms/cross_icon.png");
   private static final ResourceLocation TRIAL_ICON_LOCATION = new ResourceLocation("realms", "textures/gui/realms/trial_icon.png");
   static final ResourceLocation BUTTON_LOCATION = new ResourceLocation("minecraft", "textures/gui/widgets.png");
   static final Component NO_PENDING_INVITES_TEXT = new TranslatableComponent("mco.invites.nopending");
   static final Component PENDING_INVITES_TEXT = new TranslatableComponent("mco.invites.pending");
   static final List<Component> TRIAL_MESSAGE_LINES = ImmutableList.of(
      new TranslatableComponent("mco.trial.message.line1"), new TranslatableComponent("mco.trial.message.line2")
   );
   static final Component SERVER_UNITIALIZED_TEXT = new TranslatableComponent("mco.selectServer.uninitialized");
   static final Component SUBSCRIPTION_EXPIRED_TEXT = new TranslatableComponent("mco.selectServer.expiredList");
   static final Component SUBSCRIPTION_RENEW_TEXT = new TranslatableComponent("mco.selectServer.expiredRenew");
   static final Component TRIAL_EXPIRED_TEXT = new TranslatableComponent("mco.selectServer.expiredTrial");
   static final Component SUBSCRIPTION_CREATE_TEXT = new TranslatableComponent("mco.selectServer.expiredSubscribe");
   static final Component SELECT_MINIGAME_PREFIX = new TranslatableComponent("mco.selectServer.minigame").append(" ");
   private static final Component POPUP_TEXT = new TranslatableComponent("mco.selectServer.popup");
   private static final Component SERVER_EXPIRED_TOOLTIP = new TranslatableComponent("mco.selectServer.expired");
   private static final Component SERVER_EXPIRES_SOON_TOOLTIP = new TranslatableComponent("mco.selectServer.expires.soon");
   private static final Component SERVER_EXPIRES_IN_DAY_TOOLTIP = new TranslatableComponent("mco.selectServer.expires.day");
   private static final Component SERVER_OPEN_TOOLTIP = new TranslatableComponent("mco.selectServer.open");
   private static final Component SERVER_CLOSED_TOOLTIP = new TranslatableComponent("mco.selectServer.closed");
   private static final Component LEAVE_SERVER_TOOLTIP = new TranslatableComponent("mco.selectServer.leave");
   private static final Component CONFIGURE_SERVER_TOOLTIP = new TranslatableComponent("mco.selectServer.configure");
   private static final Component SERVER_INFO_TOOLTIP = new TranslatableComponent("mco.selectServer.info");
   private static final Component NEWS_TOOLTIP = new TranslatableComponent("mco.news");
   static final Component UNITIALIZED_WORLD_NARRATION = new TranslatableComponent("gui.narrate.button", SERVER_UNITIALIZED_TEXT);
   static final Component TRIAL_TEXT = CommonComponents.joinLines(TRIAL_MESSAGE_LINES);
   private static List<ResourceLocation> teaserImages = ImmutableList.of();
   static final RealmsDataFetcher REALMS_DATA_FETCHER = new RealmsDataFetcher(Minecraft.getInstance(), RealmsClient.create());
   static boolean overrideConfigure;
   private static int lastScrollYPosition = -1;
   static volatile boolean hasParentalConsent;
   static volatile boolean checkedParentalConsent;
   static volatile boolean checkedClientCompatability;
   static Screen realmsGenericErrorScreen;
   private static boolean regionsPinged;
   private final RateLimiter inviteNarrationLimiter;
   private boolean dontSetConnectedToRealms;
   final Screen lastScreen;
   volatile RealmsMainScreen.RealmSelectionList realmSelectionList;
   private boolean realmsSelectionListAdded;
   long selectedServerId = -1L;
   Button playButton;
   private Button backButton;
   private Button renewButton;
   private Button configureButton;
   private Button leaveButton;
   private List<Component> toolTip;
   List<RealmsServer> realmsServers = Lists.<RealmsServer>newArrayList();
   volatile int numberOfPendingInvites;
   int animTick;
   private boolean hasFetchedServers;
   boolean popupOpenedByUser;
   private boolean justClosedPopup;
   private volatile boolean trialsAvailable;
   private volatile boolean createdTrial;
   private volatile boolean showingPopup;
   volatile boolean hasUnreadNews;
   volatile String newsLink;
   private int carouselIndex;
   private int carouselTick;
   private boolean hasSwitchedCarouselImage;
   private List<KeyCombo> keyCombos;
   int clicks;
   private ReentrantLock connectLock = new ReentrantLock();
   private MultiLineLabel formattedPopup = MultiLineLabel.EMPTY;
   RealmsMainScreen.HoveredElement hoveredElement;
   private Button showPopupButton;
   @Nullable
   private RealmsMainScreen.PendingInvitesButton pendingInvitesButton;
   private Button newsButton;
   private Button createTrialButton;
   private Button buyARealmButton;
   private Button closeButton;

   public RealmsMainScreen(Screen var1) {
      super(NarratorChatListener.NO_TITLE);
      this.lastScreen = â˜ƒ;
      this.inviteNarrationLimiter = RateLimiter.create(0.016666668F);
   }

   private boolean shouldShowMessageInList() {
      if (hasParentalConsent() && this.hasFetchedServers) {
         if (this.trialsAvailable && !this.createdTrial) {
            return true;
         } else {
            for(RealmsServer â˜ƒ : this.realmsServers) {
               if (â˜ƒ.ownerUUID.equals(this.minecraft.getUser().getUuid())) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public boolean shouldShowPopup() {
      if (!hasParentalConsent() || !this.hasFetchedServers) {
         return false;
      } else if (this.popupOpenedByUser) {
         return true;
      } else {
         return this.trialsAvailable && !this.createdTrial && this.realmsServers.isEmpty() ? true : this.realmsServers.isEmpty();
      }
   }

   @Override
   public void init() {
      this.keyCombos = Lists.<KeyCombo>newArrayList(
         new KeyCombo(new char[]{'3', '2', '1', '4', '5', '6'}, () -> overrideConfigure = !overrideConfigure),
         new KeyCombo(new char[]{'9', '8', '7', '1', '2', '3'}, () -> {
            if (RealmsClient.currentEnvironment == RealmsClient.Environment.STAGE) {
               this.switchToProd();
            } else {
               this.switchToStage();
            }
         }),
         new KeyCombo(new char[]{'9', '8', '7', '4', '5', '6'}, () -> {
            if (RealmsClient.currentEnvironment == RealmsClient.Environment.LOCAL) {
               this.switchToProd();
            } else {
               this.switchToLocal();
            }
         })
      );
      if (realmsGenericErrorScreen != null) {
         this.minecraft.setScreen(realmsGenericErrorScreen);
      } else {
         this.connectLock = new ReentrantLock();
         if (checkedClientCompatability && !hasParentalConsent()) {
            this.checkParentalConsent();
         }

         this.checkClientCompatability();
         this.checkUnreadNews();
         if (!this.dontSetConnectedToRealms) {
            this.minecraft.setConnectedToRealms(false);
         }

         this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
         if (hasParentalConsent()) {
            REALMS_DATA_FETCHER.forceUpdate();
         }

         this.showingPopup = false;
         if (hasParentalConsent() && this.hasFetchedServers) {
            this.addButtons();
         }

         this.realmSelectionList = new RealmsMainScreen.RealmSelectionList();
         if (lastScrollYPosition != -1) {
            this.realmSelectionList.setScrollAmount((double)lastScrollYPosition);
         }

         this.addWidget(this.realmSelectionList);
         this.realmsSelectionListAdded = true;
         this.magicalSpecialHackyFocus(this.realmSelectionList);
         this.formattedPopup = MultiLineLabel.create(this.font, POPUP_TEXT, 100);
      }
   }

   private static boolean hasParentalConsent() {
      return checkedParentalConsent && hasParentalConsent;
   }

   public void addButtons() {
      this.leaveButton = this.addRenderableWidget(
         new Button(
            this.width / 2 - 202,
            this.height - 32,
            90,
            20,
            new TranslatableComponent("mco.selectServer.leave"),
            var1x -> this.leaveClicked(this.findServer(this.selectedServerId))
         )
      );
      this.configureButton = this.addRenderableWidget(
         new Button(
            this.width / 2 - 190,
            this.height - 32,
            90,
            20,
            new TranslatableComponent("mco.selectServer.configure"),
            var1x -> this.configureClicked(this.findServer(this.selectedServerId))
         )
      );
      this.playButton = this.addRenderableWidget(
         new Button(this.width / 2 - 93, this.height - 32, 90, 20, new TranslatableComponent("mco.selectServer.play"), var1x -> {
            RealmsServer â˜ƒ = this.findServer(this.selectedServerId);
            if (â˜ƒ != null) {
               this.play(â˜ƒ, this);
            }
         })
      );
      this.backButton = this.addRenderableWidget(new Button(this.width / 2 + 4, this.height - 32, 90, 20, CommonComponents.GUI_BACK, var1x -> {
         if (!this.justClosedPopup) {
            this.minecraft.setScreen(this.lastScreen);
         }
      }));
      this.renewButton = this.addRenderableWidget(
         new Button(this.width / 2 + 100, this.height - 32, 90, 20, new TranslatableComponent("mco.selectServer.expiredRenew"), var1x -> this.onRenew())
      );
      this.pendingInvitesButton = this.addRenderableWidget(new RealmsMainScreen.PendingInvitesButton());
      this.newsButton = this.addRenderableWidget(new RealmsMainScreen.NewsButton());
      this.showPopupButton = this.addRenderableWidget(new RealmsMainScreen.ShowPopupButton());
      this.closeButton = this.addRenderableWidget(new RealmsMainScreen.CloseButton());
      this.createTrialButton = this.addRenderableWidget(
         new Button(this.width / 2 + 52, this.popupY0() + 137 - 20, 98, 20, new TranslatableComponent("mco.selectServer.trial"), var1x -> {
            if (this.trialsAvailable && !this.createdTrial) {
               Util.getPlatform().openUri("https://aka.ms/startjavarealmstrial");
               this.minecraft.setScreen(this.lastScreen);
            }
         })
      );
      this.buyARealmButton = this.addRenderableWidget(
         new Button(
            this.width / 2 + 52,
            this.popupY0() + 160 - 20,
            98,
            20,
            new TranslatableComponent("mco.selectServer.buy"),
            var0 -> Util.getPlatform().openUri("https://aka.ms/BuyJavaRealms")
         )
      );
      RealmsServer â˜ƒ = this.findServer(this.selectedServerId);
      this.updateButtonStates(â˜ƒ);
   }

   void updateButtonStates(@Nullable RealmsServer var1) {
      this.playButton.active = this.shouldPlayButtonBeActive(â˜ƒ) && !this.shouldShowPopup();
      this.renewButton.visible = this.shouldRenewButtonBeActive(â˜ƒ);
      this.configureButton.visible = this.shouldConfigureButtonBeVisible(â˜ƒ);
      this.leaveButton.visible = this.shouldLeaveButtonBeVisible(â˜ƒ);
      boolean â˜ƒ = this.shouldShowPopup() && this.trialsAvailable && !this.createdTrial;
      this.createTrialButton.visible = â˜ƒ;
      this.createTrialButton.active = â˜ƒ;
      this.buyARealmButton.visible = this.shouldShowPopup();
      this.closeButton.visible = this.shouldShowPopup() && this.popupOpenedByUser;
      this.renewButton.active = !this.shouldShowPopup();
      this.configureButton.active = !this.shouldShowPopup();
      this.leaveButton.active = !this.shouldShowPopup();
      this.newsButton.active = true;
      this.pendingInvitesButton.active = true;
      this.backButton.active = true;
      this.showPopupButton.active = !this.shouldShowPopup();
   }

   private boolean shouldShowPopupButton() {
      return (!this.shouldShowPopup() || this.popupOpenedByUser) && hasParentalConsent() && this.hasFetchedServers;
   }

   private boolean shouldPlayButtonBeActive(@Nullable RealmsServer var1) {
      return â˜ƒ != null && !â˜ƒ.expired && â˜ƒ.state == RealmsServer.State.OPEN;
   }

   private boolean shouldRenewButtonBeActive(@Nullable RealmsServer var1) {
      return â˜ƒ != null && â˜ƒ.expired && this.isSelfOwnedServer(â˜ƒ);
   }

   private boolean shouldConfigureButtonBeVisible(@Nullable RealmsServer var1) {
      return â˜ƒ != null && this.isSelfOwnedServer(â˜ƒ);
   }

   private boolean shouldLeaveButtonBeVisible(@Nullable RealmsServer var1) {
      return â˜ƒ != null && !this.isSelfOwnedServer(â˜ƒ);
   }

   @Override
   public void tick() {
      super.tick();
      if (this.pendingInvitesButton != null) {
         this.pendingInvitesButton.tick();
      }

      this.justClosedPopup = false;
      ++this.animTick;
      --this.clicks;
      if (this.clicks < 0) {
         this.clicks = 0;
      }

      if (hasParentalConsent()) {
         REALMS_DATA_FETCHER.init();
         if (REALMS_DATA_FETCHER.isFetchedSinceLastTry(RealmsDataFetcher.Task.SERVER_LIST)) {
            List<RealmsServer> â˜ƒ = REALMS_DATA_FETCHER.getServers();
            this.realmSelectionList.clear();
            boolean â˜ƒx = !this.hasFetchedServers;
            if (â˜ƒx) {
               this.hasFetchedServers = true;
            }

            if (â˜ƒ != null) {
               boolean â˜ƒ = false;

               for(RealmsServer â˜ƒx : â˜ƒ) {
                  if (this.isSelfOwnedNonExpiredServer(â˜ƒx)) {
                     â˜ƒ = true;
                  }
               }

               this.realmsServers = â˜ƒ;
               if (this.shouldShowMessageInList()) {
                  this.realmSelectionList.addMessageEntry(new RealmsMainScreen.TrialEntry());
               }

               for(RealmsServer â˜ƒx : this.realmsServers) {
                  this.realmSelectionList.addEntry(new RealmsMainScreen.ServerEntry(â˜ƒx));
               }

               if (!regionsPinged && â˜ƒ) {
                  regionsPinged = true;
                  this.pingRegions();
               }
            }

            if (â˜ƒx) {
               this.addButtons();
            } else {
               this.updateButtonStates(this.findServer(this.selectedServerId));
            }
         }

         if (REALMS_DATA_FETCHER.isFetchedSinceLastTry(RealmsDataFetcher.Task.PENDING_INVITE)) {
            this.numberOfPendingInvites = REALMS_DATA_FETCHER.getPendingInvitesCount();
            if (this.numberOfPendingInvites > 0 && this.inviteNarrationLimiter.tryAcquire(1)) {
               NarratorChatListener.INSTANCE.sayNow(new TranslatableComponent("mco.configure.world.invite.narration", this.numberOfPendingInvites));
            }
         }

         if (REALMS_DATA_FETCHER.isFetchedSinceLastTry(RealmsDataFetcher.Task.TRIAL_AVAILABLE) && !this.createdTrial) {
            boolean â˜ƒ = REALMS_DATA_FETCHER.isTrialAvailable();
            if (â˜ƒ != this.trialsAvailable && this.shouldShowPopup()) {
               this.trialsAvailable = â˜ƒ;
               this.showingPopup = false;
            } else {
               this.trialsAvailable = â˜ƒ;
            }
         }

         if (REALMS_DATA_FETCHER.isFetchedSinceLastTry(RealmsDataFetcher.Task.LIVE_STATS)) {
            RealmsServerPlayerLists â˜ƒ = REALMS_DATA_FETCHER.getLivestats();

            for(RealmsServerPlayerList â˜ƒx : â˜ƒ.servers) {
               for(RealmsServer â˜ƒxx : this.realmsServers) {
                  if (â˜ƒxx.id == â˜ƒx.serverId) {
                     â˜ƒxx.updateServerPing(â˜ƒx);
                     break;
                  }
               }
            }
         }

         if (REALMS_DATA_FETCHER.isFetchedSinceLastTry(RealmsDataFetcher.Task.UNREAD_NEWS)) {
            this.hasUnreadNews = REALMS_DATA_FETCHER.hasUnreadNews();
            this.newsLink = REALMS_DATA_FETCHER.newsLink();
         }

         REALMS_DATA_FETCHER.markClean();
         if (this.shouldShowPopup()) {
            ++this.carouselTick;
         }

         if (this.showPopupButton != null) {
            this.showPopupButton.visible = this.shouldShowPopupButton();
         }
      }
   }

   private void pingRegions() {
      new Thread(() -> {
         List<RegionPingResult> â˜ƒ = Ping.pingAllRegions();
         RealmsClient â˜ƒx = RealmsClient.create();
         PingResult â˜ƒxx = new PingResult();
         â˜ƒxx.pingResults = â˜ƒ;
         â˜ƒxx.worldIds = this.getOwnedNonExpiredWorldIds();

         try {
            â˜ƒx.sendPingResults(â˜ƒxx);
         } catch (Throwable var5) {
            LOGGER.warn("Could not send ping result to Realms: ", var5);
         }
      }).start();
   }

   private List<Long> getOwnedNonExpiredWorldIds() {
      List<Long> â˜ƒ = Lists.newArrayList();

      for(RealmsServer â˜ƒx : this.realmsServers) {
         if (this.isSelfOwnedNonExpiredServer(â˜ƒx)) {
            â˜ƒ.add(â˜ƒx.id);
         }
      }

      return â˜ƒ;
   }

   @Override
   public void removed() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
      this.stopRealmsFetcher();
   }

   public void setCreatedTrial(boolean var1) {
      this.createdTrial = â˜ƒ;
   }

   void onRenew() {
      RealmsServer â˜ƒ = this.findServer(this.selectedServerId);
      if (â˜ƒ != null) {
         String â˜ƒx = "https://aka.ms/ExtendJavaRealms?subscriptionId="
            + â˜ƒ.remoteSubscriptionId
            + "&profileId="
            + this.minecraft.getUser().getUuid()
            + "&ref="
            + (â˜ƒ.expiredTrial ? "expiredTrial" : "expiredRealm");
         this.minecraft.keyboardHandler.setClipboard(â˜ƒx);
         Util.getPlatform().openUri(â˜ƒx);
      }
   }

   private void checkClientCompatability() {
      if (!checkedClientCompatability) {
         checkedClientCompatability = true;
         (new Thread("MCO Compatability Checker #1") {
               public void run() {
                  RealmsClient â˜ƒ = RealmsClient.create();
   
                  try {
                     RealmsClient.CompatibleVersionResponse â˜ƒx = â˜ƒ.clientCompatible();
                     if (â˜ƒx == RealmsClient.CompatibleVersionResponse.OUTDATED) {
                        RealmsMainScreen.realmsGenericErrorScreen = new RealmsClientOutdatedScreen(RealmsMainScreen.this.lastScreen, true);
                        RealmsMainScreen.this.minecraft.execute(() -> RealmsMainScreen.this.minecraft.setScreen(RealmsMainScreen.realmsGenericErrorScreen));
                        return;
                     }
   
                     if (â˜ƒx == RealmsClient.CompatibleVersionResponse.OTHER) {
                        RealmsMainScreen.realmsGenericErrorScreen = new RealmsClientOutdatedScreen(RealmsMainScreen.this.lastScreen, false);
                        RealmsMainScreen.this.minecraft.execute(() -> RealmsMainScreen.this.minecraft.setScreen(RealmsMainScreen.realmsGenericErrorScreen));
                        return;
                     }
   
                     RealmsMainScreen.this.checkParentalConsent();
                  } catch (RealmsServiceException var3) {
                     RealmsMainScreen.checkedClientCompatability = false;
                     RealmsMainScreen.LOGGER.error("Couldn't connect to realms", var3);
                     if (var3.httpResultCode == 401) {
                        RealmsMainScreen.realmsGenericErrorScreen = new RealmsGenericErrorScreen(
                           new TranslatableComponent("mco.error.invalid.session.title"),
                           new TranslatableComponent("mco.error.invalid.session.message"),
                           RealmsMainScreen.this.lastScreen
                        );
                        RealmsMainScreen.this.minecraft.execute(() -> RealmsMainScreen.this.minecraft.setScreen(RealmsMainScreen.realmsGenericErrorScreen));
                     } else {
                        RealmsMainScreen.this.minecraft
                           .execute(() -> RealmsMainScreen.this.minecraft.setScreen(new RealmsGenericErrorScreen(var3, RealmsMainScreen.this.lastScreen)));
                     }
                  }
               }
            })
            .start();
      }
   }

   private void checkUnreadNews() {
   }

   void checkParentalConsent() {
      (new Thread("MCO Compatability Checker #1") {
            public void run() {
               RealmsClient â˜ƒ = RealmsClient.create();
   
               try {
                  Boolean â˜ƒx = â˜ƒ.mcoEnabled();
                  if (â˜ƒx) {
                     RealmsMainScreen.LOGGER.info("Realms is available for this user");
                     RealmsMainScreen.hasParentalConsent = true;
                  } else {
                     RealmsMainScreen.LOGGER.info("Realms is not available for this user");
                     RealmsMainScreen.hasParentalConsent = false;
                     RealmsMainScreen.this.minecraft
                        .execute(() -> RealmsMainScreen.this.minecraft.setScreen(new RealmsParentalConsentScreen(RealmsMainScreen.this.lastScreen)));
                  }
   
                  RealmsMainScreen.checkedParentalConsent = true;
               } catch (RealmsServiceException var3) {
                  RealmsMainScreen.LOGGER.error("Couldn't connect to realms", var3);
                  RealmsMainScreen.this.minecraft
                     .execute(() -> RealmsMainScreen.this.minecraft.setScreen(new RealmsGenericErrorScreen(var3, RealmsMainScreen.this.lastScreen)));
               }
            }
         })
         .start();
   }

   private void switchToStage() {
      if (RealmsClient.currentEnvironment != RealmsClient.Environment.STAGE) {
         (new Thread("MCO Stage Availability Checker #1") {
            public void run() {
               RealmsClient â˜ƒ = RealmsClient.create();

               try {
                  Boolean â˜ƒx = â˜ƒ.stageAvailable();
                  if (â˜ƒx) {
                     RealmsClient.switchToStage();
                     RealmsMainScreen.LOGGER.info("Switched to stage");
                     RealmsMainScreen.REALMS_DATA_FETCHER.forceUpdate();
                  }
               } catch (RealmsServiceException var3) {
                  RealmsMainScreen.LOGGER.error("Couldn't connect to Realms: {}", var3.toString());
               }
            }
         }).start();
      }
   }

   private void switchToLocal() {
      if (RealmsClient.currentEnvironment != RealmsClient.Environment.LOCAL) {
         (new Thread("MCO Local Availability Checker #1") {
            public void run() {
               RealmsClient â˜ƒ = RealmsClient.create();

               try {
                  Boolean â˜ƒx = â˜ƒ.stageAvailable();
                  if (â˜ƒx) {
                     RealmsClient.switchToLocal();
                     RealmsMainScreen.LOGGER.info("Switched to local");
                     RealmsMainScreen.REALMS_DATA_FETCHER.forceUpdate();
                  }
               } catch (RealmsServiceException var3) {
                  RealmsMainScreen.LOGGER.error("Couldn't connect to Realms: {}", var3.toString());
               }
            }
         }).start();
      }
   }

   private void switchToProd() {
      RealmsClient.switchToProd();
      REALMS_DATA_FETCHER.forceUpdate();
   }

   private void stopRealmsFetcher() {
      REALMS_DATA_FETCHER.stop();
   }

   void configureClicked(@Nullable RealmsServer var1) {
      if (â˜ƒ != null && (this.minecraft.getUser().getUuid().equals(â˜ƒ.ownerUUID) || overrideConfigure)) {
         this.saveListScrollPosition();
         this.minecraft.setScreen(new RealmsConfigureWorldScreen(this, â˜ƒ.id));
      }
   }

   void leaveClicked(@Nullable RealmsServer var1) {
      if (â˜ƒ != null && !this.minecraft.getUser().getUuid().equals(â˜ƒ.ownerUUID)) {
         this.saveListScrollPosition();
         Component â˜ƒ = new TranslatableComponent("mco.configure.world.leave.question.line1");
         Component â˜ƒx = new TranslatableComponent("mco.configure.world.leave.question.line2");
         this.minecraft.setScreen(new RealmsLongConfirmationScreen(this::leaveServer, RealmsLongConfirmationScreen.Type.Info, â˜ƒ, â˜ƒx, true));
      }
   }

   private void saveListScrollPosition() {
      lastScrollYPosition = (int)this.realmSelectionList.getScrollAmount();
   }

   @Nullable
   RealmsServer findServer(long var1) {
      for(RealmsServer â˜ƒ : this.realmsServers) {
         if (â˜ƒ.id == â˜ƒ) {
            return â˜ƒ;
         }
      }

      return null;
   }

   private void leaveServer(boolean var1) {
      if (â˜ƒ) {
         final long â˜ƒ = this.selectedServerId;
         (new Thread("Realms-leave-server") {
               public void run() {
                  try {
                     RealmsServer â˜ƒ = RealmsMainScreen.this.findServer(â˜ƒ);
                     if (â˜ƒ != null) {
                        RealmsClient â˜ƒx = RealmsClient.create();
                        â˜ƒx.uninviteMyselfFrom(â˜ƒ.id);
                        RealmsMainScreen.this.minecraft.execute(() -> RealmsMainScreen.this.removeServer(â˜ƒ));
                     }
                  } catch (RealmsServiceException var3) {
                     RealmsMainScreen.LOGGER.error("Couldn't configure world");
                     RealmsMainScreen.this.minecraft
                        .execute(() -> RealmsMainScreen.this.minecraft.setScreen(new RealmsGenericErrorScreen(var3, RealmsMainScreen.this)));
                  }
               }
            })
            .start();
      }

      this.minecraft.setScreen(this);
   }

   void removeServer(RealmsServer var1) {
      REALMS_DATA_FETCHER.removeItem(â˜ƒ);
      this.realmsServers.remove(â˜ƒ);
      this.realmSelectionList
         .children()
         .removeIf(var1x -> var1x instanceof RealmsMainScreen.ServerEntry && ((RealmsMainScreen.ServerEntry)var1x).serverData.id == this.selectedServerId);
      this.realmSelectionList.setSelected(null);
      this.updateButtonStates(null);
      this.selectedServerId = -1L;
      this.playButton.active = false;
   }

   public void removeSelection() {
      this.selectedServerId = -1L;
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (â˜ƒ == 256) {
         this.keyCombos.forEach(KeyCombo::reset);
         this.onClosePopup();
         return true;
      } else {
         return super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   void onClosePopup() {
      if (this.shouldShowPopup() && this.popupOpenedByUser) {
         this.popupOpenedByUser = false;
      } else {
         this.minecraft.setScreen(this.lastScreen);
      }
   }

   @Override
   public boolean charTyped(char var1, int var2) {
      this.keyCombos.forEach(var1x -> var1x.keyPressed(â˜ƒ));
      return true;
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.hoveredElement = RealmsMainScreen.HoveredElement.NONE;
      this.toolTip = null;
      this.renderBackground(â˜ƒ);
      this.realmSelectionList.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.drawRealmsLogo(â˜ƒ, this.width / 2 - 50, 7);
      if (RealmsClient.currentEnvironment == RealmsClient.Environment.STAGE) {
         this.renderStage(â˜ƒ);
      }

      if (RealmsClient.currentEnvironment == RealmsClient.Environment.LOCAL) {
         this.renderLocal(â˜ƒ);
      }

      if (this.shouldShowPopup()) {
         this.drawPopup(â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         if (this.showingPopup) {
            this.updateButtonStates(null);
            if (!this.realmsSelectionListAdded) {
               this.addWidget(this.realmSelectionList);
               this.realmsSelectionListAdded = true;
            }

            RealmsServer â˜ƒ = this.findServer(this.selectedServerId);
            this.playButton.active = this.shouldPlayButtonBeActive(â˜ƒ);
         }

         this.showingPopup = false;
      }

      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (this.toolTip != null) {
         this.renderMousehoverTooltip(â˜ƒ, this.toolTip, â˜ƒ, â˜ƒ);
      }

      if (this.trialsAvailable && !this.createdTrial && this.shouldShowPopup()) {
         RenderSystem.setShaderTexture(0, TRIAL_ICON_LOCATION);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         int â˜ƒ = 8;
         int â˜ƒx = 8;
         int â˜ƒxx = 0;
         if ((Util.getMillis() / 800L & 1L) == 1L) {
            â˜ƒxx = 8;
         }

         GuiComponent.blit(
            â˜ƒ,
            this.createTrialButton.x + this.createTrialButton.getWidth() - 8 - 4,
            this.createTrialButton.y + this.createTrialButton.getHeight() / 2 - 4,
            0.0F,
            (float)â˜ƒxx,
            8,
            8,
            8,
            16
         );
      }
   }

   private void drawRealmsLogo(PoseStack var1, int var2, int var3) {
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderTexture(0, LOGO_LOCATION);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      â˜ƒ.pushPose();
      â˜ƒ.scale(0.5F, 0.5F, 0.5F);
      GuiComponent.blit(â˜ƒ, â˜ƒ * 2, â˜ƒ * 2 - 5, 0.0F, 0.0F, 200, 50, 200, 50);
      â˜ƒ.popPose();
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (this.isOutsidePopup(â˜ƒ, â˜ƒ) && this.popupOpenedByUser) {
         this.popupOpenedByUser = false;
         this.justClosedPopup = true;
         return true;
      } else {
         return super.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private boolean isOutsidePopup(double var1, double var3) {
      int â˜ƒ = this.popupX0();
      int â˜ƒx = this.popupY0();
      return â˜ƒ < (double)(â˜ƒ - 5) || â˜ƒ > (double)(â˜ƒ + 315) || â˜ƒ < (double)(â˜ƒx - 5) || â˜ƒ > (double)(â˜ƒx + 171);
   }

   private void drawPopup(PoseStack var1, int var2, int var3) {
      int â˜ƒ = this.popupX0();
      int â˜ƒx = this.popupY0();
      if (!this.showingPopup) {
         this.carouselIndex = 0;
         this.carouselTick = 0;
         this.hasSwitchedCarouselImage = true;
         this.updateButtonStates(null);
         if (this.realmsSelectionListAdded) {
            this.removeWidget(this.realmSelectionList);
            this.realmsSelectionListAdded = false;
         }

         NarratorChatListener.INSTANCE.sayNow(POPUP_TEXT);
      }

      if (this.hasFetchedServers) {
         this.showingPopup = true;
      }

      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.7F);
      RenderSystem.enableBlend();
      RenderSystem.setShaderTexture(0, DARKEN_LOCATION);
      int â˜ƒ = 0;
      int â˜ƒx = 32;
      GuiComponent.blit(â˜ƒ, 0, 32, 0.0F, 0.0F, this.width, this.height - 40 - 32, 310, 166);
      RenderSystem.disableBlend();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShaderTexture(0, POPUP_LOCATION);
      GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒx, 0.0F, 0.0F, 310, 166, 310, 166);
      if (!teaserImages.isEmpty()) {
         RenderSystem.setShaderTexture(0, (ResourceLocation)teaserImages.get(this.carouselIndex));
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         GuiComponent.blit(â˜ƒ, â˜ƒ + 7, â˜ƒx + 7, 0.0F, 0.0F, 195, 152, 195, 152);
         if (this.carouselTick % 95 < 5) {
            if (!this.hasSwitchedCarouselImage) {
               this.carouselIndex = (this.carouselIndex + 1) % teaserImages.size();
               this.hasSwitchedCarouselImage = true;
            }
         } else {
            this.hasSwitchedCarouselImage = false;
         }
      }

      this.formattedPopup.renderLeftAlignedNoShadow(â˜ƒ, this.width / 2 + 52, â˜ƒx + 7, 10, 5000268);
   }

   int popupX0() {
      return (this.width - 310) / 2;
   }

   int popupY0() {
      return this.height / 2 - 80;
   }

   void drawInvitationPendingIcon(PoseStack var1, int var2, int var3, int var4, int var5, boolean var6, boolean var7) {
      int â˜ƒ = this.numberOfPendingInvites;
      boolean â˜ƒx = this.inPendingInvitationArea((double)â˜ƒ, (double)â˜ƒ);
      boolean â˜ƒxx = â˜ƒ && â˜ƒ;
      if (â˜ƒxx) {
         float â˜ƒxxx = 0.25F + (1.0F + Mth.sin((float)this.animTick * 0.5F)) * 0.25F;
         int â˜ƒxxxx = 0xFF000000 | (int)(â˜ƒxxx * 64.0F) << 16 | (int)(â˜ƒxxx * 64.0F) << 8 | (int)(â˜ƒxxx * 64.0F) << 0;
         this.fillGradient(â˜ƒ, â˜ƒ - 2, â˜ƒ - 2, â˜ƒ + 18, â˜ƒ + 18, â˜ƒxxxx, â˜ƒxxxx);
         â˜ƒxxxx = 0xFF000000 | (int)(â˜ƒxxx * 255.0F) << 16 | (int)(â˜ƒxxx * 255.0F) << 8 | (int)(â˜ƒxxx * 255.0F) << 0;
         this.fillGradient(â˜ƒ, â˜ƒ - 2, â˜ƒ - 2, â˜ƒ + 18, â˜ƒ - 1, â˜ƒxxxx, â˜ƒxxxx);
         this.fillGradient(â˜ƒ, â˜ƒ - 2, â˜ƒ - 2, â˜ƒ - 1, â˜ƒ + 18, â˜ƒxxxx, â˜ƒxxxx);
         this.fillGradient(â˜ƒ, â˜ƒ + 17, â˜ƒ - 2, â˜ƒ + 18, â˜ƒ + 18, â˜ƒxxxx, â˜ƒxxxx);
         this.fillGradient(â˜ƒ, â˜ƒ - 2, â˜ƒ + 17, â˜ƒ + 18, â˜ƒ + 18, â˜ƒxxxx, â˜ƒxxxx);
      }

      RenderSystem.setShaderTexture(0, INVITE_ICON_LOCATION);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      boolean â˜ƒ = â˜ƒ && â˜ƒ;
      float â˜ƒx = â˜ƒ ? 16.0F : 0.0F;
      GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ - 6, â˜ƒx, 0.0F, 15, 25, 31, 25);
      boolean â˜ƒxx = â˜ƒ && â˜ƒ != 0;
      if (â˜ƒxx) {
         int â˜ƒxxx = (Math.min(â˜ƒ, 6) - 1) * 8;
         int â˜ƒxxxx = (int)(Math.max(0.0F, Math.max(Mth.sin((float)(10 + this.animTick) * 0.57F), Mth.cos((float)this.animTick * 0.35F))) * -6.0F);
         RenderSystem.setShaderTexture(0, INVITATION_ICONS_LOCATION);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         float â˜ƒxxxxx = â˜ƒx ? 8.0F : 0.0F;
         GuiComponent.blit(â˜ƒ, â˜ƒ + 4, â˜ƒ + 4 + â˜ƒxxxx, (float)â˜ƒxxx, â˜ƒxxxxx, 8, 8, 48, 16);
      }

      int â˜ƒ = â˜ƒ + 12;
      boolean â˜ƒx = â˜ƒ && â˜ƒx;
      if (â˜ƒx) {
         Component â˜ƒxx = â˜ƒ == 0 ? NO_PENDING_INVITES_TEXT : PENDING_INVITES_TEXT;
         int â˜ƒxxx = this.font.width(â˜ƒxx);
         this.fillGradient(â˜ƒ, â˜ƒ - 3, â˜ƒ - 3, â˜ƒ + â˜ƒxxx + 3, â˜ƒ + 8 + 3, -1073741824, -1073741824);
         this.font.drawShadow(â˜ƒ, â˜ƒxx, (float)â˜ƒ, (float)â˜ƒ, -1);
      }
   }

   private boolean inPendingInvitationArea(double var1, double var3) {
      int â˜ƒ = this.width / 2 + 50;
      int â˜ƒx = this.width / 2 + 66;
      int â˜ƒxx = 11;
      int â˜ƒxxx = 23;
      if (this.numberOfPendingInvites != 0) {
         â˜ƒ -= 3;
         â˜ƒx += 3;
         â˜ƒxx -= 5;
         â˜ƒxxx += 5;
      }

      return (double)â˜ƒ <= â˜ƒ && â˜ƒ <= (double)â˜ƒx && (double)â˜ƒxx <= â˜ƒ && â˜ƒ <= (double)â˜ƒxxx;
   }

   public void play(@Nullable RealmsServer var1, Screen var2) {
      if (â˜ƒ != null) {
         try {
            if (!this.connectLock.tryLock(1L, TimeUnit.SECONDS)) {
               return;
            }

            if (this.connectLock.getHoldCount() > 1) {
               return;
            }
         } catch (InterruptedException var4) {
            return;
         }

         this.dontSetConnectedToRealms = true;
         this.minecraft.setScreen(new RealmsLongRunningMcoTaskScreen(â˜ƒ, new GetServerDetailsTask(this, â˜ƒ, â˜ƒ, this.connectLock)));
      }
   }

   boolean isSelfOwnedServer(RealmsServer var1) {
      return â˜ƒ.ownerUUID != null && â˜ƒ.ownerUUID.equals(this.minecraft.getUser().getUuid());
   }

   private boolean isSelfOwnedNonExpiredServer(RealmsServer var1) {
      return this.isSelfOwnedServer(â˜ƒ) && !â˜ƒ.expired;
   }

   void drawExpired(PoseStack var1, int var2, int var3, int var4, int var5) {
      RenderSystem.setShaderTexture(0, EXPIRED_ICON_LOCATION);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 0.0F, 10, 28, 10, 28);
      if (â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒ + 9 && â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒ + 27 && â˜ƒ < this.height - 40 && â˜ƒ > 32 && !this.shouldShowPopup()) {
         this.setTooltip(SERVER_EXPIRED_TOOLTIP);
      }
   }

   void drawExpiring(PoseStack var1, int var2, int var3, int var4, int var5, int var6) {
      RenderSystem.setShaderTexture(0, EXPIRES_SOON_ICON_LOCATION);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      if (this.animTick % 20 < 10) {
         GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 0.0F, 10, 28, 20, 28);
      } else {
         GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 10.0F, 0.0F, 10, 28, 20, 28);
      }

      if (â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒ + 9 && â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒ + 27 && â˜ƒ < this.height - 40 && â˜ƒ > 32 && !this.shouldShowPopup()) {
         if (â˜ƒ <= 0) {
            this.setTooltip(SERVER_EXPIRES_SOON_TOOLTIP);
         } else if (â˜ƒ == 1) {
            this.setTooltip(SERVER_EXPIRES_IN_DAY_TOOLTIP);
         } else {
            this.setTooltip(new TranslatableComponent("mco.selectServer.expires.days", â˜ƒ));
         }
      }
   }

   void drawOpen(PoseStack var1, int var2, int var3, int var4, int var5) {
      RenderSystem.setShaderTexture(0, ON_ICON_LOCATION);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 0.0F, 10, 28, 10, 28);
      if (â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒ + 9 && â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒ + 27 && â˜ƒ < this.height - 40 && â˜ƒ > 32 && !this.shouldShowPopup()) {
         this.setTooltip(SERVER_OPEN_TOOLTIP);
      }
   }

   void drawClose(PoseStack var1, int var2, int var3, int var4, int var5) {
      RenderSystem.setShaderTexture(0, OFF_ICON_LOCATION);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 0.0F, 10, 28, 10, 28);
      if (â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒ + 9 && â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒ + 27 && â˜ƒ < this.height - 40 && â˜ƒ > 32 && !this.shouldShowPopup()) {
         this.setTooltip(SERVER_CLOSED_TOOLTIP);
      }
   }

   void drawLeave(PoseStack var1, int var2, int var3, int var4, int var5) {
      boolean â˜ƒ = false;
      if (â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒ + 28 && â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒ + 28 && â˜ƒ < this.height - 40 && â˜ƒ > 32 && !this.shouldShowPopup()) {
         â˜ƒ = true;
      }

      RenderSystem.setShaderTexture(0, LEAVE_ICON_LOCATION);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      float â˜ƒ = â˜ƒ ? 28.0F : 0.0F;
      GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 28, 28, 56, 28);
      if (â˜ƒ) {
         this.setTooltip(LEAVE_SERVER_TOOLTIP);
         this.hoveredElement = RealmsMainScreen.HoveredElement.LEAVE;
      }
   }

   void drawConfigure(PoseStack var1, int var2, int var3, int var4, int var5) {
      boolean â˜ƒ = false;
      if (â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒ + 28 && â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒ + 28 && â˜ƒ < this.height - 40 && â˜ƒ > 32 && !this.shouldShowPopup()) {
         â˜ƒ = true;
      }

      RenderSystem.setShaderTexture(0, CONFIGURE_LOCATION);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      float â˜ƒ = â˜ƒ ? 28.0F : 0.0F;
      GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 28, 28, 56, 28);
      if (â˜ƒ) {
         this.setTooltip(CONFIGURE_SERVER_TOOLTIP);
         this.hoveredElement = RealmsMainScreen.HoveredElement.CONFIGURE;
      }
   }

   protected void renderMousehoverTooltip(PoseStack var1, List<Component> var2, int var3, int var4) {
      if (!â˜ƒ.isEmpty()) {
         int â˜ƒ = 0;
         int â˜ƒx = 0;

         for(Component â˜ƒxx : â˜ƒ) {
            int â˜ƒxxx = this.font.width(â˜ƒxx);
            if (â˜ƒxxx > â˜ƒx) {
               â˜ƒx = â˜ƒxxx;
            }
         }

         int â˜ƒxx = â˜ƒ - â˜ƒx - 5;
         int â˜ƒxxx = â˜ƒ;
         if (â˜ƒxx < 0) {
            â˜ƒxx = â˜ƒ + 12;
         }

         for(Component â˜ƒxx : â˜ƒ) {
            int â˜ƒxxx = â˜ƒxxx - (â˜ƒ == 0 ? 3 : 0) + â˜ƒ;
            this.fillGradient(â˜ƒ, â˜ƒxx - 3, â˜ƒxxx, â˜ƒxx + â˜ƒx + 3, â˜ƒxxx + 8 + 3 + â˜ƒ, -1073741824, -1073741824);
            this.font.drawShadow(â˜ƒ, â˜ƒxx, (float)â˜ƒxx, (float)(â˜ƒxxx + â˜ƒ), 16777215);
            â˜ƒ += 10;
         }
      }
   }

   void renderMoreInfo(PoseStack var1, int var2, int var3, int var4, int var5, boolean var6) {
      boolean â˜ƒ = false;
      if (â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒ + 20 && â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒ + 20) {
         â˜ƒ = true;
      }

      RenderSystem.setShaderTexture(0, QUESTIONMARK_LOCATION);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      float â˜ƒ = â˜ƒ ? 20.0F : 0.0F;
      GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 20, 20, 40, 20);
      if (â˜ƒ) {
         this.setTooltip(SERVER_INFO_TOOLTIP);
      }
   }

   void renderNews(PoseStack var1, int var2, int var3, boolean var4, int var5, int var6, boolean var7, boolean var8) {
      boolean â˜ƒ = false;
      if (â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒ + 20 && â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒ + 20) {
         â˜ƒ = true;
      }

      RenderSystem.setShaderTexture(0, NEWS_LOCATION);
      if (â˜ƒ) {
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      } else {
         RenderSystem.setShaderColor(0.5F, 0.5F, 0.5F, 1.0F);
      }

      boolean â˜ƒ = â˜ƒ && â˜ƒ;
      float â˜ƒx = â˜ƒ ? 20.0F : 0.0F;
      GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, 0.0F, 20, 20, 40, 20);
      if (â˜ƒ && â˜ƒ) {
         this.setTooltip(NEWS_TOOLTIP);
      }

      if (â˜ƒ && â˜ƒ) {
         int â˜ƒ = â˜ƒ ? 0 : (int)(Math.max(0.0F, Math.max(Mth.sin((float)(10 + this.animTick) * 0.57F), Mth.cos((float)this.animTick * 0.35F))) * -6.0F);
         RenderSystem.setShaderTexture(0, INVITATION_ICONS_LOCATION);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         GuiComponent.blit(â˜ƒ, â˜ƒ + 10, â˜ƒ + 2 + â˜ƒ, 40.0F, 0.0F, 8, 8, 48, 16);
      }
   }

   private void renderLocal(PoseStack var1) {
      String â˜ƒ = "LOCAL!";
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      â˜ƒ.pushPose();
      â˜ƒ.translate((double)(this.width / 2 - 25), 20.0, 0.0);
      â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(-20.0F));
      â˜ƒ.scale(1.5F, 1.5F, 1.5F);
      this.font.draw(â˜ƒ, "LOCAL!", 0.0F, 0.0F, 8388479);
      â˜ƒ.popPose();
   }

   private void renderStage(PoseStack var1) {
      String â˜ƒ = "STAGE!";
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      â˜ƒ.pushPose();
      â˜ƒ.translate((double)(this.width / 2 - 25), 20.0, 0.0);
      â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(-20.0F));
      â˜ƒ.scale(1.5F, 1.5F, 1.5F);
      this.font.draw(â˜ƒ, "STAGE!", 0.0F, 0.0F, -256);
      â˜ƒ.popPose();
   }

   public RealmsMainScreen newScreen() {
      RealmsMainScreen â˜ƒ = new RealmsMainScreen(this.lastScreen);
      â˜ƒ.init(this.minecraft, this.width, this.height);
      return â˜ƒ;
   }

   public void closePopup() {
      if (this.shouldShowPopup() && this.popupOpenedByUser) {
         this.popupOpenedByUser = false;
      }
   }

   public static void updateTeaserImages(ResourceManager var0) {
      Collection<ResourceLocation> â˜ƒ = â˜ƒ.listResources("textures/gui/images", var0x -> var0x.endsWith(".png"));
      teaserImages = (List)â˜ƒ.stream().filter(var0x -> var0x.getNamespace().equals("realms")).collect(ImmutableList.toImmutableList());
   }

   void setTooltip(Component... var1) {
      this.toolTip = Arrays.asList(â˜ƒ);
   }

   private void setTooltip(Iterable<Component> var1) {
      this.toolTip = ImmutableList.copyOf(â˜ƒ);
   }

   private void pendingButtonPress(Button var1) {
      this.minecraft.setScreen(new RealmsPendingInvitesScreen(this.lastScreen));
   }

   class CloseButton extends Button {
      public CloseButton() {
         super(
            RealmsMainScreen.this.popupX0() + 4,
            RealmsMainScreen.this.popupY0() + 4,
            12,
            12,
            new TranslatableComponent("mco.selectServer.close"),
            var1x -> RealmsMainScreen.this.onClosePopup()
         );
      }

      @Override
      public void renderButton(PoseStack var1, int var2, int var3, float var4) {
         RenderSystem.setShaderTexture(0, RealmsMainScreen.CROSS_ICON_LOCATION);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         float â˜ƒ = this.isHovered() ? 12.0F : 0.0F;
         blit(â˜ƒ, this.x, this.y, 0.0F, â˜ƒ, 12, 12, 12, 24);
         if (this.isMouseOver((double)â˜ƒ, (double)â˜ƒ)) {
            RealmsMainScreen.this.setTooltip(this.getMessage());
         }
      }
   }

   abstract class Entry extends ObjectSelectionList.Entry<RealmsMainScreen.Entry> {
   }

   static enum HoveredElement {
      NONE,
      EXPIRED,
      LEAVE,
      CONFIGURE;
   }

   class NewsButton extends Button {
      public NewsButton() {
         super(RealmsMainScreen.this.width - 62, 6, 20, 20, new TranslatableComponent("mco.news"), var1x -> {
            if (RealmsMainScreen.this.newsLink != null) {
               Util.getPlatform().openUri(RealmsMainScreen.this.newsLink);
               if (RealmsMainScreen.this.hasUnreadNews) {
                  RealmsPersistence.RealmsPersistenceData â˜ƒ = RealmsPersistence.readFile();
                  â˜ƒ.hasUnreadNews = false;
                  RealmsMainScreen.this.hasUnreadNews = false;
                  RealmsPersistence.writeFile(â˜ƒ);
               }
            }
         });
      }

      @Override
      public void renderButton(PoseStack var1, int var2, int var3, float var4) {
         RealmsMainScreen.this.renderNews(â˜ƒ, â˜ƒ, â˜ƒ, RealmsMainScreen.this.hasUnreadNews, this.x, this.y, this.isHovered(), this.active);
      }
   }

   class PendingInvitesButton extends Button {
      public PendingInvitesButton() {
         super(RealmsMainScreen.this.width / 2 + 47, 6, 22, 22, TextComponent.EMPTY, RealmsMainScreen.this::pendingButtonPress);
      }

      public void tick() {
         this.setMessage(RealmsMainScreen.this.numberOfPendingInvites == 0 ? RealmsMainScreen.NO_PENDING_INVITES_TEXT : RealmsMainScreen.PENDING_INVITES_TEXT);
      }

      @Override
      public void renderButton(PoseStack var1, int var2, int var3, float var4) {
         RealmsMainScreen.this.drawInvitationPendingIcon(â˜ƒ, â˜ƒ, â˜ƒ, this.x, this.y, this.isHovered(), this.active);
      }
   }

   class RealmSelectionList extends RealmsObjectSelectionList<RealmsMainScreen.Entry> {
      private boolean showingMessage;

      public RealmSelectionList() {
         super(RealmsMainScreen.this.width, RealmsMainScreen.this.height, 32, RealmsMainScreen.this.height - 40, 36);
      }

      @Override
      public void clear() {
         super.clear();
         this.showingMessage = false;
      }

      public int addMessageEntry(RealmsMainScreen.Entry var1) {
         this.showingMessage = true;
         return this.addEntry(â˜ƒ);
      }

      @Override
      public boolean isFocused() {
         return RealmsMainScreen.this.getFocused() == this;
      }

      @Override
      public boolean keyPressed(int var1, int var2, int var3) {
         if (â˜ƒ != 257 && â˜ƒ != 32 && â˜ƒ != 335) {
            return super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ);
         } else {
            RealmsMainScreen.Entry â˜ƒ = this.getSelected();
            return â˜ƒ == null ? super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ) : â˜ƒ.mouseClicked(0.0, 0.0, 0);
         }
      }

      @Override
      public boolean mouseClicked(double var1, double var3, int var5) {
         if (â˜ƒ == 0 && â˜ƒ < (double)this.getScrollbarPosition() && â˜ƒ >= (double)this.y0 && â˜ƒ <= (double)this.y1) {
            int â˜ƒ = RealmsMainScreen.this.realmSelectionList.getRowLeft();
            int â˜ƒx = this.getScrollbarPosition();
            int â˜ƒxx = (int)Math.floor(â˜ƒ - (double)this.y0) - this.headerHeight + (int)this.getScrollAmount() - 4;
            int â˜ƒxxx = â˜ƒxx / this.itemHeight;
            if (â˜ƒ >= (double)â˜ƒ && â˜ƒ <= (double)â˜ƒx && â˜ƒxxx >= 0 && â˜ƒxx >= 0 && â˜ƒxxx < this.getItemCount()) {
               this.itemClicked(â˜ƒxx, â˜ƒxxx, â˜ƒ, â˜ƒ, this.width);
               RealmsMainScreen.this.clicks += 7;
               this.selectItem(â˜ƒxxx);
            }

            return true;
         } else {
            return super.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }

      @Override
      public void selectItem(int var1) {
         this.setSelectedItem(â˜ƒ);
         if (â˜ƒ != -1) {
            RealmsServer â˜ƒ;
            if (this.showingMessage) {
               if (â˜ƒ == 0) {
                  â˜ƒ = null;
               } else {
                  if (â˜ƒ - 1 >= RealmsMainScreen.this.realmsServers.size()) {
                     RealmsMainScreen.this.selectedServerId = -1L;
                     return;
                  }

                  â˜ƒ = (RealmsServer)RealmsMainScreen.this.realmsServers.get(â˜ƒ - 1);
               }
            } else {
               if (â˜ƒ >= RealmsMainScreen.this.realmsServers.size()) {
                  RealmsMainScreen.this.selectedServerId = -1L;
                  return;
               }

               â˜ƒ = (RealmsServer)RealmsMainScreen.this.realmsServers.get(â˜ƒ);
            }

            RealmsMainScreen.this.updateButtonStates(â˜ƒ);
            if (â˜ƒ == null) {
               RealmsMainScreen.this.selectedServerId = -1L;
            } else if (â˜ƒ.state == RealmsServer.State.UNINITIALIZED) {
               RealmsMainScreen.this.selectedServerId = -1L;
            } else {
               RealmsMainScreen.this.selectedServerId = â˜ƒ.id;
               if (RealmsMainScreen.this.clicks >= 10 && RealmsMainScreen.this.playButton.active) {
                  RealmsMainScreen.this.play(RealmsMainScreen.this.findServer(RealmsMainScreen.this.selectedServerId), RealmsMainScreen.this);
               }
            }
         }
      }

      public void setSelected(@Nullable RealmsMainScreen.Entry var1) {
         super.setSelected(â˜ƒ);
         int â˜ƒ = this.children().indexOf(â˜ƒ) - (this.showingMessage ? 1 : 0);
         if (â˜ƒ >= 0 && â˜ƒ < RealmsMainScreen.this.realmsServers.size()) {
            RealmsServer â˜ƒx = (RealmsServer)RealmsMainScreen.this.realmsServers.get(â˜ƒ);
            RealmsMainScreen.this.selectedServerId = â˜ƒx.id;
            RealmsMainScreen.this.updateButtonStates(â˜ƒx);
         }
      }

      @Override
      public void itemClicked(int var1, int var2, double var3, double var5, int var7) {
         if (this.showingMessage) {
            if (â˜ƒ == 0) {
               RealmsMainScreen.this.popupOpenedByUser = true;
               return;
            }

            --â˜ƒ;
         }

         if (â˜ƒ < RealmsMainScreen.this.realmsServers.size()) {
            RealmsServer â˜ƒ = (RealmsServer)RealmsMainScreen.this.realmsServers.get(â˜ƒ);
            if (â˜ƒ != null) {
               if (â˜ƒ.state == RealmsServer.State.UNINITIALIZED) {
                  RealmsMainScreen.this.selectedServerId = -1L;
                  Minecraft.getInstance().setScreen(new RealmsCreateRealmScreen(â˜ƒ, RealmsMainScreen.this));
               } else {
                  RealmsMainScreen.this.selectedServerId = â˜ƒ.id;
               }

               if (RealmsMainScreen.this.hoveredElement == RealmsMainScreen.HoveredElement.CONFIGURE) {
                  RealmsMainScreen.this.selectedServerId = â˜ƒ.id;
                  RealmsMainScreen.this.configureClicked(â˜ƒ);
               } else if (RealmsMainScreen.this.hoveredElement == RealmsMainScreen.HoveredElement.LEAVE) {
                  RealmsMainScreen.this.selectedServerId = â˜ƒ.id;
                  RealmsMainScreen.this.leaveClicked(â˜ƒ);
               } else if (RealmsMainScreen.this.hoveredElement == RealmsMainScreen.HoveredElement.EXPIRED) {
                  RealmsMainScreen.this.onRenew();
               }
            }
         }
      }

      @Override
      public int getMaxPosition() {
         return this.getItemCount() * 36;
      }

      @Override
      public int getRowWidth() {
         return 300;
      }
   }

   class ServerEntry extends RealmsMainScreen.Entry {
      private static final int SKIN_HEAD_LARGE_WIDTH = 36;
      final RealmsServer serverData;

      public ServerEntry(RealmsServer var2) {
         this.serverData = â˜ƒ;
      }

      @Override
      public void render(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         this.renderMcoServerItem(this.serverData, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public boolean mouseClicked(double var1, double var3, int var5) {
         if (this.serverData.state == RealmsServer.State.UNINITIALIZED) {
            RealmsMainScreen.this.selectedServerId = -1L;
            RealmsMainScreen.this.minecraft.setScreen(new RealmsCreateRealmScreen(this.serverData, RealmsMainScreen.this));
         } else {
            RealmsMainScreen.this.selectedServerId = this.serverData.id;
         }

         return true;
      }

      private void renderMcoServerItem(RealmsServer var1, PoseStack var2, int var3, int var4, int var5, int var6) {
         this.renderLegacy(â˜ƒ, â˜ƒ, â˜ƒ + 36, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      private void renderLegacy(RealmsServer var1, PoseStack var2, int var3, int var4, int var5, int var6) {
         if (â˜ƒ.state == RealmsServer.State.UNINITIALIZED) {
            RenderSystem.setShaderTexture(0, RealmsMainScreen.WORLDICON_LOCATION);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            GuiComponent.blit(â˜ƒ, â˜ƒ + 10, â˜ƒ + 6, 0.0F, 0.0F, 40, 20, 40, 20);
            float â˜ƒ = 0.5F + (1.0F + Mth.sin((float)RealmsMainScreen.this.animTick * 0.25F)) * 0.25F;
            int â˜ƒx = 0xFF000000 | (int)(127.0F * â˜ƒ) << 16 | (int)(255.0F * â˜ƒ) << 8 | (int)(127.0F * â˜ƒ);
            GuiComponent.drawCenteredString(â˜ƒ, RealmsMainScreen.this.font, RealmsMainScreen.SERVER_UNITIALIZED_TEXT, â˜ƒ + 10 + 40 + 75, â˜ƒ + 12, â˜ƒx);
         } else {
            int â˜ƒ = 225;
            int â˜ƒx = 2;
            if (â˜ƒ.expired) {
               RealmsMainScreen.this.drawExpired(â˜ƒ, â˜ƒ + 225 - 14, â˜ƒ + 2, â˜ƒ, â˜ƒ);
            } else if (â˜ƒ.state == RealmsServer.State.CLOSED) {
               RealmsMainScreen.this.drawClose(â˜ƒ, â˜ƒ + 225 - 14, â˜ƒ + 2, â˜ƒ, â˜ƒ);
            } else if (RealmsMainScreen.this.isSelfOwnedServer(â˜ƒ) && â˜ƒ.daysLeft < 7) {
               RealmsMainScreen.this.drawExpiring(â˜ƒ, â˜ƒ + 225 - 14, â˜ƒ + 2, â˜ƒ, â˜ƒ, â˜ƒ.daysLeft);
            } else if (â˜ƒ.state == RealmsServer.State.OPEN) {
               RealmsMainScreen.this.drawOpen(â˜ƒ, â˜ƒ + 225 - 14, â˜ƒ + 2, â˜ƒ, â˜ƒ);
            }

            if (!RealmsMainScreen.this.isSelfOwnedServer(â˜ƒ) && !RealmsMainScreen.overrideConfigure) {
               RealmsMainScreen.this.drawLeave(â˜ƒ, â˜ƒ + 225, â˜ƒ + 2, â˜ƒ, â˜ƒ);
            } else {
               RealmsMainScreen.this.drawConfigure(â˜ƒ, â˜ƒ + 225, â˜ƒ + 2, â˜ƒ, â˜ƒ);
            }

            if (!"0".equals(â˜ƒ.serverPing.nrOfPlayers)) {
               String â˜ƒ = ChatFormatting.GRAY + â˜ƒ.serverPing.nrOfPlayers;
               RealmsMainScreen.this.font.draw(â˜ƒ, â˜ƒ, (float)(â˜ƒ + 207 - RealmsMainScreen.this.font.width(â˜ƒ)), (float)(â˜ƒ + 3), 8421504);
               if (â˜ƒ >= â˜ƒ + 207 - RealmsMainScreen.this.font.width(â˜ƒ)
                  && â˜ƒ <= â˜ƒ + 207
                  && â˜ƒ >= â˜ƒ + 1
                  && â˜ƒ <= â˜ƒ + 10
                  && â˜ƒ < RealmsMainScreen.this.height - 40
                  && â˜ƒ > 32
                  && !RealmsMainScreen.this.shouldShowPopup()) {
                  RealmsMainScreen.this.setTooltip(new TextComponent(â˜ƒ.serverPing.playerList));
               }
            }

            if (RealmsMainScreen.this.isSelfOwnedServer(â˜ƒ) && â˜ƒ.expired) {
               RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
               RenderSystem.enableBlend();
               RenderSystem.setShaderTexture(0, RealmsMainScreen.BUTTON_LOCATION);
               RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
               Component â˜ƒ;
               Component â˜ƒx;
               if (â˜ƒ.expiredTrial) {
                  â˜ƒ = RealmsMainScreen.TRIAL_EXPIRED_TEXT;
                  â˜ƒx = RealmsMainScreen.SUBSCRIPTION_CREATE_TEXT;
               } else {
                  â˜ƒ = RealmsMainScreen.SUBSCRIPTION_EXPIRED_TEXT;
                  â˜ƒx = RealmsMainScreen.SUBSCRIPTION_RENEW_TEXT;
               }

               int â˜ƒ = RealmsMainScreen.this.font.width(â˜ƒx) + 17;
               int â˜ƒx = 16;
               int â˜ƒxx = â˜ƒ + RealmsMainScreen.this.font.width(â˜ƒ) + 8;
               int â˜ƒxxx = â˜ƒ + 13;
               boolean â˜ƒxxxx = false;
               if (â˜ƒ >= â˜ƒxx
                  && â˜ƒ < â˜ƒxx + â˜ƒ
                  && â˜ƒ > â˜ƒxxx
                  && â˜ƒ <= â˜ƒxxx + 16
                  && â˜ƒ < RealmsMainScreen.this.height - 40
                  && â˜ƒ > 32
                  && !RealmsMainScreen.this.shouldShowPopup()) {
                  â˜ƒxxxx = true;
                  RealmsMainScreen.this.hoveredElement = RealmsMainScreen.HoveredElement.EXPIRED;
               }

               int â˜ƒ = â˜ƒxxxx ? 2 : 1;
               GuiComponent.blit(â˜ƒ, â˜ƒxx, â˜ƒxxx, 0.0F, (float)(46 + â˜ƒ * 20), â˜ƒ / 2, 8, 256, 256);
               GuiComponent.blit(â˜ƒ, â˜ƒxx + â˜ƒ / 2, â˜ƒxxx, (float)(200 - â˜ƒ / 2), (float)(46 + â˜ƒ * 20), â˜ƒ / 2, 8, 256, 256);
               GuiComponent.blit(â˜ƒ, â˜ƒxx, â˜ƒxxx + 8, 0.0F, (float)(46 + â˜ƒ * 20 + 12), â˜ƒ / 2, 8, 256, 256);
               GuiComponent.blit(â˜ƒ, â˜ƒxx + â˜ƒ / 2, â˜ƒxxx + 8, (float)(200 - â˜ƒ / 2), (float)(46 + â˜ƒ * 20 + 12), â˜ƒ / 2, 8, 256, 256);
               RenderSystem.disableBlend();
               int â˜ƒx = â˜ƒ + 11 + 5;
               int â˜ƒxx = â˜ƒxxxx ? 16777120 : 16777215;
               RealmsMainScreen.this.font.draw(â˜ƒ, â˜ƒ, (float)(â˜ƒ + 2), (float)(â˜ƒx + 1), 15553363);
               GuiComponent.drawCenteredString(â˜ƒ, RealmsMainScreen.this.font, â˜ƒx, â˜ƒxx + â˜ƒ / 2, â˜ƒx + 1, â˜ƒxx);
            } else {
               if (â˜ƒ.worldType == RealmsServer.WorldType.MINIGAME) {
                  int â˜ƒ = 13413468;
                  int â˜ƒx = RealmsMainScreen.this.font.width(RealmsMainScreen.SELECT_MINIGAME_PREFIX);
                  RealmsMainScreen.this.font.draw(â˜ƒ, RealmsMainScreen.SELECT_MINIGAME_PREFIX, (float)(â˜ƒ + 2), (float)(â˜ƒ + 12), 13413468);
                  RealmsMainScreen.this.font.draw(â˜ƒ, â˜ƒ.getMinigameName(), (float)(â˜ƒ + 2 + â˜ƒx), (float)(â˜ƒ + 12), 7105644);
               } else {
                  RealmsMainScreen.this.font.draw(â˜ƒ, â˜ƒ.getDescription(), (float)(â˜ƒ + 2), (float)(â˜ƒ + 12), 7105644);
               }

               if (!RealmsMainScreen.this.isSelfOwnedServer(â˜ƒ)) {
                  RealmsMainScreen.this.font.draw(â˜ƒ, â˜ƒ.owner, (float)(â˜ƒ + 2), (float)(â˜ƒ + 12 + 11), 5000268);
               }
            }

            RealmsMainScreen.this.font.draw(â˜ƒ, â˜ƒ.getName(), (float)(â˜ƒ + 2), (float)(â˜ƒ + 1), 16777215);
            RealmsTextureManager.withBoundFace(â˜ƒ.ownerUUID, () -> {
               RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
               GuiComponent.blit(â˜ƒ, â˜ƒ - 36, â˜ƒ, 32, 32, 8.0F, 8.0F, 8, 8, 64, 64);
               GuiComponent.blit(â˜ƒ, â˜ƒ - 36, â˜ƒ, 32, 32, 40.0F, 8.0F, 8, 8, 64, 64);
            });
         }
      }

      @Override
      public Component getNarration() {
         return (Component)(this.serverData.state == RealmsServer.State.UNINITIALIZED
            ? RealmsMainScreen.UNITIALIZED_WORLD_NARRATION
            : new TranslatableComponent("narrator.select", this.serverData.name));
      }
   }

   class ShowPopupButton extends Button {
      public ShowPopupButton() {
         super(
            RealmsMainScreen.this.width - 37,
            6,
            20,
            20,
            new TranslatableComponent("mco.selectServer.info"),
            var1x -> RealmsMainScreen.this.popupOpenedByUser = !RealmsMainScreen.this.popupOpenedByUser
         );
      }

      @Override
      public void renderButton(PoseStack var1, int var2, int var3, float var4) {
         RealmsMainScreen.this.renderMoreInfo(â˜ƒ, â˜ƒ, â˜ƒ, this.x, this.y, this.isHovered());
      }
   }

   class TrialEntry extends RealmsMainScreen.Entry {
      @Override
      public void render(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         this.renderTrialItem(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public boolean mouseClicked(double var1, double var3, int var5) {
         RealmsMainScreen.this.popupOpenedByUser = true;
         return true;
      }

      private void renderTrialItem(PoseStack var1, int var2, int var3, int var4, int var5, int var6) {
         int â˜ƒ = â˜ƒ + 8;
         int â˜ƒx = 0;
         boolean â˜ƒxx = false;
         if (â˜ƒ <= â˜ƒ && â˜ƒ <= (int)RealmsMainScreen.this.realmSelectionList.getScrollAmount() && â˜ƒ <= â˜ƒ && â˜ƒ <= â˜ƒ + 32) {
            â˜ƒxx = true;
         }

         int â˜ƒ = 8388479;
         if (â˜ƒxx && !RealmsMainScreen.this.shouldShowPopup()) {
            â˜ƒ = 6077788;
         }

         for(Component â˜ƒ : RealmsMainScreen.TRIAL_MESSAGE_LINES) {
            GuiComponent.drawCenteredString(â˜ƒ, RealmsMainScreen.this.font, â˜ƒ, RealmsMainScreen.this.width / 2, â˜ƒ + â˜ƒx, â˜ƒ);
            â˜ƒx += 10;
         }
      }

      @Override
      public Component getNarration() {
         return RealmsMainScreen.TRIAL_TEXT;
      }
   }
}
