package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class AirItem extends Item {
   private final Block block;

   public AirItem(Block var1, Item.Properties var2) {
      super(â˜ƒ);
      this.block = â˜ƒ;
   }

   @Override
   public String getDescriptionId() {
      return this.block.getDescriptionId();
   }

   @Override
   public void appendHoverText(ItemStack var1, @Nullable Level var2, List<Component> var3, TooltipFlag var4) {
      super.appendHoverText(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.block.appendHoverText(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
