

#include "handlerclassnames.h"
#include "jdbgpipeline.h"
#include <string>
#include <map>

HandlerClassNames::HandlerClassNames(jvmtiEnv* jvmti, JNIEnv* jni, JdbgPipeline* pipeline) : Handler{ GET_LOADED_CLASS_NAMES, jvmti, jni, pipeline } {

}




int HandlerClassNames::handle(char* data, DWORD length, char* responseBuffer, std::map<std::string, jclass>& klassMap) {
	int cur = 0;
	for (auto const& x : klassMap) {
		jclass klass = x.second;
		const char* name = x.first.c_str();

		int idx = 0;
		while (name[idx] != '\0') {
			responseBuffer[cur + idx] = name[idx];
			idx++;
		}
		cur += idx;
		responseBuffer[cur] = '\0';
		cur++;
	}


	return cur+1;
}