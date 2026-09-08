package net.minecraft.client.gui.components;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.mojang.brigadier.context.ParsedArgument;
import com.mojang.brigadier.context.SuggestionContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;

public class CommandSuggestions {
   private static final Pattern WHITESPACE_PATTERN = Pattern.compile("(\\s+)");
   private static final Style UNPARSED_STYLE = Style.EMPTY.withColor(ChatFormatting.RED);
   private static final Style LITERAL_STYLE = Style.EMPTY.withColor(ChatFormatting.GRAY);
   private static final List<Style> ARGUMENT_STYLES = (List<Style>)Stream.of(
         ChatFormatting.AQUA, ChatFormatting.YELLOW, ChatFormatting.GREEN, ChatFormatting.LIGHT_PURPLE, ChatFormatting.GOLD
      )
      .map(Style.EMPTY::withColor)
      .collect(ImmutableList.toImmutableList());
   final Minecraft minecraft;
   final Screen screen;
   final EditBox input;
   final Font font;
   private final boolean commandsOnly;
   private final boolean onlyShowIfCursorPastError;
   final int lineStartOffset;
   final int suggestionLineLimit;
   final boolean anchorToBottom;
   final int fillColor;
   private final List<FormattedCharSequence> commandUsage = Lists.<FormattedCharSequence>newArrayList();
   private int commandUsagePosition;
   private int commandUsageWidth;
   @Nullable
   private ParseResults<SharedSuggestionProvider> currentParse;
   @Nullable
   private CompletableFuture<Suggestions> pendingSuggestions;
   @Nullable
   CommandSuggestions.SuggestionsList suggestions;
   private boolean allowSuggestions;
   boolean keepSuggestions;

   public CommandSuggestions(Minecraft var1, Screen var2, EditBox var3, Font var4, boolean var5, boolean var6, int var7, int var8, boolean var9, int var10) {
      this.minecraft = â˜ƒ;
      this.screen = â˜ƒ;
      this.input = â˜ƒ;
      this.font = â˜ƒ;
      this.commandsOnly = â˜ƒ;
      this.onlyShowIfCursorPastError = â˜ƒ;
      this.lineStartOffset = â˜ƒ;
      this.suggestionLineLimit = â˜ƒ;
      this.anchorToBottom = â˜ƒ;
      this.fillColor = â˜ƒ;
      â˜ƒ.setFormatter(this::formatChat);
   }

   public void setAllowSuggestions(boolean var1) {
      this.allowSuggestions = â˜ƒ;
      if (!â˜ƒ) {
         this.suggestions = null;
      }
   }

