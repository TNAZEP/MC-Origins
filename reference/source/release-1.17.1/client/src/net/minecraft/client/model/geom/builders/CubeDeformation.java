package net.minecraft.client.model.geom.builders;

public class CubeDeformation {
   public static final CubeDeformation NONE = new CubeDeformation(0.0F);
   final float growX;
   final float growY;
   final float growZ;

   public CubeDeformation(float var1, float var2, float var3) {
      this.growX = â˜ƒ;
      this.growY = â˜ƒ;
      this.growZ = â˜ƒ;
   }

   public CubeDeformation(float var1) {
      this(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public CubeDeformation extend(float var1) {
      return new CubeDeformation(this.growX + â˜ƒ, this.growY + â˜ƒ, this.growZ + â˜ƒ);
   }

   public CubeDeformation extend(float var1, float var2, float var3) {
      return new CubeDeformation(this.growX + â˜ƒ, this.growY + â˜ƒ, this.growZ + â˜ƒ);
   }
}
