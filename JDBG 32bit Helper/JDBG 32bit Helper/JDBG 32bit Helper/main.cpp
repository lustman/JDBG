#include <Windows.h>

int main() {

	// we need this for the 32 bit function in kernel32.dll
	return (int) LoadLibraryA;
}