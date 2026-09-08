package net.minecraft.network.protocol.game;

import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class ServerboundInteractPacket implements Packet<ServerGamePacketListener> {
   private final int entityId;
   private final ServerboundInteractPacket.Action action;
   private final boolean usingSecondaryAction;
   static final ServerboundInteractPacket.Action ATTACK_ACTION = new ServerboundInteractPacket.Action() {
      @Override
      public ServerboundInteractPacket.ActionType getType() {
         return ServerboundInteractPacket.ActionType.ATTACK;
      }

      @Override
      public void dispatch(ServerboundInteractPacket.Handler var1) {
         â˜ƒ.onAttack();
      }

      @Override
      public void write(FriendlyByteBuf var1) {
      }
   };

   private ServerboundInteractPacket(int var1, boolean var2, ServerboundInteractPacket.Action var3) {
      this.entityId = â˜ƒ;
      this.action = â˜ƒ;
      this.usingSecondaryAction = â˜ƒ;
   }

   public static ServerboundInteractPacket createAttackPacket(Entity var0, boolean var1) {
      return new ServerboundInteractPacket(â˜ƒ.getId(), â˜ƒ, ATTACK_ACTION);
   }

   public static ServerboundInteractPacket createInteractionPacket(Entity var0, boolean var1, InteractionHand var2) {
      return new ServerboundInteractPacket(â˜ƒ.getId(), â˜ƒ, new ServerboundInteractPacket.InteractionAction(â˜ƒ));
   }

   public static ServerboundInteractPacket createInteractionPacket(Entity var0, boolean var1, InteractionHand var2, Vec3 var3) {
      return new ServerboundInteractPacket(â˜ƒ.getId(), â˜ƒ, new ServerboundInteractPacket.InteractionAtLocationAction(â˜ƒ, â˜ƒ));
   }

   public ServerboundInteractPacket(FriendlyByteBuf var1) {
      this.entityId = â˜ƒ.readVarInt();
      ServerboundInteractPacket.ActionType â˜ƒ = â˜ƒ.readEnum(ServerboundInteractPacket.ActionType.class);
      this.action = (ServerboundInteractPacket.Action)â˜ƒ.reader.apply(â˜ƒ);
      this.usingSecondaryAction = â˜ƒ.readBoolean();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.entityId);
      â˜ƒ.writeEnum(this.action.getType());
      this.action.write(â˜ƒ);
      â˜ƒ.writeBoolean(this.usingSecondaryAction);
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleInteract(this);
   }

   @Nullable
   public Entity getTarget(ServerLevel var1) {
      return â˜ƒ.getEntityOrPart(this.entityId);
   }

   public boolean isUsingSecondaryAction() {
      return this.usingSecondaryAction;
   }

   public void dispatch(ServerboundInteractPacket.Handler var1) {
      this.action.dispatch(â˜ƒ);
   }

   interface Action {
      ServerboundInteractPacket.ActionType getType();

      void dispatch(ServerboundInteractPacket.Handler var1);

      void write(FriendlyByteBuf var1);
   }

   static enum ActionType {
      INTERACT(ServerboundInteractPacket.InteractionAction::new),
      ATTACK(var0 -> ServerboundInteractPacket.ATTACK_ACTION),
      INTERACT_AT(ServerboundInteractPacket.InteractionAtLocationAction::new);

      final Function<FriendlyByteBuf, ServerboundInteractPacket.Action> reader;

      private ActionType(Function<FriendlyByteBuf, ServerboundInteractPacket.Action> var3) {
         this.reader = â˜ƒ;
      }
   }

   public interface Handler {
      void onInteraction(InteractionHand var1);

      void onInteraction(InteractionHand var1, Vec3 var2);

      void onAttack();
   }

   static class InteractionAction implements ServerboundInteractPacket.Action {
      private final InteractionHand hand;

      InteractionAction(InteractionHand var1) {
         this.hand = â˜ƒ;
      }

      private InteractionAction(FriendlyByteBuf var1) {
         this.hand = â˜ƒ.readEnum(InteractionHand.class);
      }

      @Override
      public ServerboundInteractPacket.ActionType getType() {
         return ServerboundInteractPacket.ActionType.INTERACT;
      }

      @Override
      public void dispatch(ServerboundInteractPacket.Handler var1) {
         â˜ƒ.onInteraction(this.hand);
      }

      @Override
      public void write(FriendlyByteBuf var1) {
         â˜ƒ.writeEnum(this.hand);
      }
   }

   static class InteractionAtLocationAction implements ServerboundInteractPacket.Action {
      private final InteractionHand hand;
      private final Vec3 location;

      InteractionAtLocationAction(InteractionHand var1, Vec3 var2) {
         this.hand = â˜ƒ;
         this.location = â˜ƒ;
      }

      private InteractionAtLocationAction(FriendlyByteBuf var1) {
         this.location = new Vec3((double)â˜ƒ.readFloat(), (double)â˜ƒ.readFloat(), (double)â˜ƒ.readFloat());
         this.hand = â˜ƒ.readEnum(InteractionHand.class);
      }

      @Override
      public ServerboundInteractPacket.ActionType getType() {
         return ServerboundInteractPacket.ActionType.INTERACT_AT;
      }

      @Override
      public void dispatch(ServerboundInteractPacket.Handler var1) {
         â˜ƒ.onInteraction(this.hand, this.location);
      }

      @Override
      public void write(FriendlyByteBuf var1) {
         â˜ƒ.writeFloat((float)this.location.x);
         â˜ƒ.writeFloat((float)this.location.y);
         â˜ƒ.writeFloat((float)this.location.z);
         â˜ƒ.writeEnum(this.hand);
      }
   }
}
