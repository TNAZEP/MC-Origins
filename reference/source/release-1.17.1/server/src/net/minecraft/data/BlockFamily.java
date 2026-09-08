package net.minecraft.data;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.world.level.block.Block;
import org.apache.commons.lang3.StringUtils;

public class BlockFamily {
   private final Block baseBlock;
   final Map<BlockFamily.Variant, Block> variants = Maps.newHashMap();
   boolean generateModel = true;
   boolean generateRecipe = true;
   @Nullable
   String recipeGroupPrefix;
   @Nullable
   String recipeUnlockedBy;

   BlockFamily(Block var1) {
      this.baseBlock = â˜ƒ;
   }

   public Block getBaseBlock() {
      return this.baseBlock;
   }

   public Map<BlockFamily.Variant, Block> getVariants() {
      return this.variants;
   }

   public Block get(BlockFamily.Variant var1) {
      return (Block)this.variants.get(â˜ƒ);
   }

   public boolean shouldGenerateModel() {
      return this.generateModel;
   }

   public boolean shouldGenerateRecipe() {
      return this.generateRecipe;
   }

   public Optional<String> getRecipeGroupPrefix() {
      return StringUtils.isBlank(this.recipeGroupPrefix) ? Optional.empty() : Optional.of(this.recipeGroupPrefix);
   }

   public Optional<String> getRecipeUnlockedBy() {
      return StringUtils.isBlank(this.recipeUnlockedBy) ? Optional.empty() : Optional.of(this.recipeUnlockedBy);
   }

   public static class Builder {
      private final BlockFamily family;

      public Builder(Block var1) {
         this.family = new BlockFamily(â˜ƒ);
      }

      public BlockFamily getFamily() {
         return this.family;
      }

      public BlockFamily.Builder button(Block var1) {
         this.family.variants.put(BlockFamily.Variant.BUTTON, â˜ƒ);
         return this;
      }

      public BlockFamily.Builder chiseled(Block var1) {
         this.family.variants.put(BlockFamily.Variant.CHISELED, â˜ƒ);
         return this;
      }

      public BlockFamily.Builder cracked(Block var1) {
         this.family.variants.put(BlockFamily.Variant.CRACKED, â˜ƒ);
         return this;
      }

      public BlockFamily.Builder cut(Block var1) {
         this.family.variants.put(BlockFamily.Variant.CUT, â˜ƒ);
         return this;
      }

      public BlockFamily.Builder door(Block var1) {
         this.family.variants.put(BlockFamily.Variant.DOOR, â˜ƒ);
         return this;
      }

      public BlockFamily.Builder fence(Block var1) {
         this.family.variants.put(BlockFamily.Variant.FENCE, â˜ƒ);
         return this;
      }

      public BlockFamily.Builder fenceGate(Block var1) {
         this.family.variants.put(BlockFamily.Variant.FENCE_GATE, â˜ƒ);
         return this;
      }

      public BlockFamily.Builder sign(Block var1, Block var2) {
         this.family.variants.put(BlockFamily.Variant.SIGN, â˜ƒ);
         this.family.variants.put(BlockFamily.Variant.WALL_SIGN, â˜ƒ);
         return this;
      }

      public BlockFamily.Builder slab(Block var1) {
         this.family.variants.put(BlockFamily.Variant.SLAB, â˜ƒ);
         return this;
      }

      public BlockFamily.Builder stairs(Block var1) {
         this.family.variants.put(BlockFamily.Variant.STAIRS, â˜ƒ);
         return this;
      }

      public BlockFamily.Builder pressurePlate(Block var1) {
         this.family.variants.put(BlockFamily.Variant.PRESSURE_PLATE, â˜ƒ);
         return this;
      }

      public BlockFamily.Builder polished(Block var1) {
         this.family.variants.put(BlockFamily.Variant.POLISHED, â˜ƒ);
         return this;
      }

      public BlockFamily.Builder trapdoor(Block var1) {
         this.family.variants.put(BlockFamily.Variant.TRAPDOOR, â˜ƒ);
         return this;
      }

      public BlockFamily.Builder wall(Block var1) {
         this.family.variants.put(BlockFamily.Variant.WALL, â˜ƒ);
         return this;
      }

      public BlockFamily.Builder dontGenerateModel() {
         this.family.generateModel = false;
         return this;
      }

      public BlockFamily.Builder dontGenerateRecipe() {
         this.family.generateRecipe = false;
         return this;
      }

      public BlockFamily.Builder recipeGroupPrefix(String var1) {
         this.family.recipeGroupPrefix = â˜ƒ;
         return this;
      }

      public BlockFamily.Builder recipeUnlockedBy(String var1) {
         this.family.recipeUnlockedBy = â˜ƒ;
         return this;
      }
   }

   public static enum Variant {
      BUTTON("button"),
      CHISELED("chiseled"),
      CRACKED("cracked"),
      CUT("cut"),
      DOOR("door"),
      FENCE("fence"),
      FENCE_GATE("fence_gate"),
      SIGN("sign"),
      SLAB("slab"),
      STAIRS("stairs"),
      PRESSURE_PLATE("pressure_plate"),
      POLISHED("polished"),
      TRAPDOOR("trapdoor"),
      WALL("wall"),
      WALL_SIGN("wall_sign");

      private final String name;

      private Variant(String var3) {
         this.name = â˜ƒ;
      }

      public String getName() {
         return this.name;
      }
   }
}
