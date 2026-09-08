package net.minecraft.world.storage.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.properties.EntityProperty;
import net.minecraft.world.storage.loot.properties.EntityPropertyManager;

public class EntityHasProperty implements LootCondition {
   private final EntityProperty[] field_186623_a;
   private final LootContext.EntityTarget field_186624_b;

   public EntityHasProperty(EntityProperty[] var1, LootContext.EntityTarget var2) {
      this.field_186623_a = ☃;
      this.field_186624_b = ☃;
   }

   @Override
   public boolean func_186618_a(Random var1, LootContext var2) {
      Entity ☃ = ☃.func_186494_a(this.field_186624_b);
      if (☃ == null) {
         return false;
      } else {
         for(EntityProperty ☃ : this.field_186623_a) {
            if (!☃.func_186657_a(☃, ☃)) {
               return false;
            }
         }

         return true;
      }
   }

   public static class Serializer extends LootCondition.Serializer<EntityHasProperty> {
      protected Serializer() {
         super(new ResourceLocation("entity_properties"), EntityHasProperty.class);
      }

      public void func_186605_a(JsonObject var1, EntityHasProperty var2, JsonSerializationContext var3) {
         JsonObject ☃ = new JsonObject();

         for(EntityProperty ☃x : ☃.field_186623_a) {
            EntityProperty.Serializer<EntityProperty> ☃xx = EntityPropertyManager.func_186645_a(☃x);
            ☃.add(☃xx.func_186649_a().toString(), ☃xx.func_186650_a(☃x, ☃));
         }

         ☃.add("properties", ☃);
         ☃.add("entity", ☃.serialize(☃.field_186624_b));
      }

      public EntityHasProperty func_186603_b(JsonObject var1, JsonDeserializationContext var2) {
         Set<Entry<String, JsonElement>> ☃ = JsonUtils.func_152754_s(☃, "properties").entrySet();
         EntityProperty[] ☃x = new EntityProperty[☃.size()];
         int ☃xx = 0;

         for(Entry<String, JsonElement> ☃xxx : ☃) {
            ☃x[☃xx++] = EntityPropertyManager.func_186646_a(new ResourceLocation((String)☃xxx.getKey())).func_186652_a((JsonElement)☃xxx.getValue(), ☃);
         }

         return new EntityHasProperty(☃x, JsonUtils.func_188174_a(☃, "entity", ☃, LootContext.EntityTarget.class));
      }
   }
}
