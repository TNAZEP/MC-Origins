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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

public class ColumnPosArgument implements ArgumentType<ILocationArgument> {
   private static final Collection<String> field_212605_b = Arrays.asList("0 0", "~ ~", "~1 ~-2", "^ ^", "^-1 ^0");
   public static final SimpleCommandExceptionType field_212604_a = new SimpleCommandExceptionType(new TextComponentTranslation("argument.pos2d.incomplete"));

   public static ColumnPosArgument func_212603_a() {
      return new ColumnPosArgument();
   }

   public static ColumnPosArgument.ColumnPos func_212602_a(CommandContext<CommandSource> var0, String var1) {
      BlockPos ☃ = ☃.<ILocationArgument>getArgument(☃, ILocationArgument.class).func_197280_c(☃.getSource());
      return new ColumnPosArgument.ColumnPos(☃.func_177958_n(), ☃.func_177952_p());
   }

   public ILocationArgument parse(StringReader var1) throws CommandSyntaxException {
      int ☃ = ☃.getCursor();
      if (!☃.canRead()) {
         throw field_212604_a.createWithContext(☃);
      } else {
         LocationPart ☃ = LocationPart.func_197307_a(☃);
         if (☃.canRead() && ☃.peek() == ' ') {
            ☃.skip();
            LocationPart ☃x = LocationPart.func_197307_a(☃);
            return new LocationInput(☃, new LocationPart(true, 0.0), ☃x);
         } else {
            ☃.setCursor(☃);
            throw field_212604_a.createWithContext(☃);
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
            ☃ = ((ISuggestionProvider)☃.getSource()).func_199613_a(false);
         }

         return ISuggestionProvider.func_211269_a(☃x, ☃, ☃, Commands.func_212590_a(this::parse));
      }
   }

   @Override
   public Collection<String> getExamples() {
      return field_212605_b;
   }

   public static class ColumnPos {
      public final int field_212600_a;
      public final int field_212601_b;

      public ColumnPos(int var1, int var2) {
         this.field_212600_a = ☃;
         this.field_212601_b = ☃;
      }

      public String toString() {
         return "[" + this.field_212600_a + ", " + this.field_212601_b + "]";
      }
   }
}
