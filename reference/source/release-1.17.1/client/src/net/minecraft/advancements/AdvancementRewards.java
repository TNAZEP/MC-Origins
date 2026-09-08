package net.minecraft.advancements;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class AdvancementRewards {
   public static final AdvancementRewards EMPTY = new AdvancementRewards(
      0, new ResourceLocation[0], new ResourceLocation[0], CommandFunction.CacheableFunction.NONE
   );
   private final int experience;
   private final ResourceLocation[] loot;
   private final ResourceLocation[] recipes;
   private final CommandFunction.CacheableFunction function;

   public AdvancementRewards(int var1, ResourceLocation[] var2, ResourceLocation[] var3, CommandFunction.CacheableFunction var4) {
      this.experience = â˜ƒ;
      this.loot = â˜ƒ;
      this.recipes = â˜ƒ;
      this.function = â˜ƒ;
   }

   public ResourceLocation[] getRecipes() {
      return this.recipes;
   }

   public void grant(ServerPlayer var1) {
      â˜ƒ.giveExperiencePoints(this.experience);
      LootContext â˜ƒ = new LootContext.Builder(â˜ƒ.getLevel())
         .withParameter(LootContextParams.THIS_ENTITY, â˜ƒ)
         .withParameter(LootContextParams.ORIGIN, â˜ƒ.position())
         .withRandom(â˜ƒ.getRandom())
         .create(LootContextParamSets.ADVANCEMENT_REWARD);
      boolean â˜ƒx = false;

      for(ResourceLocation â˜ƒxx : this.loot) {
         for(ItemStack â˜ƒxxx : â˜ƒ.server.getLootTables().get(â˜ƒxx).getRandomItems(â˜ƒ)) {
            if (â˜ƒ.addItem(â˜ƒxxx)) {
               â˜ƒ.level
                  .playSound(
                     null,
                     â˜ƒ.getX(),
                     â˜ƒ.getY(),
                     â˜ƒ.getZ(),
                     SoundEvents.ITEM_PICKUP,
                     SoundSource.PLAYERS,
                     0.2F,
                     ((â˜ƒ.getRandom().nextFloat() - â˜ƒ.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F
                  );
               â˜ƒx = true;
            } else {
               ItemEntity â˜ƒxxxx = â˜ƒ.drop(â˜ƒxxx, false);
               if (â˜ƒxxxx != null) {
                  â˜ƒxxxx.setNoPickUpDelay();
                  â˜ƒxxxx.setOwner(â˜ƒ.getUUID());
               }
            }
         }
      }

      if (â˜ƒx) {
         â˜ƒ.containerMenu.broadcastChanges();
      }

      if (this.recipes.length > 0) {
         â˜ƒ.awardRecipesByKey(this.recipes);
      }

      MinecraftServer â˜ƒxx = â˜ƒ.server;
      this.function
         .get(â˜ƒxx.getFunctions())
         .ifPresent(var2x -> â˜ƒ.getFunctions().execute(var2x, â˜ƒ.createCommandSourceStack().withSuppressedOutput().withPermission(2)));
   }

   public String toString() {
      return "AdvancementRewards{experience="
         + this.experience
         + ", loot="
         + Arrays.toString(this.loot)
         + ", recipes="
         + Arrays.toString(this.recipes)
         + ", function="
         + this.function
         + "}";
   }

   public JsonElement serializeToJson() {
      if (this == EMPTY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject â˜ƒ = new JsonObject();
         if (this.experience != 0) {
            â˜ƒ.addProperty("experience", this.experience);
         }

         if (this.loot.length > 0) {
            JsonArray â˜ƒ = new JsonArray();

            for(ResourceLocation â˜ƒx : this.loot) {
               â˜ƒ.add(â˜ƒx.toString());
            }

            â˜ƒ.add("loot", â˜ƒ);
         }

         if (this.recipes.length > 0) {
            JsonArray â˜ƒ = new JsonArray();

            for(ResourceLocation â˜ƒx : this.recipes) {
               â˜ƒ.add(â˜ƒx.toString());
            }

            â˜ƒ.add("recipes", â˜ƒ);
         }

         if (this.function.getId() != null) {
            â˜ƒ.addProperty("function", this.function.getId().toString());
         }

         return â˜ƒ;
      }
   }

   public static AdvancementRewards deserialize(JsonObject var0) throws JsonParseException {
      int â˜ƒ = GsonHelper.getAsInt(â˜ƒ, "experience", 0);
      JsonArray â˜ƒx = GsonHelper.getAsJsonArray(â˜ƒ, "loot", new JsonArray());
      ResourceLocation[] â˜ƒxx = new ResourceLocation[â˜ƒx.size()];

      for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒxx.length; ++â˜ƒxxx) {
         â˜ƒxx[â˜ƒxxx] = new ResourceLocation(GsonHelper.convertToString(â˜ƒx.get(â˜ƒxxx), "loot[" + â˜ƒxxx + "]"));
      }

      JsonArray â˜ƒxxx = GsonHelper.getAsJsonArray(â˜ƒ, "recipes", new JsonArray());
      ResourceLocation[] â˜ƒxxxx = new ResourceLocation[â˜ƒxxx.size()];

      for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < â˜ƒxxxx.length; ++â˜ƒxxxxx) {
         â˜ƒxxxx[â˜ƒxxxxx] = new ResourceLocation(GsonHelper.convertToString(â˜ƒxxx.get(â˜ƒxxxxx), "recipes[" + â˜ƒxxxxx + "]"));
      }

      CommandFunction.CacheableFunction â˜ƒxxxxx;
      if (â˜ƒ.has("function")) {
         â˜ƒxxxxx = new CommandFunction.CacheableFunction(new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "function")));
      } else {
         â˜ƒxxxxx = CommandFunction.CacheableFunction.NONE;
      }

      return new AdvancementRewards(â˜ƒ, â˜ƒxx, â˜ƒxxxx, â˜ƒxxxxx);
   }

   public static class Builder {
      private int experience;
      private final List<ResourceLocation> loot = Lists.<ResourceLocation>newArrayList();
      private final List<ResourceLocation> recipes = Lists.<ResourceLocation>newArrayList();
      @Nullable
      private ResourceLocation function;

      public static AdvancementRewards.Builder experience(int var0) {
         return new AdvancementRewards.Builder().addExperience(â˜ƒ);
      }

      public AdvancementRewards.Builder addExperience(int var1) {
         this.experience += â˜ƒ;
         return this;
      }

      public static AdvancementRewards.Builder loot(ResourceLocation var0) {
         return new AdvancementRewards.Builder().addLootTable(â˜ƒ);
      }

      public AdvancementRewards.Builder addLootTable(ResourceLocation var1) {
         this.loot.add(â˜ƒ);
         return this;
      }

      public static AdvancementRewards.Builder recipe(ResourceLocation var0) {
         return new AdvancementRewards.Builder().addRecipe(â˜ƒ);
      }

      public AdvancementRewards.Builder addRecipe(ResourceLocation var1) {
         this.recipes.add(â˜ƒ);
         return this;
      }

      public static AdvancementRewards.Builder function(ResourceLocation var0) {
         return new AdvancementRewards.Builder().runs(â˜ƒ);
      }

      public AdvancementRewards.Builder runs(ResourceLocation var1) {
         this.function = â˜ƒ;
         return this;
      }

      public AdvancementRewards build() {
         return new AdvancementRewards(
            this.experience,
            (ResourceLocation[])this.loot.toArray(new ResourceLocation[0]),
            (ResourceLocation[])this.recipes.toArray(new ResourceLocation[0]),
            this.function == null ? CommandFunction.CacheableFunction.NONE : new CommandFunction.CacheableFunction(this.function)
         );
      }
   }
}
