package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;

public class ChestBlockEntity extends RandomizableContainerBlockEntity implements LidBlockEntity {
   private static final int EVENT_SET_OPEN_COUNT = 1;
   private NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
   private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
      @Override
      protected void onOpen(Level var1, BlockPos var2, BlockState var3) {
         ChestBlockEntity.playSound(â˜ƒ, â˜ƒ, â˜ƒ, SoundEvents.CHEST_OPEN);
      }

      @Override
      protected void onClose(Level var1, BlockPos var2, BlockState var3) {
         ChestBlockEntity.playSound(â˜ƒ, â˜ƒ, â˜ƒ, SoundEvents.CHEST_CLOSE);
      }

      @Override
      protected void openerCountChanged(Level var1, BlockPos var2, BlockState var3, int var4, int var5) {
         ChestBlockEntity.this.signalOpenCount(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      protected boolean isOwnContainer(Player var1) {
         if (!(â˜ƒ.containerMenu instanceof ChestMenu)) {
            return false;
         } else {
            Container â˜ƒ = ((ChestMenu)â˜ƒ.containerMenu).getContainer();
            return â˜ƒ == ChestBlockEntity.this || â˜ƒ instanceof CompoundContainer && ((CompoundContainer)â˜ƒ).contains(ChestBlockEntity.this);
         }
      }
   };
   private final ChestLidController chestLidController = new ChestLidController();

   protected ChestBlockEntity(BlockEntityType<?> var1, BlockPos var2, BlockState var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public ChestBlockEntity(BlockPos var1, BlockState var2) {
      this(BlockEntityType.CHEST, â˜ƒ, â˜ƒ);
   }

   @Override
   public int getContainerSize() {
      return 27;
   }

   @Override
   protected Component getDefaultName() {
      return new TranslatableComponent("container.chest");
   }

   @Override
   public void load(CompoundTag var1) {
      super.load(â˜ƒ);
      this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
      if (!this.tryLoadLootTable(â˜ƒ)) {
         ContainerHelper.loadAllItems(â˜ƒ, this.items);
      }
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      super.save(â˜ƒ);
      if (!this.trySaveLootTable(â˜ƒ)) {
         ContainerHelper.saveAllItems(â˜ƒ, this.items);
      }

      return â˜ƒ;
   }

   public static void lidAnimateTick(Level var0, BlockPos var1, BlockState var2, ChestBlockEntity var3) {
      â˜ƒ.chestLidController.tickLid();
   }

   static void playSound(Level var0, BlockPos var1, BlockState var2, SoundEvent var3) {
      ChestType â˜ƒ = â˜ƒ.getValue(ChestBlock.TYPE);
      if (â˜ƒ != ChestType.LEFT) {
         double â˜ƒx = (double)â˜ƒ.getX() + 0.5;
         double â˜ƒxx = (double)â˜ƒ.getY() + 0.5;
         double â˜ƒxxx = (double)â˜ƒ.getZ() + 0.5;
         if (â˜ƒ == ChestType.RIGHT) {
            Direction â˜ƒxxxx = ChestBlock.getConnectedDirection(â˜ƒ);
            â˜ƒx += (double)â˜ƒxxxx.getStepX() * 0.5;
            â˜ƒxxx += (double)â˜ƒxxxx.getStepZ() * 0.5;
         }

         â˜ƒ.playSound(null, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒ, SoundSource.BLOCKS, 0.5F, â˜ƒ.random.nextFloat() * 0.1F + 0.9F);
      }
   }

   @Override
   public boolean triggerEvent(int var1, int var2) {
      if (â˜ƒ == 1) {
         this.chestLidController.shouldBeOpen(â˜ƒ > 0);
         return true;
      } else {
         return super.triggerEvent(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void startOpen(Player var1) {
      if (!this.remove && !â˜ƒ.isSpectator()) {
         this.openersCounter.incrementOpeners(â˜ƒ, this.getLevel(), this.getBlockPos(), this.getBlockState());
      }
   }

   @Override
   public void stopOpen(Player var1) {
      if (!this.remove && !â˜ƒ.isSpectator()) {
         this.openersCounter.decrementOpeners(â˜ƒ, this.getLevel(), this.getBlockPos(), this.getBlockState());
      }
   }

   @Override
   protected NonNullList<ItemStack> getItems() {
      return this.items;
   }

   @Override
   protected void setItems(NonNullList<ItemStack> var1) {
      this.items = â˜ƒ;
   }

   @Override
   public float getOpenNess(float var1) {
      return this.chestLidController.getOpenness(â˜ƒ);
   }

   public static int getOpenCount(BlockGetter var0, BlockPos var1) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      if (â˜ƒ.hasBlockEntity()) {
         BlockEntity â˜ƒx = â˜ƒ.getBlockEntity(â˜ƒ);
         if (â˜ƒx instanceof ChestBlockEntity) {
            return ((ChestBlockEntity)â˜ƒx).openersCounter.getOpenerCount();
         }
      }

      return 0;
   }

   public static void swapContents(ChestBlockEntity var0, ChestBlockEntity var1) {
      NonNullList<ItemStack> â˜ƒ = â˜ƒ.getItems();
      â˜ƒ.setItems(â˜ƒ.getItems());
      â˜ƒ.setItems(â˜ƒ);
   }

   @Override
   protected AbstractContainerMenu createMenu(int var1, Inventory var2) {
      return ChestMenu.threeRows(â˜ƒ, â˜ƒ, this);
   }

   public void recheckOpen() {
      if (!this.remove) {
         this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
      }
   }

   protected void signalOpenCount(Level var1, BlockPos var2, BlockState var3, int var4, int var5) {
      Block â˜ƒ = â˜ƒ.getBlock();
      â˜ƒ.blockEvent(â˜ƒ, â˜ƒ, 1, â˜ƒ);
   }
}
