package net.minecraft.world.level.levelgen.structure;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.RepeaterBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TripWireBlock;
import net.minecraft.world.level.block.TripWireHookBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class JunglePyramidPiece extends ScatteredFeaturePiece {
   private boolean placedMainChest;
   private boolean placedHiddenChest;
   private boolean placedTrap1;
   private boolean placedTrap2;
   private static final JunglePyramidPiece.MossStoneSelector STONE_SELECTOR = new JunglePyramidPiece.MossStoneSelector();

   public JunglePyramidPiece(Random var1, int var2, int var3) {
      super(StructurePieceType.JUNGLE_PYRAMID_PIECE, â˜ƒ, 64, â˜ƒ, 12, 10, 15, getRandomHorizontalDirection(â˜ƒ));
   }

   public JunglePyramidPiece(ServerLevel var1, CompoundTag var2) {
      super(StructurePieceType.JUNGLE_PYRAMID_PIECE, â˜ƒ);
      this.placedMainChest = â˜ƒ.getBoolean("placedMainChest");
      this.placedHiddenChest = â˜ƒ.getBoolean("placedHiddenChest");
      this.placedTrap1 = â˜ƒ.getBoolean("placedTrap1");
      this.placedTrap2 = â˜ƒ.getBoolean("placedTrap2");
   }

   @Override
   protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
      super.addAdditionalSaveData(â˜ƒ, â˜ƒ);
      â˜ƒ.putBoolean("placedMainChest", this.placedMainChest);
      â˜ƒ.putBoolean("placedHiddenChest", this.placedHiddenChest);
      â˜ƒ.putBoolean("placedTrap1", this.placedTrap1);
      â˜ƒ.putBoolean("placedTrap2", this.placedTrap2);
   }

   @Override
   public boolean postProcess(
      WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
   ) {
      if (!this.updateAverageGroundHeight(â˜ƒ, â˜ƒ, 0)) {
         return false;
      } else {
         this.generateBox(â˜ƒ, â˜ƒ, 0, -4, 0, this.width - 1, 0, this.depth - 1, false, â˜ƒ, STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 1, 2, 9, 2, 2, false, â˜ƒ, STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 1, 12, 9, 2, 12, false, â˜ƒ, STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 1, 3, 2, 2, 11, false, â˜ƒ, STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 9, 1, 3, 9, 2, 11, false, â˜ƒ, STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 3, 1, 10, 6, 1, false, â˜ƒ, STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 3, 13, 10, 6, 13, false, â˜ƒ, STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 3, 2, 1, 6, 12, false, â˜ƒ, STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 10, 3, 2, 10, 6, 12, false, â˜ƒ, STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 3, 2, 9, 3, 12, false, â˜ƒ, STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 6, 2, 9, 6, 12, false, â˜ƒ, STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 3, 7, 3, 8, 7, 11, false, â˜ƒ, STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 8, 4, 7, 8, 10, false, â˜ƒ, STONE_SELECTOR);
         this.generateAirBox(â˜ƒ, â˜ƒ, 3, 1, 3, 8, 2, 11);
         this.generateAirBox(â˜ƒ, â˜ƒ, 4, 3, 6, 7, 3, 9);
         this.generateAirBox(â˜ƒ, â˜ƒ, 2, 4, 2, 9, 5, 12);
         this.generateAirBox(â˜ƒ, â˜ƒ, 4, 6, 5, 7, 6, 9);
         this.generateAirBox(â˜ƒ, â˜ƒ, 5, 7, 6, 6, 7, 8);
         this.generateAirBox(â˜ƒ, â˜ƒ, 5, 1, 2, 6, 2, 2);
         this.generateAirBox(â˜ƒ, â˜ƒ, 5, 2, 12, 6, 2, 12);
         this.generateAirBox(â˜ƒ, â˜ƒ, 5, 5, 1, 6, 5, 1);
         this.generateAirBox(â˜ƒ, â˜ƒ, 5, 5, 13, 6, 5, 13);
         this.placeBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 1, 5, 5, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 10, 5, 5, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 1, 5, 9, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 10, 5, 9, â˜ƒ);

         for(int â˜ƒ = 0; â˜ƒ <= 14; â˜ƒ += 14) {
            this.generateBox(â˜ƒ, â˜ƒ, 2, 4, â˜ƒ, 2, 5, â˜ƒ, false, â˜ƒ, STONE_SELECTOR);
            this.generateBox(â˜ƒ, â˜ƒ, 4, 4, â˜ƒ, 4, 5, â˜ƒ, false, â˜ƒ, STONE_SELECTOR);
            this.generateBox(â˜ƒ, â˜ƒ, 7, 4, â˜ƒ, 7, 5, â˜ƒ, false, â˜ƒ, STONE_SELECTOR);
            this.generateBox(â˜ƒ, â˜ƒ, 9, 4, â˜ƒ, 9, 5, â˜ƒ, false, â˜ƒ, STONE_SELECTOR);
         }

         this.generateBox(â˜ƒ, â˜ƒ, 5, 6, 0, 6, 6, 0, false, â˜ƒ, STONE_SELECTOR);

         for(int â˜ƒ = 0; â˜ƒ <= 11; â˜ƒ += 11) {
            for(int â˜ƒx = 2; â˜ƒx <= 12; â˜ƒx += 2) {
               this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ, 4, â˜ƒx, â˜ƒ, 5, â˜ƒx, false, â˜ƒ, STONE_SELECTOR);
            }

            this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ, 6, 5, â˜ƒ, 6, 5, false, â˜ƒ, STONE_SELECTOR);
            this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ, 6, 9, â˜ƒ, 6, 9, false, â˜ƒ, STONE_SELECTOR);
         }

         this.generateBox(â˜ƒ, â˜ƒ, 2, 7, 2, 2, 9, 2, false, â˜ƒ, STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 9, 7, 2, 9, 9, 2, false, â˜ƒ, STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 7, 12, 2, 9, 12, false, â˜ƒ, STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 9, 7, 12, 9, 9, 12, false, â˜ƒ, STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 9, 4, 4, 9, 4, false, â˜ƒ, STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 7, 9, 4, 7, 9, 4, false, â˜ƒ, STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 9, 10, 4, 9, 10, false, â˜ƒ, STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 7, 9, 10, 7, 9, 10, false, â˜ƒ, STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 9, 7, 6, 9, 7, false, â˜ƒ, STONE_SELECTOR);
         BlockState â˜ƒ = Blocks.COBBLESTONE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST);
         BlockState â˜ƒx = Blocks.COBBLESTONE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST);
         BlockState â˜ƒxx = Blocks.COBBLESTONE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
         BlockState â˜ƒxxx = Blocks.COBBLESTONE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
         this.placeBlock(â˜ƒ, â˜ƒxxx, 5, 9, 6, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxx, 6, 9, 6, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxx, 5, 9, 8, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxx, 6, 9, 8, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxx, 4, 0, 0, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxx, 5, 0, 0, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxx, 6, 0, 0, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxx, 7, 0, 0, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxx, 4, 1, 8, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxx, 4, 2, 9, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxx, 4, 3, 10, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxx, 7, 1, 8, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxx, 7, 2, 9, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxx, 7, 3, 10, â˜ƒ);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 1, 9, 4, 1, 9, false, â˜ƒ, STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 7, 1, 9, 7, 1, 9, false, â˜ƒ, STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 4, 1, 10, 7, 2, 10, false, â˜ƒ, STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 4, 5, 6, 4, 5, false, â˜ƒ, STONE_SELECTOR);
         this.placeBlock(â˜ƒ, â˜ƒ, 4, 4, 5, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒx, 7, 4, 5, â˜ƒ);

         for(int â˜ƒxxxx = 0; â˜ƒxxxx < 4; ++â˜ƒxxxx) {
            this.placeBlock(â˜ƒ, â˜ƒxx, 5, 0 - â˜ƒxxxx, 6 + â˜ƒxxxx, â˜ƒ);
            this.placeBlock(â˜ƒ, â˜ƒxx, 6, 0 - â˜ƒxxxx, 6 + â˜ƒxxxx, â˜ƒ);
            this.generateAirBox(â˜ƒ, â˜ƒ, 5, 0 - â˜ƒxxxx, 7 + â˜ƒxxxx, 6, 0 - â˜ƒxxxx, 9 + â˜ƒxxxx);
         }

         this.generateAirBox(â˜ƒ, â˜ƒ, 1, -3, 12, 10, -1, 13);
         this.generateAirBox(â˜ƒ, â˜ƒ, 1, -3, 1, 3, -1, 13);
         this.generateAirBox(â˜ƒ, â˜ƒ, 1, -3, 1, 9, -1, 5);

         for(int â˜ƒxxxx = 1; â˜ƒxxxx <= 13; â˜ƒxxxx += 2) {
            this.generateBox(â˜ƒ, â˜ƒ, 1, -3, â˜ƒxxxx, 1, -2, â˜ƒxxxx, false, â˜ƒ, STONE_SELECTOR);
         }

         for(int â˜ƒxxxx = 2; â˜ƒxxxx <= 12; â˜ƒxxxx += 2) {
            this.generateBox(â˜ƒ, â˜ƒ, 1, -1, â˜ƒxxxx, 3, -1, â˜ƒxxxx, false, â˜ƒ, STONE_SELECTOR);
         }

         this.generateBox(â˜ƒ, â˜ƒ, 2, -2, 1, 5, -2, 1, false, â˜ƒ, STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 7, -2, 1, 9, -2, 1, false, â˜ƒ, STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 6, -3, 1, 6, -3, 1, false, â˜ƒ, STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 6, -1, 1, 6, -1, 1, false, â˜ƒ, STONE_SELECTOR);
         this.placeBlock(
            â˜ƒ,
            Blocks.TRIPWIRE_HOOK
               .defaultBlockState()
               .setValue(TripWireHookBlock.FACING, Direction.EAST)
               .setValue(TripWireHookBlock.ATTACHED, Boolean.valueOf(true)),
            1,
            -3,
            8,
            â˜ƒ
         );
         this.placeBlock(
            â˜ƒ,
            Blocks.TRIPWIRE_HOOK
               .defaultBlockState()
               .setValue(TripWireHookBlock.FACING, Direction.WEST)
               .setValue(TripWireHookBlock.ATTACHED, Boolean.valueOf(true)),
            4,
            -3,
            8,
            â˜ƒ
         );
         this.placeBlock(
            â˜ƒ,
            Blocks.TRIPWIRE
               .defaultBlockState()
               .setValue(TripWireBlock.EAST, Boolean.valueOf(true))
               .setValue(TripWireBlock.WEST, Boolean.valueOf(true))
               .setValue(TripWireBlock.ATTACHED, Boolean.valueOf(true)),
            2,
            -3,
            8,
            â˜ƒ
         );
         this.placeBlock(
            â˜ƒ,
            Blocks.TRIPWIRE
               .defaultBlockState()
               .setValue(TripWireBlock.EAST, Boolean.valueOf(true))
               .setValue(TripWireBlock.WEST, Boolean.valueOf(true))
               .setValue(TripWireBlock.ATTACHED, Boolean.valueOf(true)),
            3,
            -3,
            8,
            â˜ƒ
         );
         BlockState â˜ƒxxxx = Blocks.REDSTONE_WIRE
            .defaultBlockState()
            .setValue(RedStoneWireBlock.NORTH, RedstoneSide.SIDE)
            .setValue(RedStoneWireBlock.SOUTH, RedstoneSide.SIDE);
         this.placeBlock(â˜ƒ, â˜ƒxxxx, 5, -3, 7, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxxx, 5, -3, 6, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxxx, 5, -3, 5, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxxx, 5, -3, 4, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxxx, 5, -3, 3, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxxx, 5, -3, 2, â˜ƒ);
         this.placeBlock(
            â˜ƒ,
            Blocks.REDSTONE_WIRE.defaultBlockState().setValue(RedStoneWireBlock.NORTH, RedstoneSide.SIDE).setValue(RedStoneWireBlock.WEST, RedstoneSide.SIDE),
            5,
            -3,
            1,
            â˜ƒ
         );
         this.placeBlock(
            â˜ƒ,
            Blocks.REDSTONE_WIRE.defaultBlockState().setValue(RedStoneWireBlock.EAST, RedstoneSide.SIDE).setValue(RedStoneWireBlock.WEST, RedstoneSide.SIDE),
            4,
            -3,
            1,
            â˜ƒ
         );
         this.placeBlock(â˜ƒ, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 3, -3, 1, â˜ƒ);
         if (!this.placedTrap1) {
            this.placedTrap1 = this.createDispenser(â˜ƒ, â˜ƒ, â˜ƒ, 3, -2, 1, Direction.NORTH, BuiltInLootTables.JUNGLE_TEMPLE_DISPENSER);
         }

         this.placeBlock(â˜ƒ, Blocks.VINE.defaultBlockState().setValue(VineBlock.SOUTH, Boolean.valueOf(true)), 3, -2, 2, â˜ƒ);
         this.placeBlock(
            â˜ƒ,
            Blocks.TRIPWIRE_HOOK
               .defaultBlockState()
               .setValue(TripWireHookBlock.FACING, Direction.NORTH)
               .setValue(TripWireHookBlock.ATTACHED, Boolean.valueOf(true)),
            7,
            -3,
            1,
            â˜ƒ
         );
         this.placeBlock(
            â˜ƒ,
            Blocks.TRIPWIRE_HOOK
               .defaultBlockState()
               .setValue(TripWireHookBlock.FACING, Direction.SOUTH)
               .setValue(TripWireHookBlock.ATTACHED, Boolean.valueOf(true)),
            7,
            -3,
            5,
            â˜ƒ
         );
         this.placeBlock(
            â˜ƒ,
            Blocks.TRIPWIRE
               .defaultBlockState()
               .setValue(TripWireBlock.NORTH, Boolean.valueOf(true))
               .setValue(TripWireBlock.SOUTH, Boolean.valueOf(true))
               .setValue(TripWireBlock.ATTACHED, Boolean.valueOf(true)),
            7,
            -3,
            2,
            â˜ƒ
         );
         this.placeBlock(
            â˜ƒ,
            Blocks.TRIPWIRE
               .defaultBlockState()
               .setValue(TripWireBlock.NORTH, Boolean.valueOf(true))
               .setValue(TripWireBlock.SOUTH, Boolean.valueOf(true))
               .setValue(TripWireBlock.ATTACHED, Boolean.valueOf(true)),
            7,
            -3,
            3,
            â˜ƒ
         );
         this.placeBlock(
            â˜ƒ,
            Blocks.TRIPWIRE
               .defaultBlockState()
               .setValue(TripWireBlock.NORTH, Boolean.valueOf(true))
               .setValue(TripWireBlock.SOUTH, Boolean.valueOf(true))
               .setValue(TripWireBlock.ATTACHED, Boolean.valueOf(true)),
            7,
            -3,
            4,
            â˜ƒ
         );
         this.placeBlock(
            â˜ƒ,
            Blocks.REDSTONE_WIRE.defaultBlockState().setValue(RedStoneWireBlock.EAST, RedstoneSide.SIDE).setValue(RedStoneWireBlock.WEST, RedstoneSide.SIDE),
            8,
            -3,
            6,
            â˜ƒ
         );
         this.placeBlock(
            â˜ƒ,
            Blocks.REDSTONE_WIRE.defaultBlockState().setValue(RedStoneWireBlock.WEST, RedstoneSide.SIDE).setValue(RedStoneWireBlock.SOUTH, RedstoneSide.SIDE),
            9,
            -3,
            6,
            â˜ƒ
         );
         this.placeBlock(
            â˜ƒ,
            Blocks.REDSTONE_WIRE.defaultBlockState().setValue(RedStoneWireBlock.NORTH, RedstoneSide.SIDE).setValue(RedStoneWireBlock.SOUTH, RedstoneSide.UP),
            9,
            -3,
            5,
            â˜ƒ
         );
         this.placeBlock(â˜ƒ, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 9, -3, 4, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxxx, 9, -2, 4, â˜ƒ);
         if (!this.placedTrap2) {
            this.placedTrap2 = this.createDispenser(â˜ƒ, â˜ƒ, â˜ƒ, 9, -2, 3, Direction.WEST, BuiltInLootTables.JUNGLE_TEMPLE_DISPENSER);
         }

         this.placeBlock(â˜ƒ, Blocks.VINE.defaultBlockState().setValue(VineBlock.EAST, Boolean.valueOf(true)), 8, -1, 3, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.VINE.defaultBlockState().setValue(VineBlock.EAST, Boolean.valueOf(true)), 8, -2, 3, â˜ƒ);
         if (!this.placedMainChest) {
            this.placedMainChest = this.createChest(â˜ƒ, â˜ƒ, â˜ƒ, 8, -3, 3, BuiltInLootTables.JUNGLE_TEMPLE);
         }

         this.placeBlock(â˜ƒ, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 9, -3, 2, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 8, -3, 1, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 4, -3, 5, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 5, -2, 5, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 5, -1, 5, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 6, -3, 5, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 7, -2, 5, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 7, -1, 5, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 8, -3, 5, â˜ƒ);
         this.generateBox(â˜ƒ, â˜ƒ, 9, -1, 1, 9, -1, 5, false, â˜ƒ, STONE_SELECTOR);
         this.generateAirBox(â˜ƒ, â˜ƒ, 8, -3, 8, 10, -1, 10);
         this.placeBlock(â˜ƒ, Blocks.CHISELED_STONE_BRICKS.defaultBlockState(), 8, -2, 11, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.CHISELED_STONE_BRICKS.defaultBlockState(), 9, -2, 11, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.CHISELED_STONE_BRICKS.defaultBlockState(), 10, -2, 11, â˜ƒ);
         BlockState â˜ƒxxxx = Blocks.LEVER.defaultBlockState().setValue(LeverBlock.FACING, Direction.NORTH).setValue(LeverBlock.FACE, AttachFace.WALL);
         this.placeBlock(â˜ƒ, â˜ƒxxxx, 8, -2, 12, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxxx, 9, -2, 12, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxxx, 10, -2, 12, â˜ƒ);
         this.generateBox(â˜ƒ, â˜ƒ, 8, -3, 8, 8, -3, 10, false, â˜ƒ, STONE_SELECTOR);
         this.generateBox(â˜ƒ, â˜ƒ, 10, -3, 8, 10, -3, 10, false, â˜ƒ, STONE_SELECTOR);
         this.placeBlock(â˜ƒ, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 10, -2, 9, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxxx, 8, -2, 9, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxxx, 8, -2, 10, â˜ƒ);
         this.placeBlock(
            â˜ƒ,
            Blocks.REDSTONE_WIRE
               .defaultBlockState()
               .setValue(RedStoneWireBlock.NORTH, RedstoneSide.SIDE)
               .setValue(RedStoneWireBlock.SOUTH, RedstoneSide.SIDE)
               .setValue(RedStoneWireBlock.EAST, RedstoneSide.SIDE)
               .setValue(RedStoneWireBlock.WEST, RedstoneSide.SIDE),
            10,
            -1,
            9,
            â˜ƒ
         );
         this.placeBlock(â˜ƒ, Blocks.STICKY_PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, Direction.UP), 9, -2, 8, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.STICKY_PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, Direction.WEST), 10, -2, 8, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.STICKY_PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, Direction.WEST), 10, -1, 8, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.REPEATER.defaultBlockState().setValue(RepeaterBlock.FACING, Direction.NORTH), 10, -2, 10, â˜ƒ);
         if (!this.placedHiddenChest) {
            this.placedHiddenChest = this.createChest(â˜ƒ, â˜ƒ, â˜ƒ, 9, -3, 10, BuiltInLootTables.JUNGLE_TEMPLE);
         }

         return true;
      }
   }

   static class MossStoneSelector extends StructurePiece.BlockSelector {
      @Override
      public void next(Random var1, int var2, int var3, int var4, boolean var5) {
         if (â˜ƒ.nextFloat() < 0.4F) {
            this.next = Blocks.COBBLESTONE.defaultBlockState();
         } else {
            this.next = Blocks.MOSSY_COBBLESTONE.defaultBlockState();
         }
      }
   }
}
