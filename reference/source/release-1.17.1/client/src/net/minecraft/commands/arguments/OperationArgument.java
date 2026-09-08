package net.minecraft.commands.arguments;

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
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.scores.Score;

public class OperationArgument implements ArgumentType<OperationArgument.Operation> {
   private static final Collection<String> EXAMPLES = Arrays.asList("=", ">", "<");
   private static final SimpleCommandExceptionType ERROR_INVALID_OPERATION = new SimpleCommandExceptionType(
      new TranslatableComponent("arguments.operation.invalid")
   );
   private static final SimpleCommandExceptionType ERROR_DIVIDE_BY_ZERO = new SimpleCommandExceptionType(new TranslatableComponent("arguments.operation.div0"));

   public static OperationArgument operation() {
      return new OperationArgument();
   }

   public static OperationArgument.Operation getOperation(CommandContext<CommandSourceStack> var0, String var1) {
      return â˜ƒ.getArgument(â˜ƒ, OperationArgument.Operation.class);
   }

   public OperationArgument.Operation parse(StringReader var1) throws CommandSyntaxException {
      if (!â˜ƒ.canRead()) {
         throw ERROR_INVALID_OPERATION.create();
      } else {
         int â˜ƒ = â˜ƒ.getCursor();

         while(â˜ƒ.canRead() && â˜ƒ.peek() != ' ') {
            â˜ƒ.skip();
         }

         return getOperation(â˜ƒ.getString().substring(â˜ƒ, â˜ƒ.getCursor()));
      }
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      return SharedSuggestionProvider.suggest(new String[]{"=", "+=", "-=", "*=", "/=", "%=", "<", ">", "><"}, â˜ƒ);
   }

   @Override
   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   private static OperationArgument.Operation getOperation(String var0) throws CommandSyntaxException {
      return (OperationArgument.Operation)(â˜ƒ.equals("><") ? (var0x, var1) -> {
         int â˜ƒ = var0x.getScore();
         var0x.setScore(var1.getScore());
         var1.setScore(â˜ƒ);
      } : getSimpleOperation(â˜ƒ));
   }

   private static OperationArgument.SimpleOperation getSimpleOperation(String var0) throws CommandSyntaxException {
      switch(â˜ƒ) {
         case "=":
            return (var0x, var1) -> var1;
         case "+=":
            return (var0x, var1) -> var0x + var1;
         case "-=":
            return (var0x, var1) -> var0x - var1;
         case "*=":
            return (var0x, var1) -> var0x * var1;
         case "/=":
            return (var0x, var1) -> {
               if (var1 == 0) {
                  throw ERROR_DIVIDE_BY_ZERO.create();
               } else {
                  return Mth.intFloorDiv(var0x, var1);
               }
            };
         case "%=":
            return (var0x, var1) -> {
               if (var1 == 0) {
                  throw ERROR_DIVIDE_BY_ZERO.create();
               } else {
                  return Mth.positiveModulo(var0x, var1);
               }
            };
         case "<":
            return Math::min;
         case ">":
            return Math::max;
         default:
            throw ERROR_INVALID_OPERATION.create();
      }
   }

   @FunctionalInterface
   public interface Operation {
      void apply(Score var1, Score var2) throws CommandSyntaxException;
   }

   @FunctionalInterface
   interface SimpleOperation extends OperationArgument.Operation {
      int apply(int var1, int var2) throws CommandSyntaxException;

      @Override
      default void apply(Score var1, Score var2) throws CommandSyntaxException {
         â˜ƒ.setScore(this.apply(â˜ƒ.getScore(), â˜ƒ.getScore()));
      }
   }
}
