package net.minecraft.client.renderer.block.model;

import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Transformation;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.FaceInfo;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.BlockMath;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class FaceBakery {
   public static final int VERTEX_INT_SIZE = 8;
   private static final float RESCALE_22_5 = 1.0F / (float)Math.cos((float) (Math.PI / 8)) - 1.0F;
   private static final float RESCALE_45 = 1.0F / (float)Math.cos((float) (Math.PI / 4)) - 1.0F;
   public static final int VERTEX_COUNT = 4;
   private static final int COLOR_INDEX = 3;
   public static final int UV_INDEX = 4;

   public BakedQuad bakeQuad(
      Vector3f var1,
      Vector3f var2,
      BlockElementFace var3,
      TextureAtlasSprite var4,
      Direction var5,
      ModelState var6,
      @Nullable BlockElementRotation var7,
      boolean var8,
      ResourceLocation var9
   ) {
      BlockFaceUV â˜ƒ = â˜ƒ.uv;
      if (â˜ƒ.isUvLocked()) {
         â˜ƒ = recomputeUVs(â˜ƒ.uv, â˜ƒ, â˜ƒ.getRotation(), â˜ƒ);
      }

      float[] â˜ƒ = new float[â˜ƒ.uvs.length];
      System.arraycopy(â˜ƒ.uvs, 0, â˜ƒ, 0, â˜ƒ.length);
      float â˜ƒx = â˜ƒ.uvShrinkRatio();
      float â˜ƒxx = (â˜ƒ.uvs[0] + â˜ƒ.uvs[0] + â˜ƒ.uvs[2] + â˜ƒ.uvs[2]) / 4.0F;
      float â˜ƒxxx = (â˜ƒ.uvs[1] + â˜ƒ.uvs[1] + â˜ƒ.uvs[3] + â˜ƒ.uvs[3]) / 4.0F;
      â˜ƒ.uvs[0] = Mth.lerp(â˜ƒx, â˜ƒ.uvs[0], â˜ƒxx);
      â˜ƒ.uvs[2] = Mth.lerp(â˜ƒx, â˜ƒ.uvs[2], â˜ƒxx);
      â˜ƒ.uvs[1] = Mth.lerp(â˜ƒx, â˜ƒ.uvs[1], â˜ƒxxx);
      â˜ƒ.uvs[3] = Mth.lerp(â˜ƒx, â˜ƒ.uvs[3], â˜ƒxxx);
      int[] â˜ƒxxxx = this.makeVertices(â˜ƒ, â˜ƒ, â˜ƒ, this.setupShape(â˜ƒ, â˜ƒ), â˜ƒ.getRotation(), â˜ƒ, â˜ƒ);
      Direction â˜ƒxxxxx = calculateFacing(â˜ƒxxxx);
      System.arraycopy(â˜ƒ, 0, â˜ƒ.uvs, 0, â˜ƒ.length);
      if (â˜ƒ == null) {
         this.recalculateWinding(â˜ƒxxxx, â˜ƒxxxxx);
      }

      return new BakedQuad(â˜ƒxxxx, â˜ƒ.tintIndex, â˜ƒxxxxx, â˜ƒ, â˜ƒ);
   }

   public static BlockFaceUV recomputeUVs(BlockFaceUV var0, Direction var1, Transformation var2, ResourceLocation var3) {
      Matrix4f â˜ƒxx = BlockMath.getUVLockTransform(â˜ƒ, â˜ƒ, () -> "Unable to resolve UVLock for model: " + â˜ƒ).getMatrix();
      float â˜ƒxxx = â˜ƒ.getU(â˜ƒ.getReverseIndex(0));
      float â˜ƒxxxx = â˜ƒ.getV(â˜ƒ.getReverseIndex(0));
      Vector4f â˜ƒxxxxx = new Vector4f(â˜ƒxxx / 16.0F, â˜ƒxxxx / 16.0F, 0.0F, 1.0F);
      â˜ƒxxxxx.transform(â˜ƒxx);
      float â˜ƒxxxxxx = 16.0F * â˜ƒxxxxx.x();
      float â˜ƒxxxxxxx = 16.0F * â˜ƒxxxxx.y();
      float â˜ƒxxxxxxxx = â˜ƒ.getU(â˜ƒ.getReverseIndex(2));
      float â˜ƒxxxxxxxxx = â˜ƒ.getV(â˜ƒ.getReverseIndex(2));
      Vector4f â˜ƒxxxxxxxxxx = new Vector4f(â˜ƒxxxxxxxx / 16.0F, â˜ƒxxxxxxxxx / 16.0F, 0.0F, 1.0F);
      â˜ƒxxxxxxxxxx.transform(â˜ƒxx);
      float â˜ƒxxxxxxxxxxx = 16.0F * â˜ƒxxxxxxxxxx.x();
      float â˜ƒxxxxxxxxxxxx = 16.0F * â˜ƒxxxxxxxxxx.y();
      float â˜ƒ;
      float â˜ƒx;
      if (Math.signum(â˜ƒxxxxxxxx - â˜ƒxxx) == Math.signum(â˜ƒxxxxxxxxxxx - â˜ƒxxxxxx)) {
         â˜ƒ = â˜ƒxxxxxx;
         â˜ƒx = â˜ƒxxxxxxxxxxx;
      } else {
         â˜ƒ = â˜ƒxxxxxxxxxxx;
         â˜ƒx = â˜ƒxxxxxx;
      }

      float â˜ƒ;
      float â˜ƒx;
      if (Math.signum(â˜ƒxxxxxxxxx - â˜ƒxxxx) == Math.signum(â˜ƒxxxxxxxxxxxx - â˜ƒxxxxxxx)) {
         â˜ƒ = â˜ƒxxxxxxx;
         â˜ƒx = â˜ƒxxxxxxxxxxxx;
      } else {
         â˜ƒ = â˜ƒxxxxxxxxxxxx;
         â˜ƒx = â˜ƒxxxxxxx;
      }

      float â˜ƒ = (float)Math.toRadians((double)â˜ƒ.rotation);
      Vector3f â˜ƒx = new Vector3f(Mth.cos(â˜ƒ), Mth.sin(â˜ƒ), 0.0F);
      Matrix3f â˜ƒxx = new Matrix3f(â˜ƒxx);
      â˜ƒx.transform(â˜ƒxx);
      int â˜ƒxxx = Math.floorMod(-((int)Math.round(Math.toDegrees(Math.atan2((double)â˜ƒx.y(), (double)â˜ƒx.x())) / 90.0)) * 90, 360);
      return new BlockFaceUV(new float[]{â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒx}, â˜ƒxxx);
   }

   private int[] makeVertices(
      BlockFaceUV var1, TextureAtlasSprite var2, Direction var3, float[] var4, Transformation var5, @Nullable BlockElementRotation var6, boolean var7
   ) {
      int[] â˜ƒ = new int[32];

      for(int â˜ƒx = 0; â˜ƒx < 4; ++â˜ƒx) {
         this.bakeVertex(â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      return â˜ƒ;
   }

   private float[] setupShape(Vector3f var1, Vector3f var2) {
      float[] â˜ƒ = new float[Direction.values().length];
      â˜ƒ[FaceInfo.Constants.MIN_X] = â˜ƒ.x() / 16.0F;
      â˜ƒ[FaceInfo.Constants.MIN_Y] = â˜ƒ.y() / 16.0F;
      â˜ƒ[FaceInfo.Constants.MIN_Z] = â˜ƒ.z() / 16.0F;
      â˜ƒ[FaceInfo.Constants.MAX_X] = â˜ƒ.x() / 16.0F;
      â˜ƒ[FaceInfo.Constants.MAX_Y] = â˜ƒ.y() / 16.0F;
      â˜ƒ[FaceInfo.Constants.MAX_Z] = â˜ƒ.z() / 16.0F;
      return â˜ƒ;
   }

   private void bakeVertex(
      int[] var1,
      int var2,
      Direction var3,
      BlockFaceUV var4,
      float[] var5,
      TextureAtlasSprite var6,
      Transformation var7,
      @Nullable BlockElementRotation var8,
      boolean var9
   ) {
      FaceInfo.VertexInfo â˜ƒ = FaceInfo.fromFacing(â˜ƒ).getVertexInfo(â˜ƒ);
      Vector3f â˜ƒx = new Vector3f(â˜ƒ[â˜ƒ.xFace], â˜ƒ[â˜ƒ.yFace], â˜ƒ[â˜ƒ.zFace]);
      this.applyElementRotation(â˜ƒx, â˜ƒ);
      this.applyModelRotation(â˜ƒx, â˜ƒ);
      this.fillVertex(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ);
   }

   private void fillVertex(int[] var1, int var2, Vector3f var3, TextureAtlasSprite var4, BlockFaceUV var5) {
      int â˜ƒ = â˜ƒ * 8;
      â˜ƒ[â˜ƒ] = Float.floatToRawIntBits(â˜ƒ.x());
      â˜ƒ[â˜ƒ + 1] = Float.floatToRawIntBits(â˜ƒ.y());
      â˜ƒ[â˜ƒ + 2] = Float.floatToRawIntBits(â˜ƒ.z());
      â˜ƒ[â˜ƒ + 3] = -1;
      â˜ƒ[â˜ƒ + 4] = Float.floatToRawIntBits(â˜ƒ.getU((double)â˜ƒ.getU(â˜ƒ)));
      â˜ƒ[â˜ƒ + 4 + 1] = Float.floatToRawIntBits(â˜ƒ.getV((double)â˜ƒ.getV(â˜ƒ)));
   }

   private void applyElementRotation(Vector3f var1, @Nullable BlockElementRotation var2) {
      if (â˜ƒ != null) {
         Vector3f â˜ƒ;
         Vector3f â˜ƒx;
         switch(â˜ƒ.axis) {
            case X:
               â˜ƒ = Vector3f.XP;
               â˜ƒx = new Vector3f(0.0F, 1.0F, 1.0F);
               break;
            case Y:
               â˜ƒ = Vector3f.YP;
               â˜ƒx = new Vector3f(1.0F, 0.0F, 1.0F);
               break;
            case Z:
               â˜ƒ = Vector3f.ZP;
               â˜ƒx = new Vector3f(1.0F, 1.0F, 0.0F);
               break;
            default:
               throw new IllegalArgumentException("There are only 3 axes");
         }

         Quaternion â˜ƒ = â˜ƒ.rotationDegrees(â˜ƒ.angle);
         if (â˜ƒ.rescale) {
            if (Math.abs(â˜ƒ.angle) == 22.5F) {
               â˜ƒx.mul(RESCALE_22_5);
            } else {
               â˜ƒx.mul(RESCALE_45);
            }

            â˜ƒx.add(1.0F, 1.0F, 1.0F);
         } else {
            â˜ƒx.set(1.0F, 1.0F, 1.0F);
         }

         this.rotateVertexBy(â˜ƒ, â˜ƒ.origin.copy(), new Matrix4f(â˜ƒ), â˜ƒx);
      }
   }

   public void applyModelRotation(Vector3f var1, Transformation var2) {
      if (â˜ƒ != Transformation.identity()) {
         this.rotateVertexBy(â˜ƒ, new Vector3f(0.5F, 0.5F, 0.5F), â˜ƒ.getMatrix(), new Vector3f(1.0F, 1.0F, 1.0F));
      }
   }

   private void rotateVertexBy(Vector3f var1, Vector3f var2, Matrix4f var3, Vector3f var4) {
      Vector4f â˜ƒ = new Vector4f(â˜ƒ.x() - â˜ƒ.x(), â˜ƒ.y() - â˜ƒ.y(), â˜ƒ.z() - â˜ƒ.z(), 1.0F);
      â˜ƒ.transform(â˜ƒ);
      â˜ƒ.mul(â˜ƒ);
      â˜ƒ.set(â˜ƒ.x() + â˜ƒ.x(), â˜ƒ.y() + â˜ƒ.y(), â˜ƒ.z() + â˜ƒ.z());
   }

   public static Direction calculateFacing(int[] var0) {
      Vector3f â˜ƒ = new Vector3f(Float.intBitsToFloat(â˜ƒ[0]), Float.intBitsToFloat(â˜ƒ[1]), Float.intBitsToFloat(â˜ƒ[2]));
      Vector3f â˜ƒx = new Vector3f(Float.intBitsToFloat(â˜ƒ[8]), Float.intBitsToFloat(â˜ƒ[9]), Float.intBitsToFloat(â˜ƒ[10]));
      Vector3f â˜ƒxx = new Vector3f(Float.intBitsToFloat(â˜ƒ[16]), Float.intBitsToFloat(â˜ƒ[17]), Float.intBitsToFloat(â˜ƒ[18]));
      Vector3f â˜ƒxxx = â˜ƒ.copy();
      â˜ƒxxx.sub(â˜ƒx);
      Vector3f â˜ƒxxxx = â˜ƒxx.copy();
      â˜ƒxxxx.sub(â˜ƒx);
      Vector3f â˜ƒxxxxx = â˜ƒxxxx.copy();
      â˜ƒxxxxx.cross(â˜ƒxxx);
      â˜ƒxxxxx.normalize();
      Direction â˜ƒxxxxxx = null;
      float â˜ƒxxxxxxx = 0.0F;

      for(Direction â˜ƒxxxxxxxx : Direction.values()) {
         Vec3i â˜ƒxxxxxxxxx = â˜ƒxxxxxxxx.getNormal();
         Vector3f â˜ƒxxxxxxxxxx = new Vector3f((float)â˜ƒxxxxxxxxx.getX(), (float)â˜ƒxxxxxxxxx.getY(), (float)â˜ƒxxxxxxxxx.getZ());
         float â˜ƒxxxxxxxxxxx = â˜ƒxxxxx.dot(â˜ƒxxxxxxxxxx);
         if (â˜ƒxxxxxxxxxxx >= 0.0F && â˜ƒxxxxxxxxxxx > â˜ƒxxxxxxx) {
            â˜ƒxxxxxxx = â˜ƒxxxxxxxxxxx;
            â˜ƒxxxxxx = â˜ƒxxxxxxxx;
         }
      }

      return â˜ƒxxxxxx == null ? Direction.UP : â˜ƒxxxxxx;
   }

   private void recalculateWinding(int[] var1, Direction var2) {
      int[] â˜ƒ = new int[â˜ƒ.length];
      System.arraycopy(â˜ƒ, 0, â˜ƒ, 0, â˜ƒ.length);
      float[] â˜ƒx = new float[Direction.values().length];
      â˜ƒx[FaceInfo.Constants.MIN_X] = 999.0F;
      â˜ƒx[FaceInfo.Constants.MIN_Y] = 999.0F;
      â˜ƒx[FaceInfo.Constants.MIN_Z] = 999.0F;
      â˜ƒx[FaceInfo.Constants.MAX_X] = -999.0F;
      â˜ƒx[FaceInfo.Constants.MAX_Y] = -999.0F;
      â˜ƒx[FaceInfo.Constants.MAX_Z] = -999.0F;

      for(int â˜ƒxx = 0; â˜ƒxx < 4; ++â˜ƒxx) {
         int â˜ƒxxx = 8 * â˜ƒxx;
         float â˜ƒxxxx = Float.intBitsToFloat(â˜ƒ[â˜ƒxxx]);
         float â˜ƒxxxxx = Float.intBitsToFloat(â˜ƒ[â˜ƒxxx + 1]);
         float â˜ƒxxxxxx = Float.intBitsToFloat(â˜ƒ[â˜ƒxxx + 2]);
         if (â˜ƒxxxx < â˜ƒx[FaceInfo.Constants.MIN_X]) {
            â˜ƒx[FaceInfo.Constants.MIN_X] = â˜ƒxxxx;
         }

         if (â˜ƒxxxxx < â˜ƒx[FaceInfo.Constants.MIN_Y]) {
            â˜ƒx[FaceInfo.Constants.MIN_Y] = â˜ƒxxxxx;
         }

         if (â˜ƒxxxxxx < â˜ƒx[FaceInfo.Constants.MIN_Z]) {
            â˜ƒx[FaceInfo.Constants.MIN_Z] = â˜ƒxxxxxx;
         }

         if (â˜ƒxxxx > â˜ƒx[FaceInfo.Constants.MAX_X]) {
            â˜ƒx[FaceInfo.Constants.MAX_X] = â˜ƒxxxx;
         }

         if (â˜ƒxxxxx > â˜ƒx[FaceInfo.Constants.MAX_Y]) {
            â˜ƒx[FaceInfo.Constants.MAX_Y] = â˜ƒxxxxx;
         }

         if (â˜ƒxxxxxx > â˜ƒx[FaceInfo.Constants.MAX_Z]) {
            â˜ƒx[FaceInfo.Constants.MAX_Z] = â˜ƒxxxxxx;
         }
      }

      FaceInfo â˜ƒxx = FaceInfo.fromFacing(â˜ƒ);

      for(int â˜ƒxxx = 0; â˜ƒxxx < 4; ++â˜ƒxxx) {
         int â˜ƒxxxx = 8 * â˜ƒxxx;
         FaceInfo.VertexInfo â˜ƒxxxxx = â˜ƒxx.getVertexInfo(â˜ƒxxx);
         float â˜ƒxxxxxx = â˜ƒx[â˜ƒxxxxx.xFace];
         float â˜ƒxxxxxxx = â˜ƒx[â˜ƒxxxxx.yFace];
         float â˜ƒxxxxxxxx = â˜ƒx[â˜ƒxxxxx.zFace];
         â˜ƒ[â˜ƒxxxx] = Float.floatToRawIntBits(â˜ƒxxxxxx);
         â˜ƒ[â˜ƒxxxx + 1] = Float.floatToRawIntBits(â˜ƒxxxxxxx);
         â˜ƒ[â˜ƒxxxx + 2] = Float.floatToRawIntBits(â˜ƒxxxxxxxx);

         for(int â˜ƒxxxxxxxxx = 0; â˜ƒxxxxxxxxx < 4; ++â˜ƒxxxxxxxxx) {
            int â˜ƒxxxxxxxxxx = 8 * â˜ƒxxxxxxxxx;
            float â˜ƒxxxxxxxxxxx = Float.intBitsToFloat(â˜ƒ[â˜ƒxxxxxxxxxx]);
            float â˜ƒxxxxxxxxxxxx = Float.intBitsToFloat(â˜ƒ[â˜ƒxxxxxxxxxx + 1]);
            float â˜ƒxxxxxxxxxxxxx = Float.intBitsToFloat(â˜ƒ[â˜ƒxxxxxxxxxx + 2]);
            if (Mth.equal(â˜ƒxxxxxx, â˜ƒxxxxxxxxxxx) && Mth.equal(â˜ƒxxxxxxx, â˜ƒxxxxxxxxxxxx) && Mth.equal(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxxxxx)) {
               â˜ƒ[â˜ƒxxxx + 4] = â˜ƒ[â˜ƒxxxxxxxxxx + 4];
               â˜ƒ[â˜ƒxxxx + 4 + 1] = â˜ƒ[â˜ƒxxxxxxxxxx + 4 + 1];
            }
         }
      }
   }
}
