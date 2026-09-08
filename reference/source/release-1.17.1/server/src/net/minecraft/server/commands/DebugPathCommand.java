package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.Path;

public class DebugPathCommand {
   private static final SimpleCommandExceptionType ERROR_NOT_MOB = new SimpleCommandExceptionType(new TextComponent("Source is not a mob"));
   private static final SimpleCommandExceptionType ERROR_NO_PATH = new SimpleCommandExceptionType(new TextComponent("Path not found"));
   private static final SimpleCommandExceptionType ERROR_NOT_COMPLETE = new SimpleCommandExceptionType(new TextComponent("Target not reached"));

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("debugpath")
            .requires(var0x -> var0x.hasPermission(2))
            .then(
               Commands.argument("to", BlockPosArgument.blockPos())
                  .executes(var0x -> fillBlocks(var0x.getSource(), BlockPosArgument.getLoadedBlockPos(var0x, "to")))
            )
      );
   }

   private static int fillBlocks(CommandSourceStack var0, BlockPos var1) throws CommandSyntaxException {
      Entity â˜ƒ = â˜ƒ.getEntity();
      if (!(â˜ƒ instanceof Mob)) {
         throw ERROR_NOT_MOB.create();
      } else {
         Mob â˜ƒ = (Mob)â˜ƒ;
         PathNavigation â˜ƒx = new GroundPathNavigation(â˜ƒ, â˜ƒ.getLevel());
         Path â˜ƒxx = â˜ƒx.createPath(â˜ƒ, 0);
         DebugPackets.sendPathFindingPacket(â˜ƒ.getLevel(), â˜ƒ, â˜ƒxx, â˜ƒx.getMaxDistanceToWaypoint());
         if (â˜ƒxx == null) {
            throw ERROR_NO_PATH.create();
         } else if (!â˜ƒxx.canReach()) {
            throw ERROR_NOT_COMPLETE.create();
         } else {
            â˜ƒ.sendSuccess(new TextComponent("Made path"), true);
            return 1;
         }
      }
   }
}
