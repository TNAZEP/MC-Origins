package net.minecraft.client.renderer.model;

import javax.annotation.Nullable;
import net.minecraft.client.renderer.EnumFaceDirection;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.Vector4f;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;

public class FaceBakery {
   private static final float field_178418_a = 1.0F / (float)Math.cos((float) (Math.PI / 8)) - 1.0F;
   private static final float field_178417_b = 1.0F / (float)Math.cos((float) (Math.PI / 4)) - 1.0F;
   private static final FaceBakery.Rotation[] field_188016_c = new FaceBakery.Rotation[ModelRotation.values().length * EnumFacing.values().length];
   private static final FaceBakery.Rotation field_188017_d = new FaceBakery.Rotation() {
      @Override
      BlockFaceUV func_188007_a(float var1, float var2, float var3, float var4) {
         return new BlockFaceUV(new float[]{☃, ☃, ☃, ☃}, 0);
      }
   };
   private static final FaceBakery.Rotation field_188018_e = new FaceBakery.Rotation() {
      @Override
      BlockFaceUV func_188007_a(float var1, float var2, float var3, float var4) {
         return new BlockFaceUV(new float[]{☃, 16.0F - ☃, ☃, 16.0F - ☃}, 270);
      }
   };
   private static final FaceBakery.Rotation field_188019_f = new FaceBakery.Rotation() {
      @Override
      BlockFaceUV func_188007_a(float var1, float var2, float var3, float var4) {
         return new BlockFaceUV(new float[]{16.0F - ☃, 16.0F - ☃, 16.0F - ☃, 16.0F - ☃}, 0);
      }
   };
   private static final FaceBakery.Rotation field_188020_g = new FaceBakery.Rotation() {
      @Override
      BlockFaceUV func_188007_a(float var1, float var2, float var3, float var4) {
         return new BlockFaceUV(new float[]{16.0F - ☃, ☃, 16.0F - ☃, ☃}, 90);
      }
   };

   public BakedQuad func_199332_a(
      Vector3f var1,
      Vector3f var2,
      BlockPartFace var3,
      TextureAtlasSprite var4,
      EnumFacing var5,
      ModelRotation var6,
      @Nullable BlockPartRotation var7,
      boolean var8,
      boolean var9
   ) {
      BlockFaceUV ☃ = ☃.field_178243_e;
      if (☃) {
         ☃ = this.func_188010_a(☃.field_178243_e, ☃, ☃);
      }

      int[] ☃ = this.func_188012_a(☃, ☃, ☃, this.func_199337_a(☃, ☃), ☃, ☃, ☃);
      EnumFacing ☃x = func_178410_a(☃);
      if (☃ == null) {
         this.func_178408_a(☃, ☃x);
      }

      return new BakedQuad(☃, ☃.field_178245_c, ☃x, ☃);
   }

   private BlockFaceUV func_188010_a(BlockFaceUV var1, EnumFacing var2, ModelRotation var3) {
      return field_188016_c[func_188014_a(☃, ☃)].func_188006_a(☃);
   }

