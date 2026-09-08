package net.minecraft.server.commands;

import com.google.common.collect.Sets;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.Set;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;

public class TagCommand {
   private static final SimpleCommandExceptionType ERROR_ADD_FAILED = new SimpleCommandExceptionType(new TranslatableComponent("commands.tag.add.failed"));
   private static final SimpleCommandExceptionType ERROR_REMOVE_FAILED = new SimpleCommandExceptionType(new TranslatableComponent("commands.tag.remove.failed"));

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("tag")
            .requires(var0x -> var0x.hasPermission(2))
            .then(
               Commands.argument("targets", EntityArgument.entities())
                  .then(
                     Commands.literal("add")
                        .then(
                           Commands.argument("name", StringArgumentType.word())
                              .executes(
                                 var0x -> addTag(var0x.getSource(), EntityArgument.getEntities(var0x, "targets"), StringArgumentType.getString(var0x, "name"))
                              )
                        )
                  )
                  .then(
                     Commands.literal("remove")
                        .then(
                           Commands.argument("name", StringArgumentType.word())
                              .suggests((var0x, var1) -> SharedSuggestionProvider.suggest(getTags(EntityArgument.getEntities(var0x, "targets")), var1))
                              .executes(
                                 var0x -> removeTag(
                                       var0x.getSource(), EntityArgument.getEntities(var0x, "targets"), StringArgumentType.getString(var0x, "name")
                                    )
                              )
                        )
                  )
                  .then(Commands.literal("list").executes(var0x -> listTags(var0x.getSource(), EntityArgument.getEntities(var0x, "targets"))))
            )
      );
   }

   private static Collection<String> getTags(Collection<? extends Entity> var0) {
      Set<String> â˜ƒ = Sets.newHashSet();

      for(Entity â˜ƒx : â˜ƒ) {
         â˜ƒ.addAll(â˜ƒx.getTags());
      }

      return â˜ƒ;
   }

   private static int addTag(CommandSourceStack var0, Collection<? extends Entity> var1, String var2) throws CommandSyntaxException {
      int â˜ƒ = 0;

      for(Entity â˜ƒx : â˜ƒ) {
         if (â˜ƒx.addTag(â˜ƒ)) {
            ++â˜ƒ;
         }
      }

      if (â˜ƒ == 0) {
         throw ERROR_ADD_FAILED.create();
      } else {
         if (â˜ƒ.size() == 1) {
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.tag.add.success.single", â˜ƒ, ((Entity)â˜ƒ.iterator().next()).getDisplayName()), true);
         } else {
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.tag.add.success.multiple", â˜ƒ, â˜ƒ.size()), true);
         }

         return â˜ƒ;
      }
   }

   private static int removeTag(CommandSourceStack var0, Collection<? extends Entity> var1, String var2) throws CommandSyntaxException {
      int â˜ƒ = 0;

      for(Entity â˜ƒx : â˜ƒ) {
         if (â˜ƒx.removeTag(â˜ƒ)) {
            ++â˜ƒ;
         }
      }

      if (â˜ƒ == 0) {
         throw ERROR_REMOVE_FAILED.create();
      } else {
         if (â˜ƒ.size() == 1) {
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.tag.remove.success.single", â˜ƒ, ((Entity)â˜ƒ.iterator().next()).getDisplayName()), true);
         } else {
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.tag.remove.success.multiple", â˜ƒ, â˜ƒ.size()), true);
         }

         return â˜ƒ;
      }
   }

   private static int listTags(CommandSourceStack var0, Collection<? extends Entity> var1) {
      Set<String> â˜ƒ = Sets.newHashSet();

      for(Entity â˜ƒx : â˜ƒ) {
         â˜ƒ.addAll(â˜ƒx.getTags());
      }

      if (â˜ƒ.size() == 1) {
         Entity â˜ƒx = (Entity)â˜ƒ.iterator().next();
         if (â˜ƒ.isEmpty()) {
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.tag.list.single.empty", â˜ƒx.getDisplayName()), false);
         } else {
            â˜ƒ.sendSuccess(
               new TranslatableComponent("commands.tag.list.single.success", â˜ƒx.getDisplayName(), â˜ƒ.size(), ComponentUtils.formatList(â˜ƒ)), false
            );
         }
      } else if (â˜ƒ.isEmpty()) {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.tag.list.multiple.empty", â˜ƒ.size()), false);
      } else {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.tag.list.multiple.success", â˜ƒ.size(), â˜ƒ.size(), ComponentUtils.formatList(â˜ƒ)), false);
      }

      return â˜ƒ.size();
   }
}
