package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import com.mojang.math.Vector3f;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;

public class ItemModelGenerator {
   public static final List<String> LAYERS = Lists.newArrayList("layer0", "layer1", "layer2", "layer3", "layer4");
   private static final float MIN_Z = 7.5F;
   private static final float MAX_Z = 8.5F;

   public BlockModel generateBlockModel(Function<Material, TextureAtlasSprite> var1, BlockModel var2) {
      Map<String, Either<Material, String>> â˜ƒ = Maps.newHashMap();
      List<BlockElement> â˜ƒx = Lists.<BlockElement>newArrayList();

      for(int â˜ƒxx = 0; â˜ƒxx < LAYERS.size(); ++â˜ƒxx) {
         String â˜ƒxxx = (String)LAYERS.get(â˜ƒxx);
         if (!â˜ƒ.hasTexture(â˜ƒxxx)) {
            break;
         }

         Material â˜ƒxxx = â˜ƒ.getMaterial(â˜ƒxxx);
         â˜ƒ.put(â˜ƒxxx, Either.left(â˜ƒxxx));
         TextureAtlasSprite â˜ƒxxxx = (TextureAtlasSprite)â˜ƒ.apply(â˜ƒxxx);
         â˜ƒx.addAll(this.processFrames(â˜ƒxx, â˜ƒxxx, â˜ƒxxxx));
      }

      â˜ƒ.put("particle", â˜ƒ.hasTexture("particle") ? Either.left(â˜ƒ.getMaterial("particle")) : (Either)â˜ƒ.get("layer0"));
      BlockModel â˜ƒxx = new BlockModel(null, â˜ƒx, â˜ƒ, false, â˜ƒ.getGuiLight(), â˜ƒ.getTransforms(), â˜ƒ.getOverrides());
      â˜ƒxx.name = â˜ƒ.name;
      return â˜ƒxx;
   }

   private List<BlockElement> processFrames(int var1, String var2, TextureAtlasSprite var3) {
      Map<Direction, BlockElementFace> â˜ƒ = Maps.<Direction, BlockElementFace>newHashMap();
      â˜ƒ.put(Direction.SOUTH, new BlockElementFace(null, â˜ƒ, â˜ƒ, new BlockFaceUV(new float[]{0.0F, 0.0F, 16.0F, 16.0F}, 0)));
      â˜ƒ.put(Direction.NORTH, new BlockElementFace(null, â˜ƒ, â˜ƒ, new BlockFaceUV(new float[]{16.0F, 0.0F, 0.0F, 16.0F}, 0)));
      List<BlockElement> â˜ƒx = Lists.<BlockElement>newArrayList();
      â˜ƒx.add(new BlockElement(new Vector3f(0.0F, 0.0F, 7.5F), new Vector3f(16.0F, 16.0F, 8.5F), â˜ƒ, null, true));
      â˜ƒx.addAll(this.createSideElements(â˜ƒ, â˜ƒ, â˜ƒ));
      return â˜ƒx;
   }