   private int[] func_188012_a(
      BlockFaceUV var1, TextureAtlasSprite var2, EnumFacing var3, float[] var4, ModelRotation var5, @Nullable BlockPartRotation var6, boolean var7
   ) {
      int[] ☃ = new int[28];

      for(int ☃x = 0; ☃x < 4; ++☃x) {
         this.func_188015_a(☃, ☃x, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
      }

      return ☃;
   }

   private int func_178413_a(EnumFacing var1) {
      float ☃ = this.func_178412_b(☃);
      int ☃x = MathHelper.func_76125_a((int)(☃ * 255.0F), 0, 255);
      return 0xFF000000 | ☃x << 16 | ☃x << 8 | ☃x;
   }

   private float func_178412_b(EnumFacing var1) {
      switch(☃) {
         case DOWN:
            return 0.5F;
         case UP:
            return 1.0F;
         case NORTH:
         case SOUTH:
            return 0.8F;
         case WEST:
         case EAST:
            return 0.6F;
         default:
            return 1.0F;
      }
   }

   private float[] func_199337_a(Vector3f var1, Vector3f var2) {
      float[] ☃ = new float[EnumFacing.values().length];
      ☃[EnumFaceDirection.Constants.field_179176_f] = ☃.func_195899_a() / 16.0F;
      ☃[EnumFaceDirection.Constants.field_179178_e] = ☃.func_195900_b() / 16.0F;
      ☃[EnumFaceDirection.Constants.field_179177_d] = ☃.func_195902_c() / 16.0F;
      ☃[EnumFaceDirection.Constants.field_179180_c] = ☃.func_195899_a() / 16.0F;
      ☃[EnumFaceDirection.Constants.field_179179_b] = ☃.func_195900_b() / 16.0F;
      ☃[EnumFaceDirection.Constants.field_179181_a] = ☃.func_195902_c() / 16.0F;
      return ☃;
   }

   private void func_188015_a(
      int[] var1,
      int var2,
      EnumFacing var3,
      BlockFaceUV var4,
      float[] var5,
      TextureAtlasSprite var6,
      ModelRotation var7,
      @Nullable BlockPartRotation var8,
      boolean var9
   ) {
      EnumFacing ☃ = ☃.func_177523_a(☃);
      int ☃x = ☃ ? this.func_178413_a(☃) : -1;
      EnumFaceDirection.VertexInformation ☃xx = EnumFaceDirection.func_179027_a(☃).func_179025_a(☃);
      Vector3f ☃xxx = new Vector3f(☃[☃xx.field_179184_a], ☃[☃xx.field_179182_b], ☃[☃xx.field_179183_c]);
      this.func_199336_a(☃xxx, ☃);
      int ☃xxxx = this.func_199335_a(☃xxx, ☃, ☃, ☃);
      this.func_199333_a(☃, ☃xxxx, ☃, ☃xxx, ☃x, ☃, ☃);
   }

   private void func_199333_a(int[] var1, int var2, int var3, Vector3f var4, int var5, TextureAtlasSprite var6, BlockFaceUV var7) {
      int ☃ = ☃ * 7;
      ☃[☃] = Float.floatToRawIntBits(☃.func_195899_a());
      ☃[☃ + 1] = Float.floatToRawIntBits(☃.func_195900_b());
      ☃[☃ + 2] = Float.floatToRawIntBits(☃.func_195902_c());
      ☃[☃ + 3] = ☃;
      ☃[☃ + 4] = Float.floatToRawIntBits(☃.func_94214_a((double)☃.func_178348_a(☃)));
      ☃[☃ + 4 + 1] = Float.floatToRawIntBits(☃.func_94207_b((double)☃.func_178346_b(☃)));
   }

   private void func_199336_a(Vector3f var1, @Nullable BlockPartRotation var2) {
      if (☃ != null) {
         Vector3f ☃;
         Vector3f ☃x;
         switch(☃.field_178342_b) {
            case X:
               ☃ = new Vector3f(1.0F, 0.0F, 0.0F);
               ☃x = new Vector3f(0.0F, 1.0F, 1.0F);
               break;
            case Y:
               ☃ = new Vector3f(0.0F, 1.0F, 0.0F);
               ☃x = new Vector3f(1.0F, 0.0F, 1.0F);
               break;
            case Z:
               ☃ = new Vector3f(0.0F, 0.0F, 1.0F);
               ☃x = new Vector3f(1.0F, 1.0F, 0.0F);
               break;
            default:
               throw new IllegalArgumentException("There are only 3 axes");
         }

         Quaternion ☃ = new Quaternion(☃, ☃.field_178343_c, true);
         if (☃.field_178341_d) {
            if (Math.abs(☃.field_178343_c) == 22.5F) {
               ☃x.func_195898_a(field_178418_a);
            } else {
               ☃x.func_195898_a(field_178417_b);
            }

            ☃x.func_195904_b(1.0F, 1.0F, 1.0F);
         } else {
            ☃x.func_195905_a(1.0F, 1.0F, 1.0F);
         }

         this.func_199334_a(☃, new Vector3f(☃.field_178344_a), ☃, ☃x);
      }
   }

   public int func_199335_a(Vector3f var1, EnumFacing var2, int var3, ModelRotation var4) {
      if (☃ == ModelRotation.X0_Y0) {
         return ☃;
      } else {
         this.func_199334_a(☃, new Vector3f(0.5F, 0.5F, 0.5F), ☃.func_195820_a(), new Vector3f(1.0F, 1.0F, 1.0F));
         return ☃.func_177520_a(☃, ☃);
      }
   }

   private void func_199334_a(Vector3f var1, Vector3f var2, Quaternion var3, Vector3f var4) {
      Vector4f ☃ = new Vector4f(☃.func_195899_a() - ☃.func_195899_a(), ☃.func_195900_b() - ☃.func_195900_b(), ☃.func_195902_c() - ☃.func_195902_c(), 1.0F);
      ☃.func_195912_a(☃);
      ☃.func_195909_a(☃);
      ☃.func_195905_a(☃.func_195910_a() + ☃.func_195899_a(), ☃.func_195913_b() + ☃.func_195900_b(), ☃.func_195914_c() + ☃.func_195902_c());
   }

   public static EnumFacing func_178410_a(int[] var0) {
      Vector3f ☃ = new Vector3f(Float.intBitsToFloat(☃[0]), Float.intBitsToFloat(☃[1]), Float.intBitsToFloat(☃[2]));
      Vector3f ☃x = new Vector3f(Float.intBitsToFloat(☃[7]), Float.intBitsToFloat(☃[8]), Float.intBitsToFloat(☃[9]));
      Vector3f ☃xx = new Vector3f(Float.intBitsToFloat(☃[14]), Float.intBitsToFloat(☃[15]), Float.intBitsToFloat(☃[16]));
      Vector3f ☃xxx = new Vector3f(☃);
      ☃xxx.func_195897_a(☃x);
      Vector3f ☃xxxx = new Vector3f(☃xx);
      ☃xxxx.func_195897_a(☃x);
      Vector3f ☃xxxxx = new Vector3f(☃xxxx);
      ☃xxxxx.func_195896_c(☃xxx);
      ☃xxxxx.func_195906_d();
      EnumFacing ☃xxxxxx = null;
      float ☃xxxxxxx = 0.0F;

      for(EnumFacing ☃xxxxxxxx : EnumFacing.values()) {
         Vec3i ☃xxxxxxxxx = ☃xxxxxxxx.func_176730_m();
         Vector3f ☃xxxxxxxxxx = new Vector3f((float)☃xxxxxxxxx.func_177958_n(), (float)☃xxxxxxxxx.func_177956_o(), (float)☃xxxxxxxxx.func_177952_p());
         float ☃xxxxxxxxxxx = ☃xxxxx.func_195903_b(☃xxxxxxxxxx);
         if (☃xxxxxxxxxxx >= 0.0F && ☃xxxxxxxxxxx > ☃xxxxxxx) {
            ☃xxxxxxx = ☃xxxxxxxxxxx;
            ☃xxxxxx = ☃xxxxxxxx;
         }
      }

      return ☃xxxxxx == null ? EnumFacing.UP : ☃xxxxxx;
   }

   private void func_178408_a(int[] var1, EnumFacing var2) {
      int[] ☃ = new int[☃.length];
      System.arraycopy(☃, 0, ☃, 0, ☃.length);
      float[] ☃x = new float[EnumFacing.values().length];
      ☃x[EnumFaceDirection.Constants.field_179176_f] = 999.0F;
      ☃x[EnumFaceDirection.Constants.field_179178_e] = 999.0F;
      ☃x[EnumFaceDirection.Constants.field_179177_d] = 999.0F;
      ☃x[EnumFaceDirection.Constants.field_179180_c] = -999.0F;
      ☃x[EnumFaceDirection.Constants.field_179179_b] = -999.0F;
      ☃x[EnumFaceDirection.Constants.field_179181_a] = -999.0F;

      for(int ☃xx = 0; ☃xx < 4; ++☃xx) {
         int ☃xxx = 7 * ☃xx;
         float ☃xxxx = Float.intBitsToFloat(☃[☃xxx]);
         float ☃xxxxx = Float.intBitsToFloat(☃[☃xxx + 1]);
         float ☃xxxxxx = Float.intBitsToFloat(☃[☃xxx + 2]);
         if (☃xxxx < ☃x[EnumFaceDirection.Constants.field_179176_f]) {
            ☃x[EnumFaceDirection.Constants.field_179176_f] = ☃xxxx;
         }

         if (☃xxxxx < ☃x[EnumFaceDirection.Constants.field_179178_e]) {
            ☃x[EnumFaceDirection.Constants.field_179178_e] = ☃xxxxx;
         }

         if (☃xxxxxx < ☃x[EnumFaceDirection.Constants.field_179177_d]) {
            ☃x[EnumFaceDirection.Constants.field_179177_d] = ☃xxxxxx;
         }

         if (☃xxxx > ☃x[EnumFaceDirection.Constants.field_179180_c]) {
            ☃x[EnumFaceDirection.Constants.field_179180_c] = ☃xxxx;
         }

         if (☃xxxxx > ☃x[EnumFaceDirection.Constants.field_179179_b]) {
            ☃x[EnumFaceDirection.Constants.field_179179_b] = ☃xxxxx;
         }

         if (☃xxxxxx > ☃x[EnumFaceDirection.Constants.field_179181_a]) {
            ☃x[EnumFaceDirection.Constants.field_179181_a] = ☃xxxxxx;
         }
      }

      EnumFaceDirection ☃xx = EnumFaceDirection.func_179027_a(☃);

      for(int ☃xxx = 0; ☃xxx < 4; ++☃xxx) {
         int ☃xxxx = 7 * ☃xxx;
         EnumFaceDirection.VertexInformation ☃xxxxx = ☃xx.func_179025_a(☃xxx);
         float ☃xxxxxx = ☃x[☃xxxxx.field_179184_a];
         float ☃xxxxxxx = ☃x[☃xxxxx.field_179182_b];
         float ☃xxxxxxxx = ☃x[☃xxxxx.field_179183_c];
         ☃[☃xxxx] = Float.floatToRawIntBits(☃xxxxxx);
         ☃[☃xxxx + 1] = Float.floatToRawIntBits(☃xxxxxxx);
         ☃[☃xxxx + 2] = Float.floatToRawIntBits(☃xxxxxxxx);

         for(int ☃xxxxxxxxx = 0; ☃xxxxxxxxx < 4; ++☃xxxxxxxxx) {
            int ☃xxxxxxxxxx = 7 * ☃xxxxxxxxx;
            float ☃xxxxxxxxxxx = Float.intBitsToFloat(☃[☃xxxxxxxxxx]);
            float ☃xxxxxxxxxxxx = Float.intBitsToFloat(☃[☃xxxxxxxxxx + 1]);
            float ☃xxxxxxxxxxxxx = Float.intBitsToFloat(☃[☃xxxxxxxxxx + 2]);
            if (MathHelper.func_180185_a(☃xxxxxx, ☃xxxxxxxxxxx)
               && MathHelper.func_180185_a(☃xxxxxxx, ☃xxxxxxxxxxxx)
               && MathHelper.func_180185_a(☃xxxxxxxx, ☃xxxxxxxxxxxxx)) {
               ☃[☃xxxx + 4] = ☃[☃xxxxxxxxxx + 4];
               ☃[☃xxxx + 4 + 1] = ☃[☃xxxxxxxxxx + 4 + 1];
            }
         }
      }
   }

   private static void func_188013_a(ModelRotation var0, EnumFacing var1, FaceBakery.Rotation var2) {
      field_188016_c[func_188014_a(☃, ☃)] = ☃;
   }

   private static int func_188014_a(ModelRotation var0, EnumFacing var1) {
      return ModelRotation.values().length * ☃.ordinal() + ☃.ordinal();
   }

   static {
      func_188013_a(ModelRotation.X0_Y0, EnumFacing.DOWN, field_188017_d);
      func_188013_a(ModelRotation.X0_Y0, EnumFacing.EAST, field_188017_d);
      func_188013_a(ModelRotation.X0_Y0, EnumFacing.NORTH, field_188017_d);
      func_188013_a(ModelRotation.X0_Y0, EnumFacing.SOUTH, field_188017_d);
      func_188013_a(ModelRotation.X0_Y0, EnumFacing.UP, field_188017_d);
      func_188013_a(ModelRotation.X0_Y0, EnumFacing.WEST, field_188017_d);
      func_188013_a(ModelRotation.X0_Y90, EnumFacing.EAST, field_188017_d);
      func_188013_a(ModelRotation.X0_Y90, EnumFacing.NORTH, field_188017_d);
      func_188013_a(ModelRotation.X0_Y90, EnumFacing.SOUTH, field_188017_d);
      func_188013_a(ModelRotation.X0_Y90, EnumFacing.WEST, field_188017_d);
      func_188013_a(ModelRotation.X0_Y180, EnumFacing.EAST, field_188017_d);
      func_188013_a(ModelRotation.X0_Y180, EnumFacing.NORTH, field_188017_d);
      func_188013_a(ModelRotation.X0_Y180, EnumFacing.SOUTH, field_188017_d);
      func_188013_a(ModelRotation.X0_Y180, EnumFacing.WEST, field_188017_d);
      func_188013_a(ModelRotation.X0_Y270, EnumFacing.EAST, field_188017_d);
      func_188013_a(ModelRotation.X0_Y270, EnumFacing.NORTH, field_188017_d);
      func_188013_a(ModelRotation.X0_Y270, EnumFacing.SOUTH, field_188017_d);
      func_188013_a(ModelRotation.X0_Y270, EnumFacing.WEST, field_188017_d);
      func_188013_a(ModelRotation.X90_Y0, EnumFacing.DOWN, field_188017_d);
      func_188013_a(ModelRotation.X90_Y0, EnumFacing.SOUTH, field_188017_d);
      func_188013_a(ModelRotation.X90_Y90, EnumFacing.DOWN, field_188017_d);
      func_188013_a(ModelRotation.X90_Y180, EnumFacing.DOWN, field_188017_d);
      func_188013_a(ModelRotation.X90_Y180, EnumFacing.NORTH, field_188017_d);
      func_188013_a(ModelRotation.X90_Y270, EnumFacing.DOWN, field_188017_d);
      func_188013_a(ModelRotation.X180_Y0, EnumFacing.DOWN, field_188017_d);
      func_188013_a(ModelRotation.X180_Y0, EnumFacing.UP, field_188017_d);
      func_188013_a(ModelRotation.X270_Y0, EnumFacing.SOUTH, field_188017_d);
      func_188013_a(ModelRotation.X270_Y0, EnumFacing.UP, field_188017_d);
      func_188013_a(ModelRotation.X270_Y90, EnumFacing.UP, field_188017_d);
      func_188013_a(ModelRotation.X270_Y180, EnumFacing.NORTH, field_188017_d);
      func_188013_a(ModelRotation.X270_Y180, EnumFacing.UP, field_188017_d);
      func_188013_a(ModelRotation.X270_Y270, EnumFacing.UP, field_188017_d);
      func_188013_a(ModelRotation.X0_Y270, EnumFacing.UP, field_188018_e);
      func_188013_a(ModelRotation.X0_Y90, EnumFacing.DOWN, field_188018_e);
      func_188013_a(ModelRotation.X90_Y0, EnumFacing.WEST, field_188018_e);
      func_188013_a(ModelRotation.X90_Y90, EnumFacing.WEST, field_188018_e);
      func_188013_a(ModelRotation.X90_Y180, EnumFacing.WEST, field_188018_e);
      func_188013_a(ModelRotation.X90_Y270, EnumFacing.NORTH, field_188018_e);
      func_188013_a(ModelRotation.X90_Y270, EnumFacing.SOUTH, field_188018_e);
      func_188013_a(ModelRotation.X90_Y270, EnumFacing.WEST, field_188018_e);
      func_188013_a(ModelRotation.X180_Y90, EnumFacing.UP, field_188018_e);
      func_188013_a(ModelRotation.X180_Y270, EnumFacing.DOWN, field_188018_e);
      func_188013_a(ModelRotation.X270_Y0, EnumFacing.EAST, field_188018_e);
      func_188013_a(ModelRotation.X270_Y90, EnumFacing.EAST, field_188018_e);
      func_188013_a(ModelRotation.X270_Y90, EnumFacing.NORTH, field_188018_e);
      func_188013_a(ModelRotation.X270_Y90, EnumFacing.SOUTH, field_188018_e);
      func_188013_a(ModelRotation.X270_Y180, EnumFacing.EAST, field_188018_e);
      func_188013_a(ModelRotation.X270_Y270, EnumFacing.EAST, field_188018_e);
      func_188013_a(ModelRotation.X0_Y180, EnumFacing.DOWN, field_188019_f);
      func_188013_a(ModelRotation.X0_Y180, EnumFacing.UP, field_188019_f);
      func_188013_a(ModelRotation.X90_Y0, EnumFacing.NORTH, field_188019_f);
      func_188013_a(ModelRotation.X90_Y0, EnumFacing.UP, field_188019_f);
      func_188013_a(ModelRotation.X90_Y90, EnumFacing.UP, field_188019_f);
      func_188013_a(ModelRotation.X90_Y180, EnumFacing.SOUTH, field_188019_f);
      func_188013_a(ModelRotation.X90_Y180, EnumFacing.UP, field_188019_f);
      func_188013_a(ModelRotation.X90_Y270, EnumFacing.UP, field_188019_f);
      func_188013_a(ModelRotation.X180_Y0, EnumFacing.EAST, field_188019_f);
      func_188013_a(ModelRotation.X180_Y0, EnumFacing.NORTH, field_188019_f);
      func_188013_a(ModelRotation.X180_Y0, EnumFacing.SOUTH, field_188019_f);
      func_188013_a(ModelRotation.X180_Y0, EnumFacing.WEST, field_188019_f);
      func_188013_a(ModelRotation.X180_Y90, EnumFacing.EAST, field_188019_f);
      func_188013_a(ModelRotation.X180_Y90, EnumFacing.NORTH, field_188019_f);
      func_188013_a(ModelRotation.X180_Y90, EnumFacing.SOUTH, field_188019_f);
      func_188013_a(ModelRotation.X180_Y90, EnumFacing.WEST, field_188019_f);
      func_188013_a(ModelRotation.X180_Y180, EnumFacing.DOWN, field_188019_f);
      func_188013_a(ModelRotation.X180_Y180, EnumFacing.EAST, field_188019_f);
      func_188013_a(ModelRotation.X180_Y180, EnumFacing.NORTH, field_188019_f);
      func_188013_a(ModelRotation.X180_Y180, EnumFacing.SOUTH, field_188019_f);
      func_188013_a(ModelRotation.X180_Y180, EnumFacing.UP, field_188019_f);
      func_188013_a(ModelRotation.X180_Y180, EnumFacing.WEST, field_188019_f);
      func_188013_a(ModelRotation.X180_Y270, EnumFacing.EAST, field_188019_f);
      func_188013_a(ModelRotation.X180_Y270, EnumFacing.NORTH, field_188019_f);
      func_188013_a(ModelRotation.X180_Y270, EnumFacing.SOUTH, field_188019_f);
      func_188013_a(ModelRotation.X180_Y270, EnumFacing.WEST, field_188019_f);
      func_188013_a(ModelRotation.X270_Y0, EnumFacing.DOWN, field_188019_f);
      func_188013_a(ModelRotation.X270_Y0, EnumFacing.NORTH, field_188019_f);
      func_188013_a(ModelRotation.X270_Y90, EnumFacing.DOWN, field_188019_f);
      func_188013_a(ModelRotation.X270_Y180, EnumFacing.DOWN, field_188019_f);
      func_188013_a(ModelRotation.X270_Y180, EnumFacing.SOUTH, field_188019_f);
      func_188013_a(ModelRotation.X270_Y270, EnumFacing.DOWN, field_188019_f);
      func_188013_a(ModelRotation.X0_Y90, EnumFacing.UP, field_188020_g);
      func_188013_a(ModelRotation.X0_Y270, EnumFacing.DOWN, field_188020_g);
      func_188013_a(ModelRotation.X90_Y0, EnumFacing.EAST, field_188020_g);
      func_188013_a(ModelRotation.X90_Y90, EnumFacing.EAST, field_188020_g);
      func_188013_a(ModelRotation.X90_Y90, EnumFacing.NORTH, field_188020_g);
      func_188013_a(ModelRotation.X90_Y90, EnumFacing.SOUTH, field_188020_g);
      func_188013_a(ModelRotation.X90_Y180, EnumFacing.EAST, field_188020_g);
      func_188013_a(ModelRotation.X90_Y270, EnumFacing.EAST, field_188020_g);
      func_188013_a(ModelRotation.X270_Y0, EnumFacing.WEST, field_188020_g);
      func_188013_a(ModelRotation.X180_Y90, EnumFacing.DOWN, field_188020_g);
      func_188013_a(ModelRotation.X180_Y270, EnumFacing.UP, field_188020_g);
      func_188013_a(ModelRotation.X270_Y90, EnumFacing.WEST, field_188020_g);
      func_188013_a(ModelRotation.X270_Y180, EnumFacing.WEST, field_188020_g);
      func_188013_a(ModelRotation.X270_Y270, EnumFacing.NORTH, field_188020_g);
      func_188013_a(ModelRotation.X270_Y270, EnumFacing.SOUTH, field_188020_g);
      func_188013_a(ModelRotation.X270_Y270, EnumFacing.WEST, field_188020_g);
   }

   abstract static class Rotation {
      private Rotation() {
      }

      public BlockFaceUV func_188006_a(BlockFaceUV var1) {
         float ☃ = ☃.func_178348_a(☃.func_178345_c(0));
         float ☃x = ☃.func_178346_b(☃.func_178345_c(0));
         float ☃xx = ☃.func_178348_a(☃.func_178345_c(2));
         float ☃xxx = ☃.func_178346_b(☃.func_178345_c(2));
         return this.func_188007_a(☃, ☃x, ☃xx, ☃xxx);
      }

      abstract BlockFaceUV func_188007_a(float var1, float var2, float var3, float var4);
   }
}
