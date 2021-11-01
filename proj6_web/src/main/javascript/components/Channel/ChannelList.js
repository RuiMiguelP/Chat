import React from "react";
import AddChannelForm from "./AddChannelForm";
import { Menu, Icon, Modal, Button, Label } from "semantic-ui-react";

class Channel extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      modal: false,
    };
    this.openModal = this.openModal.bind(this);
    this.closeModal = this.closeModal.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.getNotificationCount = this.getNotificationCount.bind(this);
  }

  handleSubmit(e) {
    e.preventDefault();
    this.props.onDeleteChannel(this.state);
    this.closeModal();
  }

  openModal() {
    this.setState({ modal: true });
  }

  closeModal() {
    this.setState({ modal: false });
  }

  getNotificationCount(channel) {
    let count = 0;

    if (this.props.notifications.length > 0) {
      this.props.notifications.forEach((notification) => {
        if (notification.channelId === channel.id) {
          count = notification.count;
        }
      });
    }

    if (count > 0) {
      return count;
    }
  }

  render() {
    const {
      channel,
      selectedChannel,
      setChannel,
      isChannelOwner,
      translationStrings,
    } = this.props;
    const { modal } = this.state;

    return (
      <React.Fragment>
        <Menu.Item
          style={{ opacity: 0.7 }}
          active={selectedChannel === channel.id}
          onClick={() => setChannel(channel.id, channel.title)}
        >
          # {channel.title}
          {channel.user.id == this.props.uid ? (
            <Icon name="delete" onClick={this.openModal} />
          ) : (
            ""
          )}
          {this.getNotificationCount(channel) && (
            <Label color="red">{this.getNotificationCount(channel)}</Label>
          )}
        </Menu.Item>

        <Modal basic open={modal} onClose={this.closeModal}>
          <Modal.Header>{translationStrings.labelDeleteChannel}</Modal.Header>
          <Modal.Content>
            {translationStrings.labelQuestionDeleteChannel}{" "}
          </Modal.Content>

          <Modal.Actions>
            <Button color="green" inverted onClick={this.handleSubmit}>
              <Icon name="checkmark" /> {translationStrings.optionYes}
            </Button>
            <Button color="red" inverted onClick={this.closeModal}>
              <Icon name="remove" /> {translationStrings.optionNo}
            </Button>
          </Modal.Actions>
        </Modal>
      </React.Fragment>
    );
  }
}

const ChannelList = ({
  channels,
  selectedChannel,
  setChannel,
  addChannel,
  isChannelOwner,
  onDeleteChannel,
  uid,
  notifications,
  translationStrings,
}) => {
  return (
    <React.Fragment>
      <Menu.Menu className="menu__add__user">
        <Menu.Item>
          <span>
            <Icon name="group" />
            {translationStrings.labelChannels}
          </span>{" "}
          ({channels.length})
        </Menu.Item>
        {channels.map((channel) => (
          <Channel
            key={channel.id}
            channel={channel}
            selectedChannel={selectedChannel}
            setChannel={setChannel}
            isChannelOwner={isChannelOwner}
            onDeleteChannel={onDeleteChannel}
            uid={uid}
            notifications={notifications}
            translationStrings={translationStrings}
          />
        ))}
        <Menu.Item className="new-workspace">
          <p>{translationStrings.labelChannels}</p>
          <AddChannelForm
            addChannel={addChannel}
            translationStrings={translationStrings}
          />
        </Menu.Item>
      </Menu.Menu>
    </React.Fragment>
  );
};

export default ChannelList;
