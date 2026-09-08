package net.minecraft.entity.ai;

import java.util.List;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class EntityAIFollowBoat extends EntityAIBase {
   private int field_205143_a;
   private final EntityCreature field_205144_b;
   private EntityLivingBase field_205145_c;
   private BoatGoals field_205146_d;

   public EntityAIFollowBoat(EntityCreature var1) {
      this.field_205144_b = ☃;
   }

   @Override
   public boolean func_75250_a() {
      List<EntityBoat> ☃ = this.field_205144_b.field_70170_p.func_72872_a(EntityBoat.class, this.field_205144_b.func_174813_aQ().func_186662_g(5.0));
      boolean ☃x = false;

      for(EntityBoat ☃xx : ☃) {
         if (☃xx.func_184179_bs() != null
            && (
               MathHelper.func_76135_e(((EntityLivingBase)☃xx.func_184179_bs()).field_70702_br) > 0.0F
                  || MathHelper.func_76135_e(((EntityLivingBase)☃xx.func_184179_bs()).field_191988_bg) > 0.0F
            )) {
            ☃x = true;
            break;
         }
      }

      return this.field_205145_c != null
            && (MathHelper.func_76135_e(this.field_205145_c.field_70702_br) > 0.0F || MathHelper.func_76135_e(this.field_205145_c.field_191988_bg) > 0.0F)
         || ☃x;
   }

   @Override
   public boolean func_75252_g() {
      return true;
   }

   @Override
   public boolean func_75253_b() {
      return this.field_205145_c != null
         && this.field_205145_c.func_184218_aH()
         && (MathHelper.func_76135_e(this.field_205145_c.field_70702_br) > 0.0F || MathHelper.func_76135_e(this.field_205145_c.field_191988_bg) > 0.0F);
   }

   @Override
   public void func_75249_e() {
      for(EntityBoat ☃ : this.field_205144_b.field_70170_p.func_72872_a(EntityBoat.class, this.field_205144_b.func_174813_aQ().func_186662_g(5.0))) {
         if (☃.func_184179_bs() != null && ☃.func_184179_bs() instanceof EntityLivingBase) {
            this.field_205145_c = (EntityLivingBase)☃.func_184179_bs();
            break;
         }
      }

      this.field_205143_a = 0;
      this.field_205146_d = BoatGoals.GO_TO_BOAT;
   }

   @Override
   public void func_75251_c() {
      this.field_205145_c = null;
   }

   @Override
   public void func_75246_d() {
      boolean ☃ = MathHelper.func_76135_e(this.field_205145_c.field_70702_br) > 0.0F || MathHelper.func_76135_e(this.field_205145_c.field_191988_bg) > 0.0F;
      float ☃x = this.field_205146_d == BoatGoals.GO_IN_BOAT_DIRECTION ? (☃ ? 0.17999999F : 0.0F) : 0.135F;
      this.field_205144_b.func_191958_b(this.field_205144_b.field_70702_br, this.field_205144_b.field_70701_bs, this.field_205144_b.field_191988_bg, ☃x);
      this.field_205144_b.func_70091_d(MoverType.SELF, this.field_205144_b.field_70159_w, this.field_205144_b.field_70181_x, this.field_205144_b.field_70179_y);
      if (--this.field_205143_a <= 0) {
         this.field_205143_a = 10;
         if (this.field_205146_d == BoatGoals.GO_TO_BOAT) {
            BlockPos ☃xx = new BlockPos(this.field_205145_c).func_177972_a(this.field_205145_c.func_174811_aO().func_176734_d());
            ☃xx = ☃xx.func_177982_a(0, -1, 0);
            this.field_205144_b.func_70661_as().func_75492_a((double)☃xx.func_177958_n(), (double)☃xx.func_177956_o(), (double)☃xx.func_177952_p(), 1.0);
            if (this.field_205144_b.func_70032_d(this.field_205145_c) < 4.0F) {
               this.field_205143_a = 0;
               this.field_205146_d = BoatGoals.GO_IN_BOAT_DIRECTION;
            }
         } else if (this.field_205146_d == BoatGoals.GO_IN_BOAT_DIRECTION) {
            EnumFacing ☃xx = this.field_205145_c.func_184172_bi();
            BlockPos ☃xxx = new BlockPos(this.field_205145_c).func_177967_a(☃xx, 10);
            this.field_205144_b
               .func_70661_as()
               .func_75492_a((double)☃xxx.func_177958_n(), (double)(☃xxx.func_177956_o() - 1), (double)☃xxx.func_177952_p(), 1.0);
            if (this.field_205144_b.func_70032_d(this.field_205145_c) > 12.0F) {
               this.field_205143_a = 0;
               this.field_205146_d = BoatGoals.GO_TO_BOAT;
            }
         }
      }
   }
}
