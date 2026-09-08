package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ClientboundUpdateRecipesPacket implements Packet<ClientGamePacketListener> {
   private final List<Recipe<?>> recipes;

   public ClientboundUpdateRecipesPacket(Collection<Recipe<?>> var1) {
      this.recipes = Lists.<Recipe<?>>newArrayList(â˜ƒ);
   }

   public ClientboundUpdateRecipesPacket(FriendlyByteBuf var1) {
      this.recipes = â˜ƒ.readList(ClientboundUpdateRecipesPacket::fromNetwork);
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeCollection(this.recipes, ClientboundUpdateRecipesPacket::toNetwork);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleUpdateRecipes(this);
   }

   public List<Recipe<?>> getRecipes() {
      return this.recipes;
   }

   public static Recipe<?> fromNetwork(FriendlyByteBuf var0) {
      ResourceLocation â˜ƒ = â˜ƒ.readResourceLocation();
      ResourceLocation â˜ƒx = â˜ƒ.readResourceLocation();
      return ((RecipeSerializer)Registry.RECIPE_SERIALIZER.getOptional(â˜ƒ).orElseThrow(() -> new IllegalArgumentException("Unknown recipe serializer " + â˜ƒ)))
         .fromNetwork(â˜ƒx, â˜ƒ);
   }

   public static <T extends Recipe<?>> void toNetwork(FriendlyByteBuf var0, T var1) {
      â˜ƒ.writeResourceLocation(Registry.RECIPE_SERIALIZER.getKey(â˜ƒ.getSerializer()));
      â˜ƒ.writeResourceLocation(â˜ƒ.getId());
      â˜ƒ.getSerializer().toNetwork(â˜ƒ, â˜ƒ);
   }
}
