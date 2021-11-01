package pt.uc.dei.paj.dto;

import java.io.Serializable;
import java.math.BigInteger;

public class StatsDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String value;
	private String type_id;
	private String name;
	
	public StatsDto() {
		super();
	}

	public StatsDto(String value) {
		super();
		this.value = value;
	}

	public StatsDto(String value, String type_id, String name) {
		super();
		this.value = value;
		this.type_id = type_id;
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
