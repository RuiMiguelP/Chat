import React from "react";
import { Menu, Icon } from "semantic-ui-react";

const SideBarDashboard = ({
  goToManageRoles,
  goToManageWorkspaces,
  goToStatistics,
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
      <Menu.Item onClick={goToManageRoles}>
        <span>
          <Icon name="male" />
          {translationStrings.labelManageRoles}
        </span>
      </Menu.Item>
      <Menu.Item onClick={goToManageWorkspaces}>
        <span>
          <Icon name="fork" />
          {translationStrings.labelManageWorkspace}
        </span>
      </Menu.Item>
      <Menu.Item onClick={goToStatistics}>
        <span>
          <Icon name="tachometer alternate" />
          {translationStrings.labelManageStatistics}
        </span>
      </Menu.Item>
    </Menu>
  );
};

export default SideBarDashboard;
