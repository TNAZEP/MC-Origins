package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.Util;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.player.Player;

public class FollowTemptation extends Behavior<PathfinderMob> {
   public static final int TEMPTATION_COOLDOWN = 100;
   public static final double CLOSE_ENOUGH_DIST = 2.5;
   private final Function<LivingEntity, Float> speedModifier;

   public FollowTemptation(Function<LivingEntity, Float> var1) {
      super(Util.make(() -> {
         Builder<MemoryModuleType<?>, MemoryStatus> â˜ƒ = ImmutableMap.builder();
         â˜ƒ.put(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED);
         â˜ƒ.put(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED);
         â˜ƒ.put(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT);
         â˜ƒ.put(MemoryModuleType.IS_TEMPTED, MemoryStatus.REGISTERED);
         â˜ƒ.put(MemoryModuleType.TEMPTING_PLAYER, MemoryStatus.VALUE_PRESENT);
         â˜ƒ.put(MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_ABSENT);
         return â˜ƒ.build();
      }));
      this.speedModifier = â˜ƒ;
   }

   protected float getSpeedModifier(PathfinderMob var1) {
      return this.speedModifier.apply(â˜ƒ);
   }

   private Optional<Player> getTemptingPlayer(PathfinderMob var1) {
      return â˜ƒ.getBrain().getMemory(MemoryModuleType.TEMPTING_PLAYER);
   }

   @Override
   protected boolean timedOut(long var1) {
      return false;
   }

   protected boolean canStillUse(ServerLevel var1, PathfinderMob var2, long var3) {
      return this.getTemptingPlayer(â˜ƒ).isPresent() && !â˜ƒ.getBrain().hasMemoryValue(MemoryModuleType.BREED_TARGET);
   }

   protected void start(ServerLevel var1, PathfinderMob var2, long var3) {
      â˜ƒ.getBrain().setMemory(MemoryModuleType.IS_TEMPTED, true);
   }

   protected void stop(ServerLevel var1, PathfinderMob var2, long var3) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      â˜ƒ.setMemory(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, 100);
      â˜ƒ.setMemory(MemoryModuleType.IS_TEMPTED, false);
      â˜ƒ.eraseMemory(MemoryModuleType.WALK_TARGET);
      â˜ƒ.eraseMemory(MemoryModuleType.LOOK_TARGET);
   }

   protected void tick(ServerLevel var1, PathfinderMob var2, long var3) {
      Player â˜ƒ = (Player)this.getTemptingPlayer(â˜ƒ).get();
      Brain<?> â˜ƒx = â˜ƒ.getBrain();
      â˜ƒx.setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(â˜ƒ, true));
      if (â˜ƒ.distanceToSqr(â˜ƒ) < 6.25) {
         â˜ƒx.eraseMemory(MemoryModuleType.WALK_TARGET);
      } else {
         â˜ƒx.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityTracker(â˜ƒ, false), this.getSpeedModifier(â˜ƒ), 2));
      }
   }
}
