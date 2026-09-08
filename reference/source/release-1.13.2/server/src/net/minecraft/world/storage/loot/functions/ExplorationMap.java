package net.minecraft.world.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Locale;
import java.util.Random;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExplorationMap extends LootFunction {
   private static final Logger field_204317_a = LogManager.getLogger();
   private final String field_204318_b;
   private final MapDecoration.Type field_204319_c;
   private final byte field_204320_d;
   private final int field_204321_e;
   private final boolean field_212428_f;

   public ExplorationMap(LootCondition[] var1, String var2, MapDecoration.Type var3, byte var4, int var5, boolean var6) {
      super(☃);
      this.field_204318_b = ☃;
      this.field_204319_c = ☃;
      this.field_204320_d = ☃;
      this.field_204321_e = ☃;
      this.field_212428_f = ☃;
   }

   @Override
   public ItemStack func_186553_a(ItemStack var1, Random var2, LootContext var3) {
      if (☃.func_77973_b() != Items.field_151148_bJ) {
         return ☃;
      } else {
         BlockPos ☃ = ☃.func_204315_e();
         if (☃ == null) {
            return ☃;
         } else {
            WorldServer ☃ = ☃.func_202879_g();
            BlockPos ☃x = ☃.func_211157_a(this.field_204318_b, ☃, this.field_204321_e, this.field_212428_f);
            if (☃x != null) {
               ItemStack ☃xx = ItemMap.func_195952_a(☃, ☃x.func_177958_n(), ☃x.func_177952_p(), this.field_204320_d, true, true);
               ItemMap.func_190905_a(☃, ☃xx);
               MapData.func_191094_a(☃xx, ☃x, "+", this.field_204319_c);
               ☃xx.func_200302_a(new TextComponentTranslation("filled_map." + this.field_204318_b.toLowerCase(Locale.ROOT)));
               return ☃xx;
            } else {
               return ☃;
            }
         }
      }
   }

   public static class Serializer extends LootFunction.Serializer<ExplorationMap> {
      protected Serializer() {
         super(new ResourceLocation("exploration_map"), ExplorationMap.class);
      }

      public void func_186532_a(JsonObject var1, ExplorationMap var2, JsonSerializationContext var3) {
         ☃.add("destination", ☃.serialize(☃.field_204318_b));
         ☃.add("decoration", ☃.serialize(☃.field_204319_c.toString().toLowerCase(Locale.ROOT)));
      }

      public ExplorationMap func_186530_b(JsonObject var1, JsonDeserializationContext var2, LootCondition[] var3) {
         String ☃ = ☃.has("destination") ? JsonUtils.func_151200_h(☃, "destination") : "Buried_Treasure";
         ☃ = Feature.field_202300_at.containsKey(☃.toLowerCase(Locale.ROOT)) ? ☃ : "Buried_Treasure";
         String ☃x = ☃.has("decoration") ? JsonUtils.func_151200_h(☃, "decoration") : "mansion";
         MapDecoration.Type ☃xx = MapDecoration.Type.MANSION;

         try {
            ☃xx = MapDecoration.Type.valueOf(☃x.toUpperCase(Locale.ROOT));
         } catch (IllegalArgumentException var10) {
            ExplorationMap.field_204317_a.error("Error while parsing loot table decoration entry. Found {}. Defaulting to MANSION", ☃x);
         }

         byte ☃xxx = ☃.has("zoom") ? JsonUtils.func_204331_o(☃, "zoom") : 2;
         int ☃xxxx = ☃.has("search_radius") ? JsonUtils.func_151203_m(☃, "search_radius") : 50;
         boolean ☃xxxxx = ☃.has("skip_existing_chunks") ? JsonUtils.func_151212_i(☃, "skip_existing_chunks") : true;
         return new ExplorationMap(☃, ☃, ☃xx, ☃xxx, ☃xxxx, ☃xxxxx);
      }
   }
}
