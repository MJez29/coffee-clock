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

    constructor(d) {
        this.orderNum = new Date().getTime();
        this.delay = d;
        this.status = this.IN_QUEUE;
    }

    /**
     * Changes the status of the order
     * @param { number } status 
     */
    setStatus(status) {
        this.status = status;
    }
}