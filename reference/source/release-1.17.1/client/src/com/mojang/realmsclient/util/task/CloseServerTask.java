package com.mojang.realmsclient.util.task;

import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
import net.minecraft.network.chat.TranslatableComponent;

public class CloseServerTask extends LongRunningTask {
   private final RealmsServer serverData;
   private final RealmsConfigureWorldScreen configureScreen;

   public CloseServerTask(RealmsServer var1, RealmsConfigureWorldScreen var2) {
      this.serverData = â˜ƒ;
      this.configureScreen = â˜ƒ;
   }

   public void run() {
      this.setTitle(new TranslatableComponent("mco.configure.world.closing"));
      RealmsClient â˜ƒ = RealmsClient.create();

      for(int â˜ƒx = 0; â˜ƒx < 25; ++â˜ƒx) {
         if (this.aborted()) {
            return;
         }

         try {
            boolean â˜ƒxx = â˜ƒ.close(this.serverData.id);
            if (â˜ƒxx) {
               this.configureScreen.stateChanged();
               this.serverData.state = RealmsServer.State.CLOSED;
               setScreen(this.configureScreen);
               break;
            }
         } catch (RetryCallException var4) {
            if (this.aborted()) {
               return;
            }

            pause((long)var4.delaySeconds);
         } catch (Exception var5) {
            if (this.aborted()) {
               return;
            }

            LOGGER.error("Failed to close server", var5);
            this.error("Failed to close the server");
         }
      }
   }
}
