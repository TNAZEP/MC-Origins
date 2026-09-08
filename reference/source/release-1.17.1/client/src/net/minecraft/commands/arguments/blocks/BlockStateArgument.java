package net.minecraft.commands.arguments.blocks;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.tags.BlockTags;

public class BlockStateArgument implements ArgumentType<BlockInput> {
   private static final Collection<String> EXAMPLES = Arrays.asList("stone", "minecraft:stone", "stone[foo=bar]", "foo{bar=baz}");

   public static BlockStateArgument block() {
      return new BlockStateArgument();
   }

   public BlockInput parse(StringReader var1) throws CommandSyntaxException {
      BlockStateParser â˜ƒ = new BlockStateParser(â˜ƒ, false).parse(true);
      return new BlockInput(â˜ƒ.getState(), â˜ƒ.getProperties().keySet(), â˜ƒ.getNbt());
   }

   public static BlockInput getBlock(CommandContext<CommandSourceStack> var0, String var1) {
      return â˜ƒ.getArgument(â˜ƒ, BlockInput.class);
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      StringReader â˜ƒ = new StringReader(â˜ƒ.getInput());
      â˜ƒ.setCursor(â˜ƒ.getStart());
      BlockStateParser â˜ƒx = new BlockStateParser(â˜ƒ, false);

      try {
         â˜ƒx.parse(true);
      } catch (CommandSyntaxException var6) {
      }

      return â˜ƒx.fillSuggestions(â˜ƒ, BlockTags.getAllTags());
   }

   @Override
   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
