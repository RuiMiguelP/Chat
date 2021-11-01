package pt.uc.dei.paj.util;

import java.util.ArrayList;
import java.util.Set;

import pt.uc.dei.paj.dto.ChannelDto;
import pt.uc.dei.paj.dto.MessageDto;
import pt.uc.dei.paj.dto.UserDto;
import pt.uc.dei.paj.dto.WorkspaceDto;
import pt.uc.dei.paj.entity.Channel;
import pt.uc.dei.paj.entity.Message;
import pt.uc.dei.paj.entity.User;
import pt.uc.dei.paj.entity.Workspace;

public class EntityMapper {

	public static ArrayList<UserDto> toUserDtoList(ArrayList<User> original) {
		ArrayList<UserDto> listDto = new ArrayList<>();
		
		for (User auc: original) {	
			listDto.add(auc.toDto());
		}
			
		return listDto;
	}
	
	public static boolean verifyUserOnChannel(Set<User> users, User user) {
		for(User userSet : users) {
			  if ((userSet.getId() == user.getId())) {
				  return true;
			  }
			}
		return false;
	}
	
	public static ArrayList<WorkspaceDto> toListWorkspaceDto(ArrayList<Workspace> original) {
		ArrayList<WorkspaceDto> listDto = new ArrayList<>();
		
		for (Workspace work: original) {	
			listDto.add(work.toDto());
		}
		
		return listDto;
	}
	
	public static ArrayList<ChannelDto> toListChannelDto(ArrayList<Channel> original) {
		ArrayList<ChannelDto> listDto = new ArrayList<>();
		
		for (Channel cha: original) {
			listDto.add(cha.toDto());
		}
		
		return listDto;
	}
	
	public static ArrayList<ChannelDto> toListStatsChannelDto(ArrayList<Channel> original) {
		ArrayList<ChannelDto> listDto = new ArrayList<>();
		
		for (Channel cha: original) {
			listDto.add(cha.toDtoStat());
		}
		
		return listDto;
	}
	
	public static ArrayList<MessageDto> toListMessageDto(ArrayList<Message> original) {
		ArrayList<MessageDto> listDto = new ArrayList<>();
		
		for (Message msg: original) {
			listDto.add(msg.toDto());
		}
		
		return listDto;
	}
}
