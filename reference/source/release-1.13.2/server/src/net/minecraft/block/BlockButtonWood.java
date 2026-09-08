package net.minecraft.block;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

public class BlockButtonWood extends BlockButton {
   protected BlockButtonWood(Block.Properties var1) {
      super(true, ☃);
   }

   @Override
   protected SoundEvent func_196369_b(boolean var1) {
      return ☃ ? SoundEvents.field_187885_gS : SoundEvents.field_187883_gR;
   }
}
