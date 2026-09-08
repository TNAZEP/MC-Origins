package net.minecraft.world.entity.projectile;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class Snowball extends ThrowableItemProjectile {
   public Snowball(EntityType<? extends Snowball> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public Snowball(Level var1, LivingEntity var2) {
      super(EntityType.SNOWBALL, â˜ƒ, â˜ƒ);
   }

   public Snowball(Level var1, double var2, double var4, double var6) {
      super(EntityType.SNOWBALL, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected Item getDefaultItem() {
      return Items.SNOWBALL;
   }

   private ParticleOptions getParticle() {
      ItemStack â˜ƒ = this.getItemRaw();
      return (ParticleOptions)(â˜ƒ.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemParticleOption(ParticleTypes.ITEM, â˜ƒ));
   }

   @Override
   public void handleEntityEvent(byte var1) {
      if (â˜ƒ == 3) {
         ParticleOptions â˜ƒ = this.getParticle();

         for(int â˜ƒx = 0; â˜ƒx < 8; ++â˜ƒx) {
            this.level.addParticle(â˜ƒ, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
         }
      }
   }

   @Override
   protected void onHitEntity(EntityHitResult var1) {
      super.onHitEntity(â˜ƒ);
      Entity â˜ƒ = â˜ƒ.getEntity();
      int â˜ƒx = â˜ƒ instanceof Blaze ? 3 : 0;
      â˜ƒ.hurt(DamageSource.thrown(this, this.getOwner()), (float)â˜ƒx);
   }

   @Override
   protected void onHit(HitResult var1) {
      super.onHit(â˜ƒ);
      if (!this.level.isClientSide) {
         this.level.broadcastEntityEvent(this, (byte)3);
         this.discard();
      }
   }
}
