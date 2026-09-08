package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.GuardianModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ElderGuardianRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public class MobAppearanceParticle extends Particle {
   private final Model model;
   private final RenderType renderType = RenderType.entityTranslucent(ElderGuardianRenderer.GUARDIAN_ELDER_LOCATION);

   MobAppearanceParticle(ClientLevel var1, double var2, double var4, double var6) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.model = new GuardianModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.ELDER_GUARDIAN));
      this.gravity = 0.0F;
      this.lifetime = 30;
   }

   @Override
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.CUSTOM;
   }

   @Override
   public void render(VertexConsumer var1, Camera var2, float var3) {
      float â˜ƒ = ((float)this.age + â˜ƒ) / (float)this.lifetime;
      float â˜ƒx = 0.05F + 0.5F * Mth.sin(â˜ƒ * (float) Math.PI);
      PoseStack â˜ƒxx = new PoseStack();
      â˜ƒxx.mulPose(â˜ƒ.rotation());
      â˜ƒxx.mulPose(Vector3f.XP.rotationDegrees(150.0F * â˜ƒ - 60.0F));
      â˜ƒxx.scale(-1.0F, -1.0F, 1.0F);
      â˜ƒxx.translate(0.0, -1.101F, 1.5);
      MultiBufferSource.BufferSource â˜ƒxxx = Minecraft.getInstance().renderBuffers().bufferSource();
      VertexConsumer â˜ƒxxxx = â˜ƒxxx.getBuffer(this.renderType);
      this.model.renderToBuffer(â˜ƒxx, â˜ƒxxxx, 15728880, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, â˜ƒx);
      â˜ƒxxx.endBatch();
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new MobAppearanceParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }
}
