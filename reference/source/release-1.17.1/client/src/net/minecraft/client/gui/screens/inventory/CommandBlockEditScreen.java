package net.minecraft.client.gui.screens.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ServerboundSetCommandBlockPacket;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraft.world.level.block.entity.CommandBlockEntity;

public class CommandBlockEditScreen extends AbstractCommandBlockEditScreen {
   private final CommandBlockEntity autoCommandBlock;
   private CycleButton<CommandBlockEntity.Mode> modeButton;
   private CycleButton<Boolean> conditionalButton;
   private CycleButton<Boolean> autoexecButton;
   private CommandBlockEntity.Mode mode = CommandBlockEntity.Mode.REDSTONE;
   private boolean conditional;
   private boolean autoexec;

   public CommandBlockEditScreen(CommandBlockEntity var1) {
      this.autoCommandBlock = â˜ƒ;
   }

   @Override
   BaseCommandBlock getCommandBlock() {
      return this.autoCommandBlock.getCommandBlock();
   }

   @Override
   int getPreviousY() {
      return 135;
   }

   @Override
   protected void init() {
      super.init();
      this.modeButton = this.addRenderableWidget(
         CycleButton.builder(var0 -> {
               switch(var0) {
                  case SEQUENCE:
                     return new TranslatableComponent("advMode.mode.sequence");
                  case AUTO:
                     return new TranslatableComponent("advMode.mode.auto");
                  case REDSTONE:
                  default:
                     return new TranslatableComponent("advMode.mode.redstone");
               }
            })
            .withValues(CommandBlockEntity.Mode.values())
            .displayOnlyValue()
            .withInitialValue(this.mode)
            .create(this.width / 2 - 50 - 100 - 4, 165, 100, 20, new TranslatableComponent("advMode.mode"), (var1, var2) -> this.mode = var2)
      );
      this.conditionalButton = this.addRenderableWidget(
         CycleButton.booleanBuilder(new TranslatableComponent("advMode.mode.conditional"), new TranslatableComponent("advMode.mode.unconditional"))
            .displayOnlyValue()
            .withInitialValue(this.conditional)
            .create(this.width / 2 - 50, 165, 100, 20, new TranslatableComponent("advMode.type"), (var1, var2) -> this.conditional = var2)
      );
      this.autoexecButton = this.addRenderableWidget(
         CycleButton.booleanBuilder(new TranslatableComponent("advMode.mode.autoexec.bat"), new TranslatableComponent("advMode.mode.redstoneTriggered"))
            .displayOnlyValue()
            .withInitialValue(this.autoexec)
            .create(this.width / 2 + 50 + 4, 165, 100, 20, new TranslatableComponent("advMode.triggering"), (var1, var2) -> this.autoexec = var2)
      );
      this.enableControls(false);
   }

   private void enableControls(boolean var1) {
      this.doneButton.active = â˜ƒ;
      this.outputButton.active = â˜ƒ;
      this.modeButton.active = â˜ƒ;
      this.conditionalButton.active = â˜ƒ;
      this.autoexecButton.active = â˜ƒ;
   }

   public void updateGui() {
      BaseCommandBlock â˜ƒ = this.autoCommandBlock.getCommandBlock();
      this.commandEdit.setValue(â˜ƒ.getCommand());
      boolean â˜ƒx = â˜ƒ.isTrackOutput();
      this.mode = this.autoCommandBlock.getMode();
      this.conditional = this.autoCommandBlock.isConditional();
      this.autoexec = this.autoCommandBlock.isAutomatic();
      this.outputButton.setValue(â˜ƒx);
      this.modeButton.setValue(this.mode);
      this.conditionalButton.setValue(this.conditional);
      this.autoexecButton.setValue(this.autoexec);
      this.updatePreviousOutput(â˜ƒx);
      this.enableControls(true);
   }

   @Override
   public void resize(Minecraft var1, int var2, int var3) {
      super.resize(â˜ƒ, â˜ƒ, â˜ƒ);
      this.enableControls(true);
   }

   @Override
   protected void populateAndSendPacket(BaseCommandBlock var1) {
      this.minecraft
         .getConnection()
         .send(
            new ServerboundSetCommandBlockPacket(
               new BlockPos(â˜ƒ.getPosition()), this.commandEdit.getValue(), this.mode, â˜ƒ.isTrackOutput(), this.conditional, this.autoexec
            )
         );
   }
}
