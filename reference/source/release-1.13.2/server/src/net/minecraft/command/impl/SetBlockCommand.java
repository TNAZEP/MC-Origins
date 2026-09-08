package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.BlockStateArgument;
import net.minecraft.command.arguments.BlockStateInput;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.WorldServer;

public class SetBlockCommand {
   private static final SimpleCommandExceptionType field_198689_a = new SimpleCommandExceptionType(new TextComponentTranslation("commands.setblock.failed"));

   public static void func_198684_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("setblock")
            .requires(var0x -> var0x.func_197034_c(2))
            .then(
               Commands.func_197056_a("pos", BlockPosArgument.func_197276_a())
                  .then(
                     Commands.func_197056_a("block", BlockStateArgument.func_197239_a())
                        .executes(
                           var0x -> func_198683_a(
                                 var0x.getSource(),
                                 BlockPosArgument.func_197273_a(var0x, "pos"),
                                 BlockStateArgument.func_197238_a(var0x, "block"),
                                 SetBlockCommand.Mode.REPLACE,
                                 null
                              )
                        )
                        .then(
                           Commands.func_197057_a("destroy")
                              .executes(
                                 var0x -> func_198683_a(
                                       var0x.getSource(),
                                       BlockPosArgument.func_197273_a(var0x, "pos"),
                                       BlockStateArgument.func_197238_a(var0x, "block"),
                                       SetBlockCommand.Mode.DESTROY,
                                       null
                                    )
                              )
                        )
                        .then(
                           Commands.func_197057_a("keep")
                              .executes(
                                 var0x -> func_198683_a(
                                       var0x.getSource(),
                                       BlockPosArgument.func_197273_a(var0x, "pos"),
                                       BlockStateArgument.func_197238_a(var0x, "block"),
                                       SetBlockCommand.Mode.REPLACE,
                                       var0xx -> var0xx.func_196960_c().func_175623_d(var0xx.func_177508_d())
                                    )
                              )
                        )
                        .then(
                           Commands.func_197057_a("replace")
                              .executes(
                                 var0x -> func_198683_a(
                                       var0x.getSource(),
                                       BlockPosArgument.func_197273_a(var0x, "pos"),
                                       BlockStateArgument.func_197238_a(var0x, "block"),
                                       SetBlockCommand.Mode.REPLACE,
                                       null
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int func_198683_a(
      CommandSource var0, BlockPos var1, BlockStateInput var2, SetBlockCommand.Mode var3, @Nullable Predicate<BlockWorldState> var4
   ) throws CommandSyntaxException {
      WorldServer ☃ = ☃.func_197023_e();
      if (☃ != null && !☃.test(new BlockWorldState(☃, ☃, true))) {
         throw field_198689_a.create();
      } else {
         boolean ☃;
         if (☃ == SetBlockCommand.Mode.DESTROY) {
            ☃.func_175655_b(☃, true);
            ☃ = !☃.func_197231_a().func_196958_f();
         } else {
            TileEntity ☃ = ☃.func_175625_s(☃);
            if (☃ instanceof IInventory) {
               ((IInventory)☃).func_174888_l();
            }

            ☃ = true;
         }

         if (☃ && !☃.func_197230_a(☃, ☃, 2)) {
            throw field_198689_a.create();
         } else {
            ☃.func_195592_c(☃, ☃.func_197231_a().func_177230_c());
            ☃.func_197030_a(new TextComponentTranslation("commands.setblock.success", ☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p()), true);
            return 1;
         }
      }
   }

   public interface IFilter {
      @Nullable
      BlockStateInput filter(MutableBoundingBox var1, BlockPos var2, BlockStateInput var3, WorldServer var4);
   }

   public static enum Mode {
      REPLACE,
      OUTLINE,
      HOLLOW,
      DESTROY;
   }
}
