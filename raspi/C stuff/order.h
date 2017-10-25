#ifndef ORDER_H
#define ORDER_H

/**
 * 
 * Object containing all information related to a coffee order
 * 
 */
struct order
{
	/**
	 * The unique reference number for a certain order
	 */
	int order_num;
	
	/**
	 * The expected amount of time until brewing will begin
	 */
	int delay;
};

#endif
