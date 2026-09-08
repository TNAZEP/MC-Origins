package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

public class UseBonemeal extends Behavior<Villager> {
   private static final int BONEMEALING_DURATION = 80;
   private long nextWorkCycleTime;
   private long lastBonemealingSession;
   private int timeWorkedSoFar;
   private Optional<BlockPos> cropPos = Optional.empty();

   public UseBonemeal() {
      super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT));
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, Villager var2) {
      if (â˜ƒ.tickCount % 10 == 0 && (this.lastBonemealingSession == 0L || this.lastBonemealingSession + 160L <= (long)â˜ƒ.tickCount)) {
         if (â˜ƒ.getInventory().countItem(Items.BONE_MEAL) <= 0) {
            return false;
         } else {
            this.cropPos = this.pickNextTarget(â˜ƒ, â˜ƒ);
            return this.cropPos.isPresent();
         }
      } else {
         return false;
      }
   }

   protected boolean canStillUse(ServerLevel var1, Villager var2, long var3) {
      return this.timeWorkedSoFar < 80 && this.cropPos.isPresent();
   }

   private Optional<BlockPos> pickNextTarget(ServerLevel var1, Villager var2) {
      BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos();
      Optional<BlockPos> â˜ƒx = Optional.empty();
      int â˜ƒxx = 0;

      for(int â˜ƒxxx = -1; â˜ƒxxx <= 1; ++â˜ƒxxx) {
         for(int â˜ƒxxxx = -1; â˜ƒxxxx <= 1; ++â˜ƒxxxx) {
            for(int â˜ƒxxxxx = -1; â˜ƒxxxxx <= 1; ++â˜ƒxxxxx) {
               â˜ƒ.setWithOffset(â˜ƒ.blockPosition(), â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
               if (this.validPos(â˜ƒ, â˜ƒ)) {
                  if (â˜ƒ.random.nextInt(++â˜ƒxx) == 0) {
                     â˜ƒx = Optional.of(â˜ƒ.immutable());
                  }
               }
            }
         }
      }

      return â˜ƒx;
   }

   private boolean validPos(BlockPos var1, ServerLevel var2) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      Block â˜ƒx = â˜ƒ.getBlock();
      return â˜ƒx instanceof CropBlock && !((CropBlock)â˜ƒx).isMaxAge(â˜ƒ);
   }

   protected void start(ServerLevel var1, Villager var2, long var3) {
      this.setCurrentCropAsTarget(â˜ƒ);
      â˜ƒ.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BONE_MEAL));
      this.nextWorkCycleTime = â˜ƒ;
      this.timeWorkedSoFar = 0;
   }

   private void setCurrentCropAsTarget(Villager var1) {
      this.cropPos.ifPresent(var1x -> {
         BlockPosTracker â˜ƒ = new BlockPosTracker(var1x);
         â˜ƒ.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, â˜ƒ);
         â˜ƒ.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(â˜ƒ, 0.5F, 1));
      });
   }

   protected void stop(ServerLevel var1, Villager var2, long var3) {
      â˜ƒ.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
      this.lastBonemealingSession = (long)â˜ƒ.tickCount;
   }

   protected void tick(ServerLevel var1, Villager var2, long var3) {
      BlockPos â˜ƒ = (BlockPos)this.cropPos.get();
      if (â˜ƒ >= this.nextWorkCycleTime && â˜ƒ.closerThan(â˜ƒ.position(), 1.0)) {
         ItemStack â˜ƒx = ItemStack.EMPTY;
         SimpleContainer â˜ƒxx = â˜ƒ.getInventory();
         int â˜ƒxxx = â˜ƒxx.getContainerSize();

         for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒxxx; ++â˜ƒxxxx) {
            ItemStack â˜ƒxxxxx = â˜ƒxx.getItem(â˜ƒxxxx);
            if (â˜ƒxxxxx.is(Items.BONE_MEAL)) {
               â˜ƒx = â˜ƒxxxxx;
               break;
            }
         }

         if (!â˜ƒx.isEmpty() && BoneMealItem.growCrop(â˜ƒx, â˜ƒ, â˜ƒ)) {
            â˜ƒ.levelEvent(1505, â˜ƒ, 0);
            this.cropPos = this.pickNextTarget(â˜ƒ, â˜ƒ);
            this.setCurrentCropAsTarget(â˜ƒ);
            this.nextWorkCycleTime = â˜ƒ + 40L;
         }

         ++this.timeWorkedSoFar;
      }
   }
}
