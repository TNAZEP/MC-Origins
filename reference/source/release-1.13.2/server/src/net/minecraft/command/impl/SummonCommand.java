package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntitySummonArgument;
import net.minecraft.command.arguments.NBTArgument;
import net.minecraft.command.arguments.SuggestionProviders;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;

public class SummonCommand {
   private static final SimpleCommandExceptionType field_198741_a = new SimpleCommandExceptionType(new TextComponentTranslation("commands.summon.failed"));

   public static void func_198736_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("summon")
            .requires(var0x -> var0x.func_197034_c(2))
            .then(
               Commands.func_197056_a("entity", EntitySummonArgument.func_211366_a())
                  .suggests(SuggestionProviders.field_197505_d)
                  .executes(
                     var0x -> func_198737_a(
                           var0x.getSource(),
                           EntitySummonArgument.func_211368_a(var0x, "entity"),
                           var0x.getSource().func_197036_d(),
                           new NBTTagCompound(),
                           true
                        )
                  )
                  .then(
                     Commands.func_197056_a("pos", Vec3Argument.func_197301_a())
                        .executes(
                           var0x -> func_198737_a(
                                 var0x.getSource(),
                                 EntitySummonArgument.func_211368_a(var0x, "entity"),
                                 Vec3Argument.func_197300_a(var0x, "pos"),
                                 new NBTTagCompound(),
                                 true
                              )
                        )
                        .then(
                           Commands.func_197056_a("nbt", NBTArgument.func_197131_a())
                              .executes(
                                 var0x -> func_198737_a(
                                       var0x.getSource(),
                                       EntitySummonArgument.func_211368_a(var0x, "entity"),
                                       Vec3Argument.func_197300_a(var0x, "pos"),
                                       NBTArgument.func_197130_a(var0x, "nbt"),
                                       false
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int func_198737_a(CommandSource var0, ResourceLocation var1, Vec3d var2, NBTTagCompound var3, boolean var4) throws CommandSyntaxException {
      NBTTagCompound ☃ = ☃.func_74737_b();
      ☃.func_74778_a("id", ☃.toString());
      if (EntityType.func_200718_a(EntityType.field_200728_aG).equals(☃)) {
         Entity ☃x = new EntityLightningBolt(☃.func_197023_e(), ☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c, false);
         ☃.func_197023_e().func_72942_c(☃x);
         ☃.func_197030_a(new TextComponentTranslation("commands.summon.success", ☃x.func_145748_c_()), true);
         return 1;
      } else {
         Entity ☃ = AnvilChunkLoader.func_186054_a(☃, ☃.func_197023_e(), ☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c, true);
         if (☃ == null) {
            throw field_198741_a.create();
         } else {
            ☃.func_70012_b(☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c, ☃.field_70177_z, ☃.field_70125_A);
            if (☃ && ☃ instanceof EntityLiving) {
               ((EntityLiving)☃).func_204210_a(☃.func_197023_e().func_175649_E(new BlockPos(☃)), null, null);
            }

            ☃.func_197030_a(new TextComponentTranslation("commands.summon.success", ☃.func_145748_c_()), true);
            return 1;
         }
      }
   }
}
