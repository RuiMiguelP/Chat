import React from "react";
import {
  Menu,
  Icon,
  Modal,
  Button,
  Form,
  Message,
  Input,
  Label,
} from "semantic-ui-react";

class AddUsersWorkspace extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      modal: false,
      emailUser: "",
      selectedUser: null,
      error: "",
    };
    this.openModal = this.openModal.bind(this);
    this.closeModal = this.closeModal.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.isEmailValid = this.isEmailValid.bind(this);
  }

  handleSubmit(e) {
    e.preventDefault();
    if (this.isEmailValid(this.state)) {
      this.props.onSubscribeWorkspace(this.state);
      this.setState(
        {
          emailUser: "",
        },
        () => this.closeModal()
      );
    }
  }

  isEmailValid({ emailUser }) {
    for (var i = 0; i < this.props.globalUsers.length; i++) {
      if (this.props.globalUsers[i].email == emailUser) {
        return true;
      }
    }
    this.setState({
      error: this.props.translationStrings.errorEmail,
    });
    return false;
  }

  handleChange(e) {
    this.setState({ [e.target.name]: e.target.value, error: "" });
  }

  openModal(e) {
    e.preventDefault();
    this.props.onAddUsersWorkspace(this.state);
    this.setState({ modal: true });
  }

  closeModal() {
    this.setState({ modal: false });
  }

  render() {
    const { modal } = this.state;

    return (
      <React.Fragment>
        <Menu.Menu className="menu__add__user">
          <Menu.Item
            style={{ opacity: 0.7 }}
            disabled={!this.props.isWorkspaceOwner}
            onClick={this.openModal}
          >
            <span>
              <Icon name="add user" />
              {this.props.translationStrings.labelAddUsersToWorkspace}
            </span>
          </Menu.Item>
        </Menu.Menu>

        <Modal basic open={modal} onClose={this.closeModal}>
          <Modal.Header>
            {this.props.translationStrings.labelAddUsersToWorkspace}
          </Modal.Header>
          <Modal.Content>
            <Form onSubmit={this.handleSubmit}>
              <Form.Field>
                <Input
                  type="email"
                  fluid
                  label={this.props.translationStrings.labelEmail}
                  name="emailUser"
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
              {this.props.translationStrings.optionAdd}
            </Button>
            <Button color="red" inverted onClick={this.closeModal}>
              <Icon name="remove" />{" "}
              {this.props.translationStrings.optionCancel}
            </Button>
          </Modal.Actions>
        </Modal>
      </React.Fragment>
    );
  }
}

export default AddUsersWorkspace;
