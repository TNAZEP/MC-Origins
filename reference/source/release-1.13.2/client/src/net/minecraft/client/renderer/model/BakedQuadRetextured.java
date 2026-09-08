package net.minecraft.client.renderer.model;

import java.util.Arrays;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class BakedQuadRetextured extends BakedQuad {
   private final TextureAtlasSprite field_178218_d;

   public BakedQuadRetextured(BakedQuad var1, TextureAtlasSprite var2) {
      super(Arrays.copyOf(☃.func_178209_a(), ☃.func_178209_a().length), ☃.field_178213_b, FaceBakery.func_178410_a(☃.func_178209_a()), ☃.func_187508_a());
      this.field_178218_d = ☃;
      this.func_178217_e();
   }

   private void func_178217_e() {
      for(int ☃ = 0; ☃ < 4; ++☃) {
         int ☃x = 7 * ☃;
         this.field_178215_a[☃x + 4] = Float.floatToRawIntBits(
            this.field_178218_d.func_94214_a((double)this.field_187509_d.func_188537_a(Float.intBitsToFloat(this.field_178215_a[☃x + 4])))
         );
         this.field_178215_a[☃x + 4 + 1] = Float.floatToRawIntBits(
            this.field_178218_d.func_94207_b((double)this.field_187509_d.func_188536_b(Float.intBitsToFloat(this.field_178215_a[☃x + 4 + 1])))
         );
      }
   }
}
