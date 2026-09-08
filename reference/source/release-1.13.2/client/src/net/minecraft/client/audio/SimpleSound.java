package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class SimpleSound extends AbstractSound {
   public SimpleSound(SoundEvent var1, SoundCategory var2, float var3, float var4, BlockPos var5) {
      this(☃, ☃, ☃, ☃, (float)☃.func_177958_n() + 0.5F, (float)☃.func_177956_o() + 0.5F, (float)☃.func_177952_p() + 0.5F);
   }

   public static SimpleSound func_184371_a(SoundEvent var0, float var1) {
      return func_194007_a(☃, ☃, 0.25F);
   }

   public static SimpleSound func_194007_a(SoundEvent var0, float var1, float var2) {
      return new SimpleSound(☃, SoundCategory.MASTER, ☃, ☃, false, 0, ISound.AttenuationType.NONE, 0.0F, 0.0F, 0.0F);
   }

   public static SimpleSound func_184370_a(SoundEvent var0) {
      return new SimpleSound(☃, SoundCategory.MUSIC, 1.0F, 1.0F, false, 0, ISound.AttenuationType.NONE, 0.0F, 0.0F, 0.0F);
   }

   public static SimpleSound func_184372_a(SoundEvent var0, float var1, float var2, float var3) {
      return new SimpleSound(☃, SoundCategory.RECORDS, 4.0F, 1.0F, false, 0, ISound.AttenuationType.LINEAR, ☃, ☃, ☃);
   }

   public SimpleSound(SoundEvent var1, SoundCategory var2, float var3, float var4, float var5, float var6, float var7) {
      this(☃, ☃, ☃, ☃, false, 0, ISound.AttenuationType.LINEAR, ☃, ☃, ☃);
   }

   private SimpleSound(
      SoundEvent var1, SoundCategory var2, float var3, float var4, boolean var5, int var6, ISound.AttenuationType var7, float var8, float var9, float var10
   ) {
      this(☃.func_187503_a(), ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
   }

   public SimpleSound(
      ResourceLocation var1,
      SoundCategory var2,
      float var3,
      float var4,
      boolean var5,
      int var6,
      ISound.AttenuationType var7,
      float var8,
      float var9,
      float var10
   ) {
      super(☃, ☃);
      this.field_147662_b = ☃;
      this.field_147663_c = ☃;
      this.field_147660_d = ☃;
      this.field_147661_e = ☃;
      this.field_147658_f = ☃;
      this.field_147659_g = ☃;
      this.field_147665_h = ☃;
      this.field_147666_i = ☃;
   }
}
