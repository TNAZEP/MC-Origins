package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.commands.arguments.EntitySummonArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SummonCommand {
   private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType(new TranslatableComponent("commands.summon.failed"));
   private static final SimpleCommandExceptionType ERROR_DUPLICATE_UUID = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.summon.failed.uuid")
   );
   private static final SimpleCommandExceptionType INVALID_POSITION = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.summon.invalidPosition")
   );

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("summon")
            .requires(var0x -> var0x.hasPermission(2))
            .then(
               Commands.argument("entity", EntitySummonArgument.id())
                  .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                  .executes(
                     var0x -> spawnEntity(
                           var0x.getSource(),
                           EntitySummonArgument.getSummonableEntity(var0x, "entity"),
                           var0x.getSource().getPosition(),
                           new CompoundTag(),
                           true
                        )
                  )
                  .then(
                     Commands.argument("pos", Vec3Argument.vec3())
                        .executes(
                           var0x -> spawnEntity(
                                 var0x.getSource(),
                                 EntitySummonArgument.getSummonableEntity(var0x, "entity"),
                                 Vec3Argument.getVec3(var0x, "pos"),
                                 new CompoundTag(),
                                 true
                              )
                        )
                        .then(
                           Commands.argument("nbt", CompoundTagArgument.compoundTag())
                              .executes(
                                 var0x -> spawnEntity(
                                       var0x.getSource(),
                                       EntitySummonArgument.getSummonableEntity(var0x, "entity"),
                                       Vec3Argument.getVec3(var0x, "pos"),
                                       CompoundTagArgument.getCompoundTag(var0x, "nbt"),
                                       false
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int spawnEntity(CommandSourceStack var0, ResourceLocation var1, Vec3 var2, CompoundTag var3, boolean var4) throws CommandSyntaxException {
      BlockPos â˜ƒ = new BlockPos(â˜ƒ);
      if (!Level.isInSpawnableBounds(â˜ƒ)) {
         throw INVALID_POSITION.create();
      } else {
         CompoundTag â˜ƒ = â˜ƒ.copy();
         â˜ƒ.putString("id", â˜ƒ.toString());
         ServerLevel â˜ƒx = â˜ƒ.getLevel();
         Entity â˜ƒxx = EntityType.loadEntityRecursive(â˜ƒ, â˜ƒx, var1x -> {
            var1x.moveTo(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z, var1x.getYRot(), var1x.getXRot());
            return var1x;
         });
         if (â˜ƒxx == null) {
            throw ERROR_FAILED.create();
         } else {
            if (â˜ƒ && â˜ƒxx instanceof Mob) {
               ((Mob)â˜ƒxx).finalizeSpawn(â˜ƒ.getLevel(), â˜ƒ.getLevel().getCurrentDifficultyAt(â˜ƒxx.blockPosition()), MobSpawnType.COMMAND, null, null);
            }

            if (!â˜ƒx.tryAddFreshEntityWithPassengers(â˜ƒxx)) {
               throw ERROR_DUPLICATE_UUID.create();
            } else {
               â˜ƒ.sendSuccess(new TranslatableComponent("commands.summon.success", â˜ƒxx.getDisplayName()), true);
               return 1;
            }
         }
      }
   }
}
