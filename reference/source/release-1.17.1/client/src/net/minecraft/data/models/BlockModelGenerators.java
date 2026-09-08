package net.minecraft.data.models;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.data.models.blockstates.Condition;
import net.minecraft.data.models.blockstates.MultiPartGenerator;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.DelegatedModel;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.level.block.state.properties.BellAttachType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.ComparatorMode;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import net.minecraft.world.level.block.state.properties.SculkSensorPhase;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.block.state.properties.Tilt;
import net.minecraft.world.level.block.state.properties.WallSide;

public class BlockModelGenerators {
   final Consumer<BlockStateGenerator> blockStateOutput;
   final BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput;
   private final Consumer<Item> skippedAutoModelsOutput;
   final List<Block> nonOrientableTrapdoor = ImmutableList.of(Blocks.OAK_TRAPDOOR, Blocks.DARK_OAK_TRAPDOOR, Blocks.IRON_TRAPDOOR);
   final Map<Block, BlockModelGenerators.BlockStateGeneratorSupplier> fullBlockModelCustomGenerators = ImmutableMap.<Block, BlockModelGenerators.BlockStateGeneratorSupplier>builder(
         
      )
      .put(Blocks.STONE, BlockModelGenerators::createMirroredCubeGenerator)
      .put(Blocks.DEEPSLATE, BlockModelGenerators::createMirroredColumnGenerator)
      .build();
   final Map<Block, TexturedModel> texturedModels = ImmutableMap.<Block, TexturedModel>builder()
      .put(Blocks.SANDSTONE, TexturedModel.TOP_BOTTOM_WITH_WALL.get(Blocks.SANDSTONE))
      .put(Blocks.RED_SANDSTONE, TexturedModel.TOP_BOTTOM_WITH_WALL.get(Blocks.RED_SANDSTONE))
      .put(Blocks.SMOOTH_SANDSTONE, TexturedModel.createAllSame(TextureMapping.getBlockTexture(Blocks.SANDSTONE, "_top")))
      .put(Blocks.SMOOTH_RED_SANDSTONE, TexturedModel.createAllSame(TextureMapping.getBlockTexture(Blocks.RED_SANDSTONE, "_top")))
      .put(
         Blocks.CUT_SANDSTONE,
         TexturedModel.COLUMN.get(Blocks.SANDSTONE).updateTextures(var0 -> var0.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.CUT_SANDSTONE)))
      )
      .put(
         Blocks.CUT_RED_SANDSTONE,
         TexturedModel.COLUMN
            .get(Blocks.RED_SANDSTONE)
            .updateTextures(var0 -> var0.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.CUT_RED_SANDSTONE)))
      )
      .put(Blocks.QUARTZ_BLOCK, TexturedModel.COLUMN.get(Blocks.QUARTZ_BLOCK))
      .put(Blocks.SMOOTH_QUARTZ, TexturedModel.createAllSame(TextureMapping.getBlockTexture(Blocks.QUARTZ_BLOCK, "_bottom")))
      .put(Blocks.BLACKSTONE, TexturedModel.COLUMN_WITH_WALL.get(Blocks.BLACKSTONE))
      .put(Blocks.DEEPSLATE, TexturedModel.COLUMN_WITH_WALL.get(Blocks.DEEPSLATE))
      .put(
         Blocks.CHISELED_QUARTZ_BLOCK,
         TexturedModel.COLUMN
            .get(Blocks.CHISELED_QUARTZ_BLOCK)
            .updateTextures(var0 -> var0.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.CHISELED_QUARTZ_BLOCK)))
      )
      .put(Blocks.CHISELED_SANDSTONE, TexturedModel.COLUMN.get(Blocks.CHISELED_SANDSTONE).updateTextures(var0 -> {
         var0.put(TextureSlot.END, TextureMapping.getBlockTexture(Blocks.SANDSTONE, "_top"));
         var0.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.CHISELED_SANDSTONE));
      }))
      .put(Blocks.CHISELED_RED_SANDSTONE, TexturedModel.COLUMN.get(Blocks.CHISELED_RED_SANDSTONE).updateTextures(var0 -> {
         var0.put(TextureSlot.END, TextureMapping.getBlockTexture(Blocks.RED_SANDSTONE, "_top"));
         var0.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.CHISELED_RED_SANDSTONE));
      }))
      .build();
   static final Map<BlockFamily.Variant, BiConsumer<BlockModelGenerators.BlockFamilyProvider, Block>> SHAPE_CONSUMERS = ImmutableMap.builder()
      .put(BlockFamily.Variant.BUTTON, BlockModelGenerators.BlockFamilyProvider::button)
      .put(BlockFamily.Variant.DOOR, BlockModelGenerators.BlockFamilyProvider::door)
      .put(BlockFamily.Variant.CHISELED, BlockModelGenerators.BlockFamilyProvider::fullBlockVariant)
      .put(BlockFamily.Variant.CRACKED, BlockModelGenerators.BlockFamilyProvider::fullBlockVariant)
      .put(BlockFamily.Variant.FENCE, BlockModelGenerators.BlockFamilyProvider::fence)
      .put(BlockFamily.Variant.FENCE_GATE, BlockModelGenerators.BlockFamilyProvider::fenceGate)
      .put(BlockFamily.Variant.SIGN, BlockModelGenerators.BlockFamilyProvider::sign)
      .put(BlockFamily.Variant.SLAB, BlockModelGenerators.BlockFamilyProvider::slab)
      .put(BlockFamily.Variant.STAIRS, BlockModelGenerators.BlockFamilyProvider::stairs)
      .put(BlockFamily.Variant.PRESSURE_PLATE, BlockModelGenerators.BlockFamilyProvider::pressurePlate)
      .put(BlockFamily.Variant.TRAPDOOR, BlockModelGenerators.BlockFamilyProvider::trapdoor)
      .put(BlockFamily.Variant.WALL, BlockModelGenerators.BlockFamilyProvider::wall)
      .build();
   public static final Map<BooleanProperty, Function<ResourceLocation, Variant>> MULTIFACE_GENERATOR = Util.make(
      Maps.newHashMap(),
      var0 -> {
         var0.put(BlockStateProperties.NORTH, (Function)var0x -> Variant.variant().with(VariantProperties.MODEL, var0x));
         var0.put(
            BlockStateProperties.EAST,
            (Function)var0x -> Variant.variant()
                  .with(VariantProperties.MODEL, var0x)
                  .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                  .with(VariantProperties.UV_LOCK, true)
         );
         var0.put(
            BlockStateProperties.SOUTH,
            (Function)var0x -> Variant.variant()
                  .with(VariantProperties.MODEL, var0x)
                  .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                  .with(VariantProperties.UV_LOCK, true)
         );
         var0.put(
            BlockStateProperties.WEST,
            (Function)var0x -> Variant.variant()
                  .with(VariantProperties.MODEL, var0x)
                  .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                  .with(VariantProperties.UV_LOCK, true)
         );
         var0.put(
            BlockStateProperties.UP,
            (Function)var0x -> Variant.variant()
                  .with(VariantProperties.MODEL, var0x)
                  .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                  .with(VariantProperties.UV_LOCK, true)
         );
         var0.put(
            BlockStateProperties.DOWN,
            (Function)var0x -> Variant.variant()
                  .with(VariantProperties.MODEL, var0x)
                  .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                  .with(VariantProperties.UV_LOCK, true)
         );
      }
   );

   private static BlockStateGenerator createMirroredCubeGenerator(
      Block var0, ResourceLocation var1, TextureMapping var2, BiConsumer<ResourceLocation, Supplier<JsonElement>> var3
   ) {
      ResourceLocation â˜ƒ = ModelTemplates.CUBE_MIRRORED_ALL.create(â˜ƒ, â˜ƒ, â˜ƒ);
      return createRotatedVariant(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private static BlockStateGenerator createMirroredColumnGenerator(
      Block var0, ResourceLocation var1, TextureMapping var2, BiConsumer<ResourceLocation, Supplier<JsonElement>> var3
   ) {
      ResourceLocation â˜ƒ = ModelTemplates.CUBE_COLUMN_MIRRORED.create(â˜ƒ, â˜ƒ, â˜ƒ);
      return createRotatedVariant(â˜ƒ, â˜ƒ, â˜ƒ).with(createRotatedPillar());
   }

   public BlockModelGenerators(Consumer<BlockStateGenerator> var1, BiConsumer<ResourceLocation, Supplier<JsonElement>> var2, Consumer<Item> var3) {
      this.blockStateOutput = â˜ƒ;
      this.modelOutput = â˜ƒ;
      this.skippedAutoModelsOutput = â˜ƒ;
   }

   void skipAutoItemBlock(Block var1) {
      this.skippedAutoModelsOutput.accept(â˜ƒ.asItem());
   }

   void delegateItemModel(Block var1, ResourceLocation var2) {
      this.modelOutput.accept(ModelLocationUtils.getModelLocation(â˜ƒ.asItem()), new DelegatedModel(â˜ƒ));
   }

   private void delegateItemModel(Item var1, ResourceLocation var2) {
      this.modelOutput.accept(ModelLocationUtils.getModelLocation(â˜ƒ), new DelegatedModel(â˜ƒ));
   }

   void createSimpleFlatItemModel(Item var1) {
      ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(â˜ƒ), TextureMapping.layer0(â˜ƒ), this.modelOutput);
   }

   private void createSimpleFlatItemModel(Block var1) {
      Item â˜ƒ = â˜ƒ.asItem();
      if (â˜ƒ != Items.AIR) {
         ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(â˜ƒ), TextureMapping.layer0(â˜ƒ), this.modelOutput);
      }
   }

   private void createSimpleFlatItemModel(Block var1, String var2) {
      Item â˜ƒ = â˜ƒ.asItem();
      ModelTemplates.FLAT_ITEM
         .create(ModelLocationUtils.getModelLocation(â˜ƒ), TextureMapping.layer0(TextureMapping.getBlockTexture(â˜ƒ, â˜ƒ)), this.modelOutput);
   }

   private static PropertyDispatch createHorizontalFacingDispatch() {
      return PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING)
         .select(Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
         .select(Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
         .select(Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
         .select(Direction.NORTH, Variant.variant());
   }

   private static PropertyDispatch createHorizontalFacingDispatchAlt() {
      return PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING)
         .select(Direction.SOUTH, Variant.variant())
         .select(Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
         .select(Direction.NORTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
         .select(Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270));
   }

   private static PropertyDispatch createTorchHorizontalDispatch() {
      return PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING)
         .select(Direction.EAST, Variant.variant())
         .select(Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
         .select(Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
         .select(Direction.NORTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270));
   }

   private static PropertyDispatch createFacingDispatch() {
      return PropertyDispatch.property(BlockStateProperties.FACING)
         .select(Direction.DOWN, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
         .select(Direction.UP, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R270))
         .select(Direction.NORTH, Variant.variant())
         .select(Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
         .select(Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
         .select(Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90));
   }

   private static MultiVariantGenerator createRotatedVariant(Block var0, ResourceLocation var1) {
      return MultiVariantGenerator.multiVariant(â˜ƒ, createRotatedVariants(â˜ƒ));
   }

   private static Variant[] createRotatedVariants(ResourceLocation var0) {
      return new Variant[]{
         Variant.variant().with(VariantProperties.MODEL, â˜ƒ),
         Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90),
         Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180),
         Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
      };
   }

   private static MultiVariantGenerator createRotatedVariant(Block var0, ResourceLocation var1, ResourceLocation var2) {
      return MultiVariantGenerator.multiVariant(
         â˜ƒ,
         Variant.variant().with(VariantProperties.MODEL, â˜ƒ),
         Variant.variant().with(VariantProperties.MODEL, â˜ƒ),
         Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180),
         Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
      );
   }

   private static PropertyDispatch createBooleanModelDispatch(BooleanProperty var0, ResourceLocation var1, ResourceLocation var2) {
      return PropertyDispatch.property(â˜ƒ)
         .select(true, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
         .select(false, Variant.variant().with(VariantProperties.MODEL, â˜ƒ));
   }

   private void createRotatedMirroredVariantBlock(Block var1) {
      ResourceLocation â˜ƒ = TexturedModel.CUBE.create(â˜ƒ, this.modelOutput);
      ResourceLocation â˜ƒx = TexturedModel.CUBE_MIRRORED.create(â˜ƒ, this.modelOutput);
      this.blockStateOutput.accept(createRotatedVariant(â˜ƒ, â˜ƒ, â˜ƒx));
   }

   private void createRotatedVariantBlock(Block var1) {
      ResourceLocation â˜ƒ = TexturedModel.CUBE.create(â˜ƒ, this.modelOutput);
      this.blockStateOutput.accept(createRotatedVariant(â˜ƒ, â˜ƒ));
   }

   static BlockStateGenerator createButton(Block var0, ResourceLocation var1, ResourceLocation var2) {
      return MultiVariantGenerator.multiVariant(â˜ƒ)
         .with(
            PropertyDispatch.property(BlockStateProperties.POWERED)
               .select(false, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               .select(true, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
         )
         .with(
            PropertyDispatch.properties(BlockStateProperties.ATTACH_FACE, BlockStateProperties.HORIZONTAL_FACING)
               .select(AttachFace.FLOOR, Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
               .select(AttachFace.FLOOR, Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
               .select(AttachFace.FLOOR, Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
               .select(AttachFace.FLOOR, Direction.NORTH, Variant.variant())
               .select(
                  AttachFace.WALL,
                  Direction.EAST,
                  Variant.variant()
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  AttachFace.WALL,
                  Direction.WEST,
                  Variant.variant()
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  AttachFace.WALL,
                  Direction.SOUTH,
                  Variant.variant()
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  AttachFace.WALL,
                  Direction.NORTH,
                  Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  AttachFace.CEILING,
                  Direction.EAST,
                  Variant.variant()
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
               )
               .select(
                  AttachFace.CEILING,
                  Direction.WEST,
                  Variant.variant()
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
               )
               .select(AttachFace.CEILING, Direction.SOUTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
               .select(
                  AttachFace.CEILING,
                  Direction.NORTH,
                  Variant.variant()
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
               )
         );
   }

   private static PropertyDispatch.C4<Direction, DoubleBlockHalf, DoorHingeSide, Boolean> configureDoorHalf(
      PropertyDispatch.C4<Direction, DoubleBlockHalf, DoorHingeSide, Boolean> var0, DoubleBlockHalf var1, ResourceLocation var2, ResourceLocation var3
   ) {
      return â˜ƒ.select(Direction.EAST, â˜ƒ, DoorHingeSide.LEFT, false, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
         .select(
            Direction.SOUTH,
            â˜ƒ,
            DoorHingeSide.LEFT,
            false,
            Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
         )
         .select(
            Direction.WEST,
            â˜ƒ,
            DoorHingeSide.LEFT,
            false,
            Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
         )
         .select(
            Direction.NORTH,
            â˜ƒ,
            DoorHingeSide.LEFT,
            false,
            Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
         )
         .select(Direction.EAST, â˜ƒ, DoorHingeSide.RIGHT, false, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
         .select(
            Direction.SOUTH,
            â˜ƒ,
            DoorHingeSide.RIGHT,
            false,
            Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
         )
         .select(
            Direction.WEST,
            â˜ƒ,
            DoorHingeSide.RIGHT,
            false,
            Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
         )
         .select(
            Direction.NORTH,
            â˜ƒ,
            DoorHingeSide.RIGHT,
            false,
            Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
         )
         .select(
            Direction.EAST,
            â˜ƒ,
            DoorHingeSide.LEFT,
            true,
            Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
         )
         .select(
            Direction.SOUTH,
            â˜ƒ,
            DoorHingeSide.LEFT,
            true,
            Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
         )
         .select(
            Direction.WEST,
            â˜ƒ,
            DoorHingeSide.LEFT,
            true,
            Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
         )
         .select(Direction.NORTH, â˜ƒ, DoorHingeSide.LEFT, true, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
         .select(
            Direction.EAST,
            â˜ƒ,
            DoorHingeSide.RIGHT,
            true,
            Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
         )
         .select(Direction.SOUTH, â˜ƒ, DoorHingeSide.RIGHT, true, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
         .select(
            Direction.WEST,
            â˜ƒ,
            DoorHingeSide.RIGHT,
            true,
            Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
         )
         .select(
            Direction.NORTH,
            â˜ƒ,
            DoorHingeSide.RIGHT,
            true,
            Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
         );
   }

   private static BlockStateGenerator createDoor(Block var0, ResourceLocation var1, ResourceLocation var2, ResourceLocation var3, ResourceLocation var4) {
      return MultiVariantGenerator.multiVariant(â˜ƒ)
         .with(
            configureDoorHalf(
               configureDoorHalf(
                  PropertyDispatch.properties(
                     BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.DOUBLE_BLOCK_HALF, BlockStateProperties.DOOR_HINGE, BlockStateProperties.OPEN
                  ),
                  DoubleBlockHalf.LOWER,
                  â˜ƒ,
                  â˜ƒ
               ),
               DoubleBlockHalf.UPPER,
               â˜ƒ,
               â˜ƒ
            )
         );
   }

   static BlockStateGenerator createFence(Block var0, ResourceLocation var1, ResourceLocation var2) {
      return MultiPartGenerator.multiPart(â˜ƒ)
         .with(Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
         .with(
            Condition.condition().term(BlockStateProperties.NORTH, true),
            Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.UV_LOCK, true)
         )
         .with(
            Condition.condition().term(BlockStateProperties.EAST, true),
            Variant.variant()
               .with(VariantProperties.MODEL, â˜ƒ)
               .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
               .with(VariantProperties.UV_LOCK, true)
         )
         .with(
            Condition.condition().term(BlockStateProperties.SOUTH, true),
            Variant.variant()
               .with(VariantProperties.MODEL, â˜ƒ)
               .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
               .with(VariantProperties.UV_LOCK, true)
         )
         .with(
            Condition.condition().term(BlockStateProperties.WEST, true),
            Variant.variant()
               .with(VariantProperties.MODEL, â˜ƒ)
               .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
               .with(VariantProperties.UV_LOCK, true)
         );
   }

   static BlockStateGenerator createWall(Block var0, ResourceLocation var1, ResourceLocation var2, ResourceLocation var3) {
      return MultiPartGenerator.multiPart(â˜ƒ)
         .with(Condition.condition().term(BlockStateProperties.UP, true), Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
         .with(
            Condition.condition().term(BlockStateProperties.NORTH_WALL, WallSide.LOW),
            Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.UV_LOCK, true)
         )
         .with(
            Condition.condition().term(BlockStateProperties.EAST_WALL, WallSide.LOW),
            Variant.variant()
               .with(VariantProperties.MODEL, â˜ƒ)
               .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
               .with(VariantProperties.UV_LOCK, true)
         )
         .with(
            Condition.condition().term(BlockStateProperties.SOUTH_WALL, WallSide.LOW),
            Variant.variant()
               .with(VariantProperties.MODEL, â˜ƒ)
               .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
               .with(VariantProperties.UV_LOCK, true)
         )
         .with(
            Condition.condition().term(BlockStateProperties.WEST_WALL, WallSide.LOW),
            Variant.variant()
               .with(VariantProperties.MODEL, â˜ƒ)
               .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
               .with(VariantProperties.UV_LOCK, true)
         )
         .with(
            Condition.condition().term(BlockStateProperties.NORTH_WALL, WallSide.TALL),
            Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.UV_LOCK, true)
         )
         .with(
            Condition.condition().term(BlockStateProperties.EAST_WALL, WallSide.TALL),
            Variant.variant()
               .with(VariantProperties.MODEL, â˜ƒ)
               .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
               .with(VariantProperties.UV_LOCK, true)
         )
         .with(
            Condition.condition().term(BlockStateProperties.SOUTH_WALL, WallSide.TALL),
            Variant.variant()
               .with(VariantProperties.MODEL, â˜ƒ)
               .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
               .with(VariantProperties.UV_LOCK, true)
         )
         .with(
            Condition.condition().term(BlockStateProperties.WEST_WALL, WallSide.TALL),
            Variant.variant()
               .with(VariantProperties.MODEL, â˜ƒ)
               .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
               .with(VariantProperties.UV_LOCK, true)
         );
   }

   static BlockStateGenerator createFenceGate(Block var0, ResourceLocation var1, ResourceLocation var2, ResourceLocation var3, ResourceLocation var4) {
      return MultiVariantGenerator.multiVariant(â˜ƒ, Variant.variant().with(VariantProperties.UV_LOCK, true))
         .with(createHorizontalFacingDispatchAlt())
         .with(
            PropertyDispatch.properties(BlockStateProperties.IN_WALL, BlockStateProperties.OPEN)
               .select(false, false, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               .select(true, false, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               .select(false, true, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               .select(true, true, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
         );
   }

   static BlockStateGenerator createStairs(Block var0, ResourceLocation var1, ResourceLocation var2, ResourceLocation var3) {
      return MultiVariantGenerator.multiVariant(â˜ƒ)
         .with(
            PropertyDispatch.properties(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.HALF, BlockStateProperties.STAIRS_SHAPE)
               .select(Direction.EAST, Half.BOTTOM, StairsShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               .select(
                  Direction.WEST,
                  Half.BOTTOM,
                  StairsShape.STRAIGHT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.SOUTH,
                  Half.BOTTOM,
                  StairsShape.STRAIGHT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.NORTH,
                  Half.BOTTOM,
                  StairsShape.STRAIGHT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(Direction.EAST, Half.BOTTOM, StairsShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               .select(
                  Direction.WEST,
                  Half.BOTTOM,
                  StairsShape.OUTER_RIGHT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.SOUTH,
                  Half.BOTTOM,
                  StairsShape.OUTER_RIGHT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.NORTH,
                  Half.BOTTOM,
                  StairsShape.OUTER_RIGHT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.EAST,
                  Half.BOTTOM,
                  StairsShape.OUTER_LEFT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.WEST,
                  Half.BOTTOM,
                  StairsShape.OUTER_LEFT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(Direction.SOUTH, Half.BOTTOM, StairsShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               .select(
                  Direction.NORTH,
                  Half.BOTTOM,
                  StairsShape.OUTER_LEFT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(Direction.EAST, Half.BOTTOM, StairsShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               .select(
                  Direction.WEST,
                  Half.BOTTOM,
                  StairsShape.INNER_RIGHT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.SOUTH,
                  Half.BOTTOM,
                  StairsShape.INNER_RIGHT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.NORTH,
                  Half.BOTTOM,
                  StairsShape.INNER_RIGHT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.EAST,
                  Half.BOTTOM,
                  StairsShape.INNER_LEFT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.WEST,
                  Half.BOTTOM,
                  StairsShape.INNER_LEFT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(Direction.SOUTH, Half.BOTTOM, StairsShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               .select(
                  Direction.NORTH,
                  Half.BOTTOM,
                  StairsShape.INNER_LEFT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.EAST,
                  Half.TOP,
                  StairsShape.STRAIGHT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.WEST,
                  Half.TOP,
                  StairsShape.STRAIGHT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.SOUTH,
                  Half.TOP,
                  StairsShape.STRAIGHT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.NORTH,
                  Half.TOP,
                  StairsShape.STRAIGHT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.EAST,
                  Half.TOP,
                  StairsShape.OUTER_RIGHT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.WEST,
                  Half.TOP,
                  StairsShape.OUTER_RIGHT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.SOUTH,
                  Half.TOP,
                  StairsShape.OUTER_RIGHT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.NORTH,
                  Half.TOP,
                  StairsShape.OUTER_RIGHT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.EAST,
                  Half.TOP,
                  StairsShape.OUTER_LEFT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.WEST,
                  Half.TOP,
                  StairsShape.OUTER_LEFT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.SOUTH,
                  Half.TOP,
                  StairsShape.OUTER_LEFT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.NORTH,
                  Half.TOP,
                  StairsShape.OUTER_LEFT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.EAST,
                  Half.TOP,
                  StairsShape.INNER_RIGHT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.WEST,
                  Half.TOP,
                  StairsShape.INNER_RIGHT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.SOUTH,
                  Half.TOP,
                  StairsShape.INNER_RIGHT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.NORTH,
                  Half.TOP,
                  StairsShape.INNER_RIGHT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.EAST,
                  Half.TOP,
                  StairsShape.INNER_LEFT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.WEST,
                  Half.TOP,
                  StairsShape.INNER_LEFT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.SOUTH,
                  Half.TOP,
                  StairsShape.INNER_LEFT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .select(
                  Direction.NORTH,
                  Half.TOP,
                  StairsShape.INNER_LEFT,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     .with(VariantProperties.UV_LOCK, true)
               )
         );
   }

   private static BlockStateGenerator createOrientableTrapdoor(Block var0, ResourceLocation var1, ResourceLocation var2, ResourceLocation var3) {
      return MultiVariantGenerator.multiVariant(â˜ƒ)
         .with(
            PropertyDispatch.properties(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.HALF, BlockStateProperties.OPEN)
               .select(Direction.NORTH, Half.BOTTOM, false, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               .select(
                  Direction.SOUTH,
                  Half.BOTTOM,
                  false,
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
               )
               .select(
                  Direction.EAST,
                  Half.BOTTOM,
                  false,
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
               )
               .select(
                  Direction.WEST,
                  Half.BOTTOM,
                  false,
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
               )
               .select(Direction.NORTH, Half.TOP, false, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               .select(
                  Direction.SOUTH,
                  Half.TOP,
                  false,
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
               )
               .select(
                  Direction.EAST,
                  Half.TOP,
                  false,
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
               )
               .select(
                  Direction.WEST,
                  Half.TOP,
                  false,
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
               )
               .select(Direction.NORTH, Half.BOTTOM, true, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               .select(
                  Direction.SOUTH,
                  Half.BOTTOM,
                  true,
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
               )
               .select(
                  Direction.EAST,
                  Half.BOTTOM,
                  true,
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
               )
               .select(
                  Direction.WEST,
                  Half.BOTTOM,
                  true,
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
               )
               .select(
                  Direction.NORTH,
                  Half.TOP,
                  true,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
               )
               .select(
                  Direction.SOUTH,
                  Half.TOP,
                  true,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0)
               )
               .select(
                  Direction.EAST,
                  Half.TOP,
                  true,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
               )
               .select(
                  Direction.WEST,
                  Half.TOP,
                  true,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
               )
         );
   }

   private static BlockStateGenerator createTrapdoor(Block var0, ResourceLocation var1, ResourceLocation var2, ResourceLocation var3) {
      return MultiVariantGenerator.multiVariant(â˜ƒ)
         .with(
            PropertyDispatch.properties(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.HALF, BlockStateProperties.OPEN)
               .select(Direction.NORTH, Half.BOTTOM, false, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               .select(Direction.SOUTH, Half.BOTTOM, false, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               .select(Direction.EAST, Half.BOTTOM, false, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               .select(Direction.WEST, Half.BOTTOM, false, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               .select(Direction.NORTH, Half.TOP, false, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               .select(Direction.SOUTH, Half.TOP, false, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               .select(Direction.EAST, Half.TOP, false, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               .select(Direction.WEST, Half.TOP, false, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               .select(Direction.NORTH, Half.BOTTOM, true, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               .select(
                  Direction.SOUTH,
                  Half.BOTTOM,
                  true,
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
               )
               .select(
                  Direction.EAST,
                  Half.BOTTOM,
                  true,
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
               )
               .select(
                  Direction.WEST,
                  Half.BOTTOM,
                  true,
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
               )
               .select(Direction.NORTH, Half.TOP, true, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               .select(
                  Direction.SOUTH,
                  Half.TOP,
                  true,
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
               )
               .select(
                  Direction.EAST,
                  Half.TOP,
                  true,
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
               )
               .select(
                  Direction.WEST,
                  Half.TOP,
                  true,
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
               )
         );
   }

   static MultiVariantGenerator createSimpleBlock(Block var0, ResourceLocation var1) {
      return MultiVariantGenerator.multiVariant(â˜ƒ, Variant.variant().with(VariantProperties.MODEL, â˜ƒ));
   }

   private static PropertyDispatch createRotatedPillar() {
      return PropertyDispatch.property(BlockStateProperties.AXIS)
         .select(Direction.Axis.Y, Variant.variant())
         .select(Direction.Axis.Z, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
         .select(
            Direction.Axis.X,
            Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
         );
   }

   static BlockStateGenerator createAxisAlignedPillarBlock(Block var0, ResourceLocation var1) {
      return MultiVariantGenerator.multiVariant(â˜ƒ, Variant.variant().with(VariantProperties.MODEL, â˜ƒ)).with(createRotatedPillar());
   }

   private void createAxisAlignedPillarBlockCustomModel(Block var1, ResourceLocation var2) {
      this.blockStateOutput.accept(createAxisAlignedPillarBlock(â˜ƒ, â˜ƒ));
   }

   private void createAxisAlignedPillarBlock(Block var1, TexturedModel.Provider var2) {
      ResourceLocation â˜ƒ = â˜ƒ.create(â˜ƒ, this.modelOutput);
      this.blockStateOutput.accept(createAxisAlignedPillarBlock(â˜ƒ, â˜ƒ));
   }

   private void createHorizontallyRotatedBlock(Block var1, TexturedModel.Provider var2) {
      ResourceLocation â˜ƒ = â˜ƒ.create(â˜ƒ, this.modelOutput);
      this.blockStateOutput
         .accept(MultiVariantGenerator.multiVariant(â˜ƒ, Variant.variant().with(VariantProperties.MODEL, â˜ƒ)).with(createHorizontalFacingDispatch()));
   }

   static BlockStateGenerator createRotatedPillarWithHorizontalVariant(Block var0, ResourceLocation var1, ResourceLocation var2) {
      return MultiVariantGenerator.multiVariant(â˜ƒ)
         .with(
            PropertyDispatch.property(BlockStateProperties.AXIS)
               .select(Direction.Axis.Y, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               .select(Direction.Axis.Z, Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
               .select(
                  Direction.Axis.X,
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
               )
         );
   }

   private void createRotatedPillarWithHorizontalVariant(Block var1, TexturedModel.Provider var2, TexturedModel.Provider var3) {
      ResourceLocation â˜ƒ = â˜ƒ.create(â˜ƒ, this.modelOutput);
      ResourceLocation â˜ƒx = â˜ƒ.create(â˜ƒ, this.modelOutput);
      this.blockStateOutput.accept(createRotatedPillarWithHorizontalVariant(â˜ƒ, â˜ƒ, â˜ƒx));
   }

   private ResourceLocation createSuffixedVariant(Block var1, String var2, ModelTemplate var3, Function<ResourceLocation, TextureMapping> var4) {
      return â˜ƒ.createWithSuffix(â˜ƒ, â˜ƒ, (TextureMapping)â˜ƒ.apply(TextureMapping.getBlockTexture(â˜ƒ, â˜ƒ)), this.modelOutput);
   }

   static BlockStateGenerator createPressurePlate(Block var0, ResourceLocation var1, ResourceLocation var2) {
      return MultiVariantGenerator.multiVariant(â˜ƒ).with(createBooleanModelDispatch(BlockStateProperties.POWERED, â˜ƒ, â˜ƒ));
   }

   static BlockStateGenerator createSlab(Block var0, ResourceLocation var1, ResourceLocation var2, ResourceLocation var3) {
      return MultiVariantGenerator.multiVariant(â˜ƒ)
         .with(
            PropertyDispatch.property(BlockStateProperties.SLAB_TYPE)
               .select(SlabType.BOTTOM, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               .select(SlabType.TOP, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               .select(SlabType.DOUBLE, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
         );
   }

   private void createTrivialCube(Block var1) {
      this.createTrivialBlock(â˜ƒ, TexturedModel.CUBE);
   }

   private void createTrivialBlock(Block var1, TexturedModel.Provider var2) {
      this.blockStateOutput.accept(createSimpleBlock(â˜ƒ, â˜ƒ.create(â˜ƒ, this.modelOutput)));
   }

   private void createTrivialBlock(Block var1, TextureMapping var2, ModelTemplate var3) {
      ResourceLocation â˜ƒ = â˜ƒ.create(â˜ƒ, â˜ƒ, this.modelOutput);
      this.blockStateOutput.accept(createSimpleBlock(â˜ƒ, â˜ƒ));
   }

   private BlockModelGenerators.BlockFamilyProvider family(Block var1) {
      TexturedModel â˜ƒ = (TexturedModel)this.texturedModels.getOrDefault(â˜ƒ, TexturedModel.CUBE.get(â˜ƒ));
      return new BlockModelGenerators.BlockFamilyProvider(â˜ƒ.getMapping()).fullBlock(â˜ƒ, â˜ƒ.getTemplate());
   }

   void createDoor(Block var1) {
      TextureMapping â˜ƒ = TextureMapping.door(â˜ƒ);
      ResourceLocation â˜ƒx = ModelTemplates.DOOR_BOTTOM.create(â˜ƒ, â˜ƒ, this.modelOutput);
      ResourceLocation â˜ƒxx = ModelTemplates.DOOR_BOTTOM_HINGE.create(â˜ƒ, â˜ƒ, this.modelOutput);
      ResourceLocation â˜ƒxxx = ModelTemplates.DOOR_TOP.create(â˜ƒ, â˜ƒ, this.modelOutput);
      ResourceLocation â˜ƒxxxx = ModelTemplates.DOOR_TOP_HINGE.create(â˜ƒ, â˜ƒ, this.modelOutput);
      this.createSimpleFlatItemModel(â˜ƒ.asItem());
      this.blockStateOutput.accept(createDoor(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx));
   }

   void createOrientableTrapdoor(Block var1) {
      TextureMapping â˜ƒ = TextureMapping.defaultTexture(â˜ƒ);
      ResourceLocation â˜ƒx = ModelTemplates.ORIENTABLE_TRAPDOOR_TOP.create(â˜ƒ, â˜ƒ, this.modelOutput);
      ResourceLocation â˜ƒxx = ModelTemplates.ORIENTABLE_TRAPDOOR_BOTTOM.create(â˜ƒ, â˜ƒ, this.modelOutput);
      ResourceLocation â˜ƒxxx = ModelTemplates.ORIENTABLE_TRAPDOOR_OPEN.create(â˜ƒ, â˜ƒ, this.modelOutput);
      this.blockStateOutput.accept(createOrientableTrapdoor(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx));
      this.delegateItemModel(â˜ƒ, â˜ƒxx);
   }

   void createTrapdoor(Block var1) {
      TextureMapping â˜ƒ = TextureMapping.defaultTexture(â˜ƒ);
      ResourceLocation â˜ƒx = ModelTemplates.TRAPDOOR_TOP.create(â˜ƒ, â˜ƒ, this.modelOutput);
      ResourceLocation â˜ƒxx = ModelTemplates.TRAPDOOR_BOTTOM.create(â˜ƒ, â˜ƒ, this.modelOutput);
      ResourceLocation â˜ƒxxx = ModelTemplates.TRAPDOOR_OPEN.create(â˜ƒ, â˜ƒ, this.modelOutput);
      this.blockStateOutput.accept(createTrapdoor(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx));
      this.delegateItemModel(â˜ƒ, â˜ƒxx);
   }

   private void createBigDripLeafBlock() {
      this.skipAutoItemBlock(Blocks.BIG_DRIPLEAF);
      ResourceLocation â˜ƒ = ModelLocationUtils.getModelLocation(Blocks.BIG_DRIPLEAF);
      ResourceLocation â˜ƒx = ModelLocationUtils.getModelLocation(Blocks.BIG_DRIPLEAF, "_partial_tilt");
      ResourceLocation â˜ƒxx = ModelLocationUtils.getModelLocation(Blocks.BIG_DRIPLEAF, "_full_tilt");
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(Blocks.BIG_DRIPLEAF)
               .with(createHorizontalFacingDispatch())
               .with(
                  PropertyDispatch.property(BlockStateProperties.TILT)
                     .select(Tilt.NONE, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
                     .select(Tilt.UNSTABLE, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
                     .select(Tilt.PARTIAL, Variant.variant().with(VariantProperties.MODEL, â˜ƒx))
                     .select(Tilt.FULL, Variant.variant().with(VariantProperties.MODEL, â˜ƒxx))
               )
         );
   }

   private BlockModelGenerators.WoodProvider woodProvider(Block var1) {
      return new BlockModelGenerators.WoodProvider(TextureMapping.logColumn(â˜ƒ));
   }

   private void createNonTemplateModelBlock(Block var1) {
      this.createNonTemplateModelBlock(â˜ƒ, â˜ƒ);
   }

   private void createNonTemplateModelBlock(Block var1, Block var2) {
      this.blockStateOutput.accept(createSimpleBlock(â˜ƒ, ModelLocationUtils.getModelLocation(â˜ƒ)));
   }

   private void createCrossBlockWithDefaultItem(Block var1, BlockModelGenerators.TintState var2) {
      this.createSimpleFlatItemModel(â˜ƒ);
      this.createCrossBlock(â˜ƒ, â˜ƒ);
   }

   private void createCrossBlockWithDefaultItem(Block var1, BlockModelGenerators.TintState var2, TextureMapping var3) {
      this.createSimpleFlatItemModel(â˜ƒ);
      this.createCrossBlock(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private void createCrossBlock(Block var1, BlockModelGenerators.TintState var2) {
      TextureMapping â˜ƒ = TextureMapping.cross(â˜ƒ);
      this.createCrossBlock(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private void createCrossBlock(Block var1, BlockModelGenerators.TintState var2, TextureMapping var3) {
      ResourceLocation â˜ƒ = â˜ƒ.getCross().create(â˜ƒ, â˜ƒ, this.modelOutput);
      this.blockStateOutput.accept(createSimpleBlock(â˜ƒ, â˜ƒ));
   }

   private void createPlant(Block var1, Block var2, BlockModelGenerators.TintState var3) {
      this.createCrossBlockWithDefaultItem(â˜ƒ, â˜ƒ);
      TextureMapping â˜ƒ = TextureMapping.plant(â˜ƒ);
      ResourceLocation â˜ƒx = â˜ƒ.getCrossPot().create(â˜ƒ, â˜ƒ, this.modelOutput);
      this.blockStateOutput.accept(createSimpleBlock(â˜ƒ, â˜ƒx));
   }

   private void createCoralFans(Block var1, Block var2) {
      TexturedModel â˜ƒ = TexturedModel.CORAL_FAN.get(â˜ƒ);
      ResourceLocation â˜ƒx = â˜ƒ.create(â˜ƒ, this.modelOutput);
      this.blockStateOutput.accept(createSimpleBlock(â˜ƒ, â˜ƒx));
      ResourceLocation â˜ƒxx = ModelTemplates.CORAL_WALL_FAN.create(â˜ƒ, â˜ƒ.getMapping(), this.modelOutput);
      this.blockStateOutput
         .accept(MultiVariantGenerator.multiVariant(â˜ƒ, Variant.variant().with(VariantProperties.MODEL, â˜ƒxx)).with(createHorizontalFacingDispatch()));
      this.createSimpleFlatItemModel(â˜ƒ);
   }

   private void createStems(Block var1, Block var2) {
      this.createSimpleFlatItemModel(â˜ƒ.asItem());
      TextureMapping â˜ƒ = TextureMapping.stem(â˜ƒ);
      TextureMapping â˜ƒx = TextureMapping.attachedStem(â˜ƒ, â˜ƒ);
      ResourceLocation â˜ƒxx = ModelTemplates.ATTACHED_STEM.create(â˜ƒ, â˜ƒx, this.modelOutput);
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(â˜ƒ, Variant.variant().with(VariantProperties.MODEL, â˜ƒxx))
               .with(
                  PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING)
                     .select(Direction.WEST, Variant.variant())
                     .select(Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                     .select(Direction.NORTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                     .select(Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
               )
         );
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(â˜ƒ)
               .with(
                  PropertyDispatch.property(BlockStateProperties.AGE_7)
                     .generate(var3x -> Variant.variant().with(VariantProperties.MODEL, ModelTemplates.STEMS[var3x].create(â˜ƒ, â˜ƒ, this.modelOutput)))
               )
         );
   }

   private void createCoral(Block var1, Block var2, Block var3, Block var4, Block var5, Block var6, Block var7, Block var8) {
      this.createCrossBlockWithDefaultItem(â˜ƒ, BlockModelGenerators.TintState.NOT_TINTED);
      this.createCrossBlockWithDefaultItem(â˜ƒ, BlockModelGenerators.TintState.NOT_TINTED);
      this.createTrivialCube(â˜ƒ);
      this.createTrivialCube(â˜ƒ);
      this.createCoralFans(â˜ƒ, â˜ƒ);
      this.createCoralFans(â˜ƒ, â˜ƒ);
   }

   private void createDoublePlant(Block var1, BlockModelGenerators.TintState var2) {
      this.createSimpleFlatItemModel(â˜ƒ, "_top");
      ResourceLocation â˜ƒ = this.createSuffixedVariant(â˜ƒ, "_top", â˜ƒ.getCross(), TextureMapping::cross);
      ResourceLocation â˜ƒx = this.createSuffixedVariant(â˜ƒ, "_bottom", â˜ƒ.getCross(), TextureMapping::cross);
      this.createDoubleBlock(â˜ƒ, â˜ƒ, â˜ƒx);
   }

   private void createSunflower() {
      this.createSimpleFlatItemModel(Blocks.SUNFLOWER, "_front");
      ResourceLocation â˜ƒ = ModelLocationUtils.getModelLocation(Blocks.SUNFLOWER, "_top");
      ResourceLocation â˜ƒx = this.createSuffixedVariant(
         Blocks.SUNFLOWER, "_bottom", BlockModelGenerators.TintState.NOT_TINTED.getCross(), TextureMapping::cross
      );
      this.createDoubleBlock(Blocks.SUNFLOWER, â˜ƒ, â˜ƒx);
   }

   private void createTallSeagrass() {
      ResourceLocation â˜ƒ = this.createSuffixedVariant(Blocks.TALL_SEAGRASS, "_top", ModelTemplates.SEAGRASS, TextureMapping::defaultTexture);
      ResourceLocation â˜ƒx = this.createSuffixedVariant(Blocks.TALL_SEAGRASS, "_bottom", ModelTemplates.SEAGRASS, TextureMapping::defaultTexture);
      this.createDoubleBlock(Blocks.TALL_SEAGRASS, â˜ƒ, â˜ƒx);
   }

   private void createSmallDripleaf() {
      this.skipAutoItemBlock(Blocks.SMALL_DRIPLEAF);
      ResourceLocation â˜ƒ = ModelLocationUtils.getModelLocation(Blocks.SMALL_DRIPLEAF, "_top");
      ResourceLocation â˜ƒx = ModelLocationUtils.getModelLocation(Blocks.SMALL_DRIPLEAF, "_bottom");
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(Blocks.SMALL_DRIPLEAF)
               .with(createHorizontalFacingDispatch())
               .with(
                  PropertyDispatch.property(BlockStateProperties.DOUBLE_BLOCK_HALF)
                     .select(DoubleBlockHalf.LOWER, Variant.variant().with(VariantProperties.MODEL, â˜ƒx))
                     .select(DoubleBlockHalf.UPPER, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               )
         );
   }

   private void createDoubleBlock(Block var1, ResourceLocation var2, ResourceLocation var3) {
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(â˜ƒ)
               .with(
                  PropertyDispatch.property(BlockStateProperties.DOUBLE_BLOCK_HALF)
                     .select(DoubleBlockHalf.LOWER, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
                     .select(DoubleBlockHalf.UPPER, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               )
         );
   }

   private void createPassiveRail(Block var1) {
      TextureMapping â˜ƒ = TextureMapping.rail(â˜ƒ);
      TextureMapping â˜ƒx = TextureMapping.rail(TextureMapping.getBlockTexture(â˜ƒ, "_corner"));
      ResourceLocation â˜ƒxx = ModelTemplates.RAIL_FLAT.create(â˜ƒ, â˜ƒ, this.modelOutput);
      ResourceLocation â˜ƒxxx = ModelTemplates.RAIL_CURVED.create(â˜ƒ, â˜ƒx, this.modelOutput);
      ResourceLocation â˜ƒxxxx = ModelTemplates.RAIL_RAISED_NE.create(â˜ƒ, â˜ƒ, this.modelOutput);
      ResourceLocation â˜ƒxxxxx = ModelTemplates.RAIL_RAISED_SW.create(â˜ƒ, â˜ƒ, this.modelOutput);
      this.createSimpleFlatItemModel(â˜ƒ);
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(â˜ƒ)
               .with(
                  PropertyDispatch.property(BlockStateProperties.RAIL_SHAPE)
                     .select(RailShape.NORTH_SOUTH, Variant.variant().with(VariantProperties.MODEL, â˜ƒxx))
                     .select(
                        RailShape.EAST_WEST,
                        Variant.variant().with(VariantProperties.MODEL, â˜ƒxx).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     )
                     .select(
                        RailShape.ASCENDING_EAST,
                        Variant.variant().with(VariantProperties.MODEL, â˜ƒxxxx).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     )
                     .select(
                        RailShape.ASCENDING_WEST,
                        Variant.variant().with(VariantProperties.MODEL, â˜ƒxxxxx).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     )
                     .select(RailShape.ASCENDING_NORTH, Variant.variant().with(VariantProperties.MODEL, â˜ƒxxxx))
                     .select(RailShape.ASCENDING_SOUTH, Variant.variant().with(VariantProperties.MODEL, â˜ƒxxxxx))
                     .select(RailShape.SOUTH_EAST, Variant.variant().with(VariantProperties.MODEL, â˜ƒxxx))
                     .select(
                        RailShape.SOUTH_WEST,
                        Variant.variant().with(VariantProperties.MODEL, â˜ƒxxx).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     )
                     .select(
                        RailShape.NORTH_WEST,
                        Variant.variant().with(VariantProperties.MODEL, â˜ƒxxx).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     )
                     .select(
                        RailShape.NORTH_EAST,
                        Variant.variant().with(VariantProperties.MODEL, â˜ƒxxx).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     )
               )
         );
   }

   private void createActiveRail(Block var1) {
      ResourceLocation â˜ƒ = this.createSuffixedVariant(â˜ƒ, "", ModelTemplates.RAIL_FLAT, TextureMapping::rail);
      ResourceLocation â˜ƒx = this.createSuffixedVariant(â˜ƒ, "", ModelTemplates.RAIL_RAISED_NE, TextureMapping::rail);
      ResourceLocation â˜ƒxx = this.createSuffixedVariant(â˜ƒ, "", ModelTemplates.RAIL_RAISED_SW, TextureMapping::rail);
      ResourceLocation â˜ƒxxx = this.createSuffixedVariant(â˜ƒ, "_on", ModelTemplates.RAIL_FLAT, TextureMapping::rail);
      ResourceLocation â˜ƒxxxx = this.createSuffixedVariant(â˜ƒ, "_on", ModelTemplates.RAIL_RAISED_NE, TextureMapping::rail);
      ResourceLocation â˜ƒxxxxx = this.createSuffixedVariant(â˜ƒ, "_on", ModelTemplates.RAIL_RAISED_SW, TextureMapping::rail);
      PropertyDispatch â˜ƒxxxxxx = PropertyDispatch.properties(BlockStateProperties.POWERED, BlockStateProperties.RAIL_SHAPE_STRAIGHT)
         .generate((var6x, var7x) -> {
            switch(var7x) {
               case NORTH_SOUTH:
                  return Variant.variant().with(VariantProperties.MODEL, var6x ? â˜ƒ : â˜ƒ);
               case EAST_WEST:
                  return Variant.variant().with(VariantProperties.MODEL, var6x ? â˜ƒ : â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90);
               case ASCENDING_EAST:
                  return Variant.variant().with(VariantProperties.MODEL, var6x ? â˜ƒ : â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90);
               case ASCENDING_WEST:
                  return Variant.variant().with(VariantProperties.MODEL, var6x ? â˜ƒ : â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90);
               case ASCENDING_NORTH:
                  return Variant.variant().with(VariantProperties.MODEL, var6x ? â˜ƒ : â˜ƒ);
               case ASCENDING_SOUTH:
                  return Variant.variant().with(VariantProperties.MODEL, var6x ? â˜ƒ : â˜ƒ);
               default:
                  throw new UnsupportedOperationException("Fix you generator!");
            }
         });
      this.createSimpleFlatItemModel(â˜ƒ);
      this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(â˜ƒ).with(â˜ƒxxxxxx));
   }

   private BlockModelGenerators.BlockEntityModelGenerator blockEntityModels(ResourceLocation var1, Block var2) {
      return new BlockModelGenerators.BlockEntityModelGenerator(â˜ƒ, â˜ƒ);
   }

   private BlockModelGenerators.BlockEntityModelGenerator blockEntityModels(Block var1, Block var2) {
      return new BlockModelGenerators.BlockEntityModelGenerator(ModelLocationUtils.getModelLocation(â˜ƒ), â˜ƒ);
   }

   private void createAirLikeBlock(Block var1, Item var2) {
      ResourceLocation â˜ƒ = ModelTemplates.PARTICLE_ONLY.create(â˜ƒ, TextureMapping.particleFromItem(â˜ƒ), this.modelOutput);
      this.blockStateOutput.accept(createSimpleBlock(â˜ƒ, â˜ƒ));
   }

   private void createAirLikeBlock(Block var1, ResourceLocation var2) {
      ResourceLocation â˜ƒ = ModelTemplates.PARTICLE_ONLY.create(â˜ƒ, TextureMapping.particle(â˜ƒ), this.modelOutput);
      this.blockStateOutput.accept(createSimpleBlock(â˜ƒ, â˜ƒ));
   }

   private void createFullAndCarpetBlocks(Block var1, Block var2) {
      this.createTrivialCube(â˜ƒ);
      ResourceLocation â˜ƒ = TexturedModel.CARPET.get(â˜ƒ).create(â˜ƒ, this.modelOutput);
      this.blockStateOutput.accept(createSimpleBlock(â˜ƒ, â˜ƒ));
   }

   private void createColoredBlockWithRandomRotations(TexturedModel.Provider var1, Block... var2) {
      for(Block â˜ƒ : â˜ƒ) {
         ResourceLocation â˜ƒx = â˜ƒ.create(â˜ƒ, this.modelOutput);
         this.blockStateOutput.accept(createRotatedVariant(â˜ƒ, â˜ƒx));
      }
   }

   private void createColoredBlockWithStateRotations(TexturedModel.Provider var1, Block... var2) {
      for(Block â˜ƒ : â˜ƒ) {
         ResourceLocation â˜ƒx = â˜ƒ.create(â˜ƒ, this.modelOutput);
         this.blockStateOutput
            .accept(MultiVariantGenerator.multiVariant(â˜ƒ, Variant.variant().with(VariantProperties.MODEL, â˜ƒx)).with(createHorizontalFacingDispatchAlt()));
      }
   }

   private void createGlassBlocks(Block var1, Block var2) {
      this.createTrivialCube(â˜ƒ);
      TextureMapping â˜ƒ = TextureMapping.pane(â˜ƒ, â˜ƒ);
      ResourceLocation â˜ƒx = ModelTemplates.STAINED_GLASS_PANE_POST.create(â˜ƒ, â˜ƒ, this.modelOutput);
      ResourceLocation â˜ƒxx = ModelTemplates.STAINED_GLASS_PANE_SIDE.create(â˜ƒ, â˜ƒ, this.modelOutput);
      ResourceLocation â˜ƒxxx = ModelTemplates.STAINED_GLASS_PANE_SIDE_ALT.create(â˜ƒ, â˜ƒ, this.modelOutput);
      ResourceLocation â˜ƒxxxx = ModelTemplates.STAINED_GLASS_PANE_NOSIDE.create(â˜ƒ, â˜ƒ, this.modelOutput);
      ResourceLocation â˜ƒxxxxx = ModelTemplates.STAINED_GLASS_PANE_NOSIDE_ALT.create(â˜ƒ, â˜ƒ, this.modelOutput);
      Item â˜ƒxxxxxx = â˜ƒ.asItem();
      ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(â˜ƒxxxxxx), TextureMapping.layer0(â˜ƒ), this.modelOutput);
      this.blockStateOutput
         .accept(
            MultiPartGenerator.multiPart(â˜ƒ)
               .with(Variant.variant().with(VariantProperties.MODEL, â˜ƒx))
               .with(Condition.condition().term(BlockStateProperties.NORTH, true), Variant.variant().with(VariantProperties.MODEL, â˜ƒxx))
               .with(
                  Condition.condition().term(BlockStateProperties.EAST, true),
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒxx).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
               )
               .with(Condition.condition().term(BlockStateProperties.SOUTH, true), Variant.variant().with(VariantProperties.MODEL, â˜ƒxxx))
               .with(
                  Condition.condition().term(BlockStateProperties.WEST, true),
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒxxx).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
               )
               .with(Condition.condition().term(BlockStateProperties.NORTH, false), Variant.variant().with(VariantProperties.MODEL, â˜ƒxxxx))
               .with(Condition.condition().term(BlockStateProperties.EAST, false), Variant.variant().with(VariantProperties.MODEL, â˜ƒxxxxx))
               .with(
                  Condition.condition().term(BlockStateProperties.SOUTH, false),
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒxxxxx).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
               )
               .with(
                  Condition.condition().term(BlockStateProperties.WEST, false),
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒxxxx).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
               )
         );
   }

   private void createCommandBlock(Block var1) {
      TextureMapping â˜ƒ = TextureMapping.commandBlock(â˜ƒ);
      ResourceLocation â˜ƒx = ModelTemplates.COMMAND_BLOCK.create(â˜ƒ, â˜ƒ, this.modelOutput);
      ResourceLocation â˜ƒxx = this.createSuffixedVariant(
         â˜ƒ, "_conditional", ModelTemplates.COMMAND_BLOCK, var1x -> â˜ƒ.copyAndUpdate(TextureSlot.SIDE, var1x)
      );
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(â˜ƒ)
               .with(createBooleanModelDispatch(BlockStateProperties.CONDITIONAL, â˜ƒxx, â˜ƒx))
               .with(createFacingDispatch())
         );
   }

   private void createAnvil(Block var1) {
      ResourceLocation â˜ƒ = TexturedModel.ANVIL.create(â˜ƒ, this.modelOutput);
      this.blockStateOutput.accept(createSimpleBlock(â˜ƒ, â˜ƒ).with(createHorizontalFacingDispatchAlt()));
   }

   private List<Variant> createBambooModels(int var1) {
      String â˜ƒ = "_age" + â˜ƒ;
      return (List<Variant>)IntStream.range(1, 5)
         .mapToObj(var1x -> Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.BAMBOO, var1x + â˜ƒ)))
         .collect(Collectors.toList());
   }

   private void createBamboo() {
      this.skipAutoItemBlock(Blocks.BAMBOO);
      this.blockStateOutput
         .accept(
            MultiPartGenerator.multiPart(Blocks.BAMBOO)
               .with(Condition.condition().term(BlockStateProperties.AGE_1, 0), this.createBambooModels(0))
               .with(Condition.condition().term(BlockStateProperties.AGE_1, 1), this.createBambooModels(1))
               .with(
                  Condition.condition().term(BlockStateProperties.BAMBOO_LEAVES, BambooLeaves.SMALL),
                  Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.BAMBOO, "_small_leaves"))
               )
               .with(
                  Condition.condition().term(BlockStateProperties.BAMBOO_LEAVES, BambooLeaves.LARGE),
                  Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.BAMBOO, "_large_leaves"))
               )
         );
   }

   private PropertyDispatch createColumnWithFacing() {
      return PropertyDispatch.property(BlockStateProperties.FACING)
         .select(Direction.DOWN, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
         .select(Direction.UP, Variant.variant())
         .select(Direction.NORTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
         .select(
            Direction.SOUTH,
            Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
         )
         .select(
            Direction.WEST,
            Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
         )
         .select(
            Direction.EAST,
            Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
         );
   }

   private void createBarrel() {
      ResourceLocation â˜ƒ = TextureMapping.getBlockTexture(Blocks.BARREL, "_top_open");
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(Blocks.BARREL)
               .with(this.createColumnWithFacing())
               .with(
                  PropertyDispatch.property(BlockStateProperties.OPEN)
                     .select(false, Variant.variant().with(VariantProperties.MODEL, TexturedModel.CUBE_TOP_BOTTOM.create(Blocks.BARREL, this.modelOutput)))
                     .select(
                        true,
                        Variant.variant()
                           .with(
                              VariantProperties.MODEL,
                              TexturedModel.CUBE_TOP_BOTTOM
                                 .get(Blocks.BARREL)
                                 .updateTextures(var1x -> var1x.put(TextureSlot.TOP, â˜ƒ))
                                 .createWithSuffix(Blocks.BARREL, "_open", this.modelOutput)
                           )
                     )
               )
         );
   }

   private static <T extends Comparable<T>> PropertyDispatch createEmptyOrFullDispatch(Property<T> var0, T var1, ResourceLocation var2, ResourceLocation var3) {
      Variant â˜ƒ = Variant.variant().with(VariantProperties.MODEL, â˜ƒ);
      Variant â˜ƒx = Variant.variant().with(VariantProperties.MODEL, â˜ƒ);
      return PropertyDispatch.property(â˜ƒ).generate(var3x -> {
         boolean â˜ƒ = var3x.compareTo(â˜ƒ) >= 0;
         return â˜ƒ ? â˜ƒ : â˜ƒ;
      });
   }

   private void createBeeNest(Block var1, Function<Block, TextureMapping> var2) {
      TextureMapping â˜ƒ = ((TextureMapping)â˜ƒ.apply(â˜ƒ)).copyForced(TextureSlot.SIDE, TextureSlot.PARTICLE);
      TextureMapping â˜ƒx = â˜ƒ.copyAndUpdate(TextureSlot.FRONT, TextureMapping.getBlockTexture(â˜ƒ, "_front_honey"));
      ResourceLocation â˜ƒxx = ModelTemplates.CUBE_ORIENTABLE_TOP_BOTTOM.create(â˜ƒ, â˜ƒ, this.modelOutput);
      ResourceLocation â˜ƒxxx = ModelTemplates.CUBE_ORIENTABLE_TOP_BOTTOM.createWithSuffix(â˜ƒ, "_honey", â˜ƒx, this.modelOutput);
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(â˜ƒ)
               .with(createHorizontalFacingDispatch())
               .with(createEmptyOrFullDispatch(BlockStateProperties.LEVEL_HONEY, 5, â˜ƒxxx, â˜ƒxx))
         );
   }

   private void createCropBlock(Block var1, Property<Integer> var2, int... var3) {
      if (â˜ƒ.getPossibleValues().size() != â˜ƒ.length) {
         throw new IllegalArgumentException();
      } else {
         Int2ObjectMap<ResourceLocation> â˜ƒ = new Int2ObjectOpenHashMap<>();
         PropertyDispatch â˜ƒx = PropertyDispatch.property(â˜ƒ)
            .generate(
               var4x -> {
                  int â˜ƒ = â˜ƒ[var4x];
                  ResourceLocation â˜ƒx = â˜ƒ.computeIfAbsent(
                     â˜ƒ, var3x -> this.createSuffixedVariant(â˜ƒ, "_stage" + â˜ƒ, ModelTemplates.CROP, TextureMapping::crop)
                  );
                  return Variant.variant().with(VariantProperties.MODEL, â˜ƒx);
               }
            );
         this.createSimpleFlatItemModel(â˜ƒ.asItem());
         this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(â˜ƒ).with(â˜ƒx));
      }
   }

   private void createBell() {
      ResourceLocation â˜ƒ = ModelLocationUtils.getModelLocation(Blocks.BELL, "_floor");
      ResourceLocation â˜ƒx = ModelLocationUtils.getModelLocation(Blocks.BELL, "_ceiling");
      ResourceLocation â˜ƒxx = ModelLocationUtils.getModelLocation(Blocks.BELL, "_wall");
      ResourceLocation â˜ƒxxx = ModelLocationUtils.getModelLocation(Blocks.BELL, "_between_walls");
      this.createSimpleFlatItemModel(Items.BELL);
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(Blocks.BELL)
               .with(
                  PropertyDispatch.properties(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.BELL_ATTACHMENT)
                     .select(Direction.NORTH, BellAttachType.FLOOR, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
                     .select(
                        Direction.SOUTH,
                        BellAttachType.FLOOR,
                        Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     )
                     .select(
                        Direction.EAST,
                        BellAttachType.FLOOR,
                        Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     )
                     .select(
                        Direction.WEST,
                        BellAttachType.FLOOR,
                        Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     )
                     .select(Direction.NORTH, BellAttachType.CEILING, Variant.variant().with(VariantProperties.MODEL, â˜ƒx))
                     .select(
                        Direction.SOUTH,
                        BellAttachType.CEILING,
                        Variant.variant().with(VariantProperties.MODEL, â˜ƒx).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     )
                     .select(
                        Direction.EAST,
                        BellAttachType.CEILING,
                        Variant.variant().with(VariantProperties.MODEL, â˜ƒx).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     )
                     .select(
                        Direction.WEST,
                        BellAttachType.CEILING,
                        Variant.variant().with(VariantProperties.MODEL, â˜ƒx).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     )
                     .select(
                        Direction.NORTH,
                        BellAttachType.SINGLE_WALL,
                        Variant.variant().with(VariantProperties.MODEL, â˜ƒxx).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     )
                     .select(
                        Direction.SOUTH,
                        BellAttachType.SINGLE_WALL,
                        Variant.variant().with(VariantProperties.MODEL, â˜ƒxx).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     )
                     .select(Direction.EAST, BellAttachType.SINGLE_WALL, Variant.variant().with(VariantProperties.MODEL, â˜ƒxx))
                     .select(
                        Direction.WEST,
                        BellAttachType.SINGLE_WALL,
                        Variant.variant().with(VariantProperties.MODEL, â˜ƒxx).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     )
                     .select(
                        Direction.SOUTH,
                        BellAttachType.DOUBLE_WALL,
                        Variant.variant().with(VariantProperties.MODEL, â˜ƒxxx).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     )
                     .select(
                        Direction.NORTH,
                        BellAttachType.DOUBLE_WALL,
                        Variant.variant().with(VariantProperties.MODEL, â˜ƒxxx).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     )
                     .select(Direction.EAST, BellAttachType.DOUBLE_WALL, Variant.variant().with(VariantProperties.MODEL, â˜ƒxxx))
                     .select(
                        Direction.WEST,
                        BellAttachType.DOUBLE_WALL,
                        Variant.variant().with(VariantProperties.MODEL, â˜ƒxxx).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     )
               )
         );
   }

   private void createGrindstone() {
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(
                  Blocks.GRINDSTONE, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.GRINDSTONE))
               )
               .with(
                  PropertyDispatch.properties(BlockStateProperties.ATTACH_FACE, BlockStateProperties.HORIZONTAL_FACING)
                     .select(AttachFace.FLOOR, Direction.NORTH, Variant.variant())
                     .select(AttachFace.FLOOR, Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                     .select(AttachFace.FLOOR, Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                     .select(AttachFace.FLOOR, Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                     .select(AttachFace.WALL, Direction.NORTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
                     .select(
                        AttachFace.WALL,
                        Direction.EAST,
                        Variant.variant()
                           .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     )
                     .select(
                        AttachFace.WALL,
                        Direction.SOUTH,
                        Variant.variant()
                           .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     )
                     .select(
                        AttachFace.WALL,
                        Direction.WEST,
                        Variant.variant()
                           .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     )
                     .select(AttachFace.CEILING, Direction.SOUTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
                     .select(
                        AttachFace.CEILING,
                        Direction.WEST,
                        Variant.variant()
                           .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     )
                     .select(
                        AttachFace.CEILING,
                        Direction.NORTH,
                        Variant.variant()
                           .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     )
                     .select(
                        AttachFace.CEILING,
                        Direction.EAST,
                        Variant.variant()
                           .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     )
               )
         );
   }

   private void createFurnace(Block var1, TexturedModel.Provider var2) {
      ResourceLocation â˜ƒ = â˜ƒ.create(â˜ƒ, this.modelOutput);
      ResourceLocation â˜ƒx = TextureMapping.getBlockTexture(â˜ƒ, "_front_on");
      ResourceLocation â˜ƒxx = â˜ƒ.get(â˜ƒ).updateTextures(var1x -> var1x.put(TextureSlot.FRONT, â˜ƒ)).createWithSuffix(â˜ƒ, "_on", this.modelOutput);
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(â˜ƒ)
               .with(createBooleanModelDispatch(BlockStateProperties.LIT, â˜ƒxx, â˜ƒ))
               .with(createHorizontalFacingDispatch())
         );
   }

   private void createCampfires(Block... var1) {
      ResourceLocation â˜ƒ = ModelLocationUtils.decorateBlockModelLocation("campfire_off");

      for(Block â˜ƒx : â˜ƒ) {
         ResourceLocation â˜ƒxx = ModelTemplates.CAMPFIRE.create(â˜ƒx, TextureMapping.campfire(â˜ƒx), this.modelOutput);
         this.createSimpleFlatItemModel(â˜ƒx.asItem());
         this.blockStateOutput
            .accept(
               MultiVariantGenerator.multiVariant(â˜ƒx)
                  .with(createBooleanModelDispatch(BlockStateProperties.LIT, â˜ƒxx, â˜ƒ))
                  .with(createHorizontalFacingDispatchAlt())
            );
      }
   }

   private void createAzalea(Block var1) {
      ResourceLocation â˜ƒ = ModelTemplates.AZALEA.create(â˜ƒ, TextureMapping.cubeTop(â˜ƒ), this.modelOutput);
      this.blockStateOutput.accept(createSimpleBlock(â˜ƒ, â˜ƒ));
   }

   private void createPottedAzalea(Block var1) {
      ResourceLocation â˜ƒ = ModelTemplates.POTTED_AZALEA.create(â˜ƒ, TextureMapping.cubeTop(â˜ƒ), this.modelOutput);
      this.blockStateOutput.accept(createSimpleBlock(â˜ƒ, â˜ƒ));
   }

   private void createBookshelf() {
      TextureMapping â˜ƒ = TextureMapping.column(TextureMapping.getBlockTexture(Blocks.BOOKSHELF), TextureMapping.getBlockTexture(Blocks.OAK_PLANKS));
      ResourceLocation â˜ƒx = ModelTemplates.CUBE_COLUMN.create(Blocks.BOOKSHELF, â˜ƒ, this.modelOutput);
      this.blockStateOutput.accept(createSimpleBlock(Blocks.BOOKSHELF, â˜ƒx));
   }

   private void createRedstoneWire() {
      this.createSimpleFlatItemModel(Items.REDSTONE);
      this.blockStateOutput
         .accept(
            MultiPartGenerator.multiPart(Blocks.REDSTONE_WIRE)
               .with(
                  Condition.or(
                     Condition.condition()
                        .term(BlockStateProperties.NORTH_REDSTONE, RedstoneSide.NONE)
                        .term(BlockStateProperties.EAST_REDSTONE, RedstoneSide.NONE)
                        .term(BlockStateProperties.SOUTH_REDSTONE, RedstoneSide.NONE)
                        .term(BlockStateProperties.WEST_REDSTONE, RedstoneSide.NONE),
                     Condition.condition()
                        .term(BlockStateProperties.NORTH_REDSTONE, RedstoneSide.SIDE, RedstoneSide.UP)
                        .term(BlockStateProperties.EAST_REDSTONE, RedstoneSide.SIDE, RedstoneSide.UP),
                     Condition.condition()
                        .term(BlockStateProperties.EAST_REDSTONE, RedstoneSide.SIDE, RedstoneSide.UP)
                        .term(BlockStateProperties.SOUTH_REDSTONE, RedstoneSide.SIDE, RedstoneSide.UP),
                     Condition.condition()
                        .term(BlockStateProperties.SOUTH_REDSTONE, RedstoneSide.SIDE, RedstoneSide.UP)
                        .term(BlockStateProperties.WEST_REDSTONE, RedstoneSide.SIDE, RedstoneSide.UP),
                     Condition.condition()
                        .term(BlockStateProperties.WEST_REDSTONE, RedstoneSide.SIDE, RedstoneSide.UP)
                        .term(BlockStateProperties.NORTH_REDSTONE, RedstoneSide.SIDE, RedstoneSide.UP)
                  ),
                  Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.decorateBlockModelLocation("redstone_dust_dot"))
               )
               .with(
                  Condition.condition().term(BlockStateProperties.NORTH_REDSTONE, RedstoneSide.SIDE, RedstoneSide.UP),
                  Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.decorateBlockModelLocation("redstone_dust_side0"))
               )
               .with(
                  Condition.condition().term(BlockStateProperties.SOUTH_REDSTONE, RedstoneSide.SIDE, RedstoneSide.UP),
                  Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.decorateBlockModelLocation("redstone_dust_side_alt0"))
               )
               .with(
                  Condition.condition().term(BlockStateProperties.EAST_REDSTONE, RedstoneSide.SIDE, RedstoneSide.UP),
                  Variant.variant()
                     .with(VariantProperties.MODEL, ModelLocationUtils.decorateBlockModelLocation("redstone_dust_side_alt1"))
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
               )
               .with(
                  Condition.condition().term(BlockStateProperties.WEST_REDSTONE, RedstoneSide.SIDE, RedstoneSide.UP),
                  Variant.variant()
                     .with(VariantProperties.MODEL, ModelLocationUtils.decorateBlockModelLocation("redstone_dust_side1"))
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
               )
               .with(
                  Condition.condition().term(BlockStateProperties.NORTH_REDSTONE, RedstoneSide.UP),
                  Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.decorateBlockModelLocation("redstone_dust_up"))
               )
               .with(
                  Condition.condition().term(BlockStateProperties.EAST_REDSTONE, RedstoneSide.UP),
                  Variant.variant()
                     .with(VariantProperties.MODEL, ModelLocationUtils.decorateBlockModelLocation("redstone_dust_up"))
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
               )
               .with(
                  Condition.condition().term(BlockStateProperties.SOUTH_REDSTONE, RedstoneSide.UP),
                  Variant.variant()
                     .with(VariantProperties.MODEL, ModelLocationUtils.decorateBlockModelLocation("redstone_dust_up"))
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
               )
               .with(
                  Condition.condition().term(BlockStateProperties.WEST_REDSTONE, RedstoneSide.UP),
                  Variant.variant()
                     .with(VariantProperties.MODEL, ModelLocationUtils.decorateBlockModelLocation("redstone_dust_up"))
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
               )
         );
   }

   private void createComparator() {
      this.createSimpleFlatItemModel(Items.COMPARATOR);
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(Blocks.COMPARATOR)
               .with(createHorizontalFacingDispatchAlt())
               .with(
                  PropertyDispatch.properties(BlockStateProperties.MODE_COMPARATOR, BlockStateProperties.POWERED)
                     .select(
                        ComparatorMode.COMPARE, false, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.COMPARATOR))
                     )
                     .select(
                        ComparatorMode.COMPARE,
                        true,
                        Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.COMPARATOR, "_on"))
                     )
                     .select(
                        ComparatorMode.SUBTRACT,
                        false,
                        Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.COMPARATOR, "_subtract"))
                     )
                     .select(
                        ComparatorMode.SUBTRACT,
                        true,
                        Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.COMPARATOR, "_on_subtract"))
                     )
               )
         );
   }

   private void createSmoothStoneSlab() {
      TextureMapping â˜ƒ = TextureMapping.cube(Blocks.SMOOTH_STONE);
      TextureMapping â˜ƒx = TextureMapping.column(TextureMapping.getBlockTexture(Blocks.SMOOTH_STONE_SLAB, "_side"), â˜ƒ.get(TextureSlot.TOP));
      ResourceLocation â˜ƒxx = ModelTemplates.SLAB_BOTTOM.create(Blocks.SMOOTH_STONE_SLAB, â˜ƒx, this.modelOutput);
      ResourceLocation â˜ƒxxx = ModelTemplates.SLAB_TOP.create(Blocks.SMOOTH_STONE_SLAB, â˜ƒx, this.modelOutput);
      ResourceLocation â˜ƒxxxx = ModelTemplates.CUBE_COLUMN.createWithOverride(Blocks.SMOOTH_STONE_SLAB, "_double", â˜ƒx, this.modelOutput);
      this.blockStateOutput.accept(createSlab(Blocks.SMOOTH_STONE_SLAB, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx));
      this.blockStateOutput.accept(createSimpleBlock(Blocks.SMOOTH_STONE, ModelTemplates.CUBE_ALL.create(Blocks.SMOOTH_STONE, â˜ƒ, this.modelOutput)));
   }

   private void createBrewingStand() {
      this.createSimpleFlatItemModel(Items.BREWING_STAND);
      this.blockStateOutput
         .accept(
            MultiPartGenerator.multiPart(Blocks.BREWING_STAND)
               .with(Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.BREWING_STAND)))
               .with(
                  Condition.condition().term(BlockStateProperties.HAS_BOTTLE_0, true),
                  Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.BREWING_STAND, "_bottle0"))
               )
               .with(
                  Condition.condition().term(BlockStateProperties.HAS_BOTTLE_1, true),
                  Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.BREWING_STAND, "_bottle1"))
               )
               .with(
                  Condition.condition().term(BlockStateProperties.HAS_BOTTLE_2, true),
                  Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.BREWING_STAND, "_bottle2"))
               )
               .with(
                  Condition.condition().term(BlockStateProperties.HAS_BOTTLE_0, false),
                  Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.BREWING_STAND, "_empty0"))
               )
               .with(
                  Condition.condition().term(BlockStateProperties.HAS_BOTTLE_1, false),
                  Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.BREWING_STAND, "_empty1"))
               )
               .with(
                  Condition.condition().term(BlockStateProperties.HAS_BOTTLE_2, false),
                  Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.BREWING_STAND, "_empty2"))
               )
         );
   }

   private void createMushroomBlock(Block var1) {
      ResourceLocation â˜ƒ = ModelTemplates.SINGLE_FACE.create(â˜ƒ, TextureMapping.defaultTexture(â˜ƒ), this.modelOutput);
      ResourceLocation â˜ƒx = ModelLocationUtils.decorateBlockModelLocation("mushroom_block_inside");
      this.blockStateOutput
         .accept(
            MultiPartGenerator.multiPart(â˜ƒ)
               .with(Condition.condition().term(BlockStateProperties.NORTH, true), Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               .with(
                  Condition.condition().term(BlockStateProperties.EAST, true),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .with(
                  Condition.condition().term(BlockStateProperties.SOUTH, true),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .with(
                  Condition.condition().term(BlockStateProperties.WEST, true),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .with(
                  Condition.condition().term(BlockStateProperties.UP, true),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .with(
                  Condition.condition().term(BlockStateProperties.DOWN, true),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .with(Condition.condition().term(BlockStateProperties.NORTH, false), Variant.variant().with(VariantProperties.MODEL, â˜ƒx))
               .with(
                  Condition.condition().term(BlockStateProperties.EAST, false),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒx)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.UV_LOCK, false)
               )
               .with(
                  Condition.condition().term(BlockStateProperties.SOUTH, false),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒx)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.UV_LOCK, false)
               )
               .with(
                  Condition.condition().term(BlockStateProperties.WEST, false),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒx)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     .with(VariantProperties.UV_LOCK, false)
               )
               .with(
                  Condition.condition().term(BlockStateProperties.UP, false),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒx)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                     .with(VariantProperties.UV_LOCK, false)
               )
               .with(
                  Condition.condition().term(BlockStateProperties.DOWN, false),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒx)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.UV_LOCK, false)
               )
         );
      this.delegateItemModel(â˜ƒ, TexturedModel.CUBE.createWithSuffix(â˜ƒ, "_inventory", this.modelOutput));
   }

   private void createCakeBlock() {
      this.createSimpleFlatItemModel(Items.CAKE);
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(Blocks.CAKE)
               .with(
                  PropertyDispatch.property(BlockStateProperties.BITES)
                     .select(0, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.CAKE)))
                     .select(1, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.CAKE, "_slice1")))
                     .select(2, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.CAKE, "_slice2")))
                     .select(3, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.CAKE, "_slice3")))
                     .select(4, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.CAKE, "_slice4")))
                     .select(5, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.CAKE, "_slice5")))
                     .select(6, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.CAKE, "_slice6")))
               )
         );
   }

   private void createCartographyTable() {
      TextureMapping â˜ƒ = new TextureMapping()
         .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(Blocks.CARTOGRAPHY_TABLE, "_side3"))
         .put(TextureSlot.DOWN, TextureMapping.getBlockTexture(Blocks.DARK_OAK_PLANKS))
         .put(TextureSlot.UP, TextureMapping.getBlockTexture(Blocks.CARTOGRAPHY_TABLE, "_top"))
         .put(TextureSlot.NORTH, TextureMapping.getBlockTexture(Blocks.CARTOGRAPHY_TABLE, "_side3"))
         .put(TextureSlot.EAST, TextureMapping.getBlockTexture(Blocks.CARTOGRAPHY_TABLE, "_side3"))
         .put(TextureSlot.SOUTH, TextureMapping.getBlockTexture(Blocks.CARTOGRAPHY_TABLE, "_side1"))
         .put(TextureSlot.WEST, TextureMapping.getBlockTexture(Blocks.CARTOGRAPHY_TABLE, "_side2"));
      this.blockStateOutput.accept(createSimpleBlock(Blocks.CARTOGRAPHY_TABLE, ModelTemplates.CUBE.create(Blocks.CARTOGRAPHY_TABLE, â˜ƒ, this.modelOutput)));
   }

   private void createSmithingTable() {
      TextureMapping â˜ƒ = new TextureMapping()
         .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(Blocks.SMITHING_TABLE, "_front"))
         .put(TextureSlot.DOWN, TextureMapping.getBlockTexture(Blocks.SMITHING_TABLE, "_bottom"))
         .put(TextureSlot.UP, TextureMapping.getBlockTexture(Blocks.SMITHING_TABLE, "_top"))
         .put(TextureSlot.NORTH, TextureMapping.getBlockTexture(Blocks.SMITHING_TABLE, "_front"))
         .put(TextureSlot.SOUTH, TextureMapping.getBlockTexture(Blocks.SMITHING_TABLE, "_front"))
         .put(TextureSlot.EAST, TextureMapping.getBlockTexture(Blocks.SMITHING_TABLE, "_side"))
         .put(TextureSlot.WEST, TextureMapping.getBlockTexture(Blocks.SMITHING_TABLE, "_side"));
      this.blockStateOutput.accept(createSimpleBlock(Blocks.SMITHING_TABLE, ModelTemplates.CUBE.create(Blocks.SMITHING_TABLE, â˜ƒ, this.modelOutput)));
   }

   private void createCraftingTableLike(Block var1, Block var2, BiFunction<Block, Block, TextureMapping> var3) {
      TextureMapping â˜ƒ = (TextureMapping)â˜ƒ.apply(â˜ƒ, â˜ƒ);
      this.blockStateOutput.accept(createSimpleBlock(â˜ƒ, ModelTemplates.CUBE.create(â˜ƒ, â˜ƒ, this.modelOutput)));
   }

   private void createPumpkins() {
      TextureMapping â˜ƒ = TextureMapping.column(Blocks.PUMPKIN);
      this.blockStateOutput.accept(createSimpleBlock(Blocks.PUMPKIN, ModelLocationUtils.getModelLocation(Blocks.PUMPKIN)));
      this.createPumpkinVariant(Blocks.CARVED_PUMPKIN, â˜ƒ);
      this.createPumpkinVariant(Blocks.JACK_O_LANTERN, â˜ƒ);
   }

   private void createPumpkinVariant(Block var1, TextureMapping var2) {
      ResourceLocation â˜ƒ = ModelTemplates.CUBE_ORIENTABLE
         .create(â˜ƒ, â˜ƒ.copyAndUpdate(TextureSlot.FRONT, TextureMapping.getBlockTexture(â˜ƒ)), this.modelOutput);
      this.blockStateOutput
         .accept(MultiVariantGenerator.multiVariant(â˜ƒ, Variant.variant().with(VariantProperties.MODEL, â˜ƒ)).with(createHorizontalFacingDispatch()));
   }

   private void createCauldrons() {
      this.createSimpleFlatItemModel(Items.CAULDRON);
      this.createNonTemplateModelBlock(Blocks.CAULDRON);
      this.blockStateOutput
         .accept(
            createSimpleBlock(
               Blocks.LAVA_CAULDRON,
               ModelTemplates.CAULDRON_FULL
                  .create(Blocks.LAVA_CAULDRON, TextureMapping.cauldron(TextureMapping.getBlockTexture(Blocks.LAVA, "_still")), this.modelOutput)
            )
         );
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(Blocks.WATER_CAULDRON)
               .with(
                  PropertyDispatch.property(LayeredCauldronBlock.LEVEL)
                     .select(
                        1,
                        Variant.variant()
                           .with(
                              VariantProperties.MODEL,
                              ModelTemplates.CAULDRON_LEVEL1
                                 .createWithSuffix(
                                    Blocks.WATER_CAULDRON,
                                    "_level1",
                                    TextureMapping.cauldron(TextureMapping.getBlockTexture(Blocks.WATER, "_still")),
                                    this.modelOutput
                                 )
                           )
                     )
                     .select(
                        2,
                        Variant.variant()
                           .with(
                              VariantProperties.MODEL,
                              ModelTemplates.CAULDRON_LEVEL2
                                 .createWithSuffix(
                                    Blocks.WATER_CAULDRON,
                                    "_level2",
                                    TextureMapping.cauldron(TextureMapping.getBlockTexture(Blocks.WATER, "_still")),
                                    this.modelOutput
                                 )
                           )
                     )
                     .select(
                        3,
                        Variant.variant()
                           .with(
                              VariantProperties.MODEL,
                              ModelTemplates.CAULDRON_FULL
                                 .createWithSuffix(
                                    Blocks.WATER_CAULDRON,
                                    "_full",
                                    TextureMapping.cauldron(TextureMapping.getBlockTexture(Blocks.WATER, "_still")),
                                    this.modelOutput
                                 )
                           )
                     )
               )
         );
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(Blocks.POWDER_SNOW_CAULDRON)
               .with(
                  PropertyDispatch.property(LayeredCauldronBlock.LEVEL)
                     .select(
                        1,
                        Variant.variant()
                           .with(
                              VariantProperties.MODEL,
                              ModelTemplates.CAULDRON_LEVEL1
                                 .createWithSuffix(
                                    Blocks.POWDER_SNOW_CAULDRON,
                                    "_level1",
                                    TextureMapping.cauldron(TextureMapping.getBlockTexture(Blocks.POWDER_SNOW)),
                                    this.modelOutput
                                 )
                           )
                     )
                     .select(
                        2,
                        Variant.variant()
                           .with(
                              VariantProperties.MODEL,
                              ModelTemplates.CAULDRON_LEVEL2
                                 .createWithSuffix(
                                    Blocks.POWDER_SNOW_CAULDRON,
                                    "_level2",
                                    TextureMapping.cauldron(TextureMapping.getBlockTexture(Blocks.POWDER_SNOW)),
                                    this.modelOutput
                                 )
                           )
                     )
                     .select(
                        3,
                        Variant.variant()
                           .with(
                              VariantProperties.MODEL,
                              ModelTemplates.CAULDRON_FULL
                                 .createWithSuffix(
                                    Blocks.POWDER_SNOW_CAULDRON,
                                    "_full",
                                    TextureMapping.cauldron(TextureMapping.getBlockTexture(Blocks.POWDER_SNOW)),
                                    this.modelOutput
                                 )
                           )
                     )
               )
         );
   }

   private void createChorusFlower() {
      TextureMapping â˜ƒ = TextureMapping.defaultTexture(Blocks.CHORUS_FLOWER);
      ResourceLocation â˜ƒx = ModelTemplates.CHORUS_FLOWER.create(Blocks.CHORUS_FLOWER, â˜ƒ, this.modelOutput);
      ResourceLocation â˜ƒxx = this.createSuffixedVariant(
         Blocks.CHORUS_FLOWER, "_dead", ModelTemplates.CHORUS_FLOWER, var1x -> â˜ƒ.copyAndUpdate(TextureSlot.TEXTURE, var1x)
      );
      this.blockStateOutput
         .accept(MultiVariantGenerator.multiVariant(Blocks.CHORUS_FLOWER).with(createEmptyOrFullDispatch(BlockStateProperties.AGE_5, 5, â˜ƒxx, â˜ƒx)));
   }

   private void createDispenserBlock(Block var1) {
      TextureMapping â˜ƒ = new TextureMapping()
         .put(TextureSlot.TOP, TextureMapping.getBlockTexture(Blocks.FURNACE, "_top"))
         .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.FURNACE, "_side"))
         .put(TextureSlot.FRONT, TextureMapping.getBlockTexture(â˜ƒ, "_front"));
      TextureMapping â˜ƒx = new TextureMapping()
         .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.FURNACE, "_top"))
         .put(TextureSlot.FRONT, TextureMapping.getBlockTexture(â˜ƒ, "_front_vertical"));
      ResourceLocation â˜ƒxx = ModelTemplates.CUBE_ORIENTABLE.create(â˜ƒ, â˜ƒ, this.modelOutput);
      ResourceLocation â˜ƒxxx = ModelTemplates.CUBE_ORIENTABLE_VERTICAL.create(â˜ƒ, â˜ƒx, this.modelOutput);
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(â˜ƒ)
               .with(
                  PropertyDispatch.property(BlockStateProperties.FACING)
                     .select(
                        Direction.DOWN, Variant.variant().with(VariantProperties.MODEL, â˜ƒxxx).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                     )
                     .select(Direction.UP, Variant.variant().with(VariantProperties.MODEL, â˜ƒxxx))
                     .select(Direction.NORTH, Variant.variant().with(VariantProperties.MODEL, â˜ƒxx))
                     .select(
                        Direction.EAST, Variant.variant().with(VariantProperties.MODEL, â˜ƒxx).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     )
                     .select(
                        Direction.SOUTH, Variant.variant().with(VariantProperties.MODEL, â˜ƒxx).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     )
                     .select(
                        Direction.WEST, Variant.variant().with(VariantProperties.MODEL, â˜ƒxx).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     )
               )
         );
   }

   private void createEndPortalFrame() {
      ResourceLocation â˜ƒ = ModelLocationUtils.getModelLocation(Blocks.END_PORTAL_FRAME);
      ResourceLocation â˜ƒx = ModelLocationUtils.getModelLocation(Blocks.END_PORTAL_FRAME, "_filled");
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(Blocks.END_PORTAL_FRAME)
               .with(
                  PropertyDispatch.property(BlockStateProperties.EYE)
                     .select(false, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
                     .select(true, Variant.variant().with(VariantProperties.MODEL, â˜ƒx))
               )
               .with(createHorizontalFacingDispatchAlt())
         );
   }

   private void createChorusPlant() {
      ResourceLocation â˜ƒ = ModelLocationUtils.getModelLocation(Blocks.CHORUS_PLANT, "_side");
      ResourceLocation â˜ƒx = ModelLocationUtils.getModelLocation(Blocks.CHORUS_PLANT, "_noside");
      ResourceLocation â˜ƒxx = ModelLocationUtils.getModelLocation(Blocks.CHORUS_PLANT, "_noside1");
      ResourceLocation â˜ƒxxx = ModelLocationUtils.getModelLocation(Blocks.CHORUS_PLANT, "_noside2");
      ResourceLocation â˜ƒxxxx = ModelLocationUtils.getModelLocation(Blocks.CHORUS_PLANT, "_noside3");
      this.blockStateOutput
         .accept(
            MultiPartGenerator.multiPart(Blocks.CHORUS_PLANT)
               .with(Condition.condition().term(BlockStateProperties.NORTH, true), Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               .with(
                  Condition.condition().term(BlockStateProperties.EAST, true),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .with(
                  Condition.condition().term(BlockStateProperties.SOUTH, true),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .with(
                  Condition.condition().term(BlockStateProperties.WEST, true),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .with(
                  Condition.condition().term(BlockStateProperties.UP, true),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .with(
                  Condition.condition().term(BlockStateProperties.DOWN, true),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒ)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .with(
                  Condition.condition().term(BlockStateProperties.NORTH, false),
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒx).with(VariantProperties.WEIGHT, 2),
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒxx),
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒxxx),
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒxxxx)
               )
               .with(
                  Condition.condition().term(BlockStateProperties.EAST, false),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒxx)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.UV_LOCK, true),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒxxx)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.UV_LOCK, true),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒxxxx)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.UV_LOCK, true),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒx)
                     .with(VariantProperties.WEIGHT, 2)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .with(
                  Condition.condition().term(BlockStateProperties.SOUTH, false),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒxxx)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.UV_LOCK, true),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒxxxx)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.UV_LOCK, true),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒx)
                     .with(VariantProperties.WEIGHT, 2)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.UV_LOCK, true),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒxx)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .with(
                  Condition.condition().term(BlockStateProperties.WEST, false),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒxxxx)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     .with(VariantProperties.UV_LOCK, true),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒx)
                     .with(VariantProperties.WEIGHT, 2)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     .with(VariantProperties.UV_LOCK, true),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒxx)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     .with(VariantProperties.UV_LOCK, true),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒxxx)
                     .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .with(
                  Condition.condition().term(BlockStateProperties.UP, false),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒx)
                     .with(VariantProperties.WEIGHT, 2)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                     .with(VariantProperties.UV_LOCK, true),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒxxxx)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                     .with(VariantProperties.UV_LOCK, true),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒxx)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                     .with(VariantProperties.UV_LOCK, true),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒxxx)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                     .with(VariantProperties.UV_LOCK, true)
               )
               .with(
                  Condition.condition().term(BlockStateProperties.DOWN, false),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒxxxx)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.UV_LOCK, true),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒxxx)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.UV_LOCK, true),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒxx)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.UV_LOCK, true),
                  Variant.variant()
                     .with(VariantProperties.MODEL, â˜ƒx)
                     .with(VariantProperties.WEIGHT, 2)
                     .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                     .with(VariantProperties.UV_LOCK, true)
               )
         );
   }

   private void createComposter() {
      this.blockStateOutput
         .accept(
            MultiPartGenerator.multiPart(Blocks.COMPOSTER)
               .with(Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.COMPOSTER)))
               .with(
                  Condition.condition().term(BlockStateProperties.LEVEL_COMPOSTER, 1),
                  Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.COMPOSTER, "_contents1"))
               )
               .with(
                  Condition.condition().term(BlockStateProperties.LEVEL_COMPOSTER, 2),
                  Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.COMPOSTER, "_contents2"))
               )
               .with(
                  Condition.condition().term(BlockStateProperties.LEVEL_COMPOSTER, 3),
                  Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.COMPOSTER, "_contents3"))
               )
               .with(
                  Condition.condition().term(BlockStateProperties.LEVEL_COMPOSTER, 4),
                  Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.COMPOSTER, "_contents4"))
               )
               .with(
                  Condition.condition().term(BlockStateProperties.LEVEL_COMPOSTER, 5),
                  Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.COMPOSTER, "_contents5"))
               )
               .with(
                  Condition.condition().term(BlockStateProperties.LEVEL_COMPOSTER, 6),
                  Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.COMPOSTER, "_contents6"))
               )
               .with(
                  Condition.condition().term(BlockStateProperties.LEVEL_COMPOSTER, 7),
                  Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.COMPOSTER, "_contents7"))
               )
               .with(
                  Condition.condition().term(BlockStateProperties.LEVEL_COMPOSTER, 8),
                  Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.COMPOSTER, "_contents_ready"))
               )
         );
   }

   private void createAmethystCluster(Block var1) {
      this.skipAutoItemBlock(â˜ƒ);
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(
                  â˜ƒ, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.CROSS.create(â˜ƒ, TextureMapping.cross(â˜ƒ), this.modelOutput))
               )
               .with(this.createColumnWithFacing())
         );
   }

   private void createAmethystClusters() {
      this.createAmethystCluster(Blocks.SMALL_AMETHYST_BUD);
      this.createAmethystCluster(Blocks.MEDIUM_AMETHYST_BUD);
      this.createAmethystCluster(Blocks.LARGE_AMETHYST_BUD);
      this.createAmethystCluster(Blocks.AMETHYST_CLUSTER);
   }

   private void createPointedDripstone() {
      this.createSimpleFlatItemModel(Blocks.POINTED_DRIPSTONE.asItem());
      PropertyDispatch.C2<Direction, DripstoneThickness> â˜ƒ = PropertyDispatch.properties(
         BlockStateProperties.VERTICAL_DIRECTION, BlockStateProperties.DRIPSTONE_THICKNESS
      );

      for(DripstoneThickness â˜ƒx : DripstoneThickness.values()) {
         â˜ƒ.select(Direction.UP, â˜ƒx, this.createPointedDripstoneVariant(Direction.UP, â˜ƒx));
      }

      for(DripstoneThickness â˜ƒx : DripstoneThickness.values()) {
         â˜ƒ.select(Direction.DOWN, â˜ƒx, this.createPointedDripstoneVariant(Direction.DOWN, â˜ƒx));
      }

      this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(Blocks.POINTED_DRIPSTONE).with(â˜ƒ));
   }

   private Variant createPointedDripstoneVariant(Direction var1, DripstoneThickness var2) {
      String â˜ƒ = "_" + â˜ƒ.getSerializedName() + "_" + â˜ƒ.getSerializedName();
      TextureMapping â˜ƒx = TextureMapping.cross(TextureMapping.getBlockTexture(Blocks.POINTED_DRIPSTONE, â˜ƒ));
      return Variant.variant()
         .with(VariantProperties.MODEL, ModelTemplates.POINTED_DRIPSTONE.createWithSuffix(Blocks.POINTED_DRIPSTONE, â˜ƒ, â˜ƒx, this.modelOutput));
   }

   private void createNyliumBlock(Block var1) {
      TextureMapping â˜ƒ = new TextureMapping()
         .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(Blocks.NETHERRACK))
         .put(TextureSlot.TOP, TextureMapping.getBlockTexture(â˜ƒ))
         .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(â˜ƒ, "_side"));
      this.blockStateOutput.accept(createSimpleBlock(â˜ƒ, ModelTemplates.CUBE_BOTTOM_TOP.create(â˜ƒ, â˜ƒ, this.modelOutput)));
   }

   private void createDaylightDetector() {
      ResourceLocation â˜ƒ = TextureMapping.getBlockTexture(Blocks.DAYLIGHT_DETECTOR, "_side");
      TextureMapping â˜ƒx = new TextureMapping()
         .put(TextureSlot.TOP, TextureMapping.getBlockTexture(Blocks.DAYLIGHT_DETECTOR, "_top"))
         .put(TextureSlot.SIDE, â˜ƒ);
      TextureMapping â˜ƒxx = new TextureMapping()
         .put(TextureSlot.TOP, TextureMapping.getBlockTexture(Blocks.DAYLIGHT_DETECTOR, "_inverted_top"))
         .put(TextureSlot.SIDE, â˜ƒ);
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(Blocks.DAYLIGHT_DETECTOR)
               .with(
                  PropertyDispatch.property(BlockStateProperties.INVERTED)
                     .select(
                        false,
                        Variant.variant()
                           .with(VariantProperties.MODEL, ModelTemplates.DAYLIGHT_DETECTOR.create(Blocks.DAYLIGHT_DETECTOR, â˜ƒx, this.modelOutput))
                     )
                     .select(
                        true,
                        Variant.variant()
                           .with(
                              VariantProperties.MODEL,
                              ModelTemplates.DAYLIGHT_DETECTOR
                                 .create(ModelLocationUtils.getModelLocation(Blocks.DAYLIGHT_DETECTOR, "_inverted"), â˜ƒxx, this.modelOutput)
                           )
                     )
               )
         );
   }

   private void createRotatableColumn(Block var1) {
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(â˜ƒ, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(â˜ƒ)))
               .with(this.createColumnWithFacing())
         );
   }

   private void createLightningRod() {
      Block â˜ƒ = Blocks.LIGHTNING_ROD;
      ResourceLocation â˜ƒx = ModelLocationUtils.getModelLocation(â˜ƒ, "_on");
      ResourceLocation â˜ƒxx = ModelLocationUtils.getModelLocation(â˜ƒ);
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(â˜ƒ, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(â˜ƒ)))
               .with(this.createColumnWithFacing())
               .with(createBooleanModelDispatch(BlockStateProperties.POWERED, â˜ƒx, â˜ƒxx))
         );
   }

   private void createFarmland() {
      TextureMapping â˜ƒ = new TextureMapping()
         .put(TextureSlot.DIRT, TextureMapping.getBlockTexture(Blocks.DIRT))
         .put(TextureSlot.TOP, TextureMapping.getBlockTexture(Blocks.FARMLAND));
      TextureMapping â˜ƒx = new TextureMapping()
         .put(TextureSlot.DIRT, TextureMapping.getBlockTexture(Blocks.DIRT))
         .put(TextureSlot.TOP, TextureMapping.getBlockTexture(Blocks.FARMLAND, "_moist"));
      ResourceLocation â˜ƒxx = ModelTemplates.FARMLAND.create(Blocks.FARMLAND, â˜ƒ, this.modelOutput);
      ResourceLocation â˜ƒxxx = ModelTemplates.FARMLAND.create(TextureMapping.getBlockTexture(Blocks.FARMLAND, "_moist"), â˜ƒx, this.modelOutput);
      this.blockStateOutput
         .accept(MultiVariantGenerator.multiVariant(Blocks.FARMLAND).with(createEmptyOrFullDispatch(BlockStateProperties.MOISTURE, 7, â˜ƒxxx, â˜ƒxx)));
   }

   private List<ResourceLocation> createFloorFireModels(Block var1) {
      ResourceLocation â˜ƒ = ModelTemplates.FIRE_FLOOR.create(ModelLocationUtils.getModelLocation(â˜ƒ, "_floor0"), TextureMapping.fire0(â˜ƒ), this.modelOutput);
      ResourceLocation â˜ƒx = ModelTemplates.FIRE_FLOOR
         .create(ModelLocationUtils.getModelLocation(â˜ƒ, "_floor1"), TextureMapping.fire1(â˜ƒ), this.modelOutput);
      return ImmutableList.of(â˜ƒ, â˜ƒx);
   }

   private List<ResourceLocation> createSideFireModels(Block var1) {
      ResourceLocation â˜ƒ = ModelTemplates.FIRE_SIDE.create(ModelLocationUtils.getModelLocation(â˜ƒ, "_side0"), TextureMapping.fire0(â˜ƒ), this.modelOutput);
      ResourceLocation â˜ƒx = ModelTemplates.FIRE_SIDE.create(ModelLocationUtils.getModelLocation(â˜ƒ, "_side1"), TextureMapping.fire1(â˜ƒ), this.modelOutput);
      ResourceLocation â˜ƒxx = ModelTemplates.FIRE_SIDE_ALT
         .create(ModelLocationUtils.getModelLocation(â˜ƒ, "_side_alt0"), TextureMapping.fire0(â˜ƒ), this.modelOutput);
      ResourceLocation â˜ƒxxx = ModelTemplates.FIRE_SIDE_ALT
         .create(ModelLocationUtils.getModelLocation(â˜ƒ, "_side_alt1"), TextureMapping.fire1(â˜ƒ), this.modelOutput);
      return ImmutableList.of(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx);
   }

   private List<ResourceLocation> createTopFireModels(Block var1) {
      ResourceLocation â˜ƒ = ModelTemplates.FIRE_UP.create(ModelLocationUtils.getModelLocation(â˜ƒ, "_up0"), TextureMapping.fire0(â˜ƒ), this.modelOutput);
      ResourceLocation â˜ƒx = ModelTemplates.FIRE_UP.create(ModelLocationUtils.getModelLocation(â˜ƒ, "_up1"), TextureMapping.fire1(â˜ƒ), this.modelOutput);
      ResourceLocation â˜ƒxx = ModelTemplates.FIRE_UP_ALT
         .create(ModelLocationUtils.getModelLocation(â˜ƒ, "_up_alt0"), TextureMapping.fire0(â˜ƒ), this.modelOutput);
      ResourceLocation â˜ƒxxx = ModelTemplates.FIRE_UP_ALT
         .create(ModelLocationUtils.getModelLocation(â˜ƒ, "_up_alt1"), TextureMapping.fire1(â˜ƒ), this.modelOutput);
      return ImmutableList.of(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx);
   }

   private static List<Variant> wrapModels(List<ResourceLocation> var0, UnaryOperator<Variant> var1) {
      return (List<Variant>)â˜ƒ.stream().map(var0x -> Variant.variant().with(VariantProperties.MODEL, var0x)).map(â˜ƒ).collect(Collectors.toList());
   }

   private void createFire() {
      Condition â˜ƒ = Condition.condition()
         .term(BlockStateProperties.NORTH, false)
         .term(BlockStateProperties.EAST, false)
         .term(BlockStateProperties.SOUTH, false)
         .term(BlockStateProperties.WEST, false)
         .term(BlockStateProperties.UP, false);
      List<ResourceLocation> â˜ƒx = this.createFloorFireModels(Blocks.FIRE);
      List<ResourceLocation> â˜ƒxx = this.createSideFireModels(Blocks.FIRE);
      List<ResourceLocation> â˜ƒxxx = this.createTopFireModels(Blocks.FIRE);
      this.blockStateOutput
         .accept(
            MultiPartGenerator.multiPart(Blocks.FIRE)
               .with(â˜ƒ, wrapModels(â˜ƒx, var0 -> var0))
               .with(Condition.or(Condition.condition().term(BlockStateProperties.NORTH, true), â˜ƒ), wrapModels(â˜ƒxx, var0 -> var0))
               .with(
                  Condition.or(Condition.condition().term(BlockStateProperties.EAST, true), â˜ƒ),
                  wrapModels(â˜ƒxx, var0 -> var0.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
               )
               .with(
                  Condition.or(Condition.condition().term(BlockStateProperties.SOUTH, true), â˜ƒ),
                  wrapModels(â˜ƒxx, var0 -> var0.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
               )
               .with(
                  Condition.or(Condition.condition().term(BlockStateProperties.WEST, true), â˜ƒ),
                  wrapModels(â˜ƒxx, var0 -> var0.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
               )
               .with(Condition.condition().term(BlockStateProperties.UP, true), wrapModels(â˜ƒxxx, var0 -> var0))
         );
   }

   private void createSoulFire() {
      List<ResourceLocation> â˜ƒ = this.createFloorFireModels(Blocks.SOUL_FIRE);
      List<ResourceLocation> â˜ƒx = this.createSideFireModels(Blocks.SOUL_FIRE);
      this.blockStateOutput
         .accept(
            MultiPartGenerator.multiPart(Blocks.SOUL_FIRE)
               .with(wrapModels(â˜ƒ, var0 -> var0))
               .with(wrapModels(â˜ƒx, var0 -> var0))
               .with(wrapModels(â˜ƒx, var0 -> var0.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)))
               .with(wrapModels(â˜ƒx, var0 -> var0.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)))
               .with(wrapModels(â˜ƒx, var0 -> var0.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)))
         );
   }

   private void createLantern(Block var1) {
      ResourceLocation â˜ƒ = TexturedModel.LANTERN.create(â˜ƒ, this.modelOutput);
      ResourceLocation â˜ƒx = TexturedModel.HANGING_LANTERN.create(â˜ƒ, this.modelOutput);
      this.createSimpleFlatItemModel(â˜ƒ.asItem());
      this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(â˜ƒ).with(createBooleanModelDispatch(BlockStateProperties.HANGING, â˜ƒx, â˜ƒ)));
   }

   private void createFrostedIce() {
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(Blocks.FROSTED_ICE)
               .with(
                  PropertyDispatch.property(BlockStateProperties.AGE_3)
                     .select(
                        0,
                        Variant.variant()
                           .with(VariantProperties.MODEL, this.createSuffixedVariant(Blocks.FROSTED_ICE, "_0", ModelTemplates.CUBE_ALL, TextureMapping::cube))
                     )
                     .select(
                        1,
                        Variant.variant()
                           .with(VariantProperties.MODEL, this.createSuffixedVariant(Blocks.FROSTED_ICE, "_1", ModelTemplates.CUBE_ALL, TextureMapping::cube))
                     )
                     .select(
                        2,
                        Variant.variant()
                           .with(VariantProperties.MODEL, this.createSuffixedVariant(Blocks.FROSTED_ICE, "_2", ModelTemplates.CUBE_ALL, TextureMapping::cube))
                     )
                     .select(
                        3,
                        Variant.variant()
                           .with(VariantProperties.MODEL, this.createSuffixedVariant(Blocks.FROSTED_ICE, "_3", ModelTemplates.CUBE_ALL, TextureMapping::cube))
                     )
               )
         );
   }

   private void createGrassBlocks() {
      ResourceLocation â˜ƒ = TextureMapping.getBlockTexture(Blocks.DIRT);
      TextureMapping â˜ƒx = new TextureMapping()
         .put(TextureSlot.BOTTOM, â˜ƒ)
         .copyForced(TextureSlot.BOTTOM, TextureSlot.PARTICLE)
         .put(TextureSlot.TOP, TextureMapping.getBlockTexture(Blocks.GRASS_BLOCK, "_top"))
         .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.GRASS_BLOCK, "_snow"));
      Variant â˜ƒxx = Variant.variant()
         .with(VariantProperties.MODEL, ModelTemplates.CUBE_BOTTOM_TOP.createWithSuffix(Blocks.GRASS_BLOCK, "_snow", â˜ƒx, this.modelOutput));
      this.createGrassLikeBlock(Blocks.GRASS_BLOCK, ModelLocationUtils.getModelLocation(Blocks.GRASS_BLOCK), â˜ƒxx);
      ResourceLocation â˜ƒxxx = TexturedModel.CUBE_TOP_BOTTOM
         .get(Blocks.MYCELIUM)
         .updateTextures(var1x -> var1x.put(TextureSlot.BOTTOM, â˜ƒ))
         .create(Blocks.MYCELIUM, this.modelOutput);
      this.createGrassLikeBlock(Blocks.MYCELIUM, â˜ƒxxx, â˜ƒxx);
      ResourceLocation â˜ƒxxxx = TexturedModel.CUBE_TOP_BOTTOM
         .get(Blocks.PODZOL)
         .updateTextures(var1x -> var1x.put(TextureSlot.BOTTOM, â˜ƒ))
         .create(Blocks.PODZOL, this.modelOutput);
      this.createGrassLikeBlock(Blocks.PODZOL, â˜ƒxxxx, â˜ƒxx);
   }

   private void createGrassLikeBlock(Block var1, ResourceLocation var2, Variant var3) {
      List<Variant> â˜ƒ = Arrays.asList(createRotatedVariants(â˜ƒ));
      this.blockStateOutput
         .accept(MultiVariantGenerator.multiVariant(â˜ƒ).with(PropertyDispatch.property(BlockStateProperties.SNOWY).select(true, â˜ƒ).select(false, â˜ƒ)));
   }

   private void createCocoa() {
      this.createSimpleFlatItemModel(Items.COCOA_BEANS);
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(Blocks.COCOA)
               .with(
                  PropertyDispatch.property(BlockStateProperties.AGE_2)
                     .select(0, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.COCOA, "_stage0")))
                     .select(1, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.COCOA, "_stage1")))
                     .select(2, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.COCOA, "_stage2")))
               )
               .with(createHorizontalFacingDispatchAlt())
         );
   }

   private void createDirtPath() {
      this.blockStateOutput.accept(createRotatedVariant(Blocks.DIRT_PATH, ModelLocationUtils.getModelLocation(Blocks.DIRT_PATH)));
   }

   private void createWeightedPressurePlate(Block var1, Block var2) {
      TextureMapping â˜ƒ = TextureMapping.defaultTexture(â˜ƒ);
      ResourceLocation â˜ƒx = ModelTemplates.PRESSURE_PLATE_UP.create(â˜ƒ, â˜ƒ, this.modelOutput);
      ResourceLocation â˜ƒxx = ModelTemplates.PRESSURE_PLATE_DOWN.create(â˜ƒ, â˜ƒ, this.modelOutput);
      this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(â˜ƒ).with(createEmptyOrFullDispatch(BlockStateProperties.POWER, 1, â˜ƒxx, â˜ƒx)));
   }

   private void createHopper() {
      ResourceLocation â˜ƒ = ModelLocationUtils.getModelLocation(Blocks.HOPPER);
      ResourceLocation â˜ƒx = ModelLocationUtils.getModelLocation(Blocks.HOPPER, "_side");
      this.createSimpleFlatItemModel(Items.HOPPER);
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(Blocks.HOPPER)
               .with(
                  PropertyDispatch.property(BlockStateProperties.FACING_HOPPER)
                     .select(Direction.DOWN, Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
                     .select(Direction.NORTH, Variant.variant().with(VariantProperties.MODEL, â˜ƒx))
                     .select(
                        Direction.EAST, Variant.variant().with(VariantProperties.MODEL, â˜ƒx).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     )
                     .select(
                        Direction.SOUTH, Variant.variant().with(VariantProperties.MODEL, â˜ƒx).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     )
                     .select(
                        Direction.WEST, Variant.variant().with(VariantProperties.MODEL, â˜ƒx).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     )
               )
         );
   }

   private void copyModel(Block var1, Block var2) {
      ResourceLocation â˜ƒ = ModelLocationUtils.getModelLocation(â˜ƒ);
      this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(â˜ƒ, Variant.variant().with(VariantProperties.MODEL, â˜ƒ)));
      this.delegateItemModel(â˜ƒ, â˜ƒ);
   }

   private void createIronBars() {
      ResourceLocation â˜ƒ = ModelLocationUtils.getModelLocation(Blocks.IRON_BARS, "_post_ends");
      ResourceLocation â˜ƒx = ModelLocationUtils.getModelLocation(Blocks.IRON_BARS, "_post");
      ResourceLocation â˜ƒxx = ModelLocationUtils.getModelLocation(Blocks.IRON_BARS, "_cap");
      ResourceLocation â˜ƒxxx = ModelLocationUtils.getModelLocation(Blocks.IRON_BARS, "_cap_alt");
      ResourceLocation â˜ƒxxxx = ModelLocationUtils.getModelLocation(Blocks.IRON_BARS, "_side");
      ResourceLocation â˜ƒxxxxx = ModelLocationUtils.getModelLocation(Blocks.IRON_BARS, "_side_alt");
      this.blockStateOutput
         .accept(
            MultiPartGenerator.multiPart(Blocks.IRON_BARS)
               .with(Variant.variant().with(VariantProperties.MODEL, â˜ƒ))
               .with(
                  Condition.condition()
                     .term(BlockStateProperties.NORTH, false)
                     .term(BlockStateProperties.EAST, false)
                     .term(BlockStateProperties.SOUTH, false)
                     .term(BlockStateProperties.WEST, false),
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒx)
               )
               .with(
                  Condition.condition()
                     .term(BlockStateProperties.NORTH, true)
                     .term(BlockStateProperties.EAST, false)
                     .term(BlockStateProperties.SOUTH, false)
                     .term(BlockStateProperties.WEST, false),
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒxx)
               )
               .with(
                  Condition.condition()
                     .term(BlockStateProperties.NORTH, false)
                     .term(BlockStateProperties.EAST, true)
                     .term(BlockStateProperties.SOUTH, false)
                     .term(BlockStateProperties.WEST, false),
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒxx).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
               )
               .with(
                  Condition.condition()
                     .term(BlockStateProperties.NORTH, false)
                     .term(BlockStateProperties.EAST, false)
                     .term(BlockStateProperties.SOUTH, true)
                     .term(BlockStateProperties.WEST, false),
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒxxx)
               )
               .with(
                  Condition.condition()
                     .term(BlockStateProperties.NORTH, false)
                     .term(BlockStateProperties.EAST, false)
                     .term(BlockStateProperties.SOUTH, false)
                     .term(BlockStateProperties.WEST, true),
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒxxx).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
               )
               .with(Condition.condition().term(BlockStateProperties.NORTH, true), Variant.variant().with(VariantProperties.MODEL, â˜ƒxxxx))
               .with(
                  Condition.condition().term(BlockStateProperties.EAST, true),
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒxxxx).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
               )
               .with(Condition.condition().term(BlockStateProperties.SOUTH, true), Variant.variant().with(VariantProperties.MODEL, â˜ƒxxxxx))
               .with(
                  Condition.condition().term(BlockStateProperties.WEST, true),
                  Variant.variant().with(VariantProperties.MODEL, â˜ƒxxxxx).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
               )
         );
      this.createSimpleFlatItemModel(Blocks.IRON_BARS);
   }

   private void createNonTemplateHorizontalBlock(Block var1) {
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(â˜ƒ, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(â˜ƒ)))
               .with(createHorizontalFacingDispatch())
         );
   }

   private void createLever() {
      ResourceLocation â˜ƒ = ModelLocationUtils.getModelLocation(Blocks.LEVER);
      ResourceLocation â˜ƒx = ModelLocationUtils.getModelLocation(Blocks.LEVER, "_on");
      this.createSimpleFlatItemModel(Blocks.LEVER);
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(Blocks.LEVER)
               .with(createBooleanModelDispatch(BlockStateProperties.POWERED, â˜ƒ, â˜ƒx))
               .with(
                  PropertyDispatch.properties(BlockStateProperties.ATTACH_FACE, BlockStateProperties.HORIZONTAL_FACING)
                     .select(
                        AttachFace.CEILING,
                        Direction.NORTH,
                        Variant.variant()
                           .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     )
                     .select(
                        AttachFace.CEILING,
                        Direction.EAST,
                        Variant.variant()
                           .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     )
                     .select(AttachFace.CEILING, Direction.SOUTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
                     .select(
                        AttachFace.CEILING,
                        Direction.WEST,
                        Variant.variant()
                           .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     )
                     .select(AttachFace.FLOOR, Direction.NORTH, Variant.variant())
                     .select(AttachFace.FLOOR, Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                     .select(AttachFace.FLOOR, Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                     .select(AttachFace.FLOOR, Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                     .select(AttachFace.WALL, Direction.NORTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
                     .select(
                        AttachFace.WALL,
                        Direction.EAST,
                        Variant.variant()
                           .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     )
                     .select(
                        AttachFace.WALL,
                        Direction.SOUTH,
                        Variant.variant()
                           .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     )
                     .select(
                        AttachFace.WALL,
                        Direction.WEST,
                        Variant.variant()
                           .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     )
               )
         );
   }

   private void createLilyPad() {
      this.createSimpleFlatItemModel(Blocks.LILY_PAD);
      this.blockStateOutput.accept(createRotatedVariant(Blocks.LILY_PAD, ModelLocationUtils.getModelLocation(Blocks.LILY_PAD)));
   }

   private void createNetherPortalBlock() {
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(Blocks.NETHER_PORTAL)
               .with(
                  PropertyDispatch.property(BlockStateProperties.HORIZONTAL_AXIS)
                     .select(
                        Direction.Axis.X, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.NETHER_PORTAL, "_ns"))
                     )
                     .select(
                        Direction.Axis.Z, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.NETHER_PORTAL, "_ew"))
                     )
               )
         );
   }

   private void createNetherrack() {
      ResourceLocation â˜ƒ = TexturedModel.CUBE.create(Blocks.NETHERRACK, this.modelOutput);
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(
               Blocks.NETHERRACK,
               Variant.variant().with(VariantProperties.MODEL, â˜ƒ),
               Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90),
               Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180),
               Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.X_ROT, VariantProperties.Rotation.R270),
               Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90),
               Variant.variant()
                  .with(VariantProperties.MODEL, â˜ƒ)
                  .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                  .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90),
               Variant.variant()
                  .with(VariantProperties.MODEL, â˜ƒ)
                  .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                  .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180),
               Variant.variant()
                  .with(VariantProperties.MODEL, â˜ƒ)
                  .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                  .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270),
               Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180),
               Variant.variant()
                  .with(VariantProperties.MODEL, â˜ƒ)
                  .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                  .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90),
               Variant.variant()
                  .with(VariantProperties.MODEL, â˜ƒ)
                  .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                  .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180),
               Variant.variant()
                  .with(VariantProperties.MODEL, â˜ƒ)
                  .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                  .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270),
               Variant.variant().with(VariantProperties.MODEL, â˜ƒ).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270),
               Variant.variant()
                  .with(VariantProperties.MODEL, â˜ƒ)
                  .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                  .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90),
               Variant.variant()
                  .with(VariantProperties.MODEL, â˜ƒ)
                  .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                  .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180),
               Variant.variant()
                  .with(VariantProperties.MODEL, â˜ƒ)
                  .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                  .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
            )
         );
   }

   private void createObserver() {
      ResourceLocation â˜ƒ = ModelLocationUtils.getModelLocation(Blocks.OBSERVER);
      ResourceLocation â˜ƒx = ModelLocationUtils.getModelLocation(Blocks.OBSERVER, "_on");
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(Blocks.OBSERVER)
               .with(createBooleanModelDispatch(BlockStateProperties.POWERED, â˜ƒx, â˜ƒ))
               .with(createFacingDispatch())
         );
   }

   private void createPistons() {
      TextureMapping â˜ƒ = new TextureMapping()
         .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(Blocks.PISTON, "_bottom"))
         .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.PISTON, "_side"));
      ResourceLocation â˜ƒx = TextureMapping.getBlockTexture(Blocks.PISTON, "_top_sticky");
      ResourceLocation â˜ƒxx = TextureMapping.getBlockTexture(Blocks.PISTON, "_top");
      TextureMapping â˜ƒxxx = â˜ƒ.copyAndUpdate(TextureSlot.PLATFORM, â˜ƒx);
      TextureMapping â˜ƒxxxx = â˜ƒ.copyAndUpdate(TextureSlot.PLATFORM, â˜ƒxx);
      ResourceLocation â˜ƒxxxxx = ModelLocationUtils.getModelLocation(Blocks.PISTON, "_base");
      this.createPistonVariant(Blocks.PISTON, â˜ƒxxxxx, â˜ƒxxxx);
      this.createPistonVariant(Blocks.STICKY_PISTON, â˜ƒxxxxx, â˜ƒxxx);
      ResourceLocation â˜ƒxxxxxx = ModelTemplates.CUBE_BOTTOM_TOP
         .createWithSuffix(Blocks.PISTON, "_inventory", â˜ƒ.copyAndUpdate(TextureSlot.TOP, â˜ƒxx), this.modelOutput);
      ResourceLocation â˜ƒxxxxxxx = ModelTemplates.CUBE_BOTTOM_TOP
         .createWithSuffix(Blocks.STICKY_PISTON, "_inventory", â˜ƒ.copyAndUpdate(TextureSlot.TOP, â˜ƒx), this.modelOutput);
      this.delegateItemModel(Blocks.PISTON, â˜ƒxxxxxx);
      this.delegateItemModel(Blocks.STICKY_PISTON, â˜ƒxxxxxxx);
   }

   private void createPistonVariant(Block var1, ResourceLocation var2, TextureMapping var3) {
      ResourceLocation â˜ƒ = ModelTemplates.PISTON.create(â˜ƒ, â˜ƒ, this.modelOutput);
      this.blockStateOutput
         .accept(MultiVariantGenerator.multiVariant(â˜ƒ).with(createBooleanModelDispatch(BlockStateProperties.EXTENDED, â˜ƒ, â˜ƒ)).with(createFacingDispatch()));
   }

   private void createPistonHeads() {
      TextureMapping â˜ƒ = new TextureMapping()
         .put(TextureSlot.UNSTICKY, TextureMapping.getBlockTexture(Blocks.PISTON, "_top"))
         .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.PISTON, "_side"));
      TextureMapping â˜ƒx = â˜ƒ.copyAndUpdate(TextureSlot.PLATFORM, TextureMapping.getBlockTexture(Blocks.PISTON, "_top_sticky"));
      TextureMapping â˜ƒxx = â˜ƒ.copyAndUpdate(TextureSlot.PLATFORM, TextureMapping.getBlockTexture(Blocks.PISTON, "_top"));
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(Blocks.PISTON_HEAD)
               .with(
                  PropertyDispatch.properties(BlockStateProperties.SHORT, BlockStateProperties.PISTON_TYPE)
                     .select(
                        false,
                        PistonType.DEFAULT,
                        Variant.variant()
                           .with(VariantProperties.MODEL, ModelTemplates.PISTON_HEAD.createWithSuffix(Blocks.PISTON, "_head", â˜ƒxx, this.modelOutput))
                     )
                     .select(
                        false,
                        PistonType.STICKY,
                        Variant.variant()
                           .with(VariantProperties.MODEL, ModelTemplates.PISTON_HEAD.createWithSuffix(Blocks.PISTON, "_head_sticky", â˜ƒx, this.modelOutput))
                     )
                     .select(
                        true,
                        PistonType.DEFAULT,
                        Variant.variant()
                           .with(
                              VariantProperties.MODEL, ModelTemplates.PISTON_HEAD_SHORT.createWithSuffix(Blocks.PISTON, "_head_short", â˜ƒxx, this.modelOutput)
                           )
                     )
                     .select(
                        true,
                        PistonType.STICKY,
                        Variant.variant()
                           .with(
                              VariantProperties.MODEL,
                              ModelTemplates.PISTON_HEAD_SHORT.createWithSuffix(Blocks.PISTON, "_head_short_sticky", â˜ƒx, this.modelOutput)
                           )
                     )
               )
               .with(createFacingDispatch())
         );
   }

   private void createSculkSensor() {
      ResourceLocation â˜ƒ = ModelLocationUtils.getModelLocation(Blocks.SCULK_SENSOR, "_inactive");
      ResourceLocation â˜ƒx = ModelLocationUtils.getModelLocation(Blocks.SCULK_SENSOR, "_active");
      this.delegateItemModel(Blocks.SCULK_SENSOR, â˜ƒ);
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(Blocks.SCULK_SENSOR)
               .with(
                  PropertyDispatch.property(BlockStateProperties.SCULK_SENSOR_PHASE)
                     .generate(var2x -> Variant.variant().with(VariantProperties.MODEL, var2x == SculkSensorPhase.ACTIVE ? â˜ƒ : â˜ƒ))
               )
         );
   }

   private void createScaffolding() {
      ResourceLocation â˜ƒ = ModelLocationUtils.getModelLocation(Blocks.SCAFFOLDING, "_stable");
      ResourceLocation â˜ƒx = ModelLocationUtils.getModelLocation(Blocks.SCAFFOLDING, "_unstable");
      this.delegateItemModel(Blocks.SCAFFOLDING, â˜ƒ);
      this.blockStateOutput
         .accept(MultiVariantGenerator.multiVariant(Blocks.SCAFFOLDING).with(createBooleanModelDispatch(BlockStateProperties.BOTTOM, â˜ƒx, â˜ƒ)));
   }

   private void createCaveVines() {
      ResourceLocation â˜ƒ = this.createSuffixedVariant(Blocks.CAVE_VINES, "", ModelTemplates.CROSS, TextureMapping::cross);
      ResourceLocation â˜ƒx = this.createSuffixedVariant(Blocks.CAVE_VINES, "_lit", ModelTemplates.CROSS, TextureMapping::cross);
      this.blockStateOutput
         .accept(MultiVariantGenerator.multiVariant(Blocks.CAVE_VINES).with(createBooleanModelDispatch(BlockStateProperties.BERRIES, â˜ƒx, â˜ƒ)));
      ResourceLocation â˜ƒxx = this.createSuffixedVariant(Blocks.CAVE_VINES_PLANT, "", ModelTemplates.CROSS, TextureMapping::cross);
      ResourceLocation â˜ƒxxx = this.createSuffixedVariant(Blocks.CAVE_VINES_PLANT, "_lit", ModelTemplates.CROSS, TextureMapping::cross);
      this.blockStateOutput
         .accept(MultiVariantGenerator.multiVariant(Blocks.CAVE_VINES_PLANT).with(createBooleanModelDispatch(BlockStateProperties.BERRIES, â˜ƒxxx, â˜ƒxx)));
   }

   private void createRedstoneLamp() {
      ResourceLocation â˜ƒ = TexturedModel.CUBE.create(Blocks.REDSTONE_LAMP, this.modelOutput);
      ResourceLocation â˜ƒx = this.createSuffixedVariant(Blocks.REDSTONE_LAMP, "_on", ModelTemplates.CUBE_ALL, TextureMapping::cube);
      this.blockStateOutput
         .accept(MultiVariantGenerator.multiVariant(Blocks.REDSTONE_LAMP).with(createBooleanModelDispatch(BlockStateProperties.LIT, â˜ƒx, â˜ƒ)));
   }

   private void createNormalTorch(Block var1, Block var2) {
      TextureMapping â˜ƒ = TextureMapping.torch(â˜ƒ);
      this.blockStateOutput.accept(createSimpleBlock(â˜ƒ, ModelTemplates.TORCH.create(â˜ƒ, â˜ƒ, this.modelOutput)));
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(
                  â˜ƒ, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.WALL_TORCH.create(â˜ƒ, â˜ƒ, this.modelOutput))
               )
               .with(createTorchHorizontalDispatch())
         );
      this.createSimpleFlatItemModel(â˜ƒ);
      this.skipAutoItemBlock(â˜ƒ);
   }

   private void createRedstoneTorch() {
      TextureMapping â˜ƒ = TextureMapping.torch(Blocks.REDSTONE_TORCH);
      TextureMapping â˜ƒx = TextureMapping.torch(TextureMapping.getBlockTexture(Blocks.REDSTONE_TORCH, "_off"));
      ResourceLocation â˜ƒxx = ModelTemplates.TORCH.create(Blocks.REDSTONE_TORCH, â˜ƒ, this.modelOutput);
      ResourceLocation â˜ƒxxx = ModelTemplates.TORCH.createWithSuffix(Blocks.REDSTONE_TORCH, "_off", â˜ƒx, this.modelOutput);
      this.blockStateOutput
         .accept(MultiVariantGenerator.multiVariant(Blocks.REDSTONE_TORCH).with(createBooleanModelDispatch(BlockStateProperties.LIT, â˜ƒxx, â˜ƒxxx)));
      ResourceLocation â˜ƒxxxx = ModelTemplates.WALL_TORCH.create(Blocks.REDSTONE_WALL_TORCH, â˜ƒ, this.modelOutput);
      ResourceLocation â˜ƒxxxxx = ModelTemplates.WALL_TORCH.createWithSuffix(Blocks.REDSTONE_WALL_TORCH, "_off", â˜ƒx, this.modelOutput);
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(Blocks.REDSTONE_WALL_TORCH)
               .with(createBooleanModelDispatch(BlockStateProperties.LIT, â˜ƒxxxx, â˜ƒxxxxx))
               .with(createTorchHorizontalDispatch())
         );
      this.createSimpleFlatItemModel(Blocks.REDSTONE_TORCH);
      this.skipAutoItemBlock(Blocks.REDSTONE_WALL_TORCH);
   }

   private void createRepeater() {
      this.createSimpleFlatItemModel(Items.REPEATER);
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(Blocks.REPEATER)
               .with(
                  PropertyDispatch.properties(BlockStateProperties.DELAY, BlockStateProperties.LOCKED, BlockStateProperties.POWERED)
                     .generate((var0, var1, var2) -> {
                        StringBuilder â˜ƒ = new StringBuilder();
                        â˜ƒ.append('_').append(var0).append("tick");
                        if (var2) {
                           â˜ƒ.append("_on");
                        }
               
                        if (var1) {
                           â˜ƒ.append("_locked");
                        }
               
                        return Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.REPEATER, â˜ƒ.toString()));
                     })
               )
               .with(createHorizontalFacingDispatchAlt())
         );
   }

   private void createSeaPickle() {
      this.createSimpleFlatItemModel(Items.SEA_PICKLE);
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(Blocks.SEA_PICKLE)
               .with(
                  PropertyDispatch.properties(BlockStateProperties.PICKLES, BlockStateProperties.WATERLOGGED)
                     .select(1, false, Arrays.asList(createRotatedVariants(ModelLocationUtils.decorateBlockModelLocation("dead_sea_pickle"))))
                     .select(2, false, Arrays.asList(createRotatedVariants(ModelLocationUtils.decorateBlockModelLocation("two_dead_sea_pickles"))))
                     .select(3, false, Arrays.asList(createRotatedVariants(ModelLocationUtils.decorateBlockModelLocation("three_dead_sea_pickles"))))
                     .select(4, false, Arrays.asList(createRotatedVariants(ModelLocationUtils.decorateBlockModelLocation("four_dead_sea_pickles"))))
                     .select(1, true, Arrays.asList(createRotatedVariants(ModelLocationUtils.decorateBlockModelLocation("sea_pickle"))))
                     .select(2, true, Arrays.asList(createRotatedVariants(ModelLocationUtils.decorateBlockModelLocation("two_sea_pickles"))))
                     .select(3, true, Arrays.asList(createRotatedVariants(ModelLocationUtils.decorateBlockModelLocation("three_sea_pickles"))))
                     .select(4, true, Arrays.asList(createRotatedVariants(ModelLocationUtils.decorateBlockModelLocation("four_sea_pickles"))))
               )
         );
   }

   private void createSnowBlocks() {
      TextureMapping â˜ƒ = TextureMapping.cube(Blocks.SNOW);
      ResourceLocation â˜ƒx = ModelTemplates.CUBE_ALL.create(Blocks.SNOW_BLOCK, â˜ƒ, this.modelOutput);
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(Blocks.SNOW)
               .with(
                  PropertyDispatch.property(BlockStateProperties.LAYERS)
                     .generate(
                        var1x -> Variant.variant()
                              .with(VariantProperties.MODEL, var1x < 8 ? ModelLocationUtils.getModelLocation(Blocks.SNOW, "_height" + var1x * 2) : â˜ƒ)
                     )
               )
         );
      this.delegateItemModel(Blocks.SNOW, ModelLocationUtils.getModelLocation(Blocks.SNOW, "_height2"));
      this.blockStateOutput.accept(createSimpleBlock(Blocks.SNOW_BLOCK, â˜ƒx));
   }

   private void createStonecutter() {
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(
                  Blocks.STONECUTTER, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.STONECUTTER))
               )
               .with(createHorizontalFacingDispatch())
         );
   }

   private void createStructureBlock() {
      ResourceLocation â˜ƒ = TexturedModel.CUBE.create(Blocks.STRUCTURE_BLOCK, this.modelOutput);
      this.delegateItemModel(Blocks.STRUCTURE_BLOCK, â˜ƒ);
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(Blocks.STRUCTURE_BLOCK)
               .with(
                  PropertyDispatch.property(BlockStateProperties.STRUCTUREBLOCK_MODE)
                     .generate(
                        var1x -> Variant.variant()
                              .with(
                                 VariantProperties.MODEL,
                                 this.createSuffixedVariant(
                                    Blocks.STRUCTURE_BLOCK, "_" + var1x.getSerializedName(), ModelTemplates.CUBE_ALL, TextureMapping::cube
                                 )
                              )
                     )
               )
         );
   }

   private void createSweetBerryBush() {
      this.createSimpleFlatItemModel(Items.SWEET_BERRIES);
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(Blocks.SWEET_BERRY_BUSH)
               .with(
                  PropertyDispatch.property(BlockStateProperties.AGE_3)
                     .generate(
                        var1 -> Variant.variant()
                              .with(
                                 VariantProperties.MODEL,
                                 this.createSuffixedVariant(Blocks.SWEET_BERRY_BUSH, "_stage" + var1, ModelTemplates.CROSS, TextureMapping::cross)
                              )
                     )
               )
         );
   }

   private void createTripwire() {
      this.createSimpleFlatItemModel(Items.STRING);
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(Blocks.TRIPWIRE)
               .with(
                  PropertyDispatch.properties(
                        BlockStateProperties.ATTACHED,
                        BlockStateProperties.EAST,
                        BlockStateProperties.NORTH,
                        BlockStateProperties.SOUTH,
                        BlockStateProperties.WEST
                     )
                     .select(
                        false,
                        false,
                        false,
                        false,
                        false,
                        Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_ns"))
                     )
                     .select(
                        false,
                        true,
                        false,
                        false,
                        false,
                        Variant.variant()
                           .with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_n"))
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     )
                     .select(
                        false,
                        false,
                        true,
                        false,
                        false,
                        Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_n"))
                     )
                     .select(
                        false,
                        false,
                        false,
                        true,
                        false,
                        Variant.variant()
                           .with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_n"))
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     )
                     .select(
                        false,
                        false,
                        false,
                        false,
                        true,
                        Variant.variant()
                           .with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_n"))
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     )
                     .select(
                        false,
                        true,
                        true,
                        false,
                        false,
                        Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_ne"))
                     )
                     .select(
                        false,
                        true,
                        false,
                        true,
                        false,
                        Variant.variant()
                           .with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_ne"))
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     )
                     .select(
                        false,
                        false,
                        false,
                        true,
                        true,
                        Variant.variant()
                           .with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_ne"))
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     )
                     .select(
                        false,
                        false,
                        true,
                        false,
                        true,
                        Variant.variant()
                           .with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_ne"))
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     )
                     .select(
                        false,
                        false,
                        true,
                        true,
                        false,
                        Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_ns"))
                     )
                     .select(
                        false,
                        true,
                        false,
                        false,
                        true,
                        Variant.variant()
                           .with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_ns"))
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     )
                     .select(
                        false,
                        true,
                        true,
                        true,
                        false,
                        Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_nse"))
                     )
                     .select(
                        false,
                        true,
                        false,
                        true,
                        true,
                        Variant.variant()
                           .with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_nse"))
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     )
                     .select(
                        false,
                        false,
                        true,
                        true,
                        true,
                        Variant.variant()
                           .with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_nse"))
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     )
                     .select(
                        false,
                        true,
                        true,
                        false,
                        true,
                        Variant.variant()
                           .with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_nse"))
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     )
                     .select(
                        false,
                        true,
                        true,
                        true,
                        true,
                        Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_nsew"))
                     )
                     .select(
                        true,
                        false,
                        false,
                        false,
                        false,
                        Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_ns"))
                     )
                     .select(
                        true,
                        false,
                        true,
                        false,
                        false,
                        Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_n"))
                     )
                     .select(
                        true,
                        false,
                        false,
                        true,
                        false,
                        Variant.variant()
                           .with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_n"))
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     )
                     .select(
                        true,
                        true,
                        false,
                        false,
                        false,
                        Variant.variant()
                           .with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_n"))
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     )
                     .select(
                        true,
                        false,
                        false,
                        false,
                        true,
                        Variant.variant()
                           .with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_n"))
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     )
                     .select(
                        true,
                        true,
                        true,
                        false,
                        false,
                        Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_ne"))
                     )
                     .select(
                        true,
                        true,
                        false,
                        true,
                        false,
                        Variant.variant()
                           .with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_ne"))
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     )
                     .select(
                        true,
                        false,
                        false,
                        true,
                        true,
                        Variant.variant()
                           .with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_ne"))
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     )
                     .select(
                        true,
                        false,
                        true,
                        false,
                        true,
                        Variant.variant()
                           .with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_ne"))
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     )
                     .select(
                        true,
                        false,
                        true,
                        true,
                        false,
                        Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_ns"))
                     )
                     .select(
                        true,
                        true,
                        false,
                        false,
                        true,
                        Variant.variant()
                           .with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_ns"))
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     )
                     .select(
                        true,
                        true,
                        true,
                        true,
                        false,
                        Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_nse"))
                     )
                     .select(
                        true,
                        true,
                        false,
                        true,
                        true,
                        Variant.variant()
                           .with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_nse"))
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                     )
                     .select(
                        true,
                        false,
                        true,
                        true,
                        true,
                        Variant.variant()
                           .with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_nse"))
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                     )
                     .select(
                        true,
                        true,
                        true,
                        false,
                        true,
                        Variant.variant()
                           .with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_nse"))
                           .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                     )
                     .select(
                        true,
                        true,
                        true,
                        true,
                        true,
                        Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_nsew"))
                     )
               )
         );
   }

   private void createTripwireHook() {
      this.createSimpleFlatItemModel(Blocks.TRIPWIRE_HOOK);
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(Blocks.TRIPWIRE_HOOK)
               .with(
                  PropertyDispatch.properties(BlockStateProperties.ATTACHED, BlockStateProperties.POWERED)
                     .generate(
                        (var0, var1) -> Variant.variant()
                              .with(
                                 VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.TRIPWIRE_HOOK, (var0 ? "_attached" : "") + (var1 ? "_on" : ""))
                              )
                     )
               )
               .with(createHorizontalFacingDispatch())
         );
   }

   private ResourceLocation createTurtleEggModel(int var1, String var2, TextureMapping var3) {
      switch(â˜ƒ) {
         case 1:
            return ModelTemplates.TURTLE_EGG.create(ModelLocationUtils.decorateBlockModelLocation(â˜ƒ + "turtle_egg"), â˜ƒ, this.modelOutput);
         case 2:
            return ModelTemplates.TWO_TURTLE_EGGS.create(ModelLocationUtils.decorateBlockModelLocation("two_" + â˜ƒ + "turtle_eggs"), â˜ƒ, this.modelOutput);
         case 3:
            return ModelTemplates.THREE_TURTLE_EGGS
               .create(ModelLocationUtils.decorateBlockModelLocation("three_" + â˜ƒ + "turtle_eggs"), â˜ƒ, this.modelOutput);
         case 4:
            return ModelTemplates.FOUR_TURTLE_EGGS.create(ModelLocationUtils.decorateBlockModelLocation("four_" + â˜ƒ + "turtle_eggs"), â˜ƒ, this.modelOutput);
         default:
            throw new UnsupportedOperationException();
      }
   }

   private ResourceLocation createTurtleEggModel(Integer var1, Integer var2) {
      switch(â˜ƒ) {
         case 0:
            return this.createTurtleEggModel(â˜ƒ, "", TextureMapping.cube(TextureMapping.getBlockTexture(Blocks.TURTLE_EGG)));
         case 1:
            return this.createTurtleEggModel(
               â˜ƒ, "slightly_cracked_", TextureMapping.cube(TextureMapping.getBlockTexture(Blocks.TURTLE_EGG, "_slightly_cracked"))
            );
         case 2:
            return this.createTurtleEggModel(â˜ƒ, "very_cracked_", TextureMapping.cube(TextureMapping.getBlockTexture(Blocks.TURTLE_EGG, "_very_cracked")));
         default:
            throw new UnsupportedOperationException();
      }
   }

   private void createTurtleEgg() {
      this.createSimpleFlatItemModel(Items.TURTLE_EGG);
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(Blocks.TURTLE_EGG)
               .with(
                  PropertyDispatch.properties(BlockStateProperties.EGGS, BlockStateProperties.HATCH)
                     .generateList((var1, var2) -> Arrays.asList(createRotatedVariants(this.createTurtleEggModel(var1, var2))))
               )
         );
   }

   private void createMultiface(Block var1) {
      this.createSimpleFlatItemModel(â˜ƒ);
      ResourceLocation â˜ƒ = ModelLocationUtils.getModelLocation(â˜ƒ);
      MultiPartGenerator â˜ƒx = MultiPartGenerator.multiPart(â˜ƒ);
      Condition.TerminalCondition â˜ƒxx = Util.make(Condition.condition(), var1x -> MULTIFACE_GENERATOR.forEach((var2x, var3x) -> {
            if (â˜ƒ.defaultBlockState().hasProperty(var2x)) {
               var1x.term(var2x, false);
            }
         }));
      MULTIFACE_GENERATOR.forEach((var4x, var5) -> {
         if (â˜ƒ.defaultBlockState().hasProperty(var4x)) {
            â˜ƒ.with(Condition.condition().term(var4x, true), (Variant)var5.apply(â˜ƒ));
            â˜ƒ.with(â˜ƒ, (Variant)var5.apply(â˜ƒ));
         }
      });
      this.blockStateOutput.accept(â˜ƒx);
   }

   private void createMagmaBlock() {
      this.blockStateOutput
         .accept(
            createSimpleBlock(
               Blocks.MAGMA_BLOCK,
               ModelTemplates.CUBE_ALL
                  .create(Blocks.MAGMA_BLOCK, TextureMapping.cube(ModelLocationUtils.decorateBlockModelLocation("magma")), this.modelOutput)
            )
         );
   }

   private void createShulkerBox(Block var1) {
      this.createTrivialBlock(â˜ƒ, TexturedModel.PARTICLE_ONLY);
      ModelTemplates.SHULKER_BOX_INVENTORY.create(ModelLocationUtils.getModelLocation(â˜ƒ.asItem()), TextureMapping.particle(â˜ƒ), this.modelOutput);
   }

   private void createGrowingPlant(Block var1, Block var2, BlockModelGenerators.TintState var3) {
      this.createCrossBlock(â˜ƒ, â˜ƒ);
      this.createCrossBlock(â˜ƒ, â˜ƒ);
   }

   private void createBedItem(Block var1, Block var2) {
      ModelTemplates.BED_INVENTORY.create(ModelLocationUtils.getModelLocation(â˜ƒ.asItem()), TextureMapping.particle(â˜ƒ), this.modelOutput);
   }

   private void createInfestedStone() {
      ResourceLocation â˜ƒ = ModelLocationUtils.getModelLocation(Blocks.STONE);
      ResourceLocation â˜ƒx = ModelLocationUtils.getModelLocation(Blocks.STONE, "_mirrored");
      this.blockStateOutput.accept(createRotatedVariant(Blocks.INFESTED_STONE, â˜ƒ, â˜ƒx));
      this.delegateItemModel(Blocks.INFESTED_STONE, â˜ƒ);
   }

   private void createInfestedDeepslate() {
      ResourceLocation â˜ƒ = ModelLocationUtils.getModelLocation(Blocks.DEEPSLATE);
      ResourceLocation â˜ƒx = ModelLocationUtils.getModelLocation(Blocks.DEEPSLATE, "_mirrored");
      this.blockStateOutput.accept(createRotatedVariant(Blocks.INFESTED_DEEPSLATE, â˜ƒ, â˜ƒx).with(createRotatedPillar()));
      this.delegateItemModel(Blocks.INFESTED_DEEPSLATE, â˜ƒ);
   }

   private void createNetherRoots(Block var1, Block var2) {
      this.createCrossBlockWithDefaultItem(â˜ƒ, BlockModelGenerators.TintState.NOT_TINTED);
      TextureMapping â˜ƒ = TextureMapping.plant(TextureMapping.getBlockTexture(â˜ƒ, "_pot"));
      ResourceLocation â˜ƒx = BlockModelGenerators.TintState.NOT_TINTED.getCrossPot().create(â˜ƒ, â˜ƒ, this.modelOutput);
      this.blockStateOutput.accept(createSimpleBlock(â˜ƒ, â˜ƒx));
   }

   private void createRespawnAnchor() {
      ResourceLocation â˜ƒ = TextureMapping.getBlockTexture(Blocks.RESPAWN_ANCHOR, "_bottom");
      ResourceLocation â˜ƒx = TextureMapping.getBlockTexture(Blocks.RESPAWN_ANCHOR, "_top_off");
      ResourceLocation â˜ƒxx = TextureMapping.getBlockTexture(Blocks.RESPAWN_ANCHOR, "_top");
      ResourceLocation[] â˜ƒxxx = new ResourceLocation[5];

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < 5; ++â˜ƒxxxx) {
         TextureMapping â˜ƒxxxxx = new TextureMapping()
            .put(TextureSlot.BOTTOM, â˜ƒ)
            .put(TextureSlot.TOP, â˜ƒxxxx == 0 ? â˜ƒx : â˜ƒxx)
            .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.RESPAWN_ANCHOR, "_side" + â˜ƒxxxx));
         â˜ƒxxx[â˜ƒxxxx] = ModelTemplates.CUBE_BOTTOM_TOP.createWithSuffix(Blocks.RESPAWN_ANCHOR, "_" + â˜ƒxxxx, â˜ƒxxxxx, this.modelOutput);
      }

      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(Blocks.RESPAWN_ANCHOR)
               .with(
                  PropertyDispatch.property(BlockStateProperties.RESPAWN_ANCHOR_CHARGES)
                     .generate(var1x -> Variant.variant().with(VariantProperties.MODEL, â˜ƒ[var1x]))
               )
         );
      this.delegateItemModel(Items.RESPAWN_ANCHOR, â˜ƒxxx[0]);
   }

   private Variant applyRotation(FrontAndTop var1, Variant var2) {
      switch(â˜ƒ) {
         case DOWN_NORTH:
            return â˜ƒ.with(VariantProperties.X_ROT, VariantProperties.Rotation.R90);
         case DOWN_SOUTH:
            return â˜ƒ.with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180);
         case DOWN_WEST:
            return â˜ƒ.with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270);
         case DOWN_EAST:
            return â˜ƒ.with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90);
         case UP_NORTH:
            return â˜ƒ.with(VariantProperties.X_ROT, VariantProperties.Rotation.R270).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180);
         case UP_SOUTH:
            return â˜ƒ.with(VariantProperties.X_ROT, VariantProperties.Rotation.R270);
         case UP_WEST:
            return â˜ƒ.with(VariantProperties.X_ROT, VariantProperties.Rotation.R270).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90);
         case UP_EAST:
            return â˜ƒ.with(VariantProperties.X_ROT, VariantProperties.Rotation.R270).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270);
         case NORTH_UP:
            return â˜ƒ;
         case SOUTH_UP:
            return â˜ƒ.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180);
         case WEST_UP:
            return â˜ƒ.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270);
         case EAST_UP:
            return â˜ƒ.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90);
         default:
            throw new UnsupportedOperationException("Rotation " + â˜ƒ + " can't be expressed with existing x and y values");
      }
   }

   private void createJigsaw() {
      ResourceLocation â˜ƒ = TextureMapping.getBlockTexture(Blocks.JIGSAW, "_top");
      ResourceLocation â˜ƒx = TextureMapping.getBlockTexture(Blocks.JIGSAW, "_bottom");
      ResourceLocation â˜ƒxx = TextureMapping.getBlockTexture(Blocks.JIGSAW, "_side");
      ResourceLocation â˜ƒxxx = TextureMapping.getBlockTexture(Blocks.JIGSAW, "_lock");
      TextureMapping â˜ƒxxxx = new TextureMapping()
         .put(TextureSlot.DOWN, â˜ƒxx)
         .put(TextureSlot.WEST, â˜ƒxx)
         .put(TextureSlot.EAST, â˜ƒxx)
         .put(TextureSlot.PARTICLE, â˜ƒ)
         .put(TextureSlot.NORTH, â˜ƒ)
         .put(TextureSlot.SOUTH, â˜ƒx)
         .put(TextureSlot.UP, â˜ƒxxx);
      ResourceLocation â˜ƒxxxxx = ModelTemplates.CUBE_DIRECTIONAL.create(Blocks.JIGSAW, â˜ƒxxxx, this.modelOutput);
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(Blocks.JIGSAW, Variant.variant().with(VariantProperties.MODEL, â˜ƒxxxxx))
               .with(PropertyDispatch.property(BlockStateProperties.ORIENTATION).generate(var1x -> this.applyRotation(var1x, Variant.variant())))
         );
   }

   private void createPetrifiedOakSlab() {
      Block â˜ƒ = Blocks.OAK_PLANKS;
      ResourceLocation â˜ƒx = ModelLocationUtils.getModelLocation(â˜ƒ);
      TexturedModel â˜ƒxx = TexturedModel.CUBE.get(â˜ƒ);
      Block â˜ƒxxx = Blocks.PETRIFIED_OAK_SLAB;
      ResourceLocation â˜ƒxxxx = ModelTemplates.SLAB_BOTTOM.create(â˜ƒxxx, â˜ƒxx.getMapping(), this.modelOutput);
      ResourceLocation â˜ƒxxxxx = ModelTemplates.SLAB_TOP.create(â˜ƒxxx, â˜ƒxx.getMapping(), this.modelOutput);
      this.blockStateOutput.accept(createSlab(â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒx));
   }

   public void run() {
      BlockFamilies.getAllFamilies().filter(BlockFamily::shouldGenerateModel).forEach(var1 -> this.family(var1.getBaseBlock()).generateFor(var1));
      this.family(Blocks.CUT_COPPER).generateFor(BlockFamilies.CUT_COPPER).fullBlockCopies(Blocks.WAXED_CUT_COPPER).generateFor(BlockFamilies.WAXED_CUT_COPPER);
      this.family(Blocks.EXPOSED_CUT_COPPER)
         .generateFor(BlockFamilies.EXPOSED_CUT_COPPER)
         .fullBlockCopies(Blocks.WAXED_EXPOSED_CUT_COPPER)
         .generateFor(BlockFamilies.WAXED_EXPOSED_CUT_COPPER);
      this.family(Blocks.WEATHERED_CUT_COPPER)
         .generateFor(BlockFamilies.WEATHERED_CUT_COPPER)
         .fullBlockCopies(Blocks.WAXED_WEATHERED_CUT_COPPER)
         .generateFor(BlockFamilies.WAXED_WEATHERED_CUT_COPPER);
      this.family(Blocks.OXIDIZED_CUT_COPPER)
         .generateFor(BlockFamilies.OXIDIZED_CUT_COPPER)
         .fullBlockCopies(Blocks.WAXED_OXIDIZED_CUT_COPPER)
         .generateFor(BlockFamilies.WAXED_OXIDIZED_CUT_COPPER);
      this.createNonTemplateModelBlock(Blocks.AIR);
      this.createNonTemplateModelBlock(Blocks.CAVE_AIR, Blocks.AIR);
      this.createNonTemplateModelBlock(Blocks.VOID_AIR, Blocks.AIR);
      this.createNonTemplateModelBlock(Blocks.BEACON);
      this.createNonTemplateModelBlock(Blocks.CACTUS);
      this.createNonTemplateModelBlock(Blocks.BUBBLE_COLUMN, Blocks.WATER);
      this.createNonTemplateModelBlock(Blocks.DRAGON_EGG);
      this.createNonTemplateModelBlock(Blocks.DRIED_KELP_BLOCK);
      this.createNonTemplateModelBlock(Blocks.ENCHANTING_TABLE);
      this.createNonTemplateModelBlock(Blocks.FLOWER_POT);
      this.createSimpleFlatItemModel(Items.FLOWER_POT);
      this.createNonTemplateModelBlock(Blocks.HONEY_BLOCK);
      this.createNonTemplateModelBlock(Blocks.WATER);
      this.createNonTemplateModelBlock(Blocks.LAVA);
      this.createNonTemplateModelBlock(Blocks.SLIME_BLOCK);
      this.createSimpleFlatItemModel(Items.CHAIN);
      this.createCandleAndCandleCake(Blocks.WHITE_CANDLE, Blocks.WHITE_CANDLE_CAKE);
      this.createCandleAndCandleCake(Blocks.ORANGE_CANDLE, Blocks.ORANGE_CANDLE_CAKE);
      this.createCandleAndCandleCake(Blocks.MAGENTA_CANDLE, Blocks.MAGENTA_CANDLE_CAKE);
      this.createCandleAndCandleCake(Blocks.LIGHT_BLUE_CANDLE, Blocks.LIGHT_BLUE_CANDLE_CAKE);
      this.createCandleAndCandleCake(Blocks.YELLOW_CANDLE, Blocks.YELLOW_CANDLE_CAKE);
      this.createCandleAndCandleCake(Blocks.LIME_CANDLE, Blocks.LIME_CANDLE_CAKE);
      this.createCandleAndCandleCake(Blocks.PINK_CANDLE, Blocks.PINK_CANDLE_CAKE);
      this.createCandleAndCandleCake(Blocks.GRAY_CANDLE, Blocks.GRAY_CANDLE_CAKE);
      this.createCandleAndCandleCake(Blocks.LIGHT_GRAY_CANDLE, Blocks.LIGHT_GRAY_CANDLE_CAKE);
      this.createCandleAndCandleCake(Blocks.CYAN_CANDLE, Blocks.CYAN_CANDLE_CAKE);
      this.createCandleAndCandleCake(Blocks.PURPLE_CANDLE, Blocks.PURPLE_CANDLE_CAKE);
      this.createCandleAndCandleCake(Blocks.BLUE_CANDLE, Blocks.BLUE_CANDLE_CAKE);
      this.createCandleAndCandleCake(Blocks.BROWN_CANDLE, Blocks.BROWN_CANDLE_CAKE);
      this.createCandleAndCandleCake(Blocks.GREEN_CANDLE, Blocks.GREEN_CANDLE_CAKE);
      this.createCandleAndCandleCake(Blocks.RED_CANDLE, Blocks.RED_CANDLE_CAKE);
      this.createCandleAndCandleCake(Blocks.BLACK_CANDLE, Blocks.BLACK_CANDLE_CAKE);
      this.createCandleAndCandleCake(Blocks.CANDLE, Blocks.CANDLE_CAKE);
      this.createNonTemplateModelBlock(Blocks.POTTED_BAMBOO);
      this.createNonTemplateModelBlock(Blocks.POTTED_CACTUS);
      this.createNonTemplateModelBlock(Blocks.POWDER_SNOW);
      this.createNonTemplateModelBlock(Blocks.SPORE_BLOSSOM);
      this.createAzalea(Blocks.AZALEA);
      this.createAzalea(Blocks.FLOWERING_AZALEA);
      this.createPottedAzalea(Blocks.POTTED_AZALEA);
      this.createPottedAzalea(Blocks.POTTED_FLOWERING_AZALEA);
      this.createCaveVines();
      this.createFullAndCarpetBlocks(Blocks.MOSS_BLOCK, Blocks.MOSS_CARPET);
      this.createAirLikeBlock(Blocks.BARRIER, Items.BARRIER);
      this.createSimpleFlatItemModel(Items.BARRIER);
      this.createAirLikeBlock(Blocks.LIGHT, Items.LIGHT);
      this.createLightBlockItems();
      this.createAirLikeBlock(Blocks.STRUCTURE_VOID, Items.STRUCTURE_VOID);
      this.createSimpleFlatItemModel(Items.STRUCTURE_VOID);
      this.createAirLikeBlock(Blocks.MOVING_PISTON, TextureMapping.getBlockTexture(Blocks.PISTON, "_side"));
      this.createTrivialCube(Blocks.COAL_ORE);
      this.createTrivialCube(Blocks.DEEPSLATE_COAL_ORE);
      this.createTrivialCube(Blocks.COAL_BLOCK);
      this.createTrivialCube(Blocks.DIAMOND_ORE);
      this.createTrivialCube(Blocks.DEEPSLATE_DIAMOND_ORE);
      this.createTrivialCube(Blocks.DIAMOND_BLOCK);
      this.createTrivialCube(Blocks.EMERALD_ORE);
      this.createTrivialCube(Blocks.DEEPSLATE_EMERALD_ORE);
      this.createTrivialCube(Blocks.EMERALD_BLOCK);
      this.createTrivialCube(Blocks.GOLD_ORE);
      this.createTrivialCube(Blocks.NETHER_GOLD_ORE);
      this.createTrivialCube(Blocks.DEEPSLATE_GOLD_ORE);
      this.createTrivialCube(Blocks.GOLD_BLOCK);
      this.createTrivialCube(Blocks.IRON_ORE);
      this.createTrivialCube(Blocks.DEEPSLATE_IRON_ORE);
      this.createTrivialCube(Blocks.IRON_BLOCK);
      this.createTrivialBlock(Blocks.ANCIENT_DEBRIS, TexturedModel.COLUMN);
      this.createTrivialCube(Blocks.NETHERITE_BLOCK);
      this.createTrivialCube(Blocks.LAPIS_ORE);
      this.createTrivialCube(Blocks.DEEPSLATE_LAPIS_ORE);
      this.createTrivialCube(Blocks.LAPIS_BLOCK);
      this.createTrivialCube(Blocks.NETHER_QUARTZ_ORE);
      this.createTrivialCube(Blocks.REDSTONE_ORE);
      this.createTrivialCube(Blocks.DEEPSLATE_REDSTONE_ORE);
      this.createTrivialCube(Blocks.REDSTONE_BLOCK);
      this.createTrivialCube(Blocks.GILDED_BLACKSTONE);
      this.createTrivialCube(Blocks.BLUE_ICE);
      this.createTrivialCube(Blocks.CLAY);
      this.createTrivialCube(Blocks.COARSE_DIRT);
      this.createTrivialCube(Blocks.CRYING_OBSIDIAN);
      this.createTrivialCube(Blocks.END_STONE);
      this.createTrivialCube(Blocks.GLOWSTONE);
      this.createTrivialCube(Blocks.GRAVEL);
      this.createTrivialCube(Blocks.HONEYCOMB_BLOCK);
      this.createTrivialCube(Blocks.ICE);
      this.createTrivialBlock(Blocks.JUKEBOX, TexturedModel.CUBE_TOP);
      this.createTrivialBlock(Blocks.LODESTONE, TexturedModel.COLUMN);
      this.createTrivialBlock(Blocks.MELON, TexturedModel.COLUMN);
      this.createTrivialCube(Blocks.NETHER_WART_BLOCK);
      this.createTrivialCube(Blocks.NOTE_BLOCK);
      this.createTrivialCube(Blocks.PACKED_ICE);
      this.createTrivialCube(Blocks.OBSIDIAN);
      this.createTrivialCube(Blocks.QUARTZ_BRICKS);
      this.createTrivialCube(Blocks.SEA_LANTERN);
      this.createTrivialCube(Blocks.SHROOMLIGHT);
      this.createTrivialCube(Blocks.SOUL_SAND);
      this.createTrivialCube(Blocks.SOUL_SOIL);
      this.createTrivialCube(Blocks.SPAWNER);
      this.createTrivialCube(Blocks.SPONGE);
      this.createTrivialBlock(Blocks.SEAGRASS, TexturedModel.SEAGRASS);
      this.createSimpleFlatItemModel(Items.SEAGRASS);
      this.createTrivialBlock(Blocks.TNT, TexturedModel.CUBE_TOP_BOTTOM);
      this.createTrivialBlock(Blocks.TARGET, TexturedModel.COLUMN);
      this.createTrivialCube(Blocks.WARPED_WART_BLOCK);
      this.createTrivialCube(Blocks.WET_SPONGE);
      this.createTrivialCube(Blocks.AMETHYST_BLOCK);
      this.createTrivialCube(Blocks.BUDDING_AMETHYST);
      this.createTrivialCube(Blocks.CALCITE);
      this.createTrivialCube(Blocks.TUFF);
      this.createTrivialCube(Blocks.DRIPSTONE_BLOCK);
      this.createTrivialCube(Blocks.RAW_IRON_BLOCK);
      this.createTrivialCube(Blocks.RAW_COPPER_BLOCK);
      this.createTrivialCube(Blocks.RAW_GOLD_BLOCK);
      this.createPetrifiedOakSlab();
      this.createTrivialCube(Blocks.COPPER_ORE);
      this.createTrivialCube(Blocks.DEEPSLATE_COPPER_ORE);
      this.createTrivialCube(Blocks.COPPER_BLOCK);
      this.createTrivialCube(Blocks.EXPOSED_COPPER);
      this.createTrivialCube(Blocks.WEATHERED_COPPER);
      this.createTrivialCube(Blocks.OXIDIZED_COPPER);
      this.copyModel(Blocks.COPPER_BLOCK, Blocks.WAXED_COPPER_BLOCK);
      this.copyModel(Blocks.EXPOSED_COPPER, Blocks.WAXED_EXPOSED_COPPER);
      this.copyModel(Blocks.WEATHERED_COPPER, Blocks.WAXED_WEATHERED_COPPER);
      this.copyModel(Blocks.OXIDIZED_COPPER, Blocks.WAXED_OXIDIZED_COPPER);
      this.createWeightedPressurePlate(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, Blocks.GOLD_BLOCK);
      this.createWeightedPressurePlate(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, Blocks.IRON_BLOCK);
      this.createAmethystClusters();
      this.createBookshelf();
      this.createBrewingStand();
      this.createCakeBlock();
      this.createCampfires(Blocks.CAMPFIRE, Blocks.SOUL_CAMPFIRE);
      this.createCartographyTable();
      this.createCauldrons();
      this.createChorusFlower();
      this.createChorusPlant();
      this.createComposter();
      this.createDaylightDetector();
      this.createEndPortalFrame();
      this.createRotatableColumn(Blocks.END_ROD);
      this.createLightningRod();
      this.createFarmland();
      this.createFire();
      this.createSoulFire();
      this.createFrostedIce();
      this.createGrassBlocks();
      this.createCocoa();
      this.createDirtPath();
      this.createGrindstone();
      this.createHopper();
      this.createIronBars();
      this.createLever();
      this.createLilyPad();
      this.createNetherPortalBlock();
      this.createNetherrack();
      this.createObserver();
      this.createPistons();
      this.createPistonHeads();
      this.createScaffolding();
      this.createRedstoneTorch();
      this.createRedstoneLamp();
      this.createRepeater();
      this.createSeaPickle();
      this.createSmithingTable();
      this.createSnowBlocks();
      this.createStonecutter();
      this.createStructureBlock();
      this.createSweetBerryBush();
      this.createTripwire();
      this.createTripwireHook();
      this.createTurtleEgg();
      this.createMultiface(Blocks.VINE);
      this.createMultiface(Blocks.GLOW_LICHEN);
      this.createMagmaBlock();
      this.createJigsaw();
      this.createSculkSensor();
      this.createNonTemplateHorizontalBlock(Blocks.LADDER);
      this.createSimpleFlatItemModel(Blocks.LADDER);
      this.createNonTemplateHorizontalBlock(Blocks.LECTERN);
      this.createBigDripLeafBlock();
      this.createNonTemplateHorizontalBlock(Blocks.BIG_DRIPLEAF_STEM);
      this.createNormalTorch(Blocks.TORCH, Blocks.WALL_TORCH);
      this.createNormalTorch(Blocks.SOUL_TORCH, Blocks.SOUL_WALL_TORCH);
      this.createCraftingTableLike(Blocks.CRAFTING_TABLE, Blocks.OAK_PLANKS, TextureMapping::craftingTable);
      this.createCraftingTableLike(Blocks.FLETCHING_TABLE, Blocks.BIRCH_PLANKS, TextureMapping::fletchingTable);
      this.createNyliumBlock(Blocks.CRIMSON_NYLIUM);
      this.createNyliumBlock(Blocks.WARPED_NYLIUM);
      this.createDispenserBlock(Blocks.DISPENSER);
      this.createDispenserBlock(Blocks.DROPPER);
      this.createLantern(Blocks.LANTERN);
      this.createLantern(Blocks.SOUL_LANTERN);
      this.createAxisAlignedPillarBlockCustomModel(Blocks.CHAIN, ModelLocationUtils.getModelLocation(Blocks.CHAIN));
      this.createAxisAlignedPillarBlock(Blocks.BASALT, TexturedModel.COLUMN);
      this.createAxisAlignedPillarBlock(Blocks.POLISHED_BASALT, TexturedModel.COLUMN);
      this.createTrivialCube(Blocks.SMOOTH_BASALT);
      this.createAxisAlignedPillarBlock(Blocks.BONE_BLOCK, TexturedModel.COLUMN);
      this.createRotatedVariantBlock(Blocks.DIRT);
      this.createRotatedVariantBlock(Blocks.ROOTED_DIRT);
      this.createRotatedVariantBlock(Blocks.SAND);
      this.createRotatedVariantBlock(Blocks.RED_SAND);
      this.createRotatedMirroredVariantBlock(Blocks.BEDROCK);
      this.createRotatedPillarWithHorizontalVariant(Blocks.HAY_BLOCK, TexturedModel.COLUMN, TexturedModel.COLUMN_HORIZONTAL);
      this.createRotatedPillarWithHorizontalVariant(Blocks.PURPUR_PILLAR, TexturedModel.COLUMN_ALT, TexturedModel.COLUMN_HORIZONTAL_ALT);
      this.createRotatedPillarWithHorizontalVariant(Blocks.QUARTZ_PILLAR, TexturedModel.COLUMN_ALT, TexturedModel.COLUMN_HORIZONTAL_ALT);
      this.createHorizontallyRotatedBlock(Blocks.LOOM, TexturedModel.ORIENTABLE);
      this.createPumpkins();
      this.createBeeNest(Blocks.BEE_NEST, TextureMapping::orientableCube);
      this.createBeeNest(Blocks.BEEHIVE, TextureMapping::orientableCubeSameEnds);
      this.createCropBlock(Blocks.BEETROOTS, BlockStateProperties.AGE_3, 0, 1, 2, 3);
      this.createCropBlock(Blocks.CARROTS, BlockStateProperties.AGE_7, 0, 0, 1, 1, 2, 2, 2, 3);
      this.createCropBlock(Blocks.NETHER_WART, BlockStateProperties.AGE_3, 0, 1, 1, 2);
      this.createCropBlock(Blocks.POTATOES, BlockStateProperties.AGE_7, 0, 0, 1, 1, 2, 2, 2, 3);
      this.createCropBlock(Blocks.WHEAT, BlockStateProperties.AGE_7, 0, 1, 2, 3, 4, 5, 6, 7);
      this.blockEntityModels(ModelLocationUtils.decorateBlockModelLocation("banner"), Blocks.OAK_PLANKS)
         .createWithCustomBlockItemModel(
            ModelTemplates.BANNER_INVENTORY,
            Blocks.WHITE_BANNER,
            Blocks.ORANGE_BANNER,
            Blocks.MAGENTA_BANNER,
            Blocks.LIGHT_BLUE_BANNER,
            Blocks.YELLOW_BANNER,
            Blocks.LIME_BANNER,
            Blocks.PINK_BANNER,
            Blocks.GRAY_BANNER,
            Blocks.LIGHT_GRAY_BANNER,
            Blocks.CYAN_BANNER,
            Blocks.PURPLE_BANNER,
            Blocks.BLUE_BANNER,
            Blocks.BROWN_BANNER,
            Blocks.GREEN_BANNER,
            Blocks.RED_BANNER,
            Blocks.BLACK_BANNER
         )
         .createWithoutBlockItem(
            Blocks.WHITE_WALL_BANNER,
            Blocks.ORANGE_WALL_BANNER,
            Blocks.MAGENTA_WALL_BANNER,
            Blocks.LIGHT_BLUE_WALL_BANNER,
            Blocks.YELLOW_WALL_BANNER,
            Blocks.LIME_WALL_BANNER,
            Blocks.PINK_WALL_BANNER,
            Blocks.GRAY_WALL_BANNER,
            Blocks.LIGHT_GRAY_WALL_BANNER,
            Blocks.CYAN_WALL_BANNER,
            Blocks.PURPLE_WALL_BANNER,
            Blocks.BLUE_WALL_BANNER,
            Blocks.BROWN_WALL_BANNER,
            Blocks.GREEN_WALL_BANNER,
            Blocks.RED_WALL_BANNER,
            Blocks.BLACK_WALL_BANNER
         );
      this.blockEntityModels(ModelLocationUtils.decorateBlockModelLocation("bed"), Blocks.OAK_PLANKS)
         .createWithoutBlockItem(
            Blocks.WHITE_BED,
            Blocks.ORANGE_BED,
            Blocks.MAGENTA_BED,
            Blocks.LIGHT_BLUE_BED,
            Blocks.YELLOW_BED,
            Blocks.LIME_BED,
            Blocks.PINK_BED,
            Blocks.GRAY_BED,
            Blocks.LIGHT_GRAY_BED,
            Blocks.CYAN_BED,
            Blocks.PURPLE_BED,
            Blocks.BLUE_BED,
            Blocks.BROWN_BED,
            Blocks.GREEN_BED,
            Blocks.RED_BED,
            Blocks.BLACK_BED
         );
      this.createBedItem(Blocks.WHITE_BED, Blocks.WHITE_WOOL);
      this.createBedItem(Blocks.ORANGE_BED, Blocks.ORANGE_WOOL);
      this.createBedItem(Blocks.MAGENTA_BED, Blocks.MAGENTA_WOOL);
      this.createBedItem(Blocks.LIGHT_BLUE_BED, Blocks.LIGHT_BLUE_WOOL);
      this.createBedItem(Blocks.YELLOW_BED, Blocks.YELLOW_WOOL);
      this.createBedItem(Blocks.LIME_BED, Blocks.LIME_WOOL);
      this.createBedItem(Blocks.PINK_BED, Blocks.PINK_WOOL);
      this.createBedItem(Blocks.GRAY_BED, Blocks.GRAY_WOOL);
      this.createBedItem(Blocks.LIGHT_GRAY_BED, Blocks.LIGHT_GRAY_WOOL);
      this.createBedItem(Blocks.CYAN_BED, Blocks.CYAN_WOOL);
      this.createBedItem(Blocks.PURPLE_BED, Blocks.PURPLE_WOOL);
      this.createBedItem(Blocks.BLUE_BED, Blocks.BLUE_WOOL);
      this.createBedItem(Blocks.BROWN_BED, Blocks.BROWN_WOOL);
      this.createBedItem(Blocks.GREEN_BED, Blocks.GREEN_WOOL);
      this.createBedItem(Blocks.RED_BED, Blocks.RED_WOOL);
      this.createBedItem(Blocks.BLACK_BED, Blocks.BLACK_WOOL);
      this.blockEntityModels(ModelLocationUtils.decorateBlockModelLocation("skull"), Blocks.SOUL_SAND)
         .createWithCustomBlockItemModel(
            ModelTemplates.SKULL_INVENTORY, Blocks.CREEPER_HEAD, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL
         )
         .create(Blocks.DRAGON_HEAD)
         .createWithoutBlockItem(
            Blocks.CREEPER_WALL_HEAD,
            Blocks.DRAGON_WALL_HEAD,
            Blocks.PLAYER_WALL_HEAD,
            Blocks.ZOMBIE_WALL_HEAD,
            Blocks.SKELETON_WALL_SKULL,
            Blocks.WITHER_SKELETON_WALL_SKULL
         );
      this.createShulkerBox(Blocks.SHULKER_BOX);
      this.createShulkerBox(Blocks.WHITE_SHULKER_BOX);
      this.createShulkerBox(Blocks.ORANGE_SHULKER_BOX);
      this.createShulkerBox(Blocks.MAGENTA_SHULKER_BOX);
      this.createShulkerBox(Blocks.LIGHT_BLUE_SHULKER_BOX);
      this.createShulkerBox(Blocks.YELLOW_SHULKER_BOX);
      this.createShulkerBox(Blocks.LIME_SHULKER_BOX);
      this.createShulkerBox(Blocks.PINK_SHULKER_BOX);
      this.createShulkerBox(Blocks.GRAY_SHULKER_BOX);
      this.createShulkerBox(Blocks.LIGHT_GRAY_SHULKER_BOX);
      this.createShulkerBox(Blocks.CYAN_SHULKER_BOX);
      this.createShulkerBox(Blocks.PURPLE_SHULKER_BOX);
      this.createShulkerBox(Blocks.BLUE_SHULKER_BOX);
      this.createShulkerBox(Blocks.BROWN_SHULKER_BOX);
      this.createShulkerBox(Blocks.GREEN_SHULKER_BOX);
      this.createShulkerBox(Blocks.RED_SHULKER_BOX);
      this.createShulkerBox(Blocks.BLACK_SHULKER_BOX);
      this.createTrivialBlock(Blocks.CONDUIT, TexturedModel.PARTICLE_ONLY);
      this.skipAutoItemBlock(Blocks.CONDUIT);
      this.blockEntityModels(ModelLocationUtils.decorateBlockModelLocation("chest"), Blocks.OAK_PLANKS)
         .createWithoutBlockItem(Blocks.CHEST, Blocks.TRAPPED_CHEST);
      this.blockEntityModels(ModelLocationUtils.decorateBlockModelLocation("ender_chest"), Blocks.OBSIDIAN).createWithoutBlockItem(Blocks.ENDER_CHEST);
      this.blockEntityModels(Blocks.END_PORTAL, Blocks.OBSIDIAN).create(Blocks.END_PORTAL, Blocks.END_GATEWAY);
      this.createTrivialCube(Blocks.AZALEA_LEAVES);
      this.createTrivialCube(Blocks.FLOWERING_AZALEA_LEAVES);
      this.createTrivialCube(Blocks.WHITE_CONCRETE);
      this.createTrivialCube(Blocks.ORANGE_CONCRETE);
      this.createTrivialCube(Blocks.MAGENTA_CONCRETE);
      this.createTrivialCube(Blocks.LIGHT_BLUE_CONCRETE);
      this.createTrivialCube(Blocks.YELLOW_CONCRETE);
      this.createTrivialCube(Blocks.LIME_CONCRETE);
      this.createTrivialCube(Blocks.PINK_CONCRETE);
      this.createTrivialCube(Blocks.GRAY_CONCRETE);
      this.createTrivialCube(Blocks.LIGHT_GRAY_CONCRETE);
      this.createTrivialCube(Blocks.CYAN_CONCRETE);
      this.createTrivialCube(Blocks.PURPLE_CONCRETE);
      this.createTrivialCube(Blocks.BLUE_CONCRETE);
      this.createTrivialCube(Blocks.BROWN_CONCRETE);
      this.createTrivialCube(Blocks.GREEN_CONCRETE);
      this.createTrivialCube(Blocks.RED_CONCRETE);
      this.createTrivialCube(Blocks.BLACK_CONCRETE);
      this.createColoredBlockWithRandomRotations(
         TexturedModel.CUBE,
         Blocks.WHITE_CONCRETE_POWDER,
         Blocks.ORANGE_CONCRETE_POWDER,
         Blocks.MAGENTA_CONCRETE_POWDER,
         Blocks.LIGHT_BLUE_CONCRETE_POWDER,
         Blocks.YELLOW_CONCRETE_POWDER,
         Blocks.LIME_CONCRETE_POWDER,
         Blocks.PINK_CONCRETE_POWDER,
         Blocks.GRAY_CONCRETE_POWDER,
         Blocks.LIGHT_GRAY_CONCRETE_POWDER,
         Blocks.CYAN_CONCRETE_POWDER,
         Blocks.PURPLE_CONCRETE_POWDER,
         Blocks.BLUE_CONCRETE_POWDER,
         Blocks.BROWN_CONCRETE_POWDER,
         Blocks.GREEN_CONCRETE_POWDER,
         Blocks.RED_CONCRETE_POWDER,
         Blocks.BLACK_CONCRETE_POWDER
      );
      this.createTrivialCube(Blocks.TERRACOTTA);
      this.createTrivialCube(Blocks.WHITE_TERRACOTTA);
      this.createTrivialCube(Blocks.ORANGE_TERRACOTTA);
      this.createTrivialCube(Blocks.MAGENTA_TERRACOTTA);
      this.createTrivialCube(Blocks.LIGHT_BLUE_TERRACOTTA);
      this.createTrivialCube(Blocks.YELLOW_TERRACOTTA);
      this.createTrivialCube(Blocks.LIME_TERRACOTTA);
      this.createTrivialCube(Blocks.PINK_TERRACOTTA);
      this.createTrivialCube(Blocks.GRAY_TERRACOTTA);
      this.createTrivialCube(Blocks.LIGHT_GRAY_TERRACOTTA);
      this.createTrivialCube(Blocks.CYAN_TERRACOTTA);
      this.createTrivialCube(Blocks.PURPLE_TERRACOTTA);
      this.createTrivialCube(Blocks.BLUE_TERRACOTTA);
      this.createTrivialCube(Blocks.BROWN_TERRACOTTA);
      this.createTrivialCube(Blocks.GREEN_TERRACOTTA);
      this.createTrivialCube(Blocks.RED_TERRACOTTA);
      this.createTrivialCube(Blocks.BLACK_TERRACOTTA);
      this.createTrivialCube(Blocks.TINTED_GLASS);
      this.createGlassBlocks(Blocks.GLASS, Blocks.GLASS_PANE);
      this.createGlassBlocks(Blocks.WHITE_STAINED_GLASS, Blocks.WHITE_STAINED_GLASS_PANE);
      this.createGlassBlocks(Blocks.ORANGE_STAINED_GLASS, Blocks.ORANGE_STAINED_GLASS_PANE);
      this.createGlassBlocks(Blocks.MAGENTA_STAINED_GLASS, Blocks.MAGENTA_STAINED_GLASS_PANE);
      this.createGlassBlocks(Blocks.LIGHT_BLUE_STAINED_GLASS, Blocks.LIGHT_BLUE_STAINED_GLASS_PANE);
      this.createGlassBlocks(Blocks.YELLOW_STAINED_GLASS, Blocks.YELLOW_STAINED_GLASS_PANE);
      this.createGlassBlocks(Blocks.LIME_STAINED_GLASS, Blocks.LIME_STAINED_GLASS_PANE);
      this.createGlassBlocks(Blocks.PINK_STAINED_GLASS, Blocks.PINK_STAINED_GLASS_PANE);
      this.createGlassBlocks(Blocks.GRAY_STAINED_GLASS, Blocks.GRAY_STAINED_GLASS_PANE);
      this.createGlassBlocks(Blocks.LIGHT_GRAY_STAINED_GLASS, Blocks.LIGHT_GRAY_STAINED_GLASS_PANE);
      this.createGlassBlocks(Blocks.CYAN_STAINED_GLASS, Blocks.CYAN_STAINED_GLASS_PANE);
      this.createGlassBlocks(Blocks.PURPLE_STAINED_GLASS, Blocks.PURPLE_STAINED_GLASS_PANE);
      this.createGlassBlocks(Blocks.BLUE_STAINED_GLASS, Blocks.BLUE_STAINED_GLASS_PANE);
      this.createGlassBlocks(Blocks.BROWN_STAINED_GLASS, Blocks.BROWN_STAINED_GLASS_PANE);
      this.createGlassBlocks(Blocks.GREEN_STAINED_GLASS, Blocks.GREEN_STAINED_GLASS_PANE);
      this.createGlassBlocks(Blocks.RED_STAINED_GLASS, Blocks.RED_STAINED_GLASS_PANE);
      this.createGlassBlocks(Blocks.BLACK_STAINED_GLASS, Blocks.BLACK_STAINED_GLASS_PANE);
      this.createColoredBlockWithStateRotations(
         TexturedModel.GLAZED_TERRACOTTA,
         Blocks.WHITE_GLAZED_TERRACOTTA,
         Blocks.ORANGE_GLAZED_TERRACOTTA,
         Blocks.MAGENTA_GLAZED_TERRACOTTA,
         Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA,
         Blocks.YELLOW_GLAZED_TERRACOTTA,
         Blocks.LIME_GLAZED_TERRACOTTA,
         Blocks.PINK_GLAZED_TERRACOTTA,
         Blocks.GRAY_GLAZED_TERRACOTTA,
         Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA,
         Blocks.CYAN_GLAZED_TERRACOTTA,
         Blocks.PURPLE_GLAZED_TERRACOTTA,
         Blocks.BLUE_GLAZED_TERRACOTTA,
         Blocks.BROWN_GLAZED_TERRACOTTA,
         Blocks.GREEN_GLAZED_TERRACOTTA,
         Blocks.RED_GLAZED_TERRACOTTA,
         Blocks.BLACK_GLAZED_TERRACOTTA
      );
      this.createFullAndCarpetBlocks(Blocks.WHITE_WOOL, Blocks.WHITE_CARPET);
      this.createFullAndCarpetBlocks(Blocks.ORANGE_WOOL, Blocks.ORANGE_CARPET);
      this.createFullAndCarpetBlocks(Blocks.MAGENTA_WOOL, Blocks.MAGENTA_CARPET);
      this.createFullAndCarpetBlocks(Blocks.LIGHT_BLUE_WOOL, Blocks.LIGHT_BLUE_CARPET);
      this.createFullAndCarpetBlocks(Blocks.YELLOW_WOOL, Blocks.YELLOW_CARPET);
      this.createFullAndCarpetBlocks(Blocks.LIME_WOOL, Blocks.LIME_CARPET);
      this.createFullAndCarpetBlocks(Blocks.PINK_WOOL, Blocks.PINK_CARPET);
      this.createFullAndCarpetBlocks(Blocks.GRAY_WOOL, Blocks.GRAY_CARPET);
      this.createFullAndCarpetBlocks(Blocks.LIGHT_GRAY_WOOL, Blocks.LIGHT_GRAY_CARPET);
      this.createFullAndCarpetBlocks(Blocks.CYAN_WOOL, Blocks.CYAN_CARPET);
      this.createFullAndCarpetBlocks(Blocks.PURPLE_WOOL, Blocks.PURPLE_CARPET);
      this.createFullAndCarpetBlocks(Blocks.BLUE_WOOL, Blocks.BLUE_CARPET);
      this.createFullAndCarpetBlocks(Blocks.BROWN_WOOL, Blocks.BROWN_CARPET);
      this.createFullAndCarpetBlocks(Blocks.GREEN_WOOL, Blocks.GREEN_CARPET);
      this.createFullAndCarpetBlocks(Blocks.RED_WOOL, Blocks.RED_CARPET);
      this.createFullAndCarpetBlocks(Blocks.BLACK_WOOL, Blocks.BLACK_CARPET);
      this.createPlant(Blocks.FERN, Blocks.POTTED_FERN, BlockModelGenerators.TintState.TINTED);
      this.createPlant(Blocks.DANDELION, Blocks.POTTED_DANDELION, BlockModelGenerators.TintState.NOT_TINTED);
      this.createPlant(Blocks.POPPY, Blocks.POTTED_POPPY, BlockModelGenerators.TintState.NOT_TINTED);
      this.createPlant(Blocks.BLUE_ORCHID, Blocks.POTTED_BLUE_ORCHID, BlockModelGenerators.TintState.NOT_TINTED);
      this.createPlant(Blocks.ALLIUM, Blocks.POTTED_ALLIUM, BlockModelGenerators.TintState.NOT_TINTED);
      this.createPlant(Blocks.AZURE_BLUET, Blocks.POTTED_AZURE_BLUET, BlockModelGenerators.TintState.NOT_TINTED);
      this.createPlant(Blocks.RED_TULIP, Blocks.POTTED_RED_TULIP, BlockModelGenerators.TintState.NOT_TINTED);
      this.createPlant(Blocks.ORANGE_TULIP, Blocks.POTTED_ORANGE_TULIP, BlockModelGenerators.TintState.NOT_TINTED);
      this.createPlant(Blocks.WHITE_TULIP, Blocks.POTTED_WHITE_TULIP, BlockModelGenerators.TintState.NOT_TINTED);
      this.createPlant(Blocks.PINK_TULIP, Blocks.POTTED_PINK_TULIP, BlockModelGenerators.TintState.NOT_TINTED);
      this.createPlant(Blocks.OXEYE_DAISY, Blocks.POTTED_OXEYE_DAISY, BlockModelGenerators.TintState.NOT_TINTED);
      this.createPlant(Blocks.CORNFLOWER, Blocks.POTTED_CORNFLOWER, BlockModelGenerators.TintState.NOT_TINTED);
      this.createPlant(Blocks.LILY_OF_THE_VALLEY, Blocks.POTTED_LILY_OF_THE_VALLEY, BlockModelGenerators.TintState.NOT_TINTED);
      this.createPlant(Blocks.WITHER_ROSE, Blocks.POTTED_WITHER_ROSE, BlockModelGenerators.TintState.NOT_TINTED);
      this.createPlant(Blocks.RED_MUSHROOM, Blocks.POTTED_RED_MUSHROOM, BlockModelGenerators.TintState.NOT_TINTED);
      this.createPlant(Blocks.BROWN_MUSHROOM, Blocks.POTTED_BROWN_MUSHROOM, BlockModelGenerators.TintState.NOT_TINTED);
      this.createPlant(Blocks.DEAD_BUSH, Blocks.POTTED_DEAD_BUSH, BlockModelGenerators.TintState.NOT_TINTED);
      this.createPointedDripstone();
      this.createMushroomBlock(Blocks.BROWN_MUSHROOM_BLOCK);
      this.createMushroomBlock(Blocks.RED_MUSHROOM_BLOCK);
      this.createMushroomBlock(Blocks.MUSHROOM_STEM);
      this.createCrossBlockWithDefaultItem(Blocks.GRASS, BlockModelGenerators.TintState.TINTED);
      this.createCrossBlock(Blocks.SUGAR_CANE, BlockModelGenerators.TintState.TINTED);
      this.createSimpleFlatItemModel(Items.SUGAR_CANE);
      this.createGrowingPlant(Blocks.KELP, Blocks.KELP_PLANT, BlockModelGenerators.TintState.TINTED);
      this.createSimpleFlatItemModel(Items.KELP);
      this.skipAutoItemBlock(Blocks.KELP_PLANT);
      this.createCrossBlock(Blocks.HANGING_ROOTS, BlockModelGenerators.TintState.NOT_TINTED);
      this.skipAutoItemBlock(Blocks.HANGING_ROOTS);
      this.skipAutoItemBlock(Blocks.CAVE_VINES_PLANT);
      this.createGrowingPlant(Blocks.WEEPING_VINES, Blocks.WEEPING_VINES_PLANT, BlockModelGenerators.TintState.NOT_TINTED);
      this.createGrowingPlant(Blocks.TWISTING_VINES, Blocks.TWISTING_VINES_PLANT, BlockModelGenerators.TintState.NOT_TINTED);
      this.createSimpleFlatItemModel(Blocks.WEEPING_VINES, "_plant");
      this.skipAutoItemBlock(Blocks.WEEPING_VINES_PLANT);
      this.createSimpleFlatItemModel(Blocks.TWISTING_VINES, "_plant");
      this.skipAutoItemBlock(Blocks.TWISTING_VINES_PLANT);
      this.createCrossBlockWithDefaultItem(
         Blocks.BAMBOO_SAPLING, BlockModelGenerators.TintState.TINTED, TextureMapping.cross(TextureMapping.getBlockTexture(Blocks.BAMBOO, "_stage0"))
      );
      this.createBamboo();
      this.createCrossBlockWithDefaultItem(Blocks.COBWEB, BlockModelGenerators.TintState.NOT_TINTED);
      this.createDoublePlant(Blocks.LILAC, BlockModelGenerators.TintState.NOT_TINTED);
      this.createDoublePlant(Blocks.ROSE_BUSH, BlockModelGenerators.TintState.NOT_TINTED);
      this.createDoublePlant(Blocks.PEONY, BlockModelGenerators.TintState.NOT_TINTED);
      this.createDoublePlant(Blocks.TALL_GRASS, BlockModelGenerators.TintState.TINTED);
      this.createDoublePlant(Blocks.LARGE_FERN, BlockModelGenerators.TintState.TINTED);
      this.createSunflower();
      this.createTallSeagrass();
      this.createSmallDripleaf();
      this.createCoral(
         Blocks.TUBE_CORAL,
         Blocks.DEAD_TUBE_CORAL,
         Blocks.TUBE_CORAL_BLOCK,
         Blocks.DEAD_TUBE_CORAL_BLOCK,
         Blocks.TUBE_CORAL_FAN,
         Blocks.DEAD_TUBE_CORAL_FAN,
         Blocks.TUBE_CORAL_WALL_FAN,
         Blocks.DEAD_TUBE_CORAL_WALL_FAN
      );
      this.createCoral(
         Blocks.BRAIN_CORAL,
         Blocks.DEAD_BRAIN_CORAL,
         Blocks.BRAIN_CORAL_BLOCK,
         Blocks.DEAD_BRAIN_CORAL_BLOCK,
         Blocks.BRAIN_CORAL_FAN,
         Blocks.DEAD_BRAIN_CORAL_FAN,
         Blocks.BRAIN_CORAL_WALL_FAN,
         Blocks.DEAD_BRAIN_CORAL_WALL_FAN
      );
      this.createCoral(
         Blocks.BUBBLE_CORAL,
         Blocks.DEAD_BUBBLE_CORAL,
         Blocks.BUBBLE_CORAL_BLOCK,
         Blocks.DEAD_BUBBLE_CORAL_BLOCK,
         Blocks.BUBBLE_CORAL_FAN,
         Blocks.DEAD_BUBBLE_CORAL_FAN,
         Blocks.BUBBLE_CORAL_WALL_FAN,
         Blocks.DEAD_BUBBLE_CORAL_WALL_FAN
      );
      this.createCoral(
         Blocks.FIRE_CORAL,
         Blocks.DEAD_FIRE_CORAL,
         Blocks.FIRE_CORAL_BLOCK,
         Blocks.DEAD_FIRE_CORAL_BLOCK,
         Blocks.FIRE_CORAL_FAN,
         Blocks.DEAD_FIRE_CORAL_FAN,
         Blocks.FIRE_CORAL_WALL_FAN,
         Blocks.DEAD_FIRE_CORAL_WALL_FAN
      );
      this.createCoral(
         Blocks.HORN_CORAL,
         Blocks.DEAD_HORN_CORAL,
         Blocks.HORN_CORAL_BLOCK,
         Blocks.DEAD_HORN_CORAL_BLOCK,
         Blocks.HORN_CORAL_FAN,
         Blocks.DEAD_HORN_CORAL_FAN,
         Blocks.HORN_CORAL_WALL_FAN,
         Blocks.DEAD_HORN_CORAL_WALL_FAN
      );
      this.createStems(Blocks.MELON_STEM, Blocks.ATTACHED_MELON_STEM);
      this.createStems(Blocks.PUMPKIN_STEM, Blocks.ATTACHED_PUMPKIN_STEM);
      this.woodProvider(Blocks.ACACIA_LOG).logWithHorizontal(Blocks.ACACIA_LOG).wood(Blocks.ACACIA_WOOD);
      this.woodProvider(Blocks.STRIPPED_ACACIA_LOG).logWithHorizontal(Blocks.STRIPPED_ACACIA_LOG).wood(Blocks.STRIPPED_ACACIA_WOOD);
      this.createPlant(Blocks.ACACIA_SAPLING, Blocks.POTTED_ACACIA_SAPLING, BlockModelGenerators.TintState.NOT_TINTED);
      this.createTrivialBlock(Blocks.ACACIA_LEAVES, TexturedModel.LEAVES);
      this.woodProvider(Blocks.BIRCH_LOG).logWithHorizontal(Blocks.BIRCH_LOG).wood(Blocks.BIRCH_WOOD);
      this.woodProvider(Blocks.STRIPPED_BIRCH_LOG).logWithHorizontal(Blocks.STRIPPED_BIRCH_LOG).wood(Blocks.STRIPPED_BIRCH_WOOD);
      this.createPlant(Blocks.BIRCH_SAPLING, Blocks.POTTED_BIRCH_SAPLING, BlockModelGenerators.TintState.NOT_TINTED);
      this.createTrivialBlock(Blocks.BIRCH_LEAVES, TexturedModel.LEAVES);
      this.woodProvider(Blocks.OAK_LOG).logWithHorizontal(Blocks.OAK_LOG).wood(Blocks.OAK_WOOD);
      this.woodProvider(Blocks.STRIPPED_OAK_LOG).logWithHorizontal(Blocks.STRIPPED_OAK_LOG).wood(Blocks.STRIPPED_OAK_WOOD);
      this.createPlant(Blocks.OAK_SAPLING, Blocks.POTTED_OAK_SAPLING, BlockModelGenerators.TintState.NOT_TINTED);
      this.createTrivialBlock(Blocks.OAK_LEAVES, TexturedModel.LEAVES);
      this.woodProvider(Blocks.SPRUCE_LOG).logWithHorizontal(Blocks.SPRUCE_LOG).wood(Blocks.SPRUCE_WOOD);
      this.woodProvider(Blocks.STRIPPED_SPRUCE_LOG).logWithHorizontal(Blocks.STRIPPED_SPRUCE_LOG).wood(Blocks.STRIPPED_SPRUCE_WOOD);
      this.createPlant(Blocks.SPRUCE_SAPLING, Blocks.POTTED_SPRUCE_SAPLING, BlockModelGenerators.TintState.NOT_TINTED);
      this.createTrivialBlock(Blocks.SPRUCE_LEAVES, TexturedModel.LEAVES);
      this.woodProvider(Blocks.DARK_OAK_LOG).logWithHorizontal(Blocks.DARK_OAK_LOG).wood(Blocks.DARK_OAK_WOOD);
      this.woodProvider(Blocks.STRIPPED_DARK_OAK_LOG).logWithHorizontal(Blocks.STRIPPED_DARK_OAK_LOG).wood(Blocks.STRIPPED_DARK_OAK_WOOD);
      this.createPlant(Blocks.DARK_OAK_SAPLING, Blocks.POTTED_DARK_OAK_SAPLING, BlockModelGenerators.TintState.NOT_TINTED);
      this.createTrivialBlock(Blocks.DARK_OAK_LEAVES, TexturedModel.LEAVES);
      this.woodProvider(Blocks.JUNGLE_LOG).logWithHorizontal(Blocks.JUNGLE_LOG).wood(Blocks.JUNGLE_WOOD);
      this.woodProvider(Blocks.STRIPPED_JUNGLE_LOG).logWithHorizontal(Blocks.STRIPPED_JUNGLE_LOG).wood(Blocks.STRIPPED_JUNGLE_WOOD);
      this.createPlant(Blocks.JUNGLE_SAPLING, Blocks.POTTED_JUNGLE_SAPLING, BlockModelGenerators.TintState.NOT_TINTED);
      this.createTrivialBlock(Blocks.JUNGLE_LEAVES, TexturedModel.LEAVES);
      this.woodProvider(Blocks.CRIMSON_STEM).log(Blocks.CRIMSON_STEM).wood(Blocks.CRIMSON_HYPHAE);
      this.woodProvider(Blocks.STRIPPED_CRIMSON_STEM).log(Blocks.STRIPPED_CRIMSON_STEM).wood(Blocks.STRIPPED_CRIMSON_HYPHAE);
      this.createPlant(Blocks.CRIMSON_FUNGUS, Blocks.POTTED_CRIMSON_FUNGUS, BlockModelGenerators.TintState.NOT_TINTED);
      this.createNetherRoots(Blocks.CRIMSON_ROOTS, Blocks.POTTED_CRIMSON_ROOTS);
      this.woodProvider(Blocks.WARPED_STEM).log(Blocks.WARPED_STEM).wood(Blocks.WARPED_HYPHAE);
      this.woodProvider(Blocks.STRIPPED_WARPED_STEM).log(Blocks.STRIPPED_WARPED_STEM).wood(Blocks.STRIPPED_WARPED_HYPHAE);
      this.createPlant(Blocks.WARPED_FUNGUS, Blocks.POTTED_WARPED_FUNGUS, BlockModelGenerators.TintState.NOT_TINTED);
      this.createNetherRoots(Blocks.WARPED_ROOTS, Blocks.POTTED_WARPED_ROOTS);
      this.createCrossBlock(Blocks.NETHER_SPROUTS, BlockModelGenerators.TintState.NOT_TINTED);
      this.createSimpleFlatItemModel(Items.NETHER_SPROUTS);
      this.createDoor(Blocks.IRON_DOOR);
      this.createTrapdoor(Blocks.IRON_TRAPDOOR);
      this.createSmoothStoneSlab();
      this.createPassiveRail(Blocks.RAIL);
      this.createActiveRail(Blocks.POWERED_RAIL);
      this.createActiveRail(Blocks.DETECTOR_RAIL);
      this.createActiveRail(Blocks.ACTIVATOR_RAIL);
      this.createComparator();
      this.createCommandBlock(Blocks.COMMAND_BLOCK);
      this.createCommandBlock(Blocks.REPEATING_COMMAND_BLOCK);
      this.createCommandBlock(Blocks.CHAIN_COMMAND_BLOCK);
      this.createAnvil(Blocks.ANVIL);
      this.createAnvil(Blocks.CHIPPED_ANVIL);
      this.createAnvil(Blocks.DAMAGED_ANVIL);
      this.createBarrel();
      this.createBell();
      this.createFurnace(Blocks.FURNACE, TexturedModel.ORIENTABLE_ONLY_TOP);
      this.createFurnace(Blocks.BLAST_FURNACE, TexturedModel.ORIENTABLE_ONLY_TOP);
      this.createFurnace(Blocks.SMOKER, TexturedModel.ORIENTABLE);
      this.createRedstoneWire();
      this.createRespawnAnchor();
      this.copyModel(Blocks.CHISELED_STONE_BRICKS, Blocks.INFESTED_CHISELED_STONE_BRICKS);
      this.copyModel(Blocks.COBBLESTONE, Blocks.INFESTED_COBBLESTONE);
      this.copyModel(Blocks.CRACKED_STONE_BRICKS, Blocks.INFESTED_CRACKED_STONE_BRICKS);
      this.copyModel(Blocks.MOSSY_STONE_BRICKS, Blocks.INFESTED_MOSSY_STONE_BRICKS);
      this.createInfestedStone();
      this.copyModel(Blocks.STONE_BRICKS, Blocks.INFESTED_STONE_BRICKS);
      this.createInfestedDeepslate();
      SpawnEggItem.eggs().forEach(var1 -> this.delegateItemModel(var1, ModelLocationUtils.decorateItemModelLocation("template_spawn_egg")));
   }

   private void createLightBlockItems() {
      this.skipAutoItemBlock(Blocks.LIGHT);

      for(int â˜ƒ = 0; â˜ƒ < 16; ++â˜ƒ) {
         String â˜ƒx = String.format("_%02d", â˜ƒ);
         ModelTemplates.FLAT_ITEM
            .create(
               ModelLocationUtils.getModelLocation(Items.LIGHT, â˜ƒx),
               TextureMapping.layer0(TextureMapping.getItemTexture(Items.LIGHT, â˜ƒx)),
               this.modelOutput
            );
      }
   }

   private void createCandleAndCandleCake(Block var1, Block var2) {
      this.createSimpleFlatItemModel(â˜ƒ.asItem());
      TextureMapping â˜ƒ = TextureMapping.cube(TextureMapping.getBlockTexture(â˜ƒ));
      TextureMapping â˜ƒx = TextureMapping.cube(TextureMapping.getBlockTexture(â˜ƒ, "_lit"));
      ResourceLocation â˜ƒxx = ModelTemplates.CANDLE.createWithSuffix(â˜ƒ, "_one_candle", â˜ƒ, this.modelOutput);
      ResourceLocation â˜ƒxxx = ModelTemplates.TWO_CANDLES.createWithSuffix(â˜ƒ, "_two_candles", â˜ƒ, this.modelOutput);
      ResourceLocation â˜ƒxxxx = ModelTemplates.THREE_CANDLES.createWithSuffix(â˜ƒ, "_three_candles", â˜ƒ, this.modelOutput);
      ResourceLocation â˜ƒxxxxx = ModelTemplates.FOUR_CANDLES.createWithSuffix(â˜ƒ, "_four_candles", â˜ƒ, this.modelOutput);
      ResourceLocation â˜ƒxxxxxx = ModelTemplates.CANDLE.createWithSuffix(â˜ƒ, "_one_candle_lit", â˜ƒx, this.modelOutput);
      ResourceLocation â˜ƒxxxxxxx = ModelTemplates.TWO_CANDLES.createWithSuffix(â˜ƒ, "_two_candles_lit", â˜ƒx, this.modelOutput);
      ResourceLocation â˜ƒxxxxxxxx = ModelTemplates.THREE_CANDLES.createWithSuffix(â˜ƒ, "_three_candles_lit", â˜ƒx, this.modelOutput);
      ResourceLocation â˜ƒxxxxxxxxx = ModelTemplates.FOUR_CANDLES.createWithSuffix(â˜ƒ, "_four_candles_lit", â˜ƒx, this.modelOutput);
      this.blockStateOutput
         .accept(
            MultiVariantGenerator.multiVariant(â˜ƒ)
               .with(
                  PropertyDispatch.properties(BlockStateProperties.CANDLES, BlockStateProperties.LIT)
                     .select(1, false, Variant.variant().with(VariantProperties.MODEL, â˜ƒxx))
                     .select(2, false, Variant.variant().with(VariantProperties.MODEL, â˜ƒxxx))
                     .select(3, false, Variant.variant().with(VariantProperties.MODEL, â˜ƒxxxx))
                     .select(4, false, Variant.variant().with(VariantProperties.MODEL, â˜ƒxxxxx))
                     .select(1, true, Variant.variant().with(VariantProperties.MODEL, â˜ƒxxxxxx))
                     .select(2, true, Variant.variant().with(VariantProperties.MODEL, â˜ƒxxxxxxx))
                     .select(3, true, Variant.variant().with(VariantProperties.MODEL, â˜ƒxxxxxxxx))
                     .select(4, true, Variant.variant().with(VariantProperties.MODEL, â˜ƒxxxxxxxxx))
               )
         );
      ResourceLocation â˜ƒxxxxxxxxxx = ModelTemplates.CANDLE_CAKE.create(â˜ƒ, TextureMapping.candleCake(â˜ƒ, false), this.modelOutput);
      ResourceLocation â˜ƒxxxxxxxxxxx = ModelTemplates.CANDLE_CAKE.createWithSuffix(â˜ƒ, "_lit", TextureMapping.candleCake(â˜ƒ, true), this.modelOutput);
      this.blockStateOutput
         .accept(MultiVariantGenerator.multiVariant(â˜ƒ).with(createBooleanModelDispatch(BlockStateProperties.LIT, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxx)));
   }

   class BlockEntityModelGenerator {
      private final ResourceLocation baseModel;

      public BlockEntityModelGenerator(ResourceLocation var2, Block var3) {
         this.baseModel = ModelTemplates.PARTICLE_ONLY.create(â˜ƒ, TextureMapping.particle(â˜ƒ), BlockModelGenerators.this.modelOutput);
      }

      public BlockModelGenerators.BlockEntityModelGenerator create(Block... var1) {
         for(Block â˜ƒ : â˜ƒ) {
            BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(â˜ƒ, this.baseModel));
         }

         return this;
      }

      public BlockModelGenerators.BlockEntityModelGenerator createWithoutBlockItem(Block... var1) {
         for(Block â˜ƒ : â˜ƒ) {
            BlockModelGenerators.this.skipAutoItemBlock(â˜ƒ);
         }

         return this.create(â˜ƒ);
      }

      public BlockModelGenerators.BlockEntityModelGenerator createWithCustomBlockItemModel(ModelTemplate var1, Block... var2) {
         for(Block â˜ƒ : â˜ƒ) {
            â˜ƒ.create(ModelLocationUtils.getModelLocation(â˜ƒ.asItem()), TextureMapping.particle(â˜ƒ), BlockModelGenerators.this.modelOutput);
         }

         return this.create(â˜ƒ);
      }
   }

   class BlockFamilyProvider {
      private final TextureMapping mapping;
      private final Map<ModelTemplate, ResourceLocation> models = Maps.<ModelTemplate, ResourceLocation>newHashMap();
      @Nullable
      private BlockFamily family;
      @Nullable
      private ResourceLocation fullBlock;

      public BlockFamilyProvider(TextureMapping var2) {
         this.mapping = â˜ƒ;
      }

      public BlockModelGenerators.BlockFamilyProvider fullBlock(Block var1, ModelTemplate var2) {
         this.fullBlock = â˜ƒ.create(â˜ƒ, this.mapping, BlockModelGenerators.this.modelOutput);
         if (BlockModelGenerators.this.fullBlockModelCustomGenerators.containsKey(â˜ƒ)) {
            BlockModelGenerators.this.blockStateOutput
               .accept(
                  ((BlockModelGenerators.BlockStateGeneratorSupplier)BlockModelGenerators.this.fullBlockModelCustomGenerators.get(â˜ƒ))
                     .create(â˜ƒ, this.fullBlock, this.mapping, BlockModelGenerators.this.modelOutput)
               );
         } else {
            BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(â˜ƒ, this.fullBlock));
         }

         return this;
      }

      public BlockModelGenerators.BlockFamilyProvider fullBlockCopies(Block... var1) {
         if (this.fullBlock == null) {
            throw new IllegalStateException("Full block not generated yet");
         } else {
            for(Block â˜ƒ : â˜ƒ) {
               BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(â˜ƒ, this.fullBlock));
               BlockModelGenerators.this.delegateItemModel(â˜ƒ, this.fullBlock);
            }

            return this;
         }
      }

      public BlockModelGenerators.BlockFamilyProvider button(Block var1) {
         ResourceLocation â˜ƒ = ModelTemplates.BUTTON.create(â˜ƒ, this.mapping, BlockModelGenerators.this.modelOutput);
         ResourceLocation â˜ƒx = ModelTemplates.BUTTON_PRESSED.create(â˜ƒ, this.mapping, BlockModelGenerators.this.modelOutput);
         BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createButton(â˜ƒ, â˜ƒ, â˜ƒx));
         ResourceLocation â˜ƒxx = ModelTemplates.BUTTON_INVENTORY.create(â˜ƒ, this.mapping, BlockModelGenerators.this.modelOutput);
         BlockModelGenerators.this.delegateItemModel(â˜ƒ, â˜ƒxx);
         return this;
      }

      public BlockModelGenerators.BlockFamilyProvider wall(Block var1) {
         ResourceLocation â˜ƒ = ModelTemplates.WALL_POST.create(â˜ƒ, this.mapping, BlockModelGenerators.this.modelOutput);
         ResourceLocation â˜ƒx = ModelTemplates.WALL_LOW_SIDE.create(â˜ƒ, this.mapping, BlockModelGenerators.this.modelOutput);
         ResourceLocation â˜ƒxx = ModelTemplates.WALL_TALL_SIDE.create(â˜ƒ, this.mapping, BlockModelGenerators.this.modelOutput);
         BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createWall(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx));
         ResourceLocation â˜ƒxxx = ModelTemplates.WALL_INVENTORY.create(â˜ƒ, this.mapping, BlockModelGenerators.this.modelOutput);
         BlockModelGenerators.this.delegateItemModel(â˜ƒ, â˜ƒxxx);
         return this;
      }

      public BlockModelGenerators.BlockFamilyProvider fence(Block var1) {
         ResourceLocation â˜ƒ = ModelTemplates.FENCE_POST.create(â˜ƒ, this.mapping, BlockModelGenerators.this.modelOutput);
         ResourceLocation â˜ƒx = ModelTemplates.FENCE_SIDE.create(â˜ƒ, this.mapping, BlockModelGenerators.this.modelOutput);
         BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createFence(â˜ƒ, â˜ƒ, â˜ƒx));
         ResourceLocation â˜ƒxx = ModelTemplates.FENCE_INVENTORY.create(â˜ƒ, this.mapping, BlockModelGenerators.this.modelOutput);
         BlockModelGenerators.this.delegateItemModel(â˜ƒ, â˜ƒxx);
         return this;
      }

      public BlockModelGenerators.BlockFamilyProvider fenceGate(Block var1) {
         ResourceLocation â˜ƒ = ModelTemplates.FENCE_GATE_OPEN.create(â˜ƒ, this.mapping, BlockModelGenerators.this.modelOutput);
         ResourceLocation â˜ƒx = ModelTemplates.FENCE_GATE_CLOSED.create(â˜ƒ, this.mapping, BlockModelGenerators.this.modelOutput);
         ResourceLocation â˜ƒxx = ModelTemplates.FENCE_GATE_WALL_OPEN.create(â˜ƒ, this.mapping, BlockModelGenerators.this.modelOutput);
         ResourceLocation â˜ƒxxx = ModelTemplates.FENCE_GATE_WALL_CLOSED.create(â˜ƒ, this.mapping, BlockModelGenerators.this.modelOutput);
         BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createFenceGate(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx));
         return this;
      }

      public BlockModelGenerators.BlockFamilyProvider pressurePlate(Block var1) {
         ResourceLocation â˜ƒ = ModelTemplates.PRESSURE_PLATE_UP.create(â˜ƒ, this.mapping, BlockModelGenerators.this.modelOutput);
         ResourceLocation â˜ƒx = ModelTemplates.PRESSURE_PLATE_DOWN.create(â˜ƒ, this.mapping, BlockModelGenerators.this.modelOutput);
         BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createPressurePlate(â˜ƒ, â˜ƒ, â˜ƒx));
         return this;
      }

      public BlockModelGenerators.BlockFamilyProvider sign(Block var1) {
         if (this.family == null) {
            throw new IllegalStateException("Family not defined");
         } else {
            Block â˜ƒ = (Block)this.family.getVariants().get(BlockFamily.Variant.WALL_SIGN);
            ResourceLocation â˜ƒx = ModelTemplates.PARTICLE_ONLY.create(â˜ƒ, this.mapping, BlockModelGenerators.this.modelOutput);
            BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(â˜ƒ, â˜ƒx));
            BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(â˜ƒ, â˜ƒx));
            BlockModelGenerators.this.createSimpleFlatItemModel(â˜ƒ.asItem());
            BlockModelGenerators.this.skipAutoItemBlock(â˜ƒ);
            return this;
         }
      }

      public BlockModelGenerators.BlockFamilyProvider slab(Block var1) {
         if (this.fullBlock == null) {
            throw new IllegalStateException("Full block not generated yet");
         } else {
            ResourceLocation â˜ƒ = this.getOrCreateModel(ModelTemplates.SLAB_BOTTOM, â˜ƒ);
            ResourceLocation â˜ƒx = this.getOrCreateModel(ModelTemplates.SLAB_TOP, â˜ƒ);
            BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSlab(â˜ƒ, â˜ƒ, â˜ƒx, this.fullBlock));
            BlockModelGenerators.this.delegateItemModel(â˜ƒ, â˜ƒ);
            return this;
         }
      }

      public BlockModelGenerators.BlockFamilyProvider stairs(Block var1) {
         ResourceLocation â˜ƒ = this.getOrCreateModel(ModelTemplates.STAIRS_INNER, â˜ƒ);
         ResourceLocation â˜ƒx = this.getOrCreateModel(ModelTemplates.STAIRS_STRAIGHT, â˜ƒ);
         ResourceLocation â˜ƒxx = this.getOrCreateModel(ModelTemplates.STAIRS_OUTER, â˜ƒ);
         BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createStairs(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx));
         BlockModelGenerators.this.delegateItemModel(â˜ƒ, â˜ƒx);
         return this;
      }

      private BlockModelGenerators.BlockFamilyProvider fullBlockVariant(Block var1) {
         TexturedModel â˜ƒ = (TexturedModel)BlockModelGenerators.this.texturedModels.getOrDefault(â˜ƒ, TexturedModel.CUBE.get(â˜ƒ));
         BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(â˜ƒ, â˜ƒ.create(â˜ƒ, BlockModelGenerators.this.modelOutput)));
         return this;
      }

      private BlockModelGenerators.BlockFamilyProvider door(Block var1) {
         BlockModelGenerators.this.createDoor(â˜ƒ);
         return this;
      }

      private void trapdoor(Block var1) {
         if (BlockModelGenerators.this.nonOrientableTrapdoor.contains(â˜ƒ)) {
            BlockModelGenerators.this.createTrapdoor(â˜ƒ);
         } else {
            BlockModelGenerators.this.createOrientableTrapdoor(â˜ƒ);
         }
      }

      private ResourceLocation getOrCreateModel(ModelTemplate var1, Block var2) {
         return (ResourceLocation)this.models.computeIfAbsent(â˜ƒ, var2x -> var2x.create(â˜ƒ, this.mapping, BlockModelGenerators.this.modelOutput));
      }

      public BlockModelGenerators.BlockFamilyProvider generateFor(BlockFamily var1) {
         this.family = â˜ƒ;
         â˜ƒ.getVariants().forEach((var1x, var2) -> {
            BiConsumer<BlockModelGenerators.BlockFamilyProvider, Block> â˜ƒ = (BiConsumer)BlockModelGenerators.SHAPE_CONSUMERS.get(var1x);
            if (â˜ƒ != null) {
               â˜ƒ.accept(this, var2);
            }
         });
         return this;
      }
   }

   @FunctionalInterface
   interface BlockStateGeneratorSupplier {
      BlockStateGenerator create(Block var1, ResourceLocation var2, TextureMapping var3, BiConsumer<ResourceLocation, Supplier<JsonElement>> var4);
   }

   static enum TintState {
      TINTED,
      NOT_TINTED;

      public ModelTemplate getCross() {
         return this == TINTED ? ModelTemplates.TINTED_CROSS : ModelTemplates.CROSS;
      }

      public ModelTemplate getCrossPot() {
         return this == TINTED ? ModelTemplates.TINTED_FLOWER_POT_CROSS : ModelTemplates.FLOWER_POT_CROSS;
      }
   }

   class WoodProvider {
      private final TextureMapping logMapping;

      public WoodProvider(TextureMapping var2) {
         this.logMapping = â˜ƒ;
      }

      public BlockModelGenerators.WoodProvider wood(Block var1) {
         TextureMapping â˜ƒ = this.logMapping.copyAndUpdate(TextureSlot.END, this.logMapping.get(TextureSlot.SIDE));
         ResourceLocation â˜ƒx = ModelTemplates.CUBE_COLUMN.create(â˜ƒ, â˜ƒ, BlockModelGenerators.this.modelOutput);
         BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createAxisAlignedPillarBlock(â˜ƒ, â˜ƒx));
         return this;
      }

      public BlockModelGenerators.WoodProvider log(Block var1) {
         ResourceLocation â˜ƒ = ModelTemplates.CUBE_COLUMN.create(â˜ƒ, this.logMapping, BlockModelGenerators.this.modelOutput);
         BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createAxisAlignedPillarBlock(â˜ƒ, â˜ƒ));
         return this;
      }

      public BlockModelGenerators.WoodProvider logWithHorizontal(Block var1) {
         ResourceLocation â˜ƒ = ModelTemplates.CUBE_COLUMN.create(â˜ƒ, this.logMapping, BlockModelGenerators.this.modelOutput);
         ResourceLocation â˜ƒx = ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(â˜ƒ, this.logMapping, BlockModelGenerators.this.modelOutput);
         BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createRotatedPillarWithHorizontalVariant(â˜ƒ, â˜ƒ, â˜ƒx));
         return this;
      }
   }
}
