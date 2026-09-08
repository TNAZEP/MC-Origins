package net.minecraft.world.level.material;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMap;
import it.unimi.dsi.fastutil.shorts.Short2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class FlowingFluid extends Fluid {
   public static final BooleanProperty FALLING = BlockStateProperties.FALLING;
   public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_FLOWING;
   private static final int CACHE_SIZE = 200;
   private static final ThreadLocal<Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey>> OCCLUSION_CACHE = ThreadLocal.withInitial(() -> {
      Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey> â˜ƒ = new Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey>(200) {
         @Override
         protected void rehash(int var1) {
         }
      };
      â˜ƒ.defaultReturnValue((byte)127);
      return â˜ƒ;
   });
   private final Map<FluidState, VoxelShape> shapes = Maps.<FluidState, VoxelShape>newIdentityHashMap();

   @Override
   protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> var1) {
      â˜ƒ.add(FALLING);
   }

   @Override
   public Vec3 getFlow(BlockGetter var1, BlockPos var2, FluidState var3) {
      double â˜ƒ = 0.0;
      double â˜ƒx = 0.0;
      BlockPos.MutableBlockPos â˜ƒxx = new BlockPos.MutableBlockPos();

      for(Direction â˜ƒxxx : Direction.Plane.HORIZONTAL) {
         â˜ƒxx.setWithOffset(â˜ƒ, â˜ƒxxx);
         FluidState â˜ƒxxxx = â˜ƒ.getFluidState(â˜ƒxx);
         if (this.affectsFlow(â˜ƒxxxx)) {
            float â˜ƒxxxxx = â˜ƒxxxx.getOwnHeight();
            float â˜ƒxxxxxx = 0.0F;
            if (â˜ƒxxxxx == 0.0F) {
               if (!â˜ƒ.getBlockState(â˜ƒxx).getMaterial().blocksMotion()) {
                  BlockPos â˜ƒxxxxxxx = â˜ƒxx.below();
                  FluidState â˜ƒxxxxxxxx = â˜ƒ.getFluidState(â˜ƒxxxxxxx);
                  if (this.affectsFlow(â˜ƒxxxxxxxx)) {
                     â˜ƒxxxxx = â˜ƒxxxxxxxx.getOwnHeight();
                     if (â˜ƒxxxxx > 0.0F) {
                        â˜ƒxxxxxx = â˜ƒ.getOwnHeight() - (â˜ƒxxxxx - 0.8888889F);
                     }
                  }
               }
            } else if (â˜ƒxxxxx > 0.0F) {
               â˜ƒxxxxxx = â˜ƒ.getOwnHeight() - â˜ƒxxxxx;
            }

            if (â˜ƒxxxxxx != 0.0F) {
               â˜ƒ += (double)((float)â˜ƒxxx.getStepX() * â˜ƒxxxxxx);
               â˜ƒx += (double)((float)â˜ƒxxx.getStepZ() * â˜ƒxxxxxx);
            }
         }
      }

      Vec3 â˜ƒxxx = new Vec3(â˜ƒ, 0.0, â˜ƒx);
      if (â˜ƒ.getValue(FALLING)) {
         for(Direction â˜ƒxxxx : Direction.Plane.HORIZONTAL) {
            â˜ƒxx.setWithOffset(â˜ƒ, â˜ƒxxxx);
            if (this.isSolidFace(â˜ƒ, â˜ƒxx, â˜ƒxxxx) || this.isSolidFace(â˜ƒ, â˜ƒxx.above(), â˜ƒxxxx)) {
               â˜ƒxxx = â˜ƒxxx.normalize().add(0.0, -6.0, 0.0);
               break;
            }
         }
      }

      return â˜ƒxxx.normalize();
   }

   private boolean affectsFlow(FluidState var1) {
      return â˜ƒ.isEmpty() || â˜ƒ.getType().isSame(this);
   }

   protected boolean isSolidFace(BlockGetter var1, BlockPos var2, Direction var3) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      FluidState â˜ƒx = â˜ƒ.getFluidState(â˜ƒ);
      if (â˜ƒx.getType().isSame(this)) {
         return false;
      } else if (â˜ƒ == Direction.UP) {
         return true;
      } else {
         return â˜ƒ.getMaterial() == Material.ICE ? false : â˜ƒ.isFaceSturdy(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   protected void spread(LevelAccessor var1, BlockPos var2, FluidState var3) {
      if (!â˜ƒ.isEmpty()) {
         BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
         BlockPos â˜ƒx = â˜ƒ.below();
         BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
         FluidState â˜ƒxxx = this.getNewLiquid(â˜ƒ, â˜ƒx, â˜ƒxx);
         if (this.canSpreadTo(â˜ƒ, â˜ƒ, â˜ƒ, Direction.DOWN, â˜ƒx, â˜ƒxx, â˜ƒ.getFluidState(â˜ƒx), â˜ƒxxx.getType())) {
            this.spreadTo(â˜ƒ, â˜ƒx, â˜ƒxx, Direction.DOWN, â˜ƒxxx);
            if (this.sourceNeighborCount(â˜ƒ, â˜ƒ) >= 3) {
               this.spreadToSides(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
            }
         } else if (â˜ƒ.isSource() || !this.isWaterHole(â˜ƒ, â˜ƒxxx.getType(), â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx)) {
            this.spreadToSides(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }
   }

   private void spreadToSides(LevelAccessor var1, BlockPos var2, FluidState var3, BlockState var4) {
      int â˜ƒ = â˜ƒ.getAmount() - this.getDropOff(â˜ƒ);
      if (â˜ƒ.getValue(FALLING)) {
         â˜ƒ = 7;
      }

      if (â˜ƒ > 0) {
         Map<Direction, FluidState> â˜ƒ = this.getSpread(â˜ƒ, â˜ƒ, â˜ƒ);

         for(Entry<Direction, FluidState> â˜ƒx : â˜ƒ.entrySet()) {
            Direction â˜ƒxx = (Direction)â˜ƒx.getKey();
            FluidState â˜ƒxxx = (FluidState)â˜ƒx.getValue();
            BlockPos â˜ƒxxxx = â˜ƒ.relative(â˜ƒxx);
            BlockState â˜ƒxxxxx = â˜ƒ.getBlockState(â˜ƒxxxx);
            if (this.canSpreadTo(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒ.getFluidState(â˜ƒxxxx), â˜ƒxxx.getType())) {
               this.spreadTo(â˜ƒ, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxx, â˜ƒxxx);
            }
         }
      }
   }

   protected FluidState getNewLiquid(LevelReader var1, BlockPos var2, BlockState var3) {
      int â˜ƒ = 0;
      int â˜ƒx = 0;

      for(Direction â˜ƒxx : Direction.Plane.HORIZONTAL) {
         BlockPos â˜ƒxxx = â˜ƒ.relative(â˜ƒxx);
         BlockState â˜ƒxxxx = â˜ƒ.getBlockState(â˜ƒxxx);
         FluidState â˜ƒxxxxx = â˜ƒxxxx.getFluidState();
         if (â˜ƒxxxxx.getType().isSame(this) && this.canPassThroughWall(â˜ƒxx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒxxxx)) {
            if (â˜ƒxxxxx.isSource()) {
               ++â˜ƒx;
            }

            â˜ƒ = Math.max(â˜ƒ, â˜ƒxxxxx.getAmount());
         }
      }

      if (this.canConvertToSource() && â˜ƒx >= 2) {
         BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒ.below());
         FluidState â˜ƒxxx = â˜ƒxx.getFluidState();
         if (â˜ƒxx.getMaterial().isSolid() || this.isSourceBlockOfThisType(â˜ƒxxx)) {
            return this.getSource(false);
         }
      }

      BlockPos â˜ƒxx = â˜ƒ.above();
      BlockState â˜ƒxxx = â˜ƒ.getBlockState(â˜ƒxx);
      FluidState â˜ƒxxxx = â˜ƒxxx.getFluidState();
      if (!â˜ƒxxxx.isEmpty() && â˜ƒxxxx.getType().isSame(this) && this.canPassThroughWall(Direction.UP, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒxxx)) {
         return this.getFlowing(8, true);
      } else {
         int â˜ƒxx = â˜ƒ - this.getDropOff(â˜ƒ);
         return â˜ƒxx <= 0 ? Fluids.EMPTY.defaultFluidState() : this.getFlowing(â˜ƒxx, false);
      }
   }

   private boolean canPassThroughWall(Direction var1, BlockGetter var2, BlockPos var3, BlockState var4, BlockPos var5, BlockState var6) {
      Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey> â˜ƒ;
      if (!â˜ƒ.getBlock().hasDynamicShape() && !â˜ƒ.getBlock().hasDynamicShape()) {
         â˜ƒ = (Object2ByteLinkedOpenHashMap)OCCLUSION_CACHE.get();
      } else {
         â˜ƒ = null;
      }

      Block.BlockStatePairKey â˜ƒ;
      if (â˜ƒ != null) {
         â˜ƒ = new Block.BlockStatePairKey(â˜ƒ, â˜ƒ, â˜ƒ);
         byte â˜ƒx = â˜ƒ.getAndMoveToFirst(â˜ƒ);
         if (â˜ƒx != 127) {
            return â˜ƒx != 0;
         }
      } else {
         â˜ƒ = null;
      }

      VoxelShape â˜ƒ = â˜ƒ.getCollisionShape(â˜ƒ, â˜ƒ);
      VoxelShape â˜ƒx = â˜ƒ.getCollisionShape(â˜ƒ, â˜ƒ);
      boolean â˜ƒxx = !Shapes.mergedFaceOccludes(â˜ƒ, â˜ƒx, â˜ƒ);
      if (â˜ƒ != null) {
         if (â˜ƒ.size() == 200) {
            â˜ƒ.removeLastByte();
         }

         â˜ƒ.putAndMoveToFirst(â˜ƒ, (byte)(â˜ƒxx ? 1 : 0));
      }

      return â˜ƒxx;
   }

   public abstract Fluid getFlowing();

   public FluidState getFlowing(int var1, boolean var2) {
      return this.getFlowing().defaultFluidState().setValue(LEVEL, Integer.valueOf(â˜ƒ)).setValue(FALLING, Boolean.valueOf(â˜ƒ));
   }

   public abstract Fluid getSource();

   public FluidState getSource(boolean var1) {
      return this.getSource().defaultFluidState().setValue(FALLING, Boolean.valueOf(â˜ƒ));
   }

   protected abstract boolean canConvertToSource();

   protected void spreadTo(LevelAccessor var1, BlockPos var2, BlockState var3, Direction var4, FluidState var5) {
      if (â˜ƒ.getBlock() instanceof LiquidBlockContainer) {
         ((LiquidBlockContainer)â˜ƒ.getBlock()).placeLiquid(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         if (!â˜ƒ.isAir()) {
            this.beforeDestroyingBlock(â˜ƒ, â˜ƒ, â˜ƒ);
         }

         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.createLegacyBlock(), 3);
      }
   }

   protected abstract void beforeDestroyingBlock(LevelAccessor var1, BlockPos var2, BlockState var3);

   private static short getCacheKey(BlockPos var0, BlockPos var1) {
      int â˜ƒ = â˜ƒ.getX() - â˜ƒ.getX();
      int â˜ƒx = â˜ƒ.getZ() - â˜ƒ.getZ();
      return (short)((â˜ƒ + 128 & 0xFF) << 8 | â˜ƒx + 128 & 0xFF);
   }

   protected int getSlopeDistance(
      LevelReader var1,
      BlockPos var2,
      int var3,
      Direction var4,
      BlockState var5,
      BlockPos var6,
      Short2ObjectMap<Pair<BlockState, FluidState>> var7,
      Short2BooleanMap var8
   ) {
      int â˜ƒ = 1000;

      for(Direction â˜ƒx : Direction.Plane.HORIZONTAL) {
         if (â˜ƒx != â˜ƒ) {
            BlockPos â˜ƒxx = â˜ƒ.relative(â˜ƒx);
            short â˜ƒxxx = getCacheKey(â˜ƒ, â˜ƒxx);
            Pair<BlockState, FluidState> â˜ƒxxxx = â˜ƒ.computeIfAbsent(â˜ƒxxx, var2x -> {
               BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
               return Pair.of(â˜ƒ, â˜ƒ.getFluidState());
            });
            BlockState â˜ƒxxxxx = â˜ƒxxxx.getFirst();
            FluidState â˜ƒxxxxxx = â˜ƒxxxx.getSecond();
            if (this.canPassThrough(â˜ƒ, this.getFlowing(), â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxxxx, â˜ƒxxxxxx)) {
               boolean â˜ƒxxxxxxx = â˜ƒ.computeIfAbsent(â˜ƒxxx, var4x -> {
                  BlockPos â˜ƒ = â˜ƒ.below();
                  BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
                  return this.isWaterHole(â˜ƒ, this.getFlowing(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx);
               });
               if (â˜ƒxxxxxxx) {
                  return â˜ƒ;
               }

               if (â˜ƒ < this.getSlopeFindDistance(â˜ƒ)) {
                  int â˜ƒxxxxxxx = this.getSlopeDistance(â˜ƒ, â˜ƒxx, â˜ƒ + 1, â˜ƒx.getOpposite(), â˜ƒxxxxx, â˜ƒ, â˜ƒ, â˜ƒ);
                  if (â˜ƒxxxxxxx < â˜ƒ) {
                     â˜ƒ = â˜ƒxxxxxxx;
                  }
               }
            }
         }
      }

      return â˜ƒ;
   }

   private boolean isWaterHole(BlockGetter var1, Fluid var2, BlockPos var3, BlockState var4, BlockPos var5, BlockState var6) {
      if (!this.canPassThroughWall(Direction.DOWN, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
         return false;
      } else {
         return â˜ƒ.getFluidState().getType().isSame(this) ? true : this.canHoldFluid(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private boolean canPassThrough(BlockGetter var1, Fluid var2, BlockPos var3, BlockState var4, Direction var5, BlockPos var6, BlockState var7, FluidState var8) {
      return !this.isSourceBlockOfThisType(â˜ƒ) && this.canPassThroughWall(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) && this.canHoldFluid(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private boolean isSourceBlockOfThisType(FluidState var1) {
      return â˜ƒ.getType().isSame(this) && â˜ƒ.isSource();
   }

   protected abstract int getSlopeFindDistance(LevelReader var1);

   private int sourceNeighborCount(LevelReader var1, BlockPos var2) {
      int â˜ƒ = 0;

      for(Direction â˜ƒx : Direction.Plane.HORIZONTAL) {
         BlockPos â˜ƒxx = â˜ƒ.relative(â˜ƒx);
         FluidState â˜ƒxxx = â˜ƒ.getFluidState(â˜ƒxx);
         if (this.isSourceBlockOfThisType(â˜ƒxxx)) {
            ++â˜ƒ;
         }
      }

      return â˜ƒ;
   }

   protected Map<Direction, FluidState> getSpread(LevelReader var1, BlockPos var2, BlockState var3) {
      int â˜ƒ = 1000;
      Map<Direction, FluidState> â˜ƒx = Maps.newEnumMap(Direction.class);
      Short2ObjectMap<Pair<BlockState, FluidState>> â˜ƒxx = new Short2ObjectOpenHashMap<>();
      Short2BooleanMap â˜ƒxxx = new Short2BooleanOpenHashMap();

      for(Direction â˜ƒxxxx : Direction.Plane.HORIZONTAL) {
         BlockPos â˜ƒxxxxx = â˜ƒ.relative(â˜ƒxxxx);
         short â˜ƒxxxxxx = getCacheKey(â˜ƒ, â˜ƒxxxxx);
         Pair<BlockState, FluidState> â˜ƒxxxxxxx = â˜ƒxx.computeIfAbsent(â˜ƒxxxxxx, var2x -> {
            BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
            return Pair.of(â˜ƒ, â˜ƒ.getFluidState());
         });
         BlockState â˜ƒxxxxxxxx = â˜ƒxxxxxxx.getFirst();
         FluidState â˜ƒxxxxxxxxx = â˜ƒxxxxxxx.getSecond();
         FluidState â˜ƒxxxxxxxxxx = this.getNewLiquid(â˜ƒ, â˜ƒxxxxx, â˜ƒxxxxxxxx);
         if (this.canPassThrough(â˜ƒ, â˜ƒxxxxxxxxxx.getType(), â˜ƒ, â˜ƒ, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx)) {
            BlockPos â˜ƒxxxxxxxxxxxx = â˜ƒxxxxx.below();
            boolean â˜ƒxxxxxxxxxxxxx = â˜ƒxxx.computeIfAbsent(â˜ƒxxxxxx, var5x -> {
               BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
               return this.isWaterHole(â˜ƒ, this.getFlowing(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
            });
            int â˜ƒxxxxxxxxxxx;
            if (â˜ƒxxxxxxxxxxxxx) {
               â˜ƒxxxxxxxxxxx = 0;
            } else {
               â˜ƒxxxxxxxxxxx = this.getSlopeDistance(â˜ƒ, â˜ƒxxxxx, 1, â˜ƒxxxx.getOpposite(), â˜ƒxxxxxxxx, â˜ƒ, â˜ƒxx, â˜ƒxxx);
            }

            if (â˜ƒxxxxxxxxxxx < â˜ƒ) {
               â˜ƒx.clear();
            }

            if (â˜ƒxxxxxxxxxxx <= â˜ƒ) {
               â˜ƒx.put(â˜ƒxxxx, â˜ƒxxxxxxxxxx);
               â˜ƒ = â˜ƒxxxxxxxxxxx;
            }
         }
      }

      return â˜ƒx;
   }

   private boolean canHoldFluid(BlockGetter var1, BlockPos var2, BlockState var3, Fluid var4) {
      Block â˜ƒ = â˜ƒ.getBlock();
      if (â˜ƒ instanceof LiquidBlockContainer) {
         return ((LiquidBlockContainer)â˜ƒ).canPlaceLiquid(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (!(â˜ƒ instanceof DoorBlock)
         && !â˜ƒ.is(BlockTags.SIGNS)
         && !â˜ƒ.is(Blocks.LADDER)
         && !â˜ƒ.is(Blocks.SUGAR_CANE)
         && !â˜ƒ.is(Blocks.BUBBLE_COLUMN)) {
         Material â˜ƒ = â˜ƒ.getMaterial();
         if (â˜ƒ != Material.PORTAL && â˜ƒ != Material.STRUCTURAL_AIR && â˜ƒ != Material.WATER_PLANT && â˜ƒ != Material.REPLACEABLE_WATER_PLANT) {
            return !â˜ƒ.blocksMotion();
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   protected boolean canSpreadTo(BlockGetter var1, BlockPos var2, BlockState var3, Direction var4, BlockPos var5, BlockState var6, FluidState var7, Fluid var8) {
      return â˜ƒ.canBeReplacedWith(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) && this.canPassThroughWall(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) && this.canHoldFluid(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   protected abstract int getDropOff(LevelReader var1);

   protected int getSpreadDelay(Level var1, BlockPos var2, FluidState var3, FluidState var4) {
      return this.getTickDelay(â˜ƒ);
   }

   @Override
   public void tick(Level var1, BlockPos var2, FluidState var3) {
      if (!â˜ƒ.isSource()) {
         FluidState â˜ƒ = this.getNewLiquid(â˜ƒ, â˜ƒ, â˜ƒ.getBlockState(â˜ƒ));
         int â˜ƒx = this.getSpreadDelay(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒ.isEmpty()) {
            â˜ƒ = â˜ƒ;
            â˜ƒ.setBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 3);
         } else if (!â˜ƒ.equals(â˜ƒ)) {
            â˜ƒ = â˜ƒ;
            BlockState â˜ƒ = â˜ƒ.createLegacyBlock();
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 2);
            â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, â˜ƒ.getType(), â˜ƒx);
            â˜ƒ.updateNeighborsAt(â˜ƒ, â˜ƒ.getBlock());
         }
      }

      this.spread(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   protected static int getLegacyLevel(FluidState var0) {
      return â˜ƒ.isSource() ? 0 : 8 - Math.min(â˜ƒ.getAmount(), 8) + (â˜ƒ.getValue(FALLING) ? 8 : 0);
   }

   private static boolean hasSameAbove(FluidState var0, BlockGetter var1, BlockPos var2) {
      return â˜ƒ.getType().isSame(â˜ƒ.getFluidState(â˜ƒ.above()).getType());
   }

   @Override
   public float getHeight(FluidState var1, BlockGetter var2, BlockPos var3) {
      return hasSameAbove(â˜ƒ, â˜ƒ, â˜ƒ) ? 1.0F : â˜ƒ.getOwnHeight();
   }

   @Override
   public float getOwnHeight(FluidState var1) {
      return (float)â˜ƒ.getAmount() / 9.0F;
   }

   @Override
   public abstract int getAmount(FluidState var1);

   @Override
   public VoxelShape getShape(FluidState var1, BlockGetter var2, BlockPos var3) {
      return â˜ƒ.getAmount() == 9 && hasSameAbove(â˜ƒ, â˜ƒ, â˜ƒ)
         ? Shapes.block()
         : (VoxelShape)this.shapes.computeIfAbsent(â˜ƒ, var2x -> Shapes.box(0.0, 0.0, 0.0, 1.0, (double)var2x.getHeight(â˜ƒ, â˜ƒ), 1.0));
   }
}
