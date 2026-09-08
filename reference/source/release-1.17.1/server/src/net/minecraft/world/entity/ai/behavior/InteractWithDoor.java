package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;

public class InteractWithDoor extends Behavior<LivingEntity> {
   private static final int COOLDOWN_BEFORE_RERUNNING_IN_SAME_NODE = 20;
   private static final double SKIP_CLOSING_DOOR_IF_FURTHER_AWAY_THAN = 2.0;
   private static final double MAX_DISTANCE_TO_HOLD_DOOR_OPEN_FOR_OTHER_MOBS = 2.0;
   @Nullable
   private Node lastCheckedNode;
   private int remainingCooldown;

   public InteractWithDoor() {
      super(ImmutableMap.of(MemoryModuleType.PATH, MemoryStatus.VALUE_PRESENT, MemoryModuleType.DOORS_TO_CLOSE, MemoryStatus.REGISTERED));
   }

   @Override
   protected boolean checkExtraStartConditions(ServerLevel var1, LivingEntity var2) {
      Path â˜ƒ = (Path)â˜ƒ.getBrain().getMemory(MemoryModuleType.PATH).get();
      if (!â˜ƒ.notStarted() && !â˜ƒ.isDone()) {
         if (!Objects.equals(this.lastCheckedNode, â˜ƒ.getNextNode())) {
            this.remainingCooldown = 20;
            return true;
         } else {
            if (this.remainingCooldown > 0) {
               --this.remainingCooldown;
            }

            return this.remainingCooldown == 0;
         }
      } else {
         return false;
      }
   }

   @Override
   protected void start(ServerLevel var1, LivingEntity var2, long var3) {
      Path â˜ƒ = (Path)â˜ƒ.getBrain().getMemory(MemoryModuleType.PATH).get();
      this.lastCheckedNode = â˜ƒ.getNextNode();
      Node â˜ƒx = â˜ƒ.getPreviousNode();
      Node â˜ƒxx = â˜ƒ.getNextNode();
      BlockPos â˜ƒxxx = â˜ƒx.asBlockPos();
      BlockState â˜ƒxxxx = â˜ƒ.getBlockState(â˜ƒxxx);
      if (â˜ƒxxxx.is(BlockTags.WOODEN_DOORS)) {
         DoorBlock â˜ƒxxxxx = (DoorBlock)â˜ƒxxxx.getBlock();
         if (!â˜ƒxxxxx.isOpen(â˜ƒxxxx)) {
            â˜ƒxxxxx.setOpen(â˜ƒ, â˜ƒ, â˜ƒxxxx, â˜ƒxxx, true);
         }

         this.rememberDoorToClose(â˜ƒ, â˜ƒ, â˜ƒxxx);
      }

      BlockPos â˜ƒ = â˜ƒxx.asBlockPos();
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
      if (â˜ƒx.is(BlockTags.WOODEN_DOORS)) {
         DoorBlock â˜ƒxx = (DoorBlock)â˜ƒx.getBlock();
         if (!â˜ƒxx.isOpen(â˜ƒx)) {
            â˜ƒxx.setOpen(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, true);
            this.rememberDoorToClose(â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }

      closeDoorsThatIHaveOpenedOrPassedThrough(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx);
   }

   public static void closeDoorsThatIHaveOpenedOrPassedThrough(ServerLevel var0, LivingEntity var1, @Nullable Node var2, @Nullable Node var3) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      if (â˜ƒ.hasMemoryValue(MemoryModuleType.DOORS_TO_CLOSE)) {
         Iterator<GlobalPos> â˜ƒx = ((Set)â˜ƒ.getMemory(MemoryModuleType.DOORS_TO_CLOSE).get()).iterator();

         while(â˜ƒx.hasNext()) {
            GlobalPos â˜ƒxx = (GlobalPos)â˜ƒx.next();
            BlockPos â˜ƒxxx = â˜ƒxx.pos();
            if ((â˜ƒ == null || !â˜ƒ.asBlockPos().equals(â˜ƒxxx)) && (â˜ƒ == null || !â˜ƒ.asBlockPos().equals(â˜ƒxxx))) {
               if (isDoorTooFarAway(â˜ƒ, â˜ƒ, â˜ƒxx)) {
                  â˜ƒx.remove();
               } else {
                  BlockState â˜ƒxxxx = â˜ƒ.getBlockState(â˜ƒxxx);
                  if (!â˜ƒxxxx.is(BlockTags.WOODEN_DOORS)) {
                     â˜ƒx.remove();
                  } else {
                     DoorBlock â˜ƒxxxx = (DoorBlock)â˜ƒxxxx.getBlock();
                     if (!â˜ƒxxxx.isOpen(â˜ƒxxxx)) {
                        â˜ƒx.remove();
                     } else if (areOtherMobsComingThroughDoor(â˜ƒ, â˜ƒ, â˜ƒxxx)) {
                        â˜ƒx.remove();
                     } else {
                        â˜ƒxxxx.setOpen(â˜ƒ, â˜ƒ, â˜ƒxxxx, â˜ƒxxx, false);
                        â˜ƒx.remove();
                     }
                  }
               }
            }
         }
      }
   }

   private static boolean areOtherMobsComingThroughDoor(ServerLevel var0, LivingEntity var1, BlockPos var2) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      return !â˜ƒ.hasMemoryValue(MemoryModuleType.NEAREST_LIVING_ENTITIES)
         ? false
         : ((List)â˜ƒ.getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES).get())
            .stream()
            .filter(var1x -> var1x.getType() == â˜ƒ.getType())
            .filter(var1x -> â˜ƒ.closerThan(var1x.position(), 2.0))
            .anyMatch(var2x -> isMobComingThroughDoor(â˜ƒ, var2x, â˜ƒ));
   }

   private static boolean isMobComingThroughDoor(ServerLevel var0, LivingEntity var1, BlockPos var2) {
      if (!â˜ƒ.getBrain().hasMemoryValue(MemoryModuleType.PATH)) {
         return false;
      } else {
         Path â˜ƒ = (Path)â˜ƒ.getBrain().getMemory(MemoryModuleType.PATH).get();
         if (â˜ƒ.isDone()) {
            return false;
         } else {
            Node â˜ƒ = â˜ƒ.getPreviousNode();
            if (â˜ƒ == null) {
               return false;
            } else {
               Node â˜ƒ = â˜ƒ.getNextNode();
               return â˜ƒ.equals(â˜ƒ.asBlockPos()) || â˜ƒ.equals(â˜ƒ.asBlockPos());
            }
         }
      }
   }

   private static boolean isDoorTooFarAway(ServerLevel var0, LivingEntity var1, GlobalPos var2) {
      return â˜ƒ.dimension() != â˜ƒ.dimension() || !â˜ƒ.pos().closerThan(â˜ƒ.position(), 2.0);
   }

   private void rememberDoorToClose(ServerLevel var1, LivingEntity var2, BlockPos var3) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      GlobalPos â˜ƒx = GlobalPos.of(â˜ƒ.dimension(), â˜ƒ);
      if (â˜ƒ.getMemory(MemoryModuleType.DOORS_TO_CLOSE).isPresent()) {
         ((Set)â˜ƒ.getMemory(MemoryModuleType.DOORS_TO_CLOSE).get()).add(â˜ƒx);
      } else {
         â˜ƒ.setMemory(MemoryModuleType.DOORS_TO_CLOSE, Sets.<GlobalPos>newHashSet(â˜ƒx));
      }
   }
}
