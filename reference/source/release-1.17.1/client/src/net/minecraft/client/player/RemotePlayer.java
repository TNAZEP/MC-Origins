package net.minecraft.client.player;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;

public class RemotePlayer extends AbstractClientPlayer {
   public RemotePlayer(ClientLevel var1, GameProfile var2) {
      super(â˜ƒ, â˜ƒ);
      this.maxUpStep = 1.0F;
      this.noPhysics = true;
   }

   @Override
   public boolean shouldRenderAtSqrDistance(double var1) {
      double â˜ƒ = this.getBoundingBox().getSize() * 10.0;
      if (Double.isNaN(â˜ƒ)) {
         â˜ƒ = 1.0;
      }

      â˜ƒ *= 64.0 * getViewScale();
      return â˜ƒ < â˜ƒ * â˜ƒ;
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      return true;
   }

   @Override
   public void tick() {
      super.tick();
      this.calculateEntityAnimation(this, false);
   }

   @Override
   public void aiStep() {
      if (this.lerpSteps > 0) {
         double â˜ƒ = this.getX() + (this.lerpX - this.getX()) / (double)this.lerpSteps;
         double â˜ƒx = this.getY() + (this.lerpY - this.getY()) / (double)this.lerpSteps;
         double â˜ƒxx = this.getZ() + (this.lerpZ - this.getZ()) / (double)this.lerpSteps;
         this.setYRot(this.getYRot() + (float)Mth.wrapDegrees(this.lerpYRot - (double)this.getYRot()) / (float)this.lerpSteps);
         this.setXRot(this.getXRot() + (float)(this.lerpXRot - (double)this.getXRot()) / (float)this.lerpSteps);
         --this.lerpSteps;
         this.setPos(â˜ƒ, â˜ƒx, â˜ƒxx);
         this.setRot(this.getYRot(), this.getXRot());
      }

      if (this.lerpHeadSteps > 0) {
         this.yHeadRot = (float)((double)this.yHeadRot + Mth.wrapDegrees(this.lyHeadRot - (double)this.yHeadRot) / (double)this.lerpHeadSteps);
         --this.lerpHeadSteps;
      }

      this.oBob = this.bob;
      this.updateSwingTime();
      float â˜ƒ;
      if (this.onGround && !this.isDeadOrDying()) {
         â˜ƒ = (float)Math.min(0.1, this.getDeltaMovement().horizontalDistance());
      } else {
         â˜ƒ = 0.0F;
      }

      this.bob += (â˜ƒ - this.bob) * 0.4F;
      this.level.getProfiler().push("push");
      this.pushEntities();
      this.level.getProfiler().pop();
   }

   @Override
   protected void updatePlayerPose() {
   }

   @Override
   public void sendMessage(Component var1, UUID var2) {
      Minecraft â˜ƒ = Minecraft.getInstance();
      if (!â˜ƒ.isBlocked(â˜ƒ)) {
         â˜ƒ.gui.getChat().addMessage(â˜ƒ);
      }
   }
}