   private List<BlockElement> createSideElements(TextureAtlasSprite var1, String var2, int var3) {
      float â˜ƒ = (float)â˜ƒ.getWidth();
      float â˜ƒx = (float)â˜ƒ.getHeight();
      List<BlockElement> â˜ƒxx = Lists.<BlockElement>newArrayList();

      for(ItemModelGenerator.Span â˜ƒxxx : this.getSpans(â˜ƒ)) {
         float â˜ƒxxxx = 0.0F;
         float â˜ƒxxxxx = 0.0F;
         float â˜ƒxxxxxx = 0.0F;
         float â˜ƒxxxxxxx = 0.0F;
         float â˜ƒxxxxxxxx = 0.0F;
         float â˜ƒxxxxxxxxx = 0.0F;
         float â˜ƒxxxxxxxxxx = 0.0F;
         float â˜ƒxxxxxxxxxxx = 0.0F;
         float â˜ƒxxxxxxxxxxxx = 16.0F / â˜ƒ;
         float â˜ƒxxxxxxxxxxxxx = 16.0F / â˜ƒx;
         float â˜ƒxxxxxxxxxxxxxx = (float)â˜ƒxxx.getMin();
         float â˜ƒxxxxxxxxxxxxxxx = (float)â˜ƒxxx.getMax();
         float â˜ƒxxxxxxxxxxxxxxxx = (float)â˜ƒxxx.getAnchor();
         ItemModelGenerator.SpanFacing â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxx.getFacing();
         switch(â˜ƒxxxxxxxxxxxxxxxxx) {
            case UP:
               â˜ƒxxxxxxxx = â˜ƒxxxxxxxxxxxxxx;
               â˜ƒxxxx = â˜ƒxxxxxxxxxxxxxx;
               â˜ƒxxxxxx = â˜ƒxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxx + 1.0F;
               â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxx;
               â˜ƒxxxxx = â˜ƒxxxxxxxxxxxxxxxx;
               â˜ƒxxxxxxx = â˜ƒxxxxxxxxxxxxxxxx;
               â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxx + 1.0F;
               break;
            case DOWN:
               â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxx;
               â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxx + 1.0F;
               â˜ƒxxxxxxxx = â˜ƒxxxxxxxxxxxxxx;
               â˜ƒxxxx = â˜ƒxxxxxxxxxxxxxx;
               â˜ƒxxxxxx = â˜ƒxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxx + 1.0F;
               â˜ƒxxxxx = â˜ƒxxxxxxxxxxxxxxxx + 1.0F;
               â˜ƒxxxxxxx = â˜ƒxxxxxxxxxxxxxxxx + 1.0F;
               break;
            case LEFT:
               â˜ƒxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxx;
               â˜ƒxxxx = â˜ƒxxxxxxxxxxxxxxxx;
               â˜ƒxxxxxx = â˜ƒxxxxxxxxxxxxxxxx;
               â˜ƒxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxx + 1.0F;
               â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxx;
               â˜ƒxxxxx = â˜ƒxxxxxxxxxxxxxx;
               â˜ƒxxxxxxx = â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxx + 1.0F;
               break;
            case RIGHT:
               â˜ƒxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxx;
               â˜ƒxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxx + 1.0F;
               â˜ƒxxxx = â˜ƒxxxxxxxxxxxxxxxx + 1.0F;
               â˜ƒxxxxxx = â˜ƒxxxxxxxxxxxxxxxx + 1.0F;
               â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxx;
               â˜ƒxxxxx = â˜ƒxxxxxxxxxxxxxx;
               â˜ƒxxxxxxx = â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxx + 1.0F;
         }

         â˜ƒxxxx *= â˜ƒxxxxxxxxxxxx;
         â˜ƒxxxxxx *= â˜ƒxxxxxxxxxxxx;
         â˜ƒxxxxx *= â˜ƒxxxxxxxxxxxxx;
         â˜ƒxxxxxxx *= â˜ƒxxxxxxxxxxxxx;
         â˜ƒxxxxx = 16.0F - â˜ƒxxxxx;
         â˜ƒxxxxxxx = 16.0F - â˜ƒxxxxxxx;
         â˜ƒxxxxxxxx *= â˜ƒxxxxxxxxxxxx;
         â˜ƒxxxxxxxxx *= â˜ƒxxxxxxxxxxxx;
         â˜ƒxxxxxxxxxx *= â˜ƒxxxxxxxxxxxxx;
         â˜ƒxxxxxxxxxxx *= â˜ƒxxxxxxxxxxxxx;
         Map<Direction, BlockElementFace> â˜ƒxxxx = Maps.<Direction, BlockElementFace>newHashMap();
         â˜ƒxxxx.put(
            â˜ƒxxxxxxxxxxxxxxxxx.getDirection(),
            new BlockElementFace(null, â˜ƒ, â˜ƒ, new BlockFaceUV(new float[]{â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxxx}, 0))
         );
         switch(â˜ƒxxxxxxxxxxxxxxxxx) {
            case UP:
               â˜ƒxx.add(new BlockElement(new Vector3f(â˜ƒxxxx, â˜ƒxxxxx, 7.5F), new Vector3f(â˜ƒxxxxxx, â˜ƒxxxxx, 8.5F), â˜ƒxxxx, null, true));
               break;
            case DOWN:
               â˜ƒxx.add(new BlockElement(new Vector3f(â˜ƒxxxx, â˜ƒxxxxxxx, 7.5F), new Vector3f(â˜ƒxxxxxx, â˜ƒxxxxxxx, 8.5F), â˜ƒxxxx, null, true));
               break;
            case LEFT:
               â˜ƒxx.add(new BlockElement(new Vector3f(â˜ƒxxxx, â˜ƒxxxxx, 7.5F), new Vector3f(â˜ƒxxxx, â˜ƒxxxxxxx, 8.5F), â˜ƒxxxx, null, true));
               break;
            case RIGHT:
               â˜ƒxx.add(new BlockElement(new Vector3f(â˜ƒxxxxxx, â˜ƒxxxxx, 7.5F), new Vector3f(â˜ƒxxxxxx, â˜ƒxxxxxxx, 8.5F), â˜ƒxxxx, null, true));
         }
      }

      return â˜ƒxx;
   }

