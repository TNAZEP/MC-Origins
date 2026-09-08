package net.minecraft.entity.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityExpBottle extends EntityThrowable {
   public EntityExpBottle(World var1) {
      super(EntityType.field_200753_as, ☃);
   }

   public EntityExpBottle(World var1, EntityLivingBase var2) {
      super(EntityType.field_200753_as, ☃, ☃);
   }

   public EntityExpBottle(World var1, double var2, double var4, double var6) {
      super(EntityType.field_200753_as, ☃, ☃, ☃, ☃);
   }

   @Override
   protected float func_70185_h() {
      return 0.07F;
   }

   @Override
   protected void func_70184_a(RayTraceResult var1) {
      if (!this.field_70170_p.field_72995_K) {
         this.field_70170_p.func_175718_b(2002, new BlockPos(this), PotionUtils.func_185183_a(PotionTypes.field_185230_b));
         int ☃ = 3 + this.field_70170_p.field_73012_v.nextInt(5) + this.field_70170_p.field_73012_v.nextInt(5);

         while(☃ > 0) {
            int ☃x = EntityXPOrb.func_70527_a(☃);
            ☃ -= ☃x;
            this.field_70170_p.func_72838_d(new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, ☃x));
         }

         this.func_70106_y();
      }
   }
}
