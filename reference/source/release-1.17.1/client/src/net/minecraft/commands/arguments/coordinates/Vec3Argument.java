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
import net.minecraft.world.phys.Vec3;

public class Vec3Argument implements ArgumentType<Coordinates> {
   private static final Collection<String> EXAMPLES = Arrays.asList("0 0 0", "~ ~ ~", "^ ^ ^", "^1 ^ ^-5", "0.1 -0.5 .9", "~0.5 ~1 ~-5");
   public static final SimpleCommandExceptionType ERROR_NOT_COMPLETE = new SimpleCommandExceptionType(new TranslatableComponent("argument.pos3d.incomplete"));
   public static final SimpleCommandExceptionType ERROR_MIXED_TYPE = new SimpleCommandExceptionType(new TranslatableComponent("argument.pos.mixed"));
   private final boolean centerCorrect;

   public Vec3Argument(boolean var1) {
      this.centerCorrect = â˜ƒ;
   }

   public static Vec3Argument vec3() {
      return new Vec3Argument(true);
   }

   public static Vec3Argument vec3(boolean var0) {
      return new Vec3Argument(â˜ƒ);
   }

   public static Vec3 getVec3(CommandContext<CommandSourceStack> var0, String var1) {
      return â˜ƒ.<Coordinates>getArgument(â˜ƒ, Coordinates.class).getPosition(â˜ƒ.getSource());
   }

   public static Coordinates getCoordinates(CommandContext<CommandSourceStack> var0, String var1) {
      return â˜ƒ.getArgument(â˜ƒ, Coordinates.class);
   }

   public Coordinates parse(StringReader var1) throws CommandSyntaxException {
      return (Coordinates)(â˜ƒ.canRead() && â˜ƒ.peek() == '^' ? LocalCoordinates.parse(â˜ƒ) : WorldCoordinates.parseDouble(â˜ƒ, this.centerCorrect));
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

         return SharedSuggestionProvider.suggestCoordinates(â˜ƒx, â˜ƒ, â˜ƒ, Commands.createValidator(this::parse));
      }
   }

   @Override
   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
