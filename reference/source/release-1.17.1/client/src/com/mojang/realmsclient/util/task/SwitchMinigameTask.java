package com.mojang.realmsclient.util.task;

import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.WorldTemplate;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
import net.minecraft.network.chat.TranslatableComponent;

public class SwitchMinigameTask extends LongRunningTask {
   private final long worldId;
   private final WorldTemplate worldTemplate;
   private final RealmsConfigureWorldScreen lastScreen;

   public SwitchMinigameTask(long var1, WorldTemplate var3, RealmsConfigureWorldScreen var4) {
      this.worldId = â˜ƒ;
      this.worldTemplate = â˜ƒ;
      this.lastScreen = â˜ƒ;
   }

   public void run() {
      RealmsClient â˜ƒ = RealmsClient.create();
      this.setTitle(new TranslatableComponent("mco.minigame.world.starting.screen.title"));

      for(int â˜ƒx = 0; â˜ƒx < 25; ++â˜ƒx) {
         try {
            if (this.aborted()) {
               return;
            }

            if (â˜ƒ.putIntoMinigameMode(this.worldId, this.worldTemplate.id)) {
               setScreen(this.lastScreen);
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

            LOGGER.error("Couldn't start mini game!");
            this.error(var5.toString());
         }
      }
   }
}
