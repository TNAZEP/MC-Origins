package net.minecraft.client.gui.screens.controls;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.MouseSettingsScreen;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;

public class ControlsScreen extends OptionsSubScreen {
   public KeyMapping selectedKey;
   public long lastKeySelection;
   private ControlList controlList;
   private Button resetButton;

   public ControlsScreen(Screen var1, Options var2) {
      super(â˜ƒ, â˜ƒ, new TranslatableComponent("controls.title"));
   }

   @Override
   protected void init() {
      this.addRenderableWidget(
         new Button(
            this.width / 2 - 155,
            18,
            150,
            20,
            new TranslatableComponent("options.mouse_settings"),
            var1 -> this.minecraft.setScreen(new MouseSettingsScreen(this, this.options))
         )
      );
      this.addRenderableWidget(Option.AUTO_JUMP.createButton(this.options, this.width / 2 - 155 + 160, 18, 150));
      this.controlList = new ControlList(this, this.minecraft);
      this.addWidget(this.controlList);
      this.resetButton = this.addRenderableWidget(
         new Button(this.width / 2 - 155, this.height - 29, 150, 20, new TranslatableComponent("controls.resetAll"), var1 -> {
            for(KeyMapping â˜ƒ : this.options.keyMappings) {
               â˜ƒ.setKey(â˜ƒ.getDefaultKey());
            }
   
            KeyMapping.resetMapping();
         })
      );
      this.addRenderableWidget(
         new Button(this.width / 2 - 155 + 160, this.height - 29, 150, 20, CommonComponents.GUI_DONE, var1 -> this.minecraft.setScreen(this.lastScreen))
      );
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (this.selectedKey != null) {
         this.options.setKey(this.selectedKey, InputConstants.Type.MOUSE.getOrCreate(â˜ƒ));
         this.selectedKey = null;
         KeyMapping.resetMapping();
         return true;
      } else {
         return super.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (this.selectedKey != null) {
         if (â˜ƒ == 256) {
            this.options.setKey(this.selectedKey, InputConstants.UNKNOWN);
         } else {
            this.options.setKey(this.selectedKey, InputConstants.getKey(â˜ƒ, â˜ƒ));
         }

         this.selectedKey = null;
         this.lastKeySelection = Util.getMillis();
         KeyMapping.resetMapping();
         return true;
      } else {
         return super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      this.controlList.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 8, 16777215);
      boolean â˜ƒ = false;

      for(KeyMapping â˜ƒx : this.options.keyMappings) {
         if (!â˜ƒx.isDefault()) {
            â˜ƒ = true;
            break;
         }
      }

      this.resetButton.active = â˜ƒ;
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
