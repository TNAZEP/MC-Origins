package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagParser;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class NbtPredicate {
   public static final NbtPredicate ANY = new NbtPredicate(null);
   @Nullable
   private final CompoundTag tag;

   public NbtPredicate(@Nullable CompoundTag var1) {
      this.tag = â˜ƒ;
   }

   public boolean matches(ItemStack var1) {
      return this == ANY ? true : this.matches(â˜ƒ.getTag());
   }

   public boolean matches(Entity var1) {
      return this == ANY ? true : this.matches(getEntityTagToCompare(â˜ƒ));
   }

   public boolean matches(@Nullable Tag var1) {
      if (â˜ƒ == null) {
         return this == ANY;
      } else {
         return this.tag == null || NbtUtils.compareNbt(this.tag, â˜ƒ, true);
      }
   }

   public JsonElement serializeToJson() {
      return (JsonElement)(this != ANY && this.tag != null ? new JsonPrimitive(this.tag.toString()) : JsonNull.INSTANCE);
   }

   public static NbtPredicate fromJson(@Nullable JsonElement var0) {
      if (â˜ƒ != null && !â˜ƒ.isJsonNull()) {
         CompoundTag â˜ƒ;
         try {
            â˜ƒ = TagParser.parseTag(GsonHelper.convertToString(â˜ƒ, "nbt"));
         } catch (CommandSyntaxException var3) {
            throw new JsonSyntaxException("Invalid nbt tag: " + var3.getMessage());
         }

         return new NbtPredicate(â˜ƒ);
      } else {
         return ANY;
      }
   }

   public static CompoundTag getEntityTagToCompare(Entity var0) {
      CompoundTag â˜ƒ = â˜ƒ.saveWithoutId(new CompoundTag());
      if (â˜ƒ instanceof Player) {
         ItemStack â˜ƒx = ((Player)â˜ƒ).getInventory().getSelected();
         if (!â˜ƒx.isEmpty()) {
            â˜ƒ.put("SelectedItem", â˜ƒx.save(new CompoundTag()));
         }
      }

      return â˜ƒ;
   }
}
