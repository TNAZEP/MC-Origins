package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.raid.Raids;

public class RaidCommand {
   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("raid")
            .requires(var0x -> var0x.hasPermission(3))
            .then(
               Commands.literal("start")
                  .then(
                     Commands.argument("omenlvl", IntegerArgumentType.integer(0))
                        .executes(var0x -> start(var0x.getSource(), IntegerArgumentType.getInteger(var0x, "omenlvl")))
                  )
            )
            .then(Commands.literal("stop").executes(var0x -> stop(var0x.getSource())))
            .then(Commands.literal("check").executes(var0x -> check(var0x.getSource())))
            .then(
               Commands.literal("sound")
                  .then(
                     Commands.argument("type", ComponentArgument.textComponent())
                        .executes(var0x -> playSound(var0x.getSource(), ComponentArgument.getComponent(var0x, "type")))
                  )
            )
            .then(Commands.literal("spawnleader").executes(var0x -> spawnLeader(var0x.getSource())))
            .then(
               Commands.literal("setomen")
                  .then(
                     Commands.argument("level", IntegerArgumentType.integer(0))
                        .executes(var0x -> setBadOmenLevel(var0x.getSource(), IntegerArgumentType.getInteger(var0x, "level")))
                  )
            )
            .then(Commands.literal("glow").executes(var0x -> glow(var0x.getSource())))
      );
   }

   private static int glow(CommandSourceStack var0) throws CommandSyntaxException {
      Raid â˜ƒ = getRaid(â˜ƒ.getPlayerOrException());
      if (â˜ƒ != null) {
         for(Raider â˜ƒx : â˜ƒ.getAllRaiders()) {
            â˜ƒx.addEffect(new MobEffectInstance(MobEffects.GLOWING, 1000, 1));
         }
      }

      return 1;
   }

   private static int setBadOmenLevel(CommandSourceStack var0, int var1) throws CommandSyntaxException {
      Raid â˜ƒ = getRaid(â˜ƒ.getPlayerOrException());
      if (â˜ƒ != null) {
         int â˜ƒx = â˜ƒ.getMaxBadOmenLevel();
         if (â˜ƒ > â˜ƒx) {
            â˜ƒ.sendFailure(new TextComponent("Sorry, the max bad omen level you can set is " + â˜ƒx));
         } else {
            int â˜ƒx = â˜ƒ.getBadOmenLevel();
            â˜ƒ.setBadOmenLevel(â˜ƒ);
            â˜ƒ.sendSuccess(new TextComponent("Changed village's bad omen level from " + â˜ƒx + " to " + â˜ƒ), false);
         }
      } else {
         â˜ƒ.sendFailure(new TextComponent("No raid found here"));
      }

      return 1;
   }

   private static int spawnLeader(CommandSourceStack var0) {
      â˜ƒ.sendSuccess(new TextComponent("Spawned a raid captain"), false);
      Raider â˜ƒ = EntityType.PILLAGER.create(â˜ƒ.getLevel());
      â˜ƒ.setPatrolLeader(true);
      â˜ƒ.setItemSlot(EquipmentSlot.HEAD, Raid.getLeaderBannerInstance());
      â˜ƒ.setPos(â˜ƒ.getPosition().x, â˜ƒ.getPosition().y, â˜ƒ.getPosition().z);
      â˜ƒ.finalizeSpawn(â˜ƒ.getLevel(), â˜ƒ.getLevel().getCurrentDifficultyAt(new BlockPos(â˜ƒ.getPosition())), MobSpawnType.COMMAND, null, null);
      â˜ƒ.getLevel().addFreshEntityWithPassengers(â˜ƒ);
      return 1;
   }

   private static int playSound(CommandSourceStack var0, Component var1) {
      if (â˜ƒ != null && â˜ƒ.getString().equals("local")) {
         â˜ƒ.getLevel().playSound(null, new BlockPos(â˜ƒ.getPosition().add(5.0, 0.0, 0.0)), SoundEvents.RAID_HORN, SoundSource.NEUTRAL, 2.0F, 1.0F);
      }

      return 1;
   }

   private static int start(CommandSourceStack var0, int var1) throws CommandSyntaxException {
      ServerPlayer â˜ƒ = â˜ƒ.getPlayerOrException();
      BlockPos â˜ƒx = â˜ƒ.blockPosition();
      if (â˜ƒ.getLevel().isRaided(â˜ƒx)) {
         â˜ƒ.sendFailure(new TextComponent("Raid already started close by"));
         return -1;
      } else {
         Raids â˜ƒ = â˜ƒ.getLevel().getRaids();
         Raid â˜ƒx = â˜ƒ.createOrExtendRaid(â˜ƒ);
         if (â˜ƒx != null) {
            â˜ƒx.setBadOmenLevel(â˜ƒ);
            â˜ƒ.setDirty();
            â˜ƒ.sendSuccess(new TextComponent("Created a raid in your local village"), false);
         } else {
            â˜ƒ.sendFailure(new TextComponent("Failed to create a raid in your local village"));
         }

         return 1;
      }
   }

   private static int stop(CommandSourceStack var0) throws CommandSyntaxException {
      ServerPlayer â˜ƒ = â˜ƒ.getPlayerOrException();
      BlockPos â˜ƒx = â˜ƒ.blockPosition();
      Raid â˜ƒxx = â˜ƒ.getLevel().getRaidAt(â˜ƒx);
      if (â˜ƒxx != null) {
         â˜ƒxx.stop();
         â˜ƒ.sendSuccess(new TextComponent("Stopped raid"), false);
         return 1;
      } else {
         â˜ƒ.sendFailure(new TextComponent("No raid here"));
         return -1;
      }
   }

   private static int check(CommandSourceStack var0) throws CommandSyntaxException {
      Raid â˜ƒ = getRaid(â˜ƒ.getPlayerOrException());
      if (â˜ƒ != null) {
         StringBuilder â˜ƒx = new StringBuilder();
         â˜ƒx.append("Found a started raid! ");
         â˜ƒ.sendSuccess(new TextComponent(â˜ƒx.toString()), false);
         â˜ƒx = new StringBuilder();
         â˜ƒx.append("Num groups spawned: ");
         â˜ƒx.append(â˜ƒ.getGroupsSpawned());
         â˜ƒx.append(" Bad omen level: ");
         â˜ƒx.append(â˜ƒ.getBadOmenLevel());
         â˜ƒx.append(" Num mobs: ");
         â˜ƒx.append(â˜ƒ.getTotalRaidersAlive());
         â˜ƒx.append(" Raid health: ");
         â˜ƒx.append(â˜ƒ.getHealthOfLivingRaiders());
         â˜ƒx.append(" / ");
         â˜ƒx.append(â˜ƒ.getTotalHealth());
         â˜ƒ.sendSuccess(new TextComponent(â˜ƒx.toString()), false);
         return 1;
      } else {
         â˜ƒ.sendFailure(new TextComponent("Found no started raids"));
         return 0;
      }
   }

   @Nullable
   private static Raid getRaid(ServerPlayer var0) {
      return â˜ƒ.getLevel().getRaidAt(â˜ƒ.blockPosition());
   }
}
