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
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;

public class TeamArgument implements ArgumentType<String> {
   private static final Collection<String> EXAMPLES = Arrays.asList("foo", "123");
   private static final DynamicCommandExceptionType ERROR_TEAM_NOT_FOUND = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("team.notFound", var0)
   );

   public static TeamArgument team() {
      return new TeamArgument();
   }

   public static PlayerTeam getTeam(CommandContext<CommandSourceStack> var0, String var1) throws CommandSyntaxException {
      String â˜ƒ = â˜ƒ.getArgument(â˜ƒ, String.class);
      Scoreboard â˜ƒx = â˜ƒ.getSource().getServer().getScoreboard();
      PlayerTeam â˜ƒxx = â˜ƒx.getPlayerTeam(â˜ƒ);
      if (â˜ƒxx == null) {
         throw ERROR_TEAM_NOT_FOUND.create(â˜ƒ);
      } else {
         return â˜ƒxx;
      }
   }

   public String parse(StringReader var1) throws CommandSyntaxException {
      return â˜ƒ.readUnquotedString();
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      return â˜ƒ.getSource() instanceof SharedSuggestionProvider
         ? SharedSuggestionProvider.suggest(((SharedSuggestionProvider)â˜ƒ.getSource()).getAllTeams(), â˜ƒ)
         : Suggestions.empty();
   }

   @Override
   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
