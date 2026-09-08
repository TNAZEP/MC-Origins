package net.minecraft.world.effect;

import com.google.common.collect.ComparisonChain;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MobEffectInstance implements Comparable<MobEffectInstance> {
   private static final Logger LOGGER = LogManager.getLogger();
   private final MobEffect effect;
   private int duration;
   private int amplifier;
   private boolean ambient;
   private boolean noCounter;
   private boolean visible;
   private boolean showIcon;
   @Nullable
   private MobEffectInstance hiddenEffect;

   public MobEffectInstance(MobEffect var1) {
      this(â˜ƒ, 0, 0);
   }

   public MobEffectInstance(MobEffect var1, int var2) {
      this(â˜ƒ, â˜ƒ, 0);
   }

   public MobEffectInstance(MobEffect var1, int var2, int var3) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, false, true);
   }

   public MobEffectInstance(MobEffect var1, int var2, int var3, boolean var4, boolean var5) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public MobEffectInstance(MobEffect var1, int var2, int var3, boolean var4, boolean var5, boolean var6) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, null);
   }

   public MobEffectInstance(MobEffect var1, int var2, int var3, boolean var4, boolean var5, boolean var6, @Nullable MobEffectInstance var7) {
      this.effect = â˜ƒ;
      this.duration = â˜ƒ;
      this.amplifier = â˜ƒ;
      this.ambient = â˜ƒ;
      this.visible = â˜ƒ;
      this.showIcon = â˜ƒ;
      this.hiddenEffect = â˜ƒ;
   }

   public MobEffectInstance(MobEffectInstance var1) {
      this.effect = â˜ƒ.effect;
      this.setDetailsFrom(â˜ƒ);
   }

   void setDetailsFrom(MobEffectInstance var1) {
      this.duration = â˜ƒ.duration;
      this.amplifier = â˜ƒ.amplifier;
      this.ambient = â˜ƒ.ambient;
      this.visible = â˜ƒ.visible;
      this.showIcon = â˜ƒ.showIcon;
   }

   public boolean update(MobEffectInstance var1) {
      if (this.effect != â˜ƒ.effect) {
         LOGGER.warn("This method should only be called for matching effects!");
      }

      boolean â˜ƒ = false;
      if (â˜ƒ.amplifier > this.amplifier) {
         if (â˜ƒ.duration < this.duration) {
            MobEffectInstance â˜ƒx = this.hiddenEffect;
            this.hiddenEffect = new MobEffectInstance(this);
            this.hiddenEffect.hiddenEffect = â˜ƒx;
         }

         this.amplifier = â˜ƒ.amplifier;
         this.duration = â˜ƒ.duration;
         â˜ƒ = true;
      } else if (â˜ƒ.duration > this.duration) {
         if (â˜ƒ.amplifier == this.amplifier) {
            this.duration = â˜ƒ.duration;
            â˜ƒ = true;
         } else if (this.hiddenEffect == null) {
            this.hiddenEffect = new MobEffectInstance(â˜ƒ);
         } else {
            this.hiddenEffect.update(â˜ƒ);
         }
      }

      if (!â˜ƒ.ambient && this.ambient || â˜ƒ) {
         this.ambient = â˜ƒ.ambient;
         â˜ƒ = true;
      }

      if (â˜ƒ.visible != this.visible) {
         this.visible = â˜ƒ.visible;
         â˜ƒ = true;
      }

      if (â˜ƒ.showIcon != this.showIcon) {
         this.showIcon = â˜ƒ.showIcon;
         â˜ƒ = true;
      }

      return â˜ƒ;
   }

   public MobEffect getEffect() {
      return this.effect;
   }

   public int getDuration() {
      return this.duration;
   }

   public int getAmplifier() {
      return this.amplifier;
   }

   public boolean isAmbient() {
      return this.ambient;
   }

   public boolean isVisible() {
      return this.visible;
   }

   public boolean showIcon() {
      return this.showIcon;
   }

   public boolean tick(LivingEntity var1, Runnable var2) {
      if (this.duration > 0) {
         if (this.effect.isDurationEffectTick(this.duration, this.amplifier)) {
            this.applyEffect(â˜ƒ);
         }

         this.tickDownDuration();
         if (this.duration == 0 && this.hiddenEffect != null) {
            this.setDetailsFrom(this.hiddenEffect);
            this.hiddenEffect = this.hiddenEffect.hiddenEffect;
            â˜ƒ.run();
         }
      }

      return this.duration > 0;
   }

   private int tickDownDuration() {
      if (this.hiddenEffect != null) {
         this.hiddenEffect.tickDownDuration();
      }

      return --this.duration;
   }

   public void applyEffect(LivingEntity var1) {
      if (this.duration > 0) {
         this.effect.applyEffectTick(â˜ƒ, this.amplifier);
      }
   }

   public String getDescriptionId() {
      return this.effect.getDescriptionId();
   }

   public String toString() {
      String â˜ƒ;
      if (this.amplifier > 0) {
         â˜ƒ = this.getDescriptionId() + " x " + (this.amplifier + 1) + ", Duration: " + this.duration;
      } else {
         â˜ƒ = this.getDescriptionId() + ", Duration: " + this.duration;
      }

      if (!this.visible) {
         â˜ƒ = â˜ƒ + ", Particles: false";
      }

      if (!this.showIcon) {
         â˜ƒ = â˜ƒ + ", Show Icon: false";
      }

      return â˜ƒ;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (!(â˜ƒ instanceof MobEffectInstance)) {
         return false;
      } else {
         MobEffectInstance â˜ƒ = (MobEffectInstance)â˜ƒ;
         return this.duration == â˜ƒ.duration && this.amplifier == â˜ƒ.amplifier && this.ambient == â˜ƒ.ambient && this.effect.equals(â˜ƒ.effect);
      }
   }

   public int hashCode() {
      int â˜ƒ = this.effect.hashCode();
      â˜ƒ = 31 * â˜ƒ + this.duration;
      â˜ƒ = 31 * â˜ƒ + this.amplifier;
      return 31 * â˜ƒ + (this.ambient ? 1 : 0);
   }

   public CompoundTag save(CompoundTag var1) {
      â˜ƒ.putByte("Id", (byte)MobEffect.getId(this.getEffect()));
      this.writeDetailsTo(â˜ƒ);
      return â˜ƒ;
   }

   private void writeDetailsTo(CompoundTag var1) {
      â˜ƒ.putByte("Amplifier", (byte)this.getAmplifier());
      â˜ƒ.putInt("Duration", this.getDuration());
      â˜ƒ.putBoolean("Ambient", this.isAmbient());
      â˜ƒ.putBoolean("ShowParticles", this.isVisible());
      â˜ƒ.putBoolean("ShowIcon", this.showIcon());
      if (this.hiddenEffect != null) {
         CompoundTag â˜ƒ = new CompoundTag();
         this.hiddenEffect.save(â˜ƒ);
         â˜ƒ.put("HiddenEffect", â˜ƒ);
      }
   }

   @Nullable
   public static MobEffectInstance load(CompoundTag var0) {
      int â˜ƒ = â˜ƒ.getByte("Id");
      MobEffect â˜ƒx = MobEffect.byId(â˜ƒ);
      return â˜ƒx == null ? null : loadSpecifiedEffect(â˜ƒx, â˜ƒ);
   }

   private static MobEffectInstance loadSpecifiedEffect(MobEffect var0, CompoundTag var1) {
      int â˜ƒ = â˜ƒ.getByte("Amplifier");
      int â˜ƒx = â˜ƒ.getInt("Duration");
      boolean â˜ƒxx = â˜ƒ.getBoolean("Ambient");
      boolean â˜ƒxxx = true;
      if (â˜ƒ.contains("ShowParticles", 1)) {
         â˜ƒxxx = â˜ƒ.getBoolean("ShowParticles");
      }

      boolean â˜ƒ = â˜ƒxxx;
      if (â˜ƒ.contains("ShowIcon", 1)) {
         â˜ƒ = â˜ƒ.getBoolean("ShowIcon");
      }

      MobEffectInstance â˜ƒ = null;
      if (â˜ƒ.contains("HiddenEffect", 10)) {
         â˜ƒ = loadSpecifiedEffect(â˜ƒ, â˜ƒ.getCompound("HiddenEffect"));
      }

      return new MobEffectInstance(â˜ƒ, â˜ƒx, â˜ƒ < 0 ? 0 : â˜ƒ, â˜ƒxx, â˜ƒxxx, â˜ƒ, â˜ƒ);
   }

   public void setNoCounter(boolean var1) {
      this.noCounter = â˜ƒ;
   }

   public boolean isNoCounter() {
      return this.noCounter;
   }

   public int compareTo(MobEffectInstance var1) {
      int â˜ƒ = 32147;
      return (this.getDuration() <= 32147 || â˜ƒ.getDuration() <= 32147) && (!this.isAmbient() || !â˜ƒ.isAmbient())
         ? ComparisonChain.start()
            .compare(this.isAmbient(), â˜ƒ.isAmbient())
            .compare(this.getDuration(), â˜ƒ.getDuration())
            .compare(this.getEffect().getColor(), â˜ƒ.getEffect().getColor())
            .result()
         : ComparisonChain.start().compare(this.isAmbient(), â˜ƒ.isAmbient()).compare(this.getEffect().getColor(), â˜ƒ.getEffect().getColor()).result();
   }
}
