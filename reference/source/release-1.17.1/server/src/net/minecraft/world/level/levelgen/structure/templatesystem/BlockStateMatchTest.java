package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.world.level.block.state.BlockState;

public class BlockStateMatchTest extends RuleTest {
   public static final Codec<BlockStateMatchTest> CODEC = BlockState.CODEC
      .fieldOf("block_state")
      .<BlockStateMatchTest>xmap(BlockStateMatchTest::new, var0 -> var0.blockState)
      .codec();
   private final BlockState blockState;

   public BlockStateMatchTest(BlockState var1) {
      this.blockState = â˜ƒ;
   }

   @Override
   public boolean test(BlockState var1, Random var2) {
      return â˜ƒ == this.blockState;
   }

   @Override
   protected RuleTestType<?> getType() {
      return RuleTestType.BLOCKSTATE_TEST;
   }
}
