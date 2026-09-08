package net.minecraft.world.item;

import net.minecraft.world.level.block.Block;

public class ItemNameBlockItem extends BlockItem {
   public ItemNameBlockItem(Block var1, Item.Properties var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public String getDescriptionId() {
      return this.getOrCreateDescriptionId();
   }
}
