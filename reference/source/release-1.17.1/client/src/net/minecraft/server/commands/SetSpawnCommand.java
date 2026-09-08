package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.AngleArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public class SetSpawnCommand {
   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("spawnpoint")
            .requires(var0x -> var0x.hasPermission(2))
            .executes(
               var0x -> setSpawn(
                     var0x.getSource(), Collections.singleton(var0x.getSource().getPlayerOrException()), new BlockPos(var0x.getSource().getPosition()), 0.0F
                  )
            )
            .then(
               Commands.argument("targets", EntityArgument.players())
                  .executes(
                     var0x -> setSpawn(var0x.getSource(), EntityArgument.getPlayers(var0x, "targets"), new BlockPos(var0x.getSource().getPosition()), 0.0F)
                  )
                  .then(
                     Commands.argument("pos", BlockPosArgument.blockPos())
                        .executes(
                           var0x -> setSpawn(
                                 var0x.getSource(), EntityArgument.getPlayers(var0x, "targets"), BlockPosArgument.getSpawnablePos(var0x, "pos"), 0.0F
                              )
                        )
                        .then(
                           Commands.argument("angle", AngleArgument.angle())
                              .executes(
                                 var0x -> setSpawn(
                                       var0x.getSource(),
                                       EntityArgument.getPlayers(var0x, "targets"),
                                       BlockPosArgument.getSpawnablePos(var0x, "pos"),
                                       AngleArgument.getAngle(var0x, "angle")
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int setSpawn(CommandSourceStack var0, Collection<ServerPlayer> var1, BlockPos var2, float var3) {
      ResourceKey<Level> â˜ƒ = â˜ƒ.getLevel().dimension();

      for(ServerPlayer â˜ƒx : â˜ƒ) {
         â˜ƒx.setRespawnPosition(â˜ƒ, â˜ƒ, â˜ƒ, true, false);
      }

      String â˜ƒx = â˜ƒ.location().toString();
      if (â˜ƒ.size() == 1) {
         â˜ƒ.sendSuccess(
            new TranslatableComponent(
               "commands.spawnpoint.success.single", â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒ, â˜ƒx, ((ServerPlayer)â˜ƒ.iterator().next()).getDisplayName()
            ),
            true
         );
      } else {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.spawnpoint.success.multiple", â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒ, â˜ƒx, â˜ƒ.size()), true);
      }

      return â˜ƒ.size();
   }
}
