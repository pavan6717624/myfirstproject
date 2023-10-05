package com.mytradingsetup.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "fno")
public class FnoStock implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -2465973999729411231L;

	@Id
	Long itoken = 0l;
	String symbol = "";
	String name = "";

}
