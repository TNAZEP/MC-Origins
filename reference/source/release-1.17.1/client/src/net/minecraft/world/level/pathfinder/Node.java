package net.minecraft.world.level.pathfinder;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class Node {
   public final int x;
   public final int y;
   public final int z;
   private final int hash;
   public int heapIdx = -1;
   public float g;
   public float h;
   public float f;
   public Node cameFrom;
   public boolean closed;
   public float walkedDistance;
   public float costMalus;
   public BlockPathTypes type = BlockPathTypes.BLOCKED;

   public Node(int var1, int var2, int var3) {
      this.x = â˜ƒ;
      this.y = â˜ƒ;
      this.z = â˜ƒ;
      this.hash = createHash(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public Node cloneAndMove(int var1, int var2, int var3) {
      Node â˜ƒ = new Node(â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.heapIdx = this.heapIdx;
      â˜ƒ.g = this.g;
      â˜ƒ.h = this.h;
      â˜ƒ.f = this.f;
      â˜ƒ.cameFrom = this.cameFrom;
      â˜ƒ.closed = this.closed;
      â˜ƒ.walkedDistance = this.walkedDistance;
      â˜ƒ.costMalus = this.costMalus;
      â˜ƒ.type = this.type;
      return â˜ƒ;
   }

   public static int createHash(int var0, int var1, int var2) {
      return â˜ƒ & 0xFF | (â˜ƒ & 32767) << 8 | (â˜ƒ & 32767) << 24 | (â˜ƒ < 0 ? Integer.MIN_VALUE : 0) | (â˜ƒ < 0 ? 32768 : 0);
   }

   public float distanceTo(Node var1) {
      float â˜ƒ = (float)(â˜ƒ.x - this.x);
      float â˜ƒx = (float)(â˜ƒ.y - this.y);
      float â˜ƒxx = (float)(â˜ƒ.z - this.z);
      return Mth.sqrt(â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx);
   }

   public float distanceTo(BlockPos var1) {
      float â˜ƒ = (float)(â˜ƒ.getX() - this.x);
      float â˜ƒx = (float)(â˜ƒ.getY() - this.y);
      float â˜ƒxx = (float)(â˜ƒ.getZ() - this.z);
      return Mth.sqrt(â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx);
   }

   public float distanceToSqr(Node var1) {
      float â˜ƒ = (float)(â˜ƒ.x - this.x);
      float â˜ƒx = (float)(â˜ƒ.y - this.y);
      float â˜ƒxx = (float)(â˜ƒ.z - this.z);
      return â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx;
   }

   public float distanceToSqr(BlockPos var1) {
      float â˜ƒ = (float)(â˜ƒ.getX() - this.x);
      float â˜ƒx = (float)(â˜ƒ.getY() - this.y);
      float â˜ƒxx = (float)(â˜ƒ.getZ() - this.z);
      return â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx;
   }

   public float distanceManhattan(Node var1) {
      float â˜ƒ = (float)Math.abs(â˜ƒ.x - this.x);
      float â˜ƒx = (float)Math.abs(â˜ƒ.y - this.y);
      float â˜ƒxx = (float)Math.abs(â˜ƒ.z - this.z);
      return â˜ƒ + â˜ƒx + â˜ƒxx;
   }

   public float distanceManhattan(BlockPos var1) {
      float â˜ƒ = (float)Math.abs(â˜ƒ.getX() - this.x);
      float â˜ƒx = (float)Math.abs(â˜ƒ.getY() - this.y);
      float â˜ƒxx = (float)Math.abs(â˜ƒ.getZ() - this.z);
      return â˜ƒ + â˜ƒx + â˜ƒxx;
   }

   public BlockPos asBlockPos() {
      return new BlockPos(this.x, this.y, this.z);
   }

   public Vec3 asVec3() {
      return new Vec3((double)this.x, (double)this.y, (double)this.z);
   }

   public boolean equals(Object var1) {
      if (!(â˜ƒ instanceof Node)) {
         return false;
      } else {
         Node â˜ƒ = (Node)â˜ƒ;
         return this.hash == â˜ƒ.hash && this.x == â˜ƒ.x && this.y == â˜ƒ.y && this.z == â˜ƒ.z;
      }
   }

   public int hashCode() {
      return this.hash;
   }

   public boolean inOpenSet() {
      return this.heapIdx >= 0;
   }

   public String toString() {
      return "Node{x=" + this.x + ", y=" + this.y + ", z=" + this.z + "}";
   }

   public void writeToStream(FriendlyByteBuf var1) {
      â˜ƒ.writeInt(this.x);
      â˜ƒ.writeInt(this.y);
      â˜ƒ.writeInt(this.z);
      â˜ƒ.writeFloat(this.walkedDistance);
      â˜ƒ.writeFloat(this.costMalus);
      â˜ƒ.writeBoolean(this.closed);
      â˜ƒ.writeInt(this.type.ordinal());
      â˜ƒ.writeFloat(this.f);
   }

   public static Node createFromStream(FriendlyByteBuf var0) {
      Node â˜ƒ = new Node(â˜ƒ.readInt(), â˜ƒ.readInt(), â˜ƒ.readInt());
      â˜ƒ.walkedDistance = â˜ƒ.readFloat();
      â˜ƒ.costMalus = â˜ƒ.readFloat();
      â˜ƒ.closed = â˜ƒ.readBoolean();
      â˜ƒ.type = BlockPathTypes.values()[â˜ƒ.readInt()];
      â˜ƒ.f = â˜ƒ.readFloat();
      return â˜ƒ;
   }
}
