package net.minecraft.world.entity.vehicle;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class Minecart extends AbstractMinecart {
   public Minecart(EntityType<?> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public Minecart(Level var1, double var2, double var4, double var6) {
      super(EntityType.MINECART, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public InteractionResult interact(Player var1, InteractionHand var2) {
      if (â˜ƒ.isSecondaryUseActive()) {
         return InteractionResult.PASS;
      } else if (this.isVehicle()) {
         return InteractionResult.PASS;
      } else if (!this.level.isClientSide) {
         return â˜ƒ.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
      } else {
         return InteractionResult.SUCCESS;
      }
   }

   @Override
   public void activateMinecart(int var1, int var2, int var3, boolean var4) {
      if (â˜ƒ) {
         if (this.isVehicle()) {
            this.ejectPassengers();
         }

         if (this.getHurtTime() == 0) {
            this.setHurtDir(-this.getHurtDir());
            this.setHurtTime(10);
            this.setDamage(50.0F);
            this.markHurt();
         }
      }
   }

   @Override
   public AbstractMinecart.Type getMinecartType() {
      return AbstractMinecart.Type.RIDEABLE;
   }
}
