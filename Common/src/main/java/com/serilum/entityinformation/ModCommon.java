package com.serilum.entityinformation;


import com.natamus.collective.translations.ServerTranslationPack;
import com.serilum.entityinformation.util.Reference;

public class ModCommon {

	public static void init() {
		load();
	}

	private static void load() {
		ServerTranslationPack.requireClientTranslations(Reference.NAME);
	}
}