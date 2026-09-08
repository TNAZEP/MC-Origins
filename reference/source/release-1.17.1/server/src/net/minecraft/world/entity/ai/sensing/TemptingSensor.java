package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class TemptingSensor extends Sensor<PathfinderMob> {
   public static final int TEMPTATION_RANGE = 10;
   private static final TargetingConditions TEMPT_TARGETING = TargetingConditions.forNonCombat().range(10.0).ignoreLineOfSight();
   private final Ingredient temptations;

   public TemptingSensor(Ingredient var1) {
      this.temptations = â˜ƒ;
   }

   protected void doTick(ServerLevel var1, PathfinderMob var2) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      List<Player> â˜ƒx = (List)â˜ƒ.players()
         .stream()
         .filter(EntitySelector.NO_SPECTATORS)
         .filter(var1x -> TEMPT_TARGETING.test(â˜ƒ, var1x))
         .filter(var1x -> â˜ƒ.closerThan(var1x, 10.0))
         .filter(this::playerHoldingTemptation)
         .sorted(Comparator.comparingDouble(â˜ƒ::distanceToSqr))
         .collect(Collectors.toList());
      if (!â˜ƒx.isEmpty()) {
         Player â˜ƒxx = (Player)â˜ƒx.get(0);
         â˜ƒ.setMemory(MemoryModuleType.TEMPTING_PLAYER, â˜ƒxx);
      } else {
         â˜ƒ.eraseMemory(MemoryModuleType.TEMPTING_PLAYER);
      }
   }

   private boolean playerHoldingTemptation(Player var1) {
      return this.isTemptation(â˜ƒ.getMainHandItem()) || this.isTemptation(â˜ƒ.getOffhandItem());
   }

   private boolean isTemptation(ItemStack var1) {
      return this.temptations.test(â˜ƒ);
   }

   @Override
   public Set<MemoryModuleType<?>> requires() {
      return ImmutableSet.of(MemoryModuleType.TEMPTING_PLAYER);
   }
}
