package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.DustColorTransitionOptions;

public class DustColorTransitionParticle extends DustParticleBase<DustColorTransitionOptions> {
   private final Vector3f fromColor;
   private final Vector3f toColor;

   protected DustColorTransitionParticle(
      ClientLevel var1, double var2, double var4, double var6, double var8, double var10, double var12, DustColorTransitionOptions var14, SpriteSet var15
   ) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      float â˜ƒ = this.random.nextFloat() * 0.4F + 0.6F;
      this.fromColor = this.randomizeColor(â˜ƒ.getFromColor(), â˜ƒ);
      this.toColor = this.randomizeColor(â˜ƒ.getToColor(), â˜ƒ);
   }

   private Vector3f randomizeColor(Vector3f var1, float var2) {
      return new Vector3f(this.randomizeColor(â˜ƒ.x(), â˜ƒ), this.randomizeColor(â˜ƒ.y(), â˜ƒ), this.randomizeColor(â˜ƒ.z(), â˜ƒ));
   }

   private void lerpColors(float var1) {
      float â˜ƒ = ((float)this.age + â˜ƒ) / ((float)this.lifetime + 1.0F);
      Vector3f â˜ƒx = this.fromColor.copy();
      â˜ƒx.lerp(this.toColor, â˜ƒ);
      this.rCol = â˜ƒx.x();
      this.gCol = â˜ƒx.y();
      this.bCol = â˜ƒx.z();
   }

   @Override
   public void render(VertexConsumer var1, Camera var2, float var3) {
      this.lerpColors(â˜ƒ);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static class Provider implements ParticleProvider<DustColorTransitionOptions> {
      private final SpriteSet sprites;

      public Provider(SpriteSet var1) {
         this.sprites = â˜ƒ;
      }

      public Particle createParticle(
         DustColorTransitionOptions var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13
      ) {
         return new DustColorTransitionParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.sprites);
      }
   }
}
