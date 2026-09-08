package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.init.Fluids;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.stats.StatList;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class ItemBucket extends Item {
   private final Fluid field_77876_a;

   public ItemBucket(Fluid var1, Item.Properties var2) {
      super(☃);
      this.field_77876_a = ☃;
   }

   @Override
   public ActionResult<ItemStack> func_77659_a(World var1, EntityPlayer var2, EnumHand var3) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      RayTraceResult ☃x = this.func_77621_a(☃, ☃, this.field_77876_a == Fluids.field_204541_a);
      if (☃x == null) {
         return new ActionResult<>(EnumActionResult.PASS, ☃);
      } else if (☃x.field_72313_a == RayTraceResult.Type.BLOCK) {
         BlockPos ☃ = ☃x.func_178782_a();
         if (!☃.func_175660_a(☃, ☃) || !☃.func_175151_a(☃, ☃x.field_178784_b, ☃)) {
            return new ActionResult<>(EnumActionResult.FAIL, ☃);
         } else if (this.field_77876_a == Fluids.field_204541_a) {
            IBlockState ☃ = ☃.func_180495_p(☃);
            if (☃.func_177230_c() instanceof IBucketPickupHandler) {
               Fluid ☃x = ((IBucketPickupHandler)☃.func_177230_c()).func_204508_a(☃, ☃, ☃);
               if (☃x != Fluids.field_204541_a) {
                  ☃.func_71029_a(StatList.field_75929_E.func_199076_b(this));
                  ☃.func_184185_a(☃x.func_207185_a(FluidTags.field_206960_b) ? SoundEvents.field_187633_N : SoundEvents.field_187630_M, 1.0F, 1.0F);
                  ItemStack ☃xx = this.func_150910_a(☃, ☃, ☃x.func_204524_b());
                  if (!☃.field_72995_K) {
                     CriteriaTriggers.field_204813_j.func_204817_a((EntityPlayerMP)☃, new ItemStack(☃x.func_204524_b()));
                  }

                  return new ActionResult<>(EnumActionResult.SUCCESS, ☃xx);
               }
            }

            return new ActionResult<>(EnumActionResult.FAIL, ☃);
         } else {
            IBlockState ☃ = ☃.func_180495_p(☃);
            BlockPos ☃x = this.func_210768_a(☃, ☃, ☃x);
            if (this.func_180616_a(☃, ☃, ☃x, ☃x)) {
               this.func_203792_a(☃, ☃, ☃x);
               if (☃ instanceof EntityPlayerMP) {
                  CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)☃, ☃x, ☃);
               }

               ☃.func_71029_a(StatList.field_75929_E.func_199076_b(this));
               return new ActionResult<>(EnumActionResult.SUCCESS, this.func_203790_a(☃, ☃));
            } else {
               return new ActionResult<>(EnumActionResult.FAIL, ☃);
            }
         }
      } else {
         return new ActionResult<>(EnumActionResult.PASS, ☃);
      }
   }

   private BlockPos func_210768_a(IBlockState var1, BlockPos var2, RayTraceResult var3) {
      return ☃.func_177230_c() instanceof ILiquidContainer ? ☃ : ☃.func_178782_a().func_177972_a(☃.field_178784_b);
   }

   protected ItemStack func_203790_a(ItemStack var1, EntityPlayer var2) {
      return !☃.field_71075_bZ.field_75098_d ? new ItemStack(Items.field_151133_ar) : ☃;
   }

   public void func_203792_a(World var1, ItemStack var2, BlockPos var3) {
   }

   private ItemStack func_150910_a(ItemStack var1, EntityPlayer var2, Item var3) {
      if (☃.field_71075_bZ.field_75098_d) {
         return ☃;
      } else {
         ☃.func_190918_g(1);
         if (☃.func_190926_b()) {
            return new ItemStack(☃);
         } else {
            if (!☃.field_71071_by.func_70441_a(new ItemStack(☃))) {
               ☃.func_71019_a(new ItemStack(☃), false);
            }

            return ☃;
         }
      }
   }

   public boolean func_180616_a(@Nullable EntityPlayer var1, World var2, BlockPos var3, @Nullable RayTraceResult var4) {
      if (!(this.field_77876_a instanceof FlowingFluid)) {
         return false;
      } else {
         IBlockState ☃ = ☃.func_180495_p(☃);
         Material ☃x = ☃.func_185904_a();
         boolean ☃xx = !☃x.func_76220_a();
         boolean ☃xxx = ☃x.func_76222_j();
         if (☃.func_175623_d(☃)
            || ☃xx
            || ☃xxx
            || ☃.func_177230_c() instanceof ILiquidContainer && ((ILiquidContainer)☃.func_177230_c()).func_204510_a(☃, ☃, ☃, this.field_77876_a)) {
            if (☃.field_73011_w.func_177500_n() && this.field_77876_a.func_207185_a(FluidTags.field_206959_a)) {
               int ☃xxxx = ☃.func_177958_n();
               int ☃xxxxx = ☃.func_177956_o();
               int ☃xxxxxx = ☃.func_177952_p();
               ☃.func_184133_a(
                  ☃, ☃, SoundEvents.field_187646_bt, SoundCategory.BLOCKS, 0.5F, 2.6F + (☃.field_73012_v.nextFloat() - ☃.field_73012_v.nextFloat()) * 0.8F
               );

               for(int ☃xxxxxxx = 0; ☃xxxxxxx < 8; ++☃xxxxxxx) {
                  ☃.func_195594_a(
                     Particles.field_197594_E, (double)☃xxxx + Math.random(), (double)☃xxxxx + Math.random(), (double)☃xxxxxx + Math.random(), 0.0, 0.0, 0.0
                  );
               }
            } else if (☃.func_177230_c() instanceof ILiquidContainer) {
               if (((ILiquidContainer)☃.func_177230_c()).func_204509_a(☃, ☃, ☃, ((FlowingFluid)this.field_77876_a).func_207204_a(false))) {
                  this.func_203791_b(☃, ☃, ☃);
               }
            } else {
               if (!☃.field_72995_K && (☃xx || ☃xxx) && !☃x.func_76224_d()) {
                  ☃.func_175655_b(☃, true);
               }

               this.func_203791_b(☃, ☃, ☃);
               ☃.func_180501_a(☃, this.field_77876_a.func_207188_f().func_206883_i(), 11);
            }

            return true;
         } else {
            return ☃ == null ? false : this.func_180616_a(☃, ☃, ☃.func_178782_a().func_177972_a(☃.field_178784_b), null);
         }
      }
   }

   protected void func_203791_b(@Nullable EntityPlayer var1, IWorld var2, BlockPos var3) {
      SoundEvent ☃ = this.field_77876_a.func_207185_a(FluidTags.field_206960_b) ? SoundEvents.field_187627_L : SoundEvents.field_187624_K;
      ☃.func_184133_a(☃, ☃, ☃, SoundCategory.BLOCKS, 1.0F, 1.0F);
   }
}
