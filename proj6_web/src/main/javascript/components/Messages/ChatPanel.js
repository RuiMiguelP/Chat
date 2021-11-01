import React from "react";
import MessageList from "./MessageList";
import SendMessageForm from "./SendMessageForm";
import MessageHeader from "./MessageHeader";

function displayChannelName(selectedChannelName, isConversation) {
  return isConversation ? `@${selectedChannelName}` : `#${selectedChannelName}`;
}

const ChatPanel = (props) => (
  <React.Fragment>
    <MessageHeader
      selectedChannelName={displayChannelName(
        props.selectedChannelName,
        props.isConversation
      )}
      globalUsers={props.globalUsers}
      onAddUsersChannel={props.onAddUsersChannel}
      onSubscribeChannel={props.onSubscribeChannel}
      isConversation={props.isConversation}
      onUnsubscribeChannel={props.onUnsubscribeChannel}
      translationStrings={props.translationStrings}
      channelId={props.channelId}
    />
    <MessageList
      messages={props.messages}
      uid={props.uid}
      translationStrings={props.translationStrings}
      channelId={props.channelId}
    />
    <SendMessageForm
      sendMessage={props.sendMessage}
      email={props.email}
      workspaceId={props.workspaceId}
      channelId={props.channelId}
      uid={props.uid}
      translationStrings={props.translationStrings}
    />
  </React.Fragment>
);

export default ChatPanel;
