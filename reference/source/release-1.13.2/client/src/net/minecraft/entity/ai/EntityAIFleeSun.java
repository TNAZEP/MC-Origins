package net.minecraft.entity.ai;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAIFleeSun extends EntityAIBase {
   private final EntityCreature field_75372_a;
   private double field_75370_b;
   private double field_75371_c;
   private double field_75368_d;
   private final double field_75369_e;
   private final World field_75367_f;

   public EntityAIFleeSun(EntityCreature var1, double var2) {
      this.field_75372_a = ☃;
      this.field_75369_e = ☃;
      this.field_75367_f = ☃.field_70170_p;
      this.func_75248_a(1);
   }

   @Override
   public boolean func_75250_a() {
      if (!this.field_75367_f.func_72935_r()) {
         return false;
      } else if (!this.field_75372_a.func_70027_ad()) {
         return false;
      } else if (!this.field_75367_f
         .func_175678_i(new BlockPos(this.field_75372_a.field_70165_t, this.field_75372_a.func_174813_aQ().field_72338_b, this.field_75372_a.field_70161_v))) {
         return false;
      } else if (!this.field_75372_a.func_184582_a(EntityEquipmentSlot.HEAD).func_190926_b()) {
         return false;
      } else {
         Vec3d ☃ = this.func_75366_f();
         if (☃ == null) {
            return false;
         } else {
            this.field_75370_b = ☃.field_72450_a;
            this.field_75371_c = ☃.field_72448_b;
            this.field_75368_d = ☃.field_72449_c;
            return true;
         }
      }
   }

   @Override
   public boolean func_75253_b() {
      return !this.field_75372_a.func_70661_as().func_75500_f();
   }

   @Override
   public void func_75249_e() {
      this.field_75372_a.func_70661_as().func_75492_a(this.field_75370_b, this.field_75371_c, this.field_75368_d, this.field_75369_e);
   }

   @Nullable
   private Vec3d func_75366_f() {
      Random ☃ = this.field_75372_a.func_70681_au();
      BlockPos ☃x = new BlockPos(this.field_75372_a.field_70165_t, this.field_75372_a.func_174813_aQ().field_72338_b, this.field_75372_a.field_70161_v);

      for(int ☃xx = 0; ☃xx < 10; ++☃xx) {
         BlockPos ☃xxx = ☃x.func_177982_a(☃.nextInt(20) - 10, ☃.nextInt(6) - 3, ☃.nextInt(20) - 10);
         if (!this.field_75367_f.func_175678_i(☃xxx) && this.field_75372_a.func_180484_a(☃xxx) < 0.0F) {
            return new Vec3d((double)☃xxx.func_177958_n(), (double)☃xxx.func_177956_o(), (double)☃xxx.func_177952_p());
         }
      }

      return null;
   }
}
