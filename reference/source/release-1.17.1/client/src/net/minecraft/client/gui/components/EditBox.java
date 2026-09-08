package net.minecraft.client.gui.components;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

public class EditBox extends AbstractWidget implements Widget, GuiEventListener {
   public static final int BACKWARDS = -1;
   public static final int FORWARDS = 1;
   private static final int CURSOR_INSERT_WIDTH = 1;
   private static final int CURSOR_INSERT_COLOR = -3092272;
   private static final String CURSOR_APPEND_CHARACTER = "_";
   public static final int DEFAULT_TEXT_COLOR = 14737632;
   private static final int BORDER_COLOR_FOCUSED = -1;
   private static final int BORDER_COLOR = -6250336;
   private static final int BACKGROUND_COLOR = -16777216;
   private final Font font;
   private String value = "";
   private int maxLength = 32;
   private int frame;
   private boolean bordered = true;
   private boolean canLoseFocus = true;
   private boolean isEditable = true;
   private boolean shiftPressed;
   private int displayPos;
   private int cursorPos;
   private int highlightPos;
   private int textColor = 14737632;
   private int textColorUneditable = 7368816;
   @Nullable
   private String suggestion;
   @Nullable
   private Consumer<String> responder;
   private Predicate<String> filter = Objects::nonNull;
   private BiFunction<String, Integer, FormattedCharSequence> formatter = (var0, var1x) -> FormattedCharSequence.forward(var0, Style.EMPTY);

