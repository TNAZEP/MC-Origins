package net.minecraft.data.models.blockstates;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableList.Builder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;

public class MultiVariantGenerator implements BlockStateGenerator {
   private final Block block;
   private final List<Variant> baseVariants;
   private final Set<Property<?>> seenProperties = Sets.<Property<?>>newHashSet();
   private final List<PropertyDispatch> declaredPropertySets = Lists.<PropertyDispatch>newArrayList();

   private MultiVariantGenerator(Block var1, List<Variant> var2) {
      this.block = â˜ƒ;
      this.baseVariants = â˜ƒ;
   }

   public MultiVariantGenerator with(PropertyDispatch var1) {
      â˜ƒ.getDefinedProperties().forEach(var1x -> {
         if (this.block.getStateDefinition().getProperty(var1x.getName()) != var1x) {
            throw new IllegalStateException("Property " + var1x + " is not defined for block " + this.block);
         } else if (!this.seenProperties.add(var1x)) {
            throw new IllegalStateException("Values of property " + var1x + " already defined for block " + this.block);
         }
      });
      this.declaredPropertySets.add(â˜ƒ);
      return this;
   }

   public JsonElement get() {
      Stream<Pair<Selector, List<Variant>>> â˜ƒ = Stream.of(Pair.of(Selector.empty(), this.baseVariants));

      for(PropertyDispatch â˜ƒx : this.declaredPropertySets) {
         Map<Selector, List<Variant>> â˜ƒxx = â˜ƒx.getEntries();
         â˜ƒ = â˜ƒ.flatMap(var1x -> â˜ƒ.entrySet().stream().map(var1xx -> {
               Selector â˜ƒ = ((Selector)var1x.getFirst()).extend((Selector)var1xx.getKey());
               List<Variant> â˜ƒx = mergeVariants((List<Variant>)var1x.getSecond(), (List<Variant>)var1xx.getValue());
               return Pair.of(â˜ƒ, â˜ƒx);
            }));
      }

      Map<String, JsonElement> â˜ƒx = new TreeMap();
      â˜ƒ.forEach(var1x -> â˜ƒ.put(((Selector)var1x.getFirst()).getKey(), Variant.convertList((List<Variant>)var1x.getSecond())));
      JsonObject â˜ƒxx = new JsonObject();
      â˜ƒxx.add("variants", Util.make(new JsonObject(), var1x -> â˜ƒ.forEach(var1x::add)));
      return â˜ƒxx;
   }

   private static List<Variant> mergeVariants(List<Variant> var0, List<Variant> var1) {
      Builder<Variant> â˜ƒ = ImmutableList.builder();
      â˜ƒ.forEach(var2x -> â˜ƒ.forEach(var2xx -> â˜ƒ.add(Variant.merge(var2x, var2xx))));
      return â˜ƒ.build();
   }

   @Override
   public Block getBlock() {
      return this.block;
   }

   public static MultiVariantGenerator multiVariant(Block var0) {
      return new MultiVariantGenerator(â˜ƒ, ImmutableList.of(Variant.variant()));
   }

   public static MultiVariantGenerator multiVariant(Block var0, Variant var1) {
      return new MultiVariantGenerator(â˜ƒ, ImmutableList.of(â˜ƒ));
   }

   public static MultiVariantGenerator multiVariant(Block var0, Variant... var1) {
      return new MultiVariantGenerator(â˜ƒ, ImmutableList.copyOf(â˜ƒ));
   }
}
