package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.command.arguments.SuggestionProviders;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketStopSound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;

public class StopSoundCommand {
   public static void func_198730_a(CommandDispatcher<CommandSource> var0) {
      RequiredArgumentBuilder<CommandSource, EntitySelector> ☃ = Commands.func_197056_a("targets", EntityArgument.func_197094_d())
         .executes(var0x -> func_198733_a(var0x.getSource(), EntityArgument.func_197090_e(var0x, "targets"), null, null))
         .then(
            Commands.func_197057_a("*")
               .then(
                  Commands.func_197056_a("sound", ResourceLocationArgument.func_197197_a())
                     .suggests(SuggestionProviders.field_197504_c)
                     .executes(
                        var0x -> func_198733_a(
                              var0x.getSource(), EntityArgument.func_197090_e(var0x, "targets"), null, ResourceLocationArgument.func_197195_e(var0x, "sound")
                           )
                     )
               )
         );

      for(SoundCategory ☃x : SoundCategory.values()) {
         ☃.then(
            Commands.func_197057_a(☃x.func_187948_a())
               .executes(var1x -> func_198733_a(var1x.getSource(), EntityArgument.func_197090_e(var1x, "targets"), ☃, null))
               .then(
                  Commands.func_197056_a("sound", ResourceLocationArgument.func_197197_a())
                     .suggests(SuggestionProviders.field_197504_c)
                     .executes(
                        var1x -> func_198733_a(
                              var1x.getSource(), EntityArgument.func_197090_e(var1x, "targets"), ☃, ResourceLocationArgument.func_197195_e(var1x, "sound")
                           )
                     )
               )
         );
      }

      ☃.register(Commands.func_197057_a("stopsound").requires(var0x -> var0x.func_197034_c(2)).then(☃));
   }

   private static int func_198733_a(CommandSource var0, Collection<EntityPlayerMP> var1, @Nullable SoundCategory var2, @Nullable ResourceLocation var3) {
      SPacketStopSound ☃ = new SPacketStopSound(☃, ☃);

      for(EntityPlayerMP ☃x : ☃) {
         ☃x.field_71135_a.func_147359_a(☃);
      }

      if (☃ != null) {
         if (☃ != null) {
            ☃.func_197030_a(new TextComponentTranslation("commands.stopsound.success.source.sound", ☃, ☃.func_187948_a()), true);
         } else {
            ☃.func_197030_a(new TextComponentTranslation("commands.stopsound.success.source.any", ☃.func_187948_a()), true);
         }
      } else if (☃ != null) {
         ☃.func_197030_a(new TextComponentTranslation("commands.stopsound.success.sourceless.sound", ☃), true);
      } else {
         ☃.func_197030_a(new TextComponentTranslation("commands.stopsound.success.sourceless.any"), true);
      }

      return ☃.size();
   }
}
