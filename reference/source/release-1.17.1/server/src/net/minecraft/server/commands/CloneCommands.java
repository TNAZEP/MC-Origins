package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Deque;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.blocks.BlockPredicateArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Clearable;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class CloneCommands {
   private static final int MAX_CLONE_AREA = 32768;
   private static final SimpleCommandExceptionType ERROR_OVERLAP = new SimpleCommandExceptionType(new TranslatableComponent("commands.clone.overlap"));
   private static final Dynamic2CommandExceptionType ERROR_AREA_TOO_LARGE = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TranslatableComponent("commands.clone.toobig", var0, var1)
   );
   private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType(new TranslatableComponent("commands.clone.failed"));
   public static final Predicate<BlockInWorld> FILTER_AIR = var0 -> !var0.getState().isAir();

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("clone")
            .requires(var0x -> var0x.hasPermission(2))
            .then(
               Commands.argument("begin", BlockPosArgument.blockPos())
                  .then(
                     Commands.argument("end", BlockPosArgument.blockPos())
                        .then(
                           Commands.argument("destination", BlockPosArgument.blockPos())
                              .executes(
                                 var0x -> clone(
                                       var0x.getSource(),
                                       BlockPosArgument.getLoadedBlockPos(var0x, "begin"),
                                       BlockPosArgument.getLoadedBlockPos(var0x, "end"),
                                       BlockPosArgument.getLoadedBlockPos(var0x, "destination"),
                                       var0xx -> true,
                                       CloneCommands.Mode.NORMAL
                                    )
                              )
                              .then(
                                 Commands.literal("replace")
                                    .executes(
                                       var0x -> clone(
                                             var0x.getSource(),
                                             BlockPosArgument.getLoadedBlockPos(var0x, "begin"),
                                             BlockPosArgument.getLoadedBlockPos(var0x, "end"),
                                             BlockPosArgument.getLoadedBlockPos(var0x, "destination"),
                                             var0xx -> true,
                                             CloneCommands.Mode.NORMAL
                                          )
                                    )
                                    .then(
                                       Commands.literal("force")
                                          .executes(
                                             var0x -> clone(
                                                   var0x.getSource(),
                                                   BlockPosArgument.getLoadedBlockPos(var0x, "begin"),
                                                   BlockPosArgument.getLoadedBlockPos(var0x, "end"),
                                                   BlockPosArgument.getLoadedBlockPos(var0x, "destination"),
                                                   var0xx -> true,
                                                   CloneCommands.Mode.FORCE
                                                )
                                          )
                                    )
                                    .then(
                                       Commands.literal("move")
                                          .executes(
                                             var0x -> clone(
                                                   var0x.getSource(),
                                                   BlockPosArgument.getLoadedBlockPos(var0x, "begin"),
                                                   BlockPosArgument.getLoadedBlockPos(var0x, "end"),
                                                   BlockPosArgument.getLoadedBlockPos(var0x, "destination"),
                                                   var0xx -> true,
                                                   CloneCommands.Mode.MOVE
                                                )
                                          )
                                    )
                                    .then(
                                       Commands.literal("normal")
                                          .executes(
                                             var0x -> clone(
                                                   var0x.getSource(),
                                                   BlockPosArgument.getLoadedBlockPos(var0x, "begin"),
                                                   BlockPosArgument.getLoadedBlockPos(var0x, "end"),
                                                   BlockPosArgument.getLoadedBlockPos(var0x, "destination"),
                                                   var0xx -> true,
                                                   CloneCommands.Mode.NORMAL
                                                )
                                          )
                                    )
                              )
                              .then(
                                 Commands.literal("masked")
                                    .executes(
                                       var0x -> clone(
                                             var0x.getSource(),
                                             BlockPosArgument.getLoadedBlockPos(var0x, "begin"),
                                             BlockPosArgument.getLoadedBlockPos(var0x, "end"),
                                             BlockPosArgument.getLoadedBlockPos(var0x, "destination"),
                                             FILTER_AIR,
                                             CloneCommands.Mode.NORMAL
                                          )
                                    )
                                    .then(
                                       Commands.literal("force")
                                          .executes(
                                             var0x -> clone(
                                                   var0x.getSource(),
                                                   BlockPosArgument.getLoadedBlockPos(var0x, "begin"),
                                                   BlockPosArgument.getLoadedBlockPos(var0x, "end"),
                                                   BlockPosArgument.getLoadedBlockPos(var0x, "destination"),
                                                   FILTER_AIR,
                                                   CloneCommands.Mode.FORCE
                                                )
                                          )
                                    )
                                    .then(
                                       Commands.literal("move")
                                          .executes(
                                             var0x -> clone(
                                                   var0x.getSource(),
                                                   BlockPosArgument.getLoadedBlockPos(var0x, "begin"),
                                                   BlockPosArgument.getLoadedBlockPos(var0x, "end"),
                                                   BlockPosArgument.getLoadedBlockPos(var0x, "destination"),
                                                   FILTER_AIR,
                                                   CloneCommands.Mode.MOVE
                                                )
                                          )
                                    )
                                    .then(
                                       Commands.literal("normal")
                                          .executes(
                                             var0x -> clone(
                                                   var0x.getSource(),
                                                   BlockPosArgument.getLoadedBlockPos(var0x, "begin"),
                                                   BlockPosArgument.getLoadedBlockPos(var0x, "end"),
                                                   BlockPosArgument.getLoadedBlockPos(var0x, "destination"),
                                                   FILTER_AIR,
                                                   CloneCommands.Mode.NORMAL
                                                )
                                          )
                                    )
                              )
                              .then(
                                 Commands.literal("filtered")
                                    .then(
                                       Commands.argument("filter", BlockPredicateArgument.blockPredicate())
                                          .executes(
                                             var0x -> clone(
                                                   var0x.getSource(),
                                                   BlockPosArgument.getLoadedBlockPos(var0x, "begin"),
                                                   BlockPosArgument.getLoadedBlockPos(var0x, "end"),
                                                   BlockPosArgument.getLoadedBlockPos(var0x, "destination"),
                                                   BlockPredicateArgument.getBlockPredicate(var0x, "filter"),
                                                   CloneCommands.Mode.NORMAL
                                                )
                                          )
                                          .then(
                                             Commands.literal("force")
                                                .executes(
                                                   var0x -> clone(
                                                         var0x.getSource(),
                                                         BlockPosArgument.getLoadedBlockPos(var0x, "begin"),
                                                         BlockPosArgument.getLoadedBlockPos(var0x, "end"),
                                                         BlockPosArgument.getLoadedBlockPos(var0x, "destination"),
                                                         BlockPredicateArgument.getBlockPredicate(var0x, "filter"),
                                                         CloneCommands.Mode.FORCE
                                                      )
                                                )
                                          )
                                          .then(
                                             Commands.literal("move")
                                                .executes(
                                                   var0x -> clone(
                                                         var0x.getSource(),
                                                         BlockPosArgument.getLoadedBlockPos(var0x, "begin"),
                                                         BlockPosArgument.getLoadedBlockPos(var0x, "end"),
                                                         BlockPosArgument.getLoadedBlockPos(var0x, "destination"),
                                                         BlockPredicateArgument.getBlockPredicate(var0x, "filter"),
                                                         CloneCommands.Mode.MOVE
                                                      )
                                                )
                                          )
                                          .then(
                                             Commands.literal("normal")
                                                .executes(
                                                   var0x -> clone(
                                                         var0x.getSource(),
                                                         BlockPosArgument.getLoadedBlockPos(var0x, "begin"),
                                                         BlockPosArgument.getLoadedBlockPos(var0x, "end"),
                                                         BlockPosArgument.getLoadedBlockPos(var0x, "destination"),
                                                         BlockPredicateArgument.getBlockPredicate(var0x, "filter"),
                                                         CloneCommands.Mode.NORMAL
                                                      )
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int clone(CommandSourceStack var0, BlockPos var1, BlockPos var2, BlockPos var3, Predicate<BlockInWorld> var4, CloneCommands.Mode var5) throws CommandSyntaxException {
      BoundingBox â˜ƒ = BoundingBox.fromCorners(â˜ƒ, â˜ƒ);
      BlockPos â˜ƒx = â˜ƒ.offset(â˜ƒ.getLength());
      BoundingBox â˜ƒxx = BoundingBox.fromCorners(â˜ƒ, â˜ƒx);
      if (!â˜ƒ.canOverlap() && â˜ƒxx.intersects(â˜ƒ)) {
         throw ERROR_OVERLAP.create();
      } else {
         int â˜ƒ = â˜ƒ.getXSpan() * â˜ƒ.getYSpan() * â˜ƒ.getZSpan();
         if (â˜ƒ > 32768) {
            throw ERROR_AREA_TOO_LARGE.create(32768, â˜ƒ);
         } else {
            ServerLevel â˜ƒ = â˜ƒ.getLevel();
            if (â˜ƒ.hasChunksAt(â˜ƒ, â˜ƒ) && â˜ƒ.hasChunksAt(â˜ƒ, â˜ƒx)) {
               List<CloneCommands.CloneBlockInfo> â˜ƒx = Lists.<CloneCommands.CloneBlockInfo>newArrayList();
               List<CloneCommands.CloneBlockInfo> â˜ƒxx = Lists.<CloneCommands.CloneBlockInfo>newArrayList();
               List<CloneCommands.CloneBlockInfo> â˜ƒxxx = Lists.<CloneCommands.CloneBlockInfo>newArrayList();
               Deque<BlockPos> â˜ƒxxxx = Lists.<BlockPos>newLinkedList();
               BlockPos â˜ƒxxxxx = new BlockPos(â˜ƒxx.minX() - â˜ƒ.minX(), â˜ƒxx.minY() - â˜ƒ.minY(), â˜ƒxx.minZ() - â˜ƒ.minZ());

               for(int â˜ƒxxxxxx = â˜ƒ.minZ(); â˜ƒxxxxxx <= â˜ƒ.maxZ(); ++â˜ƒxxxxxx) {
                  for(int â˜ƒxxxxxxx = â˜ƒ.minY(); â˜ƒxxxxxxx <= â˜ƒ.maxY(); ++â˜ƒxxxxxxx) {
                     for(int â˜ƒxxxxxxxx = â˜ƒ.minX(); â˜ƒxxxxxxxx <= â˜ƒ.maxX(); ++â˜ƒxxxxxxxx) {
                        BlockPos â˜ƒxxxxxxxxx = new BlockPos(â˜ƒxxxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxx);
                        BlockPos â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxx.offset(â˜ƒxxxxx);
                        BlockInWorld â˜ƒxxxxxxxxxxx = new BlockInWorld(â˜ƒ, â˜ƒxxxxxxxxx, false);
                        BlockState â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx.getState();
                        if (â˜ƒ.test(â˜ƒxxxxxxxxxxx)) {
                           BlockEntity â˜ƒxxxxxxxxxxxxx = â˜ƒ.getBlockEntity(â˜ƒxxxxxxxxx);
                           if (â˜ƒxxxxxxxxxxxxx != null) {
                              CompoundTag â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxx.save(new CompoundTag());
                              â˜ƒxx.add(new CloneCommands.CloneBlockInfo(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx));
                              â˜ƒxxxx.addLast(â˜ƒxxxxxxxxx);
                           } else if (!â˜ƒxxxxxxxxxxxx.isSolidRender(â˜ƒ, â˜ƒxxxxxxxxx) && !â˜ƒxxxxxxxxxxxx.isCollisionShapeFullBlock(â˜ƒ, â˜ƒxxxxxxxxx)) {
                              â˜ƒxxx.add(new CloneCommands.CloneBlockInfo(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxxx, null));
                              â˜ƒxxxx.addFirst(â˜ƒxxxxxxxxx);
                           } else {
                              â˜ƒx.add(new CloneCommands.CloneBlockInfo(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxxx, null));
                              â˜ƒxxxx.addLast(â˜ƒxxxxxxxxx);
                           }
                        }
                     }
                  }
               }

               if (â˜ƒ == CloneCommands.Mode.MOVE) {
                  for(BlockPos â˜ƒxxxxxx : â˜ƒxxxx) {
                     BlockEntity â˜ƒxxxxxxx = â˜ƒ.getBlockEntity(â˜ƒxxxxxx);
                     Clearable.tryClear(â˜ƒxxxxxxx);
                     â˜ƒ.setBlock(â˜ƒxxxxxx, Blocks.BARRIER.defaultBlockState(), 2);
                  }

                  for(BlockPos â˜ƒxxxxxx : â˜ƒxxxx) {
                     â˜ƒ.setBlock(â˜ƒxxxxxx, Blocks.AIR.defaultBlockState(), 3);
                  }
               }

               List<CloneCommands.CloneBlockInfo> â˜ƒxxxxxx = Lists.<CloneCommands.CloneBlockInfo>newArrayList();
               â˜ƒxxxxxx.addAll(â˜ƒx);
               â˜ƒxxxxxx.addAll(â˜ƒxx);
               â˜ƒxxxxxx.addAll(â˜ƒxxx);
               List<CloneCommands.CloneBlockInfo> â˜ƒxxxxxxx = Lists.reverse(â˜ƒxxxxxx);

               for(CloneCommands.CloneBlockInfo â˜ƒxxxxxxxx : â˜ƒxxxxxxx) {
                  BlockEntity â˜ƒxxxxxxxxx = â˜ƒ.getBlockEntity(â˜ƒxxxxxxxx.pos);
                  Clearable.tryClear(â˜ƒxxxxxxxxx);
                  â˜ƒ.setBlock(â˜ƒxxxxxxxx.pos, Blocks.BARRIER.defaultBlockState(), 2);
               }

               int â˜ƒxxxxxxxx = 0;

               for(CloneCommands.CloneBlockInfo â˜ƒxxxxxxxxx : â˜ƒxxxxxx) {
                  if (â˜ƒ.setBlock(â˜ƒxxxxxxxxx.pos, â˜ƒxxxxxxxxx.state, 2)) {
                     ++â˜ƒxxxxxxxx;
                  }
               }

               for(CloneCommands.CloneBlockInfo â˜ƒxxxxxxxxx : â˜ƒxx) {
                  BlockEntity â˜ƒxxxxxxxxxx = â˜ƒ.getBlockEntity(â˜ƒxxxxxxxxx.pos);
                  if (â˜ƒxxxxxxxxx.tag != null && â˜ƒxxxxxxxxxx != null) {
                     â˜ƒxxxxxxxxx.tag.putInt("x", â˜ƒxxxxxxxxx.pos.getX());
                     â˜ƒxxxxxxxxx.tag.putInt("y", â˜ƒxxxxxxxxx.pos.getY());
                     â˜ƒxxxxxxxxx.tag.putInt("z", â˜ƒxxxxxxxxx.pos.getZ());
                     â˜ƒxxxxxxxxxx.load(â˜ƒxxxxxxxxx.tag);
                     â˜ƒxxxxxxxxxx.setChanged();
                  }

                  â˜ƒ.setBlock(â˜ƒxxxxxxxxx.pos, â˜ƒxxxxxxxxx.state, 2);
               }

               for(CloneCommands.CloneBlockInfo â˜ƒxxxxxxxxx : â˜ƒxxxxxxx) {
                  â˜ƒ.blockUpdated(â˜ƒxxxxxxxxx.pos, â˜ƒxxxxxxxxx.state.getBlock());
               }

               â˜ƒ.getBlockTicks().copy(â˜ƒ, â˜ƒxxxxx);
               if (â˜ƒxxxxxxxx == 0) {
                  throw ERROR_FAILED.create();
               } else {
                  â˜ƒ.sendSuccess(new TranslatableComponent("commands.clone.success", â˜ƒxxxxxxxx), true);
                  return â˜ƒxxxxxxxx;
               }
            } else {
               throw BlockPosArgument.ERROR_NOT_LOADED.create();
            }
         }
      }
   }

   static class CloneBlockInfo {
      public final BlockPos pos;
      public final BlockState state;
      @Nullable
      public final CompoundTag tag;

      public CloneBlockInfo(BlockPos var1, BlockState var2, @Nullable CompoundTag var3) {
         this.pos = â˜ƒ;
         this.state = â˜ƒ;
         this.tag = â˜ƒ;
      }
   }

   static enum Mode {
      FORCE(true),
      MOVE(true),
      NORMAL(false);

      private final boolean canOverlap;

      private Mode(boolean var3) {
         this.canOverlap = â˜ƒ;
      }

      public boolean canOverlap() {
         return this.canOverlap;
      }
   }
}
