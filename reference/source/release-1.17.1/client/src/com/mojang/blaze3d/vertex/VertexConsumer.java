package com.mojang.blaze3d.vertex;

import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.Vec3i;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.system.MemoryStack;

public interface VertexConsumer {
   Logger LOGGER = LogManager.getLogger();

   VertexConsumer vertex(double var1, double var3, double var5);

   VertexConsumer color(int var1, int var2, int var3, int var4);

   VertexConsumer uv(float var1, float var2);

   VertexConsumer overlayCoords(int var1, int var2);

   VertexConsumer uv2(int var1, int var2);

   VertexConsumer normal(float var1, float var2, float var3);

   void endVertex();

   default void vertex(
      float var1,
      float var2,
      float var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
      int var10,
      int var11,
      float var12,
      float var13,
      float var14
   ) {
      this.vertex((double)â˜ƒ, (double)â˜ƒ, (double)â˜ƒ);
      this.color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.uv(â˜ƒ, â˜ƒ);
      this.overlayCoords(â˜ƒ);
      this.uv2(â˜ƒ);
      this.normal(â˜ƒ, â˜ƒ, â˜ƒ);
      this.endVertex();
   }

   void defaultColor(int var1, int var2, int var3, int var4);

   void unsetDefaultColor();

   default VertexConsumer color(float var1, float var2, float var3, float var4) {
      return this.color((int)(â˜ƒ * 255.0F), (int)(â˜ƒ * 255.0F), (int)(â˜ƒ * 255.0F), (int)(â˜ƒ * 255.0F));
   }

   default VertexConsumer uv2(int var1) {
      return this.uv2(â˜ƒ & 65535, â˜ƒ >> 16 & 65535);
   }

   default VertexConsumer overlayCoords(int var1) {
      return this.overlayCoords(â˜ƒ & 65535, â˜ƒ >> 16 & 65535);
   }

   default void putBulkData(PoseStack.Pose var1, BakedQuad var2, float var3, float var4, float var5, int var6, int var7) {
      this.putBulkData(â˜ƒ, â˜ƒ, new float[]{1.0F, 1.0F, 1.0F, 1.0F}, â˜ƒ, â˜ƒ, â˜ƒ, new int[]{â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ}, â˜ƒ, false);
   }

   default void putBulkData(PoseStack.Pose var1, BakedQuad var2, float[] var3, float var4, float var5, float var6, int[] var7, int var8, boolean var9) {
      float[] â˜ƒ = new float[]{â˜ƒ[0], â˜ƒ[1], â˜ƒ[2], â˜ƒ[3]};
      int[] â˜ƒx = new int[]{â˜ƒ[0], â˜ƒ[1], â˜ƒ[2], â˜ƒ[3]};
      int[] â˜ƒxx = â˜ƒ.getVertices();
      Vec3i â˜ƒxxx = â˜ƒ.getDirection().getNormal();
      Vector3f â˜ƒxxxx = new Vector3f((float)â˜ƒxxx.getX(), (float)â˜ƒxxx.getY(), (float)â˜ƒxxx.getZ());
      Matrix4f â˜ƒxxxxx = â˜ƒ.pose();
      â˜ƒxxxx.transform(â˜ƒ.normal());
      int â˜ƒxxxxxx = 8;
      int â˜ƒxxxxxxx = â˜ƒxx.length / 8;

      try (MemoryStack â˜ƒxxxxxxxx = MemoryStack.stackPush()) {
         ByteBuffer â˜ƒxxxxxxxxx = â˜ƒxxxxxxxx.malloc(DefaultVertexFormat.BLOCK.getVertexSize());
         IntBuffer â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxx.asIntBuffer();

         for(int â˜ƒxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxx < â˜ƒxxxxxxx; ++â˜ƒxxxxxxxxxxx) {
            â˜ƒxxxxxxxxxx.clear();
            â˜ƒxxxxxxxxxx.put(â˜ƒxx, â˜ƒxxxxxxxxxxx * 8, 8);
            float â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxx.getFloat(0);
            float â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxx.getFloat(4);
            float â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxx.getFloat(8);
            float â˜ƒxxxxxxxxxxxx;
            float â˜ƒxxxxxxxxxxxxx;
            float â˜ƒxxxxxxxxxxxxxx;
            if (â˜ƒ) {
               float â˜ƒxxxxxxxxxxxxxxxxxx = (float)(â˜ƒxxxxxxxxx.get(12) & 255) / 255.0F;
               float â˜ƒxxxxxxxxxxxxxxxxxxx = (float)(â˜ƒxxxxxxxxx.get(13) & 255) / 255.0F;
               float â˜ƒxxxxxxxxxxxxxxxxxxxx = (float)(â˜ƒxxxxxxxxx.get(14) & 255) / 255.0F;
               â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxx * â˜ƒ[â˜ƒxxxxxxxxxxx] * â˜ƒ;
               â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxx * â˜ƒ[â˜ƒxxxxxxxxxxx] * â˜ƒ;
               â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxx * â˜ƒ[â˜ƒxxxxxxxxxxx] * â˜ƒ;
            } else {
               â˜ƒxxxxxxxxxxxx = â˜ƒ[â˜ƒxxxxxxxxxxx] * â˜ƒ;
               â˜ƒxxxxxxxxxxxxx = â˜ƒ[â˜ƒxxxxxxxxxxx] * â˜ƒ;
               â˜ƒxxxxxxxxxxxxxx = â˜ƒ[â˜ƒxxxxxxxxxxx] * â˜ƒ;
            }

            int â˜ƒxxxxxxxxxxxx = â˜ƒx[â˜ƒxxxxxxxxxxx];
            float â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxxx.getFloat(16);
            float â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxx.getFloat(20);
            Vector4f â˜ƒxxxxxxxxxxxxxxx = new Vector4f(â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, 1.0F);
            â˜ƒxxxxxxxxxxxxxxx.transform(â˜ƒxxxxx);
            this.vertex(
               â˜ƒxxxxxxxxxxxxxxx.x(),
               â˜ƒxxxxxxxxxxxxxxx.y(),
               â˜ƒxxxxxxxxxxxxxxx.z(),
               â˜ƒxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxx,
               1.0F,
               â˜ƒxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxx,
               â˜ƒ,
               â˜ƒxxxxxxxxxxxx,
               â˜ƒxxxx.x(),
               â˜ƒxxxx.y(),
               â˜ƒxxxx.z()
            );
         }
      }
   }

   default VertexConsumer vertex(Matrix4f var1, float var2, float var3, float var4) {
      Vector4f â˜ƒ = new Vector4f(â˜ƒ, â˜ƒ, â˜ƒ, 1.0F);
      â˜ƒ.transform(â˜ƒ);
      return this.vertex((double)â˜ƒ.x(), (double)â˜ƒ.y(), (double)â˜ƒ.z());
   }

   default VertexConsumer normal(Matrix3f var1, float var2, float var3, float var4) {
      Vector3f â˜ƒ = new Vector3f(â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.transform(â˜ƒ);
      return this.normal(â˜ƒ.x(), â˜ƒ.y(), â˜ƒ.z());
   }
}
