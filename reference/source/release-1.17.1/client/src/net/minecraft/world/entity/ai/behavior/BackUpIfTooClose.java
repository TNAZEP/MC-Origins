package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class BackUpIfTooClose<E extends Mob> extends Behavior<E> {
   private final int tooCloseDistance;
   private final float strafeSpeed;

   public BackUpIfTooClose(int var1, float var2) {
      super(
         ImmutableMap.of(
            MemoryModuleType.WALK_TARGET,
            MemoryStatus.VALUE_ABSENT,
            MemoryModuleType.LOOK_TARGET,
            MemoryStatus.REGISTERED,
            MemoryModuleType.ATTACK_TARGET,
            MemoryStatus.VALUE_PRESENT,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryStatus.VALUE_PRESENT
         )
      );
      this.tooCloseDistance = â˜ƒ;
      this.strafeSpeed = â˜ƒ;
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, E var2) {
      return this.isTargetVisible(â˜ƒ) && this.isTargetTooClose(â˜ƒ);
   }

   protected void start(ServerLevel var1, E var2, long var3) {
      â˜ƒ.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(this.getTarget(â˜ƒ), true));
      â˜ƒ.getMoveControl().strafe(-this.strafeSpeed, 0.0F);
      â˜ƒ.setYRot(Mth.rotateIfNecessary(â˜ƒ.getYRot(), â˜ƒ.yHeadRot, 0.0F));
   }

   private boolean isTargetVisible(E var1) {
      return ((List)â˜ƒ.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).get()).contains(this.getTarget(â˜ƒ));
   }

   private boolean isTargetTooClose(E var1) {
      return this.getTarget(â˜ƒ).closerThan(â˜ƒ, (double)this.tooCloseDistance);
   }

   private LivingEntity getTarget(E var1) {
      return (LivingEntity)â˜ƒ.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get();
   }
}
