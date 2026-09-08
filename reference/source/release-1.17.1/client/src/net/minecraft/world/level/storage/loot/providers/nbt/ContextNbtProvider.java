package net.minecraft.world.level.storage.loot.providers.nbt;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.GsonAdapterFactory;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class ContextNbtProvider implements NbtProvider {
   private static final String BLOCK_ENTITY_ID = "block_entity";
   private static final ContextNbtProvider.Getter BLOCK_ENTITY_PROVIDER = new ContextNbtProvider.Getter() {
      @Override
      public Tag get(LootContext var1) {
         BlockEntity â˜ƒ = â˜ƒ.getParamOrNull(LootContextParams.BLOCK_ENTITY);
         return â˜ƒ != null ? â˜ƒ.save(new CompoundTag()) : null;
      }

      @Override
      public String getId() {
         return "block_entity";
      }

      @Override
      public Set<LootContextParam<?>> getReferencedContextParams() {
         return ImmutableSet.of(LootContextParams.BLOCK_ENTITY);
      }
   };
   public static final ContextNbtProvider BLOCK_ENTITY = new ContextNbtProvider(BLOCK_ENTITY_PROVIDER);
   final ContextNbtProvider.Getter getter;

   private static ContextNbtProvider.Getter forEntity(final LootContext.EntityTarget var0) {
      return new ContextNbtProvider.Getter() {
         @Nullable
         @Override
         public Tag get(LootContext var1) {
            Entity â˜ƒ = â˜ƒ.getParamOrNull(â˜ƒ.getParam());
            return â˜ƒ != null ? NbtPredicate.getEntityTagToCompare(â˜ƒ) : null;
         }

         @Override
         public String getId() {
            return â˜ƒ.name();
         }

         @Override
         public Set<LootContextParam<?>> getReferencedContextParams() {
            return ImmutableSet.of(â˜ƒ.getParam());
         }
      };
   }

   private ContextNbtProvider(ContextNbtProvider.Getter var1) {
      this.getter = â˜ƒ;
   }

   @Override
   public LootNbtProviderType getType() {
      return NbtProviders.CONTEXT;
   }

   @Nullable
   @Override
   public Tag get(LootContext var1) {
      return this.getter.get(â˜ƒ);
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return this.getter.getReferencedContextParams();
   }

   public static NbtProvider forContextEntity(LootContext.EntityTarget var0) {
      return new ContextNbtProvider(forEntity(â˜ƒ));
   }

   static ContextNbtProvider createFromContext(String var0) {
      if (â˜ƒ.equals("block_entity")) {
         return new ContextNbtProvider(BLOCK_ENTITY_PROVIDER);
      } else {
         LootContext.EntityTarget â˜ƒ = LootContext.EntityTarget.getByName(â˜ƒ);
         return new ContextNbtProvider(forEntity(â˜ƒ));
      }
   }

   interface Getter {
      @Nullable
      Tag get(LootContext var1);

      String getId();

      Set<LootContextParam<?>> getReferencedContextParams();
   }

   public static class InlineSerializer implements GsonAdapterFactory.InlineSerializer<ContextNbtProvider> {
      public JsonElement serialize(ContextNbtProvider var1, JsonSerializationContext var2) {
         return new JsonPrimitive(â˜ƒ.getter.getId());
      }

      public ContextNbtProvider deserialize(JsonElement var1, JsonDeserializationContext var2) {
         String â˜ƒ = â˜ƒ.getAsString();
         return ContextNbtProvider.createFromContext(â˜ƒ);
      }
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<ContextNbtProvider> {
      public void serialize(JsonObject var1, ContextNbtProvider var2, JsonSerializationContext var3) {
         â˜ƒ.addProperty("target", â˜ƒ.getter.getId());
      }

      public ContextNbtProvider deserialize(JsonObject var1, JsonDeserializationContext var2) {
         String â˜ƒ = GsonHelper.getAsString(â˜ƒ, "target");
         return ContextNbtProvider.createFromContext(â˜ƒ);
      }
   }
}
