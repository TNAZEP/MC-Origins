package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.pathfinder.Path;

public class VillagerMakeLove extends Behavior<Villager> {
   private static final int INTERACT_DIST_SQR = 5;
   private static final float SPEED_MODIFIER = 0.5F;
   private long birthTimestamp;

   public VillagerMakeLove() {
      super(
         ImmutableMap.of(
            MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_PRESENT, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryStatus.VALUE_PRESENT
         ),
         350,
         350
      );
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, Villager var2) {
      return this.isBreedingPossible(â˜ƒ);
   }

   protected boolean canStillUse(ServerLevel var1, Villager var2, long var3) {
      return â˜ƒ <= this.birthTimestamp && this.isBreedingPossible(â˜ƒ);
   }

   protected void start(ServerLevel var1, Villager var2, long var3) {
      AgeableMob â˜ƒ = (AgeableMob)â˜ƒ.getBrain().getMemory(MemoryModuleType.BREED_TARGET).get();
      BehaviorUtils.lockGazeAndWalkToEachOther(â˜ƒ, â˜ƒ, 0.5F);
      â˜ƒ.broadcastEntityEvent(â˜ƒ, (byte)18);
      â˜ƒ.broadcastEntityEvent(â˜ƒ, (byte)18);
      int â˜ƒx = 275 + â˜ƒ.getRandom().nextInt(50);
      this.birthTimestamp = â˜ƒ + (long)â˜ƒx;
   }

   protected void tick(ServerLevel var1, Villager var2, long var3) {
      Villager â˜ƒ = (Villager)â˜ƒ.getBrain().getMemory(MemoryModuleType.BREED_TARGET).get();
      if (!(â˜ƒ.distanceToSqr(â˜ƒ) > 5.0)) {
         BehaviorUtils.lockGazeAndWalkToEachOther(â˜ƒ, â˜ƒ, 0.5F);
         if (â˜ƒ >= this.birthTimestamp) {
            â˜ƒ.eatAndDigestFood();
            â˜ƒ.eatAndDigestFood();
            this.tryToGiveBirth(â˜ƒ, â˜ƒ, â˜ƒ);
         } else if (â˜ƒ.getRandom().nextInt(35) == 0) {
            â˜ƒ.broadcastEntityEvent(â˜ƒ, (byte)12);
            â˜ƒ.broadcastEntityEvent(â˜ƒ, (byte)12);
         }
      }
   }

   private void tryToGiveBirth(ServerLevel var1, Villager var2, Villager var3) {
      Optional<BlockPos> â˜ƒ = this.takeVacantBed(â˜ƒ, â˜ƒ);
      if (!â˜ƒ.isPresent()) {
         â˜ƒ.broadcastEntityEvent(â˜ƒ, (byte)13);
         â˜ƒ.broadcastEntityEvent(â˜ƒ, (byte)13);
      } else {
         Optional<Villager> â˜ƒ = this.breed(â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒ.isPresent()) {
            this.giveBedToChild(â˜ƒ, (Villager)â˜ƒ.get(), (BlockPos)â˜ƒ.get());
         } else {
            â˜ƒ.getPoiManager().release((BlockPos)â˜ƒ.get());
            DebugPackets.sendPoiTicketCountPacket(â˜ƒ, (BlockPos)â˜ƒ.get());
         }
      }
   }

   protected void stop(ServerLevel var1, Villager var2, long var3) {
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.BREED_TARGET);
   }

   private boolean isBreedingPossible(Villager var1) {
      Brain<Villager> â˜ƒ = â˜ƒ.getBrain();
      Optional<AgeableMob> â˜ƒx = â˜ƒ.getMemory(MemoryModuleType.BREED_TARGET).filter(var0 -> var0.getType() == EntityType.VILLAGER);
      if (!â˜ƒx.isPresent()) {
         return false;
      } else {
         return BehaviorUtils.targetIsValid(â˜ƒ, MemoryModuleType.BREED_TARGET, EntityType.VILLAGER) && â˜ƒ.canBreed() && ((AgeableMob)â˜ƒx.get()).canBreed();
      }
   }

   private Optional<BlockPos> takeVacantBed(ServerLevel var1, Villager var2) {
      return â˜ƒ.getPoiManager().take(PoiType.HOME.getPredicate(), var2x -> this.canReach(â˜ƒ, var2x), â˜ƒ.blockPosition(), 48);
   }

   private boolean canReach(Villager var1, BlockPos var2) {
      Path â˜ƒ = â˜ƒ.getNavigation().createPath(â˜ƒ, PoiType.HOME.getValidRange());
      return â˜ƒ != null && â˜ƒ.canReach();
   }

   private Optional<Villager> breed(ServerLevel var1, Villager var2, Villager var3) {
      Villager â˜ƒ = â˜ƒ.getBreedOffspring(â˜ƒ, â˜ƒ);
      if (â˜ƒ == null) {
         return Optional.empty();
      } else {
         â˜ƒ.setAge(6000);
         â˜ƒ.setAge(6000);
         â˜ƒ.setAge(-24000);
         â˜ƒ.moveTo(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), 0.0F, 0.0F);
         â˜ƒ.addFreshEntityWithPassengers(â˜ƒ);
         â˜ƒ.broadcastEntityEvent(â˜ƒ, (byte)12);
         return Optional.of(â˜ƒ);
      }
   }

   private void giveBedToChild(ServerLevel var1, Villager var2, BlockPos var3) {
      GlobalPos â˜ƒ = GlobalPos.of(â˜ƒ.dimension(), â˜ƒ);
      â˜ƒ.getBrain().setMemory(MemoryModuleType.HOME, â˜ƒ);
   }
}
