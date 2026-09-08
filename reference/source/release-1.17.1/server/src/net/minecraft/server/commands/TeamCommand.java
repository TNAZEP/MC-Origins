package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ColorArgument;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.commands.arguments.ScoreHolderArgument;
import net.minecraft.commands.arguments.TeamArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team;

public class TeamCommand {
   private static final SimpleCommandExceptionType ERROR_TEAM_ALREADY_EXISTS = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.team.add.duplicate")
   );
   private static final DynamicCommandExceptionType ERROR_TEAM_NAME_TOO_LONG = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("commands.team.add.longName", var0)
   );
   private static final SimpleCommandExceptionType ERROR_TEAM_ALREADY_EMPTY = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.team.empty.unchanged")
   );
   private static final SimpleCommandExceptionType ERROR_TEAM_ALREADY_NAME = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.team.option.name.unchanged")
   );
   private static final SimpleCommandExceptionType ERROR_TEAM_ALREADY_COLOR = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.team.option.color.unchanged")
   );
   private static final SimpleCommandExceptionType ERROR_TEAM_ALREADY_FRIENDLYFIRE_ENABLED = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.team.option.friendlyfire.alreadyEnabled")
   );
   private static final SimpleCommandExceptionType ERROR_TEAM_ALREADY_FRIENDLYFIRE_DISABLED = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.team.option.friendlyfire.alreadyDisabled")
   );
   private static final SimpleCommandExceptionType ERROR_TEAM_ALREADY_FRIENDLYINVISIBLES_ENABLED = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.team.option.seeFriendlyInvisibles.alreadyEnabled")
   );
   private static final SimpleCommandExceptionType ERROR_TEAM_ALREADY_FRIENDLYINVISIBLES_DISABLED = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.team.option.seeFriendlyInvisibles.alreadyDisabled")
   );
   private static final SimpleCommandExceptionType ERROR_TEAM_NAMETAG_VISIBLITY_UNCHANGED = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.team.option.nametagVisibility.unchanged")
   );
   private static final SimpleCommandExceptionType ERROR_TEAM_DEATH_MESSAGE_VISIBLITY_UNCHANGED = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.team.option.deathMessageVisibility.unchanged")
   );
   private static final SimpleCommandExceptionType ERROR_TEAM_COLLISION_UNCHANGED = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.team.option.collisionRule.unchanged")
   );

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("team")
            .requires(var0x -> var0x.hasPermission(2))
            .then(
               Commands.literal("list")
                  .executes(var0x -> listTeams(var0x.getSource()))
                  .then(Commands.argument("team", TeamArgument.team()).executes(var0x -> listMembers(var0x.getSource(), TeamArgument.getTeam(var0x, "team"))))
            )
            .then(
               Commands.literal("add")
                  .then(
                     ((RequiredArgumentBuilder)Commands.argument("team", StringArgumentType.word())
                           .executes(var0x -> createTeam((CommandSourceStack)var0x.getSource(), StringArgumentType.getString(var0x, "team"))))
                        .then(
                           Commands.argument("displayName", ComponentArgument.textComponent())
                              .executes(
                                 var0x -> createTeam(
                                       var0x.getSource(), StringArgumentType.getString(var0x, "team"), ComponentArgument.getComponent(var0x, "displayName")
                                    )
                              )
                        )
                  )
            )
            .then(
               Commands.literal("remove")
                  .then(Commands.argument("team", TeamArgument.team()).executes(var0x -> deleteTeam(var0x.getSource(), TeamArgument.getTeam(var0x, "team"))))
            )
            .then(
               Commands.literal("empty")
                  .then(Commands.argument("team", TeamArgument.team()).executes(var0x -> emptyTeam(var0x.getSource(), TeamArgument.getTeam(var0x, "team"))))
            )
            .then(
               Commands.literal("join")
                  .then(
                     ((RequiredArgumentBuilder)Commands.argument("team", TeamArgument.team())
                           .executes(
                              var0x -> joinTeam(
                                    (CommandSourceStack)var0x.getSource(),
                                    TeamArgument.getTeam(var0x, "team"),
                                    Collections.singleton(((CommandSourceStack)var0x.getSource()).getEntityOrException().getScoreboardName())
                                 )
                           ))
                        .then(
                           Commands.argument("members", ScoreHolderArgument.scoreHolders())
                              .suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
                              .executes(
                                 var0x -> joinTeam(
                                       var0x.getSource(),
                                       TeamArgument.getTeam(var0x, "team"),
                                       ScoreHolderArgument.getNamesWithDefaultWildcard(var0x, "members")
                                    )
                              )
                        )
                  )
            )
            .then(
               Commands.literal("leave")
                  .then(
                     Commands.argument("members", ScoreHolderArgument.scoreHolders())
                        .suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
                        .executes(var0x -> leaveTeam(var0x.getSource(), ScoreHolderArgument.getNamesWithDefaultWildcard(var0x, "members")))
                  )
            )
            .then(
               Commands.literal("modify")
                  .then(
                     ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument(
                                                   "team", TeamArgument.team()
                                                )
                                                .then(
                                                   Commands.literal("displayName")
                                                      .then(
                                                         Commands.argument("displayName", ComponentArgument.textComponent())
                                                            .executes(
                                                               var0x -> setDisplayName(
                                                                     var0x.getSource(),
                                                                     TeamArgument.getTeam(var0x, "team"),
                                                                     ComponentArgument.getComponent(var0x, "displayName")
                                                                  )
                                                            )
                                                      )
                                                ))
                                             .then(
                                                Commands.literal("color")
                                                   .then(
                                                      Commands.argument("value", ColorArgument.color())
                                                         .executes(
                                                            var0x -> setColor(
                                                                  var0x.getSource(),
                                                                  TeamArgument.getTeam(var0x, "team"),
                                                                  ColorArgument.getColor(var0x, "value")
                                                               )
                                                         )
                                                   )
                                             ))
                                          .then(
                                             Commands.literal("friendlyFire")
                                                .then(
                                                   Commands.argument("allowed", BoolArgumentType.bool())
                                                      .executes(
                                                         var0x -> setFriendlyFire(
                                                               var0x.getSource(),
                                                               TeamArgument.getTeam(var0x, "team"),
                                                               BoolArgumentType.getBool(var0x, "allowed")
                                                            )
                                                      )
                                                )
                                          ))
                                       .then(
                                          Commands.literal("seeFriendlyInvisibles")
                                             .then(
                                                Commands.argument("allowed", BoolArgumentType.bool())
                                                   .executes(
                                                      var0x -> setFriendlySight(
                                                            var0x.getSource(), TeamArgument.getTeam(var0x, "team"), BoolArgumentType.getBool(var0x, "allowed")
                                                         )
                                                   )
                                             )
                                       ))
                                    .then(
                                       Commands.literal("nametagVisibility")
                                          .then(
                                             Commands.literal("never")
                                                .executes(
                                                   var0x -> setNametagVisibility(var0x.getSource(), TeamArgument.getTeam(var0x, "team"), Team.Visibility.NEVER)
                                                )
                                          )
                                          .then(
                                             Commands.literal("hideForOtherTeams")
                                                .executes(
                                                   var0x -> setNametagVisibility(
                                                         var0x.getSource(), TeamArgument.getTeam(var0x, "team"), Team.Visibility.HIDE_FOR_OTHER_TEAMS
                                                      )
                                                )
                                          )
                                          .then(
                                             Commands.literal("hideForOwnTeam")
                                                .executes(
                                                   var0x -> setNametagVisibility(
                                                         var0x.getSource(), TeamArgument.getTeam(var0x, "team"), Team.Visibility.HIDE_FOR_OWN_TEAM
                                                      )
                                                )
                                          )
                                          .then(
                                             Commands.literal("always")
                                                .executes(
                                                   var0x -> setNametagVisibility(var0x.getSource(), TeamArgument.getTeam(var0x, "team"), Team.Visibility.ALWAYS)
                                                )
                                          )
                                    ))
                                 .then(
                                    Commands.literal("deathMessageVisibility")
                                       .then(
                                          Commands.literal("never")
                                             .executes(
                                                var0x -> setDeathMessageVisibility(
                                                      var0x.getSource(), TeamArgument.getTeam(var0x, "team"), Team.Visibility.NEVER
                                                   )
                                             )
                                       )
                                       .then(
                                          Commands.literal("hideForOtherTeams")
                                             .executes(
                                                var0x -> setDeathMessageVisibility(
                                                      var0x.getSource(), TeamArgument.getTeam(var0x, "team"), Team.Visibility.HIDE_FOR_OTHER_TEAMS
                                                   )
                                             )
                                       )
                                       .then(
                                          Commands.literal("hideForOwnTeam")
                                             .executes(
                                                var0x -> setDeathMessageVisibility(
                                                      var0x.getSource(), TeamArgument.getTeam(var0x, "team"), Team.Visibility.HIDE_FOR_OWN_TEAM
                                                   )
                                             )
                                       )
                                       .then(
                                          Commands.literal("always")
                                             .executes(
                                                var0x -> setDeathMessageVisibility(
                                                      var0x.getSource(), TeamArgument.getTeam(var0x, "team"), Team.Visibility.ALWAYS
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 Commands.literal("collisionRule")
                                    .then(
                                       Commands.literal("never")
                                          .executes(var0x -> setCollision(var0x.getSource(), TeamArgument.getTeam(var0x, "team"), Team.CollisionRule.NEVER))
                                    )
                                    .then(
                                       Commands.literal("pushOwnTeam")
                                          .executes(
                                             var0x -> setCollision(var0x.getSource(), TeamArgument.getTeam(var0x, "team"), Team.CollisionRule.PUSH_OWN_TEAM)
                                          )
                                    )
                                    .then(
                                       Commands.literal("pushOtherTeams")
                                          .executes(
                                             var0x -> setCollision(var0x.getSource(), TeamArgument.getTeam(var0x, "team"), Team.CollisionRule.PUSH_OTHER_TEAMS)
                                          )
                                    )
                                    .then(
                                       Commands.literal("always")
                                          .executes(var0x -> setCollision(var0x.getSource(), TeamArgument.getTeam(var0x, "team"), Team.CollisionRule.ALWAYS))
                                    )
                              ))
                           .then(
                              Commands.literal("prefix")
                                 .then(
                                    Commands.argument("prefix", ComponentArgument.textComponent())
                                       .executes(
                                          var0x -> setPrefix(
                                                var0x.getSource(), TeamArgument.getTeam(var0x, "team"), ComponentArgument.getComponent(var0x, "prefix")
                                             )
                                       )
                                 )
                           ))
                        .then(
                           Commands.literal("suffix")
                              .then(
                                 Commands.argument("suffix", ComponentArgument.textComponent())
                                    .executes(
                                       var0x -> setSuffix(
                                             var0x.getSource(), TeamArgument.getTeam(var0x, "team"), ComponentArgument.getComponent(var0x, "suffix")
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int leaveTeam(CommandSourceStack var0, Collection<String> var1) {
      Scoreboard â˜ƒ = â˜ƒ.getServer().getScoreboard();

      for(String â˜ƒx : â˜ƒ) {
         â˜ƒ.removePlayerFromTeam(â˜ƒx);
      }

      if (â˜ƒ.size() == 1) {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.team.leave.success.single", â˜ƒ.iterator().next()), true);
      } else {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.team.leave.success.multiple", â˜ƒ.size()), true);
      }

      return â˜ƒ.size();
   }

   private static int joinTeam(CommandSourceStack var0, PlayerTeam var1, Collection<String> var2) {
      Scoreboard â˜ƒ = â˜ƒ.getServer().getScoreboard();

      for(String â˜ƒx : â˜ƒ) {
         â˜ƒ.addPlayerToTeam(â˜ƒx, â˜ƒ);
      }

      if (â˜ƒ.size() == 1) {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.team.join.success.single", â˜ƒ.iterator().next(), â˜ƒ.getFormattedDisplayName()), true);
      } else {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.team.join.success.multiple", â˜ƒ.size(), â˜ƒ.getFormattedDisplayName()), true);
      }

      return â˜ƒ.size();
   }

   private static int setNametagVisibility(CommandSourceStack var0, PlayerTeam var1, Team.Visibility var2) throws CommandSyntaxException {
      if (â˜ƒ.getNameTagVisibility() == â˜ƒ) {
         throw ERROR_TEAM_NAMETAG_VISIBLITY_UNCHANGED.create();
      } else {
         â˜ƒ.setNameTagVisibility(â˜ƒ);
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.team.option.nametagVisibility.success", â˜ƒ.getFormattedDisplayName(), â˜ƒ.getDisplayName()), true);
         return 0;
      }
   }

   private static int setDeathMessageVisibility(CommandSourceStack var0, PlayerTeam var1, Team.Visibility var2) throws CommandSyntaxException {
      if (â˜ƒ.getDeathMessageVisibility() == â˜ƒ) {
         throw ERROR_TEAM_DEATH_MESSAGE_VISIBLITY_UNCHANGED.create();
      } else {
         â˜ƒ.setDeathMessageVisibility(â˜ƒ);
         â˜ƒ.sendSuccess(
            new TranslatableComponent("commands.team.option.deathMessageVisibility.success", â˜ƒ.getFormattedDisplayName(), â˜ƒ.getDisplayName()), true
         );
         return 0;
      }
   }

   private static int setCollision(CommandSourceStack var0, PlayerTeam var1, Team.CollisionRule var2) throws CommandSyntaxException {
      if (â˜ƒ.getCollisionRule() == â˜ƒ) {
         throw ERROR_TEAM_COLLISION_UNCHANGED.create();
      } else {
         â˜ƒ.setCollisionRule(â˜ƒ);
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.team.option.collisionRule.success", â˜ƒ.getFormattedDisplayName(), â˜ƒ.getDisplayName()), true);
         return 0;
      }
   }

   private static int setFriendlySight(CommandSourceStack var0, PlayerTeam var1, boolean var2) throws CommandSyntaxException {
      if (â˜ƒ.canSeeFriendlyInvisibles() == â˜ƒ) {
         if (â˜ƒ) {
            throw ERROR_TEAM_ALREADY_FRIENDLYINVISIBLES_ENABLED.create();
         } else {
            throw ERROR_TEAM_ALREADY_FRIENDLYINVISIBLES_DISABLED.create();
         }
      } else {
         â˜ƒ.setSeeFriendlyInvisibles(â˜ƒ);
         â˜ƒ.sendSuccess(
            new TranslatableComponent("commands.team.option.seeFriendlyInvisibles." + (â˜ƒ ? "enabled" : "disabled"), â˜ƒ.getFormattedDisplayName()), true
         );
         return 0;
      }
   }

   private static int setFriendlyFire(CommandSourceStack var0, PlayerTeam var1, boolean var2) throws CommandSyntaxException {
      if (â˜ƒ.isAllowFriendlyFire() == â˜ƒ) {
         if (â˜ƒ) {
            throw ERROR_TEAM_ALREADY_FRIENDLYFIRE_ENABLED.create();
         } else {
            throw ERROR_TEAM_ALREADY_FRIENDLYFIRE_DISABLED.create();
         }
      } else {
         â˜ƒ.setAllowFriendlyFire(â˜ƒ);
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.team.option.friendlyfire." + (â˜ƒ ? "enabled" : "disabled"), â˜ƒ.getFormattedDisplayName()), true);
         return 0;
      }
   }

   private static int setDisplayName(CommandSourceStack var0, PlayerTeam var1, Component var2) throws CommandSyntaxException {
      if (â˜ƒ.getDisplayName().equals(â˜ƒ)) {
         throw ERROR_TEAM_ALREADY_NAME.create();
      } else {
         â˜ƒ.setDisplayName(â˜ƒ);
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.team.option.name.success", â˜ƒ.getFormattedDisplayName()), true);
         return 0;
      }
   }

   private static int setColor(CommandSourceStack var0, PlayerTeam var1, ChatFormatting var2) throws CommandSyntaxException {
      if (â˜ƒ.getColor() == â˜ƒ) {
         throw ERROR_TEAM_ALREADY_COLOR.create();
      } else {
         â˜ƒ.setColor(â˜ƒ);
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.team.option.color.success", â˜ƒ.getFormattedDisplayName(), â˜ƒ.getName()), true);
         return 0;
      }
   }

   private static int emptyTeam(CommandSourceStack var0, PlayerTeam var1) throws CommandSyntaxException {
      Scoreboard â˜ƒ = â˜ƒ.getServer().getScoreboard();
      Collection<String> â˜ƒx = Lists.newArrayList(â˜ƒ.getPlayers());
      if (â˜ƒx.isEmpty()) {
         throw ERROR_TEAM_ALREADY_EMPTY.create();
      } else {
         for(String â˜ƒ : â˜ƒx) {
            â˜ƒ.removePlayerFromTeam(â˜ƒ, â˜ƒ);
         }

         â˜ƒ.sendSuccess(new TranslatableComponent("commands.team.empty.success", â˜ƒx.size(), â˜ƒ.getFormattedDisplayName()), true);
         return â˜ƒx.size();
      }
   }

   private static int deleteTeam(CommandSourceStack var0, PlayerTeam var1) {
      Scoreboard â˜ƒ = â˜ƒ.getServer().getScoreboard();
      â˜ƒ.removePlayerTeam(â˜ƒ);
      â˜ƒ.sendSuccess(new TranslatableComponent("commands.team.remove.success", â˜ƒ.getFormattedDisplayName()), true);
      return â˜ƒ.getPlayerTeams().size();
   }

   private static int createTeam(CommandSourceStack var0, String var1) throws CommandSyntaxException {
      return createTeam(â˜ƒ, â˜ƒ, new TextComponent(â˜ƒ));
   }

   private static int createTeam(CommandSourceStack var0, String var1, Component var2) throws CommandSyntaxException {
      Scoreboard â˜ƒ = â˜ƒ.getServer().getScoreboard();
      if (â˜ƒ.getPlayerTeam(â˜ƒ) != null) {
         throw ERROR_TEAM_ALREADY_EXISTS.create();
      } else if (â˜ƒ.length() > 16) {
         throw ERROR_TEAM_NAME_TOO_LONG.create(16);
      } else {
         PlayerTeam â˜ƒ = â˜ƒ.addPlayerTeam(â˜ƒ);
         â˜ƒ.setDisplayName(â˜ƒ);
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.team.add.success", â˜ƒ.getFormattedDisplayName()), true);
         return â˜ƒ.getPlayerTeams().size();
      }
   }

   private static int listMembers(CommandSourceStack var0, PlayerTeam var1) {
      Collection<String> â˜ƒ = â˜ƒ.getPlayers();
      if (â˜ƒ.isEmpty()) {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.team.list.members.empty", â˜ƒ.getFormattedDisplayName()), false);
      } else {
         â˜ƒ.sendSuccess(
            new TranslatableComponent("commands.team.list.members.success", â˜ƒ.getFormattedDisplayName(), â˜ƒ.size(), ComponentUtils.formatList(â˜ƒ)), false
         );
      }

      return â˜ƒ.size();
   }

   private static int listTeams(CommandSourceStack var0) {
      Collection<PlayerTeam> â˜ƒ = â˜ƒ.getServer().getScoreboard().getPlayerTeams();
      if (â˜ƒ.isEmpty()) {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.team.list.teams.empty"), false);
      } else {
         â˜ƒ.sendSuccess(
            new TranslatableComponent("commands.team.list.teams.success", â˜ƒ.size(), ComponentUtils.formatList(â˜ƒ, PlayerTeam::getFormattedDisplayName)),
            false
         );
      }

      return â˜ƒ.size();
   }

   private static int setPrefix(CommandSourceStack var0, PlayerTeam var1, Component var2) {
      â˜ƒ.setPlayerPrefix(â˜ƒ);
      â˜ƒ.sendSuccess(new TranslatableComponent("commands.team.option.prefix.success", â˜ƒ), false);
      return 1;
   }

   private static int setSuffix(CommandSourceStack var0, PlayerTeam var1, Component var2) {
      â˜ƒ.setPlayerSuffix(â˜ƒ);
      â˜ƒ.sendSuccess(new TranslatableComponent("commands.team.option.suffix.success", â˜ƒ), false);
      return 1;
   }
}
