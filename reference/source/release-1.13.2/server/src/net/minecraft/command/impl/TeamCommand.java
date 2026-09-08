package net.minecraft.command.impl;

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
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.ColorArgument;
import net.minecraft.command.arguments.ComponentArgument;
import net.minecraft.command.arguments.ScoreHolderArgument;
import net.minecraft.command.arguments.TeamArgument;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;

public class TeamCommand {
   private static final SimpleCommandExceptionType field_198793_a = new SimpleCommandExceptionType(new TextComponentTranslation("commands.team.add.duplicate"));
   private static final DynamicCommandExceptionType field_198794_b = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("commands.team.add.longName", var0)
   );
   private static final SimpleCommandExceptionType field_198796_d = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.team.empty.unchanged")
   );
   private static final SimpleCommandExceptionType field_211921_d = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.team.option.name.unchanged")
   );
   private static final SimpleCommandExceptionType field_198797_e = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.team.option.color.unchanged")
   );
   private static final SimpleCommandExceptionType field_198798_f = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.team.option.friendlyfire.alreadyEnabled")
   );
   private static final SimpleCommandExceptionType field_198799_g = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.team.option.friendlyfire.alreadyDisabled")
   );
   private static final SimpleCommandExceptionType field_198800_h = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.team.option.seeFriendlyInvisibles.alreadyEnabled")
   );
   private static final SimpleCommandExceptionType field_198801_i = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.team.option.seeFriendlyInvisibles.alreadyDisabled")
   );
   private static final SimpleCommandExceptionType field_198802_j = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.team.option.nametagVisibility.unchanged")
   );
   private static final SimpleCommandExceptionType field_198803_k = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.team.option.deathMessageVisibility.unchanged")
   );
   private static final SimpleCommandExceptionType field_198804_l = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.team.option.collisionRule.unchanged")
   );

   public static void func_198771_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("team")
            .requires(var0x -> var0x.func_197034_c(2))
            .then(
               Commands.func_197057_a("list")
                  .executes(var0x -> func_198792_a(var0x.getSource()))
                  .then(
                     Commands.func_197056_a("team", TeamArgument.func_197227_a())
                        .executes(var0x -> func_198782_c(var0x.getSource(), TeamArgument.func_197228_a(var0x, "team")))
                  )
            )
            .then(
               Commands.func_197057_a("add")
                  .then(
                     ((RequiredArgumentBuilder)Commands.func_197056_a("team", StringArgumentType.word())
                           .executes(var0x -> func_211916_a((CommandSource)var0x.getSource(), StringArgumentType.getString(var0x, "team"))))
                        .then(
                           Commands.func_197056_a("displayName", ComponentArgument.func_197067_a())
                              .executes(
                                 var0x -> func_211917_a(
                                       var0x.getSource(), StringArgumentType.getString(var0x, "team"), ComponentArgument.func_197068_a(var0x, "displayName")
                                    )
                              )
                        )
                  )
            )
            .then(
               Commands.func_197057_a("remove")
                  .then(
                     Commands.func_197056_a("team", TeamArgument.func_197227_a())
                        .executes(var0x -> func_198784_b(var0x.getSource(), TeamArgument.func_197228_a(var0x, "team")))
                  )
            )
            .then(
               Commands.func_197057_a("empty")
                  .then(
                     Commands.func_197056_a("team", TeamArgument.func_197227_a())
                        .executes(var0x -> func_198788_a(var0x.getSource(), TeamArgument.func_197228_a(var0x, "team")))
                  )
            )
            .then(
               Commands.func_197057_a("join")
                  .then(
                     ((RequiredArgumentBuilder)Commands.func_197056_a("team", TeamArgument.func_197227_a())
                           .executes(
                              var0x -> func_198768_a(
                                    (CommandSource)var0x.getSource(),
                                    TeamArgument.func_197228_a(var0x, "team"),
                                    Collections.singleton(((CommandSource)var0x.getSource()).func_197027_g().func_195047_I_())
                                 )
                           ))
                        .then(
                           Commands.func_197056_a("members", ScoreHolderArgument.func_197214_b())
                              .suggests(ScoreHolderArgument.field_201326_a)
                              .executes(
                                 var0x -> func_198768_a(
                                       var0x.getSource(), TeamArgument.func_197228_a(var0x, "team"), ScoreHolderArgument.func_211707_c(var0x, "members")
                                    )
                              )
                        )
                  )
            )
            .then(
               Commands.func_197057_a("leave")
                  .then(
                     Commands.func_197056_a("members", ScoreHolderArgument.func_197214_b())
                        .suggests(ScoreHolderArgument.field_201326_a)
                        .executes(var0x -> func_198786_a(var0x.getSource(), ScoreHolderArgument.func_211707_c(var0x, "members")))
                  )
            )
            .then(
               Commands.func_197057_a("modify")
                  .then(
                     ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.func_197056_a(
                                                   "team", TeamArgument.func_197227_a()
                                                )
                                                .then(
                                                   Commands.func_197057_a("displayName")
                                                      .then(
                                                         Commands.func_197056_a("displayName", ComponentArgument.func_197067_a())
                                                            .executes(
                                                               var0x -> func_211920_a(
                                                                     var0x.getSource(),
                                                                     TeamArgument.func_197228_a(var0x, "team"),
                                                                     ComponentArgument.func_197068_a(var0x, "displayName")
                                                                  )
                                                            )
                                                      )
                                                ))
                                             .then(
                                                Commands.func_197057_a("color")
                                                   .then(
                                                      Commands.func_197056_a("value", ColorArgument.func_197063_a())
                                                         .executes(
                                                            var0x -> func_198757_a(
                                                                  var0x.getSource(),
                                                                  TeamArgument.func_197228_a(var0x, "team"),
                                                                  ColorArgument.func_197064_a(var0x, "value")
                                                               )
                                                         )
                                                   )
                                             ))
                                          .then(
                                             Commands.func_197057_a("friendlyFire")
                                                .then(
                                                   Commands.func_197056_a("allowed", BoolArgumentType.bool())
                                                      .executes(
                                                         var0x -> func_198781_b(
                                                               var0x.getSource(),
                                                               TeamArgument.func_197228_a(var0x, "team"),
                                                               BoolArgumentType.getBool(var0x, "allowed")
                                                            )
                                                      )
                                                )
                                          ))
                                       .then(
                                          Commands.func_197057_a("seeFriendlyInvisibles")
                                             .then(
                                                Commands.func_197056_a("allowed", BoolArgumentType.bool())
                                                   .executes(
                                                      var0x -> func_198783_a(
                                                            var0x.getSource(),
                                                            TeamArgument.func_197228_a(var0x, "team"),
                                                            BoolArgumentType.getBool(var0x, "allowed")
                                                         )
                                                   )
                                             )
                                       ))
                                    .then(
                                       Commands.func_197057_a("nametagVisibility")
                                          .then(
                                             Commands.func_197057_a("never")
                                                .executes(
                                                   var0x -> func_198777_a(var0x.getSource(), TeamArgument.func_197228_a(var0x, "team"), Team.EnumVisible.NEVER)
                                                )
                                          )
                                          .then(
                                             Commands.func_197057_a("hideForOtherTeams")
                                                .executes(
                                                   var0x -> func_198777_a(
                                                         var0x.getSource(), TeamArgument.func_197228_a(var0x, "team"), Team.EnumVisible.HIDE_FOR_OTHER_TEAMS
                                                      )
                                                )
                                          )
                                          .then(
                                             Commands.func_197057_a("hideForOwnTeam")
                                                .executes(
                                                   var0x -> func_198777_a(
                                                         var0x.getSource(), TeamArgument.func_197228_a(var0x, "team"), Team.EnumVisible.HIDE_FOR_OWN_TEAM
                                                      )
                                                )
                                          )
                                          .then(
                                             Commands.func_197057_a("always")
                                                .executes(
                                                   var0x -> func_198777_a(var0x.getSource(), TeamArgument.func_197228_a(var0x, "team"), Team.EnumVisible.ALWAYS)
                                                )
                                          )
                                    ))
                                 .then(
                                    Commands.func_197057_a("deathMessageVisibility")
                                       .then(
                                          Commands.func_197057_a("never")
                                             .executes(
                                                var0x -> func_198776_b(var0x.getSource(), TeamArgument.func_197228_a(var0x, "team"), Team.EnumVisible.NEVER)
                                             )
                                       )
                                       .then(
                                          Commands.func_197057_a("hideForOtherTeams")
                                             .executes(
                                                var0x -> func_198776_b(
                                                      var0x.getSource(), TeamArgument.func_197228_a(var0x, "team"), Team.EnumVisible.HIDE_FOR_OTHER_TEAMS
                                                   )
                                             )
                                       )
                                       .then(
                                          Commands.func_197057_a("hideForOwnTeam")
                                             .executes(
                                                var0x -> func_198776_b(
                                                      var0x.getSource(), TeamArgument.func_197228_a(var0x, "team"), Team.EnumVisible.HIDE_FOR_OWN_TEAM
                                                   )
                                             )
                                       )
                                       .then(
                                          Commands.func_197057_a("always")
                                             .executes(
                                                var0x -> func_198776_b(var0x.getSource(), TeamArgument.func_197228_a(var0x, "team"), Team.EnumVisible.ALWAYS)
                                             )
                                       )
                                 ))
                              .then(
                                 Commands.func_197057_a("collisionRule")
                                    .then(
                                       Commands.func_197057_a("never")
                                          .executes(
                                             var0x -> func_198787_a(var0x.getSource(), TeamArgument.func_197228_a(var0x, "team"), Team.CollisionRule.NEVER)
                                          )
                                    )
                                    .then(
                                       Commands.func_197057_a("pushOwnTeam")
                                          .executes(
                                             var0x -> func_198787_a(
                                                   var0x.getSource(), TeamArgument.func_197228_a(var0x, "team"), Team.CollisionRule.PUSH_OWN_TEAM
                                                )
                                          )
                                    )
                                    .then(
                                       Commands.func_197057_a("pushOtherTeams")
                                          .executes(
                                             var0x -> func_198787_a(
                                                   var0x.getSource(), TeamArgument.func_197228_a(var0x, "team"), Team.CollisionRule.PUSH_OTHER_TEAMS
                                                )
                                          )
                                    )
                                    .then(
                                       Commands.func_197057_a("always")
                                          .executes(
                                             var0x -> func_198787_a(var0x.getSource(), TeamArgument.func_197228_a(var0x, "team"), Team.CollisionRule.ALWAYS)
                                          )
                                    )
                              ))
                           .then(
                              Commands.func_197057_a("prefix")
                                 .then(
                                    Commands.func_197056_a("prefix", ComponentArgument.func_197067_a())
                                       .executes(
                                          var0x -> func_207515_a(
                                                var0x.getSource(), TeamArgument.func_197228_a(var0x, "team"), ComponentArgument.func_197068_a(var0x, "prefix")
                                             )
                                       )
                                 )
                           ))
                        .then(
                           Commands.func_197057_a("suffix")
                              .then(
                                 Commands.func_197056_a("suffix", ComponentArgument.func_197067_a())
                                    .executes(
                                       var0x -> func_207517_b(
                                             var0x.getSource(), TeamArgument.func_197228_a(var0x, "team"), ComponentArgument.func_197068_a(var0x, "suffix")
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int func_198786_a(CommandSource var0, Collection<String> var1) {
      Scoreboard ☃ = ☃.func_197028_i().func_200251_aP();

      for(String ☃x : ☃) {
         ☃.func_96524_g(☃x);
      }

      if (☃.size() == 1) {
         ☃.func_197030_a(new TextComponentTranslation("commands.team.leave.success.single", ☃.iterator().next()), true);
      } else {
         ☃.func_197030_a(new TextComponentTranslation("commands.team.leave.success.multiple", ☃.size()), true);
      }

      return ☃.size();
   }

   private static int func_198768_a(CommandSource var0, ScorePlayerTeam var1, Collection<String> var2) {
      Scoreboard ☃ = ☃.func_197028_i().func_200251_aP();

      for(String ☃x : ☃) {
         ☃.func_197901_a(☃x, ☃);
      }

      if (☃.size() == 1) {
         ☃.func_197030_a(new TextComponentTranslation("commands.team.join.success.single", ☃.iterator().next(), ☃.func_197892_d()), true);
      } else {
         ☃.func_197030_a(new TextComponentTranslation("commands.team.join.success.multiple", ☃.size(), ☃.func_197892_d()), true);
      }

      return ☃.size();
   }

   private static int func_198777_a(CommandSource var0, ScorePlayerTeam var1, Team.EnumVisible var2) throws CommandSyntaxException {
      if (☃.func_178770_i() == ☃) {
         throw field_198802_j.create();
      } else {
         ☃.func_178772_a(☃);
         ☃.func_197030_a(new TextComponentTranslation("commands.team.option.nametagVisibility.success", ☃.func_197892_d(), ☃.func_197910_b()), true);
         return 0;
      }
   }

   private static int func_198776_b(CommandSource var0, ScorePlayerTeam var1, Team.EnumVisible var2) throws CommandSyntaxException {
      if (☃.func_178771_j() == ☃) {
         throw field_198803_k.create();
      } else {
         ☃.func_178773_b(☃);
         ☃.func_197030_a(new TextComponentTranslation("commands.team.option.deathMessageVisibility.success", ☃.func_197892_d(), ☃.func_197910_b()), true);
         return 0;
      }
   }

   private static int func_198787_a(CommandSource var0, ScorePlayerTeam var1, Team.CollisionRule var2) throws CommandSyntaxException {
      if (☃.func_186681_k() == ☃) {
         throw field_198804_l.create();
      } else {
         ☃.func_186682_a(☃);
         ☃.func_197030_a(new TextComponentTranslation("commands.team.option.collisionRule.success", ☃.func_197892_d(), ☃.func_197907_b()), true);
         return 0;
      }
   }

   private static int func_198783_a(CommandSource var0, ScorePlayerTeam var1, boolean var2) throws CommandSyntaxException {
      if (☃.func_98297_h() == ☃) {
         if (☃) {
            throw field_198800_h.create();
         } else {
            throw field_198801_i.create();
         }
      } else {
         ☃.func_98300_b(☃);
         ☃.func_197030_a(new TextComponentTranslation("commands.team.option.seeFriendlyInvisibles." + (☃ ? "enabled" : "disabled"), ☃.func_197892_d()), true);
         return 0;
      }
   }

   private static int func_198781_b(CommandSource var0, ScorePlayerTeam var1, boolean var2) throws CommandSyntaxException {
      if (☃.func_96665_g() == ☃) {
         if (☃) {
            throw field_198798_f.create();
         } else {
            throw field_198799_g.create();
         }
      } else {
         ☃.func_96660_a(☃);
         ☃.func_197030_a(new TextComponentTranslation("commands.team.option.friendlyfire." + (☃ ? "enabled" : "disabled"), ☃.func_197892_d()), true);
         return 0;
      }
   }

   private static int func_211920_a(CommandSource var0, ScorePlayerTeam var1, ITextComponent var2) throws CommandSyntaxException {
      if (☃.func_96669_c().equals(☃)) {
         throw field_211921_d.create();
      } else {
         ☃.func_96664_a(☃);
         ☃.func_197030_a(new TextComponentTranslation("commands.team.option.name.success", ☃.func_197892_d()), true);
         return 0;
      }
   }

   private static int func_198757_a(CommandSource var0, ScorePlayerTeam var1, TextFormatting var2) throws CommandSyntaxException {
      if (☃.func_178775_l() == ☃) {
         throw field_198797_e.create();
      } else {
         ☃.func_178774_a(☃);
         ☃.func_197030_a(new TextComponentTranslation("commands.team.option.color.success", ☃.func_197892_d(), ☃.func_96297_d()), true);
         return 0;
      }
   }

   private static int func_198788_a(CommandSource var0, ScorePlayerTeam var1) throws CommandSyntaxException {
      Scoreboard ☃ = ☃.func_197028_i().func_200251_aP();
      Collection<String> ☃x = Lists.newArrayList(☃.func_96670_d());
      if (☃x.isEmpty()) {
         throw field_198796_d.create();
      } else {
         for(String ☃ : ☃x) {
            ☃.func_96512_b(☃, ☃);
         }

         ☃.func_197030_a(new TextComponentTranslation("commands.team.empty.success", ☃x.size(), ☃.func_197892_d()), true);
         return ☃x.size();
      }
   }

   private static int func_198784_b(CommandSource var0, ScorePlayerTeam var1) {
      Scoreboard ☃ = ☃.func_197028_i().func_200251_aP();
      ☃.func_96511_d(☃);
      ☃.func_197030_a(new TextComponentTranslation("commands.team.remove.success", ☃.func_197892_d()), true);
      return ☃.func_96525_g().size();
   }

   private static int func_211916_a(CommandSource var0, String var1) throws CommandSyntaxException {
      return func_211917_a(☃, ☃, new TextComponentString(☃));
   }

   private static int func_211917_a(CommandSource var0, String var1, ITextComponent var2) throws CommandSyntaxException {
      Scoreboard ☃ = ☃.func_197028_i().func_200251_aP();
      if (☃.func_96508_e(☃) != null) {
         throw field_198793_a.create();
      } else if (☃.length() > 16) {
         throw field_198794_b.create(16);
      } else {
         ScorePlayerTeam ☃ = ☃.func_96527_f(☃);
         ☃.func_96664_a(☃);
         ☃.func_197030_a(new TextComponentTranslation("commands.team.add.success", ☃.func_197892_d()), true);
         return ☃.func_96525_g().size();
      }
   }

   private static int func_198782_c(CommandSource var0, ScorePlayerTeam var1) {
      Collection<String> ☃ = ☃.func_96670_d();
      if (☃.isEmpty()) {
         ☃.func_197030_a(new TextComponentTranslation("commands.team.list.members.empty", ☃.func_197892_d()), false);
      } else {
         ☃.func_197030_a(
            new TextComponentTranslation("commands.team.list.members.success", ☃.func_197892_d(), ☃.size(), TextComponentUtils.func_197678_a(☃)), false
         );
      }

      return ☃.size();
   }

   private static int func_198792_a(CommandSource var0) {
      Collection<ScorePlayerTeam> ☃ = ☃.func_197028_i().func_200251_aP().func_96525_g();
      if (☃.isEmpty()) {
         ☃.func_197030_a(new TextComponentTranslation("commands.team.list.teams.empty"), false);
      } else {
         ☃.func_197030_a(
            new TextComponentTranslation("commands.team.list.teams.success", ☃.size(), TextComponentUtils.func_197677_b(☃, ScorePlayerTeam::func_197892_d)),
            false
         );
      }

      return ☃.size();
   }

   private static int func_207515_a(CommandSource var0, ScorePlayerTeam var1, ITextComponent var2) {
      ☃.func_207408_a(☃);
      ☃.func_197030_a(new TextComponentTranslation("commands.team.option.prefix.success", ☃), false);
      return 1;
   }

   private static int func_207517_b(CommandSource var0, ScorePlayerTeam var1, ITextComponent var2) {
      ☃.func_207409_b(☃);
      ☃.func_197030_a(new TextComponentTranslation("commands.team.option.suffix.success", ☃), false);
      return 1;
   }
}
