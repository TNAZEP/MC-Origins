package net.minecraft.commands.arguments;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.commands.synchronization.ArgumentSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;

public class ScoreHolderArgument implements ArgumentType<ScoreHolderArgument.Result> {
   public static final SuggestionProvider<CommandSourceStack> SUGGEST_SCORE_HOLDERS = (var0, var1) -> {
      StringReader â˜ƒ = new StringReader(var1.getInput());
      â˜ƒ.setCursor(var1.getStart());
      EntitySelectorParser â˜ƒx = new EntitySelectorParser(â˜ƒ);

      try {
         â˜ƒx.parse();
      } catch (CommandSyntaxException var5) {
      }

      return â˜ƒx.fillSuggestions(var1, var1x -> SharedSuggestionProvider.suggest(var0.getSource().getOnlinePlayerNames(), var1x));
   };
   private static final Collection<String> EXAMPLES = Arrays.asList("Player", "0123", "*", "@e");
   private static final SimpleCommandExceptionType ERROR_NO_RESULTS = new SimpleCommandExceptionType(new TranslatableComponent("argument.scoreHolder.empty"));
   private static final byte FLAG_MULTIPLE = 1;
   final boolean multiple;

   public ScoreHolderArgument(boolean var1) {
      this.multiple = â˜ƒ;
   }

   public static String getName(CommandContext<CommandSourceStack> var0, String var1) throws CommandSyntaxException {
      return (String)getNames(â˜ƒ, â˜ƒ).iterator().next();
   }

   public static Collection<String> getNames(CommandContext<CommandSourceStack> var0, String var1) throws CommandSyntaxException {
      return getNames(â˜ƒ, â˜ƒ, Collections::emptyList);
   }

   public static Collection<String> getNamesWithDefaultWildcard(CommandContext<CommandSourceStack> var0, String var1) throws CommandSyntaxException {
      return getNames(â˜ƒ, â˜ƒ, â˜ƒ.getSource().getServer().getScoreboard()::getTrackedPlayers);
   }

   public static Collection<String> getNames(CommandContext<CommandSourceStack> var0, String var1, Supplier<Collection<String>> var2) throws CommandSyntaxException {
      Collection<String> â˜ƒ = â˜ƒ.<ScoreHolderArgument.Result>getArgument(â˜ƒ, ScoreHolderArgument.Result.class).getNames(â˜ƒ.getSource(), â˜ƒ);
      if (â˜ƒ.isEmpty()) {
         throw EntityArgument.NO_ENTITIES_FOUND.create();
      } else {
         return â˜ƒ;
      }
   }

   public static ScoreHolderArgument scoreHolder() {
      return new ScoreHolderArgument(false);
   }

   public static ScoreHolderArgument scoreHolders() {
      return new ScoreHolderArgument(true);
   }

   public ScoreHolderArgument.Result parse(StringReader var1) throws CommandSyntaxException {
      if (â˜ƒ.canRead() && â˜ƒ.peek() == '@') {
         EntitySelectorParser â˜ƒ = new EntitySelectorParser(â˜ƒ);
         EntitySelector â˜ƒx = â˜ƒ.parse();
         if (!this.multiple && â˜ƒx.getMaxResults() > 1) {
            throw EntityArgument.ERROR_NOT_SINGLE_ENTITY.create();
         } else {
            return new ScoreHolderArgument.SelectorResult(â˜ƒx);
         }
      } else {
         int â˜ƒ = â˜ƒ.getCursor();

         while(â˜ƒ.canRead() && â˜ƒ.peek() != ' ') {
            â˜ƒ.skip();
         }

         String â˜ƒx = â˜ƒ.getString().substring(â˜ƒ, â˜ƒ.getCursor());
         if (â˜ƒx.equals("*")) {
            return (var0, var1x) -> {
               Collection<String> â˜ƒ = (Collection)var1x.get();
               if (â˜ƒ.isEmpty()) {
                  throw ERROR_NO_RESULTS.create();
               } else {
                  return â˜ƒ;
               }
            };
         } else {
            Collection<String> â˜ƒx = Collections.singleton(â˜ƒx);
            return (var1x, var2x) -> â˜ƒ;
         }
      }
   }

   @Override
   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   @FunctionalInterface
   public interface Result {
      Collection<String> getNames(CommandSourceStack var1, Supplier<Collection<String>> var2) throws CommandSyntaxException;
   }

   public static class SelectorResult implements ScoreHolderArgument.Result {
      private final EntitySelector selector;

      public SelectorResult(EntitySelector var1) {
         this.selector = â˜ƒ;
      }

      @Override
      public Collection<String> getNames(CommandSourceStack var1, Supplier<Collection<String>> var2) throws CommandSyntaxException {
         List<? extends Entity> â˜ƒ = this.selector.findEntities(â˜ƒ);
         if (â˜ƒ.isEmpty()) {
            throw EntityArgument.NO_ENTITIES_FOUND.create();
         } else {
            List<String> â˜ƒ = Lists.newArrayList();

            for(Entity â˜ƒx : â˜ƒ) {
               â˜ƒ.add(â˜ƒx.getScoreboardName());
            }

            return â˜ƒ;
         }
      }
   }

   public static class Serializer implements ArgumentSerializer<ScoreHolderArgument> {
      public void serializeToNetwork(ScoreHolderArgument var1, FriendlyByteBuf var2) {
         byte â˜ƒ = 0;
         if (â˜ƒ.multiple) {
            â˜ƒ = (byte)(â˜ƒ | 1);
         }

         â˜ƒ.writeByte(â˜ƒ);
      }

      public ScoreHolderArgument deserializeFromNetwork(FriendlyByteBuf var1) {
         byte â˜ƒ = â˜ƒ.readByte();
         boolean â˜ƒx = (â˜ƒ & 1) != 0;
         return new ScoreHolderArgument(â˜ƒx);
      }

      public void serializeToJson(ScoreHolderArgument var1, JsonObject var2) {
         â˜ƒ.addProperty("amount", â˜ƒ.multiple ? "multiple" : "single");
      }
   }
}
