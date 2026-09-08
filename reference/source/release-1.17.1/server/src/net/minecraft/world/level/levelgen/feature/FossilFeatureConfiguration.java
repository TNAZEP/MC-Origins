package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

public class FossilFeatureConfiguration implements FeatureConfiguration {
   public static final Codec<FossilFeatureConfiguration> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               ResourceLocation.CODEC.listOf().fieldOf("fossil_structures").forGetter(var0x -> var0x.fossilStructures),
               ResourceLocation.CODEC.listOf().fieldOf("overlay_structures").forGetter(var0x -> var0x.overlayStructures),
               StructureProcessorType.LIST_CODEC.fieldOf("fossil_processors").forGetter(var0x -> var0x.fossilProcessors),
               StructureProcessorType.LIST_CODEC.fieldOf("overlay_processors").forGetter(var0x -> var0x.overlayProcessors),
               Codec.intRange(0, 7).fieldOf("max_empty_corners_allowed").forGetter(var0x -> var0x.maxEmptyCornersAllowed)
            )
            .apply(var0, FossilFeatureConfiguration::new)
   );
   public final List<ResourceLocation> fossilStructures;
   public final List<ResourceLocation> overlayStructures;
   public final Supplier<StructureProcessorList> fossilProcessors;
   public final Supplier<StructureProcessorList> overlayProcessors;
   public final int maxEmptyCornersAllowed;

   public FossilFeatureConfiguration(
      List<ResourceLocation> var1, List<ResourceLocation> var2, Supplier<StructureProcessorList> var3, Supplier<StructureProcessorList> var4, int var5
   ) {
      if (â˜ƒ.isEmpty()) {
         throw new IllegalArgumentException("Fossil structure lists need at least one entry");
      } else if (â˜ƒ.size() != â˜ƒ.size()) {
         throw new IllegalArgumentException("Fossil structure lists must be equal lengths");
      } else {
         this.fossilStructures = â˜ƒ;
         this.overlayStructures = â˜ƒ;
         this.fossilProcessors = â˜ƒ;
         this.overlayProcessors = â˜ƒ;
         this.maxEmptyCornersAllowed = â˜ƒ;
      }
   }

   public FossilFeatureConfiguration(
      List<ResourceLocation> var1, List<ResourceLocation> var2, StructureProcessorList var3, StructureProcessorList var4, int var5
   ) {
      this(â˜ƒ, â˜ƒ, () -> â˜ƒ, () -> â˜ƒ, â˜ƒ);
   }
}
