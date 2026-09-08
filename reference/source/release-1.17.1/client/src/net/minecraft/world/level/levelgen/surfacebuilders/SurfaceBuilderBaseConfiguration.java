package net.minecraft.world.level.levelgen.surfacebuilders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import net.minecraft.world.level.block.state.BlockState;

public class SurfaceBuilderBaseConfiguration implements SurfaceBuilderConfiguration {
   public static final Codec<SurfaceBuilderBaseConfiguration> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               BlockState.CODEC.fieldOf("top_material").forGetter(var0x -> var0x.topMaterial),
               BlockState.CODEC.fieldOf("under_material").forGetter(var0x -> var0x.underMaterial),
               BlockState.CODEC.fieldOf("underwater_material").forGetter(var0x -> var0x.underwaterMaterial)
            )
            .apply(var0, SurfaceBuilderBaseConfiguration::new)
   );
   private final BlockState topMaterial;
   private final BlockState underMaterial;
   private final BlockState underwaterMaterial;

   public SurfaceBuilderBaseConfiguration(BlockState var1, BlockState var2, BlockState var3) {
      this.topMaterial = â˜ƒ;
      this.underMaterial = â˜ƒ;
      this.underwaterMaterial = â˜ƒ;
   }

   @Override
   public BlockState getTopMaterial() {
      return this.topMaterial;
   }

   @Override
   public BlockState getUnderMaterial() {
      return this.underMaterial;
   }

   @Override
   public BlockState getUnderwaterMaterial() {
      return this.underwaterMaterial;
   }
}
