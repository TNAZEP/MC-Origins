package net.minecraft.world.item;

import com.google.common.collect.Iterables;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class MapItem extends ComplexItem {
   public static final int IMAGE_WIDTH = 128;
   public static final int IMAGE_HEIGHT = 128;
   private static final int DEFAULT_MAP_COLOR = -12173266;
   private static final String TAG_MAP = "map";

   public MapItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   public static ItemStack create(Level var0, int var1, int var2, byte var3, boolean var4, boolean var5) {
      ItemStack â˜ƒ = new ItemStack(Items.FILLED_MAP);
      createAndStoreSavedData(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.dimension());
      return â˜ƒ;
   }

   @Nullable
   public static MapItemSavedData getSavedData(@Nullable Integer var0, Level var1) {
      return â˜ƒ == null ? null : â˜ƒ.getMapData(makeKey(â˜ƒ));
   }

   @Nullable
   public static MapItemSavedData getSavedData(ItemStack var0, Level var1) {
      Integer â˜ƒ = getMapId(â˜ƒ);
      return getSavedData(â˜ƒ, â˜ƒ);
   }

   @Nullable
   public static Integer getMapId(ItemStack var0) {
      CompoundTag â˜ƒ = â˜ƒ.getTag();
      return â˜ƒ != null && â˜ƒ.contains("map", 99) ? â˜ƒ.getInt("map") : null;
   }

   private static int createNewSavedData(Level var0, int var1, int var2, int var3, boolean var4, boolean var5, ResourceKey<Level> var6) {
      MapItemSavedData â˜ƒ = MapItemSavedData.createFresh((double)â˜ƒ, (double)â˜ƒ, (byte)â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      int â˜ƒx = â˜ƒ.getFreeMapId();
      â˜ƒ.setMapData(makeKey(â˜ƒx), â˜ƒ);
      return â˜ƒx;
   }

   private static void storeMapData(ItemStack var0, int var1) {
      â˜ƒ.getOrCreateTag().putInt("map", â˜ƒ);
   }

   private static void createAndStoreSavedData(ItemStack var0, Level var1, int var2, int var3, int var4, boolean var5, boolean var6, ResourceKey<Level> var7) {
      int â˜ƒ = createNewSavedData(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      storeMapData(â˜ƒ, â˜ƒ);
   }

   public static String makeKey(int var0) {
      return "map_" + â˜ƒ;
   }

   public void update(Level var1, Entity var2, MapItemSavedData var3) {
      if (â˜ƒ.dimension() == â˜ƒ.dimension && â˜ƒ instanceof Player) {
         int â˜ƒ = 1 << â˜ƒ.scale;
         int â˜ƒx = â˜ƒ.x;
         int â˜ƒxx = â˜ƒ.z;
         int â˜ƒxxx = Mth.floor(â˜ƒ.getX() - (double)â˜ƒx) / â˜ƒ + 64;
         int â˜ƒxxxx = Mth.floor(â˜ƒ.getZ() - (double)â˜ƒxx) / â˜ƒ + 64;
         int â˜ƒxxxxx = 128 / â˜ƒ;
         if (â˜ƒ.dimensionType().hasCeiling()) {
            â˜ƒxxxxx /= 2;
         }

         MapItemSavedData.HoldingPlayer â˜ƒ = â˜ƒ.getHoldingPlayer((Player)â˜ƒ);
         ++â˜ƒ.step;
         boolean â˜ƒx = false;

         for(int â˜ƒxx = â˜ƒxxx - â˜ƒxxxxx + 1; â˜ƒxx < â˜ƒxxx + â˜ƒxxxxx; ++â˜ƒxx) {
            if ((â˜ƒxx & 15) == (â˜ƒ.step & 15) || â˜ƒx) {
               â˜ƒx = false;
               double â˜ƒxxx = 0.0;

               for(int â˜ƒxxxx = â˜ƒxxxx - â˜ƒxxxxx - 1; â˜ƒxxxx < â˜ƒxxxx + â˜ƒxxxxx; ++â˜ƒxxxx) {
                  if (â˜ƒxx >= 0 && â˜ƒxxxx >= -1 && â˜ƒxx < 128 && â˜ƒxxxx < 128) {
                     int â˜ƒxxxxx = â˜ƒxx - â˜ƒxxx;
                     int â˜ƒxxxxxx = â˜ƒxxxx - â˜ƒxxxx;
                     boolean â˜ƒxxxxxxx = â˜ƒxxxxx * â˜ƒxxxxx + â˜ƒxxxxxx * â˜ƒxxxxxx > (â˜ƒxxxxx - 2) * (â˜ƒxxxxx - 2);
                     int â˜ƒxxxxxxxx = (â˜ƒx / â˜ƒ + â˜ƒxx - 64) * â˜ƒ;
                     int â˜ƒxxxxxxxxx = (â˜ƒxx / â˜ƒ + â˜ƒxxxx - 64) * â˜ƒ;
                     Multiset<MaterialColor> â˜ƒxxxxxxxxxx = LinkedHashMultiset.create();
                     LevelChunk â˜ƒxxxxxxxxxxx = â˜ƒ.getChunkAt(new BlockPos(â˜ƒxxxxxxxx, 0, â˜ƒxxxxxxxxx));
                     if (!â˜ƒxxxxxxxxxxx.isEmpty()) {
                        ChunkPos â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx.getPos();
                        int â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxx & 15;
                        int â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxx & 15;
                        int â˜ƒxxxxxxxxxxxxxxx = 0;
                        double â˜ƒxxxxxxxxxxxxxxxx = 0.0;
                        if (â˜ƒ.dimensionType().hasCeiling()) {
                           int â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxx + â˜ƒxxxxxxxxx * 231871;
                           â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxx * 31287121 + â˜ƒxxxxxxxxxxxxxxxxx * 11;
                           if ((â˜ƒxxxxxxxxxxxxxxxxx >> 20 & 1) == 0) {
                              â˜ƒxxxxxxxxxx.add(Blocks.DIRT.defaultBlockState().getMapColor(â˜ƒ, BlockPos.ZERO), 10);
                           } else {
                              â˜ƒxxxxxxxxxx.add(Blocks.STONE.defaultBlockState().getMapColor(â˜ƒ, BlockPos.ZERO), 100);
                           }

                           â˜ƒxxxxxxxxxxxxxxxx = 100.0;
                        } else {
                           BlockPos.MutableBlockPos â˜ƒxxxxxxxxxxxx = new BlockPos.MutableBlockPos();
                           BlockPos.MutableBlockPos â˜ƒxxxxxxxxxxxxx = new BlockPos.MutableBlockPos();

                           for(int â˜ƒxxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxxx < â˜ƒ; ++â˜ƒxxxxxxxxxxxxxx) {
                              for(int â˜ƒxxxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxxxx < â˜ƒ; ++â˜ƒxxxxxxxxxxxxxxx) {
                                 int â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx.getHeight(
                                       Heightmap.Types.WORLD_SURFACE, â˜ƒxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxx
                                    )
                                    + 1;
                                 BlockState â˜ƒxxxxxxxxxxxxxxxx;
                                 if (â˜ƒxxxxxxxxxxxxxxxxx <= â˜ƒ.getMinBuildHeight() + 1) {
                                    â˜ƒxxxxxxxxxxxxxxxx = Blocks.BEDROCK.defaultBlockState();
                                 } else {
                                    do {
                                       â˜ƒxxxxxxxxxxxx.set(
                                          â˜ƒxxxxxxxxxxxx.getMinBlockX() + â˜ƒxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxx,
                                          --â˜ƒxxxxxxxxxxxxxxxxx,
                                          â˜ƒxxxxxxxxxxxx.getMinBlockZ() + â˜ƒxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxx
                                       );
                                       â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx.getBlockState(â˜ƒxxxxxxxxxxxx);
                                    } while(
                                       â˜ƒxxxxxxxxxxxxxxxx.getMapColor(â˜ƒ, â˜ƒxxxxxxxxxxxx) == MaterialColor.NONE
                                          && â˜ƒxxxxxxxxxxxxxxxxx > â˜ƒ.getMinBuildHeight()
                                    );

                                    if (â˜ƒxxxxxxxxxxxxxxxxx > â˜ƒ.getMinBuildHeight() && !â˜ƒxxxxxxxxxxxxxxxx.getFluidState().isEmpty()) {
                                       int â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxx - 1;
                                       â˜ƒxxxxxxxxxxxxx.set(â˜ƒxxxxxxxxxxxx);

                                       BlockState â˜ƒ;
                                       do {
                                          â˜ƒxxxxxxxxxxxxx.setY(â˜ƒxxxxxxxxxxxxxxxx--);
                                          â˜ƒ = â˜ƒxxxxxxxxxxx.getBlockState(â˜ƒxxxxxxxxxxxxx);
                                          ++â˜ƒxxxxxxxxxxxxxxx;
                                       } while(â˜ƒxxxxxxxxxxxxxxxx > â˜ƒ.getMinBuildHeight() && !â˜ƒ.getFluidState().isEmpty());

                                       â˜ƒxxxxxxxxxxxxxxxx = this.getCorrectStateForFluidBlock(â˜ƒ, â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx);
                                    }
                                 }

                                 â˜ƒ.checkBanners(
                                    â˜ƒ,
                                    â˜ƒxxxxxxxxxxxx.getMinBlockX() + â˜ƒxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxx,
                                    â˜ƒxxxxxxxxxxxx.getMinBlockZ() + â˜ƒxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxx
                                 );
                                 â˜ƒxxxxxxxxxxxxxxxx += (double)â˜ƒxxxxxxxxxxxxxxxxx / (double)(â˜ƒ * â˜ƒ);
                                 â˜ƒxxxxxxxxxx.add(â˜ƒxxxxxxxxxxxxxxxx.getMapColor(â˜ƒ, â˜ƒxxxxxxxxxxxx));
                              }
                           }
                        }

                        â˜ƒxxxxxxxxxxxxxxx /= â˜ƒ * â˜ƒ;
                        double â˜ƒxxxxxxxxxxxx = (â˜ƒxxxxxxxxxxxxxxxx - â˜ƒxxx) * 4.0 / (double)(â˜ƒ + 4) + ((double)(â˜ƒxx + â˜ƒxxxx & 1) - 0.5) * 0.4;
                        int â˜ƒxxxxxxxxxxxxx = 1;
                        if (â˜ƒxxxxxxxxxxxx > 0.6) {
                           â˜ƒxxxxxxxxxxxxx = 2;
                        }

                        if (â˜ƒxxxxxxxxxxxx < -0.6) {
                           â˜ƒxxxxxxxxxxxxx = 0;
                        }

                        MaterialColor â˜ƒxxxxxxxxxxxx = Iterables.getFirst(Multisets.copyHighestCountFirst(â˜ƒxxxxxxxxxx), MaterialColor.NONE);
                        if (â˜ƒxxxxxxxxxxxx == MaterialColor.WATER) {
                           â˜ƒxxxxxxxxxxxx = (double)â˜ƒxxxxxxxxxxxxxxx * 0.1 + (double)(â˜ƒxx + â˜ƒxxxx & 1) * 0.2;
                           â˜ƒxxxxxxxxxxxxx = 1;
                           if (â˜ƒxxxxxxxxxxxx < 0.5) {
                              â˜ƒxxxxxxxxxxxxx = 2;
                           }

                           if (â˜ƒxxxxxxxxxxxx > 0.9) {
                              â˜ƒxxxxxxxxxxxxx = 0;
                           }
                        }

                        â˜ƒxxx = â˜ƒxxxxxxxxxxxxxxxx;
                        if (â˜ƒxxxx >= 0 && â˜ƒxxxxx * â˜ƒxxxxx + â˜ƒxxxxxx * â˜ƒxxxxxx < â˜ƒxxxxx * â˜ƒxxxxx && (!â˜ƒxxxxxxx || (â˜ƒxx + â˜ƒxxxx & 1) != 0)) {
                           â˜ƒx |= â˜ƒ.updateColor(â˜ƒxx, â˜ƒxxxx, (byte)(â˜ƒxxxxxxxxxxxx.id * 4 + â˜ƒxxxxxxxxxxxxx));
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private BlockState getCorrectStateForFluidBlock(Level var1, BlockState var2, BlockPos var3) {
      FluidState â˜ƒ = â˜ƒ.getFluidState();
      return !â˜ƒ.isEmpty() && !â˜ƒ.isFaceSturdy(â˜ƒ, â˜ƒ, Direction.UP) ? â˜ƒ.createLegacyBlock() : â˜ƒ;
   }

   private static boolean isLand(Biome[] var0, int var1, int var2, int var3) {
      return â˜ƒ[â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ * 128 * â˜ƒ].getDepth() >= 0.0F;
   }

   public static void renderBiomePreviewMap(ServerLevel var0, ItemStack var1) {
      MapItemSavedData â˜ƒ = getSavedData(â˜ƒ, â˜ƒ);
      if (â˜ƒ != null) {
         if (â˜ƒ.dimension() == â˜ƒ.dimension) {
            int â˜ƒx = 1 << â˜ƒ.scale;
            int â˜ƒxx = â˜ƒ.x;
            int â˜ƒxxx = â˜ƒ.z;
            Biome[] â˜ƒxxxx = new Biome[128 * â˜ƒx * 128 * â˜ƒx];

            for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < 128 * â˜ƒx; ++â˜ƒxxxxx) {
               for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < 128 * â˜ƒx; ++â˜ƒxxxxxx) {
                  â˜ƒxxxx[â˜ƒxxxxx * 128 * â˜ƒx + â˜ƒxxxxxx] = â˜ƒ.getBiome(
                     new BlockPos((â˜ƒxx / â˜ƒx - 64) * â˜ƒx + â˜ƒxxxxxx, 0, (â˜ƒxxx / â˜ƒx - 64) * â˜ƒx + â˜ƒxxxxx)
                  );
               }
            }

            for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < 128; ++â˜ƒxxxxx) {
               for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < 128; ++â˜ƒxxxxxx) {
                  if (â˜ƒxxxxx > 0 && â˜ƒxxxxxx > 0 && â˜ƒxxxxx < 127 && â˜ƒxxxxxx < 127) {
                     Biome â˜ƒxxxxxxx = â˜ƒxxxx[â˜ƒxxxxx * â˜ƒx + â˜ƒxxxxxx * â˜ƒx * 128 * â˜ƒx];
                     int â˜ƒxxxxxxxx = 8;
                     if (isLand(â˜ƒxxxx, â˜ƒx, â˜ƒxxxxx - 1, â˜ƒxxxxxx - 1)) {
                        --â˜ƒxxxxxxxx;
                     }

                     if (isLand(â˜ƒxxxx, â˜ƒx, â˜ƒxxxxx - 1, â˜ƒxxxxxx + 1)) {
                        --â˜ƒxxxxxxxx;
                     }

                     if (isLand(â˜ƒxxxx, â˜ƒx, â˜ƒxxxxx - 1, â˜ƒxxxxxx)) {
                        --â˜ƒxxxxxxxx;
                     }

                     if (isLand(â˜ƒxxxx, â˜ƒx, â˜ƒxxxxx + 1, â˜ƒxxxxxx - 1)) {
                        --â˜ƒxxxxxxxx;
                     }

                     if (isLand(â˜ƒxxxx, â˜ƒx, â˜ƒxxxxx + 1, â˜ƒxxxxxx + 1)) {
                        --â˜ƒxxxxxxxx;
                     }

                     if (isLand(â˜ƒxxxx, â˜ƒx, â˜ƒxxxxx + 1, â˜ƒxxxxxx)) {
                        --â˜ƒxxxxxxxx;
                     }

                     if (isLand(â˜ƒxxxx, â˜ƒx, â˜ƒxxxxx, â˜ƒxxxxxx - 1)) {
                        --â˜ƒxxxxxxxx;
                     }

                     if (isLand(â˜ƒxxxx, â˜ƒx, â˜ƒxxxxx, â˜ƒxxxxxx + 1)) {
                        --â˜ƒxxxxxxxx;
                     }

                     int â˜ƒxxxxxxx = 3;
                     MaterialColor â˜ƒxxxxxxxx = MaterialColor.NONE;
                     if (â˜ƒxxxxxxx.getDepth() < 0.0F) {
                        â˜ƒxxxxxxxx = MaterialColor.COLOR_ORANGE;
                        if (â˜ƒxxxxxxxx > 7 && â˜ƒxxxxxx % 2 == 0) {
                           â˜ƒxxxxxxx = (â˜ƒxxxxx + (int)(Mth.sin((float)â˜ƒxxxxxx + 0.0F) * 7.0F)) / 8 % 5;
                           if (â˜ƒxxxxxxx == 3) {
                              â˜ƒxxxxxxx = 1;
                           } else if (â˜ƒxxxxxxx == 4) {
                              â˜ƒxxxxxxx = 0;
                           }
                        } else if (â˜ƒxxxxxxxx > 7) {
                           â˜ƒxxxxxxxx = MaterialColor.NONE;
                        } else if (â˜ƒxxxxxxxx > 5) {
                           â˜ƒxxxxxxx = 1;
                        } else if (â˜ƒxxxxxxxx > 3) {
                           â˜ƒxxxxxxx = 0;
                        } else if (â˜ƒxxxxxxxx > 1) {
                           â˜ƒxxxxxxx = 0;
                        }
                     } else if (â˜ƒxxxxxxxx > 0) {
                        â˜ƒxxxxxxxx = MaterialColor.COLOR_BROWN;
                        if (â˜ƒxxxxxxxx > 3) {
                           â˜ƒxxxxxxx = 1;
                        } else {
                           â˜ƒxxxxxxx = 3;
                        }
                     }

                     if (â˜ƒxxxxxxxx != MaterialColor.NONE) {
                        â˜ƒ.setColor(â˜ƒxxxxx, â˜ƒxxxxxx, (byte)(â˜ƒxxxxxxxx.id * 4 + â˜ƒxxxxxxx));
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public void inventoryTick(ItemStack var1, Level var2, Entity var3, int var4, boolean var5) {
      if (!â˜ƒ.isClientSide) {
         MapItemSavedData â˜ƒ = getSavedData(â˜ƒ, â˜ƒ);
         if (â˜ƒ != null) {
            if (â˜ƒ instanceof Player â˜ƒx) {
               â˜ƒ.tickCarriedBy(â˜ƒx, â˜ƒ);
            }

            if (!â˜ƒ.locked && (â˜ƒ || â˜ƒ instanceof Player && ((Player)â˜ƒ).getOffhandItem() == â˜ƒ)) {
               this.update(â˜ƒ, â˜ƒ, â˜ƒ);
            }
         }
      }
   }

   @Nullable
   @Override
   public Packet<?> getUpdatePacket(ItemStack var1, Level var2, Player var3) {
      Integer â˜ƒ = getMapId(â˜ƒ);
      MapItemSavedData â˜ƒx = getSavedData(â˜ƒ, â˜ƒ);
      return â˜ƒx != null ? â˜ƒx.getUpdatePacket(â˜ƒ, â˜ƒ) : null;
   }

   @Override
   public void onCraftedBy(ItemStack var1, Level var2, Player var3) {
      CompoundTag â˜ƒ = â˜ƒ.getTag();
      if (â˜ƒ != null && â˜ƒ.contains("map_scale_direction", 99)) {
         scaleMap(â˜ƒ, â˜ƒ, â˜ƒ.getInt("map_scale_direction"));
         â˜ƒ.remove("map_scale_direction");
      } else if (â˜ƒ != null && â˜ƒ.contains("map_to_lock", 1) && â˜ƒ.getBoolean("map_to_lock")) {
         lockMap(â˜ƒ, â˜ƒ);
         â˜ƒ.remove("map_to_lock");
      }
   }

   private static void scaleMap(ItemStack var0, Level var1, int var2) {
      MapItemSavedData â˜ƒ = getSavedData(â˜ƒ, â˜ƒ);
      if (â˜ƒ != null) {
         int â˜ƒx = â˜ƒ.getFreeMapId();
         â˜ƒ.setMapData(makeKey(â˜ƒx), â˜ƒ.scaled(â˜ƒ));
         storeMapData(â˜ƒ, â˜ƒx);
      }
   }

   public static void lockMap(Level var0, ItemStack var1) {
      MapItemSavedData â˜ƒ = getSavedData(â˜ƒ, â˜ƒ);
      if (â˜ƒ != null) {
         int â˜ƒx = â˜ƒ.getFreeMapId();
         String â˜ƒxx = makeKey(â˜ƒx);
         MapItemSavedData â˜ƒxxx = â˜ƒ.locked();
         â˜ƒ.setMapData(â˜ƒxx, â˜ƒxxx);
         storeMapData(â˜ƒ, â˜ƒx);
      }
   }

   @Override
   public void appendHoverText(ItemStack var1, @Nullable Level var2, List<Component> var3, TooltipFlag var4) {
      Integer â˜ƒ = getMapId(â˜ƒ);
      MapItemSavedData â˜ƒx = â˜ƒ == null ? null : getSavedData(â˜ƒ, â˜ƒ);
      if (â˜ƒx != null && â˜ƒx.locked) {
         â˜ƒ.add(new TranslatableComponent("filled_map.locked", â˜ƒ).withStyle(ChatFormatting.GRAY));
      }

      if (â˜ƒ.isAdvanced()) {
         if (â˜ƒx != null) {
            â˜ƒ.add(new TranslatableComponent("filled_map.id", â˜ƒ).withStyle(ChatFormatting.GRAY));
            â˜ƒ.add(new TranslatableComponent("filled_map.scale", 1 << â˜ƒx.scale).withStyle(ChatFormatting.GRAY));
            â˜ƒ.add(new TranslatableComponent("filled_map.level", â˜ƒx.scale, 4).withStyle(ChatFormatting.GRAY));
         } else {
            â˜ƒ.add(new TranslatableComponent("filled_map.unknown").withStyle(ChatFormatting.GRAY));
         }
      }
   }

   public static int getColor(ItemStack var0) {
      CompoundTag â˜ƒ = â˜ƒ.getTagElement("display");
      if (â˜ƒ != null && â˜ƒ.contains("MapColor", 99)) {
         int â˜ƒx = â˜ƒ.getInt("MapColor");
         return 0xFF000000 | â˜ƒx & 16777215;
      } else {
         return -12173266;
      }
   }

   @Override
   public InteractionResult useOn(UseOnContext var1) {
      BlockState â˜ƒ = â˜ƒ.getLevel().getBlockState(â˜ƒ.getClickedPos());
      if (â˜ƒ.is(BlockTags.BANNERS)) {
         if (!â˜ƒ.getLevel().isClientSide) {
            MapItemSavedData â˜ƒx = getSavedData(â˜ƒ.getItemInHand(), â˜ƒ.getLevel());
            if (â˜ƒx != null && !â˜ƒx.toggleBanner(â˜ƒ.getLevel(), â˜ƒ.getClickedPos())) {
               return InteractionResult.FAIL;
            }
         }

         return InteractionResult.sidedSuccess(â˜ƒ.getLevel().isClientSide);
      } else {
         return super.useOn(â˜ƒ);
      }
   }
}
