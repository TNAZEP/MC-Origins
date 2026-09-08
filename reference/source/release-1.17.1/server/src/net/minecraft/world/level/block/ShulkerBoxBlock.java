package net.minecraft.world.level.block;

import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ShulkerBoxBlock extends BaseEntityBlock {
   public static final EnumProperty<Direction> FACING = DirectionalBlock.FACING;
   public static final ResourceLocation CONTENTS = new ResourceLocation("contents");
   @Nullable
   private final DyeColor color;

   public ShulkerBoxBlock(@Nullable DyeColor var1, BlockBehaviour.Properties var2) {
      super(â˜ƒ);
      this.color = â˜ƒ;
      this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP));
   }

   @Override
   public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
      return new ShulkerBoxBlockEntity(this.color, â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level var1, BlockState var2, BlockEntityType<T> var3) {
      return createTickerHelper(â˜ƒ, BlockEntityType.SHULKER_BOX, ShulkerBoxBlockEntity::tick);
   }

   @Override
   public RenderShape getRenderShape(BlockState var1) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      if (â˜ƒ.isClientSide) {
         return InteractionResult.SUCCESS;
      } else if (â˜ƒ.isSpectator()) {
         return InteractionResult.CONSUME;
      } else {
         BlockEntity â˜ƒx = â˜ƒ.getBlockEntity(â˜ƒ);
         if (â˜ƒx instanceof ShulkerBoxBlockEntity â˜ƒ) {
            if (canOpen(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
               â˜ƒ.openMenu(â˜ƒ);
               â˜ƒ.awardStat(Stats.OPEN_SHULKER_BOX);
               PiglinAi.angerNearbyPiglins(â˜ƒ, true);
            }

            return InteractionResult.CONSUME;
         } else {
            return InteractionResult.PASS;
         }
      }
   }

   private static boolean canOpen(BlockState var0, Level var1, BlockPos var2, ShulkerBoxBlockEntity var3) {
      if (â˜ƒ.getAnimationStatus() != ShulkerBoxBlockEntity.AnimationStatus.CLOSED) {
         return true;
      } else {
         AABB â˜ƒ = Shulker.getProgressDeltaAabb(â˜ƒ.getValue(FACING), 0.0F, 0.5F).move(â˜ƒ).deflate(1.0E-6);
         return â˜ƒ.noCollision(â˜ƒ);
      }
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      return this.defaultBlockState().setValue(FACING, â˜ƒ.getClickedFace());
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(FACING);
   }

   @Override
   public void playerWillDestroy(Level var1, BlockPos var2, BlockState var3, Player var4) {
      BlockEntity â˜ƒx = â˜ƒ.getBlockEntity(â˜ƒ);
      if (â˜ƒx instanceof ShulkerBoxBlockEntity â˜ƒ) {
         if (!â˜ƒ.isClientSide && â˜ƒ.isCreative() && !â˜ƒ.isEmpty()) {
            ItemStack â˜ƒxx = getColoredItemStack(this.getColor());
            CompoundTag â˜ƒxxx = â˜ƒ.saveToTag(new CompoundTag());
            if (!â˜ƒxxx.isEmpty()) {
               â˜ƒxx.addTagElement("BlockEntityTag", â˜ƒxxx);
            }

            if (â˜ƒ.hasCustomName()) {
               â˜ƒxx.setHoverName(â˜ƒ.getCustomName());
            }

            ItemEntity â˜ƒxx = new ItemEntity(â˜ƒ, (double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY() + 0.5, (double)â˜ƒ.getZ() + 0.5, â˜ƒxx);
            â˜ƒxx.setDefaultPickUpDelay();
            â˜ƒ.addFreshEntity(â˜ƒxx);
         } else {
            â˜ƒ.unpackLootTable(â˜ƒ);
         }
      }

      super.playerWillDestroy(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public List<ItemStack> getDrops(BlockState var1, LootContext.Builder var2) {
      BlockEntity â˜ƒx = â˜ƒ.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
      if (â˜ƒx instanceof ShulkerBoxBlockEntity â˜ƒ) {
         â˜ƒ = â˜ƒ.withDynamicDrop(CONTENTS, (var1x, var2x) -> {
            for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.getContainerSize(); ++â˜ƒ) {
               var2x.accept(â˜ƒ.getItem(â˜ƒ));
            }
         });
      }

      return super.getDrops(â˜ƒ, â˜ƒ);
   }

   @Override
   public void setPlacedBy(Level var1, BlockPos var2, BlockState var3, LivingEntity var4, ItemStack var5) {
      if (â˜ƒ.hasCustomHoverName()) {
         BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
         if (â˜ƒ instanceof ShulkerBoxBlockEntity) {
            ((ShulkerBoxBlockEntity)â˜ƒ).setCustomName(â˜ƒ.getHoverName());
         }
      }
   }

   @Override
   public void onRemove(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ.is(â˜ƒ.getBlock())) {
         BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
         if (â˜ƒ instanceof ShulkerBoxBlockEntity) {
            â˜ƒ.updateNeighbourForOutputSignal(â˜ƒ, â˜ƒ.getBlock());
         }

         super.onRemove(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void appendHoverText(ItemStack var1, @Nullable BlockGetter var2, List<Component> var3, TooltipFlag var4) {
      super.appendHoverText(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      CompoundTag â˜ƒ = â˜ƒ.getTagElement("BlockEntityTag");
      if (â˜ƒ != null) {
         if (â˜ƒ.contains("LootTable", 8)) {
            â˜ƒ.add(new TextComponent("???????"));
         }

         if (â˜ƒ.contains("Items", 9)) {
            NonNullList<ItemStack> â˜ƒx = NonNullList.withSize(27, ItemStack.EMPTY);
            ContainerHelper.loadAllItems(â˜ƒ, â˜ƒx);
            int â˜ƒxx = 0;
            int â˜ƒxxx = 0;

            for(ItemStack â˜ƒxxxx : â˜ƒx) {
               if (!â˜ƒxxxx.isEmpty()) {
                  ++â˜ƒxxx;
                  if (â˜ƒxx <= 4) {
                     ++â˜ƒxx;
                     MutableComponent â˜ƒxxxxx = â˜ƒxxxx.getHoverName().copy();
                     â˜ƒxxxxx.append(" x").append(String.valueOf(â˜ƒxxxx.getCount()));
                     â˜ƒ.add(â˜ƒxxxxx);
                  }
               }
            }

            if (â˜ƒxxx - â˜ƒxx > 0) {
               â˜ƒ.add(new TranslatableComponent("container.shulkerBox.more", â˜ƒxxx - â˜ƒxx).withStyle(ChatFormatting.ITALIC));
            }
         }
      }
   }

   @Override
   public PushReaction getPistonPushReaction(BlockState var1) {
      return PushReaction.DESTROY;
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
      return â˜ƒ instanceof ShulkerBoxBlockEntity ? Shapes.create(((ShulkerBoxBlockEntity)â˜ƒ).getBoundingBox(â˜ƒ)) : Shapes.block();
   }

   @Override
   public boolean hasAnalogOutputSignal(BlockState var1) {
      return true;
   }

   @Override
   public int getAnalogOutputSignal(BlockState var1, Level var2, BlockPos var3) {
      return AbstractContainerMenu.getRedstoneSignalFromContainer((Container)â˜ƒ.getBlockEntity(â˜ƒ));
   }

   @Override
   public ItemStack getCloneItemStack(BlockGetter var1, BlockPos var2, BlockState var3) {
      ItemStack â˜ƒ = super.getCloneItemStack(â˜ƒ, â˜ƒ, â˜ƒ);
      ShulkerBoxBlockEntity â˜ƒx = (ShulkerBoxBlockEntity)â˜ƒ.getBlockEntity(â˜ƒ);
      CompoundTag â˜ƒxx = â˜ƒx.saveToTag(new CompoundTag());
      if (!â˜ƒxx.isEmpty()) {
         â˜ƒ.addTagElement("BlockEntityTag", â˜ƒxx);
      }

      return â˜ƒ;
   }

   @Nullable
   public static DyeColor getColorFromItem(Item var0) {
      return getColorFromBlock(Block.byItem(â˜ƒ));
   }

   @Nullable
   public static DyeColor getColorFromBlock(Block var0) {
      return â˜ƒ instanceof ShulkerBoxBlock ? ((ShulkerBoxBlock)â˜ƒ).getColor() : null;
   }

   public static Block getBlockByColor(@Nullable DyeColor var0) {
      if (â˜ƒ == null) {
         return Blocks.SHULKER_BOX;
      } else {
         switch(â˜ƒ) {
            case WHITE:
               return Blocks.WHITE_SHULKER_BOX;
            case ORANGE:
               return Blocks.ORANGE_SHULKER_BOX;
            case MAGENTA:
               return Blocks.MAGENTA_SHULKER_BOX;
            case LIGHT_BLUE:
               return Blocks.LIGHT_BLUE_SHULKER_BOX;
            case YELLOW:
               return Blocks.YELLOW_SHULKER_BOX;
            case LIME:
               return Blocks.LIME_SHULKER_BOX;
            case PINK:
               return Blocks.PINK_SHULKER_BOX;
            case GRAY:
               return Blocks.GRAY_SHULKER_BOX;
            case LIGHT_GRAY:
               return Blocks.LIGHT_GRAY_SHULKER_BOX;
            case CYAN:
               return Blocks.CYAN_SHULKER_BOX;
            case PURPLE:
            default:
               return Blocks.PURPLE_SHULKER_BOX;
            case BLUE:
               return Blocks.BLUE_SHULKER_BOX;
            case BROWN:
               return Blocks.BROWN_SHULKER_BOX;
            case GREEN:
               return Blocks.GREEN_SHULKER_BOX;
            case RED:
               return Blocks.RED_SHULKER_BOX;
            case BLACK:
               return Blocks.BLACK_SHULKER_BOX;
         }
      }
   }

   @Nullable
   public DyeColor getColor() {
      return this.color;
   }

   public static ItemStack getColoredItemStack(@Nullable DyeColor var0) {
      return new ItemStack(getBlockByColor(â˜ƒ));
   }

   @Override
   public BlockState rotate(BlockState var1, Rotation var2) {
      return â˜ƒ.setValue(FACING, â˜ƒ.rotate(â˜ƒ.getValue(FACING)));
   }

   @Override
   public BlockState mirror(BlockState var1, Mirror var2) {
      return â˜ƒ.rotate(â˜ƒ.getRotation(â˜ƒ.getValue(FACING)));
   }
}
