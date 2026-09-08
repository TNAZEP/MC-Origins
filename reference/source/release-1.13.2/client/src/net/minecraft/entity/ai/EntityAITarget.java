package net.minecraft.entity.ai;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public abstract class EntityAITarget extends EntityAIBase {
   protected final EntityCreature field_75299_d;
   protected boolean field_75297_f;
   private final boolean field_75303_a;
   private int field_75301_b;
   private int field_75302_c;
   private int field_75298_g;
   protected EntityLivingBase field_188509_g;
   protected int field_188510_h = 60;

   public EntityAITarget(EntityCreature var1, boolean var2) {
      this(☃, ☃, false);
   }

   public EntityAITarget(EntityCreature var1, boolean var2, boolean var3) {
      this.field_75299_d = ☃;
      this.field_75297_f = ☃;
      this.field_75303_a = ☃;
   }

   @Override
   public boolean func_75253_b() {
      EntityLivingBase ☃ = this.field_75299_d.func_70638_az();
      if (☃ == null) {
         ☃ = this.field_188509_g;
      }

      if (☃ == null) {
         return false;
      } else if (!☃.func_70089_S()) {
         return false;
      } else {
         Team ☃ = this.field_75299_d.func_96124_cp();
         Team ☃x = ☃.func_96124_cp();
         if (☃ != null && ☃x == ☃) {
            return false;
         } else {
            double ☃ = this.func_111175_f();
            if (this.field_75299_d.func_70068_e(☃) > ☃ * ☃) {
               return false;
            } else {
               if (this.field_75297_f) {
                  if (this.field_75299_d.func_70635_at().func_75522_a(☃)) {
                     this.field_75298_g = 0;
                  } else if (++this.field_75298_g > this.field_188510_h) {
                     return false;
                  }
               }

               if (☃ instanceof EntityPlayer && ((EntityPlayer)☃).field_71075_bZ.field_75102_a) {
                  return false;
               } else {
                  this.field_75299_d.func_70624_b(☃);
                  return true;
               }
            }
         }
      }
   }

   protected double func_111175_f() {
      IAttributeInstance ☃ = this.field_75299_d.func_110148_a(SharedMonsterAttributes.field_111265_b);
      return ☃ == null ? 16.0 : ☃.func_111126_e();
   }

   @Override
   public void func_75249_e() {
      this.field_75301_b = 0;
      this.field_75302_c = 0;
      this.field_75298_g = 0;
   }

   @Override
   public void func_75251_c() {
      this.field_75299_d.func_70624_b(null);
      this.field_188509_g = null;
   }

   public static boolean func_179445_a(EntityLiving var0, @Nullable EntityLivingBase var1, boolean var2, boolean var3) {
      if (☃ == null) {
         return false;
      } else if (☃ == ☃) {
         return false;
      } else if (!☃.func_70089_S()) {
         return false;
      } else if (!☃.func_70686_a(☃.getClass())) {
         return false;
      } else if (☃.func_184191_r(☃)) {
         return false;
      } else {
         if (☃ instanceof IEntityOwnable && ((IEntityOwnable)☃).func_184753_b() != null) {
            if (☃ instanceof IEntityOwnable && ((IEntityOwnable)☃).func_184753_b().equals(((IEntityOwnable)☃).func_184753_b())) {
               return false;
            }

            if (☃ == ((IEntityOwnable)☃).func_70902_q()) {
               return false;
            }
         } else if (☃ instanceof EntityPlayer && !☃ && ((EntityPlayer)☃).field_71075_bZ.field_75102_a) {
            return false;
         }

         return !☃ || ☃.func_70635_at().func_75522_a(☃);
      }
   }

   protected boolean func_75296_a(@Nullable EntityLivingBase var1, boolean var2) {
      if (!func_179445_a(this.field_75299_d, ☃, ☃, this.field_75297_f)) {
         return false;
      } else if (!this.field_75299_d.func_180485_d(new BlockPos(☃))) {
         return false;
      } else {
         if (this.field_75303_a) {
            if (--this.field_75302_c <= 0) {
               this.field_75301_b = 0;
            }

            if (this.field_75301_b == 0) {
               this.field_75301_b = this.func_75295_a(☃) ? 1 : 2;
            }

            if (this.field_75301_b == 2) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean func_75295_a(EntityLivingBase var1) {
      this.field_75302_c = 10 + this.field_75299_d.func_70681_au().nextInt(5);
      Path ☃ = this.field_75299_d.func_70661_as().func_75494_a(☃);
      if (☃ == null) {
         return false;
      } else {
         PathPoint ☃ = ☃.func_75870_c();
         if (☃ == null) {
            return false;
         } else {
            int ☃ = ☃.field_75839_a - MathHelper.func_76128_c(☃.field_70165_t);
            int ☃x = ☃.field_75838_c - MathHelper.func_76128_c(☃.field_70161_v);
            return (double)(☃ * ☃ + ☃x * ☃x) <= 2.25;
         }
      }
   }

   public EntityAITarget func_190882_b(int var1) {
      this.field_188510_h = ☃;
      return this;
   }
}
