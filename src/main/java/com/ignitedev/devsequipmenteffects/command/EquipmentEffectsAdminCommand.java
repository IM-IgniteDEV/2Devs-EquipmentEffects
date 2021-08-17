package com.ignitedev.devsequipmenteffects.command;

import com.ignitedev.devsequipmenteffects.EquipmentEffects;
import com.ignitedev.devsequipmenteffects.base.equipment.BaseEquipment;
import com.ignitedev.devsequipmenteffects.base.equipment.repository.BaseEquipmentRepository;
import com.ignitedev.devsequipmenteffects.configuration.BaseConfiguration;
import com.ignitedev.devsequipmenteffects.util.BaseUtil;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent.Builder;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.ClickEvent.Action;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class EquipmentEffectsAdminCommand implements CommandExecutor {

  private final BaseConfiguration baseConfiguration;
  private final BaseEquipmentRepository baseEquipmentRepository;
  private final EquipmentEffects equipmentEffects;

  @Override
  public boolean onCommand(@NotNull CommandSender sender,
      @NotNull Command command,
      @NotNull String label,
      @NotNull String[] args
  ) {

    if (!sender.hasPermission("EquipmentEffects.admin")) {
      sender.sendMessage(BaseUtil.fixColor(baseConfiguration.getNoPermissions()));
      return false;
    }

    Player senderPlayer = (Player) sender;

    if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
      String identifier = args[1];
      BaseEquipment baseEquipmentById = baseEquipmentRepository.findById(identifier);

      if (baseEquipmentById != null) {
        senderPlayer.getInventory().addItem(baseEquipmentById.getItemStack());
        return true;
      }
    } else if (args.length == 3 && args[0].equalsIgnoreCase("give")) {
      Player target = Bukkit.getPlayer(args[1]);

      if (target == null) {
        return false;
      }

      String identifier = args[2];
      BaseEquipment baseEquipmentById = baseEquipmentRepository.findById(identifier);

      if (baseEquipmentById != null) {
        target.getInventory().addItem(baseEquipmentById.getItemStack());
        return true;
      }
    } else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
      Builder builder = null;

      for (String identifier : baseEquipmentRepository.getBaseEquipmentCache().keySet()) {
        Builder content = Component.text()
            .clickEvent(ClickEvent.clickEvent(Action.SUGGEST_COMMAND, "/eea give " + identifier))
            .hoverEvent(
                HoverEvent.showText(
                    Component.text().content("CLICK ME").color(TextColor.color(255, 0, 0))))
            .content(BaseUtil.fixColor("&e" + identifier + " &7| "));

        if (builder == null) {
          builder = Component.text().content("Available: ").append(content);
        } else {
          builder.append(content);
        }
      }
      if (builder != null) {
        equipmentEffects.adventure.player(senderPlayer).sendMessage(builder.build());
        return true;
      }
      return true;
    } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
      equipmentEffects.reloadConfig();
      baseConfiguration.initialize(equipmentEffects.getConfig());
      sender.sendMessage(BaseUtil.fixColor(baseConfiguration.getReloadMessage()));
      return true;
    }

    sender.sendMessage(BaseUtil.fixColor(baseConfiguration.getAdminCommandUsage()));
    return false;
  }
}
