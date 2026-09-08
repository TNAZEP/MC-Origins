package net.minecraft.world.item;

import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;

public class BlockItem extends Item {
   public static final String BLOCK_ENTITY_TAG = "BlockEntityTag";
   public static final String BLOCK_STATE_TAG = "BlockStateTag";
   @Deprecated
   private final Block block;

   public BlockItem(Block var1, Item.Properties var2) {
      super(â˜ƒ);
      this.block = â˜ƒ;
   }

   @Override
   public InteractionResult useOn(UseOnContext var1) {
      InteractionResult â˜ƒ = this.place(new BlockPlaceContext(â˜ƒ));
      if (!â˜ƒ.consumesAction() && this.isEdible()) {
         InteractionResult â˜ƒx = this.use(â˜ƒ.getLevel(), â˜ƒ.getPlayer(), â˜ƒ.getHand()).getResult();
         return â˜ƒx == InteractionResult.CONSUME ? InteractionResult.CONSUME_PARTIAL : â˜ƒx;
      } else {
         return â˜ƒ;
      }
   }

   public InteractionResult place(BlockPlaceContext var1) {
      if (!â˜ƒ.canPlace()) {
         return InteractionResult.FAIL;
      } else {
         BlockPlaceContext â˜ƒ = this.updatePlacementContext(â˜ƒ);
         if (â˜ƒ == null) {
            return InteractionResult.FAIL;
         } else {
            BlockState â˜ƒ = this.getPlacementState(â˜ƒ);
            if (â˜ƒ == null) {
               return InteractionResult.FAIL;
            } else if (!this.placeBlock(â˜ƒ, â˜ƒ)) {
               return InteractionResult.FAIL;
            } else {
               BlockPos â˜ƒ = â˜ƒ.getClickedPos();
               Level â˜ƒx = â˜ƒ.getLevel();
               Player â˜ƒxx = â˜ƒ.getPlayer();
               ItemStack â˜ƒxxx = â˜ƒ.getItemInHand();
               BlockState â˜ƒxxxx = â˜ƒx.getBlockState(â˜ƒ);
               if (â˜ƒxxxx.is(â˜ƒ.getBlock())) {
                  â˜ƒxxxx = this.updateBlockStateFromTag(â˜ƒ, â˜ƒx, â˜ƒxxx, â˜ƒxxxx);
                  this.updateCustomBlockEntityTag(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
                  â˜ƒxxxx.getBlock().setPlacedBy(â˜ƒx, â˜ƒ, â˜ƒxxxx, â˜ƒxx, â˜ƒxxx);
                  if (â˜ƒxx instanceof ServerPlayer) {
                     CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)â˜ƒxx, â˜ƒ, â˜ƒxxx);
                  }
               }

               SoundType â˜ƒ = â˜ƒxxxx.getSoundType();
               â˜ƒx.playSound(â˜ƒxx, â˜ƒ, this.getPlaceSound(â˜ƒxxxx), SoundSource.BLOCKS, (â˜ƒ.getVolume() + 1.0F) / 2.0F, â˜ƒ.getPitch() * 0.8F);
               â˜ƒx.gameEvent(â˜ƒxx, GameEvent.BLOCK_PLACE, â˜ƒ);
               if (â˜ƒxx == null || !â˜ƒxx.getAbilities().instabuild) {
                  â˜ƒxxx.shrink(1);
               }

               return InteractionResult.sidedSuccess(â˜ƒx.isClientSide);
            }
         }
      }
   }

   protected SoundEvent getPlaceSound(BlockState var1) {
      return â˜ƒ.getSoundType().getPlaceSound();
   }

   @Nullable
   public BlockPlaceContext updatePlacementContext(BlockPlaceContext var1) {
      return â˜ƒ;
   }

   protected boolean updateCustomBlockEntityTag(BlockPos var1, Level var2, @Nullable Player var3, ItemStack var4, BlockState var5) {
      return updateCustomBlockEntityTag(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Nullable
   protected BlockState getPlacementState(BlockPlaceContext var1) {
      BlockState â˜ƒ = this.getBlock().getStateForPlacement(â˜ƒ);
      return â˜ƒ != null && this.canPlace(â˜ƒ, â˜ƒ) ? â˜ƒ : null;
   }

   private BlockState updateBlockStateFromTag(BlockPos var1, Level var2, ItemStack var3, BlockState var4) {
      BlockState â˜ƒ = â˜ƒ;
      CompoundTag â˜ƒx = â˜ƒ.getTag();
      if (â˜ƒx != null) {
         CompoundTag â˜ƒxx = â˜ƒx.getCompound("BlockStateTag");
         StateDefinition<Block, BlockState> â˜ƒxxx = â˜ƒ.getBlock().getStateDefinition();

         for(String â˜ƒxxxx : â˜ƒxx.getAllKeys()) {
            Property<?> â˜ƒxxxxx = â˜ƒxxx.getProperty(â˜ƒxxxx);
            if (â˜ƒxxxxx != null) {
               String â˜ƒxxxxxx = â˜ƒxx.get(â˜ƒxxxx).getAsString();
               â˜ƒ = updateState(â˜ƒ, â˜ƒxxxxx, â˜ƒxxxxxx);
            }
         }
      }

      if (â˜ƒ != â˜ƒ) {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 2);
      }

      return â˜ƒ;
   }

   private static <T extends Comparable<T>> BlockState updateState(BlockState var0, Property<T> var1, String var2) {
      return (BlockState)â˜ƒ.getValue(â˜ƒ).map(var2x -> â˜ƒ.setValue(â˜ƒ, var2x)).orElse(â˜ƒ);
   }

   protected boolean canPlace(BlockPlaceContext var1, BlockState var2) {
      Player â˜ƒ = â˜ƒ.getPlayer();
      CollisionContext â˜ƒx = â˜ƒ == null ? CollisionContext.empty() : CollisionContext.of(â˜ƒ);
      return (!this.mustSurvive() || â˜ƒ.canSurvive(â˜ƒ.getLevel(), â˜ƒ.getClickedPos())) && â˜ƒ.getLevel().isUnobstructed(â˜ƒ, â˜ƒ.getClickedPos(), â˜ƒx);
   }

   protected boolean mustSurvive() {
      return true;
   }

   protected boolean placeBlock(BlockPlaceContext var1, BlockState var2) {
      return â˜ƒ.getLevel().setBlock(â˜ƒ.getClickedPos(), â˜ƒ, 11);
   }

   public static boolean updateCustomBlockEntityTag(Level var0, @Nullable Player var1, BlockPos var2, ItemStack var3) {
      MinecraftServer â˜ƒ = â˜ƒ.getServer();
      if (â˜ƒ == null) {
         return false;
      } else {
         CompoundTag â˜ƒ = â˜ƒ.getTagElement("BlockEntityTag");
         if (â˜ƒ != null) {
            BlockEntity â˜ƒx = â˜ƒ.getBlockEntity(â˜ƒ);
            if (â˜ƒx != null) {
               if (!â˜ƒ.isClientSide && â˜ƒx.onlyOpCanSetNbt() && (â˜ƒ == null || !â˜ƒ.canUseGameMasterBlocks())) {
                  return false;
               }

               CompoundTag â˜ƒxx = â˜ƒx.save(new CompoundTag());
               CompoundTag â˜ƒxxx = â˜ƒxx.copy();
               â˜ƒxx.merge(â˜ƒ);
               â˜ƒxx.putInt("x", â˜ƒ.getX());
               â˜ƒxx.putInt("y", â˜ƒ.getY());
               â˜ƒxx.putInt("z", â˜ƒ.getZ());
               if (!â˜ƒxx.equals(â˜ƒxxx)) {
                  â˜ƒx.load(â˜ƒxx);
                  â˜ƒx.setChanged();
                  return true;
               }
            }
         }

         return false;
      }
   }

   @Override
   public String getDescriptionId() {
      return this.getBlock().getDescriptionId();
   }

   @Override
   public void fillItemCategory(CreativeModeTab var1, NonNullList<ItemStack> var2) {
      if (this.allowdedIn(â˜ƒ)) {
         this.getBlock().fillItemCategory(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void appendHoverText(ItemStack var1, @Nullable Level var2, List<Component> var3, TooltipFlag var4) {
      super.appendHoverText(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.getBlock().appendHoverText(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public Block getBlock() {
      return this.block;
   }

   public void registerBlocks(Map<Block, Item> var1, Item var2) {
      â˜ƒ.put(this.getBlock(), â˜ƒ);
   }

   @Override
   public boolean canFitInsideContainerItems() {
      return !(this.block instanceof ShulkerBoxBlock);
   }

   @Override
   public void onDestroyed(ItemEntity var1) {
      if (this.block instanceof ShulkerBoxBlock) {
         CompoundTag â˜ƒ = â˜ƒ.getItem().getTag();
         if (â˜ƒ != null) {
            ListTag â˜ƒx = â˜ƒ.getCompound("BlockEntityTag").getList("Items", 10);
            ItemUtils.onContainerDestroyed(â˜ƒ, â˜ƒx.stream().map(CompoundTag.class::cast).map(ItemStack::of));
         }
      }
   }
}
