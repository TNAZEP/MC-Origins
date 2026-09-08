package net.minecraft.world.entity.projectile;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class ThrownExperienceBottle extends ThrowableItemProjectile {
   public ThrownExperienceBottle(EntityType<? extends ThrownExperienceBottle> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public ThrownExperienceBottle(Level var1, LivingEntity var2) {
      super(EntityType.EXPERIENCE_BOTTLE, â˜ƒ, â˜ƒ);
   }

   public ThrownExperienceBottle(Level var1, double var2, double var4, double var6) {
      super(EntityType.EXPERIENCE_BOTTLE, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected Item getDefaultItem() {
      return Items.EXPERIENCE_BOTTLE;
   }

   @Override
   protected float getGravity() {
      return 0.07F;
   }

   @Override
   protected void onHit(HitResult var1) {
      super.onHit(â˜ƒ);
      if (this.level instanceof ServerLevel) {
         this.level.levelEvent(2002, this.blockPosition(), PotionUtils.getColor(Potions.WATER));
         int â˜ƒ = 3 + this.level.random.nextInt(5) + this.level.random.nextInt(5);
         ExperienceOrb.award((ServerLevel)this.level, this.position(), â˜ƒ);
         this.discard();
      }
   }
}
