package net.minecraft.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.advancements.Advancement;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.storage.loot.ItemModifierManager;
import net.minecraft.world.level.storage.loot.PredicateManager;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class ResourceLocationArgument implements ArgumentType<ResourceLocation> {
   private static final Collection<String> EXAMPLES = Arrays.asList("foo", "foo:bar", "012");
   private static final DynamicCommandExceptionType ERROR_UNKNOWN_ADVANCEMENT = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("advancement.advancementNotFound", var0)
   );
   private static final DynamicCommandExceptionType ERROR_UNKNOWN_RECIPE = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("recipe.notFound", var0)
   );
   private static final DynamicCommandExceptionType ERROR_UNKNOWN_PREDICATE = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("predicate.unknown", var0)
   );
   private static final DynamicCommandExceptionType ERROR_UNKNOWN_ATTRIBUTE = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("attribute.unknown", var0)
   );
   private static final DynamicCommandExceptionType ERROR_UNKNOWN_ITEM_MODIFIER = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("item_modifier.unknown", var0)
   );

   public static ResourceLocationArgument id() {
      return new ResourceLocationArgument();
   }

   public static Advancement getAdvancement(CommandContext<CommandSourceStack> var0, String var1) throws CommandSyntaxException {
      ResourceLocation â˜ƒ = â˜ƒ.getArgument(â˜ƒ, ResourceLocation.class);
      Advancement â˜ƒx = â˜ƒ.getSource().getServer().getAdvancements().getAdvancement(â˜ƒ);
      if (â˜ƒx == null) {
         throw ERROR_UNKNOWN_ADVANCEMENT.create(â˜ƒ);
      } else {
         return â˜ƒx;
      }
   }

   public static Recipe<?> getRecipe(CommandContext<CommandSourceStack> var0, String var1) throws CommandSyntaxException {
      RecipeManager â˜ƒ = â˜ƒ.getSource().getServer().getRecipeManager();
      ResourceLocation â˜ƒx = â˜ƒ.getArgument(â˜ƒ, ResourceLocation.class);
      return (Recipe<?>)â˜ƒ.byKey(â˜ƒx).orElseThrow(() -> ERROR_UNKNOWN_RECIPE.create(â˜ƒ));
   }

   public static LootItemCondition getPredicate(CommandContext<CommandSourceStack> var0, String var1) throws CommandSyntaxException {
      ResourceLocation â˜ƒ = â˜ƒ.getArgument(â˜ƒ, ResourceLocation.class);
      PredicateManager â˜ƒx = â˜ƒ.getSource().getServer().getPredicateManager();
      LootItemCondition â˜ƒxx = â˜ƒx.get(â˜ƒ);
      if (â˜ƒxx == null) {
         throw ERROR_UNKNOWN_PREDICATE.create(â˜ƒ);
      } else {
         return â˜ƒxx;
      }
   }

   public static LootItemFunction getItemModifier(CommandContext<CommandSourceStack> var0, String var1) throws CommandSyntaxException {
      ResourceLocation â˜ƒ = â˜ƒ.getArgument(â˜ƒ, ResourceLocation.class);
      ItemModifierManager â˜ƒx = â˜ƒ.getSource().getServer().getItemModifierManager();
      LootItemFunction â˜ƒxx = â˜ƒx.get(â˜ƒ);
      if (â˜ƒxx == null) {
         throw ERROR_UNKNOWN_ITEM_MODIFIER.create(â˜ƒ);
      } else {
         return â˜ƒxx;
      }
   }

   public static Attribute getAttribute(CommandContext<CommandSourceStack> var0, String var1) throws CommandSyntaxException {
      ResourceLocation â˜ƒ = â˜ƒ.getArgument(â˜ƒ, ResourceLocation.class);
      return (Attribute)Registry.ATTRIBUTE.getOptional(â˜ƒ).orElseThrow(() -> ERROR_UNKNOWN_ATTRIBUTE.create(â˜ƒ));
   }

   public static ResourceLocation getId(CommandContext<CommandSourceStack> var0, String var1) {
      return â˜ƒ.getArgument(â˜ƒ, ResourceLocation.class);
   }

   public ResourceLocation parse(StringReader var1) throws CommandSyntaxException {
      return ResourceLocation.read(â˜ƒ);
   }

   @Override
   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
