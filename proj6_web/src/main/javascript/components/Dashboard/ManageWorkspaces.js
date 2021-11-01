import React from "react";
import {
  Table,
  Label,
  Divider,
  Icon,
  Modal,
  Button,
  Form,
  Input,
  Message,
} from "semantic-ui-react";

class ManageWorkspaces extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      modal: false,
      modalChannel: false,
      titleWorkspace: "",
      workspaceId: "",
      titleChannel: "",
      error: "",
    };
    this.openModal = this.openModal.bind(this);
    this.openModalChannel = this.openModalChannel.bind(this);

    this.closeModal = this.closeModal.bind(this);
    this.closeModalChannel = this.closeModalChannel.bind(this);

    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleSubmitChannel = this.handleSubmitChannel.bind(this);
    this.handleChange = this.handleChange.bind(this);

    this.isFormEmpty = this.isFormEmpty.bind(this);
    this.isFormValid = this.isFormValid.bind(this);

    this.isFormValidChannel = this.isFormValidChannel.bind(this);
    this.isFormEmptyChannel = this.isFormEmptyChannel.bind(this);

    this.deleteWorkspace = this.deleteWorkspace.bind(this);
    this.deleteChannel = this.deleteChannel.bind(this);
  }

  handleChange(e) {
    this.setState({ [e.target.name]: e.target.value, error: "" });
  }

  deleteWorkspace(workspaceId) {
    this.props.removeWorkspace(workspaceId);
  }

  deleteChannel(channelId, workspaceId) {
    this.props.removeChannel(channelId, workspaceId);
  }

  handleSubmitChannel(e) {
    e.preventDefault();
    if (this.isFormValidChannel(this.state)) {
      this.props.handleAddChannel(
        this.state.titleChannel,
        this.state.workspaceId
      );
    }
    this.closeModalChannel();
  }

  handleSubmit(e) {
    e.preventDefault();
    if (this.isFormValid(this.state)) {
      this.props.addWorkspace(this.state.titleWorkspace);
    }
    this.closeModal();
  }

  isFormValid() {
    if (this.isFormEmpty(this.state)) {
      this.setState({
        error: this.props.translationStrings.labelFillAllFields,
      });
      return false;
    } else {
      return true;
    }
  }

  isFormValidChannel() {
    if (this.isFormEmptyChannel(this.state)) {
      this.setState({
        error: this.props.translationStrings.labelFillAllFields,
      });
      return false;
    } else {
      return true;
    }
  }

  isFormEmpty({ titleWorkspace }) {
    return !titleWorkspace.length;
  }

  isFormEmptyChannel({ titleChannel }) {
    return !titleChannel.length;
  }

  openModal() {
    this.setState({ modal: true });
  }

  openModalChannel(id) {
    this.setState({ modalChannel: true, workspaceId: id });
  }

  closeModal() {
    this.setState({ modal: false, error: "" });
  }

  closeModalChannel() {
    this.setState({ modalChannel: false, workspaceId: null, error: "" });
  }

  render() {
    const { modal, modalChannel } = this.state;

    return (
      <React.Fragment>
        <Label>{this.props.translationStrings.labelManageWorkspace}</Label>
        <Divider />
        <Table celled inverted selectable textAlign="center">
          <Table.Header>
            <Table.Row>
              <Table.HeaderCell>Id</Table.HeaderCell>
              <Table.HeaderCell>
                {this.props.translationStrings.labelTitle}
              </Table.HeaderCell>
              <Table.HeaderCell>
                {this.props.translationStrings.labelRemoved}
              </Table.HeaderCell>
              <Table.HeaderCell>
                {this.props.translationStrings.labelOperation}
              </Table.HeaderCell>
            </Table.Row>
          </Table.Header>
          <Table.Body>
            {this.props.workspaces.map((workspace) => (
              <Table.Row key={workspace.id}>
                <Table.Cell>{workspace.id}</Table.Cell>
                <Table.Cell>{workspace.title}</Table.Cell>
                <Table.Cell>
                  {workspace.active ? (
                    ""
                  ) : (
                    <Icon color="green" name="checkmark" size="large" />
                  )}
                </Table.Cell>
                <Table.Cell>
                  {workspace.active ? (
                    <React.Fragment>
                      <Button
                        floated="left"
                        icon
                        labelPosition="left"
                        secondary
                        size="small"
                        onClick={() => this.openModalChannel(workspace.id)}
                      >
                        <Icon name="add" />{" "}
                        {this.props.translationStrings.labelAddChannel}
                      </Button>
                      <Icon
                        color="red"
                        name="delete"
                        size="large"
                        onClick={() => this.deleteWorkspace(workspace.id)}
                      />
                    </React.Fragment>
                  ) : (
                    ""
                  )}
                </Table.Cell>
              </Table.Row>
            ))}
          </Table.Body>
          <Table.Footer fullWidth>
            <Table.Row>
              <Table.HeaderCell />
              <Table.HeaderCell colSpan="4">
                <Button
                  floated="right"
                  icon
                  labelPosition="left"
                  primary
                  size="small"
                  onClick={this.openModal}
                >
                  <Icon name="add" />{" "}
                  {this.props.translationStrings.labelAddWorkspace}
                </Button>
              </Table.HeaderCell>
            </Table.Row>
          </Table.Footer>
        </Table>

        <Modal basic open={modal} onClose={this.closeModal}>
          <Modal.Header>
            {this.props.translationStrings.labelCreateWorkspace}
          </Modal.Header>
          <Modal.Content>
            <Form onSubmit={this.handleSubmit}>
              <Form.Field>
                <Input
                  type="text"
                  fluid
                  label="Workspace Title"
                  name="titleWorkspace"
                  onChange={this.handleChange}
                />
              </Form.Field>
            </Form>
            {this.state.error.length > 0 && (
              <Message error>
                <h3>{this.props.translationStrings.error}</h3>
                {this.state.error}
              </Message>
            )}
          </Modal.Content>
          <Modal.Actions>
            <Button color="green" inverted onClick={this.handleSubmit}>
              <Icon name="checkmark" />{" "}
              {this.props.translationStrings.labelConfirm}
            </Button>
            <Button color="red" inverted onClick={this.closeModal}>
              <Icon name="remove" /> {this.props.translationStrings.labelCancel}
            </Button>
          </Modal.Actions>
        </Modal>

        <Label>{this.props.translationStrings.labelManageChannels}</Label>
        <Divider />
        <Table celled inverted selectable textAlign="center">
          <Table.Header>
            <Table.Row>
              <Table.HeaderCell>
                {this.props.translationStrings.labelWorkspace} Id
              </Table.HeaderCell>
              <Table.HeaderCell>Id</Table.HeaderCell>
              <Table.HeaderCell>
                {this.props.translationStrings.labelTitle}
              </Table.HeaderCell>
              <Table.HeaderCell>
                {this.props.translationStrings.labelRemoved}
              </Table.HeaderCell>
              <Table.HeaderCell>
                {this.props.translationStrings.labelOperation}
              </Table.HeaderCell>
            </Table.Row>
          </Table.Header>
          <Table.Body>
            {this.props.channels.map((channel) => (
              <Table.Row key={channel.id}>
                <Table.Cell>{channel.workspace.id}</Table.Cell>
                <Table.Cell>{channel.id}</Table.Cell>
                <Table.Cell>{channel.title}</Table.Cell>
                <Table.Cell>
                  {channel.active ? (
                    ""
                  ) : (
                    <Icon color="green" name="checkmark" size="large" />
                  )}
                </Table.Cell>
                <Table.Cell>
                  {channel.active ? (
                    <Icon
                      color="red"
                      name="delete"
                      size="large"
                      onClick={() =>
                        this.deleteChannel(channel.id, channel.workspace.id)
                      }
                    />
                  ) : (
                    ""
                  )}
                </Table.Cell>
              </Table.Row>
            ))}
          </Table.Body>
        </Table>

        <Modal basic open={modalChannel} onClose={this.closeModalChannel}>
          <Modal.Header>
            {this.props.translationStrings.labelCreateChannel}
          </Modal.Header>
          <Modal.Content>
            <Form onSubmit={this.handleSubmitChannel}>
              <Form.Field>
                <Input
                  type="text"
                  fluid
                  label="Channel Title"
                  name="titleChannel"
                  onChange={this.handleChange}
                />
              </Form.Field>
            </Form>
            {this.state.error.length > 0 && (
              <Message error>
                <h3>{this.props.translationStrings.error}</h3>
                {this.state.error}
              </Message>
            )}
          </Modal.Content>

          <Modal.Actions>
            <Button color="green" inverted onClick={this.handleSubmitChannel}>
              <Icon name="checkmark" />{" "}
              {this.props.translationStrings.labelConfirm}
            </Button>
            <Button color="red" inverted onClick={this.closeModalChannel}>
              <Icon name="remove" /> {this.props.translationStrings.labelCancel}
            </Button>
          </Modal.Actions>
        </Modal>
      </React.Fragment>
    );
  }
}

export default ManageWorkspaces;
