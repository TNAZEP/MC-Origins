package net.minecraft.command.arguments;

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
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.text.TextComponentTranslation;

public class TeamArgument implements ArgumentType<String> {
   private static final Collection<String> field_201330_a = Arrays.asList("foo", "123");
   private static final DynamicCommandExceptionType field_197229_a = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("team.notFound", var0)
   );

   public static TeamArgument func_197227_a() {
      return new TeamArgument();
   }

   public static ScorePlayerTeam func_197228_a(CommandContext<CommandSource> var0, String var1) throws CommandSyntaxException {
      String ☃ = ☃.getArgument(☃, String.class);
      Scoreboard ☃x = ☃.getSource().func_197028_i().func_200251_aP();
      ScorePlayerTeam ☃xx = ☃x.func_96508_e(☃);
      if (☃xx == null) {
         throw field_197229_a.create(☃);
      } else {
         return ☃xx;
      }
   }

   public String parse(StringReader var1) throws CommandSyntaxException {
      return ☃.readUnquotedString();
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      return ☃.getSource() instanceof ISuggestionProvider
         ? ISuggestionProvider.func_197005_b(((ISuggestionProvider)☃.getSource()).func_197012_k(), ☃)
         : Suggestions.empty();
   }

   @Override
   public Collection<String> getExamples() {
      return field_201330_a;
   }
}
