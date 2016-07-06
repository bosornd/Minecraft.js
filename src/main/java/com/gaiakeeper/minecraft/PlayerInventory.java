package com.gaiakeeper.minecraft;

import net.minecraft.entity.player.InventoryPlayer;

public class PlayerInventory extends Inventory {
	InventoryPlayer _inventory;
	
	public PlayerInventory(InventoryPlayer inventory){
		super(inventory);
		
		_inventory = inventory;
	}
	
	public int getSlotInHand(){
		return _inventory.currentItem;
	}

	public void setSlotInHand(int slot){
		_inventory.changeCurrentItem(slot);
	}

}
