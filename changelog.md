# Changelog

## 0.1.12

### Updates
- Updated to Farmer's Delight 1.3;
- There is now some chance to obtain gold and diamonds while scavenging enhanced and ultimate hunter armor of swiftness respectively;
- Added FD new tags support: `farmersdelight:snacks`, `farmersdelight:meals`, `farmersdelight:sweets`, `farmersdelight:drinks`, `farmersdelight:feasts`, `c:foods/soup`, `c:foods/pie`;

### Fixes
- The top texture of the weird jelly leftover is now aligned with other stages;

### Translations
- Renamed some advancements;

## 0.1.11b

### Additions
- Added a new config:
  - `enableFDFoodEffectsDespiteFaction`:
  - `false` by default;

## 0.1.11

### Fixes
- Fixed **Botany Pots** mod integration (Thanks, HidekiHrk!);
- Fixed infinite wine and beer when pouring using a crafting table;

## 0.1.10d

### Translations
- Added:
  - zh_Cn (Thanks, chairs!);

## 0.1.10c

### Updates
- Changed food effects:
  - **Blood Wine Glass**: changed `Regeneration II for 10 seconds` to `Regeneration for 15 seconds`;
  - **Orchid Tea**: changed `Regeneration for 20 seconds` to `10 seconds`;
  - **Mulled Wine Glass**: replaced `Regeneration` with `Haste for  3 minutes`;
  - **Orchid Ice Cream**: replaced `Resistance` with `Vampire Fire Protection II for 1 minute`;
  - **Dandelion Beer Mug**: replaced `Strength` with `Haste for 3 minutes` and added half a point of saturation;
  - **Borscht**: replaced `Resistance` with `Absorption for 1 minute 30 seconds`;
  - **Wolf Berry Ice Cream**: replaced `Resistance` with `Haste for 2 minutes`;
- Cooking spot at hunter outpost generation has been fixed, and it now finally spawns as intended. The structure itself has also been updated, and it is now sometimes generated instead of the alchemy building and not the stables;
- A few new tents storing crops are now generated at hunter outposts as a variant of existing tents;

### Fixes
- Fixed the weathered letter creative tab generation conflicting because of referring to client on server;

## 0.1.10b

### Fixes
- Fixed the weathered letter packet causing the server fail to start;

## 0.1.10

### Additions
- New item added:
  - **Weathered Letter**. It can sometimes be found in hunter outposts and can either be read or used to craft paper or vampire books when a few are combined. There are two different letters as of now;
- Added a new building to hunter outposts - a canteen/kitchen that sometimes spawns at hunter outposts instead of stables;
- **Rice** and **Cabbage** are now also generated in chests of tents and towers at hunter outposts;
- New advancement added:
  - `Centennial fanfics` - Find a Weathered Letter;

### Updates
- Updated textures:
  - **Blood Wine Bottle**;
  - **Dandelion Beer Bottle**;
  - **Blood Syrup**;
  - **Blood Hot Dog**;

### Fixes
- Placing this mod's cabinets no longer crashes the game;

### Translations
- Added new lines for weathered letters;
- Corrected:
  - en_Us;

## 0.1.9c

### Fixes
- Fixed a bug that crashed the game when bat-related food effect tooltips were displayed and `batMeatWithersHumans` was set to `false`;

## 0.1.9b

### Fixes
- Fixed a critical bug that allowed vampire players to eat everything, even without custom data components;

## 0.1.9

### Additions
- Non-human food values in the food are now stored in data components as well, and they can be modified using data packs or commands. However, it may cause various bugs. If you find any please report them. These new components are:
  - `vampiresdelight:vampire_food`;
  - `vampiresdelight:hunter_food`;
  - `vampiresdelight:werewolf_food`;

### Updates
- **Hardtack** can now be eaten faster by hunters; 
- **Orchid Tea** no longer has a colored outline and a faction tooltip for humans who can be infected. For everyone else, it's displayed as before;

