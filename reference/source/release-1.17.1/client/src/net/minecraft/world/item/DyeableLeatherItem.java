package net.minecraft.world.item;

import java.util.List;
import net.minecraft.nbt.CompoundTag;

public interface DyeableLeatherItem {
   String TAG_COLOR = "color";
   String TAG_DISPLAY = "display";
   int DEFAULT_LEATHER_COLOR = 10511680;

   default boolean hasCustomColor(ItemStack var1) {
      CompoundTag â˜ƒ = â˜ƒ.getTagElement("display");
      return â˜ƒ != null && â˜ƒ.contains("color", 99);
   }

   default int getColor(ItemStack var1) {
      CompoundTag â˜ƒ = â˜ƒ.getTagElement("display");
      return â˜ƒ != null && â˜ƒ.contains("color", 99) ? â˜ƒ.getInt("color") : 10511680;
   }

   default void clearColor(ItemStack var1) {
      CompoundTag â˜ƒ = â˜ƒ.getTagElement("display");
      if (â˜ƒ != null && â˜ƒ.contains("color")) {
         â˜ƒ.remove("color");
      }
   }

   default void setColor(ItemStack var1, int var2) {
      â˜ƒ.getOrCreateTagElement("display").putInt("color", â˜ƒ);
   }

   static ItemStack dyeArmor(ItemStack var0, List<DyeItem> var1) {
      ItemStack â˜ƒ = ItemStack.EMPTY;
      int[] â˜ƒx = new int[3];
      int â˜ƒxx = 0;
      int â˜ƒxxx = 0;
      DyeableLeatherItem â˜ƒxxxx = null;
      Item â˜ƒxxxxx = â˜ƒ.getItem();
      if (â˜ƒxxxxx instanceof DyeableLeatherItem) {
         â˜ƒxxxx = (DyeableLeatherItem)â˜ƒxxxxx;
         â˜ƒ = â˜ƒ.copy();
         â˜ƒ.setCount(1);
         if (â˜ƒxxxx.hasCustomColor(â˜ƒ)) {
            int â˜ƒxxxxxx = â˜ƒxxxx.getColor(â˜ƒ);
            float â˜ƒxxxxxxx = (float)(â˜ƒxxxxxx >> 16 & 0xFF) / 255.0F;
            float â˜ƒxxxxxxxx = (float)(â˜ƒxxxxxx >> 8 & 0xFF) / 255.0F;
            float â˜ƒxxxxxxxxx = (float)(â˜ƒxxxxxx & 0xFF) / 255.0F;
            â˜ƒxx = (int)((float)â˜ƒxx + Math.max(â˜ƒxxxxxxx, Math.max(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx)) * 255.0F);
            â˜ƒx[0] = (int)((float)â˜ƒx[0] + â˜ƒxxxxxxx * 255.0F);
            â˜ƒx[1] = (int)((float)â˜ƒx[1] + â˜ƒxxxxxxxx * 255.0F);
            â˜ƒx[2] = (int)((float)â˜ƒx[2] + â˜ƒxxxxxxxxx * 255.0F);
            ++â˜ƒxxx;
         }

         for(DyeItem â˜ƒxxxxxx : â˜ƒ) {
            float[] â˜ƒxxxxxxx = â˜ƒxxxxxx.getDyeColor().getTextureDiffuseColors();
            int â˜ƒxxxxxxxx = (int)(â˜ƒxxxxxxx[0] * 255.0F);
            int â˜ƒxxxxxxxxx = (int)(â˜ƒxxxxxxx[1] * 255.0F);
            int â˜ƒxxxxxxxxxx = (int)(â˜ƒxxxxxxx[2] * 255.0F);
            â˜ƒxx += Math.max(â˜ƒxxxxxxxx, Math.max(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx));
            â˜ƒx[0] += â˜ƒxxxxxxxx;
            â˜ƒx[1] += â˜ƒxxxxxxxxx;
            â˜ƒx[2] += â˜ƒxxxxxxxxxx;
            ++â˜ƒxxx;
         }
      }

      if (â˜ƒxxxx == null) {
         return ItemStack.EMPTY;
      } else {
         int â˜ƒ = â˜ƒx[0] / â˜ƒxxx;
         int â˜ƒx = â˜ƒx[1] / â˜ƒxxx;
         int â˜ƒxx = â˜ƒx[2] / â˜ƒxxx;
         float â˜ƒxxx = (float)â˜ƒxx / (float)â˜ƒxxx;
         float â˜ƒxxxx = (float)Math.max(â˜ƒ, Math.max(â˜ƒx, â˜ƒxx));
         â˜ƒ = (int)((float)â˜ƒ * â˜ƒxxx / â˜ƒxxxx);
         â˜ƒx = (int)((float)â˜ƒx * â˜ƒxxx / â˜ƒxxxx);
         â˜ƒxx = (int)((float)â˜ƒxx * â˜ƒxxx / â˜ƒxxxx);
         int var26 = (â˜ƒ << 8) + â˜ƒx;
         var26 = (var26 << 8) + â˜ƒxx;
         â˜ƒxxxx.setColor(â˜ƒ, var26);
         return â˜ƒ;
      }
   }
}
