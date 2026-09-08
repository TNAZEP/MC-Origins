package net.minecraft.world.level.block;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;

public class RailState {
   private final Level level;
   private final BlockPos pos;
   private final BaseRailBlock block;
   private BlockState state;
   private final boolean isStraight;
   private final List<BlockPos> connections = Lists.<BlockPos>newArrayList();

   public RailState(Level var1, BlockPos var2, BlockState var3) {
      this.level = â˜ƒ;
      this.pos = â˜ƒ;
      this.state = â˜ƒ;
      this.block = (BaseRailBlock)â˜ƒ.getBlock();
      RailShape â˜ƒ = â˜ƒ.getValue(this.block.getShapeProperty());
      this.isStraight = this.block.isStraight();
      this.updateConnections(â˜ƒ);
   }

   public List<BlockPos> getConnections() {
      return this.connections;
   }

   private void updateConnections(RailShape var1) {
      this.connections.clear();
      switch(â˜ƒ) {
         case NORTH_SOUTH:
            this.connections.add(this.pos.north());
            this.connections.add(this.pos.south());
            break;
         case EAST_WEST:
            this.connections.add(this.pos.west());
            this.connections.add(this.pos.east());
            break;
         case ASCENDING_EAST:
            this.connections.add(this.pos.west());
            this.connections.add(this.pos.east().above());
            break;
         case ASCENDING_WEST:
            this.connections.add(this.pos.west().above());
            this.connections.add(this.pos.east());
            break;
         case ASCENDING_NORTH:
            this.connections.add(this.pos.north().above());
            this.connections.add(this.pos.south());
            break;
         case ASCENDING_SOUTH:
            this.connections.add(this.pos.north());
            this.connections.add(this.pos.south().above());
            break;
         case SOUTH_EAST:
            this.connections.add(this.pos.east());
            this.connections.add(this.pos.south());
            break;
         case SOUTH_WEST:
            this.connections.add(this.pos.west());
            this.connections.add(this.pos.south());
            break;
         case NORTH_WEST:
            this.connections.add(this.pos.west());
            this.connections.add(this.pos.north());
            break;
         case NORTH_EAST:
            this.connections.add(this.pos.east());
            this.connections.add(this.pos.north());
      }
   }

   private void removeSoftConnections() {
      for(int â˜ƒ = 0; â˜ƒ < this.connections.size(); ++â˜ƒ) {
         RailState â˜ƒx = this.getRail((BlockPos)this.connections.get(â˜ƒ));
         if (â˜ƒx != null && â˜ƒx.connectsTo(this)) {
            this.connections.set(â˜ƒ, â˜ƒx.pos);
         } else {
            this.connections.remove(â˜ƒ--);
         }
      }
   }

   private boolean hasRail(BlockPos var1) {
      return BaseRailBlock.isRail(this.level, â˜ƒ) || BaseRailBlock.isRail(this.level, â˜ƒ.above()) || BaseRailBlock.isRail(this.level, â˜ƒ.below());
   }

   @Nullable
   private RailState getRail(BlockPos var1) {
      BlockState â˜ƒ = this.level.getBlockState(â˜ƒ);
      if (BaseRailBlock.isRail(â˜ƒ)) {
         return new RailState(this.level, â˜ƒ, â˜ƒ);
      } else {
         BlockPos var2 = â˜ƒ.above();
         â˜ƒ = this.level.getBlockState(var2);
         if (BaseRailBlock.isRail(â˜ƒ)) {
            return new RailState(this.level, var2, â˜ƒ);
         } else {
            var2 = â˜ƒ.below();
            â˜ƒ = this.level.getBlockState(var2);
            return BaseRailBlock.isRail(â˜ƒ) ? new RailState(this.level, var2, â˜ƒ) : null;
         }
      }
   }

   private boolean connectsTo(RailState var1) {
      return this.hasConnection(â˜ƒ.pos);
   }

   private boolean hasConnection(BlockPos var1) {
      for(int â˜ƒ = 0; â˜ƒ < this.connections.size(); ++â˜ƒ) {
         BlockPos â˜ƒx = (BlockPos)this.connections.get(â˜ƒ);
         if (â˜ƒx.getX() == â˜ƒ.getX() && â˜ƒx.getZ() == â˜ƒ.getZ()) {
            return true;
         }
      }

      return false;
   }

   protected int countPotentialConnections() {
      int â˜ƒ = 0;

      for(Direction â˜ƒx : Direction.Plane.HORIZONTAL) {
         if (this.hasRail(this.pos.relative(â˜ƒx))) {
            ++â˜ƒ;
         }
      }

      return â˜ƒ;
   }

   private boolean canConnectTo(RailState var1) {
      return this.connectsTo(â˜ƒ) || this.connections.size() != 2;
   }

