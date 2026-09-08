package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.command.arguments.SuggestionProviders;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketCustomSound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;

public class PlaySoundCommand {
   private static final SimpleCommandExceptionType field_198579_a = new SimpleCommandExceptionType(new TextComponentTranslation("commands.playsound.failed"));

   public static void func_198572_a(CommandDispatcher<CommandSource> var0) {
      RequiredArgumentBuilder<CommandSource, ResourceLocation> ☃ = Commands.func_197056_a("sound", ResourceLocationArgument.func_197197_a())
         .suggests(SuggestionProviders.field_197504_c);

      for(SoundCategory ☃x : SoundCategory.values()) {
         ☃.then(func_198577_a(☃x));
      }

      ☃.register(Commands.func_197057_a("playsound").requires(var0x -> var0x.func_197034_c(2)).then(☃));
   }

   private static LiteralArgumentBuilder<CommandSource> func_198577_a(SoundCategory var0) {
      return Commands.func_197057_a(☃.func_187948_a())
         .then(
            Commands.func_197056_a("targets", EntityArgument.func_197094_d())
               .executes(
                  var1 -> func_198573_a(
                        var1.getSource(),
                        EntityArgument.func_197090_e(var1, "targets"),
                        ResourceLocationArgument.func_197195_e(var1, "sound"),
                        ☃,
                        var1.getSource().func_197036_d(),
                        1.0F,
                        1.0F,
                        0.0F
                     )
               )
               .then(
                  Commands.func_197056_a("pos", Vec3Argument.func_197301_a())
                     .executes(
                        var1 -> func_198573_a(
                              var1.getSource(),
                              EntityArgument.func_197090_e(var1, "targets"),
                              ResourceLocationArgument.func_197195_e(var1, "sound"),
                              ☃,
                              Vec3Argument.func_197300_a(var1, "pos"),
                              1.0F,
                              1.0F,
                              0.0F
                           )
                     )
                     .then(
                        Commands.func_197056_a("volume", FloatArgumentType.floatArg(0.0F))
                           .executes(
                              var1 -> func_198573_a(
                                    var1.getSource(),
                                    EntityArgument.func_197090_e(var1, "targets"),
                                    ResourceLocationArgument.func_197195_e(var1, "sound"),
                                    ☃,
                                    Vec3Argument.func_197300_a(var1, "pos"),
                                    var1.getArgument("volume", Float.class),
                                    1.0F,
                                    0.0F
                                 )
                           )
                           .then(
                              Commands.func_197056_a("pitch", FloatArgumentType.floatArg(0.0F, 2.0F))
                                 .executes(
                                    var1 -> func_198573_a(
                                          var1.getSource(),
                                          EntityArgument.func_197090_e(var1, "targets"),
                                          ResourceLocationArgument.func_197195_e(var1, "sound"),
                                          ☃,
                                          Vec3Argument.func_197300_a(var1, "pos"),
                                          var1.getArgument("volume", Float.class),
                                          var1.getArgument("pitch", Float.class),
                                          0.0F
                                       )
                                 )
                                 .then(
                                    Commands.func_197056_a("minVolume", FloatArgumentType.floatArg(0.0F, 1.0F))
                                       .executes(
                                          var1 -> func_198573_a(
                                                var1.getSource(),
                                                EntityArgument.func_197090_e(var1, "targets"),
                                                ResourceLocationArgument.func_197195_e(var1, "sound"),
                                                ☃,
                                                Vec3Argument.func_197300_a(var1, "pos"),
                                                var1.getArgument("volume", Float.class),
                                                var1.getArgument("pitch", Float.class),
                                                var1.getArgument("minVolume", Float.class)
                                             )
                                       )
                                 )
                           )
                     )
               )
         );
   }

   private static int func_198573_a(
      CommandSource var0, Collection<EntityPlayerMP> var1, ResourceLocation var2, SoundCategory var3, Vec3d var4, float var5, float var6, float var7
   ) throws CommandSyntaxException {
      double ☃ = Math.pow(☃ > 1.0F ? (double)(☃ * 16.0F) : 16.0, 2.0);
      int ☃x = 0;

      for(EntityPlayerMP ☃xx : ☃) {
         double ☃xxx = ☃.field_72450_a - ☃xx.field_70165_t;
         double ☃xxxx = ☃.field_72448_b - ☃xx.field_70163_u;
         double ☃xxxxx = ☃.field_72449_c - ☃xx.field_70161_v;
         double ☃xxxxxx = ☃xxx * ☃xxx + ☃xxxx * ☃xxxx + ☃xxxxx * ☃xxxxx;
         Vec3d ☃xxxxxxx = ☃;
         float ☃xxxxxxxx = ☃;
         if (☃xxxxxx > ☃) {
            if (☃ <= 0.0F) {
               continue;
            }

            double ☃xxxxxxxxx = (double)MathHelper.func_76133_a(☃xxxxxx);
            ☃xxxxxxx = new Vec3d(
               ☃xx.field_70165_t + ☃xxx / ☃xxxxxxxxx * 2.0, ☃xx.field_70163_u + ☃xxxx / ☃xxxxxxxxx * 2.0, ☃xx.field_70161_v + ☃xxxxx / ☃xxxxxxxxx * 2.0
            );
            ☃xxxxxxxx = ☃;
         }

         ☃xx.field_71135_a.func_147359_a(new SPacketCustomSound(☃, ☃, ☃xxxxxxx, ☃xxxxxxxx, ☃));
         ++☃x;
      }

      if (☃x == 0) {
         throw field_198579_a.create();
      } else {
         if (☃.size() == 1) {
            ☃.func_197030_a(new TextComponentTranslation("commands.playsound.success.single", ☃, ((EntityPlayerMP)☃.iterator().next()).func_145748_c_()), true);
         } else {
            ☃.func_197030_a(new TextComponentTranslation("commands.playsound.success.single", ☃, ((EntityPlayerMP)☃.iterator().next()).func_145748_c_()), true);
         }

         return ☃x;
      }
   }
}
