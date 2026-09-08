package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.Subscription;
import com.mojang.realmsclient.exception.RealmsServiceException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsSubscriptionInfoScreen extends RealmsScreen {
   static final Logger LOGGER = LogManager.getLogger();
   private static final Component SUBSCRIPTION_TITLE = new TranslatableComponent("mco.configure.world.subscription.title");
   private static final Component SUBSCRIPTION_START_LABEL = new TranslatableComponent("mco.configure.world.subscription.start");
   private static final Component TIME_LEFT_LABEL = new TranslatableComponent("mco.configure.world.subscription.timeleft");
   private static final Component DAYS_LEFT_LABEL = new TranslatableComponent("mco.configure.world.subscription.recurring.daysleft");
   private static final Component SUBSCRIPTION_EXPIRED_TEXT = new TranslatableComponent("mco.configure.world.subscription.expired");
   private static final Component SUBSCRIPTION_LESS_THAN_A_DAY_TEXT = new TranslatableComponent("mco.configure.world.subscription.less_than_a_day");
   private static final Component MONTH_SUFFIX = new TranslatableComponent("mco.configure.world.subscription.month");
   private static final Component MONTHS_SUFFIX = new TranslatableComponent("mco.configure.world.subscription.months");
   private static final Component DAY_SUFFIX = new TranslatableComponent("mco.configure.world.subscription.day");
   private static final Component DAYS_SUFFIX = new TranslatableComponent("mco.configure.world.subscription.days");
   private static final Component UNKNOWN = new TranslatableComponent("mco.configure.world.subscription.unknown");
   private final Screen lastScreen;
   final RealmsServer serverData;
   final Screen mainScreen;
   private Component daysLeft = UNKNOWN;
   private Component startDate = UNKNOWN;
   @Nullable
   private Subscription.SubscriptionType type;
   private static final String PURCHASE_LINK = "https://aka.ms/ExtendJavaRealms";

   public RealmsSubscriptionInfoScreen(Screen var1, RealmsServer var2, Screen var3) {
      super(NarratorChatListener.NO_TITLE);
      this.lastScreen = â˜ƒ;
      this.serverData = â˜ƒ;
      this.mainScreen = â˜ƒ;
   }

   @Override
   public void init() {
      this.getSubscription(this.serverData.id);
      this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
      this.addRenderableWidget(
         new Button(
            this.width / 2 - 100,
            row(6),
            200,
            20,
            new TranslatableComponent("mco.configure.world.subscription.extend"),
            var1 -> {
               String â˜ƒ = "https://aka.ms/ExtendJavaRealms?subscriptionId="
                  + this.serverData.remoteSubscriptionId
                  + "&profileId="
                  + this.minecraft.getUser().getUuid();
               this.minecraft.keyboardHandler.setClipboard(â˜ƒ);
               Util.getPlatform().openUri(â˜ƒ);
            }
         )
      );
      this.addRenderableWidget(new Button(this.width / 2 - 100, row(12), 200, 20, CommonComponents.GUI_BACK, var1 -> this.minecraft.setScreen(this.lastScreen)));
      if (this.serverData.expired) {
         this.addRenderableWidget(new Button(this.width / 2 - 100, row(10), 200, 20, new TranslatableComponent("mco.configure.world.delete.button"), var1 -> {
            Component â˜ƒ = new TranslatableComponent("mco.configure.world.delete.question.line1");
            Component â˜ƒx = new TranslatableComponent("mco.configure.world.delete.question.line2");
            this.minecraft.setScreen(new RealmsLongConfirmationScreen(this::deleteRealm, RealmsLongConfirmationScreen.Type.Warning, â˜ƒ, â˜ƒx, true));
         }));
      }
   }

   @Override
   public Component getNarrationMessage() {
      return CommonComponents.joinLines(SUBSCRIPTION_TITLE, SUBSCRIPTION_START_LABEL, this.startDate, TIME_LEFT_LABEL, this.daysLeft);
   }

   private void deleteRealm(boolean var1) {
      if (â˜ƒ) {
         (new Thread("Realms-delete-realm") {
               public void run() {
                  try {
                     RealmsClient â˜ƒ = RealmsClient.create();
                     â˜ƒ.deleteWorld(RealmsSubscriptionInfoScreen.this.serverData.id);
                  } catch (RealmsServiceException var2) {
                     RealmsSubscriptionInfoScreen.LOGGER.error("Couldn't delete world");
                     RealmsSubscriptionInfoScreen.LOGGER.error(var2);
                  }
   
                  RealmsSubscriptionInfoScreen.this.minecraft
                     .execute(() -> RealmsSubscriptionInfoScreen.this.minecraft.setScreen(RealmsSubscriptionInfoScreen.this.mainScreen));
               }
            })
            .start();
      }

      this.minecraft.setScreen(this);
   }

   private void getSubscription(long var1) {
      RealmsClient â˜ƒ = RealmsClient.create();

      try {
         Subscription â˜ƒx = â˜ƒ.subscriptionFor(â˜ƒ);
         this.daysLeft = this.daysLeftPresentation(â˜ƒx.daysLeft);
         this.startDate = localPresentation(â˜ƒx.startDate);
         this.type = â˜ƒx.type;
      } catch (RealmsServiceException var5) {
         LOGGER.error("Couldn't get subscription");
         this.minecraft.setScreen(new RealmsGenericErrorScreen(var5, this.lastScreen));
      }
   }

   private static Component localPresentation(long var0) {
      Calendar â˜ƒ = new GregorianCalendar(TimeZone.getDefault());
      â˜ƒ.setTimeInMillis(â˜ƒ);
      return new TextComponent(DateFormat.getDateTimeInstance().format(â˜ƒ.getTime()));
   }

   @Override
   public void removed() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (â˜ƒ == 256) {
         this.minecraft.setScreen(this.lastScreen);
         return true;
      } else {
         return super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      int â˜ƒ = this.width / 2 - 100;
      drawCenteredString(â˜ƒ, this.font, SUBSCRIPTION_TITLE, this.width / 2, 17, 16777215);
      this.font.draw(â˜ƒ, SUBSCRIPTION_START_LABEL, (float)â˜ƒ, (float)row(0), 10526880);
      this.font.draw(â˜ƒ, this.startDate, (float)â˜ƒ, (float)row(1), 16777215);
      if (this.type == Subscription.SubscriptionType.NORMAL) {
         this.font.draw(â˜ƒ, TIME_LEFT_LABEL, (float)â˜ƒ, (float)row(3), 10526880);
      } else if (this.type == Subscription.SubscriptionType.RECURRING) {
         this.font.draw(â˜ƒ, DAYS_LEFT_LABEL, (float)â˜ƒ, (float)row(3), 10526880);
      }

      this.font.draw(â˜ƒ, this.daysLeft, (float)â˜ƒ, (float)row(4), 16777215);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private Component daysLeftPresentation(int var1) {
      if (â˜ƒ < 0 && this.serverData.expired) {
         return SUBSCRIPTION_EXPIRED_TEXT;
      } else if (â˜ƒ <= 1) {
         return SUBSCRIPTION_LESS_THAN_A_DAY_TEXT;
      } else {
         int â˜ƒ = â˜ƒ / 30;
         int â˜ƒx = â˜ƒ % 30;
         MutableComponent â˜ƒxx = new TextComponent("");
         if (â˜ƒ > 0) {
            â˜ƒxx.append(Integer.toString(â˜ƒ)).append(" ");
            if (â˜ƒ == 1) {
               â˜ƒxx.append(MONTH_SUFFIX);
            } else {
               â˜ƒxx.append(MONTHS_SUFFIX);
            }
         }

         if (â˜ƒx > 0) {
            if (â˜ƒ > 0) {
               â˜ƒxx.append(", ");
            }

            â˜ƒxx.append(Integer.toString(â˜ƒx)).append(" ");
            if (â˜ƒx == 1) {
               â˜ƒxx.append(DAY_SUFFIX);
            } else {
               â˜ƒxx.append(DAYS_SUFFIX);
            }
         }

         return â˜ƒxx;
      }
   }
}
