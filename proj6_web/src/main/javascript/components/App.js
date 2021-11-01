import React, { Component } from "react";
import "./App.css";
import SignUpForm from "./Credentials/SignUpForm";
import LoginForm from "./Credentials/LoginForm";
import SideBarWorkspace from "./Menu/SideBarWorkspace";
import SideBarChannel from "./Menu/SideBarChannel";
import MainPanel from "./Credentials/MainPanel";
import ChatPanel from "./Messages/ChatPanel";
import { Grid } from "semantic-ui-react";
import DashboardPanel from "./Dashboard/DashboardPanel";
import SideBarDashboard from "./Menu/SideBarDashboard";
import Notification from "./Channel/Notification";
import LocalizedStrings from "react-localization";
import { languages } from "./Menu/TranslationLanguages";

let translationStrings = new LocalizedStrings(languages);

function simpleHeaders() {
  var headers = new Headers();
  headers.append("Content-Type", "application/json;charset=utf-8");
  headers.append("Accept", "application/json;charset=utf-8");
  headers.append("Cache-Control", "no-cache");
  headers.append("Host", "localhost:8080");
  return headers;
}

function formHeaders() {
  var headers = new Headers();
  headers.append(
    "Content-Type",
    "application/x-www-form-urlencoded;charset=utf-8"
  );
  headers.append("Accept", "application/json;charset=utf-8");
  headers.append("Cache-Control", "no-cache");
  headers.append("Host", "localhost:8080");
  return headers;
}

/* =============================
  SERVIÇOS EXTERNOS - WEB SERVER
 ============================= */
const serverUrl = "http://localhost:8080/proj6_web_server/rest";
const usersUrl = serverUrl.concat("/users");
const workspacesUrl = serverUrl.concat("/workspaces");
const channelsUrl = serverUrl.concat("/channels");

async function postData(url = "", headerData = {}, bodyData = {}) {
  return await fetch(url, {
    method: "POST",
    mode: "cors",
    cache: "no-cache",
    credentials: "same-origin",
    headers: headerData,
    redirect: "follow",
    referrerPolicy: "no-referrer",
    body: JSON.stringify(bodyData),
  });
}

async function putData(url = "", headerData = {}, bodyData = {}) {
  return await fetch(url, {
    method: "PUT",
    mode: "cors",
    cache: "no-cache",
    credentials: "same-origin",
    headers: headerData,
    redirect: "follow",
    referrerPolicy: "no-referrer",
    body: JSON.stringify(bodyData),
  });
}

async function deleteData(url = "", headerData = {}, bodyData = {}) {
  return await fetch(url, {
    method: "DELETE",
    mode: "cors",
    cache: "no-cache",
    credentials: "same-origin",
    headers: headerData,
    redirect: "follow",
    referrerPolicy: "no-referrer",
    body: JSON.stringify(bodyData),
  });
}

async function getDataWithHeaders(url = "", headerData = {}) {
  return await fetch(url, {
    method: "GET",
    mode: "cors",
    cache: "no-cache",
    credentials: "same-origin",
    headers: headerData,
    redirect: "follow",
    referrerPolicy: "no-referrer",
  });
}

