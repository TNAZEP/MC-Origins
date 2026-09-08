package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public class ProcessorRule {
   public static final Codec<ProcessorRule> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               RuleTest.CODEC.fieldOf("input_predicate").forGetter(var0x -> var0x.inputPredicate),
               RuleTest.CODEC.fieldOf("location_predicate").forGetter(var0x -> var0x.locPredicate),
               PosRuleTest.CODEC.optionalFieldOf("position_predicate", PosAlwaysTrueTest.INSTANCE).forGetter(var0x -> var0x.posPredicate),
               BlockState.CODEC.fieldOf("output_state").forGetter(var0x -> var0x.outputState),
               CompoundTag.CODEC.optionalFieldOf("output_nbt").forGetter(var0x -> Optional.ofNullable(var0x.outputTag))
            )
            .apply(var0, ProcessorRule::new)
   );
   private final RuleTest inputPredicate;
   private final RuleTest locPredicate;
   private final PosRuleTest posPredicate;
   private final BlockState outputState;
   @Nullable
   private final CompoundTag outputTag;

   public ProcessorRule(RuleTest var1, RuleTest var2, BlockState var3) {
      this(â˜ƒ, â˜ƒ, PosAlwaysTrueTest.INSTANCE, â˜ƒ, Optional.empty());
   }

   public ProcessorRule(RuleTest var1, RuleTest var2, PosRuleTest var3, BlockState var4) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Optional.empty());
   }

   public ProcessorRule(RuleTest var1, RuleTest var2, PosRuleTest var3, BlockState var4, Optional<CompoundTag> var5) {
      this.inputPredicate = â˜ƒ;
      this.locPredicate = â˜ƒ;
      this.posPredicate = â˜ƒ;
      this.outputState = â˜ƒ;
      this.outputTag = (CompoundTag)â˜ƒ.orElse(null);
   }

   public boolean test(BlockState var1, BlockState var2, BlockPos var3, BlockPos var4, BlockPos var5, Random var6) {
      return this.inputPredicate.test(â˜ƒ, â˜ƒ) && this.locPredicate.test(â˜ƒ, â˜ƒ) && this.posPredicate.test(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public BlockState getOutputState() {
      return this.outputState;
   }

   @Nullable
   public CompoundTag getOutputTag() {
      return this.outputTag;
   }
}
