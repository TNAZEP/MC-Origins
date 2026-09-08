package net.minecraft.world.level.block;

import com.google.common.collect.Lists;
import com.mojang.math.OctahedralGroup;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.Util;
import net.minecraft.core.Direction;

public enum Rotation {
   NONE(OctahedralGroup.IDENTITY),
   CLOCKWISE_90(OctahedralGroup.ROT_90_Y_NEG),
   CLOCKWISE_180(OctahedralGroup.ROT_180_FACE_XZ),
   COUNTERCLOCKWISE_90(OctahedralGroup.ROT_90_Y_POS);

   private final OctahedralGroup rotation;

   private Rotation(OctahedralGroup var3) {
      this.rotation = â˜ƒ;
   }

   public Rotation getRotated(Rotation var1) {
      switch(â˜ƒ) {
         case CLOCKWISE_180:
            switch(this) {
               case NONE:
                  return CLOCKWISE_180;
               case CLOCKWISE_90:
                  return COUNTERCLOCKWISE_90;
               case CLOCKWISE_180:
                  return NONE;
               case COUNTERCLOCKWISE_90:
                  return CLOCKWISE_90;
            }
         case COUNTERCLOCKWISE_90:
            switch(this) {
               case NONE:
                  return COUNTERCLOCKWISE_90;
               case CLOCKWISE_90:
                  return NONE;
               case CLOCKWISE_180:
                  return CLOCKWISE_90;
               case COUNTERCLOCKWISE_90:
                  return CLOCKWISE_180;
            }
         case CLOCKWISE_90:
            switch(this) {
               case NONE:
                  return CLOCKWISE_90;
               case CLOCKWISE_90:
                  return CLOCKWISE_180;
               case CLOCKWISE_180:
                  return COUNTERCLOCKWISE_90;
               case COUNTERCLOCKWISE_90:
                  return NONE;
            }
         default:
            return this;
      }
   }

   public OctahedralGroup rotation() {
      return this.rotation;
   }

   public Direction rotate(Direction var1) {
      if (â˜ƒ.getAxis() == Direction.Axis.Y) {
         return â˜ƒ;
      } else {
         switch(this) {
            case CLOCKWISE_90:
               return â˜ƒ.getClockWise();
            case CLOCKWISE_180:
               return â˜ƒ.getOpposite();
            case COUNTERCLOCKWISE_90:
               return â˜ƒ.getCounterClockWise();
            default:
               return â˜ƒ;
         }
      }
   }

   public int rotate(int var1, int var2) {
      switch(this) {
         case CLOCKWISE_90:
            return (â˜ƒ + â˜ƒ / 4) % â˜ƒ;
         case CLOCKWISE_180:
            return (â˜ƒ + â˜ƒ / 2) % â˜ƒ;
         case COUNTERCLOCKWISE_90:
            return (â˜ƒ + â˜ƒ * 3 / 4) % â˜ƒ;
         default:
            return â˜ƒ;
      }
   }

   public static Rotation getRandom(Random var0) {
      return Util.getRandom((Rotation[])values(), â˜ƒ);
   }

   public static List<Rotation> getShuffled(Random var0) {
      List<Rotation> â˜ƒ = Lists.newArrayList(values());
      Collections.shuffle(â˜ƒ, â˜ƒ);
      return â˜ƒ;
   }
}
