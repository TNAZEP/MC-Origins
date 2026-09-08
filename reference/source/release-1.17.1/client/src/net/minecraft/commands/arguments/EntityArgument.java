package net.minecraft.commands.arguments;

import com.google.common.collect.Iterables;
import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.commands.synchronization.ArgumentSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class EntityArgument implements ArgumentType<EntitySelector> {
   private static final Collection<String> EXAMPLES = Arrays.asList("Player", "0123", "@e", "@e[type=foo]", "dd12be42-52a9-4a91-a8a1-11c01849e498");
   public static final SimpleCommandExceptionType ERROR_NOT_SINGLE_ENTITY = new SimpleCommandExceptionType(new TranslatableComponent("argument.entity.toomany"));
   public static final SimpleCommandExceptionType ERROR_NOT_SINGLE_PLAYER = new SimpleCommandExceptionType(new TranslatableComponent("argument.player.toomany"));
   public static final SimpleCommandExceptionType ERROR_ONLY_PLAYERS_ALLOWED = new SimpleCommandExceptionType(
      new TranslatableComponent("argument.player.entities")
   );
   public static final SimpleCommandExceptionType NO_ENTITIES_FOUND = new SimpleCommandExceptionType(
      new TranslatableComponent("argument.entity.notfound.entity")
   );
   public static final SimpleCommandExceptionType NO_PLAYERS_FOUND = new SimpleCommandExceptionType(
      new TranslatableComponent("argument.entity.notfound.player")
   );
   public static final SimpleCommandExceptionType ERROR_SELECTORS_NOT_ALLOWED = new SimpleCommandExceptionType(
      new TranslatableComponent("argument.entity.selector.not_allowed")
   );
   private static final byte FLAG_SINGLE = 1;
   private static final byte FLAG_PLAYERS_ONLY = 2;
   final boolean single;
   final boolean playersOnly;

   protected EntityArgument(boolean var1, boolean var2) {
      this.single = â˜ƒ;
      this.playersOnly = â˜ƒ;
   }

   public static EntityArgument entity() {
      return new EntityArgument(true, false);
   }

   public static Entity getEntity(CommandContext<CommandSourceStack> var0, String var1) throws CommandSyntaxException {
      return â˜ƒ.<EntitySelector>getArgument(â˜ƒ, EntitySelector.class).findSingleEntity(â˜ƒ.getSource());
   }

   public static EntityArgument entities() {
      return new EntityArgument(false, false);
   }

   public static Collection<? extends Entity> getEntities(CommandContext<CommandSourceStack> var0, String var1) throws CommandSyntaxException {
      Collection<? extends Entity> â˜ƒ = getOptionalEntities(â˜ƒ, â˜ƒ);
      if (â˜ƒ.isEmpty()) {
         throw NO_ENTITIES_FOUND.create();
      } else {
         return â˜ƒ;
      }
   }

   public static Collection<? extends Entity> getOptionalEntities(CommandContext<CommandSourceStack> var0, String var1) throws CommandSyntaxException {
      return â˜ƒ.<EntitySelector>getArgument(â˜ƒ, EntitySelector.class).findEntities(â˜ƒ.getSource());
   }

   public static Collection<ServerPlayer> getOptionalPlayers(CommandContext<CommandSourceStack> var0, String var1) throws CommandSyntaxException {
      return â˜ƒ.<EntitySelector>getArgument(â˜ƒ, EntitySelector.class).findPlayers(â˜ƒ.getSource());
   }

   public static EntityArgument player() {
      return new EntityArgument(true, true);
   }

   public static ServerPlayer getPlayer(CommandContext<CommandSourceStack> var0, String var1) throws CommandSyntaxException {
      return â˜ƒ.<EntitySelector>getArgument(â˜ƒ, EntitySelector.class).findSinglePlayer(â˜ƒ.getSource());
   }

   public static EntityArgument players() {
      return new EntityArgument(false, true);
   }

   public static Collection<ServerPlayer> getPlayers(CommandContext<CommandSourceStack> var0, String var1) throws CommandSyntaxException {
      List<ServerPlayer> â˜ƒ = â˜ƒ.<EntitySelector>getArgument(â˜ƒ, EntitySelector.class).findPlayers(â˜ƒ.getSource());
      if (â˜ƒ.isEmpty()) {
         throw NO_PLAYERS_FOUND.create();
      } else {
         return â˜ƒ;
      }
   }

   public EntitySelector parse(StringReader var1) throws CommandSyntaxException {
      int â˜ƒ = 0;
      EntitySelectorParser â˜ƒx = new EntitySelectorParser(â˜ƒ);
      EntitySelector â˜ƒxx = â˜ƒx.parse();
      if (â˜ƒxx.getMaxResults() > 1 && this.single) {
         if (this.playersOnly) {
            â˜ƒ.setCursor(0);
            throw ERROR_NOT_SINGLE_PLAYER.createWithContext(â˜ƒ);
         } else {
            â˜ƒ.setCursor(0);
            throw ERROR_NOT_SINGLE_ENTITY.createWithContext(â˜ƒ);
         }
      } else if (â˜ƒxx.includesEntities() && this.playersOnly && !â˜ƒxx.isSelfSelector()) {
         â˜ƒ.setCursor(0);
         throw ERROR_ONLY_PLAYERS_ALLOWED.createWithContext(â˜ƒ);
      } else {
         return â˜ƒxx;
      }
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      if (â˜ƒ.getSource() instanceof SharedSuggestionProvider) {
         StringReader â˜ƒ = new StringReader(â˜ƒ.getInput());
         â˜ƒ.setCursor(â˜ƒ.getStart());
         SharedSuggestionProvider â˜ƒx = (SharedSuggestionProvider)â˜ƒ.getSource();
         EntitySelectorParser â˜ƒxx = new EntitySelectorParser(â˜ƒ, â˜ƒx.hasPermission(2));

         try {
            â˜ƒxx.parse();
         } catch (CommandSyntaxException var7) {
         }

         return â˜ƒxx.fillSuggestions(â˜ƒ, var2x -> {
            Collection<String> â˜ƒ = â˜ƒ.getOnlinePlayerNames();
            Iterable<String> â˜ƒx = (Iterable<String>)(this.playersOnly ? â˜ƒ : Iterables.concat(â˜ƒ, â˜ƒ.getSelectedEntities()));
            SharedSuggestionProvider.suggest(â˜ƒx, var2x);
         });
      } else {
         return Suggestions.empty();
      }
   }

   @Override
   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   public static class Serializer implements ArgumentSerializer<EntityArgument> {
      public void serializeToNetwork(EntityArgument var1, FriendlyByteBuf var2) {
         byte â˜ƒ = 0;
         if (â˜ƒ.single) {
            â˜ƒ = (byte)(â˜ƒ | 1);
         }

         if (â˜ƒ.playersOnly) {
            â˜ƒ = (byte)(â˜ƒ | 2);
         }

         â˜ƒ.writeByte(â˜ƒ);
      }

      public EntityArgument deserializeFromNetwork(FriendlyByteBuf var1) {
         byte â˜ƒ = â˜ƒ.readByte();
         return new EntityArgument((â˜ƒ & 1) != 0, (â˜ƒ & 2) != 0);
      }

      public void serializeToJson(EntityArgument var1, JsonObject var2) {
         â˜ƒ.addProperty("amount", â˜ƒ.single ? "single" : "multiple");
         â˜ƒ.addProperty("type", â˜ƒ.playersOnly ? "players" : "entities");
      }
   }
}
