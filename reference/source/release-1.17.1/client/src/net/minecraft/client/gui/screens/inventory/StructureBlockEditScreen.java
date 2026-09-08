package net.minecraft.client.gui.screens.inventory;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ServerboundSetStructureBlockPacket;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.properties.StructureMode;

public class StructureBlockEditScreen extends Screen {
   private static final Component NAME_LABEL = new TranslatableComponent("structure_block.structure_name");
   private static final Component POSITION_LABEL = new TranslatableComponent("structure_block.position");
   private static final Component SIZE_LABEL = new TranslatableComponent("structure_block.size");
   private static final Component INTEGRITY_LABEL = new TranslatableComponent("structure_block.integrity");
   private static final Component CUSTOM_DATA_LABEL = new TranslatableComponent("structure_block.custom_data");
   private static final Component INCLUDE_ENTITIES_LABEL = new TranslatableComponent("structure_block.include_entities");
   private static final Component DETECT_SIZE_LABEL = new TranslatableComponent("structure_block.detect_size");
   private static final Component SHOW_AIR_LABEL = new TranslatableComponent("structure_block.show_air");
   private static final Component SHOW_BOUNDING_BOX_LABEL = new TranslatableComponent("structure_block.show_boundingbox");
   private static final ImmutableList<StructureMode> ALL_MODES = ImmutableList.copyOf(StructureMode.values());
   private static final ImmutableList<StructureMode> DEFAULT_MODES = (ImmutableList<StructureMode>)ALL_MODES.stream()
      .filter(var0 -> var0 != StructureMode.DATA)
      .collect(ImmutableList.toImmutableList());
   private final StructureBlockEntity structure;
   private Mirror initialMirror = Mirror.NONE;
   private Rotation initialRotation = Rotation.NONE;
   private StructureMode initialMode = StructureMode.DATA;
   private boolean initialEntityIgnoring;
   private boolean initialShowAir;
   private boolean initialShowBoundingBox;
   private EditBox nameEdit;
   private EditBox posXEdit;
   private EditBox posYEdit;
   private EditBox posZEdit;
   private EditBox sizeXEdit;
   private EditBox sizeYEdit;
   private EditBox sizeZEdit;
   private EditBox integrityEdit;
   private EditBox seedEdit;
   private EditBox dataEdit;
   private Button saveButton;
   private Button loadButton;
   private Button rot0Button;
   private Button rot90Button;
   private Button rot180Button;
   private Button rot270Button;
   private Button detectButton;
   private CycleButton<Boolean> includeEntitiesButton;
   private CycleButton<Mirror> mirrorButton;
   private CycleButton<Boolean> toggleAirButton;
   private CycleButton<Boolean> toggleBoundingBox;
   private final DecimalFormat decimalFormat = new DecimalFormat("0.0###");

   public StructureBlockEditScreen(StructureBlockEntity var1) {
      super(new TranslatableComponent(Blocks.STRUCTURE_BLOCK.getDescriptionId()));
      this.structure = â˜ƒ;
      this.decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
   }

   @Override
   public void tick() {
      this.nameEdit.tick();
      this.posXEdit.tick();
      this.posYEdit.tick();
      this.posZEdit.tick();
      this.sizeXEdit.tick();
      this.sizeYEdit.tick();
      this.sizeZEdit.tick();
      this.integrityEdit.tick();
      this.seedEdit.tick();
      this.dataEdit.tick();
   }

   private void onDone() {
      if (this.sendToServer(StructureBlockEntity.UpdateType.UPDATE_DATA)) {
         this.minecraft.setScreen(null);
      }
   }

   private void onCancel() {
      this.structure.setMirror(this.initialMirror);
      this.structure.setRotation(this.initialRotation);
      this.structure.setMode(this.initialMode);
      this.structure.setIgnoreEntities(this.initialEntityIgnoring);
      this.structure.setShowAir(this.initialShowAir);
      this.structure.setShowBoundingBox(this.initialShowBoundingBox);
      this.minecraft.setScreen(null);
   }

