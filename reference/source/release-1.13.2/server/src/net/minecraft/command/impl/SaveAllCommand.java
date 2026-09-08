package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.SessionLockException;

public class SaveAllCommand {
   private static final SimpleCommandExceptionType field_198616_a = new SimpleCommandExceptionType(new TextComponentTranslation("commands.save.failed"));

   public static void func_198611_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("save-all")
            .requires(var0x -> var0x.func_197034_c(4))
            .executes(var0x -> func_198614_a(var0x.getSource(), false))
            .then(Commands.func_197057_a("flush").executes(var0x -> func_198614_a(var0x.getSource(), true)))
      );
   }

   private static int func_198614_a(CommandSource var0, boolean var1) throws CommandSyntaxException {
      ☃.func_197030_a(new TextComponentTranslation("commands.save.saving"), false);
      MinecraftServer ☃ = ☃.func_197028_i();
      boolean ☃x = false;
      ☃.func_184103_al().func_72389_g();

      for(WorldServer ☃xx : ☃.func_212370_w()) {
         if (☃xx != null && func_198612_a(☃xx, ☃)) {
            ☃x = true;
         }
      }

      if (!☃x) {
         throw field_198616_a.create();
      } else {
         ☃.func_197030_a(new TextComponentTranslation("commands.save.success"), true);
         return 1;
      }
   }

   private static boolean func_198612_a(WorldServer var0, boolean var1) {
      boolean ☃ = ☃.field_73058_d;
      ☃.field_73058_d = false;

      boolean var4;
      try {
         ☃.func_73044_a(true, null);
         if (☃) {
            ☃.func_104140_m();
         }

         return true;
      } catch (SessionLockException var8) {
         var4 = false;
      } finally {
         ☃.field_73058_d = ☃;
      }

      return var4;
   }
}
