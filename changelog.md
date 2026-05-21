# Changelog

## 0.1.13b

### Changed
- Item tooltips are now displayed correctly;
- Updated the zh_ch translation (Thanks, Chairs!);

## 0.1.13

> Note: This version is not a direct continuation of the previous 1.20.1 release — it was rewritten from scratch, drawing on content from 1.21.1 while keeping its own differences and improvements. This entry lists only the changes relative to the old 1.20.1 version.

### Changed

**Vampire Orchid**
- The crop now has 6 growth stages instead of 3, starting with a new small sprout stage. It no longer grows into a full Vampire Orchid block — instead, the final stage drops orchid petals and seeds when broken;
- Cutting the Vampire Orchid flower now gives very few petals, the main result is seeds now. This should encourage growing the crop properly and make automation simpler;
- Growth logic is now clearer: the crop grows 35% faster in vampire fog, 20% faster out of direct sunlight, and 40% slower under direct sunlight. Otherwise, it behaves like any normal crop;
- The crop now has a tooltip explaining how it grows;

**Farming**
- Cursed Farmland now supports all normal crops in addition to cursed ones. Bloody Soil and Bloody Soil Farmland still support only vampire crops;
- Bloody Soil and Bloody Soil Farmland can now be obtained by pouring pure blood onto Rich Soil and Rich Soil Farmland respectively;

**Effects**
- Nourishment from Farmer's Delight now correctly affects vampires: health recovery no longer drains blood while under the effect;
- The Blessing effect was renamed to Consecration and optimized: instead of clearing all spirits within a radius, it now only removes entities that start targeting the player;
- Consecration and Fog Vision no longer have multiple amplifiers — each now has a single level. Consecration no longer has a defined range of effect, so there's nothing to amplify, and Fog Vision now reaches further by default, so the stronger potion variants were removed;
- The Dissolving effect is now marked as unobtainable with a tooltip, can be removed via config;

**Food and Items**
- Cooked Bat and Cooked Chops were renamed to Grilled Bat and Grilled Chops;

**Structures**
- Cooking Pots in Hunter Camps are now generated with food in them. As of now, the list consists of: Beef Stew, Onion Soup, Vegetable Soup, and Chicken Soup;

**Textures and Models**
- Updated the Spirit Lantern and Cursed Farmland textures;
- Wine Shelves now render bottles inside them manually instead of being a part of the block state. This significantly shortens the block state files;

**Advancements**
- Several advancements were renamed or reworded:
  - "Local Brewery" —> "He Hasn't Brewed It Since...";
  - "Local Bar" —> "In Vino Veritas";
  - "Funny Cuts of Children" —> "An Eye-Catching Display";
  - "Both Beauty and Health" —> "Afflictive Beauty";
  - "Tea with the taste of jalapeño" —> "Only Stings at First";
  - "Blood Wine Tastes the Same as I Remember" —> "Tastes the Same as I Remember...";
- The `pour_dandelion_beer` advancement's title was shortened, and the root advancement's description was reworded;

**Configuration**
- Added `coloredTooltipsForVampirismItems` (`true` by default): controls whether tooltips of Vampirism's items (Garlic Bread, Human Heart, etc.) are recolored;
- Added `factionTooltips` (`true` by default): controls whether items show a tooltip with the faction they belong to;
- Added `showDissolvingPotionWarning` (`true` by default): controls the tooltip marking Dissolving potions as not intended for survival;
- `disableVampireBite` was replaced with `enableVampireBite` (`true` by default) — the option is now phrased as an enable toggle instead of a disable one;
- `replaceWeirdJellySunscreenWithJumpboost` was renamed to `replaceWeirdJellySunscreenWithJumpBoost` to fix its capitalization;
- The food tooltip color options (`vampireFoodTooltipStartColor`, `vampireFoodTooltipEndColor`, and their hunter and werewolf equivalents) now take a hex string (e.g. `#7c287c`) instead of a list of RGB values (e.g. `124, 40, 124`);
- Changed default values:
  - `vampireBiteChanceLevel1`: `20` —> `25`;
  - `vampireBiteChanceLevel2`: `25` —> `30`;
  - `vampireBiteChanceLevel3`: `30` —> `35`;
  - `cookingPotInHunterCampSpawnChance`: `60` —> `40`;
- Removed `generateVDChestLoot`;

**Tags**
- New tags added:
  - `bloody_soil` (block);
  - `wine_shelves` and `wine_shelves_wooden` (block) — these replace the old single `wine_shelf` tag;
  - `unholy_spirits` and `drops_human_eye` (entity);
  - `blood_syrup_ingredients` (item) — used in Blood Syrup recipe;
  - `without_wild_garlic` (biome);
- Tags removed:
  - `drops_orchid_cake_slice` (block);
  - `vampire_food`, `hunter_food`, `werewolf_only_food`, and `not_rotten_food` (item) — faction food is now handled in code instead of tags;
  - `has_structure/lost_carriage` (biome) — the structure was removed;

**Integrations**
- Farmer's Respite compatibility was improved: Daisy Tea and Orchid Tea now have dedicated fluid types and can be brewed in the Kettle properly. Create compatibility was also added — the drinks can be emptied and their fluid poured into glasses;

### Removed
- The Lost Carriage structure;
- The `get_clothes_dissolving_potion` advancement ("The Best Present for Any Man"): it is impossible to obtain in survival;
- Leftover Brewing Barrel translation entries — the block was planned as a workstation but never added. It may return later, but there is no reason to keep the entries for now;
- The mod's special commands — they were mainly used for testing and are now obsolete;

### Technical
- This version is made for Farmer's Delight 1.3 or newer. All recipes now use modern FD tags;
- Werewolves mod is now integrated at a deeper level:
  - Werewolves' code is accessed at compile time, so it can be used directly instead of relying on workarounds;
  - Faction food for Werewolves is handled directly;
  - All item and block resources are data-generated rather than created manually;
  - Werewolves-specific items and blocks are registered only if Werewolves is installed. They are not just inaccessible and hidden as they used to be and will disappear if Werewolves is removed;
- Almost all data is now generated through code; the only remaining exceptions are external integrations;
- The mixin config plugin class (`VDMixinConfigPlugin`) was removed, as it is no longer needed;
- The Dark Stone Stove now reuses Farmer's Delight's Stove block entity instead of a duplicate that did the same thing;

## 0.1.7e

### Translations
- Added:
  - zh_Cn (Thanks, chairs!);

## 0.1.7d

### Updates
- Changed food effects:
  - **Snow_White Ice Cream**: `4` to `3,5`;
  - **Blood Wine Glass**: changed `Regeneration II for 10 seconds` to `Regeneration for 15 seconds`;
  - **Orchid Tea**: changed `Regeneration for 20 seconds` to `10 seconds`;
  - **Mulled Wine Glass**: replaced `Regeneration` with `Haste for  3 minutes`;
  - **Orchid Ice Cream**: replaced `Resistance` with `Vampire Fire Protection II for 1 minute`;
  - **Dandelion Beer Mug**: replaced `Strength` with `Haste for 3 minutes` and added half a point of saturation;
  - **Borscht**: replaced `Resistance` with `Absorption for 1 minute 30 seconds`;
  - **Wolf Berry Ice Cream**: replaced `Resistance` with `Haste for 2 minutes`;

## 0.1.7c

### Fixes
- Fixed a bug that crashed the game when bat-related food effect tooltips were displayed and `batMeatWithersHumans` was set to `false`;

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