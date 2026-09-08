package net.minecraft.world.level.block.entity;

import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.Clearable;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CampfireBlockEntity extends BlockEntity implements Clearable {
   private static final int BURN_COOL_SPEED = 2;
   private static final int NUM_SLOTS = 4;
   private final NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
   private final int[] cookingProgress = new int[4];
   private final int[] cookingTime = new int[4];

   public CampfireBlockEntity(BlockPos var1, BlockState var2) {
      super(BlockEntityType.CAMPFIRE, â˜ƒ, â˜ƒ);
   }

   public static void cookTick(Level var0, BlockPos var1, BlockState var2, CampfireBlockEntity var3) {
      boolean â˜ƒ = false;

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.items.size(); ++â˜ƒx) {
         ItemStack â˜ƒxx = â˜ƒ.items.get(â˜ƒx);
         if (!â˜ƒxx.isEmpty()) {
            â˜ƒ = true;
            â˜ƒ.cookingProgress[â˜ƒx]++;
            if (â˜ƒ.cookingProgress[â˜ƒx] >= â˜ƒ.cookingTime[â˜ƒx]) {
               Container â˜ƒxxx = new SimpleContainer(â˜ƒxx);
               ItemStack â˜ƒxxxx = (ItemStack)â˜ƒ.getRecipeManager()
                  .getRecipeFor(RecipeType.CAMPFIRE_COOKING, â˜ƒxxx, â˜ƒ)
                  .map(var1x -> var1x.assemble(â˜ƒ))
                  .orElse(â˜ƒxx);
               Containers.dropItemStack(â˜ƒ, (double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ(), â˜ƒxxxx);
               â˜ƒ.items.set(â˜ƒx, ItemStack.EMPTY);
               â˜ƒ.sendBlockUpdated(â˜ƒ, â˜ƒ, â˜ƒ, 3);
            }
         }
      }

      if (â˜ƒ) {
         setChanged(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public static void cooldownTick(Level var0, BlockPos var1, BlockState var2, CampfireBlockEntity var3) {
      boolean â˜ƒ = false;

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.items.size(); ++â˜ƒx) {
         if (â˜ƒ.cookingProgress[â˜ƒx] > 0) {
            â˜ƒ = true;
            â˜ƒ.cookingProgress[â˜ƒx] = Mth.clamp(â˜ƒ.cookingProgress[â˜ƒx] - 2, 0, â˜ƒ.cookingTime[â˜ƒx]);
         }
      }

      if (â˜ƒ) {
         setChanged(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public static void particleTick(Level var0, BlockPos var1, BlockState var2, CampfireBlockEntity var3) {
      Random â˜ƒ = â˜ƒ.random;
      if (â˜ƒ.nextFloat() < 0.11F) {
         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.nextInt(2) + 2; ++â˜ƒx) {
            CampfireBlock.makeParticles(â˜ƒ, â˜ƒ, â˜ƒ.getValue(CampfireBlock.SIGNAL_FIRE), false);
         }
      }

      int â˜ƒ = ((Direction)â˜ƒ.getValue(CampfireBlock.FACING)).get2DDataValue();

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.items.size(); ++â˜ƒx) {
         if (!â˜ƒ.items.get(â˜ƒx).isEmpty() && â˜ƒ.nextFloat() < 0.2F) {
            Direction â˜ƒxx = Direction.from2DDataValue(Math.floorMod(â˜ƒx + â˜ƒ, 4));
            float â˜ƒxxx = 0.3125F;
            double â˜ƒxxxx = (double)â˜ƒ.getX()
               + 0.5
               - (double)((float)â˜ƒxx.getStepX() * 0.3125F)
               + (double)((float)â˜ƒxx.getClockWise().getStepX() * 0.3125F);
            double â˜ƒxxxxx = (double)â˜ƒ.getY() + 0.5;
            double â˜ƒxxxxxx = (double)â˜ƒ.getZ()
               + 0.5
               - (double)((float)â˜ƒxx.getStepZ() * 0.3125F)
               + (double)((float)â˜ƒxx.getClockWise().getStepZ() * 0.3125F);

            for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < 4; ++â˜ƒxxxxxxx) {
               â˜ƒ.addParticle(ParticleTypes.SMOKE, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, 0.0, 5.0E-4, 0.0);
            }
         }
      }
   }

   public NonNullList<ItemStack> getItems() {
      return this.items;
   }

   @Override
   public void load(CompoundTag var1) {
      super.load(â˜ƒ);
      this.items.clear();
      ContainerHelper.loadAllItems(â˜ƒ, this.items);
      if (â˜ƒ.contains("CookingTimes", 11)) {
         int[] â˜ƒ = â˜ƒ.getIntArray("CookingTimes");
         System.arraycopy(â˜ƒ, 0, this.cookingProgress, 0, Math.min(this.cookingTime.length, â˜ƒ.length));
      }

      if (â˜ƒ.contains("CookingTotalTimes", 11)) {
         int[] â˜ƒ = â˜ƒ.getIntArray("CookingTotalTimes");
         System.arraycopy(â˜ƒ, 0, this.cookingTime, 0, Math.min(this.cookingTime.length, â˜ƒ.length));
      }
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      this.saveMetadataAndItems(â˜ƒ);
      â˜ƒ.putIntArray("CookingTimes", this.cookingProgress);
      â˜ƒ.putIntArray("CookingTotalTimes", this.cookingTime);
      return â˜ƒ;
   }

   private CompoundTag saveMetadataAndItems(CompoundTag var1) {
      super.save(â˜ƒ);
      ContainerHelper.saveAllItems(â˜ƒ, this.items, true);
      return â˜ƒ;
   }

   @Nullable
   @Override
   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return new ClientboundBlockEntityDataPacket(this.worldPosition, 13, this.getUpdateTag());
   }

   @Override
   public CompoundTag getUpdateTag() {
      return this.saveMetadataAndItems(new CompoundTag());
   }

   public Optional<CampfireCookingRecipe> getCookableRecipe(ItemStack var1) {
      return this.items.stream().noneMatch(ItemStack::isEmpty)
         ? Optional.empty()
         : this.level.getRecipeManager().getRecipeFor(RecipeType.CAMPFIRE_COOKING, new SimpleContainer(â˜ƒ), this.level);
   }

   public boolean placeFood(ItemStack var1, int var2) {
      for(int â˜ƒ = 0; â˜ƒ < this.items.size(); ++â˜ƒ) {
         ItemStack â˜ƒx = this.items.get(â˜ƒ);
         if (â˜ƒx.isEmpty()) {
            this.cookingTime[â˜ƒ] = â˜ƒ;
            this.cookingProgress[â˜ƒ] = 0;
            this.items.set(â˜ƒ, â˜ƒ.split(1));
            this.markUpdated();
            return true;
         }
      }

      return false;
   }

   private void markUpdated() {
      this.setChanged();
      this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
   }

   @Override
   public void clearContent() {
      this.items.clear();
   }

   public void dowse() {
      if (this.level != null) {
         this.markUpdated();
      }
   }
}
