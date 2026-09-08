package net.minecraft.client.gui.fonts;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.chars.CharArrayList;
import it.unimi.dsi.fastutil.chars.CharList;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.client.gui.fonts.providers.IGlyphProvider;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Font implements AutoCloseable {
   private static final Logger field_211189_a = LogManager.getLogger();
   private static final EmptyGlyph field_212460_b = new EmptyGlyph();
   private static final IGlyph field_212461_c = () -> 4.0F;
   private static final Random field_212462_d = new Random();
   private final TextureManager field_211191_c;
   private final ResourceLocation field_211192_d;
   private TexturedGlyph field_211572_d;
   private final List<IGlyphProvider> field_211194_f = Lists.<IGlyphProvider>newArrayList();
   private final Char2ObjectMap<TexturedGlyph> field_212463_j = new Char2ObjectOpenHashMap<>();
   private final Char2ObjectMap<IGlyph> field_211195_g = new Char2ObjectOpenHashMap<>();
   private final Int2ObjectMap<CharList> field_211196_h = new Int2ObjectOpenHashMap<>();
   private final List<FontTexture> field_211197_i = Lists.<FontTexture>newArrayList();

   public Font(TextureManager var1, ResourceLocation var2) {
      this.field_211191_c = ☃;
      this.field_211192_d = ☃;
   }

   public void func_211570_a(List<IGlyphProvider> var1) {
      for(IGlyphProvider ☃ : this.field_211194_f) {
         ☃.close();
      }

      this.field_211194_f.clear();
      this.func_211571_a();
      this.field_211197_i.clear();
      this.field_212463_j.clear();
      this.field_211195_g.clear();
      this.field_211196_h.clear();
      this.field_211572_d = this.func_211185_a(DefaultGlyph.INSTANCE);
      Set<IGlyphProvider> ☃ = Sets.<IGlyphProvider>newHashSet();

      for(char ☃x = 0; ☃x < '\uffff'; ++☃x) {
         for(IGlyphProvider ☃xx : ☃) {
            IGlyph ☃xxx = (IGlyph)(☃x == ' ' ? field_212461_c : ☃xx.func_212248_a(☃x));
            if (☃xxx != null) {
               ☃.add(☃xx);
               if (☃xxx != DefaultGlyph.INSTANCE) {
                  this.field_211196_h.computeIfAbsent(MathHelper.func_76123_f(☃xxx.getAdvance(false)), var0 -> new CharArrayList()).add(☃x);
               }
               break;
            }
         }
      }

      ☃.stream().filter(☃::contains).forEach(this.field_211194_f::add);
   }

   public void close() {
      this.func_211571_a();
   }

   public void func_211571_a() {
      for(FontTexture ☃ : this.field_211197_i) {
         ☃.close();
      }
   }

   public IGlyph func_211184_b(char var1) {
      return this.field_211195_g.computeIfAbsent(☃, var1x -> (IGlyph)(var1x == 32 ? field_212461_c : this.func_212455_c((char)var1x)));
   }

   private IGlyphInfo func_212455_c(char var1) {
      for(IGlyphProvider ☃ : this.field_211194_f) {
         IGlyphInfo ☃x = ☃.func_212248_a(☃);
         if (☃x != null) {
            return ☃x;
         }
      }

      return DefaultGlyph.INSTANCE;
   }

   public TexturedGlyph func_211187_a(char var1) {
      return this.field_212463_j
         .computeIfAbsent(☃, var1x -> (TexturedGlyph)(var1x == 32 ? field_212460_b : this.func_211185_a(this.func_212455_c((char)var1x))));
   }

   private TexturedGlyph func_211185_a(IGlyphInfo var1) {
      for(FontTexture ☃ : this.field_211197_i) {
         TexturedGlyph ☃x = ☃.func_211131_a(☃);
         if (☃x != null) {
            return ☃x;
         }
      }

      FontTexture ☃ = new FontTexture(
         new ResourceLocation(this.field_211192_d.func_110624_b(), this.field_211192_d.func_110623_a() + "/" + this.field_211197_i.size()), ☃.func_211579_f()
      );
      this.field_211197_i.add(☃);
      this.field_211191_c.func_110579_a(☃.func_211132_a(), ☃);
      TexturedGlyph ☃x = ☃.func_211131_a(☃);
      return ☃x == null ? this.field_211572_d : ☃x;
   }

   public TexturedGlyph func_211188_a(IGlyph var1) {
      CharList ☃ = this.field_211196_h.get(MathHelper.func_76123_f(☃.getAdvance(false)));
      return ☃ != null && !☃.isEmpty() ? this.func_211187_a(☃.get(field_212462_d.nextInt(☃.size()))) : this.field_211572_d;
   }
}
