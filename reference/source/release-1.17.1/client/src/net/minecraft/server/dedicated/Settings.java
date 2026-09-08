package net.minecraft.server.dedicated;

import com.google.common.base.MoreObjects;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.core.RegistryAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Settings<T extends Settings<T>> {
   private static final Logger LOGGER = LogManager.getLogger();
   protected final Properties properties;

   public Settings(Properties var1) {
      this.properties = â˜ƒ;
   }

   public static Properties loadFromFile(Path var0) {
      Properties â˜ƒ = new Properties();

      try {
         InputStream â˜ƒx = Files.newInputStream(â˜ƒ);

         try {
            â˜ƒ.load(â˜ƒx);
         } catch (Throwable var6) {
            if (â˜ƒx != null) {
               try {
                  â˜ƒx.close();
               } catch (Throwable var5) {
                  var6.addSuppressed(var5);
               }
            }

            throw var6;
         }

         if (â˜ƒx != null) {
            â˜ƒx.close();
         }
      } catch (IOException var7) {
         LOGGER.error("Failed to load properties from file: {}", â˜ƒ);
      }

      return â˜ƒ;
   }

   public void store(Path var1) {
      try {
         OutputStream â˜ƒ = Files.newOutputStream(â˜ƒ);

         try {
            this.properties.store(â˜ƒ, "Minecraft server properties");
         } catch (Throwable var6) {
            if (â˜ƒ != null) {
               try {
                  â˜ƒ.close();
               } catch (Throwable var5) {
                  var6.addSuppressed(var5);
               }
            }

            throw var6;
         }

         if (â˜ƒ != null) {
            â˜ƒ.close();
         }
      } catch (IOException var7) {
         LOGGER.error("Failed to store properties to file: {}", â˜ƒ);
      }
   }

   private static <V extends Number> Function<String, V> wrapNumberDeserializer(Function<String, V> var0) {
      return var1 -> {
         try {
            return (Number)â˜ƒ.apply(var1);
         } catch (NumberFormatException var3) {
            return null;
         }
      };
   }

   protected static <V> Function<String, V> dispatchNumberOrString(IntFunction<V> var0, Function<String, V> var1) {
      return var2 -> {
         try {
            return â˜ƒ.apply(Integer.parseInt(var2));
         } catch (NumberFormatException var4) {
            return â˜ƒ.apply(var2);
         }
      };
   }

   @Nullable
   private String getStringRaw(String var1) {
      return (String)this.properties.get(â˜ƒ);
   }

   @Nullable
   protected <V> V getLegacy(String var1, Function<String, V> var2) {
      String â˜ƒ = this.getStringRaw(â˜ƒ);
      if (â˜ƒ == null) {
         return null;
      } else {
         this.properties.remove(â˜ƒ);
         return (V)â˜ƒ.apply(â˜ƒ);
      }
   }

   protected <V> V get(String var1, Function<String, V> var2, Function<V, String> var3, V var4) {
      String â˜ƒ = this.getStringRaw(â˜ƒ);
      V â˜ƒx = MoreObjects.firstNonNull((V)(â˜ƒ != null ? â˜ƒ.apply(â˜ƒ) : null), â˜ƒ);
      this.properties.put(â˜ƒ, â˜ƒ.apply(â˜ƒx));
      return â˜ƒx;
   }

   protected <V> Settings<T>.MutableValue<V> getMutable(String var1, Function<String, V> var2, Function<V, String> var3, V var4) {
      String â˜ƒ = this.getStringRaw(â˜ƒ);
      V â˜ƒx = MoreObjects.firstNonNull((V)(â˜ƒ != null ? â˜ƒ.apply(â˜ƒ) : null), â˜ƒ);
      this.properties.put(â˜ƒ, â˜ƒ.apply(â˜ƒx));
      return new Settings.MutableValue<>(â˜ƒ, â˜ƒx, â˜ƒ);
   }

   protected <V> V get(String var1, Function<String, V> var2, UnaryOperator<V> var3, Function<V, String> var4, V var5) {
      return this.get(â˜ƒ, var2x -> {
         V â˜ƒ = (V)â˜ƒ.apply(var2x);
         return â˜ƒ != null ? â˜ƒ.apply(â˜ƒ) : null;
      }, â˜ƒ, â˜ƒ);
   }

   protected <V> V get(String var1, Function<String, V> var2, V var3) {
      return this.get(â˜ƒ, â˜ƒ, Objects::toString, â˜ƒ);
   }

   protected <V> Settings<T>.MutableValue<V> getMutable(String var1, Function<String, V> var2, V var3) {
      return this.getMutable(â˜ƒ, â˜ƒ, Objects::toString, â˜ƒ);
   }

   protected String get(String var1, String var2) {
      return this.get(â˜ƒ, Function.identity(), Function.identity(), â˜ƒ);
   }

   @Nullable
   protected String getLegacyString(String var1) {
      return this.getLegacy(â˜ƒ, Function.identity());
   }

   protected int get(String var1, int var2) {
      return this.get(â˜ƒ, wrapNumberDeserializer(Integer::parseInt), Integer.valueOf(â˜ƒ));
   }

   protected Settings<T>.MutableValue<Integer> getMutable(String var1, int var2) {
      return this.getMutable(â˜ƒ, wrapNumberDeserializer(Integer::parseInt), â˜ƒ);
   }

   protected int get(String var1, UnaryOperator<Integer> var2, int var3) {
      return this.get(â˜ƒ, wrapNumberDeserializer(Integer::parseInt), â˜ƒ, Objects::toString, â˜ƒ);
   }

   protected long get(String var1, long var2) {
      return this.get(â˜ƒ, wrapNumberDeserializer(Long::parseLong), â˜ƒ);
   }

   protected boolean get(String var1, boolean var2) {
      return this.get(â˜ƒ, Boolean::valueOf, â˜ƒ);
   }

   protected Settings<T>.MutableValue<Boolean> getMutable(String var1, boolean var2) {
      return this.getMutable(â˜ƒ, Boolean::valueOf, â˜ƒ);
   }

   @Nullable
   protected Boolean getLegacyBoolean(String var1) {
      return this.getLegacy(â˜ƒ, Boolean::valueOf);
   }

   protected Properties cloneProperties() {
      Properties â˜ƒ = new Properties();
      â˜ƒ.putAll(this.properties);
      return â˜ƒ;
   }

   protected abstract T reload(RegistryAccess var1, Properties var2);

   public class MutableValue<V> implements Supplier<V> {
      private final String key;
      private final V value;
      private final Function<V, String> serializer;

      MutableValue(String var2, V var3, Function<V, String> var4) {
         this.key = â˜ƒ;
         this.value = â˜ƒ;
         this.serializer = â˜ƒ;
      }

      public V get() {
         return this.value;
      }

      public T update(RegistryAccess var1, V var2) {
         Properties â˜ƒ = Settings.this.cloneProperties();
         â˜ƒ.put(this.key, this.serializer.apply(â˜ƒ));
         return Settings.this.reload(â˜ƒ, â˜ƒ);
      }
   }
}
