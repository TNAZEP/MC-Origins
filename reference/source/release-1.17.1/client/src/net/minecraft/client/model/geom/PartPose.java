package net.minecraft.client.model.geom;

public class PartPose {
   public static final PartPose ZERO = offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
   public final float x;
   public final float y;
   public final float z;
   public final float xRot;
   public final float yRot;
   public final float zRot;

   private PartPose(float var1, float var2, float var3, float var4, float var5, float var6) {
      this.x = â˜ƒ;
      this.y = â˜ƒ;
      this.z = â˜ƒ;
      this.xRot = â˜ƒ;
      this.yRot = â˜ƒ;
      this.zRot = â˜ƒ;
   }

   public static PartPose offset(float var0, float var1, float var2) {
      return offsetAndRotation(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 0.0F, 0.0F);
   }

   public static PartPose rotation(float var0, float var1, float var2) {
      return offsetAndRotation(0.0F, 0.0F, 0.0F, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static PartPose offsetAndRotation(float var0, float var1, float var2, float var3, float var4, float var5) {
      return new PartPose(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
