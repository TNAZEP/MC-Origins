package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ServerboundJigsawGeneratePacket;
import net.minecraft.network.protocol.game.ServerboundSetJigsawBlockPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;

public class JigsawBlockEditScreen extends Screen {
   private static final int MAX_LEVELS = 7;
   private static final Component JOINT_LABEL = new TranslatableComponent("jigsaw_block.joint_label");
   private static final Component POOL_LABEL = new TranslatableComponent("jigsaw_block.pool");
   private static final Component NAME_LABEL = new TranslatableComponent("jigsaw_block.name");
   private static final Component TARGET_LABEL = new TranslatableComponent("jigsaw_block.target");
   private static final Component FINAL_STATE_LABEL = new TranslatableComponent("jigsaw_block.final_state");
   private final JigsawBlockEntity jigsawEntity;
   private EditBox nameEdit;
   private EditBox targetEdit;
   private EditBox poolEdit;
   private EditBox finalStateEdit;
   int levels;
   private boolean keepJigsaws = true;
   private CycleButton<JigsawBlockEntity.JointType> jointButton;
   private Button doneButton;
   private Button generateButton;
   private JigsawBlockEntity.JointType joint;

   public JigsawBlockEditScreen(JigsawBlockEntity var1) {
      super(NarratorChatListener.NO_TITLE);
      this.jigsawEntity = â˜ƒ;
   }

   @Override
   public void tick() {
      this.nameEdit.tick();
      this.targetEdit.tick();
      this.poolEdit.tick();
      this.finalStateEdit.tick();
   }

   private void onDone() {
      this.sendToServer();
      this.minecraft.setScreen(null);
   }

   private void onCancel() {
      this.minecraft.setScreen(null);
   }

   private void sendToServer() {
      this.minecraft
         .getConnection()
         .send(
            new ServerboundSetJigsawBlockPacket(
               this.jigsawEntity.getBlockPos(),
               new ResourceLocation(this.nameEdit.getValue()),
               new ResourceLocation(this.targetEdit.getValue()),
               new ResourceLocation(this.poolEdit.getValue()),
               this.finalStateEdit.getValue(),
               this.joint
            )
         );
   }

   private void sendGenerate() {
      this.minecraft.getConnection().send(new ServerboundJigsawGeneratePacket(this.jigsawEntity.getBlockPos(), this.levels, this.keepJigsaws));
   }

   @Override
   public void onClose() {
      this.onCancel();
   }

