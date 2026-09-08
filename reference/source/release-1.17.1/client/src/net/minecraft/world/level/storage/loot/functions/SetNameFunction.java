package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Set;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SetNameFunction extends LootItemConditionalFunction {
   private static final Logger LOGGER = LogManager.getLogger();
   final Component name;
   @Nullable
   final LootContext.EntityTarget resolutionContext;

   SetNameFunction(LootItemCondition[] var1, @Nullable Component var2, @Nullable LootContext.EntityTarget var3) {
      super(â˜ƒ);
      this.name = â˜ƒ;
      this.resolutionContext = â˜ƒ;
   }

   @Override
   public LootItemFunctionType getType() {
      return LootItemFunctions.SET_NAME;
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return this.resolutionContext != null ? ImmutableSet.of(this.resolutionContext.getParam()) : ImmutableSet.of();
   }

   public static UnaryOperator<Component> createResolver(LootContext var0, @Nullable LootContext.EntityTarget var1) {
      if (â˜ƒ != null) {
         Entity â˜ƒ = â˜ƒ.getParamOrNull(â˜ƒ.getParam());
         if (â˜ƒ != null) {
            CommandSourceStack â˜ƒx = â˜ƒ.createCommandSourceStack().withPermission(2);
            return var2x -> {
               try {
                  return ComponentUtils.updateForEntity(â˜ƒ, var2x, â˜ƒ, 0);
               } catch (CommandSyntaxException var4) {
                  LOGGER.warn("Failed to resolve text component", var4);
                  return var2x;
               }
            };
         }
      }

      return var0x -> var0x;
   }

   @Override
   public ItemStack run(ItemStack var1, LootContext var2) {
      if (this.name != null) {
         â˜ƒ.setHoverName((Component)createResolver(â˜ƒ, this.resolutionContext).apply(this.name));
      }

      return â˜ƒ;
   }

   public static LootItemConditionalFunction.Builder<?> setName(Component var0) {
      return simpleBuilder(var1 -> new SetNameFunction(var1, â˜ƒ, null));
   }

   public static LootItemConditionalFunction.Builder<?> setName(Component var0, LootContext.EntityTarget var1) {
      return simpleBuilder(var2 -> new SetNameFunction(var2, â˜ƒ, â˜ƒ));
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<SetNameFunction> {
      public void serialize(JsonObject var1, SetNameFunction var2, JsonSerializationContext var3) {
         super.serialize(â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒ.name != null) {
            â˜ƒ.add("name", Component.Serializer.toJsonTree(â˜ƒ.name));
         }

         if (â˜ƒ.resolutionContext != null) {
            â˜ƒ.add("entity", â˜ƒ.serialize(â˜ƒ.resolutionContext));
         }
      }

      public SetNameFunction deserialize(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3) {
         Component â˜ƒ = Component.Serializer.fromJson(â˜ƒ.get("name"));
         LootContext.EntityTarget â˜ƒx = GsonHelper.getAsObject(â˜ƒ, "entity", null, â˜ƒ, LootContext.EntityTarget.class);
         return new SetNameFunction(â˜ƒ, â˜ƒ, â˜ƒx);
      }
   }
}
