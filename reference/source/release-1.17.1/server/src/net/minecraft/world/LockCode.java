package net.minecraft.world;

import javax.annotation.concurrent.Immutable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

@Immutable
public class LockCode {
   public static final LockCode NO_LOCK = new LockCode("");
   public static final String TAG_LOCK = "Lock";
   private final String key;

   public LockCode(String var1) {
      this.key = â˜ƒ;
   }

   public boolean unlocksWith(ItemStack var1) {
      return this.key.isEmpty() || !â˜ƒ.isEmpty() && â˜ƒ.hasCustomHoverName() && this.key.equals(â˜ƒ.getHoverName().getString());
   }

   public void addToTag(CompoundTag var1) {
      if (!this.key.isEmpty()) {
         â˜ƒ.putString("Lock", this.key);
      }
   }

   public static LockCode fromTag(CompoundTag var0) {
      return â˜ƒ.contains("Lock", 8) ? new LockCode(â˜ƒ.getString("Lock")) : NO_LOCK;
   }
}
