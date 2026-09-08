package net.minecraft.client.multiplayer;

import com.google.common.collect.Lists;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.protocol.game.ServerboundCommandSuggestionPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ClientSuggestionProvider implements SharedSuggestionProvider {
   private final ClientPacketListener connection;
   private final Minecraft minecraft;
   private int pendingSuggestionsId = -1;
   private CompletableFuture<Suggestions> pendingSuggestionsFuture;

   public ClientSuggestionProvider(ClientPacketListener var1, Minecraft var2) {
      this.connection = â˜ƒ;
      this.minecraft = â˜ƒ;
   }

   @Override
   public Collection<String> getOnlinePlayerNames() {
      List<String> â˜ƒ = Lists.newArrayList();

      for(PlayerInfo â˜ƒx : this.connection.getOnlinePlayers()) {
         â˜ƒ.add(â˜ƒx.getProfile().getName());
      }

      return â˜ƒ;
   }

   @Override
   public Collection<String> getSelectedEntities() {
      return (Collection<String>)(this.minecraft.hitResult != null && this.minecraft.hitResult.getType() == HitResult.Type.ENTITY
         ? Collections.singleton(((EntityHitResult)this.minecraft.hitResult).getEntity().getStringUUID())
         : Collections.emptyList());
   }

   @Override
   public Collection<String> getAllTeams() {
      return this.connection.getLevel().getScoreboard().getTeamNames();
   }

   @Override
   public Collection<ResourceLocation> getAvailableSoundEvents() {
      return this.minecraft.getSoundManager().getAvailableSounds();
   }

   @Override
   public Stream<ResourceLocation> getRecipeNames() {
      return this.connection.getRecipeManager().getRecipeIds();
   }

   @Override
   public boolean hasPermission(int var1) {
      LocalPlayer â˜ƒ = this.minecraft.player;
      return â˜ƒ != null ? â˜ƒ.hasPermissions(â˜ƒ) : â˜ƒ == 0;
   }

   @Override
   public CompletableFuture<Suggestions> customSuggestion(CommandContext<SharedSuggestionProvider> var1, SuggestionsBuilder var2) {
      if (this.pendingSuggestionsFuture != null) {
         this.pendingSuggestionsFuture.cancel(false);
      }

      this.pendingSuggestionsFuture = new CompletableFuture();
      int â˜ƒ = ++this.pendingSuggestionsId;
      this.connection.send(new ServerboundCommandSuggestionPacket(â˜ƒ, â˜ƒ.getInput()));
      return this.pendingSuggestionsFuture;
   }

   private static String prettyPrint(double var0) {
      return String.format(Locale.ROOT, "%.2f", â˜ƒ);
   }

   private static String prettyPrint(int var0) {
      return Integer.toString(â˜ƒ);
   }

   @Override
   public Collection<SharedSuggestionProvider.TextCoordinates> getRelevantCoordinates() {
      HitResult â˜ƒ = this.minecraft.hitResult;
      if (â˜ƒ != null && â˜ƒ.getType() == HitResult.Type.BLOCK) {
         BlockPos â˜ƒx = ((BlockHitResult)â˜ƒ).getBlockPos();
         return Collections.singleton(
            new SharedSuggestionProvider.TextCoordinates(prettyPrint(â˜ƒx.getX()), prettyPrint(â˜ƒx.getY()), prettyPrint(â˜ƒx.getZ()))
         );
      } else {
         return SharedSuggestionProvider.super.getRelevantCoordinates();
      }
   }

   @Override
   public Collection<SharedSuggestionProvider.TextCoordinates> getAbsoluteCoordinates() {
      HitResult â˜ƒ = this.minecraft.hitResult;
      if (â˜ƒ != null && â˜ƒ.getType() == HitResult.Type.BLOCK) {
         Vec3 â˜ƒx = â˜ƒ.getLocation();
         return Collections.singleton(new SharedSuggestionProvider.TextCoordinates(prettyPrint(â˜ƒx.x), prettyPrint(â˜ƒx.y), prettyPrint(â˜ƒx.z)));
      } else {
         return SharedSuggestionProvider.super.getAbsoluteCoordinates();
      }
   }

   @Override
   public Set<ResourceKey<Level>> levels() {
      return this.connection.levels();
   }

   @Override
   public RegistryAccess registryAccess() {
      return this.connection.registryAccess();
   }

   public void completeCustomSuggestions(int var1, Suggestions var2) {
      if (â˜ƒ == this.pendingSuggestionsId) {
         this.pendingSuggestionsFuture.complete(â˜ƒ);
         this.pendingSuggestionsFuture = null;
         this.pendingSuggestionsId = -1;
      }
   }
}
