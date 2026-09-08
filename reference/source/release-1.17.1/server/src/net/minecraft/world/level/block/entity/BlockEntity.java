package net.minecraft.world.level.block.entity;

import javax.annotation.Nullable;
import net.minecraft.CrashReportCategory;
import net.minecraft.CrashReportDetail;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BlockEntity {
   private static final Logger LOGGER = LogManager.getLogger();
   private final BlockEntityType<?> type;
   @Nullable
   protected Level level;
   protected final BlockPos worldPosition;
   protected boolean remove;
   private BlockState blockState;

   public BlockEntity(BlockEntityType<?> var1, BlockPos var2, BlockState var3) {
      this.type = â˜ƒ;
      this.worldPosition = â˜ƒ.immutable();
      this.blockState = â˜ƒ;
   }

   @Nullable
   public Level getLevel() {
      return this.level;
   }

   public void setLevel(Level var1) {
      this.level = â˜ƒ;
   }

   public boolean hasLevel() {
      return this.level != null;
   }

   public void load(CompoundTag var1) {
   }

   public CompoundTag save(CompoundTag var1) {
      return this.saveMetadata(â˜ƒ);
   }

   private CompoundTag saveMetadata(CompoundTag var1) {
      ResourceLocation â˜ƒ = BlockEntityType.getKey(this.getType());
      if (â˜ƒ == null) {
         throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
      } else {
         â˜ƒ.putString("id", â˜ƒ.toString());
         â˜ƒ.putInt("x", this.worldPosition.getX());
         â˜ƒ.putInt("y", this.worldPosition.getY());
         â˜ƒ.putInt("z", this.worldPosition.getZ());
         return â˜ƒ;
      }
   }

   @Nullable
   public static BlockEntity loadStatic(BlockPos var0, BlockState var1, CompoundTag var2) {
      String â˜ƒ = â˜ƒ.getString("id");
      ResourceLocation â˜ƒx = ResourceLocation.tryParse(â˜ƒ);
      if (â˜ƒx == null) {
         LOGGER.error("Block entity has invalid type: {}", â˜ƒ);
         return null;
      } else {
         return (BlockEntity)Registry.BLOCK_ENTITY_TYPE.getOptional(â˜ƒx).map(var3x -> {
            try {
               return var3x.create(â˜ƒ, â˜ƒ);
            } catch (Throwable var5) {
               LOGGER.error("Failed to create block entity {}", â˜ƒ, var5);
               return null;
            }
         }).map(var2x -> {
            try {
               var2x.load(â˜ƒ);
               return var2x;
            } catch (Throwable var4xx) {
               LOGGER.error("Failed to load data for block entity {}", â˜ƒ, var4xx);
               return null;
            }
         }).orElseGet(() -> {
            LOGGER.warn("Skipping BlockEntity with id {}", â˜ƒ);
            return null;
         });
      }
   }

   public void setChanged() {
      if (this.level != null) {
         setChanged(this.level, this.worldPosition, this.blockState);
      }
   }

   protected static void setChanged(Level var0, BlockPos var1, BlockState var2) {
      â˜ƒ.blockEntityChanged(â˜ƒ);
      if (!â˜ƒ.isAir()) {
         â˜ƒ.updateNeighbourForOutputSignal(â˜ƒ, â˜ƒ.getBlock());
      }
   }

   public BlockPos getBlockPos() {
      return this.worldPosition;
   }

   public BlockState getBlockState() {
      return this.blockState;
   }

   @Nullable
   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return null;
   }

   public CompoundTag getUpdateTag() {
      return this.saveMetadata(new CompoundTag());
   }

   public boolean isRemoved() {
      return this.remove;
   }

   public void setRemoved() {
      this.remove = true;
   }

   public void clearRemoved() {
      this.remove = false;
   }

   public boolean triggerEvent(int var1, int var2) {
      return false;
   }

   public void fillCrashReportCategory(CrashReportCategory var1) {
      â˜ƒ.setDetail("Name", (CrashReportDetail<String>)(() -> Registry.BLOCK_ENTITY_TYPE.getKey(this.getType()) + " // " + this.getClass().getCanonicalName()));
      if (this.level != null) {
         CrashReportCategory.populateBlockDetails(â˜ƒ, this.level, this.worldPosition, this.getBlockState());
         CrashReportCategory.populateBlockDetails(â˜ƒ, this.level, this.worldPosition, this.level.getBlockState(this.worldPosition));
      }
   }

   public boolean onlyOpCanSetNbt() {
      return false;
   }

   public BlockEntityType<?> getType() {
      return this.type;
   }

   @Deprecated
   public void setBlockState(BlockState var1) {
      this.blockState = â˜ƒ;
   }
}
