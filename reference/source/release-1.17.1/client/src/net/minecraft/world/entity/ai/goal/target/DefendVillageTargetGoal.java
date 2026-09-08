package net.minecraft.world.entity.ai.goal.target;

import java.util.EnumSet;
import java.util.List;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class DefendVillageTargetGoal extends TargetGoal {
   private final IronGolem golem;
   private LivingEntity potentialTarget;
   private final TargetingConditions attackTargeting = TargetingConditions.forCombat().range(64.0);

   public DefendVillageTargetGoal(IronGolem var1) {
      super(â˜ƒ, false, true);
      this.golem = â˜ƒ;
      this.setFlags(EnumSet.of(Goal.Flag.TARGET));
   }

   @Override
   public boolean canUse() {
      AABB â˜ƒ = this.golem.getBoundingBox().inflate(10.0, 8.0, 10.0);
      List<? extends LivingEntity> â˜ƒx = this.golem.level.getNearbyEntities(Villager.class, this.attackTargeting, this.golem, â˜ƒ);
      List<Player> â˜ƒxx = this.golem.level.getNearbyPlayers(this.attackTargeting, this.golem, â˜ƒ);

      for(LivingEntity â˜ƒxxx : â˜ƒx) {
         Villager â˜ƒxxxx = (Villager)â˜ƒxxx;

         for(Player â˜ƒxxxxx : â˜ƒxx) {
            int â˜ƒxxxxxx = â˜ƒxxxx.getPlayerReputation(â˜ƒxxxxx);
            if (â˜ƒxxxxxx <= -100) {
               this.potentialTarget = â˜ƒxxxxx;
            }
         }
      }

      if (this.potentialTarget == null) {
         return false;
      } else {
         return !(this.potentialTarget instanceof Player) || !this.potentialTarget.isSpectator() && !((Player)this.potentialTarget).isCreative();
      }
   }

   @Override
   public void start() {
      this.golem.setTarget(this.potentialTarget);
      super.start();
   }
}
