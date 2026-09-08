package net.minecraft.world.level.pathfinder;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.PathNavigationRegion;

public abstract class NodeEvaluator {
   protected PathNavigationRegion level;
   protected Mob mob;
   protected final Int2ObjectMap<Node> nodes = new Int2ObjectOpenHashMap<>();
   protected int entityWidth;
   protected int entityHeight;
   protected int entityDepth;
   protected boolean canPassDoors;
   protected boolean canOpenDoors;
   protected boolean canFloat;

   public void prepare(PathNavigationRegion var1, Mob var2) {
      this.level = â˜ƒ;
      this.mob = â˜ƒ;
      this.nodes.clear();
      this.entityWidth = Mth.floor(â˜ƒ.getBbWidth() + 1.0F);
      this.entityHeight = Mth.floor(â˜ƒ.getBbHeight() + 1.0F);
      this.entityDepth = Mth.floor(â˜ƒ.getBbWidth() + 1.0F);
   }

   public void done() {
      this.level = null;
      this.mob = null;
   }

   protected Node getNode(BlockPos var1) {
      return this.getNode(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
   }

   protected Node getNode(int var1, int var2, int var3) {
      return this.nodes.computeIfAbsent(Node.createHash(â˜ƒ, â˜ƒ, â˜ƒ), var3x -> new Node(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public abstract Node getStart();

   public abstract Target getGoal(double var1, double var3, double var5);

   public abstract int getNeighbors(Node[] var1, Node var2);

   public abstract BlockPathTypes getBlockPathType(
      BlockGetter var1, int var2, int var3, int var4, Mob var5, int var6, int var7, int var8, boolean var9, boolean var10
   );

   public abstract BlockPathTypes getBlockPathType(BlockGetter var1, int var2, int var3, int var4);

   public void setCanPassDoors(boolean var1) {
      this.canPassDoors = â˜ƒ;
   }

   public void setCanOpenDoors(boolean var1) {
      this.canOpenDoors = â˜ƒ;
   }

   public void setCanFloat(boolean var1) {
      this.canFloat = â˜ƒ;
   }

   public boolean canPassDoors() {
      return this.canPassDoors;
   }

   public boolean canOpenDoors() {
      return this.canOpenDoors;
   }

   public boolean canFloat() {
      return this.canFloat;
   }
}
