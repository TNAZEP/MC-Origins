package net.minecraft.data;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collectors;
import joptsimple.AbstractOptionSpec;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpecBuilder;

public class Main {
   public static void main(String[] var0) throws IOException {
      OptionParser ☃ = new OptionParser();
      AbstractOptionSpec<Void> ☃x = ☃.accepts("help", "Show the help menu").forHelp();
      OptionSpecBuilder ☃xx = ☃.accepts("server", "Include server generators");
      OptionSpecBuilder ☃xxx = ☃.accepts("client", "Include client generators");
      OptionSpecBuilder ☃xxxx = ☃.accepts("dev", "Include development tools");
      OptionSpecBuilder ☃xxxxx = ☃.accepts("reports", "Include data reports");
      OptionSpecBuilder ☃xxxxxx = ☃.accepts("all", "Include all generators");
      ArgumentAcceptingOptionSpec<String> ☃xxxxxxx = ☃.accepts("output", "Output folder").withRequiredArg().defaultsTo("generated");
      ArgumentAcceptingOptionSpec<String> ☃xxxxxxxx = ☃.accepts("input", "Input folder").withRequiredArg();
      OptionSet ☃xxxxxxxxx = ☃.parse(☃);
      if (!☃xxxxxxxxx.has(☃x) && ☃xxxxxxxxx.hasOptions()) {
         Path ☃xxxxxxxxxx = Paths.get((String)☃xxxxxxx.value(☃xxxxxxxxx));
         boolean ☃xxxxxxxxxxx = ☃xxxxxxxxx.has(☃xxx) || ☃xxxxxxxxx.has(☃xxxxxx);
         boolean ☃xxxxxxxxxxxx = ☃xxxxxxxxx.has(☃xx) || ☃xxxxxxxxx.has(☃xxxxxx);
         boolean ☃xxxxxxxxxxxxx = ☃xxxxxxxxx.has(☃xxxx) || ☃xxxxxxxxx.has(☃xxxxxx);
         boolean ☃xxxxxxxxxxxxxx = ☃xxxxxxxxx.has(☃xxxxx) || ☃xxxxxxxxx.has(☃xxxxxx);
         DataGenerator ☃xxxxxxxxxxxxxxx = func_200264_a(
            ☃xxxxxxxxxx,
            (Collection<Path>)☃xxxxxxxxx.valuesOf(☃xxxxxxxx).stream().map(var0x -> Paths.get(var0x)).collect(Collectors.toList()),
            ☃xxxxxxxxxxx,
            ☃xxxxxxxxxxxx,
            ☃xxxxxxxxxxxxx,
            ☃xxxxxxxxxxxxxx
         );
         ☃xxxxxxxxxxxxxxx.func_200392_c();
      } else {
         ☃.printHelpOn(System.out);
      }
   }

   public static DataGenerator func_200264_a(Path var0, Collection<Path> var1, boolean var2, boolean var3, boolean var4, boolean var5) {
      DataGenerator ☃ = new DataGenerator(☃, ☃);
      if (☃ || ☃) {
         ☃.func_200390_a(new SNBTToNBTConverter(☃));
      }

      if (☃) {
         ☃.func_200390_a(new FluidTagsProvider(☃));
         ☃.func_200390_a(new BlockTagsProvider(☃));
         ☃.func_200390_a(new ItemTagsProvider(☃));
         ☃.func_200390_a(new RecipeProvider(☃));
         ☃.func_200390_a(new AdvancementProvider(☃));
      }

      if (☃) {
         ☃.func_200390_a(new NBTToSNBTConverter(☃));
      }

      if (☃) {
         ☃.func_200390_a(new BlockListReport(☃));
         ☃.func_200390_a(new ItemListReport(☃));
         ☃.func_200390_a(new CommandsReport(☃));
      }

      return ☃;
   }
}
