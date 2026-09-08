package net.minecraft.data;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import net.minecraft.init.Bootstrap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataGenerator {
   private static final Logger field_200393_a = LogManager.getLogger();
   private final Collection<Path> field_200394_b;
   private final Path field_200395_c;
   private final List<IDataProvider> field_200396_d = Lists.<IDataProvider>newArrayList();

   public DataGenerator(Path var1, Collection<Path> var2) {
      this.field_200395_c = ☃;
      this.field_200394_b = ☃;
   }

   public Collection<Path> func_200389_a() {
      return this.field_200394_b;
   }

   public Path func_200391_b() {
      return this.field_200395_c;
   }

   public void func_200392_c() throws IOException {
      DirectoryCache ☃ = new DirectoryCache(this.field_200395_c, "cache");
      Stopwatch ☃x = Stopwatch.createUnstarted();

      for(IDataProvider ☃xx : this.field_200396_d) {
         field_200393_a.info("Starting provider: {}", ☃xx.func_200397_b());
         ☃x.start();
         ☃xx.func_200398_a(☃);
         ☃x.stop();
         field_200393_a.info("{} finished after {} ms", ☃xx.func_200397_b(), ☃x.elapsed(TimeUnit.MILLISECONDS));
         ☃x.reset();
      }

      ☃.func_208317_a();
   }

   public void func_200390_a(IDataProvider var1) {
      this.field_200396_d.add(☃);
   }

   static {
      Bootstrap.func_151354_b();
   }
}
