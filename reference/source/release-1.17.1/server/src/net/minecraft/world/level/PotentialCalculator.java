package net.minecraft.world.level;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.core.BlockPos;

public class PotentialCalculator {
   private final List<PotentialCalculator.PointCharge> charges = Lists.<PotentialCalculator.PointCharge>newArrayList();

   public void addCharge(BlockPos var1, double var2) {
      if (â˜ƒ != 0.0) {
         this.charges.add(new PotentialCalculator.PointCharge(â˜ƒ, â˜ƒ));
      }
   }

   public double getPotentialEnergyChange(BlockPos var1, double var2) {
      if (â˜ƒ == 0.0) {
         return 0.0;
      } else {
         double â˜ƒ = 0.0;

         for(PotentialCalculator.PointCharge â˜ƒx : this.charges) {
            â˜ƒ += â˜ƒx.getPotentialChange(â˜ƒ);
         }

         return â˜ƒ * â˜ƒ;
      }
   }

   static class PointCharge {
      private final BlockPos pos;
      private final double charge;

      public PointCharge(BlockPos var1, double var2) {
         this.pos = â˜ƒ;
         this.charge = â˜ƒ;
      }

      public double getPotentialChange(BlockPos var1) {
         double â˜ƒ = this.pos.distSqr(â˜ƒ);
         return â˜ƒ == 0.0 ? Double.POSITIVE_INFINITY : this.charge / Math.sqrt(â˜ƒ);
      }
   }
}
