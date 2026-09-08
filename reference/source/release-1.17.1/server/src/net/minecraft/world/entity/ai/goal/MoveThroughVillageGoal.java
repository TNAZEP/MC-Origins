package net.minecraft.world.entity.ai.goal;

import com.google.common.collect.Lists;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class MoveThroughVillageGoal extends Goal {
   protected final PathfinderMob mob;
   private final double speedModifier;
   private Path path;
   private BlockPos poiPos;
   private final boolean onlyAtNight;
   private final List<BlockPos> visited = Lists.<BlockPos>newArrayList();
   private final int distanceToPoi;
   private final BooleanSupplier canDealWithDoors;

   public MoveThroughVillageGoal(PathfinderMob var1, double var2, boolean var4, int var5, BooleanSupplier var6) {
      this.mob = â˜ƒ;
      this.speedModifier = â˜ƒ;
      this.onlyAtNight = â˜ƒ;
      this.distanceToPoi = â˜ƒ;
      this.canDealWithDoors = â˜ƒ;
      this.setFlags(EnumSet.of(Goal.Flag.MOVE));
      if (!GoalUtils.hasGroundPathNavigation(â˜ƒ)) {
         throw new IllegalArgumentException("Unsupported mob for MoveThroughVillageGoal");
      }
   }

   @Override
   public boolean canUse() {
      if (!GoalUtils.hasGroundPathNavigation(this.mob)) {
         return false;
      } else {
         this.updateVisited();
         if (this.onlyAtNight && this.mob.level.isDay()) {
            return false;
         } else {
            ServerLevel â˜ƒ = (ServerLevel)this.mob.level;
            BlockPos â˜ƒx = this.mob.blockPosition();
            if (!â˜ƒ.isCloseToVillage(â˜ƒx, 6)) {
               return false;
            } else {
               Vec3 â˜ƒ = LandRandomPos.getPos(this.mob, 15, 7, var3x -> {
                  if (!â˜ƒ.isVillage(var3x)) {
                     return Double.NEGATIVE_INFINITY;
                  } else {
                     Optional<BlockPos> â˜ƒ = â˜ƒ.getPoiManager().find(PoiType.ALL, this::hasNotVisited, var3x, 10, PoiManager.Occupancy.IS_OCCUPIED);
                     return !â˜ƒ.isPresent() ? Double.NEGATIVE_INFINITY : -((BlockPos)â˜ƒ.get()).distSqr(â˜ƒ);
                  }
               });
               if (â˜ƒ == null) {
                  return false;
               } else {
                  Optional<BlockPos> â˜ƒ = â˜ƒ.getPoiManager().find(PoiType.ALL, this::hasNotVisited, new BlockPos(â˜ƒ), 10, PoiManager.Occupancy.IS_OCCUPIED);
                  if (!â˜ƒ.isPresent()) {
                     return false;
                  } else {
                     this.poiPos = ((BlockPos)â˜ƒ.get()).immutable();
                     GroundPathNavigation â˜ƒ = (GroundPathNavigation)this.mob.getNavigation();
                     boolean â˜ƒx = â˜ƒ.canOpenDoors();
                     â˜ƒ.setCanOpenDoors(this.canDealWithDoors.getAsBoolean());
                     this.path = â˜ƒ.createPath(this.poiPos, 0);
                     â˜ƒ.setCanOpenDoors(â˜ƒx);
                     if (this.path == null) {
                        Vec3 â˜ƒxx = DefaultRandomPos.getPosTowards(this.mob, 10, 7, Vec3.atBottomCenterOf(this.poiPos), (float) (Math.PI / 2));
                        if (â˜ƒxx == null) {
                           return false;
                        }

                        â˜ƒ.setCanOpenDoors(this.canDealWithDoors.getAsBoolean());
                        this.path = this.mob.getNavigation().createPath(â˜ƒxx.x, â˜ƒxx.y, â˜ƒxx.z, 0);
                        â˜ƒ.setCanOpenDoors(â˜ƒx);
                        if (this.path == null) {
                           return false;
                        }
                     }

                     for(int â˜ƒ = 0; â˜ƒ < this.path.getNodeCount(); ++â˜ƒ) {
                        Node â˜ƒx = this.path.getNode(â˜ƒ);
                        BlockPos â˜ƒxx = new BlockPos(â˜ƒx.x, â˜ƒx.y + 1, â˜ƒx.z);
                        if (DoorBlock.isWoodenDoor(this.mob.level, â˜ƒxx)) {
                           this.path = this.mob.getNavigation().createPath((double)â˜ƒx.x, (double)â˜ƒx.y, (double)â˜ƒx.z, 0);
                           break;
                        }
                     }

                     return this.path != null;
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean canContinueToUse() {
      if (this.mob.getNavigation().isDone()) {
         return false;
      } else {
         return !this.poiPos.closerThan(this.mob.position(), (double)(this.mob.getBbWidth() + (float)this.distanceToPoi));
      }
   }

   @Override
   public void start() {
      this.mob.getNavigation().moveTo(this.path, this.speedModifier);
   }

   @Override
   public void stop() {
      if (this.mob.getNavigation().isDone() || this.poiPos.closerThan(this.mob.position(), (double)this.distanceToPoi)) {
         this.visited.add(this.poiPos);
      }
   }

   private boolean hasNotVisited(BlockPos var1) {
      for(BlockPos â˜ƒ : this.visited) {
         if (Objects.equals(â˜ƒ, â˜ƒ)) {
            return false;
         }
      }

      return true;
   }

   private void updateVisited() {
      if (this.visited.size() > 15) {
         this.visited.remove(0);
      }
   }
}
