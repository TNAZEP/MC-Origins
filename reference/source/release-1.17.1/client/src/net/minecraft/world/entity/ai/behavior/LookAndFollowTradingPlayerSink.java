package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;

public class LookAndFollowTradingPlayerSink extends Behavior<Villager> {
   private final float speedModifier;

   public LookAndFollowTradingPlayerSink(float var1) {
      super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED), Integer.MAX_VALUE);
      this.speedModifier = â˜ƒ;
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, Villager var2) {
      Player â˜ƒ = â˜ƒ.getTradingPlayer();
      return â˜ƒ.isAlive() && â˜ƒ != null && !â˜ƒ.isInWater() && !â˜ƒ.hurtMarked && â˜ƒ.distanceToSqr(â˜ƒ) <= 16.0 && â˜ƒ.containerMenu != null;
   }

   protected boolean canStillUse(ServerLevel var1, Villager var2, long var3) {
      return this.checkExtraStartConditions(â˜ƒ, â˜ƒ);
   }

   protected void start(ServerLevel var1, Villager var2, long var3) {
      this.followPlayer(â˜ƒ);
   }

   protected void stop(ServerLevel var1, Villager var2, long var3) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      â˜ƒ.eraseMemory(MemoryModuleType.WALK_TARGET);
      â˜ƒ.eraseMemory(MemoryModuleType.LOOK_TARGET);
   }

   protected void tick(ServerLevel var1, Villager var2, long var3) {
      this.followPlayer(â˜ƒ);
   }

   @Override
   protected boolean timedOut(long var1) {
      return false;
   }

   private void followPlayer(Villager var1) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      â˜ƒ.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityTracker(â˜ƒ.getTradingPlayer(), false), this.speedModifier, 2));
      â˜ƒ.setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(â˜ƒ.getTradingPlayer(), true));
   }
}
