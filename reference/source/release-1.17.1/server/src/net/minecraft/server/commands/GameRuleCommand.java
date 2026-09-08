package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.GameRules;

public class GameRuleCommand {
   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      final LiteralArgumentBuilder<CommandSourceStack> â˜ƒ = Commands.literal("gamerule").requires(var0x -> var0x.hasPermission(2));
      GameRules.visitGameRuleTypes(
         new GameRules.GameRuleTypeVisitor() {
            @Override
            public <T extends GameRules.Value<T>> void visit(GameRules.Key<T> var1x, GameRules.Type<T> var2) {
               â˜ƒ.then(
                  Commands.literal(â˜ƒ.getId())
                     .executes(var1xx -> GameRuleCommand.queryRule(var1xx.getSource(), â˜ƒ))
                     .then(â˜ƒ.createArgument("value").executes(var1xx -> GameRuleCommand.setRule(var1xx, â˜ƒ)))
               );
            }
         }
      );
      â˜ƒ.register(â˜ƒ);
   }

   static <T extends GameRules.Value<T>> int setRule(CommandContext<CommandSourceStack> var0, GameRules.Key<T> var1) {
      CommandSourceStack â˜ƒ = â˜ƒ.getSource();
      T â˜ƒx = â˜ƒ.getServer().getGameRules().getRule(â˜ƒ);
      â˜ƒx.setFromArgument(â˜ƒ, "value");
      â˜ƒ.sendSuccess(new TranslatableComponent("commands.gamerule.set", â˜ƒ.getId(), â˜ƒx.toString()), true);
      return â˜ƒx.getCommandResult();
   }

   static <T extends GameRules.Value<T>> int queryRule(CommandSourceStack var0, GameRules.Key<T> var1) {
      T â˜ƒ = â˜ƒ.getServer().getGameRules().getRule(â˜ƒ);
      â˜ƒ.sendSuccess(new TranslatableComponent("commands.gamerule.query", â˜ƒ.getId(), â˜ƒ.toString()), false);
      return â˜ƒ.getCommandResult();
   }
}
