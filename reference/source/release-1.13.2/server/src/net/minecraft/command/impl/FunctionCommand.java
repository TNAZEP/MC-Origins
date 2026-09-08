package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import net.minecraft.advancements.FunctionManager;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.FunctionObject;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.FunctionArgument;
import net.minecraft.util.text.TextComponentTranslation;

public class FunctionCommand {
   public static final SuggestionProvider<CommandSource> field_198481_a = (var0, var1) -> {
      FunctionManager ☃ = var0.getSource().func_197028_i().func_193030_aL();
      ISuggestionProvider.func_197006_a(☃.func_200000_g().func_199908_a(), var1, "#");
      return ISuggestionProvider.func_197014_a(☃.func_193066_d().keySet(), var1);
   };

   public static void func_198476_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("function")
            .requires(var0x -> var0x.func_197034_c(2))
            .then(
               Commands.func_197056_a("name", FunctionArgument.func_200021_a())
                  .suggests(field_198481_a)
                  .executes(var0x -> func_200025_a(var0x.getSource(), FunctionArgument.func_200022_a(var0x, "name")))
            )
      );
   }

   private static int func_200025_a(CommandSource var0, Collection<FunctionObject> var1) {
      int ☃ = 0;

      for(FunctionObject ☃x : ☃) {
         ☃ += ☃.func_197028_i().func_193030_aL().func_195447_a(☃x, ☃.func_197031_a().func_197026_b(2));
      }

      if (☃.size() == 1) {
         ☃.func_197030_a(new TextComponentTranslation("commands.function.success.single", ☃, ((FunctionObject)☃.iterator().next()).func_197001_a()), true);
      } else {
         ☃.func_197030_a(new TextComponentTranslation("commands.function.success.multiple", ☃, ☃.size()), true);
      }

      return ☃;
   }
}
