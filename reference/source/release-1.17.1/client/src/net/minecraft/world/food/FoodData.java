package net.minecraft.world.food;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;

public class FoodData {
   private int foodLevel = 20;
   private float saturationLevel;
   private float exhaustionLevel;
   private int tickTimer;
   private int lastFoodLevel = 20;

   public FoodData() {
      this.saturationLevel = 5.0F;
   }

   public void eat(int var1, float var2) {
      this.foodLevel = Math.min(â˜ƒ + this.foodLevel, 20);
      this.saturationLevel = Math.min(this.saturationLevel + (float)â˜ƒ * â˜ƒ * 2.0F, (float)this.foodLevel);
   }

   public void eat(Item var1, ItemStack var2) {
      if (â˜ƒ.isEdible()) {
         FoodProperties â˜ƒ = â˜ƒ.getFoodProperties();
         this.eat(â˜ƒ.getNutrition(), â˜ƒ.getSaturationModifier());
      }
   }

   public void tick(Player var1) {
      Difficulty â˜ƒ = â˜ƒ.level.getDifficulty();
      this.lastFoodLevel = this.foodLevel;
      if (this.exhaustionLevel > 4.0F) {
         this.exhaustionLevel -= 4.0F;
         if (this.saturationLevel > 0.0F) {
            this.saturationLevel = Math.max(this.saturationLevel - 1.0F, 0.0F);
         } else if (â˜ƒ != Difficulty.PEACEFUL) {
            this.foodLevel = Math.max(this.foodLevel - 1, 0);
         }
      }

      boolean â˜ƒ = â˜ƒ.level.getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION);
      if (â˜ƒ && this.saturationLevel > 0.0F && â˜ƒ.isHurt() && this.foodLevel >= 20) {
         ++this.tickTimer;
         if (this.tickTimer >= 10) {
            float â˜ƒx = Math.min(this.saturationLevel, 6.0F);
            â˜ƒ.heal(â˜ƒx / 6.0F);
            this.addExhaustion(â˜ƒx);
            this.tickTimer = 0;
         }
      } else if (â˜ƒ && this.foodLevel >= 18 && â˜ƒ.isHurt()) {
         ++this.tickTimer;
         if (this.tickTimer >= 80) {
            â˜ƒ.heal(1.0F);
            this.addExhaustion(6.0F);
            this.tickTimer = 0;
         }
      } else if (this.foodLevel <= 0) {
         ++this.tickTimer;
         if (this.tickTimer >= 80) {
            if (â˜ƒ.getHealth() > 10.0F || â˜ƒ == Difficulty.HARD || â˜ƒ.getHealth() > 1.0F && â˜ƒ == Difficulty.NORMAL) {
               â˜ƒ.hurt(DamageSource.STARVE, 1.0F);
            }

            this.tickTimer = 0;
         }
      } else {
         this.tickTimer = 0;
      }
   }

   public void readAdditionalSaveData(CompoundTag var1) {
      if (â˜ƒ.contains("foodLevel", 99)) {
         this.foodLevel = â˜ƒ.getInt("foodLevel");
         this.tickTimer = â˜ƒ.getInt("foodTickTimer");
         this.saturationLevel = â˜ƒ.getFloat("foodSaturationLevel");
         this.exhaustionLevel = â˜ƒ.getFloat("foodExhaustionLevel");
      }
   }

   public void addAdditionalSaveData(CompoundTag var1) {
      â˜ƒ.putInt("foodLevel", this.foodLevel);
      â˜ƒ.putInt("foodTickTimer", this.tickTimer);
      â˜ƒ.putFloat("foodSaturationLevel", this.saturationLevel);
      â˜ƒ.putFloat("foodExhaustionLevel", this.exhaustionLevel);
   }

   public int getFoodLevel() {
      return this.foodLevel;
   }

   public int getLastFoodLevel() {
      return this.lastFoodLevel;
   }

   public boolean needsFood() {
      return this.foodLevel < 20;
   }

   public void addExhaustion(float var1) {
      this.exhaustionLevel = Math.min(this.exhaustionLevel + â˜ƒ, 40.0F);
   }

   public float getExhaustionLevel() {
      return this.exhaustionLevel;
   }

   public float getSaturationLevel() {
      return this.saturationLevel;
   }

   public void setFoodLevel(int var1) {
      this.foodLevel = â˜ƒ;
   }

   public void setSaturation(float var1) {
      this.saturationLevel = â˜ƒ;
   }

   public void setExhaustion(float var1) {
      this.exhaustionLevel = â˜ƒ;
   }
}
