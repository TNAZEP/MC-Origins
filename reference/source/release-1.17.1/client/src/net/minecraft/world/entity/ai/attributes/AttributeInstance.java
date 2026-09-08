package net.minecraft.world.entity.ai.attributes;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

public class AttributeInstance {
   private final Attribute attribute;
   private final Map<AttributeModifier.Operation, Set<AttributeModifier>> modifiersByOperation = Maps.newEnumMap(AttributeModifier.Operation.class);
   private final Map<UUID, AttributeModifier> modifierById = new Object2ObjectArrayMap<>();
   private final Set<AttributeModifier> permanentModifiers = new ObjectArraySet<>();
   private double baseValue;
   private boolean dirty = true;
   private double cachedValue;
   private final Consumer<AttributeInstance> onDirty;

   public AttributeInstance(Attribute var1, Consumer<AttributeInstance> var2) {
      this.attribute = â˜ƒ;
      this.onDirty = â˜ƒ;
      this.baseValue = â˜ƒ.getDefaultValue();
   }

   public Attribute getAttribute() {
      return this.attribute;
   }

   public double getBaseValue() {
      return this.baseValue;
   }

   public void setBaseValue(double var1) {
      if (â˜ƒ != this.baseValue) {
         this.baseValue = â˜ƒ;
         this.setDirty();
      }
   }

   public Set<AttributeModifier> getModifiers(AttributeModifier.Operation var1) {
      return (Set<AttributeModifier>)this.modifiersByOperation.computeIfAbsent(â˜ƒ, var0 -> Sets.newHashSet());
   }

   public Set<AttributeModifier> getModifiers() {
      return ImmutableSet.copyOf(this.modifierById.values());
   }

   @Nullable
   public AttributeModifier getModifier(UUID var1) {
      return (AttributeModifier)this.modifierById.get(â˜ƒ);
   }

   public boolean hasModifier(AttributeModifier var1) {
      return this.modifierById.get(â˜ƒ.getId()) != null;
   }

   private void addModifier(AttributeModifier var1) {
      AttributeModifier â˜ƒ = (AttributeModifier)this.modifierById.putIfAbsent(â˜ƒ.getId(), â˜ƒ);
      if (â˜ƒ != null) {
         throw new IllegalArgumentException("Modifier is already applied on this attribute!");
      } else {
         this.getModifiers(â˜ƒ.getOperation()).add(â˜ƒ);
         this.setDirty();
      }
   }

   public void addTransientModifier(AttributeModifier var1) {
      this.addModifier(â˜ƒ);
   }

   public void addPermanentModifier(AttributeModifier var1) {
      this.addModifier(â˜ƒ);
      this.permanentModifiers.add(â˜ƒ);
   }

   protected void setDirty() {
      this.dirty = true;
      this.onDirty.accept(this);
   }

   public void removeModifier(AttributeModifier var1) {
      this.getModifiers(â˜ƒ.getOperation()).remove(â˜ƒ);
      this.modifierById.remove(â˜ƒ.getId());
      this.permanentModifiers.remove(â˜ƒ);
      this.setDirty();
   }

   public void removeModifier(UUID var1) {
      AttributeModifier â˜ƒ = this.getModifier(â˜ƒ);
      if (â˜ƒ != null) {
         this.removeModifier(â˜ƒ);
      }
   }

   public boolean removePermanentModifier(UUID var1) {
      AttributeModifier â˜ƒ = this.getModifier(â˜ƒ);
      if (â˜ƒ != null && this.permanentModifiers.contains(â˜ƒ)) {
         this.removeModifier(â˜ƒ);
         return true;
      } else {
         return false;
      }
   }

   public void removeModifiers() {
      for(AttributeModifier â˜ƒ : this.getModifiers()) {
         this.removeModifier(â˜ƒ);
      }
   }

   public double getValue() {
      if (this.dirty) {
         this.cachedValue = this.calculateValue();
         this.dirty = false;
      }

      return this.cachedValue;
   }

   private double calculateValue() {
      double â˜ƒ = this.getBaseValue();

      for(AttributeModifier â˜ƒx : this.getModifiersOrEmpty(AttributeModifier.Operation.ADDITION)) {
         â˜ƒ += â˜ƒx.getAmount();
      }

      double â˜ƒx = â˜ƒ;

      for(AttributeModifier â˜ƒxx : this.getModifiersOrEmpty(AttributeModifier.Operation.MULTIPLY_BASE)) {
         â˜ƒx += â˜ƒ * â˜ƒxx.getAmount();
      }

      for(AttributeModifier â˜ƒxx : this.getModifiersOrEmpty(AttributeModifier.Operation.MULTIPLY_TOTAL)) {
         â˜ƒx *= 1.0 + â˜ƒxx.getAmount();
      }

      return this.attribute.sanitizeValue(â˜ƒx);
   }

   private Collection<AttributeModifier> getModifiersOrEmpty(AttributeModifier.Operation var1) {
      return (Collection<AttributeModifier>)this.modifiersByOperation.getOrDefault(â˜ƒ, Collections.emptySet());
   }

   public void replaceFrom(AttributeInstance var1) {
      this.baseValue = â˜ƒ.baseValue;
      this.modifierById.clear();
      this.modifierById.putAll(â˜ƒ.modifierById);
      this.permanentModifiers.clear();
      this.permanentModifiers.addAll(â˜ƒ.permanentModifiers);
      this.modifiersByOperation.clear();
      â˜ƒ.modifiersByOperation.forEach((var1x, var2) -> this.getModifiers(var1x).addAll(var2));
      this.setDirty();
   }

   public CompoundTag save() {
      CompoundTag â˜ƒ = new CompoundTag();
      â˜ƒ.putString("Name", Registry.ATTRIBUTE.getKey(this.attribute).toString());
      â˜ƒ.putDouble("Base", this.baseValue);
      if (!this.permanentModifiers.isEmpty()) {
         ListTag â˜ƒx = new ListTag();

         for(AttributeModifier â˜ƒxx : this.permanentModifiers) {
            â˜ƒx.add(â˜ƒxx.save());
         }

         â˜ƒ.put("Modifiers", â˜ƒx);
      }

      return â˜ƒ;
   }

   public void load(CompoundTag var1) {
      this.baseValue = â˜ƒ.getDouble("Base");
      if (â˜ƒ.contains("Modifiers", 9)) {
         ListTag â˜ƒ = â˜ƒ.getList("Modifiers", 10);

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
            AttributeModifier â˜ƒxx = AttributeModifier.load(â˜ƒ.getCompound(â˜ƒx));
            if (â˜ƒxx != null) {
               this.modifierById.put(â˜ƒxx.getId(), â˜ƒxx);
               this.getModifiers(â˜ƒxx.getOperation()).add(â˜ƒxx);
               this.permanentModifiers.add(â˜ƒxx);
            }
         }
      }

      this.setDirty();
   }
}
