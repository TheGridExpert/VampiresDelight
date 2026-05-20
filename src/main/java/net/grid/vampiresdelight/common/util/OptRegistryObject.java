package net.grid.vampiresdelight.common.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class OptRegistryObject<T> {

    private final @Nullable RegistryObject<T> registryObject;
    private final String modId;

    private OptRegistryObject(@Nullable RegistryObject<T> registryObject, String modId) {
        this.registryObject = registryObject;
        this.modId = modId;
    }

    public static <T> OptRegistryObject<T> register(DeferredRegister<? super T> registry, String modId, String name, Supplier<T> supplier) {
        if (!ModList.get().isLoaded(modId)) {
            return new OptRegistryObject<>(null, modId);
        }
        RegistryObject<T> ro = registry.register(name, supplier);
        return new OptRegistryObject<>(ro, modId);
    }

    public static <T> OptRegistryObject<T> empty(String modId) {
        return new OptRegistryObject<>(null, modId);
    }

    public String modId() {
        return modId;
    }

    public boolean isModLoaded() {
        return ModList.get().isLoaded(modId);
    }

    public boolean isPresent() {
        return registryObject != null && registryObject.isPresent();
    }

    public void ifPresent(Consumer<? super T> action) {
        RegistryObject<T> ro = registryObject;
        if (ro != null && ro.isPresent()) {
            action.accept(ro.get());
        }
    }

    public <U> Optional<U> map(Function<? super T, ? extends U> mapper) {
        return asOptional().map(mapper);
    }

    public Optional<T> asOptional() {
        RegistryObject<T> ro = registryObject;
        return ro != null && ro.isPresent() ? Optional.of(ro.get()) : Optional.empty();
    }

    public T getOrThrow() {
        RegistryObject<T> ro = registryObject;
        if (ro == null) {
            throw new NoSuchElementException("LoadedOptional for mod '" + modId + "' is not present");
        }
        return ro.get();
    }

    public Optional<ResourceLocation> id() {
        RegistryObject<T> ro = registryObject;
        return ro != null ? Optional.ofNullable(ro.getId()) : Optional.empty();
    }

    public Optional<String> name() {
        RegistryObject<T> ro = registryObject;
        return ro != null && ro.getId() != null ? Optional.of(ro.getId().getPath()) : Optional.empty();
    }

    public Optional<RegistryObject<T>> registryObject() {
        return Optional.ofNullable(registryObject);
    }

    public RegistryObject<T> registryObjectOrThrow() {
        RegistryObject<T> ro = registryObject;
        if (ro == null) {
            throw new NoSuchElementException("LoadedOptional for mod '" + modId + "' is not present");
        }
        return ro;
    }
}