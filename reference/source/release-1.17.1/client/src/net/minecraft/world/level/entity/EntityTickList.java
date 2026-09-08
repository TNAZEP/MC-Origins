package net.minecraft.world.level.entity;

import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;

public class EntityTickList {
   private Int2ObjectMap<Entity> active = new Int2ObjectLinkedOpenHashMap<>();
   private Int2ObjectMap<Entity> passive = new Int2ObjectLinkedOpenHashMap<>();
   @Nullable
   private Int2ObjectMap<Entity> iterated;

   private void ensureActiveIsNotIterated() {
      if (this.iterated == this.active) {
         this.passive.clear();

         for(Entry<Entity> â˜ƒ : Int2ObjectMaps.fastIterable(this.active)) {
            this.passive.put(â˜ƒ.getIntKey(), (Entity)â˜ƒ.getValue());
         }

         Int2ObjectMap<Entity> â˜ƒ = this.active;
         this.active = this.passive;
         this.passive = â˜ƒ;
      }
   }

   public void add(Entity var1) {
      this.ensureActiveIsNotIterated();
      this.active.put(â˜ƒ.getId(), â˜ƒ);
   }

   public void remove(Entity var1) {
      this.ensureActiveIsNotIterated();
      this.active.remove(â˜ƒ.getId());
   }

   public boolean contains(Entity var1) {
      return this.active.containsKey(â˜ƒ.getId());
   }

   public void forEach(Consumer<Entity> var1) {
      if (this.iterated != null) {
         throw new UnsupportedOperationException("Only one concurrent iteration supported");
      } else {
         this.iterated = this.active;

         try {
            for(Entity â˜ƒ : this.active.values()) {
               â˜ƒ.accept(â˜ƒ);
            }
         } finally {
            this.iterated = null;
         }
      }
   }
}