### Fixes
- Fixed **Werewolves** mod integration;

### Configurations
- `tooltip_colors` category is now inside of `tooltips` category;
- Added new configs:
  - `overrideFoodPropertiesForFactions`:
    - `true` by default;
  - `spiritLanternFogDistanceMultiplier`:
    - `0.4` by default;
    - Range: from `0.0` to `10.0`;
  - `coloredTooltipsForVampirismItems`:
    - `true` by default;
  - `factionTooltips`:
    - `true` by default;
  - `factionTooltipsForVampirismItems`:
    - `true` by default;
  - `debugFoodTooltips`:
    - `false` by default;

### Tags
- Renamed tags:
  - `vampiresdelight:drop_human_eye` into `vampiresdelight:drops_human_eye`;
  - `vampiresdelight:wine_shelf` into `vampiresdelight:wine_shelves`;
- Removed `vampiresdelight:blood_food` tag for technical reasons since it's no longer necessary;

### Translations
- Added translations to all mod's tags;

## 0.1.8

### Additions
- Updated to NeoForge;
- **Bloody Soil** can now be obtained by clicking on the **Rich Soil** or the **Rich Soil Farmland** with a **Pure Blood Bottle**;
- Added `vampiresdelight:splash_radius` data component which can modify the splash radius of an **Alchemical Cocktail**. It can be used in data packs or with commands;

### Updates
- Most of the normal plants can now be planted in the **Cursed Farmland** and the **Bloody Soil Farmland**, except for the **Garlic**;
- **Bloody Soil** now only boost cursed plants such as the **Vampire Orchid** and the **Dark Spruce Sapling**;
- **Vampire Orchid Crop** no longer needs darkness, it will now grow in any light, but planting it in the dark or in fog will increase its growth speed;
- Attacking an entity with **Alchemical Cocktail** will now cause it to break, setting everything on fire as if you threw it;
- **Alchemical Cocktail** now has a handheld model, like a sword or a pickaxe;
- **Spirit Lantern**'s model is no longer 3D in hand;
- **Weird Jelly** no longer breaks instantly;
- Updated **Grilled (Roasted) Garlic** texture;

### Fixes
- **Blood Pie** and **Orchid Cake** now drop slices after being broken with a knife as intended;

### Configurations
- Added brand-new config screen that can be found in mod menu;
- Set the default value of `cookingPotInHunterCampSpawnChance` to `40`;
- `hunterTooltipsForEveryone` now changes the color of tooltips as well;
- Tooltip border colors are now stored in hex value in configuration;
- Renamed `replaceWeirdJellySunscreenWithJumpboost` to `replaceWeirdJellySunscreenWithJumpBoost` for accuracy;
- Removed configs since now their functions can be configured using data packs:
  - `disableVampireBite`;
  - `backstabbingCanBeAppliedToHunterWeapon`;

### Translations
- Renamed **Grilled Garlic** to **Roasted Garlic**;
- Renamed advancement **Local Brewery** to **He hasn't brewed it since...**, since there's already a vanilla advancement with the same name;
- Added more subtitles for sounds;
- Added missing translation for thrown **Alchemical Cocktail**;
- Corrected:
  - en_Us;
  - uk_Ua;
  - ru_Ru;

## 0.1.7b

### Updates
- Tooltips modified and added:
  - **Spirit Lantern**'s tooltip is now hidden to save space on screen. Hold shift to view it;
  - **Dandelion Beer Bottle** and **Blood Wine Bottle** now have a tooltip explaining how to pour drinks. Hold shift to view it;
- **Alchemical Cocktail** can now be used by non-hunters;

### Fixes
- Fixed a bug which made gui tooltips be highlighted with orange;

## 0.1.7

### Additions
- New food items added:
  - **Dandelion Beer Bottle**;
  - **Dandelion Beer Mug**;
  - **Black Mushroom Noodles**;
- New blocks added:
  - **Bloody Soil**;
  - **Bloody Soil Farmland**;
