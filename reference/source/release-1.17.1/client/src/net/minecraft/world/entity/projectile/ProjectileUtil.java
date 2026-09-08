package net.minecraft.world.entity.projectile;

import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public final class ProjectileUtil {
   public static HitResult getHitResult(Entity var0, Predicate<Entity> var1) {
      Vec3 â˜ƒ = â˜ƒ.getDeltaMovement();
      Level â˜ƒx = â˜ƒ.level;
      Vec3 â˜ƒxx = â˜ƒ.position();
      Vec3 â˜ƒxxx = â˜ƒxx.add(â˜ƒ);
      HitResult â˜ƒxxxx = â˜ƒx.clip(new ClipContext(â˜ƒxx, â˜ƒxxx, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, â˜ƒ));
      if (â˜ƒxxxx.getType() != HitResult.Type.MISS) {
         â˜ƒxxx = â˜ƒxxxx.getLocation();
      }

      HitResult â˜ƒ = getEntityHitResult(â˜ƒx, â˜ƒ, â˜ƒxx, â˜ƒxxx, â˜ƒ.getBoundingBox().expandTowards(â˜ƒ.getDeltaMovement()).inflate(1.0), â˜ƒ);
      if (â˜ƒ != null) {
         â˜ƒxxxx = â˜ƒ;
      }

      return â˜ƒxxxx;
   }

   @Nullable
   public static EntityHitResult getEntityHitResult(Entity var0, Vec3 var1, Vec3 var2, AABB var3, Predicate<Entity> var4, double var5) {
      Level â˜ƒ = â˜ƒ.level;
      double â˜ƒx = â˜ƒ;
      Entity â˜ƒxx = null;
      Vec3 â˜ƒxxx = null;

      for(Entity â˜ƒxxxx : â˜ƒ.getEntities(â˜ƒ, â˜ƒ, â˜ƒ)) {
         AABB â˜ƒxxxxx = â˜ƒxxxx.getBoundingBox().inflate((double)â˜ƒxxxx.getPickRadius());
         Optional<Vec3> â˜ƒxxxxxx = â˜ƒxxxxx.clip(â˜ƒ, â˜ƒ);
         if (â˜ƒxxxxx.contains(â˜ƒ)) {
            if (â˜ƒx >= 0.0) {
               â˜ƒxx = â˜ƒxxxx;
               â˜ƒxxx = (Vec3)â˜ƒxxxxxx.orElse(â˜ƒ);
               â˜ƒx = 0.0;
            }
         } else if (â˜ƒxxxxxx.isPresent()) {
            Vec3 â˜ƒxxxxx = (Vec3)â˜ƒxxxxxx.get();
            double â˜ƒxxxxxx = â˜ƒ.distanceToSqr(â˜ƒxxxxx);
            if (â˜ƒxxxxxx < â˜ƒx || â˜ƒx == 0.0) {
               if (â˜ƒxxxx.getRootVehicle() == â˜ƒ.getRootVehicle()) {
                  if (â˜ƒx == 0.0) {
                     â˜ƒxx = â˜ƒxxxx;
                     â˜ƒxxx = â˜ƒxxxxx;
                  }
               } else {
                  â˜ƒxx = â˜ƒxxxx;
                  â˜ƒxxx = â˜ƒxxxxx;
                  â˜ƒx = â˜ƒxxxxxx;
               }
            }
         }
      }

      return â˜ƒxx == null ? null : new EntityHitResult(â˜ƒxx, â˜ƒxxx);
   }

   @Nullable
   public static EntityHitResult getEntityHitResult(Level var0, Entity var1, Vec3 var2, Vec3 var3, AABB var4, Predicate<Entity> var5) {
      return getEntityHitResult(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0.3F);
   }

   @Nullable
   public static EntityHitResult getEntityHitResult(Level var0, Entity var1, Vec3 var2, Vec3 var3, AABB var4, Predicate<Entity> var5, float var6) {
      double â˜ƒ = Double.MAX_VALUE;
      Entity â˜ƒx = null;

      for(Entity â˜ƒxx : â˜ƒ.getEntities(â˜ƒ, â˜ƒ, â˜ƒ)) {
         AABB â˜ƒxxx = â˜ƒxx.getBoundingBox().inflate((double)â˜ƒ);
         Optional<Vec3> â˜ƒxxxx = â˜ƒxxx.clip(â˜ƒ, â˜ƒ);
         if (â˜ƒxxxx.isPresent()) {
            double â˜ƒxxxxx = â˜ƒ.distanceToSqr((Vec3)â˜ƒxxxx.get());
            if (â˜ƒxxxxx < â˜ƒ) {
               â˜ƒx = â˜ƒxx;
               â˜ƒ = â˜ƒxxxxx;
            }
         }
      }

      return â˜ƒx == null ? null : new EntityHitResult(â˜ƒx);
   }

   public static void rotateTowardsMovement(Entity var0, float var1) {
      Vec3 â˜ƒ = â˜ƒ.getDeltaMovement();
      if (â˜ƒ.lengthSqr() != 0.0) {
         double â˜ƒx = â˜ƒ.horizontalDistance();
         â˜ƒ.setYRot((float)(Mth.atan2(â˜ƒ.z, â˜ƒ.x) * 180.0F / (float)Math.PI) + 90.0F);
         â˜ƒ.setXRot((float)(Mth.atan2(â˜ƒx, â˜ƒ.y) * 180.0F / (float)Math.PI) - 90.0F);

         while(â˜ƒ.getXRot() - â˜ƒ.xRotO < -180.0F) {
            â˜ƒ.xRotO -= 360.0F;
         }

         while(â˜ƒ.getXRot() - â˜ƒ.xRotO >= 180.0F) {
            â˜ƒ.xRotO += 360.0F;
         }

         while(â˜ƒ.getYRot() - â˜ƒ.yRotO < -180.0F) {
            â˜ƒ.yRotO -= 360.0F;
         }

         while(â˜ƒ.getYRot() - â˜ƒ.yRotO >= 180.0F) {
            â˜ƒ.yRotO += 360.0F;
         }

         â˜ƒ.setXRot(Mth.lerp(â˜ƒ, â˜ƒ.xRotO, â˜ƒ.getXRot()));
         â˜ƒ.setYRot(Mth.lerp(â˜ƒ, â˜ƒ.yRotO, â˜ƒ.getYRot()));
      }
   }

   public static InteractionHand getWeaponHoldingHand(LivingEntity var0, Item var1) {
      return â˜ƒ.getMainHandItem().is(â˜ƒ) ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
   }

   public static AbstractArrow getMobArrow(LivingEntity var0, ItemStack var1, float var2) {
      ArrowItem â˜ƒ = (ArrowItem)(â˜ƒ.getItem() instanceof ArrowItem ? â˜ƒ.getItem() : Items.ARROW);
      AbstractArrow â˜ƒx = â˜ƒ.createArrow(â˜ƒ.level, â˜ƒ, â˜ƒ);
      â˜ƒx.setEnchantmentEffectsFromEntity(â˜ƒ, â˜ƒ);
      if (â˜ƒ.is(Items.TIPPED_ARROW) && â˜ƒx instanceof Arrow) {
         ((Arrow)â˜ƒx).setEffectsFromItem(â˜ƒ);
      }

      return â˜ƒx;
   }
}
