import React, { Component } from "react";
import { Form, Input } from "semantic-ui-react";

class AddChannelForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      channelName: "",
    };
    this.handleAddChannel = this.handleAddChannel.bind(this);
  }

  handleAddChannel(e) {
    e.preventDefault();
    this.props.addChannel(this.state.channelName);
    this.setState({ channelName: "" });
  }

  render() {
    return (
      <Form onSubmit={this.handleAddChannel}>
        <Form.Field>
          <Input
            fluid
            type="text"
            placeholder={this.props.translationStrings.labelChannelName}
            onChange={(e) => this.setState({ channelName: e.target.value })}
            className="input"
            value={this.state.channelName}
          />
        </Form.Field>
      </Form>
    );
  }
}

export default AddChannelForm;
