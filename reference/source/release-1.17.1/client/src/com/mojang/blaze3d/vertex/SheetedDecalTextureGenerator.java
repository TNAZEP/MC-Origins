package com.mojang.blaze3d.vertex;

import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import net.minecraft.core.Direction;

public class SheetedDecalTextureGenerator extends DefaultedVertexConsumer {
   private final VertexConsumer delegate;
   private final Matrix4f cameraInversePose;
   private final Matrix3f normalInversePose;
   private float x;
   private float y;
   private float z;
   private int overlayU;
   private int overlayV;
   private int lightCoords;
   private float nx;
   private float ny;
   private float nz;

   public SheetedDecalTextureGenerator(VertexConsumer var1, Matrix4f var2, Matrix3f var3) {
      this.delegate = â˜ƒ;
      this.cameraInversePose = â˜ƒ.copy();
      this.cameraInversePose.invert();
      this.normalInversePose = â˜ƒ.copy();
      this.normalInversePose.invert();
      this.resetState();
   }

   private void resetState() {
      this.x = 0.0F;
      this.y = 0.0F;
      this.z = 0.0F;
      this.overlayU = 0;
      this.overlayV = 10;
      this.lightCoords = 15728880;
      this.nx = 0.0F;
      this.ny = 1.0F;
      this.nz = 0.0F;
   }

   @Override
   public void endVertex() {
      Vector3f â˜ƒ = new Vector3f(this.nx, this.ny, this.nz);
      â˜ƒ.transform(this.normalInversePose);
      Direction â˜ƒx = Direction.getNearest(â˜ƒ.x(), â˜ƒ.y(), â˜ƒ.z());
      Vector4f â˜ƒxx = new Vector4f(this.x, this.y, this.z, 1.0F);
      â˜ƒxx.transform(this.cameraInversePose);
      â˜ƒxx.transform(Vector3f.YP.rotationDegrees(180.0F));
      â˜ƒxx.transform(Vector3f.XP.rotationDegrees(-90.0F));
      â˜ƒxx.transform(â˜ƒx.getRotation());
      float â˜ƒxxx = -â˜ƒxx.x();
      float â˜ƒxxxx = -â˜ƒxx.y();
      this.delegate
         .vertex((double)this.x, (double)this.y, (double)this.z)
         .color(1.0F, 1.0F, 1.0F, 1.0F)
         .uv(â˜ƒxxx, â˜ƒxxxx)
         .overlayCoords(this.overlayU, this.overlayV)
         .uv2(this.lightCoords)
         .normal(this.nx, this.ny, this.nz)
         .endVertex();
      this.resetState();
   }

   @Override
   public VertexConsumer vertex(double var1, double var3, double var5) {
      this.x = (float)â˜ƒ;
      this.y = (float)â˜ƒ;
      this.z = (float)â˜ƒ;
      return this;
   }

   @Override
   public VertexConsumer color(int var1, int var2, int var3, int var4) {
      return this;
   }

   @Override
   public VertexConsumer uv(float var1, float var2) {
      return this;
   }

   @Override
   public VertexConsumer overlayCoords(int var1, int var2) {
      this.overlayU = â˜ƒ;
      this.overlayV = â˜ƒ;
      return this;
   }

   @Override
   public VertexConsumer uv2(int var1, int var2) {
      this.lightCoords = â˜ƒ | â˜ƒ << 16;
      return this;
   }

   @Override
   public VertexConsumer normal(float var1, float var2, float var3) {
      this.nx = â˜ƒ;
      this.ny = â˜ƒ;
      this.nz = â˜ƒ;
      return this;
   }
}
