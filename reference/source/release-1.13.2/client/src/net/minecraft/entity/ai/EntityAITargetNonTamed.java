package net.minecraft.entity.ai;

import java.util.function.Predicate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAITargetNonTamed<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T> {
   private final EntityTameable field_75310_g;

   public EntityAITargetNonTamed(EntityTameable var1, Class<T> var2, boolean var3, Predicate<? super T> var4) {
      super(☃, ☃, 10, ☃, false, ☃);
      this.field_75310_g = ☃;
   }

   @Override
   public boolean func_75250_a() {
      return !this.field_75310_g.func_70909_n() && super.func_75250_a();
   }

   @Override
   public boolean func_75253_b() {
      return this.field_82643_g != null ? this.field_82643_g.test(this.field_75309_a) : super.func_75253_b();
   }
}
