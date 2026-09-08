package net.minecraft.world.entity.boss.enderdragon.phases;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.phys.Vec3;

public class DragonSittingFlamingPhase extends AbstractDragonSittingPhase {
   private static final int FLAME_DURATION = 200;
   private static final int SITTING_FLAME_ATTACKS_COUNT = 4;
   private static final int WARMUP_TIME = 10;
   private int flameTicks;
   private int flameCount;
   private AreaEffectCloud flame;

   public DragonSittingFlamingPhase(EnderDragon var1) {
      super(â˜ƒ);
   }

   @Override
   public void doClientTick() {
      ++this.flameTicks;
      if (this.flameTicks % 2 == 0 && this.flameTicks < 10) {
         Vec3 â˜ƒ = this.dragon.getHeadLookVector(1.0F).normalize();
         â˜ƒ.yRot((float) (-Math.PI / 4));
         double â˜ƒx = this.dragon.head.getX();
         double â˜ƒxx = this.dragon.head.getY(0.5);
         double â˜ƒxxx = this.dragon.head.getZ();

         for(int â˜ƒxxxx = 0; â˜ƒxxxx < 8; ++â˜ƒxxxx) {
            double â˜ƒxxxxx = â˜ƒx + this.dragon.getRandom().nextGaussian() / 2.0;
            double â˜ƒxxxxxx = â˜ƒxx + this.dragon.getRandom().nextGaussian() / 2.0;
            double â˜ƒxxxxxxx = â˜ƒxxx + this.dragon.getRandom().nextGaussian() / 2.0;

            for(int â˜ƒxxxxxxxx = 0; â˜ƒxxxxxxxx < 6; ++â˜ƒxxxxxxxx) {
               this.dragon
                  .level
                  .addParticle(
                     ParticleTypes.DRAGON_BREATH,
                     â˜ƒxxxxx,
                     â˜ƒxxxxxx,
                     â˜ƒxxxxxxx,
                     -â˜ƒ.x * 0.08F * (double)â˜ƒxxxxxxxx,
                     -â˜ƒ.y * 0.6F,
                     -â˜ƒ.z * 0.08F * (double)â˜ƒxxxxxxxx
                  );
            }

            â˜ƒ.yRot((float) (Math.PI / 16));
         }
      }
   }

   @Override
   public void doServerTick() {
      ++this.flameTicks;
      if (this.flameTicks >= 200) {
         if (this.flameCount >= 4) {
            this.dragon.getPhaseManager().setPhase(EnderDragonPhase.TAKEOFF);
         } else {
            this.dragon.getPhaseManager().setPhase(EnderDragonPhase.SITTING_SCANNING);
         }
      } else if (this.flameTicks == 10) {
         Vec3 â˜ƒ = new Vec3(this.dragon.head.getX() - this.dragon.getX(), 0.0, this.dragon.head.getZ() - this.dragon.getZ()).normalize();
         float â˜ƒx = 5.0F;
         double â˜ƒxx = this.dragon.head.getX() + â˜ƒ.x * 5.0 / 2.0;
         double â˜ƒxxx = this.dragon.head.getZ() + â˜ƒ.z * 5.0 / 2.0;
         double â˜ƒxxxx = this.dragon.head.getY(0.5);
         double â˜ƒxxxxx = â˜ƒxxxx;
         BlockPos.MutableBlockPos â˜ƒxxxxxx = new BlockPos.MutableBlockPos(â˜ƒxx, â˜ƒxxxx, â˜ƒxxx);

         while(this.dragon.level.isEmptyBlock(â˜ƒxxxxxx)) {
            if (--â˜ƒxxxxx < 0.0) {
               â˜ƒxxxxx = â˜ƒxxxx;
               break;
            }

            â˜ƒxxxxxx.set(â˜ƒxx, â˜ƒxxxxx, â˜ƒxxx);
         }

         â˜ƒxxxxx = (double)(Mth.floor(â˜ƒxxxxx) + 1);
         this.flame = new AreaEffectCloud(this.dragon.level, â˜ƒxx, â˜ƒxxxxx, â˜ƒxxx);
         this.flame.setOwner(this.dragon);
         this.flame.setRadius(5.0F);
         this.flame.setDuration(200);
         this.flame.setParticle(ParticleTypes.DRAGON_BREATH);
         this.flame.addEffect(new MobEffectInstance(MobEffects.HARM));
         this.dragon.level.addFreshEntity(this.flame);
      }
   }

   @Override
   public void begin() {
      this.flameTicks = 0;
      ++this.flameCount;
   }

   @Override
   public void end() {
      if (this.flame != null) {
         this.flame.discard();
         this.flame = null;
      }
   }

   @Override
   public EnderDragonPhase<DragonSittingFlamingPhase> getPhase() {
      return EnderDragonPhase.SITTING_FLAMING;
   }

   public void resetFlameCount() {
      this.flameCount = 0;
   }
}
