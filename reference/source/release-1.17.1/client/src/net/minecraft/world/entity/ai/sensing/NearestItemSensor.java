package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.item.ItemEntity;

public class NearestItemSensor extends Sensor<Mob> {
   private static final long XZ_RANGE = 8L;
   private static final long Y_RANGE = 4L;
   public static final int MAX_DISTANCE_TO_WANTED_ITEM = 9;

   @Override
   public Set<MemoryModuleType<?>> requires() {
      return ImmutableSet.of(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
   }

   protected void doTick(ServerLevel var1, Mob var2) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      List<ItemEntity> â˜ƒx = â˜ƒ.getEntitiesOfClass(ItemEntity.class, â˜ƒ.getBoundingBox().inflate(8.0, 4.0, 8.0), var0 -> true);
      â˜ƒx.sort(Comparator.comparingDouble(â˜ƒ::distanceToSqr));
      Optional<ItemEntity> â˜ƒxx = â˜ƒx.stream()
         .filter(var1x -> â˜ƒ.wantsToPickUp(var1x.getItem()))
         .filter(var1x -> var1x.closerThan(â˜ƒ, 9.0))
         .filter(â˜ƒ::hasLineOfSight)
         .findFirst();
      â˜ƒ.setMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, â˜ƒxx);
   }
}
