package net.minecraft.entity.ai;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;

public class EntityAISkeletonRiders extends EntityAIBase {
   private final EntitySkeletonHorse field_188516_a;

   public EntityAISkeletonRiders(EntitySkeletonHorse var1) {
      this.field_188516_a = ☃;
   }

   @Override
   public boolean func_75250_a() {
      return this.field_188516_a
         .field_70170_p
         .func_175636_b(this.field_188516_a.field_70165_t, this.field_188516_a.field_70163_u, this.field_188516_a.field_70161_v, 10.0);
   }

   @Override
   public void func_75246_d() {
      DifficultyInstance ☃ = this.field_188516_a.field_70170_p.func_175649_E(new BlockPos(this.field_188516_a));
      this.field_188516_a.func_190691_p(false);
      this.field_188516_a.func_110234_j(true);
      this.field_188516_a.func_70873_a(0);
      this.field_188516_a
         .field_70170_p
         .func_72942_c(
            new EntityLightningBolt(
               this.field_188516_a.field_70170_p, this.field_188516_a.field_70165_t, this.field_188516_a.field_70163_u, this.field_188516_a.field_70161_v, true
            )
         );
      EntitySkeleton ☃x = this.func_188514_a(☃, this.field_188516_a);
      ☃x.func_184220_m(this.field_188516_a);

      for(int ☃xx = 0; ☃xx < 3; ++☃xx) {
         AbstractHorse ☃xxx = this.func_188515_a(☃);
         EntitySkeleton ☃xxxx = this.func_188514_a(☃, ☃xxx);
         ☃xxxx.func_184220_m(☃xxx);
         ☃xxx.func_70024_g(this.field_188516_a.func_70681_au().nextGaussian() * 0.5, 0.0, this.field_188516_a.func_70681_au().nextGaussian() * 0.5);
      }
   }

   private AbstractHorse func_188515_a(DifficultyInstance var1) {
      EntitySkeletonHorse ☃ = new EntitySkeletonHorse(this.field_188516_a.field_70170_p);
      ☃.func_204210_a(☃, null, null);
      ☃.func_70107_b(this.field_188516_a.field_70165_t, this.field_188516_a.field_70163_u, this.field_188516_a.field_70161_v);
      ☃.field_70172_ad = 60;
      ☃.func_110163_bv();
      ☃.func_110234_j(true);
      ☃.func_70873_a(0);
      ☃.field_70170_p.func_72838_d(☃);
      return ☃;
   }

   private EntitySkeleton func_188514_a(DifficultyInstance var1, AbstractHorse var2) {
      EntitySkeleton ☃ = new EntitySkeleton(☃.field_70170_p);
      ☃.func_204210_a(☃, null, null);
      ☃.func_70107_b(☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v);
      ☃.field_70172_ad = 60;
      ☃.func_110163_bv();
      if (☃.func_184582_a(EntityEquipmentSlot.HEAD).func_190926_b()) {
         ☃.func_184201_a(EntityEquipmentSlot.HEAD, new ItemStack(Items.field_151028_Y));
      }

      ☃.func_184201_a(
         EntityEquipmentSlot.MAINHAND,
         EnchantmentHelper.func_77504_a(☃.func_70681_au(), ☃.func_184614_ca(), (int)(5.0F + ☃.func_180170_c() * (float)☃.func_70681_au().nextInt(18)), false)
      );
      ☃.func_184201_a(
         EntityEquipmentSlot.HEAD,
         EnchantmentHelper.func_77504_a(
            ☃.func_70681_au(), ☃.func_184582_a(EntityEquipmentSlot.HEAD), (int)(5.0F + ☃.func_180170_c() * (float)☃.func_70681_au().nextInt(18)), false
         )
      );
      ☃.field_70170_p.func_72838_d(☃);
      return ☃;
   }
}
