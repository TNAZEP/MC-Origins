package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.authlib.GameProfile;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class FillPlayerHead extends LootItemConditionalFunction {
   final LootContext.EntityTarget entityTarget;

   public FillPlayerHead(LootItemCondition[] var1, LootContext.EntityTarget var2) {
      super(â˜ƒ);
      this.entityTarget = â˜ƒ;
   }

   @Override
   public LootItemFunctionType getType() {
      return LootItemFunctions.FILL_PLAYER_HEAD;
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return ImmutableSet.of(this.entityTarget.getParam());
   }

   @Override
   public ItemStack run(ItemStack var1, LootContext var2) {
      if (â˜ƒ.is(Items.PLAYER_HEAD)) {
         Entity â˜ƒ = â˜ƒ.getParamOrNull(this.entityTarget.getParam());
         if (â˜ƒ instanceof Player) {
            GameProfile â˜ƒx = ((Player)â˜ƒ).getGameProfile();
            â˜ƒ.getOrCreateTag().put("SkullOwner", NbtUtils.writeGameProfile(new CompoundTag(), â˜ƒx));
         }
      }

      return â˜ƒ;
   }

   public static LootItemConditionalFunction.Builder<?> fillPlayerHead(LootContext.EntityTarget var0) {
      return simpleBuilder(var1 -> new FillPlayerHead(var1, â˜ƒ));
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<FillPlayerHead> {
      public void serialize(JsonObject var1, FillPlayerHead var2, JsonSerializationContext var3) {
         super.serialize(â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.add("entity", â˜ƒ.serialize(â˜ƒ.entityTarget));
      }

      public FillPlayerHead deserialize(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3) {
         LootContext.EntityTarget â˜ƒ = GsonHelper.getAsObject(â˜ƒ, "entity", â˜ƒ, LootContext.EntityTarget.class);
         return new FillPlayerHead(â˜ƒ, â˜ƒ);
      }
   }
}
