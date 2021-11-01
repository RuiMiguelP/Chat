import React from "react";
import { Menu, Icon } from "semantic-ui-react";

async function getDataWithHeaders(url = "", headerData = {}) {
  return await fetch(url, {
    method: "GET",
    mode: "cors",
    cache: "no-cache",
    credentials: "same-origin",
    headers: headerData,
    redirect: "follow",
    referrerPolicy: "no-referrer",
  });
}

class User extends React.Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    const {
      user,
      selectedUser,
      setUserWorkspace,
      uid,
      translationStrings,
    } = this.props;

    return (
      <Menu.Item
        disabled={user.id == uid}
        style={{ opacity: 0.7, fontStyle: "italic" }}
        active={selectedUser === user.id}
        onClick={() => setUserWorkspace(user.id, user.name)}
      >
        @
        {user.id == uid
          ? user.name + translationStrings.labelYourself
          : user.name}
        <Icon name="circle" color={user.active ? "green" : "red"} />
      </Menu.Item>
    );
  }
}

const DirectMessages = ({
  users,
  selectedUser,
  setUserWorkspace,
  notifications,
  uid,
  translationStrings,
}) => {
  return (
    <Menu.Menu className="menu">
      <Menu.Item>
        <span>
          <Icon name="chat" />
          {translationStrings.labelDirectMessages}
        </span>{" "}
        ({users.length})
      </Menu.Item>
      {users.map((user) => (
        <User
          key={user.id}
          user={user}
          selectedUser={selectedUser}
          setUserWorkspace={setUserWorkspace}
          notifications={notifications}
          uid={uid}
          translationStrings={translationStrings}
        />
      ))}
    </Menu.Menu>
  );
};

export default DirectMessages;
