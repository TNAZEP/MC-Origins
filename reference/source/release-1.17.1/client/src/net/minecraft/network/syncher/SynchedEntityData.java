package net.minecraft.network.syncher;

import com.google.common.collect.Lists;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SynchedEntityData {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Object2IntMap<Class<? extends Entity>> ENTITY_ID_POOL = new Object2IntOpenHashMap();
   private static final int EOF_MARKER = 255;
   private static final int MAX_ID_VALUE = 254;
   private final Entity entity;
   private final Int2ObjectMap<SynchedEntityData.DataItem<?>> itemsById = new Int2ObjectOpenHashMap<>();
   private final ReadWriteLock lock = new ReentrantReadWriteLock();
   private boolean isEmpty = true;
   private boolean isDirty;

   public SynchedEntityData(Entity var1) {
      this.entity = â˜ƒ;
   }

   public static <T> EntityDataAccessor<T> defineId(Class<? extends Entity> var0, EntityDataSerializer<T> var1) {
      if (LOGGER.isDebugEnabled()) {
         try {
            Class<?> â˜ƒ = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
            if (!â˜ƒ.equals(â˜ƒ)) {
               LOGGER.debug("defineId called for: {} from {}", â˜ƒ, â˜ƒ, new RuntimeException());
            }
         } catch (ClassNotFoundException var5) {
         }
      }

      int â˜ƒ;
      if (ENTITY_ID_POOL.containsKey(â˜ƒ)) {
         â˜ƒ = ENTITY_ID_POOL.getInt(â˜ƒ) + 1;
      } else {
         int â˜ƒ = 0;
         Class<?> â˜ƒx = â˜ƒ;

         while(â˜ƒx != Entity.class) {
            â˜ƒx = â˜ƒx.getSuperclass();
            if (ENTITY_ID_POOL.containsKey(â˜ƒx)) {
               â˜ƒ = ENTITY_ID_POOL.getInt(â˜ƒx) + 1;
               break;
            }
         }

         â˜ƒ = â˜ƒ;
      }

      if (â˜ƒ > 254) {
         throw new IllegalArgumentException("Data value id is too big with " + â˜ƒ + "! (Max is 254)");
      } else {
         ENTITY_ID_POOL.put(â˜ƒ, â˜ƒ);
         return â˜ƒ.createAccessor(â˜ƒ);
      }
   }

   public <T> void define(EntityDataAccessor<T> var1, T var2) {
      int â˜ƒ = â˜ƒ.getId();
      if (â˜ƒ > 254) {
         throw new IllegalArgumentException("Data value id is too big with " + â˜ƒ + "! (Max is 254)");
      } else if (this.itemsById.containsKey(â˜ƒ)) {
         throw new IllegalArgumentException("Duplicate id value for " + â˜ƒ + "!");
      } else if (EntityDataSerializers.getSerializedId(â˜ƒ.getSerializer()) < 0) {
         throw new IllegalArgumentException("Unregistered serializer " + â˜ƒ.getSerializer() + " for " + â˜ƒ + "!");
      } else {
         this.createDataItem(â˜ƒ, â˜ƒ);
      }
   }

   private <T> void createDataItem(EntityDataAccessor<T> var1, T var2) {
      SynchedEntityData.DataItem<T> â˜ƒ = new SynchedEntityData.DataItem<>(â˜ƒ, â˜ƒ);
      this.lock.writeLock().lock();
      this.itemsById.put(â˜ƒ.getId(), â˜ƒ);
      this.isEmpty = false;
      this.lock.writeLock().unlock();
   }

   private <T> SynchedEntityData.DataItem<T> getItem(EntityDataAccessor<T> var1) {
      this.lock.readLock().lock();

      SynchedEntityData.DataItem<T> â˜ƒ;
      try {
         â˜ƒ = (SynchedEntityData.DataItem)this.itemsById.get(â˜ƒ.getId());
      } catch (Throwable var9) {
         CrashReport â˜ƒ = CrashReport.forThrowable(var9, "Getting synched entity data");
         CrashReportCategory â˜ƒx = â˜ƒ.addCategory("Synched entity data");
         â˜ƒx.setDetail("Data ID", â˜ƒ);
         throw new ReportedException(â˜ƒ);
      } finally {
         this.lock.readLock().unlock();
      }

      return â˜ƒ;
   }

   public <T> T get(EntityDataAccessor<T> var1) {
      return this.getItem(â˜ƒ).getValue();
   }

   public <T> void set(EntityDataAccessor<T> var1, T var2) {
      SynchedEntityData.DataItem<T> â˜ƒ = this.getItem(â˜ƒ);
      if (ObjectUtils.notEqual(â˜ƒ, â˜ƒ.getValue())) {
         â˜ƒ.setValue(â˜ƒ);
         this.entity.onSyncedDataUpdated(â˜ƒ);
         â˜ƒ.setDirty(true);
         this.isDirty = true;
      }
   }

   public boolean isDirty() {
      return this.isDirty;
   }

   public static void pack(@Nullable List<SynchedEntityData.DataItem<?>> var0, FriendlyByteBuf var1) {
      if (â˜ƒ != null) {
         for(SynchedEntityData.DataItem<?> â˜ƒ : â˜ƒ) {
            writeDataItem(â˜ƒ, â˜ƒ);
         }
      }

      â˜ƒ.writeByte(255);
   }

   @Nullable
   public List<SynchedEntityData.DataItem<?>> packDirty() {
      List<SynchedEntityData.DataItem<?>> â˜ƒ = null;
      if (this.isDirty) {
         this.lock.readLock().lock();

         for(SynchedEntityData.DataItem<?> â˜ƒx : this.itemsById.values()) {
            if (â˜ƒx.isDirty()) {
               â˜ƒx.setDirty(false);
               if (â˜ƒ == null) {
                  â˜ƒ = Lists.<SynchedEntityData.DataItem<?>>newArrayList();
               }

               â˜ƒ.add(â˜ƒx.copy());
            }
         }

         this.lock.readLock().unlock();
      }

      this.isDirty = false;
      return â˜ƒ;
   }

   @Nullable
   public List<SynchedEntityData.DataItem<?>> getAll() {
      List<SynchedEntityData.DataItem<?>> â˜ƒ = null;
      this.lock.readLock().lock();

      for(SynchedEntityData.DataItem<?> â˜ƒx : this.itemsById.values()) {
         if (â˜ƒ == null) {
            â˜ƒ = Lists.<SynchedEntityData.DataItem<?>>newArrayList();
         }

         â˜ƒ.add(â˜ƒx.copy());
      }

      this.lock.readLock().unlock();
      return â˜ƒ;
   }

   private static <T> void writeDataItem(FriendlyByteBuf var0, SynchedEntityData.DataItem<T> var1) {
      EntityDataAccessor<T> â˜ƒ = â˜ƒ.getAccessor();
      int â˜ƒx = EntityDataSerializers.getSerializedId(â˜ƒ.getSerializer());
      if (â˜ƒx < 0) {
         throw new EncoderException("Unknown serializer type " + â˜ƒ.getSerializer());
      } else {
         â˜ƒ.writeByte(â˜ƒ.getId());
         â˜ƒ.writeVarInt(â˜ƒx);
         â˜ƒ.getSerializer().write(â˜ƒ, â˜ƒ.getValue());
      }
   }

   @Nullable
   public static List<SynchedEntityData.DataItem<?>> unpack(FriendlyByteBuf var0) {
      List<SynchedEntityData.DataItem<?>> â˜ƒ = null;

      int â˜ƒ;
      while((â˜ƒ = â˜ƒ.readUnsignedByte()) != 255) {
         if (â˜ƒ == null) {
            â˜ƒ = Lists.<SynchedEntityData.DataItem<?>>newArrayList();
         }

         int â˜ƒx = â˜ƒ.readVarInt();
         EntityDataSerializer<?> â˜ƒxx = EntityDataSerializers.getSerializer(â˜ƒx);
         if (â˜ƒxx == null) {
            throw new DecoderException("Unknown serializer type " + â˜ƒx);
         }

         â˜ƒ.add(genericHelper(â˜ƒ, â˜ƒ, â˜ƒxx));
      }

      return â˜ƒ;
   }

   private static <T> SynchedEntityData.DataItem<T> genericHelper(FriendlyByteBuf var0, int var1, EntityDataSerializer<T> var2) {
      return new SynchedEntityData.DataItem<>(â˜ƒ.createAccessor(â˜ƒ), â˜ƒ.read(â˜ƒ));
   }

   public void assignValues(List<SynchedEntityData.DataItem<?>> var1) {
      this.lock.writeLock().lock();

      try {
         for(SynchedEntityData.DataItem<?> â˜ƒ : â˜ƒ) {
            SynchedEntityData.DataItem<?> â˜ƒx = this.itemsById.get(â˜ƒ.getAccessor().getId());
            if (â˜ƒx != null) {
               this.assignValue(â˜ƒx, â˜ƒ);
               this.entity.onSyncedDataUpdated(â˜ƒ.getAccessor());
            }
         }
      } finally {
         this.lock.writeLock().unlock();
      }

      this.isDirty = true;
   }

   private <T> void assignValue(SynchedEntityData.DataItem<T> var1, SynchedEntityData.DataItem<?> var2) {
      if (!Objects.equals(â˜ƒ.accessor.getSerializer(), â˜ƒ.accessor.getSerializer())) {
         throw new IllegalStateException(
            String.format(
               "Invalid entity data item type for field %d on entity %s: old=%s(%s), new=%s(%s)",
               â˜ƒ.accessor.getId(),
               this.entity,
               â˜ƒ.value,
               â˜ƒ.value.getClass(),
               â˜ƒ.value,
               â˜ƒ.value.getClass()
            )
         );
      } else {
         â˜ƒ.setValue((T)â˜ƒ.getValue());
      }
   }

   public boolean isEmpty() {
      return this.isEmpty;
   }

   public void clearDirty() {
      this.isDirty = false;
      this.lock.readLock().lock();

      for(SynchedEntityData.DataItem<?> â˜ƒ : this.itemsById.values()) {
         â˜ƒ.setDirty(false);
      }

      this.lock.readLock().unlock();
   }

   public static class DataItem<T> {
      final EntityDataAccessor<T> accessor;
      T value;
      private boolean dirty;

      public DataItem(EntityDataAccessor<T> var1, T var2) {
         this.accessor = â˜ƒ;
         this.value = â˜ƒ;
         this.dirty = true;
      }

      public EntityDataAccessor<T> getAccessor() {
         return this.accessor;
      }

      public void setValue(T var1) {
         this.value = â˜ƒ;
      }

      public T getValue() {
         return this.value;
      }

      public boolean isDirty() {
         return this.dirty;
      }

      public void setDirty(boolean var1) {
         this.dirty = â˜ƒ;
      }

      public SynchedEntityData.DataItem<T> copy() {
         return new SynchedEntityData.DataItem<>(this.accessor, this.accessor.getSerializer().copy(this.value));
      }
   }
}
