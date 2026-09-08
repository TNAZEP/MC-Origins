package net.minecraft.world.storage.loot.functions;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SetAttributes extends LootFunction {
   private static final Logger field_186560_a = LogManager.getLogger();
   private final SetAttributes.Modifier[] field_186561_b;

   public SetAttributes(LootCondition[] var1, SetAttributes.Modifier[] var2) {
      super(☃);
      this.field_186561_b = ☃;
   }

   @Override
   public ItemStack func_186553_a(ItemStack var1, Random var2, LootContext var3) {
      for(SetAttributes.Modifier ☃ : this.field_186561_b) {
         UUID ☃x = ☃.field_186600_e;
         if (☃x == null) {
            ☃x = UUID.randomUUID();
         }

         EntityEquipmentSlot ☃x = ☃.field_186601_f[☃.nextInt(☃.field_186601_f.length)];
         ☃.func_185129_a(☃.field_186597_b, new AttributeModifier(☃x, ☃.field_186596_a, (double)☃.field_186599_d.func_186507_b(☃), ☃.field_186598_c), ☃x);
      }

      return ☃;
   }

   static class Modifier {
      private final String field_186596_a;
      private final String field_186597_b;
      private final int field_186598_c;
      private final RandomValueRange field_186599_d;
      @Nullable
      private final UUID field_186600_e;
      private final EntityEquipmentSlot[] field_186601_f;

      private Modifier(String var1, String var2, int var3, RandomValueRange var4, EntityEquipmentSlot[] var5, @Nullable UUID var6) {
         this.field_186596_a = ☃;
         this.field_186597_b = ☃;
         this.field_186598_c = ☃;
         this.field_186599_d = ☃;
         this.field_186600_e = ☃;
         this.field_186601_f = ☃;
      }

      public JsonObject func_186592_a(JsonSerializationContext var1) {
         JsonObject ☃ = new JsonObject();
         ☃.addProperty("name", this.field_186596_a);
         ☃.addProperty("attribute", this.field_186597_b);
         ☃.addProperty("operation", func_186594_a(this.field_186598_c));
         ☃.add("amount", ☃.serialize(this.field_186599_d));
         if (this.field_186600_e != null) {
            ☃.addProperty("id", this.field_186600_e.toString());
         }

         if (this.field_186601_f.length == 1) {
            ☃.addProperty("slot", this.field_186601_f[0].func_188450_d());
         } else {
            JsonArray ☃ = new JsonArray();

            for(EntityEquipmentSlot ☃x : this.field_186601_f) {
               ☃.add(new JsonPrimitive(☃x.func_188450_d()));
            }

            ☃.add("slot", ☃);
         }

         return ☃;
      }

      public static SetAttributes.Modifier func_186586_a(JsonObject var0, JsonDeserializationContext var1) {
         String ☃x = JsonUtils.func_151200_h(☃, "name");
         String ☃xx = JsonUtils.func_151200_h(☃, "attribute");
         int ☃xxx = func_186595_a(JsonUtils.func_151200_h(☃, "operation"));
         RandomValueRange ☃xxxx = JsonUtils.func_188174_a(☃, "amount", ☃, RandomValueRange.class);
         UUID ☃xxxxx = null;
         EntityEquipmentSlot[] ☃;
         if (JsonUtils.func_151205_a(☃, "slot")) {
            ☃ = new EntityEquipmentSlot[]{EntityEquipmentSlot.func_188451_a(JsonUtils.func_151200_h(☃, "slot"))};
         } else {
            if (!JsonUtils.func_151202_d(☃, "slot")) {
               throw new JsonSyntaxException("Invalid or missing attribute modifier slot; must be either string or array of strings.");
            }

            JsonArray ☃ = JsonUtils.func_151214_t(☃, "slot");
            ☃ = new EntityEquipmentSlot[☃.size()];
            int ☃x = 0;

            for(JsonElement ☃xx : ☃) {
               ☃[☃x++] = EntityEquipmentSlot.func_188451_a(JsonUtils.func_151206_a(☃xx, "slot"));
            }

            if (☃.length == 0) {
               throw new JsonSyntaxException("Invalid attribute modifier slot; must contain at least one entry.");
            }
         }

         if (☃.has("id")) {
            String ☃ = JsonUtils.func_151200_h(☃, "id");

            try {
               ☃xxxxx = UUID.fromString(☃);
            } catch (IllegalArgumentException var12) {
               throw new JsonSyntaxException("Invalid attribute modifier id '" + ☃ + "' (must be UUID format, with dashes)");
            }
         }

         return new SetAttributes.Modifier(☃x, ☃xx, ☃xxx, ☃xxxx, ☃, ☃xxxxx);
      }

      private static String func_186594_a(int var0) {
         switch(☃) {
            case 0:
               return "addition";
            case 1:
               return "multiply_base";
            case 2:
               return "multiply_total";
            default:
               throw new IllegalArgumentException("Unknown operation " + ☃);
         }
      }

      private static int func_186595_a(String var0) {
         if ("addition".equals(☃)) {
            return 0;
         } else if ("multiply_base".equals(☃)) {
            return 1;
         } else if ("multiply_total".equals(☃)) {
            return 2;
         } else {
            throw new JsonSyntaxException("Unknown attribute modifier operation " + ☃);
         }
      }
   }

   public static class Serializer extends LootFunction.Serializer<SetAttributes> {
      public Serializer() {
         super(new ResourceLocation("set_attributes"), SetAttributes.class);
      }

      public void func_186532_a(JsonObject var1, SetAttributes var2, JsonSerializationContext var3) {
         JsonArray ☃ = new JsonArray();

         for(SetAttributes.Modifier ☃x : ☃.field_186561_b) {
            ☃.add(☃x.func_186592_a(☃));
         }

         ☃.add("modifiers", ☃);
      }

      public SetAttributes func_186530_b(JsonObject var1, JsonDeserializationContext var2, LootCondition[] var3) {
         JsonArray ☃ = JsonUtils.func_151214_t(☃, "modifiers");
         SetAttributes.Modifier[] ☃x = new SetAttributes.Modifier[☃.size()];
         int ☃xx = 0;

         for(JsonElement ☃xxx : ☃) {
            ☃x[☃xx++] = SetAttributes.Modifier.func_186586_a(JsonUtils.func_151210_l(☃xxx, "modifier"), ☃);
         }

         if (☃x.length == 0) {
            throw new JsonSyntaxException("Invalid attribute modifiers array; cannot be empty");
         } else {
            return new SetAttributes(☃, ☃x);
         }
      }
   }
}
