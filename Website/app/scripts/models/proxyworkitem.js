'use strict';

function(workitem, user)
{
	workitem.user = user;
	return workitem;
}