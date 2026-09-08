package net.minecraft.world.level.block;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class WoolCarpetBlock extends CarpetBlock {
   private final DyeColor color;

   protected WoolCarpetBlock(DyeColor var1, BlockBehaviour.Properties var2) {
      super(â˜ƒ);
      this.color = â˜ƒ;
   }

   public DyeColor getColor() {
      return this.color;
   }
}
