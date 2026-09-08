package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Locale;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExplorationMapFunction extends LootItemConditionalFunction {
   static final Logger LOGGER = LogManager.getLogger();
   public static final StructureFeature<?> DEFAULT_FEATURE = StructureFeature.BURIED_TREASURE;
   public static final String DEFAULT_DECORATION_NAME = "mansion";
   public static final MapDecoration.Type DEFAULT_DECORATION = MapDecoration.Type.MANSION;
   public static final byte DEFAULT_ZOOM = 2;
   public static final int DEFAULT_SEARCH_RADIUS = 50;
   public static final boolean DEFAULT_SKIP_EXISTING = true;
   final StructureFeature<?> destination;
   final MapDecoration.Type mapDecoration;
   final byte zoom;
   final int searchRadius;
   final boolean skipKnownStructures;

   ExplorationMapFunction(LootItemCondition[] var1, StructureFeature<?> var2, MapDecoration.Type var3, byte var4, int var5, boolean var6) {
      super(â˜ƒ);
      this.destination = â˜ƒ;
      this.mapDecoration = â˜ƒ;
      this.zoom = â˜ƒ;
      this.searchRadius = â˜ƒ;
      this.skipKnownStructures = â˜ƒ;
   }

   @Override
   public LootItemFunctionType getType() {
      return LootItemFunctions.EXPLORATION_MAP;
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return ImmutableSet.of(LootContextParams.ORIGIN);
   }

   @Override
   public ItemStack run(ItemStack var1, LootContext var2) {
      if (!â˜ƒ.is(Items.MAP)) {
         return â˜ƒ;
      } else {
         Vec3 â˜ƒ = â˜ƒ.getParamOrNull(LootContextParams.ORIGIN);
         if (â˜ƒ != null) {
            ServerLevel â˜ƒx = â˜ƒ.getLevel();
            BlockPos â˜ƒxx = â˜ƒx.findNearestMapFeature(this.destination, new BlockPos(â˜ƒ), this.searchRadius, this.skipKnownStructures);
            if (â˜ƒxx != null) {
               ItemStack â˜ƒxxx = MapItem.create(â˜ƒx, â˜ƒxx.getX(), â˜ƒxx.getZ(), this.zoom, true, true);
               MapItem.renderBiomePreviewMap(â˜ƒx, â˜ƒxxx);
               MapItemSavedData.addTargetDecoration(â˜ƒxxx, â˜ƒxx, "+", this.mapDecoration);
               â˜ƒxxx.setHoverName(new TranslatableComponent("filled_map." + this.destination.getFeatureName().toLowerCase(Locale.ROOT)));
               return â˜ƒxxx;
            }
         }

         return â˜ƒ;
      }
   }

   public static ExplorationMapFunction.Builder makeExplorationMap() {
      return new ExplorationMapFunction.Builder();
   }

   public static class Builder extends LootItemConditionalFunction.Builder<ExplorationMapFunction.Builder> {
      private StructureFeature<?> destination = ExplorationMapFunction.DEFAULT_FEATURE;
      private MapDecoration.Type mapDecoration = ExplorationMapFunction.DEFAULT_DECORATION;
      private byte zoom = 2;
      private int searchRadius = 50;
      private boolean skipKnownStructures = true;

      protected ExplorationMapFunction.Builder getThis() {
         return this;
      }

      public ExplorationMapFunction.Builder setDestination(StructureFeature<?> var1) {
         this.destination = â˜ƒ;
         return this;
      }

      public ExplorationMapFunction.Builder setMapDecoration(MapDecoration.Type var1) {
         this.mapDecoration = â˜ƒ;
         return this;
      }

      public ExplorationMapFunction.Builder setZoom(byte var1) {
         this.zoom = â˜ƒ;
         return this;
      }

      public ExplorationMapFunction.Builder setSearchRadius(int var1) {
         this.searchRadius = â˜ƒ;
         return this;
      }

      public ExplorationMapFunction.Builder setSkipKnownStructures(boolean var1) {
         this.skipKnownStructures = â˜ƒ;
         return this;
      }

      @Override
      public LootItemFunction build() {
         return new ExplorationMapFunction(this.getConditions(), this.destination, this.mapDecoration, this.zoom, this.searchRadius, this.skipKnownStructures);
      }
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<ExplorationMapFunction> {
      public void serialize(JsonObject var1, ExplorationMapFunction var2, JsonSerializationContext var3) {
         super.serialize(â˜ƒ, â˜ƒ, â˜ƒ);
         if (!â˜ƒ.destination.equals(ExplorationMapFunction.DEFAULT_FEATURE)) {
            â˜ƒ.add("destination", â˜ƒ.serialize(â˜ƒ.destination.getFeatureName()));
         }

         if (â˜ƒ.mapDecoration != ExplorationMapFunction.DEFAULT_DECORATION) {
            â˜ƒ.add("decoration", â˜ƒ.serialize(â˜ƒ.mapDecoration.toString().toLowerCase(Locale.ROOT)));
         }

         if (â˜ƒ.zoom != 2) {
            â˜ƒ.addProperty("zoom", â˜ƒ.zoom);
         }

         if (â˜ƒ.searchRadius != 50) {
            â˜ƒ.addProperty("search_radius", â˜ƒ.searchRadius);
         }

         if (!â˜ƒ.skipKnownStructures) {
            â˜ƒ.addProperty("skip_existing_chunks", â˜ƒ.skipKnownStructures);
         }
      }

      public ExplorationMapFunction deserialize(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3) {
         StructureFeature<?> â˜ƒ = readStructure(â˜ƒ);
         String â˜ƒx = â˜ƒ.has("decoration") ? GsonHelper.getAsString(â˜ƒ, "decoration") : "mansion";
         MapDecoration.Type â˜ƒxx = ExplorationMapFunction.DEFAULT_DECORATION;

         try {
            â˜ƒxx = MapDecoration.Type.valueOf(â˜ƒx.toUpperCase(Locale.ROOT));
         } catch (IllegalArgumentException var10) {
            ExplorationMapFunction.LOGGER
               .error("Error while parsing loot table decoration entry. Found {}. Defaulting to {}", â˜ƒx, ExplorationMapFunction.DEFAULT_DECORATION);
         }

         byte â˜ƒxxx = GsonHelper.getAsByte(â˜ƒ, "zoom", (byte)2);
         int â˜ƒxxxx = GsonHelper.getAsInt(â˜ƒ, "search_radius", 50);
         boolean â˜ƒxxxxx = GsonHelper.getAsBoolean(â˜ƒ, "skip_existing_chunks", true);
         return new ExplorationMapFunction(â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
      }

      private static StructureFeature<?> readStructure(JsonObject var0) {
         if (â˜ƒ.has("destination")) {
            String â˜ƒ = GsonHelper.getAsString(â˜ƒ, "destination");
            StructureFeature<?> â˜ƒx = (StructureFeature)StructureFeature.STRUCTURES_REGISTRY.get(â˜ƒ.toLowerCase(Locale.ROOT));
            if (â˜ƒx != null) {
               return â˜ƒx;
            }
         }

         return ExplorationMapFunction.DEFAULT_FEATURE;
      }
   }
}
