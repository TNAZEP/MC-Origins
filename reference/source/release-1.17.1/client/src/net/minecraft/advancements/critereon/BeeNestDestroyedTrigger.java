package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BeeNestDestroyedTrigger extends SimpleCriterionTrigger<BeeNestDestroyedTrigger.TriggerInstance> {
   static final ResourceLocation ID = new ResourceLocation("bee_nest_destroyed");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   public BeeNestDestroyedTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      Block â˜ƒ = deserializeBlock(â˜ƒ);
      ItemPredicate â˜ƒx = ItemPredicate.fromJson(â˜ƒ.get("item"));
      MinMaxBounds.Ints â˜ƒxx = MinMaxBounds.Ints.fromJson(â˜ƒ.get("num_bees_inside"));
      return new BeeNestDestroyedTrigger.TriggerInstance(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx);
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

   public void trigger(ServerPlayer var1, BlockState var2, ItemStack var3, int var4) {
      this.trigger(â˜ƒ, var3x -> var3x.matches(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      @Nullable
      private final Block block;
      private final ItemPredicate item;
      private final MinMaxBounds.Ints numBees;

      public TriggerInstance(EntityPredicate.Composite var1, @Nullable Block var2, ItemPredicate var3, MinMaxBounds.Ints var4) {
         super(BeeNestDestroyedTrigger.ID, â˜ƒ);
         this.block = â˜ƒ;
         this.item = â˜ƒ;
         this.numBees = â˜ƒ;
      }

      public static BeeNestDestroyedTrigger.TriggerInstance destroyedBeeNest(Block var0, ItemPredicate.Builder var1, MinMaxBounds.Ints var2) {
         return new BeeNestDestroyedTrigger.TriggerInstance(EntityPredicate.Composite.ANY, â˜ƒ, â˜ƒ.build(), â˜ƒ);
      }

      public boolean matches(BlockState var1, ItemStack var2, int var3) {
         if (this.block != null && !â˜ƒ.is(this.block)) {
            return false;
         } else {
            return !this.item.matches(â˜ƒ) ? false : this.numBees.matches(â˜ƒ);
         }
      }

      @Override
      public JsonObject serializeToJson(SerializationContext var1) {
         JsonObject â˜ƒ = super.serializeToJson(â˜ƒ);
         if (this.block != null) {
            â˜ƒ.addProperty("block", Registry.BLOCK.getKey(this.block).toString());
         }

         â˜ƒ.add("item", this.item.serializeToJson());
         â˜ƒ.add("num_bees_inside", this.numBees.serializeToJson());
         return â˜ƒ;
      }
   }
}
