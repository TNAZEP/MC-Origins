package net.minecraft.advancements;

import java.util.Collection;

public interface RequirementsStrategy {
   RequirementsStrategy AND = var0 -> {
      String[][] â˜ƒ = new String[var0.size()][];
      int â˜ƒx = 0;

      for(String â˜ƒxx : var0) {
         â˜ƒ[â˜ƒx++] = new String[]{â˜ƒxx};
      }

      return â˜ƒ;
   };
   RequirementsStrategy OR = var0 -> new String[][]{(String[])var0.toArray(new String[0])};

   String[][] createRequirements(Collection<String> var1);
}
