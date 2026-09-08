package net.minecraft.world.level.block.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractFurnaceBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeHolder, StackedContentsCompatible {
   protected static final int SLOT_INPUT = 0;
   protected static final int SLOT_FUEL = 1;
   protected static final int SLOT_RESULT = 2;
   public static final int DATA_LIT_TIME = 0;
   private static final int[] SLOTS_FOR_UP = new int[]{0};
   private static final int[] SLOTS_FOR_DOWN = new int[]{2, 1};
   private static final int[] SLOTS_FOR_SIDES = new int[]{1};
   public static final int DATA_LIT_DURATION = 1;
   public static final int DATA_COOKING_PROGRESS = 2;
   public static final int DATA_COOKING_TOTAL_TIME = 3;
   public static final int NUM_DATA_VALUES = 4;
   public static final int BURN_TIME_STANDARD = 200;
   public static final int BURN_COOL_SPEED = 2;
   protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
   int litTime;
   int litDuration;
   int cookingProgress;
   int cookingTotalTime;
   protected final ContainerData dataAccess = new ContainerData() {
      @Override
      public int get(int var1) {
         switch(â˜ƒ) {
            case 0:
               return AbstractFurnaceBlockEntity.this.litTime;
            case 1:
               return AbstractFurnaceBlockEntity.this.litDuration;
            case 2:
               return AbstractFurnaceBlockEntity.this.cookingProgress;
            case 3:
               return AbstractFurnaceBlockEntity.this.cookingTotalTime;
            default:
               return 0;
         }
      }

      @Override
      public void set(int var1, int var2) {
         switch(â˜ƒ) {
            case 0:
               AbstractFurnaceBlockEntity.this.litTime = â˜ƒ;
               break;
            case 1:
               AbstractFurnaceBlockEntity.this.litDuration = â˜ƒ;
               break;
            case 2:
               AbstractFurnaceBlockEntity.this.cookingProgress = â˜ƒ;
               break;
            case 3:
               AbstractFurnaceBlockEntity.this.cookingTotalTime = â˜ƒ;
         }
      }

      @Override
      public int getCount() {
         return 4;
      }
   };
   private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
   private final RecipeType<? extends AbstractCookingRecipe> recipeType;

   protected AbstractFurnaceBlockEntity(BlockEntityType<?> var1, BlockPos var2, BlockState var3, RecipeType<? extends AbstractCookingRecipe> var4) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
      this.recipeType = â˜ƒ;
   }

   public static Map<Item, Integer> getFuel() {
      Map<Item, Integer> â˜ƒ = Maps.newLinkedHashMap();
      add(â˜ƒ, Items.LAVA_BUCKET, 20000);
      add(â˜ƒ, Blocks.COAL_BLOCK, 16000);
      add(â˜ƒ, Items.BLAZE_ROD, 2400);
      add(â˜ƒ, Items.COAL, 1600);
      add(â˜ƒ, Items.CHARCOAL, 1600);
      add(â˜ƒ, ItemTags.LOGS, 300);
      add(â˜ƒ, ItemTags.PLANKS, 300);
      add(â˜ƒ, ItemTags.WOODEN_STAIRS, 300);
      add(â˜ƒ, ItemTags.WOODEN_SLABS, 150);
      add(â˜ƒ, ItemTags.WOODEN_TRAPDOORS, 300);
      add(â˜ƒ, ItemTags.WOODEN_PRESSURE_PLATES, 300);
      add(â˜ƒ, Blocks.OAK_FENCE, 300);
      add(â˜ƒ, Blocks.BIRCH_FENCE, 300);
      add(â˜ƒ, Blocks.SPRUCE_FENCE, 300);
      add(â˜ƒ, Blocks.JUNGLE_FENCE, 300);
      add(â˜ƒ, Blocks.DARK_OAK_FENCE, 300);
      add(â˜ƒ, Blocks.ACACIA_FENCE, 300);
      add(â˜ƒ, Blocks.OAK_FENCE_GATE, 300);
      add(â˜ƒ, Blocks.BIRCH_FENCE_GATE, 300);
      add(â˜ƒ, Blocks.SPRUCE_FENCE_GATE, 300);
      add(â˜ƒ, Blocks.JUNGLE_FENCE_GATE, 300);
      add(â˜ƒ, Blocks.DARK_OAK_FENCE_GATE, 300);
      add(â˜ƒ, Blocks.ACACIA_FENCE_GATE, 300);
      add(â˜ƒ, Blocks.NOTE_BLOCK, 300);
      add(â˜ƒ, Blocks.BOOKSHELF, 300);
      add(â˜ƒ, Blocks.LECTERN, 300);
      add(â˜ƒ, Blocks.JUKEBOX, 300);
      add(â˜ƒ, Blocks.CHEST, 300);
      add(â˜ƒ, Blocks.TRAPPED_CHEST, 300);
      add(â˜ƒ, Blocks.CRAFTING_TABLE, 300);
      add(â˜ƒ, Blocks.DAYLIGHT_DETECTOR, 300);
      add(â˜ƒ, ItemTags.BANNERS, 300);
      add(â˜ƒ, Items.BOW, 300);
      add(â˜ƒ, Items.FISHING_ROD, 300);
      add(â˜ƒ, Blocks.LADDER, 300);
      add(â˜ƒ, ItemTags.SIGNS, 200);
      add(â˜ƒ, Items.WOODEN_SHOVEL, 200);
      add(â˜ƒ, Items.WOODEN_SWORD, 200);
      add(â˜ƒ, Items.WOODEN_HOE, 200);
      add(â˜ƒ, Items.WOODEN_AXE, 200);
      add(â˜ƒ, Items.WOODEN_PICKAXE, 200);
      add(â˜ƒ, ItemTags.WOODEN_DOORS, 200);
      add(â˜ƒ, ItemTags.BOATS, 1200);
      add(â˜ƒ, ItemTags.WOOL, 100);
      add(â˜ƒ, ItemTags.WOODEN_BUTTONS, 100);
      add(â˜ƒ, Items.STICK, 100);
      add(â˜ƒ, ItemTags.SAPLINGS, 100);
      add(â˜ƒ, Items.BOWL, 100);
      add(â˜ƒ, ItemTags.CARPETS, 67);
      add(â˜ƒ, Blocks.DRIED_KELP_BLOCK, 4001);
      add(â˜ƒ, Items.CROSSBOW, 300);
      add(â˜ƒ, Blocks.BAMBOO, 50);
      add(â˜ƒ, Blocks.DEAD_BUSH, 100);
      add(â˜ƒ, Blocks.SCAFFOLDING, 400);
      add(â˜ƒ, Blocks.LOOM, 300);
      add(â˜ƒ, Blocks.BARREL, 300);
      add(â˜ƒ, Blocks.CARTOGRAPHY_TABLE, 300);
      add(â˜ƒ, Blocks.FLETCHING_TABLE, 300);
      add(â˜ƒ, Blocks.SMITHING_TABLE, 300);
      add(â˜ƒ, Blocks.COMPOSTER, 300);
      add(â˜ƒ, Blocks.AZALEA, 100);
      add(â˜ƒ, Blocks.FLOWERING_AZALEA, 100);
      return â˜ƒ;
   }

   private static boolean isNeverAFurnaceFuel(Item var0) {
      return ItemTags.NON_FLAMMABLE_WOOD.contains(â˜ƒ);
   }

   private static void add(Map<Item, Integer> var0, Tag<Item> var1, int var2) {
      for(Item â˜ƒ : â˜ƒ.getValues()) {
         if (!isNeverAFurnaceFuel(â˜ƒ)) {
            â˜ƒ.put(â˜ƒ, â˜ƒ);
         }
      }
   }

   private static void add(Map<Item, Integer> var0, ItemLike var1, int var2) {
      Item â˜ƒ = â˜ƒ.asItem();
      if (isNeverAFurnaceFuel(â˜ƒ)) {
         if (SharedConstants.IS_RUNNING_IN_IDE) {
            throw (IllegalStateException)Util.pauseInIde(
               new IllegalStateException(
                  "A developer tried to explicitly make fire resistant item " + â˜ƒ.getName(null).getString() + " a furnace fuel. That will not work!"
               )
            );
         }
      } else {
         â˜ƒ.put(â˜ƒ, â˜ƒ);
      }
   }

   private boolean isLit() {
      return this.litTime > 0;
   }

   @Override
   public void load(CompoundTag var1) {
      super.load(â˜ƒ);
      this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
      ContainerHelper.loadAllItems(â˜ƒ, this.items);
      this.litTime = â˜ƒ.getShort("BurnTime");
      this.cookingProgress = â˜ƒ.getShort("CookTime");
      this.cookingTotalTime = â˜ƒ.getShort("CookTimeTotal");
      this.litDuration = this.getBurnDuration(this.items.get(1));
      CompoundTag â˜ƒ = â˜ƒ.getCompound("RecipesUsed");

      for(String â˜ƒx : â˜ƒ.getAllKeys()) {
         this.recipesUsed.put(new ResourceLocation(â˜ƒx), â˜ƒ.getInt(â˜ƒx));
      }
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      super.save(â˜ƒ);
      â˜ƒ.putShort("BurnTime", (short)this.litTime);
      â˜ƒ.putShort("CookTime", (short)this.cookingProgress);
      â˜ƒ.putShort("CookTimeTotal", (short)this.cookingTotalTime);
      ContainerHelper.saveAllItems(â˜ƒ, this.items);
      CompoundTag â˜ƒ = new CompoundTag();
      this.recipesUsed.forEach((var1x, var2x) -> â˜ƒ.putInt(var1x.toString(), var2x));
      â˜ƒ.put("RecipesUsed", â˜ƒ);
      return â˜ƒ;
   }

   public static void serverTick(Level var0, BlockPos var1, BlockState var2, AbstractFurnaceBlockEntity var3) {
      boolean â˜ƒ = â˜ƒ.isLit();
      boolean â˜ƒx = false;
      if (â˜ƒ.isLit()) {
         --â˜ƒ.litTime;
      }

      ItemStack â˜ƒ = â˜ƒ.items.get(1);
      if (â˜ƒ.isLit() || !â˜ƒ.isEmpty() && !â˜ƒ.items.get(0).isEmpty()) {
         Recipe<?> â˜ƒx = (Recipe)â˜ƒ.getRecipeManager().getRecipeFor(â˜ƒ.recipeType, â˜ƒ, â˜ƒ).orElse(null);
         int â˜ƒxx = â˜ƒ.getMaxStackSize();
         if (!â˜ƒ.isLit() && canBurn(â˜ƒx, â˜ƒ.items, â˜ƒxx)) {
            â˜ƒ.litTime = â˜ƒ.getBurnDuration(â˜ƒ);
            â˜ƒ.litDuration = â˜ƒ.litTime;
            if (â˜ƒ.isLit()) {
               â˜ƒx = true;
               if (!â˜ƒ.isEmpty()) {
                  Item â˜ƒxxx = â˜ƒ.getItem();
                  â˜ƒ.shrink(1);
                  if (â˜ƒ.isEmpty()) {
                     Item â˜ƒxxxx = â˜ƒxxx.getCraftingRemainingItem();
                     â˜ƒ.items.set(1, â˜ƒxxxx == null ? ItemStack.EMPTY : new ItemStack(â˜ƒxxxx));
                  }
               }
            }
         }

         if (â˜ƒ.isLit() && canBurn(â˜ƒx, â˜ƒ.items, â˜ƒxx)) {
            ++â˜ƒ.cookingProgress;
            if (â˜ƒ.cookingProgress == â˜ƒ.cookingTotalTime) {
               â˜ƒ.cookingProgress = 0;
               â˜ƒ.cookingTotalTime = getTotalCookTime(â˜ƒ, â˜ƒ.recipeType, â˜ƒ);
               if (burn(â˜ƒx, â˜ƒ.items, â˜ƒxx)) {
                  â˜ƒ.setRecipeUsed(â˜ƒx);
               }

               â˜ƒx = true;
            }
         } else {
            â˜ƒ.cookingProgress = 0;
         }
      } else if (!â˜ƒ.isLit() && â˜ƒ.cookingProgress > 0) {
         â˜ƒ.cookingProgress = Mth.clamp(â˜ƒ.cookingProgress - 2, 0, â˜ƒ.cookingTotalTime);
      }

      if (â˜ƒ != â˜ƒ.isLit()) {
         â˜ƒx = true;
         â˜ƒ = â˜ƒ.setValue(AbstractFurnaceBlock.LIT, Boolean.valueOf(â˜ƒ.isLit()));
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 3);
      }

      if (â˜ƒx) {
         setChanged(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private static boolean canBurn(@Nullable Recipe<?> var0, NonNullList<ItemStack> var1, int var2) {
      if (!â˜ƒ.get(0).isEmpty() && â˜ƒ != null) {
         ItemStack â˜ƒ = â˜ƒ.getResultItem();
         if (â˜ƒ.isEmpty()) {
            return false;
         } else {
            ItemStack â˜ƒ = â˜ƒ.get(2);
            if (â˜ƒ.isEmpty()) {
               return true;
            } else if (!â˜ƒ.sameItem(â˜ƒ)) {
               return false;
            } else if (â˜ƒ.getCount() < â˜ƒ && â˜ƒ.getCount() < â˜ƒ.getMaxStackSize()) {
               return true;
            } else {
               return â˜ƒ.getCount() < â˜ƒ.getMaxStackSize();
            }
         }
      } else {
         return false;
      }
   }

   private static boolean burn(@Nullable Recipe<?> var0, NonNullList<ItemStack> var1, int var2) {
      if (â˜ƒ != null && canBurn(â˜ƒ, â˜ƒ, â˜ƒ)) {
         ItemStack â˜ƒ = â˜ƒ.get(0);
         ItemStack â˜ƒx = â˜ƒ.getResultItem();
         ItemStack â˜ƒxx = â˜ƒ.get(2);
         if (â˜ƒxx.isEmpty()) {
            â˜ƒ.set(2, â˜ƒx.copy());
         } else if (â˜ƒxx.is(â˜ƒx.getItem())) {
            â˜ƒxx.grow(1);
         }

         if (â˜ƒ.is(Blocks.WET_SPONGE.asItem()) && !â˜ƒ.get(1).isEmpty() && â˜ƒ.get(1).is(Items.BUCKET)) {
            â˜ƒ.set(1, new ItemStack(Items.WATER_BUCKET));
         }

         â˜ƒ.shrink(1);
         return true;
      } else {
         return false;
      }
   }

   protected int getBurnDuration(ItemStack var1) {
      if (â˜ƒ.isEmpty()) {
         return 0;
      } else {
         Item â˜ƒ = â˜ƒ.getItem();
         return getFuel().getOrDefault(â˜ƒ, 0);
      }
   }

   private static int getTotalCookTime(Level var0, RecipeType<? extends AbstractCookingRecipe> var1, Container var2) {
      return â˜ƒ.getRecipeManager().getRecipeFor(â˜ƒ, â˜ƒ, â˜ƒ).map(AbstractCookingRecipe::getCookingTime).orElse(200);
   }

   public static boolean isFuel(ItemStack var0) {
      return getFuel().containsKey(â˜ƒ.getItem());
   }

   @Override
   public int[] getSlotsForFace(Direction var1) {
      if (â˜ƒ == Direction.DOWN) {
         return SLOTS_FOR_DOWN;
      } else {
         return â˜ƒ == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
      }
   }

   @Override
   public boolean canPlaceItemThroughFace(int var1, ItemStack var2, @Nullable Direction var3) {
      return this.canPlaceItem(â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean canTakeItemThroughFace(int var1, ItemStack var2, Direction var3) {
      if (â˜ƒ == Direction.DOWN && â˜ƒ == 1) {
         return â˜ƒ.is(Items.WATER_BUCKET) || â˜ƒ.is(Items.BUCKET);
      } else {
         return true;
      }
   }

   @Override
   public int getContainerSize() {
      return this.items.size();
   }

   @Override
   public boolean isEmpty() {
      for(ItemStack â˜ƒ : this.items) {
         if (!â˜ƒ.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public ItemStack getItem(int var1) {
      return this.items.get(â˜ƒ);
   }

   @Override
   public ItemStack removeItem(int var1, int var2) {
      return ContainerHelper.removeItem(this.items, â˜ƒ, â˜ƒ);
   }

   @Override
   public ItemStack removeItemNoUpdate(int var1) {
      return ContainerHelper.takeItem(this.items, â˜ƒ);
   }

   @Override
   public void setItem(int var1, ItemStack var2) {
      ItemStack â˜ƒ = this.items.get(â˜ƒ);
      boolean â˜ƒx = !â˜ƒ.isEmpty() && â˜ƒ.sameItem(â˜ƒ) && ItemStack.tagMatches(â˜ƒ, â˜ƒ);
      this.items.set(â˜ƒ, â˜ƒ);
      if (â˜ƒ.getCount() > this.getMaxStackSize()) {
         â˜ƒ.setCount(this.getMaxStackSize());
      }

      if (â˜ƒ == 0 && !â˜ƒx) {
         this.cookingTotalTime = getTotalCookTime(this.level, this.recipeType, this);
         this.cookingProgress = 0;
         this.setChanged();
      }
   }

   @Override
   public boolean stillValid(Player var1) {
      if (this.level.getBlockEntity(this.worldPosition) != this) {
         return false;
      } else {
         return â˜ƒ.distanceToSqr((double)this.worldPosition.getX() + 0.5, (double)this.worldPosition.getY() + 0.5, (double)this.worldPosition.getZ() + 0.5)
            <= 64.0;
      }
   }

   @Override
   public boolean canPlaceItem(int var1, ItemStack var2) {
      if (â˜ƒ == 2) {
         return false;
      } else if (â˜ƒ != 1) {
         return true;
      } else {
         ItemStack â˜ƒ = this.items.get(1);
         return isFuel(â˜ƒ) || â˜ƒ.is(Items.BUCKET) && !â˜ƒ.is(Items.BUCKET);
      }
   }

   @Override
   public void clearContent() {
      this.items.clear();
   }

   @Override
   public void setRecipeUsed(@Nullable Recipe<?> var1) {
      if (â˜ƒ != null) {
         ResourceLocation â˜ƒ = â˜ƒ.getId();
         this.recipesUsed.addTo(â˜ƒ, 1);
      }
   }

   @Nullable
   @Override
   public Recipe<?> getRecipeUsed() {
      return null;
   }

   @Override
   public void awardUsedRecipes(Player var1) {
   }

   public void awardUsedRecipesAndPopExperience(ServerPlayer var1) {
      List<Recipe<?>> â˜ƒ = this.getRecipesToAwardAndPopExperience(â˜ƒ.getLevel(), â˜ƒ.position());
      â˜ƒ.awardRecipes(â˜ƒ);
      this.recipesUsed.clear();
   }

   public List<Recipe<?>> getRecipesToAwardAndPopExperience(ServerLevel var1, Vec3 var2) {
      List<Recipe<?>> â˜ƒ = Lists.<Recipe<?>>newArrayList();

      for(Entry<ResourceLocation> â˜ƒx : this.recipesUsed.object2IntEntrySet()) {
         â˜ƒ.getRecipeManager().byKey((ResourceLocation)â˜ƒx.getKey()).ifPresent(var4 -> {
            â˜ƒ.add(var4);
            createExperience(â˜ƒ, â˜ƒ, â˜ƒ.getIntValue(), ((AbstractCookingRecipe)var4).getExperience());
         });
      }

      return â˜ƒ;
   }

   private static void createExperience(ServerLevel var0, Vec3 var1, int var2, float var3) {
      int â˜ƒ = Mth.floor((float)â˜ƒ * â˜ƒ);
      float â˜ƒx = Mth.frac((float)â˜ƒ * â˜ƒ);
      if (â˜ƒx != 0.0F && Math.random() < (double)â˜ƒx) {
         ++â˜ƒ;
      }

      ExperienceOrb.award(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void fillStackedContents(StackedContents var1) {
      for(ItemStack â˜ƒ : this.items) {
         â˜ƒ.accountStack(â˜ƒ);
      }
   }
}
