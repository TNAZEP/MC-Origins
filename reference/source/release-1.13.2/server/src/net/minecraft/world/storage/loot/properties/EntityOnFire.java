package net.minecraft.world.storage.loot.properties;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class EntityOnFire implements EntityProperty {
   private final boolean field_186659_a;

   public EntityOnFire(boolean var1) {
      this.field_186659_a = ☃;
   }

   @Override
   public boolean func_186657_a(Random var1, Entity var2) {
      return ☃.func_70027_ad() == this.field_186659_a;
   }

   public static class Serializer extends EntityProperty.Serializer<EntityOnFire> {
      protected Serializer() {
         super(new ResourceLocation("on_fire"), EntityOnFire.class);
      }

      public JsonElement func_186650_a(EntityOnFire var1, JsonSerializationContext var2) {
         return new JsonPrimitive(☃.field_186659_a);
      }

      public EntityOnFire func_186652_a(JsonElement var1, JsonDeserializationContext var2) {
         return new EntityOnFire(JsonUtils.func_151216_b(☃, "on_fire"));
      }
   }
}
