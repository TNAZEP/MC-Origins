package net.minecraft.command.arguments;

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
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;

public class Vec3Argument implements ArgumentType<ILocationArgument> {
   private static final Collection<String> field_201337_c = Arrays.asList("0 0 0", "~ ~ ~", "^ ^ ^", "^1 ^ ^-5", "0.1 -0.5 .9", "~0.5 ~1 ~-5");
   public static final SimpleCommandExceptionType field_197304_a = new SimpleCommandExceptionType(new TextComponentTranslation("argument.pos3d.incomplete"));
   public static final SimpleCommandExceptionType field_200149_b = new SimpleCommandExceptionType(new TextComponentTranslation("argument.pos.mixed"));
   private final boolean field_197305_b;

   public Vec3Argument(boolean var1) {
      this.field_197305_b = ☃;
   }

   public static Vec3Argument func_197301_a() {
      return new Vec3Argument(true);
   }

   public static Vec3Argument func_197303_a(boolean var0) {
      return new Vec3Argument(☃);
   }

   public static Vec3d func_197300_a(CommandContext<CommandSource> var0, String var1) throws CommandSyntaxException {
      return ☃.<ILocationArgument>getArgument(☃, ILocationArgument.class).func_197281_a(☃.getSource());
   }

   public static ILocationArgument func_200385_b(CommandContext<CommandSource> var0, String var1) {
      return ☃.getArgument(☃, ILocationArgument.class);
   }

   public ILocationArgument parse(StringReader var1) throws CommandSyntaxException {
      return (ILocationArgument)(☃.canRead() && ☃.peek() == '^' ? LocalLocationArgument.func_200142_a(☃) : LocationInput.func_200147_a(☃, this.field_197305_b));
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      if (!(☃.getSource() instanceof ISuggestionProvider)) {
         return Suggestions.empty();
      } else {
         String ☃x = ☃.getRemaining();
         Collection<ISuggestionProvider.Coordinates> ☃;
         if (!☃x.isEmpty() && ☃x.charAt(0) == '^') {
            ☃ = Collections.singleton(ISuggestionProvider.Coordinates.field_209004_a);
         } else {
            ☃ = ((ISuggestionProvider)☃.getSource()).func_199613_a(true);
         }

         return ISuggestionProvider.func_209000_a(☃x, ☃, ☃, Commands.func_212590_a(this::parse));
      }
   }

   @Override
   public Collection<String> getExamples() {
      return field_201337_c;
   }
}
