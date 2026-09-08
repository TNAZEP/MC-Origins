package net.minecraft.util.text;

import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class TextComponentBase implements ITextComponent {
   protected List<ITextComponent> field_150264_a = Lists.<ITextComponent>newArrayList();
   private Style field_150263_b;

   @Override
   public ITextComponent func_150257_a(ITextComponent var1) {
      ☃.func_150256_b().func_150221_a(this.func_150256_b());
      this.field_150264_a.add(☃);
      return this;
   }

   @Override
   public List<ITextComponent> func_150253_a() {
      return this.field_150264_a;
   }

   @Override
   public ITextComponent func_150255_a(Style var1) {
      this.field_150263_b = ☃;

      for(ITextComponent ☃ : this.field_150264_a) {
         ☃.func_150256_b().func_150221_a(this.func_150256_b());
      }

      return this;
   }

   @Override
   public Style func_150256_b() {
      if (this.field_150263_b == null) {
         this.field_150263_b = new Style();

         for(ITextComponent ☃ : this.field_150264_a) {
            ☃.func_150256_b().func_150221_a(this.field_150263_b);
         }
      }

      return this.field_150263_b;
   }

   @Override
   public Stream<ITextComponent> func_212640_c() {
      return Streams.concat(Stream.of(this), this.field_150264_a.stream().flatMap(ITextComponent::func_212640_c));
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (!(☃ instanceof TextComponentBase)) {
         return false;
      } else {
         TextComponentBase ☃ = (TextComponentBase)☃;
         return this.field_150264_a.equals(☃.field_150264_a) && this.func_150256_b().equals(☃.func_150256_b());
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.func_150256_b(), this.field_150264_a});
   }

   public String toString() {
      return "BaseComponent{style=" + this.field_150263_b + ", siblings=" + this.field_150264_a + '}';
   }
}
