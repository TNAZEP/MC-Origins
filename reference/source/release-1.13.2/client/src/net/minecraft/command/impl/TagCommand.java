package net.minecraft.command.impl;

import com.google.common.collect.Sets;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.Set;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextComponentUtils;

public class TagCommand {
   private static final SimpleCommandExceptionType field_198752_a = new SimpleCommandExceptionType(new TextComponentTranslation("commands.tag.add.failed"));
   private static final SimpleCommandExceptionType field_198753_b = new SimpleCommandExceptionType(new TextComponentTranslation("commands.tag.remove.failed"));

   public static void func_198743_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("tag")
            .requires(var0x -> var0x.func_197034_c(2))
            .then(
               Commands.func_197056_a("targets", EntityArgument.func_197093_b())
                  .then(
                     Commands.func_197057_a("add")
                        .then(
                           Commands.func_197056_a("name", StringArgumentType.word())
                              .executes(
                                 var0x -> func_198749_a(
                                       var0x.getSource(), EntityArgument.func_197097_b(var0x, "targets"), StringArgumentType.getString(var0x, "name")
                                    )
                              )
                        )
                  )
                  .then(
                     Commands.func_197057_a("remove")
                        .then(
                           Commands.func_197056_a("name", StringArgumentType.word())
                              .suggests((var0x, var1) -> ISuggestionProvider.func_197005_b(func_198748_a(EntityArgument.func_197097_b(var0x, "targets")), var1))
                              .executes(
                                 var0x -> func_198750_b(
                                       var0x.getSource(), EntityArgument.func_197097_b(var0x, "targets"), StringArgumentType.getString(var0x, "name")
                                    )
                              )
                        )
                  )
                  .then(Commands.func_197057_a("list").executes(var0x -> func_198744_a(var0x.getSource(), EntityArgument.func_197097_b(var0x, "targets"))))
            )
      );
   }

   private static Collection<String> func_198748_a(Collection<? extends Entity> var0) {
      Set<String> ☃ = Sets.newHashSet();

      for(Entity ☃x : ☃) {
         ☃.addAll(☃x.func_184216_O());
      }

      return ☃;
   }

   private static int func_198749_a(CommandSource var0, Collection<? extends Entity> var1, String var2) throws CommandSyntaxException {
      int ☃ = 0;

      for(Entity ☃x : ☃) {
         if (☃x.func_184211_a(☃)) {
            ++☃;
         }
      }

      if (☃ == 0) {
         throw field_198752_a.create();
      } else {
         if (☃.size() == 1) {
            ☃.func_197030_a(new TextComponentTranslation("commands.tag.add.success.single", ☃, ((Entity)☃.iterator().next()).func_145748_c_()), true);
         } else {
            ☃.func_197030_a(new TextComponentTranslation("commands.tag.add.success.multiple", ☃, ☃.size()), true);
         }

         return ☃;
      }
   }

   private static int func_198750_b(CommandSource var0, Collection<? extends Entity> var1, String var2) throws CommandSyntaxException {
      int ☃ = 0;

      for(Entity ☃x : ☃) {
         if (☃x.func_184197_b(☃)) {
            ++☃;
         }
      }

      if (☃ == 0) {
         throw field_198753_b.create();
      } else {
         if (☃.size() == 1) {
            ☃.func_197030_a(new TextComponentTranslation("commands.tag.remove.success.single", ☃, ((Entity)☃.iterator().next()).func_145748_c_()), true);
         } else {
            ☃.func_197030_a(new TextComponentTranslation("commands.tag.remove.success.multiple", ☃, ☃.size()), true);
         }

         return ☃;
      }
   }

   private static int func_198744_a(CommandSource var0, Collection<? extends Entity> var1) {
      Set<String> ☃ = Sets.newHashSet();

      for(Entity ☃x : ☃) {
         ☃.addAll(☃x.func_184216_O());
      }

      if (☃.size() == 1) {
         Entity ☃x = (Entity)☃.iterator().next();
         if (☃.isEmpty()) {
            ☃.func_197030_a(new TextComponentTranslation("commands.tag.list.single.empty", ☃x.func_145748_c_()), false);
         } else {
            ☃.func_197030_a(
               new TextComponentTranslation("commands.tag.list.single.success", ☃x.func_145748_c_(), ☃.size(), TextComponentUtils.func_197678_a(☃)), false
            );
         }
      } else if (☃.isEmpty()) {
         ☃.func_197030_a(new TextComponentTranslation("commands.tag.list.multiple.empty", ☃.size()), false);
      } else {
         ☃.func_197030_a(new TextComponentTranslation("commands.tag.list.multiple.success", ☃.size(), ☃.size(), TextComponentUtils.func_197678_a(☃)), false);
      }

      return ☃.size();
   }
}
