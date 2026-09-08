package net.minecraft.item.crafting;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.World;

public class ShapedRecipe implements IRecipe {
   private final int field_77576_b;
   private final int field_77577_c;
   private final NonNullList<Ingredient> field_77574_d;
   private final ItemStack field_77575_e;
   private final ResourceLocation field_199561_e;
   private final String field_194137_e;

   public ShapedRecipe(ResourceLocation var1, String var2, int var3, int var4, NonNullList<Ingredient> var5, ItemStack var6) {
      this.field_199561_e = ☃;
      this.field_194137_e = ☃;
      this.field_77576_b = ☃;
      this.field_77577_c = ☃;
      this.field_77574_d = ☃;
      this.field_77575_e = ☃;
   }

   @Override
   public ResourceLocation func_199560_c() {
      return this.field_199561_e;
   }

   @Override
   public IRecipeSerializer<?> func_199559_b() {
      return RecipeSerializers.field_199575_a;
   }

   @Override
   public ItemStack func_77571_b() {
      return this.field_77575_e;
   }

   @Override
   public NonNullList<Ingredient> func_192400_c() {
      return this.field_77574_d;
   }

   @Override
   public boolean func_77569_a(IInventory var1, World var2) {
      if (!(☃ instanceof InventoryCrafting)) {
         return false;
      } else {
         for(int ☃ = 0; ☃ <= ☃.func_174922_i() - this.field_77576_b; ++☃) {
            for(int ☃x = 0; ☃x <= ☃.func_174923_h() - this.field_77577_c; ++☃x) {
               if (this.func_77573_a(☃, ☃, ☃x, true)) {
                  return true;
               }

               if (this.func_77573_a(☃, ☃, ☃x, false)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private boolean func_77573_a(IInventory var1, int var2, int var3, boolean var4) {
      for(int ☃ = 0; ☃ < ☃.func_174922_i(); ++☃) {
         for(int ☃x = 0; ☃x < ☃.func_174923_h(); ++☃x) {
            int ☃xx = ☃ - ☃;
            int ☃xxx = ☃x - ☃;
            Ingredient ☃xxxx = Ingredient.field_193370_a;
            if (☃xx >= 0 && ☃xxx >= 0 && ☃xx < this.field_77576_b && ☃xxx < this.field_77577_c) {
               if (☃) {
                  ☃xxxx = this.field_77574_d.get(this.field_77576_b - ☃xx - 1 + ☃xxx * this.field_77576_b);
               } else {
                  ☃xxxx = this.field_77574_d.get(☃xx + ☃xxx * this.field_77576_b);
               }
            }

            if (!☃xxxx.test(☃.func_70301_a(☃ + ☃x * ☃.func_174922_i()))) {
               return false;
            }
         }
      }

      return true;
   }

   @Override
   public ItemStack func_77572_b(IInventory var1) {
      return this.func_77571_b().func_77946_l();
   }

   public int func_192403_f() {
      return this.field_77576_b;
   }

   public int func_192404_g() {
      return this.field_77577_c;
   }

   private static NonNullList<Ingredient> func_192402_a(String[] var0, Map<String, Ingredient> var1, int var2, int var3) {
      NonNullList<Ingredient> ☃ = NonNullList.func_191197_a(☃ * ☃, Ingredient.field_193370_a);
      Set<String> ☃x = Sets.newHashSet(☃.keySet());
      ☃x.remove(" ");

      for(int ☃xx = 0; ☃xx < ☃.length; ++☃xx) {
         for(int ☃xxx = 0; ☃xxx < ☃[☃xx].length(); ++☃xxx) {
            String ☃xxxx = ☃[☃xx].substring(☃xxx, ☃xxx + 1);
            Ingredient ☃xxxxx = (Ingredient)☃.get(☃xxxx);
            if (☃xxxxx == null) {
               throw new JsonSyntaxException("Pattern references symbol '" + ☃xxxx + "' but it's not defined in the key");
            }

            ☃x.remove(☃xxxx);
            ☃.set(☃xxx + ☃ * ☃xx, ☃xxxxx);
         }
      }

      if (!☃x.isEmpty()) {
         throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + ☃x);
      } else {
         return ☃;
      }
   }

   @VisibleForTesting
   static String[] func_194134_a(String... var0) {
      int ☃ = Integer.MAX_VALUE;
      int ☃x = 0;
      int ☃xx = 0;
      int ☃xxx = 0;

      for(int ☃xxxx = 0; ☃xxxx < ☃.length; ++☃xxxx) {
         String ☃xxxxx = ☃[☃xxxx];
         ☃ = Math.min(☃, func_194135_a(☃xxxxx));
         int ☃xxxxxx = func_194136_b(☃xxxxx);
         ☃x = Math.max(☃x, ☃xxxxxx);
         if (☃xxxxxx < 0) {
            if (☃xx == ☃xxxx) {
               ++☃xx;
            }

            ++☃xxx;
         } else {
            ☃xxx = 0;
         }
      }

      if (☃.length == ☃xxx) {
         return new String[0];
      } else {
         String[] ☃xxxx = new String[☃.length - ☃xxx - ☃xx];

         for(int ☃xxxxx = 0; ☃xxxxx < ☃xxxx.length; ++☃xxxxx) {
            ☃xxxx[☃xxxxx] = ☃[☃xxxxx + ☃xx].substring(☃, ☃x + 1);
         }

         return ☃xxxx;
      }
   }

   private static int func_194135_a(String var0) {
      int ☃ = 0;

      while(☃ < ☃.length() && ☃.charAt(☃) == ' ') {
         ++☃;
      }

      return ☃;
   }

   private static int func_194136_b(String var0) {
      int ☃ = ☃.length() - 1;

      while(☃ >= 0 && ☃.charAt(☃) == ' ') {
         --☃;
      }

      return ☃;
   }

   private static String[] func_192407_a(JsonArray var0) {
      String[] ☃ = new String[☃.size()];
      if (☃.length > 3) {
         throw new JsonSyntaxException("Invalid pattern: too many rows, 3 is maximum");
      } else if (☃.length == 0) {
         throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
      } else {
         for(int ☃ = 0; ☃ < ☃.length; ++☃) {
            String ☃x = JsonUtils.func_151206_a(☃.get(☃), "pattern[" + ☃ + "]");
            if (☃x.length() > 3) {
               throw new JsonSyntaxException("Invalid pattern: too many columns, 3 is maximum");
            }

            if (☃ > 0 && ☃[0].length() != ☃x.length()) {
               throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
            }

            ☃[☃] = ☃x;
         }

         return ☃;
      }
   }

   private static Map<String, Ingredient> func_192408_a(JsonObject var0) {
      Map<String, Ingredient> ☃ = Maps.newHashMap();

      for(Entry<String, JsonElement> ☃x : ☃.entrySet()) {
         if (((String)☃x.getKey()).length() != 1) {
            throw new JsonSyntaxException("Invalid key entry: '" + (String)☃x.getKey() + "' is an invalid symbol (must be 1 character only).");
         }

         if (" ".equals(☃x.getKey())) {
            throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
         }

         ☃.put(☃x.getKey(), Ingredient.func_199802_a((JsonElement)☃x.getValue()));
      }

      ☃.put(" ", Ingredient.field_193370_a);
      return ☃;
   }

   public static ItemStack func_199798_a(JsonObject var0) {
      String ☃ = JsonUtils.func_151200_h(☃, "item");
      Item ☃x = IRegistry.field_212630_s.func_212608_b(new ResourceLocation(☃));
      if (☃x == null) {
         throw new JsonSyntaxException("Unknown item '" + ☃ + "'");
      } else if (☃.has("data")) {
         throw new JsonParseException("Disallowed data tag found");
      } else {
         int ☃ = JsonUtils.func_151208_a(☃, "count", 1);
         return new ItemStack(☃x, ☃);
      }
   }

   public static class Serializer implements IRecipeSerializer<ShapedRecipe> {
      public ShapedRecipe func_199425_a_(ResourceLocation var1, JsonObject var2) {
         String ☃ = JsonUtils.func_151219_a(☃, "group", "");
         Map<String, Ingredient> ☃x = ShapedRecipe.func_192408_a(JsonUtils.func_152754_s(☃, "key"));
         String[] ☃xx = ShapedRecipe.func_194134_a(ShapedRecipe.func_192407_a(JsonUtils.func_151214_t(☃, "pattern")));
         int ☃xxx = ☃xx[0].length();
         int ☃xxxx = ☃xx.length;
         NonNullList<Ingredient> ☃xxxxx = ShapedRecipe.func_192402_a(☃xx, ☃x, ☃xxx, ☃xxxx);
         ItemStack ☃xxxxxx = ShapedRecipe.func_199798_a(JsonUtils.func_152754_s(☃, "result"));
         return new ShapedRecipe(☃, ☃, ☃xxx, ☃xxxx, ☃xxxxx, ☃xxxxxx);
      }

      @Override
      public String func_199567_a() {
         return "crafting_shaped";
      }

      public ShapedRecipe func_199426_a_(ResourceLocation var1, PacketBuffer var2) {
         int ☃ = ☃.func_150792_a();
         int ☃x = ☃.func_150792_a();
         String ☃xx = ☃.func_150789_c(32767);
         NonNullList<Ingredient> ☃xxx = NonNullList.func_191197_a(☃ * ☃x, Ingredient.field_193370_a);

         for(int ☃xxxx = 0; ☃xxxx < ☃xxx.size(); ++☃xxxx) {
            ☃xxx.set(☃xxxx, Ingredient.func_199566_b(☃));
         }

         ItemStack ☃xxxx = ☃.func_150791_c();
         return new ShapedRecipe(☃, ☃xx, ☃, ☃x, ☃xxx, ☃xxxx);
      }

      public void func_199427_a_(PacketBuffer var1, ShapedRecipe var2) {
         ☃.func_150787_b(☃.field_77576_b);
         ☃.func_150787_b(☃.field_77577_c);
         ☃.func_180714_a(☃.field_194137_e);

         for(Ingredient ☃ : ☃.field_77574_d) {
            ☃.func_199564_a(☃);
         }

         ☃.func_150788_a(☃.field_77575_e);
      }
   }
}
