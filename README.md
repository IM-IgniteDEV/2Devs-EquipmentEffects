# 2Devs Equipment Effects #
# Work in progress ;)

### Description ###

This is a plugin that allows you to create equipment or held items which gives you potion effects, permissions, particles or execute commands :)

### Todo List ###

- Giving player permission on effect
- Particles
- Executing commands
- NBT support
- WorldGuard Support
- One Item Per File(seperate files for each item)
- Support for projectiles

Note: Thanks to https://github.com/kawaii for all suggestions <3

### Commands ###

- /EquipmentEffectsAdmin reload - To reload Configuration
- /EquipmentEffectsAdmin list - To check list of created items
- /EquipmentEffectsAdmin give {PLAYER} {ITEM_ID} - Give Item to player 
- /EquipmentEffectsAdmin give {ITEM_ID} - Give Item

### Permissions ###

- /EquipmentEffectsAdmin - EquipmentEffects.admin

### Configuration ###

```yaml
# this is version of config, if it is different from plugin version that means that you have outdated config!
config-version: ${project.version}

# partition minimum players amount multiplier
# that's multiplier of {update-partitions-amount}, if you have this value set to 4, and you use
# default 2 multiplier, it will enable partitioning when you have at least 8 players, below this number
# it will update for all players at once
#
# 2 is minimum, otherwise it can throw an IllegalArgumentException
partition-minimum-players-multiplier: 2

# to how many partitions split player count
# ex: 105 players on server, if you specify {update-partitions-amount} as 4 it will split player inventory calculation
# for 4 parts; 25, 25, 25, 30, every part will be calculated every {task-schedule-time}, with default value of 20,
# it will take server 4 seconds to calculate all players inventory, and it will constantly repeat
update-partitions-amount: 4

# update player inventory(apply effects) task period, in ticks(20 ticks = 1 sec)
task-schedule-time: 20

effect-items:

  1:
    # id to identify item (for example in give command)
    id: "GLADIATOR_BOOTS"

    # name of item
    name: "Gladiator Boots"

    # [!] Information
    # options: must-wear | must-hold-mainhand | must-hold-offhand can be used together at the same time :)

    # if true, player have to wear that item to make it work, hands does not count
    must-wear: true

    # if both {must-hold-mainhand} and {must-hold-offhand} that makes it work for both hands

    # if true, player have to hold item in main hand to make it work
    must-hold-mainhand: false

    # if true, player have to hold item in off hand to make it work
    must-hold-offhand: false

    # effects which will be applied
    # format: EFFECT:AMPLIFIER
    # EFFECTS: (https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/potion/PotionEffectType.html)
    applicable-effects:
      - "SPEED:2"

    # itemstack for which effects will work(it don't have to be given by command, just similar itemstack
    # for more information about how this section work, go here: https://www.spigotmc.org/wiki/itemstack-serialization/
    itemstack:
      ==: org.bukkit.inventory.ItemStack
      v: 2230
      type: IRON_BOOTS
      meta:
        ==: ItemMeta
        meta-type: UNSPECIFIC
        display-name: "Gladiator Boots"
  2:
    id: "GLADIATOR_BOOTS2"

    name: "Gladiator Boots 2"

    must-wear: true

    must-hold-mainhand: false

    must-hold-offhand: false

    applicable-effects:
      - "SPEED:2"
      - "JUMP:2"
      - "GLOWING:1"

    itemstack:
      ==: org.bukkit.inventory.ItemStack
      v: 2230
      type: DIAMOND_BOOTS
      meta:
        ==: ItemMeta
        meta-type: UNSPECIFIC
        display-name: "Gladiator Boots"


messages:
  admin-command-usage: "Usage of admin command: /EEA give <id> | /EEA list | /EEA reload"
  reload: "Config got reloaded!"
  no-permissions: "No Permissions!"
```
