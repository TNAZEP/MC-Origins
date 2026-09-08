package net.minecraft.world.level.entity;

import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.world.phys.AABB;

public class LevelEntityGetterAdapter<T extends EntityAccess> implements LevelEntityGetter<T> {
   private final EntityLookup<T> visibleEntities;
   private final EntitySectionStorage<T> sectionStorage;

   public LevelEntityGetterAdapter(EntityLookup<T> var1, EntitySectionStorage<T> var2) {
      this.visibleEntities = â˜ƒ;
      this.sectionStorage = â˜ƒ;
   }

   @Nullable
   @Override
   public T get(int var1) {
      return this.visibleEntities.getEntity(â˜ƒ);
   }

   @Nullable
   @Override
   public T get(UUID var1) {
      return this.visibleEntities.getEntity(â˜ƒ);
   }

   @Override
   public Iterable<T> getAll() {
      return this.visibleEntities.getAllEntities();
   }

   @Override
   public <U extends T> void get(EntityTypeTest<T, U> var1, Consumer<U> var2) {
      this.visibleEntities.getEntities(â˜ƒ, â˜ƒ);
   }

   @Override
   public void get(AABB var1, Consumer<T> var2) {
      this.sectionStorage.getEntities(â˜ƒ, â˜ƒ);
   }

   @Override
   public <U extends T> void get(EntityTypeTest<T, U> var1, AABB var2, Consumer<U> var3) {
      this.sectionStorage.getEntities(â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
