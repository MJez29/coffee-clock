module.exports = class Order {

    static IN_QUEUE = 0;
    static BREWING = 1;
    static DONE = 2;

    constructor(d) {
        this.orderNum = n;
        this.delay = d;
        this.status = IN_QUEUE;
    }

    /**
     * Changes the status of the order
     * @param { number } status 
     */
    setStatus(status) {
        this.status = status;
    }
}