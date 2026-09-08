package net.minecraft.world.level;

import com.mojang.brigadier.context.CommandContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.CrashReportDetail;
import net.minecraft.ReportedException;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public abstract class BaseCommandBlock implements CommandSource {
   private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
   private static final Component DEFAULT_NAME = new TextComponent("@");
   private long lastExecution = -1L;
   private boolean updateLastExecution = true;
   private int successCount;
   private boolean trackOutput = true;
   @Nullable
   private Component lastOutput;
   private String command = "";
   private Component name = DEFAULT_NAME;

   public int getSuccessCount() {
      return this.successCount;
   }

   public void setSuccessCount(int var1) {
      this.successCount = â˜ƒ;
   }

   public Component getLastOutput() {
      return this.lastOutput == null ? TextComponent.EMPTY : this.lastOutput;
   }

   public CompoundTag save(CompoundTag var1) {
      â˜ƒ.putString("Command", this.command);
      â˜ƒ.putInt("SuccessCount", this.successCount);
      â˜ƒ.putString("CustomName", Component.Serializer.toJson(this.name));
      â˜ƒ.putBoolean("TrackOutput", this.trackOutput);
      if (this.lastOutput != null && this.trackOutput) {
         â˜ƒ.putString("LastOutput", Component.Serializer.toJson(this.lastOutput));
      }

      â˜ƒ.putBoolean("UpdateLastExecution", this.updateLastExecution);
      if (this.updateLastExecution && this.lastExecution > 0L) {
         â˜ƒ.putLong("LastExecution", this.lastExecution);
      }

      return â˜ƒ;
   }

   public void load(CompoundTag var1) {
      this.command = â˜ƒ.getString("Command");
      this.successCount = â˜ƒ.getInt("SuccessCount");
      if (â˜ƒ.contains("CustomName", 8)) {
         this.setName(Component.Serializer.fromJson(â˜ƒ.getString("CustomName")));
      }

      if (â˜ƒ.contains("TrackOutput", 1)) {
         this.trackOutput = â˜ƒ.getBoolean("TrackOutput");
      }

      if (â˜ƒ.contains("LastOutput", 8) && this.trackOutput) {
         try {
            this.lastOutput = Component.Serializer.fromJson(â˜ƒ.getString("LastOutput"));
         } catch (Throwable var3) {
            this.lastOutput = new TextComponent(var3.getMessage());
         }
      } else {
         this.lastOutput = null;
      }

      if (â˜ƒ.contains("UpdateLastExecution")) {
         this.updateLastExecution = â˜ƒ.getBoolean("UpdateLastExecution");
      }

      if (this.updateLastExecution && â˜ƒ.contains("LastExecution")) {
         this.lastExecution = â˜ƒ.getLong("LastExecution");
      } else {
         this.lastExecution = -1L;
      }
   }

   public void setCommand(String var1) {
      this.command = â˜ƒ;
      this.successCount = 0;
   }

   public String getCommand() {
      return this.command;
   }

   public boolean performCommand(Level var1) {
      if (â˜ƒ.isClientSide || â˜ƒ.getGameTime() == this.lastExecution) {
         return false;
      } else if ("Searge".equalsIgnoreCase(this.command)) {
         this.lastOutput = new TextComponent("#itzlipofutzli");
         this.successCount = 1;
         return true;
      } else {
         this.successCount = 0;
         MinecraftServer â˜ƒ = this.getLevel().getServer();
         if (â˜ƒ.isCommandBlockEnabled() && !StringUtil.isNullOrEmpty(this.command)) {
            try {
               this.lastOutput = null;
               CommandSourceStack â˜ƒx = this.createCommandSourceStack().withCallback((var1x, var2x, var3x) -> {
                  if (var2x) {
                     ++this.successCount;
                  }
               });
               â˜ƒ.getCommands().performCommand(â˜ƒx, this.command);
            } catch (Throwable var6) {
               CrashReport â˜ƒxx = CrashReport.forThrowable(var6, "Executing command block");
               CrashReportCategory â˜ƒxxx = â˜ƒxx.addCategory("Command to be executed");
               â˜ƒxxx.setDetail("Command", this::getCommand);
               â˜ƒxxx.setDetail("Name", (CrashReportDetail<String>)(() -> this.getName().getString()));
               throw new ReportedException(â˜ƒxx);
            }
         }

         if (this.updateLastExecution) {
            this.lastExecution = â˜ƒ.getGameTime();
         } else {
            this.lastExecution = -1L;
         }

         return true;
      }
   }

   public Component getName() {
      return this.name;
   }

   public void setName(@Nullable Component var1) {
      if (â˜ƒ != null) {
         this.name = â˜ƒ;
      } else {
         this.name = DEFAULT_NAME;
      }
   }

   @Override
   public void sendMessage(Component var1, UUID var2) {
      if (this.trackOutput) {
         this.lastOutput = new TextComponent("[" + TIME_FORMAT.format(new Date()) + "] ").append(â˜ƒ);
         this.onUpdated();
      }
   }

   public abstract ServerLevel getLevel();

   public abstract void onUpdated();

   public void setLastOutput(@Nullable Component var1) {
      this.lastOutput = â˜ƒ;
   }

   public void setTrackOutput(boolean var1) {
      this.trackOutput = â˜ƒ;
   }

   public boolean isTrackOutput() {
      return this.trackOutput;
   }

   public InteractionResult usedBy(Player var1) {
      if (!â˜ƒ.canUseGameMasterBlocks()) {
         return InteractionResult.PASS;
      } else {
         if (â˜ƒ.getCommandSenderWorld().isClientSide) {
            â˜ƒ.openMinecartCommandBlock(this);
         }

         return InteractionResult.sidedSuccess(â˜ƒ.level.isClientSide);
      }
   }

   public abstract Vec3 getPosition();

   public abstract CommandSourceStack createCommandSourceStack();

   @Override
   public boolean acceptsSuccess() {
      return this.getLevel().getGameRules().getBoolean(GameRules.RULE_SENDCOMMANDFEEDBACK) && this.trackOutput;
   }

   @Override
   public boolean acceptsFailure() {
      return this.trackOutput;
   }

   @Override
   public boolean shouldInformAdmins() {
      return this.getLevel().getGameRules().getBoolean(GameRules.RULE_COMMANDBLOCKOUTPUT);
   }
}
