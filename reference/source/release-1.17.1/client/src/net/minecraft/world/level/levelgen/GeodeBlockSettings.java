package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class GeodeBlockSettings {
   public final BlockStateProvider fillingProvider;
   public final BlockStateProvider innerLayerProvider;
   public final BlockStateProvider alternateInnerLayerProvider;
   public final BlockStateProvider middleLayerProvider;
   public final BlockStateProvider outerLayerProvider;
   public final List<BlockState> innerPlacements;
   public final ResourceLocation cannotReplace;
   public final ResourceLocation invalidBlocks;
   public static final Codec<GeodeBlockSettings> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               BlockStateProvider.CODEC.fieldOf("filling_provider").forGetter(var0x -> var0x.fillingProvider),
               BlockStateProvider.CODEC.fieldOf("inner_layer_provider").forGetter(var0x -> var0x.innerLayerProvider),
               BlockStateProvider.CODEC.fieldOf("alternate_inner_layer_provider").forGetter(var0x -> var0x.alternateInnerLayerProvider),
               BlockStateProvider.CODEC.fieldOf("middle_layer_provider").forGetter(var0x -> var0x.middleLayerProvider),
               BlockStateProvider.CODEC.fieldOf("outer_layer_provider").forGetter(var0x -> var0x.outerLayerProvider),
               ExtraCodecs.nonEmptyList(BlockState.CODEC.listOf()).fieldOf("inner_placements").forGetter(var0x -> var0x.innerPlacements),
               ResourceLocation.CODEC.fieldOf("cannot_replace").forGetter(var0x -> var0x.cannotReplace),
               ResourceLocation.CODEC.fieldOf("invalid_blocks").forGetter(var0x -> var0x.invalidBlocks)
            )
            .apply(var0, GeodeBlockSettings::new)
   );

   public GeodeBlockSettings(
      BlockStateProvider var1,
      BlockStateProvider var2,
      BlockStateProvider var3,
      BlockStateProvider var4,
      BlockStateProvider var5,
      List<BlockState> var6,
      ResourceLocation var7,
      ResourceLocation var8
   ) {
      this.fillingProvider = â˜ƒ;
      this.innerLayerProvider = â˜ƒ;
      this.alternateInnerLayerProvider = â˜ƒ;
      this.middleLayerProvider = â˜ƒ;
      this.outerLayerProvider = â˜ƒ;
      this.innerPlacements = â˜ƒ;
      this.cannotReplace = â˜ƒ;
      this.invalidBlocks = â˜ƒ;
   }
}
