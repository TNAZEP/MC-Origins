package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.util.datafix.PackedBitStorage;

public class LeavesFix extends DataFix {
   private static final int NORTH_WEST_MASK = 128;
   private static final int WEST_MASK = 64;
   private static final int SOUTH_WEST_MASK = 32;
   private static final int SOUTH_MASK = 16;
   private static final int SOUTH_EAST_MASK = 8;
   private static final int EAST_MASK = 4;
   private static final int NORTH_EAST_MASK = 2;
   private static final int NORTH_MASK = 1;
   private static final int[][] DIRECTIONS = new int[][]{{-1, 0, 0}, {1, 0, 0}, {0, -1, 0}, {0, 1, 0}, {0, 0, -1}, {0, 0, 1}};
   private static final int DECAY_DISTANCE = 7;
   private static final int SIZE_BITS = 12;
   private static final int SIZE = 4096;
   static final Object2IntMap<String> LEAVES = DataFixUtils.make(new Object2IntOpenHashMap(), var0 -> {
      var0.put("minecraft:acacia_leaves", 0);
      var0.put("minecraft:birch_leaves", 1);
      var0.put("minecraft:dark_oak_leaves", 2);
      var0.put("minecraft:jungle_leaves", 3);
      var0.put("minecraft:oak_leaves", 4);
      var0.put("minecraft:spruce_leaves", 5);
   });
   static final Set<String> LOGS = ImmutableSet.of(
      "minecraft:acacia_bark",
      "minecraft:birch_bark",
      "minecraft:dark_oak_bark",
      "minecraft:jungle_bark",
      "minecraft:oak_bark",
      "minecraft:spruce_bark",
      "minecraft:acacia_log",
      "minecraft:birch_log",
      "minecraft:dark_oak_log",
      "minecraft:jungle_log",
      "minecraft:oak_log",
      "minecraft:spruce_log",
      "minecraft:stripped_acacia_log",
      "minecraft:stripped_birch_log",
      "minecraft:stripped_dark_oak_log",
      "minecraft:stripped_jungle_log",
      "minecraft:stripped_oak_log",
      "minecraft:stripped_spruce_log"
   );

