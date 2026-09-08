package net.minecraft.command.impl;

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
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentTranslation;

public class AdvancementCommand {
   private static final SuggestionProvider<CommandSource> field_198218_a = (var0, var1) -> {
      Collection<Advancement> ☃ = var0.getSource().func_197028_i().func_191949_aK().func_195438_b();
      return ISuggestionProvider.func_212476_a(☃.stream().map(Advancement::func_192067_g), var1);
   };

   public static void func_198199_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("advancement")
            .requires(var0x -> var0x.func_197034_c(2))
            .then(
               Commands.func_197057_a("grant")
                  .then(
                     Commands.func_197056_a("targets", EntityArgument.func_197094_d())
                        .then(
                           Commands.func_197057_a("only")
                              .then(
                                 Commands.func_197056_a("advancement", ResourceLocationArgument.func_197197_a())
                                    .suggests(field_198218_a)
                                    .executes(
                                       var0x -> func_198214_a(
                                             var0x.getSource(),
                                             EntityArgument.func_197090_e(var0x, "targets"),
                                             AdvancementCommand.Action.GRANT,
                                             func_198216_a(ResourceLocationArgument.func_197198_a(var0x, "advancement"), AdvancementCommand.Mode.ONLY)
                                          )
                                    )
                                    .then(
                                       Commands.func_197056_a("criterion", StringArgumentType.greedyString())
                                          .suggests(
                                             (var0x, var1) -> ISuggestionProvider.func_197005_b(
                                                   ResourceLocationArgument.func_197198_a(var0x, "advancement").func_192073_f().keySet(), var1
                                                )
                                          )
                                          .executes(
                                             var0x -> func_198203_a(
                                                   var0x.getSource(),
                                                   EntityArgument.func_197090_e(var0x, "targets"),
                                                   AdvancementCommand.Action.GRANT,
                                                   ResourceLocationArgument.func_197198_a(var0x, "advancement"),
                                                   StringArgumentType.getString(var0x, "criterion")
                                                )
                                          )
                                    )
                              )
                        )
                        .then(
                           Commands.func_197057_a("from")
                              .then(
                                 Commands.func_197056_a("advancement", ResourceLocationArgument.func_197197_a())
                                    .suggests(field_198218_a)
                                    .executes(
                                       var0x -> func_198214_a(
                                             var0x.getSource(),
                                             EntityArgument.func_197090_e(var0x, "targets"),
                                             AdvancementCommand.Action.GRANT,
                                             func_198216_a(ResourceLocationArgument.func_197198_a(var0x, "advancement"), AdvancementCommand.Mode.FROM)
                                          )
                                    )
                              )
                        )
                        .then(
                           Commands.func_197057_a("until")
                              .then(
                                 Commands.func_197056_a("advancement", ResourceLocationArgument.func_197197_a())
                                    .suggests(field_198218_a)
                                    .executes(
                                       var0x -> func_198214_a(
                                             var0x.getSource(),
                                             EntityArgument.func_197090_e(var0x, "targets"),
                                             AdvancementCommand.Action.GRANT,
                                             func_198216_a(ResourceLocationArgument.func_197198_a(var0x, "advancement"), AdvancementCommand.Mode.UNTIL)
                                          )
                                    )
                              )
                        )
                        .then(
                           Commands.func_197057_a("through")
                              .then(
                                 Commands.func_197056_a("advancement", ResourceLocationArgument.func_197197_a())
                                    .suggests(field_198218_a)
                                    .executes(
                                       var0x -> func_198214_a(
                                             var0x.getSource(),
                                             EntityArgument.func_197090_e(var0x, "targets"),
                                             AdvancementCommand.Action.GRANT,
                                             func_198216_a(ResourceLocationArgument.func_197198_a(var0x, "advancement"), AdvancementCommand.Mode.THROUGH)
                                          )
                                    )
                              )
                        )
                        .then(
                           Commands.func_197057_a("everything")
                              .executes(
                                 var0x -> func_198214_a(
                                       var0x.getSource(),
                                       EntityArgument.func_197090_e(var0x, "targets"),
                                       AdvancementCommand.Action.GRANT,
                                       var0x.getSource().func_197028_i().func_191949_aK().func_195438_b()
                                    )
                              )
                        )
                  )
            )
            .then(
               Commands.func_197057_a("revoke")
                  .then(
                     Commands.func_197056_a("targets", EntityArgument.func_197094_d())
                        .then(
                           Commands.func_197057_a("only")
                              .then(
                                 Commands.func_197056_a("advancement", ResourceLocationArgument.func_197197_a())
                                    .suggests(field_198218_a)
                                    .executes(
                                       var0x -> func_198214_a(
                                             var0x.getSource(),
                                             EntityArgument.func_197090_e(var0x, "targets"),
                                             AdvancementCommand.Action.REVOKE,
                                             func_198216_a(ResourceLocationArgument.func_197198_a(var0x, "advancement"), AdvancementCommand.Mode.ONLY)
                                          )
                                    )
                                    .then(
                                       Commands.func_197056_a("criterion", StringArgumentType.greedyString())
                                          .suggests(
                                             (var0x, var1) -> ISuggestionProvider.func_197005_b(
                                                   ResourceLocationArgument.func_197198_a(var0x, "advancement").func_192073_f().keySet(), var1
                                                )
                                          )
                                          .executes(
                                             var0x -> func_198203_a(
                                                   var0x.getSource(),
                                                   EntityArgument.func_197090_e(var0x, "targets"),
                                                   AdvancementCommand.Action.REVOKE,
                                                   ResourceLocationArgument.func_197198_a(var0x, "advancement"),
                                                   StringArgumentType.getString(var0x, "criterion")
                                                )
                                          )
                                    )
                              )
                        )
                        .then(
                           Commands.func_197057_a("from")
                              .then(
                                 Commands.func_197056_a("advancement", ResourceLocationArgument.func_197197_a())
                                    .suggests(field_198218_a)
                                    .executes(
                                       var0x -> func_198214_a(
                                             var0x.getSource(),
                                             EntityArgument.func_197090_e(var0x, "targets"),
                                             AdvancementCommand.Action.REVOKE,
                                             func_198216_a(ResourceLocationArgument.func_197198_a(var0x, "advancement"), AdvancementCommand.Mode.FROM)
                                          )
                                    )
                              )
                        )
                        .then(
                           Commands.func_197057_a("until")
                              .then(
                                 Commands.func_197056_a("advancement", ResourceLocationArgument.func_197197_a())
                                    .suggests(field_198218_a)
                                    .executes(
                                       var0x -> func_198214_a(
                                             var0x.getSource(),
                                             EntityArgument.func_197090_e(var0x, "targets"),
                                             AdvancementCommand.Action.REVOKE,
                                             func_198216_a(ResourceLocationArgument.func_197198_a(var0x, "advancement"), AdvancementCommand.Mode.UNTIL)
                                          )
                                    )
                              )
                        )
                        .then(
                           Commands.func_197057_a("through")
                              .then(
                                 Commands.func_197056_a("advancement", ResourceLocationArgument.func_197197_a())
                                    .suggests(field_198218_a)
                                    .executes(
                                       var0x -> func_198214_a(
                                             var0x.getSource(),
                                             EntityArgument.func_197090_e(var0x, "targets"),
                                             AdvancementCommand.Action.REVOKE,
                                             func_198216_a(ResourceLocationArgument.func_197198_a(var0x, "advancement"), AdvancementCommand.Mode.THROUGH)
                                          )
                                    )
                              )
                        )
                        .then(
                           Commands.func_197057_a("everything")
                              .executes(
                                 var0x -> func_198214_a(
                                       var0x.getSource(),
                                       EntityArgument.func_197090_e(var0x, "targets"),
                                       AdvancementCommand.Action.REVOKE,
                                       var0x.getSource().func_197028_i().func_191949_aK().func_195438_b()
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int func_198214_a(CommandSource var0, Collection<EntityPlayerMP> var1, AdvancementCommand.Action var2, Collection<Advancement> var3) {
      int ☃ = 0;

      for(EntityPlayerMP ☃x : ☃) {
         ☃ += ☃.func_198180_a(☃x, ☃);
      }

      if (☃ == 0) {
         if (☃.size() == 1) {
            if (☃.size() == 1) {
               throw new CommandException(
                  new TextComponentTranslation(
                     ☃.func_198181_a() + ".one.to.one.failure",
                     ((Advancement)☃.iterator().next()).func_193123_j(),
                     ((EntityPlayerMP)☃.iterator().next()).func_145748_c_()
                  )
               );
            } else {
               throw new CommandException(
                  new TextComponentTranslation(☃.func_198181_a() + ".one.to.many.failure", ((Advancement)☃.iterator().next()).func_193123_j(), ☃.size())
               );
            }
         } else if (☃.size() == 1) {
            throw new CommandException(
               new TextComponentTranslation(☃.func_198181_a() + ".many.to.one.failure", ☃.size(), ((EntityPlayerMP)☃.iterator().next()).func_145748_c_())
            );
         } else {
            throw new CommandException(new TextComponentTranslation(☃.func_198181_a() + ".many.to.many.failure", ☃.size(), ☃.size()));
         }
      } else {
         if (☃.size() == 1) {
            if (☃.size() == 1) {
               ☃.func_197030_a(
                  new TextComponentTranslation(
                     ☃.func_198181_a() + ".one.to.one.success",
                     ((Advancement)☃.iterator().next()).func_193123_j(),
                     ((EntityPlayerMP)☃.iterator().next()).func_145748_c_()
                  ),
                  true
               );
            } else {
               ☃.func_197030_a(
                  new TextComponentTranslation(☃.func_198181_a() + ".one.to.many.success", ((Advancement)☃.iterator().next()).func_193123_j(), ☃.size()), true
               );
            }
         } else if (☃.size() == 1) {
            ☃.func_197030_a(
               new TextComponentTranslation(☃.func_198181_a() + ".many.to.one.success", ☃.size(), ((EntityPlayerMP)☃.iterator().next()).func_145748_c_()), true
            );
         } else {
            ☃.func_197030_a(new TextComponentTranslation(☃.func_198181_a() + ".many.to.many.success", ☃.size(), ☃.size()), true);
         }

         return ☃;
      }
   }

   private static int func_198203_a(CommandSource var0, Collection<EntityPlayerMP> var1, AdvancementCommand.Action var2, Advancement var3, String var4) {
      int ☃ = 0;
      if (!☃.func_192073_f().containsKey(☃)) {
         throw new CommandException(new TextComponentTranslation("commands.advancement.criterionNotFound", ☃.func_193123_j(), ☃));
      } else {
         for(EntityPlayerMP ☃ : ☃) {
            if (☃.func_198182_a(☃, ☃, ☃)) {
               ++☃;
            }
         }

         if (☃ == 0) {
            if (☃.size() == 1) {
               throw new CommandException(
                  new TextComponentTranslation(
                     ☃.func_198181_a() + ".criterion.to.one.failure", ☃, ☃.func_193123_j(), ((EntityPlayerMP)☃.iterator().next()).func_145748_c_()
                  )
               );
            } else {
               throw new CommandException(new TextComponentTranslation(☃.func_198181_a() + ".criterion.to.many.failure", ☃, ☃.func_193123_j(), ☃.size()));
            }
         } else {
            if (☃.size() == 1) {
               ☃.func_197030_a(
                  new TextComponentTranslation(
                     ☃.func_198181_a() + ".criterion.to.one.success", ☃, ☃.func_193123_j(), ((EntityPlayerMP)☃.iterator().next()).func_145748_c_()
                  ),
                  true
               );
            } else {
               ☃.func_197030_a(new TextComponentTranslation(☃.func_198181_a() + ".criterion.to.many.success", ☃, ☃.func_193123_j(), ☃.size()), true);
            }

            return ☃;
         }
      }
   }

   private static List<Advancement> func_198216_a(Advancement var0, AdvancementCommand.Mode var1) {
      List<Advancement> ☃ = Lists.<Advancement>newArrayList();
      if (☃.field_198194_f) {
         for(Advancement ☃x = ☃.func_192070_b(); ☃x != null; ☃x = ☃x.func_192070_b()) {
            ☃.add(☃x);
         }
      }

      ☃.add(☃);
      if (☃.field_198195_g) {
         func_198207_a(☃, ☃);
      }

      return ☃;
   }

   private static void func_198207_a(Advancement var0, List<Advancement> var1) {
      for(Advancement ☃ : ☃.func_192069_e()) {
         ☃.add(☃);
         func_198207_a(☃, ☃);
      }
   }

   static enum Action {
      GRANT("grant") {
         @Override
         protected boolean func_198179_a(EntityPlayerMP var1, Advancement var2) {
            AdvancementProgress ☃ = ☃.func_192039_O().func_192747_a(☃);
            if (☃.func_192105_a()) {
               return false;
            } else {
               for(String ☃ : ☃.func_192107_d()) {
                  ☃.func_192039_O().func_192750_a(☃, ☃);
               }

               return true;
            }
         }

         @Override
         protected boolean func_198182_a(EntityPlayerMP var1, Advancement var2, String var3) {
            return ☃.func_192039_O().func_192750_a(☃, ☃);
         }
      },
      REVOKE("revoke") {
         @Override
         protected boolean func_198179_a(EntityPlayerMP var1, Advancement var2) {
            AdvancementProgress ☃ = ☃.func_192039_O().func_192747_a(☃);
            if (!☃.func_192108_b()) {
               return false;
            } else {
               for(String ☃ : ☃.func_192102_e()) {
                  ☃.func_192039_O().func_192744_b(☃, ☃);
               }

               return true;
            }
         }

         @Override
         protected boolean func_198182_a(EntityPlayerMP var1, Advancement var2, String var3) {
            return ☃.func_192039_O().func_192744_b(☃, ☃);
         }
      };

      private final String field_198186_c;

      private Action(String var3) {
         this.field_198186_c = "commands.advancement." + ☃;
      }

      public int func_198180_a(EntityPlayerMP var1, Iterable<Advancement> var2) {
         int ☃ = 0;

         for(Advancement ☃x : ☃) {
            if (this.func_198179_a(☃, ☃x)) {
               ++☃;
            }
         }

         return ☃;
      }

      protected abstract boolean func_198179_a(EntityPlayerMP var1, Advancement var2);

      protected abstract boolean func_198182_a(EntityPlayerMP var1, Advancement var2, String var3);

      protected String func_198181_a() {
         return this.field_198186_c;
      }
   }

   static enum Mode {
      ONLY(false, false),
      THROUGH(true, true),
      FROM(false, true),
      UNTIL(true, false),
      EVERYTHING(true, true);

      private final boolean field_198194_f;
      private final boolean field_198195_g;

      private Mode(boolean var3, boolean var4) {
         this.field_198194_f = ☃;
         this.field_198195_g = ☃;
      }
   }
}
