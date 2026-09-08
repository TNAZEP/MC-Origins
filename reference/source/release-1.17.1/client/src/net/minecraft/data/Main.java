package net.minecraft.data;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collectors;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.minecraft.SharedConstants;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.info.BlockListReport;
import net.minecraft.data.info.CommandsReport;
import net.minecraft.data.info.RegistryDumpReport;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.models.ModelProvider;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.structures.NbtToSnbt;
import net.minecraft.data.structures.SnbtToNbt;
import net.minecraft.data.structures.StructureUpdater;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.data.tags.GameEventTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.worldgen.biome.BiomeReport;
import net.minecraft.obfuscate.DontObfuscate;

public class Main {
   @DontObfuscate
   public static void main(String[] var0) throws IOException {
      SharedConstants.tryDetectVersion();
      OptionParser â˜ƒ = new OptionParser();
      OptionSpec<Void> â˜ƒx = â˜ƒ.accepts("help", "Show the help menu").forHelp();
      OptionSpec<Void> â˜ƒxx = â˜ƒ.accepts("server", "Include server generators");
      OptionSpec<Void> â˜ƒxxx = â˜ƒ.accepts("client", "Include client generators");
      OptionSpec<Void> â˜ƒxxxx = â˜ƒ.accepts("dev", "Include development tools");
      OptionSpec<Void> â˜ƒxxxxx = â˜ƒ.accepts("reports", "Include data reports");
      OptionSpec<Void> â˜ƒxxxxxx = â˜ƒ.accepts("validate", "Validate inputs");
      OptionSpec<Void> â˜ƒxxxxxxx = â˜ƒ.accepts("all", "Include all generators");
      OptionSpec<String> â˜ƒxxxxxxxx = â˜ƒ.accepts("output", "Output folder").withRequiredArg().defaultsTo("generated");
      OptionSpec<String> â˜ƒxxxxxxxxx = â˜ƒ.accepts("input", "Input folder").withRequiredArg();
      OptionSet â˜ƒxxxxxxxxxx = â˜ƒ.parse(â˜ƒ);
      if (!â˜ƒxxxxxxxxxx.has(â˜ƒx) && â˜ƒxxxxxxxxxx.hasOptions()) {
         Path â˜ƒxxxxxxxxxxx = Paths.get((String)â˜ƒxxxxxxxx.value(â˜ƒxxxxxxxxxx));
         boolean â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxx.has(â˜ƒxxxxxxx);
         boolean â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx || â˜ƒxxxxxxxxxx.has(â˜ƒxxx);
         boolean â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx || â˜ƒxxxxxxxxxx.has(â˜ƒxx);
         boolean â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx || â˜ƒxxxxxxxxxx.has(â˜ƒxxxx);
         boolean â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx || â˜ƒxxxxxxxxxx.has(â˜ƒxxxxx);
         boolean â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx || â˜ƒxxxxxxxxxx.has(â˜ƒxxxxxx);
         DataGenerator â˜ƒxxxxxxxxxxxxxxxxxx = createStandardGenerator(
            â˜ƒxxxxxxxxxxx,
            (Collection<Path>)â˜ƒxxxxxxxxxx.valuesOf(â˜ƒxxxxxxxxx).stream().map(var0x -> Paths.get(var0x)).collect(Collectors.toList()),
            â˜ƒxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxx
         );
         â˜ƒxxxxxxxxxxxxxxxxxx.run();
      } else {
         â˜ƒ.printHelpOn(System.out);
      }
   }

   public static DataGenerator createStandardGenerator(Path var0, Collection<Path> var1, boolean var2, boolean var3, boolean var4, boolean var5, boolean var6) {
      DataGenerator â˜ƒ = new DataGenerator(â˜ƒ, â˜ƒ);
      if (â˜ƒ || â˜ƒ) {
         â˜ƒ.addProvider(new SnbtToNbt(â˜ƒ).addFilter(new StructureUpdater()));
      }

      if (â˜ƒ) {
         â˜ƒ.addProvider(new ModelProvider(â˜ƒ));
      }

      if (â˜ƒ) {
         â˜ƒ.addProvider(new FluidTagsProvider(â˜ƒ));
         BlockTagsProvider â˜ƒ = new BlockTagsProvider(â˜ƒ);
         â˜ƒ.addProvider(â˜ƒ);
         â˜ƒ.addProvider(new ItemTagsProvider(â˜ƒ, â˜ƒ));
         â˜ƒ.addProvider(new EntityTypeTagsProvider(â˜ƒ));
         â˜ƒ.addProvider(new RecipeProvider(â˜ƒ));
         â˜ƒ.addProvider(new AdvancementProvider(â˜ƒ));
         â˜ƒ.addProvider(new LootTableProvider(â˜ƒ));
         â˜ƒ.addProvider(new GameEventTagsProvider(â˜ƒ));
      }

      if (â˜ƒ) {
         â˜ƒ.addProvider(new NbtToSnbt(â˜ƒ));
      }

      if (â˜ƒ) {
         â˜ƒ.addProvider(new BlockListReport(â˜ƒ));
         â˜ƒ.addProvider(new RegistryDumpReport(â˜ƒ));
         â˜ƒ.addProvider(new CommandsReport(â˜ƒ));
         â˜ƒ.addProvider(new BiomeReport(â˜ƒ));
      }

      return â˜ƒ;
   }
}
