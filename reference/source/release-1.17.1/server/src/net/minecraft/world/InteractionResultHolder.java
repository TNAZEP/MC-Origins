package net.minecraft.world;

public class InteractionResultHolder<T> {
   private final InteractionResult result;
   private final T object;

   public InteractionResultHolder(InteractionResult var1, T var2) {
      this.result = â˜ƒ;
      this.object = â˜ƒ;
   }

   public InteractionResult getResult() {
      return this.result;
   }

   public T getObject() {
      return this.object;
   }

   public static <T> InteractionResultHolder<T> success(T var0) {
      return new InteractionResultHolder<>(InteractionResult.SUCCESS, â˜ƒ);
   }

   public static <T> InteractionResultHolder<T> consume(T var0) {
      return new InteractionResultHolder<>(InteractionResult.CONSUME, â˜ƒ);
   }

   public static <T> InteractionResultHolder<T> pass(T var0) {
      return new InteractionResultHolder<>(InteractionResult.PASS, â˜ƒ);
   }

   public static <T> InteractionResultHolder<T> fail(T var0) {
      return new InteractionResultHolder<>(InteractionResult.FAIL, â˜ƒ);
   }

   public static <T> InteractionResultHolder<T> sidedSuccess(T var0, boolean var1) {
      return â˜ƒ ? success(â˜ƒ) : consume(â˜ƒ);
   }
}
