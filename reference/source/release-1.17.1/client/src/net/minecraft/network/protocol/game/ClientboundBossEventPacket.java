package net.minecraft.network.protocol.game;

import java.util.UUID;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.BossEvent;

public class ClientboundBossEventPacket implements Packet<ClientGamePacketListener> {
   private static final int FLAG_DARKEN = 1;
   private static final int FLAG_MUSIC = 2;
   private static final int FLAG_FOG = 4;
   private final UUID id;
   private final ClientboundBossEventPacket.Operation operation;
   static final ClientboundBossEventPacket.Operation REMOVE_OPERATION = new ClientboundBossEventPacket.Operation() {
      @Override
      public ClientboundBossEventPacket.OperationType getType() {
         return ClientboundBossEventPacket.OperationType.REMOVE;
      }

      @Override
      public void dispatch(UUID var1, ClientboundBossEventPacket.Handler var2) {
         â˜ƒ.remove(â˜ƒ);
      }

      @Override
      public void write(FriendlyByteBuf var1) {
      }
   };

   private ClientboundBossEventPacket(UUID var1, ClientboundBossEventPacket.Operation var2) {
      this.id = â˜ƒ;
      this.operation = â˜ƒ;
   }

   public ClientboundBossEventPacket(FriendlyByteBuf var1) {
      this.id = â˜ƒ.readUUID();
      ClientboundBossEventPacket.OperationType â˜ƒ = â˜ƒ.readEnum(ClientboundBossEventPacket.OperationType.class);
      this.operation = (ClientboundBossEventPacket.Operation)â˜ƒ.reader.apply(â˜ƒ);
   }

   public static ClientboundBossEventPacket createAddPacket(BossEvent var0) {
      return new ClientboundBossEventPacket(â˜ƒ.getId(), new ClientboundBossEventPacket.AddOperation(â˜ƒ));
   }

   public static ClientboundBossEventPacket createRemovePacket(UUID var0) {
      return new ClientboundBossEventPacket(â˜ƒ, REMOVE_OPERATION);
   }

   public static ClientboundBossEventPacket createUpdateProgressPacket(BossEvent var0) {
      return new ClientboundBossEventPacket(â˜ƒ.getId(), new ClientboundBossEventPacket.UpdateProgressOperation(â˜ƒ.getProgress()));
   }

   public static ClientboundBossEventPacket createUpdateNamePacket(BossEvent var0) {
      return new ClientboundBossEventPacket(â˜ƒ.getId(), new ClientboundBossEventPacket.UpdateNameOperation(â˜ƒ.getName()));
   }

   public static ClientboundBossEventPacket createUpdateStylePacket(BossEvent var0) {
      return new ClientboundBossEventPacket(â˜ƒ.getId(), new ClientboundBossEventPacket.UpdateStyleOperation(â˜ƒ.getColor(), â˜ƒ.getOverlay()));
   }

   public static ClientboundBossEventPacket createUpdatePropertiesPacket(BossEvent var0) {
      return new ClientboundBossEventPacket(
         â˜ƒ.getId(), new ClientboundBossEventPacket.UpdatePropertiesOperation(â˜ƒ.shouldDarkenScreen(), â˜ƒ.shouldPlayBossMusic(), â˜ƒ.shouldCreateWorldFog())
      );
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeUUID(this.id);
      â˜ƒ.writeEnum(this.operation.getType());
      this.operation.write(â˜ƒ);
   }

   static int encodeProperties(boolean var0, boolean var1, boolean var2) {
      int â˜ƒ = 0;
      if (â˜ƒ) {
         â˜ƒ |= 1;
      }

      if (â˜ƒ) {
         â˜ƒ |= 2;
      }

      if (â˜ƒ) {
         â˜ƒ |= 4;
      }

      return â˜ƒ;
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleBossUpdate(this);
   }

   public void dispatch(ClientboundBossEventPacket.Handler var1) {
      this.operation.dispatch(this.id, â˜ƒ);
   }

