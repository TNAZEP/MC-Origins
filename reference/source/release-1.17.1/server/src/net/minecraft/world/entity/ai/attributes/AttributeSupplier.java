package net.minecraft.world.entity.ai.attributes;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;

public class AttributeSupplier {
   private final Map<Attribute, AttributeInstance> instances;

   public AttributeSupplier(Map<Attribute, AttributeInstance> var1) {
      this.instances = ImmutableMap.copyOf(â˜ƒ);
   }

   private AttributeInstance getAttributeInstance(Attribute var1) {
      AttributeInstance â˜ƒ = (AttributeInstance)this.instances.get(â˜ƒ);
      if (â˜ƒ == null) {
         throw new IllegalArgumentException("Can't find attribute " + Registry.ATTRIBUTE.getKey(â˜ƒ));
      } else {
         return â˜ƒ;
      }
   }

   public double getValue(Attribute var1) {
      return this.getAttributeInstance(â˜ƒ).getValue();
   }

   public double getBaseValue(Attribute var1) {
      return this.getAttributeInstance(â˜ƒ).getBaseValue();
   }

   public double getModifierValue(Attribute var1, UUID var2) {
      AttributeModifier â˜ƒ = this.getAttributeInstance(â˜ƒ).getModifier(â˜ƒ);
      if (â˜ƒ == null) {
         throw new IllegalArgumentException("Can't find modifier " + â˜ƒ + " on attribute " + Registry.ATTRIBUTE.getKey(â˜ƒ));
      } else {
         return â˜ƒ.getAmount();
      }
   }

   @Nullable
   public AttributeInstance createInstance(Consumer<AttributeInstance> var1, Attribute var2) {
      AttributeInstance â˜ƒ = (AttributeInstance)this.instances.get(â˜ƒ);
      if (â˜ƒ == null) {
         return null;
      } else {
         AttributeInstance â˜ƒ = new AttributeInstance(â˜ƒ, â˜ƒ);
         â˜ƒ.replaceFrom(â˜ƒ);
         return â˜ƒ;
      }
   }

   public static AttributeSupplier.Builder builder() {
      return new AttributeSupplier.Builder();
   }

   public boolean hasAttribute(Attribute var1) {
      return this.instances.containsKey(â˜ƒ);
   }

   public boolean hasModifier(Attribute var1, UUID var2) {
      AttributeInstance â˜ƒ = (AttributeInstance)this.instances.get(â˜ƒ);
      return â˜ƒ != null && â˜ƒ.getModifier(â˜ƒ) != null;
   }

   public static class Builder {
      private final Map<Attribute, AttributeInstance> builder = Maps.<Attribute, AttributeInstance>newHashMap();
      private boolean instanceFrozen;

      private AttributeInstance create(Attribute var1) {
         AttributeInstance â˜ƒ = new AttributeInstance(â˜ƒ, var2x -> {
            if (this.instanceFrozen) {
               throw new UnsupportedOperationException("Tried to change value for default attribute instance: " + Registry.ATTRIBUTE.getKey(â˜ƒ));
            }
         });
         this.builder.put(â˜ƒ, â˜ƒ);
         return â˜ƒ;
      }

      public AttributeSupplier.Builder add(Attribute var1) {
         this.create(â˜ƒ);
         return this;
      }

      public AttributeSupplier.Builder add(Attribute var1, double var2) {
         AttributeInstance â˜ƒ = this.create(â˜ƒ);
         â˜ƒ.setBaseValue(â˜ƒ);
         return this;
      }

      public AttributeSupplier build() {
         this.instanceFrozen = true;
         return new AttributeSupplier(this.builder);
      }
   }
}
