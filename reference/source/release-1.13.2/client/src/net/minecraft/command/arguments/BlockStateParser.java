package net.minecraft.command.arguments;

import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.text.TextComponentTranslation;

public class BlockStateParser {
   public static final SimpleCommandExceptionType field_199831_a = new SimpleCommandExceptionType(new TextComponentTranslation("argument.block.tag.disallowed"));
   public static final DynamicCommandExceptionType field_197259_a = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("argument.block.id.invalid", var0)
   );
   public static final Dynamic2CommandExceptionType field_197260_b = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TextComponentTranslation("argument.block.property.unknown", var0, var1)
   );
   public static final Dynamic2CommandExceptionType field_197261_c = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TextComponentTranslation("argument.block.property.duplicate", var1, var0)
   );
   public static final Dynamic3CommandExceptionType field_197262_d = new Dynamic3CommandExceptionType(
      (var0, var1, var2) -> new TextComponentTranslation("argument.block.property.invalid", var0, var2, var1)
   );
   public static final Dynamic2CommandExceptionType field_197263_e = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TextComponentTranslation("argument.block.property.novalue", var0, var1)
   );
   public static final SimpleCommandExceptionType field_197264_f = new SimpleCommandExceptionType(
      new TextComponentTranslation("argument.block.property.unclosed")
   );
   private static final Function<SuggestionsBuilder, CompletableFuture<Suggestions>> field_197265_g = SuggestionsBuilder::buildFuture;
   private final StringReader field_197266_h;
   private final boolean field_199832_j;
   private final Map<IProperty<?>, Comparable<?>> field_197267_i = Maps.newHashMap();
   private final Map<String, String> field_200141_l = Maps.newHashMap();
   private ResourceLocation field_197268_j = new ResourceLocation("");
   private StateContainer<Block, IBlockState> field_197269_k;
   private IBlockState field_197270_l;
   @Nullable
   private NBTTagCompound field_197271_m;
   private ResourceLocation field_199833_p = new ResourceLocation("");
   private int field_201954_r;
   private Function<SuggestionsBuilder, CompletableFuture<Suggestions>> field_197272_n = field_197265_g;

   public BlockStateParser(StringReader var1, boolean var2) {
      this.field_197266_h = ☃;
      this.field_199832_j = ☃;
   }

   public Map<IProperty<?>, Comparable<?>> func_197254_a() {
      return this.field_197267_i;
   }

   @Nullable
   public IBlockState func_197249_b() {
      return this.field_197270_l;
   }

   @Nullable
   public NBTTagCompound func_197241_c() {
      return this.field_197271_m;
   }

   @Nullable
   public ResourceLocation func_199829_d() {
      return this.field_199833_p;
   }

   public BlockStateParser func_197243_a(boolean var1) throws CommandSyntaxException {
      this.field_197272_n = this::func_197250_h;
      if (this.field_197266_h.canRead() && this.field_197266_h.peek() == '#') {
         this.func_199827_f();
         this.field_197272_n = this::func_212599_i;
         if (this.field_197266_h.canRead() && this.field_197266_h.peek() == '[') {
            this.func_200137_h();
            this.field_197272_n = this::func_197244_d;
         }
      } else {
         this.func_197258_d();
         this.field_197272_n = this::func_197255_g;
         if (this.field_197266_h.canRead() && this.field_197266_h.peek() == '[') {
            this.func_197257_e();
            this.field_197272_n = this::func_197244_d;
         }
      }

      if (☃ && this.field_197266_h.canRead() && this.field_197266_h.peek() == '{') {
         this.field_197272_n = field_197265_g;
         this.func_197240_f();
      }

      return this;
   }

   private CompletableFuture<Suggestions> func_197252_b(SuggestionsBuilder var1) {
      if (☃.getRemaining().isEmpty()) {
         ☃.suggest(String.valueOf(']'));
      }

      return this.func_197256_c(☃);
   }

   private CompletableFuture<Suggestions> func_200136_c(SuggestionsBuilder var1) {
      if (☃.getRemaining().isEmpty()) {
         ☃.suggest(String.valueOf(']'));
      }

      return this.func_200134_e(☃);
   }

   private CompletableFuture<Suggestions> func_197256_c(SuggestionsBuilder var1) {
      String ☃ = ☃.getRemaining().toLowerCase(Locale.ROOT);

      for(IProperty<?> ☃x : this.field_197270_l.func_206869_a()) {
         if (!this.field_197267_i.containsKey(☃x) && ☃x.func_177701_a().startsWith(☃)) {
            ☃.suggest(☃x.func_177701_a() + '=');
         }
      }

      return ☃.buildFuture();
   }

   private CompletableFuture<Suggestions> func_200134_e(SuggestionsBuilder var1) {
      String ☃ = ☃.getRemaining().toLowerCase(Locale.ROOT);
      if (this.field_199833_p != null && !this.field_199833_p.func_110623_a().isEmpty()) {
         Tag<Block> ☃x = BlockTags.func_199896_a().func_199910_a(this.field_199833_p);
         if (☃x != null) {
            for(Block ☃xx : ☃x.func_199885_a()) {
               for(IProperty<?> ☃xxx : ☃xx.func_176194_O().func_177623_d()) {
                  if (!this.field_200141_l.containsKey(☃xxx.func_177701_a()) && ☃xxx.func_177701_a().startsWith(☃)) {
                     ☃.suggest(☃xxx.func_177701_a() + '=');
                  }
               }
            }
         }
      }

      return ☃.buildFuture();
   }

   private CompletableFuture<Suggestions> func_197244_d(SuggestionsBuilder var1) {
      if (☃.getRemaining().isEmpty() && this.func_212598_k()) {
         ☃.suggest(String.valueOf('{'));
      }

      return ☃.buildFuture();
   }

   private boolean func_212598_k() {
      if (this.field_197270_l != null) {
         return this.field_197270_l.func_177230_c().func_149716_u();
      } else {
         if (this.field_199833_p != null) {
            Tag<Block> ☃ = BlockTags.func_199896_a().func_199910_a(this.field_199833_p);
            if (☃ != null) {
               for(Block ☃x : ☃.func_199885_a()) {
                  if (☃x.func_149716_u()) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   private CompletableFuture<Suggestions> func_197246_e(SuggestionsBuilder var1) {
      if (☃.getRemaining().isEmpty()) {
         ☃.suggest(String.valueOf('='));
      }

      return ☃.buildFuture();
   }

   private CompletableFuture<Suggestions> func_197248_f(SuggestionsBuilder var1) {
      if (☃.getRemaining().isEmpty()) {
         ☃.suggest(String.valueOf(']'));
      }

      if (☃.getRemaining().isEmpty() && this.field_197267_i.size() < this.field_197270_l.func_206869_a().size()) {
         ☃.suggest(String.valueOf(','));
      }

      return ☃.buildFuture();
   }

   private static <T extends Comparable<T>> SuggestionsBuilder func_201037_a(SuggestionsBuilder var0, IProperty<T> var1) {
      for(T ☃ : ☃.func_177700_c()) {
         if (☃ instanceof Integer) {
            ☃.suggest((Integer)☃);
         } else {
            ☃.suggest(☃.func_177702_a(☃));
         }
      }

      return ☃;
   }

   private CompletableFuture<Suggestions> func_200140_a(SuggestionsBuilder var1, String var2) {
      boolean ☃ = false;
      if (this.field_199833_p != null && !this.field_199833_p.func_110623_a().isEmpty()) {
         Tag<Block> ☃x = BlockTags.func_199896_a().func_199910_a(this.field_199833_p);
         if (☃x != null) {
            for(Block ☃xx : ☃x.func_199885_a()) {
               IProperty<?> ☃xxx = ☃xx.func_176194_O().func_185920_a(☃);
               if (☃xxx != null) {
                  func_201037_a(☃, ☃xxx);
               }

               if (!☃) {
                  for(IProperty<?> ☃xxx : ☃xx.func_176194_O().func_177623_d()) {
                     if (!this.field_200141_l.containsKey(☃xxx.func_177701_a())) {
                        ☃ = true;
                        break;
                     }
                  }
               }
            }
         }
      }

      if (☃) {
         ☃.suggest(String.valueOf(','));
      }

      ☃.suggest(String.valueOf(']'));
      return ☃.buildFuture();
   }

   private CompletableFuture<Suggestions> func_212599_i(SuggestionsBuilder var1) {
      if (☃.getRemaining().isEmpty()) {
         Tag<Block> ☃ = BlockTags.func_199896_a().func_199910_a(this.field_199833_p);
         if (☃ != null) {
            boolean ☃x = false;
            boolean ☃xx = false;

            for(Block ☃xxx : ☃.func_199885_a()) {
               ☃x |= !☃xxx.func_176194_O().func_177623_d().isEmpty();
               ☃xx |= ☃xxx.func_149716_u();
               if (☃x && ☃xx) {
                  break;
               }
            }

            if (☃x) {
               ☃.suggest(String.valueOf('['));
            }

            if (☃xx) {
               ☃.suggest(String.valueOf('{'));
            }
         }
      }

      return this.func_201953_j(☃);
   }

   private CompletableFuture<Suggestions> func_197255_g(SuggestionsBuilder var1) {
      if (☃.getRemaining().isEmpty()) {
         if (!this.field_197270_l.func_177230_c().func_176194_O().func_177623_d().isEmpty()) {
            ☃.suggest(String.valueOf('['));
         }

         if (this.field_197270_l.func_177230_c().func_149716_u()) {
            ☃.suggest(String.valueOf('{'));
         }
      }

      return ☃.buildFuture();
   }

   private CompletableFuture<Suggestions> func_201953_j(SuggestionsBuilder var1) {
      return ISuggestionProvider.func_197014_a(BlockTags.func_199896_a().func_199908_a(), ☃.createOffset(this.field_201954_r).add(☃));
   }

   private CompletableFuture<Suggestions> func_197250_h(SuggestionsBuilder var1) {
      if (this.field_199832_j) {
         ISuggestionProvider.func_197006_a(BlockTags.func_199896_a().func_199908_a(), ☃, String.valueOf('#'));
      }

      ISuggestionProvider.func_197014_a(IRegistry.field_212618_g.func_148742_b(), ☃);
      return ☃.buildFuture();
   }

   public void func_197258_d() throws CommandSyntaxException {
      int ☃ = this.field_197266_h.getCursor();
      this.field_197268_j = ResourceLocation.func_195826_a(this.field_197266_h);
      if (IRegistry.field_212618_g.func_212607_c(this.field_197268_j)) {
         Block ☃x = IRegistry.field_212618_g.func_82594_a(this.field_197268_j);
         this.field_197269_k = ☃x.func_176194_O();
         this.field_197270_l = ☃x.func_176223_P();
      } else {
         this.field_197266_h.setCursor(☃);
         throw field_197259_a.createWithContext(this.field_197266_h, this.field_197268_j.toString());
      }
   }

   public void func_199827_f() throws CommandSyntaxException {
      if (!this.field_199832_j) {
         throw field_199831_a.create();
      } else {
         this.field_197272_n = this::func_201953_j;
         this.field_197266_h.expect('#');
         this.field_201954_r = this.field_197266_h.getCursor();
         this.field_199833_p = ResourceLocation.func_195826_a(this.field_197266_h);
      }
   }

   public void func_197257_e() throws CommandSyntaxException {
      this.field_197266_h.skip();
      this.field_197272_n = this::func_197252_b;
      this.field_197266_h.skipWhitespace();

      while(this.field_197266_h.canRead() && this.field_197266_h.peek() != ']') {
         this.field_197266_h.skipWhitespace();
         int ☃ = this.field_197266_h.getCursor();
         String ☃x = this.field_197266_h.readString();
         IProperty<?> ☃xx = this.field_197269_k.func_185920_a(☃x);
         if (☃xx == null) {
            this.field_197266_h.setCursor(☃);
            throw field_197260_b.createWithContext(this.field_197266_h, this.field_197268_j.toString(), ☃x);
         }

         if (this.field_197267_i.containsKey(☃xx)) {
            this.field_197266_h.setCursor(☃);
            throw field_197261_c.createWithContext(this.field_197266_h, this.field_197268_j.toString(), ☃x);
         }

         this.field_197266_h.skipWhitespace();
         this.field_197272_n = this::func_197246_e;
         if (!this.field_197266_h.canRead() || this.field_197266_h.peek() != '=') {
            throw field_197263_e.createWithContext(this.field_197266_h, this.field_197268_j.toString(), ☃x);
         }

         this.field_197266_h.skip();
         this.field_197266_h.skipWhitespace();
         this.field_197272_n = var1x -> func_201037_a(var1x, ☃).buildFuture();
         int ☃ = this.field_197266_h.getCursor();
         this.func_197253_a(☃xx, this.field_197266_h.readString(), ☃);
         this.field_197272_n = this::func_197248_f;
         this.field_197266_h.skipWhitespace();
         if (this.field_197266_h.canRead()) {
            if (this.field_197266_h.peek() != ',') {
               if (this.field_197266_h.peek() != ']') {
                  throw field_197264_f.createWithContext(this.field_197266_h);
               }
               break;
            }

            this.field_197266_h.skip();
            this.field_197272_n = this::func_197256_c;
         }
      }

      if (this.field_197266_h.canRead()) {
         this.field_197266_h.skip();
      } else {
         throw field_197264_f.createWithContext(this.field_197266_h);
      }
   }

   public void func_200137_h() throws CommandSyntaxException {
      this.field_197266_h.skip();
      this.field_197272_n = this::func_200136_c;
      int ☃ = -1;
      this.field_197266_h.skipWhitespace();

      while(this.field_197266_h.canRead() && this.field_197266_h.peek() != ']') {
         this.field_197266_h.skipWhitespace();
         int ☃x = this.field_197266_h.getCursor();
         String ☃xx = this.field_197266_h.readString();
         if (this.field_200141_l.containsKey(☃xx)) {
            this.field_197266_h.setCursor(☃x);
            throw field_197261_c.createWithContext(this.field_197266_h, this.field_197268_j.toString(), ☃xx);
         }

         this.field_197266_h.skipWhitespace();
         if (!this.field_197266_h.canRead() || this.field_197266_h.peek() != '=') {
            this.field_197266_h.setCursor(☃x);
            throw field_197263_e.createWithContext(this.field_197266_h, this.field_197268_j.toString(), ☃xx);
         }

         this.field_197266_h.skip();
         this.field_197266_h.skipWhitespace();
         this.field_197272_n = var2x -> this.func_200140_a(var2x, ☃);
         ☃ = this.field_197266_h.getCursor();
         String ☃x = this.field_197266_h.readString();
         this.field_200141_l.put(☃xx, ☃x);
         this.field_197266_h.skipWhitespace();
         if (this.field_197266_h.canRead()) {
            ☃ = -1;
            if (this.field_197266_h.peek() != ',') {
               if (this.field_197266_h.peek() != ']') {
                  throw field_197264_f.createWithContext(this.field_197266_h);
               }
               break;
            }

            this.field_197266_h.skip();
            this.field_197272_n = this::func_200134_e;
         }
      }

      if (this.field_197266_h.canRead()) {
         this.field_197266_h.skip();
      } else {
         if (☃ >= 0) {
            this.field_197266_h.setCursor(☃);
         }

         throw field_197264_f.createWithContext(this.field_197266_h);
      }
   }

   public void func_197240_f() throws CommandSyntaxException {
      this.field_197271_m = new JsonToNBT(this.field_197266_h).func_193593_f();
   }

   private <T extends Comparable<T>> void func_197253_a(IProperty<T> var1, String var2, int var3) throws CommandSyntaxException {
      Optional<T> ☃ = ☃.func_185929_b(☃);
      if (☃.isPresent()) {
         this.field_197270_l = this.field_197270_l.func_206870_a(☃, (Comparable)☃.get());
         this.field_197267_i.put(☃, ☃.get());
      } else {
         this.field_197266_h.setCursor(☃);
         throw field_197262_d.createWithContext(this.field_197266_h, this.field_197268_j.toString(), ☃.func_177701_a(), ☃);
      }
   }

   public static String func_197247_a(IBlockState var0, @Nullable NBTTagCompound var1) {
      StringBuilder ☃ = new StringBuilder(IRegistry.field_212618_g.func_177774_c(☃.func_177230_c()).toString());
      if (!☃.func_206869_a().isEmpty()) {
         ☃.append('[');
         boolean ☃x = false;

         for(Entry<IProperty<?>, Comparable<?>> ☃xx : ☃.func_206871_b().entrySet()) {
            if (☃x) {
               ☃.append(',');
            }

            func_211375_a(☃, (IProperty)☃xx.getKey(), (Comparable<?>)☃xx.getValue());
            ☃x = true;
         }

         ☃.append(']');
      }

      if (☃ != null) {
         ☃.append(☃);
      }

      return ☃.toString();
   }

   private static <T extends Comparable<T>> void func_211375_a(StringBuilder var0, IProperty<T> var1, Comparable<?> var2) {
      ☃.append(☃.func_177701_a());
      ☃.append('=');
      ☃.append(☃.func_177702_a((T)☃));
   }

   public CompletableFuture<Suggestions> func_197245_a(SuggestionsBuilder var1) {
      return (CompletableFuture<Suggestions>)this.field_197272_n.apply(☃.createOffset(this.field_197266_h.getCursor()));
   }

   public Map<String, String> func_200139_j() {
      return this.field_200141_l;
   }
}
