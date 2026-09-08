package net.minecraft.client.renderer.culling;

import com.mojang.math.Matrix4f;
import com.mojang.math.Vector4f;
import net.minecraft.world.phys.AABB;

public class Frustum {
   private final Vector4f[] frustumData = new Vector4f[6];
   private double camX;
   private double camY;
   private double camZ;

   public Frustum(Matrix4f var1, Matrix4f var2) {
      this.calculateFrustum(â˜ƒ, â˜ƒ);
   }

   public void prepare(double var1, double var3, double var5) {
      this.camX = â˜ƒ;
      this.camY = â˜ƒ;
      this.camZ = â˜ƒ;
   }

   private void calculateFrustum(Matrix4f var1, Matrix4f var2) {
      Matrix4f â˜ƒ = â˜ƒ.copy();
      â˜ƒ.multiply(â˜ƒ);
      â˜ƒ.transpose();
      this.getPlane(â˜ƒ, -1, 0, 0, 0);
      this.getPlane(â˜ƒ, 1, 0, 0, 1);
      this.getPlane(â˜ƒ, 0, -1, 0, 2);
      this.getPlane(â˜ƒ, 0, 1, 0, 3);
      this.getPlane(â˜ƒ, 0, 0, -1, 4);
      this.getPlane(â˜ƒ, 0, 0, 1, 5);
   }

   private void getPlane(Matrix4f var1, int var2, int var3, int var4, int var5) {
      Vector4f â˜ƒ = new Vector4f((float)â˜ƒ, (float)â˜ƒ, (float)â˜ƒ, 1.0F);
      â˜ƒ.transform(â˜ƒ);
      â˜ƒ.normalize();
      this.frustumData[â˜ƒ] = â˜ƒ;
   }

   public boolean isVisible(AABB var1) {
      return this.cubeInFrustum(â˜ƒ.minX, â˜ƒ.minY, â˜ƒ.minZ, â˜ƒ.maxX, â˜ƒ.maxY, â˜ƒ.maxZ);
   }

   private boolean cubeInFrustum(double var1, double var3, double var5, double var7, double var9, double var11) {
      float â˜ƒ = (float)(â˜ƒ - this.camX);
      float â˜ƒx = (float)(â˜ƒ - this.camY);
      float â˜ƒxx = (float)(â˜ƒ - this.camZ);
      float â˜ƒxxx = (float)(â˜ƒ - this.camX);
      float â˜ƒxxxx = (float)(â˜ƒ - this.camY);
      float â˜ƒxxxxx = (float)(â˜ƒ - this.camZ);
      return this.cubeInFrustum(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
   }

   private boolean cubeInFrustum(float var1, float var2, float var3, float var4, float var5, float var6) {
      for(int â˜ƒ = 0; â˜ƒ < 6; ++â˜ƒ) {
         Vector4f â˜ƒx = this.frustumData[â˜ƒ];
         if (!(â˜ƒx.dot(new Vector4f(â˜ƒ, â˜ƒ, â˜ƒ, 1.0F)) > 0.0F)
            && !(â˜ƒx.dot(new Vector4f(â˜ƒ, â˜ƒ, â˜ƒ, 1.0F)) > 0.0F)
            && !(â˜ƒx.dot(new Vector4f(â˜ƒ, â˜ƒ, â˜ƒ, 1.0F)) > 0.0F)
            && !(â˜ƒx.dot(new Vector4f(â˜ƒ, â˜ƒ, â˜ƒ, 1.0F)) > 0.0F)
            && !(â˜ƒx.dot(new Vector4f(â˜ƒ, â˜ƒ, â˜ƒ, 1.0F)) > 0.0F)
            && !(â˜ƒx.dot(new Vector4f(â˜ƒ, â˜ƒ, â˜ƒ, 1.0F)) > 0.0F)
            && !(â˜ƒx.dot(new Vector4f(â˜ƒ, â˜ƒ, â˜ƒ, 1.0F)) > 0.0F)
            && !(â˜ƒx.dot(new Vector4f(â˜ƒ, â˜ƒ, â˜ƒ, 1.0F)) > 0.0F)) {
            return false;
         }
      }

      return true;
   }
}
