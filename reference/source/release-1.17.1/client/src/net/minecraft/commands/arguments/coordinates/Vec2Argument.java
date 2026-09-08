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
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class Vec2Argument implements ArgumentType<Coordinates> {
   private static final Collection<String> EXAMPLES = Arrays.asList("0 0", "~ ~", "0.1 -0.5", "~1 ~-2");
   public static final SimpleCommandExceptionType ERROR_NOT_COMPLETE = new SimpleCommandExceptionType(new TranslatableComponent("argument.pos2d.incomplete"));
   private final boolean centerCorrect;

   public Vec2Argument(boolean var1) {
      this.centerCorrect = â˜ƒ;
   }

   public static Vec2Argument vec2() {
      return new Vec2Argument(true);
   }

   public static Vec2Argument vec2(boolean var0) {
      return new Vec2Argument(â˜ƒ);
   }

   public static Vec2 getVec2(CommandContext<CommandSourceStack> var0, String var1) {
      Vec3 â˜ƒ = â˜ƒ.<Coordinates>getArgument(â˜ƒ, Coordinates.class).getPosition(â˜ƒ.getSource());
      return new Vec2((float)â˜ƒ.x, (float)â˜ƒ.z);
   }

   public Coordinates parse(StringReader var1) throws CommandSyntaxException {
      int â˜ƒ = â˜ƒ.getCursor();
      if (!â˜ƒ.canRead()) {
         throw ERROR_NOT_COMPLETE.createWithContext(â˜ƒ);
      } else {
         WorldCoordinate â˜ƒ = WorldCoordinate.parseDouble(â˜ƒ, this.centerCorrect);
         if (â˜ƒ.canRead() && â˜ƒ.peek() == ' ') {
            â˜ƒ.skip();
            WorldCoordinate â˜ƒx = WorldCoordinate.parseDouble(â˜ƒ, this.centerCorrect);
            return new WorldCoordinates(â˜ƒ, new WorldCoordinate(true, 0.0), â˜ƒx);
         } else {
            â˜ƒ.setCursor(â˜ƒ);
            throw ERROR_NOT_COMPLETE.createWithContext(â˜ƒ);
         }
      }
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
            â˜ƒ = ((SharedSuggestionProvider)â˜ƒ.getSource()).getAbsoluteCoordinates();
         }

         return SharedSuggestionProvider.suggest2DCoordinates(â˜ƒx, â˜ƒ, â˜ƒ, Commands.createValidator(this::parse));
      }
   }

   @Override
   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
