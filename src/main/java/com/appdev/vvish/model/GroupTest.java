package com.appdev.vvish.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GroupTest {
private Map<String,List<Objects>> group;


public GroupTest() {
	super();
}

@Override
public String toString() {
	return "GroupTest [group=" + group + "]";
}

public Map<String, List<Objects>> getGroup() {
	return group;
}

public void setGroup(Map<String, List<Objects>> group) {
	this.group = group;
}

}
