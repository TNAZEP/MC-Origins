package net.minecraft.server.commands;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;

public class KillCommand {
   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("kill")
            .requires(var0x -> var0x.hasPermission(2))
            .executes(var0x -> kill(var0x.getSource(), ImmutableList.of(var0x.getSource().getEntityOrException())))
            .then(
               Commands.argument("targets", EntityArgument.entities()).executes(var0x -> kill(var0x.getSource(), EntityArgument.getEntities(var0x, "targets")))
            )
      );
   }

   private static int kill(CommandSourceStack var0, Collection<? extends Entity> var1) {
      for(Entity â˜ƒ : â˜ƒ) {
         â˜ƒ.kill();
      }

      if (â˜ƒ.size() == 1) {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.kill.success.single", ((Entity)â˜ƒ.iterator().next()).getDisplayName()), true);
      } else {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.kill.success.multiple", â˜ƒ.size()), true);
      }

      return â˜ƒ.size();
   }
}
