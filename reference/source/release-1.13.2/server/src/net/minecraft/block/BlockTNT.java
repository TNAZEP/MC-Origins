package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockTNT extends Block {
   public static final BooleanProperty field_212569_a = BlockStateProperties.field_212646_x;

   public BlockTNT(Block.Properties var1) {
      super(☃);
      this.func_180632_j(this.func_176223_P().func_206870_a(field_212569_a, Boolean.valueOf(false)));
   }

   @Override
   public void func_196259_b(IBlockState var1, World var2, BlockPos var3, IBlockState var4) {
      if (☃.func_177230_c() != ☃.func_177230_c()) {
         if (☃.func_175640_z(☃)) {
            this.func_196534_a(☃, ☃);
            ☃.func_175698_g(☃);
         }
      }
   }

   @Override
   public void func_189540_a(IBlockState var1, World var2, BlockPos var3, Block var4, BlockPos var5) {
      if (☃.func_175640_z(☃)) {
         this.func_196534_a(☃, ☃);
         ☃.func_175698_g(☃);
      }
   }

   @Override
   public void func_196255_a(IBlockState var1, World var2, BlockPos var3, float var4, int var5) {
      if (!☃.func_177229_b(field_212569_a)) {
         super.func_196255_a(☃, ☃, ☃, ☃, ☃);
      }
   }

   @Override
   public void func_176208_a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      if (!☃.func_201670_d() && !☃.func_184812_l_() && ☃.func_177229_b(field_212569_a)) {
         this.func_196534_a(☃, ☃);
      }

      super.func_176208_a(☃, ☃, ☃, ☃);
   }

   @Override
   public void func_180652_a(World var1, BlockPos var2, Explosion var3) {
      if (!☃.field_72995_K) {
         EntityTNTPrimed ☃ = new EntityTNTPrimed(
            ☃, (double)((float)☃.func_177958_n() + 0.5F), (double)☃.func_177956_o(), (double)((float)☃.func_177952_p() + 0.5F), ☃.func_94613_c()
         );
         ☃.func_184534_a((short)(☃.field_73012_v.nextInt(☃.func_184536_l() / 4) + ☃.func_184536_l() / 8));
         ☃.func_72838_d(☃);
      }
   }

   public void func_196534_a(World var1, BlockPos var2) {
      this.func_196535_a(☃, ☃, null);
   }

   private void func_196535_a(World var1, BlockPos var2, @Nullable EntityLivingBase var3) {
      if (!☃.field_72995_K) {
         EntityTNTPrimed ☃ = new EntityTNTPrimed(
            ☃, (double)((float)☃.func_177958_n() + 0.5F), (double)☃.func_177956_o(), (double)((float)☃.func_177952_p() + 0.5F), ☃
         );
         ☃.func_72838_d(☃);
         ☃.func_184148_a(null, ☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v, SoundEvents.field_187904_gd, SoundCategory.BLOCKS, 1.0F, 1.0F);
      }
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      Item ☃x = ☃.func_77973_b();
      if (☃x != Items.field_151033_d && ☃x != Items.field_151059_bz) {
         return super.func_196250_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else {
         this.func_196535_a(☃, ☃, ☃);
         ☃.func_180501_a(☃, Blocks.field_150350_a.func_176223_P(), 11);
         if (☃x == Items.field_151033_d) {
            ☃.func_77972_a(1, ☃);
         } else {
            ☃.func_190918_g(1);
         }

         return true;
      }
   }

   @Override
   public void func_196262_a(IBlockState var1, World var2, BlockPos var3, Entity var4) {
      if (!☃.field_72995_K && ☃ instanceof EntityArrow) {
         EntityArrow ☃ = (EntityArrow)☃;
         Entity ☃x = ☃.func_212360_k();
         if (☃.func_70027_ad()) {
            this.func_196535_a(☃, ☃, ☃x instanceof EntityLivingBase ? (EntityLivingBase)☃x : null);
            ☃.func_175698_g(☃);
         }
      }
   }

   @Override
   public boolean func_149659_a(Explosion var1) {
      return false;
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_212569_a);
   }
}
