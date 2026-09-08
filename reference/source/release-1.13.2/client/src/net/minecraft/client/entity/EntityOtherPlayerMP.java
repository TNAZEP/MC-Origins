package net.minecraft.client.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class EntityOtherPlayerMP extends AbstractClientPlayer {
   public EntityOtherPlayerMP(World var1, GameProfile var2) {
      super(☃, ☃);
      this.field_70138_W = 1.0F;
      this.field_70145_X = true;
      this.field_71082_cx = 0.25F;
   }

   @Override
   public boolean func_70112_a(double var1) {
      double ☃ = this.func_174813_aQ().func_72320_b() * 10.0;
      if (Double.isNaN(☃)) {
         ☃ = 1.0;
      }

      ☃ *= 64.0 * func_184183_bd();
      return ☃ < ☃ * ☃;
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      return true;
   }

   @Override
   public void func_70071_h_() {
      this.field_71082_cx = 0.0F;
      super.func_70071_h_();
      this.field_184618_aE = this.field_70721_aZ;
      double ☃ = this.field_70165_t - this.field_70169_q;
      double ☃x = this.field_70161_v - this.field_70166_s;
      float ☃xx = MathHelper.func_76133_a(☃ * ☃ + ☃x * ☃x) * 4.0F;
      if (☃xx > 1.0F) {
         ☃xx = 1.0F;
      }

      this.field_70721_aZ += (☃xx - this.field_70721_aZ) * 0.4F;
      this.field_184619_aG += this.field_70721_aZ;
   }

   @Override
   public void func_70636_d() {
      if (this.field_70716_bi > 0) {
         double ☃ = this.field_70165_t + (this.field_184623_bh - this.field_70165_t) / (double)this.field_70716_bi;
         double ☃x = this.field_70163_u + (this.field_184624_bi - this.field_70163_u) / (double)this.field_70716_bi;
         double ☃xx = this.field_70161_v + (this.field_184625_bj - this.field_70161_v) / (double)this.field_70716_bi;
         this.field_70177_z = (float)(
            (double)this.field_70177_z + MathHelper.func_76138_g(this.field_184626_bk - (double)this.field_70177_z) / (double)this.field_70716_bi
         );
         this.field_70125_A = (float)((double)this.field_70125_A + (this.field_70709_bj - (double)this.field_70125_A) / (double)this.field_70716_bi);
         --this.field_70716_bi;
         this.func_70107_b(☃, ☃x, ☃xx);
         this.func_70101_b(this.field_70177_z, this.field_70125_A);
      }

      if (this.field_208002_br > 0) {
         this.field_70759_as = (float)(
            (double)this.field_70759_as + MathHelper.func_76138_g(this.field_208001_bq - (double)this.field_70759_as) / (double)this.field_208002_br
         );
         --this.field_208002_br;
      }

      this.field_71107_bF = this.field_71109_bG;
      this.func_82168_bl();
      float ☃ = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
      float ☃x = (float)Math.atan(-this.field_70181_x * 0.2F) * 15.0F;
      if (☃ > 0.1F) {
         ☃ = 0.1F;
      }

      if (!this.field_70122_E || this.func_110143_aJ() <= 0.0F) {
         ☃ = 0.0F;
      }

      if (this.field_70122_E || this.func_110143_aJ() <= 0.0F) {
         ☃x = 0.0F;
      }

      this.field_71109_bG += (☃ - this.field_71109_bG) * 0.4F;
      this.field_70726_aT += (☃x - this.field_70726_aT) * 0.8F;
      this.field_70170_p.field_72984_F.func_76320_a("push");
      this.func_85033_bc();
      this.field_70170_p.field_72984_F.func_76319_b();
   }

   @Override
   public void func_145747_a(ITextComponent var1) {
      Minecraft.func_71410_x().field_71456_v.func_146158_b().func_146227_a(☃);
   }
}
