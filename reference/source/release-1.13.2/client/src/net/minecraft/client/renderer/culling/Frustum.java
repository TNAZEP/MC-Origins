package net.minecraft.client.renderer.culling;

import net.minecraft.util.math.AxisAlignedBB;

public class Frustum implements ICamera {
   private final ClippingHelper field_78552_a;
   private double field_78550_b;
   private double field_78551_c;
   private double field_78549_d;

   public Frustum() {
      this(ClippingHelperImpl.func_78558_a());
   }

   public Frustum(ClippingHelper var1) {
      this.field_78552_a = ☃;
   }

   @Override
   public void func_78547_a(double var1, double var3, double var5) {
      this.field_78550_b = ☃;
      this.field_78551_c = ☃;
      this.field_78549_d = ☃;
   }

   public boolean func_78548_b(double var1, double var3, double var5, double var7, double var9, double var11) {
      return this.field_78552_a
         .func_78553_b(
            ☃ - this.field_78550_b, ☃ - this.field_78551_c, ☃ - this.field_78549_d, ☃ - this.field_78550_b, ☃ - this.field_78551_c, ☃ - this.field_78549_d
         );
   }

   @Override
   public boolean func_78546_a(AxisAlignedBB var1) {
      return this.func_78548_b(☃.field_72340_a, ☃.field_72338_b, ☃.field_72339_c, ☃.field_72336_d, ☃.field_72337_e, ☃.field_72334_f);
   }
}
