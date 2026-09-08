package net.minecraft.world.item.crafting;

import com.google.gson.JsonObject;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class SimpleRecipeSerializer<T extends Recipe<?>> implements RecipeSerializer<T> {
   private final Function<ResourceLocation, T> constructor;

   public SimpleRecipeSerializer(Function<ResourceLocation, T> var1) {
      this.constructor = â˜ƒ;
   }

   @Override
   public T fromJson(ResourceLocation var1, JsonObject var2) {
      return (T)this.constructor.apply(â˜ƒ);
   }

   @Override
   public T fromNetwork(ResourceLocation var1, FriendlyByteBuf var2) {
      return (T)this.constructor.apply(â˜ƒ);
   }

   @Override
   public void toNetwork(FriendlyByteBuf var1, T var2) {
   }
}
