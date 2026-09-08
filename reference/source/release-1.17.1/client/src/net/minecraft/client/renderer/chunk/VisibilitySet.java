package net.minecraft.client.renderer.chunk;

import java.util.BitSet;
import java.util.Set;
import net.minecraft.core.Direction;

public class VisibilitySet {
   private static final int FACINGS = Direction.values().length;
   private final BitSet data = new BitSet(FACINGS * FACINGS);

   public void add(Set<Direction> var1) {
      for(Direction â˜ƒ : â˜ƒ) {
         for(Direction â˜ƒx : â˜ƒ) {
            this.set(â˜ƒ, â˜ƒx, true);
         }
      }
   }

   public void set(Direction var1, Direction var2, boolean var3) {
      this.data.set(â˜ƒ.ordinal() + â˜ƒ.ordinal() * FACINGS, â˜ƒ);
      this.data.set(â˜ƒ.ordinal() + â˜ƒ.ordinal() * FACINGS, â˜ƒ);
   }

   public void setAll(boolean var1) {
      this.data.set(0, this.data.size(), â˜ƒ);
   }

   public boolean visibilityBetween(Direction var1, Direction var2) {
      return this.data.get(â˜ƒ.ordinal() + â˜ƒ.ordinal() * FACINGS);
   }

   public String toString() {
      StringBuilder â˜ƒ = new StringBuilder();
      â˜ƒ.append(' ');

      for(Direction â˜ƒx : Direction.values()) {
         â˜ƒ.append(' ').append(â˜ƒx.toString().toUpperCase().charAt(0));
      }

      â˜ƒ.append('\n');

      for(Direction â˜ƒx : Direction.values()) {
         â˜ƒ.append(â˜ƒx.toString().toUpperCase().charAt(0));

         for(Direction â˜ƒxx : Direction.values()) {
            if (â˜ƒx == â˜ƒxx) {
               â˜ƒ.append("  ");
            } else {
               boolean â˜ƒxxx = this.visibilityBetween(â˜ƒx, â˜ƒxx);
               â˜ƒ.append(' ').append((char)(â˜ƒxxx ? 'Y' : 'n'));
            }
         }

         â˜ƒ.append('\n');
      }

      return â˜ƒ.toString();
   }
}
