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

class ManageRoles extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      modal: false,
      emailUser: "",
      error: "",
    };
    this.openModal = this.openModal.bind(this);
    this.closeModal = this.closeModal.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.isEmailValid = this.isEmailValid.bind(this);
    this.handleChange = this.handleChange.bind(this);
  }

  handleChange(e) {
    this.setState({ [e.target.name]: e.target.value, error: "" });
  }

  handleSubmit(e) {
    e.preventDefault();
    if (this.isEmailValid(this.state)) {
      this.props.onChangeRole(this.state);
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
      error: this.props.translationStrings.labelEmailNotValid,
    });
    return false;
  }

  openModal() {
    this.setState({ modal: true });
  }

  closeModal() {
    this.setState({ modal: false });
  }

  render() {
    const { modal } = this.state;

    return (
      <React.Fragment>
        <Label>{this.props.translationStrings.labelManageUserRoles}</Label>
        <Divider />
        <Table celled inverted selectable textAlign="center">
          <Table.Header>
            <Table.Row>
              <Table.HeaderCell>
                {this.props.translationStrings.labelName}
              </Table.HeaderCell>
              <Table.HeaderCell>
                {this.props.translationStrings.labelEmail}
              </Table.HeaderCell>
              <Table.HeaderCell>Admin</Table.HeaderCell>
            </Table.Row>
          </Table.Header>

          <Table.Body>
            {this.props.globalUsers.map((globalUser) => (
              <Table.Row key={globalUser.id}>
                <Table.Cell>{globalUser.name}</Table.Cell>
                <Table.Cell>{globalUser.email}</Table.Cell>
                <Table.Cell>
                  {globalUser.admin ? (
                    <Icon color="green" name="checkmark" size="large" />
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
                  <Icon name="user" />{" "}
                  {this.props.translationStrings.labelChangeRole}
                </Button>
              </Table.HeaderCell>
            </Table.Row>
          </Table.Footer>
        </Table>

        <Modal basic open={modal} onClose={this.closeModal}>
          <Modal.Header>
            {this.props.translationStrings.labelChangeRole}
          </Modal.Header>
          <Modal.Content>
            <Form onSubmit={this.handleSubmit}>
              <Form.Field>
                <Input
                  type="email"
                  fluid
                  label="User Email"
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
              {this.props.translationStrings.labelConfirm}
            </Button>
            <Button color="red" inverted onClick={this.closeModal}>
              <Icon name="remove" /> {this.props.translationStrings.labelCancel}
            </Button>
          </Modal.Actions>
        </Modal>
      </React.Fragment>
    );
  }
}

export default ManageRoles;
