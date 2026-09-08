package net.minecraft.world.level.block.entity;

import javax.annotation.Nullable;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.Clearable;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.LecternMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class LecternBlockEntity extends BlockEntity implements Clearable, MenuProvider {
   public static final int DATA_PAGE = 0;
   public static final int NUM_DATA = 1;
   public static final int SLOT_BOOK = 0;
   public static final int NUM_SLOTS = 1;
   private final Container bookAccess = new Container() {
      @Override
      public int getContainerSize() {
         return 1;
      }

      @Override
      public boolean isEmpty() {
         return LecternBlockEntity.this.book.isEmpty();
      }

      @Override
      public ItemStack getItem(int var1) {
         return â˜ƒ == 0 ? LecternBlockEntity.this.book : ItemStack.EMPTY;
      }

      @Override
      public ItemStack removeItem(int var1, int var2) {
         if (â˜ƒ == 0) {
            ItemStack â˜ƒ = LecternBlockEntity.this.book.split(â˜ƒ);
            if (LecternBlockEntity.this.book.isEmpty()) {
               LecternBlockEntity.this.onBookItemRemove();
            }

            return â˜ƒ;
         } else {
            return ItemStack.EMPTY;
         }
      }

      @Override
      public ItemStack removeItemNoUpdate(int var1) {
         if (â˜ƒ == 0) {
            ItemStack â˜ƒ = LecternBlockEntity.this.book;
            LecternBlockEntity.this.book = ItemStack.EMPTY;
            LecternBlockEntity.this.onBookItemRemove();
            return â˜ƒ;
         } else {
            return ItemStack.EMPTY;
         }
      }

      @Override
      public void setItem(int var1, ItemStack var2) {
      }

      @Override
      public int getMaxStackSize() {
         return 1;
      }

      @Override
      public void setChanged() {
         LecternBlockEntity.this.setChanged();
      }

      @Override
      public boolean stillValid(Player var1) {
         if (LecternBlockEntity.this.level.getBlockEntity(LecternBlockEntity.this.worldPosition) != LecternBlockEntity.this) {
            return false;
         } else {
            return â˜ƒ.distanceToSqr(
                     (double)LecternBlockEntity.this.worldPosition.getX() + 0.5,
                     (double)LecternBlockEntity.this.worldPosition.getY() + 0.5,
                     (double)LecternBlockEntity.this.worldPosition.getZ() + 0.5
                  )
                  > 64.0
               ? false
               : LecternBlockEntity.this.hasBook();
         }
      }

      @Override
      public boolean canPlaceItem(int var1, ItemStack var2) {
         return false;
      }

      @Override
      public void clearContent() {
      }
   };
   private final ContainerData dataAccess = new ContainerData() {
      @Override
      public int get(int var1) {
         return â˜ƒ == 0 ? LecternBlockEntity.this.page : 0;
      }

      @Override
      public void set(int var1, int var2) {
         if (â˜ƒ == 0) {
            LecternBlockEntity.this.setPage(â˜ƒ);
         }
      }

      @Override
      public int getCount() {
         return 1;
      }
   };
   ItemStack book = ItemStack.EMPTY;
   int page;
   private int pageCount;

   public LecternBlockEntity(BlockPos var1, BlockState var2) {
      super(BlockEntityType.LECTERN, â˜ƒ, â˜ƒ);
   }

   public ItemStack getBook() {
      return this.book;
   }

   public boolean hasBook() {
      return this.book.is(Items.WRITABLE_BOOK) || this.book.is(Items.WRITTEN_BOOK);
   }

   public void setBook(ItemStack var1) {
      this.setBook(â˜ƒ, null);
   }

   void onBookItemRemove() {
      this.page = 0;
      this.pageCount = 0;
      LecternBlock.resetBookState(this.getLevel(), this.getBlockPos(), this.getBlockState(), false);
   }

   public void setBook(ItemStack var1, @Nullable Player var2) {
      this.book = this.resolveBook(â˜ƒ, â˜ƒ);
      this.page = 0;
      this.pageCount = WrittenBookItem.getPageCount(this.book);
      this.setChanged();
   }

   void setPage(int var1) {
      int â˜ƒ = Mth.clamp(â˜ƒ, 0, this.pageCount - 1);
      if (â˜ƒ != this.page) {
         this.page = â˜ƒ;
         this.setChanged();
         LecternBlock.signalPageChange(this.getLevel(), this.getBlockPos(), this.getBlockState());
      }
   }

   public int getPage() {
      return this.page;
   }

   public int getRedstoneSignal() {
      float â˜ƒ = this.pageCount > 1 ? (float)this.getPage() / ((float)this.pageCount - 1.0F) : 1.0F;
      return Mth.floor(â˜ƒ * 14.0F) + (this.hasBook() ? 1 : 0);
   }

   private ItemStack resolveBook(ItemStack var1, @Nullable Player var2) {
      if (this.level instanceof ServerLevel && â˜ƒ.is(Items.WRITTEN_BOOK)) {
         WrittenBookItem.resolveBookComponents(â˜ƒ, this.createCommandSourceStack(â˜ƒ), â˜ƒ);
      }

      return â˜ƒ;
   }

   private CommandSourceStack createCommandSourceStack(@Nullable Player var1) {
      String â˜ƒ;
      Component â˜ƒx;
      if (â˜ƒ == null) {
         â˜ƒ = "Lectern";
         â˜ƒx = new TextComponent("Lectern");
      } else {
         â˜ƒ = â˜ƒ.getName().getString();
         â˜ƒx = â˜ƒ.getDisplayName();
      }

      Vec3 â˜ƒ = Vec3.atCenterOf(this.worldPosition);
      return new CommandSourceStack(CommandSource.NULL, â˜ƒ, Vec2.ZERO, (ServerLevel)this.level, 2, â˜ƒ, â˜ƒx, this.level.getServer(), â˜ƒ);
   }

   @Override
   public boolean onlyOpCanSetNbt() {
      return true;
   }

   @Override
   public void load(CompoundTag var1) {
      super.load(â˜ƒ);
      if (â˜ƒ.contains("Book", 10)) {
         this.book = this.resolveBook(ItemStack.of(â˜ƒ.getCompound("Book")), null);
      } else {
         this.book = ItemStack.EMPTY;
      }

      this.pageCount = WrittenBookItem.getPageCount(this.book);
      this.page = Mth.clamp(â˜ƒ.getInt("Page"), 0, this.pageCount - 1);
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      super.save(â˜ƒ);
      if (!this.getBook().isEmpty()) {
         â˜ƒ.put("Book", this.getBook().save(new CompoundTag()));
         â˜ƒ.putInt("Page", this.page);
      }

      return â˜ƒ;
   }

   @Override
   public void clearContent() {
      this.setBook(ItemStack.EMPTY);
   }

   @Override
   public AbstractContainerMenu createMenu(int var1, Inventory var2, Player var3) {
      return new LecternMenu(â˜ƒ, this.bookAccess, this.dataAccess);
   }

   @Override
   public Component getDisplayName() {
      return new TranslatableComponent("container.lectern");
   }
}
