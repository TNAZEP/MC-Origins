package net.minecraft.world.entity.boss.enderdragon.phases;

import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnderDragonPhaseManager {
   private static final Logger LOGGER = LogManager.getLogger();
   private final EnderDragon dragon;
   private final DragonPhaseInstance[] phases = new DragonPhaseInstance[EnderDragonPhase.getCount()];
   private DragonPhaseInstance currentPhase;

   public EnderDragonPhaseManager(EnderDragon var1) {
      this.dragon = â˜ƒ;
      this.setPhase(EnderDragonPhase.HOVERING);
   }

   public void setPhase(EnderDragonPhase<?> var1) {
      if (this.currentPhase == null || â˜ƒ != this.currentPhase.getPhase()) {
         if (this.currentPhase != null) {
            this.currentPhase.end();
         }

         this.currentPhase = this.getPhase(â˜ƒ);
         if (!this.dragon.level.isClientSide) {
            this.dragon.getEntityData().set(EnderDragon.DATA_PHASE, â˜ƒ.getId());
         }

         LOGGER.debug("Dragon is now in phase {} on the {}", â˜ƒ, this.dragon.level.isClientSide ? "client" : "server");
         this.currentPhase.begin();
      }
   }

   public DragonPhaseInstance getCurrentPhase() {
      return this.currentPhase;
   }

   public <T extends DragonPhaseInstance> T getPhase(EnderDragonPhase<T> var1) {
      int â˜ƒ = â˜ƒ.getId();
      if (this.phases[â˜ƒ] == null) {
         this.phases[â˜ƒ] = â˜ƒ.createInstance(this.dragon);
      }

      return (T)this.phases[â˜ƒ];
   }
}
