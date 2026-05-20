package net.grid.vampiresdelight.misc.mixin;

import de.teamlapen.vampirism.api.items.oil.IOil;
import de.teamlapen.vampirism.world.loot.functions.SetOilFunction;
import net.grid.vampiresdelight.common.core.VDOils;
import net.minecraftforge.registries.IForgeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Collection;

@Mixin(SetOilFunction.class)
public class SetOilFunctionMixin {

    @Redirect(
            method = "run(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/storage/loot/LootContext;)Lnet/minecraft/world/item/ItemStack;",
            at = @At(value = "INVOKE", target = "Lnet/minecraftforge/registries/IForgeRegistry;getValues()Ljava/util/Collection;", remap = false)
    )
    private Collection<IOil> vampiresdelight$filterOils(IForgeRegistry<IOil> registry) {
        return registry.getValues().stream().filter(oil -> oil != VDOils.DISSOLVING.get()).toList();
    }
}