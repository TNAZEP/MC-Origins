package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;

public class DataPackCommand {
   private static final DynamicCommandExceptionType ERROR_UNKNOWN_PACK = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("commands.datapack.unknown", var0)
   );
   private static final DynamicCommandExceptionType ERROR_PACK_ALREADY_ENABLED = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("commands.datapack.enable.failed", var0)
   );
   private static final DynamicCommandExceptionType ERROR_PACK_ALREADY_DISABLED = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("commands.datapack.disable.failed", var0)
   );
   private static final SuggestionProvider<CommandSourceStack> SELECTED_PACKS = (var0, var1) -> SharedSuggestionProvider.suggest(
         var0.getSource().getServer().getPackRepository().getSelectedIds().stream().map(StringArgumentType::escapeIfRequired), var1
      );
   private static final SuggestionProvider<CommandSourceStack> UNSELECTED_PACKS = (var0, var1) -> {
      PackRepository â˜ƒ = var0.getSource().getServer().getPackRepository();
      Collection<String> â˜ƒx = â˜ƒ.getSelectedIds();
      return SharedSuggestionProvider.suggest(
         â˜ƒ.getAvailableIds().stream().filter(var1x -> !â˜ƒ.contains(var1x)).map(StringArgumentType::escapeIfRequired), var1
      );
   };

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("datapack")
            .requires(var0x -> var0x.hasPermission(2))
            .then(
               Commands.literal("enable")
                  .then(
                     ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument(
                                       "name", StringArgumentType.string()
                                    )
                                    .suggests(UNSELECTED_PACKS)
                                    .executes(
                                       var0x -> enablePack(
                                             (CommandSourceStack)var0x.getSource(),
                                             getPack(var0x, "name", true),
                                             (var0xx, var1) -> var1.getDefaultPosition().insert(var0xx, var1, var0xxx -> var0xxx, false)
                                          )
                                    ))
                                 .then(
                                    Commands.literal("after")
                                       .then(
                                          Commands.argument("existing", StringArgumentType.string())
                                             .suggests(SELECTED_PACKS)
                                             .executes(
                                                var0x -> enablePack(
                                                      var0x.getSource(),
                                                      getPack(var0x, "name", true),
                                                      (var1, var2) -> var1.add(var1.indexOf(getPack(var0x, "existing", false)) + 1, var2)
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 Commands.literal("before")
                                    .then(
                                       Commands.argument("existing", StringArgumentType.string())
                                          .suggests(SELECTED_PACKS)
                                          .executes(
                                             var0x -> enablePack(
                                                   var0x.getSource(),
                                                   getPack(var0x, "name", true),
                                                   (var1, var2) -> var1.add(var1.indexOf(getPack(var0x, "existing", false)), var2)
                                                )
                                          )
                                    )
                              ))
                           .then(Commands.literal("last").executes(var0x -> enablePack(var0x.getSource(), getPack(var0x, "name", true), List::add))))
                        .then(
                           Commands.literal("first")
                              .executes(var0x -> enablePack(var0x.getSource(), getPack(var0x, "name", true), (var0xx, var1) -> var0xx.add(0, var1)))
                        )
                  )
            )
            .then(
               Commands.literal("disable")
                  .then(
                     Commands.argument("name", StringArgumentType.string())
                        .suggests(SELECTED_PACKS)
                        .executes(var0x -> disablePack(var0x.getSource(), getPack(var0x, "name", false)))
                  )
            )
            .then(
               Commands.literal("list")
                  .executes(var0x -> listPacks(var0x.getSource()))
                  .then(Commands.literal("available").executes(var0x -> listAvailablePacks(var0x.getSource())))
                  .then(Commands.literal("enabled").executes(var0x -> listEnabledPacks(var0x.getSource())))
            )
      );
   }

   private static int enablePack(CommandSourceStack var0, Pack var1, DataPackCommand.Inserter var2) throws CommandSyntaxException {
      PackRepository â˜ƒ = â˜ƒ.getServer().getPackRepository();
      List<Pack> â˜ƒx = Lists.<Pack>newArrayList(â˜ƒ.getSelectedPacks());
      â˜ƒ.apply(â˜ƒx, â˜ƒ);
      â˜ƒ.sendSuccess(new TranslatableComponent("commands.datapack.modify.enable", â˜ƒ.getChatLink(true)), true);
      ReloadCommand.reloadPacks((Collection<String>)â˜ƒx.stream().map(Pack::getId).collect(Collectors.toList()), â˜ƒ);
      return â˜ƒx.size();
   }

   private static int disablePack(CommandSourceStack var0, Pack var1) {
      PackRepository â˜ƒ = â˜ƒ.getServer().getPackRepository();
      List<Pack> â˜ƒx = Lists.<Pack>newArrayList(â˜ƒ.getSelectedPacks());
      â˜ƒx.remove(â˜ƒ);
      â˜ƒ.sendSuccess(new TranslatableComponent("commands.datapack.modify.disable", â˜ƒ.getChatLink(true)), true);
      ReloadCommand.reloadPacks((Collection<String>)â˜ƒx.stream().map(Pack::getId).collect(Collectors.toList()), â˜ƒ);
      return â˜ƒx.size();
   }

   private static int listPacks(CommandSourceStack var0) {
      return listEnabledPacks(â˜ƒ) + listAvailablePacks(â˜ƒ);
   }

   private static int listAvailablePacks(CommandSourceStack var0) {
      PackRepository â˜ƒ = â˜ƒ.getServer().getPackRepository();
      â˜ƒ.reload();
      Collection<? extends Pack> â˜ƒx = â˜ƒ.getSelectedPacks();
      Collection<? extends Pack> â˜ƒxx = â˜ƒ.getAvailablePacks();
      List<Pack> â˜ƒxxx = (List)â˜ƒxx.stream().filter(var1x -> !â˜ƒ.contains(var1x)).collect(Collectors.toList());
      if (â˜ƒxxx.isEmpty()) {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.datapack.list.available.none"), false);
      } else {
         â˜ƒ.sendSuccess(
            new TranslatableComponent(
               "commands.datapack.list.available.success", â˜ƒxxx.size(), ComponentUtils.formatList(â˜ƒxxx, var0x -> var0x.getChatLink(false))
            ),
            false
         );
      }

      return â˜ƒxxx.size();
   }

   private static int listEnabledPacks(CommandSourceStack var0) {
      PackRepository â˜ƒ = â˜ƒ.getServer().getPackRepository();
      â˜ƒ.reload();
      Collection<? extends Pack> â˜ƒx = â˜ƒ.getSelectedPacks();
      if (â˜ƒx.isEmpty()) {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.datapack.list.enabled.none"), false);
      } else {
         â˜ƒ.sendSuccess(
            new TranslatableComponent("commands.datapack.list.enabled.success", â˜ƒx.size(), ComponentUtils.formatList(â˜ƒx, var0x -> var0x.getChatLink(true))),
            false
         );
      }

      return â˜ƒx.size();
   }

   private static Pack getPack(CommandContext<CommandSourceStack> var0, String var1, boolean var2) throws CommandSyntaxException {
      String â˜ƒ = StringArgumentType.getString(â˜ƒ, â˜ƒ);
      PackRepository â˜ƒx = â˜ƒ.getSource().getServer().getPackRepository();
      Pack â˜ƒxx = â˜ƒx.getPack(â˜ƒ);
      if (â˜ƒxx == null) {
         throw ERROR_UNKNOWN_PACK.create(â˜ƒ);
      } else {
         boolean â˜ƒ = â˜ƒx.getSelectedPacks().contains(â˜ƒxx);
         if (â˜ƒ && â˜ƒ) {
            throw ERROR_PACK_ALREADY_ENABLED.create(â˜ƒ);
         } else if (!â˜ƒ && !â˜ƒ) {
            throw ERROR_PACK_ALREADY_DISABLED.create(â˜ƒ);
         } else {
            return â˜ƒxx;
         }
      }
   }

   interface Inserter {
      void apply(List<Pack> var1, Pack var2) throws CommandSyntaxException;
   }
}