   static class AddOperation implements ClientboundBossEventPacket.Operation {
      private final Component name;
      private final float progress;
      private final BossEvent.BossBarColor color;
      private final BossEvent.BossBarOverlay overlay;
      private final boolean darkenScreen;
      private final boolean playMusic;
      private final boolean createWorldFog;

      AddOperation(BossEvent var1) {
         this.name = â˜ƒ.getName();
         this.progress = â˜ƒ.getProgress();
         this.color = â˜ƒ.getColor();
         this.overlay = â˜ƒ.getOverlay();
         this.darkenScreen = â˜ƒ.shouldDarkenScreen();
         this.playMusic = â˜ƒ.shouldPlayBossMusic();
         this.createWorldFog = â˜ƒ.shouldCreateWorldFog();
      }

      private AddOperation(FriendlyByteBuf var1) {
         this.name = â˜ƒ.readComponent();
         this.progress = â˜ƒ.readFloat();
         this.color = â˜ƒ.readEnum(BossEvent.BossBarColor.class);
         this.overlay = â˜ƒ.readEnum(BossEvent.BossBarOverlay.class);
         int â˜ƒ = â˜ƒ.readUnsignedByte();
         this.darkenScreen = (â˜ƒ & 1) > 0;
         this.playMusic = (â˜ƒ & 2) > 0;
         this.createWorldFog = (â˜ƒ & 4) > 0;
      }

      @Override
      public ClientboundBossEventPacket.OperationType getType() {
         return ClientboundBossEventPacket.OperationType.ADD;
      }

      @Override
      public void dispatch(UUID var1, ClientboundBossEventPacket.Handler var2) {
         â˜ƒ.add(â˜ƒ, this.name, this.progress, this.color, this.overlay, this.darkenScreen, this.playMusic, this.createWorldFog);
      }

      @Override
      public void write(FriendlyByteBuf var1) {
         â˜ƒ.writeComponent(this.name);
         â˜ƒ.writeFloat(this.progress);
         â˜ƒ.writeEnum(this.color);
         â˜ƒ.writeEnum(this.overlay);
         â˜ƒ.writeByte(ClientboundBossEventPacket.encodeProperties(this.darkenScreen, this.playMusic, this.createWorldFog));
      }
   }

   public interface Handler {
      default void add(
         UUID var1, Component var2, float var3, BossEvent.BossBarColor var4, BossEvent.BossBarOverlay var5, boolean var6, boolean var7, boolean var8
      ) {
      }

      default void remove(UUID var1) {
      }

      default void updateProgress(UUID var1, float var2) {
      }

      default void updateName(UUID var1, Component var2) {
      }

      default void updateStyle(UUID var1, BossEvent.BossBarColor var2, BossEvent.BossBarOverlay var3) {
      }

      default void updateProperties(UUID var1, boolean var2, boolean var3, boolean var4) {
      }
   }

   interface Operation {
      ClientboundBossEventPacket.OperationType getType();

      void dispatch(UUID var1, ClientboundBossEventPacket.Handler var2);

      void write(FriendlyByteBuf var1);
   }

   static enum OperationType {
      ADD(ClientboundBossEventPacket.AddOperation::new),
      REMOVE(var0 -> ClientboundBossEventPacket.REMOVE_OPERATION),
      UPDATE_PROGRESS(ClientboundBossEventPacket.UpdateProgressOperation::new),
      UPDATE_NAME(ClientboundBossEventPacket.UpdateNameOperation::new),
      UPDATE_STYLE(ClientboundBossEventPacket.UpdateStyleOperation::new),
      UPDATE_PROPERTIES(ClientboundBossEventPacket.UpdatePropertiesOperation::new);

      final Function<FriendlyByteBuf, ClientboundBossEventPacket.Operation> reader;

      private OperationType(Function<FriendlyByteBuf, ClientboundBossEventPacket.Operation> var3) {
         this.reader = â˜ƒ;
      }
   }

   static class UpdateNameOperation implements ClientboundBossEventPacket.Operation {
      private final Component name;

