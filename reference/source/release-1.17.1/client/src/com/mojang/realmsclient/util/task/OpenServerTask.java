package com.mojang.realmsclient.util.task;

import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;

public class OpenServerTask extends LongRunningTask {
   private final RealmsServer serverData;
   private final Screen returnScreen;
   private final boolean join;
   private final RealmsMainScreen mainScreen;
   private final Minecraft minecraft;

   public OpenServerTask(RealmsServer var1, Screen var2, RealmsMainScreen var3, boolean var4, Minecraft var5) {
      this.serverData = â˜ƒ;
      this.returnScreen = â˜ƒ;
      this.join = â˜ƒ;
      this.mainScreen = â˜ƒ;
      this.minecraft = â˜ƒ;
   }

   public void run() {
      this.setTitle(new TranslatableComponent("mco.configure.world.opening"));
      RealmsClient â˜ƒ = RealmsClient.create();

      for(int â˜ƒx = 0; â˜ƒx < 25; ++â˜ƒx) {
         if (this.aborted()) {
            return;
         }

         try {
            boolean â˜ƒxx = â˜ƒ.open(this.serverData.id);
            if (â˜ƒxx) {
               this.minecraft.execute(() -> {
                  if (this.returnScreen instanceof RealmsConfigureWorldScreen) {
                     ((RealmsConfigureWorldScreen)this.returnScreen).stateChanged();
                  }

                  this.serverData.state = RealmsServer.State.OPEN;
                  if (this.join) {
                     this.mainScreen.play(this.serverData, this.returnScreen);
                  } else {
                     this.minecraft.setScreen(this.returnScreen);
                  }
               });
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

            LOGGER.error("Failed to open server", var5);
            this.error("Failed to open the server");
         }
      }
   }
}
