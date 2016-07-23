package com.gaiakeeper.minecraft;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class Item {
	protected ItemStack _itemstack;
	protected net.minecraft.item.Item _item;
	
	public Item(ItemStack itemstack){
		_itemstack = itemstack;
		_item = _itemstack.getItem();
	}
	
	public ItemStack getItemStack(){
		return _itemstack;
	}
	
	public net.minecraft.item.Item getItem(){
		return _item;
	}
	
	public int getStack(){
		return _itemstack.stackSize;
	}
	
	public void setStack(int stack){
		_itemstack.stackSize = stack;
	}
	
	public int getStackMax(){
		return _itemstack.getMaxStackSize();
	}
	
	public void setStackMax(int stack){
		_item.setMaxStackSize(stack);
	}
	
	public String getName(){
		return _itemstack.getDisplayName();
	}
	
	public void setName(String name){
		_itemstack.setStackDisplayName(name);
	}
	
	public int getDamage(){
		return _itemstack.getItemDamage();
	}
	
	public void setDamage(int damage){
		_itemstack.setItemDamage(damage);
	}
	
	public int getDamageMax(){
		return _itemstack.getMaxDamage();
	}
	
	public void setDamageMax(int damage){
		_item.setMaxDamage(damage);
	}
	
	public void enchant(int enchantment, int level){
		_itemstack.addEnchantment(Enchantment.enchantmentsList[enchantment], level);
	}

	public void enchant(String name, int level){
		int enchantment = EnchantType.lookup(name).getID();
		enchant(enchantment, level);
	}

	public String getEffect(){
		return _item.getPotionEffect(_itemstack);
	}
	
	public void setEffect(String effect){
		_item.setPotionEffect(effect);
	}
	
	// only for food
	public void setEffect(int effect, int duration, int amplifier, float probability){
		if ( _item instanceof ItemFood){
			ItemFood food = (ItemFood)_item;
			food.setPotionEffect(effect, duration, amplifier, probability);
		}
	}

	public void setEffect(String name, int duration, int amplifier, float probability){
		int effect = EffectType.lookup(name).getID();
		setEffect(effect, duration, amplifier, probability);
	}
	
	public String toString(){
		String msg = "Item [id=" + net.minecraft.item.Item.getIdFromItem(_item) + ", " +
					"name=" + _itemstack.getDisplayName() + ", " +
					"damage=" + _itemstack.getItemDamage() + ", " +
					"max damage=" + _itemstack.getMaxDamage() + "]";
		return msg;
	}
}
