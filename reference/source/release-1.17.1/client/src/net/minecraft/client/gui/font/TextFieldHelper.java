package net.minecraft.client.gui.font;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Mth;

public class TextFieldHelper {
   private final Supplier<String> getMessageFn;
   private final Consumer<String> setMessageFn;
   private final Supplier<String> getClipboardFn;
   private final Consumer<String> setClipboardFn;
   private final Predicate<String> stringValidator;
   private int cursorPos;
   private int selectionPos;

   public TextFieldHelper(Supplier<String> var1, Consumer<String> var2, Supplier<String> var3, Consumer<String> var4, Predicate<String> var5) {
      this.getMessageFn = â˜ƒ;
      this.setMessageFn = â˜ƒ;
      this.getClipboardFn = â˜ƒ;
      this.setClipboardFn = â˜ƒ;
      this.stringValidator = â˜ƒ;
      this.setCursorToEnd();
   }

   public static Supplier<String> createClipboardGetter(Minecraft var0) {
      return () -> getClipboardContents(â˜ƒ);
   }

   public static String getClipboardContents(Minecraft var0) {
      return ChatFormatting.stripFormatting(â˜ƒ.keyboardHandler.getClipboard().replaceAll("\\r", ""));
   }

   public static Consumer<String> createClipboardSetter(Minecraft var0) {
      return var1 -> setClipboardContents(â˜ƒ, var1);
   }

   public static void setClipboardContents(Minecraft var0, String var1) {
      â˜ƒ.keyboardHandler.setClipboard(â˜ƒ);
   }

   public boolean charTyped(char var1) {
      if (SharedConstants.isAllowedChatCharacter(â˜ƒ)) {
         this.insertText((String)this.getMessageFn.get(), Character.toString(â˜ƒ));
      }

      return true;
   }

   public boolean keyPressed(int var1) {
      if (Screen.isSelectAll(â˜ƒ)) {
         this.selectAll();
         return true;
      } else if (Screen.isCopy(â˜ƒ)) {
         this.copy();
         return true;
      } else if (Screen.isPaste(â˜ƒ)) {
         this.paste();
         return true;
      } else if (Screen.isCut(â˜ƒ)) {
         this.cut();
         return true;
      } else if (â˜ƒ == 259) {
         this.removeCharsFromCursor(-1);
         return true;
      } else {
         if (â˜ƒ == 261) {
            this.removeCharsFromCursor(1);
         } else {
            if (â˜ƒ == 263) {
               if (Screen.hasControlDown()) {
                  this.moveByWords(-1, Screen.hasShiftDown());
               } else {
                  this.moveByChars(-1, Screen.hasShiftDown());
               }

               return true;
            }

            if (â˜ƒ == 262) {
               if (Screen.hasControlDown()) {
                  this.moveByWords(1, Screen.hasShiftDown());
               } else {
                  this.moveByChars(1, Screen.hasShiftDown());
               }

               return true;
            }

            if (â˜ƒ == 268) {
               this.setCursorToStart(Screen.hasShiftDown());
               return true;
            }

            if (â˜ƒ == 269) {
               this.setCursorToEnd(Screen.hasShiftDown());
               return true;
            }
         }

         return false;
      }
   }

   private int clampToMsgLength(int var1) {
      return Mth.clamp(â˜ƒ, 0, ((String)this.getMessageFn.get()).length());
   }

   private void insertText(String var1, String var2) {
      if (this.selectionPos != this.cursorPos) {
         â˜ƒ = this.deleteSelection(â˜ƒ);
      }

      this.cursorPos = Mth.clamp(this.cursorPos, 0, â˜ƒ.length());
      String â˜ƒ = new StringBuilder(â˜ƒ).insert(this.cursorPos, â˜ƒ).toString();
      if (this.stringValidator.test(â˜ƒ)) {
         this.setMessageFn.accept(â˜ƒ);
         this.selectionPos = this.cursorPos = Math.min(â˜ƒ.length(), this.cursorPos + â˜ƒ.length());
      }
   }

   public void insertText(String var1) {
      this.insertText((String)this.getMessageFn.get(), â˜ƒ);
   }

   private void resetSelectionIfNeeded(boolean var1) {
      if (!â˜ƒ) {
         this.selectionPos = this.cursorPos;
      }
   }

   public void moveByChars(int var1) {
      this.moveByChars(â˜ƒ, false);
   }

