package com.mojang.math;

import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.Util;
import org.apache.commons.lang3.tuple.Triple;

public final class Transformation {
   private final Matrix4f matrix;
   private boolean decomposed;
   @Nullable
   private Vector3f translation;
   @Nullable
   private Quaternion leftRotation;
   @Nullable
   private Vector3f scale;
   @Nullable
   private Quaternion rightRotation;
   private static final Transformation IDENTITY = Util.make(() -> {
      Matrix4f â˜ƒ = new Matrix4f();
      â˜ƒ.setIdentity();
      Transformation â˜ƒx = new Transformation(â˜ƒ);
      â˜ƒx.getLeftRotation();
      return â˜ƒx;
   });

   public Transformation(@Nullable Matrix4f var1) {
      if (â˜ƒ == null) {
         this.matrix = IDENTITY.matrix;
      } else {
         this.matrix = â˜ƒ;
      }
   }

   public Transformation(@Nullable Vector3f var1, @Nullable Quaternion var2, @Nullable Vector3f var3, @Nullable Quaternion var4) {
      this.matrix = compose(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.translation = â˜ƒ != null ? â˜ƒ : new Vector3f();
      this.leftRotation = â˜ƒ != null ? â˜ƒ : Quaternion.ONE.copy();
      this.scale = â˜ƒ != null ? â˜ƒ : new Vector3f(1.0F, 1.0F, 1.0F);
      this.rightRotation = â˜ƒ != null ? â˜ƒ : Quaternion.ONE.copy();
      this.decomposed = true;
   }

   public static Transformation identity() {
      return IDENTITY;
   }

   public Transformation compose(Transformation var1) {
      Matrix4f â˜ƒ = this.getMatrix();
      â˜ƒ.multiply(â˜ƒ.getMatrix());
      return new Transformation(â˜ƒ);
   }

   @Nullable
   public Transformation inverse() {
      if (this == IDENTITY) {
         return this;
      } else {
         Matrix4f â˜ƒ = this.getMatrix();
         return â˜ƒ.invert() ? new Transformation(â˜ƒ) : null;
      }
   }

   private void ensureDecomposed() {
      if (!this.decomposed) {
         Pair<Matrix3f, Vector3f> â˜ƒ = toAffine(this.matrix);
         Triple<Quaternion, Vector3f, Quaternion> â˜ƒx = â˜ƒ.getFirst().svdDecompose();
         this.translation = â˜ƒ.getSecond();
         this.leftRotation = â˜ƒx.getLeft();
         this.scale = â˜ƒx.getMiddle();
         this.rightRotation = â˜ƒx.getRight();
         this.decomposed = true;
      }
   }

   private static Matrix4f compose(@Nullable Vector3f var0, @Nullable Quaternion var1, @Nullable Vector3f var2, @Nullable Quaternion var3) {
      Matrix4f â˜ƒ = new Matrix4f();
      â˜ƒ.setIdentity();
      if (â˜ƒ != null) {
         â˜ƒ.multiply(new Matrix4f(â˜ƒ));
      }

      if (â˜ƒ != null) {
         â˜ƒ.multiply(Matrix4f.createScaleMatrix(â˜ƒ.x(), â˜ƒ.y(), â˜ƒ.z()));
      }

      if (â˜ƒ != null) {
         â˜ƒ.multiply(new Matrix4f(â˜ƒ));
      }

      if (â˜ƒ != null) {
         â˜ƒ.m03 = â˜ƒ.x();
         â˜ƒ.m13 = â˜ƒ.y();
         â˜ƒ.m23 = â˜ƒ.z();
      }

      return â˜ƒ;
   }

   public static Pair<Matrix3f, Vector3f> toAffine(Matrix4f var0) {
      â˜ƒ.multiply(1.0F / â˜ƒ.m33);
      Vector3f â˜ƒ = new Vector3f(â˜ƒ.m03, â˜ƒ.m13, â˜ƒ.m23);
      Matrix3f â˜ƒx = new Matrix3f(â˜ƒ);
      return Pair.of(â˜ƒx, â˜ƒ);
   }

   public Matrix4f getMatrix() {
      return this.matrix.copy();
   }

   public Vector3f getTranslation() {
      this.ensureDecomposed();
      return this.translation.copy();
   }

   public Quaternion getLeftRotation() {
      this.ensureDecomposed();
      return this.leftRotation.copy();
   }

   public Vector3f getScale() {
      this.ensureDecomposed();
      return this.scale.copy();
   }

   public Quaternion getRightRotation() {
      this.ensureDecomposed();
      return this.rightRotation.copy();
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (â˜ƒ != null && this.getClass() == â˜ƒ.getClass()) {
         Transformation â˜ƒ = (Transformation)â˜ƒ;
         return Objects.equals(this.matrix, â˜ƒ.matrix);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.matrix});
   }

   public Transformation slerp(Transformation var1, float var2) {
      Vector3f â˜ƒ = this.getTranslation();
      Quaternion â˜ƒx = this.getLeftRotation();
      Vector3f â˜ƒxx = this.getScale();
      Quaternion â˜ƒxxx = this.getRightRotation();
      â˜ƒ.lerp(â˜ƒ.getTranslation(), â˜ƒ);
      â˜ƒx.slerp(â˜ƒ.getLeftRotation(), â˜ƒ);
      â˜ƒxx.lerp(â˜ƒ.getScale(), â˜ƒ);
      â˜ƒxxx.slerp(â˜ƒ.getRightRotation(), â˜ƒ);
      return new Transformation(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx);
   }
}
