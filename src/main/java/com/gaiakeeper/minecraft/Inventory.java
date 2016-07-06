package com.gaiakeeper.minecraft;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class Inventory {
	protected IInventory _inventory;

	public Inventory(IInventory inventory){
		_inventory = inventory;
	}
	
	public int getSize(){
		return _inventory.getSizeInventory();
	}
	
    public Item getInSlot(int slot)
    {
         return new Item(_inventory.getStackInSlot(slot));
    }
    
    public void setInSlot(int slot, Item item)
    {
    	_inventory.setInventorySlotContents(slot, item.getItemStack());
    }
    
    public boolean addItem(Item item)
    {
    	for (int i = 0; i < _inventory.getSizeInventory(); i++){
    		if ( _inventory.getStackInSlot(i) == null ){
    			_inventory.setInventorySlotContents(i, item.getItemStack());
    			
    			return true;
    		}
    	}
    	return false;
    }
    
    public boolean hasItem(Item item){
    	for (int i = 0; i < _inventory.getSizeInventory(); i++){
    		ItemStack in = _inventory.getStackInSlot(i);
    		if ( (in != null) && (in.getItem() == item.getItem()) ){
    			return true;
    		}
    	}
    	return false;
    }

    public void clear(){
    	for (int i = 0; i < _inventory.getSizeInventory(); i++){
    		_inventory.setInventorySlotContents(i, null);
    	}
    }
}
