"use strict";function UIWorkItem(){var a={};return a.name="",a.description="",a.status="",a.priority=0,a.issue=null,a.id=0,a.userID=0,a}function UIWorkItem(a,b){return a.userID=b,a}function UIWorkItem(a,b,c,d,e,f,g){var h={};return h.name=a,h.description=b,h.status=c,h.priority=d,h.issue=e,h.id=f,h.userID=g,h}function WorkItem(){var a={};return a.name="",a.description="",a.status="",a.priority=0,a.issue=null,a.id=0,a}function WorkItem(a,b,c,d,e,f){var g={};return g.name=a,g.description=b,g.status=c,g.priority=d,g.issue=e,g.id=f,g}angular.module("leanbitSupportTicketSystemApp",["ngAnimate","ngResource","ngRoute","ngSanitize","ngTouch","ui.sortable"]).config(["$routeProvider",function(a){a.when("/main",{templateUrl:"views/main.html",controller:"MainCtrl",controllerAs:"main"}).when("/login",{templateUrl:"views/login.html",controller:"MainCtrl",controllerAs:"main"}).when("/newitem",{templateUrl:"views/newitem.html",controller:"MainCtrl",controllerAs:"main"}).otherwise({redirectTo:"/login"})}]),angular.module("leanbitSupportTicketSystemApp").controller("MainCtrl",function(){}),angular.module("leanbitSupportTicketSystemApp").controller("AboutCtrl",function(){this.awesomeThings=["HTML5 Boilerplate","AngularJS","Karma"]});var app=angular.module("leanbitSupportTicketSystemApp");app.controller("WorkitemCtrl",["$scope","$location","workitemDb","usersDb","tokenDb",function(a,b,c,d,e){function f(a){console.log("Error",a.data)}function g(){d.getAll().then(function(b){a.users=b.data,console.dir(a.users)},f)}function h(){var a=window.localStorage.token;e.checkToken(a).then(function(a){i()},function(){b.path("/login"),b.replace()})}function i(){c.getAll().then(function(b){a.workItems=b.data},f)}function j(b){c.update(b),a.update=!1}function k(b){var c={placeholder:"app",connectWith:".apps-container",stop:function(b,c){var d=c.item.parent().attr("id"),e=c.item.attr("id");a.workItems[e].status=d,j(a.workItems[e])}};return c}a.categories=WorkItemCategories,a.users=[],a.work=WorkItem("","","",0,null,0),a.update,g(),h(),a.getWorkitemUsers=function(b,c){for(var d=[],e=0;e<a.users.length;e++)for(var f in a.users[e].workItems)a.users[e].workItems[f].name===b&&d.push(a.users[e]);return d},a.addNewWorkItemPopup=function(b,c){a.selectedCategory=b,a.update=!1;for(var d=0;d<a.categories.length;d++)if(a.categories[d]==b){var e=document.getElementById("workitemStatusSelection");e.options[d+1].selected=!0}document.getElementById("popup").style.display="block"},a.closeAddNewWorkItemPopup=function(){document.getElementById("popup").style.display="none",b.replace()},a.modifyWorkItemPopup=function(b){a.update=!0,a.selectedCategory=b.status;for(var c=0;c<a.categories.length;c++)if(a.categories[c]==a.selectedCategory){var d=document.getElementById("workitemStatusSelection");d.options[c+1].selected=!0}for(var e=0,c=0;c<a.users.length;c++)for(var f in a.users[c].workItems)a.users[c].workItems[f].name===name&&(e=c);var d=document.getElementById("useritemUserSelection");d.options[e+1].selected=!0,a.work.name=b.name,document.getElementById("workitemName").value=b.name,a.work.description=b.description,document.getElementById("workitemDescription").value=b.description,a.work.priority=b.priority,document.getElementById("workitemPriority").value=b.priority,document.getElementById("popup").style.display="block"},a.deleteWorkItemButton=function(b){for(var d=0,e=0;e<a.users.length;e++)for(var f in a.users[e].workItems)a.users[e].workItems[f].name===name&&(d=e);a.users[d].workItem=null,c.remove(b.name).then(function(){i()})},a.createWorkItem=function(){if(""===a.work.status&&(a.work.status=a.selectedCategory),console.log(a.update),a.update===!0){j(a.work);for(var b=document.getElementById("useritemUserSelection"),d=0;d<b.options.length;d++)b.options[d].selected===!0&&c.addToUser(a.work,a.users[d].userID);a.update=!1}else c.add(a.work).then(function(){for(var b=document.getElementById("useritemUserSelection"),d=0;d<b.options.length;d++)b.options[d].selected===!0&&c.addToUser(a.work,a.users[d].userID);a.workItems.push(a.work)})},a.sortableOptionsList={};for(var l=0;l<WorkItemCategories.length;l++)a.sortableOptionsList[WorkItemCategories[l]]=k(WorkItemCategories[l])}]),angular.module("leanbitSupportTicketSystemApp").controller("LoginCtrl",["$scope","usersDb","$location",function(a,b,c){function d(b){alert("Username or password!"),console.log(a.token),console.log("Error",b)}a.userName,a.password,a.login=function(){b.login(a.userName,a.password).then(function(b){a.token=b.data,console.log(b),window.localStorage.token=a.token,c.path("/main"),c.replace()},d)},a.logout=function(){b.logout(window.localStorage.token),window.localStorage.token="",c.path("/login"),c.replace()}}]);var WorkItemCategories=["Backlog","Current","Testing","Working"];angular.module("leanbitSupportTicketSystemApp").factory("workitemDb",["$http",function(a){var b="http://localhost:8080/ticketsystem/workitem";return a.defaults.headers.common["X-Requested-With"],{getAll:function(){return a.get(b+"/getall")},add:function(c){return a.post(b,c)},remove:function(c){return a["delete"](b+"/"+c)},update:function(c){return a.put(b,c)},addToUser:function(c,d){return a.put(b+"/addtouser/"+d,c)}}}]),angular.module("leanbitSupportTicketSystemApp").factory("tokenDb",["$http",function(a){var b="http://localhost:8080/ticketsystem/token";return{checkToken:function(c){return a.get(b+"/"+c)}}}]),angular.module("leanbitSupportTicketSystemApp").factory("usersDb",["$http",function(a){var b="http://localhost:8080/ticketsystem/user";return{getAll:function(){return a.get(b+"/team/leanbit")},update:function(c){return a.put(b,c)},login:function(c,d){return a.get(b+"/login/"+c+"/"+d)},logout:function(c){return a["delete"](b+"/logout/"+c)}}}]),angular.module("leanbitSupportTicketSystemApp").run(["$templateCache",function(a){a.put("views/about.html","<p>This is the about view.</p>"),a.put("views/login.html",'<div class="col-md-6 col-md-offset-3" ng-controller="LoginCtrl">\n    <h2>Login</h2>\n    <form name="form" role="form" ng-submit="login();">\n        <div class="form-group" ng-class="{ \'has-error\': form.username.$dirty && form.username.$error.required }">\n            <label for="username">Username</label>\n            <input type="text" name="username" id="username" class="form-control" ng-model="userName" required />\n            <span ng-show="form.username.$dirty && form.username.$error.required" class="help-block">Username is required</span>\n        </div>\n        <div class="form-group" ng-class="{ \'has-error\': form.password.$dirty && form.password.$error.required }">\n            <label for="password">Password</label>\n            <input type="password" name="password" id="password" class="form-control" ng-model="password" required />\n            <span ng-show="form.password.$dirty && form.password.$error.required" class="help-block">Password is required</span>\n        </div>\n        <div class="form-actions">\n            <button type="submit" ng-disabled="form.$invalid" class="btn btn-primary">Login</\n        </div>\n    </form>\n</div>'),a.put("views/main.html",'<div class="center-content font-pen"> <h1 class="center-text">Leanbit Ticket system</h1> <br> <div class="row" ng-controller="LoginCtrl"> <button class="btn btn-primary" type="button" ng-click="logout()"> <h4>Logout</h4> </button> </div> <div class="row" ng-controller="WorkitemCtrl"> <div class="row color-white"> Team name: {{users[0].teamName}}<br> <div class="col-lg-4 color-white" ng-repeat="user in users"> User: {{user.firstName}} {{user.lastName}} </div> </div> <div ui-sortable> <div class="col-lg-3" ng-repeat="col in categories"> <h1 class="movable"> {{col}} <span class="glyphicon glyphicon-plus-sign clickable" ng-click="addNewWorkItemPopup(col)"></span> </h1> <div ui-sortable="sortableOptionsList[col]" id="{{col}}" class="apps-container"> <div id="{{$index}}" class="post-it-note" ng-repeat="workitem in workItems | filter:col:strict | orderBy: \'priority\'"> <br> <br> <div class="row"> <div class="col-lg-10"> <span class="glyphicon glyphicon-remove clickable" ng-click="deleteWorkItemButton({{workitem}})"></span> </div> <div class="col-lg-1"> <span class="glyphicon glyphicon-cog clickable" ng-click="modifyWorkItemPopup({{workitem}})"></span> </div> </div> <h2 class="inline">{{workitem.name}}</h2> <div class="row"> </div> <span class="center-text"> <p class="center-text" ng-repeat="user in getWorkitemUsers(workitem.name, users)"> {{user.userName}}: Authur<br> Prioritet: {{workitem.priority}} <br> <br> {{workitem.description}} </p> </span> </div> </div> </div> </div> <!--    <span class="input-group-addon" id="basic-addon1">@</span>\n  <input type="text" class="form-control" placeholder="Username" aria-describedby="basic-addon1"> --> <div id="popup" class="popup-content" ng-controller="WorkitemCtrl"> <div class="row"> <div class="col-lg-3"> <span class="clickable" ng-click="closeAddNewWorkItemPopup();">Close</span> </div> <div class="col-lg-8"> Add new work item </div> </div> <!-- Workitems input --> <form name="inputForm" class="center" role="form" ng-submit="createWorkItem();closeAddNewWorkItemPopup();"> <div class="row"> <div class="input-group col-xs-12"> <span class="input-group-addon form-text-description-size">Name</span> <input name="name" class="form-control" type="text" ng-model="work.name" placeholder="Name" id="workitemName" required> </div> </div> <div class="row"> <div class="input-group col-xs-12"> <span class="input-group-addon form-text-description-size">Descripton</span> <textarea name="description" class="form-control" rows="5" ng-model="work.description" id="workitemDescription" placeholder="Description" required>\n        </div>\n      </div>\n      <div class="row">\n        <div class="input-group col-xs-12">\n          <span class="input-group-addon form-text-description-size">Status</span>\n          <select type="text" ng-model="work.status" class="form-control" id="workitemStatusSelection">\n              <option ng-repeat="col in categories" value="{{col}}">{{col}}</option>\n          </select>\n        </div>\n      </div>\n      <div class="row">\n        <div class="input-group col-xs-12">\n          <span class="input-group-addon form-text-description-size">User</span>\n          <select type="text" ng-model="workitem.user" class="form-control" id="useritemUserSelection">\n              <option ng-repeat="user in users" value="{{user.userID}}" ng-model="work.user">{{user.firstName}} {{user.lastName}}</option>\n          </select>\n        </div>\n      </div>   \n      <div class="row">\n        <div class="input-group col-xs-12">\n          <span class="input-group-addon form-text-description-size">Priority</span>\n            <input name="priority" type="number" ng-model="work.priority" placeholder="from 0 to 10" class="form-control" min="0" max="10" id="workitemPriority" required>\n        </div>\n      </div> \n      <div class="row">\n        <div class="input-group col-xs-12">\n            <input type="submit" class="btn btn-primary fill-width" value="Save" ng-disabled="inputForm.$dirty && inputForm.$invalid"> \n        </div>\n      </div>\n      </form>\n    </div>\n   <!--\n     <div ng-repeat="d1 in show">\n        <div class="col-lg-3">\n          <h1>{{d1.name}}</h1>\n          <div ui-sortable>\n          <div class="post-it-note" ng-repeat="d2 in d1.data">\n            <br>\n            <h2 class="center-text">{{$index}} {{d2.title}}</h2>\n            <p class="center-text">{{$index}} {{d2.authur}}</p>\n            <br>\n            <br>\n            {{d2.message}}\n         </div>\n         </div>\n     </div>\n   </div>\n   -->\n</div></div>'),a.put("views/newitem.html",'<div class="center" class="background-color"> <h2>New workitem</h2> <!-- Workitems input --> <form class="center" role="form" ng-submit="createWorkitem()"> <div class="row"> <div class="input-group"> <label for="name">Workitems name</label> <input type="text" ng-model="work.name" placeholder="Name" class="form-control"> </div> <div class="input-group"> <label for="description">Description</label> <input type="text" ng-model="work.description" placeholder="Description" class="form-control"> </div> <div class="input-group"> <label for="status">Status</label> <input type="text" ng-model="work.status" placeholder="Backlog, InProgress, Test or Done" class="form- control"> </div> <div class="input-group"> <label for="priority">Priority</label> <input type="number" ng-model="work.priority" placeholder="from 1 to 10" class="form-control"> </div> <div> <span class="input-group-btn"> <input type="submit" class="btn btn-primary" value="Save" ng-disabled="testForm.$dirty &&   testForm.$invalid" ng-click="add"> </span> </div> </div> {{work}} </form> <!-- Workitems list --> <!--{{workitem}}\n      <p class="form-group" ng-repeat="workitem in workitems">\n        <!--<input type="text" ng-model="workitem" class="form-control">\n     \n        <span class="input-group-btn">\n          <button class="btn btn-green" ng-click="updateWorkitem($index)" aria-label="Update">Update</button>\n          <button class="btn btn-danger" ng-click="removeWorkitem($index)" aria-label="Remove">Remove</button>\n        </span>\n         {{workitem}} <br/>\n        {{workitem.name}}<br/>\n        {{workitem.description}}<br/>\n         {{workitem.status}}<br/>\n         {{workitem.priority}}<br/>\n      </p>--> </div>')}]);