   public void moveByChars(int var1, boolean var2) {
      this.cursorPos = Util.offsetByCodepoints((String)this.getMessageFn.get(), this.cursorPos, â˜ƒ);
      this.resetSelectionIfNeeded(â˜ƒ);
   }

   public void moveByWords(int var1) {
      this.moveByWords(â˜ƒ, false);
   }

   public void moveByWords(int var1, boolean var2) {
      this.cursorPos = StringSplitter.getWordPosition((String)this.getMessageFn.get(), â˜ƒ, this.cursorPos, true);
      this.resetSelectionIfNeeded(â˜ƒ);
   }

   public void removeCharsFromCursor(int var1) {
      String â˜ƒ = (String)this.getMessageFn.get();
      if (!â˜ƒ.isEmpty()) {
         String â˜ƒx;
         if (this.selectionPos != this.cursorPos) {
            â˜ƒx = this.deleteSelection(â˜ƒ);
         } else {
            int â˜ƒx = Util.offsetByCodepoints(â˜ƒ, this.cursorPos, â˜ƒ);
            int â˜ƒxx = Math.min(â˜ƒx, this.cursorPos);
            int â˜ƒxxx = Math.max(â˜ƒx, this.cursorPos);
            â˜ƒx = new StringBuilder(â˜ƒ).delete(â˜ƒxx, â˜ƒxxx).toString();
            if (â˜ƒ < 0) {
               this.selectionPos = this.cursorPos = â˜ƒxx;
            }
         }

         this.setMessageFn.accept(â˜ƒx);
      }
   }

   public void cut() {
      String â˜ƒ = (String)this.getMessageFn.get();
      this.setClipboardFn.accept(this.getSelected(â˜ƒ));
      this.setMessageFn.accept(this.deleteSelection(â˜ƒ));
   }

   public void paste() {
      this.insertText((String)this.getMessageFn.get(), (String)this.getClipboardFn.get());
      this.selectionPos = this.cursorPos;
   }

   public void copy() {
      this.setClipboardFn.accept(this.getSelected((String)this.getMessageFn.get()));
   }

   public void selectAll() {
      this.selectionPos = 0;
      this.cursorPos = ((String)this.getMessageFn.get()).length();
   }

   private String getSelected(String var1) {
      int â˜ƒ = Math.min(this.cursorPos, this.selectionPos);
      int â˜ƒx = Math.max(this.cursorPos, this.selectionPos);
      return â˜ƒ.substring(â˜ƒ, â˜ƒx);
   }

   private String deleteSelection(String var1) {
      if (this.selectionPos == this.cursorPos) {
         return â˜ƒ;
      } else {
         int â˜ƒ = Math.min(this.cursorPos, this.selectionPos);
         int â˜ƒx = Math.max(this.cursorPos, this.selectionPos);
         String â˜ƒxx = â˜ƒ.substring(0, â˜ƒ) + â˜ƒ.substring(â˜ƒx);
         this.selectionPos = this.cursorPos = â˜ƒ;
         return â˜ƒxx;
      }
   }

   public void setCursorToStart() {
      this.setCursorToStart(false);
   }

   private void setCursorToStart(boolean var1) {
      this.cursorPos = 0;
      this.resetSelectionIfNeeded(â˜ƒ);
   }

   public void setCursorToEnd() {
      this.setCursorToEnd(false);
   }

   private void setCursorToEnd(boolean var1) {
      this.cursorPos = ((String)this.getMessageFn.get()).length();
      this.resetSelectionIfNeeded(â˜ƒ);
   }

   public int getCursorPos() {
      return this.cursorPos;
   }

   public void setCursorPos(int var1) {
      this.setCursorPos(â˜ƒ, true);
   }

   public void setCursorPos(int var1, boolean var2) {
      this.cursorPos = this.clampToMsgLength(â˜ƒ);
      this.resetSelectionIfNeeded(â˜ƒ);
   }

   public int getSelectionPos() {
      return this.selectionPos;
   }

   public void setSelectionPos(int var1) {
      this.selectionPos = this.clampToMsgLength(â˜ƒ);
   }

   public void setSelectionRange(int var1, int var2) {
      int â˜ƒ = ((String)this.getMessageFn.get()).length();
      this.cursorPos = Mth.clamp(â˜ƒ, 0, â˜ƒ);
      this.selectionPos = Mth.clamp(â˜ƒ, 0, â˜ƒ);
   }

   public boolean isSelecting() {
      return this.cursorPos != this.selectionPos;
   }
}
