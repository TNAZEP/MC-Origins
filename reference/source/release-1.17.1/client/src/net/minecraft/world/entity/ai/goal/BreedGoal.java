package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

public class BreedGoal extends Goal {
   private static final TargetingConditions PARTNER_TARGETING = TargetingConditions.forNonCombat().range(8.0).ignoreLineOfSight();
   protected final Animal animal;
   private final Class<? extends Animal> partnerClass;
   protected final Level level;
   protected Animal partner;
   private int loveTime;
   private final double speedModifier;

   public BreedGoal(Animal var1, double var2) {
      this(â˜ƒ, â˜ƒ, â˜ƒ.getClass());
   }

   public BreedGoal(Animal var1, double var2, Class<? extends Animal> var4) {
      this.animal = â˜ƒ;
      this.level = â˜ƒ.level;
      this.partnerClass = â˜ƒ;
      this.speedModifier = â˜ƒ;
      this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
   }

   @Override
   public boolean canUse() {
      if (!this.animal.isInLove()) {
         return false;
      } else {
         this.partner = this.getFreePartner();
         return this.partner != null;
      }
   }

   @Override
   public boolean canContinueToUse() {
      return this.partner.isAlive() && this.partner.isInLove() && this.loveTime < 60;
   }

   @Override
   public void stop() {
      this.partner = null;
      this.loveTime = 0;
   }

   @Override
   public void tick() {
      this.animal.getLookControl().setLookAt(this.partner, 10.0F, (float)this.animal.getMaxHeadXRot());
      this.animal.getNavigation().moveTo(this.partner, this.speedModifier);
      ++this.loveTime;
      if (this.loveTime >= 60 && this.animal.distanceToSqr(this.partner) < 9.0) {
         this.breed();
      }
   }

   @Nullable
   private Animal getFreePartner() {
      List<? extends Animal> â˜ƒ = this.level.getNearbyEntities(this.partnerClass, PARTNER_TARGETING, this.animal, this.animal.getBoundingBox().inflate(8.0));
      double â˜ƒx = Double.MAX_VALUE;
      Animal â˜ƒxx = null;

      for(Animal â˜ƒxxx : â˜ƒ) {
         if (this.animal.canMate(â˜ƒxxx) && this.animal.distanceToSqr(â˜ƒxxx) < â˜ƒx) {
            â˜ƒxx = â˜ƒxxx;
            â˜ƒx = this.animal.distanceToSqr(â˜ƒxxx);
         }
      }

      return â˜ƒxx;
   }

   protected void breed() {
      this.animal.spawnChildFromBreeding((ServerLevel)this.level, this.partner);
   }
}
