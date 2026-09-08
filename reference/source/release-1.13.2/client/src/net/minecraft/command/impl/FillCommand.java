package net.minecraft.command.impl;

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
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.BlockPredicateArgument;
import net.minecraft.command.arguments.BlockStateArgument;
import net.minecraft.command.arguments.BlockStateInput;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.WorldServer;

public class FillCommand {
   private static final Dynamic2CommandExceptionType field_198473_a = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TextComponentTranslation("commands.fill.toobig", var0, var1)
   );
   private static final BlockStateInput field_198474_b = new BlockStateInput(Blocks.field_150350_a.func_176223_P(), Collections.emptySet(), null);
   private static final SimpleCommandExceptionType field_198475_c = new SimpleCommandExceptionType(new TextComponentTranslation("commands.fill.failed"));

   public static void func_198465_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("fill")
            .requires(var0x -> var0x.func_197034_c(2))
            .then(
               Commands.func_197056_a("from", BlockPosArgument.func_197276_a())
                  .then(
                     Commands.func_197056_a("to", BlockPosArgument.func_197276_a())
                        .then(
                           Commands.func_197056_a("block", BlockStateArgument.func_197239_a())
                              .executes(
                                 var0x -> func_198463_a(
                                       var0x.getSource(),
                                       new MutableBoundingBox(BlockPosArgument.func_197273_a(var0x, "from"), BlockPosArgument.func_197273_a(var0x, "to")),
                                       BlockStateArgument.func_197238_a(var0x, "block"),
                                       FillCommand.Mode.REPLACE,
                                       null
                                    )
                              )
                              .then(
                                 Commands.func_197057_a("replace")
                                    .executes(
                                       var0x -> func_198463_a(
                                             var0x.getSource(),
                                             new MutableBoundingBox(BlockPosArgument.func_197273_a(var0x, "from"), BlockPosArgument.func_197273_a(var0x, "to")),
                                             BlockStateArgument.func_197238_a(var0x, "block"),
                                             FillCommand.Mode.REPLACE,
                                             null
                                          )
                                    )
                                    .then(
                                       Commands.func_197056_a("filter", BlockPredicateArgument.func_199824_a())
                                          .executes(
                                             var0x -> func_198463_a(
                                                   var0x.getSource(),
                                                   new MutableBoundingBox(
                                                      BlockPosArgument.func_197273_a(var0x, "from"), BlockPosArgument.func_197273_a(var0x, "to")
                                                   ),
                                                   BlockStateArgument.func_197238_a(var0x, "block"),
                                                   FillCommand.Mode.REPLACE,
                                                   BlockPredicateArgument.func_199825_a(var0x, "filter")
                                                )
                                          )
                                    )
                              )
                              .then(
                                 Commands.func_197057_a("keep")
                                    .executes(
                                       var0x -> func_198463_a(
                                             var0x.getSource(),
                                             new MutableBoundingBox(BlockPosArgument.func_197273_a(var0x, "from"), BlockPosArgument.func_197273_a(var0x, "to")),
                                             BlockStateArgument.func_197238_a(var0x, "block"),
                                             FillCommand.Mode.REPLACE,
                                             var0xx -> var0xx.func_196960_c().func_175623_d(var0xx.func_177508_d())
                                          )
                                    )
                              )
                              .then(
                                 Commands.func_197057_a("outline")
                                    .executes(
                                       var0x -> func_198463_a(
                                             var0x.getSource(),
                                             new MutableBoundingBox(BlockPosArgument.func_197273_a(var0x, "from"), BlockPosArgument.func_197273_a(var0x, "to")),
                                             BlockStateArgument.func_197238_a(var0x, "block"),
                                             FillCommand.Mode.OUTLINE,
                                             null
                                          )
                                    )
                              )
                              .then(
                                 Commands.func_197057_a("hollow")
                                    .executes(
                                       var0x -> func_198463_a(
                                             var0x.getSource(),
                                             new MutableBoundingBox(BlockPosArgument.func_197273_a(var0x, "from"), BlockPosArgument.func_197273_a(var0x, "to")),
                                             BlockStateArgument.func_197238_a(var0x, "block"),
                                             FillCommand.Mode.HOLLOW,
                                             null
                                          )
                                    )
                              )
                              .then(
                                 Commands.func_197057_a("destroy")
                                    .executes(
                                       var0x -> func_198463_a(
                                             var0x.getSource(),
                                             new MutableBoundingBox(BlockPosArgument.func_197273_a(var0x, "from"), BlockPosArgument.func_197273_a(var0x, "to")),
                                             BlockStateArgument.func_197238_a(var0x, "block"),
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

   private static int func_198463_a(
      CommandSource var0, MutableBoundingBox var1, BlockStateInput var2, FillCommand.Mode var3, @Nullable Predicate<BlockWorldState> var4
   ) throws CommandSyntaxException {
      int ☃ = ☃.func_78883_b() * ☃.func_78882_c() * ☃.func_78880_d();
      if (☃ > 32768) {
         throw field_198473_a.create(32768, ☃);
      } else {
         List<BlockPos> ☃ = Lists.<BlockPos>newArrayList();
         WorldServer ☃x = ☃.func_197023_e();
         int ☃xx = 0;

         for(BlockPos ☃xxx : BlockPos.MutableBlockPos.func_191532_a(
            ☃.field_78897_a, ☃.field_78895_b, ☃.field_78896_c, ☃.field_78893_d, ☃.field_78894_e, ☃.field_78892_f
         )) {
            if (☃ == null || ☃.test(new BlockWorldState(☃x, ☃xxx, true))) {
               BlockStateInput ☃xxxx = ☃.field_198459_e.filter(☃, ☃xxx, ☃, ☃x);
               if (☃xxxx != null) {
                  TileEntity ☃xxxxx = ☃x.func_175625_s(☃xxx);
                  if (☃xxxxx != null && ☃xxxxx instanceof IInventory) {
                     ((IInventory)☃xxxxx).func_174888_l();
                  }

                  if (☃xxxx.func_197230_a(☃x, ☃xxx, 2)) {
                     ☃.add(☃xxx.func_185334_h());
                     ++☃xx;
                  }
               }
            }
         }

         for(BlockPos ☃xxx : ☃) {
            Block ☃xxxx = ☃x.func_180495_p(☃xxx).func_177230_c();
            ☃x.func_195592_c(☃xxx, ☃xxxx);
         }

         if (☃xx == 0) {
            throw field_198475_c.create();
         } else {
            ☃.func_197030_a(new TextComponentTranslation("commands.fill.success", ☃xx), true);
            return ☃xx;
         }
      }
   }

   static enum Mode {
      REPLACE((var0, var1, var2, var3) -> var2),
      OUTLINE(
         (var0, var1, var2, var3) -> var1.func_177958_n() != var0.field_78897_a
                  && var1.func_177958_n() != var0.field_78893_d
                  && var1.func_177956_o() != var0.field_78895_b
                  && var1.func_177956_o() != var0.field_78894_e
                  && var1.func_177952_p() != var0.field_78896_c
                  && var1.func_177952_p() != var0.field_78892_f
               ? null
               : var2
      ),
      HOLLOW(
         (var0, var1, var2, var3) -> var1.func_177958_n() != var0.field_78897_a
                  && var1.func_177958_n() != var0.field_78893_d
                  && var1.func_177956_o() != var0.field_78895_b
                  && var1.func_177956_o() != var0.field_78894_e
                  && var1.func_177952_p() != var0.field_78896_c
                  && var1.func_177952_p() != var0.field_78892_f
               ? FillCommand.field_198474_b
               : var2
      ),
      DESTROY((var0, var1, var2, var3) -> {
         var3.func_175655_b(var1, true);
         return var2;
      });

      public final SetBlockCommand.IFilter field_198459_e;

      private Mode(SetBlockCommand.IFilter var3) {
         this.field_198459_e = ☃;
      }
   }
}
