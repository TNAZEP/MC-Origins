package net.minecraft.commands.arguments.blocks;

import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.properties.Property;

public class BlockInput implements Predicate<BlockInWorld> {
   private final BlockState state;
   private final Set<Property<?>> properties;
   @Nullable
   private final CompoundTag tag;

   public BlockInput(BlockState var1, Set<Property<?>> var2, @Nullable CompoundTag var3) {
      this.state = â˜ƒ;
      this.properties = â˜ƒ;
      this.tag = â˜ƒ;
   }

   public BlockState getState() {
      return this.state;
   }

   public Set<Property<?>> getDefinedProperties() {
      return this.properties;
   }

   public boolean test(BlockInWorld var1) {
      BlockState â˜ƒ = â˜ƒ.getState();
      if (!â˜ƒ.is(this.state.getBlock())) {
         return false;
      } else {
         for(Property<?> â˜ƒ : this.properties) {
            if (â˜ƒ.getValue(â˜ƒ) != this.state.getValue(â˜ƒ)) {
               return false;
            }
         }

         if (this.tag == null) {
            return true;
         } else {
            BlockEntity â˜ƒ = â˜ƒ.getEntity();
            return â˜ƒ != null && NbtUtils.compareNbt(this.tag, â˜ƒ.save(new CompoundTag()), true);
         }
      }
   }

   public boolean test(ServerLevel var1, BlockPos var2) {
      return this.test(new BlockInWorld(â˜ƒ, â˜ƒ, false));
   }

   public boolean place(ServerLevel var1, BlockPos var2, int var3) {
      BlockState â˜ƒ = Block.updateFromNeighbourShapes(this.state, â˜ƒ, â˜ƒ);
      if (â˜ƒ.isAir()) {
         â˜ƒ = this.state;
      }

      if (!â˜ƒ.setBlock(â˜ƒ, â˜ƒ, â˜ƒ)) {
         return false;
      } else {
         if (this.tag != null) {
            BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
            if (â˜ƒ != null) {
               CompoundTag â˜ƒx = this.tag.copy();
               â˜ƒx.putInt("x", â˜ƒ.getX());
               â˜ƒx.putInt("y", â˜ƒ.getY());
               â˜ƒx.putInt("z", â˜ƒ.getZ());
               â˜ƒ.load(â˜ƒx);
            }
         }

         return true;
      }
   }
}
