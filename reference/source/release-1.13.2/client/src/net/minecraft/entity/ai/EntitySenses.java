package net.minecraft.entity.ai;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

public class EntitySenses {
   private final EntityLiving field_75526_a;
   private final List<Entity> field_75524_b = Lists.<Entity>newArrayList();
   private final List<Entity> field_75525_c = Lists.<Entity>newArrayList();

   public EntitySenses(EntityLiving var1) {
      this.field_75526_a = ☃;
   }

   public void func_75523_a() {
      this.field_75524_b.clear();
      this.field_75525_c.clear();
   }

   public boolean func_75522_a(Entity var1) {
      if (this.field_75524_b.contains(☃)) {
         return true;
      } else if (this.field_75525_c.contains(☃)) {
         return false;
      } else {
         this.field_75526_a.field_70170_p.field_72984_F.func_76320_a("canSee");
         boolean ☃ = this.field_75526_a.func_70685_l(☃);
         this.field_75526_a.field_70170_p.field_72984_F.func_76319_b();
         if (☃) {
            this.field_75524_b.add(☃);
         } else {
            this.field_75525_c.add(☃);
         }

         return ☃;
      }
   }
}
