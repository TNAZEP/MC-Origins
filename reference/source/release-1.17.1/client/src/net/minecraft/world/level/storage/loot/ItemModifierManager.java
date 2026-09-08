package net.minecraft.world.level.storage.loot;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ItemModifierManager extends SimpleJsonResourceReloadListener {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = Deserializers.createFunctionSerializer().create();
   private final PredicateManager predicateManager;
   private final LootTables lootTables;
   private Map<ResourceLocation, LootItemFunction> functions = ImmutableMap.of();

   public ItemModifierManager(PredicateManager var1, LootTables var2) {
      super(GSON, "item_modifiers");
      this.predicateManager = â˜ƒ;
      this.lootTables = â˜ƒ;
   }

   @Nullable
   public LootItemFunction get(ResourceLocation var1) {
      return (LootItemFunction)this.functions.get(â˜ƒ);
   }

   public LootItemFunction get(ResourceLocation var1, LootItemFunction var2) {
      return (LootItemFunction)this.functions.getOrDefault(â˜ƒ, â˜ƒ);
   }

   protected void apply(Map<ResourceLocation, JsonElement> var1, ResourceManager var2, ProfilerFiller var3) {
      Builder<ResourceLocation, LootItemFunction> â˜ƒ = ImmutableMap.builder();
      â˜ƒ.forEach((var1x, var2x) -> {
         try {
            if (var2x.isJsonArray()) {
               LootItemFunction[] â˜ƒ = (LootItemFunction[])GSON.fromJson(var2x, LootItemFunction[].class);
               â˜ƒ.put(var1x, new ItemModifierManager.FunctionSequence(â˜ƒ));
            } else {
               LootItemFunction â˜ƒ = GSON.fromJson(var2x, LootItemFunction.class);
               â˜ƒ.put(var1x, â˜ƒ);
            }
         } catch (Exception var4xx) {
            LOGGER.error("Couldn't parse item modifier {}", var1x, var4xx);
         }
      });
      Map<ResourceLocation, LootItemFunction> â˜ƒx = â˜ƒ.build();
      ValidationContext â˜ƒxx = new ValidationContext(LootContextParamSets.ALL_PARAMS, this.predicateManager::get, this.lootTables::get);
      â˜ƒx.forEach((var1x, var2x) -> var2x.validate(â˜ƒ));
      â˜ƒxx.getProblems().forEach((var0, var1x) -> LOGGER.warn("Found item modifier validation problem in {}: {}", var0, var1x));
      this.functions = â˜ƒx;
   }

   public Set<ResourceLocation> getKeys() {
      return Collections.unmodifiableSet(this.functions.keySet());
   }

   static class FunctionSequence implements LootItemFunction {
      protected final LootItemFunction[] functions;
      private final BiFunction<ItemStack, LootContext, ItemStack> compositeFunction;

      public FunctionSequence(LootItemFunction[] var1) {
         this.functions = â˜ƒ;
         this.compositeFunction = LootItemFunctions.compose(â˜ƒ);
      }

      public ItemStack apply(ItemStack var1, LootContext var2) {
         return (ItemStack)this.compositeFunction.apply(â˜ƒ, â˜ƒ);
      }

      @Override
      public LootItemFunctionType getType() {
         throw new UnsupportedOperationException();
      }
   }
}