   @Override
   protected void init() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
      this.addRenderableWidget(new Button(this.width / 2 - 4 - 150, 210, 150, 20, CommonComponents.GUI_DONE, var1x -> this.onDone()));
      this.addRenderableWidget(new Button(this.width / 2 + 4, 210, 150, 20, CommonComponents.GUI_CANCEL, var1x -> this.onCancel()));
      this.initialMirror = this.structure.getMirror();
      this.initialRotation = this.structure.getRotation();
      this.initialMode = this.structure.getMode();
      this.initialEntityIgnoring = this.structure.isIgnoreEntities();
      this.initialShowAir = this.structure.getShowAir();
      this.initialShowBoundingBox = this.structure.getShowBoundingBox();
      this.saveButton = this.addRenderableWidget(
         new Button(this.width / 2 + 4 + 100, 185, 50, 20, new TranslatableComponent("structure_block.button.save"), var1x -> {
            if (this.structure.getMode() == StructureMode.SAVE) {
               this.sendToServer(StructureBlockEntity.UpdateType.SAVE_AREA);
               this.minecraft.setScreen(null);
            }
         })
      );
      this.loadButton = this.addRenderableWidget(
         new Button(this.width / 2 + 4 + 100, 185, 50, 20, new TranslatableComponent("structure_block.button.load"), var1x -> {
            if (this.structure.getMode() == StructureMode.LOAD) {
               this.sendToServer(StructureBlockEntity.UpdateType.LOAD_AREA);
               this.minecraft.setScreen(null);
            }
         })
      );
      this.addRenderableWidget(
         CycleButton.builder(var0 -> new TranslatableComponent("structure_block.mode." + var0.getSerializedName()))
            .withValues(DEFAULT_MODES, ALL_MODES)
            .displayOnlyValue()
            .withInitialValue(this.initialMode)
            .create(this.width / 2 - 4 - 150, 185, 50, 20, new TextComponent("MODE"), (var1x, var2x) -> {
               this.structure.setMode(var2x);
               this.updateMode(var2x);
            })
      );
      this.detectButton = this.addRenderableWidget(
         new Button(this.width / 2 + 4 + 100, 120, 50, 20, new TranslatableComponent("structure_block.button.detect_size"), var1x -> {
            if (this.structure.getMode() == StructureMode.SAVE) {
               this.sendToServer(StructureBlockEntity.UpdateType.SCAN_AREA);
               this.minecraft.setScreen(null);
            }
         })
      );
      this.includeEntitiesButton = this.addRenderableWidget(
         CycleButton.onOffBuilder(!this.structure.isIgnoreEntities())
            .displayOnlyValue()
            .create(this.width / 2 + 4 + 100, 160, 50, 20, INCLUDE_ENTITIES_LABEL, (var1x, var2x) -> this.structure.setIgnoreEntities(!var2x))
      );
      this.mirrorButton = this.addRenderableWidget(
         CycleButton.builder(Mirror::symbol)
            .withValues(Mirror.values())
            .displayOnlyValue()
            .withInitialValue(this.initialMirror)
            .create(this.width / 2 - 20, 185, 40, 20, new TextComponent("MIRROR"), (var1x, var2x) -> this.structure.setMirror(var2x))
      );
      this.toggleAirButton = this.addRenderableWidget(
         CycleButton.onOffBuilder(this.structure.getShowAir())
            .displayOnlyValue()
            .create(this.width / 2 + 4 + 100, 80, 50, 20, SHOW_AIR_LABEL, (var1x, var2x) -> this.structure.setShowAir(var2x))
      );
      this.toggleBoundingBox = this.addRenderableWidget(
         CycleButton.onOffBuilder(this.structure.getShowBoundingBox())
            .displayOnlyValue()
            .create(this.width / 2 + 4 + 100, 80, 50, 20, SHOW_BOUNDING_BOX_LABEL, (var1x, var2x) -> this.structure.setShowBoundingBox(var2x))
      );
      this.rot0Button = this.addRenderableWidget(new Button(this.width / 2 - 1 - 40 - 1 - 40 - 20, 185, 40, 20, new TextComponent("0"), var1x -> {
         this.structure.setRotation(Rotation.NONE);
         this.updateDirectionButtons();
      }));
      this.rot90Button = this.addRenderableWidget(new Button(this.width / 2 - 1 - 40 - 20, 185, 40, 20, new TextComponent("90"), var1x -> {
         this.structure.setRotation(Rotation.CLOCKWISE_90);
         this.updateDirectionButtons();
      }));
      this.rot180Button = this.addRenderableWidget(new Button(this.width / 2 + 1 + 20, 185, 40, 20, new TextComponent("180"), var1x -> {
         this.structure.setRotation(Rotation.CLOCKWISE_180);
         this.updateDirectionButtons();
      }));
      this.rot270Button = this.addRenderableWidget(new Button(this.width / 2 + 1 + 40 + 1 + 20, 185, 40, 20, new TextComponent("270"), var1x -> {
         this.structure.setRotation(Rotation.COUNTERCLOCKWISE_90);
         this.updateDirectionButtons();
      }));
      this.nameEdit = new EditBox(this.font, this.width / 2 - 152, 40, 300, 20, new TranslatableComponent("structure_block.structure_name")) {
         @Override
         public boolean charTyped(char var1, int var2) {
            return !StructureBlockEditScreen.this.isValidCharacterForName(this.getValue(), â˜ƒ, this.getCursorPosition()) ? false : super.charTyped(â˜ƒ, â˜ƒ);
         }
      };
      this.nameEdit.setMaxLength(64);
      this.nameEdit.setValue(this.structure.getStructureName());
      this.addWidget(this.nameEdit);
      BlockPos â˜ƒ = this.structure.getStructurePos();
      this.posXEdit = new EditBox(this.font, this.width / 2 - 152, 80, 80, 20, new TranslatableComponent("structure_block.position.x"));
      this.posXEdit.setMaxLength(15);
      this.posXEdit.setValue(Integer.toString(â˜ƒ.getX()));
      this.addWidget(this.posXEdit);
      this.posYEdit = new EditBox(this.font, this.width / 2 - 72, 80, 80, 20, new TranslatableComponent("structure_block.position.y"));
      this.posYEdit.setMaxLength(15);
      this.posYEdit.setValue(Integer.toString(â˜ƒ.getY()));
      this.addWidget(this.posYEdit);
      this.posZEdit = new EditBox(this.font, this.width / 2 + 8, 80, 80, 20, new TranslatableComponent("structure_block.position.z"));
      this.posZEdit.setMaxLength(15);
      this.posZEdit.setValue(Integer.toString(â˜ƒ.getZ()));
      this.addWidget(this.posZEdit);
      Vec3i â˜ƒx = this.structure.getStructureSize();
      this.sizeXEdit = new EditBox(this.font, this.width / 2 - 152, 120, 80, 20, new TranslatableComponent("structure_block.size.x"));
      this.sizeXEdit.setMaxLength(15);
      this.sizeXEdit.setValue(Integer.toString(â˜ƒx.getX()));
      this.addWidget(this.sizeXEdit);
      this.sizeYEdit = new EditBox(this.font, this.width / 2 - 72, 120, 80, 20, new TranslatableComponent("structure_block.size.y"));
      this.sizeYEdit.setMaxLength(15);
      this.sizeYEdit.setValue(Integer.toString(â˜ƒx.getY()));
      this.addWidget(this.sizeYEdit);
      this.sizeZEdit = new EditBox(this.font, this.width / 2 + 8, 120, 80, 20, new TranslatableComponent("structure_block.size.z"));
      this.sizeZEdit.setMaxLength(15);
      this.sizeZEdit.setValue(Integer.toString(â˜ƒx.getZ()));
      this.addWidget(this.sizeZEdit);
      this.integrityEdit = new EditBox(this.font, this.width / 2 - 152, 120, 80, 20, new TranslatableComponent("structure_block.integrity.integrity"));
      this.integrityEdit.setMaxLength(15);
      this.integrityEdit.setValue(this.decimalFormat.format((double)this.structure.getIntegrity()));
      this.addWidget(this.integrityEdit);
      this.seedEdit = new EditBox(this.font, this.width / 2 - 72, 120, 80, 20, new TranslatableComponent("structure_block.integrity.seed"));
      this.seedEdit.setMaxLength(31);
      this.seedEdit.setValue(Long.toString(this.structure.getSeed()));
      this.addWidget(this.seedEdit);
      this.dataEdit = new EditBox(this.font, this.width / 2 - 152, 120, 240, 20, new TranslatableComponent("structure_block.custom_data"));
      this.dataEdit.setMaxLength(128);
      this.dataEdit.setValue(this.structure.getMetaData());
      this.addWidget(this.dataEdit);
      this.updateDirectionButtons();
      this.updateMode(this.initialMode);
      this.setInitialFocus(this.nameEdit);
   }

   @Override
   public void resize(Minecraft var1, int var2, int var3) {
      String â˜ƒ = this.nameEdit.getValue();
      String â˜ƒx = this.posXEdit.getValue();
      String â˜ƒxx = this.posYEdit.getValue();
      String â˜ƒxxx = this.posZEdit.getValue();
      String â˜ƒxxxx = this.sizeXEdit.getValue();
      String â˜ƒxxxxx = this.sizeYEdit.getValue();
      String â˜ƒxxxxxx = this.sizeZEdit.getValue();
      String â˜ƒxxxxxxx = this.integrityEdit.getValue();
      String â˜ƒxxxxxxxx = this.seedEdit.getValue();
      String â˜ƒxxxxxxxxx = this.dataEdit.getValue();
      this.init(â˜ƒ, â˜ƒ, â˜ƒ);
      this.nameEdit.setValue(â˜ƒ);
      this.posXEdit.setValue(â˜ƒx);
      this.posYEdit.setValue(â˜ƒxx);
      this.posZEdit.setValue(â˜ƒxxx);
      this.sizeXEdit.setValue(â˜ƒxxxx);
      this.sizeYEdit.setValue(â˜ƒxxxxx);
      this.sizeZEdit.setValue(â˜ƒxxxxxx);
      this.integrityEdit.setValue(â˜ƒxxxxxxx);
      this.seedEdit.setValue(â˜ƒxxxxxxxx);
      this.dataEdit.setValue(â˜ƒxxxxxxxxx);
   }

   @Override
   public void removed() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
   }

   private void updateDirectionButtons() {
      this.rot0Button.active = true;
      this.rot90Button.active = true;
      this.rot180Button.active = true;
      this.rot270Button.active = true;
      switch(this.structure.getRotation()) {
         case NONE:
            this.rot0Button.active = false;
            break;
         case CLOCKWISE_180:
            this.rot180Button.active = false;
            break;
         case COUNTERCLOCKWISE_90:
            this.rot270Button.active = false;
            break;
         case CLOCKWISE_90:
            this.rot90Button.active = false;
      }
   }

   private void updateMode(StructureMode var1) {
      this.nameEdit.setVisible(false);
      this.posXEdit.setVisible(false);
      this.posYEdit.setVisible(false);
      this.posZEdit.setVisible(false);
      this.sizeXEdit.setVisible(false);
      this.sizeYEdit.setVisible(false);
      this.sizeZEdit.setVisible(false);
      this.integrityEdit.setVisible(false);
      this.seedEdit.setVisible(false);
      this.dataEdit.setVisible(false);
      this.saveButton.visible = false;
      this.loadButton.visible = false;
      this.detectButton.visible = false;
      this.includeEntitiesButton.visible = false;
      this.mirrorButton.visible = false;
      this.rot0Button.visible = false;
      this.rot90Button.visible = false;
      this.rot180Button.visible = false;
      this.rot270Button.visible = false;
      this.toggleAirButton.visible = false;
      this.toggleBoundingBox.visible = false;
      switch(â˜ƒ) {
         case SAVE:
            this.nameEdit.setVisible(true);
            this.posXEdit.setVisible(true);
            this.posYEdit.setVisible(true);
            this.posZEdit.setVisible(true);
            this.sizeXEdit.setVisible(true);
            this.sizeYEdit.setVisible(true);
            this.sizeZEdit.setVisible(true);
            this.saveButton.visible = true;
            this.detectButton.visible = true;
            this.includeEntitiesButton.visible = true;
            this.toggleAirButton.visible = true;
            break;
         case LOAD:
            this.nameEdit.setVisible(true);
            this.posXEdit.setVisible(true);
            this.posYEdit.setVisible(true);
            this.posZEdit.setVisible(true);
            this.integrityEdit.setVisible(true);
            this.seedEdit.setVisible(true);
            this.loadButton.visible = true;
            this.includeEntitiesButton.visible = true;
            this.mirrorButton.visible = true;
            this.rot0Button.visible = true;
            this.rot90Button.visible = true;
            this.rot180Button.visible = true;
            this.rot270Button.visible = true;
            this.toggleBoundingBox.visible = true;
            this.updateDirectionButtons();
            break;
         case CORNER:
            this.nameEdit.setVisible(true);
            break;
         case DATA:
            this.dataEdit.setVisible(true);
      }
   }

   private boolean sendToServer(StructureBlockEntity.UpdateType var1) {
      BlockPos â˜ƒ = new BlockPos(
         this.parseCoordinate(this.posXEdit.getValue()), this.parseCoordinate(this.posYEdit.getValue()), this.parseCoordinate(this.posZEdit.getValue())
      );
      Vec3i â˜ƒx = new Vec3i(
         this.parseCoordinate(this.sizeXEdit.getValue()), this.parseCoordinate(this.sizeYEdit.getValue()), this.parseCoordinate(this.sizeZEdit.getValue())
      );
      float â˜ƒxx = this.parseIntegrity(this.integrityEdit.getValue());
      long â˜ƒxxx = this.parseSeed(this.seedEdit.getValue());
      this.minecraft
         .getConnection()
         .send(
            new ServerboundSetStructureBlockPacket(
               this.structure.getBlockPos(),
               â˜ƒ,
               this.structure.getMode(),
               this.nameEdit.getValue(),
               â˜ƒ,
               â˜ƒx,
               this.structure.getMirror(),
               this.structure.getRotation(),
               this.dataEdit.getValue(),
               this.structure.isIgnoreEntities(),
               this.structure.getShowAir(),
               this.structure.getShowBoundingBox(),
               â˜ƒxx,
               â˜ƒxxx
            )
         );
      return true;
   }

   private long parseSeed(String var1) {
      try {
         return Long.valueOf(â˜ƒ);
      } catch (NumberFormatException var3) {
         return 0L;
      }
   }

   private float parseIntegrity(String var1) {
      try {
         return Float.valueOf(â˜ƒ);
      } catch (NumberFormatException var3) {
         return 1.0F;
      }
   }

   private int parseCoordinate(String var1) {
      try {
         return Integer.parseInt(â˜ƒ);
      } catch (NumberFormatException var3) {
         return 0;
      }
   }

   @Override
   public void onClose() {
      this.onCancel();
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ)) {
         return true;
      } else if (â˜ƒ != 257 && â˜ƒ != 335) {
         return false;
      } else {
         this.onDone();
         return true;
      }
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      StructureMode â˜ƒ = this.structure.getMode();
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 10, 16777215);
      if (â˜ƒ != StructureMode.DATA) {
         drawString(â˜ƒ, this.font, NAME_LABEL, this.width / 2 - 153, 30, 10526880);
         this.nameEdit.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      if (â˜ƒ == StructureMode.LOAD || â˜ƒ == StructureMode.SAVE) {
         drawString(â˜ƒ, this.font, POSITION_LABEL, this.width / 2 - 153, 70, 10526880);
         this.posXEdit.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.posYEdit.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.posZEdit.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         drawString(â˜ƒ, this.font, INCLUDE_ENTITIES_LABEL, this.width / 2 + 154 - this.font.width(INCLUDE_ENTITIES_LABEL), 150, 10526880);
      }

      if (â˜ƒ == StructureMode.SAVE) {
         drawString(â˜ƒ, this.font, SIZE_LABEL, this.width / 2 - 153, 110, 10526880);
         this.sizeXEdit.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.sizeYEdit.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.sizeZEdit.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         drawString(â˜ƒ, this.font, DETECT_SIZE_LABEL, this.width / 2 + 154 - this.font.width(DETECT_SIZE_LABEL), 110, 10526880);
         drawString(â˜ƒ, this.font, SHOW_AIR_LABEL, this.width / 2 + 154 - this.font.width(SHOW_AIR_LABEL), 70, 10526880);
      }

      if (â˜ƒ == StructureMode.LOAD) {
         drawString(â˜ƒ, this.font, INTEGRITY_LABEL, this.width / 2 - 153, 110, 10526880);
         this.integrityEdit.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.seedEdit.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         drawString(â˜ƒ, this.font, SHOW_BOUNDING_BOX_LABEL, this.width / 2 + 154 - this.font.width(SHOW_BOUNDING_BOX_LABEL), 70, 10526880);
      }

      if (â˜ƒ == StructureMode.DATA) {
         drawString(â˜ƒ, this.font, CUSTOM_DATA_LABEL, this.width / 2 - 153, 110, 10526880);
         this.dataEdit.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      drawString(â˜ƒ, this.font, â˜ƒ.getDisplayName(), this.width / 2 - 153, 174, 10526880);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean isPauseScreen() {
      return false;
   }
}
