package net.minecraft.world.entity.ai.behavior;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;

public class BehaviorUtils {
   public static void lockGazeAndWalkToEachOther(LivingEntity var0, LivingEntity var1, float var2) {
      lookAtEachOther(â˜ƒ, â˜ƒ);
      setWalkAndLookTargetMemoriesToEachOther(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static boolean entityIsVisible(Brain<?> var0, LivingEntity var1) {
      return â˜ƒ.getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).filter(var1x -> var1x.contains(â˜ƒ)).isPresent();
   }

   public static boolean targetIsValid(Brain<?> var0, MemoryModuleType<? extends LivingEntity> var1, EntityType<?> var2) {
      return targetIsValid(â˜ƒ, â˜ƒ, var1x -> var1x.getType() == â˜ƒ);
   }

   private static boolean targetIsValid(Brain<?> var0, MemoryModuleType<? extends LivingEntity> var1, Predicate<LivingEntity> var2) {
      return â˜ƒ.getMemory(â˜ƒ).filter(â˜ƒ).filter(LivingEntity::isAlive).filter(var1x -> entityIsVisible(â˜ƒ, var1x)).isPresent();
   }

   private static void lookAtEachOther(LivingEntity var0, LivingEntity var1) {
      lookAtEntity(â˜ƒ, â˜ƒ);
      lookAtEntity(â˜ƒ, â˜ƒ);
   }

   public static void lookAtEntity(LivingEntity var0, LivingEntity var1) {
      â˜ƒ.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(â˜ƒ, true));
   }

   private static void setWalkAndLookTargetMemoriesToEachOther(LivingEntity var0, LivingEntity var1, float var2) {
      int â˜ƒ = 2;
      setWalkAndLookTargetMemories(â˜ƒ, â˜ƒ, â˜ƒ, 2);
      setWalkAndLookTargetMemories(â˜ƒ, â˜ƒ, â˜ƒ, 2);
   }

