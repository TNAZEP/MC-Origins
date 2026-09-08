package net.minecraft.world.entity.projectile;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ThrownEgg extends ThrowableItemProjectile {
   public ThrownEgg(EntityType<? extends ThrownEgg> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public ThrownEgg(Level var1, LivingEntity var2) {
      super(EntityType.EGG, â˜ƒ, â˜ƒ);
   }

   public ThrownEgg(Level var1, double var2, double var4, double var6) {
      super(EntityType.EGG, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void handleEntityEvent(byte var1) {
      if (â˜ƒ == 3) {
         double â˜ƒ = 0.08;

         for(int â˜ƒx = 0; â˜ƒx < 8; ++â˜ƒx) {
            this.level
               .addParticle(
                  new ItemParticleOption(ParticleTypes.ITEM, this.getItem()),
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  ((double)this.random.nextFloat() - 0.5) * 0.08,
                  ((double)this.random.nextFloat() - 0.5) * 0.08,
                  ((double)this.random.nextFloat() - 0.5) * 0.08
               );
         }
      }
   }

   @Override
   protected void onHitEntity(EntityHitResult var1) {
      super.onHitEntity(â˜ƒ);
      â˜ƒ.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 0.0F);
   }

   @Override
   protected void onHit(HitResult var1) {
      super.onHit(â˜ƒ);
      if (!this.level.isClientSide) {
         if (this.random.nextInt(8) == 0) {
            int â˜ƒ = 1;
            if (this.random.nextInt(32) == 0) {
               â˜ƒ = 4;
            }

            for(int â˜ƒ = 0; â˜ƒ < â˜ƒ; ++â˜ƒ) {
               Chicken â˜ƒx = EntityType.CHICKEN.create(this.level);
               â˜ƒx.setAge(-24000);
               â˜ƒx.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
               this.level.addFreshEntity(â˜ƒx);
            }
         }

         this.level.broadcastEntityEvent(this, (byte)3);
         this.discard();
      }
   }

   @Override
   protected Item getDefaultItem() {
      return Items.EGG;
   }
}
