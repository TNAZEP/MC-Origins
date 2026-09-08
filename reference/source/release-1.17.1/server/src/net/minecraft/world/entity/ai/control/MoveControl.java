package net.minecraft.world.entity.ai.control;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MoveControl implements Control {
   public static final float MIN_SPEED = 5.0E-4F;
   public static final float MIN_SPEED_SQR = 2.5000003E-7F;
   protected static final int MAX_TURN = 90;
   protected final Mob mob;
   protected double wantedX;
   protected double wantedY;
   protected double wantedZ;
   protected double speedModifier;
   protected float strafeForwards;
   protected float strafeRight;
   protected MoveControl.Operation operation = MoveControl.Operation.WAIT;

   public MoveControl(Mob var1) {
      this.mob = â˜ƒ;
   }

   public boolean hasWanted() {
      return this.operation == MoveControl.Operation.MOVE_TO;
   }

   public double getSpeedModifier() {
      return this.speedModifier;
   }

   public void setWantedPosition(double var1, double var3, double var5, double var7) {
      this.wantedX = â˜ƒ;
      this.wantedY = â˜ƒ;
      this.wantedZ = â˜ƒ;
      this.speedModifier = â˜ƒ;
      if (this.operation != MoveControl.Operation.JUMPING) {
         this.operation = MoveControl.Operation.MOVE_TO;
      }
   }

   public void strafe(float var1, float var2) {
      this.operation = MoveControl.Operation.STRAFE;
      this.strafeForwards = â˜ƒ;
      this.strafeRight = â˜ƒ;
      this.speedModifier = 0.25;
   }

   public void tick() {
      if (this.operation == MoveControl.Operation.STRAFE) {
         float â˜ƒ = (float)this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED);
         float â˜ƒx = (float)this.speedModifier * â˜ƒ;
         float â˜ƒxx = this.strafeForwards;
         float â˜ƒxxx = this.strafeRight;
         float â˜ƒxxxx = Mth.sqrt(â˜ƒxx * â˜ƒxx + â˜ƒxxx * â˜ƒxxx);
         if (â˜ƒxxxx < 1.0F) {
            â˜ƒxxxx = 1.0F;
         }

         â˜ƒxxxx = â˜ƒx / â˜ƒxxxx;
         â˜ƒxx *= â˜ƒxxxx;
         â˜ƒxxx *= â˜ƒxxxx;
         float â˜ƒ = Mth.sin(this.mob.getYRot() * (float) (Math.PI / 180.0));
         float â˜ƒx = Mth.cos(this.mob.getYRot() * (float) (Math.PI / 180.0));
         float â˜ƒxx = â˜ƒxx * â˜ƒx - â˜ƒxxx * â˜ƒ;
         float â˜ƒxxx = â˜ƒxxx * â˜ƒx + â˜ƒxx * â˜ƒ;
         if (!this.isWalkable(â˜ƒxx, â˜ƒxxx)) {
            this.strafeForwards = 1.0F;
            this.strafeRight = 0.0F;
         }

         this.mob.setSpeed(â˜ƒx);
         this.mob.setZza(this.strafeForwards);
         this.mob.setXxa(this.strafeRight);
         this.operation = MoveControl.Operation.WAIT;
      } else if (this.operation == MoveControl.Operation.MOVE_TO) {
         this.operation = MoveControl.Operation.WAIT;
         double â˜ƒ = this.wantedX - this.mob.getX();
         double â˜ƒx = this.wantedZ - this.mob.getZ();
         double â˜ƒxx = this.wantedY - this.mob.getY();
         double â˜ƒxxx = â˜ƒ * â˜ƒ + â˜ƒxx * â˜ƒxx + â˜ƒx * â˜ƒx;
         if (â˜ƒxxx < 2.5000003E-7F) {
            this.mob.setZza(0.0F);
            return;
         }

         float â˜ƒ = (float)(Mth.atan2(â˜ƒx, â˜ƒ) * 180.0F / (float)Math.PI) - 90.0F;
         this.mob.setYRot(this.rotlerp(this.mob.getYRot(), â˜ƒ, 90.0F));
         this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
         BlockPos â˜ƒx = this.mob.blockPosition();
         BlockState â˜ƒxx = this.mob.level.getBlockState(â˜ƒx);
         VoxelShape â˜ƒxxx = â˜ƒxx.getCollisionShape(this.mob.level, â˜ƒx);
         if (â˜ƒxx > (double)this.mob.maxUpStep && â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx < (double)Math.max(1.0F, this.mob.getBbWidth())
            || !â˜ƒxxx.isEmpty()
               && this.mob.getY() < â˜ƒxxx.max(Direction.Axis.Y) + (double)â˜ƒx.getY()
               && !â˜ƒxx.is(BlockTags.DOORS)
               && !â˜ƒxx.is(BlockTags.FENCES)) {
            this.mob.getJumpControl().jump();
            this.operation = MoveControl.Operation.JUMPING;
         }
      } else if (this.operation == MoveControl.Operation.JUMPING) {
         this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
         if (this.mob.isOnGround()) {
            this.operation = MoveControl.Operation.WAIT;
         }
      } else {
         this.mob.setZza(0.0F);
      }
   }

   private boolean isWalkable(float var1, float var2) {
      PathNavigation â˜ƒ = this.mob.getNavigation();
      if (â˜ƒ != null) {
         NodeEvaluator â˜ƒx = â˜ƒ.getNodeEvaluator();
         if (â˜ƒx != null
            && â˜ƒx.getBlockPathType(this.mob.level, Mth.floor(this.mob.getX() + (double)â˜ƒ), this.mob.getBlockY(), Mth.floor(this.mob.getZ() + (double)â˜ƒ))
               != BlockPathTypes.WALKABLE) {
            return false;
         }
      }

      return true;
   }

   protected float rotlerp(float var1, float var2, float var3) {
      float â˜ƒ = Mth.wrapDegrees(â˜ƒ - â˜ƒ);
      if (â˜ƒ > â˜ƒ) {
         â˜ƒ = â˜ƒ;
      }

      if (â˜ƒ < -â˜ƒ) {
         â˜ƒ = -â˜ƒ;
      }

      float â˜ƒ = â˜ƒ + â˜ƒ;
      if (â˜ƒ < 0.0F) {
         â˜ƒ += 360.0F;
      } else if (â˜ƒ > 360.0F) {
         â˜ƒ -= 360.0F;
      }

      return â˜ƒ;
   }

   public double getWantedX() {
      return this.wantedX;
   }

   public double getWantedY() {
      return this.wantedY;
   }

   public double getWantedZ() {
      return this.wantedZ;
   }

   protected static enum Operation {
      WAIT,
      MOVE_TO,
      STRAFE,
      JUMPING;
   }
}
