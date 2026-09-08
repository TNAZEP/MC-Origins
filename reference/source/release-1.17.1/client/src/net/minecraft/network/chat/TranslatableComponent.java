package net.minecraft.network.chat;

import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.locale.Language;
import net.minecraft.world.entity.Entity;

public class TranslatableComponent extends BaseComponent implements ContextAwareComponent {
   private static final Object[] NO_ARGS = new Object[0];
   private static final FormattedText TEXT_PERCENT = FormattedText.of("%");
   private static final FormattedText TEXT_NULL = FormattedText.of("null");
   private final String key;
   private final Object[] args;
   @Nullable
   private Language decomposedWith;
   private final List<FormattedText> decomposedParts = Lists.<FormattedText>newArrayList();
   private static final Pattern FORMAT_PATTERN = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");

   public TranslatableComponent(String var1) {
      this.key = â˜ƒ;
      this.args = NO_ARGS;
   }

   public TranslatableComponent(String var1, Object... var2) {
      this.key = â˜ƒ;
      this.args = â˜ƒ;
   }

   private void decompose() {
      Language â˜ƒ = Language.getInstance();
      if (â˜ƒ != this.decomposedWith) {
         this.decomposedWith = â˜ƒ;
         this.decomposedParts.clear();
         String â˜ƒx = â˜ƒ.getOrDefault(this.key);

         try {
            this.decomposeTemplate(â˜ƒx);
         } catch (TranslatableFormatException var4) {
            this.decomposedParts.clear();
            this.decomposedParts.add(FormattedText.of(â˜ƒx));
         }
      }
   }

   private void decomposeTemplate(String var1) {
      Matcher â˜ƒ = FORMAT_PATTERN.matcher(â˜ƒ);

      try {
         int â˜ƒx = 0;

         int â˜ƒ;
         int â˜ƒ;
         for(â˜ƒ = 0; â˜ƒ.find(â˜ƒ); â˜ƒ = â˜ƒ) {
            int â˜ƒxx = â˜ƒ.start();
            â˜ƒ = â˜ƒ.end();
            if (â˜ƒxx > â˜ƒ) {
               String â˜ƒxxx = â˜ƒ.substring(â˜ƒ, â˜ƒxx);
               if (â˜ƒxxx.indexOf(37) != -1) {
                  throw new IllegalArgumentException();
               }

               this.decomposedParts.add(FormattedText.of(â˜ƒxxx));
            }

            String â˜ƒxx = â˜ƒ.group(2);
            String â˜ƒxxx = â˜ƒ.substring(â˜ƒxx, â˜ƒ);
            if ("%".equals(â˜ƒxx) && "%%".equals(â˜ƒxxx)) {
               this.decomposedParts.add(TEXT_PERCENT);
            } else {
               if (!"s".equals(â˜ƒxx)) {
                  throw new TranslatableFormatException(this, "Unsupported format: '" + â˜ƒxxx + "'");
               }

               String â˜ƒxx = â˜ƒ.group(1);
               int â˜ƒxxx = â˜ƒxx != null ? Integer.parseInt(â˜ƒxx) - 1 : â˜ƒx++;
               if (â˜ƒxxx < this.args.length) {
                  this.decomposedParts.add(this.getArgument(â˜ƒxxx));
               }
            }
         }

         if (â˜ƒ < â˜ƒ.length()) {
            String â˜ƒxx = â˜ƒ.substring(â˜ƒ);
            if (â˜ƒxx.indexOf(37) != -1) {
               throw new IllegalArgumentException();
            }

            this.decomposedParts.add(FormattedText.of(â˜ƒxx));
         }
      } catch (IllegalArgumentException var11) {
         throw new TranslatableFormatException(this, var11);
      }
   }

   private FormattedText getArgument(int var1) {
      if (â˜ƒ >= this.args.length) {
         throw new TranslatableFormatException(this, â˜ƒ);
      } else {
         Object â˜ƒ = this.args[â˜ƒ];
         if (â˜ƒ instanceof Component) {
            return (Component)â˜ƒ;
         } else {
            return â˜ƒ == null ? TEXT_NULL : FormattedText.of(â˜ƒ.toString());
         }
      }
   }

   public TranslatableComponent plainCopy() {
      return new TranslatableComponent(this.key, this.args);
   }

   @Override
   public <T> Optional<T> visitSelf(FormattedText.StyledContentConsumer<T> var1, Style var2) {
      this.decompose();

      for(FormattedText â˜ƒ : this.decomposedParts) {
         Optional<T> â˜ƒx = â˜ƒ.visit(â˜ƒ, â˜ƒ);
         if (â˜ƒx.isPresent()) {
            return â˜ƒx;
         }
      }

      return Optional.empty();
   }

   @Override
   public <T> Optional<T> visitSelf(FormattedText.ContentConsumer<T> var1) {
      this.decompose();

      for(FormattedText â˜ƒ : this.decomposedParts) {
         Optional<T> â˜ƒx = â˜ƒ.visit(â˜ƒ);
         if (â˜ƒx.isPresent()) {
            return â˜ƒx;
         }
      }

      return Optional.empty();
   }

   @Override
   public MutableComponent resolve(@Nullable CommandSourceStack var1, @Nullable Entity var2, int var3) throws CommandSyntaxException {
      Object[] â˜ƒ = new Object[this.args.length];

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.length; ++â˜ƒx) {
         Object â˜ƒxx = this.args[â˜ƒx];
         if (â˜ƒxx instanceof Component) {
            â˜ƒ[â˜ƒx] = ComponentUtils.updateForEntity(â˜ƒ, (Component)â˜ƒxx, â˜ƒ, â˜ƒ);
         } else {
            â˜ƒ[â˜ƒx] = â˜ƒxx;
         }
      }

      return new TranslatableComponent(this.key, â˜ƒ);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (!(â˜ƒ instanceof TranslatableComponent)) {
         return false;
      } else {
         TranslatableComponent â˜ƒ = (TranslatableComponent)â˜ƒ;
         return Arrays.equals(this.args, â˜ƒ.args) && this.key.equals(â˜ƒ.key) && super.equals(â˜ƒ);
      }
   }

   @Override
   public int hashCode() {
      int â˜ƒ = super.hashCode();
      â˜ƒ = 31 * â˜ƒ + this.key.hashCode();
      return 31 * â˜ƒ + Arrays.hashCode(this.args);
   }

   @Override
   public String toString() {
      return "TranslatableComponent{key='"
         + this.key
         + "', args="
         + Arrays.toString(this.args)
         + ", siblings="
         + this.siblings
         + ", style="
         + this.getStyle()
         + "}";
   }

   public String getKey() {
      return this.key;
   }

   public Object[] getArgs() {
      return this.args;
   }
}