   public EditBox(Font var1, int var2, int var3, int var4, int var5, Component var6) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, null, â˜ƒ);
   }

   public EditBox(Font var1, int var2, int var3, int var4, int var5, @Nullable EditBox var6, Component var7) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.font = â˜ƒ;
      if (â˜ƒ != null) {
         this.setValue(â˜ƒ.getValue());
      }
   }

   public void setResponder(Consumer<String> var1) {
      this.responder = â˜ƒ;
   }

   public void setFormatter(BiFunction<String, Integer, FormattedCharSequence> var1) {
      this.formatter = â˜ƒ;
   }

   public void tick() {
      ++this.frame;
   }

   @Override
   protected MutableComponent createNarrationMessage() {
      Component â˜ƒ = this.getMessage();
      return new TranslatableComponent("gui.narrate.editBox", â˜ƒ, this.value);
   }

   public void setValue(String var1) {
      if (this.filter.test(â˜ƒ)) {
         if (â˜ƒ.length() > this.maxLength) {
            this.value = â˜ƒ.substring(0, this.maxLength);
         } else {
            this.value = â˜ƒ;
         }

         this.moveCursorToEnd();
         this.setHighlightPos(this.cursorPos);
         this.onValueChange(â˜ƒ);
      }
   }

   public String getValue() {
      return this.value;
   }

   public String getHighlighted() {
      int â˜ƒ = Math.min(this.cursorPos, this.highlightPos);
      int â˜ƒx = Math.max(this.cursorPos, this.highlightPos);
      return this.value.substring(â˜ƒ, â˜ƒx);
   }

   public void setFilter(Predicate<String> var1) {
      this.filter = â˜ƒ;
   }

   public void insertText(String var1) {
      int â˜ƒ = Math.min(this.cursorPos, this.highlightPos);
      int â˜ƒx = Math.max(this.cursorPos, this.highlightPos);
      int â˜ƒxx = this.maxLength - this.value.length() - (â˜ƒ - â˜ƒx);
      String â˜ƒxxx = SharedConstants.filterText(â˜ƒ);
      int â˜ƒxxxx = â˜ƒxxx.length();
      if (â˜ƒxx < â˜ƒxxxx) {
         â˜ƒxxx = â˜ƒxxx.substring(0, â˜ƒxx);
         â˜ƒxxxx = â˜ƒxx;
      }

      String â˜ƒ = new StringBuilder(this.value).replace(â˜ƒ, â˜ƒx, â˜ƒxxx).toString();
      if (this.filter.test(â˜ƒ)) {
         this.value = â˜ƒ;
         this.setCursorPosition(â˜ƒ + â˜ƒxxxx);
         this.setHighlightPos(this.cursorPos);
         this.onValueChange(this.value);
      }
   }

   private void onValueChange(String var1) {
      if (this.responder != null) {
         this.responder.accept(â˜ƒ);
      }
   }

   private void deleteText(int var1) {
      if (Screen.hasControlDown()) {
         this.deleteWords(â˜ƒ);
      } else {
         this.deleteChars(â˜ƒ);
      }
   }

   public void deleteWords(int var1) {
      if (!this.value.isEmpty()) {
         if (this.highlightPos != this.cursorPos) {
            this.insertText("");
         } else {
            this.deleteChars(this.getWordPosition(â˜ƒ) - this.cursorPos);
         }
      }
   }

   public void deleteChars(int var1) {
      if (!this.value.isEmpty()) {
         if (this.highlightPos != this.cursorPos) {
            this.insertText("");
         } else {
            int â˜ƒ = this.getCursorPos(â˜ƒ);
            int â˜ƒx = Math.min(â˜ƒ, this.cursorPos);
            int â˜ƒxx = Math.max(â˜ƒ, this.cursorPos);
            if (â˜ƒx != â˜ƒxx) {
               String â˜ƒxxx = new StringBuilder(this.value).delete(â˜ƒx, â˜ƒxx).toString();
               if (this.filter.test(â˜ƒxxx)) {
                  this.value = â˜ƒxxx;
                  this.moveCursorTo(â˜ƒx);
               }
            }
         }
      }
   }

   public int getWordPosition(int var1) {
      return this.getWordPosition(â˜ƒ, this.getCursorPosition());
   }

   private int getWordPosition(int var1, int var2) {
      return this.getWordPosition(â˜ƒ, â˜ƒ, true);
   }

   private int getWordPosition(int var1, int var2, boolean var3) {
      int â˜ƒ = â˜ƒ;
      boolean â˜ƒx = â˜ƒ < 0;
      int â˜ƒxx = Math.abs(â˜ƒ);

      for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒxx; ++â˜ƒxxx) {
         if (!â˜ƒx) {
            int â˜ƒxxxx = this.value.length();
            â˜ƒ = this.value.indexOf(32, â˜ƒ);
            if (â˜ƒ == -1) {
               â˜ƒ = â˜ƒxxxx;
            } else {
               while(â˜ƒ && â˜ƒ < â˜ƒxxxx && this.value.charAt(â˜ƒ) == ' ') {
                  ++â˜ƒ;
               }
            }
         } else {
            while(â˜ƒ && â˜ƒ > 0 && this.value.charAt(â˜ƒ - 1) == ' ') {
               --â˜ƒ;
            }

            while(â˜ƒ > 0 && this.value.charAt(â˜ƒ - 1) != ' ') {
               --â˜ƒ;
            }
         }
      }

      return â˜ƒ;
   }

   public void moveCursor(int var1) {
      this.moveCursorTo(this.getCursorPos(â˜ƒ));
   }

   private int getCursorPos(int var1) {
      return Util.offsetByCodepoints(this.value, this.cursorPos, â˜ƒ);
   }

   public void moveCursorTo(int var1) {
      this.setCursorPosition(â˜ƒ);
      if (!this.shiftPressed) {
         this.setHighlightPos(this.cursorPos);
      }

      this.onValueChange(this.value);
   }

   public void setCursorPosition(int var1) {
      this.cursorPos = Mth.clamp(â˜ƒ, 0, this.value.length());
   }

   public void moveCursorToStart() {
      this.moveCursorTo(0);
   }

   public void moveCursorToEnd() {
      this.moveCursorTo(this.value.length());
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (!this.canConsumeInput()) {
         return false;
      } else {
         this.shiftPressed = Screen.hasShiftDown();
         if (Screen.isSelectAll(â˜ƒ)) {
            this.moveCursorToEnd();
            this.setHighlightPos(0);
            return true;
         } else if (Screen.isCopy(â˜ƒ)) {
            Minecraft.getInstance().keyboardHandler.setClipboard(this.getHighlighted());
            return true;
         } else if (Screen.isPaste(â˜ƒ)) {
            if (this.isEditable) {
               this.insertText(Minecraft.getInstance().keyboardHandler.getClipboard());
            }

            return true;
         } else if (Screen.isCut(â˜ƒ)) {
            Minecraft.getInstance().keyboardHandler.setClipboard(this.getHighlighted());
            if (this.isEditable) {
               this.insertText("");
            }

            return true;
         } else {
            switch(â˜ƒ) {
               case 259:
                  if (this.isEditable) {
                     this.shiftPressed = false;
                     this.deleteText(-1);
                     this.shiftPressed = Screen.hasShiftDown();
                  }

                  return true;
               case 260:
               case 264:
               case 265:
               case 266:
               case 267:
               default:
                  return false;
               case 261:
                  if (this.isEditable) {
                     this.shiftPressed = false;
                     this.deleteText(1);
                     this.shiftPressed = Screen.hasShiftDown();
                  }

                  return true;
               case 262:
                  if (Screen.hasControlDown()) {
                     this.moveCursorTo(this.getWordPosition(1));
                  } else {
                     this.moveCursor(1);
                  }

                  return true;
               case 263:
                  if (Screen.hasControlDown()) {
                     this.moveCursorTo(this.getWordPosition(-1));
                  } else {
                     this.moveCursor(-1);
                  }

                  return true;
               case 268:
                  this.moveCursorToStart();
                  return true;
               case 269:
                  this.moveCursorToEnd();
                  return true;
            }
         }
      }
   }

   public boolean canConsumeInput() {
      return this.isVisible() && this.isFocused() && this.isEditable();
   }

   @Override
   public boolean charTyped(char var1, int var2) {
      if (!this.canConsumeInput()) {
         return false;
      } else if (SharedConstants.isAllowedChatCharacter(â˜ƒ)) {
         if (this.isEditable) {
            this.insertText(Character.toString(â˜ƒ));
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (!this.isVisible()) {
         return false;
      } else {
         boolean â˜ƒ = â˜ƒ >= (double)this.x && â˜ƒ < (double)(this.x + this.width) && â˜ƒ >= (double)this.y && â˜ƒ < (double)(this.y + this.height);
         if (this.canLoseFocus) {
            this.setFocus(â˜ƒ);
         }

         if (this.isFocused() && â˜ƒ && â˜ƒ == 0) {
            int â˜ƒ = Mth.floor(â˜ƒ) - this.x;
            if (this.bordered) {
               â˜ƒ -= 4;
            }

            String â˜ƒ = this.font.plainSubstrByWidth(this.value.substring(this.displayPos), this.getInnerWidth());
            this.moveCursorTo(this.font.plainSubstrByWidth(â˜ƒ, â˜ƒ).length() + this.displayPos);
            return true;
         } else {
            return false;
         }
      }
   }

   public void setFocus(boolean var1) {
      this.setFocused(â˜ƒ);
   }

   @Override
   public void renderButton(PoseStack var1, int var2, int var3, float var4) {
      if (this.isVisible()) {
         if (this.isBordered()) {
            int â˜ƒ = this.isFocused() ? -1 : -6250336;
            fill(â˜ƒ, this.x - 1, this.y - 1, this.x + this.width + 1, this.y + this.height + 1, â˜ƒ);
            fill(â˜ƒ, this.x, this.y, this.x + this.width, this.y + this.height, -16777216);
         }

         int â˜ƒ = this.isEditable ? this.textColor : this.textColorUneditable;
         int â˜ƒx = this.cursorPos - this.displayPos;
         int â˜ƒxx = this.highlightPos - this.displayPos;
         String â˜ƒxxx = this.font.plainSubstrByWidth(this.value.substring(this.displayPos), this.getInnerWidth());
         boolean â˜ƒxxxx = â˜ƒx >= 0 && â˜ƒx <= â˜ƒxxx.length();
         boolean â˜ƒxxxxx = this.isFocused() && this.frame / 6 % 2 == 0 && â˜ƒxxxx;
         int â˜ƒxxxxxx = this.bordered ? this.x + 4 : this.x;
         int â˜ƒxxxxxxx = this.bordered ? this.y + (this.height - 8) / 2 : this.y;
         int â˜ƒxxxxxxxx = â˜ƒxxxxxx;
         if (â˜ƒxx > â˜ƒxxx.length()) {
            â˜ƒxx = â˜ƒxxx.length();
         }

         if (!â˜ƒxxx.isEmpty()) {
            String â˜ƒ = â˜ƒxxxx ? â˜ƒxxx.substring(0, â˜ƒx) : â˜ƒxxx;
            â˜ƒxxxxxxxx = this.font
               .drawShadow(â˜ƒ, (FormattedCharSequence)this.formatter.apply(â˜ƒ, this.displayPos), (float)â˜ƒxxxxxx, (float)â˜ƒxxxxxxx, â˜ƒ);
         }

         boolean â˜ƒ = this.cursorPos < this.value.length() || this.value.length() >= this.getMaxLength();
         int â˜ƒx = â˜ƒxxxxxxxx;
         if (!â˜ƒxxxx) {
            â˜ƒx = â˜ƒx > 0 ? â˜ƒxxxxxx + this.width : â˜ƒxxxxxx;
         } else if (â˜ƒ) {
            â˜ƒx = â˜ƒxxxxxxxx - 1;
            --â˜ƒxxxxxxxx;
         }

         if (!â˜ƒxxx.isEmpty() && â˜ƒxxxx && â˜ƒx < â˜ƒxxx.length()) {
            this.font
               .drawShadow(â˜ƒ, (FormattedCharSequence)this.formatter.apply(â˜ƒxxx.substring(â˜ƒx), this.cursorPos), (float)â˜ƒxxxxxxxx, (float)â˜ƒxxxxxxx, â˜ƒ);
         }

         if (!â˜ƒ && this.suggestion != null) {
            this.font.drawShadow(â˜ƒ, this.suggestion, (float)(â˜ƒx - 1), (float)â˜ƒxxxxxxx, -8355712);
         }

         if (â˜ƒxxxxx) {
            if (â˜ƒ) {
               GuiComponent.fill(â˜ƒ, â˜ƒx, â˜ƒxxxxxxx - 1, â˜ƒx + 1, â˜ƒxxxxxxx + 1 + 9, -3092272);
            } else {
               this.font.drawShadow(â˜ƒ, "_", (float)â˜ƒx, (float)â˜ƒxxxxxxx, â˜ƒ);
            }
         }

         if (â˜ƒxx != â˜ƒx) {
            int â˜ƒ = â˜ƒxxxxxx + this.font.width(â˜ƒxxx.substring(0, â˜ƒxx));
            this.renderHighlight(â˜ƒx, â˜ƒxxxxxxx - 1, â˜ƒ - 1, â˜ƒxxxxxxx + 1 + 9);
         }
      }
   }

   private void renderHighlight(int var1, int var2, int var3, int var4) {
      if (â˜ƒ < â˜ƒ) {
         int â˜ƒ = â˜ƒ;
         â˜ƒ = â˜ƒ;
         â˜ƒ = â˜ƒ;
      }

      if (â˜ƒ < â˜ƒ) {
         int â˜ƒ = â˜ƒ;
         â˜ƒ = â˜ƒ;
         â˜ƒ = â˜ƒ;
      }

      if (â˜ƒ > this.x + this.width) {
         â˜ƒ = this.x + this.width;
      }

      if (â˜ƒ > this.x + this.width) {
         â˜ƒ = this.x + this.width;
      }

      Tesselator â˜ƒ = Tesselator.getInstance();
      BufferBuilder â˜ƒx = â˜ƒ.getBuilder();
      RenderSystem.setShader(GameRenderer::getPositionShader);
      RenderSystem.setShaderColor(0.0F, 0.0F, 1.0F, 1.0F);
      RenderSystem.disableTexture();
      RenderSystem.enableColorLogicOp();
      RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
      â˜ƒx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
      â˜ƒx.vertex((double)â˜ƒ, (double)â˜ƒ, 0.0).endVertex();
      â˜ƒx.vertex((double)â˜ƒ, (double)â˜ƒ, 0.0).endVertex();
      â˜ƒx.vertex((double)â˜ƒ, (double)â˜ƒ, 0.0).endVertex();
      â˜ƒx.vertex((double)â˜ƒ, (double)â˜ƒ, 0.0).endVertex();
      â˜ƒ.end();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.disableColorLogicOp();
      RenderSystem.enableTexture();
   }

   public void setMaxLength(int var1) {
      this.maxLength = â˜ƒ;
      if (this.value.length() > â˜ƒ) {
         this.value = this.value.substring(0, â˜ƒ);
         this.onValueChange(this.value);
      }
   }

   private int getMaxLength() {
      return this.maxLength;
   }

   public int getCursorPosition() {
      return this.cursorPos;
   }

   private boolean isBordered() {
      return this.bordered;
   }

   public void setBordered(boolean var1) {
      this.bordered = â˜ƒ;
   }

   public void setTextColor(int var1) {
      this.textColor = â˜ƒ;
   }

   public void setTextColorUneditable(int var1) {
      this.textColorUneditable = â˜ƒ;
   }

   @Override
   public boolean changeFocus(boolean var1) {
      return this.visible && this.isEditable ? super.changeFocus(â˜ƒ) : false;
   }

   @Override
   public boolean isMouseOver(double var1, double var3) {
      return this.visible && â˜ƒ >= (double)this.x && â˜ƒ < (double)(this.x + this.width) && â˜ƒ >= (double)this.y && â˜ƒ < (double)(this.y + this.height);
   }

   @Override
   protected void onFocusedChanged(boolean var1) {
      if (â˜ƒ) {
         this.frame = 0;
      }
   }

   private boolean isEditable() {
      return this.isEditable;
   }

   public void setEditable(boolean var1) {
      this.isEditable = â˜ƒ;
   }

   public int getInnerWidth() {
      return this.isBordered() ? this.width - 8 : this.width;
   }

   public void setHighlightPos(int var1) {
      int â˜ƒ = this.value.length();
      this.highlightPos = Mth.clamp(â˜ƒ, 0, â˜ƒ);
      if (this.font != null) {
         if (this.displayPos > â˜ƒ) {
            this.displayPos = â˜ƒ;
         }

         int â˜ƒx = this.getInnerWidth();
         String â˜ƒxx = this.font.plainSubstrByWidth(this.value.substring(this.displayPos), â˜ƒx);
         int â˜ƒxxx = â˜ƒxx.length() + this.displayPos;
         if (this.highlightPos == this.displayPos) {
            this.displayPos -= this.font.plainSubstrByWidth(this.value, â˜ƒx, true).length();
         }

         if (this.highlightPos > â˜ƒxxx) {
            this.displayPos += this.highlightPos - â˜ƒxxx;
         } else if (this.highlightPos <= this.displayPos) {
            this.displayPos -= this.displayPos - this.highlightPos;
         }

         this.displayPos = Mth.clamp(this.displayPos, 0, â˜ƒ);
      }
   }

   public void setCanLoseFocus(boolean var1) {
      this.canLoseFocus = â˜ƒ;
   }

   public boolean isVisible() {
      return this.visible;
   }

   public void setVisible(boolean var1) {
      this.visible = â˜ƒ;
   }

   public void setSuggestion(@Nullable String var1) {
      this.suggestion = â˜ƒ;
   }

   public int getScreenX(int var1) {
      return â˜ƒ > this.value.length() ? this.x : this.x + this.font.width(this.value.substring(0, â˜ƒ));
   }

   public void setX(int var1) {
      this.x = â˜ƒ;
   }

   @Override
   public void updateNarration(NarrationElementOutput var1) {
      â˜ƒ.add(NarratedElementType.TITLE, new TranslatableComponent("narration.edit_box", this.getValue()));
   }
}
