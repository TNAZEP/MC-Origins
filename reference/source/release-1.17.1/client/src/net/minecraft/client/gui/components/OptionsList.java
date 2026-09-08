package net.minecraft.client.gui.components;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;

public class OptionsList extends ContainerObjectSelectionList<OptionsList.Entry> {
   public OptionsList(Minecraft var1, int var2, int var3, int var4, int var5, int var6) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.centerListVertically = false;
   }

   public int addBig(Option var1) {
      return this.addEntry(OptionsList.Entry.big(this.minecraft.options, this.width, â˜ƒ));
   }

   public void addSmall(Option var1, @Nullable Option var2) {
      this.addEntry(OptionsList.Entry.small(this.minecraft.options, this.width, â˜ƒ, â˜ƒ));
   }

   public void addSmall(Option[] var1) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.length; â˜ƒ += 2) {
         this.addSmall(â˜ƒ[â˜ƒ], â˜ƒ < â˜ƒ.length - 1 ? â˜ƒ[â˜ƒ + 1] : null);
      }
   }

   @Override
   public int getRowWidth() {
      return 400;
   }

   @Override
   protected int getScrollbarPosition() {
      return super.getScrollbarPosition() + 32;
   }

   @Nullable
   public AbstractWidget findOption(Option var1) {
      for(OptionsList.Entry â˜ƒ : this.children()) {
         AbstractWidget â˜ƒx = (AbstractWidget)â˜ƒ.options.get(â˜ƒ);
         if (â˜ƒx != null) {
            return â˜ƒx;
         }
      }

      return null;
   }

   public Optional<AbstractWidget> getMouseOver(double var1, double var3) {
      for(OptionsList.Entry â˜ƒ : this.children()) {
         for(AbstractWidget â˜ƒx : â˜ƒ.children) {
            if (â˜ƒx.isMouseOver(â˜ƒ, â˜ƒ)) {
               return Optional.of(â˜ƒx);
            }
         }
      }

      return Optional.empty();
   }

   protected static class Entry extends ContainerObjectSelectionList.Entry<OptionsList.Entry> {
      final Map<Option, AbstractWidget> options;
      final List<AbstractWidget> children;

      private Entry(Map<Option, AbstractWidget> var1) {
         this.options = â˜ƒ;
         this.children = ImmutableList.copyOf(â˜ƒ.values());
      }

      public static OptionsList.Entry big(Options var0, int var1, Option var2) {
         return new OptionsList.Entry(ImmutableMap.of(â˜ƒ, â˜ƒ.createButton(â˜ƒ, â˜ƒ / 2 - 155, 0, 310)));
      }

      public static OptionsList.Entry small(Options var0, int var1, Option var2, @Nullable Option var3) {
         AbstractWidget â˜ƒ = â˜ƒ.createButton(â˜ƒ, â˜ƒ / 2 - 155, 0, 150);
         return â˜ƒ == null
            ? new OptionsList.Entry(ImmutableMap.of(â˜ƒ, â˜ƒ))
            : new OptionsList.Entry(ImmutableMap.of(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.createButton(â˜ƒ, â˜ƒ / 2 - 155 + 160, 0, 150)));
      }

      @Override
      public void render(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         this.children.forEach(var5x -> {
            var5x.y = â˜ƒ;
            var5x.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         });
      }

      @Override
      public List<? extends GuiEventListener> children() {
         return this.children;
      }

      @Override
      public List<? extends NarratableEntry> narratables() {
         return this.children;
      }
   }
}
