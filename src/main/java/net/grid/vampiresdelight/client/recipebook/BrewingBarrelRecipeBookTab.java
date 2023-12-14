package net.grid.vampiresdelight.client.recipebook;

public enum BrewingBarrelRecipeBookTab {
    DRINKS("drinks"),
    MISC("misc");

    public final String name;

    BrewingBarrelRecipeBookTab(String name) {
        this.name = name;
    }

    public static BrewingBarrelRecipeBookTab findByName(String name) {
        for (BrewingBarrelRecipeBookTab value : values()) {
            if (value.name.equals(name)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