      UpdateNameOperation(Component var1) {
         this.name = â˜ƒ;
      }

      private UpdateNameOperation(FriendlyByteBuf var1) {
         this.name = â˜ƒ.readComponent();
      }

      @Override
      public ClientboundBossEventPacket.OperationType getType() {
         return ClientboundBossEventPacket.OperationType.UPDATE_NAME;
      }

      @Override
      public void dispatch(UUID var1, ClientboundBossEventPacket.Handler var2) {
         â˜ƒ.updateName(â˜ƒ, this.name);
      }

      @Override
      public void write(FriendlyByteBuf var1) {
         â˜ƒ.writeComponent(this.name);
      }
   }

   static class UpdateProgressOperation implements ClientboundBossEventPacket.Operation {
      private final float progress;

      UpdateProgressOperation(float var1) {
         this.progress = â˜ƒ;
      }

      private UpdateProgressOperation(FriendlyByteBuf var1) {
         this.progress = â˜ƒ.readFloat();
      }

      @Override
      public ClientboundBossEventPacket.OperationType getType() {
         return ClientboundBossEventPacket.OperationType.UPDATE_PROGRESS;
      }

      @Override
      public void dispatch(UUID var1, ClientboundBossEventPacket.Handler var2) {
         â˜ƒ.updateProgress(â˜ƒ, this.progress);
      }

      @Override
      public void write(FriendlyByteBuf var1) {
         â˜ƒ.writeFloat(this.progress);
      }
   }

   static class UpdatePropertiesOperation implements ClientboundBossEventPacket.Operation {
      private final boolean darkenScreen;
      private final boolean playMusic;
      private final boolean createWorldFog;

      UpdatePropertiesOperation(boolean var1, boolean var2, boolean var3) {
         this.darkenScreen = â˜ƒ;
         this.playMusic = â˜ƒ;
         this.createWorldFog = â˜ƒ;
      }

      private UpdatePropertiesOperation(FriendlyByteBuf var1) {
         int â˜ƒ = â˜ƒ.readUnsignedByte();
         this.darkenScreen = (â˜ƒ & 1) > 0;
         this.playMusic = (â˜ƒ & 2) > 0;
         this.createWorldFog = (â˜ƒ & 4) > 0;
      }

      @Override
      public ClientboundBossEventPacket.OperationType getType() {
         return ClientboundBossEventPacket.OperationType.UPDATE_PROPERTIES;
      }

      @Override
      public void dispatch(UUID var1, ClientboundBossEventPacket.Handler var2) {
         â˜ƒ.updateProperties(â˜ƒ, this.darkenScreen, this.playMusic, this.createWorldFog);
      }

      @Override
      public void write(FriendlyByteBuf var1) {
         â˜ƒ.writeByte(ClientboundBossEventPacket.encodeProperties(this.darkenScreen, this.playMusic, this.createWorldFog));
      }
   }

   static class UpdateStyleOperation implements ClientboundBossEventPacket.Operation {
      private final BossEvent.BossBarColor color;
      private final BossEvent.BossBarOverlay overlay;

      UpdateStyleOperation(BossEvent.BossBarColor var1, BossEvent.BossBarOverlay var2) {
         this.color = â˜ƒ;
         this.overlay = â˜ƒ;
      }

      private UpdateStyleOperation(FriendlyByteBuf var1) {
         this.color = â˜ƒ.readEnum(BossEvent.BossBarColor.class);
         this.overlay = â˜ƒ.readEnum(BossEvent.BossBarOverlay.class);
      }

      @Override
      public ClientboundBossEventPacket.OperationType getType() {
         return ClientboundBossEventPacket.OperationType.UPDATE_STYLE;
      }

      @Override
      public void dispatch(UUID var1, ClientboundBossEventPacket.Handler var2) {
         â˜ƒ.updateStyle(â˜ƒ, this.color, this.overlay);
      }

      @Override
      public void write(FriendlyByteBuf var1) {
         â˜ƒ.writeEnum(this.color);
         â˜ƒ.writeEnum(this.overlay);
      }
   }
}
