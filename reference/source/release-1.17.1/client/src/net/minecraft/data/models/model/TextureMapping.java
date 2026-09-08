package net.minecraft.data.models.model;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class TextureMapping {
   private final Map<TextureSlot, ResourceLocation> slots = Maps.<TextureSlot, ResourceLocation>newHashMap();
   private final Set<TextureSlot> forcedSlots = Sets.<TextureSlot>newHashSet();

   public TextureMapping put(TextureSlot var1, ResourceLocation var2) {
      this.slots.put(â˜ƒ, â˜ƒ);
      return this;
   }

   public TextureMapping putForced(TextureSlot var1, ResourceLocation var2) {
      this.slots.put(â˜ƒ, â˜ƒ);
      this.forcedSlots.add(â˜ƒ);
      return this;
   }

   public Stream<TextureSlot> getForced() {
      return this.forcedSlots.stream();
   }

   public TextureMapping copySlot(TextureSlot var1, TextureSlot var2) {
      this.slots.put(â˜ƒ, (ResourceLocation)this.slots.get(â˜ƒ));
      return this;
   }

   public TextureMapping copyForced(TextureSlot var1, TextureSlot var2) {
      this.slots.put(â˜ƒ, (ResourceLocation)this.slots.get(â˜ƒ));
      this.forcedSlots.add(â˜ƒ);
      return this;
   }

   public ResourceLocation get(TextureSlot var1) {
      for(TextureSlot â˜ƒ = â˜ƒ; â˜ƒ != null; â˜ƒ = â˜ƒ.getParent()) {
         ResourceLocation â˜ƒx = (ResourceLocation)this.slots.get(â˜ƒ);
         if (â˜ƒx != null) {
            return â˜ƒx;
         }
      }

      throw new IllegalStateException("Can't find texture for slot " + â˜ƒ);
   }

   public TextureMapping copyAndUpdate(TextureSlot var1, ResourceLocation var2) {
      TextureMapping â˜ƒ = new TextureMapping();
      â˜ƒ.slots.putAll(this.slots);
      â˜ƒ.forcedSlots.addAll(this.forcedSlots);
      â˜ƒ.put(â˜ƒ, â˜ƒ);
      return â˜ƒ;
   }

   public static TextureMapping cube(Block var0) {
      ResourceLocation â˜ƒ = getBlockTexture(â˜ƒ);
      return cube(â˜ƒ);
   }

   public static TextureMapping defaultTexture(Block var0) {
      ResourceLocation â˜ƒ = getBlockTexture(â˜ƒ);
      return defaultTexture(â˜ƒ);
   }

   public static TextureMapping defaultTexture(ResourceLocation var0) {
      return new TextureMapping().put(TextureSlot.TEXTURE, â˜ƒ);
   }

   public static TextureMapping cube(ResourceLocation var0) {
      return new TextureMapping().put(TextureSlot.ALL, â˜ƒ);
   }

   public static TextureMapping cross(Block var0) {
      return singleSlot(TextureSlot.CROSS, getBlockTexture(â˜ƒ));
   }

   public static TextureMapping cross(ResourceLocation var0) {
      return singleSlot(TextureSlot.CROSS, â˜ƒ);
   }

   public static TextureMapping plant(Block var0) {
      return singleSlot(TextureSlot.PLANT, getBlockTexture(â˜ƒ));
   }

   public static TextureMapping plant(ResourceLocation var0) {
      return singleSlot(TextureSlot.PLANT, â˜ƒ);
   }

   public static TextureMapping rail(Block var0) {
      return singleSlot(TextureSlot.RAIL, getBlockTexture(â˜ƒ));
   }

   public static TextureMapping rail(ResourceLocation var0) {
      return singleSlot(TextureSlot.RAIL, â˜ƒ);
   }

   public static TextureMapping wool(Block var0) {
      return singleSlot(TextureSlot.WOOL, getBlockTexture(â˜ƒ));
   }

   public static TextureMapping wool(ResourceLocation var0) {
      return singleSlot(TextureSlot.WOOL, â˜ƒ);
   }

   public static TextureMapping stem(Block var0) {
      return singleSlot(TextureSlot.STEM, getBlockTexture(â˜ƒ));
   }

   public static TextureMapping attachedStem(Block var0, Block var1) {
      return new TextureMapping().put(TextureSlot.STEM, getBlockTexture(â˜ƒ)).put(TextureSlot.UPPER_STEM, getBlockTexture(â˜ƒ));
   }

   public static TextureMapping pattern(Block var0) {
      return singleSlot(TextureSlot.PATTERN, getBlockTexture(â˜ƒ));
   }

   public static TextureMapping fan(Block var0) {
      return singleSlot(TextureSlot.FAN, getBlockTexture(â˜ƒ));
   }

   public static TextureMapping crop(ResourceLocation var0) {
      return singleSlot(TextureSlot.CROP, â˜ƒ);
   }

   public static TextureMapping pane(Block var0, Block var1) {
      return new TextureMapping().put(TextureSlot.PANE, getBlockTexture(â˜ƒ)).put(TextureSlot.EDGE, getBlockTexture(â˜ƒ, "_top"));
   }

   public static TextureMapping singleSlot(TextureSlot var0, ResourceLocation var1) {
      return new TextureMapping().put(â˜ƒ, â˜ƒ);
   }

   public static TextureMapping column(Block var0) {
      return new TextureMapping().put(TextureSlot.SIDE, getBlockTexture(â˜ƒ, "_side")).put(TextureSlot.END, getBlockTexture(â˜ƒ, "_top"));
   }

   public static TextureMapping cubeTop(Block var0) {
      return new TextureMapping().put(TextureSlot.SIDE, getBlockTexture(â˜ƒ, "_side")).put(TextureSlot.TOP, getBlockTexture(â˜ƒ, "_top"));
   }

   public static TextureMapping logColumn(Block var0) {
      return new TextureMapping().put(TextureSlot.SIDE, getBlockTexture(â˜ƒ)).put(TextureSlot.END, getBlockTexture(â˜ƒ, "_top"));
   }

   public static TextureMapping column(ResourceLocation var0, ResourceLocation var1) {
      return new TextureMapping().put(TextureSlot.SIDE, â˜ƒ).put(TextureSlot.END, â˜ƒ);
   }

   public static TextureMapping cubeBottomTop(Block var0) {
      return new TextureMapping()
         .put(TextureSlot.SIDE, getBlockTexture(â˜ƒ, "_side"))
         .put(TextureSlot.TOP, getBlockTexture(â˜ƒ, "_top"))
         .put(TextureSlot.BOTTOM, getBlockTexture(â˜ƒ, "_bottom"));
   }

   public static TextureMapping cubeBottomTopWithWall(Block var0) {
      ResourceLocation â˜ƒ = getBlockTexture(â˜ƒ);
      return new TextureMapping()
         .put(TextureSlot.WALL, â˜ƒ)
         .put(TextureSlot.SIDE, â˜ƒ)
         .put(TextureSlot.TOP, getBlockTexture(â˜ƒ, "_top"))
         .put(TextureSlot.BOTTOM, getBlockTexture(â˜ƒ, "_bottom"));
   }

   public static TextureMapping columnWithWall(Block var0) {
      ResourceLocation â˜ƒ = getBlockTexture(â˜ƒ);
      return new TextureMapping().put(TextureSlot.WALL, â˜ƒ).put(TextureSlot.SIDE, â˜ƒ).put(TextureSlot.END, getBlockTexture(â˜ƒ, "_top"));
   }

   public static TextureMapping door(ResourceLocation var0, ResourceLocation var1) {
      return new TextureMapping().put(TextureSlot.TOP, â˜ƒ).put(TextureSlot.BOTTOM, â˜ƒ);
   }

   public static TextureMapping door(Block var0) {
      return new TextureMapping().put(TextureSlot.TOP, getBlockTexture(â˜ƒ, "_top")).put(TextureSlot.BOTTOM, getBlockTexture(â˜ƒ, "_bottom"));
   }

   public static TextureMapping particle(Block var0) {
      return new TextureMapping().put(TextureSlot.PARTICLE, getBlockTexture(â˜ƒ));
   }

   public static TextureMapping particle(ResourceLocation var0) {
      return new TextureMapping().put(TextureSlot.PARTICLE, â˜ƒ);
   }

   public static TextureMapping fire0(Block var0) {
      return new TextureMapping().put(TextureSlot.FIRE, getBlockTexture(â˜ƒ, "_0"));
   }

   public static TextureMapping fire1(Block var0) {
      return new TextureMapping().put(TextureSlot.FIRE, getBlockTexture(â˜ƒ, "_1"));
   }

   public static TextureMapping lantern(Block var0) {
      return new TextureMapping().put(TextureSlot.LANTERN, getBlockTexture(â˜ƒ));
   }

   public static TextureMapping torch(Block var0) {
      return new TextureMapping().put(TextureSlot.TORCH, getBlockTexture(â˜ƒ));
   }

   public static TextureMapping torch(ResourceLocation var0) {
      return new TextureMapping().put(TextureSlot.TORCH, â˜ƒ);
   }

   public static TextureMapping particleFromItem(Item var0) {
      return new TextureMapping().put(TextureSlot.PARTICLE, getItemTexture(â˜ƒ));
   }

   public static TextureMapping commandBlock(Block var0) {
      return new TextureMapping()
         .put(TextureSlot.SIDE, getBlockTexture(â˜ƒ, "_side"))
         .put(TextureSlot.FRONT, getBlockTexture(â˜ƒ, "_front"))
         .put(TextureSlot.BACK, getBlockTexture(â˜ƒ, "_back"));
   }

   public static TextureMapping orientableCube(Block var0) {
      return new TextureMapping()
         .put(TextureSlot.SIDE, getBlockTexture(â˜ƒ, "_side"))
         .put(TextureSlot.FRONT, getBlockTexture(â˜ƒ, "_front"))
         .put(TextureSlot.TOP, getBlockTexture(â˜ƒ, "_top"))
         .put(TextureSlot.BOTTOM, getBlockTexture(â˜ƒ, "_bottom"));
   }

   public static TextureMapping orientableCubeOnlyTop(Block var0) {
      return new TextureMapping()
         .put(TextureSlot.SIDE, getBlockTexture(â˜ƒ, "_side"))
         .put(TextureSlot.FRONT, getBlockTexture(â˜ƒ, "_front"))
         .put(TextureSlot.TOP, getBlockTexture(â˜ƒ, "_top"));
   }

   public static TextureMapping orientableCubeSameEnds(Block var0) {
      return new TextureMapping()
         .put(TextureSlot.SIDE, getBlockTexture(â˜ƒ, "_side"))
         .put(TextureSlot.FRONT, getBlockTexture(â˜ƒ, "_front"))
         .put(TextureSlot.END, getBlockTexture(â˜ƒ, "_end"));
   }

   public static TextureMapping top(Block var0) {
      return new TextureMapping().put(TextureSlot.TOP, getBlockTexture(â˜ƒ, "_top"));
   }

   public static TextureMapping craftingTable(Block var0, Block var1) {
      return new TextureMapping()
         .put(TextureSlot.PARTICLE, getBlockTexture(â˜ƒ, "_front"))
         .put(TextureSlot.DOWN, getBlockTexture(â˜ƒ))
         .put(TextureSlot.UP, getBlockTexture(â˜ƒ, "_top"))
         .put(TextureSlot.NORTH, getBlockTexture(â˜ƒ, "_front"))
         .put(TextureSlot.EAST, getBlockTexture(â˜ƒ, "_side"))
         .put(TextureSlot.SOUTH, getBlockTexture(â˜ƒ, "_side"))
         .put(TextureSlot.WEST, getBlockTexture(â˜ƒ, "_front"));
   }

   public static TextureMapping fletchingTable(Block var0, Block var1) {
      return new TextureMapping()
         .put(TextureSlot.PARTICLE, getBlockTexture(â˜ƒ, "_front"))
         .put(TextureSlot.DOWN, getBlockTexture(â˜ƒ))
         .put(TextureSlot.UP, getBlockTexture(â˜ƒ, "_top"))
         .put(TextureSlot.NORTH, getBlockTexture(â˜ƒ, "_front"))
         .put(TextureSlot.SOUTH, getBlockTexture(â˜ƒ, "_front"))
         .put(TextureSlot.EAST, getBlockTexture(â˜ƒ, "_side"))
         .put(TextureSlot.WEST, getBlockTexture(â˜ƒ, "_side"));
   }

   public static TextureMapping campfire(Block var0) {
      return new TextureMapping().put(TextureSlot.LIT_LOG, getBlockTexture(â˜ƒ, "_log_lit")).put(TextureSlot.FIRE, getBlockTexture(â˜ƒ, "_fire"));
   }

   public static TextureMapping candleCake(Block var0, boolean var1) {
      return new TextureMapping()
         .put(TextureSlot.PARTICLE, getBlockTexture(Blocks.CAKE, "_side"))
         .put(TextureSlot.BOTTOM, getBlockTexture(Blocks.CAKE, "_bottom"))
         .put(TextureSlot.TOP, getBlockTexture(Blocks.CAKE, "_top"))
         .put(TextureSlot.SIDE, getBlockTexture(Blocks.CAKE, "_side"))
         .put(TextureSlot.CANDLE, getBlockTexture(â˜ƒ, â˜ƒ ? "_lit" : ""));
   }

   public static TextureMapping cauldron(ResourceLocation var0) {
      return new TextureMapping()
         .put(TextureSlot.PARTICLE, getBlockTexture(Blocks.CAULDRON, "_side"))
         .put(TextureSlot.SIDE, getBlockTexture(Blocks.CAULDRON, "_side"))
         .put(TextureSlot.TOP, getBlockTexture(Blocks.CAULDRON, "_top"))
         .put(TextureSlot.BOTTOM, getBlockTexture(Blocks.CAULDRON, "_bottom"))
         .put(TextureSlot.INSIDE, getBlockTexture(Blocks.CAULDRON, "_inner"))
         .put(TextureSlot.CONTENT, â˜ƒ);
   }

   public static TextureMapping layer0(Item var0) {
      return new TextureMapping().put(TextureSlot.LAYER0, getItemTexture(â˜ƒ));
   }

   public static TextureMapping layer0(Block var0) {
      return new TextureMapping().put(TextureSlot.LAYER0, getBlockTexture(â˜ƒ));
   }

   public static TextureMapping layer0(ResourceLocation var0) {
      return new TextureMapping().put(TextureSlot.LAYER0, â˜ƒ);
   }

   public static ResourceLocation getBlockTexture(Block var0) {
      ResourceLocation â˜ƒ = Registry.BLOCK.getKey(â˜ƒ);
      return new ResourceLocation(â˜ƒ.getNamespace(), "block/" + â˜ƒ.getPath());
   }

   public static ResourceLocation getBlockTexture(Block var0, String var1) {
      ResourceLocation â˜ƒ = Registry.BLOCK.getKey(â˜ƒ);
      return new ResourceLocation(â˜ƒ.getNamespace(), "block/" + â˜ƒ.getPath() + â˜ƒ);
   }

   public static ResourceLocation getItemTexture(Item var0) {
      ResourceLocation â˜ƒ = Registry.ITEM.getKey(â˜ƒ);
      return new ResourceLocation(â˜ƒ.getNamespace(), "item/" + â˜ƒ.getPath());
   }

   public static ResourceLocation getItemTexture(Item var0, String var1) {
      ResourceLocation â˜ƒ = Registry.ITEM.getKey(â˜ƒ);
      return new ResourceLocation(â˜ƒ.getNamespace(), "item/" + â˜ƒ.getPath() + â˜ƒ);
   }
}
