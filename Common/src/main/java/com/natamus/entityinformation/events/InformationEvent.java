package com.natamus.entityinformation.events;

import com.natamus.collective.functions.MessageFunctions;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class InformationEvent {
	public static boolean onEntityDamage(Level world, Entity entity, DamageSource damageSource, float damageAmount) {
		Entity source = damageSource.getEntity();
		if (source == null) {
			return true;
		}

		if (world.isClientSide) {
			return true;
		}

		if (!(source instanceof Player)) {
			return true;
		}

		Player player = (Player)source;

		if (!checkStick(player.getMainHandItem())) {
			return true;
		}

		sendEntityInfo(entity, player);

		return false;
	}

	public static void onStickUse(Player player) {
		Level world = player.level();

		if(world.isClientSide || !checkStick(player.getMainHandItem())) {
			return;
		}

		infoRaycast(player, 10);
	}

	public static boolean checkStick(ItemStack item) {
		boolean isStick = item.getItem().equals(Items.STICK);
		boolean hasName = item.getHoverName().getString().equals(ChatFormatting.BLUE + "The Information Stick");

		return isStick && hasName;
	}

	public static void infoRaycast(Player player, float range) {
		Vec3 eyePos = player.getEyePosition();
		Vec3 lookDirection = player.getLookAngle().scale(range);
		Vec3 lookTarget = eyePos.add(lookDirection);

		Level world = player.level();
		AABB searchArea = AABB.ofSize(player.position(), 2*range, 2*range, 2*range);
		List<Entity> entities = world.getEntities(null, searchArea);

		for(Entity e: entities) {
			AABB bounds = e.getBoundingBox();
			if(e instanceof ItemEntity) {
				// Make item hitbox easier to click
				bounds = bounds.inflate(.1, .3, .1);
			}
			Optional<Vec3> raycastHit = bounds.clip(eyePos, lookTarget);
			if(raycastHit.isPresent()) {
				sendEntityInfo(e, player);
			}
		}
	}

	public static void sendEntityInfo(Entity entity, Player player) {
		String name = "Name: " + entity.getName().getString();
		String entityName = "Entity" + name;
		try {
			entityName = "EntityName: " + entity.toString().split("\\[")[0];
		} catch (Exception ignored) {}

		String entityId = "EntityId: " + entity.getId() + " (mod 4: " + entity.getId() % 4 + ")";
		String UUID = "UUID: " + entity.getUUID();
		BlockPos blockPos = entity.blockPosition();
		String blockPosition = String.format("Block Position: %d, %d, %d", blockPos.getX(), blockPos.getY(), blockPos.getZ());
		Vec3 pos = entity.position();
		DecimalFormat fmt = (DecimalFormat) NumberFormat.getNumberInstance(Locale.ENGLISH);
		fmt.setMaximumFractionDigits(3);
		String position = String.format("Position: %s, %s, %s", fmt.format(pos.x), fmt.format(pos.y), fmt.format(pos.z));
		String isSilent = "isSilent: " + entity.isSilent();
		String ticksExisted = "ticksExisted: " + entity.tickCount;

		MessageFunctions.sendMessage(player, "---- Entity Information:", ChatFormatting.BLUE, true);
		MessageFunctions.sendMessage(player, name, ChatFormatting.BLUE);
		MessageFunctions.sendMessage(player, entityName, ChatFormatting.BLUE);
		MessageFunctions.sendMessage(player, entityId, ChatFormatting.BLUE);
		MessageFunctions.sendMessage(player, UUID, ChatFormatting.BLUE);
		MessageFunctions.sendMessage(player, blockPosition, ChatFormatting.BLUE);
		MessageFunctions.sendMessage(player, position, ChatFormatting.BLUE);
		MessageFunctions.sendMessage(player, isSilent, ChatFormatting.BLUE);
		MessageFunctions.sendMessage(player, ticksExisted, ChatFormatting.BLUE);
	}
}
