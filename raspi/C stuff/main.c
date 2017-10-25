#include <ulfius.h>
#include <stdio.h>

#define PORT 3000

int main(void)
{
	struct _u_instance instance;
	
	// Initialize instance with the port number
	if (ulfius_init_instance(&instance, PORT, NULL, NULL) != U_OK) 
	{
		fprintf(stderr, "Error ulfius_init_instance, abort\n");
		return(1);
	}
}
