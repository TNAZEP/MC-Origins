package net.minecraft.client.gui.screens.inventory;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.font.TextFieldHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ServerboundEditBookPacket;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;

public class BookEditScreen extends Screen {
   private static final int TEXT_WIDTH = 114;
   private static final int TEXT_HEIGHT = 128;
   private static final int DOUBLECLICK_SPEED = 250;
   private static final int IMAGE_WIDTH = 192;
   private static final int IMAGE_HEIGHT = 192;
   private static final Component EDIT_TITLE_LABEL = new TranslatableComponent("book.editTitle");
   private static final Component FINALIZE_WARNING_LABEL = new TranslatableComponent("book.finalizeWarning");
   private static final FormattedCharSequence BLACK_CURSOR = FormattedCharSequence.forward("_", Style.EMPTY.withColor(ChatFormatting.BLACK));
   private static final FormattedCharSequence GRAY_CURSOR = FormattedCharSequence.forward("_", Style.EMPTY.withColor(ChatFormatting.GRAY));
   private final Player owner;
   private final ItemStack book;
   private boolean isModified;
   private boolean isSigning;
   private int frameTick;
   private int currentPage;
   private final List<String> pages = Lists.newArrayList();
   private String title = "";
   private final TextFieldHelper pageEdit = new TextFieldHelper(
      this::getCurrentPageText,
      this::setCurrentPageText,
      this::getClipboard,
      this::setClipboard,
      var1x -> var1x.length() < 1024 && this.font.wordWrapHeight(var1x, 114) <= 128
   );
   private final TextFieldHelper titleEdit = new TextFieldHelper(
      () -> this.title, var1x -> this.title = var1x, this::getClipboard, this::setClipboard, var0 -> var0.length() < 16
   );
   private long lastClickTime;
   private int lastIndex = -1;
   private PageButton forwardButton;
   private PageButton backButton;
   private Button doneButton;
   private Button signButton;
   private Button finalizeButton;
   private Button cancelButton;
   private final InteractionHand hand;
   @Nullable
   private BookEditScreen.DisplayCache displayCache = BookEditScreen.DisplayCache.EMPTY;
   private Component pageMsg = TextComponent.EMPTY;
   private final Component ownerText;

   public BookEditScreen(Player var1, ItemStack var2, InteractionHand var3) {
      super(NarratorChatListener.NO_TITLE);
      this.owner = â˜ƒ;
      this.book = â˜ƒ;
      this.hand = â˜ƒ;
      CompoundTag â˜ƒ = â˜ƒ.getTag();
      if (â˜ƒ != null) {
         BookViewScreen.loadPages(â˜ƒ, this.pages::add);
      }

      if (this.pages.isEmpty()) {
         this.pages.add("");
      }

      this.ownerText = new TranslatableComponent("book.byAuthor", â˜ƒ.getName()).withStyle(ChatFormatting.DARK_GRAY);
   }

   private void setClipboard(String var1) {
      if (this.minecraft != null) {
         TextFieldHelper.setClipboardContents(this.minecraft, â˜ƒ);
      }
   }

   private String getClipboard() {
      return this.minecraft != null ? TextFieldHelper.getClipboardContents(this.minecraft) : "";
   }

   private int getNumPages() {
      return this.pages.size();
   }

   @Override
   public void tick() {
      super.tick();
      ++this.frameTick;
   }

   @Override
   protected void init() {
      this.clearDisplayCache();
      this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
      this.signButton = this.addRenderableWidget(new Button(this.width / 2 - 100, 196, 98, 20, new TranslatableComponent("book.signButton"), var1x -> {
         this.isSigning = true;
         this.updateButtonVisibility();
      }));
      this.doneButton = this.addRenderableWidget(new Button(this.width / 2 + 2, 196, 98, 20, CommonComponents.GUI_DONE, var1x -> {
         this.minecraft.setScreen(null);
         this.saveChanges(false);
      }));
      this.finalizeButton = this.addRenderableWidget(new Button(this.width / 2 - 100, 196, 98, 20, new TranslatableComponent("book.finalizeButton"), var1x -> {
         if (this.isSigning) {
            this.saveChanges(true);
            this.minecraft.setScreen(null);
         }
      }));
      this.cancelButton = this.addRenderableWidget(new Button(this.width / 2 + 2, 196, 98, 20, CommonComponents.GUI_CANCEL, var1x -> {
         if (this.isSigning) {
            this.isSigning = false;
         }

         this.updateButtonVisibility();
      }));
      int â˜ƒ = (this.width - 192) / 2;
      int â˜ƒx = 2;
      this.forwardButton = this.addRenderableWidget(new PageButton(â˜ƒ + 116, 159, true, var1x -> this.pageForward(), true));
      this.backButton = this.addRenderableWidget(new PageButton(â˜ƒ + 43, 159, false, var1x -> this.pageBack(), true));
      this.updateButtonVisibility();
   }

