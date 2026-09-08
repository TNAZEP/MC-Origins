package net.minecraft.commands.arguments;

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
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class ObjectiveCriteriaArgument implements ArgumentType<ObjectiveCriteria> {
   private static final Collection<String> EXAMPLES = Arrays.asList("foo", "foo.bar.baz", "minecraft:foo");
   public static final DynamicCommandExceptionType ERROR_INVALID_VALUE = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("argument.criteria.invalid", var0)
   );

   private ObjectiveCriteriaArgument() {
   }

   public static ObjectiveCriteriaArgument criteria() {
      return new ObjectiveCriteriaArgument();
   }

   public static ObjectiveCriteria getCriteria(CommandContext<CommandSourceStack> var0, String var1) {
      return â˜ƒ.getArgument(â˜ƒ, ObjectiveCriteria.class);
   }

   public ObjectiveCriteria parse(StringReader var1) throws CommandSyntaxException {
      int â˜ƒ = â˜ƒ.getCursor();

      while(â˜ƒ.canRead() && â˜ƒ.peek() != ' ') {
         â˜ƒ.skip();
      }

      String â˜ƒx = â˜ƒ.getString().substring(â˜ƒ, â˜ƒ.getCursor());
      return (ObjectiveCriteria)ObjectiveCriteria.byName(â˜ƒx).orElseThrow(() -> {
         â˜ƒ.setCursor(â˜ƒ);
         return ERROR_INVALID_VALUE.create(â˜ƒ);
      });
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      List<String> â˜ƒ = Lists.newArrayList(ObjectiveCriteria.getCustomCriteriaNames());

      for(StatType<?> â˜ƒx : Registry.STAT_TYPE) {
         for(Object â˜ƒxx : â˜ƒx.getRegistry()) {
            String â˜ƒxxx = this.getName(â˜ƒx, â˜ƒxx);
            â˜ƒ.add(â˜ƒxxx);
         }
      }

      return SharedSuggestionProvider.suggest(â˜ƒ, â˜ƒ);
   }

   public <T> String getName(StatType<T> var1, Object var2) {
      return Stat.buildName(â˜ƒ, (T)â˜ƒ);
   }

   @Override
   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
