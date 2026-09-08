package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.phys.Vec3;

public class LlamaFollowCaravanGoal extends Goal {
   public final Llama llama;
   private double speedModifier;
   private static final int CARAVAN_LIMIT = 8;
   private int distCheckCounter;

   public LlamaFollowCaravanGoal(Llama var1, double var2) {
      this.llama = â˜ƒ;
      this.speedModifier = â˜ƒ;
      this.setFlags(EnumSet.of(Goal.Flag.MOVE));
   }

   @Override
   public boolean canUse() {
      if (!this.llama.isLeashed() && !this.llama.inCaravan()) {
         List<Entity> â˜ƒ = this.llama.level.getEntities(this.llama, this.llama.getBoundingBox().inflate(9.0, 4.0, 9.0), var0 -> {
            EntityType<?> â˜ƒ = var0.getType();
            return â˜ƒ == EntityType.LLAMA || â˜ƒ == EntityType.TRADER_LLAMA;
         });
         Llama â˜ƒx = null;
         double â˜ƒxx = Double.MAX_VALUE;

         for(Entity â˜ƒxxx : â˜ƒ) {
            Llama â˜ƒxxxx = (Llama)â˜ƒxxx;
            if (â˜ƒxxxx.inCaravan() && !â˜ƒxxxx.hasCaravanTail()) {
               double â˜ƒxxxxx = this.llama.distanceToSqr(â˜ƒxxxx);
               if (!(â˜ƒxxxxx > â˜ƒxx)) {
                  â˜ƒxx = â˜ƒxxxxx;
                  â˜ƒx = â˜ƒxxxx;
               }
            }
         }

         if (â˜ƒx == null) {
            for(Entity â˜ƒxxx : â˜ƒ) {
               Llama â˜ƒxxxx = (Llama)â˜ƒxxx;
               if (â˜ƒxxxx.isLeashed() && !â˜ƒxxxx.hasCaravanTail()) {
                  double â˜ƒxxxxx = this.llama.distanceToSqr(â˜ƒxxxx);
                  if (!(â˜ƒxxxxx > â˜ƒxx)) {
                     â˜ƒxx = â˜ƒxxxxx;
                     â˜ƒx = â˜ƒxxxx;
                  }
               }
            }
         }

         if (â˜ƒx == null) {
            return false;
         } else if (â˜ƒxx < 4.0) {
            return false;
         } else if (!â˜ƒx.isLeashed() && !this.firstIsLeashed(â˜ƒx, 1)) {
            return false;
         } else {
            this.llama.joinCaravan(â˜ƒx);
            return true;
         }
      } else {
         return false;
      }
   }

   @Override
   public boolean canContinueToUse() {
      if (this.llama.inCaravan() && this.llama.getCaravanHead().isAlive() && this.firstIsLeashed(this.llama, 0)) {
         double â˜ƒ = this.llama.distanceToSqr(this.llama.getCaravanHead());
         if (â˜ƒ > 676.0) {
            if (this.speedModifier <= 3.0) {
               this.speedModifier *= 1.2;
               this.distCheckCounter = 40;
               return true;
            }

            if (this.distCheckCounter == 0) {
               return false;
            }
         }

         if (this.distCheckCounter > 0) {
            --this.distCheckCounter;
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public void stop() {
      this.llama.leaveCaravan();
      this.speedModifier = 2.1;
   }

   @Override
   public void tick() {
      if (this.llama.inCaravan()) {
         if (!(this.llama.getLeashHolder() instanceof LeashFenceKnotEntity)) {
            Llama â˜ƒ = this.llama.getCaravanHead();
            double â˜ƒx = (double)this.llama.distanceTo(â˜ƒ);
            float â˜ƒxx = 2.0F;
            Vec3 â˜ƒxxx = new Vec3(â˜ƒ.getX() - this.llama.getX(), â˜ƒ.getY() - this.llama.getY(), â˜ƒ.getZ() - this.llama.getZ())
               .normalize()
               .scale(Math.max(â˜ƒx - 2.0, 0.0));
            this.llama.getNavigation().moveTo(this.llama.getX() + â˜ƒxxx.x, this.llama.getY() + â˜ƒxxx.y, this.llama.getZ() + â˜ƒxxx.z, this.speedModifier);
         }
      }
   }

   private boolean firstIsLeashed(Llama var1, int var2) {
      if (â˜ƒ > 8) {
         return false;
      } else if (â˜ƒ.inCaravan()) {
         return â˜ƒ.getCaravanHead().isLeashed() ? true : this.firstIsLeashed(â˜ƒ.getCaravanHead(), ++â˜ƒ);
      } else {
         return false;
      }
   }
}
