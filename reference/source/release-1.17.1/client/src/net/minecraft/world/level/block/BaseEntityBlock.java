package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BaseEntityBlock extends Block implements EntityBlock {
   protected BaseEntityBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public RenderShape getRenderShape(BlockState var1) {
      return RenderShape.INVISIBLE;
   }

   @Override
   public boolean triggerEvent(BlockState var1, Level var2, BlockPos var3, int var4, int var5) {
      super.triggerEvent(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
      return â˜ƒ == null ? false : â˜ƒ.triggerEvent(â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   public MenuProvider getMenuProvider(BlockState var1, Level var2, BlockPos var3) {
      BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
      return â˜ƒ instanceof MenuProvider ? (MenuProvider)â˜ƒ : null;
   }

   @Nullable
   protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(
      BlockEntityType<A> var0, BlockEntityType<E> var1, BlockEntityTicker<? super E> var2
   ) {
      return â˜ƒ == â˜ƒ ? â˜ƒ : null;
   }
}
