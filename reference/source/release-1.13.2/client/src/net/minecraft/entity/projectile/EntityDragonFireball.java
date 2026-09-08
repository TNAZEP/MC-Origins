package net.minecraft.entity.projectile;

import java.util.List;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.init.MobEffects;
import net.minecraft.init.Particles;
import net.minecraft.particles.IParticleData;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityDragonFireball extends EntityFireball {
   public EntityDragonFireball(World var1) {
      super(EntityType.field_200799_m, ☃, 1.0F, 1.0F);
   }

   public EntityDragonFireball(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(EntityType.field_200799_m, ☃, ☃, ☃, ☃, ☃, ☃, ☃, 1.0F, 1.0F);
   }

   public EntityDragonFireball(World var1, EntityLivingBase var2, double var3, double var5, double var7) {
      super(EntityType.field_200799_m, ☃, ☃, ☃, ☃, ☃, 1.0F, 1.0F);
   }

   @Override
   protected void func_70227_a(RayTraceResult var1) {
      if (☃.field_72308_g == null || !☃.field_72308_g.func_70028_i(this.field_70235_a)) {
         if (!this.field_70170_p.field_72995_K) {
            List<EntityLivingBase> ☃ = this.field_70170_p.func_72872_a(EntityLivingBase.class, this.func_174813_aQ().func_72314_b(4.0, 2.0, 4.0));
            EntityAreaEffectCloud ☃x = new EntityAreaEffectCloud(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
            ☃x.func_184481_a(this.field_70235_a);
            ☃x.func_195059_a(Particles.field_197616_i);
            ☃x.func_184483_a(3.0F);
            ☃x.func_184486_b(600);
            ☃x.func_184487_c((7.0F - ☃x.func_184490_j()) / (float)☃x.func_184489_o());
            ☃x.func_184496_a(new PotionEffect(MobEffects.field_76433_i, 1, 1));
            if (!☃.isEmpty()) {
               for(EntityLivingBase ☃xx : ☃) {
                  double ☃xxx = this.func_70068_e(☃xx);
                  if (☃xxx < 16.0) {
                     ☃x.func_70107_b(☃xx.field_70165_t, ☃xx.field_70163_u, ☃xx.field_70161_v);
                     break;
                  }
               }
            }

            this.field_70170_p.func_175718_b(2006, new BlockPos(this.field_70165_t, this.field_70163_u, this.field_70161_v), 0);
            this.field_70170_p.func_72838_d(☃x);
            this.func_70106_y();
         }
      }
   }

   @Override
   public boolean func_70067_L() {
      return false;
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      return false;
   }

   @Override
   protected IParticleData func_195057_f() {
      return Particles.field_197616_i;
   }

   @Override
   protected boolean func_184564_k() {
      return false;
   }
}
