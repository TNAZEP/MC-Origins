package net.minecraft.world.item.alchemy;

import com.google.common.collect.ImmutableList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;

public class Potion {
   private final String name;
   private final ImmutableList<MobEffectInstance> effects;

   public static Potion byName(String var0) {
      return Registry.POTION.get(ResourceLocation.tryParse(â˜ƒ));
   }

   public Potion(MobEffectInstance... var1) {
      this(null, â˜ƒ);
   }

   public Potion(@Nullable String var1, MobEffectInstance... var2) {
      this.name = â˜ƒ;
      this.effects = ImmutableList.copyOf(â˜ƒ);
   }

   public String getName(String var1) {
      return â˜ƒ + (this.name == null ? Registry.POTION.getKey(this).getPath() : this.name);
   }

   public List<MobEffectInstance> getEffects() {
      return this.effects;
   }

   public boolean hasInstantEffects() {
      if (!this.effects.isEmpty()) {
         for(MobEffectInstance â˜ƒ : this.effects) {
            if (â˜ƒ.getEffect().isInstantenous()) {
               return true;
            }
         }
      }

      return false;
   }
}
