package com.gaiakeeper.minecraft;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class Mob {
	protected EntityLivingBase _mob;	
	public Mob(EntityLivingBase mob){
		_mob = mob;
	};
	
	public EntityLivingBase getEntity(){
		return _mob;
	};

	public Item getHand(){
		ItemStack itemstack = _mob.getEquipmentInSlot(0);
		if ( itemstack != null ) return new Item(itemstack);
		return null;
	}

	public Item getBoots(){
		ItemStack itemstack = _mob.getEquipmentInSlot(1);
		if ( itemstack != null ) return new Item(itemstack);
		return null;
	}

	public Item getLeggings(){
		ItemStack itemstack = _mob.getEquipmentInSlot(2);
		if ( itemstack != null ) return new Item(itemstack);
		return null;
	}

	public Item getChestplate(){
		ItemStack itemstack = _mob.getEquipmentInSlot(3);
		if ( itemstack != null ) return new Item(itemstack);
		return null;
	}

	public Item getHelmet(){
		ItemStack itemstack = _mob.getEquipmentInSlot(4);
		if ( itemstack != null ) return new Item(itemstack);
		return null;
	}

	public void setHand(Item item){
		_mob.setCurrentItemOrArmor(0, item.getItemStack());
	}

	public void setBoots(Item item){
		_mob.setCurrentItemOrArmor(1, item.getItemStack());
	}
	
	public void setLeggings(Item item){
		_mob.setCurrentItemOrArmor(2, item.getItemStack());
	}
	
	public void setChestplate(Item item){
		_mob.setCurrentItemOrArmor(3, item.getItemStack());
	}
	
	public void setHelmet(Item item){
		_mob.setCurrentItemOrArmor(4, item.getItemStack());
	}
	
	// same as '/effect' command
	public void addEffect(int effect, int amplifier, int duration){
		_mob.addPotionEffect(new PotionEffect(effect, duration * 20, amplifier));
	}

	public void removeEffect(int effect){
		_mob.removePotionEffect(effect);
	}

	public void addEffect(String name, int amplifier, int duration){
		int effect = EffectType.lookup(name).getID();
		_mob.addPotionEffect(new PotionEffect(effect, duration * 20, amplifier));
	}

	public void removeEffect(String name){
		int effect = EffectType.lookup(name).getID();
		_mob.removePotionEffect(effect);
	}

	public void removeAllEffects(){
		_mob.clearActivePotions();
	}
	
	public String toString(){
		return _mob.toString();
	}
}
