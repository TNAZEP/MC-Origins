package net.minecraft.block.state.pattern;

import com.google.common.base.Joiner;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import net.minecraft.block.state.BlockWorldState;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class FactoryBlockPattern {
   private static final Joiner field_177667_a = Joiner.on(",");
   private final List<String[]> field_177665_b = Lists.newArrayList();
   private final Map<Character, Predicate<BlockWorldState>> field_177666_c = Maps.newHashMap();
   private int field_177663_d;
   private int field_177664_e;

   private FactoryBlockPattern() {
      this.field_177666_c.put(' ', Predicates.alwaysTrue());
   }

   public FactoryBlockPattern func_177659_a(String... var1) {
      if (!ArrayUtils.isEmpty((Object[])☃) && !StringUtils.isEmpty(☃[0])) {
         if (this.field_177665_b.isEmpty()) {
            this.field_177663_d = ☃.length;
            this.field_177664_e = ☃[0].length();
         }

         if (☃.length != this.field_177663_d) {
            throw new IllegalArgumentException(
               "Expected aisle with height of " + this.field_177663_d + ", but was given one with a height of " + ☃.length + ")"
            );
         } else {
            for(String ☃ : ☃) {
               if (☃.length() != this.field_177664_e) {
                  throw new IllegalArgumentException(
                     "Not all rows in the given aisle are the correct width (expected " + this.field_177664_e + ", found one with " + ☃.length() + ")"
                  );
               }

               for(char ☃x : ☃.toCharArray()) {
                  if (!this.field_177666_c.containsKey(☃x)) {
                     this.field_177666_c.put(☃x, null);
                  }
               }
            }

            this.field_177665_b.add(☃);
            return this;
         }
      } else {
         throw new IllegalArgumentException("Empty pattern for aisle");
      }
   }

   public static FactoryBlockPattern func_177660_a() {
      return new FactoryBlockPattern();
   }

   public FactoryBlockPattern func_177662_a(char var1, Predicate<BlockWorldState> var2) {
      this.field_177666_c.put(☃, ☃);
      return this;
   }

   public BlockPattern func_177661_b() {
      return new BlockPattern(this.func_201014_c());
   }

   private Predicate<BlockWorldState>[][][] func_201014_c() {
      this.func_177657_d();
      Predicate<BlockWorldState>[][][] ☃ = (Predicate[][][])Array.newInstance(
         Predicate.class, new int[]{this.field_177665_b.size(), this.field_177663_d, this.field_177664_e}
      );

      for(int ☃x = 0; ☃x < this.field_177665_b.size(); ++☃x) {
         for(int ☃xx = 0; ☃xx < this.field_177663_d; ++☃xx) {
            for(int ☃xxx = 0; ☃xxx < this.field_177664_e; ++☃xxx) {
               ☃[☃x][☃xx][☃xxx] = (Predicate)this.field_177666_c.get(((String[])this.field_177665_b.get(☃x))[☃xx].charAt(☃xxx));
            }
         }
      }

      return ☃;
   }

   private void func_177657_d() {
      List<Character> ☃ = Lists.newArrayList();

      for(Entry<Character, Predicate<BlockWorldState>> ☃x : this.field_177666_c.entrySet()) {
         if (☃x.getValue() == null) {
            ☃.add(☃x.getKey());
         }
      }

      if (!☃.isEmpty()) {
         throw new IllegalStateException("Predicates for character(s) " + field_177667_a.join(☃) + " are missing");
      }
   }
}
