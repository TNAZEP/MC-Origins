package net.minecraft.world.level.entity;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.util.ClassInstanceMultiMap;
import net.minecraft.util.VisibleForDebug;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntitySection<T> {
   protected static final Logger LOGGER = LogManager.getLogger();
   private final ClassInstanceMultiMap<T> storage;
   private Visibility chunkStatus;

   public EntitySection(Class<T> var1, Visibility var2) {
      this.chunkStatus = â˜ƒ;
      this.storage = new ClassInstanceMultiMap<>(â˜ƒ);
   }

   public void add(T var1) {
      this.storage.add(â˜ƒ);
   }

   public boolean remove(T var1) {
      return this.storage.remove(â˜ƒ);
   }

   public void getEntities(Predicate<? super T> var1, Consumer<T> var2) {
      for(T â˜ƒ : this.storage) {
         if (â˜ƒ.test(â˜ƒ)) {
            â˜ƒ.accept(â˜ƒ);
         }
      }
   }

   public <U extends T> void getEntities(EntityTypeTest<T, U> var1, Predicate<? super U> var2, Consumer<? super U> var3) {
      for(T â˜ƒ : this.storage.find(â˜ƒ.getBaseClass())) {
         U â˜ƒx = (U)â˜ƒ.tryCast(â˜ƒ);
         if (â˜ƒx != null && â˜ƒ.test(â˜ƒx)) {
            â˜ƒ.accept(â˜ƒx);
         }
      }
   }

   public boolean isEmpty() {
      return this.storage.isEmpty();
   }

   public Stream<T> getEntities() {
      return this.storage.stream();
   }

   public Visibility getStatus() {
      return this.chunkStatus;
   }

   public Visibility updateChunkStatus(Visibility var1) {
      Visibility â˜ƒ = this.chunkStatus;
      this.chunkStatus = â˜ƒ;
      return â˜ƒ;
   }

   @VisibleForDebug
   public int size() {
      return this.storage.size();
   }
}
