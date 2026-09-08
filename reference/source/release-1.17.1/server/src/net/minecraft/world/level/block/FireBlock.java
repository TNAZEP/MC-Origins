package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FireBlock extends BaseFireBlock {
   public static final int MAX_AGE = 15;
   public static final IntegerProperty AGE = BlockStateProperties.AGE_15;
   public static final BooleanProperty NORTH = PipeBlock.NORTH;
   public static final BooleanProperty EAST = PipeBlock.EAST;
   public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
   public static final BooleanProperty WEST = PipeBlock.WEST;
   public static final BooleanProperty UP = PipeBlock.UP;
   private static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = (Map<Direction, BooleanProperty>)PipeBlock.PROPERTY_BY_DIRECTION
      .entrySet()
      .stream()
      .filter(var0 -> var0.getKey() != Direction.DOWN)
      .collect(Util.toMap());
   private static final VoxelShape UP_AABB = Block.box(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape WEST_AABB = Block.box(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
   private static final VoxelShape EAST_AABB = Block.box(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape NORTH_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
   private static final VoxelShape SOUTH_AABB = Block.box(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
   private final Map<BlockState, VoxelShape> shapesCache;
   private static final int FLAME_INSTANT = 60;
   private static final int FLAME_EASY = 30;
   private static final int FLAME_MEDIUM = 15;
   private static final int FLAME_HARD = 5;
   private static final int BURN_INSTANT = 100;
   private static final int BURN_EASY = 60;
   private static final int BURN_MEDIUM = 20;
   private static final int BURN_HARD = 5;
   private final Object2IntMap<Block> flameOdds = new Object2IntOpenHashMap<>();
   private final Object2IntMap<Block> burnOdds = new Object2IntOpenHashMap<>();

   public FireBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ, 1.0F);
      this.registerDefaultState(
         this.stateDefinition
            .any()
            .setValue(AGE, Integer.valueOf(0))
            .setValue(NORTH, Boolean.valueOf(false))
            .setValue(EAST, Boolean.valueOf(false))
            .setValue(SOUTH, Boolean.valueOf(false))
            .setValue(WEST, Boolean.valueOf(false))
            .setValue(UP, Boolean.valueOf(false))
      );
      this.shapesCache = ImmutableMap.copyOf(
         (Map<? extends BlockState, ? extends VoxelShape>)this.stateDefinition
            .getPossibleStates()
            .stream()
            .filter(var0 -> var0.getValue(AGE) == 0)
            .collect(Collectors.toMap(Function.identity(), FireBlock::calculateShape))
      );
   }

   private static VoxelShape calculateShape(BlockState var0) {
      VoxelShape â˜ƒ = Shapes.empty();
      if (â˜ƒ.getValue(UP)) {
         â˜ƒ = UP_AABB;
      }

      if (â˜ƒ.getValue(NORTH)) {
         â˜ƒ = Shapes.or(â˜ƒ, NORTH_AABB);
      }

      if (â˜ƒ.getValue(SOUTH)) {
         â˜ƒ = Shapes.or(â˜ƒ, SOUTH_AABB);
      }

      if (â˜ƒ.getValue(EAST)) {
         â˜ƒ = Shapes.or(â˜ƒ, EAST_AABB);
      }

      if (â˜ƒ.getValue(WEST)) {
         â˜ƒ = Shapes.or(â˜ƒ, WEST_AABB);
      }

      return â˜ƒ.isEmpty() ? DOWN_AABB : â˜ƒ;
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      return this.canSurvive(â˜ƒ, â˜ƒ, â˜ƒ) ? this.getStateWithAge(â˜ƒ, â˜ƒ, â˜ƒ.getValue(AGE)) : Blocks.AIR.defaultBlockState();
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return (VoxelShape)this.shapesCache.get(â˜ƒ.setValue(AGE, Integer.valueOf(0)));
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      return this.getStateForPlacement(â˜ƒ.getLevel(), â˜ƒ.getClickedPos());
   }

   protected BlockState getStateForPlacement(BlockGetter var1, BlockPos var2) {
      BlockPos â˜ƒ = â˜ƒ.below();
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
      if (!this.canBurn(â˜ƒx) && !â˜ƒx.isFaceSturdy(â˜ƒ, â˜ƒ, Direction.UP)) {
         BlockState â˜ƒxx = this.defaultBlockState();

         for(Direction â˜ƒxxx : Direction.values()) {
            BooleanProperty â˜ƒxxxx = (BooleanProperty)PROPERTY_BY_DIRECTION.get(â˜ƒxxx);
            if (â˜ƒxxxx != null) {
               â˜ƒxx = â˜ƒxx.setValue(â˜ƒxxxx, Boolean.valueOf(this.canBurn(â˜ƒ.getBlockState(â˜ƒ.relative(â˜ƒxxx)))));
            }
         }

         return â˜ƒxx;
      } else {
         return this.defaultBlockState();
      }
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      BlockPos â˜ƒ = â˜ƒ.below();
      return â˜ƒ.getBlockState(â˜ƒ).isFaceSturdy(â˜ƒ, â˜ƒ, Direction.UP) || this.isValidFireLocation(â˜ƒ, â˜ƒ);
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, getFireTickDelay(â˜ƒ.random));
      if (â˜ƒ.getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {
         if (!â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)) {
            â˜ƒ.removeBlock(â˜ƒ, false);
         }

         BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ.below());
         boolean â˜ƒx = â˜ƒ.is(â˜ƒ.dimensionType().infiniburn());
         int â˜ƒxx = â˜ƒ.getValue(AGE);
         if (!â˜ƒx && â˜ƒ.isRaining() && this.isNearRain(â˜ƒ, â˜ƒ) && â˜ƒ.nextFloat() < 0.2F + (float)â˜ƒxx * 0.03F) {
            â˜ƒ.removeBlock(â˜ƒ, false);
         } else {
            int â˜ƒ = Math.min(15, â˜ƒxx + â˜ƒ.nextInt(3) / 2);
            if (â˜ƒxx != â˜ƒ) {
               â˜ƒ = â˜ƒ.setValue(AGE, Integer.valueOf(â˜ƒ));
               â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 4);
            }

            if (!â˜ƒx) {
               if (!this.isValidFireLocation(â˜ƒ, â˜ƒ)) {
                  BlockPos â˜ƒ = â˜ƒ.below();
                  if (!â˜ƒ.getBlockState(â˜ƒ).isFaceSturdy(â˜ƒ, â˜ƒ, Direction.UP) || â˜ƒxx > 3) {
                     â˜ƒ.removeBlock(â˜ƒ, false);
                  }

                  return;
               }

               if (â˜ƒxx == 15 && â˜ƒ.nextInt(4) == 0 && !this.canBurn(â˜ƒ.getBlockState(â˜ƒ.below()))) {
                  â˜ƒ.removeBlock(â˜ƒ, false);
                  return;
               }
            }

            boolean â˜ƒ = â˜ƒ.isHumidAt(â˜ƒ);
            int â˜ƒx = â˜ƒ ? -50 : 0;
            this.checkBurnOut(â˜ƒ, â˜ƒ.east(), 300 + â˜ƒx, â˜ƒ, â˜ƒxx);
            this.checkBurnOut(â˜ƒ, â˜ƒ.west(), 300 + â˜ƒx, â˜ƒ, â˜ƒxx);
            this.checkBurnOut(â˜ƒ, â˜ƒ.below(), 250 + â˜ƒx, â˜ƒ, â˜ƒxx);
            this.checkBurnOut(â˜ƒ, â˜ƒ.above(), 250 + â˜ƒx, â˜ƒ, â˜ƒxx);
            this.checkBurnOut(â˜ƒ, â˜ƒ.north(), 300 + â˜ƒx, â˜ƒ, â˜ƒxx);
            this.checkBurnOut(â˜ƒ, â˜ƒ.south(), 300 + â˜ƒx, â˜ƒ, â˜ƒxx);
            BlockPos.MutableBlockPos â˜ƒxx = new BlockPos.MutableBlockPos();

            for(int â˜ƒxxx = -1; â˜ƒxxx <= 1; ++â˜ƒxxx) {
               for(int â˜ƒxxxx = -1; â˜ƒxxxx <= 1; ++â˜ƒxxxx) {
                  for(int â˜ƒxxxxx = -1; â˜ƒxxxxx <= 4; ++â˜ƒxxxxx) {
                     if (â˜ƒxxx != 0 || â˜ƒxxxxx != 0 || â˜ƒxxxx != 0) {
                        int â˜ƒxxxxxx = 100;
                        if (â˜ƒxxxxx > 1) {
                           â˜ƒxxxxxx += (â˜ƒxxxxx - 1) * 100;
                        }

                        â˜ƒxx.setWithOffset(â˜ƒ, â˜ƒxxx, â˜ƒxxxxx, â˜ƒxxxx);
                        int â˜ƒxxxxxx = this.getFireOdds(â˜ƒ, â˜ƒxx);
                        if (â˜ƒxxxxxx > 0) {
                           int â˜ƒxxxxxxx = (â˜ƒxxxxxx + 40 + â˜ƒ.getDifficulty().getId() * 7) / (â˜ƒxx + 30);
                           if (â˜ƒ) {
                              â˜ƒxxxxxxx /= 2;
                           }

                           if (â˜ƒxxxxxxx > 0 && â˜ƒ.nextInt(â˜ƒxxxxxx) <= â˜ƒxxxxxxx && (!â˜ƒ.isRaining() || !this.isNearRain(â˜ƒ, â˜ƒxx))) {
                              int â˜ƒxxxxxxx = Math.min(15, â˜ƒxx + â˜ƒ.nextInt(5) / 4);
                              â˜ƒ.setBlock(â˜ƒxx, this.getStateWithAge(â˜ƒ, â˜ƒxx, â˜ƒxxxxxxx), 3);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   protected boolean isNearRain(Level var1, BlockPos var2) {
      return â˜ƒ.isRainingAt(â˜ƒ) || â˜ƒ.isRainingAt(â˜ƒ.west()) || â˜ƒ.isRainingAt(â˜ƒ.east()) || â˜ƒ.isRainingAt(â˜ƒ.north()) || â˜ƒ.isRainingAt(â˜ƒ.south());
   }

   private int getBurnOdd(BlockState var1) {
      return â˜ƒ.hasProperty(BlockStateProperties.WATERLOGGED) && â˜ƒ.getValue(BlockStateProperties.WATERLOGGED) ? 0 : this.burnOdds.getInt(â˜ƒ.getBlock());
   }

   private int getFlameOdds(BlockState var1) {
      return â˜ƒ.hasProperty(BlockStateProperties.WATERLOGGED) && â˜ƒ.getValue(BlockStateProperties.WATERLOGGED) ? 0 : this.flameOdds.getInt(â˜ƒ.getBlock());
   }

   private void checkBurnOut(Level var1, BlockPos var2, int var3, Random var4, int var5) {
      int â˜ƒ = this.getBurnOdd(â˜ƒ.getBlockState(â˜ƒ));
      if (â˜ƒ.nextInt(â˜ƒ) < â˜ƒ) {
         BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
         if (â˜ƒ.nextInt(â˜ƒ + 10) < 5 && !â˜ƒ.isRainingAt(â˜ƒ)) {
            int â˜ƒxx = Math.min(â˜ƒ + â˜ƒ.nextInt(5) / 4, 15);
            â˜ƒ.setBlock(â˜ƒ, this.getStateWithAge(â˜ƒ, â˜ƒ, â˜ƒxx), 3);
         } else {
            â˜ƒ.removeBlock(â˜ƒ, false);
         }

         Block â˜ƒx = â˜ƒx.getBlock();
         if (â˜ƒx instanceof TntBlock) {
            TntBlock.explode(â˜ƒ, â˜ƒ);
         }
      }
   }

   private BlockState getStateWithAge(LevelAccessor var1, BlockPos var2, int var3) {
      BlockState â˜ƒ = getState(â˜ƒ, â˜ƒ);
      return â˜ƒ.is(Blocks.FIRE) ? â˜ƒ.setValue(AGE, Integer.valueOf(â˜ƒ)) : â˜ƒ;
   }

   private boolean isValidFireLocation(BlockGetter var1, BlockPos var2) {
      for(Direction â˜ƒ : Direction.values()) {
         if (this.canBurn(â˜ƒ.getBlockState(â˜ƒ.relative(â˜ƒ)))) {
            return true;
         }
      }

      return false;
   }

   private int getFireOdds(LevelReader var1, BlockPos var2) {
      if (!â˜ƒ.isEmptyBlock(â˜ƒ)) {
         return 0;
      } else {
         int â˜ƒ = 0;

         for(Direction â˜ƒx : Direction.values()) {
            BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒ.relative(â˜ƒx));
            â˜ƒ = Math.max(this.getFlameOdds(â˜ƒxx), â˜ƒ);
         }

         return â˜ƒ;
      }
   }

   @Override
   protected boolean canBurn(BlockState var1) {
      return this.getFlameOdds(â˜ƒ) > 0;
   }

   @Override
   public void onPlace(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      super.onPlace(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, getFireTickDelay(â˜ƒ.random));
   }

   private static int getFireTickDelay(Random var0) {
      return 30 + â˜ƒ.nextInt(10);
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(AGE, NORTH, EAST, SOUTH, WEST, UP);
   }

   private void setFlammable(Block var1, int var2, int var3) {
      this.flameOdds.put(â˜ƒ, â˜ƒ);
      this.burnOdds.put(â˜ƒ, â˜ƒ);
   }

   public static void bootStrap() {
      FireBlock â˜ƒ = (FireBlock)Blocks.FIRE;
      â˜ƒ.setFlammable(Blocks.OAK_PLANKS, 5, 20);
      â˜ƒ.setFlammable(Blocks.SPRUCE_PLANKS, 5, 20);
      â˜ƒ.setFlammable(Blocks.BIRCH_PLANKS, 5, 20);
      â˜ƒ.setFlammable(Blocks.JUNGLE_PLANKS, 5, 20);
      â˜ƒ.setFlammable(Blocks.ACACIA_PLANKS, 5, 20);
      â˜ƒ.setFlammable(Blocks.DARK_OAK_PLANKS, 5, 20);
      â˜ƒ.setFlammable(Blocks.OAK_SLAB, 5, 20);
      â˜ƒ.setFlammable(Blocks.SPRUCE_SLAB, 5, 20);
      â˜ƒ.setFlammable(Blocks.BIRCH_SLAB, 5, 20);
      â˜ƒ.setFlammable(Blocks.JUNGLE_SLAB, 5, 20);
      â˜ƒ.setFlammable(Blocks.ACACIA_SLAB, 5, 20);
      â˜ƒ.setFlammable(Blocks.DARK_OAK_SLAB, 5, 20);
      â˜ƒ.setFlammable(Blocks.OAK_FENCE_GATE, 5, 20);
      â˜ƒ.setFlammable(Blocks.SPRUCE_FENCE_GATE, 5, 20);
      â˜ƒ.setFlammable(Blocks.BIRCH_FENCE_GATE, 5, 20);
      â˜ƒ.setFlammable(Blocks.JUNGLE_FENCE_GATE, 5, 20);
      â˜ƒ.setFlammable(Blocks.DARK_OAK_FENCE_GATE, 5, 20);
      â˜ƒ.setFlammable(Blocks.ACACIA_FENCE_GATE, 5, 20);
      â˜ƒ.setFlammable(Blocks.OAK_FENCE, 5, 20);
      â˜ƒ.setFlammable(Blocks.SPRUCE_FENCE, 5, 20);
      â˜ƒ.setFlammable(Blocks.BIRCH_FENCE, 5, 20);
      â˜ƒ.setFlammable(Blocks.JUNGLE_FENCE, 5, 20);
      â˜ƒ.setFlammable(Blocks.DARK_OAK_FENCE, 5, 20);
      â˜ƒ.setFlammable(Blocks.ACACIA_FENCE, 5, 20);
      â˜ƒ.setFlammable(Blocks.OAK_STAIRS, 5, 20);
      â˜ƒ.setFlammable(Blocks.BIRCH_STAIRS, 5, 20);
      â˜ƒ.setFlammable(Blocks.SPRUCE_STAIRS, 5, 20);
      â˜ƒ.setFlammable(Blocks.JUNGLE_STAIRS, 5, 20);
      â˜ƒ.setFlammable(Blocks.ACACIA_STAIRS, 5, 20);
      â˜ƒ.setFlammable(Blocks.DARK_OAK_STAIRS, 5, 20);
      â˜ƒ.setFlammable(Blocks.OAK_LOG, 5, 5);
      â˜ƒ.setFlammable(Blocks.SPRUCE_LOG, 5, 5);
      â˜ƒ.setFlammable(Blocks.BIRCH_LOG, 5, 5);
      â˜ƒ.setFlammable(Blocks.JUNGLE_LOG, 5, 5);
      â˜ƒ.setFlammable(Blocks.ACACIA_LOG, 5, 5);
      â˜ƒ.setFlammable(Blocks.DARK_OAK_LOG, 5, 5);
      â˜ƒ.setFlammable(Blocks.STRIPPED_OAK_LOG, 5, 5);
      â˜ƒ.setFlammable(Blocks.STRIPPED_SPRUCE_LOG, 5, 5);
      â˜ƒ.setFlammable(Blocks.STRIPPED_BIRCH_LOG, 5, 5);
      â˜ƒ.setFlammable(Blocks.STRIPPED_JUNGLE_LOG, 5, 5);
      â˜ƒ.setFlammable(Blocks.STRIPPED_ACACIA_LOG, 5, 5);
      â˜ƒ.setFlammable(Blocks.STRIPPED_DARK_OAK_LOG, 5, 5);
      â˜ƒ.setFlammable(Blocks.STRIPPED_OAK_WOOD, 5, 5);
      â˜ƒ.setFlammable(Blocks.STRIPPED_SPRUCE_WOOD, 5, 5);
      â˜ƒ.setFlammable(Blocks.STRIPPED_BIRCH_WOOD, 5, 5);
      â˜ƒ.setFlammable(Blocks.STRIPPED_JUNGLE_WOOD, 5, 5);
      â˜ƒ.setFlammable(Blocks.STRIPPED_ACACIA_WOOD, 5, 5);
      â˜ƒ.setFlammable(Blocks.STRIPPED_DARK_OAK_WOOD, 5, 5);
      â˜ƒ.setFlammable(Blocks.OAK_WOOD, 5, 5);
      â˜ƒ.setFlammable(Blocks.SPRUCE_WOOD, 5, 5);
      â˜ƒ.setFlammable(Blocks.BIRCH_WOOD, 5, 5);
      â˜ƒ.setFlammable(Blocks.JUNGLE_WOOD, 5, 5);
      â˜ƒ.setFlammable(Blocks.ACACIA_WOOD, 5, 5);
      â˜ƒ.setFlammable(Blocks.DARK_OAK_WOOD, 5, 5);
      â˜ƒ.setFlammable(Blocks.OAK_LEAVES, 30, 60);
      â˜ƒ.setFlammable(Blocks.SPRUCE_LEAVES, 30, 60);
      â˜ƒ.setFlammable(Blocks.BIRCH_LEAVES, 30, 60);
      â˜ƒ.setFlammable(Blocks.JUNGLE_LEAVES, 30, 60);
      â˜ƒ.setFlammable(Blocks.ACACIA_LEAVES, 30, 60);
      â˜ƒ.setFlammable(Blocks.DARK_OAK_LEAVES, 30, 60);
      â˜ƒ.setFlammable(Blocks.BOOKSHELF, 30, 20);
      â˜ƒ.setFlammable(Blocks.TNT, 15, 100);
      â˜ƒ.setFlammable(Blocks.GRASS, 60, 100);
      â˜ƒ.setFlammable(Blocks.FERN, 60, 100);
      â˜ƒ.setFlammable(Blocks.DEAD_BUSH, 60, 100);
      â˜ƒ.setFlammable(Blocks.SUNFLOWER, 60, 100);
      â˜ƒ.setFlammable(Blocks.LILAC, 60, 100);
      â˜ƒ.setFlammable(Blocks.ROSE_BUSH, 60, 100);
      â˜ƒ.setFlammable(Blocks.PEONY, 60, 100);
      â˜ƒ.setFlammable(Blocks.TALL_GRASS, 60, 100);
      â˜ƒ.setFlammable(Blocks.LARGE_FERN, 60, 100);
      â˜ƒ.setFlammable(Blocks.DANDELION, 60, 100);
      â˜ƒ.setFlammable(Blocks.POPPY, 60, 100);
      â˜ƒ.setFlammable(Blocks.BLUE_ORCHID, 60, 100);
      â˜ƒ.setFlammable(Blocks.ALLIUM, 60, 100);
      â˜ƒ.setFlammable(Blocks.AZURE_BLUET, 60, 100);
      â˜ƒ.setFlammable(Blocks.RED_TULIP, 60, 100);
      â˜ƒ.setFlammable(Blocks.ORANGE_TULIP, 60, 100);
      â˜ƒ.setFlammable(Blocks.WHITE_TULIP, 60, 100);
      â˜ƒ.setFlammable(Blocks.PINK_TULIP, 60, 100);
      â˜ƒ.setFlammable(Blocks.OXEYE_DAISY, 60, 100);
      â˜ƒ.setFlammable(Blocks.CORNFLOWER, 60, 100);
      â˜ƒ.setFlammable(Blocks.LILY_OF_THE_VALLEY, 60, 100);
      â˜ƒ.setFlammable(Blocks.WITHER_ROSE, 60, 100);
      â˜ƒ.setFlammable(Blocks.WHITE_WOOL, 30, 60);
      â˜ƒ.setFlammable(Blocks.ORANGE_WOOL, 30, 60);
      â˜ƒ.setFlammable(Blocks.MAGENTA_WOOL, 30, 60);
      â˜ƒ.setFlammable(Blocks.LIGHT_BLUE_WOOL, 30, 60);
      â˜ƒ.setFlammable(Blocks.YELLOW_WOOL, 30, 60);
      â˜ƒ.setFlammable(Blocks.LIME_WOOL, 30, 60);
      â˜ƒ.setFlammable(Blocks.PINK_WOOL, 30, 60);
      â˜ƒ.setFlammable(Blocks.GRAY_WOOL, 30, 60);
      â˜ƒ.setFlammable(Blocks.LIGHT_GRAY_WOOL, 30, 60);
      â˜ƒ.setFlammable(Blocks.CYAN_WOOL, 30, 60);
      â˜ƒ.setFlammable(Blocks.PURPLE_WOOL, 30, 60);
      â˜ƒ.setFlammable(Blocks.BLUE_WOOL, 30, 60);
      â˜ƒ.setFlammable(Blocks.BROWN_WOOL, 30, 60);
      â˜ƒ.setFlammable(Blocks.GREEN_WOOL, 30, 60);
      â˜ƒ.setFlammable(Blocks.RED_WOOL, 30, 60);
      â˜ƒ.setFlammable(Blocks.BLACK_WOOL, 30, 60);
      â˜ƒ.setFlammable(Blocks.VINE, 15, 100);
      â˜ƒ.setFlammable(Blocks.COAL_BLOCK, 5, 5);
      â˜ƒ.setFlammable(Blocks.HAY_BLOCK, 60, 20);
      â˜ƒ.setFlammable(Blocks.TARGET, 15, 20);
      â˜ƒ.setFlammable(Blocks.WHITE_CARPET, 60, 20);
      â˜ƒ.setFlammable(Blocks.ORANGE_CARPET, 60, 20);
      â˜ƒ.setFlammable(Blocks.MAGENTA_CARPET, 60, 20);
      â˜ƒ.setFlammable(Blocks.LIGHT_BLUE_CARPET, 60, 20);
      â˜ƒ.setFlammable(Blocks.YELLOW_CARPET, 60, 20);
      â˜ƒ.setFlammable(Blocks.LIME_CARPET, 60, 20);
      â˜ƒ.setFlammable(Blocks.PINK_CARPET, 60, 20);
      â˜ƒ.setFlammable(Blocks.GRAY_CARPET, 60, 20);
      â˜ƒ.setFlammable(Blocks.LIGHT_GRAY_CARPET, 60, 20);
      â˜ƒ.setFlammable(Blocks.CYAN_CARPET, 60, 20);
      â˜ƒ.setFlammable(Blocks.PURPLE_CARPET, 60, 20);
      â˜ƒ.setFlammable(Blocks.BLUE_CARPET, 60, 20);
      â˜ƒ.setFlammable(Blocks.BROWN_CARPET, 60, 20);
      â˜ƒ.setFlammable(Blocks.GREEN_CARPET, 60, 20);
      â˜ƒ.setFlammable(Blocks.RED_CARPET, 60, 20);
      â˜ƒ.setFlammable(Blocks.BLACK_CARPET, 60, 20);
      â˜ƒ.setFlammable(Blocks.DRIED_KELP_BLOCK, 30, 60);
      â˜ƒ.setFlammable(Blocks.BAMBOO, 60, 60);
      â˜ƒ.setFlammable(Blocks.SCAFFOLDING, 60, 60);
      â˜ƒ.setFlammable(Blocks.LECTERN, 30, 20);
      â˜ƒ.setFlammable(Blocks.COMPOSTER, 5, 20);
      â˜ƒ.setFlammable(Blocks.SWEET_BERRY_BUSH, 60, 100);
      â˜ƒ.setFlammable(Blocks.BEEHIVE, 5, 20);
      â˜ƒ.setFlammable(Blocks.BEE_NEST, 30, 20);
      â˜ƒ.setFlammable(Blocks.AZALEA_LEAVES, 30, 60);
      â˜ƒ.setFlammable(Blocks.FLOWERING_AZALEA_LEAVES, 30, 60);
      â˜ƒ.setFlammable(Blocks.CAVE_VINES, 15, 60);
      â˜ƒ.setFlammable(Blocks.CAVE_VINES_PLANT, 15, 60);
      â˜ƒ.setFlammable(Blocks.SPORE_BLOSSOM, 60, 100);
      â˜ƒ.setFlammable(Blocks.AZALEA, 30, 60);
      â˜ƒ.setFlammable(Blocks.FLOWERING_AZALEA, 30, 60);
      â˜ƒ.setFlammable(Blocks.BIG_DRIPLEAF, 60, 100);
      â˜ƒ.setFlammable(Blocks.BIG_DRIPLEAF_STEM, 60, 100);
      â˜ƒ.setFlammable(Blocks.SMALL_DRIPLEAF, 60, 100);
      â˜ƒ.setFlammable(Blocks.HANGING_ROOTS, 30, 60);
      â˜ƒ.setFlammable(Blocks.GLOW_LICHEN, 15, 100);
   }
}
