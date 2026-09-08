package com.mojang.realmsclient.util.task;

import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.util.WorldGenerationInfo;
import net.minecraft.network.chat.Component;

public class ResettingGeneratedWorldTask extends ResettingWorldTask {
   private final WorldGenerationInfo generationInfo;

   public ResettingGeneratedWorldTask(WorldGenerationInfo var1, long var2, Component var4, Runnable var5) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
      this.generationInfo = â˜ƒ;
   }

   @Override
   protected void sendResetRequest(RealmsClient var1, long var2) throws RealmsServiceException {
      â˜ƒ.resetWorldWithSeed(â˜ƒ, this.generationInfo);
   }
}
