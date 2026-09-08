package net.minecraft.client.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.VanillaPack;
import net.minecraft.util.ResourceLocation;

public class VirtualAssetsPack extends VanillaPack {
   private final ResourceIndex field_195785_b;

   public VirtualAssetsPack(ResourceIndex var1) {
      super("minecraft", "realms");
      this.field_195785_b = ☃;
   }

   @Nullable
   @Override
   protected InputStream func_195782_c(ResourcePackType var1, ResourceLocation var2) {
      if (☃ == ResourcePackType.CLIENT_RESOURCES) {
         File ☃ = this.field_195785_b.func_188547_a(☃);
         if (☃ != null && ☃.exists()) {
            try {
               return new FileInputStream(☃);
            } catch (FileNotFoundException var5) {
            }
         }
      }

      return super.func_195782_c(☃, ☃);
   }

   @Nullable
   @Override
   protected InputStream func_200010_a(String var1) {
      File ☃ = this.field_195785_b.func_200009_a(☃);
      if (☃ != null && ☃.exists()) {
         try {
            return new FileInputStream(☃);
         } catch (FileNotFoundException var4) {
         }
      }

      return super.func_200010_a(☃);
   }

   @Override
   public Collection<ResourceLocation> func_195758_a(ResourcePackType var1, String var2, int var3, Predicate<String> var4) {
      Collection<ResourceLocation> ☃ = super.func_195758_a(☃, ☃, ☃, ☃);
      ☃.addAll((Collection)this.field_195785_b.func_211685_a(☃, ☃, ☃).stream().map(ResourceLocation::new).collect(Collectors.toList()));
      return ☃;
   }
}
