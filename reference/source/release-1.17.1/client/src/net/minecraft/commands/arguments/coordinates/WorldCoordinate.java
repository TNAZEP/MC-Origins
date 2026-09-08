package net.minecraft.commands.arguments.coordinates;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.network.chat.TranslatableComponent;

public class WorldCoordinate {
   private static final char PREFIX_RELATIVE = '~';
   public static final SimpleCommandExceptionType ERROR_EXPECTED_DOUBLE = new SimpleCommandExceptionType(
      new TranslatableComponent("argument.pos.missing.double")
   );
   public static final SimpleCommandExceptionType ERROR_EXPECTED_INT = new SimpleCommandExceptionType(new TranslatableComponent("argument.pos.missing.int"));
   private final boolean relative;
   private final double value;

   public WorldCoordinate(boolean var1, double var2) {
      this.relative = â˜ƒ;
      this.value = â˜ƒ;
   }

   public double get(double var1) {
      return this.relative ? this.value + â˜ƒ : this.value;
   }

   public static WorldCoordinate parseDouble(StringReader var0, boolean var1) throws CommandSyntaxException {
      if (â˜ƒ.canRead() && â˜ƒ.peek() == '^') {
         throw Vec3Argument.ERROR_MIXED_TYPE.createWithContext(â˜ƒ);
      } else if (!â˜ƒ.canRead()) {
         throw ERROR_EXPECTED_DOUBLE.createWithContext(â˜ƒ);
      } else {
         boolean â˜ƒ = isRelative(â˜ƒ);
         int â˜ƒx = â˜ƒ.getCursor();
         double â˜ƒxx = â˜ƒ.canRead() && â˜ƒ.peek() != ' ' ? â˜ƒ.readDouble() : 0.0;
         String â˜ƒxxx = â˜ƒ.getString().substring(â˜ƒx, â˜ƒ.getCursor());
         if (â˜ƒ && â˜ƒxxx.isEmpty()) {
            return new WorldCoordinate(true, 0.0);
         } else {
            if (!â˜ƒxxx.contains(".") && !â˜ƒ && â˜ƒ) {
               â˜ƒxx += 0.5;
            }

            return new WorldCoordinate(â˜ƒ, â˜ƒxx);
         }
      }
   }

   public static WorldCoordinate parseInt(StringReader var0) throws CommandSyntaxException {
      if (â˜ƒ.canRead() && â˜ƒ.peek() == '^') {
         throw Vec3Argument.ERROR_MIXED_TYPE.createWithContext(â˜ƒ);
      } else if (!â˜ƒ.canRead()) {
         throw ERROR_EXPECTED_INT.createWithContext(â˜ƒ);
      } else {
         boolean â˜ƒx = isRelative(â˜ƒ);
         double â˜ƒ;
         if (â˜ƒ.canRead() && â˜ƒ.peek() != ' ') {
            â˜ƒ = â˜ƒx ? â˜ƒ.readDouble() : (double)â˜ƒ.readInt();
         } else {
            â˜ƒ = 0.0;
         }

         return new WorldCoordinate(â˜ƒx, â˜ƒ);
      }
   }

   public static boolean isRelative(StringReader var0) {
      boolean â˜ƒ;
      if (â˜ƒ.peek() == '~') {
         â˜ƒ = true;
         â˜ƒ.skip();
      } else {
         â˜ƒ = false;
      }

      return â˜ƒ;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (!(â˜ƒ instanceof WorldCoordinate)) {
         return false;
      } else {
         WorldCoordinate â˜ƒ = (WorldCoordinate)â˜ƒ;
         if (this.relative != â˜ƒ.relative) {
            return false;
         } else {
            return Double.compare(â˜ƒ.value, this.value) == 0;
         }
      }
   }

   public int hashCode() {
      int â˜ƒ = this.relative ? 1 : 0;
      long â˜ƒx = Double.doubleToLongBits(this.value);
      return 31 * â˜ƒ + (int)(â˜ƒx ^ â˜ƒx >>> 32);
   }

   public boolean isRelative() {
      return this.relative;
   }
}
