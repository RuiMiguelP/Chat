import React from "react";
import AddWorkspaceForm from "./AddWorkspaceForm";
import { Menu, Icon, Modal, Button } from "semantic-ui-react";

class Workspace extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      modal: false,
      modalDelete: false,
    };
    this.openModal = this.openModal.bind(this);
    this.closeModal = this.closeModal.bind(this);
    this.openModalDelete = this.openModalDelete.bind(this);
    this.closeModalDelete = this.closeModalDelete.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handlSubmitDelete = this.handlSubmitDelete.bind(this);
  }

  handleSubmit(e) {
    e.preventDefault();
    this.props.onUnsubscribe(this.state);
    this.closeModal();
  }

  handlSubmitDelete(e) {
    e.preventDefault();
    this.props.onDeleteWorkspace(this.state);
    this.closeModalDelete();
  }

  openModal() {
    this.setState({ modal: true });
  }

  closeModal() {
    this.setState({ modal: false });
  }

  openModalDelete() {
    this.setState({ modalDelete: true });
  }

  closeModalDelete() {
    this.setState({ modalDelete: false });
  }

  render() {
    const {
      workspace,
      selectedWorkspace,
      setWorkspace,
      translationStrings,
    } = this.props;
    const { modal, modalDelete } = this.state;

    return (
      <React.Fragment>
        <Menu.Item
          style={{ opacity: 0.7 }}
          active={selectedWorkspace === workspace.id}
          onClick={() => setWorkspace(workspace.id)}
        >
          # {workspace.title}{" "}
          {workspace.user.id == this.props.uid ? (
            <Icon name="delete" onClick={this.openModalDelete} />
          ) : (
            ""
          )}
          <Icon name="user delete" onClick={this.openModal} />
        </Menu.Item>

        <Modal basic open={modal} onClose={this.closeModal}>
          <Modal.Header>
            {translationStrings.labelUnsubscriptionWorkspace}
          </Modal.Header>
          <Modal.Content>
            {translationStrings.labelQuestionUnsubscriptionWorkspace}{" "}
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

        <Modal basic open={modalDelete} onClose={this.closeModalDelete}>
          <Modal.Header>{translationStrings.labelDeleteWorkspace}</Modal.Header>
          <Modal.Content>
            {translationStrings.labelQuestionDeleteWorkspace}{" "}
          </Modal.Content>

          <Modal.Actions>
            <Button color="green" inverted onClick={this.handlSubmitDelete}>
              <Icon name="checkmark" /> {translationStrings.optionYes}
            </Button>
            <Button color="red" inverted onClick={this.closeModalDelete}>
              <Icon name="remove" /> {translationStrings.optionNo}
            </Button>
          </Modal.Actions>
        </Modal>
      </React.Fragment>
    );
  }
}

const WorkspaceList = ({
  workspaces,
  selectedWorkspace,
  setWorkspace,
  addWorkspace,
  onUnsubscribe,
  onDeleteWorkspace,
  isWorkspaceOwner,
  uid,
  translationStrings,
}) => {
  return (
    <Menu.Menu style={{ paddingBottom: "2em" }} className="menu__add__user">
      <Menu.Item>
        <span>
          <Icon name="fork" />
          {translationStrings.labelWorkspaces}
        </span>{" "}
        ({workspaces.length})
      </Menu.Item>
      {workspaces.map((workspace) => (
        <Workspace
          key={workspace.id}
          workspace={workspace}
          selectedWorkspace={selectedWorkspace}
          setWorkspace={setWorkspace}
          onUnsubscribe={onUnsubscribe}
          onDeleteWorkspace={onDeleteWorkspace}
          isWorkspaceOwner={isWorkspaceOwner}
          uid={uid}
          translationStrings={translationStrings}
        />
      ))}
      <Menu.Item>
        <p>{translationStrings.labelAddWorkspace}</p>{" "}
        <AddWorkspaceForm
          addWorkspace={addWorkspace}
          translationStrings={translationStrings}
        />
      </Menu.Item>
    </Menu.Menu>
  );
};

export default WorkspaceList;
