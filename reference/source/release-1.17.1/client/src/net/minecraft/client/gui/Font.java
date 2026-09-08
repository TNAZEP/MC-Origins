package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;
import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Transformation;
import com.mojang.math.Vector3f;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.gui.font.glyphs.EmptyGlyph;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.FormattedCharSink;
import net.minecraft.util.Mth;
import net.minecraft.util.StringDecomposer;

public class Font {
   private static final float EFFECT_DEPTH = 0.01F;
   private static final Vector3f SHADOW_OFFSET = new Vector3f(0.0F, 0.0F, 0.03F);
   public final int lineHeight = 9;
   public final Random random = new Random();
   private final Function<ResourceLocation, FontSet> fonts;
   private final StringSplitter splitter;

   public Font(Function<ResourceLocation, FontSet> var1) {
      this.fonts = â˜ƒ;
      this.splitter = new StringSplitter((var1x, var2) -> this.getFontSet(var2.getFont()).getGlyphInfo(var1x).getAdvance(var2.isBold()));
   }

   FontSet getFontSet(ResourceLocation var1) {
      return (FontSet)this.fonts.apply(â˜ƒ);
   }

   public int drawShadow(PoseStack var1, String var2, float var3, float var4, int var5) {
      return this.drawInternal(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.last().pose(), true, this.isBidirectional());
   }

   public int drawShadow(PoseStack var1, String var2, float var3, float var4, int var5, boolean var6) {
      return this.drawInternal(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.last().pose(), true, â˜ƒ);
   }

   public int draw(PoseStack var1, String var2, float var3, float var4, int var5) {
      return this.drawInternal(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.last().pose(), false, this.isBidirectional());
   }

   public int drawShadow(PoseStack var1, FormattedCharSequence var2, float var3, float var4, int var5) {
      return this.drawInternal(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.last().pose(), true);
   }

