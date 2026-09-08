package net.minecraft.item;

import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class ItemDebugStick extends Item {
   public ItemDebugStick(Item.Properties var1) {
      super(☃);
   }

   @Override
   public boolean func_195938_a(IBlockState var1, World var2, BlockPos var3, EntityPlayer var4) {
      if (!☃.field_72995_K) {
         this.func_195958_a(☃, ☃, ☃, ☃, false, ☃.func_184586_b(EnumHand.MAIN_HAND));
      }

      return false;
   }

   @Override
   public EnumActionResult func_195939_a(ItemUseContext var1) {
      EntityPlayer ☃ = ☃.func_195999_j();
      World ☃x = ☃.func_195991_k();
      if (!☃x.field_72995_K && ☃ != null) {
         BlockPos ☃xx = ☃.func_195995_a();
         this.func_195958_a(☃, ☃x.func_180495_p(☃xx), ☃x, ☃xx, true, ☃.func_195996_i());
      }

      return EnumActionResult.SUCCESS;
   }

   private void func_195958_a(EntityPlayer var1, IBlockState var2, IWorld var3, BlockPos var4, boolean var5, ItemStack var6) {
      if (☃.func_195070_dx()) {
         Block ☃ = ☃.func_177230_c();
         StateContainer<Block, IBlockState> ☃x = ☃.func_176194_O();
         Collection<IProperty<?>> ☃xx = ☃x.func_177623_d();
         String ☃xxx = IRegistry.field_212618_g.func_177774_c(☃).toString();
         if (☃xx.isEmpty()) {
            func_195956_a(☃, new TextComponentTranslation(this.func_77658_a() + ".empty", ☃xxx));
         } else {
            NBTTagCompound ☃ = ☃.func_190925_c("DebugProperty");
            String ☃x = ☃.func_74779_i(☃xxx);
            IProperty<?> ☃xx = ☃x.func_185920_a(☃x);
            if (☃) {
               if (☃xx == null) {
                  ☃xx = (IProperty)☃xx.iterator().next();
               }

               IBlockState ☃xxx = func_195960_a(☃, ☃xx, ☃.func_70093_af());
               ☃.func_180501_a(☃, ☃xxx, 18);
               func_195956_a(☃, new TextComponentTranslation(this.func_77658_a() + ".update", ☃xx.func_177701_a(), func_195957_a(☃xxx, ☃xx)));
            } else {
               ☃xx = func_195959_a(☃xx, ☃xx, ☃.func_70093_af());
               String ☃ = ☃xx.func_177701_a();
               ☃.func_74778_a(☃xxx, ☃);
               func_195956_a(☃, new TextComponentTranslation(this.func_77658_a() + ".select", ☃, func_195957_a(☃, ☃xx)));
            }
         }
      }
   }

   private static <T extends Comparable<T>> IBlockState func_195960_a(IBlockState var0, IProperty<T> var1, boolean var2) {
      return ☃.func_206870_a(☃, func_195959_a(☃.func_177700_c(), ☃.func_177229_b(☃), ☃));
   }

   private static <T> T func_195959_a(Iterable<T> var0, @Nullable T var1, boolean var2) {
      return (T)(☃ ? Util.func_195648_b(☃, ☃) : Util.func_195647_a(☃, ☃));
   }

   private static void func_195956_a(EntityPlayer var0, ITextComponent var1) {
      ((EntityPlayerMP)☃).func_195395_a(☃, ChatType.GAME_INFO);
   }

   private static <T extends Comparable<T>> String func_195957_a(IBlockState var0, IProperty<T> var1) {
      return ☃.func_177702_a(☃.func_177229_b(☃));
   }
}
