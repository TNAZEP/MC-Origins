package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import java.util.stream.IntStream;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.font.TextFieldHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ServerboundSignUpdatePacket;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class SignEditScreen extends Screen {
   private final SignBlockEntity sign;
   private int frame;
   private int line;
   private TextFieldHelper signField;
   private WoodType woodType;
   private SignRenderer.SignModel signModel;
   private final String[] messages;

   public SignEditScreen(SignBlockEntity var1, boolean var2) {
      super(new TranslatableComponent("sign.edit"));
      this.messages = (String[])IntStream.range(0, 4).mapToObj(var2x -> â˜ƒ.getMessage(var2x, â˜ƒ)).map(Component::getString).toArray(var0 -> new String[var0]);
      this.sign = â˜ƒ;
   }

   @Override
   protected void init() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
      this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 4 + 120, 200, 20, CommonComponents.GUI_DONE, var1x -> this.onDone()));
      this.sign.setEditable(false);
      this.signField = new TextFieldHelper(
         () -> this.messages[this.line],
         var1x -> {
            this.messages[this.line] = var1x;
            this.sign.setMessage(this.line, new TextComponent(var1x));
         },
         TextFieldHelper.createClipboardGetter(this.minecraft),
         TextFieldHelper.createClipboardSetter(this.minecraft),
         var1x -> this.minecraft.font.width(var1x) <= 90
      );
      BlockState â˜ƒ = this.sign.getBlockState();
      this.woodType = SignRenderer.getWoodType(â˜ƒ.getBlock());
      this.signModel = SignRenderer.createSignModel(this.minecraft.getEntityModels(), this.woodType);
   }

   @Override
   public void removed() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
      ClientPacketListener â˜ƒ = this.minecraft.getConnection();
      if (â˜ƒ != null) {
         â˜ƒ.send(new ServerboundSignUpdatePacket(this.sign.getBlockPos(), this.messages[0], this.messages[1], this.messages[2], this.messages[3]));
      }

      this.sign.setEditable(true);
   }

   @Override
   public void tick() {
      ++this.frame;
      if (!this.sign.getType().isValid(this.sign.getBlockState())) {
         this.onDone();
      }
   }

   private void onDone() {
      this.sign.setChanged();
      this.minecraft.setScreen(null);
   }

   @Override
   public boolean charTyped(char var1, int var2) {
      this.signField.charTyped(â˜ƒ);
      return true;
   }

   @Override
   public void onClose() {
      this.onDone();
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (â˜ƒ == 265) {
         this.line = this.line - 1 & 3;
         this.signField.setCursorToEnd();
         return true;
      } else if (â˜ƒ == 264 || â˜ƒ == 257 || â˜ƒ == 335) {
         this.line = this.line + 1 & 3;
         this.signField.setCursorToEnd();
         return true;
      } else {
         return this.signField.keyPressed(â˜ƒ) ? true : super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      Lighting.setupForFlatItems();
      this.renderBackground(â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 40, 16777215);
      â˜ƒ.pushPose();
      â˜ƒ.translate((double)(this.width / 2), 0.0, 50.0);
      float â˜ƒ = 93.75F;
      â˜ƒ.scale(93.75F, -93.75F, 93.75F);
      â˜ƒ.translate(0.0, -1.3125, 0.0);
      BlockState â˜ƒx = this.sign.getBlockState();
      boolean â˜ƒxx = â˜ƒx.getBlock() instanceof StandingSignBlock;
      if (!â˜ƒxx) {
         â˜ƒ.translate(0.0, -0.3125, 0.0);
      }

      boolean â˜ƒ = this.frame / 6 % 2 == 0;
      float â˜ƒx = 0.6666667F;
      â˜ƒ.pushPose();
      â˜ƒ.scale(0.6666667F, -0.6666667F, -0.6666667F);
      MultiBufferSource.BufferSource â˜ƒxx = this.minecraft.renderBuffers().bufferSource();
      Material â˜ƒxxx = Sheets.getSignMaterial(this.woodType);
      VertexConsumer â˜ƒxxxx = â˜ƒxxx.buffer(â˜ƒxx, this.signModel::renderType);
      this.signModel.stick.visible = â˜ƒxx;
      this.signModel.root.render(â˜ƒ, â˜ƒxxxx, 15728880, OverlayTexture.NO_OVERLAY);
      â˜ƒ.popPose();
      float â˜ƒxxxxx = 0.010416667F;
      â˜ƒ.translate(0.0, 0.33333334F, 0.046666667F);
      â˜ƒ.scale(0.010416667F, -0.010416667F, 0.010416667F);
      int â˜ƒxxxxxx = this.sign.getColor().getTextColor();
      int â˜ƒxxxxxxx = this.signField.getCursorPos();
      int â˜ƒxxxxxxxx = this.signField.getSelectionPos();
      int â˜ƒxxxxxxxxx = this.line * 10 - this.messages.length * 5;
      Matrix4f â˜ƒxxxxxxxxxx = â˜ƒ.last().pose();

      for(int â˜ƒxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxx < this.messages.length; ++â˜ƒxxxxxxxxxxx) {
         String â˜ƒxxxxxxxxxxxx = this.messages[â˜ƒxxxxxxxxxxx];
         if (â˜ƒxxxxxxxxxxxx != null) {
            if (this.font.isBidirectional()) {
               â˜ƒxxxxxxxxxxxx = this.font.bidirectionalShaping(â˜ƒxxxxxxxxxxxx);
            }

            float â˜ƒxxxxxxxxxxxxx = (float)(-this.minecraft.font.width(â˜ƒxxxxxxxxxxxx) / 2);
            this.minecraft
               .font
               .drawInBatch(
                  â˜ƒxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxx,
                  (float)(â˜ƒxxxxxxxxxxx * 10 - this.messages.length * 5),
                  â˜ƒxxxxxx,
                  false,
                  â˜ƒxxxxxxxxxx,
                  â˜ƒxx,
                  false,
                  0,
                  15728880,
                  false
               );
            if (â˜ƒxxxxxxxxxxx == this.line && â˜ƒxxxxxxx >= 0 && â˜ƒ) {
               int â˜ƒxxxxxxxxxxxxxx = this.minecraft.font.width(â˜ƒxxxxxxxxxxxx.substring(0, Math.max(Math.min(â˜ƒxxxxxxx, â˜ƒxxxxxxxxxxxx.length()), 0)));
               int â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxx - this.minecraft.font.width(â˜ƒxxxxxxxxxxxx) / 2;
               if (â˜ƒxxxxxxx >= â˜ƒxxxxxxxxxxxx.length()) {
                  this.minecraft
                     .font
                     .drawInBatch("_", (float)â˜ƒxxxxxxxxxxxxxxx, (float)â˜ƒxxxxxxxxx, â˜ƒxxxxxx, false, â˜ƒxxxxxxxxxx, â˜ƒxx, false, 0, 15728880, false);
               }
            }
         }
      }

      â˜ƒxx.endBatch();

      for(int â˜ƒxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxx < this.messages.length; ++â˜ƒxxxxxxxxxxx) {
         String â˜ƒxxxxxxxxxxxx = this.messages[â˜ƒxxxxxxxxxxx];
         if (â˜ƒxxxxxxxxxxxx != null && â˜ƒxxxxxxxxxxx == this.line && â˜ƒxxxxxxx >= 0) {
            int â˜ƒxxxxxxxxxxxxx = this.minecraft.font.width(â˜ƒxxxxxxxxxxxx.substring(0, Math.max(Math.min(â˜ƒxxxxxxx, â˜ƒxxxxxxxxxxxx.length()), 0)));
            int â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxx - this.minecraft.font.width(â˜ƒxxxxxxxxxxxx) / 2;
            if (â˜ƒ && â˜ƒxxxxxxx < â˜ƒxxxxxxxxxxxx.length()) {
               fill(â˜ƒ, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxx - 1, â˜ƒxxxxxxxxxxxxxx + 1, â˜ƒxxxxxxxxx + 9, 0xFF000000 | â˜ƒxxxxxx);
            }

            if (â˜ƒxxxxxxxx != â˜ƒxxxxxxx) {
               int â˜ƒxxxxxxxxxxxxx = Math.min(â˜ƒxxxxxxx, â˜ƒxxxxxxxx);
               int â˜ƒxxxxxxxxxxxxxx = Math.max(â˜ƒxxxxxxx, â˜ƒxxxxxxxx);
               int â˜ƒxxxxxxxxxxxxxxx = this.minecraft.font.width(â˜ƒxxxxxxxxxxxx.substring(0, â˜ƒxxxxxxxxxxxxx))
                  - this.minecraft.font.width(â˜ƒxxxxxxxxxxxx) / 2;
               int â˜ƒxxxxxxxxxxxxxxxx = this.minecraft.font.width(â˜ƒxxxxxxxxxxxx.substring(0, â˜ƒxxxxxxxxxxxxxx))
                  - this.minecraft.font.width(â˜ƒxxxxxxxxxxxx) / 2;
               int â˜ƒxxxxxxxxxxxxxxxxx = Math.min(â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx);
               int â˜ƒxxxxxxxxxxxxxxxxxx = Math.max(â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx);
               Tesselator â˜ƒxxxxxxxxxxxxxxxxxxx = Tesselator.getInstance();
               BufferBuilder â˜ƒxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxx.getBuilder();
               RenderSystem.setShader(GameRenderer::getPositionColorShader);
               RenderSystem.disableTexture();
               RenderSystem.enableColorLogicOp();
               RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
               â˜ƒxxxxxxxxxxxxxxxxxxxx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
               â˜ƒxxxxxxxxxxxxxxxxxxxx.vertex(â˜ƒxxxxxxxxxx, (float)â˜ƒxxxxxxxxxxxxxxxxx, (float)(â˜ƒxxxxxxxxx + 9), 0.0F).color(0, 0, 255, 255).endVertex();
               â˜ƒxxxxxxxxxxxxxxxxxxxx.vertex(â˜ƒxxxxxxxxxx, (float)â˜ƒxxxxxxxxxxxxxxxxxx, (float)(â˜ƒxxxxxxxxx + 9), 0.0F).color(0, 0, 255, 255).endVertex();
               â˜ƒxxxxxxxxxxxxxxxxxxxx.vertex(â˜ƒxxxxxxxxxx, (float)â˜ƒxxxxxxxxxxxxxxxxxx, (float)â˜ƒxxxxxxxxx, 0.0F).color(0, 0, 255, 255).endVertex();
               â˜ƒxxxxxxxxxxxxxxxxxxxx.vertex(â˜ƒxxxxxxxxxx, (float)â˜ƒxxxxxxxxxxxxxxxxx, (float)â˜ƒxxxxxxxxx, 0.0F).color(0, 0, 255, 255).endVertex();
               â˜ƒxxxxxxxxxxxxxxxxxxxx.end();
               BufferUploader.end(â˜ƒxxxxxxxxxxxxxxxxxxxx);
               RenderSystem.disableColorLogicOp();
               RenderSystem.enableTexture();
            }
         }
      }

      â˜ƒ.popPose();
      Lighting.setupFor3DItems();
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
