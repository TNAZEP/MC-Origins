package net.minecraft.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntitySpectralArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntitySkeleton extends AbstractSkeleton {
   public EntitySkeleton(World var1) {
      super(EntityType.field_200741_ag, ☃);
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186385_aj;
   }

   @Override
   protected SoundEvent func_184639_G() {
      return SoundEvents.field_187854_fc;
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_187864_fh;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187856_fd;
   }

   @Override
   SoundEvent func_190727_o() {
      return SoundEvents.field_187868_fj;
   }

   @Override
   public void func_70645_a(DamageSource var1) {
      super.func_70645_a(☃);
      if (☃.func_76346_g() instanceof EntityCreeper) {
         EntityCreeper ☃ = (EntityCreeper)☃.func_76346_g();
         if (☃.func_70830_n() && ☃.func_70650_aV()) {
            ☃.func_175493_co();
            this.func_199703_a(Items.field_196182_dv);
         }
      }
   }

   @Override
   protected EntityArrow func_190726_a(float var1) {
      ItemStack ☃ = this.func_184582_a(EntityEquipmentSlot.OFFHAND);
      if (☃.func_77973_b() == Items.field_185166_h) {
         EntitySpectralArrow ☃x = new EntitySpectralArrow(this.field_70170_p, this);
         ☃x.func_190547_a(this, ☃);
         return ☃x;
      } else {
         EntityArrow ☃ = super.func_190726_a(☃);
         if (☃.func_77973_b() == Items.field_185167_i && ☃ instanceof EntityTippedArrow) {
            ((EntityTippedArrow)☃).func_184555_a(☃);
         }

         return ☃;
      }
   }
}
