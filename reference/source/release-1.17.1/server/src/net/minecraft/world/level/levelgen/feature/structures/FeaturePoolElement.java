package net.minecraft.world.level.levelgen.feature.structures;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class FeaturePoolElement extends StructurePoolElement {
   public static final Codec<FeaturePoolElement> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(ConfiguredFeature.CODEC.fieldOf("feature").forGetter(var0x -> var0x.feature), projectionCodec()).apply(var0, FeaturePoolElement::new)
   );
   private final Supplier<ConfiguredFeature<?, ?>> feature;
   private final CompoundTag defaultJigsawNBT;

   protected FeaturePoolElement(Supplier<ConfiguredFeature<?, ?>> var1, StructureTemplatePool.Projection var2) {
      super(â˜ƒ);
      this.feature = â˜ƒ;
      this.defaultJigsawNBT = this.fillDefaultJigsawNBT();
   }

   private CompoundTag fillDefaultJigsawNBT() {
      CompoundTag â˜ƒ = new CompoundTag();
      â˜ƒ.putString("name", "minecraft:bottom");
      â˜ƒ.putString("final_state", "minecraft:air");
      â˜ƒ.putString("pool", "minecraft:empty");
      â˜ƒ.putString("target", "minecraft:empty");
      â˜ƒ.putString("joint", JigsawBlockEntity.JointType.ROLLABLE.getSerializedName());
      return â˜ƒ;
   }

   @Override
   public Vec3i getSize(StructureManager var1, Rotation var2) {
      return Vec3i.ZERO;
   }

   @Override
   public List<StructureTemplate.StructureBlockInfo> getShuffledJigsawBlocks(StructureManager var1, BlockPos var2, Rotation var3, Random var4) {
      List<StructureTemplate.StructureBlockInfo> â˜ƒ = Lists.<StructureTemplate.StructureBlockInfo>newArrayList();
      â˜ƒ.add(
         new StructureTemplate.StructureBlockInfo(
            â˜ƒ,
            Blocks.JIGSAW.defaultBlockState().setValue(JigsawBlock.ORIENTATION, FrontAndTop.fromFrontAndTop(Direction.DOWN, Direction.SOUTH)),
            this.defaultJigsawNBT
         )
      );
      return â˜ƒ;
   }

   @Override
   public BoundingBox getBoundingBox(StructureManager var1, BlockPos var2, Rotation var3) {
      Vec3i â˜ƒ = this.getSize(â˜ƒ, â˜ƒ);
      return new BoundingBox(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒ.getX() + â˜ƒ.getX(), â˜ƒ.getY() + â˜ƒ.getY(), â˜ƒ.getZ() + â˜ƒ.getZ());
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
      return ((ConfiguredFeature)this.feature.get()).place(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public StructurePoolElementType<?> getType() {
      return StructurePoolElementType.FEATURE;
   }

   public String toString() {
      return "Feature[" + Registry.FEATURE.getKey(((ConfiguredFeature)this.feature.get()).feature()) + "]";
   }
}
