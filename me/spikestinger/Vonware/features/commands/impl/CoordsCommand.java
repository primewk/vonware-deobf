package me.spikestinger.Vonware.features.commands.impl;

import me.spikestinger.Vonware.features.commands.Command;
import net.minecraft.class_1937;

public class CoordsCommand extends Command {
   public CoordsCommand() {
      super("coords", new String[]{"<playername>"});
   }

   public void sendCoordinatesMessage(String[] args) {
      if (mc.field_1724 != null && mc.field_1687 != null) {
         String dimensionName;
         if (mc.field_1687.method_27983() == class_1937.field_25179) {
            dimensionName = "Overworld";
         } else if (mc.field_1687.method_27983() == class_1937.field_25181) {
            dimensionName = "End";
         } else if (mc.field_1687.method_27983() == class_1937.field_25180) {
            dimensionName = "Nether";
         } else {
            dimensionName = "failed to detect dimension";
         }

         String message = "msg " + args[0] + " My coordinates are X: " + Math.round(mc.field_1724.method_23317()) + " Y: " + Math.round(mc.field_1724.method_23318()) + " Z: " + Math.round(mc.field_1724.method_23321()) + " in the " + dimensionName;
         mc.field_1724.field_3944.method_45730(message);
      }

   }

   public void execute(String[] args) {
      if (args.length == 1) {
         Command.sendMessage("Please enter a player name");
      }

      this.sendCoordinatesMessage(args);
   }
}
