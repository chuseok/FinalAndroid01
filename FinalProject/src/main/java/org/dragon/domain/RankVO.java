package org.dragon.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankVO {

	private String userId;
	private String img;
	private int rank;
	private int totalLevel;
	private int score;
	private String totalConnection;
}
