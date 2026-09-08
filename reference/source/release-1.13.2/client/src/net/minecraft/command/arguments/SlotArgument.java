package net.minecraft.command.arguments;

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
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.Util;
import net.minecraft.util.text.TextComponentTranslation;

public class SlotArgument implements ArgumentType<Integer> {
   private static final Collection<String> field_201329_a = Arrays.asList("container.5", "12", "weapon");
   private static final DynamicCommandExceptionType field_197224_a = new DynamicCommandExceptionType(var0 -> new TextComponentTranslation("slot.unknown", var0));
   private static final Map<String, Integer> field_197225_b = Util.func_200696_a(Maps.newHashMap(), var0 -> {
      for(int ☃ = 0; ☃ < 54; ++☃) {
         var0.put("container." + ☃, ☃);
      }

      for(int ☃ = 0; ☃ < 9; ++☃) {
         var0.put("hotbar." + ☃, ☃);
      }

      for(int ☃ = 0; ☃ < 27; ++☃) {
         var0.put("inventory." + ☃, 9 + ☃);
      }

      for(int ☃ = 0; ☃ < 27; ++☃) {
         var0.put("enderchest." + ☃, 200 + ☃);
      }

      for(int ☃ = 0; ☃ < 8; ++☃) {
         var0.put("villager." + ☃, 300 + ☃);
      }

      for(int ☃ = 0; ☃ < 15; ++☃) {
         var0.put("horse." + ☃, 500 + ☃);
      }

      var0.put("weapon", 98);
      var0.put("weapon.mainhand", 98);
      var0.put("weapon.offhand", 99);
      var0.put("armor.head", 100 + EntityEquipmentSlot.HEAD.func_188454_b());
      var0.put("armor.chest", 100 + EntityEquipmentSlot.CHEST.func_188454_b());
      var0.put("armor.legs", 100 + EntityEquipmentSlot.LEGS.func_188454_b());
      var0.put("armor.feet", 100 + EntityEquipmentSlot.FEET.func_188454_b());
      var0.put("horse.saddle", 400);
      var0.put("horse.armor", 401);
      var0.put("horse.chest", 499);
   });

   public static SlotArgument func_197223_a() {
      return new SlotArgument();
   }

   public static int func_197221_a(CommandContext<CommandSource> var0, String var1) {
      return ☃.getArgument(☃, Integer.class);
   }

   public Integer parse(StringReader var1) throws CommandSyntaxException {
      String ☃ = ☃.readUnquotedString();
      if (!field_197225_b.containsKey(☃)) {
         throw field_197224_a.create(☃);
      } else {
         return (Integer)field_197225_b.get(☃);
      }
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      return ISuggestionProvider.func_197005_b(field_197225_b.keySet(), ☃);
   }

   @Override
   public Collection<String> getExamples() {
      return field_201329_a;
   }
}
