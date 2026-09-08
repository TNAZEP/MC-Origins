package net.minecraft.world.level.block.state.properties;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.core.Direction;

public class DirectionProperty extends EnumProperty<Direction> {
   protected DirectionProperty(String var1, Collection<Direction> var2) {
      super(â˜ƒ, Direction.class, â˜ƒ);
   }

   public static DirectionProperty create(String var0) {
      return create(â˜ƒ, Predicates.alwaysTrue());
   }

   public static DirectionProperty create(String var0, Predicate<Direction> var1) {
      return create(â˜ƒ, (Collection<Direction>)Arrays.stream(Direction.values()).filter(â˜ƒ).collect(Collectors.toList()));
   }

   public static DirectionProperty create(String var0, Direction... var1) {
      return create(â˜ƒ, Lists.<Direction>newArrayList(â˜ƒ));
   }

   public static DirectionProperty create(String var0, Collection<Direction> var1) {
      return new DirectionProperty(â˜ƒ, â˜ƒ);
   }
}
