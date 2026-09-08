package net.minecraft.client.gui.screens.inventory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.WrittenBookItem;

public class BookViewScreen extends Screen {
   public static final int PAGE_INDICATOR_TEXT_Y_OFFSET = 16;
   public static final int PAGE_TEXT_X_OFFSET = 36;
   public static final int PAGE_TEXT_Y_OFFSET = 30;
   public static final BookViewScreen.BookAccess EMPTY_ACCESS = new BookViewScreen.BookAccess() {
      @Override
      public int getPageCount() {
         return 0;
      }

      @Override
      public FormattedText getPageRaw(int var1) {
         return FormattedText.EMPTY;
      }
   };
   public static final ResourceLocation BOOK_LOCATION = new ResourceLocation("textures/gui/book.png");
   protected static final int TEXT_WIDTH = 114;
   protected static final int TEXT_HEIGHT = 128;
   protected static final int IMAGE_WIDTH = 192;
   protected static final int IMAGE_HEIGHT = 192;
   private BookViewScreen.BookAccess bookAccess;
   private int currentPage;
   private List<FormattedCharSequence> cachedPageComponents = Collections.emptyList();
   private int cachedPage = -1;
   private Component pageMsg = TextComponent.EMPTY;
   private PageButton forwardButton;
   private PageButton backButton;
   private final boolean playTurnSound;

   public BookViewScreen(BookViewScreen.BookAccess var1) {
      this(â˜ƒ, true);
   }

   public BookViewScreen() {
      this(EMPTY_ACCESS, false);
   }

   private BookViewScreen(BookViewScreen.BookAccess var1, boolean var2) {
      super(NarratorChatListener.NO_TITLE);
      this.bookAccess = â˜ƒ;
      this.playTurnSound = â˜ƒ;
   }

   public void setBookAccess(BookViewScreen.BookAccess var1) {
      this.bookAccess = â˜ƒ;
      this.currentPage = Mth.clamp(this.currentPage, 0, â˜ƒ.getPageCount());
      this.updateButtonVisibility();
      this.cachedPage = -1;
   }

   public boolean setPage(int var1) {
      int â˜ƒ = Mth.clamp(â˜ƒ, 0, this.bookAccess.getPageCount() - 1);
      if (â˜ƒ != this.currentPage) {
         this.currentPage = â˜ƒ;
         this.updateButtonVisibility();
         this.cachedPage = -1;
         return true;
      } else {
         return false;
      }
   }

   protected boolean forcePage(int var1) {
      return this.setPage(â˜ƒ);
   }

   @Override
   protected void init() {
      this.createMenuControls();
      this.createPageControlButtons();
   }

   protected void createMenuControls() {
      this.addRenderableWidget(new Button(this.width / 2 - 100, 196, 200, 20, CommonComponents.GUI_DONE, var1 -> this.minecraft.setScreen(null)));
   }

   protected void createPageControlButtons() {
      int â˜ƒ = (this.width - 192) / 2;
      int â˜ƒx = 2;
      this.forwardButton = this.addRenderableWidget(new PageButton(â˜ƒ + 116, 159, true, var1x -> this.pageForward(), this.playTurnSound));
      this.backButton = this.addRenderableWidget(new PageButton(â˜ƒ + 43, 159, false, var1x -> this.pageBack(), this.playTurnSound));
      this.updateButtonVisibility();
   }

   private int getNumPages() {
      return this.bookAccess.getPageCount();
   }

   protected void pageBack() {
      if (this.currentPage > 0) {
         --this.currentPage;
      }

      this.updateButtonVisibility();
   }

   protected void pageForward() {
      if (this.currentPage < this.getNumPages() - 1) {
         ++this.currentPage;
      }

      this.updateButtonVisibility();
   }

