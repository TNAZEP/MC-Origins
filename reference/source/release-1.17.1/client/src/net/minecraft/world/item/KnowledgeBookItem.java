package net.minecraft.world.item;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KnowledgeBookItem extends Item {
   private static final String RECIPE_TAG = "Recipes";
   private static final Logger LOGGER = LogManager.getLogger();

   public KnowledgeBookItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      CompoundTag â˜ƒx = â˜ƒ.getTag();
      if (!â˜ƒ.getAbilities().instabuild) {
         â˜ƒ.setItemInHand(â˜ƒ, ItemStack.EMPTY);
      }

      if (â˜ƒx != null && â˜ƒx.contains("Recipes", 9)) {
         if (!â˜ƒ.isClientSide) {
            ListTag â˜ƒ = â˜ƒx.getList("Recipes", 8);
            List<Recipe<?>> â˜ƒx = Lists.<Recipe<?>>newArrayList();
            RecipeManager â˜ƒxx = â˜ƒ.getServer().getRecipeManager();

            for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒ.size(); ++â˜ƒxxx) {
               String â˜ƒxxxx = â˜ƒ.getString(â˜ƒxxx);
               Optional<? extends Recipe<?>> â˜ƒxxxxx = â˜ƒxx.byKey(new ResourceLocation(â˜ƒxxxx));
               if (!â˜ƒxxxxx.isPresent()) {
                  LOGGER.error("Invalid recipe: {}", â˜ƒxxxx);
                  return InteractionResultHolder.fail(â˜ƒ);
               }

               â˜ƒx.add((Recipe)â˜ƒxxxxx.get());
            }

            â˜ƒ.awardRecipes(â˜ƒx);
            â˜ƒ.awardStat(Stats.ITEM_USED.get(this));
         }

         return InteractionResultHolder.sidedSuccess(â˜ƒ, â˜ƒ.isClientSide());
      } else {
         LOGGER.error("Tag not valid: {}", â˜ƒx);
         return InteractionResultHolder.fail(â˜ƒ);
      }
   }
}
