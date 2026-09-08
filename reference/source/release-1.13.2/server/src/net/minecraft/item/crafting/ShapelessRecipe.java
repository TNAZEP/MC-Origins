package net.minecraft.item.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ShapelessRecipe implements IRecipe {
   private final ResourceLocation field_199562_a;
   private final String field_194138_c;
   private final ItemStack field_77580_a;
   private final NonNullList<Ingredient> field_77579_b;

   public ShapelessRecipe(ResourceLocation var1, String var2, ItemStack var3, NonNullList<Ingredient> var4) {
      this.field_199562_a = ☃;
      this.field_194138_c = ☃;
      this.field_77580_a = ☃;
      this.field_77579_b = ☃;
   }

   @Override
   public ResourceLocation func_199560_c() {
      return this.field_199562_a;
   }

   @Override
   public IRecipeSerializer<?> func_199559_b() {
      return RecipeSerializers.field_199576_b;
   }

   @Override
   public ItemStack func_77571_b() {
      return this.field_77580_a;
   }

   @Override
   public NonNullList<Ingredient> func_192400_c() {
      return this.field_77579_b;
   }

   @Override
   public boolean func_77569_a(IInventory var1, World var2) {
      if (!(☃ instanceof InventoryCrafting)) {
         return false;
      } else {
         RecipeItemHelper ☃ = new RecipeItemHelper();
         int ☃x = 0;

         for(int ☃xx = 0; ☃xx < ☃.func_174923_h(); ++☃xx) {
            for(int ☃xxx = 0; ☃xxx < ☃.func_174922_i(); ++☃xxx) {
               ItemStack ☃xxxx = ☃.func_70301_a(☃xxx + ☃xx * ☃.func_174922_i());
               if (!☃xxxx.func_190926_b()) {
                  ++☃x;
                  ☃.func_194112_a(new ItemStack(☃xxxx.func_77973_b()));
               }
            }
         }

         return ☃x == this.field_77579_b.size() && ☃.func_194116_a(this, null);
      }
   }

   @Override
   public ItemStack func_77572_b(IInventory var1) {
      return this.field_77580_a.func_77946_l();
   }

   public static class Serializer implements IRecipeSerializer<ShapelessRecipe> {
      public ShapelessRecipe func_199425_a_(ResourceLocation var1, JsonObject var2) {
         String ☃ = JsonUtils.func_151219_a(☃, "group", "");
         NonNullList<Ingredient> ☃x = func_199568_a(JsonUtils.func_151214_t(☃, "ingredients"));
         if (☃x.isEmpty()) {
            throw new JsonParseException("No ingredients for shapeless recipe");
         } else if (☃x.size() > 9) {
            throw new JsonParseException("Too many ingredients for shapeless recipe");
         } else {
            ItemStack ☃ = ShapedRecipe.func_199798_a(JsonUtils.func_152754_s(☃, "result"));
            return new ShapelessRecipe(☃, ☃, ☃, ☃x);
         }
      }

      private static NonNullList<Ingredient> func_199568_a(JsonArray var0) {
         NonNullList<Ingredient> ☃ = NonNullList.func_191196_a();

         for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
            Ingredient ☃xx = Ingredient.func_199802_a(☃.get(☃x));
            if (!☃xx.func_203189_d()) {
               ☃.add(☃xx);
            }
         }

         return ☃;
      }

      @Override
      public String func_199567_a() {
         return "crafting_shapeless";
      }

      public ShapelessRecipe func_199426_a_(ResourceLocation var1, PacketBuffer var2) {
         String ☃ = ☃.func_150789_c(32767);
         int ☃x = ☃.func_150792_a();
         NonNullList<Ingredient> ☃xx = NonNullList.func_191197_a(☃x, Ingredient.field_193370_a);

         for(int ☃xxx = 0; ☃xxx < ☃xx.size(); ++☃xxx) {
            ☃xx.set(☃xxx, Ingredient.func_199566_b(☃));
         }

         ItemStack ☃xxx = ☃.func_150791_c();
         return new ShapelessRecipe(☃, ☃, ☃xxx, ☃xx);
      }

      public void func_199427_a_(PacketBuffer var1, ShapelessRecipe var2) {
         ☃.func_180714_a(☃.field_194138_c);
         ☃.func_150787_b(☃.field_77579_b.size());

         for(Ingredient ☃ : ☃.field_77579_b) {
            ☃.func_199564_a(☃);
         }

         ☃.func_150788_a(☃.field_77580_a);
      }
   }
}
