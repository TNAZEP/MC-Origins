package net.minecraft.commands.arguments;

import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.EquipmentSlot;

public class SlotArgument implements ArgumentType<Integer> {
   private static final Collection<String> EXAMPLES = Arrays.asList("container.5", "12", "weapon");
   private static final DynamicCommandExceptionType ERROR_UNKNOWN_SLOT = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("slot.unknown", var0)
   );
   private static final Map<String, Integer> SLOTS = Util.make(Maps.newHashMap(), var0 -> {
      for(int â˜ƒ = 0; â˜ƒ < 54; ++â˜ƒ) {
         var0.put("container." + â˜ƒ, â˜ƒ);
      }

      for(int â˜ƒ = 0; â˜ƒ < 9; ++â˜ƒ) {
         var0.put("hotbar." + â˜ƒ, â˜ƒ);
      }

      for(int â˜ƒ = 0; â˜ƒ < 27; ++â˜ƒ) {
         var0.put("inventory." + â˜ƒ, 9 + â˜ƒ);
      }

      for(int â˜ƒ = 0; â˜ƒ < 27; ++â˜ƒ) {
         var0.put("enderchest." + â˜ƒ, 200 + â˜ƒ);
      }

      for(int â˜ƒ = 0; â˜ƒ < 8; ++â˜ƒ) {
         var0.put("villager." + â˜ƒ, 300 + â˜ƒ);
      }

      for(int â˜ƒ = 0; â˜ƒ < 15; ++â˜ƒ) {
         var0.put("horse." + â˜ƒ, 500 + â˜ƒ);
      }

      var0.put("weapon", EquipmentSlot.MAINHAND.getIndex(98));
      var0.put("weapon.mainhand", EquipmentSlot.MAINHAND.getIndex(98));
      var0.put("weapon.offhand", EquipmentSlot.OFFHAND.getIndex(98));
      var0.put("armor.head", EquipmentSlot.HEAD.getIndex(100));
      var0.put("armor.chest", EquipmentSlot.CHEST.getIndex(100));
      var0.put("armor.legs", EquipmentSlot.LEGS.getIndex(100));
      var0.put("armor.feet", EquipmentSlot.FEET.getIndex(100));
      var0.put("horse.saddle", 400);
      var0.put("horse.armor", 401);
      var0.put("horse.chest", 499);
   });

   public static SlotArgument slot() {
      return new SlotArgument();
   }

   public static int getSlot(CommandContext<CommandSourceStack> var0, String var1) {
      return â˜ƒ.getArgument(â˜ƒ, Integer.class);
   }

   public Integer parse(StringReader var1) throws CommandSyntaxException {
      String â˜ƒ = â˜ƒ.readUnquotedString();
      if (!SLOTS.containsKey(â˜ƒ)) {
         throw ERROR_UNKNOWN_SLOT.create(â˜ƒ);
      } else {
         return (Integer)SLOTS.get(â˜ƒ);
      }
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      return SharedSuggestionProvider.suggest(SLOTS.keySet(), â˜ƒ);
   }

   @Override
   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
