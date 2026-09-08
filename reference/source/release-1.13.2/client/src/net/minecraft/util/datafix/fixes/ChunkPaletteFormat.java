package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.util.BitArray;
import net.minecraft.util.IntIdentityHashBiMap;
import net.minecraft.util.datafix.TypeReferences;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkPaletteFormat extends DataFix {
   private static final Logger field_199145_a = LogManager.getLogger();
   private static final BitSet field_199146_b = new BitSet(256);
   private static final BitSet field_199147_c = new BitSet(256);
   private static final Dynamic<?> field_199148_d = BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:pumpkin'}");
   private static final Dynamic<?> field_199149_e = BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:podzol',Properties:{snowy:'true'}}");
   private static final Dynamic<?> field_199150_f = BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:grass_block',Properties:{snowy:'true'}}");
   private static final Dynamic<?> field_199151_g = BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:mycelium',Properties:{snowy:'true'}}");
   private static final Dynamic<?> field_199152_h = BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:sunflower',Properties:{half:'upper'}}");
   private static final Dynamic<?> field_199153_i = BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:lilac',Properties:{half:'upper'}}");
   private static final Dynamic<?> field_199154_j = BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:tall_grass',Properties:{half:'upper'}}");
   private static final Dynamic<?> field_199155_k = BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:large_fern',Properties:{half:'upper'}}");
   private static final Dynamic<?> field_199156_l = BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:rose_bush',Properties:{half:'upper'}}");
   private static final Dynamic<?> field_199157_m = BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:peony',Properties:{half:'upper'}}");
   private static final Map<String, Dynamic<?>> field_199158_n = DataFixUtils.make(Maps.newHashMap(), var0 -> {
      var0.put("minecraft:air0", BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:flower_pot'}"));
      var0.put("minecraft:red_flower0", BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:potted_poppy'}"));
      var0.put("minecraft:red_flower1", BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:potted_blue_orchid'}"));
      var0.put("minecraft:red_flower2", BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:potted_allium'}"));
      var0.put("minecraft:red_flower3", BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:potted_azure_bluet'}"));
      var0.put("minecraft:red_flower4", BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:potted_red_tulip'}"));
      var0.put("minecraft:red_flower5", BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:potted_orange_tulip'}"));
      var0.put("minecraft:red_flower6", BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:potted_white_tulip'}"));
      var0.put("minecraft:red_flower7", BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:potted_pink_tulip'}"));
      var0.put("minecraft:red_flower8", BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:potted_oxeye_daisy'}"));
      var0.put("minecraft:yellow_flower0", BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:potted_dandelion'}"));
      var0.put("minecraft:sapling0", BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:potted_oak_sapling'}"));
      var0.put("minecraft:sapling1", BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:potted_spruce_sapling'}"));
      var0.put("minecraft:sapling2", BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:potted_birch_sapling'}"));
      var0.put("minecraft:sapling3", BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:potted_jungle_sapling'}"));
      var0.put("minecraft:sapling4", BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:potted_acacia_sapling'}"));
      var0.put("minecraft:sapling5", BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:potted_dark_oak_sapling'}"));
      var0.put("minecraft:red_mushroom0", BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:potted_red_mushroom'}"));
      var0.put("minecraft:brown_mushroom0", BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:potted_brown_mushroom'}"));
      var0.put("minecraft:deadbush0", BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:potted_dead_bush'}"));
      var0.put("minecraft:tallgrass2", BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:potted_fern'}"));
      var0.put("minecraft:cactus0", BlockStateFlatteningMap.func_210049_b(2240));
   });
   private static final Map<String, Dynamic<?>> field_199159_o = DataFixUtils.make(Maps.newHashMap(), var0 -> {
      func_209300_a(var0, 0, "skeleton", "skull");
      func_209300_a(var0, 1, "wither_skeleton", "skull");
      func_209300_a(var0, 2, "zombie", "head");
      func_209300_a(var0, 3, "player", "head");
      func_209300_a(var0, 4, "creeper", "head");
      func_209300_a(var0, 5, "dragon", "head");
   });
   private static final Map<String, Dynamic<?>> field_199160_p = DataFixUtils.make(Maps.newHashMap(), var0 -> {
      func_209301_a(var0, "oak_door", 1024);
      func_209301_a(var0, "iron_door", 1136);
      func_209301_a(var0, "spruce_door", 3088);
      func_209301_a(var0, "birch_door", 3104);
      func_209301_a(var0, "jungle_door", 3120);
      func_209301_a(var0, "acacia_door", 3136);
      func_209301_a(var0, "dark_oak_door", 3152);
   });
   private static final Map<String, Dynamic<?>> field_199161_q = DataFixUtils.make(Maps.newHashMap(), var0 -> {
      for(int ☃ = 0; ☃ < 26; ++☃) {
         var0.put("true" + ☃, BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:note_block',Properties:{powered:'true',note:'" + ☃ + "'}}"));
         var0.put("false" + ☃, BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:note_block',Properties:{powered:'false',note:'" + ☃ + "'}}"));
      }
   });
   private static final Int2ObjectMap<String> field_199162_r = DataFixUtils.make(new Int2ObjectOpenHashMap(), var0 -> {
      var0.put(0, "white");
      var0.put(1, "orange");
      var0.put(2, "magenta");
      var0.put(3, "light_blue");
      var0.put(4, "yellow");
      var0.put(5, "lime");
      var0.put(6, "pink");
      var0.put(7, "gray");
      var0.put(8, "light_gray");
      var0.put(9, "cyan");
      var0.put(10, "purple");
      var0.put(11, "blue");
      var0.put(12, "brown");
      var0.put(13, "green");
      var0.put(14, "red");
      var0.put(15, "black");
   });
   private static final Map<String, Dynamic<?>> field_199163_s = DataFixUtils.make(Maps.newHashMap(), var0 -> {
      for(Entry<String> ☃ : field_199162_r.int2ObjectEntrySet()) {
         if (!Objects.equals(☃.getValue(), "red")) {
            func_209307_a(var0, ☃.getIntKey(), (String)☃.getValue());
         }
      }
   });
   private static final Map<String, Dynamic<?>> field_199164_t = DataFixUtils.make(Maps.newHashMap(), var0 -> {
      for(Entry<String> ☃ : field_199162_r.int2ObjectEntrySet()) {
         if (!Objects.equals(☃.getValue(), "white")) {
            func_209297_b(var0, 15 - ☃.getIntKey(), (String)☃.getValue());
         }
      }
   });
   private static final Dynamic<?> field_199165_u = BlockStateFlatteningMap.func_210049_b(0);

   public ChunkPaletteFormat(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   private static void func_209300_a(Map<String, Dynamic<?>> var0, int var1, String var2, String var3) {
      ☃.put(☃ + "north", BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "_wall_" + ☃ + "',Properties:{facing:'north'}}"));
      ☃.put(☃ + "east", BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "_wall_" + ☃ + "',Properties:{facing:'east'}}"));
      ☃.put(☃ + "south", BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "_wall_" + ☃ + "',Properties:{facing:'south'}}"));
      ☃.put(☃ + "west", BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "_wall_" + ☃ + "',Properties:{facing:'west'}}"));

      for(int ☃ = 0; ☃ < 16; ++☃) {
         ☃.put(☃ + "" + ☃, BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "_" + ☃ + "',Properties:{rotation:'" + ☃ + "'}}"));
      }
   }

   private static void func_209301_a(Map<String, Dynamic<?>> var0, String var1, int var2) {
      ☃.put(
         "minecraft:" + ☃ + "eastlowerleftfalsefalse",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'false'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "eastlowerleftfalsetrue",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'true'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "eastlowerlefttruefalse",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'false'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "eastlowerlefttruetrue",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'true'}}")
      );
      ☃.put("minecraft:" + ☃ + "eastlowerrightfalsefalse", BlockStateFlatteningMap.func_210049_b(☃));
      ☃.put(
         "minecraft:" + ☃ + "eastlowerrightfalsetrue",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'true'}}")
      );
      ☃.put("minecraft:" + ☃ + "eastlowerrighttruefalse", BlockStateFlatteningMap.func_210049_b(☃ + 4));
      ☃.put(
         "minecraft:" + ☃ + "eastlowerrighttruetrue",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'true'}}")
      );
      ☃.put("minecraft:" + ☃ + "eastupperleftfalsefalse", BlockStateFlatteningMap.func_210049_b(☃ + 8));
      ☃.put("minecraft:" + ☃ + "eastupperleftfalsetrue", BlockStateFlatteningMap.func_210049_b(☃ + 10));
      ☃.put(
         "minecraft:" + ☃ + "eastupperlefttruefalse",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'false'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "eastupperlefttruetrue",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'true'}}")
      );
      ☃.put("minecraft:" + ☃ + "eastupperrightfalsefalse", BlockStateFlatteningMap.func_210049_b(☃ + 9));
      ☃.put("minecraft:" + ☃ + "eastupperrightfalsetrue", BlockStateFlatteningMap.func_210049_b(☃ + 11));
      ☃.put(
         "minecraft:" + ☃ + "eastupperrighttruefalse",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'false'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "eastupperrighttruetrue",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'true'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "northlowerleftfalsefalse",
         BlockStateFlatteningMap.func_210048_b(
            "{Name:'minecraft:" + ☃ + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'false'}}"
         )
      );
      ☃.put(
         "minecraft:" + ☃ + "northlowerleftfalsetrue",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'true'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "northlowerlefttruefalse",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'false'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "northlowerlefttruetrue",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'true'}}")
      );
      ☃.put("minecraft:" + ☃ + "northlowerrightfalsefalse", BlockStateFlatteningMap.func_210049_b(☃ + 3));
      ☃.put(
         "minecraft:" + ☃ + "northlowerrightfalsetrue",
         BlockStateFlatteningMap.func_210048_b(
            "{Name:'minecraft:" + ☃ + "',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'true'}}"
         )
      );
      ☃.put("minecraft:" + ☃ + "northlowerrighttruefalse", BlockStateFlatteningMap.func_210049_b(☃ + 7));
      ☃.put(
         "minecraft:" + ☃ + "northlowerrighttruetrue",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'true'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "northupperleftfalsefalse",
         BlockStateFlatteningMap.func_210048_b(
            "{Name:'minecraft:" + ☃ + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'false'}}"
         )
      );
      ☃.put(
         "minecraft:" + ☃ + "northupperleftfalsetrue",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'true'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "northupperlefttruefalse",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'false'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "northupperlefttruetrue",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'true'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "northupperrightfalsefalse",
         BlockStateFlatteningMap.func_210048_b(
            "{Name:'minecraft:" + ☃ + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'false'}}"
         )
      );
      ☃.put(
         "minecraft:" + ☃ + "northupperrightfalsetrue",
         BlockStateFlatteningMap.func_210048_b(
            "{Name:'minecraft:" + ☃ + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'true'}}"
         )
      );
      ☃.put(
         "minecraft:" + ☃ + "northupperrighttruefalse",
         BlockStateFlatteningMap.func_210048_b(
            "{Name:'minecraft:" + ☃ + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'false'}}"
         )
      );
      ☃.put(
         "minecraft:" + ☃ + "northupperrighttruetrue",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'true'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "southlowerleftfalsefalse",
         BlockStateFlatteningMap.func_210048_b(
            "{Name:'minecraft:" + ☃ + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'false'}}"
         )
      );
      ☃.put(
         "minecraft:" + ☃ + "southlowerleftfalsetrue",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'true'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "southlowerlefttruefalse",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'false'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "southlowerlefttruetrue",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'true'}}")
      );
      ☃.put("minecraft:" + ☃ + "southlowerrightfalsefalse", BlockStateFlatteningMap.func_210049_b(☃ + 1));
      ☃.put(
         "minecraft:" + ☃ + "southlowerrightfalsetrue",
         BlockStateFlatteningMap.func_210048_b(
            "{Name:'minecraft:" + ☃ + "',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'true'}}"
         )
      );
      ☃.put("minecraft:" + ☃ + "southlowerrighttruefalse", BlockStateFlatteningMap.func_210049_b(☃ + 5));
      ☃.put(
         "minecraft:" + ☃ + "southlowerrighttruetrue",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'true'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "southupperleftfalsefalse",
         BlockStateFlatteningMap.func_210048_b(
            "{Name:'minecraft:" + ☃ + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'false'}}"
         )
      );
      ☃.put(
         "minecraft:" + ☃ + "southupperleftfalsetrue",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'true'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "southupperlefttruefalse",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'false'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "southupperlefttruetrue",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'true'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "southupperrightfalsefalse",
         BlockStateFlatteningMap.func_210048_b(
            "{Name:'minecraft:" + ☃ + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'false'}}"
         )
      );
      ☃.put(
         "minecraft:" + ☃ + "southupperrightfalsetrue",
         BlockStateFlatteningMap.func_210048_b(
            "{Name:'minecraft:" + ☃ + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'true'}}"
         )
      );
      ☃.put(
         "minecraft:" + ☃ + "southupperrighttruefalse",
         BlockStateFlatteningMap.func_210048_b(
            "{Name:'minecraft:" + ☃ + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'false'}}"
         )
      );
      ☃.put(
         "minecraft:" + ☃ + "southupperrighttruetrue",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'true'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "westlowerleftfalsefalse",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'false'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "westlowerleftfalsetrue",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'true'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "westlowerlefttruefalse",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'false'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "westlowerlefttruetrue",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'true'}}")
      );
      ☃.put("minecraft:" + ☃ + "westlowerrightfalsefalse", BlockStateFlatteningMap.func_210049_b(☃ + 2));
      ☃.put(
         "minecraft:" + ☃ + "westlowerrightfalsetrue",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'true'}}")
      );
      ☃.put("minecraft:" + ☃ + "westlowerrighttruefalse", BlockStateFlatteningMap.func_210049_b(☃ + 6));
      ☃.put(
         "minecraft:" + ☃ + "westlowerrighttruetrue",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'true'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "westupperleftfalsefalse",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'false'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "westupperleftfalsetrue",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'true'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "westupperlefttruefalse",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'false'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "westupperlefttruetrue",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'true'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "westupperrightfalsefalse",
         BlockStateFlatteningMap.func_210048_b(
            "{Name:'minecraft:" + ☃ + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'false'}}"
         )
      );
      ☃.put(
         "minecraft:" + ☃ + "westupperrightfalsetrue",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'true'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "westupperrighttruefalse",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'false'}}")
      );
      ☃.put(
         "minecraft:" + ☃ + "westupperrighttruetrue",
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'true'}}")
      );
   }

   private static void func_209307_a(Map<String, Dynamic<?>> var0, int var1, String var2) {
      ☃.put(
         "southfalsefoot" + ☃,
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "_bed',Properties:{facing:'south',occupied:'false',part:'foot'}}")
      );
      ☃.put(
         "westfalsefoot" + ☃, BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "_bed',Properties:{facing:'west',occupied:'false',part:'foot'}}")
      );
      ☃.put(
         "northfalsefoot" + ☃,
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "_bed',Properties:{facing:'north',occupied:'false',part:'foot'}}")
      );
      ☃.put(
         "eastfalsefoot" + ☃, BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "_bed',Properties:{facing:'east',occupied:'false',part:'foot'}}")
      );
      ☃.put(
         "southfalsehead" + ☃,
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "_bed',Properties:{facing:'south',occupied:'false',part:'head'}}")
      );
      ☃.put(
         "westfalsehead" + ☃, BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "_bed',Properties:{facing:'west',occupied:'false',part:'head'}}")
      );
      ☃.put(
         "northfalsehead" + ☃,
         BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "_bed',Properties:{facing:'north',occupied:'false',part:'head'}}")
      );
      ☃.put(
         "eastfalsehead" + ☃, BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "_bed',Properties:{facing:'east',occupied:'false',part:'head'}}")
      );
      ☃.put(
         "southtruehead" + ☃, BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "_bed',Properties:{facing:'south',occupied:'true',part:'head'}}")
      );
      ☃.put(
         "westtruehead" + ☃, BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "_bed',Properties:{facing:'west',occupied:'true',part:'head'}}")
      );
      ☃.put(
         "northtruehead" + ☃, BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "_bed',Properties:{facing:'north',occupied:'true',part:'head'}}")
      );
      ☃.put(
         "easttruehead" + ☃, BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "_bed',Properties:{facing:'east',occupied:'true',part:'head'}}")
      );
   }

   private static void func_209297_b(Map<String, Dynamic<?>> var0, int var1, String var2) {
      for(int ☃ = 0; ☃ < 16; ++☃) {
         ☃.put("" + ☃ + "_" + ☃, BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "_banner',Properties:{rotation:'" + ☃ + "'}}"));
      }

      ☃.put("north_" + ☃, BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "_wall_banner',Properties:{facing:'north'}}"));
      ☃.put("south_" + ☃, BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "_wall_banner',Properties:{facing:'south'}}"));
      ☃.put("west_" + ☃, BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "_wall_banner',Properties:{facing:'west'}}"));
      ☃.put("east_" + ☃, BlockStateFlatteningMap.func_210048_b("{Name:'minecraft:" + ☃ + "_wall_banner',Properties:{facing:'east'}}"));
   }

   public static String func_209726_a(Dynamic<?> var0) {
      return ☃.getString("Name");
   }

   public static String func_209719_a(Dynamic<?> var0, String var1) {
      return (String)☃.get("Properties").map(var1x -> var1x.getString(☃)).orElse("");
   }

   public static int func_209724_a(IntIdentityHashBiMap<Dynamic<?>> var0, Dynamic<?> var1) {
      int ☃ = ☃.func_186815_a(☃);
      if (☃ == -1) {
         ☃ = ☃.func_186808_c(☃);
      }

      return ☃;
   }

   private Dynamic<?> func_209712_b(Dynamic<?> var1) {
      Optional<? extends Dynamic<?>> ☃ = ☃.get("Level");
      return ☃.isPresent() && ((Dynamic)☃.get()).get("Sections").flatMap(Dynamic::getStream).isPresent()
         ? ☃.set("Level", new ChunkPaletteFormat.UpgradeChunk((Dynamic<?>)☃.get()).func_210058_a())
         : ☃;
   }

   @Override
   public TypeRewriteRule makeRule() {
      Type<?> ☃ = this.getInputSchema().getType(TypeReferences.field_211287_c);
      Type<?> ☃x = this.getOutputSchema().getType(TypeReferences.field_211287_c);
      return this.writeFixAndRead("ChunkPalettedStorageFix", ☃, ☃x, this::func_209712_b);
   }

   public static int func_210957_a(boolean var0, boolean var1, boolean var2, boolean var3) {
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

   static {
      field_199147_c.set(2);
      field_199147_c.set(3);
      field_199147_c.set(110);
      field_199147_c.set(140);
      field_199147_c.set(144);
      field_199147_c.set(25);
      field_199147_c.set(86);
      field_199147_c.set(26);
      field_199147_c.set(176);
      field_199147_c.set(177);
      field_199147_c.set(175);
      field_199147_c.set(64);
      field_199147_c.set(71);
      field_199147_c.set(193);
      field_199147_c.set(194);
      field_199147_c.set(195);
      field_199147_c.set(196);
      field_199147_c.set(197);
      field_199146_b.set(54);
      field_199146_b.set(146);
      field_199146_b.set(25);
      field_199146_b.set(26);
      field_199146_b.set(51);
      field_199146_b.set(53);
      field_199146_b.set(67);
      field_199146_b.set(108);
      field_199146_b.set(109);
      field_199146_b.set(114);
      field_199146_b.set(128);
      field_199146_b.set(134);
      field_199146_b.set(135);
      field_199146_b.set(136);
      field_199146_b.set(156);
      field_199146_b.set(163);
      field_199146_b.set(164);
      field_199146_b.set(180);
      field_199146_b.set(203);
      field_199146_b.set(55);
      field_199146_b.set(85);
      field_199146_b.set(113);
      field_199146_b.set(188);
      field_199146_b.set(189);
      field_199146_b.set(190);
      field_199146_b.set(191);
      field_199146_b.set(192);
      field_199146_b.set(93);
      field_199146_b.set(94);
      field_199146_b.set(101);
      field_199146_b.set(102);
      field_199146_b.set(160);
      field_199146_b.set(106);
      field_199146_b.set(107);
      field_199146_b.set(183);
      field_199146_b.set(184);
      field_199146_b.set(185);
      field_199146_b.set(186);
      field_199146_b.set(187);
      field_199146_b.set(132);
      field_199146_b.set(139);
      field_199146_b.set(199);
   }

   public static enum Direction {
      DOWN(ChunkPaletteFormat.Direction.Offset.NEGATIVE, ChunkPaletteFormat.Direction.Axis.Y),
      UP(ChunkPaletteFormat.Direction.Offset.POSITIVE, ChunkPaletteFormat.Direction.Axis.Y),
      NORTH(ChunkPaletteFormat.Direction.Offset.NEGATIVE, ChunkPaletteFormat.Direction.Axis.Z),
      SOUTH(ChunkPaletteFormat.Direction.Offset.POSITIVE, ChunkPaletteFormat.Direction.Axis.Z),
      WEST(ChunkPaletteFormat.Direction.Offset.NEGATIVE, ChunkPaletteFormat.Direction.Axis.X),
      EAST(ChunkPaletteFormat.Direction.Offset.POSITIVE, ChunkPaletteFormat.Direction.Axis.X);

      private final ChunkPaletteFormat.Direction.Axis field_210941_g;
      private final ChunkPaletteFormat.Direction.Offset field_210942_h;

      private Direction(ChunkPaletteFormat.Direction.Offset var3, ChunkPaletteFormat.Direction.Axis var4) {
         this.field_210941_g = ☃;
         this.field_210942_h = ☃;
      }

      public ChunkPaletteFormat.Direction.Offset func_210939_a() {
         return this.field_210942_h;
      }

      public ChunkPaletteFormat.Direction.Axis func_210940_b() {
         return this.field_210941_g;
      }

      public static enum Axis {
         X,
         Y,
         Z;
      }

      public static enum Offset {
         POSITIVE(1),
         NEGATIVE(-1);

         private final int field_210938_c;

         private Offset(int var3) {
            this.field_210938_c = ☃;
         }

         public int func_210937_a() {
            return this.field_210938_c;
         }
      }
   }

   static class NibbleArray {
      private final byte[] field_210935_a;

      public NibbleArray() {
         this.field_210935_a = new byte[2048];
      }

      public NibbleArray(byte[] var1) {
         this.field_210935_a = ☃;
         if (☃.length != 2048) {
            throw new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + ☃.length);
         }
      }

      public int func_210932_a(int var1, int var2, int var3) {
         int ☃ = this.func_210934_b(☃ << 8 | ☃ << 4 | ☃);
         return this.func_210933_a(☃ << 8 | ☃ << 4 | ☃) ? this.field_210935_a[☃] & 15 : this.field_210935_a[☃] >> 4 & 15;
      }

      private boolean func_210933_a(int var1) {
         return (☃ & 1) == 0;
      }

      private int func_210934_b(int var1) {
         return ☃ >> 1;
      }
   }

   static class Section {
      private final IntIdentityHashBiMap<Dynamic<?>> field_199210_a = new IntIdentityHashBiMap<>(32);
      private Dynamic<?> field_199211_b;
      private final Dynamic<?> field_199213_d;
      private final boolean field_199214_e;
      private final Int2ObjectMap<IntList> field_199215_f = new Int2ObjectLinkedOpenHashMap<>();
      private final IntList field_199216_g = new IntArrayList();
      public int field_199212_c;
      private final Set<Dynamic<?>> field_199217_h = Sets.newIdentityHashSet();
      private final int[] field_199218_i = new int[4096];

      public Section(Dynamic<?> var1) {
         this.field_199211_b = ☃.emptyList();
         this.field_199213_d = ☃;
         this.field_199212_c = ☃.getInt("Y");
         this.field_199214_e = ☃.get("Blocks").isPresent();
      }

      public Dynamic<?> func_210056_a(int var1) {
         if (☃ >= 0 && ☃ <= 4095) {
            Dynamic<?> ☃ = this.field_199210_a.func_186813_a(this.field_199218_i[☃]);
            return ☃ == null ? ChunkPaletteFormat.field_199165_u : ☃;
         } else {
            return ChunkPaletteFormat.field_199165_u;
         }
      }

      public void func_210053_a(int var1, Dynamic<?> var2) {
         if (this.field_199217_h.add(☃)) {
            this.field_199211_b = this.field_199211_b
               .merge("%%FILTER_ME%%".equals(ChunkPaletteFormat.func_209726_a(☃)) ? ChunkPaletteFormat.field_199165_u : ☃);
         }

         this.field_199218_i[☃] = ChunkPaletteFormat.func_209724_a(this.field_199210_a, ☃);
      }

      public int func_199207_b(int var1) {
         if (!this.field_199214_e) {
            return ☃;
         } else {
            ByteBuffer ☃ = (ByteBuffer)this.field_199213_d.get("Blocks").flatMap(Dynamic::getByteBuffer).get();
            ChunkPaletteFormat.NibbleArray ☃x = (ChunkPaletteFormat.NibbleArray)this.field_199213_d
               .get("Data")
               .flatMap(Dynamic::getByteBuffer)
               .map(var0 -> new ChunkPaletteFormat.NibbleArray(DataFixUtils.toArray(var0)))
               .orElseGet(ChunkPaletteFormat.NibbleArray::new);
            ChunkPaletteFormat.NibbleArray ☃xx = (ChunkPaletteFormat.NibbleArray)this.field_199213_d
               .get("Add")
               .flatMap(Dynamic::getByteBuffer)
               .map(var0 -> new ChunkPaletteFormat.NibbleArray(DataFixUtils.toArray(var0)))
               .orElseGet(ChunkPaletteFormat.NibbleArray::new);
            this.field_199217_h.add(ChunkPaletteFormat.field_199165_u);
            ChunkPaletteFormat.func_209724_a(this.field_199210_a, ChunkPaletteFormat.field_199165_u);
            this.field_199211_b = this.field_199211_b.merge(ChunkPaletteFormat.field_199165_u);

            for(int ☃xxx = 0; ☃xxx < 4096; ++☃xxx) {
               int ☃xxxx = ☃xxx & 15;
               int ☃xxxxx = ☃xxx >> 8 & 15;
               int ☃xxxxxx = ☃xxx >> 4 & 15;
               int ☃xxxxxxx = ☃xx.func_210932_a(☃xxxx, ☃xxxxx, ☃xxxxxx) << 12 | (☃.get(☃xxx) & 255) << 4 | ☃x.func_210932_a(☃xxxx, ☃xxxxx, ☃xxxxxx);
               if (ChunkPaletteFormat.field_199147_c.get(☃xxxxxxx >> 4)) {
                  this.func_199205_a(☃xxxxxxx >> 4, ☃xxx);
               }

               if (ChunkPaletteFormat.field_199146_b.get(☃xxxxxxx >> 4)) {
                  int ☃xxxx = ChunkPaletteFormat.func_210957_a(☃xxxx == 0, ☃xxxx == 15, ☃xxxxxx == 0, ☃xxxxxx == 15);
                  if (☃xxxx == 0) {
                     this.field_199216_g.add(☃xxx);
                  } else {
                     ☃ |= ☃xxxx;
                  }
               }

               this.func_210053_a(☃xxx, BlockStateFlatteningMap.func_210049_b(☃xxxxxxx));
            }

            return ☃;
         }
      }

      private void func_199205_a(int var1, int var2) {
         IntList ☃ = this.field_199215_f.get(☃);
         if (☃ == null) {
            ☃ = new IntArrayList();
            this.field_199215_f.put(☃, ☃);
         }

         ☃.add(☃);
      }

      public Dynamic<?> func_210051_a() {
         Dynamic<?> ☃ = this.field_199213_d;
         if (!this.field_199214_e) {
            return ☃;
         } else {
            ☃ = ☃.set("Palette", this.field_199211_b);
            int ☃ = Math.max(4, DataFixUtils.ceillog2(this.field_199217_h.size()));
            BitArray ☃x = new BitArray(☃, 4096);

            for(int ☃xx = 0; ☃xx < this.field_199218_i.length; ++☃xx) {
               ☃x.func_188141_a(☃xx, this.field_199218_i[☃xx]);
            }

            ☃ = ☃.set("BlockStates", ☃.createLongList(Arrays.stream(☃x.func_188143_a())));
            ☃ = ☃.remove("Blocks");
            ☃ = ☃.remove("Data");
            return ☃.remove("Add");
         }
      }
   }

   static final class UpgradeChunk {
      private int field_199227_a;
      private final ChunkPaletteFormat.Section[] field_199228_b = new ChunkPaletteFormat.Section[16];
      private final Dynamic<?> field_199229_c;
      private final int field_199230_d;
      private final int field_199231_e;
      private final Int2ObjectMap<Dynamic<?>> field_199232_f = new Int2ObjectLinkedOpenHashMap<>(16);

      public UpgradeChunk(Dynamic<?> var1) {
         this.field_199229_c = ☃;
         this.field_199230_d = ☃.getInt("xPos") << 4;
         this.field_199231_e = ☃.getInt("zPos") << 4;
         ☃.get("TileEntities")
            .flatMap(Dynamic::getStream)
            .ifPresent(
               var1x -> var1x.forEach(
                     var1xx -> {
                        int ☃ = var1xx.getInt("x") - this.field_199230_d & 15;
                        int ☃x = var1xx.getInt("y");
                        int ☃xx = var1xx.getInt("z") - this.field_199231_e & 15;
                        int ☃xxx = ☃x << 8 | ☃xx << 4 | ☃;
                        if (this.field_199232_f.put(☃xxx, var1xx) != null) {
                           ChunkPaletteFormat.field_199145_a
                              .warn(
                                 "In chunk: {}x{} found a duplicate block entity at position: [{}, {}, {}]",
                                 this.field_199230_d,
                                 this.field_199231_e,
                                 ☃,
                                 ☃x,
                                 ☃xx
                              );
                        }
                     }
                  )
            );
         boolean ☃ = ☃.getBoolean("convertedFromAlphaFormat");
         ☃.get("Sections").flatMap(Dynamic::getStream).ifPresent(var1x -> var1x.forEach(var1xx -> {
               ChunkPaletteFormat.Section ☃ = new ChunkPaletteFormat.Section(var1xx);
               this.field_199227_a = ☃.func_199207_b(this.field_199227_a);
               this.field_199228_b[☃.field_199212_c] = ☃;
            }));

         for(ChunkPaletteFormat.Section ☃x : this.field_199228_b) {
            if (☃x != null) {
               for(java.util.Map.Entry<Integer, IntList> ☃xx : ☃x.field_199215_f.entrySet()) {
                  int ☃xxx = ☃x.field_199212_c << 12;
                  switch(☃xx.getKey()) {
                     case 2:
                        for(int ☃xxxx : (IntList)☃xx.getValue()) {
                           ☃xxxx |= ☃xxx;
                           Dynamic<?> ☃xxxxx = this.func_210064_a(☃xxxx);
                           if ("minecraft:grass_block".equals(ChunkPaletteFormat.func_209726_a(☃xxxxx))) {
                              String ☃xxxxxx = ChunkPaletteFormat.func_209726_a(this.func_210064_a(func_199223_a(☃xxxx, ChunkPaletteFormat.Direction.UP)));
                              if ("minecraft:snow".equals(☃xxxxxx) || "minecraft:snow_layer".equals(☃xxxxxx)) {
                                 this.func_210060_a(☃xxxx, ChunkPaletteFormat.field_199150_f);
                              }
                           }
                        }
                        break;
                     case 3:
                        for(int ☃xxxx : (IntList)☃xx.getValue()) {
                           ☃xxxx |= ☃xxx;
                           Dynamic<?> ☃xxxxx = this.func_210064_a(☃xxxx);
                           if ("minecraft:podzol".equals(ChunkPaletteFormat.func_209726_a(☃xxxxx))) {
                              String ☃xxxxxx = ChunkPaletteFormat.func_209726_a(this.func_210064_a(func_199223_a(☃xxxx, ChunkPaletteFormat.Direction.UP)));
                              if ("minecraft:snow".equals(☃xxxxxx) || "minecraft:snow_layer".equals(☃xxxxxx)) {
                                 this.func_210060_a(☃xxxx, ChunkPaletteFormat.field_199149_e);
                              }
                           }
                        }
                        break;
                     case 25:
                        for(int ☃xxxx : (IntList)☃xx.getValue()) {
                           ☃xxxx |= ☃xxx;
                           Dynamic<?> ☃xxxxx = this.func_210059_c(☃xxxx);
                           if (☃xxxxx != null) {
                              String ☃xxxxxx = Boolean.toString(☃xxxxx.getBoolean("powered")) + (byte)Math.min(Math.max(☃xxxxx.getByte("note"), 0), 24);
                              this.func_210060_a(
                                 ☃xxxx, (Dynamic<?>)ChunkPaletteFormat.field_199161_q.getOrDefault(☃xxxxxx, ChunkPaletteFormat.field_199161_q.get("false0"))
                              );
                           }
                        }
                        break;
                     case 26:
                        for(int ☃xxxx : (IntList)☃xx.getValue()) {
                           ☃xxxx |= ☃xxx;
                           Dynamic<?> ☃xxxxx = this.func_210066_b(☃xxxx);
                           Dynamic<?> ☃xxxxxx = this.func_210064_a(☃xxxx);
                           if (☃xxxxx != null) {
                              int ☃xxxxxxx = ☃xxxxx.getInt("color");
                              if (☃xxxxxxx != 14 && ☃xxxxxxx >= 0 && ☃xxxxxxx < 16) {
                                 String ☃xxxxxxxx = ChunkPaletteFormat.func_209719_a(☃xxxxxx, "facing")
                                    + ChunkPaletteFormat.func_209719_a(☃xxxxxx, "occupied")
                                    + ChunkPaletteFormat.func_209719_a(☃xxxxxx, "part")
                                    + ☃xxxxxxx;
                                 if (ChunkPaletteFormat.field_199163_s.containsKey(☃xxxxxxxx)) {
                                    this.func_210060_a(☃xxxx, (Dynamic<?>)ChunkPaletteFormat.field_199163_s.get(☃xxxxxxxx));
                                 }
                              }
                           }
                        }
                        break;
                     case 64:
                     case 71:
                     case 193:
                     case 194:
                     case 195:
                     case 196:
                     case 197:
                        for(int ☃xxxx : (IntList)☃xx.getValue()) {
                           ☃xxxx |= ☃xxx;
                           Dynamic<?> ☃xxxxx = this.func_210064_a(☃xxxx);
                           if (ChunkPaletteFormat.func_209726_a(☃xxxxx).endsWith("_door")) {
                              Dynamic<?> ☃xxxxxx = this.func_210064_a(☃xxxx);
                              if ("lower".equals(ChunkPaletteFormat.func_209719_a(☃xxxxxx, "half"))) {
                                 int ☃xxxxxxx = func_199223_a(☃xxxx, ChunkPaletteFormat.Direction.UP);
                                 Dynamic<?> ☃xxxxxxxx = this.func_210064_a(☃xxxxxxx);
                                 String ☃xxxxxxxxx = ChunkPaletteFormat.func_209726_a(☃xxxxxx);
                                 if (☃xxxxxxxxx.equals(ChunkPaletteFormat.func_209726_a(☃xxxxxxxx))) {
                                    String ☃xxxxxxxxxx = ChunkPaletteFormat.func_209719_a(☃xxxxxx, "facing");
                                    String ☃xxxxxxxxxxx = ChunkPaletteFormat.func_209719_a(☃xxxxxx, "open");
                                    String ☃xxxxxxxxxxxx = ☃ ? "left" : ChunkPaletteFormat.func_209719_a(☃xxxxxxxx, "hinge");
                                    String ☃xxxxxxxxxxxxx = ☃ ? "false" : ChunkPaletteFormat.func_209719_a(☃xxxxxxxx, "powered");
                                    this.func_210060_a(
                                       ☃xxxx,
                                       (Dynamic<?>)ChunkPaletteFormat.field_199160_p
                                          .get(☃xxxxxxxxx + ☃xxxxxxxxxx + "lower" + ☃xxxxxxxxxxxx + ☃xxxxxxxxxxx + ☃xxxxxxxxxxxxx)
                                    );
                                    this.func_210060_a(
                                       ☃xxxxxxx,
                                       (Dynamic<?>)ChunkPaletteFormat.field_199160_p
                                          .get(☃xxxxxxxxx + ☃xxxxxxxxxx + "upper" + ☃xxxxxxxxxxxx + ☃xxxxxxxxxxx + ☃xxxxxxxxxxxxx)
                                    );
                                 }
                              }
                           }
                        }
                        break;
                     case 86:
                        for(int ☃xxxx : (IntList)☃xx.getValue()) {
                           ☃xxxx |= ☃xxx;
                           Dynamic<?> ☃xxxxx = this.func_210064_a(☃xxxx);
                           if ("minecraft:carved_pumpkin".equals(ChunkPaletteFormat.func_209726_a(☃xxxxx))) {
                              String ☃xxxxxx = ChunkPaletteFormat.func_209726_a(this.func_210064_a(func_199223_a(☃xxxx, ChunkPaletteFormat.Direction.DOWN)));
                              if ("minecraft:grass_block".equals(☃xxxxxx) || "minecraft:dirt".equals(☃xxxxxx)) {
                                 this.func_210060_a(☃xxxx, ChunkPaletteFormat.field_199148_d);
                              }
                           }
                        }
                        break;
                     case 110:
                        for(int ☃xxxx : (IntList)☃xx.getValue()) {
                           ☃xxxx |= ☃xxx;
                           Dynamic<?> ☃xxxxx = this.func_210064_a(☃xxxx);
                           if ("minecraft:mycelium".equals(ChunkPaletteFormat.func_209726_a(☃xxxxx))) {
                              String ☃xxxxxx = ChunkPaletteFormat.func_209726_a(this.func_210064_a(func_199223_a(☃xxxx, ChunkPaletteFormat.Direction.UP)));
                              if ("minecraft:snow".equals(☃xxxxxx) || "minecraft:snow_layer".equals(☃xxxxxx)) {
                                 this.func_210060_a(☃xxxx, ChunkPaletteFormat.field_199151_g);
                              }
                           }
                        }
                        break;
                     case 140:
                        for(int ☃xxxx : (IntList)☃xx.getValue()) {
                           ☃xxxx |= ☃xxx;
                           Dynamic<?> ☃xxxxx = this.func_210059_c(☃xxxx);
                           if (☃xxxxx != null) {
                              String ☃xxxxxx = ☃xxxxx.getString("Item") + ☃xxxxx.getInt("Data");
                              this.func_210060_a(
                                 ☃xxxx,
                                 (Dynamic<?>)ChunkPaletteFormat.field_199158_n.getOrDefault(☃xxxxxx, ChunkPaletteFormat.field_199158_n.get("minecraft:air0"))
                              );
                           }
                        }
                        break;
                     case 144:
                        for(int ☃xxxx : (IntList)☃xx.getValue()) {
                           ☃xxxx |= ☃xxx;
                           Dynamic<?> ☃xxxxx = this.func_210066_b(☃xxxx);
                           if (☃xxxxx != null) {
                              String ☃xxxxxxx = String.valueOf(☃xxxxx.getByte("SkullType"));
                              String ☃xxxxxxxx = ChunkPaletteFormat.func_209719_a(this.func_210064_a(☃xxxx), "facing");
                              String ☃xxxxxx;
                              if (!"up".equals(☃xxxxxxxx) && !"down".equals(☃xxxxxxxx)) {
                                 ☃xxxxxx = ☃xxxxxxx + ☃xxxxxxxx;
                              } else {
                                 ☃xxxxxx = ☃xxxxxxx + String.valueOf(☃xxxxx.getInt("Rot"));
                              }

                              ☃xxxxx.remove("SkullType");
                              ☃xxxxx.remove("facing");
                              ☃xxxxx.remove("Rot");
                              this.func_210060_a(
                                 ☃xxxx, (Dynamic<?>)ChunkPaletteFormat.field_199159_o.getOrDefault(☃xxxxxx, ChunkPaletteFormat.field_199159_o.get("0north"))
                              );
                           }
                        }
                        break;
                     case 175:
                        for(int ☃xxxx : (IntList)☃xx.getValue()) {
                           ☃xxxx |= ☃xxx;
                           Dynamic<?> ☃xxxxx = this.func_210064_a(☃xxxx);
                           if ("upper".equals(ChunkPaletteFormat.func_209719_a(☃xxxxx, "half"))) {
                              Dynamic<?> ☃xxxxxx = this.func_210064_a(func_199223_a(☃xxxx, ChunkPaletteFormat.Direction.DOWN));
                              String ☃xxxxxxx = ChunkPaletteFormat.func_209726_a(☃xxxxxx);
                              if ("minecraft:sunflower".equals(☃xxxxxxx)) {
                                 this.func_210060_a(☃xxxx, ChunkPaletteFormat.field_199152_h);
                              } else if ("minecraft:lilac".equals(☃xxxxxxx)) {
                                 this.func_210060_a(☃xxxx, ChunkPaletteFormat.field_199153_i);
                              } else if ("minecraft:tall_grass".equals(☃xxxxxxx)) {
                                 this.func_210060_a(☃xxxx, ChunkPaletteFormat.field_199154_j);
                              } else if ("minecraft:large_fern".equals(☃xxxxxxx)) {
                                 this.func_210060_a(☃xxxx, ChunkPaletteFormat.field_199155_k);
                              } else if ("minecraft:rose_bush".equals(☃xxxxxxx)) {
                                 this.func_210060_a(☃xxxx, ChunkPaletteFormat.field_199156_l);
                              } else if ("minecraft:peony".equals(☃xxxxxxx)) {
                                 this.func_210060_a(☃xxxx, ChunkPaletteFormat.field_199157_m);
                              }
                           }
                        }
                        break;
                     case 176:
                     case 177:
                        for(int ☃xxxx : (IntList)☃xx.getValue()) {
                           ☃xxxx |= ☃xxx;
                           Dynamic<?> ☃xxxxx = this.func_210066_b(☃xxxx);
                           Dynamic<?> ☃xxxxxx = this.func_210064_a(☃xxxx);
                           if (☃xxxxx != null) {
                              int ☃xxxxxxx = ☃xxxxx.getInt("Base");
                              if (☃xxxxxxx != 15 && ☃xxxxxxx >= 0 && ☃xxxxxxx < 16) {
                                 String ☃xxxxxxxx = ChunkPaletteFormat.func_209719_a(☃xxxxxx, ☃xx.getKey() == 176 ? "rotation" : "facing") + "_" + ☃xxxxxxx;
                                 if (ChunkPaletteFormat.field_199164_t.containsKey(☃xxxxxxxx)) {
                                    this.func_210060_a(☃xxxx, (Dynamic<?>)ChunkPaletteFormat.field_199164_t.get(☃xxxxxxxx));
                                 }
                              }
                           }
                        }
                  }
               }
            }
         }
      }

      @Nullable
      private Dynamic<?> func_210066_b(int var1) {
         return this.field_199232_f.get(☃);
      }

      @Nullable
      private Dynamic<?> func_210059_c(int var1) {
         return this.field_199232_f.remove(☃);
      }

      public static int func_199223_a(int var0, ChunkPaletteFormat.Direction var1) {
         switch(☃.func_210940_b()) {
            case X: {
               int ☃ = (☃ & 15) + ☃.func_210939_a().func_210937_a();
               return ☃ >= 0 && ☃ <= 15 ? ☃ & -16 | ☃ : -1;
            }
            case Y: {
               int ☃ = (☃ >> 8) + ☃.func_210939_a().func_210937_a();
               return ☃ >= 0 && ☃ <= 255 ? ☃ & 0xFF | ☃ << 8 : -1;
            }
            case Z: {
               int ☃ = (☃ >> 4 & 15) + ☃.func_210939_a().func_210937_a();
               return ☃ >= 0 && ☃ <= 15 ? ☃ & -241 | ☃ << 4 : -1;
            }
            default:
               return -1;
         }
      }

      private void func_210060_a(int var1, Dynamic<?> var2) {
         if (☃ >= 0 && ☃ <= 65535) {
            ChunkPaletteFormat.Section ☃ = this.func_199221_d(☃);
            if (☃ != null) {
               ☃.func_210053_a(☃ & 4095, ☃);
            }
         }
      }

      @Nullable
      private ChunkPaletteFormat.Section func_199221_d(int var1) {
         int ☃ = ☃ >> 12;
         return ☃ < this.field_199228_b.length ? this.field_199228_b[☃] : null;
      }

      public Dynamic<?> func_210064_a(int var1) {
         if (☃ >= 0 && ☃ <= 65535) {
            ChunkPaletteFormat.Section ☃ = this.func_199221_d(☃);
            return ☃ == null ? ChunkPaletteFormat.field_199165_u : ☃.func_210056_a(☃ & 4095);
         } else {
            return ChunkPaletteFormat.field_199165_u;
         }
      }

      public Dynamic<?> func_210058_a() {
         Dynamic<?> ☃ = this.field_199229_c;
         if (this.field_199232_f.isEmpty()) {
            ☃ = ☃.remove("TileEntities");
         } else {
            ☃ = ☃.set("TileEntities", ☃.createList(this.field_199232_f.values().stream()));
         }

         Dynamic<?> ☃ = ☃.emptyMap();
         Dynamic<?> ☃x = ☃.emptyList();

         for(ChunkPaletteFormat.Section ☃xx : this.field_199228_b) {
            if (☃xx != null) {
               ☃x = ☃x.merge(☃xx.func_210051_a());
               ☃ = ☃.set(String.valueOf(☃xx.field_199212_c), ☃.createIntList(Arrays.stream(☃xx.field_199216_g.toIntArray())));
            }
         }

         Dynamic<?> ☃xx = ☃.emptyMap();
         ☃xx = ☃xx.set("Sides", ☃xx.createByte((byte)this.field_199227_a));
         ☃xx = ☃xx.set("Indices", ☃);
         return ☃.set("UpgradeData", ☃xx).set("Sections", ☃x);
      }
   }
}
