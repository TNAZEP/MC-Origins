package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class SeedCommand {
   public static void register(CommandDispatcher<CommandSourceStack> var0, boolean var1) {
      â˜ƒ.register(
         Commands.literal("seed")
            .requires(var1x -> !â˜ƒ || var1x.hasPermission(2))
            .executes(
               var0x -> {
                  long â˜ƒ = var0x.getSource().getLevel().getSeed();
                  Component â˜ƒx = ComponentUtils.wrapInSquareBrackets(
                     new TextComponent(String.valueOf(â˜ƒ))
                        .withStyle(
                           var2 -> var2.withColor(ChatFormatting.GREEN)
                                 .withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, String.valueOf(â˜ƒ)))
                                 .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableComponent("chat.copy.click")))
                                 .withInsertion(String.valueOf(â˜ƒ))
                        )
                  );
                  var0x.getSource().sendSuccess(new TranslatableComponent("commands.seed.success", â˜ƒx), false);
                  return (int)â˜ƒ;
               }
            )
      );
   }
}
