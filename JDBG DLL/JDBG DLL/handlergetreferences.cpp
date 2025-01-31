

#include "handlergetreferences.h"
#include <optional>
#include "json.hpp"


using json = nlohmann::basic_json<>;


HandlerGetReferences::HandlerGetReferences(jvmtiEnv* jvmti, JNIEnv* jni, JdbgPipeline* pipeline) : ObjectHandler{ GET_REFS, jvmti, jni, pipeline } {

}




struct GraphInfo {
	std::vector<int> adj;
	std::vector<char> relationshipType;
	std::string className;

	NLOHMANN_DEFINE_TYPE_INTRUSIVE(GraphInfo, adj, relationshipType, className)

};



bool dfs(int node, boolean isFirst, std::string& originalClass,
	std::vector<RelationshipData>* heapGraph, 
	std::map<int, GraphInfo>& subGraph, 
	std::set<int>& visited, 
	std::map<int, std::string>& classTagMap) {

	if (visited.find(node) != visited.end())
		return true;
	visited.insert(node);


	int classTag = (*heapGraph)[node].klassTag;

	std::string blacklist[]{
		"java",
		"jdk",
		"sun",
	};

	std::string whitelistOverride[]{
		"java/lang",
		"java/util"
	};

	std::string className;
	if (classTagMap.find(classTag) != classTagMap.end()) {
		className = classTagMap[classTag];
	}
	else {
		className = "??";
	}

	for (std::string& ignorePath : blacklist) {
		if (className.find(ignorePath) != std::string::npos) {

			for (std::string& whitelist : whitelistOverride) {
				if (className.find(whitelist) != std::string::npos) {
					goto skip;
				}
			}


			return false;
		}
	}

	skip: {}

	subGraph[node].className = std::move(className);

	if (subGraph[node].className == "Ljava/lang/Class;") {
		MessageBoxA(nullptr, std::to_string(node).c_str(), "AAAHHH", MB_ICONERROR);
		MessageBoxA(nullptr, std::to_string((*heapGraph)[node].klassTag).c_str(), "AAAHHH", MB_ICONERROR);


	}

	if (!isFirst && (subGraph[node].className.find("java/lang") == std::string::npos
		&& subGraph[node].className.find("java/util") == std::string::npos
		&& subGraph[node].className.find(originalClass) == std::string::npos
		)) {
		MessageBoxA(nullptr, subGraph[node].className.c_str(), "Insider", MB_ICONERROR);

		return true;
	}

	MessageBoxA(nullptr, subGraph[node].className.c_str(), "Insider", MB_ICONERROR);


	for (int i = 0; i < (*heapGraph)[node].referrers.size(); i++) {
		if (dfs((*heapGraph)[node].referrers[i], false, originalClass, heapGraph, subGraph, visited, classTagMap)) {
			subGraph[node].adj.push_back((*heapGraph)[node].referrers[i]);
			subGraph[node].relationshipType.push_back((*heapGraph)[node].referrersType[i]);
		}
	}

	return true;
}


int HandlerGetReferences::handle(char* data, DWORD length, char* responseBuffer, std::map<std::string, jclass>& klassMap) {
	long tag = *((long*)data);
	char* klass = (data + 4);

	auto result{ getObject(tag, klass, klassMap) };
	if (!result.has_value()) {
		return 0;
	}

	auto [objClass, obj] = (*result);


	if (!heapGraphBuilt) {
		buildHeapGraph();
	}

	jlong objTag;
	jvmti->GetTag(obj, &objTag);

	if (objTag == 0) {
		msgLog("The object was not present in the heap graph.");
		return 0;
	}



	std::map<int, GraphInfo> subGraph;
	std::set<int> visited;


	if (classTagMap.find((*heapGraph)[objTag].klassTag) == classTagMap.end()) {
		msgLog("The objects class was not found in the classTagMap");
		return 0;
	}


	std::string klassName = classTagMap[(*heapGraph)[objTag].klassTag];


	dfs(objTag, true, klassName, heapGraph, subGraph, visited, classTagMap);

	
	std::map<std::string, GraphInfo> newGraph;

	for (const auto& entry : subGraph) {
		newGraph.insert({ std::to_string(entry.first), entry.second });
	}


	json response = newGraph;



	std::string dump{ response.dump() };
	std::memcpy((void*)responseBuffer, (void*)dump.c_str(), strlen(dump.c_str()));

	return dump.size();
}