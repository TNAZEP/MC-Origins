package net.minecraft.data;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import net.minecraft.server.Bootstrap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataGenerator {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Collection<Path> inputFolders;
   private final Path outputFolder;
   private final List<DataProvider> providers = Lists.<DataProvider>newArrayList();

   public DataGenerator(Path var1, Collection<Path> var2) {
      this.outputFolder = â˜ƒ;
      this.inputFolders = â˜ƒ;
   }

   public Collection<Path> getInputFolders() {
      return this.inputFolders;
   }

   public Path getOutputFolder() {
      return this.outputFolder;
   }

   public void run() throws IOException {
      HashCache â˜ƒ = new HashCache(this.outputFolder, "cache");
      â˜ƒ.keep(this.getOutputFolder().resolve("version.json"));
      Stopwatch â˜ƒx = Stopwatch.createStarted();
      Stopwatch â˜ƒxx = Stopwatch.createUnstarted();

      for(DataProvider â˜ƒxxx : this.providers) {
         LOGGER.info("Starting provider: {}", â˜ƒxxx.getName());
         â˜ƒxx.start();
         â˜ƒxxx.run(â˜ƒ);
         â˜ƒxx.stop();
         LOGGER.info("{} finished after {} ms", â˜ƒxxx.getName(), â˜ƒxx.elapsed(TimeUnit.MILLISECONDS));
         â˜ƒxx.reset();
      }

      LOGGER.info("All providers took: {} ms", â˜ƒx.elapsed(TimeUnit.MILLISECONDS));
      â˜ƒ.purgeStaleAndWrite();
   }

   public void addProvider(DataProvider var1) {
      this.providers.add(â˜ƒ);
   }

   static {
      Bootstrap.bootStrap();
   }
}
