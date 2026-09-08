package net.minecraft.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;

public class BlockStateArgument implements ArgumentType<BlockStateInput> {
   private static final Collection<String> field_201332_a = Arrays.asList("stone", "minecraft:stone", "stone[foo=bar]", "foo{bar=baz}");

   public static BlockStateArgument func_197239_a() {
      return new BlockStateArgument();
   }

   public BlockStateInput parse(StringReader var1) throws CommandSyntaxException {
      BlockStateParser ☃ = new BlockStateParser(☃, false).func_197243_a(true);
      return new BlockStateInput(☃.func_197249_b(), ☃.func_197254_a().keySet(), ☃.func_197241_c());
   }

   public static BlockStateInput func_197238_a(CommandContext<CommandSource> var0, String var1) {
      return ☃.getArgument(☃, BlockStateInput.class);
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      StringReader ☃ = new StringReader(☃.getInput());
      ☃.setCursor(☃.getStart());
      BlockStateParser ☃x = new BlockStateParser(☃, false);

      try {
         ☃x.func_197243_a(true);
      } catch (CommandSyntaxException var6) {
      }

      return ☃x.func_197245_a(☃);
   }

   @Override
   public Collection<String> getExamples() {
      return field_201332_a;
   }
}
