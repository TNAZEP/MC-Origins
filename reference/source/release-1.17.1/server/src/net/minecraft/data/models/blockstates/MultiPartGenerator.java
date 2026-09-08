package net.minecraft.data.models.blockstates;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class MultiPartGenerator implements BlockStateGenerator {
   private final Block block;
   private final List<MultiPartGenerator.Entry> parts = Lists.<MultiPartGenerator.Entry>newArrayList();

   private MultiPartGenerator(Block var1) {
      this.block = â˜ƒ;
   }

   @Override
   public Block getBlock() {
      return this.block;
   }

   public static MultiPartGenerator multiPart(Block var0) {
      return new MultiPartGenerator(â˜ƒ);
   }

   public MultiPartGenerator with(List<Variant> var1) {
      this.parts.add(new MultiPartGenerator.Entry(â˜ƒ));
      return this;
   }

   public MultiPartGenerator with(Variant var1) {
      return this.with(ImmutableList.of(â˜ƒ));
   }

   public MultiPartGenerator with(Condition var1, List<Variant> var2) {
      this.parts.add(new MultiPartGenerator.ConditionalEntry(â˜ƒ, â˜ƒ));
      return this;
   }

   public MultiPartGenerator with(Condition var1, Variant... var2) {
      return this.with(â˜ƒ, ImmutableList.copyOf(â˜ƒ));
   }

   public MultiPartGenerator with(Condition var1, Variant var2) {
      return this.with(â˜ƒ, ImmutableList.of(â˜ƒ));
   }

   public JsonElement get() {
      StateDefinition<Block, BlockState> â˜ƒ = this.block.getStateDefinition();
      this.parts.forEach(var1x -> var1x.validate(â˜ƒ));
      JsonArray â˜ƒx = new JsonArray();
      this.parts.stream().map(MultiPartGenerator.Entry::get).forEach(â˜ƒx::add);
      JsonObject â˜ƒxx = new JsonObject();
      â˜ƒxx.add("multipart", â˜ƒx);
      return â˜ƒxx;
   }

   static class ConditionalEntry extends MultiPartGenerator.Entry {
      private final Condition condition;

      ConditionalEntry(Condition var1, List<Variant> var2) {
         super(â˜ƒ);
         this.condition = â˜ƒ;
      }

      @Override
      public void validate(StateDefinition<?, ?> var1) {
         this.condition.validate(â˜ƒ);
      }

      @Override
      public void decorate(JsonObject var1) {
         â˜ƒ.add("when", (JsonElement)this.condition.get());
      }
   }

   static class Entry implements Supplier<JsonElement> {
      private final List<Variant> variants;

      Entry(List<Variant> var1) {
         this.variants = â˜ƒ;
      }

      public void validate(StateDefinition<?, ?> var1) {
      }

      public void decorate(JsonObject var1) {
      }

      public JsonElement get() {
         JsonObject â˜ƒ = new JsonObject();
         this.decorate(â˜ƒ);
         â˜ƒ.add("apply", Variant.convertList(this.variants));
         return â˜ƒ;
      }
   }
}
