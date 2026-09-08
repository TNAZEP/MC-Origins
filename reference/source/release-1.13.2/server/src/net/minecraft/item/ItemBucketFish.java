package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AbstractFish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.Fluid;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class ItemBucketFish extends ItemBucket {
   private final EntityType<?> field_203794_a;

   public ItemBucketFish(EntityType<?> var1, Fluid var2, Item.Properties var3) {
      super(☃, ☃);
      this.field_203794_a = ☃;
   }

   @Override
   public void func_203792_a(World var1, ItemStack var2, BlockPos var3) {
      if (!☃.field_72995_K) {
         this.func_205357_b(☃, ☃, ☃);
      }
   }

   @Override
   protected void func_203791_b(@Nullable EntityPlayer var1, IWorld var2, BlockPos var3) {
      ☃.func_184133_a(☃, ☃, SoundEvents.field_203819_X, SoundCategory.NEUTRAL, 1.0F, 1.0F);
   }

   private void func_205357_b(World var1, ItemStack var2, BlockPos var3) {
      Entity ☃ = this.field_203794_a.func_208049_a(☃, ☃, null, ☃, true, false);
      if (☃ != null) {
         ((AbstractFish)☃).func_203706_r(true);
      }
   }
}
