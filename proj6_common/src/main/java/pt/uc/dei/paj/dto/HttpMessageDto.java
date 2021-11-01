package pt.uc.dei.paj.dto;

import java.io.Serializable;

public class HttpMessageDto extends AbstractDto implements Serializable {
	private static final long serialVersionUID = 1L;

	public HttpMessageDto(String msg) {
		super(msg);
	} 
}
