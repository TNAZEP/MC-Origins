package net.minecraft.command.impl;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.List;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ItemArgument;
import net.minecraft.command.arguments.SlotArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

public class ReplaceItemCommand {
   private static final SimpleCommandExceptionType field_198608_a = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.replaceitem.block.failed")
   );
   private static final DynamicCommandExceptionType field_198609_b = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("commands.replaceitem.slot.inapplicable", var0)
   );
   private static final Dynamic2CommandExceptionType field_211412_c = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TextComponentTranslation("commands.replaceitem.entity.failed", var0, var1)
   );

   public static void func_198602_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("replaceitem")
            .requires(var0x -> var0x.func_197034_c(2))
            .then(
               Commands.func_197057_a("block")
                  .then(
                     Commands.func_197056_a("pos", BlockPosArgument.func_197276_a())
                        .then(
                           Commands.func_197056_a("slot", SlotArgument.func_197223_a())
                              .then(
                                 Commands.func_197056_a("item", ItemArgument.func_197317_a())
                                    .executes(
                                       var0x -> func_198603_a(
                                             var0x.getSource(),
                                             BlockPosArgument.func_197273_a(var0x, "pos"),
                                             SlotArgument.func_197221_a(var0x, "slot"),
                                             ItemArgument.func_197316_a(var0x, "item").func_197320_a(1, false)
                                          )
                                    )
                                    .then(
                                       Commands.func_197056_a("count", IntegerArgumentType.integer(1, 64))
                                          .executes(
                                             var0x -> func_198603_a(
                                                   var0x.getSource(),
                                                   BlockPosArgument.func_197273_a(var0x, "pos"),
                                                   SlotArgument.func_197221_a(var0x, "slot"),
                                                   ItemArgument.func_197316_a(var0x, "item")
                                                      .func_197320_a(IntegerArgumentType.getInteger(var0x, "count"), true)
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
            .then(
               Commands.func_197057_a("entity")
                  .then(
                     Commands.func_197056_a("targets", EntityArgument.func_197093_b())
                        .then(
                           Commands.func_197056_a("slot", SlotArgument.func_197223_a())
                              .then(
                                 Commands.func_197056_a("item", ItemArgument.func_197317_a())
                                    .executes(
                                       var0x -> func_198604_a(
                                             var0x.getSource(),
                                             EntityArgument.func_197097_b(var0x, "targets"),
                                             SlotArgument.func_197221_a(var0x, "slot"),
                                             ItemArgument.func_197316_a(var0x, "item").func_197320_a(1, false)
                                          )
                                    )
                                    .then(
                                       Commands.func_197056_a("count", IntegerArgumentType.integer(1, 64))
                                          .executes(
                                             var0x -> func_198604_a(
                                                   var0x.getSource(),
                                                   EntityArgument.func_197097_b(var0x, "targets"),
                                                   SlotArgument.func_197221_a(var0x, "slot"),
                                                   ItemArgument.func_197316_a(var0x, "item")
                                                      .func_197320_a(IntegerArgumentType.getInteger(var0x, "count"), true)
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int func_198603_a(CommandSource var0, BlockPos var1, int var2, ItemStack var3) throws CommandSyntaxException {
      TileEntity ☃ = ☃.func_197023_e().func_175625_s(☃);
      if (!(☃ instanceof IInventory)) {
         throw field_198608_a.create();
      } else {
         IInventory ☃ = (IInventory)☃;
         if (☃ >= 0 && ☃ < ☃.func_70302_i_()) {
            ☃.func_70299_a(☃, ☃);
            ☃.func_197030_a(
               new TextComponentTranslation("commands.replaceitem.block.success", ☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p(), ☃.func_151000_E()),
               true
            );
            return 1;
         } else {
            throw field_198609_b.create(☃);
         }
      }
   }

   private static int func_198604_a(CommandSource var0, Collection<? extends Entity> var1, int var2, ItemStack var3) throws CommandSyntaxException {
      List<Entity> ☃ = Lists.<Entity>newArrayListWithCapacity(☃.size());

      for(Entity ☃x : ☃) {
         if (☃x instanceof EntityPlayerMP) {
            ((EntityPlayerMP)☃x).field_71069_bz.func_75142_b();
         }

         if (☃x.func_174820_d(☃, ☃.func_77946_l())) {
            ☃.add(☃x);
            if (☃x instanceof EntityPlayerMP) {
               ((EntityPlayerMP)☃x).field_71069_bz.func_75142_b();
            }
         }
      }

      if (☃.isEmpty()) {
         throw field_211412_c.create(☃.func_151000_E(), ☃);
      } else {
         if (☃.size() == 1) {
            ☃.func_197030_a(
               new TextComponentTranslation("commands.replaceitem.entity.success.single", ((Entity)☃.iterator().next()).func_145748_c_(), ☃.func_151000_E()),
               true
            );
         } else {
            ☃.func_197030_a(new TextComponentTranslation("commands.replaceitem.entity.success.multiple", ☃.size(), ☃.func_151000_E()), true);
         }

         return ☃.size();
      }
   }
}
