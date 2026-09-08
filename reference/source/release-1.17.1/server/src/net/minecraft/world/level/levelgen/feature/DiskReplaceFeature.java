package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;

public class DiskReplaceFeature extends BaseDiskFeature {
   public DiskReplaceFeature(Codec<DiskConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<DiskConfiguration> var1) {
      return !â˜ƒ.level().getFluidState(â˜ƒ.origin()).is(FluidTags.WATER) ? false : super.place(â˜ƒ);
   }
}
