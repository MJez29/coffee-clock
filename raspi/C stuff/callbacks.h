#ifndef CALLBACKS_H
#define CALLBACKS_H

/*
 * Definitions of all API route callbacks
 * 
 * If a route is 
 * 	"GET /a/b/:c" 
 * then its equivalent callback is
 * 	"GET_a_b_c_callback
 */
 
 int add_endpoints(struct _u_instance* instance);

/**
 * 
 * 
 * 
 */
int POST_brew_callback(const struct _u_request* request, struct _u_response* response, void* user_data);

int wait_callback(const struct _u_request* request, struct _u_response* response, void* user_data);



#endif
