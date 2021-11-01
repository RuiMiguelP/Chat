import React from "react";
import { Dropdown, Menu } from "semantic-ui-react";

const languageOptions = [
  { key: 1, text: "EN", value: "en" },
  { key: 2, text: "PT", value: "pt" },
  { key: 3, text: "FR", value: "fr" },
];

class MenuLocalization extends React.Component {
  constructor(props) {
    super(props);
    this.handleChange = this.handleChange.bind(this);
  }

  handleChange(e, { value }) {
    e.preventDefault();
    this.props.onChangeLanguage(value);
  }

  render() {
    return (
      <Menu.Menu className="menu">
        <Menu.Item style={{ opacity: 0.7 }}>
          <Dropdown
            button
            className="icon"
            floating
            labeled
            icon="world"
            options={languageOptions}
            onChange={this.handleChange}
            defaultValue={this.props.language}
          />
        </Menu.Item>
      </Menu.Menu>
    );
  }
}

export default MenuLocalization;
