package net.minecraft.entity.ai;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.Team;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityAIFindEntityNearestPlayer extends EntityAIBase {
   private static final Logger field_179436_a = LogManager.getLogger();
   private final EntityLiving field_179434_b;
   private final Predicate<Entity> field_179435_c;
   private final EntityAINearestAttackableTarget.Sorter field_179432_d;
   private EntityLivingBase field_179433_e;

   public EntityAIFindEntityNearestPlayer(EntityLiving var1) {
      this.field_179434_b = ☃;
      if (☃ instanceof EntityCreature) {
         field_179436_a.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
      }

      this.field_179435_c = var1x -> {
         if (!(var1x instanceof EntityPlayer)) {
            return false;
         } else if (((EntityPlayer)var1x).field_71075_bZ.field_75102_a) {
            return false;
         } else {
            double ☃ = this.func_179431_f();
            if (var1x.func_70093_af()) {
               ☃ *= 0.8F;
            }

            if (var1x.func_82150_aj()) {
               float ☃ = ((EntityPlayer)var1x).func_82243_bO();
               if (☃ < 0.1F) {
                  ☃ = 0.1F;
               }

               ☃ *= (double)(0.7F * ☃);
            }

            return (double)var1x.func_70032_d(this.field_179434_b) > ☃
               ? false
               : EntityAITarget.func_179445_a(this.field_179434_b, (EntityLivingBase)var1x, false, true);
         }
      };
      this.field_179432_d = new EntityAINearestAttackableTarget.Sorter(☃);
   }

   @Override
   public boolean func_75250_a() {
      double ☃ = this.func_179431_f();
      List<EntityPlayer> ☃x = this.field_179434_b
         .field_70170_p
         .func_175647_a(EntityPlayer.class, this.field_179434_b.func_174813_aQ().func_72314_b(☃, 4.0, ☃), this.field_179435_c);
      Collections.sort(☃x, this.field_179432_d);
      if (☃x.isEmpty()) {
         return false;
      } else {
         this.field_179433_e = (EntityLivingBase)☃x.get(0);
         return true;
      }
   }

   @Override
   public boolean func_75253_b() {
      EntityLivingBase ☃ = this.field_179434_b.func_70638_az();
      if (☃ == null) {
         return false;
      } else if (!☃.func_70089_S()) {
         return false;
      } else if (☃ instanceof EntityPlayer && ((EntityPlayer)☃).field_71075_bZ.field_75102_a) {
         return false;
      } else {
         Team ☃ = this.field_179434_b.func_96124_cp();
         Team ☃x = ☃.func_96124_cp();
         if (☃ != null && ☃x == ☃) {
            return false;
         } else {
            double ☃ = this.func_179431_f();
            if (this.field_179434_b.func_70068_e(☃) > ☃ * ☃) {
               return false;
            } else {
               return !(☃ instanceof EntityPlayerMP) || !((EntityPlayerMP)☃).field_71134_c.func_73083_d();
            }
         }
      }
   }

   @Override
   public void func_75249_e() {
      this.field_179434_b.func_70624_b(this.field_179433_e);
      super.func_75249_e();
   }

   @Override
   public void func_75251_c() {
      this.field_179434_b.func_70624_b(null);
      super.func_75249_e();
   }

   protected double func_179431_f() {
      IAttributeInstance ☃ = this.field_179434_b.func_110148_a(SharedMonsterAttributes.field_111265_b);
      return ☃ == null ? 16.0 : ☃.func_111126_e();
   }
}
