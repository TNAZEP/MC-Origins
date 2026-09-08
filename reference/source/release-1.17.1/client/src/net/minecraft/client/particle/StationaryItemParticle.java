package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

public class StationaryItemParticle extends TextureSheetParticle {
   StationaryItemParticle(ClientLevel var1, double var2, double var4, double var6, ItemLike var8) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.setSprite(Minecraft.getInstance().getItemRenderer().getItemModelShaper().getParticleIcon(â˜ƒ));
      this.gravity = 0.0F;
      this.lifetime = 80;
      this.hasPhysics = false;
   }

   @Override
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.TERRAIN_SHEET;
   }

   @Override
   public float getQuadSize(float var1) {
      return 0.5F;
   }

   public static class BarrierProvider implements ParticleProvider<SimpleParticleType> {
      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new StationaryItemParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Blocks.BARRIER.asItem());
      }
   }

   public static class LightProvider implements ParticleProvider<SimpleParticleType> {
      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new StationaryItemParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Items.LIGHT);
      }
   }
}
