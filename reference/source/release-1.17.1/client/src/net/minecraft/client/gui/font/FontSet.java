package net.minecraft.client.gui.font;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.font.GlyphProvider;
import com.mojang.blaze3d.font.RawGlyph;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.gui.font.glyphs.EmptyGlyph;
import net.minecraft.client.gui.font.glyphs.MissingGlyph;
import net.minecraft.client.gui.font.glyphs.WhiteGlyph;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class FontSet implements AutoCloseable {
   private static final EmptyGlyph SPACE_GLYPH = new EmptyGlyph();
   private static final GlyphInfo SPACE_INFO = () -> 4.0F;
   private static final Random RANDOM = new Random();
   private final TextureManager textureManager;
   private final ResourceLocation name;
   private BakedGlyph missingGlyph;
   private BakedGlyph whiteGlyph;
   private final List<GlyphProvider> providers = Lists.<GlyphProvider>newArrayList();
   private final Int2ObjectMap<BakedGlyph> glyphs = new Int2ObjectOpenHashMap<>();
   private final Int2ObjectMap<GlyphInfo> glyphInfos = new Int2ObjectOpenHashMap<>();
   private final Int2ObjectMap<IntList> glyphsByWidth = new Int2ObjectOpenHashMap<>();
   private final List<FontTexture> textures = Lists.<FontTexture>newArrayList();

   public FontSet(TextureManager var1, ResourceLocation var2) {
      this.textureManager = â˜ƒ;
      this.name = â˜ƒ;
   }

   public void reload(List<GlyphProvider> var1) {
      this.closeProviders();
      this.closeTextures();
      this.glyphs.clear();
      this.glyphInfos.clear();
      this.glyphsByWidth.clear();
      this.missingGlyph = this.stitch(MissingGlyph.INSTANCE);
      this.whiteGlyph = this.stitch(WhiteGlyph.INSTANCE);
      IntSet â˜ƒ = new IntOpenHashSet();

      for(GlyphProvider â˜ƒx : â˜ƒ) {
         â˜ƒ.addAll(â˜ƒx.getSupportedGlyphs());
      }

      Set<GlyphProvider> â˜ƒx = Sets.<GlyphProvider>newHashSet();
      â˜ƒ.forEach(var3 -> {
         for(GlyphProvider â˜ƒ : â˜ƒ) {
            GlyphInfo â˜ƒx = (GlyphInfo)(var3 == 32 ? SPACE_INFO : â˜ƒ.getGlyph(var3));
            if (â˜ƒx != null) {
               â˜ƒ.add(â˜ƒ);
               if (â˜ƒx != MissingGlyph.INSTANCE) {
                  this.glyphsByWidth.computeIfAbsent(Mth.ceil(â˜ƒx.getAdvance(false)), var0 -> new IntArrayList()).add(var3);
               }
               break;
            }
         }
      });
      â˜ƒ.stream().filter(â˜ƒx::contains).forEach(this.providers::add);
   }

   public void close() {
      this.closeProviders();
      this.closeTextures();
   }

   private void closeProviders() {
      for(GlyphProvider â˜ƒ : this.providers) {
         â˜ƒ.close();
      }

      this.providers.clear();
   }

   private void closeTextures() {
      for(FontTexture â˜ƒ : this.textures) {
         â˜ƒ.close();
      }

      this.textures.clear();
   }

   public GlyphInfo getGlyphInfo(int var1) {
      return this.glyphInfos.computeIfAbsent(â˜ƒ, var1x -> (GlyphInfo)(var1x == 32 ? SPACE_INFO : this.getRaw(var1x)));
   }

   private RawGlyph getRaw(int var1) {
      for(GlyphProvider â˜ƒ : this.providers) {
         RawGlyph â˜ƒx = â˜ƒ.getGlyph(â˜ƒ);
         if (â˜ƒx != null) {
            return â˜ƒx;
         }
      }

      return MissingGlyph.INSTANCE;
   }

   public BakedGlyph getGlyph(int var1) {
      return this.glyphs.computeIfAbsent(â˜ƒ, var1x -> (BakedGlyph)(var1x == 32 ? SPACE_GLYPH : this.stitch(this.getRaw(var1x))));
   }

   private BakedGlyph stitch(RawGlyph var1) {
      for(FontTexture â˜ƒ : this.textures) {
         BakedGlyph â˜ƒx = â˜ƒ.add(â˜ƒ);
         if (â˜ƒx != null) {
            return â˜ƒx;
         }
      }

      FontTexture â˜ƒ = new FontTexture(new ResourceLocation(this.name.getNamespace(), this.name.getPath() + "/" + this.textures.size()), â˜ƒ.isColored());
      this.textures.add(â˜ƒ);
      this.textureManager.register(â˜ƒ.getName(), â˜ƒ);
      BakedGlyph â˜ƒx = â˜ƒ.add(â˜ƒ);
      return â˜ƒx == null ? this.missingGlyph : â˜ƒx;
   }

   public BakedGlyph getRandomGlyph(GlyphInfo var1) {
      IntList â˜ƒ = this.glyphsByWidth.get(Mth.ceil(â˜ƒ.getAdvance(false)));
      return â˜ƒ != null && !â˜ƒ.isEmpty() ? this.getGlyph(â˜ƒ.getInt(RANDOM.nextInt(â˜ƒ.size()))) : this.missingGlyph;
   }

   public BakedGlyph whiteGlyph() {
      return this.whiteGlyph;
   }
}
