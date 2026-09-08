package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import java.util.function.Predicate;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class AvoidEntityGoal<T extends LivingEntity> extends Goal {
   protected final PathfinderMob mob;
   private final double walkSpeedModifier;
   private final double sprintSpeedModifier;
   protected T toAvoid;
   protected final float maxDist;
   protected Path path;
   protected final PathNavigation pathNav;
   protected final Class<T> avoidClass;
   protected final Predicate<LivingEntity> avoidPredicate;
   protected final Predicate<LivingEntity> predicateOnAvoidEntity;
   private final TargetingConditions avoidEntityTargeting;

   public AvoidEntityGoal(PathfinderMob var1, Class<T> var2, float var3, double var4, double var6) {
      this(â˜ƒ, â˜ƒ, var0 -> true, â˜ƒ, â˜ƒ, â˜ƒ, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
   }

   public AvoidEntityGoal(PathfinderMob var1, Class<T> var2, Predicate<LivingEntity> var3, float var4, double var5, double var7, Predicate<LivingEntity> var9) {
      this.mob = â˜ƒ;
      this.avoidClass = â˜ƒ;
      this.avoidPredicate = â˜ƒ;
      this.maxDist = â˜ƒ;
      this.walkSpeedModifier = â˜ƒ;
      this.sprintSpeedModifier = â˜ƒ;
      this.predicateOnAvoidEntity = â˜ƒ;
      this.pathNav = â˜ƒ.getNavigation();
      this.setFlags(EnumSet.of(Goal.Flag.MOVE));
      this.avoidEntityTargeting = TargetingConditions.forCombat().range((double)â˜ƒ).selector(â˜ƒ.and(â˜ƒ));
   }

   public AvoidEntityGoal(PathfinderMob var1, Class<T> var2, float var3, double var4, double var6, Predicate<LivingEntity> var8) {
      this(â˜ƒ, â˜ƒ, var0 -> true, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean canUse() {
      this.toAvoid = this.mob
         .level
         .getNearestEntity(
            this.mob
               .level
               .getEntitiesOfClass(this.avoidClass, this.mob.getBoundingBox().inflate((double)this.maxDist, 3.0, (double)this.maxDist), var0 -> true),
            this.avoidEntityTargeting,
            this.mob,
            this.mob.getX(),
            this.mob.getY(),
            this.mob.getZ()
         );
      if (this.toAvoid == null) {
         return false;
      } else {
         Vec3 â˜ƒ = DefaultRandomPos.getPosAway(this.mob, 16, 7, this.toAvoid.position());
         if (â˜ƒ == null) {
            return false;
         } else if (this.toAvoid.distanceToSqr(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z) < this.toAvoid.distanceToSqr(this.mob)) {
            return false;
         } else {
            this.path = this.pathNav.createPath(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z, 0);
            return this.path != null;
         }
      }
   }

   @Override
   public boolean canContinueToUse() {
      return !this.pathNav.isDone();
   }

   @Override
   public void start() {
      this.pathNav.moveTo(this.path, this.walkSpeedModifier);
   }

   @Override
   public void stop() {
      this.toAvoid = null;
   }

   @Override
   public void tick() {
      if (this.mob.distanceToSqr(this.toAvoid) < 49.0) {
         this.mob.getNavigation().setSpeedModifier(this.sprintSpeedModifier);
      } else {
         this.mob.getNavigation().setSpeedModifier(this.walkSpeedModifier);
      }
   }
}
