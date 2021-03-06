package io.github.apace100.origins;

import io.github.apace100.origins.command.LayerArgument;
import io.github.apace100.origins.command.OriginArgument;
import io.github.apace100.origins.command.OriginCommand;
import io.github.apace100.origins.mixin.CriteriaRegistryInvoker;
import io.github.apace100.origins.networking.ModPacketsC2S;
import io.github.apace100.origins.origin.Origin;
import io.github.apace100.origins.origin.OriginLayers;
import io.github.apace100.origins.origin.OriginManager;
import io.github.apace100.origins.power.PowerTypes;
import io.github.apace100.origins.power.factory.PowerFactories;
import io.github.apace100.origins.power.factory.condition.*;
import io.github.apace100.origins.power.factory.action.BlockActions;
import io.github.apace100.origins.power.factory.action.EntityActions;
import io.github.apace100.origins.power.factory.action.ItemActions;
import io.github.apace100.origins.registry.*;
import io.github.apace100.origins.util.ChoseOriginCriterion;
import io.github.apace100.origins.util.ElytraPowerFallFlying;
import io.github.apace100.origins.util.GainedPowerCriterion;
import net.adriantodt.fallflyinglib.FallFlyingLib;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.command.argument.ArgumentTypes;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Origins implements ModInitializer {

	public static final String MODID = "origins";
	public static final Logger LOGGER = LogManager.getLogger(Origins.class);

	@Override
	public void onInitialize() {
		LOGGER.info("Origins is initializing. Have fun!");
		ModBlocks.register();
		ModItems.register();
		ModTags.register();
		ModPacketsC2S.register();
		ModEnchantments.register();
		ModEntities.register();
		ModLoot.register();
		PowerFactories.register();
		PlayerConditions.register();
		ItemConditions.register();
		BlockConditions.register();
		DamageConditions.register();
		FluidConditions.register();
		EntityActions.register();
		ItemActions.register();
		BlockActions.register();
		Origin.init();
		FallFlyingLib.registerAccessor(ElytraPowerFallFlying::new);
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			OriginCommand.register(dispatcher);
		});
		CriteriaRegistryInvoker.callRegister(ChoseOriginCriterion.INSTANCE);
		CriteriaRegistryInvoker.callRegister(GainedPowerCriterion.INSTANCE);
		ArgumentTypes.register("origin", OriginArgument.class, new ConstantArgumentSerializer<>(OriginArgument::origin));
		ArgumentTypes.register("layer", LayerArgument.class, new ConstantArgumentSerializer<>(LayerArgument::layer));
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new PowerTypes());
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new OriginManager());
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new OriginLayers());
	}

	public static Identifier identifier(String path) {
		return new Identifier(Origins.MODID, path);
	}
}
