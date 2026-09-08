package net.minecraft.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class LocationInput implements ILocationArgument {
   private final LocationPart field_197284_b;
   private final LocationPart field_197285_c;
   private final LocationPart field_197286_d;

   public LocationInput(LocationPart var1, LocationPart var2, LocationPart var3) {
      this.field_197284_b = ☃;
      this.field_197285_c = ☃;
      this.field_197286_d = ☃;
   }

   @Override
   public Vec3d func_197281_a(CommandSource var1) {
      Vec3d ☃ = ☃.func_197036_d();
      return new Vec3d(
         this.field_197284_b.func_197306_a(☃.field_72450_a),
         this.field_197285_c.func_197306_a(☃.field_72448_b),
         this.field_197286_d.func_197306_a(☃.field_72449_c)
      );
   }

   @Override
   public Vec2f func_197282_b(CommandSource var1) {
      Vec2f ☃ = ☃.func_201004_i();
      return new Vec2f((float)this.field_197284_b.func_197306_a((double)☃.field_189982_i), (float)this.field_197285_c.func_197306_a((double)☃.field_189983_j));
   }

   @Override
   public boolean func_200380_a() {
      return this.field_197284_b.func_200386_a();
   }

   @Override
   public boolean func_200381_b() {
      return this.field_197285_c.func_200386_a();
   }

   @Override
   public boolean func_200382_c() {
      return this.field_197286_d.func_200386_a();
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (!(☃ instanceof LocationInput)) {
         return false;
      } else {
         LocationInput ☃ = (LocationInput)☃;
         if (!this.field_197284_b.equals(☃.field_197284_b)) {
            return false;
         } else {
            return !this.field_197285_c.equals(☃.field_197285_c) ? false : this.field_197286_d.equals(☃.field_197286_d);
         }
      }
   }

   public static LocationInput func_200148_a(StringReader var0) throws CommandSyntaxException {
      int ☃ = ☃.getCursor();
      LocationPart ☃x = LocationPart.func_197307_a(☃);
      if (☃.canRead() && ☃.peek() == ' ') {
         ☃.skip();
         LocationPart ☃xx = LocationPart.func_197307_a(☃);
         if (☃.canRead() && ☃.peek() == ' ') {
            ☃.skip();
            LocationPart ☃xxx = LocationPart.func_197307_a(☃);
            return new LocationInput(☃x, ☃xx, ☃xxx);
         } else {
            ☃.setCursor(☃);
            throw Vec3Argument.field_197304_a.createWithContext(☃);
         }
      } else {
         ☃.setCursor(☃);
         throw Vec3Argument.field_197304_a.createWithContext(☃);
      }
   }

   public static LocationInput func_200147_a(StringReader var0, boolean var1) throws CommandSyntaxException {
      int ☃ = ☃.getCursor();
      LocationPart ☃x = LocationPart.func_197308_a(☃, ☃);
      if (☃.canRead() && ☃.peek() == ' ') {
         ☃.skip();
         LocationPart ☃xx = LocationPart.func_197308_a(☃, false);
         if (☃.canRead() && ☃.peek() == ' ') {
            ☃.skip();
            LocationPart ☃xxx = LocationPart.func_197308_a(☃, ☃);
            return new LocationInput(☃x, ☃xx, ☃xxx);
         } else {
            ☃.setCursor(☃);
            throw Vec3Argument.field_197304_a.createWithContext(☃);
         }
      } else {
         ☃.setCursor(☃);
         throw Vec3Argument.field_197304_a.createWithContext(☃);
      }
   }

   public static LocationInput func_200383_d() {
      return new LocationInput(new LocationPart(true, 0.0), new LocationPart(true, 0.0), new LocationPart(true, 0.0));
   }

   public int hashCode() {
      int ☃ = this.field_197284_b.hashCode();
      ☃ = 31 * ☃ + this.field_197285_c.hashCode();
      return 31 * ☃ + this.field_197286_d.hashCode();
   }
}
