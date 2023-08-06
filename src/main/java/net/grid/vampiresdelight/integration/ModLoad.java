package net.grid.vampiresdelight.integration;

import java.util.Locale;

import net.minecraftforge.fml.ModList;

public enum ModLoad {
    CREATE;

    public boolean isLoaded() {
        return ModList.get().isLoaded(asId());
    }

    public String asId() {
        return asId(name());
    }

    public static String asId(String name) {
        return name.toLowerCase(Locale.ROOT);
    }
}
