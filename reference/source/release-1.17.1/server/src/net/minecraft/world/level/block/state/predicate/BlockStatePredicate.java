package net.minecraft.world.level.block.state.predicate;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

public class BlockStatePredicate implements Predicate<BlockState> {
   public static final Predicate<BlockState> ANY = var0 -> true;
   private final StateDefinition<Block, BlockState> definition;
   private final Map<Property<?>, Predicate<Object>> properties = Maps.newHashMap();

   private BlockStatePredicate(StateDefinition<Block, BlockState> var1) {
      this.definition = â˜ƒ;
   }

   public static BlockStatePredicate forBlock(Block var0) {
      return new BlockStatePredicate(â˜ƒ.getStateDefinition());
   }

   public boolean test(@Nullable BlockState var1) {
      if (â˜ƒ != null && â˜ƒ.getBlock().equals(this.definition.getOwner())) {
         if (this.properties.isEmpty()) {
            return true;
         } else {
            for(Entry<Property<?>, Predicate<Object>> â˜ƒ : this.properties.entrySet()) {
               if (!this.applies(â˜ƒ, (Property)â˜ƒ.getKey(), (Predicate<Object>)â˜ƒ.getValue())) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   protected <T extends Comparable<T>> boolean applies(BlockState var1, Property<T> var2, Predicate<Object> var3) {
      T â˜ƒ = â˜ƒ.getValue(â˜ƒ);
      return â˜ƒ.test(â˜ƒ);
   }

   public <V extends Comparable<V>> BlockStatePredicate where(Property<V> var1, Predicate<Object> var2) {
      if (!this.definition.getProperties().contains(â˜ƒ)) {
         throw new IllegalArgumentException(this.definition + " cannot support property " + â˜ƒ);
      } else {
         this.properties.put(â˜ƒ, â˜ƒ);
         return this;
      }
   }
}