- **Blood Wine Bottle** (and Dandelion Beer Bottle) can now be placed on the ground;
  - Click while crouching to place it;
  - Can be picked up by right-clicking while crouching;
  - Shatters when hit with a projectile (Arrows, Snow Balls, etc.);
- New advancements added:
  - `I knew you fharkin' elves had no honour! No respect! No BEER!` - Pour dandelion beer into glass bottle;
  - `Local Brewery` - Place Dandelion Beer Bottle on a Wine Shelf;
- New configs added:
  - `bloodySoilBoostChance`:
    - `0.2` by default;
    - Range: from `0.0` to `1.0`;

- ### Updates
- Updated textures and models:
  - **Blood Wine Bottle**;
  - **Blood Wine Glass**;
  - **Daisy Tea**;
- Changed food values:
  - **Snow_White Ice Cream**: `4` to `3,5`;
- **Vampire Bite** enchantment now heals at least half a heart on a successful hit;

### Translations
- **New lines added**;
- Renamed **Wine Glass** to **Blood Wine Glass**;
- Fixed:
  - uk_Ua;
  - ru_Ru;

## 0.1.6b

### Fixes
- Fixed game crushing on load because of incorrect mixin plugin setup;

## 0.1.6

### Additions
- Added new food:
  - **Daisy Tea**;
- Added **Botany Pots** compatibility tags:
  - **Vampire Orchid (flower and crop), Cursed Roots, Wild Garlic, and Black Mushroom** can now be grown in pots;
- Added new configs:
  - `hideAppleSkinHumanFoodTooltipsForWerewolves`:
    - `true` by default;

### Updates
- **Blood Wine Bottle** now has a tooltip displaying the amount of servings it contains;
- Made ice cream items also clear fire for other factions' members;
- **Vampire Books** can now be put in **Chiseled Bookshelf**;
- **Vampire Bite** enchantment books now spawn more often in vampire dungeons;
- The chance of **Cooking Pot** spawning at hunter camps is now higher;
- AppleSkin non-meat food tooltips are now hidden for werewolves without "Not Meat" skill;
- Added faction food tooltip to **Wolf Berries**;

### Fixes
- Werewolves without "Not Meat" skill and vampires no longer can drink human drinks like Melon Juice and Hot Cocoa (they still can drink milk bottle) (Thanks, Cheaterpaul!);
- Fixed being able to pour wine from **Blood Wine Bottle** into more than 3 glasses;
- "Disgusting" advancement is now also given after eating werewolf food while being non-werewolf;

### Translations
- Added:
  - fr_Fr (Thanks, Cursed Shadow!);
  - fr_Ca (Thanks, Cursed Shadow!);

## 0.1.5b

### Updates
- **Alchemical Cocktail**'s splash now has a spherical form;
- **Garlic Bread** can now be eaten by humans;

### Fixes
- Fixed vampire food item not giving effects and leftover item (bowl/glass bottle);

## 0.1.5

### Additions
- Added new food:
  - **Orchid Cream Soup**;
- Added **Werewolves** integration items (more to come later!):
  - **Silver Knife**;
  - **Wolf Berry Cookie**;
  - **Wolf Berry Ice Cream**;
  - **Jacaranda Cabinet and Wine Shelf**;
  - **Magic Cabinet and Wine Shelf**;
- Added new configs:
  - `generateCookingPotNearHunterCamp`:
    - `false` by default;
  - `alchemicalCocktailSplashRadius`:
    - `3` by default;
    - Range: from `1` to `99`;
  - `alchemicalCocktailStackSize`:
    - `8` by default;
    - Range: from `1` to `64`;
