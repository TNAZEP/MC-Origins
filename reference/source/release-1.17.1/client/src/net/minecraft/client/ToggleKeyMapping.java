package net.minecraft.client;

import com.mojang.blaze3d.platform.InputConstants;
import java.util.function.BooleanSupplier;

public class ToggleKeyMapping extends KeyMapping {
   private final BooleanSupplier needsToggle;

   public ToggleKeyMapping(String var1, int var2, String var3, BooleanSupplier var4) {
      super(â˜ƒ, InputConstants.Type.KEYSYM, â˜ƒ, â˜ƒ);
      this.needsToggle = â˜ƒ;
   }

   @Override
   public void setDown(boolean var1) {
      if (this.needsToggle.getAsBoolean()) {
         if (â˜ƒ) {
            super.setDown(!this.isDown());
         }
      } else {
         super.setDown(â˜ƒ);
      }
   }
}
