import React from "react";
import { Grid } from "semantic-ui-react";

const MainPanel = ({ children }) => {
  return (
    <Grid.Column style={{ marginLeft: 480 }}>
      <React.Fragment>{children}</React.Fragment>
    </Grid.Column>
  );
};

export default MainPanel;
