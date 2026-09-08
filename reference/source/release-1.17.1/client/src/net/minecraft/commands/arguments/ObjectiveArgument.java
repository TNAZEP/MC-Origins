package net.minecraft.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;

public class ObjectiveArgument implements ArgumentType<String> {
   private static final Collection<String> EXAMPLES = Arrays.asList("foo", "*", "012");
   private static final DynamicCommandExceptionType ERROR_OBJECTIVE_NOT_FOUND = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("arguments.objective.notFound", var0)
   );
   private static final DynamicCommandExceptionType ERROR_OBJECTIVE_READ_ONLY = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("arguments.objective.readonly", var0)
   );
   public static final DynamicCommandExceptionType ERROR_OBJECTIVE_NAME_TOO_LONG = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("commands.scoreboard.objectives.add.longName", var0)
   );

   public static ObjectiveArgument objective() {
      return new ObjectiveArgument();
   }

   public static Objective getObjective(CommandContext<CommandSourceStack> var0, String var1) throws CommandSyntaxException {
      String â˜ƒ = â˜ƒ.getArgument(â˜ƒ, String.class);
      Scoreboard â˜ƒx = â˜ƒ.getSource().getServer().getScoreboard();
      Objective â˜ƒxx = â˜ƒx.getObjective(â˜ƒ);
      if (â˜ƒxx == null) {
         throw ERROR_OBJECTIVE_NOT_FOUND.create(â˜ƒ);
      } else {
         return â˜ƒxx;
      }
   }

   public static Objective getWritableObjective(CommandContext<CommandSourceStack> var0, String var1) throws CommandSyntaxException {
      Objective â˜ƒ = getObjective(â˜ƒ, â˜ƒ);
      if (â˜ƒ.getCriteria().isReadOnly()) {
         throw ERROR_OBJECTIVE_READ_ONLY.create(â˜ƒ.getName());
      } else {
         return â˜ƒ;
      }
   }

   public String parse(StringReader var1) throws CommandSyntaxException {
      String â˜ƒ = â˜ƒ.readUnquotedString();
      if (â˜ƒ.length() > 16) {
         throw ERROR_OBJECTIVE_NAME_TOO_LONG.create(16);
      } else {
         return â˜ƒ;
      }
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      if (â˜ƒ.getSource() instanceof CommandSourceStack) {
         return SharedSuggestionProvider.suggest(((CommandSourceStack)â˜ƒ.getSource()).getServer().getScoreboard().getObjectiveNames(), â˜ƒ);
      } else {
         return â˜ƒ.getSource() instanceof SharedSuggestionProvider â˜ƒ ? â˜ƒ.customSuggestion(â˜ƒ, â˜ƒ) : Suggestions.empty();
      }
   }

   @Override
   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
