package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import net.minecraft.commands.CommandFunction;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.item.FunctionArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.ServerFunctionManager;

public class FunctionCommand {
   public static final SuggestionProvider<CommandSourceStack> SUGGEST_FUNCTION = (var0, var1) -> {
      ServerFunctionManager â˜ƒ = var0.getSource().getServer().getFunctions();
      SharedSuggestionProvider.suggestResource(â˜ƒ.getTagNames(), var1, "#");
      return SharedSuggestionProvider.suggestResource(â˜ƒ.getFunctionNames(), var1);
   };

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("function")
            .requires(var0x -> var0x.hasPermission(2))
            .then(
               Commands.argument("name", FunctionArgument.functions())
                  .suggests(SUGGEST_FUNCTION)
                  .executes(var0x -> runFunction(var0x.getSource(), FunctionArgument.getFunctions(var0x, "name")))
            )
      );
   }

   private static int runFunction(CommandSourceStack var0, Collection<CommandFunction> var1) {
      int â˜ƒ = 0;

      for(CommandFunction â˜ƒx : â˜ƒ) {
         â˜ƒ += â˜ƒ.getServer().getFunctions().execute(â˜ƒx, â˜ƒ.withSuppressedOutput().withMaximumPermission(2));
      }

      if (â˜ƒ.size() == 1) {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.function.success.single", â˜ƒ, ((CommandFunction)â˜ƒ.iterator().next()).getId()), true);
      } else {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.function.success.multiple", â˜ƒ, â˜ƒ.size()), true);
      }

      return â˜ƒ;
   }
}
