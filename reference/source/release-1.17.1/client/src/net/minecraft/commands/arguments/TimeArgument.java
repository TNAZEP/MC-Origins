package net.minecraft.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.TranslatableComponent;

public class TimeArgument implements ArgumentType<Integer> {
   private static final Collection<String> EXAMPLES = Arrays.asList("0d", "0s", "0t", "0");
   private static final SimpleCommandExceptionType ERROR_INVALID_UNIT = new SimpleCommandExceptionType(new TranslatableComponent("argument.time.invalid_unit"));
   private static final DynamicCommandExceptionType ERROR_INVALID_TICK_COUNT = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("argument.time.invalid_tick_count", var0)
   );
   private static final Object2IntMap<String> UNITS = new Object2IntOpenHashMap();

   public static TimeArgument time() {
      return new TimeArgument();
   }

   public Integer parse(StringReader var1) throws CommandSyntaxException {
      float â˜ƒ = â˜ƒ.readFloat();
      String â˜ƒx = â˜ƒ.readUnquotedString();
      int â˜ƒxx = UNITS.getOrDefault(â˜ƒx, 0);
      if (â˜ƒxx == 0) {
         throw ERROR_INVALID_UNIT.create();
      } else {
         int â˜ƒ = Math.round(â˜ƒ * (float)â˜ƒxx);
         if (â˜ƒ < 0) {
            throw ERROR_INVALID_TICK_COUNT.create(â˜ƒ);
         } else {
            return â˜ƒ;
         }
      }
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      StringReader â˜ƒ = new StringReader(â˜ƒ.getRemaining());

      try {
         â˜ƒ.readFloat();
      } catch (CommandSyntaxException var5) {
         return â˜ƒ.buildFuture();
      }

      return SharedSuggestionProvider.suggest(UNITS.keySet(), â˜ƒ.createOffset(â˜ƒ.getStart() + â˜ƒ.getCursor()));
   }

   @Override
   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   static {
      UNITS.put("d", 24000);
      UNITS.put("s", 20);
      UNITS.put("t", 1);
      UNITS.put("", 1);
   }
}
