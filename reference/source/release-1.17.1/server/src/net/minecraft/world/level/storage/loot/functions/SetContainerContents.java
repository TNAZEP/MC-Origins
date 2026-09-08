package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Arrays;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntry;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SetContainerContents extends LootItemConditionalFunction {
   final List<LootPoolEntryContainer> entries;

   SetContainerContents(LootItemCondition[] var1, List<LootPoolEntryContainer> var2) {
      super(â˜ƒ);
      this.entries = ImmutableList.copyOf(â˜ƒ);
   }

   @Override
   public LootItemFunctionType getType() {
      return LootItemFunctions.SET_CONTENTS;
   }

   @Override
   public ItemStack run(ItemStack var1, LootContext var2) {
      if (â˜ƒ.isEmpty()) {
         return â˜ƒ;
      } else {
         NonNullList<ItemStack> â˜ƒ = NonNullList.create();
         this.entries.forEach(var2x -> var2x.expand(â˜ƒ, var2xx -> var2xx.createItemStack(LootTable.createStackSplitter(â˜ƒ::add), â˜ƒ)));
         CompoundTag â˜ƒx = new CompoundTag();
         ContainerHelper.saveAllItems(â˜ƒx, â˜ƒ);
         CompoundTag â˜ƒxx = â˜ƒ.getOrCreateTag();
         â˜ƒxx.put("BlockEntityTag", â˜ƒx.merge(â˜ƒxx.getCompound("BlockEntityTag")));
         return â˜ƒ;
      }
   }

   @Override
   public void validate(ValidationContext var1) {
      super.validate(â˜ƒ);

      for(int â˜ƒ = 0; â˜ƒ < this.entries.size(); ++â˜ƒ) {
         ((LootPoolEntryContainer)this.entries.get(â˜ƒ)).validate(â˜ƒ.forChild(".entry[" + â˜ƒ + "]"));
      }
   }

   public static SetContainerContents.Builder setContents() {
      return new SetContainerContents.Builder();
   }

   public static class Builder extends LootItemConditionalFunction.Builder<SetContainerContents.Builder> {
      private final List<LootPoolEntryContainer> entries = Lists.<LootPoolEntryContainer>newArrayList();

      protected SetContainerContents.Builder getThis() {
         return this;
      }

      public SetContainerContents.Builder withEntry(LootPoolEntryContainer.Builder<?> var1) {
         this.entries.add(â˜ƒ.build());
         return this;
      }

      @Override
      public LootItemFunction build() {
         return new SetContainerContents(this.getConditions(), this.entries);
      }
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<SetContainerContents> {
      public void serialize(JsonObject var1, SetContainerContents var2, JsonSerializationContext var3) {
         super.serialize(â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.add("entries", â˜ƒ.serialize(â˜ƒ.entries));
      }

      public SetContainerContents deserialize(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3) {
         LootPoolEntryContainer[] â˜ƒ = (LootPoolEntryContainer[])GsonHelper.getAsObject(â˜ƒ, "entries", â˜ƒ, LootPoolEntryContainer[].class);
         return new SetContainerContents(â˜ƒ, Arrays.asList(â˜ƒ));
      }
   }
}
