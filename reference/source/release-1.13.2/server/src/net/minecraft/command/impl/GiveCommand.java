package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ItemArgument;
import net.minecraft.command.arguments.ItemInput;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;

public class GiveCommand {
   public static void func_198494_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("give")
            .requires(var0x -> var0x.func_197034_c(2))
            .then(
               Commands.func_197056_a("targets", EntityArgument.func_197094_d())
                  .then(
                     Commands.func_197056_a("item", ItemArgument.func_197317_a())
                        .executes(
                           var0x -> func_198497_a(
                                 var0x.getSource(), ItemArgument.func_197316_a(var0x, "item"), EntityArgument.func_197090_e(var0x, "targets"), 1
                              )
                        )
                        .then(
                           Commands.func_197056_a("count", IntegerArgumentType.integer(1))
                              .executes(
                                 var0x -> func_198497_a(
                                       var0x.getSource(),
                                       ItemArgument.func_197316_a(var0x, "item"),
                                       EntityArgument.func_197090_e(var0x, "targets"),
                                       IntegerArgumentType.getInteger(var0x, "count")
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int func_198497_a(CommandSource var0, ItemInput var1, Collection<EntityPlayerMP> var2, int var3) throws CommandSyntaxException {
      for(EntityPlayerMP ☃ : ☃) {
         int ☃x = ☃;

         while(☃x > 0) {
            int ☃xx = Math.min(☃.func_197319_a().func_77639_j(), ☃x);
            ☃x -= ☃xx;
            ItemStack ☃xxx = ☃.func_197320_a(☃xx, false);
            boolean ☃xxxx = ☃.field_71071_by.func_70441_a(☃xxx);
            if (☃xxxx && ☃xxx.func_190926_b()) {
               ☃xxx.func_190920_e(1);
               EntityItem ☃xxxxx = ☃.func_71019_a(☃xxx, false);
               if (☃xxxxx != null) {
                  ☃xxxxx.func_174870_v();
               }

               ☃.field_70170_p
                  .func_184148_a(
                     null,
                     ☃.field_70165_t,
                     ☃.field_70163_u,
                     ☃.field_70161_v,
                     SoundEvents.field_187638_cR,
                     SoundCategory.PLAYERS,
                     0.2F,
                     ((☃.func_70681_au().nextFloat() - ☃.func_70681_au().nextFloat()) * 0.7F + 1.0F) * 2.0F
                  );
               ☃.field_71069_bz.func_75142_b();
            } else {
               EntityItem ☃xx = ☃.func_71019_a(☃xxx, false);
               if (☃xx != null) {
                  ☃xx.func_174868_q();
                  ☃xx.func_200217_b(☃.func_110124_au());
               }
            }
         }
      }

      if (☃.size() == 1) {
         ☃.func_197030_a(
            new TextComponentTranslation(
               "commands.give.success.single", ☃, ☃.func_197320_a(☃, false).func_151000_E(), ((EntityPlayerMP)☃.iterator().next()).func_145748_c_()
            ),
            true
         );
      } else {
         ☃.func_197030_a(new TextComponentTranslation("commands.give.success.single", ☃, ☃.func_197320_a(☃, false).func_151000_E(), ☃.size()), true);
      }

      return ☃.size();
   }
}
