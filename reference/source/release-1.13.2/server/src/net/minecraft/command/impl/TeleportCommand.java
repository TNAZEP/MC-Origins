package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ILocationArgument;
import net.minecraft.command.arguments.LocationInput;
import net.minecraft.command.arguments.RotationArgument;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.WorldServer;

public class TeleportCommand {
   public static void func_198809_a(CommandDispatcher<CommandSource> var0) {
      LiteralCommandNode<CommandSource> ☃ = ☃.register(
         Commands.func_197057_a("teleport")
            .requires(var0x -> var0x.func_197034_c(2))
            .then(
               Commands.func_197056_a("targets", EntityArgument.func_197093_b())
                  .then(
                     Commands.func_197056_a("location", Vec3Argument.func_197301_a())
                        .executes(
                           var0x -> func_200559_a(
                                 var0x.getSource(),
                                 EntityArgument.func_197097_b(var0x, "targets"),
                                 var0x.getSource().func_197023_e(),
                                 Vec3Argument.func_200385_b(var0x, "location"),
                                 null,
                                 null
                              )
                        )
                        .then(
                           Commands.func_197056_a("rotation", RotationArgument.func_197288_a())
                              .executes(
                                 var0x -> func_200559_a(
                                       var0x.getSource(),
                                       EntityArgument.func_197097_b(var0x, "targets"),
                                       var0x.getSource().func_197023_e(),
                                       Vec3Argument.func_200385_b(var0x, "location"),
                                       RotationArgument.func_200384_a(var0x, "rotation"),
                                       null
                                    )
                              )
                        )
                        .then(
                           Commands.func_197057_a("facing")
                              .then(
                                 Commands.func_197057_a("entity")
                                    .then(
                                       Commands.func_197056_a("facingEntity", EntityArgument.func_197086_a())
                                          .executes(
                                             var0x -> func_200559_a(
                                                   var0x.getSource(),
                                                   EntityArgument.func_197097_b(var0x, "targets"),
                                                   var0x.getSource().func_197023_e(),
                                                   Vec3Argument.func_200385_b(var0x, "location"),
                                                   null,
                                                   new TeleportCommand.Facing(
                                                      EntityArgument.func_197088_a(var0x, "facingEntity"), EntityAnchorArgument.Type.FEET
                                                   )
                                                )
                                          )
                                          .then(
                                             Commands.func_197056_a("facingAnchor", EntityAnchorArgument.func_201024_a())
                                                .executes(
                                                   var0x -> func_200559_a(
                                                         var0x.getSource(),
                                                         EntityArgument.func_197097_b(var0x, "targets"),
                                                         var0x.getSource().func_197023_e(),
                                                         Vec3Argument.func_200385_b(var0x, "location"),
                                                         null,
                                                         new TeleportCommand.Facing(
                                                            EntityArgument.func_197088_a(var0x, "facingEntity"),
                                                            EntityAnchorArgument.func_201023_a(var0x, "facingAnchor")
                                                         )
                                                      )
                                                )
                                          )
                                    )
                              )
                              .then(
                                 Commands.func_197056_a("facingLocation", Vec3Argument.func_197301_a())
                                    .executes(
                                       var0x -> func_200559_a(
                                             var0x.getSource(),
                                             EntityArgument.func_197097_b(var0x, "targets"),
                                             var0x.getSource().func_197023_e(),
                                             Vec3Argument.func_200385_b(var0x, "location"),
                                             null,
                                             new TeleportCommand.Facing(Vec3Argument.func_197300_a(var0x, "facingLocation"))
                                          )
                                    )
                              )
                        )
                  )
                  .then(
                     Commands.func_197056_a("destination", EntityArgument.func_197086_a())
                        .executes(
                           var0x -> func_201126_a(
                                 var0x.getSource(), EntityArgument.func_197097_b(var0x, "targets"), EntityArgument.func_197088_a(var0x, "destination")
                              )
                        )
                  )
            )
            .then(
               Commands.func_197056_a("location", Vec3Argument.func_197301_a())
                  .executes(
                     var0x -> func_200559_a(
                           var0x.getSource(),
                           Collections.singleton(var0x.getSource().func_197027_g()),
                           var0x.getSource().func_197023_e(),
                           Vec3Argument.func_200385_b(var0x, "location"),
                           LocationInput.func_200383_d(),
                           null
                        )
                  )
            )
            .then(
               Commands.func_197056_a("destination", EntityArgument.func_197086_a())
                  .executes(
                     var0x -> func_201126_a(
                           var0x.getSource(), Collections.singleton(var0x.getSource().func_197027_g()), EntityArgument.func_197088_a(var0x, "destination")
                        )
                  )
            )
      );
      ☃.register(Commands.func_197057_a("tp").requires(var0x -> var0x.func_197034_c(2)).redirect(☃));
   }

