package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;

public class ShipwreckConfiguration implements FeatureConfiguration {
   public static final Codec<ShipwreckConfiguration> CODEC = Codec.BOOL
      .fieldOf("is_beached")
      .orElse(false)
      .<ShipwreckConfiguration>xmap(ShipwreckConfiguration::new, var0 -> var0.isBeached)
      .codec();
   public final boolean isBeached;

   public ShipwreckConfiguration(boolean var1) {
      this.isBeached = â˜ƒ;
   }
}
