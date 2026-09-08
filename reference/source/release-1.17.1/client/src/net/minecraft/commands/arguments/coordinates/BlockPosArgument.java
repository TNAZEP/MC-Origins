package net.minecraft.commands.arguments.coordinates;

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
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

public class BlockPosArgument implements ArgumentType<Coordinates> {
   private static final Collection<String> EXAMPLES = Arrays.asList("0 0 0", "~ ~ ~", "^ ^ ^", "^1 ^ ^-5", "~0.5 ~1 ~-5");
   public static final SimpleCommandExceptionType ERROR_NOT_LOADED = new SimpleCommandExceptionType(new TranslatableComponent("argument.pos.unloaded"));
   public static final SimpleCommandExceptionType ERROR_OUT_OF_WORLD = new SimpleCommandExceptionType(new TranslatableComponent("argument.pos.outofworld"));
   public static final SimpleCommandExceptionType ERROR_OUT_OF_BOUNDS = new SimpleCommandExceptionType(new TranslatableComponent("argument.pos.outofbounds"));

   public static BlockPosArgument blockPos() {
      return new BlockPosArgument();
   }

   public static BlockPos getLoadedBlockPos(CommandContext<CommandSourceStack> var0, String var1) throws CommandSyntaxException {
      BlockPos â˜ƒ = â˜ƒ.<Coordinates>getArgument(â˜ƒ, Coordinates.class).getBlockPos(â˜ƒ.getSource());
      if (!â˜ƒ.getSource().getLevel().hasChunkAt(â˜ƒ)) {
         throw ERROR_NOT_LOADED.create();
      } else if (!â˜ƒ.getSource().getLevel().isInWorldBounds(â˜ƒ)) {
         throw ERROR_OUT_OF_WORLD.create();
      } else {
         return â˜ƒ;
      }
   }

   public static BlockPos getSpawnablePos(CommandContext<CommandSourceStack> var0, String var1) throws CommandSyntaxException {
      BlockPos â˜ƒ = â˜ƒ.<Coordinates>getArgument(â˜ƒ, Coordinates.class).getBlockPos(â˜ƒ.getSource());
      if (!Level.isInSpawnableBounds(â˜ƒ)) {
         throw ERROR_OUT_OF_BOUNDS.create();
      } else {
         return â˜ƒ;
      }
   }

   public Coordinates parse(StringReader var1) throws CommandSyntaxException {
      return (Coordinates)(â˜ƒ.canRead() && â˜ƒ.peek() == '^' ? LocalCoordinates.parse(â˜ƒ) : WorldCoordinates.parseInt(â˜ƒ));
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      if (!(â˜ƒ.getSource() instanceof SharedSuggestionProvider)) {
         return Suggestions.empty();
      } else {
         String â˜ƒx = â˜ƒ.getRemaining();
         Collection<SharedSuggestionProvider.TextCoordinates> â˜ƒ;
         if (!â˜ƒx.isEmpty() && â˜ƒx.charAt(0) == '^') {
            â˜ƒ = Collections.singleton(SharedSuggestionProvider.TextCoordinates.DEFAULT_LOCAL);
         } else {
            â˜ƒ = ((SharedSuggestionProvider)â˜ƒ.getSource()).getRelevantCoordinates();
         }

         return SharedSuggestionProvider.suggestCoordinates(â˜ƒx, â˜ƒ, â˜ƒ, Commands.createValidator(this::parse));
      }
   }

   @Override
   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
