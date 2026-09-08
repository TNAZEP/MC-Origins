package net.minecraft.entity.ai;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;

public class EntityAINearestAttackableTarget<T extends EntityLivingBase> extends EntityAITarget {
   protected final Class<T> field_75307_b;
   private final int field_75308_c;
   protected final EntityAINearestAttackableTarget.Sorter field_75306_g;
   protected final Predicate<? super T> field_82643_g;
   protected T field_75309_a;

   public EntityAINearestAttackableTarget(EntityCreature var1, Class<T> var2, boolean var3) {
      this(☃, ☃, ☃, false);
   }

   public EntityAINearestAttackableTarget(EntityCreature var1, Class<T> var2, boolean var3, boolean var4) {
      this(☃, ☃, 10, ☃, ☃, null);
   }

   public EntityAINearestAttackableTarget(EntityCreature var1, Class<T> var2, int var3, boolean var4, boolean var5, @Nullable Predicate<? super T> var6) {
      super(☃, ☃, ☃);
      this.field_75307_b = ☃;
      this.field_75308_c = ☃;
      this.field_75306_g = new EntityAINearestAttackableTarget.Sorter(☃);
      this.func_75248_a(1);
      this.field_82643_g = var2x -> {
         if (var2x == null) {
            return false;
         } else if (☃ != null && !☃.test(var2x)) {
            return false;
         } else {
            return !EntitySelectors.field_180132_d.test(var2x) ? false : this.func_75296_a(var2x, false);
         }
      };
   }

   @Override
   public boolean func_75250_a() {
      if (this.field_75308_c > 0 && this.field_75299_d.func_70681_au().nextInt(this.field_75308_c) != 0) {
         return false;
      } else if (this.field_75307_b != EntityPlayer.class && this.field_75307_b != EntityPlayerMP.class) {
         List<T> ☃ = this.field_75299_d.field_70170_p.func_175647_a(this.field_75307_b, this.func_188511_a(this.func_111175_f()), this.field_82643_g);
         if (☃.isEmpty()) {
            return false;
         } else {
            Collections.sort(☃, this.field_75306_g);
            this.field_75309_a = (T)☃.get(0);
            return true;
         }
      } else {
         this.field_75309_a = this.field_75299_d
            .field_70170_p
            .func_184150_a(
               this.field_75299_d.field_70165_t,
               this.field_75299_d.field_70163_u + (double)this.field_75299_d.func_70047_e(),
               this.field_75299_d.field_70161_v,
               this.func_111175_f(),
               this.func_111175_f(),
               new Function<EntityPlayer, Double>() {
                  @Nullable
                  public Double apply(@Nullable EntityPlayer var1) {
                     ItemStack ☃ = ☃.func_184582_a(EntityEquipmentSlot.HEAD);
                     return (!(EntityAINearestAttackableTarget.this.field_75299_d instanceof EntitySkeleton) || ☃.func_77973_b() != Items.field_196182_dv)
                           && (!(EntityAINearestAttackableTarget.this.field_75299_d instanceof EntityZombie) || ☃.func_77973_b() != Items.field_196186_dz)
                           && (!(EntityAINearestAttackableTarget.this.field_75299_d instanceof EntityCreeper) || ☃.func_77973_b() != Items.field_196185_dy)
                        ? 1.0
                        : 0.5;
                  }
               },
               this.field_82643_g
            );
         return this.field_75309_a != null;
      }
   }

   protected AxisAlignedBB func_188511_a(double var1) {
      return this.field_75299_d.func_174813_aQ().func_72314_b(☃, 4.0, ☃);
   }

   @Override
   public void func_75249_e() {
      this.field_75299_d.func_70624_b(this.field_75309_a);
      super.func_75249_e();
   }

   public static class Sorter implements Comparator<Entity> {
      private final Entity field_75459_b;

      public Sorter(Entity var1) {
         this.field_75459_b = ☃;
      }

      public int compare(Entity var1, Entity var2) {
         double ☃ = this.field_75459_b.func_70068_e(☃);
         double ☃x = this.field_75459_b.func_70068_e(☃);
         if (☃ < ☃x) {
            return -1;
         } else {
            return ☃ > ☃x ? 1 : 0;
         }
      }
   }
}
