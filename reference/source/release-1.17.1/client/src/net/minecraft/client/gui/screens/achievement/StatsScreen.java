package net.minecraft.client.gui.screens.achievement;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraft.stats.StatsCounter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

public class StatsScreen extends Screen implements StatsUpdateListener {
   private static final Component PENDING_TEXT = new TranslatableComponent("multiplayer.downloadingStats");
   protected final Screen lastScreen;
   private StatsScreen.GeneralStatisticsList statsList;
   StatsScreen.ItemStatisticsList itemStatsList;
   private StatsScreen.MobsStatisticsList mobsStatsList;
   final StatsCounter stats;
   @Nullable
   private ObjectSelectionList<?> activeList;
   private boolean isLoading = true;
   private static final int SLOT_TEX_SIZE = 128;
   private static final int SLOT_BG_SIZE = 18;
   private static final int SLOT_STAT_HEIGHT = 20;
   private static final int SLOT_BG_X = 1;
   private static final int SLOT_BG_Y = 1;
   private static final int SLOT_FG_X = 2;
   private static final int SLOT_FG_Y = 2;
   private static final int SLOT_LEFT_INSERT = 40;
   private static final int SLOT_TEXT_OFFSET = 5;
   private static final int SORT_NONE = 0;
   private static final int SORT_DOWN = -1;
   private static final int SORT_UP = 1;

   public StatsScreen(Screen var1, StatsCounter var2) {
      super(new TranslatableComponent("gui.stats"));
      this.lastScreen = â˜ƒ;
      this.stats = â˜ƒ;
   }

   @Override
   protected void init() {
      this.isLoading = true;
      this.minecraft.getConnection().send(new ServerboundClientCommandPacket(ServerboundClientCommandPacket.Action.REQUEST_STATS));
   }

   public void initLists() {
      this.statsList = new StatsScreen.GeneralStatisticsList(this.minecraft);
      this.itemStatsList = new StatsScreen.ItemStatisticsList(this.minecraft);
      this.mobsStatsList = new StatsScreen.MobsStatisticsList(this.minecraft);
   }

