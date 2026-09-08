package net.minecraft.client.tutorial;

import java.util.function.Function;

public enum TutorialSteps {
   MOVEMENT("movement", MovementStep::new),
   FIND_TREE("find_tree", FindTreeStep::new),
   PUNCH_TREE("punch_tree", PunchTreeStep::new),
   OPEN_INVENTORY("open_inventory", OpenInventoryStep::new),
   CRAFT_PLANKS("craft_planks", CraftPlanksStep::new),
   NONE("none", CompletedTutorialStep::new);

   private final String field_193316_g;
   private final Function<Tutorial, ? extends ITutorialStep> field_193317_h;

   private <T extends ITutorialStep> TutorialSteps(String var3, Function<Tutorial, T> var4) {
      this.field_193316_g = ☃;
      this.field_193317_h = ☃;
   }

   public ITutorialStep func_193309_a(Tutorial var1) {
      return (ITutorialStep)this.field_193317_h.apply(☃);
   }

   public String func_193308_a() {
      return this.field_193316_g;
   }

   public static TutorialSteps func_193307_a(String var0) {
      for(TutorialSteps ☃ : values()) {
         if (☃.field_193316_g.equals(☃)) {
            return ☃;
         }
      }

      return NONE;
   }
}
