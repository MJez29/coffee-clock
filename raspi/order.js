const COFFEE_SIZE = require("./coffee-size");

module.exports = class Order {

    static get IN_QUEUE() {
        return 0;
    }
    static get BREWING() {
        return 1;
    }
    static get DONE() {
        return 2;
    }

    /**
     * 
     * @param { string } size 
     */
    constructor(size) {
        this.orderNum = new Date().getTime();
        this.status = this.IN_QUEUE;
        this.size = size;
    }

    /**
     * Changes the status of the order
     * @param { number } status 
     */
    setStatus(status) {
        this.status = status;
    }

    getSize() {
        return this.size;
    }

    toJSON() {
        return {
            size: this.size,
            orderNum: this.orderNum,
            status: this.status
        };
    }

    /**
     * Returns true if it is a valid size to be brewed
     * @param { string } size 
     * 
     * @return { boolean }
     */
    static isValidSize(size) {
        return size === COFFEE_SIZE.SMALL || size === COFFEE_SIZE.MEDIUM || size === COFFEE_SIZE.LARGE;
    }
}