package net.minecraft.advancements.critereon;

import com.google.common.base.Joiner;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.EntityType;

public abstract class EntityTypePredicate {
   public static final EntityTypePredicate ANY = new EntityTypePredicate() {
      @Override
      public boolean matches(EntityType<?> var1) {
         return true;
      }

      @Override
      public JsonElement serializeToJson() {
         return JsonNull.INSTANCE;
      }
   };
   private static final Joiner COMMA_JOINER = Joiner.on(", ");

   public abstract boolean matches(EntityType<?> var1);

   public abstract JsonElement serializeToJson();

   public static EntityTypePredicate fromJson(@Nullable JsonElement var0) {
      if (â˜ƒ != null && !â˜ƒ.isJsonNull()) {
         String â˜ƒ = GsonHelper.convertToString(â˜ƒ, "type");
         if (â˜ƒ.startsWith("#")) {
            ResourceLocation â˜ƒx = new ResourceLocation(â˜ƒ.substring(1));
            return new EntityTypePredicate.TagPredicate(
               SerializationTags.getInstance()
                  .getTagOrThrow(Registry.ENTITY_TYPE_REGISTRY, â˜ƒx, var0x -> new JsonSyntaxException("Unknown entity tag '" + var0x + "'"))
            );
         } else {
            ResourceLocation â˜ƒ = new ResourceLocation(â˜ƒ);
            EntityType<?> â˜ƒx = (EntityType)Registry.ENTITY_TYPE
               .getOptional(â˜ƒ)
               .orElseThrow(
                  () -> new JsonSyntaxException("Unknown entity type '" + â˜ƒ + "', valid types are: " + COMMA_JOINER.join(Registry.ENTITY_TYPE.keySet()))
               );
            return new EntityTypePredicate.TypePredicate(â˜ƒx);
         }
      } else {
         return ANY;
      }
   }

   public static EntityTypePredicate of(EntityType<?> var0) {
      return new EntityTypePredicate.TypePredicate(â˜ƒ);
   }

   public static EntityTypePredicate of(Tag<EntityType<?>> var0) {
      return new EntityTypePredicate.TagPredicate(â˜ƒ);
   }

   static class TagPredicate extends EntityTypePredicate {
      private final Tag<EntityType<?>> tag;

      public TagPredicate(Tag<EntityType<?>> var1) {
         this.tag = â˜ƒ;
      }

      @Override
      public boolean matches(EntityType<?> var1) {
         return â˜ƒ.is(this.tag);
      }

      @Override
      public JsonElement serializeToJson() {
         return new JsonPrimitive(
            "#"
               + SerializationTags.getInstance()
                  .getIdOrThrow(Registry.ENTITY_TYPE_REGISTRY, this.tag, () -> new IllegalStateException("Unknown entity type tag"))
         );
      }
   }

   static class TypePredicate extends EntityTypePredicate {
      private final EntityType<?> type;

      public TypePredicate(EntityType<?> var1) {
         this.type = â˜ƒ;
      }

      @Override
      public boolean matches(EntityType<?> var1) {
         return this.type == â˜ƒ;
      }

      @Override
      public JsonElement serializeToJson() {
         return new JsonPrimitive(Registry.ENTITY_TYPE.getKey(this.type).toString());
      }
   }
}
