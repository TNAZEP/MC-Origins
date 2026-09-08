package net.minecraft.world.level.block.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.UUID;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class SignBlockEntity extends BlockEntity {
   public static final int LINES = 4;
   private static final String[] RAW_TEXT_FIELD_NAMES = new String[]{"Text1", "Text2", "Text3", "Text4"};
   private static final String[] FILTERED_TEXT_FIELD_NAMES = new String[]{"FilteredText1", "FilteredText2", "FilteredText3", "FilteredText4"};
   private final Component[] messages = new Component[]{TextComponent.EMPTY, TextComponent.EMPTY, TextComponent.EMPTY, TextComponent.EMPTY};
   private final Component[] filteredMessages = new Component[]{TextComponent.EMPTY, TextComponent.EMPTY, TextComponent.EMPTY, TextComponent.EMPTY};
   private boolean isEditable = true;
   @Nullable
   private UUID playerWhoMayEdit;
   @Nullable
   private FormattedCharSequence[] renderMessages;
   private boolean renderMessagedFiltered;
   private DyeColor color = DyeColor.BLACK;
   private boolean hasGlowingText;

   public SignBlockEntity(BlockPos var1, BlockState var2) {
      super(BlockEntityType.SIGN, â˜ƒ, â˜ƒ);
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      super.save(â˜ƒ);

      for(int â˜ƒ = 0; â˜ƒ < 4; ++â˜ƒ) {
         Component â˜ƒx = this.messages[â˜ƒ];
         String â˜ƒxx = Component.Serializer.toJson(â˜ƒx);
         â˜ƒ.putString(RAW_TEXT_FIELD_NAMES[â˜ƒ], â˜ƒxx);
         Component â˜ƒxxx = this.filteredMessages[â˜ƒ];
         if (!â˜ƒxxx.equals(â˜ƒx)) {
            â˜ƒ.putString(FILTERED_TEXT_FIELD_NAMES[â˜ƒ], Component.Serializer.toJson(â˜ƒxxx));
         }
      }

      â˜ƒ.putString("Color", this.color.getName());
      â˜ƒ.putBoolean("GlowingText", this.hasGlowingText);
      return â˜ƒ;
   }

   @Override
   public void load(CompoundTag var1) {
      this.isEditable = false;
      super.load(â˜ƒ);
      this.color = DyeColor.byName(â˜ƒ.getString("Color"), DyeColor.BLACK);

      for(int â˜ƒ = 0; â˜ƒ < 4; ++â˜ƒ) {
         String â˜ƒx = â˜ƒ.getString(RAW_TEXT_FIELD_NAMES[â˜ƒ]);
         Component â˜ƒxx = this.loadLine(â˜ƒx);
         this.messages[â˜ƒ] = â˜ƒxx;
         String â˜ƒxxx = FILTERED_TEXT_FIELD_NAMES[â˜ƒ];
         if (â˜ƒ.contains(â˜ƒxxx, 8)) {
            this.filteredMessages[â˜ƒ] = this.loadLine(â˜ƒ.getString(â˜ƒxxx));
         } else {
            this.filteredMessages[â˜ƒ] = â˜ƒxx;
         }
      }

      this.renderMessages = null;
      this.hasGlowingText = â˜ƒ.getBoolean("GlowingText");
   }

   private Component loadLine(String var1) {
      Component â˜ƒ = this.deserializeTextSafe(â˜ƒ);
      if (this.level instanceof ServerLevel) {
         try {
            return ComponentUtils.updateForEntity(this.createCommandSourceStack(null), â˜ƒ, null, 0);
         } catch (CommandSyntaxException var4) {
         }
      }

      return â˜ƒ;
   }

   private Component deserializeTextSafe(String var1) {
      try {
         Component â˜ƒ = Component.Serializer.fromJson(â˜ƒ);
         if (â˜ƒ != null) {
            return â˜ƒ;
         }
      } catch (Exception var3) {
      }

      return TextComponent.EMPTY;
   }

   public Component getMessage(int var1, boolean var2) {
      return this.getMessages(â˜ƒ)[â˜ƒ];
   }

   public void setMessage(int var1, Component var2) {
      this.setMessage(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void setMessage(int var1, Component var2, Component var3) {
      this.messages[â˜ƒ] = â˜ƒ;
      this.filteredMessages[â˜ƒ] = â˜ƒ;
      this.renderMessages = null;
   }

   public FormattedCharSequence[] getRenderMessages(boolean var1, Function<Component, FormattedCharSequence> var2) {
      if (this.renderMessages == null || this.renderMessagedFiltered != â˜ƒ) {
         this.renderMessagedFiltered = â˜ƒ;
         this.renderMessages = new FormattedCharSequence[4];

         for(int â˜ƒ = 0; â˜ƒ < 4; ++â˜ƒ) {
            this.renderMessages[â˜ƒ] = (FormattedCharSequence)â˜ƒ.apply(this.getMessage(â˜ƒ, â˜ƒ));
         }
      }

      return this.renderMessages;
   }

   private Component[] getMessages(boolean var1) {
      return â˜ƒ ? this.filteredMessages : this.messages;
   }

   @Nullable
   @Override
   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return new ClientboundBlockEntityDataPacket(this.worldPosition, 9, this.getUpdateTag());
   }

   @Override
   public CompoundTag getUpdateTag() {
      return this.save(new CompoundTag());
   }

   @Override
   public boolean onlyOpCanSetNbt() {
      return true;
   }

   public boolean isEditable() {
      return this.isEditable;
   }

   public void setEditable(boolean var1) {
      this.isEditable = â˜ƒ;
      if (!â˜ƒ) {
         this.playerWhoMayEdit = null;
      }
   }

   public void setAllowedPlayerEditor(UUID var1) {
      this.playerWhoMayEdit = â˜ƒ;
   }

   @Nullable
   public UUID getPlayerWhoMayEdit() {
      return this.playerWhoMayEdit;
   }

   public boolean executeClickCommands(ServerPlayer var1) {
      for(Component â˜ƒ : this.getMessages(â˜ƒ.isTextFilteringEnabled())) {
         Style â˜ƒx = â˜ƒ.getStyle();
         ClickEvent â˜ƒxx = â˜ƒx.getClickEvent();
         if (â˜ƒxx != null && â˜ƒxx.getAction() == ClickEvent.Action.RUN_COMMAND) {
            â˜ƒ.getServer().getCommands().performCommand(this.createCommandSourceStack(â˜ƒ), â˜ƒxx.getValue());
         }
      }

      return true;
   }

   public CommandSourceStack createCommandSourceStack(@Nullable ServerPlayer var1) {
      String â˜ƒ = â˜ƒ == null ? "Sign" : â˜ƒ.getName().getString();
      Component â˜ƒx = (Component)(â˜ƒ == null ? new TextComponent("Sign") : â˜ƒ.getDisplayName());
      return new CommandSourceStack(
         CommandSource.NULL, Vec3.atCenterOf(this.worldPosition), Vec2.ZERO, (ServerLevel)this.level, 2, â˜ƒ, â˜ƒx, this.level.getServer(), â˜ƒ
      );
   }

   public DyeColor getColor() {
      return this.color;
   }

   public boolean setColor(DyeColor var1) {
      if (â˜ƒ != this.getColor()) {
         this.color = â˜ƒ;
         this.markUpdated();
         return true;
      } else {
         return false;
      }
   }

   public boolean hasGlowingText() {
      return this.hasGlowingText;
   }

   public boolean setHasGlowingText(boolean var1) {
      if (this.hasGlowingText != â˜ƒ) {
         this.hasGlowingText = â˜ƒ;
         this.markUpdated();
         return true;
      } else {
         return false;
      }
   }

   private void markUpdated() {
      this.setChanged();
      this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
   }
}
