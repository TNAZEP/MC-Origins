package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.datafixers.util.Pair;
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
import java.util.stream.LongStream;
import javax.annotation.Nullable;
import net.minecraft.util.BitArray;
import net.minecraft.util.datafix.TypeReferences;

public class LeavesFix extends DataFix {
   private static final int[][] field_208425_a = new int[][]{{-1, 0, 0}, {1, 0, 0}, {0, -1, 0}, {0, 1, 0}, {0, 0, -1}, {0, 0, 1}};
   private static final Object2IntMap<String> field_208434_j = DataFixUtils.make(new Object2IntOpenHashMap(), var0 -> {
      var0.put("minecraft:acacia_leaves", 0);
      var0.put("minecraft:birch_leaves", 1);
      var0.put("minecraft:dark_oak_leaves", 2);
      var0.put("minecraft:jungle_leaves", 3);
      var0.put("minecraft:oak_leaves", 4);
      var0.put("minecraft:spruce_leaves", 5);
   });
   private static final Set<String> field_208435_k = ImmutableSet.of(
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
      super(☃, ☃);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      Type<?> ☃ = this.getInputSchema().getType(TypeReferences.field_211287_c);
      OpticFinder<?> ☃x = ☃.findField("Level");
      OpticFinder<?> ☃xx = ☃x.type().findField("Sections");
      Type<?> ☃xxx = ☃xx.type();
      if (!(☃xxx instanceof ListType)) {
         throw new IllegalStateException("Expecting sections to be a list.");
      } else {
         Type<?> ☃ = ((ListType)☃xxx).getElement();
         OpticFinder<?> ☃x = DSL.typeFinder(☃);
         return this.fixTypeEverywhereTyped(
            "Leaves fix",
            ☃,
            var4x -> var4x.updateTyped(
                  ☃,
                  var3x -> {
                     int[] ☃ = new int[]{0};
                     Typed<?> ☃x = var3x.updateTyped(
                        ☃,
                        var3xx -> {
                           Int2ObjectMap<LeavesFix.LeavesSection> ☃ = new Int2ObjectOpenHashMap<>(
                              (Map<? extends Integer, ? extends LeavesFix.LeavesSection>)var3xx.getAllTyped(☃)
                                 .stream()
                                 .map(var1x -> new LeavesFix.LeavesSection(var1x, this.getInputSchema()))
                                 .collect(Collectors.toMap(LeavesFix.Section::func_208456_b, var0 -> var0))
                           );
                           if (☃.values().stream().allMatch(LeavesFix.Section::func_208461_a)) {
                              return var3xx;
                           } else {
                              List<IntSet> ☃ = Lists.<IntSet>newArrayList();
         
                              for(int ☃x = 0; ☃x < 7; ++☃x) {
                                 ☃.add(new IntOpenHashSet());
                              }
         
                              for(LeavesFix.LeavesSection ☃x : ☃.values()) {
                                 if (!☃x.func_208461_a()) {
                                    for(int ☃xx = 0; ☃xx < 4096; ++☃xx) {
                                       int ☃xxx = ☃x.func_208453_a(☃xx);
                                       if (☃x.func_208457_b(☃xxx)) {
                                          ((IntSet)☃.get(0)).add(☃x.func_208456_b() << 12 | ☃xx);
                                       } else if (☃x.func_208460_c(☃xxx)) {
                                          int ☃xxx = this.func_208412_a(☃xx);
                                          int ☃xxxx = this.func_208409_c(☃xx);
                                          ☃[0] |= func_210537_a(☃xxx == 0, ☃xxx == 15, ☃xxxx == 0, ☃xxxx == 15);
                                       }
                                    }
                                 }
                              }
         
                              for(int ☃x = 1; ☃x < 7; ++☃x) {
                                 IntSet ☃xx = (IntSet)☃.get(☃x - 1);
                                 IntSet ☃xxx = (IntSet)☃.get(☃x);
                                 IntIterator ☃xxxx = ☃xx.iterator();
         
                                 while(☃xxxx.hasNext()) {
                                    int ☃xxxxx = ☃xxxx.nextInt();
                                    int ☃xxxxxx = this.func_208412_a(☃xxxxx);
                                    int ☃xxxxxxx = this.func_208421_b(☃xxxxx);
                                    int ☃xxxxxxxx = this.func_208409_c(☃xxxxx);
         
                                    for(int[] ☃xxxxxxxxx : field_208425_a) {
                                       int ☃xxxxxxxxxx = ☃xxxxxx + ☃xxxxxxxxx[0];
                                       int ☃xxxxxxxxxxx = ☃xxxxxxx + ☃xxxxxxxxx[1];
                                       int ☃xxxxxxxxxxxx = ☃xxxxxxxx + ☃xxxxxxxxx[2];
                                       if (☃xxxxxxxxxx >= 0
                                          && ☃xxxxxxxxxx <= 15
                                          && ☃xxxxxxxxxxxx >= 0
                                          && ☃xxxxxxxxxxxx <= 15
                                          && ☃xxxxxxxxxxx >= 0
                                          && ☃xxxxxxxxxxx <= 255) {
                                          LeavesFix.LeavesSection ☃xxxxxxxxxxxxx = ☃.get(☃xxxxxxxxxxx >> 4);
                                          if (☃xxxxxxxxxxxxx != null && !☃xxxxxxxxxxxxx.func_208461_a()) {
                                             int ☃xxxxxxxxxxxxxx = func_208411_a(☃xxxxxxxxxx, ☃xxxxxxxxxxx & 15, ☃xxxxxxxxxxxx);
                                             int ☃xxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxx.func_208453_a(☃xxxxxxxxxxxxxx);
                                             if (☃xxxxxxxxxxxxx.func_208460_c(☃xxxxxxxxxxxxxxx)) {
                                                int ☃xxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxx.func_208459_d(☃xxxxxxxxxxxxxxx);
                                                if (☃xxxxxxxxxxxxxxxx > ☃x) {
                                                   ☃xxxxxxxxxxxxx.func_208454_a(☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx, ☃x);
                                                   ☃xxx.add(func_208411_a(☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx));
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
         
                              return var3xx.updateTyped(☃, var1x -> ☃.get(var1x.get(DSL.remainderFinder()).getInt("Y")).func_208465_a(var1x));
                           }
                        }
                     );
                     if (☃[0] != 0) {
                        ☃x = ☃x.update(DSL.remainderFinder(), var1x -> {
                           Dynamic<?> ☃ = DataFixUtils.orElse(var1x.get("UpgradeData"), var1x.emptyMap());
                           return var1x.set("UpgradeData", ☃.set("Sides", var1x.createByte((byte)(☃.getByte("Sides") | ☃[0]))));
                        });
                     }
      
                     return ☃x;
                  }
               )
         );
      }
   }

   public static int func_208411_a(int var0, int var1, int var2) {
      return ☃ << 8 | ☃ << 4 | ☃;
   }

   private int func_208412_a(int var1) {
      return ☃ & 15;
   }

   private int func_208421_b(int var1) {
      return ☃ >> 8 & 0xFF;
   }

   private int func_208409_c(int var1) {
      return ☃ >> 4 & 15;
   }

   public static int func_210537_a(boolean var0, boolean var1, boolean var2, boolean var3) {
      int ☃ = 0;
      if (☃) {
         if (☃) {
            ☃ |= 2;
         } else if (☃) {
            ☃ |= 128;
         } else {
            ☃ |= 1;
         }
      } else if (☃) {
         if (☃) {
            ☃ |= 32;
         } else if (☃) {
            ☃ |= 8;
         } else {
            ☃ |= 16;
         }
      } else if (☃) {
         ☃ |= 4;
      } else if (☃) {
         ☃ |= 64;
      }

      return ☃;
   }

   public static final class LeavesSection extends LeavesFix.Section {
      @Nullable
      private IntSet field_212523_f;
      @Nullable
      private IntSet field_212524_g;
      @Nullable
      private Int2IntMap field_212525_h;

      public LeavesSection(Typed<?> var1, Schema var2) {
         super(☃, ☃);
      }

      @Override
      protected boolean func_212508_a() {
         this.field_212523_f = new IntOpenHashSet();
         this.field_212524_g = new IntOpenHashSet();
         this.field_212525_h = new Int2IntOpenHashMap();

         for(int ☃ = 0; ☃ < this.field_208469_d.size(); ++☃) {
            Dynamic<?> ☃x = (Dynamic)this.field_208469_d.get(☃);
            String ☃xx = ☃x.getString("Name");
            if (LeavesFix.field_208434_j.containsKey(☃xx)) {
               boolean ☃xxx = Objects.equals(☃x.get("Properties").flatMap(var0 -> var0.get("decayable")).flatMap(Dynamic::getStringValue).orElse(""), "false");
               this.field_212523_f.add(☃);
               this.field_212525_h.put(this.func_208464_a(☃xx, ☃xxx, 7), ☃);
               this.field_208469_d.set(☃, this.func_209770_a(☃x, ☃xx, ☃xxx, 7));
            }

            if (LeavesFix.field_208435_k.contains(☃xx)) {
               this.field_212524_g.add(☃);
            }
         }

         return this.field_212523_f.isEmpty() && this.field_212524_g.isEmpty();
      }

      private Dynamic<?> func_209770_a(Dynamic<?> var1, String var2, boolean var3, int var4) {
         Dynamic<?> ☃ = ☃.emptyMap();
         ☃ = ☃.set("persistent", ☃.createString(☃ ? "true" : "false"));
         ☃ = ☃.set("distance", ☃.createString(Integer.toString(☃)));
         Dynamic<?> ☃x = ☃.emptyMap();
         ☃x = ☃x.set("Properties", ☃);
         return ☃x.set("Name", ☃x.createString(☃));
      }

      public boolean func_208457_b(int var1) {
         return this.field_212524_g.contains(☃);
      }

      public boolean func_208460_c(int var1) {
         return this.field_212523_f.contains(☃);
      }

      private int func_208459_d(int var1) {
         return this.func_208457_b(☃)
            ? 0
            : Integer.parseInt(
               (String)((Dynamic)this.field_208469_d.get(☃))
                  .get("Properties")
                  .flatMap(var0 -> var0.get("distance"))
                  .flatMap(Dynamic::getStringValue)
                  .orElse("")
            );
      }

      private void func_208454_a(int var1, int var2, int var3) {
         Dynamic<?> ☃ = (Dynamic)this.field_208469_d.get(☃);
         String ☃x = ☃.getString("Name");
         boolean ☃xx = Objects.equals(☃.get("Properties").flatMap(var0 -> var0.get("persistent")).flatMap(Dynamic::getStringValue).orElse(""), "true");
         int ☃xxx = this.func_208464_a(☃x, ☃xx, ☃);
         if (!this.field_212525_h.containsKey(☃xxx)) {
            int ☃xxxx = this.field_208469_d.size();
            this.field_212523_f.add(☃xxxx);
            this.field_212525_h.put(☃xxx, ☃xxxx);
            this.field_208469_d.add(this.func_209770_a(☃, ☃x, ☃xx, ☃));
         }

         int ☃ = this.field_212525_h.get(☃xxx);
         if (1 << this.field_208470_e.func_208535_c() <= ☃) {
            BitArray ☃x = new BitArray(this.field_208470_e.func_208535_c() + 1, 4096);

            for(int ☃xx = 0; ☃xx < 4096; ++☃xx) {
               ☃x.func_188141_a(☃xx, this.field_208470_e.func_188142_a(☃xx));
            }

            this.field_208470_e = ☃x;
         }

         this.field_208470_e.func_188141_a(☃, ☃);
      }
   }

   public abstract static class Section {
      final Type<Pair<String, Dynamic<?>>> field_208466_a = DSL.named(TypeReferences.field_211296_l.typeName(), DSL.remainderType());
      protected final OpticFinder<List<Pair<String, Dynamic<?>>>> field_208468_c = DSL.fieldFinder("Palette", DSL.list(this.field_208466_a));
      protected final List<Dynamic<?>> field_208469_d;
      protected final int field_208474_i;
      @Nullable
      protected BitArray field_208470_e;

      public Section(Typed<?> var1, Schema var2) {
         if (!Objects.equals(☃.getType(TypeReferences.field_211296_l), this.field_208466_a)) {
            throw new IllegalStateException("Block state type is not what was expected.");
         } else {
            Optional<List<Pair<String, Dynamic<?>>>> ☃ = ☃.getOptional(this.field_208468_c);
            this.field_208469_d = (List)☃.map(var0 -> (List)var0.stream().map(Pair::getSecond).collect(Collectors.toList())).orElse(ImmutableList.of());
            Dynamic<?> ☃x = ☃.get(DSL.remainderFinder());
            this.field_208474_i = ☃x.getInt("Y");
            this.func_212507_a(☃x);
         }
      }

      protected void func_212507_a(Dynamic<?> var1) {
         if (this.func_212508_a()) {
            this.field_208470_e = null;
         } else {
            long[] ☃ = ((LongStream)☃.get("BlockStates").flatMap(Dynamic::getLongStream).get()).toArray();
            int ☃x = Math.max(4, DataFixUtils.ceillog2(this.field_208469_d.size()));
            this.field_208470_e = new BitArray(☃x, 4096, ☃);
         }
      }

      public Typed<?> func_208465_a(Typed<?> var1) {
         return this.func_208461_a()
            ? ☃
            : ☃.update(DSL.remainderFinder(), var1x -> var1x.set("BlockStates", var1x.createLongList(Arrays.stream(this.field_208470_e.func_188143_a()))))
               .set(
                  this.field_208468_c,
                  this.field_208469_d.stream().map(var0 -> Pair.of(TypeReferences.field_211296_l.typeName(), var0)).collect(Collectors.toList())
               );
      }

      public boolean func_208461_a() {
         return this.field_208470_e == null;
      }

      public int func_208453_a(int var1) {
         return this.field_208470_e.func_188142_a(☃);
      }

      protected int func_208464_a(String var1, boolean var2, int var3) {
         return LeavesFix.field_208434_j.get(☃) << 5 | (☃ ? 16 : 0) | ☃;
      }

      int func_208456_b() {
         return this.field_208474_i;
      }

      protected abstract boolean func_212508_a();
   }
}
