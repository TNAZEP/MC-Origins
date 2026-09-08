package net.minecraft.item.crafting;

import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.World;

public class FurnaceRecipe implements IRecipe {
   private final ResourceLocation field_201832_a;
   private final String field_201833_b;
   private final Ingredient field_201834_c;
   private final ItemStack field_201835_d;
   private final float field_201836_e;
   private final int field_201837_f;

   public FurnaceRecipe(ResourceLocation var1, String var2, Ingredient var3, ItemStack var4, float var5, int var6) {
      this.field_201832_a = ☃;
      this.field_201833_b = ☃;
      this.field_201834_c = ☃;
      this.field_201835_d = ☃;
      this.field_201836_e = ☃;
      this.field_201837_f = ☃;
   }

   @Override
   public boolean func_77569_a(IInventory var1, World var2) {
      return ☃ instanceof TileEntityFurnace && this.field_201834_c.test(☃.func_70301_a(0));
   }

   @Override
   public ItemStack func_77572_b(IInventory var1) {
      return this.field_201835_d.func_77946_l();
   }

   @Override
   public IRecipeSerializer<?> func_199559_b() {
      return RecipeSerializers.field_201839_p;
   }

   @Override
   public NonNullList<Ingredient> func_192400_c() {
      NonNullList<Ingredient> ☃ = NonNullList.func_191196_a();
      ☃.add(this.field_201834_c);
      return ☃;
   }

   public float func_201831_g() {
      return this.field_201836_e;
   }

   @Override
   public ItemStack func_77571_b() {
      return this.field_201835_d;
   }

   public int func_201830_h() {
      return this.field_201837_f;
   }

   @Override
   public ResourceLocation func_199560_c() {
      return this.field_201832_a;
   }

   public static class Serializer implements IRecipeSerializer<FurnaceRecipe> {
      public FurnaceRecipe func_199425_a_(ResourceLocation var1, JsonObject var2) {
         String ☃x = JsonUtils.func_151219_a(☃, "group", "");
         Ingredient ☃;
         if (JsonUtils.func_151202_d(☃, "ingredient")) {
            ☃ = Ingredient.func_199802_a(JsonUtils.func_151214_t(☃, "ingredient"));
         } else {
            ☃ = Ingredient.func_199802_a(JsonUtils.func_152754_s(☃, "ingredient"));
         }

         String ☃ = JsonUtils.func_151200_h(☃, "result");
         Item ☃x = IRegistry.field_212630_s.func_212608_b(new ResourceLocation(☃));
         if (☃x != null) {
            ItemStack ☃xx = new ItemStack(☃x);
            float var8 = JsonUtils.func_151221_a(☃, "experience", 0.0F);
            int var9 = JsonUtils.func_151208_a(☃, "cookingtime", 200);
            return new FurnaceRecipe(☃, ☃x, ☃, ☃xx, var8, var9);
         } else {
            throw new IllegalStateException(☃ + " did not exist");
         }
      }

      public FurnaceRecipe func_199426_a_(ResourceLocation var1, PacketBuffer var2) {
         String ☃ = ☃.func_150789_c(32767);
         Ingredient ☃x = Ingredient.func_199566_b(☃);
         ItemStack ☃xx = ☃.func_150791_c();
         float ☃xxx = ☃.readFloat();
         int ☃xxxx = ☃.func_150792_a();
         return new FurnaceRecipe(☃, ☃, ☃x, ☃xx, ☃xxx, ☃xxxx);
      }

      public void func_199427_a_(PacketBuffer var1, FurnaceRecipe var2) {
         ☃.func_180714_a(☃.field_201833_b);
         ☃.field_201834_c.func_199564_a(☃);
         ☃.func_150788_a(☃.field_201835_d);
         ☃.writeFloat(☃.field_201836_e);
         ☃.func_150787_b(☃.field_201837_f);
      }

      @Override
      public String func_199567_a() {
         return "smelting";
      }
   }
}
