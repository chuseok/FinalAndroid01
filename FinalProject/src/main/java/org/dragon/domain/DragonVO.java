package org.dragon.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DragonVO {

	private String userId;
	private int totalLevel;
	private int levelValue;
	private int foodValue;
	private int toyValue;
	
	private int dragonId;
	private boolean equip;
	
	private String img; 
	private int backgroundId;
	
	public DragonVO(String userId, int dragonId, boolean equip) {
		this.userId = userId;
		this.dragonId = dragonId;
		this.equip = equip;
	}
	public DragonVO(String userId, int dragonId, int backgroundId) {
		this.userId = userId;
		this.backgroundId = backgroundId;
		this.dragonId = dragonId;
	}
	public DragonVO(String userId, int totalLevel, int levelValue, int foodValue, boolean equip) {
		this.userId = userId;
		this.totalLevel = totalLevel;
		this.levelValue = levelValue;
		this.foodValue = foodValue;
		this.equip = equip;
	}
}
