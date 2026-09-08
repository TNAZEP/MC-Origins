package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.RuinedPortalFeature;

public class RuinedPortalConfiguration implements FeatureConfiguration {
   public static final Codec<RuinedPortalConfiguration> CODEC = RuinedPortalFeature.Type.CODEC
      .fieldOf("portal_type")
      .<RuinedPortalConfiguration>xmap(RuinedPortalConfiguration::new, var0 -> var0.portalType)
      .codec();
   public final RuinedPortalFeature.Type portalType;

   public RuinedPortalConfiguration(RuinedPortalFeature.Type var1) {
      this.portalType = â˜ƒ;
   }
}
