package net.minecraft.commands.arguments.coordinates;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class WorldCoordinates implements Coordinates {
   private final WorldCoordinate x;
   private final WorldCoordinate y;
   private final WorldCoordinate z;

   public WorldCoordinates(WorldCoordinate var1, WorldCoordinate var2, WorldCoordinate var3) {
      this.x = â˜ƒ;
      this.y = â˜ƒ;
      this.z = â˜ƒ;
   }

   @Override
   public Vec3 getPosition(CommandSourceStack var1) {
      Vec3 â˜ƒ = â˜ƒ.getPosition();
      return new Vec3(this.x.get(â˜ƒ.x), this.y.get(â˜ƒ.y), this.z.get(â˜ƒ.z));
   }

   @Override
   public Vec2 getRotation(CommandSourceStack var1) {
      Vec2 â˜ƒ = â˜ƒ.getRotation();
      return new Vec2((float)this.x.get((double)â˜ƒ.x), (float)this.y.get((double)â˜ƒ.y));
   }

   @Override
   public boolean isXRelative() {
      return this.x.isRelative();
   }

   @Override
   public boolean isYRelative() {
      return this.y.isRelative();
   }

   @Override
   public boolean isZRelative() {
      return this.z.isRelative();
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (!(â˜ƒ instanceof WorldCoordinates)) {
         return false;
      } else {
         WorldCoordinates â˜ƒ = (WorldCoordinates)â˜ƒ;
         if (!this.x.equals(â˜ƒ.x)) {
            return false;
         } else {
            return !this.y.equals(â˜ƒ.y) ? false : this.z.equals(â˜ƒ.z);
         }
      }
   }

   public static WorldCoordinates parseInt(StringReader var0) throws CommandSyntaxException {
      int â˜ƒ = â˜ƒ.getCursor();
      WorldCoordinate â˜ƒx = WorldCoordinate.parseInt(â˜ƒ);
      if (â˜ƒ.canRead() && â˜ƒ.peek() == ' ') {
         â˜ƒ.skip();
         WorldCoordinate â˜ƒxx = WorldCoordinate.parseInt(â˜ƒ);
         if (â˜ƒ.canRead() && â˜ƒ.peek() == ' ') {
            â˜ƒ.skip();
            WorldCoordinate â˜ƒxxx = WorldCoordinate.parseInt(â˜ƒ);
            return new WorldCoordinates(â˜ƒx, â˜ƒxx, â˜ƒxxx);
         } else {
            â˜ƒ.setCursor(â˜ƒ);
            throw Vec3Argument.ERROR_NOT_COMPLETE.createWithContext(â˜ƒ);
         }
      } else {
         â˜ƒ.setCursor(â˜ƒ);
         throw Vec3Argument.ERROR_NOT_COMPLETE.createWithContext(â˜ƒ);
      }
   }

   public static WorldCoordinates parseDouble(StringReader var0, boolean var1) throws CommandSyntaxException {
      int â˜ƒ = â˜ƒ.getCursor();
      WorldCoordinate â˜ƒx = WorldCoordinate.parseDouble(â˜ƒ, â˜ƒ);
      if (â˜ƒ.canRead() && â˜ƒ.peek() == ' ') {
         â˜ƒ.skip();
         WorldCoordinate â˜ƒxx = WorldCoordinate.parseDouble(â˜ƒ, false);
         if (â˜ƒ.canRead() && â˜ƒ.peek() == ' ') {
            â˜ƒ.skip();
            WorldCoordinate â˜ƒxxx = WorldCoordinate.parseDouble(â˜ƒ, â˜ƒ);
            return new WorldCoordinates(â˜ƒx, â˜ƒxx, â˜ƒxxx);
         } else {
            â˜ƒ.setCursor(â˜ƒ);
            throw Vec3Argument.ERROR_NOT_COMPLETE.createWithContext(â˜ƒ);
         }
      } else {
         â˜ƒ.setCursor(â˜ƒ);
         throw Vec3Argument.ERROR_NOT_COMPLETE.createWithContext(â˜ƒ);
      }
   }

   public static WorldCoordinates absolute(double var0, double var2, double var4) {
      return new WorldCoordinates(new WorldCoordinate(false, â˜ƒ), new WorldCoordinate(false, â˜ƒ), new WorldCoordinate(false, â˜ƒ));
   }

   public static WorldCoordinates absolute(Vec2 var0) {
      return new WorldCoordinates(new WorldCoordinate(false, (double)â˜ƒ.x), new WorldCoordinate(false, (double)â˜ƒ.y), new WorldCoordinate(true, 0.0));
   }

   public static WorldCoordinates current() {
      return new WorldCoordinates(new WorldCoordinate(true, 0.0), new WorldCoordinate(true, 0.0), new WorldCoordinate(true, 0.0));
   }

   public int hashCode() {
      int â˜ƒ = this.x.hashCode();
      â˜ƒ = 31 * â˜ƒ + this.y.hashCode();
      return 31 * â˜ƒ + this.z.hashCode();
   }
}
