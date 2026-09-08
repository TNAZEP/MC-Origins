package net.minecraft.world.level.pathfinder;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class Path {
   private final List<Node> nodes;
   private Node[] openSet = new Node[0];
   private Node[] closedSet = new Node[0];
   private Set<Target> targetNodes;
   private int nextNodeIndex;
   private final BlockPos target;
   private final float distToTarget;
   private final boolean reached;

   public Path(List<Node> var1, BlockPos var2, boolean var3) {
      this.nodes = â˜ƒ;
      this.target = â˜ƒ;
      this.distToTarget = â˜ƒ.isEmpty() ? Float.MAX_VALUE : ((Node)this.nodes.get(this.nodes.size() - 1)).distanceManhattan(this.target);
      this.reached = â˜ƒ;
   }

   public void advance() {
      ++this.nextNodeIndex;
   }

   public boolean notStarted() {
      return this.nextNodeIndex <= 0;
   }

   public boolean isDone() {
      return this.nextNodeIndex >= this.nodes.size();
   }

   @Nullable
   public Node getEndNode() {
      return !this.nodes.isEmpty() ? (Node)this.nodes.get(this.nodes.size() - 1) : null;
   }

   public Node getNode(int var1) {
      return (Node)this.nodes.get(â˜ƒ);
   }

   public void truncateNodes(int var1) {
      if (this.nodes.size() > â˜ƒ) {
         this.nodes.subList(â˜ƒ, this.nodes.size()).clear();
      }
   }

   public void replaceNode(int var1, Node var2) {
      this.nodes.set(â˜ƒ, â˜ƒ);
   }

   public int getNodeCount() {
      return this.nodes.size();
   }

   public int getNextNodeIndex() {
      return this.nextNodeIndex;
   }

   public void setNextNodeIndex(int var1) {
      this.nextNodeIndex = â˜ƒ;
   }

   public Vec3 getEntityPosAtNode(Entity var1, int var2) {
      Node â˜ƒ = (Node)this.nodes.get(â˜ƒ);
      double â˜ƒx = (double)â˜ƒ.x + (double)((int)(â˜ƒ.getBbWidth() + 1.0F)) * 0.5;
      double â˜ƒxx = (double)â˜ƒ.y;
      double â˜ƒxxx = (double)â˜ƒ.z + (double)((int)(â˜ƒ.getBbWidth() + 1.0F)) * 0.5;
      return new Vec3(â˜ƒx, â˜ƒxx, â˜ƒxxx);
   }

   public BlockPos getNodePos(int var1) {
      return ((Node)this.nodes.get(â˜ƒ)).asBlockPos();
   }

   public Vec3 getNextEntityPos(Entity var1) {
      return this.getEntityPosAtNode(â˜ƒ, this.nextNodeIndex);
   }

   public BlockPos getNextNodePos() {
      return ((Node)this.nodes.get(this.nextNodeIndex)).asBlockPos();
   }

   public Node getNextNode() {
      return (Node)this.nodes.get(this.nextNodeIndex);
   }

   @Nullable
   public Node getPreviousNode() {
      return this.nextNodeIndex > 0 ? (Node)this.nodes.get(this.nextNodeIndex - 1) : null;
   }

   public boolean sameAs(@Nullable Path var1) {
      if (â˜ƒ == null) {
         return false;
      } else if (â˜ƒ.nodes.size() != this.nodes.size()) {
         return false;
      } else {
         for(int â˜ƒ = 0; â˜ƒ < this.nodes.size(); ++â˜ƒ) {
            Node â˜ƒx = (Node)this.nodes.get(â˜ƒ);
            Node â˜ƒxx = (Node)â˜ƒ.nodes.get(â˜ƒ);
            if (â˜ƒx.x != â˜ƒxx.x || â˜ƒx.y != â˜ƒxx.y || â˜ƒx.z != â˜ƒxx.z) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean canReach() {
      return this.reached;
   }

   @VisibleForDebug
   void setDebug(Node[] var1, Node[] var2, Set<Target> var3) {
      this.openSet = â˜ƒ;
      this.closedSet = â˜ƒ;
      this.targetNodes = â˜ƒ;
   }

   @VisibleForDebug
   public Node[] getOpenSet() {
      return this.openSet;
   }

   @VisibleForDebug
   public Node[] getClosedSet() {
      return this.closedSet;
   }

   public void writeToStream(FriendlyByteBuf var1) {
      if (this.targetNodes != null && !this.targetNodes.isEmpty()) {
         â˜ƒ.writeBoolean(this.reached);
         â˜ƒ.writeInt(this.nextNodeIndex);
         â˜ƒ.writeInt(this.targetNodes.size());
         this.targetNodes.forEach(var1x -> var1x.writeToStream(â˜ƒ));
         â˜ƒ.writeInt(this.target.getX());
         â˜ƒ.writeInt(this.target.getY());
         â˜ƒ.writeInt(this.target.getZ());
         â˜ƒ.writeInt(this.nodes.size());

         for(Node â˜ƒ : this.nodes) {
            â˜ƒ.writeToStream(â˜ƒ);
         }

         â˜ƒ.writeInt(this.openSet.length);

         for(Node â˜ƒ : this.openSet) {
            â˜ƒ.writeToStream(â˜ƒ);
         }

         â˜ƒ.writeInt(this.closedSet.length);

         for(Node â˜ƒ : this.closedSet) {
            â˜ƒ.writeToStream(â˜ƒ);
         }
      }
   }

   public static Path createFromStream(FriendlyByteBuf var0) {
      boolean â˜ƒ = â˜ƒ.readBoolean();
      int â˜ƒx = â˜ƒ.readInt();
      int â˜ƒxx = â˜ƒ.readInt();
      Set<Target> â˜ƒxxx = Sets.<Target>newHashSet();

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒxx; ++â˜ƒxxxx) {
         â˜ƒxxx.add(Target.createFromStream(â˜ƒ));
      }

      BlockPos â˜ƒxxxx = new BlockPos(â˜ƒ.readInt(), â˜ƒ.readInt(), â˜ƒ.readInt());
      List<Node> â˜ƒxxxxx = Lists.<Node>newArrayList();
      int â˜ƒxxxxxx = â˜ƒ.readInt();

      for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < â˜ƒxxxxxx; ++â˜ƒxxxxxxx) {
         â˜ƒxxxxx.add(Node.createFromStream(â˜ƒ));
      }

      Node[] â˜ƒxxxxxxx = new Node[â˜ƒ.readInt()];

      for(int â˜ƒxxxxxxxx = 0; â˜ƒxxxxxxxx < â˜ƒxxxxxxx.length; ++â˜ƒxxxxxxxx) {
         â˜ƒxxxxxxx[â˜ƒxxxxxxxx] = Node.createFromStream(â˜ƒ);
      }

      Node[] â˜ƒxxxxxxxx = new Node[â˜ƒ.readInt()];

      for(int â˜ƒxxxxxxxxx = 0; â˜ƒxxxxxxxxx < â˜ƒxxxxxxxx.length; ++â˜ƒxxxxxxxxx) {
         â˜ƒxxxxxxxx[â˜ƒxxxxxxxxx] = Node.createFromStream(â˜ƒ);
      }

      Path â˜ƒxxxxxxxxx = new Path(â˜ƒxxxxx, â˜ƒxxxx, â˜ƒ);
      â˜ƒxxxxxxxxx.openSet = â˜ƒxxxxxxx;
      â˜ƒxxxxxxxxx.closedSet = â˜ƒxxxxxxxx;
      â˜ƒxxxxxxxxx.targetNodes = â˜ƒxxx;
      â˜ƒxxxxxxxxx.nextNodeIndex = â˜ƒx;
      return â˜ƒxxxxxxxxx;
   }

   public String toString() {
      return "Path(length=" + this.nodes.size() + ")";
   }

   public BlockPos getTarget() {
      return this.target;
   }

   public float getDistToTarget() {
      return this.distToTarget;
   }
}
