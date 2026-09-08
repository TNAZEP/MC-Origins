package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import java.util.List;
import java.util.function.Function;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextComponentUtils;

public class ListCommand {
   public static void func_198522_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("list")
            .executes(var0x -> func_198524_a(var0x.getSource()))
            .then(Commands.func_197057_a("uuids").executes(var0x -> func_208201_b(var0x.getSource())))
      );
   }

   private static int func_198524_a(CommandSource var0) {
      return func_208200_a(☃, EntityPlayer::func_145748_c_);
   }

   private static int func_208201_b(CommandSource var0) {
      return func_208200_a(☃, EntityPlayer::func_208017_dF);
   }

   private static int func_208200_a(CommandSource var0, Function<EntityPlayerMP, ITextComponent> var1) {
      PlayerList ☃ = ☃.func_197028_i().func_184103_al();
      List<EntityPlayerMP> ☃x = ☃.func_181057_v();
      ITextComponent ☃xx = TextComponentUtils.func_197677_b(☃x, ☃);
      ☃.func_197030_a(new TextComponentTranslation("commands.list.players", ☃x.size(), ☃.func_72352_l(), ☃xx), false);
      return ☃x.size();
   }
}
