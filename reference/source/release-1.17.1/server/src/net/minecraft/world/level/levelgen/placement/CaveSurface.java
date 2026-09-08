package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;

public enum CaveSurface implements StringRepresentable {
   CEILING(Direction.UP, 1, "ceiling"),
   FLOOR(Direction.DOWN, -1, "floor");

   public static final Codec<CaveSurface> CODEC = StringRepresentable.fromEnum(CaveSurface::values, CaveSurface::byName);
   private final Direction direction;
   private final int y;
   private final String id;
   private static final CaveSurface[] VALUES = values();

   private CaveSurface(Direction var3, int var4, String var5) {
      this.direction = â˜ƒ;
      this.y = â˜ƒ;
      this.id = â˜ƒ;
   }

   public Direction getDirection() {
      return this.direction;
   }

   public int getY() {
      return this.y;
   }

   public static CaveSurface byName(String var0) {
      for(CaveSurface â˜ƒ : VALUES) {
         if (â˜ƒ.getSerializedName().equals(â˜ƒ)) {
            return â˜ƒ;
         }
      }

      throw new IllegalArgumentException("Unknown Surface type: " + â˜ƒ);
   }

   @Override
   public String getSerializedName() {
      return this.id;
   }
}
