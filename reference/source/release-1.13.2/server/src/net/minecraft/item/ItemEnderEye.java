package net.minecraft.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ItemEnderEye extends Item {
   public ItemEnderEye(Item.Properties var1) {
      super(☃);
   }

   @Override
   public EnumActionResult func_195939_a(ItemUseContext var1) {
      World ☃ = ☃.func_195991_k();
      BlockPos ☃x = ☃.func_195995_a();
      IBlockState ☃xx = ☃.func_180495_p(☃x);
      if (☃xx.func_177230_c() != Blocks.field_150378_br || ☃xx.func_177229_b(BlockEndPortalFrame.field_176507_b)) {
         return EnumActionResult.PASS;
      } else if (☃.field_72995_K) {
         return EnumActionResult.SUCCESS;
      } else {
         IBlockState ☃ = ☃xx.func_206870_a(BlockEndPortalFrame.field_176507_b, Boolean.valueOf(true));
         Block.func_199601_a(☃xx, ☃, ☃, ☃x);
         ☃.func_180501_a(☃x, ☃, 2);
         ☃.func_175666_e(☃x, Blocks.field_150378_br);
         ☃.func_195996_i().func_190918_g(1);

         for(int ☃x = 0; ☃x < 16; ++☃x) {
            double ☃xx = (double)((float)☃x.func_177958_n() + (5.0F + field_77697_d.nextFloat() * 6.0F) / 16.0F);
            double ☃xxx = (double)((float)☃x.func_177956_o() + 0.8125F);
            double ☃xxxx = (double)((float)☃x.func_177952_p() + (5.0F + field_77697_d.nextFloat() * 6.0F) / 16.0F);
            double ☃xxxxx = 0.0;
            double ☃xxxxxx = 0.0;
            double ☃xxxxxxx = 0.0;
            ☃.func_195594_a(Particles.field_197601_L, ☃xx, ☃xxx, ☃xxxx, 0.0, 0.0, 0.0);
         }

         ☃.func_184133_a(null, ☃x, SoundEvents.field_193781_bp, SoundCategory.BLOCKS, 1.0F, 1.0F);
         BlockPattern.PatternHelper ☃x = BlockEndPortalFrame.func_185661_e().func_177681_a(☃, ☃x);
         if (☃x != null) {
            BlockPos ☃xx = ☃x.func_181117_a().func_177982_a(-3, 0, -3);

            for(int ☃xxx = 0; ☃xxx < 3; ++☃xxx) {
               for(int ☃xxxx = 0; ☃xxxx < 3; ++☃xxxx) {
                  ☃.func_180501_a(☃xx.func_177982_a(☃xxx, 0, ☃xxxx), Blocks.field_150384_bq.func_176223_P(), 2);
               }
            }

            ☃.func_175669_a(1038, ☃xx.func_177982_a(1, 0, 1), 0);
         }

         return EnumActionResult.SUCCESS;
      }
   }

   @Override
   public ActionResult<ItemStack> func_77659_a(World var1, EntityPlayer var2, EnumHand var3) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      RayTraceResult ☃x = this.func_77621_a(☃, ☃, false);
      if (☃x != null && ☃x.field_72313_a == RayTraceResult.Type.BLOCK && ☃.func_180495_p(☃x.func_178782_a()).func_177230_c() == Blocks.field_150378_br) {
         return new ActionResult<>(EnumActionResult.PASS, ☃);
      } else {
         ☃.func_184598_c(☃);
         if (!☃.field_72995_K) {
            BlockPos ☃ = ((WorldServer)☃).func_72863_F().func_211268_a(☃, "Stronghold", new BlockPos(☃), 100, false);
            if (☃ != null) {
               EntityEnderEye ☃x = new EntityEnderEye(☃, ☃.field_70165_t, ☃.field_70163_u + (double)(☃.field_70131_O / 2.0F), ☃.field_70161_v);
               ☃x.func_180465_a(☃);
               ☃.func_72838_d(☃x);
               if (☃ instanceof EntityPlayerMP) {
                  CriteriaTriggers.field_192132_l.func_192239_a((EntityPlayerMP)☃, ☃);
               }

               ☃.func_184148_a(
                  null,
                  ☃.field_70165_t,
                  ☃.field_70163_u,
                  ☃.field_70161_v,
                  SoundEvents.field_187528_aR,
                  SoundCategory.NEUTRAL,
                  0.5F,
                  0.4F / (field_77697_d.nextFloat() * 0.4F + 0.8F)
               );
               ☃.func_180498_a(null, 1003, new BlockPos(☃), 0);
               if (!☃.field_71075_bZ.field_75098_d) {
                  ☃.func_190918_g(1);
               }

               ☃.func_71029_a(StatList.field_75929_E.func_199076_b(this));
               return new ActionResult<>(EnumActionResult.SUCCESS, ☃);
            }
         }

         return new ActionResult<>(EnumActionResult.SUCCESS, ☃);
      }
   }
}
