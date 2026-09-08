package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;

public class AxisAlignedLinearPosTest extends PosRuleTest {
   public static final Codec<AxisAlignedLinearPosTest> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.FLOAT.fieldOf("min_chance").orElse(0.0F).forGetter(var0x -> var0x.minChance),
               Codec.FLOAT.fieldOf("max_chance").orElse(0.0F).forGetter(var0x -> var0x.maxChance),
               Codec.INT.fieldOf("min_dist").orElse(0).forGetter(var0x -> var0x.minDist),
               Codec.INT.fieldOf("max_dist").orElse(0).forGetter(var0x -> var0x.maxDist),
               Direction.Axis.CODEC.fieldOf("axis").orElse(Direction.Axis.Y).forGetter(var0x -> var0x.axis)
            )
            .apply(var0, AxisAlignedLinearPosTest::new)
   );
   private final float minChance;
   private final float maxChance;
   private final int minDist;
   private final int maxDist;
   private final Direction.Axis axis;

   public AxisAlignedLinearPosTest(float var1, float var2, int var3, int var4, Direction.Axis var5) {
      if (â˜ƒ >= â˜ƒ) {
         throw new IllegalArgumentException("Invalid range: [" + â˜ƒ + "," + â˜ƒ + "]");
      } else {
         this.minChance = â˜ƒ;
         this.maxChance = â˜ƒ;
         this.minDist = â˜ƒ;
         this.maxDist = â˜ƒ;
         this.axis = â˜ƒ;
      }
   }

   @Override
   public boolean test(BlockPos var1, BlockPos var2, BlockPos var3, Random var4) {
      Direction â˜ƒ = Direction.get(Direction.AxisDirection.POSITIVE, this.axis);
      float â˜ƒx = (float)Math.abs((â˜ƒ.getX() - â˜ƒ.getX()) * â˜ƒ.getStepX());
      float â˜ƒxx = (float)Math.abs((â˜ƒ.getY() - â˜ƒ.getY()) * â˜ƒ.getStepY());
      float â˜ƒxxx = (float)Math.abs((â˜ƒ.getZ() - â˜ƒ.getZ()) * â˜ƒ.getStepZ());
      int â˜ƒxxxx = (int)(â˜ƒx + â˜ƒxx + â˜ƒxxx);
      float â˜ƒxxxxx = â˜ƒ.nextFloat();
      return (double)â˜ƒxxxxx
         <= Mth.clampedLerp((double)this.minChance, (double)this.maxChance, Mth.inverseLerp((double)â˜ƒxxxx, (double)this.minDist, (double)this.maxDist));
   }

   @Override
   protected PosRuleTestType<?> getType() {
      return PosRuleTestType.AXIS_ALIGNED_LINEAR_POS_TEST;
   }
}
