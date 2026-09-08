package net.minecraft.world.level.block.state.pattern;

import com.google.common.base.Joiner;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class BlockPatternBuilder {
   private static final Joiner COMMA_JOINED = Joiner.on(",");
   private final List<String[]> pattern = Lists.newArrayList();
   private final Map<Character, Predicate<BlockInWorld>> lookup = Maps.newHashMap();
   private int height;
   private int width;

   private BlockPatternBuilder() {
      this.lookup.put(' ', Predicates.alwaysTrue());
   }

   public BlockPatternBuilder aisle(String... var1) {
      if (!ArrayUtils.isEmpty((Object[])â˜ƒ) && !StringUtils.isEmpty(â˜ƒ[0])) {
         if (this.pattern.isEmpty()) {
            this.height = â˜ƒ.length;
            this.width = â˜ƒ[0].length();
         }

         if (â˜ƒ.length != this.height) {
            throw new IllegalArgumentException("Expected aisle with height of " + this.height + ", but was given one with a height of " + â˜ƒ.length + ")");
         } else {
            for(String â˜ƒ : â˜ƒ) {
               if (â˜ƒ.length() != this.width) {
                  throw new IllegalArgumentException(
                     "Not all rows in the given aisle are the correct width (expected " + this.width + ", found one with " + â˜ƒ.length() + ")"
                  );
               }

               for(char â˜ƒx : â˜ƒ.toCharArray()) {
                  if (!this.lookup.containsKey(â˜ƒx)) {
                     this.lookup.put(â˜ƒx, null);
                  }
               }
            }

            this.pattern.add(â˜ƒ);
            return this;
         }
      } else {
         throw new IllegalArgumentException("Empty pattern for aisle");
      }
   }

   public static BlockPatternBuilder start() {
      return new BlockPatternBuilder();
   }

   public BlockPatternBuilder where(char var1, Predicate<BlockInWorld> var2) {
      this.lookup.put(â˜ƒ, â˜ƒ);
      return this;
   }

   public BlockPattern build() {
      return new BlockPattern(this.createPattern());
   }

   private Predicate<BlockInWorld>[][][] createPattern() {
      this.ensureAllCharactersMatched();
      Predicate<BlockInWorld>[][][] â˜ƒ = (Predicate[][][])Array.newInstance(Predicate.class, new int[]{this.pattern.size(), this.height, this.width});

      for(int â˜ƒx = 0; â˜ƒx < this.pattern.size(); ++â˜ƒx) {
         for(int â˜ƒxx = 0; â˜ƒxx < this.height; ++â˜ƒxx) {
            for(int â˜ƒxxx = 0; â˜ƒxxx < this.width; ++â˜ƒxxx) {
               â˜ƒ[â˜ƒx][â˜ƒxx][â˜ƒxxx] = (Predicate)this.lookup.get(((String[])this.pattern.get(â˜ƒx))[â˜ƒxx].charAt(â˜ƒxxx));
            }
         }
      }

      return â˜ƒ;
   }

   private void ensureAllCharactersMatched() {
      List<Character> â˜ƒ = Lists.newArrayList();

      for(Entry<Character, Predicate<BlockInWorld>> â˜ƒx : this.lookup.entrySet()) {
         if (â˜ƒx.getValue() == null) {
            â˜ƒ.add((Character)â˜ƒx.getKey());
         }
      }

      if (!â˜ƒ.isEmpty()) {
         throw new IllegalStateException("Predicates for character(s) " + COMMA_JOINED.join(â˜ƒ) + " are missing");
      }
   }
}
