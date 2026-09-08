package net.minecraft.item;

import java.util.List;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemGlassBottle extends Item {
   public ItemGlassBottle(Item.Properties var1) {
      super(☃);
   }

   @Override
   public ActionResult<ItemStack> func_77659_a(World var1, EntityPlayer var2, EnumHand var3) {
      List<EntityAreaEffectCloud> ☃ = ☃.func_175647_a(
         EntityAreaEffectCloud.class,
         ☃.func_174813_aQ().func_186662_g(2.0),
         var0 -> var0 != null && var0.func_70089_S() && var0.func_184494_w() instanceof EntityDragon
      );
      ItemStack ☃x = ☃.func_184586_b(☃);
      if (!☃.isEmpty()) {
         EntityAreaEffectCloud ☃xx = (EntityAreaEffectCloud)☃.get(0);
         ☃xx.func_184483_a(☃xx.func_184490_j() - 0.5F);
         ☃.func_184148_a(null, ☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v, SoundEvents.field_187618_I, SoundCategory.NEUTRAL, 1.0F, 1.0F);
         return new ActionResult<>(EnumActionResult.SUCCESS, this.func_185061_a(☃x, ☃, new ItemStack(Items.field_185157_bK)));
      } else {
         RayTraceResult ☃ = this.func_77621_a(☃, ☃, true);
         if (☃ == null) {
            return new ActionResult<>(EnumActionResult.PASS, ☃x);
         } else {
            if (☃.field_72313_a == RayTraceResult.Type.BLOCK) {
               BlockPos ☃ = ☃.func_178782_a();
               if (!☃.func_175660_a(☃, ☃)) {
                  return new ActionResult<>(EnumActionResult.PASS, ☃x);
               }

               if (☃.func_204610_c(☃).func_206884_a(FluidTags.field_206959_a)) {
                  ☃.func_184148_a(☃, ☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v, SoundEvents.field_187615_H, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                  return new ActionResult<>(
                     EnumActionResult.SUCCESS,
                     this.func_185061_a(☃x, ☃, PotionUtils.func_185188_a(new ItemStack(Items.field_151068_bn), PotionTypes.field_185230_b))
                  );
               }
            }

            return new ActionResult<>(EnumActionResult.PASS, ☃x);
         }
      }
   }

   protected ItemStack func_185061_a(ItemStack var1, EntityPlayer var2, ItemStack var3) {
      ☃.func_190918_g(1);
      ☃.func_71029_a(StatList.field_75929_E.func_199076_b(this));
      if (☃.func_190926_b()) {
         return ☃;
      } else {
         if (!☃.field_71071_by.func_70441_a(☃)) {
            ☃.func_71019_a(☃, false);
         }

         return ☃;
      }
   }
}
