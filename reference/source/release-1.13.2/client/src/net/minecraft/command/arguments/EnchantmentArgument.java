package net.minecraft.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.text.TextComponentTranslation;

public class EnchantmentArgument implements ArgumentType<Enchantment> {
   private static final Collection<String> field_201947_b = Arrays.asList("unbreaking", "silk_touch");
   public static final DynamicCommandExceptionType field_201946_a = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("enchantment.unknown", var0)
   );

   public static EnchantmentArgument func_201945_a() {
      return new EnchantmentArgument();
   }

   public static Enchantment func_201944_a(CommandContext<CommandSource> var0, String var1) {
      return ☃.getArgument(☃, Enchantment.class);
   }

   public Enchantment parse(StringReader var1) throws CommandSyntaxException {
      ResourceLocation ☃ = ResourceLocation.func_195826_a(☃);
      Enchantment ☃x = IRegistry.field_212628_q.func_212608_b(☃);
      if (☃x == null) {
         throw field_201946_a.create(☃);
      } else {
         return ☃x;
      }
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      return ISuggestionProvider.func_197014_a(IRegistry.field_212628_q.func_148742_b(), ☃);
   }

   @Override
   public Collection<String> getExamples() {
      return field_201947_b;
   }
}