   public static void setWalkAndLookTargetMemories(LivingEntity var0, Entity var1, float var2, int var3) {
      WalkTarget â˜ƒ = new WalkTarget(new EntityTracker(â˜ƒ, false), â˜ƒ, â˜ƒ);
      â˜ƒ.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(â˜ƒ, true));
      â˜ƒ.getBrain().setMemory(MemoryModuleType.WALK_TARGET, â˜ƒ);
   }

   public static void setWalkAndLookTargetMemories(LivingEntity var0, BlockPos var1, float var2, int var3) {
      WalkTarget â˜ƒ = new WalkTarget(new BlockPosTracker(â˜ƒ), â˜ƒ, â˜ƒ);
      â˜ƒ.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosTracker(â˜ƒ));
      â˜ƒ.getBrain().setMemory(MemoryModuleType.WALK_TARGET, â˜ƒ);
   }

   public static void throwItem(LivingEntity var0, ItemStack var1, Vec3 var2) {
      double â˜ƒ = â˜ƒ.getEyeY() - 0.3F;
      ItemEntity â˜ƒx = new ItemEntity(â˜ƒ.level, â˜ƒ.getX(), â˜ƒ, â˜ƒ.getZ(), â˜ƒ);
      float â˜ƒxx = 0.3F;
      Vec3 â˜ƒxxx = â˜ƒ.subtract(â˜ƒ.position());
      â˜ƒxxx = â˜ƒxxx.normalize().scale(0.3F);
      â˜ƒx.setDeltaMovement(â˜ƒxxx);
      â˜ƒx.setDefaultPickUpDelay();
      â˜ƒ.level.addFreshEntity(â˜ƒx);
   }

   public static SectionPos findSectionClosestToVillage(ServerLevel var0, SectionPos var1, int var2) {
      int â˜ƒ = â˜ƒ.sectionsToVillage(â˜ƒ);
      return (SectionPos)SectionPos.cube(â˜ƒ, â˜ƒ)
         .filter(var2x -> â˜ƒ.sectionsToVillage(var2x) < â˜ƒ)
         .min(Comparator.comparingInt(â˜ƒ::sectionsToVillage))
         .orElse(â˜ƒ);
   }

   public static boolean isWithinAttackRange(Mob var0, LivingEntity var1, int var2) {
      Item â˜ƒ = â˜ƒ.getMainHandItem().getItem();
      if (â˜ƒ instanceof ProjectileWeaponItem && â˜ƒ.canFireProjectileWeapon((ProjectileWeaponItem)â˜ƒ)) {
         int â˜ƒx = ((ProjectileWeaponItem)â˜ƒ).getDefaultProjectileRange() - â˜ƒ;
         return â˜ƒ.closerThan(â˜ƒ, (double)â˜ƒx);
      } else {
         return isWithinMeleeAttackRange(â˜ƒ, â˜ƒ);
      }
   }

   public static boolean isWithinMeleeAttackRange(Mob var0, LivingEntity var1) {
      double â˜ƒ = â˜ƒ.distanceToSqr(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
      return â˜ƒ <= â˜ƒ.getMeleeAttackRangeSqr(â˜ƒ);
   }

   public static boolean isOtherTargetMuchFurtherAwayThanCurrentAttackTarget(LivingEntity var0, LivingEntity var1, double var2) {
      Optional<LivingEntity> â˜ƒ = â˜ƒ.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
      if (!â˜ƒ.isPresent()) {
         return false;
      } else {
         double â˜ƒ = â˜ƒ.distanceToSqr(((LivingEntity)â˜ƒ.get()).position());
         double â˜ƒx = â˜ƒ.distanceToSqr(â˜ƒ.position());
         return â˜ƒx > â˜ƒ + â˜ƒ * â˜ƒ;
      }
   }

   public static boolean canSee(LivingEntity var0, LivingEntity var1) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      return !â˜ƒ.hasMemoryValue(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)
         ? false
         : ((List)â˜ƒ.getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).get()).contains(â˜ƒ);
   }

   public static LivingEntity getNearestTarget(LivingEntity var0, Optional<LivingEntity> var1, LivingEntity var2) {
      return !â˜ƒ.isPresent() ? â˜ƒ : getTargetNearestMe(â˜ƒ, (LivingEntity)â˜ƒ.get(), â˜ƒ);
   }

   public static LivingEntity getTargetNearestMe(LivingEntity var0, LivingEntity var1, LivingEntity var2) {
      Vec3 â˜ƒ = â˜ƒ.position();
      Vec3 â˜ƒx = â˜ƒ.position();
      return â˜ƒ.distanceToSqr(â˜ƒ) < â˜ƒ.distanceToSqr(â˜ƒx) ? â˜ƒ : â˜ƒ;
   }

   public static Optional<LivingEntity> getLivingEntityFromUUIDMemory(LivingEntity var0, MemoryModuleType<UUID> var1) {
      Optional<UUID> â˜ƒ = â˜ƒ.getBrain().getMemory(â˜ƒ);
      return â˜ƒ.map(var1x -> ((ServerLevel)â˜ƒ.level).getEntity(var1x)).map(var0x -> var0x instanceof LivingEntity ? (LivingEntity)var0x : null);
   }

   public static Stream<Villager> getNearbyVillagersWithCondition(Villager var0, Predicate<Villager> var1) {
      return (Stream<Villager>)â˜ƒ.getBrain()
         .getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES)
         .map(
            var2 -> var2.stream()
                  .filter(var1x -> var1x instanceof Villager && var1x != â˜ƒ)
                  .map(var0x -> (Villager)var0x)
                  .filter(LivingEntity::isAlive)
                  .filter(â˜ƒ)
         )
         .orElseGet(Stream::empty);
   }

   @Nullable
   public static Vec3 getRandomSwimmablePos(PathfinderMob var0, int var1, int var2) {
      Vec3 â˜ƒ = DefaultRandomPos.getPos(â˜ƒ, â˜ƒ, â˜ƒ);
      int â˜ƒx = 0;

      while(â˜ƒ != null && !â˜ƒ.level.getBlockState(new BlockPos(â˜ƒ)).isPathfindable(â˜ƒ.level, new BlockPos(â˜ƒ), PathComputationType.WATER) && â˜ƒx++ < 10) {
         â˜ƒ = DefaultRandomPos.getPos(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      return â˜ƒ;
   }
}