async function postForm(url = "", headerData = {}, bodyData = {}) {
  return await fetch(url, {
    method: "POST",
    mode: "cors",
    cache: "no-cache",
    credentials: "same-origin",
    headers: headerData,
    redirect: "follow",
    referrerPolicy: "no-referrer",
    body: bodyData,
  });
}

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isAdmin: false,
      isLoggedIn: false,
      wantsToLogIn: false,
      isWorkspaceOwner: false,
      isChannelOwner: false,
      firstLoad: true,
      isConversation: false,
      wantsToGoDashboard: false,
      wantsToGoManageRoles: false,
      wantsToGoManageWorkspaces: false,
      wantsToGoStatistics: false,
      email: "",
      displayName: "",
      token: null,
      uid: null,
      registerErrorMsg: "",
      loginErrorMsg: "",
      workspaces: [],
      channels: [],
      messages: [],
      users: [],
      globalUsers: [],
      notifications: [],
      selectedWorkspace: null,
      selectedChannel: null,
      selectedChannelName: "",
      selectedUser: null,
      language: "en",
    };

    this.handleUnsubscribeChannel = this.handleUnsubscribeChannel.bind(this);
    this.handleDeleteWorkspace = this.handleDeleteWorkspace.bind(this);
    this.handleDeleteChannel = this.handleDeleteChannel.bind(this);
    this.handleSubscribeChannel = this.handleSubscribeChannel.bind(this);
    this.handleOwnSubscribeChannel = this.handleOwnSubscribeChannel.bind(this);
    this.handleSubscribeWorkspace = this.handleSubscribeWorkspace.bind(this);
    this.handleUnsubscribe = this.handleUnsubscribe.bind(this);
    this.handleSignUp = this.handleSignUp.bind(this);
    this.handleLogin = this.handleLogin.bind(this);
    this.handleChangeRole = this.handleChangeRole.bind(this);
    this.handleAddChannel = this.handleAddChannel.bind(this);

    this.logout = this.logout.bind(this);

    this.setWorkspaceOwner = this.setWorkspaceOwner.bind(this);
    this.setChannelOwner = this.setChannelOwner.bind(this);

    this.loadGlobalUsers = this.loadGlobalUsers.bind(this);
    this.setGlobalUsers = this.setGlobalUsers.bind(this);
    this.loadAvailableUsers = this.loadAvailableUsers.bind(this);

    this.setWorkspace = this.setWorkspace.bind(this);
    this.setChannel = this.setChannel.bind(this);
    this.setUserWorkspace = this.setUserWorkspace.bind(this);

    this.addWorkspace = this.addWorkspace.bind(this);
    this.addChannel = this.addChannel.bind(this);

    this.removeChannel = this.removeChannel.bind(this);
    this.handleRemoveChannel = this.handleRemoveChannel.bind(this);

    this.removeWorkspace = this.removeWorkspace.bind(this);
    this.handleRemoveWorkspace = this.handleRemoveWorkspace.bind(this);

    this.loadData = this.loadData.bind(this);
    this.loadWorkspace = this.loadWorkspace.bind(this);
    this.loadChannel = this.loadChannel.bind(this);
    this.loadMessage = this.loadMessage.bind(this);
    this.loadUsers = this.loadUsers.bind(this);

    this.sendMessage = this.sendMessage.bind(this);
    this.createConversation = this.createConversation.bind(this);
    this.loadConversation = this.loadConversation.bind(this);
    this.findConversation = this.findConversation.bind(this);
    this.loadAllWorkspace = this.loadAllWorkspace.bind(this);
    this.loadAllChannel = this.loadAllChannel.bind(this);
    this.addNotification = this.addNotification.bind(this);
    this.findConversationId = this.findConversationId.bind(this);
    this.clearNotifications = this.clearNotifications.bind(this);
    this.handleChangeLanguage = this.handleChangeLanguage.bind(this);
    this.verifyUserBelongChannel = this.verifyUserBelongChannel.bind(this);
  }

  loadAllChannel() {
    var reStatus;
    var headers = simpleHeaders();
    headers.append("email", this.state.email);
    headers.append("token", this.state.token);

    getDataWithHeaders(channelsUrl, headers)
      .then((response) => {
        reStatus = response.status;
        return response.json();
      })
      .then((data) => {
        if (reStatus != 200) {
          throw new Error(reStatus + " : " + data.msg);
        }
        if (Array.isArray(data) && data.length) {
          this.setState({
            channels: data,
          });
        }
      })
      .catch((error) => {
        console.error("loadAllChannel > " + error);
      });
  }

  removeChannel(channelId, workspaceId) {
    this.setState(
      {
        selectedChannel: channelId,
        selectedWorkspace: workspaceId,
      },
      () => this.handleRemoveChannel()
    );
  }

  handleAddChannel(channelName, workspaceId) {
    var headers = formHeaders();
    headers.append("email", this.state.email);
    headers.append("token", this.state.token);

    var reStatus;
    var urlencoded = new URLSearchParams();
    urlencoded.append("title", channelName);

    // chamar método: e envio objecto Dto
    postForm(
      workspacesUrl + "/" + workspaceId + "/channels",
      headers,
      urlencoded
    )
      .then((response) => {
        reStatus = response.status;
        return response.json();
      })
      .then((data) => {
        if (reStatus != 200) {
          throw new Error(`${reStatus} : ${data.msg}`);
        }
        this.loadAllChannel();
      })
      .catch((error) => {
        console.error("Create Channel > " + error);
      });
  }

  handleRemoveChannel() {
    var headers = formHeaders();
    headers.append("email", this.state.email);
    headers.append("token", this.state.token);
    var reStatus;

    deleteData(
      workspacesUrl +
        "/" +
        this.state.selectedWorkspace +
        "/channels/" +
        this.state.selectedChannel,
      headers,
      ""
    )
      .then((response) => {
        reStatus = response.status;
        return response.json();
      })
      .then((data) => {
        if (reStatus != 200) {
          throw new Error(reStatus + " : " + data.msg);
        }
        this.loadAllChannel();
      })
      .catch((error) => {
        console.error("handleRemoveChannel > " + error);
      });
  }

  loadAllWorkspace() {
    var reStatus;
    var headers = simpleHeaders();
    headers.append("email", this.state.email);
    headers.append("token", this.state.token);

    getDataWithHeaders(workspacesUrl + "/stats", headers)
      .then((response) => {
        reStatus = response.status;
        return response.json();
      })
      .then((data) => {
        if (reStatus != 200) {
          throw new Error(reStatus + " : " + data.msg);
        }
        if (Array.isArray(data) && data.length) {
          this.setState(
            {
              workspaces: data,
            },
            () => this.loadAllChannel()
          );
        }
      })
      .catch((error) => {
        console.error("loadAllWorkspace > " + error);
      });
  }

  removeWorkspace(workspaceId) {
    this.setState(
      {
        selectedWorkspace: workspaceId,
      },
      () => this.handleRemoveWorkspace()
    );
  }

  handleRemoveWorkspace() {
    var headers = formHeaders();
    headers.append("email", this.state.email);
    headers.append("token", this.state.token);
    var reStatus;

    deleteData(workspacesUrl + "/" + this.state.selectedWorkspace, headers, "")
      .then((response) => {
        reStatus = response.status;
        return response.json();
      })
      .then((data) => {
        if (reStatus != 200) {
          throw new Error(reStatus + " : " + data.msg);
        }
        this.loadAllWorkspace();
      })
      .catch((error) => {
        console.error("handleRemoveWorkspace > " + error);
      });
  }

  loadData() {
    // chama workspaces consoante o userId
    //if (status == 200) {
    // passa para o array
    // Para o selected workspace é o primeiro elemento
    // const workspaces
    //const selectedWorkspace =
    // se vier vazio é passo um {};
    // this.setState({
    //   workspaces,
    //   selectedWorkspace:
    // })
    // apresentar  os channels e os users associados ao workspace
    // query para retornar channels e users consoante o selectworkspace
    // }
    // }
  }

  handleChangeRole({ emailUser }) {
    var headers = formHeaders();
    headers.append("email", this.state.email);
    headers.append("token", this.state.token);
    var reStatus;
    var selectedIdUser = null;
    var flagRole = false;

    for (var i = 0; i < this.state.globalUsers.length; i++) {
      if (this.state.globalUsers[i].email == emailUser) {
        selectedIdUser = this.state.globalUsers[i].id;
        flagRole = this.state.globalUsers[i].admin;
      }
    }

    putData(
      usersUrl + "/" + selectedIdUser + "?admin=" + !flagRole,
      headers,
      ""
    )
      .then((response) => {
        reStatus = response.status;
        return response.json();
      })
      .then((data) => {
        if (reStatus != 200) {
          throw new Error(reStatus + " : " + data.msg);
        }
        this.loadRoleUsers();
      })
      .catch((error) => {
        console.error("handleChangeRole > " + error);
      });
  }

  setGlobalUsers() {
    var workspaceUsers = this.state.users;
    var globalUsers = this.state.globalUsers;

    var filteredUsers = globalUsers.filter(function (objFromGlobal) {
      return !workspaceUsers.find(function (objFromWorkspace) {
        return objFromGlobal.id === objFromWorkspace.id;
      });
    });

    this.setState({
      globalUsers: filteredUsers,
    });
  }

  loadRoleUsers() {
    var reStatus;
    var headers = simpleHeaders();
    headers.append("email", this.state.email);
    headers.append("token", this.state.token);

    getDataWithHeaders(usersUrl, headers)
      .then((response) => {
        reStatus = response.status;
        return response.json();
      })
      .then((data) => {
        if (reStatus != 200) {
          this.setState({
            globalUsers: [],
          });
          throw new Error(reStatus + " : " + data.msg);
        }
        if (Array.isArray(data) && data.length) {
          var index = null;
          for (var i = 0; i < this.state.globalUsers.length; i++) {
            if (this.state.globalUsers[i].id == this.state.uid) {
              index = i;
            }
          }
          data.splice(index, 1);

          this.setState({
            globalUsers: data,
          });
        }
      })
      .catch((error) => {
        console.error("loadRoleUsers > " + error);
      });
  }

  loadGlobalUsers() {
    var reStatus;
    var headers = simpleHeaders();
    headers.append("email", this.state.email);
    headers.append("token", this.state.token);

    getDataWithHeaders(usersUrl + "?isAdmin=false", headers)
      .then((response) => {
        reStatus = response.status;
        return response.json();
      })
      .then((data) => {
        if (reStatus != 200) {
          this.setState({
            globalUsers: [],
          });
          throw new Error(reStatus + " : " + data.msg);
        }
        if (Array.isArray(data) && data.length) {
          this.setState(
            {
              globalUsers: data,
            },
            () => this.setGlobalUsers()
          );
        }
      })
      .catch((error) => {
        console.error("loadGlobalUsers > " + error);
      });
  }

  loadAvailableUsers() {
    var workspaceUsers = this.state.users;
    var channelUsers = [];

    for (var i = 0; i < this.state.channels.length; i++) {
      if (this.state.channels[i].id == this.state.selectedChannel) {
        channelUsers = this.state.channels[i].users;
      }
    }
    var filteredUsers = workspaceUsers.filter(function (objFromWorkspace) {
      return !channelUsers.find(function (objFromChannel) {
        return objFromWorkspace.id === objFromChannel.id;
      });
    });

    this.setState({
      globalUsers: filteredUsers,
    });
  }

  loadUsers(workspaceId) {
    var reStatus;
    var headers = simpleHeaders();
    headers.append("email", this.state.email);
    headers.append("token", this.state.token);

    getDataWithHeaders(workspacesUrl + "/" + workspaceId + "/users", headers)
      .then((response) => {
        reStatus = response.status;
        return response.json();
      })
      .then((data) => {
        if (reStatus != 200) {
          this.setState({
            users: [],
            selectedUser: null,
          });
          throw new Error(reStatus + " : " + data.msg);
        }
        if (Array.isArray(data) && data.length) {
          this.setState({
            users: data,
          });
        }
      })
      .catch((error) => {
        console.error("loadUsersWorkpace > " + error);
      });
  }

  loadChannel(workspaceId) {
    var reStatus;
    var headers = simpleHeaders();
    headers.append("email", this.state.email);
    headers.append("token", this.state.token);

    getDataWithHeaders(workspacesUrl + "/" + workspaceId + "/channels", headers)
      .then((response) => {
        reStatus = response.status;
        return response.json();
      })
      .then((data) => {
        if (reStatus != 200) {
          this.setState({
            channels: [],
            selectedChannel: null,
            isConversation: false,
          });
          throw new Error(reStatus + " : " + data.msg);
        }
        if (Array.isArray(data) && data.length) {
          this.setState({
            channels: data,
            isConversation: false,
          });
        }
      })
      .catch((error) => {
        console.error("loadChannel > " + error);
      });
  }

  loadWorkspace() {
    var reStatus;
    var headers = simpleHeaders();
    headers.append("email", this.state.email);
    headers.append("token", this.state.token);

    getDataWithHeaders(
      workspacesUrl + "?userId=" + this.state.uid + "&isActive=true",
      headers
    )
      .then((response) => {
        reStatus = response.status;
        return response.json();
      })
      .then((data) => {
        if (reStatus != 200) {
          throw new Error(reStatus + " : " + data.msg);
        }
        if (Array.isArray(data) && data.length) {
          this.setState(
            {
              workspaces: data,
            },
            () => this.setFirstWorkspace()
          );
        }
      })
      .catch((error) => {
        console.error("loadWorkspace > " + error);
      });
  }

  setFirstWorkspace() {
    const firstWorkspace = this.state.workspaces[0];
    if (this.state.firstLoad && this.state.workspaces.length > 0) {
      this.setWorkspace(firstWorkspace.id);
    }
    this.setState({
      firstLoad: false,
    });
  }

  loadMessage(channelId) {
    var reStatus;
    var headers = simpleHeaders();
    headers.append("email", this.state.email);
    headers.append("token", this.state.token);

    getDataWithHeaders(channelsUrl + "/" + channelId + "/messages", headers)
      .then((response) => {
        reStatus = response.status;
        return response.json();
      })
      .then((data) => {
        if (reStatus != 200) {
          this.setState({
            messages: [],
          });
          throw new Error(reStatus + " : " + data.msg);
        }

        if (Array.isArray(data) && data.length) {
          this.setState({
            messages: data,
          });
        }
      })
      .catch((error) => {
        console.error("loadMessage > " + error);
      });
  }

  findConversation(userId, userName) {
    var reStatus;
    var headers = simpleHeaders();
    headers.append("email", this.state.email);
    headers.append("token", this.state.token);

    getDataWithHeaders(
      workspacesUrl +
        "/" +
        this.state.selectedWorkspace +
        "/conversation?otherUserId=" +
        userId,
      headers
    )
      .then((response) => {
        reStatus = response.status;
        return response.json();
      })
      .then((data) => {
        if (reStatus != 200) {
          this.createConversation(userId, userName);
          throw new Error(reStatus + " : " + data.msg);
        }
        console.log("Success find conversation");
        this.setState({
          selectedChannel: data.id,
          selectedChannelName: userName,
          isConversation: true,
        });
        this.loadConversation(userId, userName);
      })
      .catch((error) => {
        console.error("Load Conversation > " + error);
      });
  }

  createConversation(userId, userName) {
    var reStatus;
    var headers = formHeaders();
    headers.append("email", this.state.email);
    headers.append("token", this.state.token);

    var urlencoded = new URLSearchParams();
    urlencoded.append("userId", userId);

    postForm(
      workspacesUrl + "/" + this.state.selectedWorkspace + "/channels",
      headers,
      urlencoded
    )
      .then((resposta) => {
        reStatus = resposta.status;
        return resposta.json();
      })
      .then((data) => {
        if (reStatus != 200) {
          throw new Error(`${reStatus} : ${data.msg}`);
        }

        console.log("Sucess create Conversation");

        this.setState({
          messages: [],
          selectedUser: userId,
          selectedChannel: data.id,
          selectedChannelName: userName,
          isConversation: true,
        });
      })
      .catch((error) => {
        console.error("Create Conversation > " + error);
      });
  }

  loadConversation(userId, userName) {
    var reStatus;
    var headers = simpleHeaders();
    headers.append("email", this.state.email);
    headers.append("token", this.state.token);

    getDataWithHeaders(
      channelsUrl +
        "/" +
        this.state.selectedWorkspace +
        "/conversation?userId=" +
        this.state.uid +
        "&otherUserId=" +
        userId,
      headers
    )
      .then((response) => {
        reStatus = response.status;
        return response.json();
      })
      .then((data) => {
        if (reStatus != 200) {
          console.log("No records found load conversation");

          this.setState({
            messages: [],
            selectedUser: userId,
            selectedChannelName: userName,
            isConversation: true,
          });
          throw new Error(reStatus + " : " + data.msg);
        }

        console.log("Success load conversation");
        this.setState({
          messages: data,
          selectedUser: userId,
          selectedChannelName: userName,
          isConversation: true,
        });
      })
      .catch((error) => {
        console.error("Load Conversation > " + error);
      });
  }

  handleSubscribeWorkspace({ emailUser }) {
    var headers = formHeaders();
    headers.append("email", this.state.email);
    headers.append("token", this.state.token);
    var reStatus;
    var selectedIdUser = null;
    for (var i = 0; i < this.state.globalUsers.length; i++) {
      if (this.state.globalUsers[i].email == emailUser) {
        selectedIdUser = this.state.globalUsers[i].id;
      }
    }

    putData(
      workspacesUrl +
        "/" +
        this.state.selectedWorkspace +
        "?userId=" +
        selectedIdUser,
      headers,
      ""
    )
      .then((response) => {
        reStatus = response.status;
        return response.json();
      })
      .then((data) => {
        if (reStatus != 200) {
          throw new Error(reStatus + " : " + data.msg);
        }
        this.setWorkspace(this.state.selectedWorkspace);
      })
      .catch((error) => {
        console.error("SubscribeWorkspace > " + error);
      });
  }

  handleSubscribeChannel({ emailUser }) {
    var headers = formHeaders();
    headers.append("email", this.state.email);
    headers.append("token", this.state.token);
    var reStatus;
    var selectedIdUser = null;

    for (var i = 0; i < this.state.globalUsers.length; i++) {
      if (this.state.globalUsers[i].email == emailUser) {
        selectedIdUser = this.state.globalUsers[i].id;
      }
    }

    putData(
      workspacesUrl +
        "/" +
        this.state.selectedWorkspace +
        "/channels/" +
        this.state.selectedChannel +
        "?userId=" +
        selectedIdUser,
      headers,
      ""
    )
      .then((response) => {
        reStatus = response.status;
        return response.json();
      })
      .then((data) => {
        if (reStatus != 200) {
          throw new Error(reStatus + " : " + data.msg);
        }
        this.setWorkspace(this.state.selectedWorkspace);
      })
      .catch((error) => {
        console.error("SubscribeChannel > " + error);
      });
  }

  handleOwnSubscribeChannel(channelId) {
    var headers = formHeaders();
    headers.append("email", this.state.email);
    headers.append("token", this.state.token);
    var reStatus;

    putData(
      workspacesUrl +
        "/" +
        this.state.selectedWorkspace +
        "/channels/" +
        channelId +
        "?userId=" +
        this.state.uid,
      headers,
      ""
    )
      .then((response) => {
        reStatus = response.status;
        return response.json();
      })
      .then((data) => {
        if (reStatus != 200) {
          throw new Error(reStatus + " : " + data.msg);
        }
        this.setWorkspace(this.state.selectedWorkspace);
      })
      .catch((error) => {
        console.error("handleOwnSubscribeChannel > " + error);
      });
  }

  handleUnsubscribe() {
    var headers = formHeaders();
    headers.append("email", this.state.email);
    headers.append("token", this.state.token);
    var reStatus;

    putData(workspacesUrl + "/" + this.state.selectedWorkspace, headers, "")
      .then((response) => {
        reStatus = response.status;
        return response.json();
      })
      .then((data) => {
        if (reStatus != 200) {
          throw new Error(reStatus + " : " + data.msg);
        }
        this.setState(
          {
            workspaces: [],
            channels: [],
            messages: [],
            users: [],
            selectedWorkspace: null,
            selectedChannel: null,
            selectedChannelName: "",
            selectedUser: null,
            isConversation: false,
            firstLoad: true,
          },
          () => this.loadWorkspace()
        );
      })
      .catch((error) => {
        console.error("Unsubscribe > " + error);
      });
  }

  handleUnsubscribeChannel() {
    var headers = formHeaders();
    headers.append("email", this.state.email);
    headers.append("token", this.state.token);
    var reStatus;

    putData(
      workspacesUrl +
        "/" +
        this.state.selectedWorkspace +
        "/channels/" +
        this.state.selectedChannel,
      headers,
      ""
    )
      .then((response) => {
        reStatus = response.status;
        return response.json();
      })
      .then((data) => {
        if (reStatus != 200) {
          throw new Error(reStatus + " : " + data.msg);
        }
        this.setState(
          {
            workspaces: [],
            channels: [],
            messages: [],
            users: [],
            selectedWorkspace: null,
            selectedChannel: null,
            selectedChannelName: "",
            selectedUser: null,
            isConversation: false,
            firstLoad: true,
          },
          () => this.loadWorkspace()
        );
      })
      .catch((error) => {
        console.error("Unsubscribe Channel > " + error);
      });
  }

  handleDeleteWorkspace() {
    var headers = formHeaders();
    headers.append("email", this.state.email);
    headers.append("token", this.state.token);
    var reStatus;

    deleteData(workspacesUrl + "/" + this.state.selectedWorkspace, headers, "")
      .then((response) => {
        reStatus = response.status;
        return response.json();
      })
      .then((data) => {
        if (reStatus != 200) {
          throw new Error(reStatus + " : " + data.msg);
        }
        this.setState({
          workspaces: [],
          channels: [],
          messages: [],
          users: [],
          selectedWorkspace: null,
          selectedChannel: null,
          selectedChannelName: "",
          selectedUser: null,
          isConversation: false,
        });
        this.loadChannel(this.state.selectedWorkspace);
      })
      .catch((error) => {
        console.error("handleDeleteChannel > " + error);
      });
  }

  handleDeleteChannel() {
    var headers = formHeaders();
    headers.append("email", this.state.email);
    headers.append("token", this.state.token);
    var reStatus;
    deleteData(
      workspacesUrl +
        "/" +
        this.state.selectedWorkspace +
        "/channels/" +
        this.state.selectedChannel,
      headers,
      ""
    )
      .then((response) => {
        reStatus = response.status;
        return response.json();
      })
      .then((data) => {
        if (reStatus != 200) {
          throw new Error(reStatus + " : " + data.msg);
        }
        this.setState({
          channels: [],
          selectedWorkspace: null,
        });
        this.loadChannel(this.state.selectedWorkspace);
      })
      .catch((error) => {
        console.error("handleDeleteChannel > " + error);
      });
  }

  handleSignUp({ name, email, password }) {
    var reStatus;
    var headers = simpleHeaders();

    var userDto = {
      name: name,
      email: email,
      password: password,
    };

    postData(usersUrl, headers, userDto)
      .then((response) => {
        reStatus = response.status;
        return response.json();
      })
      .then((data) => {
        if (reStatus != 200) {
          if (reStatus == 409) {
            this.setState({ registerErrorMsg: "User already exist." });
          } else {
            this.setState({ registerErrorMsg: data.msg });
          }
          throw new Error(reStatus + " : " + data.msg);
        }
        this.setState({ wantsToLogIn: true });
      })
      .catch((error) => {
        console.error("Register > " + error);
      });
  }

  handleLogin({ email, password }) {
    var reStatus;
    var headers = simpleHeaders();
    headers.append("email", email);
    headers.append("password", password);

    postData(usersUrl.concat("/login"), headers, {})
      .then((response) => {
        reStatus = response.status;
        response.json().then((data) => {
          if (reStatus != 200) {
            this.setState({ loginErrorMsg: data.msg });
            throw new Error(reStatus + " : " + data.msg);
          }
          this.setState({
            isLoggedIn: true,
            isAdmin: data.admin,
            email,
            token: data.token,
            uid: data.id,
            displayName: data.name,
          });
          this.loadWorkspace();
        });
      })
      .catch((error) => {
        console.error("Login > " + error);
      });
  }

  logout() {
    var reStatus;
    var headers = simpleHeaders();

    headers.append("email", this.state.email);
    headers.append("token", this.state.token);

    postData(usersUrl.concat("/logout"), headers, {})
      .then((response) => {
        reStatus = response.status;
        return response.json();
      })
      .then((data) => {
        if (reStatus != 200) {
          this.setState({ loginErrorMsg: data.msg });
          throw new Error(reStatus + " : " + data.msg);
        }
        this.setState({
          isAdmin: false,
          isLoggedIn: false,
          wantsToLogIn: false,
          isWorkspaceOwner: false,
          isChannelOwner: false,
          firstLoad: true,
          isConversation: false,
          wantsToGoDashboard: false,
          wantsToGoManageRoles: false,
          wantsToGoManageWorkspaces: false,
          wantsToGoStatistics: false,
          email: "",
          displayName: "",
          token: null,
          uid: null,
          registerErrorMsg: "",
          loginErrorMsg: "",
          workspaces: [],
          channels: [],
          messages: [],
          users: [],
          globalUsers: [],
          notifications: [],
          selectedWorkspace: null,
          selectedChannel: null,
          selectedChannelName: "",
          selectedUser: null,
        });
      })
      .catch((error) => {
        console.error("Logout > " + error);
      });
  }

  setWorkspace(id) {
    this.loadWorkspace();
    this.loadChannel(id);
    this.loadUsers(id);
    this.setState(
      {
        selectedWorkspace: id,
      },
      () => this.setWorkspaceOwner()
    );
  }

  setWorkspaceOwner() {
    this.setState({ isWorkspaceOwner: false });
    for (var i = 0; i < this.state.workspaces.length; i++) {
      if (this.state.workspaces[i].id == this.state.selectedWorkspace) {
        if (this.state.workspaces[i].user.id == this.state.uid) {
          this.setState({ isWorkspaceOwner: true });
          break;
        }
      }
    }
  }

  setChannelOwner() {
    this.setState({ isChannelOwner: false });
    for (var i = 0; i < this.state.channels.length; i++) {
      if (this.state.channels[i].id == this.state.selectedChannel) {
        if (this.state.channels[i].user.id == this.state.uid) {
          this.setState({ isChannelOwner: true });
          break;
        }
      }
    }
  }

  verifyUserBelongChannel(channelId) {
    for (var i = 0; i < this.state.channels.length; i++) {
      if (this.state.channels[i].id == channelId) {
        let index = this.state.channels[i].users.findIndex(
          (user) => user.id == this.state.uid
        );

        if (index !== -1) {
          return true;
        } else {
          return false;
        }
      }
    }
  }

  setChannel(id, title) {
    this.clearNotifications();

    this.setState(
      {
        selectedChannel: id,
        selectedChannelName: title,
        selectedUser: null,
        isConversation: false,
      },
      () => this.setChannelOwner()
    );
    this.loadMessage(id);

    var belongToChannel = this.verifyUserBelongChannel(id);
    if (!belongToChannel) {
      this.handleOwnSubscribeChannel(id);
    }
  }

  setUserWorkspace(id, name) {
    this.findConversation(id, name);
  }

  addWorkspace(workspaceName) {
    var headers = formHeaders();
    headers.append("email", this.state.email);
    headers.append("token", this.state.token);

    var reStatus;
    var urlencoded = new URLSearchParams();
    urlencoded.append("title", workspaceName);

    postForm(workspacesUrl, headers, urlencoded)
      .then((response) => {
        reStatus = response.status;
        return response.json();
      })
      .then((data) => {
        if (reStatus != 200) {
          throw new Error(`${reStatus} : ${data.msg}`);
        }
        var workspaces = this.state.workspaces;
        workspaces.push(data);
        this.setState(
          {
            workspaces: workspaces,
          },
          () => this.setFirstWorkspace()
        );
      })
      .catch((error) => {
        console.error("Create Workspace > " + error);
      });
  }

  addChannel(channelName) {
    var headers = formHeaders();
    headers.append("email", this.state.email);
    headers.append("token", this.state.token);

    var reStatus;
    var urlencoded = new URLSearchParams();
    urlencoded.append("title", channelName);

    // chamar método: e envio objecto Dto
    postForm(
      workspacesUrl + "/" + this.state.selectedWorkspace + "/channels",
      headers,
      urlencoded
    )
      .then((response) => {
        reStatus = response.status;
        return response.json();
      })
      .then((data) => {
        if (reStatus != 200) {
          throw new Error(`${reStatus} : ${data.msg}`);
        }
        var channels = this.state.channels;
        channels.push(data);
        this.setState({
          channels: channels,
        });
      })
      .catch((error) => {
        console.error("Create Channel > " + error);
      });
  }

  sendMessage(message) {
    var reStatus;
    var headers = formHeaders();
    headers.append("email", this.state.email);
    headers.append("token", this.state.token);

    var urlencoded = new URLSearchParams();
    urlencoded.append("bodyMessage", message);

    postForm(
      channelsUrl + "/" + this.state.selectedChannel + "/messages",
      headers,
      urlencoded
    )
      .then((response) => {
        reStatus = response.status;
        return response.json();
      })
      .then((data) => {
        if (reStatus != 200) {
          throw new Error(`${reStatus} : ${data.msg}`);
        }
        this.loadMessage(this.state.selectedChannel);
      })
      .catch((error) => {
        console.error("Send Message > " + error);
      });
  }

  addNotification(message) {
    let lastTotal = 0;

    if (this.state.notifications.length > 0) {
      let index = this.state.notifications.findIndex(
        (notification) => notification.channelId == message.channelId
      );

      if (index !== -1) {
        if (message.channelId !== this.state.selectedChannel) {
          lastTotal = this.state.notifications[index].count;
          lastTotal++;
          this.state.notifications[index].count = lastTotal;
        }
      } else {
        this.state.notifications.push({
          channelId: message.channelId,
          count: 0,
        });
      }
    } else {
      this.state.notifications.push({
        channelId: message.channelId,
        count: 1,
      });
    }
    this.setState({ notifications: this.state.notifications });
  }

  clearNotifications() {
    let index = this.state.notifications.findIndex(
      (notification) => notification.channelId == this.state.selectedChannel
    );

    if (index !== -1) {
      let updatedNotifications = [...this.state.notifications];
      updatedNotifications[index].count = 0;
      this.setState({ notifications: updatedNotifications });
    }
  }

  findConversationId(userId) {
    var reStatus;
    var headers = simpleHeaders();
    headers.append("email", this.state.email);
    headers.append("token", this.state.token);

    getDataWithHeaders(
      workspacesUrl +
        "/" +
        this.state.selectedWorkspace +
        "/conversation?otherUserId=" +
        userId,
      headers
    )
      .then((response) => {
        reStatus = response.status;
        return response.json();
      })
      .then((data) => {
        if (reStatus != 200) {
          return 0;
        }
        return data.id;
      })
      .catch((error) => {
        console.error("Find Conversation Id > " + error);
      });
  }

  handleChangeLanguage(valueLanguage) {
    this.setState({ language: valueLanguage });
  }

  render() {
    translationStrings.setLanguage(this.state.language);

    return (
      <div>
        {this.state.isLoggedIn ? (
          <Grid columns="equal" className="app" style={{ background: "#eee" }}>
            <SideBarWorkspace
              onUnsubscribe={this.handleUnsubscribe}
              logout={this.logout}
              workspaces={this.state.workspaces}
              selectedWorkspace={this.state.selectedWorkspace}
              setWorkspace={this.setWorkspace}
              addWorkspace={this.addWorkspace}
              displayName={this.state.displayName}
              onDeleteWorkspace={this.handleDeleteWorkspace}
              isWorkspaceOwner={this.state.isWorkspaceOwner}
              isAdmin={this.state.isAdmin}
              wantsToGoDashboard={this.state.wantsToGoDashboard}
              goToDashboard={() => this.setState({ wantsToGoDashboard: true })}
              goToChatPanel={() => this.setState({ wantsToGoDashboard: false })}
              uid={this.state.uid}
              language={this.state.language}
              onChangeLanguage={this.handleChangeLanguage}
              translationStrings={translationStrings}
            />
            {this.state.wantsToGoDashboard ? (
              <React.Fragment>
                <SideBarDashboard
                  goToManageRoles={() =>
                    this.setState(
                      {
                        wantsToGoManageRoles: true,
                        wantsToGoManageWorkspaces: false,
                        wantsToGoStatistics: false,
                      },
                      () => this.loadRoleUsers()
                    )
                  }
                  goToManageWorkspaces={() =>
                    this.setState(
                      {
                        wantsToGoManageRoles: false,
                        wantsToGoManageWorkspaces: true,
                        wantsToGoStatistics: false,
                      },
                      () => this.loadAllWorkspace()
                    )
                  }
                  goToStatistics={() =>
                    this.setState({
                      wantsToGoManageRoles: false,
                      wantsToGoManageWorkspaces: false,
                      wantsToGoStatistics: true,
                    })
                  }
                  translationStrings={translationStrings}
                />
                <DashboardPanel
                  wantsToGoManageRoles={this.state.wantsToGoManageRoles}
                  wantsToGoManageWorkspaces={
                    this.state.wantsToGoManageWorkspaces
                  }
                  wantsToGoStatistics={this.state.wantsToGoStatistics}
                  globalUsers={this.state.globalUsers}
                  onChangeRole={this.handleChangeRole}
                  workspaces={this.state.workspaces}
                  addWorkspace={this.addWorkspace}
                  removeWorkspace={this.removeWorkspace}
                  channels={this.state.channels}
                  removeChannel={this.removeChannel}
                  handleAddChannel={this.handleAddChannel}
                  translationStrings={translationStrings}
                />
              </React.Fragment>
            ) : (
              <React.Fragment>
                {" "}
                <SideBarChannel
                  channels={this.state.channels}
                  selectedChannel={this.state.selectedChannel}
                  setChannel={this.setChannel}
                  addChannel={this.addChannel}
                  users={this.state.users}
                  selectedUser={this.state.selectedUser}
                  setUserWorkspace={this.setUserWorkspace}
                  isChannelOwner={this.state.isChannelOwner}
                  isWorkspaceOwner={this.state.isWorkspaceOwner}
                  onAddUsersWorkspace={this.loadGlobalUsers}
                  onSubscribeWorkspace={this.handleSubscribeWorkspace}
                  globalUsers={this.state.globalUsers}
                  onDeleteChannel={this.handleDeleteChannel}
                  uid={this.state.uid}
                  notifications={this.state.notifications}
                  translationStrings={translationStrings}
                />
                <MainPanel>
                  <ChatPanel
                    messages={this.state.messages}
                    workspaceId={this.state.selectedWorkspace}
                    channelId={this.state.selectedChannel}
                    email={this.state.email}
                    token={this.state.token}
                    uid={this.state.uid}
                    selectedChannelName={this.state.selectedChannelName}
                    sendMessage={this.sendMessage}
                    handleSearchChange={this.handleSearchChange}
                    onAddUsersChannel={this.loadAvailableUsers}
                    onSubscribeChannel={this.handleSubscribeChannel}
                    globalUsers={this.state.globalUsers}
                    isConversation={this.state.isConversation}
                    onUnsubscribeChannel={this.handleUnsubscribeChannel}
                    translationStrings={translationStrings}
                  />
                  <Notification
                    isLoggedIn={this.state.isLoggedIn}
                    uid={this.state.uid}
                    addNotification={this.addNotification}
                    language={this.state.language}
                    workspaces={this.state.workspaces}
                    channels={this.state.channels}
                    translationStrings={translationStrings}
                  />
                </MainPanel>
              </React.Fragment>
            )}
          </Grid>
        ) : (
          <React.Fragment>
            {this.state.wantsToLogIn ? (
              <LoginForm
                onLogin={this.handleLogin}
                goToSignUp={() => this.setState({ wantsToLogIn: false })}
              />
            ) : (
              <SignUpForm
                onSignUp={this.handleSignUp}
                goToLogin={() => this.setState({ wantsToLogIn: true })}
              />
            )}
          </React.Fragment>
        )}
      </div>
    );
  }
}

export default App;
