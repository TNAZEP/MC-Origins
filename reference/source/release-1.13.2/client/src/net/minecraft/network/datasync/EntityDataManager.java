package net.minecraft.network.datasync;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.Nullable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityDataManager {
   private static final Logger field_190303_a = LogManager.getLogger();
   private static final Map<Class<? extends Entity>, Integer> field_187232_a = Maps.newHashMap();
   private final Entity field_187233_b;
   private final Map<Integer, EntityDataManager.DataEntry<?>> field_187234_c = Maps.newHashMap();
   private final ReadWriteLock field_187235_d = new ReentrantReadWriteLock();
   private boolean field_187236_e = true;
   private boolean field_187237_f;

   public EntityDataManager(Entity var1) {
      this.field_187233_b = ☃;
   }

   public static <T> DataParameter<T> func_187226_a(Class<? extends Entity> var0, DataSerializer<T> var1) {
      if (field_190303_a.isDebugEnabled()) {
         try {
            Class<?> ☃ = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
            if (!☃.equals(☃)) {
               field_190303_a.debug("defineId called for: {} from {}", ☃, ☃, new RuntimeException());
            }
         } catch (ClassNotFoundException var5) {
         }
      }

      int ☃;
      if (field_187232_a.containsKey(☃)) {
         ☃ = field_187232_a.get(☃) + 1;
      } else {
         int ☃ = 0;
         Class<?> ☃x = ☃;

         while(☃x != Entity.class) {
            ☃x = ☃x.getSuperclass();
            if (field_187232_a.containsKey(☃x)) {
               ☃ = field_187232_a.get(☃x) + 1;
               break;
            }
         }

         ☃ = ☃;
      }

      if (☃ > 254) {
         throw new IllegalArgumentException("Data value id is too big with " + ☃ + "! (Max is " + 254 + ")");
      } else {
         field_187232_a.put(☃, ☃);
         return ☃.func_187161_a(☃);
      }
   }

   public <T> void func_187214_a(DataParameter<T> var1, T var2) {
      int ☃ = ☃.func_187155_a();
      if (☃ > 254) {
         throw new IllegalArgumentException("Data value id is too big with " + ☃ + "! (Max is " + 254 + ")");
      } else if (this.field_187234_c.containsKey(☃)) {
         throw new IllegalArgumentException("Duplicate id value for " + ☃ + "!");
      } else if (DataSerializers.func_187188_b(☃.func_187156_b()) < 0) {
         throw new IllegalArgumentException("Unregistered serializer " + ☃.func_187156_b() + " for " + ☃ + "!");
      } else {
         this.func_187222_c(☃, ☃);
      }
   }

   private <T> void func_187222_c(DataParameter<T> var1, T var2) {
      EntityDataManager.DataEntry<T> ☃ = new EntityDataManager.DataEntry<>(☃, ☃);
      this.field_187235_d.writeLock().lock();
      this.field_187234_c.put(☃.func_187155_a(), ☃);
      this.field_187236_e = false;
      this.field_187235_d.writeLock().unlock();
   }

   private <T> EntityDataManager.DataEntry<T> func_187219_c(DataParameter<T> var1) {
      this.field_187235_d.readLock().lock();

      EntityDataManager.DataEntry<T> ☃;
      try {
         ☃ = (EntityDataManager.DataEntry)this.field_187234_c.get(☃.func_187155_a());
      } catch (Throwable var6) {
         CrashReport ☃ = CrashReport.func_85055_a(var6, "Getting synched entity data");
         CrashReportCategory ☃x = ☃.func_85058_a("Synched entity data");
         ☃x.func_71507_a("Data ID", ☃);
         throw new ReportedException(☃);
      }

      this.field_187235_d.readLock().unlock();
      return ☃;
   }

   public <T> T func_187225_a(DataParameter<T> var1) {
      return this.func_187219_c(☃).func_187206_b();
   }

   public <T> void func_187227_b(DataParameter<T> var1, T var2) {
      EntityDataManager.DataEntry<T> ☃ = this.func_187219_c(☃);
      if (ObjectUtils.notEqual(☃, ☃.func_187206_b())) {
         ☃.func_187210_a(☃);
         this.field_187233_b.func_184206_a(☃);
         ☃.func_187208_a(true);
         this.field_187237_f = true;
      }
   }

   public boolean func_187223_a() {
      return this.field_187237_f;
   }

   public static void func_187229_a(List<EntityDataManager.DataEntry<?>> var0, PacketBuffer var1) throws IOException {
      if (☃ != null) {
         int ☃ = 0;

         for(int ☃x = ☃.size(); ☃ < ☃x; ++☃) {
            func_187220_a(☃, (EntityDataManager.DataEntry)☃.get(☃));
         }
      }

      ☃.writeByte(255);
   }

   @Nullable
   public List<EntityDataManager.DataEntry<?>> func_187221_b() {
      List<EntityDataManager.DataEntry<?>> ☃ = null;
      if (this.field_187237_f) {
         this.field_187235_d.readLock().lock();

         for(EntityDataManager.DataEntry<?> ☃x : this.field_187234_c.values()) {
            if (☃x.func_187209_c()) {
               ☃x.func_187208_a(false);
               if (☃ == null) {
                  ☃ = Lists.<EntityDataManager.DataEntry<?>>newArrayList();
               }

               ☃.add(☃x.func_192735_d());
            }
         }

         this.field_187235_d.readLock().unlock();
      }

      this.field_187237_f = false;
      return ☃;
   }

   public void func_187216_a(PacketBuffer var1) throws IOException {
      this.field_187235_d.readLock().lock();

      for(EntityDataManager.DataEntry<?> ☃ : this.field_187234_c.values()) {
         func_187220_a(☃, ☃);
      }

      this.field_187235_d.readLock().unlock();
      ☃.writeByte(255);
   }

   @Nullable
   public List<EntityDataManager.DataEntry<?>> func_187231_c() {
      List<EntityDataManager.DataEntry<?>> ☃ = null;
      this.field_187235_d.readLock().lock();

      for(EntityDataManager.DataEntry<?> ☃x : this.field_187234_c.values()) {
         if (☃ == null) {
            ☃ = Lists.<EntityDataManager.DataEntry<?>>newArrayList();
         }

         ☃.add(☃x.func_192735_d());
      }

      this.field_187235_d.readLock().unlock();
      return ☃;
   }

   private static <T> void func_187220_a(PacketBuffer var0, EntityDataManager.DataEntry<T> var1) throws IOException {
      DataParameter<T> ☃ = ☃.func_187205_a();
      int ☃x = DataSerializers.func_187188_b(☃.func_187156_b());
      if (☃x < 0) {
         throw new EncoderException("Unknown serializer type " + ☃.func_187156_b());
      } else {
         ☃.writeByte(☃.func_187155_a());
         ☃.func_150787_b(☃x);
         ☃.func_187156_b().func_187160_a(☃, ☃.func_187206_b());
      }
   }

   @Nullable
   public static List<EntityDataManager.DataEntry<?>> func_187215_b(PacketBuffer var0) throws IOException {
      List<EntityDataManager.DataEntry<?>> ☃ = null;

      int ☃;
      while((☃ = ☃.readUnsignedByte()) != 255) {
         if (☃ == null) {
            ☃ = Lists.<EntityDataManager.DataEntry<?>>newArrayList();
         }

         int ☃x = ☃.func_150792_a();
         DataSerializer<?> ☃xx = DataSerializers.func_187190_a(☃x);
         if (☃xx == null) {
            throw new DecoderException("Unknown serializer type " + ☃x);
         }

         ☃.add(func_198167_a(☃, ☃, ☃xx));
      }

      return ☃;
   }

   private static <T> EntityDataManager.DataEntry<T> func_198167_a(PacketBuffer var0, int var1, DataSerializer<T> var2) {
      return new EntityDataManager.DataEntry<>(☃.func_187161_a(☃), ☃.func_187159_a(☃));
   }

   public void func_187218_a(List<EntityDataManager.DataEntry<?>> var1) {
      this.field_187235_d.writeLock().lock();

      for(EntityDataManager.DataEntry<?> ☃ : ☃) {
         EntityDataManager.DataEntry<?> ☃x = (EntityDataManager.DataEntry)this.field_187234_c.get(☃.func_187205_a().func_187155_a());
         if (☃x != null) {
            this.func_187224_a(☃x, ☃);
            this.field_187233_b.func_184206_a(☃.func_187205_a());
         }
      }

      this.field_187235_d.writeLock().unlock();
      this.field_187237_f = true;
   }

   protected <T> void func_187224_a(EntityDataManager.DataEntry<T> var1, EntityDataManager.DataEntry<?> var2) {
      ☃.func_187210_a((T)☃.func_187206_b());
   }

   public boolean func_187228_d() {
      return this.field_187236_e;
   }

   public void func_187230_e() {
      this.field_187237_f = false;
      this.field_187235_d.readLock().lock();

      for(EntityDataManager.DataEntry<?> ☃ : this.field_187234_c.values()) {
         ☃.func_187208_a(false);
      }

      this.field_187235_d.readLock().unlock();
   }

   public static class DataEntry<T> {
      private final DataParameter<T> field_187211_a;
      private T field_187212_b;
      private boolean field_187213_c;

      public DataEntry(DataParameter<T> var1, T var2) {
         this.field_187211_a = ☃;
         this.field_187212_b = ☃;
         this.field_187213_c = true;
      }

      public DataParameter<T> func_187205_a() {
         return this.field_187211_a;
      }

      public void func_187210_a(T var1) {
         this.field_187212_b = ☃;
      }

      public T func_187206_b() {
         return this.field_187212_b;
      }

      public boolean func_187209_c() {
         return this.field_187213_c;
      }

      public void func_187208_a(boolean var1) {
         this.field_187213_c = ☃;
      }

      public EntityDataManager.DataEntry<T> func_192735_d() {
         return new EntityDataManager.DataEntry<>(this.field_187211_a, this.field_187211_a.func_187156_b().func_192717_a(this.field_187212_b));
      }
   }
}
