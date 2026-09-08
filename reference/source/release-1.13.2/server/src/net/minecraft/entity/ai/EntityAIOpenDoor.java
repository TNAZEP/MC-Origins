package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;

public class EntityAIOpenDoor extends EntityAIDoorInteract {
   private final boolean field_75361_i;
   private int field_75360_j;

   public EntityAIOpenDoor(EntityLiving var1, boolean var2) {
      super(☃);
      this.field_75356_a = ☃;
      this.field_75361_i = ☃;
   }

   @Override
   public boolean func_75253_b() {
      return this.field_75361_i && this.field_75360_j > 0 && super.func_75253_b();
   }

   @Override
   public void func_75249_e() {
      this.field_75360_j = 20;
      this.func_195921_a(true);
   }

   @Override
   public void func_75251_c() {
      this.func_195921_a(false);
   }

   @Override
   public void func_75246_d() {
      --this.field_75360_j;
      super.func_75246_d();
   }
}
