package net.minecraft.world.level.pathfinder;

public class BinaryHeap {
   private Node[] heap = new Node[128];
   private int size;

   public Node insert(Node var1) {
      if (â˜ƒ.heapIdx >= 0) {
         throw new IllegalStateException("OW KNOWS!");
      } else {
         if (this.size == this.heap.length) {
            Node[] â˜ƒ = new Node[this.size << 1];
            System.arraycopy(this.heap, 0, â˜ƒ, 0, this.size);
            this.heap = â˜ƒ;
         }

         this.heap[this.size] = â˜ƒ;
         â˜ƒ.heapIdx = this.size;
         this.upHeap(this.size++);
         return â˜ƒ;
      }
   }

   public void clear() {
      this.size = 0;
   }

   public Node peek() {
      return this.heap[0];
   }

   public Node pop() {
      Node â˜ƒ = this.heap[0];
      this.heap[0] = this.heap[--this.size];
      this.heap[this.size] = null;
      if (this.size > 0) {
         this.downHeap(0);
      }

      â˜ƒ.heapIdx = -1;
      return â˜ƒ;
   }

   public void remove(Node var1) {
      this.heap[â˜ƒ.heapIdx] = this.heap[--this.size];
      this.heap[this.size] = null;
      if (this.size > â˜ƒ.heapIdx) {
         if (this.heap[â˜ƒ.heapIdx].f < â˜ƒ.f) {
            this.upHeap(â˜ƒ.heapIdx);
         } else {
            this.downHeap(â˜ƒ.heapIdx);
         }
      }

      â˜ƒ.heapIdx = -1;
   }

   public void changeCost(Node var1, float var2) {
      float â˜ƒ = â˜ƒ.f;
      â˜ƒ.f = â˜ƒ;
      if (â˜ƒ < â˜ƒ) {
         this.upHeap(â˜ƒ.heapIdx);
      } else {
         this.downHeap(â˜ƒ.heapIdx);
      }
   }

   public int size() {
      return this.size;
   }

   private void upHeap(int var1) {
      Node â˜ƒ = this.heap[â˜ƒ];

      int â˜ƒ;
      for(float â˜ƒx = â˜ƒ.f; â˜ƒ > 0; â˜ƒ = â˜ƒ) {
         â˜ƒ = â˜ƒ - 1 >> 1;
         Node â˜ƒxx = this.heap[â˜ƒ];
         if (!(â˜ƒx < â˜ƒxx.f)) {
            break;
         }

         this.heap[â˜ƒ] = â˜ƒxx;
         â˜ƒxx.heapIdx = â˜ƒ;
      }

      this.heap[â˜ƒ] = â˜ƒ;
      â˜ƒ.heapIdx = â˜ƒ;
   }

   private void downHeap(int var1) {
      Node â˜ƒ = this.heap[â˜ƒ];
      float â˜ƒx = â˜ƒ.f;

      while(true) {
         int â˜ƒxx = 1 + (â˜ƒ << 1);
         int â˜ƒxxx = â˜ƒxx + 1;
         if (â˜ƒxx >= this.size) {
            break;
         }

         Node â˜ƒxxxx = this.heap[â˜ƒxx];
         float â˜ƒxxxxx = â˜ƒxxxx.f;
         Node â˜ƒxx;
         float â˜ƒxxx;
         if (â˜ƒxxx >= this.size) {
            â˜ƒxx = null;
            â˜ƒxxx = Float.POSITIVE_INFINITY;
         } else {
            â˜ƒxx = this.heap[â˜ƒxxx];
            â˜ƒxxx = â˜ƒxx.f;
         }

         if (â˜ƒxxxxx < â˜ƒxxx) {
            if (!(â˜ƒxxxxx < â˜ƒx)) {
               break;
            }

            this.heap[â˜ƒ] = â˜ƒxxxx;
            â˜ƒxxxx.heapIdx = â˜ƒ;
            â˜ƒ = â˜ƒxx;
         } else {
            if (!(â˜ƒxxx < â˜ƒx)) {
               break;
            }

            this.heap[â˜ƒ] = â˜ƒxx;
            â˜ƒxx.heapIdx = â˜ƒ;
            â˜ƒ = â˜ƒxxx;
         }
      }

      this.heap[â˜ƒ] = â˜ƒ;
      â˜ƒ.heapIdx = â˜ƒ;
   }

   public boolean isEmpty() {
      return this.size == 0;
   }

   public Node[] getHeap() {
      Node[] â˜ƒ = new Node[this.size()];
      System.arraycopy(this.heap, 0, â˜ƒ, 0, this.size());
      return â˜ƒ;
   }
}
