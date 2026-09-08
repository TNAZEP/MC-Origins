package net.minecraft.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.ResultConsumer;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class CommandSourceStack implements SharedSuggestionProvider {
   public static final SimpleCommandExceptionType ERROR_NOT_PLAYER = new SimpleCommandExceptionType(new TranslatableComponent("permissions.requires.player"));
   public static final SimpleCommandExceptionType ERROR_NOT_ENTITY = new SimpleCommandExceptionType(new TranslatableComponent("permissions.requires.entity"));
   private final CommandSource source;
   private final Vec3 worldPosition;
   private final ServerLevel level;
   private final int permissionLevel;
   private final String textName;
   private final Component displayName;
   private final MinecraftServer server;
   private final boolean silent;
   @Nullable
   private final Entity entity;
   private final ResultConsumer<CommandSourceStack> consumer;
   private final EntityAnchorArgument.Anchor anchor;
   private final Vec2 rotation;

   public CommandSourceStack(
      CommandSource var1, Vec3 var2, Vec2 var3, ServerLevel var4, int var5, String var6, Component var7, MinecraftServer var8, @Nullable Entity var9
   ) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, false, (var0, var1x, var2x) -> {
      }, EntityAnchorArgument.Anchor.FEET);
   }

   protected CommandSourceStack(
      CommandSource var1,
      Vec3 var2,
      Vec2 var3,
      ServerLevel var4,
      int var5,
      String var6,
      Component var7,
      MinecraftServer var8,
      @Nullable Entity var9,
      boolean var10,
      ResultConsumer<CommandSourceStack> var11,
      EntityAnchorArgument.Anchor var12
   ) {
      this.source = â˜ƒ;
      this.worldPosition = â˜ƒ;
      this.level = â˜ƒ;
      this.silent = â˜ƒ;
      this.entity = â˜ƒ;
      this.permissionLevel = â˜ƒ;
      this.textName = â˜ƒ;
      this.displayName = â˜ƒ;
      this.server = â˜ƒ;
      this.consumer = â˜ƒ;
      this.anchor = â˜ƒ;
      this.rotation = â˜ƒ;
   }

   public CommandSourceStack withSource(CommandSource var1) {
      return this.source == â˜ƒ
         ? this
         : new CommandSourceStack(
            â˜ƒ,
            this.worldPosition,
            this.rotation,
            this.level,
            this.permissionLevel,
            this.textName,
            this.displayName,
            this.server,
            this.entity,
            this.silent,
            this.consumer,
            this.anchor
         );
   }

   public CommandSourceStack withEntity(Entity var1) {
      return this.entity == â˜ƒ
         ? this
         : new CommandSourceStack(
            this.source,
            this.worldPosition,
            this.rotation,
            this.level,
            this.permissionLevel,
            â˜ƒ.getName().getString(),
            â˜ƒ.getDisplayName(),
            this.server,
            â˜ƒ,
            this.silent,
            this.consumer,
            this.anchor
         );
   }

   public CommandSourceStack withPosition(Vec3 var1) {
      return this.worldPosition.equals(â˜ƒ)
         ? this
         : new CommandSourceStack(
            this.source,
            â˜ƒ,
            this.rotation,
            this.level,
            this.permissionLevel,
            this.textName,
            this.displayName,
            this.server,
            this.entity,
            this.silent,
            this.consumer,
            this.anchor
         );
   }

   public CommandSourceStack withRotation(Vec2 var1) {
      return this.rotation.equals(â˜ƒ)
         ? this
         : new CommandSourceStack(
            this.source,
            this.worldPosition,
            â˜ƒ,
            this.level,
            this.permissionLevel,
            this.textName,
            this.displayName,
            this.server,
            this.entity,
            this.silent,
            this.consumer,
            this.anchor
         );
   }

   public CommandSourceStack withCallback(ResultConsumer<CommandSourceStack> var1) {
      return this.consumer.equals(â˜ƒ)
         ? this
         : new CommandSourceStack(
            this.source,
            this.worldPosition,
            this.rotation,
            this.level,
            this.permissionLevel,
            this.textName,
            this.displayName,
            this.server,
            this.entity,
            this.silent,
            â˜ƒ,
            this.anchor
         );
   }

   public CommandSourceStack withCallback(ResultConsumer<CommandSourceStack> var1, BinaryOperator<ResultConsumer<CommandSourceStack>> var2) {
      ResultConsumer<CommandSourceStack> â˜ƒ = (ResultConsumer)â˜ƒ.apply(this.consumer, â˜ƒ);
      return this.withCallback(â˜ƒ);
   }

   public CommandSourceStack withSuppressedOutput() {
      return !this.silent && !this.source.alwaysAccepts()
         ? new CommandSourceStack(
            this.source,
            this.worldPosition,
            this.rotation,
            this.level,
            this.permissionLevel,
            this.textName,
            this.displayName,
            this.server,
            this.entity,
            true,
            this.consumer,
            this.anchor
         )
         : this;
   }

   public CommandSourceStack withPermission(int var1) {
      return â˜ƒ == this.permissionLevel
         ? this
         : new CommandSourceStack(
            this.source,
            this.worldPosition,
            this.rotation,
            this.level,
            â˜ƒ,
            this.textName,
            this.displayName,
            this.server,
            this.entity,
            this.silent,
            this.consumer,
            this.anchor
         );
   }

   public CommandSourceStack withMaximumPermission(int var1) {
      return â˜ƒ <= this.permissionLevel
         ? this
         : new CommandSourceStack(
            this.source,
            this.worldPosition,
            this.rotation,
            this.level,
            â˜ƒ,
            this.textName,
            this.displayName,
            this.server,
            this.entity,
            this.silent,
            this.consumer,
            this.anchor
         );
   }

   public CommandSourceStack withAnchor(EntityAnchorArgument.Anchor var1) {
      return â˜ƒ == this.anchor
         ? this
         : new CommandSourceStack(
            this.source,
            this.worldPosition,
            this.rotation,
            this.level,
            this.permissionLevel,
            this.textName,
            this.displayName,
            this.server,
            this.entity,
            this.silent,
            this.consumer,
            â˜ƒ
         );
   }

   public CommandSourceStack withLevel(ServerLevel var1) {
      if (â˜ƒ == this.level) {
         return this;
      } else {
         double â˜ƒ = DimensionType.getTeleportationScale(this.level.dimensionType(), â˜ƒ.dimensionType());
         Vec3 â˜ƒx = new Vec3(this.worldPosition.x * â˜ƒ, this.worldPosition.y, this.worldPosition.z * â˜ƒ);
         return new CommandSourceStack(
            this.source,
            â˜ƒx,
            this.rotation,
            â˜ƒ,
            this.permissionLevel,
            this.textName,
            this.displayName,
            this.server,
            this.entity,
            this.silent,
            this.consumer,
            this.anchor
         );
      }
   }

   public CommandSourceStack facing(Entity var1, EntityAnchorArgument.Anchor var2) {
      return this.facing(â˜ƒ.apply(â˜ƒ));
   }

   public CommandSourceStack facing(Vec3 var1) {
      Vec3 â˜ƒ = this.anchor.apply(this);
      double â˜ƒx = â˜ƒ.x - â˜ƒ.x;
      double â˜ƒxx = â˜ƒ.y - â˜ƒ.y;
      double â˜ƒxxx = â˜ƒ.z - â˜ƒ.z;
      double â˜ƒxxxx = Math.sqrt(â˜ƒx * â˜ƒx + â˜ƒxxx * â˜ƒxxx);
      float â˜ƒxxxxx = Mth.wrapDegrees((float)(-(Mth.atan2(â˜ƒxx, â˜ƒxxxx) * 180.0F / (float)Math.PI)));
      float â˜ƒxxxxxx = Mth.wrapDegrees((float)(Mth.atan2(â˜ƒxxx, â˜ƒx) * 180.0F / (float)Math.PI) - 90.0F);
      return this.withRotation(new Vec2(â˜ƒxxxxx, â˜ƒxxxxxx));
   }

   public Component getDisplayName() {
      return this.displayName;
   }

   public String getTextName() {
      return this.textName;
   }

   @Override
   public boolean hasPermission(int var1) {
      return this.permissionLevel >= â˜ƒ;
   }

   public Vec3 getPosition() {
      return this.worldPosition;
   }

   public ServerLevel getLevel() {
      return this.level;
   }

   @Nullable
   public Entity getEntity() {
      return this.entity;
   }

   public Entity getEntityOrException() throws CommandSyntaxException {
      if (this.entity == null) {
         throw ERROR_NOT_ENTITY.create();
      } else {
         return this.entity;
      }
   }

   public ServerPlayer getPlayerOrException() throws CommandSyntaxException {
      if (!(this.entity instanceof ServerPlayer)) {
         throw ERROR_NOT_PLAYER.create();
      } else {
         return (ServerPlayer)this.entity;
      }
   }

   public Vec2 getRotation() {
      return this.rotation;
   }

   public MinecraftServer getServer() {
      return this.server;
   }

   public EntityAnchorArgument.Anchor getAnchor() {
      return this.anchor;
   }

   public void sendSuccess(Component var1, boolean var2) {
      if (this.source.acceptsSuccess() && !this.silent) {
         this.source.sendMessage(â˜ƒ, Util.NIL_UUID);
      }

      if (â˜ƒ && this.source.shouldInformAdmins() && !this.silent) {
         this.broadcastToAdmins(â˜ƒ);
      }
   }

   private void broadcastToAdmins(Component var1) {
      Component â˜ƒ = new TranslatableComponent("chat.type.admin", this.getDisplayName(), â˜ƒ)
         .withStyle(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC});
      if (this.server.getGameRules().getBoolean(GameRules.RULE_SENDCOMMANDFEEDBACK)) {
         for(ServerPlayer â˜ƒx : this.server.getPlayerList().getPlayers()) {
            if (â˜ƒx != this.source && this.server.getPlayerList().isOp(â˜ƒx.getGameProfile())) {
               â˜ƒx.sendMessage(â˜ƒ, Util.NIL_UUID);
            }
         }
      }

      if (this.source != this.server && this.server.getGameRules().getBoolean(GameRules.RULE_LOGADMINCOMMANDS)) {
         this.server.sendMessage(â˜ƒ, Util.NIL_UUID);
      }
   }

   public void sendFailure(Component var1) {
      if (this.source.acceptsFailure() && !this.silent) {
         this.source.sendMessage(new TextComponent("").append(â˜ƒ).withStyle(ChatFormatting.RED), Util.NIL_UUID);
      }
   }

   public void onCommandComplete(CommandContext<CommandSourceStack> var1, boolean var2, int var3) {
      if (this.consumer != null) {
         this.consumer.onCommandComplete(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public Collection<String> getOnlinePlayerNames() {
      return Lists.newArrayList(this.server.getPlayerNames());
   }

   @Override
   public Collection<String> getAllTeams() {
      return this.server.getScoreboard().getTeamNames();
   }

   @Override
   public Collection<ResourceLocation> getAvailableSoundEvents() {
      return Registry.SOUND_EVENT.keySet();
   }

   @Override
   public Stream<ResourceLocation> getRecipeNames() {
      return this.server.getRecipeManager().getRecipeIds();
   }

   @Override
   public CompletableFuture<Suggestions> customSuggestion(CommandContext<SharedSuggestionProvider> var1, SuggestionsBuilder var2) {
      return null;
   }

   @Override
   public Set<ResourceKey<Level>> levels() {
      return this.server.levelKeys();
   }

   @Override
   public RegistryAccess registryAccess() {
      return this.server.registryAccess();
   }
}
