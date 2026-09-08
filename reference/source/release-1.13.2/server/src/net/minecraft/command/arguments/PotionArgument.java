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
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.text.TextComponentTranslation;

public class PotionArgument implements ArgumentType<Potion> {
   private static final Collection<String> field_201314_b = Arrays.asList("spooky", "effect");
   public static final DynamicCommandExceptionType field_197128_a = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("effect.effectNotFound", var0)
   );

   public static PotionArgument func_197126_a() {
      return new PotionArgument();
   }

   public static Potion func_197125_a(CommandContext<CommandSource> var0, String var1) throws CommandSyntaxException {
      return ☃.getArgument(☃, Potion.class);
   }

   public Potion parse(StringReader var1) throws CommandSyntaxException {
      ResourceLocation ☃ = ResourceLocation.func_195826_a(☃);
      Potion ☃x = IRegistry.field_212631_t.func_212608_b(☃);
      if (☃x == null) {
         throw field_197128_a.create(☃);
      } else {
         return ☃x;
      }
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      return ISuggestionProvider.func_197014_a(IRegistry.field_212631_t.func_148742_b(), ☃);
   }

   @Override
   public Collection<String> getExamples() {
      return field_201314_b;
   }
}
