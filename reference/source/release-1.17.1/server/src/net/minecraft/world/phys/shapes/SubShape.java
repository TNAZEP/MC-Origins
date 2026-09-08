package net.minecraft.world.phys.shapes;

import net.minecraft.core.Direction;
import net.minecraft.util.Mth;

public final class SubShape extends DiscreteVoxelShape {
   private final DiscreteVoxelShape parent;
   private final int startX;
   private final int startY;
   private final int startZ;
   private final int endX;
   private final int endY;
   private final int endZ;

   protected SubShape(DiscreteVoxelShape var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(â˜ƒ - â˜ƒ, â˜ƒ - â˜ƒ, â˜ƒ - â˜ƒ);
      this.parent = â˜ƒ;
      this.startX = â˜ƒ;
      this.startY = â˜ƒ;
      this.startZ = â˜ƒ;
      this.endX = â˜ƒ;
      this.endY = â˜ƒ;
      this.endZ = â˜ƒ;
   }

   @Override
   public boolean isFull(int var1, int var2, int var3) {
      return this.parent.isFull(this.startX + â˜ƒ, this.startY + â˜ƒ, this.startZ + â˜ƒ);
   }

   @Override
   public void fill(int var1, int var2, int var3) {
      this.parent.fill(this.startX + â˜ƒ, this.startY + â˜ƒ, this.startZ + â˜ƒ);
   }

   @Override
   public int firstFull(Direction.Axis var1) {
      return this.clampToShape(â˜ƒ, this.parent.firstFull(â˜ƒ));
   }

   @Override
   public int lastFull(Direction.Axis var1) {
      return this.clampToShape(â˜ƒ, this.parent.lastFull(â˜ƒ));
   }

   private int clampToShape(Direction.Axis var1, int var2) {
      int â˜ƒ = â˜ƒ.choose(this.startX, this.startY, this.startZ);
      int â˜ƒx = â˜ƒ.choose(this.endX, this.endY, this.endZ);
      return Mth.clamp(â˜ƒ, â˜ƒ, â˜ƒx) - â˜ƒ;
   }
}
