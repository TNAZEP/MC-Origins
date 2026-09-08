package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;

public class WaterDropParticle extends TextureSheetParticle {
   protected WaterDropParticle(ClientLevel var1, double var2, double var4, double var6) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0.0, 0.0, 0.0);
      this.xd *= 0.3F;
      this.yd = Math.random() * 0.2F + 0.1F;
      this.zd *= 0.3F;
      this.setSize(0.01F, 0.01F);
      this.gravity = 0.06F;
      this.lifetime = (int)(8.0 / (Math.random() * 0.8 + 0.2));
   }

   @Override
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   @Override
   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (this.lifetime-- <= 0) {
         this.remove();
      } else {
         this.yd -= (double)this.gravity;
         this.move(this.xd, this.yd, this.zd);
         this.xd *= 0.98F;
         this.yd *= 0.98F;
         this.zd *= 0.98F;
         if (this.onGround) {
            if (Math.random() < 0.5) {
               this.remove();
            }

            this.xd *= 0.7F;
            this.zd *= 0.7F;
         }

         BlockPos â˜ƒ = new BlockPos(this.x, this.y, this.z);
         double â˜ƒx = Math.max(
            this.level.getBlockState(â˜ƒ).getCollisionShape(this.level, â˜ƒ).max(Direction.Axis.Y, this.x - (double)â˜ƒ.getX(), this.z - (double)â˜ƒ.getZ()),
            (double)this.level.getFluidState(â˜ƒ).getHeight(this.level, â˜ƒ)
         );
         if (â˜ƒx > 0.0 && this.y < (double)â˜ƒ.getY() + â˜ƒx) {
            this.remove();
         }
      }
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public Provider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         WaterDropParticle â˜ƒ = new WaterDropParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.pickSprite(this.sprite);
         return â˜ƒ;
      }
   }
}
