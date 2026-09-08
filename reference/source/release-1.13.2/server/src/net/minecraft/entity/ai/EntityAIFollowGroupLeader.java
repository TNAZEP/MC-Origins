package net.minecraft.entity.ai;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.entity.passive.AbstractGroupFish;

public class EntityAIFollowGroupLeader extends EntityAIBase {
   private final AbstractGroupFish field_203785_a;
   private int field_203787_c;
   private int field_212826_c;

   public EntityAIFollowGroupLeader(AbstractGroupFish var1) {
      this.field_203785_a = ☃;
      this.field_212826_c = this.func_212825_a(☃);
   }

   protected int func_212825_a(AbstractGroupFish var1) {
      return 200 + ☃.func_70681_au().nextInt(200) % 20;
   }

   @Override
   public boolean func_75250_a() {
      if (this.field_203785_a.func_212812_dE()) {
         return false;
      } else if (this.field_203785_a.func_212802_dB()) {
         return true;
      } else if (this.field_212826_c > 0) {
         --this.field_212826_c;
         return false;
      } else {
         this.field_212826_c = this.func_212825_a(this.field_203785_a);
         Predicate<AbstractGroupFish> ☃ = var0 -> var0.func_212811_dD() || !var0.func_212802_dB();
         List<AbstractGroupFish> ☃x = this.field_203785_a
            .field_70170_p
            .func_175647_a(this.field_203785_a.getClass(), this.field_203785_a.func_174813_aQ().func_72314_b(8.0, 8.0, 8.0), ☃);
         AbstractGroupFish ☃xx = (AbstractGroupFish)☃x.stream().filter(AbstractGroupFish::func_212811_dD).findAny().orElse(this.field_203785_a);
         ☃xx.func_212810_a(☃x.stream().filter(var0 -> !var0.func_212802_dB()));
         return this.field_203785_a.func_212802_dB();
      }
   }

   @Override
   public boolean func_75253_b() {
      return this.field_203785_a.func_212802_dB() && this.field_203785_a.func_212809_dF();
   }

   @Override
   public void func_75249_e() {
      this.field_203787_c = 0;
   }

   @Override
   public void func_75251_c() {
      this.field_203785_a.func_212808_dC();
   }

   @Override
   public void func_75246_d() {
      if (--this.field_203787_c <= 0) {
         this.field_203787_c = 10;
         this.field_203785_a.func_212805_dG();
      }
   }
}
