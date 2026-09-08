package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundCustomSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;

public class PlaySoundCommand {
   private static final SimpleCommandExceptionType ERROR_TOO_FAR = new SimpleCommandExceptionType(new TranslatableComponent("commands.playsound.failed"));

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      RequiredArgumentBuilder<CommandSourceStack, ResourceLocation> â˜ƒ = Commands.argument("sound", ResourceLocationArgument.id())
         .suggests(SuggestionProviders.AVAILABLE_SOUNDS);

      for(SoundSource â˜ƒx : SoundSource.values()) {
         â˜ƒ.then(source(â˜ƒx));
      }

      â˜ƒ.register(Commands.literal("playsound").requires(var0x -> var0x.hasPermission(2)).then(â˜ƒ));
   }

   private static LiteralArgumentBuilder<CommandSourceStack> source(SoundSource var0) {
      return Commands.literal(â˜ƒ.getName())
         .then(
            Commands.argument("targets", EntityArgument.players())
               .executes(
                  var1 -> playSound(
                        var1.getSource(),
                        EntityArgument.getPlayers(var1, "targets"),
                        ResourceLocationArgument.getId(var1, "sound"),
                        â˜ƒ,
                        var1.getSource().getPosition(),
                        1.0F,
                        1.0F,
                        0.0F
                     )
               )
               .then(
                  Commands.argument("pos", Vec3Argument.vec3())
                     .executes(
                        var1 -> playSound(
                              var1.getSource(),
                              EntityArgument.getPlayers(var1, "targets"),
                              ResourceLocationArgument.getId(var1, "sound"),
                              â˜ƒ,
                              Vec3Argument.getVec3(var1, "pos"),
                              1.0F,
                              1.0F,
                              0.0F
                           )
                     )
                     .then(
                        Commands.argument("volume", FloatArgumentType.floatArg(0.0F))
                           .executes(
                              var1 -> playSound(
                                    var1.getSource(),
                                    EntityArgument.getPlayers(var1, "targets"),
                                    ResourceLocationArgument.getId(var1, "sound"),
                                    â˜ƒ,
                                    Vec3Argument.getVec3(var1, "pos"),
                                    var1.getArgument("volume", Float.class),
                                    1.0F,
                                    0.0F
                                 )
                           )
                           .then(
                              Commands.argument("pitch", FloatArgumentType.floatArg(0.0F, 2.0F))
                                 .executes(
                                    var1 -> playSound(
                                          var1.getSource(),
                                          EntityArgument.getPlayers(var1, "targets"),
                                          ResourceLocationArgument.getId(var1, "sound"),
                                          â˜ƒ,
                                          Vec3Argument.getVec3(var1, "pos"),
                                          var1.getArgument("volume", Float.class),
                                          var1.getArgument("pitch", Float.class),
                                          0.0F
                                       )
                                 )
                                 .then(
                                    Commands.argument("minVolume", FloatArgumentType.floatArg(0.0F, 1.0F))
                                       .executes(
                                          var1 -> playSound(
                                                var1.getSource(),
                                                EntityArgument.getPlayers(var1, "targets"),
                                                ResourceLocationArgument.getId(var1, "sound"),
                                                â˜ƒ,
                                                Vec3Argument.getVec3(var1, "pos"),
                                                var1.getArgument("volume", Float.class),
                                                var1.getArgument("pitch", Float.class),
                                                var1.getArgument("minVolume", Float.class)
                                             )
                                       )
                                 )
                           )
                     )
               )
         );
   }

   private static int playSound(
      CommandSourceStack var0, Collection<ServerPlayer> var1, ResourceLocation var2, SoundSource var3, Vec3 var4, float var5, float var6, float var7
   ) throws CommandSyntaxException {
      double â˜ƒ = Math.pow(â˜ƒ > 1.0F ? (double)(â˜ƒ * 16.0F) : 16.0, 2.0);
      int â˜ƒx = 0;

      for(ServerPlayer â˜ƒxx : â˜ƒ) {
         double â˜ƒxxx = â˜ƒ.x - â˜ƒxx.getX();
         double â˜ƒxxxx = â˜ƒ.y - â˜ƒxx.getY();
         double â˜ƒxxxxx = â˜ƒ.z - â˜ƒxx.getZ();
         double â˜ƒxxxxxx = â˜ƒxxx * â˜ƒxxx + â˜ƒxxxx * â˜ƒxxxx + â˜ƒxxxxx * â˜ƒxxxxx;
         Vec3 â˜ƒxxxxxxx = â˜ƒ;
         float â˜ƒxxxxxxxx = â˜ƒ;
         if (â˜ƒxxxxxx > â˜ƒ) {
            if (â˜ƒ <= 0.0F) {
               continue;
            }

            double â˜ƒxxxxxxxxx = Math.sqrt(â˜ƒxxxxxx);
            â˜ƒxxxxxxx = new Vec3(
               â˜ƒxx.getX() + â˜ƒxxx / â˜ƒxxxxxxxxx * 2.0, â˜ƒxx.getY() + â˜ƒxxxx / â˜ƒxxxxxxxxx * 2.0, â˜ƒxx.getZ() + â˜ƒxxxxx / â˜ƒxxxxxxxxx * 2.0
            );
            â˜ƒxxxxxxxx = â˜ƒ;
         }

         â˜ƒxx.connection.send(new ClientboundCustomSoundPacket(â˜ƒ, â˜ƒ, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒ));
         ++â˜ƒx;
      }

      if (â˜ƒx == 0) {
         throw ERROR_TOO_FAR.create();
      } else {
         if (â˜ƒ.size() == 1) {
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.playsound.success.single", â˜ƒ, ((ServerPlayer)â˜ƒ.iterator().next()).getDisplayName()), true);
         } else {
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.playsound.success.multiple", â˜ƒ, â˜ƒ.size()), true);
         }

         return â˜ƒx;
      }
   }
}
