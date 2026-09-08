package net.minecraft.world.level.block;

import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.WorldlyContainerHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ComposterBlock extends Block implements WorldlyContainerHolder {
   public static final int READY = 8;
   public static final int MIN_LEVEL = 0;
   public static final int MAX_LEVEL = 7;
   public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_COMPOSTER;
   public static final Object2FloatMap<ItemLike> COMPOSTABLES = new Object2FloatOpenHashMap<>();
   private static final int AABB_SIDE_THICKNESS = 2;
   private static final VoxelShape OUTER_SHAPE = Shapes.block();
   private static final VoxelShape[] SHAPES = Util.make(new VoxelShape[9], var0 -> {
      for(int â˜ƒ = 0; â˜ƒ < 8; ++â˜ƒ) {
         var0[â˜ƒ] = Shapes.join(OUTER_SHAPE, Block.box(2.0, (double)Math.max(2, 1 + â˜ƒ * 2), 2.0, 14.0, 16.0, 14.0), BooleanOp.ONLY_FIRST);
      }

      var0[8] = var0[7];
   });

   public static void bootStrap() {
      COMPOSTABLES.defaultReturnValue(-1.0F);
      float â˜ƒ = 0.3F;
      float â˜ƒx = 0.5F;
      float â˜ƒxx = 0.65F;
      float â˜ƒxxx = 0.85F;
      float â˜ƒxxxx = 1.0F;
      add(0.3F, Items.JUNGLE_LEAVES);
      add(0.3F, Items.OAK_LEAVES);
      add(0.3F, Items.SPRUCE_LEAVES);
      add(0.3F, Items.DARK_OAK_LEAVES);
      add(0.3F, Items.ACACIA_LEAVES);
      add(0.3F, Items.BIRCH_LEAVES);
      add(0.3F, Items.AZALEA_LEAVES);
      add(0.3F, Items.OAK_SAPLING);
      add(0.3F, Items.SPRUCE_SAPLING);
      add(0.3F, Items.BIRCH_SAPLING);
      add(0.3F, Items.JUNGLE_SAPLING);
      add(0.3F, Items.ACACIA_SAPLING);
      add(0.3F, Items.DARK_OAK_SAPLING);
      add(0.3F, Items.BEETROOT_SEEDS);
      add(0.3F, Items.DRIED_KELP);
      add(0.3F, Items.GRASS);
      add(0.3F, Items.KELP);
      add(0.3F, Items.MELON_SEEDS);
      add(0.3F, Items.PUMPKIN_SEEDS);
      add(0.3F, Items.SEAGRASS);
      add(0.3F, Items.SWEET_BERRIES);
      add(0.3F, Items.GLOW_BERRIES);
      add(0.3F, Items.WHEAT_SEEDS);
      add(0.3F, Items.MOSS_CARPET);
      add(0.3F, Items.SMALL_DRIPLEAF);
      add(0.3F, Items.HANGING_ROOTS);
      add(0.5F, Items.DRIED_KELP_BLOCK);
      add(0.5F, Items.TALL_GRASS);
      add(0.5F, Items.AZALEA_LEAVES_FLOWERS);
      add(0.5F, Items.CACTUS);
      add(0.5F, Items.SUGAR_CANE);
      add(0.5F, Items.VINE);
      add(0.5F, Items.NETHER_SPROUTS);
      add(0.5F, Items.WEEPING_VINES);
      add(0.5F, Items.TWISTING_VINES);
      add(0.5F, Items.MELON_SLICE);
      add(0.5F, Items.GLOW_LICHEN);
      add(0.65F, Items.SEA_PICKLE);
      add(0.65F, Items.LILY_PAD);
      add(0.65F, Items.PUMPKIN);
      add(0.65F, Items.CARVED_PUMPKIN);
      add(0.65F, Items.MELON);
      add(0.65F, Items.APPLE);
      add(0.65F, Items.BEETROOT);
      add(0.65F, Items.CARROT);
      add(0.65F, Items.COCOA_BEANS);
      add(0.65F, Items.POTATO);
      add(0.65F, Items.WHEAT);
      add(0.65F, Items.BROWN_MUSHROOM);
      add(0.65F, Items.RED_MUSHROOM);
      add(0.65F, Items.MUSHROOM_STEM);
      add(0.65F, Items.CRIMSON_FUNGUS);
      add(0.65F, Items.WARPED_FUNGUS);
      add(0.65F, Items.NETHER_WART);
      add(0.65F, Items.CRIMSON_ROOTS);
      add(0.65F, Items.WARPED_ROOTS);
      add(0.65F, Items.SHROOMLIGHT);
      add(0.65F, Items.DANDELION);
      add(0.65F, Items.POPPY);
      add(0.65F, Items.BLUE_ORCHID);
      add(0.65F, Items.ALLIUM);
      add(0.65F, Items.AZURE_BLUET);
      add(0.65F, Items.RED_TULIP);
      add(0.65F, Items.ORANGE_TULIP);
      add(0.65F, Items.WHITE_TULIP);
      add(0.65F, Items.PINK_TULIP);
      add(0.65F, Items.OXEYE_DAISY);
      add(0.65F, Items.CORNFLOWER);
      add(0.65F, Items.LILY_OF_THE_VALLEY);
      add(0.65F, Items.WITHER_ROSE);
      add(0.65F, Items.FERN);
      add(0.65F, Items.SUNFLOWER);
      add(0.65F, Items.LILAC);
      add(0.65F, Items.ROSE_BUSH);
      add(0.65F, Items.PEONY);
      add(0.65F, Items.LARGE_FERN);
      add(0.65F, Items.SPORE_BLOSSOM);
      add(0.65F, Items.AZALEA);
      add(0.65F, Items.MOSS_BLOCK);
      add(0.65F, Items.BIG_DRIPLEAF);
      add(0.85F, Items.HAY_BLOCK);
      add(0.85F, Items.BROWN_MUSHROOM_BLOCK);
      add(0.85F, Items.RED_MUSHROOM_BLOCK);
      add(0.85F, Items.NETHER_WART_BLOCK);
      add(0.85F, Items.WARPED_WART_BLOCK);
      add(0.85F, Items.FLOWERING_AZALEA);
      add(0.85F, Items.BREAD);
      add(0.85F, Items.BAKED_POTATO);
      add(0.85F, Items.COOKIE);
      add(1.0F, Items.CAKE);
      add(1.0F, Items.PUMPKIN_PIE);
   }

   private static void add(float var0, ItemLike var1) {
      COMPOSTABLES.put(â˜ƒ.asItem(), â˜ƒ);
   }

   public ComposterBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, Integer.valueOf(0)));
   }

   public static void handleFill(Level var0, BlockPos var1, boolean var2) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      â˜ƒ.playLocalSound(
         (double)â˜ƒ.getX(),
         (double)â˜ƒ.getY(),
         (double)â˜ƒ.getZ(),
         â˜ƒ ? SoundEvents.COMPOSTER_FILL_SUCCESS : SoundEvents.COMPOSTER_FILL,
         SoundSource.BLOCKS,
         1.0F,
         1.0F,
         false
      );
      double â˜ƒx = â˜ƒ.getShape(â˜ƒ, â˜ƒ).max(Direction.Axis.Y, 0.5, 0.5) + 0.03125;
      double â˜ƒxx = 0.13125F;
      double â˜ƒxxx = 0.7375F;
      Random â˜ƒxxxx = â˜ƒ.getRandom();

      for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < 10; ++â˜ƒxxxxx) {
         double â˜ƒxxxxxx = â˜ƒxxxx.nextGaussian() * 0.02;
         double â˜ƒxxxxxxx = â˜ƒxxxx.nextGaussian() * 0.02;
         double â˜ƒxxxxxxxx = â˜ƒxxxx.nextGaussian() * 0.02;
         â˜ƒ.addParticle(
            ParticleTypes.COMPOSTER,
            (double)â˜ƒ.getX() + 0.13125F + 0.7375F * (double)â˜ƒxxxx.nextFloat(),
            (double)â˜ƒ.getY() + â˜ƒx + (double)â˜ƒxxxx.nextFloat() * (1.0 - â˜ƒx),
            (double)â˜ƒ.getZ() + 0.13125F + 0.7375F * (double)â˜ƒxxxx.nextFloat(),
            â˜ƒxxxxxx,
            â˜ƒxxxxxxx,
            â˜ƒxxxxxxxx
         );
      }
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return SHAPES[â˜ƒ.getValue(LEVEL)];
   }

   @Override
   public VoxelShape getInteractionShape(BlockState var1, BlockGetter var2, BlockPos var3) {
      return OUTER_SHAPE;
   }

   @Override
   public VoxelShape getCollisionShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return SHAPES[0];
   }

   @Override
   public void onPlace(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (â˜ƒ.getValue(LEVEL) == 7) {
         â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, â˜ƒ.getBlock(), 20);
      }
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      int â˜ƒ = â˜ƒ.getValue(LEVEL);
      ItemStack â˜ƒx = â˜ƒ.getItemInHand(â˜ƒ);
      if (â˜ƒ < 8 && COMPOSTABLES.containsKey(â˜ƒx.getItem())) {
         if (â˜ƒ < 7 && !â˜ƒ.isClientSide) {
            BlockState â˜ƒxx = addItem(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx);
            â˜ƒ.levelEvent(1500, â˜ƒ, â˜ƒ != â˜ƒxx ? 1 : 0);
            â˜ƒ.awardStat(Stats.ITEM_USED.get(â˜ƒx.getItem()));
            if (!â˜ƒ.getAbilities().instabuild) {
               â˜ƒx.shrink(1);
            }
         }

         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      } else if (â˜ƒ == 8) {
         extractProduce(â˜ƒ, â˜ƒ, â˜ƒ);
         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      } else {
         return InteractionResult.PASS;
      }
   }

   public static BlockState insertItem(BlockState var0, ServerLevel var1, ItemStack var2, BlockPos var3) {
      int â˜ƒ = â˜ƒ.getValue(LEVEL);
      if (â˜ƒ < 7 && COMPOSTABLES.containsKey(â˜ƒ.getItem())) {
         BlockState â˜ƒx = addItem(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.shrink(1);
         return â˜ƒx;
      } else {
         return â˜ƒ;
      }
   }

   public static BlockState extractProduce(BlockState var0, Level var1, BlockPos var2) {
      if (!â˜ƒ.isClientSide) {
         float â˜ƒ = 0.7F;
         double â˜ƒx = (double)(â˜ƒ.random.nextFloat() * 0.7F) + 0.15F;
         double â˜ƒxx = (double)(â˜ƒ.random.nextFloat() * 0.7F) + 0.060000002F + 0.6;
         double â˜ƒxxx = (double)(â˜ƒ.random.nextFloat() * 0.7F) + 0.15F;
         ItemEntity â˜ƒxxxx = new ItemEntity(
            â˜ƒ, (double)â˜ƒ.getX() + â˜ƒx, (double)â˜ƒ.getY() + â˜ƒxx, (double)â˜ƒ.getZ() + â˜ƒxxx, new ItemStack(Items.BONE_MEAL)
         );
         â˜ƒxxxx.setDefaultPickUpDelay();
         â˜ƒ.addFreshEntity(â˜ƒxxxx);
      }

      BlockState â˜ƒ = empty(â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.playSound(null, â˜ƒ, SoundEvents.COMPOSTER_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
      return â˜ƒ;
   }

   static BlockState empty(BlockState var0, LevelAccessor var1, BlockPos var2) {
      BlockState â˜ƒ = â˜ƒ.setValue(LEVEL, Integer.valueOf(0));
      â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 3);
      return â˜ƒ;
   }

   static BlockState addItem(BlockState var0, LevelAccessor var1, BlockPos var2, ItemStack var3) {
      int â˜ƒ = â˜ƒ.getValue(LEVEL);
      float â˜ƒx = COMPOSTABLES.getFloat(â˜ƒ.getItem());
      if ((â˜ƒ != 0 || !(â˜ƒx > 0.0F)) && !(â˜ƒ.getRandom().nextDouble() < (double)â˜ƒx)) {
         return â˜ƒ;
      } else {
         int â˜ƒ = â˜ƒ + 1;
         BlockState â˜ƒx = â˜ƒ.setValue(LEVEL, Integer.valueOf(â˜ƒ));
         â˜ƒ.setBlock(â˜ƒ, â˜ƒx, 3);
         if (â˜ƒ == 7) {
            â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, â˜ƒ.getBlock(), 20);
         }

         return â˜ƒx;
      }
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (â˜ƒ.getValue(LEVEL) == 7) {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.cycle(LEVEL), 3);
         â˜ƒ.playSound(null, â˜ƒ, SoundEvents.COMPOSTER_READY, SoundSource.BLOCKS, 1.0F, 1.0F);
      }
   }

   @Override
   public boolean hasAnalogOutputSignal(BlockState var1) {
      return true;
   }

   @Override
   public int getAnalogOutputSignal(BlockState var1, Level var2, BlockPos var3) {
      return â˜ƒ.getValue(LEVEL);
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(LEVEL);
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      return false;
   }

   @Override
   public WorldlyContainer getContainer(BlockState var1, LevelAccessor var2, BlockPos var3) {
      int â˜ƒ = â˜ƒ.getValue(LEVEL);
      if (â˜ƒ == 8) {
         return new ComposterBlock.OutputContainer(â˜ƒ, â˜ƒ, â˜ƒ, new ItemStack(Items.BONE_MEAL));
      } else {
         return (WorldlyContainer)(â˜ƒ < 7 ? new ComposterBlock.InputContainer(â˜ƒ, â˜ƒ, â˜ƒ) : new ComposterBlock.EmptyContainer());
      }
   }

   static class EmptyContainer extends SimpleContainer implements WorldlyContainer {
      public EmptyContainer() {
         super(0);
      }

      @Override
      public int[] getSlotsForFace(Direction var1) {
         return new int[0];
      }

      @Override
      public boolean canPlaceItemThroughFace(int var1, ItemStack var2, @Nullable Direction var3) {
         return false;
      }

      @Override
      public boolean canTakeItemThroughFace(int var1, ItemStack var2, Direction var3) {
         return false;
      }
   }

   static class InputContainer extends SimpleContainer implements WorldlyContainer {
      private final BlockState state;
      private final LevelAccessor level;
      private final BlockPos pos;
      private boolean changed;

      public InputContainer(BlockState var1, LevelAccessor var2, BlockPos var3) {
         super(1);
         this.state = â˜ƒ;
         this.level = â˜ƒ;
         this.pos = â˜ƒ;
      }

      @Override
      public int getMaxStackSize() {
         return 1;
      }

      @Override
      public int[] getSlotsForFace(Direction var1) {
         return â˜ƒ == Direction.UP ? new int[]{0} : new int[0];
      }

      @Override
      public boolean canPlaceItemThroughFace(int var1, ItemStack var2, @Nullable Direction var3) {
         return !this.changed && â˜ƒ == Direction.UP && ComposterBlock.COMPOSTABLES.containsKey(â˜ƒ.getItem());
      }

      @Override
      public boolean canTakeItemThroughFace(int var1, ItemStack var2, Direction var3) {
         return false;
      }

      @Override
      public void setChanged() {
         ItemStack â˜ƒ = this.getItem(0);
         if (!â˜ƒ.isEmpty()) {
            this.changed = true;
            BlockState â˜ƒx = ComposterBlock.addItem(this.state, this.level, this.pos, â˜ƒ);
            this.level.levelEvent(1500, this.pos, â˜ƒx != this.state ? 1 : 0);
            this.removeItemNoUpdate(0);
         }
      }
   }

   static class OutputContainer extends SimpleContainer implements WorldlyContainer {
      private final BlockState state;
      private final LevelAccessor level;
      private final BlockPos pos;
      private boolean changed;

      public OutputContainer(BlockState var1, LevelAccessor var2, BlockPos var3, ItemStack var4) {
         super(â˜ƒ);
         this.state = â˜ƒ;
         this.level = â˜ƒ;
         this.pos = â˜ƒ;
      }

      @Override
      public int getMaxStackSize() {
         return 1;
      }

      @Override
      public int[] getSlotsForFace(Direction var1) {
         return â˜ƒ == Direction.DOWN ? new int[]{0} : new int[0];
      }

      @Override
      public boolean canPlaceItemThroughFace(int var1, ItemStack var2, @Nullable Direction var3) {
         return false;
      }

      @Override
      public boolean canTakeItemThroughFace(int var1, ItemStack var2, Direction var3) {
         return !this.changed && â˜ƒ == Direction.DOWN && â˜ƒ.is(Items.BONE_MEAL);
      }

      @Override
      public void setChanged() {
         ComposterBlock.empty(this.state, this.level, this.pos);
         this.changed = true;
      }
   }
}
