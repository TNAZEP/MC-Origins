package net.minecraft.advancements.critereon;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockPredicate {
   public static final BlockPredicate ANY = new BlockPredicate(null, null, StatePropertiesPredicate.ANY, NbtPredicate.ANY);
   @Nullable
   private final Tag<Block> tag;
   @Nullable
   private final Set<Block> blocks;
   private final StatePropertiesPredicate properties;
   private final NbtPredicate nbt;

   public BlockPredicate(@Nullable Tag<Block> var1, @Nullable Set<Block> var2, StatePropertiesPredicate var3, NbtPredicate var4) {
      this.tag = â˜ƒ;
      this.blocks = â˜ƒ;
      this.properties = â˜ƒ;
      this.nbt = â˜ƒ;
   }

   public boolean matches(ServerLevel var1, BlockPos var2) {
      if (this == ANY) {
         return true;
      } else if (!â˜ƒ.isLoaded(â˜ƒ)) {
         return false;
      } else {
         BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
         if (this.tag != null && !â˜ƒ.is(this.tag)) {
            return false;
         } else if (this.blocks != null && !this.blocks.contains(â˜ƒ.getBlock())) {
            return false;
         } else if (!this.properties.matches(â˜ƒ)) {
            return false;
         } else {
            if (this.nbt != NbtPredicate.ANY) {
               BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
               if (â˜ƒ == null || !this.nbt.matches(â˜ƒ.save(new CompoundTag()))) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public static BlockPredicate fromJson(@Nullable JsonElement var0) {
      if (â˜ƒ != null && !â˜ƒ.isJsonNull()) {
         JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "block");
         NbtPredicate â˜ƒx = NbtPredicate.fromJson(â˜ƒ.get("nbt"));
         Set<Block> â˜ƒxx = null;
         JsonArray â˜ƒxxx = GsonHelper.getAsJsonArray(â˜ƒ, "blocks", null);
         if (â˜ƒxxx != null) {
            ImmutableSet.Builder<Block> â˜ƒxxxx = ImmutableSet.builder();

            for(JsonElement â˜ƒxxxxx : â˜ƒxxx) {
               ResourceLocation â˜ƒxxxxxx = new ResourceLocation(GsonHelper.convertToString(â˜ƒxxxxx, "block"));
               â˜ƒxxxx.add((Block)Registry.BLOCK.getOptional(â˜ƒxxxxxx).orElseThrow(() -> new JsonSyntaxException("Unknown block id '" + â˜ƒ + "'")));
            }

            â˜ƒxx = â˜ƒxxxx.build();
         }

         Tag<Block> â˜ƒ = null;
         if (â˜ƒ.has("tag")) {
            ResourceLocation â˜ƒx = new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "tag"));
            â˜ƒ = SerializationTags.getInstance()
               .getTagOrThrow(Registry.BLOCK_REGISTRY, â˜ƒx, var0x -> new JsonSyntaxException("Unknown block tag '" + var0x + "'"));
         }

         StatePropertiesPredicate â˜ƒ = StatePropertiesPredicate.fromJson(â˜ƒ.get("state"));
         return new BlockPredicate(â˜ƒ, â˜ƒxx, â˜ƒ, â˜ƒx);
      } else {
         return ANY;
      }
   }

   public JsonElement serializeToJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject â˜ƒ = new JsonObject();
         if (this.blocks != null) {
            JsonArray â˜ƒx = new JsonArray();

            for(Block â˜ƒxx : this.blocks) {
               â˜ƒx.add(Registry.BLOCK.getKey(â˜ƒxx).toString());
            }

            â˜ƒ.add("blocks", â˜ƒx);
         }

         if (this.tag != null) {
            â˜ƒ.addProperty(
               "tag",
               SerializationTags.getInstance().getIdOrThrow(Registry.BLOCK_REGISTRY, this.tag, () -> new IllegalStateException("Unknown block tag")).toString()
            );
         }

         â˜ƒ.add("nbt", this.nbt.serializeToJson());
         â˜ƒ.add("state", this.properties.serializeToJson());
         return â˜ƒ;
      }
   }

   public static class Builder {
      @Nullable
      private Set<Block> blocks;
      @Nullable
      private Tag<Block> tag;
      private StatePropertiesPredicate properties = StatePropertiesPredicate.ANY;
      private NbtPredicate nbt = NbtPredicate.ANY;

      private Builder() {
      }

      public static BlockPredicate.Builder block() {
         return new BlockPredicate.Builder();
      }

      public BlockPredicate.Builder of(Block... var1) {
         this.blocks = ImmutableSet.copyOf(â˜ƒ);
         return this;
      }

      public BlockPredicate.Builder of(Iterable<Block> var1) {
         this.blocks = ImmutableSet.copyOf(â˜ƒ);
         return this;
      }

      public BlockPredicate.Builder of(Tag<Block> var1) {
         this.tag = â˜ƒ;
         return this;
      }

      public BlockPredicate.Builder hasNbt(CompoundTag var1) {
         this.nbt = new NbtPredicate(â˜ƒ);
         return this;
      }

      public BlockPredicate.Builder setProperties(StatePropertiesPredicate var1) {
         this.properties = â˜ƒ;
         return this;
      }

      public BlockPredicate build() {
         return new BlockPredicate(this.tag, this.blocks, this.properties, this.nbt);
      }
   }
}
