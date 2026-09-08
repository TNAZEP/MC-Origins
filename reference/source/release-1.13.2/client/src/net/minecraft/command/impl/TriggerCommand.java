package net.minecraft.command.impl;

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
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.ObjectiveArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.text.TextComponentTranslation;

public class TriggerCommand {
   private static final SimpleCommandExceptionType field_198857_a = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.trigger.failed.unprimed")
   );
   private static final SimpleCommandExceptionType field_198858_b = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.trigger.failed.invalid")
   );

   public static void func_198852_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("trigger")
            .then(
               ((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.func_197056_a("objective", ObjectiveArgument.func_197157_a())
                        .suggests((var0x, var1) -> func_198850_a((CommandSource)var0x.getSource(), var1))
                        .executes(
                           var0x -> func_201477_a(
                                 (CommandSource)var0x.getSource(),
                                 func_198848_a(((CommandSource)var0x.getSource()).func_197035_h(), ObjectiveArgument.func_197158_a(var0x, "objective"))
                              )
                        ))
                     .then(
                        Commands.func_197057_a("add")
                           .then(
                              Commands.func_197056_a("value", IntegerArgumentType.integer())
                                 .executes(
                                    var0x -> func_201479_a(
                                          var0x.getSource(),
                                          func_198848_a(var0x.getSource().func_197035_h(), ObjectiveArgument.func_197158_a(var0x, "objective")),
                                          IntegerArgumentType.getInteger(var0x, "value")
                                       )
                                 )
                           )
                     ))
                  .then(
                     Commands.func_197057_a("set")
                        .then(
                           Commands.func_197056_a("value", IntegerArgumentType.integer())
                              .executes(
                                 var0x -> func_201478_b(
                                       var0x.getSource(),
                                       func_198848_a(var0x.getSource().func_197035_h(), ObjectiveArgument.func_197158_a(var0x, "objective")),
                                       IntegerArgumentType.getInteger(var0x, "value")
                                    )
                              )
                        )
                  )
            )
      );
   }

   public static CompletableFuture<Suggestions> func_198850_a(CommandSource var0, SuggestionsBuilder var1) {
      Entity ☃ = ☃.func_197022_f();
      List<String> ☃x = Lists.newArrayList();
      if (☃ != null) {
         Scoreboard ☃xx = ☃.func_197028_i().func_200251_aP();
         String ☃xxx = ☃.func_195047_I_();

         for(ScoreObjective ☃xxxx : ☃xx.func_96514_c()) {
            if (☃xxxx.func_96680_c() == ScoreCriteria.field_178791_c && ☃xx.func_178819_b(☃xxx, ☃xxxx)) {
               Score ☃xxxxx = ☃xx.func_96529_a(☃xxx, ☃xxxx);
               if (!☃xxxxx.func_178816_g()) {
                  ☃x.add(☃xxxx.func_96679_b());
               }
            }
         }
      }

      return ISuggestionProvider.func_197005_b(☃x, ☃);
   }

   private static int func_201479_a(CommandSource var0, Score var1, int var2) {
      ☃.func_96649_a(☃);
      ☃.func_197030_a(new TextComponentTranslation("commands.trigger.add.success", ☃.func_96645_d().func_197890_e(), ☃), true);
      return ☃.func_96652_c();
   }

   private static int func_201478_b(CommandSource var0, Score var1, int var2) {
      ☃.func_96647_c(☃);
      ☃.func_197030_a(new TextComponentTranslation("commands.trigger.set.success", ☃.func_96645_d().func_197890_e(), ☃), true);
      return ☃;
   }

   private static int func_201477_a(CommandSource var0, Score var1) {
      ☃.func_96649_a(1);
      ☃.func_197030_a(new TextComponentTranslation("commands.trigger.simple.success", ☃.func_96645_d().func_197890_e()), true);
      return ☃.func_96652_c();
   }

   private static Score func_198848_a(EntityPlayerMP var0, ScoreObjective var1) throws CommandSyntaxException {
      if (☃.func_96680_c() != ScoreCriteria.field_178791_c) {
         throw field_198858_b.create();
      } else {
         Scoreboard ☃ = ☃.func_96123_co();
         String ☃x = ☃.func_195047_I_();
         if (!☃.func_178819_b(☃x, ☃)) {
            throw field_198857_a.create();
         } else {
            Score ☃ = ☃.func_96529_a(☃x, ☃);
            if (☃.func_178816_g()) {
               throw field_198857_a.create();
            } else {
               ☃.func_178815_a(true);
               return ☃;
            }
         }
      }
   }
}
