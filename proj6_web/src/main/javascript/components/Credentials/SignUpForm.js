import React, { Component } from "react";
import {
  Grid,
  Form,
  Segment,
  Button,
  Header,
  Message,
  Icon,
} from "semantic-ui-react";

class SignUpForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name: "",
      email: "",
      password: "",
      error: "",
    };
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
    this.isFormEmpty = this.isFormEmpty.bind(this);
    this.isFormValid = this.isFormValid.bind(this);
  }

  isFormValid() {
    if (this.isFormEmpty(this.state)) {
      this.setState({
        error: "Fill all fields",
      });
      return false;
    } else {
      return true;
    }
  }

  isFormEmpty({ name, email, password }) {
    return !name.length || !email.length || !password.length;
  }

  handleChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  onSubmit(e) {
    e.preventDefault();
    if (this.isFormValid()) {
      this.props.onSignUp(this.state);
      this.setState({
        name: "",
        email: "",
        password: "",
        error: "",
      });
    }
  }

  render() {
    return (
      <Grid textAlign="center" verticalAlign="middle" className="App">
        <Grid.Column style={{ maxWidth: 450 }}>
          <Header as="h1" icon color="blue" textAlign="center">
            Sign Up!
            <Icon name="wechat" color="blue" />
          </Header>
          <Form size="large" onSubmit={this.onSubmit}>
            <Segment stacked>
              <Form.Input
                fluid
                name="name"
                icon="user"
                iconPosition="left"
                placeholder="Name"
                type="text"
                value={this.state.name}
                onChange={this.handleChange}
              />
              <Form.Input
                fluid
                name="email"
                icon="mail"
                iconPosition="left"
                placeholder="Email"
                type="email"
                value={this.state.email}
                onChange={this.handleChange}
              />
              <Form.Input
                fluid
                name="password"
                icon="lock"
                iconPosition="left"
                placeholder="Password"
                type="password"
                value={this.state.password}
                onChange={this.handleChange}
              />
              <Button color="blue" fluid size="large">
                Sign Up
              </Button>
            </Segment>
          </Form>
          {this.state.error.length > 0 && (
            <Message error>
              <h3>Error</h3>
              {this.state.error}
            </Message>
          )}
          <Message>
            Already have an account?{" "}
            <a role="button" onClick={this.props.goToLogin}>
              {" "}
              Go login!
            </a>
          </Message>
        </Grid.Column>
      </Grid>
    );
  }
}

export default SignUpForm;
