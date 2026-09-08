package net.minecraft.world.level.block;

import java.util.Map;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class AbstractCauldronBlock extends Block {
   private static final int SIDE_THICKNESS = 2;
   private static final int LEG_WIDTH = 4;
   private static final int LEG_HEIGHT = 3;
   private static final int LEG_DEPTH = 2;
   protected static final int FLOOR_LEVEL = 4;
   private static final VoxelShape INSIDE = box(2.0, 4.0, 2.0, 14.0, 16.0, 14.0);
   protected static final VoxelShape SHAPE = Shapes.join(
      Shapes.block(),
      Shapes.or(box(0.0, 0.0, 4.0, 16.0, 3.0, 12.0), box(4.0, 0.0, 0.0, 12.0, 3.0, 16.0), box(2.0, 0.0, 2.0, 14.0, 3.0, 14.0), INSIDE),
      BooleanOp.ONLY_FIRST
   );
   private final Map<Item, CauldronInteraction> interactions;

   public AbstractCauldronBlock(BlockBehaviour.Properties var1, Map<Item, CauldronInteraction> var2) {
      super(â˜ƒ);
      this.interactions = â˜ƒ;
   }

   protected double getContentHeight(BlockState var1) {
      return 0.0;
   }

   protected boolean isEntityInsideContent(BlockState var1, BlockPos var2, Entity var3) {
      return â˜ƒ.getY() < (double)â˜ƒ.getY() + this.getContentHeight(â˜ƒ) && â˜ƒ.getBoundingBox().maxY > (double)â˜ƒ.getY() + 0.25;
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      CauldronInteraction â˜ƒx = (CauldronInteraction)this.interactions.get(â˜ƒ.getItem());
      return â˜ƒx.interact(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return SHAPE;
   }

   @Override
   public VoxelShape getInteractionShape(BlockState var1, BlockGetter var2, BlockPos var3) {
      return INSIDE;
   }

   @Override
   public boolean hasAnalogOutputSignal(BlockState var1) {
      return true;
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      return false;
   }

   public abstract boolean isFull(BlockState var1);

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      BlockPos â˜ƒ = PointedDripstoneBlock.findStalactiteTipAboveCauldron(â˜ƒ, â˜ƒ);
      if (â˜ƒ != null) {
         Fluid â˜ƒx = PointedDripstoneBlock.getCauldronFillFluidType(â˜ƒ, â˜ƒ);
         if (â˜ƒx != Fluids.EMPTY && this.canReceiveStalactiteDrip(â˜ƒx)) {
            this.receiveStalactiteDrip(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx);
         }
      }
   }

   protected boolean canReceiveStalactiteDrip(Fluid var1) {
      return false;
   }

   protected void receiveStalactiteDrip(BlockState var1, Level var2, BlockPos var3, Fluid var4) {
   }
}
