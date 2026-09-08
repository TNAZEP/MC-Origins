package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratorConfiguration;

public class CarvingMaskDecoratorConfiguration implements DecoratorConfiguration {
   public static final Codec<CarvingMaskDecoratorConfiguration> CODEC = GenerationStep.Carving.CODEC
      .fieldOf("step")
      .<CarvingMaskDecoratorConfiguration>xmap(CarvingMaskDecoratorConfiguration::new, var0 -> var0.step)
      .codec();
   protected final GenerationStep.Carving step;

   public CarvingMaskDecoratorConfiguration(GenerationStep.Carving var1) {
      this.step = â˜ƒ;
   }
}
