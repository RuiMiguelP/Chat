package pt.uc.dei.paj.dto;

public class AbstractDto {

	protected String msg;
	
	public AbstractDto() {
		
	}
	
	public AbstractDto(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
