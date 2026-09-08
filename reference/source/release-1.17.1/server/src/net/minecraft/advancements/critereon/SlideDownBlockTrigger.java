package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class SlideDownBlockTrigger extends SimpleCriterionTrigger<SlideDownBlockTrigger.TriggerInstance> {
   static final ResourceLocation ID = new ResourceLocation("slide_down_block");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   public SlideDownBlockTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      Block â˜ƒ = deserializeBlock(â˜ƒ);
      StatePropertiesPredicate â˜ƒx = StatePropertiesPredicate.fromJson(â˜ƒ.get("state"));
      if (â˜ƒ != null) {
         â˜ƒx.checkState(â˜ƒ.getStateDefinition(), var1x -> {
            throw new JsonSyntaxException("Block " + â˜ƒ + " has no property " + var1x);
         });
      }

      return new SlideDownBlockTrigger.TriggerInstance(â˜ƒ, â˜ƒ, â˜ƒx);
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

   public void trigger(ServerPlayer var1, BlockState var2) {
      this.trigger(â˜ƒ, var1x -> var1x.matches(â˜ƒ));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final Block block;
      private final StatePropertiesPredicate state;

      public TriggerInstance(EntityPredicate.Composite var1, @Nullable Block var2, StatePropertiesPredicate var3) {
         super(SlideDownBlockTrigger.ID, â˜ƒ);
         this.block = â˜ƒ;
         this.state = â˜ƒ;
      }

      public static SlideDownBlockTrigger.TriggerInstance slidesDownBlock(Block var0) {
         return new SlideDownBlockTrigger.TriggerInstance(EntityPredicate.Composite.ANY, â˜ƒ, StatePropertiesPredicate.ANY);
      }

      @Override
      public JsonObject serializeToJson(SerializationContext var1) {
         JsonObject â˜ƒ = super.serializeToJson(â˜ƒ);
         if (this.block != null) {
            â˜ƒ.addProperty("block", Registry.BLOCK.getKey(this.block).toString());
         }

         â˜ƒ.add("state", this.state.serializeToJson());
         return â˜ƒ;
      }

      public boolean matches(BlockState var1) {
         if (this.block != null && !â˜ƒ.is(this.block)) {
            return false;
         } else {
            return this.state.matches(â˜ƒ);
         }
      }
   }
}
