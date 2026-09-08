package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.BlockHitResult;

public class JigsawBlock extends Block implements EntityBlock, GameMasterBlock {
   public static final EnumProperty<FrontAndTop> ORIENTATION = BlockStateProperties.ORIENTATION;

   protected JigsawBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(ORIENTATION, FrontAndTop.NORTH_UP));
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(ORIENTATION);
   }

   @Override
   public BlockState rotate(BlockState var1, Rotation var2) {
      return â˜ƒ.setValue(ORIENTATION, â˜ƒ.rotation().rotate(â˜ƒ.getValue(ORIENTATION)));
   }

   @Override
   public BlockState mirror(BlockState var1, Mirror var2) {
      return â˜ƒ.setValue(ORIENTATION, â˜ƒ.rotation().rotate(â˜ƒ.getValue(ORIENTATION)));
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      Direction â˜ƒx = â˜ƒ.getClickedFace();
      Direction â˜ƒ;
      if (â˜ƒx.getAxis() == Direction.Axis.Y) {
         â˜ƒ = â˜ƒ.getHorizontalDirection().getOpposite();
      } else {
         â˜ƒ = Direction.UP;
      }

      return this.defaultBlockState().setValue(ORIENTATION, FrontAndTop.fromFrontAndTop(â˜ƒx, â˜ƒ));
   }

   @Override
   public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
      return new JigsawBlockEntity(â˜ƒ, â˜ƒ);
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
      if (â˜ƒ instanceof JigsawBlockEntity && â˜ƒ.canUseGameMasterBlocks()) {
         â˜ƒ.openJigsawBlock((JigsawBlockEntity)â˜ƒ);
         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      } else {
         return InteractionResult.PASS;
      }
   }

   public static boolean canAttach(StructureTemplate.StructureBlockInfo var0, StructureTemplate.StructureBlockInfo var1) {
      Direction â˜ƒ = getFrontFacing(â˜ƒ.state);
      Direction â˜ƒx = getFrontFacing(â˜ƒ.state);
      Direction â˜ƒxx = getTopFacing(â˜ƒ.state);
      Direction â˜ƒxxx = getTopFacing(â˜ƒ.state);
      JigsawBlockEntity.JointType â˜ƒxxxx = (JigsawBlockEntity.JointType)JigsawBlockEntity.JointType.byName(â˜ƒ.nbt.getString("joint"))
         .orElseGet(() -> â˜ƒ.getAxis().isHorizontal() ? JigsawBlockEntity.JointType.ALIGNED : JigsawBlockEntity.JointType.ROLLABLE);
      boolean â˜ƒxxxxx = â˜ƒxxxx == JigsawBlockEntity.JointType.ROLLABLE;
      return â˜ƒ == â˜ƒx.getOpposite() && (â˜ƒxxxxx || â˜ƒxx == â˜ƒxxx) && â˜ƒ.nbt.getString("target").equals(â˜ƒ.nbt.getString("name"));
   }

   public static Direction getFrontFacing(BlockState var0) {
      return ((FrontAndTop)â˜ƒ.getValue(ORIENTATION)).front();
   }

   public static Direction getTopFacing(BlockState var0) {
      return ((FrontAndTop)â˜ƒ.getValue(ORIENTATION)).top();
   }
}
