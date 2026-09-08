package net.minecraft.entity.player;

import net.minecraft.nbt.NBTTagCompound;

public class PlayerCapabilities {
   public boolean field_75102_a;
   public boolean field_75100_b;
   public boolean field_75101_c;
   public boolean field_75098_d;
   public boolean field_75099_e = true;
   private double field_75096_f = 0.05F;
   private float field_75097_g = 0.1F;

   public void func_75091_a(NBTTagCompound var1) {
      NBTTagCompound ☃ = new NBTTagCompound();
      ☃.func_74757_a("invulnerable", this.field_75102_a);
      ☃.func_74757_a("flying", this.field_75100_b);
      ☃.func_74757_a("mayfly", this.field_75101_c);
      ☃.func_74757_a("instabuild", this.field_75098_d);
      ☃.func_74757_a("mayBuild", this.field_75099_e);
      ☃.func_74776_a("flySpeed", (float)this.field_75096_f);
      ☃.func_74776_a("walkSpeed", this.field_75097_g);
      ☃.func_74782_a("abilities", ☃);
   }

   public void func_75095_b(NBTTagCompound var1) {
      if (☃.func_150297_b("abilities", 10)) {
         NBTTagCompound ☃ = ☃.func_74775_l("abilities");
         this.field_75102_a = ☃.func_74767_n("invulnerable");
         this.field_75100_b = ☃.func_74767_n("flying");
         this.field_75101_c = ☃.func_74767_n("mayfly");
         this.field_75098_d = ☃.func_74767_n("instabuild");
         if (☃.func_150297_b("flySpeed", 99)) {
            this.field_75096_f = (double)☃.func_74760_g("flySpeed");
            this.field_75097_g = ☃.func_74760_g("walkSpeed");
         }

         if (☃.func_150297_b("mayBuild", 1)) {
            this.field_75099_e = ☃.func_74767_n("mayBuild");
         }
      }
   }

   public float func_75093_a() {
      return (float)this.field_75096_f;
   }

   public float func_75094_b() {
      return this.field_75097_g;
   }
}
