package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Map.Entry;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.feature.StructureFeature;

public class LocateCommand {
   private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType(new TranslatableComponent("commands.locate.failed"));

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      LiteralArgumentBuilder<CommandSourceStack> â˜ƒ = Commands.literal("locate").requires(var0x -> var0x.hasPermission(2));

      for(Entry<String, StructureFeature<?>> â˜ƒx : StructureFeature.STRUCTURES_REGISTRY.entrySet()) {
         â˜ƒ = â˜ƒ.then(Commands.literal((String)â˜ƒx.getKey()).executes(var1x -> locate(var1x.getSource(), (StructureFeature<?>)â˜ƒ.getValue())));
      }

      â˜ƒ.register(â˜ƒ);
   }

   private static int locate(CommandSourceStack var0, StructureFeature<?> var1) throws CommandSyntaxException {
      BlockPos â˜ƒ = new BlockPos(â˜ƒ.getPosition());
      BlockPos â˜ƒx = â˜ƒ.getLevel().findNearestMapFeature(â˜ƒ, â˜ƒ, 100, false);
      if (â˜ƒx == null) {
         throw ERROR_FAILED.create();
      } else {
         return showLocateResult(â˜ƒ, â˜ƒ.getFeatureName(), â˜ƒ, â˜ƒx, "commands.locate.success");
      }
   }

   public static int showLocateResult(CommandSourceStack var0, String var1, BlockPos var2, BlockPos var3, String var4) {
      int â˜ƒ = Mth.floor(dist(â˜ƒ.getX(), â˜ƒ.getZ(), â˜ƒ.getX(), â˜ƒ.getZ()));
      Component â˜ƒx = ComponentUtils.wrapInSquareBrackets(new TranslatableComponent("chat.coordinates", â˜ƒ.getX(), "~", â˜ƒ.getZ()))
         .withStyle(
            var1x -> var1x.withColor(ChatFormatting.GREEN)
                  .withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tp @s " + â˜ƒ.getX() + " ~ " + â˜ƒ.getZ()))
                  .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableComponent("chat.coordinates.tooltip")))
         );
      â˜ƒ.sendSuccess(new TranslatableComponent(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ), false);
      return â˜ƒ;
   }

   private static float dist(int var0, int var1, int var2, int var3) {
      int â˜ƒ = â˜ƒ - â˜ƒ;
      int â˜ƒx = â˜ƒ - â˜ƒ;
      return Mth.sqrt((float)(â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx));
   }
}
