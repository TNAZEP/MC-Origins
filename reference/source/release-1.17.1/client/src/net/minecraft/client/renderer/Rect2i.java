package net.minecraft.client.renderer;

public class Rect2i {
   private int xPos;
   private int yPos;
   private int width;
   private int height;

   public Rect2i(int var1, int var2, int var3, int var4) {
      this.xPos = â˜ƒ;
      this.yPos = â˜ƒ;
      this.width = â˜ƒ;
      this.height = â˜ƒ;
   }

   public Rect2i intersect(Rect2i var1) {
      int â˜ƒ = this.xPos;
      int â˜ƒx = this.yPos;
      int â˜ƒxx = this.xPos + this.width;
      int â˜ƒxxx = this.yPos + this.height;
      int â˜ƒxxxx = â˜ƒ.getX();
      int â˜ƒxxxxx = â˜ƒ.getY();
      int â˜ƒxxxxxx = â˜ƒxxxx + â˜ƒ.getWidth();
      int â˜ƒxxxxxxx = â˜ƒxxxxx + â˜ƒ.getHeight();
      this.xPos = Math.max(â˜ƒ, â˜ƒxxxx);
      this.yPos = Math.max(â˜ƒx, â˜ƒxxxxx);
      this.width = Math.max(0, Math.min(â˜ƒxx, â˜ƒxxxxxx) - this.xPos);
      this.height = Math.max(0, Math.min(â˜ƒxxx, â˜ƒxxxxxxx) - this.yPos);
      return this;
   }

   public int getX() {
      return this.xPos;
   }

   public int getY() {
      return this.yPos;
   }

   public void setX(int var1) {
      this.xPos = â˜ƒ;
   }

   public void setY(int var1) {
      this.yPos = â˜ƒ;
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public void setWidth(int var1) {
      this.width = â˜ƒ;
   }

   public void setHeight(int var1) {
      this.height = â˜ƒ;
   }

   public void setPosition(int var1, int var2) {
      this.xPos = â˜ƒ;
      this.yPos = â˜ƒ;
   }

   public boolean contains(int var1, int var2) {
      return â˜ƒ >= this.xPos && â˜ƒ <= this.xPos + this.width && â˜ƒ >= this.yPos && â˜ƒ <= this.yPos + this.height;
   }
}
