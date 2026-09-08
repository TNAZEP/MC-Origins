package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AbstractFish;
import net.minecraft.entity.passive.EntityTropicalFish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.Fluid;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
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

   @Override
   public void func_77624_a(ItemStack var1, @Nullable World var2, List<ITextComponent> var3, ITooltipFlag var4) {
      if (this.field_203794_a == EntityType.field_204262_at) {
         NBTTagCompound ☃ = ☃.func_77978_p();
         if (☃ != null && ☃.func_150297_b("BucketVariantTag", 3)) {
            int ☃x = ☃.func_74762_e("BucketVariantTag");
            TextFormatting[] ☃xx = new TextFormatting[]{TextFormatting.ITALIC, TextFormatting.GRAY};
            String ☃xxx = "color.minecraft." + EntityTropicalFish.func_212326_d(☃x);
            String ☃xxxx = "color.minecraft." + EntityTropicalFish.func_212323_p(☃x);

            for(int ☃xxxxx = 0; ☃xxxxx < EntityTropicalFish.field_204227_bz.length; ++☃xxxxx) {
               if (☃x == EntityTropicalFish.field_204227_bz[☃xxxxx]) {
                  ☃.add(new TextComponentTranslation(EntityTropicalFish.func_212324_b(☃xxxxx)).func_211709_a(☃xx));
                  return;
               }
            }

            ☃.add(new TextComponentTranslation(EntityTropicalFish.func_212327_q(☃x)).func_211709_a(☃xx));
            ITextComponent ☃xxxxx = new TextComponentTranslation(☃xxx);
            if (!☃xxx.equals(☃xxxx)) {
               ☃xxxxx.func_150258_a(", ").func_150257_a(new TextComponentTranslation(☃xxxx));
            }

            ☃xxxxx.func_211709_a(☃xx);
            ☃.add(☃xxxxx);
         }
      }
   }
}
