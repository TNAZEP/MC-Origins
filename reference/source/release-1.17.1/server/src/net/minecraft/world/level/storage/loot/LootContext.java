package net.minecraft.world.level.storage.loot;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LootContext {
   private final Random random;
   private final float luck;
   private final ServerLevel level;
   private final Function<ResourceLocation, LootTable> lootTables;
   private final Set<LootTable> visitedTables = Sets.<LootTable>newLinkedHashSet();
   private final Function<ResourceLocation, LootItemCondition> conditions;
   private final Set<LootItemCondition> visitedConditions = Sets.<LootItemCondition>newLinkedHashSet();
   private final Map<LootContextParam<?>, Object> params;
   private final Map<ResourceLocation, LootContext.DynamicDrop> dynamicDrops;

   LootContext(
      Random var1,
      float var2,
      ServerLevel var3,
      Function<ResourceLocation, LootTable> var4,
      Function<ResourceLocation, LootItemCondition> var5,
      Map<LootContextParam<?>, Object> var6,
      Map<ResourceLocation, LootContext.DynamicDrop> var7
   ) {
      this.random = â˜ƒ;
      this.luck = â˜ƒ;
      this.level = â˜ƒ;
      this.lootTables = â˜ƒ;
      this.conditions = â˜ƒ;
      this.params = ImmutableMap.copyOf(â˜ƒ);
      this.dynamicDrops = ImmutableMap.copyOf(â˜ƒ);
   }

   public boolean hasParam(LootContextParam<?> var1) {
      return this.params.containsKey(â˜ƒ);
   }

   public <T> T getParam(LootContextParam<T> var1) {
      T â˜ƒ = (T)this.params.get(â˜ƒ);
      if (â˜ƒ == null) {
         throw new NoSuchElementException(â˜ƒ.getName().toString());
      } else {
         return â˜ƒ;
      }
   }

   public void addDynamicDrops(ResourceLocation var1, Consumer<ItemStack> var2) {
      LootContext.DynamicDrop â˜ƒ = (LootContext.DynamicDrop)this.dynamicDrops.get(â˜ƒ);
      if (â˜ƒ != null) {
         â˜ƒ.add(this, â˜ƒ);
      }
   }

   @Nullable
   public <T> T getParamOrNull(LootContextParam<T> var1) {
      return (T)this.params.get(â˜ƒ);
   }

   public boolean addVisitedTable(LootTable var1) {
      return this.visitedTables.add(â˜ƒ);
   }

   public void removeVisitedTable(LootTable var1) {
      this.visitedTables.remove(â˜ƒ);
   }

   public boolean addVisitedCondition(LootItemCondition var1) {
      return this.visitedConditions.add(â˜ƒ);
   }

   public void removeVisitedCondition(LootItemCondition var1) {
      this.visitedConditions.remove(â˜ƒ);
   }

   public LootTable getLootTable(ResourceLocation var1) {
      return (LootTable)this.lootTables.apply(â˜ƒ);
   }

   public LootItemCondition getCondition(ResourceLocation var1) {
      return (LootItemCondition)this.conditions.apply(â˜ƒ);
   }

   public Random getRandom() {
      return this.random;
   }

   public float getLuck() {
      return this.luck;
   }

   public ServerLevel getLevel() {
      return this.level;
   }

   public static class Builder {
      private final ServerLevel level;
      private final Map<LootContextParam<?>, Object> params = Maps.<LootContextParam<?>, Object>newIdentityHashMap();
      private final Map<ResourceLocation, LootContext.DynamicDrop> dynamicDrops = Maps.<ResourceLocation, LootContext.DynamicDrop>newHashMap();
      private Random random;
      private float luck;

      public Builder(ServerLevel var1) {
         this.level = â˜ƒ;
      }

      public LootContext.Builder withRandom(Random var1) {
         this.random = â˜ƒ;
         return this;
      }

      public LootContext.Builder withOptionalRandomSeed(long var1) {
         if (â˜ƒ != 0L) {
            this.random = new Random(â˜ƒ);
         }

         return this;
      }

      public LootContext.Builder withOptionalRandomSeed(long var1, Random var3) {
         if (â˜ƒ == 0L) {
            this.random = â˜ƒ;
         } else {
            this.random = new Random(â˜ƒ);
         }

         return this;
      }

      public LootContext.Builder withLuck(float var1) {
         this.luck = â˜ƒ;
         return this;
      }

      public <T> LootContext.Builder withParameter(LootContextParam<T> var1, T var2) {
         this.params.put(â˜ƒ, â˜ƒ);
         return this;
      }

      public <T> LootContext.Builder withOptionalParameter(LootContextParam<T> var1, @Nullable T var2) {
         if (â˜ƒ == null) {
            this.params.remove(â˜ƒ);
         } else {
            this.params.put(â˜ƒ, â˜ƒ);
         }

         return this;
      }

      public LootContext.Builder withDynamicDrop(ResourceLocation var1, LootContext.DynamicDrop var2) {
         LootContext.DynamicDrop â˜ƒ = (LootContext.DynamicDrop)this.dynamicDrops.put(â˜ƒ, â˜ƒ);
         if (â˜ƒ != null) {
            throw new IllegalStateException("Duplicated dynamic drop '" + this.dynamicDrops + "'");
         } else {
            return this;
         }
      }

      public ServerLevel getLevel() {
         return this.level;
      }

      public <T> T getParameter(LootContextParam<T> var1) {
         T â˜ƒ = (T)this.params.get(â˜ƒ);
         if (â˜ƒ == null) {
            throw new IllegalArgumentException("No parameter " + â˜ƒ);
         } else {
            return â˜ƒ;
         }
      }

      @Nullable
      public <T> T getOptionalParameter(LootContextParam<T> var1) {
         return (T)this.params.get(â˜ƒ);
      }

      public LootContext create(LootContextParamSet var1) {
         Set<LootContextParam<?>> â˜ƒ = Sets.<LootContextParam<?>>difference(this.params.keySet(), â˜ƒ.getAllowed());
         if (!â˜ƒ.isEmpty()) {
            throw new IllegalArgumentException("Parameters not allowed in this parameter set: " + â˜ƒ);
         } else {
            Set<LootContextParam<?>> â˜ƒ = Sets.<LootContextParam<?>>difference(â˜ƒ.getRequired(), this.params.keySet());
            if (!â˜ƒ.isEmpty()) {
               throw new IllegalArgumentException("Missing required parameters: " + â˜ƒ);
            } else {
               Random â˜ƒ = this.random;
               if (â˜ƒ == null) {
                  â˜ƒ = new Random();
               }

               MinecraftServer â˜ƒ = this.level.getServer();
               return new LootContext(â˜ƒ, this.luck, this.level, â˜ƒ.getLootTables()::get, â˜ƒ.getPredicateManager()::get, this.params, this.dynamicDrops);
            }
         }
      }
   }

   @FunctionalInterface
   public interface DynamicDrop {
      void add(LootContext var1, Consumer<ItemStack> var2);
   }

   public static enum EntityTarget {
      THIS("this", LootContextParams.THIS_ENTITY),
      KILLER("killer", LootContextParams.KILLER_ENTITY),
      DIRECT_KILLER("direct_killer", LootContextParams.DIRECT_KILLER_ENTITY),
      KILLER_PLAYER("killer_player", LootContextParams.LAST_DAMAGE_PLAYER);

      final String name;
      private final LootContextParam<? extends Entity> param;

      private EntityTarget(String var3, LootContextParam<? extends Entity> var4) {
         this.name = â˜ƒ;
         this.param = â˜ƒ;
      }

      public LootContextParam<? extends Entity> getParam() {
         return this.param;
      }

      public static LootContext.EntityTarget getByName(String var0) {
         for(LootContext.EntityTarget â˜ƒ : values()) {
            if (â˜ƒ.name.equals(â˜ƒ)) {
               return â˜ƒ;
            }
         }

         throw new IllegalArgumentException("Invalid entity target " + â˜ƒ);
      }

      public static class Serializer extends TypeAdapter<LootContext.EntityTarget> {
         public void write(JsonWriter var1, LootContext.EntityTarget var2) throws IOException {
            â˜ƒ.value(â˜ƒ.name);
         }

         public LootContext.EntityTarget read(JsonReader var1) throws IOException {
            return LootContext.EntityTarget.getByName(â˜ƒ.nextString());
         }
      }
   }
}
