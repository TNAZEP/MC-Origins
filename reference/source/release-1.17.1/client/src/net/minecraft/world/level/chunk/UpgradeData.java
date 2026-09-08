package net.minecraft.world.level.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.EnumSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction8;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.StemGrownBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ChestType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpgradeData {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final UpgradeData EMPTY = new UpgradeData(EmptyBlockGetter.INSTANCE);
   private static final String TAG_INDICES = "Indices";
   private static final Direction8[] DIRECTIONS = Direction8.values();
   private final EnumSet<Direction8> sides = EnumSet.noneOf(Direction8.class);
   private final int[][] index;
   static final Map<Block, UpgradeData.BlockFixer> MAP = new IdentityHashMap();
   static final Set<UpgradeData.BlockFixer> CHUNKY_FIXERS = Sets.<UpgradeData.BlockFixer>newHashSet();

   private UpgradeData(LevelHeightAccessor var1) {
      this.index = new int[â˜ƒ.getSectionsCount()][];
   }

   public UpgradeData(CompoundTag var1, LevelHeightAccessor var2) {
      this(â˜ƒ);
      if (â˜ƒ.contains("Indices", 10)) {
         CompoundTag â˜ƒ = â˜ƒ.getCompound("Indices");

         for(int â˜ƒx = 0; â˜ƒx < this.index.length; ++â˜ƒx) {
            String â˜ƒxx = String.valueOf(â˜ƒx);
            if (â˜ƒ.contains(â˜ƒxx, 11)) {
               this.index[â˜ƒx] = â˜ƒ.getIntArray(â˜ƒxx);
            }
         }
      }

      int â˜ƒ = â˜ƒ.getInt("Sides");

      for(Direction8 â˜ƒx : Direction8.values()) {
         if ((â˜ƒ & 1 << â˜ƒx.ordinal()) != 0) {
            this.sides.add(â˜ƒx);
         }
      }
   }

   public void upgrade(LevelChunk var1) {
      this.upgradeInside(â˜ƒ);

      for(Direction8 â˜ƒ : DIRECTIONS) {
         upgradeSides(â˜ƒ, â˜ƒ);
      }

      Level â˜ƒ = â˜ƒ.getLevel();
      CHUNKY_FIXERS.forEach(var1x -> var1x.processChunk(â˜ƒ));
   }

   private static void upgradeSides(LevelChunk var0, Direction8 var1) {
      Level â˜ƒ = â˜ƒ.getLevel();
      if (â˜ƒ.getUpgradeData().sides.remove(â˜ƒ)) {
         Set<Direction> â˜ƒx = â˜ƒ.getDirections();
         int â˜ƒxx = 0;
         int â˜ƒxxx = 15;
         boolean â˜ƒxxxx = â˜ƒx.contains(Direction.EAST);
         boolean â˜ƒxxxxx = â˜ƒx.contains(Direction.WEST);
         boolean â˜ƒxxxxxx = â˜ƒx.contains(Direction.SOUTH);
         boolean â˜ƒxxxxxxx = â˜ƒx.contains(Direction.NORTH);
         boolean â˜ƒxxxxxxxx = â˜ƒx.size() == 1;
         ChunkPos â˜ƒxxxxxxxxx = â˜ƒ.getPos();
         int â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxx.getMinBlockX() + (!â˜ƒxxxxxxxx || !â˜ƒxxxxxxx && !â˜ƒxxxxxx ? (â˜ƒxxxxx ? 0 : 15) : 1);
         int â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxx.getMinBlockX() + (!â˜ƒxxxxxxxx || !â˜ƒxxxxxxx && !â˜ƒxxxxxx ? (â˜ƒxxxxx ? 0 : 15) : 14);
         int â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxx.getMinBlockZ() + (!â˜ƒxxxxxxxx || !â˜ƒxxxx && !â˜ƒxxxxx ? (â˜ƒxxxxxxx ? 0 : 15) : 1);
         int â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxxx.getMinBlockZ() + (!â˜ƒxxxxxxxx || !â˜ƒxxxx && !â˜ƒxxxxx ? (â˜ƒxxxxxxx ? 0 : 15) : 14);
         Direction[] â˜ƒxxxxxxxxxxxxxx = Direction.values();
         BlockPos.MutableBlockPos â˜ƒxxxxxxxxxxxxxxx = new BlockPos.MutableBlockPos();

         for(BlockPos â˜ƒxxxxxxxxxxxxxxxx : BlockPos.betweenClosed(
            â˜ƒxxxxxxxxxx, â˜ƒ.getMinBuildHeight(), â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒ.getMaxBuildHeight() - 1, â˜ƒxxxxxxxxxxxxx
         )) {
            BlockState â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxxxxxxxxxxxx);
            BlockState â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxx;

            for(Direction â˜ƒxxxxxxxxxxxxxxxxxxx : â˜ƒxxxxxxxxxxxxxx) {
               â˜ƒxxxxxxxxxxxxxxx.setWithOffset(â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxx);
               â˜ƒxxxxxxxxxxxxxxxxxx = updateState(â˜ƒxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxx, â˜ƒ, â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx);
            }

            Block.updateOrDestroy(â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxx, â˜ƒ, â˜ƒxxxxxxxxxxxxxxxx, 18);
         }
      }
   }

   private static BlockState updateState(BlockState var0, Direction var1, LevelAccessor var2, BlockPos var3, BlockPos var4) {
      return ((UpgradeData.BlockFixer)MAP.getOrDefault(â˜ƒ.getBlock(), UpgradeData.BlockFixers.DEFAULT))
         .updateShape(â˜ƒ, â˜ƒ, â˜ƒ.getBlockState(â˜ƒ), â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private void upgradeInside(LevelChunk var1) {
      BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos();
      BlockPos.MutableBlockPos â˜ƒx = new BlockPos.MutableBlockPos();
      ChunkPos â˜ƒxx = â˜ƒ.getPos();
      LevelAccessor â˜ƒxxx = â˜ƒ.getLevel();

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < this.index.length; ++â˜ƒxxxx) {
         LevelChunkSection â˜ƒxxxxx = â˜ƒ.getSections()[â˜ƒxxxx];
         int[] â˜ƒxxxxxx = this.index[â˜ƒxxxx];
         this.index[â˜ƒxxxx] = null;
         if (â˜ƒxxxxx != null && â˜ƒxxxxxx != null && â˜ƒxxxxxx.length > 0) {
            Direction[] â˜ƒxxxxxxx = Direction.values();
            PalettedContainer<BlockState> â˜ƒxxxxxxxx = â˜ƒxxxxx.getStates();

            for(int â˜ƒxxxxxxxxx : â˜ƒxxxxxx) {
               int â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxx & 15;
               int â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxx >> 8 & 15;
               int â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxx >> 4 & 15;
               â˜ƒ.set(â˜ƒxx.getMinBlockX() + â˜ƒxxxxxxxxxx, â˜ƒxxxxx.bottomBlockY() + â˜ƒxxxxxxxxxxx, â˜ƒxx.getMinBlockZ() + â˜ƒxxxxxxxxxxxx);
               BlockState â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxx.get(â˜ƒxxxxxxxxx);
               BlockState â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxx;

               for(Direction â˜ƒxxxxxxxxxxxxxxx : â˜ƒxxxxxxx) {
                  â˜ƒx.setWithOffset(â˜ƒ, â˜ƒxxxxxxxxxxxxxxx);
                  if (SectionPos.blockToSectionCoord(â˜ƒ.getX()) == â˜ƒxx.x && SectionPos.blockToSectionCoord(â˜ƒ.getZ()) == â˜ƒxx.z) {
                     â˜ƒxxxxxxxxxxxxxx = updateState(â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxx, â˜ƒ, â˜ƒx);
                  }
               }

               Block.updateOrDestroy(â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxx, â˜ƒ, 18);
            }
         }
      }

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < this.index.length; ++â˜ƒxxxx) {
         if (this.index[â˜ƒxxxx] != null) {
            LOGGER.warn("Discarding update data for section {} for chunk ({} {})", â˜ƒxxx.getSectionYFromSectionIndex(â˜ƒxxxx), â˜ƒxx.x, â˜ƒxx.z);
         }

         this.index[â˜ƒxxxx] = null;
      }
   }

   public boolean isEmpty() {
      for(int[] â˜ƒ : this.index) {
         if (â˜ƒ != null) {
            return false;
         }
      }

      return this.sides.isEmpty();
   }

   public CompoundTag write() {
      CompoundTag â˜ƒ = new CompoundTag();
      CompoundTag â˜ƒx = new CompoundTag();

      for(int â˜ƒxx = 0; â˜ƒxx < this.index.length; ++â˜ƒxx) {
         String â˜ƒxxx = String.valueOf(â˜ƒxx);
         if (this.index[â˜ƒxx] != null && this.index[â˜ƒxx].length != 0) {
            â˜ƒx.putIntArray(â˜ƒxxx, this.index[â˜ƒxx]);
         }
      }

      if (!â˜ƒx.isEmpty()) {
         â˜ƒ.put("Indices", â˜ƒx);
      }

      int â˜ƒxx = 0;

      for(Direction8 â˜ƒxxx : this.sides) {
         â˜ƒxx |= 1 << â˜ƒxxx.ordinal();
      }

      â˜ƒ.putByte("Sides", (byte)â˜ƒxx);
      return â˜ƒ;
   }

   public interface BlockFixer {
      BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6);

      default void processChunk(LevelAccessor var1) {
      }
   }

   static enum BlockFixers implements UpgradeData.BlockFixer {
      BLACKLIST(
         Blocks.OBSERVER,
         Blocks.NETHER_PORTAL,
         Blocks.WHITE_CONCRETE_POWDER,
         Blocks.ORANGE_CONCRETE_POWDER,
         Blocks.MAGENTA_CONCRETE_POWDER,
         Blocks.LIGHT_BLUE_CONCRETE_POWDER,
         Blocks.YELLOW_CONCRETE_POWDER,
         Blocks.LIME_CONCRETE_POWDER,
         Blocks.PINK_CONCRETE_POWDER,
         Blocks.GRAY_CONCRETE_POWDER,
         Blocks.LIGHT_GRAY_CONCRETE_POWDER,
         Blocks.CYAN_CONCRETE_POWDER,
         Blocks.PURPLE_CONCRETE_POWDER,
         Blocks.BLUE_CONCRETE_POWDER,
         Blocks.BROWN_CONCRETE_POWDER,
         Blocks.GREEN_CONCRETE_POWDER,
         Blocks.RED_CONCRETE_POWDER,
         Blocks.BLACK_CONCRETE_POWDER,
         Blocks.ANVIL,
         Blocks.CHIPPED_ANVIL,
         Blocks.DAMAGED_ANVIL,
         Blocks.DRAGON_EGG,
         Blocks.GRAVEL,
         Blocks.SAND,
         Blocks.RED_SAND,
         Blocks.OAK_SIGN,
         Blocks.SPRUCE_SIGN,
         Blocks.BIRCH_SIGN,
         Blocks.ACACIA_SIGN,
         Blocks.JUNGLE_SIGN,
         Blocks.DARK_OAK_SIGN,
         Blocks.OAK_WALL_SIGN,
         Blocks.SPRUCE_WALL_SIGN,
         Blocks.BIRCH_WALL_SIGN,
         Blocks.ACACIA_WALL_SIGN,
         Blocks.JUNGLE_WALL_SIGN,
         Blocks.DARK_OAK_WALL_SIGN
      ) {
         @Override
         public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
            return â˜ƒ;
         }
      },
      DEFAULT {
         @Override
         public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
            return â˜ƒ.updateShape(â˜ƒ, â˜ƒ.getBlockState(â˜ƒ), â˜ƒ, â˜ƒ, â˜ƒ);
         }
      },
      CHEST(Blocks.CHEST, Blocks.TRAPPED_CHEST) {
         @Override
         public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
            if (â˜ƒ.is(â˜ƒ.getBlock())
               && â˜ƒ.getAxis().isHorizontal()
               && â˜ƒ.getValue(ChestBlock.TYPE) == ChestType.SINGLE
               && â˜ƒ.getValue(ChestBlock.TYPE) == ChestType.SINGLE) {
               Direction â˜ƒ = â˜ƒ.getValue(ChestBlock.FACING);
               if (â˜ƒ.getAxis() != â˜ƒ.getAxis() && â˜ƒ == â˜ƒ.getValue(ChestBlock.FACING)) {
                  ChestType â˜ƒx = â˜ƒ == â˜ƒ.getClockWise() ? ChestType.LEFT : ChestType.RIGHT;
                  â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(ChestBlock.TYPE, â˜ƒx.getOpposite()), 18);
                  if (â˜ƒ == Direction.NORTH || â˜ƒ == Direction.EAST) {
                     BlockEntity â˜ƒxx = â˜ƒ.getBlockEntity(â˜ƒ);
                     BlockEntity â˜ƒxxx = â˜ƒ.getBlockEntity(â˜ƒ);
                     if (â˜ƒxx instanceof ChestBlockEntity && â˜ƒxxx instanceof ChestBlockEntity) {
                        ChestBlockEntity.swapContents((ChestBlockEntity)â˜ƒxx, (ChestBlockEntity)â˜ƒxxx);
                     }
                  }

                  return â˜ƒ.setValue(ChestBlock.TYPE, â˜ƒx);
               }
            }

            return â˜ƒ;
         }
      },
      LEAVES(true, Blocks.ACACIA_LEAVES, Blocks.BIRCH_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES) {
         private final ThreadLocal<List<ObjectSet<BlockPos>>> queue = ThreadLocal.withInitial(() -> Lists.newArrayListWithCapacity(7));

         @Override
         public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
            BlockState â˜ƒ = â˜ƒ.updateShape(â˜ƒ, â˜ƒ.getBlockState(â˜ƒ), â˜ƒ, â˜ƒ, â˜ƒ);
            if (â˜ƒ != â˜ƒ) {
               int â˜ƒx = â˜ƒ.getValue(BlockStateProperties.DISTANCE);
               List<ObjectSet<BlockPos>> â˜ƒxx = (List)this.queue.get();
               if (â˜ƒxx.isEmpty()) {
                  for(int â˜ƒxxx = 0; â˜ƒxxx < 7; ++â˜ƒxxx) {
                     â˜ƒxx.add(new ObjectOpenHashSet());
                  }
               }

               ((ObjectSet)â˜ƒxx.get(â˜ƒx)).add(â˜ƒ.immutable());
            }

            return â˜ƒ;
         }

         @Override
         public void processChunk(LevelAccessor var1) {
            BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos();
            List<ObjectSet<BlockPos>> â˜ƒx = (List)this.queue.get();

            for(int â˜ƒxx = 2; â˜ƒxx < â˜ƒx.size(); ++â˜ƒxx) {
               int â˜ƒxxx = â˜ƒxx - 1;
               ObjectSet<BlockPos> â˜ƒxxxx = (ObjectSet)â˜ƒx.get(â˜ƒxxx);
               ObjectSet<BlockPos> â˜ƒxxxxx = (ObjectSet)â˜ƒx.get(â˜ƒxx);

               for(BlockPos â˜ƒxxxxxx : â˜ƒxxxx) {
                  BlockState â˜ƒxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxx);
                  if (â˜ƒxxxxxxx.getValue(BlockStateProperties.DISTANCE) >= â˜ƒxxx) {
                     â˜ƒ.setBlock(â˜ƒxxxxxx, â˜ƒxxxxxxx.setValue(BlockStateProperties.DISTANCE, Integer.valueOf(â˜ƒxxx)), 18);
                     if (â˜ƒxx != 7) {
                        for(Direction â˜ƒxxxxxxxx : DIRECTIONS) {
                           â˜ƒ.setWithOffset(â˜ƒxxxxxx, â˜ƒxxxxxxxx);
                           BlockState â˜ƒxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒ);
                           if (â˜ƒxxxxxxxxx.hasProperty(BlockStateProperties.DISTANCE) && â˜ƒxxxxxxx.getValue(BlockStateProperties.DISTANCE) > â˜ƒxx) {
                              â˜ƒxxxxx.add(â˜ƒ.immutable());
                           }
                        }
                     }
                  }
               }
            }

            â˜ƒx.clear();
         }
      },
      STEM_BLOCK(Blocks.MELON_STEM, Blocks.PUMPKIN_STEM) {
         @Override
         public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
            if (â˜ƒ.getValue(StemBlock.AGE) == 7) {
               StemGrownBlock â˜ƒ = ((StemBlock)â˜ƒ.getBlock()).getFruit();
               if (â˜ƒ.is(â˜ƒ)) {
                  return â˜ƒ.getAttachedStem().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, â˜ƒ);
               }
            }

            return â˜ƒ;
         }
      };

      public static final Direction[] DIRECTIONS = Direction.values();

      BlockFixers(Block... var3) {
         this(false, â˜ƒ);
      }

      BlockFixers(boolean var3, Block... var4) {
         for(Block â˜ƒ : â˜ƒ) {
            UpgradeData.MAP.put(â˜ƒ, this);
         }

         if (â˜ƒ) {
            UpgradeData.CHUNKY_FIXERS.add(this);
         }
      }
   }
}
