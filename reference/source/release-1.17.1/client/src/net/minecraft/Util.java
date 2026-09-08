package net.minecraft;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.MoreExecutors;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import it.unimi.dsi.fastutil.Hash.Strategy;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.Bootstrap;
import net.minecraft.util.Mth;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.world.level.block.state.properties.Property;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Util {
   private static final AtomicInteger WORKER_COUNT = new AtomicInteger(1);
   private static final ExecutorService BOOTSTRAP_EXECUTOR = makeExecutor("Bootstrap");
   private static final ExecutorService BACKGROUND_EXECUTOR = makeExecutor("Main");
   private static final ExecutorService IO_POOL = makeIoExecutor();
   public static LongSupplier timeSource = System::nanoTime;
   public static final UUID NIL_UUID = new UUID(0L, 0L);
   public static final FileSystemProvider ZIP_FILE_SYSTEM_PROVIDER = (FileSystemProvider)FileSystemProvider.installedProviders()
      .stream()
      .filter(var0 -> var0.getScheme().equalsIgnoreCase("jar"))
      .findFirst()
      .orElseThrow(() -> new IllegalStateException("No jar file system provider found"));
   static final Logger LOGGER = LogManager.getLogger();

   public static <K, V> Collector<Entry<? extends K, ? extends V>, ?, Map<K, V>> toMap() {
      return Collectors.toMap(Entry::getKey, Entry::getValue);
   }

   public static <T extends Comparable<T>> String getPropertyName(Property<T> var0, Object var1) {
      return â˜ƒ.getName((T)â˜ƒ);
   }

   public static String makeDescriptionId(String var0, @Nullable ResourceLocation var1) {
      return â˜ƒ == null ? â˜ƒ + ".unregistered_sadface" : â˜ƒ + "." + â˜ƒ.getNamespace() + "." + â˜ƒ.getPath().replace('/', '.');
   }

   public static long getMillis() {
      return getNanos() / 1000000L;
   }

   public static long getNanos() {
      return timeSource.getAsLong();
   }

   public static long getEpochMillis() {
      return Instant.now().toEpochMilli();
   }

   private static ExecutorService makeExecutor(String var0) {
      int â˜ƒx = Mth.clamp(Runtime.getRuntime().availableProcessors() - 1, 1, 7);
      ExecutorService â˜ƒ;
      if (â˜ƒx <= 0) {
         â˜ƒ = MoreExecutors.newDirectExecutorService();
      } else {
         â˜ƒ = new ForkJoinPool(â˜ƒx, var1x -> {
            ForkJoinWorkerThread â˜ƒ = new ForkJoinWorkerThread(var1x) {
               protected void onTermination(Throwable var1) {
                  if (â˜ƒ != null) {
                     Util.LOGGER.warn("{} died", this.getName(), â˜ƒ);
                  } else {
                     Util.LOGGER.debug("{} shutdown", this.getName());
                  }

                  super.onTermination(â˜ƒ);
               }
            };
            â˜ƒ.setName("Worker-" + â˜ƒ + "-" + WORKER_COUNT.getAndIncrement());
            return â˜ƒ;
         }, Util::onThreadException, true);
      }

      return â˜ƒ;
   }

   public static Executor bootstrapExecutor() {
      return BOOTSTRAP_EXECUTOR;
   }

   public static Executor backgroundExecutor() {
      return BACKGROUND_EXECUTOR;
   }

   public static Executor ioPool() {
      return IO_POOL;
   }

   public static void shutdownExecutors() {
      shutdownExecutor(BACKGROUND_EXECUTOR);
      shutdownExecutor(IO_POOL);
   }

   private static void shutdownExecutor(ExecutorService var0) {
      â˜ƒ.shutdown();

      boolean â˜ƒ;
      try {
         â˜ƒ = â˜ƒ.awaitTermination(3L, TimeUnit.SECONDS);
      } catch (InterruptedException var3) {
         â˜ƒ = false;
      }

      if (!â˜ƒ) {
         â˜ƒ.shutdownNow();
      }
   }

   private static ExecutorService makeIoExecutor() {
      return Executors.newCachedThreadPool(var0 -> {
         Thread â˜ƒ = new Thread(var0);
         â˜ƒ.setName("IO-Worker-" + WORKER_COUNT.getAndIncrement());
         â˜ƒ.setUncaughtExceptionHandler(Util::onThreadException);
         return â˜ƒ;
      });
   }

   public static <T> CompletableFuture<T> failedFuture(Throwable var0) {
      CompletableFuture<T> â˜ƒ = new CompletableFuture();
      â˜ƒ.completeExceptionally(â˜ƒ);
      return â˜ƒ;
   }

   public static void throwAsRuntime(Throwable var0) {
      throw â˜ƒ instanceof RuntimeException ? (RuntimeException)â˜ƒ : new RuntimeException(â˜ƒ);
   }

   private static void onThreadException(Thread var0, Throwable var1) {
      pauseInIde(â˜ƒ);
      if (â˜ƒ instanceof CompletionException) {
         â˜ƒ = â˜ƒ.getCause();
      }

      if (â˜ƒ instanceof ReportedException) {
         Bootstrap.realStdoutPrintln(((ReportedException)â˜ƒ).getReport().getFriendlyReport());
         System.exit(-1);
      }

      LOGGER.error(String.format("Caught exception in thread %s", â˜ƒ), â˜ƒ);
   }

   @Nullable
   public static Type<?> fetchChoiceType(TypeReference var0, String var1) {
      return !SharedConstants.CHECK_DATA_FIXER_SCHEMA ? null : doFetchChoiceType(â˜ƒ, â˜ƒ);
   }

   @Nullable
   private static Type<?> doFetchChoiceType(TypeReference var0, String var1) {
      Type<?> â˜ƒ = null;

      try {
         â˜ƒ = DataFixers.getDataFixer().getSchema(DataFixUtils.makeKey(SharedConstants.getCurrentVersion().getWorldVersion())).getChoiceType(â˜ƒ, â˜ƒ);
      } catch (IllegalArgumentException var4) {
         LOGGER.error("No data fixer registered for {}", â˜ƒ);
         if (SharedConstants.IS_RUNNING_IN_IDE) {
            throw var4;
         }
      }

      return â˜ƒ;
   }

   public static Runnable wrapThreadWithTaskName(String var0, Runnable var1) {
      return SharedConstants.IS_RUNNING_IN_IDE ? () -> {
         Thread â˜ƒ = Thread.currentThread();
         String â˜ƒx = â˜ƒ.getName();
         â˜ƒ.setName(â˜ƒ);

         try {
            â˜ƒ.run();
         } finally {
            â˜ƒ.setName(â˜ƒx);
         }
      } : â˜ƒ;
   }

   public static Util.OS getPlatform() {
      String â˜ƒ = System.getProperty("os.name").toLowerCase(Locale.ROOT);
      if (â˜ƒ.contains("win")) {
         return Util.OS.WINDOWS;
      } else if (â˜ƒ.contains("mac")) {
         return Util.OS.OSX;
      } else if (â˜ƒ.contains("solaris")) {
         return Util.OS.SOLARIS;
      } else if (â˜ƒ.contains("sunos")) {
         return Util.OS.SOLARIS;
      } else if (â˜ƒ.contains("linux")) {
         return Util.OS.LINUX;
      } else {
         return â˜ƒ.contains("unix") ? Util.OS.LINUX : Util.OS.UNKNOWN;
      }
   }

   public static Stream<String> getVmArguments() {
      RuntimeMXBean â˜ƒ = ManagementFactory.getRuntimeMXBean();
      return â˜ƒ.getInputArguments().stream().filter(var0x -> var0x.startsWith("-X"));
   }

   public static <T> T lastOf(List<T> var0) {
      return (T)â˜ƒ.get(â˜ƒ.size() - 1);
   }

   public static <T> T findNextInIterable(Iterable<T> var0, @Nullable T var1) {
      Iterator<T> â˜ƒ = â˜ƒ.iterator();
      T â˜ƒx = (T)â˜ƒ.next();
      if (â˜ƒ != null) {
         T â˜ƒxx = â˜ƒx;

         while(â˜ƒxx != â˜ƒ) {
            if (â˜ƒ.hasNext()) {
               â˜ƒxx = (T)â˜ƒ.next();
            }
         }

         if (â˜ƒ.hasNext()) {
            return (T)â˜ƒ.next();
         }
      }

      return â˜ƒx;
   }

   public static <T> T findPreviousInIterable(Iterable<T> var0, @Nullable T var1) {
      Iterator<T> â˜ƒ = â˜ƒ.iterator();

      T â˜ƒ;
      T â˜ƒ;
      for(â˜ƒ = null; â˜ƒ.hasNext(); â˜ƒ = â˜ƒ) {
         â˜ƒ = (T)â˜ƒ.next();
         if (â˜ƒ == â˜ƒ) {
            if (â˜ƒ == null) {
               â˜ƒ = (T)(â˜ƒ.hasNext() ? Iterators.getLast(â˜ƒ) : â˜ƒ);
            }
            break;
         }
      }

      return â˜ƒ;
   }

   public static <T> T make(Supplier<T> var0) {
      return (T)â˜ƒ.get();
   }

   public static <T> T make(T var0, Consumer<T> var1) {
      â˜ƒ.accept(â˜ƒ);
      return â˜ƒ;
   }

   public static <K> Strategy<K> identityStrategy() {
      return Util.IdentityStrategy.INSTANCE;
   }

   public static <V> CompletableFuture<List<V>> sequence(List<? extends CompletableFuture<? extends V>> var0) {
      return (CompletableFuture<List<V>>)â˜ƒ.stream()
         .reduce(CompletableFuture.completedFuture(Lists.newArrayList()), (var0x, var1) -> var1.thenCombine(var0x, (var0xx, var1x) -> {
               List<V> â˜ƒ = Lists.<V>newArrayListWithCapacity(var1x.size() + 1);
               â˜ƒ.addAll(var1x);
               â˜ƒ.add(var0xx);
               return â˜ƒ;
            }), (var0x, var1) -> var0x.thenCombine(var1, (var0xx, var1x) -> {
               List<V> â˜ƒ = Lists.<V>newArrayListWithCapacity(var0xx.size() + var1x.size());
               â˜ƒ.addAll(var0xx);
               â˜ƒ.addAll(var1x);
               return â˜ƒ;
            }));
   }

   public static <V> CompletableFuture<List<V>> sequenceFailFast(List<? extends CompletableFuture<? extends V>> var0) {
      List<V> â˜ƒ = Lists.<V>newArrayListWithCapacity(â˜ƒ.size());
      CompletableFuture<?>[] â˜ƒx = new CompletableFuture[â˜ƒ.size()];
      CompletableFuture<Void> â˜ƒxx = new CompletableFuture();
      â˜ƒ.forEach(var3x -> {
         int â˜ƒ = â˜ƒ.size();
         â˜ƒ.add(null);
         â˜ƒ[â˜ƒ] = var3x.whenComplete((var3xx, var4x) -> {
            if (var4x != null) {
               â˜ƒ.completeExceptionally(var4x);
            } else {
               â˜ƒ.set(â˜ƒ, var3xx);
            }
         });
      });
      return CompletableFuture.allOf(â˜ƒx).applyToEither(â˜ƒxx, var1x -> â˜ƒ);
   }

   public static <T> Stream<T> toStream(Optional<? extends T> var0) {
      return DataFixUtils.orElseGet(â˜ƒ.map(Stream::of), Stream::empty);
   }

   public static <T> Optional<T> ifElse(Optional<T> var0, Consumer<T> var1, Runnable var2) {
      if (â˜ƒ.isPresent()) {
         â˜ƒ.accept(â˜ƒ.get());
      } else {
         â˜ƒ.run();
      }

      return â˜ƒ;
   }

   public static Runnable name(Runnable var0, Supplier<String> var1) {
      return â˜ƒ;
   }

   public static void logAndPauseIfInIde(String var0) {
      LOGGER.error(â˜ƒ);
      if (SharedConstants.IS_RUNNING_IN_IDE) {
         doPause();
      }
   }

   public static <T extends Throwable> T pauseInIde(T var0) {
      if (SharedConstants.IS_RUNNING_IN_IDE) {
         LOGGER.error("Trying to throw a fatal exception, pausing in IDE", â˜ƒ);
         doPause();
      }

      return â˜ƒ;
   }

   private static void doPause() {
      while(true) {
         try {
            Thread.sleep(1000L);
            LOGGER.error("paused");
         } catch (InterruptedException var1) {
            return;
         }
      }
   }

   public static String describeError(Throwable var0) {
      if (â˜ƒ.getCause() != null) {
         return describeError(â˜ƒ.getCause());
      } else {
         return â˜ƒ.getMessage() != null ? â˜ƒ.getMessage() : â˜ƒ.toString();
      }
   }

   public static <T> T getRandom(T[] var0, Random var1) {
      return â˜ƒ[â˜ƒ.nextInt(â˜ƒ.length)];
   }

   public static int getRandom(int[] var0, Random var1) {
      return â˜ƒ[â˜ƒ.nextInt(â˜ƒ.length)];
   }

   public static <T> T getRandom(List<T> var0, Random var1) {
      return (T)â˜ƒ.get(â˜ƒ.nextInt(â˜ƒ.size()));
   }

   private static BooleanSupplier createRenamer(final Path var0, final Path var1) {
      return new BooleanSupplier() {
         public boolean getAsBoolean() {
            try {
               Files.move(â˜ƒ, â˜ƒ);
               return true;
            } catch (IOException var2) {
               Util.LOGGER.error("Failed to rename", var2);
               return false;
            }
         }

         public String toString() {
            return "rename " + â˜ƒ + " to " + â˜ƒ;
         }
      };
   }

   private static BooleanSupplier createDeleter(final Path var0) {
      return new BooleanSupplier() {
         public boolean getAsBoolean() {
            try {
               Files.deleteIfExists(â˜ƒ);
               return true;
            } catch (IOException var2) {
               Util.LOGGER.warn("Failed to delete", var2);
               return false;
            }
         }

         public String toString() {
            return "delete old " + â˜ƒ;
         }
      };
   }

   private static BooleanSupplier createFileDeletedCheck(final Path var0) {
      return new BooleanSupplier() {
         public boolean getAsBoolean() {
            return !Files.exists(â˜ƒ, new LinkOption[0]);
         }

         public String toString() {
            return "verify that " + â˜ƒ + " is deleted";
         }
      };
   }

   private static BooleanSupplier createFileCreatedCheck(final Path var0) {
      return new BooleanSupplier() {
         public boolean getAsBoolean() {
            return Files.isRegularFile(â˜ƒ, new LinkOption[0]);
         }

         public String toString() {
            return "verify that " + â˜ƒ + " is present";
         }
      };
   }

   private static boolean executeInSequence(BooleanSupplier... var0) {
      for(BooleanSupplier â˜ƒ : â˜ƒ) {
         if (!â˜ƒ.getAsBoolean()) {
            LOGGER.warn("Failed to execute {}", â˜ƒ);
            return false;
         }
      }

      return true;
   }

   private static boolean runWithRetries(int var0, String var1, BooleanSupplier... var2) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ; ++â˜ƒ) {
         if (executeInSequence(â˜ƒ)) {
            return true;
         }

         LOGGER.error("Failed to {}, retrying {}/{}", â˜ƒ, â˜ƒ, â˜ƒ);
      }

      LOGGER.error("Failed to {}, aborting, progress might be lost", â˜ƒ);
      return false;
   }

   public static void safeReplaceFile(File var0, File var1, File var2) {
      safeReplaceFile(â˜ƒ.toPath(), â˜ƒ.toPath(), â˜ƒ.toPath());
   }

   public static void safeReplaceFile(Path var0, Path var1, Path var2) {
      int â˜ƒ = 10;
      if (!Files.exists(â˜ƒ, new LinkOption[0])
         || runWithRetries(10, "create backup " + â˜ƒ, createDeleter(â˜ƒ), createRenamer(â˜ƒ, â˜ƒ), createFileCreatedCheck(â˜ƒ))) {
         if (runWithRetries(10, "remove old " + â˜ƒ, createDeleter(â˜ƒ), createFileDeletedCheck(â˜ƒ))) {
            if (!runWithRetries(10, "replace " + â˜ƒ + " with " + â˜ƒ, createRenamer(â˜ƒ, â˜ƒ), createFileCreatedCheck(â˜ƒ))) {
               runWithRetries(10, "restore " + â˜ƒ + " from " + â˜ƒ, createRenamer(â˜ƒ, â˜ƒ), createFileCreatedCheck(â˜ƒ));
            }
         }
      }
   }

   public static int offsetByCodepoints(String var0, int var1, int var2) {
      int â˜ƒ = â˜ƒ.length();
      if (â˜ƒ >= 0) {
         for(int â˜ƒx = 0; â˜ƒ < â˜ƒ && â˜ƒx < â˜ƒ; ++â˜ƒx) {
            if (Character.isHighSurrogate(â˜ƒ.charAt(â˜ƒ++)) && â˜ƒ < â˜ƒ && Character.isLowSurrogate(â˜ƒ.charAt(â˜ƒ))) {
               ++â˜ƒ;
            }
         }
      } else {
         for(int â˜ƒ = â˜ƒ; â˜ƒ > 0 && â˜ƒ < 0; ++â˜ƒ) {
            --â˜ƒ;
            if (Character.isLowSurrogate(â˜ƒ.charAt(â˜ƒ)) && â˜ƒ > 0 && Character.isHighSurrogate(â˜ƒ.charAt(â˜ƒ - 1))) {
               --â˜ƒ;
            }
         }
      }

      return â˜ƒ;
   }

   public static Consumer<String> prefix(String var0, Consumer<String> var1) {
      return var2 -> â˜ƒ.accept(â˜ƒ + var2);
   }

   public static DataResult<int[]> fixedSize(IntStream var0, int var1) {
      int[] â˜ƒ = â˜ƒ.limit((long)(â˜ƒ + 1)).toArray();
      if (â˜ƒ.length != â˜ƒ) {
         String â˜ƒx = "Input is not a list of " + â˜ƒ + " ints";
         return â˜ƒ.length >= â˜ƒ ? DataResult.error(â˜ƒx, Arrays.copyOf(â˜ƒ, â˜ƒ)) : DataResult.error(â˜ƒx);
      } else {
         return DataResult.success(â˜ƒ);
      }
   }

   public static <T> DataResult<List<T>> fixedSize(List<T> var0, int var1) {
      if (â˜ƒ.size() != â˜ƒ) {
         String â˜ƒ = "Input is not a list of " + â˜ƒ + " elements";
         return â˜ƒ.size() >= â˜ƒ ? DataResult.error(â˜ƒ, â˜ƒ.subList(0, â˜ƒ)) : DataResult.error(â˜ƒ);
      } else {
         return DataResult.success(â˜ƒ);
      }
   }

   public static void startTimerHackThread() {
      Thread â˜ƒ = new Thread("Timer hack thread") {
         public void run() {
            while(true) {
               try {
                  Thread.sleep(2147483647L);
               } catch (InterruptedException var2) {
                  Util.LOGGER.warn("Timer hack thread interrupted, that really should not happen");
                  return;
               }
            }
         }
      };
      â˜ƒ.setDaemon(true);
      â˜ƒ.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(LOGGER));
      â˜ƒ.start();
   }

   public static void copyBetweenDirs(Path var0, Path var1, Path var2) throws IOException {
      Path â˜ƒ = â˜ƒ.relativize(â˜ƒ);
      Path â˜ƒx = â˜ƒ.resolve(â˜ƒ);
      Files.copy(â˜ƒ, â˜ƒx);
   }

   public static String sanitizeName(String var0, CharPredicate var1) {
      return (String)â˜ƒ.toLowerCase(Locale.ROOT)
         .chars()
         .mapToObj(var1x -> â˜ƒ.test((char)var1x) ? Character.toString((char)var1x) : "_")
         .collect(Collectors.joining());
   }

   public static <T, R> Function<T, R> memoize(final Function<T, R> var0) {
      return new Function<T, R>() {
         private final Map<T, R> cache = Maps.<T, R>newHashMap();

         public R apply(T var1) {
            return (R)this.cache.computeIfAbsent(â˜ƒ, â˜ƒ);
         }

         public String toString() {
            return "memoize/1[function=" + â˜ƒ + ", size=" + this.cache.size() + "]";
         }
      };
   }

   public static <T, U, R> BiFunction<T, U, R> memoize(final BiFunction<T, U, R> var0) {
      return new BiFunction<T, U, R>() {
         private final Map<Pair<T, U>, R> cache = Maps.<Pair<T, U>, R>newHashMap();

         public R apply(T var1, U var2) {
            return (R)this.cache.computeIfAbsent(Pair.of(â˜ƒ, â˜ƒ), var1x -> â˜ƒ.apply(var1x.getFirst(), var1x.getSecond()));
         }

         public String toString() {
            return "memoize/2[function=" + â˜ƒ + ", size=" + this.cache.size() + "]";
         }
      };
   }

   static enum IdentityStrategy implements Strategy<Object> {
      INSTANCE;

      @Override
      public int hashCode(Object var1) {
         return System.identityHashCode(â˜ƒ);
      }

      @Override
      public boolean equals(Object var1, Object var2) {
         return â˜ƒ == â˜ƒ;
      }
   }

   public static enum OS {
      LINUX,
      SOLARIS,
      WINDOWS {
         @Override
         protected String[] getOpenUrlArguments(URL var1) {
            return new String[]{"rundll32", "url.dll,FileProtocolHandler", â˜ƒ.toString()};
         }
      },
      OSX {
         @Override
         protected String[] getOpenUrlArguments(URL var1) {
            return new String[]{"open", â˜ƒ.toString()};
         }
      },
      UNKNOWN;

      public void openUrl(URL var1) {
         try {
            Process â˜ƒ = (Process)AccessController.doPrivileged(() -> Runtime.getRuntime().exec(this.getOpenUrlArguments(â˜ƒ)));

            for(String â˜ƒx : IOUtils.readLines(â˜ƒ.getErrorStream())) {
               Util.LOGGER.error(â˜ƒx);
            }

            â˜ƒ.getInputStream().close();
            â˜ƒ.getErrorStream().close();
            â˜ƒ.getOutputStream().close();
         } catch (IOException | PrivilegedActionException var5) {
            Util.LOGGER.error("Couldn't open url '{}'", â˜ƒ, var5);
         }
      }

      public void openUri(URI var1) {
         try {
            this.openUrl(â˜ƒ.toURL());
         } catch (MalformedURLException var3) {
            Util.LOGGER.error("Couldn't open uri '{}'", â˜ƒ, var3);
         }
      }

      public void openFile(File var1) {
         try {
            this.openUrl(â˜ƒ.toURI().toURL());
         } catch (MalformedURLException var3) {
            Util.LOGGER.error("Couldn't open file '{}'", â˜ƒ, var3);
         }
      }

      protected String[] getOpenUrlArguments(URL var1) {
         String â˜ƒ = â˜ƒ.toString();
         if ("file".equals(â˜ƒ.getProtocol())) {
            â˜ƒ = â˜ƒ.replace("file:", "file://");
         }

         return new String[]{"xdg-open", â˜ƒ};
      }

      public void openUri(String var1) {
         try {
            this.openUrl(new URI(â˜ƒ).toURL());
         } catch (MalformedURLException | IllegalArgumentException | URISyntaxException var3) {
            Util.LOGGER.error("Couldn't open uri '{}'", â˜ƒ, var3);
         }
      }
   }
}
