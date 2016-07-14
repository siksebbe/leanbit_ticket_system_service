'use strict';
/*[{"name":"dev","description":"new builds","status":"started","priority":3,"issue":null,"id":1}]*/
var WorkItemCategories = ['Backlog', 'Current', 'Testing', 'Working'];

function WorkItem() {
	var WorkItem = {};
	WorkItem.name = "";
	WorkItem.description = "";
	WorkItem.status = "";
	WorkItem.priority = 0;
	WorkItem.issue = null;
	WorkItem.id = 0;
	return WorkItem;
}

function WorkItem(name, description, status, priority, issue, id) {
	var WorkItem = {};
	WorkItem.name = name;
	WorkItem.description = description;
	WorkItem.status = status;
	WorkItem.priority = priority;
	WorkItem.issue = issue;
	WorkItem.id = id;
	return WorkItem;
}