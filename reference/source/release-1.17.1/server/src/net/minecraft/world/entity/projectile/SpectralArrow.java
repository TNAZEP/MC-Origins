package net.minecraft.world.entity.projectile;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class SpectralArrow extends AbstractArrow {
   private int duration = 200;

   public SpectralArrow(EntityType<? extends SpectralArrow> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public SpectralArrow(Level var1, LivingEntity var2) {
      super(EntityType.SPECTRAL_ARROW, â˜ƒ, â˜ƒ);
   }

   public SpectralArrow(Level var1, double var2, double var4, double var6) {
      super(EntityType.SPECTRAL_ARROW, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void tick() {
      super.tick();
      if (this.level.isClientSide && !this.inGround) {
         this.level.addParticle(ParticleTypes.INSTANT_EFFECT, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
      }
   }

   @Override
   protected ItemStack getPickupItem() {
      return new ItemStack(Items.SPECTRAL_ARROW);
   }

   @Override
   protected void doPostHurtEffects(LivingEntity var1) {
      super.doPostHurtEffects(â˜ƒ);
      MobEffectInstance â˜ƒ = new MobEffectInstance(MobEffects.GLOWING, this.duration, 0);
      â˜ƒ.addEffect(â˜ƒ, this.getEffectSource());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      if (â˜ƒ.contains("Duration")) {
         this.duration = â˜ƒ.getInt("Duration");
      }
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putInt("Duration", this.duration);
   }
}
