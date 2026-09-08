package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.text.TextComponentTranslation;

public class PublishCommand {
   private static final SimpleCommandExceptionType field_198585_a = new SimpleCommandExceptionType(new TextComponentTranslation("commands.publish.failed"));
   private static final DynamicCommandExceptionType field_198586_b = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("commands.publish.alreadyPublished", var0)
   );

   public static void func_198581_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("publish")
            .requires(var0x -> var0x.func_197028_i().func_71264_H() && var0x.func_197034_c(4))
            .executes(var0x -> func_198584_a(var0x.getSource(), HttpUtil.func_76181_a()))
            .then(
               Commands.func_197056_a("port", IntegerArgumentType.integer(0, 65535))
                  .executes(var0x -> func_198584_a(var0x.getSource(), IntegerArgumentType.getInteger(var0x, "port")))
            )
      );
   }

   private static int func_198584_a(CommandSource var0, int var1) throws CommandSyntaxException {
      if (☃.func_197028_i().func_71344_c()) {
         throw field_198586_b.create(☃.func_197028_i().func_71215_F());
      } else if (!☃.func_197028_i().func_195565_a(☃.func_197028_i().func_71265_f(), false, ☃)) {
         throw field_198585_a.create();
      } else {
         ☃.func_197030_a(new TextComponentTranslation("commands.publish.success", ☃), true);
         return ☃;
      }
   }
}
