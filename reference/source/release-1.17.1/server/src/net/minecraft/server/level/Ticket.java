package net.minecraft.server.level;

import java.util.Objects;

public final class Ticket<T> implements Comparable<Ticket<?>> {
   private final TicketType<T> type;
   private final int ticketLevel;
   private final T key;
   private long createdTick;

   protected Ticket(TicketType<T> var1, int var2, T var3) {
      this.type = â˜ƒ;
      this.ticketLevel = â˜ƒ;
      this.key = â˜ƒ;
   }

   public int compareTo(Ticket<?> var1) {
      int â˜ƒ = Integer.compare(this.ticketLevel, â˜ƒ.ticketLevel);
      if (â˜ƒ != 0) {
         return â˜ƒ;
      } else {
         int â˜ƒ = Integer.compare(System.identityHashCode(this.type), System.identityHashCode(â˜ƒ.type));
         return â˜ƒ != 0 ? â˜ƒ : this.type.getComparator().compare(this.key, â˜ƒ.key);
      }
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (!(â˜ƒ instanceof Ticket)) {
         return false;
      } else {
         Ticket<?> â˜ƒ = (Ticket)â˜ƒ;
         return this.ticketLevel == â˜ƒ.ticketLevel && Objects.equals(this.type, â˜ƒ.type) && Objects.equals(this.key, â˜ƒ.key);
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.type, this.ticketLevel, this.key});
   }

   public String toString() {
      return "Ticket[" + this.type + " " + this.ticketLevel + " (" + this.key + ")] at " + this.createdTick;
   }

   public TicketType<T> getType() {
      return this.type;
   }

   public int getTicketLevel() {
      return this.ticketLevel;
   }

   protected void setCreatedTick(long var1) {
      this.createdTick = â˜ƒ;
   }

   protected boolean timedOut(long var1) {
      long â˜ƒ = this.type.timeout();
      return â˜ƒ != 0L && â˜ƒ - this.createdTick > â˜ƒ;
   }
}
