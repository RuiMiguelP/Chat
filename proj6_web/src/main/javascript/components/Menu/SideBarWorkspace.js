import React from "react";
import WorkspaceList from "../Workspace/WorkspaceList";
import { Menu } from "semantic-ui-react";
import UserPanel from "../Users/UserPanel";
import MenuLocalization from "./MenuLocalization";

const SideBarWorkspace = ({
  logout,
  workspaces,
  selectedWorkspace,
  setWorkspace,
  addWorkspace,
  displayName,
  onUnsubscribe,
  onDeleteWorkspace,
  isWorkspaceOwner,
  isAdmin,
  goToDashboard,
  wantsToGoDashboard,
  goToChatPanel,
  uid,
  language,
  onChangeLanguage,
  translationStrings,
}) => {
  return (
    <Menu
      size="large"
      inverted
      fixed="left"
      vertical
      style={{ background: "#303641", fontSize: "1.2rem" }}
    >
      <UserPanel
        logout={logout}
        displayName={displayName}
        isAdmin={isAdmin}
        goToDashboard={goToDashboard}
        wantsToGoDashboard={wantsToGoDashboard}
        goToChatPanel={goToChatPanel}
        translationStrings={translationStrings}
      />
      {wantsToGoDashboard ? (
        ""
      ) : (
        <React.Fragment>
          <WorkspaceList
            workspaces={workspaces}
            selectedWorkspace={selectedWorkspace}
            setWorkspace={setWorkspace}
            addWorkspace={addWorkspace}
            onUnsubscribe={onUnsubscribe}
            onDeleteWorkspace={onDeleteWorkspace}
            isWorkspaceOwner={isWorkspaceOwner}
            uid={uid}
            translationStrings={translationStrings}
          />
          <MenuLocalization
            language={language}
            onChangeLanguage={onChangeLanguage}
            translationStrings={translationStrings}
          />
        </React.Fragment>
      )}
    </Menu>
  );
};

export default SideBarWorkspace;
