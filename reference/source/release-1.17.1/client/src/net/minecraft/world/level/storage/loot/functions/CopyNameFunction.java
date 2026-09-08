package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Nameable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class CopyNameFunction extends LootItemConditionalFunction {
   final CopyNameFunction.NameSource source;

   CopyNameFunction(LootItemCondition[] var1, CopyNameFunction.NameSource var2) {
      super(â˜ƒ);
      this.source = â˜ƒ;
   }

   @Override
   public LootItemFunctionType getType() {
      return LootItemFunctions.COPY_NAME;
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return ImmutableSet.of(this.source.param);
   }

   @Override
   public ItemStack run(ItemStack var1, LootContext var2) {
      Object â˜ƒx = â˜ƒ.getParamOrNull(this.source.param);
      if (â˜ƒx instanceof Nameable â˜ƒ && â˜ƒ.hasCustomName()) {
         â˜ƒ.setHoverName(â˜ƒ.getDisplayName());
      }

      return â˜ƒ;
   }

   public static LootItemConditionalFunction.Builder<?> copyName(CopyNameFunction.NameSource var0) {
      return simpleBuilder(var1 -> new CopyNameFunction(var1, â˜ƒ));
   }

   public static enum NameSource {
      THIS("this", LootContextParams.THIS_ENTITY),
      KILLER("killer", LootContextParams.KILLER_ENTITY),
      KILLER_PLAYER("killer_player", LootContextParams.LAST_DAMAGE_PLAYER),
      BLOCK_ENTITY("block_entity", LootContextParams.BLOCK_ENTITY);

      public final String name;
      public final LootContextParam<?> param;

      private NameSource(String var3, LootContextParam<?> var4) {
         this.name = â˜ƒ;
         this.param = â˜ƒ;
      }

      public static CopyNameFunction.NameSource getByName(String var0) {
         for(CopyNameFunction.NameSource â˜ƒ : values()) {
            if (â˜ƒ.name.equals(â˜ƒ)) {
               return â˜ƒ;
            }
         }

         throw new IllegalArgumentException("Invalid name source " + â˜ƒ);
      }
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<CopyNameFunction> {
      public void serialize(JsonObject var1, CopyNameFunction var2, JsonSerializationContext var3) {
         super.serialize(â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.addProperty("source", â˜ƒ.source.name);
      }

      public CopyNameFunction deserialize(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3) {
         CopyNameFunction.NameSource â˜ƒ = CopyNameFunction.NameSource.getByName(GsonHelper.getAsString(â˜ƒ, "source"));
         return new CopyNameFunction(â˜ƒ, â˜ƒ);
      }
   }
}
