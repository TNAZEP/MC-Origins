package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EnchantmentTableBlock extends BaseEntityBlock {
   protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);

   protected EnchantmentTableBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean useShapeForLightOcclusion(BlockState var1) {
      return true;
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return SHAPE;
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      super.animateTick(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);

      for(int â˜ƒ = -2; â˜ƒ <= 2; ++â˜ƒ) {
         for(int â˜ƒx = -2; â˜ƒx <= 2; ++â˜ƒx) {
            if (â˜ƒ > -2 && â˜ƒ < 2 && â˜ƒx == -1) {
               â˜ƒx = 2;
            }

            if (â˜ƒ.nextInt(16) == 0) {
               for(int â˜ƒxx = 0; â˜ƒxx <= 1; ++â˜ƒxx) {
                  BlockPos â˜ƒxxx = â˜ƒ.offset(â˜ƒ, â˜ƒxx, â˜ƒx);
                  if (â˜ƒ.getBlockState(â˜ƒxxx).is(Blocks.BOOKSHELF)) {
                     if (!â˜ƒ.isEmptyBlock(â˜ƒ.offset(â˜ƒ / 2, 0, â˜ƒx / 2))) {
                        break;
                     }

                     â˜ƒ.addParticle(
                        ParticleTypes.ENCHANT,
                        (double)â˜ƒ.getX() + 0.5,
                        (double)â˜ƒ.getY() + 2.0,
                        (double)â˜ƒ.getZ() + 0.5,
                        (double)((float)â˜ƒ + â˜ƒ.nextFloat()) - 0.5,
                        (double)((float)â˜ƒxx - â˜ƒ.nextFloat() - 1.0F),
                        (double)((float)â˜ƒx + â˜ƒ.nextFloat()) - 0.5
                     );
                  }
               }
            }
         }
      }
   }

   @Override
   public RenderShape getRenderShape(BlockState var1) {
      return RenderShape.MODEL;
   }

   @Override
   public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
      return new EnchantmentTableBlockEntity(â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level var1, BlockState var2, BlockEntityType<T> var3) {
      return â˜ƒ.isClientSide ? createTickerHelper(â˜ƒ, BlockEntityType.ENCHANTING_TABLE, EnchantmentTableBlockEntity::bookAnimationTick) : null;
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      if (â˜ƒ.isClientSide) {
         return InteractionResult.SUCCESS;
      } else {
         â˜ƒ.openMenu(â˜ƒ.getMenuProvider(â˜ƒ, â˜ƒ));
         return InteractionResult.CONSUME;
      }
   }

   @Nullable
   @Override
   public MenuProvider getMenuProvider(BlockState var1, Level var2, BlockPos var3) {
      BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
      if (â˜ƒ instanceof EnchantmentTableBlockEntity) {
         Component â˜ƒx = ((Nameable)â˜ƒ).getDisplayName();
         return new SimpleMenuProvider((var2x, var3x, var4x) -> new EnchantmentMenu(var2x, var3x, ContainerLevelAccess.create(â˜ƒ, â˜ƒ)), â˜ƒx);
      } else {
         return null;
      }
   }

   @Override
   public void setPlacedBy(Level var1, BlockPos var2, BlockState var3, LivingEntity var4, ItemStack var5) {
      if (â˜ƒ.hasCustomHoverName()) {
         BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
         if (â˜ƒ instanceof EnchantmentTableBlockEntity) {
            ((EnchantmentTableBlockEntity)â˜ƒ).setCustomName(â˜ƒ.getHoverName());
         }
      }
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      return false;
   }
}
