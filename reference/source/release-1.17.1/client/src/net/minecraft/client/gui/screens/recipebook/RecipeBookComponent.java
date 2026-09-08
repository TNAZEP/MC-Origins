package net.minecraft.client.gui.screens.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.LanguageInfo;
import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.client.searchtree.SearchRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ServerboundRecipeBookChangeSettingsPacket;
import net.minecraft.recipebook.PlaceRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public class RecipeBookComponent extends GuiComponent implements Widget, GuiEventListener, NarratableEntry, RecipeShownListener, PlaceRecipe<Ingredient> {
   protected static final ResourceLocation RECIPE_BOOK_LOCATION = new ResourceLocation("textures/gui/recipe_book.png");
   private static final Component SEARCH_HINT = new TranslatableComponent("gui.recipebook.search_hint")
      .withStyle(ChatFormatting.ITALIC)
      .withStyle(ChatFormatting.GRAY);
   public static final int IMAGE_WIDTH = 147;
   public static final int IMAGE_HEIGHT = 166;
   private static final int OFFSET_X_POSITION = 86;
   private static final Component ONLY_CRAFTABLES_TOOLTIP = new TranslatableComponent("gui.recipebook.toggleRecipes.craftable");
   private static final Component ALL_RECIPES_TOOLTIP = new TranslatableComponent("gui.recipebook.toggleRecipes.all");
   private int xOffset;
   private int width;
   private int height;
   protected final GhostRecipe ghostRecipe = new GhostRecipe();
   private final List<RecipeBookTabButton> tabButtons = Lists.<RecipeBookTabButton>newArrayList();
   @Nullable
   private RecipeBookTabButton selectedTab;
   protected StateSwitchingButton filterButton;
   protected RecipeBookMenu<?> menu;
   protected Minecraft minecraft;
   @Nullable
   private EditBox searchBox;
   private String lastSearch = "";
   private ClientRecipeBook book;
   private final RecipeBookPage recipeBookPage = new RecipeBookPage();
   private final StackedContents stackedContents = new StackedContents();
   private int timesInventoryChanged;
   private boolean ignoreTextInput;
   private boolean visible;
   private boolean widthTooNarrow;

   public void init(int var1, int var2, Minecraft var3, boolean var4, RecipeBookMenu<?> var5) {
      this.minecraft = â˜ƒ;
      this.width = â˜ƒ;
      this.height = â˜ƒ;
      this.menu = â˜ƒ;
      this.widthTooNarrow = â˜ƒ;
      â˜ƒ.player.containerMenu = â˜ƒ;
      this.book = â˜ƒ.player.getRecipeBook();
      this.timesInventoryChanged = â˜ƒ.player.getInventory().getTimesChanged();
      this.visible = this.isVisibleAccordingToBookData();
      if (this.visible) {
         this.initVisuals();
      }

      â˜ƒ.keyboardHandler.setSendRepeatsToGui(true);
   }

   public void initVisuals() {
      this.xOffset = this.widthTooNarrow ? 0 : 86;
      int â˜ƒ = (this.width - 147) / 2 - this.xOffset;
      int â˜ƒx = (this.height - 166) / 2;
      this.stackedContents.clear();
      this.minecraft.player.getInventory().fillStackedContents(this.stackedContents);
      this.menu.fillCraftSlotsStackedContents(this.stackedContents);
      String â˜ƒxx = this.searchBox != null ? this.searchBox.getValue() : "";
      this.searchBox = new EditBox(this.minecraft.font, â˜ƒ + 25, â˜ƒx + 14, 80, 9 + 5, new TranslatableComponent("itemGroup.search"));
      this.searchBox.setMaxLength(50);
      this.searchBox.setBordered(false);
      this.searchBox.setVisible(true);
      this.searchBox.setTextColor(16777215);
      this.searchBox.setValue(â˜ƒxx);
      this.recipeBookPage.init(this.minecraft, â˜ƒ, â˜ƒx);
      this.recipeBookPage.addListener(this);
      this.filterButton = new StateSwitchingButton(â˜ƒ + 110, â˜ƒx + 12, 26, 16, this.book.isFiltering(this.menu));
      this.initFilterButtonTextures();
      this.tabButtons.clear();

      for(RecipeBookCategories â˜ƒxxx : RecipeBookCategories.getCategories(this.menu.getRecipeBookType())) {
         this.tabButtons.add(new RecipeBookTabButton(â˜ƒxxx));
      }

      if (this.selectedTab != null) {
         this.selectedTab = (RecipeBookTabButton)this.tabButtons
            .stream()
            .filter(var1x -> var1x.getCategory().equals(this.selectedTab.getCategory()))
            .findFirst()
            .orElse(null);
      }

      if (this.selectedTab == null) {
         this.selectedTab = (RecipeBookTabButton)this.tabButtons.get(0);
      }

      this.selectedTab.setStateTriggered(true);
      this.updateCollections(false);
      this.updateTabs();
   }

   @Override
   public boolean changeFocus(boolean var1) {
      return false;
   }

   protected void initFilterButtonTextures() {
      this.filterButton.initTextureValues(152, 41, 28, 18, RECIPE_BOOK_LOCATION);
   }

   public void removed() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
   }

   public int updateScreenPosition(int var1, int var2) {
      int â˜ƒ;
      if (this.isVisible() && !this.widthTooNarrow) {
         â˜ƒ = 177 + (â˜ƒ - â˜ƒ - 200) / 2;
      } else {
         â˜ƒ = (â˜ƒ - â˜ƒ) / 2;
      }

      return â˜ƒ;
   }

   public void toggleVisibility() {
      this.setVisible(!this.isVisible());
   }

   public boolean isVisible() {
      return this.visible;
   }

   private boolean isVisibleAccordingToBookData() {
      return this.book.isOpen(this.menu.getRecipeBookType());
   }

   protected void setVisible(boolean var1) {
      if (â˜ƒ) {
         this.initVisuals();
      }

      this.visible = â˜ƒ;
      this.book.setOpen(this.menu.getRecipeBookType(), â˜ƒ);
      if (!â˜ƒ) {
         this.recipeBookPage.setInvisible();
      }

      this.sendUpdateSettings();
   }

   public void slotClicked(@Nullable Slot var1) {
      if (â˜ƒ != null && â˜ƒ.index < this.menu.getSize()) {
         this.ghostRecipe.clear();
         if (this.isVisible()) {
            this.updateStackedContents();
         }
      }
   }

   private void updateCollections(boolean var1) {
      List<RecipeCollection> â˜ƒ = this.book.getCollection(this.selectedTab.getCategory());
      â˜ƒ.forEach(var1x -> var1x.canCraft(this.stackedContents, this.menu.getGridWidth(), this.menu.getGridHeight(), this.book));
      List<RecipeCollection> â˜ƒx = Lists.<RecipeCollection>newArrayList(â˜ƒ);
      â˜ƒx.removeIf(var0 -> !var0.hasKnownRecipes());
      â˜ƒx.removeIf(var0 -> !var0.hasFitting());
      String â˜ƒxx = this.searchBox.getValue();
      if (!â˜ƒxx.isEmpty()) {
         ObjectSet<RecipeCollection> â˜ƒxxx = new ObjectLinkedOpenHashSet<>(
            this.minecraft.getSearchTree(SearchRegistry.RECIPE_COLLECTIONS).search(â˜ƒxx.toLowerCase(Locale.ROOT))
         );
         â˜ƒx.removeIf(var1x -> !â˜ƒ.contains(var1x));
      }

      if (this.book.isFiltering(this.menu)) {
         â˜ƒx.removeIf(var0 -> !var0.hasCraftable());
      }

      this.recipeBookPage.updateCollections(â˜ƒx, â˜ƒ);
   }

   private void updateTabs() {
      int â˜ƒ = (this.width - 147) / 2 - this.xOffset - 30;
      int â˜ƒx = (this.height - 166) / 2 + 3;
      int â˜ƒxx = 27;
      int â˜ƒxxx = 0;

      for(RecipeBookTabButton â˜ƒxxxx : this.tabButtons) {
         RecipeBookCategories â˜ƒxxxxx = â˜ƒxxxx.getCategory();
         if (â˜ƒxxxxx == RecipeBookCategories.CRAFTING_SEARCH || â˜ƒxxxxx == RecipeBookCategories.FURNACE_SEARCH) {
            â˜ƒxxxx.visible = true;
            â˜ƒxxxx.setPosition(â˜ƒ, â˜ƒx + 27 * â˜ƒxxx++);
         } else if (â˜ƒxxxx.updateVisibility(this.book)) {
            â˜ƒxxxx.setPosition(â˜ƒ, â˜ƒx + 27 * â˜ƒxxx++);
            â˜ƒxxxx.startAnimation(this.minecraft);
         }
      }
   }

   public void tick() {
      boolean â˜ƒ = this.isVisibleAccordingToBookData();
      if (this.isVisible() != â˜ƒ) {
         this.setVisible(â˜ƒ);
      }

      if (this.isVisible()) {
         if (this.timesInventoryChanged != this.minecraft.player.getInventory().getTimesChanged()) {
            this.updateStackedContents();
            this.timesInventoryChanged = this.minecraft.player.getInventory().getTimesChanged();
         }

         this.searchBox.tick();
      }
   }

   private void updateStackedContents() {
      this.stackedContents.clear();
      this.minecraft.player.getInventory().fillStackedContents(this.stackedContents);
      this.menu.fillCraftSlotsStackedContents(this.stackedContents);
      this.updateCollections(false);
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      if (this.isVisible()) {
         â˜ƒ.pushPose();
         â˜ƒ.translate(0.0, 0.0, 100.0);
         RenderSystem.setShader(GameRenderer::getPositionTexShader);
         RenderSystem.setShaderTexture(0, RECIPE_BOOK_LOCATION);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         int â˜ƒ = (this.width - 147) / 2 - this.xOffset;
         int â˜ƒx = (this.height - 166) / 2;
         this.blit(â˜ƒ, â˜ƒ, â˜ƒx, 1, 1, 147, 166);
         if (!this.searchBox.isFocused() && this.searchBox.getValue().isEmpty()) {
            drawString(â˜ƒ, this.minecraft.font, SEARCH_HINT, â˜ƒ + 25, â˜ƒx + 14, -1);
         } else {
            this.searchBox.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }

         for(RecipeBookTabButton â˜ƒ : this.tabButtons) {
            â˜ƒ.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }

         this.filterButton.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.recipeBookPage.render(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.popPose();
      }
   }

   public void renderTooltip(PoseStack var1, int var2, int var3, int var4, int var5) {
      if (this.isVisible()) {
         this.recipeBookPage.renderTooltip(â˜ƒ, â˜ƒ, â˜ƒ);
         if (this.filterButton.isHovered()) {
            Component â˜ƒ = this.getFilterButtonTooltip();
            if (this.minecraft.screen != null) {
               this.minecraft.screen.renderTooltip(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
            }
         }

         this.renderGhostRecipeTooltip(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private Component getFilterButtonTooltip() {
      return this.filterButton.isStateTriggered() ? this.getRecipeFilterName() : ALL_RECIPES_TOOLTIP;
   }

   protected Component getRecipeFilterName() {
      return ONLY_CRAFTABLES_TOOLTIP;
   }

   private void renderGhostRecipeTooltip(PoseStack var1, int var2, int var3, int var4, int var5) {
      ItemStack â˜ƒ = null;

      for(int â˜ƒx = 0; â˜ƒx < this.ghostRecipe.size(); ++â˜ƒx) {
         GhostRecipe.GhostIngredient â˜ƒxx = this.ghostRecipe.get(â˜ƒx);
         int â˜ƒxxx = â˜ƒxx.getX() + â˜ƒ;
         int â˜ƒxxxx = â˜ƒxx.getY() + â˜ƒ;
         if (â˜ƒ >= â˜ƒxxx && â˜ƒ >= â˜ƒxxxx && â˜ƒ < â˜ƒxxx + 16 && â˜ƒ < â˜ƒxxxx + 16) {
            â˜ƒ = â˜ƒxx.getItem();
         }
      }

      if (â˜ƒ != null && this.minecraft.screen != null) {
         this.minecraft.screen.renderComponentTooltip(â˜ƒ, this.minecraft.screen.getTooltipFromItem(â˜ƒ), â˜ƒ, â˜ƒ);
      }
   }

   public void renderGhostRecipe(PoseStack var1, int var2, int var3, boolean var4, float var5) {
      this.ghostRecipe.render(â˜ƒ, this.minecraft, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (this.isVisible() && !this.minecraft.player.isSpectator()) {
         if (this.recipeBookPage.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ, (this.width - 147) / 2 - this.xOffset, (this.height - 166) / 2, 147, 166)) {
            Recipe<?> â˜ƒ = this.recipeBookPage.getLastClickedRecipe();
            RecipeCollection â˜ƒx = this.recipeBookPage.getLastClickedRecipeCollection();
            if (â˜ƒ != null && â˜ƒx != null) {
               if (!â˜ƒx.isCraftable(â˜ƒ) && this.ghostRecipe.getRecipe() == â˜ƒ) {
                  return false;
               }

               this.ghostRecipe.clear();
               this.minecraft.gameMode.handlePlaceRecipe(this.minecraft.player.containerMenu.containerId, â˜ƒ, Screen.hasShiftDown());
               if (!this.isOffsetNextToMainGUI()) {
                  this.setVisible(false);
               }
            }

            return true;
         } else if (this.searchBox.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ)) {
            return true;
         } else if (this.filterButton.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ)) {
            boolean â˜ƒ = this.toggleFiltering();
            this.filterButton.setStateTriggered(â˜ƒ);
            this.sendUpdateSettings();
            this.updateCollections(false);
            return true;
         } else {
            for(RecipeBookTabButton â˜ƒ : this.tabButtons) {
               if (â˜ƒ.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ)) {
                  if (this.selectedTab != â˜ƒ) {
                     if (this.selectedTab != null) {
                        this.selectedTab.setStateTriggered(false);
                     }

                     this.selectedTab = â˜ƒ;
                     this.selectedTab.setStateTriggered(true);
                     this.updateCollections(true);
                  }

                  return true;
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   private boolean toggleFiltering() {
      RecipeBookType â˜ƒ = this.menu.getRecipeBookType();
      boolean â˜ƒx = !this.book.isFiltering(â˜ƒ);
      this.book.setFiltering(â˜ƒ, â˜ƒx);
      return â˜ƒx;
   }

   public boolean hasClickedOutside(double var1, double var3, int var5, int var6, int var7, int var8, int var9) {
      if (!this.isVisible()) {
         return true;
      } else {
         boolean â˜ƒ = â˜ƒ < (double)â˜ƒ || â˜ƒ < (double)â˜ƒ || â˜ƒ >= (double)(â˜ƒ + â˜ƒ) || â˜ƒ >= (double)(â˜ƒ + â˜ƒ);
         boolean â˜ƒx = (double)(â˜ƒ - 147) < â˜ƒ && â˜ƒ < (double)â˜ƒ && (double)â˜ƒ < â˜ƒ && â˜ƒ < (double)(â˜ƒ + â˜ƒ);
         return â˜ƒ && !â˜ƒx && !this.selectedTab.isHovered();
      }
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      this.ignoreTextInput = false;
      if (!this.isVisible() || this.minecraft.player.isSpectator()) {
         return false;
      } else if (â˜ƒ == 256 && !this.isOffsetNextToMainGUI()) {
         this.setVisible(false);
         return true;
      } else if (this.searchBox.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ)) {
         this.checkSearchStringUpdate();
         return true;
      } else if (this.searchBox.isFocused() && this.searchBox.isVisible() && â˜ƒ != 256) {
         return true;
      } else if (this.minecraft.options.keyChat.matches(â˜ƒ, â˜ƒ) && !this.searchBox.isFocused()) {
         this.ignoreTextInput = true;
         this.searchBox.setFocus(true);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean keyReleased(int var1, int var2, int var3) {
      this.ignoreTextInput = false;
      return GuiEventListener.super.keyReleased(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean charTyped(char var1, int var2) {
      if (this.ignoreTextInput) {
         return false;
      } else if (!this.isVisible() || this.minecraft.player.isSpectator()) {
         return false;
      } else if (this.searchBox.charTyped(â˜ƒ, â˜ƒ)) {
         this.checkSearchStringUpdate();
         return true;
      } else {
         return GuiEventListener.super.charTyped(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public boolean isMouseOver(double var1, double var3) {
      return false;
   }

   private void checkSearchStringUpdate() {
      String â˜ƒ = this.searchBox.getValue().toLowerCase(Locale.ROOT);
      this.pirateSpeechForThePeople(â˜ƒ);
      if (!â˜ƒ.equals(this.lastSearch)) {
         this.updateCollections(false);
         this.lastSearch = â˜ƒ;
      }
   }

   private void pirateSpeechForThePeople(String var1) {
      if ("excitedze".equals(â˜ƒ)) {
         LanguageManager â˜ƒ = this.minecraft.getLanguageManager();
         LanguageInfo â˜ƒx = â˜ƒ.getLanguage("en_pt");
         if (â˜ƒ.getSelected().compareTo(â˜ƒx) == 0) {
            return;
         }

         â˜ƒ.setSelected(â˜ƒx);
         this.minecraft.options.languageCode = â˜ƒx.getCode();
         this.minecraft.reloadResourcePacks();
         this.minecraft.options.save();
      }
   }

   private boolean isOffsetNextToMainGUI() {
      return this.xOffset == 86;
   }

   public void recipesUpdated() {
      this.updateTabs();
      if (this.isVisible()) {
         this.updateCollections(false);
      }
   }

   @Override
   public void recipesShown(List<Recipe<?>> var1) {
      for(Recipe<?> â˜ƒ : â˜ƒ) {
         this.minecraft.player.removeRecipeHighlight(â˜ƒ);
      }
   }

   public void setupGhostRecipe(Recipe<?> var1, List<Slot> var2) {
      ItemStack â˜ƒ = â˜ƒ.getResultItem();
      this.ghostRecipe.setRecipe(â˜ƒ);
      this.ghostRecipe.addIngredient(Ingredient.of(â˜ƒ), ((Slot)â˜ƒ.get(0)).x, ((Slot)â˜ƒ.get(0)).y);
      this.placeRecipe(this.menu.getGridWidth(), this.menu.getGridHeight(), this.menu.getResultSlotIndex(), â˜ƒ, â˜ƒ.getIngredients().iterator(), 0);
   }

   @Override
   public void addItemToSlot(Iterator<Ingredient> var1, int var2, int var3, int var4, int var5) {
      Ingredient â˜ƒ = (Ingredient)â˜ƒ.next();
      if (!â˜ƒ.isEmpty()) {
         Slot â˜ƒx = this.menu.slots.get(â˜ƒ);
         this.ghostRecipe.addIngredient(â˜ƒ, â˜ƒx.x, â˜ƒx.y);
      }
   }

   protected void sendUpdateSettings() {
      if (this.minecraft.getConnection() != null) {
         RecipeBookType â˜ƒ = this.menu.getRecipeBookType();
         boolean â˜ƒx = this.book.getBookSettings().isOpen(â˜ƒ);
         boolean â˜ƒxx = this.book.getBookSettings().isFiltering(â˜ƒ);
         this.minecraft.getConnection().send(new ServerboundRecipeBookChangeSettingsPacket(â˜ƒ, â˜ƒx, â˜ƒxx));
      }
   }

   @Override
   public NarratableEntry.NarrationPriority narrationPriority() {
      return this.visible ? NarratableEntry.NarrationPriority.HOVERED : NarratableEntry.NarrationPriority.NONE;
   }

   @Override
   public void updateNarration(NarrationElementOutput var1) {
      List<NarratableEntry> â˜ƒ = Lists.<NarratableEntry>newArrayList();
      this.recipeBookPage.listButtons(var1x -> {
         if (var1x.isActive()) {
            â˜ƒ.add(var1x);
         }
      });
      â˜ƒ.add(this.searchBox);
      â˜ƒ.add(this.filterButton);
      â˜ƒ.addAll(this.tabButtons);
      Screen.NarratableSearchResult â˜ƒx = Screen.findNarratableWidget(â˜ƒ, null);
      if (â˜ƒx != null) {
         â˜ƒx.entry.updateNarration(â˜ƒ.nest());
      }
   }
}
