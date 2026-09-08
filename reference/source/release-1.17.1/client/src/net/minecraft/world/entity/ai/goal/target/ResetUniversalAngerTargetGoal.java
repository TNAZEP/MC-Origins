package net.minecraft.world.entity.ai.goal.target;

import java.util.List;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.phys.AABB;

public class ResetUniversalAngerTargetGoal<T extends Mob & NeutralMob> extends Goal {
   private static final int ALERT_RANGE_Y = 10;
   private final T mob;
   private final boolean alertOthersOfSameType;
   private int lastHurtByPlayerTimestamp;

   public ResetUniversalAngerTargetGoal(T var1, boolean var2) {
      this.mob = â˜ƒ;
      this.alertOthersOfSameType = â˜ƒ;
   }

   @Override
   public boolean canUse() {
      return this.mob.level.getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER) && this.wasHurtByPlayer();
   }

   private boolean wasHurtByPlayer() {
      return this.mob.getLastHurtByMob() != null
         && this.mob.getLastHurtByMob().getType() == EntityType.PLAYER
         && this.mob.getLastHurtByMobTimestamp() > this.lastHurtByPlayerTimestamp;
   }

   @Override
   public void start() {
      this.lastHurtByPlayerTimestamp = this.mob.getLastHurtByMobTimestamp();
      this.mob.forgetCurrentTargetAndRefreshUniversalAnger();
      if (this.alertOthersOfSameType) {
         this.getNearbyMobsOfSameType()
            .stream()
            .filter(var1 -> var1 != this.mob)
            .map(var0 -> (NeutralMob)var0)
            .forEach(NeutralMob::forgetCurrentTargetAndRefreshUniversalAnger);
      }

      super.start();
   }

   private List<? extends Mob> getNearbyMobsOfSameType() {
      double â˜ƒ = this.mob.getAttributeValue(Attributes.FOLLOW_RANGE);
      AABB â˜ƒx = AABB.unitCubeFromLowerCorner(this.mob.position()).inflate(â˜ƒ, 10.0, â˜ƒ);
      return this.mob.level.getEntitiesOfClass(this.mob.getClass(), â˜ƒx, EntitySelector.NO_SPECTATORS);
   }
}
