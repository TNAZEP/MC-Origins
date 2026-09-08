package net.minecraft.world.level.entity;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityLookup<T extends EntityAccess> {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Int2ObjectMap<T> byId = new Int2ObjectLinkedOpenHashMap<>();
   private final Map<UUID, T> byUuid = Maps.newHashMap();

   public <U extends T> void getEntities(EntityTypeTest<T, U> var1, Consumer<U> var2) {
      for(T â˜ƒ : this.byId.values()) {
         U â˜ƒx = â˜ƒ.tryCast(â˜ƒ);
         if (â˜ƒx != null) {
            â˜ƒ.accept(â˜ƒx);
         }
      }
   }

   public Iterable<T> getAllEntities() {
      return Iterables.unmodifiableIterable(this.byId.values());
   }

   public void add(T var1) {
      UUID â˜ƒ = â˜ƒ.getUUID();
      if (this.byUuid.containsKey(â˜ƒ)) {
         LOGGER.warn("Duplicate entity UUID {}: {}", â˜ƒ, â˜ƒ);
      } else {
         this.byUuid.put(â˜ƒ, â˜ƒ);
         this.byId.put(â˜ƒ.getId(), â˜ƒ);
      }
   }

   public void remove(T var1) {
      this.byUuid.remove(â˜ƒ.getUUID());
      this.byId.remove(â˜ƒ.getId());
   }

   @Nullable
   public T getEntity(int var1) {
      return this.byId.get(â˜ƒ);
   }

   @Nullable
   public T getEntity(UUID var1) {
      return (T)this.byUuid.get(â˜ƒ);
   }

   public int count() {
      return this.byUuid.size();
   }
}
