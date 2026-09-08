package net.minecraft.client.resources.sounds;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;

public class EntityBoundSoundInstance extends AbstractTickableSoundInstance {
   private final Entity entity;

   public EntityBoundSoundInstance(SoundEvent var1, SoundSource var2, float var3, float var4, Entity var5) {
      super(â˜ƒ, â˜ƒ);
      this.volume = â˜ƒ;
      this.pitch = â˜ƒ;
      this.entity = â˜ƒ;
      this.x = (double)((float)this.entity.getX());
      this.y = (double)((float)this.entity.getY());
      this.z = (double)((float)this.entity.getZ());
   }

   @Override
   public boolean canPlaySound() {
      return !this.entity.isSilent();
   }

   @Override
   public void tick() {
      if (this.entity.isRemoved()) {
         this.stop();
      } else {
         this.x = (double)((float)this.entity.getX());
         this.y = (double)((float)this.entity.getY());
         this.z = (double)((float)this.entity.getZ());
      }
   }
}
