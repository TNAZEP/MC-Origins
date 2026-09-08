package net.minecraft.world.entity.animal.horse;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class SkeletonTrapGoal extends Goal {
   private final SkeletonHorse horse;

   public SkeletonTrapGoal(SkeletonHorse var1) {
      this.horse = â˜ƒ;
   }

   @Override
   public boolean canUse() {
      return this.horse.level.hasNearbyAlivePlayer(this.horse.getX(), this.horse.getY(), this.horse.getZ(), 10.0);
   }

   @Override
   public void tick() {
      ServerLevel â˜ƒ = (ServerLevel)this.horse.level;
      DifficultyInstance â˜ƒx = â˜ƒ.getCurrentDifficultyAt(this.horse.blockPosition());
      this.horse.setTrap(false);
      this.horse.setTamed(true);
      this.horse.setAge(0);
      LightningBolt â˜ƒxx = EntityType.LIGHTNING_BOLT.create(â˜ƒ);
      â˜ƒxx.moveTo(this.horse.getX(), this.horse.getY(), this.horse.getZ());
      â˜ƒxx.setVisualOnly(true);
      â˜ƒ.addFreshEntity(â˜ƒxx);
      Skeleton â˜ƒxxx = this.createSkeleton(â˜ƒx, this.horse);
      â˜ƒxxx.startRiding(this.horse);
      â˜ƒ.addFreshEntityWithPassengers(â˜ƒxxx);

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < 3; ++â˜ƒxxxx) {
         AbstractHorse â˜ƒxxxxx = this.createHorse(â˜ƒx);
         Skeleton â˜ƒxxxxxx = this.createSkeleton(â˜ƒx, â˜ƒxxxxx);
         â˜ƒxxxxxx.startRiding(â˜ƒxxxxx);
         â˜ƒxxxxx.push(this.horse.getRandom().nextGaussian() * 0.5, 0.0, this.horse.getRandom().nextGaussian() * 0.5);
         â˜ƒ.addFreshEntityWithPassengers(â˜ƒxxxxx);
      }
   }

   private AbstractHorse createHorse(DifficultyInstance var1) {
      SkeletonHorse â˜ƒ = EntityType.SKELETON_HORSE.create(this.horse.level);
      â˜ƒ.finalizeSpawn((ServerLevel)this.horse.level, â˜ƒ, MobSpawnType.TRIGGERED, null, null);
      â˜ƒ.setPos(this.horse.getX(), this.horse.getY(), this.horse.getZ());
      â˜ƒ.invulnerableTime = 60;
      â˜ƒ.setPersistenceRequired();
      â˜ƒ.setTamed(true);
      â˜ƒ.setAge(0);
      return â˜ƒ;
   }

   private Skeleton createSkeleton(DifficultyInstance var1, AbstractHorse var2) {
      Skeleton â˜ƒ = EntityType.SKELETON.create(â˜ƒ.level);
      â˜ƒ.finalizeSpawn((ServerLevel)â˜ƒ.level, â˜ƒ, MobSpawnType.TRIGGERED, null, null);
      â˜ƒ.setPos(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
      â˜ƒ.invulnerableTime = 60;
      â˜ƒ.setPersistenceRequired();
      if (â˜ƒ.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
         â˜ƒ.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
      }

      â˜ƒ.setItemSlot(
         EquipmentSlot.MAINHAND,
         EnchantmentHelper.enchantItem(
            â˜ƒ.getRandom(), this.disenchant(â˜ƒ.getMainHandItem()), (int)(5.0F + â˜ƒ.getSpecialMultiplier() * (float)â˜ƒ.getRandom().nextInt(18)), false
         )
      );
      â˜ƒ.setItemSlot(
         EquipmentSlot.HEAD,
         EnchantmentHelper.enchantItem(
            â˜ƒ.getRandom(),
            this.disenchant(â˜ƒ.getItemBySlot(EquipmentSlot.HEAD)),
            (int)(5.0F + â˜ƒ.getSpecialMultiplier() * (float)â˜ƒ.getRandom().nextInt(18)),
            false
         )
      );
      return â˜ƒ;
   }

   private ItemStack disenchant(ItemStack var1) {
      â˜ƒ.removeTagKey("Enchantments");
      return â˜ƒ;
   }
}