   public void initButtons() {
      this.addRenderableWidget(
         new Button(
            this.width / 2 - 120, this.height - 52, 80, 20, new TranslatableComponent("stat.generalButton"), var1x -> this.setActiveList(this.statsList)
         )
      );
      Button â˜ƒ = this.addRenderableWidget(
         new Button(
            this.width / 2 - 40, this.height - 52, 80, 20, new TranslatableComponent("stat.itemsButton"), var1x -> this.setActiveList(this.itemStatsList)
         )
      );
      Button â˜ƒx = this.addRenderableWidget(
         new Button(
            this.width / 2 + 40, this.height - 52, 80, 20, new TranslatableComponent("stat.mobsButton"), var1x -> this.setActiveList(this.mobsStatsList)
         )
      );
      this.addRenderableWidget(
         new Button(this.width / 2 - 100, this.height - 28, 200, 20, CommonComponents.GUI_DONE, var1x -> this.minecraft.setScreen(this.lastScreen))
      );
      if (this.itemStatsList.children().isEmpty()) {
         â˜ƒ.active = false;
      }

      if (this.mobsStatsList.children().isEmpty()) {
         â˜ƒx.active = false;
      }
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      if (this.isLoading) {
         this.renderBackground(â˜ƒ);
         drawCenteredString(â˜ƒ, this.font, PENDING_TEXT, this.width / 2, this.height / 2, 16777215);
         drawCenteredString(
            â˜ƒ, this.font, LOADING_SYMBOLS[(int)(Util.getMillis() / 150L % (long)LOADING_SYMBOLS.length)], this.width / 2, this.height / 2 + 9 * 2, 16777215
         );
      } else {
         this.getActiveList().render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 20, 16777215);
         super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void onStatsUpdated() {
      if (this.isLoading) {
         this.initLists();
         this.initButtons();
         this.setActiveList(this.statsList);
         this.isLoading = false;
      }
   }

   @Override
   public boolean isPauseScreen() {
      return !this.isLoading;
   }

   @Nullable
   public ObjectSelectionList<?> getActiveList() {
      return this.activeList;
   }

   public void setActiveList(@Nullable ObjectSelectionList<?> var1) {
      if (this.activeList != null) {
         this.removeWidget(this.activeList);
      }

      if (â˜ƒ != null) {
         this.addWidget(â˜ƒ);
         this.activeList = â˜ƒ;
      }
   }

   static String getTranslationKey(Stat<ResourceLocation> var0) {
      return "stat." + â˜ƒ.getValue().toString().replace(':', '.');
   }

   int getColumnX(int var1) {
      return 115 + 40 * â˜ƒ;
   }

   void blitSlot(PoseStack var1, int var2, int var3, Item var4) {
      this.blitSlotIcon(â˜ƒ, â˜ƒ + 1, â˜ƒ + 1, 0, 0);
      this.itemRenderer.renderGuiItem(â˜ƒ.getDefaultInstance(), â˜ƒ + 2, â˜ƒ + 2);
   }

   void blitSlotIcon(PoseStack var1, int var2, int var3, int var4, int var5) {
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderTexture(0, STATS_ICON_LOCATION);
      blit(â˜ƒ, â˜ƒ, â˜ƒ, this.getBlitOffset(), (float)â˜ƒ, (float)â˜ƒ, 18, 18, 128, 128);
   }

   class GeneralStatisticsList extends ObjectSelectionList<StatsScreen.GeneralStatisticsList.Entry> {
      public GeneralStatisticsList(Minecraft var2) {
         super(â˜ƒ, StatsScreen.this.width, StatsScreen.this.height, 32, StatsScreen.this.height - 64, 10);
         ObjectArrayList<Stat<ResourceLocation>> â˜ƒ = new ObjectArrayList<>(Stats.CUSTOM.iterator());
         â˜ƒ.sort(Comparator.comparing(var0 -> I18n.get(StatsScreen.getTranslationKey(var0))));

         for(Stat<ResourceLocation> â˜ƒx : â˜ƒ) {
            this.addEntry(new StatsScreen.GeneralStatisticsList.Entry(â˜ƒx));
         }
      }

      @Override
      protected void renderBackground(PoseStack var1) {
         StatsScreen.this.renderBackground(â˜ƒ);
      }

      class Entry extends ObjectSelectionList.Entry<StatsScreen.GeneralStatisticsList.Entry> {
         private final Stat<ResourceLocation> stat;
         private final Component statDisplay;

         Entry(Stat<ResourceLocation> var2) {
            this.stat = â˜ƒ;
            this.statDisplay = new TranslatableComponent(StatsScreen.getTranslationKey(â˜ƒ));
         }

         private String getValueText() {
            return this.stat.format(StatsScreen.this.stats.getValue(this.stat));
         }

         @Override
         public void render(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
            GuiComponent.drawString(â˜ƒ, StatsScreen.this.font, this.statDisplay, â˜ƒ + 2, â˜ƒ + 1, â˜ƒ % 2 == 0 ? 16777215 : 9474192);
            String â˜ƒ = this.getValueText();
            GuiComponent.drawString(
               â˜ƒ, StatsScreen.this.font, â˜ƒ, â˜ƒ + 2 + 213 - StatsScreen.this.font.width(â˜ƒ), â˜ƒ + 1, â˜ƒ % 2 == 0 ? 16777215 : 9474192
            );
         }

         @Override
         public Component getNarration() {
            return new TranslatableComponent("narrator.select", new TextComponent("").append(this.statDisplay).append(" ").append(this.getValueText()));
         }
      }
   }

   class ItemStatisticsList extends ObjectSelectionList<StatsScreen.ItemStatisticsList.ItemRow> {
      protected final List<StatType<Block>> blockColumns;
      protected final List<StatType<Item>> itemColumns;
      private final int[] iconOffsets = new int[]{3, 4, 1, 2, 5, 6};
      protected int headerPressed = -1;
      protected final Comparator<StatsScreen.ItemStatisticsList.ItemRow> itemStatSorter = new StatsScreen.ItemStatisticsList.ItemRowComparator();
      @Nullable
      protected StatType<?> sortColumn;
      protected int sortOrder;

      public ItemStatisticsList(Minecraft var2) {
         super(â˜ƒ, StatsScreen.this.width, StatsScreen.this.height, 32, StatsScreen.this.height - 64, 20);
         this.blockColumns = Lists.<StatType<Block>>newArrayList();
         this.blockColumns.add(Stats.BLOCK_MINED);
         this.itemColumns = Lists.<StatType<Item>>newArrayList(Stats.ITEM_BROKEN, Stats.ITEM_CRAFTED, Stats.ITEM_USED, Stats.ITEM_PICKED_UP, Stats.ITEM_DROPPED);
         this.setRenderHeader(true, 20);
         Set<Item> â˜ƒ = Sets.newIdentityHashSet();

         for(Item â˜ƒx : Registry.ITEM) {
            boolean â˜ƒxx = false;

            for(StatType<Item> â˜ƒxxx : this.itemColumns) {
               if (â˜ƒxxx.contains(â˜ƒx) && StatsScreen.this.stats.getValue(â˜ƒxxx.get(â˜ƒx)) > 0) {
                  â˜ƒxx = true;
               }
            }

            if (â˜ƒxx) {
               â˜ƒ.add(â˜ƒx);
            }
         }

         for(Block â˜ƒx : Registry.BLOCK) {
            boolean â˜ƒxx = false;

            for(StatType<Block> â˜ƒxxx : this.blockColumns) {
               if (â˜ƒxxx.contains(â˜ƒx) && StatsScreen.this.stats.getValue(â˜ƒxxx.get(â˜ƒx)) > 0) {
                  â˜ƒxx = true;
               }
            }

            if (â˜ƒxx) {
               â˜ƒ.add(â˜ƒx.asItem());
            }
         }

         â˜ƒ.remove(Items.AIR);

         for(Item â˜ƒx : â˜ƒ) {
            this.addEntry(new StatsScreen.ItemStatisticsList.ItemRow(â˜ƒx));
         }
      }

      @Override
      protected void renderHeader(PoseStack var1, int var2, int var3, Tesselator var4) {
         if (!this.minecraft.mouseHandler.isLeftPressed()) {
            this.headerPressed = -1;
         }

         for(int â˜ƒ = 0; â˜ƒ < this.iconOffsets.length; ++â˜ƒ) {
            StatsScreen.this.blitSlotIcon(â˜ƒ, â˜ƒ + StatsScreen.this.getColumnX(â˜ƒ) - 18, â˜ƒ + 1, 0, this.headerPressed == â˜ƒ ? 0 : 18);
         }

         if (this.sortColumn != null) {
            int â˜ƒ = StatsScreen.this.getColumnX(this.getColumnIndex(this.sortColumn)) - 36;
            int â˜ƒx = this.sortOrder == 1 ? 2 : 1;
            StatsScreen.this.blitSlotIcon(â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ + 1, 18 * â˜ƒx, 0);
         }

         for(int â˜ƒ = 0; â˜ƒ < this.iconOffsets.length; ++â˜ƒ) {
            int â˜ƒx = this.headerPressed == â˜ƒ ? 1 : 0;
            StatsScreen.this.blitSlotIcon(â˜ƒ, â˜ƒ + StatsScreen.this.getColumnX(â˜ƒ) - 18 + â˜ƒx, â˜ƒ + 1 + â˜ƒx, 18 * this.iconOffsets[â˜ƒ], 18);
         }
      }

      @Override
      public int getRowWidth() {
         return 375;
      }

      @Override
      protected int getScrollbarPosition() {
         return this.width / 2 + 140;
      }

      @Override
      protected void renderBackground(PoseStack var1) {
         StatsScreen.this.renderBackground(â˜ƒ);
      }

      @Override
      protected void clickedHeader(int var1, int var2) {
         this.headerPressed = -1;

         for(int â˜ƒ = 0; â˜ƒ < this.iconOffsets.length; ++â˜ƒ) {
            int â˜ƒx = â˜ƒ - StatsScreen.this.getColumnX(â˜ƒ);
            if (â˜ƒx >= -36 && â˜ƒx <= 0) {
               this.headerPressed = â˜ƒ;
               break;
            }
         }

         if (this.headerPressed >= 0) {
            this.sortByColumn(this.getColumn(this.headerPressed));
            this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
         }
      }

      private StatType<?> getColumn(int var1) {
         return â˜ƒ < this.blockColumns.size() ? (StatType)this.blockColumns.get(â˜ƒ) : (StatType)this.itemColumns.get(â˜ƒ - this.blockColumns.size());
      }

      private int getColumnIndex(StatType<?> var1) {
         int â˜ƒ = this.blockColumns.indexOf(â˜ƒ);
         if (â˜ƒ >= 0) {
            return â˜ƒ;
         } else {
            int â˜ƒ = this.itemColumns.indexOf(â˜ƒ);
            return â˜ƒ >= 0 ? â˜ƒ + this.blockColumns.size() : -1;
         }
      }

      @Override
      protected void renderDecorations(PoseStack var1, int var2, int var3) {
         if (â˜ƒ >= this.y0 && â˜ƒ <= this.y1) {
            StatsScreen.ItemStatisticsList.ItemRow â˜ƒ = this.getHovered();
            int â˜ƒx = (this.width - this.getRowWidth()) / 2;
            if (â˜ƒ != null) {
               if (â˜ƒ < â˜ƒx + 40 || â˜ƒ > â˜ƒx + 40 + 20) {
                  return;
               }

               Item â˜ƒxx = â˜ƒ.getItem();
               this.renderMousehoverTooltip(â˜ƒ, this.getString(â˜ƒxx), â˜ƒ, â˜ƒ);
            } else {
               Component â˜ƒ = null;
               int â˜ƒx = â˜ƒ - â˜ƒx;

               for(int â˜ƒxx = 0; â˜ƒxx < this.iconOffsets.length; ++â˜ƒxx) {
                  int â˜ƒxxx = StatsScreen.this.getColumnX(â˜ƒxx);
                  if (â˜ƒx >= â˜ƒxxx - 18 && â˜ƒx <= â˜ƒxxx) {
                     â˜ƒ = this.getColumn(â˜ƒxx).getDisplayName();
                     break;
                  }
               }

               this.renderMousehoverTooltip(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
            }
         }
      }

      protected void renderMousehoverTooltip(PoseStack var1, @Nullable Component var2, int var3, int var4) {
         if (â˜ƒ != null) {
            int â˜ƒ = â˜ƒ + 12;
            int â˜ƒx = â˜ƒ - 12;
            int â˜ƒxx = StatsScreen.this.font.width(â˜ƒ);
            this.fillGradient(â˜ƒ, â˜ƒ - 3, â˜ƒx - 3, â˜ƒ + â˜ƒxx + 3, â˜ƒx + 8 + 3, -1073741824, -1073741824);
            â˜ƒ.pushPose();
            â˜ƒ.translate(0.0, 0.0, 400.0);
            StatsScreen.this.font.drawShadow(â˜ƒ, â˜ƒ, (float)â˜ƒ, (float)â˜ƒx, -1);
            â˜ƒ.popPose();
         }
      }

      protected Component getString(Item var1) {
         return â˜ƒ.getDescription();
      }

      protected void sortByColumn(StatType<?> var1) {
         if (â˜ƒ != this.sortColumn) {
            this.sortColumn = â˜ƒ;
            this.sortOrder = -1;
         } else if (this.sortOrder == -1) {
            this.sortOrder = 1;
         } else {
            this.sortColumn = null;
            this.sortOrder = 0;
         }

         this.children().sort(this.itemStatSorter);
      }

      class ItemRow extends ObjectSelectionList.Entry<StatsScreen.ItemStatisticsList.ItemRow> {
         private final Item item;

         ItemRow(Item var2) {
            this.item = â˜ƒ;
         }

         public Item getItem() {
            return this.item;
         }

         @Override
         public void render(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
            StatsScreen.this.blitSlot(â˜ƒ, â˜ƒ + 40, â˜ƒ, this.item);

            for(int â˜ƒ = 0; â˜ƒ < StatsScreen.this.itemStatsList.blockColumns.size(); ++â˜ƒ) {
               Stat<Block> â˜ƒx;
               if (this.item instanceof BlockItem) {
                  â˜ƒx = ((StatType)StatsScreen.this.itemStatsList.blockColumns.get(â˜ƒ)).get(((BlockItem)this.item).getBlock());
               } else {
                  â˜ƒx = null;
               }

               this.renderStat(â˜ƒ, â˜ƒx, â˜ƒ + StatsScreen.this.getColumnX(â˜ƒ), â˜ƒ, â˜ƒ % 2 == 0);
            }

            for(int â˜ƒ = 0; â˜ƒ < StatsScreen.this.itemStatsList.itemColumns.size(); ++â˜ƒ) {
               this.renderStat(
                  â˜ƒ,
                  ((StatType)StatsScreen.this.itemStatsList.itemColumns.get(â˜ƒ)).get(this.item),
                  â˜ƒ + StatsScreen.this.getColumnX(â˜ƒ + StatsScreen.this.itemStatsList.blockColumns.size()),
                  â˜ƒ,
                  â˜ƒ % 2 == 0
               );
            }
         }

         protected void renderStat(PoseStack var1, @Nullable Stat<?> var2, int var3, int var4, boolean var5) {
            String â˜ƒ = â˜ƒ == null ? "-" : â˜ƒ.format(StatsScreen.this.stats.getValue(â˜ƒ));
            GuiComponent.drawString(â˜ƒ, StatsScreen.this.font, â˜ƒ, â˜ƒ - StatsScreen.this.font.width(â˜ƒ), â˜ƒ + 5, â˜ƒ ? 16777215 : 9474192);
         }

         @Override
         public Component getNarration() {
            return new TranslatableComponent("narrator.select", this.item.getDescription());
         }
      }

      class ItemRowComparator implements Comparator<StatsScreen.ItemStatisticsList.ItemRow> {
         public int compare(StatsScreen.ItemStatisticsList.ItemRow var1, StatsScreen.ItemStatisticsList.ItemRow var2) {
            Item â˜ƒxx = â˜ƒ.getItem();
            Item â˜ƒxxx = â˜ƒ.getItem();
            int â˜ƒ;
            int â˜ƒx;
            if (ItemStatisticsList.this.sortColumn == null) {
               â˜ƒ = 0;
               â˜ƒx = 0;
            } else if (ItemStatisticsList.this.blockColumns.contains(ItemStatisticsList.this.sortColumn)) {
               StatType<Block> â˜ƒ = ItemStatisticsList.this.sortColumn;
               â˜ƒ = â˜ƒxx instanceof BlockItem ? StatsScreen.this.stats.getValue(â˜ƒ, ((BlockItem)â˜ƒxx).getBlock()) : -1;
               â˜ƒx = â˜ƒxxx instanceof BlockItem ? StatsScreen.this.stats.getValue(â˜ƒ, ((BlockItem)â˜ƒxxx).getBlock()) : -1;
            } else {
               StatType<Item> â˜ƒ = ItemStatisticsList.this.sortColumn;
               â˜ƒ = StatsScreen.this.stats.getValue(â˜ƒ, â˜ƒxx);
               â˜ƒx = StatsScreen.this.stats.getValue(â˜ƒ, â˜ƒxxx);
            }

            return â˜ƒ == â˜ƒx
               ? ItemStatisticsList.this.sortOrder * Integer.compare(Item.getId(â˜ƒxx), Item.getId(â˜ƒxxx))
               : ItemStatisticsList.this.sortOrder * Integer.compare(â˜ƒ, â˜ƒx);
         }
      }
   }

   class MobsStatisticsList extends ObjectSelectionList<StatsScreen.MobsStatisticsList.MobRow> {
      public MobsStatisticsList(Minecraft var2) {
         super(â˜ƒ, StatsScreen.this.width, StatsScreen.this.height, 32, StatsScreen.this.height - 64, 9 * 4);

         for(EntityType<?> â˜ƒ : Registry.ENTITY_TYPE) {
            if (StatsScreen.this.stats.getValue(Stats.ENTITY_KILLED.get(â˜ƒ)) > 0 || StatsScreen.this.stats.getValue(Stats.ENTITY_KILLED_BY.get(â˜ƒ)) > 0) {
               this.addEntry(new StatsScreen.MobsStatisticsList.MobRow(â˜ƒ));
            }
         }
      }

      @Override
      protected void renderBackground(PoseStack var1) {
         StatsScreen.this.renderBackground(â˜ƒ);
      }

      class MobRow extends ObjectSelectionList.Entry<StatsScreen.MobsStatisticsList.MobRow> {
         private final Component mobName;
         private final Component kills;
         private final boolean hasKills;
         private final Component killedBy;
         private final boolean wasKilledBy;

         public MobRow(EntityType<?> var2) {
            this.mobName = â˜ƒ.getDescription();
            int â˜ƒ = StatsScreen.this.stats.getValue(Stats.ENTITY_KILLED.get(â˜ƒ));
            if (â˜ƒ == 0) {
               this.kills = new TranslatableComponent("stat_type.minecraft.killed.none", this.mobName);
               this.hasKills = false;
            } else {
               this.kills = new TranslatableComponent("stat_type.minecraft.killed", â˜ƒ, this.mobName);
               this.hasKills = true;
            }

            int â˜ƒ = StatsScreen.this.stats.getValue(Stats.ENTITY_KILLED_BY.get(â˜ƒ));
            if (â˜ƒ == 0) {
               this.killedBy = new TranslatableComponent("stat_type.minecraft.killed_by.none", this.mobName);
               this.wasKilledBy = false;
            } else {
               this.killedBy = new TranslatableComponent("stat_type.minecraft.killed_by", this.mobName, â˜ƒ);
               this.wasKilledBy = true;
            }
         }

         @Override
         public void render(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
            GuiComponent.drawString(â˜ƒ, StatsScreen.this.font, this.mobName, â˜ƒ + 2, â˜ƒ + 1, 16777215);
            GuiComponent.drawString(â˜ƒ, StatsScreen.this.font, this.kills, â˜ƒ + 2 + 10, â˜ƒ + 1 + 9, this.hasKills ? 9474192 : 6316128);
            GuiComponent.drawString(â˜ƒ, StatsScreen.this.font, this.killedBy, â˜ƒ + 2 + 10, â˜ƒ + 1 + 9 * 2, this.wasKilledBy ? 9474192 : 6316128);
         }

         @Override
         public Component getNarration() {
            return new TranslatableComponent("narrator.select", CommonComponents.joinForNarration(this.kills, this.killedBy));
         }
      }
   }
}
