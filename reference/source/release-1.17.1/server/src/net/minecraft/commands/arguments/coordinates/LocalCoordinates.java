package net.minecraft.commands.arguments.coordinates;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Objects;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class LocalCoordinates implements Coordinates {
   public static final char PREFIX_LOCAL_COORDINATE = '^';
   private final double left;
   private final double up;
   private final double forwards;

   public LocalCoordinates(double var1, double var3, double var5) {
      this.left = â˜ƒ;
      this.up = â˜ƒ;
      this.forwards = â˜ƒ;
   }

   @Override
   public Vec3 getPosition(CommandSourceStack var1) {
      Vec2 â˜ƒ = â˜ƒ.getRotation();
      Vec3 â˜ƒx = â˜ƒ.getAnchor().apply(â˜ƒ);
      float â˜ƒxx = Mth.cos((â˜ƒ.y + 90.0F) * (float) (Math.PI / 180.0));
      float â˜ƒxxx = Mth.sin((â˜ƒ.y + 90.0F) * (float) (Math.PI / 180.0));
      float â˜ƒxxxx = Mth.cos(-â˜ƒ.x * (float) (Math.PI / 180.0));
      float â˜ƒxxxxx = Mth.sin(-â˜ƒ.x * (float) (Math.PI / 180.0));
      float â˜ƒxxxxxx = Mth.cos((-â˜ƒ.x + 90.0F) * (float) (Math.PI / 180.0));
      float â˜ƒxxxxxxx = Mth.sin((-â˜ƒ.x + 90.0F) * (float) (Math.PI / 180.0));
      Vec3 â˜ƒxxxxxxxx = new Vec3((double)(â˜ƒxx * â˜ƒxxxx), (double)â˜ƒxxxxx, (double)(â˜ƒxxx * â˜ƒxxxx));
      Vec3 â˜ƒxxxxxxxxx = new Vec3((double)(â˜ƒxx * â˜ƒxxxxxx), (double)â˜ƒxxxxxxx, (double)(â˜ƒxxx * â˜ƒxxxxxx));
      Vec3 â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxx.cross(â˜ƒxxxxxxxxx).scale(-1.0);
      double â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxx.x * this.forwards + â˜ƒxxxxxxxxx.x * this.up + â˜ƒxxxxxxxxxx.x * this.left;
      double â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxx.y * this.forwards + â˜ƒxxxxxxxxx.y * this.up + â˜ƒxxxxxxxxxx.y * this.left;
      double â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxx.z * this.forwards + â˜ƒxxxxxxxxx.z * this.up + â˜ƒxxxxxxxxxx.z * this.left;
      return new Vec3(â˜ƒx.x + â˜ƒxxxxxxxxxxx, â˜ƒx.y + â˜ƒxxxxxxxxxxxx, â˜ƒx.z + â˜ƒxxxxxxxxxxxxx);
   }

   @Override
   public Vec2 getRotation(CommandSourceStack var1) {
      return Vec2.ZERO;
   }

   @Override
   public boolean isXRelative() {
      return true;
   }

   @Override
   public boolean isYRelative() {
      return true;
   }

   @Override
   public boolean isZRelative() {
      return true;
   }

   public static LocalCoordinates parse(StringReader var0) throws CommandSyntaxException {
      int â˜ƒ = â˜ƒ.getCursor();
      double â˜ƒx = readDouble(â˜ƒ, â˜ƒ);
      if (â˜ƒ.canRead() && â˜ƒ.peek() == ' ') {
         â˜ƒ.skip();
         double â˜ƒxx = readDouble(â˜ƒ, â˜ƒ);
         if (â˜ƒ.canRead() && â˜ƒ.peek() == ' ') {
            â˜ƒ.skip();
            double â˜ƒxxx = readDouble(â˜ƒ, â˜ƒ);
            return new LocalCoordinates(â˜ƒx, â˜ƒxx, â˜ƒxxx);
         } else {
            â˜ƒ.setCursor(â˜ƒ);
            throw Vec3Argument.ERROR_NOT_COMPLETE.createWithContext(â˜ƒ);
         }
      } else {
         â˜ƒ.setCursor(â˜ƒ);
         throw Vec3Argument.ERROR_NOT_COMPLETE.createWithContext(â˜ƒ);
      }
   }

   private static double readDouble(StringReader var0, int var1) throws CommandSyntaxException {
      if (!â˜ƒ.canRead()) {
         throw WorldCoordinate.ERROR_EXPECTED_DOUBLE.createWithContext(â˜ƒ);
      } else if (â˜ƒ.peek() != '^') {
         â˜ƒ.setCursor(â˜ƒ);
         throw Vec3Argument.ERROR_MIXED_TYPE.createWithContext(â˜ƒ);
      } else {
         â˜ƒ.skip();
         return â˜ƒ.canRead() && â˜ƒ.peek() != ' ' ? â˜ƒ.readDouble() : 0.0;
      }
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (!(â˜ƒ instanceof LocalCoordinates)) {
         return false;
      } else {
         LocalCoordinates â˜ƒ = (LocalCoordinates)â˜ƒ;
         return this.left == â˜ƒ.left && this.up == â˜ƒ.up && this.forwards == â˜ƒ.forwards;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.left, this.up, this.forwards});
   }
}
