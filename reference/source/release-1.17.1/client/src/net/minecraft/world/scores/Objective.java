package net.minecraft.world.scores;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class Objective {
   public static final int MAX_NAME_LENGTH = 16;
   private final Scoreboard scoreboard;
   private final String name;
   private final ObjectiveCriteria criteria;
   private Component displayName;
   private Component formattedDisplayName;
   private ObjectiveCriteria.RenderType renderType;

   public Objective(Scoreboard var1, String var2, ObjectiveCriteria var3, Component var4, ObjectiveCriteria.RenderType var5) {
      this.scoreboard = â˜ƒ;
      this.name = â˜ƒ;
      this.criteria = â˜ƒ;
      this.displayName = â˜ƒ;
      this.formattedDisplayName = this.createFormattedDisplayName();
      this.renderType = â˜ƒ;
   }

   public Scoreboard getScoreboard() {
      return this.scoreboard;
   }

   public String getName() {
      return this.name;
   }

   public ObjectiveCriteria getCriteria() {
      return this.criteria;
   }

   public Component getDisplayName() {
      return this.displayName;
   }

   private Component createFormattedDisplayName() {
      return ComponentUtils.wrapInSquareBrackets(
         this.displayName.copy().withStyle(var1 -> var1.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent(this.name))))
      );
   }

   public Component getFormattedDisplayName() {
      return this.formattedDisplayName;
   }

   public void setDisplayName(Component var1) {
      this.displayName = â˜ƒ;
      this.formattedDisplayName = this.createFormattedDisplayName();
      this.scoreboard.onObjectiveChanged(this);
   }

   public ObjectiveCriteria.RenderType getRenderType() {
      return this.renderType;
   }

   public void setRenderType(ObjectiveCriteria.RenderType var1) {
      this.renderType = â˜ƒ;
      this.scoreboard.onObjectiveChanged(this);
   }
}
