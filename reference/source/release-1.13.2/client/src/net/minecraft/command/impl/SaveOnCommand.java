package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.WorldServer;

public class SaveOnCommand {
   private static final SimpleCommandExceptionType field_198624_a = new SimpleCommandExceptionType(new TextComponentTranslation("commands.save.alreadyOn"));

   public static void func_198621_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(Commands.func_197057_a("save-on").requires(var0x -> var0x.func_197034_c(4)).executes(var0x -> {
         CommandSource ☃ = var0x.getSource();
         boolean ☃x = false;

         for(WorldServer ☃xx : ☃.func_197028_i().func_212370_w()) {
            if (☃xx != null && ☃xx.field_73058_d) {
               ☃xx.field_73058_d = false;
               ☃x = true;
            }
         }

         if (!☃x) {
            throw field_198624_a.create();
         } else {
            ☃.func_197030_a(new TextComponentTranslation("commands.save.enabled"), true);
            return 1;
         }
      }));
   }
}
