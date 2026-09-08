package net.minecraft.world.level.storage.loot.parameters;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.world.level.storage.loot.LootContextUser;
import net.minecraft.world.level.storage.loot.ValidationContext;

public class LootContextParamSet {
   private final Set<LootContextParam<?>> required;
   private final Set<LootContextParam<?>> all;

   LootContextParamSet(Set<LootContextParam<?>> var1, Set<LootContextParam<?>> var2) {
      this.required = ImmutableSet.copyOf(â˜ƒ);
      this.all = ImmutableSet.copyOf(Sets.union(â˜ƒ, â˜ƒ));
   }

   public boolean isAllowed(LootContextParam<?> var1) {
      return this.all.contains(â˜ƒ);
   }

   public Set<LootContextParam<?>> getRequired() {
      return this.required;
   }

   public Set<LootContextParam<?>> getAllowed() {
      return this.all;
   }

   public String toString() {
      return "[" + Joiner.on(", ").join(this.all.stream().map(var1 -> (this.required.contains(var1) ? "!" : "") + var1.getName()).iterator()) + "]";
   }

   public void validateUser(ValidationContext var1, LootContextUser var2) {
      Set<LootContextParam<?>> â˜ƒ = â˜ƒ.getReferencedContextParams();
      Set<LootContextParam<?>> â˜ƒx = Sets.<LootContextParam<?>>difference(â˜ƒ, this.all);
      if (!â˜ƒx.isEmpty()) {
         â˜ƒ.reportProblem("Parameters " + â˜ƒx + " are not provided in this context");
      }
   }

   public static LootContextParamSet.Builder builder() {
      return new LootContextParamSet.Builder();
   }

   public static class Builder {
      private final Set<LootContextParam<?>> required = Sets.newIdentityHashSet();
      private final Set<LootContextParam<?>> optional = Sets.newIdentityHashSet();

      public LootContextParamSet.Builder required(LootContextParam<?> var1) {
         if (this.optional.contains(â˜ƒ)) {
            throw new IllegalArgumentException("Parameter " + â˜ƒ.getName() + " is already optional");
         } else {
            this.required.add(â˜ƒ);
            return this;
         }
      }

      public LootContextParamSet.Builder optional(LootContextParam<?> var1) {
         if (this.required.contains(â˜ƒ)) {
            throw new IllegalArgumentException("Parameter " + â˜ƒ.getName() + " is already required");
         } else {
            this.optional.add(â˜ƒ);
            return this;
         }
      }

      public LootContextParamSet build() {
         return new LootContextParamSet(this.required, this.optional);
      }
   }
}
