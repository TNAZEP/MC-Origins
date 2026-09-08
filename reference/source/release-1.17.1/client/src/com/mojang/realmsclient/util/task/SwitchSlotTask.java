package com.mojang.realmsclient.util.task;

import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.exception.RetryCallException;
import net.minecraft.network.chat.TranslatableComponent;

public class SwitchSlotTask extends LongRunningTask {
   private final long worldId;
   private final int slot;
   private final Runnable callback;

   public SwitchSlotTask(long var1, int var3, Runnable var4) {
      this.worldId = â˜ƒ;
      this.slot = â˜ƒ;
      this.callback = â˜ƒ;
   }

   public void run() {
      RealmsClient â˜ƒ = RealmsClient.create();
      this.setTitle(new TranslatableComponent("mco.minigame.world.slot.screen.title"));

      for(int â˜ƒx = 0; â˜ƒx < 25; ++â˜ƒx) {
         try {
            if (this.aborted()) {
               return;
            }

            if (â˜ƒ.switchSlot(this.worldId, this.slot)) {
               this.callback.run();
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

            LOGGER.error("Couldn't switch world!");
            this.error(var5.toString());
         }
      }
   }
}
