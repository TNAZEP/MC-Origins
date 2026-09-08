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
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;

public class Vec2Argument implements ArgumentType<ILocationArgument> {
   private static final Collection<String> field_201336_b = Arrays.asList("0 0", "~ ~", "0.1 -0.5", "~1 ~-2");
   public static final SimpleCommandExceptionType field_197298_a = new SimpleCommandExceptionType(new TextComponentTranslation("argument.pos2d.incomplete"));
   private final boolean field_197299_b;

   public Vec2Argument(boolean var1) {
      this.field_197299_b = ☃;
   }

   public static Vec2Argument func_197296_a() {
      return new Vec2Argument(true);
   }

   public static Vec2f func_197295_a(CommandContext<CommandSource> var0, String var1) throws CommandSyntaxException {
      Vec3d ☃ = ☃.<ILocationArgument>getArgument(☃, ILocationArgument.class).func_197281_a(☃.getSource());
      return new Vec2f((float)☃.field_72450_a, (float)☃.field_72449_c);
   }

   public ILocationArgument parse(StringReader var1) throws CommandSyntaxException {
      int ☃ = ☃.getCursor();
      if (!☃.canRead()) {
         throw field_197298_a.createWithContext(☃);
      } else {
         LocationPart ☃ = LocationPart.func_197308_a(☃, this.field_197299_b);
         if (☃.canRead() && ☃.peek() == ' ') {
            ☃.skip();
            LocationPart ☃x = LocationPart.func_197308_a(☃, this.field_197299_b);
            return new LocationInput(☃, new LocationPart(true, 0.0), ☃x);
         } else {
            ☃.setCursor(☃);
            throw field_197298_a.createWithContext(☃);
         }
      }
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

         return ISuggestionProvider.func_211269_a(☃x, ☃, ☃, Commands.func_212590_a(this::parse));
      }
   }

   @Override
   public Collection<String> getExamples() {
      return field_201336_b;
   }
}
