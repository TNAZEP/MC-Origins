package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAbstractBanner;
import org.apache.commons.lang3.Validate;

public class ItemBanner extends ItemWallOrFloor {
   public ItemBanner(Block var1, Block var2, Item.Properties var3) {
      super(☃, ☃, ☃);
      Validate.isInstanceOf(BlockAbstractBanner.class, ☃);
      Validate.isInstanceOf(BlockAbstractBanner.class, ☃);
   }

   public EnumDyeColor func_195948_b() {
      return ((BlockAbstractBanner)this.func_179223_d()).func_196285_M_();
   }
}
