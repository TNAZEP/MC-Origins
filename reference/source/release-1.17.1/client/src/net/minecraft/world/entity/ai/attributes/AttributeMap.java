package net.minecraft.world.entity.ai.attributes;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AttributeMap {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Map<Attribute, AttributeInstance> attributes = Maps.<Attribute, AttributeInstance>newHashMap();
   private final Set<AttributeInstance> dirtyAttributes = Sets.<AttributeInstance>newHashSet();
   private final AttributeSupplier supplier;

   public AttributeMap(AttributeSupplier var1) {
      this.supplier = â˜ƒ;
   }

   private void onAttributeModified(AttributeInstance var1) {
      if (â˜ƒ.getAttribute().isClientSyncable()) {
         this.dirtyAttributes.add(â˜ƒ);
      }
   }

   public Set<AttributeInstance> getDirtyAttributes() {
      return this.dirtyAttributes;
   }

   public Collection<AttributeInstance> getSyncableAttributes() {
      return (Collection<AttributeInstance>)this.attributes
         .values()
         .stream()
         .filter(var0 -> var0.getAttribute().isClientSyncable())
         .collect(Collectors.toList());
   }

   @Nullable
   public AttributeInstance getInstance(Attribute var1) {
      return (AttributeInstance)this.attributes.computeIfAbsent(â˜ƒ, var1x -> this.supplier.createInstance(this::onAttributeModified, var1x));
   }

   public boolean hasAttribute(Attribute var1) {
      return this.attributes.get(â˜ƒ) != null || this.supplier.hasAttribute(â˜ƒ);
   }

   public boolean hasModifier(Attribute var1, UUID var2) {
      AttributeInstance â˜ƒ = (AttributeInstance)this.attributes.get(â˜ƒ);
      return â˜ƒ != null ? â˜ƒ.getModifier(â˜ƒ) != null : this.supplier.hasModifier(â˜ƒ, â˜ƒ);
   }

   public double getValue(Attribute var1) {
      AttributeInstance â˜ƒ = (AttributeInstance)this.attributes.get(â˜ƒ);
      return â˜ƒ != null ? â˜ƒ.getValue() : this.supplier.getValue(â˜ƒ);
   }

   public double getBaseValue(Attribute var1) {
      AttributeInstance â˜ƒ = (AttributeInstance)this.attributes.get(â˜ƒ);
      return â˜ƒ != null ? â˜ƒ.getBaseValue() : this.supplier.getBaseValue(â˜ƒ);
   }

   public double getModifierValue(Attribute var1, UUID var2) {
      AttributeInstance â˜ƒ = (AttributeInstance)this.attributes.get(â˜ƒ);
      return â˜ƒ != null ? â˜ƒ.getModifier(â˜ƒ).getAmount() : this.supplier.getModifierValue(â˜ƒ, â˜ƒ);
   }

   public void removeAttributeModifiers(Multimap<Attribute, AttributeModifier> var1) {
      â˜ƒ.asMap().forEach((var1x, var2) -> {
         AttributeInstance â˜ƒ = (AttributeInstance)this.attributes.get(var1x);
         if (â˜ƒ != null) {
            var2.forEach(â˜ƒ::removeModifier);
         }
      });
   }

   public void addTransientAttributeModifiers(Multimap<Attribute, AttributeModifier> var1) {
      â˜ƒ.forEach((var1x, var2) -> {
         AttributeInstance â˜ƒ = this.getInstance(var1x);
         if (â˜ƒ != null) {
            â˜ƒ.removeModifier(var2);
            â˜ƒ.addTransientModifier(var2);
         }
      });
   }

   public void assignValues(AttributeMap var1) {
      â˜ƒ.attributes.values().forEach(var1x -> {
         AttributeInstance â˜ƒ = this.getInstance(var1x.getAttribute());
         if (â˜ƒ != null) {
            â˜ƒ.replaceFrom(var1x);
         }
      });
   }

   public ListTag save() {
      ListTag â˜ƒ = new ListTag();

      for(AttributeInstance â˜ƒx : this.attributes.values()) {
         â˜ƒ.add(â˜ƒx.save());
      }

      return â˜ƒ;
   }

   public void load(ListTag var1) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.size(); ++â˜ƒ) {
         CompoundTag â˜ƒx = â˜ƒ.getCompound(â˜ƒ);
         String â˜ƒxx = â˜ƒx.getString("Name");
         Util.ifElse(Registry.ATTRIBUTE.getOptional(ResourceLocation.tryParse(â˜ƒxx)), var2x -> {
            AttributeInstance â˜ƒ = this.getInstance(var2x);
            if (â˜ƒ != null) {
               â˜ƒ.load(â˜ƒ);
            }
         }, () -> LOGGER.warn("Ignoring unknown attribute '{}'", â˜ƒ));
      }
   }
}
