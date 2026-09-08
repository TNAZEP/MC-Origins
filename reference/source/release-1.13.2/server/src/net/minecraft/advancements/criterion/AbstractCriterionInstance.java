package net.minecraft.advancements.criterion;

import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.util.ResourceLocation;

public class AbstractCriterionInstance implements ICriterionInstance {
   private final ResourceLocation field_192245_a;

   public AbstractCriterionInstance(ResourceLocation var1) {
      this.field_192245_a = ☃;
   }

   @Override
   public ResourceLocation func_192244_a() {
      return this.field_192245_a;
   }

   public String toString() {
      return "AbstractCriterionInstance{criterion=" + this.field_192245_a + '}';
   }
}
