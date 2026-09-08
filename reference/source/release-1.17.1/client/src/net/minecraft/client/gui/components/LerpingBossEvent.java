package net.minecraft.client.gui.components;

import java.util.UUID;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;

public class LerpingBossEvent extends BossEvent {
   private static final long LERP_MILLISECONDS = 100L;
   protected float targetPercent;
   protected long setTime;

   public LerpingBossEvent(
      UUID var1, Component var2, float var3, BossEvent.BossBarColor var4, BossEvent.BossBarOverlay var5, boolean var6, boolean var7, boolean var8
   ) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.targetPercent = â˜ƒ;
      this.progress = â˜ƒ;
      this.setTime = Util.getMillis();
      this.setDarkenScreen(â˜ƒ);
      this.setPlayBossMusic(â˜ƒ);
      this.setCreateWorldFog(â˜ƒ);
   }

   @Override
   public void setProgress(float var1) {
      this.progress = this.getProgress();
      this.targetPercent = â˜ƒ;
      this.setTime = Util.getMillis();
   }

   @Override
   public float getProgress() {
      long â˜ƒ = Util.getMillis() - this.setTime;
      float â˜ƒx = Mth.clamp((float)â˜ƒ / 100.0F, 0.0F, 1.0F);
      return Mth.lerp(â˜ƒx, this.progress, this.targetPercent);
   }
}
