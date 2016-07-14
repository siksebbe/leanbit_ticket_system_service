'use strict';

var app = angular.module('leanbitSupportTicketSystemApp')
app.controller('WorkitemCtrl', function ($scope, $location, workitemDb, usersDb, tokenDb) {
  $scope.categories = WorkItemCategories;
  $scope.users = [];
  $scope.work = WorkItem('', '', '', 0, null, 0);
  $scope.update;

  function onError(res) {
    console.log('Error', res.data);
  }

  function getUsers() {
    usersDb.getAll()
      .then(function (res) {
        $scope.users = res.data;
        console.dir($scope.users);
      }, onError);
  }

  getUsers();

  function checkToken() {
    var token = window.localStorage['token'];

    tokenDb.checkToken(token)
      .then(function (res) {
        getWorkItems();
      }, function () {
        $location.path('/login');
        $location.replace();
      })
  }

  function getWorkItems() {
    workitemDb.getAll()
      .then(function (res) {
        $scope.workItems = res.data;
      }, onError);
  }

  function updateWoritem(workitem) {
    workitemDb.update(workitem);
    $scope.update = false;
  }

  function updateUser(user) {
    usersDb.updateUser(user);
  }

  checkToken();

  $scope.getWorkitemUsers = function (workItemName, data) {
    var workItemUsers = [];
    for (var i = 0; i < $scope.users.length; i++) {
      for (var item in $scope.users[i].workItems) {

        if ($scope.users[i].workItems[item].name === workItemName) {
          workItemUsers.push($scope.users[i]);
        }
      }
    }
    return workItemUsers;
  }


  function createOptions(listName) {
    var _listName = listName;
    var options = {
      placeholder: "app",
      connectWith: ".apps-container",

      stop: function (event, ui) {
        var category = ui.item.parent().attr('id');
        var index = ui.item.attr('id');
        $scope.workItems[index].status = category;
        updateWoritem($scope.workItems[index]);
      }
    };
    return options;
  }

  $scope.addNewWorkItemPopup = function (category, name) {
    $scope.selectedCategory = category;
    $scope.update = false;
    for (var i = 0; i < $scope.categories.length; i++) {
      if ($scope.categories[i] == category) {
        var selectOptions = document.getElementById("workitemStatusSelection");
        selectOptions.options[i + 1].selected = true;
      }
    }
    document.getElementById('popup').style.display = 'block';
  }
  $scope.closeAddNewWorkItemPopup = function () {
    document.getElementById('popup').style.display = 'none';
    $location.replace();
  }

  $scope.modifyWorkItemPopup = function (workitem) {
    $scope.update = true;
    $scope.selectedCategory = workitem.status;
    for (var i = 0; i < $scope.categories.length; i++) {
      if ($scope.categories[i] == $scope.selectedCategory) {
        var selectOptions = document.getElementById("workitemStatusSelection");
        selectOptions.options[i + 1].selected = true;
      }
    }

    var userIndex = 0;
    for (var i = 0; i < $scope.users.length; i++) {
      for (var item in $scope.users[i].workItems) {

        if ($scope.users[i].workItems[item].name === name) {
          userIndex = i;
        }
      }
    }
    var selectOptions = document.getElementById("useritemUserSelection");
    selectOptions.options[userIndex + 1].selected = true;

    $scope.work.name = workitem.name;
    document.getElementById("workitemName").value = workitem.name;

    $scope.work.description = workitem.description;
    document.getElementById("workitemDescription").value = workitem.description;

    $scope.work.priority = workitem.priority;
    document.getElementById("workitemPriority").value = workitem.priority;

    document.getElementById('popup').style.display = 'block';
  }

  $scope.deleteWorkItemButton = function (workitem) {

    var userIndex = 0;
    for (var i = 0; i < $scope.users.length; i++) {
      for (var item in $scope.users[i].workItems) {

        if ($scope.users[i].workItems[item].name === name) {
          userIndex = i;
        }
      }
    }

    $scope.users[userIndex].workItem = null;
    workitemDb.remove(workitem.name)
      .then(function () {
        getWorkItems();
      });
  }

  $scope.createWorkItem = function () {
    if ($scope.work.status === "") {
      $scope.work.status = $scope.selectedCategory;
    }
    console.log($scope.update);
    if ($scope.update === true) {
      updateWoritem($scope.work);
      var selectOptions = document.getElementById("useritemUserSelection");
      for (var i = 0; i < selectOptions.options.length; i++) {
        if (selectOptions.options[i].selected === true) {
          workitemDb.addToUser($scope.work, $scope.users[i].userID);
        }
      }
      $scope.update = false;
    } else {
      workitemDb.add($scope.work)
        .then(function () {
          var selectOptions = document.getElementById("useritemUserSelection");
          for (var i = 0; i < selectOptions.options.length; i++) {
            if (selectOptions.options[i].selected === true) {
              workitemDb.addToUser($scope.work, $scope.users[i].userID);
            }
          }
          $scope.workItems.push($scope.work);
        });
    }
  }

  $scope.sortableOptionsList = {};
  for (var i = 0; i < WorkItemCategories.length; i++) {
    $scope.sortableOptionsList[WorkItemCategories[i]] = createOptions(WorkItemCategories[i]);
  }
});