package net.minecraft.command.impl;

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
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.BlockPredicateArgument;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.WorldServer;

public class CloneCommand {
   private static final SimpleCommandExceptionType field_198284_b = new SimpleCommandExceptionType(new TextComponentTranslation("commands.clone.overlap"));
   private static final Dynamic2CommandExceptionType field_198285_c = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TextComponentTranslation("commands.clone.toobig", var0, var1)
   );
   private static final SimpleCommandExceptionType field_198286_d = new SimpleCommandExceptionType(new TextComponentTranslation("commands.clone.failed"));
   public static final Predicate<BlockWorldState> field_198283_a = var0 -> !var0.func_177509_a().func_196958_f();

   public static void func_198265_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("clone")
            .requires(var0x -> var0x.func_197034_c(2))
            .then(
               Commands.func_197056_a("begin", BlockPosArgument.func_197276_a())
                  .then(
                     Commands.func_197056_a("end", BlockPosArgument.func_197276_a())
                        .then(
                           Commands.func_197056_a("destination", BlockPosArgument.func_197276_a())
                              .executes(
                                 var0x -> func_198274_a(
                                       var0x.getSource(),
                                       BlockPosArgument.func_197273_a(var0x, "begin"),
                                       BlockPosArgument.func_197273_a(var0x, "end"),
                                       BlockPosArgument.func_197273_a(var0x, "destination"),
                                       var0xx -> true,
                                       CloneCommand.Mode.NORMAL
                                    )
                              )
                              .then(
                                 Commands.func_197057_a("replace")
                                    .executes(
                                       var0x -> func_198274_a(
                                             var0x.getSource(),
                                             BlockPosArgument.func_197273_a(var0x, "begin"),
                                             BlockPosArgument.func_197273_a(var0x, "end"),
                                             BlockPosArgument.func_197273_a(var0x, "destination"),
                                             var0xx -> true,
                                             CloneCommand.Mode.NORMAL
                                          )
                                    )
                                    .then(
                                       Commands.func_197057_a("force")
                                          .executes(
                                             var0x -> func_198274_a(
                                                   var0x.getSource(),
                                                   BlockPosArgument.func_197273_a(var0x, "begin"),
                                                   BlockPosArgument.func_197273_a(var0x, "end"),
                                                   BlockPosArgument.func_197273_a(var0x, "destination"),
                                                   var0xx -> true,
                                                   CloneCommand.Mode.FORCE
                                                )
                                          )
                                    )
                                    .then(
                                       Commands.func_197057_a("move")
                                          .executes(
                                             var0x -> func_198274_a(
                                                   var0x.getSource(),
                                                   BlockPosArgument.func_197273_a(var0x, "begin"),
                                                   BlockPosArgument.func_197273_a(var0x, "end"),
                                                   BlockPosArgument.func_197273_a(var0x, "destination"),
                                                   var0xx -> true,
                                                   CloneCommand.Mode.MOVE
                                                )
                                          )
                                    )
                                    .then(
                                       Commands.func_197057_a("normal")
                                          .executes(
                                             var0x -> func_198274_a(
                                                   var0x.getSource(),
                                                   BlockPosArgument.func_197273_a(var0x, "begin"),
                                                   BlockPosArgument.func_197273_a(var0x, "end"),
                                                   BlockPosArgument.func_197273_a(var0x, "destination"),
                                                   var0xx -> true,
                                                   CloneCommand.Mode.NORMAL
                                                )
                                          )
                                    )
                              )
                              .then(
                                 Commands.func_197057_a("masked")
                                    .executes(
                                       var0x -> func_198274_a(
                                             var0x.getSource(),
                                             BlockPosArgument.func_197273_a(var0x, "begin"),
                                             BlockPosArgument.func_197273_a(var0x, "end"),
                                             BlockPosArgument.func_197273_a(var0x, "destination"),
                                             field_198283_a,
                                             CloneCommand.Mode.NORMAL
                                          )
                                    )
                                    .then(
                                       Commands.func_197057_a("force")
                                          .executes(
                                             var0x -> func_198274_a(
                                                   var0x.getSource(),
                                                   BlockPosArgument.func_197273_a(var0x, "begin"),
                                                   BlockPosArgument.func_197273_a(var0x, "end"),
                                                   BlockPosArgument.func_197273_a(var0x, "destination"),
                                                   field_198283_a,
                                                   CloneCommand.Mode.FORCE
                                                )
                                          )
                                    )
                                    .then(
                                       Commands.func_197057_a("move")
                                          .executes(
                                             var0x -> func_198274_a(
                                                   var0x.getSource(),
                                                   BlockPosArgument.func_197273_a(var0x, "begin"),
                                                   BlockPosArgument.func_197273_a(var0x, "end"),
                                                   BlockPosArgument.func_197273_a(var0x, "destination"),
                                                   field_198283_a,
                                                   CloneCommand.Mode.MOVE
                                                )
                                          )
                                    )
                                    .then(
                                       Commands.func_197057_a("normal")
                                          .executes(
                                             var0x -> func_198274_a(
                                                   var0x.getSource(),
                                                   BlockPosArgument.func_197273_a(var0x, "begin"),
                                                   BlockPosArgument.func_197273_a(var0x, "end"),
                                                   BlockPosArgument.func_197273_a(var0x, "destination"),
                                                   field_198283_a,
                                                   CloneCommand.Mode.NORMAL
                                                )
                                          )
                                    )
                              )
                              .then(
                                 Commands.func_197057_a("filtered")
                                    .then(
                                       Commands.func_197056_a("filter", BlockPredicateArgument.func_199824_a())
                                          .executes(
                                             var0x -> func_198274_a(
                                                   var0x.getSource(),
                                                   BlockPosArgument.func_197273_a(var0x, "begin"),
                                                   BlockPosArgument.func_197273_a(var0x, "end"),
                                                   BlockPosArgument.func_197273_a(var0x, "destination"),
                                                   BlockPredicateArgument.func_199825_a(var0x, "filter"),
                                                   CloneCommand.Mode.NORMAL
                                                )
                                          )
                                          .then(
                                             Commands.func_197057_a("force")
                                                .executes(
                                                   var0x -> func_198274_a(
                                                         var0x.getSource(),
                                                         BlockPosArgument.func_197273_a(var0x, "begin"),
                                                         BlockPosArgument.func_197273_a(var0x, "end"),
                                                         BlockPosArgument.func_197273_a(var0x, "destination"),
                                                         BlockPredicateArgument.func_199825_a(var0x, "filter"),
                                                         CloneCommand.Mode.FORCE
                                                      )
                                                )
                                          )
                                          .then(
                                             Commands.func_197057_a("move")
                                                .executes(
                                                   var0x -> func_198274_a(
                                                         var0x.getSource(),
                                                         BlockPosArgument.func_197273_a(var0x, "begin"),
                                                         BlockPosArgument.func_197273_a(var0x, "end"),
                                                         BlockPosArgument.func_197273_a(var0x, "destination"),
                                                         BlockPredicateArgument.func_199825_a(var0x, "filter"),
                                                         CloneCommand.Mode.MOVE
                                                      )
                                                )
                                          )
                                          .then(
                                             Commands.func_197057_a("normal")
                                                .executes(
                                                   var0x -> func_198274_a(
                                                         var0x.getSource(),
                                                         BlockPosArgument.func_197273_a(var0x, "begin"),
                                                         BlockPosArgument.func_197273_a(var0x, "end"),
                                                         BlockPosArgument.func_197273_a(var0x, "destination"),
                                                         BlockPredicateArgument.func_199825_a(var0x, "filter"),
                                                         CloneCommand.Mode.NORMAL
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

   private static int func_198274_a(CommandSource var0, BlockPos var1, BlockPos var2, BlockPos var3, Predicate<BlockWorldState> var4, CloneCommand.Mode var5) throws CommandSyntaxException {
      MutableBoundingBox ☃ = new MutableBoundingBox(☃, ☃);
      MutableBoundingBox ☃x = new MutableBoundingBox(☃, ☃.func_177971_a(☃.func_175896_b()));
      if (!☃.func_198254_a() && ☃x.func_78884_a(☃)) {
         throw field_198284_b.create();
      } else {
         int ☃ = ☃.func_78883_b() * ☃.func_78882_c() * ☃.func_78880_d();
         if (☃ > 32768) {
            throw field_198285_c.create(32768, ☃);
         } else {
            WorldServer ☃ = ☃.func_197023_e();
            if (☃.func_175711_a(☃) && ☃.func_175711_a(☃x)) {
               List<CloneCommand.BlockInfo> ☃x = Lists.<CloneCommand.BlockInfo>newArrayList();
               List<CloneCommand.BlockInfo> ☃xx = Lists.<CloneCommand.BlockInfo>newArrayList();
               List<CloneCommand.BlockInfo> ☃xxx = Lists.<CloneCommand.BlockInfo>newArrayList();
               Deque<BlockPos> ☃xxxx = Lists.<BlockPos>newLinkedList();
               BlockPos ☃xxxxx = new BlockPos(☃x.field_78897_a - ☃.field_78897_a, ☃x.field_78895_b - ☃.field_78895_b, ☃x.field_78896_c - ☃.field_78896_c);

               for(int ☃xxxxxx = ☃.field_78896_c; ☃xxxxxx <= ☃.field_78892_f; ++☃xxxxxx) {
                  for(int ☃xxxxxxx = ☃.field_78895_b; ☃xxxxxxx <= ☃.field_78894_e; ++☃xxxxxxx) {
                     for(int ☃xxxxxxxx = ☃.field_78897_a; ☃xxxxxxxx <= ☃.field_78893_d; ++☃xxxxxxxx) {
                        BlockPos ☃xxxxxxxxx = new BlockPos(☃xxxxxxxx, ☃xxxxxxx, ☃xxxxxx);
                        BlockPos ☃xxxxxxxxxx = ☃xxxxxxxxx.func_177971_a(☃xxxxx);
                        BlockWorldState ☃xxxxxxxxxxx = new BlockWorldState(☃, ☃xxxxxxxxx, false);
                        IBlockState ☃xxxxxxxxxxxx = ☃xxxxxxxxxxx.func_177509_a();
                        if (☃.test(☃xxxxxxxxxxx)) {
                           TileEntity ☃xxxxxxxxxxxxx = ☃.func_175625_s(☃xxxxxxxxx);
                           if (☃xxxxxxxxxxxxx != null) {
                              NBTTagCompound ☃xxxxxxxxxxxxxx = ☃xxxxxxxxxxxxx.func_189515_b(new NBTTagCompound());
                              ☃xx.add(new CloneCommand.BlockInfo(☃xxxxxxxxxx, ☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxxx));
                              ☃xxxx.addLast(☃xxxxxxxxx);
                           } else if (!☃xxxxxxxxxxxx.func_200015_d(☃, ☃xxxxxxxxx) && !☃xxxxxxxxxxxx.func_185917_h()) {
                              ☃xxx.add(new CloneCommand.BlockInfo(☃xxxxxxxxxx, ☃xxxxxxxxxxxx, null));
                              ☃xxxx.addFirst(☃xxxxxxxxx);
                           } else {
                              ☃x.add(new CloneCommand.BlockInfo(☃xxxxxxxxxx, ☃xxxxxxxxxxxx, null));
                              ☃xxxx.addLast(☃xxxxxxxxx);
                           }
                        }
                     }
                  }
               }

               if (☃ == CloneCommand.Mode.MOVE) {
                  for(BlockPos ☃xxxxxx : ☃xxxx) {
                     TileEntity ☃xxxxxxx = ☃.func_175625_s(☃xxxxxx);
                     if (☃xxxxxxx instanceof IInventory) {
                        ((IInventory)☃xxxxxxx).func_174888_l();
                     }

                     ☃.func_180501_a(☃xxxxxx, Blocks.field_180401_cv.func_176223_P(), 2);
                  }

                  for(BlockPos ☃xxxxxx : ☃xxxx) {
                     ☃.func_180501_a(☃xxxxxx, Blocks.field_150350_a.func_176223_P(), 3);
                  }
               }

               List<CloneCommand.BlockInfo> ☃xxxxxx = Lists.<CloneCommand.BlockInfo>newArrayList();
               ☃xxxxxx.addAll(☃x);
               ☃xxxxxx.addAll(☃xx);
               ☃xxxxxx.addAll(☃xxx);
               List<CloneCommand.BlockInfo> ☃xxxxxxx = Lists.reverse(☃xxxxxx);

               for(CloneCommand.BlockInfo ☃xxxxxxxx : ☃xxxxxxx) {
                  TileEntity ☃xxxxxxxxx = ☃.func_175625_s(☃xxxxxxxx.field_198251_a);
                  if (☃xxxxxxxxx instanceof IInventory) {
                     ((IInventory)☃xxxxxxxxx).func_174888_l();
                  }

                  ☃.func_180501_a(☃xxxxxxxx.field_198251_a, Blocks.field_180401_cv.func_176223_P(), 2);
               }

               int ☃xxxxxxxx = 0;

               for(CloneCommand.BlockInfo ☃xxxxxxxxx : ☃xxxxxx) {
                  if (☃.func_180501_a(☃xxxxxxxxx.field_198251_a, ☃xxxxxxxxx.field_198252_b, 2)) {
                     ++☃xxxxxxxx;
                  }
               }

               for(CloneCommand.BlockInfo ☃xxxxxxxxx : ☃xx) {
                  TileEntity ☃xxxxxxxxxx = ☃.func_175625_s(☃xxxxxxxxx.field_198251_a);
                  if (☃xxxxxxxxx.field_198253_c != null && ☃xxxxxxxxxx != null) {
                     ☃xxxxxxxxx.field_198253_c.func_74768_a("x", ☃xxxxxxxxx.field_198251_a.func_177958_n());
                     ☃xxxxxxxxx.field_198253_c.func_74768_a("y", ☃xxxxxxxxx.field_198251_a.func_177956_o());
                     ☃xxxxxxxxx.field_198253_c.func_74768_a("z", ☃xxxxxxxxx.field_198251_a.func_177952_p());
                     ☃xxxxxxxxxx.func_145839_a(☃xxxxxxxxx.field_198253_c);
                     ☃xxxxxxxxxx.func_70296_d();
                  }

                  ☃.func_180501_a(☃xxxxxxxxx.field_198251_a, ☃xxxxxxxxx.field_198252_b, 2);
               }

               for(CloneCommand.BlockInfo ☃xxxxxxxxx : ☃xxxxxxx) {
                  ☃.func_195592_c(☃xxxxxxxxx.field_198251_a, ☃xxxxxxxxx.field_198252_b.func_177230_c());
               }

               ☃.func_205220_G_().func_205368_a(☃, ☃xxxxx);
               if (☃xxxxxxxx == 0) {
                  throw field_198286_d.create();
               } else {
                  ☃.func_197030_a(new TextComponentTranslation("commands.clone.success", ☃xxxxxxxx), true);
                  return ☃xxxxxxxx;
               }
            } else {
               throw BlockPosArgument.field_197278_b.create();
            }
         }
      }
   }

   static class BlockInfo {
      public final BlockPos field_198251_a;
      public final IBlockState field_198252_b;
      @Nullable
      public final NBTTagCompound field_198253_c;

      public BlockInfo(BlockPos var1, IBlockState var2, @Nullable NBTTagCompound var3) {
         this.field_198251_a = ☃;
         this.field_198252_b = ☃;
         this.field_198253_c = ☃;
      }
   }

   static enum Mode {
      FORCE(true),
      MOVE(true),
      NORMAL(false);

      private final boolean field_198259_d;

      private Mode(boolean var3) {
         this.field_198259_d = ☃;
      }

      public boolean func_198254_a() {
         return this.field_198259_d;
      }
   }
}
