package net.minecraft.commands;

import com.google.common.collect.Maps;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.commands.synchronization.ArgumentTypes;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.gametest.framework.TestCommand;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundCommandsPacket;
import net.minecraft.server.commands.AdvancementCommands;
import net.minecraft.server.commands.AttributeCommand;
import net.minecraft.server.commands.BanIpCommands;
import net.minecraft.server.commands.BanListCommands;
import net.minecraft.server.commands.BanPlayerCommands;
import net.minecraft.server.commands.BossBarCommands;
import net.minecraft.server.commands.ClearInventoryCommands;
import net.minecraft.server.commands.CloneCommands;
import net.minecraft.server.commands.DataPackCommand;
import net.minecraft.server.commands.DeOpCommands;
import net.minecraft.server.commands.DebugCommand;
import net.minecraft.server.commands.DefaultGameModeCommands;
import net.minecraft.server.commands.DifficultyCommand;
import net.minecraft.server.commands.EffectCommands;
import net.minecraft.server.commands.EmoteCommands;
import net.minecraft.server.commands.EnchantCommand;
import net.minecraft.server.commands.ExecuteCommand;
import net.minecraft.server.commands.ExperienceCommand;
import net.minecraft.server.commands.FillCommand;
import net.minecraft.server.commands.ForceLoadCommand;
import net.minecraft.server.commands.FunctionCommand;
import net.minecraft.server.commands.GameModeCommand;
import net.minecraft.server.commands.GameRuleCommand;
import net.minecraft.server.commands.GiveCommand;
import net.minecraft.server.commands.HelpCommand;
import net.minecraft.server.commands.ItemCommands;
import net.minecraft.server.commands.KickCommand;
import net.minecraft.server.commands.KillCommand;
import net.minecraft.server.commands.ListPlayersCommand;
import net.minecraft.server.commands.LocateBiomeCommand;
import net.minecraft.server.commands.LocateCommand;
import net.minecraft.server.commands.LootCommand;
import net.minecraft.server.commands.MsgCommand;
import net.minecraft.server.commands.OpCommand;
import net.minecraft.server.commands.PardonCommand;
import net.minecraft.server.commands.PardonIpCommand;
import net.minecraft.server.commands.ParticleCommand;
import net.minecraft.server.commands.PerfCommand;
import net.minecraft.server.commands.PlaySoundCommand;
import net.minecraft.server.commands.PublishCommand;
import net.minecraft.server.commands.RecipeCommand;
import net.minecraft.server.commands.ReloadCommand;
import net.minecraft.server.commands.SaveAllCommand;
import net.minecraft.server.commands.SaveOffCommand;
import net.minecraft.server.commands.SaveOnCommand;
import net.minecraft.server.commands.SayCommand;
import net.minecraft.server.commands.ScheduleCommand;
import net.minecraft.server.commands.ScoreboardCommand;
import net.minecraft.server.commands.SeedCommand;
import net.minecraft.server.commands.SetBlockCommand;
import net.minecraft.server.commands.SetPlayerIdleTimeoutCommand;
import net.minecraft.server.commands.SetSpawnCommand;
import net.minecraft.server.commands.SetWorldSpawnCommand;
import net.minecraft.server.commands.SpectateCommand;
import net.minecraft.server.commands.SpreadPlayersCommand;
import net.minecraft.server.commands.StopCommand;
import net.minecraft.server.commands.StopSoundCommand;
import net.minecraft.server.commands.SummonCommand;
import net.minecraft.server.commands.TagCommand;
import net.minecraft.server.commands.TeamCommand;
import net.minecraft.server.commands.TeamMsgCommand;
import net.minecraft.server.commands.TeleportCommand;
import net.minecraft.server.commands.TellRawCommand;
import net.minecraft.server.commands.TimeCommand;
import net.minecraft.server.commands.TitleCommand;
import net.minecraft.server.commands.TriggerCommand;
import net.minecraft.server.commands.WeatherCommand;
import net.minecraft.server.commands.WhitelistCommand;
import net.minecraft.server.commands.WorldBorderCommand;
import net.minecraft.server.commands.data.DataCommands;
import net.minecraft.server.level.ServerPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Commands {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final int LEVEL_ALL = 0;
   public static final int LEVEL_MODERATORS = 1;
   public static final int LEVEL_GAMEMASTERS = 2;
   public static final int LEVEL_ADMINS = 3;
   public static final int LEVEL_OWNERS = 4;
   private final CommandDispatcher<CommandSourceStack> dispatcher = new CommandDispatcher<>();

   public Commands(Commands.CommandSelection var1) {
      AdvancementCommands.register(this.dispatcher);
      AttributeCommand.register(this.dispatcher);
      ExecuteCommand.register(this.dispatcher);
      BossBarCommands.register(this.dispatcher);
      ClearInventoryCommands.register(this.dispatcher);
      CloneCommands.register(this.dispatcher);
      DataCommands.register(this.dispatcher);
      DataPackCommand.register(this.dispatcher);
      DebugCommand.register(this.dispatcher);
      DefaultGameModeCommands.register(this.dispatcher);
      DifficultyCommand.register(this.dispatcher);
      EffectCommands.register(this.dispatcher);
      EmoteCommands.register(this.dispatcher);
      EnchantCommand.register(this.dispatcher);
      ExperienceCommand.register(this.dispatcher);
      FillCommand.register(this.dispatcher);
      ForceLoadCommand.register(this.dispatcher);
      FunctionCommand.register(this.dispatcher);
      GameModeCommand.register(this.dispatcher);
      GameRuleCommand.register(this.dispatcher);
      GiveCommand.register(this.dispatcher);
      HelpCommand.register(this.dispatcher);
      ItemCommands.register(this.dispatcher);
      KickCommand.register(this.dispatcher);
      KillCommand.register(this.dispatcher);
      ListPlayersCommand.register(this.dispatcher);
      LocateCommand.register(this.dispatcher);
      LocateBiomeCommand.register(this.dispatcher);
      LootCommand.register(this.dispatcher);
      MsgCommand.register(this.dispatcher);
      ParticleCommand.register(this.dispatcher);
      PlaySoundCommand.register(this.dispatcher);
      ReloadCommand.register(this.dispatcher);
      RecipeCommand.register(this.dispatcher);
      SayCommand.register(this.dispatcher);
      ScheduleCommand.register(this.dispatcher);
      ScoreboardCommand.register(this.dispatcher);
      SeedCommand.register(this.dispatcher, â˜ƒ != Commands.CommandSelection.INTEGRATED);
      SetBlockCommand.register(this.dispatcher);
      SetSpawnCommand.register(this.dispatcher);
      SetWorldSpawnCommand.register(this.dispatcher);
      SpectateCommand.register(this.dispatcher);
      SpreadPlayersCommand.register(this.dispatcher);
      StopSoundCommand.register(this.dispatcher);
      SummonCommand.register(this.dispatcher);
      TagCommand.register(this.dispatcher);
      TeamCommand.register(this.dispatcher);
      TeamMsgCommand.register(this.dispatcher);
      TeleportCommand.register(this.dispatcher);
      TellRawCommand.register(this.dispatcher);
      TimeCommand.register(this.dispatcher);
      TitleCommand.register(this.dispatcher);
      TriggerCommand.register(this.dispatcher);
      WeatherCommand.register(this.dispatcher);
      WorldBorderCommand.register(this.dispatcher);
      if (SharedConstants.IS_RUNNING_IN_IDE) {
         TestCommand.register(this.dispatcher);
      }

      if (â˜ƒ.includeDedicated) {
         BanIpCommands.register(this.dispatcher);
         BanListCommands.register(this.dispatcher);
         BanPlayerCommands.register(this.dispatcher);
         DeOpCommands.register(this.dispatcher);
         OpCommand.register(this.dispatcher);
         PardonCommand.register(this.dispatcher);
         PardonIpCommand.register(this.dispatcher);
         PerfCommand.register(this.dispatcher);
         SaveAllCommand.register(this.dispatcher);
         SaveOffCommand.register(this.dispatcher);
         SaveOnCommand.register(this.dispatcher);
         SetPlayerIdleTimeoutCommand.register(this.dispatcher);
         StopCommand.register(this.dispatcher);
         WhitelistCommand.register(this.dispatcher);
      }

      if (â˜ƒ.includeIntegrated) {
         PublishCommand.register(this.dispatcher);
      }

      this.dispatcher
         .findAmbiguities(
            (var1x, var2, var3, var4) -> LOGGER.warn(
                  "Ambiguity between arguments {} and {} with inputs: {}", this.dispatcher.getPath(var2), this.dispatcher.getPath(var3), var4
               )
         );
      this.dispatcher.setConsumer((var0, var1x, var2) -> var0.getSource().onCommandComplete(var0, var1x, var2));
   }

   public int performCommand(CommandSourceStack var1, String var2) {
      StringReader â˜ƒ = new StringReader(â˜ƒ);
      if (â˜ƒ.canRead() && â˜ƒ.peek() == '/') {
         â˜ƒ.skip();
      }

      â˜ƒ.getServer().getProfiler().push(â˜ƒ);

      int â˜ƒ;
      try {
         try {
            return this.dispatcher.execute(â˜ƒ, â˜ƒ);
         } catch (CommandRuntimeException var13) {
            â˜ƒ.sendFailure(var13.getComponent());
            return 0;
         } catch (CommandSyntaxException var14) {
            â˜ƒ.sendFailure(ComponentUtils.fromMessage(var14.getRawMessage()));
            if (var14.getInput() != null && var14.getCursor() >= 0) {
               â˜ƒ = Math.min(var14.getInput().length(), var14.getCursor());
               MutableComponent â˜ƒ = new TextComponent("")
                  .withStyle(ChatFormatting.GRAY)
                  .withStyle(var1x -> var1x.withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, â˜ƒ)));
               if (â˜ƒ > 10) {
                  â˜ƒ.append("...");
               }

               â˜ƒ.append(var14.getInput().substring(Math.max(0, â˜ƒ - 10), â˜ƒ));
               if (â˜ƒ < var14.getInput().length()) {
                  Component â˜ƒ = new TextComponent(var14.getInput().substring(â˜ƒ))
                     .withStyle(new ChatFormatting[]{ChatFormatting.RED, ChatFormatting.UNDERLINE});
                  â˜ƒ.append(â˜ƒ);
               }

               â˜ƒ.append(new TranslatableComponent("command.context.here").withStyle(new ChatFormatting[]{ChatFormatting.RED, ChatFormatting.ITALIC}));
               â˜ƒ.sendFailure(â˜ƒ);
            }
         } catch (Exception var15) {
            MutableComponent â˜ƒ = new TextComponent(var15.getMessage() == null ? var15.getClass().getName() : var15.getMessage());
            if (LOGGER.isDebugEnabled()) {
               LOGGER.error("Command exception: {}", â˜ƒ, var15);
               StackTraceElement[] â˜ƒx = var15.getStackTrace();

               for(int â˜ƒxx = 0; â˜ƒxx < Math.min(â˜ƒx.length, 3); ++â˜ƒxx) {
                  â˜ƒ.append("\n\n")
                     .append(â˜ƒx[â˜ƒxx].getMethodName())
                     .append("\n ")
                     .append(â˜ƒx[â˜ƒxx].getFileName())
                     .append(":")
                     .append(String.valueOf(â˜ƒx[â˜ƒxx].getLineNumber()));
               }
            }

            â˜ƒ.sendFailure(
               new TranslatableComponent("command.failed").withStyle(var1x -> var1x.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, â˜ƒ)))
            );
            if (SharedConstants.IS_RUNNING_IN_IDE) {
               â˜ƒ.sendFailure(new TextComponent(Util.describeError(var15)));
               LOGGER.error("'{}' threw an exception", â˜ƒ, var15);
            }

            return 0;
         }

         â˜ƒ = 0;
      } finally {
         â˜ƒ.getServer().getProfiler().pop();
      }

      return â˜ƒ;
   }

   public void sendCommands(ServerPlayer var1) {
      Map<CommandNode<CommandSourceStack>, CommandNode<SharedSuggestionProvider>> â˜ƒ = Maps.<CommandNode<CommandSourceStack>, CommandNode<SharedSuggestionProvider>>newHashMap(
         
      );
      RootCommandNode<SharedSuggestionProvider> â˜ƒx = new RootCommandNode<>();
      â˜ƒ.put(this.dispatcher.getRoot(), â˜ƒx);
      this.fillUsableCommands(this.dispatcher.getRoot(), â˜ƒx, â˜ƒ.createCommandSourceStack(), â˜ƒ);
      â˜ƒ.connection.send(new ClientboundCommandsPacket(â˜ƒx));
   }

   private void fillUsableCommands(
      CommandNode<CommandSourceStack> var1,
      CommandNode<SharedSuggestionProvider> var2,
      CommandSourceStack var3,
      Map<CommandNode<CommandSourceStack>, CommandNode<SharedSuggestionProvider>> var4
   ) {
      for(CommandNode<CommandSourceStack> â˜ƒ : â˜ƒ.getChildren()) {
         if (â˜ƒ.canUse(â˜ƒ)) {
            ArgumentBuilder<SharedSuggestionProvider, ?> â˜ƒx = â˜ƒ.createBuilder();
            â˜ƒx.requires(var0 -> true);
            if (â˜ƒx.getCommand() != null) {
               â˜ƒx.executes(var0 -> 0);
            }

            if (â˜ƒx instanceof RequiredArgumentBuilder â˜ƒx && â˜ƒx.getSuggestionsProvider() != null) {
               â˜ƒx.suggests(SuggestionProviders.safelySwap(â˜ƒx.getSuggestionsProvider()));
            }

            if (â˜ƒx.getRedirect() != null) {
               â˜ƒx.redirect((CommandNode<SharedSuggestionProvider>)â˜ƒ.get(â˜ƒx.getRedirect()));
            }

            CommandNode<SharedSuggestionProvider> â˜ƒx = â˜ƒx.build();
            â˜ƒ.put(â˜ƒ, â˜ƒx);
            â˜ƒ.addChild(â˜ƒx);
            if (!â˜ƒ.getChildren().isEmpty()) {
               this.fillUsableCommands(â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ);
            }
         }
      }
   }

   public static LiteralArgumentBuilder<CommandSourceStack> literal(String var0) {
      return LiteralArgumentBuilder.literal(â˜ƒ);
   }

   public static <T> RequiredArgumentBuilder<CommandSourceStack, T> argument(String var0, ArgumentType<T> var1) {
      return RequiredArgumentBuilder.argument(â˜ƒ, â˜ƒ);
   }

   public static Predicate<String> createValidator(Commands.ParseFunction var0) {
      return var1 -> {
         try {
            â˜ƒ.parse(new StringReader(var1));
            return true;
         } catch (CommandSyntaxException var3) {
            return false;
         }
      };
   }

   public CommandDispatcher<CommandSourceStack> getDispatcher() {
      return this.dispatcher;
   }

   @Nullable
   public static <S> CommandSyntaxException getParseException(ParseResults<S> var0) {
      if (!â˜ƒ.getReader().canRead()) {
         return null;
      } else if (â˜ƒ.getExceptions().size() == 1) {
         return (CommandSyntaxException)â˜ƒ.getExceptions().values().iterator().next();
      } else {
         return â˜ƒ.getContext().getRange().isEmpty()
            ? CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand().createWithContext(â˜ƒ.getReader())
            : CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().createWithContext(â˜ƒ.getReader());
      }
   }

   public static void validate() {
      RootCommandNode<CommandSourceStack> â˜ƒ = new Commands(Commands.CommandSelection.ALL).getDispatcher().getRoot();
      Set<ArgumentType<?>> â˜ƒx = ArgumentTypes.findUsedArgumentTypes(â˜ƒ);
      Set<ArgumentType<?>> â˜ƒxx = (Set)â˜ƒx.stream().filter(var0x -> !ArgumentTypes.isTypeRegistered(var0x)).collect(Collectors.toSet());
      if (!â˜ƒxx.isEmpty()) {
         LOGGER.warn("Missing type registration for following arguments:\n {}", â˜ƒxx.stream().map(var0x -> "\t" + var0x).collect(Collectors.joining(",\n")));
         throw new IllegalStateException("Unregistered argument types");
      }
   }

   public static enum CommandSelection {
      ALL(true, true),
      DEDICATED(false, true),
      INTEGRATED(true, false);

      final boolean includeIntegrated;
      final boolean includeDedicated;

      private CommandSelection(boolean var3, boolean var4) {
         this.includeIntegrated = â˜ƒ;
         this.includeDedicated = â˜ƒ;
      }
   }

   @FunctionalInterface
   public interface ParseFunction {
      void parse(StringReader var1) throws CommandSyntaxException;
   }
}
