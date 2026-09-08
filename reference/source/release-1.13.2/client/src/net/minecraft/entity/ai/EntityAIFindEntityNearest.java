package net.minecraft.entity.ai;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityAIFindEntityNearest extends EntityAIBase {
   private static final Logger field_179444_a = LogManager.getLogger();
   private final EntityLiving field_179442_b;
   private final Predicate<EntityLivingBase> field_179443_c;
   private final EntityAINearestAttackableTarget.Sorter field_179440_d;
   private EntityLivingBase field_179441_e;
   private final Class<? extends EntityLivingBase> field_179439_f;

   public EntityAIFindEntityNearest(EntityLiving var1, Class<? extends EntityLivingBase> var2) {
      this.field_179442_b = ☃;
      this.field_179439_f = ☃;
      if (☃ instanceof EntityCreature) {
         field_179444_a.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
      }

      this.field_179443_c = var1x -> {
         double ☃ = this.func_179438_f();
         if (var1x.func_70093_af()) {
            ☃ *= 0.8F;
         }

         if (var1x.func_82150_aj()) {
            return false;
         } else {
            return (double)var1x.func_70032_d(this.field_179442_b) > ☃ ? false : EntityAITarget.func_179445_a(this.field_179442_b, var1x, false, true);
         }
      };
      this.field_179440_d = new EntityAINearestAttackableTarget.Sorter(☃);
   }

   @Override
   public boolean func_75250_a() {
      double ☃ = this.func_179438_f();
      List<EntityLivingBase> ☃x = this.field_179442_b
         .field_70170_p
         .func_175647_a(this.field_179439_f, this.field_179442_b.func_174813_aQ().func_72314_b(☃, 4.0, ☃), this.field_179443_c);
      Collections.sort(☃x, this.field_179440_d);
      if (☃x.isEmpty()) {
         return false;
      } else {
         this.field_179441_e = (EntityLivingBase)☃x.get(0);
         return true;
      }
   }

   @Override
   public boolean func_75253_b() {
      EntityLivingBase ☃ = this.field_179442_b.func_70638_az();
      if (☃ == null) {
         return false;
      } else if (!☃.func_70089_S()) {
         return false;
      } else {
         double ☃ = this.func_179438_f();
         if (this.field_179442_b.func_70068_e(☃) > ☃ * ☃) {
            return false;
         } else {
            return !(☃ instanceof EntityPlayerMP) || !((EntityPlayerMP)☃).field_71134_c.func_73083_d();
         }
      }
   }

   @Override
   public void func_75249_e() {
      this.field_179442_b.func_70624_b(this.field_179441_e);
      super.func_75249_e();
   }

   @Override
   public void func_75251_c() {
      this.field_179442_b.func_70624_b(null);
      super.func_75249_e();
   }

   protected double func_179438_f() {
      IAttributeInstance ☃ = this.field_179442_b.func_110148_a(SharedMonsterAttributes.field_111265_b);
      return ☃ == null ? 16.0 : ☃.func_111126_e();
   }
}
