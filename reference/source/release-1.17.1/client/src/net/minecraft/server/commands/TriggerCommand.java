package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ObjectiveArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class TriggerCommand {
   private static final SimpleCommandExceptionType ERROR_NOT_PRIMED = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.trigger.failed.unprimed")
   );
   private static final SimpleCommandExceptionType ERROR_INVALID_OBJECTIVE = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.trigger.failed.invalid")
   );

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("trigger")
            .then(
               ((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("objective", ObjectiveArgument.objective())
                        .suggests((var0x, var1) -> suggestObjectives((CommandSourceStack)var0x.getSource(), var1))
                        .executes(
                           var0x -> simpleTrigger(
                                 (CommandSourceStack)var0x.getSource(),
                                 getScore(((CommandSourceStack)var0x.getSource()).getPlayerOrException(), ObjectiveArgument.getObjective(var0x, "objective"))
                              )
                        ))
                     .then(
                        Commands.literal("add")
                           .then(
                              Commands.argument("value", IntegerArgumentType.integer())
                                 .executes(
                                    var0x -> addValue(
                                          var0x.getSource(),
                                          getScore(var0x.getSource().getPlayerOrException(), ObjectiveArgument.getObjective(var0x, "objective")),
                                          IntegerArgumentType.getInteger(var0x, "value")
                                       )
                                 )
                           )
                     ))
                  .then(
                     Commands.literal("set")
                        .then(
                           Commands.argument("value", IntegerArgumentType.integer())
                              .executes(
                                 var0x -> setValue(
                                       var0x.getSource(),
                                       getScore(var0x.getSource().getPlayerOrException(), ObjectiveArgument.getObjective(var0x, "objective")),
                                       IntegerArgumentType.getInteger(var0x, "value")
                                    )
                              )
                        )
                  )
            )
      );
   }

   public static CompletableFuture<Suggestions> suggestObjectives(CommandSourceStack var0, SuggestionsBuilder var1) {
      Entity â˜ƒ = â˜ƒ.getEntity();
      List<String> â˜ƒx = Lists.newArrayList();
      if (â˜ƒ != null) {
         Scoreboard â˜ƒxx = â˜ƒ.getServer().getScoreboard();
         String â˜ƒxxx = â˜ƒ.getScoreboardName();

         for(Objective â˜ƒxxxx : â˜ƒxx.getObjectives()) {
            if (â˜ƒxxxx.getCriteria() == ObjectiveCriteria.TRIGGER && â˜ƒxx.hasPlayerScore(â˜ƒxxx, â˜ƒxxxx)) {
               Score â˜ƒxxxxx = â˜ƒxx.getOrCreatePlayerScore(â˜ƒxxx, â˜ƒxxxx);
               if (!â˜ƒxxxxx.isLocked()) {
                  â˜ƒx.add(â˜ƒxxxx.getName());
               }
            }
         }
      }

      return SharedSuggestionProvider.suggest(â˜ƒx, â˜ƒ);
   }

   private static int addValue(CommandSourceStack var0, Score var1, int var2) {
      â˜ƒ.add(â˜ƒ);
      â˜ƒ.sendSuccess(new TranslatableComponent("commands.trigger.add.success", â˜ƒ.getObjective().getFormattedDisplayName(), â˜ƒ), true);
      return â˜ƒ.getScore();
   }

   private static int setValue(CommandSourceStack var0, Score var1, int var2) {
      â˜ƒ.setScore(â˜ƒ);
      â˜ƒ.sendSuccess(new TranslatableComponent("commands.trigger.set.success", â˜ƒ.getObjective().getFormattedDisplayName(), â˜ƒ), true);
      return â˜ƒ;
   }

   private static int simpleTrigger(CommandSourceStack var0, Score var1) {
      â˜ƒ.add(1);
      â˜ƒ.sendSuccess(new TranslatableComponent("commands.trigger.simple.success", â˜ƒ.getObjective().getFormattedDisplayName()), true);
      return â˜ƒ.getScore();
   }

   private static Score getScore(ServerPlayer var0, Objective var1) throws CommandSyntaxException {
      if (â˜ƒ.getCriteria() != ObjectiveCriteria.TRIGGER) {
         throw ERROR_INVALID_OBJECTIVE.create();
      } else {
         Scoreboard â˜ƒ = â˜ƒ.getScoreboard();
         String â˜ƒx = â˜ƒ.getScoreboardName();
         if (!â˜ƒ.hasPlayerScore(â˜ƒx, â˜ƒ)) {
            throw ERROR_NOT_PRIMED.create();
         } else {
            Score â˜ƒ = â˜ƒ.getOrCreatePlayerScore(â˜ƒx, â˜ƒ);
            if (â˜ƒ.isLocked()) {
               throw ERROR_NOT_PRIMED.create();
            } else {
               â˜ƒ.setLocked(true);
               return â˜ƒ;
            }
         }
      }
   }
}
