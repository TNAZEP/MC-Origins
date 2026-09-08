package net.minecraft.world.entity.monster;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import javax.annotation.Nullable;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

public interface CrossbowAttackMob extends RangedAttackMob {
   void setChargingCrossbow(boolean var1);

   void shootCrossbowProjectile(LivingEntity var1, ItemStack var2, Projectile var3, float var4);

   @Nullable
   LivingEntity getTarget();

   void onCrossbowAttackPerformed();

   default void performCrossbowAttack(LivingEntity var1, float var2) {
      InteractionHand â˜ƒ = ProjectileUtil.getWeaponHoldingHand(â˜ƒ, Items.CROSSBOW);
      ItemStack â˜ƒx = â˜ƒ.getItemInHand(â˜ƒ);
      if (â˜ƒ.isHolding(Items.CROSSBOW)) {
         CrossbowItem.performShooting(â˜ƒ.level, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, (float)(14 - â˜ƒ.level.getDifficulty().getId() * 4));
      }

      this.onCrossbowAttackPerformed();
   }

   default void shootCrossbowProjectile(LivingEntity var1, LivingEntity var2, Projectile var3, float var4, float var5) {
      double â˜ƒ = â˜ƒ.getX() - â˜ƒ.getX();
      double â˜ƒx = â˜ƒ.getZ() - â˜ƒ.getZ();
      double â˜ƒxx = Math.sqrt(â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx);
      double â˜ƒxxx = â˜ƒ.getY(0.3333333333333333) - â˜ƒ.getY() + â˜ƒxx * 0.2F;
      Vector3f â˜ƒxxxx = this.getProjectileShotVector(â˜ƒ, new Vec3(â˜ƒ, â˜ƒxxx, â˜ƒx), â˜ƒ);
      â˜ƒ.shoot((double)â˜ƒxxxx.x(), (double)â˜ƒxxxx.y(), (double)â˜ƒxxxx.z(), â˜ƒ, (float)(14 - â˜ƒ.level.getDifficulty().getId() * 4));
      â˜ƒ.playSound(SoundEvents.CROSSBOW_SHOOT, 1.0F, 1.0F / (â˜ƒ.getRandom().nextFloat() * 0.4F + 0.8F));
   }

   default Vector3f getProjectileShotVector(LivingEntity var1, Vec3 var2, float var3) {
      Vec3 â˜ƒ = â˜ƒ.normalize();
      Vec3 â˜ƒx = â˜ƒ.cross(new Vec3(0.0, 1.0, 0.0));
      if (â˜ƒx.lengthSqr() <= 1.0E-7) {
         â˜ƒx = â˜ƒ.cross(â˜ƒ.getUpVector(1.0F));
      }

      Quaternion â˜ƒ = new Quaternion(new Vector3f(â˜ƒx), 90.0F, true);
      Vector3f â˜ƒx = new Vector3f(â˜ƒ);
      â˜ƒx.transform(â˜ƒ);
      Quaternion â˜ƒxx = new Quaternion(â˜ƒx, â˜ƒ, true);
      Vector3f â˜ƒxxx = new Vector3f(â˜ƒ);
      â˜ƒxxx.transform(â˜ƒxx);
      return â˜ƒxxx;
   }
}
