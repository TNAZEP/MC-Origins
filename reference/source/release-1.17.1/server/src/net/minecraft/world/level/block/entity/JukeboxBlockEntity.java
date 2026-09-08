package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class JukeboxBlockEntity extends BlockEntity implements Clearable {
   private ItemStack record = ItemStack.EMPTY;

   public JukeboxBlockEntity(BlockPos var1, BlockState var2) {
      super(BlockEntityType.JUKEBOX, â˜ƒ, â˜ƒ);
   }

   @Override
   public void load(CompoundTag var1) {
      super.load(â˜ƒ);
      if (â˜ƒ.contains("RecordItem", 10)) {
         this.setRecord(ItemStack.of(â˜ƒ.getCompound("RecordItem")));
      }
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      super.save(â˜ƒ);
      if (!this.getRecord().isEmpty()) {
         â˜ƒ.put("RecordItem", this.getRecord().save(new CompoundTag()));
      }

      return â˜ƒ;
   }

   public ItemStack getRecord() {
      return this.record;
   }

   public void setRecord(ItemStack var1) {
      this.record = â˜ƒ;
      this.setChanged();
   }

   @Override
   public void clearContent() {
      this.setRecord(ItemStack.EMPTY);
   }
}
