package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;

public class StopSoundCommand {
   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      RequiredArgumentBuilder<CommandSourceStack, EntitySelector> â˜ƒ = Commands.argument("targets", EntityArgument.players())
         .executes(var0x -> stopSound(var0x.getSource(), EntityArgument.getPlayers(var0x, "targets"), null, null))
         .then(
            Commands.literal("*")
               .then(
                  Commands.argument("sound", ResourceLocationArgument.id())
                     .suggests(SuggestionProviders.AVAILABLE_SOUNDS)
                     .executes(
                        var0x -> stopSound(var0x.getSource(), EntityArgument.getPlayers(var0x, "targets"), null, ResourceLocationArgument.getId(var0x, "sound"))
                     )
               )
         );

      for(SoundSource â˜ƒx : SoundSource.values()) {
         â˜ƒ.then(
            Commands.literal(â˜ƒx.getName())
               .executes(var1x -> stopSound(var1x.getSource(), EntityArgument.getPlayers(var1x, "targets"), â˜ƒ, null))
               .then(
                  Commands.argument("sound", ResourceLocationArgument.id())
                     .suggests(SuggestionProviders.AVAILABLE_SOUNDS)
                     .executes(
                        var1x -> stopSound(var1x.getSource(), EntityArgument.getPlayers(var1x, "targets"), â˜ƒ, ResourceLocationArgument.getId(var1x, "sound"))
                     )
               )
         );
      }

      â˜ƒ.register(Commands.literal("stopsound").requires(var0x -> var0x.hasPermission(2)).then(â˜ƒ));
   }

   private static int stopSound(CommandSourceStack var0, Collection<ServerPlayer> var1, @Nullable SoundSource var2, @Nullable ResourceLocation var3) {
      ClientboundStopSoundPacket â˜ƒ = new ClientboundStopSoundPacket(â˜ƒ, â˜ƒ);

      for(ServerPlayer â˜ƒx : â˜ƒ) {
         â˜ƒx.connection.send(â˜ƒ);
      }

      if (â˜ƒ != null) {
         if (â˜ƒ != null) {
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.stopsound.success.source.sound", â˜ƒ, â˜ƒ.getName()), true);
         } else {
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.stopsound.success.source.any", â˜ƒ.getName()), true);
         }
      } else if (â˜ƒ != null) {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.stopsound.success.sourceless.sound", â˜ƒ), true);
      } else {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.stopsound.success.sourceless.any"), true);
      }

      return â˜ƒ.size();
   }
}
