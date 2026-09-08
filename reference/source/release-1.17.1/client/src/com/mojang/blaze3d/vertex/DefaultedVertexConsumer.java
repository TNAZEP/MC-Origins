package com.mojang.blaze3d.vertex;

public abstract class DefaultedVertexConsumer implements VertexConsumer {
   protected boolean defaultColorSet;
   protected int defaultR = 255;
   protected int defaultG = 255;
   protected int defaultB = 255;
   protected int defaultA = 255;

   @Override
   public void defaultColor(int var1, int var2, int var3, int var4) {
      this.defaultR = â˜ƒ;
      this.defaultG = â˜ƒ;
      this.defaultB = â˜ƒ;
      this.defaultA = â˜ƒ;
      this.defaultColorSet = true;
   }

   @Override
   public void unsetDefaultColor() {
      this.defaultColorSet = false;
   }
}
