package net.minecraft.client.particle;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class FallingDustParticle extends TextureSheetParticle {
   private final float rotSpeed;
   private final SpriteSet sprites;

   FallingDustParticle(ClientLevel var1, double var2, double var4, double var6, float var8, float var9, float var10, SpriteSet var11) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.sprites = â˜ƒ;
      this.rCol = â˜ƒ;
      this.gCol = â˜ƒ;
      this.bCol = â˜ƒ;
      float â˜ƒ = 0.9F;
      this.quadSize *= 0.67499995F;
      int â˜ƒx = (int)(32.0 / (Math.random() * 0.8 + 0.2));
      this.lifetime = (int)Math.max((float)â˜ƒx * 0.9F, 1.0F);
      this.setSpriteFromAge(â˜ƒ);
      this.rotSpeed = ((float)Math.random() - 0.5F) * 0.1F;
      this.roll = (float)Math.random() * (float) (Math.PI * 2);
   }

   @Override
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   @Override
   public float getQuadSize(float var1) {
      return this.quadSize * Mth.clamp(((float)this.age + â˜ƒ) / (float)this.lifetime * 32.0F, 0.0F, 1.0F);
   }

   @Override
   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (this.age++ >= this.lifetime) {
         this.remove();
      } else {
         this.setSpriteFromAge(this.sprites);
         this.oRoll = this.roll;
         this.roll += (float) Math.PI * this.rotSpeed * 2.0F;
         if (this.onGround) {
            this.oRoll = this.roll = 0.0F;
         }

         this.move(this.xd, this.yd, this.zd);
         this.yd -= 0.003F;
         this.yd = Math.max(this.yd, -0.14F);
      }
   }

   public static class Provider implements ParticleProvider<BlockParticleOption> {
      private final SpriteSet sprite;

      public Provider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      @Nullable
      public Particle createParticle(BlockParticleOption var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         BlockState â˜ƒ = â˜ƒ.getState();
         if (!â˜ƒ.isAir() && â˜ƒ.getRenderShape() == RenderShape.INVISIBLE) {
            return null;
         } else {
            BlockPos â˜ƒ = new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ);
            int â˜ƒx = Minecraft.getInstance().getBlockColors().getColor(â˜ƒ, â˜ƒ, â˜ƒ);
            if (â˜ƒ.getBlock() instanceof FallingBlock) {
               â˜ƒx = ((FallingBlock)â˜ƒ.getBlock()).getDustColor(â˜ƒ, â˜ƒ, â˜ƒ);
            }

            float â˜ƒ = (float)(â˜ƒx >> 16 & 0xFF) / 255.0F;
            float â˜ƒx = (float)(â˜ƒx >> 8 & 0xFF) / 255.0F;
            float â˜ƒxx = (float)(â˜ƒx & 0xFF) / 255.0F;
            return new FallingDustParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx, this.sprite);
         }
      }
   }
}
