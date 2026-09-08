package net.minecraft.client.tutorial;

import java.util.function.Function;

public enum TutorialSteps {
   MOVEMENT("movement", MovementTutorialStepInstance::new),
   FIND_TREE("find_tree", FindTreeTutorialStepInstance::new),
   PUNCH_TREE("punch_tree", PunchTreeTutorialStepInstance::new),
   OPEN_INVENTORY("open_inventory", OpenInventoryTutorialStep::new),
   CRAFT_PLANKS("craft_planks", CraftPlanksTutorialStep::new),
   NONE("none", CompletedTutorialStepInstance::new);

   private final String name;
   private final Function<Tutorial, ? extends TutorialStepInstance> constructor;

   private <T extends TutorialStepInstance> TutorialSteps(String var3, Function<Tutorial, T> var4) {
      this.name = â˜ƒ;
      this.constructor = â˜ƒ;
   }

   public TutorialStepInstance create(Tutorial var1) {
      return (TutorialStepInstance)this.constructor.apply(â˜ƒ);
   }

   public String getName() {
      return this.name;
   }

   public static TutorialSteps getByName(String var0) {
      for(TutorialSteps â˜ƒ : values()) {
         if (â˜ƒ.name.equals(â˜ƒ)) {
            return â˜ƒ;
         }
      }

      return NONE;
   }
}
