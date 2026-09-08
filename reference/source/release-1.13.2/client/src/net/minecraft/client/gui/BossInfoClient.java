package net.minecraft.client.gui;

import net.minecraft.network.play.server.SPacketUpdateBossInfo;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BossInfo;

public class BossInfoClient extends BossInfo {
   protected float field_186766_h;
   protected long field_186767_i;

   public BossInfoClient(SPacketUpdateBossInfo var1) {
      super(☃.func_186908_a(), ☃.func_186907_c(), ☃.func_186900_e(), ☃.func_186904_f());
      this.field_186766_h = ☃.func_186906_d();
      this.field_186750_b = ☃.func_186906_d();
      this.field_186767_i = Util.func_211177_b();
      this.func_186741_a(☃.func_186909_g());
      this.func_186742_b(☃.func_186910_h());
      this.func_186743_c(☃.func_186901_i());
   }

   @Override
   public void func_186735_a(float var1) {
      this.field_186750_b = this.func_186738_f();
      this.field_186766_h = ☃;
      this.field_186767_i = Util.func_211177_b();
   }

   @Override
   public float func_186738_f() {
      long ☃ = Util.func_211177_b() - this.field_186767_i;
      float ☃x = MathHelper.func_76131_a((float)☃ / 100.0F, 0.0F, 1.0F);
      return this.field_186750_b + (this.field_186766_h - this.field_186750_b) * ☃x;
   }

   public void func_186765_a(SPacketUpdateBossInfo var1) {
      switch(☃.func_186902_b()) {
         case UPDATE_NAME:
            this.func_186739_a(☃.func_186907_c());
            break;
         case UPDATE_PCT:
            this.func_186735_a(☃.func_186906_d());
            break;
         case UPDATE_STYLE:
            this.func_186745_a(☃.func_186900_e());
            this.func_186746_a(☃.func_186904_f());
            break;
         case UPDATE_PROPERTIES:
            this.func_186741_a(☃.func_186909_g());
            this.func_186742_b(☃.func_186910_h());
      }
   }
}