   public int drawShadow(PoseStack var1, Component var2, float var3, float var4, int var5) {
      return this.drawInternal(â˜ƒ.getVisualOrderText(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.last().pose(), true);
   }

   public int draw(PoseStack var1, FormattedCharSequence var2, float var3, float var4, int var5) {
      return this.drawInternal(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.last().pose(), false);
   }

   public int draw(PoseStack var1, Component var2, float var3, float var4, int var5) {
      return this.drawInternal(â˜ƒ.getVisualOrderText(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.last().pose(), false);
   }

   public String bidirectionalShaping(String var1) {
      try {
         Bidi â˜ƒ = new Bidi(new ArabicShaping(8).shape(â˜ƒ), 127);
         â˜ƒ.setReorderingMode(0);
         return â˜ƒ.writeReordered(2);
      } catch (ArabicShapingException var3) {
         return â˜ƒ;
      }
   }

   private int drawInternal(String var1, float var2, float var3, int var4, Matrix4f var5, boolean var6, boolean var7) {
      if (â˜ƒ == null) {
         return 0;
      } else {
         MultiBufferSource.BufferSource â˜ƒ = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
         int â˜ƒx = this.drawInBatch(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, false, 0, 15728880, â˜ƒ);
         â˜ƒ.endBatch();
         return â˜ƒx;
      }
   }

   private int drawInternal(FormattedCharSequence var1, float var2, float var3, int var4, Matrix4f var5, boolean var6) {
      MultiBufferSource.BufferSource â˜ƒ = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
      int â˜ƒx = this.drawInBatch(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, false, 0, 15728880);
      â˜ƒ.endBatch();
      return â˜ƒx;
   }

   public int drawInBatch(String var1, float var2, float var3, int var4, boolean var5, Matrix4f var6, MultiBufferSource var7, boolean var8, int var9, int var10) {
      return this.drawInBatch(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.isBidirectional());
   }

   public int drawInBatch(
      String var1, float var2, float var3, int var4, boolean var5, Matrix4f var6, MultiBufferSource var7, boolean var8, int var9, int var10, boolean var11
   ) {
      return this.drawInternal(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public int drawInBatch(
      Component var1, float var2, float var3, int var4, boolean var5, Matrix4f var6, MultiBufferSource var7, boolean var8, int var9, int var10
   ) {
      return this.drawInBatch(â˜ƒ.getVisualOrderText(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public int drawInBatch(
      FormattedCharSequence var1, float var2, float var3, int var4, boolean var5, Matrix4f var6, MultiBufferSource var7, boolean var8, int var9, int var10
   ) {
      return this.drawInternal(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void drawInBatch8xOutline(FormattedCharSequence var1, float var2, float var3, int var4, int var5, Matrix4f var6, MultiBufferSource var7, int var8) {
      int â˜ƒ = adjustColor(â˜ƒ);
      Font.StringRenderOutput â˜ƒx = new Font.StringRenderOutput(â˜ƒ, 0.0F, 0.0F, â˜ƒ, false, â˜ƒ, Font.DisplayMode.NORMAL, â˜ƒ);

      for(int â˜ƒxx = -1; â˜ƒxx <= 1; ++â˜ƒxx) {
         for(int â˜ƒxxx = -1; â˜ƒxxx <= 1; ++â˜ƒxxx) {
            if (â˜ƒxx != 0 || â˜ƒxxx != 0) {
               float[] â˜ƒxxxx = new float[]{â˜ƒ};
               int â˜ƒxxxxx = â˜ƒxx;
               int â˜ƒxxxxxx = â˜ƒxxx;
               â˜ƒ.accept((var7x, var8x, var9x) -> {
                  boolean â˜ƒ = var8x.isBold();
                  FontSet â˜ƒx = this.getFontSet(var8x.getFont());
                  GlyphInfo â˜ƒxx = â˜ƒx.getGlyphInfo(var9x);
                  â˜ƒ.x = â˜ƒ[0] + (float)â˜ƒ * â˜ƒxx.getShadowOffset();
                  â˜ƒ.y = â˜ƒ + (float)â˜ƒ * â˜ƒxx.getShadowOffset();
                  â˜ƒ[0] += â˜ƒxx.getAdvance(â˜ƒ);
                  return â˜ƒ.accept(var7x, var8x.withColor(â˜ƒ), var9x);
               });
            }
         }
      }

      Font.StringRenderOutput â˜ƒxx = new Font.StringRenderOutput(â˜ƒ, â˜ƒ, â˜ƒ, adjustColor(â˜ƒ), false, â˜ƒ, Font.DisplayMode.POLYGON_OFFSET, â˜ƒ);
      â˜ƒ.accept(â˜ƒxx);
      â˜ƒxx.finish(0, â˜ƒ);
   }

   private static int adjustColor(int var0) {
      return (â˜ƒ & -67108864) == 0 ? â˜ƒ | 0xFF000000 : â˜ƒ;
   }

   private int drawInternal(
      String var1, float var2, float var3, int var4, boolean var5, Matrix4f var6, MultiBufferSource var7, boolean var8, int var9, int var10, boolean var11
   ) {
      if (â˜ƒ) {
         â˜ƒ = this.bidirectionalShaping(â˜ƒ);
      }

      â˜ƒ = adjustColor(â˜ƒ);
      Matrix4f â˜ƒ = â˜ƒ.copy();
      if (â˜ƒ) {
         this.renderText(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, true, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.translate(SHADOW_OFFSET);
      }

      â˜ƒ = this.renderText(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, false, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      return (int)â˜ƒ + (â˜ƒ ? 1 : 0);
   }

   private int drawInternal(
      FormattedCharSequence var1, float var2, float var3, int var4, boolean var5, Matrix4f var6, MultiBufferSource var7, boolean var8, int var9, int var10
   ) {
      â˜ƒ = adjustColor(â˜ƒ);
      Matrix4f â˜ƒ = â˜ƒ.copy();
      if (â˜ƒ) {
         this.renderText(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, true, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.translate(SHADOW_OFFSET);
      }

      â˜ƒ = this.renderText(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, false, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      return (int)â˜ƒ + (â˜ƒ ? 1 : 0);
   }

   private float renderText(
      String var1, float var2, float var3, int var4, boolean var5, Matrix4f var6, MultiBufferSource var7, boolean var8, int var9, int var10
   ) {
      Font.StringRenderOutput â˜ƒ = new Font.StringRenderOutput(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      StringDecomposer.iterateFormatted(â˜ƒ, Style.EMPTY, â˜ƒ);
      return â˜ƒ.finish(â˜ƒ, â˜ƒ);
   }

   private float renderText(
      FormattedCharSequence var1, float var2, float var3, int var4, boolean var5, Matrix4f var6, MultiBufferSource var7, boolean var8, int var9, int var10
   ) {
      Font.StringRenderOutput â˜ƒ = new Font.StringRenderOutput(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.accept(â˜ƒ);
      return â˜ƒ.finish(â˜ƒ, â˜ƒ);
   }

   void renderChar(
      BakedGlyph var1,
      boolean var2,
      boolean var3,
      float var4,
      float var5,
      float var6,
      Matrix4f var7,
      VertexConsumer var8,
      float var9,
      float var10,
      float var11,
      float var12,
      int var13
   ) {
      â˜ƒ.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ) {
         â˜ƒ.render(â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public int width(String var1) {
      return Mth.ceil(this.splitter.stringWidth(â˜ƒ));
   }

   public int width(FormattedText var1) {
      return Mth.ceil(this.splitter.stringWidth(â˜ƒ));
   }

   public int width(FormattedCharSequence var1) {
      return Mth.ceil(this.splitter.stringWidth(â˜ƒ));
   }

   public String plainSubstrByWidth(String var1, int var2, boolean var3) {
      return â˜ƒ ? this.splitter.plainTailByWidth(â˜ƒ, â˜ƒ, Style.EMPTY) : this.splitter.plainHeadByWidth(â˜ƒ, â˜ƒ, Style.EMPTY);
   }

   public String plainSubstrByWidth(String var1, int var2) {
      return this.splitter.plainHeadByWidth(â˜ƒ, â˜ƒ, Style.EMPTY);
   }

   public FormattedText substrByWidth(FormattedText var1, int var2) {
      return this.splitter.headByWidth(â˜ƒ, â˜ƒ, Style.EMPTY);
   }

   public void drawWordWrap(FormattedText var1, int var2, int var3, int var4, int var5) {
      Matrix4f â˜ƒ = Transformation.identity().getMatrix();

      for(FormattedCharSequence â˜ƒx : this.split(â˜ƒ, â˜ƒ)) {
         this.drawInternal(â˜ƒx, (float)â˜ƒ, (float)â˜ƒ, â˜ƒ, â˜ƒ, false);
         â˜ƒ += 9;
      }
   }

   public int wordWrapHeight(String var1, int var2) {
      return 9 * this.splitter.splitLines(â˜ƒ, â˜ƒ, Style.EMPTY).size();
   }

   public List<FormattedCharSequence> split(FormattedText var1, int var2) {
      return Language.getInstance().getVisualOrder(this.splitter.splitLines(â˜ƒ, â˜ƒ, Style.EMPTY));
   }

   public boolean isBidirectional() {
      return Language.getInstance().isDefaultRightToLeft();
   }

   public StringSplitter getSplitter() {
      return this.splitter;
   }

   public static enum DisplayMode {
      NORMAL,
      SEE_THROUGH,
      POLYGON_OFFSET;
   }

   class StringRenderOutput implements FormattedCharSink {
      final MultiBufferSource bufferSource;
      private final boolean dropShadow;
      private final float dimFactor;
      private final float r;
      private final float g;
      private final float b;
      private final float a;
      private final Matrix4f pose;
      private final Font.DisplayMode mode;
      private final int packedLightCoords;
      float x;
      float y;
      @Nullable
      private List<BakedGlyph.Effect> effects;

      private void addEffect(BakedGlyph.Effect var1) {
         if (this.effects == null) {
            this.effects = Lists.<BakedGlyph.Effect>newArrayList();
         }

         this.effects.add(â˜ƒ);
      }

      public StringRenderOutput(MultiBufferSource var2, float var3, float var4, int var5, boolean var6, Matrix4f var7, boolean var8, int var9) {
         this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ ? Font.DisplayMode.SEE_THROUGH : Font.DisplayMode.NORMAL, â˜ƒ);
      }

      public StringRenderOutput(MultiBufferSource var2, float var3, float var4, int var5, boolean var6, Matrix4f var7, Font.DisplayMode var8, int var9) {
         this.bufferSource = â˜ƒ;
         this.x = â˜ƒ;
         this.y = â˜ƒ;
         this.dropShadow = â˜ƒ;
         this.dimFactor = â˜ƒ ? 0.25F : 1.0F;
         this.r = (float)(â˜ƒ >> 16 & 0xFF) / 255.0F * this.dimFactor;
         this.g = (float)(â˜ƒ >> 8 & 0xFF) / 255.0F * this.dimFactor;
         this.b = (float)(â˜ƒ & 0xFF) / 255.0F * this.dimFactor;
         this.a = (float)(â˜ƒ >> 24 & 0xFF) / 255.0F;
         this.pose = â˜ƒ;
         this.mode = â˜ƒ;
         this.packedLightCoords = â˜ƒ;
      }

      @Override
      public boolean accept(int var1, Style var2, int var3) {
         FontSet â˜ƒxxx = Font.this.getFontSet(â˜ƒ.getFont());
         GlyphInfo â˜ƒxxxx = â˜ƒxxx.getGlyphInfo(â˜ƒ);
         BakedGlyph â˜ƒxxxxx = â˜ƒ.isObfuscated() && â˜ƒ != 32 ? â˜ƒxxx.getRandomGlyph(â˜ƒxxxx) : â˜ƒxxx.getGlyph(â˜ƒ);
         boolean â˜ƒxxxxxx = â˜ƒ.isBold();
         float â˜ƒxxxxxxx = this.a;
         TextColor â˜ƒxxxxxxxx = â˜ƒ.getColor();
         float â˜ƒ;
         float â˜ƒx;
         float â˜ƒxx;
         if (â˜ƒxxxxxxxx != null) {
            int â˜ƒxxxxxxxxx = â˜ƒxxxxxxxx.getValue();
            â˜ƒ = (float)(â˜ƒxxxxxxxxx >> 16 & 0xFF) / 255.0F * this.dimFactor;
            â˜ƒx = (float)(â˜ƒxxxxxxxxx >> 8 & 0xFF) / 255.0F * this.dimFactor;
            â˜ƒxx = (float)(â˜ƒxxxxxxxxx & 0xFF) / 255.0F * this.dimFactor;
         } else {
            â˜ƒ = this.r;
            â˜ƒx = this.g;
            â˜ƒxx = this.b;
         }

         if (!(â˜ƒxxxxx instanceof EmptyGlyph)) {
            float â˜ƒ = â˜ƒxxxxxx ? â˜ƒxxxx.getBoldOffset() : 0.0F;
            float â˜ƒx = this.dropShadow ? â˜ƒxxxx.getShadowOffset() : 0.0F;
            VertexConsumer â˜ƒxx = this.bufferSource.getBuffer(â˜ƒxxxxx.renderType(this.mode));
            Font.this.renderChar(
               â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒ.isItalic(), â˜ƒ, this.x + â˜ƒx, this.y + â˜ƒx, this.pose, â˜ƒxx, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxxxxxx, this.packedLightCoords
            );
         }

         float â˜ƒ = â˜ƒxxxx.getAdvance(â˜ƒxxxxxx);
         float â˜ƒx = this.dropShadow ? 1.0F : 0.0F;
         if (â˜ƒ.isStrikethrough()) {
            this.addEffect(
               new BakedGlyph.Effect(
                  this.x + â˜ƒx - 1.0F, this.y + â˜ƒx + 4.5F, this.x + â˜ƒx + â˜ƒ, this.y + â˜ƒx + 4.5F - 1.0F, 0.01F, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxxxxxx
               )
            );
         }

         if (â˜ƒ.isUnderlined()) {
            this.addEffect(
               new BakedGlyph.Effect(
                  this.x + â˜ƒx - 1.0F, this.y + â˜ƒx + 9.0F, this.x + â˜ƒx + â˜ƒ, this.y + â˜ƒx + 9.0F - 1.0F, 0.01F, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxxxxxx
               )
            );
         }

         this.x += â˜ƒ;
         return true;
      }

      public float finish(int var1, float var2) {
         if (â˜ƒ != 0) {
            float â˜ƒ = (float)(â˜ƒ >> 24 & 0xFF) / 255.0F;
            float â˜ƒx = (float)(â˜ƒ >> 16 & 0xFF) / 255.0F;
            float â˜ƒxx = (float)(â˜ƒ >> 8 & 0xFF) / 255.0F;
            float â˜ƒxxx = (float)(â˜ƒ & 0xFF) / 255.0F;
            this.addEffect(new BakedGlyph.Effect(â˜ƒ - 1.0F, this.y + 9.0F, this.x + 1.0F, this.y - 1.0F, 0.01F, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒ));
         }

         if (this.effects != null) {
            BakedGlyph â˜ƒ = Font.this.getFontSet(Style.DEFAULT_FONT).whiteGlyph();
            VertexConsumer â˜ƒx = this.bufferSource.getBuffer(â˜ƒ.renderType(this.mode));

            for(BakedGlyph.Effect â˜ƒxx : this.effects) {
               â˜ƒ.renderEffect(â˜ƒxx, this.pose, â˜ƒx, this.packedLightCoords);
            }
         }

         return this.x;
      }
   }
}
