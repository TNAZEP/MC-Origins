package net.minecraft.util.text;

public class TextComponentString extends TextComponentBase {
   private final String field_150267_b;

   public TextComponentString(String var1) {
      this.field_150267_b = ☃;
   }

   public String func_150265_g() {
      return this.field_150267_b;
   }

   @Override
   public String func_150261_e() {
      return this.field_150267_b;
   }

   public TextComponentString func_150259_f() {
      return new TextComponentString(this.field_150267_b);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (!(☃ instanceof TextComponentString)) {
         return false;
      } else {
         TextComponentString ☃ = (TextComponentString)☃;
         return this.field_150267_b.equals(☃.func_150265_g()) && super.equals(☃);
      }
   }

   @Override
   public String toString() {
      return "TextComponent{text='" + this.field_150267_b + '\'' + ", siblings=" + this.field_150264_a + ", style=" + this.func_150256_b() + '}';
   }
}
