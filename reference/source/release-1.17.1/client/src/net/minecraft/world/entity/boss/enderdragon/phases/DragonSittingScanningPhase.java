package net.minecraft.world.entity.boss.enderdragon.phases;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.phys.Vec3;

public class DragonSittingScanningPhase extends AbstractDragonSittingPhase {
   private static final int SITTING_SCANNING_IDLE_TICKS = 100;
   private static final int SITTING_ATTACK_Y_VIEW_RANGE = 10;
   private static final int SITTING_ATTACK_VIEW_RANGE = 20;
   private static final int SITTING_CHARGE_VIEW_RANGE = 150;
   private static final TargetingConditions CHARGE_TARGETING = TargetingConditions.forCombat().range(150.0);
   private final TargetingConditions scanTargeting;
   private int scanningTime;

   public DragonSittingScanningPhase(EnderDragon var1) {
      super(â˜ƒ);
      this.scanTargeting = TargetingConditions.forCombat().range(20.0).selector(var1x -> Math.abs(var1x.getY() - â˜ƒ.getY()) <= 10.0);
   }

   @Override
   public void doServerTick() {
      ++this.scanningTime;
      LivingEntity â˜ƒ = this.dragon.level.getNearestPlayer(this.scanTargeting, this.dragon, this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
      if (â˜ƒ != null) {
         if (this.scanningTime > 25) {
            this.dragon.getPhaseManager().setPhase(EnderDragonPhase.SITTING_ATTACKING);
         } else {
            Vec3 â˜ƒx = new Vec3(â˜ƒ.getX() - this.dragon.getX(), 0.0, â˜ƒ.getZ() - this.dragon.getZ()).normalize();
            Vec3 â˜ƒxx = new Vec3(
                  (double)Mth.sin(this.dragon.getYRot() * (float) (Math.PI / 180.0)),
                  0.0,
                  (double)(-Mth.cos(this.dragon.getYRot() * (float) (Math.PI / 180.0)))
               )
               .normalize();
            float â˜ƒxxx = (float)â˜ƒxx.dot(â˜ƒx);
            float â˜ƒxxxx = (float)(Math.acos((double)â˜ƒxxx) * 180.0F / (float)Math.PI) + 0.5F;
            if (â˜ƒxxxx < 0.0F || â˜ƒxxxx > 10.0F) {
               double â˜ƒxxxxx = â˜ƒ.getX() - this.dragon.head.getX();
               double â˜ƒxxxxxx = â˜ƒ.getZ() - this.dragon.head.getZ();
               double â˜ƒxxxxxxx = Mth.clamp(
                  Mth.wrapDegrees(180.0 - Mth.atan2(â˜ƒxxxxx, â˜ƒxxxxxx) * 180.0F / (float)Math.PI - (double)this.dragon.getYRot()), -100.0, 100.0
               );
               this.dragon.yRotA *= 0.8F;
               float â˜ƒxxxxxxxx = (float)Math.sqrt(â˜ƒxxxxx * â˜ƒxxxxx + â˜ƒxxxxxx * â˜ƒxxxxxx) + 1.0F;
               float â˜ƒxxxxxxxxx = â˜ƒxxxxxxxx;
               if (â˜ƒxxxxxxxx > 40.0F) {
                  â˜ƒxxxxxxxx = 40.0F;
               }

               this.dragon.yRotA = (float)((double)this.dragon.yRotA + â˜ƒxxxxxxx * (double)(0.7F / â˜ƒxxxxxxxx / â˜ƒxxxxxxxxx));
               this.dragon.setYRot(this.dragon.getYRot() + this.dragon.yRotA);
            }
         }
      } else if (this.scanningTime >= 100) {
         â˜ƒ = this.dragon.level.getNearestPlayer(CHARGE_TARGETING, this.dragon, this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
         this.dragon.getPhaseManager().setPhase(EnderDragonPhase.TAKEOFF);
         if (â˜ƒ != null) {
            this.dragon.getPhaseManager().setPhase(EnderDragonPhase.CHARGING_PLAYER);
            this.dragon.getPhaseManager().getPhase(EnderDragonPhase.CHARGING_PLAYER).setTarget(new Vec3(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ()));
         }
      }
   }

   @Override
   public void begin() {
      this.scanningTime = 0;
   }

   @Override
   public EnderDragonPhase<DragonSittingScanningPhase> getPhase() {
      return EnderDragonPhase.SITTING_SCANNING;
   }
}
