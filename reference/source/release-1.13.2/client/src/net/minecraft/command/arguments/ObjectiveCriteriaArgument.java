package net.minecraft.command.arguments;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.text.TextComponentTranslation;

public class ObjectiveCriteriaArgument implements ArgumentType<ScoreCriteria> {
   private static final Collection<String> field_201318_b = Arrays.asList("foo", "foo.bar.baz", "minecraft:foo");
   public static final DynamicCommandExceptionType field_197164_a = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("argument.criteria.invalid", var0)
   );

   private ObjectiveCriteriaArgument() {
   }

   public static ObjectiveCriteriaArgument func_197162_a() {
      return new ObjectiveCriteriaArgument();
   }

   public static ScoreCriteria func_197161_a(CommandContext<CommandSource> var0, String var1) {
      return ☃.getArgument(☃, ScoreCriteria.class);
   }

   public ScoreCriteria parse(StringReader var1) throws CommandSyntaxException {
      int ☃ = ☃.getCursor();

      while(☃.canRead() && ☃.peek() != ' ') {
         ☃.skip();
      }

      String ☃x = ☃.getString().substring(☃, ☃.getCursor());
      ScoreCriteria ☃xx = ScoreCriteria.func_197911_a(☃x);
      if (☃xx == null) {
         ☃.setCursor(☃);
         throw field_197164_a.create(☃x);
      } else {
         return ☃xx;
      }
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      List<String> ☃ = Lists.newArrayList(ScoreCriteria.field_96643_a.keySet());

      for(StatType<?> ☃x : IRegistry.field_212634_w) {
         for(Object ☃xx : ☃x.func_199080_a()) {
            String ☃xxx = this.func_199815_a(☃x, ☃xx);
            ☃.add(☃xxx);
         }
      }

      return ISuggestionProvider.func_197005_b(☃, ☃);
   }

   public <T> String func_199815_a(StatType<T> var1, Object var2) {
      return Stat.func_197918_a(☃, (T)☃);
   }

   @Override
   public Collection<String> getExamples() {
      return field_201318_b;
   }
}
