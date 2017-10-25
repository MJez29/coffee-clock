#ifndef COFFEE_MANAGER_H
#define COFFEE_MANAGER_H

struct order make_coffee();

int cancel_order();

int brew_status(int order_num);

void init_coffee_manager();

#endif
