package net.minecraft.advancements;

import java.util.Collection;

public interface RequirementsStrategy {
   RequirementsStrategy AND = var0 -> {
      String[][] ☃ = new String[var0.size()][];
      int ☃x = 0;

      for(String ☃xx : var0) {
         ☃[☃x++] = new String[]{☃xx};
      }

      return ☃;
   };
   RequirementsStrategy OR = var0 -> new String[][]{(String[])var0.toArray(new String[0])};

   String[][] createRequirements(Collection<String> var1);
}
