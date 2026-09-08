package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.player.Player;

public class PlayerSensor extends Sensor<LivingEntity> {
   @Override
   public Set<MemoryModuleType<?>> requires() {
      return ImmutableSet.of(MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
   }

   @Override
   protected void doTick(ServerLevel var1, LivingEntity var2) {
      List<Player> â˜ƒ = (List)â˜ƒ.players()
         .stream()
         .filter(EntitySelector.NO_SPECTATORS)
         .filter(var1x -> â˜ƒ.closerThan(var1x, 16.0))
         .sorted(Comparator.comparingDouble(â˜ƒ::distanceToSqr))
         .collect(Collectors.toList());
      Brain<?> â˜ƒx = â˜ƒ.getBrain();
      â˜ƒx.setMemory(MemoryModuleType.NEAREST_PLAYERS, â˜ƒ);
      List<Player> â˜ƒxx = (List)â˜ƒ.stream().filter(var1x -> isEntityTargetable(â˜ƒ, var1x)).collect(Collectors.toList());
      â˜ƒx.setMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER, â˜ƒxx.isEmpty() ? null : (Player)â˜ƒxx.get(0));
      Optional<Player> â˜ƒxxx = â˜ƒxx.stream().filter(var1x -> isEntityAttackable(â˜ƒ, var1x)).findFirst();
      â˜ƒx.setMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, â˜ƒxxx);
   }
}