   private static int func_201126_a(CommandSource var0, Collection<? extends Entity> var1, Entity var2) {
      for(Entity ☃ : ☃) {
         func_201127_a(
            ☃,
            ☃,
            ☃.func_197023_e(),
            ☃.field_70165_t,
            ☃.field_70163_u,
            ☃.field_70161_v,
            EnumSet.noneOf(SPacketPlayerPosLook.EnumFlags.class),
            ☃.field_70177_z,
            ☃.field_70125_A,
            null
         );
      }

      if (☃.size() == 1) {
         ☃.func_197030_a(
            new TextComponentTranslation("commands.teleport.success.entity.single", ((Entity)☃.iterator().next()).func_145748_c_(), ☃.func_145748_c_()), true
         );
      } else {
         ☃.func_197030_a(new TextComponentTranslation("commands.teleport.success.entity.multiple", ☃.size(), ☃.func_145748_c_()), true);
      }

      return ☃.size();
   }

   private static int func_200559_a(
      CommandSource var0,
      Collection<? extends Entity> var1,
      WorldServer var2,
      ILocationArgument var3,
      @Nullable ILocationArgument var4,
      @Nullable TeleportCommand.Facing var5
   ) throws CommandSyntaxException {
      Vec3d ☃ = ☃.func_197281_a(☃);
      Vec2f ☃x = ☃ == null ? null : ☃.func_197282_b(☃);
      Set<SPacketPlayerPosLook.EnumFlags> ☃xx = EnumSet.noneOf(SPacketPlayerPosLook.EnumFlags.class);
      if (☃.func_200380_a()) {
         ☃xx.add(SPacketPlayerPosLook.EnumFlags.X);
      }

      if (☃.func_200381_b()) {
         ☃xx.add(SPacketPlayerPosLook.EnumFlags.Y);
      }

      if (☃.func_200382_c()) {
         ☃xx.add(SPacketPlayerPosLook.EnumFlags.Z);
      }

      if (☃ == null) {
         ☃xx.add(SPacketPlayerPosLook.EnumFlags.X_ROT);
         ☃xx.add(SPacketPlayerPosLook.EnumFlags.Y_ROT);
      } else {
         if (☃.func_200380_a()) {
            ☃xx.add(SPacketPlayerPosLook.EnumFlags.X_ROT);
         }

         if (☃.func_200381_b()) {
            ☃xx.add(SPacketPlayerPosLook.EnumFlags.Y_ROT);
         }
      }

      for(Entity ☃ : ☃) {
         if (☃ == null) {
            func_201127_a(☃, ☃, ☃, ☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c, ☃xx, ☃.field_70177_z, ☃.field_70125_A, ☃);
         } else {
            func_201127_a(☃, ☃, ☃, ☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c, ☃xx, ☃x.field_189983_j, ☃x.field_189982_i, ☃);
         }
      }

      if (☃.size() == 1) {
         ☃.func_197030_a(
            new TextComponentTranslation(
               "commands.teleport.success.location.single", ((Entity)☃.iterator().next()).func_145748_c_(), ☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c
            ),
            true
         );
      } else {
         ☃.func_197030_a(
            new TextComponentTranslation("commands.teleport.success.location.multiple", ☃.size(), ☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c), true
         );
      }

      return ☃.size();
   }

