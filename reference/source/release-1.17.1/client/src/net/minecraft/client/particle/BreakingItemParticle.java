package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class BreakingItemParticle extends TextureSheetParticle {
   private final float uo;
   private final float vo;

   BreakingItemParticle(ClientLevel var1, double var2, double var4, double var6, double var8, double var10, double var12, ItemStack var14) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.xd *= 0.1F;
      this.yd *= 0.1F;
      this.zd *= 0.1F;
      this.xd += â˜ƒ;
      this.yd += â˜ƒ;
      this.zd += â˜ƒ;
   }

   @Override
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.TERRAIN_SHEET;
   }

   protected BreakingItemParticle(ClientLevel var1, double var2, double var4, double var6, ItemStack var8) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0.0, 0.0, 0.0);
      this.setSprite(Minecraft.getInstance().getItemRenderer().getModel(â˜ƒ, â˜ƒ, null, 0).getParticleIcon());
      this.gravity = 1.0F;
      this.quadSize /= 2.0F;
      this.uo = this.random.nextFloat() * 3.0F;
      this.vo = this.random.nextFloat() * 3.0F;
   }

   @Override
   protected float getU0() {
      return this.sprite.getU((double)((this.uo + 1.0F) / 4.0F * 16.0F));
   }

   @Override
   protected float getU1() {
      return this.sprite.getU((double)(this.uo / 4.0F * 16.0F));
   }

   @Override
   protected float getV0() {
      return this.sprite.getV((double)(this.vo / 4.0F * 16.0F));
   }

   @Override
   protected float getV1() {
      return this.sprite.getV((double)((this.vo + 1.0F) / 4.0F * 16.0F));
   }

   public static class Provider implements ParticleProvider<ItemParticleOption> {
      public Particle createParticle(ItemParticleOption var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new BreakingItemParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getItem());
      }
   }

   public static class SlimeProvider implements ParticleProvider<SimpleParticleType> {
      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new BreakingItemParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, new ItemStack(Items.SLIME_BALL));
      }
   }

   public static class SnowballProvider implements ParticleProvider<SimpleParticleType> {
      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new BreakingItemParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, new ItemStack(Items.SNOWBALL));
      }
   }
}
