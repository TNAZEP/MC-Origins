package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class CopyBlockState extends LootItemConditionalFunction {
   final Block block;
   final Set<Property<?>> properties;

   CopyBlockState(LootItemCondition[] var1, Block var2, Set<Property<?>> var3) {
      super(â˜ƒ);
      this.block = â˜ƒ;
      this.properties = â˜ƒ;
   }

   @Override
   public LootItemFunctionType getType() {
      return LootItemFunctions.COPY_STATE;
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return ImmutableSet.of(LootContextParams.BLOCK_STATE);
   }

   @Override
   protected ItemStack run(ItemStack var1, LootContext var2) {
      BlockState â˜ƒ = â˜ƒ.getParamOrNull(LootContextParams.BLOCK_STATE);
      if (â˜ƒ != null) {
         CompoundTag â˜ƒxx = â˜ƒ.getOrCreateTag();
         CompoundTag â˜ƒx;
         if (â˜ƒxx.contains("BlockStateTag", 10)) {
            â˜ƒx = â˜ƒxx.getCompound("BlockStateTag");
         } else {
            â˜ƒx = new CompoundTag();
            â˜ƒxx.put("BlockStateTag", â˜ƒx);
         }

         this.properties.stream().filter(â˜ƒ::hasProperty).forEach(var2x -> â˜ƒ.putString(var2x.getName(), serialize(â˜ƒ, var2x)));
      }

      return â˜ƒ;
   }

   public static CopyBlockState.Builder copyState(Block var0) {
      return new CopyBlockState.Builder(â˜ƒ);
   }

   private static <T extends Comparable<T>> String serialize(BlockState var0, Property<T> var1) {
      T â˜ƒ = â˜ƒ.getValue(â˜ƒ);
      return â˜ƒ.getName(â˜ƒ);
   }

   public static class Builder extends LootItemConditionalFunction.Builder<CopyBlockState.Builder> {
      private final Block block;
      private final Set<Property<?>> properties = Sets.<Property<?>>newHashSet();

      Builder(Block var1) {
         this.block = â˜ƒ;
      }

      public CopyBlockState.Builder copy(Property<?> var1) {
         if (!this.block.getStateDefinition().getProperties().contains(â˜ƒ)) {
            throw new IllegalStateException("Property " + â˜ƒ + " is not present on block " + this.block);
         } else {
            this.properties.add(â˜ƒ);
            return this;
         }
      }

      protected CopyBlockState.Builder getThis() {
         return this;
      }

      @Override
      public LootItemFunction build() {
         return new CopyBlockState(this.getConditions(), this.block, this.properties);
      }
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<CopyBlockState> {
      public void serialize(JsonObject var1, CopyBlockState var2, JsonSerializationContext var3) {
         super.serialize(â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.addProperty("block", Registry.BLOCK.getKey(â˜ƒ.block).toString());
         JsonArray â˜ƒ = new JsonArray();
         â˜ƒ.properties.forEach(var1x -> â˜ƒ.add(var1x.getName()));
         â˜ƒ.add("properties", â˜ƒ);
      }

      public CopyBlockState deserialize(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3) {
         ResourceLocation â˜ƒ = new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "block"));
         Block â˜ƒx = (Block)Registry.BLOCK.getOptional(â˜ƒ).orElseThrow(() -> new IllegalArgumentException("Can't find block " + â˜ƒ));
         StateDefinition<Block, BlockState> â˜ƒxx = â˜ƒx.getStateDefinition();
         Set<Property<?>> â˜ƒxxx = Sets.<Property<?>>newHashSet();
         JsonArray â˜ƒxxxx = GsonHelper.getAsJsonArray(â˜ƒ, "properties", null);
         if (â˜ƒxxxx != null) {
            â˜ƒxxxx.forEach(var2x -> â˜ƒ.add(â˜ƒ.getProperty(GsonHelper.convertToString(var2x, "property"))));
         }

         return new CopyBlockState(â˜ƒ, â˜ƒx, â˜ƒxxx);
      }
   }
}
