package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.HttpUtil;

public class PublishCommand {
   private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType(new TranslatableComponent("commands.publish.failed"));
   private static final DynamicCommandExceptionType ERROR_ALREADY_PUBLISHED = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("commands.publish.alreadyPublished", var0)
   );

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("publish")
            .requires(var0x -> var0x.hasPermission(4))
            .executes(var0x -> publish(var0x.getSource(), HttpUtil.getAvailablePort()))
            .then(
               Commands.argument("port", IntegerArgumentType.integer(0, 65535))
                  .executes(var0x -> publish(var0x.getSource(), IntegerArgumentType.getInteger(var0x, "port")))
            )
      );
   }

   private static int publish(CommandSourceStack var0, int var1) throws CommandSyntaxException {
      if (â˜ƒ.getServer().isPublished()) {
         throw ERROR_ALREADY_PUBLISHED.create(â˜ƒ.getServer().getPort());
      } else if (!â˜ƒ.getServer().publishServer(null, false, â˜ƒ)) {
         throw ERROR_FAILED.create();
      } else {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.publish.success", â˜ƒ), true);
         return â˜ƒ;
      }
   }
}
