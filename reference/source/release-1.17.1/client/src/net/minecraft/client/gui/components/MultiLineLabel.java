package net.minecraft.client.gui.components;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;

public interface MultiLineLabel {
   MultiLineLabel EMPTY = new MultiLineLabel() {
      @Override
      public int renderCentered(PoseStack var1, int var2, int var3) {
         return â˜ƒ;
      }

      @Override
      public int renderCentered(PoseStack var1, int var2, int var3, int var4, int var5) {
         return â˜ƒ;
      }

      @Override
      public int renderLeftAligned(PoseStack var1, int var2, int var3, int var4, int var5) {
         return â˜ƒ;
      }

      @Override
      public int renderLeftAlignedNoShadow(PoseStack var1, int var2, int var3, int var4, int var5) {
         return â˜ƒ;
      }

      @Override
      public int getLineCount() {
         return 0;
      }
   };

   static MultiLineLabel create(Font var0, FormattedText var1, int var2) {
      return createFixed(
         â˜ƒ,
         (List<MultiLineLabel.TextWithWidth>)â˜ƒ.split(â˜ƒ, â˜ƒ)
            .stream()
            .map(var1x -> new MultiLineLabel.TextWithWidth(var1x, â˜ƒ.width(var1x)))
            .collect(ImmutableList.toImmutableList())
      );
   }

   static MultiLineLabel create(Font var0, FormattedText var1, int var2, int var3) {
      return createFixed(
         â˜ƒ,
         (List<MultiLineLabel.TextWithWidth>)â˜ƒ.split(â˜ƒ, â˜ƒ)
            .stream()
            .limit((long)â˜ƒ)
            .map(var1x -> new MultiLineLabel.TextWithWidth(var1x, â˜ƒ.width(var1x)))
            .collect(ImmutableList.toImmutableList())
      );
   }

   static MultiLineLabel create(Font var0, Component... var1) {
      return createFixed(
         â˜ƒ,
         (List<MultiLineLabel.TextWithWidth>)Arrays.stream(â˜ƒ)
            .map(Component::getVisualOrderText)
            .map(var1x -> new MultiLineLabel.TextWithWidth(var1x, â˜ƒ.width(var1x)))
            .collect(ImmutableList.toImmutableList())
      );
   }

   static MultiLineLabel create(Font var0, List<Component> var1) {
      return createFixed(
         â˜ƒ,
         (List<MultiLineLabel.TextWithWidth>)â˜ƒ.stream()
            .map(Component::getVisualOrderText)
            .map(var1x -> new MultiLineLabel.TextWithWidth(var1x, â˜ƒ.width(var1x)))
            .collect(ImmutableList.toImmutableList())
      );
   }

   static MultiLineLabel createFixed(final Font var0, final List<MultiLineLabel.TextWithWidth> var1) {
      return â˜ƒ.isEmpty() ? EMPTY : new MultiLineLabel() {
         @Override
         public int renderCentered(PoseStack var1x, int var2, int var3) {
            return this.renderCentered(â˜ƒ, â˜ƒ, â˜ƒ, 9, 16777215);
         }

         @Override
         public int renderCentered(PoseStack var1x, int var2, int var3, int var4, int var5) {
            int â˜ƒ = â˜ƒ;

            for(MultiLineLabel.TextWithWidth â˜ƒx : â˜ƒ) {
               â˜ƒ.drawShadow(â˜ƒ, â˜ƒx.text, (float)(â˜ƒ - â˜ƒx.width / 2), (float)â˜ƒ, â˜ƒ);
               â˜ƒ += â˜ƒ;
            }

            return â˜ƒ;
         }

         @Override
         public int renderLeftAligned(PoseStack var1x, int var2, int var3, int var4, int var5) {
            int â˜ƒ = â˜ƒ;

            for(MultiLineLabel.TextWithWidth â˜ƒx : â˜ƒ) {
               â˜ƒ.drawShadow(â˜ƒ, â˜ƒx.text, (float)â˜ƒ, (float)â˜ƒ, â˜ƒ);
               â˜ƒ += â˜ƒ;
            }

            return â˜ƒ;
         }

         @Override
         public int renderLeftAlignedNoShadow(PoseStack var1x, int var2, int var3, int var4, int var5) {
            int â˜ƒ = â˜ƒ;

            for(MultiLineLabel.TextWithWidth â˜ƒx : â˜ƒ) {
               â˜ƒ.draw(â˜ƒ, â˜ƒx.text, (float)â˜ƒ, (float)â˜ƒ, â˜ƒ);
               â˜ƒ += â˜ƒ;
            }

            return â˜ƒ;
         }

         @Override
         public int getLineCount() {
            return â˜ƒ.size();
         }
      };
   }

   int renderCentered(PoseStack var1, int var2, int var3);

   int renderCentered(PoseStack var1, int var2, int var3, int var4, int var5);

   int renderLeftAligned(PoseStack var1, int var2, int var3, int var4, int var5);

   int renderLeftAlignedNoShadow(PoseStack var1, int var2, int var3, int var4, int var5);

   int getLineCount();

   public static class TextWithWidth {
      final FormattedCharSequence text;
      final int width;

      TextWithWidth(FormattedCharSequence var1, int var2) {
         this.text = â˜ƒ;
         this.width = â˜ƒ;
      }
   }
}
