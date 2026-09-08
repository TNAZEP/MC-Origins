package net.minecraft.client.gui.screens.worldselection;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.level.GameRules;

public class EditGameRulesScreen extends Screen {
   private final Consumer<Optional<GameRules>> exitCallback;
   private EditGameRulesScreen.RuleList rules;
   private final Set<EditGameRulesScreen.RuleEntry> invalidEntries = Sets.<EditGameRulesScreen.RuleEntry>newHashSet();
   private Button doneButton;
   @Nullable
   private List<FormattedCharSequence> tooltip;
   private final GameRules gameRules;

   public EditGameRulesScreen(GameRules var1, Consumer<Optional<GameRules>> var2) {
      super(new TranslatableComponent("editGamerule.title"));
      this.gameRules = â˜ƒ;
      this.exitCallback = â˜ƒ;
   }

   @Override
   protected void init() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
      super.init();
      this.rules = new EditGameRulesScreen.RuleList(this.gameRules);
      this.addWidget(this.rules);
      this.addRenderableWidget(
         new Button(this.width / 2 - 155 + 160, this.height - 29, 150, 20, CommonComponents.GUI_CANCEL, var1 -> this.exitCallback.accept(Optional.empty()))
      );
      this.doneButton = this.addRenderableWidget(
         new Button(this.width / 2 - 155, this.height - 29, 150, 20, CommonComponents.GUI_DONE, var1 -> this.exitCallback.accept(Optional.of(this.gameRules)))
      );
   }

   @Override
   public void removed() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
   }

   @Override
   public void onClose() {
      this.exitCallback.accept(Optional.empty());
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.tooltip = null;
      this.rules.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 20, 16777215);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (this.tooltip != null) {
         this.renderTooltip(â˜ƒ, this.tooltip, â˜ƒ, â˜ƒ);
      }
   }

   void setTooltip(@Nullable List<FormattedCharSequence> var1) {
      this.tooltip = â˜ƒ;
   }

   private void updateDoneButton() {
      this.doneButton.active = this.invalidEntries.isEmpty();
   }

   void markInvalid(EditGameRulesScreen.RuleEntry var1) {
      this.invalidEntries.add(â˜ƒ);
      this.updateDoneButton();
   }

   void clearInvalid(EditGameRulesScreen.RuleEntry var1) {
      this.invalidEntries.remove(â˜ƒ);
      this.updateDoneButton();
   }

   public class BooleanRuleEntry extends EditGameRulesScreen.GameRuleEntry {
      private final CycleButton<Boolean> checkbox;

      public BooleanRuleEntry(Component var2, List<FormattedCharSequence> var3, String var4, GameRules.BooleanValue var5) {
         super(â˜ƒ, â˜ƒ);
         this.checkbox = CycleButton.onOffBuilder(â˜ƒ.get())
            .displayOnlyValue()
            .withCustomNarration(var1x -> var1x.createDefaultNarrationMessage().append("\n").append(â˜ƒ))
            .create(10, 5, 44, 20, â˜ƒ, (var1x, var2x) -> â˜ƒ.set(var2x, null));
         this.children.add(this.checkbox);
      }

      @Override
      public void render(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         this.renderLabel(â˜ƒ, â˜ƒ, â˜ƒ);
         this.checkbox.x = â˜ƒ + â˜ƒ - 45;
         this.checkbox.y = â˜ƒ;
         this.checkbox.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public class CategoryRuleEntry extends EditGameRulesScreen.RuleEntry {
      final Component label;

      public CategoryRuleEntry(Component var2) {
         super(null);
         this.label = â˜ƒ;
      }

      @Override
      public void render(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         GuiComponent.drawCenteredString(â˜ƒ, EditGameRulesScreen.this.minecraft.font, this.label, â˜ƒ + â˜ƒ / 2, â˜ƒ + 5, 16777215);
      }

      @Override
      public List<? extends GuiEventListener> children() {
         return ImmutableList.of();
      }

      @Override
      public List<? extends NarratableEntry> narratables() {
         return ImmutableList.of(new NarratableEntry() {
            @Override
            public NarratableEntry.NarrationPriority narrationPriority() {
               return NarratableEntry.NarrationPriority.HOVERED;
            }

            @Override
            public void updateNarration(NarrationElementOutput var1) {
               â˜ƒ.add(NarratedElementType.TITLE, CategoryRuleEntry.this.label);
            }
         });
      }
   }

   @FunctionalInterface
   interface EntryFactory<T extends GameRules.Value<T>> {
      EditGameRulesScreen.RuleEntry create(Component var1, List<FormattedCharSequence> var2, String var3, T var4);
   }

   public abstract class GameRuleEntry extends EditGameRulesScreen.RuleEntry {
      private final List<FormattedCharSequence> label;
      protected final List<AbstractWidget> children = Lists.<AbstractWidget>newArrayList();

      public GameRuleEntry(@Nullable List<FormattedCharSequence> var2, Component var3) {
         super(â˜ƒ);
         this.label = EditGameRulesScreen.this.minecraft.font.split(â˜ƒ, 175);
      }

      @Override
      public List<? extends GuiEventListener> children() {
         return this.children;
      }

      @Override
      public List<? extends NarratableEntry> narratables() {
         return this.children;
      }

      protected void renderLabel(PoseStack var1, int var2, int var3) {
         if (this.label.size() == 1) {
            EditGameRulesScreen.this.minecraft.font.draw(â˜ƒ, (FormattedCharSequence)this.label.get(0), (float)â˜ƒ, (float)(â˜ƒ + 5), 16777215);
         } else if (this.label.size() >= 2) {
            EditGameRulesScreen.this.minecraft.font.draw(â˜ƒ, (FormattedCharSequence)this.label.get(0), (float)â˜ƒ, (float)â˜ƒ, 16777215);
            EditGameRulesScreen.this.minecraft.font.draw(â˜ƒ, (FormattedCharSequence)this.label.get(1), (float)â˜ƒ, (float)(â˜ƒ + 10), 16777215);
         }
      }
   }

   public class IntegerRuleEntry extends EditGameRulesScreen.GameRuleEntry {
      private final EditBox input;

      public IntegerRuleEntry(Component var2, List<FormattedCharSequence> var3, String var4, GameRules.IntegerValue var5) {
         super(â˜ƒ, â˜ƒ);
         this.input = new EditBox(EditGameRulesScreen.this.minecraft.font, 10, 5, 42, 20, â˜ƒ.copy().append("\n").append(â˜ƒ).append("\n"));
         this.input.setValue(Integer.toString(â˜ƒ.get()));
         this.input.setResponder(var2x -> {
            if (â˜ƒ.tryDeserialize(var2x)) {
               this.input.setTextColor(14737632);
               EditGameRulesScreen.this.clearInvalid(this);
            } else {
               this.input.setTextColor(16711680);
               EditGameRulesScreen.this.markInvalid(this);
            }
         });
         this.children.add(this.input);
      }

      @Override
      public void render(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         this.renderLabel(â˜ƒ, â˜ƒ, â˜ƒ);
         this.input.x = â˜ƒ + â˜ƒ - 44;
         this.input.y = â˜ƒ;
         this.input.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public abstract class RuleEntry extends ContainerObjectSelectionList.Entry<EditGameRulesScreen.RuleEntry> {
      @Nullable
      final List<FormattedCharSequence> tooltip;

      public RuleEntry(@Nullable List<FormattedCharSequence> var2) {
         this.tooltip = â˜ƒ;
      }
   }

   public class RuleList extends ContainerObjectSelectionList<EditGameRulesScreen.RuleEntry> {
      public RuleList(final GameRules var2) {
         super(
            EditGameRulesScreen.this.minecraft, EditGameRulesScreen.this.width, EditGameRulesScreen.this.height, 43, EditGameRulesScreen.this.height - 32, 24
         );
         final Map<GameRules.Category, Map<GameRules.Key<?>, EditGameRulesScreen.RuleEntry>> â˜ƒ = Maps.newHashMap();
         GameRules.visitGameRuleTypes(new GameRules.GameRuleTypeVisitor() {
            @Override
            public void visitBoolean(GameRules.Key<GameRules.BooleanValue> var1, GameRules.Type<GameRules.BooleanValue> var2x) {
               this.addEntry(â˜ƒ, (var1x, var2xx, var3x, var4) -> EditGameRulesScreen.this.new BooleanRuleEntry(var1x, var2xx, var3x, var4));
            }

            @Override
            public void visitInteger(GameRules.Key<GameRules.IntegerValue> var1, GameRules.Type<GameRules.IntegerValue> var2x) {
               this.addEntry(â˜ƒ, (var1x, var2xx, var3x, var4) -> EditGameRulesScreen.this.new IntegerRuleEntry(var1x, var2xx, var3x, var4));
            }

            private <T extends GameRules.Value<T>> void addEntry(GameRules.Key<T> var1, EditGameRulesScreen.EntryFactory<T> var2x) {
               Component â˜ƒxx = new TranslatableComponent(â˜ƒ.getDescriptionId());
               Component â˜ƒxxx = new TextComponent(â˜ƒ.getId()).withStyle(ChatFormatting.YELLOW);
               T â˜ƒxxxx = â˜ƒ.getRule(â˜ƒ);
               String â˜ƒxxxxx = â˜ƒxxxx.serialize();
               Component â˜ƒxxxxxx = new TranslatableComponent("editGamerule.default", new TextComponent(â˜ƒxxxxx)).withStyle(ChatFormatting.GRAY);
               String â˜ƒxxxxxxx = â˜ƒ.getDescriptionId() + ".description";
               List<FormattedCharSequence> â˜ƒ;
               String â˜ƒx;
               if (I18n.exists(â˜ƒxxxxxxx)) {
                  Builder<FormattedCharSequence> â˜ƒxxxxxxxx = ImmutableList.<FormattedCharSequence>builder().add(â˜ƒxxx.getVisualOrderText());
                  Component â˜ƒxxxxxxxxx = new TranslatableComponent(â˜ƒxxxxxxx);
                  EditGameRulesScreen.this.font.split(â˜ƒxxxxxxxxx, 150).forEach(â˜ƒxxxxxxxx::add);
                  â˜ƒ = â˜ƒxxxxxxxx.add(â˜ƒxxxxxx.getVisualOrderText()).build();
                  â˜ƒx = â˜ƒxxxxxxxxx.getString() + "\n" + â˜ƒxxxxxx.getString();
               } else {
                  â˜ƒ = ImmutableList.of(â˜ƒxxx.getVisualOrderText(), â˜ƒxxxxxx.getVisualOrderText());
                  â˜ƒx = â˜ƒxxxxxx.getString();
               }

               ((Map)â˜ƒ.computeIfAbsent(â˜ƒ.getCategory(), var0 -> Maps.newHashMap())).put(â˜ƒ, â˜ƒ.create(â˜ƒxx, â˜ƒ, â˜ƒx, â˜ƒxxxx));
            }
         });
         â˜ƒ.entrySet()
            .stream()
            .sorted(java.util.Map.Entry.comparingByKey())
            .forEach(
               var1x -> {
                  this.addEntry(
                     EditGameRulesScreen.this.new CategoryRuleEntry(
                        new TranslatableComponent(((GameRules.Category)var1x.getKey()).getDescriptionId())
                           .withStyle(new ChatFormatting[]{ChatFormatting.BOLD, ChatFormatting.YELLOW})
                     )
                  );
                  ((Map)var1x.getValue())
                     .entrySet()
                     .stream()
                     .sorted(java.util.Map.Entry.comparingByKey(Comparator.comparing(GameRules.Key::getId)))
                     .forEach(var1xx -> this.addEntry((EditGameRulesScreen.RuleEntry)var1xx.getValue()));
               }
            );
      }

      @Override
      public void render(PoseStack var1, int var2, int var3, float var4) {
         super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         EditGameRulesScreen.RuleEntry â˜ƒ = this.getHovered();
         if (â˜ƒ != null) {
            EditGameRulesScreen.this.setTooltip(â˜ƒ.tooltip);
         }
      }
   }
}
