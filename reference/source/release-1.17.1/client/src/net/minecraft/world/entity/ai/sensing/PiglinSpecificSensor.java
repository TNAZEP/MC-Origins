package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;

public class PiglinSpecificSensor extends Sensor<LivingEntity> {
   @Override
   public Set<MemoryModuleType<?>> requires() {
      return ImmutableSet.of(
         MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
         MemoryModuleType.NEAREST_LIVING_ENTITIES,
         MemoryModuleType.NEAREST_VISIBLE_NEMESIS,
         MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD,
         MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM,
         MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN,
         MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN,
         MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS,
         MemoryModuleType.NEARBY_ADULT_PIGLINS,
         MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT,
         MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT,
         MemoryModuleType.NEAREST_REPELLENT
      );
   }

   @Override
   protected void doTick(ServerLevel var1, LivingEntity var2) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      â˜ƒ.setMemory(MemoryModuleType.NEAREST_REPELLENT, findNearestRepellent(â˜ƒ, â˜ƒ));
      Optional<Mob> â˜ƒx = Optional.empty();
      Optional<Hoglin> â˜ƒxx = Optional.empty();
      Optional<Hoglin> â˜ƒxxx = Optional.empty();
      Optional<Piglin> â˜ƒxxxx = Optional.empty();
      Optional<LivingEntity> â˜ƒxxxxx = Optional.empty();
      Optional<Player> â˜ƒxxxxxx = Optional.empty();
      Optional<Player> â˜ƒxxxxxxx = Optional.empty();
      int â˜ƒxxxxxxxx = 0;
      List<AbstractPiglin> â˜ƒxxxxxxxxx = Lists.<AbstractPiglin>newArrayList();
      List<AbstractPiglin> â˜ƒxxxxxxxxxx = Lists.<AbstractPiglin>newArrayList();

      for(LivingEntity â˜ƒxxxxxxxxxxx : (List)â˜ƒ.getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).orElse(ImmutableList.of())) {
         if (â˜ƒxxxxxxxxxxx instanceof Hoglin â˜ƒxxxxxxxxxxxx) {
            if (â˜ƒxxxxxxxxxxxx.isBaby() && !â˜ƒxxx.isPresent()) {
               â˜ƒxxx = Optional.of(â˜ƒxxxxxxxxxxxx);
            } else if (â˜ƒxxxxxxxxxxxx.isAdult()) {
               ++â˜ƒxxxxxxxx;
               if (!â˜ƒxx.isPresent() && â˜ƒxxxxxxxxxxxx.canBeHunted()) {
                  â˜ƒxx = Optional.of(â˜ƒxxxxxxxxxxxx);
               }
            }
         } else if (â˜ƒxxxxxxxxxxx instanceof PiglinBrute) {
            â˜ƒxxxxxxxxx.add((PiglinBrute)â˜ƒxxxxxxxxxxx);
         } else if (â˜ƒxxxxxxxxxxx instanceof Piglin â˜ƒxxxxxxxxxxxx) {
            if (â˜ƒxxxxxxxxxxxx.isBaby() && !â˜ƒxxxx.isPresent()) {
               â˜ƒxxxx = Optional.of(â˜ƒxxxxxxxxxxxx);
            } else if (â˜ƒxxxxxxxxxxxx.isAdult()) {
               â˜ƒxxxxxxxxx.add(â˜ƒxxxxxxxxxxxx);
            }
         } else if (â˜ƒxxxxxxxxxxx instanceof Player â˜ƒxxxxxxxxxxxx) {
            if (!â˜ƒxxxxxx.isPresent() && â˜ƒ.canAttack(â˜ƒxxxxxxxxxxx) && !PiglinAi.isWearingGold(â˜ƒxxxxxxxxxxxx)) {
               â˜ƒxxxxxx = Optional.of(â˜ƒxxxxxxxxxxxx);
            }

            if (!â˜ƒxxxxxxx.isPresent() && !â˜ƒxxxxxxxxxxxx.isSpectator() && PiglinAi.isPlayerHoldingLovedItem(â˜ƒxxxxxxxxxxxx)) {
               â˜ƒxxxxxxx = Optional.of(â˜ƒxxxxxxxxxxxx);
            }
         } else if (â˜ƒx.isPresent() || !(â˜ƒxxxxxxxxxxx instanceof WitherSkeleton) && !(â˜ƒxxxxxxxxxxx instanceof WitherBoss)) {
            if (!â˜ƒxxxxx.isPresent() && PiglinAi.isZombified(â˜ƒxxxxxxxxxxx.getType())) {
               â˜ƒxxxxx = Optional.of(â˜ƒxxxxxxxxxxx);
            }
         } else {
            â˜ƒx = Optional.of((Mob)â˜ƒxxxxxxxxxxx);
         }
      }

      for(LivingEntity â˜ƒxxxxxxxxxxx : (List)â˜ƒ.getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES).orElse(ImmutableList.of())) {
         if (â˜ƒxxxxxxxxxxx instanceof AbstractPiglin && ((AbstractPiglin)â˜ƒxxxxxxxxxxx).isAdult()) {
            â˜ƒxxxxxxxxxx.add((AbstractPiglin)â˜ƒxxxxxxxxxxx);
         }
      }

      â˜ƒ.setMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS, â˜ƒx);
      â˜ƒ.setMemory(MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN, â˜ƒxx);
      â˜ƒ.setMemory(MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN, â˜ƒxxx);
      â˜ƒ.setMemory(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, â˜ƒxxxxx);
      â˜ƒ.setMemory(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD, â˜ƒxxxxxx);
      â˜ƒ.setMemory(MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM, â˜ƒxxxxxxx);
      â˜ƒ.setMemory(MemoryModuleType.NEARBY_ADULT_PIGLINS, â˜ƒxxxxxxxxxx);
      â˜ƒ.setMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS, â˜ƒxxxxxxxxx);
      â˜ƒ.setMemory(MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, â˜ƒxxxxxxxxx.size());
      â˜ƒ.setMemory(MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, â˜ƒxxxxxxxx);
   }

   private static Optional<BlockPos> findNearestRepellent(ServerLevel var0, LivingEntity var1) {
      return BlockPos.findClosestMatch(â˜ƒ.blockPosition(), 8, 4, var1x -> isValidRepellent(â˜ƒ, var1x));
   }

   private static boolean isValidRepellent(ServerLevel var0, BlockPos var1) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      boolean â˜ƒx = â˜ƒ.is(BlockTags.PIGLIN_REPELLENTS);
      return â˜ƒx && â˜ƒ.is(Blocks.SOUL_CAMPFIRE) ? CampfireBlock.isLitCampfire(â˜ƒ) : â˜ƒx;
   }
}
