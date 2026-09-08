package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LocationPredicate {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final LocationPredicate ANY = new LocationPredicate(
      MinMaxBounds.Doubles.ANY,
      MinMaxBounds.Doubles.ANY,
      MinMaxBounds.Doubles.ANY,
      null,
      null,
      null,
      null,
      LightPredicate.ANY,
      BlockPredicate.ANY,
      FluidPredicate.ANY
   );
   private final MinMaxBounds.Doubles x;
   private final MinMaxBounds.Doubles y;
   private final MinMaxBounds.Doubles z;
   @Nullable
   private final ResourceKey<Biome> biome;
   @Nullable
   private final StructureFeature<?> feature;
   @Nullable
   private final ResourceKey<Level> dimension;
   @Nullable
   private final Boolean smokey;
   private final LightPredicate light;
   private final BlockPredicate block;
   private final FluidPredicate fluid;

   public LocationPredicate(
      MinMaxBounds.Doubles var1,
      MinMaxBounds.Doubles var2,
      MinMaxBounds.Doubles var3,
      @Nullable ResourceKey<Biome> var4,
      @Nullable StructureFeature<?> var5,
      @Nullable ResourceKey<Level> var6,
      @Nullable Boolean var7,
      LightPredicate var8,
      BlockPredicate var9,
      FluidPredicate var10
   ) {
      this.x = â˜ƒ;
      this.y = â˜ƒ;
      this.z = â˜ƒ;
      this.biome = â˜ƒ;
      this.feature = â˜ƒ;
      this.dimension = â˜ƒ;
      this.smokey = â˜ƒ;
      this.light = â˜ƒ;
      this.block = â˜ƒ;
      this.fluid = â˜ƒ;
   }

   public static LocationPredicate inBiome(ResourceKey<Biome> var0) {
      return new LocationPredicate(
         MinMaxBounds.Doubles.ANY,
         MinMaxBounds.Doubles.ANY,
         MinMaxBounds.Doubles.ANY,
         â˜ƒ,
         null,
         null,
         null,
         LightPredicate.ANY,
         BlockPredicate.ANY,
         FluidPredicate.ANY
      );
   }

   public static LocationPredicate inDimension(ResourceKey<Level> var0) {
      return new LocationPredicate(
         MinMaxBounds.Doubles.ANY,
         MinMaxBounds.Doubles.ANY,
         MinMaxBounds.Doubles.ANY,
         null,
         null,
         â˜ƒ,
         null,
         LightPredicate.ANY,
         BlockPredicate.ANY,
         FluidPredicate.ANY
      );
   }

   public static LocationPredicate inFeature(StructureFeature<?> var0) {
      return new LocationPredicate(
         MinMaxBounds.Doubles.ANY,
         MinMaxBounds.Doubles.ANY,
         MinMaxBounds.Doubles.ANY,
         null,
         â˜ƒ,
         null,
         null,
         LightPredicate.ANY,
         BlockPredicate.ANY,
         FluidPredicate.ANY
      );
   }

   public boolean matches(ServerLevel var1, double var2, double var4, double var6) {
      if (!this.x.matches(â˜ƒ)) {
         return false;
      } else if (!this.y.matches(â˜ƒ)) {
         return false;
      } else if (!this.z.matches(â˜ƒ)) {
         return false;
      } else if (this.dimension != null && this.dimension != â˜ƒ.dimension()) {
         return false;
      } else {
         BlockPos â˜ƒ = new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ);
         boolean â˜ƒx = â˜ƒ.isLoaded(â˜ƒ);
         Optional<ResourceKey<Biome>> â˜ƒxx = â˜ƒ.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getResourceKey(â˜ƒ.getBiome(â˜ƒ));
         if (!â˜ƒxx.isPresent()) {
            return false;
         } else if (this.biome == null || â˜ƒx && this.biome == â˜ƒxx.get()) {
            if (this.feature == null || â˜ƒx && â˜ƒ.structureFeatureManager().getStructureAt(â˜ƒ, true, this.feature).isValid()) {
               if (this.smokey == null || â˜ƒx && this.smokey == CampfireBlock.isSmokeyPos(â˜ƒ, â˜ƒ)) {
                  if (!this.light.matches(â˜ƒ, â˜ƒ)) {
                     return false;
                  } else if (!this.block.matches(â˜ƒ, â˜ƒ)) {
                     return false;
                  } else {
                     return this.fluid.matches(â˜ƒ, â˜ƒ);
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   public JsonElement serializeToJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject â˜ƒ = new JsonObject();
         if (!this.x.isAny() || !this.y.isAny() || !this.z.isAny()) {
            JsonObject â˜ƒx = new JsonObject();
            â˜ƒx.add("x", this.x.serializeToJson());
            â˜ƒx.add("y", this.y.serializeToJson());
            â˜ƒx.add("z", this.z.serializeToJson());
            â˜ƒ.add("position", â˜ƒx);
         }

         if (this.dimension != null) {
            Level.RESOURCE_KEY_CODEC
               .encodeStart(JsonOps.INSTANCE, this.dimension)
               .resultOrPartial(LOGGER::error)
               .ifPresent(var1x -> â˜ƒ.add("dimension", var1x));
         }

         if (this.feature != null) {
            â˜ƒ.addProperty("feature", this.feature.getFeatureName());
         }

         if (this.biome != null) {
            â˜ƒ.addProperty("biome", this.biome.location().toString());
         }

         if (this.smokey != null) {
            â˜ƒ.addProperty("smokey", this.smokey);
         }

         â˜ƒ.add("light", this.light.serializeToJson());
         â˜ƒ.add("block", this.block.serializeToJson());
         â˜ƒ.add("fluid", this.fluid.serializeToJson());
         return â˜ƒ;
      }
   }

   public static LocationPredicate fromJson(@Nullable JsonElement var0) {
      if (â˜ƒ != null && !â˜ƒ.isJsonNull()) {
         JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "location");
         JsonObject â˜ƒx = GsonHelper.getAsJsonObject(â˜ƒ, "position", new JsonObject());
         MinMaxBounds.Doubles â˜ƒxx = MinMaxBounds.Doubles.fromJson(â˜ƒx.get("x"));
         MinMaxBounds.Doubles â˜ƒxxx = MinMaxBounds.Doubles.fromJson(â˜ƒx.get("y"));
         MinMaxBounds.Doubles â˜ƒxxxx = MinMaxBounds.Doubles.fromJson(â˜ƒx.get("z"));
         ResourceKey<Level> â˜ƒxxxxx = â˜ƒ.has("dimension")
            ? (ResourceKey)ResourceLocation.CODEC
               .parse(JsonOps.INSTANCE, â˜ƒ.get("dimension"))
               .resultOrPartial(LOGGER::error)
               .map(var0x -> ResourceKey.create(Registry.DIMENSION_REGISTRY, var0x))
               .orElse(null)
            : null;
         StructureFeature<?> â˜ƒxxxxxx = â˜ƒ.has("feature")
            ? (StructureFeature)StructureFeature.STRUCTURES_REGISTRY.get(GsonHelper.getAsString(â˜ƒ, "feature"))
            : null;
         ResourceKey<Biome> â˜ƒxxxxxxx = null;
         if (â˜ƒ.has("biome")) {
            ResourceLocation â˜ƒxxxxxxxx = new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "biome"));
            â˜ƒxxxxxxx = ResourceKey.create(Registry.BIOME_REGISTRY, â˜ƒxxxxxxxx);
         }

         Boolean â˜ƒ = â˜ƒ.has("smokey") ? â˜ƒ.get("smokey").getAsBoolean() : null;
         LightPredicate â˜ƒx = LightPredicate.fromJson(â˜ƒ.get("light"));
         BlockPredicate â˜ƒxx = BlockPredicate.fromJson(â˜ƒ.get("block"));
         FluidPredicate â˜ƒxxx = FluidPredicate.fromJson(â˜ƒ.get("fluid"));
         return new LocationPredicate(â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxx, â˜ƒxxxxx, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx);
      } else {
         return ANY;
      }
   }

   public static class Builder {
      private MinMaxBounds.Doubles x = MinMaxBounds.Doubles.ANY;
      private MinMaxBounds.Doubles y = MinMaxBounds.Doubles.ANY;
      private MinMaxBounds.Doubles z = MinMaxBounds.Doubles.ANY;
      @Nullable
      private ResourceKey<Biome> biome;
      @Nullable
      private StructureFeature<?> feature;
      @Nullable
      private ResourceKey<Level> dimension;
      @Nullable
      private Boolean smokey;
      private LightPredicate light = LightPredicate.ANY;
      private BlockPredicate block = BlockPredicate.ANY;
      private FluidPredicate fluid = FluidPredicate.ANY;

      public static LocationPredicate.Builder location() {
         return new LocationPredicate.Builder();
      }

      public LocationPredicate.Builder setX(MinMaxBounds.Doubles var1) {
         this.x = â˜ƒ;
         return this;
      }

      public LocationPredicate.Builder setY(MinMaxBounds.Doubles var1) {
         this.y = â˜ƒ;
         return this;
      }

      public LocationPredicate.Builder setZ(MinMaxBounds.Doubles var1) {
         this.z = â˜ƒ;
         return this;
      }

      public LocationPredicate.Builder setBiome(@Nullable ResourceKey<Biome> var1) {
         this.biome = â˜ƒ;
         return this;
      }

      public LocationPredicate.Builder setFeature(@Nullable StructureFeature<?> var1) {
         this.feature = â˜ƒ;
         return this;
      }

      public LocationPredicate.Builder setDimension(@Nullable ResourceKey<Level> var1) {
         this.dimension = â˜ƒ;
         return this;
      }

      public LocationPredicate.Builder setLight(LightPredicate var1) {
         this.light = â˜ƒ;
         return this;
      }

      public LocationPredicate.Builder setBlock(BlockPredicate var1) {
         this.block = â˜ƒ;
         return this;
      }

      public LocationPredicate.Builder setFluid(FluidPredicate var1) {
         this.fluid = â˜ƒ;
         return this;
      }

      public LocationPredicate.Builder setSmokey(Boolean var1) {
         this.smokey = â˜ƒ;
         return this;
      }

      public LocationPredicate build() {
         return new LocationPredicate(this.x, this.y, this.z, this.biome, this.feature, this.dimension, this.smokey, this.light, this.block, this.fluid);
      }
   }
}
