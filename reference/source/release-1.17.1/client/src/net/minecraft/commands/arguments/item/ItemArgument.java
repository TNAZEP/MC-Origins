package net.minecraft.commands.arguments.item;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.tags.ItemTags;

public class ItemArgument implements ArgumentType<ItemInput> {
   private static final Collection<String> EXAMPLES = Arrays.asList("stick", "minecraft:stick", "stick{foo=bar}");

   public static ItemArgument item() {
      return new ItemArgument();
   }

   public ItemInput parse(StringReader var1) throws CommandSyntaxException {
      ItemParser â˜ƒ = new ItemParser(â˜ƒ, false).parse();
      return new ItemInput(â˜ƒ.getItem(), â˜ƒ.getNbt());
   }

   public static <S> ItemInput getItem(CommandContext<S> var0, String var1) {
      return â˜ƒ.getArgument(â˜ƒ, ItemInput.class);
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      StringReader â˜ƒ = new StringReader(â˜ƒ.getInput());
      â˜ƒ.setCursor(â˜ƒ.getStart());
      ItemParser â˜ƒx = new ItemParser(â˜ƒ, false);

      try {
         â˜ƒx.parse();
      } catch (CommandSyntaxException var6) {
      }

      return â˜ƒx.fillSuggestions(â˜ƒ, ItemTags.getAllTags());
   }

   @Override
   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
