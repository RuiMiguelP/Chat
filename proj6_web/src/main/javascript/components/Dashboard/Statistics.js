import React from "react";
import { Statistic, Label, Divider } from "semantic-ui-react";
import TableStats from "./TableStats";

class Statistics extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      numberWorkspaces: null,
      numberUsers: null,
      numberChannelsPerWorkspace: [],
      numberMessagesPerWorkspace: [],
      numberMessagesPerChannel: [],
      numberMessagesPerDirectMessage: [],
      stats: [],
    };
    this.ws = new WebSocket("ws://localhost:8080/proj6_web_server/dashboard");
  }

  componentDidMount() {
    this.ws.onopen = () => {
      console.log("Opened Connection Dashboard!");
    };

    this.ws.onmessage = (event) => {
      var numberWorkspaces = [];
      var numberUsers = [];
      var numberChannelsPerWorkspace = [];
      var numberMessagesPerWorkspace = [];
      var numberMessagesPerChannel = [];
      var numberMessagesPerDirectMessage = [];
      var mapData = JSON.parse(event.data);

      Object.keys(mapData).forEach(function (key) {
        switch (key) {
          case "number_users":
            numberUsers = mapData[key];
            break;
          case "number_messages_conversation":
            numberMessagesPerDirectMessage = mapData[key];
            break;
          case "number_channels_workspace":
            numberChannelsPerWorkspace = mapData[key];
            break;
          case "number_workspaces":
            numberWorkspaces = mapData[key];
            break;
          case "number_messages_channel":
            numberMessagesPerChannel = mapData[key];
            break;
          case "number_messages_workspace":
            numberMessagesPerWorkspace = mapData[key];
            break;
        }
      });

      this.setState({
        numberWorkspaces: numberWorkspaces[0].value,
        numberUsers: numberUsers[0].value,
        numberChannelsPerWorkspace: numberChannelsPerWorkspace,
        numberMessagesPerWorkspace: numberMessagesPerWorkspace,
        numberMessagesPerChannel: numberMessagesPerChannel,
        numberMessagesPerDirectMessage: numberMessagesPerDirectMessage,
      });
    };
  }

  componentWillUnmount() {
    this.ws.close = () => {
      console.log("Closed Dashboard connection.");
    };
  }

  render() {
    return (
      <React.Fragment>
        <Statistic.Group color="blue" floated="center">
          <Statistic>
            <Statistic.Value>{this.state.numberUsers}</Statistic.Value>
            <Statistic.Label>
              {this.props.translationStrings.labelNumUser}
            </Statistic.Label>
          </Statistic>
          <Statistic>
            <Statistic.Value>{this.state.numberWorkspaces}</Statistic.Value>
            <Statistic.Label>
              {this.props.translationStrings.labelNumWorkspaces}
            </Statistic.Label>
          </Statistic>
        </Statistic.Group>

        <Divider />
        <Label>
          {this.props.translationStrings.labelNumberChannelsPerWorkspace}
        </Label>
        <TableStats
          stats={this.state.numberChannelsPerWorkspace}
          translationStrings={this.props.translationStrings}
        />
        <Divider />

        <Label>
          {this.props.translationStrings.labelNumberMessagesPerWorkspace}
        </Label>
        <TableStats
          stats={this.state.numberMessagesPerWorkspace}
          translationStrings={this.props.translationStrings}
        />

        <Divider />
        <Label>
          {this.props.translationStrings.labelNumberMessagesPerChannel}
        </Label>
        <TableStats
          stats={this.state.numberMessagesPerChannel}
          translationStrings={this.props.translationStrings}
        />

        <Divider />
        <Label>
          {this.props.translationStrings.labelNumberMessagesPerDirectMessages}
        </Label>
        <TableStats
          stats={this.state.numberMessagesPerDirectMessage}
          translationStrings={this.props.translationStrings}
        />
      </React.Fragment>
    );
  }
}

export default Statistics;
