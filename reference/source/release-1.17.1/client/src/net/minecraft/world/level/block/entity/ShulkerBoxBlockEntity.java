package net.minecraft.world.level.block.entity;

import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ShulkerBoxBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
   public static final int COLUMNS = 9;
   public static final int ROWS = 3;
   public static final int CONTAINER_SIZE = 27;
   public static final int EVENT_SET_OPEN_COUNT = 1;
   public static final int OPENING_TICK_LENGTH = 10;
   public static final float MAX_LID_HEIGHT = 0.5F;
   public static final float MAX_LID_ROTATION = 270.0F;
   public static final String ITEMS_TAG = "Items";
   private static final int[] SLOTS = IntStream.range(0, 27).toArray();
   private NonNullList<ItemStack> itemStacks = NonNullList.withSize(27, ItemStack.EMPTY);
   private int openCount;
   private ShulkerBoxBlockEntity.AnimationStatus animationStatus = ShulkerBoxBlockEntity.AnimationStatus.CLOSED;
   private float progress;
   private float progressOld;
   @Nullable
   private final DyeColor color;

   public ShulkerBoxBlockEntity(@Nullable DyeColor var1, BlockPos var2, BlockState var3) {
      super(BlockEntityType.SHULKER_BOX, â˜ƒ, â˜ƒ);
      this.color = â˜ƒ;
   }

   public ShulkerBoxBlockEntity(BlockPos var1, BlockState var2) {
      super(BlockEntityType.SHULKER_BOX, â˜ƒ, â˜ƒ);
      this.color = ShulkerBoxBlock.getColorFromBlock(â˜ƒ.getBlock());
   }

   public static void tick(Level var0, BlockPos var1, BlockState var2, ShulkerBoxBlockEntity var3) {
      â˜ƒ.updateAnimation(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private void updateAnimation(Level var1, BlockPos var2, BlockState var3) {
      this.progressOld = this.progress;
      switch(this.animationStatus) {
         case CLOSED:
            this.progress = 0.0F;
            break;
         case OPENING:
            this.progress += 0.1F;
            if (this.progress >= 1.0F) {
               this.animationStatus = ShulkerBoxBlockEntity.AnimationStatus.OPENED;
               this.progress = 1.0F;
               doNeighborUpdates(â˜ƒ, â˜ƒ, â˜ƒ);
            }

            this.moveCollidedEntities(â˜ƒ, â˜ƒ, â˜ƒ);
            break;
         case CLOSING:
            this.progress -= 0.1F;
            if (this.progress <= 0.0F) {
               this.animationStatus = ShulkerBoxBlockEntity.AnimationStatus.CLOSED;
               this.progress = 0.0F;
               doNeighborUpdates(â˜ƒ, â˜ƒ, â˜ƒ);
            }
            break;
         case OPENED:
            this.progress = 1.0F;
      }
   }

   public ShulkerBoxBlockEntity.AnimationStatus getAnimationStatus() {
      return this.animationStatus;
   }

   public AABB getBoundingBox(BlockState var1) {
      return Shulker.getProgressAabb(â˜ƒ.getValue(ShulkerBoxBlock.FACING), 0.5F * this.getProgress(1.0F));
   }

   private void moveCollidedEntities(Level var1, BlockPos var2, BlockState var3) {
      if (â˜ƒ.getBlock() instanceof ShulkerBoxBlock) {
         Direction â˜ƒ = â˜ƒ.getValue(ShulkerBoxBlock.FACING);
         AABB â˜ƒx = Shulker.getProgressDeltaAabb(â˜ƒ, this.progressOld, this.progress).move(â˜ƒ);
         List<Entity> â˜ƒxx = â˜ƒ.getEntities(null, â˜ƒx);
         if (!â˜ƒxx.isEmpty()) {
            for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒxx.size(); ++â˜ƒxxx) {
               Entity â˜ƒxxxx = (Entity)â˜ƒxx.get(â˜ƒxxx);
               if (â˜ƒxxxx.getPistonPushReaction() != PushReaction.IGNORE) {
                  â˜ƒxxxx.move(
                     MoverType.SHULKER_BOX,
                     new Vec3(
                        (â˜ƒx.getXsize() + 0.01) * (double)â˜ƒ.getStepX(),
                        (â˜ƒx.getYsize() + 0.01) * (double)â˜ƒ.getStepY(),
                        (â˜ƒx.getZsize() + 0.01) * (double)â˜ƒ.getStepZ()
                     )
                  );
               }
            }
         }
      }
   }

   @Override
   public int getContainerSize() {
      return this.itemStacks.size();
   }

   @Override
   public boolean triggerEvent(int var1, int var2) {
      if (â˜ƒ == 1) {
         this.openCount = â˜ƒ;
         if (â˜ƒ == 0) {
            this.animationStatus = ShulkerBoxBlockEntity.AnimationStatus.CLOSING;
            doNeighborUpdates(this.getLevel(), this.worldPosition, this.getBlockState());
         }

         if (â˜ƒ == 1) {
            this.animationStatus = ShulkerBoxBlockEntity.AnimationStatus.OPENING;
            doNeighborUpdates(this.getLevel(), this.worldPosition, this.getBlockState());
         }

         return true;
      } else {
         return super.triggerEvent(â˜ƒ, â˜ƒ);
      }
   }

   private static void doNeighborUpdates(Level var0, BlockPos var1, BlockState var2) {
      â˜ƒ.updateNeighbourShapes(â˜ƒ, â˜ƒ, 3);
   }

   @Override
   public void startOpen(Player var1) {
      if (!â˜ƒ.isSpectator()) {
         if (this.openCount < 0) {
            this.openCount = 0;
         }

         ++this.openCount;
         this.level.blockEvent(this.worldPosition, this.getBlockState().getBlock(), 1, this.openCount);
         if (this.openCount == 1) {
            this.level.gameEvent(â˜ƒ, GameEvent.CONTAINER_OPEN, this.worldPosition);
            this.level.playSound(null, this.worldPosition, SoundEvents.SHULKER_BOX_OPEN, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
         }
      }
   }

   @Override
   public void stopOpen(Player var1) {
      if (!â˜ƒ.isSpectator()) {
         --this.openCount;
         this.level.blockEvent(this.worldPosition, this.getBlockState().getBlock(), 1, this.openCount);
         if (this.openCount <= 0) {
            this.level.gameEvent(â˜ƒ, GameEvent.CONTAINER_CLOSE, this.worldPosition);
            this.level
               .playSound(null, this.worldPosition, SoundEvents.SHULKER_BOX_CLOSE, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
         }
      }
   }

   @Override
   protected Component getDefaultName() {
      return new TranslatableComponent("container.shulkerBox");
   }

   @Override
   public void load(CompoundTag var1) {
      super.load(â˜ƒ);
      this.loadFromTag(â˜ƒ);
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      super.save(â˜ƒ);
      return this.saveToTag(â˜ƒ);
   }

   public void loadFromTag(CompoundTag var1) {
      this.itemStacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
      if (!this.tryLoadLootTable(â˜ƒ) && â˜ƒ.contains("Items", 9)) {
         ContainerHelper.loadAllItems(â˜ƒ, this.itemStacks);
      }
   }

   public CompoundTag saveToTag(CompoundTag var1) {
      if (!this.trySaveLootTable(â˜ƒ)) {
         ContainerHelper.saveAllItems(â˜ƒ, this.itemStacks, false);
      }

      return â˜ƒ;
   }

   @Override
   protected NonNullList<ItemStack> getItems() {
      return this.itemStacks;
   }

   @Override
   protected void setItems(NonNullList<ItemStack> var1) {
      this.itemStacks = â˜ƒ;
   }

   @Override
   public int[] getSlotsForFace(Direction var1) {
      return SLOTS;
   }

   @Override
   public boolean canPlaceItemThroughFace(int var1, ItemStack var2, @Nullable Direction var3) {
      return !(Block.byItem(â˜ƒ.getItem()) instanceof ShulkerBoxBlock);
   }

   @Override
   public boolean canTakeItemThroughFace(int var1, ItemStack var2, Direction var3) {
      return true;
   }

   public float getProgress(float var1) {
      return Mth.lerp(â˜ƒ, this.progressOld, this.progress);
   }

   @Nullable
   public DyeColor getColor() {
      return this.color;
   }

   @Override
   protected AbstractContainerMenu createMenu(int var1, Inventory var2) {
      return new ShulkerBoxMenu(â˜ƒ, â˜ƒ, this);
   }

   public boolean isClosed() {
      return this.animationStatus == ShulkerBoxBlockEntity.AnimationStatus.CLOSED;
   }

   public static enum AnimationStatus {
      CLOSED,
      OPENING,
      OPENED,
      CLOSING;
   }
}
