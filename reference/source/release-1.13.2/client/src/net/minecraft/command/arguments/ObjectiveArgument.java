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
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.text.TextComponentTranslation;

public class ObjectiveArgument implements ArgumentType<String> {
   private static final Collection<String> field_201317_b = Arrays.asList("foo", "*", "012");
   private static final DynamicCommandExceptionType field_197159_a = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("arguments.objective.notFound", var0)
   );
   private static final DynamicCommandExceptionType field_197160_b = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("arguments.objective.readonly", var0)
   );
   public static final DynamicCommandExceptionType field_200379_a = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("commands.scoreboard.objectives.add.longName", var0)
   );

   public static ObjectiveArgument func_197157_a() {
      return new ObjectiveArgument();
   }

   public static ScoreObjective func_197158_a(CommandContext<CommandSource> var0, String var1) throws CommandSyntaxException {
      String ☃ = ☃.getArgument(☃, String.class);
      Scoreboard ☃x = ☃.getSource().func_197028_i().func_200251_aP();
      ScoreObjective ☃xx = ☃x.func_96518_b(☃);
      if (☃xx == null) {
         throw field_197159_a.create(☃);
      } else {
         return ☃xx;
      }
   }

   public static ScoreObjective func_197156_b(CommandContext<CommandSource> var0, String var1) throws CommandSyntaxException {
      ScoreObjective ☃ = func_197158_a(☃, ☃);
      if (☃.func_96680_c().func_96637_b()) {
         throw field_197160_b.create(☃.func_96679_b());
      } else {
         return ☃;
      }
   }

   public String parse(StringReader var1) throws CommandSyntaxException {
      String ☃ = ☃.readUnquotedString();
      if (☃.length() > 16) {
         throw field_200379_a.create(16);
      } else {
         return ☃;
      }
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      if (☃.getSource() instanceof CommandSource) {
         return ISuggestionProvider.func_197005_b(((CommandSource)☃.getSource()).func_197028_i().func_200251_aP().func_197897_d(), ☃);
      } else if (☃.getSource() instanceof ISuggestionProvider) {
         ISuggestionProvider ☃ = (ISuggestionProvider)☃.getSource();
         return ☃.func_197009_a(☃, ☃);
      } else {
         return Suggestions.empty();
      }
   }

   @Override
   public Collection<String> getExamples() {
      return field_201317_b;
   }
}
