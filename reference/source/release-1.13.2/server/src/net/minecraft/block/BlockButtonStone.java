package net.minecraft.block;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

public class BlockButtonStone extends BlockButton {
   protected BlockButtonStone(Block.Properties var1) {
      super(false, ☃);
   }

   @Override
   protected SoundEvent func_196369_b(boolean var1) {
      return ☃ ? SoundEvents.field_187839_fV : SoundEvents.field_187837_fU;
   }
}
