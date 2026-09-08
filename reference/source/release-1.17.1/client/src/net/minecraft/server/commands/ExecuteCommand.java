package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.Command;
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
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.IntFunction;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.commands.arguments.ObjectiveArgument;
import net.minecraft.commands.arguments.RangeArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.ScoreHolderArgument;
import net.minecraft.commands.arguments.blocks.BlockPredicateArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.coordinates.RotationArgument;
import net.minecraft.commands.arguments.coordinates.SwizzleArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.bossevents.CustomBossEvent;
import net.minecraft.server.commands.data.DataAccessor;
import net.minecraft.server.commands.data.DataCommands;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.PredicateManager;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;

public class ExecuteCommand {
   private static final int MAX_TEST_AREA = 32768;
   private static final Dynamic2CommandExceptionType ERROR_AREA_TOO_LARGE = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TranslatableComponent("commands.execute.blocks.toobig", var0, var1)
   );
   private static final SimpleCommandExceptionType ERROR_CONDITIONAL_FAILED = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.execute.conditional.fail")
   );
   private static final DynamicCommandExceptionType ERROR_CONDITIONAL_FAILED_COUNT = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("commands.execute.conditional.fail_count", var0)
   );
   private static final BinaryOperator<ResultConsumer<CommandSourceStack>> CALLBACK_CHAINER = (var0, var1) -> (var2, var3, var4) -> {
         var0.onCommandComplete(var2, var3, var4);
         var1.onCommandComplete(var2, var3, var4);
      };
   private static final SuggestionProvider<CommandSourceStack> SUGGEST_PREDICATE = (var0, var1) -> {
      PredicateManager â˜ƒ = var0.getSource().getServer().getPredicateManager();
      return SharedSuggestionProvider.suggestResource(â˜ƒ.getKeys(), var1);
   };

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      LiteralCommandNode<CommandSourceStack> â˜ƒ = â˜ƒ.register(Commands.literal("execute").requires(var0x -> var0x.hasPermission(2)));
      â˜ƒ.register(
         Commands.literal("execute")
            .requires(var0x -> var0x.hasPermission(2))
            .then(Commands.literal("run").redirect(â˜ƒ.getRoot()))
            .then(addConditionals(â˜ƒ, Commands.literal("if"), true))
            .then(addConditionals(â˜ƒ, Commands.literal("unless"), false))
            .then(Commands.literal("as").then(Commands.argument("targets", EntityArgument.entities()).fork(â˜ƒ, var0x -> {
               List<CommandSourceStack> â˜ƒ = Lists.<CommandSourceStack>newArrayList();
      
               for(Entity â˜ƒx : EntityArgument.getOptionalEntities(var0x, "targets")) {
                  â˜ƒ.add(var0x.getSource().withEntity(â˜ƒx));
               }
      
               return â˜ƒ;
            })))
            .then(Commands.literal("at").then(Commands.argument("targets", EntityArgument.entities()).fork(â˜ƒ, var0x -> {
               List<CommandSourceStack> â˜ƒ = Lists.<CommandSourceStack>newArrayList();
      
               for(Entity â˜ƒx : EntityArgument.getOptionalEntities(var0x, "targets")) {
                  â˜ƒ.add(var0x.getSource().withLevel((ServerLevel)â˜ƒx.level).withPosition(â˜ƒx.position()).withRotation(â˜ƒx.getRotationVector()));
               }
      
               return â˜ƒ;
            })))
            .then(Commands.literal("store").then(wrapStores(â˜ƒ, Commands.literal("result"), true)).then(wrapStores(â˜ƒ, Commands.literal("success"), false)))
            .then(
               Commands.literal("positioned")
                  .then(
                     Commands.argument("pos", Vec3Argument.vec3())
                        .redirect(â˜ƒ, var0x -> var0x.getSource().withPosition(Vec3Argument.getVec3(var0x, "pos")).withAnchor(EntityAnchorArgument.Anchor.FEET))
                  )
                  .then(Commands.literal("as").then(Commands.argument("targets", EntityArgument.entities()).fork(â˜ƒ, var0x -> {
                     List<CommandSourceStack> â˜ƒ = Lists.<CommandSourceStack>newArrayList();
            
                     for(Entity â˜ƒx : EntityArgument.getOptionalEntities(var0x, "targets")) {
                        â˜ƒ.add(var0x.getSource().withPosition(â˜ƒx.position()));
                     }
            
                     return â˜ƒ;
                  })))
            )
            .then(
               Commands.literal("rotated")
                  .then(
                     Commands.argument("rot", RotationArgument.rotation())
                        .redirect(â˜ƒ, var0x -> var0x.getSource().withRotation(RotationArgument.getRotation(var0x, "rot").getRotation(var0x.getSource())))
                  )
                  .then(Commands.literal("as").then(Commands.argument("targets", EntityArgument.entities()).fork(â˜ƒ, var0x -> {
                     List<CommandSourceStack> â˜ƒ = Lists.<CommandSourceStack>newArrayList();
            
                     for(Entity â˜ƒx : EntityArgument.getOptionalEntities(var0x, "targets")) {
                        â˜ƒ.add(var0x.getSource().withRotation(â˜ƒx.getRotationVector()));
                     }
            
                     return â˜ƒ;
                  })))
            )
            .then(
               Commands.literal("facing")
                  .then(
                     Commands.literal("entity")
                        .then(
                           Commands.argument("targets", EntityArgument.entities())
                              .then(Commands.argument("anchor", EntityAnchorArgument.anchor()).fork(â˜ƒ, var0x -> {
                                 List<CommandSourceStack> â˜ƒ = Lists.<CommandSourceStack>newArrayList();
                                 EntityAnchorArgument.Anchor â˜ƒx = EntityAnchorArgument.getAnchor(var0x, "anchor");
                        
                                 for(Entity â˜ƒxx : EntityArgument.getOptionalEntities(var0x, "targets")) {
                                    â˜ƒ.add(var0x.getSource().facing(â˜ƒxx, â˜ƒx));
                                 }
                        
                                 return â˜ƒ;
                              }))
                        )
                  )
                  .then(Commands.argument("pos", Vec3Argument.vec3()).redirect(â˜ƒ, var0x -> var0x.getSource().facing(Vec3Argument.getVec3(var0x, "pos"))))
            )
            .then(
               Commands.literal("align")
                  .then(
                     Commands.argument("axes", SwizzleArgument.swizzle())
                        .redirect(
                           â˜ƒ, var0x -> var0x.getSource().withPosition(var0x.getSource().getPosition().align(SwizzleArgument.getSwizzle(var0x, "axes")))
                        )
                  )
            )
            .then(
               Commands.literal("anchored")
                  .then(
                     Commands.argument("anchor", EntityAnchorArgument.anchor())
                        .redirect(â˜ƒ, var0x -> var0x.getSource().withAnchor(EntityAnchorArgument.getAnchor(var0x, "anchor")))
                  )
            )
            .then(
               Commands.literal("in")
                  .then(
                     Commands.argument("dimension", DimensionArgument.dimension())
                        .redirect(â˜ƒ, var0x -> var0x.getSource().withLevel(DimensionArgument.getDimension(var0x, "dimension")))
                  )
            )
      );
   }

   private static ArgumentBuilder<CommandSourceStack, ?> wrapStores(
      LiteralCommandNode<CommandSourceStack> var0, LiteralArgumentBuilder<CommandSourceStack> var1, boolean var2
   ) {
      â˜ƒ.then(
         Commands.literal("score")
            .then(
               Commands.argument("targets", ScoreHolderArgument.scoreHolders())
                  .suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
                  .then(
                     Commands.argument("objective", ObjectiveArgument.objective())
                        .redirect(
                           â˜ƒ,
                           var1x -> storeValue(
                                 var1x.getSource(),
                                 ScoreHolderArgument.getNamesWithDefaultWildcard(var1x, "targets"),
                                 ObjectiveArgument.getObjective(var1x, "objective"),
                                 â˜ƒ
                              )
                        )
                  )
            )
      );
      â˜ƒ.then(
         Commands.literal("bossbar")
            .then(
               Commands.argument("id", ResourceLocationArgument.id())
                  .suggests(BossBarCommands.SUGGEST_BOSS_BAR)
                  .then(Commands.literal("value").redirect(â˜ƒ, var1x -> storeValue(var1x.getSource(), BossBarCommands.getBossBar(var1x), true, â˜ƒ)))
                  .then(Commands.literal("max").redirect(â˜ƒ, var1x -> storeValue(var1x.getSource(), BossBarCommands.getBossBar(var1x), false, â˜ƒ)))
            )
      );

      for(DataCommands.DataProvider â˜ƒ : DataCommands.TARGET_PROVIDERS) {
         â˜ƒ.wrap(
            â˜ƒ,
            var3 -> var3.then(
                  Commands.argument("path", NbtPathArgument.nbtPath())
                     .then(
                        Commands.literal("int")
                           .then(
                              Commands.argument("scale", DoubleArgumentType.doubleArg())
                                 .redirect(
                                    â˜ƒ,
                                    var2x -> storeData(
                                          var2x.getSource(),
                                          â˜ƒ.access(var2x),
                                          NbtPathArgument.getPath(var2x, "path"),
                                          var1x -> IntTag.valueOf((int)((double)var1x * DoubleArgumentType.getDouble(var2x, "scale"))),
                                          â˜ƒ
                                       )
                                 )
                           )
                     )
                     .then(
                        Commands.literal("float")
                           .then(
                              Commands.argument("scale", DoubleArgumentType.doubleArg())
                                 .redirect(
                                    â˜ƒ,
                                    var2x -> storeData(
                                          var2x.getSource(),
                                          â˜ƒ.access(var2x),
                                          NbtPathArgument.getPath(var2x, "path"),
                                          var1x -> FloatTag.valueOf((float)((double)var1x * DoubleArgumentType.getDouble(var2x, "scale"))),
                                          â˜ƒ
                                       )
                                 )
                           )
                     )
                     .then(
                        Commands.literal("short")
                           .then(
                              Commands.argument("scale", DoubleArgumentType.doubleArg())
                                 .redirect(
                                    â˜ƒ,
                                    var2x -> storeData(
                                          var2x.getSource(),
                                          â˜ƒ.access(var2x),
                                          NbtPathArgument.getPath(var2x, "path"),
                                          var1x -> ShortTag.valueOf((short)((int)((double)var1x * DoubleArgumentType.getDouble(var2x, "scale")))),
                                          â˜ƒ
                                       )
                                 )
                           )
                     )
                     .then(
                        Commands.literal("long")
                           .then(
                              Commands.argument("scale", DoubleArgumentType.doubleArg())
                                 .redirect(
                                    â˜ƒ,
                                    var2x -> storeData(
                                          var2x.getSource(),
                                          â˜ƒ.access(var2x),
                                          NbtPathArgument.getPath(var2x, "path"),
                                          var1x -> LongTag.valueOf((long)((double)var1x * DoubleArgumentType.getDouble(var2x, "scale"))),
                                          â˜ƒ
                                       )
                                 )
                           )
                     )
                     .then(
                        Commands.literal("double")
                           .then(
                              Commands.argument("scale", DoubleArgumentType.doubleArg())
                                 .redirect(
                                    â˜ƒ,
                                    var2x -> storeData(
                                          var2x.getSource(),
                                          â˜ƒ.access(var2x),
                                          NbtPathArgument.getPath(var2x, "path"),
                                          var1x -> DoubleTag.valueOf((double)var1x * DoubleArgumentType.getDouble(var2x, "scale")),
                                          â˜ƒ
                                       )
                                 )
                           )
                     )
                     .then(
                        Commands.literal("byte")
                           .then(
                              Commands.argument("scale", DoubleArgumentType.doubleArg())
                                 .redirect(
                                    â˜ƒ,
                                    var2x -> storeData(
                                          var2x.getSource(),
                                          â˜ƒ.access(var2x),
                                          NbtPathArgument.getPath(var2x, "path"),
                                          var1x -> ByteTag.valueOf((byte)((int)((double)var1x * DoubleArgumentType.getDouble(var2x, "scale")))),
                                          â˜ƒ
                                       )
                                 )
                           )
                     )
               )
         );
      }

      return â˜ƒ;
   }

   private static CommandSourceStack storeValue(CommandSourceStack var0, Collection<String> var1, Objective var2, boolean var3) {
      Scoreboard â˜ƒ = â˜ƒ.getServer().getScoreboard();
      return â˜ƒ.withCallback((var4x, var5, var6) -> {
         for(String â˜ƒ : â˜ƒ) {
            Score â˜ƒx = â˜ƒ.getOrCreatePlayerScore(â˜ƒ, â˜ƒ);
            int â˜ƒxx = â˜ƒ ? var6 : (var5 ? 1 : 0);
            â˜ƒx.setScore(â˜ƒxx);
         }
      }, CALLBACK_CHAINER);
   }

   private static CommandSourceStack storeValue(CommandSourceStack var0, CustomBossEvent var1, boolean var2, boolean var3) {
      return â˜ƒ.withCallback((var3x, var4, var5) -> {
         int â˜ƒ = â˜ƒ ? var5 : (var4 ? 1 : 0);
         if (â˜ƒ) {
            â˜ƒ.setValue(â˜ƒ);
         } else {
            â˜ƒ.setMax(â˜ƒ);
         }
      }, CALLBACK_CHAINER);
   }

   private static CommandSourceStack storeData(CommandSourceStack var0, DataAccessor var1, NbtPathArgument.NbtPath var2, IntFunction<Tag> var3, boolean var4) {
      return â˜ƒ.withCallback((var4x, var5, var6) -> {
         try {
            CompoundTag â˜ƒ = â˜ƒ.getData();
            int â˜ƒx = â˜ƒ ? var6 : (var5 ? 1 : 0);
            â˜ƒ.set(â˜ƒ, () -> (Tag)â˜ƒ.apply(â˜ƒ));
            â˜ƒ.setData(â˜ƒ);
         } catch (CommandSyntaxException var9) {
         }
      }, CALLBACK_CHAINER);
   }

   private static ArgumentBuilder<CommandSourceStack, ?> addConditionals(
      CommandNode<CommandSourceStack> var0, LiteralArgumentBuilder<CommandSourceStack> var1, boolean var2
   ) {
      â˜ƒ.then(
            Commands.literal("block")
               .then(
                  Commands.argument("pos", BlockPosArgument.blockPos())
                     .then(
                        addConditional(
                           â˜ƒ,
                           Commands.argument("block", BlockPredicateArgument.blockPredicate()),
                           â˜ƒ,
                           var0x -> BlockPredicateArgument.getBlockPredicate(var0x, "block")
                                 .test(new BlockInWorld(var0x.getSource().getLevel(), BlockPosArgument.getLoadedBlockPos(var0x, "pos"), true))
                        )
                     )
               )
         )
         .then(
            Commands.literal("score")
               .then(
                  Commands.argument("target", ScoreHolderArgument.scoreHolder())
                     .suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
                     .then(
                        ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument(
                                             "targetObjective", ObjectiveArgument.objective()
                                          )
                                          .then(
                                             Commands.literal("=")
                                                .then(
                                                   Commands.argument("source", ScoreHolderArgument.scoreHolder())
                                                      .suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
                                                      .then(
                                                         addConditional(
                                                            â˜ƒ,
                                                            Commands.argument("sourceObjective", ObjectiveArgument.objective()),
                                                            â˜ƒ,
                                                            var0x -> checkScore(var0x, Integer::equals)
                                                         )
                                                      )
                                                )
                                          ))
                                       .then(
                                          Commands.literal("<")
                                             .then(
                                                Commands.argument("source", ScoreHolderArgument.scoreHolder())
                                                   .suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
                                                   .then(
                                                      addConditional(
                                                         â˜ƒ,
                                                         Commands.argument("sourceObjective", ObjectiveArgument.objective()),
                                                         â˜ƒ,
                                                         var0x -> checkScore(var0x, (var0xx, var1x) -> var0xx < var1x)
                                                      )
                                                   )
                                             )
                                       ))
                                    .then(
                                       Commands.literal("<=")
                                          .then(
                                             Commands.argument("source", ScoreHolderArgument.scoreHolder())
                                                .suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
                                                .then(
                                                   addConditional(
                                                      â˜ƒ,
                                                      Commands.argument("sourceObjective", ObjectiveArgument.objective()),
                                                      â˜ƒ,
                                                      var0x -> checkScore(var0x, (var0xx, var1x) -> var0xx <= var1x)
                                                   )
                                                )
                                          )
                                    ))
                                 .then(
                                    Commands.literal(">")
                                       .then(
                                          Commands.argument("source", ScoreHolderArgument.scoreHolder())
                                             .suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
                                             .then(
                                                addConditional(
                                                   â˜ƒ,
                                                   Commands.argument("sourceObjective", ObjectiveArgument.objective()),
                                                   â˜ƒ,
                                                   var0x -> checkScore(var0x, (var0xx, var1x) -> var0xx > var1x)
                                                )
                                             )
                                       )
                                 ))
                              .then(
                                 Commands.literal(">=")
                                    .then(
                                       Commands.argument("source", ScoreHolderArgument.scoreHolder())
                                          .suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
                                          .then(
                                             addConditional(
                                                â˜ƒ,
                                                Commands.argument("sourceObjective", ObjectiveArgument.objective()),
                                                â˜ƒ,
                                                var0x -> checkScore(var0x, (var0xx, var1x) -> var0xx >= var1x)
                                             )
                                          )
                                    )
                              ))
                           .then(
                              Commands.literal("matches")
                                 .then(
                                    addConditional(
                                       â˜ƒ,
                                       Commands.argument("range", RangeArgument.intRange()),
                                       â˜ƒ,
                                       var0x -> checkScore(var0x, RangeArgument.Ints.getRange(var0x, "range"))
                                    )
                                 )
                           )
                     )
               )
         )
         .then(
            Commands.literal("blocks")
               .then(
                  Commands.argument("start", BlockPosArgument.blockPos())
                     .then(
                        Commands.argument("end", BlockPosArgument.blockPos())
                           .then(
                              Commands.argument("destination", BlockPosArgument.blockPos())
                                 .then(addIfBlocksConditional(â˜ƒ, Commands.literal("all"), â˜ƒ, false))
                                 .then(addIfBlocksConditional(â˜ƒ, Commands.literal("masked"), â˜ƒ, true))
                           )
                     )
               )
         )
         .then(
            Commands.literal("entity")
               .then(
                  Commands.argument("entities", EntityArgument.entities())
                     .fork(â˜ƒ, var1x -> expect(var1x, â˜ƒ, !EntityArgument.getOptionalEntities(var1x, "entities").isEmpty()))
                     .executes(createNumericConditionalHandler(â˜ƒ, var0x -> EntityArgument.getOptionalEntities(var0x, "entities").size()))
               )
         )
         .then(
            Commands.literal("predicate")
               .then(
                  addConditional(
                     â˜ƒ,
                     Commands.argument("predicate", ResourceLocationArgument.id()).suggests(SUGGEST_PREDICATE),
                     â˜ƒ,
                     var0x -> checkCustomPredicate(var0x.getSource(), ResourceLocationArgument.getPredicate(var0x, "predicate"))
                  )
               )
         );

      for(DataCommands.DataProvider â˜ƒ : DataCommands.SOURCE_PROVIDERS) {
         â˜ƒ.then(
            â˜ƒ.wrap(
               Commands.literal("data"),
               var3 -> var3.then(
                     Commands.argument("path", NbtPathArgument.nbtPath())
                        .fork(â˜ƒ, var2x -> expect(var2x, â˜ƒ, checkMatchingData(â˜ƒ.access(var2x), NbtPathArgument.getPath(var2x, "path")) > 0))
                        .executes(createNumericConditionalHandler(â˜ƒ, var1x -> checkMatchingData(â˜ƒ.access(var1x), NbtPathArgument.getPath(var1x, "path"))))
                  )
            )
         );
      }

      return â˜ƒ;
   }

   private static Command<CommandSourceStack> createNumericConditionalHandler(boolean var0, ExecuteCommand.CommandNumericPredicate var1) {
      return â˜ƒ ? var1x -> {
         int â˜ƒ = â˜ƒ.test(var1x);
         if (â˜ƒ > 0) {
            var1x.getSource().sendSuccess(new TranslatableComponent("commands.execute.conditional.pass_count", â˜ƒ), false);
            return â˜ƒ;
         } else {
            throw ERROR_CONDITIONAL_FAILED.create();
         }
      } : var1x -> {
         int â˜ƒ = â˜ƒ.test(var1x);
         if (â˜ƒ == 0) {
            var1x.getSource().sendSuccess(new TranslatableComponent("commands.execute.conditional.pass"), false);
            return 1;
         } else {
            throw ERROR_CONDITIONAL_FAILED_COUNT.create(â˜ƒ);
         }
      };
   }

   private static int checkMatchingData(DataAccessor var0, NbtPathArgument.NbtPath var1) throws CommandSyntaxException {
      return â˜ƒ.countMatching(â˜ƒ.getData());
   }

   private static boolean checkScore(CommandContext<CommandSourceStack> var0, BiPredicate<Integer, Integer> var1) throws CommandSyntaxException {
      String â˜ƒ = ScoreHolderArgument.getName(â˜ƒ, "target");
      Objective â˜ƒx = ObjectiveArgument.getObjective(â˜ƒ, "targetObjective");
      String â˜ƒxx = ScoreHolderArgument.getName(â˜ƒ, "source");
      Objective â˜ƒxxx = ObjectiveArgument.getObjective(â˜ƒ, "sourceObjective");
      Scoreboard â˜ƒxxxx = â˜ƒ.getSource().getServer().getScoreboard();
      if (â˜ƒxxxx.hasPlayerScore(â˜ƒ, â˜ƒx) && â˜ƒxxxx.hasPlayerScore(â˜ƒxx, â˜ƒxxx)) {
         Score â˜ƒxxxxx = â˜ƒxxxx.getOrCreatePlayerScore(â˜ƒ, â˜ƒx);
         Score â˜ƒxxxxxx = â˜ƒxxxx.getOrCreatePlayerScore(â˜ƒxx, â˜ƒxxx);
         return â˜ƒ.test(â˜ƒxxxxx.getScore(), â˜ƒxxxxxx.getScore());
      } else {
         return false;
      }
   }

   private static boolean checkScore(CommandContext<CommandSourceStack> var0, MinMaxBounds.Ints var1) throws CommandSyntaxException {
      String â˜ƒ = ScoreHolderArgument.getName(â˜ƒ, "target");
      Objective â˜ƒx = ObjectiveArgument.getObjective(â˜ƒ, "targetObjective");
      Scoreboard â˜ƒxx = â˜ƒ.getSource().getServer().getScoreboard();
      return !â˜ƒxx.hasPlayerScore(â˜ƒ, â˜ƒx) ? false : â˜ƒ.matches(â˜ƒxx.getOrCreatePlayerScore(â˜ƒ, â˜ƒx).getScore());
   }

   private static boolean checkCustomPredicate(CommandSourceStack var0, LootItemCondition var1) {
      ServerLevel â˜ƒ = â˜ƒ.getLevel();
      LootContext.Builder â˜ƒx = new LootContext.Builder(â˜ƒ)
         .withParameter(LootContextParams.ORIGIN, â˜ƒ.getPosition())
         .withOptionalParameter(LootContextParams.THIS_ENTITY, â˜ƒ.getEntity());
      return â˜ƒ.test(â˜ƒx.create(LootContextParamSets.COMMAND));
   }

   private static Collection<CommandSourceStack> expect(CommandContext<CommandSourceStack> var0, boolean var1, boolean var2) {
      return (Collection<CommandSourceStack>)(â˜ƒ == â˜ƒ ? Collections.singleton(â˜ƒ.getSource()) : Collections.emptyList());
   }

   private static ArgumentBuilder<CommandSourceStack, ?> addConditional(
      CommandNode<CommandSourceStack> var0, ArgumentBuilder<CommandSourceStack, ?> var1, boolean var2, ExecuteCommand.CommandPredicate var3
   ) {
      return â˜ƒ.fork(â˜ƒ, var2x -> expect(var2x, â˜ƒ, â˜ƒ.test(var2x))).executes(var2x -> {
         if (â˜ƒ == â˜ƒ.test(var2x)) {
            ((CommandSourceStack)var2x.getSource()).sendSuccess(new TranslatableComponent("commands.execute.conditional.pass"), false);
            return 1;
         } else {
            throw ERROR_CONDITIONAL_FAILED.create();
         }
      });
   }

   private static ArgumentBuilder<CommandSourceStack, ?> addIfBlocksConditional(
      CommandNode<CommandSourceStack> var0, ArgumentBuilder<CommandSourceStack, ?> var1, boolean var2, boolean var3
   ) {
      return â˜ƒ.fork(â˜ƒ, var2x -> expect(var2x, â˜ƒ, checkRegions(var2x, â˜ƒ).isPresent()))
         .executes(â˜ƒ ? var1x -> checkIfRegions(var1x, â˜ƒ) : var1x -> checkUnlessRegions(var1x, â˜ƒ));
   }

   private static int checkIfRegions(CommandContext<CommandSourceStack> var0, boolean var1) throws CommandSyntaxException {
      OptionalInt â˜ƒ = checkRegions(â˜ƒ, â˜ƒ);
      if (â˜ƒ.isPresent()) {
         â˜ƒ.getSource().sendSuccess(new TranslatableComponent("commands.execute.conditional.pass_count", â˜ƒ.getAsInt()), false);
         return â˜ƒ.getAsInt();
      } else {
         throw ERROR_CONDITIONAL_FAILED.create();
      }
   }

   private static int checkUnlessRegions(CommandContext<CommandSourceStack> var0, boolean var1) throws CommandSyntaxException {
      OptionalInt â˜ƒ = checkRegions(â˜ƒ, â˜ƒ);
      if (â˜ƒ.isPresent()) {
         throw ERROR_CONDITIONAL_FAILED_COUNT.create(â˜ƒ.getAsInt());
      } else {
         â˜ƒ.getSource().sendSuccess(new TranslatableComponent("commands.execute.conditional.pass"), false);
         return 1;
      }
   }

   private static OptionalInt checkRegions(CommandContext<CommandSourceStack> var0, boolean var1) throws CommandSyntaxException {
      return checkRegions(
         â˜ƒ.getSource().getLevel(),
         BlockPosArgument.getLoadedBlockPos(â˜ƒ, "start"),
         BlockPosArgument.getLoadedBlockPos(â˜ƒ, "end"),
         BlockPosArgument.getLoadedBlockPos(â˜ƒ, "destination"),
         â˜ƒ
      );
   }

   private static OptionalInt checkRegions(ServerLevel var0, BlockPos var1, BlockPos var2, BlockPos var3, boolean var4) throws CommandSyntaxException {
      BoundingBox â˜ƒ = BoundingBox.fromCorners(â˜ƒ, â˜ƒ);
      BoundingBox â˜ƒx = BoundingBox.fromCorners(â˜ƒ, â˜ƒ.offset(â˜ƒ.getLength()));
      BlockPos â˜ƒxx = new BlockPos(â˜ƒx.minX() - â˜ƒ.minX(), â˜ƒx.minY() - â˜ƒ.minY(), â˜ƒx.minZ() - â˜ƒ.minZ());
      int â˜ƒxxx = â˜ƒ.getXSpan() * â˜ƒ.getYSpan() * â˜ƒ.getZSpan();
      if (â˜ƒxxx > 32768) {
         throw ERROR_AREA_TOO_LARGE.create(32768, â˜ƒxxx);
      } else {
         int â˜ƒ = 0;

         for(int â˜ƒx = â˜ƒ.minZ(); â˜ƒx <= â˜ƒ.maxZ(); ++â˜ƒx) {
            for(int â˜ƒxx = â˜ƒ.minY(); â˜ƒxx <= â˜ƒ.maxY(); ++â˜ƒxx) {
               for(int â˜ƒxxx = â˜ƒ.minX(); â˜ƒxxx <= â˜ƒ.maxX(); ++â˜ƒxxx) {
                  BlockPos â˜ƒxxxx = new BlockPos(â˜ƒxxx, â˜ƒxx, â˜ƒx);
                  BlockPos â˜ƒxxxxx = â˜ƒxxxx.offset(â˜ƒxx);
                  BlockState â˜ƒxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxx);
                  if (!â˜ƒ || !â˜ƒxxxxxx.is(Blocks.AIR)) {
                     if (â˜ƒxxxxxx != â˜ƒ.getBlockState(â˜ƒxxxxx)) {
                        return OptionalInt.empty();
                     }

                     BlockEntity â˜ƒxxxxxxx = â˜ƒ.getBlockEntity(â˜ƒxxxx);
                     BlockEntity â˜ƒxxxxxxxx = â˜ƒ.getBlockEntity(â˜ƒxxxxx);
                     if (â˜ƒxxxxxxx != null) {
                        if (â˜ƒxxxxxxxx == null) {
                           return OptionalInt.empty();
                        }

                        CompoundTag â˜ƒxxxxxxxxx = â˜ƒxxxxxxx.save(new CompoundTag());
                        â˜ƒxxxxxxxxx.remove("x");
                        â˜ƒxxxxxxxxx.remove("y");
                        â˜ƒxxxxxxxxx.remove("z");
                        CompoundTag â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxx.save(new CompoundTag());
                        â˜ƒxxxxxxxxxx.remove("x");
                        â˜ƒxxxxxxxxxx.remove("y");
                        â˜ƒxxxxxxxxxx.remove("z");
                        if (!â˜ƒxxxxxxxxx.equals(â˜ƒxxxxxxxxxx)) {
                           return OptionalInt.empty();
                        }
                     }

                     ++â˜ƒ;
                  }
               }
            }
         }

         return OptionalInt.of(â˜ƒ);
      }
   }

   @FunctionalInterface
   interface CommandNumericPredicate {
      int test(CommandContext<CommandSourceStack> var1) throws CommandSyntaxException;
   }

   @FunctionalInterface
   interface CommandPredicate {
      boolean test(CommandContext<CommandSourceStack> var1) throws CommandSyntaxException;
   }
}
