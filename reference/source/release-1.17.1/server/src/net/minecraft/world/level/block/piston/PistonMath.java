package net.minecraft.world.level.block.piston;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;

public class PistonMath {
   public static AABB getMovementArea(AABB var0, Direction var1, double var2) {
      double â˜ƒ = â˜ƒ * (double)â˜ƒ.getAxisDirection().getStep();
      double â˜ƒx = Math.min(â˜ƒ, 0.0);
      double â˜ƒxx = Math.max(â˜ƒ, 0.0);
      switch(â˜ƒ) {
         case WEST:
            return new AABB(â˜ƒ.minX + â˜ƒx, â˜ƒ.minY, â˜ƒ.minZ, â˜ƒ.minX + â˜ƒxx, â˜ƒ.maxY, â˜ƒ.maxZ);
         case EAST:
            return new AABB(â˜ƒ.maxX + â˜ƒx, â˜ƒ.minY, â˜ƒ.minZ, â˜ƒ.maxX + â˜ƒxx, â˜ƒ.maxY, â˜ƒ.maxZ);
         case DOWN:
            return new AABB(â˜ƒ.minX, â˜ƒ.minY + â˜ƒx, â˜ƒ.minZ, â˜ƒ.maxX, â˜ƒ.minY + â˜ƒxx, â˜ƒ.maxZ);
         case UP:
         default:
            return new AABB(â˜ƒ.minX, â˜ƒ.maxY + â˜ƒx, â˜ƒ.minZ, â˜ƒ.maxX, â˜ƒ.maxY + â˜ƒxx, â˜ƒ.maxZ);
         case NORTH:
            return new AABB(â˜ƒ.minX, â˜ƒ.minY, â˜ƒ.minZ + â˜ƒx, â˜ƒ.maxX, â˜ƒ.maxY, â˜ƒ.minZ + â˜ƒxx);
         case SOUTH:
            return new AABB(â˜ƒ.minX, â˜ƒ.minY, â˜ƒ.maxZ + â˜ƒx, â˜ƒ.maxX, â˜ƒ.maxY, â˜ƒ.maxZ + â˜ƒxx);
      }
   }
}
