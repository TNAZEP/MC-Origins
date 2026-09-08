package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class PlacedBlockTrigger extends SimpleCriterionTrigger<PlacedBlockTrigger.TriggerInstance> {
   static final ResourceLocation ID = new ResourceLocation("placed_block");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   public PlacedBlockTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      Block â˜ƒ = deserializeBlock(â˜ƒ);
      StatePropertiesPredicate â˜ƒx = StatePropertiesPredicate.fromJson(â˜ƒ.get("state"));
      if (â˜ƒ != null) {
         â˜ƒx.checkState(â˜ƒ.getStateDefinition(), var1x -> {
            throw new JsonSyntaxException("Block " + â˜ƒ + " has no property " + var1x + ":");
         });
      }

      LocationPredicate â˜ƒ = LocationPredicate.fromJson(â˜ƒ.get("location"));
      ItemPredicate â˜ƒx = ItemPredicate.fromJson(â˜ƒ.get("item"));
      return new PlacedBlockTrigger.TriggerInstance(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒx);
   }

   @Nullable
   private static Block deserializeBlock(JsonObject var0) {
      if (â˜ƒ.has("block")) {
         ResourceLocation â˜ƒ = new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "block"));
         return (Block)Registry.BLOCK.getOptional(â˜ƒ).orElseThrow(() -> new JsonSyntaxException("Unknown block type '" + â˜ƒ + "'"));
      } else {
         return null;
      }
   }

   public void trigger(ServerPlayer var1, BlockPos var2, ItemStack var3) {
      BlockState â˜ƒ = â˜ƒ.getLevel().getBlockState(â˜ƒ);
      this.trigger(â˜ƒ, var4x -> var4x.matches(â˜ƒ, â˜ƒ, â˜ƒ.getLevel(), â˜ƒ));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final Block block;
      private final StatePropertiesPredicate state;
      private final LocationPredicate location;
      private final ItemPredicate item;

      public TriggerInstance(EntityPredicate.Composite var1, @Nullable Block var2, StatePropertiesPredicate var3, LocationPredicate var4, ItemPredicate var5) {
         super(PlacedBlockTrigger.ID, â˜ƒ);
         this.block = â˜ƒ;
         this.state = â˜ƒ;
         this.location = â˜ƒ;
         this.item = â˜ƒ;
      }

      public static PlacedBlockTrigger.TriggerInstance placedBlock(Block var0) {
         return new PlacedBlockTrigger.TriggerInstance(
            EntityPredicate.Composite.ANY, â˜ƒ, StatePropertiesPredicate.ANY, LocationPredicate.ANY, ItemPredicate.ANY
         );
      }

      public boolean matches(BlockState var1, BlockPos var2, ServerLevel var3, ItemStack var4) {
         if (this.block != null && !â˜ƒ.is(this.block)) {
            return false;
         } else if (!this.state.matches(â˜ƒ)) {
            return false;
         } else if (!this.location.matches(â˜ƒ, (double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ())) {
            return false;
         } else {
            return this.item.matches(â˜ƒ);
         }
      }

      @Override
      public JsonObject serializeToJson(SerializationContext var1) {
         JsonObject â˜ƒ = super.serializeToJson(â˜ƒ);
         if (this.block != null) {
            â˜ƒ.addProperty("block", Registry.BLOCK.getKey(this.block).toString());
         }

         â˜ƒ.add("state", this.state.serializeToJson());
         â˜ƒ.add("location", this.location.serializeToJson());
         â˜ƒ.add("item", this.item.serializeToJson());
         return â˜ƒ;
      }
   }
}
