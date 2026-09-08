package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;

public class SaveOffCommand {
   private static final SimpleCommandExceptionType ERROR_ALREADY_OFF = new SimpleCommandExceptionType(new TranslatableComponent("commands.save.alreadyOff"));

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(Commands.literal("save-off").requires(var0x -> var0x.hasPermission(4)).executes(var0x -> {
         CommandSourceStack â˜ƒ = var0x.getSource();
         boolean â˜ƒx = false;

         for(ServerLevel â˜ƒxx : â˜ƒ.getServer().getAllLevels()) {
            if (â˜ƒxx != null && !â˜ƒxx.noSave) {
               â˜ƒxx.noSave = true;
               â˜ƒx = true;
            }
         }

         if (!â˜ƒx) {
            throw ERROR_ALREADY_OFF.create();
         } else {
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.save.disabled"), true);
            return 1;
         }
      }));
   }
}
