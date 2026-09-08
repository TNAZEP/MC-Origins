package net.minecraft.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class DimensionArgument implements ArgumentType<ResourceLocation> {
   private static final Collection<String> EXAMPLES = (Collection<String>)Stream.of(Level.OVERWORLD, Level.NETHER)
      .map(var0 -> var0.location().toString())
      .collect(Collectors.toList());
   private static final DynamicCommandExceptionType ERROR_INVALID_VALUE = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("argument.dimension.invalid", var0)
   );

   public ResourceLocation parse(StringReader var1) throws CommandSyntaxException {
      return ResourceLocation.read(â˜ƒ);
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      return â˜ƒ.getSource() instanceof SharedSuggestionProvider
         ? SharedSuggestionProvider.suggestResource(((SharedSuggestionProvider)â˜ƒ.getSource()).levels().stream().map(ResourceKey::location), â˜ƒ)
         : Suggestions.empty();
   }

   @Override
   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   public static DimensionArgument dimension() {
      return new DimensionArgument();
   }

   public static ServerLevel getDimension(CommandContext<CommandSourceStack> var0, String var1) throws CommandSyntaxException {
      ResourceLocation â˜ƒ = â˜ƒ.getArgument(â˜ƒ, ResourceLocation.class);
      ResourceKey<Level> â˜ƒx = ResourceKey.create(Registry.DIMENSION_REGISTRY, â˜ƒ);
      ServerLevel â˜ƒxx = â˜ƒ.getSource().getServer().getLevel(â˜ƒx);
      if (â˜ƒxx == null) {
         throw ERROR_INVALID_VALUE.create(â˜ƒ);
      } else {
         return â˜ƒxx;
      }
   }
}
