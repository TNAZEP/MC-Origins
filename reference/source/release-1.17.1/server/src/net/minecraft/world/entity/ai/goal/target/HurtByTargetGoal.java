package net.minecraft.world.entity.ai.goal.target;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.phys.AABB;

public class HurtByTargetGoal extends TargetGoal {
   private static final TargetingConditions HURT_BY_TARGETING = TargetingConditions.forCombat().ignoreLineOfSight().ignoreInvisibilityTesting();
   private static final int ALERT_RANGE_Y = 10;
   private boolean alertSameType;
   private int timestamp;
   private final Class<?>[] toIgnoreDamage;
   private Class<?>[] toIgnoreAlert;

   public HurtByTargetGoal(PathfinderMob var1, Class<?>... var2) {
      super(â˜ƒ, true);
      this.toIgnoreDamage = â˜ƒ;
      this.setFlags(EnumSet.of(Goal.Flag.TARGET));
   }

   @Override
   public boolean canUse() {
      int â˜ƒ = this.mob.getLastHurtByMobTimestamp();
      LivingEntity â˜ƒx = this.mob.getLastHurtByMob();
      if (â˜ƒ != this.timestamp && â˜ƒx != null) {
         if (â˜ƒx.getType() == EntityType.PLAYER && this.mob.level.getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
            return false;
         } else {
            for(Class<?> â˜ƒxx : this.toIgnoreDamage) {
               if (â˜ƒxx.isAssignableFrom(â˜ƒx.getClass())) {
                  return false;
               }
            }

            return this.canAttack(â˜ƒx, HURT_BY_TARGETING);
         }
      } else {
         return false;
      }
   }

   public HurtByTargetGoal setAlertOthers(Class<?>... var1) {
      this.alertSameType = true;
      this.toIgnoreAlert = â˜ƒ;
      return this;
   }

   @Override
   public void start() {
      this.mob.setTarget(this.mob.getLastHurtByMob());
      this.targetMob = this.mob.getTarget();
      this.timestamp = this.mob.getLastHurtByMobTimestamp();
      this.unseenMemoryTicks = 300;
      if (this.alertSameType) {
         this.alertOthers();
      }

      super.start();
   }

   protected void alertOthers() {
      double â˜ƒ = this.getFollowDistance();
      AABB â˜ƒx = AABB.unitCubeFromLowerCorner(this.mob.position()).inflate(â˜ƒ, 10.0, â˜ƒ);
      List<? extends Mob> â˜ƒxx = this.mob.level.getEntitiesOfClass(this.mob.getClass(), â˜ƒx, EntitySelector.NO_SPECTATORS);
      Iterator var5 = â˜ƒxx.iterator();

      while(true) {
         Mob â˜ƒ;
         while(true) {
            if (!var5.hasNext()) {
               return;
            }

            â˜ƒ = (Mob)var5.next();
            if (this.mob != â˜ƒ
               && â˜ƒ.getTarget() == null
               && (!(this.mob instanceof TamableAnimal) || ((TamableAnimal)this.mob).getOwner() == ((TamableAnimal)â˜ƒ).getOwner())
               && !â˜ƒ.isAlliedTo(this.mob.getLastHurtByMob())) {
               if (this.toIgnoreAlert == null) {
                  break;
               }

               boolean â˜ƒxxx = false;

               for(Class<?> â˜ƒxxxx : this.toIgnoreAlert) {
                  if (â˜ƒ.getClass() == â˜ƒxxxx) {
                     â˜ƒxxx = true;
                     break;
                  }
               }

               if (!â˜ƒxxx) {
                  break;
               }
            }
         }

         this.alertOther(â˜ƒ, this.mob.getLastHurtByMob());
      }
   }

   protected void alertOther(Mob var1, LivingEntity var2) {
      â˜ƒ.setTarget(â˜ƒ);
   }
}