   public LeavesFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      Type<?> â˜ƒ = this.getInputSchema().getType(References.CHUNK);
      OpticFinder<?> â˜ƒx = â˜ƒ.findField("Level");
      OpticFinder<?> â˜ƒxx = â˜ƒx.type().findField("Sections");
      Type<?> â˜ƒxxx = â˜ƒxx.type();
      if (!(â˜ƒxxx instanceof ListType)) {
         throw new IllegalStateException("Expecting sections to be a list.");
      } else {
         Type<?> â˜ƒ = ((ListType)â˜ƒxxx).getElement();
         OpticFinder<?> â˜ƒx = DSL.typeFinder(â˜ƒ);
         return this.fixTypeEverywhereTyped(
            "Leaves fix",
            â˜ƒ,
            var4x -> var4x.updateTyped(
                  â˜ƒ,
                  var3x -> {
                     int[] â˜ƒ = new int[]{0};
                     Typed<?> â˜ƒx = var3x.updateTyped(
                        â˜ƒ,
                        var3xx -> {
                           Int2ObjectMap<LeavesFix.LeavesSection> â˜ƒ = new Int2ObjectOpenHashMap<>(
                              (Map<? extends Integer, ? extends LeavesFix.LeavesSection>)var3xx.getAllTyped(â˜ƒ)
                                 .stream()
                                 .map(var1x -> new LeavesFix.LeavesSection(var1x, this.getInputSchema()))
                                 .collect(Collectors.toMap(LeavesFix.Section::getIndex, var0 -> var0))
                           );
                           if (â˜ƒ.values().stream().allMatch(LeavesFix.Section::isSkippable)) {
                              return var3xx;
                           } else {
                              List<IntSet> â˜ƒ = Lists.<IntSet>newArrayList();
         
                              for(int â˜ƒx = 0; â˜ƒx < 7; ++â˜ƒx) {
                                 â˜ƒ.add(new IntOpenHashSet());
                              }
         
                              for(LeavesFix.LeavesSection â˜ƒx : â˜ƒ.values()) {
                                 if (!â˜ƒx.isSkippable()) {
                                    for(int â˜ƒxx = 0; â˜ƒxx < 4096; ++â˜ƒxx) {
                                       int â˜ƒxxx = â˜ƒx.getBlock(â˜ƒxx);
                                       if (â˜ƒx.isLog(â˜ƒxxx)) {
                                          ((IntSet)â˜ƒ.get(0)).add(â˜ƒx.getIndex() << 12 | â˜ƒxx);
                                       } else if (â˜ƒx.isLeaf(â˜ƒxxx)) {
                                          int â˜ƒxxx = this.getX(â˜ƒxx);
                                          int â˜ƒxxxx = this.getZ(â˜ƒxx);
                                          â˜ƒ[0] |= getSideMask(â˜ƒxxx == 0, â˜ƒxxx == 15, â˜ƒxxxx == 0, â˜ƒxxxx == 15);
                                       }
                                    }
                                 }
                              }
         
                              for(int â˜ƒx = 1; â˜ƒx < 7; ++â˜ƒx) {
                                 IntSet â˜ƒxx = (IntSet)â˜ƒ.get(â˜ƒx - 1);
                                 IntSet â˜ƒxxx = (IntSet)â˜ƒ.get(â˜ƒx);
                                 IntIterator â˜ƒxxxx = â˜ƒxx.iterator();
         
                                 while(â˜ƒxxxx.hasNext()) {
                                    int â˜ƒxxxxx = â˜ƒxxxx.nextInt();
                                    int â˜ƒxxxxxx = this.getX(â˜ƒxxxxx);
                                    int â˜ƒxxxxxxx = this.getY(â˜ƒxxxxx);
                                    int â˜ƒxxxxxxxx = this.getZ(â˜ƒxxxxx);
         
                                    for(int[] â˜ƒxxxxxxxxx : DIRECTIONS) {
                                       int â˜ƒxxxxxxxxxx = â˜ƒxxxxxx + â˜ƒxxxxxxxxx[0];
                                       int â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxx + â˜ƒxxxxxxxxx[1];
                                       int â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxx + â˜ƒxxxxxxxxx[2];
                                       if (â˜ƒxxxxxxxxxx >= 0
                                          && â˜ƒxxxxxxxxxx <= 15
                                          && â˜ƒxxxxxxxxxxxx >= 0
                                          && â˜ƒxxxxxxxxxxxx <= 15
                                          && â˜ƒxxxxxxxxxxx >= 0
                                          && â˜ƒxxxxxxxxxxx <= 255) {
                                          LeavesFix.LeavesSection â˜ƒxxxxxxxxxxxxx = â˜ƒ.get(â˜ƒxxxxxxxxxxx >> 4);
                                          if (â˜ƒxxxxxxxxxxxxx != null && !â˜ƒxxxxxxxxxxxxx.isSkippable()) {
                                             int â˜ƒxxxxxxxxxxxxxx = getIndex(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx & 15, â˜ƒxxxxxxxxxxxx);
                                             int â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxx.getBlock(â˜ƒxxxxxxxxxxxxxx);
                                             if (â˜ƒxxxxxxxxxxxxx.isLeaf(â˜ƒxxxxxxxxxxxxxxx)) {
                                                int â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxx.getDistance(â˜ƒxxxxxxxxxxxxxxx);
                                                if (â˜ƒxxxxxxxxxxxxxxxx > â˜ƒx) {
                                                   â˜ƒxxxxxxxxxxxxx.setDistance(â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx, â˜ƒx);
                                                   â˜ƒxxx.add(getIndex(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx));
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
         
                              return var3xx.updateTyped(â˜ƒ, var1x -> â˜ƒ.get(var1x.get(DSL.remainderFinder()).get("Y").asInt(0)).write(var1x));
                           }
                        }
                     );
                     if (â˜ƒ[0] != 0) {
                        â˜ƒx = â˜ƒx.update(DSL.remainderFinder(), var1x -> {
                           Dynamic<?> â˜ƒ = DataFixUtils.orElse(var1x.get("UpgradeData").result(), var1x.emptyMap());
                           return var1x.set("UpgradeData", â˜ƒ.set("Sides", var1x.createByte((byte)(â˜ƒ.get("Sides").asByte((byte)0) | â˜ƒ[0]))));
                        });
                     }
      
                     return â˜ƒx;
                  }
               )
         );
      }
   }

   public static int getIndex(int var0, int var1, int var2) {
      return â˜ƒ << 8 | â˜ƒ << 4 | â˜ƒ;
   }

   private int getX(int var1) {
      return â˜ƒ & 15;
   }

   private int getY(int var1) {
      return â˜ƒ >> 8 & 0xFF;
   }

   private int getZ(int var1) {
      return â˜ƒ >> 4 & 15;
   }

   public static int getSideMask(boolean var0, boolean var1, boolean var2, boolean var3) {
      int â˜ƒ = 0;
      if (â˜ƒ) {
         if (â˜ƒ) {
            â˜ƒ |= 2;
         } else if (â˜ƒ) {
            â˜ƒ |= 128;
         } else {
            â˜ƒ |= 1;
         }
      } else if (â˜ƒ) {
         if (â˜ƒ) {
            â˜ƒ |= 32;
         } else if (â˜ƒ) {
            â˜ƒ |= 8;
         } else {
            â˜ƒ |= 16;
         }
      } else if (â˜ƒ) {
         â˜ƒ |= 4;
      } else if (â˜ƒ) {
         â˜ƒ |= 64;
      }

      return â˜ƒ;
   }

   public static final class LeavesSection extends LeavesFix.Section {
      private static final String PERSISTENT = "persistent";
      private static final String DECAYABLE = "decayable";
      private static final String DISTANCE = "distance";
      @Nullable
      private IntSet leaveIds;
      @Nullable
      private IntSet logIds;
      @Nullable
      private Int2IntMap stateToIdMap;

      public LeavesSection(Typed<?> var1, Schema var2) {
         super(â˜ƒ, â˜ƒ);
      }

      @Override
      protected boolean skippable() {
         this.leaveIds = new IntOpenHashSet();
         this.logIds = new IntOpenHashSet();
         this.stateToIdMap = new Int2IntOpenHashMap();

         for(int â˜ƒ = 0; â˜ƒ < this.palette.size(); ++â˜ƒ) {
            Dynamic<?> â˜ƒx = (Dynamic)this.palette.get(â˜ƒ);
            String â˜ƒxx = â˜ƒx.get("Name").asString("");
            if (LeavesFix.LEAVES.containsKey(â˜ƒxx)) {
               boolean â˜ƒxxx = Objects.equals(â˜ƒx.get("Properties").get("decayable").asString(""), "false");
               this.leaveIds.add(â˜ƒ);
               this.stateToIdMap.put(this.getStateId(â˜ƒxx, â˜ƒxxx, 7), â˜ƒ);
               this.palette.set(â˜ƒ, this.makeLeafTag(â˜ƒx, â˜ƒxx, â˜ƒxxx, 7));
            }

            if (LeavesFix.LOGS.contains(â˜ƒxx)) {
               this.logIds.add(â˜ƒ);
            }
         }

         return this.leaveIds.isEmpty() && this.logIds.isEmpty();
      }

      private Dynamic<?> makeLeafTag(Dynamic<?> var1, String var2, boolean var3, int var4) {
         Dynamic<?> â˜ƒ = â˜ƒ.emptyMap();
         â˜ƒ = â˜ƒ.set("persistent", â˜ƒ.createString(â˜ƒ ? "true" : "false"));
         â˜ƒ = â˜ƒ.set("distance", â˜ƒ.createString(Integer.toString(â˜ƒ)));
         Dynamic<?> â˜ƒx = â˜ƒ.emptyMap();
         â˜ƒx = â˜ƒx.set("Properties", â˜ƒ);
         return â˜ƒx.set("Name", â˜ƒx.createString(â˜ƒ));
      }

      public boolean isLog(int var1) {
         return this.logIds.contains(â˜ƒ);
      }

      public boolean isLeaf(int var1) {
         return this.leaveIds.contains(â˜ƒ);
      }

      int getDistance(int var1) {
         return this.isLog(â˜ƒ) ? 0 : Integer.parseInt(((Dynamic)this.palette.get(â˜ƒ)).get("Properties").get("distance").asString(""));
      }

      void setDistance(int var1, int var2, int var3) {
         Dynamic<?> â˜ƒ = (Dynamic)this.palette.get(â˜ƒ);
         String â˜ƒx = â˜ƒ.get("Name").asString("");
         boolean â˜ƒxx = Objects.equals(â˜ƒ.get("Properties").get("persistent").asString(""), "true");
         int â˜ƒxxx = this.getStateId(â˜ƒx, â˜ƒxx, â˜ƒ);
         if (!this.stateToIdMap.containsKey(â˜ƒxxx)) {
            int â˜ƒxxxx = this.palette.size();
            this.leaveIds.add(â˜ƒxxxx);
            this.stateToIdMap.put(â˜ƒxxx, â˜ƒxxxx);
            this.palette.add(this.makeLeafTag(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒ));
         }

         int â˜ƒ = this.stateToIdMap.get(â˜ƒxxx);
         if (1 << this.storage.getBits() <= â˜ƒ) {
            PackedBitStorage â˜ƒx = new PackedBitStorage(this.storage.getBits() + 1, 4096);

            for(int â˜ƒxx = 0; â˜ƒxx < 4096; ++â˜ƒxx) {
               â˜ƒx.set(â˜ƒxx, this.storage.get(â˜ƒxx));
            }

            this.storage = â˜ƒx;
         }

         this.storage.set(â˜ƒ, â˜ƒ);
      }
   }

   public abstract static class Section {
      protected static final String BLOCK_STATES_TAG = "BlockStates";
      protected static final String NAME_TAG = "Name";
      protected static final String PROPERTIES_TAG = "Properties";
      private final Type<Pair<String, Dynamic<?>>> blockStateType = DSL.named(References.BLOCK_STATE.typeName(), DSL.remainderType());
      protected final OpticFinder<List<Pair<String, Dynamic<?>>>> paletteFinder = DSL.fieldFinder("Palette", DSL.list(this.blockStateType));
      protected final List<Dynamic<?>> palette;
      protected final int index;
      @Nullable
      protected PackedBitStorage storage;

      public Section(Typed<?> var1, Schema var2) {
         if (!Objects.equals(â˜ƒ.getType(References.BLOCK_STATE), this.blockStateType)) {
            throw new IllegalStateException("Block state type is not what was expected.");
         } else {
            Optional<List<Pair<String, Dynamic<?>>>> â˜ƒ = â˜ƒ.getOptional(this.paletteFinder);
            this.palette = (List)â˜ƒ.map(var0 -> (List)var0.stream().map(Pair::getSecond).collect(Collectors.toList())).orElse(ImmutableList.of());
            Dynamic<?> â˜ƒx = â˜ƒ.get(DSL.remainderFinder());
            this.index = â˜ƒx.get("Y").asInt(0);
            this.readStorage(â˜ƒx);
         }
      }

      protected void readStorage(Dynamic<?> var1) {
         if (this.skippable()) {
            this.storage = null;
         } else {
            long[] â˜ƒ = â˜ƒ.get("BlockStates").asLongStream().toArray();
            int â˜ƒx = Math.max(4, DataFixUtils.ceillog2(this.palette.size()));
            this.storage = new PackedBitStorage(â˜ƒx, 4096, â˜ƒ);
         }
      }

      public Typed<?> write(Typed<?> var1) {
         return this.isSkippable()
            ? â˜ƒ
            : â˜ƒ.update(DSL.remainderFinder(), var1x -> var1x.set("BlockStates", var1x.createLongList(Arrays.stream(this.storage.getRaw()))))
               .set(this.paletteFinder, (List)this.palette.stream().map(var0 -> Pair.of(References.BLOCK_STATE.typeName(), var0)).collect(Collectors.toList()));
      }

      public boolean isSkippable() {
         return this.storage == null;
      }

      public int getBlock(int var1) {
         return this.storage.get(â˜ƒ);
      }

      protected int getStateId(String var1, boolean var2, int var3) {
         return LeavesFix.LEAVES.get(â˜ƒ) << 5 | (â˜ƒ ? 16 : 0) | â˜ƒ;
      }

      int getIndex() {
         return this.index;
      }

      protected abstract boolean skippable();
   }
}
