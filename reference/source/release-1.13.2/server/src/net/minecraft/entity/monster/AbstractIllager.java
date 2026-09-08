package net.minecraft.entity.monster;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public abstract class AbstractIllager extends EntityMob {
   protected static final DataParameter<Byte> field_193080_a = EntityDataManager.func_187226_a(AbstractIllager.class, DataSerializers.field_187191_a);

   protected AbstractIllager(EntityType<?> var1, World var2) {
      super(☃, ☃);
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_193080_a, (byte)0);
   }

   protected void func_193079_a(int var1, boolean var2) {
      int ☃ = this.field_70180_af.func_187225_a(field_193080_a);
      if (☃) {
         ☃ |= ☃;
      } else {
         ☃ &= ~☃;
      }

      this.field_70180_af.func_187227_b(field_193080_a, (byte)(☃ & 0xFF));
   }

   @Override
   public CreatureAttribute func_70668_bt() {
      return CreatureAttribute.ILLAGER;
   }
}
