package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.properties.StructureMode;

public class ServerboundSetStructureBlockPacket implements Packet<ServerGamePacketListener> {
   private static final int FLAG_IGNORE_ENTITIES = 1;
   private static final int FLAG_SHOW_AIR = 2;
   private static final int FLAG_SHOW_BOUNDING_BOX = 4;
   private final BlockPos pos;
   private final StructureBlockEntity.UpdateType updateType;
   private final StructureMode mode;
   private final String name;
   private final BlockPos offset;
   private final Vec3i size;
   private final Mirror mirror;
   private final Rotation rotation;
   private final String data;
   private final boolean ignoreEntities;
   private final boolean showAir;
   private final boolean showBoundingBox;
   private final float integrity;
   private final long seed;

   public ServerboundSetStructureBlockPacket(
      BlockPos var1,
      StructureBlockEntity.UpdateType var2,
      StructureMode var3,
      String var4,
      BlockPos var5,
      Vec3i var6,
      Mirror var7,
      Rotation var8,
      String var9,
      boolean var10,
      boolean var11,
      boolean var12,
      float var13,
      long var14
   ) {
      this.pos = â˜ƒ;
      this.updateType = â˜ƒ;
      this.mode = â˜ƒ;
      this.name = â˜ƒ;
      this.offset = â˜ƒ;
      this.size = â˜ƒ;
      this.mirror = â˜ƒ;
      this.rotation = â˜ƒ;
      this.data = â˜ƒ;
      this.ignoreEntities = â˜ƒ;
      this.showAir = â˜ƒ;
      this.showBoundingBox = â˜ƒ;
      this.integrity = â˜ƒ;
      this.seed = â˜ƒ;
   }

   public ServerboundSetStructureBlockPacket(FriendlyByteBuf var1) {
      this.pos = â˜ƒ.readBlockPos();
      this.updateType = â˜ƒ.readEnum(StructureBlockEntity.UpdateType.class);
      this.mode = â˜ƒ.readEnum(StructureMode.class);
      this.name = â˜ƒ.readUtf();
      int â˜ƒ = 48;
      this.offset = new BlockPos(Mth.clamp(â˜ƒ.readByte(), -48, 48), Mth.clamp(â˜ƒ.readByte(), -48, 48), Mth.clamp(â˜ƒ.readByte(), -48, 48));
      int â˜ƒx = 48;
      this.size = new Vec3i(Mth.clamp(â˜ƒ.readByte(), 0, 48), Mth.clamp(â˜ƒ.readByte(), 0, 48), Mth.clamp(â˜ƒ.readByte(), 0, 48));
      this.mirror = â˜ƒ.readEnum(Mirror.class);
      this.rotation = â˜ƒ.readEnum(Rotation.class);
      this.data = â˜ƒ.readUtf(128);
      this.integrity = Mth.clamp(â˜ƒ.readFloat(), 0.0F, 1.0F);
      this.seed = â˜ƒ.readVarLong();
      int â˜ƒxx = â˜ƒ.readByte();
      this.ignoreEntities = (â˜ƒxx & 1) != 0;
      this.showAir = (â˜ƒxx & 2) != 0;
      this.showBoundingBox = (â˜ƒxx & 4) != 0;
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeBlockPos(this.pos);
      â˜ƒ.writeEnum(this.updateType);
      â˜ƒ.writeEnum(this.mode);
      â˜ƒ.writeUtf(this.name);
      â˜ƒ.writeByte(this.offset.getX());
      â˜ƒ.writeByte(this.offset.getY());
      â˜ƒ.writeByte(this.offset.getZ());
      â˜ƒ.writeByte(this.size.getX());
      â˜ƒ.writeByte(this.size.getY());
      â˜ƒ.writeByte(this.size.getZ());
      â˜ƒ.writeEnum(this.mirror);
      â˜ƒ.writeEnum(this.rotation);
      â˜ƒ.writeUtf(this.data);
      â˜ƒ.writeFloat(this.integrity);
      â˜ƒ.writeVarLong(this.seed);
      int â˜ƒ = 0;
      if (this.ignoreEntities) {
         â˜ƒ |= 1;
      }

      if (this.showAir) {
         â˜ƒ |= 2;
      }

      if (this.showBoundingBox) {
         â˜ƒ |= 4;
      }

      â˜ƒ.writeByte(â˜ƒ);
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleSetStructureBlock(this);
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public StructureBlockEntity.UpdateType getUpdateType() {
      return this.updateType;
   }

   public StructureMode getMode() {
      return this.mode;
   }

   public String getName() {
      return this.name;
   }

   public BlockPos getOffset() {
      return this.offset;
   }

   public Vec3i getSize() {
      return this.size;
   }

   public Mirror getMirror() {
      return this.mirror;
   }

   public Rotation getRotation() {
      return this.rotation;
   }

   public String getData() {
      return this.data;
   }

   public boolean isIgnoreEntities() {
      return this.ignoreEntities;
   }

   public boolean isShowAir() {
      return this.showAir;
   }

   public boolean isShowBoundingBox() {
      return this.showBoundingBox;
   }

   public float getIntegrity() {
      return this.integrity;
   }

   public long getSeed() {
      return this.seed;
   }
}
