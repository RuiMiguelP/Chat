import React from "react";
import {
  Header,
  Segment,
  Input,
  Table,
  Form,
  Icon,
  Modal,
  Button,
  Message,
} from "semantic-ui-react";

class MessageHeader extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      modal: false,
      modalUnsubscribe: false,
      emailUser: "",
      selectedUser: null,
      error: "",
    };
    this.openModal = this.openModal.bind(this);
    this.openModalUnsubscribe = this.openModalUnsubscribe.bind(this);
    this.closeModal = this.closeModal.bind(this);
    this.closeModalUnsubscribe = this.closeModalUnsubscribe.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleSubmitUnsubscribe = this.handleSubmitUnsubscribe.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.isEmailValid = this.isEmailValid.bind(this);
  }

  handleChange(e) {
    this.setState({ [e.target.name]: e.target.value, error: "" });
  }

  handleSubmit(e) {
    e.preventDefault();
    if (this.isEmailValid(this.state)) {
      this.props.onSubscribeChannel(this.state);
      this.setState(
        {
          emailUser: "",
        },
        () => this.closeModal()
      );
    }
  }

  handleSubmitUnsubscribe(e) {
    e.preventDefault();
    this.props.onUnsubscribeChannel(this.state);
    this.closeModalUnsubscribe();
  }

  isEmailValid({ emailUser }) {
    for (var i = 0; i < this.props.globalUsers.length; i++) {
      if (this.props.globalUsers[i].email == emailUser) {
        return true;
      }
    }
    this.setState({
      error: this.props.translationStrings.errorEmailNotFound,
    });
    return false;
  }

  openModal(e) {
    e.preventDefault();
    this.props.onAddUsersChannel(this.state);
    this.setState({ modal: true });
  }

  openModalUnsubscribe(e) {
    e.preventDefault();
    this.setState({ modalUnsubscribe: true });
  }

  closeModal() {
    this.setState({ modal: false });
  }

  closeModalUnsubscribe() {
    this.setState({ modalUnsubscribe: false });
  }

  render() {
    const {
      selectedChannelName,
      isConversation,
      translationStrings,
    } = this.props;
    const { modal, modalUnsubscribe } = this.state;

    return (
      <React.Fragment>
        <Segment clearing>
          <Header
            fluid="true"
            as="h2"
            floated="left"
            style={{ marginBottom: 0 }}
          >
            {this.props.channelId == null ? (
              ""
            ) : (
              <span>{selectedChannelName}</span>
            )}
          </Header>

          <Header floated="right">
            {isConversation ? (
              ""
            ) : (
              <React.Fragment>
                <Icon
                  disabled={this.props.channelId == null}
                  className="header__icon"
                  name="add user"
                  onClick={this.openModal}
                />{" "}
                <Icon
                  disabled={this.props.channelId == null}
                  name="sign-out"
                  onClick={this.openModalUnsubscribe}
                />
              </React.Fragment>
            )}
          </Header>
        </Segment>

        <Modal basic open={modal} onClose={this.closeModal}>
          <Modal.Header>
            {translationStrings.labelChannelAvailableUsers}
          </Modal.Header>
          <Modal.Content>
            <Table celled inverted selectable>
              <Table.Header>
                <Table.Row>
                  <Table.HeaderCell>
                    {translationStrings.labelName}
                  </Table.HeaderCell>
                  <Table.HeaderCell>
                    {translationStrings.labelEmail}
                  </Table.HeaderCell>
                </Table.Row>
              </Table.Header>

              <Table.Body>
                {this.props.globalUsers.map((user) => (
                  <Table.Row key={user.id}>
                    <Table.Cell>{user.name}</Table.Cell>
                    <Table.Cell>{user.email}</Table.Cell>
                  </Table.Row>
                ))}
              </Table.Body>
            </Table>

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
                <h3>{translationStrings.error}</h3>
                {this.state.error}
              </Message>
            )}
          </Modal.Content>

          <Modal.Actions>
            <Button color="green" inverted onClick={this.handleSubmit}>
              <Icon name="checkmark" /> {translationStrings.optionAdd}
            </Button>
            <Button color="red" inverted onClick={this.closeModal}>
              <Icon name="remove" /> {translationStrings.optionCancel}
            </Button>
          </Modal.Actions>
        </Modal>

        <Modal
          basic
          open={modalUnsubscribe}
          onClose={this.closeModalUnsubscribe}
        >
          <Modal.Header>
            {translationStrings.labelUnsubscriptionChannel}
          </Modal.Header>
          <Modal.Content>
            {translationStrings.labelQuestionDeleteChannel}{" "}
          </Modal.Content>

          <Modal.Actions>
            <Button
              color="green"
              inverted
              onClick={this.handleSubmitUnsubscribe}
            >
              <Icon name="checkmark" /> {translationStrings.optionYes}
            </Button>
            <Button color="red" inverted onClick={this.closeModalUnsubscribe}>
              <Icon name="remove" /> {translationStrings.optionNo}
            </Button>
          </Modal.Actions>
        </Modal>
      </React.Fragment>
    );
  }
}

export default MessageHeader;
