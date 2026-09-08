package net.minecraft.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class ItemArgument implements ArgumentType<ItemInput> {
   private static final Collection<String> field_201339_a = Arrays.asList("stick", "minecraft:stick", "stick{foo=bar}");

   public static ItemArgument func_197317_a() {
      return new ItemArgument();
   }

   public ItemInput parse(StringReader var1) throws CommandSyntaxException {
      ItemParser ☃ = new ItemParser(☃, false).func_197327_f();
      return new ItemInput(☃.func_197326_b(), ☃.func_197325_c());
   }

   public static <S> ItemInput func_197316_a(CommandContext<S> var0, String var1) {
      return ☃.getArgument(☃, ItemInput.class);
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      StringReader ☃ = new StringReader(☃.getInput());
      ☃.setCursor(☃.getStart());
      ItemParser ☃x = new ItemParser(☃, false);

      try {
         ☃x.func_197327_f();
      } catch (CommandSyntaxException var6) {
      }

      return ☃x.func_197329_a(☃);
   }

   @Override
   public Collection<String> getExamples() {
      return field_201339_a;
   }
}