   private static void func_201127_a(
      CommandSource var0,
      Entity var1,
      WorldServer var2,
      double var3,
      double var5,
      double var7,
      Set<SPacketPlayerPosLook.EnumFlags> var9,
      float var10,
      float var11,
      @Nullable TeleportCommand.Facing var12
   ) {
      if (☃ instanceof EntityPlayerMP) {
         ☃.func_184210_p();
         if (((EntityPlayerMP)☃).func_70608_bn()) {
            ((EntityPlayerMP)☃).func_70999_a(true, true, false);
         }

         if (☃ == ☃.field_70170_p) {
            ((EntityPlayerMP)☃).field_71135_a.func_175089_a(☃, ☃, ☃, ☃, ☃, ☃);
         } else {
            ((EntityPlayerMP)☃).func_200619_a(☃, ☃, ☃, ☃, ☃, ☃);
         }

         ☃.func_70034_d(☃);
      } else {
         float ☃ = MathHelper.func_76142_g(☃);
         float ☃x = MathHelper.func_76142_g(☃);
         ☃x = MathHelper.func_76131_a(☃x, -90.0F, 90.0F);
         if (☃ == ☃.field_70170_p) {
            ☃.func_70012_b(☃, ☃, ☃, ☃, ☃x);
            ☃.func_70034_d(☃);
         } else {
            WorldServer ☃ = (WorldServer)☃.field_70170_p;
            ☃.func_72900_e(☃);
            ☃.field_71093_bK = ☃.field_73011_w.func_186058_p();
            ☃.field_70128_L = false;
            Entity ☃x = ☃;
            ☃ = ☃.func_200600_R().func_200721_a(☃);
            if (☃ == null) {
               return;
            }

            ☃.func_180432_n(☃x);
            ☃.func_70012_b(☃, ☃, ☃, ☃, ☃x);
            ☃.func_70034_d(☃);
            boolean ☃ = ☃.field_98038_p;
            ☃.field_98038_p = true;
            ☃.func_72838_d(☃);
            ☃.field_98038_p = ☃;
            ☃.func_72866_a(☃, false);
            ☃x.field_70128_L = true;
         }
      }

      if (☃ != null) {
         ☃.func_201124_a(☃, ☃);
      }

      if (!(☃ instanceof EntityLivingBase) || !((EntityLivingBase)☃).func_184613_cA()) {
         ☃.field_70181_x = 0.0;
         ☃.field_70122_E = true;
      }
   }

   static class Facing {
      private final Vec3d field_200549_a;
      private final Entity field_200550_b;
      private final EntityAnchorArgument.Type field_201125_c;

      public Facing(Entity var1, EntityAnchorArgument.Type var2) {
         this.field_200550_b = ☃;
         this.field_201125_c = ☃;
         this.field_200549_a = ☃.func_201017_a(☃);
      }

      public Facing(Vec3d var1) {
         this.field_200550_b = null;
         this.field_200549_a = ☃;
         this.field_201125_c = null;
      }

      public void func_201124_a(CommandSource var1, Entity var2) {
         if (this.field_200550_b != null) {
            if (☃ instanceof EntityPlayerMP) {
               ((EntityPlayerMP)☃).func_200618_a(☃.func_201008_k(), this.field_200550_b, this.field_201125_c);
            } else {
               ☃.func_200602_a(☃.func_201008_k(), this.field_200549_a);
            }
         } else {
            ☃.func_200602_a(☃.func_201008_k(), this.field_200549_a);
         }
      }
   }
}
