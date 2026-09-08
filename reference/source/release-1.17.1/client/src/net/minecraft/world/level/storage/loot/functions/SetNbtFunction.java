package net.minecraft.world.level.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SetNbtFunction extends LootItemConditionalFunction {
   final CompoundTag tag;

   SetNbtFunction(LootItemCondition[] var1, CompoundTag var2) {
      super(â˜ƒ);
      this.tag = â˜ƒ;
   }

   @Override
   public LootItemFunctionType getType() {
      return LootItemFunctions.SET_NBT;
   }

   @Override
   public ItemStack run(ItemStack var1, LootContext var2) {
      â˜ƒ.getOrCreateTag().merge(this.tag);
      return â˜ƒ;
   }

   public static LootItemConditionalFunction.Builder<?> setTag(CompoundTag var0) {
      return simpleBuilder(var1 -> new SetNbtFunction(var1, â˜ƒ));
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<SetNbtFunction> {
      public void serialize(JsonObject var1, SetNbtFunction var2, JsonSerializationContext var3) {
         super.serialize(â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.addProperty("tag", â˜ƒ.tag.toString());
      }

      public SetNbtFunction deserialize(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3) {
         try {
            CompoundTag â˜ƒ = TagParser.parseTag(GsonHelper.getAsString(â˜ƒ, "tag"));
            return new SetNbtFunction(â˜ƒ, â˜ƒ);
         } catch (CommandSyntaxException var5) {
            throw new JsonSyntaxException(var5.getMessage());
         }
      }
   }
}
