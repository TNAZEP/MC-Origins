package net.minecraft.command.impl;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.ComponentArgument;
import net.minecraft.command.arguments.ObjectiveArgument;
import net.minecraft.command.arguments.ObjectiveCriteriaArgument;
import net.minecraft.command.arguments.OperationArgument;
import net.minecraft.command.arguments.ScoreHolderArgument;
import net.minecraft.command.arguments.ScoreboardSlotArgument;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextComponentUtils;

public class ScoreboardCommand {
   private static final SimpleCommandExceptionType field_198663_a = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.scoreboard.objectives.add.duplicate")
   );
   private static final SimpleCommandExceptionType field_198666_d = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.scoreboard.objectives.display.alreadyEmpty")
   );
   private static final SimpleCommandExceptionType field_198667_e = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.scoreboard.objectives.display.alreadySet")
   );
   private static final SimpleCommandExceptionType field_198668_f = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.scoreboard.players.enable.failed")
   );
   private static final SimpleCommandExceptionType field_198669_g = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.scoreboard.players.enable.invalid")
   );
   private static final Dynamic2CommandExceptionType field_198670_h = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TextComponentTranslation("commands.scoreboard.players.get.null", var0, var1)
   );

   public static void func_198647_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("scoreboard")
            .requires(var0x -> var0x.func_197034_c(2))
            .then(
               Commands.func_197057_a("objectives")
                  .then(Commands.func_197057_a("list").executes(var0x -> func_198662_b(var0x.getSource())))
                  .then(
                     Commands.func_197057_a("add")
                        .then(
                           Commands.func_197056_a("objective", StringArgumentType.word())
                              .then(
                                 Commands.func_197056_a("criteria", ObjectiveCriteriaArgument.func_197162_a())
                                    .executes(
                                       var0x -> func_198629_a(
                                             var0x.getSource(),
                                             StringArgumentType.getString(var0x, "objective"),
                                             ObjectiveCriteriaArgument.func_197161_a(var0x, "criteria"),
                                             new TextComponentString(StringArgumentType.getString(var0x, "objective"))
                                          )
                                    )
                                    .then(
                                       Commands.func_197056_a("displayName", ComponentArgument.func_197067_a())
                                          .executes(
                                             var0x -> func_198629_a(
                                                   var0x.getSource(),
                                                   StringArgumentType.getString(var0x, "objective"),
                                                   ObjectiveCriteriaArgument.func_197161_a(var0x, "criteria"),
                                                   ComponentArgument.func_197068_a(var0x, "displayName")
                                                )
                                          )
                                    )
                              )
                        )
                  )
                  .then(
                     Commands.func_197057_a("modify")
                        .then(
                           ((RequiredArgumentBuilder)Commands.func_197056_a("objective", ObjectiveArgument.func_197157_a())
                                 .then(
                                    Commands.func_197057_a("displayname")
                                       .then(
                                          Commands.func_197056_a("displayName", ComponentArgument.func_197067_a())
                                             .executes(
                                                var0x -> func_211749_a(
                                                      var0x.getSource(),
                                                      ObjectiveArgument.func_197158_a(var0x, "objective"),
                                                      ComponentArgument.func_197068_a(var0x, "displayName")
                                                   )
                                             )
                                       )
                                 ))
                              .then(func_211915_a())
                        )
                  )
                  .then(
                     Commands.func_197057_a("remove")
                        .then(
                           Commands.func_197056_a("objective", ObjectiveArgument.func_197157_a())
                              .executes(var0x -> func_198637_a(var0x.getSource(), ObjectiveArgument.func_197158_a(var0x, "objective")))
                        )
                  )
                  .then(
                     Commands.func_197057_a("setdisplay")
                        .then(
                           Commands.func_197056_a("slot", ScoreboardSlotArgument.func_197219_a())
                              .executes(var0x -> func_198632_a(var0x.getSource(), ScoreboardSlotArgument.func_197217_a(var0x, "slot")))
                              .then(
                                 Commands.func_197056_a("objective", ObjectiveArgument.func_197157_a())
                                    .executes(
                                       var0x -> func_198659_a(
                                             var0x.getSource(),
                                             ScoreboardSlotArgument.func_197217_a(var0x, "slot"),
                                             ObjectiveArgument.func_197158_a(var0x, "objective")
                                          )
                                    )
                              )
                        )
                  )
            )
            .then(
               Commands.func_197057_a("players")
                  .then(
                     Commands.func_197057_a("list")
                        .executes(var0x -> func_198661_a(var0x.getSource()))
                        .then(
                           Commands.func_197056_a("target", ScoreHolderArgument.func_197209_a())
                              .suggests(ScoreHolderArgument.field_201326_a)
                              .executes(var0x -> func_198643_a(var0x.getSource(), ScoreHolderArgument.func_197211_a(var0x, "target")))
                        )
                  )
                  .then(
                     Commands.func_197057_a("set")
                        .then(
                           Commands.func_197056_a("targets", ScoreHolderArgument.func_197214_b())
                              .suggests(ScoreHolderArgument.field_201326_a)
                              .then(
                                 Commands.func_197056_a("objective", ObjectiveArgument.func_197157_a())
                                    .then(
                                       Commands.func_197056_a("score", IntegerArgumentType.integer())
                                          .executes(
                                             var0x -> func_198653_a(
                                                   var0x.getSource(),
                                                   ScoreHolderArgument.func_211707_c(var0x, "targets"),
                                                   ObjectiveArgument.func_197156_b(var0x, "objective"),
                                                   IntegerArgumentType.getInteger(var0x, "score")
                                                )
                                          )
                                    )
                              )
                        )
                  )
                  .then(
                     Commands.func_197057_a("get")
                        .then(
                           Commands.func_197056_a("target", ScoreHolderArgument.func_197209_a())
                              .suggests(ScoreHolderArgument.field_201326_a)
                              .then(
                                 Commands.func_197056_a("objective", ObjectiveArgument.func_197157_a())
                                    .executes(
                                       var0x -> func_198634_a(
                                             var0x.getSource(),
                                             ScoreHolderArgument.func_197211_a(var0x, "target"),
                                             ObjectiveArgument.func_197158_a(var0x, "objective")
                                          )
                                    )
                              )
                        )
                  )
                  .then(
                     Commands.func_197057_a("add")
                        .then(
                           Commands.func_197056_a("targets", ScoreHolderArgument.func_197214_b())
                              .suggests(ScoreHolderArgument.field_201326_a)
                              .then(
                                 Commands.func_197056_a("objective", ObjectiveArgument.func_197157_a())
                                    .then(
                                       Commands.func_197056_a("score", IntegerArgumentType.integer(0))
                                          .executes(
                                             var0x -> func_198633_b(
                                                   var0x.getSource(),
                                                   ScoreHolderArgument.func_211707_c(var0x, "targets"),
                                                   ObjectiveArgument.func_197156_b(var0x, "objective"),
                                                   IntegerArgumentType.getInteger(var0x, "score")
                                                )
                                          )
                                    )
                              )
                        )
                  )
                  .then(
                     Commands.func_197057_a("remove")
                        .then(
                           Commands.func_197056_a("targets", ScoreHolderArgument.func_197214_b())
                              .suggests(ScoreHolderArgument.field_201326_a)
                              .then(
                                 Commands.func_197056_a("objective", ObjectiveArgument.func_197157_a())
                                    .then(
                                       Commands.func_197056_a("score", IntegerArgumentType.integer(0))
                                          .executes(
                                             var0x -> func_198651_c(
                                                   var0x.getSource(),
                                                   ScoreHolderArgument.func_211707_c(var0x, "targets"),
                                                   ObjectiveArgument.func_197156_b(var0x, "objective"),
                                                   IntegerArgumentType.getInteger(var0x, "score")
                                                )
                                          )
                                    )
                              )
                        )
                  )
                  .then(
                     Commands.func_197057_a("reset")
                        .then(
                           Commands.func_197056_a("targets", ScoreHolderArgument.func_197214_b())
                              .suggests(ScoreHolderArgument.field_201326_a)
                              .executes(var0x -> func_198654_a(var0x.getSource(), ScoreHolderArgument.func_211707_c(var0x, "targets")))
                              .then(
                                 Commands.func_197056_a("objective", ObjectiveArgument.func_197157_a())
                                    .executes(
                                       var0x -> func_198656_b(
                                             var0x.getSource(),
                                             ScoreHolderArgument.func_211707_c(var0x, "targets"),
                                             ObjectiveArgument.func_197158_a(var0x, "objective")
                                          )
                                    )
                              )
                        )
                  )
                  .then(
                     Commands.func_197057_a("enable")
                        .then(
                           Commands.func_197056_a("targets", ScoreHolderArgument.func_197214_b())
                              .suggests(ScoreHolderArgument.field_201326_a)
                              .then(
                                 Commands.func_197056_a("objective", ObjectiveArgument.func_197157_a())
                                    .suggests((var0x, var1) -> func_198641_a(var0x.getSource(), ScoreHolderArgument.func_211707_c(var0x, "targets"), var1))
                                    .executes(
                                       var0x -> func_198644_a(
                                             var0x.getSource(),
                                             ScoreHolderArgument.func_211707_c(var0x, "targets"),
                                             ObjectiveArgument.func_197158_a(var0x, "objective")
                                          )
                                    )
                              )
                        )
                  )
                  .then(
                     Commands.func_197057_a("operation")
                        .then(
                           Commands.func_197056_a("targets", ScoreHolderArgument.func_197214_b())
                              .suggests(ScoreHolderArgument.field_201326_a)
                              .then(
                                 Commands.func_197056_a("targetObjective", ObjectiveArgument.func_197157_a())
                                    .then(
                                       Commands.func_197056_a("operation", OperationArgument.func_197184_a())
                                          .then(
                                             Commands.func_197056_a("source", ScoreHolderArgument.func_197214_b())
                                                .suggests(ScoreHolderArgument.field_201326_a)
                                                .then(
                                                   Commands.func_197056_a("sourceObjective", ObjectiveArgument.func_197157_a())
                                                      .executes(
                                                         var0x -> func_198658_a(
                                                               var0x.getSource(),
                                                               ScoreHolderArgument.func_211707_c(var0x, "targets"),
                                                               ObjectiveArgument.func_197156_b(var0x, "targetObjective"),
                                                               OperationArgument.func_197179_a(var0x, "operation"),
                                                               ScoreHolderArgument.func_211707_c(var0x, "source"),
                                                               ObjectiveArgument.func_197158_a(var0x, "sourceObjective")
                                                            )
                                                      )
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static LiteralArgumentBuilder<CommandSource> func_211915_a() {
      LiteralArgumentBuilder<CommandSource> ☃ = Commands.func_197057_a("rendertype");

      for(ScoreCriteria.RenderType ☃x : ScoreCriteria.RenderType.values()) {
         ☃.then(
            Commands.func_197057_a(☃x.func_211838_a()).executes(var1 -> func_211910_a(var1.getSource(), ObjectiveArgument.func_197158_a(var1, "objective"), ☃))
         );
      }

      return ☃;
   }

   private static CompletableFuture<Suggestions> func_198641_a(CommandSource var0, Collection<String> var1, SuggestionsBuilder var2) {
      List<String> ☃ = Lists.newArrayList();
      Scoreboard ☃x = ☃.func_197028_i().func_200251_aP();

      for(ScoreObjective ☃xx : ☃x.func_96514_c()) {
         if (☃xx.func_96680_c() == ScoreCriteria.field_178791_c) {
            boolean ☃xxx = false;

            for(String ☃xxxx : ☃) {
               if (!☃x.func_178819_b(☃xxxx, ☃xx) || ☃x.func_96529_a(☃xxxx, ☃xx).func_178816_g()) {
                  ☃xxx = true;
                  break;
               }
            }

            if (☃xxx) {
               ☃.add(☃xx.func_96679_b());
            }
         }
      }

      return ISuggestionProvider.func_197005_b(☃, ☃);
   }

   private static int func_198634_a(CommandSource var0, String var1, ScoreObjective var2) throws CommandSyntaxException {
      Scoreboard ☃ = ☃.func_197028_i().func_200251_aP();
      if (!☃.func_178819_b(☃, ☃)) {
         throw field_198670_h.create(☃.func_96679_b(), ☃);
      } else {
         Score ☃ = ☃.func_96529_a(☃, ☃);
         ☃.func_197030_a(new TextComponentTranslation("commands.scoreboard.players.get.success", ☃, ☃.func_96652_c(), ☃.func_197890_e()), false);
         return ☃.func_96652_c();
      }
   }

   private static int func_198658_a(
      CommandSource var0, Collection<String> var1, ScoreObjective var2, OperationArgument.IOperation var3, Collection<String> var4, ScoreObjective var5
   ) throws CommandSyntaxException {
      Scoreboard ☃ = ☃.func_197028_i().func_200251_aP();
      int ☃x = 0;

      for(String ☃xx : ☃) {
         Score ☃xxx = ☃.func_96529_a(☃xx, ☃);

         for(String ☃xxxx : ☃) {
            Score ☃xxxxx = ☃.func_96529_a(☃xxxx, ☃);
            ☃.apply(☃xxx, ☃xxxxx);
         }

         ☃x += ☃xxx.func_96652_c();
      }

      if (☃.size() == 1) {
         ☃.func_197030_a(new TextComponentTranslation("commands.scoreboard.players.operation.success.single", ☃.func_197890_e(), ☃.iterator().next(), ☃x), true);
      } else {
         ☃.func_197030_a(new TextComponentTranslation("commands.scoreboard.players.operation.success.multiple", ☃.func_197890_e(), ☃.size()), true);
      }

      return ☃x;
   }

   private static int func_198644_a(CommandSource var0, Collection<String> var1, ScoreObjective var2) throws CommandSyntaxException {
      if (☃.func_96680_c() != ScoreCriteria.field_178791_c) {
         throw field_198669_g.create();
      } else {
         Scoreboard ☃ = ☃.func_197028_i().func_200251_aP();
         int ☃x = 0;

         for(String ☃xx : ☃) {
            Score ☃xxx = ☃.func_96529_a(☃xx, ☃);
            if (☃xxx.func_178816_g()) {
               ☃xxx.func_178815_a(false);
               ++☃x;
            }
         }

         if (☃x == 0) {
            throw field_198668_f.create();
         } else {
            if (☃.size() == 1) {
               ☃.func_197030_a(new TextComponentTranslation("commands.scoreboard.players.enable.success.single", ☃.func_197890_e(), ☃.iterator().next()), true);
            } else {
               ☃.func_197030_a(new TextComponentTranslation("commands.scoreboard.players.enable.success.multiple", ☃.func_197890_e(), ☃.size()), true);
            }

            return ☃x;
         }
      }
   }

   private static int func_198654_a(CommandSource var0, Collection<String> var1) {
      Scoreboard ☃ = ☃.func_197028_i().func_200251_aP();

      for(String ☃x : ☃) {
         ☃.func_178822_d(☃x, null);
      }

      if (☃.size() == 1) {
         ☃.func_197030_a(new TextComponentTranslation("commands.scoreboard.players.reset.all.single", ☃.iterator().next()), true);
      } else {
         ☃.func_197030_a(new TextComponentTranslation("commands.scoreboard.players.reset.all.multiple", ☃.size()), true);
      }

      return ☃.size();
   }

   private static int func_198656_b(CommandSource var0, Collection<String> var1, ScoreObjective var2) {
      Scoreboard ☃ = ☃.func_197028_i().func_200251_aP();

      for(String ☃x : ☃) {
         ☃.func_178822_d(☃x, ☃);
      }

      if (☃.size() == 1) {
         ☃.func_197030_a(new TextComponentTranslation("commands.scoreboard.players.reset.specific.single", ☃.func_197890_e(), ☃.iterator().next()), true);
      } else {
         ☃.func_197030_a(new TextComponentTranslation("commands.scoreboard.players.reset.specific.multiple", ☃.func_197890_e(), ☃.size()), true);
      }

      return ☃.size();
   }

   private static int func_198653_a(CommandSource var0, Collection<String> var1, ScoreObjective var2, int var3) {
      Scoreboard ☃ = ☃.func_197028_i().func_200251_aP();

      for(String ☃x : ☃) {
         Score ☃xx = ☃.func_96529_a(☃x, ☃);
         ☃xx.func_96647_c(☃);
      }

      if (☃.size() == 1) {
         ☃.func_197030_a(new TextComponentTranslation("commands.scoreboard.players.set.success.single", ☃.func_197890_e(), ☃.iterator().next(), ☃), true);
      } else {
         ☃.func_197030_a(new TextComponentTranslation("commands.scoreboard.players.set.success.multiple", ☃.func_197890_e(), ☃.size(), ☃), true);
      }

      return ☃ * ☃.size();
   }

   private static int func_198633_b(CommandSource var0, Collection<String> var1, ScoreObjective var2, int var3) {
      Scoreboard ☃ = ☃.func_197028_i().func_200251_aP();
      int ☃x = 0;

      for(String ☃xx : ☃) {
         Score ☃xxx = ☃.func_96529_a(☃xx, ☃);
         ☃xxx.func_96647_c(☃xxx.func_96652_c() + ☃);
         ☃x += ☃xxx.func_96652_c();
      }

      if (☃.size() == 1) {
         ☃.func_197030_a(new TextComponentTranslation("commands.scoreboard.players.add.success.single", ☃, ☃.func_197890_e(), ☃.iterator().next(), ☃x), true);
      } else {
         ☃.func_197030_a(new TextComponentTranslation("commands.scoreboard.players.add.success.multiple", ☃, ☃.func_197890_e(), ☃.size()), true);
      }

      return ☃x;
   }

   private static int func_198651_c(CommandSource var0, Collection<String> var1, ScoreObjective var2, int var3) {
      Scoreboard ☃ = ☃.func_197028_i().func_200251_aP();
      int ☃x = 0;

      for(String ☃xx : ☃) {
         Score ☃xxx = ☃.func_96529_a(☃xx, ☃);
         ☃xxx.func_96647_c(☃xxx.func_96652_c() - ☃);
         ☃x += ☃xxx.func_96652_c();
      }

      if (☃.size() == 1) {
         ☃.func_197030_a(new TextComponentTranslation("commands.scoreboard.players.remove.success.single", ☃, ☃.func_197890_e(), ☃.iterator().next(), ☃x), true);
      } else {
         ☃.func_197030_a(new TextComponentTranslation("commands.scoreboard.players.remove.success.multiple", ☃, ☃.func_197890_e(), ☃.size()), true);
      }

      return ☃x;
   }

   private static int func_198661_a(CommandSource var0) {
      Collection<String> ☃ = ☃.func_197028_i().func_200251_aP().func_96526_d();
      if (☃.isEmpty()) {
         ☃.func_197030_a(new TextComponentTranslation("commands.scoreboard.players.list.empty"), false);
      } else {
         ☃.func_197030_a(new TextComponentTranslation("commands.scoreboard.players.list.success", ☃.size(), TextComponentUtils.func_197678_a(☃)), false);
      }

      return ☃.size();
   }

   private static int func_198643_a(CommandSource var0, String var1) {
      Map<ScoreObjective, Score> ☃ = ☃.func_197028_i().func_200251_aP().func_96510_d(☃);
      if (☃.isEmpty()) {
         ☃.func_197030_a(new TextComponentTranslation("commands.scoreboard.players.list.entity.empty", ☃), false);
      } else {
         ☃.func_197030_a(new TextComponentTranslation("commands.scoreboard.players.list.entity.success", ☃, ☃.size()), false);

         for(Entry<ScoreObjective, Score> ☃ : ☃.entrySet()) {
            ☃.func_197030_a(
               new TextComponentTranslation(
                  "commands.scoreboard.players.list.entity.entry", ((ScoreObjective)☃.getKey()).func_197890_e(), ((Score)☃.getValue()).func_96652_c()
               ),
               false
            );
         }
      }

      return ☃.size();
   }

   private static int func_198632_a(CommandSource var0, int var1) throws CommandSyntaxException {
      Scoreboard ☃ = ☃.func_197028_i().func_200251_aP();
      if (☃.func_96539_a(☃) == null) {
         throw field_198666_d.create();
      } else {
         ☃.func_96530_a(☃, null);
         ☃.func_197030_a(new TextComponentTranslation("commands.scoreboard.objectives.display.cleared", Scoreboard.func_178821_h()[☃]), true);
         return 0;
      }
   }

   private static int func_198659_a(CommandSource var0, int var1, ScoreObjective var2) throws CommandSyntaxException {
      Scoreboard ☃ = ☃.func_197028_i().func_200251_aP();
      if (☃.func_96539_a(☃) == ☃) {
         throw field_198667_e.create();
      } else {
         ☃.func_96530_a(☃, ☃);
         ☃.func_197030_a(new TextComponentTranslation("commands.scoreboard.objectives.display.set", Scoreboard.func_178821_h()[☃], ☃.func_96678_d()), true);
         return 0;
      }
   }

   private static int func_211749_a(CommandSource var0, ScoreObjective var1, ITextComponent var2) {
      if (!☃.func_96678_d().equals(☃)) {
         ☃.func_199864_a(☃);
         ☃.func_197030_a(new TextComponentTranslation("commands.scoreboard.objectives.modify.displayname", ☃.func_96679_b(), ☃.func_197890_e()), true);
      }

      return 0;
   }

   private static int func_211910_a(CommandSource var0, ScoreObjective var1, ScoreCriteria.RenderType var2) {
      if (☃.func_199865_f() != ☃) {
         ☃.func_199866_a(☃);
         ☃.func_197030_a(new TextComponentTranslation("commands.scoreboard.objectives.modify.rendertype", ☃.func_197890_e()), true);
      }

      return 0;
   }

   private static int func_198637_a(CommandSource var0, ScoreObjective var1) {
      Scoreboard ☃ = ☃.func_197028_i().func_200251_aP();
      ☃.func_96519_k(☃);
      ☃.func_197030_a(new TextComponentTranslation("commands.scoreboard.objectives.remove.success", ☃.func_197890_e()), true);
      return ☃.func_96514_c().size();
   }

   private static int func_198629_a(CommandSource var0, String var1, ScoreCriteria var2, ITextComponent var3) throws CommandSyntaxException {
      Scoreboard ☃ = ☃.func_197028_i().func_200251_aP();
      if (☃.func_96518_b(☃) != null) {
         throw field_198663_a.create();
      } else if (☃.length() > 16) {
         throw ObjectiveArgument.field_200379_a.create(16);
      } else {
         ☃.func_199868_a(☃, ☃, ☃, ☃.func_178790_c());
         ScoreObjective ☃ = ☃.func_96518_b(☃);
         ☃.func_197030_a(new TextComponentTranslation("commands.scoreboard.objectives.add.success", ☃.func_197890_e()), true);
         return ☃.func_96514_c().size();
      }
   }

   private static int func_198662_b(CommandSource var0) {
      Collection<ScoreObjective> ☃ = ☃.func_197028_i().func_200251_aP().func_96514_c();
      if (☃.isEmpty()) {
         ☃.func_197030_a(new TextComponentTranslation("commands.scoreboard.objectives.list.empty"), false);
      } else {
         ☃.func_197030_a(
            new TextComponentTranslation(
               "commands.scoreboard.objectives.list.success", ☃.size(), TextComponentUtils.func_197677_b(☃, ScoreObjective::func_197890_e)
            ),
            false
         );
      }

      return ☃.size();
   }
}
