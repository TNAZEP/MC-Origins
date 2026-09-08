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
import net.minecraft.world.WorldServer;

public class BlockPosArgument implements ArgumentType<ILocationArgument> {
   private static final Collection<String> field_201333_c = Arrays.asList("0 0 0", "~ ~ ~", "^ ^ ^", "^1 ^ ^-5", "~0.5 ~1 ~-5");
   public static final SimpleCommandExceptionType field_197278_b = new SimpleCommandExceptionType(new TextComponentTranslation("argument.pos.unloaded"));
   public static final SimpleCommandExceptionType field_197279_c = new SimpleCommandExceptionType(new TextComponentTranslation("argument.pos.outofworld"));

   public static BlockPosArgument func_197276_a() {
      return new BlockPosArgument();
   }

   public static BlockPos func_197273_a(CommandContext<CommandSource> var0, String var1) throws CommandSyntaxException {
      BlockPos ☃ = ☃.<ILocationArgument>getArgument(☃, ILocationArgument.class).func_197280_c(☃.getSource());
      if (!☃.getSource().func_197023_e().func_175667_e(☃)) {
         throw field_197278_b.create();
      } else {
         ☃.getSource().func_197023_e();
         if (!WorldServer.func_175701_a(☃)) {
            throw field_197279_c.create();
         } else {
            return ☃;
         }
      }
   }

   public static BlockPos func_197274_b(CommandContext<CommandSource> var0, String var1) throws CommandSyntaxException {
      return ☃.<ILocationArgument>getArgument(☃, ILocationArgument.class).func_197280_c(☃.getSource());
   }

   public ILocationArgument parse(StringReader var1) throws CommandSyntaxException {
      return (ILocationArgument)(☃.canRead() && ☃.peek() == '^' ? LocalLocationArgument.func_200142_a(☃) : LocationInput.func_200148_a(☃));
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

         return ISuggestionProvider.func_209000_a(☃x, ☃, ☃, Commands.func_212590_a(this::parse));
      }
   }

   @Override
   public Collection<String> getExamples() {
      return field_201333_c;
   }
}
