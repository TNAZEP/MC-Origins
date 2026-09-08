package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.TextComponentTranslation;

public class KillCommand {
   public static void func_198518_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("kill")
            .requires(var0x -> var0x.func_197034_c(2))
            .then(
               Commands.func_197056_a("targets", EntityArgument.func_197093_b())
                  .executes(var0x -> func_198519_a(var0x.getSource(), EntityArgument.func_197097_b(var0x, "targets")))
            )
      );
   }

   private static int func_198519_a(CommandSource var0, Collection<? extends Entity> var1) {
      for(Entity ☃ : ☃) {
         ☃.func_174812_G();
      }

      if (☃.size() == 1) {
         ☃.func_197030_a(new TextComponentTranslation("commands.kill.success.single", ((Entity)☃.iterator().next()).func_145748_c_()), true);
      } else {
         ☃.func_197030_a(new TextComponentTranslation("commands.kill.success.multiple", ☃.size()), true);
      }

      return ☃.size();
   }
}
