package net.minecraft.world.entity.monster.hoglin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;

public interface HoglinBase {
   int ATTACK_ANIMATION_DURATION = 10;

   int getAttackAnimationRemainingTicks();

   static boolean hurtAndThrowTarget(LivingEntity var0, LivingEntity var1) {
      float â˜ƒx = (float)â˜ƒ.getAttributeValue(Attributes.ATTACK_DAMAGE);
      float â˜ƒ;
      if (!â˜ƒ.isBaby() && (int)â˜ƒx > 0) {
         â˜ƒ = â˜ƒx / 2.0F + (float)â˜ƒ.level.random.nextInt((int)â˜ƒx);
      } else {
         â˜ƒ = â˜ƒx;
      }

      boolean â˜ƒ = â˜ƒ.hurt(DamageSource.mobAttack(â˜ƒ), â˜ƒ);
      if (â˜ƒ) {
         â˜ƒ.doEnchantDamageEffects(â˜ƒ, â˜ƒ);
         if (!â˜ƒ.isBaby()) {
            throwTarget(â˜ƒ, â˜ƒ);
         }
      }

      return â˜ƒ;
   }

   static void throwTarget(LivingEntity var0, LivingEntity var1) {
      double â˜ƒ = â˜ƒ.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
      double â˜ƒx = â˜ƒ.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
      double â˜ƒxx = â˜ƒ - â˜ƒx;
      if (!(â˜ƒxx <= 0.0)) {
         double â˜ƒxxx = â˜ƒ.getX() - â˜ƒ.getX();
         double â˜ƒxxxx = â˜ƒ.getZ() - â˜ƒ.getZ();
         float â˜ƒxxxxx = (float)(â˜ƒ.level.random.nextInt(21) - 10);
         double â˜ƒxxxxxx = â˜ƒxx * (double)(â˜ƒ.level.random.nextFloat() * 0.5F + 0.2F);
         Vec3 â˜ƒxxxxxxx = new Vec3(â˜ƒxxx, 0.0, â˜ƒxxxx).normalize().scale(â˜ƒxxxxxx).yRot(â˜ƒxxxxx);
         double â˜ƒxxxxxxxx = â˜ƒxx * (double)â˜ƒ.level.random.nextFloat() * 0.5;
         â˜ƒ.push(â˜ƒxxxxxxx.x, â˜ƒxxxxxxxx, â˜ƒxxxxxxx.z);
         â˜ƒ.hurtMarked = true;
      }
   }
}
