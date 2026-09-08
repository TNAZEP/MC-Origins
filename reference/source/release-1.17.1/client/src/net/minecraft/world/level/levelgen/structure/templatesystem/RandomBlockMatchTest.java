package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Random;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class RandomBlockMatchTest extends RuleTest {
   public static final Codec<RandomBlockMatchTest> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               Registry.BLOCK.fieldOf("block").forGetter(var0x -> var0x.block), Codec.FLOAT.fieldOf("probability").forGetter(var0x -> var0x.probability)
            )
            .apply(var0, RandomBlockMatchTest::new)
   );
   private final Block block;
   private final float probability;

   public RandomBlockMatchTest(Block var1, float var2) {
      this.block = â˜ƒ;
      this.probability = â˜ƒ;
   }

   @Override
   public boolean test(BlockState var1, Random var2) {
      return â˜ƒ.is(this.block) && â˜ƒ.nextFloat() < this.probability;
   }

   @Override
   protected RuleTestType<?> getType() {
      return RuleTestType.RANDOM_BLOCK_TEST;
   }
}
