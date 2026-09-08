package net.minecraft.network.chat;

public class TextComponent extends BaseComponent {
   public static final Component EMPTY = new TextComponent("");
   private final String text;

   public TextComponent(String var1) {
      this.text = â˜ƒ;
   }

   public String getText() {
      return this.text;
   }

   @Override
   public String getContents() {
      return this.text;
   }

   public TextComponent plainCopy() {
      return new TextComponent(this.text);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (!(â˜ƒ instanceof TextComponent)) {
         return false;
      } else {
         TextComponent â˜ƒ = (TextComponent)â˜ƒ;
         return this.text.equals(â˜ƒ.getText()) && super.equals(â˜ƒ);
      }
   }

   @Override
   public String toString() {
      return "TextComponent{text='" + this.text + "', siblings=" + this.siblings + ", style=" + this.getStyle() + "}";
   }
}
