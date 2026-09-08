package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

public class PlayTagWithOtherKids extends Behavior<PathfinderMob> {
   private static final int MAX_FLEE_XZ_DIST = 20;
   private static final int MAX_FLEE_Y_DIST = 8;
   private static final float FLEE_SPEED_MODIFIER = 0.6F;
   private static final float CHASE_SPEED_MODIFIER = 0.6F;
   private static final int MAX_CHASERS_PER_TARGET = 5;
   private static final int AVERAGE_WAIT_TIME_BETWEEN_RUNS = 10;

   public PlayTagWithOtherKids() {
      super(
         ImmutableMap.of(
            MemoryModuleType.VISIBLE_VILLAGER_BABIES,
            MemoryStatus.VALUE_PRESENT,
            MemoryModuleType.WALK_TARGET,
            MemoryStatus.VALUE_ABSENT,
            MemoryModuleType.LOOK_TARGET,
            MemoryStatus.REGISTERED,
            MemoryModuleType.INTERACTION_TARGET,
            MemoryStatus.REGISTERED
         )
      );
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, PathfinderMob var2) {
      return â˜ƒ.getRandom().nextInt(10) == 0 && this.hasFriendsNearby(â˜ƒ);
   }

   protected void start(ServerLevel var1, PathfinderMob var2, long var3) {
      LivingEntity â˜ƒ = this.seeIfSomeoneIsChasingMe(â˜ƒ);
      if (â˜ƒ != null) {
         this.fleeFromChaser(â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         Optional<LivingEntity> â˜ƒ = this.findSomeoneBeingChased(â˜ƒ);
         if (â˜ƒ.isPresent()) {
            chaseKid(â˜ƒ, (LivingEntity)â˜ƒ.get());
         } else {
            this.findSomeoneToChase(â˜ƒ).ifPresent(var1x -> chaseKid(â˜ƒ, var1x));
         }
      }
   }

   private void fleeFromChaser(ServerLevel var1, PathfinderMob var2, LivingEntity var3) {
      for(int â˜ƒ = 0; â˜ƒ < 10; ++â˜ƒ) {
         Vec3 â˜ƒx = LandRandomPos.getPos(â˜ƒ, 20, 8);
         if (â˜ƒx != null && â˜ƒ.isVillage(new BlockPos(â˜ƒx))) {
            â˜ƒ.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(â˜ƒx, 0.6F, 0));
            return;
         }
      }
   }

   private static void chaseKid(PathfinderMob var0, LivingEntity var1) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      â˜ƒ.setMemory(MemoryModuleType.INTERACTION_TARGET, â˜ƒ);
      â˜ƒ.setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(â˜ƒ, true));
      â˜ƒ.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityTracker(â˜ƒ, false), 0.6F, 1));
   }

   private Optional<LivingEntity> findSomeoneToChase(PathfinderMob var1) {
      return this.getFriendsNearby(â˜ƒ).stream().findAny();
   }

   private Optional<LivingEntity> findSomeoneBeingChased(PathfinderMob var1) {
      Map<LivingEntity, Integer> â˜ƒ = this.checkHowManyChasersEachFriendHas(â˜ƒ);
      return â˜ƒ.entrySet()
         .stream()
         .sorted(Comparator.comparingInt(Entry::getValue))
         .filter(var0 -> var0.getValue() > 0 && var0.getValue() <= 5)
         .map(Entry::getKey)
         .findFirst();
   }

   private Map<LivingEntity, Integer> checkHowManyChasersEachFriendHas(PathfinderMob var1) {
      Map<LivingEntity, Integer> â˜ƒ = Maps.newHashMap();
      this.getFriendsNearby(â˜ƒ)
         .stream()
         .filter(this::isChasingSomeone)
         .forEach(var2x -> â˜ƒ.compute(this.whoAreYouChasing(var2x), (var0, var1x) -> var1x == null ? 1 : var1x + 1));
      return â˜ƒ;
   }

   private List<LivingEntity> getFriendsNearby(PathfinderMob var1) {
      return (List<LivingEntity>)â˜ƒ.getBrain().getMemory(MemoryModuleType.VISIBLE_VILLAGER_BABIES).get();
   }

   private LivingEntity whoAreYouChasing(LivingEntity var1) {
      return (LivingEntity)â˜ƒ.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).get();
   }

   @Nullable
   private LivingEntity seeIfSomeoneIsChasingMe(LivingEntity var1) {
      return (LivingEntity)((List)â˜ƒ.getBrain().getMemory(MemoryModuleType.VISIBLE_VILLAGER_BABIES).get())
         .stream()
         .filter(var2 -> this.isFriendChasingMe(â˜ƒ, var2))
         .findAny()
         .orElse(null);
   }

   private boolean isChasingSomeone(LivingEntity var1) {
      return â˜ƒ.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).isPresent();
   }

   private boolean isFriendChasingMe(LivingEntity var1, LivingEntity var2) {
      return â˜ƒ.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).filter(var1x -> var1x == â˜ƒ).isPresent();
   }

   private boolean hasFriendsNearby(PathfinderMob var1) {
      return â˜ƒ.getBrain().hasMemoryValue(MemoryModuleType.VISIBLE_VILLAGER_BABIES);
   }
}
