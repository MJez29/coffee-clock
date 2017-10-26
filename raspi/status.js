/**
 * Constants sent in response bodies to indicate the outcome of the initial request
 */
module.exports = {
    /**
     * When the response is as expected
     * @type { number }
     */
    OK: 0,

    /**
     * When an invalid order number was passed to the Keurig through the route /brew/:orderNum
     * @type { number }
     */
    INVALID_ORDER_NUMBER: 1,

    /**
     * When the order cannot be deleted, most likely because it is already being brewed
     * @type { number }
     */
    CANNOT_DELETE_ORDER: 2,

    /**
     * When an error occured during the brewing process
     * This error will most likely not be resolved without physically going to the Keurig
     * @type { number }
     */
    INTERNAL_BREWING_ERROR: 3,

    /**
     * When the brew size is not "Small", "Medium", "Large"
     * @type { number }
     */
    INVALID_BREW_SIZE: 4
}