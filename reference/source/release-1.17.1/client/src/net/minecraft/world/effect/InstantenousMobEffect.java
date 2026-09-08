package net.minecraft.world.effect;

public class InstantenousMobEffect extends MobEffect {
   public InstantenousMobEffect(MobEffectCategory var1, int var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean isInstantenous() {
      return true;
   }

   @Override
   public boolean isDurationEffectTick(int var1, int var2) {
      return â˜ƒ >= 1;
   }
}
