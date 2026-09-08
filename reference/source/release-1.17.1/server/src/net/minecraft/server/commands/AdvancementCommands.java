package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.List;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;

public class AdvancementCommands {
   private static final SuggestionProvider<CommandSourceStack> SUGGEST_ADVANCEMENTS = (var0, var1) -> {
      Collection<Advancement> â˜ƒ = var0.getSource().getServer().getAdvancements().getAllAdvancements();
      return SharedSuggestionProvider.suggestResource(â˜ƒ.stream().map(Advancement::getId), var1);
   };

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("advancement")
            .requires(var0x -> var0x.hasPermission(2))
            .then(
               Commands.literal("grant")
                  .then(
                     Commands.argument("targets", EntityArgument.players())
                        .then(
                           Commands.literal("only")
                              .then(
                                 Commands.argument("advancement", ResourceLocationArgument.id())
                                    .suggests(SUGGEST_ADVANCEMENTS)
                                    .executes(
                                       var0x -> perform(
                                             var0x.getSource(),
                                             EntityArgument.getPlayers(var0x, "targets"),
                                             AdvancementCommands.Action.GRANT,
                                             getAdvancements(ResourceLocationArgument.getAdvancement(var0x, "advancement"), AdvancementCommands.Mode.ONLY)
                                          )
                                    )
                                    .then(
                                       Commands.argument("criterion", StringArgumentType.greedyString())
                                          .suggests(
                                             (var0x, var1) -> SharedSuggestionProvider.suggest(
                                                   ResourceLocationArgument.getAdvancement(var0x, "advancement").getCriteria().keySet(), var1
                                                )
                                          )
                                          .executes(
                                             var0x -> performCriterion(
                                                   var0x.getSource(),
                                                   EntityArgument.getPlayers(var0x, "targets"),
                                                   AdvancementCommands.Action.GRANT,
                                                   ResourceLocationArgument.getAdvancement(var0x, "advancement"),
                                                   StringArgumentType.getString(var0x, "criterion")
                                                )
                                          )
                                    )
                              )
                        )
                        .then(
                           Commands.literal("from")
                              .then(
                                 Commands.argument("advancement", ResourceLocationArgument.id())
                                    .suggests(SUGGEST_ADVANCEMENTS)
                                    .executes(
                                       var0x -> perform(
                                             var0x.getSource(),
                                             EntityArgument.getPlayers(var0x, "targets"),
                                             AdvancementCommands.Action.GRANT,
                                             getAdvancements(ResourceLocationArgument.getAdvancement(var0x, "advancement"), AdvancementCommands.Mode.FROM)
                                          )
                                    )
                              )
                        )
                        .then(
                           Commands.literal("until")
                              .then(
                                 Commands.argument("advancement", ResourceLocationArgument.id())
                                    .suggests(SUGGEST_ADVANCEMENTS)
                                    .executes(
                                       var0x -> perform(
                                             var0x.getSource(),
                                             EntityArgument.getPlayers(var0x, "targets"),
                                             AdvancementCommands.Action.GRANT,
                                             getAdvancements(ResourceLocationArgument.getAdvancement(var0x, "advancement"), AdvancementCommands.Mode.UNTIL)
                                          )
                                    )
                              )
                        )
                        .then(
                           Commands.literal("through")
                              .then(
                                 Commands.argument("advancement", ResourceLocationArgument.id())
                                    .suggests(SUGGEST_ADVANCEMENTS)
                                    .executes(
                                       var0x -> perform(
                                             var0x.getSource(),
                                             EntityArgument.getPlayers(var0x, "targets"),
                                             AdvancementCommands.Action.GRANT,
                                             getAdvancements(ResourceLocationArgument.getAdvancement(var0x, "advancement"), AdvancementCommands.Mode.THROUGH)
                                          )
                                    )
                              )
                        )
                        .then(
                           Commands.literal("everything")
                              .executes(
                                 var0x -> perform(
                                       var0x.getSource(),
                                       EntityArgument.getPlayers(var0x, "targets"),
                                       AdvancementCommands.Action.GRANT,
                                       var0x.getSource().getServer().getAdvancements().getAllAdvancements()
                                    )
                              )
                        )
                  )
            )
            .then(
               Commands.literal("revoke")
                  .then(
                     Commands.argument("targets", EntityArgument.players())
                        .then(
                           Commands.literal("only")
                              .then(
                                 Commands.argument("advancement", ResourceLocationArgument.id())
                                    .suggests(SUGGEST_ADVANCEMENTS)
                                    .executes(
                                       var0x -> perform(
                                             var0x.getSource(),
                                             EntityArgument.getPlayers(var0x, "targets"),
                                             AdvancementCommands.Action.REVOKE,
                                             getAdvancements(ResourceLocationArgument.getAdvancement(var0x, "advancement"), AdvancementCommands.Mode.ONLY)
                                          )
                                    )
                                    .then(
                                       Commands.argument("criterion", StringArgumentType.greedyString())
                                          .suggests(
                                             (var0x, var1) -> SharedSuggestionProvider.suggest(
                                                   ResourceLocationArgument.getAdvancement(var0x, "advancement").getCriteria().keySet(), var1
                                                )
                                          )
                                          .executes(
                                             var0x -> performCriterion(
                                                   var0x.getSource(),
                                                   EntityArgument.getPlayers(var0x, "targets"),
                                                   AdvancementCommands.Action.REVOKE,
                                                   ResourceLocationArgument.getAdvancement(var0x, "advancement"),
                                                   StringArgumentType.getString(var0x, "criterion")
                                                )
                                          )
                                    )
                              )
                        )
                        .then(
                           Commands.literal("from")
                              .then(
                                 Commands.argument("advancement", ResourceLocationArgument.id())
                                    .suggests(SUGGEST_ADVANCEMENTS)
                                    .executes(
                                       var0x -> perform(
                                             var0x.getSource(),
                                             EntityArgument.getPlayers(var0x, "targets"),
                                             AdvancementCommands.Action.REVOKE,
                                             getAdvancements(ResourceLocationArgument.getAdvancement(var0x, "advancement"), AdvancementCommands.Mode.FROM)
                                          )
                                    )
                              )
                        )
                        .then(
                           Commands.literal("until")
                              .then(
                                 Commands.argument("advancement", ResourceLocationArgument.id())
                                    .suggests(SUGGEST_ADVANCEMENTS)
                                    .executes(
                                       var0x -> perform(
                                             var0x.getSource(),
                                             EntityArgument.getPlayers(var0x, "targets"),
                                             AdvancementCommands.Action.REVOKE,
                                             getAdvancements(ResourceLocationArgument.getAdvancement(var0x, "advancement"), AdvancementCommands.Mode.UNTIL)
                                          )
                                    )
                              )
                        )
                        .then(
                           Commands.literal("through")
                              .then(
                                 Commands.argument("advancement", ResourceLocationArgument.id())
                                    .suggests(SUGGEST_ADVANCEMENTS)
                                    .executes(
                                       var0x -> perform(
                                             var0x.getSource(),
                                             EntityArgument.getPlayers(var0x, "targets"),
                                             AdvancementCommands.Action.REVOKE,
                                             getAdvancements(ResourceLocationArgument.getAdvancement(var0x, "advancement"), AdvancementCommands.Mode.THROUGH)
                                          )
                                    )
                              )
                        )
                        .then(
                           Commands.literal("everything")
                              .executes(
                                 var0x -> perform(
                                       var0x.getSource(),
                                       EntityArgument.getPlayers(var0x, "targets"),
                                       AdvancementCommands.Action.REVOKE,
                                       var0x.getSource().getServer().getAdvancements().getAllAdvancements()
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int perform(CommandSourceStack var0, Collection<ServerPlayer> var1, AdvancementCommands.Action var2, Collection<Advancement> var3) {
      int â˜ƒ = 0;

      for(ServerPlayer â˜ƒx : â˜ƒ) {
         â˜ƒ += â˜ƒ.perform(â˜ƒx, â˜ƒ);
      }

      if (â˜ƒ == 0) {
         if (â˜ƒ.size() == 1) {
            if (â˜ƒ.size() == 1) {
               throw new CommandRuntimeException(
                  new TranslatableComponent(
                     â˜ƒ.getKey() + ".one.to.one.failure",
                     ((Advancement)â˜ƒ.iterator().next()).getChatComponent(),
                     ((ServerPlayer)â˜ƒ.iterator().next()).getDisplayName()
                  )
               );
            } else {
               throw new CommandRuntimeException(
                  new TranslatableComponent(â˜ƒ.getKey() + ".one.to.many.failure", ((Advancement)â˜ƒ.iterator().next()).getChatComponent(), â˜ƒ.size())
               );
            }
         } else if (â˜ƒ.size() == 1) {
            throw new CommandRuntimeException(
               new TranslatableComponent(â˜ƒ.getKey() + ".many.to.one.failure", â˜ƒ.size(), ((ServerPlayer)â˜ƒ.iterator().next()).getDisplayName())
            );
         } else {
            throw new CommandRuntimeException(new TranslatableComponent(â˜ƒ.getKey() + ".many.to.many.failure", â˜ƒ.size(), â˜ƒ.size()));
         }
      } else {
         if (â˜ƒ.size() == 1) {
            if (â˜ƒ.size() == 1) {
               â˜ƒ.sendSuccess(
                  new TranslatableComponent(
                     â˜ƒ.getKey() + ".one.to.one.success",
                     ((Advancement)â˜ƒ.iterator().next()).getChatComponent(),
                     ((ServerPlayer)â˜ƒ.iterator().next()).getDisplayName()
                  ),
                  true
               );
            } else {
               â˜ƒ.sendSuccess(
                  new TranslatableComponent(â˜ƒ.getKey() + ".one.to.many.success", ((Advancement)â˜ƒ.iterator().next()).getChatComponent(), â˜ƒ.size()), true
               );
            }
         } else if (â˜ƒ.size() == 1) {
            â˜ƒ.sendSuccess(
               new TranslatableComponent(â˜ƒ.getKey() + ".many.to.one.success", â˜ƒ.size(), ((ServerPlayer)â˜ƒ.iterator().next()).getDisplayName()), true
            );
         } else {
            â˜ƒ.sendSuccess(new TranslatableComponent(â˜ƒ.getKey() + ".many.to.many.success", â˜ƒ.size(), â˜ƒ.size()), true);
         }

         return â˜ƒ;
      }
   }

   private static int performCriterion(CommandSourceStack var0, Collection<ServerPlayer> var1, AdvancementCommands.Action var2, Advancement var3, String var4) {
      int â˜ƒ = 0;
      if (!â˜ƒ.getCriteria().containsKey(â˜ƒ)) {
         throw new CommandRuntimeException(new TranslatableComponent("commands.advancement.criterionNotFound", â˜ƒ.getChatComponent(), â˜ƒ));
      } else {
         for(ServerPlayer â˜ƒ : â˜ƒ) {
            if (â˜ƒ.performCriterion(â˜ƒ, â˜ƒ, â˜ƒ)) {
               ++â˜ƒ;
            }
         }

         if (â˜ƒ == 0) {
            if (â˜ƒ.size() == 1) {
               throw new CommandRuntimeException(
                  new TranslatableComponent(
                     â˜ƒ.getKey() + ".criterion.to.one.failure", â˜ƒ, â˜ƒ.getChatComponent(), ((ServerPlayer)â˜ƒ.iterator().next()).getDisplayName()
                  )
               );
            } else {
               throw new CommandRuntimeException(
                  new TranslatableComponent(â˜ƒ.getKey() + ".criterion.to.many.failure", â˜ƒ, â˜ƒ.getChatComponent(), â˜ƒ.size())
               );
            }
         } else {
            if (â˜ƒ.size() == 1) {
               â˜ƒ.sendSuccess(
                  new TranslatableComponent(
                     â˜ƒ.getKey() + ".criterion.to.one.success", â˜ƒ, â˜ƒ.getChatComponent(), ((ServerPlayer)â˜ƒ.iterator().next()).getDisplayName()
                  ),
                  true
               );
            } else {
               â˜ƒ.sendSuccess(new TranslatableComponent(â˜ƒ.getKey() + ".criterion.to.many.success", â˜ƒ, â˜ƒ.getChatComponent(), â˜ƒ.size()), true);
            }

            return â˜ƒ;
         }
      }
   }

   private static List<Advancement> getAdvancements(Advancement var0, AdvancementCommands.Mode var1) {
      List<Advancement> â˜ƒ = Lists.<Advancement>newArrayList();
      if (â˜ƒ.parents) {
         for(Advancement â˜ƒx = â˜ƒ.getParent(); â˜ƒx != null; â˜ƒx = â˜ƒx.getParent()) {
            â˜ƒ.add(â˜ƒx);
         }
      }

      â˜ƒ.add(â˜ƒ);
      if (â˜ƒ.children) {
         addChildren(â˜ƒ, â˜ƒ);
      }

      return â˜ƒ;
   }

   private static void addChildren(Advancement var0, List<Advancement> var1) {
      for(Advancement â˜ƒ : â˜ƒ.getChildren()) {
         â˜ƒ.add(â˜ƒ);
         addChildren(â˜ƒ, â˜ƒ);
      }
   }

   static enum Action {
      GRANT("grant") {
         @Override
         protected boolean perform(ServerPlayer var1, Advancement var2) {
            AdvancementProgress â˜ƒ = â˜ƒ.getAdvancements().getOrStartProgress(â˜ƒ);
            if (â˜ƒ.isDone()) {
               return false;
            } else {
               for(String â˜ƒ : â˜ƒ.getRemainingCriteria()) {
                  â˜ƒ.getAdvancements().award(â˜ƒ, â˜ƒ);
               }

               return true;
            }
         }

         @Override
         protected boolean performCriterion(ServerPlayer var1, Advancement var2, String var3) {
            return â˜ƒ.getAdvancements().award(â˜ƒ, â˜ƒ);
         }
      },
      REVOKE("revoke") {
         @Override
         protected boolean perform(ServerPlayer var1, Advancement var2) {
            AdvancementProgress â˜ƒ = â˜ƒ.getAdvancements().getOrStartProgress(â˜ƒ);
            if (!â˜ƒ.hasProgress()) {
               return false;
            } else {
               for(String â˜ƒ : â˜ƒ.getCompletedCriteria()) {
                  â˜ƒ.getAdvancements().revoke(â˜ƒ, â˜ƒ);
               }

               return true;
            }
         }

         @Override
         protected boolean performCriterion(ServerPlayer var1, Advancement var2, String var3) {
            return â˜ƒ.getAdvancements().revoke(â˜ƒ, â˜ƒ);
         }
      };

      private final String key;

      Action(String var3) {
         this.key = "commands.advancement." + â˜ƒ;
      }

      public int perform(ServerPlayer var1, Iterable<Advancement> var2) {
         int â˜ƒ = 0;

         for(Advancement â˜ƒx : â˜ƒ) {
            if (this.perform(â˜ƒ, â˜ƒx)) {
               ++â˜ƒ;
            }
         }

         return â˜ƒ;
      }

      protected abstract boolean perform(ServerPlayer var1, Advancement var2);

      protected abstract boolean performCriterion(ServerPlayer var1, Advancement var2, String var3);

      protected String getKey() {
         return this.key;
      }
   }

   static enum Mode {
      ONLY(false, false),
      THROUGH(true, true),
      FROM(false, true),
      UNTIL(true, false),
      EVERYTHING(true, true);

      final boolean parents;
      final boolean children;

      private Mode(boolean var3, boolean var4) {
         this.parents = â˜ƒ;
         this.children = â˜ƒ;
      }
   }
}
