package net.minecraft.world.level.levelgen;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.util.BitStorage;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Heightmap {
   private static final Logger LOGGER = LogManager.getLogger();
   static final Predicate<BlockState> NOT_AIR = var0 -> !var0.isAir();
   static final Predicate<BlockState> MATERIAL_MOTION_BLOCKING = var0 -> var0.getMaterial().blocksMotion();
   private final BitStorage data;
   private final Predicate<BlockState> isOpaque;
   private final ChunkAccess chunk;

   public Heightmap(ChunkAccess var1, Heightmap.Types var2) {
      this.isOpaque = â˜ƒ.isOpaque();
      this.chunk = â˜ƒ;
      int â˜ƒ = Mth.ceillog2(â˜ƒ.getHeight() + 1);
      this.data = new BitStorage(â˜ƒ, 256);
   }

   public static void primeHeightmaps(ChunkAccess var0, Set<Heightmap.Types> var1) {
      int â˜ƒ = â˜ƒ.size();
      ObjectList<Heightmap> â˜ƒx = new ObjectArrayList<>(â˜ƒ);
      ObjectListIterator<Heightmap> â˜ƒxx = â˜ƒx.iterator();
      int â˜ƒxxx = â˜ƒ.getHighestSectionPosition() + 16;
      BlockPos.MutableBlockPos â˜ƒxxxx = new BlockPos.MutableBlockPos();

      for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < 16; ++â˜ƒxxxxx) {
         for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < 16; ++â˜ƒxxxxxx) {
            for(Heightmap.Types â˜ƒxxxxxxx : â˜ƒ) {
               â˜ƒx.add(â˜ƒ.getOrCreateHeightmapUnprimed(â˜ƒxxxxxxx));
            }

            for(int â˜ƒxxxxxxx = â˜ƒxxx - 1; â˜ƒxxxxxxx >= â˜ƒ.getMinBuildHeight(); --â˜ƒxxxxxxx) {
               â˜ƒxxxx.set(â˜ƒxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxx);
               BlockState â˜ƒxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxx);
               if (!â˜ƒxxxxxxxx.is(Blocks.AIR)) {
                  while(â˜ƒxx.hasNext()) {
                     Heightmap â˜ƒxxxxxxxxx = (Heightmap)â˜ƒxx.next();
                     if (â˜ƒxxxxxxxxx.isOpaque.test(â˜ƒxxxxxxxx)) {
                        â˜ƒxxxxxxxxx.setHeight(â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx + 1);
                        â˜ƒxx.remove();
                     }
                  }

                  if (â˜ƒx.isEmpty()) {
                     break;
                  }

                  â˜ƒxx.back(â˜ƒ);
               }
            }
         }
      }
   }

   public boolean update(int var1, int var2, int var3, BlockState var4) {
      int â˜ƒ = this.getFirstAvailable(â˜ƒ, â˜ƒ);
      if (â˜ƒ <= â˜ƒ - 2) {
         return false;
      } else {
         if (this.isOpaque.test(â˜ƒ)) {
            if (â˜ƒ >= â˜ƒ) {
               this.setHeight(â˜ƒ, â˜ƒ, â˜ƒ + 1);
               return true;
            }
         } else if (â˜ƒ - 1 == â˜ƒ) {
            BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos();

            for(int â˜ƒx = â˜ƒ - 1; â˜ƒx >= this.chunk.getMinBuildHeight(); --â˜ƒx) {
               â˜ƒ.set(â˜ƒ, â˜ƒx, â˜ƒ);
               if (this.isOpaque.test(this.chunk.getBlockState(â˜ƒ))) {
                  this.setHeight(â˜ƒ, â˜ƒ, â˜ƒx + 1);
                  return true;
               }
            }

            this.setHeight(â˜ƒ, â˜ƒ, this.chunk.getMinBuildHeight());
            return true;
         }

         return false;
      }
   }

   public int getFirstAvailable(int var1, int var2) {
      return this.getFirstAvailable(getIndex(â˜ƒ, â˜ƒ));
   }

   public int getHighestTaken(int var1, int var2) {
      return this.getFirstAvailable(getIndex(â˜ƒ, â˜ƒ)) - 1;
   }

   private int getFirstAvailable(int var1) {
      return this.data.get(â˜ƒ) + this.chunk.getMinBuildHeight();
   }

   private void setHeight(int var1, int var2, int var3) {
      this.data.set(getIndex(â˜ƒ, â˜ƒ), â˜ƒ - this.chunk.getMinBuildHeight());
   }

   public void setRawData(ChunkAccess var1, Heightmap.Types var2, long[] var3) {
      long[] â˜ƒ = this.data.getRaw();
      if (â˜ƒ.length == â˜ƒ.length) {
         System.arraycopy(â˜ƒ, 0, â˜ƒ, 0, â˜ƒ.length);
      } else {
         LOGGER.warn("Ignoring heightmap data for chunk " + â˜ƒ.getPos() + ", size does not match; expected: " + â˜ƒ.length + ", got: " + â˜ƒ.length);
         primeHeightmaps(â˜ƒ, EnumSet.of(â˜ƒ));
      }
   }

   public long[] getRawData() {
      return this.data.getRaw();
   }

   private static int getIndex(int var0, int var1) {
      return â˜ƒ + â˜ƒ * 16;
   }

   public static enum Types implements StringRepresentable {
      WORLD_SURFACE_WG("WORLD_SURFACE_WG", Heightmap.Usage.WORLDGEN, Heightmap.NOT_AIR),
      WORLD_SURFACE("WORLD_SURFACE", Heightmap.Usage.CLIENT, Heightmap.NOT_AIR),
      OCEAN_FLOOR_WG("OCEAN_FLOOR_WG", Heightmap.Usage.WORLDGEN, Heightmap.MATERIAL_MOTION_BLOCKING),
      OCEAN_FLOOR("OCEAN_FLOOR", Heightmap.Usage.LIVE_WORLD, Heightmap.MATERIAL_MOTION_BLOCKING),
      MOTION_BLOCKING("MOTION_BLOCKING", Heightmap.Usage.CLIENT, var0 -> var0.getMaterial().blocksMotion() || !var0.getFluidState().isEmpty()),
      MOTION_BLOCKING_NO_LEAVES(
         "MOTION_BLOCKING_NO_LEAVES",
         Heightmap.Usage.LIVE_WORLD,
         var0 -> (var0.getMaterial().blocksMotion() || !var0.getFluidState().isEmpty()) && !(var0.getBlock() instanceof LeavesBlock)
      );

      public static final Codec<Heightmap.Types> CODEC = StringRepresentable.fromEnum(Heightmap.Types::values, Heightmap.Types::getFromKey);
      private final String serializationKey;
      private final Heightmap.Usage usage;
      private final Predicate<BlockState> isOpaque;
      private static final Map<String, Heightmap.Types> REVERSE_LOOKUP = Util.make(Maps.newHashMap(), var0 -> {
         for(Heightmap.Types â˜ƒ : values()) {
            var0.put(â˜ƒ.serializationKey, â˜ƒ);
         }
      });

      private Types(String var3, Heightmap.Usage var4, Predicate<BlockState> var5) {
         this.serializationKey = â˜ƒ;
         this.usage = â˜ƒ;
         this.isOpaque = â˜ƒ;
      }

      public String getSerializationKey() {
         return this.serializationKey;
      }

      public boolean sendToClient() {
         return this.usage == Heightmap.Usage.CLIENT;
      }

      public boolean keepAfterWorldgen() {
         return this.usage != Heightmap.Usage.WORLDGEN;
      }

      @Nullable
      public static Heightmap.Types getFromKey(String var0) {
         return (Heightmap.Types)REVERSE_LOOKUP.get(â˜ƒ);
      }

      public Predicate<BlockState> isOpaque() {
         return this.isOpaque;
      }

      @Override
      public String getSerializedName() {
         return this.serializationKey;
      }
   }

   public static enum Usage {
      WORLDGEN,
      LIVE_WORLD,
      CLIENT;
   }
}
