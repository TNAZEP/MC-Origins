package net.minecraft.world.storage.loot.functions;

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
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.conditions.LootCondition;

public class LootFunctionManager {
   private static final Map<ResourceLocation, LootFunction.Serializer<?>> field_186584_a = Maps.<ResourceLocation, LootFunction.Serializer<?>>newHashMap();
   private static final Map<Class<? extends LootFunction>, LootFunction.Serializer<?>> field_186585_b = Maps.newHashMap();

   public static <T extends LootFunction> void func_186582_a(LootFunction.Serializer<? extends T> var0) {
      ResourceLocation ☃ = ☃.func_186529_a();
      Class<T> ☃x = ☃.func_186531_b();
      if (field_186584_a.containsKey(☃)) {
         throw new IllegalArgumentException("Can't re-register item function name " + ☃);
      } else if (field_186585_b.containsKey(☃x)) {
         throw new IllegalArgumentException("Can't re-register item function class " + ☃x.getName());
      } else {
         field_186584_a.put(☃, ☃);
         field_186585_b.put(☃x, ☃);
      }
   }

   public static LootFunction.Serializer<?> func_186583_a(ResourceLocation var0) {
      LootFunction.Serializer<?> ☃ = (LootFunction.Serializer)field_186584_a.get(☃);
      if (☃ == null) {
         throw new IllegalArgumentException("Unknown loot item function '" + ☃ + "'");
      } else {
         return ☃;
      }
   }

   public static <T extends LootFunction> LootFunction.Serializer<T> func_186581_a(T var0) {
      LootFunction.Serializer<T> ☃ = (LootFunction.Serializer)field_186585_b.get(☃.getClass());
      if (☃ == null) {
         throw new IllegalArgumentException("Unknown loot item function " + ☃);
      } else {
         return ☃;
      }
   }

   static {
      func_186582_a(new SetCount.Serializer());
      func_186582_a(new EnchantWithLevels.Serializer());
      func_186582_a(new EnchantRandomly.Serializer());
      func_186582_a(new SetNBT.Serializer());
      func_186582_a(new Smelt.Serializer());
      func_186582_a(new LootingEnchantBonus.Serializer());
      func_186582_a(new SetDamage.Serializer());
      func_186582_a(new SetAttributes.Serializer());
      func_186582_a(new SetName.Serializer());
      func_186582_a(new ExplorationMap.Serializer());
   }

   public static class Serializer implements JsonDeserializer<LootFunction>, JsonSerializer<LootFunction> {
      public LootFunction deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject ☃ = JsonUtils.func_151210_l(☃, "function");
         ResourceLocation ☃x = new ResourceLocation(JsonUtils.func_151200_h(☃, "function"));

         LootFunction.Serializer<?> ☃;
         try {
            ☃ = LootFunctionManager.func_186583_a(☃x);
         } catch (IllegalArgumentException var8) {
            throw new JsonSyntaxException("Unknown function '" + ☃x + "'");
         }

         return ☃.func_186530_b(☃, ☃, JsonUtils.func_188177_a(☃, "conditions", new LootCondition[0], ☃, LootCondition[].class));
      }

      public JsonElement serialize(LootFunction var1, Type var2, JsonSerializationContext var3) {
         LootFunction.Serializer<LootFunction> ☃ = LootFunctionManager.func_186581_a(☃);
         JsonObject ☃x = new JsonObject();
         ☃.func_186532_a(☃x, ☃, ☃);
         ☃x.addProperty("function", ☃.func_186529_a().toString());
         if (☃.func_186554_a() != null && ☃.func_186554_a().length > 0) {
            ☃x.add("conditions", ☃.serialize(☃.func_186554_a()));
         }

         return ☃x;
      }
   }
}