   private void updateButtonVisibility() {
      this.forwardButton.visible = this.currentPage < this.getNumPages() - 1;
      this.backButton.visible = this.currentPage > 0;
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ)) {
         return true;
      } else {
         switch(â˜ƒ) {
            case 266:
               this.backButton.onPress();
               return true;
            case 267:
               this.forwardButton.onPress();
               return true;
            default:
               return false;
         }
      }
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShaderTexture(0, BOOK_LOCATION);
      int â˜ƒ = (this.width - 192) / 2;
      int â˜ƒx = 2;
      this.blit(â˜ƒ, â˜ƒ, 2, 0, 0, 192, 192);
      if (this.cachedPage != this.currentPage) {
         FormattedText â˜ƒxx = this.bookAccess.getPage(this.currentPage);
         this.cachedPageComponents = this.font.split(â˜ƒxx, 114);
         this.pageMsg = new TranslatableComponent("book.pageIndicator", this.currentPage + 1, Math.max(this.getNumPages(), 1));
      }

      this.cachedPage = this.currentPage;
      int â˜ƒ = this.font.width(this.pageMsg);
      this.font.draw(â˜ƒ, this.pageMsg, (float)(â˜ƒ - â˜ƒ + 192 - 44), 18.0F, 0);
      int â˜ƒx = Math.min(128 / 9, this.cachedPageComponents.size());

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx; ++â˜ƒxx) {
         FormattedCharSequence â˜ƒxxx = (FormattedCharSequence)this.cachedPageComponents.get(â˜ƒxx);
         this.font.draw(â˜ƒ, â˜ƒxxx, (float)(â˜ƒ + 36), (float)(32 + â˜ƒxx * 9), 0);
      }

      Style â˜ƒxx = this.getClickedComponentStyleAt((double)â˜ƒ, (double)â˜ƒ);
      if (â˜ƒxx != null) {
         this.renderComponentHoverEffect(â˜ƒ, â˜ƒxx, â˜ƒ, â˜ƒ);
      }

      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (â˜ƒ == 0) {
         Style â˜ƒ = this.getClickedComponentStyleAt(â˜ƒ, â˜ƒ);
         if (â˜ƒ != null && this.handleComponentClicked(â˜ƒ)) {
            return true;
         }
      }

      return super.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean handleComponentClicked(Style var1) {
      ClickEvent â˜ƒ = â˜ƒ.getClickEvent();
      if (â˜ƒ == null) {
         return false;
      } else if (â˜ƒ.getAction() == ClickEvent.Action.CHANGE_PAGE) {
         String â˜ƒ = â˜ƒ.getValue();

         try {
            int â˜ƒx = Integer.parseInt(â˜ƒ) - 1;
            return this.forcePage(â˜ƒx);
         } catch (Exception var5) {
            return false;
         }
      } else {
         boolean â˜ƒ = super.handleComponentClicked(â˜ƒ);
         if (â˜ƒ && â˜ƒ.getAction() == ClickEvent.Action.RUN_COMMAND) {
            this.closeScreen();
         }

         return â˜ƒ;
      }
   }

   protected void closeScreen() {
      this.minecraft.setScreen(null);
   }

   @Nullable
   public Style getClickedComponentStyleAt(double var1, double var3) {
      if (this.cachedPageComponents.isEmpty()) {
         return null;
      } else {
         int â˜ƒ = Mth.floor(â˜ƒ - (double)((this.width - 192) / 2) - 36.0);
         int â˜ƒx = Mth.floor(â˜ƒ - 2.0 - 30.0);
         if (â˜ƒ >= 0 && â˜ƒx >= 0) {
            int â˜ƒxx = Math.min(128 / 9, this.cachedPageComponents.size());
            if (â˜ƒ <= 114 && â˜ƒx < 9 * â˜ƒxx + â˜ƒxx) {
               int â˜ƒxxx = â˜ƒx / 9;
               if (â˜ƒxxx >= 0 && â˜ƒxxx < this.cachedPageComponents.size()) {
                  FormattedCharSequence â˜ƒxxxx = (FormattedCharSequence)this.cachedPageComponents.get(â˜ƒxxx);
                  return this.minecraft.font.getSplitter().componentStyleAtWidth(â˜ƒxxxx, â˜ƒ);
               } else {
                  return null;
               }
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   static List<String> loadPages(CompoundTag var0) {
      Builder<String> â˜ƒ = ImmutableList.builder();
      loadPages(â˜ƒ, â˜ƒ::add);
      return â˜ƒ.build();
   }

   public static void loadPages(CompoundTag var0, Consumer<String> var1) {
      ListTag â˜ƒx = â˜ƒ.getList("pages", 8).copy();
      IntFunction<String> â˜ƒ;
      if (Minecraft.getInstance().isTextFilteringEnabled() && â˜ƒ.contains("filtered_pages", 10)) {
         CompoundTag â˜ƒxx = â˜ƒ.getCompound("filtered_pages");
         â˜ƒ = var2x -> {
            String â˜ƒ = String.valueOf(var2x);
            return â˜ƒ.contains(â˜ƒ) ? â˜ƒ.getString(â˜ƒ) : â˜ƒ.getString(var2x);
         };
      } else {
         â˜ƒ = â˜ƒx::getString;
      }

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒx.size(); ++â˜ƒ) {
         â˜ƒ.accept((String)â˜ƒ.apply(â˜ƒ));
      }
   }

   public interface BookAccess {
      int getPageCount();

      FormattedText getPageRaw(int var1);

      default FormattedText getPage(int var1) {
         return â˜ƒ >= 0 && â˜ƒ < this.getPageCount() ? this.getPageRaw(â˜ƒ) : FormattedText.EMPTY;
      }

      static BookViewScreen.BookAccess fromItem(ItemStack var0) {
         if (â˜ƒ.is(Items.WRITTEN_BOOK)) {
            return new BookViewScreen.WrittenBookAccess(â˜ƒ);
         } else {
            return (BookViewScreen.BookAccess)(â˜ƒ.is(Items.WRITABLE_BOOK) ? new BookViewScreen.WritableBookAccess(â˜ƒ) : BookViewScreen.EMPTY_ACCESS);
         }
      }
   }

   public static class WritableBookAccess implements BookViewScreen.BookAccess {
      private final List<String> pages;

      public WritableBookAccess(ItemStack var1) {
         this.pages = readPages(â˜ƒ);
      }

      private static List<String> readPages(ItemStack var0) {
         CompoundTag â˜ƒ = â˜ƒ.getTag();
         return (List<String>)(â˜ƒ != null ? BookViewScreen.loadPages(â˜ƒ) : ImmutableList.of());
      }

      @Override
      public int getPageCount() {
         return this.pages.size();
      }

      @Override
      public FormattedText getPageRaw(int var1) {
         return FormattedText.of((String)this.pages.get(â˜ƒ));
      }
   }

   public static class WrittenBookAccess implements BookViewScreen.BookAccess {
      private final List<String> pages;

      public WrittenBookAccess(ItemStack var1) {
         this.pages = readPages(â˜ƒ);
      }

      private static List<String> readPages(ItemStack var0) {
         CompoundTag â˜ƒ = â˜ƒ.getTag();
         return (List<String>)(â˜ƒ != null && WrittenBookItem.makeSureTagIsValid(â˜ƒ)
            ? BookViewScreen.loadPages(â˜ƒ)
            : ImmutableList.of(Component.Serializer.toJson(new TranslatableComponent("book.invalid.tag").withStyle(ChatFormatting.DARK_RED))));
      }

      @Override
      public int getPageCount() {
         return this.pages.size();
      }

      @Override
      public FormattedText getPageRaw(int var1) {
         String â˜ƒ = (String)this.pages.get(â˜ƒ);

         try {
            FormattedText â˜ƒx = Component.Serializer.fromJson(â˜ƒ);
            if (â˜ƒx != null) {
               return â˜ƒx;
            }
         } catch (Exception var4) {
         }

         return FormattedText.of(â˜ƒ);
      }
   }
}
