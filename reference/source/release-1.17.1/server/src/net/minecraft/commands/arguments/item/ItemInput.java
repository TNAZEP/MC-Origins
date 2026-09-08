package net.minecraft.commands.arguments.item;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemInput implements Predicate<ItemStack> {
   private static final Dynamic2CommandExceptionType ERROR_STACK_TOO_BIG = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TranslatableComponent("arguments.item.overstacked", var0, var1)
   );
   private final Item item;
   @Nullable
   private final CompoundTag tag;

   public ItemInput(Item var1, @Nullable CompoundTag var2) {
      this.item = â˜ƒ;
      this.tag = â˜ƒ;
   }

   public Item getItem() {
      return this.item;
   }

   public boolean test(ItemStack var1) {
      return â˜ƒ.is(this.item) && NbtUtils.compareNbt(this.tag, â˜ƒ.getTag(), true);
   }

   public ItemStack createItemStack(int var1, boolean var2) throws CommandSyntaxException {
      ItemStack â˜ƒ = new ItemStack(this.item, â˜ƒ);
      if (this.tag != null) {
         â˜ƒ.setTag(this.tag);
      }

      if (â˜ƒ && â˜ƒ > â˜ƒ.getMaxStackSize()) {
         throw ERROR_STACK_TOO_BIG.create(Registry.ITEM.getKey(this.item), â˜ƒ.getMaxStackSize());
      } else {
         return â˜ƒ;
      }
   }

   public String serialize() {
      StringBuilder â˜ƒ = new StringBuilder(Registry.ITEM.getId(this.item));
      if (this.tag != null) {
         â˜ƒ.append(this.tag);
      }

      return â˜ƒ.toString();
   }
}
