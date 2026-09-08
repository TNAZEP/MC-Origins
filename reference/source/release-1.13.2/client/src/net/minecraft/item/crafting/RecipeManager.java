package net.minecraft.item.crafting;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RecipeManager implements IResourceManagerReloadListener {
   private static final Logger field_199521_c = LogManager.getLogger();
   public static final int field_199519_a = "recipes/".length();
   public static final int field_199520_b = ".json".length();
   private final Map<ResourceLocation, IRecipe> field_199522_d = Maps.<ResourceLocation, IRecipe>newHashMap();
   private boolean field_199523_e;

   @Override
   public void func_195410_a(IResourceManager var1) {
      Gson ☃ = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
      this.field_199523_e = false;
      this.field_199522_d.clear();

      for(ResourceLocation ☃x : ☃.func_199003_a("recipes", var0 -> var0.endsWith(".json"))) {
         String ☃xx = ☃x.func_110623_a();
         ResourceLocation ☃xxx = new ResourceLocation(☃x.func_110624_b(), ☃xx.substring(field_199519_a, ☃xx.length() - field_199520_b));

         try {
            IResource ☃xxxx = ☃.func_199002_a(☃x);
            Throwable var8 = null;

            try {
               JsonObject ☃xxxxx = JsonUtils.func_188178_a(☃, IOUtils.toString(☃xxxx.func_199027_b(), StandardCharsets.UTF_8), JsonObject.class);
               if (☃xxxxx == null) {
                  field_199521_c.error("Couldn't load recipe {} as it's null or empty", ☃xxx);
               } else {
                  this.func_199509_a(RecipeSerializers.func_199572_a(☃xxx, ☃xxxxx));
               }
            } catch (Throwable var19) {
               var8 = var19;
               throw var19;
            } finally {
               if (☃xxxx != null) {
                  if (var8 != null) {
                     try {
                        ☃xxxx.close();
                     } catch (Throwable var18) {
                        var8.addSuppressed(var18);
                     }
                  } else {
                     ☃xxxx.close();
                  }
               }
            }
         } catch (IllegalArgumentException | JsonParseException var21) {
            field_199521_c.error("Parsing error loading recipe {}", ☃xxx, var21);
            this.field_199523_e = true;
         } catch (IOException var22) {
            field_199521_c.error("Couldn't read custom advancement {} from {}", ☃xxx, ☃x, var22);
            this.field_199523_e = true;
         }
      }

      field_199521_c.info("Loaded {} recipes", this.field_199522_d.size());
   }

   public void func_199509_a(IRecipe var1) {
      if (this.field_199522_d.containsKey(☃.func_199560_c())) {
         throw new IllegalStateException("Duplicate recipe ignored with ID " + ☃.func_199560_c());
      } else {
         this.field_199522_d.put(☃.func_199560_c(), ☃);
      }
   }

   public ItemStack func_199514_a(IInventory var1, World var2) {
      for(IRecipe ☃ : this.field_199522_d.values()) {
         if (☃.func_77569_a(☃, ☃)) {
            return ☃.func_77572_b(☃);
         }
      }

      return ItemStack.field_190927_a;
   }

   @Nullable
   public IRecipe func_199515_b(IInventory var1, World var2) {
      for(IRecipe ☃ : this.field_199522_d.values()) {
         if (☃.func_77569_a(☃, ☃)) {
            return ☃;
         }
      }

      return null;
   }

   public NonNullList<ItemStack> func_199513_c(IInventory var1, World var2) {
      for(IRecipe ☃ : this.field_199522_d.values()) {
         if (☃.func_77569_a(☃, ☃)) {
            return ☃.func_179532_b(☃);
         }
      }

      NonNullList<ItemStack> ☃ = NonNullList.func_191197_a(☃.func_70302_i_(), ItemStack.field_190927_a);

      for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
         ☃.set(☃x, ☃.func_70301_a(☃x));
      }

      return ☃;
   }

   @Nullable
   public IRecipe func_199517_a(ResourceLocation var1) {
      return (IRecipe)this.field_199522_d.get(☃);
   }

   public Collection<IRecipe> func_199510_b() {
      return this.field_199522_d.values();
   }

   public Collection<ResourceLocation> func_199511_c() {
      return this.field_199522_d.keySet();
   }

   public void func_199518_d() {
      this.field_199522_d.clear();
   }
}
