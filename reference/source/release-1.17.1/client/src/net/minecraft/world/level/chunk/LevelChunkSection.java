package net.minecraft.world.level.chunk;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class LevelChunkSection {
   public static final int SECTION_WIDTH = 16;
   public static final int SECTION_HEIGHT = 16;
   public static final int SECTION_SIZE = 4096;
   private static final Palette<BlockState> GLOBAL_BLOCKSTATE_PALETTE = new GlobalPalette<>(Block.BLOCK_STATE_REGISTRY, Blocks.AIR.defaultBlockState());
   private final int bottomBlockY;
   private short nonEmptyBlockCount;
   private short tickingBlockCount;
   private short tickingFluidCount;
   private final PalettedContainer<BlockState> states;

   public LevelChunkSection(int var1) {
      this(â˜ƒ, (short)0, (short)0, (short)0);
   }

   public LevelChunkSection(int var1, short var2, short var3, short var4) {
      this.bottomBlockY = getBottomBlockY(â˜ƒ);
      this.nonEmptyBlockCount = â˜ƒ;
      this.tickingBlockCount = â˜ƒ;
      this.tickingFluidCount = â˜ƒ;
      this.states = new PalettedContainer<>(
         GLOBAL_BLOCKSTATE_PALETTE, Block.BLOCK_STATE_REGISTRY, NbtUtils::readBlockState, NbtUtils::writeBlockState, Blocks.AIR.defaultBlockState()
      );
   }

   public static int getBottomBlockY(int var0) {
      return â˜ƒ << 4;
   }

   public BlockState getBlockState(int var1, int var2, int var3) {
      return this.states.get(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public FluidState getFluidState(int var1, int var2, int var3) {
      return this.states.get(â˜ƒ, â˜ƒ, â˜ƒ).getFluidState();
   }

   public void acquire() {
      this.states.acquire();
   }

   public void release() {
      this.states.release();
   }

   public BlockState setBlockState(int var1, int var2, int var3, BlockState var4) {
      return this.setBlockState(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, true);
   }

   public BlockState setBlockState(int var1, int var2, int var3, BlockState var4, boolean var5) {
      BlockState â˜ƒ;
      if (â˜ƒ) {
         â˜ƒ = this.states.getAndSet(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         â˜ƒ = this.states.getAndSetUnchecked(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      FluidState â˜ƒ = â˜ƒ.getFluidState();
      FluidState â˜ƒx = â˜ƒ.getFluidState();
      if (!â˜ƒ.isAir()) {
         --this.nonEmptyBlockCount;
         if (â˜ƒ.isRandomlyTicking()) {
            --this.tickingBlockCount;
         }
      }

      if (!â˜ƒ.isEmpty()) {
         --this.tickingFluidCount;
      }

      if (!â˜ƒ.isAir()) {
         ++this.nonEmptyBlockCount;
         if (â˜ƒ.isRandomlyTicking()) {
            ++this.tickingBlockCount;
         }
      }

      if (!â˜ƒx.isEmpty()) {
         ++this.tickingFluidCount;
      }

      return â˜ƒ;
   }

   public boolean isEmpty() {
      return this.nonEmptyBlockCount == 0;
   }

   public static boolean isEmpty(@Nullable LevelChunkSection var0) {
      return â˜ƒ == LevelChunk.EMPTY_SECTION || â˜ƒ.isEmpty();
   }

   public boolean isRandomlyTicking() {
      return this.isRandomlyTickingBlocks() || this.isRandomlyTickingFluids();
   }

   public boolean isRandomlyTickingBlocks() {
      return this.tickingBlockCount > 0;
   }

   public boolean isRandomlyTickingFluids() {
      return this.tickingFluidCount > 0;
   }

   public int bottomBlockY() {
      return this.bottomBlockY;
   }

   public void recalcBlockCounts() {
      this.nonEmptyBlockCount = 0;
      this.tickingBlockCount = 0;
      this.tickingFluidCount = 0;
      this.states.count((var1, var2) -> {
         FluidState â˜ƒ = var1.getFluidState();
         if (!var1.isAir()) {
            this.nonEmptyBlockCount = (short)(this.nonEmptyBlockCount + var2);
            if (var1.isRandomlyTicking()) {
               this.tickingBlockCount = (short)(this.tickingBlockCount + var2);
            }
         }

         if (!â˜ƒ.isEmpty()) {
            this.nonEmptyBlockCount = (short)(this.nonEmptyBlockCount + var2);
            if (â˜ƒ.isRandomlyTicking()) {
               this.tickingFluidCount = (short)(this.tickingFluidCount + var2);
            }
         }
      });
   }

   public PalettedContainer<BlockState> getStates() {
      return this.states;
   }

   public void read(FriendlyByteBuf var1) {
      this.nonEmptyBlockCount = â˜ƒ.readShort();
      this.states.read(â˜ƒ);
   }

   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeShort(this.nonEmptyBlockCount);
      this.states.write(â˜ƒ);
   }

   public int getSerializedSize() {
      return 2 + this.states.getSerializedSize();
   }

   public boolean maybeHas(Predicate<BlockState> var1) {
      return this.states.maybeHas(â˜ƒ);
   }
}
