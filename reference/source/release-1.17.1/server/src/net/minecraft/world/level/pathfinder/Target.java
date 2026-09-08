package net.minecraft.world.level.pathfinder;

import net.minecraft.network.FriendlyByteBuf;

public class Target extends Node {
   private float bestHeuristic = Float.MAX_VALUE;
   private Node bestNode;
   private boolean reached;

   public Target(Node var1) {
      super(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z);
   }

   public Target(int var1, int var2, int var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void updateBest(float var1, Node var2) {
      if (â˜ƒ < this.bestHeuristic) {
         this.bestHeuristic = â˜ƒ;
         this.bestNode = â˜ƒ;
      }
   }

   public Node getBestNode() {
      return this.bestNode;
   }

   public void setReached() {
      this.reached = true;
   }

   public boolean isReached() {
      return this.reached;
   }

   public static Target createFromStream(FriendlyByteBuf var0) {
      Target â˜ƒ = new Target(â˜ƒ.readInt(), â˜ƒ.readInt(), â˜ƒ.readInt());
      â˜ƒ.walkedDistance = â˜ƒ.readFloat();
      â˜ƒ.costMalus = â˜ƒ.readFloat();
      â˜ƒ.closed = â˜ƒ.readBoolean();
      â˜ƒ.type = BlockPathTypes.values()[â˜ƒ.readInt()];
      â˜ƒ.f = â˜ƒ.readFloat();
      return â˜ƒ;
   }
}
