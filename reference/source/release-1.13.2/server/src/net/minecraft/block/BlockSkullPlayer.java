package net.minecraft.block;

import com.mojang.authlib.GameProfile;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;

public class BlockSkullPlayer extends BlockSkull {
   protected BlockSkullPlayer(Block.Properties var1) {
      super(BlockSkull.Types.PLAYER, ☃);
   }

   @Override
   public void func_180633_a(World var1, BlockPos var2, IBlockState var3, @Nullable EntityLivingBase var4, ItemStack var5) {
      super.func_180633_a(☃, ☃, ☃, ☃, ☃);
      TileEntity ☃ = ☃.func_175625_s(☃);
      if (☃ instanceof TileEntitySkull) {
         TileEntitySkull ☃x = (TileEntitySkull)☃;
         GameProfile ☃xx = null;
         if (☃.func_77942_o()) {
            NBTTagCompound ☃xxx = ☃.func_77978_p();
            if (☃xxx.func_150297_b("SkullOwner", 10)) {
               ☃xx = NBTUtil.func_152459_a(☃xxx.func_74775_l("SkullOwner"));
            } else if (☃xxx.func_150297_b("SkullOwner", 8) && !StringUtils.isBlank(☃xxx.func_74779_i("SkullOwner"))) {
               ☃xx = new GameProfile(null, ☃xxx.func_74779_i("SkullOwner"));
            }
         }

         ☃x.func_195485_a(☃xx);
      }
   }
}
