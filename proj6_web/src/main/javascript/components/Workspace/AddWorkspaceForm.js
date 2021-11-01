import React, { Component } from "react";
import { Form, Input } from "semantic-ui-react";

class AddWorkspaceForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      workspaceName: "",
    };
    this.handleAddWorkspace = this.handleAddWorkspace.bind(this);
  }

  handleAddWorkspace(e) {
    e.preventDefault();
    this.props.addWorkspace(this.state.workspaceName);
    this.setState({ workspaceName: "" });
  }

  render() {
    return (
      <Form onSubmit={this.handleAddWorkspace}>
        <Form.Field>
          <Input
            fluid
            type="text"
            placeholder={
              this.props.translationStrings.labelPlaceholderAddWorkspace
            }
            onChange={(e) => this.setState({ workspaceName: e.target.value })}
            value={this.state.workspaceName}
          />
        </Form.Field>
      </Form>
    );
  }
}

export default AddWorkspaceForm;
