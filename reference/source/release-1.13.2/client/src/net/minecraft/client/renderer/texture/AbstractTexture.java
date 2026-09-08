package net.minecraft.client.renderer.texture;

import net.minecraft.client.renderer.GlStateManager;

public abstract class AbstractTexture implements ITextureObject {
   protected int field_110553_a = -1;
   protected boolean field_174940_b;
   protected boolean field_174941_c;
   protected boolean field_174938_d;
   protected boolean field_174939_e;

   public void func_174937_a(boolean var1, boolean var2) {
      this.field_174940_b = ☃;
      this.field_174941_c = ☃;
      int ☃;
      int ☃x;
      if (☃) {
         ☃ = ☃ ? 9987 : 9729;
         ☃x = 9729;
      } else {
         ☃ = ☃ ? 9986 : 9728;
         ☃x = 9728;
      }

      GlStateManager.func_187421_b(3553, 10241, ☃);
      GlStateManager.func_187421_b(3553, 10240, ☃x);
   }

   @Override
   public void func_174936_b(boolean var1, boolean var2) {
      this.field_174938_d = this.field_174940_b;
      this.field_174939_e = this.field_174941_c;
      this.func_174937_a(☃, ☃);
   }

   @Override
   public void func_174935_a() {
      this.func_174937_a(this.field_174938_d, this.field_174939_e);
   }

   @Override
   public int func_110552_b() {
      if (this.field_110553_a == -1) {
         this.field_110553_a = TextureUtil.func_110996_a();
      }

      return this.field_110553_a;
   }

   public void func_147631_c() {
      if (this.field_110553_a != -1) {
         TextureUtil.func_147942_a(this.field_110553_a);
         this.field_110553_a = -1;
      }
   }
}
