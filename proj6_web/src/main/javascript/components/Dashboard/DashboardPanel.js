import React from "react";
import { Grid } from "semantic-ui-react";
import ManageRoles from "../Dashboard/ManageRoles";
import ManageWorkspaces from "../Dashboard/ManageWorkspaces";
import Statistics from "../Dashboard/Statistics";

class DashboardPanel extends React.Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    return (
      <React.Fragment>
        <Grid.Column style={{ marginLeft: 480 }}>
          {this.props.wantsToGoManageRoles == true && (
            <ManageRoles
              globalUsers={this.props.globalUsers}
              onChangeRole={this.props.onChangeRole}
              translationStrings={this.props.translationStrings}
            />
          )}
          {this.props.wantsToGoManageWorkspaces == true && (
            <ManageWorkspaces
              workspaces={this.props.workspaces}
              addWorkspace={this.props.addWorkspace}
              removeWorkspace={this.props.removeWorkspace}
              channels={this.props.channels}
              removeChannel={this.props.removeChannel}
              handleAddChannel={this.props.handleAddChannel}
              translationStrings={this.props.translationStrings}
            />
          )}
          {this.props.wantsToGoStatistics == true && (
            <Statistics translationStrings={this.props.translationStrings} />
          )}
        </Grid.Column>
      </React.Fragment>
    );
  }
}

export default DashboardPanel;
