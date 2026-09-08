package net.minecraft.world.entity.player;

import net.minecraft.nbt.CompoundTag;

public class Abilities {
   public boolean invulnerable;
   public boolean flying;
   public boolean mayfly;
   public boolean instabuild;
   public boolean mayBuild = true;
   private float flyingSpeed = 0.05F;
   private float walkingSpeed = 0.1F;

   public void addSaveData(CompoundTag var1) {
      CompoundTag â˜ƒ = new CompoundTag();
      â˜ƒ.putBoolean("invulnerable", this.invulnerable);
      â˜ƒ.putBoolean("flying", this.flying);
      â˜ƒ.putBoolean("mayfly", this.mayfly);
      â˜ƒ.putBoolean("instabuild", this.instabuild);
      â˜ƒ.putBoolean("mayBuild", this.mayBuild);
      â˜ƒ.putFloat("flySpeed", this.flyingSpeed);
      â˜ƒ.putFloat("walkSpeed", this.walkingSpeed);
      â˜ƒ.put("abilities", â˜ƒ);
   }

   public void loadSaveData(CompoundTag var1) {
      if (â˜ƒ.contains("abilities", 10)) {
         CompoundTag â˜ƒ = â˜ƒ.getCompound("abilities");
         this.invulnerable = â˜ƒ.getBoolean("invulnerable");
         this.flying = â˜ƒ.getBoolean("flying");
         this.mayfly = â˜ƒ.getBoolean("mayfly");
         this.instabuild = â˜ƒ.getBoolean("instabuild");
         if (â˜ƒ.contains("flySpeed", 99)) {
            this.flyingSpeed = â˜ƒ.getFloat("flySpeed");
            this.walkingSpeed = â˜ƒ.getFloat("walkSpeed");
         }

         if (â˜ƒ.contains("mayBuild", 1)) {
            this.mayBuild = â˜ƒ.getBoolean("mayBuild");
         }
      }
   }

   public float getFlyingSpeed() {
      return this.flyingSpeed;
   }

   public void setFlyingSpeed(float var1) {
      this.flyingSpeed = â˜ƒ;
   }

   public float getWalkingSpeed() {
      return this.walkingSpeed;
   }

   public void setWalkingSpeed(float var1) {
      this.walkingSpeed = â˜ƒ;
   }
}
