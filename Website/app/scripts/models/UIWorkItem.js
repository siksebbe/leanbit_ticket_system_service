function UIWorkItem() {
	var WorkItem = {};
	WorkItem.name = "";
	WorkItem.description = "";
	WorkItem.status = "";
	WorkItem.priority = 0;
	WorkItem.issue = null;
	WorkItem.id = 0;
	WorkItem.userID = 0;
	return WorkItem;
}

function UIWorkItem(WorkItem, userID) {
	WorkItem.userID = userID;
	return WorkItem;
}

function UIWorkItem(name, description, status, priority, issue, id, userID) {
	var WorkItem = {};
	WorkItem.name = name;
	WorkItem.description = description;
	WorkItem.status = status;
	WorkItem.priority = priority;
	WorkItem.issue = issue;
	WorkItem.id = id;
	WorkItem.userID = userID;
	return WorkItem;
}