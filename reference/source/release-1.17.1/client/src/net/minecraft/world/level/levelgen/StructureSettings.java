package net.minecraft.world.level.levelgen;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.StrongholdConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;

public class StructureSettings {
   public static final Codec<StructureSettings> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               StrongholdConfiguration.CODEC.optionalFieldOf("stronghold").forGetter(var0x -> Optional.ofNullable(var0x.stronghold)),
               Codec.simpleMap(Registry.STRUCTURE_FEATURE, StructureFeatureConfiguration.CODEC, Registry.STRUCTURE_FEATURE)
                  .fieldOf("structures")
                  .forGetter(var0x -> var0x.structureConfig)
            )
            .apply(var0, StructureSettings::new)
   );
   public static final ImmutableMap<StructureFeature<?>, StructureFeatureConfiguration> DEFAULTS = ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration>builder(
         
      )
      .put(StructureFeature.VILLAGE, new StructureFeatureConfiguration(32, 8, 10387312))
      .put(StructureFeature.DESERT_PYRAMID, new StructureFeatureConfiguration(32, 8, 14357617))
      .put(StructureFeature.IGLOO, new StructureFeatureConfiguration(32, 8, 14357618))
      .put(StructureFeature.JUNGLE_TEMPLE, new StructureFeatureConfiguration(32, 8, 14357619))
      .put(StructureFeature.SWAMP_HUT, new StructureFeatureConfiguration(32, 8, 14357620))
      .put(StructureFeature.PILLAGER_OUTPOST, new StructureFeatureConfiguration(32, 8, 165745296))
      .put(StructureFeature.STRONGHOLD, new StructureFeatureConfiguration(1, 0, 0))
      .put(StructureFeature.OCEAN_MONUMENT, new StructureFeatureConfiguration(32, 5, 10387313))
      .put(StructureFeature.END_CITY, new StructureFeatureConfiguration(20, 11, 10387313))
      .put(StructureFeature.WOODLAND_MANSION, new StructureFeatureConfiguration(80, 20, 10387319))
      .put(StructureFeature.BURIED_TREASURE, new StructureFeatureConfiguration(1, 0, 0))
      .put(StructureFeature.MINESHAFT, new StructureFeatureConfiguration(1, 0, 0))
      .put(StructureFeature.RUINED_PORTAL, new StructureFeatureConfiguration(40, 15, 34222645))
      .put(StructureFeature.SHIPWRECK, new StructureFeatureConfiguration(24, 4, 165745295))
      .put(StructureFeature.OCEAN_RUIN, new StructureFeatureConfiguration(20, 8, 14357621))
      .put(StructureFeature.BASTION_REMNANT, new StructureFeatureConfiguration(27, 4, 30084232))
      .put(StructureFeature.NETHER_BRIDGE, new StructureFeatureConfiguration(27, 4, 30084232))
      .put(StructureFeature.NETHER_FOSSIL, new StructureFeatureConfiguration(2, 1, 14357921))
      .build();
   public static final StrongholdConfiguration DEFAULT_STRONGHOLD;
   private final Map<StructureFeature<?>, StructureFeatureConfiguration> structureConfig;
   @Nullable
   private final StrongholdConfiguration stronghold;

   public StructureSettings(Optional<StrongholdConfiguration> var1, Map<StructureFeature<?>, StructureFeatureConfiguration> var2) {
      this.stronghold = (StrongholdConfiguration)â˜ƒ.orElse(null);
      this.structureConfig = â˜ƒ;
   }

   public StructureSettings(boolean var1) {
      this.structureConfig = Maps.<StructureFeature<?>, StructureFeatureConfiguration>newHashMap(DEFAULTS);
      this.stronghold = â˜ƒ ? DEFAULT_STRONGHOLD : null;
   }

   public Map<StructureFeature<?>, StructureFeatureConfiguration> structureConfig() {
      return this.structureConfig;
   }

   @Nullable
   public StructureFeatureConfiguration getConfig(StructureFeature<?> var1) {
      return (StructureFeatureConfiguration)this.structureConfig.get(â˜ƒ);
   }

   @Nullable
   public StrongholdConfiguration stronghold() {
      return this.stronghold;
   }

   static {
      for(StructureFeature<?> â˜ƒ : Registry.STRUCTURE_FEATURE) {
         if (!DEFAULTS.containsKey(â˜ƒ)) {
            throw new IllegalStateException("Structure feature without default settings: " + Registry.STRUCTURE_FEATURE.getKey(â˜ƒ));
         }
      }

      DEFAULT_STRONGHOLD = new StrongholdConfiguration(32, 3, 128);
   }
}
