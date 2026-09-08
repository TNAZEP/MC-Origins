package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.bossevents.CustomBossEvent;
import net.minecraft.server.bossevents.CustomBossEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.player.Player;

public class BossBarCommands {
   private static final DynamicCommandExceptionType ERROR_ALREADY_EXISTS = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("commands.bossbar.create.failed", var0)
   );
   private static final DynamicCommandExceptionType ERROR_DOESNT_EXIST = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("commands.bossbar.unknown", var0)
   );
   private static final SimpleCommandExceptionType ERROR_NO_PLAYER_CHANGE = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.bossbar.set.players.unchanged")
   );
   private static final SimpleCommandExceptionType ERROR_NO_NAME_CHANGE = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.bossbar.set.name.unchanged")
   );
   private static final SimpleCommandExceptionType ERROR_NO_COLOR_CHANGE = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.bossbar.set.color.unchanged")
   );
   private static final SimpleCommandExceptionType ERROR_NO_STYLE_CHANGE = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.bossbar.set.style.unchanged")
   );
   private static final SimpleCommandExceptionType ERROR_NO_VALUE_CHANGE = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.bossbar.set.value.unchanged")
   );
   private static final SimpleCommandExceptionType ERROR_NO_MAX_CHANGE = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.bossbar.set.max.unchanged")
   );
   private static final SimpleCommandExceptionType ERROR_ALREADY_HIDDEN = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.bossbar.set.visibility.unchanged.hidden")
   );
   private static final SimpleCommandExceptionType ERROR_ALREADY_VISIBLE = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.bossbar.set.visibility.unchanged.visible")
   );
   public static final SuggestionProvider<CommandSourceStack> SUGGEST_BOSS_BAR = (var0, var1) -> SharedSuggestionProvider.suggestResource(
         var0.getSource().getServer().getCustomBossEvents().getIds(), var1
      );

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("bossbar")
            .requires(var0x -> var0x.hasPermission(2))
            .then(
               Commands.literal("add")
                  .then(
                     Commands.argument("id", ResourceLocationArgument.id())
                        .then(
                           Commands.argument("name", ComponentArgument.textComponent())
                              .executes(
                                 var0x -> createBar(
                                       var0x.getSource(), ResourceLocationArgument.getId(var0x, "id"), ComponentArgument.getComponent(var0x, "name")
                                    )
                              )
                        )
                  )
            )
            .then(
               Commands.literal("remove")
                  .then(
                     Commands.argument("id", ResourceLocationArgument.id())
                        .suggests(SUGGEST_BOSS_BAR)
                        .executes(var0x -> removeBar(var0x.getSource(), getBossBar(var0x)))
                  )
            )
            .then(Commands.literal("list").executes(var0x -> listBars(var0x.getSource())))
            .then(
               Commands.literal("set")
                  .then(
                     Commands.argument("id", ResourceLocationArgument.id())
                        .suggests(SUGGEST_BOSS_BAR)
                        .then(
                           Commands.literal("name")
                              .then(
                                 Commands.argument("name", ComponentArgument.textComponent())
                                    .executes(var0x -> setName(var0x.getSource(), getBossBar(var0x), ComponentArgument.getComponent(var0x, "name")))
                              )
                        )
                        .then(
                           Commands.literal("color")
                              .then(Commands.literal("pink").executes(var0x -> setColor(var0x.getSource(), getBossBar(var0x), BossEvent.BossBarColor.PINK)))
                              .then(Commands.literal("blue").executes(var0x -> setColor(var0x.getSource(), getBossBar(var0x), BossEvent.BossBarColor.BLUE)))
                              .then(Commands.literal("red").executes(var0x -> setColor(var0x.getSource(), getBossBar(var0x), BossEvent.BossBarColor.RED)))
                              .then(Commands.literal("green").executes(var0x -> setColor(var0x.getSource(), getBossBar(var0x), BossEvent.BossBarColor.GREEN)))
                              .then(Commands.literal("yellow").executes(var0x -> setColor(var0x.getSource(), getBossBar(var0x), BossEvent.BossBarColor.YELLOW)))
                              .then(Commands.literal("purple").executes(var0x -> setColor(var0x.getSource(), getBossBar(var0x), BossEvent.BossBarColor.PURPLE)))
                              .then(Commands.literal("white").executes(var0x -> setColor(var0x.getSource(), getBossBar(var0x), BossEvent.BossBarColor.WHITE)))
                        )
                        .then(
                           Commands.literal("style")
                              .then(
                                 Commands.literal("progress")
                                    .executes(var0x -> setStyle(var0x.getSource(), getBossBar(var0x), BossEvent.BossBarOverlay.PROGRESS))
                              )
                              .then(
                                 Commands.literal("notched_6")
                                    .executes(var0x -> setStyle(var0x.getSource(), getBossBar(var0x), BossEvent.BossBarOverlay.NOTCHED_6))
                              )
                              .then(
                                 Commands.literal("notched_10")
                                    .executes(var0x -> setStyle(var0x.getSource(), getBossBar(var0x), BossEvent.BossBarOverlay.NOTCHED_10))
                              )
                              .then(
                                 Commands.literal("notched_12")
                                    .executes(var0x -> setStyle(var0x.getSource(), getBossBar(var0x), BossEvent.BossBarOverlay.NOTCHED_12))
                              )
                              .then(
                                 Commands.literal("notched_20")
                                    .executes(var0x -> setStyle(var0x.getSource(), getBossBar(var0x), BossEvent.BossBarOverlay.NOTCHED_20))
                              )
                        )
                        .then(
                           Commands.literal("value")
                              .then(
                                 Commands.argument("value", IntegerArgumentType.integer(0))
                                    .executes(var0x -> setValue(var0x.getSource(), getBossBar(var0x), IntegerArgumentType.getInteger(var0x, "value")))
                              )
                        )
                        .then(
                           Commands.literal("max")
                              .then(
                                 Commands.argument("max", IntegerArgumentType.integer(1))
                                    .executes(var0x -> setMax(var0x.getSource(), getBossBar(var0x), IntegerArgumentType.getInteger(var0x, "max")))
                              )
                        )
                        .then(
                           Commands.literal("visible")
                              .then(
                                 Commands.argument("visible", BoolArgumentType.bool())
                                    .executes(var0x -> setVisible(var0x.getSource(), getBossBar(var0x), BoolArgumentType.getBool(var0x, "visible")))
                              )
                        )
                        .then(
                           Commands.literal("players")
                              .executes(var0x -> setPlayers(var0x.getSource(), getBossBar(var0x), Collections.emptyList()))
                              .then(
                                 Commands.argument("targets", EntityArgument.players())
                                    .executes(var0x -> setPlayers(var0x.getSource(), getBossBar(var0x), EntityArgument.getOptionalPlayers(var0x, "targets")))
                              )
                        )
                  )
            )
            .then(
               Commands.literal("get")
                  .then(
                     Commands.argument("id", ResourceLocationArgument.id())
                        .suggests(SUGGEST_BOSS_BAR)
                        .then(Commands.literal("value").executes(var0x -> getValue(var0x.getSource(), getBossBar(var0x))))
                        .then(Commands.literal("max").executes(var0x -> getMax(var0x.getSource(), getBossBar(var0x))))
                        .then(Commands.literal("visible").executes(var0x -> getVisible(var0x.getSource(), getBossBar(var0x))))
                        .then(Commands.literal("players").executes(var0x -> getPlayers(var0x.getSource(), getBossBar(var0x))))
                  )
            )
      );
   }

   private static int getValue(CommandSourceStack var0, CustomBossEvent var1) {
      â˜ƒ.sendSuccess(new TranslatableComponent("commands.bossbar.get.value", â˜ƒ.getDisplayName(), â˜ƒ.getValue()), true);
      return â˜ƒ.getValue();
   }

   private static int getMax(CommandSourceStack var0, CustomBossEvent var1) {
      â˜ƒ.sendSuccess(new TranslatableComponent("commands.bossbar.get.max", â˜ƒ.getDisplayName(), â˜ƒ.getMax()), true);
      return â˜ƒ.getMax();
   }

   private static int getVisible(CommandSourceStack var0, CustomBossEvent var1) {
      if (â˜ƒ.isVisible()) {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.bossbar.get.visible.visible", â˜ƒ.getDisplayName()), true);
         return 1;
      } else {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.bossbar.get.visible.hidden", â˜ƒ.getDisplayName()), true);
         return 0;
      }
   }

   private static int getPlayers(CommandSourceStack var0, CustomBossEvent var1) {
      if (â˜ƒ.getPlayers().isEmpty()) {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.bossbar.get.players.none", â˜ƒ.getDisplayName()), true);
      } else {
         â˜ƒ.sendSuccess(
            new TranslatableComponent(
               "commands.bossbar.get.players.some",
               â˜ƒ.getDisplayName(),
               â˜ƒ.getPlayers().size(),
               ComponentUtils.formatList(â˜ƒ.getPlayers(), Player::getDisplayName)
            ),
            true
         );
      }

      return â˜ƒ.getPlayers().size();
   }

   private static int setVisible(CommandSourceStack var0, CustomBossEvent var1, boolean var2) throws CommandSyntaxException {
      if (â˜ƒ.isVisible() == â˜ƒ) {
         if (â˜ƒ) {
            throw ERROR_ALREADY_VISIBLE.create();
         } else {
            throw ERROR_ALREADY_HIDDEN.create();
         }
      } else {
         â˜ƒ.setVisible(â˜ƒ);
         if (â˜ƒ) {
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.bossbar.set.visible.success.visible", â˜ƒ.getDisplayName()), true);
         } else {
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.bossbar.set.visible.success.hidden", â˜ƒ.getDisplayName()), true);
         }

         return 0;
      }
   }

   private static int setValue(CommandSourceStack var0, CustomBossEvent var1, int var2) throws CommandSyntaxException {
      if (â˜ƒ.getValue() == â˜ƒ) {
         throw ERROR_NO_VALUE_CHANGE.create();
      } else {
         â˜ƒ.setValue(â˜ƒ);
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.bossbar.set.value.success", â˜ƒ.getDisplayName(), â˜ƒ), true);
         return â˜ƒ;
      }
   }

   private static int setMax(CommandSourceStack var0, CustomBossEvent var1, int var2) throws CommandSyntaxException {
      if (â˜ƒ.getMax() == â˜ƒ) {
         throw ERROR_NO_MAX_CHANGE.create();
      } else {
         â˜ƒ.setMax(â˜ƒ);
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.bossbar.set.max.success", â˜ƒ.getDisplayName(), â˜ƒ), true);
         return â˜ƒ;
      }
   }

   private static int setColor(CommandSourceStack var0, CustomBossEvent var1, BossEvent.BossBarColor var2) throws CommandSyntaxException {
      if (â˜ƒ.getColor().equals(â˜ƒ)) {
         throw ERROR_NO_COLOR_CHANGE.create();
      } else {
         â˜ƒ.setColor(â˜ƒ);
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.bossbar.set.color.success", â˜ƒ.getDisplayName()), true);
         return 0;
      }
   }

   private static int setStyle(CommandSourceStack var0, CustomBossEvent var1, BossEvent.BossBarOverlay var2) throws CommandSyntaxException {
      if (â˜ƒ.getOverlay().equals(â˜ƒ)) {
         throw ERROR_NO_STYLE_CHANGE.create();
      } else {
         â˜ƒ.setOverlay(â˜ƒ);
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.bossbar.set.style.success", â˜ƒ.getDisplayName()), true);
         return 0;
      }
   }

   private static int setName(CommandSourceStack var0, CustomBossEvent var1, Component var2) throws CommandSyntaxException {
      Component â˜ƒ = ComponentUtils.updateForEntity(â˜ƒ, â˜ƒ, null, 0);
      if (â˜ƒ.getName().equals(â˜ƒ)) {
         throw ERROR_NO_NAME_CHANGE.create();
      } else {
         â˜ƒ.setName(â˜ƒ);
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.bossbar.set.name.success", â˜ƒ.getDisplayName()), true);
         return 0;
      }
   }

   private static int setPlayers(CommandSourceStack var0, CustomBossEvent var1, Collection<ServerPlayer> var2) throws CommandSyntaxException {
      boolean â˜ƒ = â˜ƒ.setPlayers(â˜ƒ);
      if (!â˜ƒ) {
         throw ERROR_NO_PLAYER_CHANGE.create();
      } else {
         if (â˜ƒ.getPlayers().isEmpty()) {
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.bossbar.set.players.success.none", â˜ƒ.getDisplayName()), true);
         } else {
            â˜ƒ.sendSuccess(
               new TranslatableComponent(
                  "commands.bossbar.set.players.success.some", â˜ƒ.getDisplayName(), â˜ƒ.size(), ComponentUtils.formatList(â˜ƒ, Player::getDisplayName)
               ),
               true
            );
         }

         return â˜ƒ.getPlayers().size();
      }
   }

   private static int listBars(CommandSourceStack var0) {
      Collection<CustomBossEvent> â˜ƒ = â˜ƒ.getServer().getCustomBossEvents().getEvents();
      if (â˜ƒ.isEmpty()) {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.bossbar.list.bars.none"), false);
      } else {
         â˜ƒ.sendSuccess(
            new TranslatableComponent("commands.bossbar.list.bars.some", â˜ƒ.size(), ComponentUtils.formatList(â˜ƒ, CustomBossEvent::getDisplayName)), false
         );
      }

      return â˜ƒ.size();
   }

   private static int createBar(CommandSourceStack var0, ResourceLocation var1, Component var2) throws CommandSyntaxException {
      CustomBossEvents â˜ƒ = â˜ƒ.getServer().getCustomBossEvents();
      if (â˜ƒ.get(â˜ƒ) != null) {
         throw ERROR_ALREADY_EXISTS.create(â˜ƒ.toString());
      } else {
         CustomBossEvent â˜ƒ = â˜ƒ.create(â˜ƒ, ComponentUtils.updateForEntity(â˜ƒ, â˜ƒ, null, 0));
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.bossbar.create.success", â˜ƒ.getDisplayName()), true);
         return â˜ƒ.getEvents().size();
      }
   }

   private static int removeBar(CommandSourceStack var0, CustomBossEvent var1) {
      CustomBossEvents â˜ƒ = â˜ƒ.getServer().getCustomBossEvents();
      â˜ƒ.removeAllPlayers();
      â˜ƒ.remove(â˜ƒ);
      â˜ƒ.sendSuccess(new TranslatableComponent("commands.bossbar.remove.success", â˜ƒ.getDisplayName()), true);
      return â˜ƒ.getEvents().size();
   }

   public static CustomBossEvent getBossBar(CommandContext<CommandSourceStack> var0) throws CommandSyntaxException {
      ResourceLocation â˜ƒ = ResourceLocationArgument.getId(â˜ƒ, "id");
      CustomBossEvent â˜ƒx = â˜ƒ.getSource().getServer().getCustomBossEvents().get(â˜ƒ);
      if (â˜ƒx == null) {
         throw ERROR_DOESNT_EXIST.create(â˜ƒ.toString());
      } else {
         return â˜ƒx;
      }
   }
}
