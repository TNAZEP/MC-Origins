package net.minecraft.world.storage.loot;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootTableManager implements IResourceManagerReloadListener {
   private static final Logger field_186525_a = LogManager.getLogger();
   private static final Gson field_186526_b = new GsonBuilder()
      .registerTypeAdapter(RandomValueRange.class, new RandomValueRange.Serializer())
      .registerTypeAdapter(LootPool.class, new LootPool.Serializer())
      .registerTypeAdapter(LootTable.class, new LootTable.Serializer())
      .registerTypeHierarchyAdapter(LootEntry.class, new LootEntry.Serializer())
      .registerTypeHierarchyAdapter(LootFunction.class, new LootFunctionManager.Serializer())
      .registerTypeHierarchyAdapter(LootCondition.class, new LootConditionManager.Serializer())
      .registerTypeHierarchyAdapter(LootContext.EntityTarget.class, new LootContext.EntityTarget.Serializer())
      .create();
   private final Map<ResourceLocation, LootTable> field_186527_c = Maps.<ResourceLocation, LootTable>newHashMap();
   public static final int field_195435_a = "loot_tables/".length();
   public static final int field_195436_b = ".json".length();

   public LootTable func_186521_a(ResourceLocation var1) {
      return (LootTable)this.field_186527_c.getOrDefault(☃, LootTable.field_186464_a);
   }

   @Override
   public void func_195410_a(IResourceManager var1) {
      this.field_186527_c.clear();

      for(ResourceLocation ☃ : ☃.func_199003_a("loot_tables", var0 -> var0.endsWith(".json"))) {
         String ☃x = ☃.func_110623_a();
         ResourceLocation ☃xx = new ResourceLocation(☃.func_110624_b(), ☃x.substring(field_195435_a, ☃x.length() - field_195436_b));

         try {
            IResource ☃xxx = ☃.func_199002_a(☃);
            Throwable var7 = null;

            try {
               LootTable ☃xxxx = JsonUtils.func_188178_a(field_186526_b, IOUtils.toString(☃xxx.func_199027_b(), StandardCharsets.UTF_8), LootTable.class);
               if (☃xxxx != null) {
                  this.field_186527_c.put(☃xx, ☃xxxx);
               }
            } catch (Throwable var17) {
               var7 = var17;
               throw var17;
            } finally {
               if (☃xxx != null) {
                  if (var7 != null) {
                     try {
                        ☃xxx.close();
                     } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                     }
                  } else {
                     ☃xxx.close();
                  }
               }
            }
         } catch (Throwable var19) {
            field_186525_a.error("Couldn't read loot table {} from {}", ☃xx, ☃, var19);
         }
      }
   }
}
