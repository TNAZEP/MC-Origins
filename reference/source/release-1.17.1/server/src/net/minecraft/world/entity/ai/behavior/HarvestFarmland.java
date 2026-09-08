package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;

public class HarvestFarmland extends Behavior<Villager> {
   private static final int HARVEST_DURATION = 200;
   public static final float SPEED_MODIFIER = 0.5F;
   @Nullable
   private BlockPos aboveFarmlandPos;
   private long nextOkStartTime;
   private int timeWorkedSoFar;
   private final List<BlockPos> validFarmlandAroundVillager = Lists.<BlockPos>newArrayList();

   public HarvestFarmland() {
      super(
         ImmutableMap.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryStatus.VALUE_ABSENT,
            MemoryModuleType.WALK_TARGET,
            MemoryStatus.VALUE_ABSENT,
            MemoryModuleType.SECONDARY_JOB_SITE,
            MemoryStatus.VALUE_PRESENT
         )
      );
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, Villager var2) {
      if (!â˜ƒ.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
         return false;
      } else if (â˜ƒ.getVillagerData().getProfession() != VillagerProfession.FARMER) {
         return false;
      } else {
         BlockPos.MutableBlockPos â˜ƒ = â˜ƒ.blockPosition().mutable();
         this.validFarmlandAroundVillager.clear();

         for(int â˜ƒx = -1; â˜ƒx <= 1; ++â˜ƒx) {
            for(int â˜ƒxx = -1; â˜ƒxx <= 1; ++â˜ƒxx) {
               for(int â˜ƒxxx = -1; â˜ƒxxx <= 1; ++â˜ƒxxx) {
                  â˜ƒ.set(â˜ƒ.getX() + (double)â˜ƒx, â˜ƒ.getY() + (double)â˜ƒxx, â˜ƒ.getZ() + (double)â˜ƒxxx);
                  if (this.validPos(â˜ƒ, â˜ƒ)) {
                     this.validFarmlandAroundVillager.add(new BlockPos(â˜ƒ));
                  }
               }
            }
         }

         this.aboveFarmlandPos = this.getValidFarmland(â˜ƒ);
         return this.aboveFarmlandPos != null;
      }
   }

   @Nullable
   private BlockPos getValidFarmland(ServerLevel var1) {
      return this.validFarmlandAroundVillager.isEmpty()
         ? null
         : (BlockPos)this.validFarmlandAroundVillager.get(â˜ƒ.getRandom().nextInt(this.validFarmlandAroundVillager.size()));
   }

   private boolean validPos(BlockPos var1, ServerLevel var2) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      Block â˜ƒx = â˜ƒ.getBlock();
      Block â˜ƒxx = â˜ƒ.getBlockState(â˜ƒ.below()).getBlock();
      return â˜ƒx instanceof CropBlock && ((CropBlock)â˜ƒx).isMaxAge(â˜ƒ) || â˜ƒ.isAir() && â˜ƒxx instanceof FarmBlock;
   }

   protected void start(ServerLevel var1, Villager var2, long var3) {
      if (â˜ƒ > this.nextOkStartTime && this.aboveFarmlandPos != null) {
         â˜ƒ.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosTracker(this.aboveFarmlandPos));
         â˜ƒ.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new BlockPosTracker(this.aboveFarmlandPos), 0.5F, 1));
      }
   }

   protected void stop(ServerLevel var1, Villager var2, long var3) {
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
      this.timeWorkedSoFar = 0;
      this.nextOkStartTime = â˜ƒ + 40L;
   }

   protected void tick(ServerLevel var1, Villager var2, long var3) {
      if (this.aboveFarmlandPos == null || this.aboveFarmlandPos.closerThan(â˜ƒ.position(), 1.0)) {
         if (this.aboveFarmlandPos != null && â˜ƒ > this.nextOkStartTime) {
            BlockState â˜ƒ = â˜ƒ.getBlockState(this.aboveFarmlandPos);
            Block â˜ƒx = â˜ƒ.getBlock();
            Block â˜ƒxx = â˜ƒ.getBlockState(this.aboveFarmlandPos.below()).getBlock();
            if (â˜ƒx instanceof CropBlock && ((CropBlock)â˜ƒx).isMaxAge(â˜ƒ)) {
               â˜ƒ.destroyBlock(this.aboveFarmlandPos, true, â˜ƒ);
            }

            if (â˜ƒ.isAir() && â˜ƒxx instanceof FarmBlock && â˜ƒ.hasFarmSeeds()) {
               SimpleContainer â˜ƒ = â˜ƒ.getInventory();

               for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.getContainerSize(); ++â˜ƒx) {
                  ItemStack â˜ƒxx = â˜ƒ.getItem(â˜ƒx);
                  boolean â˜ƒxxx = false;
                  if (!â˜ƒxx.isEmpty()) {
                     if (â˜ƒxx.is(Items.WHEAT_SEEDS)) {
                        â˜ƒ.setBlock(this.aboveFarmlandPos, Blocks.WHEAT.defaultBlockState(), 3);
                        â˜ƒxxx = true;
                     } else if (â˜ƒxx.is(Items.POTATO)) {
                        â˜ƒ.setBlock(this.aboveFarmlandPos, Blocks.POTATOES.defaultBlockState(), 3);
                        â˜ƒxxx = true;
                     } else if (â˜ƒxx.is(Items.CARROT)) {
                        â˜ƒ.setBlock(this.aboveFarmlandPos, Blocks.CARROTS.defaultBlockState(), 3);
                        â˜ƒxxx = true;
                     } else if (â˜ƒxx.is(Items.BEETROOT_SEEDS)) {
                        â˜ƒ.setBlock(this.aboveFarmlandPos, Blocks.BEETROOTS.defaultBlockState(), 3);
                        â˜ƒxxx = true;
                     }
                  }

                  if (â˜ƒxxx) {
                     â˜ƒ.playSound(
                        null,
                        (double)this.aboveFarmlandPos.getX(),
                        (double)this.aboveFarmlandPos.getY(),
                        (double)this.aboveFarmlandPos.getZ(),
                        SoundEvents.CROP_PLANTED,
                        SoundSource.BLOCKS,
                        1.0F,
                        1.0F
                     );
                     â˜ƒxx.shrink(1);
                     if (â˜ƒxx.isEmpty()) {
                        â˜ƒ.setItem(â˜ƒx, ItemStack.EMPTY);
                     }
                     break;
                  }
               }
            }

            if (â˜ƒx instanceof CropBlock && !((CropBlock)â˜ƒx).isMaxAge(â˜ƒ)) {
               this.validFarmlandAroundVillager.remove(this.aboveFarmlandPos);
               this.aboveFarmlandPos = this.getValidFarmland(â˜ƒ);
               if (this.aboveFarmlandPos != null) {
                  this.nextOkStartTime = â˜ƒ + 20L;
                  â˜ƒ.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new BlockPosTracker(this.aboveFarmlandPos), 0.5F, 1));
                  â˜ƒ.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosTracker(this.aboveFarmlandPos));
               }
            }
         }

         ++this.timeWorkedSoFar;
      }
   }

   protected boolean canStillUse(ServerLevel var1, Villager var2, long var3) {
      return this.timeWorkedSoFar < 200;
   }
}
