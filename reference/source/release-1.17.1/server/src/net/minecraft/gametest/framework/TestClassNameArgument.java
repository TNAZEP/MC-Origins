package net.minecraft.gametest.framework;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.TextComponent;

public class TestClassNameArgument implements ArgumentType<String> {
   private static final Collection<String> EXAMPLES = Arrays.asList("techtests", "mobtests");

   public String parse(StringReader var1) throws CommandSyntaxException {
      String â˜ƒ = â˜ƒ.readUnquotedString();
      if (GameTestRegistry.isTestClass(â˜ƒ)) {
         return â˜ƒ;
      } else {
         Message â˜ƒ = new TextComponent("No such test class: " + â˜ƒ);
         throw new CommandSyntaxException(new SimpleCommandExceptionType(â˜ƒ), â˜ƒ);
      }
   }

   public static TestClassNameArgument testClassName() {
      return new TestClassNameArgument();
   }

   public static String getTestClassName(CommandContext<CommandSourceStack> var0, String var1) {
      return â˜ƒ.getArgument(â˜ƒ, String.class);
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      return SharedSuggestionProvider.suggest(GameTestRegistry.getAllTestClassNames().stream(), â˜ƒ);
   }

   @Override
   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