   private void pageBack() {
      if (this.currentPage > 0) {
         --this.currentPage;
      }

      this.updateButtonVisibility();
      this.clearDisplayCacheAfterPageChange();
   }

   private void pageForward() {
      if (this.currentPage < this.getNumPages() - 1) {
         ++this.currentPage;
      } else {
         this.appendPageToBook();
         if (this.currentPage < this.getNumPages() - 1) {
            ++this.currentPage;
         }
      }

      this.updateButtonVisibility();
      this.clearDisplayCacheAfterPageChange();
   }

   @Override
   public void removed() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
   }

   private void updateButtonVisibility() {
      this.backButton.visible = !this.isSigning && this.currentPage > 0;
      this.forwardButton.visible = !this.isSigning;
      this.doneButton.visible = !this.isSigning;
      this.signButton.visible = !this.isSigning;
      this.cancelButton.visible = this.isSigning;
      this.finalizeButton.visible = this.isSigning;
      this.finalizeButton.active = !this.title.trim().isEmpty();
   }

   private void eraseEmptyTrailingPages() {
      ListIterator<String> â˜ƒ = this.pages.listIterator(this.pages.size());

      while(â˜ƒ.hasPrevious() && ((String)â˜ƒ.previous()).isEmpty()) {
         â˜ƒ.remove();
      }
   }

   private void saveChanges(boolean var1) {
      if (this.isModified) {
         this.eraseEmptyTrailingPages();
         this.updateLocalCopy(â˜ƒ);
         int â˜ƒ = this.hand == InteractionHand.MAIN_HAND ? this.owner.getInventory().selected : 40;
         this.minecraft.getConnection().send(new ServerboundEditBookPacket(â˜ƒ, this.pages, â˜ƒ ? Optional.of(this.title.trim()) : Optional.empty()));
      }
   }

   private void updateLocalCopy(boolean var1) {
      ListTag â˜ƒ = new ListTag();
      this.pages.stream().map(StringTag::valueOf).forEach(â˜ƒ::add);
      if (!this.pages.isEmpty()) {
         this.book.addTagElement("pages", â˜ƒ);
      }

      if (â˜ƒ) {
         this.book.addTagElement("author", StringTag.valueOf(this.owner.getGameProfile().getName()));
         this.book.addTagElement("title", StringTag.valueOf(this.title.trim()));
      }
   }

   private void appendPageToBook() {
      if (this.getNumPages() < 100) {
         this.pages.add("");
         this.isModified = true;
      }
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ)) {
         return true;
      } else if (this.isSigning) {
         return this.titleKeyPressed(â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         boolean â˜ƒ = this.bookKeyPressed(â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒ) {
            this.clearDisplayCache();
            return true;
         } else {
            return false;
         }
      }
   }

   @Override
   public boolean charTyped(char var1, int var2) {
      if (super.charTyped(â˜ƒ, â˜ƒ)) {
         return true;
      } else if (this.isSigning) {
         boolean â˜ƒ = this.titleEdit.charTyped(â˜ƒ);
         if (â˜ƒ) {
            this.updateButtonVisibility();
            this.isModified = true;
            return true;
         } else {
            return false;
         }
      } else if (SharedConstants.isAllowedChatCharacter(â˜ƒ)) {
         this.pageEdit.insertText(Character.toString(â˜ƒ));
         this.clearDisplayCache();
         return true;
      } else {
         return false;
      }
   }

   private boolean bookKeyPressed(int var1, int var2, int var3) {
      if (Screen.isSelectAll(â˜ƒ)) {
         this.pageEdit.selectAll();
         return true;
      } else if (Screen.isCopy(â˜ƒ)) {
         this.pageEdit.copy();
         return true;
      } else if (Screen.isPaste(â˜ƒ)) {
         this.pageEdit.paste();
         return true;
      } else if (Screen.isCut(â˜ƒ)) {
         this.pageEdit.cut();
         return true;
      } else {
         switch(â˜ƒ) {
            case 257:
            case 335:
               this.pageEdit.insertText("\n");
               return true;
            case 259:
               this.pageEdit.removeCharsFromCursor(-1);
               return true;
            case 261:
               this.pageEdit.removeCharsFromCursor(1);
               return true;
            case 262:
               this.pageEdit.moveByChars(1, Screen.hasShiftDown());
               return true;
            case 263:
               this.pageEdit.moveByChars(-1, Screen.hasShiftDown());
               return true;
            case 264:
               this.keyDown();
               return true;
            case 265:
               this.keyUp();
               return true;
            case 266:
               this.backButton.onPress();
               return true;
            case 267:
               this.forwardButton.onPress();
               return true;
            case 268:
               this.keyHome();
               return true;
            case 269:
               this.keyEnd();
               return true;
            default:
               return false;
         }
      }
   }

   private void keyUp() {
      this.changeLine(-1);
   }

   private void keyDown() {
      this.changeLine(1);
   }

   private void changeLine(int var1) {
      int â˜ƒ = this.pageEdit.getCursorPos();
      int â˜ƒx = this.getDisplayCache().changeLine(â˜ƒ, â˜ƒ);
      this.pageEdit.setCursorPos(â˜ƒx, Screen.hasShiftDown());
   }

   private void keyHome() {
      int â˜ƒ = this.pageEdit.getCursorPos();
      int â˜ƒx = this.getDisplayCache().findLineStart(â˜ƒ);
      this.pageEdit.setCursorPos(â˜ƒx, Screen.hasShiftDown());
   }

   private void keyEnd() {
      BookEditScreen.DisplayCache â˜ƒ = this.getDisplayCache();
      int â˜ƒx = this.pageEdit.getCursorPos();
      int â˜ƒxx = â˜ƒ.findLineEnd(â˜ƒx);
      this.pageEdit.setCursorPos(â˜ƒxx, Screen.hasShiftDown());
   }

   private boolean titleKeyPressed(int var1, int var2, int var3) {
      switch(â˜ƒ) {
         case 257:
         case 335:
            if (!this.title.isEmpty()) {
               this.saveChanges(true);
               this.minecraft.setScreen(null);
            }

            return true;
         case 259:
            this.titleEdit.removeCharsFromCursor(-1);
            this.updateButtonVisibility();
            this.isModified = true;
            return true;
         default:
            return false;
      }
   }

   private String getCurrentPageText() {
      return this.currentPage >= 0 && this.currentPage < this.pages.size() ? (String)this.pages.get(this.currentPage) : "";
   }

   private void setCurrentPageText(String var1) {
      if (this.currentPage >= 0 && this.currentPage < this.pages.size()) {
         this.pages.set(this.currentPage, â˜ƒ);
         this.isModified = true;
         this.clearDisplayCache();
      }
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      this.setFocused(null);
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShaderTexture(0, BookViewScreen.BOOK_LOCATION);
      int â˜ƒ = (this.width - 192) / 2;
      int â˜ƒx = 2;
      this.blit(â˜ƒ, â˜ƒ, 2, 0, 0, 192, 192);
      if (this.isSigning) {
         boolean â˜ƒxx = this.frameTick / 6 % 2 == 0;
         FormattedCharSequence â˜ƒxxx = FormattedCharSequence.composite(
            FormattedCharSequence.forward(this.title, Style.EMPTY), â˜ƒxx ? BLACK_CURSOR : GRAY_CURSOR
         );
         int â˜ƒxxxx = this.font.width(EDIT_TITLE_LABEL);
         this.font.draw(â˜ƒ, EDIT_TITLE_LABEL, (float)(â˜ƒ + 36 + (114 - â˜ƒxxxx) / 2), 34.0F, 0);
         int â˜ƒxxxxx = this.font.width(â˜ƒxxx);
         this.font.draw(â˜ƒ, â˜ƒxxx, (float)(â˜ƒ + 36 + (114 - â˜ƒxxxxx) / 2), 50.0F, 0);
         int â˜ƒxxxxxx = this.font.width(this.ownerText);
         this.font.draw(â˜ƒ, this.ownerText, (float)(â˜ƒ + 36 + (114 - â˜ƒxxxxxx) / 2), 60.0F, 0);
         this.font.drawWordWrap(FINALIZE_WARNING_LABEL, â˜ƒ + 36, 82, 114, 0);
      } else {
         int â˜ƒ = this.font.width(this.pageMsg);
         this.font.draw(â˜ƒ, this.pageMsg, (float)(â˜ƒ - â˜ƒ + 192 - 44), 18.0F, 0);
         BookEditScreen.DisplayCache â˜ƒx = this.getDisplayCache();

         for(BookEditScreen.LineInfo â˜ƒxx : â˜ƒx.lines) {
            this.font.draw(â˜ƒ, â˜ƒxx.asComponent, (float)â˜ƒxx.x, (float)â˜ƒxx.y, -16777216);
         }

         this.renderHighlight(â˜ƒx.selection);
         this.renderCursor(â˜ƒ, â˜ƒx.cursor, â˜ƒx.cursorAtEnd);
      }

      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private void renderCursor(PoseStack var1, BookEditScreen.Pos2i var2, boolean var3) {
      if (this.frameTick / 6 % 2 == 0) {
         â˜ƒ = this.convertLocalToScreen(â˜ƒ);
         if (!â˜ƒ) {
            GuiComponent.fill(â˜ƒ, â˜ƒ.x, â˜ƒ.y - 1, â˜ƒ.x + 1, â˜ƒ.y + 9, -16777216);
         } else {
            this.font.draw(â˜ƒ, "_", (float)â˜ƒ.x, (float)â˜ƒ.y, 0);
         }
      }
   }

   private void renderHighlight(Rect2i[] var1) {
      Tesselator â˜ƒ = Tesselator.getInstance();
      BufferBuilder â˜ƒx = â˜ƒ.getBuilder();
      RenderSystem.setShader(GameRenderer::getPositionShader);
      RenderSystem.setShaderColor(0.0F, 0.0F, 255.0F, 255.0F);
      RenderSystem.disableTexture();
      RenderSystem.enableColorLogicOp();
      RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
      â˜ƒx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);

      for(Rect2i â˜ƒxx : â˜ƒ) {
         int â˜ƒxxx = â˜ƒxx.getX();
         int â˜ƒxxxx = â˜ƒxx.getY();
         int â˜ƒxxxxx = â˜ƒxxx + â˜ƒxx.getWidth();
         int â˜ƒxxxxxx = â˜ƒxxxx + â˜ƒxx.getHeight();
         â˜ƒx.vertex((double)â˜ƒxxx, (double)â˜ƒxxxxxx, 0.0).endVertex();
         â˜ƒx.vertex((double)â˜ƒxxxxx, (double)â˜ƒxxxxxx, 0.0).endVertex();
         â˜ƒx.vertex((double)â˜ƒxxxxx, (double)â˜ƒxxxx, 0.0).endVertex();
         â˜ƒx.vertex((double)â˜ƒxxx, (double)â˜ƒxxxx, 0.0).endVertex();
      }

      â˜ƒ.end();
      RenderSystem.disableColorLogicOp();
      RenderSystem.enableTexture();
   }

   private BookEditScreen.Pos2i convertScreenToLocal(BookEditScreen.Pos2i var1) {
      return new BookEditScreen.Pos2i(â˜ƒ.x - (this.width - 192) / 2 - 36, â˜ƒ.y - 32);
   }

   private BookEditScreen.Pos2i convertLocalToScreen(BookEditScreen.Pos2i var1) {
      return new BookEditScreen.Pos2i(â˜ƒ.x + (this.width - 192) / 2 + 36, â˜ƒ.y + 32);
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (super.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ)) {
         return true;
      } else {
         if (â˜ƒ == 0) {
            long â˜ƒ = Util.getMillis();
            BookEditScreen.DisplayCache â˜ƒx = this.getDisplayCache();
            int â˜ƒxx = â˜ƒx.getIndexAtPosition(this.font, this.convertScreenToLocal(new BookEditScreen.Pos2i((int)â˜ƒ, (int)â˜ƒ)));
            if (â˜ƒxx >= 0) {
               if (â˜ƒxx != this.lastIndex || â˜ƒ - this.lastClickTime >= 250L) {
                  this.pageEdit.setCursorPos(â˜ƒxx, Screen.hasShiftDown());
               } else if (!this.pageEdit.isSelecting()) {
                  this.selectWord(â˜ƒxx);
               } else {
                  this.pageEdit.selectAll();
               }

               this.clearDisplayCache();
            }

            this.lastIndex = â˜ƒxx;
            this.lastClickTime = â˜ƒ;
         }

         return true;
      }
   }

   private void selectWord(int var1) {
      String â˜ƒ = this.getCurrentPageText();
      this.pageEdit.setSelectionRange(StringSplitter.getWordPosition(â˜ƒ, -1, â˜ƒ, false), StringSplitter.getWordPosition(â˜ƒ, 1, â˜ƒ, false));
   }

   @Override
   public boolean mouseDragged(double var1, double var3, int var5, double var6, double var8) {
      if (super.mouseDragged(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
         return true;
      } else {
         if (â˜ƒ == 0) {
            BookEditScreen.DisplayCache â˜ƒ = this.getDisplayCache();
            int â˜ƒx = â˜ƒ.getIndexAtPosition(this.font, this.convertScreenToLocal(new BookEditScreen.Pos2i((int)â˜ƒ, (int)â˜ƒ)));
            this.pageEdit.setCursorPos(â˜ƒx, true);
            this.clearDisplayCache();
         }

         return true;
      }
   }

   private BookEditScreen.DisplayCache getDisplayCache() {
      if (this.displayCache == null) {
         this.displayCache = this.rebuildDisplayCache();
         this.pageMsg = new TranslatableComponent("book.pageIndicator", this.currentPage + 1, this.getNumPages());
      }

      return this.displayCache;
   }

   private void clearDisplayCache() {
      this.displayCache = null;
   }

   private void clearDisplayCacheAfterPageChange() {
      this.pageEdit.setCursorToEnd();
      this.clearDisplayCache();
   }

   private BookEditScreen.DisplayCache rebuildDisplayCache() {
      String â˜ƒ = this.getCurrentPageText();
      if (â˜ƒ.isEmpty()) {
         return BookEditScreen.DisplayCache.EMPTY;
      } else {
         int â˜ƒx = this.pageEdit.getCursorPos();
         int â˜ƒxx = this.pageEdit.getSelectionPos();
         IntList â˜ƒxxx = new IntArrayList();
         List<BookEditScreen.LineInfo> â˜ƒxxxx = Lists.<BookEditScreen.LineInfo>newArrayList();
         MutableInt â˜ƒxxxxx = new MutableInt();
         MutableBoolean â˜ƒxxxxxx = new MutableBoolean();
         StringSplitter â˜ƒxxxxxxx = this.font.getSplitter();
         â˜ƒxxxxxxx.splitLines(â˜ƒ, 114, Style.EMPTY, true, (var6x, var7x, var8x) -> {
            int â˜ƒ = â˜ƒ.getAndIncrement();
            String â˜ƒx = â˜ƒ.substring(var7x, var8x);
            â˜ƒ.setValue(â˜ƒx.endsWith("\n"));
            String â˜ƒxx = StringUtils.stripEnd(â˜ƒx, " \n");
            int â˜ƒxxx = â˜ƒ * 9;
            BookEditScreen.Pos2i â˜ƒxxxx = this.convertLocalToScreen(new BookEditScreen.Pos2i(0, â˜ƒxxx));
            â˜ƒ.add(var7x);
            â˜ƒ.add(new BookEditScreen.LineInfo(var6x, â˜ƒxx, â˜ƒxxxx.x, â˜ƒxxxx.y));
         });
         int[] â˜ƒxxxxxxxx = â˜ƒxxx.toIntArray();
         boolean â˜ƒxxxxxxxxx = â˜ƒx == â˜ƒ.length();
         BookEditScreen.Pos2i â˜ƒ;
         if (â˜ƒxxxxxxxxx && â˜ƒxxxxxx.isTrue()) {
            â˜ƒ = new BookEditScreen.Pos2i(0, â˜ƒxxxx.size() * 9);
         } else {
            int â˜ƒ = findLineFromPos(â˜ƒxxxxxxxx, â˜ƒx);
            int â˜ƒx = this.font.width(â˜ƒ.substring(â˜ƒxxxxxxxx[â˜ƒ], â˜ƒx));
            â˜ƒ = new BookEditScreen.Pos2i(â˜ƒx, â˜ƒ * 9);
         }

         List<Rect2i> â˜ƒ = Lists.<Rect2i>newArrayList();
         if (â˜ƒx != â˜ƒxx) {
            int â˜ƒx = Math.min(â˜ƒx, â˜ƒxx);
            int â˜ƒxx = Math.max(â˜ƒx, â˜ƒxx);
            int â˜ƒxxx = findLineFromPos(â˜ƒxxxxxxxx, â˜ƒx);
            int â˜ƒxxxx = findLineFromPos(â˜ƒxxxxxxxx, â˜ƒxx);
            if (â˜ƒxxx == â˜ƒxxxx) {
               int â˜ƒxxxxx = â˜ƒxxx * 9;
               int â˜ƒxxxxxx = â˜ƒxxxxxxxx[â˜ƒxxx];
               â˜ƒ.add(this.createPartialLineSelection(â˜ƒ, â˜ƒxxxxxxx, â˜ƒx, â˜ƒxx, â˜ƒxxxxx, â˜ƒxxxxxx));
            } else {
               int â˜ƒx = â˜ƒxxx + 1 > â˜ƒxxxxxxxx.length ? â˜ƒ.length() : â˜ƒxxxxxxxx[â˜ƒxxx + 1];
               â˜ƒ.add(this.createPartialLineSelection(â˜ƒ, â˜ƒxxxxxxx, â˜ƒx, â˜ƒx, â˜ƒxxx * 9, â˜ƒxxxxxxxx[â˜ƒxxx]));

               for(int â˜ƒxx = â˜ƒxxx + 1; â˜ƒxx < â˜ƒxxxx; ++â˜ƒxx) {
                  int â˜ƒxxx = â˜ƒxx * 9;
                  String â˜ƒxxxx = â˜ƒ.substring(â˜ƒxxxxxxxx[â˜ƒxx], â˜ƒxxxxxxxx[â˜ƒxx + 1]);
                  int â˜ƒxxxxx = (int)â˜ƒxxxxxxx.stringWidth(â˜ƒxxxx);
                  â˜ƒ.add(this.createSelection(new BookEditScreen.Pos2i(0, â˜ƒxxx), new BookEditScreen.Pos2i(â˜ƒxxxxx, â˜ƒxxx + 9)));
               }

               â˜ƒ.add(this.createPartialLineSelection(â˜ƒ, â˜ƒxxxxxxx, â˜ƒxxxxxxxx[â˜ƒxxxx], â˜ƒxx, â˜ƒxxxx * 9, â˜ƒxxxxxxxx[â˜ƒxxxx]));
            }
         }

         return new BookEditScreen.DisplayCache(
            â˜ƒ,
            â˜ƒ,
            â˜ƒxxxxxxxxx,
            â˜ƒxxxxxxxx,
            (BookEditScreen.LineInfo[])â˜ƒxxxx.toArray(new BookEditScreen.LineInfo[0]),
            (Rect2i[])â˜ƒ.toArray(new Rect2i[0])
         );
      }
   }

   static int findLineFromPos(int[] var0, int var1) {
      int â˜ƒ = Arrays.binarySearch(â˜ƒ, â˜ƒ);
      return â˜ƒ < 0 ? -(â˜ƒ + 2) : â˜ƒ;
   }

   private Rect2i createPartialLineSelection(String var1, StringSplitter var2, int var3, int var4, int var5, int var6) {
      String â˜ƒ = â˜ƒ.substring(â˜ƒ, â˜ƒ);
      String â˜ƒx = â˜ƒ.substring(â˜ƒ, â˜ƒ);
      BookEditScreen.Pos2i â˜ƒxx = new BookEditScreen.Pos2i((int)â˜ƒ.stringWidth(â˜ƒ), â˜ƒ);
      BookEditScreen.Pos2i â˜ƒxxx = new BookEditScreen.Pos2i((int)â˜ƒ.stringWidth(â˜ƒx), â˜ƒ + 9);
      return this.createSelection(â˜ƒxx, â˜ƒxxx);
   }

   private Rect2i createSelection(BookEditScreen.Pos2i var1, BookEditScreen.Pos2i var2) {
      BookEditScreen.Pos2i â˜ƒ = this.convertLocalToScreen(â˜ƒ);
      BookEditScreen.Pos2i â˜ƒx = this.convertLocalToScreen(â˜ƒ);
      int â˜ƒxx = Math.min(â˜ƒ.x, â˜ƒx.x);
      int â˜ƒxxx = Math.max(â˜ƒ.x, â˜ƒx.x);
      int â˜ƒxxxx = Math.min(â˜ƒ.y, â˜ƒx.y);
      int â˜ƒxxxxx = Math.max(â˜ƒ.y, â˜ƒx.y);
      return new Rect2i(â˜ƒxx, â˜ƒxxxx, â˜ƒxxx - â˜ƒxx, â˜ƒxxxxx - â˜ƒxxxx);
   }

   static class DisplayCache {
      static final BookEditScreen.DisplayCache EMPTY = new BookEditScreen.DisplayCache(
         "",
         new BookEditScreen.Pos2i(0, 0),
         true,
         new int[]{0},
         new BookEditScreen.LineInfo[]{new BookEditScreen.LineInfo(Style.EMPTY, "", 0, 0)},
         new Rect2i[0]
      );
      private final String fullText;
      final BookEditScreen.Pos2i cursor;
      final boolean cursorAtEnd;
      private final int[] lineStarts;
      final BookEditScreen.LineInfo[] lines;
      final Rect2i[] selection;

      public DisplayCache(String var1, BookEditScreen.Pos2i var2, boolean var3, int[] var4, BookEditScreen.LineInfo[] var5, Rect2i[] var6) {
         this.fullText = â˜ƒ;
         this.cursor = â˜ƒ;
         this.cursorAtEnd = â˜ƒ;
         this.lineStarts = â˜ƒ;
         this.lines = â˜ƒ;
         this.selection = â˜ƒ;
      }

      public int getIndexAtPosition(Font var1, BookEditScreen.Pos2i var2) {
         int â˜ƒ = â˜ƒ.y / 9;
         if (â˜ƒ < 0) {
            return 0;
         } else if (â˜ƒ >= this.lines.length) {
            return this.fullText.length();
         } else {
            BookEditScreen.LineInfo â˜ƒ = this.lines[â˜ƒ];
            return this.lineStarts[â˜ƒ] + â˜ƒ.getSplitter().plainIndexAtWidth(â˜ƒ.contents, â˜ƒ.x, â˜ƒ.style);
         }
      }

      public int changeLine(int var1, int var2) {
         int â˜ƒx = BookEditScreen.findLineFromPos(this.lineStarts, â˜ƒ);
         int â˜ƒxx = â˜ƒx + â˜ƒ;
         int â˜ƒ;
         if (0 <= â˜ƒxx && â˜ƒxx < this.lineStarts.length) {
            int â˜ƒxxx = â˜ƒ - this.lineStarts[â˜ƒx];
            int â˜ƒxxxx = this.lines[â˜ƒxx].contents.length();
            â˜ƒ = this.lineStarts[â˜ƒxx] + Math.min(â˜ƒxxx, â˜ƒxxxx);
         } else {
            â˜ƒ = â˜ƒ;
         }

         return â˜ƒ;
      }

      public int findLineStart(int var1) {
         int â˜ƒ = BookEditScreen.findLineFromPos(this.lineStarts, â˜ƒ);
         return this.lineStarts[â˜ƒ];
      }

      public int findLineEnd(int var1) {
         int â˜ƒ = BookEditScreen.findLineFromPos(this.lineStarts, â˜ƒ);
         return this.lineStarts[â˜ƒ] + this.lines[â˜ƒ].contents.length();
      }
   }

   static class LineInfo {
      final Style style;
      final String contents;
      final Component asComponent;
      final int x;
      final int y;

      public LineInfo(Style var1, String var2, int var3, int var4) {
         this.style = â˜ƒ;
         this.contents = â˜ƒ;
         this.x = â˜ƒ;
         this.y = â˜ƒ;
         this.asComponent = new TextComponent(â˜ƒ).setStyle(â˜ƒ);
      }
   }

   static class Pos2i {
      public final int x;
      public final int y;

      Pos2i(int var1, int var2) {
         this.x = â˜ƒ;
         this.y = â˜ƒ;
      }
   }
}
