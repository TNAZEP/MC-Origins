package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class RandomizedIntStateProvider extends BlockStateProvider {
   public static final Codec<RandomizedIntStateProvider> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               BlockStateProvider.CODEC.fieldOf("source").forGetter(var0x -> var0x.source),
               Codec.STRING.fieldOf("property").forGetter(var0x -> var0x.propertyName),
               IntProvider.CODEC.fieldOf("values").forGetter(var0x -> var0x.values)
            )
            .apply(var0, RandomizedIntStateProvider::new)
   );
   private final BlockStateProvider source;
   private final String propertyName;
   @Nullable
   private IntegerProperty property;
   private final IntProvider values;

   public RandomizedIntStateProvider(BlockStateProvider var1, IntegerProperty var2, IntProvider var3) {
      this.source = â˜ƒ;
      this.property = â˜ƒ;
      this.propertyName = â˜ƒ.getName();
      this.values = â˜ƒ;
      Collection<Integer> â˜ƒ = â˜ƒ.getPossibleValues();

      for(int â˜ƒx = â˜ƒ.getMinValue(); â˜ƒx <= â˜ƒ.getMaxValue(); ++â˜ƒx) {
         if (!â˜ƒ.contains(â˜ƒx)) {
            throw new IllegalArgumentException("Property value out of range: " + â˜ƒ.getName() + ": " + â˜ƒx);
         }
      }
   }

   public RandomizedIntStateProvider(BlockStateProvider var1, String var2, IntProvider var3) {
      this.source = â˜ƒ;
      this.propertyName = â˜ƒ;
      this.values = â˜ƒ;
   }

   @Override
   protected BlockStateProviderType<?> type() {
      return BlockStateProviderType.RANDOMIZED_INT_STATE_PROVIDER;
   }

   @Override
   public BlockState getState(Random var1, BlockPos var2) {
      BlockState â˜ƒ = this.source.getState(â˜ƒ, â˜ƒ);
      if (this.property == null || !â˜ƒ.hasProperty(this.property)) {
         this.property = findProperty(â˜ƒ, this.propertyName);
      }

      return â˜ƒ.setValue(this.property, Integer.valueOf(this.values.sample(â˜ƒ)));
   }

   private static IntegerProperty findProperty(BlockState var0, String var1) {
      Collection<Property<?>> â˜ƒ = â˜ƒ.getProperties();
      Optional<IntegerProperty> â˜ƒx = â˜ƒ.stream()
         .filter(var1x -> var1x.getName().equals(â˜ƒ))
         .filter(var0x -> var0x instanceof IntegerProperty)
         .map(var0x -> (IntegerProperty)var0x)
         .findAny();
      return (IntegerProperty)â˜ƒx.orElseThrow(() -> new IllegalArgumentException("Illegal property: " + â˜ƒ));
   }
}
