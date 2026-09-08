package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratorConfiguration;

public class ChanceDecoratorConfiguration implements DecoratorConfiguration {
   public static final Codec<ChanceDecoratorConfiguration> CODEC = Codec.INT
      .fieldOf("chance")
      .<ChanceDecoratorConfiguration>xmap(ChanceDecoratorConfiguration::new, var0 -> var0.chance)
      .codec();
   public final int chance;

   public ChanceDecoratorConfiguration(int var1) {
      this.chance = â˜ƒ;
   }
}
