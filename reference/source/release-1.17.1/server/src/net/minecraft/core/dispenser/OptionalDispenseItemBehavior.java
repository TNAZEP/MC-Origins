package net.minecraft.core.dispenser;

import net.minecraft.core.BlockSource;

public abstract class OptionalDispenseItemBehavior extends DefaultDispenseItemBehavior {
   private boolean success = true;

   public boolean isSuccess() {
      return this.success;
   }

   public void setSuccess(boolean var1) {
      this.success = â˜ƒ;
   }

   @Override
   protected void playSound(BlockSource var1) {
      â˜ƒ.getLevel().levelEvent(this.isSuccess() ? 1000 : 1001, â˜ƒ.getPos(), 0);
   }
}