   private List<ItemModelGenerator.Span> getSpans(TextureAtlasSprite var1) {
      int â˜ƒ = â˜ƒ.getWidth();
      int â˜ƒx = â˜ƒ.getHeight();
      List<ItemModelGenerator.Span> â˜ƒxx = Lists.<ItemModelGenerator.Span>newArrayList();
      â˜ƒ.getUniqueFrames().forEach(var5 -> {
         for(int â˜ƒ = 0; â˜ƒ < â˜ƒ; ++â˜ƒ) {
            for(int â˜ƒx = 0; â˜ƒx < â˜ƒ; ++â˜ƒx) {
               boolean â˜ƒxx = !this.isTransparent(â˜ƒ, var5, â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒ);
               this.checkTransition(ItemModelGenerator.SpanFacing.UP, â˜ƒ, â˜ƒ, var5, â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx);
               this.checkTransition(ItemModelGenerator.SpanFacing.DOWN, â˜ƒ, â˜ƒ, var5, â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx);
               this.checkTransition(ItemModelGenerator.SpanFacing.LEFT, â˜ƒ, â˜ƒ, var5, â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx);
               this.checkTransition(ItemModelGenerator.SpanFacing.RIGHT, â˜ƒ, â˜ƒ, var5, â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx);
            }
         }
      });
      return â˜ƒxx;
   }

   private void checkTransition(
      ItemModelGenerator.SpanFacing var1,
      List<ItemModelGenerator.Span> var2,
      TextureAtlasSprite var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      boolean var9
   ) {
      boolean â˜ƒ = this.isTransparent(â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒ.getXOffset(), â˜ƒ + â˜ƒ.getYOffset(), â˜ƒ, â˜ƒ) && â˜ƒ;
      if (â˜ƒ) {
         this.createOrExpandSpan(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private void createOrExpandSpan(List<ItemModelGenerator.Span> var1, ItemModelGenerator.SpanFacing var2, int var3, int var4) {
      ItemModelGenerator.Span â˜ƒ = null;

      for(ItemModelGenerator.Span â˜ƒx : â˜ƒ) {
         if (â˜ƒx.getFacing() == â˜ƒ) {
            int â˜ƒxx = â˜ƒ.isHorizontal() ? â˜ƒ : â˜ƒ;
            if (â˜ƒx.getAnchor() == â˜ƒxx) {
               â˜ƒ = â˜ƒx;
               break;
            }
         }
      }

      int â˜ƒx = â˜ƒ.isHorizontal() ? â˜ƒ : â˜ƒ;
      int â˜ƒxx = â˜ƒ.isHorizontal() ? â˜ƒ : â˜ƒ;
      if (â˜ƒ == null) {
         â˜ƒ.add(new ItemModelGenerator.Span(â˜ƒ, â˜ƒxx, â˜ƒx));
      } else {
         â˜ƒ.expand(â˜ƒxx);
      }
   }

   private boolean isTransparent(TextureAtlasSprite var1, int var2, int var3, int var4, int var5, int var6) {
      return â˜ƒ >= 0 && â˜ƒ >= 0 && â˜ƒ < â˜ƒ && â˜ƒ < â˜ƒ ? â˜ƒ.isTransparent(â˜ƒ, â˜ƒ, â˜ƒ) : true;
   }

   static class Span {
      private final ItemModelGenerator.SpanFacing facing;
      private int min;
      private int max;
      private final int anchor;

      public Span(ItemModelGenerator.SpanFacing var1, int var2, int var3) {
         this.facing = â˜ƒ;
         this.min = â˜ƒ;
         this.max = â˜ƒ;
         this.anchor = â˜ƒ;
      }

      public void expand(int var1) {
         if (â˜ƒ < this.min) {
            this.min = â˜ƒ;
         } else if (â˜ƒ > this.max) {
            this.max = â˜ƒ;
         }
      }

      public ItemModelGenerator.SpanFacing getFacing() {
         return this.facing;
      }

      public int getMin() {
         return this.min;
      }

      public int getMax() {
         return this.max;
      }

      public int getAnchor() {
         return this.anchor;
      }
   }

   static enum SpanFacing {
      UP(Direction.UP, 0, -1),
      DOWN(Direction.DOWN, 0, 1),
      LEFT(Direction.EAST, -1, 0),
      RIGHT(Direction.WEST, 1, 0);

      private final Direction direction;
      private final int xOffset;
      private final int yOffset;

      private SpanFacing(Direction var3, int var4, int var5) {
         this.direction = â˜ƒ;
         this.xOffset = â˜ƒ;
         this.yOffset = â˜ƒ;
      }

      public Direction getDirection() {
         return this.direction;
      }

      public int getXOffset() {
         return this.xOffset;
      }

      public int getYOffset() {
         return this.yOffset;
      }

      boolean isHorizontal() {
         return this == DOWN || this == UP;
      }
   }
}
