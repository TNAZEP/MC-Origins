package net.minecraft.resources;

import java.util.Map;

public class ServerPackFinder implements IPackFinder {
   private final VanillaPack field_195738_a = new VanillaPack("minecraft");

   @Override
   public <T extends ResourcePackInfo> void func_195730_a(Map<String, T> var1, ResourcePackInfo.IFactory<T> var2) {
      T ☃ = ResourcePackInfo.func_195793_a("vanilla", false, () -> this.field_195738_a, ☃, ResourcePackInfo.Priority.BOTTOM);
      if (☃ != null) {
         ☃.put("vanilla", ☃);
      }
   }
}
