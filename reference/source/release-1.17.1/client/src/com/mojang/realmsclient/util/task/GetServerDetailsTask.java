package com.mojang.realmsclient.util.task;

import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsServerAddress;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.gui.screens.RealmsBrokenWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongConfirmationScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTaskScreen;
import com.mojang.realmsclient.gui.screens.RealmsTermsScreen;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class GetServerDetailsTask extends LongRunningTask {
   private final RealmsServer server;
   private final Screen lastScreen;
   private final RealmsMainScreen mainScreen;
   private final ReentrantLock connectLock;

   public GetServerDetailsTask(RealmsMainScreen var1, Screen var2, RealmsServer var3, ReentrantLock var4) {
      this.lastScreen = â˜ƒ;
      this.mainScreen = â˜ƒ;
      this.server = â˜ƒ;
      this.connectLock = â˜ƒ;
   }

   public void run() {
      this.setTitle(new TranslatableComponent("mco.connect.connecting"));

      RealmsServerAddress â˜ƒ;
      try {
         â˜ƒ = this.fetchServerAddress();
      } catch (CancellationException var4) {
         LOGGER.info("User aborted connecting to realms");
         return;
      } catch (RealmsServiceException var5) {
         switch(var5.errorCode) {
            case 6002:
               setScreen(new RealmsTermsScreen(this.lastScreen, this.mainScreen, this.server));
               return;
            case 6006:
               boolean â˜ƒ = this.server.ownerUUID.equals(Minecraft.getInstance().getUser().getUuid());
               setScreen(
                  (Screen)(â˜ƒ
                     ? new RealmsBrokenWorldScreen(this.lastScreen, this.mainScreen, this.server.id, this.server.worldType == RealmsServer.WorldType.MINIGAME)
                     : new RealmsGenericErrorScreen(
                        new TranslatableComponent("mco.brokenworld.nonowner.title"),
                        new TranslatableComponent("mco.brokenworld.nonowner.error"),
                        this.lastScreen
                     ))
               );
               return;
            default:
               this.error(var5.toString());
               LOGGER.error("Couldn't connect to world", var5);
               return;
         }
      } catch (TimeoutException var6) {
         this.error(new TranslatableComponent("mco.errorMessage.connectionFailure"));
         return;
      } catch (Exception var7) {
         LOGGER.error("Couldn't connect to world", var7);
         this.error(var7.getLocalizedMessage());
         return;
      }

      boolean â˜ƒ = â˜ƒ.resourcePackUrl != null && â˜ƒ.resourcePackHash != null;
      Screen â˜ƒx = (Screen)(â˜ƒ ? this.resourcePackDownloadConfirmationScreen(â˜ƒ, this::connectScreen) : this.connectScreen(â˜ƒ));
      setScreen(â˜ƒx);
   }

   private RealmsServerAddress fetchServerAddress() throws RealmsServiceException, TimeoutException, CancellationException {
      RealmsClient â˜ƒ = RealmsClient.create();

      for(int â˜ƒx = 0; â˜ƒx < 40; ++â˜ƒx) {
         if (this.aborted()) {
            throw new CancellationException();
         }

         try {
            return â˜ƒ.join(this.server.id);
         } catch (RetryCallException var4) {
            pause((long)var4.delaySeconds);
         }
      }

      throw new TimeoutException();
   }

   public RealmsLongRunningMcoTaskScreen connectScreen(RealmsServerAddress var1) {
      return new RealmsLongRunningMcoTaskScreen(this.lastScreen, new ConnectTask(this.lastScreen, this.server, â˜ƒ));
   }

   private RealmsLongConfirmationScreen resourcePackDownloadConfirmationScreen(RealmsServerAddress var1, Function<RealmsServerAddress, Screen> var2) {
      BooleanConsumer â˜ƒ = var3x -> {
         try {
            if (var3x) {
               this.scheduleResourcePackDownload(â˜ƒ).thenRun(() -> setScreen((Screen)â˜ƒ.apply(â˜ƒ))).exceptionally(var1x -> {
                  Minecraft.getInstance().getClientPackSource().clearServerPack();
                  LOGGER.error(var1x);
                  setScreen(new RealmsGenericErrorScreen(new TextComponent("Failed to download resource pack!"), this.lastScreen));
                  return null;
               });
               return;
            }

            setScreen(this.lastScreen);
         } finally {
            if (this.connectLock.isHeldByCurrentThread()) {
               this.connectLock.unlock();
            }
         }
      };
      return new RealmsLongConfirmationScreen(
         â˜ƒ,
         RealmsLongConfirmationScreen.Type.Info,
         new TranslatableComponent("mco.configure.world.resourcepack.question.line1"),
         new TranslatableComponent("mco.configure.world.resourcepack.question.line2"),
         true
      );
   }

   private CompletableFuture<?> scheduleResourcePackDownload(RealmsServerAddress var1) {
      try {
         return Minecraft.getInstance().getClientPackSource().downloadAndSelectResourcePack(â˜ƒ.resourcePackUrl, â˜ƒ.resourcePackHash, false);
      } catch (Exception var4) {
         CompletableFuture<Void> â˜ƒ = new CompletableFuture();
         â˜ƒ.completeExceptionally(var4);
         return â˜ƒ;
      }
   }
}
