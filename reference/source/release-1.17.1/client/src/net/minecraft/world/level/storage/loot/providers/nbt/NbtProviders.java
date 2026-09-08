package net.minecraft.world.level.storage.loot.providers.nbt;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.GsonAdapterFactory;
import net.minecraft.world.level.storage.loot.Serializer;

public class NbtProviders {
   public static final LootNbtProviderType STORAGE = register("storage", new StorageNbtProvider.Serializer());
   public static final LootNbtProviderType CONTEXT = register("context", new ContextNbtProvider.Serializer());

   private static LootNbtProviderType register(String var0, Serializer<? extends NbtProvider> var1) {
      return Registry.register(Registry.LOOT_NBT_PROVIDER_TYPE, new ResourceLocation(â˜ƒ), new LootNbtProviderType(â˜ƒ));
   }

   public static Object createGsonAdapter() {
      return GsonAdapterFactory.<NbtProvider, LootNbtProviderType>builder(Registry.LOOT_NBT_PROVIDER_TYPE, "provider", "type", NbtProvider::getType)
         .withInlineSerializer(CONTEXT, new ContextNbtProvider.InlineSerializer())
         .build();
   }
}
