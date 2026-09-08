package net.minecraft.network.chat;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import net.minecraft.util.Unit;

public interface FormattedText {
   Optional<Unit> STOP_ITERATION = Optional.of(Unit.INSTANCE);
   FormattedText EMPTY = new FormattedText() {
      @Override
      public <T> Optional<T> visit(FormattedText.ContentConsumer<T> var1) {
         return Optional.empty();
      }

      @Override
      public <T> Optional<T> visit(FormattedText.StyledContentConsumer<T> var1, Style var2) {
         return Optional.empty();
      }
   };

   <T> Optional<T> visit(FormattedText.ContentConsumer<T> var1);

   <T> Optional<T> visit(FormattedText.StyledContentConsumer<T> var1, Style var2);

   static FormattedText of(final String var0) {
      return new FormattedText() {
         @Override
         public <T> Optional<T> visit(FormattedText.ContentConsumer<T> var1) {
            return â˜ƒ.accept(â˜ƒ);
         }

         @Override
         public <T> Optional<T> visit(FormattedText.StyledContentConsumer<T> var1, Style var2) {
            return â˜ƒ.accept(â˜ƒ, â˜ƒ);
         }
      };
   }

   static FormattedText of(final String var0, final Style var1) {
      return new FormattedText() {
         @Override
         public <T> Optional<T> visit(FormattedText.ContentConsumer<T> var1x) {
            return â˜ƒ.accept(â˜ƒ);
         }

         @Override
         public <T> Optional<T> visit(FormattedText.StyledContentConsumer<T> var1x, Style var2) {
            return â˜ƒ.accept(â˜ƒ.applyTo(â˜ƒ), â˜ƒ);
         }
      };
   }

   static FormattedText composite(FormattedText... var0) {
      return composite(ImmutableList.copyOf(â˜ƒ));
   }

   static FormattedText composite(final List<? extends FormattedText> var0) {
      return new FormattedText() {
         @Override
         public <T> Optional<T> visit(FormattedText.ContentConsumer<T> var1) {
            for(FormattedText â˜ƒ : â˜ƒ) {
               Optional<T> â˜ƒx = â˜ƒ.visit(â˜ƒ);
               if (â˜ƒx.isPresent()) {
                  return â˜ƒx;
               }
            }

            return Optional.empty();
         }

         @Override
         public <T> Optional<T> visit(FormattedText.StyledContentConsumer<T> var1, Style var2) {
            for(FormattedText â˜ƒ : â˜ƒ) {
               Optional<T> â˜ƒx = â˜ƒ.visit(â˜ƒ, â˜ƒ);
               if (â˜ƒx.isPresent()) {
                  return â˜ƒx;
               }
            }

            return Optional.empty();
         }
      };
   }

   default String getString() {
      StringBuilder â˜ƒ = new StringBuilder();
      this.visit(var1x -> {
         â˜ƒ.append(var1x);
         return Optional.empty();
      });
      return â˜ƒ.toString();
   }

   public interface ContentConsumer<T> {
      Optional<T> accept(String var1);
   }

   public interface StyledContentConsumer<T> {
      Optional<T> accept(Style var1, String var2);
   }
}
