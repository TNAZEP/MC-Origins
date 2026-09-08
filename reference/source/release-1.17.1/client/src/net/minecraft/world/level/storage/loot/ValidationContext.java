package net.minecraft.world.level.storage.loot;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class ValidationContext {
   private final Multimap<String, String> problems;
   private final Supplier<String> context;
   private final LootContextParamSet params;
   private final Function<ResourceLocation, LootItemCondition> conditionResolver;
   private final Set<ResourceLocation> visitedConditions;
   private final Function<ResourceLocation, LootTable> tableResolver;
   private final Set<ResourceLocation> visitedTables;
   private String contextCache;

   public ValidationContext(LootContextParamSet var1, Function<ResourceLocation, LootItemCondition> var2, Function<ResourceLocation, LootTable> var3) {
      this(HashMultimap.create(), () -> "", â˜ƒ, â˜ƒ, ImmutableSet.of(), â˜ƒ, ImmutableSet.of());
   }

   public ValidationContext(
      Multimap<String, String> var1,
      Supplier<String> var2,
      LootContextParamSet var3,
      Function<ResourceLocation, LootItemCondition> var4,
      Set<ResourceLocation> var5,
      Function<ResourceLocation, LootTable> var6,
      Set<ResourceLocation> var7
   ) {
      this.problems = â˜ƒ;
      this.context = â˜ƒ;
      this.params = â˜ƒ;
      this.conditionResolver = â˜ƒ;
      this.visitedConditions = â˜ƒ;
      this.tableResolver = â˜ƒ;
      this.visitedTables = â˜ƒ;
   }

   private String getContext() {
      if (this.contextCache == null) {
         this.contextCache = (String)this.context.get();
      }

      return this.contextCache;
   }

   public void reportProblem(String var1) {
      this.problems.put(this.getContext(), â˜ƒ);
   }

   public ValidationContext forChild(String var1) {
      return new ValidationContext(
         this.problems, () -> this.getContext() + â˜ƒ, this.params, this.conditionResolver, this.visitedConditions, this.tableResolver, this.visitedTables
      );
   }

   public ValidationContext enterTable(String var1, ResourceLocation var2) {
      ImmutableSet<ResourceLocation> â˜ƒ = ImmutableSet.<ResourceLocation>builder().addAll(this.visitedTables).add(â˜ƒ).build();
      return new ValidationContext(
         this.problems, () -> this.getContext() + â˜ƒ, this.params, this.conditionResolver, this.visitedConditions, this.tableResolver, â˜ƒ
      );
   }

   public ValidationContext enterCondition(String var1, ResourceLocation var2) {
      ImmutableSet<ResourceLocation> â˜ƒ = ImmutableSet.<ResourceLocation>builder().addAll(this.visitedConditions).add(â˜ƒ).build();
      return new ValidationContext(
         this.problems, () -> this.getContext() + â˜ƒ, this.params, this.conditionResolver, â˜ƒ, this.tableResolver, this.visitedTables
      );
   }

   public boolean hasVisitedTable(ResourceLocation var1) {
      return this.visitedTables.contains(â˜ƒ);
   }

   public boolean hasVisitedCondition(ResourceLocation var1) {
      return this.visitedConditions.contains(â˜ƒ);
   }

   public Multimap<String, String> getProblems() {
      return ImmutableMultimap.copyOf(this.problems);
   }

   public void validateUser(LootContextUser var1) {
      this.params.validateUser(this, â˜ƒ);
   }

   @Nullable
   public LootTable resolveLootTable(ResourceLocation var1) {
      return (LootTable)this.tableResolver.apply(â˜ƒ);
   }

   @Nullable
   public LootItemCondition resolveCondition(ResourceLocation var1) {
      return (LootItemCondition)this.conditionResolver.apply(â˜ƒ);
   }

   public ValidationContext setParams(LootContextParamSet var1) {
      return new ValidationContext(this.problems, this.context, â˜ƒ, this.conditionResolver, this.visitedConditions, this.tableResolver, this.visitedTables);
   }
}
