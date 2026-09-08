package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;

public class SaveOnCommand {
   private static final SimpleCommandExceptionType ERROR_ALREADY_ON = new SimpleCommandExceptionType(new TranslatableComponent("commands.save.alreadyOn"));

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(Commands.literal("save-on").requires(var0x -> var0x.hasPermission(4)).executes(var0x -> {
         CommandSourceStack â˜ƒ = var0x.getSource();
         boolean â˜ƒx = false;

         for(ServerLevel â˜ƒxx : â˜ƒ.getServer().getAllLevels()) {
            if (â˜ƒxx != null && â˜ƒxx.noSave) {
               â˜ƒxx.noSave = false;
               â˜ƒx = true;
            }
         }

         if (!â˜ƒx) {
            throw ERROR_ALREADY_ON.create();
         } else {
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.save.enabled"), true);
            return 1;
         }
      }));
   }
}