   private void connectTo(RailState var1) {
      this.connections.add(â˜ƒ.pos);
      BlockPos â˜ƒ = this.pos.north();
      BlockPos â˜ƒx = this.pos.south();
      BlockPos â˜ƒxx = this.pos.west();
      BlockPos â˜ƒxxx = this.pos.east();
      boolean â˜ƒxxxx = this.hasConnection(â˜ƒ);
      boolean â˜ƒxxxxx = this.hasConnection(â˜ƒx);
      boolean â˜ƒxxxxxx = this.hasConnection(â˜ƒxx);
      boolean â˜ƒxxxxxxx = this.hasConnection(â˜ƒxxx);
      RailShape â˜ƒxxxxxxxx = null;
      if (â˜ƒxxxx || â˜ƒxxxxx) {
         â˜ƒxxxxxxxx = RailShape.NORTH_SOUTH;
      }

      if (â˜ƒxxxxxx || â˜ƒxxxxxxx) {
         â˜ƒxxxxxxxx = RailShape.EAST_WEST;
      }

      if (!this.isStraight) {
         if (â˜ƒxxxxx && â˜ƒxxxxxxx && !â˜ƒxxxx && !â˜ƒxxxxxx) {
            â˜ƒxxxxxxxx = RailShape.SOUTH_EAST;
         }

         if (â˜ƒxxxxx && â˜ƒxxxxxx && !â˜ƒxxxx && !â˜ƒxxxxxxx) {
            â˜ƒxxxxxxxx = RailShape.SOUTH_WEST;
         }

         if (â˜ƒxxxx && â˜ƒxxxxxx && !â˜ƒxxxxx && !â˜ƒxxxxxxx) {
            â˜ƒxxxxxxxx = RailShape.NORTH_WEST;
         }

         if (â˜ƒxxxx && â˜ƒxxxxxxx && !â˜ƒxxxxx && !â˜ƒxxxxxx) {
            â˜ƒxxxxxxxx = RailShape.NORTH_EAST;
         }
      }

      if (â˜ƒxxxxxxxx == RailShape.NORTH_SOUTH) {
         if (BaseRailBlock.isRail(this.level, â˜ƒ.above())) {
            â˜ƒxxxxxxxx = RailShape.ASCENDING_NORTH;
         }

         if (BaseRailBlock.isRail(this.level, â˜ƒx.above())) {
            â˜ƒxxxxxxxx = RailShape.ASCENDING_SOUTH;
         }
      }

      if (â˜ƒxxxxxxxx == RailShape.EAST_WEST) {
         if (BaseRailBlock.isRail(this.level, â˜ƒxxx.above())) {
            â˜ƒxxxxxxxx = RailShape.ASCENDING_EAST;
         }

         if (BaseRailBlock.isRail(this.level, â˜ƒxx.above())) {
            â˜ƒxxxxxxxx = RailShape.ASCENDING_WEST;
         }
      }

      if (â˜ƒxxxxxxxx == null) {
         â˜ƒxxxxxxxx = RailShape.NORTH_SOUTH;
      }

      this.state = this.state.setValue(this.block.getShapeProperty(), â˜ƒxxxxxxxx);
      this.level.setBlock(this.pos, this.state, 3);
   }

   private boolean hasNeighborRail(BlockPos var1) {
      RailState â˜ƒ = this.getRail(â˜ƒ);
      if (â˜ƒ == null) {
         return false;
      } else {
         â˜ƒ.removeSoftConnections();
         return â˜ƒ.canConnectTo(this);
      }
   }

