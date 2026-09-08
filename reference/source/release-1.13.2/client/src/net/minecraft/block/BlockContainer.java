package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.INameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BlockContainer extends Block implements ITileEntityProvider {
   private static final Logger field_196284_a = LogManager.getLogger();

   protected BlockContainer(Block.Properties var1) {
      super(☃);
   }

   @Override
   public EnumBlockRenderType func_149645_b(IBlockState var1) {
      return EnumBlockRenderType.INVISIBLE;
   }

   @Override
   public void func_196243_a(IBlockState var1, World var2, BlockPos var3, IBlockState var4, boolean var5) {
      if (☃.func_177230_c() != ☃.func_177230_c()) {
         super.func_196243_a(☃, ☃, ☃, ☃, ☃);
         ☃.func_175713_t(☃);
      }
   }

   @Override
   public void func_180657_a(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, @Nullable TileEntity var5, ItemStack var6) {
      if (☃ instanceof INameable && ((INameable)☃).func_145818_k_()) {
         ☃.func_71029_a(StatList.field_188065_ae.func_199076_b(this));
         ☃.func_71020_j(0.005F);
         if (☃.field_72995_K) {
            field_196284_a.debug("Never going to hit this!");
            return;
         }

         int ☃ = EnchantmentHelper.func_77506_a(Enchantments.field_185308_t, ☃);
         Item ☃x = this.func_199769_a(☃, ☃, ☃, ☃).func_199767_j();
         if (☃x == Items.field_190931_a) {
            return;
         }

         ItemStack ☃ = new ItemStack(☃x, this.func_196264_a(☃, ☃.field_73012_v));
         ☃.func_200302_a(((INameable)☃).func_200201_e());
         func_180635_a(☃, ☃, ☃);
      } else {
         super.func_180657_a(☃, ☃, ☃, ☃, null, ☃);
      }
   }

   @Override
   public boolean func_189539_a(IBlockState var1, World var2, BlockPos var3, int var4, int var5) {
      super.func_189539_a(☃, ☃, ☃, ☃, ☃);
      TileEntity ☃ = ☃.func_175625_s(☃);
      return ☃ == null ? false : ☃.func_145842_c(☃, ☃);
   }
}
