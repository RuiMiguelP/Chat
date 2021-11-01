import React from "react";
import { ToastsContainer, ToastsStore } from "react-toasts";

const wsPath = "ws://localhost:8080/proj6_web_server/notifications/";

class Notification extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      notification: null,
      message: "",
    };
  }

  componentDidMount() {
    var path = this.props.uid;

    if (this.props.isLoggedIn) {
      this.ws = new WebSocket(wsPath + path);

      this.ws.onopen = () => {
        console.log("Opened Connection Notification!");
      };

      this.ws.onmessage = (event) => {
        let serverMessage = JSON.parse(event.data);
        let indexWorkspace = this.props.workspaces.findIndex(
          (workspace) => workspace.id == serverMessage.workspaceId
        );

        let indexChannel = this.props.channels.findIndex(
          (channel) => channel.id == serverMessage.channelId
        );

        var workspaceName = this.props.workspaces[indexWorkspace];
        var channelName = this.props.channels[indexChannel];

        switch (serverMessage.notificationType) {
          case "channel":
            this.setState(
              {
                notification: serverMessage,
              },
              () => this.props.addNotification(serverMessage)
            );
            break;
          case "new channel":
            this.setState({
              message: this.props.translationStrings.formatString(
                this.props.translationStrings.newChannel,
                {
                  workspaceName: workspaceName.title,
                }
              ),
            });
            ToastsStore.warning(this.state.message, 10000);
            break;
          case "subscription channel":
            this.setState({
              message: this.props.translationStrings.formatString(
                this.props.translationStrings.subscriptionChannel,
                {
                  workspaceName: workspaceName.title,
                  channelName: channelName.title,
                }
              ),
            });
            ToastsStore.warning(this.state.message, 10000);
            break;
          case "unsubscription channel":
            this.setState({
              message: this.props.translationStrings.formatString(
                this.props.translationStrings.unsubscriptionChannel,
                {
                  workspaceName: workspaceName.title,
                  channelName: channelName.title,
                }
              ),
            });
            ToastsStore.warning(this.state.message, 10000);
            break;
          case "closed channel":
            this.setState({
              message: this.props.translationStrings.formatString(
                this.props.translationStrings.closedChannel,
                {
                  workspaceName: workspaceName.title,
                  channelName: channelName.title,
                }
              ),
            });
            ToastsStore.warning(this.state.message, 10000);
            break;
          case "conversation":
            var senderMessage = serverMessage.senderMessage;
            this.setState({
              message: this.props.translationStrings.formatString(
                this.props.translationStrings.newMessageConversation,
                {
                  username: senderMessage,
                  workspaceName: workspaceName.title,
                }
              ),
            });
            ToastsStore.warning(this.state.message, 10000);
            break;
        }
      };
    }
  }

  componentWillUnmount() {
    this.ws.close = () => {
      console.log("Closed Notification connection.");
    };
  }

  render() {
    return (
      <React.Fragment>
        <ToastsContainer store={ToastsStore} />
      </React.Fragment>
    );
  }
}

export default Notification;