   public RailState place(boolean var1, boolean var2, RailShape var3) {
      BlockPos â˜ƒ = this.pos.north();
      BlockPos â˜ƒx = this.pos.south();
      BlockPos â˜ƒxx = this.pos.west();
      BlockPos â˜ƒxxx = this.pos.east();
      boolean â˜ƒxxxx = this.hasNeighborRail(â˜ƒ);
      boolean â˜ƒxxxxx = this.hasNeighborRail(â˜ƒx);
      boolean â˜ƒxxxxxx = this.hasNeighborRail(â˜ƒxx);
      boolean â˜ƒxxxxxxx = this.hasNeighborRail(â˜ƒxxx);
      RailShape â˜ƒxxxxxxxx = null;
      boolean â˜ƒxxxxxxxxx = â˜ƒxxxx || â˜ƒxxxxx;
      boolean â˜ƒxxxxxxxxxx = â˜ƒxxxxxx || â˜ƒxxxxxxx;
      if (â˜ƒxxxxxxxxx && !â˜ƒxxxxxxxxxx) {
         â˜ƒxxxxxxxx = RailShape.NORTH_SOUTH;
      }

      if (â˜ƒxxxxxxxxxx && !â˜ƒxxxxxxxxx) {
         â˜ƒxxxxxxxx = RailShape.EAST_WEST;
      }

      boolean â˜ƒ = â˜ƒxxxxx && â˜ƒxxxxxxx;
      boolean â˜ƒx = â˜ƒxxxxx && â˜ƒxxxxxx;
      boolean â˜ƒxx = â˜ƒxxxx && â˜ƒxxxxxxx;
      boolean â˜ƒxxx = â˜ƒxxxx && â˜ƒxxxxxx;
      if (!this.isStraight) {
         if (â˜ƒ && !â˜ƒxxxx && !â˜ƒxxxxxx) {
            â˜ƒxxxxxxxx = RailShape.SOUTH_EAST;
         }

         if (â˜ƒx && !â˜ƒxxxx && !â˜ƒxxxxxxx) {
            â˜ƒxxxxxxxx = RailShape.SOUTH_WEST;
         }

         if (â˜ƒxxx && !â˜ƒxxxxx && !â˜ƒxxxxxxx) {
            â˜ƒxxxxxxxx = RailShape.NORTH_WEST;
         }

         if (â˜ƒxx && !â˜ƒxxxxx && !â˜ƒxxxxxx) {
            â˜ƒxxxxxxxx = RailShape.NORTH_EAST;
         }
      }

      if (â˜ƒxxxxxxxx == null) {
         if (â˜ƒxxxxxxxxx && â˜ƒxxxxxxxxxx) {
            â˜ƒxxxxxxxx = â˜ƒ;
         } else if (â˜ƒxxxxxxxxx) {
            â˜ƒxxxxxxxx = RailShape.NORTH_SOUTH;
         } else if (â˜ƒxxxxxxxxxx) {
            â˜ƒxxxxxxxx = RailShape.EAST_WEST;
         }

         if (!this.isStraight) {
            if (â˜ƒ) {
               if (â˜ƒ) {
                  â˜ƒxxxxxxxx = RailShape.SOUTH_EAST;
               }

               if (â˜ƒx) {
                  â˜ƒxxxxxxxx = RailShape.SOUTH_WEST;
               }

               if (â˜ƒxx) {
                  â˜ƒxxxxxxxx = RailShape.NORTH_EAST;
               }

               if (â˜ƒxxx) {
                  â˜ƒxxxxxxxx = RailShape.NORTH_WEST;
               }
            } else {
               if (â˜ƒxxx) {
                  â˜ƒxxxxxxxx = RailShape.NORTH_WEST;
               }

               if (â˜ƒxx) {
                  â˜ƒxxxxxxxx = RailShape.NORTH_EAST;
               }

               if (â˜ƒx) {
                  â˜ƒxxxxxxxx = RailShape.SOUTH_WEST;
               }

               if (â˜ƒ) {
                  â˜ƒxxxxxxxx = RailShape.SOUTH_EAST;
               }
            }
         }
      }

      if (â˜ƒxxxxxxxx == RailShape.NORTH_SOUTH) {
         if (BaseRailBlock.isRail(this.level, â˜ƒ.above())) {
            â˜ƒxxxxxxxx = RailShape.ASCENDING_NORTH;
         }

         if (BaseRailBlock.isRail(this.level, â˜ƒx.above())) {
            â˜ƒxxxxxxxx = RailShape.ASCENDING_SOUTH;
         }
      }

      if (â˜ƒxxxxxxxx == RailShape.EAST_WEST) {
         if (BaseRailBlock.isRail(this.level, â˜ƒxxx.above())) {
            â˜ƒxxxxxxxx = RailShape.ASCENDING_EAST;
         }

         if (BaseRailBlock.isRail(this.level, â˜ƒxx.above())) {
            â˜ƒxxxxxxxx = RailShape.ASCENDING_WEST;
         }
      }

      if (â˜ƒxxxxxxxx == null) {
         â˜ƒxxxxxxxx = â˜ƒ;
      }

      this.updateConnections(â˜ƒxxxxxxxx);
      this.state = this.state.setValue(this.block.getShapeProperty(), â˜ƒxxxxxxxx);
      if (â˜ƒ || this.level.getBlockState(this.pos) != this.state) {
         this.level.setBlock(this.pos, this.state, 3);

         for(int â˜ƒ = 0; â˜ƒ < this.connections.size(); ++â˜ƒ) {
            RailState â˜ƒx = this.getRail((BlockPos)this.connections.get(â˜ƒ));
            if (â˜ƒx != null) {
               â˜ƒx.removeSoftConnections();
               if (â˜ƒx.canConnectTo(this)) {
                  â˜ƒx.connectTo(this);
               }
            }
         }
      }

      return this;
   }

   public BlockState getState() {
      return this.state;
   }
}
