package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.minecraft.commands.CommandFunction;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.TimeArgument;
import net.minecraft.commands.arguments.item.FunctionArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.timers.FunctionCallback;
import net.minecraft.world.level.timers.FunctionTagCallback;
import net.minecraft.world.level.timers.TimerQueue;

public class ScheduleCommand {
   private static final SimpleCommandExceptionType ERROR_SAME_TICK = new SimpleCommandExceptionType(new TranslatableComponent("commands.schedule.same_tick"));
   private static final DynamicCommandExceptionType ERROR_CANT_REMOVE = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("commands.schedule.cleared.failure", var0)
   );
   private static final SuggestionProvider<CommandSourceStack> SUGGEST_SCHEDULE = (var0, var1) -> SharedSuggestionProvider.suggest(
         var0.getSource().getServer().getWorldData().overworldData().getScheduledEvents().getEventsIds(), var1
      );

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("schedule")
            .requires(var0x -> var0x.hasPermission(2))
            .then(
               Commands.literal("function")
                  .then(
                     Commands.argument("function", FunctionArgument.functions())
                        .suggests(FunctionCommand.SUGGEST_FUNCTION)
                        .then(
                           ((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("time", TimeArgument.time())
                                    .executes(
                                       var0x -> schedule(
                                             (CommandSourceStack)var0x.getSource(),
                                             FunctionArgument.getFunctionOrTag(var0x, "function"),
                                             IntegerArgumentType.getInteger(var0x, "time"),
                                             true
                                          )
                                    ))
                                 .then(
                                    Commands.literal("append")
                                       .executes(
                                          var0x -> schedule(
                                                var0x.getSource(),
                                                FunctionArgument.getFunctionOrTag(var0x, "function"),
                                                IntegerArgumentType.getInteger(var0x, "time"),
                                                false
                                             )
                                       )
                                 ))
                              .then(
                                 Commands.literal("replace")
                                    .executes(
                                       var0x -> schedule(
                                             var0x.getSource(),
                                             FunctionArgument.getFunctionOrTag(var0x, "function"),
                                             IntegerArgumentType.getInteger(var0x, "time"),
                                             true
                                          )
                                    )
                              )
                        )
                  )
            )
            .then(
               Commands.literal("clear")
                  .then(
                     Commands.argument("function", StringArgumentType.greedyString())
                        .suggests(SUGGEST_SCHEDULE)
                        .executes(var0x -> remove(var0x.getSource(), StringArgumentType.getString(var0x, "function")))
                  )
            )
      );
   }

   private static int schedule(CommandSourceStack var0, Pair<ResourceLocation, Either<CommandFunction, Tag<CommandFunction>>> var1, int var2, boolean var3) throws CommandSyntaxException {
      if (â˜ƒ == 0) {
         throw ERROR_SAME_TICK.create();
      } else {
         long â˜ƒ = â˜ƒ.getLevel().getGameTime() + (long)â˜ƒ;
         ResourceLocation â˜ƒx = â˜ƒ.getFirst();
         TimerQueue<MinecraftServer> â˜ƒxx = â˜ƒ.getServer().getWorldData().overworldData().getScheduledEvents();
         â˜ƒ.getSecond().ifLeft(var7x -> {
            String â˜ƒ = â˜ƒ.toString();
            if (â˜ƒ) {
               â˜ƒ.remove(â˜ƒ);
            }

            â˜ƒ.schedule(â˜ƒ, â˜ƒ, new FunctionCallback(â˜ƒ));
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.schedule.created.function", â˜ƒ, â˜ƒ, â˜ƒ), true);
         }).ifRight(var7x -> {
            String â˜ƒ = "#" + â˜ƒ;
            if (â˜ƒ) {
               â˜ƒ.remove(â˜ƒ);
            }

            â˜ƒ.schedule(â˜ƒ, â˜ƒ, new FunctionTagCallback(â˜ƒ));
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.schedule.created.tag", â˜ƒ, â˜ƒ, â˜ƒ), true);
         });
         return Math.floorMod(â˜ƒ, Integer.MAX_VALUE);
      }
   }

   private static int remove(CommandSourceStack var0, String var1) throws CommandSyntaxException {
      int â˜ƒ = â˜ƒ.getServer().getWorldData().overworldData().getScheduledEvents().remove(â˜ƒ);
      if (â˜ƒ == 0) {
         throw ERROR_CANT_REMOVE.create(â˜ƒ);
      } else {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.schedule.cleared.success", â˜ƒ, â˜ƒ), true);
         return â˜ƒ;
      }
   }
}