- Added new configs for configuring faction food tooltip colors:
  - `vampireFoodTooltipStartColor`:
    - `124, 40, 124` by default;
  - `vampireFoodTooltipEndColor`:
    - `50, 0, 70` by default;
  - `hunterFoodTooltipStartColor`:
    - `65, 65, 220` by default;
  - `hunterFoodTooltipEndColor`:
    - `30, 30, 90` by default;
  - `werewolfFoodTooltipStartColor`:
    - `250, 135, 0` by default;
  - `werewolfFoodTooltipEndColor`:
    - `115, 45, 0` by default;
- Added new commands (mostly for testing purposes):
  - `vampires-delight hungerBar`:
    - `fill` - fills the food bar fully;
    - `empty` - empties the hunger bar;
    - `set` - fills the hunger bar by the entered value;
- Added an item tag for data packs:
  - `vampiresdelight:blood_food` - makes food restore blood bar for vampires. The value is the same as human food;

### Updates
- Renamed **Clothes Dissolving** effect to just **Dissolving** to avoid unnecessary questions;
- Tweaked config file appearance;
- **Pure Sorbet**, **Orchid Ice Cream**, **Dark Ice Cream**, and **Snow-White Ice Cream** now clear fire from the consumer and can always be eaten;
- **Cursed Cupcake** no longer heals player, but gives regeneration effect;
- Changed **Alchemical Cocktail**'s splash form to be more organic;
- Modified food values:
  - **Blood Syrup**: `2,5` to `4,5` for vampires;
  - **Pure Sorbet**: `5` to `4`;
  - **Weird Jelly**: effect duration: `30` to `60` seconds;
  - **Snow_White Ice Cream**: `3,5` to `4`;
  - **Orchid Ice Cream** and **Borscht**: changed `absorption` effect to `resistance`;

### Fixes
- Fixed food not nourishing humans;
- Fixed not being able to eat **Orchid Cake** with candle;
- Fixed **Black Mushroom Soup** not giving bowl after eaten;
- Fixed non-vampire player eating two vampire food items at a time;
- Fixed non-vampire player being able to fill blood bottles after eating vampire food;
- Fixed showing **Nourishment** effect blood bar overlay to humans;
- Fixed **Alchemical Cocktail** not working on servers;

### Translations
- Corrected:
  - ru_Ru;

## 0.1.4

### Updates
- **Lost Carriages** no longer have grass floor;

### Fixes
- Game no longer crashes because of oil function mixin and hunter camp structure mixin while loading;

## 0.1.3

### Additions
- Added new configs:
  - `vampireBiteMaxHealingValue`:
    - `1.5` by default;
  - `disableVampireBite`:
    - `false` by default;
  - `generateCookingPotInHunterCamp`:
    - `true` by default;
  - `cookingPotInHunterCampSpawnChance`:
    - `25` by default;
    - Range: from `0` to `100`;
  - `correctAppleSkinTooltips`:
    - `true` by default;
  - `hideAppleSkinHumanFoodTooltipsForVampires`:
    - `true` by default;
- Added **Bar Stools**;
- Added advancements (*WIP*);

### Updates
- Removed **Alchemical Fire** from vanilla combat creative tab;
- Added JEI ingredient information for **Black Mushroom**;
- **Black Stone Stove** is now considered as campfire cooking recipe catalyst in JEI;
- **Cursed Farmland** now can get moistened;
- **Black Mushroom** blocks are now a bit darker;
- The shape of the **Huge Black Mushroom** is now different from the Huge Red Mushroom's one;
- **Cooking Pot** on a Campfire now spawns in **Hunter Camps**;
- Added **Apple Skin** compatibility (*WIP*);
- **Hardtack** no longer gives Saturation effect, but restores more hunger for hunters;
- Hunter npcs and players wearing hunter armor boots are now able to walk on **Powder Snow**;

### Fixes
- Clothes Dissolving oil no longer spawns at hunter outpost alchemy chests;
- Food made of **Bat** is no longer marked as vampire faction only;

### Translations
- Added:
  - uk_Ua;
  - en_Gb;
- Corrected:
  - ru_Ru;

## 0.1.2