   @Override
   protected void init() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
      this.poolEdit = new EditBox(this.font, this.width / 2 - 152, 20, 300, 20, new TranslatableComponent("jigsaw_block.pool"));
      this.poolEdit.setMaxLength(128);
      this.poolEdit.setValue(this.jigsawEntity.getPool().toString());
      this.poolEdit.setResponder(var1x -> this.updateValidity());
      this.addWidget(this.poolEdit);
      this.nameEdit = new EditBox(this.font, this.width / 2 - 152, 55, 300, 20, new TranslatableComponent("jigsaw_block.name"));
      this.nameEdit.setMaxLength(128);
      this.nameEdit.setValue(this.jigsawEntity.getName().toString());
      this.nameEdit.setResponder(var1x -> this.updateValidity());
      this.addWidget(this.nameEdit);
      this.targetEdit = new EditBox(this.font, this.width / 2 - 152, 90, 300, 20, new TranslatableComponent("jigsaw_block.target"));
      this.targetEdit.setMaxLength(128);
      this.targetEdit.setValue(this.jigsawEntity.getTarget().toString());
      this.targetEdit.setResponder(var1x -> this.updateValidity());
      this.addWidget(this.targetEdit);
      this.finalStateEdit = new EditBox(this.font, this.width / 2 - 152, 125, 300, 20, new TranslatableComponent("jigsaw_block.final_state"));
      this.finalStateEdit.setMaxLength(256);
      this.finalStateEdit.setValue(this.jigsawEntity.getFinalState());
      this.addWidget(this.finalStateEdit);
      this.joint = this.jigsawEntity.getJoint();
      int â˜ƒ = this.font.width(JOINT_LABEL) + 10;
      this.jointButton = this.addRenderableWidget(
         CycleButton.<JigsawBlockEntity.JointType>builder(JigsawBlockEntity.JointType::getTranslatedName)
            .withValues(JigsawBlockEntity.JointType.values())
            .withInitialValue(this.joint)
            .displayOnlyValue()
            .create(this.width / 2 - 152 + â˜ƒ, 150, 300 - â˜ƒ, 20, JOINT_LABEL, (var1x, var2x) -> this.joint = var2x)
      );
      boolean â˜ƒx = JigsawBlock.getFrontFacing(this.jigsawEntity.getBlockState()).getAxis().isVertical();
      this.jointButton.active = â˜ƒx;
      this.jointButton.visible = â˜ƒx;
      this.addRenderableWidget(new AbstractSliderButton(this.width / 2 - 154, 180, 100, 20, TextComponent.EMPTY, 0.0) {
         {
            this.updateMessage();
         }

         @Override
         protected void updateMessage() {
            this.setMessage(new TranslatableComponent("jigsaw_block.levels", JigsawBlockEditScreen.this.levels));
         }

         @Override
         protected void applyValue() {
            JigsawBlockEditScreen.this.levels = Mth.floor(Mth.clampedLerp(0.0, 7.0, this.value));
         }
      });
      this.addRenderableWidget(
         CycleButton.onOffBuilder(this.keepJigsaws)
            .create(this.width / 2 - 50, 180, 100, 20, new TranslatableComponent("jigsaw_block.keep_jigsaws"), (var1x, var2x) -> this.keepJigsaws = var2x)
      );
      this.generateButton = this.addRenderableWidget(
         new Button(this.width / 2 + 54, 180, 100, 20, new TranslatableComponent("jigsaw_block.generate"), var1x -> {
            this.onDone();
            this.sendGenerate();
         })
      );
      this.doneButton = this.addRenderableWidget(new Button(this.width / 2 - 4 - 150, 210, 150, 20, CommonComponents.GUI_DONE, var1x -> this.onDone()));
      this.addRenderableWidget(new Button(this.width / 2 + 4, 210, 150, 20, CommonComponents.GUI_CANCEL, var1x -> this.onCancel()));
      this.setInitialFocus(this.poolEdit);
      this.updateValidity();
   }

   private void updateValidity() {
      boolean â˜ƒ = ResourceLocation.isValidResourceLocation(this.nameEdit.getValue())
         && ResourceLocation.isValidResourceLocation(this.targetEdit.getValue())
         && ResourceLocation.isValidResourceLocation(this.poolEdit.getValue());
      this.doneButton.active = â˜ƒ;
      this.generateButton.active = â˜ƒ;
   }

   @Override
   public void resize(Minecraft var1, int var2, int var3) {
      String â˜ƒ = this.nameEdit.getValue();
      String â˜ƒx = this.targetEdit.getValue();
      String â˜ƒxx = this.poolEdit.getValue();
      String â˜ƒxxx = this.finalStateEdit.getValue();
      int â˜ƒxxxx = this.levels;
      JigsawBlockEntity.JointType â˜ƒxxxxx = this.joint;
      this.init(â˜ƒ, â˜ƒ, â˜ƒ);
      this.nameEdit.setValue(â˜ƒ);
      this.targetEdit.setValue(â˜ƒx);
      this.poolEdit.setValue(â˜ƒxx);
      this.finalStateEdit.setValue(â˜ƒxxx);
      this.levels = â˜ƒxxxx;
      this.joint = â˜ƒxxxxx;
      this.jointButton.setValue(â˜ƒxxxxx);
   }

   @Override
   public void removed() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ)) {
         return true;
      } else if (!this.doneButton.active || â˜ƒ != 257 && â˜ƒ != 335) {
         return false;
      } else {
         this.onDone();
         return true;
      }
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      drawString(â˜ƒ, this.font, POOL_LABEL, this.width / 2 - 153, 10, 10526880);
      this.poolEdit.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      drawString(â˜ƒ, this.font, NAME_LABEL, this.width / 2 - 153, 45, 10526880);
      this.nameEdit.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      drawString(â˜ƒ, this.font, TARGET_LABEL, this.width / 2 - 153, 80, 10526880);
      this.targetEdit.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      drawString(â˜ƒ, this.font, FINAL_STATE_LABEL, this.width / 2 - 153, 115, 10526880);
      this.finalStateEdit.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (JigsawBlock.getFrontFacing(this.jigsawEntity.getBlockState()).getAxis().isVertical()) {
         drawString(â˜ƒ, this.font, JOINT_LABEL, this.width / 2 - 153, 156, 16777215);
      }

      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
