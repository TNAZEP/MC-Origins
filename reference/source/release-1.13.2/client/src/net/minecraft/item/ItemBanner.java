package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAbstractBanner;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import org.apache.commons.lang3.Validate;

public class ItemBanner extends ItemWallOrFloor {
   public ItemBanner(Block var1, Block var2, Item.Properties var3) {
      super(☃, ☃, ☃);
      Validate.isInstanceOf(BlockAbstractBanner.class, ☃);
      Validate.isInstanceOf(BlockAbstractBanner.class, ☃);
   }

   public static void func_185054_a(ItemStack var0, List<ITextComponent> var1) {
      NBTTagCompound ☃ = ☃.func_179543_a("BlockEntityTag");
      if (☃ != null && ☃.func_74764_b("Patterns")) {
         NBTTagList ☃x = ☃.func_150295_c("Patterns", 10);

         for(int ☃xx = 0; ☃xx < ☃x.size() && ☃xx < 6; ++☃xx) {
            NBTTagCompound ☃xxx = ☃x.func_150305_b(☃xx);
            EnumDyeColor ☃xxxx = EnumDyeColor.func_196056_a(☃xxx.func_74762_e("Color"));
            BannerPattern ☃xxxxx = BannerPattern.func_190994_a(☃xxx.func_74779_i("Pattern"));
            if (☃xxxxx != null) {
               ☃.add(
                  new TextComponentTranslation("block.minecraft.banner." + ☃xxxxx.func_190997_a() + '.' + ☃xxxx.func_176762_d())
                     .func_211708_a(TextFormatting.GRAY)
               );
            }
         }
      }
   }

   public EnumDyeColor func_195948_b() {
      return ((BlockAbstractBanner)this.func_179223_d()).func_196285_M_();
   }

   @Override
   public void func_77624_a(ItemStack var1, @Nullable World var2, List<ITextComponent> var3, ITooltipFlag var4) {
      func_185054_a(☃, ☃);
   }
}
