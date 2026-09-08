package net.minecraft.client.renderer.chunk;

import java.util.BitSet;
import java.util.Set;
import net.minecraft.util.EnumFacing;

public class SetVisibility {
   private static final int field_178623_a = EnumFacing.values().length;
   private final BitSet field_178622_b = new BitSet(field_178623_a * field_178623_a);

   public void func_178620_a(Set<EnumFacing> var1) {
      for(EnumFacing ☃ : ☃) {
         for(EnumFacing ☃x : ☃) {
            this.func_178619_a(☃, ☃x, true);
         }
      }
   }

   public void func_178619_a(EnumFacing var1, EnumFacing var2, boolean var3) {
      this.field_178622_b.set(☃.ordinal() + ☃.ordinal() * field_178623_a, ☃);
      this.field_178622_b.set(☃.ordinal() + ☃.ordinal() * field_178623_a, ☃);
   }

   public void func_178618_a(boolean var1) {
      this.field_178622_b.set(0, this.field_178622_b.size(), ☃);
   }

   public boolean func_178621_a(EnumFacing var1, EnumFacing var2) {
      return this.field_178622_b.get(☃.ordinal() + ☃.ordinal() * field_178623_a);
   }

   public String toString() {
      StringBuilder ☃ = new StringBuilder();
      ☃.append(' ');

      for(EnumFacing ☃x : EnumFacing.values()) {
         ☃.append(' ').append(☃x.toString().toUpperCase().charAt(0));
      }

      ☃.append('\n');

      for(EnumFacing ☃x : EnumFacing.values()) {
         ☃.append(☃x.toString().toUpperCase().charAt(0));

         for(EnumFacing ☃xx : EnumFacing.values()) {
            if (☃x == ☃xx) {
               ☃.append("  ");
            } else {
               boolean ☃xxx = this.func_178621_a(☃x, ☃xx);
               ☃.append(' ').append((char)(☃xxx ? 'Y' : 'n'));
            }
         }

         ☃.append('\n');
      }

      return ☃.toString();
   }
}
