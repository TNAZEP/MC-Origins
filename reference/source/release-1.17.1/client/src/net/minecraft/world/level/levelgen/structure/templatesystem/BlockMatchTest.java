package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockMatchTest extends RuleTest {
   public static final Codec<BlockMatchTest> CODEC = Registry.BLOCK.fieldOf("block").<BlockMatchTest>xmap(BlockMatchTest::new, var0 -> var0.block).codec();
   private final Block block;

   public BlockMatchTest(Block var1) {
      this.block = â˜ƒ;
   }

   @Override
   public boolean test(BlockState var1, Random var2) {
      return â˜ƒ.is(this.block);
   }

   @Override
   protected RuleTestType<?> getType() {
      return RuleTestType.BLOCK_TEST;
   }
}
