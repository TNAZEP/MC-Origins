package net.minecraft.commands.arguments;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;

public class GameProfileArgument implements ArgumentType<GameProfileArgument.Result> {
   private static final Collection<String> EXAMPLES = Arrays.asList("Player", "0123", "dd12be42-52a9-4a91-a8a1-11c01849e498", "@e");
   public static final SimpleCommandExceptionType ERROR_UNKNOWN_PLAYER = new SimpleCommandExceptionType(new TranslatableComponent("argument.player.unknown"));

   public static Collection<GameProfile> getGameProfiles(CommandContext<CommandSourceStack> var0, String var1) throws CommandSyntaxException {
      return â˜ƒ.<GameProfileArgument.Result>getArgument(â˜ƒ, GameProfileArgument.Result.class).getNames(â˜ƒ.getSource());
   }

   public static GameProfileArgument gameProfile() {
      return new GameProfileArgument();
   }

   public GameProfileArgument.Result parse(StringReader var1) throws CommandSyntaxException {
      if (â˜ƒ.canRead() && â˜ƒ.peek() == '@') {
         EntitySelectorParser â˜ƒ = new EntitySelectorParser(â˜ƒ);
         EntitySelector â˜ƒx = â˜ƒ.parse();
         if (â˜ƒx.includesEntities()) {
            throw EntityArgument.ERROR_ONLY_PLAYERS_ALLOWED.create();
         } else {
            return new GameProfileArgument.SelectorResult(â˜ƒx);
         }
      } else {
         int â˜ƒ = â˜ƒ.getCursor();

         while(â˜ƒ.canRead() && â˜ƒ.peek() != ' ') {
            â˜ƒ.skip();
         }

         String â˜ƒx = â˜ƒ.getString().substring(â˜ƒ, â˜ƒ.getCursor());
         return var1x -> {
            Optional<GameProfile> â˜ƒ = var1x.getServer().getProfileCache().get(â˜ƒ);
            return Collections.singleton((GameProfile)â˜ƒ.orElseThrow(ERROR_UNKNOWN_PLAYER::create));
         };
      }
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      if (â˜ƒ.getSource() instanceof SharedSuggestionProvider) {
         StringReader â˜ƒ = new StringReader(â˜ƒ.getInput());
         â˜ƒ.setCursor(â˜ƒ.getStart());
         EntitySelectorParser â˜ƒx = new EntitySelectorParser(â˜ƒ);

         try {
            â˜ƒx.parse();
         } catch (CommandSyntaxException var6) {
         }

         return â˜ƒx.fillSuggestions(â˜ƒ, var1x -> SharedSuggestionProvider.suggest(((SharedSuggestionProvider)â˜ƒ.getSource()).getOnlinePlayerNames(), var1x));
      } else {
         return Suggestions.empty();
      }
   }

   @Override
   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   @FunctionalInterface
   public interface Result {
      Collection<GameProfile> getNames(CommandSourceStack var1) throws CommandSyntaxException;
   }

   public static class SelectorResult implements GameProfileArgument.Result {
      private final EntitySelector selector;

      public SelectorResult(EntitySelector var1) {
         this.selector = â˜ƒ;
      }

      @Override
      public Collection<GameProfile> getNames(CommandSourceStack var1) throws CommandSyntaxException {
         List<ServerPlayer> â˜ƒ = this.selector.findPlayers(â˜ƒ);
         if (â˜ƒ.isEmpty()) {
            throw EntityArgument.NO_PLAYERS_FOUND.create();
         } else {
            List<GameProfile> â˜ƒ = Lists.<GameProfile>newArrayList();

            for(ServerPlayer â˜ƒx : â˜ƒ) {
               â˜ƒ.add(â˜ƒx.getGameProfile());
            }

            return â˜ƒ;
         }
      }
   }
}
