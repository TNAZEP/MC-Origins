package net.minecraft.client.renderer.block.model.multipart;

import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

public class KeyValueCondition implements Condition {
   private static final Splitter PIPE_SPLITTER = Splitter.on('|').omitEmptyStrings();
   private final String key;
   private final String value;

   public KeyValueCondition(String var1, String var2) {
      this.key = â˜ƒ;
      this.value = â˜ƒ;
   }

   @Override
   public Predicate<BlockState> getPredicate(StateDefinition<Block, BlockState> var1) {
      Property<?> â˜ƒ = â˜ƒ.getProperty(this.key);
      if (â˜ƒ == null) {
         throw new RuntimeException(String.format("Unknown property '%s' on '%s'", this.key, â˜ƒ.getOwner()));
      } else {
         String â˜ƒ = this.value;
         boolean â˜ƒx = !â˜ƒ.isEmpty() && â˜ƒ.charAt(0) == '!';
         if (â˜ƒx) {
            â˜ƒ = â˜ƒ.substring(1);
         }

         List<String> â˜ƒ = PIPE_SPLITTER.splitToList(â˜ƒ);
         if (â˜ƒ.isEmpty()) {
            throw new RuntimeException(String.format("Empty value '%s' for property '%s' on '%s'", this.value, this.key, â˜ƒ.getOwner()));
         } else {
            Predicate<BlockState> â˜ƒ;
            if (â˜ƒ.size() == 1) {
               â˜ƒ = this.getBlockStatePredicate(â˜ƒ, â˜ƒ, â˜ƒ);
            } else {
               List<Predicate<BlockState>> â˜ƒ = (List)â˜ƒ.stream().map(var3x -> this.getBlockStatePredicate(â˜ƒ, â˜ƒ, var3x)).collect(Collectors.toList());
               â˜ƒ = var1x -> â˜ƒ.stream().anyMatch(var1xx -> var1xx.test(var1x));
            }

            return â˜ƒx ? â˜ƒ.negate() : â˜ƒ;
         }
      }
   }

   private Predicate<BlockState> getBlockStatePredicate(StateDefinition<Block, BlockState> var1, Property<?> var2, String var3) {
      Optional<?> â˜ƒ = â˜ƒ.getValue(â˜ƒ);
      if (!â˜ƒ.isPresent()) {
         throw new RuntimeException(String.format("Unknown value '%s' for property '%s' on '%s' in '%s'", â˜ƒ, this.key, â˜ƒ.getOwner(), this.value));
      } else {
         return var2x -> var2x.getValue(â˜ƒ).equals(â˜ƒ.get());
      }
   }

   public String toString() {
      return MoreObjects.toStringHelper(this).add("key", this.key).add("value", this.value).toString();
   }
}
