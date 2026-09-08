package net.minecraft.world.entity.animal;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public abstract class WaterAnimal extends PathfinderMob {
   protected WaterAnimal(EntityType<? extends WaterAnimal> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
   }

   @Override
   public boolean canBreatheUnderwater() {
      return true;
   }

   @Override
   public MobType getMobType() {
      return MobType.WATER;
   }

   @Override
   public boolean checkSpawnObstruction(LevelReader var1) {
      return â˜ƒ.isUnobstructed(this);
   }

   @Override
   public int getAmbientSoundInterval() {
      return 120;
   }

   @Override
   protected int getExperienceReward(Player var1) {
      return 1 + this.level.random.nextInt(3);
   }

   protected void handleAirSupply(int var1) {
      if (this.isAlive() && !this.isInWaterOrBubble()) {
         this.setAirSupply(â˜ƒ - 1);
         if (this.getAirSupply() == -20) {
            this.setAirSupply(0);
            this.hurt(DamageSource.DROWN, 2.0F);
         }
      } else {
         this.setAirSupply(300);
      }
   }

   @Override
   public void baseTick() {
      int â˜ƒ = this.getAirSupply();
      super.baseTick();
      this.handleAirSupply(â˜ƒ);
   }

   @Override
   public boolean isPushedByFluid() {
      return false;
   }

   @Override
   public boolean canBeLeashed(Player var1) {
      return false;
   }

   public static boolean checkUndergroundWaterCreatureSpawnRules(
      EntityType<? extends LivingEntity> var0, ServerLevelAccessor var1, MobSpawnType var2, BlockPos var3, Random var4
   ) {
      return â˜ƒ.getY() < â˜ƒ.getSeaLevel()
         && â˜ƒ.getY() < â˜ƒ.getHeight(Heightmap.Types.OCEAN_FLOOR, â˜ƒ.getX(), â˜ƒ.getZ())
         && isDarkEnoughToSpawn(â˜ƒ, â˜ƒ)
         && isBaseStoneBelow(â˜ƒ, â˜ƒ);
   }

   public static boolean isBaseStoneBelow(BlockPos var0, ServerLevelAccessor var1) {
      BlockPos.MutableBlockPos â˜ƒ = â˜ƒ.mutable();

      for(int â˜ƒx = 0; â˜ƒx < 5; ++â˜ƒx) {
         â˜ƒ.move(Direction.DOWN);
         BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒ);
         if (â˜ƒxx.is(BlockTags.BASE_STONE_OVERWORLD)) {
            return true;
         }

         if (!â˜ƒxx.is(Blocks.WATER)) {
            return false;
         }
      }

      return false;
   }

   public static boolean isDarkEnoughToSpawn(ServerLevelAccessor var0, BlockPos var1) {
      int â˜ƒ = â˜ƒ.getLevel().isThundering() ? â˜ƒ.getMaxLocalRawBrightness(â˜ƒ, 10) : â˜ƒ.getMaxLocalRawBrightness(â˜ƒ);
      return â˜ƒ == 0;
   }
}
