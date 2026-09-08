package net.minecraft.client.renderer;

import com.google.common.primitives.Floats;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import java.util.BitSet;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BufferBuilder {
   private static final Logger field_187316_a = LogManager.getLogger();
   private ByteBuffer field_179001_a;
   private IntBuffer field_178999_b;
   private ShortBuffer field_181676_c;
   private FloatBuffer field_179000_c;
   private int field_178997_d;
   private VertexFormatElement field_181677_f;
   private int field_181678_g;
   private boolean field_78939_q;
   private int field_179006_k;
   private double field_179004_l;
   private double field_179005_m;
   private double field_179002_n;
   private VertexFormat field_179011_q;
   private boolean field_179010_r;

   public BufferBuilder(int var1) {
      this.field_179001_a = GLAllocation.func_74524_c(☃ * 4);
      this.field_178999_b = this.field_179001_a.asIntBuffer();
      this.field_181676_c = this.field_179001_a.asShortBuffer();
      this.field_179000_c = this.field_179001_a.asFloatBuffer();
   }

   private void func_181670_b(int var1) {
      if (this.field_178997_d * this.field_179011_q.func_177338_f() + ☃ > this.field_179001_a.capacity()) {
         int ☃ = this.field_179001_a.capacity();
         int ☃x = ☃ + MathHelper.func_154354_b(☃, 2097152);
         field_187316_a.debug("Needed to grow BufferBuilder buffer: Old size {} bytes, new size {} bytes.", ☃, ☃x);
         int ☃xx = this.field_178999_b.position();
         ByteBuffer ☃xxx = GLAllocation.func_74524_c(☃x);
         this.field_179001_a.position(0);
         ☃xxx.put(this.field_179001_a);
         ☃xxx.rewind();
         this.field_179001_a = ☃xxx;
         this.field_179000_c = this.field_179001_a.asFloatBuffer().asReadOnlyBuffer();
         this.field_178999_b = this.field_179001_a.asIntBuffer();
         this.field_178999_b.position(☃xx);
         this.field_181676_c = this.field_179001_a.asShortBuffer();
         this.field_181676_c.position(☃xx << 1);
      }
   }

   public void func_181674_a(float var1, float var2, float var3) {
      int ☃ = this.field_178997_d / 4;
      float[] ☃x = new float[☃];

      for(int ☃xx = 0; ☃xx < ☃; ++☃xx) {
         ☃x[☃xx] = func_181665_a(
            this.field_179000_c,
            (float)((double)☃ + this.field_179004_l),
            (float)((double)☃ + this.field_179005_m),
            (float)((double)☃ + this.field_179002_n),
            this.field_179011_q.func_181719_f(),
            ☃xx * this.field_179011_q.func_177338_f()
         );
      }

      Integer[] ☃xx = new Integer[☃];

      for(int ☃xxx = 0; ☃xxx < ☃xx.length; ++☃xxx) {
         ☃xx[☃xxx] = ☃xxx;
      }

      Arrays.sort(☃xx, (var1x, var2x) -> Floats.compare(☃[var2x], ☃[var1x]));
      BitSet ☃xxx = new BitSet();
      int ☃xxxx = this.field_179011_q.func_177338_f();
      int[] ☃xxxxx = new int[☃xxxx];

      for(int ☃xxxxxx = ☃xxx.nextClearBit(0); ☃xxxxxx < ☃xx.length; ☃xxxxxx = ☃xxx.nextClearBit(☃xxxxxx + 1)) {
         int ☃xxxxxxx = ☃xx[☃xxxxxx];
         if (☃xxxxxxx != ☃xxxxxx) {
            this.field_178999_b.limit(☃xxxxxxx * ☃xxxx + ☃xxxx);
            this.field_178999_b.position(☃xxxxxxx * ☃xxxx);
            this.field_178999_b.get(☃xxxxx);
            int ☃xxxxxxxx = ☃xxxxxxx;

            for(int ☃xxxxxxxxx = ☃xx[☃xxxxxxx]; ☃xxxxxxxx != ☃xxxxxx; ☃xxxxxxxxx = ☃xx[☃xxxxxxxxx]) {
               this.field_178999_b.limit(☃xxxxxxxxx * ☃xxxx + ☃xxxx);
               this.field_178999_b.position(☃xxxxxxxxx * ☃xxxx);
               IntBuffer ☃xxxxxxxxxx = this.field_178999_b.slice();
               this.field_178999_b.limit(☃xxxxxxxx * ☃xxxx + ☃xxxx);
               this.field_178999_b.position(☃xxxxxxxx * ☃xxxx);
               this.field_178999_b.put(☃xxxxxxxxxx);
               ☃xxx.set(☃xxxxxxxx);
               ☃xxxxxxxx = ☃xxxxxxxxx;
            }

            this.field_178999_b.limit(☃xxxxxx * ☃xxxx + ☃xxxx);
            this.field_178999_b.position(☃xxxxxx * ☃xxxx);
            this.field_178999_b.put(☃xxxxx);
         }

         ☃xxx.set(☃xxxxxx);
      }
   }

   public BufferBuilder.State func_181672_a() {
      this.field_178999_b.rewind();
      int ☃ = this.func_181664_j();
      this.field_178999_b.limit(☃);
      int[] ☃x = new int[☃];
      this.field_178999_b.get(☃x);
      this.field_178999_b.limit(this.field_178999_b.capacity());
      this.field_178999_b.position(☃);
      return new BufferBuilder.State(☃x, new VertexFormat(this.field_179011_q));
   }

   private int func_181664_j() {
      return this.field_178997_d * this.field_179011_q.func_181719_f();
   }

   private static float func_181665_a(FloatBuffer var0, float var1, float var2, float var3, int var4, int var5) {
      float ☃ = ☃.get(☃ + ☃ * 0 + 0);
      float ☃x = ☃.get(☃ + ☃ * 0 + 1);
      float ☃xx = ☃.get(☃ + ☃ * 0 + 2);
      float ☃xxx = ☃.get(☃ + ☃ * 1 + 0);
      float ☃xxxx = ☃.get(☃ + ☃ * 1 + 1);
      float ☃xxxxx = ☃.get(☃ + ☃ * 1 + 2);
      float ☃xxxxxx = ☃.get(☃ + ☃ * 2 + 0);
      float ☃xxxxxxx = ☃.get(☃ + ☃ * 2 + 1);
      float ☃xxxxxxxx = ☃.get(☃ + ☃ * 2 + 2);
      float ☃xxxxxxxxx = ☃.get(☃ + ☃ * 3 + 0);
      float ☃xxxxxxxxxx = ☃.get(☃ + ☃ * 3 + 1);
      float ☃xxxxxxxxxxx = ☃.get(☃ + ☃ * 3 + 2);
      float ☃xxxxxxxxxxxx = (☃ + ☃xxx + ☃xxxxxx + ☃xxxxxxxxx) * 0.25F - ☃;
      float ☃xxxxxxxxxxxxx = (☃x + ☃xxxx + ☃xxxxxxx + ☃xxxxxxxxxx) * 0.25F - ☃;
      float ☃xxxxxxxxxxxxxx = (☃xx + ☃xxxxx + ☃xxxxxxxx + ☃xxxxxxxxxxx) * 0.25F - ☃;
      return ☃xxxxxxxxxxxx * ☃xxxxxxxxxxxx + ☃xxxxxxxxxxxxx * ☃xxxxxxxxxxxxx + ☃xxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxx;
   }

   public void func_178993_a(BufferBuilder.State var1) {
      this.field_178999_b.clear();
      this.func_181670_b(☃.func_179013_a().length * 4);
      this.field_178999_b.put(☃.func_179013_a());
      this.field_178997_d = ☃.func_179014_c();
      this.field_179011_q = new VertexFormat(☃.func_179016_d());
   }

   public void func_178965_a() {
      this.field_178997_d = 0;
      this.field_181677_f = null;
      this.field_181678_g = 0;
   }

   public void func_181668_a(int var1, VertexFormat var2) {
      if (this.field_179010_r) {
         throw new IllegalStateException("Already building!");
      } else {
         this.field_179010_r = true;
         this.func_178965_a();
         this.field_179006_k = ☃;
         this.field_179011_q = ☃;
         this.field_181677_f = ☃.func_177348_c(this.field_181678_g);
         this.field_78939_q = false;
         this.field_179001_a.limit(this.field_179001_a.capacity());
      }
   }

   public BufferBuilder func_187315_a(double var1, double var3) {
      int ☃ = this.field_178997_d * this.field_179011_q.func_177338_f() + this.field_179011_q.func_181720_d(this.field_181678_g);
      switch(this.field_181677_f.func_177367_b()) {
         case FLOAT:
            this.field_179001_a.putFloat(☃, (float)☃);
            this.field_179001_a.putFloat(☃ + 4, (float)☃);
            break;
         case UINT:
         case INT:
            this.field_179001_a.putInt(☃, (int)☃);
            this.field_179001_a.putInt(☃ + 4, (int)☃);
            break;
         case USHORT:
         case SHORT:
            this.field_179001_a.putShort(☃, (short)((int)☃));
            this.field_179001_a.putShort(☃ + 2, (short)((int)☃));
            break;
         case UBYTE:
         case BYTE:
            this.field_179001_a.put(☃, (byte)((int)☃));
            this.field_179001_a.put(☃ + 1, (byte)((int)☃));
      }

      this.func_181667_k();
      return this;
   }

   public BufferBuilder func_187314_a(int var1, int var2) {
      int ☃ = this.field_178997_d * this.field_179011_q.func_177338_f() + this.field_179011_q.func_181720_d(this.field_181678_g);
      switch(this.field_181677_f.func_177367_b()) {
         case FLOAT:
            this.field_179001_a.putFloat(☃, (float)☃);
            this.field_179001_a.putFloat(☃ + 4, (float)☃);
            break;
         case UINT:
         case INT:
            this.field_179001_a.putInt(☃, ☃);
            this.field_179001_a.putInt(☃ + 4, ☃);
            break;
         case USHORT:
         case SHORT:
            this.field_179001_a.putShort(☃, (short)☃);
            this.field_179001_a.putShort(☃ + 2, (short)☃);
            break;
         case UBYTE:
         case BYTE:
            this.field_179001_a.put(☃, (byte)☃);
            this.field_179001_a.put(☃ + 1, (byte)☃);
      }

      this.func_181667_k();
      return this;
   }

   public void func_178962_a(int var1, int var2, int var3, int var4) {
      int ☃ = (this.field_178997_d - 4) * this.field_179011_q.func_181719_f() + this.field_179011_q.func_177344_b(1) / 4;
      int ☃x = this.field_179011_q.func_177338_f() >> 2;
      this.field_178999_b.put(☃, ☃);
      this.field_178999_b.put(☃ + ☃x, ☃);
      this.field_178999_b.put(☃ + ☃x * 2, ☃);
      this.field_178999_b.put(☃ + ☃x * 3, ☃);
   }

   public void func_178987_a(double var1, double var3, double var5) {
      int ☃ = this.field_179011_q.func_181719_f();
      int ☃x = (this.field_178997_d - 4) * ☃;

      for(int ☃xx = 0; ☃xx < 4; ++☃xx) {
         int ☃xxx = ☃x + ☃xx * ☃;
         int ☃xxxx = ☃xxx + 1;
         int ☃xxxxx = ☃xxxx + 1;
         this.field_178999_b.put(☃xxx, Float.floatToRawIntBits((float)(☃ + this.field_179004_l) + Float.intBitsToFloat(this.field_178999_b.get(☃xxx))));
         this.field_178999_b.put(☃xxxx, Float.floatToRawIntBits((float)(☃ + this.field_179005_m) + Float.intBitsToFloat(this.field_178999_b.get(☃xxxx))));
         this.field_178999_b.put(☃xxxxx, Float.floatToRawIntBits((float)(☃ + this.field_179002_n) + Float.intBitsToFloat(this.field_178999_b.get(☃xxxxx))));
      }
   }

   private int func_78909_a(int var1) {
      return ((this.field_178997_d - ☃) * this.field_179011_q.func_177338_f() + this.field_179011_q.func_177340_e()) / 4;
   }

   public void func_178978_a(float var1, float var2, float var3, int var4) {
      int ☃ = this.func_78909_a(☃);
      int ☃x = -1;
      if (!this.field_78939_q) {
         ☃x = this.field_178999_b.get(☃);
         if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            int ☃xx = (int)((float)(☃x & 0xFF) * ☃);
            int ☃xxx = (int)((float)(☃x >> 8 & 0xFF) * ☃);
            int ☃xxxx = (int)((float)(☃x >> 16 & 0xFF) * ☃);
            ☃x &= -16777216;
            ☃x |= ☃xxxx << 16 | ☃xxx << 8 | ☃xx;
         } else {
            int ☃xx = (int)((float)(☃x >> 24 & 0xFF) * ☃);
            int ☃xxx = (int)((float)(☃x >> 16 & 0xFF) * ☃);
            int ☃xxxx = (int)((float)(☃x >> 8 & 0xFF) * ☃);
            ☃x &= 255;
            ☃x |= ☃xx << 24 | ☃xxx << 16 | ☃xxxx << 8;
         }
      }

      this.field_178999_b.put(☃, ☃x);
   }

   private void func_192836_a(int var1, int var2) {
      int ☃ = this.func_78909_a(☃);
      int ☃x = ☃ >> 16 & 0xFF;
      int ☃xx = ☃ >> 8 & 0xFF;
      int ☃xxx = ☃ & 0xFF;
      this.func_178972_a(☃, ☃x, ☃xx, ☃xxx);
   }

   public void func_178994_b(float var1, float var2, float var3, int var4) {
      int ☃ = this.func_78909_a(☃);
      int ☃x = MathHelper.func_76125_a((int)(☃ * 255.0F), 0, 255);
      int ☃xx = MathHelper.func_76125_a((int)(☃ * 255.0F), 0, 255);
      int ☃xxx = MathHelper.func_76125_a((int)(☃ * 255.0F), 0, 255);
      this.func_178972_a(☃, ☃x, ☃xx, ☃xxx);
   }

   private void func_178972_a(int var1, int var2, int var3, int var4) {
      if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
         this.field_178999_b.put(☃, 0xFF000000 | ☃ << 16 | ☃ << 8 | ☃);
      } else {
         this.field_178999_b.put(☃, ☃ << 24 | ☃ << 16 | ☃ << 8 | 0xFF);
      }
   }

   public void func_78914_f() {
      this.field_78939_q = true;
   }

   public BufferBuilder func_181666_a(float var1, float var2, float var3, float var4) {
      return this.func_181669_b((int)(☃ * 255.0F), (int)(☃ * 255.0F), (int)(☃ * 255.0F), (int)(☃ * 255.0F));
   }

   public BufferBuilder func_181669_b(int var1, int var2, int var3, int var4) {
      if (this.field_78939_q) {
         return this;
      } else {
         int ☃ = this.field_178997_d * this.field_179011_q.func_177338_f() + this.field_179011_q.func_181720_d(this.field_181678_g);
         switch(this.field_181677_f.func_177367_b()) {
            case FLOAT:
               this.field_179001_a.putFloat(☃, (float)☃ / 255.0F);
               this.field_179001_a.putFloat(☃ + 4, (float)☃ / 255.0F);
               this.field_179001_a.putFloat(☃ + 8, (float)☃ / 255.0F);
               this.field_179001_a.putFloat(☃ + 12, (float)☃ / 255.0F);
               break;
            case UINT:
            case INT:
               this.field_179001_a.putFloat(☃, (float)☃);
               this.field_179001_a.putFloat(☃ + 4, (float)☃);
               this.field_179001_a.putFloat(☃ + 8, (float)☃);
               this.field_179001_a.putFloat(☃ + 12, (float)☃);
               break;
            case USHORT:
            case SHORT:
               this.field_179001_a.putShort(☃, (short)☃);
               this.field_179001_a.putShort(☃ + 2, (short)☃);
               this.field_179001_a.putShort(☃ + 4, (short)☃);
               this.field_179001_a.putShort(☃ + 6, (short)☃);
               break;
            case UBYTE:
            case BYTE:
               if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
                  this.field_179001_a.put(☃, (byte)☃);
                  this.field_179001_a.put(☃ + 1, (byte)☃);
                  this.field_179001_a.put(☃ + 2, (byte)☃);
                  this.field_179001_a.put(☃ + 3, (byte)☃);
               } else {
                  this.field_179001_a.put(☃, (byte)☃);
                  this.field_179001_a.put(☃ + 1, (byte)☃);
                  this.field_179001_a.put(☃ + 2, (byte)☃);
                  this.field_179001_a.put(☃ + 3, (byte)☃);
               }
         }

         this.func_181667_k();
         return this;
      }
   }

   public void func_178981_a(int[] var1) {
      this.func_181670_b(☃.length * 4 + this.field_179011_q.func_177338_f());
      this.field_178999_b.position(this.func_181664_j());
      this.field_178999_b.put(☃);
      this.field_178997_d += ☃.length / this.field_179011_q.func_181719_f();
   }

   public void func_181675_d() {
      ++this.field_178997_d;
      this.func_181670_b(this.field_179011_q.func_177338_f());
   }

   public BufferBuilder func_181662_b(double var1, double var3, double var5) {
      int ☃ = this.field_178997_d * this.field_179011_q.func_177338_f() + this.field_179011_q.func_181720_d(this.field_181678_g);
      switch(this.field_181677_f.func_177367_b()) {
         case FLOAT:
            this.field_179001_a.putFloat(☃, (float)(☃ + this.field_179004_l));
            this.field_179001_a.putFloat(☃ + 4, (float)(☃ + this.field_179005_m));
            this.field_179001_a.putFloat(☃ + 8, (float)(☃ + this.field_179002_n));
            break;
         case UINT:
         case INT:
            this.field_179001_a.putInt(☃, Float.floatToRawIntBits((float)(☃ + this.field_179004_l)));
            this.field_179001_a.putInt(☃ + 4, Float.floatToRawIntBits((float)(☃ + this.field_179005_m)));
            this.field_179001_a.putInt(☃ + 8, Float.floatToRawIntBits((float)(☃ + this.field_179002_n)));
            break;
         case USHORT:
         case SHORT:
            this.field_179001_a.putShort(☃, (short)((int)(☃ + this.field_179004_l)));
            this.field_179001_a.putShort(☃ + 2, (short)((int)(☃ + this.field_179005_m)));
            this.field_179001_a.putShort(☃ + 4, (short)((int)(☃ + this.field_179002_n)));
            break;
         case UBYTE:
         case BYTE:
            this.field_179001_a.put(☃, (byte)((int)(☃ + this.field_179004_l)));
            this.field_179001_a.put(☃ + 1, (byte)((int)(☃ + this.field_179005_m)));
            this.field_179001_a.put(☃ + 2, (byte)((int)(☃ + this.field_179002_n)));
      }

      this.func_181667_k();
      return this;
   }

   public void func_178975_e(float var1, float var2, float var3) {
      int ☃ = (byte)((int)(☃ * 127.0F)) & 255;
      int ☃x = (byte)((int)(☃ * 127.0F)) & 255;
      int ☃xx = (byte)((int)(☃ * 127.0F)) & 255;
      int ☃xxx = ☃ | ☃x << 8 | ☃xx << 16;
      int ☃xxxx = this.field_179011_q.func_177338_f() >> 2;
      int ☃xxxxx = (this.field_178997_d - 4) * ☃xxxx + this.field_179011_q.func_177342_c() / 4;
      this.field_178999_b.put(☃xxxxx, ☃xxx);
      this.field_178999_b.put(☃xxxxx + ☃xxxx, ☃xxx);
      this.field_178999_b.put(☃xxxxx + ☃xxxx * 2, ☃xxx);
      this.field_178999_b.put(☃xxxxx + ☃xxxx * 3, ☃xxx);
   }

   private void func_181667_k() {
      ++this.field_181678_g;
      this.field_181678_g %= this.field_179011_q.func_177345_h();
      this.field_181677_f = this.field_179011_q.func_177348_c(this.field_181678_g);
      if (this.field_181677_f.func_177375_c() == VertexFormatElement.EnumUsage.PADDING) {
         this.func_181667_k();
      }
   }

   public BufferBuilder func_181663_c(float var1, float var2, float var3) {
      int ☃ = this.field_178997_d * this.field_179011_q.func_177338_f() + this.field_179011_q.func_181720_d(this.field_181678_g);
      switch(this.field_181677_f.func_177367_b()) {
         case FLOAT:
            this.field_179001_a.putFloat(☃, ☃);
            this.field_179001_a.putFloat(☃ + 4, ☃);
            this.field_179001_a.putFloat(☃ + 8, ☃);
            break;
         case UINT:
         case INT:
            this.field_179001_a.putInt(☃, (int)☃);
            this.field_179001_a.putInt(☃ + 4, (int)☃);
            this.field_179001_a.putInt(☃ + 8, (int)☃);
            break;
         case USHORT:
         case SHORT:
            this.field_179001_a.putShort(☃, (short)((int)☃ * 32767 & 65535));
            this.field_179001_a.putShort(☃ + 2, (short)((int)☃ * 32767 & 65535));
            this.field_179001_a.putShort(☃ + 4, (short)((int)☃ * 32767 & 65535));
            break;
         case UBYTE:
         case BYTE:
            this.field_179001_a.put(☃, (byte)((int)☃ * 127 & 0xFF));
            this.field_179001_a.put(☃ + 1, (byte)((int)☃ * 127 & 0xFF));
            this.field_179001_a.put(☃ + 2, (byte)((int)☃ * 127 & 0xFF));
      }

      this.func_181667_k();
      return this;
   }

   public void func_178969_c(double var1, double var3, double var5) {
      this.field_179004_l = ☃;
      this.field_179005_m = ☃;
      this.field_179002_n = ☃;
   }

   public void func_178977_d() {
      if (!this.field_179010_r) {
         throw new IllegalStateException("Not building!");
      } else {
         this.field_179010_r = false;
         this.field_179001_a.position(0);
         this.field_179001_a.limit(this.func_181664_j() * 4);
      }
   }

   public ByteBuffer func_178966_f() {
      return this.field_179001_a;
   }

   public VertexFormat func_178973_g() {
      return this.field_179011_q;
   }

   public int func_178989_h() {
      return this.field_178997_d;
   }

   public int func_178979_i() {
      return this.field_179006_k;
   }

   public void func_178968_d(int var1) {
      for(int ☃ = 0; ☃ < 4; ++☃) {
         this.func_192836_a(☃, ☃ + 1);
      }
   }

   public void func_178990_f(float var1, float var2, float var3) {
      for(int ☃ = 0; ☃ < 4; ++☃) {
         this.func_178994_b(☃, ☃, ☃, ☃ + 1);
      }
   }

   public class State {
      private final int[] field_179019_b;
      private final VertexFormat field_179018_e;

      public State(int[] var2, VertexFormat var3) {
         this.field_179019_b = ☃;
         this.field_179018_e = ☃;
      }

      public int[] func_179013_a() {
         return this.field_179019_b;
      }

      public int func_179014_c() {
         return this.field_179019_b.length / this.field_179018_e.func_181719_f();
      }

      public VertexFormat func_179016_d() {
         return this.field_179018_e;
      }
   }
}
