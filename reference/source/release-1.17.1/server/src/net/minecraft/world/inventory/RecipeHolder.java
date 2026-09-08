package net.minecraft.world.inventory;

import java.util.Collections;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

public interface RecipeHolder {
   void setRecipeUsed(@Nullable Recipe<?> var1);

   @Nullable
   Recipe<?> getRecipeUsed();

   default void awardUsedRecipes(Player var1) {
      Recipe<?> â˜ƒ = this.getRecipeUsed();
      if (â˜ƒ != null && !â˜ƒ.isSpecial()) {
         â˜ƒ.awardRecipes(Collections.singleton(â˜ƒ));
         this.setRecipeUsed(null);
      }
   }

   default boolean setRecipeUsed(Level var1, ServerPlayer var2, Recipe<?> var3) {
      if (!â˜ƒ.isSpecial() && â˜ƒ.getGameRules().getBoolean(GameRules.RULE_LIMITED_CRAFTING) && !â˜ƒ.getRecipeBook().contains(â˜ƒ)) {
         return false;
      } else {
         this.setRecipeUsed(â˜ƒ);
         return true;
      }
   }
}
