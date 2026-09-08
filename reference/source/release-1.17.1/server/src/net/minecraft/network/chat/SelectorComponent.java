package net.minecraft.network.chat;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.world.entity.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SelectorComponent extends BaseComponent implements ContextAwareComponent {
   private static final Logger LOGGER = LogManager.getLogger();
   private final String pattern;
   @Nullable
   private final EntitySelector selector;
   protected final Optional<Component> separator;

   public SelectorComponent(String var1, Optional<Component> var2) {
      this.pattern = â˜ƒ;
      this.separator = â˜ƒ;
      EntitySelector â˜ƒ = null;

      try {
         EntitySelectorParser â˜ƒx = new EntitySelectorParser(new StringReader(â˜ƒ));
         â˜ƒ = â˜ƒx.parse();
      } catch (CommandSyntaxException var5) {
         LOGGER.warn("Invalid selector component: {}: {}", â˜ƒ, var5.getMessage());
      }

      this.selector = â˜ƒ;
   }

   public String getPattern() {
      return this.pattern;
   }

   @Nullable
   public EntitySelector getSelector() {
      return this.selector;
   }

   public Optional<Component> getSeparator() {
      return this.separator;
   }

   @Override
   public MutableComponent resolve(@Nullable CommandSourceStack var1, @Nullable Entity var2, int var3) throws CommandSyntaxException {
      if (â˜ƒ != null && this.selector != null) {
         Optional<? extends Component> â˜ƒ = ComponentUtils.updateForEntity(â˜ƒ, this.separator, â˜ƒ, â˜ƒ);
         return ComponentUtils.formatList(this.selector.findEntities(â˜ƒ), â˜ƒ, Entity::getDisplayName);
      } else {
         return new TextComponent("");
      }
   }

   @Override
   public String getContents() {
      return this.pattern;
   }

   public SelectorComponent plainCopy() {
      return new SelectorComponent(this.pattern, this.separator);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (!(â˜ƒ instanceof SelectorComponent)) {
         return false;
      } else {
         SelectorComponent â˜ƒ = (SelectorComponent)â˜ƒ;
         return this.pattern.equals(â˜ƒ.pattern) && super.equals(â˜ƒ);
      }
   }

   @Override
   public String toString() {
      return "SelectorComponent{pattern='" + this.pattern + "', siblings=" + this.siblings + ", style=" + this.getStyle() + "}";
   }
}
