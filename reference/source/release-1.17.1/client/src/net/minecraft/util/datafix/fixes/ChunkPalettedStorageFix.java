package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.util.CrudeIncrementalIntIdentityHashBiMap;
import net.minecraft.util.datafix.PackedBitStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkPalettedStorageFix extends DataFix {
   private static final int NORTH_WEST_MASK = 128;
   private static final int WEST_MASK = 64;
   private static final int SOUTH_WEST_MASK = 32;
   private static final int SOUTH_MASK = 16;
   private static final int SOUTH_EAST_MASK = 8;
   private static final int EAST_MASK = 4;
   private static final int NORTH_EAST_MASK = 2;
   private static final int NORTH_MASK = 1;
   static final Logger LOGGER = LogManager.getLogger();
   static final BitSet VIRTUAL = new BitSet(256);
   static final BitSet FIX = new BitSet(256);
   static final Dynamic<?> PUMPKIN = BlockStateData.parse("{Name:'minecraft:pumpkin'}");
   static final Dynamic<?> SNOWY_PODZOL = BlockStateData.parse("{Name:'minecraft:podzol',Properties:{snowy:'true'}}");
   static final Dynamic<?> SNOWY_GRASS = BlockStateData.parse("{Name:'minecraft:grass_block',Properties:{snowy:'true'}}");
   static final Dynamic<?> SNOWY_MYCELIUM = BlockStateData.parse("{Name:'minecraft:mycelium',Properties:{snowy:'true'}}");
   static final Dynamic<?> UPPER_SUNFLOWER = BlockStateData.parse("{Name:'minecraft:sunflower',Properties:{half:'upper'}}");
   static final Dynamic<?> UPPER_LILAC = BlockStateData.parse("{Name:'minecraft:lilac',Properties:{half:'upper'}}");
   static final Dynamic<?> UPPER_TALL_GRASS = BlockStateData.parse("{Name:'minecraft:tall_grass',Properties:{half:'upper'}}");
   static final Dynamic<?> UPPER_LARGE_FERN = BlockStateData.parse("{Name:'minecraft:large_fern',Properties:{half:'upper'}}");
   static final Dynamic<?> UPPER_ROSE_BUSH = BlockStateData.parse("{Name:'minecraft:rose_bush',Properties:{half:'upper'}}");
   static final Dynamic<?> UPPER_PEONY = BlockStateData.parse("{Name:'minecraft:peony',Properties:{half:'upper'}}");
   static final Map<String, Dynamic<?>> FLOWER_POT_MAP = DataFixUtils.make(Maps.newHashMap(), var0 -> {
      var0.put("minecraft:air0", BlockStateData.parse("{Name:'minecraft:flower_pot'}"));
      var0.put("minecraft:red_flower0", BlockStateData.parse("{Name:'minecraft:potted_poppy'}"));
      var0.put("minecraft:red_flower1", BlockStateData.parse("{Name:'minecraft:potted_blue_orchid'}"));
      var0.put("minecraft:red_flower2", BlockStateData.parse("{Name:'minecraft:potted_allium'}"));
      var0.put("minecraft:red_flower3", BlockStateData.parse("{Name:'minecraft:potted_azure_bluet'}"));
      var0.put("minecraft:red_flower4", BlockStateData.parse("{Name:'minecraft:potted_red_tulip'}"));
      var0.put("minecraft:red_flower5", BlockStateData.parse("{Name:'minecraft:potted_orange_tulip'}"));
      var0.put("minecraft:red_flower6", BlockStateData.parse("{Name:'minecraft:potted_white_tulip'}"));
      var0.put("minecraft:red_flower7", BlockStateData.parse("{Name:'minecraft:potted_pink_tulip'}"));
      var0.put("minecraft:red_flower8", BlockStateData.parse("{Name:'minecraft:potted_oxeye_daisy'}"));
      var0.put("minecraft:yellow_flower0", BlockStateData.parse("{Name:'minecraft:potted_dandelion'}"));
      var0.put("minecraft:sapling0", BlockStateData.parse("{Name:'minecraft:potted_oak_sapling'}"));
      var0.put("minecraft:sapling1", BlockStateData.parse("{Name:'minecraft:potted_spruce_sapling'}"));
      var0.put("minecraft:sapling2", BlockStateData.parse("{Name:'minecraft:potted_birch_sapling'}"));
      var0.put("minecraft:sapling3", BlockStateData.parse("{Name:'minecraft:potted_jungle_sapling'}"));
      var0.put("minecraft:sapling4", BlockStateData.parse("{Name:'minecraft:potted_acacia_sapling'}"));
      var0.put("minecraft:sapling5", BlockStateData.parse("{Name:'minecraft:potted_dark_oak_sapling'}"));
      var0.put("minecraft:red_mushroom0", BlockStateData.parse("{Name:'minecraft:potted_red_mushroom'}"));
      var0.put("minecraft:brown_mushroom0", BlockStateData.parse("{Name:'minecraft:potted_brown_mushroom'}"));
      var0.put("minecraft:deadbush0", BlockStateData.parse("{Name:'minecraft:potted_dead_bush'}"));
      var0.put("minecraft:tallgrass2", BlockStateData.parse("{Name:'minecraft:potted_fern'}"));
      var0.put("minecraft:cactus0", BlockStateData.getTag(2240));
   });
   static final Map<String, Dynamic<?>> SKULL_MAP = DataFixUtils.make(Maps.newHashMap(), var0 -> {
      mapSkull(var0, 0, "skeleton", "skull");
      mapSkull(var0, 1, "wither_skeleton", "skull");
      mapSkull(var0, 2, "zombie", "head");
      mapSkull(var0, 3, "player", "head");
      mapSkull(var0, 4, "creeper", "head");
      mapSkull(var0, 5, "dragon", "head");
   });
   static final Map<String, Dynamic<?>> DOOR_MAP = DataFixUtils.make(Maps.newHashMap(), var0 -> {
      mapDoor(var0, "oak_door", 1024);
      mapDoor(var0, "iron_door", 1136);
      mapDoor(var0, "spruce_door", 3088);
      mapDoor(var0, "birch_door", 3104);
      mapDoor(var0, "jungle_door", 3120);
      mapDoor(var0, "acacia_door", 3136);
      mapDoor(var0, "dark_oak_door", 3152);
   });
   static final Map<String, Dynamic<?>> NOTE_BLOCK_MAP = DataFixUtils.make(Maps.newHashMap(), var0 -> {
      for(int â˜ƒ = 0; â˜ƒ < 26; ++â˜ƒ) {
         var0.put("true" + â˜ƒ, BlockStateData.parse("{Name:'minecraft:note_block',Properties:{powered:'true',note:'" + â˜ƒ + "'}}"));
         var0.put("false" + â˜ƒ, BlockStateData.parse("{Name:'minecraft:note_block',Properties:{powered:'false',note:'" + â˜ƒ + "'}}"));
      }
   });
   private static final Int2ObjectMap<String> DYE_COLOR_MAP = DataFixUtils.make(new Int2ObjectOpenHashMap(), var0 -> {
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
   static final Map<String, Dynamic<?>> BED_BLOCK_MAP = DataFixUtils.make(Maps.newHashMap(), var0 -> {
      for(Entry<String> â˜ƒ : DYE_COLOR_MAP.int2ObjectEntrySet()) {
         if (!Objects.equals(â˜ƒ.getValue(), "red")) {
            addBeds(var0, â˜ƒ.getIntKey(), (String)â˜ƒ.getValue());
         }
      }
   });
   static final Map<String, Dynamic<?>> BANNER_BLOCK_MAP = DataFixUtils.make(Maps.newHashMap(), var0 -> {
      for(Entry<String> â˜ƒ : DYE_COLOR_MAP.int2ObjectEntrySet()) {
         if (!Objects.equals(â˜ƒ.getValue(), "white")) {
            addBanners(var0, 15 - â˜ƒ.getIntKey(), (String)â˜ƒ.getValue());
         }
      }
   });
   static final Dynamic<?> AIR = BlockStateData.getTag(0);
   private static final int SIZE = 4096;

   public ChunkPalettedStorageFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   private static void mapSkull(Map<String, Dynamic<?>> var0, int var1, String var2, String var3) {
      â˜ƒ.put(â˜ƒ + "north", BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "_wall_" + â˜ƒ + "',Properties:{facing:'north'}}"));
      â˜ƒ.put(â˜ƒ + "east", BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "_wall_" + â˜ƒ + "',Properties:{facing:'east'}}"));
      â˜ƒ.put(â˜ƒ + "south", BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "_wall_" + â˜ƒ + "',Properties:{facing:'south'}}"));
      â˜ƒ.put(â˜ƒ + "west", BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "_wall_" + â˜ƒ + "',Properties:{facing:'west'}}"));

      for(int â˜ƒ = 0; â˜ƒ < 16; ++â˜ƒ) {
         â˜ƒ.put("" + â˜ƒ + â˜ƒ, BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "_" + â˜ƒ + "',Properties:{rotation:'" + â˜ƒ + "'}}"));
      }
   }

   private static void mapDoor(Map<String, Dynamic<?>> var0, String var1, int var2) {
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "eastlowerleftfalsefalse",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'false'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "eastlowerleftfalsetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'true'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "eastlowerlefttruefalse",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'false'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "eastlowerlefttruetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'true'}}")
      );
      â˜ƒ.put("minecraft:" + â˜ƒ + "eastlowerrightfalsefalse", BlockStateData.getTag(â˜ƒ));
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "eastlowerrightfalsetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'true'}}")
      );
      â˜ƒ.put("minecraft:" + â˜ƒ + "eastlowerrighttruefalse", BlockStateData.getTag(â˜ƒ + 4));
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "eastlowerrighttruetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'true'}}")
      );
      â˜ƒ.put("minecraft:" + â˜ƒ + "eastupperleftfalsefalse", BlockStateData.getTag(â˜ƒ + 8));
      â˜ƒ.put("minecraft:" + â˜ƒ + "eastupperleftfalsetrue", BlockStateData.getTag(â˜ƒ + 10));
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "eastupperlefttruefalse",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'false'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "eastupperlefttruetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'true'}}")
      );
      â˜ƒ.put("minecraft:" + â˜ƒ + "eastupperrightfalsefalse", BlockStateData.getTag(â˜ƒ + 9));
      â˜ƒ.put("minecraft:" + â˜ƒ + "eastupperrightfalsetrue", BlockStateData.getTag(â˜ƒ + 11));
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "eastupperrighttruefalse",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'false'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "eastupperrighttruetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'true'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "northlowerleftfalsefalse",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'false'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "northlowerleftfalsetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'true'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "northlowerlefttruefalse",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'false'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "northlowerlefttruetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'true'}}")
      );
      â˜ƒ.put("minecraft:" + â˜ƒ + "northlowerrightfalsefalse", BlockStateData.getTag(â˜ƒ + 3));
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "northlowerrightfalsetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'true'}}")
      );
      â˜ƒ.put("minecraft:" + â˜ƒ + "northlowerrighttruefalse", BlockStateData.getTag(â˜ƒ + 7));
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "northlowerrighttruetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'true'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "northupperleftfalsefalse",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'false'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "northupperleftfalsetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'true'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "northupperlefttruefalse",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'false'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "northupperlefttruetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'true'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "northupperrightfalsefalse",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'false'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "northupperrightfalsetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'true'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "northupperrighttruefalse",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'false'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "northupperrighttruetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'true'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "southlowerleftfalsefalse",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'false'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "southlowerleftfalsetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'true'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "southlowerlefttruefalse",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'false'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "southlowerlefttruetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'true'}}")
      );
      â˜ƒ.put("minecraft:" + â˜ƒ + "southlowerrightfalsefalse", BlockStateData.getTag(â˜ƒ + 1));
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "southlowerrightfalsetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'true'}}")
      );
      â˜ƒ.put("minecraft:" + â˜ƒ + "southlowerrighttruefalse", BlockStateData.getTag(â˜ƒ + 5));
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "southlowerrighttruetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'true'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "southupperleftfalsefalse",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'false'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "southupperleftfalsetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'true'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "southupperlefttruefalse",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'false'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "southupperlefttruetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'true'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "southupperrightfalsefalse",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'false'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "southupperrightfalsetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'true'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "southupperrighttruefalse",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'false'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "southupperrighttruetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'true'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "westlowerleftfalsefalse",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'false'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "westlowerleftfalsetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'true'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "westlowerlefttruefalse",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'false'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "westlowerlefttruetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'true'}}")
      );
      â˜ƒ.put("minecraft:" + â˜ƒ + "westlowerrightfalsefalse", BlockStateData.getTag(â˜ƒ + 2));
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "westlowerrightfalsetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'true'}}")
      );
      â˜ƒ.put("minecraft:" + â˜ƒ + "westlowerrighttruefalse", BlockStateData.getTag(â˜ƒ + 6));
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "westlowerrighttruetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'true'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "westupperleftfalsefalse",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'false'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "westupperleftfalsetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'true'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "westupperlefttruefalse",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'false'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "westupperlefttruetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'true'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "westupperrightfalsefalse",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'false'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "westupperrightfalsetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'true'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "westupperrighttruefalse",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'false'}}")
      );
      â˜ƒ.put(
         "minecraft:" + â˜ƒ + "westupperrighttruetrue",
         BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'true'}}")
      );
   }

   private static void addBeds(Map<String, Dynamic<?>> var0, int var1, String var2) {
      â˜ƒ.put("southfalsefoot" + â˜ƒ, BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "_bed',Properties:{facing:'south',occupied:'false',part:'foot'}}"));
      â˜ƒ.put("westfalsefoot" + â˜ƒ, BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "_bed',Properties:{facing:'west',occupied:'false',part:'foot'}}"));
      â˜ƒ.put("northfalsefoot" + â˜ƒ, BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "_bed',Properties:{facing:'north',occupied:'false',part:'foot'}}"));
      â˜ƒ.put("eastfalsefoot" + â˜ƒ, BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "_bed',Properties:{facing:'east',occupied:'false',part:'foot'}}"));
      â˜ƒ.put("southfalsehead" + â˜ƒ, BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "_bed',Properties:{facing:'south',occupied:'false',part:'head'}}"));
      â˜ƒ.put("westfalsehead" + â˜ƒ, BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "_bed',Properties:{facing:'west',occupied:'false',part:'head'}}"));
      â˜ƒ.put("northfalsehead" + â˜ƒ, BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "_bed',Properties:{facing:'north',occupied:'false',part:'head'}}"));
      â˜ƒ.put("eastfalsehead" + â˜ƒ, BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "_bed',Properties:{facing:'east',occupied:'false',part:'head'}}"));
      â˜ƒ.put("southtruehead" + â˜ƒ, BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "_bed',Properties:{facing:'south',occupied:'true',part:'head'}}"));
      â˜ƒ.put("westtruehead" + â˜ƒ, BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "_bed',Properties:{facing:'west',occupied:'true',part:'head'}}"));
      â˜ƒ.put("northtruehead" + â˜ƒ, BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "_bed',Properties:{facing:'north',occupied:'true',part:'head'}}"));
      â˜ƒ.put("easttruehead" + â˜ƒ, BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "_bed',Properties:{facing:'east',occupied:'true',part:'head'}}"));
   }

   private static void addBanners(Map<String, Dynamic<?>> var0, int var1, String var2) {
      for(int â˜ƒ = 0; â˜ƒ < 16; ++â˜ƒ) {
         â˜ƒ.put(â˜ƒ + "_" + â˜ƒ, BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "_banner',Properties:{rotation:'" + â˜ƒ + "'}}"));
      }

      â˜ƒ.put("north_" + â˜ƒ, BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "_wall_banner',Properties:{facing:'north'}}"));
      â˜ƒ.put("south_" + â˜ƒ, BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "_wall_banner',Properties:{facing:'south'}}"));
      â˜ƒ.put("west_" + â˜ƒ, BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "_wall_banner',Properties:{facing:'west'}}"));
      â˜ƒ.put("east_" + â˜ƒ, BlockStateData.parse("{Name:'minecraft:" + â˜ƒ + "_wall_banner',Properties:{facing:'east'}}"));
   }

   public static String getName(Dynamic<?> var0) {
      return â˜ƒ.get("Name").asString("");
   }

   public static String getProperty(Dynamic<?> var0, String var1) {
      return â˜ƒ.get("Properties").get(â˜ƒ).asString("");
   }

   public static int idFor(CrudeIncrementalIntIdentityHashBiMap<Dynamic<?>> var0, Dynamic<?> var1) {
      int â˜ƒ = â˜ƒ.getId(â˜ƒ);
      if (â˜ƒ == -1) {
         â˜ƒ = â˜ƒ.add(â˜ƒ);
      }

      return â˜ƒ;
   }

   private Dynamic<?> fix(Dynamic<?> var1) {
      Optional<? extends Dynamic<?>> â˜ƒ = â˜ƒ.get("Level").result();
      return â˜ƒ.isPresent() && ((Dynamic)â˜ƒ.get()).get("Sections").asStreamOpt().result().isPresent()
         ? â˜ƒ.set("Level", new ChunkPalettedStorageFix.UpgradeChunk((Dynamic<?>)â˜ƒ.get()).write())
         : â˜ƒ;
   }

   @Override
   public TypeRewriteRule makeRule() {
      Type<?> â˜ƒ = this.getInputSchema().getType(References.CHUNK);
      Type<?> â˜ƒx = this.getOutputSchema().getType(References.CHUNK);
      return this.writeFixAndRead("ChunkPalettedStorageFix", â˜ƒ, â˜ƒx, this::fix);
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

   static {
      FIX.set(2);
      FIX.set(3);
      FIX.set(110);
      FIX.set(140);
      FIX.set(144);
      FIX.set(25);
      FIX.set(86);
      FIX.set(26);
      FIX.set(176);
      FIX.set(177);
      FIX.set(175);
      FIX.set(64);
      FIX.set(71);
      FIX.set(193);
      FIX.set(194);
      FIX.set(195);
      FIX.set(196);
      FIX.set(197);
      VIRTUAL.set(54);
      VIRTUAL.set(146);
      VIRTUAL.set(25);
      VIRTUAL.set(26);
      VIRTUAL.set(51);
      VIRTUAL.set(53);
      VIRTUAL.set(67);
      VIRTUAL.set(108);
      VIRTUAL.set(109);
      VIRTUAL.set(114);
      VIRTUAL.set(128);
      VIRTUAL.set(134);
      VIRTUAL.set(135);
      VIRTUAL.set(136);
      VIRTUAL.set(156);
      VIRTUAL.set(163);
      VIRTUAL.set(164);
      VIRTUAL.set(180);
      VIRTUAL.set(203);
      VIRTUAL.set(55);
      VIRTUAL.set(85);
      VIRTUAL.set(113);
      VIRTUAL.set(188);
      VIRTUAL.set(189);
      VIRTUAL.set(190);
      VIRTUAL.set(191);
      VIRTUAL.set(192);
      VIRTUAL.set(93);
      VIRTUAL.set(94);
      VIRTUAL.set(101);
      VIRTUAL.set(102);
      VIRTUAL.set(160);
      VIRTUAL.set(106);
      VIRTUAL.set(107);
      VIRTUAL.set(183);
      VIRTUAL.set(184);
      VIRTUAL.set(185);
      VIRTUAL.set(186);
      VIRTUAL.set(187);
      VIRTUAL.set(132);
      VIRTUAL.set(139);
      VIRTUAL.set(199);
   }

   static class DataLayer {
      private static final int SIZE = 2048;
      private static final int NIBBLE_SIZE = 4;
      private final byte[] data;

      public DataLayer() {
         this.data = new byte[2048];
      }

      public DataLayer(byte[] var1) {
         this.data = â˜ƒ;
         if (â˜ƒ.length != 2048) {
            throw new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + â˜ƒ.length);
         }
      }

      public int get(int var1, int var2, int var3) {
         int â˜ƒ = this.getPosition(â˜ƒ << 8 | â˜ƒ << 4 | â˜ƒ);
         return this.isFirst(â˜ƒ << 8 | â˜ƒ << 4 | â˜ƒ) ? this.data[â˜ƒ] & 15 : this.data[â˜ƒ] >> 4 & 15;
      }

      private boolean isFirst(int var1) {
         return (â˜ƒ & 1) == 0;
      }

      private int getPosition(int var1) {
         return â˜ƒ >> 1;
      }
   }

   public static enum Direction {
      DOWN(ChunkPalettedStorageFix.Direction.AxisDirection.NEGATIVE, ChunkPalettedStorageFix.Direction.Axis.Y),
      UP(ChunkPalettedStorageFix.Direction.AxisDirection.POSITIVE, ChunkPalettedStorageFix.Direction.Axis.Y),
      NORTH(ChunkPalettedStorageFix.Direction.AxisDirection.NEGATIVE, ChunkPalettedStorageFix.Direction.Axis.Z),
      SOUTH(ChunkPalettedStorageFix.Direction.AxisDirection.POSITIVE, ChunkPalettedStorageFix.Direction.Axis.Z),
      WEST(ChunkPalettedStorageFix.Direction.AxisDirection.NEGATIVE, ChunkPalettedStorageFix.Direction.Axis.X),
      EAST(ChunkPalettedStorageFix.Direction.AxisDirection.POSITIVE, ChunkPalettedStorageFix.Direction.Axis.X);

      private final ChunkPalettedStorageFix.Direction.Axis axis;
      private final ChunkPalettedStorageFix.Direction.AxisDirection axisDirection;

      private Direction(ChunkPalettedStorageFix.Direction.AxisDirection var3, ChunkPalettedStorageFix.Direction.Axis var4) {
         this.axis = â˜ƒ;
         this.axisDirection = â˜ƒ;
      }

      public ChunkPalettedStorageFix.Direction.AxisDirection getAxisDirection() {
         return this.axisDirection;
      }

      public ChunkPalettedStorageFix.Direction.Axis getAxis() {
         return this.axis;
      }

      public static enum Axis {
         X,
         Y,
         Z;
      }

      public static enum AxisDirection {
         POSITIVE(1),
         NEGATIVE(-1);

         private final int step;

         private AxisDirection(int var3) {
            this.step = â˜ƒ;
         }

         public int getStep() {
            return this.step;
         }
      }
   }

   static class Section {
      private final CrudeIncrementalIntIdentityHashBiMap<Dynamic<?>> palette = new CrudeIncrementalIntIdentityHashBiMap<>(32);
      private final List<Dynamic<?>> listTag;
      private final Dynamic<?> section;
      private final boolean hasData;
      final Int2ObjectMap<IntList> toFix = new Int2ObjectLinkedOpenHashMap<>();
      final IntList update = new IntArrayList();
      public final int y;
      private final Set<Dynamic<?>> seen = Sets.newIdentityHashSet();
      private final int[] buffer = new int[4096];

      public Section(Dynamic<?> var1) {
         this.listTag = Lists.<Dynamic<?>>newArrayList();
         this.section = â˜ƒ;
         this.y = â˜ƒ.get("Y").asInt(0);
         this.hasData = â˜ƒ.get("Blocks").result().isPresent();
      }

      public Dynamic<?> getBlock(int var1) {
         if (â˜ƒ >= 0 && â˜ƒ <= 4095) {
            Dynamic<?> â˜ƒ = this.palette.byId(this.buffer[â˜ƒ]);
            return â˜ƒ == null ? ChunkPalettedStorageFix.AIR : â˜ƒ;
         } else {
            return ChunkPalettedStorageFix.AIR;
         }
      }

      public void setBlock(int var1, Dynamic<?> var2) {
         if (this.seen.add(â˜ƒ)) {
            this.listTag.add("%%FILTER_ME%%".equals(ChunkPalettedStorageFix.getName(â˜ƒ)) ? ChunkPalettedStorageFix.AIR : â˜ƒ);
         }

         this.buffer[â˜ƒ] = ChunkPalettedStorageFix.idFor(this.palette, â˜ƒ);
      }

      public int upgrade(int var1) {
         if (!this.hasData) {
            return â˜ƒ;
         } else {
            ByteBuffer â˜ƒ = (ByteBuffer)this.section.get("Blocks").asByteBufferOpt().result().get();
            ChunkPalettedStorageFix.DataLayer â˜ƒx = (ChunkPalettedStorageFix.DataLayer)this.section
               .get("Data")
               .asByteBufferOpt()
               .map(var0 -> new ChunkPalettedStorageFix.DataLayer(DataFixUtils.toArray(var0)))
               .result()
               .orElseGet(ChunkPalettedStorageFix.DataLayer::new);
            ChunkPalettedStorageFix.DataLayer â˜ƒxx = (ChunkPalettedStorageFix.DataLayer)this.section
               .get("Add")
               .asByteBufferOpt()
               .map(var0 -> new ChunkPalettedStorageFix.DataLayer(DataFixUtils.toArray(var0)))
               .result()
               .orElseGet(ChunkPalettedStorageFix.DataLayer::new);
            this.seen.add(ChunkPalettedStorageFix.AIR);
            ChunkPalettedStorageFix.idFor(this.palette, ChunkPalettedStorageFix.AIR);
            this.listTag.add(ChunkPalettedStorageFix.AIR);

            for(int â˜ƒxxx = 0; â˜ƒxxx < 4096; ++â˜ƒxxx) {
               int â˜ƒxxxx = â˜ƒxxx & 15;
               int â˜ƒxxxxx = â˜ƒxxx >> 8 & 15;
               int â˜ƒxxxxxx = â˜ƒxxx >> 4 & 15;
               int â˜ƒxxxxxxx = â˜ƒxx.get(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx) << 12 | (â˜ƒ.get(â˜ƒxxx) & 255) << 4 | â˜ƒx.get(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx);
               if (ChunkPalettedStorageFix.FIX.get(â˜ƒxxxxxxx >> 4)) {
                  this.addFix(â˜ƒxxxxxxx >> 4, â˜ƒxxx);
               }

               if (ChunkPalettedStorageFix.VIRTUAL.get(â˜ƒxxxxxxx >> 4)) {
                  int â˜ƒxxxx = ChunkPalettedStorageFix.getSideMask(â˜ƒxxxx == 0, â˜ƒxxxx == 15, â˜ƒxxxxxx == 0, â˜ƒxxxxxx == 15);
                  if (â˜ƒxxxx == 0) {
                     this.update.add(â˜ƒxxx);
                  } else {
                     â˜ƒ |= â˜ƒxxxx;
                  }
               }

               this.setBlock(â˜ƒxxx, BlockStateData.getTag(â˜ƒxxxxxxx));
            }

            return â˜ƒ;
         }
      }

      private void addFix(int var1, int var2) {
         IntList â˜ƒ = this.toFix.get(â˜ƒ);
         if (â˜ƒ == null) {
            â˜ƒ = new IntArrayList();
            this.toFix.put(â˜ƒ, â˜ƒ);
         }

         â˜ƒ.add(â˜ƒ);
      }

      public Dynamic<?> write() {
         Dynamic<?> â˜ƒ = this.section;
         if (!this.hasData) {
            return â˜ƒ;
         } else {
            â˜ƒ = â˜ƒ.set("Palette", â˜ƒ.createList(this.listTag.stream()));
            int â˜ƒ = Math.max(4, DataFixUtils.ceillog2(this.seen.size()));
            PackedBitStorage â˜ƒx = new PackedBitStorage(â˜ƒ, 4096);

            for(int â˜ƒxx = 0; â˜ƒxx < this.buffer.length; ++â˜ƒxx) {
               â˜ƒx.set(â˜ƒxx, this.buffer[â˜ƒxx]);
            }

            â˜ƒ = â˜ƒ.set("BlockStates", â˜ƒ.createLongList(Arrays.stream(â˜ƒx.getRaw())));
            â˜ƒ = â˜ƒ.remove("Blocks");
            â˜ƒ = â˜ƒ.remove("Data");
            return â˜ƒ.remove("Add");
         }
      }
   }

   static final class UpgradeChunk {
      private int sides;
      private final ChunkPalettedStorageFix.Section[] sections = new ChunkPalettedStorageFix.Section[16];
      private final Dynamic<?> level;
      private final int x;
      private final int z;
      private final Int2ObjectMap<Dynamic<?>> blockEntities = new Int2ObjectLinkedOpenHashMap<>(16);

      public UpgradeChunk(Dynamic<?> var1) {
         this.level = â˜ƒ;
         this.x = â˜ƒ.get("xPos").asInt(0) << 4;
         this.z = â˜ƒ.get("zPos").asInt(0) << 4;
         â˜ƒ.get("TileEntities")
            .asStreamOpt()
            .result()
            .ifPresent(
               var1x -> var1x.forEach(
                     var1xx -> {
                        int â˜ƒ = var1xx.get("x").asInt(0) - this.x & 15;
                        int â˜ƒx = var1xx.get("y").asInt(0);
                        int â˜ƒxx = var1xx.get("z").asInt(0) - this.z & 15;
                        int â˜ƒxxx = â˜ƒx << 8 | â˜ƒxx << 4 | â˜ƒ;
                        if (this.blockEntities.put(â˜ƒxxx, var1xx) != null) {
                           ChunkPalettedStorageFix.LOGGER
                              .warn("In chunk: {}x{} found a duplicate block entity at position: [{}, {}, {}]", this.x, this.z, â˜ƒ, â˜ƒx, â˜ƒxx);
                        }
                     }
                  )
            );
         boolean â˜ƒ = â˜ƒ.get("convertedFromAlphaFormat").asBoolean(false);
         â˜ƒ.get("Sections").asStreamOpt().result().ifPresent(var1x -> var1x.forEach(var1xx -> {
               ChunkPalettedStorageFix.Section â˜ƒ = new ChunkPalettedStorageFix.Section(var1xx);
               this.sides = â˜ƒ.upgrade(this.sides);
               this.sections[â˜ƒ.y] = â˜ƒ;
            }));

         for(ChunkPalettedStorageFix.Section â˜ƒx : this.sections) {
            if (â˜ƒx != null) {
               for(java.util.Map.Entry<Integer, IntList> â˜ƒxx : â˜ƒx.toFix.entrySet()) {
                  int â˜ƒxxx = â˜ƒx.y << 12;
                  switch(â˜ƒxx.getKey()) {
                     case 2:
                        for(int â˜ƒxxxx : (IntList)â˜ƒxx.getValue()) {
                           â˜ƒxxxx |= â˜ƒxxx;
                           Dynamic<?> â˜ƒxxxxx = this.getBlock(â˜ƒxxxx);
                           if ("minecraft:grass_block".equals(ChunkPalettedStorageFix.getName(â˜ƒxxxxx))) {
                              String â˜ƒxxxxxx = ChunkPalettedStorageFix.getName(this.getBlock(relative(â˜ƒxxxx, ChunkPalettedStorageFix.Direction.UP)));
                              if ("minecraft:snow".equals(â˜ƒxxxxxx) || "minecraft:snow_layer".equals(â˜ƒxxxxxx)) {
                                 this.setBlock(â˜ƒxxxx, ChunkPalettedStorageFix.SNOWY_GRASS);
                              }
                           }
                        }
                        break;
                     case 3:
                        for(int â˜ƒxxxx : (IntList)â˜ƒxx.getValue()) {
                           â˜ƒxxxx |= â˜ƒxxx;
                           Dynamic<?> â˜ƒxxxxx = this.getBlock(â˜ƒxxxx);
                           if ("minecraft:podzol".equals(ChunkPalettedStorageFix.getName(â˜ƒxxxxx))) {
                              String â˜ƒxxxxxx = ChunkPalettedStorageFix.getName(this.getBlock(relative(â˜ƒxxxx, ChunkPalettedStorageFix.Direction.UP)));
                              if ("minecraft:snow".equals(â˜ƒxxxxxx) || "minecraft:snow_layer".equals(â˜ƒxxxxxx)) {
                                 this.setBlock(â˜ƒxxxx, ChunkPalettedStorageFix.SNOWY_PODZOL);
                              }
                           }
                        }
                        break;
                     case 25:
                        for(int â˜ƒxxxx : (IntList)â˜ƒxx.getValue()) {
                           â˜ƒxxxx |= â˜ƒxxx;
                           Dynamic<?> â˜ƒxxxxx = this.removeBlockEntity(â˜ƒxxxx);
                           if (â˜ƒxxxxx != null) {
                              String â˜ƒxxxxxx = Boolean.toString(â˜ƒxxxxx.get("powered").asBoolean(false))
                                 + (byte)Math.min(Math.max(â˜ƒxxxxx.get("note").asInt(0), 0), 24);
                              this.setBlock(
                                 â˜ƒxxxx,
                                 (Dynamic<?>)ChunkPalettedStorageFix.NOTE_BLOCK_MAP
                                    .getOrDefault(â˜ƒxxxxxx, (Dynamic)ChunkPalettedStorageFix.NOTE_BLOCK_MAP.get("false0"))
                              );
                           }
                        }
                        break;
                     case 26:
                        for(int â˜ƒxxxx : (IntList)â˜ƒxx.getValue()) {
                           â˜ƒxxxx |= â˜ƒxxx;
                           Dynamic<?> â˜ƒxxxxx = this.getBlockEntity(â˜ƒxxxx);
                           Dynamic<?> â˜ƒxxxxxx = this.getBlock(â˜ƒxxxx);
                           if (â˜ƒxxxxx != null) {
                              int â˜ƒxxxxxxx = â˜ƒxxxxx.get("color").asInt(0);
                              if (â˜ƒxxxxxxx != 14 && â˜ƒxxxxxxx >= 0 && â˜ƒxxxxxxx < 16) {
                                 String â˜ƒxxxxxxxx = ChunkPalettedStorageFix.getProperty(â˜ƒxxxxxx, "facing")
                                    + ChunkPalettedStorageFix.getProperty(â˜ƒxxxxxx, "occupied")
                                    + ChunkPalettedStorageFix.getProperty(â˜ƒxxxxxx, "part")
                                    + â˜ƒxxxxxxx;
                                 if (ChunkPalettedStorageFix.BED_BLOCK_MAP.containsKey(â˜ƒxxxxxxxx)) {
                                    this.setBlock(â˜ƒxxxx, (Dynamic<?>)ChunkPalettedStorageFix.BED_BLOCK_MAP.get(â˜ƒxxxxxxxx));
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
                        for(int â˜ƒxxxx : (IntList)â˜ƒxx.getValue()) {
                           â˜ƒxxxx |= â˜ƒxxx;
                           Dynamic<?> â˜ƒxxxxx = this.getBlock(â˜ƒxxxx);
                           if (ChunkPalettedStorageFix.getName(â˜ƒxxxxx).endsWith("_door")) {
                              Dynamic<?> â˜ƒxxxxxx = this.getBlock(â˜ƒxxxx);
                              if ("lower".equals(ChunkPalettedStorageFix.getProperty(â˜ƒxxxxxx, "half"))) {
                                 int â˜ƒxxxxxxx = relative(â˜ƒxxxx, ChunkPalettedStorageFix.Direction.UP);
                                 Dynamic<?> â˜ƒxxxxxxxx = this.getBlock(â˜ƒxxxxxxx);
                                 String â˜ƒxxxxxxxxx = ChunkPalettedStorageFix.getName(â˜ƒxxxxxx);
                                 if (â˜ƒxxxxxxxxx.equals(ChunkPalettedStorageFix.getName(â˜ƒxxxxxxxx))) {
                                    String â˜ƒxxxxxxxxxx = ChunkPalettedStorageFix.getProperty(â˜ƒxxxxxx, "facing");
                                    String â˜ƒxxxxxxxxxxx = ChunkPalettedStorageFix.getProperty(â˜ƒxxxxxx, "open");
                                    String â˜ƒxxxxxxxxxxxx = â˜ƒ ? "left" : ChunkPalettedStorageFix.getProperty(â˜ƒxxxxxxxx, "hinge");
                                    String â˜ƒxxxxxxxxxxxxx = â˜ƒ ? "false" : ChunkPalettedStorageFix.getProperty(â˜ƒxxxxxxxx, "powered");
                                    this.setBlock(
                                       â˜ƒxxxx,
                                       (Dynamic<?>)ChunkPalettedStorageFix.DOOR_MAP
                                          .get(â˜ƒxxxxxxxxx + â˜ƒxxxxxxxxxx + "lower" + â˜ƒxxxxxxxxxxxx + â˜ƒxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxx)
                                    );
                                    this.setBlock(
                                       â˜ƒxxxxxxx,
                                       (Dynamic<?>)ChunkPalettedStorageFix.DOOR_MAP
                                          .get(â˜ƒxxxxxxxxx + â˜ƒxxxxxxxxxx + "upper" + â˜ƒxxxxxxxxxxxx + â˜ƒxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxx)
                                    );
                                 }
                              }
                           }
                        }
                        break;
                     case 86:
                        for(int â˜ƒxxxx : (IntList)â˜ƒxx.getValue()) {
                           â˜ƒxxxx |= â˜ƒxxx;
                           Dynamic<?> â˜ƒxxxxx = this.getBlock(â˜ƒxxxx);
                           if ("minecraft:carved_pumpkin".equals(ChunkPalettedStorageFix.getName(â˜ƒxxxxx))) {
                              String â˜ƒxxxxxx = ChunkPalettedStorageFix.getName(this.getBlock(relative(â˜ƒxxxx, ChunkPalettedStorageFix.Direction.DOWN)));
                              if ("minecraft:grass_block".equals(â˜ƒxxxxxx) || "minecraft:dirt".equals(â˜ƒxxxxxx)) {
                                 this.setBlock(â˜ƒxxxx, ChunkPalettedStorageFix.PUMPKIN);
                              }
                           }
                        }
                        break;
                     case 110:
                        for(int â˜ƒxxxx : (IntList)â˜ƒxx.getValue()) {
                           â˜ƒxxxx |= â˜ƒxxx;
                           Dynamic<?> â˜ƒxxxxx = this.getBlock(â˜ƒxxxx);
                           if ("minecraft:mycelium".equals(ChunkPalettedStorageFix.getName(â˜ƒxxxxx))) {
                              String â˜ƒxxxxxx = ChunkPalettedStorageFix.getName(this.getBlock(relative(â˜ƒxxxx, ChunkPalettedStorageFix.Direction.UP)));
                              if ("minecraft:snow".equals(â˜ƒxxxxxx) || "minecraft:snow_layer".equals(â˜ƒxxxxxx)) {
                                 this.setBlock(â˜ƒxxxx, ChunkPalettedStorageFix.SNOWY_MYCELIUM);
                              }
                           }
                        }
                        break;
                     case 140:
                        for(int â˜ƒxxxx : (IntList)â˜ƒxx.getValue()) {
                           â˜ƒxxxx |= â˜ƒxxx;
                           Dynamic<?> â˜ƒxxxxx = this.removeBlockEntity(â˜ƒxxxx);
                           if (â˜ƒxxxxx != null) {
                              String â˜ƒxxxxxx = â˜ƒxxxxx.get("Item").asString("") + â˜ƒxxxxx.get("Data").asInt(0);
                              this.setBlock(
                                 â˜ƒxxxx,
                                 (Dynamic<?>)ChunkPalettedStorageFix.FLOWER_POT_MAP
                                    .getOrDefault(â˜ƒxxxxxx, (Dynamic)ChunkPalettedStorageFix.FLOWER_POT_MAP.get("minecraft:air0"))
                              );
                           }
                        }
                        break;
                     case 144:
                        for(int â˜ƒxxxx : (IntList)â˜ƒxx.getValue()) {
                           â˜ƒxxxx |= â˜ƒxxx;
                           Dynamic<?> â˜ƒxxxxx = this.getBlockEntity(â˜ƒxxxx);
                           if (â˜ƒxxxxx != null) {
                              String â˜ƒxxxxxxx = String.valueOf(â˜ƒxxxxx.get("SkullType").asInt(0));
                              String â˜ƒxxxxxxxx = ChunkPalettedStorageFix.getProperty(this.getBlock(â˜ƒxxxx), "facing");
                              String â˜ƒxxxxxx;
                              if (!"up".equals(â˜ƒxxxxxxxx) && !"down".equals(â˜ƒxxxxxxxx)) {
                                 â˜ƒxxxxxx = â˜ƒxxxxxxx + â˜ƒxxxxxxxx;
                              } else {
                                 â˜ƒxxxxxx = â˜ƒxxxxxxx + â˜ƒxxxxx.get("Rot").asInt(0);
                              }

                              â˜ƒxxxxx.remove("SkullType");
                              â˜ƒxxxxx.remove("facing");
                              â˜ƒxxxxx.remove("Rot");
                              this.setBlock(
                                 â˜ƒxxxx,
                                 (Dynamic<?>)ChunkPalettedStorageFix.SKULL_MAP
                                    .getOrDefault(â˜ƒxxxxxx, (Dynamic)ChunkPalettedStorageFix.SKULL_MAP.get("0north"))
                              );
                           }
                        }
                        break;
                     case 175:
                        for(int â˜ƒxxxx : (IntList)â˜ƒxx.getValue()) {
                           â˜ƒxxxx |= â˜ƒxxx;
                           Dynamic<?> â˜ƒxxxxx = this.getBlock(â˜ƒxxxx);
                           if ("upper".equals(ChunkPalettedStorageFix.getProperty(â˜ƒxxxxx, "half"))) {
                              Dynamic<?> â˜ƒxxxxxx = this.getBlock(relative(â˜ƒxxxx, ChunkPalettedStorageFix.Direction.DOWN));
                              String â˜ƒxxxxxxx = ChunkPalettedStorageFix.getName(â˜ƒxxxxxx);
                              if ("minecraft:sunflower".equals(â˜ƒxxxxxxx)) {
                                 this.setBlock(â˜ƒxxxx, ChunkPalettedStorageFix.UPPER_SUNFLOWER);
                              } else if ("minecraft:lilac".equals(â˜ƒxxxxxxx)) {
                                 this.setBlock(â˜ƒxxxx, ChunkPalettedStorageFix.UPPER_LILAC);
                              } else if ("minecraft:tall_grass".equals(â˜ƒxxxxxxx)) {
                                 this.setBlock(â˜ƒxxxx, ChunkPalettedStorageFix.UPPER_TALL_GRASS);
                              } else if ("minecraft:large_fern".equals(â˜ƒxxxxxxx)) {
                                 this.setBlock(â˜ƒxxxx, ChunkPalettedStorageFix.UPPER_LARGE_FERN);
                              } else if ("minecraft:rose_bush".equals(â˜ƒxxxxxxx)) {
                                 this.setBlock(â˜ƒxxxx, ChunkPalettedStorageFix.UPPER_ROSE_BUSH);
                              } else if ("minecraft:peony".equals(â˜ƒxxxxxxx)) {
                                 this.setBlock(â˜ƒxxxx, ChunkPalettedStorageFix.UPPER_PEONY);
                              }
                           }
                        }
                        break;
                     case 176:
                     case 177:
                        for(int â˜ƒxxxx : (IntList)â˜ƒxx.getValue()) {
                           â˜ƒxxxx |= â˜ƒxxx;
                           Dynamic<?> â˜ƒxxxxx = this.getBlockEntity(â˜ƒxxxx);
                           Dynamic<?> â˜ƒxxxxxx = this.getBlock(â˜ƒxxxx);
                           if (â˜ƒxxxxx != null) {
                              int â˜ƒxxxxxxx = â˜ƒxxxxx.get("Base").asInt(0);
                              if (â˜ƒxxxxxxx != 15 && â˜ƒxxxxxxx >= 0 && â˜ƒxxxxxxx < 16) {
                                 String â˜ƒxxxxxxxx = ChunkPalettedStorageFix.getProperty(â˜ƒxxxxxx, â˜ƒxx.getKey() == 176 ? "rotation" : "facing")
                                    + "_"
                                    + â˜ƒxxxxxxx;
                                 if (ChunkPalettedStorageFix.BANNER_BLOCK_MAP.containsKey(â˜ƒxxxxxxxx)) {
                                    this.setBlock(â˜ƒxxxx, (Dynamic<?>)ChunkPalettedStorageFix.BANNER_BLOCK_MAP.get(â˜ƒxxxxxxxx));
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
      private Dynamic<?> getBlockEntity(int var1) {
         return this.blockEntities.get(â˜ƒ);
      }

      @Nullable
      private Dynamic<?> removeBlockEntity(int var1) {
         return this.blockEntities.remove(â˜ƒ);
      }

      public static int relative(int var0, ChunkPalettedStorageFix.Direction var1) {
         switch(â˜ƒ.getAxis()) {
            case X: {
               int â˜ƒ = (â˜ƒ & 15) + â˜ƒ.getAxisDirection().getStep();
               return â˜ƒ >= 0 && â˜ƒ <= 15 ? â˜ƒ & -16 | â˜ƒ : -1;
            }
            case Y: {
               int â˜ƒ = (â˜ƒ >> 8) + â˜ƒ.getAxisDirection().getStep();
               return â˜ƒ >= 0 && â˜ƒ <= 255 ? â˜ƒ & 0xFF | â˜ƒ << 8 : -1;
            }
            case Z: {
               int â˜ƒ = (â˜ƒ >> 4 & 15) + â˜ƒ.getAxisDirection().getStep();
               return â˜ƒ >= 0 && â˜ƒ <= 15 ? â˜ƒ & -241 | â˜ƒ << 4 : -1;
            }
            default:
               return -1;
         }
      }

      private void setBlock(int var1, Dynamic<?> var2) {
         if (â˜ƒ >= 0 && â˜ƒ <= 65535) {
            ChunkPalettedStorageFix.Section â˜ƒ = this.getSection(â˜ƒ);
            if (â˜ƒ != null) {
               â˜ƒ.setBlock(â˜ƒ & 4095, â˜ƒ);
            }
         }
      }

      @Nullable
      private ChunkPalettedStorageFix.Section getSection(int var1) {
         int â˜ƒ = â˜ƒ >> 12;
         return â˜ƒ < this.sections.length ? this.sections[â˜ƒ] : null;
      }

      public Dynamic<?> getBlock(int var1) {
         if (â˜ƒ >= 0 && â˜ƒ <= 65535) {
            ChunkPalettedStorageFix.Section â˜ƒ = this.getSection(â˜ƒ);
            return â˜ƒ == null ? ChunkPalettedStorageFix.AIR : â˜ƒ.getBlock(â˜ƒ & 4095);
         } else {
            return ChunkPalettedStorageFix.AIR;
         }
      }

      public Dynamic<?> write() {
         Dynamic<?> â˜ƒ = this.level;
         if (this.blockEntities.isEmpty()) {
            â˜ƒ = â˜ƒ.remove("TileEntities");
         } else {
            â˜ƒ = â˜ƒ.set("TileEntities", â˜ƒ.createList(this.blockEntities.values().stream()));
         }

         Dynamic<?> â˜ƒ = â˜ƒ.emptyMap();
         List<Dynamic<?>> â˜ƒx = Lists.<Dynamic<?>>newArrayList();

         for(ChunkPalettedStorageFix.Section â˜ƒxx : this.sections) {
            if (â˜ƒxx != null) {
               â˜ƒx.add(â˜ƒxx.write());
               â˜ƒ = â˜ƒ.set(String.valueOf(â˜ƒxx.y), â˜ƒ.createIntList(Arrays.stream(â˜ƒxx.update.toIntArray())));
            }
         }

         Dynamic<?> â˜ƒxx = â˜ƒ.emptyMap();
         â˜ƒxx = â˜ƒxx.set("Sides", â˜ƒxx.createByte((byte)this.sides));
         â˜ƒxx = â˜ƒxx.set("Indices", â˜ƒ);
         return â˜ƒ.set("UpgradeData", â˜ƒxx).set("Sections", â˜ƒxx.createList(â˜ƒx.stream()));
      }
   }
}
