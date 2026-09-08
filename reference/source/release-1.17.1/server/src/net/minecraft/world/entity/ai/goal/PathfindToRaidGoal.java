package net.minecraft.world.entity.ai.goal;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.raid.Raids;
import net.minecraft.world.phys.Vec3;

public class PathfindToRaidGoal<T extends Raider> extends Goal {
   private static final float SPEED_MODIFIER = 1.0F;
   private final T mob;

   public PathfindToRaidGoal(T var1) {
      this.mob = â˜ƒ;
      this.setFlags(EnumSet.of(Goal.Flag.MOVE));
   }

   @Override
   public boolean canUse() {
      return this.mob.getTarget() == null
         && !this.mob.isVehicle()
         && this.mob.hasActiveRaid()
         && !this.mob.getCurrentRaid().isOver()
         && !((ServerLevel)this.mob.level).isVillage(this.mob.blockPosition());
   }

   @Override
   public boolean canContinueToUse() {
      return this.mob.hasActiveRaid()
         && !this.mob.getCurrentRaid().isOver()
         && this.mob.level instanceof ServerLevel
         && !((ServerLevel)this.mob.level).isVillage(this.mob.blockPosition());
   }

   @Override
   public void tick() {
      if (this.mob.hasActiveRaid()) {
         Raid â˜ƒ = this.mob.getCurrentRaid();
         if (this.mob.tickCount % 20 == 0) {
            this.recruitNearby(â˜ƒ);
         }

         if (!this.mob.isPathFinding()) {
            Vec3 â˜ƒ = DefaultRandomPos.getPosTowards(this.mob, 15, 4, Vec3.atBottomCenterOf(â˜ƒ.getCenter()), (float) (Math.PI / 2));
            if (â˜ƒ != null) {
               this.mob.getNavigation().moveTo(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z, 1.0);
            }
         }
      }
   }

   private void recruitNearby(Raid var1) {
      if (â˜ƒ.isActive()) {
         Set<Raider> â˜ƒ = Sets.<Raider>newHashSet();
         List<Raider> â˜ƒx = this.mob
            .level
            .getEntitiesOfClass(Raider.class, this.mob.getBoundingBox().inflate(16.0), var1x -> !var1x.hasActiveRaid() && Raids.canJoinRaid(var1x, â˜ƒ));
         â˜ƒ.addAll(â˜ƒx);

         for(Raider â˜ƒxx : â˜ƒ) {
            â˜ƒ.joinRaid(â˜ƒ.getGroupsSpawned(), â˜ƒxx, null, true);
         }
      }
   }
}
