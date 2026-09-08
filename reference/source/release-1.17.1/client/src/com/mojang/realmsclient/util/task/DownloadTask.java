package com.mojang.realmsclient.util.task;

import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.WorldDownload;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.gui.screens.RealmsDownloadLatestWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;

public class DownloadTask extends LongRunningTask {
   private final long worldId;
   private final int slot;
   private final Screen lastScreen;
   private final String downloadName;

   public DownloadTask(long var1, int var3, String var4, Screen var5) {
      this.worldId = â˜ƒ;
      this.slot = â˜ƒ;
      this.lastScreen = â˜ƒ;
      this.downloadName = â˜ƒ;
   }

   public void run() {
      this.setTitle(new TranslatableComponent("mco.download.preparing"));
      RealmsClient â˜ƒ = RealmsClient.create();
      int â˜ƒx = 0;

      while(â˜ƒx < 25) {
         try {
            if (this.aborted()) {
               return;
            }

            WorldDownload â˜ƒxx = â˜ƒ.requestDownloadInfo(this.worldId, this.slot);
            pause(1L);
            if (this.aborted()) {
               return;
            }

            setScreen(new RealmsDownloadLatestWorldScreen(this.lastScreen, â˜ƒxx, this.downloadName, var0 -> {
            }));
            return;
         } catch (RetryCallException var4) {
            if (this.aborted()) {
               return;
            }

            pause((long)var4.delaySeconds);
            ++â˜ƒx;
         } catch (RealmsServiceException var5) {
            if (this.aborted()) {
               return;
            }

            LOGGER.error("Couldn't download world data");
            setScreen(new RealmsGenericErrorScreen(var5, this.lastScreen));
            return;
         } catch (Exception var6) {
            if (this.aborted()) {
               return;
            }

            LOGGER.error("Couldn't download world data", var6);
            this.error(var6.getLocalizedMessage());
            return;
         }
      }
   }
}
