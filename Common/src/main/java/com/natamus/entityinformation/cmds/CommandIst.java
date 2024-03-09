package com.natamus.entityinformation.cmds;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.natamus.collective.functions.MessageFunctions;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CommandIst {
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("ist")
			.requires((iCommandSender) -> iCommandSender.getEntity() instanceof Player && iCommandSender.hasPermission(2))
			.executes((command) -> {
				processInformationstick(command);
				return 1;
			})
		);
		dispatcher.register(Commands.literal("informationstick")
			.requires((iCommandSender) -> iCommandSender.getEntity() instanceof Player && iCommandSender.hasPermission(2))
			.executes((command) -> {
				processInformationstick(command);
				return 1;
			})
		);
	}
	
	public static void processInformationstick(CommandContext<CommandSourceStack> command) throws CommandSyntaxException {
		CommandSourceStack source = command.getSource();
		Player player = source.getPlayerOrException();
		
		ItemStack informationstick = new ItemStack(Items.STICK, 1);
		informationstick.setHoverName(Component.literal(ChatFormatting.BLUE + "The Information Stick"));
		player.addItem(informationstick);
		MessageFunctions.sendMessage(player, "You have been given The Information Stick!", ChatFormatting.BLUE);
	}
}