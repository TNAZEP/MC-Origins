package com.mojang.blaze3d.vertex;

import com.google.common.collect.Queues;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import java.util.ArrayDeque;
import java.util.Deque;
import net.minecraft.Util;
import net.minecraft.util.Mth;

public class PoseStack {
   private final Deque<PoseStack.Pose> poseStack = Util.make(Queues.<PoseStack.Pose>newArrayDeque(), var0 -> {
      Matrix4f â˜ƒ = new Matrix4f();
      â˜ƒ.setIdentity();
      Matrix3f â˜ƒx = new Matrix3f();
      â˜ƒx.setIdentity();
      var0.add(new PoseStack.Pose(â˜ƒ, â˜ƒx));
   });

   public void translate(double var1, double var3, double var5) {
      PoseStack.Pose â˜ƒ = (PoseStack.Pose)this.poseStack.getLast();
      â˜ƒ.pose.multiplyWithTranslation((float)â˜ƒ, (float)â˜ƒ, (float)â˜ƒ);
   }

   public void scale(float var1, float var2, float var3) {
      PoseStack.Pose â˜ƒ = (PoseStack.Pose)this.poseStack.getLast();
      â˜ƒ.pose.multiply(Matrix4f.createScaleMatrix(â˜ƒ, â˜ƒ, â˜ƒ));
      if (â˜ƒ == â˜ƒ && â˜ƒ == â˜ƒ) {
         if (â˜ƒ > 0.0F) {
            return;
         }

         â˜ƒ.normal.mul(-1.0F);
      }

      float â˜ƒ = 1.0F / â˜ƒ;
      float â˜ƒx = 1.0F / â˜ƒ;
      float â˜ƒxx = 1.0F / â˜ƒ;
      float â˜ƒxxx = Mth.fastInvCubeRoot(â˜ƒ * â˜ƒx * â˜ƒxx);
      â˜ƒ.normal.mul(Matrix3f.createScaleMatrix(â˜ƒxxx * â˜ƒ, â˜ƒxxx * â˜ƒx, â˜ƒxxx * â˜ƒxx));
   }

   public void mulPose(Quaternion var1) {
      PoseStack.Pose â˜ƒ = (PoseStack.Pose)this.poseStack.getLast();
      â˜ƒ.pose.multiply(â˜ƒ);
      â˜ƒ.normal.mul(â˜ƒ);
   }

   public void pushPose() {
      PoseStack.Pose â˜ƒ = (PoseStack.Pose)this.poseStack.getLast();
      this.poseStack.addLast(new PoseStack.Pose(â˜ƒ.pose.copy(), â˜ƒ.normal.copy()));
   }

   public void popPose() {
      this.poseStack.removeLast();
   }

   public PoseStack.Pose last() {
      return (PoseStack.Pose)this.poseStack.getLast();
   }

   public boolean clear() {
      return this.poseStack.size() == 1;
   }

   public void setIdentity() {
      PoseStack.Pose â˜ƒ = (PoseStack.Pose)this.poseStack.getLast();
      â˜ƒ.pose.setIdentity();
      â˜ƒ.normal.setIdentity();
   }

   public void mulPoseMatrix(Matrix4f var1) {
      ((PoseStack.Pose)this.poseStack.getLast()).pose.multiply(â˜ƒ);
   }

   public static final class Pose {
      final Matrix4f pose;
      final Matrix3f normal;

      Pose(Matrix4f var1, Matrix3f var2) {
         this.pose = â˜ƒ;
         this.normal = â˜ƒ;
      }

      public Matrix4f pose() {
         return this.pose;
      }

      public Matrix3f normal() {
         return this.normal;
      }
   }
}
