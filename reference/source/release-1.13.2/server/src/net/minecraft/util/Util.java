package net.minecraft.util;

import com.google.common.collect.Iterators;
import it.unimi.dsi.fastutil.Hash.Strategy;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.function.Consumer;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.state.IProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Util {
   public static LongSupplier field_211180_a = System::nanoTime;
   private static final Logger field_195650_a = LogManager.getLogger();
   private static final Pattern field_209538_b = Pattern.compile(
      ".*\\.|(?:CON|PRN|AUX|NUL|COM1|COM2|COM3|COM4|COM5|COM6|COM7|COM8|COM9|LPT1|LPT2|LPT3|LPT4|LPT5|LPT6|LPT7|LPT8|LPT9)(?:\\..*)?", 2
   );

   public static <K, V> Collector<Entry<? extends K, ? extends V>, ?, Map<K, V>> func_199749_a() {
      return Collectors.toMap(Entry::getKey, Entry::getValue);
   }

   public static <T extends Comparable<T>> String func_200269_a(IProperty<T> var0, Object var1) {
      return ☃.func_177702_a((T)☃);
   }

   public static String func_200697_a(String var0, @Nullable ResourceLocation var1) {
      return ☃ == null ? ☃ + ".unregistered_sadface" : ☃ + '.' + ☃.func_110624_b() + '.' + ☃.func_110623_a().replace('/', '.');
   }

   public static long func_211177_b() {
      return func_211178_c() / 1000000L;
   }

   public static long func_211178_c() {
      return field_211180_a.getAsLong();
   }

   public static long func_211179_d() {
      return Instant.now().toEpochMilli();
   }

   public static Util.EnumOS func_110647_a() {
      String ☃ = System.getProperty("os.name").toLowerCase(Locale.ROOT);
      if (☃.contains("win")) {
         return Util.EnumOS.WINDOWS;
      } else if (☃.contains("mac")) {
         return Util.EnumOS.OSX;
      } else if (☃.contains("solaris")) {
         return Util.EnumOS.SOLARIS;
      } else if (☃.contains("sunos")) {
         return Util.EnumOS.SOLARIS;
      } else if (☃.contains("linux")) {
         return Util.EnumOS.LINUX;
      } else {
         return ☃.contains("unix") ? Util.EnumOS.LINUX : Util.EnumOS.UNKNOWN;
      }
   }

   public static Stream<String> func_211565_f() {
      RuntimeMXBean ☃ = ManagementFactory.getRuntimeMXBean();
      return ☃.getInputArguments().stream().filter(var0x -> var0x.startsWith("-X"));
   }

   public static boolean func_209537_a(Path var0) {
      Path ☃ = ☃.normalize();
      return ☃.equals(☃);
   }

   public static boolean func_209536_b(Path var0) {
      for(Path ☃ : ☃) {
         if (field_209538_b.matcher(☃.toString()).matches()) {
            return false;
         }
      }

      return true;
   }

   public static Path func_209535_a(Path var0, String var1, String var2) {
      String ☃ = ☃ + ☃;
      Path ☃x = Paths.get(☃);
      if (☃x.endsWith(☃)) {
         throw new InvalidPathException(☃, "empty resource name");
      } else {
         return ☃.resolve(☃x);
      }
   }

   @Nullable
   public static <V> V func_181617_a(FutureTask<V> var0, Logger var1) {
      try {
         ☃.run();
         return (V)☃.get();
      } catch (ExecutionException var3) {
         ☃.fatal("Error executing task", var3);
      } catch (InterruptedException var4) {
         ☃.fatal("Error executing task", var4);
      }

      return null;
   }

   public static <T> T func_184878_a(List<T> var0) {
      return (T)☃.get(☃.size() - 1);
   }

   public static <T> T func_195647_a(Iterable<T> var0, @Nullable T var1) {
      Iterator<T> ☃ = ☃.iterator();
      T ☃x = (T)☃.next();
      if (☃ != null) {
         T ☃xx = ☃x;

         while(☃xx != ☃) {
            if (☃.hasNext()) {
               ☃xx = (T)☃.next();
            }
         }

         if (☃.hasNext()) {
            return (T)☃.next();
         }
      }

      return ☃x;
   }

   public static <T> T func_195648_b(Iterable<T> var0, @Nullable T var1) {
      Iterator<T> ☃ = ☃.iterator();

      T ☃;
      T ☃;
      for(☃ = null; ☃.hasNext(); ☃ = ☃) {
         ☃ = (T)☃.next();
         if (☃ == ☃) {
            if (☃ == null) {
               ☃ = (T)(☃.hasNext() ? Iterators.getLast(☃) : ☃);
            }
            break;
         }
      }

      return ☃;
   }

   public static <T> T func_199748_a(Supplier<T> var0) {
      return (T)☃.get();
   }

   public static <T> T func_200696_a(T var0, Consumer<T> var1) {
      ☃.accept(☃);
      return ☃;
   }

   public static <K> Strategy<K> func_212443_g() {
      return Util.IdentityStrategy.INSTANCE;
   }

   public static enum EnumOS {
      LINUX,
      SOLARIS,
      WINDOWS {
      },
      OSX {
      },
      UNKNOWN;

      private EnumOS() {
      }
   }

   static enum IdentityStrategy implements Strategy<Object> {
      INSTANCE;

      @Override
      public int hashCode(Object var1) {
         return System.identityHashCode(☃);
      }

      @Override
      public boolean equals(Object var1, Object var2) {
         return ☃ == ☃;
      }
   }
}
