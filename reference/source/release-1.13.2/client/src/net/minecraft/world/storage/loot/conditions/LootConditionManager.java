package net.minecraft.world.storage.loot.conditions;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;

public class LootConditionManager {
   private static final Map<ResourceLocation, LootCondition.Serializer<?>> field_186642_a = Maps.<ResourceLocation, LootCondition.Serializer<?>>newHashMap();
   private static final Map<Class<? extends LootCondition>, LootCondition.Serializer<?>> field_186643_b = Maps.newHashMap();

   public static <T extends LootCondition> void func_186639_a(LootCondition.Serializer<? extends T> var0) {
      ResourceLocation ☃ = ☃.func_186602_a();
      Class<T> ☃x = ☃.func_186604_b();
      if (field_186642_a.containsKey(☃)) {
         throw new IllegalArgumentException("Can't re-register item condition name " + ☃);
      } else if (field_186643_b.containsKey(☃x)) {
         throw new IllegalArgumentException("Can't re-register item condition class " + ☃x.getName());
      } else {
         field_186642_a.put(☃, ☃);
         field_186643_b.put(☃x, ☃);
      }
   }

   public static boolean func_186638_a(@Nullable LootCondition[] var0, Random var1, LootContext var2) {
      if (☃ == null) {
         return true;
      } else {
         for(LootCondition ☃ : ☃) {
            if (!☃.func_186618_a(☃, ☃)) {
               return false;
            }
         }

         return true;
      }
   }

   public static LootCondition.Serializer<?> func_186641_a(ResourceLocation var0) {
      LootCondition.Serializer<?> ☃ = (LootCondition.Serializer)field_186642_a.get(☃);
      if (☃ == null) {
         throw new IllegalArgumentException("Unknown loot item condition '" + ☃ + "'");
      } else {
         return ☃;
      }
   }

   public static <T extends LootCondition> LootCondition.Serializer<T> func_186640_a(T var0) {
      LootCondition.Serializer<T> ☃ = (LootCondition.Serializer)field_186643_b.get(☃.getClass());
      if (☃ == null) {
         throw new IllegalArgumentException("Unknown loot item condition " + ☃);
      } else {
         return ☃;
      }
   }

   static {
      func_186639_a(new RandomChance.Serializer());
      func_186639_a(new RandomChanceWithLooting.Serializer());
      func_186639_a(new EntityHasProperty.Serializer());
      func_186639_a(new KilledByPlayer.Serializer());
      func_186639_a(new EntityHasScore.Serializer());
   }

   public static class Serializer implements JsonDeserializer<LootCondition>, JsonSerializer<LootCondition> {
      public LootCondition deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject ☃ = JsonUtils.func_151210_l(☃, "condition");
         ResourceLocation ☃x = new ResourceLocation(JsonUtils.func_151200_h(☃, "condition"));

         LootCondition.Serializer<?> ☃;
         try {
            ☃ = LootConditionManager.func_186641_a(☃x);
         } catch (IllegalArgumentException var8) {
            throw new JsonSyntaxException("Unknown condition '" + ☃x + "'");
         }

         return ☃.func_186603_b(☃, ☃);
      }

      public JsonElement serialize(LootCondition var1, Type var2, JsonSerializationContext var3) {
         LootCondition.Serializer<LootCondition> ☃ = LootConditionManager.func_186640_a(☃);
         JsonObject ☃x = new JsonObject();
         ☃.func_186605_a(☃x, ☃, ☃);
         ☃x.addProperty("condition", ☃.func_186602_a().toString());
         return ☃x;
      }
   }
}
