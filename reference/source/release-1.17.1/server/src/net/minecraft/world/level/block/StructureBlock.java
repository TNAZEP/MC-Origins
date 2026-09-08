package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.phys.BlockHitResult;

public class StructureBlock extends BaseEntityBlock implements GameMasterBlock {
   public static final EnumProperty<StructureMode> MODE = BlockStateProperties.STRUCTUREBLOCK_MODE;

   protected StructureBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(MODE, StructureMode.LOAD));
   }

   @Override
   public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
      return new StructureBlockEntity(â˜ƒ, â˜ƒ);
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
      if (â˜ƒ instanceof StructureBlockEntity) {
         return ((StructureBlockEntity)â˜ƒ).usedBy(â˜ƒ) ? InteractionResult.sidedSuccess(â˜ƒ.isClientSide) : InteractionResult.PASS;
      } else {
         return InteractionResult.PASS;
      }
   }

   @Override
   public void setPlacedBy(Level var1, BlockPos var2, BlockState var3, @Nullable LivingEntity var4, ItemStack var5) {
      if (!â˜ƒ.isClientSide) {
         if (â˜ƒ != null) {
            BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
            if (â˜ƒ instanceof StructureBlockEntity) {
               ((StructureBlockEntity)â˜ƒ).createdBy(â˜ƒ);
            }
         }
      }
   }

   @Override
   public RenderShape getRenderShape(BlockState var1) {
      return RenderShape.MODEL;
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(MODE);
   }

   @Override
   public void neighborChanged(BlockState var1, Level var2, BlockPos var3, Block var4, BlockPos var5, boolean var6) {
      if (â˜ƒ instanceof ServerLevel) {
         BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
         if (â˜ƒ instanceof StructureBlockEntity) {
            StructureBlockEntity â˜ƒx = (StructureBlockEntity)â˜ƒ;
            boolean â˜ƒxx = â˜ƒ.hasNeighborSignal(â˜ƒ);
            boolean â˜ƒxxx = â˜ƒx.isPowered();
            if (â˜ƒxx && !â˜ƒxxx) {
               â˜ƒx.setPowered(true);
               this.trigger((ServerLevel)â˜ƒ, â˜ƒx);
            } else if (!â˜ƒxx && â˜ƒxxx) {
               â˜ƒx.setPowered(false);
            }
         }
      }
   }

   private void trigger(ServerLevel var1, StructureBlockEntity var2) {
      switch(â˜ƒ.getMode()) {
         case SAVE:
            â˜ƒ.saveStructure(false);
            break;
         case LOAD:
            â˜ƒ.loadStructure(â˜ƒ, false);
            break;
         case CORNER:
            â˜ƒ.unloadStructure();
         case DATA:
      }
   }
}
