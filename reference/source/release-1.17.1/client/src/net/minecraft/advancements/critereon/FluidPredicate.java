package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

public class FluidPredicate {
   public static final FluidPredicate ANY = new FluidPredicate(null, null, StatePropertiesPredicate.ANY);
   @Nullable
   private final Tag<Fluid> tag;
   @Nullable
   private final Fluid fluid;
   private final StatePropertiesPredicate properties;

   public FluidPredicate(@Nullable Tag<Fluid> var1, @Nullable Fluid var2, StatePropertiesPredicate var3) {
      this.tag = â˜ƒ;
      this.fluid = â˜ƒ;
      this.properties = â˜ƒ;
   }

   public boolean matches(ServerLevel var1, BlockPos var2) {
      if (this == ANY) {
         return true;
      } else if (!â˜ƒ.isLoaded(â˜ƒ)) {
         return false;
      } else {
         FluidState â˜ƒ = â˜ƒ.getFluidState(â˜ƒ);
         Fluid â˜ƒx = â˜ƒ.getType();
         if (this.tag != null && !â˜ƒx.is(this.tag)) {
            return false;
         } else if (this.fluid != null && â˜ƒx != this.fluid) {
            return false;
         } else {
            return this.properties.matches(â˜ƒ);
         }
      }
   }

   public static FluidPredicate fromJson(@Nullable JsonElement var0) {
      if (â˜ƒ != null && !â˜ƒ.isJsonNull()) {
         JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "fluid");
         Fluid â˜ƒx = null;
         if (â˜ƒ.has("fluid")) {
            ResourceLocation â˜ƒxx = new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "fluid"));
            â˜ƒx = Registry.FLUID.get(â˜ƒxx);
         }

         Tag<Fluid> â˜ƒ = null;
         if (â˜ƒ.has("tag")) {
            ResourceLocation â˜ƒx = new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "tag"));
            â˜ƒ = SerializationTags.getInstance()
               .getTagOrThrow(Registry.FLUID_REGISTRY, â˜ƒx, var0x -> new JsonSyntaxException("Unknown fluid tag '" + var0x + "'"));
         }

         StatePropertiesPredicate â˜ƒ = StatePropertiesPredicate.fromJson(â˜ƒ.get("state"));
         return new FluidPredicate(â˜ƒ, â˜ƒx, â˜ƒ);
      } else {
         return ANY;
      }
   }

   public JsonElement serializeToJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject â˜ƒ = new JsonObject();
         if (this.fluid != null) {
            â˜ƒ.addProperty("fluid", Registry.FLUID.getKey(this.fluid).toString());
         }

         if (this.tag != null) {
            â˜ƒ.addProperty(
               "tag",
               SerializationTags.getInstance().getIdOrThrow(Registry.FLUID_REGISTRY, this.tag, () -> new IllegalStateException("Unknown fluid tag")).toString()
            );
         }

         â˜ƒ.add("state", this.properties.serializeToJson());
         return â˜ƒ;
      }
   }

   public static class Builder {
      @Nullable
      private Fluid fluid;
      @Nullable
      private Tag<Fluid> fluids;
      private StatePropertiesPredicate properties = StatePropertiesPredicate.ANY;

      private Builder() {
      }

      public static FluidPredicate.Builder fluid() {
         return new FluidPredicate.Builder();
      }

      public FluidPredicate.Builder of(Fluid var1) {
         this.fluid = â˜ƒ;
         return this;
      }

      public FluidPredicate.Builder of(Tag<Fluid> var1) {
         this.fluids = â˜ƒ;
         return this;
      }

      public FluidPredicate.Builder setProperties(StatePropertiesPredicate var1) {
         this.properties = â˜ƒ;
         return this;
      }

      public FluidPredicate build() {
         return new FluidPredicate(this.fluids, this.fluid, this.properties);
      }
   }
}
