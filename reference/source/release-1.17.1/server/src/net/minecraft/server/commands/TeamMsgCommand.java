package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.List;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.scores.PlayerTeam;

public class TeamMsgCommand {
   private static final Style SUGGEST_STYLE = Style.EMPTY
      .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableComponent("chat.type.team.hover")))
      .withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/teammsg "));
   private static final SimpleCommandExceptionType ERROR_NOT_ON_TEAM = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.teammsg.failed.noteam")
   );

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      LiteralCommandNode<CommandSourceStack> â˜ƒ = â˜ƒ.register(
         Commands.literal("teammsg")
            .then(
               Commands.argument("message", MessageArgument.message())
                  .executes(var0x -> sendMessage(var0x.getSource(), MessageArgument.getMessage(var0x, "message")))
            )
      );
      â˜ƒ.register(Commands.literal("tm").redirect(â˜ƒ));
   }

   private static int sendMessage(CommandSourceStack var0, Component var1) throws CommandSyntaxException {
      Entity â˜ƒ = â˜ƒ.getEntityOrException();
      PlayerTeam â˜ƒx = (PlayerTeam)â˜ƒ.getTeam();
      if (â˜ƒx == null) {
         throw ERROR_NOT_ON_TEAM.create();
      } else {
         Component â˜ƒ = â˜ƒx.getFormattedDisplayName().withStyle(SUGGEST_STYLE);
         List<ServerPlayer> â˜ƒx = â˜ƒ.getServer().getPlayerList().getPlayers();

         for(ServerPlayer â˜ƒxx : â˜ƒx) {
            if (â˜ƒxx == â˜ƒ) {
               â˜ƒxx.sendMessage(new TranslatableComponent("chat.type.team.sent", â˜ƒ, â˜ƒ.getDisplayName(), â˜ƒ), â˜ƒ.getUUID());
            } else if (â˜ƒxx.getTeam() == â˜ƒx) {
               â˜ƒxx.sendMessage(new TranslatableComponent("chat.type.team.text", â˜ƒ, â˜ƒ.getDisplayName(), â˜ƒ), â˜ƒ.getUUID());
            }
         }

         return â˜ƒx.size();
      }
   }
}
