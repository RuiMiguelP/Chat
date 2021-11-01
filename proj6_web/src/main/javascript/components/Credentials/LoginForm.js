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

class LoginForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      email: "",
      password: "",
      error: "",
    };
    this.handleChange = this.handleChange.bind(this);
    this.login = this.login.bind(this);
    this.isFormValid = this.isFormValid.bind(this);
    this.isFormEmpty = this.isFormEmpty.bind(this);
  }

  handleChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  login(e) {
    e.preventDefault();
    if (this.isFormValid()) {
      this.props.onLogin(this.state);
      this.setState({
        email: "",
        password: "",
      });
    }
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

  isFormEmpty({ email, password }) {
    return !email.length || !password.length;
  }

  render() {
    return (
      <Grid textAlign="center" verticalAlign="middle" className="App">
        <Grid.Column style={{ maxWidth: 450 }}>
          <Header as="h1" icon color="violet" textAlign="center">
            Login to AoR Chat!
            <Icon name="rocketchat" color="violet" />
          </Header>
          <Form size="large" onSubmit={this.login}>
            <Segment stacked>
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

              <Button color="violet" fluid size="large">
                Login
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
            Don't have an account?{" "}
            <a role="button" onClick={this.props.goToSignUp}>
              {" "}
              Go signup!
            </a>
          </Message>
        </Grid.Column>
      </Grid>
    );
  }
}

export default LoginForm;
