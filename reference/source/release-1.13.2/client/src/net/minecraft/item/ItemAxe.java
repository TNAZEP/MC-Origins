package net.minecraft.item;

import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemAxe extends ItemTool {
   private static final Set<Block> field_150917_c = Sets.<Block>newHashSet(
      Blocks.field_196662_n,
      Blocks.field_196664_o,
      Blocks.field_196666_p,
      Blocks.field_196668_q,
      Blocks.field_196670_r,
      Blocks.field_196672_s,
      Blocks.field_150342_X,
      Blocks.field_196626_Q,
      Blocks.field_196629_R,
      Blocks.field_196631_S,
      Blocks.field_196634_T,
      Blocks.field_196637_U,
      Blocks.field_196639_V,
      Blocks.field_196617_K,
      Blocks.field_196618_L,
      Blocks.field_196619_M,
      Blocks.field_196620_N,
      Blocks.field_196621_O,
      Blocks.field_196623_P,
      Blocks.field_150486_ae,
      Blocks.field_150423_aK,
      Blocks.field_196625_cS,
      Blocks.field_196628_cT,
      Blocks.field_150440_ba,
      Blocks.field_150468_ap,
      Blocks.field_196689_eF,
      Blocks.field_196691_eG,
      Blocks.field_196693_eH,
      Blocks.field_196695_eI,
      Blocks.field_196699_eK,
      Blocks.field_196697_eJ,
      Blocks.field_196663_cq,
      Blocks.field_196665_cr,
      Blocks.field_196667_cs,
      Blocks.field_196669_ct,
      Blocks.field_196673_cv,
      Blocks.field_196671_cu
   );
   protected static final Map<Block, Block> field_203176_a = new Builder<Block, Block>()
      .put(Blocks.field_196626_Q, Blocks.field_209389_ab)
      .put(Blocks.field_196617_K, Blocks.field_203204_R)
      .put(Blocks.field_196639_V, Blocks.field_209394_ag)
      .put(Blocks.field_196623_P, Blocks.field_203209_W)
      .put(Blocks.field_196637_U, Blocks.field_209393_af)
      .put(Blocks.field_196621_O, Blocks.field_203208_V)
      .put(Blocks.field_196631_S, Blocks.field_209391_ad)
      .put(Blocks.field_196619_M, Blocks.field_203206_T)
      .put(Blocks.field_196634_T, Blocks.field_209392_ae)
      .put(Blocks.field_196620_N, Blocks.field_203207_U)
      .put(Blocks.field_196629_R, Blocks.field_209390_ac)
      .put(Blocks.field_196618_L, Blocks.field_203205_S)
      .build();

   protected ItemAxe(IItemTier var1, float var2, float var3, Item.Properties var4) {
      super(☃, ☃, ☃, field_150917_c, ☃);
   }

   @Override
   public float func_150893_a(ItemStack var1, IBlockState var2) {
      Material ☃ = ☃.func_185904_a();
      return ☃ != Material.field_151575_d && ☃ != Material.field_151585_k && ☃ != Material.field_151582_l ? super.func_150893_a(☃, ☃) : this.field_77864_a;
   }

   @Override
   public EnumActionResult func_195939_a(ItemUseContext var1) {
      World ☃ = ☃.func_195991_k();
      BlockPos ☃x = ☃.func_195995_a();
      IBlockState ☃xx = ☃.func_180495_p(☃x);
      Block ☃xxx = (Block)field_203176_a.get(☃xx.func_177230_c());
      if (☃xxx != null) {
         EntityPlayer ☃xxxx = ☃.func_195999_j();
         ☃.func_184133_a(☃xxxx, ☃x, SoundEvents.field_203255_y, SoundCategory.BLOCKS, 1.0F, 1.0F);
         if (!☃.field_72995_K) {
            ☃.func_180501_a(☃x, ☃xxx.func_176223_P().func_206870_a(BlockRotatedPillar.field_176298_M, ☃xx.func_177229_b(BlockRotatedPillar.field_176298_M)), 11);
            if (☃xxxx != null) {
               ☃.func_195996_i().func_77972_a(1, ☃xxxx);
            }
         }

         return EnumActionResult.SUCCESS;
      } else {
         return EnumActionResult.PASS;
      }
   }
}
