package net.minecraft.world;

import javax.annotation.concurrent.Immutable;
import net.minecraft.util.Mth;

@Immutable
public class DifficultyInstance {
   private static final float DIFFICULTY_TIME_GLOBAL_OFFSET = -72000.0F;
   private static final float MAX_DIFFICULTY_TIME_GLOBAL = 1440000.0F;
   private static final float MAX_DIFFICULTY_TIME_LOCAL = 3600000.0F;
   private final Difficulty base;
   private final float effectiveDifficulty;

   public DifficultyInstance(Difficulty var1, long var2, long var4, float var6) {
      this.base = â˜ƒ;
      this.effectiveDifficulty = this.calculateDifficulty(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public Difficulty getDifficulty() {
      return this.base;
   }

   public float getEffectiveDifficulty() {
      return this.effectiveDifficulty;
   }

   public boolean isHard() {
      return this.effectiveDifficulty >= (float)Difficulty.HARD.ordinal();
   }

   public boolean isHarderThan(float var1) {
      return this.effectiveDifficulty > â˜ƒ;
   }

   public float getSpecialMultiplier() {
      if (this.effectiveDifficulty < 2.0F) {
         return 0.0F;
      } else {
         return this.effectiveDifficulty > 4.0F ? 1.0F : (this.effectiveDifficulty - 2.0F) / 2.0F;
      }
   }

   private float calculateDifficulty(Difficulty var1, long var2, long var4, float var6) {
      if (â˜ƒ == Difficulty.PEACEFUL) {
         return 0.0F;
      } else {
         boolean â˜ƒ = â˜ƒ == Difficulty.HARD;
         float â˜ƒx = 0.75F;
         float â˜ƒxx = Mth.clamp(((float)â˜ƒ + -72000.0F) / 1440000.0F, 0.0F, 1.0F) * 0.25F;
         â˜ƒx += â˜ƒxx;
         float â˜ƒxxx = 0.0F;
         â˜ƒxxx += Mth.clamp((float)â˜ƒ / 3600000.0F, 0.0F, 1.0F) * (â˜ƒ ? 1.0F : 0.75F);
         â˜ƒxxx += Mth.clamp(â˜ƒ * 0.25F, 0.0F, â˜ƒxx);
         if (â˜ƒ == Difficulty.EASY) {
            â˜ƒxxx *= 0.5F;
         }

         â˜ƒx += â˜ƒxxx;
         return (float)â˜ƒ.getId() * â˜ƒx;
      }
   }
}
