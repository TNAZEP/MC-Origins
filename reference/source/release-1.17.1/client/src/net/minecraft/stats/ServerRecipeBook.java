package net.minecraft.stats;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.ResourceLocationException;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.protocol.game.ClientboundRecipePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerRecipeBook extends RecipeBook {
   public static final String RECIPE_BOOK_TAG = "recipeBook";
   private static final Logger LOGGER = LogManager.getLogger();

   public int addRecipes(Collection<Recipe<?>> var1, ServerPlayer var2) {
      List<ResourceLocation> â˜ƒ = Lists.<ResourceLocation>newArrayList();
      int â˜ƒx = 0;

      for(Recipe<?> â˜ƒxx : â˜ƒ) {
         ResourceLocation â˜ƒxxx = â˜ƒxx.getId();
         if (!this.known.contains(â˜ƒxxx) && !â˜ƒxx.isSpecial()) {
            this.add(â˜ƒxxx);
            this.addHighlight(â˜ƒxxx);
            â˜ƒ.add(â˜ƒxxx);
            CriteriaTriggers.RECIPE_UNLOCKED.trigger(â˜ƒ, â˜ƒxx);
            ++â˜ƒx;
         }
      }

      this.sendRecipes(ClientboundRecipePacket.State.ADD, â˜ƒ, â˜ƒ);
      return â˜ƒx;
   }

   public int removeRecipes(Collection<Recipe<?>> var1, ServerPlayer var2) {
      List<ResourceLocation> â˜ƒ = Lists.<ResourceLocation>newArrayList();
      int â˜ƒx = 0;

      for(Recipe<?> â˜ƒxx : â˜ƒ) {
         ResourceLocation â˜ƒxxx = â˜ƒxx.getId();
         if (this.known.contains(â˜ƒxxx)) {
            this.remove(â˜ƒxxx);
            â˜ƒ.add(â˜ƒxxx);
            ++â˜ƒx;
         }
      }

      this.sendRecipes(ClientboundRecipePacket.State.REMOVE, â˜ƒ, â˜ƒ);
      return â˜ƒx;
   }

   private void sendRecipes(ClientboundRecipePacket.State var1, ServerPlayer var2, List<ResourceLocation> var3) {
      â˜ƒ.connection.send(new ClientboundRecipePacket(â˜ƒ, â˜ƒ, Collections.emptyList(), this.getBookSettings()));
   }

   public CompoundTag toNbt() {
      CompoundTag â˜ƒ = new CompoundTag();
      this.getBookSettings().write(â˜ƒ);
      ListTag â˜ƒx = new ListTag();

      for(ResourceLocation â˜ƒxx : this.known) {
         â˜ƒx.add(StringTag.valueOf(â˜ƒxx.toString()));
      }

      â˜ƒ.put("recipes", â˜ƒx);
      ListTag â˜ƒxx = new ListTag();

      for(ResourceLocation â˜ƒxxx : this.highlight) {
         â˜ƒxx.add(StringTag.valueOf(â˜ƒxxx.toString()));
      }

      â˜ƒ.put("toBeDisplayed", â˜ƒxx);
      return â˜ƒ;
   }

   public void fromNbt(CompoundTag var1, RecipeManager var2) {
      this.setBookSettings(RecipeBookSettings.read(â˜ƒ));
      ListTag â˜ƒ = â˜ƒ.getList("recipes", 8);
      this.loadRecipes(â˜ƒ, this::add, â˜ƒ);
      ListTag â˜ƒx = â˜ƒ.getList("toBeDisplayed", 8);
      this.loadRecipes(â˜ƒx, this::addHighlight, â˜ƒ);
   }

   private void loadRecipes(ListTag var1, Consumer<Recipe<?>> var2, RecipeManager var3) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.size(); ++â˜ƒ) {
         String â˜ƒx = â˜ƒ.getString(â˜ƒ);

         try {
            ResourceLocation â˜ƒxx = new ResourceLocation(â˜ƒx);
            Optional<? extends Recipe<?>> â˜ƒxxx = â˜ƒ.byKey(â˜ƒxx);
            if (!â˜ƒxxx.isPresent()) {
               LOGGER.error("Tried to load unrecognized recipe: {} removed now.", â˜ƒxx);
            } else {
               â˜ƒ.accept((Recipe)â˜ƒxxx.get());
            }
         } catch (ResourceLocationException var8) {
            LOGGER.error("Tried to load improperly formatted recipe: {} removed now.", â˜ƒx);
         }
      }
   }

   public void sendInitialRecipeBook(ServerPlayer var1) {
      â˜ƒ.connection.send(new ClientboundRecipePacket(ClientboundRecipePacket.State.INIT, this.known, this.highlight, this.getBookSettings()));
   }
}
