import React from "react";
import { Button, Segment, Input, Form } from "semantic-ui-react";

class SendMessageForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      textMessage: "",
    };
    this.onMessageSend = this.onMessageSend.bind(this);
  }

  onMessageSend(e) {
    e.preventDefault();
    this.props.sendMessage(this.state.textMessage);
    this.setState({ textMessage: "" });
  }

  render() {
    return (
      <React.Fragment>
        <Segment>
          <Form className="message__from" onSubmit={this.onMessageSend}>
            <Input
              disabled={this.props.channelId == null}
              fluid
              name="message"
              style={{ marginBottom: "0.7em" }}
              label={<Button icon={"add"} />}
              labelPosition="left"
              value={this.state.textMessage}
              onChange={(e) => this.setState({ textMessage: e.target.value })}
              placeholder={this.props.translationStrings.labelTypeYourMessage}
            />
          </Form>
        </Segment>
      </React.Fragment>
    );
  }
}

export default SendMessageForm;
