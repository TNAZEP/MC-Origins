package net.minecraft.world.level.storage.loot.providers.nbt;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;

public class StorageNbtProvider implements NbtProvider {
   final ResourceLocation id;

   StorageNbtProvider(ResourceLocation var1) {
      this.id = â˜ƒ;
   }

   @Override
   public LootNbtProviderType getType() {
      return NbtProviders.STORAGE;
   }

   @Nullable
   @Override
   public Tag get(LootContext var1) {
      return â˜ƒ.getLevel().getServer().getCommandStorage().get(this.id);
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return ImmutableSet.of();
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<StorageNbtProvider> {
      public void serialize(JsonObject var1, StorageNbtProvider var2, JsonSerializationContext var3) {
         â˜ƒ.addProperty("source", â˜ƒ.id.toString());
      }

      public StorageNbtProvider deserialize(JsonObject var1, JsonDeserializationContext var2) {
         String â˜ƒ = GsonHelper.getAsString(â˜ƒ, "source");
         return new StorageNbtProvider(new ResourceLocation(â˜ƒ));
      }
   }
}
