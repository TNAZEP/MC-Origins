package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

public class LocateCommand {
   private static final SimpleCommandExceptionType field_198536_a = new SimpleCommandExceptionType(new TextComponentTranslation("commands.locate.failed"));

   public static void func_198528_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("locate")
            .requires(var0x -> var0x.func_197034_c(2))
            .then(Commands.func_197057_a("Village").executes(var0x -> func_198534_a(var0x.getSource(), "Village")))
            .then(Commands.func_197057_a("Mineshaft").executes(var0x -> func_198534_a(var0x.getSource(), "Mineshaft")))
            .then(Commands.func_197057_a("Mansion").executes(var0x -> func_198534_a(var0x.getSource(), "Mansion")))
            .then(Commands.func_197057_a("Igloo").executes(var0x -> func_198534_a(var0x.getSource(), "Igloo")))
            .then(Commands.func_197057_a("Desert_Pyramid").executes(var0x -> func_198534_a(var0x.getSource(), "Desert_Pyramid")))
            .then(Commands.func_197057_a("Jungle_Pyramid").executes(var0x -> func_198534_a(var0x.getSource(), "Jungle_Pyramid")))
            .then(Commands.func_197057_a("Swamp_Hut").executes(var0x -> func_198534_a(var0x.getSource(), "Swamp_Hut")))
            .then(Commands.func_197057_a("Stronghold").executes(var0x -> func_198534_a(var0x.getSource(), "Stronghold")))
            .then(Commands.func_197057_a("Monument").executes(var0x -> func_198534_a(var0x.getSource(), "Monument")))
            .then(Commands.func_197057_a("Fortress").executes(var0x -> func_198534_a(var0x.getSource(), "Fortress")))
            .then(Commands.func_197057_a("EndCity").executes(var0x -> func_198534_a(var0x.getSource(), "EndCity")))
            .then(Commands.func_197057_a("Ocean_Ruin").executes(var0x -> func_198534_a(var0x.getSource(), "Ocean_Ruin")))
            .then(Commands.func_197057_a("Buried_Treasure").executes(var0x -> func_198534_a(var0x.getSource(), "Buried_Treasure")))
            .then(Commands.func_197057_a("Shipwreck").executes(var0x -> func_198534_a(var0x.getSource(), "Shipwreck")))
      );
   }

   private static int func_198534_a(CommandSource var0, String var1) throws CommandSyntaxException {
      BlockPos ☃ = new BlockPos(☃.func_197036_d());
      BlockPos ☃x = ☃.func_197023_e().func_211157_a(☃, ☃, 100, false);
      if (☃x == null) {
         throw field_198536_a.create();
      } else {
         int ☃ = MathHelper.func_76141_d(func_211907_a(☃.func_177958_n(), ☃.func_177952_p(), ☃x.func_177958_n(), ☃x.func_177952_p()));
         ITextComponent ☃x = TextComponentUtils.func_197676_a(new TextComponentTranslation("chat.coordinates", ☃x.func_177958_n(), "~", ☃x.func_177952_p()))
            .func_211710_a(
               var1x -> var1x.func_150238_a(TextFormatting.GREEN)
                     .func_150241_a(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tp @s " + ☃.func_177958_n() + " ~ " + ☃.func_177952_p()))
                     .func_150209_a(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentTranslation("chat.coordinates.tooltip")))
            );
         ☃.func_197030_a(new TextComponentTranslation("commands.locate.success", ☃, ☃x, ☃), false);
         return ☃;
      }
   }

   private static float func_211907_a(int var0, int var1, int var2, int var3) {
      int ☃ = ☃ - ☃;
      int ☃x = ☃ - ☃;
      return MathHelper.func_76129_c((float)(☃ * ☃ + ☃x * ☃x));
   }
}
