package net.minecraft.world.item.crafting;

import com.google.gson.JsonObject;
import java.util.stream.Stream;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class UpgradeRecipe implements Recipe<Container> {
   final Ingredient base;
   final Ingredient addition;
   final ItemStack result;
   private final ResourceLocation id;

   public UpgradeRecipe(ResourceLocation var1, Ingredient var2, Ingredient var3, ItemStack var4) {
      this.id = â˜ƒ;
      this.base = â˜ƒ;
      this.addition = â˜ƒ;
      this.result = â˜ƒ;
   }

   @Override
   public boolean matches(Container var1, Level var2) {
      return this.base.test(â˜ƒ.getItem(0)) && this.addition.test(â˜ƒ.getItem(1));
   }

   @Override
   public ItemStack assemble(Container var1) {
      ItemStack â˜ƒ = this.result.copy();
      CompoundTag â˜ƒx = â˜ƒ.getItem(0).getTag();
      if (â˜ƒx != null) {
         â˜ƒ.setTag(â˜ƒx.copy());
      }

      return â˜ƒ;
   }

   @Override
   public boolean canCraftInDimensions(int var1, int var2) {
      return â˜ƒ * â˜ƒ >= 2;
   }

   @Override
   public ItemStack getResultItem() {
      return this.result;
   }

   public boolean isAdditionIngredient(ItemStack var1) {
      return this.addition.test(â˜ƒ);
   }

   @Override
   public ItemStack getToastSymbol() {
      return new ItemStack(Blocks.SMITHING_TABLE);
   }

   @Override
   public ResourceLocation getId() {
      return this.id;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.SMITHING;
   }

   @Override
   public RecipeType<?> getType() {
      return RecipeType.SMITHING;
   }

   @Override
   public boolean isIncomplete() {
      return Stream.of(this.base, this.addition).anyMatch(var0 -> var0.getItems().length == 0);
   }

   public static class Serializer implements RecipeSerializer<UpgradeRecipe> {
      public UpgradeRecipe fromJson(ResourceLocation var1, JsonObject var2) {
         Ingredient â˜ƒ = Ingredient.fromJson(GsonHelper.getAsJsonObject(â˜ƒ, "base"));
         Ingredient â˜ƒx = Ingredient.fromJson(GsonHelper.getAsJsonObject(â˜ƒ, "addition"));
         ItemStack â˜ƒxx = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(â˜ƒ, "result"));
         return new UpgradeRecipe(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx);
      }

      public UpgradeRecipe fromNetwork(ResourceLocation var1, FriendlyByteBuf var2) {
         Ingredient â˜ƒ = Ingredient.fromNetwork(â˜ƒ);
         Ingredient â˜ƒx = Ingredient.fromNetwork(â˜ƒ);
         ItemStack â˜ƒxx = â˜ƒ.readItem();
         return new UpgradeRecipe(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx);
      }

      public void toNetwork(FriendlyByteBuf var1, UpgradeRecipe var2) {
         â˜ƒ.base.toNetwork(â˜ƒ);
         â˜ƒ.addition.toNetwork(â˜ƒ);
         â˜ƒ.writeItem(â˜ƒ.result);
      }
   }
}
