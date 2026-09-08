package net.minecraft.world.entity.ai.attributes;

import io.netty.util.internal.ThreadLocalRandom;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AttributeModifier {
   private static final Logger LOGGER = LogManager.getLogger();
   private final double amount;
   private final AttributeModifier.Operation operation;
   private final Supplier<String> nameGetter;
   private final UUID id;

   public AttributeModifier(String var1, double var2, AttributeModifier.Operation var4) {
      this(Mth.createInsecureUUID(ThreadLocalRandom.current()), (Supplier<String>)(() -> â˜ƒ), â˜ƒ, â˜ƒ);
   }

   public AttributeModifier(UUID var1, String var2, double var3, AttributeModifier.Operation var5) {
      this(â˜ƒ, (Supplier<String>)(() -> â˜ƒ), â˜ƒ, â˜ƒ);
   }

   public AttributeModifier(UUID var1, Supplier<String> var2, double var3, AttributeModifier.Operation var5) {
      this.id = â˜ƒ;
      this.nameGetter = â˜ƒ;
      this.amount = â˜ƒ;
      this.operation = â˜ƒ;
   }

   public UUID getId() {
      return this.id;
   }

   public String getName() {
      return (String)this.nameGetter.get();
   }

   public AttributeModifier.Operation getOperation() {
      return this.operation;
   }

   public double getAmount() {
      return this.amount;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (â˜ƒ != null && this.getClass() == â˜ƒ.getClass()) {
         AttributeModifier â˜ƒ = (AttributeModifier)â˜ƒ;
         return Objects.equals(this.id, â˜ƒ.id);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.id.hashCode();
   }

   public String toString() {
      return "AttributeModifier{amount="
         + this.amount
         + ", operation="
         + this.operation
         + ", name='"
         + (String)this.nameGetter.get()
         + "', id="
         + this.id
         + "}";
   }

   public CompoundTag save() {
      CompoundTag â˜ƒ = new CompoundTag();
      â˜ƒ.putString("Name", this.getName());
      â˜ƒ.putDouble("Amount", this.amount);
      â˜ƒ.putInt("Operation", this.operation.toValue());
      â˜ƒ.putUUID("UUID", this.id);
      return â˜ƒ;
   }

   @Nullable
   public static AttributeModifier load(CompoundTag var0) {
      try {
         UUID â˜ƒ = â˜ƒ.getUUID("UUID");
         AttributeModifier.Operation â˜ƒx = AttributeModifier.Operation.fromValue(â˜ƒ.getInt("Operation"));
         return new AttributeModifier(â˜ƒ, â˜ƒ.getString("Name"), â˜ƒ.getDouble("Amount"), â˜ƒx);
      } catch (Exception var3) {
         LOGGER.warn("Unable to create attribute: {}", var3.getMessage());
         return null;
      }
   }

   public static enum Operation {
      ADDITION(0),
      MULTIPLY_BASE(1),
      MULTIPLY_TOTAL(2);

      private static final AttributeModifier.Operation[] OPERATIONS = new AttributeModifier.Operation[]{ADDITION, MULTIPLY_BASE, MULTIPLY_TOTAL};
      private final int value;

      private Operation(int var3) {
         this.value = â˜ƒ;
      }

      public int toValue() {
         return this.value;
      }

      public static AttributeModifier.Operation fromValue(int var0) {
         if (â˜ƒ >= 0 && â˜ƒ < OPERATIONS.length) {
            return OPERATIONS[â˜ƒ];
         } else {
            throw new IllegalArgumentException("No operation with value " + â˜ƒ);
         }
      }
   }
}
