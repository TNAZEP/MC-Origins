package net.minecraft.command.impl;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ResultConsumer;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.IntFunction;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.BlockPredicateArgument;
import net.minecraft.command.arguments.DimensionArgument;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.NBTPathArgument;
import net.minecraft.command.arguments.ObjectiveArgument;
import net.minecraft.command.arguments.RangeArgument;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.command.arguments.RotationArgument;
import net.minecraft.command.arguments.ScoreHolderArgument;
import net.minecraft.command.arguments.SwizzleArgument;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.command.impl.data.DataCommand;
import net.minecraft.command.impl.data.IDataAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.CustomBossEvent;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.WorldServer;

public class ExecuteCommand {
   private static final Dynamic2CommandExceptionType field_198421_a = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TextComponentTranslation("commands.execute.blocks.toobig", var0, var1)
   );
   private static final SimpleCommandExceptionType field_210456_b = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.execute.conditional.fail")
   );
   private static final DynamicCommandExceptionType field_210457_c = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("commands.execute.conditional.fail_count", var0)
   );
   private static final BinaryOperator<ResultConsumer<CommandSource>> field_209957_b = (var0, var1) -> (var2, var3, var4) -> {
         var0.onCommandComplete(var2, var3, var4);
         var1.onCommandComplete(var2, var3, var4);
      };

   public static void func_198378_a(CommandDispatcher<CommandSource> var0) {
      LiteralCommandNode<CommandSource> ☃ = ☃.register(Commands.func_197057_a("execute").requires(var0x -> var0x.func_197034_c(2)));
      ☃.register(
         Commands.func_197057_a("execute")
            .requires(var0x -> var0x.func_197034_c(2))
            .then(Commands.func_197057_a("run").redirect(☃.getRoot()))
            .then(func_198394_a(☃, Commands.func_197057_a("if"), true))
            .then(func_198394_a(☃, Commands.func_197057_a("unless"), false))
            .then(Commands.func_197057_a("as").then(Commands.func_197056_a("targets", EntityArgument.func_197093_b()).fork(☃, var0x -> {
               List<CommandSource> ☃ = Lists.<CommandSource>newArrayList();
      
               for(Entity ☃x : EntityArgument.func_197087_c(var0x, "targets")) {
                  ☃.add(var0x.getSource().func_197024_a(☃x));
               }
      
               return ☃;
            })))
            .then(Commands.func_197057_a("at").then(Commands.func_197056_a("targets", EntityArgument.func_197093_b()).fork(☃, var0x -> {
               List<CommandSource> ☃ = Lists.<CommandSource>newArrayList();
      
               for(Entity ☃x : EntityArgument.func_197087_c(var0x, "targets")) {
                  ☃.add(var0x.getSource().func_201003_a((WorldServer)☃x.field_70170_p).func_201009_a(☃x.func_174791_d()).func_201007_a(☃x.func_189653_aC()));
               }
      
               return ☃;
            })))
            .then(
               Commands.func_197057_a("store")
                  .then(func_198392_a(☃, Commands.func_197057_a("result"), true))
                  .then(func_198392_a(☃, Commands.func_197057_a("success"), false))
            )
            .then(
               Commands.func_197057_a("positioned")
                  .then(
                     Commands.func_197056_a("pos", Vec3Argument.func_197301_a())
                        .redirect(☃, var0x -> var0x.getSource().func_201009_a(Vec3Argument.func_197300_a(var0x, "pos")))
                  )
                  .then(Commands.func_197057_a("as").then(Commands.func_197056_a("targets", EntityArgument.func_197093_b()).fork(☃, var0x -> {
                     List<CommandSource> ☃ = Lists.<CommandSource>newArrayList();
            
                     for(Entity ☃x : EntityArgument.func_197087_c(var0x, "targets")) {
                        ☃.add(var0x.getSource().func_201009_a(☃x.func_174791_d()));
                     }
            
                     return ☃;
                  })))
            )
            .then(
               Commands.func_197057_a("rotated")
                  .then(
                     Commands.func_197056_a("rot", RotationArgument.func_197288_a())
                        .redirect(☃, var0x -> var0x.getSource().func_201007_a(RotationArgument.func_200384_a(var0x, "rot").func_197282_b(var0x.getSource())))
                  )
                  .then(Commands.func_197057_a("as").then(Commands.func_197056_a("targets", EntityArgument.func_197093_b()).fork(☃, var0x -> {
                     List<CommandSource> ☃ = Lists.<CommandSource>newArrayList();
            
                     for(Entity ☃x : EntityArgument.func_197087_c(var0x, "targets")) {
                        ☃.add(var0x.getSource().func_201007_a(☃x.func_189653_aC()));
                     }
            
                     return ☃;
                  })))
            )
            .then(
               Commands.func_197057_a("facing")
                  .then(
                     Commands.func_197057_a("entity")
                        .then(
                           Commands.func_197056_a("targets", EntityArgument.func_197093_b())
                              .then(Commands.func_197056_a("anchor", EntityAnchorArgument.func_201024_a()).fork(☃, var0x -> {
                                 List<CommandSource> ☃ = Lists.<CommandSource>newArrayList();
                                 EntityAnchorArgument.Type ☃x = EntityAnchorArgument.func_201023_a(var0x, "anchor");
                        
                                 for(Entity ☃xx : EntityArgument.func_197087_c(var0x, "targets")) {
                                    ☃.add(var0x.getSource().func_201006_a(☃xx, ☃x));
                                 }
                        
                                 return ☃;
                              }))
                        )
                  )
                  .then(
                     Commands.func_197056_a("pos", Vec3Argument.func_197301_a())
                        .redirect(☃, var0x -> var0x.getSource().func_201005_b(Vec3Argument.func_197300_a(var0x, "pos")))
                  )
            )
            .then(
               Commands.func_197057_a("align")
                  .then(
                     Commands.func_197056_a("axes", SwizzleArgument.func_197293_a())
                        .redirect(
                           ☃,
                           var0x -> var0x.getSource()
                                 .func_201009_a(var0x.getSource().func_197036_d().func_197746_a(SwizzleArgument.func_197291_a(var0x, "axes")))
                        )
                  )
            )
            .then(
               Commands.func_197057_a("anchored")
                  .then(
                     Commands.func_197056_a("anchor", EntityAnchorArgument.func_201024_a())
                        .redirect(☃, var0x -> var0x.getSource().func_201010_a(EntityAnchorArgument.func_201023_a(var0x, "anchor")))
                  )
            )
            .then(
               Commands.func_197057_a("in")
                  .then(
                     Commands.func_197056_a("dimension", DimensionArgument.func_212595_a())
                        .redirect(
                           ☃,
                           var0x -> var0x.getSource()
                                 .func_201003_a(var0x.getSource().func_197028_i().func_71218_a(DimensionArgument.func_212592_a(var0x, "dimension")))
                        )
                  )
            )
      );
   }

   private static ArgumentBuilder<CommandSource, ?> func_198392_a(
      LiteralCommandNode<CommandSource> var0, LiteralArgumentBuilder<CommandSource> var1, boolean var2
   ) {
      ☃.then(
         Commands.func_197057_a("score")
            .then(
               Commands.func_197056_a("targets", ScoreHolderArgument.func_197214_b())
                  .suggests(ScoreHolderArgument.field_201326_a)
                  .then(
                     Commands.func_197056_a("objective", ObjectiveArgument.func_197157_a())
                        .redirect(
                           ☃,
                           var1x -> func_209930_a(
                                 var1x.getSource(), ScoreHolderArgument.func_211707_c(var1x, "targets"), ObjectiveArgument.func_197158_a(var1x, "objective"), ☃
                              )
                        )
                  )
            )
      );
      ☃.then(
         Commands.func_197057_a("bossbar")
            .then(
               Commands.func_197056_a("id", ResourceLocationArgument.func_197197_a())
                  .suggests(BossBarCommand.field_201431_a)
                  .then(Commands.func_197057_a("value").redirect(☃, var1x -> func_209952_a(var1x.getSource(), BossBarCommand.func_201416_a(var1x), true, ☃)))
                  .then(Commands.func_197057_a("max").redirect(☃, var1x -> func_209952_a(var1x.getSource(), BossBarCommand.func_201416_a(var1x), false, ☃)))
            )
      );

      for(DataCommand.IDataProvider ☃ : DataCommand.field_198948_a) {
         ☃.func_198920_a(
            ☃,
            var3 -> var3.then(
                  Commands.func_197056_a("path", NBTPathArgument.func_197149_a())
                     .then(
                        Commands.func_197057_a("int")
                           .then(
                              Commands.func_197056_a("scale", DoubleArgumentType.doubleArg())
                                 .redirect(
                                    ☃,
                                    var2x -> func_198397_a(
                                          var2x.getSource(),
                                          ☃.func_198919_a(var2x),
                                          NBTPathArgument.func_197148_a(var2x, "path"),
                                          var1x -> new NBTTagInt((int)((double)var1x * DoubleArgumentType.getDouble(var2x, "scale"))),
                                          ☃
                                       )
                                 )
                           )
                     )
                     .then(
                        Commands.func_197057_a("float")
                           .then(
                              Commands.func_197056_a("scale", DoubleArgumentType.doubleArg())
                                 .redirect(
                                    ☃,
                                    var2x -> func_198397_a(
                                          var2x.getSource(),
                                          ☃.func_198919_a(var2x),
                                          NBTPathArgument.func_197148_a(var2x, "path"),
                                          var1x -> new NBTTagFloat((float)((double)var1x * DoubleArgumentType.getDouble(var2x, "scale"))),
                                          ☃
                                       )
                                 )
                           )
                     )
                     .then(
                        Commands.func_197057_a("short")
                           .then(
                              Commands.func_197056_a("scale", DoubleArgumentType.doubleArg())
                                 .redirect(
                                    ☃,
                                    var2x -> func_198397_a(
                                          var2x.getSource(),
                                          ☃.func_198919_a(var2x),
                                          NBTPathArgument.func_197148_a(var2x, "path"),
                                          var1x -> new NBTTagShort((short)((int)((double)var1x * DoubleArgumentType.getDouble(var2x, "scale")))),
                                          ☃
                                       )
                                 )
                           )
                     )
                     .then(
                        Commands.func_197057_a("long")
                           .then(
                              Commands.func_197056_a("scale", DoubleArgumentType.doubleArg())
                                 .redirect(
                                    ☃,
                                    var2x -> func_198397_a(
                                          var2x.getSource(),
                                          ☃.func_198919_a(var2x),
                                          NBTPathArgument.func_197148_a(var2x, "path"),
                                          var1x -> new NBTTagLong((long)((double)var1x * DoubleArgumentType.getDouble(var2x, "scale"))),
                                          ☃
                                       )
                                 )
                           )
                     )
                     .then(
                        Commands.func_197057_a("double")
                           .then(
                              Commands.func_197056_a("scale", DoubleArgumentType.doubleArg())
                                 .redirect(
                                    ☃,
                                    var2x -> func_198397_a(
                                          var2x.getSource(),
                                          ☃.func_198919_a(var2x),
                                          NBTPathArgument.func_197148_a(var2x, "path"),
                                          var1x -> new NBTTagDouble((double)var1x * DoubleArgumentType.getDouble(var2x, "scale")),
                                          ☃
                                       )
                                 )
                           )
                     )
                     .then(
                        Commands.func_197057_a("byte")
                           .then(
                              Commands.func_197056_a("scale", DoubleArgumentType.doubleArg())
                                 .redirect(
                                    ☃,
                                    var2x -> func_198397_a(
                                          var2x.getSource(),
                                          ☃.func_198919_a(var2x),
                                          NBTPathArgument.func_197148_a(var2x, "path"),
                                          var1x -> new NBTTagByte((byte)((int)((double)var1x * DoubleArgumentType.getDouble(var2x, "scale")))),
                                          ☃
                                       )
                                 )
                           )
                     )
               )
         );
      }

      return ☃;
   }

   private static CommandSource func_209930_a(CommandSource var0, Collection<String> var1, ScoreObjective var2, boolean var3) {
      Scoreboard ☃ = ☃.func_197028_i().func_200251_aP();
      return ☃.func_209550_a((var4x, var5, var6) -> {
         for(String ☃ : ☃) {
            Score ☃x = ☃.func_96529_a(☃, ☃);
            int ☃xx = ☃ ? var6 : (var5 ? 1 : 0);
            ☃x.func_96647_c(☃xx);
         }
      }, field_209957_b);
   }

   private static CommandSource func_209952_a(CommandSource var0, CustomBossEvent var1, boolean var2, boolean var3) {
      return ☃.func_209550_a((var3x, var4, var5) -> {
         int ☃ = ☃ ? var5 : (var4 ? 1 : 0);
         if (☃) {
            ☃.func_201362_a(☃);
         } else {
            ☃.func_201366_b(☃);
         }
      }, field_209957_b);
   }

   private static CommandSource func_198397_a(CommandSource var0, IDataAccessor var1, NBTPathArgument.NBTPath var2, IntFunction<INBTBase> var3, boolean var4) {
      return ☃.func_209550_a((var4x, var5, var6) -> {
         try {
            NBTTagCompound ☃ = ☃.func_198923_a();
            int ☃x = ☃ ? var6 : (var5 ? 1 : 0);
            ☃.func_197142_a(☃, (INBTBase)☃.apply(☃x));
            ☃.func_198925_a(☃);
         } catch (CommandSyntaxException var9) {
         }
      }, field_209957_b);
   }

   private static ArgumentBuilder<CommandSource, ?> func_198394_a(CommandNode<CommandSource> var0, LiteralArgumentBuilder<CommandSource> var1, boolean var2) {
      return ☃.then(
            Commands.func_197057_a("block")
               .then(
                  Commands.func_197056_a("pos", BlockPosArgument.func_197276_a())
                     .then(
                        func_210415_a(
                           ☃,
                           Commands.func_197056_a("block", BlockPredicateArgument.func_199824_a()),
                           ☃,
                           var0x -> BlockPredicateArgument.func_199825_a(var0x, "block")
                                 .test(new BlockWorldState(var0x.getSource().func_197023_e(), BlockPosArgument.func_197273_a(var0x, "pos"), true))
                        )
                     )
               )
         )
         .then(
            Commands.func_197057_a("score")
               .then(
                  Commands.func_197056_a("target", ScoreHolderArgument.func_197209_a())
                     .suggests(ScoreHolderArgument.field_201326_a)
                     .then(
                        ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.func_197056_a(
                                             "targetObjective", ObjectiveArgument.func_197157_a()
                                          )
                                          .then(
                                             Commands.func_197057_a("=")
                                                .then(
                                                   Commands.func_197056_a("source", ScoreHolderArgument.func_197209_a())
                                                      .suggests(ScoreHolderArgument.field_201326_a)
                                                      .then(
                                                         func_210415_a(
                                                            ☃,
                                                            Commands.func_197056_a("sourceObjective", ObjectiveArgument.func_197157_a()),
                                                            ☃,
                                                            var0x -> func_198371_a(var0x, Integer::equals)
                                                         )
                                                      )
                                                )
                                          ))
                                       .then(
                                          Commands.func_197057_a("<")
                                             .then(
                                                Commands.func_197056_a("source", ScoreHolderArgument.func_197209_a())
                                                   .suggests(ScoreHolderArgument.field_201326_a)
                                                   .then(
                                                      func_210415_a(
                                                         ☃,
                                                         Commands.func_197056_a("sourceObjective", ObjectiveArgument.func_197157_a()),
                                                         ☃,
                                                         var0x -> func_198371_a(var0x, (var0xx, var1x) -> var0xx < var1x)
                                                      )
                                                   )
                                             )
                                       ))
                                    .then(
                                       Commands.func_197057_a("<=")
                                          .then(
                                             Commands.func_197056_a("source", ScoreHolderArgument.func_197209_a())
                                                .suggests(ScoreHolderArgument.field_201326_a)
                                                .then(
                                                   func_210415_a(
                                                      ☃,
                                                      Commands.func_197056_a("sourceObjective", ObjectiveArgument.func_197157_a()),
                                                      ☃,
                                                      var0x -> func_198371_a(var0x, (var0xx, var1x) -> var0xx <= var1x)
                                                   )
                                                )
                                          )
                                    ))
                                 .then(
                                    Commands.func_197057_a(">")
                                       .then(
                                          Commands.func_197056_a("source", ScoreHolderArgument.func_197209_a())
                                             .suggests(ScoreHolderArgument.field_201326_a)
                                             .then(
                                                func_210415_a(
                                                   ☃,
                                                   Commands.func_197056_a("sourceObjective", ObjectiveArgument.func_197157_a()),
                                                   ☃,
                                                   var0x -> func_198371_a(var0x, (var0xx, var1x) -> var0xx > var1x)
                                                )
                                             )
                                       )
                                 ))
                              .then(
                                 Commands.func_197057_a(">=")
                                    .then(
                                       Commands.func_197056_a("source", ScoreHolderArgument.func_197209_a())
                                          .suggests(ScoreHolderArgument.field_201326_a)
                                          .then(
                                             func_210415_a(
                                                ☃,
                                                Commands.func_197056_a("sourceObjective", ObjectiveArgument.func_197157_a()),
                                                ☃,
                                                var0x -> func_198371_a(var0x, (var0xx, var1x) -> var0xx >= var1x)
                                             )
                                          )
                                    )
                              ))
                           .then(
                              Commands.func_197057_a("matches")
                                 .then(
                                    func_210415_a(
                                       ☃,
                                       Commands.func_197056_a("range", RangeArgument.func_211371_a()),
                                       ☃,
                                       var0x -> func_201115_a(var0x, RangeArgument.IntRange.func_211372_a(var0x, "range"))
                                    )
                                 )
                           )
                     )
               )
         )
         .then(
            Commands.func_197057_a("blocks")
               .then(
                  Commands.func_197056_a("start", BlockPosArgument.func_197276_a())
                     .then(
                        Commands.func_197056_a("end", BlockPosArgument.func_197276_a())
                           .then(
                              Commands.func_197056_a("destination", BlockPosArgument.func_197276_a())
                                 .then(func_212178_a(☃, Commands.func_197057_a("all"), ☃, false))
                                 .then(func_212178_a(☃, Commands.func_197057_a("masked"), ☃, true))
                           )
                     )
               )
         )
         .then(
            Commands.func_197057_a("entity")
               .then(
                  Commands.func_197056_a("entities", EntityArgument.func_197093_b())
                     .fork(☃, var1x -> func_198411_a(var1x, ☃, !EntityArgument.func_197087_c(var1x, "entities").isEmpty()))
                     .executes(☃ ? var0x -> {
                        int ☃ = EntityArgument.func_197087_c(var0x, "entities").size();
                        if (☃ > 0) {
                           var0x.getSource().func_197030_a(new TextComponentTranslation("commands.execute.conditional.pass_count", ☃), false);
                           return ☃;
                        } else {
                           throw field_210456_b.create();
                        }
                     } : var0x -> {
                        int ☃ = EntityArgument.func_197087_c(var0x, "entities").size();
                        if (☃ == 0) {
                           var0x.getSource().func_197030_a(new TextComponentTranslation("commands.execute.conditional.pass"), false);
                           return 1;
                        } else {
                           throw field_210457_c.create(☃);
                        }
                     })
               )
         );
   }

   private static boolean func_198371_a(CommandContext<CommandSource> var0, BiPredicate<Integer, Integer> var1) throws CommandSyntaxException {
      String ☃ = ScoreHolderArgument.func_197211_a(☃, "target");
      ScoreObjective ☃x = ObjectiveArgument.func_197158_a(☃, "targetObjective");
      String ☃xx = ScoreHolderArgument.func_197211_a(☃, "source");
      ScoreObjective ☃xxx = ObjectiveArgument.func_197158_a(☃, "sourceObjective");
      Scoreboard ☃xxxx = ☃.getSource().func_197028_i().func_200251_aP();
      if (☃xxxx.func_178819_b(☃, ☃x) && ☃xxxx.func_178819_b(☃xx, ☃xxx)) {
         Score ☃xxxxx = ☃xxxx.func_96529_a(☃, ☃x);
         Score ☃xxxxxx = ☃xxxx.func_96529_a(☃xx, ☃xxx);
         return ☃.test(☃xxxxx.func_96652_c(), ☃xxxxxx.func_96652_c());
      } else {
         return false;
      }
   }

   private static boolean func_201115_a(CommandContext<CommandSource> var0, MinMaxBounds.IntBound var1) throws CommandSyntaxException {
      String ☃ = ScoreHolderArgument.func_197211_a(☃, "target");
      ScoreObjective ☃x = ObjectiveArgument.func_197158_a(☃, "targetObjective");
      Scoreboard ☃xx = ☃.getSource().func_197028_i().func_200251_aP();
      return !☃xx.func_178819_b(☃, ☃x) ? false : ☃.func_211339_d(☃xx.func_96529_a(☃, ☃x).func_96652_c());
   }

   private static Collection<CommandSource> func_198411_a(CommandContext<CommandSource> var0, boolean var1, boolean var2) {
      return (Collection<CommandSource>)(☃ == ☃ ? Collections.singleton(☃.getSource()) : Collections.emptyList());
   }

   private static ArgumentBuilder<CommandSource, ?> func_210415_a(
      CommandNode<CommandSource> var0, ArgumentBuilder<CommandSource, ?> var1, boolean var2, ExecuteCommand.ExecuteTest var3
   ) {
      return ☃.fork(☃, var2x -> func_198411_a(var2x, ☃, ☃.test(var2x))).executes(var2x -> {
         if (☃ == ☃.test(var2x)) {
            ((CommandSource)var2x.getSource()).func_197030_a(new TextComponentTranslation("commands.execute.conditional.pass"), false);
            return 1;
         } else {
            throw field_210456_b.create();
         }
      });
   }

   private static ArgumentBuilder<CommandSource, ?> func_212178_a(
      CommandNode<CommandSource> var0, ArgumentBuilder<CommandSource, ?> var1, boolean var2, boolean var3
   ) {
      return ☃.fork(☃, var2x -> func_198411_a(var2x, ☃, func_212169_c(var2x, ☃).isPresent()))
         .executes(☃ ? var1x -> func_212175_a(var1x, ☃) : var1x -> func_212173_b(var1x, ☃));
   }

   private static int func_212175_a(CommandContext<CommandSource> var0, boolean var1) throws CommandSyntaxException {
      OptionalInt ☃ = func_212169_c(☃, ☃);
      if (☃.isPresent()) {
         ☃.getSource().func_197030_a(new TextComponentTranslation("commands.execute.conditional.pass_count", ☃.getAsInt()), false);
         return ☃.getAsInt();
      } else {
         throw field_210456_b.create();
      }
   }

   private static int func_212173_b(CommandContext<CommandSource> var0, boolean var1) throws CommandSyntaxException {
      OptionalInt ☃ = func_212169_c(☃, ☃);
      if (!☃.isPresent()) {
         ☃.getSource().func_197030_a(new TextComponentTranslation("commands.execute.conditional.pass"), false);
         return 1;
      } else {
         throw field_210457_c.create(☃.getAsInt());
      }
   }

   private static OptionalInt func_212169_c(CommandContext<CommandSource> var0, boolean var1) throws CommandSyntaxException {
      return func_198395_a(
         ☃.getSource().func_197023_e(),
         BlockPosArgument.func_197273_a(☃, "start"),
         BlockPosArgument.func_197273_a(☃, "end"),
         BlockPosArgument.func_197273_a(☃, "destination"),
         ☃
      );
   }

   private static OptionalInt func_198395_a(WorldServer var0, BlockPos var1, BlockPos var2, BlockPos var3, boolean var4) throws CommandSyntaxException {
      MutableBoundingBox ☃ = new MutableBoundingBox(☃, ☃);
      MutableBoundingBox ☃x = new MutableBoundingBox(☃, ☃.func_177971_a(☃.func_175896_b()));
      BlockPos ☃xx = new BlockPos(☃x.field_78897_a - ☃.field_78897_a, ☃x.field_78895_b - ☃.field_78895_b, ☃x.field_78896_c - ☃.field_78896_c);
      int ☃xxx = ☃.func_78883_b() * ☃.func_78882_c() * ☃.func_78880_d();
      if (☃xxx > 32768) {
         throw field_198421_a.create(32768, ☃xxx);
      } else {
         int ☃ = 0;

         for(int ☃x = ☃.field_78896_c; ☃x <= ☃.field_78892_f; ++☃x) {
            for(int ☃xx = ☃.field_78895_b; ☃xx <= ☃.field_78894_e; ++☃xx) {
               for(int ☃xxx = ☃.field_78897_a; ☃xxx <= ☃.field_78893_d; ++☃xxx) {
                  BlockPos ☃xxxx = new BlockPos(☃xxx, ☃xx, ☃x);
                  BlockPos ☃xxxxx = ☃xxxx.func_177971_a(☃xx);
                  IBlockState ☃xxxxxx = ☃.func_180495_p(☃xxxx);
                  if (!☃ || ☃xxxxxx.func_177230_c() != Blocks.field_150350_a) {
                     if (☃xxxxxx != ☃.func_180495_p(☃xxxxx)) {
                        return OptionalInt.empty();
                     }

                     TileEntity ☃xxxxxxx = ☃.func_175625_s(☃xxxx);
                     TileEntity ☃xxxxxxxx = ☃.func_175625_s(☃xxxxx);
                     if (☃xxxxxxx != null) {
                        if (☃xxxxxxxx == null) {
                           return OptionalInt.empty();
                        }

                        NBTTagCompound ☃xxxxxxxxx = ☃xxxxxxx.func_189515_b(new NBTTagCompound());
                        ☃xxxxxxxxx.func_82580_o("x");
                        ☃xxxxxxxxx.func_82580_o("y");
                        ☃xxxxxxxxx.func_82580_o("z");
                        NBTTagCompound ☃xxxxxxxxxx = ☃xxxxxxxx.func_189515_b(new NBTTagCompound());
                        ☃xxxxxxxxxx.func_82580_o("x");
                        ☃xxxxxxxxxx.func_82580_o("y");
                        ☃xxxxxxxxxx.func_82580_o("z");
                        if (!☃xxxxxxxxx.equals(☃xxxxxxxxxx)) {
                           return OptionalInt.empty();
                        }
                     }

                     ++☃;
                  }
               }
            }
         }

         return OptionalInt.of(☃);
      }
   }

   @FunctionalInterface
   interface ExecuteTest {
      boolean test(CommandContext<CommandSource> var1) throws CommandSyntaxException;
   }
}