### Additions
- Added new configs:
  - `alchemicalCocktailBurnsGround`:
    - `true` by default;
  - `backstabbingCanBeAppliedToHunterWeapon`:
    - `true` by default;
  - `vampireBiteChanceLevel1`:
    - `20` by default;
    - Range: from `0` to `100`;
  - `vampireBiteChanceLevel2`:
    - `25` by default;
    - Range: from `0` to `100`;
  - `vampireBiteChanceLevel3`:
    - `30` by default;
    - Range: from `0` to `100`;
- Added new cutting recipes:
  - Stripping Dark and Cursed Spruce Logs;
  - Salvaging Dark and Cursed Spruce furniture;
  - Breaking Dark Stone into Cobbled Dark Stone;
  - Cutting Armor of Swiftness into leather;

### Updates
- **Wild Garlic** now gives Garlic effect to vampires when inside;
- Tweaked **Black Mushroom** to spawn more frequently;
- **Vampire Orchid Crop** now grows only in dark areas with maximum light level of 12 or in vampire fog;
- The damage **Clothes Dissolving** effect deals to armor can now be reduced by Unbreaking enchantment;
- **Clothes Dissolving** potion is now creative only, and can't be crafted;
- Wandering traders now sell **Black Mushrooms**;
- Recalculated **Vampire Bite** enchantment's healing values. Level 1 now has 20% to healing, level 2 has 25%, and level 3 has 30% (can be configured). Maximum healing value is now 2 hearts;

### Fixes
- Fixed Hardtack giving bowl after being eaten;

### Translations
- Added:
  - ru_Ru (Thanks, DimensionPainter!);

## 0.1.1

### Additions
- Added **Orchid Curry**;
- Added **Bat Taco**;
- Added **Black Mushroom**:
  - Spawns in vampire forest;
  - Can grow huge if bonemealed;
- Added **Black Mushroom Soup**;
- Added new configs:
    - `replaceWeirdJellySunscreenWithJumpboost`. `false` by default (Thanks, Zin!);
    - `blessingHelpsAgainstGhosts`. `true` by default;
    - `batMeatWithersHumans`. `true` by default;

### Updates
- Tweaked food values for some foods;

### Fixes
- Fixed decoding exception with not encoded brewing barrel recipe tab which resulted in the servers not working (Thanks, Cheaterpaul!);
- Fixed config indentation (Thanks, Zin!);
- Fixed Farmer's Bliss incompatibility;

## 0.1.0

### Additions
- Added **Dark Stone Stove**;
- Added **Blood Wine**;
- Added **Wine Shelves**;
- Added **Wine Glass**;
- Added **Tricolor Dango**;
- Added **Sugared Berries**;
- Added several new effects and hunter potions for them;
- And more;

### Updates
- Several texture updates;
- **Farmer Villagers** now buy **Garlic**;
- **Wandering Traders** now sell **Orchid Seeds**, **Orchid Petals**, **Garlic**, and **Cursed Earth**;
- Vampire food tooltip now shows custom fraction tooltips;
- All vampire food recipes now require blood syrup instead of blood bottles;
- Nourishment hunger overlay now works for blood bar as well;
- Rebalanced food values for all food;
- And more;

### Fixes
- Fix vampire bite crashing game;

## 0.0.1.2

### Additions
- **Vampire Orchid** is now growable;
- Added **Cursed Farmland** to grow **Vampire Orchid**;

### Updates
- Cutting **Vampire Orchid** now drops **Orchid Seeds**;
- Changed **Blood Dough** recipe to use **Rice** instead of **Wheat**;

### Fixes
- **Orchid Bad** now drops correctly;

## 0.0.1.1

### Additions
- Added recipe for **Alchemical Cocktail**
- Hunters, Villagers, and Wandering traders now drop **Human Eyes** when killed with a knife;

### Updates
- Changed **Alchemical Cocktail**'s physics to act like vanilla potions;

### Fixes
- Fixed a bug that crashes game while trying to eat vampire food;