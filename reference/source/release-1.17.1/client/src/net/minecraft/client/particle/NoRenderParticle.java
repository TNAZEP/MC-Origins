package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;

public class NoRenderParticle extends Particle {
   protected NoRenderParticle(ClientLevel var1, double var2, double var4, double var6) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   protected NoRenderParticle(ClientLevel var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public final void render(VertexConsumer var1, Camera var2, float var3) {
   }

   @Override
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.NO_RENDER;
   }
}
