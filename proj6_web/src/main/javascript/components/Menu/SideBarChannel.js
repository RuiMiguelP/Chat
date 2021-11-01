import React from "react";
import ChannelList from "../Channel/ChannelList";
import DirectMessages from "../Users/DirectMessages";
import { Menu } from "semantic-ui-react";
import AddUsersWorkspace from "../Users/AddUsersWorkspace";

const SideBarChannel = ({
  channels,
  selectedChannel,
  setChannel,
  addChannel,
  users,
  selectedUser,
  setUserWorkspace,
  isWorkspaceOwner,
  onAddUsersWorkspace,
  globalUsers,
  onSubscribeWorkspace,
  isChannelOwner,
  onDeleteChannel,
  isConversation,
  uid,
  notifications,
  translationStrings,
}) => {
  return (
    <Menu
      size="large"
      inverted
      fixed="left"
      vertical
      style={{ background: "#373D48", fontSize: "1.2rem", marginLeft: 240 }}
    >
      <AddUsersWorkspace
        isWorkspaceOwner={isWorkspaceOwner}
        onAddUsersWorkspace={onAddUsersWorkspace}
        globalUsers={globalUsers}
        onSubscribeWorkspace={onSubscribeWorkspace}
        translationStrings={translationStrings}
      />
      <ChannelList
        channels={channels}
        selectedChannel={selectedChannel}
        setChannel={setChannel}
        addChannel={addChannel}
        isChannelOwner={isChannelOwner}
        onDeleteChannel={onDeleteChannel}
        isConversation={isConversation}
        uid={uid}
        notifications={notifications}
        translationStrings={translationStrings}
      />
      <DirectMessages
        users={users}
        selectedUser={selectedUser}
        setUserWorkspace={setUserWorkspace}
        notifications={notifications}
        uid={uid}
        translationStrings={translationStrings}
      />
    </Menu>
  );
};

export default SideBarChannel;
