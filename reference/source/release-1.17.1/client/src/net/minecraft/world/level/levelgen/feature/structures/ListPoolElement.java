package net.minecraft.world.level.levelgen.feature.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class ListPoolElement extends StructurePoolElement {
   public static final Codec<ListPoolElement> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(StructurePoolElement.CODEC.listOf().fieldOf("elements").forGetter(var0x -> var0x.elements), projectionCodec())
            .apply(var0, ListPoolElement::new)
   );
   private final List<StructurePoolElement> elements;

   public ListPoolElement(List<StructurePoolElement> var1, StructureTemplatePool.Projection var2) {
      super(â˜ƒ);
      if (â˜ƒ.isEmpty()) {
         throw new IllegalArgumentException("Elements are empty");
      } else {
         this.elements = â˜ƒ;
         this.setProjectionOnEachElement(â˜ƒ);
      }
   }

   @Override
   public Vec3i getSize(StructureManager var1, Rotation var2) {
      int â˜ƒ = 0;
      int â˜ƒx = 0;
      int â˜ƒxx = 0;

      for(StructurePoolElement â˜ƒxxx : this.elements) {
         Vec3i â˜ƒxxxx = â˜ƒxxx.getSize(â˜ƒ, â˜ƒ);
         â˜ƒ = Math.max(â˜ƒ, â˜ƒxxxx.getX());
         â˜ƒx = Math.max(â˜ƒx, â˜ƒxxxx.getY());
         â˜ƒxx = Math.max(â˜ƒxx, â˜ƒxxxx.getZ());
      }

      return new Vec3i(â˜ƒ, â˜ƒx, â˜ƒxx);
   }

   @Override
   public List<StructureTemplate.StructureBlockInfo> getShuffledJigsawBlocks(StructureManager var1, BlockPos var2, Rotation var3, Random var4) {
      return ((StructurePoolElement)this.elements.get(0)).getShuffledJigsawBlocks(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public BoundingBox getBoundingBox(StructureManager var1, BlockPos var2, Rotation var3) {
      Stream<BoundingBox> â˜ƒ = this.elements.stream().filter(var0 -> var0 != EmptyPoolElement.INSTANCE).map(var3x -> var3x.getBoundingBox(â˜ƒ, â˜ƒ, â˜ƒ));
      return (BoundingBox)BoundingBox.encapsulatingBoxes(â˜ƒ::iterator)
         .orElseThrow(() -> new IllegalStateException("Unable to calculate boundingbox for ListPoolElement"));
   }

   @Override
   public boolean place(
      StructureManager var1,
      WorldGenLevel var2,
      StructureFeatureManager var3,
      ChunkGenerator var4,
      BlockPos var5,
      BlockPos var6,
      Rotation var7,
      BoundingBox var8,
      Random var9,
      boolean var10
   ) {
      for(StructurePoolElement â˜ƒ : this.elements) {
         if (!â˜ƒ.place(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public StructurePoolElementType<?> getType() {
      return StructurePoolElementType.LIST;
   }

   @Override
   public StructurePoolElement setProjection(StructureTemplatePool.Projection var1) {
      super.setProjection(â˜ƒ);
      this.setProjectionOnEachElement(â˜ƒ);
      return this;
   }

   public String toString() {
      return "List[" + (String)this.elements.stream().map(Object::toString).collect(Collectors.joining(", ")) + "]";
   }

   private void setProjectionOnEachElement(StructureTemplatePool.Projection var1) {
      this.elements.forEach(var1x -> var1x.setProjection(â˜ƒ));
   }
}
