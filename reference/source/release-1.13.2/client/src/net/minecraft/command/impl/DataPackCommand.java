package net.minecraft.command.impl;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.List;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.WorldInfo;

public class DataPackCommand {
   private static final DynamicCommandExceptionType field_198316_a = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("commands.datapack.unknown", var0)
   );
   private static final DynamicCommandExceptionType field_198317_b = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("commands.datapack.enable.failed", var0)
   );
   private static final DynamicCommandExceptionType field_198318_c = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("commands.datapack.disable.failed", var0)
   );
   private static final SuggestionProvider<CommandSource> field_198319_d = (var0, var1) -> ISuggestionProvider.func_197013_a(
         var0.getSource()
            .func_197028_i()
            .func_195561_aH()
            .func_198980_d()
            .stream()
            .map(ResourcePackInfo::func_195790_f)
            .map(StringArgumentType::escapeIfRequired),
         var1
      );
   private static final SuggestionProvider<CommandSource> field_198320_e = (var0, var1) -> ISuggestionProvider.func_197013_a(
         var0.getSource()
            .func_197028_i()
            .func_195561_aH()
            .func_198979_c()
            .stream()
            .map(ResourcePackInfo::func_195790_f)
            .map(StringArgumentType::escapeIfRequired),
         var1
      );

   public static void func_198299_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("datapack")
            .requires(var0x -> var0x.func_197034_c(2))
            .then(
               Commands.func_197057_a("enable")
                  .then(
                     ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.func_197056_a(
                                       "name", StringArgumentType.string()
                                    )
                                    .suggests(field_198320_e)
                                    .executes(
                                       var0x -> func_198297_a(
                                             (CommandSource)var0x.getSource(),
                                             func_198303_a(var0x, "name", true),
                                             (var0xx, var1) -> var1.func_195792_i().func_198993_a(var0xx, var1, var0xxx -> var0xxx, false)
                                          )
                                    ))
                                 .then(
                                    Commands.func_197057_a("after")
                                       .then(
                                          Commands.func_197056_a("existing", StringArgumentType.string())
                                             .suggests(field_198319_d)
                                             .executes(
                                                var0x -> func_198297_a(
                                                      var0x.getSource(),
                                                      func_198303_a(var0x, "name", true),
                                                      (var1, var2) -> var1.add(var1.indexOf(func_198303_a(var0x, "existing", false)) + 1, var2)
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 Commands.func_197057_a("before")
                                    .then(
                                       Commands.func_197056_a("existing", StringArgumentType.string())
                                          .suggests(field_198319_d)
                                          .executes(
                                             var0x -> func_198297_a(
                                                   var0x.getSource(),
                                                   func_198303_a(var0x, "name", true),
                                                   (var1, var2) -> var1.add(var1.indexOf(func_198303_a(var0x, "existing", false)), var2)
                                                )
                                          )
                                    )
                              ))
                           .then(
                              Commands.func_197057_a("last").executes(var0x -> func_198297_a(var0x.getSource(), func_198303_a(var0x, "name", true), List::add))
                           ))
                        .then(
                           Commands.func_197057_a("first")
                              .executes(var0x -> func_198297_a(var0x.getSource(), func_198303_a(var0x, "name", true), (var0xx, var1) -> var0xx.add(0, var1)))
                        )
                  )
            )
            .then(
               Commands.func_197057_a("disable")
                  .then(
                     Commands.func_197056_a("name", StringArgumentType.string())
                        .suggests(field_198319_d)
                        .executes(var0x -> func_198312_a(var0x.getSource(), func_198303_a(var0x, "name", false)))
                  )
            )
            .then(
               Commands.func_197057_a("list")
                  .executes(var0x -> func_198313_a(var0x.getSource()))
                  .then(Commands.func_197057_a("available").executes(var0x -> func_198314_b(var0x.getSource())))
                  .then(Commands.func_197057_a("enabled").executes(var0x -> func_198315_c(var0x.getSource())))
            )
      );
   }

   private static int func_198297_a(CommandSource var0, ResourcePackInfo var1, DataPackCommand.IHandler var2) throws CommandSyntaxException {
      ResourcePackList<ResourcePackInfo> ☃ = ☃.func_197028_i().func_195561_aH();
      List<ResourcePackInfo> ☃x = Lists.<ResourcePackInfo>newArrayList(☃.func_198980_d());
      ☃.apply(☃x, ☃);
      ☃.func_198985_a(☃x);
      WorldInfo ☃xx = ☃.func_197028_i().func_71218_a(DimensionType.OVERWORLD).func_72912_H();
      ☃xx.func_197720_O().clear();
      ☃.func_198980_d().forEach(var1x -> ☃.func_197720_O().add(var1x.func_195790_f()));
      ☃xx.func_197719_N().remove(☃.func_195790_f());
      ☃.func_197030_a(new TextComponentTranslation("commands.datapack.enable.success", ☃.func_195794_a(true)), true);
      ☃.func_197028_i().func_193031_aM();
      return ☃.func_198980_d().size();
   }

   private static int func_198312_a(CommandSource var0, ResourcePackInfo var1) {
      ResourcePackList<ResourcePackInfo> ☃ = ☃.func_197028_i().func_195561_aH();
      List<ResourcePackInfo> ☃x = Lists.<ResourcePackInfo>newArrayList(☃.func_198980_d());
      ☃x.remove(☃);
      ☃.func_198985_a(☃x);
      WorldInfo ☃xx = ☃.func_197028_i().func_71218_a(DimensionType.OVERWORLD).func_72912_H();
      ☃xx.func_197720_O().clear();
      ☃.func_198980_d().forEach(var1x -> ☃.func_197720_O().add(var1x.func_195790_f()));
      ☃xx.func_197719_N().add(☃.func_195790_f());
      ☃.func_197030_a(new TextComponentTranslation("commands.datapack.disable.success", ☃.func_195794_a(true)), true);
      ☃.func_197028_i().func_193031_aM();
      return ☃.func_198980_d().size();
   }

   private static int func_198313_a(CommandSource var0) {
      return func_198315_c(☃) + func_198314_b(☃);
   }

   private static int func_198314_b(CommandSource var0) {
      ResourcePackList<ResourcePackInfo> ☃ = ☃.func_197028_i().func_195561_aH();
      if (☃.func_198979_c().isEmpty()) {
         ☃.func_197030_a(new TextComponentTranslation("commands.datapack.list.available.none"), false);
      } else {
         ☃.func_197030_a(
            new TextComponentTranslation(
               "commands.datapack.list.available.success",
               ☃.func_198979_c().size(),
               TextComponentUtils.func_197677_b(☃.func_198979_c(), var0x -> var0x.func_195794_a(false))
            ),
            false
         );
      }

      return ☃.func_198979_c().size();
   }

   private static int func_198315_c(CommandSource var0) {
      ResourcePackList<ResourcePackInfo> ☃ = ☃.func_197028_i().func_195561_aH();
      if (☃.func_198980_d().isEmpty()) {
         ☃.func_197030_a(new TextComponentTranslation("commands.datapack.list.enabled.none"), false);
      } else {
         ☃.func_197030_a(
            new TextComponentTranslation(
               "commands.datapack.list.enabled.success",
               ☃.func_198980_d().size(),
               TextComponentUtils.func_197677_b(☃.func_198980_d(), var0x -> var0x.func_195794_a(true))
            ),
            false
         );
      }

      return ☃.func_198980_d().size();
   }

   private static ResourcePackInfo func_198303_a(CommandContext<CommandSource> var0, String var1, boolean var2) throws CommandSyntaxException {
      String ☃ = StringArgumentType.getString(☃, ☃);
      ResourcePackList<ResourcePackInfo> ☃x = ☃.getSource().func_197028_i().func_195561_aH();
      ResourcePackInfo ☃xx = ☃x.func_198981_a(☃);
      if (☃xx == null) {
         throw field_198316_a.create(☃);
      } else {
         boolean ☃ = ☃x.func_198980_d().contains(☃xx);
         if (☃ && ☃) {
            throw field_198317_b.create(☃);
         } else if (!☃ && !☃) {
            throw field_198318_c.create(☃);
         } else {
            return ☃xx;
         }
      }
   }

   interface IHandler {
      void apply(List<ResourcePackInfo> var1, ResourcePackInfo var2) throws CommandSyntaxException;
   }
}
