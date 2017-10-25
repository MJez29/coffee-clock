#include <ulfius.h>
#include <stdio.h>

/**
 * 
 * POST /brew callback
 * 
 * 
 * 
 */
int post_brew_callback(const struct _u_request* request, struct _u_response* response, void* user_data)
{
	
}

int get_makecoffee_callback(const struct _u_request* request,
								struct _u_response* response,
								void* user_data)
{
	printf("Request to make coffee from %d");

	return U_OK;
}


int wait_callback(const struct _u_request* request, struct _u_response* response, void* user_data)
{
	

}

int add_endpoints(struct _u_instance* instance)
{
	ulfius_add_endpoint_by_val(&instance, "POST", "/brew", NULL, 0, &post_brew_callback, NULL);
}
