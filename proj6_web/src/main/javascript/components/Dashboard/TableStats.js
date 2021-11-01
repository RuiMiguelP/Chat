import _ from "lodash";
import React, { Component } from "react";
import { Table } from "semantic-ui-react";

const TableStats = (props) => (
  <Table celled inverted selectable>
    <Table.Header>
      <Table.Row>
        <Table.HeaderCell>Id</Table.HeaderCell>
        <Table.HeaderCell>
          {props.translationStrings.labelName}
        </Table.HeaderCell>
        <Table.HeaderCell>
          {props.translationStrings.labelValue}
        </Table.HeaderCell>
      </Table.Row>
    </Table.Header>
    <Table.Body>
      {props.stats.map((stat) => (
        <Table.Row key={stat.type_id}>
          <Table.Cell>{stat.type_id}</Table.Cell>
          <Table.Cell>{stat.name}</Table.Cell>
          <Table.Cell>{stat.value}</Table.Cell>
        </Table.Row>
      ))}
    </Table.Body>
  </Table>
);

export default TableStats;
