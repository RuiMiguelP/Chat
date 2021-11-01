import React from "react";
import { Grid, Header, Icon, Dropdown } from "semantic-ui-react";

class UserPanel extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      user: this.props.displayName,
    };
  }
  dropdownOptions(isAdmin, wantsToGoDashboard) {
    if (isAdmin) {
      if (wantsToGoDashboard) {
        return [
          {
            key: "user",
            text: (
              <span>
                {this.props.translationStrings.labelSignedInAs}{" "}
                <strong>{this.state.user}</strong>
              </span>
            ),
            disabled: true,
          },
          {
            key: "chatpanel",
            text: (
              <span onClick={this.props.goToChatPanel}>
                {this.props.translationStrings.labelGoChatPanel}
              </span>
            ),
          },
          {
            key: "signout",
            text: (
              <span onClick={this.props.logout}>
                {this.props.translationStrings.labelSignedOut}
              </span>
            ),
          },
        ];
      } else {
        return [
          {
            key: "user",
            text: (
              <span>
                {this.props.translationStrings.labelSignedInAs}{" "}
                <strong>{this.state.user}</strong>
              </span>
            ),
            disabled: true,
          },
          {
            key: "dashboard",
            text: (
              <span onClick={this.props.goToDashboard}>
                {this.props.translationStrings.labelGoDashboard}
              </span>
            ),
          },
          {
            key: "signout",
            text: (
              <span onClick={this.props.logout}>
                {this.props.translationStrings.labelSignedOut}
              </span>
            ),
          },
        ];
      }
    } else {
      return [
        {
          key: "user",
          text: (
            <span>
              {this.props.translationStrings.labelSignedInAs}{" "}
              <strong>{this.state.user}</strong>
            </span>
          ),
          disabled: true,
        },
        {
          key: "signout",
          text: (
            <span onClick={this.props.logout}>
              {this.props.translationStrings.labelSignedOut}
            </span>
          ),
        },
      ];
    }
  }

  render() {
    return (
      <Grid style={{ background: "#303641" }}>
        <Grid.Column>
          <Grid.Row style={{ padding: "1.2em", margin: 0 }}>
            <Header inverted floated="left" as="h2">
              <Icon name="at"></Icon>
              <Header.Content>AoRChat</Header.Content>
            </Header>
          </Grid.Row>
          <Header style={{ padding: "0.25em" }} as="h4" inverted>
            <Dropdown
              trigger={<span>{this.state.user}</span>}
              options={this.dropdownOptions(
                this.props.isAdmin,
                this.props.wantsToGoDashboard
              )}
            />
          </Header>
        </Grid.Column>
      </Grid>
    );
  }
}

export default UserPanel;
