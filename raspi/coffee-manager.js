let Order = require("./order");
const Status = require("./status");
const { spawn } = require("child_process");

/**
 * A container for all the orders. It functions like a queue with the front of the queue being index 0
 * @type { Order[] }
 */
let brewQueue = [];

/**
 * The amount of time (in ms) it takes to brew a cup of coffee
 * @type { number }
 * @const
 */
const BREW_LENGTH = 3 * 60 * 1000;
const PAUSE_LENGTH = 2 * 60 * 1000;

let brewingProcess;

let brewOrder = () => {
// If there is an order to be brewed
if (brewQueue.length > 0) {
	    let order = brewQueue[0];
	    order.setStatus(Order.BREWING);
	
	    let brewingProcess = spawn("python3", ["brew.py", "Large"]);
	    brewingProcess.on("close", (code) => {
		if (code == 0) {
			console.log("Brew successful");
		} else {
			console.log("Brew failed");
		}
		brewOrder();
	    });
	
	    // Sets a timer to go off when the coffee is brewed
	    // setTimeout(onBrewingFinished, BREW_LENGTH);
	} else {
	
	}

};

module.exports = {

    /**
     * Adds an order to the brew queue and returns info about the order
     * 
     * @return { { ordernNum: number, delay: number } }
     */
    addOrder: () => {
        let order = new Order(brewQueue.length * BREW_LENGTH);

        brewQueue.push(order);
	if (brewQueue.length === 1) {
		brewOrder();
	}
        return order;
    },

    /**
     * Returns the order info for a particular order number. If the orderNum is invalid then it throws an
     * error
     * @param { string } orderNum
     * @return { Order }
     */
    getOrder: (orderNum) => {

        // The orderNum passed through the url is stored as a string
        orderNum = parseInt(orderNum);

        for (let i = 0; i < brewQueue.length; ++i) {
            if (brewQueue[i].orderNum === orderNum) {
                return brewQueue[i];
            }
        }

        throw Status.INVALID_ORDER_NUMBER;
    },

    /**
     * Begins brewing the first Order in brewQueue
     */
    brewOrder: brewOrder,

    /**
     * Attempts to delete an order, returns true if successful
     * @param { number } orderNum
     * 
     * @return { number }
     * 
     * Status.OK is returned when the order is deleted successfully
     * Status.CANNOT_DELETE_ORDER is returned when the order is being currently being brewed
     * Status.INVALID_ORDER_NUMBER is returned if the order cannot be found which could be because
     *      - Was previously deleted
     *      - orderNum cannot be converted to an integer
     *      - The order has already been brewed
     */
    deleteOrder: (orderNum) => {
        orderNum = parseInt(orderNum);

        for (let i = 0; i < brewQueue.length; ++i) {
            if (brewQueue[i].orderNum === orderNum) {
                if (i == 0) {
                    return Status.CANNOT_DELETE_ORDER;
                } else {
                    return Status.OK
                }
            }
        }

        // If the orderNum is not a number or is not in the brew queue
        return Status.INVALID_ORDER_NUMBER;
    }

}

/**
 * Called when brewing is finished. It updates the order info and starts a timer to allow the current user
 * to replace the kcup and add more water
 * 
 * @return { void }
 */
onBrewingFinished = () => {
    brewQueue[0].setStatus(Order.DONE);

    // Sets a delay to allow the person to retrieve their coffee and to add more water and a kcup for the next person
    setTimeout(onPausingFinished, PAUSE_LENGTH);
}

/**
 * Called when pausing for the current user to add a new kcup and water is over.
 * Begins the new brew if there is one.
 * 
 * @return { void }

 */
onPausingFinished = () => {
    // Removes the first element
    brewQueue.shift();

    module.exports.brewOrder();
}