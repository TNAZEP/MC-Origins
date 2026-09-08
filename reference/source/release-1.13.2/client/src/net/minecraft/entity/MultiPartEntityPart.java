package net.minecraft.entity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

public class MultiPartEntityPart extends Entity {
   public final IEntityMultiPart field_70259_a;
   public final String field_146032_b;

   public MultiPartEntityPart(IEntityMultiPart var1, String var2, float var3, float var4) {
      super(☃.func_200600_R(), ☃.func_82194_d());
      this.func_70105_a(☃, ☃);
      this.field_70259_a = ☃;
      this.field_146032_b = ☃;
   }

   @Override
   protected void func_70088_a() {
   }

   @Override
   protected void func_70037_a(NBTTagCompound var1) {
   }

   @Override
   protected void func_70014_b(NBTTagCompound var1) {
   }

   @Override
   public boolean func_70067_L() {
      return true;
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      return this.func_180431_b(☃) ? false : this.field_70259_a.func_70965_a(this, ☃, ☃);
   }

   @Override
   public boolean func_70028_i(Entity var1) {
      return this == ☃ || this.field_70259_a == ☃;
   }
}
