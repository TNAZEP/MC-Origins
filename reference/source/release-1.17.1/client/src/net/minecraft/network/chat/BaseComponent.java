package net.minecraft.network.chat;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.locale.Language;
import net.minecraft.util.FormattedCharSequence;

public abstract class BaseComponent implements MutableComponent {
   protected final List<Component> siblings = Lists.<Component>newArrayList();
   private FormattedCharSequence visualOrderText = FormattedCharSequence.EMPTY;
   @Nullable
   private Language decomposedWith;
   private Style style = Style.EMPTY;

   @Override
   public MutableComponent append(Component var1) {
      this.siblings.add(â˜ƒ);
      return this;
   }

   @Override
   public String getContents() {
      return "";
   }

   @Override
   public List<Component> getSiblings() {
      return this.siblings;
   }

   @Override
   public MutableComponent setStyle(Style var1) {
      this.style = â˜ƒ;
      return this;
   }

   @Override
   public Style getStyle() {
      return this.style;
   }

   public abstract BaseComponent plainCopy();

   @Override
   public final MutableComponent copy() {
      BaseComponent â˜ƒ = this.plainCopy();
      â˜ƒ.siblings.addAll(this.siblings);
      â˜ƒ.setStyle(this.style);
      return â˜ƒ;
   }

   @Override
   public FormattedCharSequence getVisualOrderText() {
      Language â˜ƒ = Language.getInstance();
      if (this.decomposedWith != â˜ƒ) {
         this.visualOrderText = â˜ƒ.getVisualOrder(this);
         this.decomposedWith = â˜ƒ;
      }

      return this.visualOrderText;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (!(â˜ƒ instanceof BaseComponent)) {
         return false;
      } else {
         BaseComponent â˜ƒ = (BaseComponent)â˜ƒ;
         return this.siblings.equals(â˜ƒ.siblings) && Objects.equals(this.getStyle(), â˜ƒ.getStyle());
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.getStyle(), this.siblings});
   }

   public String toString() {
      return "BaseComponent{style=" + this.style + ", siblings=" + this.siblings + "}";
   }
}
