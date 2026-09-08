package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;

public class RecipesMapExtending extends ShapedRecipe {
   public RecipesMapExtending(ResourceLocation var1) {
      super(
         ☃,
         "",
         3,
         3,
         NonNullList.func_193580_a(
            Ingredient.field_193370_a,
            Ingredient.func_199804_a(Items.field_151121_aF),
            Ingredient.func_199804_a(Items.field_151121_aF),
            Ingredient.func_199804_a(Items.field_151121_aF),
            Ingredient.func_199804_a(Items.field_151121_aF),
            Ingredient.func_199804_a(Items.field_151098_aY),
            Ingredient.func_199804_a(Items.field_151121_aF),
            Ingredient.func_199804_a(Items.field_151121_aF),
            Ingredient.func_199804_a(Items.field_151121_aF),
            Ingredient.func_199804_a(Items.field_151121_aF)
         ),
         new ItemStack(Items.field_151148_bJ)
      );
   }

   @Override
   public boolean func_77569_a(IInventory var1, World var2) {
      if (!super.func_77569_a(☃, ☃)) {
         return false;
      } else {
         ItemStack ☃ = ItemStack.field_190927_a;

         for(int ☃x = 0; ☃x < ☃.func_70302_i_() && ☃.func_190926_b(); ++☃x) {
            ItemStack ☃xx = ☃.func_70301_a(☃x);
            if (☃xx.func_77973_b() == Items.field_151098_aY) {
               ☃ = ☃xx;
            }
         }

         if (☃.func_190926_b()) {
            return false;
         } else {
            MapData ☃x = ItemMap.func_195950_a(☃, ☃);
            if (☃x == null) {
               return false;
            } else if (this.func_190934_a(☃x)) {
               return false;
            } else {
               return ☃x.field_76197_d < 4;
            }
         }
      }
   }

   private boolean func_190934_a(MapData var1) {
      if (☃.field_76203_h != null) {
         for(MapDecoration ☃ : ☃.field_76203_h.values()) {
            if (☃.func_191179_b() == MapDecoration.Type.MANSION || ☃.func_191179_b() == MapDecoration.Type.MONUMENT) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public ItemStack func_77572_b(IInventory var1) {
      ItemStack ☃ = ItemStack.field_190927_a;

      for(int ☃x = 0; ☃x < ☃.func_70302_i_() && ☃.func_190926_b(); ++☃x) {
         ItemStack ☃xx = ☃.func_70301_a(☃x);
         if (☃xx.func_77973_b() == Items.field_151098_aY) {
            ☃ = ☃xx;
         }
      }

      ☃ = ☃.func_77946_l();
      ☃.func_190920_e(1);
      ☃.func_196082_o().func_74768_a("map_scale_direction", 1);
      return ☃;
   }

   @Override
   public boolean func_192399_d() {
      return true;
   }

   @Override
   public IRecipeSerializer<?> func_199559_b() {
      return RecipeSerializers.field_199580_f;
   }
}