   public boolean keyPressed(int var1, int var2, int var3) {
      if (this.suggestions != null && this.suggestions.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ)) {
         return true;
      } else if (this.screen.getFocused() == this.input && â˜ƒ == 258) {
         this.showSuggestions(true);
         return true;
      } else {
         return false;
      }
   }

   public boolean mouseScrolled(double var1) {
      return this.suggestions != null && this.suggestions.mouseScrolled(Mth.clamp(â˜ƒ, -1.0, 1.0));
   }

   public boolean mouseClicked(double var1, double var3, int var5) {
      return this.suggestions != null && this.suggestions.mouseClicked((int)â˜ƒ, (int)â˜ƒ, â˜ƒ);
   }

   public void showSuggestions(boolean var1) {
      if (this.pendingSuggestions != null && this.pendingSuggestions.isDone()) {
         Suggestions â˜ƒ = (Suggestions)this.pendingSuggestions.join();
         if (!â˜ƒ.isEmpty()) {
            int â˜ƒx = 0;

            for(Suggestion â˜ƒxx : â˜ƒ.getList()) {
               â˜ƒx = Math.max(â˜ƒx, this.font.width(â˜ƒxx.getText()));
            }

            int â˜ƒxx = Mth.clamp(this.input.getScreenX(â˜ƒ.getRange().getStart()), 0, this.input.getScreenX(0) + this.input.getInnerWidth() - â˜ƒx);
            int â˜ƒxxx = this.anchorToBottom ? this.screen.height - 12 : 72;
            this.suggestions = new CommandSuggestions.SuggestionsList(â˜ƒxx, â˜ƒxxx, â˜ƒx, this.sortSuggestions(â˜ƒ), â˜ƒ);
         }
      }
   }

   private List<Suggestion> sortSuggestions(Suggestions var1) {
      String â˜ƒ = this.input.getValue().substring(0, this.input.getCursorPosition());
      int â˜ƒx = getLastWordIndex(â˜ƒ);
      String â˜ƒxx = â˜ƒ.substring(â˜ƒx).toLowerCase(Locale.ROOT);
      List<Suggestion> â˜ƒxxx = Lists.<Suggestion>newArrayList();
      List<Suggestion> â˜ƒxxxx = Lists.<Suggestion>newArrayList();

      for(Suggestion â˜ƒxxxxx : â˜ƒ.getList()) {
         if (!â˜ƒxxxxx.getText().startsWith(â˜ƒxx) && !â˜ƒxxxxx.getText().startsWith("minecraft:" + â˜ƒxx)) {
            â˜ƒxxxx.add(â˜ƒxxxxx);
         } else {
            â˜ƒxxx.add(â˜ƒxxxxx);
         }
      }

      â˜ƒxxx.addAll(â˜ƒxxxx);
      return â˜ƒxxx;
   }

   public void updateCommandInfo() {
      String â˜ƒ = this.input.getValue();
      if (this.currentParse != null && !this.currentParse.getReader().getString().equals(â˜ƒ)) {
         this.currentParse = null;
      }

      if (!this.keepSuggestions) {
         this.input.setSuggestion(null);
         this.suggestions = null;
      }

      this.commandUsage.clear();
      StringReader â˜ƒ = new StringReader(â˜ƒ);
      boolean â˜ƒx = â˜ƒ.canRead() && â˜ƒ.peek() == '/';
      if (â˜ƒx) {
         â˜ƒ.skip();
      }

      boolean â˜ƒ = this.commandsOnly || â˜ƒx;
      int â˜ƒx = this.input.getCursorPosition();
      if (â˜ƒ) {
         CommandDispatcher<SharedSuggestionProvider> â˜ƒxx = this.minecraft.player.connection.getCommands();
         if (this.currentParse == null) {
            this.currentParse = â˜ƒxx.parse(â˜ƒ, this.minecraft.player.connection.getSuggestionsProvider());
         }

         int â˜ƒxx = this.onlyShowIfCursorPastError ? â˜ƒ.getCursor() : 1;
         if (â˜ƒx >= â˜ƒxx && (this.suggestions == null || !this.keepSuggestions)) {
            this.pendingSuggestions = â˜ƒxx.getCompletionSuggestions(this.currentParse, â˜ƒx);
            this.pendingSuggestions.thenRun(() -> {
               if (this.pendingSuggestions.isDone()) {
                  this.updateUsageInfo();
               }
            });
         }
      } else {
         String â˜ƒ = â˜ƒ.substring(0, â˜ƒx);
         int â˜ƒx = getLastWordIndex(â˜ƒ);
         Collection<String> â˜ƒxx = this.minecraft.player.connection.getSuggestionsProvider().getOnlinePlayerNames();
         this.pendingSuggestions = SharedSuggestionProvider.suggest(â˜ƒxx, new SuggestionsBuilder(â˜ƒ, â˜ƒx));
      }
   }

   private static int getLastWordIndex(String var0) {
      if (Strings.isNullOrEmpty(â˜ƒ)) {
         return 0;
      } else {
         int â˜ƒ = 0;
         Matcher â˜ƒx = WHITESPACE_PATTERN.matcher(â˜ƒ);

         while(â˜ƒx.find()) {
            â˜ƒ = â˜ƒx.end();
         }

         return â˜ƒ;
      }
   }

   private static FormattedCharSequence getExceptionMessage(CommandSyntaxException var0) {
      Component â˜ƒ = ComponentUtils.fromMessage(â˜ƒ.getRawMessage());
      String â˜ƒx = â˜ƒ.getContext();
      return â˜ƒx == null
         ? â˜ƒ.getVisualOrderText()
         : new TranslatableComponent("command.context.parse_error", â˜ƒ, â˜ƒ.getCursor(), â˜ƒx).getVisualOrderText();
   }

   private void updateUsageInfo() {
      if (this.input.getCursorPosition() == this.input.getValue().length()) {
         if (((Suggestions)this.pendingSuggestions.join()).isEmpty() && !this.currentParse.getExceptions().isEmpty()) {
            int â˜ƒ = 0;

            for(Entry<CommandNode<SharedSuggestionProvider>, CommandSyntaxException> â˜ƒx : this.currentParse.getExceptions().entrySet()) {
               CommandSyntaxException â˜ƒxx = (CommandSyntaxException)â˜ƒx.getValue();
               if (â˜ƒxx.getType() == CommandSyntaxException.BUILT_IN_EXCEPTIONS.literalIncorrect()) {
                  ++â˜ƒ;
               } else {
                  this.commandUsage.add(getExceptionMessage(â˜ƒxx));
               }
            }

            if (â˜ƒ > 0) {
               this.commandUsage.add(getExceptionMessage(CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand().create()));
            }
         } else if (this.currentParse.getReader().canRead()) {
            this.commandUsage.add(getExceptionMessage(Commands.getParseException(this.currentParse)));
         }
      }

      this.commandUsagePosition = 0;
      this.commandUsageWidth = this.screen.width;
      if (this.commandUsage.isEmpty()) {
         this.fillNodeUsage(ChatFormatting.GRAY);
      }

      this.suggestions = null;
      if (this.allowSuggestions && this.minecraft.options.autoSuggestions) {
         this.showSuggestions(false);
      }
   }

   private void fillNodeUsage(ChatFormatting var1) {
      CommandContextBuilder<SharedSuggestionProvider> â˜ƒ = this.currentParse.getContext();
      SuggestionContext<SharedSuggestionProvider> â˜ƒx = â˜ƒ.findSuggestionContext(this.input.getCursorPosition());
      Map<CommandNode<SharedSuggestionProvider>, String> â˜ƒxx = this.minecraft
         .player
         .connection
         .getCommands()
         .getSmartUsage(â˜ƒx.parent, this.minecraft.player.connection.getSuggestionsProvider());
      List<FormattedCharSequence> â˜ƒxxx = Lists.<FormattedCharSequence>newArrayList();
      int â˜ƒxxxx = 0;
      Style â˜ƒxxxxx = Style.EMPTY.withColor(â˜ƒ);

      for(Entry<CommandNode<SharedSuggestionProvider>, String> â˜ƒxxxxxx : â˜ƒxx.entrySet()) {
         if (!(â˜ƒxxxxxx.getKey() instanceof LiteralCommandNode)) {
            â˜ƒxxx.add(FormattedCharSequence.forward((String)â˜ƒxxxxxx.getValue(), â˜ƒxxxxx));
            â˜ƒxxxx = Math.max(â˜ƒxxxx, this.font.width((String)â˜ƒxxxxxx.getValue()));
         }
      }

      if (!â˜ƒxxx.isEmpty()) {
         this.commandUsage.addAll(â˜ƒxxx);
         this.commandUsagePosition = Mth.clamp(this.input.getScreenX(â˜ƒx.startPos), 0, this.input.getScreenX(0) + this.input.getInnerWidth() - â˜ƒxxxx);
         this.commandUsageWidth = â˜ƒxxxx;
      }
   }

   private FormattedCharSequence formatChat(String var1, int var2) {
      return this.currentParse != null ? formatText(this.currentParse, â˜ƒ, â˜ƒ) : FormattedCharSequence.forward(â˜ƒ, Style.EMPTY);
   }

   @Nullable
   static String calculateSuggestionSuffix(String var0, String var1) {
      return â˜ƒ.startsWith(â˜ƒ) ? â˜ƒ.substring(â˜ƒ.length()) : null;
   }

   private static FormattedCharSequence formatText(ParseResults<SharedSuggestionProvider> var0, String var1, int var2) {
      List<FormattedCharSequence> â˜ƒ = Lists.<FormattedCharSequence>newArrayList();
      int â˜ƒx = 0;
      int â˜ƒxx = -1;
      CommandContextBuilder<SharedSuggestionProvider> â˜ƒxxx = â˜ƒ.getContext().getLastChild();

      for(ParsedArgument<SharedSuggestionProvider, ?> â˜ƒxxxx : â˜ƒxxx.getArguments().values()) {
         if (++â˜ƒxx >= ARGUMENT_STYLES.size()) {
            â˜ƒxx = 0;
         }

         int â˜ƒxxxxx = Math.max(â˜ƒxxxx.getRange().getStart() - â˜ƒ, 0);
         if (â˜ƒxxxxx >= â˜ƒ.length()) {
            break;
         }

         int â˜ƒxxxxx = Math.min(â˜ƒxxxx.getRange().getEnd() - â˜ƒ, â˜ƒ.length());
         if (â˜ƒxxxxx > 0) {
            â˜ƒ.add(FormattedCharSequence.forward(â˜ƒ.substring(â˜ƒx, â˜ƒxxxxx), LITERAL_STYLE));
            â˜ƒ.add(FormattedCharSequence.forward(â˜ƒ.substring(â˜ƒxxxxx, â˜ƒxxxxx), (Style)ARGUMENT_STYLES.get(â˜ƒxx)));
            â˜ƒx = â˜ƒxxxxx;
         }
      }

      if (â˜ƒ.getReader().canRead()) {
         int â˜ƒxxxx = Math.max(â˜ƒ.getReader().getCursor() - â˜ƒ, 0);
         if (â˜ƒxxxx < â˜ƒ.length()) {
            int â˜ƒxxxxx = Math.min(â˜ƒxxxx + â˜ƒ.getReader().getRemainingLength(), â˜ƒ.length());
            â˜ƒ.add(FormattedCharSequence.forward(â˜ƒ.substring(â˜ƒx, â˜ƒxxxx), LITERAL_STYLE));
            â˜ƒ.add(FormattedCharSequence.forward(â˜ƒ.substring(â˜ƒxxxx, â˜ƒxxxxx), UNPARSED_STYLE));
            â˜ƒx = â˜ƒxxxxx;
         }
      }

      â˜ƒ.add(FormattedCharSequence.forward(â˜ƒ.substring(â˜ƒx), LITERAL_STYLE));
      return FormattedCharSequence.composite(â˜ƒ);
   }

   public void render(PoseStack var1, int var2, int var3) {
      if (this.suggestions != null) {
         this.suggestions.render(â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         int â˜ƒ = 0;

         for(FormattedCharSequence â˜ƒx : this.commandUsage) {
            int â˜ƒxx = this.anchorToBottom ? this.screen.height - 14 - 13 - 12 * â˜ƒ : 72 + 12 * â˜ƒ;
            GuiComponent.fill(â˜ƒ, this.commandUsagePosition - 1, â˜ƒxx, this.commandUsagePosition + this.commandUsageWidth + 1, â˜ƒxx + 12, this.fillColor);
            this.font.drawShadow(â˜ƒ, â˜ƒx, (float)this.commandUsagePosition, (float)(â˜ƒxx + 2), -1);
            ++â˜ƒ;
         }
      }
   }

   public String getNarrationMessage() {
      return this.suggestions != null ? "\n" + this.suggestions.getNarrationMessage() : "";
   }

   public class SuggestionsList {
      private final Rect2i rect;
      private final String originalContents;
      private final List<Suggestion> suggestionList;
      private int offset;
      private int current;
      private Vec2 lastMouse = Vec2.ZERO;
      private boolean tabCycles;
      private int lastNarratedEntry;

      SuggestionsList(int var2, int var3, int var4, List<Suggestion> var5, boolean var6) {
         int â˜ƒ = â˜ƒ - 1;
         int â˜ƒx = CommandSuggestions.this.anchorToBottom ? â˜ƒ - 3 - Math.min(â˜ƒ.size(), CommandSuggestions.this.suggestionLineLimit) * 12 : â˜ƒ;
         this.rect = new Rect2i(â˜ƒ, â˜ƒx, â˜ƒ + 1, Math.min(â˜ƒ.size(), CommandSuggestions.this.suggestionLineLimit) * 12);
         this.originalContents = CommandSuggestions.this.input.getValue();
         this.lastNarratedEntry = â˜ƒ ? -1 : 0;
         this.suggestionList = â˜ƒ;
         this.select(0);
      }

      public void render(PoseStack var1, int var2, int var3) {
         int â˜ƒ = Math.min(this.suggestionList.size(), CommandSuggestions.this.suggestionLineLimit);
         int â˜ƒx = -5592406;
         boolean â˜ƒxx = this.offset > 0;
         boolean â˜ƒxxx = this.suggestionList.size() > this.offset + â˜ƒ;
         boolean â˜ƒxxxx = â˜ƒxx || â˜ƒxxx;
         boolean â˜ƒxxxxx = this.lastMouse.x != (float)â˜ƒ || this.lastMouse.y != (float)â˜ƒ;
         if (â˜ƒxxxxx) {
            this.lastMouse = new Vec2((float)â˜ƒ, (float)â˜ƒ);
         }

         if (â˜ƒxxxx) {
            GuiComponent.fill(
               â˜ƒ, this.rect.getX(), this.rect.getY() - 1, this.rect.getX() + this.rect.getWidth(), this.rect.getY(), CommandSuggestions.this.fillColor
            );
            GuiComponent.fill(
               â˜ƒ,
               this.rect.getX(),
               this.rect.getY() + this.rect.getHeight(),
               this.rect.getX() + this.rect.getWidth(),
               this.rect.getY() + this.rect.getHeight() + 1,
               CommandSuggestions.this.fillColor
            );
            if (â˜ƒxx) {
               for(int â˜ƒ = 0; â˜ƒ < this.rect.getWidth(); ++â˜ƒ) {
                  if (â˜ƒ % 2 == 0) {
                     GuiComponent.fill(â˜ƒ, this.rect.getX() + â˜ƒ, this.rect.getY() - 1, this.rect.getX() + â˜ƒ + 1, this.rect.getY(), -1);
                  }
               }
            }

            if (â˜ƒxxx) {
               for(int â˜ƒ = 0; â˜ƒ < this.rect.getWidth(); ++â˜ƒ) {
                  if (â˜ƒ % 2 == 0) {
                     GuiComponent.fill(
                        â˜ƒ,
                        this.rect.getX() + â˜ƒ,
                        this.rect.getY() + this.rect.getHeight(),
                        this.rect.getX() + â˜ƒ + 1,
                        this.rect.getY() + this.rect.getHeight() + 1,
                        -1
                     );
                  }
               }
            }
         }

         boolean â˜ƒ = false;

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ; ++â˜ƒx) {
            Suggestion â˜ƒxx = (Suggestion)this.suggestionList.get(â˜ƒx + this.offset);
            GuiComponent.fill(
               â˜ƒ,
               this.rect.getX(),
               this.rect.getY() + 12 * â˜ƒx,
               this.rect.getX() + this.rect.getWidth(),
               this.rect.getY() + 12 * â˜ƒx + 12,
               CommandSuggestions.this.fillColor
            );
            if (â˜ƒ > this.rect.getX()
               && â˜ƒ < this.rect.getX() + this.rect.getWidth()
               && â˜ƒ > this.rect.getY() + 12 * â˜ƒx
               && â˜ƒ < this.rect.getY() + 12 * â˜ƒx + 12) {
               if (â˜ƒxxxxx) {
                  this.select(â˜ƒx + this.offset);
               }

               â˜ƒ = true;
            }

            CommandSuggestions.this.font
               .drawShadow(
                  â˜ƒ,
                  â˜ƒxx.getText(),
                  (float)(this.rect.getX() + 1),
                  (float)(this.rect.getY() + 2 + 12 * â˜ƒx),
                  â˜ƒx + this.offset == this.current ? -256 : -5592406
               );
         }

         if (â˜ƒ) {
            Message â˜ƒx = ((Suggestion)this.suggestionList.get(this.current)).getTooltip();
            if (â˜ƒx != null) {
               CommandSuggestions.this.screen.renderTooltip(â˜ƒ, ComponentUtils.fromMessage(â˜ƒx), â˜ƒ, â˜ƒ);
            }
         }
      }

      public boolean mouseClicked(int var1, int var2, int var3) {
         if (!this.rect.contains(â˜ƒ, â˜ƒ)) {
            return false;
         } else {
            int â˜ƒ = (â˜ƒ - this.rect.getY()) / 12 + this.offset;
            if (â˜ƒ >= 0 && â˜ƒ < this.suggestionList.size()) {
               this.select(â˜ƒ);
               this.useSuggestion();
            }

            return true;
         }
      }

      public boolean mouseScrolled(double var1) {
         int â˜ƒ = (int)(
            CommandSuggestions.this.minecraft.mouseHandler.xpos()
               * (double)CommandSuggestions.this.minecraft.getWindow().getGuiScaledWidth()
               / (double)CommandSuggestions.this.minecraft.getWindow().getScreenWidth()
         );
         int â˜ƒx = (int)(
            CommandSuggestions.this.minecraft.mouseHandler.ypos()
               * (double)CommandSuggestions.this.minecraft.getWindow().getGuiScaledHeight()
               / (double)CommandSuggestions.this.minecraft.getWindow().getScreenHeight()
         );
         if (this.rect.contains(â˜ƒ, â˜ƒx)) {
            this.offset = Mth.clamp((int)((double)this.offset - â˜ƒ), 0, Math.max(this.suggestionList.size() - CommandSuggestions.this.suggestionLineLimit, 0));
            return true;
         } else {
            return false;
         }
      }

      public boolean keyPressed(int var1, int var2, int var3) {
         if (â˜ƒ == 265) {
            this.cycle(-1);
            this.tabCycles = false;
            return true;
         } else if (â˜ƒ == 264) {
            this.cycle(1);
            this.tabCycles = false;
            return true;
         } else if (â˜ƒ == 258) {
            if (this.tabCycles) {
               this.cycle(Screen.hasShiftDown() ? -1 : 1);
            }

            this.useSuggestion();
            return true;
         } else if (â˜ƒ == 256) {
            this.hide();
            return true;
         } else {
            return false;
         }
      }

      public void cycle(int var1) {
         this.select(this.current + â˜ƒ);
         int â˜ƒ = this.offset;
         int â˜ƒx = this.offset + CommandSuggestions.this.suggestionLineLimit - 1;
         if (this.current < â˜ƒ) {
            this.offset = Mth.clamp(this.current, 0, Math.max(this.suggestionList.size() - CommandSuggestions.this.suggestionLineLimit, 0));
         } else if (this.current > â˜ƒx) {
            this.offset = Mth.clamp(
               this.current + CommandSuggestions.this.lineStartOffset - CommandSuggestions.this.suggestionLineLimit,
               0,
               Math.max(this.suggestionList.size() - CommandSuggestions.this.suggestionLineLimit, 0)
            );
         }
      }

      public void select(int var1) {
         this.current = â˜ƒ;
         if (this.current < 0) {
            this.current += this.suggestionList.size();
         }

         if (this.current >= this.suggestionList.size()) {
            this.current -= this.suggestionList.size();
         }

         Suggestion â˜ƒ = (Suggestion)this.suggestionList.get(this.current);
         CommandSuggestions.this.input
            .setSuggestion(CommandSuggestions.calculateSuggestionSuffix(CommandSuggestions.this.input.getValue(), â˜ƒ.apply(this.originalContents)));
         if (this.lastNarratedEntry != this.current) {
            NarratorChatListener.INSTANCE.sayNow(this.getNarrationMessage());
         }
      }

      public void useSuggestion() {
         Suggestion â˜ƒ = (Suggestion)this.suggestionList.get(this.current);
         CommandSuggestions.this.keepSuggestions = true;
         CommandSuggestions.this.input.setValue(â˜ƒ.apply(this.originalContents));
         int â˜ƒx = â˜ƒ.getRange().getStart() + â˜ƒ.getText().length();
         CommandSuggestions.this.input.setCursorPosition(â˜ƒx);
         CommandSuggestions.this.input.setHighlightPos(â˜ƒx);
         this.select(this.current);
         CommandSuggestions.this.keepSuggestions = false;
         this.tabCycles = true;
      }

      Component getNarrationMessage() {
         this.lastNarratedEntry = this.current;
         Suggestion â˜ƒ = (Suggestion)this.suggestionList.get(this.current);
         Message â˜ƒx = â˜ƒ.getTooltip();
         return â˜ƒx != null
            ? new TranslatableComponent("narration.suggestion.tooltip", this.current + 1, this.suggestionList.size(), â˜ƒ.getText(), â˜ƒx)
            : new TranslatableComponent("narration.suggestion", this.current + 1, this.suggestionList.size(), â˜ƒ.getText());
      }

      public void hide() {
         CommandSuggestions.this.suggestions = null;
      }
   }
}
