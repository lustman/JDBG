#include "pipeline.h"
#include "jdbgpipeline.h"
#include "handlerclassnames.h"
#include <jni.h>
#include <jvmti.h>
#include <vector>
#include <memory>
#include "handlerclassdata.h"
#include "handlerclassnames.h"
#include "handlertagobjects.h"
#include "handlergetreferences.h"
#include "handlerobjectfields.h"

#include <map>
#include <string>
#include "logpipeline.h"


std::map<std::string, jclass> klassMap;

JdbgPipeline::JdbgPipeline(const wchar_t* p) : ClientPipeline{ p } {

};


void JdbgPipeline::sendStatus(char status) {
	sendData(&status, 1);
}

void populateKlassMap(jvmtiEnv* jvmti) {
	jint classCount;
	jclass* classesPtr;

	jvmti->GetLoadedClasses(&classCount, &classesPtr);


	int cur = 0;
	for (int i = 0; i < classCount; i++) {
		jclass klass = *(classesPtr + i);

		char* name;
		jvmti->GetClassSignature(klass, &name, NULL);

		if (sizeof(name) > 2 && name[0] != '[') {

			std::string n{ name };
			std::string cleanedName{ n.substr(1, n.size() - 2) };

			//MessageBoxA(nullptr, cleanedName.c_str(), "Insider", MB_ICONERROR);


			
			klassMap.insert({cleanedName, klass });

		}

	}
}


void JdbgPipeline::startListen(jvmtiEnv* jvmti, JNIEnv* jni) {

	LogPipeline log{ L"\\\\.\\pipe\\jdbg_log" };


	populateKlassMap(jvmti);



	std::vector<std::unique_ptr<Handler>> handlers;
	handlers.push_back(std::make_unique<HandlerClassNames>(HandlerClassNames{ jvmti, jni, this }));
	handlers.push_back(std::make_unique<HandlerClassData>(HandlerClassData{ jvmti, jni, this }));
	handlers.push_back(std::make_unique<HandlerTagObjects>(HandlerTagObjects{ jvmti, jni, this }));
	handlers.push_back(std::make_unique<HandlerGetReferences>(HandlerGetReferences{ jvmti, jni, this }));
	handlers.push_back(std::make_unique<HandlerObjectFields>(HandlerObjectFields{ jvmti, jni, this }));

	for (std::unique_ptr<Handler>& h : handlers) {
		h->setLogPipeline(&log);
	}


	constexpr int BUFFER_SIZE = 500;
	constexpr int RESPONSE_BUFFER_SIZE = 50000000;

	char* responseBuffer = new char[RESPONSE_BUFFER_SIZE];

	while(true) {
		char* buffer = new char[BUFFER_SIZE];

		DWORD bytesRead{ 0 };

		if (!ReadFile(handle, buffer, BUFFER_SIZE, &bytesRead, NULL)) {
			break;
		}
		if (bytesRead == 0) {
			MessageBox(nullptr, L"Failed to read message", L"Insider", MB_ICONERROR);
			continue;
		}

		ServerCommand command = static_cast<ServerCommand>(buffer[0]);

		for (std::unique_ptr<Handler>& h : handlers) {
			if (h->getCommand() == command) {

				// make space for the code byte
				responseBuffer[0] = 0;
				int len = h->handle(buffer + 1, bytesRead - 1, responseBuffer+1, klassMap);
				sendData(responseBuffer, len + 1);

			}
		}

		log.flush();
		delete[] buffer;
	}

	delete[] responseBuffer;
}

