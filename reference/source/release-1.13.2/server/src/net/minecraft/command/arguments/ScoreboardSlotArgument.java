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
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.text.TextComponentTranslation;

public class ScoreboardSlotArgument implements ArgumentType<Integer> {
   private static final Collection<String> field_201328_b = Arrays.asList("sidebar", "foo.bar");
   public static final DynamicCommandExceptionType field_197220_a = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("argument.scoreboardDisplaySlot.invalid", var0)
   );

   private ScoreboardSlotArgument() {
   }

   public static ScoreboardSlotArgument func_197219_a() {
      return new ScoreboardSlotArgument();
   }

   public static int func_197217_a(CommandContext<CommandSource> var0, String var1) {
      return ☃.getArgument(☃, Integer.class);
   }

   public Integer parse(StringReader var1) throws CommandSyntaxException {
      String ☃ = ☃.readUnquotedString();
      int ☃x = Scoreboard.func_96537_j(☃);
      if (☃x == -1) {
         throw field_197220_a.create(☃);
      } else {
         return ☃x;
      }
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      return ISuggestionProvider.func_197008_a(Scoreboard.func_178821_h(), ☃);
   }

   @Override
   public Collection<String> getExamples() {
      return field_201328_b;
   }
}
