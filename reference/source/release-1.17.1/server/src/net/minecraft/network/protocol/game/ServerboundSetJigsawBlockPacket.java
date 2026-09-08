package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;

public class ServerboundSetJigsawBlockPacket implements Packet<ServerGamePacketListener> {
   private final BlockPos pos;
   private final ResourceLocation name;
   private final ResourceLocation target;
   private final ResourceLocation pool;
   private final String finalState;
   private final JigsawBlockEntity.JointType joint;

   public ServerboundSetJigsawBlockPacket(
      BlockPos var1, ResourceLocation var2, ResourceLocation var3, ResourceLocation var4, String var5, JigsawBlockEntity.JointType var6
   ) {
      this.pos = â˜ƒ;
      this.name = â˜ƒ;
      this.target = â˜ƒ;
      this.pool = â˜ƒ;
      this.finalState = â˜ƒ;
      this.joint = â˜ƒ;
   }

   public ServerboundSetJigsawBlockPacket(FriendlyByteBuf var1) {
      this.pos = â˜ƒ.readBlockPos();
      this.name = â˜ƒ.readResourceLocation();
      this.target = â˜ƒ.readResourceLocation();
      this.pool = â˜ƒ.readResourceLocation();
      this.finalState = â˜ƒ.readUtf();
      this.joint = (JigsawBlockEntity.JointType)JigsawBlockEntity.JointType.byName(â˜ƒ.readUtf()).orElse(JigsawBlockEntity.JointType.ALIGNED);
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeBlockPos(this.pos);
      â˜ƒ.writeResourceLocation(this.name);
      â˜ƒ.writeResourceLocation(this.target);
      â˜ƒ.writeResourceLocation(this.pool);
      â˜ƒ.writeUtf(this.finalState);
      â˜ƒ.writeUtf(this.joint.getSerializedName());
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleSetJigsawBlock(this);
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public ResourceLocation getName() {
      return this.name;
   }

   public ResourceLocation getTarget() {
      return this.target;
   }

   public ResourceLocation getPool() {
      return this.pool;
   }

   public String getFinalState() {
      return this.finalState;
   }

   public JigsawBlockEntity.JointType getJoint() {
      return this.joint;
   }
}
