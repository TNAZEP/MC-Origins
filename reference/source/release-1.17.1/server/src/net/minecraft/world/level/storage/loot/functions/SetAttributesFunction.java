package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class SetAttributesFunction extends LootItemConditionalFunction {
   final List<SetAttributesFunction.Modifier> modifiers;

   SetAttributesFunction(LootItemCondition[] var1, List<SetAttributesFunction.Modifier> var2) {
      super(â˜ƒ);
      this.modifiers = ImmutableList.copyOf(â˜ƒ);
   }

   @Override
   public LootItemFunctionType getType() {
      return LootItemFunctions.SET_ATTRIBUTES;
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return (Set<LootContextParam<?>>)this.modifiers
         .stream()
         .flatMap(var0 -> var0.amount.getReferencedContextParams().stream())
         .collect(ImmutableSet.toImmutableSet());
   }

   @Override
   public ItemStack run(ItemStack var1, LootContext var2) {
      Random â˜ƒ = â˜ƒ.getRandom();

      for(SetAttributesFunction.Modifier â˜ƒx : this.modifiers) {
         UUID â˜ƒxx = â˜ƒx.id;
         if (â˜ƒxx == null) {
            â˜ƒxx = UUID.randomUUID();
         }

         EquipmentSlot â˜ƒxx = Util.getRandom((EquipmentSlot[])â˜ƒx.slots, â˜ƒ);
         â˜ƒ.addAttributeModifier(â˜ƒx.attribute, new AttributeModifier(â˜ƒxx, â˜ƒx.name, (double)â˜ƒx.amount.getFloat(â˜ƒ), â˜ƒx.operation), â˜ƒxx);
      }

      return â˜ƒ;
   }

   public static SetAttributesFunction.ModifierBuilder modifier(String var0, Attribute var1, AttributeModifier.Operation var2, NumberProvider var3) {
      return new SetAttributesFunction.ModifierBuilder(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static SetAttributesFunction.Builder setAttributes() {
      return new SetAttributesFunction.Builder();
   }

   public static class Builder extends LootItemConditionalFunction.Builder<SetAttributesFunction.Builder> {
      private final List<SetAttributesFunction.Modifier> modifiers = Lists.<SetAttributesFunction.Modifier>newArrayList();

      protected SetAttributesFunction.Builder getThis() {
         return this;
      }

      public SetAttributesFunction.Builder withModifier(SetAttributesFunction.ModifierBuilder var1) {
         this.modifiers.add(â˜ƒ.build());
         return this;
      }

      @Override
      public LootItemFunction build() {
         return new SetAttributesFunction(this.getConditions(), this.modifiers);
      }
   }

   static class Modifier {
      final String name;
      final Attribute attribute;
      final AttributeModifier.Operation operation;
      final NumberProvider amount;
      @Nullable
      final UUID id;
      final EquipmentSlot[] slots;

      Modifier(String var1, Attribute var2, AttributeModifier.Operation var3, NumberProvider var4, EquipmentSlot[] var5, @Nullable UUID var6) {
         this.name = â˜ƒ;
         this.attribute = â˜ƒ;
         this.operation = â˜ƒ;
         this.amount = â˜ƒ;
         this.id = â˜ƒ;
         this.slots = â˜ƒ;
      }

      public JsonObject serialize(JsonSerializationContext var1) {
         JsonObject â˜ƒ = new JsonObject();
         â˜ƒ.addProperty("name", this.name);
         â˜ƒ.addProperty("attribute", Registry.ATTRIBUTE.getKey(this.attribute).toString());
         â˜ƒ.addProperty("operation", operationToString(this.operation));
         â˜ƒ.add("amount", â˜ƒ.serialize(this.amount));
         if (this.id != null) {
            â˜ƒ.addProperty("id", this.id.toString());
         }

         if (this.slots.length == 1) {
            â˜ƒ.addProperty("slot", this.slots[0].getName());
         } else {
            JsonArray â˜ƒ = new JsonArray();

            for(EquipmentSlot â˜ƒx : this.slots) {
               â˜ƒ.add(new JsonPrimitive(â˜ƒx.getName()));
            }

            â˜ƒ.add("slot", â˜ƒ);
         }

         return â˜ƒ;
      }

      public static SetAttributesFunction.Modifier deserialize(JsonObject var0, JsonDeserializationContext var1) {
         String â˜ƒ = GsonHelper.getAsString(â˜ƒ, "name");
         ResourceLocation â˜ƒx = new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "attribute"));
         Attribute â˜ƒxx = Registry.ATTRIBUTE.get(â˜ƒx);
         if (â˜ƒxx == null) {
            throw new JsonSyntaxException("Unknown attribute: " + â˜ƒx);
         } else {
            AttributeModifier.Operation â˜ƒx = operationFromString(GsonHelper.getAsString(â˜ƒ, "operation"));
            NumberProvider â˜ƒxx = GsonHelper.getAsObject(â˜ƒ, "amount", â˜ƒ, NumberProvider.class);
            UUID â˜ƒxxx = null;
            EquipmentSlot[] â˜ƒ;
            if (GsonHelper.isStringValue(â˜ƒ, "slot")) {
               â˜ƒ = new EquipmentSlot[]{EquipmentSlot.byName(GsonHelper.getAsString(â˜ƒ, "slot"))};
            } else {
               if (!GsonHelper.isArrayNode(â˜ƒ, "slot")) {
                  throw new JsonSyntaxException("Invalid or missing attribute modifier slot; must be either string or array of strings.");
               }

               JsonArray â˜ƒ = GsonHelper.getAsJsonArray(â˜ƒ, "slot");
               â˜ƒ = new EquipmentSlot[â˜ƒ.size()];
               int â˜ƒx = 0;

               for(JsonElement â˜ƒxx : â˜ƒ) {
                  â˜ƒ[â˜ƒx++] = EquipmentSlot.byName(GsonHelper.convertToString(â˜ƒxx, "slot"));
               }

               if (â˜ƒ.length == 0) {
                  throw new JsonSyntaxException("Invalid attribute modifier slot; must contain at least one entry.");
               }
            }

            if (â˜ƒ.has("id")) {
               String â˜ƒ = GsonHelper.getAsString(â˜ƒ, "id");

               try {
                  â˜ƒxxx = UUID.fromString(â˜ƒ);
               } catch (IllegalArgumentException var13) {
                  throw new JsonSyntaxException("Invalid attribute modifier id '" + â˜ƒ + "' (must be UUID format, with dashes)");
               }
            }

            return new SetAttributesFunction.Modifier(â˜ƒ, â˜ƒxx, â˜ƒx, â˜ƒxx, â˜ƒ, â˜ƒxxx);
         }
      }

      private static String operationToString(AttributeModifier.Operation var0) {
         switch(â˜ƒ) {
            case ADDITION:
               return "addition";
            case MULTIPLY_BASE:
               return "multiply_base";
            case MULTIPLY_TOTAL:
               return "multiply_total";
            default:
               throw new IllegalArgumentException("Unknown operation " + â˜ƒ);
         }
      }

      private static AttributeModifier.Operation operationFromString(String var0) {
         switch(â˜ƒ) {
            case "addition":
               return AttributeModifier.Operation.ADDITION;
            case "multiply_base":
               return AttributeModifier.Operation.MULTIPLY_BASE;
            case "multiply_total":
               return AttributeModifier.Operation.MULTIPLY_TOTAL;
            default:
               throw new JsonSyntaxException("Unknown attribute modifier operation " + â˜ƒ);
         }
      }
   }

   public static class ModifierBuilder {
      private final String name;
      private final Attribute attribute;
      private final AttributeModifier.Operation operation;
      private final NumberProvider amount;
      @Nullable
      private UUID id;
      private final Set<EquipmentSlot> slots = EnumSet.noneOf(EquipmentSlot.class);

      public ModifierBuilder(String var1, Attribute var2, AttributeModifier.Operation var3, NumberProvider var4) {
         this.name = â˜ƒ;
         this.attribute = â˜ƒ;
         this.operation = â˜ƒ;
         this.amount = â˜ƒ;
      }

      public SetAttributesFunction.ModifierBuilder forSlot(EquipmentSlot var1) {
         this.slots.add(â˜ƒ);
         return this;
      }

      public SetAttributesFunction.ModifierBuilder withUuid(UUID var1) {
         this.id = â˜ƒ;
         return this;
      }

      public SetAttributesFunction.Modifier build() {
         return new SetAttributesFunction.Modifier(
            this.name, this.attribute, this.operation, this.amount, (EquipmentSlot[])this.slots.toArray(new EquipmentSlot[0]), this.id
         );
      }
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<SetAttributesFunction> {
      public void serialize(JsonObject var1, SetAttributesFunction var2, JsonSerializationContext var3) {
         super.serialize(â˜ƒ, â˜ƒ, â˜ƒ);
         JsonArray â˜ƒ = new JsonArray();

         for(SetAttributesFunction.Modifier â˜ƒx : â˜ƒ.modifiers) {
            â˜ƒ.add(â˜ƒx.serialize(â˜ƒ));
         }

         â˜ƒ.add("modifiers", â˜ƒ);
      }

      public SetAttributesFunction deserialize(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3) {
         JsonArray â˜ƒ = GsonHelper.getAsJsonArray(â˜ƒ, "modifiers");
         List<SetAttributesFunction.Modifier> â˜ƒx = Lists.<SetAttributesFunction.Modifier>newArrayListWithExpectedSize(â˜ƒ.size());

         for(JsonElement â˜ƒxx : â˜ƒ) {
            â˜ƒx.add(SetAttributesFunction.Modifier.deserialize(GsonHelper.convertToJsonObject(â˜ƒxx, "modifier"), â˜ƒ));
         }

         if (â˜ƒx.isEmpty()) {
            throw new JsonSyntaxException("Invalid attribute modifiers array; cannot be empty");
         } else {
            return new SetAttributesFunction(â˜ƒ, â˜ƒx);
         }
      }
   }
}
