package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;

public class Sound implements ISoundEventAccessor<Sound> {
   private final ResourceLocation field_188726_a;
   private final float field_188727_b;
   private final float field_188728_c;
   private final int field_188729_d;
   private final Sound.Type field_188730_e;
   private final boolean field_188731_f;
   private final boolean field_204258_g;
   private final int field_206256_h;

   public Sound(String var1, float var2, float var3, int var4, Sound.Type var5, boolean var6, boolean var7, int var8) {
      this.field_188726_a = new ResourceLocation(☃);
      this.field_188727_b = ☃;
      this.field_188728_c = ☃;
      this.field_188729_d = ☃;
      this.field_188730_e = ☃;
      this.field_188731_f = ☃;
      this.field_204258_g = ☃;
      this.field_206256_h = ☃;
   }

   public ResourceLocation func_188719_a() {
      return this.field_188726_a;
   }

   public ResourceLocation func_188721_b() {
      return new ResourceLocation(this.field_188726_a.func_110624_b(), "sounds/" + this.field_188726_a.func_110623_a() + ".ogg");
   }

   public float func_188724_c() {
      return this.field_188727_b;
   }

   public float func_188725_d() {
      return this.field_188728_c;
   }

   @Override
   public int func_148721_a() {
      return this.field_188729_d;
   }

   public Sound func_148720_g() {
      return this;
   }

   public Sound.Type func_188722_g() {
      return this.field_188730_e;
   }

   public boolean func_188723_h() {
      return this.field_188731_f;
   }

   public boolean func_204257_i() {
      return this.field_204258_g;
   }

   public int func_206255_j() {
      return this.field_206256_h;
   }

   public static enum Type {
      FILE("file"),
      SOUND_EVENT("event");

      private final String field_188708_c;

      private Type(String var3) {
         this.field_188708_c = ☃;
      }

      public static Sound.Type func_188704_a(String var0) {
         for(Sound.Type ☃ : values()) {
            if (☃.field_188708_c.equals(☃)) {
               return ☃;
            }
         }

         return null;
      }
   }
}
