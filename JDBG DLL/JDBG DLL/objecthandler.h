#pragma once

#include "jdbgpipeline.h"
#include "handler.h"
#include <map>
#include <unordered_map>
#include <iostream>
#include <vector>
#include <set>
#include <utility>
#include <optional>


struct RelationshipData {
	//std::vector<int> referees;
	std::vector<int> referrers;
	std::vector<char> referrersType; // jvmtiHeapReferenceKind
	int klassTag;
};

class ObjectHandler : public Handler {
protected:
	static std::unordered_map<std::string, std::map<long, jweak>> instanceMap;

	static std::unordered_map<std::string, bool> instanceMapInit;

	static std::unordered_map<std::string, int> instanceMapTag;


	static std::vector<RelationshipData>* heapGraph;

	static std::map<int, std::string> classTagMap;

	bool heapGraphBuilt = false;

public:
	ObjectHandler(ServerCommand c, jvmtiEnv* jvmti, JNIEnv* jni, JdbgPipeline* pipeline) : Handler{ c, jvmti, jni, pipeline } {

	}

protected:

	void populateMap(jclass& klass, char* klassName);

	void buildHeapGraph();

	std::optional<std::pair<jclass, jobject>> getObject(long tag, char* klass, std::map<std::string, jclass>& klassMap);


};