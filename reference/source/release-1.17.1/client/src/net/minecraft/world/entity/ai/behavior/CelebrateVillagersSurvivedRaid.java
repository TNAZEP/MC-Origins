package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CelebrateVillagersSurvivedRaid extends Behavior<Villager> {
   @Nullable
   private Raid currentRaid;

   public CelebrateVillagersSurvivedRaid(int var1, int var2) {
      super(ImmutableMap.of(), â˜ƒ, â˜ƒ);
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, Villager var2) {
      BlockPos â˜ƒ = â˜ƒ.blockPosition();
      this.currentRaid = â˜ƒ.getRaidAt(â˜ƒ);
      return this.currentRaid != null && this.currentRaid.isVictory() && MoveToSkySeeingSpot.hasNoBlocksAbove(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   protected boolean canStillUse(ServerLevel var1, Villager var2, long var3) {
      return this.currentRaid != null && !this.currentRaid.isStopped();
   }

   protected void stop(ServerLevel var1, Villager var2, long var3) {
      this.currentRaid = null;
      â˜ƒ.getBrain().updateActivityFromSchedule(â˜ƒ.getDayTime(), â˜ƒ.getGameTime());
   }

   protected void tick(ServerLevel var1, Villager var2, long var3) {
      Random â˜ƒ = â˜ƒ.getRandom();
      if (â˜ƒ.nextInt(100) == 0) {
         â˜ƒ.playCelebrateSound();
      }

      if (â˜ƒ.nextInt(200) == 0 && MoveToSkySeeingSpot.hasNoBlocksAbove(â˜ƒ, â˜ƒ, â˜ƒ.blockPosition())) {
         DyeColor â˜ƒ = Util.getRandom(DyeColor.values(), â˜ƒ);
         int â˜ƒx = â˜ƒ.nextInt(3);
         ItemStack â˜ƒxx = this.getFirework(â˜ƒ, â˜ƒx);
         FireworkRocketEntity â˜ƒxxx = new FireworkRocketEntity(â˜ƒ.level, â˜ƒ, â˜ƒ.getX(), â˜ƒ.getEyeY(), â˜ƒ.getZ(), â˜ƒxx);
         â˜ƒ.level.addFreshEntity(â˜ƒxxx);
      }
   }

   private ItemStack getFirework(DyeColor var1, int var2) {
      ItemStack â˜ƒ = new ItemStack(Items.FIREWORK_ROCKET, 1);
      ItemStack â˜ƒx = new ItemStack(Items.FIREWORK_STAR);
      CompoundTag â˜ƒxx = â˜ƒx.getOrCreateTagElement("Explosion");
      List<Integer> â˜ƒxxx = Lists.newArrayList();
      â˜ƒxxx.add(â˜ƒ.getFireworkColor());
      â˜ƒxx.putIntArray("Colors", â˜ƒxxx);
      â˜ƒxx.putByte("Type", (byte)FireworkRocketItem.Shape.BURST.getId());
      CompoundTag â˜ƒxxxx = â˜ƒ.getOrCreateTagElement("Fireworks");
      ListTag â˜ƒxxxxx = new ListTag();
      CompoundTag â˜ƒxxxxxx = â˜ƒx.getTagElement("Explosion");
      if (â˜ƒxxxxxx != null) {
         â˜ƒxxxxx.add(â˜ƒxxxxxx);
      }

      â˜ƒxxxx.putByte("Flight", (byte)â˜ƒ);
      if (!â˜ƒxxxxx.isEmpty()) {
         â˜ƒxxxx.put("Explosions", â˜ƒxxxxx);
      }

      return â˜ƒ;
   }
}
