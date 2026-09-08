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
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.TextComponent;

public class TestFunctionArgument implements ArgumentType<TestFunction> {
   private static final Collection<String> EXAMPLES = Arrays.asList("techtests.piston", "techtests");

   public TestFunction parse(StringReader var1) throws CommandSyntaxException {
      String â˜ƒ = â˜ƒ.readUnquotedString();
      Optional<TestFunction> â˜ƒx = GameTestRegistry.findTestFunction(â˜ƒ);
      if (â˜ƒx.isPresent()) {
         return (TestFunction)â˜ƒx.get();
      } else {
         Message â˜ƒ = new TextComponent("No such test: " + â˜ƒ);
         throw new CommandSyntaxException(new SimpleCommandExceptionType(â˜ƒ), â˜ƒ);
      }
   }

   public static TestFunctionArgument testFunctionArgument() {
      return new TestFunctionArgument();
   }

   public static TestFunction getTestFunction(CommandContext<CommandSourceStack> var0, String var1) {
      return â˜ƒ.getArgument(â˜ƒ, TestFunction.class);
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      Stream<String> â˜ƒ = GameTestRegistry.getAllTestFunctions().stream().map(TestFunction::getTestName);
      return SharedSuggestionProvider.suggest(â˜ƒ, â˜ƒ);
   }

   @Override
   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
