package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SetLoreFunction extends LootItemConditionalFunction {
   final boolean replace;
   final List<Component> lore;
   @Nullable
   final LootContext.EntityTarget resolutionContext;

   public SetLoreFunction(LootItemCondition[] var1, boolean var2, List<Component> var3, @Nullable LootContext.EntityTarget var4) {
      super(â˜ƒ);
      this.replace = â˜ƒ;
      this.lore = ImmutableList.copyOf(â˜ƒ);
      this.resolutionContext = â˜ƒ;
   }

   @Override
   public LootItemFunctionType getType() {
      return LootItemFunctions.SET_LORE;
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return this.resolutionContext != null ? ImmutableSet.of(this.resolutionContext.getParam()) : ImmutableSet.of();
   }

   @Override
   public ItemStack run(ItemStack var1, LootContext var2) {
      ListTag â˜ƒ = this.getLoreTag(â˜ƒ, !this.lore.isEmpty());
      if (â˜ƒ != null) {
         if (this.replace) {
            â˜ƒ.clear();
         }

         UnaryOperator<Component> â˜ƒx = SetNameFunction.createResolver(â˜ƒ, this.resolutionContext);
         this.lore.stream().map(â˜ƒx).map(Component.Serializer::toJson).map(StringTag::valueOf).forEach(â˜ƒ::add);
      }

      return â˜ƒ;
   }

   @Nullable
   private ListTag getLoreTag(ItemStack var1, boolean var2) {
      CompoundTag â˜ƒ;
      if (â˜ƒ.hasTag()) {
         â˜ƒ = â˜ƒ.getTag();
      } else {
         if (!â˜ƒ) {
            return null;
         }

         â˜ƒ = new CompoundTag();
         â˜ƒ.setTag(â˜ƒ);
      }

      CompoundTag â˜ƒ;
      if (â˜ƒ.contains("display", 10)) {
         â˜ƒ = â˜ƒ.getCompound("display");
      } else {
         if (!â˜ƒ) {
            return null;
         }

         â˜ƒ = new CompoundTag();
         â˜ƒ.put("display", â˜ƒ);
      }

      if (â˜ƒ.contains("Lore", 9)) {
         return â˜ƒ.getList("Lore", 8);
      } else if (â˜ƒ) {
         ListTag â˜ƒ = new ListTag();
         â˜ƒ.put("Lore", â˜ƒ);
         return â˜ƒ;
      } else {
         return null;
      }
   }

   public static SetLoreFunction.Builder setLore() {
      return new SetLoreFunction.Builder();
   }

   public static class Builder extends LootItemConditionalFunction.Builder<SetLoreFunction.Builder> {
      private boolean replace;
      private LootContext.EntityTarget resolutionContext;
      private final List<Component> lore = Lists.<Component>newArrayList();

      public SetLoreFunction.Builder setReplace(boolean var1) {
         this.replace = â˜ƒ;
         return this;
      }

      public SetLoreFunction.Builder setResolutionContext(LootContext.EntityTarget var1) {
         this.resolutionContext = â˜ƒ;
         return this;
      }

      public SetLoreFunction.Builder addLine(Component var1) {
         this.lore.add(â˜ƒ);
         return this;
      }

      protected SetLoreFunction.Builder getThis() {
         return this;
      }

      @Override
      public LootItemFunction build() {
         return new SetLoreFunction(this.getConditions(), this.replace, this.lore, this.resolutionContext);
      }
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<SetLoreFunction> {
      public void serialize(JsonObject var1, SetLoreFunction var2, JsonSerializationContext var3) {
         super.serialize(â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.addProperty("replace", â˜ƒ.replace);
         JsonArray â˜ƒ = new JsonArray();

         for(Component â˜ƒx : â˜ƒ.lore) {
            â˜ƒ.add(Component.Serializer.toJsonTree(â˜ƒx));
         }

         â˜ƒ.add("lore", â˜ƒ);
         if (â˜ƒ.resolutionContext != null) {
            â˜ƒ.add("entity", â˜ƒ.serialize(â˜ƒ.resolutionContext));
         }
      }

      public SetLoreFunction deserialize(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3) {
         boolean â˜ƒ = GsonHelper.getAsBoolean(â˜ƒ, "replace", false);
         List<Component> â˜ƒx = (List)Streams.stream(GsonHelper.getAsJsonArray(â˜ƒ, "lore"))
            .map(Component.Serializer::fromJson)
            .collect(ImmutableList.toImmutableList());
         LootContext.EntityTarget â˜ƒxx = GsonHelper.getAsObject(â˜ƒ, "entity", null, â˜ƒ, LootContext.EntityTarget.class);
         return new SetLoreFunction(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx);
      }
   }
}
