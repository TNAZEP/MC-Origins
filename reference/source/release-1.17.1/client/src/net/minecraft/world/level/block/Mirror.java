package net.minecraft.world.level.block;

import com.mojang.math.OctahedralGroup;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public enum Mirror {
   NONE(new TranslatableComponent("mirror.none"), OctahedralGroup.IDENTITY),
   LEFT_RIGHT(new TranslatableComponent("mirror.left_right"), OctahedralGroup.INVERT_Z),
   FRONT_BACK(new TranslatableComponent("mirror.front_back"), OctahedralGroup.INVERT_X);

   private final Component symbol;
   private final OctahedralGroup rotation;

   private Mirror(Component var3, OctahedralGroup var4) {
      this.symbol = â˜ƒ;
      this.rotation = â˜ƒ;
   }

   public int mirror(int var1, int var2) {
      int â˜ƒ = â˜ƒ / 2;
      int â˜ƒx = â˜ƒ > â˜ƒ ? â˜ƒ - â˜ƒ : â˜ƒ;
      switch(this) {
         case FRONT_BACK:
            return (â˜ƒ - â˜ƒx) % â˜ƒ;
         case LEFT_RIGHT:
            return (â˜ƒ - â˜ƒx + â˜ƒ) % â˜ƒ;
         default:
            return â˜ƒ;
      }
   }

   public Rotation getRotation(Direction var1) {
      Direction.Axis â˜ƒ = â˜ƒ.getAxis();
      return (this != LEFT_RIGHT || â˜ƒ != Direction.Axis.Z) && (this != FRONT_BACK || â˜ƒ != Direction.Axis.X) ? Rotation.NONE : Rotation.CLOCKWISE_180;
   }

   public Direction mirror(Direction var1) {
      if (this == FRONT_BACK && â˜ƒ.getAxis() == Direction.Axis.X) {
         return â˜ƒ.getOpposite();
      } else {
         return this == LEFT_RIGHT && â˜ƒ.getAxis() == Direction.Axis.Z ? â˜ƒ.getOpposite() : â˜ƒ;
      }
   }

   public OctahedralGroup rotation() {
      return this.rotation;
   }

   public Component symbol() {
      return this.symbol;
   }
}
