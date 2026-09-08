package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.commands.arguments.blocks.BlockPredicateArgument;
import net.minecraft.commands.arguments.blocks.BlockStateArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Clearable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class FillCommand {
   private static final int MAX_FILL_AREA = 32768;
   private static final Dynamic2CommandExceptionType ERROR_AREA_TOO_LARGE = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TranslatableComponent("commands.fill.toobig", var0, var1)
   );
   static final BlockInput HOLLOW_CORE = new BlockInput(Blocks.AIR.defaultBlockState(), Collections.emptySet(), null);
   private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType(new TranslatableComponent("commands.fill.failed"));

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("fill")
            .requires(var0x -> var0x.hasPermission(2))
            .then(
               Commands.argument("from", BlockPosArgument.blockPos())
                  .then(
                     Commands.argument("to", BlockPosArgument.blockPos())
                        .then(
                           Commands.argument("block", BlockStateArgument.block())
                              .executes(
                                 var0x -> fillBlocks(
                                       var0x.getSource(),
                                       BoundingBox.fromCorners(
                                          BlockPosArgument.getLoadedBlockPos(var0x, "from"), BlockPosArgument.getLoadedBlockPos(var0x, "to")
                                       ),
                                       BlockStateArgument.getBlock(var0x, "block"),
                                       FillCommand.Mode.REPLACE,
                                       null
                                    )
                              )
                              .then(
                                 Commands.literal("replace")
                                    .executes(
                                       var0x -> fillBlocks(
                                             var0x.getSource(),
                                             BoundingBox.fromCorners(
                                                BlockPosArgument.getLoadedBlockPos(var0x, "from"), BlockPosArgument.getLoadedBlockPos(var0x, "to")
                                             ),
                                             BlockStateArgument.getBlock(var0x, "block"),
                                             FillCommand.Mode.REPLACE,
                                             null
                                          )
                                    )
                                    .then(
                                       Commands.argument("filter", BlockPredicateArgument.blockPredicate())
                                          .executes(
                                             var0x -> fillBlocks(
                                                   var0x.getSource(),
                                                   BoundingBox.fromCorners(
                                                      BlockPosArgument.getLoadedBlockPos(var0x, "from"), BlockPosArgument.getLoadedBlockPos(var0x, "to")
                                                   ),
                                                   BlockStateArgument.getBlock(var0x, "block"),
                                                   FillCommand.Mode.REPLACE,
                                                   BlockPredicateArgument.getBlockPredicate(var0x, "filter")
                                                )
                                          )
                                    )
                              )
                              .then(
                                 Commands.literal("keep")
                                    .executes(
                                       var0x -> fillBlocks(
                                             var0x.getSource(),
                                             BoundingBox.fromCorners(
                                                BlockPosArgument.getLoadedBlockPos(var0x, "from"), BlockPosArgument.getLoadedBlockPos(var0x, "to")
                                             ),
                                             BlockStateArgument.getBlock(var0x, "block"),
                                             FillCommand.Mode.REPLACE,
                                             var0xx -> var0xx.getLevel().isEmptyBlock(var0xx.getPos())
                                          )
                                    )
                              )
                              .then(
                                 Commands.literal("outline")
                                    .executes(
                                       var0x -> fillBlocks(
                                             var0x.getSource(),
                                             BoundingBox.fromCorners(
                                                BlockPosArgument.getLoadedBlockPos(var0x, "from"), BlockPosArgument.getLoadedBlockPos(var0x, "to")
                                             ),
                                             BlockStateArgument.getBlock(var0x, "block"),
                                             FillCommand.Mode.OUTLINE,
                                             null
                                          )
                                    )
                              )
                              .then(
                                 Commands.literal("hollow")
                                    .executes(
                                       var0x -> fillBlocks(
                                             var0x.getSource(),
                                             BoundingBox.fromCorners(
                                                BlockPosArgument.getLoadedBlockPos(var0x, "from"), BlockPosArgument.getLoadedBlockPos(var0x, "to")
                                             ),
                                             BlockStateArgument.getBlock(var0x, "block"),
                                             FillCommand.Mode.HOLLOW,
                                             null
                                          )
                                    )
                              )
                              .then(
                                 Commands.literal("destroy")
                                    .executes(
                                       var0x -> fillBlocks(
                                             var0x.getSource(),
                                             BoundingBox.fromCorners(
                                                BlockPosArgument.getLoadedBlockPos(var0x, "from"), BlockPosArgument.getLoadedBlockPos(var0x, "to")
                                             ),
                                             BlockStateArgument.getBlock(var0x, "block"),
                                             FillCommand.Mode.DESTROY,
                                             null
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int fillBlocks(CommandSourceStack var0, BoundingBox var1, BlockInput var2, FillCommand.Mode var3, @Nullable Predicate<BlockInWorld> var4) throws CommandSyntaxException {
      int â˜ƒ = â˜ƒ.getXSpan() * â˜ƒ.getYSpan() * â˜ƒ.getZSpan();
      if (â˜ƒ > 32768) {
         throw ERROR_AREA_TOO_LARGE.create(32768, â˜ƒ);
      } else {
         List<BlockPos> â˜ƒ = Lists.<BlockPos>newArrayList();
         ServerLevel â˜ƒx = â˜ƒ.getLevel();
         int â˜ƒxx = 0;

         for(BlockPos â˜ƒxxx : BlockPos.betweenClosed(â˜ƒ.minX(), â˜ƒ.minY(), â˜ƒ.minZ(), â˜ƒ.maxX(), â˜ƒ.maxY(), â˜ƒ.maxZ())) {
            if (â˜ƒ == null || â˜ƒ.test(new BlockInWorld(â˜ƒx, â˜ƒxxx, true))) {
               BlockInput â˜ƒxxxx = â˜ƒ.filter.filter(â˜ƒ, â˜ƒxxx, â˜ƒ, â˜ƒx);
               if (â˜ƒxxxx != null) {
                  BlockEntity â˜ƒxxxxx = â˜ƒx.getBlockEntity(â˜ƒxxx);
                  Clearable.tryClear(â˜ƒxxxxx);
                  if (â˜ƒxxxx.place(â˜ƒx, â˜ƒxxx, 2)) {
                     â˜ƒ.add(â˜ƒxxx.immutable());
                     ++â˜ƒxx;
                  }
               }
            }
         }

         for(BlockPos â˜ƒxxx : â˜ƒ) {
            Block â˜ƒxxxx = â˜ƒx.getBlockState(â˜ƒxxx).getBlock();
            â˜ƒx.blockUpdated(â˜ƒxxx, â˜ƒxxxx);
         }

         if (â˜ƒxx == 0) {
            throw ERROR_FAILED.create();
         } else {
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.fill.success", â˜ƒxx), true);
            return â˜ƒxx;
         }
      }
   }

   static enum Mode {
      REPLACE((var0, var1, var2, var3) -> var2),
      OUTLINE(
         (var0, var1, var2, var3) -> var1.getX() != var0.minX()
                  && var1.getX() != var0.maxX()
                  && var1.getY() != var0.minY()
                  && var1.getY() != var0.maxY()
                  && var1.getZ() != var0.minZ()
                  && var1.getZ() != var0.maxZ()
               ? null
               : var2
      ),
      HOLLOW(
         (var0, var1, var2, var3) -> var1.getX() != var0.minX()
                  && var1.getX() != var0.maxX()
                  && var1.getY() != var0.minY()
                  && var1.getY() != var0.maxY()
                  && var1.getZ() != var0.minZ()
                  && var1.getZ() != var0.maxZ()
               ? FillCommand.HOLLOW_CORE
               : var2
      ),
      DESTROY((var0, var1, var2, var3) -> {
         var3.destroyBlock(var1, true);
         return var2;
      });

      public final SetBlockCommand.Filter filter;

      private Mode(SetBlockCommand.Filter var3) {
         this.filter = â˜ƒ;
      }
   }
}
