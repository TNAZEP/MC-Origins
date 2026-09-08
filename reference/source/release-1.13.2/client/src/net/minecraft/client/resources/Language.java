package net.minecraft.client.resources;

public class Language implements Comparable<Language> {
   private final String field_135039_a;
   private final String field_135037_b;
   private final String field_135038_c;
   private final boolean field_135036_d;

   public Language(String var1, String var2, String var3, boolean var4) {
      this.field_135039_a = ☃;
      this.field_135037_b = ☃;
      this.field_135038_c = ☃;
      this.field_135036_d = ☃;
   }

   public String func_135034_a() {
      return this.field_135039_a;
   }

   public boolean func_135035_b() {
      return this.field_135036_d;
   }

   public String toString() {
      return String.format("%s (%s)", this.field_135038_c, this.field_135037_b);
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else {
         return !(☃ instanceof Language) ? false : this.field_135039_a.equals(((Language)☃).field_135039_a);
      }
   }

   public int hashCode() {
      return this.field_135039_a.hashCode();
   }

   public int compareTo(Language var1) {
      return this.field_135039_a.compareTo(☃.field_135039_a);
   }
}
