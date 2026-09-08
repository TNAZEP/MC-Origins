package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class DemoIntroScreen extends Screen {
   private static final ResourceLocation DEMO_BACKGROUND_LOCATION = new ResourceLocation("textures/gui/demo_background.png");
   private MultiLineLabel movementMessage = MultiLineLabel.EMPTY;
   private MultiLineLabel durationMessage = MultiLineLabel.EMPTY;

   public DemoIntroScreen() {
      super(new TranslatableComponent("demo.help.title"));
   }

   @Override
   protected void init() {
      int â˜ƒ = -16;
      this.addRenderableWidget(new Button(this.width / 2 - 116, this.height / 2 + 62 + -16, 114, 20, new TranslatableComponent("demo.help.buy"), var0 -> {
         var0.active = false;
         Util.getPlatform().openUri("http://www.minecraft.net/store?source=demo");
      }));
      this.addRenderableWidget(new Button(this.width / 2 + 2, this.height / 2 + 62 + -16, 114, 20, new TranslatableComponent("demo.help.later"), var1x -> {
         this.minecraft.setScreen(null);
         this.minecraft.mouseHandler.grabMouse();
      }));
      Options â˜ƒx = this.minecraft.options;
      this.movementMessage = MultiLineLabel.create(
         this.font,
         new TranslatableComponent(
            "demo.help.movementShort",
            â˜ƒx.keyUp.getTranslatedKeyMessage(),
            â˜ƒx.keyLeft.getTranslatedKeyMessage(),
            â˜ƒx.keyDown.getTranslatedKeyMessage(),
            â˜ƒx.keyRight.getTranslatedKeyMessage()
         ),
         new TranslatableComponent("demo.help.movementMouse"),
         new TranslatableComponent("demo.help.jump", â˜ƒx.keyJump.getTranslatedKeyMessage()),
         new TranslatableComponent("demo.help.inventory", â˜ƒx.keyInventory.getTranslatedKeyMessage())
      );
      this.durationMessage = MultiLineLabel.create(this.font, new TranslatableComponent("demo.help.fullWrapped"), 218);
   }

   @Override
   public void renderBackground(PoseStack var1) {
      super.renderBackground(â˜ƒ);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShaderTexture(0, DEMO_BACKGROUND_LOCATION);
      int â˜ƒ = (this.width - 248) / 2;
      int â˜ƒx = (this.height - 166) / 2;
      this.blit(â˜ƒ, â˜ƒ, â˜ƒx, 0, 0, 248, 166);
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      int â˜ƒ = (this.width - 248) / 2 + 10;
      int â˜ƒx = (this.height - 166) / 2 + 8;
      this.font.draw(â˜ƒ, this.title, (float)â˜ƒ, (float)â˜ƒx, 2039583);
      â˜ƒx = this.movementMessage.renderLeftAlignedNoShadow(â˜ƒ, â˜ƒ, â˜ƒx + 12, 12, 5197647);
      this.durationMessage.renderLeftAlignedNoShadow(â˜ƒ, â˜ƒ, â˜ƒx + 20, 9, 2039583);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
