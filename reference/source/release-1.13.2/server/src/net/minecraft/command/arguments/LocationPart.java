package net.minecraft.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.util.text.TextComponentTranslation;

public class LocationPart {
   public static final SimpleCommandExceptionType field_197311_b = new SimpleCommandExceptionType(new TextComponentTranslation("argument.pos.missing.double"));
   public static final SimpleCommandExceptionType field_197312_c = new SimpleCommandExceptionType(new TextComponentTranslation("argument.pos.missing.int"));
   private final boolean field_197313_d;
   private final double field_197314_e;

   public LocationPart(boolean var1, double var2) {
      this.field_197313_d = ☃;
      this.field_197314_e = ☃;
   }

   public double func_197306_a(double var1) {
      return this.field_197313_d ? this.field_197314_e + ☃ : this.field_197314_e;
   }

   public static LocationPart func_197308_a(StringReader var0, boolean var1) throws CommandSyntaxException {
      if (☃.canRead() && ☃.peek() == '^') {
         throw Vec3Argument.field_200149_b.createWithContext(☃);
      } else if (!☃.canRead()) {
         throw field_197311_b.createWithContext(☃);
      } else {
         boolean ☃ = func_197309_b(☃);
         int ☃x = ☃.getCursor();
         double ☃xx = ☃.canRead() && ☃.peek() != ' ' ? ☃.readDouble() : 0.0;
         String ☃xxx = ☃.getString().substring(☃x, ☃.getCursor());
         if (☃ && ☃xxx.isEmpty()) {
            return new LocationPart(true, 0.0);
         } else {
            if (!☃xxx.contains(".") && !☃ && ☃) {
               ☃xx += 0.5;
            }

            return new LocationPart(☃, ☃xx);
         }
      }
   }

   public static LocationPart func_197307_a(StringReader var0) throws CommandSyntaxException {
      if (☃.canRead() && ☃.peek() == '^') {
         throw Vec3Argument.field_200149_b.createWithContext(☃);
      } else if (!☃.canRead()) {
         throw field_197312_c.createWithContext(☃);
      } else {
         boolean ☃x = func_197309_b(☃);
         double ☃;
         if (☃.canRead() && ☃.peek() != ' ') {
            ☃ = ☃x ? ☃.readDouble() : (double)☃.readInt();
         } else {
            ☃ = 0.0;
         }

         return new LocationPart(☃x, ☃);
      }
   }

   private static boolean func_197309_b(StringReader var0) {
      boolean ☃;
      if (☃.peek() == '~') {
         ☃ = true;
         ☃.skip();
      } else {
         ☃ = false;
      }

      return ☃;
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (!(☃ instanceof LocationPart)) {
         return false;
      } else {
         LocationPart ☃ = (LocationPart)☃;
         if (this.field_197313_d != ☃.field_197313_d) {
            return false;
         } else {
            return Double.compare(☃.field_197314_e, this.field_197314_e) == 0;
         }
      }
   }

   public int hashCode() {
      int ☃ = this.field_197313_d ? 1 : 0;
      long ☃x = Double.doubleToLongBits(this.field_197314_e);
      return 31 * ☃ + (int)(☃x ^ ☃x >>> 32);
   }

   public boolean func_200386_a() {
      return this.field_197313_d;
   }
}
