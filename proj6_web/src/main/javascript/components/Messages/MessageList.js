import React from "react";
import moment from "moment";
import { Segment, Comment } from "semantic-ui-react";

const timeFromNow = (timestamp) => moment(timestamp).fromNow();

const isOwnMessage = (message, uid) => {
  return message.userDto.id === uid ? "message__self" : "";
};

const Message = ({ message, uid }) => {
  return (
    <Comment>
      <Comment.Content className={isOwnMessage(message, uid)}>
        <Comment.Author>{message.userDto.name}</Comment.Author>
        <Comment.Metadata>{timeFromNow(message.createdDate)}</Comment.Metadata>
        <Comment.Text>{message.bodyMessage}</Comment.Text>
      </Comment.Content>
    </Comment>
  );
};

const MessageList = ({ messages, uid, channelId }) => (
  <Segment>
    {channelId == null ? (
      <Comment.Group className="messages"></Comment.Group>
    ) : (
      <Comment.Group className="messages">
        {messages.map((message) => (
          <Message key={message.id} message={message} uid={uid} />
        ))}
      </Comment.Group>
    )}
  </Segment>
);

export default MessageList;
