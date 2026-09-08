package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.commands.arguments.blocks.BlockStateArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Clearable;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class SetBlockCommand {
   private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType(new TranslatableComponent("commands.setblock.failed"));

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("setblock")
            .requires(var0x -> var0x.hasPermission(2))
            .then(
               Commands.argument("pos", BlockPosArgument.blockPos())
                  .then(
                     Commands.argument("block", BlockStateArgument.block())
                        .executes(
                           var0x -> setBlock(
                                 var0x.getSource(),
                                 BlockPosArgument.getLoadedBlockPos(var0x, "pos"),
                                 BlockStateArgument.getBlock(var0x, "block"),
                                 SetBlockCommand.Mode.REPLACE,
                                 null
                              )
                        )
                        .then(
                           Commands.literal("destroy")
                              .executes(
                                 var0x -> setBlock(
                                       var0x.getSource(),
                                       BlockPosArgument.getLoadedBlockPos(var0x, "pos"),
                                       BlockStateArgument.getBlock(var0x, "block"),
                                       SetBlockCommand.Mode.DESTROY,
                                       null
                                    )
                              )
                        )
                        .then(
                           Commands.literal("keep")
                              .executes(
                                 var0x -> setBlock(
                                       var0x.getSource(),
                                       BlockPosArgument.getLoadedBlockPos(var0x, "pos"),
                                       BlockStateArgument.getBlock(var0x, "block"),
                                       SetBlockCommand.Mode.REPLACE,
                                       var0xx -> var0xx.getLevel().isEmptyBlock(var0xx.getPos())
                                    )
                              )
                        )
                        .then(
                           Commands.literal("replace")
                              .executes(
                                 var0x -> setBlock(
                                       var0x.getSource(),
                                       BlockPosArgument.getLoadedBlockPos(var0x, "pos"),
                                       BlockStateArgument.getBlock(var0x, "block"),
                                       SetBlockCommand.Mode.REPLACE,
                                       null
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int setBlock(CommandSourceStack var0, BlockPos var1, BlockInput var2, SetBlockCommand.Mode var3, @Nullable Predicate<BlockInWorld> var4) throws CommandSyntaxException {
      ServerLevel â˜ƒ = â˜ƒ.getLevel();
      if (â˜ƒ != null && !â˜ƒ.test(new BlockInWorld(â˜ƒ, â˜ƒ, true))) {
         throw ERROR_FAILED.create();
      } else {
         boolean â˜ƒ;
         if (â˜ƒ == SetBlockCommand.Mode.DESTROY) {
            â˜ƒ.destroyBlock(â˜ƒ, true);
            â˜ƒ = !â˜ƒ.getState().isAir() || !â˜ƒ.getBlockState(â˜ƒ).isAir();
         } else {
            BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
            Clearable.tryClear(â˜ƒ);
            â˜ƒ = true;
         }

         if (â˜ƒ && !â˜ƒ.place(â˜ƒ, â˜ƒ, 2)) {
            throw ERROR_FAILED.create();
         } else {
            â˜ƒ.blockUpdated(â˜ƒ, â˜ƒ.getState().getBlock());
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.setblock.success", â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ()), true);
            return 1;
         }
      }
   }

   public interface Filter {
      @Nullable
      BlockInput filter(BoundingBox var1, BlockPos var2, BlockInput var3, ServerLevel var4);
   }

   public static enum Mode {
      REPLACE,
      DESTROY;
   }
}
