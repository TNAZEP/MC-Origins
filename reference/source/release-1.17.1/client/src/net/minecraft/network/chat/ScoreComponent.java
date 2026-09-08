package net.minecraft.network.chat;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;

public class ScoreComponent extends BaseComponent implements ContextAwareComponent {
   private static final String SCORER_PLACEHOLDER = "*";
   private final String name;
   @Nullable
   private final EntitySelector selector;
   private final String objective;

   @Nullable
   private static EntitySelector parseSelector(String var0) {
      try {
         return new EntitySelectorParser(new StringReader(â˜ƒ)).parse();
      } catch (CommandSyntaxException var2) {
         return null;
      }
   }

   public ScoreComponent(String var1, String var2) {
      this(â˜ƒ, parseSelector(â˜ƒ), â˜ƒ);
   }

   private ScoreComponent(String var1, @Nullable EntitySelector var2, String var3) {
      this.name = â˜ƒ;
      this.selector = â˜ƒ;
      this.objective = â˜ƒ;
   }

   public String getName() {
      return this.name;
   }

   @Nullable
   public EntitySelector getSelector() {
      return this.selector;
   }

   public String getObjective() {
      return this.objective;
   }

   private String findTargetName(CommandSourceStack var1) throws CommandSyntaxException {
      if (this.selector != null) {
         List<? extends Entity> â˜ƒ = this.selector.findEntities(â˜ƒ);
         if (!â˜ƒ.isEmpty()) {
            if (â˜ƒ.size() != 1) {
               throw EntityArgument.ERROR_NOT_SINGLE_ENTITY.create();
            }

            return ((Entity)â˜ƒ.get(0)).getScoreboardName();
         }
      }

      return this.name;
   }

   private String getScore(String var1, CommandSourceStack var2) {
      MinecraftServer â˜ƒ = â˜ƒ.getServer();
      if (â˜ƒ != null) {
         Scoreboard â˜ƒx = â˜ƒ.getScoreboard();
         Objective â˜ƒxx = â˜ƒx.getObjective(this.objective);
         if (â˜ƒx.hasPlayerScore(â˜ƒ, â˜ƒxx)) {
            Score â˜ƒxxx = â˜ƒx.getOrCreatePlayerScore(â˜ƒ, â˜ƒxx);
            return Integer.toString(â˜ƒxxx.getScore());
         }
      }

      return "";
   }

   public ScoreComponent plainCopy() {
      return new ScoreComponent(this.name, this.selector, this.objective);
   }

   @Override
   public MutableComponent resolve(@Nullable CommandSourceStack var1, @Nullable Entity var2, int var3) throws CommandSyntaxException {
      if (â˜ƒ == null) {
         return new TextComponent("");
      } else {
         String â˜ƒ = this.findTargetName(â˜ƒ);
         String â˜ƒx = â˜ƒ != null && â˜ƒ.equals("*") ? â˜ƒ.getScoreboardName() : â˜ƒ;
         return new TextComponent(this.getScore(â˜ƒx, â˜ƒ));
      }
   }

   @Override
   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (!(â˜ƒ instanceof ScoreComponent)) {
         return false;
      } else {
         ScoreComponent â˜ƒ = (ScoreComponent)â˜ƒ;
         return this.name.equals(â˜ƒ.name) && this.objective.equals(â˜ƒ.objective) && super.equals(â˜ƒ);
      }
   }

   @Override
   public String toString() {
      return "ScoreComponent{name='" + this.name + "'objective='" + this.objective + "', siblings=" + this.siblings + ", style=" + this.getStyle() + "}";
   }
}
