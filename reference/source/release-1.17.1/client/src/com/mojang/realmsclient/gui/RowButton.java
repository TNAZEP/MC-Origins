package com.mojang.realmsclient.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.realms.RealmsObjectSelectionList;

public abstract class RowButton {
   public final int width;
   public final int height;
   public final int xOffset;
   public final int yOffset;

   public RowButton(int var1, int var2, int var3, int var4) {
      this.width = â˜ƒ;
      this.height = â˜ƒ;
      this.xOffset = â˜ƒ;
      this.yOffset = â˜ƒ;
   }

   public void drawForRowAt(PoseStack var1, int var2, int var3, int var4, int var5) {
      int â˜ƒ = â˜ƒ + this.xOffset;
      int â˜ƒx = â˜ƒ + this.yOffset;
      boolean â˜ƒxx = false;
      if (â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒ + this.width && â˜ƒ >= â˜ƒx && â˜ƒ <= â˜ƒx + this.height) {
         â˜ƒxx = true;
      }

      this.draw(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx);
   }

   protected abstract void draw(PoseStack var1, int var2, int var3, boolean var4);

   public int getRight() {
      return this.xOffset + this.width;
   }

   public int getBottom() {
      return this.yOffset + this.height;
   }

   public abstract void onClick(int var1);

   public static void drawButtonsInRow(PoseStack var0, List<RowButton> var1, RealmsObjectSelectionList<?> var2, int var3, int var4, int var5, int var6) {
      for(RowButton â˜ƒ : â˜ƒ) {
         if (â˜ƒ.getRowWidth() > â˜ƒ.getRight()) {
            â˜ƒ.drawForRowAt(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }
   }

   public static void rowButtonMouseClicked(
      RealmsObjectSelectionList<?> var0, ObjectSelectionList.Entry<?> var1, List<RowButton> var2, int var3, double var4, double var6
   ) {
      if (â˜ƒ == 0) {
         int â˜ƒ = â˜ƒ.children().indexOf(â˜ƒ);
         if (â˜ƒ > -1) {
            â˜ƒ.selectItem(â˜ƒ);
            int â˜ƒx = â˜ƒ.getRowLeft();
            int â˜ƒxx = â˜ƒ.getRowTop(â˜ƒ);
            int â˜ƒxxx = (int)(â˜ƒ - (double)â˜ƒx);
            int â˜ƒxxxx = (int)(â˜ƒ - (double)â˜ƒxx);

            for(RowButton â˜ƒxxxxx : â˜ƒ) {
               if (â˜ƒxxx >= â˜ƒxxxxx.xOffset && â˜ƒxxx <= â˜ƒxxxxx.getRight() && â˜ƒxxxx >= â˜ƒxxxxx.yOffset && â˜ƒxxxx <= â˜ƒxxxxx.getBottom()) {
                  â˜ƒxxxxx.onClick(â˜ƒ);
               }
            }
         }
      }
   }
}
