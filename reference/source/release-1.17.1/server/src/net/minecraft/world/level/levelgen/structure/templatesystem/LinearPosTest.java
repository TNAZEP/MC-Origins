package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;

public class LinearPosTest extends PosRuleTest {
   public static final Codec<LinearPosTest> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.FLOAT.fieldOf("min_chance").orElse(0.0F).forGetter(var0x -> var0x.minChance),
               Codec.FLOAT.fieldOf("max_chance").orElse(0.0F).forGetter(var0x -> var0x.maxChance),
               Codec.INT.fieldOf("min_dist").orElse(0).forGetter(var0x -> var0x.minDist),
               Codec.INT.fieldOf("max_dist").orElse(0).forGetter(var0x -> var0x.maxDist)
            )
            .apply(var0, LinearPosTest::new)
   );
   private final float minChance;
   private final float maxChance;
   private final int minDist;
   private final int maxDist;

   public LinearPosTest(float var1, float var2, int var3, int var4) {
      if (â˜ƒ >= â˜ƒ) {
         throw new IllegalArgumentException("Invalid range: [" + â˜ƒ + "," + â˜ƒ + "]");
      } else {
         this.minChance = â˜ƒ;
         this.maxChance = â˜ƒ;
         this.minDist = â˜ƒ;
         this.maxDist = â˜ƒ;
      }
   }

   @Override
   public boolean test(BlockPos var1, BlockPos var2, BlockPos var3, Random var4) {
      int â˜ƒ = â˜ƒ.distManhattan(â˜ƒ);
      float â˜ƒx = â˜ƒ.nextFloat();
      return (double)â˜ƒx
         <= Mth.clampedLerp((double)this.minChance, (double)this.maxChance, Mth.inverseLerp((double)â˜ƒ, (double)this.minDist, (double)this.maxDist));
   }

   @Override
   protected PosRuleTestType<?> getType() {
      return PosRuleTestType.LINEAR_POS_TEST;
   }
}
