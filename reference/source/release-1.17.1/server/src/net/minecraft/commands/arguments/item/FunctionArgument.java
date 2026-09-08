package net.minecraft.commands.arguments.item;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.commands.CommandFunction;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;

public class FunctionArgument implements ArgumentType<FunctionArgument.Result> {
   private static final Collection<String> EXAMPLES = Arrays.asList("foo", "foo:bar", "#foo");
   private static final DynamicCommandExceptionType ERROR_UNKNOWN_TAG = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("arguments.function.tag.unknown", var0)
   );
   private static final DynamicCommandExceptionType ERROR_UNKNOWN_FUNCTION = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("arguments.function.unknown", var0)
   );

   public static FunctionArgument functions() {
      return new FunctionArgument();
   }

   public FunctionArgument.Result parse(StringReader var1) throws CommandSyntaxException {
      if (â˜ƒ.canRead() && â˜ƒ.peek() == '#') {
         â˜ƒ.skip();
         final ResourceLocation â˜ƒ = ResourceLocation.read(â˜ƒ);
         return new FunctionArgument.Result() {
            @Override
            public Collection<CommandFunction> create(CommandContext<CommandSourceStack> var1) throws CommandSyntaxException {
               Tag<CommandFunction> â˜ƒ = FunctionArgument.getFunctionTag(â˜ƒ, â˜ƒ);
               return â˜ƒ.getValues();
            }

            @Override
            public Pair<ResourceLocation, Either<CommandFunction, Tag<CommandFunction>>> unwrap(CommandContext<CommandSourceStack> var1) throws CommandSyntaxException {
               return Pair.of(â˜ƒ, Either.right(FunctionArgument.getFunctionTag(â˜ƒ, â˜ƒ)));
            }
         };
      } else {
         final ResourceLocation â˜ƒ = ResourceLocation.read(â˜ƒ);
         return new FunctionArgument.Result() {
            @Override
            public Collection<CommandFunction> create(CommandContext<CommandSourceStack> var1) throws CommandSyntaxException {
               return Collections.singleton(FunctionArgument.getFunction(â˜ƒ, â˜ƒ));
            }

            @Override
            public Pair<ResourceLocation, Either<CommandFunction, Tag<CommandFunction>>> unwrap(CommandContext<CommandSourceStack> var1) throws CommandSyntaxException {
               return Pair.of(â˜ƒ, Either.left(FunctionArgument.getFunction(â˜ƒ, â˜ƒ)));
            }
         };
      }
   }

   static CommandFunction getFunction(CommandContext<CommandSourceStack> var0, ResourceLocation var1) throws CommandSyntaxException {
      return (CommandFunction)â˜ƒ.getSource().getServer().getFunctions().get(â˜ƒ).orElseThrow(() -> ERROR_UNKNOWN_FUNCTION.create(â˜ƒ.toString()));
   }

   static Tag<CommandFunction> getFunctionTag(CommandContext<CommandSourceStack> var0, ResourceLocation var1) throws CommandSyntaxException {
      Tag<CommandFunction> â˜ƒ = â˜ƒ.getSource().getServer().getFunctions().getTag(â˜ƒ);
      if (â˜ƒ == null) {
         throw ERROR_UNKNOWN_TAG.create(â˜ƒ.toString());
      } else {
         return â˜ƒ;
      }
   }

   public static Collection<CommandFunction> getFunctions(CommandContext<CommandSourceStack> var0, String var1) throws CommandSyntaxException {
      return â˜ƒ.<FunctionArgument.Result>getArgument(â˜ƒ, FunctionArgument.Result.class).create(â˜ƒ);
   }

   public static Pair<ResourceLocation, Either<CommandFunction, Tag<CommandFunction>>> getFunctionOrTag(CommandContext<CommandSourceStack> var0, String var1) throws CommandSyntaxException {
      return â˜ƒ.<FunctionArgument.Result>getArgument(â˜ƒ, FunctionArgument.Result.class).unwrap(â˜ƒ);
   }

   @Override
   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   public interface Result {
      Collection<CommandFunction> create(CommandContext<CommandSourceStack> var1) throws CommandSyntaxException;

      Pair<ResourceLocation, Either<CommandFunction, Tag<CommandFunction>>> unwrap(CommandContext<CommandSourceStack> var1) throws CommandSyntaxException;
   }
}
